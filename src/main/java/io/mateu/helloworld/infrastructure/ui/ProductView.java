package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.EditableOnlyWhenCreating;
import io.mateu.uidl.annotations.HiddenInCreate;
import io.mateu.uidl.annotations.Multiline;
import io.mateu.uidl.annotations.ReadOnly;
import io.mateu.uidl.interfaces.Identifiable;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * Read/write model exposed to Mateu's CRUD. It is a UI-facing DTO — not the
 * Product aggregate — so the domain never leaks into the framework. The record
 * accessor {@code id()} satisfies {@link Identifiable}.
 */
public record ProductView(
        @ReadOnly @HiddenInCreate String id,
        @EditableOnlyWhenCreating String sku,
        @NotEmpty
        String name,
        @Multiline String description,
        BigDecimal price,
        String currency,
        @ReadOnly boolean active) implements Identifiable {

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return name != null ? name : "New product";
    }
}
