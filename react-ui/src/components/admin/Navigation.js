import React, {useContext} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {NavLink} from "react-router-dom";

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const Navigation = observer(() => {
    const {adminNavigation} = useContext(Context)

    return (
        <div className="hidden sm:block sm:ml-6">
            <div className="flex space-x-4">
                {adminNavigation.ways.map((item) => (
                    <NavLink
                        key={item.name}
                        to={item.href}
                        className={classNames(
                            item.id === adminNavigation.selectedWay ? 'bg-pink-900 text-white' : 'text-gray-100 hover:bg-pink-700 hover:text-white',
                            'px-3 py-2 rounded-md text-sm font-semibold font-sans'
                        )}
                        aria-current={item.id === adminNavigation.selectedWay ? 'page' : undefined}
                        onClick={() => adminNavigation.setSelectedWay(item.id)}
                    >
                        {item.name}
                    </NavLink>
                ))}
            </div>
        </div>
    );
});

export default Navigation;

