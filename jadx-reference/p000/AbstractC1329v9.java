package p000;

import android.content.res.Configuration;
import android.os.LocaleList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v9 */
/* loaded from: classes.dex */
public abstract class AbstractC1329v9 {
    /* renamed from: a0 */
    public static void m214907a0(Configuration configuration, Configuration configuration2, Configuration configuration3) {
        LocaleList locales = configuration.getLocales();
        LocaleList locales2 = configuration2.getLocales();
        if (locales.equals(locales2)) {
            return;
        }
        configuration3.setLocales(locales2);
        configuration3.locale = configuration2.locale;
    }

    /* renamed from: a1 */
    public static dc0 m214908a1(Configuration configuration) {
        return dc0.m212582a0(configuration.getLocales().toLanguageTags());
    }

    /* renamed from: a2 */
    public static void m214909a2(dc0 dc0Var) {
        LocaleList.setDefault(LocaleList.forLanguageTags(dc0Var.f55691a0.f55969a0.toLanguageTags()));
    }

    /* renamed from: a3 */
    public static void m214910a3(Configuration configuration, dc0 dc0Var) {
        configuration.setLocales(LocaleList.forLanguageTags(dc0Var.f55691a0.f55969a0.toLanguageTags()));
    }
}
