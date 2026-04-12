package p000;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class a21 {

    /* renamed from: a0 */
    public CharSequence f19a0;

    /* renamed from: a1 */
    public final TextPaint f20a1;

    /* renamed from: a2 */
    public final int f21a2;

    /* renamed from: a3 */
    public int f22a3;

    /* renamed from: b0 */
    public boolean f29b0;

    /* renamed from: a4 */
    public Layout.Alignment f23a4 = Layout.Alignment.ALIGN_NORMAL;

    /* renamed from: a5 */
    public int f24a5 = Integer.MAX_VALUE;

    /* renamed from: a6 */
    public float f25a6 = 0.0f;

    /* renamed from: a7 */
    public float f26a7 = 1.0f;

    /* renamed from: a8 */
    public int f27a8 = 1;

    /* renamed from: a9 */
    public boolean f28a9 = true;

    /* renamed from: b1 */
    public TextUtils.TruncateAt f30b1 = null;

    public a21(CharSequence charSequence, TextPaint textPaint, int i) {
        this.f19a0 = charSequence;
        this.f20a1 = textPaint;
        this.f21a2 = i;
        this.f22a3 = charSequence.length();
    }

    /* renamed from: a0 */
    public final StaticLayout m49a0() {
        if (this.f19a0 == null) {
            this.f19a0 = "";
        }
        int iMax = Math.max(0, this.f21a2);
        CharSequence charSequenceEllipsize = this.f19a0;
        int i = this.f24a5;
        TextPaint textPaint = this.f20a1;
        if (i == 1) {
            charSequenceEllipsize = TextUtils.ellipsize(charSequenceEllipsize, textPaint, iMax, this.f30b1);
        }
        int iMin = Math.min(charSequenceEllipsize.length(), this.f22a3);
        this.f22a3 = iMin;
        if (this.f29b0 && this.f24a5 == 1) {
            this.f23a4 = Layout.Alignment.ALIGN_OPPOSITE;
        }
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(charSequenceEllipsize, 0, iMin, textPaint, iMax);
        builderObtain.setAlignment(this.f23a4);
        builderObtain.setIncludePad(this.f28a9);
        builderObtain.setTextDirection(this.f29b0 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        TextUtils.TruncateAt truncateAt = this.f30b1;
        if (truncateAt != null) {
            builderObtain.setEllipsize(truncateAt);
        }
        builderObtain.setMaxLines(this.f24a5);
        float f = this.f25a6;
        if (f != 0.0f || this.f26a7 != 1.0f) {
            builderObtain.setLineSpacing(f, this.f26a7);
        }
        if (this.f24a5 > 1) {
            builderObtain.setHyphenationFrequency(this.f27a8);
        }
        return builderObtain.build();
    }
}
