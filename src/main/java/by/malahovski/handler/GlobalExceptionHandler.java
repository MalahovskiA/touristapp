package by.malahovski.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global exception handler for the application.
 * This class is responsible for handling exceptions thrown by controllers throughout the application.
 * It provides a centralized way to handle errors and return appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions not specifically handled by other methods.
     *
     * @param ex The exception that was thrown
     * @return ResponseEntity containing the exception message and a status of 404 (Not Found)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles EntityNotFoundException specifically.
     *
     * @param ex The EntityNotFoundException that was thrown
     * @return ResponseEntity containing the exception message and a status of 204 (No Content)
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }
}
