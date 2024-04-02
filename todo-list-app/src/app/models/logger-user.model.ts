import { JwtPayload } from "jwt-decode"

export class LoggedUser {
  constructor(public id: string, private _token: string, private _tokenExpirationDate: number, public decodedToken:JwtPayload) { }

  get token() {
    if (!this._tokenExpirationDate || Date.now() > this._tokenExpirationDate) {
      return undefined as string
    }
    return this._token
  }

  static fromJson(json:string) {
    const data = JSON.parse(json)
    return Object.assign(new LoggedUser('','',0,undefined as JwtPayload), data)
  }
}

