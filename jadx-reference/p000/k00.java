package p000;

import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class k00 extends ib1 {

    /* renamed from: a9 */
    public static final j00 f57404a9 = new j00(0);

    /* renamed from: a6 */
    public final boolean f57408a6;

    /* renamed from: a3 */
    public final HashMap f57405a3 = new HashMap();

    /* renamed from: a4 */
    public final HashMap f57406a4 = new HashMap();

    /* renamed from: a5 */
    public final HashMap f57407a5 = new HashMap();

    /* renamed from: a7 */
    public boolean f57409a7 = false;

    /* renamed from: a8 */
    public boolean f57410a8 = false;

    public k00(boolean z) {
        this.f57408a6 = z;
    }

    @Override // p000.ib1
    /* renamed from: a1 */
    public final void mo213148a1() {
        if (C0071a7.m210158c7(3)) {
            toString();
        }
        this.f57409a7 = true;
    }

    /* renamed from: a2 */
    public final void m213395a2(AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5) {
        if (this.f57410a8 || this.f57405a3.remove(abstractComponentCallbacksC0069a5.f45081a4) == null || !C0071a7.m210158c7(2)) {
            return;
        }
        abstractComponentCallbacksC0069a5.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && k00.class == obj.getClass()) {
            k00 k00Var = (k00) obj;
            if (this.f57405a3.equals(k00Var.f57405a3) && this.f57406a4.equals(k00Var.f57406a4) && this.f57407a5.equals(k00Var.f57407a5)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f57407a5.hashCode() + ((this.f57406a4.hashCode() + (this.f57405a3.hashCode() * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator it = this.f57405a3.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator it2 = this.f57406a4.keySet().iterator();
        while (it2.hasNext()) {
            sb.append((String) it2.next());
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator it3 = this.f57407a5.keySet().iterator();
        while (it3.hasNext()) {
            sb.append((String) it3.next());
            if (it3.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
