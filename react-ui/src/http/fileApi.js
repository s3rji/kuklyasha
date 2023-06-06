import {$authHost} from "./index";

export const uploadFile = async (file) => {
    const {data} = await $authHost.post("/api/admin/file", file)
    return data
}

export const deleteFiles = async (files) => {
    await $authHost.delete("/api/admin/file", {data: files})
}