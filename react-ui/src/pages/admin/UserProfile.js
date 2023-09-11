import React, {useEffect, useState} from "react";
import {Checkbox, InputField, InputPhone} from "../../components/index";
import {observer} from "mobx-react-lite";
import {useParams} from "react-router-dom";
import {getUserById, getUserOrders} from "../../http/userAPI";
import {status, statusAsColor} from "../../utils/functions";
import {EditOrder} from "../../components/admin";
import {getOrder} from "../../http/orderApi";

const Profile = observer(() => {
    const {id} = useParams()
    const [user, setUser] = useState({
        name: '',
        lastname: '',
        email: '',
        phone: '',
        country: '',
        street: '',
        city: '',
        region: '',
        zipcode: '',
        emailNotice: false,
        phoneNotice: false,
    })
    const [orders, setOrders] = useState([])
    const [activeOrder, setActiveOrder] = useState(null)
    const [isShowModal, setIsShowModal] = useState(false)


    useEffect(() => {
        getUserById(id).then(data => setUser(data))
        getUserOrders(id).then(data => setOrders(data))
    }, [id])

    const showOrderInfo = (id) => {
        getOrder(id).then(data => setActiveOrder(data))
        setIsShowModal(true)
    }

    return (
        <>
            <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="md:col-span-1">
                        <div className="px-4 sm:px-0">
                            <h3 className="text-lg font-medium leading-6 text-gray-900">Личные данные</h3>
                        </div>
                    </div>
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <div className="overflow-visible shadow-xl shadow-gray-500/50 sm:rounded-md ">
                            <div className="bg-white px-4 py-5 sm:p-6">
                                <div className="grid grid-cols-6 gap-6">
                                    <div className="col-span-6 sm:col-span-3">
                                        <InputField type="text" name="first-name" label="Имя" value={user.name}
                                                    readOnly={true} autoComplete="given-name"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-3">
                                        <InputField type="text" name="last-name" label="Фамилия"
                                                    value={user.lastname}
                                                    readOnly={true} autoComplete="family-name"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-3">
                                        <InputField type="email" name="email-address" label="Имейл"
                                                    value={user.email}
                                                    readOnly={true} autoComplete="email"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-3">
                                        <InputPhone name="phone" label="Телефон" value={user.phone}
                                                    readOnly={true}
                                        />

                                    </div>
                                    <div className="col-span-6 sm:col-span-2">
                                        <InputField type="text" name="country" label="Страна"
                                                    value={user.country}
                                                    readOnly="true" autoComplete="country-name"
                                        />
                                    </div>
                                    <div className="col-span-6">
                                        <InputField type="text" name="street-address" label="Адрес"
                                                    value={user.street}
                                                    readOnly={true} autoComplete="street-address"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-6 lg:col-span-2">
                                        <InputField type="text" name="city" label="Город" value={user.city}
                                                    readOnly={true} autoComplete="address-level2"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                        <InputField type="text" name="region" label="Область" value={user.region}
                                                    readOnly={true} autoComplete="address-level1"
                                        />
                                    </div>
                                    <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                        <InputField type="text" name="postal-code" label="Почтовый индекс"
                                                    value={user.zipcode}
                                                    readOnly={true} autoComplete="postal-code"
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="hidden sm:block" aria-hidden="true">
                <div className="py-5">
                    <div className="border-t border-gray-200 mx-auto max-w-7xl"/>
                </div>
            </div>
            <div className="mt-10 sm:mt-0 py-6 mx-auto max-w-7xl">
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="md:col-span-1">
                        <div className="px-4 sm:px-0">
                            <h3 className="text-lg font-medium leading-6 text-gray-900">Уведомления</h3>
                        </div>
                    </div>
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                            <div className="space-y-6 bg-white px-4 py-5 sm:p-6">
                                <fieldset>
                                    <div className="mt-4 space-y-4">
                                        <Checkbox name="email" label="Имейл" value={user.emailNotice}
                                                  readOnly={true}/>
                                        <Checkbox name="phone" label="Телефон" value={user.phoneNotice}
                                                  readOnly={true}/>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="hidden sm:block" aria-hidden="true">
                <div className="py-5">
                    <div className="border-t border-gray-200 mx-auto max-w-7xl"/>
                </div>
            </div>
            <div className="mt-10 sm:mt-0 py-6 mx-auto max-w-7xl">
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="md:col-span-1">
                        <div className="px-4 sm:px-0">
                            <h3 className="text-lg font-medium leading-6 text-gray-900">Заказы</h3>
                        </div>
                    </div>
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                            <div className="space-y-6 bg-white px-4 py-5 sm:p-6">
                                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                                    <thead
                                        className="text-xs text-gray-700 uppercase bg-gray-200 dark:bg-gray-700 dark:text-gray-400">
                                    <tr>
                                        <th scope="col" className="py-3 px-6">
                                            Номер заказа
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Статус
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Стоимость
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Дата доставки
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Дата создания
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {orders.map(order =>
                                        <tr key={order.id}
                                            className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 cursor-pointer hover:bg-blue-200"
                                            onClick={() => showOrderInfo(order.id)}>
                                            <th scope="row"
                                                className="py-4 px-6 font-medium text-zinc-700 whitespace-nowrap dark:text-white">
                                                {order.id}
                                            </th>
                                            <td className="py-4 px-6 text-zinc-700  font-medium">
                                                <div className="flex items-center">
                                                    <div
                                                        className={"h-2.5 w-2.5 rounded-full mr-2 " + statusAsColor(order.status.type)}></div>
                                                    {status(order.status.type)}
                                                </div>
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700">
                                                {order.total + " руб."}
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700">
                                                {order.deliveryDate}
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700">
                                                {order.created}
                                            </td>
                                        </tr>
                                    )}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {activeOrder && <EditOrder order={activeOrder} show={isShowModal}
                                       onClose={() => setIsShowModal(false)}></EditOrder>}
        </>
    );
});

export default Profile;