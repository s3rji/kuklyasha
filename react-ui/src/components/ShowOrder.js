import {Dialog, Transition} from '@headlessui/react'
import {Fragment} from 'react'
import status from "../utils/functions"

const ShowOrder = ({show, onClose, order}) => {

    return (
        <Transition appear show={show} as={Fragment}>
            <Dialog as="div" className="relative z-10" onClose={onClose}>
                <Transition.Child
                    as={Fragment}
                    enter="ease-out duration-300"
                    enterFrom="opacity-0"
                    enterTo="opacity-100"
                    leave="ease-in duration-200"
                    leaveFrom="opacity-100"
                    leaveTo="opacity-0"
                >
                    <div className="fixed inset-0 bg-black bg-opacity-25"/>
                </Transition.Child>

                <div className="fixed inset-0 overflow-y-auto">
                    <div className="flex min-h-full items-center justify-center p-4 text-center">
                        <Transition.Child
                            as={Fragment}
                            enter="ease-out duration-300"
                            enterFrom="opacity-0 scale-95"
                            enterTo="opacity-100 scale-100"
                            leave="ease-in duration-200"
                            leaveFrom="opacity-100 scale-100"
                            leaveTo="opacity-0 scale-95"
                        >
                            <Dialog.Panel
                                className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                                <div className="flex items-start justify-between">
                                    <Dialog.Title
                                        as="h3"
                                        className="text-lg font-medium leading-6 text-indigo-700"
                                    >
                                        {"Заказ № " + order.id}
                                    </Dialog.Title>
                                    <p className="text-lg font-medium leading-6 text-indigo-700">
                                        {status(order.status.type)}
                                    </p>
                                </div>

                                <div className="mt-6">
                                    <div className="flow-root">
                                        <div className="-my-6 divide-y divide-gray-200">
                                            {order.items.map((product) => (
                                                <li key={product.id} className="flex py-6">
                                                    <div
                                                        className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                                        <img
                                                            src={process.env.REACT_APP_IMAGES_URL + product.doll.poster}
                                                            alt={product.doll.description}
                                                            className="h-full w-full object-cover object-center"
                                                        />
                                                    </div>

                                                    <div className="ml-4 flex flex-1 flex-col">
                                                        <div>
                                                            <div
                                                                className="flex justify-between text-base font-medium text-gray-900">
                                                                <h3>
                                                                    {product.doll.name}
                                                                </h3>
                                                                <p className="ml-4">{product.price + " руб."}</p>
                                                            </div>
                                                            <p className="mt-1 text-sm text-gray-500">{product.doll.description.substring(0, 100)}</p>
                                                        </div>
                                                        <div
                                                            className="flex py-2 flex-1 items-end justify-between text-sm">
                                                            <p className="text-gray-900">{"Кол-во: " + product.quantity}</p>
                                                        </div>
                                                    </div>
                                                </li>
                                            ))}
                                        </div>
                                    </div>
                                </div>

                                <div className="border-t border-gray-200 mt-6 py-2 px-4 sm:px-6">
                                    <div className="flex justify-between text-base font-medium text-gray-900">
                                        <p>Итого</p>
                                        <p>{order.total + " руб."}</p>
                                    </div>
                                    <p className="mt-0.5 text-sm text-gray-500">Стоимость указана с учетом доставки и
                                        налогов</p>
                                    <div className="flex mt-2 justify-between text-base font-medium text-gray-900">
                                        <p>Дата доставка</p>
                                        <p>{order.deliveryDate}</p>
                                    </div>
                                    <div className="mt-4 flex flex-col items-center">
                                        <button type="button" onClick={onClose}
                                                className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                                        >
                                            Закрыть
                                        </button>
                                    </div>
                                </div>


                            </Dialog.Panel>
                        </Transition.Child>
                    </div>
                </div>
            </Dialog>
        </Transition>
    )
}

export default ShowOrder