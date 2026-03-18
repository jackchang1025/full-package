package com.vendor.rat.network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTP 客户端
 *
 * 基于 OkHttp 4.12.0
 * 超时配置: 连接 60s, 读 120s, 写 120s, 调用 240s
 */
public class HttpClient {

    private static final String TAG = "HttpClient";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final String baseUrl;
    private final String deviceId;
    private final Gson gson;

    public HttpClient(String baseUrl, String deviceId) {
        this.baseUrl = baseUrl;
        this.deviceId = deviceId;
        this.gson = new Gson();

        this.client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .callTimeout(240, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
    }

    /**
     * POST JSON 请求 (原始 JSON + OkHttp Callback)
     * ADAPT: 供 NetworkManager API 方法使用
     */
    public void postAsync(String path, String json, Callback callback) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
            .url(baseUrl + path)
            .addHeader("X-Device-Id", deviceId)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * POST JSON 请求
     */
    public void post(String path, Object data, HttpCallback callback) {
        String json = gson.toJson(data);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
            .url(baseUrl + path)
            .addHeader("X-Device-Id", deviceId)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, java.io.IOException e) {
                Log.e(TAG, "POST failed: " + path, e);
                if (callback != null) callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body() != null
                        ? response.body().string() : "";
                    if (response.isSuccessful()) {
                        if (callback != null) callback.onSuccess(responseBody);
                    } else {
                        if (callback != null) callback.onFailure(
                            new Exception("HTTP " + response.code()));
                    }
                } catch (Exception e) {
                    if (callback != null) callback.onFailure(e);
                } finally {
                    response.close();
                }
            }
        });
    }

    /**
     * 上传文件（Multipart）
     */
    public void uploadFile(String path, File file, String fieldName,
                           HttpCallback callback) {
        RequestBody fileBody = RequestBody.create(file,
            MediaType.get("application/octet-stream"));

        MultipartBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(fieldName, file.getName(), fileBody)
            .addFormDataPart("deviceId", deviceId)
            .build();

        Request request = new Request.Builder()
            .url(baseUrl + path)
            .addHeader("X-Device-Id", deviceId)
            .post(body)
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, java.io.IOException e) {
                Log.e(TAG, "Upload failed: " + path, e);
                if (callback != null) callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body() != null
                        ? response.body().string() : "";
                    if (response.isSuccessful()) {
                        if (callback != null) callback.onSuccess(responseBody);
                    } else {
                        if (callback != null) callback.onFailure(
                            new Exception("HTTP " + response.code()));
                    }
                } catch (Exception e) {
                    if (callback != null) callback.onFailure(e);
                } finally {
                    response.close();
                }
            }
        });
    }

    /**
     * GET 请求
     */
    public void get(String path, HttpCallback callback) {
        Request request = new Request.Builder()
            .url(baseUrl + path)
            .addHeader("X-Device-Id", deviceId)
            .get()
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, java.io.IOException e) {
                Log.e(TAG, "GET failed: " + path, e);
                if (callback != null) callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body() != null
                        ? response.body().string() : "";
                    if (response.isSuccessful()) {
                        if (callback != null) callback.onSuccess(responseBody);
                    } else {
                        if (callback != null) callback.onFailure(
                            new Exception("HTTP " + response.code()));
                    }
                } catch (Exception e) {
                    if (callback != null) callback.onFailure(e);
                } finally {
                    response.close();
                }
            }
        });
    }

    public OkHttpClient getOkHttpClient() { return client; }
}
