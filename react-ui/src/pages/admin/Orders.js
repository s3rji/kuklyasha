import React, {useContext, useEffect, useState} from 'react';
import {status, statusAsColor} from "../../utils/functions";
import {EyeIcon} from "@heroicons/react/outline";
import {getLimitWithUserAndSort} from "../../http/orderApi";
import {Context} from "../../index";
import {observer} from "mobx-react-lite";
import {OrdersFilterBar, OrdersPages, EditOrder} from "../../components/admin/index";

const Orders = observer(() => {
    const {order} = useContext(Context)
    const [isShowModal, setIsShowModal] = useState(false)
    const [activeOrder, setActiveOrder] = useState(null)

    useEffect(() => {
        getLimitWithUserAndSort(order.page, order.limit, order.sort, order.sortDirection).then(data => {
            order.setOrders(data.content)
            order.setTotal(data.total)
        })
    }, [order, order.sort, order.filter])

    const showOrder = (order) => {
        setActiveOrder(order)
        setIsShowModal(true)
    }

    return (
        <div className="mt-5 md:col-span-2 md:mt-10 px-8">
            <OrdersFilterBar/>

            <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead
                        className="text-xs text-gray-700 uppercase bg-gray-200 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="py-3 px-6">
                            Номер заказа
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Пользователь
                        </th>
                        <th scope="col" className="py-3 px-6">
                            Имейл пользователя
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
                        <th scope="col" className="py-3 px-6">
                            Просмотр
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {order.orders.map(order =>
                        <tr key={order.id}
                            className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <th scope="row"
                                className="py-4 px-6 font-medium text-zinc-700 whitespace-nowrap dark:text-white">
                                {order.id}
                            </th>
                            <th scope="row"
                                className="py-4 px-6 font-medium text-sky-500 whitespace-nowrap dark:text-white">
                                {order.user.name}
                            </th>
                            <th scope="row"
                                className="py-4 px-6 font-medium text-sky-500 whitespace-nowrap dark:text-white">
                                {order.user.email}
                            </th>
                            <td className="py-4 px-6 text-zinc-700">
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
                            <td className="py-4 px-6 text-zinc-700 indent-5">
                                <button type="button" onClick={() => showOrder(order)}
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
            <OrdersPages/>
            {activeOrder && <EditOrder order={activeOrder} show={isShowModal}
                                       onClose={() => setIsShowModal(false)}></EditOrder>}
        </div>

    )
})

export default Orders;