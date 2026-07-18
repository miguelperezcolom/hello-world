package io.mateu.helloworld.product.domain.model;

import io.mateu.helloworld.product.domain.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {

    private static Money eur(String amount) {
        return Money.of(new BigDecimal(amount), "EUR");
    }

    @Test
    void createsActiveProductWithGeneratedId() {
        Product product = Product.create(Sku.of("sku-1"), "Keyboard", "Mechanical", eur("49.90"));

        assertTrue(product.isActive());
        assertEquals("SKU-1", product.sku().value());
        assertEquals("Keyboard", product.name());
        assertEquals(eur("49.90"), product.price());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(DomainException.class,
                () -> Product.create(Sku.of("sku-1"), "  ", "", eur("10")));
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(DomainException.class, () -> eur("-1"));
    }

    @Test
    void changesPriceAndDeactivates() {
        Product product = Product.create(Sku.of("sku-1"), "Keyboard", "", eur("49.90"));

        product.changePrice(eur("59.90"));
        product.deactivate();

        assertEquals(eur("59.90"), product.price());
        assertFalse(product.isActive());
    }

    @Test
    void identityIsBasedOnId() {
        Sku sku = Sku.of("sku-1");
        Product a = Product.create(sku, "Keyboard", "", eur("49.90"));
        Product reloaded = Product.reconstitute(a.id(), sku, "Renamed", "", eur("10"), false);

        assertEquals(a, reloaded);
        assertEquals(a.hashCode(), reloaded.hashCode());
    }
}
