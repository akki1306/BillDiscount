package com.assignment.model;


import java.math.BigDecimal;
import java.util.List;

import com.assignment.discountcalculators.DiscountCalculator;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ZERO;

public class Bill {
    List<Item> billItems;
    BigDecimal totalValue = ZERO;
    List<DiscountCalculator> discountCalculators;

    public Bill(List<Item> billItems) {
        this.billItems = billItems;
        calculateTotalValue();
    }

    public void setDiscountCalculators(List<DiscountCalculator> discountCalculators) {
        this.discountCalculators = discountCalculators;
    }

    public BigDecimal getTotalValueAfterDiscount() {
        BigDecimal finalAmount = ZERO;
        finalAmount = finalAmount.add(totalValue);
        finalAmount = finalAmount.subtract(getDiscount());
        return finalAmount.setScale(2, ROUND_CEILING);
    }

    public BigDecimal getTotalValueBeforeDiscount() {
        return totalValue;
    }

    private BigDecimal getDiscount() {
        BigDecimal discount = ZERO;
        for (DiscountCalculator discountCalculator : discountCalculators) {
            discount = discount.add(discountCalculator.getDiscount());
        }
        return discount;
    }

    public void calculateTotalValue() {
        for (Item billItem : billItems) {
            totalValue = totalValue.add(billItem.getValue());
        }
    }

    public List<Item> getItems() {
        return this.billItems;
    }
}
