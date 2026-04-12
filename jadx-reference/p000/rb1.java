package p000;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class rb1 {

    /* renamed from: a0 */
    public final LinkedHashMap f59667a0 = new LinkedHashMap();

    /* renamed from: a0 */
    public final void m214526a0() {
        for (ib1 ib1Var : this.f59667a0.values()) {
            ib1Var.f56855a2 = true;
            HashMap map = ib1Var.f56853a0;
            if (map != null) {
                synchronized (map) {
                    try {
                        Iterator it = ib1Var.f56853a0.values().iterator();
                        while (it.hasNext()) {
                            ib1.m213147a0(it.next());
                        }
                    } finally {
                    }
                }
            }
            LinkedHashSet linkedHashSet = ib1Var.f56854a1;
            if (linkedHashSet != null) {
                synchronized (linkedHashSet) {
                    try {
                        Iterator it2 = ib1Var.f56854a1.iterator();
                        while (it2.hasNext()) {
                            ib1.m213147a0((Closeable) it2.next());
                        }
                    } finally {
                    }
                }
            }
            ib1Var.mo213148a1();
        }
        this.f59667a0.clear();
    }
}
