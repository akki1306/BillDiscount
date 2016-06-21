package com.assignment.rules.discount.totalvalue;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class FiveOffOnHunderedTotalValueDiscountRule implements TotalValueDiscountRule {

    public BigDecimal getDiscountAmount(BigDecimal amount) {
        return amount.divideToIntegralValue(valueOf(100d)).multiply(valueOf(5d));
    }
}
