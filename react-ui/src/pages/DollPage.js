import {useEffect, useState} from "react"
import {StarIcon} from "@heroicons/react/solid"
import {RadioGroup} from "@headlessui/react"
import {getDoll} from "../http/dollApi";
import {useParams} from "react-router-dom";
import {CATALOG_ROUTE, DOLL_ROUTE} from "../utils/consts";
import {observer} from "mobx-react-lite";

const breadcrumbs = [
    {id: 1, name: 'Catalog', href: CATALOG_ROUTE}
]
const colors = [
    {name: 'White', class: 'bg-white', selectedClass: 'ring-gray-400'},
    {name: 'Gray', class: 'bg-gray-200', selectedClass: 'ring-gray-400'},
    {name: 'Black', class: 'bg-gray-900', selectedClass: 'ring-gray-900'},
]
const highlights = [
    'Hand cut and sewn locally',
    'Dyed with our proprietary colors',
    'Pre-washed & pre-shrunk',
    'Ultra-soft 100% cotton',
]
const details = 'The 6-Pack includes two black, two white, and two heather gray Basic Tees. Sign up for our subscription service and be the first to get new, exciting colors, like our upcoming "Charcoal Gray" limited release.'
const reviews = {href: '#', average: 4, totalCount: 117}


function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const DollPage = observer(() => {
    const [doll, setDoll] = useState({})
    const {id} = useParams()
    useEffect(() => {
        getDoll(id).then(data => setDoll(data))
    }, [])
    const [selectedColor, setSelectedColor] = useState(colors[0])

    const labelReview = () => {
        const lastDigit = reviews.totalCount % 10;
        switch (lastDigit) {
            case 1:
                return " отзыв"
            case 2:
            case 3:
            case 4:
                return " отзыва"
            default:
                return " отзывов"
        }
    }

    return (
        <div className="bg-white">
            <div className="pt-6">
                <nav aria-label="Breadcrumb">
                    <ol role="list"
                        className="max-w-2xl mx-auto px-4 flex items-center space-x-2 sm:px-6 lg:max-w-7xl lg:px-8">
                        {breadcrumbs.map((breadcrumb) => (
                            <li key={breadcrumb.id}>
                                <div className="flex items-center">
                                    <a href={breadcrumb.href} className="mr-2 text-sm font-medium text-gray-900">
                                        {breadcrumb.name}
                                    </a>
                                    <svg
                                        width={16}
                                        height={20}
                                        viewBox="0 0 16 20"
                                        fill="currentColor"
                                        xmlns="http://www.w3.org/2000/svg"
                                        aria-hidden="true"
                                        className="w-4 h-5 text-gray-300"
                                    >
                                        <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z"/>
                                    </svg>
                                </div>
                            </li>
                        ))}
                        <li className="text-sm">
                            <a href={DOLL_ROUTE + '/' + doll.id} aria-current="page"
                               className="font-medium text-gray-500 hover:text-gray-600">
                                {doll.name}
                            </a>
                        </li>
                    </ol>
                </nav>

                {/* Image gallery */}
                <div className="mt-6 max-w-2xl mx-auto sm:px-6 lg:max-w-7xl lg:px-8 lg:grid lg:grid-cols-3 lg:gap-x-8">
                    <div className="hidden aspect-w-3 aspect-h-4 rounded-lg overflow-hidden lg:block">
                        <img
                            src={process.env.REACT_APP_IMAGES_URL + doll.image}
                            alt={doll.description}
                            className="w-full h-90 object-center object-cover"
                        />
                    </div>
                    <div className="hidden lg:grid lg:grid-cols-1 lg:gap-y-8">
                        <div className="aspect-w-3 aspect-h-2 rounded-lg overflow-hidden">
                            <img
                                src={process.env.REACT_APP_IMAGES_URL + doll.image}
                                alt={doll.description}
                                className="w-full h-56 object-center object-cover"
                            />
                        </div>
                        <div className="aspect-w-3 aspect-h-2 rounded-lg overflow-hidden">
                            <img
                                src={process.env.REACT_APP_IMAGES_URL + doll.image}
                                alt={doll.description}
                                className="w-full h-56 object-center object-cover"
                            />
                        </div>
                    </div>
                    <div className="aspect-w-4 aspect-h-5 sm:rounded-lg sm:overflow-hidden lg:aspect-w-3 lg:aspect-h-4">
                        <img
                            src={process.env.REACT_APP_IMAGES_URL + doll.image}
                            alt={doll.description}
                            className="w-full h-90 object-center object-cover"
                        />
                    </div>
                </div>

                {/* Product info */}
                <div
                    className="max-w-2xl mx-auto pt-10 pb-16 px-4 sm:px-6 lg:max-w-7xl lg:pt-16 lg:pb-24 lg:px-8 lg:grid lg:grid-cols-3 lg:grid-rows-[auto,auto,1fr] lg:gap-x-8">
                    <div className="lg:col-span-2 lg:border-r lg:border-gray-200 lg:pr-8">
                        <h1 className="text-2xl font-extrabold tracking-tight text-gray-900 sm:text-3xl">{doll.name}</h1>
                    </div>

                    {/* Options */}
                    <div className="mt-4 lg:mt-0 lg:row-span-3">
                        <h2 className="sr-only">Product information</h2>
                        <p className="text-3xl text-gray-900">{doll.price + " RUB"}</p>

                        {/* Reviews */}
                        <div className="mt-6">
                            <h3 className="sr-only">Reviews</h3>
                            <div className="flex items-center">
                                <div className="flex items-center">
                                    {[0, 1, 2, 3, 4].map((rating) => (
                                        <StarIcon
                                            key={rating}
                                            className={classNames(
                                                reviews.average > rating ? 'text-gray-900' : 'text-gray-200',
                                                'h-5 w-5 flex-shrink-0'
                                            )}
                                            aria-hidden="true"
                                        />
                                    ))}
                                </div>
                                <p className="sr-only">{reviews.average} out of 5 stars</p>
                                <a href={reviews.href}
                                   className="ml-3 text-sm font-medium text-indigo-600 hover:text-indigo-500">
                                    {reviews.totalCount + labelReview()}
                                </a>
                            </div>
                        </div>

                        <form className="mt-10">
                            {/* Colors */}
                            <div>
                                <h3 className="text-sm text-gray-900 font-medium">Цвет</h3>

                                <RadioGroup value={selectedColor} onChange={setSelectedColor} className="mt-4">
                                    <RadioGroup.Label className="sr-only">Choose a color</RadioGroup.Label>
                                    <div className="flex items-center space-x-3">
                                        {colors.map((color) => (
                                            <RadioGroup.Option
                                                key={color.name}
                                                value={color}
                                                className={({active, checked}) =>
                                                    classNames(
                                                        color.selectedClass,
                                                        active && checked ? 'ring ring-offset-1' : '',
                                                        !active && checked ? 'ring-2' : '',
                                                        '-m-0.5 relative p-0.5 rounded-full flex items-center justify-center cursor-pointer focus:outline-none'
                                                    )
                                                }
                                            >
                                                <RadioGroup.Label as="span" className="sr-only">
                                                    {color.name}
                                                </RadioGroup.Label>
                                                <span
                                                    aria-hidden="true"
                                                    className={classNames(
                                                        color.class,
                                                        'h-8 w-8 border border-black border-opacity-10 rounded-full'
                                                    )}
                                                />
                                            </RadioGroup.Option>
                                        ))}
                                    </div>
                                </RadioGroup>
                            </div>

                            <button
                                type="submit"
                                className="mt-10 w-full bg-indigo-600 border border-transparent rounded-md py-3 px-8 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                            >
                                Добавить в корзину
                            </button>
                        </form>
                    </div>

                    <div
                        className="py-10 lg:pt-6 lg:pb-16 lg:col-start-1 lg:col-span-2 lg:border-r lg:border-gray-200 lg:pr-8">
                        {/* Description and details */}
                        <div>
                            <h3 className="sr-only">Description</h3>

                            <div className="space-y-6">
                                <p className="text-base text-gray-900">{doll.description}</p>
                            </div>
                        </div>

                        <div className="mt-10">
                            <h3 className="text-sm font-medium text-gray-900">Особенности</h3>

                            <div className="mt-4">
                                <ul role="list" className="pl-4 list-disc text-sm space-y-2">
                                    {highlights.map((highlight) => (
                                        <li key={highlight} className="text-gray-400">
                                            <span className="text-gray-600">{highlight}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>

                        <div className="mt-10">
                            <h2 className="text-sm font-medium text-gray-900">Подробности</h2>

                            <div className="mt-4 space-y-6">
                                <p className="text-sm text-gray-600">{details}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
});

export default DollPage;
