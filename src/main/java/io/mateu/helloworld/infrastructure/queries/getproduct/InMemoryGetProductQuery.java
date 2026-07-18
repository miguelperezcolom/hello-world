package io.mateu.helloworld.infrastructure.queries.getproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproduct.GetProductQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import java.util.Objects;
import java.util.Optional;

/** Infrastructure implementation of the {@link GetProductQuery} read side. */
public class InMemoryGetProductQuery implements GetProductQuery {

    private final ProductRepository products;

    public InMemoryGetProductQuery(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    @Override
    public Optional<Product> handle(ProductId id) {
        return products.findById(id);
    }
}
