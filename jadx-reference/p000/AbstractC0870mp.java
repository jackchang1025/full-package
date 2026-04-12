package p000;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mp */
/* loaded from: classes.dex */
public abstract class AbstractC0870mp {
    /* renamed from: a0 */
    public static File m214012a0(Context context) {
        return context.getCodeCacheDir();
    }

    /* renamed from: a1 */
    public static Drawable m214013a1(Context context, int i) {
        return context.getDrawable(i);
    }

    /* renamed from: a2 */
    public static File m214014a2(Context context) {
        return context.getNoBackupFilesDir();
    }
}
