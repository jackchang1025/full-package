package com.storm.safe.rock.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import java.util.concurrent.atomic.AtomicInteger;
import p000.AbstractC0003a2;
import p000.AbstractC1120qr;
import p000.C1351vv;
import p000.RunnableC0027ag;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class InitWorkerService extends Service {

    /* renamed from: a3 */
    public static volatile boolean f52299a3;

    /* renamed from: a0 */
    public Thread f52301a0;

    /* renamed from: a1 */
    public int f52302a1;

    /* renamed from: a2 */
    public static final C0278a0 f52298a2 = new C0278a0(null);

    /* renamed from: a4 */
    public static final AtomicInteger f52300a4 = new AtomicInteger(0);

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.InitWorkerService$a0 */
    public static final class C0278a0 {
        public /* synthetic */ C0278a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final void forceReset() {
            InitWorkerService.f52299a3 = false;
        }

        public final void start(Context context) {
            t60.m214695b6(context, "context");
            if (InitWorkerService.f52299a3) {
                t60.m214702c3("InitWorkerService", "⏳ 已在运行，跳过重复启动");
                return;
            }
            try {
                Intent intent = new Intent(context, (Class<?>) InitWorkerService.class);
                intent.putExtra("gen", InitWorkerService.f52300a4.incrementAndGet());
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            } catch (Exception e) {
                t60.m214705c6("InitWorkerService", "❌ 启动 InitWorkerService 失败", e);
            }
        }

        private C0278a0() {
        }
    }

    /* renamed from: a0 */
    public final void m211386a0() {
        try {
            C1351vv.m214962a3(this);
            Notification notificationM214961a2 = C1351vv.m214961a2(this);
            if (Build.VERSION.SDK_INT >= 34) {
                startForeground(10087, notificationM214961a2, 1073741824);
            } else {
                startForeground(10087, notificationM214961a2);
            }
        } catch (Exception e) {
            t60.m214705c6("InitWorkerService", "❌ 前台服务启动失败", e);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        f52299a3 = true;
        m211386a0();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f52299a3 = false;
        Thread thread = this.f52301a0;
        if (thread != null) {
            thread.interrupt();
        }
        this.f52301a0 = null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        m211386a0();
        int intExtra = intent != null ? intent.getIntExtra("gen", 0) : 0;
        Thread thread = this.f52301a0;
        if (thread != null && thread.isAlive()) {
            int i3 = this.f52302a1;
            if (intExtra <= i3) {
                t60.m214702c3("InitWorkerService", "⏳ 工作线程已在运行(gen=" + i3 + ")，跳过");
                return 2;
            }
            t60.m214714d6("InitWorkerService", AbstractC0003a2.m31b2("🔄 新 generation(", intExtra, " > ", i3, ")，中断旧线程重新执行"));
            Thread thread2 = this.f52301a0;
            if (thread2 != null) {
                thread2.interrupt();
            }
        }
        this.f52302a1 = intExtra;
        Thread thread3 = new Thread(new RunnableC0027ag(this, intExtra, 2));
        this.f52301a0 = thread3;
        thread3.setName("InitWorker");
        Thread thread4 = this.f52301a0;
        if (thread4 != null) {
            thread4.start();
        }
        return 2;
    }
}
