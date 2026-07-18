package io.mateu.helloworld.infrastructure.queries.searchproducts;

import io.mateu.helloworld.application.queries.searchproducts.SearchProductsQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Sku;
import io.mateu.helloworld.infrastructure.persistence.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemorySearchProductsQueryTest {

    private InMemoryProductRepository repository;
    private SearchProductsQuery search;

    private static Money eur(String amount) {
        return Money.of(new BigDecimal(amount), "EUR");
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        repository.save(Product.create(Sku.of("kbd-1"), "Keyboard", "", eur("49.90")));
        repository.save(Product.create(Sku.of("mouse-1"), "Mouse", "", eur("19.90")));
        repository.save(Product.reconstitute(
                ProductId.generate(), Sku.of("old-1"), "Old keyboard", "", eur("5.00"), false));
        search = new InMemorySearchProductsQuery(repository);
    }

    @Test
    void returnsAllWhenNoFilter() {
        assertEquals(3, search.handle("", false).size());
    }

    @Test
    void filtersByTextOverNameAndSku() {
        assertEquals(2, search.handle("keyboard", false).size()); // "Keyboard" + "Old keyboard"
        assertEquals(1, search.handle("mouse", false).size());
        assertEquals(1, search.handle("kbd", false).size());      // matches SKU
    }

    @Test
    void excludesInactiveWhenOnlyActive() {
        assertEquals(2, search.handle("", true).size());
        assertEquals(1, search.handle("keyboard", true).size());  // the inactive "Old keyboard" is filtered out
    }
}
