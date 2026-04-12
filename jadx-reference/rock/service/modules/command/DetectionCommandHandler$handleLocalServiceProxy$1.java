package com.storm.safe.rock.service.modules.command;

import io.socket.engineio.client.transports.PollingXHR;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import org.json.JSONObject;
import p000.AbstractC0577hd;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.DetectionCommandHandler$handleLocalServiceProxy$1", m214403f = "DetectionCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class DetectionCommandHandler$handleLocalServiceProxy$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f53438a1;

    /* renamed from: a2 */
    public final /* synthetic */ JSONObject f53439a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53440a3;

    /* renamed from: a4 */
    public final /* synthetic */ uz0 f53441a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DetectionCommandHandler$handleLocalServiceProxy$1(String str, JSONObject jSONObject, String str2, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53438a1 = str;
        this.f53439a2 = jSONObject;
        this.f53440a3 = str2;
        this.f53441a4 = uz0Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new DetectionCommandHandler$handleLocalServiceProxy$1(this.f53438a1, this.f53439a2, this.f53440a3, this.f53441a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        DetectionCommandHandler$handleLocalServiceProxy$1 detectionCommandHandler$handleLocalServiceProxy$1 = (DetectionCommandHandler$handleLocalServiceProxy$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        detectionCommandHandler$handleLocalServiceProxy$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:92|3|4|(1:6)(1:9)|(1:11)|12|(3:14|(1:16)|17)|18|19|(1:25)(1:24)|26|(3:30|(1:32)|(7:34|(4:37|(1:106)(3:100|47|107)|101|35)|95|48|84|49|50))|57|58|(2:90|59)|65|88|66|(2:69|67)|94|75) */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x016f, code lost:
    
        if (r0 == 200) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0171, code lost:
    
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0172, code lost:
    
        r6 = kotlin.collections.AbstractC0770a1.m213614f9(new kotlin.Pair(io.socket.engineio.client.transports.PollingXHR.Request.EVENT_SUCCESS, java.lang.Boolean.valueOf(r12)), new kotlin.Pair("data", r4));
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0155 A[Catch: Exception -> 0x016d, LOOP:0: B:67:0x014f->B:69:0x0155, LOOP_END, TRY_LEAVE, TryCatch #3 {Exception -> 0x016d, blocks: (B:66:0x0141, B:67:0x014f, B:69:0x0155), top: B:88:0x0141, outer: #5 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String strM210590e1;
        Iterator<String> itKeys;
        uz0 uz0Var = this.f53441a4;
        String str = this.f53440a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            String strConcat = "http://127.0.0.1:7912".concat(this.f53438a1);
            JSONObject jSONObject = this.f53439a2;
            String strM210590e12 = "";
            String strOptString = jSONObject != null ? jSONObject.optString("query", "") : null;
            if (strOptString == null) {
                strOptString = "";
            }
            boolean z = false;
            if (strOptString.length() > 0) {
                strConcat = strConcat + (AbstractC0779a1.m213652a5(strConcat, "?", false) ? "&" : "?") + strOptString;
            }
            URLConnection uRLConnectionOpenConnection = new URL(strConcat).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod((str.equals("POST") || str.equals("PUT")) ? str : "GET");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            if (str.equals("POST") || str.equals("PUT")) {
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                JSONObject jSONObjectOptJSONObject = jSONObject != null ? jSONObject.optJSONObject("params") : null;
                if (jSONObjectOptJSONObject != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    Iterator<String> itKeys2 = jSONObjectOptJSONObject.keys();
                    while (itKeys2.hasNext()) {
                        String next = itKeys2.next();
                        if (!t60.m214686a2(next, "deviceId") && !t60.m214686a2(next, "path") && !t60.m214686a2(next, "method") && !t60.m214686a2(next, "query") && !t60.m214686a2(next, "requestId")) {
                            jSONObject2.put(next, jSONObjectOptJSONObject.get(next));
                        }
                    }
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    try {
                        String string = jSONObject2.toString();
                        t60.m214694b5(string, "cleaned.toString()");
                        byte[] bytes = string.getBytes(AbstractC0577hd.f56650a0);
                        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                        outputStream.write(bytes);
                        outputStream.close();
                    } finally {
                    }
                }
            }
            int responseCode = httpURLConnection.getResponseCode();
            try {
                try {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    t60.m214694b5(inputStream, "conn.inputStream");
                    strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                } catch (Exception unused) {
                    strM210590e1 = strM210590e12;
                    httpURLConnection.disconnect();
                    JSONObject jSONObject3 = new JSONObject(strM210590e1);
                    Map mapM213614f9 = new LinkedHashMap();
                    itKeys = jSONObject3.keys();
                    while (itKeys.hasNext()) {
                    }
                    uz0Var.f60536a0.m211515l2("proxy_result", mapM213614f9);
                    return C1351vv.f60710b1;
                }
            } catch (Exception unused2) {
                InputStream errorStream = httpURLConnection.getErrorStream();
                if (errorStream != null) {
                    strM210590e12 = b81.m210590e1(new BufferedReader(new InputStreamReader(errorStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                }
                strM210590e1 = strM210590e12;
                httpURLConnection.disconnect();
                JSONObject jSONObject32 = new JSONObject(strM210590e1);
                Map mapM213614f92 = new LinkedHashMap();
                itKeys = jSONObject32.keys();
                while (itKeys.hasNext()) {
                }
                uz0Var.f60536a0.m211515l2("proxy_result", mapM213614f92);
                return C1351vv.f60710b1;
            }
            httpURLConnection.disconnect();
            JSONObject jSONObject322 = new JSONObject(strM210590e1);
            Map mapM213614f922 = new LinkedHashMap();
            itKeys = jSONObject322.keys();
            while (itKeys.hasNext()) {
                String next2 = itKeys.next();
                t60.m214694b5(next2, "k");
                Object obj2 = jSONObject322.get(next2);
                t60.m214694b5(obj2, "json.get(k)");
                mapM213614f922.put(next2, obj2);
            }
            uz0Var.f60536a0.m211515l2("proxy_result", mapM213614f922);
        } catch (Exception e) {
            tz0.m214807a7("LOCAL_SERVICE_PROXY 失败: ", e.getMessage(), "DetectionCmdHandler");
            Pair pair = new Pair(PollingXHR.Request.EVENT_SUCCESS, Boolean.FALSE);
            String message = e.getMessage();
            if (message == null) {
                message = "unknown";
            }
            uz0Var.f60536a0.m211515l2("proxy_result", AbstractC0770a1.m213614f9(pair, new Pair("error", message)));
        }
        return C1351vv.f60710b1;
    }
}
