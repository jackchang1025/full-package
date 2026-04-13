package p018v;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: v.c */
/* loaded from: classes.dex */
public final class C0929c {

    /* renamed from: f */
    public static volatile C0929c f2113f;

    /* renamed from: a */
    public LocationManager f2114a;

    /* renamed from: b */
    public String f2115b;

    /* renamed from: c */
    public final C0927a f2116c;

    /* renamed from: d */
    public C0928b f2117d;

    /* renamed from: e */
    public final AtomicReference f2118e = new AtomicReference(null);

    public C0929c() {
        if (C0927a.f2110b == null) {
            C0927a.f2110b = new C0927a();
        }
        this.f2116c = C0927a.f2110b;
        m1393a();
    }

    /* renamed from: a */
    public final void m1393a() {
        C0927a c0927a;
        if (AbstractC0251g.m653Z() != null && ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            LocationManager locationManager = (LocationManager) AbstractC0251g.m653Z().getSystemService("location");
            this.f2114a = locationManager;
            List<String> providers = locationManager.getProviders(true);
            Location location = null;
            if (providers != null && !providers.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 31 && providers.contains("fused")) {
                    this.f2115b = "fused";
                    location = this.f2114a.getLastKnownLocation("fused");
                }
                if (location == null && providers.contains("gps")) {
                    location = this.f2114a.getLastKnownLocation("gps");
                    if (this.f2115b == null) {
                        this.f2115b = "gps";
                    }
                }
                if (location == null && providers.contains("network")) {
                    location = this.f2114a.getLastKnownLocation("network");
                    if (this.f2115b == null) {
                        this.f2115b = "network";
                    }
                }
                if (location == null && providers.contains("passive")) {
                    location = this.f2114a.getLastKnownLocation("passive");
                    if (this.f2115b == null) {
                        this.f2115b = "passive";
                    }
                }
            }
            if (location == null || (c0927a = this.f2116c) == null) {
                return;
            }
            c0927a.f2111a = 1;
            C0927a.m1392a(location);
        }
    }

    /* renamed from: b */
    public final boolean m1394b(ReqMonitorLocationVO reqMonitorLocationVO) {
        AtomicReference atomicReference = this.f2118e;
        if (atomicReference.get() == null && reqMonitorLocationVO != null) {
            atomicReference.set(reqMonitorLocationVO);
            return true;
        }
        if (atomicReference.get() == null || reqMonitorLocationVO != null) {
            return false;
        }
        atomicReference.set(null);
        return true;
    }
}
