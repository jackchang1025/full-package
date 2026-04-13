package k0;

import com.guard.wallet.http.C0203h;
import f0.C0292m;
import f0.C0300u;
import f0.InterfaceC0294o;
import f0.InterfaceC0301v;
import g0.InterfaceC0310b;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: k0.d */
/* loaded from: classes.dex */
public final class C0360d implements InterfaceC0301v, InterfaceC0310b {

    /* renamed from: d */
    public final /* synthetic */ int f709d;

    /* renamed from: e */
    public final /* synthetic */ C0361e f710e;

    public /* synthetic */ C0360d(C0361e c0361e, int i2) {
        this.f709d = i2;
        this.f710e = c0361e;
    }

    /* renamed from: a */
    public final void m938a(byte[] bArr) {
        int i2 = this.f709d;
        C0361e c0361e = this.f710e;
        switch (i2) {
            case 0:
                if (c0361e.f712e) {
                    c0361e.f715h.f717l.update(bArr, 0, 2);
                }
                c0361e.f714g.f560d.add(new C0300u(C0362f.m940l(bArr, ByteOrder.LITTLE_ENDIAN) & 65535, new C0203h(this, 6)));
                break;
            default:
                short m940l = C0362f.m940l(bArr, ByteOrder.LITTLE_ENDIAN);
                short value = (short) c0361e.f715h.f717l.getValue();
                C0362f c0362f = c0361e.f715h;
                if (value == m940l) {
                    c0362f.f717l.reset();
                    c0362f.f716k = false;
                    c0362f.m814i(c0361e.f713f);
                    break;
                } else {
                    c0362f.mo813c(new IOException("CRC mismatch"));
                    break;
                }
        }
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        C0361e c0361e = this.f710e;
        boolean z2 = c0361e.f712e;
        C0362f c0362f = c0361e.f715h;
        if (z2) {
            while (c0292m.f539a.size() > 0) {
                ByteBuffer m812l = c0292m.m812l();
                c0362f.f717l.update(m812l.array(), m812l.position() + m812l.arrayOffset(), m812l.remaining());
                C0292m.m802j(m812l);
            }
        }
        c0292m.m811k();
        if (c0361e.f712e) {
            c0361e.f714g.f560d.add(new C0300u(2, new C0360d(c0361e, 2)));
        } else {
            c0362f.f716k = false;
            c0362f.m814i(c0361e.f713f);
        }
    }

    @Override // f0.InterfaceC0301v
    /* renamed from: c */
    public final /* bridge */ /* synthetic */ void mo391c(Object obj) {
        switch (this.f709d) {
            case 0:
                m938a((byte[]) obj);
                break;
            default:
                m938a((byte[]) obj);
                break;
        }
    }
}
