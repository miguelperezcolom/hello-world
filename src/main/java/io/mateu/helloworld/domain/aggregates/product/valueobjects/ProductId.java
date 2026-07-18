package io.mateu.helloworld.domain.aggregates.product.valueobjects;

import io.mateu.helloworld.domain.DomainException;

import java.util.UUID;

/**
 * Identity of a Product. Value object: two ids are equal iff they wrap the
 * same UUID.
 */
public record ProductId(UUID value) {

    public ProductId {
        if (value == null) {
            throw new DomainException("ProductId must not be null");
        }
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(String value) {
        try {
            return new ProductId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainException("Invalid ProductId: " + value);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
