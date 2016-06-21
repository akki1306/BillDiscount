package com.assignment.roles;

import java.util.List;

import com.assignment.rules.discount.DiscountRule;

public interface Role {
    List<DiscountRule> getDiscountRules();

    boolean shouldApplyDiscountRules();
}
