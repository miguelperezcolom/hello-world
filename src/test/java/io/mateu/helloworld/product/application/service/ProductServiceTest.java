package io.mateu.helloworld.product.application.service;

import io.mateu.helloworld.product.application.port.in.ChangeProductPriceUseCase.ChangeProductPriceCommand;
import io.mateu.helloworld.product.application.port.in.CreateProductUseCase.CreateProductCommand;
import io.mateu.helloworld.product.domain.DomainException;
import io.mateu.helloworld.product.domain.model.Money;
import io.mateu.helloworld.product.domain.model.Product;
import io.mateu.helloworld.product.domain.model.ProductId;
import io.mateu.helloworld.product.infrastructure.persistence.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new InMemoryProductRepository());
    }

    @Test
    void createsAndPersistsProduct() {
        ProductId id = service.createProduct(
                new CreateProductCommand("sku-1", "Keyboard", "Mechanical", new BigDecimal("49.90"), "EUR"));

        Product stored = service.findById(id).orElseThrow();
        assertEquals("SKU-1", stored.sku().value());
        assertEquals(1, service.findAll().size());
    }

    @Test
    void rejectsDuplicateSku() {
        service.createProduct(new CreateProductCommand("sku-1", "Keyboard", "", new BigDecimal("49.90"), "EUR"));

        assertThrows(DomainException.class, () -> service.createProduct(
                new CreateProductCommand("SKU-1", "Another", "", new BigDecimal("10"), "EUR")));
    }

    @Test
    void changesPriceOfExistingProduct() {
        ProductId id = service.createProduct(
                new CreateProductCommand("sku-1", "Keyboard", "", new BigDecimal("49.90"), "EUR"));

        service.changeProductPrice(new ChangeProductPriceCommand(id.toString(), new BigDecimal("59.90"), "EUR"));

        assertEquals(Money.of(new BigDecimal("59.90"), "EUR"), service.findById(id).orElseThrow().price());
    }

    @Test
    void failsToChangePriceOfUnknownProduct() {
        assertThrows(DomainException.class, () -> service.changeProductPrice(
                new ChangeProductPriceCommand(ProductId.generate().toString(), new BigDecimal("10"), "EUR")));
    }
}
