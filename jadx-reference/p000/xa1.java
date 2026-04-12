package p000;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.R$id;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class xa1 {

    /* renamed from: a0 */
    public static WeakHashMap f61054a0;

    /* renamed from: a1 */
    public static Field f61055a1;

    /* renamed from: a2 */
    public static boolean f61056a2;

    /* renamed from: a3 */
    public static final int[] f61057a3;

    /* renamed from: a4 */
    public static final ba1 f61058a4;

    /* renamed from: a5 */
    public static final da1 f61059a5;

    static {
        new AtomicInteger(1);
        f61054a0 = null;
        f61056a2 = false;
        f61057a3 = new int[]{R$id.accessibility_custom_action_0, R$id.accessibility_custom_action_1, R$id.accessibility_custom_action_2, R$id.accessibility_custom_action_3, R$id.accessibility_custom_action_4, R$id.accessibility_custom_action_5, R$id.accessibility_custom_action_6, R$id.accessibility_custom_action_7, R$id.accessibility_custom_action_8, R$id.accessibility_custom_action_9, R$id.accessibility_custom_action_10, R$id.accessibility_custom_action_11, R$id.accessibility_custom_action_12, R$id.accessibility_custom_action_13, R$id.accessibility_custom_action_14, R$id.accessibility_custom_action_15, R$id.accessibility_custom_action_16, R$id.accessibility_custom_action_17, R$id.accessibility_custom_action_18, R$id.accessibility_custom_action_19, R$id.accessibility_custom_action_20, R$id.accessibility_custom_action_21, R$id.accessibility_custom_action_22, R$id.accessibility_custom_action_23, R$id.accessibility_custom_action_24, R$id.accessibility_custom_action_25, R$id.accessibility_custom_action_26, R$id.accessibility_custom_action_27, R$id.accessibility_custom_action_28, R$id.accessibility_custom_action_29, R$id.accessibility_custom_action_30, R$id.accessibility_custom_action_31};
        f61058a4 = new ba1();
        f61059a5 = new da1();
    }

    /* renamed from: a0 */
    public static mc1 m215138a0(View view) {
        if (f61054a0 == null) {
            f61054a0 = new WeakHashMap();
        }
        mc1 mc1Var = (mc1) f61054a0.get(view);
        if (mc1Var != null) {
            return mc1Var;
        }
        mc1 mc1Var2 = new mc1(view);
        f61054a0.put(view, mc1Var2);
        return mc1Var2;
    }

    /* renamed from: a1 */
    public static void m215139a1(View view, xf1 xf1Var) {
        WindowInsets windowInsetsM215175a5 = xf1Var.m215175a5();
        if (windowInsetsM215175a5 != null) {
            WindowInsets windowInsetsM213280a0 = ja1.m213280a0(view, windowInsetsM215175a5);
            if (windowInsetsM213280a0.equals(windowInsetsM215175a5)) {
                return;
            }
            xf1.m215170a6(view, windowInsetsM213280a0);
        }
    }

    /* renamed from: a2 */
    public static boolean m215140a2(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        ArrayList arrayList = wa1.f60875a3;
        wa1 wa1Var = (wa1) view.getTag(R$id.tag_unhandled_key_event_manager);
        if (wa1Var == null) {
            wa1Var = new wa1();
            wa1Var.f60876a0 = null;
            wa1Var.f60877a1 = null;
            wa1Var.f60878a2 = null;
            view.setTag(R$id.tag_unhandled_key_event_manager, wa1Var);
        }
        if (keyEvent.getAction() == 0) {
            WeakHashMap weakHashMap = wa1Var.f60876a0;
            if (weakHashMap != null) {
                weakHashMap.clear();
            }
            ArrayList arrayList2 = wa1.f60875a3;
            if (!arrayList2.isEmpty()) {
                synchronized (arrayList2) {
                    try {
                        if (wa1Var.f60876a0 == null) {
                            wa1Var.f60876a0 = new WeakHashMap();
                        }
                        for (int size = arrayList2.size() - 1; size >= 0; size--) {
                            ArrayList arrayList3 = wa1.f60875a3;
                            View view2 = (View) ((WeakReference) arrayList3.get(size)).get();
                            if (view2 == null) {
                                arrayList3.remove(size);
                            } else {
                                wa1Var.f60876a0.put(view2, Boolean.TRUE);
                                for (ViewParent parent = view2.getParent(); parent instanceof View; parent = parent.getParent()) {
                                    wa1Var.f60876a0.put((View) parent, Boolean.TRUE);
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        View viewM215043a0 = wa1Var.m215043a0(view);
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (viewM215043a0 != null && !KeyEvent.isModifierKey(keyCode)) {
                if (wa1Var.f60877a1 == null) {
                    wa1Var.f60877a1 = new SparseArray();
                }
                wa1Var.f60877a1.put(keyCode, new WeakReference(viewM215043a0));
            }
        }
        return viewM215043a0 != null;
    }

    /* renamed from: a3 */
    public static View.AccessibilityDelegate m215141a3(View view) {
        if (Build.VERSION.SDK_INT >= 29) {
            return ra1.m214522a0(view);
        }
        if (f61056a2) {
            return null;
        }
        if (f61055a1 == null) {
            try {
                Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                f61055a1 = declaredField;
                declaredField.setAccessible(true);
            } catch (Throwable unused) {
                f61056a2 = true;
                return null;
            }
        }
        try {
            Object obj = f61055a1.get(view);
            if (obj instanceof View.AccessibilityDelegate) {
                return (View.AccessibilityDelegate) obj;
            }
            return null;
        } catch (Throwable unused2) {
            f61056a2 = true;
            return null;
        }
    }

    /* renamed from: a4 */
    public static CharSequence m215142a4(View view) {
        Object tag;
        int i = R$id.tag_accessibility_pane_title;
        if (Build.VERSION.SDK_INT >= 28) {
            tag = qa1.m214364a1(view);
        } else {
            tag = view.getTag(i);
            if (!CharSequence.class.isInstance(tag)) {
                tag = null;
            }
        }
        return (CharSequence) tag;
    }

    /* renamed from: a5 */
    public static ArrayList m215143a5(View view) {
        ArrayList arrayList = (ArrayList) view.getTag(R$id.tag_accessibility_actions);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        view.setTag(R$id.tag_accessibility_actions, arrayList2);
        return arrayList2;
    }

    /* renamed from: a6 */
    public static String[] m215144a6(AppCompatEditText appCompatEditText) {
        return Build.VERSION.SDK_INT >= 31 ? ta1.m214730a0(appCompatEditText) : (String[]) appCompatEditText.getTag(R$id.tag_on_receive_content_mime_types);
    }

    /* renamed from: a7 */
    public static ag1 m215145a7(View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            return sa1.m214587a1(view);
        }
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    return new ag1(window, view);
                }
                return null;
            }
        }
        return null;
    }

    /* renamed from: a8 */
    public static void m215146a8(View view, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            boolean z = m215142a4(view) != null && view.isShown() && view.getWindowVisibility() == 0;
            if (ia1.m213140a0(view) != 0 || z) {
                AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                accessibilityEventObtain.setEventType(z ? 32 : 2048);
                ia1.m213146a6(accessibilityEventObtain, i);
                if (z) {
                    accessibilityEventObtain.getText().add(m215142a4(view));
                    if (fa1.m212765a2(view) == 0) {
                        fa1.m212781b8(view, 1);
                    }
                    ViewParent parent = view.getParent();
                    while (true) {
                        if (!(parent instanceof View)) {
                            break;
                        }
                        if (fa1.m212765a2((View) parent) == 4) {
                            fa1.m212781b8(view, 2);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
                view.sendAccessibilityEventUnchecked(accessibilityEventObtain);
                return;
            }
            if (i != 32) {
                if (view.getParent() != null) {
                    try {
                        ia1.m213144a4(view.getParent(), view, view, i);
                        return;
                    } catch (AbstractMethodError unused) {
                        view.getParent().getClass();
                        return;
                    }
                }
                return;
            }
            AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain();
            view.onInitializeAccessibilityEvent(accessibilityEventObtain2);
            accessibilityEventObtain2.setEventType(32);
            ia1.m213146a6(accessibilityEventObtain2, i);
            accessibilityEventObtain2.setSource(view);
            view.onPopulateAccessibilityEvent(accessibilityEventObtain2);
            accessibilityEventObtain2.getText().add(m215142a4(view));
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a9 */
    public static C0862mi m215147a9(View view, C0862mi c0862mi) {
        if (Log.isLoggable("ViewCompat", 3)) {
            Objects.toString(c0862mi);
            view.getClass();
            view.getId();
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return ta1.m214731a1(view, c0862mi);
        }
        cl0 cl0Var = (cl0) view.getTag(R$id.tag_on_receive_content_listener);
        dl0 dl0Var = f61058a4;
        if (cl0Var == null) {
            if (view instanceof dl0) {
                dl0Var = (dl0) view;
            }
            return dl0Var.mo209878a0(c0862mi);
        }
        C0862mi c0862miM213004a0 = ((h61) cl0Var).m213004a0(view, c0862mi);
        if (c0862miM213004a0 == null) {
            return null;
        }
        if (view instanceof dl0) {
            dl0Var = (dl0) view;
        }
        return dl0Var.mo209878a0(c0862miM213004a0);
    }

    /* renamed from: b0 */
    public static void m215148b0(View view, int i) {
        m215149b1(view, i);
        m215146a8(view, 0);
    }

    /* renamed from: b1 */
    public static void m215149b1(View view, int i) {
        ArrayList arrayListM215143a5 = m215143a5(view);
        for (int i2 = 0; i2 < arrayListM215143a5.size(); i2++) {
            if (((C0745k4) arrayListM215143a5.get(i2)).m213448a0() == i) {
                arrayListM215143a5.remove(i2);
                return;
            }
        }
    }

    /* renamed from: b2 */
    public static void m215150b2(View view, C0745k4 c0745k4, String str, InterfaceC0812l9 interfaceC0812l9) {
        if (interfaceC0812l9 == null && str == null) {
            m215148b0(view, c0745k4.m213448a0());
            return;
        }
        C0745k4 c0745k42 = new C0745k4(null, c0745k4.f57447a1, str, interfaceC0812l9, c0745k4.f57448a2);
        View.AccessibilityDelegate accessibilityDelegateM215141a3 = m215141a3(view);
        C0608i4 c0608i4 = accessibilityDelegateM215141a3 == null ? null : accessibilityDelegateM215141a3 instanceof C0606i2 ? ((C0606i2) accessibilityDelegateM215141a3).f56784a0 : new C0608i4(accessibilityDelegateM215141a3);
        if (c0608i4 == null) {
            c0608i4 = new C0608i4();
        }
        m215152b4(view, c0608i4);
        m215149b1(view, c0745k42.m213448a0());
        m215143a5(view).add(c0745k42);
        m215146a8(view, 0);
    }

    /* renamed from: b3 */
    public static void m215151b3(View view, Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            ra1.m214524a2(view, context, iArr, attributeSet, typedArray, i, 0);
        }
    }

    /* renamed from: b4 */
    public static void m215152b4(View view, C0608i4 c0608i4) {
        if (c0608i4 == null && (m215141a3(view) instanceof C0606i2)) {
            c0608i4 = new C0608i4();
        }
        view.setAccessibilityDelegate(c0608i4 == null ? null : c0608i4.f56793a1);
    }

    /* renamed from: b5 */
    public static void m215153b5(View view, CharSequence charSequence) {
        new ca1(R$id.tag_accessibility_pane_title, CharSequence.class, 8, 28, 1).m215363a3(view, charSequence);
        da1 da1Var = f61059a5;
        if (charSequence == null) {
            da1Var.f55604a0.remove(view);
            view.removeOnAttachStateChangeListener(da1Var);
            fa1.m212777b4(view.getViewTreeObserver(), da1Var);
        } else {
            da1Var.f55604a0.put(view, Boolean.valueOf(view.isShown() && view.getWindowVisibility() == 0));
            view.addOnAttachStateChangeListener(da1Var);
            if (ia1.m213141a1(view)) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(da1Var);
            }
        }
    }
}
