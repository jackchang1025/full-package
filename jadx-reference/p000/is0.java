package p000;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class is0 {
    public /* synthetic */ is0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final js0 acquire(String str, int i) {
        t60.m214695b6(str, "query");
        TreeMap treeMap = js0.f57368a9;
        synchronized (treeMap) {
            Map.Entry entryCeilingEntry = treeMap.ceilingEntry(Integer.valueOf(i));
            if (entryCeilingEntry == null) {
                js0 js0Var = new js0(i);
                js0Var.f57370a1 = str;
                js0Var.f57376a7 = i;
                return js0Var;
            }
            treeMap.remove(entryCeilingEntry.getKey());
            js0 js0Var2 = (js0) entryCeilingEntry.getValue();
            js0Var2.f57370a1 = str;
            js0Var2.f57376a7 = i;
            return js0Var2;
        }
    }

    public final js0 copyFrom(m31 m31Var) {
        t60.m214695b6(m31Var, "supportSQLiteQuery");
        js0 js0VarAcquire = acquire(m31Var.mo213339a0(), m31Var.mo213342a7());
        m31Var.mo213340a5(new t00(js0VarAcquire));
        return js0VarAcquire;
    }

    public final void prunePoolLocked$room_runtime_release() {
        TreeMap treeMap = js0.f57368a9;
        if (treeMap.size() <= 15) {
            return;
        }
        int size = treeMap.size() - 10;
        Iterator it = treeMap.descendingKeySet().iterator();
        t60.m214694b5(it, "queryPool.descendingKeySet().iterator()");
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return;
            }
            it.next();
            it.remove();
            size = i;
        }
    }

    private is0() {
    }

    public static /* synthetic */ void getDESIRED_POOL_SIZE$annotations() {
    }

    public static /* synthetic */ void getPOOL_LIMIT$annotations() {
    }

    public static /* synthetic */ void getQueryPool$annotations() {
    }
}
