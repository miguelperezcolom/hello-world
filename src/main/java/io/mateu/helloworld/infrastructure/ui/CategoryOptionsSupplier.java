package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.queries.getcategories.GetCategoriesQuery;
import io.mateu.uidl.data.ListingData;
import io.mateu.uidl.data.Option;
import io.mateu.uidl.data.Pageable;
import io.mateu.uidl.interfaces.HttpRequest;
import io.mateu.uidl.interfaces.LookupOptionsSupplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Read-side adapter for the category lookup: turns the {@link GetCategoriesQuery}
 * results into {@link Option}s (value = stored id, label = shown name). The
 * ViewModel that declares {@code @Lookup} never sees the data source — the
 * supplier is a Spring bean and can inject any query service.
 */
@Service
@RequiredArgsConstructor
public class CategoryOptionsSupplier implements LookupOptionsSupplier {

    private final GetCategoriesQuery getCategories;

    @Override
    public ListingData<Option> search(String searchText, String field, Pageable pageable, HttpRequest httpRequest) {
        List<Option> options = getCategories.handle(searchText).stream()
                .map(category -> new Option(category.id().toString(), category.name()))
                .toList();
        return ListingData.from(options);
    }
}
