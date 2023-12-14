package ru.serji.notificationkuklyasha;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.context.config.annotation.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.cloud.stream.messaging.*;

@SpringBootApplication
@EnableBinding(Sink.class)
@RefreshScope
public class NotificationKuklyashaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationKuklyashaApplication.class, args);
    }

}
