package io.socket.engineio.parser;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class Packet<T> {
    public static final String CLOSE = "close";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String NOOP = "noop";
    public static final String OPEN = "open";
    public static final String PING = "ping";
    public static final String PONG = "pong";
    public static final String UPGRADE = "upgrade";
    public T data;
    public String type;

    public Packet(String str) {
        this(str, null);
    }

    public Packet(String str, T t) {
        this.type = str;
        this.data = t;
    }
}
