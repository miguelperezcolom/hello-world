package io.mateu.helloworld.application.out;

import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Sku;

import java.util.List;
import java.util.Optional;

/**
 * Driven (outbound) port. Use cases depend on this abstraction; infrastructure
 * provides the adapter.
 */
public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    boolean existsBySku(Sku sku);

    void deleteById(ProductId id);
}
