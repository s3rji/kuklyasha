import React, {Fragment, useContext} from 'react';
import {Menu, Transition} from "@headlessui/react";
import {NavLink} from "react-router-dom";
import {Context} from "../../index";
import {observer} from "mobx-react-lite";
import {CATALOG_ROUTE} from "../../utils/consts";

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const AdminDropdown = observer(() => {
    const {user} = useContext(Context)

    const logOut = () => {
        user.logOut()
        localStorage.setItem("token", null)
    }

    return (
        <Menu as="div" className="ml-3 relative z-10">
            <div>
                <Menu.Button
                    className="bg-gray-800 flex text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white">
                    <img
                        className="h-8 w-8 rounded-full"
                        src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                        alt=""
                    />
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
                    className="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <Menu.Item>
                        {({active}) => (
                            <NavLink
                                to={CATALOG_ROUTE}
                                onClick={() => user.setActiveRole(user.roles[1])}
                                className={classNames(active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700')}
                            >
                                Зайти как пользователь
                            </NavLink>
                        )}
                    </Menu.Item>
                    <Menu.Item>
                        {({active}) => (
                            <NavLink
                                to={CATALOG_ROUTE}
                                onClick={() => logOut()}
                                className={classNames(active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700')}
                            >
                                Выйти
                            </NavLink>
                        )}
                    </Menu.Item>
                </Menu.Items>
            </Transition>
        </Menu>
    );
});

export default AdminDropdown;