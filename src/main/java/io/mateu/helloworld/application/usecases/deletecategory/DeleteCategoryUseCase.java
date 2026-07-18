package io.mateu.helloworld.application.usecases.deletecategory;

import io.mateu.helloworld.application.out.CategoryRepository;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Removes a category from the catalog. */
@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCase {

    private final CategoryRepository categories;

    public void handle(DeleteCategoryCommand command) {
        categories.deleteById(CategoryId.of(command.categoryId()));
    }
}
