package p000;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.internal.view.SupportMenu;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class bf0 implements SupportMenu {

    /* renamed from: c4 */
    public static final int[] f45865c4 = {1, 4, 5, 3, 2, 0};

    /* renamed from: a0 */
    public final Context f45866a0;

    /* renamed from: a1 */
    public final Resources f45867a1;

    /* renamed from: a2 */
    public boolean f45868a2;

    /* renamed from: a3 */
    public final boolean f45869a3;

    /* renamed from: a4 */
    public ze0 f45870a4;

    /* renamed from: a5 */
    public final ArrayList f45871a5;

    /* renamed from: a6 */
    public final ArrayList f45872a6;

    /* renamed from: a7 */
    public boolean f45873a7;

    /* renamed from: a8 */
    public final ArrayList f45874a8;

    /* renamed from: a9 */
    public final ArrayList f45875a9;

    /* renamed from: b0 */
    public boolean f45876b0;

    /* renamed from: b2 */
    public CharSequence f45878b2;

    /* renamed from: b3 */
    public Drawable f45879b3;

    /* renamed from: b4 */
    public View f45880b4;

    /* renamed from: c1 */
    public ff0 f45887c1;

    /* renamed from: c3 */
    public boolean f45889c3;

    /* renamed from: b1 */
    public int f45877b1 = 0;

    /* renamed from: b5 */
    public boolean f45881b5 = false;

    /* renamed from: b6 */
    public boolean f45882b6 = false;

    /* renamed from: b7 */
    public boolean f45883b7 = false;

    /* renamed from: b8 */
    public boolean f45884b8 = false;

    /* renamed from: b9 */
    public final ArrayList f45885b9 = new ArrayList();

    /* renamed from: c0 */
    public final CopyOnWriteArrayList f45886c0 = new CopyOnWriteArrayList();

    /* renamed from: c2 */
    public boolean f45888c2 = false;

    public bf0(Context context) {
        boolean zM215386a1;
        boolean z = false;
        this.f45866a0 = context;
        Resources resources = context.getResources();
        this.f45867a1 = resources;
        this.f45871a5 = new ArrayList();
        this.f45872a6 = new ArrayList();
        this.f45873a7 = true;
        this.f45874a8 = new ArrayList();
        this.f45875a9 = new ArrayList();
        this.f45876b0 = true;
        if (resources.getConfiguration().keyboard != 1) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            Method method = ab1.f43607a0;
            if (Build.VERSION.SDK_INT >= 28) {
                zM215386a1 = za1.m215386a1(viewConfiguration);
            } else {
                Resources resources2 = context.getResources();
                int identifier = resources2.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
                zM215386a1 = identifier != 0 && resources2.getBoolean(identifier);
            }
            if (zM215386a1) {
                z = true;
            }
        }
        this.f45869a3 = z;
    }

    /* renamed from: a0 */
    public ff0 mo210688a0(int i, int i2, int i3, CharSequence charSequence) {
        int i4;
        int i5 = ((-65536) & i3) >> 16;
        if (i5 < 0 || i5 >= 6) {
            throw new IllegalArgumentException("order does not contain a valid category.");
        }
        int i6 = (f45865c4[i5] << 16) | (65535 & i3);
        ff0 ff0Var = new ff0(this, i, i2, i3, i6, charSequence, this.f45877b1);
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                i4 = 0;
                break;
            }
            if (((ff0) arrayList.get(size)).f56208a3 <= i6) {
                i4 = size + 1;
                break;
            }
            size--;
        }
        arrayList.add(i4, ff0Var);
        mo210703b5(true);
        return ff0Var;
    }

    /* renamed from: a1 */
    public final void m210689a1(tf0 tf0Var, Context context) {
        this.f45886c0.add(new WeakReference(tf0Var));
        tf0Var.mo65a8(context, this);
        this.f45876b0 = true;
    }

    /* renamed from: a2 */
    public final void m210690a2(boolean z) {
        if (this.f45884b8) {
            return;
        }
        this.f45884b8 = true;
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            tf0 tf0Var = (tf0) weakReference.get();
            if (tf0Var == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                tf0Var.mo61a0(this, z);
            }
        }
        this.f45884b8 = false;
    }

    /* renamed from: a3 */
    public boolean mo210691a3(ff0 ff0Var) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        boolean zMo62a2 = false;
        if (!copyOnWriteArrayList.isEmpty() && this.f45887c1 == ff0Var) {
            m210712c4();
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                tf0 tf0Var = (tf0) weakReference.get();
                if (tf0Var == null) {
                    copyOnWriteArrayList.remove(weakReference);
                } else {
                    zMo62a2 = tf0Var.mo62a2(ff0Var);
                    if (zMo62a2) {
                        break;
                    }
                }
            }
            m210711c3();
            if (zMo62a2) {
                this.f45887c1 = null;
            }
        }
        return zMo62a2;
    }

    /* renamed from: a4 */
    public boolean mo210692a4(bf0 bf0Var, MenuItem menuItem) {
        ze0 ze0Var = this.f45870a4;
        return ze0Var != null && ze0Var.mo214682a4(bf0Var, menuItem);
    }

    /* renamed from: a5 */
    public boolean mo210693a5(ff0 ff0Var) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        boolean zMo64a6 = false;
        if (copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        m210712c4();
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            tf0 tf0Var = (tf0) weakReference.get();
            if (tf0Var == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                zMo64a6 = tf0Var.mo64a6(ff0Var);
                if (zMo64a6) {
                    break;
                }
            }
        }
        m210711c3();
        if (zMo64a6) {
            this.f45887c1 = ff0Var;
        }
        return zMo64a6;
    }

    /* renamed from: a6 */
    public final ff0 m210694a6(int i, KeyEvent keyEvent) {
        ArrayList arrayList = this.f45885b9;
        arrayList.clear();
        m210695a7(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return (ff0) arrayList.get(0);
        }
        boolean zMo210701b3 = mo210701b3();
        for (int i2 = 0; i2 < size; i2++) {
            ff0 ff0Var = (ff0) arrayList.get(i2);
            char c = zMo210701b3 ? ff0Var.f56214a9 : ff0Var.f56212a7;
            char[] cArr = keyData.meta;
            if ((c == cArr[0] && (metaState & 2) == 0) || ((c == cArr[2] && (metaState & 2) != 0) || (zMo210701b3 && c == '\b' && i == 67))) {
                return ff0Var;
            }
        }
        return null;
    }

    /* renamed from: a7 */
    public final void m210695a7(List list, int i, KeyEvent keyEvent) {
        boolean zMo210701b3 = mo210701b3();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            ArrayList arrayList = this.f45871a5;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ff0 ff0Var = (ff0) arrayList.get(i2);
                if (ff0Var.hasSubMenu()) {
                    ff0Var.f56219b4.m210695a7(list, i, keyEvent);
                }
                char c = zMo210701b3 ? ff0Var.f56214a9 : ff0Var.f56212a7;
                if ((modifiers & SupportMenu.SUPPORTED_MODIFIERS_MASK) == ((zMo210701b3 ? ff0Var.f56215b0 : ff0Var.f56213a8) & SupportMenu.SUPPORTED_MODIFIERS_MASK) && c != 0) {
                    char[] cArr = keyData.meta;
                    if ((c == cArr[0] || c == cArr[2] || (zMo210701b3 && c == '\b' && i == 67)) && ff0Var.isEnabled()) {
                        list.add(ff0Var);
                    }
                }
            }
        }
    }

    /* renamed from: a8 */
    public final void m210696a8() {
        ArrayList arrayListM210699b1 = m210699b1();
        if (this.f45876b0) {
            CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
            Iterator it = copyOnWriteArrayList.iterator();
            boolean zMo68b1 = false;
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                tf0 tf0Var = (tf0) weakReference.get();
                if (tf0Var == null) {
                    copyOnWriteArrayList.remove(weakReference);
                } else {
                    zMo68b1 |= tf0Var.mo68b1();
                }
            }
            ArrayList arrayList = this.f45874a8;
            ArrayList arrayList2 = this.f45875a9;
            if (zMo68b1) {
                arrayList.clear();
                arrayList2.clear();
                int size = arrayListM210699b1.size();
                for (int i = 0; i < size; i++) {
                    ff0 ff0Var = (ff0) arrayListM210699b1.get(i);
                    if ((ff0Var.f56228c3 & 32) == 32) {
                        arrayList.add(ff0Var);
                    } else {
                        arrayList2.add(ff0Var);
                    }
                }
            } else {
                arrayList.clear();
                arrayList2.clear();
                arrayList2.addAll(m210699b1());
            }
            this.f45876b0 = false;
        }
    }

    /* renamed from: a9 */
    public String mo210697a9() {
        return "android:menu:actionviewstates";
    }

    @Override // android.view.Menu
    public final MenuItem add(CharSequence charSequence) {
        return mo210688a0(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public final int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        int i5;
        PackageManager packageManager = this.f45866a0.getPackageManager();
        List<ResolveInfo> listQueryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = listQueryIntentActivityOptions != null ? listQueryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i6 = 0; i6 < size; i6++) {
            ResolveInfo resolveInfo = listQueryIntentActivityOptions.get(i6);
            int i7 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i7 < 0 ? intent : intentArr[i7]);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            ff0 ff0VarMo210688a0 = mo210688a0(i, i2, i3, resolveInfo.loadLabel(packageManager));
            ff0VarMo210688a0.setIcon(resolveInfo.loadIcon(packageManager));
            ff0VarMo210688a0.f56211a6 = intent2;
            if (menuItemArr != null && (i5 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i5] = ff0VarMo210688a0;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    /* renamed from: b1 */
    public final ArrayList m210699b1() {
        boolean z = this.f45873a7;
        ArrayList arrayList = this.f45872a6;
        if (!z) {
            return arrayList;
        }
        arrayList.clear();
        ArrayList arrayList2 = this.f45871a5;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            ff0 ff0Var = (ff0) arrayList2.get(i);
            if (ff0Var.isVisible()) {
                arrayList.add(ff0Var);
            }
        }
        this.f45873a7 = false;
        this.f45876b0 = true;
        return arrayList;
    }

    /* renamed from: b2 */
    public boolean mo210700b2() {
        return this.f45888c2;
    }

    /* renamed from: b3 */
    public boolean mo210701b3() {
        return this.f45868a2;
    }

    /* renamed from: b4 */
    public boolean mo210702b4() {
        return this.f45869a3;
    }

    /* renamed from: b5 */
    public void mo210703b5(boolean z) {
        if (this.f45881b5) {
            this.f45882b6 = true;
            if (z) {
                this.f45883b7 = true;
                return;
            }
            return;
        }
        if (z) {
            this.f45873a7 = true;
            this.f45876b0 = true;
        }
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        if (copyOnWriteArrayList.isEmpty()) {
            return;
        }
        m210712c4();
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            tf0 tf0Var = (tf0) weakReference.get();
            if (tf0Var == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                tf0Var.mo66a9(z);
            }
        }
        m210711c3();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m210704b6(MenuItem menuItem, tf0 tf0Var, int i) {
        boolean zExpandActionView;
        ff0 ff0Var = (ff0) menuItem;
        if (ff0Var == null || !ff0Var.isEnabled()) {
            return false;
        }
        bf0 bf0Var = ff0Var.f56218b3;
        MenuItem.OnMenuItemClickListener onMenuItemClickListener = ff0Var.f56220b5;
        if ((onMenuItemClickListener == null || !onMenuItemClickListener.onMenuItemClick(ff0Var)) && !bf0Var.mo210692a4(bf0Var, ff0Var)) {
            Intent intent = ff0Var.f56211a6;
            if (intent != null) {
                try {
                    bf0Var.f45866a0.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                }
            } else {
                AbstractC0904n8 abstractC0904n8 = ff0Var.f56231c6;
                zExpandActionView = abstractC0904n8 != null && ((gf0) abstractC0904n8).f56454a1.onPerformDefaultAction();
            }
        }
        AbstractC0904n8 abstractC0904n82 = ff0Var.f56231c6;
        boolean z = abstractC0904n82 != null && ((gf0) abstractC0904n82).f56454a1.hasSubMenu();
        if (ff0Var.m212798a2()) {
            zExpandActionView |= ff0Var.expandActionView();
            if (zExpandActionView) {
                m210690a2(true);
            }
        } else if (ff0Var.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                m210690a2(false);
            }
            if (!ff0Var.hasSubMenu()) {
                r21 r21Var = new r21(this.f45866a0, this, ff0Var);
                ff0Var.f56219b4 = r21Var;
                r21Var.setHeaderTitle(ff0Var.f56209a4);
            }
            r21 r21Var2 = ff0Var.f56219b4;
            if (z) {
                gf0 gf0Var = (gf0) abstractC0904n82;
                gf0Var.f56454a1.onPrepareSubMenu(gf0Var.f56455a2.m212540b2(r21Var2));
            }
            CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
            if (!copyOnWriteArrayList.isEmpty()) {
                zMo67b0 = tf0Var != null ? tf0Var.mo67b0(r21Var2) : false;
                Iterator it = copyOnWriteArrayList.iterator();
                while (it.hasNext()) {
                    WeakReference weakReference = (WeakReference) it.next();
                    tf0 tf0Var2 = (tf0) weakReference.get();
                    if (tf0Var2 == null) {
                        copyOnWriteArrayList.remove(weakReference);
                    } else if (!zMo67b0) {
                        zMo67b0 = tf0Var2.mo67b0(r21Var2);
                    }
                }
            }
            zExpandActionView |= zMo67b0;
            if (!zExpandActionView) {
                m210690a2(true);
            }
        } else if ((i & 1) == 0) {
            m210690a2(true);
        }
        return zExpandActionView;
    }

    /* renamed from: b7 */
    public final void m210705b7(tf0 tf0Var) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            tf0 tf0Var2 = (tf0) weakReference.get();
            if (tf0Var2 == null || tf0Var2 == tf0Var) {
                copyOnWriteArrayList.remove(weakReference);
            }
        }
    }

    /* renamed from: b8 */
    public final void m210706b8(Bundle bundle) {
        MenuItem menuItemFindItem;
        if (bundle == null) {
            return;
        }
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(mo210697a9());
        int size = this.f45871a5.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                actionView.restoreHierarchyState(sparseParcelableArray);
            }
            if (item.hasSubMenu()) {
                ((r21) item.getSubMenu()).m210706b8(bundle);
            }
        }
        int i2 = bundle.getInt("android:menu:expandedactionview");
        if (i2 <= 0 || (menuItemFindItem = findItem(i2)) == null) {
            return;
        }
        menuItemFindItem.expandActionView();
    }

    /* renamed from: b9 */
    public final void m210707b9(Bundle bundle) {
        Parcelable parcelable;
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray != null) {
            CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
            if (copyOnWriteArrayList.isEmpty()) {
                return;
            }
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                tf0 tf0Var = (tf0) weakReference.get();
                if (tf0Var == null) {
                    copyOnWriteArrayList.remove(weakReference);
                } else {
                    int id = tf0Var.getId();
                    if (id > 0 && (parcelable = (Parcelable) sparseParcelableArray.get(id)) != null) {
                        tf0Var.mo63a4(parcelable);
                    }
                }
            }
        }
    }

    /* renamed from: c0 */
    public final void m210708c0(Bundle bundle) {
        int size = this.f45871a5.size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (actionView != null && actionView.getId() != -1) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((r21) item.getSubMenu()).m210708c0(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(mo210697a9(), sparseArray);
        }
    }

    /* renamed from: c1 */
    public final void m210709c1(Bundle bundle) {
        Parcelable parcelableMo69b2;
        CopyOnWriteArrayList copyOnWriteArrayList = this.f45886c0;
        if (copyOnWriteArrayList.isEmpty()) {
            return;
        }
        SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            tf0 tf0Var = (tf0) weakReference.get();
            if (tf0Var == null) {
                copyOnWriteArrayList.remove(weakReference);
            } else {
                int id = tf0Var.getId();
                if (id > 0 && (parcelableMo69b2 = tf0Var.mo69b2()) != null) {
                    sparseArray.put(id, parcelableMo69b2);
                }
            }
        }
        bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
    }

    /* renamed from: c2 */
    public final void m210710c2(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        if (view != null) {
            this.f45880b4 = view;
            this.f45878b2 = null;
            this.f45879b3 = null;
        } else {
            if (i > 0) {
                this.f45878b2 = this.f45867a1.getText(i);
            } else if (charSequence != null) {
                this.f45878b2 = charSequence;
            }
            if (i2 > 0) {
                this.f45879b3 = AbstractC0870mp.m214013a1(this.f45866a0, i2);
            } else if (drawable != null) {
                this.f45879b3 = drawable;
            }
            this.f45880b4 = null;
        }
        mo210703b5(false);
    }

    /* renamed from: c3 */
    public final void m210711c3() {
        this.f45881b5 = false;
        if (this.f45882b6) {
            this.f45882b6 = false;
            mo210703b5(this.f45883b7);
        }
    }

    /* renamed from: c4 */
    public final void m210712c4() {
        if (this.f45881b5) {
            return;
        }
        this.f45881b5 = true;
        this.f45882b6 = false;
        this.f45883b7 = false;
    }

    @Override // android.view.Menu
    public final void clear() {
        ff0 ff0Var = this.f45887c1;
        if (ff0Var != null) {
            mo210691a3(ff0Var);
        }
        this.f45871a5.clear();
        mo210703b5(true);
    }

    public final void clearHeader() {
        this.f45879b3 = null;
        this.f45878b2 = null;
        this.f45880b4 = null;
        mo210703b5(false);
    }

    @Override // android.view.Menu
    public final void close() {
        m210690a2(true);
    }

    @Override // android.view.Menu
    public final MenuItem findItem(int i) {
        MenuItem menuItemFindItem;
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ff0 ff0Var = (ff0) arrayList.get(i2);
            if (ff0Var.f56205a0 == i) {
                return ff0Var;
            }
            if (ff0Var.hasSubMenu() && (menuItemFindItem = ff0Var.f56219b4.findItem(i)) != null) {
                return menuItemFindItem;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public final MenuItem getItem(int i) {
        return (MenuItem) this.f45871a5.get(i);
    }

    @Override // android.view.Menu
    public final boolean hasVisibleItems() {
        if (this.f45889c3) {
            return true;
        }
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((ff0) arrayList.get(i)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.Menu
    public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return m210694a6(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public final boolean performIdentifierAction(int i, int i2) {
        return m210704b6(findItem(i), null, i2);
    }

    @Override // android.view.Menu
    public final boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        ff0 ff0VarM210694a6 = m210694a6(i, keyEvent);
        boolean zM210704b6 = ff0VarM210694a6 != null ? m210704b6(ff0VarM210694a6, null, i2) : false;
        if ((i2 & 2) != 0) {
            m210690a2(true);
        }
        return zM210704b6;
    }

    @Override // android.view.Menu
    public final void removeGroup(int i) {
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                i3 = -1;
                break;
            } else if (((ff0) arrayList.get(i3)).f56206a1 == i) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 >= 0) {
            int size2 = arrayList.size() - i3;
            while (true) {
                int i4 = i2 + 1;
                if (i2 >= size2 || ((ff0) arrayList.get(i3)).f56206a1 != i) {
                    break;
                }
                if (i3 >= 0 && i3 < arrayList.size()) {
                    arrayList.remove(i3);
                }
                i2 = i4;
            }
            mo210703b5(true);
        }
    }

    @Override // android.view.Menu
    public final void removeItem(int i) {
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (((ff0) arrayList.get(i2)).f56205a0 == i) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 < 0 || i2 >= arrayList.size()) {
            return;
        }
        arrayList.remove(i2);
        mo210703b5(true);
    }

    @Override // android.view.Menu
    public final void setGroupCheckable(int i, boolean z, boolean z2) {
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ff0 ff0Var = (ff0) arrayList.get(i2);
            if (ff0Var.f56206a1 == i) {
                ff0Var.m212799a3(z2);
                ff0Var.setCheckable(z);
            }
        }
    }

    @Override // androidx.core.internal.view.SupportMenu, android.view.Menu
    public void setGroupDividerEnabled(boolean z) {
        this.f45888c2 = z;
    }

    @Override // android.view.Menu
    public final void setGroupEnabled(int i, boolean z) {
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ff0 ff0Var = (ff0) arrayList.get(i2);
            if (ff0Var.f56206a1 == i) {
                ff0Var.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public final void setGroupVisible(int i, boolean z) {
        ArrayList arrayList = this.f45871a5;
        int size = arrayList.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ff0 ff0Var = (ff0) arrayList.get(i2);
            if (ff0Var.f56206a1 == i) {
                int i3 = ff0Var.f56228c3;
                int i4 = (i3 & (-9)) | (z ? 0 : 8);
                ff0Var.f56228c3 = i4;
                if (i3 != i4) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            mo210703b5(true);
        }
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.f45868a2 = z;
        mo210703b5(false);
    }

    @Override // android.view.Menu
    public final int size() {
        return this.f45871a5.size();
    }

    @Override // android.view.Menu
    public final MenuItem add(int i) {
        return mo210688a0(0, 0, 0, this.f45867a1.getString(i));
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.f45867a1.getString(i));
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return mo210688a0(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        ff0 ff0VarMo210688a0 = mo210688a0(i, i2, i3, charSequence);
        r21 r21Var = new r21(this.f45866a0, this, ff0VarMo210688a0);
        ff0VarMo210688a0.f56219b4 = r21Var;
        r21Var.setHeaderTitle(ff0VarMo210688a0.f56209a4);
        return r21Var;
    }

    @Override // android.view.Menu
    public final MenuItem add(int i, int i2, int i3, int i4) {
        return mo210688a0(i, i2, i3, this.f45867a1.getString(i4));
    }

    /* renamed from: b0 */
    public bf0 mo210698b0() {
        return this;
    }

    @Override // android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.f45867a1.getString(i4));
    }
}
