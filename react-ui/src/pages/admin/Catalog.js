import React, {useContext, useEffect, useState} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {getDollsByPage} from "../../http/dollApi";
import {Pages} from "../../components/index";
import {AdminDollList, EditDoll} from "../../components/admin/index";
import {Doll} from "../../store/admin";

const Catalog = observer(() => {
    const {doll} = useContext(Context)
    const [isShowModal, setIsShowModal] = useState(false)

    useEffect(() => {
        getDollsByPage(doll.page, doll.limit).then(data => {
            doll.setCatalog(data.content)
            doll.setTotal(data.total)
        })
    }, [doll, doll.page, doll.total, doll.selected])

    const addNewDoll = () => {
        doll.setSelected(new Doll());
        setIsShowModal(true)
    }

    return (
        <div
            className="container flex flex-row space-x-4">
            <div className="ml-8 pl-10 mt-5 basis-1/6">
                <button
                    onClick={addNewDoll}
                    className="bg-sky-500 border border-transparent rounded-md py-3 px-8 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Добавить товар
                </button>
            </div>
            <div className="basis-5/6">
                <AdminDollList></AdminDollList>
                <Pages></Pages>
            </div>
            <EditDoll show={isShowModal} hide={() => setIsShowModal(false)}></EditDoll>
        </div>
    );
});

export default Catalog;