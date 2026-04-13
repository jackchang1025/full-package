package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public final class TlsDecodeResult {
    public final byte[] buf;
    public final short contentType;
    public final int len;
    public final int off;

    public TlsDecodeResult(byte[] bArr, int i2, int i3, short s2) {
        this.buf = bArr;
        this.off = i2;
        this.len = i3;
        this.contentType = s2;
    }
}
