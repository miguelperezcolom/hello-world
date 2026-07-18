package io.mateu.helloworld.product.application.port.in;

import io.mateu.helloworld.product.domain.model.Product;
import io.mateu.helloworld.product.domain.model.ProductId;

import java.util.List;
import java.util.Optional;

/** Driving (inbound) port: query the product catalog. */
public interface GetProductsUseCase {

    List<Product> findAll();

    Optional<Product> findById(ProductId id);
}
