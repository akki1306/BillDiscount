package com.assignment.discountcalculators;

import java.math.BigDecimal;

import com.assignment.model.User;
import com.assignment.roles.Role;
import com.assignment.rules.discount.DiscountRule;
import com.assignment.rules.discount.totalvalue.TotalValueDiscountRule;

import static java.math.BigDecimal.ZERO;

public class TotalValueDiscountCalculator implements DiscountCalculator {

    private BigDecimal totalValue;

    private User user;

    public TotalValueDiscountCalculator(User user, BigDecimal totalValue) {
        this.totalValue = totalValue;
        this.user = user;
    }

    public BigDecimal getDiscount() {
        BigDecimal discount = ZERO;
        for (Role role : user.getRoles()) {
            for (DiscountRule rule : role.getDiscountRules()) {
                if (rule instanceof TotalValueDiscountRule) {
                    discount = discount.add(rule.getDiscountAmount(totalValue));
                }
            }
        }
        return discount;
    }
}
