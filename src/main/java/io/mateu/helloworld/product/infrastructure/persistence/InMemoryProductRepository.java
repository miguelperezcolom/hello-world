package io.mateu.helloworld.product.infrastructure.persistence;

import io.mateu.helloworld.product.application.port.out.ProductRepository;
import io.mateu.helloworld.product.domain.model.Product;
import io.mateu.helloworld.product.domain.model.ProductId;
import io.mateu.helloworld.product.domain.model.Sku;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driven adapter backing the {@link ProductRepository} port with an in-memory
 * map. No external infrastructure required — ideal for trying things out.
 */
public class InMemoryProductRepository implements ProductRepository {

    private final Map<ProductId, Product> store = new ConcurrentHashMap<>();

    @Override
    public void save(Product product) {
        Objects.requireNonNull(product, "product");
        store.put(product.id(), product);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public boolean existsBySku(Sku sku) {
        return store.values().stream().anyMatch(p -> p.sku().equals(sku));
    }

    @Override
    public void deleteById(ProductId id) {
        store.remove(id);
    }
}
