export function status(status) {
    switch (status) {
        case "NEW":
            return "Новый"
        case "CONFIRMED":
            return "Подтвержден"
        case "DELIVERY":
            return "Доставка"
        case "DONE":
            return "Выполнен"
        default:
            return ""
    }
}

export function statusAsColor(status) {
    switch (status) {
        case "NEW":
            return "bg-gray-500"
        case "CONFIRMED":
            return "bg-cyan-400"
        case "DELIVERY":
            return "bg-amber-400"
        case "DONE":
            return "bg-green-400"
        default:
            return "bg-red-500"
    }
}

export function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default status