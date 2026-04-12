package p000;

import android.annotation.SuppressLint;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class v01 {
    public /* synthetic */ v01(AbstractC1120qr abstractC1120qr) {
        this();
    }

    @SuppressLint({"SyntheticAccessor"})
    public final void bind(l31 l31Var, Object[] objArr) {
        t60.m214695b6(l31Var, "statement");
        if (objArr == null) {
            return;
        }
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            i++;
            bind(l31Var, i, obj);
        }
    }

    private v01() {
    }

    private final void bind(l31 l31Var, int i, Object obj) {
        if (obj == null) {
            l31Var.mo213343a9(i);
            return;
        }
        if (obj instanceof byte[]) {
            l31Var.mo213347c1(i, (byte[]) obj);
            return;
        }
        if (obj instanceof Float) {
            l31Var.mo213345b1(i, ((Number) obj).floatValue());
            return;
        }
        if (obj instanceof Double) {
            l31Var.mo213345b1(i, ((Number) obj).doubleValue());
            return;
        }
        if (obj instanceof Long) {
            l31Var.mo213346b6(i, ((Number) obj).longValue());
            return;
        }
        if (obj instanceof Integer) {
            l31Var.mo213346b6(i, ((Number) obj).intValue());
            return;
        }
        if (obj instanceof Short) {
            l31Var.mo213346b6(i, ((Number) obj).shortValue());
            return;
        }
        if (obj instanceof Byte) {
            l31Var.mo213346b6(i, ((Number) obj).byteValue());
            return;
        }
        if (obj instanceof String) {
            l31Var.mo213341a6(i, (String) obj);
            return;
        }
        if (obj instanceof Boolean) {
            l31Var.mo213346b6(i, ((Boolean) obj).booleanValue() ? 1L : 0L);
            return;
        }
        throw new IllegalArgumentException("Cannot bind " + obj + " at index " + i + " Supported types: Null, ByteArray, Float, Double, Long, Int, Short, Byte, String");
    }
}
