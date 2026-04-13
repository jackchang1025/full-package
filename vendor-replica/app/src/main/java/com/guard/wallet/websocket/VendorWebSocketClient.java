package com.guard.wallet.websocket;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

/**
 * Vendor WebSocketClient wrapper.
 *
 * The original obfuscated class {@code f1.a} mapped to
 * {@code org.java_websocket.client.WebSocketClient}.  Callers in {@code o/d.java}
 * directly access internal fields ({@code engine}, {@code ostream}, {@code socket})
 * that are private in the real library.  This class exposes them via reflection so
 * that the existing caller code compiles and runs without modifying the library.
 *
 * <h3>Field mapping (f1.a -> real library)</h3>
 * <ul>
 *   <li>{@code j} -> {@code engine} (WebSocketImpl) — has public {@code outQueue}</li>
 *   <li>{@code l} -> {@code ostream} (OutputStream)</li>
 *   <li>{@code k} -> {@code socket} (Socket)</li>
 *   <li>{@code t} -> static DECODERTHREAD_ID placeholder (always 0)</li>
 * </ul>
 *
 * <h3>Method mapping (vendor single-letter -> real API)</h3>
 * <ul>
 *   <li>{@code u()} -> {@link #connect()}</li>
 *   <li>{@code t()} -> {@link #close()}</li>
 *   <li>{@code c(String)} -> {@link #send(String)}</li>
 *   <li>{@code w(Exception)} -> {@link #onError(Exception)}</li>
 *   <li>{@code x(String)} -> {@link #onMessage(String)}</li>
 * </ul>
 */
public abstract class VendorWebSocketClient extends WebSocketClient {

    /**
     * Static field from vendor: DECODERTHREAD_ID equivalent (always 0).
     * Referenced by {@code o/d.java} as {@code f1.a.t}.
     */
    public static final int t = 0;

    // Reflection-cached Field objects for accessing private WebSocketClient internals.
    private static final Field ENGINE_FIELD;
    private static final Field OSTREAM_FIELD;
    private static final Field SOCKET_FIELD;

    static {
        try {
            ENGINE_FIELD = WebSocketClient.class.getDeclaredField("engine");
            ENGINE_FIELD.setAccessible(true);

            OSTREAM_FIELD = WebSocketClient.class.getDeclaredField("ostream");
            OSTREAM_FIELD.setAccessible(true);

            SOCKET_FIELD = WebSocketClient.class.getDeclaredField("socket");
            SOCKET_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to locate WebSocketClient internal fields", e);
        }
    }

    /**
     * Exposed engine reference (vendor field name: {@code j}).
     * Callers access {@code j.outQueue} for the send queue.
     * Populated lazily on first access via reflection.
     */
    public WebSocketImpl j;

    /**
     * Exposed socket reference (vendor field name: {@code k}).
     * Callers check for null and call {@code close()} on it.
     * Populated lazily on first access via reflection.
     */
    public Socket k;

    /**
     * Exposed output stream reference (vendor field name: {@code l}).
     * Callers call {@code write()} and {@code flush()} on it.
     * Populated lazily on first access via reflection.
     */
    public OutputStream l;

    public VendorWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    // ---- Accessor methods to sync field values from real WebSocketClient ----

    /**
     * Sync internal field values from the real library into the public fields
     * that {@code o/d.java} accesses.  Call this before accessing j/k/l.
     */
    public void syncInternalFields() {
        try {
            this.j = (WebSocketImpl) ENGINE_FIELD.get(this);
            this.l = (OutputStream) OSTREAM_FIELD.get(this);
            this.k = (Socket) SOCKET_FIELD.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to read WebSocketClient internal fields", e);
        }
    }

    /** Get the WebSocketImpl engine (vendor field j). */
    public WebSocketImpl getEngine() {
        try {
            return (WebSocketImpl) ENGINE_FIELD.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /** Get the output stream (vendor field l). */
    public OutputStream getOutputStream() {
        try {
            return (OutputStream) OSTREAM_FIELD.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /** Get the underlying socket (vendor field k). Uses public API first. */
    public Socket getVendorSocket() {
        Socket s = getSocket();
        if (s != null) return s;
        try {
            return (Socket) SOCKET_FIELD.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // ---- Vendor method aliases (single-letter names from decompiled code) ----

    /**
     * Vendor {@code u()} -> start the WebSocket connection.
     * Called by bridge code: {@code bridge.u()}.
     */
    public void u() {
        connect();
    }

    /**
     * Vendor {@code t()} -> close the WebSocket connection.
     * Called by bridge code: {@code this.t()}.
     * Note: this shadows the static field {@code t} but Java resolves
     * method vs field unambiguously.
     */
    public void t() {
        close();
    }

    /**
     * Vendor {@code c(String)} -> send a text message.
     * Called by bridge code: {@code this.c(json)}.
     */
    public void c(String message) {
        send(message);
    }

    /**
     * Vendor {@code w(Exception)} -> error handler.
     * Subclasses override this to handle errors.
     * The real library calls {@link #onError(Exception)}, which delegates here.
     */
    public void w(Exception ex) {
        // Default: no-op.  Subclasses (bridge.a) override this.
    }

    /**
     * Vendor {@code x(String)} -> message handler.
     * Subclasses override this to handle incoming messages.
     * The real library calls {@link #onMessage(String)}, which delegates here.
     */
    public void x(String message) {
        // Default: no-op.  Subclasses (bridge.a) override this.
    }

    // ---- WebSocketClient abstract method implementations ----
    // Delegate to vendor single-letter methods so subclasses only need to
    // override the vendor-named methods they already implement.

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // Sync internal fields now that connection is established.
        syncInternalFields();
    }

    @Override
    public void onMessage(String message) {
        x(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // Default: no-op
    }

    @Override
    public void onError(Exception ex) {
        w(ex);
    }
}
