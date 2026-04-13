package l0;

import a1.AbstractC0026q;
import android.text.TextUtils;
import b0.C0078b;
import com.guard.wallet.http.C0203h;
import f0.C0281b;
import f0.C0289j;
import f0.C0292m;
import f0.InterfaceC0290k;
import f0.InterfaceC0295p;
import g0.InterfaceC0309a;
import g0.InterfaceC0311c;
import i0.C0334e;
import i0.EnumC0337h;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import k0.C0358b;
import com.guard.wallet.entity.BuildConfig;
import p012o.RunnableC0412a;

/* renamed from: l0.k */
/* loaded from: classes.dex */
public abstract class AbstractC0381k implements InterfaceC0295p, InterfaceC0309a {

    /* renamed from: d */
    public final C0203h f763d;

    /* renamed from: e */
    public long f764e;

    /* renamed from: f */
    public final InterfaceC0290k f765f;

    /* renamed from: g */
    public final AbstractC0378h f766g;

    /* renamed from: h */
    public boolean f767h;

    /* renamed from: i */
    public InterfaceC0295p f768i;

    /* renamed from: j */
    public InterfaceC0311c f769j;

    /* renamed from: k */
    public boolean f770k;

    /* renamed from: l */
    public int f771l;

    /* renamed from: m */
    public final String f772m;

    /* renamed from: n */
    public InterfaceC0309a f773n;

