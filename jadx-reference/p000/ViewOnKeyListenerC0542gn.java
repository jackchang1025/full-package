package p000;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.widget.C0043a3;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gn */
/* loaded from: classes.dex */
public final class ViewOnKeyListenerC0542gn extends kf0 implements View.OnKeyListener, PopupWindow.OnDismissListener {

    /* renamed from: c6 */
    public static final int f56519c6 = R$layout.abc_cascading_menu_item_layout;

    /* renamed from: a1 */
    public final Context f56520a1;

    /* renamed from: a2 */
    public final int f56521a2;

    /* renamed from: a3 */
    public final int f56522a3;

    /* renamed from: a4 */
    public final boolean f56523a4;

    /* renamed from: a5 */
    public final Handler f56524a5;

    /* renamed from: b3 */
    public View f56532b3;

    /* renamed from: b4 */
    public View f56533b4;

    /* renamed from: b5 */
    public int f56534b5;

    /* renamed from: b6 */
    public boolean f56535b6;

    /* renamed from: b7 */
    public boolean f56536b7;

    /* renamed from: b8 */
    public int f56537b8;

    /* renamed from: b9 */
    public int f56538b9;

    /* renamed from: c1 */
    public boolean f56540c1;

    /* renamed from: c2 */
    public sf0 f56541c2;

    /* renamed from: c3 */
    public ViewTreeObserver f56542c3;

    /* renamed from: c4 */
    public PopupWindow.OnDismissListener f56543c4;

    /* renamed from: c5 */
    public boolean f56544c5;

    /* renamed from: a6 */
    public final ArrayList f56525a6 = new ArrayList();

    /* renamed from: a7 */
    public final ArrayList f56526a7 = new ArrayList();

    /* renamed from: a8 */
    public final ViewTreeObserverOnGlobalLayoutListenerC0937o2 f56527a8 = new ViewTreeObserverOnGlobalLayoutListenerC0937o2(3, this);

    /* renamed from: a9 */
    public final ViewOnAttachStateChangeListenerC0539gk f56528a9 = new ViewOnAttachStateChangeListenerC0539gk(0, this);

    /* renamed from: b0 */
    public final tg0 f56529b0 = new tg0(7, this);

    /* renamed from: b1 */
    public int f56530b1 = 0;

    /* renamed from: b2 */
    public int f56531b2 = 0;

    /* renamed from: c0 */
    public boolean f56539c0 = false;

    public ViewOnKeyListenerC0542gn(Context context, View view, int i, boolean z) {
        this.f56520a1 = context;
        this.f56532b3 = view;
        this.f56522a3 = i;
        this.f56523a4 = z;
        WeakHashMap weakHashMap = xa1.f61054a0;
        this.f56534b5 = ga1.m212904a3(view) != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.f56521a2 = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.f56524a5 = new Handler();
    }

