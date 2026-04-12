package p000;

import android.os.Build;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jg0 {

    /* renamed from: a0 */
    public Method f57331a0;

    /* renamed from: a1 */
    public Method f57332a1;

    /* renamed from: a2 */
    public Method f57333a2;

    public jg0(Method method, Method method2, Method method3) {
        this.f57331a0 = method;
        this.f57332a1 = method2;
        this.f57333a2 = method3;
    }

    /* renamed from: a0 */
    public static void m213311a0() {
        if (Build.VERSION.SDK_INT >= 29) {
            throw new UnsupportedClassVersionError("This function can only be used for API Level < 29.");
        }
    }
}
