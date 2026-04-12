package p000;

import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x3 */
/* loaded from: classes.dex */
public final class C1400x3 {

    /* renamed from: a0 */
    public final TextView f61010a0;

    /* renamed from: a1 */
    public final tg0 f61011a1;

    public C1400x3(TextView textView) {
        this.f61010a0 = textView;
        this.f61011a1 = new tg0(textView);
    }

    /* renamed from: a0 */
    public final InputFilter[] m215104a0(InputFilter[] inputFilterArr) {
        return ((b81) this.f61011a1.f60218a1).mo210603b8(inputFilterArr);
    }

    /* renamed from: a1 */
    public final void m215105a1(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = this.f61010a0.getContext().obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView, i, 0);
        try {
            boolean z = typedArrayObtainStyledAttributes.hasValue(R$styleable.AppCompatTextView_emojiCompatEnabled) ? typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTextView_emojiCompatEnabled, true) : true;
            typedArrayObtainStyledAttributes.recycle();
            m215107a3(z);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    /* renamed from: a2 */
    public final void m215106a2(boolean z) {
        ((b81) this.f61011a1.f60218a1).mo210607e6(z);
    }

    /* renamed from: a3 */
    public final void m215107a3(boolean z) {
        ((b81) this.f61011a1.f60218a1).mo210608e7(z);
    }
}
