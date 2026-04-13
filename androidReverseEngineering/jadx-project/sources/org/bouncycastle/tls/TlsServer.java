package org.bouncycastle.tls;

import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;

/* loaded from: classes.dex */
public interface TlsServer extends TlsPeer {
    CertificateRequest getCertificateRequest();

    CertificateStatus getCertificateStatus();

    TlsCredentials getCredentials();

    TlsDHConfig getDHConfig();

    TlsECConfig getECDHConfig();

    TlsPSKExternal getExternalPSK(Vector vector);

    byte[] getNewSessionID();

    NewSessionTicket getNewSessionTicket();

    TlsPSKIdentityManager getPSKIdentityManager();

    TlsSRPLoginParameters getSRPLoginParameters();

    int getSelectedCipherSuite();

    Hashtable getServerExtensions();

    void getServerExtensionsForConnection(Hashtable hashtable);

    Vector getServerSupplementalData();

    ProtocolVersion getServerVersion();

    TlsSession getSessionToResume(byte[] bArr);

    int[] getSupportedGroups();

    void init(TlsServerContext tlsServerContext);

    void notifyClientCertificate(Certificate certificate);

    void notifyClientVersion(ProtocolVersion protocolVersion);

    void notifyFallback(boolean z2);

    void notifyOfferedCipherSuites(int[] iArr);

    void notifySession(TlsSession tlsSession);

    void processClientExtensions(Hashtable hashtable);

    void processClientSupplementalData(Vector vector);
}
