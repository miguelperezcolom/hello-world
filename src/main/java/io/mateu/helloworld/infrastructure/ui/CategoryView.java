package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.HiddenInCreate;
import io.mateu.uidl.annotations.ReadOnly;
import io.mateu.uidl.interfaces.Identifiable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * UI-facing DTO for the category CRUD — the read/write row shown in the listing
 * and form. Not the {@link io.mateu.helloworld.domain.aggregates.category.Category}
 * aggregate; the record accessor {@code id()} satisfies {@link Identifiable}.
 */
public record CategoryView(
        @ReadOnly @HiddenInCreate String id,
        @NotEmpty @Size(max = 60) String name) implements Identifiable {

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return name != null ? name : "New category";
    }
}
