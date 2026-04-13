package f0;

import g0.InterfaceC0310b;
import java.nio.ByteBuffer;
import k0.C0360d;

/* renamed from: f0.w */
/* loaded from: classes.dex */
public final class C0302w extends AbstractC0303x {

    /* renamed from: b */
    public final byte f557b;

    /* renamed from: c */
    public final InterfaceC0310b f558c;

    public C0302w(C0360d c0360d) {
        super(1);
        this.f557b = (byte) 0;
        this.f558c = c0360d;
    }

    @Override // f0.AbstractC0303x
    /* renamed from: a */
    public final AbstractC0303x mo820a(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        C0292m c0292m2 = new C0292m();
        boolean z2 = true;
        while (true) {
            if (c0292m.f539a.size() <= 0) {
                break;
            }
            ByteBuffer m812l = c0292m.m812l();
            m812l.mark();
            int i2 = 0;
            while (m812l.remaining() > 0) {
                z2 = m812l.get() == this.f557b;
                if (z2) {
                    break;
                }
                i2++;
            }
            m812l.reset();
            if (z2) {
                c0292m.m804b(m812l);
                c0292m.m806d(c0292m2, i2);
                c0292m.m810i(1).get();
                c0292m.f541c--;
                break;
            }
            c0292m2.m803a(m812l);
        }
        this.f558c.mo294b(interfaceC0294o, c0292m2);
        if (z2) {
            return null;
        }
        return this;
    }
}
