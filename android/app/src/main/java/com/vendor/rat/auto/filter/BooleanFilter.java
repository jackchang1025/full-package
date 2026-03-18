package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;

/**
 * // ADAPT: 反混淆 BooleanFilter, b0.a → BooleanPropertyGetter
 */
public class BooleanFilter implements NodeFilter {
    private BooleanPropertyGetter booleanSupplier;
    public Boolean exceptedValue;

    public BooleanFilter(BooleanPropertyGetter supplier, Boolean expected) {
        this.booleanSupplier = supplier;
        this.exceptedValue = expected;
    }

    @Override
    public boolean accept(UiNode node) {
        // ADAPT: vendor returns Boolean.valueOf(==), we return boolean
        return booleanSupplier.get(node) == exceptedValue;
    }

    public BooleanPropertyGetter getBooleanSupplier() { return booleanSupplier; }
    public Boolean getExceptedValue() { return exceptedValue; }
    public void setBooleanSupplier(BooleanPropertyGetter supplier) { this.booleanSupplier = supplier; }
    public void setExceptedValue(Boolean value) { this.exceptedValue = value; }

    @NonNull
    @Override
    public String toString() {
        return booleanSupplier.toString() + "(" + exceptedValue + ")";
    }
}
