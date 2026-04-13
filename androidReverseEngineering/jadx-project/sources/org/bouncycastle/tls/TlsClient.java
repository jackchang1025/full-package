package org.bouncycastle.tls;

import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes.dex */
public interface TlsClient extends TlsPeer {
    TlsAuthentication getAuthentication();

    Hashtable getClientExtensions();

    Vector getClientSupplementalData();

    TlsDHGroupVerifier getDHGroupVerifier();

    Vector getEarlyKeyShareGroups();

    Vector getExternalPSKs();

    TlsPSKIdentity getPSKIdentity();

    TlsSRPConfigVerifier getSRPConfigVerifier();

    TlsSRPIdentity getSRPIdentity();

    TlsSession getSessionToResume();

    void init(TlsClientContext tlsClientContext);

    boolean isFallback();

    void notifyNewSessionTicket(NewSessionTicket newSessionTicket);

    void notifySelectedCipherSuite(int i2);

    void notifySelectedPSK(TlsPSK tlsPSK);

    void notifyServerVersion(ProtocolVersion protocolVersion);

    void notifySessionID(byte[] bArr);

    void notifySessionToResume(TlsSession tlsSession);

    void processServerExtensions(Hashtable hashtable);

    void processServerSupplementalData(Vector vector);
}
