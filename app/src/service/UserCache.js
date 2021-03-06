import UserService from './UserService';

export default class UserCache {
  static _it = new UserCache();
  _userService = new UserService();

  _accessToken;
  _userId;
  _miniAppToken;
  _photo;
  _firstName;
  _lastName;

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

  refreshMiniAppToken = (callback) => {
    const { _userId, _accessToken } = this;
    if (_userId && _accessToken) {
      console.log('Reg with ', _userId, _accessToken);
      this._userService.registration(
        { userId: _userId, accessToken: _accessToken })
        .then(result => {
          const { status, miniAppToken } = result;

          if (status === 'USER_CREATED' || status === 'USER_UPDATED') {
            this.setMiniAppToken(miniAppToken);
            if (callback) {
              callback();
            }
          } else {
            console.log('Internal error: ', _userId, _accessToken);
          }
        })
        .catch(e =>
          console.log(e)
        );
    } else {
      console.log("Can't refresh token!");
    }
  }
}