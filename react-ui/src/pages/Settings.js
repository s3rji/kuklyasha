import React, {useState} from "react";
import {Checkbox} from "../components/index";

const Settings = () => {
    const [readOnly, setReadOnly] = useState(true)

    return (
        <div className="mt-10 sm:mt-0 py-12 mx-auto max-w-7xl">
            <div className="md:grid md:grid-cols-3 md:gap-6">
                <div className="md:col-span-1">
                    <div className="px-4 sm:px-0">
                        <h3 className="text-lg font-medium leading-6 text-gray-900">Уведомления</h3>
                        <p className="mt-1 text-sm text-gray-600">Укажите каким способом вы хотите получать
                            сообщения.</p>
                    </div>
                </div>
                <div className="mt-5 md:col-span-2 md:mt-0">
                    <form action="#" method="POST">
                        <div className="overflow-hidden shadow-xl shadow-gray-500/50 sm:rounded-md">
                            <div className="space-y-6 bg-white px-4 py-5 sm:p-6">
                                <fieldset>
                                    <div className="mt-4 space-y-4">
                                        <Checkbox name="email" label="Имейл" readOnly={readOnly}/>
                                        <Checkbox name="phone" label="Телефон" readOnly={readOnly}/>
                                    </div>
                                </fieldset>

                            </div>
                            <div className="bg-gray-50 px-4 py-3 text-right sm:px-6">
                                <button
                                    type="button"
                                    onClick={() => setReadOnly(!readOnly)}
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
    );
};

export default Settings;