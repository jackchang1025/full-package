package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
// ADAPT: vendor r.b (obfuscated compare enum) -> String compare
import java.io.Serializable;
public class SearchFieldVO implements Serializable {
    private String compare; // ADAPT: was obfuscated type r.b in vendor
    private Integer isTimestamp;
    private String name;
    private Object value;
    public SearchFieldVO() {
    }
    public SearchFieldVO(String str, Object obj, String compare, Integer num) {
        this.name = str;
        this.value = obj;
        this.compare = compare;
        this.isTimestamp = num;
    }
    public String getCompare() {
        return this.compare;
    }
    public Integer getIsTimestamp() {
        return this.isTimestamp;
    }
    public String getName() {
        return this.name;
    }
    public Object getValue() {
        return this.value;
    }
    public void setCompare(String compare) {
        this.compare = compare;
    }
    public void setIsTimestamp(Integer num) {
        this.isTimestamp = num;
    }
    public void setName(String str) {
        this.name = str;
    }
    public void setValue(Object obj) {
        this.value = obj;
    }
}
