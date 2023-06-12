import React, {Fragment, useContext, useEffect, useState} from 'react'
import {Dialog, Transition} from '@headlessui/react'
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {UploadFile} from "./index";
import {createDoll, updateDoll} from "../../http/dollApi";
import {deleteFiles} from "../../http/fileApi";
import {Doll} from "../../store/admin";

const EditDoll = observer(({show, hide}) => {
    const {doll} = useContext(Context)
    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [price, setPrice] = useState(0)
    const [quantity, setQuantity] = useState(0)
    const [gallery, setGallery] = useState([])

    const [nameErr, setNameErr] = useState('')
    const [descriptionErr, setDescriptionErr] = useState('')
    const [priceErr, setPriceErr] = useState('')
    const [quantityErr, setQuantityErr] = useState('')
    const [posterErr, setPosterErr] = useState('')

    const resetFields = () => {
        setName(doll.selected.name)
        setDescription(doll.selected.description)
        setPrice(doll.selected.price)
        setQuantity(doll.selected.quantity)
        setGallery([...doll.selected.gallery])
        setNameErr('')
        setDescriptionErr('')
        setPriceErr('')
        setQuantityErr('')
        setPosterErr('')
    }

    useEffect(() => {
        resetFields()
    }, [doll.selected])

    const saveChanges = () => {
        if (validationFailed()) {
            return
        }

        doll.selected.name = name
        doll.selected.description = description
        doll.selected.price = price
        doll.selected.quantity = quantity
        doll.selected.poster = gallery[0]
        const deletedPictures = doll.selected.gallery.filter(value => !gallery.includes(value))
        doll.selected.gallery = gallery

        if (doll.selected.id === null) {
            createDoll(doll.selected)
                .then(() => {
                    doll.setSelected(new Doll())
                    doll.setTotal(doll.total + 1)
                }).catch(error => alert(error.response.data.message))
        } else {
            updateDoll(doll.selected.id, doll.selected)
                .then(() => doll.setSelected(new Doll()))
                .catch(error => alert(error.response.data.message))

            if (deletedPictures.length > 0) {
                deleteFiles({"fileNames": deletedPictures})
                    .catch(error => console.log(error))
            }
        }
        hide()
    }

    const validationFailed = () => {
        setNameErr('')
        setDescriptionErr('')
        setPriceErr('')
        setQuantityErr('')
        setPosterErr('')
        let hasErrors = false
        if (name.length < 2 || name.length > 128) {
            setNameErr('От 2 до 128 символов')
            hasErrors = true
        }
        if (description.length < 10) {
            setDescriptionErr('Не менее 10 символов')
            hasErrors = true
        }
        if (price < 1 || price > 100000) {
            setPriceErr('В диапазоне от 1 до 100\'000')
            hasErrors = true
        }
        if (quantity < 0) {
            setQuantityErr('Не может быть отрицательным')
            hasErrors = true
        }
        if (gallery.length < 1) {
            setPosterErr('Загрузите минимум одно фото')
            hasErrors = true
        }
        return hasErrors
    }

    const closeModal = () => {
        hide()
        const newPictures = gallery.filter(value => !doll.selected.gallery.includes(value))
        if (newPictures.length > 0) {
            deleteFiles({"fileNames": newPictures})
                .catch(reason => console.log(reason))
        }
        resetFields()
    }

    return (
        <Transition appear show={show} as={Fragment}>
            <Dialog as="div" className="relative z-10" onClose={closeModal}>
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
                                className="w-full max-w-5xl transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">

                                <form>
                                    <div className="space-y-12">
                                        <div className="border-b border-gray-900/10 pb-12">
                                            <h2 className="text-base font-semibold leading-7 text-gray-900">Редактирование
                                                товара</h2>
                                            <p className="mt-1 text-sm leading-6 text-gray-600">
                                                Введите информацию о товаре.
                                            </p>

                                            <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-7">
                                                <div className="sm:col-span-3">
                                                    <label htmlFor="name"
                                                           className="block text-sm font-medium leading-6 text-gray-900">
                                                        Наименование
                                                    </label>
                                                    <div className="mt-2">
                                                        <div
                                                            className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                            <input
                                                                type="text"
                                                                name="name"
                                                                id="name"
                                                                autoComplete="name"
                                                                value={name}
                                                                onChange={e => setName(e.target.value)}
                                                                className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 focus:outline-none sm:text-sm sm:leading-6"
                                                            />
                                                        </div>
                                                        <span
                                                            className="mt-2 p-2 text-sm text-red-600 dark:text-red-500 ">
                                                            {nameErr}
                                                        </span>
                                                    </div>
                                                </div>

                                                <div className="sm:col-span-2">
                                                    <label htmlFor="price"
                                                           className="block text-sm font-medium leading-6 text-gray-900">
                                                        Цена
                                                    </label>
                                                    <div className="mt-2">
                                                        <div
                                                            className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-full">
                                                            <input
                                                                type="number"
                                                                step="0.1"
                                                                min="0"
                                                                max="100000"
                                                                name="price"
                                                                id="price"
                                                                autoComplete="price"
                                                                value={price}
                                                                onChange={e => setPrice(Number.parseInt(e.target.value))}
                                                                className="block flex-1 border-0 bg-transparent max-w-10 py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 focus:outline-none sm:text-sm sm:leading-6"
                                                            />
                                                        </div>
                                                        <span
                                                            className="mt-2 p-2 text-sm text-red-600 dark:text-red-500 ">
                                                            {priceErr}
                                                        </span>
                                                    </div>
                                                </div>

                                                <div className="sm:col-span-2">
                                                    <label htmlFor="quantity"
                                                           className="block text-sm font-medium leading-6 text-gray-900">
                                                        Количество
                                                    </label>
                                                    <div className="mt-2">
                                                        <div
                                                            className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-full">
                                                            <input
                                                                type="number"
                                                                name="quantity"
                                                                id="quantity"
                                                                autoComplete="quantity"
                                                                value={quantity}
                                                                onChange={e => setQuantity(Number.parseInt(e.target.value))}
                                                                className="block flex-1 border-0 bg-transparent max-w-full py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 focus:outline-none sm:text-sm sm:leading-6"
                                                            />
                                                        </div>
                                                        <span
                                                            className="mt-2 p-2 text-sm text-red-600 dark:text-red-500 ">
                                                            {quantityErr}
                                                        </span>
                                                    </div>
                                                </div>

                                                <div className="col-span-full">
                                                    <label htmlFor="about"
                                                           className="block text-sm font-medium leading-6 text-gray-900">
                                                        Описание
                                                    </label>
                                                    <div className="mt-2">
                                                        <textarea
                                                            id="about"
                                                            name="about"
                                                            rows={3}
                                                            value={description}
                                                            onChange={e => setDescription(e.target.value)}
                                                            className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 focus:outline-none sm:text-sm sm:leading-6"
                                                        />
                                                    </div>
                                                    <span className="mt-2 p-2 text-sm text-red-600 dark:text-red-500 ">
                                                            {descriptionErr}
                                                    </span>
                                                </div>
                                                <div className="col-span-full">
                                                    <UploadFile gallery={gallery} setGallery={setGallery}
                                                                doll={doll.selected}/>
                                                    <span className="mt-2 p-2 text-sm text-red-600 dark:text-red-500 ">
                                                            {posterErr}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="mt-6 flex items-center justify-end gap-x-6">
                                        <button type="button" onClick={closeModal}
                                                className="text-sm font-semibold leading-6 text-gray-900">
                                            Отмена
                                        </button>
                                        <button
                                            type="button"
                                            onClick={saveChanges}
                                            className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                                        >
                                            Сохранить
                                        </button>
                                    </div>
                                </form>
                            </Dialog.Panel>
                        </Transition.Child>
                    </div>
                </div>
            </Dialog>
        </Transition>
    )
})

export default EditDoll