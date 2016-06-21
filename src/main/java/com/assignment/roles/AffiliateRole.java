package com.assignment.roles;

import java.util.List;

import com.assignment.rules.discount.percentage.AffiliateDiscountRule;
import com.assignment.rules.discount.DiscountRule;
import com.assignment.rules.discount.totalvalue.FiveOffOnHunderedTotalValueDiscountRule;

import static java.util.Arrays.asList;

public class AffiliateRole implements Role {

    private boolean applyDiscountOnThisRole;

    public AffiliateRole(boolean applyDiscountOnThisRole) {
        this.applyDiscountOnThisRole = applyDiscountOnThisRole;
    }

    public List<DiscountRule> getDiscountRules() {
        return asList(new AffiliateDiscountRule(), new FiveOffOnHunderedTotalValueDiscountRule());
    }

    public boolean shouldApplyDiscountRules() {
        return applyDiscountOnThisRole;
    }
}
