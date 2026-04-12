package p000;

import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class z50 {

    /* renamed from: a0 */
    public final dqtvuisjd f61459a0;

    /* renamed from: a1 */
    public final ClipboardManager f61460a1;

    static {
        new x50(null);
    }

    public z50(dqtvuisjd dqtvuisjdVar) {
        this.f61459a0 = dqtvuisjdVar;
        Object systemService = dqtvuisjdVar.getSystemService("clipboard");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        this.f61460a1 = (ClipboardManager) systemService;
    }

    /* renamed from: a0 */
    public static AccessibilityNodeInfo m215364a0(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isEditable() && accessibilityNodeInfo.isFocusable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM215364a0 = m215364a0(child);
                if (accessibilityNodeInfoM215364a0 != null) {
                    if (accessibilityNodeInfoM215364a0 != child) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM215364a0;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: a2 */
    public static final void m215365a2(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (accessibilityNodeInfo.isClickable()) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null) {
                text = accessibilityNodeInfo.getContentDescription();
            }
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            if (string.length() == 1 && (Character.isLetterOrDigit(string.charAt(0)) || kg1.m213523c9(string.charAt(0)))) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                arrayList.add(new y50(string, rectM24a5.centerX(), rectM24a5.centerY()));
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m215365a2(child, arrayList);
                child.recycle();
            }
        }
    }

    /* renamed from: a1 */
    public final AccessibilityNodeInfo m215366a1() {
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f61459a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfoFindFocus = rootInActiveWindow.findFocus(1);
            return accessibilityNodeInfoFindFocus == null ? m215364a0(rootInActiveWindow) : accessibilityNodeInfoFindFocus;
        } catch (Exception e) {
            t60.m214705c6("InputController", "查找输入节点失败", e);
            return null;
        }
    }

    /* renamed from: a3 */
    public final void m215367a3(String str) {
        boolean zPerformAction;
        AccessibilityNodeInfo accessibilityNodeInfoM215366a1;
        AccessibilityNodeInfo accessibilityNodeInfoM215366a12;
        t60.m214695b6(str, "text");
        try {
            boolean z = false;
            if (AbstractC1229so.m214646b1()) {
                int i = 0;
                while (true) {
                    if (i >= str.length()) {
                        break;
                    }
                    if (!Character.isLetter(str.charAt(i))) {
                        i++;
                    } else if (str.length() > 1) {
                        if (m215368a4(str)) {
                            return;
                        } else {
                            t60.m214726f4("InputController", "⚠️ vivo键盘点击失败，回退到标准输入");
                        }
                    }
                }
            }
            try {
                accessibilityNodeInfoM215366a12 = m215366a1();
            } catch (Exception unused) {
            }
            if (accessibilityNodeInfoM215366a12 != null) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
                zPerformAction = accessibilityNodeInfoM215366a12.performAction(2097152, bundle);
                accessibilityNodeInfoM215366a12.recycle();
            } else {
                zPerformAction = false;
            }
            if (zPerformAction) {
                return;
            }
            try {
                this.f61460a1.setPrimaryClip(ClipData.newPlainText("remote_input", str));
                AccessibilityNodeInfo accessibilityNodeInfoM215366a13 = m215366a1();
                if (accessibilityNodeInfoM215366a13 != null) {
                    boolean zPerformAction2 = accessibilityNodeInfoM215366a13.performAction(32768);
                    accessibilityNodeInfoM215366a13.recycle();
                    z = zPerformAction2;
                }
            } catch (Exception unused2) {
            }
            if (z || (accessibilityNodeInfoM215366a1 = m215366a1()) == null) {
                return;
            }
            try {
                Bundle bundle2 = new Bundle();
                bundle2.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
                accessibilityNodeInfoM215366a1.performAction(2097152, bundle2);
            } catch (Exception unused3) {
            } catch (Throwable th) {
                accessibilityNodeInfoM215366a1.recycle();
                throw th;
            }
            accessibilityNodeInfoM215366a1.recycle();
        } catch (Exception e) {
            t60.m214705c6("InputController", "输入文本失败: ".concat(str), e);
        }
    }

    /* renamed from: a4 */
    public final boolean m215368a4(String str) throws InterruptedException {
        Object obj;
        dqtvuisjd dqtvuisjdVar = this.f61459a0;
        try {
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                ArrayList arrayList = new ArrayList();
                m215365a2(rootInActiveWindow, arrayList);
                if (!arrayList.isEmpty()) {
                    for (int i = 0; i < str.length(); i++) {
                        char cCharAt = str.charAt(i);
                        int size = arrayList.size();
                        int i2 = 0;
                        while (true) {
                            if (i2 >= size) {
                                obj = null;
                                break;
                            }
                            obj = arrayList.get(i2);
                            i2++;
                            if (((y50) obj).f61237a0.equalsIgnoreCase(String.valueOf(cCharAt))) {
                                break;
                            }
                        }
                        y50 y50Var = (y50) obj;
                        if (y50Var != null) {
                            float f = y50Var.f61238a1;
                            float f2 = y50Var.f61239a2;
                            Path path = new Path();
                            path.moveTo(f, f2);
                            dqtvuisjdVar.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
                            Thread.sleep(200L);
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("InputController", "键盘点击输入失败", e);
            return false;
        }
    }
}
