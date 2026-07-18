package io.mateu.helloworld.application.queries.getproductstats;

/** Read-side projection: aggregate counters about the product catalog. */
public record ProductStats(long total, long active, long inactive) {
}
