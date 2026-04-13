package p018v;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/* renamed from: v.b */
/* loaded from: classes.dex */
public final class C0928b implements LocationListener {

    /* renamed from: a */
    public final /* synthetic */ C0929c f2112a;

    public C0928b(C0929c c0929c) {
        this.f2112a = c0929c;
    }

    @Override // android.location.LocationListener
    public final void onLocationChanged(Location location) {
        C0927a c0927a = this.f2112a.f2116c;
        if (c0927a != null) {
            c0927a.f2111a = 1;
            C0927a.m1392a(location);
        }
    }

    @Override // android.location.LocationListener
    public final void onProviderDisabled(String str) {
    }

    @Override // android.location.LocationListener
    public final void onProviderEnabled(String str) {
    }

    @Override // android.location.LocationListener
    public final void onStatusChanged(String str, int i2, Bundle bundle) {
    }
}
