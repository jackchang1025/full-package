package k0;

import f0.AbstractC0296q;
import f0.C0292m;
import f0.InterfaceC0294o;
import i0.C0331b;

/* renamed from: k0.c */
/* loaded from: classes.dex */
public final class C0359c extends AbstractC0296q {

    /* renamed from: i */
    public final long f706i;

    /* renamed from: j */
    public long f707j;

    /* renamed from: k */
    public final C0292m f708k = new C0292m();

    public C0359c(long j2) {
        this.f706i = j2;
    }

    @Override // f0.AbstractC0296q, g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        int i2 = c0292m.f541c;
        long j2 = this.f707j;
        long j3 = this.f706i;
        int min = (int) Math.min(j3 - j2, i2);
        C0292m c0292m2 = this.f708k;
        c0292m.m806d(c0292m2, min);
        int i3 = c0292m2.f541c;
        super.mo294b(interfaceC0294o, c0292m2);
        this.f707j += i3 - c0292m2.f541c;
        c0292m2.m805c(c0292m);
        if (this.f707j == j3) {
            mo813c(null);
        }
    }

    @Override // f0.AbstractC0296q
    /* renamed from: c */
    public final void mo813c(Exception exc) {
        if (exc == null) {
            long j2 = this.f707j;
            long j3 = this.f706i;
            if (j2 != j3) {
                exc = new C0331b("End of data reached before content length was read: " + this.f707j + "/" + j3 + " Paused: " + mo780e());
            }
        }
        super.mo813c(exc);
    }
}
