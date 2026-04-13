package b1;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509ExtendedKeyManager;

/* renamed from: b1.q */
/* loaded from: classes.dex */
public final class C0095q extends X509ExtendedKeyManager {

    /* renamed from: a */
    public final /* synthetic */ C0089k f169a;

    public C0095q(C0089k c0089k) {
        this.f169a = c0089k;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        for (String str : strArr) {
            if (str.equals("RSA")) {
                return "key";
            }
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final X509Certificate[] getCertificateChain(String str) {
        if ("key".equals(str)) {
            return new X509Certificate[]{(X509Certificate) this.f169a.f144b};
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getClientAliases(String str, Principal[] principalArr) {
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final PrivateKey getPrivateKey(String str) {
        if ("key".equals(str)) {
            return this.f169a.f143a;
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getServerAliases(String str, Principal[] principalArr) {
        return null;
    }
}
