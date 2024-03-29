import React from "react";
import {useNavigate} from "react-router-dom";
import {DOLL_ROUTE} from "../../utils/consts";

const DollItem = ({doll, showDoll}) => {
    const navigate = useNavigate()

    return (
        <div className="group">
            <div onClick={() => navigate(DOLL_ROUTE + "/" + doll.id)}
                 className="h-80 w-full aspect-w-1 aspect-h-1 bg-gray-200 rounded-lg overflow-hidden xl:aspect-w-7 xl:aspect-h-8">
                <img
                    src={process.env.REACT_APP_IMAGES_URL + doll.poster}
                    alt={doll.description}
                    className="w-80 h-85 object-center object-cover group-hover:opacity-75"
                />
            </div>
            <div className="flex grid-col-3 gap-16 relative">
                <div className="col-span-2">
                    <h3 className="mt-4 text-sm text-gray-700">{doll.name.length < 15 ? doll.name : doll.name.substring(0, 15) + '...'}</h3>
                    <p className="mt-1 text-base font-medium text-gray-900">{doll.price + " RUB"}</p>
                </div>
                <div className="col-span-1 mt-5 absolute bottom-0 right-0">
                    <button
                        onClick={() => showDoll(doll)}
                        className="bg-sky-500 border border-transparent rounded-md py-2 px-5 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                    >
                        Изменить
                    </button>
                </div>
            </div>
        </div>
    );
};

export default DollItem;