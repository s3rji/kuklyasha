import React, {Fragment, useEffect, useState} from 'react'
import {Dialog, Menu, Transition} from '@headlessui/react'
import status, {classNames} from "../../utils/functions"
import {ChevronDownIcon} from "@heroicons/react/solid";
import {NavLink} from "react-router-dom";
import Moment from "moment";
import {updateOrder} from "../../http/orderApi";
import {runInAction} from "mobx";

const orderStatuses = [
    {name: 'Новый', type: 'NEW'},
    {name: 'Подтвержден', type: 'CONFIRMED'},
    {name: 'Доставка', type: 'DELIVERY'},
    {name: 'Выполнен', type: 'DONE'},
]

const EditOrder = ({show, onClose, order}) => {
    const [activeStatus, setActiveStatus] = useState(order.status.type)
    const [deliveryDate, setDeliveryDate] = useState(Moment(order.deliveryDate, "DD.MM.yyyy").format("yyyy-MM-DD"))
    const address = order.user.zipcode + ", " + order.user.country + ", " + order.user.region + ", " + order.user.city +
        ", " + order.user.street

    useEffect(() => {
        setActiveStatus(order.status.type)
        setDeliveryDate(Moment(order.deliveryDate, "DD.MM.yyyy").format("yyyy-MM-DD"))
    }, [show, order])

    const saveChanges = () => {
        const newDeliveryDate = Moment(deliveryDate, "yyyy-MM-DD").format("DD.MM.yyyy")
        if (activeStatus !== order.status.type || newDeliveryDate !== order.deliveryDate) {
            runInAction(() => {
                order.status.type = activeStatus
                order.deliveryDate = newDeliveryDate
            })
            const orderChange = {
                "id": order.id,
                "type": activeStatus,
                "deliveryDate": newDeliveryDate
            }
            updateOrder(order.id, orderChange).then(() => onClose())
        }
        onClose();
    }

    return (
        <Transition appear show={show} as={Fragment}>
            <Dialog as="div" className="relative z-10" onClose={onClose}>
                <Transition.Child
                    as={Fragment}
                    enter="ease-out duration-300"
                    enterFrom="opacity-0"
                    enterTo="opacity-100"
                    leave="ease-in duration-200"
                    leaveFrom="opacity-100"
                    leaveTo="opacity-0"
                >
                    <div className="fixed inset-0 bg-black bg-opacity-25"/>
                </Transition.Child>

                <div className="fixed inset-0 overflow-y-auto">
                    <div className="flex min-h-full items-center justify-center p-4 text-center">
                        <Transition.Child
                            as={Fragment}
                            enter="ease-out duration-300"
                            enterFrom="opacity-0 scale-95"
                            enterTo="opacity-100 scale-100"
                            leave="ease-in duration-200"
                            leaveFrom="opacity-100 scale-100"
                            leaveTo="opacity-0 scale-95"
                        >
                            <Dialog.Panel
                                className="w-full max-w-3xl transform overflow-hidden rounded-2xl bg-white p-10 text-left align-middle shadow-xl transition-all">
                                <div className="flex items-start justify-between">
                                    <Dialog.Title
                                        as="h3"
                                        className="text-base font-medium leading-6 text-indigo-700 py-1"
                                    >
                                        {"Заказ № " + order.id}
                                    </Dialog.Title>
                                    <Menu as="div" className="relative flex inline-block text-left ">
                                        <div>
                                            <Menu.Button
                                                className="inline-flex w-full justify-center gap-x-2 rounded-md bg-white py-1 px-3 text-sm font-semibold text-indigo-700 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                                                {status(activeStatus)}
                                                <ChevronDownIcon className="-mr-1 h-5 w-5 text-gray-400"
                                                                 aria-hidden="true"/>
                                            </Menu.Button>
                                        </div>
                                        <Transition
                                            as={Fragment}
                                            enter="transition ease-out duration-100"
                                            enterFrom="transform opacity-0 scale-95"
                                            enterTo="transform opacity-100 scale-100"
                                            leave="transition ease-in duration-75"
                                            leaveFrom="transform opacity-100 scale-100"
                                            leaveTo="transform opacity-0 scale-95"
                                        >
                                            <Menu.Items
                                                className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                                                <div className="py-1">
                                                    {orderStatuses.map((status) => (
                                                            <Menu.Item key={status.type}>
                                                                {({active}) => (
                                                                    <NavLink
                                                                        to="#"
                                                                        className={classNames(status.type === activeStatus ? 'font-medium text-gray-900' : 'text-gray-500',
                                                                            active ? 'bg-gray-100' : '',
                                                                            'block px-4 py-2 text-sm'
                                                                        )}
                                                                        onClick={() => setActiveStatus(status.type)}
                                                                    >
                                                                        {status.name}
                                                                    </NavLink>
                                                                )}
                                                            </Menu.Item>
                                                        )
                                                    )}
                                                </div>
                                            </Menu.Items>
                                        </Transition>
                                    </Menu>
                                </div>

                                <div className="mt-6">
                                    <div className="flow-root">
                                        <div className="-my-6 divide-y divide-gray-200">
                                            {order.items.map((product) => (
                                                <li key={product.id} className="flex py-6">
                                                    <div
                                                        className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                                        <img
                                                            src={process.env.REACT_APP_IMAGES_URL + product.doll.poster}
                                                            alt={product.doll.description}
                                                            className="h-full w-full object-cover object-center"
                                                        />
                                                    </div>

                                                    <div className="ml-4 flex flex-1 flex-col">
                                                        <div>
                                                            <div
                                                                className="flex justify-between text-base font-medium text-gray-900">
                                                                <h3>
                                                                    {product.doll.name}
                                                                </h3>
                                                                <p className="ml-4">{product.price + " руб."}</p>
                                                            </div>
                                                            <p className="mt-1 text-sm text-gray-500">{product.doll.description.substring(0, 100)}</p>
                                                        </div>
                                                        <div
                                                            className="flex py-2 flex-1 items-end justify-between text-sm">
                                                            <p className="text-gray-900">{"Кол-во: " + product.quantity}</p>
                                                        </div>
                                                    </div>
                                                </li>
                                            ))}
                                        </div>
                                    </div>
                                </div>

                                <div className="border-t border-gray-200 mt-6 py-2 text-gray-900">
                                    <div className="flex text-base font-normal">
                                        Пользователь:
                                        <p className="px-1 text-sky-500">{order.user.name}</p>
                                    </div>
                                    <div className="flex justify-between text-base font-normal">
                                        <div className="flex">
                                            Имейл:
                                            <p className="px-1 text-sky-500">{order.user.email}</p>
                                        </div>
                                        <div className="flex">
                                            Телефон:
                                            <p className="px-1 text-sky-500">{"+" + order.user.phone}</p>
                                        </div>
                                    </div>
                                    <div className="flex text-base">
                                        <p>Адрес доставки:</p>
                                        <p className="px-1 text-sky-500">{address}</p>
                                    </div>
                                </div>

                                <div className="border-t border-gray-200 mt-4 py-2">
                                    <div className="flex justify-between text-base font-medium text-gray-900">
                                        <p>Итого</p>
                                        <p>{order.total + " руб."}</p>
                                    </div>
                                    <p className="mt-0.5 text-sm text-gray-500">Стоимость указана с учетом доставки и
                                        налогов</p>
                                    <div className="flex mt-2 justify-between text-base font-medium text-gray-900">
                                        <p>Дата доставка</p>
                                        <input type="date" id="delivery-date" value={deliveryDate}
                                               onChange={e => setDeliveryDate(e.target.value)}
                                               min={Moment(Date.now()).format("yyyy-MM-DD")}
                                               className="rounded-md bg-gray ring-1 ring-black ring-opacity-30 text-indigo-700"/>
                                    </div>

                                    <div className="mt-6 flex items-center justify-end gap-x-6">
                                        <button type="button" onClick={onClose}
                                                className="text-sm font-semibold leading-6 text-gray-900">
                                            Отмена
                                        </button>
                                        <button
                                            type="button"
                                            onClick={saveChanges}
                                            className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                                        >
                                            Сохранить
                                        </button>
                                    </div>
                                </div>


                            </Dialog.Panel>
                        </Transition.Child>
                    </div>
                </div>
            </Dialog>
        </Transition>
    )
}

export default EditOrder