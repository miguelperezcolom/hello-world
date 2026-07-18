package io.mateu.helloworld.infrastructure.ui;

import io.mateu.core.infra.declarative.orchestrators.crud.AutoCrud;
import io.mateu.uidl.annotations.UI;
import io.mateu.uidl.interfaces.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mateu inbound adapter: an auto-generated CRUD screen for products at
 * {@code /products}. All behaviour is delegated to {@link ProductCrudRepository},
 * which in turn drives the application's use cases.
 */
@Service
@UI("/products")
@RequiredArgsConstructor
public class ProductCrud extends AutoCrud<ProductView> {

    private final CrudRepository<ProductView> repository;

    @Override
    public CrudRepository<ProductView> repository() {
        return repository;
    }
}
