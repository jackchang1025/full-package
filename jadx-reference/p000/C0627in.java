package p000;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: in */
/* loaded from: classes2.dex */
public final class C0627in implements a80, InterfaceC0625il {

    /* renamed from: a1 */
    public static final C0626im f56913a1 = new C0626im(null);

    /* renamed from: a2 */
    public static final Map f56914a2;

    /* renamed from: a3 */
    public static final HashMap f56915a3;

    /* renamed from: a4 */
    public static final LinkedHashMap f56916a4;

    /* renamed from: a0 */
    public final Class f56917a0;

    static {
        List listM213306g5 = AbstractC0716jf.m213306g5(w00.class, h10.class, l10.class, m10.class, n10.class, o10.class, p10.class, q10.class, r10.class, s10.class, x00.class, y00.class, z00.class, a10.class, b10.class, c10.class, d10.class, e10.class, f10.class, g10.class, i10.class, j10.class, k10.class);
        ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(listM213306g5));
        int i = 0;
        for (Object obj : listM213306g5) {
            int i2 = i + 1;
            if (i < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            arrayList.add(new Pair((Class) obj, Integer.valueOf(i)));
            i = i2;
        }
        f56914a2 = AbstractC0770a1.m213617g2(arrayList);
        HashMap map = new HashMap();
        map.put("boolean", "kotlin.Boolean");
        map.put("char", "kotlin.Char");
        map.put("byte", "kotlin.Byte");
        map.put("short", "kotlin.Short");
        map.put("int", "kotlin.Int");
        map.put("float", "kotlin.Float");
        map.put("long", "kotlin.Long");
        map.put("double", "kotlin.Double");
        HashMap map2 = new HashMap();
        map2.put("java.lang.Boolean", "kotlin.Boolean");
        map2.put("java.lang.Character", "kotlin.Char");
        map2.put("java.lang.Byte", "kotlin.Byte");
        map2.put("java.lang.Short", "kotlin.Short");
        map2.put("java.lang.Integer", "kotlin.Int");
        map2.put("java.lang.Float", "kotlin.Float");
        map2.put("java.lang.Long", "kotlin.Long");
        map2.put("java.lang.Double", "kotlin.Double");
        HashMap map3 = new HashMap();
        map3.put("java.lang.Object", "kotlin.Any");
        map3.put("java.lang.String", "kotlin.String");
        map3.put("java.lang.CharSequence", "kotlin.CharSequence");
        map3.put("java.lang.Throwable", "kotlin.Throwable");
        map3.put("java.lang.Cloneable", "kotlin.Cloneable");
        map3.put("java.lang.Number", "kotlin.Number");
        map3.put("java.lang.Comparable", "kotlin.Comparable");
        map3.put("java.lang.Enum", "kotlin.Enum");
        map3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        map3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        map3.put("java.util.Iterator", "kotlin.collections.Iterator");
        map3.put("java.util.Collection", "kotlin.collections.Collection");
        map3.put("java.util.List", "kotlin.collections.List");
        map3.put("java.util.Set", "kotlin.collections.Set");
        map3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        map3.put("java.util.Map", "kotlin.collections.Map");
        map3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        map3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        map3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        map3.putAll(map);
        map3.putAll(map2);
        Collection<String> collectionValues = map.values();
        t60.m214694b5(collectionValues, "primitiveFqNames.values");
        for (String str : collectionValues) {
            StringBuilder sb = new StringBuilder("kotlin.jvm.internal.");
            t60.m214694b5(str, "kotlinName");
            sb.append(AbstractC0779a1.m213683d6(str, str));
            sb.append("CompanionObject");
            map3.put(sb.toString(), str.concat(".Companion"));
        }
        for (Map.Entry entry : f56914a2.entrySet()) {
            map3.put(((Class) entry.getKey()).getName(), "kotlin.Function" + ((Number) entry.getValue()).intValue());
        }
        f56915a3 = map3;
        LinkedHashMap linkedHashMap = new LinkedHashMap(AbstractC0770a1.m213612f7(map3.size()));
        for (Map.Entry entry2 : map3.entrySet()) {
            Object key = entry2.getKey();
            String str2 = (String) entry2.getValue();
            linkedHashMap.put(key, AbstractC0779a1.m213683d6(str2, str2));
        }
        f56916a4 = linkedHashMap;
    }

    public C0627in(Class cls) {
        this.f56917a0 = cls;
    }

    @Override // p000.InterfaceC0625il
    /* renamed from: a0 */
    public final Class mo213174a0() {
        return this.f56917a0;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C0627in) && t60.m214708c9(this).equals(t60.m214708c9((a80) obj));
    }

    public final int hashCode() {
        return t60.m214708c9(this).hashCode();
    }

    public final String toString() {
        return this.f56917a0.toString() + " (Kotlin reflection is not available)";
    }
}
