import {$authHost} from "./index";

export const getOrders = async () => {
    const {data} = await $authHost.get('api/orders' )
    return data
}

export const createOrder = async (purchasedDolls) => {
    const {data} = await $authHost.post('api/orders', purchasedDolls)
    return data
}