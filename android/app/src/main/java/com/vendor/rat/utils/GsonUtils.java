package com.vendor.rat.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Vendor: com.guard.wallet.utils.h (Gson portion)
 * JSON serialization/deserialization utilities using Gson.
 */
public abstract class GsonUtils {

    private static final String TAG = "GsonUtils";

    /**
     * Deserialize JSON string using TypeToken. Vendor: h.c()
     */
    public static Object fromJson(String json, Type typeToken) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, typeToken);
        } catch (Exception e) {
            Log.e(TAG, "fromJson error", e);
            return null;
        }
    }

    /**
     * Deserialize JSON string using Class. Vendor: h.d()
     */
    public static <T> T fromJsonString(String json, Class<T> cls) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, cls);
        } catch (Exception e) {
            Log.e(TAG, "fromJsonString error", e);
            return null;
        }
    }

    /**
     * Parse JSON string to JsonObject. Vendor: h.M()
     */
    public static JsonObject parseJsonObject(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, JsonObject.class);
        } catch (Exception e) {
            Log.e(TAG, "parseJsonObject error", e);
            return null;
        }
    }

    /**
     * Serialize object to JSON string. Vendor: h.N()
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception e) {
            Log.e(TAG, "toJson error", e);
            return "{}";
        }
    }
}