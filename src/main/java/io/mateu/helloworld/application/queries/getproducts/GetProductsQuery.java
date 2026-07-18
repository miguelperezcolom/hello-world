package io.mateu.helloworld.application.queries.getproducts;

import io.mateu.helloworld.domain.aggregates.product.Product;

import java.util.List;

/**
 * Read side: lists every product in the catalog. Declared in the application
 * layer, implemented by infrastructure.
 */
public interface GetProductsQuery {

    List<Product> handle();
}
