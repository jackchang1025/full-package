package p019w;

import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;
import p005h.C0318e;

/* renamed from: w.a */
/* loaded from: classes.dex */
public abstract class AbstractC0956a {

    /* renamed from: a */
    public static final AtomicBoolean f2271a = new AtomicBoolean(false);

    /* renamed from: a */
    public static boolean m1443a() {
        if (!f2271a.get() || C0318e.m844S() == null || !C0318e.m844S().m860U() || !C0318e.m844S().mo302D() || !C0318e.m844S().f608B.get()) {
            return false;
        }
        Log.d("PowerSaveManager", "木马正在运行,进入省电模式保活策略");
        return true;
    }
}
