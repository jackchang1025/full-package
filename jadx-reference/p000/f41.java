package p000;

import java.io.Serializable;
import java.net.Socket;
import java.security.KeyPair;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class f41 extends X509ExtendedKeyManager {

    /* renamed from: a0 */
    public final /* synthetic */ int f56147a0 = 0;

    /* renamed from: a1 */
    public final X509Certificate f56148a1;

    /* renamed from: a2 */
    public final Serializable f56149a2;

    public f41(X509Certificate x509Certificate, KeyPair keyPair) {
        this.f56148a1 = x509Certificate;
        this.f56149a2 = keyPair;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        switch (this.f56147a0) {
            case 0:
                if (strArr != null) {
                    for (String str : strArr) {
                        if (t60.m214686a2(str, "RSA")) {
                            break;
                        }
                    }
                    break;
                }
                break;
            default:
                if (strArr != null) {
                    for (String str2 : strArr) {
                        if (t60.m214686a2(str2, "RSA")) {
                            break;
                        }
                    }
                    break;
                }
                break;
        }
        return null;
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineClientAlias(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        switch (this.f56147a0) {
            case 1:
                if (strArr != null) {
                    for (String str : strArr) {
                        if (t60.m214686a2(str, "RSA")) {
                            return "key";
                        }
                    }
                }
                return null;
            default:
                return super.chooseEngineClientAlias(strArr, principalArr, sSLEngine);
        }
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineServerAlias(String str, Principal[] principalArr, SSLEngine sSLEngine) {
        switch (this.f56147a0) {
            case 1:
                return null;
            default:
                return super.chooseEngineServerAlias(str, principalArr, sSLEngine);
        }
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        switch (this.f56147a0) {
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final X509Certificate[] getCertificateChain(String str) {
        switch (this.f56147a0) {
            case 0:
                if ("key".equals(str)) {
                    return new X509Certificate[]{this.f56148a1};
                }
                return null;
            default:
                if (t60.m214686a2(str, "key")) {
                    return new X509Certificate[]{this.f56148a1};
                }
                return null;
        }
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getClientAliases(String str, Principal[] principalArr) {
        switch (this.f56147a0) {
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final PrivateKey getPrivateKey(String str) {
        switch (this.f56147a0) {
            case 0:
                if ("key".equals(str)) {
                    return (PrivateKey) this.f56149a2;
                }
                return null;
            default:
                if (t60.m214686a2(str, "key")) {
                    return ((KeyPair) this.f56149a2).getPrivate();
                }
                return null;
        }
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getServerAliases(String str, Principal[] principalArr) {
        switch (this.f56147a0) {
        }
        return null;
    }

    public f41(PrivateKey privateKey, X509Certificate x509Certificate) {
        this.f56149a2 = privateKey;
        this.f56148a1 = x509Certificate;
    }
}
