package com.guard.wallet.condition;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import android.util.Log;
import com.guard.wallet.filter.IntFilter;
import java.io.Serializable;
import p007j.C0350e;

/* loaded from: classes.dex */
public class IntCondition implements Serializable {
    private static final String TAG = "IntCondition";
    private String compare;
    private boolean filterEnabled;
    private String filterKey;
    private int filterValue;

    public IntCondition() {
    }

    public IntCondition(String str, boolean z2, int i2, String str2) {
        this.filterKey = str;
        this.filterEnabled = z2;
        this.filterValue = i2;
        this.compare = str2;
    }

    public String getCompare() {
        return this.compare;
    }

    public String getFilterKey() {
        return this.filterKey;
    }

    public int getFilterValue() {
        return this.filterValue;
    }

    public boolean isFilterEnabled() {
        return this.filterEnabled;
    }

    public void setCompare(String str) {
        this.compare = str;
    }

    public void setFilterEnabled(boolean z2) {
        this.filterEnabled = z2;
    }

    public void setFilterKey(String str) {
        this.filterKey = str;
    }

    public void setFilterValue(int i2) {
        this.filterValue = i2;
    }

    public IntFilter toIntFilter() {
        int i2;
        int i3;
        int i4;
        int i5;
        C0350e c0350e;
        if (!this.filterEnabled || AbstractC0026q.m151B(this.filterKey) || this.filterValue < 0) {
            return null;
        }
        String str = this.filterKey;
        str.getClass();
        i2 = 9;
        i3 = 8;
        i4 = 7;
        i5 = 6;
        switch (str) {
            case "columnSpan":
                c0350e = new C0350e(i3);
                break;
            case "regionCount":
                c0350e = new C0350e(17);
                break;
            case "column":
                c0350e = new C0350e(i4);
                break;
            case "columnCount":
                c0350e = new C0350e(i5);
                break;
            case "drawingOrder":
                c0350e = new C0350e(11);
                break;
            case "row":
                c0350e = new C0350e(20);
                break;
            case "rowCount":
                c0350e = new C0350e(19);
                break;
            case "depth":
                c0350e = new C0350e(i2);
                break;
            case "indexInParent":
                c0350e = new C0350e(14);
                break;
            case "rowSpan":
                c0350e = new C0350e(21);
                break;
            default:
                Log.d(TAG, "未识别整型条件");
                c0350e = null;
                break;
        }
        if (c0350e != null) {
            return new IntFilter(c0350e, this.filterValue);
        }
        return null;
    }

    @NonNull
    public String toString() {
        return "IntCondition{filterKey='" + this.filterKey + "', filterEnabled=" + this.filterEnabled + ", filterValue=" + this.filterValue + '}';
    }
}
