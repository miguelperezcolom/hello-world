package io.mateu.helloworld.infrastructure.ui;

/**
 * Filter form shown above the product listing. Mateu renders each field as a
 * filter input: a text box and an "only active" checkbox. It is a separate type
 * from {@link ProductView} (the row), which is what {@code FilteredAutoCrud}
 * enables.
 */
public record ProductFilters(String text, boolean onlyActive) {
}
