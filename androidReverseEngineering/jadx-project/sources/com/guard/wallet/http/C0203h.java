package com.guard.wallet.http;

import a1.AbstractC0026q;
import b0.C0078b;
import com.guard.wallet.entity.CookieVO;
import com.guard.wallet.entity.HostCookies;
import com.guard.wallet.thread.C0241j;
import com.guard.wallet.utils.AbstractC0252h;
import f0.AbstractC0296q;
import f0.C0291l;
import f0.C0292m;
import f0.InterfaceC0294o;
import f0.InterfaceC0301v;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import h0.C0323e;
import h0.FutureC0326h;
import i0.C0332c;
import i0.C0334e;
import j0.C0352b;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import k0.C0360d;
import k0.C0361e;
import n0.C0409e;
import com.guard.wallet.entity.BuildConfig;
import p0.C0871m;
import p0.C0879u;
import p0.InterfaceC0872n;
import p0.m0;
import s0.C0905h;

/* renamed from: com.guard.wallet.http.h */
/* loaded from: classes.dex */
public final class C0203h implements InterfaceC0872n, InterfaceC0309a, InterfaceC0310b, InterfaceC0301v {

    /* renamed from: d */
    public final /* synthetic */ int f244d;

    /* renamed from: e */
    public Object f245e;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0203h() {
        this(2, 0);
        this.f244d = 2;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        ((AbstractC0296q) this.f245e).mo813c(exc);
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        c0292m.m805c(((C0352b) ((C0241j) this.f245e).f388f).f693l);
    }

    @Override // f0.InterfaceC0301v
    /* renamed from: c */
    public final void mo391c(Object obj) {
        byte[] bArr = (byte[]) obj;
        C0361e c0361e = ((C0360d) this.f245e).f710e;
        if (c0361e.f712e) {
            c0361e.f715h.f717l.update(bArr, 0, bArr.length);
        }
        ((C0360d) this.f245e).f710e.m939a();
    }

    @Override // p0.InterfaceC0872n
    /* renamed from: d */
    public final List mo295d(C0879u c0879u) {
        String m708l = AbstractC0252h.m708l("Cookies:".concat(c0879u.f1910d));
        HostCookies hostCookies = !AbstractC0026q.m151B(m708l) ? (HostCookies) AbstractC0252h.m700d(m708l, HostCookies.class) : null;
        if (hostCookies == null) {
            hostCookies = new HostCookies();
            hostCookies.setHost(c0879u.f1910d);
        }
        return hostCookies.loadForRequest();
    }

