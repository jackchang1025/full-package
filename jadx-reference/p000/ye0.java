package p000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.appcompat.view.menu.ListMenuItemView;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ye0 extends BaseAdapter {

    /* renamed from: a0 */
    public final bf0 f61296a0;

    /* renamed from: a1 */
    public int f61297a1 = -1;

    /* renamed from: a2 */
    public boolean f61298a2;

    /* renamed from: a3 */
    public final boolean f61299a3;

    /* renamed from: a4 */
    public final LayoutInflater f61300a4;

    /* renamed from: a5 */
    public final int f61301a5;

    public ye0(bf0 bf0Var, LayoutInflater layoutInflater, boolean z, int i) {
        this.f61299a3 = z;
        this.f61300a4 = layoutInflater;
        this.f61296a0 = bf0Var;
        this.f61301a5 = i;
        m215276a0();
    }

    /* renamed from: a0 */
    public final void m215276a0() {
        bf0 bf0Var = this.f61296a0;
        ff0 ff0Var = bf0Var.f45887c1;
        if (ff0Var != null) {
            bf0Var.m210696a8();
            ArrayList arrayList = bf0Var.f45875a9;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((ff0) arrayList.get(i)) == ff0Var) {
                    this.f61297a1 = i;
                    return;
                }
            }
        }
        this.f61297a1 = -1;
    }

    @Override // android.widget.Adapter
    /* renamed from: a1, reason: merged with bridge method [inline-methods] */
    public final ff0 getItem(int i) {
        ArrayList arrayListM210699b1;
        boolean z = this.f61299a3;
        bf0 bf0Var = this.f61296a0;
        if (z) {
            bf0Var.m210696a8();
            arrayListM210699b1 = bf0Var.f45875a9;
        } else {
            arrayListM210699b1 = bf0Var.m210699b1();
        }
        int i2 = this.f61297a1;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return (ff0) arrayListM210699b1.get(i);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        ArrayList arrayListM210699b1;
        boolean z = this.f61299a3;
        bf0 bf0Var = this.f61296a0;
        if (z) {
            bf0Var.m210696a8();
            arrayListM210699b1 = bf0Var.f45875a9;
        } else {
            arrayListM210699b1 = bf0Var.m210699b1();
        }
        return this.f61297a1 < 0 ? arrayListM210699b1.size() : arrayListM210699b1.size() - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        boolean z = false;
        if (view == null) {
            view = this.f61300a4.inflate(this.f61301a5, viewGroup, false);
        }
        int i2 = getItem(i).f56206a1;
        int i3 = i - 1;
        int i4 = i3 >= 0 ? getItem(i3).f56206a1 : i2;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        if (this.f61296a0.mo210700b2() && i2 != i4) {
            z = true;
        }
        listMenuItemView.setGroupDividerEnabled(z);
        uf0 uf0Var = (uf0) view;
        if (this.f61298a2) {
            listMenuItemView.setForceShowIcon(true);
        }
        uf0Var.mo209844a2(getItem(i));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        m215276a0();
        super.notifyDataSetChanged();
    }
}
