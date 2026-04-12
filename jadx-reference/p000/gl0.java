package p000;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class gl0 {
    public /* synthetic */ gl0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final hl0 from(Class<? extends tb0> cls) {
        t60.m214695b6(cls, "workerClass");
        return (hl0) new fl0(cls).m213153a0();
    }

    private gl0() {
    }

    public final List<hl0> from(List<? extends Class<? extends tb0>> list) {
        t60.m214695b6(list, "workerClasses");
        ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(list));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((hl0) new fl0((Class) it.next()).m213153a0());
        }
        return arrayList;
    }
}
