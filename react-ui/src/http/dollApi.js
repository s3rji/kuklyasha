import {$authHost, $host} from "./index";

export const getDolls = async () => {
    const {data} = await $host.get("api/dolls")
    return data
}

export const getDoll = async (id) => {
    const {data} = await $host.get('api/dolls/' + id)
    return data
}

export const getDollsByPage = async (page, limit) => {
    const {data} = await $host.get('api/dolls', {params: {page, limit}})
    return data
}

export const createDoll = async (doll) => {
    const {data} = await $authHost.post('api/dolls', doll)
    return data
}

export const updateDoll = async (id, doll) => {
    await $authHost.put('api/dolls/' + id, doll)
}