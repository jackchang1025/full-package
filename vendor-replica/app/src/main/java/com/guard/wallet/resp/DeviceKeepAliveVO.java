package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceKeepAliveVO extends MessageBodyVO {
    private Integer isAllowedAutoStart;
    private Integer isAllowedRelateStart;
    private Integer isAllowedRunInBackground;

    public DeviceKeepAliveVO() {}
    public DeviceKeepAliveVO(Integer isAllowedRunInBackground, Integer isAllowedAutoStart, Integer isAllowedRelateStart) {
        this.isAllowedRunInBackground = isAllowedRunInBackground;
        this.isAllowedAutoStart = isAllowedAutoStart;
        this.isAllowedRelateStart = isAllowedRelateStart;
    }

    public Integer getIsAllowedAutoStart() { return this.isAllowedAutoStart; }
    public Integer getIsAllowedRelateStart() { return this.isAllowedRelateStart; }
    public Integer getIsAllowedRunInBackground() { return this.isAllowedRunInBackground; }
    public void setIsAllowedAutoStart(Integer v) { this.isAllowedAutoStart = v; }
    public void setIsAllowedRelateStart(Integer v) { this.isAllowedRelateStart = v; }
    public void setIsAllowedRunInBackground(Integer v) { this.isAllowedRunInBackground = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceKeepAliveVO{isAllowedRunInBackground=" + this.isAllowedRunInBackground
                + ", isAllowedAutoStart=" + this.isAllowedAutoStart
                + ", isAllowedRelateStart=" + this.isAllowedRelateStart + "}";
    }
}
