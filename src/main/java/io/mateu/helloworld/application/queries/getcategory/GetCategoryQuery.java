package io.mateu.helloworld.application.queries.getcategory;

import io.mateu.helloworld.domain.aggregates.category.Category;
import io.mateu.helloworld.domain.aggregates.category.valueobjects.CategoryId;

import java.util.Optional;

/**
 * Read side: fetches a single category by id, used to resolve the label of a
 * stored category id in the product's lookup.
 */
public interface GetCategoryQuery {

    Optional<Category> handle(CategoryId id);
}
