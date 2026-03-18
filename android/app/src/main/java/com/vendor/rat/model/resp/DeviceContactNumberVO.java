package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
public class DeviceContactNumberVO implements Serializable {
    private String label;
    private String number;
    private int numberType;
    public String getLabel() {
        String str = this.label;
        return str == null ? "" : str;
    }
    public String getNumber() {
        String str = this.number;
        return str == null ? "" : str;
    }
    public int getNumberType() {
        return this.numberType;
    }
    public void setLabel(String str) {
        this.label = str;
    }
    public void setNumber(String str) {
        this.number = str;
    }
    public void setNumberType(int i2) {
        this.numberType = i2;
    }
    @NonNull
    public String toString() {
        return "ContactsNumber{numberType=" + this.numberType + ", label='" + this.label + "', number='" + this.number + "'}";
    }
}
