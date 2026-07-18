package io.mateu.helloworld.domain;

/**
 * Raised when a domain invariant is violated. Lives at the root of the domain
 * so it can be shared across aggregates without any framework dependency.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
