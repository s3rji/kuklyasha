import React, {useContext} from 'react';
import {NavLink} from "react-router-dom";
import {LOGIN_ROUTE, REGISTRATION_ROUTE} from "../utils/consts";
import {Context} from "../index";

const Identification = () => {
    const {navigation} = useContext(Context)

    return (
        <div className="hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6">
            <NavLink to={LOGIN_ROUTE}
                     onClick={() => navigation.setSelectedWay(0)}
                     className="text-gray-100 hover:bg-pink-700 hover:text-white rounded-md text-sm font-semibold font-sans">
                Войти
            </NavLink>
            <span className="h-6 w-px bg-gray-200" aria-hidden="true"/>
            <NavLink to={REGISTRATION_ROUTE}
                     onClick={() => navigation.setSelectedWay(0)}
                     className="text-gray-100 hover:bg-pink-700 hover:text-white rounded-md text-sm font-semibold font-sans">
                Зарегистрироваться
            </NavLink>
        </div>
    );
};

export default Identification;