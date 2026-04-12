package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R$styleable;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ll */
/* loaded from: classes.dex */
public final class C0824ll {

    /* renamed from: b4 */
    public static final SparseIntArray f58029b4;

    /* renamed from: a0 */
    public boolean f58030a0;

    /* renamed from: a1 */
    public float f58031a1;

    /* renamed from: a2 */
    public float f58032a2;

    /* renamed from: a3 */
    public float f58033a3;

    /* renamed from: a4 */
    public float f58034a4;

    /* renamed from: a5 */
    public float f58035a5;

    /* renamed from: a6 */
    public float f58036a6;

    /* renamed from: a7 */
    public float f58037a7;

    /* renamed from: a8 */
    public int f58038a8;

    /* renamed from: a9 */
    public float f58039a9;

    /* renamed from: b0 */
    public float f58040b0;

    /* renamed from: b1 */
    public float f58041b1;

    /* renamed from: b2 */
    public boolean f58042b2;

    /* renamed from: b3 */
    public float f58043b3;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f58029b4 = sparseIntArray;
        sparseIntArray.append(R$styleable.Transform_android_rotation, 1);
        sparseIntArray.append(R$styleable.Transform_android_rotationX, 2);
        sparseIntArray.append(R$styleable.Transform_android_rotationY, 3);
        sparseIntArray.append(R$styleable.Transform_android_scaleX, 4);
        sparseIntArray.append(R$styleable.Transform_android_scaleY, 5);
        sparseIntArray.append(R$styleable.Transform_android_transformPivotX, 6);
        sparseIntArray.append(R$styleable.Transform_android_transformPivotY, 7);
        sparseIntArray.append(R$styleable.Transform_android_translationX, 8);
        sparseIntArray.append(R$styleable.Transform_android_translationY, 9);
        sparseIntArray.append(R$styleable.Transform_android_translationZ, 10);
        sparseIntArray.append(R$styleable.Transform_android_elevation, 11);
        sparseIntArray.append(R$styleable.Transform_transformPivotTarget, 12);
    }

    /* renamed from: a0 */
    public final void m213856a0(C0824ll c0824ll) {
        this.f58030a0 = c0824ll.f58030a0;
        this.f58031a1 = c0824ll.f58031a1;
        this.f58032a2 = c0824ll.f58032a2;
        this.f58033a3 = c0824ll.f58033a3;
        this.f58034a4 = c0824ll.f58034a4;
        this.f58035a5 = c0824ll.f58035a5;
        this.f58036a6 = c0824ll.f58036a6;
        this.f58037a7 = c0824ll.f58037a7;
        this.f58038a8 = c0824ll.f58038a8;
        this.f58039a9 = c0824ll.f58039a9;
        this.f58040b0 = c0824ll.f58040b0;
        this.f58041b1 = c0824ll.f58041b1;
        this.f58042b2 = c0824ll.f58042b2;
        this.f58043b3 = c0824ll.f58043b3;
    }

    /* renamed from: a1 */
    public final void m213857a1(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Transform);
        this.f58030a0 = true;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            switch (f58029b4.get(index)) {
                case 1:
                    this.f58031a1 = typedArrayObtainStyledAttributes.getFloat(index, this.f58031a1);
                    break;
                case 2:
                    this.f58032a2 = typedArrayObtainStyledAttributes.getFloat(index, this.f58032a2);
                    break;
                case 3:
                    this.f58033a3 = typedArrayObtainStyledAttributes.getFloat(index, this.f58033a3);
                    break;
                case 4:
                    this.f58034a4 = typedArrayObtainStyledAttributes.getFloat(index, this.f58034a4);
                    break;
                case 5:
                    this.f58035a5 = typedArrayObtainStyledAttributes.getFloat(index, this.f58035a5);
                    break;
                case 6:
                    this.f58036a6 = typedArrayObtainStyledAttributes.getDimension(index, this.f58036a6);
                    break;
                case 7:
                    this.f58037a7 = typedArrayObtainStyledAttributes.getDimension(index, this.f58037a7);
                    break;
                case 8:
                    this.f58039a9 = typedArrayObtainStyledAttributes.getDimension(index, this.f58039a9);
                    break;
                case 9:
                    this.f58040b0 = typedArrayObtainStyledAttributes.getDimension(index, this.f58040b0);
                    break;
                case 10:
                    this.f58041b1 = typedArrayObtainStyledAttributes.getDimension(index, this.f58041b1);
                    break;
                case oe0.DEFAULT_M /* 11 */:
                    this.f58042b2 = true;
                    this.f58043b3 = typedArrayObtainStyledAttributes.getDimension(index, this.f58043b3);
                    break;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    this.f58038a8 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f58038a8);
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
