package p000;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bi0 implements View.OnLayoutChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f45896a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f45897a1;

    public /* synthetic */ bi0(int i, Object obj) {
        this.f45896a0 = i;
        this.f45897a1 = obj;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        C0390ct c0390ct;
        switch (this.f45896a0) {
            case 0:
                ei0 ei0Var = (ei0) this.f45897a1;
                ImageView imageView = ei0Var.f56047b2;
                if (imageView.getVisibility() == 0 && (c0390ct = ei0Var.f56064c9) != null) {
                    Rect rect = new Rect();
                    imageView.getDrawingRect(rect);
                    c0390ct.setBounds(rect);
                    c0390ct.m212529a4(imageView, null);
                    break;
                }
                break;
            case 1:
                SearchView searchView = (SearchView) this.f45897a1;
                SearchView.SearchAutoComplete searchAutoComplete = searchView.f43999b5;
                View view2 = searchView.f44007c3;
                if (view2.getWidth() > 1) {
                    Resources resources = searchView.getContext().getResources();
                    int paddingLeft = searchView.f44001b7.getPaddingLeft();
                    Rect rect2 = new Rect();
                    boolean zM213156a0 = id1.m213156a0(searchView);
                    int dimensionPixelSize = searchView.f44022d8 ? resources.getDimensionPixelSize(R$dimen.abc_dropdownitem_text_padding_left) + resources.getDimensionPixelSize(R$dimen.abc_dropdownitem_icon_width) : 0;
                    searchAutoComplete.getDropDownBackground().getPadding(rect2);
                    searchAutoComplete.setDropDownHorizontalOffset(zM213156a0 ? -rect2.left : paddingLeft - (rect2.left + dimensionPixelSize));
                    searchAutoComplete.setDropDownWidth((((view2.getWidth() + rect2.left) + rect2.right) + dimensionPixelSize) - paddingLeft);
                    break;
                }
                break;
            default:
                j71 j71Var = (j71) this.f45897a1;
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                j71Var.f57300d4 = iArr[0];
                view.getWindowVisibleDisplayFrame(j71Var.f57294c8);
                break;
        }
    }
}
