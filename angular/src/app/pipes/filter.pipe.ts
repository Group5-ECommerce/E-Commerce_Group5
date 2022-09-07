import { Pipe, PipeTransform } from '@angular/core';
import { Product } from '../models/product.model';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(products: any, query: any): Product[] {

    //check if search term is undefined
    if (query === undefined) return products;
    //return updated products array
    return products.filter((p: Product) => {
      if (p.productName) {
        return p.productName.toLowerCase() == query.toLowerCase()
          || p.productName.toLowerCase().indexOf(query.toLowerCase()) !== -1
      }
      return false
    })

  }
}
