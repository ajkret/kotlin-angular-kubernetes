export interface AuthenticationResponse {
  access_token: string,
  expires_in: number,
  refresh_expires_in: number,
  refresh_token: string,
  token_type: string,
  'not-before-policy': number,
  session_state: string,
  scope: string
}

export class User {
  constructor(public id: string, private _token: string, private _tokenExpirationDate: Date) { }

  get token() {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate) {
      return null
    }
    return this.token
  }

}