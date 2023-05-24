import {useContext} from "react";
import {Context} from "../index";
import {observer} from "mobx-react-lite";
import {DollItem} from "./index";

const DollList = observer(() => {
    const {doll} = useContext(Context)

    return (
        <div className="bg-white">
            <div className="max-w-3xl mx-auto py-16 px-4 sm:py-5 sm:px-6 lg:max-w-7xl lg:px-8">
                <div
                    className="grid grid-cols-1 gap-y-10 sm:grid-cols-2 gap-x-6 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
                    {doll.catalog.map(doll => (
                        <DollItem key={doll.id} doll={doll}/>
                    ))}
                </div>
            </div>
        </div>
    );
});

export default DollList;
