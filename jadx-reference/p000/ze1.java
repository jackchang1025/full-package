package p000;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$bool;
import androidx.appcompat.R$id;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ze1 extends kj1 implements InterfaceC0849m5 {

    /* renamed from: d1 */
    public static final AccelerateInterpolator f61514d1 = new AccelerateInterpolator();

    /* renamed from: d2 */
    public static final DecelerateInterpolator f61515d2 = new DecelerateInterpolator();

    /* renamed from: a6 */
    public Context f61516a6;

    /* renamed from: a7 */
    public Context f61517a7;

    /* renamed from: a8 */
    public ActionBarOverlayLayout f61518a8;

    /* renamed from: a9 */
    public ActionBarContainer f61519a9;

    /* renamed from: b0 */
    public InterfaceC1119qq f61520b0;

    /* renamed from: b1 */
    public ActionBarContextView f61521b1;

    /* renamed from: b2 */
    public final View f61522b2;

    /* renamed from: b3 */
    public boolean f61523b3;

    /* renamed from: b4 */
    public ye1 f61524b4;

    /* renamed from: b5 */
    public ye1 f61525b5;

    /* renamed from: b6 */
    public eo0 f61526b6;

    /* renamed from: b7 */
    public boolean f61527b7;

    /* renamed from: b8 */
    public final ArrayList f61528b8;

    /* renamed from: b9 */
    public int f61529b9;

    /* renamed from: c0 */
    public boolean f61530c0;

    /* renamed from: c1 */
    public boolean f61531c1;

    /* renamed from: c2 */
    public boolean f61532c2;

    /* renamed from: c3 */
    public boolean f61533c3;

    /* renamed from: c4 */
    public boolean f61534c4;

    /* renamed from: c5 */
    public nc1 f61535c5;

    /* renamed from: c6 */
    public boolean f61536c6;

    /* renamed from: c7 */
    public boolean f61537c7;

    /* renamed from: c8 */
    public final xe1 f61538c8;

    /* renamed from: c9 */
    public final xe1 f61539c9;

    /* renamed from: d0 */
    public final jl0 f61540d0;

    public ze1(Activity activity, boolean z) {
        new ArrayList();
        this.f61528b8 = new ArrayList();
        this.f61529b9 = 0;
        this.f61530c0 = true;
        this.f61534c4 = true;
        this.f61538c8 = new xe1(this, 0);
        this.f61539c9 = new xe1(this, 1);
        this.f61540d0 = new jl0(this);
        View decorView = activity.getWindow().getDecorView();
        m215398e4(decorView);
        if (z) {
            return;
        }
        this.f61522b2 = decorView.findViewById(R.id.content);
    }

    /* renamed from: e2 */
    public final void m215396e2(boolean z) {
        mc1 mc1VarM209856a8;
        mc1 mc1VarM209856a82;
        if (z) {
            if (!this.f61533c3) {
                this.f61533c3 = true;
                ActionBarOverlayLayout actionBarOverlayLayout = this.f61518a8;
                if (actionBarOverlayLayout != null) {
                    actionBarOverlayLayout.setShowingForActionMode(true);
                }
                m215401e7(false);
            }
        } else if (this.f61533c3) {
            this.f61533c3 = false;
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f61518a8;
            if (actionBarOverlayLayout2 != null) {
                actionBarOverlayLayout2.setShowingForActionMode(false);
            }
            m215401e7(false);
        }
        ActionBarContainer actionBarContainer = this.f61519a9;
        WeakHashMap weakHashMap = xa1.f61054a0;
        if (!ia1.m213142a2(actionBarContainer)) {
            if (z) {
                ((f71) this.f61520b0).f56159a0.setVisibility(4);
                this.f61521b1.setVisibility(0);
                return;
            } else {
                ((f71) this.f61520b0).f56159a0.setVisibility(0);
                this.f61521b1.setVisibility(8);
                return;
            }
        }
        if (z) {
            f71 f71Var = (f71) this.f61520b0;
            mc1VarM209856a8 = xa1.m215138a0(f71Var.f56159a0);
            mc1VarM209856a8.m213967a0(0.0f);
            mc1VarM209856a8.m213969a2(100L);
            mc1VarM209856a8.m213970a3(new e71(f71Var, 4));
            mc1VarM209856a82 = this.f61521b1.m209856a8(0, 200L);
        } else {
            f71 f71Var2 = (f71) this.f61520b0;
            mc1 mc1VarM215138a0 = xa1.m215138a0(f71Var2.f56159a0);
            mc1VarM215138a0.m213967a0(1.0f);
            mc1VarM215138a0.m213969a2(200L);
            mc1VarM215138a0.m213970a3(new e71(f71Var2, 0));
            mc1VarM209856a8 = this.f61521b1.m209856a8(8, 100L);
            mc1VarM209856a82 = mc1VarM215138a0;
        }
        nc1 nc1Var = new nc1();
        ArrayList arrayList = nc1Var.f58497a0;
        arrayList.add(mc1VarM209856a8);
        View view = (View) mc1VarM209856a8.f58331a0.get();
        long duration = view != null ? view.animate().getDuration() : 0L;
        View view2 = (View) mc1VarM209856a82.f58331a0.get();
        if (view2 != null) {
            view2.animate().setStartDelay(duration);
        }
        arrayList.add(mc1VarM209856a82);
        nc1Var.m214071a1();
    }

    /* renamed from: e3 */
    public final Context m215397e3() {
        if (this.f61517a7 == null) {
            TypedValue typedValue = new TypedValue();
            this.f61516a6.getTheme().resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.f61517a7 = new ContextThemeWrapper(this.f61516a6, i);
            } else {
                this.f61517a7 = this.f61516a6;
            }
        }
        return this.f61517a7;
    }

    /* renamed from: e4 */
    public final void m215398e4(View view) {
        InterfaceC1119qq wrapper;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(R$id.decor_content_parent);
        this.f61518a8 = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        KeyEvent.Callback callbackFindViewById = view.findViewById(R$id.action_bar);
        if (callbackFindViewById instanceof InterfaceC1119qq) {
            wrapper = (InterfaceC1119qq) callbackFindViewById;
        } else {
            if (!(callbackFindViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(callbackFindViewById != null ? callbackFindViewById.getClass().getSimpleName() : "null"));
            }
            wrapper = ((Toolbar) callbackFindViewById).getWrapper();
        }
        this.f61520b0 = wrapper;
        this.f61521b1 = (ActionBarContextView) view.findViewById(R$id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(R$id.action_bar_container);
        this.f61519a9 = actionBarContainer;
        InterfaceC1119qq interfaceC1119qq = this.f61520b0;
        if (interfaceC1119qq == null || this.f61521b1 == null || actionBarContainer == null) {
            throw new IllegalStateException(ze1.class.getSimpleName().concat(" can only be used with a compatible window decor layout"));
        }
        Context context = ((f71) interfaceC1119qq).f56159a0.getContext();
        this.f61516a6 = context;
        if ((((f71) this.f61520b0).f56160a1 & 4) != 0) {
            this.f61523b3 = true;
        }
        int i = context.getApplicationInfo().targetSdkVersion;
        this.f61520b0.getClass();
        m215400e6(context.getResources().getBoolean(R$bool.abc_action_bar_embed_tabs));
        TypedArray typedArrayObtainStyledAttributes = this.f61516a6.obtainStyledAttributes(null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(R$styleable.ActionBar_hideOnContentScroll, false)) {
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.f61518a8;
            if (!actionBarOverlayLayout2.f43843a7) {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
            this.f61537c7 = true;
            actionBarOverlayLayout2.setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            ActionBarContainer actionBarContainer2 = this.f61519a9;
            WeakHashMap weakHashMap = xa1.f61054a0;
            la1.m213819b8(actionBarContainer2, dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: e5 */
    public final void m215399e5(boolean z) {
        if (this.f61523b3) {
            return;
        }
        int i = z ? 4 : 0;
        f71 f71Var = (f71) this.f61520b0;
        int i2 = f71Var.f56160a1;
        this.f61523b3 = true;
        f71Var.m212754a0((i & 4) | (i2 & (-5)));
    }

    /* renamed from: e6 */
    public final void m215400e6(boolean z) {
        if (z) {
            this.f61519a9.setTabContainer(null);
            ((f71) this.f61520b0).getClass();
        } else {
            ((f71) this.f61520b0).getClass();
            this.f61519a9.setTabContainer(null);
        }
        f71 f71Var = (f71) this.f61520b0;
        f71Var.getClass();
        f71Var.f56159a0.setCollapsible(false);
        this.f61518a8.setHasNonEmbeddedTabs(false);
    }

    /* renamed from: e7 */
    public final void m215401e7(boolean z) {
        boolean z2 = this.f61531c1;
        boolean z3 = this.f61532c2;
        boolean z4 = this.f61533c3;
        jl0 jl0Var = this.f61540d0;
        View view = this.f61522b2;
        if (!z4 && (z2 || z3)) {
            if (this.f61534c4) {
                this.f61534c4 = false;
                nc1 nc1Var = this.f61535c5;
                if (nc1Var != null) {
                    nc1Var.m214070a0();
                }
                int i = this.f61529b9;
                xe1 xe1Var = this.f61538c8;
                if (i != 0 || (!this.f61536c6 && !z)) {
                    xe1Var.mo212658a0();
                    return;
                }
                this.f61519a9.setAlpha(1.0f);
                this.f61519a9.setTransitioning(true);
                nc1 nc1Var2 = new nc1();
                float f = -this.f61519a9.getHeight();
                if (z) {
                    this.f61519a9.getLocationInWindow(new int[]{0, 0});
                    f -= r12[1];
                }
                mc1 mc1VarM215138a0 = xa1.m215138a0(this.f61519a9);
                mc1VarM215138a0.m213971a4(f);
                View view2 = (View) mc1VarM215138a0.f58331a0.get();
                if (view2 != null) {
                    lc1.m213832a0(view2.animate(), jl0Var != null ? new C1306un(jl0Var, view2) : null);
                }
                boolean z5 = nc1Var2.f58501a4;
                ArrayList arrayList = nc1Var2.f58497a0;
                if (!z5) {
                    arrayList.add(mc1VarM215138a0);
                }
                if (this.f61530c0 && view != null) {
                    mc1 mc1VarM215138a02 = xa1.m215138a0(view);
                    mc1VarM215138a02.m213971a4(f);
                    if (!nc1Var2.f58501a4) {
                        arrayList.add(mc1VarM215138a02);
                    }
                }
                boolean z6 = nc1Var2.f58501a4;
                if (!z6) {
                    nc1Var2.f58499a2 = f61514d1;
                }
                if (!z6) {
                    nc1Var2.f58498a1 = 250L;
                }
                if (!z6) {
                    nc1Var2.f58500a3 = xe1Var;
                }
                this.f61535c5 = nc1Var2;
                nc1Var2.m214071a1();
                return;
            }
            return;
        }
        if (this.f61534c4) {
            return;
        }
        this.f61534c4 = true;
        nc1 nc1Var3 = this.f61535c5;
        if (nc1Var3 != null) {
            nc1Var3.m214070a0();
        }
        this.f61519a9.setVisibility(0);
        int i2 = this.f61529b9;
        xe1 xe1Var2 = this.f61539c9;
        if (i2 == 0 && (this.f61536c6 || z)) {
            this.f61519a9.setTranslationY(0.0f);
            float f2 = -this.f61519a9.getHeight();
            if (z) {
                this.f61519a9.getLocationInWindow(new int[]{0, 0});
                f2 -= r12[1];
            }
            this.f61519a9.setTranslationY(f2);
            nc1 nc1Var4 = new nc1();
            mc1 mc1VarM215138a03 = xa1.m215138a0(this.f61519a9);
            mc1VarM215138a03.m213971a4(0.0f);
            View view3 = (View) mc1VarM215138a03.f58331a0.get();
            if (view3 != null) {
                lc1.m213832a0(view3.animate(), jl0Var != null ? new C1306un(jl0Var, view3) : null);
            }
            boolean z7 = nc1Var4.f58501a4;
            ArrayList arrayList2 = nc1Var4.f58497a0;
            if (!z7) {
                arrayList2.add(mc1VarM215138a03);
            }
            if (this.f61530c0 && view != null) {
                view.setTranslationY(f2);
                mc1 mc1VarM215138a04 = xa1.m215138a0(view);
                mc1VarM215138a04.m213971a4(0.0f);
                if (!nc1Var4.f58501a4) {
                    arrayList2.add(mc1VarM215138a04);
                }
            }
            boolean z8 = nc1Var4.f58501a4;
            if (!z8) {
                nc1Var4.f58499a2 = f61515d2;
            }
            if (!z8) {
                nc1Var4.f58498a1 = 250L;
            }
            if (!z8) {
                nc1Var4.f58500a3 = xe1Var2;
            }
            this.f61535c5 = nc1Var4;
            nc1Var4.m214071a1();
        } else {
            this.f61519a9.setAlpha(1.0f);
            this.f61519a9.setTranslationY(0.0f);
            if (this.f61530c0 && view != null) {
                view.setTranslationY(0.0f);
            }
            xe1Var2.mo212658a0();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.f61518a8;
        if (actionBarOverlayLayout != null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            ja1.m213282a2(actionBarOverlayLayout);
        }
    }

    public ze1(Dialog dialog) {
        new ArrayList();
        this.f61528b8 = new ArrayList();
        this.f61529b9 = 0;
        this.f61530c0 = true;
        this.f61534c4 = true;
        this.f61538c8 = new xe1(this, 0);
        this.f61539c9 = new xe1(this, 1);
        this.f61540d0 = new jl0(this);
        m215398e4(dialog.getWindow().getDecorView());
    }
}
