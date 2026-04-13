package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.OfferedPsks;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public class TlsClientProtocol extends TlsProtocol {
    protected TlsAuthentication authentication;
    protected CertificateRequest certificateRequest;
    protected CertificateStatus certificateStatus;
    protected Hashtable clientAgreements;
    OfferedPsks.BindersConfig clientBinders;
    protected ClientHello clientHello;
    protected TlsKeyExchange keyExchange;
    protected TlsClient tlsClient;
    TlsClientContextImpl tlsClientContext;

    public TlsClientProtocol() {
        this.tlsClient = null;
        this.tlsClientContext = null;
        this.clientAgreements = null;
        this.clientBinders = null;
        this.clientHello = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void beginHandshake(boolean z2) {
        super.beginHandshake(z2);
        sendClientHello();
        this.connection_state = (short) 1;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.clientAgreements = null;
        this.clientBinders = null;
        this.clientHello = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }

    public void connect(TlsClient tlsClient) {
        if (tlsClient == null) {
            throw new IllegalArgumentException("'tlsClient' cannot be null");
        }
        if (this.tlsClient != null) {
            throw new IllegalStateException("'connect' can only be called once");
        }
        this.tlsClient = tlsClient;
        TlsClientContextImpl tlsClientContextImpl = new TlsClientContextImpl(tlsClient.getCrypto());
        this.tlsClientContext = tlsClientContextImpl;
        tlsClient.init(tlsClientContextImpl);
        tlsClient.notifyCloseHandle(this);
        beginHandshake(false);
        if (this.blocking) {
            blockForHandshake();
        }
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public TlsContext getContext() {
        return this.tlsClientContext;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public AbstractTlsContext getContextAdmin() {
        return this.tlsClientContext;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public TlsPeer getPeer() {
        return this.tlsClient;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handle13HandshakeMessage(short s2, HandshakeMessageInput handshakeMessageInput) {
        CertificateRequest certificateRequest;
        if (!isTLSv13ConnectionState() || this.resumedSession) {
            throw new TlsFatalAlert((short) 80);
        }
        if (s2 == 2) {
            short s3 = this.connection_state;
            if (s3 == 1) {
                throw new TlsFatalAlert((short) 80);
            }
            if (s3 != 3) {
                throw new TlsFatalAlert((short) 10);
            }
            ServerHello receiveServerHelloMessage = receiveServerHelloMessage(handshakeMessageInput);
            if (receiveServerHelloMessage.isHelloRetryRequest()) {
                throw new TlsFatalAlert((short) 10);
            }
            process13ServerHello(receiveServerHelloMessage, true);
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 4;
            process13ServerHelloCoda(receiveServerHelloMessage, true);
            return;
        }
        if (s2 == 4) {
            receive13NewSessionTicket(handshakeMessageInput);
            return;
        }
        if (s2 == 8) {
            if (this.connection_state != 4) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13EncryptedExtensions(handshakeMessageInput);
            this.connection_state = (short) 5;
            return;
        }
        if (s2 == 11) {
            short s4 = this.connection_state;
            if (s4 == 5) {
                skip13CertificateRequest();
            } else if (s4 != 11) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13ServerCertificate(handshakeMessageInput);
            this.connection_state = (short) 7;
            return;
        }
        if (s2 == 13) {
            short s5 = this.connection_state;
            if (s5 != 5) {
                if (s5 == 21) {
                    throw new TlsFatalAlert((short) 10);
                }
                throw new TlsFatalAlert((short) 10);
            }
            receive13CertificateRequest(handshakeMessageInput, false);
            this.connection_state = (short) 11;
            return;
        }
        if (s2 == 15) {
            if (this.connection_state != 7) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13ServerCertificateVerify(handshakeMessageInput);
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 9;
            return;
        }
        if (s2 != 20) {
            if (s2 != 24) {
                throw new TlsFatalAlert((short) 10);
            }
            receive13KeyUpdate(handshakeMessageInput);
            return;
        }
        short s6 = this.connection_state;
        if (s6 != 5) {
            if (s6 != 9) {
                if (s6 != 11) {
                    throw new TlsFatalAlert((short) 10);
                }
            }
            receive13ServerFinished(handshakeMessageInput);
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 20;
            byte[] currentPRFHash = TlsUtils.getCurrentPRFHash(this.handshakeHash);
            this.recordStream.setIgnoreChangeCipherSpec(false);
            certificateRequest = this.certificateRequest;
            if (certificateRequest != null) {
                TlsCredentialedSigner establish13ClientCredentials = TlsUtils.establish13ClientCredentials(this.authentication, certificateRequest);
                Certificate certificate = establish13ClientCredentials != null ? establish13ClientCredentials.getCertificate() : null;
                if (certificate == null) {
                    certificate = Certificate.EMPTY_CHAIN_TLS13;
                }
                send13CertificateMessage(certificate);
                this.connection_state = (short) 15;
                if (establish13ClientCredentials != null) {
                    send13CertificateVerifyMessage(TlsUtils.generate13CertificateVerify(this.tlsClientContext, establish13ClientCredentials, this.handshakeHash));
                    this.connection_state = (short) 17;
                }
            }
            send13FinishedMessage();
            this.connection_state = (short) 18;
            TlsUtils.establish13PhaseApplication(this.tlsClientContext, currentPRFHash, this.recordStream);
            this.recordStream.enablePendingCipherWrite();
            this.recordStream.enablePendingCipherRead(false);
            completeHandshake();
        }
        skip13CertificateRequest();
        skip13ServerCertificate();
        receive13ServerFinished(handshakeMessageInput);
        handshakeMessageInput.updateHash(this.handshakeHash);
        this.connection_state = (short) 20;
        byte[] currentPRFHash2 = TlsUtils.getCurrentPRFHash(this.handshakeHash);
        this.recordStream.setIgnoreChangeCipherSpec(false);
        certificateRequest = this.certificateRequest;
        if (certificateRequest != null) {
        }
        send13FinishedMessage();
        this.connection_state = (short) 18;
        TlsUtils.establish13PhaseApplication(this.tlsClientContext, currentPRFHash2, this.recordStream);
        this.recordStream.enablePendingCipherWrite();
        this.recordStream.enablePendingCipherRead(false);
        completeHandshake();
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b3  */
    @Override // org.bouncycastle.tls.TlsProtocol
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleHandshakeMessage(short s2, HandshakeMessageInput handshakeMessageInput) {
        Vector clientSupplementalData;
        CertificateRequest certificateRequest;
        Certificate certificate;
        TlsCredentialedSigner tlsCredentialedSigner;
        TlsStreamSigner tlsStreamSigner;
        boolean isSSL;
        SecurityParameters securityParameters = this.tlsClientContext.getSecurityParameters();
        if (this.connection_state > 1 && TlsUtils.isTLSv13(securityParameters.getNegotiatedVersion())) {
            handle13HandshakeMessage(s2, handshakeMessageInput);
            return;
        }
        if (!isLegacyConnectionState()) {
            throw new TlsFatalAlert((short) 80);
        }
        if (this.resumedSession) {
            if (s2 != 20 || this.connection_state != 4) {
                throw new TlsFatalAlert((short) 10);
            }
            processFinishedMessage(handshakeMessageInput);
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 20;
            sendChangeCipherSpec();
            sendFinishedMessage();
            this.connection_state = (short) 18;
            completeHandshake();
            return;
        }
        if (s2 == 0) {
            TlsProtocol.assertEmpty(handshakeMessageInput);
            if (isApplicationDataReady()) {
                handleRenegotiation();
                return;
            }
            return;
        }
        if (s2 == 2) {
            if (this.connection_state != 1) {
                throw new TlsFatalAlert((short) 10);
            }
            ServerHello receiveServerHelloMessage = receiveServerHelloMessage(handshakeMessageInput);
            if (receiveServerHelloMessage.isHelloRetryRequest()) {
                process13HelloRetryRequest(receiveServerHelloMessage);
                this.handshakeHash.notifyPRFDetermined();
                TlsUtils.adjustTranscriptForRetry(this.handshakeHash);
                handshakeMessageInput.updateHash(this.handshakeHash);
                this.connection_state = (short) 2;
                send13ClientHelloRetry();
                this.handshakeHash.sealHashAlgorithms();
                this.connection_state = (short) 3;
                return;
            }
            processServerHello(receiveServerHelloMessage);
            this.handshakeHash.notifyPRFDetermined();
            handshakeMessageInput.updateHash(this.handshakeHash);
            this.connection_state = (short) 4;
            if (TlsUtils.isTLSv13(securityParameters.getNegotiatedVersion())) {
                this.handshakeHash.sealHashAlgorithms();
                process13ServerHelloCoda(receiveServerHelloMessage, false);
                return;
            }
            return;
        }
        short s3 = 19;
        TlsStreamSigner tlsStreamSigner2 = null;
        if (s2 != 4) {
            if (s2 == 20) {
                short s4 = this.connection_state;
                if (s4 != 18) {
                    if (s4 != 19) {
                        throw new TlsFatalAlert((short) 10);
                    }
                } else if (this.expectSessionTicket) {
                    throw new TlsFatalAlert((short) 10);
                }
                processFinishedMessage(handshakeMessageInput);
                this.connection_state = (short) 20;
                completeHandshake();
                return;
            }
            s3 = 8;
            if (s2 != 22) {
                if (s2 == 23) {
                    if (this.connection_state != 4) {
                        throw new TlsFatalAlert((short) 10);
                    }
                    handleSupplementalData(TlsProtocol.readSupplementalDataMessage(handshakeMessageInput));
                    return;
                }
                switch (s2) {
                    case 11:
                        short s5 = this.connection_state;
                        if (s5 == 4) {
                            handleSupplementalData(null);
                        } else if (s5 != 6) {
                            throw new TlsFatalAlert((short) 10);
                        }
                        this.authentication = TlsUtils.receiveServerCertificate(this.tlsClientContext, this.tlsClient, handshakeMessageInput);
                        this.connection_state = (short) 7;
                        return;
                    case 12:
                        short s6 = this.connection_state;
                        if (s6 == 4) {
                            handleSupplementalData(null);
                        } else if (s6 != 6) {
                            if (s6 != 7 && s6 != 8) {
                                throw new TlsFatalAlert((short) 10);
                            }
                            handleServerCertificate();
                            this.keyExchange.processServerKeyExchange(handshakeMessageInput);
                            TlsProtocol.assertEmpty(handshakeMessageInput);
                            this.connection_state = (short) 10;
                            return;
                        }
                        this.authentication = null;
                        handleServerCertificate();
                        this.keyExchange.processServerKeyExchange(handshakeMessageInput);
                        TlsProtocol.assertEmpty(handshakeMessageInput);
                        this.connection_state = (short) 10;
                        return;
                    case 13:
                        short s7 = this.connection_state;
                        if (s7 == 7 || s7 == 8) {
                            handleServerCertificate();
                            this.keyExchange.skipServerKeyExchange();
                        } else if (s7 != 10) {
                            throw new TlsFatalAlert((short) 10);
                        }
                        receiveCertificateRequest(handshakeMessageInput);
                        TlsUtils.establishServerSigAlgs(securityParameters, this.certificateRequest);
                        TlsUtils.trackHashAlgorithms(this.handshakeHash, securityParameters.getServerSigAlgs());
                        this.connection_state = (short) 11;
                        return;
                    case 14:
                        short s8 = this.connection_state;
                        if (s8 == 4) {
                            handleSupplementalData(null);
                        } else if (s8 != 6) {
                            if (s8 != 7 && s8 != 8) {
                                if (s8 != 10 && s8 != 11) {
                                    throw new TlsFatalAlert((short) 10);
                                }
                                TlsProtocol.assertEmpty(handshakeMessageInput);
                                this.connection_state = (short) 12;
                                clientSupplementalData = this.tlsClient.getClientSupplementalData();
                                if (clientSupplementalData != null) {
                                    sendSupplementalDataMessage(clientSupplementalData);
                                    this.connection_state = (short) 14;
                                }
                                certificateRequest = this.certificateRequest;
                                if (certificateRequest == null) {
                                    this.keyExchange.skipClientCredentials();
                                    tlsCredentialedSigner = null;
                                } else {
                                    TlsCredentials establishClientCredentials = TlsUtils.establishClientCredentials(this.authentication, certificateRequest);
                                    if (establishClientCredentials == null) {
                                        this.keyExchange.skipClientCredentials();
                                        tlsCredentialedSigner = null;
                                        certificate = null;
                                        tlsStreamSigner = null;
                                    } else {
                                        this.keyExchange.processClientCredentials(establishClientCredentials);
                                        certificate = establishClientCredentials.getCertificate();
                                        if (establishClientCredentials instanceof TlsCredentialedSigner) {
                                            tlsCredentialedSigner = (TlsCredentialedSigner) establishClientCredentials;
                                            tlsStreamSigner = tlsCredentialedSigner.getStreamSigner();
                                        } else {
                                            tlsCredentialedSigner = null;
                                            tlsStreamSigner = null;
                                        }
                                    }
                                    sendCertificateMessage(certificate, null);
                                    this.connection_state = (short) 15;
                                    tlsStreamSigner2 = tlsStreamSigner;
                                }
                                TlsUtils.sealHandshakeHash(this.tlsClientContext, this.handshakeHash, tlsStreamSigner2 != null);
                                sendClientKeyExchange();
                                this.connection_state = (short) 16;
                                isSSL = TlsUtils.isSSL(this.tlsClientContext);
                                if (isSSL) {
                                    TlsProtocol.establishMasterSecret(this.tlsClientContext, this.keyExchange);
                                }
                                securityParameters.sessionHash = TlsUtils.getCurrentPRFHash(this.handshakeHash);
                                if (!isSSL) {
                                    TlsProtocol.establishMasterSecret(this.tlsClientContext, this.keyExchange);
                                }
                                this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsClientContext));
                                if (tlsCredentialedSigner != null) {
                                    sendCertificateVerifyMessage(TlsUtils.generateCertificateVerifyClient(this.tlsClientContext, tlsCredentialedSigner, tlsStreamSigner2, this.handshakeHash));
                                    this.connection_state = (short) 17;
                                }
                                this.handshakeHash = this.handshakeHash.stopTracking();
                                sendChangeCipherSpec();
                                sendFinishedMessage();
                                this.connection_state = (short) 18;
                                return;
                            }
                            handleServerCertificate();
                            this.keyExchange.skipServerKeyExchange();
                            TlsProtocol.assertEmpty(handshakeMessageInput);
                            this.connection_state = (short) 12;
                            clientSupplementalData = this.tlsClient.getClientSupplementalData();
                            if (clientSupplementalData != null) {
                            }
                            certificateRequest = this.certificateRequest;
                            if (certificateRequest == null) {
                            }
                            TlsUtils.sealHandshakeHash(this.tlsClientContext, this.handshakeHash, tlsStreamSigner2 != null);
                            sendClientKeyExchange();
                            this.connection_state = (short) 16;
                            isSSL = TlsUtils.isSSL(this.tlsClientContext);
                            if (isSSL) {
                            }
                            securityParameters.sessionHash = TlsUtils.getCurrentPRFHash(this.handshakeHash);
                            if (!isSSL) {
                            }
                            this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsClientContext));
                            if (tlsCredentialedSigner != null) {
                            }
                            this.handshakeHash = this.handshakeHash.stopTracking();
                            sendChangeCipherSpec();
                            sendFinishedMessage();
                            this.connection_state = (short) 18;
                            return;
                        }
                        this.authentication = null;
                        handleServerCertificate();
                        this.keyExchange.skipServerKeyExchange();
                        TlsProtocol.assertEmpty(handshakeMessageInput);
                        this.connection_state = (short) 12;
                        clientSupplementalData = this.tlsClient.getClientSupplementalData();
                        if (clientSupplementalData != null) {
                        }
                        certificateRequest = this.certificateRequest;
                        if (certificateRequest == null) {
                        }
                        TlsUtils.sealHandshakeHash(this.tlsClientContext, this.handshakeHash, tlsStreamSigner2 != null);
                        sendClientKeyExchange();
                        this.connection_state = (short) 16;
                        isSSL = TlsUtils.isSSL(this.tlsClientContext);
                        if (isSSL) {
                        }
                        securityParameters.sessionHash = TlsUtils.getCurrentPRFHash(this.handshakeHash);
                        if (!isSSL) {
                        }
                        this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsClientContext));
                        if (tlsCredentialedSigner != null) {
                        }
                        this.handshakeHash = this.handshakeHash.stopTracking();
                        sendChangeCipherSpec();
                        sendFinishedMessage();
                        this.connection_state = (short) 18;
                        return;
                    default:
                        throw new TlsFatalAlert((short) 10);
                }
            }
            if (this.connection_state != 7) {
                throw new TlsFatalAlert((short) 10);
            }
            if (securityParameters.getStatusRequestVersion() < 1) {
                throw new TlsFatalAlert((short) 10);
            }
            this.certificateStatus = CertificateStatus.parse(this.tlsClientContext, handshakeMessageInput);
            TlsProtocol.assertEmpty(handshakeMessageInput);
        } else {
            if (this.connection_state != 18) {
                throw new TlsFatalAlert((short) 10);
            }
            if (!this.expectSessionTicket) {
                throw new TlsFatalAlert((short) 10);
            }
            securityParameters.sessionID = TlsUtils.EMPTY_BYTES;
            invalidateSession();
            this.tlsSession = TlsUtils.importSession(securityParameters.getSessionID(), null);
            receiveNewSessionTicket(handshakeMessageInput);
        }
        this.connection_state = s3;
    }

    public void handleServerCertificate() {
        TlsUtils.processServerCertificate(this.tlsClientContext, this.certificateStatus, this.keyExchange, this.authentication, this.clientExtensions, this.serverExtensions);
    }

    public void handleSupplementalData(Vector vector) {
        this.tlsClient.processServerSupplementalData(vector);
        this.connection_state = (short) 6;
        this.keyExchange = TlsUtils.initKeyExchangeClient(this.tlsClientContext, this.tlsClient);
    }

    public void process13HelloRetryRequest(ServerHello serverHello) {
        ProtocolVersion protocolVersion = ProtocolVersion.TLSv12;
        this.recordStream.setWriteVersion(protocolVersion);
        SecurityParameters securityParametersHandshake = this.tlsClientContext.getSecurityParametersHandshake();
        if (securityParametersHandshake.isRenegotiating()) {
            throw new TlsFatalAlert((short) 80);
        }
        ProtocolVersion version = serverHello.getVersion();
        byte[] sessionID = serverHello.getSessionID();
        int cipherSuite = serverHello.getCipherSuite();
        if (!protocolVersion.equals(version) || !Arrays.areEqual(this.clientHello.getSessionID(), sessionID) || !TlsUtils.isValidCipherSuiteSelection(this.clientHello.getCipherSuites(), cipherSuite)) {
            throw new TlsFatalAlert((short) 47);
        }
        Hashtable extensions = serverHello.getExtensions();
        if (extensions == null) {
            throw new TlsFatalAlert((short) 47);
        }
        TlsUtils.checkExtensionData13(extensions, 6, (short) 47);
        Enumeration keys = extensions.keys();
        while (keys.hasMoreElements()) {
            Integer num = (Integer) keys.nextElement();
            if (44 != num.intValue() && TlsUtils.getExtensionData(this.clientExtensions, num) == null) {
                throw new TlsFatalAlert(AlertDescription.unsupported_extension);
            }
        }
        ProtocolVersion supportedVersionsExtensionServer = TlsExtensionsUtils.getSupportedVersionsExtensionServer(extensions);
        if (supportedVersionsExtensionServer == null) {
            throw new TlsFatalAlert(AlertDescription.missing_extension);
        }
        if (!ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(supportedVersionsExtensionServer) || !ProtocolVersion.contains(this.tlsClientContext.getClientSupportedVersions(), supportedVersionsExtensionServer) || !TlsUtils.isValidVersionForCipherSuite(cipherSuite, supportedVersionsExtensionServer)) {
            throw new TlsFatalAlert((short) 47);
        }
        OfferedPsks.BindersConfig bindersConfig = this.clientBinders;
        if (bindersConfig != null && !Arrays.contains(bindersConfig.pskKeyExchangeModes, (short) 1)) {
            this.clientBinders = null;
            this.tlsClient.notifySelectedPSK(null);
        }
        int keyShareHelloRetryRequest = TlsExtensionsUtils.getKeyShareHelloRetryRequest(extensions);
        if (!TlsUtils.isValidKeyShareSelection(supportedVersionsExtensionServer, securityParametersHandshake.getClientSupportedGroups(), this.clientAgreements, keyShareHelloRetryRequest)) {
            throw new TlsFatalAlert((short) 47);
        }
        byte[] cookieExtension = TlsExtensionsUtils.getCookieExtension(extensions);
        securityParametersHandshake.negotiatedVersion = supportedVersionsExtensionServer;
        TlsUtils.negotiatedVersionTLSClient(this.tlsClientContext, this.tlsClient);
        this.resumedSession = false;
        byte[] bArr = TlsUtils.EMPTY_BYTES;
        securityParametersHandshake.sessionID = bArr;
        this.tlsClient.notifySessionID(bArr);
        TlsUtils.negotiatedCipherSuite(securityParametersHandshake, cipherSuite);
        this.tlsClient.notifySelectedCipherSuite(cipherSuite);
        this.clientAgreements = null;
        this.retryCookie = cookieExtension;
        this.retryGroup = keyShareHelloRetryRequest;
    }

    public void process13ServerHello(ServerHello serverHello, boolean z2) {
        TlsSecret tlsSecret;
        TlsPSK tlsPSK;
        TlsSecret calculateSecret;
        SecurityParameters securityParametersHandshake = this.tlsClientContext.getSecurityParametersHandshake();
        ProtocolVersion version = serverHello.getVersion();
        byte[] sessionID = serverHello.getSessionID();
        int cipherSuite = serverHello.getCipherSuite();
        if (!ProtocolVersion.TLSv12.equals(version) || !Arrays.areEqual(this.clientHello.getSessionID(), sessionID)) {
            throw new TlsFatalAlert((short) 47);
        }
        Hashtable extensions = serverHello.getExtensions();
        if (extensions == null) {
            throw new TlsFatalAlert((short) 47);
        }
        TlsUtils.checkExtensionData13(extensions, 2, (short) 47);
        if (z2) {
            ProtocolVersion supportedVersionsExtensionServer = TlsExtensionsUtils.getSupportedVersionsExtensionServer(extensions);
            if (supportedVersionsExtensionServer == null) {
                throw new TlsFatalAlert(AlertDescription.missing_extension);
            }
            if (!securityParametersHandshake.getNegotiatedVersion().equals(supportedVersionsExtensionServer) || securityParametersHandshake.getCipherSuite() != cipherSuite) {
                throw new TlsFatalAlert((short) 47);
            }
        } else {
            if (!TlsUtils.isValidCipherSuiteSelection(this.clientHello.getCipherSuites(), cipherSuite) || !TlsUtils.isValidVersionForCipherSuite(cipherSuite, securityParametersHandshake.getNegotiatedVersion())) {
                throw new TlsFatalAlert((short) 47);
            }
            this.resumedSession = false;
            byte[] bArr = TlsUtils.EMPTY_BYTES;
            securityParametersHandshake.sessionID = bArr;
            this.tlsClient.notifySessionID(bArr);
            TlsUtils.negotiatedCipherSuite(securityParametersHandshake, cipherSuite);
            this.tlsClient.notifySelectedCipherSuite(cipherSuite);
        }
        this.clientHello = null;
        securityParametersHandshake.serverRandom = serverHello.getRandom();
        securityParametersHandshake.secureRenegotiation = false;
        securityParametersHandshake.extendedMasterSecret = true;
        securityParametersHandshake.statusRequestVersion = this.clientExtensions.containsKey(TlsExtensionsUtils.EXT_status_request) ? 1 : 0;
        int preSharedKeyServerHello = TlsExtensionsUtils.getPreSharedKeyServerHello(extensions);
        if (preSharedKeyServerHello >= 0) {
            OfferedPsks.BindersConfig bindersConfig = this.clientBinders;
            if (bindersConfig != null) {
                TlsPSK[] tlsPSKArr = bindersConfig.psks;
                if (preSharedKeyServerHello < tlsPSKArr.length) {
                    tlsPSK = tlsPSKArr[preSharedKeyServerHello];
                    if (tlsPSK.getPRFAlgorithm() != securityParametersHandshake.getPRFAlgorithm()) {
                        throw new TlsFatalAlert((short) 47);
                    }
                    tlsSecret = this.clientBinders.earlySecrets[preSharedKeyServerHello];
                    this.selectedPSK13 = true;
                }
            }
            throw new TlsFatalAlert((short) 47);
        }
        tlsSecret = null;
        tlsPSK = null;
        this.tlsClient.notifySelectedPSK(tlsPSK);
        KeyShareEntry keyShareServerHello = TlsExtensionsUtils.getKeyShareServerHello(extensions);
        if (keyShareServerHello == null) {
            if (z2 || tlsSecret == null || !Arrays.contains(this.clientBinders.pskKeyExchangeModes, (short) 0)) {
                throw new TlsFatalAlert((short) 47);
            }
            calculateSecret = null;
        } else {
            if (tlsSecret != null && !Arrays.contains(this.clientBinders.pskKeyExchangeModes, (short) 1)) {
                throw new TlsFatalAlert((short) 47);
            }
            TlsAgreement tlsAgreement = (TlsAgreement) this.clientAgreements.get(Integers.valueOf(keyShareServerHello.getNamedGroup()));
            if (tlsAgreement == null) {
                throw new TlsFatalAlert((short) 47);
            }
            tlsAgreement.receivePeerValue(keyShareServerHello.getKeyExchange());
            calculateSecret = tlsAgreement.calculateSecret();
        }
        this.clientAgreements = null;
        this.clientBinders = null;
        TlsUtils.establish13PhaseSecrets(this.tlsClientContext, tlsSecret, calculateSecret);
        invalidateSession();
        this.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), null);
    }

    public void process13ServerHelloCoda(ServerHello serverHello, boolean z2) {
        TlsUtils.establish13PhaseHandshake(this.tlsClientContext, TlsUtils.getCurrentPRFHash(this.handshakeHash), this.recordStream);
        if (!z2) {
            this.recordStream.setIgnoreChangeCipherSpec(true);
            sendChangeCipherSpecMessage();
        }
        this.recordStream.enablePendingCipherWrite();
        this.recordStream.enablePendingCipherRead(false);
    }

    public void processServerHello(ServerHello serverHello) {
        TlsSession tlsSession;
        Hashtable extensions = serverHello.getExtensions();
        ProtocolVersion version = serverHello.getVersion();
        ProtocolVersion supportedVersionsExtensionServer = TlsExtensionsUtils.getSupportedVersionsExtensionServer(extensions);
        if (supportedVersionsExtensionServer != null) {
            if (!ProtocolVersion.TLSv12.equals(version) || !ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(supportedVersionsExtensionServer)) {
                throw new TlsFatalAlert((short) 47);
            }
            version = supportedVersionsExtensionServer;
        }
        SecurityParameters securityParametersHandshake = this.tlsClientContext.getSecurityParametersHandshake();
        if (securityParametersHandshake.isRenegotiating()) {
            if (!version.equals(securityParametersHandshake.getNegotiatedVersion())) {
                throw new TlsFatalAlert((short) 47);
            }
        } else {
            if (!ProtocolVersion.contains(this.tlsClientContext.getClientSupportedVersions(), version)) {
                throw new TlsFatalAlert((short) 70);
            }
            ProtocolVersion protocolVersion = ProtocolVersion.TLSv12;
            if (!version.isLaterVersionOf(protocolVersion)) {
                protocolVersion = version;
            }
            this.recordStream.setWriteVersion(protocolVersion);
            securityParametersHandshake.negotiatedVersion = version;
        }
        TlsUtils.negotiatedVersionTLSClient(this.tlsClientContext, this.tlsClient);
        if (ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(version)) {
            process13ServerHello(serverHello, false);
            return;
        }
        int[] cipherSuites = this.clientHello.getCipherSuites();
        this.clientHello = null;
        this.retryCookie = null;
        this.retryGroup = -1;
        securityParametersHandshake.serverRandom = serverHello.getRandom();
        if (!this.tlsClientContext.getClientVersion().equals(version)) {
            TlsUtils.checkDowngradeMarker(version, securityParametersHandshake.getServerRandom());
        }
        byte[] sessionID = serverHello.getSessionID();
        securityParametersHandshake.sessionID = sessionID;
        this.tlsClient.notifySessionID(sessionID);
        this.resumedSession = sessionID.length > 0 && (tlsSession = this.tlsSession) != null && Arrays.areEqual(sessionID, tlsSession.getSessionID());
        int cipherSuite = serverHello.getCipherSuite();
        if (!TlsUtils.isValidCipherSuiteSelection(cipherSuites, cipherSuite) || !TlsUtils.isValidVersionForCipherSuite(cipherSuite, securityParametersHandshake.getNegotiatedVersion())) {
            throw new TlsFatalAlert((short) 47);
        }
        TlsUtils.negotiatedCipherSuite(securityParametersHandshake, cipherSuite);
        this.tlsClient.notifySelectedCipherSuite(cipherSuite);
        this.serverExtensions = extensions;
        if (extensions != null) {
            Enumeration keys = extensions.keys();
            while (keys.hasMoreElements()) {
                Integer num = (Integer) keys.nextElement();
                if (!num.equals(TlsProtocol.EXT_RenegotiationInfo) && TlsUtils.getExtensionData(this.clientExtensions, num) == null) {
                    throw new TlsFatalAlert(AlertDescription.unsupported_extension);
                }
            }
        }
        byte[] extensionData = TlsUtils.getExtensionData(this.serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
        if (securityParametersHandshake.isRenegotiating()) {
            if (!securityParametersHandshake.isSecureRenegotiation()) {
                throw new TlsFatalAlert((short) 80);
            }
            if (extensionData == null) {
                throw new TlsFatalAlert((short) 40);
            }
            SecurityParameters securityParametersConnection = this.tlsClientContext.getSecurityParametersConnection();
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.concat(securityParametersConnection.getLocalVerifyData(), securityParametersConnection.getPeerVerifyData())))) {
                throw new TlsFatalAlert((short) 40);
            }
        } else if (extensionData == null) {
            securityParametersHandshake.secureRenegotiation = false;
        } else {
            securityParametersHandshake.secureRenegotiation = true;
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                throw new TlsFatalAlert((short) 40);
            }
        }
        this.tlsClient.notifySecureRenegotiation(securityParametersHandshake.isSecureRenegotiation());
        boolean hasExtendedMasterSecretExtension = TlsExtensionsUtils.hasExtendedMasterSecretExtension(this.serverExtensions);
        if (hasExtendedMasterSecretExtension) {
            if (version.isSSL() || (!this.resumedSession && !this.tlsClient.shouldUseExtendedMasterSecret())) {
                throw new TlsFatalAlert((short) 40);
            }
        } else if (this.tlsClient.requiresExtendedMasterSecret() || (this.resumedSession && !this.tlsClient.allowLegacyResumption())) {
            throw new TlsFatalAlert((short) 40);
        }
        securityParametersHandshake.extendedMasterSecret = hasExtendedMasterSecretExtension;
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(this.serverExtensions);
        securityParametersHandshake.applicationProtocolSet = true;
        Hashtable hashtable = this.clientExtensions;
        Hashtable hashtable2 = this.serverExtensions;
        if (this.resumedSession) {
            if (securityParametersHandshake.getCipherSuite() != this.sessionParameters.getCipherSuite() || this.sessionParameters.getCompressionAlgorithm() != 0 || !version.equals(this.sessionParameters.getNegotiatedVersion())) {
                throw new TlsFatalAlert((short) 47);
            }
            hashtable2 = this.sessionParameters.readServerExtensions();
            hashtable = null;
        }
        if (hashtable2 != null && !hashtable2.isEmpty()) {
            boolean hasEncryptThenMACExtension = TlsExtensionsUtils.hasEncryptThenMACExtension(hashtable2);
            if (hasEncryptThenMACExtension && !TlsUtils.isBlockCipherSuite(securityParametersHandshake.getCipherSuite())) {
                throw new TlsFatalAlert((short) 47);
            }
            securityParametersHandshake.encryptThenMAC = hasEncryptThenMACExtension;
            securityParametersHandshake.maxFragmentLength = processMaxFragmentLengthExtension(hashtable, hashtable2, (short) 47);
            securityParametersHandshake.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(hashtable2);
            if (!this.resumedSession) {
                if (TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsExtensionsUtils.EXT_status_request_v2, (short) 47)) {
                    securityParametersHandshake.statusRequestVersion = 2;
                } else if (TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsExtensionsUtils.EXT_status_request, (short) 47)) {
                    securityParametersHandshake.statusRequestVersion = 1;
                }
                this.expectSessionTicket = TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsProtocol.EXT_SessionTicket, (short) 47);
            }
        }
        if (hashtable != null) {
            this.tlsClient.processServerExtensions(hashtable2);
        }
        applyMaxFragmentLengthExtension(securityParametersHandshake.getMaxFragmentLength());
        if (this.resumedSession) {
            securityParametersHandshake.masterSecret = this.sessionMasterSecret;
            this.recordStream.setPendingCipher(TlsUtils.initCipher(this.tlsClientContext));
        } else {
            invalidateSession();
            this.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), null);
        }
    }

    public void receive13CertificateRequest(ByteArrayInputStream byteArrayInputStream, boolean z2) {
        if (z2) {
            throw new TlsFatalAlert((short) 80);
        }
        if (this.selectedPSK13) {
            throw new TlsFatalAlert((short) 10);
        }
        CertificateRequest parse = CertificateRequest.parse(this.tlsClientContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        if (!parse.hasCertificateRequestContext(TlsUtils.EMPTY_BYTES)) {
            throw new TlsFatalAlert((short) 47);
        }
        this.certificateRequest = parse;
        TlsUtils.establishServerSigAlgs(this.tlsClientContext.getSecurityParametersHandshake(), parse);
    }

    public void receive13EncryptedExtensions(ByteArrayInputStream byteArrayInputStream) {
        byte[] readOpaque16 = TlsUtils.readOpaque16(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        Hashtable readExtensionsData13 = TlsProtocol.readExtensionsData13(8, readOpaque16);
        this.serverExtensions = readExtensionsData13;
        Enumeration keys = readExtensionsData13.keys();
        while (keys.hasMoreElements()) {
            if (TlsUtils.getExtensionData(this.clientExtensions, (Integer) keys.nextElement()) == null) {
                throw new TlsFatalAlert(AlertDescription.unsupported_extension);
            }
        }
        SecurityParameters securityParametersHandshake = this.tlsClientContext.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        securityParametersHandshake.applicationProtocol = TlsExtensionsUtils.getALPNExtensionServer(this.serverExtensions);
        securityParametersHandshake.applicationProtocolSet = true;
        Hashtable hashtable = this.clientExtensions;
        Hashtable hashtable2 = this.serverExtensions;
        if (this.resumedSession) {
            if (securityParametersHandshake.getCipherSuite() != this.sessionParameters.getCipherSuite() || this.sessionParameters.getCompressionAlgorithm() != 0 || !negotiatedVersion.equals(this.sessionParameters.getNegotiatedVersion())) {
                throw new TlsFatalAlert((short) 47);
            }
            hashtable2 = this.sessionParameters.readServerExtensions();
            hashtable = null;
        }
        securityParametersHandshake.maxFragmentLength = processMaxFragmentLengthExtension(hashtable, hashtable2, (short) 47);
        securityParametersHandshake.encryptThenMAC = false;
        securityParametersHandshake.truncatedHMac = false;
        securityParametersHandshake.statusRequestVersion = this.clientExtensions.containsKey(TlsExtensionsUtils.EXT_status_request) ? 1 : 0;
        this.expectSessionTicket = false;
        if (hashtable != null) {
            this.tlsClient.processServerExtensions(this.serverExtensions);
        }
        applyMaxFragmentLengthExtension(securityParametersHandshake.getMaxFragmentLength());
    }

    public void receive13NewSessionTicket(ByteArrayInputStream byteArrayInputStream) {
        if (!isApplicationDataReady()) {
            throw new TlsFatalAlert((short) 10);
        }
        TlsUtils.readUint32(byteArrayInputStream);
        TlsUtils.readUint32(byteArrayInputStream);
        TlsUtils.readOpaque8(byteArrayInputStream);
        TlsUtils.readOpaque16(byteArrayInputStream);
        TlsUtils.readOpaque16(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    public void receive13ServerCertificate(ByteArrayInputStream byteArrayInputStream) {
        if (this.selectedPSK13) {
            throw new TlsFatalAlert((short) 10);
        }
        this.authentication = TlsUtils.receive13ServerCertificate(this.tlsClientContext, this.tlsClient, byteArrayInputStream);
        handleServerCertificate();
    }

    public void receive13ServerCertificateVerify(ByteArrayInputStream byteArrayInputStream) {
        Certificate peerCertificate = this.tlsClientContext.getSecurityParametersHandshake().getPeerCertificate();
        if (peerCertificate == null || peerCertificate.isEmpty()) {
            throw new TlsFatalAlert((short) 80);
        }
        DigitallySigned parse = DigitallySigned.parse(this.tlsClientContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        TlsUtils.verify13CertificateVerifyServer(this.tlsClientContext, parse, this.handshakeHash);
    }

    public void receive13ServerFinished(ByteArrayInputStream byteArrayInputStream) {
        process13FinishedMessage(byteArrayInputStream);
    }

    public void receiveCertificateRequest(ByteArrayInputStream byteArrayInputStream) {
        if (this.authentication == null) {
            throw new TlsFatalAlert((short) 40);
        }
        CertificateRequest parse = CertificateRequest.parse(this.tlsClientContext, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        this.certificateRequest = TlsUtils.validateCertificateRequest(parse, this.keyExchange);
    }

    public void receiveNewSessionTicket(ByteArrayInputStream byteArrayInputStream) {
        NewSessionTicket parse = NewSessionTicket.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        this.tlsClient.notifyNewSessionTicket(parse);
    }

    public ServerHello receiveServerHelloMessage(ByteArrayInputStream byteArrayInputStream) {
        return ServerHello.parse(byteArrayInputStream);
    }

    public void send13ClientHelloRetry() {
        Hashtable extensions = this.clientHello.getExtensions();
        extensions.remove(TlsExtensionsUtils.EXT_cookie);
        extensions.remove(TlsExtensionsUtils.EXT_early_data);
        extensions.remove(TlsExtensionsUtils.EXT_key_share);
        extensions.remove(TlsExtensionsUtils.EXT_pre_shared_key);
        byte[] bArr = this.retryCookie;
        if (bArr != null) {
            TlsExtensionsUtils.addCookieExtension(extensions, bArr);
            this.retryCookie = null;
        }
        OfferedPsks.BindersConfig bindersConfig = this.clientBinders;
        if (bindersConfig != null) {
            OfferedPsks.BindersConfig addPreSharedKeyToClientHelloRetry = TlsUtils.addPreSharedKeyToClientHelloRetry(this.tlsClientContext, bindersConfig, extensions);
            this.clientBinders = addPreSharedKeyToClientHelloRetry;
            if (addPreSharedKeyToClientHelloRetry == null) {
                this.tlsClient.notifySelectedPSK(null);
            }
        }
        int i2 = this.retryGroup;
        if (i2 < 0) {
            throw new TlsFatalAlert((short) 80);
        }
        this.clientAgreements = TlsUtils.addKeyShareToClientHelloRetry(this.tlsClientContext, extensions, i2);
        this.recordStream.setIgnoreChangeCipherSpec(true);
        sendChangeCipherSpecMessage();
        sendClientHelloMessage();
    }

    public void sendCertificateVerifyMessage(DigitallySigned digitallySigned) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 15);
        digitallySigned.encode(handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void sendClientHello() {
        ProtocolVersion[] protocolVersions;
        ProtocolVersion earliestTLS;
        ProtocolVersion latestTLS;
        byte[] bArr;
        ProtocolVersion protocolVersion;
        SessionParameters sessionParameters;
        SessionParameters sessionParameters2;
        SecurityParameters securityParametersHandshake = this.tlsClientContext.getSecurityParametersHandshake();
        if (securityParametersHandshake.isRenegotiating()) {
            earliestTLS = this.tlsClientContext.getClientVersion();
            protocolVersions = earliestTLS.only();
            latestTLS = earliestTLS;
        } else {
            protocolVersions = this.tlsClient.getProtocolVersions();
            ProtocolVersion protocolVersion2 = ProtocolVersion.SSLv3;
            if (ProtocolVersion.contains(protocolVersions, protocolVersion2)) {
                this.recordStream.setWriteVersion(protocolVersion2);
            } else {
                this.recordStream.setWriteVersion(ProtocolVersion.TLSv10);
            }
            earliestTLS = ProtocolVersion.getEarliestTLS(protocolVersions);
            latestTLS = ProtocolVersion.getLatestTLS(protocolVersions);
            if (!ProtocolVersion.isSupportedTLSVersionClient(latestTLS)) {
                throw new TlsFatalAlert((short) 80);
            }
            this.tlsClientContext.setClientVersion(latestTLS);
        }
        this.tlsClientContext.setClientSupportedVersions(protocolVersions);
        ProtocolVersion protocolVersion3 = ProtocolVersion.TLSv12;
        boolean isEqualOrLaterVersionOf = protocolVersion3.isEqualOrLaterVersionOf(earliestTLS);
        boolean isEqualOrEarlierVersionOf = ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(latestTLS);
        establishSession(isEqualOrLaterVersionOf ? this.tlsClient.getSessionToResume() : null);
        this.tlsClient.notifySessionToResume(this.tlsSession);
        byte[] sessionID = TlsUtils.getSessionID(this.tlsSession);
        boolean isFallback = this.tlsClient.isFallback();
        int[] cipherSuites = this.tlsClient.getCipherSuites();
        if (sessionID.length > 0 && (sessionParameters2 = this.sessionParameters) != null && (!Arrays.contains(cipherSuites, sessionParameters2.getCipherSuite()) || this.sessionParameters.getCompressionAlgorithm() != 0)) {
            sessionID = TlsUtils.EMPTY_BYTES;
        }
        Hashtable ensureExtensionsInitialised = TlsExtensionsUtils.ensureExtensionsInitialised(this.tlsClient.getClientExtensions());
        this.clientExtensions = ensureExtensionsInitialised;
        if (isEqualOrEarlierVersionOf) {
            TlsExtensionsUtils.addSupportedVersionsExtensionClient(ensureExtensionsInitialised, protocolVersions);
            if (sessionID.length < 1) {
                sessionID = this.tlsClientContext.getNonceGenerator().generateNonce(32);
            }
            bArr = sessionID;
            protocolVersion = protocolVersion3;
        } else {
            bArr = sessionID;
            protocolVersion = latestTLS;
        }
        this.tlsClientContext.setRSAPreMasterSecretVersion(protocolVersion);
        securityParametersHandshake.clientServerNames = TlsExtensionsUtils.getServerNameExtensionClient(this.clientExtensions);
        if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(latestTLS)) {
            TlsUtils.establishClientSigAlgs(securityParametersHandshake, this.clientExtensions);
        }
        securityParametersHandshake.clientSupportedGroups = TlsExtensionsUtils.getSupportedGroupsExtension(this.clientExtensions);
        this.clientBinders = TlsUtils.addPreSharedKeyToClientHello(this.tlsClientContext, this.tlsClient, this.clientExtensions, cipherSuites);
        this.clientAgreements = TlsUtils.addKeyShareToClientHello(this.tlsClientContext, this.tlsClient, this.clientExtensions);
        if (TlsUtils.isExtendedMasterSecretOptionalTLS(protocolVersions) && (this.tlsClient.shouldUseExtendedMasterSecret() || ((sessionParameters = this.sessionParameters) != null && sessionParameters.isExtendedMasterSecret()))) {
            TlsExtensionsUtils.addExtendedMasterSecretExtension(this.clientExtensions);
        } else if (!isEqualOrEarlierVersionOf && this.tlsClient.requiresExtendedMasterSecret()) {
            throw new TlsFatalAlert((short) 80);
        }
        securityParametersHandshake.clientRandom = TlsProtocol.createRandomBlock(!isEqualOrEarlierVersionOf && this.tlsClient.shouldUseGMTUnixTime(), this.tlsClientContext);
        if (!securityParametersHandshake.isRenegotiating()) {
            boolean z2 = TlsUtils.getExtensionData(this.clientExtensions, TlsProtocol.EXT_RenegotiationInfo) == null;
            boolean z3 = !Arrays.contains(cipherSuites, 255);
            if (z2 && z3) {
                cipherSuites = Arrays.append(cipherSuites, 255);
            }
        } else {
            if (!securityParametersHandshake.isSecureRenegotiation()) {
                throw new TlsFatalAlert((short) 80);
            }
            this.clientExtensions.put(TlsProtocol.EXT_RenegotiationInfo, TlsProtocol.createRenegotiationInfo(this.tlsClientContext.getSecurityParametersConnection().getLocalVerifyData()));
        }
        int[] append = (!isFallback || Arrays.contains(cipherSuites, CipherSuite.TLS_FALLBACK_SCSV)) ? cipherSuites : Arrays.append(cipherSuites, CipherSuite.TLS_FALLBACK_SCSV);
        OfferedPsks.BindersConfig bindersConfig = this.clientBinders;
        this.clientHello = new ClientHello(protocolVersion, securityParametersHandshake.getClientRandom(), bArr, null, append, this.clientExtensions, bindersConfig != null ? bindersConfig.bindersSize : 0);
        sendClientHelloMessage();
    }

    public void sendClientHelloMessage() {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 1);
        this.clientHello.encode(this.tlsClientContext, handshakeMessageOutput);
        handshakeMessageOutput.prepareClientHello(this.handshakeHash, this.clientHello.getBindersSize());
        if (this.clientBinders != null) {
            OfferedPsks.encodeBinders(handshakeMessageOutput, this.tlsClientContext.getCrypto(), this.handshakeHash, this.clientBinders);
        }
        handshakeMessageOutput.sendClientHello(this, this.handshakeHash, this.clientHello.getBindersSize());
    }

    public void sendClientKeyExchange() {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 16);
        this.keyExchange.generateClientKeyExchange(handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void skip13CertificateRequest() {
        this.certificateRequest = null;
    }

    public void skip13ServerCertificate() {
        if (!this.selectedPSK13) {
            throw new TlsFatalAlert((short) 10);
        }
        this.authentication = TlsUtils.skip13ServerCertificate(this.tlsClientContext);
    }

    public TlsClientProtocol(InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
        this.tlsClient = null;
        this.tlsClientContext = null;
        this.clientAgreements = null;
        this.clientBinders = null;
        this.clientHello = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }
}
