package com.assignment.rules.discount.percentage;

import java.util.List;

import com.assignment.model.Item;
import com.assignment.rules.discount.DiscountRule;

public interface PercentageDiscountRule extends DiscountRule {
    List<Item.ItemType> getApplicableItemTypes();
}
