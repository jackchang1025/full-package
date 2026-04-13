package p0;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import q0.AbstractRunnableC0885a;
import s0.C0909l;
import w0.C0966i;

/* loaded from: classes.dex */
public final class d0 extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final InterfaceC0863e f1768b;

    /* renamed from: c */
    public volatile AtomicInteger f1769c;

    /* renamed from: d */
    public final /* synthetic */ e0 f1770d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d0(e0 e0Var, InterfaceC0863e interfaceC0863e) {
        super(new Object[]{e0Var.f1773c.f1777a.m1298m()}, "OkHttp %s");
        this.f1770d = e0Var;
        this.f1769c = new AtomicInteger(0);
        this.f1768b = interfaceC0863e;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        InterfaceC0863e interfaceC0863e = this.f1768b;
        e0 e0Var = this.f1770d;
        C0909l c0909l = e0Var.f1772b;
        b0 b0Var = e0Var.f1771a;
        c0909l.f2057e.m71i();
        boolean z2 = false;
        try {
            try {
            } catch (Throwable th) {
                b0Var.f1719a.m1274b(this);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            interfaceC0863e.mo390d(e0Var, e0Var.m1249c());
        } catch (IOException e3) {
            e = e3;
            z2 = true;
            if (z2) {
                C0966i.f2293a.mo1455m(4, "Callback failure for " + e0Var.m1250e(), e);
            } else {
                interfaceC0863e.mo389b(e0Var, e);
            }
            b0Var.f1719a.m1274b(this);
        } catch (Throwable th3) {
            th = th3;
            z2 = true;
            e0Var.f1772b.m1362a();
            if (!z2) {
                IOException iOException = new IOException("canceled due to " + th);
                iOException.addSuppressed(th);
                interfaceC0863e.mo389b(e0Var, iOException);
            }
            throw th;
        }
        b0Var.f1719a.m1274b(this);
    }
}
