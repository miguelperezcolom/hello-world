package io.mateu.helloworld.infrastructure.ui;

import io.mateu.core.infra.declarative.orchestrators.crud.AutoCrud;
import io.mateu.uidl.interfaces.CrudRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mateu inbound adapter: an auto-generated CRUD screen for categories, so the
 * options offered by the product's {@code @Lookup} are managed here. All
 * behaviour is delegated to {@link CategoryCrudRepository}.
 */
@Service
@RequiredArgsConstructor
public class CategoryCrud extends AutoCrud<CategoryView> {

    private final CategoryCrudRepository repository;

    @Override
    public CrudRepository<CategoryView> repository() {
        return repository;
    }
}
