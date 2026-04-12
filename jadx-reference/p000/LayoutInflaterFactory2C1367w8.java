package p000;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$style;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0041a1;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle$State;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w8 */
/* loaded from: classes.dex */
public final class LayoutInflaterFactory2C1367w8 extends AbstractC1325v5 implements ze0, LayoutInflater.Factory2 {

    /* renamed from: f9 */
    public static final t01 f60804f9 = new t01();

    /* renamed from: g0 */
    public static final int[] f60805g0 = {R.attr.windowBackground};

    /* renamed from: g1 */
    public static final boolean f60806g1 = !"robolectric".equals(Build.FINGERPRINT);

    /* renamed from: g2 */
    public static final boolean f60807g2 = true;

    /* renamed from: a9 */
    public final Object f60808a9;

    /* renamed from: b0 */
    public final Context f60809b0;

    /* renamed from: b1 */
    public Window f60810b1;

    /* renamed from: b2 */
    public WindowCallbackC1361w2 f60811b2;

    /* renamed from: b3 */
    public final Object f60812b3;

    /* renamed from: b4 */
    public ze1 f60813b4;

    /* renamed from: b5 */
    public c31 f60814b5;

    /* renamed from: b6 */
    public CharSequence f60815b6;

    /* renamed from: b7 */
    public InterfaceC1118qp f60816b7;

    /* renamed from: b8 */
    public C1327v7 f60817b8;

    /* renamed from: b9 */
    public C1327v7 f60818b9;

    /* renamed from: c0 */
    public AbstractC0903n7 f60819c0;

    /* renamed from: c1 */
    public ActionBarContextView f60820c1;

    /* renamed from: c2 */
    public PopupWindow f60821c2;

    /* renamed from: c3 */
    public RunnableC1326v6 f60822c3;

    /* renamed from: c5 */
    public boolean f60824c5;

    /* renamed from: c6 */
    public ViewGroup f60825c6;

    /* renamed from: c7 */
    public TextView f60826c7;

    /* renamed from: c8 */
    public View f60827c8;

    /* renamed from: c9 */
    public boolean f60828c9;

    /* renamed from: d0 */
    public boolean f60829d0;

    /* renamed from: d1 */
    public boolean f60830d1;

    /* renamed from: d2 */
    public boolean f60831d2;

    /* renamed from: d3 */
    public boolean f60832d3;

    /* renamed from: d4 */
    public boolean f60833d4;

    /* renamed from: d5 */
    public boolean f60834d5;

    /* renamed from: d6 */
    public boolean f60835d6;

    /* renamed from: d7 */
    public C1366w7[] f60836d7;

    /* renamed from: d8 */
    public C1366w7 f60837d8;

    /* renamed from: d9 */
    public boolean f60838d9;

    /* renamed from: e0 */
    public boolean f60839e0;

    /* renamed from: e1 */
    public boolean f60840e1;

    /* renamed from: e2 */
    public boolean f60841e2;

    /* renamed from: e3 */
    public Configuration f60842e3;

    /* renamed from: e4 */
    public final int f60843e4;

    /* renamed from: e5 */
    public int f60844e5;

    /* renamed from: e6 */
    public int f60845e6;

    /* renamed from: e7 */
    public boolean f60846e7;

    /* renamed from: e8 */
    public C1362w3 f60847e8;

    /* renamed from: e9 */
    public C1362w3 f60848e9;

    /* renamed from: f0 */
    public boolean f60849f0;

    /* renamed from: f1 */
    public int f60850f1;

    /* renamed from: f3 */
    public boolean f60852f3;

    /* renamed from: f4 */
    public Rect f60853f4;

    /* renamed from: f5 */
    public Rect f60854f5;

    /* renamed from: f6 */
    public C0026af f60855f6;

    /* renamed from: f7 */
    public OnBackInvokedDispatcher f60856f7;

    /* renamed from: f8 */
    public OnBackInvokedCallback f60857f8;

    /* renamed from: c4 */
    public mc1 f60823c4 = null;

    /* renamed from: f2 */
    public final RunnableC1326v6 f60851f2 = new RunnableC1326v6(this, 0);

    public LayoutInflaterFactory2C1367w8(Context context, Window window, InterfaceC1320v0 interfaceC1320v0, Object obj) {
        AppCompatActivity appCompatActivity;
        this.f60843e4 = -100;
        this.f60809b0 = context;
        this.f60812b3 = interfaceC1320v0;
        this.f60808a9 = obj;
        if (obj instanceof Dialog) {
            while (context != null) {
                if (!(context instanceof AppCompatActivity)) {
                    if (!(context instanceof ContextWrapper)) {
                        break;
                    } else {
                        context = ((ContextWrapper) context).getBaseContext();
                    }
                } else {
                    appCompatActivity = (AppCompatActivity) context;
                    break;
                }
            }
            appCompatActivity = null;
            if (appCompatActivity != null) {
                this.f60843e4 = ((LayoutInflaterFactory2C1367w8) appCompatActivity.m209838b1()).f60843e4;
            }
        }
        if (this.f60843e4 == -100) {
            String name = this.f60808a9.getClass().getName();
            t01 t01Var = f60804f9;
            Integer num = (Integer) t01Var.getOrDefault(name, null);
            if (num != null) {
                this.f60843e4 = num.intValue();
                t01Var.remove(this.f60808a9.getClass().getName());
            }
        }
        if (window != null) {
            m215020b3(window);
        }
        C1398x1.m215097a3();
    }

