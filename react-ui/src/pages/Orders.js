import React, {useContext, useEffect, useState} from "react";
import {EyeIcon} from "@heroicons/react/outline";
import {ShowOrder} from "../components/index";
import {status, statusAsColor} from "../utils/functions"
import {Context} from "../index";
import {getOrders} from "../http/orderApi";
import {observer} from "mobx-react-lite";


const Orders = observer(() => {
    const [isShowModal, setIsShowModal] = useState(false)
    const [activeOrder, setActiveOrder] = useState(null)
    const {order} = useContext(Context)

    useEffect(() => {
        getOrders().then(data => {
            order.setOrders(data)
        })
    }, [])

    const showOrder = (order) => {
        setActiveOrder(order)
        setIsShowModal(true)
    }

    return (
        <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
            <div className="md:grid md:grid-cols-3 md:gap-6">
                <div className="md:col-span-1">
                    <div className="px-4 sm:px-0">
                        <h3 className="text-lg font-medium leading-6 text-gray-900">Заказы</h3>
                        <p className="mt-1 text-sm text-gray-600">Здесь вы можете отслеживать статус заказа</p>
                    </div>
                </div>
                {order.orders.length === 0 ?
                    <p className="mt-1 text-sm text-gray-600">Вы пока не сделали ни одного заказа</p>
                    :
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                            <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                                <thead
                                    className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
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
                    </div>
                }
                {activeOrder && <ShowOrder order={activeOrder} show={isShowModal}
                                           onClose={() => setIsShowModal(false)}></ShowOrder>}
            </div>
        </div>
    )
})

export default Orders