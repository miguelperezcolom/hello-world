package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.EditableOnlyWhenCreating;
import io.mateu.uidl.annotations.HiddenInCreate;
import io.mateu.uidl.annotations.Multiline;
import io.mateu.uidl.annotations.ReadOnly;
import io.mateu.uidl.interfaces.Identifiable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Read/write model exposed to Mateu's CRUD. It is a UI-facing DTO — not the
 * Product aggregate — so the domain never leaks into the framework. The record
 * accessor {@code id()} satisfies {@link Identifiable}.
 *
 * <p>The Jakarta Bean Validation constraints below are read by Mateu and shown
 * as form validations for early feedback. They are UX-level checks: the real
 * business invariants still live in the domain (Sku, Money, Product), which
 * validates again regardless of what the UI sends.
 */
public record ProductView(
        @ReadOnly @HiddenInCreate String id,
        @EditableOnlyWhenCreating @NotEmpty @Size(max = 40) String sku,
        @NotEmpty @Size(max = 100) String name,
        @Multiline @Size(max = 500) String description,
        @NotNull @Min(0) BigDecimal price,
        @NotEmpty @Size(min = 3, max = 3) String currency,
        @ReadOnly boolean active) implements Identifiable {

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return name != null ? name : "New product";
    }
}
