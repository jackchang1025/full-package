package p000;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.PopupWindow;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ViewStubCompat;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w2 */
/* loaded from: classes.dex */
public final class WindowCallbackC1361w2 implements Window.Callback {

    /* renamed from: a0 */
    public final Window.Callback f60750a0;

    /* renamed from: a1 */
    public boolean f60751a1;

    /* renamed from: a2 */
    public boolean f60752a2;

    /* renamed from: a3 */
    public boolean f60753a3;

    /* renamed from: a4 */
    public final /* synthetic */ LayoutInflaterFactory2C1367w8 f60754a4;

    public WindowCallbackC1361w2(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, Window.Callback callback) {
        this.f60754a4 = layoutInflaterFactory2C1367w8;
        if (callback == null) {
            throw new IllegalArgumentException("Window callback may not be null");
        }
        this.f60750a0 = callback;
    }

    /* renamed from: a0 */
    public final void m214981a0(Window.Callback callback) {
        try {
            this.f60751a1 = true;
            callback.onContentChanged();
        } finally {
            this.f60751a1 = false;
        }
    }

    /* renamed from: a1 */
    public final boolean m214982a1(int i, Menu menu) {
        return this.f60750a0.onMenuOpened(i, menu);
    }

    /* renamed from: a2 */
    public final void m214983a2(int i, Menu menu) {
        this.f60750a0.onPanelClosed(i, menu);
    }

