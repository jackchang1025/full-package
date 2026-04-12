package p000;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import java.util.HashSet;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class nk0 {

    /* renamed from: a2 */
    public static String f58643a2;

    /* renamed from: a0 */
    public final NotificationManager f58645a0;

    /* renamed from: a1 */
    public static final Object f58642a1 = new Object();

    /* renamed from: a3 */
    public static HashSet f58644a3 = new HashSet();

    public nk0(Context context) {
        this.f58645a0 = (NotificationManager) context.getSystemService("notification");
    }

    /* renamed from: a0 */
    public static Set m214111a0(Context context) {
        HashSet hashSet;
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        synchronized (f58642a1) {
            if (string != null) {
                try {
                    if (!string.equals(f58643a2)) {
                        String[] strArrSplit = string.split(":", -1);
                        HashSet hashSet2 = new HashSet(strArrSplit.length);
                        for (String str : strArrSplit) {
                            ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
                            if (componentNameUnflattenFromString != null) {
                                hashSet2.add(componentNameUnflattenFromString.getPackageName());
                            }
                        }
                        f58644a3 = hashSet2;
                        f58643a2 = string;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            hashSet = f58644a3;
        }
        return hashSet;
    }
}
