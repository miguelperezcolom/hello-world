package io.mateu.helloworld.product.infrastructure.config;

import io.mateu.helloworld.product.application.port.out.ProductRepository;
import io.mateu.helloworld.product.application.service.ProductService;
import io.mateu.helloworld.product.infrastructure.persistence.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition root for the product module: this is where Spring meets the
 * framework-agnostic core. The {@link ProductService} bean satisfies every
 * inbound use-case port through which callers inject it.
 */
@Configuration
public class ProductBeanConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
