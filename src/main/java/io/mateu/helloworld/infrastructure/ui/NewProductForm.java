package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.usecases.createproduct.CreateProductCommand;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductUseCase;
import io.mateu.uidl.annotations.Button;
import io.mateu.uidl.annotations.Lookup;
import io.mateu.uidl.annotations.Multiline;
import io.mateu.uidl.data.Message;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Inbound adapter: a hand-written form screen, as an alternative to the generated
 * {@link ProductCrud} when you want a bespoke "create" flow. Any public field is
 * rendered as a form field; a public {@code @Button} method becomes the submit
 * button and drives the {@link CreateProductUseCase}. Returning a {@link Message}
 * shows feedback to the user — returning a {@link java.net.URI} instead would
 * navigate to another route.
 *
 * <p>It is a Spring bean so Mateu can inject the use case: Mateu resolves a screen
 * from the Spring context before falling back to reflection. It is
 * {@code prototype}-scoped so every render/submit gets a fresh instance with its
 * own form state, instead of sharing a singleton's fields across requests.
 */
@Service
@Scope("prototype")
@RequiredArgsConstructor
public class NewProductForm {

    private final CreateProductUseCase createProduct;

    @NotEmpty
    String sku;
    @NotEmpty
    String name;
    @Multiline
    String description;
    @NotNull
    @Min(0)
    BigDecimal price;
    @NotEmpty
    String currency = "EUR";
    @Lookup(search = CategoryOptionsSupplier.class, label = CategoryLabelSupplier.class)
    String categoryId;

    @Button
    public Message create() {
        createProduct.handle(new CreateProductCommand(sku, name, description, price, currency, categoryId));
        return Message.builder()
                .text("Product '" + name + "' created")
                .build();
    }
}
