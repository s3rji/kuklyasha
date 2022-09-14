const Contacts = () => {
    return (
        <div className="overflow-hidden bg-white sm:rounded-lg shadow-xl shadow-gray-500/50 max-w-7xl mx-auto mt-12">
            <div className="px-4 py-5 sm:px-6">
                <h3 className="text-lg text-center font-medium leading-6 text-gray-900 ">Контактная информация</h3>
                <p className="mt-1 text-sm text-gray-500 text-center">Интернет-магазин Кукляша.</p>
            </div>
            <div className="border-t border-gray-900 border-solid sm:rounded-lg">
                <dl>
                    <div className="bg-gray-100 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 ">
                        <dt className="text-sm font-medium text-gray-500">Менеджер по работе с клиентами</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_MANAGER}</dd>
                    </div>
                    <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Адрес</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_SHOP_ADDRESS}</dd>
                    </div>
                    <div className="bg-gray-100 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Имейл</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_EMAIL}</dd>
                    </div>
                    <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Телефон</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_PHONE_NUMBER}</dd>
                    </div>
                    <div className="bg-gray-100 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Telegram</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_TELEGRAM}</dd>
                    </div>
                    <div className="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Vkontakte</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_VK}</dd>
                    </div>
                    <div className="bg-gray-100 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                        <dt className="text-sm font-medium text-gray-500">Instagram</dt>
                        <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">{process.env.REACT_APP_INSTAGRAM}</dd>
                    </div>
                </dl>
            </div>
        </div>
    );
};

export default Contacts;