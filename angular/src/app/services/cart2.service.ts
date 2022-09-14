import { Injectable } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { Observable, Subject } from 'rxjs';
import { IndexedDatabase } from '../indexeddb';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class Cart2Service {
  tableName = "userCart"
  oktaId?: string;
  watcher = new Subject()

  constructor(private db: IndexedDatabase, private _oktaStateService: OktaAuthStateService) {
    this._oktaStateService.authState$.subscribe(
      (s) => {
        this.oktaId = s.idToken?.claims.sub
      }
    );
  }

  activateWatcher(): Observable<any> {
    return this.watcher.asObservable();
  }

  async addProduct(product: Product) {
    let existingItem = await this.db.table(this.tableName).where({ productId: product.productId, userId: this.oktaId }).toArray()
    // console.log(existingItem)
    this.db.transaction('rw', this.db.table(this.tableName), async () => {
      let item;
      if (existingItem.length != 0) {
        item = existingItem[0]
        item.amt++;
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
          storageId: product.storageId
        }
        this.db.table(this.tableName).put(
          item
        )
      }


      existingItem = await this.db.table(this.tableName).where({ productId: product.productId, userId: this.oktaId }).toArray()
      this.watcher.next(existingItem)

    }).then((res) => {
      console.log(res)
    }).catch((err) => {
      console.log(err)
    })
  }

  async getUserCart() {
    let items = await this.db.table(this.tableName).where({ userId: this.oktaId }).toArray();
    console.log(items)
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
      // console.log(items)
      this.watcher.next(items)
    }).catch((err) => {
      console.log(err)
    })
  }

  clearCart() {
    this.db.table(this.tableName).clear();
  }

  fillCartWithProducts(cartItems: any) {
    console.log("cartItems inside fillcartwithproducts", cartItems)
    this.db.transaction('rw', this.db.table(this.tableName), () => {
      cartItems.forEach((product: any) => {
        let item = {
          userId: product.oktaId,
          amt: product.amt,
          productId: product.productId,
          productName: product.productName,
          productPrice: product.productPrice,
          productImage: product.productImage,
          productStock: product.productStock,
          storageId: product.storageId
        }
        this.db.table(this.tableName).put(
          item
        )
      })
    }).then((res) => {
      console.log(res)
    }).catch((err) => {
      console.log(err)
    })


  }

}
