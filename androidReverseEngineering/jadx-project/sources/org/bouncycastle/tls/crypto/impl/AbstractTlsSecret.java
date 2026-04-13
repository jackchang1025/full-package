package org.bouncycastle.tls.crypto.impl;

import org.bouncycastle.tls.crypto.TlsEncryptor;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public abstract class AbstractTlsSecret implements TlsSecret {
    protected byte[] data;

    public AbstractTlsSecret(byte[] bArr) {
        this.data = bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized byte[] calculateHMAC(int i2, byte[] bArr, int i3, int i4) {
        TlsHMAC createHMACForHash;
        checkAlive();
        createHMACForHash = getCrypto().createHMACForHash(i2);
        byte[] bArr2 = this.data;
        createHMACForHash.setKey(bArr2, 0, bArr2.length);
        createHMACForHash.update(bArr, i3, i4);
        return createHMACForHash.calculateMAC();
    }

    public void checkAlive() {
        if (this.data == null) {
            throw new IllegalStateException("Secret has already been extracted or destroyed");
        }
    }

    public synchronized byte[] copyData() {
        return Arrays.clone(this.data);
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized void destroy() {
        byte[] bArr = this.data;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            this.data = null;
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized byte[] encrypt(TlsEncryptor tlsEncryptor) {
        byte[] bArr;
        checkAlive();
        bArr = this.data;
        return tlsEncryptor.encrypt(bArr, 0, bArr.length);
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized byte[] extract() {
        byte[] bArr;
        checkAlive();
        bArr = this.data;
        this.data = null;
        return bArr;
    }

    public abstract AbstractTlsCrypto getCrypto();

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized boolean isAlive() {
        return this.data != null;
    }

    public static byte[] copyData(AbstractTlsSecret abstractTlsSecret) {
        return abstractTlsSecret.copyData();
    }
}
