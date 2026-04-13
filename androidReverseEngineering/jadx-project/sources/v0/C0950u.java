package v0;

import a1.C0014e;
import a1.C0031v;
import a1.InterfaceC0016g;
import a1.InterfaceC0029t;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: v0.u */
/* loaded from: classes.dex */
public final class C0950u implements InterfaceC0029t {

    /* renamed from: a */
    public final InterfaceC0016g f2231a;

    /* renamed from: b */
    public int f2232b;

    /* renamed from: c */
    public byte f2233c;

    /* renamed from: d */
    public int f2234d;

    /* renamed from: e */
    public int f2235e;

    /* renamed from: f */
    public short f2236f;

    public C0950u(InterfaceC0016g interfaceC0016g) {
        this.f2231a = interfaceC0016g;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f2231a.mo68a();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    public final long mo69u(C0014e c0014e, long j2) {
        int i2;
        int readInt;
        do {
            int i3 = this.f2235e;
            InterfaceC0016g interfaceC0016g = this.f2231a;
            if (i3 != 0) {
                long mo69u = interfaceC0016g.mo69u(c0014e, Math.min(j2, i3));
                if (mo69u == -1) {
                    return -1L;
                }
                this.f2235e = (int) (this.f2235e - mo69u);
                return mo69u;
            }
            interfaceC0016g.skip(this.f2236f);
            this.f2236f = (short) 0;
            if ((this.f2233c & 4) != 0) {
                return -1L;
            }
            i2 = this.f2234d;
            int readByte = ((interfaceC0016g.readByte() & 255) << 16) | ((interfaceC0016g.readByte() & 255) << 8) | (interfaceC0016g.readByte() & 255);
            this.f2235e = readByte;
            this.f2232b = readByte;
            byte readByte2 = (byte) (interfaceC0016g.readByte() & 255);
            this.f2233c = (byte) (interfaceC0016g.readByte() & 255);
            Logger logger = C0951v.f2237e;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(AbstractC0936g.m1406a(true, this.f2234d, this.f2232b, readByte2, this.f2233c));
            }
            readInt = interfaceC0016g.readInt() & Integer.MAX_VALUE;
            this.f2234d = readInt;
            if (readByte2 != 9) {
                AbstractC0936g.m1407b(new Object[]{Byte.valueOf(readByte2)}, "%s != TYPE_CONTINUATION");
                throw null;
            }
        } while (readInt == i2);
        AbstractC0936g.m1407b(new Object[0], "TYPE_CONTINUATION streamId changed");
        throw null;
    }
}
