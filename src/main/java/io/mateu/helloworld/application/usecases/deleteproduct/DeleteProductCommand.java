package io.mateu.helloworld.application.usecases.deleteproduct;

/** Input for the delete-product use case. */
public record DeleteProductCommand(String productId) {
}
