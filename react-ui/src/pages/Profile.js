import React, {useContext, useEffect, useState} from "react";
import {InputField, InputPhone, Settings} from "../components/index";
import {getUser, updateUser} from "../http/userAPI";
import {Context} from "../index";
import {observer} from "mobx-react-lite";

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

    const [firstnameErr, setFirstnameErr] = useState('')
    const [lastnameErr, setLastnameErr] = useState('')
    const [emailErr, setEmailErr] = useState('')
    const [phoneErr, setPhoneErr] = useState('')
    const [streetErr, setStreetErr] = useState('')
    const [cityErr, setCityErr] = useState('')
    const [regionErr, setRegionErr] = useState('')
    const [zipcodeErr, setZipcodeErr] = useState('')
    const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    const zipcodeRegex = /(^\d{6}$)/

    useEffect(() => {
        getUser().then(data => {
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
        if (validationFailed()) {
            return
        }

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
            console.log(phone)
            updateUser(user.user).then(future => {
            })
        } catch (e) {
            alert(e.response.data.message)
        }
    }

    const validationFailed = () => {
        setFirstnameErr('')
        setLastnameErr('')
        setEmailErr('')
        setPhoneErr('')
        setStreetErr('')
        setCityErr('')
        setRegionErr('')
        setZipcodeErr('')
        let hasErrors = false;
        if (firstName.length < 2) {
            setFirstnameErr("Не менее двух символов")
            hasErrors = true
        }
        if (lastName.length < 2) {
            setLastnameErr("Не менее двух символов")
            hasErrors = true
        }
        if (!email.toLowerCase().match(emailRegex)) {
            setEmailErr("Неверный формат электронной почты")
            hasErrors = true
        }
        if (phone.length !== 11) {
            setPhoneErr("Введите 10 цифр номера без +7")
            hasErrors = true
        }
        if (street.length < 2) {
            setStreetErr("Не менее двух символов")
            hasErrors = true
        }
        if (city.length < 2) {
            setCityErr("Не менее двух символов")
            hasErrors = true
        }
        if (region.length < 2) {
            setRegionErr("Не менее двух символов")
            hasErrors = true
        }
        if (!zipcode.match(zipcodeRegex)) {
            setZipcodeErr("Почтовый индекс должен состоять из 6 цифр")
            hasErrors = true
        }

        return hasErrors
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
                                                        error={firstnameErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="text" name="last-name" label="Фамилия" value={lastName}
                                                        readOnly={readOnly} autoComplete="family-name"
                                                        setValue={e => setLastName(e.target.value)}
                                                        error={lastnameErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputField type="email" name="email-address" label="Имейл" value={email}
                                                        readOnly={readOnly} autoComplete="email"
                                                        setValue={e => setEmail(e.target.value)}
                                                        error={emailErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3">
                                            <InputPhone name="phone" label="Телефон" value={phone}
                                                        readOnly={readOnly} setValue={e => setPhone(e)}
                                                        error={phoneErr}
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
                                                        error={streetErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-6 lg:col-span-2">
                                            <InputField type="text" name="city" label="Город" value={city}
                                                        readOnly={readOnly} autoComplete="address-level2"
                                                        setValue={e => setCity(e.target.value)}
                                                        error={cityErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                            <InputField type="text" name="region" label="Область" value={region}
                                                        readOnly={readOnly} autoComplete="address-level1"
                                                        setValue={e => setRegion(e.target.value)}
                                                        error={regionErr}
                                            />
                                        </div>
                                        <div className="col-span-6 sm:col-span-3 lg:col-span-2">
                                            <InputField type="text" name="postal-code" label="Почтовый индекс"
                                                        value={zipcode}
                                                        readOnly={readOnly} autoComplete="postal-code"
                                                        setValue={e => setZipcode(e.target.value)}
                                                        error={zipcodeErr}
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
            <Settings></Settings>
        </>
    );
});

export default Profile;