    /* renamed from: a3 */
    public final void m214984a3(List list, Menu menu, int i) {
        se1.m214610a0(this.f60750a0, list, menu, i);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.f60750a0.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z = this.f60752a2;
        Window.Callback callback = this.f60750a0;
        return z ? callback.dispatchKeyEvent(keyEvent) : this.f60754a4.m215024b9(keyEvent) || callback.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006e A[RETURN] */
    @Override // android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        boolean z;
        bf0 bf0Var;
        boolean zPerformShortcut;
        if (!this.f60750a0.dispatchKeyShortcutEvent(keyEvent)) {
            int keyCode = keyEvent.getKeyCode();
            LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60754a4;
            layoutInflaterFactory2C1367w8.m215030c5();
            ze1 ze1Var = layoutInflaterFactory2C1367w8.f60813b4;
            if (ze1Var == null) {
                C1366w7 c1366w7 = layoutInflaterFactory2C1367w8.f60837d8;
                if (c1366w7 == null || !layoutInflaterFactory2C1367w8.m215035d0(c1366w7, keyEvent.getKeyCode(), keyEvent)) {
                    if (layoutInflaterFactory2C1367w8.f60837d8 == null) {
                        C1366w7 c1366w7M215029c4 = layoutInflaterFactory2C1367w8.m215029c4(0);
                        layoutInflaterFactory2C1367w8.m215036d1(c1366w7M215029c4, keyEvent);
                        boolean zM215035d0 = layoutInflaterFactory2C1367w8.m215035d0(c1366w7M215029c4, keyEvent.getKeyCode(), keyEvent);
                        c1366w7M215029c4.f60791b0 = false;
                        if (zM215035d0) {
                        }
                        if (z) {
                            return false;
                        }
                    }
                    z = false;
                    if (z) {
                    }
                } else {
                    C1366w7 c1366w72 = layoutInflaterFactory2C1367w8.f60837d8;
                    if (c1366w72 != null) {
                        c1366w72.f60792b1 = true;
                    }
                }
                z = true;
                if (z) {
                }
            } else {
                ye1 ye1Var = ze1Var.f61524b4;
                if (ye1Var == null || (bf0Var = ye1Var.f61303a3) == null) {
                    zPerformShortcut = false;
                } else {
                    bf0Var.setQwertyMode(KeyCharacterMap.load(keyEvent.getDeviceId()).getKeyboardType() != 1);
                    zPerformShortcut = bf0Var.performShortcut(keyCode, keyEvent, 0);
                }
                if (zPerformShortcut) {
                    z = true;
                    if (z) {
                    }
                }
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.f60750a0.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.f60750a0.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.f60750a0.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeFinished(ActionMode actionMode) {
        this.f60750a0.onActionModeFinished(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onActionModeStarted(ActionMode actionMode) {
        this.f60750a0.onActionModeStarted(actionMode);
    }

    @Override // android.view.Window.Callback
    public final void onAttachedToWindow() {
        this.f60750a0.onAttachedToWindow();
    }

    @Override // android.view.Window.Callback
    public final void onContentChanged() {
        if (this.f60751a1) {
            this.f60750a0.onContentChanged();
        }
    }

    @Override // android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0 || (menu instanceof bf0)) {
            return this.f60750a0.onCreatePanelMenu(i, menu);
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public final View onCreatePanelView(int i) {
        return this.f60750a0.onCreatePanelView(i);
    }

    @Override // android.view.Window.Callback
    public final void onDetachedFromWindow() {
        this.f60750a0.onDetachedFromWindow();
    }

    @Override // android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return this.f60750a0.onMenuItemSelected(i, menuItem);
    }

    @Override // android.view.Window.Callback
    public final boolean onMenuOpened(int i, Menu menu) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m214982a1(i, menu);
        if (i == 108) {
            LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60754a4;
            layoutInflaterFactory2C1367w8.m215030c5();
            ze1 ze1Var = layoutInflaterFactory2C1367w8.f60813b4;
            if (ze1Var != null) {
                ArrayList arrayList = ze1Var.f61528b8;
                if (true != ze1Var.f61527b7) {
                    ze1Var.f61527b7 = true;
                    if (arrayList.size() > 0) {
                        arrayList.get(0).getClass();
                        throw new ClassCastException();
                    }
                }
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public final void onPanelClosed(int i, Menu menu) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f60753a3) {
            this.f60750a0.onPanelClosed(i, menu);
            return;
        }
        m214983a2(i, menu);
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60754a4;
        if (i != 108) {
            if (i == 0) {
                C1366w7 c1366w7M215029c4 = layoutInflaterFactory2C1367w8.m215029c4(i);
                if (c1366w7M215029c4.f60793b2) {
                    layoutInflaterFactory2C1367w8.m215023b7(c1366w7M215029c4, false);
                    return;
                }
                return;
            }
            return;
        }
        layoutInflaterFactory2C1367w8.m215030c5();
        ze1 ze1Var = layoutInflaterFactory2C1367w8.f60813b4;
        if (ze1Var != null) {
            ArrayList arrayList = ze1Var.f61528b8;
            if (ze1Var.f61527b7) {
                ze1Var.f61527b7 = false;
                if (arrayList.size() <= 0) {
                    return;
                }
                arrayList.get(0).getClass();
                throw new ClassCastException();
            }
        }
    }

    @Override // android.view.Window.Callback
    public final void onPointerCaptureChanged(boolean z) {
        te1.m214738a0(this.f60750a0, z);
    }

    @Override // android.view.Window.Callback
    public final boolean onPreparePanel(int i, View view, Menu menu) {
        bf0 bf0Var = menu instanceof bf0 ? (bf0) menu : null;
        if (i == 0 && bf0Var == null) {
            return false;
        }
        if (bf0Var != null) {
            bf0Var.f45889c3 = true;
        }
        boolean zOnPreparePanel = this.f60750a0.onPreparePanel(i, view, menu);
        if (bf0Var != null) {
            bf0Var.f45889c3 = false;
        }
        return zOnPreparePanel;
    }

    @Override // android.view.Window.Callback
    public final void onProvideKeyboardShortcuts(List list, Menu menu, int i) {
        bf0 bf0Var = this.f60754a4.m215029c4(0).f60788a7;
        if (bf0Var != null) {
            m214984a3(list, bf0Var, i);
        } else {
            m214984a3(list, menu, i);
        }
    }

    @Override // android.view.Window.Callback
    public final boolean onSearchRequested(SearchEvent searchEvent) {
        return re1.m214533a0(this.f60750a0, searchEvent);
    }

    @Override // android.view.Window.Callback
    public final void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.f60750a0.onWindowAttributesChanged(layoutParams);
    }

    @Override // android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        this.f60750a0.onWindowFocusChanged(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c2  */
    @Override // android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewGroup viewGroup;
        if (i != 0) {
            return re1.m214534a1(this.f60750a0, callback, i);
        }
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60754a4;
        Context context = layoutInflaterFactory2C1367w8.f60809b0;
        x31 x31Var = new x31(context, callback);
        AbstractC0903n7 abstractC0903n7 = layoutInflaterFactory2C1367w8.f60819c0;
        if (abstractC0903n7 != null) {
            abstractC0903n7.mo214038a0();
        }
        eo0 eo0Var = new eo0(layoutInflaterFactory2C1367w8, x31Var);
        layoutInflaterFactory2C1367w8.m215030c5();
        ze1 ze1Var = layoutInflaterFactory2C1367w8.f60813b4;
        int i2 = 1;
        if (ze1Var != null) {
            ye1 ye1Var = ze1Var.f61524b4;
            if (ye1Var != null) {
                ye1Var.mo214038a0();
            }
            ze1Var.f61518a8.setHideOnContentScrollEnabled(false);
            ze1Var.f61521b1.m209854a4();
            ye1 ye1Var2 = new ye1(ze1Var, ze1Var.f61521b1.getContext(), eo0Var);
            bf0 bf0Var = ye1Var2.f61303a3;
            bf0Var.m210712c4();
            try {
                if (((x31) ye1Var2.f61304a4.f56088a1).m215113a5(ye1Var2, bf0Var)) {
                    ze1Var.f61524b4 = ye1Var2;
                    ye1Var2.mo214044a7();
                    ze1Var.f61521b1.m209852a2(ye1Var2);
                    ze1Var.m215396e2(true);
                } else {
                    ye1Var2 = null;
                }
                layoutInflaterFactory2C1367w8.f60819c0 = ye1Var2;
            } finally {
                bf0Var.m210711c3();
            }
        }
        if (layoutInflaterFactory2C1367w8.f60819c0 == null) {
            mc1 mc1Var = layoutInflaterFactory2C1367w8.f60823c4;
            if (mc1Var != null) {
                mc1Var.m213968a1();
            }
            AbstractC0903n7 abstractC0903n72 = layoutInflaterFactory2C1367w8.f60819c0;
            if (abstractC0903n72 != null) {
                abstractC0903n72.mo214038a0();
            }
            if (layoutInflaterFactory2C1367w8.f60820c1 == null) {
                if (layoutInflaterFactory2C1367w8.f60833d4) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = context.getTheme();
                    theme.resolveAttribute(R$attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = context.getResources().newTheme();
                        themeNewTheme.setTo(theme);
                        themeNewTheme.applyStyle(typedValue.resourceId, true);
                        C0875mu c0875mu = new C0875mu(context, 0);
                        c0875mu.getTheme().setTo(themeNewTheme);
                        context = c0875mu;
                    }
                    layoutInflaterFactory2C1367w8.f60820c1 = new ActionBarContextView(context, null);
                    PopupWindow popupWindow = new PopupWindow(context, (AttributeSet) null, R$attr.actionModePopupWindowStyle);
                    layoutInflaterFactory2C1367w8.f60821c2 = popupWindow;
                    yn0.m215300a3(popupWindow, 2);
                    layoutInflaterFactory2C1367w8.f60821c2.setContentView(layoutInflaterFactory2C1367w8.f60820c1);
                    layoutInflaterFactory2C1367w8.f60821c2.setWidth(-1);
                    context.getTheme().resolveAttribute(R$attr.actionBarSize, typedValue, true);
                    layoutInflaterFactory2C1367w8.f60820c1.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics()));
                    layoutInflaterFactory2C1367w8.f60821c2.setHeight(-2);
                    layoutInflaterFactory2C1367w8.f60822c3 = new RunnableC1326v6(layoutInflaterFactory2C1367w8, i2);
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) layoutInflaterFactory2C1367w8.f60825c6.findViewById(R$id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        layoutInflaterFactory2C1367w8.m215030c5();
                        ze1 ze1Var2 = layoutInflaterFactory2C1367w8.f60813b4;
                        Context contextM215397e3 = ze1Var2 != null ? ze1Var2.m215397e3() : null;
                        if (contextM215397e3 != null) {
                            context = contextM215397e3;
                        }
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(context));
                        layoutInflaterFactory2C1367w8.f60820c1 = (ActionBarContextView) viewStubCompat.m209937a0();
                    }
                }
            }
            if (layoutInflaterFactory2C1367w8.f60820c1 != null) {
                mc1 mc1Var2 = layoutInflaterFactory2C1367w8.f60823c4;
                if (mc1Var2 != null) {
                    mc1Var2.m213968a1();
                }
                layoutInflaterFactory2C1367w8.f60820c1.m209854a4();
                Context context2 = layoutInflaterFactory2C1367w8.f60820c1.getContext();
                ActionBarContextView actionBarContextView = layoutInflaterFactory2C1367w8.f60820c1;
                t11 t11Var = new t11();
                t11Var.f60119a2 = context2;
                t11Var.f60120a3 = actionBarContextView;
                t11Var.f60121a4 = eo0Var;
                bf0 bf0Var2 = new bf0(actionBarContextView.getContext());
                bf0Var2.f45877b1 = 1;
                t11Var.f60124a7 = bf0Var2;
                bf0Var2.f45870a4 = t11Var;
                if (x31Var.m215113a5(t11Var, bf0Var2)) {
                    t11Var.mo214044a7();
                    layoutInflaterFactory2C1367w8.f60820c1.m209852a2(t11Var);
                    layoutInflaterFactory2C1367w8.f60819c0 = t11Var;
                    if (!layoutInflaterFactory2C1367w8.f60824c5 || (viewGroup = layoutInflaterFactory2C1367w8.f60825c6) == null) {
                        layoutInflaterFactory2C1367w8.f60820c1.setAlpha(1.0f);
                        layoutInflaterFactory2C1367w8.f60820c1.setVisibility(0);
                        if (layoutInflaterFactory2C1367w8.f60820c1.getParent() instanceof View) {
                            View view = (View) layoutInflaterFactory2C1367w8.f60820c1.getParent();
                            WeakHashMap weakHashMap = xa1.f61054a0;
                            ja1.m213282a2(view);
                        }
                        if (layoutInflaterFactory2C1367w8.f60821c2 != null) {
                            layoutInflaterFactory2C1367w8.f60810b1.getDecorView().post(layoutInflaterFactory2C1367w8.f60822c3);
                        }
                    } else {
                        WeakHashMap weakHashMap2 = xa1.f61054a0;
                        if (ia1.m213142a2(viewGroup)) {
                            layoutInflaterFactory2C1367w8.f60820c1.setAlpha(0.0f);
                            mc1 mc1VarM215138a0 = xa1.m215138a0(layoutInflaterFactory2C1367w8.f60820c1);
                            mc1VarM215138a0.m213967a0(1.0f);
                            layoutInflaterFactory2C1367w8.f60823c4 = mc1VarM215138a0;
                            mc1VarM215138a0.m213970a3(new C1328v8(i2, layoutInflaterFactory2C1367w8));
                        }
                        if (layoutInflaterFactory2C1367w8.f60821c2 != null) {
                        }
                    }
                } else {
                    layoutInflaterFactory2C1367w8.f60819c0 = null;
                }
            }
            layoutInflaterFactory2C1367w8.m215038d3();
            layoutInflaterFactory2C1367w8.f60819c0 = layoutInflaterFactory2C1367w8.f60819c0;
        }
        layoutInflaterFactory2C1367w8.m215038d3();
        AbstractC0903n7 abstractC0903n73 = layoutInflaterFactory2C1367w8.f60819c0;
        if (abstractC0903n73 != null) {
            return x31Var.m215109a1(abstractC0903n73);
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public final boolean onSearchRequested() {
        return this.f60750a0.onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }
}
