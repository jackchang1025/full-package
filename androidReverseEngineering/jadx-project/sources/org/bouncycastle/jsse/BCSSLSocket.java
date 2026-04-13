package org.bouncycastle.jsse;

import javax.net.ssl.SSLSocket;

/* loaded from: classes.dex */
public interface BCSSLSocket {
    void connect(String str, int i2, int i3);

    String getApplicationProtocol();

    BCApplicationProtocolSelector<SSLSocket> getBCHandshakeApplicationProtocolSelector();

    BCExtendedSSLSession getBCHandshakeSession();

    BCExtendedSSLSession getBCSession();

    BCSSLConnection getConnection();

    String getHandshakeApplicationProtocol();

    BCSSLParameters getParameters();

    void setBCHandshakeApplicationProtocolSelector(BCApplicationProtocolSelector<SSLSocket> bCApplicationProtocolSelector);

    void setBCSessionToResume(BCExtendedSSLSession bCExtendedSSLSession);

    void setHost(String str);

    void setParameters(BCSSLParameters bCSSLParameters);
}
