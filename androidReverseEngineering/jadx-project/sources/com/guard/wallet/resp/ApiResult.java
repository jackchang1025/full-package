package com.guard.wallet.resp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ApiResult<T> {
    private Integer code;
    private Integer count;
    private T data;
    private String msg;
    private Boolean success;

    @SuppressLint({"SimpleDateFormat"})
    private String timestamp;

    public ApiResult() {
        this.success = Boolean.TRUE;
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public Integer getCode() {
        return this.code;
    }

    public Integer getCount() {
        return this.count;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setCount(Integer num) {
        this.count = num;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setSuccess(Boolean bool) {
        this.success = bool;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("ApiResult{data=");
        sb.append(this.data);
        sb.append(", count=");
        sb.append(this.count);
        sb.append(", success=");
        sb.append(this.success);
        sb.append(", msg='");
        sb.append(this.msg);
        sb.append("', code=");
        sb.append(this.code);
        sb.append(", timestamp='");
        return AbstractC0000a.m18n(sb, this.timestamp, "'}");
    }

    public ApiResult(T t2, Integer num, Boolean bool, String str, Integer num2) {
        this.success = Boolean.TRUE;
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.data = t2;
        this.count = num;
        this.success = bool;
        this.msg = str;
        this.code = num2;
    }
}
