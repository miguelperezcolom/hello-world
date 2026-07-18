package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.queries.getproduct.GetProductQuery;
import io.mateu.helloworld.application.queries.getproducts.GetProductsQuery;
import io.mateu.helloworld.application.queries.searchproducts.SearchProductsQuery;
import io.mateu.helloworld.application.usecases.activateproduct.ActivateProductCommand;
import io.mateu.helloworld.application.usecases.activateproduct.ActivateProductUseCase;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductCommand;
import io.mateu.helloworld.application.usecases.createproduct.CreateProductUseCase;
import io.mateu.helloworld.application.usecases.deactivateproduct.DeactivateProductCommand;
import io.mateu.helloworld.application.usecases.deactivateproduct.DeactivateProductUseCase;
import io.mateu.helloworld.application.usecases.deleteproduct.DeleteProductCommand;
import io.mateu.helloworld.application.usecases.deleteproduct.DeleteProductUseCase;
import io.mateu.helloworld.application.usecases.updateproduct.UpdateProductCommand;
import io.mateu.helloworld.application.usecases.updateproduct.UpdateProductUseCase;
import io.mateu.helloworld.domain.aggregates.product.Product;
import io.mateu.helloworld.domain.aggregates.product.valueobjects.ProductId;
import io.mateu.uidl.data.ListingData;
import io.mateu.uidl.interfaces.CrudRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Inbound adapter: bridges Mateu's {@link CrudRepository} contract to the
 * application's use cases and queries. It maps between {@link ProductView}
 * (UI DTO) and the {@link Product} aggregate, and never touches persistence
 * directly — every mutation goes through a use case.
 */
@Component
@RequiredArgsConstructor
public class ProductCrudRepository implements CrudRepository<ProductView> {

    private final CreateProductUseCase createProduct;
    private final UpdateProductUseCase updateProduct;
    private final DeleteProductUseCase deleteProduct;
    private final ActivateProductUseCase activateProduct;
    private final DeactivateProductUseCase deactivateProduct;
    private final GetProductsQuery getProducts;
    private final GetProductQuery getProduct;
    private final SearchProductsQuery searchProducts;

    @Override
    public List<ProductView> findAll() {
        return getProducts.handle().stream().map(ProductCrudRepository::toView).toList();
    }

    /** Filtered listing used by the CRUD's filter form. */
    public ListingData<ProductView> search(String text, boolean onlyActive) {
        return ListingData.from(
                searchProducts.handle(text, onlyActive).stream().map(ProductCrudRepository::toView).toList());
    }

    @Override
    public Optional<ProductView> findById(String id) {
        return getProduct.handle(ProductId.of(id)).map(ProductCrudRepository::toView);
    }

    @Override
    public String save(ProductView view) {
        if (view.id() == null || view.id().isBlank()) {
            ProductId created = createProduct.handle(new CreateProductCommand(
                    view.sku(), view.name(), view.description(), view.price(), view.currency()));
            return created.toString();
        }
        updateProduct.handle(new UpdateProductCommand(
                view.id(), view.name(), view.description(), view.price(), view.currency()));
        return view.id();
    }

    @Override
    public void deleteAllById(List<String> ids) {
        ids.forEach(id -> deleteProduct.handle(new DeleteProductCommand(id)));
    }

    /** Activates every product in the given id list, one use-case call each. */
    public void activate(List<String> ids) {
        ids.forEach(id -> activateProduct.handle(new ActivateProductCommand(id)));
    }

    /** Deactivates every product in the given id list, one use-case call each. */
    public void deactivate(List<String> ids) {
        ids.forEach(id -> deactivateProduct.handle(new DeactivateProductCommand(id)));
    }

    private static ProductView toView(Product product) {
        return new ProductView(
                product.id().toString(),
                product.sku().value(),
                product.name(),
                product.description(),
                product.price().amount(),
                product.price().currency().getCurrencyCode(),
                product.isActive());
    }
}
