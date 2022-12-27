import React from 'react'

const products = [
    {
        id: 1,
        name: 'Throwback Hip Bag',
        href: '#',
        color: 'Salmon',
        price: '$90.00',
        quantity: 1,
        imageSrc: 'https://tailwindui.com/img/ecommerce-images/shopping-cart-page-04-product-01.jpg',
        imageAlt: 'Salmon orange fabric pouch with match zipper, gray zipper pull, and adjustable hip belt.',
    },
    {
        id: 2,
        name: 'Medium Stuff Satchel',
        href: '#',
        color: 'Blue',
        price: '$32.00',
        quantity: 1,
        imageSrc: 'https://tailwindui.com/img/ecommerce-images/shopping-cart-page-04-product-02.jpg',
        imageAlt:
            'Front of satchel with blue canvas body, black straps and handle, drawstring top, and front zipper pouch.',
    },
    // More products...
]

const Cart = () => {

    return (
        <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
            <div className="md:grid md:grid-cols-3 md:gap-6">
                <div className="mt-5 md:col-span-2 md:mt-0">
                    <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                        <div className="mt-4 mb-6">
                            <div className="flow-root">
                                <div className="-my-6 divide-y divide-gray-200 mx-20">
                                    {products.map((product) => (
                                        <li key={product.id} className="flex py-6">
                                            <div
                                                className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                                <img
                                                    src={product.imageSrc}
                                                    alt={product.imageAlt}
                                                    className="h-full w-full object-cover object-center"
                                                />
                                            </div>

                                            <div className="ml-4 flex flex-1 flex-col">
                                                <div>
                                                    <div
                                                        className="flex justify-between text-base font-medium text-gray-900">
                                                        <h3>
                                                            <a href={product.href}>{product.name}</a>
                                                        </h3>
                                                        <p className="ml-4">{product.price}</p>
                                                    </div>
                                                    <p className="mt-1 text-sm text-gray-500">{product.color}</p>
                                                </div>
                                                <div className="flex flex-1 items-end justify-between text-sm">
                                                    <p className="text-gray-500">Qty {product.quantity}</p>

                                                    <div className="flex">
                                                        <button
                                                            type="button"
                                                            className="font-medium text-indigo-600 hover:text-indigo-500"
                                                        >
                                                            Remove
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
                            <p>01.01.2023 - 05.01.2023</p>
                        </div>
                        <p className="mt-0.5 text-sm text-gray-500">Указана приблизительная дата доставки</p>
                    </div>
                    <div className="border-t border-gray-200 py-6 px-4 sm:px-6">
                        <div className="flex justify-between text-base font-medium text-gray-900">
                            <p>Итого:</p>
                            <p>262.00 руб.</p>
                        </div>
                        <p className="mt-0.5 text-sm text-gray-500">Стоимость указана с учетом доставки и
                            налогов</p>
                        <div className="mt-6">
                            <div
                                onClick={''}
                                className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700"
                            >
                                Заказать
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Cart