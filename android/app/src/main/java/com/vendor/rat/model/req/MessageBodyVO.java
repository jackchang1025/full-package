package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import java.io.Serializable;
public class MessageBodyVO implements Serializable {
    private Long timestamp = Long.valueOf(System.nanoTime());
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long l2) {
        this.timestamp = l2;
    }
}
