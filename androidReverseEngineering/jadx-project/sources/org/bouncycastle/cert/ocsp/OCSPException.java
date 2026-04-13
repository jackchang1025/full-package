package org.bouncycastle.cert.ocsp;

/* loaded from: classes.dex */
public class OCSPException extends Exception {
    private Throwable cause;

    public OCSPException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public OCSPException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
