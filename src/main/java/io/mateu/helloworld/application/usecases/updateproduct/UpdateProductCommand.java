package io.mateu.helloworld.application.usecases.updateproduct;

import java.math.BigDecimal;

/** Input for the update-product use case. */
public record UpdateProductCommand(
        String productId,
        String name,
        String description,
        BigDecimal price,
        String currency) {
}
