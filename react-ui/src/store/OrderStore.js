import {makeAutoObservable} from "mobx";

export default class OrderStore {
    constructor() {
        this._orders = []
        this._page = 0
        this._limit = 20
        this._total = 0
        this._sort = 'id'
        this._sortDirection = 'asc'
        this._filter = 'id'
        this._search = ''
        makeAutoObservable(this)
    }

    get orders() {
        return this._orders;
    }

    setOrders(orders) {
        this._orders = orders;
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

    get sort() {
        return this._sort;
    }

    setSort(sort) {
        this._sort = sort;
    }

    get sortDirection() {
        return this._sortDirection;
    }

    setSortDirection(direction) {
        this._sortDirection = direction;
    }

    get filter() {
        return this._filter;
    }

    setFilter(filter) {
        this._filter = filter;
    }

    get search() {
        return this._search;
    }

    setSearch(search) {
        this._search = search;
    }
}