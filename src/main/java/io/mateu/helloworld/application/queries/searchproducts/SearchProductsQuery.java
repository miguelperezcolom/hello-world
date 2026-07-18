package io.mateu.helloworld.application.queries.searchproducts;

import io.mateu.helloworld.domain.aggregates.product.Product;

import java.util.List;

/**
 * Read side: lists the products matching a free-text term (over name/SKU) and,
 * optionally, only the active ones. Declared in the application layer,
 * implemented by infrastructure.
 */
public interface SearchProductsQuery {

    List<Product> handle(String text, boolean onlyActive);
}
