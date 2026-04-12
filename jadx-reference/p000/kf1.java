package p000;

import android.view.View;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class kf1 {

    /* renamed from: a0 */
    public static final Field f57515a0;

    /* renamed from: a1 */
    public static final Field f57516a1;

    /* renamed from: a2 */
    public static final Field f57517a2;

    /* renamed from: a3 */
    public static final boolean f57518a3;

    static {
        try {
            Field declaredField = View.class.getDeclaredField("mAttachInfo");
            f57515a0 = declaredField;
            declaredField.setAccessible(true);
            Class<?> cls = Class.forName("android.view.View$AttachInfo");
            Field declaredField2 = cls.getDeclaredField("mStableInsets");
            f57516a1 = declaredField2;
            declaredField2.setAccessible(true);
            Field declaredField3 = cls.getDeclaredField("mContentInsets");
            f57517a2 = declaredField3;
            declaredField3.setAccessible(true);
            f57518a3 = true;
        } catch (ReflectiveOperationException e) {
            e.getMessage();
        }
    }
}
