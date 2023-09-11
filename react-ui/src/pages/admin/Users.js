import React, {useContext, useEffect} from 'react';
import {Context} from "../../index";
import {EyeIcon} from "@heroicons/react/outline";
import {getUsersByPage, updateUser} from "../../http/userAPI";
import {observer} from "mobx-react-lite";
import {runInAction} from "mobx";
import {UsersPages} from "../../components/admin/index";
import {ADMIN_USERS_ROUTE} from "../../utils/consts";
import {useNavigate} from "react-router-dom";

const Users = observer(() => {
    const {usersList} = useContext(Context)
    const navigate = useNavigate()


    useEffect(() => {
        getUsersByPage(usersList.page, usersList.limit).then(data => {
            usersList.setAllUsers(data.content)
            usersList.setTotal(data.total)
        })
    }, [usersList, usersList.page])

    const onUserChange = (user, enabled) => {
        runInAction(() => user.enabled = enabled)
        updateUser(user.id, {"id": user.id, "enabled": enabled}).then()
    }

    return (
        <div className="mt-5 md:col-span-2 md:mt-10 px-8">
            <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead
                        className="text-xs text-gray-700 uppercase bg-gray-200 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="py-3 px-6">
                            ID
                        </th>
                        <th scope="col" className="py-3 px-6">
                            ФИО
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Имейл
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Телефон
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Заблокирован
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Просмотр
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {usersList.allUsers.map(user =>
                        <tr key={user.id}
                            className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <th scope="row"
                                className="py-4 px-6 font-medium text-zinc-700 whitespace-nowrap dark:text-white">
                                {user.id}
                            </th>
                            <th scope="row"
                                className="py-4 px-6 font-medium text-sky-500 whitespace-nowrap dark:text-white">
                                {user.name + ' ' + user.lastname}
                            </th>
                            <th scope="row"
                                className="py-4 px-6 font-medium text-sky-500 whitespace-nowrap dark:text-white">
                                {user.email}
                            </th>
                            <th scope="row"
                                className="py-4 px-6 font-medium text-sky-500 whitespace-nowrap dark:text-white">
                                {user.phone}
                            </th>
                            <td className="px-16">
                                <input
                                    id="disabled"
                                    name="disabled"
                                    type="checkbox"
                                    checked={!user.enabled}
                                    onChange={(e) => onUserChange(user, !e.target.checked)}
                                    className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                                />
                            </td>
                            <td className="py-4 px-6 text-zinc-700 indent-5">
                                <button type="button" onClick={() => navigate(ADMIN_USERS_ROUTE + "/" + user.id)}
                                        className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                    <EyeIcon className="h-3 w-3" aria-hidden="true"/>
                                    <span className="sr-only">Просмотр</span>
                                </button>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
            <UsersPages/>
        </div>
    )
})

export default Users;