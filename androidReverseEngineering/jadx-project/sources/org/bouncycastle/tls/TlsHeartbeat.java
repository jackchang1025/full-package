package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface TlsHeartbeat {
    byte[] generatePayload();

    int getIdleMillis();

    int getTimeoutMillis();
}
