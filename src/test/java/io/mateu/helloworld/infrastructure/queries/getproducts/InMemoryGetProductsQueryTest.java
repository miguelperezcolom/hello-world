package io.mateu.helloworld.infrastructure.queries.getproducts;

import io.mateu.helloworld.application.queries.getproducts.GetProductsQuery;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductCommand;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductUseCase;
import io.mateu.helloworld.infrastructure.persistence.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryGetProductsQueryTest {

    @Test
    void listsAllProducts() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        CreateProductUseCase createProduct = new CreateProductUseCase(repository);
        createProduct.handle(new CreateProductCommand("sku-1", "Keyboard", "", new BigDecimal("49.90"), "EUR"));
        createProduct.handle(new CreateProductCommand("sku-2", "Mouse", "", new BigDecimal("19.90"), "EUR"));

        GetProductsQuery getProducts = new InMemoryGetProductsQuery(repository);

        assertEquals(2, getProducts.handle().size());
    }

    @Test
    void returnsEmptyWhenNoProducts() {
        GetProductsQuery getProducts = new InMemoryGetProductsQuery(new InMemoryProductRepository());

        assertTrue(getProducts.handle().isEmpty());
    }
}
