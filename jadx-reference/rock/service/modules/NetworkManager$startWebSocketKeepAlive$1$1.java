package com.storm.safe.rock.service.modules;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.channels.C0786a0;
import kotlinx.coroutines.channels.ClosedReceiveChannelException;
import p000.AbstractC0494fg;
import p000.C0530gb;
import p000.C0576hc;
import p000.C0728jr;
import p000.C0794ks;
import p000.C0905n9;
import p000.C1257tf;
import p000.C1347vr;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.h10;
import p000.jz0;
import p000.kg1;
import p000.kj1;
import p000.l10;
import p000.p11;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$startWebSocketKeepAlive$1$1", m214403f = "NetworkManager.kt", m214404l = {701, 710, 716, 732, 752, 759}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$startWebSocketKeepAlive$1$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public long f52873a1;

    /* renamed from: a2 */
    public int f52874a2;

    /* renamed from: a3 */
    public int f52875a3;

    /* renamed from: a4 */
    public int f52876a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f52877a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0323a8 f52878a6;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$startWebSocketKeepAlive$1$1$3", m214403f = "NetworkManager.kt", m214404l = {733}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.modules.NetworkManager$startWebSocketKeepAlive$1$1$3 */
    final class C03073 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52879a1;

        /* renamed from: a2 */
        public final /* synthetic */ C0323a8 f52880a2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03073(C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52880a2 = c0323a8;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C03073(this.f52880a2, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03073) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x00d6  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            C0576hc c0576hc;
            C0530gb c0530gb;
            C0530gb c0530gb2;
            AtomicLongFieldUpdater atomicLongFieldUpdater;
            C0794ks c0794ks;
            h10 h10Var;
            C0576hc c0576hc2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52879a1;
            if (i == 0) {
                kg1.m213544f4(obj);
                C0794ks c0794ks2 = this.f52880a2.f53125c5;
                this.f52879a1 = 1;
                c0794ks2.getClass();
                AtomicLongFieldUpdater atomicLongFieldUpdater2 = C0786a0.f57673a2;
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0786a0.f57677a6;
                C0576hc c0576hc3 = (C0576hc) atomicReferenceFieldUpdater.get(c0794ks2);
                while (!c0794ks2.m213721b1(C0786a0.f57672a1.get(c0794ks2), true)) {
                    long andIncrement = atomicLongFieldUpdater2.getAndIncrement(c0794ks2);
                    long j = AbstractC0494fg.f56238a1;
                    long j2 = andIncrement / j;
                    int i2 = (int) (andIncrement % j);
                    if (c0576hc3.f57401a2 != j2) {
                        C0576hc c0576hcM213718a7 = c0794ks2.m213718a7(j2, c0576hc3);
                        if (c0576hcM213718a7 == null) {
                            continue;
                        } else {
                            c0576hc3 = c0576hcM213718a7;
                        }
                    }
                    Object objM213727b8 = c0794ks2.m213727b8(c0576hc3, i2, andIncrement, null);
                    C1347vr c1347vr = AbstractC0494fg.f56249b2;
                    if (objM213727b8 == c1347vr) {
                        throw new IllegalStateException("unexpected");
                    }
                    C1347vr c1347vr2 = AbstractC0494fg.f56251b4;
                    if (objM213727b8 != c1347vr2) {
                        if (objM213727b8 == AbstractC0494fg.f56250b3) {
                            InterfaceC0876mv interfaceC0876mvM213575c2 = kj1.m213575c2(this);
                            if (interfaceC0876mvM213575c2 instanceof C1257tf) {
                                C1257tf c1257tf = (C1257tf) interfaceC0876mvM213575c2;
                                C1347vr c1347vr3 = b81.f45734a5;
                                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = C1257tf.f60207a7;
                                C0794ks c0794ks3 = c0794ks2;
                                loop2: while (true) {
                                    Object obj2 = atomicReferenceFieldUpdater2.get(c1257tf);
                                    if (obj2 == null) {
                                        atomicReferenceFieldUpdater2.set(c1257tf, c1347vr3);
                                        c0576hc = c0576hc3;
                                        c0530gb = null;
                                        break;
                                    }
                                    c0576hc = c0576hc3;
                                    if (obj2 instanceof C0530gb) {
                                        while (!atomicReferenceFieldUpdater2.compareAndSet(c1257tf, obj2, c1347vr3)) {
                                            atomicLongFieldUpdater = atomicLongFieldUpdater2;
                                            c0794ks = c0794ks3;
                                            if (atomicReferenceFieldUpdater2.get(c1257tf) != obj2) {
                                                break;
                                            }
                                            c0794ks3 = c0794ks;
                                            atomicLongFieldUpdater2 = atomicLongFieldUpdater;
                                        }
                                        c0530gb = (C0530gb) obj2;
                                        break loop2;
                                    }
                                    atomicLongFieldUpdater = atomicLongFieldUpdater2;
                                    c0794ks = c0794ks3;
                                    if (obj2 != c1347vr3 && !(obj2 instanceof Throwable)) {
                                        throw new IllegalStateException(("Inconsistent state " + obj2).toString());
                                    }
                                    c0794ks3 = c0794ks;
                                    c0576hc3 = c0576hc;
                                    atomicLongFieldUpdater2 = atomicLongFieldUpdater;
                                }
                                if (c0530gb != null) {
                                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = C0530gb.f56431a6;
                                    Object obj3 = atomicReferenceFieldUpdater3.get(c0530gb);
                                    if (!(obj3 instanceof C0728jr) || ((C0728jr) obj3).f57361a3 == null) {
                                        C0530gb.f56430a5.set(c0530gb, 536870911);
                                        atomicReferenceFieldUpdater3.set(c0530gb, C0905n9.f58465a0);
                                        c0530gb2 = c0530gb;
                                    } else {
                                        c0530gb.m212923b3();
                                        c0530gb2 = null;
                                    }
                                    if (c0530gb2 == null) {
                                        c0530gb2 = new C0530gb(2, interfaceC0876mvM213575c2);
                                    }
                                    c0794ks2 = c0794ks3;
                                    c0576hc3 = c0576hc;
                                }
                            } else {
                                c0530gb2 = new C0530gb(1, interfaceC0876mvM213575c2);
                            }
                            try {
                                Object objM213727b82 = c0794ks2.m213727b8(c0576hc3, i2, andIncrement, c0530gb2);
                                if (objM213727b82 == c1347vr) {
                                    c0530gb2.mo212795a0(c0576hc3, i2);
                                } else {
                                    if (objM213727b82 == c1347vr2) {
                                        if (andIncrement < c0794ks2.m213720a9()) {
                                            c0576hc3.m213553a0();
                                        }
                                        C0576hc c0576hc4 = (C0576hc) atomicReferenceFieldUpdater.get(c0794ks2);
                                        while (true) {
                                            if (c0794ks2.m213721b1(C0786a0.f57672a1.get(c0794ks2), true)) {
                                                int i3 = Result.f57558a1;
                                                Throwable closedReceiveChannelException = (Throwable) C0786a0.f57679a8.get(c0794ks2);
                                                if (closedReceiveChannelException == null) {
                                                    closedReceiveChannelException = new ClosedReceiveChannelException("Channel was closed");
                                                }
                                                c0530gb2.resumeWith(kg1.m213507a7(closedReceiveChannelException));
                                            } else {
                                                long andIncrement2 = atomicLongFieldUpdater2.getAndIncrement(c0794ks2);
                                                long j3 = AbstractC0494fg.f56238a1;
                                                long j4 = andIncrement2 / j3;
                                                int i4 = (int) (andIncrement2 % j3);
                                                C0794ks c0794ks4 = c0794ks2;
                                                if (c0576hc4.f57401a2 != j4) {
                                                    c0794ks2 = c0794ks4;
                                                    C0576hc c0576hcM213718a72 = c0794ks2.m213718a7(j4, c0576hc4);
                                                    if (c0576hcM213718a72 != null) {
                                                        c0576hc2 = c0576hcM213718a72;
                                                    }
                                                } else {
                                                    c0576hc2 = c0576hc4;
                                                    c0794ks2 = c0794ks4;
                                                }
                                                objM213727b82 = c0794ks2.m213727b8(c0576hc2, i4, andIncrement2, c0530gb2);
                                                jz0 jz0Var = c0576hc2;
                                                if (objM213727b82 == AbstractC0494fg.f56249b2) {
                                                    c0530gb2.mo212795a0(jz0Var, i4);
                                                    break;
                                                }
                                                if (objM213727b82 == AbstractC0494fg.f56251b4) {
                                                    if (andIncrement2 < c0794ks2.m213720a9()) {
                                                        jz0Var.m213553a0();
                                                    }
                                                    c0576hc4 = jz0Var;
                                                } else {
                                                    if (objM213727b82 == AbstractC0494fg.f56250b3) {
                                                        throw new IllegalStateException("unexpected");
                                                    }
                                                    jz0Var.m213553a0();
                                                    h10Var = null;
                                                }
                                            }
                                        }
                                    } else {
                                        c0576hc3.m213553a0();
                                        h10Var = null;
                                    }
                                    c0530gb2.m212933c4(objM213727b82, h10Var);
                                }
                                objM213727b8 = c0530gb2.m212925b5();
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.f57606a0;
                            } catch (Throwable th) {
                                c0530gb2.m212932c3();
                                throw th;
                            }
                        } else {
                            c0576hc3.m213553a0();
                        }
                        if (objM213727b8 == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else if (andIncrement < c0794ks2.m213720a9()) {
                        c0576hc3.m213553a0();
                    }
                }
                Throwable closedReceiveChannelException2 = (Throwable) C0786a0.f57679a8.get(c0794ks2);
                if (closedReceiveChannelException2 == null) {
                    closedReceiveChannelException2 = new ClosedReceiveChannelException("Channel was closed");
                }
                int i5 = p11.f59137a0;
                throw closedReceiveChannelException2;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$startWebSocketKeepAlive$1$1(C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52878a6 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        NetworkManager$startWebSocketKeepAlive$1$1 networkManager$startWebSocketKeepAlive$1$1 = new NetworkManager$startWebSocketKeepAlive$1$1(this.f52878a6, interfaceC0876mv);
        networkManager$startWebSocketKeepAlive$1$1.f52877a5 = obj;
        return networkManager$startWebSocketKeepAlive$1$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkManager$startWebSocketKeepAlive$1$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:28:0x0089
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Path cross not found for [B:117:?, B:37:0x00b1], limit reached: 120 */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007e A[Catch: all -> 0x0037, CancellationException -> 0x01f3, TRY_ENTER, TryCatch #1 {all -> 0x0037, blocks: (B:24:0x007e, B:29:0x008b, B:31:0x0093, B:34:0x00a7, B:35:0x00a9, B:37:0x00b1, B:39:0x00b7, B:41:0x00bd, B:43:0x00dc, B:44:0x00e4, B:45:0x00e7, B:46:0x00e8, B:49:0x00fa, B:51:0x0100, B:54:0x0113, B:56:0x011c, B:58:0x0124, B:59:0x0128, B:61:0x012e, B:63:0x0136, B:8:0x0031, B:13:0x0044, B:16:0x0053, B:19:0x0061), top: B:106:0x000e }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:100:0x01e3 -> B:101:0x01e6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x00af -> B:22:0x0078). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x00f6 -> B:22:0x0078). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.NetworkManager$startWebSocketKeepAlive$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
