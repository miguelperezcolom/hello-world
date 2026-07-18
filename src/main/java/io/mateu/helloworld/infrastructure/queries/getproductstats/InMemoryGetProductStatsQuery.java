package io.mateu.helloworld.infrastructure.queries.getproductstats;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproductstats.GetProductStatsQuery;
import io.mateu.helloworld.application.queries.getproductstats.ProductStats;
import io.mateu.helloworld.domain.aggregates.product.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** Infrastructure implementation of the {@link GetProductStatsQuery} read side. */
@Component
@RequiredArgsConstructor
public class InMemoryGetProductStatsQuery implements GetProductStatsQuery {

    private final ProductRepository products;

    @Override
    public ProductStats handle() {
        List<Product> all = products.findAll();
        long active = all.stream().filter(Product::isActive).count();
        return new ProductStats(all.size(), active, all.size() - active);
    }
}
