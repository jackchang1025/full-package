package h0;

import com.guard.wallet.http.C0203h;
import f0.C0291l;
import f0.C0292m;
import f0.C0299t;
import java.nio.charset.Charset;

/* renamed from: h0.e */
/* loaded from: classes.dex */
public final /* synthetic */ class C0323e implements InterfaceC0325g, InterfaceC0327i {

    /* renamed from: d */
    public final /* synthetic */ Object f632d;

    /* renamed from: e */
    public final /* synthetic */ Object f633e;

    public /* synthetic */ C0323e(Object obj, Object obj2) {
        this.f632d = obj;
        this.f633e = obj2;
    }

    @Override // h0.InterfaceC0327i
    /* renamed from: a */
    public final Object mo866a(Object obj) {
        C0203h c0203h = (C0203h) this.f632d;
        String str = (String) this.f633e;
        C0292m c0292m = (C0292m) obj;
        Charset charset = (Charset) c0203h.f245e;
        if (charset == null && str != null) {
            charset = Charset.forName(str);
        }
        String m809h = c0292m.m809h(charset);
        c0292m.m811k();
        return m809h;
    }

    @Override // h0.InterfaceC0325g
    /* renamed from: b */
    public final void mo799b(Exception e2, Object obj, C0299t c0299t) {
        FutureC0326h futureC0326h = (FutureC0326h) this.f632d;
        C0291l c0291l = (C0291l) this.f633e;
        if (e2 == null) {
            try {
                FutureC0326h futureC0326h2 = new FutureC0326h(((InterfaceC0327i) c0291l.f531d).mo866a(obj));
                synchronized (futureC0326h) {
                    if (!futureC0326h.f629a) {
                        futureC0326h.f631c = futureC0326h2;
                    }
                }
                futureC0326h2.m870f(c0299t, new C0324f(futureC0326h, new FutureC0326h()));
                return;
            } catch (Exception e3) {
                e2 = e3;
            }
        }
        futureC0326h.m871g(e2, null, c0299t);
    }
}
