import React from 'react';
import {MAIN_ROUTE} from "../utils/consts";
import logo from "../assets/main.jpeg";
import {NavLink} from "react-router-dom";

const Logo = ({setNavigation}) => {

    return (
        <NavLink to={MAIN_ROUTE}>
            <div className="flex-shrink-0 flex items-center">
                <img
                    className="hidden lg:block h-8 w-auto"
                    src={logo}
                    alt="Kuklyasha"
                    onClick={setNavigation}
                />
            </div>
        </NavLink>
    );
};

export default Logo;