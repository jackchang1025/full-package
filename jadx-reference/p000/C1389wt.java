package p000;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wt */
/* loaded from: classes.dex */
public final class C1389wt implements TextWatcher {

    /* renamed from: a0 */
    public final EditText f60969a0;

    /* renamed from: a1 */
    public C1388ws f60970a1;

    /* renamed from: a2 */
    public boolean f60971a2 = true;

    public C1389wt(EditText editText) {
        this.f60969a0 = editText;
    }

    /* renamed from: a0 */
    public static void m215089a0(EditText editText, int i) {
        int length;
        if (i == 1 && editText != null && editText.isAttachedToWindow()) {
            Editable editableText = editText.getEditableText();
            int selectionStart = Selection.getSelectionStart(editableText);
            int selectionEnd = Selection.getSelectionEnd(editableText);
            C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
            if (editableText == null) {
                length = 0;
            } else {
                c1375wgM215058a0.getClass();
                length = editableText.length();
            }
            c1375wgM215058a0.m215062a4(editableText, 0, length);
            if (selectionStart >= 0 && selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionStart, selectionEnd);
            } else if (selectionStart >= 0) {
                Selection.setSelection(editableText, selectionStart);
            } else if (selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionEnd);
            }
        }
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        EditText editText = this.f60969a0;
        if (editText.isInEditMode() || !this.f60971a2 || C1375wg.f60900a9 == null || i2 > i3 || !(charSequence instanceof Spannable)) {
            return;
        }
        int iM215059a1 = C1375wg.m215058a0().m215059a1();
        if (iM215059a1 != 0) {
            if (iM215059a1 == 1) {
                C1375wg.m215058a0().m215062a4((Spannable) charSequence, i, i3 + i);
                return;
            } else if (iM215059a1 != 3) {
                return;
            }
        }
        C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
        if (this.f60970a1 == null) {
            this.f60970a1 = new C1388ws(editText);
        }
        c1375wgM215058a0.m215063a5(this.f60970a1);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
