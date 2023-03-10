import {$authHost} from "./index";

export const getOrders = async () => {
    const {data} = await $authHost.get('api/orders' )
    return data
}

export const createOrder = async (order) => {
    const {data} = await $authHost.post('api/orders', order)
    return data
}