package io.mateu.helloworld.application.usecases.changeproductprice;

import io.mateu.helloworld.application.usecases.createproduct.CreateProductCommand;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductUseCase;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.infrastructure.persistence.InMemoryProductRepository;
import io.mateu.helloworld.infrastructure.queries.getproduct.InMemoryGetProductQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangeProductPriceUseCaseTest {

    private InMemoryProductRepository repository;
    private ChangeProductPriceUseCase changePrice;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        changePrice = new ChangeProductPriceUseCase(repository);
    }

    @Test
    void changesPriceOfExistingProduct() {
        ProductId id = new CreateProductUseCase(repository).handle(
                new CreateProductCommand("sku-1", "Keyboard", "", new BigDecimal("49.90"), "EUR"));

        changePrice.handle(new ChangeProductPriceCommand(id.toString(), new BigDecimal("59.90"), "EUR"));

        assertEquals(Money.of(new BigDecimal("59.90"), "EUR"),
                new InMemoryGetProductQuery(repository).handle(id).orElseThrow().price());
    }

    @Test
    void failsToChangePriceOfUnknownProduct() {
        assertThrows(DomainException.class, () -> changePrice.handle(
                new ChangeProductPriceCommand(ProductId.generate().toString(), new BigDecimal("10"), "EUR")));
    }
}
