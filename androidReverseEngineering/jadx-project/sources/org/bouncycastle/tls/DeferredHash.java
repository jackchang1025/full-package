package org.bouncycastle.tls;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class DeferredHash implements TlsHandshakeHash {
    protected static final int BUFFERING_HASH_LIMIT = 4;
    private DigestInputBuffer buf;
    protected TlsContext context;
    private boolean forceBuffering;
    private Hashtable hashes;
    private boolean sealed;

    public DeferredHash(TlsContext tlsContext) {
        this.context = tlsContext;
        this.buf = new DigestInputBuffer();
        this.hashes = new Hashtable();
        this.forceBuffering = false;
        this.sealed = false;
    }

    public Integer box(int i2) {
        return Integers.valueOf(i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public byte[] calculateHash() {
        throw new IllegalStateException("Use 'forkPRFHash' to get a definite hash");
    }

    public void checkStopBuffering() {
        if (this.forceBuffering || !this.sealed || this.buf == null || this.hashes.size() > 4) {
            return;
        }
        Enumeration elements = this.hashes.elements();
        while (elements.hasMoreElements()) {
            this.buf.updateDigest((TlsHash) elements.nextElement());
        }
        this.buf = null;
    }

    public void checkTrackingHash(int i2) {
        checkTrackingHash(box(i2));
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public TlsHash cloneHash() {
        throw new IllegalStateException("attempt to clone a DeferredHash");
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public void copyBufferTo(OutputStream outputStream) {
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer == null) {
            throw new IllegalStateException("Not buffering");
        }
        digestInputBuffer.copyTo(outputStream);
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public void forceBuffering() {
        if (this.sealed) {
            throw new IllegalStateException("Too late to force buffering");
        }
        this.forceBuffering = true;
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public TlsHash forkPRFHash() {
        checkStopBuffering();
        SecurityParameters securityParametersHandshake = this.context.getSecurityParametersHandshake();
        int pRFAlgorithm = securityParametersHandshake.getPRFAlgorithm();
        TlsHash combinedHash = (pRFAlgorithm == 0 || pRFAlgorithm == 1) ? new CombinedHash(this.context, cloneHash(1), cloneHash(2)) : cloneHash(securityParametersHandshake.getPRFCryptoHashAlgorithm());
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer != null) {
            digestInputBuffer.updateDigest(combinedHash);
        }
        return combinedHash;
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public byte[] getFinalHash(int i2) {
        TlsHash tlsHash = (TlsHash) this.hashes.get(box(i2));
        if (tlsHash == null) {
            throw new IllegalStateException(AbstractC0000a.m12h("CryptoHashAlgorithm.", i2, " is not being tracked"));
        }
        checkStopBuffering();
        TlsHash cloneHash = tlsHash.cloneHash();
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer != null) {
            digestInputBuffer.updateDigest(cloneHash);
        }
        return cloneHash.calculateHash();
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public void notifyPRFDetermined() {
        int i2;
        SecurityParameters securityParametersHandshake = this.context.getSecurityParametersHandshake();
        int pRFAlgorithm = securityParametersHandshake.getPRFAlgorithm();
        if (pRFAlgorithm == 0 || pRFAlgorithm == 1) {
            checkTrackingHash(1);
            i2 = 2;
        } else {
            i2 = securityParametersHandshake.getPRFCryptoHashAlgorithm();
        }
        checkTrackingHash(i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void reset() {
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer != null) {
            digestInputBuffer.reset();
            return;
        }
        Enumeration elements = this.hashes.elements();
        while (elements.hasMoreElements()) {
            ((TlsHash) elements.nextElement()).reset();
        }
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public void sealHashAlgorithms() {
        if (this.sealed) {
            throw new IllegalStateException("Already sealed");
        }
        this.sealed = true;
        checkStopBuffering();
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public TlsHandshakeHash stopTracking() {
        int i2;
        SecurityParameters securityParametersHandshake = this.context.getSecurityParametersHandshake();
        Hashtable hashtable = new Hashtable();
        int pRFAlgorithm = securityParametersHandshake.getPRFAlgorithm();
        if (pRFAlgorithm == 0 || pRFAlgorithm == 1) {
            cloneHash(hashtable, 1);
            i2 = 2;
        } else {
            i2 = securityParametersHandshake.getPRFCryptoHashAlgorithm();
        }
        cloneHash(hashtable, i2);
        return new DeferredHash(this.context, hashtable);
    }

    @Override // org.bouncycastle.tls.TlsHandshakeHash
    public void trackHashAlgorithm(int i2) {
        if (this.sealed) {
            throw new IllegalStateException("Too late to track more hash algorithms");
        }
        checkTrackingHash(i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void update(byte[] bArr, int i2, int i3) {
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer != null) {
            digestInputBuffer.write(bArr, i2, i3);
            return;
        }
        Enumeration elements = this.hashes.elements();
        while (elements.hasMoreElements()) {
            ((TlsHash) elements.nextElement()).update(bArr, i2, i3);
        }
    }

    private DeferredHash(TlsContext tlsContext, Hashtable hashtable) {
        this.context = tlsContext;
        this.buf = null;
        this.hashes = hashtable;
        this.forceBuffering = false;
        this.sealed = true;
    }

    public void checkTrackingHash(Integer num) {
        if (this.hashes.containsKey(num)) {
            return;
        }
        this.hashes.put(num, this.context.getCrypto().createHash(num.intValue()));
    }

    public TlsHash cloneHash(int i2) {
        return cloneHash(box(i2));
    }

    public TlsHash cloneHash(Integer num) {
        return ((TlsHash) this.hashes.get(num)).cloneHash();
    }

    public void cloneHash(Hashtable hashtable, int i2) {
        cloneHash(hashtable, box(i2));
    }

    public void cloneHash(Hashtable hashtable, Integer num) {
        TlsHash cloneHash = cloneHash(num);
        DigestInputBuffer digestInputBuffer = this.buf;
        if (digestInputBuffer != null) {
            digestInputBuffer.updateDigest(cloneHash);
        }
        hashtable.put(num, cloneHash);
    }
}
