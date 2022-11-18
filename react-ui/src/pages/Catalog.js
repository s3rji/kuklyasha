import React, {useContext, useEffect} from "react";
import {DollList, Pages} from "../components/index";
import {observer} from "mobx-react-lite";
import {Context} from "../index";
import {getDollsByPage} from "../http/dollApi";

const Catalog = observer(() => {
    const {doll} = useContext(Context)

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
        <div className="container mx-auto">
            <DollList></DollList>
            <Pages></Pages>
        </div>
    );
});

export default Catalog;