package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BasicTlsPSKExternal implements TlsPSKExternal {
    protected final byte[] identity;
    protected final TlsSecret key;
    protected final int prfAlgorithm;

    public BasicTlsPSKExternal(byte[] bArr, TlsSecret tlsSecret) {
        this(bArr, tlsSecret, 4);
    }

    @Override // org.bouncycastle.tls.TlsPSK
    public byte[] getIdentity() {
        return this.identity;
    }

    @Override // org.bouncycastle.tls.TlsPSK
    public TlsSecret getKey() {
        return this.key;
    }

    @Override // org.bouncycastle.tls.TlsPSK
    public int getPRFAlgorithm() {
        return this.prfAlgorithm;
    }

    public BasicTlsPSKExternal(byte[] bArr, TlsSecret tlsSecret, int i2) {
        this.identity = Arrays.clone(bArr);
        this.key = tlsSecret;
        this.prfAlgorithm = i2;
    }
}
