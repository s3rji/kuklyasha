export const status = (status) => {
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

export default status