import React, {useContext, useEffect, useState} from "react";
import {InputField} from "../components/index";
import {EyeIcon} from '@heroicons/react/outline'
import {get, update} from "../http/userAPI";
import {Context} from "../index";
import {observer} from "mobx-react-lite";

const orders = [
    {
        id: 1,
        data: "05-09-2022",
        status: "ready",
        amount: 1000,
        goods: [],
        delivery: "15-09-2022"
    },
    {
        id: 2,
        data: "05-09-2022",
        status: "delivering",
        amount: 2000,
        goods: [],
        delivery: "20-09-2022"
    },
    {
        id: 3,
        data: "05-09-2022",
        status: "done",
        amount: 1500,
        goods: [],
        delivery: "14-09-2022"
    }
]

const Profile = observer(() => {
    const {user} = useContext(Context)

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [phone, setPhone] = useState('')
    const [country, setCountry] = useState('')
    const [city, setCity] = useState('')
    const [region, setRegion] = useState('')
    const [street, setStreet] = useState('')
    const [zipcode, setZipcode] = useState('')
    const [readOnly, setReadOnly] = useState(true)

    useEffect(() => {
        get().then(data => {
            user.setUser(data)
            data.name && setFirstName(data.name)
            data.lastname && setLastName(data.lastname)
            data.email && setEmail(data.email)
            data.phone && setPhone(data.phone)
            data.country && setCountry(data.country)
            data.region && setRegion(data.region)
            data.city && setCity(data.city)
            data.street && setStreet(data.street)
            data.zipcode && setZipcode(data.zipcode)
        })
    }, [user])

    const saveChanges = () => {
        setReadOnly(!readOnly)
        user.user.name = firstName
        user.user.email = email
        user.user.password = "ignored"
        user.user.lastname = lastName
        user.user.phone = phone
        user.user.country = country
        user.user.city = city
        user.user.region = region
        user.user.street = street
        user.user.zipcode = zipcode
        try {
            update(user.user).then(future => {})
        } catch (e) {
            alert(e.response.data.message)
        }
    }

    return (
        <>
            <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="md:col-span-1">
                        <div className="px-4 sm:px-0">
                            <h3 className="text-lg font-medium leading-6 text-gray-900">Личные данные</h3>
                            <p className="mt-1 text-sm text-gray-600">Используйте постоянный адрес, для доставки ваших
                                заказов.</p>
                        </div>
                    </div>
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <form action="#" method="POST">
                            <div className="overflow-visible shadow-xl shadow-gray-500/50 sm:rounded-md ">
                                <div className="bg-white px-4 py-5 sm:p-6">
                                    <div className="grid grid-cols-6 gap-6">
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="text" name="first-name" label="Имя" value={firstName}
                                                        readOnly={readOnly} autoComplete="given-name"
                                                        setValue={e => setFirstName(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="text" name="last-name" label="Фамилия" value={lastName}
                                                        readOnly={readOnly} autoComplete="family-name"
                                                        setValue={e => setLastName(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="email" name="email-address" label="Имейл" value={email}
                                                        readOnly={readOnly} autoComplete="email"
                                                        setValue={e => setEmail(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="tel" name="phone" label="Телефон" value={phone}
                                                        readOnly={readOnly} autoComplete="phone"
                                                        setValue={e => setPhone(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-2">
                                            <InputField type="text" name="country" label="Страна"
                                                        value={country}
                                                        readOnly="true" autoComplete="country-name"
                                            />
                                        </div>
                                        <div className="col-span-6">
                                            <InputField type="text" name="street-address" label="Адрес" value={street}
                                                        readOnly={readOnly} autoComplete="street-address"
                                                        setValue={e => setStreet(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-6 lg:col-span-2">
                                            <InputField type="text" name="city" label="Город" value={city}
                                                        readOnly={readOnly} autoComplete="address-level2"
                                                        setValue={e => setCity(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                            <InputField type="text" name="region" label="Область" value={region}
                                                        readOnly={readOnly} autoComplete="address-level1"
                                                        setValue={e => setRegion(e.target.value)}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                            <InputField type="text" name="postal-code" label="Почтовый индекс"
                                                        value={zipcode}
                                                        readOnly={readOnly} autoComplete="postal-code"
                                                        setValue={e => setZipcode(e.target.value)}
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div className="bg-gray-50 px-4 py-3 text-right sm:px-6">
                                    <button
                                        type="button"
                                        onClick={readOnly ? () => setReadOnly(!readOnly) : saveChanges}
                                        className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                    >
                                        {readOnly ? "Изменить" : "Сохранить"}
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div className="hidden sm:block" aria-hidden="true">
                <div className="py-5">
                    <div className="border-t border-gray-200 mx-auto max-w-7xl"/>
                </div>
            </div>

            <div className="mt-10 sm:mt-0 mx-auto max-w-7xl">
                <div className="md:grid md:grid-cols-3 md:gap-6">
                    <div className="md:col-span-1">
                        <div className="px-4 sm:px-0">
                            <h3 className="text-lg font-medium leading-6 text-gray-900">Заказы</h3>
                            <p className="mt-1 text-sm text-gray-600">Здесь вы можете отслеживать статус заказа</p>
                        </div>
                    </div>
                    <div className="mt-5 md:col-span-2 md:mt-0">
                        <form action="#" method="POST">
                            <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                                    <thead
                                        className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                                    <tr>
                                        <th scope="col" className="py-3 px-6">
                                            Номер заказа
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Статус
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Стоимость
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Дата доставки
                                        </th>
                                        <th scope="col" className="py-3 px-6">
                                            Просмотр
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {orders.map(order =>
                                        <tr key={order.id}
                                            className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                                            <th scope="row"
                                                className="py-4 px-6 font-medium text-zinc-700 whitespace-nowrap dark:text-white">
                                                {order.id}
                                            </th>
                                            <td className="py-4 px-6 text-zinc-700">
                                                <div className="flex items-center">
                                                    <div className="h-2.5 w-2.5 rounded-full bg-green-400 mr-2"></div>
                                                    {order.status}
                                                </div>
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700">
                                                {order.amount + " руб."}
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700">
                                                {order.delivery}
                                            </td>
                                            <td className="py-4 px-6 text-zinc-700 indent-5">
                                                <button type="button"
                                                        className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                                    <EyeIcon className="h-3 w-3" aria-hidden="true"/>
                                                    <span className="sr-only">Просмотр</span>
                                                </button>
                                            </td>
                                        </tr>
                                    )}
                                    </tbody>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
});

export default Profile;