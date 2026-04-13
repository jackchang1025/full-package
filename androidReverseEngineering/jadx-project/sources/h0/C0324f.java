package h0;

import f0.C0299t;
import java.util.concurrent.CancellationException;

/* renamed from: h0.f */
/* loaded from: classes.dex */
public final /* synthetic */ class C0324f implements InterfaceC0325g, InterfaceC0320b {

    /* renamed from: d */
    public final /* synthetic */ FutureC0326h f634d;

    /* renamed from: e */
    public final /* synthetic */ FutureC0326h f635e;

    public /* synthetic */ C0324f(FutureC0326h futureC0326h, FutureC0326h futureC0326h2) {
        this.f634d = futureC0326h;
        this.f635e = futureC0326h2;
    }

    @Override // h0.InterfaceC0320b
    /* renamed from: a */
    public final void mo587a(Exception exc, Object obj) {
        this.f635e.m871g(this.f634d.m871g(exc, obj, null) ? null : new CancellationException(), null, null);
    }

    @Override // h0.InterfaceC0325g
    /* renamed from: b */
    public final void mo799b(Exception exc, Object obj, C0299t c0299t) {
        this.f635e.m871g(this.f634d.m871g(exc, obj, c0299t) ? null : new CancellationException(), obj, c0299t);
    }
}
