package io.mateu.helloworld.application.queries.getcategories;

import io.mateu.helloworld.domain.aggregates.category.Category;

import java.util.List;

/**
 * Read side: lists categories, optionally filtered by a search string. Feeds the
 * product's category lookup. Declared in application, implemented by infrastructure.
 */
public interface GetCategoriesQuery {

    List<Category> handle(String search);
}
