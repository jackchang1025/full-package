package io.socket.engineio.client.transports;

import io.socket.engineio.client.Transport;
import io.socket.engineio.parser.Packet;
import io.socket.engineio.parser.Parser;
import io.socket.parseqs.ParseQS;
import io.socket.thread.EventThread;
import io.socket.yeast.Yeast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;
import okio.ByteString;
import p000.AbstractC0003a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class WebSocket extends Transport {
    public static final String NAME = "websocket";
    private static final Logger logger = Logger.getLogger(PollingXHR.class.getName());

    /* renamed from: ws */
    private okhttp3.WebSocket f57213ws;

    public WebSocket(Transport.Options options) {
        super(options);
        this.name = NAME;
    }

    @Override // io.socket.engineio.client.Transport
    public void doClose() {
        okhttp3.WebSocket webSocket = this.f57213ws;
        if (webSocket != null) {
            webSocket.close(1000, "");
            this.f57213ws = null;
        }
    }

    @Override // io.socket.engineio.client.Transport
    public void doOpen() {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Map<String, List<String>> map = this.extraHeaders;
        if (map != null) {
            treeMap.putAll(map);
        }
        emit("requestHeaders", treeMap);
        Request.Builder builderUrl = new Request.Builder().url(uri());
        for (Map.Entry entry : treeMap.entrySet()) {
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                builderUrl.addHeader((String) entry.getKey(), (String) it.next());
            }
        }
        this.f57213ws = this.webSocketFactory.newWebSocket(builderUrl.build(), new WebSocketListener() { // from class: io.socket.engineio.client.transports.WebSocket.1
            @Override // okhttp3.WebSocketListener
            public void onClosed(okhttp3.WebSocket webSocket, int i, String str) {
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onClose();
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(okhttp3.WebSocket webSocket, final Throwable th, Response response) {
                if (th instanceof Exception) {
                    EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.5
                        @Override // java.lang.Runnable
                        public void run() {
                            this.onError("websocket error", (Exception) th);
                        }
                    });
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final String str) {
                if (str == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(str);
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                final Map<String, List<String>> multimap = response.headers().toMultimap();
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        this.emit("responseHeaders", multimap);
                        this.onOpen();
                    }
                });
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(okhttp3.WebSocket webSocket, final ByteString byteString) {
                if (byteString == null) {
                    return;
                }
                EventThread.exec(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        this.onData(byteString.toByteArray());
                    }
                });
            }
        });
    }

    public String uri() {
        String str;
        Map map = this.query;
        if (map == null) {
            map = new HashMap();
        }
        String str2 = this.secure ? "wss" : "ws";
        if (this.port <= 0 || ((!"wss".equals(str2) || this.port == 443) && (!"ws".equals(str2) || this.port == 80))) {
            str = "";
        } else {
            str = ":" + this.port;
        }
        if (this.timestampRequests) {
            map.put(this.timestampParam, Yeast.yeast());
        }
        String strEncode = ParseQS.encode(map);
        if (strEncode.length() > 0) {
            strEncode = "?".concat(strEncode);
        }
        boolean zContains = this.hostname.contains(":");
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0(str2, "://");
        sbM39c0.append(zContains ? AbstractC0003a2.m35b6(new StringBuilder("["), this.hostname, "]") : this.hostname);
        sbM39c0.append(str);
        return AbstractC0003a2.m35b6(sbM39c0, this.path, strEncode);
    }

    @Override // io.socket.engineio.client.Transport
    public void write(Packet[] packetArr) {
        this.writable = false;
        final Runnable runnable = new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2
            @Override // java.lang.Runnable
            public void run() {
                EventThread.nextTick(new Runnable() { // from class: io.socket.engineio.client.transports.WebSocket.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        WebSocket webSocket = this;
                        webSocket.writable = true;
                        webSocket.emit("drain", new Object[0]);
                    }
                });
            }
        };
        final int[] iArr = {packetArr.length};
        for (Packet packet : packetArr) {
            Transport.ReadyState readyState = this.readyState;
            if (readyState != Transport.ReadyState.OPENING && readyState != Transport.ReadyState.OPEN) {
                return;
            }
            Parser.encodePacket(packet, new Parser.EncodeCallback() { // from class: io.socket.engineio.client.transports.WebSocket.3
                @Override // io.socket.engineio.parser.Parser.EncodeCallback
                public void call(Object obj) {
                    try {
                        if (obj instanceof String) {
                            this.f57213ws.send((String) obj);
                        } else if (obj instanceof byte[]) {
                            this.f57213ws.send(ByteString.m214223of((byte[]) obj));
                        }
                    } catch (IllegalStateException unused) {
                        WebSocket.logger.fine("websocket closed before we could write");
                    }
                    int[] iArr2 = iArr;
                    int i = iArr2[0] - 1;
                    iArr2[0] = i;
                    if (i == 0) {
                        runnable.run();
                    }
                }
            });
        }
    }
}
