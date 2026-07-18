package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.queries.getcategories.GetCategoriesQuery;
import io.mateu.helloworld.application.queries.getcategory.GetCategoryQuery;
import io.mateu.helloworld.application.usecases.createcategory.CreateCategoryCommand;
import io.mateu.helloworld.application.usecases.createcategory.CreateCategoryUseCase;
import io.mateu.helloworld.application.usecases.deletecategory.DeleteCategoryCommand;
import io.mateu.helloworld.application.usecases.deletecategory.DeleteCategoryUseCase;
import io.mateu.helloworld.application.usecases.updatecategory.UpdateCategoryCommand;
import io.mateu.helloworld.application.usecases.updatecategory.UpdateCategoryUseCase;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;
import io.mateu.uidl.interfaces.CrudRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Inbound adapter: bridges Mateu's {@link CrudRepository} contract to the
 * category use cases and queries, mapping between {@link CategoryView} and the
 * {@link Category} aggregate. Every mutation goes through a use case — the same
 * pattern as {@link ProductCrudRepository}.
 */
@Component
@RequiredArgsConstructor
public class CategoryCrudRepository implements CrudRepository<CategoryView> {

    private final CreateCategoryUseCase createCategory;
    private final UpdateCategoryUseCase updateCategory;
    private final DeleteCategoryUseCase deleteCategory;
    private final GetCategoriesQuery getCategories;
    private final GetCategoryQuery getCategory;

    @Override
    public List<CategoryView> findAll() {
        return getCategories.handle("").stream().map(CategoryCrudRepository::toView).toList();
    }

    @Override
    public Optional<CategoryView> findById(String id) {
        return getCategory.handle(CategoryId.of(id)).map(CategoryCrudRepository::toView);
    }

    @Override
    public String save(CategoryView view) {
        if (view.id() == null || view.id().isBlank()) {
            return createCategory.handle(new CreateCategoryCommand(view.name())).toString();
        }
        updateCategory.handle(new UpdateCategoryCommand(view.id(), view.name()));
        return view.id();
    }

    @Override
    public void deleteAllById(List<String> ids) {
        ids.forEach(id -> deleteCategory.handle(new DeleteCategoryCommand(id)));
    }

    private static CategoryView toView(Category category) {
        return new CategoryView(category.id().toString(), category.name());
    }
}
