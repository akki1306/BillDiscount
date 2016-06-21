package com.assignment.rules.discount;

import java.math.BigDecimal;

public interface DiscountRule {
    BigDecimal getDiscountAmount(BigDecimal amount);
}
