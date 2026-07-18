package io.mateu.helloworld.product.application.port.in;

import java.math.BigDecimal;

/** Driving (inbound) port: change the price of an existing product. */
public interface ChangeProductPriceUseCase {

    void changeProductPrice(ChangeProductPriceCommand command);

    record ChangeProductPriceCommand(
            String productId,
            BigDecimal newPrice,
            String currency) {
    }
}
