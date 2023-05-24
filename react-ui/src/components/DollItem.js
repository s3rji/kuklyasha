import React from "react";
import {useNavigate} from "react-router-dom";
import {DOLL_ROUTE} from "../utils/consts";

const DollItem = ({doll}) => {
    const navigate = useNavigate()

    return (
        <div key={doll.id} className="group" onClick={() => navigate(DOLL_ROUTE + "/" + doll.id)}>
            <div
                className="w-full aspect-w-1 aspect-h-1 bg-gray-200 rounded-lg overflow-hidden xl:aspect-w-7 xl:aspect-h-8">
                <img
                    src={process.env.REACT_APP_IMAGES_URL + doll.poster}
                    alt={doll.description}
                    className="w-80 h-85 object-center object-cover group-hover:opacity-75"
                />
            </div>
            <h3 className="mt-4 text-sm text-gray-700">{doll.name}</h3>
            <p className="mt-1 text-base font-medium text-gray-900">{doll.price + " RUB"}</p>
        </div>
    );
};

export default DollItem;