package io.mateu.helloworld.domain.aggregates.category;

import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import java.util.Objects;

/**
 * Category aggregate root — a second aggregate, related to Product only by id
 * (a Product references a category through its {@code categoryId}). Aggregates
 * reference each other by identity, never by object graph.
 */
public class Category {

    private final CategoryId id;
    private String name;

    private Category(CategoryId id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = normalisedName(name);
    }

    /** Factory for brand-new categories: fresh identity. */
    public static Category create(String name) {
        return new Category(CategoryId.generate(), name);
    }

    /** Rebuilds an existing category from persisted state. */
    public static Category reconstitute(CategoryId id, String name) {
        return new Category(id, name);
    }

    public void rename(String newName) {
        this.name = normalisedName(newName);
    }

    private static String normalisedName(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Category name must not be blank");
        }
        return name.trim();
    }

    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof Category other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
