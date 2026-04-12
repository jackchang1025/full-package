package p000;

import android.net.ConnectivityManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class ij0 {
    /* renamed from: a0 */
    public static final void m213166a0(ConnectivityManager connectivityManager, ConnectivityManager.NetworkCallback networkCallback) {
        t60.m214695b6(connectivityManager, "<this>");
        t60.m214695b6(networkCallback, "networkCallback");
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }
}
