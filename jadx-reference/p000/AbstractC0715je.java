package p000;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.text.AbstractC0778a0;

/* renamed from: je */
/* loaded from: classes2.dex */
public abstract class AbstractC0715je extends AbstractC0721jk {
    /* renamed from: h5 */
    public static List m213288h5(ArrayList arrayList) {
        return m213303j0(new LinkedHashSet(arrayList));
    }

    /* renamed from: h6 */
    public static List m213289h6(List list) {
        int size = list.size() - 1;
        if (size <= 0) {
            return EmptyList.f57568a0;
        }
        if (size == 1) {
            return AbstractC1117qo.m214451e7(m213296i3(list));
        }
        ArrayList arrayList = new ArrayList(size);
        if (list instanceof RandomAccess) {
            int size2 = list.size();
            for (int i = 1; i < size2; i++) {
                arrayList.add(list.get(i));
            }
        } else {
            ListIterator listIterator = list.listIterator(1);
            while (listIterator.hasNext()) {
                arrayList.add(listIterator.next());
            }
        }
        return arrayList;
    }

    /* renamed from: h7 */
    public static Object m213290h7(List list) {
        t60.m214695b6(list, "<this>");
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return list.get(0);
    }

    /* renamed from: h8 */
    public static Object m213291h8(List list) {
        t60.m214695b6(list, "<this>");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /* renamed from: h9 */
    public static Object m213292h9(Set set) {
        t60.m214695b6(set, "<this>");
        if (set instanceof List) {
            List list = (List) set;
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }
        Iterator it = set.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    /* renamed from: i0 */
    public static final void m213293i0(Iterable iterable, StringBuilder sb, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, h10 h10Var) {
        t60.m214695b6(iterable, "<this>");
        sb.append(charSequence2);
        int i = 0;
        for (Object obj : iterable) {
            i++;
            if (i > 1) {
                sb.append(charSequence);
            }
            AbstractC0778a0.m213648a0(sb, obj, h10Var);
        }
        sb.append(charSequence3);
    }

    /* renamed from: i2 */
    public static String m213295i2(Iterable iterable, String str, String str2, String str3, h10 h10Var, int i) {
        if ((i & 1) != 0) {
            str = ", ";
        }
        String str4 = str;
        String str5 = (i & 2) != 0 ? "" : str2;
        String str6 = (i & 4) != 0 ? "" : str3;
        if ((i & 32) != 0) {
            h10Var = null;
        }
        t60.m214695b6(iterable, "<this>");
        StringBuilder sb = new StringBuilder();
        m213293i0(iterable, sb, str4, str5, str6, "...", h10Var);
        String string = sb.toString();
        t60.m214694b5(string, "joinTo(StringBuilder(), …ed, transform).toString()");
        return string;
    }

    /* renamed from: i3 */
    public static Object m213296i3(List list) {
        t60.m214695b6(list, "<this>");
        if (list.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return list.get(AbstractC0716jf.m213305g4(list));
    }

    /* renamed from: i4 */
    public static Object m213297i4(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /* renamed from: i5 */
    public static ArrayList m213298i5(Collection collection, Iterable iterable) {
        t60.m214695b6(collection, "<this>");
        t60.m214695b6(iterable, "elements");
        if (!(iterable instanceof Collection)) {
            ArrayList arrayList = new ArrayList(collection);
            AbstractC0721jk.m213314h2(arrayList, iterable);
            return arrayList;
        }
        Collection collection2 = (Collection) iterable;
        ArrayList arrayList2 = new ArrayList(collection2.size() + collection.size());
        arrayList2.addAll(collection);
        arrayList2.addAll(collection2);
        return arrayList2;
    }

    /* renamed from: i6 */
    public static List m213299i6(Collection collection) {
        t60.m214695b6(collection, "<this>");
        if (collection.size() <= 1) {
            return m213303j0(collection);
        }
        Object[] array = collection.toArray(new Comparable[0]);
        Comparable[] comparableArr = (Comparable[]) array;
        t60.m214695b6(comparableArr, "<this>");
        if (comparableArr.length > 1) {
            Arrays.sort(comparableArr);
        }
        return AbstractC0134bh.m210719e2(array);
    }

    /* renamed from: i7 */
    public static List m213300i7(List list, Comparator comparator) {
        t60.m214695b6(list, "<this>");
        if (list.size() <= 1) {
            return m213303j0(list);
        }
        Object[] array = list.toArray(new Object[0]);
        t60.m214695b6(array, "<this>");
        if (array.length > 1) {
            Arrays.sort(array, comparator);
        }
        return AbstractC0134bh.m210719e2(array);
    }

    /* renamed from: i8 */
    public static List m213301i8(Iterable iterable, int i) {
        Object next;
        t60.m214695b6(iterable, "<this>");
        if (i < 0) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Requested element count ", i, " is less than zero.").toString());
        }
        if (i == 0) {
            return EmptyList.f57568a0;
        }
        if (iterable instanceof Collection) {
            if (i >= ((Collection) iterable).size()) {
                return m213303j0(iterable);
            }
            if (i == 1) {
                if (iterable instanceof List) {
                    next = m213290h7((List) iterable);
                } else {
                    Iterator it = iterable.iterator();
                    if (!it.hasNext()) {
                        throw new NoSuchElementException("Collection is empty.");
                    }
                    next = it.next();
                }
                return AbstractC1117qo.m214451e7(next);
            }
        }
        ArrayList arrayList = new ArrayList(i);
        Iterator it2 = iterable.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            arrayList.add(it2.next());
            i2++;
            if (i2 == i) {
                break;
            }
        }
        return AbstractC0716jf.m213308g7(arrayList);
    }

    /* renamed from: i9 */
    public static final void m213302i9(Iterable iterable, AbstractCollection abstractCollection) {
        t60.m214695b6(iterable, "<this>");
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            abstractCollection.add(it.next());
        }
    }

    /* renamed from: j0 */
    public static List m213303j0(Iterable iterable) {
        ArrayList arrayList;
        t60.m214695b6(iterable, "<this>");
        if (!(iterable instanceof Collection)) {
            t60.m214695b6(iterable, "<this>");
            if (iterable instanceof Collection) {
                arrayList = new ArrayList((Collection) iterable);
            } else {
                arrayList = new ArrayList();
                m213302i9(iterable, arrayList);
            }
            return AbstractC0716jf.m213308g7(arrayList);
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return EmptyList.f57568a0;
        }
        if (size != 1) {
            return new ArrayList(collection);
        }
        return AbstractC1117qo.m214451e7(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
    }

    /* renamed from: j1 */
    public static Set m213304j1(Iterable iterable) {
        t60.m214695b6(iterable, "<this>");
        if (!(iterable instanceof Collection)) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            m213302i9(iterable, linkedHashSet);
            int size = linkedHashSet.size();
            if (size == 0) {
                return EmptySet.f57570a0;
            }
            if (size != 1) {
                return linkedHashSet;
            }
            Set setSingleton = Collections.singleton(linkedHashSet.iterator().next());
            t60.m214694b5(setSingleton, "singleton(element)");
            return setSingleton;
        }
        Collection collection = (Collection) iterable;
        int size2 = collection.size();
        if (size2 == 0) {
            return EmptySet.f57570a0;
        }
        if (size2 != 1) {
            LinkedHashSet linkedHashSet2 = new LinkedHashSet(AbstractC0770a1.m213612f7(collection.size()));
            m213302i9(iterable, linkedHashSet2);
            return linkedHashSet2;
        }
        Set setSingleton2 = Collections.singleton(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
        t60.m214694b5(setSingleton2, "singleton(element)");
        return setSingleton2;
    }
}
