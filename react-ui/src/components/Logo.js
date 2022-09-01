import React, {useContext} from 'react';
import {MAIN_ROUTE} from "../utils/consts";
import logo from "../assets/main.jpeg";
import {NavLink} from "react-router-dom";
import {Context} from "../index";

const Logo = () => {
    const {navigation} = useContext(Context)

    return (
        <NavLink to={MAIN_ROUTE}>
            <div className="flex-shrink-0 flex items-center">
                <img
                    className="hidden lg:block h-8 w-auto"
                    src={logo}
                    alt="Kuklyasha"
                    onClick={() => navigation.setSelectedWay(0)}
                />
            </div>
        </NavLink>
    );
};

export default Logo;