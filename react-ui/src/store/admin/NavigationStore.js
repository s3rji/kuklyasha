import {makeAutoObservable} from "mobx";

export default class NavigationStore {
    constructor() {
        this._ways = [
            {name: 'Каталог', href: '/admin/catalog', id: 1},
            {name: 'Заказы', href: '/admin/orders', id: 2},
            {name: 'Пользователи', href: '/admin/users', id: 3},
        ]
        this._selectedWay = 1
        makeAutoObservable(this)
    }

    get ways() {
        return this._ways;
    }

    get selectedWay() {
        return this._selectedWay;
    }

    setSelectedWay(id) {
        this._selectedWay = id;
    }
}