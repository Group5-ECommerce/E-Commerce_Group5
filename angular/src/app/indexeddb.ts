import Dexie from "dexie";

export class IndexedDatabase extends Dexie {

  constructor() {
    super("CartDatabase");

    this.version(1).stores({
      userCart: '++id, [userId+productId], amt, productName, productStock, productImage, productPrice, storageId'
    });

    this.open().then(data => console.log("DB Opened"))
      .catch(err => console.log(err.message))

  }
}
