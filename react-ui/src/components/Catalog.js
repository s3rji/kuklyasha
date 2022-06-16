import doll1 from '../assets/doll-1.JPG';
import doll2 from '../assets/doll-2.JPG';
import doll3 from '../assets/doll-3.JPG';
import doll4 from '../assets/doll-4.JPG';
import doll5 from '../assets/doll-5.JPG';
import doll6 from '../assets/doll-6.JPG';
import doll7 from '../assets/doll-7.JPG';
import doll8 from '../assets/doll-8.JPG';
import Pages from "./Pages"

const products = [
    {
        id: 1,
        name: 'Manyasha',
        description: 'Some description',
        price: '$48',
        imageSrc: doll1,
        href: '#',
    },
    {
        id: 2,
        name: 'Paulina',
        description: 'Some description',
        price: '$35',
        imageSrc: doll2,
        href: '#',
    },
    {
        id: 3,
        name: 'Angelica',
        description: 'Some description',
        price: '$89',
        imageSrc: doll3,
        href: '#',
    },
    {
        id: 4,
        name: 'Olechka',
        description: 'Some description',
        price: '$35',
        imageSrc: doll4,
        href: '#',
    },
    {
        id: 5,
        name: 'Peppy',
        description: 'Some description',
        price: '$35',
        imageSrc: doll5,
        href: '#',
    },
    {
        id: 6,
        name: 'Polina',
        description: 'Some description',
        price: '$35',
        imageSrc: doll6,
        href: '#',
    },
    {
        id: 7,
        name: 'Margo',
        description: 'Some description',
        price: '$35',
        imageSrc: doll7,
        href: '#',
    },
    {
        id: 8,
        name: 'Marina',
        description: 'Some description',
        price: '$35',
        imageSrc: doll8,
        href: '#',
    },
    // More products...
]

export default function Catalog() {
    return (
        <div className="bg-white">
            <div className="max-w-3xl mx-auto py-16 px-4 sm:py-5 sm:px-6 lg:max-w-7xl lg:px-8">
                <h2 className="sr-only">Products</h2>
                <div
                    className="grid grid-cols-1 gap-y-10 sm:grid-cols-2 gap-x-6 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
                    {products.map((product) => (
                        <a key={product.id} href={product.href} className="group">
                            <div
                                className="w-full aspect-w-1 aspect-h-1 bg-gray-200 rounded-lg overflow-hidden xl:aspect-w-7 xl:aspect-h-8">
                                <img
                                    src={product.imageSrc}
                                    alt={product.description}
                                    className="w-80 h-9 object-center object-cover group-hover:opacity-75"
                                />
                            </div>
                            <h3 className="mt-4 text-sm text-gray-700">{product.name}</h3>
                            <p className="mt-1 text-lg font-medium text-gray-900">{product.price}</p>
                        </a>
                    ))}
                </div>
                <Pages></Pages>
            </div>
        </div>
    )
}
