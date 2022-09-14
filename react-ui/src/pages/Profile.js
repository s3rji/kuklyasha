import React, {useState} from "react";
import {InputField} from "../components/index";

const profile = {
    firstName: "Marina",
    lastName: "Shalimova",
    email: "my@email.ru",
    phone: "8-921-111-11-11",
    street: "Some street",
    city: "Some city",
    region: "Some region",
    zipcode: "123456"
}

const Profile = () => {
    const [firstName, setFirstName] = useState(profile.firstName)
    const [lastName, setLastName] = useState(profile.lastName)
    const [email, setEmail] = useState(profile.email)
    const [phone, setPhone] = useState(profile.phone)
    const [street, setStreet] = useState(profile.street)
    const [city, setCity] = useState(profile.city)
    const [region, setRegion] = useState(profile.region)
    const [zipcode, setZipcode] = useState(profile.zipcode)
    const [readOnly, setReadOnly] = useState(true)

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
                                                        value={process.env.REACT_APP_COUNTRY_LOCATION}
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
                                        onClick={() => setReadOnly(!readOnly)}
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
                                <div className="space-y-6 bg-white px-4 py-5 sm:p-6">
                                    <fieldset>
                                        <legend className="sr-only">By Email</legend>
                                        <div className="text-base font-medium text-gray-900" aria-hidden="true">
                                            By Email
                                        </div>
                                        <div className="mt-4 space-y-4">
                                            <div className="flex items-start">
                                                <div className="flex h-5 items-center">
                                                    <input
                                                        id="comments"
                                                        name="comments"
                                                        type="checkbox"
                                                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                                                    />
                                                </div>
                                                <div className="ml-3 text-sm">
                                                    <label htmlFor="comments" className="font-medium text-gray-700">
                                                        Comments
                                                    </label>
                                                    <p className="text-gray-500">Get notified when someones posts a
                                                        comment on a posting.</p>
                                                </div>
                                            </div>
                                            <div className="flex items-start">
                                                <div className="flex h-5 items-center">
                                                    <input
                                                        id="candidates"
                                                        name="candidates"
                                                        type="checkbox"
                                                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                                                    />
                                                </div>
                                                <div className="ml-3 text-sm">
                                                    <label htmlFor="candidates" className="font-medium text-gray-700">
                                                        Candidates
                                                    </label>
                                                    <p className="text-gray-500">Get notified when a candidate applies
                                                        for a job.</p>
                                                </div>
                                            </div>
                                            <div className="flex items-start">
                                                <div className="flex h-5 items-center">
                                                    <input
                                                        id="offers"
                                                        name="offers"
                                                        type="checkbox"
                                                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                                                    />
                                                </div>
                                                <div className="ml-3 text-sm">
                                                    <label htmlFor="offers" className="font-medium text-gray-700">
                                                        Offers
                                                    </label>
                                                    <p className="text-gray-500">Get notified when a candidate accepts
                                                        or rejects an offer.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                                <div className="bg-gray-50 px-4 py-3 text-right sm:px-6">
                                    <button
                                        type="submit"
                                        className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                    >
                                        Save
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Profile;
