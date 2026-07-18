package io.mateu.helloworld.infrastructure.queries.getproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproduct.GetProductQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** Infrastructure implementation of the {@link GetProductQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemoryGetProductQuery implements GetProductQuery {

    private final ProductRepository products;

    @Override
    public Optional<Product> handle(ProductId id) {
        return products.findById(id);
    }
}
