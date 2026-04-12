package p000;

import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z2 */
/* loaded from: classes.dex */
public abstract class AbstractC1502z2 {
    /* renamed from: a0 */
    public static int m215334a0(TextView textView) {
        return textView.getAutoSizeStepGranularity();
    }

    /* renamed from: a1 */
    public static void m215335a1(TextView textView, int i, int i2, int i3, int i4) {
        textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    /* renamed from: a2 */
    public static void m215336a2(TextView textView, int[] iArr, int i) {
        textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    /* renamed from: a3 */
    public static boolean m215337a3(TextView textView, String str) {
        return textView.setFontVariationSettings(str);
    }
}
