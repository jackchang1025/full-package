package io.socket.client;

import io.socket.client.C0639On;
import io.socket.client.Manager;
import io.socket.emitter.Emitter;
import io.socket.parser.Packet;
import io.socket.thread.EventThread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class Socket extends Emitter {
    public static final String EVENT_CONNECT = "connect";
    public static final String EVENT_CONNECT_ERROR = "connect_error";
    public static final String EVENT_DISCONNECT = "disconnect";
    static final String EVENT_MESSAGE = "message";
    private Map<String, String> auth;
    private volatile boolean connected;

    /* renamed from: id */
    String f57208id;
    private int ids;

    /* renamed from: io */
    private Manager f57209io;
    private String nsp;
    private Queue<C0639On.Handle> subs;
    private static final Logger logger = Logger.getLogger(Socket.class.getName());
    protected static Map<String, Integer> RESERVED_EVENTS = new HashMap<String, Integer>() { // from class: io.socket.client.Socket.1
        {
            put(Socket.EVENT_CONNECT, 1);
            put(Socket.EVENT_CONNECT_ERROR, 1);
            put(Socket.EVENT_DISCONNECT, 1);
            put("disconnecting", 1);
            put("newListener", 1);
            put("removeListener", 1);
        }
    };
    private Map<Integer, Ack> acks = new HashMap();
    private final Queue<List<Object>> receiveBuffer = new LinkedList();
    private final Queue<Packet<JSONArray>> sendBuffer = new LinkedList();
    private ConcurrentLinkedQueue<Emitter.Listener> onAnyIncomingListeners = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Emitter.Listener> onAnyOutgoingListeners = new ConcurrentLinkedQueue<>();

    public Socket(Manager manager, String str, Manager.Options options) {
        this.f57209io = manager;
        this.nsp = str;
        if (options != null) {
            this.auth = options.auth;
        }
    }

    public static /* synthetic */ int access$708(Socket socket) {
        int i = socket.ids;
        socket.ids = i + 1;
        return i;
    }

    private Ack ack(final int i) {
        final boolean[] zArr = {false};
        return new Ack() { // from class: io.socket.client.Socket.7
            @Override // io.socket.client.Ack
            public void call(final Object... objArr) {
                EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean[] zArr2 = zArr;
                        if (zArr2[0]) {
                            return;
                        }
                        zArr2[0] = true;
                        if (Socket.logger.isLoggable(Level.FINE)) {
                            Logger logger2 = Socket.logger;
                            Object[] objArr2 = objArr;
                            if (objArr2.length == 0) {
                                objArr2 = null;
                            }
                            logger2.fine(String.format("sending ack %s", objArr2));
                        }
                        JSONArray jSONArray = new JSONArray();
                        for (Object obj : objArr) {
                            jSONArray.put(obj);
                        }
                        Packet packet = new Packet(3, jSONArray);
                        C06467 c06467 = C06467.this;
                        packet.f57215id = i;
                        this.packet(packet);
                    }
                });
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroy() {
        Queue<C0639On.Handle> queue = this.subs;
        if (queue != null) {
            Iterator<C0639On.Handle> it = queue.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.subs = null;
        }
        for (Ack ack : this.acks.values()) {
            if (ack instanceof AckWithTimeout) {
                ((AckWithTimeout) ack).cancelTimer();
            }
        }
        this.f57209io.destroy();
    }

    private void emitBuffered() {
        while (true) {
            List<Object> listPoll = this.receiveBuffer.poll();
            if (listPoll == null) {
                break;
            } else {
                super.emit((String) listPoll.get(0), listPoll.toArray());
            }
        }
        this.receiveBuffer.clear();
        while (true) {
            Packet<JSONArray> packetPoll = this.sendBuffer.poll();
            if (packetPoll == null) {
                this.sendBuffer.clear();
                return;
            }
            packet(packetPoll);
        }
    }

    private void onack(Packet<JSONArray> packet) {
        Ack ackRemove = this.acks.remove(Integer.valueOf(packet.f57215id));
        if (ackRemove != null) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                logger2.fine(String.format("calling ack %s with %s", Integer.valueOf(packet.f57215id), packet.data));
            }
            ackRemove.call(toArray(packet.data));
            return;
        }
        Logger logger3 = logger;
        if (logger3.isLoggable(Level.FINE)) {
            logger3.fine("bad ack " + packet.f57215id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onclose(String str) {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine("close (" + str + ")");
        }
        this.connected = false;
        this.f57208id = null;
        super.emit(EVENT_DISCONNECT, str);
    }

    private void onconnect(String str) {
        this.connected = true;
        this.f57208id = str;
        emitBuffered();
        super.emit(EVENT_CONNECT, new Object[0]);
    }

    private void ondisconnect() {
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine("server disconnect (" + this.nsp + ")");
        }
        destroy();
        onclose("io server disconnect");
    }

    private void onevent(Packet<JSONArray> packet) {
        ArrayList arrayList = new ArrayList(Arrays.asList(toArray(packet.data)));
        Logger logger2 = logger;
        if (logger2.isLoggable(Level.FINE)) {
            logger2.fine(String.format("emitting event %s", arrayList));
        }
        if (packet.f57215id >= 0) {
            logger2.fine("attaching ack callback to event");
            arrayList.add(ack(packet.f57215id));
        }
        if (!this.connected) {
            this.receiveBuffer.add(arrayList);
            return;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (!this.onAnyIncomingListeners.isEmpty()) {
            Object[] array = arrayList.toArray();
            Iterator<Emitter.Listener> it = this.onAnyIncomingListeners.iterator();
            while (it.hasNext()) {
                it.next().call(array);
            }
        }
        super.emit(arrayList.remove(0).toString(), arrayList.toArray());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onopen() {
        logger.fine("transport is open - connecting");
        if (this.auth != null) {
            packet(new Packet(0, new JSONObject((Map<?, ?>) this.auth)));
        } else {
            packet(new Packet(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void onpacket(Packet<?> packet) {
        if (this.nsp.equals(packet.nsp)) {
            switch (packet.type) {
                case 0:
                    T t = packet.data;
                    if (!(t instanceof JSONObject) || !((JSONObject) t).has("sid")) {
                        super.emit(EVENT_CONNECT_ERROR, new SocketIOException("It seems you are trying to reach a Socket.IO server in v2.x with a v3.x client, which is not possible"));
                        break;
                    } else {
                        try {
                            onconnect(((JSONObject) packet.data).getString("sid"));
                            break;
                        } catch (JSONException unused) {
                            return;
                        }
                    }
                    break;
                case 1:
                    ondisconnect();
                    break;
                case 2:
                    onevent(packet);
                    break;
                case 3:
                    onack(packet);
                    break;
                case 4:
                    destroy();
                    super.emit(EVENT_CONNECT_ERROR, packet.data);
                    break;
                case 5:
                    onevent(packet);
                    break;
                case 6:
                    onack(packet);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void packet(Packet packet) {
        if (packet.type == 2 && !this.onAnyOutgoingListeners.isEmpty()) {
            Object[] array = toArray((JSONArray) packet.data);
            Iterator<Emitter.Listener> it = this.onAnyOutgoingListeners.iterator();
            while (it.hasNext()) {
                it.next().call(array);
            }
        }
        packet.nsp = this.nsp;
        this.f57209io.packet(packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subEvents() {
        if (this.subs != null) {
            return;
        }
        this.subs = new LinkedList<C0639On.Handle>(this.f57209io) { // from class: io.socket.client.Socket.2
            final /* synthetic */ Manager val$io;

            {
                this.val$io = manager;
                add(C0639On.m213181on(manager, "open", new Emitter.Listener() { // from class: io.socket.client.Socket.2.1
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onopen();
                    }
                }));
                add(C0639On.m213181on(manager, "packet", new Emitter.Listener() { // from class: io.socket.client.Socket.2.2
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onpacket((Packet) objArr[0]);
                    }
                }));
                add(C0639On.m213181on(manager, "error", new Emitter.Listener() { // from class: io.socket.client.Socket.2.3
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        if (Socket.this.connected) {
                            return;
                        }
                        Socket.super.emit(Socket.EVENT_CONNECT_ERROR, objArr[0]);
                    }
                }));
                add(C0639On.m213181on(manager, "close", new Emitter.Listener() { // from class: io.socket.client.Socket.2.4
                    @Override // io.socket.emitter.Emitter.Listener
                    public void call(Object... objArr) {
                        Socket.this.onclose(objArr.length > 0 ? (String) objArr[0] : null);
                    }
                }));
            }
        };
    }

    private static Object[] toArray(JSONArray jSONArray) {
        Object obj;
        int length = jSONArray.length();
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            Object obj2 = null;
            try {
                obj = jSONArray.get(i);
            } catch (JSONException e) {
                logger.log(Level.WARNING, "An error occured while retrieving data from JSONArray", (Throwable) e);
                obj = null;
            }
            if (!JSONObject.NULL.equals(obj)) {
                obj2 = obj;
            }
            objArr[i] = obj2;
        }
        return objArr;
    }

    public Socket close() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.8
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected) {
                    if (Socket.logger.isLoggable(Level.FINE)) {
                        Socket.logger.fine("performing disconnect (" + Socket.this.nsp + ")");
                    }
                    Socket.this.packet(new Packet(1));
                }
                Socket.this.destroy();
                if (Socket.this.connected) {
                    Socket.this.onclose("io client disconnect");
                }
            }
        });
        return this;
    }

    public Socket connect() {
        return open();
    }

    public boolean connected() {
        return this.connected;
    }

    public Socket disconnect() {
        return close();
    }

    @Override // io.socket.emitter.Emitter
    public Emitter emit(final String str, final Object... objArr) {
        if (RESERVED_EVENTS.containsKey(str)) {
            throw new RuntimeException(AbstractC0003a2.m33b4("'", str, "' is a reserved event name"));
        }
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.5
            @Override // java.lang.Runnable
            public void run() {
                Ack ack;
                Object[] objArr2 = objArr;
                int length = objArr2.length - 1;
                if (objArr2.length <= 0 || !(objArr2[length] instanceof Ack)) {
                    ack = null;
                } else {
                    objArr2 = new Object[length];
                    for (int i = 0; i < length; i++) {
                        objArr2[i] = objArr[i];
                    }
                    ack = (Ack) objArr[length];
                }
                Socket.this.emit(str, objArr2, ack);
            }
        });
        return this;
    }

    /* renamed from: id */
    public String m213182id() {
        return this.f57208id;
    }

    /* renamed from: io */
    public Manager m213183io() {
        return this.f57209io;
    }

    public boolean isActive() {
        return this.subs != null;
    }

    public Socket offAnyIncoming() {
        this.onAnyIncomingListeners.clear();
        return this;
    }

    public Socket offAnyOutgoing() {
        this.onAnyOutgoingListeners.clear();
        return this;
    }

    public Socket onAnyIncoming(Emitter.Listener listener) {
        this.onAnyIncomingListeners.add(listener);
        return this;
    }

    public Socket onAnyOutgoing(Emitter.Listener listener) {
        this.onAnyOutgoingListeners.add(listener);
        return this;
    }

    public Socket open() {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.3
            @Override // java.lang.Runnable
            public void run() {
                if (Socket.this.connected || Socket.this.f57209io.isReconnecting()) {
                    return;
                }
                Socket.this.subEvents();
                Socket.this.f57209io.open();
                if (Manager.ReadyState.OPEN == Socket.this.f57209io.readyState) {
                    Socket.this.onopen();
                }
            }
        });
        return this;
    }

    public Socket send(final Object... objArr) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.4
            @Override // java.lang.Runnable
            public void run() {
                Socket.this.emit("message", objArr);
            }
        });
        return this;
    }

    public Socket offAnyIncoming(Emitter.Listener listener) {
        Iterator<Emitter.Listener> it = this.onAnyIncomingListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next() == listener) {
                it.remove();
                break;
            }
        }
        return this;
    }

    public Socket offAnyOutgoing(Emitter.Listener listener) {
        Iterator<Emitter.Listener> it = this.onAnyOutgoingListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next() == listener) {
                it.remove();
                break;
            }
        }
        return this;
    }

    public Emitter emit(final String str, final Object[] objArr, final Ack ack) {
        EventThread.exec(new Runnable() { // from class: io.socket.client.Socket.6
            @Override // java.lang.Runnable
            public void run() {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(str);
                Object[] objArr2 = objArr;
                if (objArr2 != null) {
                    for (Object obj : objArr2) {
                        jSONArray.put(obj);
                    }
                }
                Packet packet = new Packet(2, jSONArray);
                if (ack != null) {
                    final int i = Socket.this.ids;
                    Socket.logger.fine(String.format("emitting packet with ack id %d", Integer.valueOf(i)));
                    Ack ack2 = ack;
                    if (ack2 instanceof AckWithTimeout) {
                        final AckWithTimeout ackWithTimeout = (AckWithTimeout) ack2;
                        ackWithTimeout.schedule(new TimerTask() { // from class: io.socket.client.Socket.6.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                Socket.this.acks.remove(Integer.valueOf(i));
                                Iterator it = Socket.this.sendBuffer.iterator();
                                while (it.hasNext()) {
                                    if (((Packet) it.next()).f57215id == i) {
                                        it.remove();
                                    }
                                }
                                ackWithTimeout.onTimeout();
                            }
                        });
                    }
                    Socket.this.acks.put(Integer.valueOf(i), ack);
                    packet.f57215id = Socket.access$708(Socket.this);
                }
                if (Socket.this.connected) {
                    Socket.this.packet(packet);
                } else {
                    Socket.this.sendBuffer.add(packet);
                }
            }
        });
        return this;
    }
}
