package com.vendor.rat.network;

// ADAPT: vendor = com.guard.wallet.http.a (AppLocateValuesCallback)

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * AppLocateValuesCallback - 应用本地化语言包回调
 * vendor: 解析 ApiResult<HashMap<String, String>>，保存到 locateValues.json，通知策略事件
 */
public final class a implements Callback {

    private static final String TAG = "AppLocateValuesCallback";

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(TAG, "request failed", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String body = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "response: " + response.code());
            // ADAPT: vendor parses ApiResult<HashMap<String, String>>, saves locateValues.json,
            // sets locateValuesLoaded flag, triggers LOAD_LOCATE_VALUES_FINISHED strategy event
        } catch (Exception e) {
            Log.e(TAG, "parse error", e);
        } finally {
            response.close();
        }
    }
}
