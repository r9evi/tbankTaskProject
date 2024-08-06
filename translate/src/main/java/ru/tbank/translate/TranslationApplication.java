package ru.tbank.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * This is the main class of the Spring Boot application.
 * It is annotated with @SpringBootApplication and @EnableAsync,
 * which means that this application is a Spring Boot application, and it supports asynchronous execution.
 */
@SpringBootApplication
@EnableAsync
public class TranslationApplication {

    /**
     * The main method of the application.
     * It starts the Spring application by calling SpringApplication.run(TranslationApplication.class, args).
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TranslationApplication.class, args);
    }

}
