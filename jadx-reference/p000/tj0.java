package p000;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class tj0 {

    /* renamed from: a0 */
    public static final /* synthetic */ int f60235a0 = 0;

    static {
        t60.m214694b5(C1351vv.m214966b1("NetworkStateTracker"), "tagWithPrefix(\"NetworkStateTracker\")");
    }

    /* renamed from: a0 */
    public static final rj0 m214752a0(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilitiesM212958a0;
        t60.m214695b6(connectivityManager, "<this>");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        try {
            networkCapabilitiesM212958a0 = gj0.m212958a0(connectivityManager, hj0.m213048a0(connectivityManager));
        } catch (SecurityException unused) {
            C1351vv.m214963a5().getClass();
        }
        boolean zM212959a1 = networkCapabilitiesM212958a0 != null ? gj0.m212959a1(networkCapabilitiesM212958a0, 16) : false;
        return new rj0(z, zM212959a1, AbstractC0795kt.m213745a0(connectivityManager), (activeNetworkInfo == null || activeNetworkInfo.isRoaming()) ? false : true);
    }
}
