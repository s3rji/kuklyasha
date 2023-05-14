import {makeAutoObservable} from "mobx";

export default class UserStore {
    constructor() {
        this._isAuth = false
        this._roles = []
        this._activeRole = ""
        this._user = {}
        makeAutoObservable(this)
    }

    setIsAuth(bool) {
        this._isAuth = bool
    }

    setRoles(roles) {
        this._roles = roles
    }

    setActiveRole(activeRole) {
        this._activeRole = activeRole
    }

    setUser(user) {
        this._user = user
    }

    get isAuth() {
        return this._isAuth
    }

    get roles() {
        return this._roles
    }

    get activeRole() {
        return this._activeRole
    }

    get user() {
        return this._user
    }

    logOut() {
        this._isAuth = false
        this._roles = []
        this._activeRole = ""
        this._user = {}
    }
}