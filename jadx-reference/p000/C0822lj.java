package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lj */
/* loaded from: classes.dex */
public final class C0822lj {

    /* renamed from: b3 */
    public static final SparseIntArray f58006b3;

    /* renamed from: a0 */
    public boolean f58007a0;

    /* renamed from: a1 */
    public int f58008a1;

    /* renamed from: a2 */
    public int f58009a2;

    /* renamed from: a3 */
    public String f58010a3;

    /* renamed from: a4 */
    public int f58011a4;

    /* renamed from: a5 */
    public int f58012a5;

    /* renamed from: a6 */
    public float f58013a6;

    /* renamed from: a7 */
    public float f58014a7;

    /* renamed from: a8 */
    public float f58015a8;

    /* renamed from: a9 */
    public int f58016a9;

    /* renamed from: b0 */
    public String f58017b0;

    /* renamed from: b1 */
    public int f58018b1;

    /* renamed from: b2 */
    public int f58019b2;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f58006b3 = sparseIntArray;
        sparseIntArray.append(R$styleable.Motion_motionPathRotate, 1);
        sparseIntArray.append(R$styleable.Motion_pathMotionArc, 2);
        sparseIntArray.append(R$styleable.Motion_transitionEasing, 3);
        sparseIntArray.append(R$styleable.Motion_drawPath, 4);
        sparseIntArray.append(R$styleable.Motion_animateRelativeTo, 5);
        sparseIntArray.append(R$styleable.Motion_animateCircleAngleTo, 6);
        sparseIntArray.append(R$styleable.Motion_motionStagger, 7);
        sparseIntArray.append(R$styleable.Motion_quantizeMotionSteps, 8);
        sparseIntArray.append(R$styleable.Motion_quantizeMotionPhase, 9);
        sparseIntArray.append(R$styleable.Motion_quantizeMotionInterpolator, 10);
    }

    /* renamed from: a0 */
    public final void m213851a0(C0822lj c0822lj) {
        this.f58007a0 = c0822lj.f58007a0;
        this.f58008a1 = c0822lj.f58008a1;
        this.f58010a3 = c0822lj.f58010a3;
        this.f58011a4 = c0822lj.f58011a4;
        this.f58012a5 = c0822lj.f58012a5;
        this.f58014a7 = c0822lj.f58014a7;
        this.f58013a6 = c0822lj.f58013a6;
    }

    /* renamed from: a1 */
    public final void m213852a1(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Motion);
        this.f58007a0 = true;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            switch (f58006b3.get(index)) {
                case 1:
                    this.f58014a7 = typedArrayObtainStyledAttributes.getFloat(index, this.f58014a7);
                    break;
                case 2:
                    this.f58011a4 = typedArrayObtainStyledAttributes.getInt(index, this.f58011a4);
                    break;
                case 3:
                    if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                        this.f58010a3 = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.f58010a3 = C1347vr.f60676a3[typedArrayObtainStyledAttributes.getInteger(index, 0)];
                        break;
                    }
                case 4:
                    this.f58012a5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    break;
                case 5:
                    this.f58008a1 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f58008a1);
                    break;
                case 6:
                    this.f58009a2 = typedArrayObtainStyledAttributes.getInteger(index, this.f58009a2);
                    break;
                case 7:
                    this.f58013a6 = typedArrayObtainStyledAttributes.getFloat(index, this.f58013a6);
                    break;
                case 8:
                    this.f58016a9 = typedArrayObtainStyledAttributes.getInteger(index, this.f58016a9);
                    break;
                case 9:
                    this.f58015a8 = typedArrayObtainStyledAttributes.getFloat(index, this.f58015a8);
                    break;
                case 10:
                    int i2 = typedArrayObtainStyledAttributes.peekValue(index).type;
                    if (i2 == 1) {
                        int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.f58019b2 = resourceId;
                        if (resourceId != -1) {
                            this.f58018b1 = -2;
                            break;
                        } else {
                            break;
                        }
                    } else if (i2 == 3) {
                        String string = typedArrayObtainStyledAttributes.getString(index);
                        this.f58017b0 = string;
                        if (string.indexOf("/") > 0) {
                            this.f58019b2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                            this.f58018b1 = -2;
                            break;
                        } else {
                            this.f58018b1 = -1;
                            break;
                        }
                    } else {
                        this.f58018b1 = typedArrayObtainStyledAttributes.getInteger(index, this.f58019b2);
                        break;
                    }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
