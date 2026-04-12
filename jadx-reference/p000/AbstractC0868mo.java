package p000;

import android.content.Context;
import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mo */
/* loaded from: classes.dex */
public abstract class AbstractC0868mo {
    /* renamed from: a0 */
    public static File[] m214009a0(Context context) {
        return context.getExternalCacheDirs();
    }

    /* renamed from: a1 */
    public static File[] m214010a1(Context context, String str) {
        return context.getExternalFilesDirs(str);
    }

    /* renamed from: a2 */
    public static File[] m214011a2(Context context) {
        return context.getObbDirs();
    }
}
