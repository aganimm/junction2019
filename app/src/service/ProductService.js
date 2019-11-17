import ApiService from './ApiService';
import UserCache from './UserCache';

export default class ProductService {
  static _it = new ProductService();

  _apiProduct = ApiService._apiBase + '/product';

  async getProducts() {
    return await ApiService.getData(
      `${ this._apiProduct }`,
      UserCache._it.getMiniAppToken()
    );
  }
}