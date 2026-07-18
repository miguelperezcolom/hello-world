package io.mateu.helloworld.application.usecases.createproduct;

import java.math.BigDecimal;

/** Input for the create-product use case. */
public record CreateProductCommand(
        String sku,
        String name,
        String description,
        BigDecimal price,
        String currency) {
}
