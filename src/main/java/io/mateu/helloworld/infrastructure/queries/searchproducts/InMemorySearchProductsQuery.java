package io.mateu.helloworld.infrastructure.queries.searchproducts;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.searchproducts.SearchProductsQuery;
import io.mateu.helloworld.domain.aggregates.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/** Infrastructure implementation of the {@link SearchProductsQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemorySearchProductsQuery implements SearchProductsQuery {

    private final ProductRepository products;

    @Override
    public List<Product> handle(String text, boolean onlyActive) {
        String needle = text == null ? "" : text.trim().toLowerCase(Locale.ROOT);
        return products.findAll().stream()
                .filter(product -> !onlyActive || product.isActive())
                .filter(product -> needle.isEmpty()
                        || product.name().toLowerCase(Locale.ROOT).contains(needle)
                        || product.sku().value().toLowerCase(Locale.ROOT).contains(needle))
                .toList();
    }
}
