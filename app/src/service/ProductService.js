import ApiService from './ApiService';
import UserCache from '../UserCache';

export default class ProductService {
  static _it = new ProductService();

  _apiProduct = ApiService._apiBase + '/product';

  async getProducts(count = 20, offset = 0) {
    return await ApiService.getData(`${ this._apiProduct }?count=${count}&offset=${offset}`, UserCache._it.getMiniAppToken());
  }

  /**
   * Массив списков пользователя
   * @returns {Promise<SpeechRecognitionResultList>}
   */
  async getProductLists () {
    return await ApiService.getData(`${ this._apiProduct }/list`, UserCache._it.getMiniAppToken());
  }

  /**
   * Все продукты в одном листе
   * @param listId
   * @returns {Promise<SpeechRecognitionResultList>}
   */
  async getProductsByListId (listId) {
    const res = await ApiService.getData(`${ this._apiProduct }/list/${ listId }`);
    return res.results;
  }

  /**
   * Удалить список продуктов
   * @param listId
   * @returns {Promise<void>}
   */
  async removeProductList (listId) {
    const res = await this.postData(
      `${ this._apiProduct }/list/${ listId }/remove`
    );
    return res.result;
  }

  /**
   * Удалить продукт из списка
   * @param listId
   * @param productId
   * @returns {Promise<void>}
   */
  async removeProductFromList (listId, productId) {
    const res = await ApiService.postData(
      `${ this._apiProduct }/list/${ listId }/product/${ productId }/remove`
    );
    return res.result;
  }

  /**
   * Создает новый список
   * @param list
   * @returns {Promise<void>}
   */
  async createProductList (list) {
    const res = await ApiService.postData(
      `${ this._apiProduct }/list/add`,
      list
    );
    return res.result;
  }

  /**
   * Добавляет продукт в список
   * @param listId
   * @param productId
   * @returns {Promise<void>}
   */
  async addProductToList (listId, productId) {
    const res = await ApiService.postData(
      `${ this._apiProduct }/list/${ listId }/add`,
      { productId }
    );
    return res.result;
  }
}