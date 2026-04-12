package com.storm.safe.rock.service.modules.command;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONArray;
import p000.AbstractC0577hd;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.DetectionCommandHandler$handleSetSensitiveApps$1", m214403f = "DetectionCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class DetectionCommandHandler$handleSetSensitiveApps$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ JSONArray f53442a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DetectionCommandHandler$handleSetSensitiveApps$1(JSONArray jSONArray, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53442a1 = jSONArray;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new DetectionCommandHandler$handleSetSensitiveApps$1(this.f53442a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        DetectionCommandHandler$handleSetSensitiveApps$1 detectionCommandHandler$handleSetSensitiveApps$1 = (DetectionCommandHandler$handleSetSensitiveApps$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        detectionCommandHandler$handleSetSensitiveApps$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/setSensitiveApps").openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                String string = this.f53442a1.toString();
                t60.m214694b5(string, "appsArr.toString()");
                byte[] bytes = string.getBytes(AbstractC0577hd.f56650a0);
                t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                outputStream.write(bytes);
                outputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                httpURLConnection.disconnect();
                t60.m214714d6("DetectionCmdHandler", "📋 敏感应用转发到 local-service: HTTP " + responseCode);
            } finally {
            }
        } catch (Exception e) {
            tz0.m214807a7("📋 敏感应用转发失败: ", e.getMessage(), "DetectionCmdHandler");
        }
        return C1351vv.f60710b1;
    }
}
