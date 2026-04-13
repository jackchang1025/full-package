package org.bouncycastle.jsse.provider;

import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jsse.BCSNIMatcher;
import org.bouncycastle.jsse.BCSNIServerName;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.tls.AlertDescription;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.CertificateRequest;
import org.bouncycastle.tls.CertificateStatus;
import org.bouncycastle.tls.DefaultTlsServer;
import org.bouncycastle.tls.ProtocolName;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.SessionParameters;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.TlsCredentials;
import org.bouncycastle.tls.TlsDHUtils;
import org.bouncycastle.tls.TlsExtensionsUtils;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsSession;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.DHGroup;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
class ProvTlsServer extends DefaultTlsServer implements ProvTlsPeer {
    private static final String PROPERTY_DEFAULT_DHE_PARAMETERS = "jdk.tls.server.defaultDHEParameters";
    private static final boolean provServerEnableStatusRequest = false;
    protected TlsCredentials credentials;
    protected boolean handshakeComplete;
    protected final JsseSecurityParameters jsseSecurityParameters;
    protected Set<String> keyManagerMissCache;
    protected final ProvTlsManager manager;
    protected BCSNIServerName matchedSNIServerName;
    protected final ProvSSLParameters sslParameters;
    protected ProvSSLSession sslSession;
    private static final Logger LOG = Logger.getLogger(ProvTlsServer.class.getName());
    private static final int provEphemeralDHKeySize = PropertyUtils.getIntegerSystemProperty("jdk.tls.ephemeralDHKeySize", 2048, 1024, 8192);
    private static final DHGroup[] provServerDefaultDHEParameters = getDefaultDHEParameters();
    private static final boolean provServerEnableCA = PropertyUtils.getBooleanSystemProperty("jdk.tls.server.enableCAExtension", true);
    private static final boolean provServerEnableSessionResumption = PropertyUtils.getBooleanSystemProperty("org.bouncycastle.jsse.server.enableSessionResumption", true);
    private static final boolean provServerEnableTrustedCAKeys = PropertyUtils.getBooleanSystemProperty("org.bouncycastle.jsse.server.enableTrustedCAKeysExtension", false);

    public ProvTlsServer(ProvTlsManager provTlsManager, ProvSSLParameters provSSLParameters) {
        super(provTlsManager.getContextData().getCrypto());
        this.jsseSecurityParameters = new JsseSecurityParameters();
        this.sslSession = null;
        this.matchedSNIServerName = null;
        this.keyManagerMissCache = null;
        this.credentials = null;
        this.handshakeComplete = false;
        this.manager = provTlsManager;
        this.sslParameters = provSSLParameters.copyForConnection();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0087 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static DHGroup[] getDefaultDHEParameters() {
        String stripDoubleQuotes;
        int length;
        int i2;
        int indexOf;
        int i3;
        int indexOf2;
        String stringSecurityProperty = PropertyUtils.getStringSecurityProperty(PROPERTY_DEFAULT_DHE_PARAMETERS);
        if (stringSecurityProperty == null || (length = (stripDoubleQuotes = JsseUtils.stripDoubleQuotes(JsseUtils.removeAllWhitespace(stringSecurityProperty))).length()) < 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i4 = -1;
        do {
            int i5 = i4 + 1;
            if (i5 >= length || '{' != stripDoubleQuotes.charAt(i5) || (indexOf = stripDoubleQuotes.indexOf(44, (i2 = i5 + 1))) <= i2 || (indexOf2 = stripDoubleQuotes.indexOf(125, (i3 = indexOf + 1))) <= i3) {
                break;
            }
            try {
                BigInteger parseDHParameter = parseDHParameter(stripDoubleQuotes, i2, indexOf);
                BigInteger parseDHParameter2 = parseDHParameter(stripDoubleQuotes, i3, indexOf2);
                DHGroup standardGroupForDHParameters = TlsDHUtils.getStandardGroupForDHParameters(parseDHParameter, parseDHParameter2);
                if (standardGroupForDHParameters == null) {
                    if (parseDHParameter.isProbablePrime(120)) {
                        standardGroupForDHParameters = new DHGroup(parseDHParameter, null, parseDHParameter2, 0);
                    } else {
                        LOG.log(Level.WARNING, "Non-prime modulus ignored in security property [jdk.tls.server.defaultDHEParameters]: " + parseDHParameter.toString(16));
                        i4 = indexOf2 + 1;
                        if (i4 < length) {
                            return (DHGroup[]) arrayList.toArray(new DHGroup[arrayList.size()]);
                        }
                    }
                }
                arrayList.add(standardGroupForDHParameters);
                i4 = indexOf2 + 1;
                if (i4 < length) {
                }
            } catch (Exception unused) {
            }
        } while (',' == stripDoubleQuotes.charAt(i4));
        LOG.log(Level.WARNING, "Invalid syntax for security property [jdk.tls.server.defaultDHEParameters]");
        return null;
    }

    private void handleKeyManagerMisses(LinkedHashMap<String, SignatureSchemeInfo> linkedHashMap, String str) {
        for (Map.Entry<String, SignatureSchemeInfo> entry : linkedHashMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals(str)) {
                return;
            }
            this.keyManagerMissCache.add(key);
            Logger logger = LOG;
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("Server found no credentials for signature scheme '" + entry.getValue() + "' (keyType '" + key + "')");
            }
        }
    }

