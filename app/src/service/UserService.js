import ApiService from './ApiService';
import UserCache from './UserCache';

export default class UserService {
  static _it = new UserService();

  _apiUser = ApiService._apiBase + '/user';

  async registration(userData) {
    return await ApiService.postData(
      `${ this._apiUser }/registration`,
      null,
      userData
    );
  }

  async getProfile() {
    const res = await ApiService.getData(
      `${ this._apiUser }/profile`,
      UserCache._it.getMiniAppToken()
    );

    return res.json()
  }

  async updateProfile(profile) {
    return await ApiService.postData(
      `${ this._apiUser }/profile`,
      UserCache._it.getMiniAppToken(),
      profile
    );
  }
}