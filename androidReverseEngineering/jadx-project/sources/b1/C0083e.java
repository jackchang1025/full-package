package b1;

import a1.AbstractC0026q;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* renamed from: b1.e */
/* loaded from: classes.dex */
public final class C0083e extends OutputStream {

    /* renamed from: a */
    public final C0086h f121a;

    public C0083e(C0086h c0086h) {
        this.f121a = c0086h;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        flush();
    }

    public final void finalize() {
        C0086h c0086h = this.f121a;
        if (c0086h != null) {
            try {
                c0086h.close();
                c0086h.m320z();
            } catch (Exception e2) {
                AbstractC0026q.m186s("b1.e", e2);
            }
        }
        super.finalize();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public final void flush() {
        OutputStream outputStream;
        C0086h c0086h = this.f121a;
        if (c0086h.f136g) {
            throw new IOException("Stream closed");
        }
        C0082d c0082d = c0086h.f130a;
        synchronized (c0082d.f120v) {
            if (c0082d.f119u) {
                outputStream = c0082d.f107i;
                Objects.requireNonNull(outputStream);
            } else {
                outputStream = c0082d.f105g;
            }
            outputStream.flush();
        }
    }

    @Override // java.io.OutputStream
    public final void write(int i2) {
        write(new byte[]{(byte) (i2 & 255)}, 0, 1);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i2, int i3) {
        C0086h c0086h = this.f121a;
        synchronized (c0086h) {
            while (!c0086h.f136g && !c0086h.f133d.compareAndSet(true, false)) {
                try {
                    c0086h.wait();
                } catch (InterruptedException e2) {
                    throw ((IOException) new IOException().initCause(e2));
                }
            }
            if (c0086h.f136g) {
                throw new IOException("Stream closed");
            }
        }
        try {
            C0082d c0082d = c0086h.f130a;
            if (c0082d.f109k) {
                c0082d.m309B(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                int i4 = c0082d.f113o;
                int i5 = i2;
                int i6 = i3;
                while (i6 != 0) {
                    C0082d c0082d2 = c0086h.f130a;
                    int i7 = c0086h.f131b;
                    int i8 = c0086h.f132c;
                    if (i6 <= i4) {
                        c0082d2.m308A(AbstractC0085g.m314a(1163154007, i7, i8, bArr, i5, i6));
                        i5 += i6;
                        i6 = 0;
                    } else {
                        c0082d2.m308A(AbstractC0085g.m314a(1163154007, i7, i8, bArr, i5, i4));
                        i5 += i4;
                        i6 -= i4;
                    }
                }
                return;
            }
            throw new IllegalStateException("connect() must be called first");
        } catch (InterruptedException e3) {
            throw ((IOException) new IOException().initCause(e3));
        }
    }
}
