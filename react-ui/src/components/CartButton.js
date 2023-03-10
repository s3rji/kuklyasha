import React, {useContext} from 'react';
import {ShoppingBagIcon} from "@heroicons/react/outline";
import {NavLink} from "react-router-dom";
import {CART_ROUTE} from "../utils/consts";
import {Context} from "../index";
import {observer} from "mobx-react-lite";

const CartButton = observer(() => {
    const {cart, navigation} = useContext(Context)

    return (
        <div className="ml-4 flow-root lg:ml-6">
            <NavLink
                to={CART_ROUTE}
                className="group -m-2 p-2 flex items-center"
                onClick={() => navigation.setSelectedWay(0)}
            >

                <ShoppingBagIcon className="flex-shrink-0 h-6 w-6 text-gray-100 group-hover:text-pink-700"
                                 aria-hidden="true"/>
                <span
                    className="ml-2 text-sm font-semibold text-gray-100 group-hover:text-pink-700">{cart.cart.length}</span>
                <span className="sr-only">items in cart, view bag</span>
            </NavLink>
        </div>
    );
});

export default CartButton;