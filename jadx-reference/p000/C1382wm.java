package p000;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wm */
/* loaded from: classes.dex */
public final class C1382wm implements InputFilter {

    /* renamed from: a0 */
    public final TextView f60944a0;

    /* renamed from: a1 */
    public C1381wl f60945a1;

    public C1382wm(TextView textView) {
        this.f60944a0 = textView;
    }

    @Override // android.text.InputFilter
    public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        TextView textView = this.f60944a0;
        if (textView.isInEditMode()) {
            return charSequence;
        }
        int iM215059a1 = C1375wg.m215058a0().m215059a1();
        if (iM215059a1 != 0) {
            if (iM215059a1 == 1) {
                if ((i4 == 0 && i3 == 0 && spanned.length() == 0 && charSequence == textView.getText()) || charSequence == null) {
                    return charSequence;
                }
                if (i != 0 || i2 != charSequence.length()) {
                    charSequence = charSequence.subSequence(i, i2);
                }
                return C1375wg.m215058a0().m215062a4(charSequence, 0, charSequence.length());
            }
            if (iM215059a1 != 3) {
                return charSequence;
            }
        }
        C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
        if (this.f60945a1 == null) {
            this.f60945a1 = new C1381wl(textView, this);
        }
        c1375wgM215058a0.m215063a5(this.f60945a1);
        return charSequence;
    }
}
