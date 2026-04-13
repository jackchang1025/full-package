package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.DTLSReliableHandshake;
import org.bouncycastle.tls.SessionParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class DTLSServerProtocol extends DTLSProtocol {
    protected boolean verifyRequests = true;

    public static class ServerHandshakeState {
        TlsServer server = null;
        TlsServerContextImpl serverContext = null;
        TlsSession tlsSession = null;
        SessionParameters sessionParameters = null;
        TlsSecret sessionMasterSecret = null;
        SessionParameters.Builder sessionParametersBuilder = null;
        int[] offeredCipherSuites = null;
        Hashtable clientExtensions = null;
        Hashtable serverExtensions = null;
        boolean offeredExtendedMasterSecret = false;
        boolean resumedSession = false;
        boolean expectSessionTicket = false;
        TlsKeyExchange keyExchange = null;
        TlsCredentials serverCredentials = null;
        CertificateRequest certificateRequest = null;
        TlsHeartbeat heartbeat = null;
        short heartbeatPolicy = 2;
    }

    public void abortServerHandshake(ServerHandshakeState serverHandshakeState, DTLSRecordLayer dTLSRecordLayer, short s2) {
        dTLSRecordLayer.fail(s2);
        invalidateSession(serverHandshakeState);
    }

    public DTLSTransport accept(TlsServer tlsServer, DatagramTransport datagramTransport) {
        return accept(tlsServer, datagramTransport, null);
    }

    public boolean expectCertificateVerifyMessage(ServerHandshakeState serverHandshakeState) {
        Certificate peerCertificate;
        if (serverHandshakeState.certificateRequest == null || (peerCertificate = serverHandshakeState.serverContext.getSecurityParametersHandshake().getPeerCertificate()) == null || peerCertificate.isEmpty()) {
            return false;
        }
        TlsKeyExchange tlsKeyExchange = serverHandshakeState.keyExchange;
        return tlsKeyExchange == null || tlsKeyExchange.requiresCertificateVerify();
    }

    public byte[] generateCertificateRequest(ServerHandshakeState serverHandshakeState, CertificateRequest certificateRequest) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificateRequest.encode(serverHandshakeState.serverContext, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateCertificateStatus(ServerHandshakeState serverHandshakeState, CertificateStatus certificateStatus) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificateStatus.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateNewSessionTicket(ServerHandshakeState serverHandshakeState, NewSessionTicket newSessionTicket) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newSessionTicket.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateServerHello(ServerHandshakeState serverHandshakeState, DTLSRecordLayer dTLSRecordLayer) {
        ProtocolVersion protocolVersion;
        TlsServerContextImpl tlsServerContextImpl = serverHandshakeState.serverContext;
        SecurityParameters securityParametersHandshake = tlsServerContextImpl.getSecurityParametersHandshake();
        ProtocolVersion serverVersion = serverHandshakeState.server.getServerVersion();
        if (!ProtocolVersion.contains(tlsServerContextImpl.getClientSupportedVersions(), serverVersion)) {
            throw new TlsFatalAlert((short) 80);
        }
        securityParametersHandshake.negotiatedVersion = serverVersion;
        TlsUtils.negotiatedVersionDTLSServer(tlsServerContextImpl);
        ProtocolVersion protocolVersion2 = ProtocolVersion.DTLSv12;
        boolean z2 = false;
        securityParametersHandshake.serverRandom = TlsProtocol.createRandomBlock(protocolVersion2.isEqualOrLaterVersionOf(serverVersion) && serverHandshakeState.server.shouldUseGMTUnixTime(), tlsServerContextImpl);
        if (!serverVersion.equals(ProtocolVersion.getLatestDTLS(serverHandshakeState.server.getProtocolVersions()))) {
            TlsUtils.writeDowngradeMarker(serverVersion, securityParametersHandshake.getServerRandom());
        }
        int validateSelectedCipherSuite = DTLSProtocol.validateSelectedCipherSuite(serverHandshakeState.server.getSelectedCipherSuite(), (short) 80);
        if (!TlsUtils.isValidCipherSuiteSelection(serverHandshakeState.offeredCipherSuites, validateSelectedCipherSuite) || !TlsUtils.isValidVersionForCipherSuite(validateSelectedCipherSuite, securityParametersHandshake.getNegotiatedVersion())) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.negotiatedCipherSuite(securityParametersHandshake, validateSelectedCipherSuite);
        Hashtable ensureExtensionsInitialised = TlsExtensionsUtils.ensureExtensionsInitialised(serverHandshakeState.server.getServerExtensions());
        serverHandshakeState.serverExtensions = ensureExtensionsInitialised;
        serverHandshakeState.server.getServerExtensionsForConnection(ensureExtensionsInitialised);
        if (serverVersion.isLaterVersionOf(protocolVersion2)) {
            TlsExtensionsUtils.addSupportedVersionsExtensionServer(serverHandshakeState.serverExtensions, serverVersion);
            protocolVersion = protocolVersion2;
        } else {
            protocolVersion = serverVersion;
        }
        if (securityParametersHandshake.isSecureRenegotiation()) {
            Hashtable hashtable = serverHandshakeState.serverExtensions;
            Integer num = TlsProtocol.EXT_RenegotiationInfo;
            if (TlsUtils.getExtensionData(hashtable, num) == null) {
                serverHandshakeState.serverExtensions.put(num, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
            }
        }
        if (TlsUtils.isTLSv13(serverVersion)) {
            securityParametersHandshake.extendedMasterSecret = true;
        } else {
            securityParametersHandshake.extendedMasterSecret = serverHandshakeState.offeredExtendedMasterSecret && serverHandshakeState.server.shouldUseExtendedMasterSecret();
            if (securityParametersHandshake.isExtendedMasterSecret()) {
                TlsExtensionsUtils.addExtendedMasterSecretExtension(serverHandshakeState.serverExtensions);
            } else {
                if (serverHandshakeState.server.requiresExtendedMasterSecret()) {
                    throw new TlsFatalAlert((short) 40);
                }
                if (serverHandshakeState.resumedSession && !serverHandshakeState.server.allowLegacyResumption()) {
                    throw new TlsFatalAlert((short) 80);
                }
            }
        }
        if (serverHandshakeState.heartbeat != null || 1 == serverHandshakeState.heartbeatPolicy) {
            TlsExtensionsUtils.addHeartbeatExtension(serverHandshakeState.serverExtensions, new HeartbeatExtension(serverHandshakeState.heartbeatPolicy));
        }
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(serverHandshakeState.serverExtensions);
        securityParametersHandshake.applicationProtocolSet = true;
        if (!serverHandshakeState.serverExtensions.isEmpty()) {
            securityParametersHandshake.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(serverHandshakeState.serverExtensions);
            securityParametersHandshake.maxFragmentLength = DTLSProtocol.evaluateMaxFragmentLengthExtension(serverHandshakeState.resumedSession, serverHandshakeState.clientExtensions, serverHandshakeState.serverExtensions, (short) 80);
            securityParametersHandshake.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(serverHandshakeState.serverExtensions);
            if (!serverHandshakeState.resumedSession) {
                if (TlsUtils.hasExpectedEmptyExtensionData(serverHandshakeState.serverExtensions, TlsExtensionsUtils.EXT_status_request_v2, (short) 80)) {
                    securityParametersHandshake.statusRequestVersion = 2;
                } else if (TlsUtils.hasExpectedEmptyExtensionData(serverHandshakeState.serverExtensions, TlsExtensionsUtils.EXT_status_request, (short) 80)) {
                    securityParametersHandshake.statusRequestVersion = 1;
                }
            }
            if (!serverHandshakeState.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(serverHandshakeState.serverExtensions, TlsProtocol.EXT_SessionTicket, (short) 80)) {
                z2 = true;
            }
            serverHandshakeState.expectSessionTicket = z2;
        }
        DTLSProtocol.applyMaxFragmentLengthExtension(dTLSRecordLayer, securityParametersHandshake.getMaxFragmentLength());
        ServerHello serverHello = new ServerHello(protocolVersion, securityParametersHandshake.getServerRandom(), serverHandshakeState.tlsSession.getSessionID(), securityParametersHandshake.getCipherSuite(), serverHandshakeState.serverExtensions);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serverHello.encode(serverHandshakeState.serverContext, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean getVerifyRequests() {
        return this.verifyRequests;
    }

    public void invalidateSession(ServerHandshakeState serverHandshakeState) {
        TlsSecret tlsSecret = serverHandshakeState.sessionMasterSecret;
        if (tlsSecret != null) {
            tlsSecret.destroy();
            serverHandshakeState.sessionMasterSecret = null;
        }
        SessionParameters sessionParameters = serverHandshakeState.sessionParameters;
        if (sessionParameters != null) {
            sessionParameters.clear();
            serverHandshakeState.sessionParameters = null;
        }
        TlsSession tlsSession = serverHandshakeState.tlsSession;
        if (tlsSession != null) {
            tlsSession.invalidate();
            serverHandshakeState.tlsSession = null;
        }
    }

    public void notifyClientCertificate(ServerHandshakeState serverHandshakeState, Certificate certificate) {
        if (serverHandshakeState.certificateRequest == null) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.processClientCertificate(serverHandshakeState.serverContext, certificate, serverHandshakeState.keyExchange, serverHandshakeState.server);
    }

    public void processCertificateVerify(ServerHandshakeState serverHandshakeState, byte[] bArr, TlsHandshakeHash tlsHandshakeHash) {
        if (serverHandshakeState.certificateRequest == null) {
            throw new IllegalStateException();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        TlsServerContextImpl tlsServerContextImpl = serverHandshakeState.serverContext;
        DigitallySigned parse = DigitallySigned.parse(tlsServerContextImpl, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        TlsUtils.verifyCertificateVerifyClient(tlsServerContextImpl, serverHandshakeState.certificateRequest, parse, tlsHandshakeHash);
    }

    public void processClientCertificate(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Certificate parse = Certificate.parse(new Certificate.ParseOptions().setMaxChainLength(serverHandshakeState.server.getMaxCertificateChainLength()), serverHandshakeState.serverContext, byteArrayInputStream, null);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        notifyClientCertificate(serverHandshakeState, parse);
    }

    public void processClientHello(ServerHandshakeState serverHandshakeState, ClientHello clientHello) {
        ProtocolVersion version = clientHello.getVersion();
        serverHandshakeState.offeredCipherSuites = clientHello.getCipherSuites();
        serverHandshakeState.clientExtensions = clientHello.getExtensions();
        TlsServerContextImpl tlsServerContextImpl = serverHandshakeState.serverContext;
        SecurityParameters securityParametersHandshake = tlsServerContextImpl.getSecurityParametersHandshake();
        if (!version.isDTLS()) {
            throw new TlsFatalAlert((short) 47);
        }
        tlsServerContextImpl.setRSAPreMasterSecretVersion(version);
        tlsServerContextImpl.setClientSupportedVersions(TlsExtensionsUtils.getSupportedVersionsExtensionClient(serverHandshakeState.clientExtensions));
        if (tlsServerContextImpl.getClientSupportedVersions() == null) {
            ProtocolVersion protocolVersion = ProtocolVersion.DTLSv12;
            if (version.isLaterVersionOf(protocolVersion)) {
                version = protocolVersion;
            }
            tlsServerContextImpl.setClientSupportedVersions(version.downTo(ProtocolVersion.DTLSv10));
        } else {
            version = ProtocolVersion.getLatestDTLS(tlsServerContextImpl.getClientSupportedVersions());
        }
        if (!ProtocolVersion.SERVER_EARLIEST_SUPPORTED_DTLS.isEqualOrEarlierVersionOf(version)) {
            throw new TlsFatalAlert((short) 70);
        }
        tlsServerContextImpl.setClientVersion(version);
        serverHandshakeState.server.notifyClientVersion(tlsServerContextImpl.getClientVersion());
        securityParametersHandshake.clientRandom = clientHello.getRandom();
        serverHandshakeState.server.notifyFallback(Arrays.contains(serverHandshakeState.offeredCipherSuites, CipherSuite.TLS_FALLBACK_SCSV));
        serverHandshakeState.server.notifyOfferedCipherSuites(serverHandshakeState.offeredCipherSuites);
        if (Arrays.contains(serverHandshakeState.offeredCipherSuites, 255)) {
            securityParametersHandshake.secureRenegotiation = true;
        }
        byte[] extensionData = TlsUtils.getExtensionData(serverHandshakeState.clientExtensions, TlsProtocol.EXT_RenegotiationInfo);
        if (extensionData != null) {
            securityParametersHandshake.secureRenegotiation = true;
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                throw new TlsFatalAlert((short) 40);
            }
        }
        serverHandshakeState.server.notifySecureRenegotiation(securityParametersHandshake.isSecureRenegotiation());
        serverHandshakeState.offeredExtendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(serverHandshakeState.clientExtensions);
        Hashtable hashtable = serverHandshakeState.clientExtensions;
        if (hashtable != null) {
            TlsExtensionsUtils.getPaddingExtension(hashtable);
            securityParametersHandshake.clientServerNames = TlsExtensionsUtils.getServerNameExtensionClient(serverHandshakeState.clientExtensions);
            if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(version)) {
                TlsUtils.establishClientSigAlgs(securityParametersHandshake, serverHandshakeState.clientExtensions);
            }
            securityParametersHandshake.clientSupportedGroups = TlsExtensionsUtils.getSupportedGroupsExtension(serverHandshakeState.clientExtensions);
            HeartbeatExtension heartbeatExtension = TlsExtensionsUtils.getHeartbeatExtension(serverHandshakeState.clientExtensions);
            if (heartbeatExtension != null) {
                if (1 == heartbeatExtension.getMode()) {
                    serverHandshakeState.heartbeat = serverHandshakeState.server.getHeartbeat();
                }
                serverHandshakeState.heartbeatPolicy = serverHandshakeState.server.getHeartbeatPolicy();
            }
            serverHandshakeState.server.processClientExtensions(serverHandshakeState.clientExtensions);
        }
    }

    public void processClientKeyExchange(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        serverHandshakeState.keyExchange.processClientKeyExchange(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    public void processClientSupplementalData(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        serverHandshakeState.server.processClientSupplementalData(TlsProtocol.readSupplementalDataMessage(new ByteArrayInputStream(bArr)));
    }

    public DTLSTransport serverHandshake(ServerHandshakeState serverHandshakeState, DTLSRecordLayer dTLSRecordLayer, DTLSRequest dTLSRequest) {
        Certificate certificate;
        CertificateStatus certificateStatus;
        SecurityParameters securityParametersHandshake = serverHandshakeState.serverContext.getSecurityParametersHandshake();
        DTLSReliableHandshake dTLSReliableHandshake = new DTLSReliableHandshake(serverHandshakeState.serverContext, dTLSRecordLayer, serverHandshakeState.server.getHandshakeTimeoutMillis(), dTLSRequest);
        if (dTLSRequest == null) {
            DTLSReliableHandshake.Message receiveMessage = dTLSReliableHandshake.receiveMessage();
            if (receiveMessage.getType() != 1) {
                throw new TlsFatalAlert((short) 10);
            }
            processClientHello(serverHandshakeState, receiveMessage.getBody());
        } else {
            processClientHello(serverHandshakeState, dTLSRequest.getClientHello());
        }
        byte[] bArr = TlsUtils.EMPTY_BYTES;
        TlsSession importSession = TlsUtils.importSession(bArr, null);
        serverHandshakeState.tlsSession = importSession;
        serverHandshakeState.sessionParameters = null;
        serverHandshakeState.sessionMasterSecret = null;
        securityParametersHandshake.sessionID = importSession.getSessionID();
        serverHandshakeState.server.notifySession(serverHandshakeState.tlsSession);
        byte[] generateServerHello = generateServerHello(serverHandshakeState, dTLSRecordLayer);
        ProtocolVersion serverVersion = serverHandshakeState.serverContext.getServerVersion();
        dTLSRecordLayer.setReadVersion(serverVersion);
        dTLSRecordLayer.setWriteVersion(serverVersion);
        dTLSReliableHandshake.sendMessage((short) 2, generateServerHello);
        dTLSReliableHandshake.getHandshakeHash().notifyPRFDetermined();
        Vector serverSupplementalData = serverHandshakeState.server.getServerSupplementalData();
        if (serverSupplementalData != null) {
            dTLSReliableHandshake.sendMessage((short) 23, DTLSProtocol.generateSupplementalData(serverSupplementalData));
        }
        serverHandshakeState.keyExchange = TlsUtils.initKeyExchangeServer(serverHandshakeState.serverContext, serverHandshakeState.server);
        serverHandshakeState.serverCredentials = TlsUtils.establishServerCredentials(serverHandshakeState.server);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsCredentials tlsCredentials = serverHandshakeState.serverCredentials;
        if (tlsCredentials == null) {
            serverHandshakeState.keyExchange.skipServerCredentials();
            certificate = null;
        } else {
            serverHandshakeState.keyExchange.processServerCredentials(tlsCredentials);
            certificate = serverHandshakeState.serverCredentials.getCertificate();
            DTLSProtocol.sendCertificateMessage(serverHandshakeState.serverContext, dTLSReliableHandshake, certificate, byteArrayOutputStream);
        }
        securityParametersHandshake.tlsServerEndPoint = byteArrayOutputStream.toByteArray();
        if (certificate == null || certificate.isEmpty()) {
            securityParametersHandshake.statusRequestVersion = 0;
        }
        if (securityParametersHandshake.getStatusRequestVersion() > 0 && (certificateStatus = serverHandshakeState.server.getCertificateStatus()) != null) {
            dTLSReliableHandshake.sendMessage((short) 22, generateCertificateStatus(serverHandshakeState, certificateStatus));
        }
        byte[] generateServerKeyExchange = serverHandshakeState.keyExchange.generateServerKeyExchange();
        if (generateServerKeyExchange != null) {
            dTLSReliableHandshake.sendMessage((short) 12, generateServerKeyExchange);
        }
        if (serverHandshakeState.serverCredentials != null) {
            CertificateRequest certificateRequest = serverHandshakeState.server.getCertificateRequest();
            serverHandshakeState.certificateRequest = certificateRequest;
            if (certificateRequest != null) {
                if (TlsUtils.isTLSv12(serverHandshakeState.serverContext) != (serverHandshakeState.certificateRequest.getSupportedSignatureAlgorithms() != null)) {
                    throw new TlsFatalAlert((short) 80);
                }
                CertificateRequest validateCertificateRequest = TlsUtils.validateCertificateRequest(serverHandshakeState.certificateRequest, serverHandshakeState.keyExchange);
                serverHandshakeState.certificateRequest = validateCertificateRequest;
                TlsUtils.establishServerSigAlgs(securityParametersHandshake, validateCertificateRequest);
                TlsUtils.trackHashAlgorithms(dTLSReliableHandshake.getHandshakeHash(), securityParametersHandshake.getServerSigAlgs());
                dTLSReliableHandshake.sendMessage((short) 13, generateCertificateRequest(serverHandshakeState, serverHandshakeState.certificateRequest));
            } else if (!serverHandshakeState.keyExchange.requiresCertificateVerify()) {
                throw new TlsFatalAlert((short) 80);
            }
        }
        dTLSReliableHandshake.sendMessage((short) 14, bArr);
        TlsUtils.sealHandshakeHash(serverHandshakeState.serverContext, dTLSReliableHandshake.getHandshakeHash(), false);
        DTLSReliableHandshake.Message receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        if (receiveMessage2.getType() == 23) {
            processClientSupplementalData(serverHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        } else {
            serverHandshakeState.server.processClientSupplementalData(null);
        }
        if (serverHandshakeState.certificateRequest == null) {
            serverHandshakeState.keyExchange.skipClientCredentials();
        } else if (receiveMessage2.getType() == 11) {
            processClientCertificate(serverHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        } else {
            if (TlsUtils.isTLSv12(serverHandshakeState.serverContext)) {
                throw new TlsFatalAlert((short) 10);
            }
            notifyClientCertificate(serverHandshakeState, Certificate.EMPTY_CHAIN);
        }
        if (receiveMessage2.getType() != 16) {
            throw new TlsFatalAlert((short) 10);
        }
        processClientKeyExchange(serverHandshakeState, receiveMessage2.getBody());
        securityParametersHandshake.sessionHash = TlsUtils.getCurrentPRFHash(dTLSReliableHandshake.getHandshakeHash());
        TlsProtocol.establishMasterSecret(serverHandshakeState.serverContext, serverHandshakeState.keyExchange);
        dTLSRecordLayer.initPendingEpoch(TlsUtils.initCipher(serverHandshakeState.serverContext));
        TlsHandshakeHash prepareToFinish = dTLSReliableHandshake.prepareToFinish();
        if (expectCertificateVerifyMessage(serverHandshakeState)) {
            processCertificateVerify(serverHandshakeState, dTLSReliableHandshake.receiveMessageBody((short) 15), prepareToFinish);
        }
        securityParametersHandshake.peerVerifyData = TlsUtils.calculateVerifyData(serverHandshakeState.serverContext, dTLSReliableHandshake.getHandshakeHash(), false);
        processFinished(dTLSReliableHandshake.receiveMessageBody((short) 20), securityParametersHandshake.getPeerVerifyData());
        if (serverHandshakeState.expectSessionTicket) {
            dTLSReliableHandshake.sendMessage((short) 4, generateNewSessionTicket(serverHandshakeState, serverHandshakeState.server.getNewSessionTicket()));
        }
        securityParametersHandshake.localVerifyData = TlsUtils.calculateVerifyData(serverHandshakeState.serverContext, dTLSReliableHandshake.getHandshakeHash(), true);
        dTLSReliableHandshake.sendMessage((short) 20, securityParametersHandshake.getLocalVerifyData());
        dTLSReliableHandshake.finish();
        serverHandshakeState.sessionMasterSecret = securityParametersHandshake.getMasterSecret();
        serverHandshakeState.sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParametersHandshake.getCipherSuite()).setCompressionAlgorithm(securityParametersHandshake.getCompressionAlgorithm()).setExtendedMasterSecret(securityParametersHandshake.isExtendedMasterSecret()).setLocalCertificate(securityParametersHandshake.getLocalCertificate()).setMasterSecret(serverHandshakeState.serverContext.getCrypto().adoptSecret(serverHandshakeState.sessionMasterSecret)).setNegotiatedVersion(securityParametersHandshake.getNegotiatedVersion()).setPeerCertificate(securityParametersHandshake.getPeerCertificate()).setPSKIdentity(securityParametersHandshake.getPSKIdentity()).setSRPIdentity(securityParametersHandshake.getSRPIdentity()).setServerExtensions(serverHandshakeState.serverExtensions).build();
        serverHandshakeState.tlsSession = TlsUtils.importSession(serverHandshakeState.tlsSession.getSessionID(), serverHandshakeState.sessionParameters);
        securityParametersHandshake.tlsUnique = securityParametersHandshake.getPeerVerifyData();
        serverHandshakeState.serverContext.handshakeComplete(serverHandshakeState.server, serverHandshakeState.tlsSession);
        dTLSRecordLayer.initHeartbeat(serverHandshakeState.heartbeat, 1 == serverHandshakeState.heartbeatPolicy);
        return new DTLSTransport(dTLSRecordLayer);
    }

    public void setVerifyRequests(boolean z2) {
        this.verifyRequests = z2;
    }

    public DTLSTransport accept(TlsServer tlsServer, DatagramTransport datagramTransport, DTLSRequest dTLSRequest) {
        if (tlsServer == null) {
            throw new IllegalArgumentException("'server' cannot be null");
        }
        if (datagramTransport == null) {
            throw new IllegalArgumentException("'transport' cannot be null");
        }
        ServerHandshakeState serverHandshakeState = new ServerHandshakeState();
        serverHandshakeState.server = tlsServer;
        TlsServerContextImpl tlsServerContextImpl = new TlsServerContextImpl(tlsServer.getCrypto());
        serverHandshakeState.serverContext = tlsServerContextImpl;
        tlsServer.init(tlsServerContextImpl);
        serverHandshakeState.serverContext.handshakeBeginning(tlsServer);
        SecurityParameters securityParametersHandshake = serverHandshakeState.serverContext.getSecurityParametersHandshake();
        securityParametersHandshake.extendedPadding = tlsServer.shouldUseExtendedPadding();
        DTLSRecordLayer dTLSRecordLayer = new DTLSRecordLayer(serverHandshakeState.serverContext, serverHandshakeState.server, datagramTransport);
        tlsServer.notifyCloseHandle(dTLSRecordLayer);
        try {
            try {
                try {
                    try {
                        return serverHandshake(serverHandshakeState, dTLSRecordLayer, dTLSRequest);
                    } catch (RuntimeException e2) {
                        abortServerHandshake(serverHandshakeState, dTLSRecordLayer, (short) 80);
                        throw new TlsFatalAlert((short) 80, (Throwable) e2);
                    }
                } catch (TlsFatalAlert e3) {
                    abortServerHandshake(serverHandshakeState, dTLSRecordLayer, e3.getAlertDescription());
                    throw e3;
                }
            } catch (IOException e4) {
                abortServerHandshake(serverHandshakeState, dTLSRecordLayer, (short) 80);
                throw e4;
            }
        } finally {
            securityParametersHandshake.clear();
        }
    }

    public void processClientHello(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        processClientHello(serverHandshakeState, ClientHello.parse(new ByteArrayInputStream(bArr), NullOutputStream.INSTANCE));
    }
}
