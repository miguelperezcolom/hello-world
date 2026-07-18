package io.mateu.helloworld.domain.aggregates.category.valueobjects;

import io.mateu.helloworld.domain.DomainException;

import java.util.UUID;

/**
 * Identity of a Category. Value object: two ids are equal iff they wrap the
 * same UUID.
 */
public record CategoryId(UUID value) {

    public CategoryId {
        if (value == null) {
            throw new DomainException("CategoryId must not be null");
        }
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }

    public static CategoryId of(String value) {
        try {
            return new CategoryId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainException("Invalid CategoryId: " + value);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
