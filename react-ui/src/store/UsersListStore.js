import {makeAutoObservable} from "mobx";

export default class UserStore {
    constructor() {
        this._allUsers = []
        this._page = 0
        this._limit = 20
        this._total = 0
        makeAutoObservable(this)
    }

    setAllUsers(allUsers) {
        this._allUsers = allUsers
    }


    get allUsers() {
        return this._allUsers
    }

    get page() {
        return this._page;
    }

    setPage(page) {
        this._page = page;
    }

    get limit() {
        return this._limit;
    }

    setLimit(limit) {
        this._limit = limit;
    }

    get total() {
        return this._total;
    }

    setTotal(total) {
        this._total = total;
    }
}