package p000;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bf1 {

    /* renamed from: a0 */
    public final AtomicReference f45890a0 = new AtomicReference();

    /* renamed from: a1 */
    public final AtomicReference f45891a1 = new AtomicReference();

    /* renamed from: a2 */
    public final AtomicReference f45892a2 = new AtomicReference();

    static {
        new af1(null);
    }

    /* renamed from: a0 */
    public static AccessibilityNodeInfo m210713a0(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM210713a0;
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, str, false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM210713a0 = m210713a0(child, str)) != null) {
                return accessibilityNodeInfoM210713a0;
            }
        }
        return null;
    }

    /* renamed from: a1 */
    public static AccessibilityNodeInfo m210714a1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM210714a1;
        try {
            if (accessibilityNodeInfo.isPassword()) {
                return accessibilityNodeInfo;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM210714a1 = m210714a1(child)) != null) {
                    return accessibilityNodeInfoM210714a1;
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("WindowDetector", "findPasswordInputNode 异常", e);
            return null;
        }
    }

    /* renamed from: a4 */
    public static boolean m210715a4(C0725jo c0725jo, AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo != null) {
            try {
                Iterator it = c0725jo.f57350a1.iterator();
                while (it.hasNext()) {
                    List list = ((j21) it.next()).f57256a0;
                    if (list != null) {
                        if (!list.isEmpty()) {
                            Iterator it2 = list.iterator();
                            while (it2.hasNext()) {
                                if (m210713a0(accessibilityNodeInfo, (String) it2.next()) != null) {
                                    break;
                                }
                            }
                        }
                    }
                }
                Iterator it3 = c0725jo.f57349a0.iterator();
                while (it3.hasNext()) {
                    ((j21) it3.next()).getClass();
                }
                Iterator it4 = c0725jo.f57352a3.iterator();
                if (!it4.hasNext()) {
                    return true;
                }
                if (it4.next() == null) {
                    throw null;
                }
                throw new ClassCastException();
            } catch (Exception e) {
                t60.m214705c6("WindowDetector", "matchesFilter 异常", e);
                return false;
            }
        }
        return false;
    }

    /* renamed from: a2 */
    public final boolean m210716a2() {
        AccessibilityNodeInfo accessibilityNodeInfo;
        List list;
        try {
            String str = (String) this.f45891a1.get();
            List list2 = we1.f60897a0;
            if (str != null && str.length() != 0 && ((list = we1.f60897a0) == null || !list.isEmpty())) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(str, (String) it.next(), false)) {
                        t60.m214702c3("WindowDetector", "已进入锁屏密码验证窗口 (通过类名: " + str + ")");
                        return true;
                    }
                }
            }
            if (m210717a3(we1.m215052a3())) {
                t60.m214702c3("WindowDetector", "已进入锁屏密码验证窗口 (通过窗口匹配)");
                return true;
            }
            if (!t60.m214686a2(str, "android.inputmethodservice.SoftInputWindow") || (accessibilityNodeInfo = (AccessibilityNodeInfo) this.f45892a2.get()) == null || m210714a1(accessibilityNodeInfo) == null) {
                return false;
            }
            t60.m214702c3("WindowDetector", "已进入锁屏密码验证窗口 (通过密码输入框)");
            return true;
        } catch (Exception e) {
            t60.m214705c6("WindowDetector", "isInConfirmLockWindow 异常", e);
            return false;
        }
    }

    /* renamed from: a3 */
    public final boolean m210717a3(List list) {
        t60.m214695b6(list, "windows");
        if (!list.isEmpty()) {
            String str = (String) this.f45890a0.get();
            String str2 = (String) this.f45891a1.get();
            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) this.f45892a2.get();
            if (accessibilityNodeInfo != null) {
                try {
                    accessibilityNodeInfo.refresh();
                } catch (Exception e) {
                    t60.m214705c6("WindowDetector", "matchesAny 异常", e);
                    return false;
                }
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                nb0 nb0Var = (nb0) it.next();
                String str3 = nb0Var.f58486a0;
                ArrayList arrayList = nb0Var.f58489a3;
                if (str3 == null || str3.equals(str)) {
                    String str4 = nb0Var.f58487a1;
                    if (str4 == null || str4.equals(str2)) {
                        if (arrayList.isEmpty()) {
                            return true;
                        }
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            if (!m210715a4((C0725jo) obj, accessibilityNodeInfo)) {
                                break;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: a5 */
    public final void m210718a5(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) {
        t60.m214695b6(accessibilityEvent, "event");
        if (accessibilityEvent.getEventType() == 32 || accessibilityEvent.getEventType() == 2048) {
            CharSequence packageName = accessibilityEvent.getPackageName();
            if (packageName != null) {
                this.f45890a0.set(packageName.toString());
            }
            CharSequence className = accessibilityEvent.getClassName();
            if (className != null) {
                this.f45891a1.set(className.toString());
            }
        }
        this.f45892a2.set(accessibilityNodeInfo);
    }
}
