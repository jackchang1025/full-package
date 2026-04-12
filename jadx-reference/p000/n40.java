package p000;

import android.icu.util.ULocale;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class n40 {
    /* renamed from: a0 */
    public static ULocale m214029a0(Object obj) {
        return ULocale.addLikelySubtags((ULocale) obj);
    }

    /* renamed from: a1 */
    public static ULocale m214030a1(Locale locale) {
        return ULocale.forLocale(locale);
    }

    /* renamed from: a2 */
    public static String m214031a2(Object obj) {
        return ((ULocale) obj).getScript();
    }
}
