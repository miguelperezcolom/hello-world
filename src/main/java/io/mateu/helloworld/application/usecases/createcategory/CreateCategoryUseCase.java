package io.mateu.helloworld.application.usecases.createcategory;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Registers a new category. */
@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categories;

    public CategoryId handle(CreateCategoryCommand command) {
        Category category = Category.create(command.name());
        categories.save(category);
        return category.id();
    }
}
