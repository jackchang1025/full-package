package p000;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;
import android.graphics.Rect;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zu */
/* loaded from: classes2.dex */
public final class C1530zu implements TypeEvaluator {

    /* renamed from: a0 */
    public final /* synthetic */ int f61587a0 = 0;

    /* renamed from: a1 */
    public Object f61588a1;

    public /* synthetic */ C1530zu() {
    }

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        switch (this.f61587a0) {
            case 0:
                float fFloatValue = ((FloatEvaluator) this.f61588a1).evaluate(f, (Number) obj, (Number) obj2).floatValue();
                if (fFloatValue < 0.1f) {
                    fFloatValue = 0.0f;
                }
                return Float.valueOf(fFloatValue);
            default:
                Rect rect = (Rect) obj;
                Rect rect2 = (Rect) obj2;
                int i = rect.left + ((int) ((rect2.left - r0) * f));
                int i2 = rect.top + ((int) ((rect2.top - r1) * f));
                int i3 = rect.right + ((int) ((rect2.right - r2) * f));
                int i4 = rect.bottom + ((int) ((rect2.bottom - r6) * f));
                Rect rect3 = (Rect) this.f61588a1;
                rect3.set(i, i2, i3, i4);
                return rect3;
        }
    }

    public C1530zu(Rect rect) {
        this.f61588a1 = rect;
    }
}
