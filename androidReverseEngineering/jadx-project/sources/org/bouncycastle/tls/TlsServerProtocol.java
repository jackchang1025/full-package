package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.OfferedPsks;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TlsServerProtocol extends TlsProtocol {
    protected CertificateRequest certificateRequest;
    protected TlsKeyExchange keyExchange;
    protected int[] offeredCipherSuites;
    protected TlsServer tlsServer;
    TlsServerContextImpl tlsServerContext;

    public TlsServerProtocol() {
        this.tlsServer = null;
        this.tlsServerContext = null;
        this.offeredCipherSuites = null;
        this.keyExchange = null;
        this.certificateRequest = null;
    }

    public void accept(TlsServer tlsServer) {
        if (tlsServer == null) {
            throw new IllegalArgumentException("'tlsServer' cannot be null");
        }
        if (this.tlsServer != null) {
            throw new IllegalStateException("'accept' can only be called once");
        }
        this.tlsServer = tlsServer;
        TlsServerContextImpl tlsServerContextImpl = new TlsServerContextImpl(tlsServer.getCrypto());
        this.tlsServerContext = tlsServerContextImpl;
        tlsServer.init(tlsServerContextImpl);
        tlsServer.notifyCloseHandle(this);
        beginHandshake(false);
        if (this.blocking) {
            blockForHandshake();
        }
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.offeredCipherSuites = null;
        this.keyExchange = null;
        this.certificateRequest = null;
    }

    public boolean expectCertificateVerifyMessage() {
        Certificate peerCertificate;
        if (this.certificateRequest == null || (peerCertificate = this.tlsServerContext.getSecurityParametersHandshake().getPeerCertificate()) == null || peerCertificate.isEmpty()) {
            return false;
        }
        TlsKeyExchange tlsKeyExchange = this.keyExchange;
        return tlsKeyExchange == null || tlsKeyExchange.requiresCertificateVerify();
    }

    public ServerHello generate13HelloRetryRequest(ClientHello clientHello) {
        if (this.retryGroup < 0) {
            throw new TlsFatalAlert((short) 80);
        }
        SecurityParameters securityParametersHandshake = this.tlsServerContext.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        Hashtable hashtable = new Hashtable();
        TlsExtensionsUtils.addSupportedVersionsExtensionServer(hashtable, negotiatedVersion);
        int i2 = this.retryGroup;
        if (i2 >= 0) {
            TlsExtensionsUtils.addKeyShareHelloRetryRequest(hashtable, i2);
        }
        byte[] bArr = this.retryCookie;
        if (bArr != null) {
            TlsExtensionsUtils.addCookieExtension(hashtable, bArr);
        }
        TlsUtils.checkExtensionData13(hashtable, 6, (short) 80);
        return new ServerHello(clientHello.getSessionID(), securityParametersHandshake.getCipherSuite(), hashtable);
    }

    public ServerHello generate13ServerHello(ClientHello clientHello, HandshakeMessageInput handshakeMessageInput, boolean z2) {
        KeyShareEntry keyShareEntry;
        TlsAgreement createDH;
        SecurityParameters securityParametersHandshake = this.tlsServerContext.getSecurityParametersHandshake();
        if (securityParametersHandshake.isRenegotiating()) {
            throw new TlsFatalAlert((short) 80);
        }
        byte[] sessionID = clientHello.getSessionID();
        Hashtable extensions = clientHello.getExtensions();
        if (extensions == null) {
            throw new TlsFatalAlert(AlertDescription.missing_extension);
        }
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        TlsCrypto crypto = this.tlsServerContext.getCrypto();
        OfferedPsks.SelectedConfig selectPreSharedKey = TlsUtils.selectPreSharedKey(this.tlsServerContext, this.tlsServer, extensions, handshakeMessageInput, this.handshakeHash, z2);
        Vector keyShareClientHello = TlsExtensionsUtils.getKeyShareClientHello(extensions);
        TlsSecret tlsSecret = null;
        if (!z2) {
            this.clientExtensions = extensions;
            securityParametersHandshake.secureRenegotiation = false;
            TlsExtensionsUtils.getPaddingExtension(extensions);
            securityParametersHandshake.clientServerNames = TlsExtensionsUtils.getServerNameExtensionClient(extensions);
            TlsUtils.establishClientSigAlgs(securityParametersHandshake, extensions);
            if (selectPreSharedKey == null && securityParametersHandshake.getClientSigAlgs() == null) {
                throw new TlsFatalAlert(AlertDescription.missing_extension);
            }
            this.tlsServer.processClientExtensions(extensions);
            TlsSession importSession = TlsUtils.importSession(TlsUtils.EMPTY_BYTES, null);
            this.tlsSession = importSession;
            this.sessionParameters = null;
            this.sessionMasterSecret = null;
            securityParametersHandshake.sessionID = importSession.getSessionID();
            this.tlsServer.notifySession(this.tlsSession);
            TlsUtils.negotiatedVersionTLSServer(this.tlsServerContext);
            securityParametersHandshake.serverRandom = TlsProtocol.createRandomBlock(false, this.tlsServerContext);
            if (!negotiatedVersion.equals(ProtocolVersion.getLatestTLS(this.tlsServer.getProtocolVersions()))) {
                TlsUtils.writeDowngradeMarker(negotiatedVersion, securityParametersHandshake.getServerRandom());
            }
            int selectedCipherSuite = this.tlsServer.getSelectedCipherSuite();
            if (!TlsUtils.isValidCipherSuiteSelection(this.offeredCipherSuites, selectedCipherSuite) || !TlsUtils.isValidVersionForCipherSuite(selectedCipherSuite, negotiatedVersion)) {
                throw new TlsFatalAlert((short) 80);
            }
            TlsUtils.negotiatedCipherSuite(securityParametersHandshake, selectedCipherSuite);
            int[] clientSupportedGroups = securityParametersHandshake.getClientSupportedGroups();
            int[] serverSupportedGroups = securityParametersHandshake.getServerSupportedGroups();
            KeyShareEntry selectKeyShare = TlsUtils.selectKeyShare(crypto, negotiatedVersion, keyShareClientHello, clientSupportedGroups, serverSupportedGroups);
            if (selectKeyShare == null) {
                int selectKeyShareGroup = TlsUtils.selectKeyShareGroup(crypto, negotiatedVersion, clientSupportedGroups, serverSupportedGroups);
                this.retryGroup = selectKeyShareGroup;
                if (selectKeyShareGroup < 0) {
                    throw new TlsFatalAlert((short) 40);
                }
                this.retryCookie = this.tlsServerContext.getNonceGenerator().generateNonce(16);
                return generate13HelloRetryRequest(clientHello);
            }
            selectKeyShare.getNamedGroup();
            int i2 = serverSupportedGroups[0];
            keyShareEntry = selectKeyShare;
        } else {
            if (this.retryGroup < 0) {
                throw new TlsFatalAlert((short) 80);
            }
            if (selectPreSharedKey == null) {
                if (securityParametersHandshake.getClientSigAlgs() == null) {
                    throw new TlsFatalAlert(AlertDescription.missing_extension);
                }
            } else if (selectPreSharedKey.psk.getPRFAlgorithm() != securityParametersHandshake.getPRFAlgorithm()) {
                throw new TlsFatalAlert((short) 47);
            }
            if (!Arrays.areEqual(this.retryCookie, TlsExtensionsUtils.getCookieExtension(extensions))) {
                throw new TlsFatalAlert((short) 47);
            }
            this.retryCookie = null;
            keyShareEntry = TlsUtils.selectKeyShare(keyShareClientHello, this.retryGroup);
            if (keyShareEntry == null) {
                throw new TlsFatalAlert((short) 47);
            }
        }
        Hashtable hashtable = new Hashtable();
        Hashtable ensureExtensionsInitialised = TlsExtensionsUtils.ensureExtensionsInitialised(this.tlsServer.getServerExtensions());
        this.tlsServer.getServerExtensionsForConnection(ensureExtensionsInitialised);
        ProtocolVersion protocolVersion = ProtocolVersion.TLSv12;
        TlsExtensionsUtils.addSupportedVersionsExtensionServer(hashtable, negotiatedVersion);
        securityParametersHandshake.extendedMasterSecret = true;
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(ensureExtensionsInitialised);
        securityParametersHandshake.applicationProtocolSet = true;
        if (!ensureExtensionsInitialised.isEmpty()) {
            securityParametersHandshake.maxFragmentLength = processMaxFragmentLengthExtension(extensions, ensureExtensionsInitialised, (short) 80);
        }
        securityParametersHandshake.encryptThenMAC = false;
        securityParametersHandshake.truncatedHMac = false;
        securityParametersHandshake.statusRequestVersion = extensions.containsKey(TlsExtensionsUtils.EXT_status_request) ? 1 : 0;
        this.expectSessionTicket = false;
        if (selectPreSharedKey != null) {
            tlsSecret = selectPreSharedKey.earlySecret;
            this.selectedPSK13 = true;
            TlsExtensionsUtils.addPreSharedKeyServerHello(hashtable, selectPreSharedKey.index);
        }
        int namedGroup = keyShareEntry.getNamedGroup();
        if (NamedGroup.refersToASpecificCurve(namedGroup)) {
            createDH = crypto.createECDomain(new TlsECConfig(namedGroup)).createECDH();
        } else {
            if (!NamedGroup.refersToASpecificFiniteField(namedGroup)) {
                throw new TlsFatalAlert((short) 80);
            }
            createDH = crypto.createDHDomain(new TlsDHConfig(namedGroup, true)).createDH();
        }
        TlsExtensionsUtils.addKeyShareServerHello(hashtable, new KeyShareEntry(namedGroup, createDH.generateEphemeral()));
        createDH.receivePeerValue(keyShareEntry.getKeyExchange());
        TlsUtils.establish13PhaseSecrets(this.tlsServerContext, tlsSecret, createDH.calculateSecret());
        this.serverExtensions = ensureExtensionsInitialised;
        applyMaxFragmentLengthExtension(securityParametersHandshake.getMaxFragmentLength());
        TlsUtils.checkExtensionData13(hashtable, 2, (short) 80);
        return new ServerHello(protocolVersion, securityParametersHandshake.getServerRandom(), sessionID, securityParametersHandshake.getCipherSuite(), hashtable);
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x02f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ServerHello generateServerHello(ClientHello clientHello, HandshakeMessageInput handshakeMessageInput) {
        ProtocolVersion latestTLS;
        ProtocolVersion serverVersion;
        ProtocolVersion version = clientHello.getVersion();
        if (!version.isTLS()) {
            throw new TlsFatalAlert((short) 47);
        }
        this.offeredCipherSuites = clientHello.getCipherSuites();
        SecurityParameters securityParametersHandshake = this.tlsServerContext.getSecurityParametersHandshake();
        this.tlsServerContext.setClientSupportedVersions(TlsExtensionsUtils.getSupportedVersionsExtensionClient(clientHello.getExtensions()));
        if (this.tlsServerContext.getClientSupportedVersions() == null) {
            latestTLS = ProtocolVersion.TLSv12;
            if (!version.isLaterVersionOf(latestTLS)) {
                latestTLS = version;
            }
            this.tlsServerContext.setClientSupportedVersions(latestTLS.downTo(ProtocolVersion.SSLv3));
        } else {
            latestTLS = ProtocolVersion.getLatestTLS(this.tlsServerContext.getClientSupportedVersions());
        }
        this.recordStream.setWriteVersion(latestTLS);
        if (!ProtocolVersion.SERVER_EARLIEST_SUPPORTED_TLS.isEqualOrEarlierVersionOf(latestTLS)) {
            throw new TlsFatalAlert((short) 70);
        }
        if (!securityParametersHandshake.isRenegotiating()) {
            this.tlsServerContext.setClientVersion(latestTLS);
        } else if (!latestTLS.equals(this.tlsServerContext.getClientVersion()) && !latestTLS.equals(this.tlsServerContext.getServerVersion())) {
            throw new TlsFatalAlert((short) 47);
        }
        this.tlsServer.notifyClientVersion(this.tlsServerContext.getClientVersion());
        securityParametersHandshake.clientRandom = clientHello.getRandom();
        this.tlsServer.notifyFallback(Arrays.contains(this.offeredCipherSuites, CipherSuite.TLS_FALLBACK_SCSV));
        this.tlsServer.notifyOfferedCipherSuites(this.offeredCipherSuites);
        if (securityParametersHandshake.isRenegotiating()) {
            serverVersion = this.tlsServerContext.getServerVersion();
        } else {
            serverVersion = this.tlsServer.getServerVersion();
            if (!ProtocolVersion.contains(this.tlsServerContext.getClientSupportedVersions(), serverVersion)) {
                throw new TlsFatalAlert((short) 80);
            }
            securityParametersHandshake.negotiatedVersion = serverVersion;
        }
        ProtocolVersion protocolVersion = serverVersion;
        securityParametersHandshake.clientSupportedGroups = TlsExtensionsUtils.getSupportedGroupsExtension(clientHello.getExtensions());
        securityParametersHandshake.serverSupportedGroups = this.tlsServer.getSupportedGroups();
        boolean z2 = false;
        if (ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(protocolVersion)) {
            this.recordStream.setIgnoreChangeCipherSpec(true);
            this.recordStream.setWriteVersion(ProtocolVersion.TLSv12);
            return generate13ServerHello(clientHello, handshakeMessageInput, false);
        }
        this.recordStream.setWriteVersion(protocolVersion);
        Hashtable extensions = clientHello.getExtensions();
        this.clientExtensions = extensions;
        Integer num = TlsProtocol.EXT_RenegotiationInfo;
        byte[] extensionData = TlsUtils.getExtensionData(extensions, num);
        if (!securityParametersHandshake.isRenegotiating()) {
            if (Arrays.contains(this.offeredCipherSuites, 255)) {
                securityParametersHandshake.secureRenegotiation = true;
            }
            if (extensionData != null) {
                securityParametersHandshake.secureRenegotiation = true;
                if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                    throw new TlsFatalAlert((short) 40);
                }
            }
        } else {
            if (!securityParametersHandshake.isSecureRenegotiation()) {
                throw new TlsFatalAlert((short) 80);
            }
            if (Arrays.contains(this.offeredCipherSuites, 255)) {
                throw new TlsFatalAlert((short) 40);
            }
            if (extensionData == null) {
                throw new TlsFatalAlert((short) 40);
            }
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(this.tlsServerContext.getSecurityParametersConnection().getPeerVerifyData()))) {
                throw new TlsFatalAlert((short) 40);
            }
        }
        this.tlsServer.notifySecureRenegotiation(securityParametersHandshake.isSecureRenegotiation());
        boolean hasExtendedMasterSecretExtension = TlsExtensionsUtils.hasExtendedMasterSecretExtension(this.clientExtensions);
        Hashtable hashtable = this.clientExtensions;
        if (hashtable != null) {
            TlsExtensionsUtils.getPaddingExtension(hashtable);
            securityParametersHandshake.clientServerNames = TlsExtensionsUtils.getServerNameExtensionClient(this.clientExtensions);
            if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(latestTLS)) {
                TlsUtils.establishClientSigAlgs(securityParametersHandshake, this.clientExtensions);
            }
            securityParametersHandshake.clientSupportedGroups = TlsExtensionsUtils.getSupportedGroupsExtension(this.clientExtensions);
            this.tlsServer.processClientExtensions(this.clientExtensions);
        }
        boolean establishSession = establishSession(this.tlsServer.getSessionToResume(clientHello.getSessionID()));
        this.resumedSession = establishSession;
        if (!establishSession) {
            byte[] newSessionID = this.tlsServer.getNewSessionID();
            if (newSessionID == null) {
                newSessionID = TlsUtils.EMPTY_BYTES;
            }
            this.tlsSession = TlsUtils.importSession(newSessionID, null);
            this.sessionParameters = null;
            this.sessionMasterSecret = null;
        }
        securityParametersHandshake.sessionID = this.tlsSession.getSessionID();
        this.tlsServer.notifySession(this.tlsSession);
        TlsUtils.negotiatedVersionTLSServer(this.tlsServerContext);
        securityParametersHandshake.serverRandom = TlsProtocol.createRandomBlock(this.tlsServer.shouldUseGMTUnixTime(), this.tlsServerContext);
        if (!protocolVersion.equals(ProtocolVersion.getLatestTLS(this.tlsServer.getProtocolVersions()))) {
            TlsUtils.writeDowngradeMarker(protocolVersion, securityParametersHandshake.getServerRandom());
        }
        int cipherSuite = this.resumedSession ? this.sessionParameters.getCipherSuite() : this.tlsServer.getSelectedCipherSuite();
        if (!TlsUtils.isValidCipherSuiteSelection(this.offeredCipherSuites, cipherSuite) || !TlsUtils.isValidVersionForCipherSuite(cipherSuite, protocolVersion)) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.negotiatedCipherSuite(securityParametersHandshake, cipherSuite);
        this.tlsServerContext.setRSAPreMasterSecretVersion(version);
        Hashtable ensureExtensionsInitialised = TlsExtensionsUtils.ensureExtensionsInitialised(this.resumedSession ? this.sessionParameters.readServerExtensions() : this.tlsServer.getServerExtensions());
        this.serverExtensions = ensureExtensionsInitialised;
        this.tlsServer.getServerExtensionsForConnection(ensureExtensionsInitialised);
        if (securityParametersHandshake.isRenegotiating()) {
            if (!securityParametersHandshake.isSecureRenegotiation()) {
                throw new TlsFatalAlert((short) 80);
            }
            SecurityParameters securityParametersConnection = this.tlsServerContext.getSecurityParametersConnection();
            this.serverExtensions.put(num, TlsProtocol.createRenegotiationInfo(TlsUtils.concat(securityParametersConnection.getPeerVerifyData(), securityParametersConnection.getLocalVerifyData())));
        } else if (securityParametersHandshake.isSecureRenegotiation()) {
            if (TlsUtils.getExtensionData(this.serverExtensions, num) == null) {
                this.serverExtensions.put(num, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
            }
        }
        if (!this.resumedSession) {
            if (hasExtendedMasterSecretExtension && !protocolVersion.isSSL() && this.tlsServer.shouldUseExtendedMasterSecret()) {
                z2 = true;
            }
            securityParametersHandshake.extendedMasterSecret = z2;
            if (!securityParametersHandshake.isExtendedMasterSecret()) {
                if (this.tlsServer.requiresExtendedMasterSecret()) {
                    throw new TlsFatalAlert((short) 40);
                }
                securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(this.serverExtensions);
                securityParametersHandshake.applicationProtocolSet = true;
                if (!this.serverExtensions.isEmpty()) {
                    securityParametersHandshake.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(this.serverExtensions);
                    securityParametersHandshake.maxFragmentLength = processMaxFragmentLengthExtension(this.clientExtensions, this.serverExtensions, (short) 80);
                    securityParametersHandshake.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(this.serverExtensions);
                    if (!this.resumedSession) {
                        if (TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsExtensionsUtils.EXT_status_request_v2, (short) 80)) {
                            securityParametersHandshake.statusRequestVersion = 2;
                        } else if (TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsExtensionsUtils.EXT_status_request, (short) 80)) {
                            securityParametersHandshake.statusRequestVersion = 1;
                        }
                        this.expectSessionTicket = TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsProtocol.EXT_SessionTicket, (short) 80);
                    }
                }
                applyMaxFragmentLengthExtension(securityParametersHandshake.getMaxFragmentLength());
                return new ServerHello(protocolVersion, securityParametersHandshake.getServerRandom(), this.tlsSession.getSessionID(), securityParametersHandshake.getCipherSuite(), this.serverExtensions);
            }
        } else {
            if (!this.sessionParameters.isExtendedMasterSecret()) {
                throw new TlsFatalAlert((short) 80);
            }
            if (!hasExtendedMasterSecretExtension) {
                throw new TlsFatalAlert((short) 40);
            }
            securityParametersHandshake.extendedMasterSecret = true;
        }
        TlsExtensionsUtils.addExtendedMasterSecretExtension(this.serverExtensions);
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(this.serverExtensions);
        securityParametersHandshake.applicationProtocolSet = true;
        if (!this.serverExtensions.isEmpty()) {
        }
        applyMaxFragmentLengthExtension(securityParametersHandshake.getMaxFragmentLength());
        return new ServerHello(protocolVersion, securityParametersHandshake.getServerRandom(), this.tlsSession.getSessionID(), securityParametersHandshake.getCipherSuite(), this.serverExtensions);
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public TlsContext getContext() {
        return this.tlsServerContext;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public AbstractTlsContext getContextAdmin() {
        return this.tlsServerContext;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public TlsPeer getPeer() {
        return this.tlsServer;
    }

    public void handle13HandshakeMessage(short s2, HandshakeMessageInput handshakeMessageInput) {
        if (!isTLSv13ConnectionState()) {
            throw new TlsFatalAlert((short) 80);
        }
        if (this.resumedSession) {
            throw new TlsFatalAlert((short) 80);
        }
        if (s2 == 1) {
            short s3 = this.connection_state;
            if (s3 == 0) {
                throw new TlsFatalAlert((short) 80);
            }
            if (s3 != 2) {
                throw new TlsFatalAlert((short) 10);
            }
            ClientHello receiveClientHelloMessage = receiveClientHelloMessage(handshakeMessageInput);
            this.connection_state = (short) 3;
            ServerHello generate13ServerHello = generate13ServerHello(receiveClientHelloMessage, handshakeMessageInput, true);
            sendServerHelloMessage(generate13ServerHello);
            this.connection_state = (short) 4;
            send13ServerHelloCoda(generate13ServerHello, true);
            return;
        }
        if (s2 == 11) {
            if (this.connection_state != 20) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13ClientCertificate(handshakeMessageInput);
            this.connection_state = (short) 15;
            return;
        }
        if (s2 == 15) {
            if (this.connection_state != 15) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13ClientCertificateVerify(handshakeMessageInput);
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 17;
            return;
        }
        if (s2 != 20) {
            if (s2 != 24) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13KeyUpdate(handshakeMessageInput);
            return;
        }
        short s4 = this.connection_state;
        if (s4 != 15) {
            if (s4 != 17) {
                if (s4 != 20) {
                    throw new TlsFatalAlert((short) 10);
                }
                skip13ClientCertificate();
            }
            receive13ClientFinished(handshakeMessageInput);
            this.connection_state = (short) 18;
            this.recordStream.setIgnoreChangeCipherSpec(false);
            this.recordStream.enablePendingCipherRead(false);
            completeHandshake();
        }
        skip13ClientCertificateVerify();
        receive13ClientFinished(handshakeMessageInput);
        this.connection_state = (short) 18;
        this.recordStream.setIgnoreChangeCipherSpec(false);
        this.recordStream.enablePendingCipherRead(false);
        completeHandshake();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0018, code lost:
    
        if (r0 != 14) goto L16;
     */
    @Override // org.bouncycastle.tls.TlsProtocol
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleAlertWarningMessage(short s2) {
        if (41 == s2 && this.certificateRequest != null && TlsUtils.isSSL(this.tlsServerContext)) {
            short s3 = this.connection_state;
            if (s3 == 12) {
                this.tlsServer.processClientSupplementalData(null);
            }
            notifyClientCertificate(Certificate.EMPTY_CHAIN);
            this.connection_state = (short) 15;
            return;
        }
        super.handleAlertWarningMessage(s2);
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void handleHandshakeMessage(short s2, HandshakeMessageInput handshakeMessageInput) {
        CertificateStatus certificateStatus;
        SecurityParameters securityParameters = this.tlsServerContext.getSecurityParameters();
        if (this.connection_state > 1 && TlsUtils.isTLSv13(securityParameters.getNegotiatedVersion())) {
            handle13HandshakeMessage(s2, handshakeMessageInput);
            return;
        }
        if (!isLegacyConnectionState()) {
            throw new TlsFatalAlert((short) 80);
        }
        if (this.resumedSession) {
            if (s2 != 20 || this.connection_state != 20) {
                throw new TlsFatalAlert((short) 10);
            }
            processFinishedMessage(handshakeMessageInput);
            this.connection_state = (short) 18;
            completeHandshake();
            return;
        }
        Certificate certificate = null;
        if (s2 != 1) {
            if (s2 == 11) {
                short s3 = this.connection_state;
                if (s3 == 12) {
                    this.tlsServer.processClientSupplementalData(null);
                } else if (s3 != 14) {
                    throw new TlsFatalAlert((short) 10);
                }
                receiveCertificateMessage(handshakeMessageInput);
                this.connection_state = (short) 15;
                return;
            }
            if (s2 == 20) {
                short s4 = this.connection_state;
                if (s4 != 16) {
                    if (s4 != 17) {
                        throw new TlsFatalAlert((short) 10);
                    }
                } else if (expectCertificateVerifyMessage()) {
                    throw new TlsFatalAlert((short) 10);
                }
                processFinishedMessage(handshakeMessageInput);
                handshakeMessageInput.updateHash(this.handshakeHash);
                this.connection_state = (short) 18;
                if (this.expectSessionTicket) {
                    sendNewSessionTicketMessage(this.tlsServer.getNewSessionTicket());
                    this.connection_state = (short) 19;
                }
                sendChangeCipherSpec();
                sendFinishedMessage();
                this.connection_state = (short) 20;
                completeHandshake();
                return;
            }
            if (s2 == 23) {
                if (this.connection_state != 12) {
                    throw new TlsFatalAlert((short) 10);
                }
                this.tlsServer.processClientSupplementalData(TlsProtocol.readSupplementalDataMessage(handshakeMessageInput));
                this.connection_state = (short) 14;
                return;
            }
            if (s2 == 15) {
                if (this.connection_state != 16) {
                    throw new TlsFatalAlert((short) 10);
                }
                if (!expectCertificateVerifyMessage()) {
                    throw new TlsFatalAlert((short) 10);
                }
                receiveCertificateVerifyMessage(handshakeMessageInput);
                handshakeMessageInput.updateHash(this.handshakeHash);
                this.connection_state = (short) 17;
                return;
            }
            if (s2 != 16) {
                throw new TlsFatalAlert((short) 10);
            }
            short s5 = this.connection_state;
            if (s5 == 12) {
                this.tlsServer.processClientSupplementalData(null);
            } else if (s5 != 14) {
                if (s5 != 15) {
                    throw new TlsFatalAlert((short) 10);
                }
                receiveClientKeyExchangeMessage(handshakeMessageInput);
                this.connection_state = (short) 16;
                return;
            }
            if (this.certificateRequest == null) {
                this.keyExchange.skipClientCredentials();
            } else {
                if (TlsUtils.isTLSv12(this.tlsServerContext)) {
                    throw new TlsFatalAlert((short) 10);
                }
                if (TlsUtils.isSSL(this.tlsServerContext)) {
                    throw new TlsFatalAlert((short) 10);
                }
                notifyClientCertificate(Certificate.EMPTY_CHAIN);
            }
            receiveClientKeyExchangeMessage(handshakeMessageInput);
            this.connection_state = (short) 16;
            return;
        }
        if (isApplicationDataReady()) {
            if (!handleRenegotiation()) {
                return;
            } else {
                this.connection_state = (short) 0;
            }
        }
        short s6 = this.connection_state;
        if (s6 != 0) {
            if (s6 == 21) {
                throw new TlsFatalAlert((short) 80);
            }
            throw new TlsFatalAlert((short) 10);
        }
        ClientHello receiveClientHelloMessage = receiveClientHelloMessage(handshakeMessageInput);
        this.connection_state = (short) 1;
        ServerHello generateServerHello = generateServerHello(receiveClientHelloMessage, handshakeMessageInput);
        this.handshakeHash.notifyPRFDetermined();
        if (TlsUtils.isTLSv13(securityParameters.getNegotiatedVersion())) {
            this.handshakeHash.sealHashAlgorithms();
            if (generateServerHello.isHelloRetryRequest()) {
                TlsUtils.adjustTranscriptForRetry(this.handshakeHash);
                sendServerHelloMessage(generateServerHello);
                this.connection_state = (short) 2;
                sendChangeCipherSpecMessage();
                return;
            }
            sendServerHelloMessage(generateServerHello);
            this.connection_state = (short) 4;
            sendChangeCipherSpecMessage();
            send13ServerHelloCoda(generateServerHello, false);
            return;
        }
        handshakeMessageInput.updateHash(this.handshakeHash);
        sendServerHelloMessage(generateServerHello);
        this.connection_state = (short) 4;
        if (this.resumedSession) {
            securityParameters.masterSecret = this.sessionMasterSecret;
            this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsServerContext));
            sendChangeCipherSpec();
            sendFinishedMessage();
            this.connection_state = (short) 20;
            return;
        }
        Vector serverSupplementalData = this.tlsServer.getServerSupplementalData();
        if (serverSupplementalData != null) {
            sendSupplementalDataMessage(serverSupplementalData);
            this.connection_state = (short) 6;
        }
        this.keyExchange = TlsUtils.initKeyExchangeServer(this.tlsServerContext, this.tlsServer);
        TlsCredentials establishServerCredentials = TlsUtils.establishServerCredentials(this.tlsServer);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsKeyExchange tlsKeyExchange = this.keyExchange;
        if (establishServerCredentials == null) {
            tlsKeyExchange.skipServerCredentials();
        } else {
            tlsKeyExchange.processServerCredentials(establishServerCredentials);
            certificate = establishServerCredentials.getCertificate();
            sendCertificateMessage(certificate, byteArrayOutputStream);
            this.connection_state = (short) 7;
        }
        securityParameters.tlsServerEndPoint = byteArrayOutputStream.toByteArray();
        if (certificate == null || certificate.isEmpty()) {
            securityParameters.statusRequestVersion = 0;
        }
        if (securityParameters.getStatusRequestVersion() > 0 && (certificateStatus = this.tlsServer.getCertificateStatus()) != null) {
            sendCertificateStatusMessage(certificateStatus);
            this.connection_state = (short) 8;
        }
        byte[] generateServerKeyExchange = this.keyExchange.generateServerKeyExchange();
        if (generateServerKeyExchange != null) {
            sendServerKeyExchangeMessage(generateServerKeyExchange);
            this.connection_state = (short) 10;
        }
        if (establishServerCredentials != null) {
            CertificateRequest certificateRequest = this.tlsServer.getCertificateRequest();
            this.certificateRequest = certificateRequest;
            if (certificateRequest != null) {
                if (TlsUtils.isTLSv12(this.tlsServerContext) != (this.certificateRequest.getSupportedSignatureAlgorithms() != null)) {
                    throw new TlsFatalAlert((short) 80);
                }
                CertificateRequest validateCertificateRequest = TlsUtils.validateCertificateRequest(this.certificateRequest, this.keyExchange);
                this.certificateRequest = validateCertificateRequest;
                TlsUtils.establishServerSigAlgs(securityParameters, validateCertificateRequest);
                TlsUtils.trackHashAlgorithms(this.handshakeHash, securityParameters.getServerSigAlgs());
                sendCertificateRequestMessage(this.certificateRequest);
                this.connection_state = (short) 11;
            } else if (!this.keyExchange.requiresCertificateVerify()) {
                throw new TlsFatalAlert((short) 80);
            }
        }
        sendServerHelloDoneMessage();
        this.connection_state = (short) 12;
        TlsUtils.sealHandshakeHash(this.tlsServerContext, this.handshakeHash, false);
    }

    public void notifyClientCertificate(Certificate certificate) {
        if (this.certificateRequest == null) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.processClientCertificate(this.tlsServerContext, certificate, this.keyExchange, this.tlsServer);
    }

    public void receive13ClientCertificate(ByteArrayInputStream byteArrayInputStream) {
        if (this.certificateRequest == null) {
            throw new TlsFatalAlert((short) 10);
        }
        Certificate parse = Certificate.parse(new Certificate.ParseOptions().setMaxChainLength(this.tlsServer.getMaxCertificateChainLength()), this.tlsServerContext, byteArrayInputStream, null);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        notifyClientCertificate(parse);
    }

    public void receive13ClientCertificateVerify(ByteArrayInputStream byteArrayInputStream) {
        Certificate peerCertificate = this.tlsServerContext.getSecurityParametersHandshake().getPeerCertificate();
        if (peerCertificate == null || peerCertificate.isEmpty()) {
            throw new TlsFatalAlert((short) 80);
        }
        DigitallySigned parse = DigitallySigned.parse(this.tlsServerContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        TlsUtils.verify13CertificateVerifyClient(this.tlsServerContext, this.certificateRequest, parse, this.handshakeHash);
    }

    public void receive13ClientFinished(ByteArrayInputStream byteArrayInputStream) {
        process13FinishedMessage(byteArrayInputStream);
    }

    public void receiveCertificateMessage(ByteArrayInputStream byteArrayInputStream) {
        if (this.certificateRequest == null) {
            throw new TlsFatalAlert((short) 10);
        }
        Certificate parse = Certificate.parse(new Certificate.ParseOptions().setMaxChainLength(this.tlsServer.getMaxCertificateChainLength()), this.tlsServerContext, byteArrayInputStream, null);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        notifyClientCertificate(parse);
    }

    public void receiveCertificateVerifyMessage(ByteArrayInputStream byteArrayInputStream) {
        DigitallySigned parse = DigitallySigned.parse(this.tlsServerContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        TlsUtils.verifyCertificateVerifyClient(this.tlsServerContext, this.certificateRequest, parse, this.handshakeHash);
        this.handshakeHash = this.handshakeHash.stopTracking();
    }

    public ClientHello receiveClientHelloMessage(ByteArrayInputStream byteArrayInputStream) {
        return ClientHello.parse(byteArrayInputStream, null);
    }

    public void receiveClientKeyExchangeMessage(ByteArrayInputStream byteArrayInputStream) {
        this.keyExchange.processClientKeyExchange(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        boolean isSSL = TlsUtils.isSSL(this.tlsServerContext);
        if (isSSL) {
            TlsProtocol.establishMasterSecret(this.tlsServerContext, this.keyExchange);
        }
        this.tlsServerContext.getSecurityParametersHandshake().sessionHash = TlsUtils.getCurrentPRFHash(this.handshakeHash);
        if (!isSSL) {
            TlsProtocol.establishMasterSecret(this.tlsServerContext, this.keyExchange);
        }
        this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsServerContext));
        if (expectCertificateVerifyMessage()) {
            return;
        }
        this.handshakeHash = this.handshakeHash.stopTracking();
    }

    public void send13EncryptedExtensionsMessage(Hashtable hashtable) {
        byte[] writeExtensionsData = TlsProtocol.writeExtensionsData(hashtable);
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 8);
        TlsUtils.writeOpaque16(writeExtensionsData, handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void send13ServerHelloCoda(ServerHello serverHello, boolean z2) {
        SecurityParameters securityParametersHandshake = this.tlsServerContext.getSecurityParametersHandshake();
        TlsUtils.establish13PhaseHandshake(this.tlsServerContext, TlsUtils.getCurrentPRFHash(this.handshakeHash), this.recordStream);
        this.recordStream.enablePendingCipherWrite();
        this.recordStream.enablePendingCipherRead(true);
        send13EncryptedExtensionsMessage(this.serverExtensions);
        this.connection_state = (short) 5;
        if (!this.selectedPSK13) {
            CertificateRequest certificateRequest = this.tlsServer.getCertificateRequest();
            this.certificateRequest = certificateRequest;
            if (certificateRequest != null) {
                if (!certificateRequest.hasCertificateRequestContext(TlsUtils.EMPTY_BYTES)) {
                    throw new TlsFatalAlert((short) 80);
                }
                TlsUtils.establishServerSigAlgs(securityParametersHandshake, this.certificateRequest);
                sendCertificateRequestMessage(this.certificateRequest);
                this.connection_state = (short) 11;
            }
            TlsCredentialedSigner establish13ServerCredentials = TlsUtils.establish13ServerCredentials(this.tlsServer);
            if (establish13ServerCredentials == null) {
                throw new TlsFatalAlert((short) 80);
            }
            send13CertificateMessage(establish13ServerCredentials.getCertificate());
            securityParametersHandshake.tlsServerEndPoint = null;
            this.connection_state = (short) 7;
            send13CertificateVerifyMessage(TlsUtils.generate13CertificateVerify(this.tlsServerContext, establish13ServerCredentials, this.handshakeHash));
            this.connection_state = (short) 17;
        }
        send13FinishedMessage();
        this.connection_state = (short) 20;
        TlsUtils.establish13PhaseApplication(this.tlsServerContext, TlsUtils.getCurrentPRFHash(this.handshakeHash), this.recordStream);
        this.recordStream.enablePendingCipherWrite();
    }

    public void sendCertificateRequestMessage(CertificateRequest certificateRequest) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 13);
        certificateRequest.encode(this.tlsServerContext, handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void sendCertificateStatusMessage(CertificateStatus certificateStatus) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 22);
        certificateStatus.encode(handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void sendHelloRequestMessage() {
        HandshakeMessageOutput.send(this, (short) 0, TlsUtils.EMPTY_BYTES);
    }

    public void sendNewSessionTicketMessage(NewSessionTicket newSessionTicket) {
        if (newSessionTicket == null) {
            throw new TlsFatalAlert((short) 80);
        }
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 4);
        newSessionTicket.encode(handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void sendServerHelloDoneMessage() {
        HandshakeMessageOutput.send(this, (short) 14, TlsUtils.EMPTY_BYTES);
    }

    public void sendServerHelloMessage(ServerHello serverHello) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 2);
        serverHello.encode(this.tlsServerContext, handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void sendServerKeyExchangeMessage(byte[] bArr) {
        HandshakeMessageOutput.send(this, (short) 12, bArr);
    }

    public void skip13ClientCertificate() {
        if (this.certificateRequest != null) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    public void skip13ClientCertificateVerify() {
        if (expectCertificateVerifyMessage()) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    public TlsServerProtocol(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
        this.tlsServer = null;
        this.tlsServerContext = null;
        this.offeredCipherSuites = null;
        this.keyExchange = null;
        this.certificateRequest = null;
    }
}
