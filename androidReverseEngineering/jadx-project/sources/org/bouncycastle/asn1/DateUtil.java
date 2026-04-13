package org.bouncycastle.asn1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
class DateUtil {
    private static Long ZERO = longValueOf(0);
    private static final Map localeCache = new HashMap();
    static Locale EN_Locale = forEN();

    public static Date epochAdjust(Date date) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return date;
        }
        Map map = localeCache;
        synchronized (map) {
            Long l2 = (Long) map.get(locale);
            if (l2 == null) {
                long time = new SimpleDateFormat("yyyyMMddHHmmssz").parse("19700101000000GMT+00:00").getTime();
                l2 = time == 0 ? ZERO : longValueOf(time);
                map.put(locale, l2);
            }
            if (l2 != ZERO) {
                return new Date(date.getTime() - l2.longValue());
            }
            return date;
        }
    }

    private static Locale forEN() {
        if ("en".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            return Locale.getDefault();
        }
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i2 = 0; i2 != availableLocales.length; i2++) {
            if ("en".equalsIgnoreCase(availableLocales[i2].getLanguage())) {
                return availableLocales[i2];
            }
        }
        return Locale.getDefault();
    }

    private static Long longValueOf(long j2) {
        return Long.valueOf(j2);
    }
}
