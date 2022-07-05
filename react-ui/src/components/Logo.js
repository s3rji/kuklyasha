import React from 'react';
import {CATALOG_ROUTE} from "../utils/consts";
import logo from "../assets/main.jpeg";
import {NavLink} from "react-router-dom";

const Logo = () => {
    return (
        <NavLink to={CATALOG_ROUTE}>
            <div className="flex-shrink-0 flex items-center">
                <img
                    className="hidden lg:block h-8 w-auto"
                    src={logo}
                    alt="Kuklyasha"
                />
            </div>
        </NavLink>
    );
};

export default Logo;