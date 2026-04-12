package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.R$dimen;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ParcelableSparseArray;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ui0 implements tf0 {

    /* renamed from: a0 */
    public NavigationMenuView f60427a0;

    /* renamed from: a1 */
    public LinearLayout f60428a1;

    /* renamed from: a2 */
    public bf0 f60429a2;

    /* renamed from: a3 */
    public int f60430a3;

    /* renamed from: a4 */
    public mi0 f60431a4;

    /* renamed from: a5 */
    public LayoutInflater f60432a5;

    /* renamed from: a7 */
    public ColorStateList f60434a7;

    /* renamed from: a9 */
    public ColorStateList f60436a9;

    /* renamed from: b0 */
    public ColorStateList f60437b0;

    /* renamed from: b1 */
    public Drawable f60438b1;

    /* renamed from: b2 */
    public RippleDrawable f60439b2;

    /* renamed from: b3 */
    public int f60440b3;

    /* renamed from: b4 */
    public int f60441b4;

    /* renamed from: b5 */
    public int f60442b5;

    /* renamed from: b6 */
    public int f60443b6;

    /* renamed from: b7 */
    public int f60444b7;

    /* renamed from: b8 */
    public int f60445b8;

    /* renamed from: b9 */
    public int f60446b9;

    /* renamed from: c0 */
    public int f60447c0;

    /* renamed from: c1 */
    public boolean f60448c1;

    /* renamed from: c3 */
    public int f60450c3;

    /* renamed from: c4 */
    public int f60451c4;

    /* renamed from: c5 */
    public int f60452c5;

    /* renamed from: a6 */
    public int f60433a6 = 0;

    /* renamed from: a8 */
    public int f60435a8 = 0;

    /* renamed from: c2 */
    public boolean f60449c2 = true;

    /* renamed from: c6 */
    public int f60453c6 = -1;

    /* renamed from: c7 */
    public final ViewOnClickListenerC0846m2 f60454c7 = new ViewOnClickListenerC0846m2(3, this);

    @Override // p000.tf0
    /* renamed from: a2 */
    public final boolean mo62a2(ff0 ff0Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: a4 */
    public final void mo63a4(Parcelable parcelable) {
        ff0 ff0Var;
        View actionView;
        ParcelableSparseArray parcelableSparseArray;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:list");
            if (sparseParcelableArray != null) {
                this.f60427a0.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                mi0 mi0Var = this.f60431a4;
                ArrayList arrayList = mi0Var.f58374a2;
                int i = bundle2.getInt("android:menu:checked", 0);
                if (i != 0) {
                    mi0Var.f58376a4 = true;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        oi0 oi0Var = (oi0) arrayList.get(i2);
                        if (oi0Var instanceof qi0) {
                            ff0 ff0Var2 = ((qi0) oi0Var).f59521a0;
                            if (ff0Var2.f56205a0 == i) {
                                mi0Var.m214003a7(ff0Var2);
                                break;
                            }
                        }
                        i2++;
                    }
                    mi0Var.f58376a4 = false;
                    mi0Var.m214002a6();
                }
                SparseArray sparseParcelableArray2 = bundle2.getSparseParcelableArray("android:menu:action_views");
                if (sparseParcelableArray2 != null) {
                    int size2 = arrayList.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        oi0 oi0Var2 = (oi0) arrayList.get(i3);
                        if ((oi0Var2 instanceof qi0) && (actionView = (ff0Var = ((qi0) oi0Var2).f59521a0).getActionView()) != null && (parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray2.get(ff0Var.f56205a0)) != null) {
                            actionView.restoreHierarchyState(parcelableSparseArray);
                        }
                    }
                }
            }
            SparseArray<Parcelable> sparseParcelableArray3 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray3 != null) {
                this.f60428a1.restoreHierarchyState(sparseParcelableArray3);
            }
        }
    }

    @Override // p000.tf0
    /* renamed from: a6 */
    public final boolean mo64a6(ff0 ff0Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: a8 */
    public final void mo65a8(Context context, bf0 bf0Var) {
        this.f60432a5 = LayoutInflater.from(context);
        this.f60429a2 = bf0Var;
        this.f60452c5 = context.getResources().getDimensionPixelOffset(R$dimen.design_navigation_separator_vertical_padding);
    }

    @Override // p000.tf0
    /* renamed from: a9 */
    public final void mo66a9(boolean z) {
        mi0 mi0Var = this.f60431a4;
        if (mi0Var != null) {
            mi0Var.m214002a6();
            mi0Var.f56549a0.m213091a1();
        }
    }

    @Override // p000.tf0
    /* renamed from: b0 */
    public final boolean mo67b0(r21 r21Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b1 */
    public final boolean mo68b1() {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b2 */
    public final Parcelable mo69b2() {
        Bundle bundle = new Bundle();
        if (this.f60427a0 != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.f60427a0.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray("android:menu:list", sparseArray);
        }
        mi0 mi0Var = this.f60431a4;
        if (mi0Var != null) {
            ArrayList arrayList = mi0Var.f58374a2;
            Bundle bundle2 = new Bundle();
            ff0 ff0Var = mi0Var.f58375a3;
            if (ff0Var != null) {
                bundle2.putInt("android:menu:checked", ff0Var.f56205a0);
            }
            SparseArray<? extends Parcelable> sparseArray2 = new SparseArray<>();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                oi0 oi0Var = (oi0) arrayList.get(i);
                if (oi0Var instanceof qi0) {
                    ff0 ff0Var2 = ((qi0) oi0Var).f59521a0;
                    View actionView = ff0Var2 != null ? ff0Var2.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray2.put(ff0Var2.f56205a0, parcelableSparseArray);
                    }
                }
            }
            bundle2.putSparseParcelableArray("android:menu:action_views", sparseArray2);
            bundle.putBundle("android:menu:adapter", bundle2);
        }
        if (this.f60428a1 != null) {
            SparseArray<Parcelable> sparseArray3 = new SparseArray<>();
            this.f60428a1.saveHierarchyState(sparseArray3);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray3);
        }
        return bundle;
    }

    @Override // p000.tf0
    public final int getId() {
        return this.f60430a3;
    }

    @Override // p000.tf0
    /* renamed from: a0 */
    public final void mo61a0(bf0 bf0Var, boolean z) {
    }
}
