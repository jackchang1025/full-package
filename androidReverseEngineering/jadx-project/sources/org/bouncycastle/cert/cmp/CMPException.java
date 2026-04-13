package org.bouncycastle.cert.cmp;

/* loaded from: classes.dex */
public class CMPException extends Exception {
    private Throwable cause;

    public CMPException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public CMPException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
