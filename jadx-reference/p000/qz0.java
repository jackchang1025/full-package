package p000;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;

/* loaded from: classes2.dex */
public abstract class qz0 extends rz0 {
    /* renamed from: f6 */
    public static nz0 m214467f6(Iterator it) {
        t60.m214695b6(it, "<this>");
        return new C0796ku(new C0722jl(2, it));
    }

    /* renamed from: f7 */
    public static List m214468f7(nz0 nz0Var) {
        Iterator it = nz0Var.iterator();
        if (!it.hasNext()) {
            return EmptyList.f57568a0;
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return AbstractC1117qo.m214451e7(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }
}
