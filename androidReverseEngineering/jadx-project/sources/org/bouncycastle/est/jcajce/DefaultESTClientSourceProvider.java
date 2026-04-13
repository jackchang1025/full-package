package org.bouncycastle.est.jcajce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.bouncycastle.est.ESTClientSourceProvider;
import org.bouncycastle.est.Source;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class DefaultESTClientSourceProvider implements ESTClientSourceProvider {
    private final Long absoluteLimit;
    private final ChannelBindingProvider bindingProvider;
    private final Set<String> cipherSuites;
    private final boolean filterSupportedSuites;
    private final JsseHostnameAuthorizer hostNameAuthorizer;
    private final SSLSocketFactory sslSocketFactory;
    private final int timeout;

    public DefaultESTClientSourceProvider(SSLSocketFactory sSLSocketFactory, JsseHostnameAuthorizer jsseHostnameAuthorizer, int i2, ChannelBindingProvider channelBindingProvider, Set<String> set, Long l2, boolean z2) {
        this.sslSocketFactory = sSLSocketFactory;
        this.hostNameAuthorizer = jsseHostnameAuthorizer;
        this.timeout = i2;
        this.bindingProvider = channelBindingProvider;
        this.cipherSuites = set;
        this.absoluteLimit = l2;
        this.filterSupportedSuites = z2;
    }

    @Override // org.bouncycastle.est.ESTClientSourceProvider
    public Source makeSource(String str, int i2) {
        SSLSocket sSLSocket = (SSLSocket) this.sslSocketFactory.createSocket(str, i2);
        sSLSocket.setSoTimeout(this.timeout);
        Set<String> set = this.cipherSuites;
        if (set != null && !set.isEmpty()) {
            if (this.filterSupportedSuites) {
                HashSet hashSet = new HashSet();
                String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
                for (int i3 = 0; i3 != supportedCipherSuites.length; i3++) {
                    hashSet.add(supportedCipherSuites[i3]);
                }
                ArrayList arrayList = new ArrayList();
                for (String str2 : this.cipherSuites) {
                    if (hashSet.contains(str2)) {
                        arrayList.add(str2);
                    }
                }
                if (arrayList.isEmpty()) {
                    throw new IllegalStateException("No supplied cipher suite is supported by the provider.");
                }
                sSLSocket.setEnabledCipherSuites((String[]) arrayList.toArray(new String[arrayList.size()]));
            } else {
                Set<String> set2 = this.cipherSuites;
                sSLSocket.setEnabledCipherSuites((String[]) set2.toArray(new String[set2.size()]));
            }
        }
        sSLSocket.startHandshake();
        JsseHostnameAuthorizer jsseHostnameAuthorizer = this.hostNameAuthorizer;
        if (jsseHostnameAuthorizer != null && !jsseHostnameAuthorizer.verified(str, sSLSocket.getSession())) {
            throw new IOException("Host name could not be verified.");
        }
        String lowerCase = Strings.toLowerCase(sSLSocket.getSession().getCipherSuite());
        if (lowerCase.contains("_des_") || lowerCase.contains("_des40_") || lowerCase.contains("_3des_")) {
            throw new IOException("EST clients must not use DES ciphers");
        }
        if (Strings.toLowerCase(sSLSocket.getSession().getCipherSuite()).contains("null")) {
            throw new IOException("EST clients must not use NULL ciphers");
        }
        if (Strings.toLowerCase(sSLSocket.getSession().getCipherSuite()).contains("anon")) {
            throw new IOException("EST clients must not use anon ciphers");
        }
        if (Strings.toLowerCase(sSLSocket.getSession().getCipherSuite()).contains("export")) {
            throw new IOException("EST clients must not use export ciphers");
        }
        if (sSLSocket.getSession().getProtocol().equalsIgnoreCase("tlsv1")) {
            try {
                sSLSocket.close();
            } catch (Exception unused) {
            }
            throw new IOException("EST clients must not use TLSv1");
        }
        JsseHostnameAuthorizer jsseHostnameAuthorizer2 = this.hostNameAuthorizer;
        if (jsseHostnameAuthorizer2 == null || jsseHostnameAuthorizer2.verified(str, sSLSocket.getSession())) {
            return new LimitedSSLSocketSource(sSLSocket, this.bindingProvider, this.absoluteLimit);
        }
        throw new IOException(AbstractC0000a.m15k("Hostname was not verified: ", str));
    }
}
