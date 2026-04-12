package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0268a1;
import java.util.List;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendOperationLog$1", m214403f = "NetworkManager.kt", m214404l = {1315}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendOperationLog$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52853a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f52854a2;

    /* renamed from: a3 */
    public final /* synthetic */ JSONObject f52855a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendOperationLog$1(C0323a8 c0323a8, JSONObject jSONObject, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52854a2 = c0323a8;
        this.f52855a3 = jSONObject;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendOperationLog$1(this.f52854a2, this.f52855a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkManager$sendOperationLog$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52853a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                C0268a1 c0268a1 = this.f52854a2.f53101a1;
                if (c0268a1 != null) {
                    List listM214451e7 = AbstractC1117qo.m214451e7(this.f52855a3);
                    this.f52853a1 = 1;
                    if (c0268a1.m211376a6(listM214451e7, this) == coroutineSingletons) {
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
            t60.m214705c6("NetworkManager", "发送操作日志失败", e);
        }
        return C1351vv.f60710b1;
    }
}
