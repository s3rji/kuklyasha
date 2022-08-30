import {$host} from "./index";

export const fetchDolls = async () => {
    const {data} = await $host.get("api/dolls")
    return data;
}

export const fetchOneDoll = async (id) => {
    const {data} = await $host.get('api/dolls/' + id)
    return data
}

export const fetchDollsByPage = async (page, limit) => {
    const {data} = await $host.get('api/dolls', {params: {page, limit}})
    return data
}