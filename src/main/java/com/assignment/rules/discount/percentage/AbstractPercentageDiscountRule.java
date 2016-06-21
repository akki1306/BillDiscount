package com.assignment.rules.discount.percentage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.assignment.model.Item.ItemType;

import static com.assignment.model.Item.ItemType.GROCERY;
import static java.util.Arrays.asList;

public abstract class AbstractPercentageDiscountRule implements PercentageDiscountRule {

    protected abstract BigDecimal getPercentage();

    public BigDecimal getDiscountAmount(BigDecimal amount) {
        return amount.multiply(getPercentage());
    }

    public List<ItemType> getApplicableItemTypes() {
        List<ItemType> itemTypes = new ArrayList<ItemType>();
        itemTypes.addAll(asList(ItemType.values()));
        itemTypes.remove(GROCERY);
        return itemTypes;
    }
}
