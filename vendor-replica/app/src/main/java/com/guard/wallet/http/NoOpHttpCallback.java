package com.guard.wallet.http;

import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * No-op HTTP callback that simply logs success/failure.
 * Replaces the removed NodePropertyDelegate (was misplaced in filter/).
 * Used for fire-and-forget HTTP calls (e.g., rewriteDebugPort notification).
 */
public final class NoOpHttpCallback implements Callback {
    private static final String TAG = "NoOpHttpCallback";
    private final int type;

    public NoOpHttpCallback(int type) {
        this.type = type;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.w(TAG, "HTTP callback type=" + type + " failed: " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (response.body() != null) {
                response.body().close();
            }
        } catch (Exception e) {
            Log.w(TAG, "HTTP callback type=" + type + " response handling error", e);
        }
    }
}
