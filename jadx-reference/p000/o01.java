package p000;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class o01 {

    /* renamed from: a0 */
    public HashMap f58706a0;

    /* renamed from: a0 */
    public final void m214148a0(int i, n01 n01Var) {
        HashMap map = this.f58706a0;
        HashSet hashSet = (HashSet) map.get(Integer.valueOf(i));
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(Integer.valueOf(i), hashSet);
        }
        hashSet.add(new WeakReference(n01Var));
    }
}
