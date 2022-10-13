import {CreditCardIcon, GlobeAltIcon, PhoneIcon, ShoppingBagIcon} from '@heroicons/react/outline'

const features = [
    {
        name: 'О нас',
        description:
            'Наша особенность в том, что мы не выходим на рынок mass market, а производим продукцию малыми авторскими сериями. ' +
            'Поэтому вы можете быть уверены, что приобрели уникальные работы. ' +
            'Мы постоянно трудимся над новинками - обновляем изображения, разнообразим формы и виды изделий, придумываем поистине оригинальные образы.',
        icon: GlobeAltIcon,
    },
    {
        name: 'Информация об ассортименте и заказах',
        description:
            'Весь ассортимент представлен на странице каталога нашего магазина. Чтобы приобрести изделие, необходимо добавить его в корзину и оформить заказ. ' +
            'В личном кабинете пользователя, вы можете отслеживать статус и историю своих заказов. Если вы использовали конструктор для оформления заказа, то он также ' +
            'будет отображаться в вашем личном кабинете.',
        icon: ShoppingBagIcon,
    },
    {
        name: 'Как с нами связаться',
        description:
            'Если у вас есть вопросы, то с нами всегда можно связаться по реквизитам, указанным на странице "Контакты".',
        icon: PhoneIcon,
    },
    {
        name: 'Как оплатить',
        description:
            'Заказ можно оплатить онлайн при оформление, используя карту любого банка. Для возврата денежных средств, свяжитесь с нами любым удобным вам способом',
        icon: CreditCardIcon,
    }
]

const AboutUs = () => {
    return (
        <div className="bg-white py-12">
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                <div className="lg:text-center font-sans">
                    <h2 className="text-5xl font-semibold text-indigo-600">Кукляша</h2>
                    <p className="mt-2 text-3xl font-bold leading-8 tracking-tight text-gray-900 sm:text-4xl">
                        Магазин авторских интерьерных кукол
                    </p>
                    <p className="mt-4 text-justify max-w-7xl text-xl text-gray-500 lg:mx-auto">
                        Хотите оживить интерьер и создать в доме особую атмосферу?
                        Дополните обстановку спальни, гостиной или кухни авторской интерьерной куклой ручной работы.
                        Изделия из папье-маше, текстиля с набивкой и других материалов создаются мастерами с любовью.
                        Каждая кукла имеет свой характер и неповторимый дизайн, передает живые эмоции.
                    </p>
                    <p className="mt-2 text-justify max-w-7xl text-xl text-gray-500 lg:mx-auto">
                        Выбираете необычный подарок для коллекционера? Хотите порадовать себя и близких?
                        Ознакомьтесь с ассортиментом нашего каталога. У нас можно купить куклы авторской работы:
                    </p>
                    <ul className="indent-8 mt-2 text-justify max-w-7xl text-xl text-gray-500 lg:mx-auto">
                        <li>
                            • текстильные
                        </li>
                        <li>
                            • будуарные с подвижными элементами.
                        </li>
                    </ul>
                    <p className="mt-2 text-justify max-w-7xl text-xl text-gray-500 lg:mx-auto">
                        Авторские интерьерные куклы ручной работы изготовлены из качественных материалов, имеют разные
                        размеры.
                        Правила ухода за изделиями и стоимость приведены на страницах товаров.
                    </p>
                    <p className="mt-2 text-justify max-w-7xl text-xl text-gray-500 lg:mx-auto">
                        Чтобы купить куклу авторской работы, добавьте ее в корзину и оставьте заявку.
                    </p>
                </div>

                <div className="mt-10">
                    <dl className="space-y-10 md:grid md:grid-cols-2 md:gap-x-8 md:gap-y-10 md:space-y-0">
                        {features.map((feature) => (
                            <div key={feature.name} className="relative">
                                <dt>
                                    <div
                                        className="absolute flex h-12 w-12 items-center justify-center rounded-md bg-indigo-500 text-white">
                                        <feature.icon className="h-6 w-6" aria-hidden="true"/>
                                    </div>
                                    <p className="ml-16 text-lg font-medium leading-6 text-gray-900">{feature.name}</p>
                                </dt>
                                <dd className="mt-2 ml-16 text-base text-gray-500">{feature.description}</dd>
                            </div>
                        ))}
                    </dl>
                </div>
            </div>
        </div>
    );
};

export default AboutUs;