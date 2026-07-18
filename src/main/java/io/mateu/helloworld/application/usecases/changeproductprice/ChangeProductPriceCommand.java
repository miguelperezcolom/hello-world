package io.mateu.helloworld.application.usecases.changeproductprice;

import java.math.BigDecimal;

/** Input for the change-product-price use case. */
public record ChangeProductPriceCommand(
        String productId,
        BigDecimal newPrice,
        String currency) {
}
