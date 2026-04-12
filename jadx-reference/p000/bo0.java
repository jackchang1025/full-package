package p000;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bo0 {

    /* renamed from: a0 */
    public final TextPaint f45987a0;

    /* renamed from: a1 */
    public final TextDirectionHeuristic f45988a1;

    /* renamed from: a2 */
    public final int f45989a2;

    /* renamed from: a3 */
    public final int f45990a3;

    public bo0(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            AbstractC0709j8.m213229b0(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build();
        }
        this.f45987a0 = textPaint;
        this.f45988a1 = textDirectionHeuristic;
        this.f45989a2 = i;
        this.f45990a3 = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof bo0)) {
            return false;
        }
        bo0 bo0Var = (bo0) obj;
        if (this.f45989a2 != bo0Var.f45989a2 || this.f45990a3 != bo0Var.f45990a3) {
            return false;
        }
        TextPaint textPaint = this.f45987a0;
        float textSize = textPaint.getTextSize();
        TextPaint textPaint2 = bo0Var.f45987a0;
        if (textSize != textPaint2.getTextSize() || textPaint.getTextScaleX() != textPaint2.getTextScaleX() || textPaint.getTextSkewX() != textPaint2.getTextSkewX() || textPaint.getLetterSpacing() != textPaint2.getLetterSpacing() || !TextUtils.equals(textPaint.getFontFeatureSettings(), textPaint2.getFontFeatureSettings()) || textPaint.getFlags() != textPaint2.getFlags() || !textPaint.getTextLocales().equals(textPaint2.getTextLocales())) {
            return false;
        }
        if (textPaint.getTypeface() == null) {
            if (textPaint2.getTypeface() != null) {
                return false;
            }
        } else if (!textPaint.getTypeface().equals(textPaint2.getTypeface())) {
            return false;
        }
        return this.f45988a1 == bo0Var.f45988a1;
    }

    public final int hashCode() {
        TextPaint textPaint = this.f45987a0;
        return tk0.m214760a1(Float.valueOf(textPaint.getTextSize()), Float.valueOf(textPaint.getTextScaleX()), Float.valueOf(textPaint.getTextSkewX()), Float.valueOf(textPaint.getLetterSpacing()), Integer.valueOf(textPaint.getFlags()), textPaint.getTextLocales(), textPaint.getTypeface(), Boolean.valueOf(textPaint.isElegantTextHeight()), this.f45988a1, Integer.valueOf(this.f45989a2), Integer.valueOf(this.f45990a3));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        StringBuilder sb2 = new StringBuilder("textSize=");
        TextPaint textPaint = this.f45987a0;
        sb2.append(textPaint.getTextSize());
        sb.append(sb2.toString());
        sb.append(", textScaleX=" + textPaint.getTextScaleX());
        sb.append(", textSkewX=" + textPaint.getTextSkewX());
        int i = Build.VERSION.SDK_INT;
        sb.append(", letterSpacing=" + textPaint.getLetterSpacing());
        sb.append(", elegantTextHeight=" + textPaint.isElegantTextHeight());
        sb.append(", textLocale=" + textPaint.getTextLocales());
        sb.append(", typeface=" + textPaint.getTypeface());
        if (i >= 26) {
            sb.append(", variationSettings=" + textPaint.getFontVariationSettings());
        }
        sb.append(", textDir=" + this.f45988a1);
        sb.append(", breakStrategy=" + this.f45989a2);
        sb.append(", hyphenationFrequency=" + this.f45990a3);
        sb.append("}");
        return sb.toString();
    }

    public bo0(PrecomputedText.Params params) {
        this.f45987a0 = params.getTextPaint();
        this.f45988a1 = params.getTextDirection();
        this.f45989a2 = params.getBreakStrategy();
        this.f45990a3 = params.getHyphenationFrequency();
    }
}
