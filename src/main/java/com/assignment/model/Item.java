package com.assignment.model;

import java.math.BigDecimal;

public class Item {
    public enum ItemType {GROCERY, FURNITURE, MISC}

    private String name;
    private BigDecimal value;
    private ItemType itemType;

    public Item(String name, BigDecimal value, ItemType itemType) {
        this.name = name;
        this.value = value;
        this.itemType = itemType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
