package com.pm.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<Void> handleWebClientException(WebClientResponseException ex, ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(ex.getStatusCode());
        return exchange.getResponse().setComplete();
    }

    @ExceptionHandler(Exception.class)
    public Mono<Void> handleGeneralException(Exception ex, ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return exchange.getResponse().setComplete();
    }
}
