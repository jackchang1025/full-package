package p000;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wl */
/* loaded from: classes.dex */
public final class C1381wl extends AbstractC1373we {

    /* renamed from: a0 */
    public final WeakReference f60942a0;

    /* renamed from: a1 */
    public final WeakReference f60943a1;

    public C1381wl(TextView textView, C1382wm c1382wm) {
        this.f60942a0 = new WeakReference(textView);
        this.f60943a1 = new WeakReference(c1382wm);
    }

    @Override // p000.AbstractC1373we
    /* renamed from: a1 */
    public final void mo215048a1() {
        InputFilter[] filters;
        int length;
        TextView textView = (TextView) this.f60942a0.get();
        InputFilter inputFilter = (InputFilter) this.f60943a1.get();
        if (inputFilter == null || textView == null || (filters = textView.getFilters()) == null) {
            return;
        }
        for (InputFilter inputFilter2 : filters) {
            if (inputFilter2 == inputFilter) {
                if (textView.isAttachedToWindow()) {
                    CharSequence text = textView.getText();
                    C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
                    if (text == null) {
                        length = 0;
                    } else {
                        c1375wgM215058a0.getClass();
                        length = text.length();
                    }
                    CharSequence charSequenceM215062a4 = c1375wgM215058a0.m215062a4(text, 0, length);
                    if (text == charSequenceM215062a4) {
                        return;
                    }
                    int selectionStart = Selection.getSelectionStart(charSequenceM215062a4);
                    int selectionEnd = Selection.getSelectionEnd(charSequenceM215062a4);
                    textView.setText(charSequenceM215062a4);
                    if (charSequenceM215062a4 instanceof Spannable) {
                        Spannable spannable = (Spannable) charSequenceM215062a4;
                        if (selectionStart >= 0 && selectionEnd >= 0) {
                            Selection.setSelection(spannable, selectionStart, selectionEnd);
                            return;
                        } else if (selectionStart >= 0) {
                            Selection.setSelection(spannable, selectionStart);
                            return;
                        } else {
                            if (selectionEnd >= 0) {
                                Selection.setSelection(spannable, selectionEnd);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                return;
            }
        }
    }
}
