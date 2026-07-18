package io.mateu.helloworld.application.usecases.changeproductprice;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Changes the price of an existing product. */
@Service
@RequiredArgsConstructor
public class ChangeProductPriceUseCase {

    private final ProductRepository products;

    public void handle(ChangeProductPriceCommand command) {
        ProductId id = ProductId.of(command.productId());
        Product product = products.findById(id)
                .orElseThrow(() -> new DomainException("Product not found: " + id));
        product.changePrice(Money.of(command.newPrice(), command.currency()));
        products.save(product);
    }
}
