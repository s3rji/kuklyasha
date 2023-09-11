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
    const {data} = await $authHost.get('api/auth')
    localStorage.setItem('token', data.token)
    return jwt_decode(data.token)
}

export const getUser = async () => {
    const {data} = await $authHost.get('api/profile')
    return data
}

export const updateProfile = async (user) => {
    await $authHost.put('api/profile', user)
}

export const getUserById = async (id) => {
    const {data} = await $authHost.get('api/admin/users/' + id)
    return data
}

export const getUserOrders = async (id) => {
    const {data} = await $authHost.get('api/admin/users/' + id + '/orders')
    return data
}

export const getAllUsers = async () => {
    const {data} = await $authHost.get('api/admin/users')
    return data;
}

export const getUsersByPage = async (page, limit) => {
    const {data} = await $authHost.get('api/admin/users', {params: {page, limit}})
    return data
}

export const updateUser = async (id, changeUser) => {
    await $authHost.patch('api/admin/users/' + id, changeUser)
}