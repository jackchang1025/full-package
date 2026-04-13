package com.guard.wallet.condition;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import android.util.Log;
import b0.C0078b;
import b0.InterfaceC0077a;
import com.guard.wallet.filter.BooleanFilter;
import java.io.Serializable;
import p007j.C0350e;

/* loaded from: classes.dex */
public class BoolCondition implements Serializable {
    private static final String TAG = "BoolCondition";
    private boolean filterEnabled;
    private String filterKey;
    private boolean filterValue;

    public BoolCondition() {
    }

    public BoolCondition(String str, boolean z2, boolean z3) {
        this.filterKey = str;
        this.filterEnabled = z2;
        this.filterValue = z3;
    }

    public String getFilterKey() {
        return this.filterKey;
    }

    public boolean isFilterEnabled() {
        return this.filterEnabled;
    }

    public boolean isFilterValue() {
        return this.filterValue;
    }

    public void setFilterEnabled(boolean z2) {
        this.filterEnabled = z2;
    }

    public void setFilterKey(String str) {
        this.filterKey = str;
    }

    public void setFilterValue(boolean z2) {
        this.filterValue = z2;
    }

    public BooleanFilter toBooleanFilter() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        InterfaceC0077a c0078b;
        if (!this.filterEnabled || AbstractC0026q.m151B(this.filterKey)) {
            return null;
        }
        String str = this.filterKey;
        str.getClass();
        i2 = 20;
        i3 = 19;
        i4 = 18;
        i5 = 17;
        i6 = 15;
        i7 = 13;
        i8 = 12;
        i9 = 11;
        i10 = 9;
        i11 = 7;
        i12 = 2;
        i13 = 1;
        i14 = 0;
        switch (str) {
            case "contentInvalid":
                c0078b = new C0078b(4);
                break;
            case "clickable":
                c0078b = new C0078b(3);
                break;
            case "textSelectable":
                c0078b = new C0078b(21);
                break;
            case "enabled":
                c0078b = new C0078b(8);
                break;
            case "dismissable":
                c0078b = new C0078b(6);
                break;
            case "multiLine":
                c0078b = new C0078b(14);
                break;
            case "screenReaderFocusable":
                c0078b = new C0078b(16);
                break;
            case "focused":
                c0078b = new C0078b(10);
                break;
            case "contextClickable":
                c0078b = new C0078b(5);
                break;
            case "scrollable":
                c0078b = new C0078b(i5);
                break;
            case "checkable":
                c0078b = new C0078b(i13);
                break;
            case "checked":
                c0078b = new C0078b(i12);
                break;
            case "importantForAccessibility":
                c0078b = new C0078b(i8);
                break;
            case "canOpenPopup":
                c0078b = new C0078b(i14);
                break;
            case "heading":
                c0078b = new C0078b(i9);
                break;
            case "visibleToUser":
                c0078b = new C0078b(22);
                break;
            case "longClickable":
                c0078b = new C0078b(i7);
                break;
            case "selected":
                c0078b = new C0078b(i4);
                break;
            case "password":
                c0078b = new C0078b(i6);
                break;
            case "editable":
                c0078b = new C0078b(i11);
                break;
            case "focusable":
                c0078b = new C0078b(i10);
                break;
            case "textEntryKey":
                c0078b = new C0078b(i2);
                break;
            case "accessibilityFocused":
                c0078b = new C0350e(29);
                break;
            case "showingHintText":
                c0078b = new C0078b(i3);
                break;
            default:
                Log.d(TAG, "未识别布尔条件");
                c0078b = null;
                break;
        }
        if (c0078b != null) {
            return new BooleanFilter(c0078b, Boolean.valueOf(this.filterValue));
        }
        return null;
    }

    @NonNull
    public String toString() {
        return "BoolCondition{filterKey='" + this.filterKey + "', filterEnabled=" + this.filterEnabled + ", filterValue=" + this.filterValue + '}';
    }
}
