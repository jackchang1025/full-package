package b1;

import java.io.ByteArrayOutputStream;

/* renamed from: b1.j */
/* loaded from: classes.dex */
public final class C0088j extends ByteArrayOutputStream {
    public C0088j(int i2) {
        super(i2);
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }
}
