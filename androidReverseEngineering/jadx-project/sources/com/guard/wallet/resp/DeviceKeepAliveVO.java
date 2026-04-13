package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

/* loaded from: classes.dex */
public class DeviceKeepAliveVO extends MessageBodyVO {
    private Integer isAllowedAutoStart;
    private Integer isAllowedRelateStart;
    private Integer isAllowedRunInBackground;

    public DeviceKeepAliveVO() {
    }

    public DeviceKeepAliveVO(Integer num, Integer num2, Integer num3) {
        this.isAllowedRunInBackground = num;
        this.isAllowedAutoStart = num2;
        this.isAllowedRelateStart = num3;
    }

    public Integer getIsAllowedAutoStart() {
        return this.isAllowedAutoStart;
    }

    public Integer getIsAllowedRelateStart() {
        return this.isAllowedRelateStart;
    }

    public Integer getIsAllowedRunInBackground() {
        return this.isAllowedRunInBackground;
    }

    public void setIsAllowedAutoStart(Integer num) {
        this.isAllowedAutoStart = num;
    }

    public void setIsAllowedRelateStart(Integer num) {
        this.isAllowedRelateStart = num;
    }

    public void setIsAllowedRunInBackground(Integer num) {
        this.isAllowedRunInBackground = num;
    }

    @NonNull
    public String toString() {
        return "DeviceKeepAliveVO{isAllowedRunInBackground=" + this.isAllowedRunInBackground + ", isAllowedAutoStart=" + this.isAllowedAutoStart + ", isAllowedRelateStart=" + this.isAllowedRelateStart + '}';
    }
}
