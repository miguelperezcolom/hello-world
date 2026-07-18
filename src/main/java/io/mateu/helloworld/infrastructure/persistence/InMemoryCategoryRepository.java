package io.mateu.helloworld.infrastructure.persistence;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driven adapter backing the {@link CategoryRepository} port with an in-memory
 * map, seeded with a handful of categories so the product lookup has something
 * to offer out of the box.
 */
@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<CategoryId, Category> store = new ConcurrentHashMap<>();

    @PostConstruct
    void seed() {
        List.of("Electronics", "Books", "Clothing", "Home & Garden", "Toys")
                .forEach(name -> save(Category.create(name)));
    }

    @Override
    public void save(Category category) {
        Objects.requireNonNull(category, "category");
        store.put(category.id(), category);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Category> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void deleteById(CategoryId id) {
        store.remove(id);
    }
}
