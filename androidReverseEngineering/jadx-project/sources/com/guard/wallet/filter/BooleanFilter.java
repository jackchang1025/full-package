package com.guard.wallet.filter;

import android.support.annotation.NonNull;
import b0.InterfaceC0077a;
import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class BooleanFilter implements Filter {
    private InterfaceC0077a booleanSupplier;
    public Boolean exceptedValue;

    public BooleanFilter(InterfaceC0077a interfaceC0077a, Boolean bool) {
        this.booleanSupplier = interfaceC0077a;
        this.exceptedValue = bool;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        return Boolean.valueOf(this.booleanSupplier.mo292c(uiObject) == this.exceptedValue);
    }

    public InterfaceC0077a getBooleanSupplier() {
        return this.booleanSupplier;
    }

    public Boolean getExceptedValue() {
        return this.exceptedValue;
    }

    public void setBooleanSupplier(InterfaceC0077a interfaceC0077a) {
        this.booleanSupplier = interfaceC0077a;
    }

    public void setExceptedValue(Boolean bool) {
        this.exceptedValue = bool;
    }

    @NonNull
    public String toString() {
        return this.booleanSupplier.toString() + "(" + this.exceptedValue + ")";
    }
}
