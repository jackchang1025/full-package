package p000;

import android.os.LocaleList;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z1 */
/* loaded from: classes.dex */
public abstract class AbstractC1501z1 {
    /* renamed from: a0 */
    public static LocaleList m215331a0(String str) {
        return LocaleList.forLanguageTags(str);
    }

    /* renamed from: a1 */
    public static void m215332a1(TextView textView, LocaleList localeList) {
        textView.setTextLocales(localeList);
    }
}
