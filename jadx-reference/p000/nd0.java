package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class nd0 extends ArrayAdapter {

    /* renamed from: a0 */
    public ColorStateList f58503a0;

    /* renamed from: a1 */
    public ColorStateList f58504a1;

    /* renamed from: a2 */
    public final /* synthetic */ MaterialAutoCompleteTextView f58505a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public nd0(MaterialAutoCompleteTextView materialAutoCompleteTextView, Context context, int i, String[] strArr) {
        super(context, i, strArr);
        this.f58505a2 = materialAutoCompleteTextView;
        m214072a0();
    }

    /* renamed from: a0 */
    public final void m214072a0() {
        ColorStateList colorStateList;
        MaterialAutoCompleteTextView materialAutoCompleteTextView = this.f58505a2;
        ColorStateList colorStateList2 = materialAutoCompleteTextView.f49928b0;
        ColorStateList colorStateList3 = null;
        if (colorStateList2 != null) {
            int[] iArr = {R.attr.state_pressed};
            colorStateList = new ColorStateList(new int[][]{iArr, new int[0]}, new int[]{colorStateList2.getColorForState(iArr, 0), 0});
        } else {
            colorStateList = null;
        }
        this.f58504a1 = colorStateList;
        if (materialAutoCompleteTextView.f49927a9 != 0 && materialAutoCompleteTextView.f49928b0 != null) {
            int[] iArr2 = {R.attr.state_hovered, -16842919};
            int[] iArr3 = {R.attr.state_selected, -16842919};
            colorStateList3 = new ColorStateList(new int[][]{iArr3, iArr2, new int[0]}, new int[]{AbstractC0724jn.m213332a2(materialAutoCompleteTextView.f49928b0.getColorForState(iArr3, 0), materialAutoCompleteTextView.f49927a9), AbstractC0724jn.m213332a2(materialAutoCompleteTextView.f49928b0.getColorForState(iArr2, 0), materialAutoCompleteTextView.f49927a9), materialAutoCompleteTextView.f49927a9});
        }
        this.f58503a0 = colorStateList3;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        if (view2 instanceof TextView) {
            TextView textView = (TextView) view2;
            MaterialAutoCompleteTextView materialAutoCompleteTextView = this.f58505a2;
            Drawable rippleDrawable = null;
            if (materialAutoCompleteTextView.getText().toString().contentEquals(textView.getText()) && materialAutoCompleteTextView.f49927a9 != 0) {
                ColorDrawable colorDrawable = new ColorDrawable(materialAutoCompleteTextView.f49927a9);
                if (this.f58504a1 != null) {
                    AbstractC1270tr.m214774a7(colorDrawable, this.f58503a0);
                    rippleDrawable = new RippleDrawable(this.f58504a1, colorDrawable, null);
                } else {
                    rippleDrawable = colorDrawable;
                }
            }
            WeakHashMap weakHashMap = xa1.f61054a0;
            fa1.m212779b6(textView, rippleDrawable);
        }
        return view2;
    }
}