    public AbstractC0381k(InterfaceC0290k interfaceC0290k, AbstractC0378h abstractC0378h) {
        C0203h c0203h = new C0203h(4);
        this.f763d = c0203h;
        this.f764e = -1L;
        this.f767h = false;
        this.f771l = 200;
        this.f772m = "HTTP/1.1";
        this.f765f = interfaceC0290k;
        this.f766g = abstractC0378h;
        EnumC0337h enumC0337h = EnumC0337h.f649b;
        String m395i = abstractC0378h.f751j.m395i("Connection");
        if (m395i == null ? true : "keep-alive".equalsIgnoreCase(m395i)) {
            c0203h.m397k("Connection", "Keep-Alive");
        }
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        mo787l();
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: b */
    public final C0289j mo777b() {
        return ((C0281b) this.f765f).f491f;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: c */
    public final void mo778c(C0292m c0292m) {
        InterfaceC0295p interfaceC0295p;
        if (!this.f767h) {
            m951e();
        }
        if (c0292m.f541c == 0 || (interfaceC0295p = this.f768i) == null) {
            return;
        }
        interfaceC0295p.mo778c(c0292m);
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: d */
    public final void mo779d(InterfaceC0311c interfaceC0311c) {
        InterfaceC0295p interfaceC0295p = this.f768i;
        if (interfaceC0295p != null) {
            interfaceC0295p.mo779d(interfaceC0311c);
        } else {
            this.f769j = interfaceC0311c;
        }
    }

    /* renamed from: e */
    public final void m951e() {
        final boolean z2;
        if (this.f767h) {
            return;
        }
        this.f767h = true;
        C0203h c0203h = this.f763d;
        String m395i = c0203h.m395i("Transfer-Encoding");
        if (BuildConfig.FLAVOR.equals(m395i)) {
        }
        boolean z3 = ("Chunked".equalsIgnoreCase(m395i) || m395i == null) && !"close".equalsIgnoreCase(c0203h.m395i("Connection"));
        if (this.f764e < 0) {
            String m395i2 = c0203h.m395i("Content-Length");
            if (!TextUtils.isEmpty(m395i2)) {
                this.f764e = Long.valueOf(m395i2).longValue();
            }
        }
        if (this.f764e >= 0 || !z3) {
            z2 = false;
        } else {
            c0203h.m397k("Transfer-Encoding", "Chunked");
            z2 = true;
        }
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[3];
        objArr[0] = this.f772m;
        objArr[1] = Integer.valueOf(this.f771l);
        String str = (String) C0376f.f745e.get(Integer.valueOf(this.f771l));
        if (str == null) {
            str = "Unknown";
        }
        objArr[2] = str;
        AbstractC0026q.m169T(this.f765f, c0203h.m398l(String.format(locale, "%s %s %s", objArr)).getBytes(), new InterfaceC0309a() { // from class: l0.j
            @Override // g0.InterfaceC0309a
            /* renamed from: a */
            public final void mo293a(Exception exc) {
                AbstractC0381k abstractC0381k = AbstractC0381k.this;
                if (exc != null) {
                    abstractC0381k.getClass();
                    C0281b c0281b = (C0281b) ((C0372b) abstractC0381k).f732o.f742x;
                    c0281b.f496k = new C0078b(24);
                    c0281b.f500o = new C0078b(23);
                    c0281b.close();
                    return;
                }
                InterfaceC0290k interfaceC0290k = abstractC0381k.f765f;
                if (z2) {
                    C0358b c0358b = new C0358b(interfaceC0290k);
                    c0358b.f551g = 0;
                    abstractC0381k.f768i = c0358b;
                } else {
                    abstractC0381k.f768i = interfaceC0290k;
                }
                abstractC0381k.f768i.mo781f(abstractC0381k.f773n);
                abstractC0381k.f773n = null;
                abstractC0381k.f768i.mo779d(abstractC0381k.f769j);
                abstractC0381k.f769j = null;
                if (abstractC0381k.f770k) {
                    abstractC0381k.mo787l();
                } else {
                    abstractC0381k.mo777b().m796c(new RunnableC0412a(abstractC0381k, 7));
                }
            }
        });
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: f */
    public final void mo781f(InterfaceC0309a interfaceC0309a) {
        InterfaceC0295p interfaceC0295p = this.f768i;
        if (interfaceC0295p != null) {
            interfaceC0295p.mo781f(interfaceC0309a);
        } else {
            this.f773n = interfaceC0309a;
        }
    }

    /* renamed from: g */
    public abstract void mo946g();

    /* renamed from: h */
    public final void m952h(String str) {
        String m395i = this.f763d.m395i("Content-Type");
        if (m395i == null) {
            m395i = "text/html; charset=utf-8";
        }
        try {
            mo777b().m796c(new RunnableC0379i(this, new C0292m(str.getBytes("UTF-8")), m395i, 0));
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: i */
    public final InterfaceC0311c mo784i() {
        InterfaceC0295p interfaceC0295p = this.f768i;
        return interfaceC0295p != null ? interfaceC0295p.mo784i() : this.f769j;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: l */
    public final void mo787l() {
        if (this.f770k) {
            return;
        }
        this.f770k = true;
        boolean z2 = this.f767h;
        if (z2 && this.f768i == null) {
            return;
        }
        int i2 = 0;
        if (!z2) {
            C0203h c0203h = this.f763d;
            c0203h.getClass();
            Locale locale = Locale.US;
            List list = (List) ((C0334e) c0203h.f245e).remove("Transfer-Encoding".toLowerCase(locale).toLowerCase(locale));
            if (list != null && list.size() != 0) {
            }
        }
        InterfaceC0295p interfaceC0295p = this.f768i;
        if (interfaceC0295p instanceof C0358b) {
            interfaceC0295p.mo787l();
            return;
        }
        if (!this.f767h) {
            if (!this.f766g.f755n.equalsIgnoreCase("HEAD")) {
                String str = "text/html";
                try {
                    mo777b().m796c(new RunnableC0379i(this, new C0292m(BuildConfig.FLAVOR.getBytes("UTF-8")), str, i2));
                    return;
                } catch (UnsupportedEncodingException e2) {
                    throw new AssertionError(e2);
                }
            }
            m951e();
        }
        mo946g();
    }

    public final String toString() {
        C0203h c0203h = this.f763d;
        if (c0203h == null) {
            return super.toString();
        }
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[3];
        objArr[0] = this.f772m;
        objArr[1] = Integer.valueOf(this.f771l);
        String str = (String) C0376f.f745e.get(Integer.valueOf(this.f771l));
        if (str == null) {
            str = "Unknown";
        }
        objArr[2] = str;
        return c0203h.m398l(String.format(locale, "%s %s %s", objArr));
    }
}
