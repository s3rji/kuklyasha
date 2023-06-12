import React, {useContext} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../index";
import {NavLink} from "react-router-dom";
import {classNames} from "../utils/functions";


const Navigation = observer(() => {
    const {navigation} = useContext(Context)

    return (
        <div className="hidden sm:block sm:ml-6">
            <div className="flex space-x-4">
                {navigation.ways.map((item) => (
                    <NavLink
                        key={item.name}
                        to={item.href}
                        className={classNames(
                            item.id === navigation.selectedWay ? 'bg-pink-900 text-white' : 'text-gray-100 hover:bg-pink-700 hover:text-white',
                            'px-3 py-2 rounded-md text-sm font-semibold font-sans'
                        )}
                        aria-current={item.id === navigation.selectedWay ? 'page' : undefined}
                        onClick={() => navigation.setSelectedWay(item.id)}
                    >
                        {item.name}
                    </NavLink>
                ))}
            </div>
        </div>
    );
});

export default Navigation;

