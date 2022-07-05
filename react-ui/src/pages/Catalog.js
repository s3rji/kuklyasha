import React, {useContext, useEffect} from "react";
import {DollList, Pages} from "../components/index";
import {observer} from "mobx-react-lite";
import {Context} from "../index";
import {fetchDolls} from "../http/dollApi";

const Catalog = observer(() => {
    const {doll} = useContext(Context)

    useEffect(() => {
        fetchDolls().then(data => doll.setDolls(data))
    }, [])

    return (
        <div className="container mx-auto">
            <DollList></DollList>
            <Pages></Pages>
        </div>
    );
});

export default Catalog;