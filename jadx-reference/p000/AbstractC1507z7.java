package p000;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z7 */
/* loaded from: classes.dex */
public abstract class AbstractC1507z7 {
    /* renamed from: a0 */
    public static StaticLayout m215374a0(CharSequence charSequence, Layout.Alignment alignment, int i, TextView textView, TextPaint textPaint) {
        return new StaticLayout(charSequence, textPaint, i, alignment, textView.getLineSpacingMultiplier(), textView.getLineSpacingExtra(), textView.getIncludeFontPadding());
    }

    /* renamed from: a1 */
    public static int m215375a1(TextView textView) {
        return textView.getMaxLines();
    }
}
