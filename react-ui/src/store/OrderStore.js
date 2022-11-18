import {makeAutoObservable} from "mobx";

export default class OrderStore {
    constructor() {
        this._orders = []
        makeAutoObservable(this)
    }

    get orders() {
        return this._orders;
    }

    setOrders(orders) {
        this._orders = orders;
    }
}