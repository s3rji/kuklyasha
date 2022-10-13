import {$authHost, $host} from "./index";
import jwt_decode from "jwt-decode";

export const registration = async (name, email, password) => {
    const {data} = await $host.post('api/profile', {name, email, password})
    return data
}

export const login = async (email, password, rememberMe) => {
    const {data} = await $host.post('api/auth/login', {email, password, rememberMe})
    localStorage.setItem('token', data.token)
    return jwt_decode(data.token)
}

export const check = async () => {
    const {data} = await $authHost.get('api/auth' )
    localStorage.setItem('token', data.token)
}

export const get = async () => {
    const {data} = await $authHost.get('api/profile' )
    return data
}