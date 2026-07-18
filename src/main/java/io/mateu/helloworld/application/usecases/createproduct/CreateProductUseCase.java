package io.mateu.helloworld.application.usecases.createproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Sku;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Registers a new product in the catalog, enforcing SKU uniqueness. */
@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository products;

    public ProductId handle(CreateProductCommand command) {
        Sku sku = Sku.of(command.sku());
        if (products.existsBySku(sku)) {
            throw new DomainException("A product with SKU " + sku + " already exists");
        }
        Money price = Money.of(command.price(), command.currency());
        Product product = Product.create(sku, command.name(), command.description(), price);
        product.assignCategory(command.categoryId());
        products.save(product);
        return product.id();
    }
}
