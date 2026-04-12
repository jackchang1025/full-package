package io.socket.client;

import io.socket.client.Manager;
import io.socket.client.Url;
import io.socket.engineio.client.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.WebSocket;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: io.socket.client.IO */
/* loaded from: classes2.dex */
public class C0630IO {
    private static final Logger logger = Logger.getLogger(C0630IO.class.getName());
    private static final ConcurrentHashMap<String, Manager> managers = new ConcurrentHashMap<>();
    public static int protocol = 5;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: io.socket.client.IO$Options */
    public static class Options extends Manager.Options {
        public boolean forceNew;
        public boolean multiplex = true;

        public static SocketOptionBuilder builder() {
            return SocketOptionBuilder.builder();
        }
    }

    private C0630IO() {
    }

    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        Manager.defaultCallFactory = factory;
    }

    public static void setDefaultOkHttpWebSocketFactory(WebSocket.Factory factory) {
        Manager.defaultWebSocketFactory = factory;
    }

    public static Socket socket(String str) throws URISyntaxException {
        return socket(str, (Options) null);
    }

    public static Socket socket(String str, Options options) throws URISyntaxException {
        return socket(new URI(str), options);
    }

    public static Socket socket(URI uri) {
        return socket(uri, (Options) null);
    }

    public static Socket socket(URI uri, Options options) {
        Manager manager;
        String str;
        if (options == null) {
            options = new Options();
        }
        Url.ParsedURI parsedURI = Url.parse(uri);
        URI uri2 = parsedURI.uri;
        String str2 = parsedURI.f57210id;
        ConcurrentHashMap<String, Manager> concurrentHashMap = managers;
        boolean z = options.forceNew || !options.multiplex || (concurrentHashMap.containsKey(str2) && concurrentHashMap.get(str2).nsps.containsKey(uri2.getPath()));
        String query = uri2.getQuery();
        if (query != null && ((str = ((Socket.Options) options).query) == null || str.isEmpty())) {
            ((Socket.Options) options).query = query;
        }
        if (z) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                logger2.fine("ignoring socket cache for " + uri2);
            }
            manager = new Manager(uri2, options);
        } else {
            if (!concurrentHashMap.containsKey(str2)) {
                Logger logger3 = logger;
                if (logger3.isLoggable(Level.FINE)) {
                    logger3.fine("new io instance for " + uri2);
                }
                concurrentHashMap.putIfAbsent(str2, new Manager(uri2, options));
            }
            manager = concurrentHashMap.get(str2);
        }
        return manager.socket(uri2.getPath(), options);
    }
}
