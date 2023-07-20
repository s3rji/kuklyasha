import {ChevronLeftIcon, ChevronRightIcon} from '@heroicons/react/solid'
import {useContext} from "react";
import {Context} from "../../index";
import {NavLink} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {getLimitWithUserAndSort, getLimitWithUserByFilter} from "../../http/orderApi";

const Pages = observer(() => {
    const {order} = useContext(Context)
    const from = order.page * order.limit + 1
    const to = order.page * order.limit + order.orders.length
    const totalPages = Math.ceil(order.total / order.limit)
    const pages = []
    for (let i = 0; i < totalPages; i++) {
        pages.push(i)
    }

    const choosePage = (page) => {
        order.setPage(page)
        if (order.search) {
            getLimitWithUserByFilter(order.page, order.limit, order.sort, order.sortDirection, order.filter, order.search)
                .then(data => {
                    order.setOrders(data.content)
                    order.setTotal(data.total)
                })
        } else {
            getLimitWithUserAndSort(order.page, order.limit, order.sort, order.sortDirection)
                .then(data => {
                    order.setOrders(data.content)
                    order.setTotal(data.total)
                })
        }
    }

    return (
        <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-500 sm:px-6">
            <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                <div>
                    {order.total !== 0 && <div className="text-sm text-gray-700">
                        Показано с <span className="font-medium">{from}</span> по <span
                        className="font-medium">{to}</span> из {' '}
                        <span className="font-medium">{order.total}</span> позиций
                    </div>}
                </div>
                <div>
                    <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                        <NavLink
                            to={"#"}

                            onClick={() => choosePage(order.page > 0 ? order.page - 1 : 0)}
                            className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                        >
                            <span className="sr-only">Previous</span>
                            <ChevronLeftIcon className="h-5 w-5" aria-hidden="true"/>
                        </NavLink>
                        {pages.map(page =>
                            <NavLink
                                to={"#"}
                                key={page}
                                onClick={() => choosePage(page)}
                                aria-current="page"
                                className={order.page === page ?
                                    "z-10 bg-indigo-50 border-indigo-500 text-indigo-600 relative inline-flex items-center px-4 py-2 border text-sm font-medium" :
                                    "bg-white border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium"}
                            >
                                {page + 1}
                            </NavLink>
                        )}
                        <NavLink
                            to={"#"}
                            onClick={() => choosePage(order.page < pages.length - 1 ? order.page + 1 : pages.length - 1)}
                            className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                        >
                            <span className="sr-only">Next</span>
                            <ChevronRightIcon className="h-5 w-5" aria-hidden="true"/>
                        </NavLink>
                    </nav>
                </div>
            </div>
        </div>
    )
});

export default Pages;