package org.bouncycastle.jsse.provider;

import java.net.Socket;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;

/* loaded from: classes.dex */
class TransportData {
    private final BCExtendedSSLSession handshakeSession;
    private final BCSSLParameters parameters;

    private TransportData(BCSSLParameters bCSSLParameters, BCExtendedSSLSession bCExtendedSSLSession) {
        this.parameters = bCSSLParameters;
        this.handshakeSession = bCExtendedSSLSession;
    }

    public static TransportData from(Socket socket) {
        SSLSocket sSLSocket;
        BCSSLParameters importSSLParameters;
        if ((socket instanceof SSLSocket) && socket.isConnected() && (importSSLParameters = SSLSocketUtil.importSSLParameters((sSLSocket = (SSLSocket) socket))) != null) {
            return new TransportData(importSSLParameters, SSLSocketUtil.importHandshakeSession(sSLSocket));
        }
        return null;
    }

    public static BCAlgorithmConstraints getAlgorithmConstraints(TransportData transportData, boolean z2) {
        return transportData == null ? ProvAlgorithmConstraints.DEFAULT : transportData.getAlgorithmConstraints(z2);
    }

    public BCExtendedSSLSession getHandshakeSession() {
        return this.handshakeSession;
    }

    public BCSSLParameters getParameters() {
        return this.parameters;
    }

    public List<byte[]> getStatusResponses() {
        BCExtendedSSLSession bCExtendedSSLSession = this.handshakeSession;
        return bCExtendedSSLSession == null ? Collections.emptyList() : bCExtendedSSLSession.getStatusResponses();
    }

    public static TransportData from(SSLEngine sSLEngine) {
        BCSSLParameters importSSLParameters;
        if (sSLEngine == null || (importSSLParameters = SSLEngineUtil.importSSLParameters(sSLEngine)) == null) {
            return null;
        }
        return new TransportData(importSSLParameters, SSLEngineUtil.importHandshakeSession(sSLEngine));
    }

    public static List<byte[]> getStatusResponses(TransportData transportData) {
        return transportData == null ? Collections.emptyList() : transportData.getStatusResponses();
    }

    public BCAlgorithmConstraints getAlgorithmConstraints(boolean z2) {
        BCAlgorithmConstraints algorithmConstraints = this.parameters.getAlgorithmConstraints();
        ProvAlgorithmConstraints provAlgorithmConstraints = ProvAlgorithmConstraints.DEFAULT;
        if (provAlgorithmConstraints == algorithmConstraints) {
            algorithmConstraints = null;
        }
        BCExtendedSSLSession bCExtendedSSLSession = this.handshakeSession;
        if (bCExtendedSSLSession != null && JsseUtils.isTLSv12(bCExtendedSSLSession.getProtocol())) {
            String[] peerSupportedSignatureAlgorithmsBC = z2 ? this.handshakeSession.getPeerSupportedSignatureAlgorithmsBC() : this.handshakeSession.getLocalSupportedSignatureAlgorithmsBC();
            if (peerSupportedSignatureAlgorithmsBC != null) {
                return new ProvAlgorithmConstraints(algorithmConstraints, peerSupportedSignatureAlgorithmsBC, true);
            }
        }
        return algorithmConstraints == null ? provAlgorithmConstraints : new ProvAlgorithmConstraints(algorithmConstraints, true);
    }
}
