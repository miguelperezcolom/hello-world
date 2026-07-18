package io.mateu.helloworld.product.domain.model;

import io.mateu.helloworld.product.domain.DomainException;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Stock Keeping Unit. Normalised to upper case and constrained to alphanumeric
 * characters and dashes so it is safe to use as a business key.
 */
public record Sku(String value) {

    private static final Pattern PATTERN = Pattern.compile("[A-Z0-9]([A-Z0-9-]*[A-Z0-9])?");

    public Sku {
        if (value == null || value.isBlank()) {
            throw new DomainException("SKU must not be blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
        if (!PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid SKU: " + value);
        }
    }

    public static Sku of(String value) {
        return new Sku(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
