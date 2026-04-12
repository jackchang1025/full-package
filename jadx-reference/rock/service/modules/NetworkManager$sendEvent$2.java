package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0267a0;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendEvent$2", m214403f = "NetworkManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendEvent$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0323a8 f52847a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f52848a2;

    /* renamed from: a3 */
    public final /* synthetic */ JSONObject f52849a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendEvent$2(C0323a8 c0323a8, String str, JSONObject jSONObject, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52847a1 = c0323a8;
        this.f52848a2 = str;
        this.f52849a3 = jSONObject;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendEvent$2(this.f52847a1, this.f52848a2, this.f52849a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        NetworkManager$sendEvent$2 networkManager$sendEvent$2 = (NetworkManager$sendEvent$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        networkManager$sendEvent$2.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        JSONObject jSONObject;
        C0267a0 c0267a0;
        C0323a8 c0323a8 = this.f52847a1;
        String str = this.f52848a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            jSONObject = new JSONObject();
            JSONObject jSONObject2 = this.f52849a3;
            jSONObject.put("type", str);
            jSONObject.put("sessionId", c0323a8.f53107a7);
            jSONObject.put("data", jSONObject2);
            c0267a0 = c0323a8.f53102a2;
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ 发送事件失败: ".concat(str), e);
        }
        if (c0267a0 == null) {
            t60.m214724f2("dataSyncClient");
            throw null;
        }
        String string = jSONObject.toString();
        t60.m214694b5(string, "eventData.toString()");
        c0267a0.m211367a8(string);
        t60.m214714d6("NetworkManager", "📤 事件已发送: ".concat(str));
        return C1351vv.f60710b1;
    }
}
