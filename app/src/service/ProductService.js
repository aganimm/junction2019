export default class ProductService {
  _apiBase = 'http://212.109.194.223:8090/api/product';

  async getData (url) {
    const res = await fetch(url);

    if (!res.ok) {
      throw new Error(`Code ${ res.code } for url ${ url }.`);
    }

    return await res.json();
  };

  async postData (url, data = {}) {
    const res = fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    return await res.json();
  }

  /**
   * Массив списков пользователя
   * @returns {Promise<SpeechRecognitionResultList>}
   */
  async getProductLists () {
    const res = await this.getData(`${ this._apiBase }/list`);
    return res.results;
  }

  /**
   * Все продукты в одном листе
   * @param listId
   * @returns {Promise<SpeechRecognitionResultList>}
   */
  async getProductsByListId (listId) {
    const res = await this.getData(`${ this._apiBase }/list/${ listId }`);
    return res.results;
  }

  /**
   * Удалить список продуктов
   * @param listId
   * @returns {Promise<void>}
   */
  async removeProductList (listId) {
    const res = await this.postData(
      `${ this._apiBase }/list/${ listId }/remove`
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
    const res = await this.postData(
      `${ this._apiBase }/list/${ listId }/product/${ productId }/remove`
    );
  }

  /**
   * Создает новый список
   * @param list
   * @returns {Promise<void>}
   */
  async createProductList (list) {
    const res = await this.postData(
      `${ this._apiBase }/list/add`,
      list
    );
  }

  /**
   * Добавляет продукт в список
   * @param listId
   * @param productId
   * @returns {Promise<void>}
   */
  async addProductToList (listId, productId) {
    const res = await this.postData(
      `${ this._apiBase }/list/${ listId }/add`,
      { productId }
    );
  }
}