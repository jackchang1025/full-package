package org.bouncycastle.tls;

import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsHash;

/* loaded from: classes.dex */
public interface TlsHandshakeHash extends TlsHash {
    void copyBufferTo(OutputStream outputStream);

    void forceBuffering();

    TlsHash forkPRFHash();

    byte[] getFinalHash(int i2);

    void notifyPRFDetermined();

    void sealHashAlgorithms();

    TlsHandshakeHash stopTracking();

    void trackHashAlgorithm(int i2);
}
