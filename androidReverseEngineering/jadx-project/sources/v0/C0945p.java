package v0;

import java.io.IOException;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.p */
/* loaded from: classes.dex */
public final class C0945p extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final boolean f2189b;

    /* renamed from: c */
    public final int f2190c;

    /* renamed from: d */
    public final int f2191d;

    /* renamed from: e */
    public final /* synthetic */ C0948s f2192e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0945p(C0948s c0948s, int i2, int i3) {
        super(new Object[]{c0948s.f2203d, Integer.valueOf(i2), Integer.valueOf(i3)}, "OkHttp %s ping %08x%08x");
        this.f2192e = c0948s;
        this.f2189b = true;
        this.f2190c = i2;
        this.f2191d = i3;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        int i2 = this.f2190c;
        int i3 = this.f2191d;
        boolean z2 = this.f2189b;
        C0948s c0948s = this.f2192e;
        c0948s.getClass();
        try {
            c0948s.f2220u.m1436B(z2, i2, i3);
        } catch (IOException e2) {
            c0948s.m1416y(e2);
        }
    }
}
