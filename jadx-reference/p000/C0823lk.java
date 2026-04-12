package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lk */
/* loaded from: classes.dex */
public final class C0823lk {

    /* renamed from: a0 */
    public boolean f58024a0;

    /* renamed from: a1 */
    public int f58025a1;

    /* renamed from: a2 */
    public int f58026a2;

    /* renamed from: a3 */
    public float f58027a3;

    /* renamed from: a4 */
    public float f58028a4;

    /* renamed from: a0 */
    public final void m213853a0(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PropertySet);
        this.f58024a0 = true;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.PropertySet_android_alpha) {
                this.f58027a3 = typedArrayObtainStyledAttributes.getFloat(index, this.f58027a3);
            } else if (index == R$styleable.PropertySet_android_visibility) {
                int i2 = typedArrayObtainStyledAttributes.getInt(index, this.f58025a1);
                this.f58025a1 = i2;
                this.f58025a1 = C0825lm.f58044a6[i2];
            } else if (index == R$styleable.PropertySet_visibilityMode) {
                this.f58026a2 = typedArrayObtainStyledAttributes.getInt(index, this.f58026a2);
            } else if (index == R$styleable.PropertySet_motionProgress) {
                this.f58028a4 = typedArrayObtainStyledAttributes.getFloat(index, this.f58028a4);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
