package w0;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.conscrypt.Conscrypt;

/* renamed from: w0.e */
/* loaded from: classes.dex */
public final class C0962e extends C0966i {
    @Override // w0.C0966i
    /* renamed from: f */
    public final void mo1457f(SSLSocketFactory sSLSocketFactory) {
        if (Conscrypt.isConscrypt(sSLSocketFactory)) {
            Conscrypt.setUseEngineSocket(sSLSocketFactory, true);
        }
    }

    @Override // w0.C0966i
    /* renamed from: g */
    public final void mo1445g(SSLSocket sSLSocket, String str, List list) {
        if (Conscrypt.isConscrypt(sSLSocket)) {
            if (str != null) {
                Conscrypt.setUseSessionTickets(sSLSocket, true);
                Conscrypt.setHostname(sSLSocket, str);
            }
            Conscrypt.setApplicationProtocols(sSLSocket, (String[]) C0966i.m1459b(list).toArray(new String[0]));
        }
    }

    @Override // w0.C0966i
    /* renamed from: i */
    public final SSLContext mo1452i() {
        try {
            return SSLContext.getInstance("TLSv1.3", Conscrypt.newProviderBuilder().provideTrustManager().build());
        } catch (NoSuchAlgorithmException e2) {
            try {
                return SSLContext.getInstance("TLS", Conscrypt.newProviderBuilder().provideTrustManager().build());
            } catch (NoSuchAlgorithmException unused) {
                throw new IllegalStateException("No TLS provider", e2);
            }
        }
    }

    @Override // w0.C0966i
    /* renamed from: j */
    public final String mo1446j(SSLSocket sSLSocket) {
        if (Conscrypt.isConscrypt(sSLSocket)) {
            return Conscrypt.getApplicationProtocol(sSLSocket);
        }
        return null;
    }
}
