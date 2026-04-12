package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.AppCompatCheckedTextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v1 */
/* loaded from: classes.dex */
public final class C1321v1 {

    /* renamed from: a0 */
    public ColorStateList f60541a0 = null;

    /* renamed from: a1 */
    public PorterDuff.Mode f60542a1 = null;

    /* renamed from: a2 */
    public boolean f60543a2 = false;

    /* renamed from: a3 */
    public boolean f60544a3 = false;

    /* renamed from: a4 */
    public boolean f60545a4;

    /* renamed from: a5 */
    public final TextView f60546a5;

    public /* synthetic */ C1321v1(TextView textView) {
        this.f60546a5 = textView;
    }

    /* renamed from: a0 */
    public void m214890a0() {
        CompoundButton compoundButton = (CompoundButton) this.f60546a5;
        Drawable drawableM213496a0 = AbstractC0755ke.m213496a0(compoundButton);
        if (drawableM213496a0 != null) {
            if (this.f60543a2 || this.f60544a3) {
                Drawable drawableMutate = drawableM213496a0.mutate();
                if (this.f60543a2) {
                    AbstractC1270tr.m214774a7(drawableMutate, this.f60541a0);
                }
                if (this.f60544a3) {
                    AbstractC1270tr.m214775a8(drawableMutate, this.f60542a1);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(compoundButton.getDrawableState());
                }
                compoundButton.setButtonDrawable(drawableMutate);
            }
        }
    }

    /* renamed from: a1 */
    public void m214891a1() {
        AppCompatCheckedTextView appCompatCheckedTextView = (AppCompatCheckedTextView) this.f60546a5;
        Drawable checkMarkDrawable = appCompatCheckedTextView.getCheckMarkDrawable();
        if (checkMarkDrawable != null) {
            if (this.f60543a2 || this.f60544a3) {
                Drawable drawableMutate = checkMarkDrawable.mutate();
                if (this.f60543a2) {
                    AbstractC1270tr.m214774a7(drawableMutate, this.f60541a0);
                }
                if (this.f60544a3) {
                    AbstractC1270tr.m214775a8(drawableMutate, this.f60542a1);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(appCompatCheckedTextView.getDrawableState());
                }
                appCompatCheckedTextView.setCheckMarkDrawable(drawableMutate);
            }
        }
    }

    /* renamed from: a2 */
    public void m214892a2(AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        CompoundButton compoundButton = (CompoundButton) this.f60546a5;
        pg1 pg1VarM214255d2 = pg1.m214255d2(compoundButton.getContext(), attributeSet, R$styleable.CompoundButton, i);
        TypedArray typedArray = (TypedArray) pg1VarM214255d2.f59230a2;
        xa1.m215151b3(compoundButton, compoundButton.getContext(), R$styleable.CompoundButton, attributeSet, (TypedArray) pg1VarM214255d2.f59230a2, i);
        try {
            if (typedArray.hasValue(R$styleable.CompoundButton_buttonCompat) && (resourceId2 = typedArray.getResourceId(R$styleable.CompoundButton_buttonCompat, 0)) != 0) {
                try {
                    compoundButton.setButtonDrawable(b81.m210576b7(compoundButton.getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                }
            } else if (typedArray.hasValue(R$styleable.CompoundButton_android_button) && (resourceId = typedArray.getResourceId(R$styleable.CompoundButton_android_button, 0)) != 0) {
                compoundButton.setButtonDrawable(b81.m210576b7(compoundButton.getContext(), resourceId));
            }
            if (typedArray.hasValue(R$styleable.CompoundButton_buttonTint)) {
                AbstractC0754kd.m213491a2(compoundButton, pg1VarM214255d2.m214276c0(R$styleable.CompoundButton_buttonTint));
            }
            if (typedArray.hasValue(R$styleable.CompoundButton_buttonTintMode)) {
                AbstractC0754kd.m213492a3(compoundButton, AbstractC1274tv.m214792a2(typedArray.getInt(R$styleable.CompoundButton_buttonTintMode, -1), null));
            }
            pg1VarM214255d2.m214288d4();
        } catch (Throwable th) {
            pg1VarM214255d2.m214288d4();
            throw th;
        }
    }
}
