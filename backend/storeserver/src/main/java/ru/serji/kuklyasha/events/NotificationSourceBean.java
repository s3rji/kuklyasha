package ru.serji.kuklyasha.events;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.messaging.*;
import org.springframework.messaging.support.*;
import org.springframework.stereotype.*;

import java.time.*;

@Component
@Log4j2
public class NotificationSourceBean {

    private final Source source;

    @Autowired
    public NotificationSourceBean(Source source) {
        this.source = source;
    }

    public void publishNotification(String action, String userName, String userEmail, int orderId, LocalDateTime orderDate,
                                    String orderStatus) {
        log.debug("Sending kafka message {} for order: {}", action, orderId);
        OrderChangeModel change = new OrderChangeModel(action, userName, userEmail, orderId, orderDate, orderStatus);
        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
