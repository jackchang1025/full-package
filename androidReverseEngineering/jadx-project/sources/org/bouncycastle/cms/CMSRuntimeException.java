package org.bouncycastle.cms;

/* loaded from: classes.dex */
public class CMSRuntimeException extends RuntimeException {

    /* renamed from: e */
    Exception f1122e;

    public CMSRuntimeException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.f1122e;
    }

    public Exception getUnderlyingException() {
        return this.f1122e;
    }

    public CMSRuntimeException(String str, Exception exc) {
        super(str);
        this.f1122e = exc;
    }
}
