import ApiService from './ApiService';

export default class UserService {
  _apiUser = ApiService._apiBase + '/user';

  async registration(userData) {
    return await ApiService.postData(
      `${ this._apiUser }/registration`,
      userData
    );
  }
}