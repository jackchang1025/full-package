package p000;

import android.widget.AbsListView;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: uk */
/* loaded from: classes.dex */
public abstract class AbstractC1303uk {

    /* renamed from: a0 */
    public static final Field f60457a0;

    static {
        Field declaredField = null;
        try {
            declaredField = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException unused) {
        }
        f60457a0 = declaredField;
    }
}
