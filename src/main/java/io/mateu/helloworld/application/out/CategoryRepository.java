package io.mateu.helloworld.application.out;

import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import java.util.List;
import java.util.Optional;

/**
 * Driven (outbound) port for categories. Use cases and queries depend on this
 * abstraction; infrastructure provides the adapter.
 */
public interface CategoryRepository {

    void save(Category category);

    Optional<Category> findById(CategoryId id);

    List<Category> findAll();

    void deleteById(CategoryId id);
}
