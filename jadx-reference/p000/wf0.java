package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class wf0 extends AbstractC0395cy implements Menu {

    /* renamed from: a3 */
    public final SupportMenu f60898a3;

    public wf0(Context context, SupportMenu supportMenu) {
        super(context);
        if (supportMenu == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.f60898a3 = supportMenu;
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return m212539b1(this.f60898a3.add(charSequence));
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int iAddIntentOptions = this.f60898a3.addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = m212539b1(menuItemArr2[i5]);
            }
        }
        return iAddIntentOptions;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return m212540b2(this.f60898a3.addSubMenu(charSequence));
    }

    @Override // android.view.Menu
    public final void clear() {
        t01 t01Var = (t01) this.f55539a1;
        if (t01Var != null) {
            t01Var.clear();
        }
        t01 t01Var2 = (t01) this.f55540a2;
        if (t01Var2 != null) {
            t01Var2.clear();
        }
        this.f60898a3.clear();
    }

    @Override // android.view.Menu
    public final void close() {
        this.f60898a3.close();
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i) {
        return m212539b1(this.f60898a3.findItem(i));
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i) {
        return m212539b1(this.f60898a3.getItem(i));
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        return this.f60898a3.hasVisibleItems();
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return this.f60898a3.isShortcutKey(i, keyEvent);
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i, int i2) {
        return this.f60898a3.performIdentifierAction(i, i2);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return this.f60898a3.performShortcut(i, keyEvent, i2);
    }

    @Override // android.view.Menu
    public final void removeGroup(int i) {
        if (((t01) this.f55539a1) != null) {
            int i2 = 0;
            while (true) {
                t01 t01Var = (t01) this.f55539a1;
                if (i2 >= t01Var.f60117a2) {
                    break;
                }
                if (((SupportMenuItem) t01Var.m214679a7(i2)).getGroupId() == i) {
                    ((t01) this.f55539a1).m214680a8(i2);
                    i2--;
                }
                i2++;
            }
        }
        this.f60898a3.removeGroup(i);
    }

    @Override // android.view.Menu
    public final void removeItem(int i) {
        if (((t01) this.f55539a1) != null) {
            int i2 = 0;
            while (true) {
                t01 t01Var = (t01) this.f55539a1;
                if (i2 >= t01Var.f60117a2) {
                    break;
                }
                if (((SupportMenuItem) t01Var.m214679a7(i2)).getItemId() == i) {
                    ((t01) this.f55539a1).m214680a8(i2);
                    break;
                }
                i2++;
            }
        }
        this.f60898a3.removeItem(i);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        this.f60898a3.setGroupCheckable(i, z, z2);
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i, boolean z) {
        this.f60898a3.setGroupEnabled(i, z);
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i, boolean z) {
        this.f60898a3.setGroupVisible(i, z);
    }

    @Override // android.view.Menu
    public final void setQwertyMode(boolean z) {
        this.f60898a3.setQwertyMode(z);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f60898a3.size();
    }

    @Override // android.view.Menu
    public final MenuItem add(int i) {
        return m212539b1(this.f60898a3.add(i));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i) {
        return m212540b2(this.f60898a3.addSubMenu(i));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return m212539b1(this.f60898a3.add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return m212540b2(this.f60898a3.addSubMenu(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, int i4) {
        return m212539b1(this.f60898a3.add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return m212540b2(this.f60898a3.addSubMenu(i, i2, i3, i4));
    }
}
