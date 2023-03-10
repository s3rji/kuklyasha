import {$authHost} from "./index";

export const getCart = async () => {
    const {data} = await $authHost.get('api/cart')
    return data
}

export const addCartItem = async (doll) => {
    const {data} = await $authHost.post('api/cart', doll)
    return data
}

export const removeCartItem = async (id) => {
    await $authHost.delete('api/cart/' + id)
}

export const removeAllCartItems = async () => {
    await $authHost.delete('api/cart/')
}

export const updateCartItem = async (id, cartItem) => {
    await $authHost.put('api/cart/' + id, cartItem)
}
