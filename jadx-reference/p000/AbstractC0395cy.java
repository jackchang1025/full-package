package p000;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cy */
/* loaded from: classes.dex */
public abstract class AbstractC0395cy {

    /* renamed from: a0 */
    public Object f55538a0;

    /* renamed from: a1 */
    public Object f55539a1;

    /* renamed from: a2 */
    public Object f55540a2;

    public AbstractC0395cy(Context context) {
        this.f55538a0 = context;
    }

    /* renamed from: b0 */
    public static boolean m212537b0(Object obj, Set set) {
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (set.size() == set2.size()) {
                return set.containsAll(set2);
            }
            return false;
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    /* renamed from: a0 */
    public abstract void mo212538a0();

    /* renamed from: a1 */
    public abstract void mo210650a1();

    /* renamed from: a2 */
    public abstract Object mo210651a2(int i, int i2);

    /* renamed from: a3 */
    public abstract Map mo210652a3();

    /* renamed from: a4 */
    public abstract int mo210653a4();

    /* renamed from: a5 */
    public abstract int mo210654a5(Object obj);

    /* renamed from: a6 */
    public abstract int mo210655a6(Object obj);

    /* renamed from: a7 */
    public abstract void mo210656a7(Object obj, Object obj2);

    /* renamed from: a8 */
    public abstract void mo210657a8(int i);

    /* renamed from: a9 */
    public abstract Object mo210658a9(int i, Object obj);

    /* renamed from: b1 */
    public MenuItem m212539b1(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (((t01) this.f55539a1) == null) {
            this.f55539a1 = new t01();
        }
        MenuItem menuItem2 = (MenuItem) ((t01) this.f55539a1).getOrDefault(supportMenuItem, null);
        if (menuItem2 != null) {
            return menuItem2;
        }
        jf0 jf0Var = new jf0((Context) this.f55538a0, supportMenuItem);
        ((t01) this.f55539a1).put(supportMenuItem, jf0Var);
        return jf0Var;
    }

    /* renamed from: b2 */
    public SubMenu m212540b2(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (((t01) this.f55540a2) == null) {
            this.f55540a2 = new t01();
        }
        SubMenu subMenu2 = (SubMenu) ((t01) this.f55540a2).getOrDefault(supportSubMenu, null);
        if (subMenu2 != null) {
            return subMenu2;
        }
        s21 s21Var = new s21((Context) this.f55538a0, supportSubMenu);
        ((t01) this.f55540a2).put(supportSubMenu, s21Var);
        return s21Var;
    }

    /* renamed from: b3 */
    public abstract void mo212541b3();

    /* renamed from: b4 */
    public abstract void mo212542b4(C0410dc c0410dc);

    /* renamed from: b5 */
    public abstract void mo212543b5();

    /* renamed from: b6 */
    public abstract void mo212544b6();

    /* renamed from: b7 */
    public Object[] m212545b7(Object[] objArr, int i) {
        int iMo210653a4 = mo210653a4();
        if (objArr.length < iMo210653a4) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), iMo210653a4);
        }
        for (int i2 = 0; i2 < iMo210653a4; i2++) {
            objArr[i2] = mo210651a2(i2, i);
        }
        if (objArr.length > iMo210653a4) {
            objArr[iMo210653a4] = null;
        }
        return objArr;
    }

    /* renamed from: b8 */
    public abstract void mo212546b8();

    public AbstractC0395cy(int i) {
        this.f55539a1 = new float[i * 2];
        this.f55540a2 = new int[i];
    }
}
