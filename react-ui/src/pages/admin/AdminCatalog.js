import React, {useContext, useEffect, useState} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {getDollsByPage} from "../../http/dollApi";
import {AdminDollList, EditDoll, Pages} from "../../components/index";
import {Doll} from "../../store/index";

const AdminCatalog = observer(() => {
    const {doll} = useContext(Context)
    const [isShowModal, setIsShowModal] = useState(false)

    useEffect(() => {
        getDollsByPage(doll.page, doll.limit).then(data => {
            doll.setDolls(data.content)
            doll.setTotal(data.total)
        })
    }, [doll])

    useEffect(() => {
        getDollsByPage(doll.page, doll.limit).then(data => {
            doll.setDolls(data.content)
            doll.setTotal(data.total)
        })
    }, [doll, doll.page])

    return (
        <div
            className="container flex flex-row space-x-4">
            <div className="ml-8 pl-10 mt-5 basis-1/6">
                <button
                    onClick={() => setIsShowModal(true)}
                    className="bg-indigo-600 border border-transparent rounded-md py-3 px-8 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Добавить товар
                </button>
            </div>
            <div className="basis-5/6">
                <AdminDollList></AdminDollList>
                <Pages></Pages>
            </div>
            <EditDoll doll={new Doll()} show={isShowModal}
                      onClose={() => setIsShowModal(false)}></EditDoll>
        </div>
    );
});

export default AdminCatalog;