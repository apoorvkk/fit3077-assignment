package edu.monash.it.fit3077.akql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the entry point of the application. It is embedded into a spring boot application so we can setup a server.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(edu.monash.it.fit3077.akql.Application.class, args);
    }
}
