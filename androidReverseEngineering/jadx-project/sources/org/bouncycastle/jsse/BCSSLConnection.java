package org.bouncycastle.jsse;

/* loaded from: classes.dex */
public interface BCSSLConnection {
    String getApplicationProtocol();

    byte[] getChannelBinding(String str);

    BCExtendedSSLSession getSession();
}
