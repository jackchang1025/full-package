package k0;

import a1.AbstractC0026q;
import f0.AbstractC0296q;
import f0.C0292m;
import f0.InterfaceC0294o;
import i0.C0331b;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;

/* renamed from: k0.g */
/* loaded from: classes.dex */
public class C0363g extends AbstractC0296q {

    /* renamed from: i */
    public final Inflater f718i;

    /* renamed from: j */
    public final C0292m f719j = new C0292m();

    public C0363g(Inflater inflater) {
        this.f718i = inflater;
    }

    @Override // f0.AbstractC0296q, g0.InterfaceC0310b
    /* renamed from: b */
    public void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        Inflater inflater = this.f718i;
        try {
            ByteBuffer m801g = C0292m.m801g(c0292m.f541c * 2);
            while (true) {
                int size = c0292m.f539a.size();
                C0292m c0292m2 = this.f719j;
                if (size <= 0) {
                    m801g.flip();
                    c0292m2.m803a(m801g);
                    AbstractC0026q.m183p(this, c0292m2);
                    return;
                }
                ByteBuffer m812l = c0292m.m812l();
                if (m812l.hasRemaining()) {
                    m812l.remaining();
                    inflater.setInput(m812l.array(), m812l.arrayOffset() + m812l.position(), m812l.remaining());
                    do {
                        m801g.position(m801g.position() + inflater.inflate(m801g.array(), m801g.arrayOffset() + m801g.position(), m801g.remaining()));
                        if (!m801g.hasRemaining()) {
                            m801g.flip();
                            c0292m2.m803a(m801g);
                            m801g = C0292m.m801g(m801g.capacity() * 2);
                        }
                        if (!inflater.needsInput()) {
                        }
                    } while (!inflater.finished());
                }
                C0292m.m802j(m812l);
            }
        } catch (Exception e2) {
            mo813c(e2);
        }
    }

    @Override // f0.AbstractC0296q
    /* renamed from: c */
    public final void mo813c(Exception exc) {
        Inflater inflater = this.f718i;
        inflater.end();
        if (exc != null && inflater.getRemaining() > 0) {
            exc = new C0331b(exc);
        }
        super.mo813c(exc);
    }
}
