import {$authHost} from "./index";

export const getOrders = async () => {
    const {data} = await $authHost.get('api/orders' )
    return data
}

export const getOrdersWithUser = async () => {
    const {data} = await $authHost.get('api/admin/orders' )
    return data
}

export const getLimitWithUserAndSort = async (page, limit, sort, direction) => {
    const {data} = await $authHost.get('api/admin/orders', {params: {page, limit, sort, direction}})
    return data
}

export const getLimitWithUserByFilter = async (page, limit, sort, direction, field, filter) => {
    const {data} = await $authHost.get('api/admin/orders', {params: {page, limit, sort, direction, field, filter}})
    return data
}

export const updateOrder = async (id, orderChange) => {
    await $authHost.patch('api/admin/orders/' + id, orderChange)
}

export const createOrder = async (purchasedDolls) => {
    const {data} = await $authHost.post('api/orders', purchasedDolls)
    return data
}