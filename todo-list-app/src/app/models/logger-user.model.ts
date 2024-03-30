import { JwtPayload } from "jwt-decode"

export class LoggedUser {
  constructor(public id: string, private _token: string, private _tokenExpirationDate: Date, public decodedToken:JwtPayload) { }

  get token() {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate) {
      return null
    }
    return this.token
  }
}