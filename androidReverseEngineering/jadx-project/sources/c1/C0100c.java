package c1;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;

/* renamed from: c1.c */
/* loaded from: classes.dex */
public final class C0100c implements NsdManager.ResolveListener {

    /* renamed from: a */
    public final C0101d f171a;

    public C0100c(C0101d c0101d) {
        this.f171a = c0101d;
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i2) {
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
        C0101d c0101d = this.f171a;
        if (c0101d.f178g) {
            try {
                Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
                while (it.hasNext()) {
                    Iterator it2 = Collections.list(((NetworkInterface) it.next()).getInetAddresses()).iterator();
                    while (it2.hasNext()) {
                        String hostAddress = ((InetAddress) it2.next()).getHostAddress();
                        if (hostAddress != null && hostAddress.equals(nsdServiceInfo.getHost().getHostAddress())) {
                            boolean z2 = true;
                            try {
                                new ServerSocket().bind(new InetSocketAddress(AbstractC0251g.c0(c0101d.f172a), nsdServiceInfo.getPort()), 1);
                                z2 = false;
                            } catch (IOException unused) {
                            }
                            if (z2) {
                                c0101d.f179h = nsdServiceInfo.getServiceName();
                                c0101d.f174c.mo298a(nsdServiceInfo.getHost(), nsdServiceInfo.getPort());
                            }
                        }
                    }
                }
            } catch (SocketException e2) {
                e2.printStackTrace();
            }
        }
    }
}
