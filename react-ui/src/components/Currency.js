import React from 'react';
import flag from "../assets/flag.png";
import {NavLink} from "react-router-dom";

const Currency = () => {
    return (
        <div className="border-t border-gray-200 py-6 px-4">
            <NavLink to={"#"} className="-m-2 p-2 flex items-center">
                <img
                    src={flag}
                    alt=""
                    className="w-5 h-auto block flex-shrink-0"
                />
                <span className="ml-3 block text-sm font-semibold text-gray-100">RUB</span>
                <span className="sr-only">, change currency</span>
            </NavLink>
        </div>
    );
};

export default Currency;