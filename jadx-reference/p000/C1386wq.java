package p000;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wq */
/* loaded from: classes.dex */
public final class C1386wq extends b81 {

    /* renamed from: c2 */
    public final TextView f60961c2;

    /* renamed from: c3 */
    public final C1382wm f60962c3;

    /* renamed from: c4 */
    public boolean f60963c4 = true;

    public C1386wq(TextView textView) {
        this.f60961c2 = textView;
        this.f60962c3 = new C1382wm(textView);
    }

    @Override // p000.b81
    /* renamed from: b8 */
    public final InputFilter[] mo210603b8(InputFilter[] inputFilterArr) {
        if (!this.f60963c4) {
            SparseArray sparseArray = new SparseArray(1);
            for (int i = 0; i < inputFilterArr.length; i++) {
                InputFilter inputFilter = inputFilterArr[i];
                if (inputFilter instanceof C1382wm) {
                    sparseArray.put(i, inputFilter);
                }
            }
            if (sparseArray.size() == 0) {
                return inputFilterArr;
            }
            int length = inputFilterArr.length;
            InputFilter[] inputFilterArr2 = new InputFilter[inputFilterArr.length - sparseArray.size()];
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                if (sparseArray.indexOfKey(i3) < 0) {
                    inputFilterArr2[i2] = inputFilterArr[i3];
                    i2++;
                }
            }
            return inputFilterArr2;
        }
        int length2 = inputFilterArr.length;
        int i4 = 0;
        while (true) {
            C1382wm c1382wm = this.f60962c3;
            if (i4 >= length2) {
                InputFilter[] inputFilterArr3 = new InputFilter[inputFilterArr.length + 1];
                System.arraycopy(inputFilterArr, 0, inputFilterArr3, 0, length2);
                inputFilterArr3[length2] = c1382wm;
                return inputFilterArr3;
            }
            if (inputFilterArr[i4] == c1382wm) {
                return inputFilterArr;
            }
            i4++;
        }
    }

    @Override // p000.b81
    /* renamed from: d3 */
    public final boolean mo210604d3() {
        return this.f60963c4;
    }

    @Override // p000.b81
    /* renamed from: e6 */
    public final void mo210607e6(boolean z) {
        if (z) {
            TextView textView = this.f60961c2;
            textView.setTransformationMethod(mo210609f4(textView.getTransformationMethod()));
        }
    }

    @Override // p000.b81
    /* renamed from: e7 */
    public final void mo210608e7(boolean z) {
        this.f60963c4 = z;
        TextView textView = this.f60961c2;
        textView.setTransformationMethod(mo210609f4(textView.getTransformationMethod()));
        textView.setFilters(mo210603b8(textView.getFilters()));
    }

    @Override // p000.b81
    /* renamed from: f4 */
    public final TransformationMethod mo210609f4(TransformationMethod transformationMethod) {
        return this.f60963c4 ? ((transformationMethod instanceof C1390wu) || (transformationMethod instanceof PasswordTransformationMethod)) ? transformationMethod : new C1390wu(transformationMethod) : transformationMethod instanceof C1390wu ? ((C1390wu) transformationMethod).f60972a0 : transformationMethod;
    }
}
