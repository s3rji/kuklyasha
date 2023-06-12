import React, {useContext, useEffect, useState} from 'react'
import {Context} from "../index";
import {getCart, removeAllCartItems, removeCartItem, updateCartItem} from "../http/cartApi";
import Moment from "moment";
import {observer} from "mobx-react-lite";
import {DOLL_ROUTE} from "../utils/consts";
import {NavLink, useNavigate} from "react-router-dom";
import {createOrder} from "../http/orderApi";
import {MinusSmIcon, PlusSmIcon} from "@heroicons/react/solid";
import {InfoModal} from "../components/index";
import {classNames} from "../utils/functions";


const Cart = observer(() => {
    const {cart, order} = useContext(Context)
    const [isShowModal, setIsShowModal] = useState(false)
    const [orderId, setOrderId] = useState('')
    const navigate = useNavigate()

    useEffect(() => {
        getCart().then(data => {
            cart.setCart(data)
        })
    }, [])

    Moment.locale('ru')
    const delivery = Moment(Date.now() + 345600000).format('DD.MM.yyyy') + ' - ' + Moment(Date.now() + 604800000).format('DD.MM.yyyy')
    const total = cart.cart.reduce((a, v) => a + v.quantity * v.doll.price, 0)

    const removeItem = async (id, index) => {
        try {
            await removeCartItem(id)
            cart.removeCartItem(index)
        } catch (e) {
            alert(e)
        }
    }

    const updateItem = async (index, value) => {
        try {
            cart.updateCartItemQuantity(index, value)
            await updateCartItem(cart.cart[index].id, {"id": cart.cart[index].id, "quantity": value})
        } catch (e) {
            alert(e)
        }
    }

    const addOrder = async () => {
        const purchasedDolls = cart.cart.map(item => ({"id": item.doll.id, "quantity": item.quantity}));
        let created = await createOrder(purchasedDolls)
        order.orders.push(created)
        await removeAllCartItems()
        cart.setCart([])
        setOrderId(created.id)
        setIsShowModal(true)
    }

    return (
        <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
            {cart.cart.length === 0 ?
                <div className="mt-1 text-base text-gray-900">Корзина пуста. Добавьте товары в корзину и оформите
                    заказ</div>
                :
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                            <div className="mt-4 mb-6">
                                <div className="flow-root">
                                    <div className="-my-6 divide-y divide-gray-200 mx-20">
                                        {cart.cart.map((product, index) => (
                                            <li key={product.id} className="flex py-6">
                                                <div
                                                    className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                                    <img
                                                        src={process.env.REACT_APP_IMAGES_URL + product.doll.poster}
                                                        alt={product.doll.description}
                                                        className="h-full w-full object-cover object-center"
                                                        onClick={() => navigate(DOLL_ROUTE + "/" + product.doll.id)}
                                                    />
                                                </div>

                                                <div className="ml-4 flex flex-1 flex-col">
                                                    <div>
                                                        <div
                                                            className="flex justify-between text-base font-medium text-gray-900">
                                                            <h3>
                                                                <NavLink
                                                                    to={DOLL_ROUTE + "/" + product.doll.id}>{product.doll.name}</NavLink>
                                                            </h3>
                                                            <p className="ml-4">{product.doll.price} руб.</p>
                                                        </div>
                                                        <p className="mt-1 text-sm text-gray-500">{product.doll.description}</p>
                                                    </div>


                                                    <div
                                                        className="flex mt-2 flex-1 items-end justify-between text-base">
                                                        <div className="flex space-x-2 text-gray-900 font-medium ">
                                                            <button
                                                                onClick={() => updateItem(index, product.quantity - 1)}
                                                                className="border border-transparent bg-gray-200 rounded-full"
                                                                disabled={product.quantity === 1}>

                                                                <MinusSmIcon className={classNames(
                                                                    product.quantity === 1 ? "text-gray-400" : "text-gray-700",
                                                                    "h-5 w-5"
                                                                )}></MinusSmIcon>
                                                            </button>
                                                            <p className="text-gray-900 font-medium">{product.quantity}</p>
                                                            <button
                                                                onClick={() => updateItem(index, product.quantity + 1)}
                                                                className=" border border-transparent bg-gray-200 rounded-full"
                                                                disabled={product.quantity >= product.doll.quantity}>
                                                                <PlusSmIcon className={classNames(
                                                                    product.quantity < product.doll.quantity ? "text-gray-700" : "text-gray-400",
                                                                    "h-5 w-5"
                                                                )}></PlusSmIcon>
                                                            </button>
                                                        </div>

                                                        <div className="flex">
                                                            <button
                                                                type="button"
                                                                onClick={() => removeItem(product.id, index)}
                                                                className="font-medium text-indigo-600 hover:text-indigo-500"
                                                            >
                                                                Удалить
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="md:col-span-1">
                        <div className="border-t border-gray-200 py-6 px-4 sm:px-6">
                            <div className="flex justify-between text-base font-medium text-gray-900">
                                <p>Доставка:</p>
                                <p>{delivery}</p>
                            </div>
                            <p className="mt-0.5 text-sm text-gray-500">Указана приблизительная дата доставки</p>
                        </div>
                        <div className="border-t border-gray-200 py-6 px-4 sm:px-6">
                            <div className="flex justify-between text-base font-medium text-gray-900">
                                <p>Итого:</p>
                                <p>{total} руб.</p>
                            </div>
                            <p className="mt-0.5 text-sm text-gray-500">Стоимость указана с учетом доставки и
                                налогов</p>
                            <div className="mt-6">
                                <button
                                    onClick={addOrder}
                                    className="flex mx-auto items-center justify-center rounded-md border border-transparent bg-indigo-600 px-12 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                                >
                                    Заказать
                                </button>
                            </div>
                        </div>
                    </div>

                </div>
            }
            {<InfoModal orderId={orderId} show={isShowModal}
                        onClose={() => setIsShowModal(false)}></InfoModal>}
        </div>
    )
});

export default Cart;