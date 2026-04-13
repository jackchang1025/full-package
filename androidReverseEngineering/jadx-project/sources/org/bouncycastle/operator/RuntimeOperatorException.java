package org.bouncycastle.operator;

/* loaded from: classes.dex */
public class RuntimeOperatorException extends RuntimeException {
    private Throwable cause;

    public RuntimeOperatorException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public RuntimeOperatorException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }
}
