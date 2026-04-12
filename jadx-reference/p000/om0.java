package p000;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class om0 extends AbstractC1416xg {

    /* renamed from: a4 */
    public final int f58905a4;

    /* renamed from: a5 */
    public EditText f58906a5;

    /* renamed from: a6 */
    public final ViewOnClickListenerC1203s1 f58907a6;

    public om0(C1415xf c1415xf, int i) {
        super(c1415xf);
        this.f58905a4 = R$drawable.design_password_eye;
        this.f58907a6 = new ViewOnClickListenerC1203s1(6, this);
        if (i != 0) {
            this.f58905a4 = i;
        }
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a1 */
    public final void mo214231a1() {
        m215176b5();
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a2 */
    public final int mo213190a2() {
        return R$string.password_toggle_content_description;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a3 */
    public final int mo213191a3() {
        return this.f58905a4;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a5 */
    public final View.OnClickListener mo213193a5() {
        return this.f58907a6;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a9 */
    public final boolean mo214232a9() {
        return true;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b0 */
    public final boolean mo214233b0() {
        EditText editText = this.f58906a5;
        return !(editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod));
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b1 */
    public final void mo213195b1(EditText editText) {
        this.f58906a5 = editText;
        m215176b5();
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b6 */
    public final void mo213197b6() {
        EditText editText = this.f58906a5;
        if (editText != null) {
            if (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224) {
                this.f58906a5.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b7 */
    public final void mo213198b7() {
        EditText editText = this.f58906a5;
        if (editText != null) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
