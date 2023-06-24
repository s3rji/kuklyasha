export function status(status) {
    switch (status) {
        case "NEW":
            return "новый"
        case "CONFIRMED":
            return "подтвержден"
        case "DELIVERY":
            return "доставка"
        case "DONE":
            return "выполнен"
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