package com.storm.safe.rock.manager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import com.storm.safe.rock.activity.qixvbtmo;
import com.storm.safe.rock.service.MediaDisplayService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.AbstractC0767a0;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.C1426xq;
import p000.ThreadFactoryC1051p0;
import p000.h10;
import p000.t60;
import p000.tz0;
import p000.u11;
import p000.uj1;
import p000.vj1;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a5 */
/* loaded from: classes2.dex */
public final class C0263a5 {

    /* renamed from: b1 */
    public static volatile Boolean f52145b1;

    /* renamed from: a0 */
    public final dqtvuisjd f52151a0;

    /* renamed from: a1 */
    public volatile boolean f52152a1;

    /* renamed from: a2 */
    public volatile boolean f52153a2;

    /* renamed from: a3 */
    public String f52154a3 = f52150b6;

    /* renamed from: a4 */
    public u11 f52155a4;

    /* renamed from: a5 */
    public volatile ThreadPoolExecutor f52156a5;

    /* renamed from: a6 */
    public volatile long f52157a6;

    /* renamed from: a7 */
    public Bitmap f52158a7;

    /* renamed from: a8 */
    public long f52159a8;

    /* renamed from: a9 */
    public final ReentrantLock f52160a9;

    /* renamed from: b0 */
    public static final uj1 f52144b0 = new uj1(null);

