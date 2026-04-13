package v0;

import java.io.IOException;
import java.util.ArrayList;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.k */
/* loaded from: classes.dex */
public final class C0940k extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2176b;

    /* renamed from: c */
    public final /* synthetic */ C0948s f2177c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0940k(C0948s c0948s, Object[] objArr, int i2, ArrayList arrayList, boolean z2) {
        super(objArr, "OkHttp %s Push Headers[%s]");
        this.f2177c = c0948s;
        this.f2176b = i2;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        this.f2177c.f2209j.getClass();
        try {
            this.f2177c.f2220u.m1437C(this.f2176b, EnumC0931b.CANCEL);
            synchronized (this.f2177c) {
                this.f2177c.f2222w.remove(Integer.valueOf(this.f2176b));
            }
        } catch (IOException unused) {
        }
    }
}
