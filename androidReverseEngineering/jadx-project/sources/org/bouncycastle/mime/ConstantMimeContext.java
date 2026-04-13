package org.bouncycastle.mime;

import java.io.InputStream;

/* loaded from: classes.dex */
public class ConstantMimeContext implements MimeContext, MimeMultipartContext {
    @Override // org.bouncycastle.mime.MimeContext
    public InputStream applyContext(Headers headers, InputStream inputStream) {
        return inputStream;
    }

    @Override // org.bouncycastle.mime.MimeMultipartContext
    public MimeContext createContext(int i2) {
        return this;
    }
}
