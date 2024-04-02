import { JwtPayload } from "jwt-decode"

export class LoggedUser {
  constructor(public id: string, public _token: string, private _tokenExpirationDate: number, public decodedToken:JwtPayload) { }

  get token() {
    if (!this._tokenExpirationDate || Date.now() > this._tokenExpirationDate) {
      return 'shit'
    }
    return this._token
  }
}
