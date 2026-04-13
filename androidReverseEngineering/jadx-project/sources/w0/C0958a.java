package w0;

import android.net.ssl.SSLSockets;
import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

/* renamed from: w0.a */
/* loaded from: classes.dex */
public final class C0958a extends C0961d {
    public C0958a() {
        super(null, null, null, null, null);
    }

    @Override // w0.C0961d, w0.C0966i
    /* renamed from: g */
    public final void mo1445g(SSLSocket sSLSocket, String str, List list) {
        boolean isSupportedSocket;
        try {
            isSupportedSocket = SSLSockets.isSupportedSocket(sSLSocket);
            if (isSupportedSocket) {
                SSLSockets.setUseSessionTickets(sSLSocket, true);
            }
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            sSLParameters.setApplicationProtocols((String[]) C0966i.m1459b(list).toArray(new String[0]));
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalArgumentException e2) {
            throw new IOException("Android internal error", e2);
        }
    }

    @Override // w0.C0961d, w0.C0966i
    /* renamed from: j */
    public final String mo1446j(SSLSocket sSLSocket) {
        String applicationProtocol;
        applicationProtocol = sSLSocket.getApplicationProtocol();
        if (applicationProtocol == null || applicationProtocol.isEmpty()) {
            return null;
        }
        return applicationProtocol;
    }
}
