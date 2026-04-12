package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0268a1;
import com.storm.safe.rock.util.AbstractC0385a0;
import java.util.List;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.h10;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class NetworkManager$initialize$3 extends Lambda implements h10 {

    /* renamed from: a0 */
    public final /* synthetic */ C0323a8 f52834a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$initialize$3$1", m214403f = "NetworkManager.kt", m214404l = {252}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.modules.NetworkManager$initialize$3$1 */
    final class C03061 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52835a1;

        /* renamed from: a2 */
        public final /* synthetic */ C0323a8 f52836a2;

        /* renamed from: a3 */
        public final /* synthetic */ List f52837a3;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03061(C0323a8 c0323a8, List list, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52836a2 = c0323a8;
            this.f52837a3 = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C03061(this.f52836a2, this.f52837a3, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03061) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52835a1;
            try {
                if (i == 0) {
                    kg1.m213544f4(obj);
                    C0268a1 c0268a1 = this.f52836a2.f53101a1;
                    if (c0268a1 != null) {
                        List list = this.f52837a3;
                        this.f52835a1 = 1;
                        if (c0268a1.m211376a6(list, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                    ((Result) obj).getClass();
                }
            } catch (Exception e) {
                t60.m214705c6("NetworkManager", "上传日志失败", e);
            }
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$initialize$3(C0323a8 c0323a8) {
        super(1);
        this.f52834a0 = c0323a8;
    }

    @Override // p000.h10
    public final Object invoke(Object obj) {
        List list = (List) obj;
        t60.m214695b6(list, "logs");
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new C03061(this.f52834a0, list, null), 3);
        return C1351vv.f60710b1;
    }
}
