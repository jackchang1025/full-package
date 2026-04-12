package p000;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import java.nio.ByteBuffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class j81 extends ReplacementSpan {

    /* renamed from: a1 */
    public final C1384wo f57306a1;

    /* renamed from: a0 */
    public final Paint.FontMetricsInt f57305a0 = new Paint.FontMetricsInt();

    /* renamed from: a2 */
    public float f57307a2 = 1.0f;

    public j81(C1384wo c1384wo) {
        b81.m210568a8(c1384wo, "metadata cannot be null");
        this.f57306a1 = c1384wo;
    }

    @Override // android.text.style.ReplacementSpan
    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        C1375wg.m215058a0().getClass();
        C1384wo c1384wo = this.f57306a1;
        x31 x31Var = c1384wo.f60953a1;
        Typeface typeface = (Typeface) x31Var.f61015a3;
        Typeface typeface2 = paint.getTypeface();
        paint.setTypeface(typeface);
        canvas.drawText((char[]) x31Var.f61013a1, c1384wo.f60952a0 * 2, 2, f, i4, paint);
        paint.setTypeface(typeface2);
    }

    @Override // android.text.style.ReplacementSpan
    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Paint.FontMetricsInt fontMetricsInt2 = this.f57305a0;
        paint.getFontMetricsInt(fontMetricsInt2);
        float fAbs = Math.abs(fontMetricsInt2.descent - fontMetricsInt2.ascent) * 1.0f;
        C1384wo c1384wo = this.f57306a1;
        this.f57307a2 = fAbs / (c1384wo.m215084a1().m215362a0(14) != 0 ? ((ByteBuffer) r8.f61458a3).getShort(r1 + r8.f61455a0) : (short) 0);
        yf0 yf0VarM215084a1 = c1384wo.m215084a1();
        int iM215362a0 = yf0VarM215084a1.m215362a0(14);
        if (iM215362a0 != 0) {
            ((ByteBuffer) yf0VarM215084a1.f61458a3).getShort(iM215362a0 + yf0VarM215084a1.f61455a0);
        }
        short s = (short) ((c1384wo.m215084a1().m215362a0(12) != 0 ? ((ByteBuffer) r5.f61458a3).getShort(r7 + r5.f61455a0) : (short) 0) * this.f57307a2);
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = fontMetricsInt2.ascent;
            fontMetricsInt.descent = fontMetricsInt2.descent;
            fontMetricsInt.top = fontMetricsInt2.top;
            fontMetricsInt.bottom = fontMetricsInt2.bottom;
        }
        return s;
    }
}
