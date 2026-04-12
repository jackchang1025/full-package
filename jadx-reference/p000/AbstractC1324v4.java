package p000;

import android.app.LocaleManager;
import android.os.LocaleList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v4 */
/* loaded from: classes.dex */
public abstract class AbstractC1324v4 {
    /* renamed from: a0 */
    public static LocaleList m214894a0(Object obj) {
        return ((LocaleManager) obj).getApplicationLocales();
    }

    /* renamed from: a1 */
    public static void m214895a1(Object obj, LocaleList localeList) {
        ((LocaleManager) obj).setApplicationLocales(localeList);
    }
}
