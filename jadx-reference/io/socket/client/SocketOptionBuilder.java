package io.socket.client;

import io.socket.client.C0630IO;
import io.socket.engineio.client.Socket;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class SocketOptionBuilder {
    private final C0630IO.Options options;

    public SocketOptionBuilder() {
        this(null);
    }

    public static SocketOptionBuilder builder() {
        return new SocketOptionBuilder();
    }

    public C0630IO.Options build() {
        return this.options;
    }

    public SocketOptionBuilder setAuth(Map<String, String> map) {
        this.options.auth = map;
        return this;
    }

    public SocketOptionBuilder setExtraHeaders(Map<String, List<String>> map) {
        this.options.extraHeaders = map;
        return this;
    }

    public SocketOptionBuilder setForceNew(boolean z) {
        this.options.forceNew = z;
        return this;
    }

    public SocketOptionBuilder setHost(String str) {
        this.options.host = str;
        return this;
    }

    public SocketOptionBuilder setHostname(String str) {
        this.options.hostname = str;
        return this;
    }

    public SocketOptionBuilder setMultiplex(boolean z) {
        this.options.multiplex = z;
        return this;
    }

    public SocketOptionBuilder setPath(String str) {
        this.options.path = str;
        return this;
    }

    public SocketOptionBuilder setPolicyPort(int i) {
        this.options.policyPort = i;
        return this;
    }

    public SocketOptionBuilder setPort(int i) {
        this.options.port = i;
        return this;
    }

    public SocketOptionBuilder setQuery(String str) {
        ((Socket.Options) this.options).query = str;
        return this;
    }

    public SocketOptionBuilder setRandomizationFactor(double d) {
        this.options.randomizationFactor = d;
        return this;
    }

    public SocketOptionBuilder setReconnection(boolean z) {
        this.options.reconnection = z;
        return this;
    }

    public SocketOptionBuilder setReconnectionAttempts(int i) {
        this.options.reconnectionAttempts = i;
        return this;
    }

    public SocketOptionBuilder setReconnectionDelay(long j) {
        this.options.reconnectionDelay = j;
        return this;
    }

    public SocketOptionBuilder setReconnectionDelayMax(long j) {
        this.options.reconnectionDelayMax = j;
        return this;
    }

    public SocketOptionBuilder setRememberUpgrade(boolean z) {
        this.options.rememberUpgrade = z;
        return this;
    }

    public SocketOptionBuilder setSecure(boolean z) {
        this.options.secure = z;
        return this;
    }

    public SocketOptionBuilder setTimeout(long j) {
        this.options.timeout = j;
        return this;
    }

    public SocketOptionBuilder setTransports(String[] strArr) {
        this.options.transports = strArr;
        return this;
    }

    public SocketOptionBuilder setUpgrade(boolean z) {
        this.options.upgrade = z;
        return this;
    }

    public SocketOptionBuilder(C0630IO.Options options) {
        this.options = new C0630IO.Options();
        if (options != null) {
            setForceNew(options.forceNew).setMultiplex(options.multiplex).setReconnection(options.reconnection).setReconnectionAttempts(options.reconnectionAttempts).setReconnectionDelay(options.reconnectionDelay).setReconnectionDelayMax(options.reconnectionDelayMax).setRandomizationFactor(options.randomizationFactor).setTimeout(options.timeout).setTransports(options.transports).setUpgrade(options.upgrade).setRememberUpgrade(options.rememberUpgrade).setHost(options.host).setHostname(options.hostname).setPort(options.port).setPolicyPort(options.policyPort).setSecure(options.secure).setPath(options.path).setQuery(((Socket.Options) options).query).setAuth(options.auth).setExtraHeaders(options.extraHeaders);
        }
    }

    public static SocketOptionBuilder builder(C0630IO.Options options) {
        return new SocketOptionBuilder(options);
    }
}
