package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.core.internal.view.SupportMenuItem;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class b31 {

    /* renamed from: a0 */
    public final Menu f45680a0;

    /* renamed from: a7 */
    public boolean f45687a7;

    /* renamed from: a8 */
    public int f45688a8;

    /* renamed from: a9 */
    public int f45689a9;

    /* renamed from: b0 */
    public CharSequence f45690b0;

    /* renamed from: b1 */
    public CharSequence f45691b1;

    /* renamed from: b2 */
    public int f45692b2;

    /* renamed from: b3 */
    public char f45693b3;

    /* renamed from: b4 */
    public int f45694b4;

    /* renamed from: b5 */
    public char f45695b5;

    /* renamed from: b6 */
    public int f45696b6;

    /* renamed from: b7 */
    public int f45697b7;

    /* renamed from: b8 */
    public boolean f45698b8;

    /* renamed from: b9 */
    public boolean f45699b9;

    /* renamed from: c0 */
    public boolean f45700c0;

    /* renamed from: c1 */
    public int f45701c1;

    /* renamed from: c2 */
    public int f45702c2;

    /* renamed from: c3 */
    public String f45703c3;

    /* renamed from: c4 */
    public String f45704c4;

    /* renamed from: c5 */
    public AbstractC0904n8 f45705c5;

    /* renamed from: c6 */
    public CharSequence f45706c6;

    /* renamed from: c7 */
    public CharSequence f45707c7;

    /* renamed from: d0 */
    public final /* synthetic */ c31 f45710d0;

    /* renamed from: c8 */
    public ColorStateList f45708c8 = null;

    /* renamed from: c9 */
    public PorterDuff.Mode f45709c9 = null;

    /* renamed from: a1 */
    public int f45681a1 = 0;

    /* renamed from: a2 */
    public int f45682a2 = 0;

    /* renamed from: a3 */
    public int f45683a3 = 0;

    /* renamed from: a4 */
    public int f45684a4 = 0;

    /* renamed from: a5 */
    public boolean f45685a5 = true;

    /* renamed from: a6 */
    public boolean f45686a6 = true;

    public b31(c31 c31Var, Menu menu) {
        this.f45710d0 = c31Var;
        this.f45680a0 = menu;
    }

    /* renamed from: a0 */
    public final Object m210533a0(String str, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, SecurityException {
        try {
            Constructor<?> constructor = Class.forName(str, false, this.f45710d0.f46068a2.getClassLoader()).getConstructor(clsArr);
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a1 */
    public final void m210534a1(MenuItem menuItem) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        c31 c31Var = this.f45710d0;
        Context context = c31Var.f46068a2;
        boolean z = false;
        menuItem.setChecked(this.f45698b8).setVisible(this.f45699b9).setEnabled(this.f45700c0).setCheckable(this.f45697b7 >= 1).setTitleCondensed(this.f45691b1).setIcon(this.f45692b2);
        int i = this.f45701c1;
        if (i >= 0) {
            menuItem.setShowAsAction(i);
        }
        if (this.f45704c4 != null) {
            if (context.isRestricted()) {
                throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
            }
            if (c31Var.f46069a3 == null) {
                c31Var.f46069a3 = c31.m210759a0(context);
            }
            Object obj = c31Var.f46069a3;
            String str = this.f45704c4;
            a31 a31Var = new a31();
            a31Var.f36a1 = obj;
            Class<?> cls = obj.getClass();
            try {
                a31Var.f37a2 = cls.getMethod(str, a31.f34a3);
                menuItem.setOnMenuItemClickListener(a31Var);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }
        if (this.f45697b7 >= 2) {
            if (menuItem instanceof ff0) {
                ((ff0) menuItem).m212799a3(true);
            } else if (menuItem instanceof jf0) {
                jf0 jf0Var = (jf0) menuItem;
                SupportMenuItem supportMenuItem = jf0Var.f57328a3;
                try {
                    if (jf0Var.f57329a4 == null) {
                        jf0Var.f57329a4 = supportMenuItem.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                    }
                    jf0Var.f57329a4.invoke(supportMenuItem, Boolean.TRUE);
                } catch (Exception unused) {
                }
            }
        }
        String str2 = this.f45703c3;
        if (str2 != null) {
            menuItem.setActionView((View) m210533a0(str2, c31.f46064a4, c31Var.f46066a0));
            z = true;
        }
        int i2 = this.f45702c2;
        if (i2 > 0 && !z) {
            menuItem.setActionView(i2);
        }
        AbstractC0904n8 abstractC0904n8 = this.f45705c5;
        if (abstractC0904n8 != null && (menuItem instanceof SupportMenuItem)) {
            ((SupportMenuItem) menuItem).setSupportActionProvider(abstractC0904n8);
        }
        CharSequence charSequence = this.f45706c6;
        boolean z2 = menuItem instanceof SupportMenuItem;
        if (z2) {
            ((SupportMenuItem) menuItem).setContentDescription(charSequence);
        } else if (Build.VERSION.SDK_INT >= 26) {
            df0.m212593a7(menuItem, charSequence);
        }
        CharSequence charSequence2 = this.f45707c7;
        if (z2) {
            ((SupportMenuItem) menuItem).setTooltipText(charSequence2);
        } else if (Build.VERSION.SDK_INT >= 26) {
            df0.m212598b2(menuItem, charSequence2);
        }
        char c = this.f45693b3;
        int i3 = this.f45694b4;
        if (z2) {
            ((SupportMenuItem) menuItem).setAlphabeticShortcut(c, i3);
        } else if (Build.VERSION.SDK_INT >= 26) {
            df0.m212592a6(menuItem, c, i3);
        }
        char c2 = this.f45695b5;
        int i4 = this.f45696b6;
        if (z2) {
            ((SupportMenuItem) menuItem).setNumericShortcut(c2, i4);
        } else if (Build.VERSION.SDK_INT >= 26) {
            df0.m212596b0(menuItem, c2, i4);
        }
        PorterDuff.Mode mode = this.f45709c9;
        if (mode != null) {
            if (z2) {
                ((SupportMenuItem) menuItem).setIconTintMode(mode);
            } else if (Build.VERSION.SDK_INT >= 26) {
                df0.m212595a9(menuItem, mode);
            }
        }
        ColorStateList colorStateList = this.f45708c8;
        if (colorStateList != null) {
            if (z2) {
                ((SupportMenuItem) menuItem).setIconTintList(colorStateList);
            } else if (Build.VERSION.SDK_INT >= 26) {
                df0.m212594a8(menuItem, colorStateList);
            }
        }
    }
}
