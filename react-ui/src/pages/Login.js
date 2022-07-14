import React, {useContext, useState} from "react";
import {LockClosedIcon} from "@heroicons/react/solid"
import {NavLink, useNavigate} from "react-router-dom";
import logo from "../assets/main.jpeg";
import {Context} from "../index";
import {login} from "../http/userAPI";
import {CATALOG_ROUTE} from "../utils/consts";
import {observer} from "mobx-react-lite";

const Login = observer(() => {
    const {user} = useContext(Context)
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [rememberMe, setRememberMe] = useState(false)
    const navigate = useNavigate()

    const click = async () => {
        try {
            let data = await login(email, password, rememberMe)
            user.setUser(data)
            user.setIsAuth(true)
            navigate(CATALOG_ROUTE)
        } catch (e) {
            alert(e)
        }
    }

    return (
        <>
            <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
                <div className="max-w-md w-full space-y-8">
                    <div>
                        <img
                            className="mx-auto h-12 w-auto"
                            src={logo}
                            alt="Kuklyasha"
                        />
                        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">Войдите в свой аккаунт</h2>
                    </div>

                    <form className="mt-8 space-y-6">
                        {/*<input type="hidden" name="remember" defaultValue="true"/>*/}
                        <div className="rounded-md shadow-sm -space-y-px">
                            <div>
                                <label htmlFor="email-address" className="sr-only">
                                    Email address
                                </label>
                                <input
                                    id="email-address"
                                    name="email"
                                    type="email"
                                    autoComplete="email"
                                    required
                                    className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                    placeholder="Адрес электронной почты"
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                />
                            </div>
                            <div>
                                <label htmlFor="password" className="sr-only">
                                    Password
                                </label>
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    autoComplete="current-password"
                                    required
                                    className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                                    placeholder="Пароль"
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="flex items-center justify-between">
                            <div className="flex items-center">
                                <input
                                    id="remember"
                                    name="remember"
                                    type="checkbox"
                                    onChange={e => setRememberMe(e.target.checked)}
                                    className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                                />
                                <label htmlFor="remember" className="ml-2 block text-sm text-gray-900">
                                    Запомните меня
                                </label>
                            </div>

                            <div className="text-sm">
                                <NavLink to={"#"} className="font-medium text-indigo-600 hover:text-indigo-500">
                                    Забыли пароль?
                                </NavLink>
                            </div>
                        </div>

                        <div>
                            <button
                                type="button"
                                onClick={click}
                                className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                            >
                                <span className="absolute left-0 inset-y-0 flex items-center pl-3">
                                <LockClosedIcon className="h-5 w-5 text-indigo-500 group-hover:text-indigo-400"
                                                aria-hidden="true"/>
                                </span>
                                Войти
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </>
    )
});

export default Login;