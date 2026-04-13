package org.bouncycastle.cms;

/* loaded from: classes.dex */
public class CMSException extends Exception {

    /* renamed from: e */
    Exception f1121e;

    public CMSException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.f1121e;
    }

    public Exception getUnderlyingException() {
        return this.f1121e;
    }

    public CMSException(String str, Exception exc) {
        super(str);
        this.f1121e = exc;
    }
}
