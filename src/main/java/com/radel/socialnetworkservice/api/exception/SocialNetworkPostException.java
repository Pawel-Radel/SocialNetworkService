package com.radel.socialnetworkservice.api.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class SocialNetworkPostException extends ResponseStatusException {

    public SocialNetworkPostException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public static SocialNetworkPostException postNotFound(Long id) {
        return new SocialNetworkPostException(NOT_FOUND, String.format("Social network post with %s not found", id));
    }
}
