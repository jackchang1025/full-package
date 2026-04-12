package p000;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jk */
/* loaded from: classes2.dex */
public abstract class AbstractC0721jk extends AbstractC0720jj {
    /* renamed from: h2 */
    public static void m213314h2(Collection collection, Iterable iterable) {
        t60.m214695b6(collection, "<this>");
        t60.m214695b6(iterable, "elements");
        if (iterable instanceof Collection) {
            collection.addAll((Collection) iterable);
            return;
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            collection.add(it.next());
        }
    }

    /* renamed from: h3 */
    public static void m213315h3(Collection collection, Object[] objArr) {
        t60.m214695b6(collection, "<this>");
        t60.m214695b6(objArr, "elements");
        collection.addAll(AbstractC0134bh.m210719e2(objArr));
    }

    /* renamed from: h4 */
    public static void m213316h4(List list, h10 h10Var) {
        int iM213305g4;
        t60.m214695b6(list, "<this>");
        t60.m214695b6(h10Var, "predicate");
        if (!(list instanceof RandomAccess)) {
            if ((list instanceof d80) && !(list instanceof e80)) {
                b81.m210600f3(list, "kotlin.collections.MutableIterable");
                throw null;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((Boolean) h10Var.invoke(it.next())).booleanValue()) {
                    it.remove();
                }
            }
            return;
        }
        int i = 0;
        l60 it2 = new n60(0, AbstractC0716jf.m213305g4(list), 1).iterator();
        while (it2.f57840a2) {
            int iNextInt = it2.nextInt();
            Object obj = list.get(iNextInt);
            if (!((Boolean) h10Var.invoke(obj)).booleanValue()) {
                if (i != iNextInt) {
                    list.set(i, obj);
                }
                i++;
            }
        }
        if (i >= list.size() || i > (iM213305g4 = AbstractC0716jf.m213305g4(list))) {
            return;
        }
        while (true) {
            list.remove(iM213305g4);
            if (iM213305g4 == i) {
                return;
            } else {
                iM213305g4--;
            }
        }
    }
}
