import React from 'react';
import {NavLink} from "react-router-dom";
import {LOGIN_ROUTE, PROFILE_ROUTE} from "../utils/consts";

const Identification = () => {
    return (
        <div className="hidden lg:flex lg:flex-1 lg:items-center lg:justify-end lg:space-x-6">
            <NavLink to={LOGIN_ROUTE}
                     className="text-gray-100 hover:bg-pink-700 hover:text-white rounded-md text-sm font-semibold font-sans">
                Войти
            </NavLink>
            <span className="h-6 w-px bg-gray-200" aria-hidden="true"/>
            <NavLink to={PROFILE_ROUTE}
                     className="text-gray-100 hover:bg-pink-700 hover:text-white rounded-md text-sm font-semibold font-sans">
                Зарегистрироваться
            </NavLink>
        </div>
    );
};

export default Identification;