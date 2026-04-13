package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.DTLSReliableHandshake;
import org.bouncycastle.tls.SessionParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class DTLSClientProtocol extends DTLSProtocol {

    public static class ClientHandshakeState {
        TlsClient client = null;
        TlsClientContextImpl clientContext = null;
        TlsSession tlsSession = null;
        SessionParameters sessionParameters = null;
        TlsSecret sessionMasterSecret = null;
        SessionParameters.Builder sessionParametersBuilder = null;
        int[] offeredCipherSuites = null;
        Hashtable clientExtensions = null;
        Hashtable serverExtensions = null;
        boolean resumedSession = false;
        boolean expectSessionTicket = false;
        Hashtable clientAgreements = null;
        TlsKeyExchange keyExchange = null;
        TlsAuthentication authentication = null;
        CertificateStatus certificateStatus = null;
        CertificateRequest certificateRequest = null;
        TlsCredentials clientCredentials = null;
        TlsHeartbeat heartbeat = null;
        short heartbeatPolicy = 2;
    }

    public static byte[] patchClientHelloWithCookie(byte[] bArr, byte[] bArr2) {
        int readUint8 = TlsUtils.readUint8(bArr, 34) + 35;
        int i2 = readUint8 + 1;
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, readUint8);
        TlsUtils.checkUint8(bArr2.length);
        TlsUtils.writeUint8(bArr2.length, bArr3, readUint8);
        System.arraycopy(bArr2, 0, bArr3, i2, bArr2.length);
        System.arraycopy(bArr, i2, bArr3, bArr2.length + i2, bArr.length - i2);
        return bArr3;
    }

    public void abortClientHandshake(ClientHandshakeState clientHandshakeState, DTLSRecordLayer dTLSRecordLayer, short s2) {
        dTLSRecordLayer.fail(s2);
        invalidateSession(clientHandshakeState);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0207  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DTLSTransport clientHandshake(ClientHandshakeState clientHandshakeState, DTLSRecordLayer dTLSRecordLayer) {
        DTLSReliableHandshake.Message receiveMessage;
        TlsCredentialedSigner tlsCredentialedSigner;
        TlsStreamSigner tlsStreamSigner;
        SecurityParameters securityParametersHandshake = clientHandshakeState.clientContext.getSecurityParametersHandshake();
        DTLSReliableHandshake dTLSReliableHandshake = new DTLSReliableHandshake(clientHandshakeState.clientContext, dTLSRecordLayer, clientHandshakeState.client.getHandshakeTimeoutMillis(), null);
        byte[] generateClientHello = generateClientHello(clientHandshakeState);
        dTLSRecordLayer.setWriteVersion(ProtocolVersion.DTLSv10);
        dTLSReliableHandshake.sendMessage((short) 1, generateClientHello);
        while (true) {
            receiveMessage = dTLSReliableHandshake.receiveMessage();
            if (receiveMessage.getType() != 3) {
                break;
            }
            byte[] patchClientHelloWithCookie = patchClientHelloWithCookie(generateClientHello, processHelloVerifyRequest(clientHandshakeState, receiveMessage.getBody()));
            dTLSReliableHandshake.resetAfterHelloVerifyRequestClient();
            dTLSReliableHandshake.sendMessage((short) 1, patchClientHelloWithCookie);
        }
        if (receiveMessage.getType() != 2) {
            throw new TlsFatalAlert((short) 10);
        }
        ProtocolVersion readVersion = dTLSRecordLayer.getReadVersion();
        reportServerVersion(clientHandshakeState, readVersion);
        dTLSRecordLayer.setWriteVersion(readVersion);
        processServerHello(clientHandshakeState, receiveMessage.getBody());
        dTLSReliableHandshake.getHandshakeHash().notifyPRFDetermined();
        DTLSProtocol.applyMaxFragmentLengthExtension(dTLSRecordLayer, securityParametersHandshake.getMaxFragmentLength());
        if (clientHandshakeState.resumedSession) {
            securityParametersHandshake.masterSecret = clientHandshakeState.sessionMasterSecret;
            dTLSRecordLayer.initPendingEpoch(TlsUtils.initCipher(clientHandshakeState.clientContext));
            securityParametersHandshake.peerVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), true);
            processFinished(dTLSReliableHandshake.receiveMessageBody((short) 20), securityParametersHandshake.getPeerVerifyData());
            securityParametersHandshake.localVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), false);
            dTLSReliableHandshake.sendMessage((short) 20, securityParametersHandshake.getLocalVerifyData());
            dTLSReliableHandshake.finish();
            if (securityParametersHandshake.isExtendedMasterSecret()) {
                securityParametersHandshake.tlsUnique = securityParametersHandshake.getPeerVerifyData();
            }
            securityParametersHandshake.localCertificate = clientHandshakeState.sessionParameters.getLocalCertificate();
            securityParametersHandshake.peerCertificate = clientHandshakeState.sessionParameters.getPeerCertificate();
            securityParametersHandshake.pskIdentity = clientHandshakeState.sessionParameters.getPSKIdentity();
            securityParametersHandshake.srpIdentity = clientHandshakeState.sessionParameters.getSRPIdentity();
            clientHandshakeState.clientContext.handshakeComplete(clientHandshakeState.client, clientHandshakeState.tlsSession);
            dTLSRecordLayer.initHeartbeat(clientHandshakeState.heartbeat, 1 == clientHandshakeState.heartbeatPolicy);
            return new DTLSTransport(dTLSRecordLayer);
        }
        invalidateSession(clientHandshakeState);
        clientHandshakeState.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), null);
        DTLSReliableHandshake.Message receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        if (receiveMessage2.getType() == 23) {
            processServerSupplementalData(clientHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        } else {
            clientHandshakeState.client.processServerSupplementalData(null);
        }
        clientHandshakeState.keyExchange = TlsUtils.initKeyExchangeClient(clientHandshakeState.clientContext, clientHandshakeState.client);
        if (receiveMessage2.getType() == 11) {
            processServerCertificate(clientHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        } else {
            clientHandshakeState.authentication = null;
        }
        if (receiveMessage2.getType() == 22) {
            if (securityParametersHandshake.getStatusRequestVersion() < 1) {
                throw new TlsFatalAlert((short) 10);
            }
            processCertificateStatus(clientHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        }
        TlsUtils.processServerCertificate(clientHandshakeState.clientContext, clientHandshakeState.certificateStatus, clientHandshakeState.keyExchange, clientHandshakeState.authentication, clientHandshakeState.clientExtensions, clientHandshakeState.serverExtensions);
        if (receiveMessage2.getType() == 12) {
            processServerKeyExchange(clientHandshakeState, receiveMessage2.getBody());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        } else {
            clientHandshakeState.keyExchange.skipServerKeyExchange();
        }
        if (receiveMessage2.getType() == 13) {
            processCertificateRequest(clientHandshakeState, receiveMessage2.getBody());
            TlsUtils.establishServerSigAlgs(securityParametersHandshake, clientHandshakeState.certificateRequest);
            TlsUtils.trackHashAlgorithms(dTLSReliableHandshake.getHandshakeHash(), securityParametersHandshake.getServerSigAlgs());
            receiveMessage2 = dTLSReliableHandshake.receiveMessage();
        }
        if (receiveMessage2.getType() != 14) {
            throw new TlsFatalAlert((short) 10);
        }
        if (receiveMessage2.getBody().length != 0) {
            throw new TlsFatalAlert((short) 50);
        }
        Vector clientSupplementalData = clientHandshakeState.client.getClientSupplementalData();
        if (clientSupplementalData != null) {
            dTLSReliableHandshake.sendMessage((short) 23, DTLSProtocol.generateSupplementalData(clientSupplementalData));
        }
        CertificateRequest certificateRequest = clientHandshakeState.certificateRequest;
        if (certificateRequest != null) {
            TlsCredentials establishClientCredentials = TlsUtils.establishClientCredentials(clientHandshakeState.authentication, certificateRequest);
            clientHandshakeState.clientCredentials = establishClientCredentials;
            DTLSProtocol.sendCertificateMessage(clientHandshakeState.clientContext, dTLSReliableHandshake, establishClientCredentials != null ? establishClientCredentials.getCertificate() : null, null);
        }
        TlsCredentials tlsCredentials = clientHandshakeState.clientCredentials;
        if (tlsCredentials != null) {
            clientHandshakeState.keyExchange.processClientCredentials(tlsCredentials);
            TlsCredentials tlsCredentials2 = clientHandshakeState.clientCredentials;
            if (tlsCredentials2 instanceof TlsCredentialedSigner) {
                tlsCredentialedSigner = (TlsCredentialedSigner) tlsCredentials2;
                tlsStreamSigner = tlsCredentialedSigner.getStreamSigner();
                TlsUtils.sealHandshakeHash(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), tlsStreamSigner == null);
                dTLSReliableHandshake.sendMessage((short) 16, generateClientKeyExchange(clientHandshakeState));
                securityParametersHandshake.sessionHash = TlsUtils.getCurrentPRFHash(dTLSReliableHandshake.getHandshakeHash());
                TlsProtocol.establishMasterSecret(clientHandshakeState.clientContext, clientHandshakeState.keyExchange);
                dTLSRecordLayer.initPendingEpoch(TlsUtils.initCipher(clientHandshakeState.clientContext));
                if (tlsCredentialedSigner != null) {
                    dTLSReliableHandshake.sendMessage((short) 15, generateCertificateVerify(clientHandshakeState, TlsUtils.generateCertificateVerifyClient(clientHandshakeState.clientContext, tlsCredentialedSigner, tlsStreamSigner, dTLSReliableHandshake.getHandshakeHash())));
                }
                dTLSReliableHandshake.prepareToFinish();
                securityParametersHandshake.localVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), false);
                dTLSReliableHandshake.sendMessage((short) 20, securityParametersHandshake.getLocalVerifyData());
                if (clientHandshakeState.expectSessionTicket) {
                    DTLSReliableHandshake.Message receiveMessage3 = dTLSReliableHandshake.receiveMessage();
                    if (receiveMessage3.getType() != 4) {
                        throw new TlsFatalAlert((short) 10);
                    }
                    securityParametersHandshake.sessionID = TlsUtils.EMPTY_BYTES;
                    invalidateSession(clientHandshakeState);
                    clientHandshakeState.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), null);
                    processNewSessionTicket(clientHandshakeState, receiveMessage3.getBody());
                }
                securityParametersHandshake.peerVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), true);
                processFinished(dTLSReliableHandshake.receiveMessageBody((short) 20), securityParametersHandshake.getPeerVerifyData());
                dTLSReliableHandshake.finish();
                clientHandshakeState.sessionMasterSecret = securityParametersHandshake.getMasterSecret();
                clientHandshakeState.sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParametersHandshake.getCipherSuite()).setCompressionAlgorithm(securityParametersHandshake.getCompressionAlgorithm()).setExtendedMasterSecret(securityParametersHandshake.isExtendedMasterSecret()).setLocalCertificate(securityParametersHandshake.getLocalCertificate()).setMasterSecret(clientHandshakeState.clientContext.getCrypto().adoptSecret(clientHandshakeState.sessionMasterSecret)).setNegotiatedVersion(securityParametersHandshake.getNegotiatedVersion()).setPeerCertificate(securityParametersHandshake.getPeerCertificate()).setPSKIdentity(securityParametersHandshake.getPSKIdentity()).setSRPIdentity(securityParametersHandshake.getSRPIdentity()).setServerExtensions(clientHandshakeState.serverExtensions).build();
                clientHandshakeState.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), clientHandshakeState.sessionParameters);
                securityParametersHandshake.tlsUnique = securityParametersHandshake.getLocalVerifyData();
                clientHandshakeState.clientContext.handshakeComplete(clientHandshakeState.client, clientHandshakeState.tlsSession);
                dTLSRecordLayer.initHeartbeat(clientHandshakeState.heartbeat, 1 != clientHandshakeState.heartbeatPolicy);
                return new DTLSTransport(dTLSRecordLayer);
            }
        } else {
            clientHandshakeState.keyExchange.skipClientCredentials();
        }
        tlsCredentialedSigner = null;
        tlsStreamSigner = null;
        TlsUtils.sealHandshakeHash(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), tlsStreamSigner == null);
        dTLSReliableHandshake.sendMessage((short) 16, generateClientKeyExchange(clientHandshakeState));
        securityParametersHandshake.sessionHash = TlsUtils.getCurrentPRFHash(dTLSReliableHandshake.getHandshakeHash());
        TlsProtocol.establishMasterSecret(clientHandshakeState.clientContext, clientHandshakeState.keyExchange);
        dTLSRecordLayer.initPendingEpoch(TlsUtils.initCipher(clientHandshakeState.clientContext));
        if (tlsCredentialedSigner != null) {
        }
        dTLSReliableHandshake.prepareToFinish();
        securityParametersHandshake.localVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), false);
        dTLSReliableHandshake.sendMessage((short) 20, securityParametersHandshake.getLocalVerifyData());
        if (clientHandshakeState.expectSessionTicket) {
        }
        securityParametersHandshake.peerVerifyData = TlsUtils.calculateVerifyData(clientHandshakeState.clientContext, dTLSReliableHandshake.getHandshakeHash(), true);
        processFinished(dTLSReliableHandshake.receiveMessageBody((short) 20), securityParametersHandshake.getPeerVerifyData());
        dTLSReliableHandshake.finish();
        clientHandshakeState.sessionMasterSecret = securityParametersHandshake.getMasterSecret();
        clientHandshakeState.sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParametersHandshake.getCipherSuite()).setCompressionAlgorithm(securityParametersHandshake.getCompressionAlgorithm()).setExtendedMasterSecret(securityParametersHandshake.isExtendedMasterSecret()).setLocalCertificate(securityParametersHandshake.getLocalCertificate()).setMasterSecret(clientHandshakeState.clientContext.getCrypto().adoptSecret(clientHandshakeState.sessionMasterSecret)).setNegotiatedVersion(securityParametersHandshake.getNegotiatedVersion()).setPeerCertificate(securityParametersHandshake.getPeerCertificate()).setPSKIdentity(securityParametersHandshake.getPSKIdentity()).setSRPIdentity(securityParametersHandshake.getSRPIdentity()).setServerExtensions(clientHandshakeState.serverExtensions).build();
        clientHandshakeState.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), clientHandshakeState.sessionParameters);
        securityParametersHandshake.tlsUnique = securityParametersHandshake.getLocalVerifyData();
        clientHandshakeState.clientContext.handshakeComplete(clientHandshakeState.client, clientHandshakeState.tlsSession);
        dTLSRecordLayer.initHeartbeat(clientHandshakeState.heartbeat, 1 != clientHandshakeState.heartbeatPolicy);
        return new DTLSTransport(dTLSRecordLayer);
    }

    public DTLSTransport connect(TlsClient tlsClient, DatagramTransport datagramTransport) {
        SessionParameters exportSessionParameters;
        if (tlsClient == null) {
            throw new IllegalArgumentException("'client' cannot be null");
        }
        if (datagramTransport == null) {
            throw new IllegalArgumentException("'transport' cannot be null");
        }
        ClientHandshakeState clientHandshakeState = new ClientHandshakeState();
        clientHandshakeState.client = tlsClient;
        TlsClientContextImpl tlsClientContextImpl = new TlsClientContextImpl(tlsClient.getCrypto());
        clientHandshakeState.clientContext = tlsClientContextImpl;
        tlsClient.init(tlsClientContextImpl);
        clientHandshakeState.clientContext.handshakeBeginning(tlsClient);
        SecurityParameters securityParametersHandshake = clientHandshakeState.clientContext.getSecurityParametersHandshake();
        securityParametersHandshake.extendedPadding = tlsClient.shouldUseExtendedPadding();
        TlsSession sessionToResume = clientHandshakeState.client.getSessionToResume();
        if (sessionToResume != null && sessionToResume.isResumable() && (exportSessionParameters = sessionToResume.exportSessionParameters()) != null && (exportSessionParameters.isExtendedMasterSecret() || (!clientHandshakeState.client.requiresExtendedMasterSecret() && clientHandshakeState.client.allowLegacyResumption()))) {
            TlsSecret masterSecret = exportSessionParameters.getMasterSecret();
            synchronized (masterSecret) {
                if (masterSecret.isAlive()) {
                    clientHandshakeState.tlsSession = sessionToResume;
                    clientHandshakeState.sessionParameters = exportSessionParameters;
                    clientHandshakeState.sessionMasterSecret = clientHandshakeState.clientContext.getCrypto().adoptSecret(masterSecret);
                }
            }
        }
        DTLSRecordLayer dTLSRecordLayer = new DTLSRecordLayer(clientHandshakeState.clientContext, clientHandshakeState.client, datagramTransport);
        tlsClient.notifyCloseHandle(dTLSRecordLayer);
        try {
            try {
                try {
                    return clientHandshake(clientHandshakeState, dTLSRecordLayer);
                } catch (RuntimeException e2) {
                    abortClientHandshake(clientHandshakeState, dTLSRecordLayer, (short) 80);
                    throw new TlsFatalAlert((short) 80, (Throwable) e2);
                }
            } catch (TlsFatalAlert e3) {
                abortClientHandshake(clientHandshakeState, dTLSRecordLayer, e3.getAlertDescription());
                throw e3;
            } catch (IOException e4) {
                abortClientHandshake(clientHandshakeState, dTLSRecordLayer, (short) 80);
                throw e4;
            }
        } finally {
            securityParametersHandshake.clear();
        }
    }

    public byte[] generateCertificateVerify(ClientHandshakeState clientHandshakeState, DigitallySigned digitallySigned) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        digitallySigned.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateClientHello(ClientHandshakeState clientHandshakeState) {
        ProtocolVersion protocolVersion;
        SessionParameters sessionParameters;
        TlsClientContextImpl tlsClientContextImpl = clientHandshakeState.clientContext;
        SecurityParameters securityParametersHandshake = tlsClientContextImpl.getSecurityParametersHandshake();
        tlsClientContextImpl.setClientSupportedVersions(clientHandshakeState.client.getProtocolVersions());
        ProtocolVersion latestDTLS = ProtocolVersion.getLatestDTLS(tlsClientContextImpl.getClientSupportedVersions());
        if (!ProtocolVersion.isSupportedDTLSVersionClient(latestDTLS)) {
            throw new TlsFatalAlert((short) 80);
        }
        tlsClientContextImpl.setClientVersion(latestDTLS);
        byte[] sessionID = TlsUtils.getSessionID(clientHandshakeState.tlsSession);
        boolean isFallback = clientHandshakeState.client.isFallback();
        int[] cipherSuites = clientHandshakeState.client.getCipherSuites();
        clientHandshakeState.offeredCipherSuites = cipherSuites;
        if (sessionID.length > 0 && (sessionParameters = clientHandshakeState.sessionParameters) != null && (!Arrays.contains(cipherSuites, sessionParameters.getCipherSuite()) || clientHandshakeState.sessionParameters.getCompressionAlgorithm() != 0)) {
            sessionID = TlsUtils.EMPTY_BYTES;
        }
        byte[] bArr = sessionID;
        clientHandshakeState.clientExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(clientHandshakeState.client.getClientExtensions());
        ProtocolVersion protocolVersion2 = ProtocolVersion.DTLSv12;
        if (latestDTLS.isLaterVersionOf(protocolVersion2)) {
            TlsExtensionsUtils.addSupportedVersionsExtensionClient(clientHandshakeState.clientExtensions, tlsClientContextImpl.getClientSupportedVersions());
            protocolVersion = protocolVersion2;
        } else {
            protocolVersion = latestDTLS;
        }
        tlsClientContextImpl.setRSAPreMasterSecretVersion(protocolVersion);
        securityParametersHandshake.clientServerNames = TlsExtensionsUtils.getServerNameExtensionClient(clientHandshakeState.clientExtensions);
        if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(latestDTLS)) {
            TlsUtils.establishClientSigAlgs(securityParametersHandshake, clientHandshakeState.clientExtensions);
        }
        securityParametersHandshake.clientSupportedGroups = TlsExtensionsUtils.getSupportedGroupsExtension(clientHandshakeState.clientExtensions);
        clientHandshakeState.clientAgreements = TlsUtils.addKeyShareToClientHello(clientHandshakeState.clientContext, clientHandshakeState.client, clientHandshakeState.clientExtensions);
        if (TlsUtils.isExtendedMasterSecretOptionalDTLS(tlsClientContextImpl.getClientSupportedVersions()) && clientHandshakeState.client.shouldUseExtendedMasterSecret()) {
            TlsExtensionsUtils.addExtendedMasterSecretExtension(clientHandshakeState.clientExtensions);
        } else if (!TlsUtils.isTLSv13(latestDTLS) && clientHandshakeState.client.requiresExtendedMasterSecret()) {
            throw new TlsFatalAlert((short) 80);
        }
        securityParametersHandshake.clientRandom = TlsProtocol.createRandomBlock(protocolVersion2.isEqualOrLaterVersionOf(latestDTLS) && clientHandshakeState.client.shouldUseGMTUnixTime(), clientHandshakeState.clientContext);
        boolean z2 = TlsUtils.getExtensionData(clientHandshakeState.clientExtensions, TlsProtocol.EXT_RenegotiationInfo) == null;
        boolean z3 = !Arrays.contains(clientHandshakeState.offeredCipherSuites, 255);
        if (z2 && z3) {
            clientHandshakeState.offeredCipherSuites = Arrays.append(clientHandshakeState.offeredCipherSuites, 255);
        }
        if (isFallback && !Arrays.contains(clientHandshakeState.offeredCipherSuites, CipherSuite.TLS_FALLBACK_SCSV)) {
            clientHandshakeState.offeredCipherSuites = Arrays.append(clientHandshakeState.offeredCipherSuites, CipherSuite.TLS_FALLBACK_SCSV);
        }
        clientHandshakeState.heartbeat = clientHandshakeState.client.getHeartbeat();
        short heartbeatPolicy = clientHandshakeState.client.getHeartbeatPolicy();
        clientHandshakeState.heartbeatPolicy = heartbeatPolicy;
        if (clientHandshakeState.heartbeat != null || 1 == heartbeatPolicy) {
            TlsExtensionsUtils.addHeartbeatExtension(clientHandshakeState.clientExtensions, new HeartbeatExtension(heartbeatPolicy));
        }
        ClientHello clientHello = new ClientHello(protocolVersion, securityParametersHandshake.getClientRandom(), bArr, TlsUtils.EMPTY_BYTES, clientHandshakeState.offeredCipherSuites, clientHandshakeState.clientExtensions, 0);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        clientHello.encode(clientHandshakeState.clientContext, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateClientKeyExchange(ClientHandshakeState clientHandshakeState) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        clientHandshakeState.keyExchange.generateClientKeyExchange(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void invalidateSession(ClientHandshakeState clientHandshakeState) {
        TlsSecret tlsSecret = clientHandshakeState.sessionMasterSecret;
        if (tlsSecret != null) {
            tlsSecret.destroy();
            clientHandshakeState.sessionMasterSecret = null;
        }
        SessionParameters sessionParameters = clientHandshakeState.sessionParameters;
        if (sessionParameters != null) {
            sessionParameters.clear();
            clientHandshakeState.sessionParameters = null;
        }
        TlsSession tlsSession = clientHandshakeState.tlsSession;
        if (tlsSession != null) {
            tlsSession.invalidate();
            clientHandshakeState.tlsSession = null;
        }
    }

    public void processCertificateRequest(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        if (clientHandshakeState.authentication == null) {
            throw new TlsFatalAlert((short) 40);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        CertificateRequest parse = CertificateRequest.parse(clientHandshakeState.clientContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        clientHandshakeState.certificateRequest = TlsUtils.validateCertificateRequest(parse, clientHandshakeState.keyExchange);
    }

    public void processCertificateStatus(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        clientHandshakeState.certificateStatus = CertificateStatus.parse(clientHandshakeState.clientContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    public byte[] processHelloVerifyRequest(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        byte[] readOpaque8 = TlsUtils.readOpaque8(byteArrayInputStream, 0, ProtocolVersion.DTLSv12.isEqualOrEarlierVersionOf(readVersion) ? 255 : 32);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        if (readVersion.isEqualOrEarlierVersionOf(clientHandshakeState.clientContext.getClientVersion())) {
            return readOpaque8;
        }
        throw new TlsFatalAlert((short) 47);
    }

    public void processNewSessionTicket(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        NewSessionTicket parse = NewSessionTicket.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        clientHandshakeState.client.notifyNewSessionTicket(parse);
    }

    public void processServerCertificate(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        clientHandshakeState.authentication = TlsUtils.receiveServerCertificate(clientHandshakeState.clientContext, clientHandshakeState.client, new ByteArrayInputStream(bArr));
    }

    public void processServerHello(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        TlsSession tlsSession;
        ServerHello parse = ServerHello.parse(new ByteArrayInputStream(bArr));
        ProtocolVersion version = parse.getVersion();
        clientHandshakeState.serverExtensions = parse.getExtensions();
        SecurityParameters securityParametersHandshake = clientHandshakeState.clientContext.getSecurityParametersHandshake();
        reportServerVersion(clientHandshakeState, version);
        securityParametersHandshake.serverRandom = parse.getRandom();
        if (!clientHandshakeState.clientContext.getClientVersion().equals(version)) {
            TlsUtils.checkDowngradeMarker(version, securityParametersHandshake.getServerRandom());
        }
        byte[] sessionID = parse.getSessionID();
        securityParametersHandshake.sessionID = sessionID;
        clientHandshakeState.client.notifySessionID(sessionID);
        boolean z2 = false;
        clientHandshakeState.resumedSession = sessionID.length > 0 && (tlsSession = clientHandshakeState.tlsSession) != null && Arrays.areEqual(sessionID, tlsSession.getSessionID());
        int validateSelectedCipherSuite = DTLSProtocol.validateSelectedCipherSuite(parse.getCipherSuite(), (short) 47);
        if (!TlsUtils.isValidCipherSuiteSelection(clientHandshakeState.offeredCipherSuites, validateSelectedCipherSuite) || !TlsUtils.isValidVersionForCipherSuite(validateSelectedCipherSuite, securityParametersHandshake.getNegotiatedVersion())) {
            throw new TlsFatalAlert((short) 47);
        }
        TlsUtils.negotiatedCipherSuite(securityParametersHandshake, validateSelectedCipherSuite);
        clientHandshakeState.client.notifySelectedCipherSuite(validateSelectedCipherSuite);
        if (TlsUtils.isTLSv13(version)) {
            securityParametersHandshake.extendedMasterSecret = true;
        } else {
            boolean hasExtendedMasterSecretExtension = TlsExtensionsUtils.hasExtendedMasterSecretExtension(clientHandshakeState.serverExtensions);
            if (hasExtendedMasterSecretExtension) {
                if (!clientHandshakeState.resumedSession && !clientHandshakeState.client.shouldUseExtendedMasterSecret()) {
                    throw new TlsFatalAlert((short) 40);
                }
            } else if (clientHandshakeState.client.requiresExtendedMasterSecret() || (clientHandshakeState.resumedSession && !clientHandshakeState.client.allowLegacyResumption())) {
                throw new TlsFatalAlert((short) 40);
            }
            securityParametersHandshake.extendedMasterSecret = hasExtendedMasterSecretExtension;
        }
        Hashtable hashtable = clientHandshakeState.serverExtensions;
        if (hashtable != null) {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                Integer num = (Integer) keys.nextElement();
                if (!num.equals(TlsProtocol.EXT_RenegotiationInfo) && TlsUtils.getExtensionData(clientHandshakeState.clientExtensions, num) == null) {
                    throw new TlsFatalAlert(AlertDescription.unsupported_extension);
                }
            }
        }
        byte[] extensionData = TlsUtils.getExtensionData(clientHandshakeState.serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
        if (extensionData != null) {
            securityParametersHandshake.secureRenegotiation = true;
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                throw new TlsFatalAlert((short) 40);
            }
        }
        clientHandshakeState.client.notifySecureRenegotiation(securityParametersHandshake.isSecureRenegotiation());
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(clientHandshakeState.serverExtensions);
        securityParametersHandshake.applicationProtocolSet = true;
        HeartbeatExtension heartbeatExtension = TlsExtensionsUtils.getHeartbeatExtension(clientHandshakeState.serverExtensions);
        Hashtable hashtable2 = null;
        if (heartbeatExtension == null) {
            clientHandshakeState.heartbeat = null;
            clientHandshakeState.heartbeatPolicy = (short) 2;
        } else if (1 != heartbeatExtension.getMode()) {
            clientHandshakeState.heartbeat = null;
        }
        Hashtable hashtable3 = clientHandshakeState.clientExtensions;
        Hashtable hashtable4 = clientHandshakeState.serverExtensions;
        if (!clientHandshakeState.resumedSession) {
            hashtable2 = hashtable3;
        } else {
            if (securityParametersHandshake.getCipherSuite() != clientHandshakeState.sessionParameters.getCipherSuite() || clientHandshakeState.sessionParameters.getCompressionAlgorithm() != 0 || !version.equals(clientHandshakeState.sessionParameters.getNegotiatedVersion())) {
                throw new TlsFatalAlert((short) 47);
            }
            hashtable4 = clientHandshakeState.sessionParameters.readServerExtensions();
        }
        if (hashtable4 != null && !hashtable4.isEmpty()) {
            boolean hasEncryptThenMACExtension = TlsExtensionsUtils.hasEncryptThenMACExtension(hashtable4);
            if (hasEncryptThenMACExtension && !TlsUtils.isBlockCipherSuite(securityParametersHandshake.getCipherSuite())) {
                throw new TlsFatalAlert((short) 47);
            }
            securityParametersHandshake.encryptThenMAC = hasEncryptThenMACExtension;
            securityParametersHandshake.maxFragmentLength = DTLSProtocol.evaluateMaxFragmentLengthExtension(clientHandshakeState.resumedSession, hashtable2, hashtable4, (short) 47);
            securityParametersHandshake.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(hashtable4);
            if (!clientHandshakeState.resumedSession) {
                if (TlsUtils.hasExpectedEmptyExtensionData(hashtable4, TlsExtensionsUtils.EXT_status_request_v2, (short) 47)) {
                    securityParametersHandshake.statusRequestVersion = 2;
                } else if (TlsUtils.hasExpectedEmptyExtensionData(hashtable4, TlsExtensionsUtils.EXT_status_request, (short) 47)) {
                    securityParametersHandshake.statusRequestVersion = 1;
                }
            }
            if (!clientHandshakeState.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(hashtable4, TlsProtocol.EXT_SessionTicket, (short) 47)) {
                z2 = true;
            }
            clientHandshakeState.expectSessionTicket = z2;
        }
        if (hashtable2 != null) {
            clientHandshakeState.client.processServerExtensions(hashtable4);
        }
    }

    public void processServerKeyExchange(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        clientHandshakeState.keyExchange.processServerKeyExchange(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    public void processServerSupplementalData(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        clientHandshakeState.client.processServerSupplementalData(TlsProtocol.readSupplementalDataMessage(new ByteArrayInputStream(bArr)));
    }

    public void reportServerVersion(ClientHandshakeState clientHandshakeState, ProtocolVersion protocolVersion) {
        TlsClientContextImpl tlsClientContextImpl = clientHandshakeState.clientContext;
        SecurityParameters securityParametersHandshake = tlsClientContextImpl.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        if (negotiatedVersion != null) {
            if (!negotiatedVersion.equals(protocolVersion)) {
                throw new TlsFatalAlert((short) 47);
            }
        } else {
            if (!ProtocolVersion.contains(tlsClientContextImpl.getClientSupportedVersions(), protocolVersion)) {
                throw new TlsFatalAlert((short) 70);
            }
            securityParametersHandshake.negotiatedVersion = protocolVersion;
            TlsUtils.negotiatedVersionDTLSClient(clientHandshakeState.clientContext, clientHandshakeState.client);
        }
    }
}
