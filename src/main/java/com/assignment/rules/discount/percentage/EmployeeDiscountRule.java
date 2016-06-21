package com.assignment.rules.discount.percentage;


import java.math.BigDecimal;

import com.assignment.rules.discount.DiscountRule;

public class EmployeeDiscountRule extends AbstractPercentageDiscountRule implements DiscountRule {

    public BigDecimal getPercentage() {
        return BigDecimal.valueOf(0.30d);
    }
}
