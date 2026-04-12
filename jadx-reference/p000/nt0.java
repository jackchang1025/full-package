package p000;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class nt0 implements Iterable {

    /* renamed from: a0 */
    public kt0 f58692a0;

    /* renamed from: a1 */
    public kt0 f58693a1;

    /* renamed from: a2 */
    public final WeakHashMap f58694a2 = new WeakHashMap();

    /* renamed from: a3 */
    public int f58695a3 = 0;

    /* renamed from: a0 */
    public kt0 mo214143a0(Object obj) {
        kt0 kt0Var = this.f58692a0;
        while (kt0Var != null && !kt0Var.f57716a0.equals(obj)) {
            kt0Var = kt0Var.f57718a2;
        }
        return kt0Var;
    }

    /* renamed from: a1 */
    public Object mo214144a1(Object obj) {
        kt0 kt0VarMo214143a0 = mo214143a0(obj);
        if (kt0VarMo214143a0 == null) {
            return null;
        }
        this.f58695a3--;
        WeakHashMap weakHashMap = this.f58694a2;
        if (!weakHashMap.isEmpty()) {
            Iterator it = weakHashMap.keySet().iterator();
            while (it.hasNext()) {
                ((mt0) it.next()).mo213348a0(kt0VarMo214143a0);
            }
        }
        kt0 kt0Var = kt0VarMo214143a0.f57719a3;
        if (kt0Var != null) {
            kt0Var.f57718a2 = kt0VarMo214143a0.f57718a2;
        } else {
            this.f58692a0 = kt0VarMo214143a0.f57718a2;
        }
        kt0 kt0Var2 = kt0VarMo214143a0.f57718a2;
        if (kt0Var2 != null) {
            kt0Var2.f57719a3 = kt0Var;
        } else {
            this.f58693a1 = kt0Var;
        }
        kt0VarMo214143a0.f57718a2 = null;
        kt0VarMo214143a0.f57719a3 = null;
        return kt0VarMo214143a0.f57717a1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
    
        if (r3.hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
    
        if (((p000.jt0) r7).hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0053, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof nt0)) {
            return false;
        }
        nt0 nt0Var = (nt0) obj;
        if (this.f58695a3 != nt0Var.f58695a3) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = nt0Var.iterator();
        while (true) {
            jt0 jt0Var = (jt0) it;
            if (!jt0Var.hasNext()) {
                break;
            }
            jt0 jt0Var2 = (jt0) it2;
            if (!jt0Var2.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) jt0Var.next();
            Object next = jt0Var2.next();
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                break;
            }
        }
        return false;
    }

    public final int hashCode() {
        Iterator it = iterator();
        int iHashCode = 0;
        while (true) {
            jt0 jt0Var = (jt0) it;
            if (!jt0Var.hasNext()) {
                return iHashCode;
            }
            iHashCode += ((Map.Entry) jt0Var.next()).hashCode();
        }
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        jt0 jt0Var = new jt0(this.f58692a0, this.f58693a1, 0);
        this.f58694a2.put(jt0Var, Boolean.FALSE);
        return jt0Var;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = iterator();
        while (true) {
            jt0 jt0Var = (jt0) it;
            if (!jt0Var.hasNext()) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(((Map.Entry) jt0Var.next()).toString());
            if (jt0Var.hasNext()) {
                sb.append(", ");
            }
        }
    }
}
