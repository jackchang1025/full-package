package p000;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cb0 extends BaseAdapter {

    /* renamed from: a0 */
    public int f46092a0 = -1;

    /* renamed from: a1 */
    public final /* synthetic */ db0 f46093a1;

    public cb0(db0 db0Var) {
        this.f46093a1 = db0Var;
        m210799a0();
    }

    /* renamed from: a0 */
    public final void m210799a0() {
        bf0 bf0Var = this.f46093a1.f55680a2;
        ff0 ff0Var = bf0Var.f45887c1;
        if (ff0Var != null) {
            bf0Var.m210696a8();
            ArrayList arrayList = bf0Var.f45875a9;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((ff0) arrayList.get(i)) == ff0Var) {
                    this.f46092a0 = i;
                    return;
                }
            }
        }
        this.f46092a0 = -1;
    }

    @Override // android.widget.Adapter
    /* renamed from: a1, reason: merged with bridge method [inline-methods] */
    public final ff0 getItem(int i) {
        db0 db0Var = this.f46093a1;
        bf0 bf0Var = db0Var.f55680a2;
        bf0Var.m210696a8();
        ArrayList arrayList = bf0Var.f45875a9;
        db0Var.getClass();
        int i2 = this.f46092a0;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return (ff0) arrayList.get(i);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        db0 db0Var = this.f46093a1;
        bf0 bf0Var = db0Var.f55680a2;
        bf0Var.m210696a8();
        int size = bf0Var.f45875a9.size();
        db0Var.getClass();
        return this.f46092a0 < 0 ? size : size - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            db0 db0Var = this.f46093a1;
            view = db0Var.f55679a1.inflate(db0Var.f55682a4, viewGroup, false);
        }
        ((uf0) view).mo209844a2(getItem(i));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        m210799a0();
        super.notifyDataSetChanged();
    }
}
