package org.bouncycastle.tls;

import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;

/* loaded from: classes.dex */
public abstract class AbstractTlsServer extends AbstractTlsPeer implements TlsServer {
    protected CertificateStatusRequest certificateStatusRequest;
    protected int[] cipherSuites;
    protected Hashtable clientExtensions;
    protected Vector clientProtocolNames;
    protected boolean clientSentECPointFormats;
    protected TlsServerContext context;
    protected boolean encryptThenMACOffered;
    protected short maxFragmentLengthOffered;
    protected int[] offeredCipherSuites;
    protected ProtocolVersion[] protocolVersions;
    protected int selectedCipherSuite;
    protected ProtocolName selectedProtocolName;
    protected final Hashtable serverExtensions;
    protected Vector statusRequestV2;
    protected boolean truncatedHMacOffered;
    protected Vector trustedCAKeys;

    public AbstractTlsServer(TlsCrypto tlsCrypto) {
        super(tlsCrypto);
        this.serverExtensions = new Hashtable();
    }

    public boolean allowCertificateStatus() {
        return true;
    }

    public boolean allowEncryptThenMAC() {
        return true;
    }

    public boolean allowMultiCertStatus() {
        return false;
    }

    public boolean allowTruncatedHMac() {
        return false;
    }

    public boolean allowTrustedCAIndication() {
        return false;
    }

    public Hashtable checkServerExtensions() {
        return this.serverExtensions;
    }

    public CertificateRequest getCertificateRequest() {
        return null;
    }

