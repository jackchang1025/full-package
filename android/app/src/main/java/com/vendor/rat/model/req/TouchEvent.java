package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class TouchEvent implements Serializable {
    private String codeName;
    private String deviceName;
    private String typeName;
    private String value;
    public TouchEvent() {
    }
    public TouchEvent(String str, String str2, String str3, String str4) {
        this.deviceName = str;
        this.typeName = str2;
        this.codeName = str3;
        this.value = str4;
    }
    public String getCodeName() {
        return this.codeName;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public String getValue() {
        return this.value;
    }
    public void setCodeName(String str) {
        this.codeName = str;
    }
    public void setDeviceName(String str) {
        this.deviceName = str;
    }
    public void setTypeName(String str) {
        this.typeName = str;
    }
    public void setValue(String str) {
        this.value = str;
    }
    @NonNull
    public String toString() {
        return "TouchEvent{deviceName='" + this.deviceName + "', typeName='" + this.typeName + "', codeName='" + this.codeName + "', value='" + this.value + "'}";
    }
}
