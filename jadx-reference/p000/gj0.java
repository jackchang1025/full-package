package p000;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class gj0 {
    /* renamed from: a0 */
    public static final NetworkCapabilities m212958a0(ConnectivityManager connectivityManager, Network network) {
        t60.m214695b6(connectivityManager, "<this>");
        return connectivityManager.getNetworkCapabilities(network);
    }

    /* renamed from: a1 */
    public static final boolean m212959a1(NetworkCapabilities networkCapabilities, int i) {
        t60.m214695b6(networkCapabilities, "<this>");
        return networkCapabilities.hasCapability(i);
    }

    /* renamed from: a2 */
    public static final void m212960a2(ConnectivityManager connectivityManager, ConnectivityManager.NetworkCallback networkCallback) {
        t60.m214695b6(connectivityManager, "<this>");
        t60.m214695b6(networkCallback, "networkCallback");
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
