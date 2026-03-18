package com.vendor.rat.auto.condition;

import java.io.Serializable;

/**
 * Vendor: com/guard/wallet/condition/ActionValueCondition.java (43行)
 * 动作参数值条件 - 纯数据类
 */
public class ActionValueCondition implements Serializable {

    private String key;
    private String type;
    private String value;

    public ActionValueCondition() {
    }

    public ActionValueCondition(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getKey() { return this.key; }
    public String getType() { return this.type; }
    public String getValue() { return this.value; }

    public void setKey(String key) { this.key = key; }
    public void setType(String type) { this.type = type; }
    public void setValue(String value) { this.value = value; }
}
