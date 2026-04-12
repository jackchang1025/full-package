package p000;

import android.icu.text.DecimalFormatSymbols;
import android.text.PrecomputedText;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class f61 {
    /* renamed from: a0 */
    public static String[] m212751a0(DecimalFormatSymbols decimalFormatSymbols) {
        return decimalFormatSymbols.getDigitStrings();
    }

    /* renamed from: a1 */
    public static PrecomputedText.Params m212752a1(TextView textView) {
        return textView.getTextMetricsParams();
    }

    /* renamed from: a2 */
    public static void m212753a2(TextView textView, int i) {
        textView.setFirstBaselineToTopHeight(i);
    }
}
