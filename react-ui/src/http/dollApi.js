import {$host} from "./index";

export const fetchDolls = async () => {
    const {data} = await $host.get("api/dolls")
    return data;
}