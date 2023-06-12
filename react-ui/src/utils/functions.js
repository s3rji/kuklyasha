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
            return "gray-500"
        case "CONFIRMED":
            return "cyan-400"
        case "DELIVERY":
            return "amber-400"
        case "DONE":
            return "green-400"
        default:
            return "red-500"
    }
}

export function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default status