import {$authHost} from "./index";

export const getOrders = async () => {
    const {data} = await $authHost.get('api/orders' )
    return data
}