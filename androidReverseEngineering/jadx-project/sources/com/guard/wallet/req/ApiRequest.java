package com.guard.wallet.req;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class ApiRequest<T> {
    private T data;

    public ApiRequest() {
    }

    public ApiRequest(T t2) {
        this.data = t2;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    @NonNull
    public String toString() {
        return "ApiRequest{data=" + this.data + '}';
    }
}