    /* renamed from: b2 */
    public static final y90 f52146b2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.manager.etzbzyzqxvqm$Companion$isVivoDevice$2
        @Override // p000.w00
        public final Object invoke() {
            return Boolean.valueOf(AbstractC0779a1.m213656a9(Build.MANUFACTURER, "vivo"));
        }
    });

    /* renamed from: b3 */
    public static volatile int f52147b3 = 10;

    /* renamed from: b4 */
    public static volatile int f52148b4 = 45;

    /* renamed from: b5 */
    public static volatile float f52149b5 = 0.5f;

    /* renamed from: b6 */
    public static final String f52150b6 = StringUtil.m212470a0("KloSP14rBSxePSJNCA==");

    public C0263a5(dqtvuisjd dqtvuisjdVar) {
        this.f52151a0 = dqtvuisjdVar;
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(2, new ThreadFactoryC1051p0(1));
        t60.m214694b5(executorServiceNewFixedThreadPool, "compressionExecutor");
        new C1426xq(executorServiceNewFixedThreadPool);
        this.f52156a5 = new ThreadPoolExecutor(1, 2, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        this.f52157a6 = 300L;
        this.f52160a9 = new ReentrantLock();
        int i = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
    }

    /* renamed from: a0 */
    public static final Bitmap m211343a0(C0263a5 c0263a5) {
        if (Build.VERSION.SDK_INT < 30 || t60.m214686a2(f52145b1, Boolean.FALSE)) {
            return null;
        }
        if (f52145b1 == null) {
            try {
                Class.forName("android.accessibilityservice.AccessibilityService$TakeScreenshotCallback", false, C0263a5.class.getClassLoader());
                if (f52144b0.isVivoDevice() && c0263a5.f52157a6 < 350) {
                    c0263a5.f52157a6 = 350L;
                }
            } catch (Throwable th) {
                t60.m214726f4("etzbzyzqxvqm", "⚠️ 此设备不支持 TakeScreenshotCallback API: ".concat(th.getClass().getSimpleName()));
                f52145b1 = Boolean.FALSE;
                return null;
            }
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - c0263a5.f52159a8 < c0263a5.f52157a6) {
                return null;
            }
            return c0263a5.m211349a5(jCurrentTimeMillis);
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "无障碍截图失败", e);
            return null;
        } catch (NoClassDefFoundError e2) {
            tz0.m214810b0("⚠️ TakeScreenshotCallback 类加载失败: ", e2.getMessage(), "etzbzyzqxvqm");
            f52145b1 = Boolean.FALSE;
            return null;
        } catch (LinkageError e3) {
            tz0.m214810b0("⚠️ TakeScreenshotCallback 链接错误: ", e3.getMessage(), "etzbzyzqxvqm");
            f52145b1 = Boolean.FALSE;
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v7, types: [byte[]] */
    /* renamed from: a1 */
    public static final byte[] m211344a1(C0263a5 c0263a5, Bitmap bitmap) throws Throwable {
        Bitmap bitmap2 = null;
        Bitmap bitmap3 = null;
        try {
        } catch (Throwable th) {
            th = th;
            bitmap3 = bitmap;
        }
        try {
            try {
                try {
                    ReentrantLock reentrantLock = c0263a5.f52160a9;
                    reentrantLock.lock();
                    try {
                        if (bitmap.isRecycled()) {
                            t60.m214726f4("etzbzyzqxvqm", "⚠️ compressBitmap: Bitmap 已被回收，跳过压缩");
                            byte[] bArr = new byte[0];
                            reentrantLock.unlock();
                            return bArr;
                        }
                        Bitmap bitmapM211348b2 = m211348b2(bitmap);
                        boolean z = (bitmapM211348b2 == null || bitmapM211348b2.equals(bitmap)) ? false : true;
                        if (bitmapM211348b2 != null) {
                            try {
                                if (!bitmapM211348b2.isRecycled()) {
                                    reentrantLock.unlock();
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    try {
                                        Bitmap.CompressFormat compressFormat = Build.VERSION.SDK_INT >= 30 ? Bitmap.CompressFormat.WEBP_LOSSY : Bitmap.CompressFormat.WEBP;
                                        if (!bitmapM211348b2.isRecycled()) {
                                            Bitmap bitmapM211347a4 = c0263a5.f52151a0.f52469k0 ? m211347a4(bitmapM211348b2) : bitmapM211348b2;
                                            bitmapM211347a4.compress(compressFormat, f52148b4, byteArrayOutputStream);
                                            if (bitmapM211347a4 != bitmapM211348b2) {
                                                bitmapM211347a4.recycle();
                                            }
                                        }
                                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                                        byteArrayOutputStream.close();
                                        t60.m214694b5(byteArray, "ByteArrayOutputStream().…ByteArray()\n            }");
                                        if (z && !bitmapM211348b2.isRecycled()) {
                                            bitmapM211348b2.recycle();
                                        }
                                        return byteArray;
                                    } finally {
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                reentrantLock.unlock();
                                throw th;
                            }
                        }
                        t60.m214726f4("etzbzyzqxvqm", "⚠️ compressBitmap: 缩放后的 Bitmap 无效，跳过压缩");
                        byte[] bArr2 = new byte[0];
                        reentrantLock.unlock();
                        if (z && bitmapM211348b2 != null && !bitmapM211348b2.isRecycled()) {
                            bitmapM211348b2.recycle();
                        }
                        return bArr2;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception e) {
                    e = e;
                    t60.m214705c6("etzbzyzqxvqm", "压缩失败", e);
                    c0263a5 = new byte[0];
                    if (bitmap != null && 0 != 0 && !bitmap2.isRecycled()) {
                        bitmap2.recycle();
                    }
                    return c0263a5;
                }
            } catch (Exception unused) {
                return c0263a5;
            }
        } catch (Exception e2) {
            e = e2;
            bitmap = null;
            t60.m214705c6("etzbzyzqxvqm", "压缩失败", e);
            c0263a5 = new byte[0];
            if (bitmap != null) {
                bitmap2.recycle();
            }
            return c0263a5;
        } catch (Throwable th4) {
            th = th4;
            if (bitmap3 != null && 0 != 0 && !bitmap2.isRecycled()) {
                try {
                    bitmap2.recycle();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    /* renamed from: a2 */
    public static final Bitmap m211345a2(C0263a5 c0263a5) {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(480, 854, Bitmap.Config.ARGB_8888);
            t60.m214694b5(bitmapCreateBitmap, "createBitmap(MAX_WIDTH, … Bitmap.Config.ARGB_8888)");
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(Color.parseColor("#1a1a2e"));
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(-1);
            paint.setTextSize(24.0f);
            canvas.drawText("等待截图权限", 240.0f, 377.0f, paint);
            paint.setTextSize(16.0f);
            paint.setColor(Color.parseColor("#888888"));
            canvas.drawText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()), 240.0f, 457.0f, paint);
            return bitmapCreateBitmap;
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "生成测试图像失败", e);
            return null;
        }
    }

    /* renamed from: a3 */
    public static final void m211346a3(C0263a5 c0263a5, byte[] bArr) {
        if (bArr.length == 0) {
            return;
        }
        try {
            C0323a8 c0323a8M211471g5 = c0263a5.f52151a0.m211471g5();
            if (c0323a8M211471g5 != null) {
                c0323a8M211471g5.m211665d1(bArr);
            }
            System.currentTimeMillis();
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "发送帧数据失败", e);
        }
    }

    /* renamed from: a4 */
    public static Bitmap m211347a4(Bitmap bitmap) {
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

    /* renamed from: b2 */
    public static Bitmap m211348b2(Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            t60.m214726f4("etzbzyzqxvqm", "⚠️ scaleDownBitmapLocked: Bitmap 已被回收");
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0) {
            t60.m214726f4("etzbzyzqxvqm", "⚠️ scaleDownBitmapLocked: Bitmap 尺寸无效 " + width + "x" + height);
            return null;
        }
        int i = (int) ((height / width) * 350);
        if (width <= 350) {
            return bitmap;
        }
        try {
            return Bitmap.createScaledBitmap(bitmap, 350, i, true);
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "⚠️ scaleDownBitmapLocked: 创建缩放 Bitmap 失败", e);
            return null;
        }
    }

    /* renamed from: a5 */
    public final Bitmap m211349a5(long j) {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.f52159a8 = j;
        try {
            dqtvuisjd dqtvuisjdVar = this.f52151a0;
            if (this.f52156a5.isShutdown() || this.f52156a5.isTerminated()) {
                t60.m214726f4("etzbzyzqxvqm", "⚠️ screenshotExecutor 已关闭，重新创建");
                this.f52156a5 = new ThreadPoolExecutor(1, 2, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            }
            dqtvuisjdVar.takeScreenshot(0, this.f52156a5, new vj1(ref$ObjectRef, countDownLatch, this));
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "takeScreenshot 调用失败", e);
            countDownLatch.countDown();
        }
        uj1 uj1Var = f52144b0;
        long j2 = uj1Var.isVivoDevice() ? 600L : 800L;
        if (countDownLatch.await(j2, TimeUnit.MILLISECONDS)) {
            if (ref$ObjectRef.f57626a0 != null) {
                long j3 = uj1Var.isVivoDevice() ? 350L : 300L;
                if (this.f52157a6 > j3) {
                    long j4 = (this.f52157a6 * 95) / 100;
                    if (j4 >= j3) {
                        j3 = j4;
                    }
                    this.f52157a6 = j3;
                }
            }
            return (Bitmap) ref$ObjectRef.f57626a0;
        }
        t60.m214726f4("etzbzyzqxvqm", "截图超时（" + j2 + "ms）interval=" + this.f52157a6 + "ms");
        return null;
    }

    /* renamed from: a6 */
    public final void m211350a6() {
        m211357b4();
        ReentrantLock reentrantLock = this.f52160a9;
        reentrantLock.lock();
        try {
            Bitmap bitmap = this.f52158a7;
            if (bitmap != null && !bitmap.isRecycled()) {
                try {
                    bitmap.recycle();
                } catch (Exception unused) {
                }
            }
            this.f52158a7 = null;
            reentrantLock.unlock();
            this.f52159a8 = 0L;
            uj1 uj1Var = f52144b0;
            this.f52157a6 = uj1Var.isVivoDevice() ? 350L : 300L;
            uj1Var.resetScreenshotSupport();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* renamed from: a7 */
    public final void m211351a7() {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new etzbzyzqxvqm$onMediaProjectionPermissionResult$1(this, null), 3);
    }

    /* renamed from: a8 */
    public final void m211352a8() {
        MediaDisplayService c0279a0;
        this.f52153a2 = true;
        m211355b1();
        if (!t60.m214686a2(this.f52154a3, "mediaprojection") || (c0279a0 = MediaDisplayService.f52303c1.getInstance()) == null) {
            return;
        }
        c0279a0.f52321b2 = true;
    }

    /* renamed from: a9 */
    public final void m211353a9() {
        dqtvuisjd dqtvuisjdVar = this.f52151a0;
        try {
            dqtvuisjd.f52358m1.setPermissionRequesting(true);
            t60.m214714d6("etzbzyzqxvqm", "📺 已设置权限请求标志，暂停防卸载检测");
            Intent intent = new Intent(dqtvuisjdVar, (Class<?>) qixvbtmo.class);
            intent.addFlags(268435456);
            intent.addFlags(67108864);
            intent.addFlags(536870912);
            if (Build.VERSION.SDK_INT >= 29) {
                intent.addFlags(524288);
            }
            dqtvuisjdVar.startActivity(intent);
            t60.m214714d6("etzbzyzqxvqm", "📺 已启动投屏权限请求 Activity");
        } catch (Exception e) {
            tz0.m214808a8("❌ 启动投屏权限请求失败: ", e.getMessage(), "etzbzyzqxvqm", e);
            dqtvuisjd.f52358m1.setPermissionRequesting(false);
        }
    }

    /* renamed from: b0 */
    public final void m211354b0() {
        MediaDisplayService c0279a0;
        this.f52153a2 = false;
        m211355b1();
        if (t60.m214686a2(this.f52154a3, "mediaprojection") && (c0279a0 = MediaDisplayService.f52303c1.getInstance()) != null) {
            c0279a0.f52321b2 = false;
        }
        if (this.f52152a1) {
            return;
        }
        m211356b3();
    }

    /* renamed from: b1 */
    public final void m211355b1() {
        try {
            this.f52151a0.getSharedPreferences("screen_capture_pause_state", 0).edit().putBoolean("is_paused", this.f52153a2).apply();
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.storm.safe.rock.manager.etzbzyzqxvqm$startMediaProjectionCapture$1, kotlin.jvm.internal.Lambda] */
    /* renamed from: b3 */
    public final void m211356b3() {
        if (this.f52152a1) {
            t60.m214726f4("etzbzyzqxvqm", "捕获已在运行");
            return;
        }
        if (this.f52153a2) {
            return;
        }
        String str = this.f52154a3;
        if (t60.m214686a2(str, "mediaprojection")) {
            MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
            if (!c0279a0.isProjecting()) {
                AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new etzbzyzqxvqm$startMediaProjectionCapture$2(this, null), 3);
                return;
            }
            this.f52152a1 = true;
            MediaDisplayService c0279a02 = c0279a0.getInstance();
            if (c0279a02 == null) {
                return;
            }
            c0279a02.f52323b4 = new h10() { // from class: com.storm.safe.rock.manager.etzbzyzqxvqm$startMediaProjectionCapture$1
                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    byte[] bArr = (byte[]) obj;
                    t60.m214695b6(bArr, "frameData");
                    C0263a5.m211346a3(this.f52176a0, bArr);
                    return C1351vv.f60710b1;
                }
            };
            return;
        }
        if (t60.m214686a2(str, f52150b6)) {
            u11 u11Var = this.f52155a4;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f52152a1 = true;
            uj1 uj1Var = f52144b0;
            long jMax = uj1Var.isVivoDevice() ? Math.max(uj1Var.getCaptureInterval(), 300L) : uj1Var.getCaptureInterval();
            if (uj1Var.isVivoDevice()) {
                t60.m214714d6("etzbzyzqxvqm", "📱 Vivo 设备检测到，使用安全截图间隔: " + jMax + "ms");
            }
            this.f52155a4 = AbstractC0780a0.m213692a3(AbstractC0385a0.f55230a1, null, new etzbzyzqxvqm$startAccessibilityCapture$1(this, jMax, null), 3);
        }
    }

    /* renamed from: b4 */
    public final void m211357b4() {
        u11 u11Var = this.f52155a4;
        if (u11Var != null) {
            u11Var.m215253a7(null);
        }
        this.f52155a4 = null;
        this.f52152a1 = false;
        this.f52153a2 = false;
        try {
            this.f52151a0.getSharedPreferences("screen_capture_pause_state", 0).edit().putBoolean("is_paused", false).apply();
        } catch (Exception unused) {
        }
        if (t60.m214686a2(this.f52154a3, "mediaprojection") || MediaDisplayService.f52303c1.isProjecting()) {
            try {
                MediaDisplayService.f52303c1.stop(this.f52151a0);
            } catch (Exception unused2) {
            }
        }
    }

    /* renamed from: b5 */
    public final void m211358b5() {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new etzbzyzqxvqm$switchToAccessibilityMode$1(this, null), 3);
    }
}
