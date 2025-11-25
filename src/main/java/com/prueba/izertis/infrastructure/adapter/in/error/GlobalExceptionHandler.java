package com.prueba.izertis.infrastructure.adapter.in.error;

import com.prueba.izertis.application.exception.PriceNotFoundException;
import com.prueba.izertis.application.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

     //404 NOT FOUND
    @ExceptionHandler(PriceNotFoundException.class)
    public Mono<ResponseEntity<String>> handlePriceNotFound(PriceNotFoundException ex) {
        log.warn("No price found: {}", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

     //400 BAD REQUEST
    @ExceptionHandler(DecodingException.class)
    public Mono<ResponseEntity<String>> handleDecode(DecodingException ex) {
        log.warn("DecoderException: {}", ex.getMessage());
        return badRequest("Invalid JSON body: " + ex.getMessage());
    }

    //403 FORBIDDEN
    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<String>> handleForbidden(AccessDeniedException ex) {
        log.warn("AccessDeniedException");
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied"));
    }

    //500 INTERNAL SERVER ERROR
    @ExceptionHandler(IOException.class)
    public Mono<ResponseEntity<String>> handleIO(IOException ex) {
        log.error("IOException", ex);
        return internal("Input/output error: " + ex.getMessage());
    }

    //503 SERVICE UNAVAILABLE
    @ExceptionHandler({ServiceUnavailableException.class, CallNotPermittedException.class})
    public Mono<ResponseEntity<String>> handleServiceUnavailable(Exception ex) {
        log.warn("Service unavailable", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service unavailable, please try again later"));
    }

     //Helpers
    private Mono<ResponseEntity<String>> badRequest(String body) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    private Mono<ResponseEntity<String>> internal(String body) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
}
