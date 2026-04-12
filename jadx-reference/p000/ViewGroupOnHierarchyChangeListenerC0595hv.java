package p000;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hv */
/* loaded from: classes2.dex */
public final class ViewGroupOnHierarchyChangeListenerC0595hv implements ViewGroup.OnHierarchyChangeListener {

    /* renamed from: a0 */
    public ViewGroup.OnHierarchyChangeListener f56755a0;

    /* renamed from: a1 */
    public final /* synthetic */ ChipGroup f56756a1;

    public ViewGroupOnHierarchyChangeListenerC0595hv(ChipGroup chipGroup) {
        this.f56756a1 = chipGroup;
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewAdded(View view, View view2) {
        ChipGroup chipGroup = this.f56756a1;
        if (view == chipGroup && (view2 instanceof Chip)) {
            if (view2.getId() == -1) {
                WeakHashMap weakHashMap = xa1.f61054a0;
                view2.setId(ga1.m212901a0());
            }
            C0578he c0578he = chipGroup.f49355a7;
            Chip chip = (Chip) view2;
            c0578he.f56656a0.put(Integer.valueOf(chip.getId()), chip);
            if (chip.isChecked()) {
                c0578he.m213027a0(chip);
            }
            chip.setInternalOnCheckedChangeListener(new tg0(8, c0578he));
        }
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.f56755a0;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewAdded(view, view2);
        }
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewRemoved(View view, View view2) {
        ChipGroup chipGroup = this.f56756a1;
        if (view == chipGroup && (view2 instanceof Chip)) {
            C0578he c0578he = chipGroup.f49355a7;
            Chip chip = (Chip) view2;
            c0578he.getClass();
            chip.setInternalOnCheckedChangeListener(null);
            c0578he.f56656a0.remove(Integer.valueOf(chip.getId()));
            c0578he.f56657a1.remove(Integer.valueOf(chip.getId()));
        }
        ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.f56755a0;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    }
}
