package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R$styleable;
import java.util.HashSet;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class v80 extends k80 {

    /* renamed from: a4 */
    public int f60599a4 = -1;

    /* renamed from: a5 */
    public String f60600a5 = null;

    /* renamed from: a6 */
    public int f60601a6 = -1;

    /* renamed from: a7 */
    public int f60602a7 = 0;

    /* renamed from: a8 */
    public float f60603a8 = Float.NaN;

    /* renamed from: a9 */
    public float f60604a9 = Float.NaN;

    /* renamed from: b0 */
    public float f60605b0 = Float.NaN;

    /* renamed from: b1 */
    public float f60606b1 = Float.NaN;

    /* renamed from: b2 */
    public int f60607b2 = 0;

    @Override // p000.k80
    /* renamed from: a0 */
    public final k80 clone() {
        v80 v80Var = new v80();
        v80Var.f57482a0 = this.f57482a0;
        v80Var.f57483a1 = this.f57483a1;
        v80Var.f57484a2 = this.f57484a2;
        v80Var.f57485a3 = this.f57485a3;
        v80Var.f60600a5 = this.f60600a5;
        v80Var.f60601a6 = this.f60601a6;
        v80Var.f60602a7 = this.f60602a7;
        v80Var.f60603a8 = this.f60603a8;
        v80Var.f60604a9 = Float.NaN;
        v80Var.f60605b0 = this.f60605b0;
        v80Var.f60606b1 = this.f60606b1;
        return v80Var;
    }

    @Override // p000.k80
    /* renamed from: a2 */
    public final void mo213474a2(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyPosition);
        SparseIntArray sparseIntArray = u80.f60341a0;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = u80.f60341a0;
            switch (sparseIntArray2.get(index)) {
                case 1:
                    if (MotionLayout.f44523i2) {
                        int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f57483a1);
                        this.f57483a1 = resourceId;
                        if (resourceId == -1) {
                            this.f57484a2 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        } else {
                            break;
                        }
                    } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                        this.f57484a2 = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.f57483a1 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57483a1);
                        break;
                    }
                case 2:
                    this.f57482a0 = typedArrayObtainStyledAttributes.getInt(index, this.f57482a0);
                    break;
                case 3:
                    if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                        this.f60600a5 = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.f60600a5 = C1347vr.f60676a3[typedArrayObtainStyledAttributes.getInteger(index, 0)];
                        break;
                    }
                case 4:
                    this.f60599a4 = typedArrayObtainStyledAttributes.getInteger(index, this.f60599a4);
                    break;
                case 5:
                    this.f60602a7 = typedArrayObtainStyledAttributes.getInt(index, this.f60602a7);
                    break;
                case 6:
                    this.f60605b0 = typedArrayObtainStyledAttributes.getFloat(index, this.f60605b0);
                    break;
                case 7:
                    this.f60606b1 = typedArrayObtainStyledAttributes.getFloat(index, this.f60606b1);
                    break;
                case 8:
                    float f = typedArrayObtainStyledAttributes.getFloat(index, this.f60604a9);
                    this.f60603a8 = f;
                    this.f60604a9 = f;
                    break;
                case 9:
                    this.f60607b2 = typedArrayObtainStyledAttributes.getInt(index, this.f60607b2);
                    break;
                case 10:
                    this.f60601a6 = typedArrayObtainStyledAttributes.getInt(index, this.f60601a6);
                    break;
                case oe0.DEFAULT_M /* 11 */:
                    this.f60603a8 = typedArrayObtainStyledAttributes.getFloat(index, this.f60603a8);
                    break;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    this.f60604a9 = typedArrayObtainStyledAttributes.getFloat(index, this.f60604a9);
                    break;
                default:
                    Integer.toHexString(index);
                    sparseIntArray2.get(index);
                    break;
            }
        }
    }

    /* renamed from: a5 */
    public final void m214906a5(Object obj, String str) {
        switch (str) {
            case "transitionEasing":
                this.f60600a5 = obj.toString();
                break;
            case "percentWidth":
                this.f60603a8 = k80.m213471a4((Number) obj);
                break;
            case "percentHeight":
                this.f60604a9 = k80.m213471a4((Number) obj);
                break;
            case "drawPath":
                Number number = (Number) obj;
                this.f60602a7 = number instanceof Integer ? ((Integer) number).intValue() : Integer.parseInt(number.toString());
                break;
            case "sizePercent":
                float fM213471a4 = k80.m213471a4((Number) obj);
                this.f60603a8 = fM213471a4;
                this.f60604a9 = fM213471a4;
                break;
            case "percentX":
                this.f60605b0 = k80.m213471a4((Number) obj);
                break;
            case "percentY":
                this.f60606b1 = k80.m213471a4((Number) obj);
                break;
        }
    }

    @Override // p000.k80
    /* renamed from: a1 */
    public final void mo213473a1(HashSet hashSet) {
    }
}
