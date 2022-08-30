import {makeAutoObservable} from "mobx";

export default class DollStore {
    constructor() {
        this._dolls = []
        this._page = 0
        this._limit = 5
        this._total = 0;
        makeAutoObservable(this)
    }

    get dolls() {
        return this._dolls;
    }

    setDolls(dolls) {
        this._dolls = dolls;
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