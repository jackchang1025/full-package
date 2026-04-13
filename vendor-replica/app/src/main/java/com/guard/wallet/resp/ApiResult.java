package com.guard.wallet.resp;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiResult<T> {
    private Integer code;
    private Integer count;
    private T data;
    private String msg;
    private Boolean success = Boolean.TRUE;
    @SuppressLint("SimpleDateFormat")
    private String timestamp;

    public ApiResult() {
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public ApiResult(T data, Integer count, Boolean success, String msg, Integer code) {
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.data = data;
        this.count = count;
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() { return this.code; }
    public Integer getCount() { return this.count; }
    public T getData() { return this.data; }
    public String getMsg() { return this.msg; }
    public Boolean getSuccess() { return this.success; }
    public String getTimestamp() { return this.timestamp; }

    public void setCode(Integer code) { this.code = code; }
    public void setCount(Integer count) { this.count = count; }
    public void setData(T data) { this.data = data; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setSuccess(Boolean success) { this.success = success; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @NonNull
    @Override
    public String toString() {
        return "ApiResult{data=" + this.data
                + ", count=" + this.count
                + ", success=" + this.success
                + ", msg='" + this.msg
                + "', code=" + this.code
                + ", timestamp='" + this.timestamp + "'}";
    }
}
