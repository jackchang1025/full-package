package p000;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class c61 {
    /* renamed from: a0 */
    public static Drawable[] m210762a0(TextView textView) {
        return textView.getCompoundDrawablesRelative();
    }

    /* renamed from: a1 */
    public static int m210763a1(View view) {
        return view.getLayoutDirection();
    }

    /* renamed from: a2 */
    public static int m210764a2(View view) {
        return view.getTextDirection();
    }

    /* renamed from: a3 */
    public static Locale m210765a3(TextView textView) {
        return textView.getTextLocale();
    }

    /* renamed from: a4 */
    public static void m210766a4(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    /* renamed from: a5 */
    public static void m210767a5(TextView textView, int i, int i2, int i3, int i4) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
    }

    /* renamed from: a6 */
    public static void m210768a6(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    /* renamed from: a7 */
    public static void m210769a7(View view, int i) {
        view.setTextDirection(i);
    }
}