    @Override // p000.tf0
    /* renamed from: a0 */
    public final void mo61a0(bf0 bf0Var, boolean z) {
        ArrayList arrayList = this.f56526a7;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (bf0Var == ((C0541gm) arrayList.get(i)).f56517a1) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            return;
        }
        int i2 = i + 1;
        if (i2 < arrayList.size()) {
            ((C0541gm) arrayList.get(i2)).f56517a1.m210690a2(false);
        }
        C0541gm c0541gm = (C0541gm) arrayList.remove(i);
        bf0 bf0Var2 = c0541gm.f56517a1;
        C0043a3 c0043a3 = c0541gm.f56516a0;
        C1402x5 c1402x5 = c0043a3.f43997c5;
        bf0Var2.m210705b7(this);
        if (this.f56544c5) {
            of0.m214189a1(c1402x5, null);
            c1402x5.setAnimationStyle(0);
        }
        c0043a3.dismiss();
        int size2 = arrayList.size();
        if (size2 > 0) {
            this.f56534b5 = ((C0541gm) arrayList.get(size2 - 1)).f56518a2;
        } else {
            View view = this.f56532b3;
            WeakHashMap weakHashMap = xa1.f61054a0;
            this.f56534b5 = ga1.m212904a3(view) == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z) {
                ((C0541gm) arrayList.get(0)).f56517a1.m210690a2(false);
                return;
            }
            return;
        }
        dismiss();
        sf0 sf0Var = this.f56541c2;
        if (sf0Var != null) {
            sf0Var.mo210850a0(bf0Var, true);
        }
        ViewTreeObserver viewTreeObserver = this.f56542c3;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.f56542c3.removeGlobalOnLayoutListener(this.f56527a8);
            }
            this.f56542c3 = null;
        }
        this.f56533b4.removeOnAttachStateChangeListener(this.f56528a9);
        this.f56543c4.onDismiss();
    }

    @Override // p000.p01
    /* renamed from: a1 */
    public final boolean mo209886a1() {
        ArrayList arrayList = this.f56526a7;
        return arrayList.size() > 0 && ((C0541gm) arrayList.get(0)).f56516a0.f43997c5.isShowing();
    }

    @Override // p000.p01
    /* renamed from: a3 */
    public final void mo209888a3() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (mo209886a1()) {
            return;
        }
        ArrayList arrayList = this.f56525a6;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            m212975c2((bf0) obj);
        }
        arrayList.clear();
        View view = this.f56532b3;
        this.f56533b4 = view;
        if (view != null) {
            boolean z = this.f56542c3 == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.f56542c3 = viewTreeObserver;
            if (z) {
                viewTreeObserver.addOnGlobalLayoutListener(this.f56527a8);
            }
            this.f56533b4.addOnAttachStateChangeListener(this.f56528a9);
        }
    }

    @Override // p000.tf0
    /* renamed from: a5 */
    public final void mo209940a5(sf0 sf0Var) {
        this.f56541c2 = sf0Var;
    }

    @Override // p000.p01
    /* renamed from: a7 */
    public final C1304ul mo209890a7() {
        ArrayList arrayList = this.f56526a7;
        if (arrayList.isEmpty()) {
            return null;
        }
        return ((C0541gm) arrayList.get(arrayList.size() - 1)).f56516a0.f43974a2;
    }

    @Override // p000.tf0
    /* renamed from: a9 */
    public final void mo66a9(boolean z) {
        ArrayList arrayList = this.f56526a7;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ListAdapter adapter = ((C0541gm) obj).f56516a0.f43974a2.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((ye0) adapter).notifyDataSetChanged();
        }
    }

    @Override // p000.tf0
    /* renamed from: b0 */
    public final boolean mo67b0(r21 r21Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = this.f56526a7;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            C0541gm c0541gm = (C0541gm) obj;
            if (r21Var == c0541gm.f56517a1) {
                c0541gm.f56516a0.f43974a2.requestFocus();
                return true;
            }
        }
        if (!r21Var.hasVisibleItems()) {
            return false;
        }
        mo212967b3(r21Var);
        sf0 sf0Var = this.f56541c2;
        if (sf0Var != null) {
            sf0Var.mo210851b6(r21Var);
        }
        return true;
    }

    @Override // p000.tf0
    /* renamed from: b1 */
    public final boolean mo68b1() {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b2 */
    public final Parcelable mo69b2() {
        return null;
    }

    @Override // p000.kf0
    /* renamed from: b3 */
    public final void mo212967b3(bf0 bf0Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        bf0Var.m210689a1(this, this.f56520a1);
        if (mo209886a1()) {
            m212975c2(bf0Var);
        } else {
            this.f56525a6.add(bf0Var);
        }
    }

    @Override // p000.kf0
    /* renamed from: b5 */
    public final void mo212968b5(View view) {
        if (this.f56532b3 != view) {
            this.f56532b3 = view;
            int i = this.f56530b1;
            WeakHashMap weakHashMap = xa1.f61054a0;
            this.f56531b2 = Gravity.getAbsoluteGravity(i, ga1.m212904a3(view));
        }
    }

    @Override // p000.kf0
    /* renamed from: b6 */
    public final void mo212969b6(boolean z) {
        this.f56539c0 = z;
    }

    @Override // p000.kf0
    /* renamed from: b7 */
    public final void mo212970b7(int i) {
        if (this.f56530b1 != i) {
            this.f56530b1 = i;
            View view = this.f56532b3;
            WeakHashMap weakHashMap = xa1.f61054a0;
            this.f56531b2 = Gravity.getAbsoluteGravity(i, ga1.m212904a3(view));
        }
    }

    @Override // p000.kf0
    /* renamed from: b8 */
    public final void mo212971b8(int i) {
        this.f56535b6 = true;
        this.f56537b8 = i;
    }

    @Override // p000.kf0
    /* renamed from: b9 */
    public final void mo212972b9(PopupWindow.OnDismissListener onDismissListener) {
        this.f56543c4 = onDismissListener;
    }

    @Override // p000.kf0
    /* renamed from: c0 */
    public final void mo212973c0(boolean z) {
        this.f56540c1 = z;
    }

    @Override // p000.kf0
    /* renamed from: c1 */
    public final void mo212974c1(int i) {
        this.f56536b7 = true;
        this.f56538b9 = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01cc  */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212975c2(bf0 bf0Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z;
        int i;
        C0541gm c0541gm;
        View childAt;
        int i2;
        int i3;
        int i4;
        int width;
        MenuItem item;
        ye0 ye0Var;
        int headersCount;
        int firstVisiblePosition;
        Context context = this.f56520a1;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        ye0 ye0Var2 = new ye0(bf0Var, layoutInflaterFrom, this.f56523a4, f56519c6);
        if (!mo209886a1() && this.f56539c0) {
            ye0Var2.f61298a2 = true;
        } else if (mo209886a1()) {
            int size = bf0Var.f45871a5.size();
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    z = false;
                    break;
                }
                MenuItem item2 = bf0Var.getItem(i5);
                if (item2.isVisible() && item2.getIcon() != null) {
                    z = true;
                    break;
                }
                i5++;
            }
            ye0Var2.f61298a2 = z;
        }
        int iM213497b4 = kf0.m213497b4(ye0Var2, context, this.f56521a2);
        C0043a3 c0043a3 = new C0043a3(context, null, this.f56522a3, 0);
        c0043a3.f44164c8 = this.f56529b0;
        c0043a3.f43987b5 = this;
        c0043a3.f43997c5.setOnDismissListener(this);
        c0043a3.f43986b4 = this.f56532b3;
        c0043a3.f43983b1 = this.f56531b2;
        c0043a3.f43996c4 = true;
        c0043a3.f43997c5.setFocusable(true);
        c0043a3.f43997c5.setInputMethodMode(2);
        c0043a3.mo209895b6(ye0Var2);
        c0043a3.m209896b7(iM213497b4);
        c0043a3.f43983b1 = this.f56531b2;
        ArrayList arrayList = this.f56526a7;
        if (arrayList.size() > 0) {
            c0541gm = (C0541gm) arrayList.get(arrayList.size() - 1);
            bf0 bf0Var2 = c0541gm.f56517a1;
            int size2 = bf0Var2.f45871a5.size();
            int i6 = 0;
            while (true) {
                if (i6 >= size2) {
                    item = null;
                    break;
                }
                item = bf0Var2.getItem(i6);
                if (item.hasSubMenu() && bf0Var == item.getSubMenu()) {
                    break;
                } else {
                    i6++;
                }
            }
            if (item == null) {
                i = 1;
                childAt = null;
            } else {
                C1304ul c1304ul = c0541gm.f56516a0.f43974a2;
                ListAdapter adapter = c1304ul.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    ye0Var = (ye0) headerViewListAdapter.getWrappedAdapter();
                } else {
                    ye0Var = (ye0) adapter;
                    headersCount = 0;
                }
                int count = ye0Var.getCount();
                i = 1;
                int i7 = 0;
                while (true) {
                    if (i7 >= count) {
                        i7 = -1;
                        break;
                    } else if (item == ye0Var.getItem(i7)) {
                        break;
                    } else {
                        i7++;
                    }
                }
                if (i7 != -1 && (firstVisiblePosition = (i7 + headersCount) - c1304ul.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < c1304ul.getChildCount()) {
                    childAt = c1304ul.getChildAt(firstVisiblePosition);
                }
            }
            if (childAt == null) {
                int i8 = Build.VERSION.SDK_INT;
                C1402x5 c1402x5 = c0043a3.f43997c5;
                if (i8 <= 28) {
                    Method method = C0043a3.f44163c9;
                    if (method != null) {
                        try {
                            method.invoke(c1402x5, Boolean.FALSE);
                        } catch (Exception unused) {
                        }
                    }
                } else {
                    pf0.m214252a0(c1402x5, false);
                }
                of0.m214188a0(c0043a3.f43997c5, null);
                C1304ul c1304ul2 = ((C0541gm) arrayList.get(arrayList.size() - 1)).f56516a0.f43974a2;
                int[] iArr = new int[2];
                c1304ul2.getLocationOnScreen(iArr);
                Rect rect = new Rect();
                this.f56533b4.getWindowVisibleDisplayFrame(rect);
                if (this.f56534b5 == i) {
                    i2 = (c1304ul2.getWidth() + iArr[0]) + iM213497b4 > rect.right ? 0 : 1;
                } else if (iArr[0] - iM213497b4 < 0) {
                }
                boolean z2 = i2 == 1;
                this.f56534b5 = i2;
                if (Build.VERSION.SDK_INT >= 26) {
                    c0043a3.f43986b4 = childAt;
                    i4 = 0;
                    i3 = 0;
                } else {
                    int[] iArr2 = new int[2];
                    this.f56532b3.getLocationOnScreen(iArr2);
                    int[] iArr3 = new int[2];
                    childAt.getLocationOnScreen(iArr3);
                    if ((this.f56531b2 & 7) == 5) {
                        iArr2[0] = this.f56532b3.getWidth() + iArr2[0];
                        iArr3[0] = childAt.getWidth() + iArr3[0];
                    }
                    i3 = iArr3[0] - iArr2[0];
                    i4 = iArr3[1] - iArr2[1];
                }
                if ((this.f56531b2 & 5) != 5) {
                    width = z2 ? i3 + childAt.getWidth() : i3 - iM213497b4;
                    c0043a3.f43977a5 = width;
                    c0043a3.f43982b0 = true;
                    c0043a3.f43981a9 = true;
                    c0043a3.m209892b0(i4);
                } else if (z2) {
                    width = i3 + iM213497b4;
                    c0043a3.f43977a5 = width;
                    c0043a3.f43982b0 = true;
                    c0043a3.f43981a9 = true;
                    c0043a3.m209892b0(i4);
                } else {
                    iM213497b4 = childAt.getWidth();
                    c0043a3.f43977a5 = width;
                    c0043a3.f43982b0 = true;
                    c0043a3.f43981a9 = true;
                    c0043a3.m209892b0(i4);
                }
            } else {
                if (this.f56535b6) {
                    c0043a3.f43977a5 = this.f56537b8;
                }
                if (this.f56536b7) {
                    c0043a3.m209892b0(this.f56538b9);
                }
                Rect rect2 = this.f57514a0;
                c0043a3.f43995c3 = rect2 != null ? new Rect(rect2) : null;
            }
            arrayList.add(new C0541gm(c0043a3, bf0Var, this.f56534b5));
            c0043a3.mo209888a3();
            C1304ul c1304ul3 = c0043a3.f43974a2;
            c1304ul3.setOnKeyListener(this);
            if (c0541gm == null || !this.f56540c1 || bf0Var.f45878b2 == null) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R$layout.abc_popup_menu_header_item_layout, (ViewGroup) c1304ul3, false);
            TextView textView = (TextView) frameLayout.findViewById(R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(bf0Var.f45878b2);
            c1304ul3.addHeaderView(frameLayout, null, false);
            c0043a3.mo209888a3();
            return;
        }
        i = 1;
        c0541gm = null;
        childAt = null;
        if (childAt == null) {
        }
        arrayList.add(new C0541gm(c0043a3, bf0Var, this.f56534b5));
        c0043a3.mo209888a3();
        C1304ul c1304ul32 = c0043a3.f43974a2;
        c1304ul32.setOnKeyListener(this);
        if (c0541gm == null) {
        }
    }

    @Override // p000.p01
    public final void dismiss() {
        ArrayList arrayList = this.f56526a7;
        int size = arrayList.size();
        if (size > 0) {
            C0541gm[] c0541gmArr = (C0541gm[]) arrayList.toArray(new C0541gm[size]);
            for (int i = size - 1; i >= 0; i--) {
                C0541gm c0541gm = c0541gmArr[i];
                if (c0541gm.f56516a0.f43997c5.isShowing()) {
                    c0541gm.f56516a0.dismiss();
                }
            }
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        C0541gm c0541gm;
        ArrayList arrayList = this.f56526a7;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                c0541gm = null;
                break;
            }
            c0541gm = (C0541gm) arrayList.get(i);
            if (!c0541gm.f56516a0.f43997c5.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (c0541gm != null) {
            c0541gm.f56517a1.m210690a2(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // p000.tf0
    /* renamed from: a4 */
    public final void mo63a4(Parcelable parcelable) {
    }
}
