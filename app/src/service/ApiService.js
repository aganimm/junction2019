import UserCache from '../UserCache';

export default class ApiService {
  static _apiBase = 'https://zirodev.com/api';

  static async getData (url) {
    const res = await fetch(url, {
      headers: {
        'Application-Token': UserCache._it.getMiniAppToken()
      }
    });

    if (!res.ok) {
      throw new Error(`Code ${ res.code } for url ${ url }.`);
    }

    return await res.json();
  };

  static async postData (url, data = {}) {
    const res = await fetch(url, {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        'Application-Token': UserCache._it.getMiniAppToken()
      },
      body: JSON.stringify(data)
    });

    return await res.json();
  }
}