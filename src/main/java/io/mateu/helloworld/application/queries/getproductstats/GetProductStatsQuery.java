package io.mateu.helloworld.application.queries.getproductstats;

/**
 * Read side: computes catalog statistics for the dashboard. Declared in the
 * application layer, implemented by infrastructure.
 */
public interface GetProductStatsQuery {

    ProductStats handle();
}
