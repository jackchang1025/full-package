package p000;

import android.os.Build;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class xj1 {

    /* renamed from: a0 */
    public final boolean f61146a0;

    /* renamed from: a1 */
    public final boolean f61147a1;

    /* renamed from: a2 */
    public final boolean f61148a2;

    /* renamed from: a3 */
    public final boolean f61149a3;

    /* renamed from: a4 */
    public final boolean f61150a4;

    /* renamed from: a5 */
    public final boolean f61151a5;

    public xj1() {
        String lowerCase;
        String lowerCase2;
        String str = Build.MANUFACTURER;
        if (str != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase = "unknown";
        }
        String str2 = Build.BRAND;
        if (str2 != null) {
            lowerCase2 = str2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase2 = "unknown";
        }
        String str3 = Build.MODEL;
        str3 = str3 == null ? "unknown" : str3;
        String str4 = Build.DISPLAY;
        String str5 = str4 != null ? str4 : "unknown";
        this.f61146a0 = AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase2, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase2, "poco", false);
        boolean z = AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false) || AbstractC0779a1.m213652a5(str5, "magic", true) || AbstractC0779a1.m213652a5(str5, "honor", true) || AbstractC0779a1.m213652a5(str3, "honor", true);
        this.f61147a1 = z;
        this.f61148a2 = (AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false)) && !z;
        this.f61149a3 = AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase2, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase2, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase2, "realme", false);
        this.f61150a4 = AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "iqoo", false);
        this.f61151a5 = AbstractC0779a1.m213652a5(lowerCase, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase2, "samsung", false);
    }
}
