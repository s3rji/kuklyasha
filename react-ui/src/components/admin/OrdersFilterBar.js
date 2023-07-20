import React, {Fragment, useContext, useState} from 'react';
import {ChevronDownIcon} from "@heroicons/react/solid";
import {Menu, Transition} from "@headlessui/react";
import {classNames} from "../../utils/functions";
import {NavLink} from "react-router-dom";
import {Context} from "../../index";
import {getLimitWithUserByFilter} from "../../http/orderApi";

const sortOptions = [
    {name: 'Номер заказа', type: 'id', current: true},
    {name: 'Пользователь', type: 'user.name', current: false},
    {name: 'Статус заказа', type: 'status', current: false},
]

const filters = [
    {name: 'Номер заказа', type: 'id', current: true},
    {name: 'Пользователь', type: 'user.name', current: false},
    {name: 'Статус заказа', type: 'status', current: false},
]

const orderStatuses = [
    {name: 'Новый', type: 'NEW'},
    {name: 'Подтвержден', type: 'CONFIRMED'},
    {name: 'Доставка', type: 'DELIVERY'},
    {name: 'Выполнен', type: 'DONE'},
]


const OrdersFilterBar = () => {
    const {order} = useContext(Context)
    const [activeSort, setActiveSort] = useState(0)
    const [activeFilter, setActiveFilter] = useState(0)
    const [search, setSearch] = useState('')

    const chooseSort = (option, index) => {
        sortOptions[activeSort].current = false
        setActiveSort(index)
        option.current = true
        order.setPage(0)
        setSearch('')
        order.setSearch('')
        order.setSort(option.type)
    }

    const chooseFilter = (filter, index) => {
        filters[activeFilter].current = false
        setActiveFilter(index)
        filter.current = true
        setSearch('')
        order.setPage(0)
        order.setSearch('')
        order.setFilter(filter.type)
    }

    const applyFilter = (event) => {
        if (event.key === 'Enter') {
            order.setSearch(search)
            order.setPage(0)
            getLimitWithUserByFilter(order.page, order.limit, order.sort, order.sortDirection, order.filter, order.search)
                .then(data => {
                    order.setOrders(data.content)
                    order.setTotal(data.total)
                })
        }
    }

    const applyStatusFilter = (statusType) => {
        order.setSearch(statusType)
        order.setPage(0)
        getLimitWithUserByFilter(order.page, order.limit, order.sort, order.sortDirection, order.filter, order.search)
            .then(data => {
                order.setOrders(data.content)
                order.setTotal(data.total)
            })
    }

    const renderFilterInput = () => {
        if (activeFilter === 2) {
            return <Menu as="div" className="relative inline-block text-left ">
                <div>
                    <Menu.Button
                        className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                        Выберите статус
                        <ChevronDownIcon className="-mr-1 h-5 w-5 text-gray-400" aria-hidden="true"/>
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
                        className="absolute left-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                        <div className="py-1">
                            {orderStatuses.map((status) => (
                                    <Menu.Item key={status.type}>
                                        {({active}) => (
                                            <NavLink
                                                to="#"
                                                className={classNames('text-gray-500',
                                                    active ? 'bg-gray-100' : '',
                                                    'block px-4 py-2 text-sm'
                                                )}
                                                onClick={() => applyStatusFilter(status.type)}
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
        } else {
            return <div
                className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 w-64">
                <input
                    type="text"
                    name="filter"
                    id="filter"
                    autoComplete="filter"
                    placeholder={'введите значение и нажмите Enter'}
                    value={search}
                    onChange={e => setSearch(e.target.value)}
                    onKeyDown={e => applyFilter(e)}
                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 focus:outline-none sm:text-sm sm:leading-6"
                />
            </div>
        }
    }


    return (
        <div className="flex justify-between py-2">
            <div className="flex gap-x-1">
                <Menu as="div" className="relative inline-block text-left ">
                    <div>
                        <Menu.Button
                            className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                            Фильтровать
                            <ChevronDownIcon className="-mr-1 h-5 w-5 text-gray-400" aria-hidden="true"/>
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
                            className="absolute left-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                            <div className="py-1">
                                {filters.map((filter, index) => (
                                        <Menu.Item key={filter.name}>
                                            {({active}) => (
                                                <NavLink
                                                    to="#"
                                                    className={classNames(
                                                        filter.current ? 'font-medium text-gray-900' : 'text-gray-500',
                                                        active ? 'bg-gray-100' : '',
                                                        'block px-4 py-2 text-sm'
                                                    )}
                                                    onClick={() => chooseFilter(filter, index)}
                                                >
                                                    {filter.name}
                                                </NavLink>
                                            )}
                                        </Menu.Item>
                                    )
                                )}
                            </div>
                        </Menu.Items>
                    </Transition>
                </Menu>
                {renderFilterInput()}
            </div>
            <Menu as="div" className="relative inline-block text-left ">
                <div>
                    <Menu.Button
                        className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                        Сортировать
                        <ChevronDownIcon className="-mr-1 h-5 w-5 text-gray-400" aria-hidden="true"/>
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
                            {sortOptions.map((option, index) => (
                                    <Menu.Item key={option.name}>
                                        {({active}) => (
                                            <NavLink
                                                to="#"
                                                className={classNames(
                                                    option.current ? 'font-medium text-gray-900' : 'text-gray-500',
                                                    active ? 'bg-gray-100' : '',
                                                    'block px-4 py-2 text-sm'
                                                )}
                                                onClick={() => chooseSort(option, index)}
                                            >
                                                {option.name}
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
    );
};

export default OrdersFilterBar;