package com.guard.wallet.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class IntCondition implements Serializable {
    private static final String TAG = "IntCondition";
    private String compare;
    private boolean filterEnabled;
    private String filterKey;
    private int filterValue;

    public IntCondition() {}

    public IntCondition(String filterKey, boolean filterEnabled, int filterValue, String compare) {
        this.filterKey = filterKey;
        this.filterEnabled = filterEnabled;
        this.filterValue = filterValue;
        this.compare = compare;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
    }

    public int getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(int filterValue) {
        this.filterValue = filterValue;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntCondition{filterKey='");
        sb.append(filterKey);
        sb.append("', filterEnabled=");
        sb.append(filterEnabled);
        sb.append(", filterValue=");
        sb.append(filterValue);
        sb.append('}');
        return sb.toString();
    }
}
