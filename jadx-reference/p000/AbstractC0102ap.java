package p000;

import android.app.AppOpsManager;
import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ap */
/* loaded from: classes.dex */
public abstract class AbstractC0102ap {
    /* renamed from: a0 */
    public static <T> T m210489a0(Context context, Class<T> cls) {
        return (T) context.getSystemService(cls);
    }

    /* renamed from: a1 */
    public static int m210490a1(AppOpsManager appOpsManager, String str, String str2) {
        return appOpsManager.noteProxyOp(str, str2);
    }

    /* renamed from: a2 */
    public static int m210491a2(AppOpsManager appOpsManager, String str, String str2) {
        return appOpsManager.noteProxyOpNoThrow(str, str2);
    }

    /* renamed from: a3 */
    public static String m210492a3(String str) {
        return AppOpsManager.permissionToOp(str);
    }
}
