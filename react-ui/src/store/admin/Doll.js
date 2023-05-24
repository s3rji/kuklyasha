export default class Doll {
    constructor() {
        this._name = ''
        this._description = ''
        this._price = 0
        this._quantity = 0;
        this._poster = ''
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get description() {
        return this._description;
    }

    set description(description) {
        this._description = description;
    }

    get price() {
        return this._price;
    }

    set price(price) {
        this._price = price;
    }

    get quantity() {
        return this._quantity;
    }

    set quantity(quantity) {
        this._quantity = quantity;
    }

    get poster() {
        return this._poster;
    }

    set poster(poster) {
        this._poster = poster;
    }
}