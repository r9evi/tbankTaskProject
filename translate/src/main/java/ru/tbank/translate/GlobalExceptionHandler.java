package ru.tbank.translate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/**
 * This class is a global exception handler for the application. It handles exceptions that occur in the application and returns a response entity with an appropriate status code and body.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles HttpClientErrorException exceptions. It checks the status code of the exception and returns a response entity with an appropriate status code and body.
     *
     * @param e The HttpClientErrorException exception that occurred.
     * @return A ResponseEntity with an appropriate status code and body.
     */
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("http 400 " + "Не найдено");
        }
        if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("http 400 " + "Не найден язык исходного сообщения");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("http 500 " + "Ошибка доступа к ресурсу перевода");
    }

    /**
     * This method handles all other exceptions. It returns a response entity with an INTERNAL_SERVER_ERROR status code and a body indicating an error accessing the translation resource.
     *
     * @param e The Exception that occurred.
     * @return A ResponseEntity with an INTERNAL_SERVER_ERROR status code and a body indicating an error accessing the translation resource.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка: " + e.getMessage());
    }
}
