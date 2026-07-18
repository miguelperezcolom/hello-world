package io.mateu.helloworld.infrastructure.queries.getproducts;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproducts.GetProductsQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/** Infrastructure implementation of the {@link GetProductsQuery} read side. */
@Component
public class InMemoryGetProductsQuery implements GetProductsQuery {

    private final ProductRepository products;

    public InMemoryGetProductsQuery(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    @Override
    public List<Product> handle() {
        return products.findAll();
    }
}
