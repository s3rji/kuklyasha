package ru.serji.kuklyasha.events;

import lombok.*;

import java.time.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderChangeModel {
    private String action;
    private String userName;
    private String userEmail;
    private int orderId;
    private LocalDateTime orderDate;
    private String orderStatus;
}
