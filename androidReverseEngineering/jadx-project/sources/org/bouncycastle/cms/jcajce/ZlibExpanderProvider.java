package org.bouncycastle.cms.jcajce;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.InputExpander;
import org.bouncycastle.operator.InputExpanderProvider;
import org.bouncycastle.util.io.StreamOverflowException;

/* loaded from: classes.dex */
public class ZlibExpanderProvider implements InputExpanderProvider {
    private final long limit;

    public static class LimitedInputStream extends FilterInputStream {
        private long remaining;

        public LimitedInputStream(InputStream inputStream, long j2) {
            super(inputStream);
            this.remaining = j2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0019, code lost:
        
            if (r4 >= 0) goto L8;
         */
        @Override // java.io.FilterInputStream, java.io.InputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int read() {
            if (this.remaining >= 0) {
                int read = ((FilterInputStream) this).in.read();
                if (read >= 0) {
                    long j2 = this.remaining - 1;
                    this.remaining = j2;
                }
                return read;
            }
            throw new StreamOverflowException("expanded byte limit exceeded");
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            if (i3 < 1) {
                return super.read(bArr, i2, i3);
            }
            long j2 = this.remaining;
            if (j2 < 1) {
                read();
                return -1;
            }
            if (j2 <= i3) {
                i3 = (int) j2;
            }
            int read = ((FilterInputStream) this).in.read(bArr, i2, i3);
            if (read > 0) {
                this.remaining -= read;
            }
            return read;
        }
    }

    public ZlibExpanderProvider() {
        this.limit = -1L;
    }

    @Override // org.bouncycastle.operator.InputExpanderProvider
    public InputExpander get(final AlgorithmIdentifier algorithmIdentifier) {
        return new InputExpander() { // from class: org.bouncycastle.cms.jcajce.ZlibExpanderProvider.1
            @Override // org.bouncycastle.operator.InputExpander
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier;
            }

            @Override // org.bouncycastle.operator.InputExpander
            public InputStream getInputStream(InputStream inputStream) {
                InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
                return ZlibExpanderProvider.this.limit >= 0 ? new LimitedInputStream(inflaterInputStream, ZlibExpanderProvider.this.limit) : inflaterInputStream;
            }
        };
    }

    public ZlibExpanderProvider(long j2) {
        this.limit = j2;
    }
}
