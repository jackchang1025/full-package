package p000;

import android.R;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import java.lang.reflect.InvocationTargetException;
import okhttp3.internal.p032ws.WebSocketProtocol;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tv */
/* loaded from: classes.dex */
public abstract class AbstractC1274tv {

    /* renamed from: a0 */
    public static final int[] f60282a0 = {R.attr.state_checked};

    /* renamed from: a1 */
    public static final int[] f60283a1 = new int[0];

    /* renamed from: a2 */
    public static final Rect f60284a2 = new Rect();

    /* renamed from: a0 */
    public static void m214790a0(Drawable drawable) {
        String name = drawable.getClass().getName();
        int i = Build.VERSION.SDK_INT;
        if (i < 29 || i >= 31 || !"android.graphics.drawable.ColorStateListDrawable".equals(name)) {
            return;
        }
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(f60282a0);
        } else {
            drawable.setState(f60283a1);
        }
        drawable.setState(state);
    }

    /* renamed from: a1 */
    public static Rect m214791a1(Drawable drawable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            Insets insetsM214781a0 = AbstractC1273tu.m214781a0(drawable);
            return new Rect(insetsM214781a0.left, insetsM214781a0.top, insetsM214781a0.right, insetsM214781a0.bottom);
        }
        Drawable drawableM213594e1 = kj1.m213594e1(drawable);
        if (i >= 29) {
            boolean z = AbstractC1272tt.f60263a0;
        } else if (AbstractC1272tt.f60263a0) {
            try {
                Object objInvoke = AbstractC1272tt.f60264a1.invoke(drawableM213594e1, null);
                if (objInvoke != null) {
                    return new Rect(AbstractC1272tt.f60265a2.getInt(objInvoke), AbstractC1272tt.f60266a3.getInt(objInvoke), AbstractC1272tt.f60267a4.getInt(objInvoke), AbstractC1272tt.f60268a5.getInt(objInvoke));
                }
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
        return f60284a2;
    }

    /* renamed from: a2 */
    public static PorterDuff.Mode m214792a2(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
