package p000;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jf */
/* loaded from: classes2.dex */
public abstract class AbstractC0716jf extends AbstractC1117qo {
    /* renamed from: g4 */
    public static int m213305g4(List list) {
        t60.m214695b6(list, "<this>");
        return list.size() - 1;
    }

    /* renamed from: g5 */
    public static List m213306g5(Object... objArr) {
        t60.m214695b6(objArr, "elements");
        return objArr.length > 0 ? AbstractC0134bh.m210719e2(objArr) : EmptyList.f57568a0;
    }

    /* renamed from: g6 */
    public static ArrayList m213307g6(Object... objArr) {
        t60.m214695b6(objArr, "elements");
        return objArr.length == 0 ? new ArrayList() : new ArrayList(new C0114ay(objArr, true));
    }

    /* renamed from: g7 */
    public static final List m213308g7(List list) {
        int size = list.size();
        return size != 0 ? size != 1 ? list : AbstractC1117qo.m214451e7(list.get(0)) : EmptyList.f57568a0;
    }

    /* renamed from: g8 */
    public static void m213309g8() {
        throw new ArithmeticException("Index overflow has happened.");
    }
}
