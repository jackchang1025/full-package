package p000;

import android.view.MenuItem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class a31 implements MenuItem.OnMenuItemClickListener {

    /* renamed from: a3 */
    public static final Class[] f34a3 = {MenuItem.class};

    /* renamed from: a0 */
    public final /* synthetic */ int f35a0 = 0;

    /* renamed from: a1 */
    public Object f36a1;

    /* renamed from: a2 */
    public Object f37a2;

    public /* synthetic */ a31() {
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final boolean onMenuItemClick(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        switch (this.f35a0) {
            case 0:
                Object obj = this.f36a1;
                Method method = (Method) this.f37a2;
                try {
                    if (method.getReturnType() == Boolean.TYPE) {
                        return ((Boolean) method.invoke(obj, menuItem)).booleanValue();
                    }
                    method.invoke(obj, menuItem);
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            default:
                return ((MenuItem.OnMenuItemClickListener) this.f36a1).onMenuItemClick(((jf0) this.f37a2).m212539b1(menuItem));
        }
    }

    public a31(jf0 jf0Var, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f37a2 = jf0Var;
        this.f36a1 = onMenuItemClickListener;
    }
}
