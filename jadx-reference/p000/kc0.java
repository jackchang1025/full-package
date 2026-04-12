package p000;

import java.io.Writer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class kc0 extends Writer {

    /* renamed from: a0 */
    public final StringBuilder f57508a0 = new StringBuilder(128);

    /* renamed from: a0 */
    public final void m213481a0() {
        StringBuilder sb = this.f57508a0;
        if (sb.length() > 0) {
            sb.toString();
            sb.delete(0, sb.length());
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        m213481a0();
    }

    @Override // java.io.Writer, java.io.Flushable
    public final void flush() {
        m213481a0();
    }

    @Override // java.io.Writer
    public final void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                m213481a0();
            } else {
                this.f57508a0.append(c);
            }
        }
    }
}
