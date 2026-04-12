package p000;

import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class l51 {

    /* renamed from: a0 */
    public static final String f57830a0;

    /* renamed from: a1 */
    public static final long f57831a1;

    /* renamed from: a2 */
    public static final int f57832a2;

    /* renamed from: a3 */
    public static final int f57833a3;

    /* renamed from: a4 */
    public static final long f57834a4;

    /* renamed from: a5 */
    public static final C1351vv f57835a5;

    /* renamed from: a6 */
    public static final j51 f57836a6;

    /* renamed from: a7 */
    public static final j51 f57837a7;

    static {
        String property;
        int i = q41.f59384a0;
        try {
            property = System.getProperty("kotlinx.coroutines.scheduler.default.name");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            property = "DefaultDispatcher";
        }
        f57830a0 = property;
        f57831a1 = kj1.m213590d7("kotlinx.coroutines.scheduler.resolution.ns", 100000L, 1L, Long.MAX_VALUE);
        int i2 = q41.f59384a0;
        if (i2 < 2) {
            i2 = 2;
        }
        f57832a2 = kj1.m213591d8(i2, 8, "kotlinx.coroutines.scheduler.core.pool.size");
        f57833a3 = kj1.m213591d8(2097150, 4, "kotlinx.coroutines.scheduler.max.pool.size");
        f57834a4 = TimeUnit.SECONDS.toNanos(kj1.m213590d7("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 1L, Long.MAX_VALUE));
        f57835a5 = C1351vv.f60707a8;
        f57836a6 = new j51(0);
        f57837a7 = new j51(1);
    }
}
