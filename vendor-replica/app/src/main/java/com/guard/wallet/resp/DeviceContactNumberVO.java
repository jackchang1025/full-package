package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class DeviceContactNumberVO implements Serializable {
    private String label;
    private String number;
    private int numberType;

    public String getLabel() { return this.label != null ? this.label : ""; }
    public String getNumber() { return this.number != null ? this.number : ""; }
    public int getNumberType() { return this.numberType; }

    public void setLabel(String v) { this.label = v; }
    public void setNumber(String v) { this.number = v; }
    public void setNumberType(int v) { this.numberType = v; }

    @NonNull
    @Override
    public String toString() {
        return "ContactsNumber{numberType=" + this.numberType
                + ", label='" + this.label + "', number='" + this.number + "'}";
    }
}