    public CertificateStatus getCertificateStatus() {
        return null;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public int[] getCipherSuites() {
        return this.cipherSuites;
    }

    @Override // org.bouncycastle.tls.TlsServer
    public TlsDHConfig getDHConfig() {
        return TlsDHUtils.createNamedDHConfig(this.context, selectDH(TlsDHUtils.getMinimumFiniteFieldBits(this.selectedCipherSuite)));
    }

    @Override // org.bouncycastle.tls.TlsServer
    public TlsECConfig getECDHConfig() {
        return TlsECCUtils.createNamedECConfig(this.context, selectECDH(TlsECCUtils.getMinimumCurveBits(this.selectedCipherSuite)));
    }

    @Override // org.bouncycastle.tls.TlsServer
    public TlsPSKExternal getExternalPSK(Vector vector) {
        return null;
    }

    public int getMaximumNegotiableCurveBits() {
        int[] clientSupportedGroups = this.context.getSecurityParametersHandshake().getClientSupportedGroups();
        if (clientSupportedGroups == null) {
            return NamedGroup.getMaximumCurveBits();
        }
        int i2 = 0;
        for (int i3 : clientSupportedGroups) {
            i2 = Math.max(i2, NamedGroup.getCurveBits(i3));
        }
        return i2;
    }

    public int getMaximumNegotiableFiniteFieldBits() {
        int[] clientSupportedGroups = this.context.getSecurityParametersHandshake().getClientSupportedGroups();
        if (clientSupportedGroups == null) {
            return NamedGroup.getMaximumFiniteFieldBits();
        }
        int i2 = 0;
        for (int i3 : clientSupportedGroups) {
            i2 = Math.max(i2, NamedGroup.getFiniteFieldBits(i3));
        }
        return i2;
    }

    public byte[] getNewSessionID() {
        return null;
    }

    @Override // org.bouncycastle.tls.TlsServer
    public NewSessionTicket getNewSessionTicket() {
        return new NewSessionTicket(0L, TlsUtils.EMPTY_BYTES);
    }

    @Override // org.bouncycastle.tls.TlsServer
    public TlsPSKIdentityManager getPSKIdentityManager() {
        return null;
    }

    public Vector getProtocolNames() {
        return null;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public ProtocolVersion[] getProtocolVersions() {
        return this.protocolVersions;
    }

    @Override // org.bouncycastle.tls.TlsServer
    public TlsSRPLoginParameters getSRPLoginParameters() {
        return null;
    }

    public int getSelectedCipherSuite() {
        SecurityParameters securityParametersHandshake = this.context.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        if (TlsUtils.isTLSv13(negotiatedVersion)) {
            int commonCipherSuite13 = TlsUtils.getCommonCipherSuite13(negotiatedVersion, this.offeredCipherSuites, getCipherSuites(), preferLocalCipherSuites());
            if (commonCipherSuite13 >= 0 && selectCipherSuite(commonCipherSuite13)) {
                return commonCipherSuite13;
            }
        } else {
            Vector usableSignatureAlgorithms = TlsUtils.getUsableSignatureAlgorithms(securityParametersHandshake.getClientSigAlgs());
            int maximumNegotiableCurveBits = getMaximumNegotiableCurveBits();
            int maximumNegotiableFiniteFieldBits = getMaximumNegotiableFiniteFieldBits();
            for (int i2 : TlsUtils.getCommonCipherSuites(this.offeredCipherSuites, getCipherSuites(), preferLocalCipherSuites())) {
                if (isSelectableCipherSuite(i2, maximumNegotiableCurveBits, maximumNegotiableFiniteFieldBits, usableSignatureAlgorithms) && selectCipherSuite(i2)) {
                    return i2;
                }
            }
        }
        throw new TlsFatalAlert((short) 40, "No selectable cipher suite");
    }

    public Hashtable getServerExtensions() {
        Hashtable hashtable;
        Integer num;
        if (!TlsUtils.isTLSv13(this.context)) {
            if (this.encryptThenMACOffered && allowEncryptThenMAC() && TlsUtils.isBlockCipherSuite(this.selectedCipherSuite)) {
                TlsExtensionsUtils.addEncryptThenMACExtension(this.serverExtensions);
            }
            if (this.truncatedHMacOffered && allowTruncatedHMac()) {
                TlsExtensionsUtils.addTruncatedHMacExtension(this.serverExtensions);
            }
            if (this.clientSentECPointFormats && TlsECCUtils.isECCCipherSuite(this.selectedCipherSuite)) {
                TlsExtensionsUtils.addSupportedPointFormatsExtension(this.serverExtensions, new short[]{0});
            }
            if (this.statusRequestV2 == null || !allowMultiCertStatus()) {
                if (this.certificateStatusRequest != null && allowCertificateStatus()) {
                    hashtable = this.serverExtensions;
                    num = TlsExtensionsUtils.EXT_status_request;
                }
                if (this.trustedCAKeys != null && allowTrustedCAIndication()) {
                    TlsExtensionsUtils.addTrustedCAKeysExtensionServer(this.serverExtensions);
                }
            } else {
                hashtable = this.serverExtensions;
                num = TlsExtensionsUtils.EXT_status_request_v2;
            }
            TlsExtensionsUtils.addEmptyExtensionData(hashtable, num);
            if (this.trustedCAKeys != null) {
                TlsExtensionsUtils.addTrustedCAKeysExtensionServer(this.serverExtensions);
            }
        } else if (this.certificateStatusRequest != null) {
            allowCertificateStatus();
        }
        short s2 = this.maxFragmentLengthOffered;
        if (s2 >= 0 && MaxFragmentLength.isValid(s2)) {
            TlsExtensionsUtils.addMaxFragmentLengthExtension(this.serverExtensions, this.maxFragmentLengthOffered);
        }
        return this.serverExtensions;
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void getServerExtensionsForConnection(Hashtable hashtable) {
        Vector vector;
        if (!shouldSelectProtocolNameEarly() && (vector = this.clientProtocolNames) != null && !vector.isEmpty()) {
            this.selectedProtocolName = selectProtocolName();
        }
        ProtocolName protocolName = this.selectedProtocolName;
        if (protocolName == null) {
            hashtable.remove(TlsExtensionsUtils.EXT_application_layer_protocol_negotiation);
        } else {
            TlsExtensionsUtils.addALPNExtensionServer(hashtable, protocolName);
        }
    }

    @Override // org.bouncycastle.tls.TlsServer
    public Vector getServerSupplementalData() {
        return null;
    }

    public ProtocolVersion getServerVersion() {
        ProtocolVersion[] protocolVersions = getProtocolVersions();
        for (ProtocolVersion protocolVersion : this.context.getClientSupportedVersions()) {
            if (ProtocolVersion.contains(protocolVersions, protocolVersion)) {
                return protocolVersion;
            }
        }
        throw new TlsFatalAlert((short) 70);
    }

    public TlsSession getSessionToResume(byte[] bArr) {
        return null;
    }

    public int[] getSupportedGroups() {
        return new int[]{29, 30, 23, 24, 256, 257, NamedGroup.ffdhe4096};
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void init(TlsServerContext tlsServerContext) {
        this.context = tlsServerContext;
        this.protocolVersions = getSupportedVersions();
        this.cipherSuites = getSupportedCipherSuites();
    }

    public boolean isSelectableCipherSuite(int i2, int i3, int i4, Vector vector) {
        return TlsUtils.isValidVersionForCipherSuite(i2, this.context.getServerVersion()) && i3 >= TlsECCUtils.getMinimumCurveBits(i2) && i4 >= TlsDHUtils.getMinimumFiniteFieldBits(i2) && TlsUtils.isValidCipherSuiteForSignatureAlgorithms(i2, vector);
    }

    public void notifyClientCertificate(Certificate certificate) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void notifyClientVersion(ProtocolVersion protocolVersion) {
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void notifyFallback(boolean z2) {
        ProtocolVersion latestDTLS;
        if (z2) {
            ProtocolVersion[] protocolVersions = getProtocolVersions();
            ProtocolVersion clientVersion = this.context.getClientVersion();
            if (clientVersion.isTLS()) {
                latestDTLS = ProtocolVersion.getLatestTLS(protocolVersions);
            } else {
                if (!clientVersion.isDTLS()) {
                    throw new TlsFatalAlert((short) 80);
                }
                latestDTLS = ProtocolVersion.getLatestDTLS(protocolVersions);
            }
            if (latestDTLS != null && latestDTLS.isLaterVersionOf(clientVersion)) {
                throw new TlsFatalAlert((short) 86);
            }
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer, org.bouncycastle.tls.TlsPeer
    public void notifyHandshakeBeginning() {
        super.notifyHandshakeBeginning();
        this.offeredCipherSuites = null;
        this.clientExtensions = null;
        this.encryptThenMACOffered = false;
        this.maxFragmentLengthOffered = (short) 0;
        this.truncatedHMacOffered = false;
        this.clientSentECPointFormats = false;
        this.certificateStatusRequest = null;
        this.selectedCipherSuite = -1;
        this.selectedProtocolName = null;
        this.serverExtensions.clear();
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void notifyOfferedCipherSuites(int[] iArr) {
        this.offeredCipherSuites = iArr;
    }

    public void notifySession(TlsSession tlsSession) {
    }

    public boolean preferLocalCipherSuites() {
        return false;
    }

    public void processClientExtensions(Hashtable hashtable) {
        Vector vector;
        this.clientExtensions = hashtable;
        if (hashtable != null) {
            this.clientProtocolNames = TlsExtensionsUtils.getALPNExtensionClient(hashtable);
            if (shouldSelectProtocolNameEarly() && (vector = this.clientProtocolNames) != null && !vector.isEmpty()) {
                this.selectedProtocolName = selectProtocolName();
            }
            this.encryptThenMACOffered = TlsExtensionsUtils.hasEncryptThenMACExtension(hashtable);
            this.truncatedHMacOffered = TlsExtensionsUtils.hasTruncatedHMacExtension(hashtable);
            this.statusRequestV2 = TlsExtensionsUtils.getStatusRequestV2Extension(hashtable);
            this.trustedCAKeys = TlsExtensionsUtils.getTrustedCAKeysExtensionClient(hashtable);
            this.clientSentECPointFormats = TlsExtensionsUtils.getSupportedPointFormatsExtension(hashtable) != null;
            this.certificateStatusRequest = TlsExtensionsUtils.getStatusRequestExtension(hashtable);
            short maxFragmentLengthExtension = TlsExtensionsUtils.getMaxFragmentLengthExtension(hashtable);
            this.maxFragmentLengthOffered = maxFragmentLengthExtension;
            if (maxFragmentLengthExtension >= 0 && !MaxFragmentLength.isValid(maxFragmentLengthExtension)) {
                throw new TlsFatalAlert((short) 47);
            }
        }
    }

    @Override // org.bouncycastle.tls.TlsServer
    public void processClientSupplementalData(Vector vector) {
        if (vector != null) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    public boolean selectCipherSuite(int i2) {
        this.selectedCipherSuite = i2;
        return true;
    }

    public int selectDH(int i2) {
        int[] clientSupportedGroups = this.context.getSecurityParametersHandshake().getClientSupportedGroups();
        if (clientSupportedGroups == null) {
            return selectDHDefault(i2);
        }
        for (int i3 : clientSupportedGroups) {
            if (NamedGroup.getFiniteFieldBits(i3) >= i2) {
                return i3;
            }
        }
        return -1;
    }

    public int selectDHDefault(int i2) {
        if (i2 <= 2048) {
            return 256;
        }
        if (i2 <= 3072) {
            return 257;
        }
        if (i2 <= 4096) {
            return NamedGroup.ffdhe4096;
        }
        if (i2 <= 6144) {
            return NamedGroup.ffdhe6144;
        }
        if (i2 <= 8192) {
            return NamedGroup.ffdhe8192;
        }
        return -1;
    }

    public int selectECDH(int i2) {
        int[] clientSupportedGroups = this.context.getSecurityParametersHandshake().getClientSupportedGroups();
        if (clientSupportedGroups == null) {
            return selectECDHDefault(i2);
        }
        for (int i3 : clientSupportedGroups) {
            if (NamedGroup.getCurveBits(i3) >= i2) {
                return i3;
            }
        }
        return -1;
    }

    public int selectECDHDefault(int i2) {
        if (i2 <= 256) {
            return 23;
        }
        if (i2 <= 384) {
            return 24;
        }
        return i2 <= 521 ? 25 : -1;
    }

    public ProtocolName selectProtocolName() {
        Vector protocolNames = getProtocolNames();
        if (protocolNames == null || protocolNames.isEmpty()) {
            return null;
        }
        ProtocolName selectProtocolName = selectProtocolName(this.clientProtocolNames, protocolNames);
        if (selectProtocolName != null) {
            return selectProtocolName;
        }
        throw new TlsFatalAlert(AlertDescription.no_application_protocol);
    }

    public boolean shouldSelectProtocolNameEarly() {
        return true;
    }

    public ProtocolName selectProtocolName(Vector vector, Vector vector2) {
        for (int i2 = 0; i2 < vector2.size(); i2++) {
            ProtocolName protocolName = (ProtocolName) vector2.elementAt(i2);
            if (vector.contains(protocolName)) {
                return protocolName;
            }
        }
        return null;
    }
}
