package io.mateu.helloworld.application.usecases.deleteproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import java.util.Objects;

/** Removes a product from the catalog. */
public class DeleteProductUseCase {

    private final ProductRepository products;

    public DeleteProductUseCase(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    public void handle(DeleteProductCommand command) {
        products.deleteById(ProductId.of(command.productId()));
    }
}
