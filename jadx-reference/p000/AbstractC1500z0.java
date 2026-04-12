package p000;

import android.graphics.drawable.Drawable;
import android.widget.TextView;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z0 */
/* loaded from: classes.dex */
public abstract class AbstractC1500z0 {
    /* renamed from: a0 */
    public static Drawable[] m215328a0(TextView textView) {
        return textView.getCompoundDrawablesRelative();
    }

    /* renamed from: a1 */
    public static void m215329a1(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    /* renamed from: a2 */
    public static void m215330a2(TextView textView, Locale locale) {
        textView.setTextLocale(locale);
    }
}
