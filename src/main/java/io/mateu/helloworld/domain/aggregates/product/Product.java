package io.mateu.helloworld.domain.aggregates.product;

import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Sku;

import java.util.Objects;

/**
 * Product aggregate root. All state changes go through behaviour methods that
 * keep the invariants intact; identity is defined solely by {@link ProductId}.
 */
public class Product {

    private final ProductId id;
    private final Sku sku;
    private String name;
    private String description;
    private Money price;
    private boolean active;
    private String categoryId;

    private Product(ProductId id, Sku sku, String name, String description, Money price, boolean active) {
        this.id = Objects.requireNonNull(id, "id");
        this.sku = Objects.requireNonNull(sku, "sku");
        this.price = Objects.requireNonNull(price, "price");
        this.name = normalisedName(name);
        this.description = normalisedDescription(description);
        this.active = active;
    }

    /** Factory for brand-new products: fresh identity, active by default. */
    public static Product create(Sku sku, String name, String description, Money price) {
        return new Product(ProductId.generate(), sku, name, description, price, true);
    }

    /** Rebuilds an existing product from persisted state, without re-generating identity. */
    public static Product reconstitute(ProductId id, Sku sku, String name, String description,
                                       Money price, boolean active) {
        return new Product(id, sku, name, description, price, active);
    }

    public void rename(String newName) {
        this.name = normalisedName(newName);
    }

    public void changePrice(Money newPrice) {
        this.price = Objects.requireNonNull(newPrice, "price");
    }

    public void updateDescription(String newDescription) {
        this.description = normalisedDescription(newDescription);
    }

    /**
     * Associates this product with a category (referenced by id) or clears it
     * when {@code null}. Aggregates reference each other by identity only.
     */
    public void assignCategory(String categoryId) {
        this.categoryId = (categoryId == null || categoryId.isBlank()) ? null : categoryId;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private static String normalisedName(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Product name must not be blank");
        }
        return name.trim();
    }

    private static String normalisedDescription(String description) {
        return description == null ? "" : description.trim();
    }

    public ProductId id() {
        return id;
    }

    public Sku sku() {
        return sku;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Money price() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    /** Id of the category this product belongs to, or {@code null} if unassigned. */
    public String categoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof Product other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Product{id=%s, sku=%s, name='%s', price=%s, active=%s}"
                .formatted(id, sku, name, price, active);
    }
}
