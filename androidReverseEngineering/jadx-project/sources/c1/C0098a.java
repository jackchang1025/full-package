package c1;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

/* renamed from: c1.a */
/* loaded from: classes.dex */
public final class C0098a implements NsdManager.DiscoveryListener {

    /* renamed from: a */
    public final C0101d f170a;

    public C0098a(C0101d c0101d) {
        this.f170a = c0101d;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStarted(String str) {
        this.f170a.f177f = true;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStopped(String str) {
        this.f170a.f177f = false;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        C0101d c0101d = this.f170a;
        c0101d.getClass();
        c0101d.f176e.resolveService(nsdServiceInfo, new C0100c(c0101d));
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        C0101d c0101d = this.f170a;
        String str = c0101d.f179h;
        if (str == null || !str.equals(nsdServiceInfo.getServiceName())) {
            return;
        }
        c0101d.f174c.mo298a(nsdServiceInfo.getHost(), -1);
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStartDiscoveryFailed(String str, int i2) {
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStopDiscoveryFailed(String str, int i2) {
    }
}
