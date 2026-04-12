package p000;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.C0038a0;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.app.AlertController$RecycleListView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;
import java.lang.reflect.InvocationTargetException;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: r4 */
/* loaded from: classes.dex */
public final class DialogC1167r4 extends Dialog implements DialogInterface, InterfaceC1320v0, ka0 {

    /* renamed from: a0 */
    public C0076a0 f59619a0;

    /* renamed from: a1 */
    public final C0038a0 f59620a1;

    /* renamed from: a2 */
    public LayoutInflaterFactory2C1367w8 f59621a2;

    /* renamed from: a3 */
    public final C1368w9 f59622a3;

    /* renamed from: a4 */
    public final C1165r2 f59623a4;

    /* JADX WARN: Type inference failed for: r1v3, types: [w9] */
    public DialogC1167r4(ContextThemeWrapper contextThemeWrapper, int i) {
        int i2;
        int iM214477a7 = m214477a7(contextThemeWrapper, i);
        if (iM214477a7 == 0) {
            TypedValue typedValue = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R$attr.dialogTheme, typedValue, true);
            i2 = typedValue.resourceId;
        } else {
            i2 = iM214477a7;
        }
        super(contextThemeWrapper, i2);
        this.f59620a1 = new C0038a0(new RunnableC0941o6(7, this));
        this.f59622a3 = new p80() { // from class: w9
            @Override // p000.p80
            /* renamed from: a1 */
            public final boolean mo210074a1(KeyEvent keyEvent) {
                return this.f60858a0.m214483a9(keyEvent);
            }
        };
        AbstractC1325v5 abstractC1325v5M214478a2 = m214478a2();
        if (iM214477a7 == 0) {
            TypedValue typedValue2 = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R$attr.dialogTheme, typedValue2, true);
            iM214477a7 = typedValue2.resourceId;
        }
        ((LayoutInflaterFactory2C1367w8) abstractC1325v5M214478a2).f60844e5 = iM214477a7;
        abstractC1325v5M214478a2.mo214899a2();
        this.f59623a4 = new C1165r2(getContext(), this, getWindow());
    }

    /* renamed from: a1 */
    public static void m214476a1(DialogC1167r4 dialogC1167r4) {
        super.onBackPressed();
    }

    /* renamed from: a7 */
    public static int m214477a7(Context context, int i) {
        if (((i >>> 24) & v10.MASK) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R$attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    /* renamed from: a2 */
    public final AbstractC1325v5 m214478a2() {
        if (this.f59621a2 == null) {
            ExecutorC0034an executorC0034an = AbstractC1325v5.f60575a0;
            this.f59621a2 = new LayoutInflaterFactory2C1367w8(getContext(), getWindow(), this, this);
        }
        return this.f59621a2;
    }

    /* renamed from: a3 */
    public final void m214479a3(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcher = getOnBackInvokedDispatcher();
            C0038a0 c0038a0 = this.f59620a1;
            c0038a0.f43759a4 = onBackInvokedDispatcher;
            c0038a0.m209836a2();
        }
        C0076a0 c0076a0 = this.f59619a0;
        if (c0076a0 == null) {
            c0076a0 = new C0076a0(this, true);
            this.f59619a0 = c0076a0;
        }
        c0076a0.m210234g1(Lifecycle$Event.ON_CREATE);
    }

    /* renamed from: a4 */
    public final void m214480a4(Bundle bundle) {
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) m214478a2();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(layoutInflaterFactory2C1367w8.f60809b0);
        if (layoutInflaterFrom.getFactory() == null) {
            layoutInflaterFrom.setFactory2(layoutInflaterFactory2C1367w8);
        } else {
            layoutInflaterFrom.getFactory2();
        }
        m214479a3(bundle);
        m214478a2().mo214899a2();
    }

    @Override // p000.ka0
    /* renamed from: a5 */
    public final C0076a0 mo209830a5() {
        C0076a0 c0076a0 = this.f59619a0;
        if (c0076a0 != null) {
            return c0076a0;
        }
        C0076a0 c0076a02 = new C0076a0(this, true);
        this.f59619a0 = c0076a02;
        return c0076a02;
    }

    /* renamed from: a6 */
    public final void m214481a6() {
        C0076a0 c0076a0 = this.f59619a0;
        if (c0076a0 == null) {
            c0076a0 = new C0076a0(this, true);
            this.f59619a0 = c0076a0;
        }
        c0076a0.m210234g1(Lifecycle$Event.ON_DESTROY);
        this.f59619a0 = null;
        super.onStop();
    }

    /* renamed from: a8 */
    public final void m214482a8(CharSequence charSequence) {
        super.setTitle(charSequence);
        m214478a2().mo214905b1(charSequence);
    }

    /* renamed from: a9 */
    public final boolean m214483a9(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Dialog
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) m214478a2();
        layoutInflaterFactory2C1367w8.m215026c1();
        ((ViewGroup) layoutInflaterFactory2C1367w8.f60825c6.findViewById(R.id.content)).addView(view, layoutParams);
        layoutInflaterFactory2C1367w8.f60811b2.m214981a0(layoutInflaterFactory2C1367w8.f60810b1.getCallback());
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        m214478a2().mo214900a3();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return cq0.m212481b1(this.f59622a3, getWindow().getDecorView(), this, keyEvent);
    }

    @Override // android.app.Dialog
    public final View findViewById(int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) m214478a2();
        layoutInflaterFactory2C1367w8.m215026c1();
        return layoutInflaterFactory2C1367w8.f60810b1.findViewById(i);
    }

    @Override // android.app.Dialog
    public final void invalidateOptionsMenu() {
        m214478a2().mo214898a0();
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        this.f59620a1.m209835a1();
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        int i;
        ListAdapter listAdapter;
        View viewFindViewById;
        m214480a4(bundle);
        C1165r2 c1165r2 = this.f59623a4;
        c1165r2.f59583a1.setContentView(c1165r2.f59598b6);
        Context context = c1165r2.f59582a0;
        Window window = c1165r2.f59584a2;
        View viewFindViewById2 = window.findViewById(R$id.parentPanel);
        View viewFindViewById3 = viewFindViewById2.findViewById(R$id.topPanel);
        View viewFindViewById4 = viewFindViewById2.findViewById(R$id.contentPanel);
        View viewFindViewById5 = viewFindViewById2.findViewById(R$id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById2.findViewById(R$id.customPanel);
        window.setFlags(131072, 131072);
        viewGroup.setVisibility(8);
        View viewFindViewById6 = viewGroup.findViewById(R$id.topPanel);
        View viewFindViewById7 = viewGroup.findViewById(R$id.contentPanel);
        View viewFindViewById8 = viewGroup.findViewById(R$id.buttonPanel);
        ViewGroup viewGroupM214469a0 = C1165r2.m214469a0(viewFindViewById6, viewFindViewById3);
        ViewGroup viewGroupM214469a02 = C1165r2.m214469a0(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupM214469a03 = C1165r2.m214469a0(viewFindViewById8, viewFindViewById5);
        NestedScrollView nestedScrollView = (NestedScrollView) window.findViewById(R$id.scrollView);
        c1165r2.f59590a8 = nestedScrollView;
        nestedScrollView.setFocusable(false);
        c1165r2.f59590a8.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroupM214469a02.findViewById(R.id.message);
        c1165r2.f59594b2 = textView;
        if (textView != null) {
            textView.setVisibility(8);
            c1165r2.f59590a8.removeView(c1165r2.f59594b2);
            if (c1165r2.f59586a4 != null) {
                ViewGroup viewGroup2 = (ViewGroup) c1165r2.f59590a8.getParent();
                int iIndexOfChild = viewGroup2.indexOfChild(c1165r2.f59590a8);
                viewGroup2.removeViewAt(iIndexOfChild);
                viewGroup2.addView(c1165r2.f59586a4, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
            } else {
                viewGroupM214469a02.setVisibility(8);
            }
        }
        Button button = (Button) viewGroupM214469a03.findViewById(R.id.button1);
        c1165r2.f59587a5 = button;
        ViewOnClickListenerC0846m2 viewOnClickListenerC0846m2 = c1165r2.f59604c2;
        button.setOnClickListener(viewOnClickListenerC0846m2);
        if (TextUtils.isEmpty(null)) {
            c1165r2.f59587a5.setVisibility(8);
            i = 0;
        } else {
            c1165r2.f59587a5.setText((CharSequence) null);
            c1165r2.f59587a5.setVisibility(0);
            i = 1;
        }
        Button button2 = (Button) viewGroupM214469a03.findViewById(R.id.button2);
        c1165r2.f59588a6 = button2;
        button2.setOnClickListener(viewOnClickListenerC0846m2);
        if (TextUtils.isEmpty(null)) {
            c1165r2.f59588a6.setVisibility(8);
        } else {
            c1165r2.f59588a6.setText((CharSequence) null);
            c1165r2.f59588a6.setVisibility(0);
            i |= 2;
        }
        Button button3 = (Button) viewGroupM214469a03.findViewById(R.id.button3);
        c1165r2.f59589a7 = button3;
        button3.setOnClickListener(viewOnClickListenerC0846m2);
        if (TextUtils.isEmpty(null)) {
            c1165r2.f59589a7.setVisibility(8);
        } else {
            c1165r2.f59589a7.setText((CharSequence) null);
            c1165r2.f59589a7.setVisibility(0);
            i |= 4;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R$attr.alertDialogCenterButtons, typedValue, true);
        if (typedValue.data != 0) {
            if (i == 1) {
                Button button4 = c1165r2.f59587a5;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button4.getLayoutParams();
                layoutParams.gravity = 1;
                layoutParams.weight = 0.5f;
                button4.setLayoutParams(layoutParams);
            } else if (i == 2) {
                Button button5 = c1165r2.f59588a6;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button5.getLayoutParams();
                layoutParams2.gravity = 1;
                layoutParams2.weight = 0.5f;
                button5.setLayoutParams(layoutParams2);
            } else if (i == 4) {
                Button button6 = c1165r2.f59589a7;
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button6.getLayoutParams();
                layoutParams3.gravity = 1;
                layoutParams3.weight = 0.5f;
                button6.setLayoutParams(layoutParams3);
            }
        }
        if (i == 0) {
            viewGroupM214469a03.setVisibility(8);
        }
        if (c1165r2.f59595b3 != null) {
            viewGroupM214469a0.addView(c1165r2.f59595b3, 0, new ViewGroup.LayoutParams(-1, -2));
            window.findViewById(R$id.title_template).setVisibility(8);
        } else {
            c1165r2.f59592b0 = (ImageView) window.findViewById(R.id.icon);
            if (TextUtils.isEmpty(c1165r2.f59585a3) || !c1165r2.f59602c0) {
                window.findViewById(R$id.title_template).setVisibility(8);
                c1165r2.f59592b0.setVisibility(8);
                viewGroupM214469a0.setVisibility(8);
            } else {
                TextView textView2 = (TextView) window.findViewById(R$id.alertTitle);
                c1165r2.f59593b1 = textView2;
                textView2.setText(c1165r2.f59585a3);
                Drawable drawable = c1165r2.f59591a9;
                if (drawable != null) {
                    c1165r2.f59592b0.setImageDrawable(drawable);
                } else {
                    c1165r2.f59593b1.setPadding(c1165r2.f59592b0.getPaddingLeft(), c1165r2.f59592b0.getPaddingTop(), c1165r2.f59592b0.getPaddingRight(), c1165r2.f59592b0.getPaddingBottom());
                    c1165r2.f59592b0.setVisibility(8);
                }
            }
        }
        boolean z = viewGroup.getVisibility() != 8;
        int i2 = (viewGroupM214469a0 == null || viewGroupM214469a0.getVisibility() == 8) ? 0 : 1;
        boolean z2 = viewGroupM214469a03.getVisibility() != 8;
        if (!z2 && (viewFindViewById = viewGroupM214469a02.findViewById(R$id.textSpacerNoButtons)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (i2 != 0) {
            NestedScrollView nestedScrollView2 = c1165r2.f59590a8;
            if (nestedScrollView2 != null) {
                nestedScrollView2.setClipToPadding(true);
            }
            View viewFindViewById9 = c1165r2.f59586a4 != null ? viewGroupM214469a0.findViewById(R$id.titleDividerNoCustom) : null;
            if (viewFindViewById9 != null) {
                viewFindViewById9.setVisibility(0);
            }
        } else {
            View viewFindViewById10 = viewGroupM214469a02.findViewById(R$id.textSpacerNoTitle);
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        }
        AlertController$RecycleListView alertController$RecycleListView = c1165r2.f59586a4;
        if (alertController$RecycleListView != null && (!z2 || i2 == 0)) {
            alertController$RecycleListView.setPadding(alertController$RecycleListView.getPaddingLeft(), i2 != 0 ? alertController$RecycleListView.getPaddingTop() : alertController$RecycleListView.f43769a0, alertController$RecycleListView.getPaddingRight(), z2 ? alertController$RecycleListView.getPaddingBottom() : alertController$RecycleListView.f43770a1);
        }
        if (!z) {
            View view = c1165r2.f59586a4;
            if (view == null) {
                view = c1165r2.f59590a8;
            }
            if (view != null) {
                int i3 = z2 ? 2 : 0;
                View viewFindViewById11 = window.findViewById(R$id.scrollIndicatorUp);
                View viewFindViewById12 = window.findViewById(R$id.scrollIndicatorDown);
                WeakHashMap weakHashMap = xa1.f61054a0;
                ma1.m213957a3(view, i2 | i3, 3);
                if (viewFindViewById11 != null) {
                    viewGroupM214469a02.removeView(viewFindViewById11);
                }
                if (viewFindViewById12 != null) {
                    viewGroupM214469a02.removeView(viewFindViewById12);
                }
            }
        }
        AlertController$RecycleListView alertController$RecycleListView2 = c1165r2.f59586a4;
        if (alertController$RecycleListView2 == null || (listAdapter = c1165r2.f59596b4) == null) {
            return;
        }
        alertController$RecycleListView2.setAdapter(listAdapter);
        int i4 = c1165r2.f59597b5;
        if (i4 > -1) {
            alertController$RecycleListView2.setItemChecked(i4, true);
            alertController$RecycleListView2.setSelection(i4);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f59623a4.f59590a8;
        if (nestedScrollView == null || !nestedScrollView.m210087a8(keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f59623a4.f59590a8;
        if (nestedScrollView == null || !nestedScrollView.m210087a8(keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        C0076a0 c0076a0 = this.f59619a0;
        if (c0076a0 == null) {
            c0076a0 = new C0076a0(this, true);
            this.f59619a0 = c0076a0;
        }
        c0076a0.m210234g1(Lifecycle$Event.ON_RESUME);
    }

    @Override // android.app.Dialog
    public final void onStop() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        m214481a6();
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) m214478a2();
        layoutInflaterFactory2C1367w8.m215030c5();
        ze1 ze1Var = layoutInflaterFactory2C1367w8.f60813b4;
        if (ze1Var != null) {
            ze1Var.f61536c6 = false;
            nc1 nc1Var = ze1Var.f61535c5;
            if (nc1Var != null) {
                nc1Var.m214070a0();
            }
        }
    }

    @Override // android.app.Dialog
    public final void setContentView(int i) {
        m214478a2().mo214902a7(i);
    }

    @Override // android.app.Dialog
    public final void setTitle(int i) {
        super.setTitle(i);
        m214478a2().mo214905b1(getContext().getString(i));
    }

    @Override // android.app.Dialog
    public final void setContentView(View view) {
        m214478a2().mo214903a8(view);
    }

    @Override // android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        m214478a2().mo214904a9(view, layoutParams);
    }

    @Override // android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        m214482a8(charSequence);
        C1165r2 c1165r2 = this.f59623a4;
        c1165r2.f59585a3 = charSequence;
        TextView textView = c1165r2.f59593b1;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }
}
