package v0;

import java.io.IOException;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.i */
/* loaded from: classes.dex */
public final class C0938i extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2171b;

    /* renamed from: c */
    public final /* synthetic */ long f2172c;

    /* renamed from: d */
    public final /* synthetic */ C0948s f2173d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0938i(C0948s c0948s, Object[] objArr, int i2, long j2) {
        super(objArr, "OkHttp Window Update %s stream %d");
        this.f2173d = c0948s;
        this.f2171b = i2;
        this.f2172c = j2;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        C0948s c0948s = this.f2173d;
        try {
            c0948s.f2220u.m1438D(this.f2171b, this.f2172c);
        } catch (IOException e2) {
            c0948s.m1416y(e2);
        }
    }
}
