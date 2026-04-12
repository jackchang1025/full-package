package p000;

import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class bc0 {

    /* renamed from: a0 */
    public static final Locale[] f45807a0 = {new Locale("en", "XA"), new Locale("ar", "XB")};

    /* renamed from: a0 */
    public static Locale m210659a0(String str) {
        return Locale.forLanguageTag(str);
    }

    /* renamed from: a1 */
    public static boolean m210660a1(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return true;
        }
        if (locale.getLanguage().equals(locale2.getLanguage())) {
            Locale[] localeArr = f45807a0;
            int length = localeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    int length2 = localeArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            String strM214031a2 = n40.m214031a2(n40.m214029a0(n40.m214030a1(locale)));
                            if (!strM214031a2.isEmpty()) {
                                return strM214031a2.equals(n40.m214031a2(n40.m214029a0(n40.m214030a1(locale2))));
                            }
                            String country = locale.getCountry();
                            if (country.isEmpty() || country.equals(locale2.getCountry())) {
                                return true;
                            }
                        } else {
                            if (localeArr[i2].equals(locale2)) {
                                break;
                            }
                            i2++;
                        }
                    }
                } else {
                    if (localeArr[i].equals(locale)) {
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }
}
