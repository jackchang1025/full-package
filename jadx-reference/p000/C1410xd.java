package p000;

import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xd */
/* loaded from: classes2.dex */
public final class C1410xd {

    /* renamed from: a0 */
    public final /* synthetic */ C1415xf f61070a0;

    public C1410xd(C1415xf c1415xf) {
        this.f61070a0 = c1415xf;
    }

    /* renamed from: a0 */
    public final void m215155a0(TextInputLayout textInputLayout) {
        C1415xf c1415xf = this.f61070a0;
        C1409xc c1409xc = c1415xf.f61100c1;
        if (c1415xf.f61097b8 == textInputLayout.getEditText()) {
            return;
        }
        EditText editText = c1415xf.f61097b8;
        if (editText != null) {
            editText.removeTextChangedListener(c1409xc);
            if (c1415xf.f61097b8.getOnFocusChangeListener() == c1415xf.m215157a1().mo213192a4()) {
                c1415xf.f61097b8.setOnFocusChangeListener(null);
            }
        }
        EditText editText2 = textInputLayout.getEditText();
        c1415xf.f61097b8 = editText2;
        if (editText2 != null) {
            editText2.addTextChangedListener(c1409xc);
        }
        c1415xf.m215157a1().mo213195b1(c1415xf.f61097b8);
        c1415xf.m215164a8(c1415xf.m215157a1());
    }
}
