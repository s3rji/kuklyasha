import {makeAutoObservable} from "mobx";
import {Doll} from "./admin/index";

export default class DollStore {
    constructor() {
        this._catalog = []
        this._selected = new Doll()
        this._page = 0
        this._limit = 8
        this._total = 0;
        makeAutoObservable(this)
    }

    get catalog() {
        return this._catalog;
    }

    setCatalog(catalog) {
        this._catalog = catalog;
    }

    get selected() {
        return this._selected;
    }

    setSelected(selected) {
        this._selected = selected;
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