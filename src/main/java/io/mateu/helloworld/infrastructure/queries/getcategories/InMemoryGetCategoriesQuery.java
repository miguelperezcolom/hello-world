package io.mateu.helloworld.infrastructure.queries.getcategories;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.application.queries.getcategories.GetCategoriesQuery;
import io.mateu.helloworld.domain.aggregates.category.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** Infrastructure implementation of the {@link GetCategoriesQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemoryGetCategoriesQuery implements GetCategoriesQuery {

    private final CategoryRepository categories;

    @Override
    public List<Category> handle(String search) {
        String needle = search == null ? "" : search.trim().toLowerCase();
        return categories.findAll().stream()
                .filter(c -> needle.isEmpty() || c.name().toLowerCase().contains(needle))
                .sorted((a, b) -> a.name().compareToIgnoreCase(b.name()))
                .toList();
    }
}
