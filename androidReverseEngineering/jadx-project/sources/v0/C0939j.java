package v0;

import java.io.IOException;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.j */
/* loaded from: classes.dex */
public final class C0939j extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2174b;

    /* renamed from: c */
    public final /* synthetic */ Object f2175c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0939j(Object obj, String str, Object[] objArr, int i2) {
        super(objArr, str);
        this.f2174b = i2;
        this.f2175c = obj;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        Object obj;
        boolean z2;
        switch (this.f2174b) {
            case 0:
                C0948s c0948s = (C0948s) this.f2175c;
                c0948s.getClass();
                try {
                    c0948s.f2220u.m1436B(false, 2, 0);
                    return;
                } catch (IOException e2) {
                    c0948s.m1416y(e2);
                    return;
                }
            case 1:
                synchronized (((C0948s) this.f2175c)) {
                    obj = this.f2175c;
                    if (((C0948s) obj).f2211l < ((C0948s) obj).f2210k) {
                        z2 = true;
                    } else {
                        ((C0948s) obj).f2210k++;
                        z2 = false;
                    }
                }
                C0948s c0948s2 = (C0948s) obj;
                if (z2) {
                    c0948s2.m1416y(null);
                    return;
                }
                c0948s2.getClass();
                try {
                    c0948s2.f2220u.m1436B(false, 1, 0);
                    return;
                } catch (IOException e3) {
                    c0948s2.m1416y(e3);
                    return;
                }
            default:
                C0948s c0948s3 = (C0948s) ((C0946q) this.f2175c).f2195d;
                c0948s3.f2201b.mo1348a(c0948s3);
                return;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0939j(C0948s c0948s) {
        super(new Object[]{c0948s.f2203d}, "OkHttp %s ping");
        this.f2174b = 1;
        this.f2175c = c0948s;
    }
}
