package io.mateu.helloworld.application.usecases.updateproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import org.springframework.stereotype.Service;

import java.util.Objects;

/** Updates the editable details of an existing product (name, description, price). */
@Service
public class UpdateProductUseCase {

    private final ProductRepository products;

    public UpdateProductUseCase(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    public void handle(UpdateProductCommand command) {
        ProductId id = ProductId.of(command.productId());
        Product product = products.findById(id)
                .orElseThrow(() -> new DomainException("Product not found: " + id));
        product.rename(command.name());
        product.updateDescription(command.description());
        product.changePrice(Money.of(command.price(), command.currency()));
        products.save(product);
    }
}
