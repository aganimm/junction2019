export default class ApiService {
  static _apiBase = 'http://212.109.194.223:8090/api';

  static async getData (url) {
    const res = await fetch(url);

    if (!res.ok) {
      throw new Error(`Code ${ res.code } for url ${ url }.`);
    }

    return await res.json();
  };

  static async postData (url, data = {}) {
    const res = fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    return await res.json();
  }
}