    /* renamed from: b4 */
    public static dc0 m215017b4(Context context) {
        dc0 dc0Var;
        dc0 dc0Var2;
        if (Build.VERSION.SDK_INT >= 33 || (dc0Var = AbstractC1325v5.f60577a2) == null) {
            return null;
        }
        ec0 ec0Var = dc0Var.f55691a0;
        dc0 dc0VarM214908a1 = AbstractC1329v9.m214908a1(context.getApplicationContext().getResources().getConfiguration());
        if (ec0Var.f55969a0.isEmpty()) {
            dc0Var2 = dc0.f55690a1;
        } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int i = 0;
            while (i < dc0VarM214908a1.f55691a0.f55969a0.size() + ec0Var.f55969a0.size()) {
                Locale locale = i < ec0Var.f55969a0.size() ? ec0Var.f55969a0.get(i) : dc0VarM214908a1.f55691a0.f55969a0.get(i - ec0Var.f55969a0.size());
                if (locale != null) {
                    linkedHashSet.add(locale);
                }
                i++;
            }
            dc0Var2 = new dc0(new ec0(cc0.m210824a0((Locale[]) linkedHashSet.toArray(new Locale[linkedHashSet.size()]))));
        }
        return dc0Var2.f55691a0.f55969a0.isEmpty() ? dc0VarM214908a1 : dc0Var2;
    }

    /* renamed from: b8 */
    public static Configuration m215018b8(Context context, int i, dc0 dc0Var, Configuration configuration, boolean z) {
        int i2 = i != 1 ? i != 2 ? z ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32 : 16;
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
        if (dc0Var != null) {
            AbstractC1329v9.m214910a3(configuration2, dc0Var);
        }
        return configuration2;
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a0 */
    public final void mo214898a0() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f60813b4 != null) {
            m215030c5();
            this.f60813b4.getClass();
            m215031c6(0);
        }
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a2 */
    public final void mo214899a2() throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        String strM213571b8;
        this.f60839e0 = true;
        m215019b2(false, true);
        m215027c2();
        Object obj = this.f60808a9;
        if (obj instanceof Activity) {
            try {
                Activity activity = (Activity) obj;
                try {
                    strM213571b8 = kj1.m213571b8(activity, activity.getComponentName());
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (IllegalArgumentException unused) {
                strM213571b8 = null;
            }
            if (strM213571b8 != null) {
                ze1 ze1Var = this.f60813b4;
                if (ze1Var == null) {
                    this.f60852f3 = true;
                } else {
                    ze1Var.m215399e5(true);
                }
            }
            synchronized (AbstractC1325v5.f60582a7) {
                AbstractC1325v5.m214897a5(this);
                AbstractC1325v5.f60581a6.add(new WeakReference(this));
            }
        }
        this.f60842e3 = new Configuration(this.f60809b0.getResources().getConfiguration());
        this.f60840e1 = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    @Override // p000.AbstractC1325v5
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo214900a3() {
        if (this.f60808a9 instanceof Activity) {
            synchronized (AbstractC1325v5.f60582a7) {
                AbstractC1325v5.m214897a5(this);
            }
        }
        if (this.f60849f0) {
            this.f60810b1.getDecorView().removeCallbacks(this.f60851f2);
        }
        this.f60841e2 = true;
        if (this.f60843e4 != -100) {
            Object obj = this.f60808a9;
            if ((obj instanceof Activity) && ((Activity) obj).isChangingConfigurations()) {
                f60804f9.put(this.f60808a9.getClass().getName(), Integer.valueOf(this.f60843e4));
            } else {
                f60804f9.remove(this.f60808a9.getClass().getName());
            }
        }
        C1362w3 c1362w3 = this.f60847e8;
        if (c1362w3 != null) {
            c1362w3.m215006a0();
        }
        C1362w3 c1362w32 = this.f60848e9;
        if (c1362w32 != null) {
            c1362w32.m215006a0();
        }
    }

    @Override // p000.ze0
    /* renamed from: a4 */
    public final boolean mo214682a4(bf0 bf0Var, MenuItem menuItem) {
        C1366w7 c1366w7;
        Window.Callback callback = this.f60810b1.getCallback();
        if (callback != null && !this.f60841e2) {
            bf0 bf0VarMo210698b0 = bf0Var.mo210698b0();
            C1366w7[] c1366w7Arr = this.f60836d7;
            int length = c1366w7Arr != null ? c1366w7Arr.length : 0;
            int i = 0;
            while (true) {
                if (i < length) {
                    c1366w7 = c1366w7Arr[i];
                    if (c1366w7 != null && c1366w7.f60788a7 == bf0VarMo210698b0) {
                        break;
                    }
                    i++;
                } else {
                    c1366w7 = null;
                    break;
                }
            }
            if (c1366w7 != null) {
                return callback.onMenuItemSelected(c1366w7.f60781a0, menuItem);
            }
        }
        return false;
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a6 */
    public final boolean mo214901a6(int i) {
        if (i == 8) {
            i = 108;
        } else if (i == 9) {
            i = 109;
        }
        if (this.f60834d5 && i == 108) {
            return false;
        }
        if (this.f60830d1 && i == 1) {
            this.f60830d1 = false;
        }
        if (i == 1) {
            m215037d2();
            this.f60834d5 = true;
            return true;
        }
        if (i == 2) {
            m215037d2();
            this.f60828c9 = true;
            return true;
        }
        if (i == 5) {
            m215037d2();
            this.f60829d0 = true;
            return true;
        }
        if (i == 10) {
            m215037d2();
            this.f60832d3 = true;
            return true;
        }
        if (i == 108) {
            m215037d2();
            this.f60830d1 = true;
            return true;
        }
        if (i != 109) {
            return this.f60810b1.requestFeature(i);
        }
        m215037d2();
        this.f60831d2 = true;
        return true;
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a7 */
    public final void mo214902a7(int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m215026c1();
        ViewGroup viewGroup = (ViewGroup) this.f60825c6.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.f60809b0).inflate(i, viewGroup);
        this.f60811b2.m214981a0(this.f60810b1.getCallback());
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a8 */
    public final void mo214903a8(View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m215026c1();
        ViewGroup viewGroup = (ViewGroup) this.f60825c6.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.f60811b2.m214981a0(this.f60810b1.getCallback());
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: a9 */
    public final void mo214904a9(View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m215026c1();
        ViewGroup viewGroup = (ViewGroup) this.f60825c6.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.f60811b2.m214981a0(this.f60810b1.getCallback());
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0048, code lost:
    
        if (r6.m209941a7() != false) goto L20;
     */
    @Override // p000.ze0
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo214683b0(bf0 bf0Var) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ActionMenuView actionMenuView;
        C0041a1 c0041a1;
        C0041a1 c0041a12;
        C0041a1 c0041a13;
        InterfaceC1118qp interfaceC1118qp = this.f60816b7;
        if (interfaceC1118qp != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) interfaceC1118qp;
            actionBarOverlayLayout.m209867b0();
            Toolbar toolbar = ((f71) actionBarOverlayLayout.f43840a4).f56159a0;
            if (toolbar.getVisibility() == 0 && (actionMenuView = toolbar.f44089a0) != null && actionMenuView.f43867b8) {
                if (ViewConfiguration.get(this.f60809b0).hasPermanentMenuKey()) {
                    ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.f60816b7;
                    actionBarOverlayLayout2.m209867b0();
                    ActionMenuView actionMenuView2 = ((f71) actionBarOverlayLayout2.f43840a4).f56159a0.f44089a0;
                    if (actionMenuView2 != null) {
                        C0041a1 c0041a14 = actionMenuView2.f43868b9;
                        if (c0041a14 != null) {
                            if (c0041a14.f44159c1 == null) {
                            }
                        }
                    }
                }
                Window.Callback callback = this.f60810b1.getCallback();
                ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.f60816b7;
                actionBarOverlayLayout3.m209867b0();
                ActionMenuView actionMenuView3 = ((f71) actionBarOverlayLayout3.f43840a4).f56159a0.f44089a0;
                if (actionMenuView3 != null && (c0041a12 = actionMenuView3.f43868b9) != null && c0041a12.m209941a7()) {
                    ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.f60816b7;
                    actionBarOverlayLayout4.m209867b0();
                    ActionMenuView actionMenuView4 = ((f71) actionBarOverlayLayout4.f43840a4).f56159a0.f44089a0;
                    if (actionMenuView4 != null && (c0041a13 = actionMenuView4.f43868b9) != null) {
                        c0041a13.m209939a3();
                    }
                    if (this.f60841e2) {
                        return;
                    }
                    callback.onPanelClosed(108, m215029c4(0).f60788a7);
                    return;
                }
                if (callback == null || this.f60841e2) {
                    return;
                }
                if (this.f60849f0 && (1 & this.f60850f1) != 0) {
                    View decorView = this.f60810b1.getDecorView();
                    RunnableC1326v6 runnableC1326v6 = this.f60851f2;
                    decorView.removeCallbacks(runnableC1326v6);
                    runnableC1326v6.run();
                }
                C1366w7 c1366w7M215029c4 = m215029c4(0);
                bf0 bf0Var2 = c1366w7M215029c4.f60788a7;
                if (bf0Var2 == null || c1366w7M215029c4.f60795b4 || !callback.onPreparePanel(0, c1366w7M215029c4.f60787a6, bf0Var2)) {
                    return;
                }
                callback.onMenuOpened(108, c1366w7M215029c4.f60788a7);
                ActionBarOverlayLayout actionBarOverlayLayout5 = (ActionBarOverlayLayout) this.f60816b7;
                actionBarOverlayLayout5.m209867b0();
                ActionMenuView actionMenuView5 = ((f71) actionBarOverlayLayout5.f43840a4).f56159a0.f44089a0;
                if (actionMenuView5 == null || (c0041a1 = actionMenuView5.f43868b9) == null) {
                    return;
                }
                c0041a1.m209942b3();
                return;
            }
        }
        C1366w7 c1366w7M215029c42 = m215029c4(0);
        c1366w7M215029c42.f60794b3 = true;
        m215023b7(c1366w7M215029c42, false);
        m215034c9(c1366w7M215029c42, null);
    }

    @Override // p000.AbstractC1325v5
    /* renamed from: b1 */
    public final void mo214905b1(CharSequence charSequence) {
        this.f60815b6 = charSequence;
        InterfaceC1118qp interfaceC1118qp = this.f60816b7;
        if (interfaceC1118qp != null) {
            interfaceC1118qp.setWindowTitle(charSequence);
            return;
        }
        ze1 ze1Var = this.f60813b4;
        if (ze1Var == null) {
            TextView textView = this.f60826c7;
            if (textView != null) {
                textView.setText(charSequence);
                return;
            }
            return;
        }
        f71 f71Var = (f71) ze1Var.f61520b0;
        if (f71Var.f56165a6) {
            return;
        }
        Toolbar toolbar = f71Var.f56159a0;
        f71Var.f56166a7 = charSequence;
        if ((f71Var.f56160a1 & 8) != 0) {
            toolbar.setTitle(charSequence);
            if (f71Var.f56165a6) {
                xa1.m215153b5(toolbar.getRootView(), charSequence);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00d8  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215019b2(boolean z, boolean z2) throws IllegalAccessException, NoSuchFieldException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        int i;
        boolean z3;
        Object obj;
        Object obj2;
        if (this.f60841e2) {
            return false;
        }
        int i2 = this.f60843e4;
        if (i2 == -100) {
            i2 = AbstractC1325v5.f60576a1;
        }
        Context context = this.f60809b0;
        int iM215032c7 = m215032c7(context, i2);
        int i3 = Build.VERSION.SDK_INT;
        LongSparseArray longSparseArray = null;
        dc0 dc0VarM215017b4 = i3 < 33 ? m215017b4(context) : null;
        if (!z2 && dc0VarM215017b4 != null) {
            dc0VarM215017b4 = AbstractC1329v9.m214908a1(context.getResources().getConfiguration());
        }
        Configuration configurationM215018b8 = m215018b8(context, iM215032c7, dc0VarM215017b4, null, false);
        boolean z4 = this.f60846e7;
        boolean z5 = true;
        Object obj3 = this.f60808a9;
        if (z4 || !(obj3 instanceof Activity)) {
            this.f60846e7 = true;
            i = this.f60845e6;
        } else {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                i = 0;
            } else {
                try {
                    ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, obj3.getClass()), i3 >= 29 ? 269221888 : 786432);
                    if (activityInfo != null) {
                        this.f60845e6 = activityInfo.configChanges;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    this.f60845e6 = 0;
                }
                this.f60846e7 = true;
                i = this.f60845e6;
            }
        }
        Configuration configuration = this.f60842e3;
        if (configuration == null) {
            configuration = context.getResources().getConfiguration();
        }
        int i4 = configuration.uiMode & 48;
        int i5 = configurationM215018b8.uiMode & 48;
        dc0 dc0VarM214908a1 = AbstractC1329v9.m214908a1(configuration);
        dc0 dc0VarM214908a12 = dc0VarM215017b4 == null ? null : AbstractC1329v9.m214908a1(configurationM215018b8);
        int i6 = i4 != i5 ? 512 : 0;
        if (dc0VarM214908a12 != null && !dc0VarM214908a1.equals(dc0VarM214908a12)) {
            i6 |= 8196;
        }
        if (((~i) & i6) != 0 && z && this.f60839e0 && ((f60806g1 || this.f60840e1) && (obj3 instanceof Activity))) {
            Activity activity = (Activity) obj3;
            if (!activity.isChild()) {
                if (Build.VERSION.SDK_INT >= 28) {
                    activity.recreate();
                } else {
                    new Handler(activity.getMainLooper()).post(new RunnableC0941o6(i, activity));
                }
                z3 = true;
            }
        } else {
            z3 = false;
        }
        if (z3 || i6 == 0) {
            z5 = z3;
        } else {
            i = (i6 & i) == i6 ? 1 : 0;
            Resources resources = context.getResources();
            Configuration configuration2 = new Configuration(resources.getConfiguration());
            configuration2.uiMode = (resources.getConfiguration().uiMode & (-49)) | i5;
            if (dc0VarM214908a12 != null) {
                AbstractC1329v9.m214910a3(configuration2, dc0VarM214908a12);
            }
            resources.updateConfiguration(configuration2, null);
            int i7 = Build.VERSION.SDK_INT;
            if (i7 < 26 && i7 < 28) {
                if (!b81.f45749c0) {
                    try {
                        Field declaredField = Resources.class.getDeclaredField("mResourcesImpl");
                        b81.f45748b9 = declaredField;
                        declaredField.setAccessible(true);
                    } catch (NoSuchFieldException unused2) {
                    }
                    b81.f45749c0 = true;
                }
                Field field = b81.f45748b9;
                if (field != null) {
                    try {
                        obj = field.get(resources);
                    } catch (IllegalAccessException unused3) {
                        obj = null;
                    }
                    if (obj != null) {
                        if (!b81.f45743b4) {
                            try {
                                Field declaredField2 = obj.getClass().getDeclaredField("mDrawableCache");
                                b81.f45742b3 = declaredField2;
                                declaredField2.setAccessible(true);
                            } catch (NoSuchFieldException unused4) {
                            }
                            b81.f45743b4 = true;
                        }
                        Field field2 = b81.f45742b3;
                        if (field2 != null) {
                            try {
                                obj2 = field2.get(obj);
                            } catch (IllegalAccessException unused5) {
                            }
                            if (obj2 != null) {
                                if (!b81.f45745b6) {
                                    try {
                                        b81.f45744b5 = Class.forName("android.content.res.ThemedResourceCache");
                                    } catch (ClassNotFoundException unused6) {
                                    }
                                    b81.f45745b6 = true;
                                }
                                Class cls = b81.f45744b5;
                                if (cls != null) {
                                    if (!b81.f45747b8) {
                                        try {
                                            Field declaredField3 = cls.getDeclaredField("mUnthemedEntries");
                                            b81.f45746b7 = declaredField3;
                                            declaredField3.setAccessible(true);
                                        } catch (NoSuchFieldException unused7) {
                                        }
                                        b81.f45747b8 = true;
                                    }
                                    Field field3 = b81.f45746b7;
                                    if (field3 != null) {
                                        try {
                                            longSparseArray = (LongSparseArray) field3.get(obj2);
                                        } catch (IllegalAccessException unused8) {
                                        }
                                        if (longSparseArray != null) {
                                            zr0.m215432a0(longSparseArray);
                                        }
                                    }
                                }
                            }
                        } else {
                            obj2 = null;
                            if (obj2 != null) {
                            }
                        }
                    }
                }
            }
            int i8 = this.f60844e5;
            if (i8 != 0) {
                context.setTheme(i8);
                context.getTheme().applyStyle(this.f60844e5, true);
            }
            if (i != 0 && (obj3 instanceof Activity)) {
                Activity activity2 = (Activity) obj3;
                if (activity2 instanceof ka0) {
                    if (((ka0) activity2).mo209830a5().f45191a6.compareTo(Lifecycle$State.f45175a2) >= 0) {
                        activity2.onConfigurationChanged(configuration2);
                    }
                } else if (this.f60840e1 && !this.f60841e2) {
                    activity2.onConfigurationChanged(configuration2);
                }
            }
        }
        if (z5 && dc0VarM214908a12 != null) {
            AbstractC1329v9.m214909a2(AbstractC1329v9.m214908a1(context.getResources().getConfiguration()));
        }
        if (i2 == 0) {
            m215028c3(context).m215008a5();
        } else {
            C1362w3 c1362w3 = this.f60847e8;
            if (c1362w3 != null) {
                c1362w3.m215006a0();
            }
        }
        if (i2 == 3) {
            if (this.f60848e9 == null) {
                this.f60848e9 = new C1362w3(this, context);
            }
            this.f60848e9.m215008a5();
        } else {
            C1362w3 c1362w32 = this.f60848e9;
            if (c1362w32 != null) {
                c1362w32.m215006a0();
            }
        }
        return z5;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0074  */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m215020b3(Window window) {
        Drawable drawableM214662a3;
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        OnBackInvokedCallback onBackInvokedCallback;
        int resourceId;
        if (this.f60810b1 != null) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callback = window.getCallback();
        if (callback instanceof WindowCallbackC1361w2) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        WindowCallbackC1361w2 windowCallbackC1361w2 = new WindowCallbackC1361w2(this, callback);
        this.f60811b2 = windowCallbackC1361w2;
        window.setCallback(windowCallbackC1361w2);
        Context context = this.f60809b0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, f60805g0);
        if (!typedArrayObtainStyledAttributes.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) == 0) {
            drawableM214662a3 = null;
        } else {
            C1398x1 c1398x1M215095a0 = C1398x1.m215095a0();
            synchronized (c1398x1M215095a0) {
                drawableM214662a3 = c1398x1M215095a0.f60990a0.m214662a3(context, resourceId, true);
            }
        }
        if (drawableM214662a3 != null) {
            window.setBackgroundDrawable(drawableM214662a3);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.f60810b1 = window;
        if (Build.VERSION.SDK_INT < 33 || (onBackInvokedDispatcher = this.f60856f7) != null) {
            return;
        }
        Object obj = this.f60808a9;
        if (onBackInvokedDispatcher != null && (onBackInvokedCallback = this.f60857f8) != null) {
            AbstractC1360w1.m214976a2(onBackInvokedDispatcher, onBackInvokedCallback);
            this.f60857f8 = null;
        }
        if (obj instanceof Activity) {
            Activity activity = (Activity) obj;
            if (activity.getWindow() != null) {
                this.f60856f7 = AbstractC1360w1.m214974a0(activity);
            } else {
                this.f60856f7 = null;
            }
        }
        m215038d3();
    }

    /* renamed from: b5 */
    public final void m215021b5(int i, C1366w7 c1366w7, bf0 bf0Var) {
        if (bf0Var == null) {
            if (c1366w7 == null && i >= 0) {
                C1366w7[] c1366w7Arr = this.f60836d7;
                if (i < c1366w7Arr.length) {
                    c1366w7 = c1366w7Arr[i];
                }
            }
            if (c1366w7 != null) {
                bf0Var = c1366w7.f60788a7;
            }
        }
        if ((c1366w7 == null || c1366w7.f60793b2) && !this.f60841e2) {
            WindowCallbackC1361w2 windowCallbackC1361w2 = this.f60811b2;
            Window.Callback callback = this.f60810b1.getCallback();
            windowCallbackC1361w2.getClass();
            try {
                windowCallbackC1361w2.f60753a3 = true;
                callback.onPanelClosed(i, bf0Var);
            } finally {
                windowCallbackC1361w2.f60753a3 = false;
            }
        }
    }

    /* renamed from: b6 */
    public final void m215022b6(bf0 bf0Var) {
        C0041a1 c0041a1;
        if (this.f60835d6) {
            return;
        }
        this.f60835d6 = true;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.f60816b7;
        actionBarOverlayLayout.m209867b0();
        ActionMenuView actionMenuView = ((f71) actionBarOverlayLayout.f43840a4).f56159a0.f44089a0;
        if (actionMenuView != null && (c0041a1 = actionMenuView.f43868b9) != null) {
            c0041a1.m209939a3();
            C0882n0 c0882n0 = c0041a1.f44158c0;
            if (c0882n0 != null && c0882n0.m214075a1()) {
                c0882n0.f58624a8.dismiss();
            }
        }
        Window.Callback callback = this.f60810b1.getCallback();
        if (callback != null && !this.f60841e2) {
            callback.onPanelClosed(108, bf0Var);
        }
        this.f60835d6 = false;
    }

    /* renamed from: b7 */
    public final void m215023b7(C1366w7 c1366w7, boolean z) {
        C1365w6 c1365w6;
        InterfaceC1118qp interfaceC1118qp;
        C0041a1 c0041a1;
        if (z && c1366w7.f60781a0 == 0 && (interfaceC1118qp = this.f60816b7) != null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) interfaceC1118qp;
            actionBarOverlayLayout.m209867b0();
            ActionMenuView actionMenuView = ((f71) actionBarOverlayLayout.f43840a4).f56159a0.f44089a0;
            if (actionMenuView != null && (c0041a1 = actionMenuView.f43868b9) != null && c0041a1.m209941a7()) {
                m215022b6(c1366w7.f60788a7);
                return;
            }
        }
        WindowManager windowManager = (WindowManager) this.f60809b0.getSystemService("window");
        if (windowManager != null && c1366w7.f60793b2 && (c1365w6 = c1366w7.f60785a4) != null) {
            windowManager.removeView(c1365w6);
            if (z) {
                m215021b5(c1366w7.f60781a0, c1366w7, null);
            }
        }
        c1366w7.f60791b0 = false;
        c1366w7.f60792b1 = false;
        c1366w7.f60793b2 = false;
        c1366w7.f60786a5 = null;
        c1366w7.f60794b3 = true;
        if (this.f60837d8 == c1366w7) {
            this.f60837d8 = null;
        }
        if (c1366w7.f60781a0 == 0) {
            m215038d3();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0123  */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215024b9(KeyEvent keyEvent) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        View decorView;
        boolean z;
        boolean zM215036d1;
        AudioManager audioManager;
        ActionMenuView actionMenuView;
        C0041a1 c0041a1;
        C0041a1 c0041a12;
        C0041a1 c0041a13;
        Object obj = this.f60808a9;
        if ((!(obj instanceof p80) && !(obj instanceof DialogC1167r4)) || (decorView = this.f60810b1.getDecorView()) == null || !cq0.m212480b0(decorView, keyEvent)) {
            if (keyEvent.getKeyCode() == 82) {
                WindowCallbackC1361w2 windowCallbackC1361w2 = this.f60811b2;
                Window.Callback callback = this.f60810b1.getCallback();
                windowCallbackC1361w2.getClass();
                try {
                    windowCallbackC1361w2.f60752a2 = true;
                    if (!callback.dispatchKeyEvent(keyEvent)) {
                        int keyCode = keyEvent.getKeyCode();
                        if (keyEvent.getAction() == 0) {
                            if (keyCode == 4) {
                                this.f60838d9 = (keyEvent.getFlags() & 128) != 0;
                                return false;
                            }
                            if (keyCode == 82) {
                                if (keyEvent.getRepeatCount() == 0) {
                                    C1366w7 c1366w7M215029c4 = m215029c4(0);
                                    if (!c1366w7M215029c4.f60793b2) {
                                        m215036d1(c1366w7M215029c4, keyEvent);
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                        if (keyCode != 4) {
                            if (keyCode == 82) {
                                if (this.f60819c0 == null) {
                                    C1366w7 c1366w7M215029c42 = m215029c4(0);
                                    InterfaceC1118qp interfaceC1118qp = this.f60816b7;
                                    Context context = this.f60809b0;
                                    if (interfaceC1118qp != null) {
                                        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) interfaceC1118qp;
                                        actionBarOverlayLayout.m209867b0();
                                        Toolbar toolbar = ((f71) actionBarOverlayLayout.f43840a4).f56159a0;
                                        if (toolbar.getVisibility() != 0 || (actionMenuView = toolbar.f44089a0) == null || !actionMenuView.f43867b8 || ViewConfiguration.get(context).hasPermanentMenuKey()) {
                                            boolean z2 = c1366w7M215029c42.f60793b2;
                                            if (z2 || c1366w7M215029c42.f60792b1) {
                                                m215023b7(c1366w7M215029c42, true);
                                                z = z2;
                                            } else {
                                                if (c1366w7M215029c42.f60791b0) {
                                                    if (c1366w7M215029c42.f60795b4) {
                                                        c1366w7M215029c42.f60791b0 = false;
                                                        zM215036d1 = m215036d1(c1366w7M215029c42, keyEvent);
                                                    } else {
                                                        zM215036d1 = true;
                                                    }
                                                    if (zM215036d1) {
                                                        m215034c9(c1366w7M215029c42, keyEvent);
                                                        z = true;
                                                    }
                                                }
                                                z = false;
                                            }
                                        } else {
                                            ActionBarOverlayLayout actionBarOverlayLayout2 = (ActionBarOverlayLayout) this.f60816b7;
                                            actionBarOverlayLayout2.m209867b0();
                                            ActionMenuView actionMenuView2 = ((f71) actionBarOverlayLayout2.f43840a4).f56159a0.f44089a0;
                                            if (actionMenuView2 == null || (c0041a12 = actionMenuView2.f43868b9) == null || !c0041a12.m209941a7()) {
                                                if (!this.f60841e2 && m215036d1(c1366w7M215029c42, keyEvent)) {
                                                    ActionBarOverlayLayout actionBarOverlayLayout3 = (ActionBarOverlayLayout) this.f60816b7;
                                                    actionBarOverlayLayout3.m209867b0();
                                                    ActionMenuView actionMenuView3 = ((f71) actionBarOverlayLayout3.f43840a4).f56159a0.f44089a0;
                                                    if (actionMenuView3 != null && (c0041a1 = actionMenuView3.f43868b9) != null && c0041a1.m209942b3()) {
                                                        z = true;
                                                    }
                                                }
                                                z = false;
                                            } else {
                                                ActionBarOverlayLayout actionBarOverlayLayout4 = (ActionBarOverlayLayout) this.f60816b7;
                                                actionBarOverlayLayout4.m209867b0();
                                                ActionMenuView actionMenuView4 = ((f71) actionBarOverlayLayout4.f43840a4).f56159a0.f44089a0;
                                                if (actionMenuView4 == null || (c0041a13 = actionMenuView4.f43868b9) == null || !c0041a13.m209939a3()) {
                                                    z = false;
                                                }
                                            }
                                        }
                                        if (z && (audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio")) != null) {
                                            audioManager.playSoundEffect(0);
                                            return true;
                                        }
                                    }
                                }
                            }
                            return false;
                        }
                        if (!m215033c8()) {
                            return false;
                        }
                    }
                } finally {
                    windowCallbackC1361w2.f60752a2 = false;
                }
            }
        }
        return true;
    }

    /* renamed from: c0 */
    public final void m215025c0(int i) {
        C1366w7 c1366w7M215029c4 = m215029c4(i);
        if (c1366w7M215029c4.f60788a7 != null) {
            Bundle bundle = new Bundle();
            c1366w7M215029c4.f60788a7.m210708c0(bundle);
            if (bundle.size() > 0) {
                c1366w7M215029c4.f60796b5 = bundle;
            }
            c1366w7M215029c4.f60788a7.m210712c4();
            c1366w7M215029c4.f60788a7.clear();
        }
        c1366w7M215029c4.f60795b4 = true;
        c1366w7M215029c4.f60794b3 = true;
        if ((i == 108 || i == 0) && this.f60816b7 != null) {
            C1366w7 c1366w7M215029c42 = m215029c4(0);
            c1366w7M215029c42.f60791b0 = false;
            m215036d1(c1366w7M215029c42, null);
        }
    }

    /* renamed from: c1 */
    public final void m215026c1() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ViewGroup viewGroup;
        if (this.f60824c5) {
            return;
        }
        int[] iArr = R$styleable.AppCompatTheme;
        Context context = this.f60809b0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        if (!typedArrayObtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        int i = 0;
        int i2 = 1;
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowNoTitle, false)) {
            mo214901a6(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBar, false)) {
            mo214901a6(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            mo214901a6(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            mo214901a6(10);
        }
        this.f60833d4 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        m215027c2();
        this.f60810b1.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        if (this.f60834d5) {
            viewGroup = this.f60832d3 ? (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_screen_simple, (ViewGroup) null);
        } else if (this.f60833d4) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(R$layout.abc_dialog_title_material, (ViewGroup) null);
            this.f60831d2 = false;
            this.f60830d1 = false;
        } else if (this.f60830d1) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R$attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new C0875mu(context, typedValue.resourceId) : context).inflate(R$layout.abc_screen_toolbar, (ViewGroup) null);
            InterfaceC1118qp interfaceC1118qp = (InterfaceC1118qp) viewGroup.findViewById(R$id.decor_content_parent);
            this.f60816b7 = interfaceC1118qp;
            interfaceC1118qp.setWindowCallback(this.f60810b1.getCallback());
            if (this.f60831d2) {
                ((ActionBarOverlayLayout) this.f60816b7).m209866a9(109);
            }
            if (this.f60828c9) {
                ((ActionBarOverlayLayout) this.f60816b7).m209866a9(2);
            }
            if (this.f60829d0) {
                ((ActionBarOverlayLayout) this.f60816b7).m209866a9(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.f60830d1 + ", windowActionBarOverlay: " + this.f60831d2 + ", android:windowIsFloating: " + this.f60833d4 + ", windowActionModeOverlay: " + this.f60832d3 + ", windowNoTitle: " + this.f60834d5 + " }");
        }
        C1327v7 c1327v7 = new C1327v7(this, i);
        WeakHashMap weakHashMap = xa1.f61054a0;
        la1.m213821c0(viewGroup, c1327v7);
        if (this.f60816b7 == null) {
            this.f60826c7 = (TextView) viewGroup.findViewById(R$id.title);
        }
        Method method = id1.f56869a0;
        try {
            Method method2 = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", null);
            if (!method2.isAccessible()) {
                method2.setAccessible(true);
            }
            method2.invoke(viewGroup, null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R$id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.f60810b1.findViewById(R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.f60810b1.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new C1327v7(this, i2));
        this.f60825c6 = viewGroup;
        Object obj = this.f60808a9;
        CharSequence title = obj instanceof Activity ? ((Activity) obj).getTitle() : this.f60815b6;
        if (!TextUtils.isEmpty(title)) {
            InterfaceC1118qp interfaceC1118qp2 = this.f60816b7;
            if (interfaceC1118qp2 != null) {
                interfaceC1118qp2.setWindowTitle(title);
            } else {
                ze1 ze1Var = this.f60813b4;
                if (ze1Var != null) {
                    f71 f71Var = (f71) ze1Var.f61520b0;
                    if (!f71Var.f56165a6) {
                        Toolbar toolbar = f71Var.f56159a0;
                        f71Var.f56166a7 = title;
                        if ((f71Var.f56160a1 & 8) != 0) {
                            toolbar.setTitle(title);
                            if (f71Var.f56165a6) {
                                xa1.m215153b5(toolbar.getRootView(), title);
                            }
                        }
                    }
                } else {
                    TextView textView = this.f60826c7;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
        }
        ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.f60825c6.findViewById(R.id.content);
        View decorView = this.f60810b1.getDecorView();
        contentFrameLayout2.f43953a6.set(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        WeakHashMap weakHashMap2 = xa1.f61054a0;
        if (ia1.m213142a2(contentFrameLayout2)) {
            contentFrameLayout2.requestLayout();
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(R$styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout2.getMinWidthMajor());
        typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout2.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout2.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout2.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout2.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes2.getValue(R$styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout2.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes2.recycle();
        contentFrameLayout2.requestLayout();
        this.f60824c5 = true;
        C1366w7 c1366w7M215029c4 = m215029c4(0);
        if (this.f60841e2 || c1366w7M215029c4.f60788a7 != null) {
            return;
        }
        m215031c6(108);
    }

    /* renamed from: c2 */
    public final void m215027c2() {
        if (this.f60810b1 == null) {
            Object obj = this.f60808a9;
            if (obj instanceof Activity) {
                m215020b3(((Activity) obj).getWindow());
            }
        }
        if (this.f60810b1 == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    /* renamed from: c3 */
    public final AbstractC1364w5 m215028c3(Context context) {
        if (this.f60847e8 == null) {
            if (zg1.f61550a3 == null) {
                Context applicationContext = context.getApplicationContext();
                LocationManager locationManager = (LocationManager) applicationContext.getSystemService("location");
                zg1 zg1Var = new zg1();
                zg1Var.f61553a2 = new a81();
                zg1Var.f61551a0 = applicationContext;
                zg1Var.f61552a1 = locationManager;
                zg1.f61550a3 = zg1Var;
            }
            this.f60847e8 = new C1362w3(this, zg1.f61550a3);
        }
        return this.f60847e8;
    }

    /* renamed from: c4 */
    public final C1366w7 m215029c4(int i) {
        C1366w7[] c1366w7Arr = this.f60836d7;
        if (c1366w7Arr == null || c1366w7Arr.length <= i) {
            C1366w7[] c1366w7Arr2 = new C1366w7[i + 1];
            if (c1366w7Arr != null) {
                System.arraycopy(c1366w7Arr, 0, c1366w7Arr2, 0, c1366w7Arr.length);
            }
            this.f60836d7 = c1366w7Arr2;
            c1366w7Arr = c1366w7Arr2;
        }
        C1366w7 c1366w7 = c1366w7Arr[i];
        if (c1366w7 != null) {
            return c1366w7;
        }
        C1366w7 c1366w72 = new C1366w7();
        c1366w72.f60781a0 = i;
        c1366w72.f60794b3 = false;
        c1366w7Arr[i] = c1366w72;
        return c1366w72;
    }

    /* renamed from: c5 */
    public final void m215030c5() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m215026c1();
        if (this.f60830d1 && this.f60813b4 == null) {
            Object obj = this.f60808a9;
            if (obj instanceof Activity) {
                this.f60813b4 = new ze1((Activity) obj, this.f60831d2);
            } else if (obj instanceof Dialog) {
                this.f60813b4 = new ze1((Dialog) obj);
            }
            ze1 ze1Var = this.f60813b4;
            if (ze1Var != null) {
                ze1Var.m215399e5(this.f60852f3);
            }
        }
    }

    /* renamed from: c6 */
    public final void m215031c6(int i) {
        this.f60850f1 = (1 << i) | this.f60850f1;
        if (this.f60849f0) {
            return;
        }
        View decorView = this.f60810b1.getDecorView();
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212775b2(decorView, this.f60851f2);
        this.f60849f0 = true;
    }

    /* renamed from: c7 */
    public final int m215032c7(Context context, int i) {
        if (i != -100) {
            if (i != -1) {
                if (i != 0) {
                    if (i != 1 && i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                        }
                        if (this.f60848e9 == null) {
                            this.f60848e9 = new C1362w3(this, context);
                        }
                        return this.f60848e9.mo215000a3();
                    }
                } else if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() != 0) {
                    return m215028c3(context).mo215000a3();
                }
            }
            return i;
        }
        return -1;
    }

    /* renamed from: c8 */
    public final boolean m215033c8() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        InterfaceC1119qq interfaceC1119qq;
        a71 a71Var;
        boolean z = this.f60838d9;
        this.f60838d9 = false;
        C1366w7 c1366w7M215029c4 = m215029c4(0);
        if (!c1366w7M215029c4.f60793b2) {
            AbstractC0903n7 abstractC0903n7 = this.f60819c0;
            if (abstractC0903n7 != null) {
                abstractC0903n7.mo214038a0();
                return true;
            }
            m215030c5();
            ze1 ze1Var = this.f60813b4;
            if (ze1Var == null || (interfaceC1119qq = ze1Var.f61520b0) == null || (a71Var = ((f71) interfaceC1119qq).f56159a0.f44126d7) == null || a71Var.f44a1 == null) {
                return false;
            }
            a71 a71Var2 = ((f71) interfaceC1119qq).f56159a0.f44126d7;
            ff0 ff0Var = a71Var2 == null ? null : a71Var2.f44a1;
            if (ff0Var != null) {
                ff0Var.collapseActionView();
            }
        } else if (!z) {
            m215023b7(c1366w7M215029c4, true);
            return true;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x0176, code lost:
    
        if (r2.f55684a6.getCount() > 0) goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* renamed from: c9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m215034c9(C1366w7 c1366w7, KeyEvent keyEvent) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i;
        ViewGroup.LayoutParams layoutParams;
        boolean z = c1366w7.f60793b2;
        int i2 = c1366w7.f60781a0;
        if (z || this.f60841e2) {
            return;
        }
        Context context = this.f60809b0;
        if (i2 == 0 && (context.getResources().getConfiguration().screenLayout & 15) == 4) {
            return;
        }
        Window.Callback callback = this.f60810b1.getCallback();
        if (callback != null && !callback.onMenuOpened(i2, c1366w7.f60788a7)) {
            m215023b7(c1366w7, true);
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null || !m215036d1(c1366w7, keyEvent)) {
            return;
        }
        C1365w6 c1365w6 = c1366w7.f60785a4;
        if (c1365w6 != null && !c1366w7.f60794b3) {
            View view = c1366w7.f60787a6;
            if (view != null && (layoutParams = view.getLayoutParams()) != null && layoutParams.width == -1) {
                i = -1;
            }
            c1366w7.f60792b1 = false;
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
            layoutParams2.gravity = c1366w7.f60783a2;
            layoutParams2.windowAnimations = c1366w7.f60784a3;
            windowManager.addView(c1366w7.f60785a4, layoutParams2);
            c1366w7.f60793b2 = true;
            if (i2 != 0) {
                m215038d3();
                return;
            }
            return;
        }
        if (c1365w6 == null) {
            m215030c5();
            ze1 ze1Var = this.f60813b4;
            Context contextM215397e3 = ze1Var != null ? ze1Var.m215397e3() : null;
            if (contextM215397e3 != null) {
                context = contextM215397e3;
            }
            TypedValue typedValue = new TypedValue();
            Resources.Theme themeNewTheme = context.getResources().newTheme();
            themeNewTheme.setTo(context.getTheme());
            themeNewTheme.resolveAttribute(R$attr.actionBarPopupTheme, typedValue, true);
            int i3 = typedValue.resourceId;
            if (i3 != 0) {
                themeNewTheme.applyStyle(i3, true);
            }
            themeNewTheme.resolveAttribute(R$attr.panelMenuListTheme, typedValue, true);
            int i4 = typedValue.resourceId;
            if (i4 != 0) {
                themeNewTheme.applyStyle(i4, true);
            } else {
                themeNewTheme.applyStyle(R$style.Theme_AppCompat_CompactMenu, true);
            }
            C0875mu c0875mu = new C0875mu(context, 0);
            c0875mu.getTheme().setTo(themeNewTheme);
            c1366w7.f60790a9 = c0875mu;
            TypedArray typedArrayObtainStyledAttributes = c0875mu.obtainStyledAttributes(R$styleable.AppCompatTheme);
            c1366w7.f60782a1 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_panelBackground, 0);
            c1366w7.f60784a3 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            typedArrayObtainStyledAttributes.recycle();
            c1366w7.f60785a4 = new C1365w6(this, c1366w7.f60790a9);
            c1366w7.f60783a2 = 81;
        } else if (c1366w7.f60794b3 && c1365w6.getChildCount() > 0) {
            c1366w7.f60785a4.removeAllViews();
        }
        View view2 = c1366w7.f60787a6;
        if (view2 == null) {
            if (c1366w7.f60788a7 != null) {
                if (this.f60818b9 == null) {
                    this.f60818b9 = new C1327v7(this, 3);
                }
                C1327v7 c1327v7 = this.f60818b9;
                if (c1366w7.f60789a8 == null) {
                    db0 db0Var = new db0(c1366w7.f60790a9, R$layout.abc_list_menu_item_layout);
                    c1366w7.f60789a8 = db0Var;
                    db0Var.f55683a5 = c1327v7;
                    bf0 bf0Var = c1366w7.f60788a7;
                    bf0Var.m210689a1(db0Var, bf0Var.f45866a0);
                }
                db0 db0Var2 = c1366w7.f60789a8;
                C1365w6 c1365w62 = c1366w7.f60785a4;
                if (db0Var2.f55681a3 == null) {
                    db0Var2.f55681a3 = (ExpandedMenuView) db0Var2.f55679a1.inflate(R$layout.abc_expanded_menu_layout, (ViewGroup) c1365w62, false);
                    if (db0Var2.f55684a6 == null) {
                        db0Var2.f55684a6 = new cb0(db0Var2);
                    }
                    db0Var2.f55681a3.setAdapter((ListAdapter) db0Var2.f55684a6);
                    db0Var2.f55681a3.setOnItemClickListener(db0Var2);
                }
                ExpandedMenuView expandedMenuView = db0Var2.f55681a3;
                c1366w7.f60786a5 = expandedMenuView;
                if (expandedMenuView != null) {
                }
            }
            c1366w7.f60794b3 = true;
            return;
        }
        c1366w7.f60786a5 = view2;
        if (c1366w7.f60786a5 != null) {
            if (c1366w7.f60787a6 == null) {
                db0 db0Var3 = c1366w7.f60789a8;
                if (db0Var3.f55684a6 == null) {
                    db0Var3.f55684a6 = new cb0(db0Var3);
                }
            }
            ViewGroup.LayoutParams layoutParams3 = c1366w7.f60786a5.getLayoutParams();
            if (layoutParams3 == null) {
                layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
            }
            c1366w7.f60785a4.setBackgroundResource(c1366w7.f60782a1);
            ViewParent parent = c1366w7.f60786a5.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(c1366w7.f60786a5);
            }
            c1366w7.f60785a4.addView(c1366w7.f60786a5, layoutParams3);
            if (!c1366w7.f60786a5.hasFocus()) {
                c1366w7.f60786a5.requestFocus();
            }
        }
        c1366w7.f60794b3 = true;
        return;
        i = -2;
        c1366w7.f60792b1 = false;
        WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(i, -2, 0, 0, 1002, 8519680, -3);
        layoutParams22.gravity = c1366w7.f60783a2;
        layoutParams22.windowAnimations = c1366w7.f60784a3;
        windowManager.addView(c1366w7.f60785a4, layoutParams22);
        c1366w7.f60793b2 = true;
        if (i2 != 0) {
        }
    }

    /* renamed from: d0 */
    public final boolean m215035d0(C1366w7 c1366w7, int i, KeyEvent keyEvent) {
        bf0 bf0Var;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((c1366w7.f60791b0 || m215036d1(c1366w7, keyEvent)) && (bf0Var = c1366w7.f60788a7) != null) {
            return bf0Var.performShortcut(i, keyEvent, 1);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00d0  */
    /* renamed from: d1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215036d1(C1366w7 c1366w7, KeyEvent keyEvent) {
        InterfaceC1118qp interfaceC1118qp;
        InterfaceC1118qp interfaceC1118qp2;
        Resources.Theme themeNewTheme;
        InterfaceC1118qp interfaceC1118qp3;
        InterfaceC1118qp interfaceC1118qp4;
        if (!this.f60841e2) {
            boolean z = c1366w7.f60791b0;
            int i = c1366w7.f60781a0;
            if (z) {
                return true;
            }
            C1366w7 c1366w72 = this.f60837d8;
            if (c1366w72 != null && c1366w72 != c1366w7) {
                m215023b7(c1366w72, false);
            }
            Window.Callback callback = this.f60810b1.getCallback();
            if (callback != null) {
                c1366w7.f60787a6 = callback.onCreatePanelView(i);
            }
            boolean z2 = i == 0 || i == 108;
            if (z2 && (interfaceC1118qp4 = this.f60816b7) != null) {
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) interfaceC1118qp4;
                actionBarOverlayLayout.m209867b0();
                ((f71) actionBarOverlayLayout.f43840a4).f56170b1 = true;
            }
            if (c1366w7.f60787a6 == null) {
                bf0 bf0Var = c1366w7.f60788a7;
                if (bf0Var == null || c1366w7.f60795b4) {
                    if (bf0Var == null) {
                        Context context = this.f60809b0;
                        if ((i == 0 || i == 108) && this.f60816b7 != null) {
                            TypedValue typedValue = new TypedValue();
                            Resources.Theme theme = context.getTheme();
                            theme.resolveAttribute(R$attr.actionBarTheme, typedValue, true);
                            if (typedValue.resourceId != 0) {
                                themeNewTheme = context.getResources().newTheme();
                                themeNewTheme.setTo(theme);
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                                themeNewTheme.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
                            } else {
                                theme.resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
                                themeNewTheme = null;
                            }
                            if (typedValue.resourceId != 0) {
                                if (themeNewTheme == null) {
                                    themeNewTheme = context.getResources().newTheme();
                                    themeNewTheme.setTo(theme);
                                }
                                themeNewTheme.applyStyle(typedValue.resourceId, true);
                            }
                            if (themeNewTheme != null) {
                                C0875mu c0875mu = new C0875mu(context, 0);
                                c0875mu.getTheme().setTo(themeNewTheme);
                                context = c0875mu;
                            }
                        }
                        bf0 bf0Var2 = new bf0(context);
                        bf0Var2.f45870a4 = this;
                        bf0 bf0Var3 = c1366w7.f60788a7;
                        if (bf0Var2 != bf0Var3) {
                            if (bf0Var3 != null) {
                                bf0Var3.m210705b7(c1366w7.f60789a8);
                            }
                            c1366w7.f60788a7 = bf0Var2;
                            db0 db0Var = c1366w7.f60789a8;
                            if (db0Var != null) {
                                bf0Var2.m210689a1(db0Var, bf0Var2.f45866a0);
                            }
                        }
                        if (c1366w7.f60788a7 != null) {
                            if (z2 && (interfaceC1118qp2 = this.f60816b7) != null) {
                                if (this.f60817b8 == null) {
                                    this.f60817b8 = new C1327v7(this, 2);
                                }
                                ((ActionBarOverlayLayout) interfaceC1118qp2).m209868b1(c1366w7.f60788a7, this.f60817b8);
                            }
                            c1366w7.f60788a7.m210712c4();
                            if (callback.onCreatePanelMenu(i, c1366w7.f60788a7)) {
                                c1366w7.f60795b4 = false;
                            } else {
                                bf0 bf0Var4 = c1366w7.f60788a7;
                                if (bf0Var4 != null) {
                                    if (bf0Var4 != null) {
                                        bf0Var4.m210705b7(c1366w7.f60789a8);
                                    }
                                    c1366w7.f60788a7 = null;
                                }
                                if (z2 && (interfaceC1118qp = this.f60816b7) != null) {
                                    ((ActionBarOverlayLayout) interfaceC1118qp).m209868b1(null, this.f60817b8);
                                }
                            }
                        }
                    }
                }
                c1366w7.f60788a7.m210712c4();
                Bundle bundle = c1366w7.f60796b5;
                if (bundle != null) {
                    c1366w7.f60788a7.m210706b8(bundle);
                    c1366w7.f60796b5 = null;
                }
                if (!callback.onPreparePanel(0, c1366w7.f60787a6, c1366w7.f60788a7)) {
                    if (z2 && (interfaceC1118qp3 = this.f60816b7) != null) {
                        ((ActionBarOverlayLayout) interfaceC1118qp3).m209868b1(null, this.f60817b8);
                    }
                    c1366w7.f60788a7.m210711c3();
                    return false;
                }
                c1366w7.f60788a7.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
                c1366w7.f60788a7.m210711c3();
            }
            c1366w7.f60791b0 = true;
            c1366w7.f60792b1 = false;
            this.f60837d8 = c1366w7;
            return true;
        }
        return false;
    }

    /* renamed from: d2 */
    public final void m215037d2() {
        if (this.f60824c5) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    /* renamed from: d3 */
    public final void m215038d3() {
        OnBackInvokedCallback onBackInvokedCallback;
        if (Build.VERSION.SDK_INT >= 33) {
            boolean z = false;
            if (this.f60856f7 != null && (m215029c4(0).f60793b2 || this.f60819c0 != null)) {
                z = true;
            }
            if (z && this.f60857f8 == null) {
                this.f60857f8 = AbstractC1360w1.m214975a1(this.f60856f7, this);
            } else {
                if (z || (onBackInvokedCallback = this.f60857f8) == null) {
                    return;
                }
                AbstractC1360w1.m214976a2(this.f60856f7, onBackInvokedCallback);
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:114:0x01bd
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0071  */
    @Override // android.view.LayoutInflater.Factory2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.view.View onCreateView(android.view.View r9, java.lang.String r10, android.content.Context r11, android.util.AttributeSet r12) {
        /*
            Method dump skipped, instructions count: 688
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p000.LayoutInflaterFactory2C1367w8.onCreateView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }
}
