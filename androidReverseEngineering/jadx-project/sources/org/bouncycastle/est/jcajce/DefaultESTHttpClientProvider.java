package org.bouncycastle.est.jcajce;

import java.util.Set;
import org.bouncycastle.est.ESTClient;
import org.bouncycastle.est.ESTClientProvider;
import org.bouncycastle.est.ESTException;

/* loaded from: classes.dex */
class DefaultESTHttpClientProvider implements ESTClientProvider {
    private final Long absoluteLimit;
    private final ChannelBindingProvider bindingProvider;
    private final Set<String> cipherSuites;
    private final boolean filterCipherSuites;
    private final JsseHostnameAuthorizer hostNameAuthorizer;
    private final SSLSocketFactoryCreator socketFactoryCreator;
    private final int timeout;

    public DefaultESTHttpClientProvider(JsseHostnameAuthorizer jsseHostnameAuthorizer, SSLSocketFactoryCreator sSLSocketFactoryCreator, int i2, ChannelBindingProvider channelBindingProvider, Set<String> set, Long l2, boolean z2) {
        this.hostNameAuthorizer = jsseHostnameAuthorizer;
        this.socketFactoryCreator = sSLSocketFactoryCreator;
        this.timeout = i2;
        this.bindingProvider = channelBindingProvider;
        this.cipherSuites = set;
        this.absoluteLimit = l2;
        this.filterCipherSuites = z2;
    }

    @Override // org.bouncycastle.est.ESTClientProvider
    public boolean isTrusted() {
        return this.socketFactoryCreator.isTrusted();
    }

    @Override // org.bouncycastle.est.ESTClientProvider
    public ESTClient makeClient() {
        try {
            return new DefaultESTClient(new DefaultESTClientSourceProvider(this.socketFactoryCreator.createFactory(), this.hostNameAuthorizer, this.timeout, this.bindingProvider, this.cipherSuites, this.absoluteLimit, this.filterCipherSuites));
        } catch (Exception e2) {
            throw new ESTException(e2.getMessage(), e2.getCause());
        }
    }
}
