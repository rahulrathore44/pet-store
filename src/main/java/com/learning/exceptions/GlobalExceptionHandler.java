package com.learning.exceptions;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Throwable, HttpResponse<?>> {


    @Override
    public HttpResponse<?> handle(HttpRequest request, Throwable exception) {
        return HttpResponse.serverError(new ErrorResponse(exception.getMessage()));
    }
}
