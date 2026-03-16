package com.vendor.rat.network;

/**
 * HTTP 回调接口
 */
public interface HttpCallback {
    void onSuccess(String response);
    void onFailure(Exception e);
}
