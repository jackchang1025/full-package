package org.bouncycastle.jsse.provider;

import java.util.List;
import java.util.Vector;
import org.bouncycastle.jsse.BCX509ExtendedKeyManager;
import org.bouncycastle.jsse.BCX509ExtendedTrustManager;
import org.bouncycastle.jsse.provider.NamedGroupInfo;
import org.bouncycastle.jsse.provider.SignatureSchemeInfo;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;

/* loaded from: classes.dex */
final class ContextData {
    private final ProvSSLContextSpi context;
    private final JcaTlsCrypto crypto;
    private final NamedGroupInfo.PerContext namedGroups;
    private final SignatureSchemeInfo.PerContext signatureSchemes;
    private final BCX509ExtendedKeyManager x509KeyManager;
    private final BCX509ExtendedTrustManager x509TrustManager;
    private final ProvSSLSessionContext clientSessionContext = new ProvSSLSessionContext(this);
    private final ProvSSLSessionContext serverSessionContext = new ProvSSLSessionContext(this);

    public ContextData(ProvSSLContextSpi provSSLContextSpi, JcaTlsCrypto jcaTlsCrypto, BCX509ExtendedKeyManager bCX509ExtendedKeyManager, BCX509ExtendedTrustManager bCX509ExtendedTrustManager) {
        this.context = provSSLContextSpi;
        this.crypto = jcaTlsCrypto;
        this.x509KeyManager = bCX509ExtendedKeyManager;
        this.x509TrustManager = bCX509ExtendedTrustManager;
        NamedGroupInfo.PerContext createPerContext = NamedGroupInfo.createPerContext(provSSLContextSpi.isFips(), jcaTlsCrypto);
        this.namedGroups = createPerContext;
        this.signatureSchemes = SignatureSchemeInfo.createPerContext(provSSLContextSpi.isFips(), jcaTlsCrypto, createPerContext);
    }

    public List<SignatureSchemeInfo> getActiveCertsSignatureSchemes(boolean z2, ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr, NamedGroupInfo.PerConnection perConnection) {
        return SignatureSchemeInfo.getActiveCertsSignatureSchemes(this.signatureSchemes, z2, provSSLParameters, protocolVersionArr, perConnection);
    }

    public ProvSSLSessionContext getClientSessionContext() {
        return this.clientSessionContext;
    }

    public ProvSSLContextSpi getContext() {
        return this.context;
    }

    public JcaTlsCrypto getCrypto() {
        return this.crypto;
    }

    public NamedGroupInfo.PerConnection getNamedGroups(ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr) {
        return NamedGroupInfo.createPerConnection(this.namedGroups, provSSLParameters, protocolVersionArr);
    }

    public ProvSSLSessionContext getServerSessionContext() {
        return this.serverSessionContext;
    }

    public List<SignatureSchemeInfo> getSignatureSchemes(Vector<SignatureAndHashAlgorithm> vector) {
        return SignatureSchemeInfo.getSignatureSchemes(this.signatureSchemes, vector);
    }

    public BCX509ExtendedKeyManager getX509KeyManager() {
        return this.x509KeyManager;
    }

    public BCX509ExtendedTrustManager getX509TrustManager() {
        return this.x509TrustManager;
    }
}
