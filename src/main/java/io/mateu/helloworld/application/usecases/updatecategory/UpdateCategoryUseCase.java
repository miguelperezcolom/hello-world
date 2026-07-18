package io.mateu.helloworld.application.usecases.updatecategory;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.domain.DomainException;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Renames an existing category. */
@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {

    private final CategoryRepository categories;

    public void handle(UpdateCategoryCommand command) {
        CategoryId id = CategoryId.of(command.categoryId());
        Category category = categories.findById(id)
                .orElseThrow(() -> new DomainException("Category not found: " + id));
        category.rename(command.name());
        categories.save(category);
    }
}
