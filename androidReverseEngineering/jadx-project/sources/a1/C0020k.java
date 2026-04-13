package a1;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* renamed from: a1.k */
/* loaded from: classes.dex */
public final class C0020k implements InterfaceC0029t {

    /* renamed from: a */
    public final InterfaceC0016g f34a;

    /* renamed from: b */
    public final Inflater f35b;

    /* renamed from: c */
    public int f36c;

    /* renamed from: d */
    public boolean f37d;

    public C0020k(C0024o c0024o, Inflater inflater) {
        this.f34a = c0024o;
        this.f35b = inflater;
    }

    @Override // a1.InterfaceC0029t
    /* renamed from: a */
    public final C0031v mo68a() {
        return this.f34a.mo68a();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        if (this.f37d) {
            return;
        }
        this.f35b.end();
        this.f37d = true;
        this.f34a.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0071 A[Catch: DataFormatException -> 0x00ae, TryCatch #0 {DataFormatException -> 0x00ae, blocks: (B:19:0x004d, B:43:0x0065, B:21:0x0071, B:23:0x0077, B:28:0x0081, B:29:0x0088, B:32:0x0089, B:35:0x009c, B:37:0x00a2, B:40:0x008e), top: B:18:0x004d }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0065 A[SYNTHETIC] */
    @Override // a1.InterfaceC0029t
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo69u(C0014e c0014e, long j2) {
        boolean z2;
        int inflate;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f37d) {
            throw new IllegalStateException("closed");
        }
        if (j2 == 0) {
            return 0L;
        }
        do {
            Inflater inflater = this.f35b;
            boolean needsInput = inflater.needsInput();
            InterfaceC0016g interfaceC0016g = this.f34a;
            try {
                if (needsInput) {
                    int i2 = this.f36c;
                    if (i2 != 0) {
                        int remaining = i2 - inflater.getRemaining();
                        this.f36c -= remaining;
                        interfaceC0016g.skip(remaining);
                    }
                    if (inflater.getRemaining() != 0) {
                        throw new IllegalStateException("?");
                    }
                    if (interfaceC0016g.mo104n()) {
                        z2 = true;
                        C0025p m83G = c0014e.m83G(1);
                        inflate = inflater.inflate(m83G.f48a, m83G.f50c, (int) Math.min(j2, 8192 - m83G.f50c));
                        if (inflate <= 0) {
                            m83G.f50c += inflate;
                            long j3 = inflate;
                            c0014e.f22b += j3;
                            return j3;
                        }
                        if (!inflater.finished() && !inflater.needsDictionary()) {
                        }
                        int i3 = this.f36c;
                        if (i3 != 0) {
                            int remaining2 = i3 - inflater.getRemaining();
                            this.f36c -= remaining2;
                            interfaceC0016g.skip(remaining2);
                        }
                        if (m83G.f49b != m83G.f50c) {
                            return -1L;
                        }
                        c0014e.f21a = m83G.m146a();
                        AbstractC0026q.m161L(m83G);
                        return -1L;
                    }
                    C0025p c0025p = interfaceC0016g.mo97f().f21a;
                    int i4 = c0025p.f50c;
                    int i5 = c0025p.f49b;
                    int i6 = i4 - i5;
                    this.f36c = i6;
                    inflater.setInput(c0025p.f48a, i5, i6);
                }
                C0025p m83G2 = c0014e.m83G(1);
                inflate = inflater.inflate(m83G2.f48a, m83G2.f50c, (int) Math.min(j2, 8192 - m83G2.f50c));
                if (inflate <= 0) {
                }
            } catch (DataFormatException e2) {
                throw new IOException(e2);
            }
            z2 = false;
        } while (!z2);
        throw new EOFException("source exhausted prematurely");
    }
}
