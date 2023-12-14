package ru.serji.kuklyashaconfig;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.config.server.*;

@SpringBootApplication
@EnableConfigServer
public class KuklyashaConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuklyashaConfigApplication.class, args);
    }

}
