package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mq */
/* loaded from: classes.dex */
public abstract class AbstractC0871mq {
    /* renamed from: a0 */
    public static int m214015a0(Context context, int i) {
        return context.getColor(i);
    }

    /* renamed from: a1 */
    public static <T> T m214016a1(Context context, Class<T> cls) {
        return (T) context.getSystemService(cls);
    }

    /* renamed from: a2 */
    public static String m214017a2(Context context, Class<?> cls) {
        return context.getSystemServiceName(cls);
    }
}
