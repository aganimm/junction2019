import UserService from './service/UserService';
import ProductService from './service/ProductService';

export default class UserCache {
  static _it = new UserCache();
  _userService = new UserService();

  _accessToken;
  _userId;
  _miniAppToken;

  setAccessToken = (token) => {
    console.log('Set access token: ', token);
    this._accessToken = token;
  };

  getAccessToken = () => {
    return this._accessToken;
  };

  setUserId = (userId) => {
    console.log('Set user id: ', userId);
    this._userId = userId;
  };

  getUserId = () => {
    return this._userId;
  };

  setMiniAppToken = (miniAppToken) => {
    console.log('Set mini app token: ', miniAppToken);
    this._miniAppToken = miniAppToken;
  };

  getMiniAppToken = () => {
    return this._miniAppToken;
  };

  refreshMiniAppToken = () => {
    const { _userId, _accessToken } = this;
    if (_userId && _accessToken) {
      this._userService.registration({ userId: _userId, accessToken: _accessToken })
        .then(result => {
          const {status, miniAppToken} = result;

          if (status === 'USER_CREATED') {
            this.setMiniAppToken(miniAppToken);

            ProductService._it.getProductLists().then(result => {
              console.log(result);
            })
          } else {
            console.log('Internal error: ', _userId, _accessToken);
          }
        })
    }
  }
}