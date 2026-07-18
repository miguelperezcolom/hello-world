package io.mateu.helloworld.product.domain;

/**
 * Raised when a domain invariant is violated. Lives in the core so the domain
 * never has to depend on framework-specific exception types.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
