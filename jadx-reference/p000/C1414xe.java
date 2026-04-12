package p000;

import android.content.res.TypedArray;
import android.util.SparseArray;
import com.google.android.material.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xe */
/* loaded from: classes2.dex */
public final class C1414xe {

    /* renamed from: a0 */
    public final SparseArray f61073a0 = new SparseArray();

    /* renamed from: a1 */
    public final C1415xf f61074a1;

    /* renamed from: a2 */
    public final int f61075a2;

    /* renamed from: a3 */
    public final int f61076a3;

    public C1414xe(C1415xf c1415xf, pg1 pg1Var) {
        this.f61074a1 = c1415xf;
        int i = R$styleable.TextInputLayout_endIconDrawable;
        TypedArray typedArray = (TypedArray) pg1Var.f59230a2;
        this.f61075a2 = typedArray.getResourceId(i, 0);
        this.f61076a3 = typedArray.getResourceId(R$styleable.TextInputLayout_passwordToggleDrawable, 0);
    }
}
