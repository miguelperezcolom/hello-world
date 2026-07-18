package io.mateu.helloworld.application.usecases.deactivateproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Marks an existing product as inactive. */
@Service
@RequiredArgsConstructor
public class DeactivateProductUseCase {

    private final ProductRepository products;

    public void handle(DeactivateProductCommand command) {
        ProductId id = ProductId.of(command.productId());
        Product product = products.findById(id)
                .orElseThrow(() -> new DomainException("Product not found: " + id));
        product.deactivate();
        products.save(product);
    }
}
