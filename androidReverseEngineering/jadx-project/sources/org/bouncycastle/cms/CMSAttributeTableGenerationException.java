package org.bouncycastle.cms;

/* loaded from: classes.dex */
public class CMSAttributeTableGenerationException extends CMSRuntimeException {

    /* renamed from: e */
    Exception f1120e;

    public CMSAttributeTableGenerationException(String str) {
        super(str);
    }

    @Override // org.bouncycastle.cms.CMSRuntimeException, java.lang.Throwable
    public Throwable getCause() {
        return this.f1120e;
    }

    @Override // org.bouncycastle.cms.CMSRuntimeException
    public Exception getUnderlyingException() {
        return this.f1120e;
    }

    public CMSAttributeTableGenerationException(String str, Exception exc) {
        super(str);
        this.f1120e = exc;
    }
}
