package io.mateu.helloworld.infrastructure.queries.getproducts;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproducts.GetProductsQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** Infrastructure implementation of the {@link GetProductsQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemoryGetProductsQuery implements GetProductsQuery {

    private final ProductRepository products;

    @Override
    public List<Product> handle() {
        return products.findAll();
    }
}
