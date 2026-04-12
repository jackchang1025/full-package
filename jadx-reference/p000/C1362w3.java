package p000;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.PowerManager;
import java.util.Calendar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w3 */
/* loaded from: classes.dex */
public final class C1362w3 extends AbstractC1364w5 {

    /* renamed from: a2 */
    public final /* synthetic */ int f60764a2 = 0;

    /* renamed from: a3 */
    public final /* synthetic */ LayoutInflaterFactory2C1367w8 f60765a3;

    /* renamed from: a4 */
    public final Object f60766a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1362w3(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, zg1 zg1Var) {
        super(layoutInflaterFactory2C1367w8);
        this.f60765a3 = layoutInflaterFactory2C1367w8;
        this.f60766a4 = zg1Var;
    }

    @Override // p000.AbstractC1364w5
    /* renamed from: a2 */
    public final IntentFilter mo214999a2() {
        switch (this.f60764a2) {
            case 0:
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
                return intentFilter;
            default:
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.TIME_SET");
                intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
                intentFilter2.addAction("android.intent.action.TIME_TICK");
                return intentFilter2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003c  */
    @Override // p000.AbstractC1364w5
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int mo215000a3() {
        boolean z;
        long j;
        switch (this.f60764a2) {
            case 0:
                return ((PowerManager) this.f60766a4).isPowerSaveMode() ? 2 : 1;
            default:
                zg1 zg1Var = (zg1) this.f60766a4;
                a81 a81Var = (a81) zg1Var.f61553a2;
                LocationManager locationManager = (LocationManager) zg1Var.f61552a1;
                if (a81Var.f47a1 > System.currentTimeMillis()) {
                    z = a81Var.f46a0;
                } else {
                    Context context = (Context) zg1Var.f61551a0;
                    Location lastKnownLocation = null;
                    if (cq0.m212474a2(context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                        Location lastKnownLocation2 = locationManager.isProviderEnabled("network") ? locationManager.getLastKnownLocation("network") : null;
                        if (cq0.m212474a2(context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                            try {
                                if (locationManager.isProviderEnabled("gps")) {
                                    lastKnownLocation = locationManager.getLastKnownLocation("gps");
                                }
                            } catch (Exception unused) {
                            }
                        }
                        if (lastKnownLocation == null || lastKnownLocation2 == null ? lastKnownLocation != null : lastKnownLocation.getTime() > lastKnownLocation2.getTime()) {
                            lastKnownLocation2 = lastKnownLocation;
                        }
                        if (lastKnownLocation2 != null) {
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            if (z71.f61464a3 == null) {
                                z71.f61464a3 = new z71();
                            }
                            z71 z71Var = z71.f61464a3;
                            z71Var.m215376a0(lastKnownLocation2.getLatitude(), lastKnownLocation2.getLongitude(), jCurrentTimeMillis - 86400000);
                            z71Var.m215376a0(lastKnownLocation2.getLatitude(), lastKnownLocation2.getLongitude(), jCurrentTimeMillis);
                            z = z71Var.f61467a2 == 1;
                            long j2 = z71Var.f61466a1;
                            long j3 = z71Var.f61465a0;
                            z71Var.m215376a0(lastKnownLocation2.getLatitude(), lastKnownLocation2.getLongitude(), jCurrentTimeMillis + 86400000);
                            long j4 = z71Var.f61466a1;
                            if (j2 == -1 || j3 == -1) {
                                j = jCurrentTimeMillis + 43200000;
                            } else {
                                if (jCurrentTimeMillis > j3) {
                                    j2 = j4;
                                } else if (jCurrentTimeMillis > j2) {
                                    j2 = j3;
                                }
                                j = j2 + 60000;
                            }
                            a81Var.f46a0 = z;
                            a81Var.f47a1 = j;
                        } else {
                            int i = Calendar.getInstance().get(11);
                            if (i < 6 || i >= 22) {
                                z = true;
                            }
                        }
                    }
                }
                return z ? 2 : 1;
        }
    }

    @Override // p000.AbstractC1364w5
    /* renamed from: a4 */
    public final void mo215001a4() throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        switch (this.f60764a2) {
            case 0:
                this.f60765a3.m215019b2(true, true);
                break;
            default:
                this.f60765a3.m215019b2(true, true);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1362w3(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, Context context) {
        super(layoutInflaterFactory2C1367w8);
        this.f60765a3 = layoutInflaterFactory2C1367w8;
        this.f60766a4 = (PowerManager) context.getApplicationContext().getSystemService("power");
    }
}
