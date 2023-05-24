import {Fragment, useState, useEffect, useContext} from 'react'
import {Dialog, Transition} from '@headlessui/react'
import {PhotographIcon} from "@heroicons/react/solid";
import {observer} from "mobx-react-lite";
import {Context} from "../../index";

const EditDoll = observer(({show, onClose}) => {
    const {doll} = useContext(Context)
    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [price, setPrice] = useState(0)
    const [quantity, setQuantity] = useState(0)
    const [poster, setPoster] = useState('')

    useEffect(() => {
        setName(doll.selected.name)
        setDescription(doll.selected.description)
        setPrice(doll.selected.price)
        setQuantity(doll.selected.quantity)
        setPoster(doll.selected.poster)
        }, [doll.selected])

    const selectFile = e => {
        setPoster(e.target.files[0])
    }

    const sendDoll = () => {
        doll.selected.name = name
        doll.selected.description = description
        doll.selected.price = price
        doll.selected.quantity = quantity
        doll.selected.poster = poster
        console.log(doll.selected)
    }

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
                                className="w-full max-w-5xl transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">

                                <form>
                                    <div className="space-y-12">
                                        <div className="border-b border-gray-900/10 pb-12">
                                            <h2 className="text-base font-semibold leading-7 text-gray-900">Редактирование
                                                товара</h2>
                                            <p className="mt-1 text-sm leading-6 text-gray-600">
                                                Введите информацию о товаре.
                                            </p>

                                            <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                                                <div className="sm:col-span-4">
                                                    <label htmlFor="username"
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
                                                    </div>
                                                </div>

                                                <div className="sm:col-span-1">
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
                                                                className="block flex-1 border-0 bg-transparent max-w-full py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 focus:outline-none sm:text-sm sm:leading-6"
                                                            />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="sm:col-span-1">
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
                                                </div>

                                                <div className="col-span-full">
                                                    <label htmlFor="photo"
                                                           className="block text-sm font-medium leading-6 text-gray-900">
                                                        Фото
                                                    </label>
                                                    {poster && <div className="grid-col-2 py-2">
                                                        <div
                                                            className="h-24 w-24 col-span-1 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                                            <img
                                                                src={process.env.REACT_APP_IMAGES_URL + poster}
                                                                alt={doll.name}
                                                                className="h-full w-full object-cover object-center"
                                                            />
                                                        </div>
                                                        <button
                                                            type="button"
                                                            className="col-span-1 w-24 rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-400 hover:bg-gray-200"
                                                        >
                                                            Удалить
                                                        </button>
                                                    </div>}
                                                    <div
                                                        className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                                                        <div className="text-center">
                                                            <PhotographIcon className="mx-auto h-12 w-12 text-gray-300"
                                                                            aria-hidden="true"/>
                                                            <div className="mt-4 flex text-sm leading-6 text-gray-600">
                                                                <label
                                                                    htmlFor="file-upload"
                                                                    className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                                                                >
                                                                    <span>Загрузить файл</span>
                                                                    <input id="file-upload" name="file-upload"
                                                                           type="file" onChange={selectFile} className="sr-only"/>
                                                                </label>
                                                                <p className="pl-1">или перетащите</p>
                                                            </div>
                                                            <p className="text-xs leading-5 text-gray-600">PNG, JPG не
                                                                более 10MB</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="mt-6 flex items-center justify-end gap-x-6">
                                        <button type="button" onClick={onClose}
                                                className="text-sm font-semibold leading-6 text-gray-900">
                                            Отмена
                                        </button>
                                        <button
                                            type="button"
                                            onClick={sendDoll}
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