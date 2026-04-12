package p000;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.R$layout;
import com.google.android.material.internal.NavigationMenuItemView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mi0 extends gq0 {

    /* renamed from: a2 */
    public final ArrayList f58374a2 = new ArrayList();

    /* renamed from: a3 */
    public ff0 f58375a3;

    /* renamed from: a4 */
    public boolean f58376a4;

    /* renamed from: a5 */
    public final /* synthetic */ ui0 f58377a5;

    public mi0(ui0 ui0Var) {
        this.f58377a5 = ui0Var;
        m214002a6();
    }

    @Override // p000.gq0
    /* renamed from: a0 */
    public final int mo211032a0() {
        return this.f58374a2.size();
    }

    @Override // p000.gq0
    /* renamed from: a1 */
    public final long mo211033a1(int i) {
        return i;
    }

    @Override // p000.gq0
    /* renamed from: a2 */
    public final int mo212978a2(int i) {
        oi0 oi0Var = (oi0) this.f58374a2.get(i);
        if (oi0Var instanceof pi0) {
            return 2;
        }
        if (oi0Var instanceof ni0) {
            return 3;
        }
        if (oi0Var instanceof qi0) {
            return ((qi0) oi0Var).f59521a0.hasSubMenu() ? 1 : 0;
        }
        throw new RuntimeException("Unknown item type.");
    }

    @Override // p000.gq0
    /* renamed from: a3 */
    public final void mo211034a3(dr0 dr0Var, int i) {
        View view = ((ti0) dr0Var).f55849a0;
        int iMo212978a2 = mo212978a2(i);
        ArrayList arrayList = this.f58374a2;
        ui0 ui0Var = this.f58377a5;
        if (iMo212978a2 != 0) {
            if (iMo212978a2 != 1) {
                if (iMo212978a2 == 2) {
                    pi0 pi0Var = (pi0) arrayList.get(i);
                    view.setPadding(ui0Var.f60444b7, pi0Var.f59233a0, ui0Var.f60445b8, pi0Var.f59234a1);
                    return;
                } else {
                    if (iMo212978a2 != 3) {
                        return;
                    }
                    xa1.m215152b4(view, new li0(this, i, true));
                    return;
                }
            }
            TextView textView = (TextView) view;
            textView.setText(((qi0) arrayList.get(i)).f59521a0.f56209a4);
            int i2 = ui0Var.f60433a6;
            if (i2 != 0) {
                textView.setTextAppearance(i2);
            }
            textView.setPadding(ui0Var.f60446b9, textView.getPaddingTop(), ui0Var.f60447c0, textView.getPaddingBottom());
            ColorStateList colorStateList = ui0Var.f60434a7;
            if (colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
            xa1.m215152b4(textView, new li0(this, i, true));
            return;
        }
        NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) view;
        navigationMenuItemView.setIconTintList(ui0Var.f60437b0);
        int i3 = ui0Var.f60435a8;
        if (i3 != 0) {
            navigationMenuItemView.setTextAppearance(i3);
        }
        ColorStateList colorStateList2 = ui0Var.f60436a9;
        if (colorStateList2 != null) {
            navigationMenuItemView.setTextColor(colorStateList2);
        }
        Drawable drawable = ui0Var.f60438b1;
        Drawable drawableNewDrawable = drawable != null ? drawable.getConstantState().newDrawable() : null;
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212779b6(navigationMenuItemView, drawableNewDrawable);
        RippleDrawable rippleDrawable = ui0Var.f60439b2;
        if (rippleDrawable != null) {
            navigationMenuItemView.setForeground(rippleDrawable.getConstantState().newDrawable());
        }
        qi0 qi0Var = (qi0) arrayList.get(i);
        navigationMenuItemView.setNeedsEmptyIcon(qi0Var.f59522a1);
        int i4 = ui0Var.f60440b3;
        int i5 = ui0Var.f60441b4;
        navigationMenuItemView.setPadding(i4, i5, i4, i5);
        navigationMenuItemView.setIconPadding(ui0Var.f60442b5);
        if (ui0Var.f60448c1) {
            navigationMenuItemView.setIconSize(ui0Var.f60443b6);
        }
        navigationMenuItemView.setMaxLines(ui0Var.f60450c3);
        navigationMenuItemView.mo209844a2(qi0Var.f59521a0);
        xa1.m215152b4(navigationMenuItemView, new li0(this, i, false));
    }

    @Override // p000.gq0
    /* renamed from: a4 */
    public final dr0 mo211035a4(ViewGroup viewGroup, int i) {
        ui0 ui0Var = this.f58377a5;
        if (i == 0) {
            LayoutInflater layoutInflater = ui0Var.f60432a5;
            ViewOnClickListenerC0846m2 viewOnClickListenerC0846m2 = ui0Var.f60454c7;
            View viewInflate = layoutInflater.inflate(R$layout.design_navigation_item, viewGroup, false);
            si0 si0Var = new si0(viewInflate);
            viewInflate.setOnClickListener(viewOnClickListenerC0846m2);
            return si0Var;
        }
        if (i == 1) {
            return new ki0(ui0Var.f60432a5.inflate(R$layout.design_navigation_item_subheader, viewGroup, false));
        }
        if (i == 2) {
            return new ki0(ui0Var.f60432a5.inflate(R$layout.design_navigation_item_separator, viewGroup, false));
        }
        if (i != 3) {
            return null;
        }
        return new ki0(ui0Var.f60428a1);
    }

    @Override // p000.gq0
    /* renamed from: a5 */
    public final void mo212979a5(dr0 dr0Var) {
        ti0 ti0Var = (ti0) dr0Var;
        if (ti0Var instanceof si0) {
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) ti0Var.f55849a0;
            FrameLayout frameLayout = navigationMenuItemView.f49558c5;
            if (frameLayout != null) {
                frameLayout.removeAllViews();
            }
            navigationMenuItemView.f49557c4.setCompoundDrawables(null, null, null, null);
        }
    }

    /* renamed from: a6 */
    public final void m214002a6() {
        boolean z;
        if (this.f58376a4) {
            return;
        }
        this.f58376a4 = true;
        ArrayList arrayList = this.f58374a2;
        arrayList.clear();
        arrayList.add(new ni0());
        ui0 ui0Var = this.f58377a5;
        int size = ui0Var.f60429a2.m210699b1().size();
        boolean z2 = false;
        int i = -1;
        int i2 = 0;
        boolean z3 = false;
        int size2 = 0;
        while (i2 < size) {
            ff0 ff0Var = (ff0) ui0Var.f60429a2.m210699b1().get(i2);
            if (ff0Var.isChecked()) {
                m214003a7(ff0Var);
            }
            if (ff0Var.isCheckable()) {
                ff0Var.m212799a3(z2);
            }
            if (ff0Var.hasSubMenu()) {
                r21 r21Var = ff0Var.f56219b4;
                if (r21Var.hasVisibleItems()) {
                    if (i2 != 0) {
                        arrayList.add(new pi0(ui0Var.f60452c5, z2 ? 1 : 0));
                    }
                    arrayList.add(new qi0(ff0Var));
                    int size3 = r21Var.f45871a5.size();
                    int i3 = z2 ? 1 : 0;
                    int i4 = i3;
                    while (i3 < size3) {
                        ff0 ff0Var2 = (ff0) r21Var.getItem(i3);
                        if (ff0Var2.isVisible()) {
                            if (i4 == 0 && ff0Var2.getIcon() != null) {
                                i4 = 1;
                            }
                            if (ff0Var2.isCheckable()) {
                                ff0Var2.m212799a3(z2);
                            }
                            if (ff0Var.isChecked()) {
                                m214003a7(ff0Var);
                            }
                            arrayList.add(new qi0(ff0Var2));
                        }
                        i3++;
                        z2 = false;
                    }
                    if (i4 != 0) {
                        int size4 = arrayList.size();
                        for (int size5 = arrayList.size(); size5 < size4; size5++) {
                            ((qi0) arrayList.get(size5)).f59522a1 = true;
                        }
                    }
                }
                z = true;
            } else {
                int i5 = ff0Var.f56206a1;
                if (i5 != i) {
                    size2 = arrayList.size();
                    z3 = ff0Var.getIcon() != null;
                    if (i2 != 0) {
                        size2++;
                        int i6 = ui0Var.f60452c5;
                        arrayList.add(new pi0(i6, i6));
                    }
                } else {
                    if (!z3 && ff0Var.getIcon() != null) {
                        int size6 = arrayList.size();
                        for (int i7 = size2; i7 < size6; i7++) {
                            ((qi0) arrayList.get(i7)).f59522a1 = true;
                        }
                        z = true;
                        z3 = true;
                    }
                    qi0 qi0Var = new qi0(ff0Var);
                    qi0Var.f59522a1 = z3;
                    arrayList.add(qi0Var);
                    i = i5;
                }
                z = true;
                qi0 qi0Var2 = new qi0(ff0Var);
                qi0Var2.f59522a1 = z3;
                arrayList.add(qi0Var2);
                i = i5;
            }
            i2++;
            z2 = false;
        }
        this.f58376a4 = z2 ? 1 : 0;
    }

    /* renamed from: a7 */
    public final void m214003a7(ff0 ff0Var) {
        if (this.f58375a3 == ff0Var || !ff0Var.isCheckable()) {
            return;
        }
        ff0 ff0Var2 = this.f58375a3;
        if (ff0Var2 != null) {
            ff0Var2.setChecked(false);
        }
        this.f58375a3 = ff0Var;
        ff0Var.setChecked(true);
    }
}
