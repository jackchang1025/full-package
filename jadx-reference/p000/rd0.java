package p000;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.datepicker.MaterialCalendar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class rd0 extends LinearLayoutManager {

    /* renamed from: d0 */
    public final /* synthetic */ int f59686d0;

    /* renamed from: d1 */
    public final /* synthetic */ MaterialCalendar f59687d1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public rd0(MaterialCalendar materialCalendar, int i, int i2) {
        super(i);
        this.f59687d1 = materialCalendar;
        this.f59686d0 = i2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, p000.pq0
    /* renamed from: h3 */
    public final void mo210312h3(RecyclerView recyclerView, int i) {
        z01 z01Var = new z01(recyclerView.getContext());
        z01Var.f61472a0 = i;
        m214330h4(z01Var);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    /* renamed from: h6 */
    public final void mo210313h6(ar0 ar0Var, int[] iArr) {
        int i = this.f59686d0;
        MaterialCalendar materialCalendar = this.f59687d1;
        if (i == 0) {
            iArr[0] = materialCalendar.f49379f3.getWidth();
            iArr[1] = materialCalendar.f49379f3.getWidth();
        } else {
            iArr[0] = materialCalendar.f49379f3.getHeight();
            iArr[1] = materialCalendar.f49379f3.getHeight();
        }
    }
}
