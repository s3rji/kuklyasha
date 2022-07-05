import React from 'react';
import {SearchIcon} from "@heroicons/react/outline";
import {NavLink} from "react-router-dom";

const SearchButton = () => {
    return (
        <div className="flex lg:ml-6">
            <NavLink to={"#"} className="p-2 text-gray-100 hover:text-pink-700">
                <span className="sr-only">Search</span>
                <SearchIcon className="w-6 h-6 " aria-hidden="true"/>
            </NavLink>
        </div>
    );
};

export default SearchButton;