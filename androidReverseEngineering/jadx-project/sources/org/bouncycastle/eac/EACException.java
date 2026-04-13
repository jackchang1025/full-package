package org.bouncycastle.eac;

/* loaded from: classes.dex */
public class EACException extends Exception {
    private Throwable cause;

    public EACException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public EACException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
