package io.mateu.helloworld.infrastructure.ui;

import io.mateu.helloworld.application.queries.getproductstats.GetProductStatsQuery;
import io.mateu.helloworld.application.queries.getproductstats.ProductStats;
import io.mateu.uidl.annotations.Route;
import io.mateu.uidl.data.HorizontalLayout;
import io.mateu.uidl.data.MetricCard;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dashboard home page. Fields typed as Mateu display components render as UI:
 * here a row of {@link MetricCard}s summarising the catalog.
 *
 * <p>It is a Spring bean so Mateu injects the read-side query, and
 * {@code prototype}-scoped so the counters are recomputed on every visit. The
 * cards are built in {@code @PostConstruct}, once the injected query is available.
 */
@Service
@Scope("prototype")
@Route("/home")
public class Home {

    private final GetProductStatsQuery stats;

    HorizontalLayout kpis;

    public Home(GetProductStatsQuery stats) {
        this.stats = stats;
    }

    @PostConstruct
    void build() {
        ProductStats s = stats.handle();
        kpis = HorizontalLayout.builder()
                .content(List.of(
                        metric("Products", s.total(), "Total in catalog"),
                        metric("Active", s.active(), "Available for sale"),
                        metric("Inactive", s.inactive(), "Hidden from sale")))
                .build();
    }

    private MetricCard metric(String title, long value, String description) {
        return MetricCard.builder()
                .title(title)
                .value(String.valueOf(value))
                .description(description)
                .build();
    }
}
