package io.mateu.helloworld.application.usecases.deleteproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Removes a product from the catalog. */
@Service
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final ProductRepository products;

    public void handle(DeleteProductCommand command) {
        products.deleteById(ProductId.of(command.productId()));
    }
}
