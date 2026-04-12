package p000;

import androidx.lifecycle.Lifecycle$Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ip */
/* loaded from: classes.dex */
public final class C0690ip {

    /* renamed from: a0 */
    public final HashMap f57216a0 = new HashMap();

    /* renamed from: a1 */
    public final HashMap f57217a1;

    public C0690ip(HashMap map) {
        this.f57217a1 = map;
        for (Map.Entry entry : map.entrySet()) {
            Lifecycle$Event lifecycle$Event = (Lifecycle$Event) entry.getValue();
            List arrayList = (List) this.f57216a0.get(lifecycle$Event);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.f57216a0.put(lifecycle$Event, arrayList);
            }
            arrayList.add((C0691iq) entry.getKey());
        }
    }

    /* renamed from: a0 */
    public static void m213186a0(List list, ka0 ka0Var, Lifecycle$Event lifecycle$Event, ja0 ja0Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                C0691iq c0691iq = (C0691iq) list.get(size);
                Method method = c0691iq.f57219a1;
                try {
                    int i = c0691iq.f57218a0;
                    if (i == 0) {
                        method.invoke(ja0Var, null);
                    } else if (i == 1) {
                        method.invoke(ja0Var, ka0Var);
                    } else if (i == 2) {
                        method.invoke(ja0Var, ka0Var, lifecycle$Event);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException("Failed to call observer method", e2.getCause());
                }
            }
        }
    }
}
