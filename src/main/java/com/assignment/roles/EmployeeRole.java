package com.assignment.roles;

import java.util.List;

import com.assignment.rules.discount.DiscountRule;
import com.assignment.rules.discount.percentage.EmployeeDiscountRule;
import com.assignment.rules.discount.totalvalue.FiveOffOnHunderedTotalValueDiscountRule;

import static java.util.Arrays.asList;

public class EmployeeRole implements Role {

    private boolean applyDiscountOnThisRole;

    public EmployeeRole(boolean applyDiscountOnThisRole) {
        this.applyDiscountOnThisRole = applyDiscountOnThisRole;
    }

    public List<DiscountRule> getDiscountRules() {
        return asList(new EmployeeDiscountRule(), new FiveOffOnHunderedTotalValueDiscountRule());
    }

    public boolean shouldApplyDiscountRules() {
        return applyDiscountOnThisRole;
    }
}
