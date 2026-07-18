package io.mateu.helloworld.application.usecases.createproduct;

import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.infrastructure.persistence.InMemoryProductRepository;
import io.mateu.helloworld.infrastructure.queries.getproduct.InMemoryGetProductQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateProductUseCaseTest {

    private InMemoryProductRepository repository;
    private CreateProductUseCase createProduct;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        createProduct = new CreateProductUseCase(repository);
    }

    @Test
    void createsAndPersistsProduct() {
        ProductId id = createProduct.handle(
                new CreateProductCommand("sku-1", "Keyboard", "Mechanical", new BigDecimal("49.90"), "EUR", null));

        Product stored = new InMemoryGetProductQuery(repository).handle(id).orElseThrow();
        assertEquals("SKU-1", stored.sku().value());
    }

    @Test
    void rejectsDuplicateSku() {
        createProduct.handle(new CreateProductCommand("sku-1", "Keyboard", "", new BigDecimal("49.90"), "EUR", null));

        assertThrows(DomainException.class, () -> createProduct.handle(
                new CreateProductCommand("SKU-1", "Another", "", new BigDecimal("10"), "EUR", null)));
    }
}
