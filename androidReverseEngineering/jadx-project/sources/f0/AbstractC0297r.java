package f0;

import g0.InterfaceC0309a;
import g0.InterfaceC0311c;
import java.nio.ByteBuffer;
import p012o.RunnableC0412a;

/* renamed from: f0.r */
/* loaded from: classes.dex */
public abstract class AbstractC0297r implements InterfaceC0295p {

    /* renamed from: d */
    public InterfaceC0295p f548d;

    /* renamed from: e */
    public final C0292m f549e = new C0292m();

    /* renamed from: f */
    public InterfaceC0311c f550f;

    /* renamed from: g */
    public int f551g;

    public AbstractC0297r(InterfaceC0290k interfaceC0290k) {
        this.f551g = Integer.MAX_VALUE;
        this.f548d = interfaceC0290k;
        ((C0281b) interfaceC0290k).f495j = new C0291l(this);
        this.f551g = 0;
    }

    /* renamed from: a */
    public static void m815a(C0292m c0292m) {
        c0292m.m804b(ByteBuffer.wrap((Integer.toString(c0292m.f541c, 16) + "\r\n").getBytes()));
        c0292m.m803a(ByteBuffer.wrap("\r\n".getBytes()));
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: b */
    public final C0289j mo777b() {
        return this.f548d.mo777b();
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: c */
    public final void mo778c(C0292m c0292m) {
        if (mo777b().f530e != Thread.currentThread()) {
            synchronized (this.f549e) {
                if (this.f549e.f541c < this.f551g) {
                    m815a(c0292m);
                    c0292m.m805c(this.f549e);
                    mo777b().m796c(new RunnableC0412a(this, 6));
                }
            }
            return;
        }
        m815a(c0292m);
        if (!(this.f549e.f541c > 0)) {
            this.f548d.mo778c(c0292m);
        }
        synchronized (this.f549e) {
            c0292m.m805c(this.f549e);
        }
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: d */
    public final void mo779d(InterfaceC0311c interfaceC0311c) {
        this.f550f = interfaceC0311c;
    }

    /* renamed from: e */
    public final void m816e() {
        boolean z2;
        InterfaceC0311c interfaceC0311c;
        synchronized (this.f549e) {
            this.f548d.mo778c(this.f549e);
            z2 = this.f549e.f541c == 0;
        }
        if (!z2 || (interfaceC0311c = this.f550f) == null) {
            return;
        }
        interfaceC0311c.mo800c();
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: f */
    public final void mo781f(InterfaceC0309a interfaceC0309a) {
        this.f548d.mo781f(interfaceC0309a);
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: i */
    public final InterfaceC0311c mo784i() {
        return this.f550f;
    }
}
