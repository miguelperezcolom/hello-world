package io.mateu.helloworld.application.queries.getproduct;

import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import java.util.Optional;

/**
 * Read side: fetches a single product by its identity. Declared in the
 * application layer, implemented by infrastructure.
 */
public interface GetProductQuery {

    Optional<Product> handle(ProductId id);
}
