package v0;

import java.io.IOException;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.h */
/* loaded from: classes.dex */
public final class C0937h extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ int f2167b;

    /* renamed from: c */
    public final /* synthetic */ int f2168c;

    /* renamed from: d */
    public final /* synthetic */ Object f2169d;

    /* renamed from: e */
    public final /* synthetic */ C0948s f2170e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0937h(C0948s c0948s, String str, Object[] objArr, int i2, Object obj, int i3) {
        super(objArr, str);
        this.f2167b = i3;
        this.f2170e = c0948s;
        this.f2168c = i2;
        this.f2169d = obj;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        switch (this.f2167b) {
            case 0:
                C0948s c0948s = this.f2170e;
                try {
                    c0948s.f2220u.m1437C(this.f2168c, (EnumC0931b) this.f2169d);
                    return;
                } catch (IOException e2) {
                    c0948s.m1416y(e2);
                    return;
                }
            case 1:
                this.f2170e.f2209j.getClass();
                synchronized (this.f2170e) {
                    this.f2170e.f2222w.remove(Integer.valueOf(this.f2168c));
                }
                return;
            default:
                this.f2170e.f2209j.getClass();
                try {
                    this.f2170e.f2220u.m1437C(this.f2168c, EnumC0931b.CANCEL);
                    synchronized (this.f2170e) {
                        this.f2170e.f2222w.remove(Integer.valueOf(this.f2168c));
                    }
                    return;
                } catch (IOException unused) {
                    return;
                }
        }
    }
}
