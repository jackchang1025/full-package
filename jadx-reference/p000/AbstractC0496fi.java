package p000;

import android.os.Build;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fi */
/* loaded from: classes.dex */
public abstract class AbstractC0496fi {

    /* renamed from: a0 */
    public static final /* synthetic */ int f56275a0 = 0;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            int i2 = AbstractC0495fh.f56254a0;
        }
        if (i >= 30) {
            int i3 = AbstractC0495fh.f56254a0;
        }
        if (i >= 30) {
            int i4 = AbstractC0495fh.f56254a0;
        }
        if (i >= 30) {
            int i5 = AbstractC0495fh.f56254a0;
        }
    }

    /* renamed from: a0 */
    public static boolean m212821a0() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            return true;
        }
        if (i < 32) {
            return false;
        }
        String str = Build.VERSION.CODENAME;
        if ("REL".equals(str)) {
            return false;
        }
        Locale locale = Locale.ROOT;
        return str.toUpperCase(locale).compareTo("Tiramisu".toUpperCase(locale)) >= 0;
    }
}
