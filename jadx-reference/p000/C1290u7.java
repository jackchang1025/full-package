package p000;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle$Event;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: u7 */
/* loaded from: classes.dex */
public final class C1290u7 implements ut0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f60334a0;

    /* renamed from: a1 */
    public final Object f60335a1;

    public /* synthetic */ C1290u7(FragmentActivity fragmentActivity, int i) {
        this.f60334a0 = i;
        this.f60335a1 = fragmentActivity;
    }

    @Override // p000.ut0
    /* renamed from: a0 */
    public final Bundle mo210245a0() {
        switch (this.f60334a0) {
            case 0:
                Bundle bundle = new Bundle();
                ((AppCompatActivity) this.f60335a1).m209838b1().getClass();
                return bundle;
            case 1:
                Bundle bundle2 = new Bundle();
                FragmentActivity fragmentActivity = (FragmentActivity) this.f60335a1;
                tg0 tg0Var = fragmentActivity.f45017b7;
                while (FragmentActivity.m210119b0(((C1499z) tg0Var.f60218a1).f61421c9)) {
                }
                fragmentActivity.f45018b8.m210234g1(Lifecycle$Event.ON_STOP);
                Parcelable parcelableM210196d8 = ((C1499z) tg0Var.f60218a1).f61421c9.m210196d8();
                if (parcelableM210196d8 != null) {
                    bundle2.putParcelable("android:support:fragments", parcelableM210196d8);
                }
                return bundle2;
            default:
                Bundle bundle3 = new Bundle();
                bundle3.putStringArrayList("classes_to_restore", new ArrayList<>((LinkedHashSet) this.f60335a1));
                return bundle3;
        }
    }

    public C1290u7(vt0 vt0Var) {
        this.f60334a0 = 2;
        this.f60335a1 = new LinkedHashSet();
        vt0Var.m214953a2("androidx.savedstate.Restarter", this);
    }
}
