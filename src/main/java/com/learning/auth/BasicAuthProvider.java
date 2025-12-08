package com.learning.auth;


import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;


@Singleton
@Requires(property = "micronaut.security.enabled", value = "true")
public class BasicAuthProvider implements AuthenticationProvider<Object, String, String> {

    @Override
    public @NonNull AuthenticationResponse authenticate(@Nullable Object requestContext, @NonNull AuthenticationRequest<String, String> authRequest) {
        String username = authRequest.getIdentity();
        String password = authRequest.getSecret();

        // Replace with your admin & welcome logic
        if ("".equals(username) && "".equals(password)) {
            return AuthenticationResponse.success(username, java.util.Collections.emptyList());
        } else {
            return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
        }
    }
}
