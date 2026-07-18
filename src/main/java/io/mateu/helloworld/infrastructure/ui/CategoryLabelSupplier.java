package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.queries.getcategory.GetCategoryQuery;
import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;
import io.mateu.uidl.interfaces.HttpRequest;
import io.mateu.uidl.interfaces.LookupLabelSupplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Resolves a stored category id back to its display label. Mateu calls this when
 * loading an existing product so the lookup shows the category name, not the raw id.
 */
@Service
@RequiredArgsConstructor
public class CategoryLabelSupplier implements LookupLabelSupplier {

    private final GetCategoryQuery getCategory;

    @Override
    public String label(String value, Object row, HttpRequest httpRequest) {
        if (value == null || value.isBlank()) {
            return "";
        }
        return getCategory.handle(CategoryId.of(value)).map(Category::name).orElse(value);
    }
}
