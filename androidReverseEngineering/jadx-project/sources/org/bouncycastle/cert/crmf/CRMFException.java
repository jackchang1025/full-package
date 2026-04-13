package org.bouncycastle.cert.crmf;

/* loaded from: classes.dex */
public class CRMFException extends Exception {
    private Throwable cause;

    public CRMFException(String str) {
        this(str, null);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public CRMFException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
