package ru.serji.notificationkuklyasha.events;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.cloud.stream.messaging.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;

@EnableBinding(Sink.class)
@Log4j2
public class OrderChangeHandler {

    @Value("${mail-template.from}")
    private String from;

    @Value("${mail-template.subject}")
    private String subject;

    @Value("${mail-template.create.body}")
    private String createBody;

    @Value("${mail-template.change.body}")
    private String changeBody;
    private final JavaMailSender emailSender;

    @Autowired
    public OrderChangeHandler(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @StreamListener(Sink.INPUT)
    public void handlerOrderChange(OrderChangeModel change) {
        log.debug("Received {} change event for the order {}", change.getAction(), change);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("s3rg.rov@gmail.com");
//        message.setTo(change.getUserEmail());
        message.setSubject(String.format(subject, change.getOrderId()));

        switch (change.getAction()) {
            case "CREATE" ->
                    message.setText(String.format(createBody, change.getUserName(), change.getOrderId()));
            case "UPDATE" ->
                    message.setText(String.format(changeBody, change.getUserName(), change.getOrderId(), change.getOrderStatus()));
        }
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
