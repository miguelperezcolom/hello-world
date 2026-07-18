package io.mateu.helloworld.application.usecases.createproduct;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Money;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.Sku;

import java.util.Objects;

/** Registers a new product in the catalog, enforcing SKU uniqueness. */
public class CreateProductUseCase {

    private final ProductRepository products;

    public CreateProductUseCase(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    public ProductId handle(CreateProductCommand command) {
        Sku sku = Sku.of(command.sku());
        if (products.existsBySku(sku)) {
            throw new DomainException("A product with SKU " + sku + " already exists");
        }
        Money price = Money.of(command.price(), command.currency());
        Product product = Product.create(sku, command.name(), command.description(), price);
        products.save(product);
        return product.id();
    }
}
