package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class d61 {
    /* renamed from: a0 */
    public static int m212550a0(TextView textView) {
        return textView.getBreakStrategy();
    }

    /* renamed from: a1 */
    public static ColorStateList m212551a1(TextView textView) {
        return textView.getCompoundDrawableTintList();
    }

    /* renamed from: a2 */
    public static PorterDuff.Mode m212552a2(TextView textView) {
        return textView.getCompoundDrawableTintMode();
    }

    /* renamed from: a3 */
    public static int m212553a3(TextView textView) {
        return textView.getHyphenationFrequency();
    }

    /* renamed from: a4 */
    public static void m212554a4(TextView textView, int i) {
        textView.setBreakStrategy(i);
    }

    /* renamed from: a5 */
    public static void m212555a5(TextView textView, ColorStateList colorStateList) {
        textView.setCompoundDrawableTintList(colorStateList);
    }

    /* renamed from: a6 */
    public static void m212556a6(TextView textView, PorterDuff.Mode mode) {
        textView.setCompoundDrawableTintMode(mode);
    }

    /* renamed from: a7 */
    public static void m212557a7(TextView textView, int i) {
        textView.setHyphenationFrequency(i);
    }
}
