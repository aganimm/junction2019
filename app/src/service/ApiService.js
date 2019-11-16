export default class ApiService {
  static _apiBase = 'https://zirodev.com/api';

  static async getData (url, token) {
    const res = await fetch(url, {
      headers: {
        'Application-Token': token
      }
    });

    if (!res.ok) {
      throw new Error(`Code ${ res.code } for url ${ url }.`);
    }

    return await res.json();
  };

  static async postData (url, token = '', data = {}) {
    const headers = {
      'Content-Type': 'application/json',
    };
    if (token) {
      headers['Application-Token'] = token;
    }
    const res = await fetch(url, {
      method: 'POST',
      mode: 'cors',
      headers,
      body: JSON.stringify(data)
    });

    return await res.json();
  }
}