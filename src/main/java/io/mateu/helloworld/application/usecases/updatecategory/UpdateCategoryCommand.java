package io.mateu.helloworld.application.usecases.updatecategory;

/** Input for the update-category use case. */
public record UpdateCategoryCommand(String categoryId, String name) {
}
