package p000;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.ExpandedMenuView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class db0 implements tf0, AdapterView.OnItemClickListener {

    /* renamed from: a0 */
    public Context f55678a0;

    /* renamed from: a1 */
    public LayoutInflater f55679a1;

    /* renamed from: a2 */
    public bf0 f55680a2;

    /* renamed from: a3 */
    public ExpandedMenuView f55681a3;

    /* renamed from: a4 */
    public final int f55682a4;

    /* renamed from: a5 */
    public sf0 f55683a5;

    /* renamed from: a6 */
    public cb0 f55684a6;

    public db0(ContextWrapper contextWrapper, int i) {
        this.f55682a4 = i;
        this.f55678a0 = contextWrapper;
        this.f55679a1 = LayoutInflater.from(contextWrapper);
    }

    @Override // p000.tf0
    /* renamed from: a0 */
    public final void mo61a0(bf0 bf0Var, boolean z) {
        sf0 sf0Var = this.f55683a5;
        if (sf0Var != null) {
            sf0Var.mo210850a0(bf0Var, z);
        }
    }

    @Override // p000.tf0
    /* renamed from: a2 */
    public final boolean mo62a2(ff0 ff0Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: a4 */
    public final void mo63a4(Parcelable parcelable) {
        SparseArray<Parcelable> sparseParcelableArray = ((Bundle) parcelable).getSparseParcelableArray("android:menu:list");
        if (sparseParcelableArray != null) {
            this.f55681a3.restoreHierarchyState(sparseParcelableArray);
        }
    }

    @Override // p000.tf0
    /* renamed from: a5 */
    public final void mo209940a5(sf0 sf0Var) {
        throw null;
    }

    @Override // p000.tf0
    /* renamed from: a6 */
    public final boolean mo64a6(ff0 ff0Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: a8 */
    public final void mo65a8(Context context, bf0 bf0Var) {
        if (this.f55678a0 != null) {
            this.f55678a0 = context;
            if (this.f55679a1 == null) {
                this.f55679a1 = LayoutInflater.from(context);
            }
        }
        this.f55680a2 = bf0Var;
        cb0 cb0Var = this.f55684a6;
        if (cb0Var != null) {
            cb0Var.notifyDataSetChanged();
        }
    }

    @Override // p000.tf0
    /* renamed from: a9 */
    public final void mo66a9(boolean z) {
        cb0 cb0Var = this.f55684a6;
        if (cb0Var != null) {
            cb0Var.notifyDataSetChanged();
        }
    }

    @Override // p000.tf0
    /* renamed from: b0 */
    public final boolean mo67b0(r21 r21Var) {
        boolean zHasVisibleItems = r21Var.hasVisibleItems();
        Context context = r21Var.f45866a0;
        if (!zHasVisibleItems) {
            return false;
        }
        cf0 cf0Var = new cf0();
        cf0Var.f46129a0 = r21Var;
        C1166r3 c1166r3 = new C1166r3(context);
        C1102q9 c1102q9 = (C1102q9) c1166r3.f59608a1;
        db0 db0Var = new db0(c1102q9.f59429a0, R$layout.abc_list_menu_item_layout);
        cf0Var.f46131a2 = db0Var;
        db0Var.f55683a5 = cf0Var;
        r21Var.m210689a1(db0Var, context);
        db0 db0Var2 = cf0Var.f46131a2;
        if (db0Var2.f55684a6 == null) {
            db0Var2.f55684a6 = new cb0(db0Var2);
        }
        c1102q9.f59435a6 = db0Var2.f55684a6;
        c1102q9.f59436a7 = cf0Var;
        View view = r21Var.f45880b4;
        if (view != null) {
            c1102q9.f59433a4 = view;
        } else {
            c1102q9.f59431a2 = r21Var.f45879b3;
            c1102q9.f59432a3 = r21Var.f45878b2;
        }
        c1102q9.f59434a5 = cf0Var;
        DialogC1167r4 dialogC1167r4M214470a0 = c1166r3.m214470a0();
        cf0Var.f46130a1 = dialogC1167r4M214470a0;
        dialogC1167r4M214470a0.setOnDismissListener(cf0Var);
        WindowManager.LayoutParams attributes = cf0Var.f46130a1.getWindow().getAttributes();
        attributes.type = 1003;
        attributes.flags |= 131072;
        cf0Var.f46130a1.show();
        sf0 sf0Var = this.f55683a5;
        if (sf0Var == null) {
            return true;
        }
        sf0Var.mo210851b6(r21Var);
        return true;
    }

    @Override // p000.tf0
    /* renamed from: b1 */
    public final boolean mo68b1() {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b2 */
    public final Parcelable mo69b2() {
        if (this.f55681a3 == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        ExpandedMenuView expandedMenuView = this.f55681a3;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray("android:menu:list", sparseArray);
        return bundle;
    }

    @Override // p000.tf0
    public final int getId() {
        return 0;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.f55680a2.m210704b6(this.f55684a6.getItem(i), this, 0);
    }
}
