package io.mateu.helloworld.infrastructure.queries.getcategory;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.application.queries.getcategory.GetCategoryQuery;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** Infrastructure implementation of the {@link GetCategoryQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemoryGetCategoryQuery implements GetCategoryQuery {

    private final CategoryRepository categories;

    @Override
    public Optional<Category> handle(CategoryId id) {
        return categories.findById(id);
    }
}
