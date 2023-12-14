package ru.serji.kuklyasha.model;

public enum StatusType {

    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    DELIVERY("Доставка"),
    DONE("Выполнен");

    private final String description;

    StatusType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}