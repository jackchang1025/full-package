package org.bouncycastle.tls.crypto;

import org.bouncycastle.tls.DigitallySigned;

/* loaded from: classes.dex */
public interface TlsVerifier {
    TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned);

    boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr);
}
