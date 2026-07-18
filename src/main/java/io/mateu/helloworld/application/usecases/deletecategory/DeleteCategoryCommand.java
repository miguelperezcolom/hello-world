package io.mateu.helloworld.application.usecases.deletecategory;

/** Input for the delete-category use case. */
public record DeleteCategoryCommand(String categoryId) {
}
