package io.mateu.helloworld.product.application.port.in;

import io.mateu.helloworld.product.domain.model.ProductId;

import java.math.BigDecimal;

/** Driving (inbound) port: register a new product in the catalog. */
public interface CreateProductUseCase {

    ProductId createProduct(CreateProductCommand command);

    record CreateProductCommand(
            String sku,
            String name,
            String description,
            BigDecimal price,
            String currency) {
    }
}
