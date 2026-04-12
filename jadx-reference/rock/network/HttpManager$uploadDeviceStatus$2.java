package com.storm.safe.rock.network;

import kotlin.Result;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager$uploadDeviceStatus$2", m214403f = "HttpManager.kt", m214404l = {268}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class HttpManager$uploadDeviceStatus$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52216a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0268a1 f52217a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52218a3;

    /* renamed from: a4 */
    public final /* synthetic */ JSONObject f52219a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$uploadDeviceStatus$2(C0268a1 c0268a1, String str, JSONObject jSONObject, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52217a2 = c0268a1;
        this.f52218a3 = str;
        this.f52219a4 = jSONObject;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new HttpManager$uploadDeviceStatus$2(this.f52217a2, this.f52218a3, this.f52219a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((HttpManager$uploadDeviceStatus$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objM213507a7;
        C0268a1 c0268a1 = this.f52217a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52216a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                JSONObject jSONObject = new JSONObject();
                String str = this.f52218a3;
                JSONObject jSONObject2 = this.f52219a4;
                jSONObject.put("deviceId", c0268a1.f52279a2);
                jSONObject.put("statusType", str);
                jSONObject.put("data", jSONObject2);
                this.f52216a1 = 1;
                objM213507a7 = c0268a1.m211371a1("/api/sync/status", jSONObject, true, this);
                if (objM213507a7 == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                objM213507a7 = ((Result) obj).f57559a0;
            }
        } catch (Exception e) {
            t60.m214705c6("HttpManager", "上传设备状态失败", e);
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(e);
        }
        return new Result(objM213507a7);
    }
}
