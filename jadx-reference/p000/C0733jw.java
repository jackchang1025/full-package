package p000;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jw */
/* loaded from: classes.dex */
public final /* synthetic */ class C0733jw implements ut0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f57387a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f57388a1;

    public /* synthetic */ C0733jw(int i, Object obj) {
        this.f57387a0 = i;
        this.f57388a1 = obj;
    }

    @Override // p000.ut0
    /* renamed from: a0 */
    public final Bundle mo210245a0() {
        int i = this.f57387a0;
        Object obj = this.f57388a1;
        switch (i) {
            case 0:
                int i2 = ComponentActivity.f43731b6;
                Bundle bundle = new Bundle();
                C0736jy c0736jy = ((ComponentActivity) obj).f43739a8;
                c0736jy.getClass();
                HashMap map = c0736jy.f57394a2;
                bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(map.values()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(map.keySet()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(c0736jy.f57396a4));
                bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) c0736jy.f57399a7.clone());
                bundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", c0736jy.f57392a0);
                return bundle;
            default:
                return pt0.m214336a0((pt0) obj);
        }
    }
}
