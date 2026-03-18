package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.ApiResult (100 行)
// 一比一复刻: 泛型响应封装, 所有 HTTP 回调依赖此类

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApiResult<T> implements Serializable {

    private T data;
    private Integer count;
    private Boolean success;
    private String msg;
    private Integer code;
    private String timestamp;

    public ApiResult() {
        this.success = Boolean.TRUE;
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }

    public ApiResult(T data, Integer count, Boolean success, String msg, Integer code) {
        this.success = Boolean.TRUE;
        this.code = 200;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
        this.data = data;
        this.count = count;
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "ApiResult{data=" + data + ", count=" + count + ", success=" + success
                + ", msg='" + msg + "', code=" + code + ", timestamp='" + timestamp + "'}";
    }
}