    @Override // p0.InterfaceC0872n
    /* renamed from: e */
    public final void mo296e(C0879u c0879u, List list) {
        if (list.isEmpty()) {
            return;
        }
        String concat = "Cookies:".concat(c0879u.f1910d);
        String m708l = AbstractC0252h.m708l(concat);
        HostCookies hostCookies = !AbstractC0026q.m151B(m708l) ? (HostCookies) AbstractC0252h.m700d(m708l, HostCookies.class) : null;
        if (hostCookies == null) {
            hostCookies = new HostCookies();
            hostCookies.setHost(c0879u.f1910d);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0871m c0871m = (C0871m) it.next();
            CookieVO cookieVO = new CookieVO(c0871m.f1865a, c0871m.f1866b, Long.valueOf(c0871m.f1867c), c0871m.f1868d, c0871m.f1869e, Boolean.valueOf(c0871m.f1870f), Boolean.valueOf(c0871m.f1871g), Boolean.valueOf(c0871m.f1872h), Boolean.valueOf(c0871m.f1873i));
            int indexOf = hostCookies.getCookies().indexOf(cookieVO);
            if (indexOf >= 0) {
                hostCookies.getCookies().set(indexOf, cookieVO);
            } else {
                hostCookies.getCookies().add(cookieVO);
            }
        }
        AbstractC0252h.m683D(AbstractC0252h.m693N(hostCookies), concat);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: f */
    public final void m392f(String str, String str2) {
        String lowerCase = str.toLowerCase(Locale.US);
        C0334e c0334e = (C0334e) this.f245e;
        List list = (List) c0334e.get(lowerCase);
        if (list == null) {
            list = c0334e.mo873b();
            c0334e.put(lowerCase, list);
        }
        list.add(str2);
        C0409e c0409e = (C0409e) ((C0334e) this.f245e).get(lowerCase);
        synchronized (c0409e) {
            if (c0409e.f819a == null) {
                c0409e.f819a = str;
            }
        }
    }

    /* renamed from: g */
    public final void m393g(String str) {
        if (str != null) {
            String[] split = str.trim().split(":", 2);
            if (split.length == 2) {
                m392f(split[0].trim(), split[1].trim());
            } else {
                m392f(split[0].trim(), BuildConfig.FLAVOR);
            }
        }
    }

    /* renamed from: h */
    public final synchronized void m394h(m0 m0Var) {
        ((Set) this.f245e).remove(m0Var);
    }

    /* renamed from: i */
    public final String m395i(String str) {
        return ((C0334e) this.f245e).m875a(str.toLowerCase(Locale.US));
    }

    /* renamed from: j */
    public final FutureC0326h m396j(AbstractC0296q abstractC0296q) {
        String mo782g = abstractC0296q.mo782g();
        FutureC0326h m297f = new C0078b(27).m297f(abstractC0296q);
        C0291l c0291l = new C0291l(new C0323e(this, mo782g));
        FutureC0326h futureC0326h = new FutureC0326h();
        synchronized (futureC0326h) {
            if (!futureC0326h.f629a) {
                futureC0326h.f631c = m297f;
            }
        }
        m297f.m870f(null, new C0323e(futureC0326h, c0291l));
        return futureC0326h;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: k */
    public final void m397k(String str, String str2) {
        if (str2 != null && (str2.contains("\n") || str2.contains("\r"))) {
            throw new IllegalArgumentException("value must not contain a new line or line feed");
        }
        String lowerCase = str.toLowerCase(Locale.US);
        C0334e c0334e = (C0334e) this.f245e;
        List mo873b = c0334e.mo873b();
        mo873b.add(str2);
        c0334e.put(lowerCase, mo873b);
        C0409e c0409e = (C0409e) ((C0334e) this.f245e).get(lowerCase);
        synchronized (c0409e) {
            if (c0409e.f819a == null) {
                c0409e.f819a = str;
            }
        }
    }

    /* renamed from: l */
    public final String m398l(String str) {
        return m399m().insert(0, str + "\r\n").toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: m */
    public final StringBuilder m399m() {
        Object obj;
        StringBuilder sb = new StringBuilder(256);
        Iterator it = ((C0334e) this.f245e).keySet().iterator();
        while (it.hasNext()) {
            C0409e c0409e = (C0409e) ((C0334e) this.f245e).get((String) it.next());
            Iterator<E> it2 = c0409e.iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                synchronized (c0409e) {
                    obj = c0409e.f819a;
                }
                sb.append((String) obj);
                sb.append(": ");
                sb.append(str);
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
        return sb;
    }

    public final String toString() {
        switch (this.f244d) {
            case 4:
                return m399m().toString();
            default:
                return super.toString();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C0203h(int i2) {
        this(TimeUnit.MINUTES);
        this.f244d = i2;
        if (i2 == 4) {
            this.f245e = new C0332c();
            return;
        }
        if (i2 == 8) {
        } else if (i2 != 9) {
            this.f245e = new Semaphore(0);
        } else {
            this.f245e = new LinkedHashSet();
        }
    }

    public /* synthetic */ C0203h(int i2, int i3) {
        this.f244d = i2;
    }

    public /* synthetic */ C0203h(Object obj, int i2) {
        this.f244d = i2;
        this.f245e = obj;
    }

    public C0203h(TimeUnit timeUnit) {
        this.f244d = 8;
        this.f245e = new C0905h(timeUnit);
    }
}
