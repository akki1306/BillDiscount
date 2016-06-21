package com.assignment.discountcalculators;

import java.math.BigDecimal;
import java.util.List;

import com.assignment.model.Item;
import com.assignment.model.User;
import com.assignment.roles.Role;
import com.assignment.rules.discount.DiscountRule;
import com.assignment.rules.discount.percentage.PercentageDiscountRule;

import static java.math.BigDecimal.ZERO;

public class PercentageDiscountCalculator implements DiscountCalculator {

    private List<Item> items;

    private User user;

    public PercentageDiscountCalculator(User user, List<Item> items) {
        this.user = user;
        this.items = items;
    }

    public BigDecimal getDiscount() {
        BigDecimal discount = ZERO;
        for (Role role : user.getRoles()) {
            if (role.shouldApplyDiscountRules()) {
                for (Item item : items) {
                    for (DiscountRule rule : role.getDiscountRules()) {
                        if (rule instanceof PercentageDiscountRule) {
                            PercentageDiscountRule percentageDiscountRule = (PercentageDiscountRule) rule;
                            if (percentageDiscountRule.getApplicableItemTypes().contains(item.getItemType())) {
                                discount = discount.add(rule.getDiscountAmount(item.getValue()));
                            }
                        }
                    }
                }
            }
        }
        return discount;
    }
}
