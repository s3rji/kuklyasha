import {makeAutoObservable} from "mobx";

export default class NavigationStore {
    constructor() {
        this._ways = [
            {name: 'Каталог', href: '#', id: 1},
            {name: 'Конструктор', href: '#', id: 2},
            {name: 'О магазине', href: '#', id: 3},
            {name: 'Контакты', href: '#', id: 4},
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