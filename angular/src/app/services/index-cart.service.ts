import { Injectable } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { Observable, Subject } from 'rxjs';
import { IndexedDatabase } from '../indexeddb';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class IndexCartService {
  tableName = "userCart"
  oktaId?: string = "guest";
  watcher = new Subject()
  sortingColName = "";
  sortBtnClicks!: number;
  length: number = 0;

  constructor(private db: IndexedDatabase, private _oktaStateService: OktaAuthStateService) {
    this._oktaStateService.authState$.subscribe(
      (s) => {
        // If this is a user, get their user id
        if (s.isAuthenticated) {
          this.oktaId = s.idToken?.claims.sub;
        }
        // If not, we should still get any guest cart items stored in this browser.
        this.getUserCart();
      }
    );
  }

  activateWatcher(): Observable<any> {
    return this.watcher.asObservable();
  }

  async addProduct(product: Product) {

    if (!this.oktaId) this.oktaId = "guest";

    let existingItem = await this.db.table(this.tableName).where({ productId: product.productId, userId: this.oktaId }).toArray()
    console.log(existingItem)
    this.db.transaction('rw', this.db.table(this.tableName), async () => {
      let item;
      if (existingItem.length != 0) {
        item = existingItem[0]
        if (item && item.amt < item.productStock) {
          item.amt++;
        }
        let key = (await this.db.table(this.tableName).where({ userId: this.oktaId, productId: item.productId }).primaryKeys())[0];
        this.db.table(this.tableName).update(key, { amt: item.amt })
      } else {
        item = {
          userId: this.oktaId,
          amt: 1,
          productId: product.productId,
          productName: product.productName,
          productPrice: product.productPrice,
          productImage: product.productImage,
          productStock: product.productStock,
          storageId: product.storageId,
          category: product.category
        }
        this.length += 1;
        this.db.table(this.tableName).put(
          item
        )
      }


      existingItem = await this.db.table(this.tableName).where({ productId: product.productId, userId: this.oktaId }).toArray()
      this.watcher.next(existingItem)

    }).then((res) => {
      // console.log(res)
    }).catch((err) => {
      console.log(err)
    })
  }

  deleteProduct(product: Product) {
    this.db.transaction('rw', this.db.table(this.tableName), async () => {
      let key = (await this.db.table(this.tableName).where({ userId: this.oktaId, productId: product.productId }).primaryKeys())[0];

      let item = await this.db.table(this.tableName).get(key);
      if (item.amt > 1) {
        return this.db.table(this.tableName).update(key, { amt: --item.amt })
      } else {
        return this.db.table(this.tableName).delete(key)
      }

    }).then(async (res) => {
      console.log(res)
      this.length -= 1;
      // let items = await this.db.table(this.tableName).where({ userId: this.oktaId }).toArray()
      // console.log(items)
      // this.watcher.next(items)
    }).catch((err) => {
      console.log(err)
    })
  }

  async getUserCart() {
    if (!this.oktaId) return [];

    let items = await this.db.table(this.tableName).where({ userId: this.oktaId }).toArray();
    console.log(items)
    // Items is sometimes incorrectly 0 directly logging in. This messes with the cart length in the nav bar.
    // The items were queued to be added in fillCartWithProducts, but this function runs before that transaction is complete afaik.
    if (items.length !== 0) this.length = items.length;

    this.watcher.next(items)
    return items;
  }

  updateItemAmount(item: any) {
    this.db.transaction('rw', this.db.table(this.tableName), async () => {
      let key = (await this.db.table(this.tableName).where({ userId: this.oktaId, productId: item.productId }).primaryKeys())[0];

      return this.db.table(this.tableName).update(key, { amt: item.amt })
    }).then(async (res) => {
      console.log(res)
      let items = await this.db.table(this.tableName).where({ userId: this.oktaId }).toArray()
      // console.log(items)
      this.watcher.next(items)
    }).catch((err) => {
      console.log(err)
    })
  }

  deleteItem(item: any) {

    this.db.transaction('rw', this.db.table(this.tableName), async () => {
      let key = (await this.db.table(this.tableName).where({ userId: this.oktaId, productId: item.productId }).primaryKeys())[0];

      return this.db.table(this.tableName).delete(key)
    }).then(async (res) => {
      console.log(res)
      let items = await this.db.table(this.tableName).where({ userId: this.oktaId }).toArray()
      this.length -= 1;
      // console.log(items)
      this.watcher.next(items)
    }).catch((err) => {
      console.log(err)
    })
  }

  clearCart() {
    this.length = 0;
    return this.db.table(this.tableName).clear();
  }

  async fillCartWithProducts(cartItems: any): Promise<boolean> {
    try {
      const guestItems = await this.db.table(this.tableName).where({ userId: 'guest' }).modify({ userId: this.oktaId });
      this.length = guestItems;

      const res = await this.db.transaction('rw', this.db.table(this.tableName), () => {
        cartItems.forEach(async (product: any) => {
          let item = {
            userId: product.oktaId,
            amt: product.amt,
            productId: product.productId,
            productName: product.productName,
            productPrice: product.productPrice,
            productImage: product.productImage,
            productStock: product.productStock,
            storageId: product.storageId,
            category: product.category
          };

          const instancesOfItemtInGuestCart = await this.db.table(this.tableName).where({ productId: product.productId, userId: this.oktaId }).toArray()

          // If the guest's cart does not contains this item, add the item from the server.
          // Otherwise, keep the version from the guest (which the user inputted more recently).
          if (instancesOfItemtInGuestCart.length === 0) {
            this.db.table(this.tableName).put(
              item
            );
            this.length += 1;
          }
        });
      });
      return true;
    } catch (err) {
      console.log(err);
      return false;
    }
  }


  sortCart(field: string) {
    // console.log(event.target.innerText, this.sortingColName)
    if (field !== this.sortingColName) {
      this.sortBtnClicks = 0
      this.sortingColName = field;
    }

    if (this.sortBtnClicks % 2 == 0) { //desc
      this.db.table(this.tableName).orderBy(this.sortingColName).reverse().toArray().then(res => {

        // this.cartList = res;
        this.sortBtnClicks++;
        this.watcher.next(res);
        // console.log(this.cartList)
      })
    } else { //asc
      this.db.table(this.tableName).orderBy(this.sortingColName).toArray().then(res => {
        // this.cartList = res;

        this.sortBtnClicks++;
        this.watcher.next(res);
        // console.log(this.cartList)
      })
    }
    // console.log(this.sortBtnClicks)

    // console.log(this.db.table(this.tableName).orderBy(event.target.value).reverse())
  }

}
