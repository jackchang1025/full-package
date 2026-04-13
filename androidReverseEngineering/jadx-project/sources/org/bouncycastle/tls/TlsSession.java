package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface TlsSession {
    SessionParameters exportSessionParameters();

    byte[] getSessionID();

    void invalidate();

    boolean isResumable();
}