    private static BigInteger parseDHParameter(String str, int i2, int i3) {
        return new BigInteger(str.substring(i2, i3), 16);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean allowCertificateStatus() {
        return false;
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public boolean allowLegacyResumption() {
        return JsseUtils.allowLegacyResumption();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean allowMultiCertStatus() {
        return false;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean allowTrustedCAIndication() {
        return this.jsseSecurityParameters.trustedIssuers != null;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public CertificateRequest getCertificateRequest() {
        if (!isClientAuthEnabled()) {
            return null;
        }
        ContextData contextData = this.manager.getContextData();
        ProtocolVersion serverVersion = this.context.getServerVersion();
        List<SignatureSchemeInfo> activeCertsSignatureSchemes = contextData.getActiveCertsSignatureSchemes(true, this.sslParameters, new ProtocolVersion[]{serverVersion}, this.jsseSecurityParameters.namedGroups);
        JsseSecurityParameters jsseSecurityParameters = this.jsseSecurityParameters;
        jsseSecurityParameters.localSigSchemes = activeCertsSignatureSchemes;
        jsseSecurityParameters.localSigSchemesCert = activeCertsSignatureSchemes;
        Vector<SignatureAndHashAlgorithm> signatureAndHashAlgorithms = SignatureSchemeInfo.getSignatureAndHashAlgorithms(activeCertsSignatureSchemes);
        Vector<X500Name> certificateAuthorities = provServerEnableCA ? JsseUtils.getCertificateAuthorities(contextData.getX509TrustManager()) : null;
        if (!TlsUtils.isTLSv13(serverVersion)) {
            return new CertificateRequest(new short[]{64, 1, 2}, signatureAndHashAlgorithms, certificateAuthorities);
        }
        byte[] bArr = TlsUtils.EMPTY_BYTES;
        JsseSecurityParameters jsseSecurityParameters2 = this.jsseSecurityParameters;
        List<SignatureSchemeInfo> list = jsseSecurityParameters2.localSigSchemes;
        List<SignatureSchemeInfo> list2 = jsseSecurityParameters2.localSigSchemesCert;
        return new CertificateRequest(bArr, signatureAndHashAlgorithms, list != list2 ? SignatureSchemeInfo.getSignatureAndHashAlgorithms(list2) : null, certificateAuthorities);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public CertificateStatus getCertificateStatus() {
        return null;
    }

    @Override // org.bouncycastle.tls.DefaultTlsServer, org.bouncycastle.tls.TlsServer
    public TlsCredentials getCredentials() {
        return this.credentials;
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public int getMaxCertificateChainLength() {
        return JsseUtils.getMaxCertificateChainLength();
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public int getMaxHandshakeMessageSize() {
        return JsseUtils.getMaxHandshakeMessageSize();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int getMaximumNegotiableCurveBits() {
        return NamedGroupInfo.getMaximumBitsServerECDH(this.jsseSecurityParameters.namedGroups);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int getMaximumNegotiableFiniteFieldBits() {
        int maximumBitsServerFFDHE = NamedGroupInfo.getMaximumBitsServerFFDHE(this.jsseSecurityParameters.namedGroups);
        if (maximumBitsServerFFDHE >= provEphemeralDHKeySize) {
            return maximumBitsServerFFDHE;
        }
        return 0;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public byte[] getNewSessionID() {
        if (!provServerEnableSessionResumption || TlsUtils.isTLSv13(this.context)) {
            return null;
        }
        return this.context.getNonceGenerator().generateNonce(32);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public Vector<ProtocolName> getProtocolNames() {
        return JsseUtils.getProtocolNames(this.sslParameters.getApplicationProtocols());
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public int getSelectedCipherSuite() {
        ContextData contextData = this.manager.getContextData();
        SecurityParameters securityParametersHandshake = this.context.getSecurityParametersHandshake();
        NamedGroupInfo.notifyPeer(this.jsseSecurityParameters.namedGroups, securityParametersHandshake.getClientSupportedGroups());
        Vector clientSigAlgs = securityParametersHandshake.getClientSigAlgs();
        Vector clientSigAlgsCert = securityParametersHandshake.getClientSigAlgsCert();
        this.jsseSecurityParameters.peerSigSchemes = contextData.getSignatureSchemes(clientSigAlgs);
        JsseSecurityParameters jsseSecurityParameters = this.jsseSecurityParameters;
        jsseSecurityParameters.peerSigSchemesCert = clientSigAlgs == clientSigAlgsCert ? jsseSecurityParameters.peerSigSchemes : contextData.getSignatureSchemes(clientSigAlgsCert);
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINEST)) {
            logger.finest(JsseUtils.getSignatureAlgorithmsReport("Peer signature_algorithms", this.jsseSecurityParameters.peerSigSchemes));
            JsseSecurityParameters jsseSecurityParameters2 = this.jsseSecurityParameters;
            List<SignatureSchemeInfo> list = jsseSecurityParameters2.peerSigSchemesCert;
            if (list != jsseSecurityParameters2.peerSigSchemes) {
                logger.finest(JsseUtils.getSignatureAlgorithmsReport("Peer signature_algorithms_cert", list));
            }
        }
        if (DummyX509KeyManager.INSTANCE == contextData.getX509KeyManager()) {
            throw new TlsFatalAlert((short) 40);
        }
        this.keyManagerMissCache = new HashSet();
        int selectedCipherSuite = super.getSelectedCipherSuite();
        this.keyManagerMissCache = null;
        logger.fine("Server selected cipher suite: " + this.manager.getContextData().getContext().validateNegotiatedCipherSuite(this.sslParameters, selectedCipherSuite));
        return selectedCipherSuite;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public Hashtable<Integer, byte[]> getServerExtensions() {
        super.getServerExtensions();
        if (this.matchedSNIServerName != null) {
            TlsExtensionsUtils.addServerNameExtensionServer(this.serverExtensions);
        }
        return this.serverExtensions;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public ProtocolVersion getServerVersion() {
        ProtocolVersion serverVersion = super.getServerVersion();
        String validateNegotiatedProtocol = this.manager.getContextData().getContext().validateNegotiatedProtocol(this.sslParameters, serverVersion);
        LOG.fine("Server selected protocol version: " + validateNegotiatedProtocol);
        return serverVersion;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public TlsSession getSessionToResume(byte[] bArr) {
        ProvSSLSession sessionImpl;
        ProvSSLSessionContext serverSessionContext = this.manager.getContextData().getServerSessionContext();
        if (provServerEnableSessionResumption && (sessionImpl = serverSessionContext.getSessionImpl(bArr)) != null) {
            TlsSession tlsSession = sessionImpl.getTlsSession();
            if (isResumable(sessionImpl, tlsSession)) {
                this.sslSession = sessionImpl;
                return tlsSession;
            }
        }
        JsseUtils.checkSessionCreationEnabled(this.manager);
        return null;
    }

    @Override // org.bouncycastle.tls.DefaultTlsServer, org.bouncycastle.tls.AbstractTlsPeer
    public int[] getSupportedCipherSuites() {
        return this.manager.getContextData().getContext().getActiveCipherSuites(getCrypto(), this.sslParameters, getProtocolVersions());
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public int[] getSupportedGroups() {
        this.jsseSecurityParameters.namedGroups = this.manager.getContextData().getNamedGroups(this.sslParameters, new ProtocolVersion[]{this.context.getServerVersion()});
        return NamedGroupInfo.getSupportedGroupsLocalServer(this.jsseSecurityParameters.namedGroups);
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer
    public ProtocolVersion[] getSupportedVersions() {
        return this.manager.getContextData().getContext().getActiveProtocolVersions(this.sslParameters);
    }

    public boolean isClientAuthEnabled() {
        return this.sslParameters.getNeedClientAuth() || this.sslParameters.getWantClientAuth();
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsPeer
    public synchronized boolean isHandshakeComplete() {
        return this.handshakeComplete;
    }

    public boolean isResumable(ProvSSLSession provSSLSession, TlsSession tlsSession) {
        SessionParameters exportSessionParameters;
        if (tlsSession != null && tlsSession.isResumable()) {
            ProtocolVersion negotiatedVersion = this.context.getSecurityParametersHandshake().getNegotiatedVersion();
            if (TlsUtils.isTLSv13(negotiatedVersion) || (exportSessionParameters = tlsSession.exportSessionParameters()) == null || !negotiatedVersion.equals(exportSessionParameters.getNegotiatedVersion()) || !Arrays.contains(getCipherSuites(), exportSessionParameters.getCipherSuite()) || !Arrays.contains(this.offeredCipherSuites, exportSessionParameters.getCipherSuite()) || !exportSessionParameters.isExtendedMasterSecret()) {
                return false;
            }
            JsseSessionParameters jsseSessionParameters = provSSLSession.getJsseSessionParameters();
            BCSNIServerName bCSNIServerName = this.matchedSNIServerName;
            BCSNIServerName matchedSNIServerName = jsseSessionParameters.getMatchedSNIServerName();
            if (JsseUtils.equals(bCSNIServerName, matchedSNIServerName)) {
                return true;
            }
            LOG.finest("Session not resumable - SNI mismatch; connection: " + bCSNIServerName + ", session: " + matchedSNIServerName);
            return false;
        }
        return false;
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public void notifyAlertRaised(short s2, short s3, String str, Throwable th) {
        Level level = s2 == 1 ? Level.FINE : s3 == 80 ? Level.WARNING : Level.INFO;
        Logger logger = LOG;
        if (logger.isLoggable(level)) {
            String alertLogMessage = JsseUtils.getAlertLogMessage("Server raised", s2, s3);
            if (str != null) {
                alertLogMessage = alertLogMessage + ": " + str;
            }
            logger.log(level, alertLogMessage, th);
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public void notifyAlertReceived(short s2, short s3) {
        super.notifyAlertReceived(s2, s3);
        Level level = s2 == 1 ? Level.FINE : Level.INFO;
        Logger logger = LOG;
        if (logger.isLoggable(level)) {
            logger.log(level, JsseUtils.getAlertLogMessage("Server received", s2, s3));
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public void notifyClientCertificate(Certificate certificate) {
        if (!isClientAuthEnabled()) {
            throw new TlsFatalAlert((short) 80);
        }
        if (certificate == null || certificate.isEmpty()) {
            if (this.sslParameters.getNeedClientAuth()) {
                throw new TlsFatalAlert(TlsUtils.isTLSv13(this.context) ? AlertDescription.certificate_required : (short) 40);
            }
            return;
        }
        X509Certificate[] x509CertificateChain = JsseUtils.getX509CertificateChain(getCrypto(), certificate);
        TlsCertificate certificateAt = certificate.getCertificateAt(0);
        short s2 = 7;
        if (!certificateAt.supportsSignatureAlgorithm((short) 7)) {
            s2 = 8;
            if (!certificateAt.supportsSignatureAlgorithm((short) 8)) {
                s2 = certificateAt.getLegacySignatureAlgorithm();
            }
        }
        if (s2 < 0) {
            throw new TlsFatalAlert((short) 43);
        }
        this.manager.checkClientTrusted(x509CertificateChain, JsseUtils.getAuthTypeClient(s2));
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public synchronized void notifyHandshakeComplete() {
        super.notifyHandshakeComplete();
        boolean z2 = true;
        this.handshakeComplete = true;
        TlsSession session = this.context.getSession();
        ProvSSLSession provSSLSession = this.sslSession;
        if (provSSLSession == null || provSSLSession.getTlsSession() != session) {
            ProvSSLSessionContext serverSessionContext = this.manager.getContextData().getServerSessionContext();
            String peerHost = this.manager.getPeerHost();
            int peerPort = this.manager.getPeerPort();
            JsseSessionParameters jsseSessionParameters = new JsseSessionParameters(null, this.matchedSNIServerName);
            if (!provServerEnableSessionResumption || TlsUtils.isTLSv13(this.context) || !this.context.getSecurityParametersConnection().isExtendedMasterSecret()) {
                z2 = false;
            }
            this.sslSession = serverSessionContext.reportSession(peerHost, peerPort, session, jsseSessionParameters, z2);
        }
        this.manager.notifyHandshakeComplete(new ProvSSLConnection(this.context, this.sslSession));
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public void notifySecureRenegotiation(boolean z2) {
        if (!z2 && !PropertyUtils.getBooleanSystemProperty("sun.security.ssl.allowLegacyHelloMessages", true)) {
            throw new TlsFatalAlert((short) 40);
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public void notifySession(TlsSession tlsSession) {
        Logger logger;
        String str;
        byte[] sessionID = tlsSession.getSessionID();
        ProvSSLSession provSSLSession = this.sslSession;
        if (provSSLSession != null && provSSLSession.getTlsSession() == tlsSession) {
            LOG.fine("Server resumed session: " + Hex.toHexString(sessionID));
        } else {
            this.sslSession = null;
            if (TlsUtils.isNullOrEmpty(sessionID)) {
                logger = LOG;
                str = "Server did not specify a session ID";
            } else {
                logger = LOG;
                str = "Server specified new session: " + Hex.toHexString(sessionID);
            }
            logger.fine(str);
            JsseUtils.checkSessionCreationEnabled(this.manager);
        }
        ProvTlsManager provTlsManager = this.manager;
        provTlsManager.notifyHandshakeSession(provTlsManager.getContextData().getServerSessionContext(), this.context.getSecurityParametersHandshake(), this.jsseSecurityParameters, this.sslSession);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean preferLocalCipherSuites() {
        return this.sslParameters.getUseCipherSuitesOrder();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer, org.bouncycastle.tls.TlsServer
    public void processClientExtensions(Hashtable hashtable) {
        Logger logger;
        String str;
        super.processClientExtensions(hashtable);
        Vector clientServerNames = this.context.getSecurityParametersHandshake().getClientServerNames();
        if (clientServerNames != null) {
            Collection<BCSNIMatcher> sNIMatchers = this.sslParameters.getSNIMatchers();
            if (sNIMatchers == null || sNIMatchers.isEmpty()) {
                logger = LOG;
                str = "Server ignored SNI (no matchers specified)";
            } else {
                BCSNIServerName findMatchingSNIServerName = JsseUtils.findMatchingSNIServerName(clientServerNames, sNIMatchers);
                this.matchedSNIServerName = findMatchingSNIServerName;
                if (findMatchingSNIServerName == null) {
                    throw new TlsFatalAlert(AlertDescription.unrecognized_name);
                }
                logger = LOG;
                str = "Server accepted SNI: " + this.matchedSNIServerName;
            }
            logger.fine(str);
        }
        if (TlsUtils.isTLSv13(this.context)) {
            this.jsseSecurityParameters.trustedIssuers = JsseUtils.toX500Principals(TlsExtensionsUtils.getCertificateAuthoritiesExtension(hashtable));
        } else if (provServerEnableTrustedCAKeys) {
            this.jsseSecurityParameters.trustedIssuers = JsseUtils.getTrustedIssuers(this.trustedCAKeys);
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public boolean requiresCloseNotify() {
        return JsseUtils.requireCloseNotify();
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public boolean requiresExtendedMasterSecret() {
        return !JsseUtils.allowLegacyMasterSecret();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean selectCipherSuite(int i2) {
        TlsCredentials selectCredentials = selectCredentials(this.jsseSecurityParameters.trustedIssuers, i2);
        if (selectCredentials != null) {
            boolean selectCipherSuite = super.selectCipherSuite(i2);
            if (selectCipherSuite) {
                this.credentials = selectCredentials;
            }
            return selectCipherSuite;
        }
        String cipherSuiteName = ProvSSLContextSpi.getCipherSuiteName(i2);
        LOG.finer("Server found no credentials for cipher suite: " + cipherSuiteName);
        return false;
    }

    public TlsCredentials selectCredentials(Principal[] principalArr, int i2) {
        int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(i2);
        if (keyExchangeAlgorithm == 0) {
            return selectServerCredentials13(principalArr, TlsUtils.EMPTY_BYTES);
        }
        if (keyExchangeAlgorithm == 1 || keyExchangeAlgorithm == 3 || keyExchangeAlgorithm == 5 || keyExchangeAlgorithm == 17 || keyExchangeAlgorithm == 19) {
            return (1 == keyExchangeAlgorithm || !TlsUtils.isSignatureAlgorithmsExtensionAllowed(this.context.getServerVersion())) ? selectServerCredentialsLegacy(principalArr, keyExchangeAlgorithm) : selectServerCredentials12(principalArr, keyExchangeAlgorithm);
        }
        return null;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int selectDH(int i2) {
        return NamedGroupInfo.selectServerFFDHE(this.jsseSecurityParameters.namedGroups, Math.max(i2, provEphemeralDHKeySize));
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int selectDHDefault(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int selectECDH(int i2) {
        return NamedGroupInfo.selectServerECDH(this.jsseSecurityParameters.namedGroups, i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public int selectECDHDefault(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public ProtocolName selectProtocolName() {
        if (this.sslParameters.getEngineAPSelector() == null && this.sslParameters.getSocketAPSelector() == null) {
            return super.selectProtocolName();
        }
        List<String> protocolNames = JsseUtils.getProtocolNames((Vector<ProtocolName>) this.clientProtocolNames);
        String selectApplicationProtocol = this.manager.selectApplicationProtocol(Collections.unmodifiableList(protocolNames));
        if (selectApplicationProtocol == null) {
            throw new TlsFatalAlert(AlertDescription.no_application_protocol);
        }
        if (selectApplicationProtocol.length() < 1) {
            return null;
        }
        if (protocolNames.contains(selectApplicationProtocol)) {
            return ProtocolName.asUtf8Encoding(selectApplicationProtocol);
        }
        throw new TlsFatalAlert(AlertDescription.no_application_protocol);
    }

    public TlsCredentials selectServerCredentials12(Principal[] principalArr, int i2) {
        Logger logger;
        StringBuilder sb;
        BCAlgorithmConstraints algorithmConstraints = this.sslParameters.getAlgorithmConstraints();
        short legacySignatureAlgorithmServer = TlsUtils.getLegacySignatureAlgorithmServer(i2);
        LinkedHashMap<String, SignatureSchemeInfo> linkedHashMap = new LinkedHashMap<>();
        for (SignatureSchemeInfo signatureSchemeInfo : this.jsseSecurityParameters.peerSigSchemes) {
            if (TlsUtils.isValidSignatureSchemeForServerKeyExchange(signatureSchemeInfo.getSignatureScheme(), i2)) {
                String keyTypeLegacyServer = legacySignatureAlgorithmServer == signatureSchemeInfo.getSignatureAlgorithm() ? JsseUtils.getKeyTypeLegacyServer(i2) : signatureSchemeInfo.getKeyType();
                if (!this.keyManagerMissCache.contains(keyTypeLegacyServer) && !linkedHashMap.containsKey(keyTypeLegacyServer) && signatureSchemeInfo.isActive(algorithmConstraints, false, true, this.jsseSecurityParameters.namedGroups)) {
                    linkedHashMap.put(keyTypeLegacyServer, signatureSchemeInfo);
                }
            }
        }
        if (linkedHashMap.isEmpty()) {
            logger = LOG;
            sb = new StringBuilder("Server (1.2) has no key types to try for KeyExchangeAlgorithm ");
        } else {
            BCX509Key chooseServerKey = this.manager.chooseServerKey((String[]) linkedHashMap.keySet().toArray(TlsUtils.EMPTY_STRINGS), principalArr);
            if (chooseServerKey != null) {
                String keyType = chooseServerKey.getKeyType();
                handleKeyManagerMisses(linkedHashMap, keyType);
                SignatureSchemeInfo signatureSchemeInfo2 = linkedHashMap.get(keyType);
                if (signatureSchemeInfo2 == null) {
                    throw new TlsFatalAlert((short) 80, "Key manager returned invalid key type");
                }
                Logger logger2 = LOG;
                if (logger2.isLoggable(Level.FINE)) {
                    logger2.fine("Server (1.2) selected credentials for signature scheme '" + signatureSchemeInfo2 + "' (keyType '" + keyType + "'), with private key algorithm '" + JsseUtils.getPrivateKeyAlgorithm(chooseServerKey.getPrivateKey()) + "'");
                }
                return JsseUtils.createCredentialedSigner(this.context, getCrypto(), chooseServerKey, signatureSchemeInfo2.getSignatureAndHashAlgorithm());
            }
            handleKeyManagerMisses(linkedHashMap, null);
            logger = LOG;
            sb = new StringBuilder("Server (1.2) did not select any credentials for KeyExchangeAlgorithm ");
        }
        sb.append(i2);
        logger.fine(sb.toString());
        return null;
    }

    public TlsCredentials selectServerCredentials13(Principal[] principalArr, byte[] bArr) {
        Logger logger;
        String str;
        BCAlgorithmConstraints algorithmConstraints = this.sslParameters.getAlgorithmConstraints();
        LinkedHashMap<String, SignatureSchemeInfo> linkedHashMap = new LinkedHashMap<>();
        for (SignatureSchemeInfo signatureSchemeInfo : this.jsseSecurityParameters.peerSigSchemes) {
            String keyType13 = signatureSchemeInfo.getKeyType13();
            if (!this.keyManagerMissCache.contains(keyType13) && !linkedHashMap.containsKey(keyType13) && signatureSchemeInfo.isActive(algorithmConstraints, true, false, this.jsseSecurityParameters.namedGroups)) {
                linkedHashMap.put(keyType13, signatureSchemeInfo);
            }
        }
        if (linkedHashMap.isEmpty()) {
            logger = LOG;
            str = "Server (1.3) found no usable signature schemes";
        } else {
            BCX509Key chooseServerKey = this.manager.chooseServerKey((String[]) linkedHashMap.keySet().toArray(TlsUtils.EMPTY_STRINGS), principalArr);
            if (chooseServerKey != null) {
                String keyType = chooseServerKey.getKeyType();
                handleKeyManagerMisses(linkedHashMap, keyType);
                SignatureSchemeInfo signatureSchemeInfo2 = linkedHashMap.get(keyType);
                if (signatureSchemeInfo2 == null) {
                    throw new TlsFatalAlert((short) 80, "Key manager returned invalid key type");
                }
                Logger logger2 = LOG;
                if (logger2.isLoggable(Level.FINE)) {
                    logger2.fine("Server (1.3) selected credentials for signature scheme '" + signatureSchemeInfo2 + "' (keyType '" + keyType + "'), with private key algorithm '" + JsseUtils.getPrivateKeyAlgorithm(chooseServerKey.getPrivateKey()) + "'");
                }
                return JsseUtils.createCredentialedSigner13(this.context, getCrypto(), chooseServerKey, signatureSchemeInfo2.getSignatureAndHashAlgorithm(), bArr);
            }
            handleKeyManagerMisses(linkedHashMap, null);
            logger = LOG;
            str = "Server (1.3) did not select any credentials";
        }
        logger.fine(str);
        return null;
    }

    public TlsCredentials selectServerCredentialsLegacy(Principal[] principalArr, int i2) {
        String keyTypeLegacyServer = JsseUtils.getKeyTypeLegacyServer(i2);
        if (this.keyManagerMissCache.contains(keyTypeLegacyServer)) {
            return null;
        }
        BCX509Key chooseServerKey = this.manager.chooseServerKey(new String[]{keyTypeLegacyServer}, principalArr);
        if (chooseServerKey != null) {
            return 1 == i2 ? JsseUtils.createCredentialedDecryptor(getCrypto(), chooseServerKey) : JsseUtils.createCredentialedSigner(this.context, getCrypto(), chooseServerKey, null);
        }
        this.keyManagerMissCache.add(keyTypeLegacyServer);
        return null;
    }

    @Override // org.bouncycastle.tls.AbstractTlsServer
    public boolean shouldSelectProtocolNameEarly() {
        return this.sslParameters.getEngineAPSelector() == null && this.sslParameters.getSocketAPSelector() == null;
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public boolean shouldUseExtendedMasterSecret() {
        return JsseUtils.useExtendedMasterSecret();
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public JcaTlsCrypto getCrypto() {
        return this.manager.getContextData().getCrypto();
    }
}
