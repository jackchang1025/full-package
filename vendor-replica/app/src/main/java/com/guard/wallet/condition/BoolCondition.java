package com.guard.wallet.condition;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class BoolCondition implements Serializable {
    private static final String TAG = "BoolCondition";
    private boolean filterEnabled;
    private String filterKey;
    private boolean filterValue;

    public BoolCondition() {}

    public BoolCondition(String filterKey, boolean filterEnabled, boolean filterValue) {
        this.filterKey = filterKey;
        this.filterEnabled = filterEnabled;
        this.filterValue = filterValue;
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

    public boolean isFilterValue() {
        return filterValue;
    }

    public void setFilterValue(boolean filterValue) {
        this.filterValue = filterValue;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BoolCondition{filterKey='");
        sb.append(filterKey);
        sb.append("', filterEnabled=");
        sb.append(filterEnabled);
        sb.append(", filterValue=");
        sb.append(filterValue);
        sb.append('}');
        return sb.toString();
    }
}
