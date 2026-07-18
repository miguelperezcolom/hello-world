package io.mateu.helloworld.infrastructure.ui;

import io.mateu.core.infra.declarative.orchestrators.crud.FilteredAutoCrud;
import io.mateu.uidl.data.ListingData;
import io.mateu.uidl.data.Pageable;
import io.mateu.uidl.interfaces.CrudRepository;
import io.mateu.uidl.interfaces.HttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mateu inbound adapter: a CRUD screen for products with a search filter form.
 * Using {@link FilteredAutoCrud} lets the filter type ({@link ProductFilters})
 * differ from the row type ({@link ProductView}); everything else is delegated
 * to {@link ProductCrudRepository}, which drives the application's use cases.
 */
@Service
@RequiredArgsConstructor
public class ProductCrud extends FilteredAutoCrud<ProductFilters, ProductView> {

    private final ProductCrudRepository repository;

    @Override
    public CrudRepository<ProductView> repository() {
        return repository;
    }

    @Override
    public Class filtersClass() {
        return ProductFilters.class;
    }

    @Override
    public ListingData<ProductView> fetchRows(String searchText, ProductFilters filters,
                                              Pageable pageable, HttpRequest httpRequest) {
        String rawText = filters != null ? filters.text() : null;
        boolean onlyActive = filters != null && filters.onlyActive();
        String text = rawText != null && !rawText.isBlank() ? rawText : searchText;
        return repository.search(text, onlyActive);
    }
}
