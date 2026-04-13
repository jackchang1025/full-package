package v0;

import a1.C0014e;
import java.io.IOException;
import p0.C0875q;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.l */
/* loaded from: classes.dex */
public final class C0941l extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2178b;

    /* renamed from: c */
    public final /* synthetic */ C0014e f2179c;

    /* renamed from: d */
    public final /* synthetic */ int f2180d;

    /* renamed from: e */
    public final /* synthetic */ C0948s f2181e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0941l(C0948s c0948s, Object[] objArr, int i2, C0014e c0014e, int i3, boolean z2) {
        super(objArr, "OkHttp %s Push Data[%s]");
        this.f2181e = c0948s;
        this.f2178b = i2;
        this.f2179c = c0014e;
        this.f2180d = i3;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        try {
            C0875q c0875q = this.f2181e.f2209j;
            C0014e c0014e = this.f2179c;
            int i2 = this.f2180d;
            c0875q.getClass();
            c0014e.skip(i2);
            this.f2181e.f2220u.m1437C(this.f2178b, EnumC0931b.CANCEL);
            synchronized (this.f2181e) {
                this.f2181e.f2222w.remove(Integer.valueOf(this.f2178b));
            }
        } catch (IOException unused) {
        }
    }
}
