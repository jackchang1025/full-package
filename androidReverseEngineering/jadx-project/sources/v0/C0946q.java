package v0;

import java.io.IOException;
import q0.AbstractC0887c;
import q0.AbstractRunnableC0885a;
import w0.C0966i;

/* renamed from: v0.q */
/* loaded from: classes.dex */
public final class C0946q extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2193b = 0;

    /* renamed from: c */
    public final Object f2194c;

    /* renamed from: d */
    public final /* synthetic */ Object f2195d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0946q(C0946q c0946q, Object[] objArr, C0954y c0954y) {
        super(objArr, "OkHttp %s stream %d");
        this.f2195d = c0946q;
        this.f2194c = c0954y;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        EnumC0931b enumC0931b;
        EnumC0931b enumC0931b2 = EnumC0931b.PROTOCOL_ERROR;
        int i2 = this.f2193b;
        Object obj = this.f2195d;
        Object obj2 = this.f2194c;
        switch (i2) {
            case 0:
                try {
                    ((C0948s) ((C0946q) obj).f2195d).f2201b.mo1349b((C0954y) obj2);
                    return;
                } catch (IOException e2) {
                    C0966i.f2293a.mo1455m(4, "Http2Connection.Listener failure for " + ((C0948s) ((C0946q) obj).f2195d).f2203d, e2);
                    try {
                        ((C0954y) obj2).m1428c(enumC0931b2, e2);
                        return;
                    } catch (IOException unused) {
                        return;
                    }
                }
            default:
                EnumC0931b enumC0931b3 = EnumC0931b.INTERNAL_ERROR;
                try {
                    try {
                        ((C0951v) obj2).m1424z(this);
                        while (((C0951v) obj2).m1423y(false, this)) {
                        }
                        enumC0931b = EnumC0931b.NO_ERROR;
                        try {
                            ((C0948s) obj).m1415x(enumC0931b, EnumC0931b.CANCEL, null);
                        } catch (Throwable th) {
                            th = th;
                            ((C0948s) obj).m1415x(enumC0931b, enumC0931b3, null);
                            AbstractC0887c.m1306c((C0951v) obj2);
                            throw th;
                        }
                    } catch (IOException e3) {
                        ((C0948s) obj).m1415x(enumC0931b2, enumC0931b2, e3);
                    }
                    AbstractC0887c.m1306c((C0951v) obj2);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    enumC0931b = enumC0931b3;
                }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0946q(C0948s c0948s, C0951v c0951v) {
        super(new Object[]{c0948s.f2203d}, "OkHttp %s");
        this.f2195d = c0948s;
        this.f2194c = c0951v;
    }
}
