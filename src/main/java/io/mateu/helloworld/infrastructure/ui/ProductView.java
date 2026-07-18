package io.mateu.helloworld.infrastructure.ui;

import io.mateu.uidl.annotations.EditableOnlyWhenCreating;
import io.mateu.uidl.annotations.HiddenInCreate;
import io.mateu.uidl.annotations.ReadOnly;
import io.mateu.uidl.annotations.Section;
import io.mateu.uidl.annotations.Stereotype;
import io.mateu.uidl.annotations.Tab;
import io.mateu.uidl.data.FieldStereotype;
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
 * <p>Presentation is declared with annotations, keeping the field order (the
 * record's canonical constructor) stable for {@code ProductCrudRepository}:
 * <ul>
 *   <li>{@code @Tab} / {@code @Section} group the fields into tabs and boxes;
 *   <li>{@code @Stereotype} states the <em>presentation intent</em> (money,
 *       textarea, toggle) and lets Mateu pick the control per design system;
 *   <li>the Jakarta Bean Validation constraints are UX-level checks — the real
 *       business invariants still live in the domain (Sku, Money, Product).
 * </ul>
 */
public record ProductView(
        @Tab("General") @Section("Identity")
        @ReadOnly @HiddenInCreate String id,
        @EditableOnlyWhenCreating @NotEmpty @Size(max = 40) String sku,
        @NotEmpty @Size(max = 100) String name,
        @Section("Description")
        @Stereotype(FieldStereotype.textarea) @Size(max = 500) String description,
        @Section("Pricing")
        @Stereotype(FieldStereotype.money) @NotNull @Min(0) BigDecimal price,
        @NotEmpty @Size(min = 3, max = 3) String currency,
        @Tab("Status")
        @Stereotype(FieldStereotype.toggle) @ReadOnly boolean active) implements Identifiable {

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return name != null ? name : "New product";
    }
}
