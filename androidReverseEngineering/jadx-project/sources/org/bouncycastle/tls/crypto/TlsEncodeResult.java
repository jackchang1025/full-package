package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public final class TlsEncodeResult {
    public final byte[] buf;
    public final int len;
    public final int off;
    public final short recordType;

    public TlsEncodeResult(byte[] bArr, int i2, int i3, short s2) {
        this.buf = bArr;
        this.off = i2;
        this.len = i3;
        this.recordType = s2;
    }
}
