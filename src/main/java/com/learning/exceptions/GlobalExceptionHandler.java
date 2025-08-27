package com.learning.exceptions;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Throwable, HttpResponse<?>> {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public HttpResponse<?> handle(HttpRequest request, Throwable exception) {
        logger.error(exception.getMessage(), exception);
        return HttpResponse.serverError(new ErrorResponse(exception.getMessage()));
    }
}
