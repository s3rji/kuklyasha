import {makeAutoObservable} from "mobx";

export default class CartStore {
    constructor() {
        this._cart = []
        makeAutoObservable(this)
    }

    get cart() {
        return this._cart;
    }

    setCart(cart) {
        this._cart = cart;
    }

    updateCartItemQuantity = (index, quantity) => {
        this._cart[index].quantity = quantity
    }

    removeCartItem = (index) => {
        this._cart.splice(index, 1)
    }

    isCartItemWithDollNotExist = (doll) => {
        return this._cart.findIndex((cartItem) => cartItem.doll.id === doll.id) === -1;
    }
}