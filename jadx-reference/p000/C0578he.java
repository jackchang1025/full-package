package p000;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: he */
/* loaded from: classes2.dex */
public final class C0578he {

    /* renamed from: a0 */
    public final HashMap f56656a0 = new HashMap();

    /* renamed from: a1 */
    public final HashSet f56657a1 = new HashSet();

    /* renamed from: a2 */
    public C0591hr f56658a2;

    /* renamed from: a3 */
    public boolean f56659a3;

    /* renamed from: a4 */
    public boolean f56660a4;

    /* renamed from: a0 */
    public final boolean m213027a0(wd0 wd0Var) {
        int id = wd0Var.getId();
        Integer numValueOf = Integer.valueOf(id);
        HashSet hashSet = this.f56657a1;
        if (hashSet.contains(numValueOf)) {
            return false;
        }
        wd0 wd0Var2 = (wd0) this.f56656a0.get(Integer.valueOf(m213029a2()));
        if (wd0Var2 != null) {
            m213031a4(wd0Var2, false);
        }
        boolean zAdd = hashSet.add(Integer.valueOf(id));
        if (!wd0Var.isChecked()) {
            wd0Var.setChecked(true);
        }
        return zAdd;
    }

    /* renamed from: a1 */
    public final ArrayList m213028a1(ViewGroup viewGroup) {
        HashSet hashSet = new HashSet(this.f56657a1);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof wd0) && hashSet.contains(Integer.valueOf(childAt.getId()))) {
                arrayList.add(Integer.valueOf(childAt.getId()));
            }
        }
        return arrayList;
    }

    /* renamed from: a2 */
    public final int m213029a2() {
        if (!this.f56659a3) {
            return -1;
        }
        HashSet hashSet = this.f56657a1;
        if (hashSet.isEmpty()) {
            return -1;
        }
        return ((Integer) hashSet.iterator().next()).intValue();
    }

    /* renamed from: a3 */
    public final void m213030a3() {
        C0591hr c0591hr = this.f56658a2;
        if (c0591hr != null) {
            new HashSet(this.f56657a1);
            ChipGroup chipGroup = c0591hr.f56749a0;
            InterfaceC0594hu interfaceC0594hu = chipGroup.f49354a6;
            if (interfaceC0594hu != null) {
                chipGroup.f49355a7.m213028a1(chipGroup);
                ChipGroup chipGroup2 = ((C0591hr) interfaceC0594hu).f56749a0;
                if (chipGroup2.f49355a7.f56659a3) {
                    chipGroup2.getCheckedChipId();
                    throw null;
                }
            }
        }
    }

    /* renamed from: a4 */
    public final boolean m213031a4(wd0 wd0Var, boolean z) {
        int id = wd0Var.getId();
        Integer numValueOf = Integer.valueOf(id);
        HashSet hashSet = this.f56657a1;
        if (!hashSet.contains(numValueOf)) {
            return false;
        }
        if (z && hashSet.size() == 1 && hashSet.contains(Integer.valueOf(id))) {
            wd0Var.setChecked(true);
            return false;
        }
        boolean zRemove = hashSet.remove(Integer.valueOf(id));
        if (wd0Var.isChecked()) {
            wd0Var.setChecked(false);
        }
        return zRemove;
    }
}
