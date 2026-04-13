package a0;

import android.net.ConnectivityManager;
import android.net.Network;

/* renamed from: a0.f */
/* loaded from: classes.dex */
public final class C0006f extends ConnectivityManager.NetworkCallback {
    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onAvailable(Network network) {
        super.onAvailable(network);
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLosing(Network network, int i2) {
        super.onLosing(network, i2);
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLost(Network network) {
        super.onLost(network);
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onUnavailable() {
        super.onUnavailable();
    }
}
