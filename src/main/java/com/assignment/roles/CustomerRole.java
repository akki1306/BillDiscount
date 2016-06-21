package com.assignment.roles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.assignment.rules.discount.percentage.CustomerDiscountRule;
import com.assignment.rules.discount.DiscountRule;
import com.assignment.rules.discount.totalvalue.FiveOffOnHunderedTotalValueDiscountRule;

public class CustomerRole implements Role {
    private Date membershipStartDate;

    private boolean applyDiscountOnThisRole;

    public CustomerRole(Date membershipStartDate, boolean applyDiscountOnThisRole) {
        this.membershipStartDate = membershipStartDate;
        this.applyDiscountOnThisRole = applyDiscountOnThisRole;
    }

    public List<DiscountRule> getDiscountRules() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        List<DiscountRule> discountRules = new ArrayList<DiscountRule>();
        discountRules.add(new FiveOffOnHunderedTotalValueDiscountRule());
        if (membershipStartDate.before(calendar.getTime())) {
            discountRules.add(new CustomerDiscountRule());
        }
        return discountRules;
    }

    public boolean shouldApplyDiscountRules() {
        return applyDiscountOnThisRole;
    }
}
