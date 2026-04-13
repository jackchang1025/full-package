package org.bouncycastle.tls.crypto;

import java.io.OutputStream;

/* loaded from: classes.dex */
public interface TlsStreamSigner {
    OutputStream getOutputStream();

    byte[] getSignature();
}
