package ru.serji.kuklyasha;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.stream.annotation.*;
import org.springframework.cloud.stream.messaging.*;

@SpringBootApplication
@EnableBinding(Source.class)
public class KuklyashaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuklyashaApplication.class, args);
    }

}
