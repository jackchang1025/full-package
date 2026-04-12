package p000;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.lifecycle.C0077a1;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class pt0 {

    /* renamed from: a5 */
    public static final ot0 f59335a5 = new ot0(null);

    /* renamed from: a6 */
    public static final Class[] f59336a6 = {Boolean.TYPE, boolean[].class, Double.TYPE, double[].class, Integer.TYPE, int[].class, Long.TYPE, long[].class, String.class, String[].class, Binder.class, Bundle.class, Byte.TYPE, byte[].class, Character.TYPE, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, Float.TYPE, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, Short.TYPE, short[].class, SparseArray.class, Size.class, SizeF.class};

    /* renamed from: a0 */
    public final LinkedHashMap f59337a0;

    /* renamed from: a1 */
    public final LinkedHashMap f59338a1;

    /* renamed from: a2 */
    public final LinkedHashMap f59339a2;

    /* renamed from: a3 */
    public final LinkedHashMap f59340a3;

    /* renamed from: a4 */
    public final ut0 f59341a4;

    public pt0(HashMap map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.f59337a0 = linkedHashMap;
        this.f59338a1 = new LinkedHashMap();
        this.f59339a2 = new LinkedHashMap();
        this.f59340a3 = new LinkedHashMap();
        this.f59341a4 = new C0733jw(1, this);
        linkedHashMap.putAll(map);
    }

    /* renamed from: a0 */
    public static Bundle m214336a0(pt0 pt0Var) {
        String str;
        LinkedHashMap linkedHashMap = pt0Var.f59337a0;
        Iterator it = AbstractC0770a1.m213618g3(pt0Var.f59338a1).entrySet().iterator();
        do {
            if (!it.hasNext()) {
                Set<String> setKeySet = linkedHashMap.keySet();
                ArrayList arrayList = new ArrayList(setKeySet.size());
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                for (String str2 : setKeySet) {
                    arrayList.add(str2);
                    arrayList2.add(linkedHashMap.get(str2));
                }
                Pair[] pairArr = {new Pair("keys", arrayList), new Pair("values", arrayList2)};
                Bundle bundle = new Bundle(2);
                for (int i = 0; i < 2; i++) {
                    Pair pair = pairArr[i];
                    String str3 = (String) pair.f57556a0;
                    Object obj = pair.f57557a1;
                    if (obj == null) {
                        bundle.putString(str3, null);
                    } else if (obj instanceof Boolean) {
                        bundle.putBoolean(str3, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof Byte) {
                        bundle.putByte(str3, ((Number) obj).byteValue());
                    } else if (obj instanceof Character) {
                        bundle.putChar(str3, ((Character) obj).charValue());
                    } else if (obj instanceof Double) {
                        bundle.putDouble(str3, ((Number) obj).doubleValue());
                    } else if (obj instanceof Float) {
                        bundle.putFloat(str3, ((Number) obj).floatValue());
                    } else if (obj instanceof Integer) {
                        bundle.putInt(str3, ((Number) obj).intValue());
                    } else if (obj instanceof Long) {
                        bundle.putLong(str3, ((Number) obj).longValue());
                    } else if (obj instanceof Short) {
                        bundle.putShort(str3, ((Number) obj).shortValue());
                    } else if (obj instanceof Bundle) {
                        bundle.putBundle(str3, (Bundle) obj);
                    } else if (obj instanceof CharSequence) {
                        bundle.putCharSequence(str3, (CharSequence) obj);
                    } else if (obj instanceof Parcelable) {
                        bundle.putParcelable(str3, (Parcelable) obj);
                    } else if (obj instanceof boolean[]) {
                        bundle.putBooleanArray(str3, (boolean[]) obj);
                    } else if (obj instanceof byte[]) {
                        bundle.putByteArray(str3, (byte[]) obj);
                    } else if (obj instanceof char[]) {
                        bundle.putCharArray(str3, (char[]) obj);
                    } else if (obj instanceof double[]) {
                        bundle.putDoubleArray(str3, (double[]) obj);
                    } else if (obj instanceof float[]) {
                        bundle.putFloatArray(str3, (float[]) obj);
                    } else if (obj instanceof int[]) {
                        bundle.putIntArray(str3, (int[]) obj);
                    } else if (obj instanceof long[]) {
                        bundle.putLongArray(str3, (long[]) obj);
                    } else if (obj instanceof short[]) {
                        bundle.putShortArray(str3, (short[]) obj);
                    } else if (obj instanceof Object[]) {
                        Class<?> componentType = obj.getClass().getComponentType();
                        t60.m214692b3(componentType);
                        if (Parcelable.class.isAssignableFrom(componentType)) {
                            bundle.putParcelableArray(str3, (Parcelable[]) obj);
                        } else if (String.class.isAssignableFrom(componentType)) {
                            bundle.putStringArray(str3, (String[]) obj);
                        } else if (CharSequence.class.isAssignableFrom(componentType)) {
                            bundle.putCharSequenceArray(str3, (CharSequence[]) obj);
                        } else {
                            if (!Serializable.class.isAssignableFrom(componentType)) {
                                throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + str3 + '\"');
                            }
                            bundle.putSerializable(str3, (Serializable) obj);
                        }
                    } else if (obj instanceof Serializable) {
                        bundle.putSerializable(str3, (Serializable) obj);
                    } else if (obj instanceof IBinder) {
                        AbstractC0497fj.m212822a0(bundle, str3, (IBinder) obj);
                    } else if (obj instanceof Size) {
                        AbstractC0499fk.m212824a0(bundle, str3, (Size) obj);
                    } else {
                        if (!(obj instanceof SizeF)) {
                            throw new IllegalArgumentException("Illegal value type " + obj.getClass().getCanonicalName() + " for key \"" + str3 + '\"');
                        }
                        AbstractC0499fk.m212825a1(bundle, str3, (SizeF) obj);
                    }
                }
                return bundle;
            }
            Map.Entry entry = (Map.Entry) it.next();
            str = (String) entry.getKey();
            Bundle bundleMo210245a0 = ((ut0) entry.getValue()).mo210245a0();
            t60.m214695b6(str, "key");
            if (!f59335a5.validateValue(bundleMo210245a0)) {
                StringBuilder sb = new StringBuilder("Can't put value with type ");
                t60.m214692b3(bundleMo210245a0);
                sb.append(bundleMo210245a0.getClass());
                sb.append(" into saved state");
                throw new IllegalArgumentException(sb.toString());
            }
            Object obj2 = pt0Var.f59339a2.get(str);
            C0077a1 c0077a1 = obj2 instanceof C0077a1 ? (C0077a1) obj2 : null;
            if (c0077a1 != null) {
                c0077a1.m210242a4(bundleMo210245a0);
            } else {
                linkedHashMap.put(str, bundleMo210245a0);
            }
        } while (pt0Var.f59340a3.get(str) == null);
        throw new ClassCastException();
    }

    public pt0() {
        this.f59337a0 = new LinkedHashMap();
        this.f59338a1 = new LinkedHashMap();
        this.f59339a2 = new LinkedHashMap();
        this.f59340a3 = new LinkedHashMap();
        this.f59341a4 = new C0733jw(1, this);
    }
}
