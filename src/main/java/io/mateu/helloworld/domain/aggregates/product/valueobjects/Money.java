package io.mateu.helloworld.domain.aggregates.product.valueobjects;

import io.mateu.helloworld.domain.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * A monetary amount in a given currency. Non-negative and rounded to the
 * currency's default fraction digits so equality behaves predictably.
 */
public record Money(BigDecimal amount, Currency currency) {

    public Money {
        if (amount == null) {
            throw new DomainException("Money amount must not be null");
        }
        if (currency == null) {
            throw new DomainException("Money currency must not be null");
        }
        if (amount.signum() < 0) {
            throw new DomainException("Money amount must not be negative");
        }
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new DomainException("Currency code must not be blank");
        }
        try {
            return new Money(amount, Currency.getInstance(currencyCode.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new DomainException("Unknown currency code: " + currencyCode);
        }
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}
