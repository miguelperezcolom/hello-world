package io.mateu.helloworld.product.application.service;

import io.mateu.helloworld.product.application.port.in.ChangeProductPriceUseCase;
import io.mateu.helloworld.product.application.port.in.CreateProductUseCase;
import io.mateu.helloworld.product.application.port.in.GetProductsUseCase;
import io.mateu.helloworld.product.application.port.out.ProductRepository;
import io.mateu.helloworld.product.domain.DomainException;
import io.mateu.helloworld.product.domain.model.Money;
import io.mateu.helloworld.product.domain.model.Product;
import io.mateu.helloworld.product.domain.model.ProductId;
import io.mateu.helloworld.product.domain.model.Sku;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Application service orchestrating the product use cases. Free of framework
 * annotations: infrastructure decides how it is wired (see config package).
 */
public class ProductService implements CreateProductUseCase, ChangeProductPriceUseCase, GetProductsUseCase {

    private final ProductRepository products;

    public ProductService(ProductRepository products) {
        this.products = Objects.requireNonNull(products, "products");
    }

    @Override
    public ProductId createProduct(CreateProductCommand command) {
        Sku sku = Sku.of(command.sku());
        if (products.existsBySku(sku)) {
            throw new DomainException("A product with SKU " + sku + " already exists");
        }
        Money price = Money.of(command.price(), command.currency());
        Product product = Product.create(sku, command.name(), command.description(), price);
        products.save(product);
        return product.id();
    }

    @Override
    public void changeProductPrice(ChangeProductPriceCommand command) {
        ProductId id = ProductId.of(command.productId());
        Product product = products.findById(id)
                .orElseThrow(() -> new DomainException("Product not found: " + id));
        product.changePrice(Money.of(command.newPrice(), command.currency()));
        products.save(product);
    }

    @Override
    public List<Product> findAll() {
        return products.findAll();
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return products.findById(id);
    }
}
