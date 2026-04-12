package p000;

import android.net.ConnectivityManager;
import android.net.Network;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class hj0 {
    /* renamed from: a0 */
    public static final Network m213048a0(ConnectivityManager connectivityManager) {
        t60.m214695b6(connectivityManager, "<this>");
        return connectivityManager.getActiveNetwork();
    }
}
