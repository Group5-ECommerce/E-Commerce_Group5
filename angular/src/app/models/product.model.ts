//has to be interface for optionals
export interface Product {
  productId: number ;
  productName: string;
  productStock?: number;
  productImage?: string;
  productPrice: number;
  storageId?: number;
}
