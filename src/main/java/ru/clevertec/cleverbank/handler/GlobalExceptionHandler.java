package ru.clevertec.cleverbank.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler on all application
 *
 * @author Konstantin Voytko
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errorResponse = new HashMap<>();

        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error ->
                        String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                .reduce((a, b) -> a + "; " + b)
                .orElse("Undefined error message");

        errorResponse.put("error", HttpStatus.BAD_REQUEST.toString());
        errorResponse.put("message", errorMessage);

        log.warn(exception.getMessage(), exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.NOT_FOUND.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        log.warn(exception.getMessage(), exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleServerSideErrorException(Exception exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
