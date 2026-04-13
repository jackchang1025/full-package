package org.bouncycastle.jsse.provider;

import java.io.Closeable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.jsse.BCSSLSocket;

/* loaded from: classes.dex */
abstract class ProvSSLSocketBase extends SSLSocket implements BCSSLSocket {
    protected static final boolean provAssumeOriginalHostName = PropertyUtils.getBooleanSystemProperty("org.bouncycastle.jsse.client.assumeOriginalHostName", false);
    protected static final boolean provJdkTlsTrustNameService = PropertyUtils.getBooleanSystemProperty("jdk.tls.trustNameService", false);
    protected final Closeable socketCloser = new Closeable() { // from class: org.bouncycastle.jsse.provider.ProvSSLSocketBase.1
        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            ProvSSLSocketBase.this.closeSocket();
        }
    };
    protected final Map<HandshakeCompletedListener, AccessControlContext> listeners = Collections.synchronizedMap(new HashMap(4));

    private Collection<Map.Entry<HandshakeCompletedListener, AccessControlContext>> getHandshakeCompletedEntries() {
        ArrayList arrayList;
        synchronized (this.listeners) {
            arrayList = this.listeners.isEmpty() ? null : new ArrayList(this.listeners.entrySet());
        }
        return arrayList;
    }

    @Override // javax.net.ssl.SSLSocket
    public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        if (handshakeCompletedListener == null) {
            throw new IllegalArgumentException("'listener' cannot be null");
        }
        this.listeners.put(handshakeCompletedListener, AccessController.getContext());
    }

    public void closeSocket() {
        super.close();
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public void connect(String str, int i2, int i3) {
        setHost(str);
        connect(createInetSocketAddress(str, i2), i3);
    }

    public InetSocketAddress createInetSocketAddress(String str, int i2) {
        return str == null ? new InetSocketAddress(InetAddress.getByName(null), i2) : new InetSocketAddress(str, i2);
    }

    @Override // java.net.Socket
    public final boolean getOOBInline() {
        throw new SocketException("This method is ineffective, since sending urgent data is not supported by SSLSockets");
    }

    public void implBind(InetAddress inetAddress, int i2) {
        bind(createInetSocketAddress(inetAddress, i2));
    }

    public void implConnect(String str, int i2) {
        connect(createInetSocketAddress(str, i2), 0);
    }

    public void notifyHandshakeCompletedListeners(SSLSession sSLSession) {
        final Collection<Map.Entry<HandshakeCompletedListener, AccessControlContext>> handshakeCompletedEntries = getHandshakeCompletedEntries();
        if (handshakeCompletedEntries == null) {
            return;
        }
        final HandshakeCompletedEvent handshakeCompletedEvent = new HandshakeCompletedEvent(this, sSLSession);
        SSLSocketUtil.handshakeCompleted(new Runnable() { // from class: org.bouncycastle.jsse.provider.ProvSSLSocketBase.2
            @Override // java.lang.Runnable
            public void run() {
                for (Map.Entry entry : handshakeCompletedEntries) {
                    final HandshakeCompletedListener handshakeCompletedListener = (HandshakeCompletedListener) entry.getKey();
                    AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: org.bouncycastle.jsse.provider.ProvSSLSocketBase.2.1
                        @Override // java.security.PrivilegedAction
                        public Void run() {
                            handshakeCompletedListener.handshakeCompleted(handshakeCompletedEvent);
                            return null;
                        }
                    }, (AccessControlContext) entry.getValue());
                }
            }
        });
    }

    @Override // javax.net.ssl.SSLSocket
    public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
        if (handshakeCompletedListener == null) {
            throw new IllegalArgumentException("'listener' cannot be null");
        }
        if (this.listeners.remove(handshakeCompletedListener) == null) {
            throw new IllegalArgumentException("'listener' is not registered");
        }
    }

    @Override // java.net.Socket
    public final void sendUrgentData(int i2) {
        throw new SocketException("This method is not supported by SSLSockets");
    }

    @Override // java.net.Socket
    public final void setOOBInline(boolean z2) {
        throw new SocketException("This method is ineffective, since sending urgent data is not supported by SSLSockets");
    }

    public InetSocketAddress createInetSocketAddress(InetAddress inetAddress, int i2) {
        return new InetSocketAddress(inetAddress, i2);
    }

    public void implConnect(InetAddress inetAddress, int i2) {
        connect(createInetSocketAddress(inetAddress, i2), 0);
    }
}
