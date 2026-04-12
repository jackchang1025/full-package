package p000;

import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import java.util.stream.IntStream;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class z81 implements Spannable {

    /* renamed from: a0 */
    public boolean f61468a0 = false;

    /* renamed from: a1 */
    public Spannable f61469a1;

    public z81(Spannable spannable) {
        this.f61469a1 = spannable;
    }

    /* renamed from: a0 */
    public final void m215380a0() {
        Spannable spannable = this.f61469a1;
        if (!this.f61468a0) {
            if ((Build.VERSION.SDK_INT < 28 ? new fh0(17) : new y81(17)).mo212811a4(spannable)) {
                this.f61469a1 = new SpannableString(spannable);
            }
        }
        this.f61468a0 = true;
    }

    @Override // java.lang.CharSequence
    public final char charAt(int i) {
        return this.f61469a1.charAt(i);
    }

    @Override // java.lang.CharSequence
    public final IntStream chars() {
        return this.f61469a1.chars();
    }

    @Override // java.lang.CharSequence
    public final IntStream codePoints() {
        return this.f61469a1.codePoints();
    }

    @Override // android.text.Spanned
    public final int getSpanEnd(Object obj) {
        return this.f61469a1.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public final int getSpanFlags(Object obj) {
        return this.f61469a1.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public final int getSpanStart(Object obj) {
        return this.f61469a1.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public final Object[] getSpans(int i, int i2, Class cls) {
        return this.f61469a1.getSpans(i, i2, cls);
    }

    @Override // java.lang.CharSequence
    public final int length() {
        return this.f61469a1.length();
    }

    @Override // android.text.Spanned
    public final int nextSpanTransition(int i, int i2, Class cls) {
        return this.f61469a1.nextSpanTransition(i, i2, cls);
    }

    @Override // android.text.Spannable
    public final void removeSpan(Object obj) {
        m215380a0();
        this.f61469a1.removeSpan(obj);
    }

    @Override // android.text.Spannable
    public final void setSpan(Object obj, int i, int i2, int i3) {
        m215380a0();
        this.f61469a1.setSpan(obj, i, i2, i3);
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return this.f61469a1.subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return this.f61469a1.toString();
    }

    public z81(CharSequence charSequence) {
        this.f61469a1 = new SpannableString(charSequence);
    }
}
