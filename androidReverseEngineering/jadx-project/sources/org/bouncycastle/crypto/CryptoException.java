package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public class CryptoException extends Exception {
    private Throwable cause;

    public CryptoException() {
    }

    public CryptoException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public CryptoException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
