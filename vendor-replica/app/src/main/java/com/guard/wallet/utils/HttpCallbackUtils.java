package com.guard.wallet.utils;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * HTTP 回调工具类。
 * <p>
 * vendor 源文件: j.java (com.guard.wallet.utils)
 * 重命名: j → HttpCallbackUtils, j.e → HttpCallbackUtils.SimpleCallback
 * </p>
 */
public abstract class HttpCallbackUtils {

    /**
     * 简单 HTTP 回调实现，仅关闭 response，不做额外处理。
     * <p>
     * vendor: j.e — 包含重试次数 (retryCount)。
     * </p>
     */
    public static class SimpleCallback implements Callback {
        private final int retryCount;

        public SimpleCallback(int retryCount) {
            this.retryCount = retryCount;
        }

        public int getRetryCount() {
            return retryCount;
        }

        @Override
        public void onFailure(Call call, IOException ex) {
            // no-op
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response != null) { response.close(); }
        }
    }
}
