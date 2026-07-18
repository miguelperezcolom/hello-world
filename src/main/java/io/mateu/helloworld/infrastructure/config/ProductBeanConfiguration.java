package io.mateu.helloworld.infrastructure.config;

import io.mateu.helloworld.application.out.ProductRepository;
import io.mateu.helloworld.application.queries.getproduct.GetProductQuery;
import io.mateu.helloworld.application.queries.getproducts.GetProductsQuery;
import io.mateu.helloworld.application.usecases.changeproductprice.ChangeProductPriceUseCase;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductUseCase;
import io.mateu.helloworld.infrastructure.persistence.InMemoryProductRepository;
import io.mateu.helloworld.infrastructure.queries.getproduct.InMemoryGetProductQuery;
import io.mateu.helloworld.infrastructure.queries.getproducts.InMemoryGetProductsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition root for the product module: this is where Spring meets the
 * framework-agnostic core. Each use case and query is wired against the
 * outbound port.
 */
@Configuration
public class ProductBeanConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    public CreateProductUseCase createProductUseCase(ProductRepository productRepository) {
        return new CreateProductUseCase(productRepository);
    }

    @Bean
    public ChangeProductPriceUseCase changeProductPriceUseCase(ProductRepository productRepository) {
        return new ChangeProductPriceUseCase(productRepository);
    }

    @Bean
    public GetProductsQuery getProductsQuery(ProductRepository productRepository) {
        return new InMemoryGetProductsQuery(productRepository);
    }

    @Bean
    public GetProductQuery getProductQuery(ProductRepository productRepository) {
        return new InMemoryGetProductQuery(productRepository);
    }
}
