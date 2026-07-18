package io.mateu.helloworld.product.application.port.out;

import io.mateu.helloworld.product.domain.model.Product;
import io.mateu.helloworld.product.domain.model.ProductId;
import io.mateu.helloworld.product.domain.model.Sku;

import java.util.List;
import java.util.Optional;

/**
 * Driven (outbound) port. The application core depends on this abstraction;
 * infrastructure provides the adapter.
 */
public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    boolean existsBySku(Sku sku);

    void deleteById(ProductId id);
}
