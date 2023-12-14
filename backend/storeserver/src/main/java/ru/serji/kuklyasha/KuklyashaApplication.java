package ru.serji.kuklyasha;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.context.config.annotation.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.cloud.stream.messaging.*;

@SpringBootApplication
@EnableBinding(Source.class)
@RefreshScope
public class KuklyashaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuklyashaApplication.class, args);
    }

}
