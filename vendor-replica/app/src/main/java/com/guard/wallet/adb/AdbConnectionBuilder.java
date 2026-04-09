package com.guard.wallet.adb;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.adb.AdbPairingTask;

import android.content.Context;
import android.util.Log;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.security.auth.DestroyFailedException;

/**
 * ADB 连接管理器抽象基类 -- 管理 ADB 连接生命周期: 发现、配对和流创建。
 * vendor 原始路径: b1/b.java
 */
public abstract class AdbConnectionBuilder implements Closeable {
    public final Object a = new Object();
    public AdbConnection b;                              // current ADB connection
    public String c = "127.0.0.1";                       // host
    public int d = 0;                                    // port
    public int e = 1;                                    // SDK version
    public long f = 30000L;                              // connect timeout
    public TimeUnit g = TimeUnit.MILLISECONDS;           // timeout unit
    public final ExecutorService h = Executors.newFixedThreadPool(1);

    /**
     * Create a key pair holder from subclass-provided key+cert.
     */
    public final AdbKeyPair A() {
        PrivateKey var1 = this.C();
        Objects.requireNonNull(var1);
        Certificate var2 = this.B();
        Objects.requireNonNull(var2);
        return new AdbKeyPair(var1, var2);
    }

    /** Subclass provides the certificate. */
    public abstract Certificate B();

    /** Subclass provides the private key. */
    public abstract PrivateKey C();

    /** Subclass reports whether already connected to ADB. */
    public abstract boolean D();

    /**
     * Open a stream on the current ADB connection.
     */
    public final AdbStream E(String[] var1, int var2) throws IOException, InterruptedException {
        synchronized (this.a) {
            AdbConnection var5 = this.b;
            android.util.Log.d("AdbDebug", "E() called: conn=" + var5 + ", service=" + var2);
            if (var5 != null) {
                Socket var39 = var5.a;
                android.util.Log.d("AdbDebug", "E() socket: closed=" + var39.isClosed() + ", connected=" + var39.isConnected() + ", auth=" + var5.n);
                if (!var39.isClosed() && var39.isConnected()) {
                    android.util.Log.d("AdbDebug", "E() opening stream...");
                    return this.b.z(var1, var2);
                }
                android.util.Log.e("AdbDebug", "E() socket not ready");
            } else {
                android.util.Log.e("AdbDebug", "E() this.b is NULL - no ADB connection");
            }
            throw new IOException("Not connected to ADB.");
        }
    }

    /**
     * Pair with ADB using TLS pairing code.
     *
     * @param param1 host
     * @param param2 port
     * @param param3 pairing code
     * @return true if pairing succeeded
     */
    public final boolean F(String param1, int param2, String param3) {
        synchronized (this.a) {
            AdbKeyPair keyPair = this.A();
            CountDownLatch latch = new CountDownLatch(1);
            AtomicBoolean result = new AtomicBoolean(false);

            Objects.requireNonNull(param1);
            Objects.requireNonNull(param3);

            AdbTlsPairing pairingClient = new AdbTlsPairing(param1, param2,
                    com.guard.wallet.utils.SystemHelper.Y(param3), keyPair);

            AdbPairingTask task = new AdbPairingTask(pairingClient, result, latch);
            this.h.submit(task);

            try {
                if (!latch.await(15, TimeUnit.SECONDS)) {
                    result.set(false);
                }
            } catch (InterruptedException ex) {
                AppUtils.s("AbsAdbConnectionManager", ex);
            }

            pairingClient.close();
            return result.get();
        }
    }

    @Override
    public void close() {
        try {
            AdbConnection var8 = this.b;
            if (var8 != null) {
                var8.close();
                this.b = null;
            }
            this.h.shutdownNow();
            this.C().destroy();
        } catch (DestroyFailedException var9) {
            AppUtils.t("AbsAdbConnectionManager", var9);
        } catch (NoSuchMethodError var10) {
            AppUtils.t("AbsAdbConnectionManager", var10);
        }
    }

    /**
     * Auto-discover and connect to ADB via mDNS.
     *
     * @param param1 Android context
     * @return discovered port, or 0 on failure
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final int x(Context param1) {
        synchronized (this.a) {
            AtomicInteger portRef = new AtomicInteger(-1);
            AtomicReference<String> hostRef = new AtomicReference<>(null);
            CountDownLatch latch = new CountDownLatch(1);

            // Start ADB discovery
            com.guard.wallet.discovery.NsdServiceDiscovery adbDiscovery = new com.guard.wallet.discovery.NsdServiceDiscovery(param1, "adb",
                    new AdbDnsResolver(hostRef, portRef, latch, 0));
            adbDiscovery.startDiscovery();

            // Start ADB-TLS discovery
            com.guard.wallet.discovery.NsdServiceDiscovery tlsDiscovery = new com.guard.wallet.discovery.NsdServiceDiscovery(param1, "adb-tls-connect",
                    new AdbDnsResolver(hostRef, portRef, latch, 1));
            tlsDiscovery.startDiscovery();

            try {
                if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
                    Log.e("AbsAdbConnectionManager",
                            "Timed out while trying to find a valid tls host address and port");
                }
            } catch (InterruptedException ex) {
                // ignore
            } finally {
                adbDiscovery.stopDiscovery();
                tlsDiscovery.stopDiscovery();
            }

            String host = hostRef.get();
            int port = portRef.get();

            if (host == null || port == -1) {
                Log.e("AbsAdbConnectionManager",
                        "Could not find any valid host address or port");
                return 0;
            }

            this.c = host;
            int sdk = this.e;
            AdbConnection conn = null;
            try {
                AdbKeyPair keyPair = this.A();
                conn = new AdbConnection(host, port, keyPair, sdk);
                conn.r = "com.guard.wallet";
            } catch (IOException ex) {
                AppUtils.s("AdbConnection", ex);
            } catch (Exception ex) {
                AppUtils.s("AdbConnection", ex);
            }

            this.b = conn;
            try {
                if (conn != null && conn.y(this.f, this.g)) {
                    return port;
                }
            } catch (Exception ex) {
                AppUtils.s("AbsAdbConnectionManager", ex);
            }
            return 0;
        }
    }

    /**
     * Connect to ADB on a specified port/host.
     *
     * @param param1 port
     * @param param2 host
     * @return connection result code (port on success, 0 on fail, -1 or -2 on error)
     */
    public final int y(int param1, String param2) {
        synchronized (this.a) {
            if (this.D()) {
                return this.d;
            }

            // Close existing connection if any
            AdbConnection existing = this.b;
            if (existing != null) {
                try {
                    existing.close();
                } catch (Exception ex) {
                    // ignore
                }
                this.b = null;
                Log.d("AbsAdbConnectionManager", "\u91ca\u653e mAdbConnection");
            }

            this.c = param2;
            int sdk = this.e;
            AdbConnection conn = null;

            try {
                AdbKeyPair keyPair = this.A();
                conn = new AdbConnection(param2, param1, keyPair, sdk);
                conn.r = "com.guard.wallet";
            } catch (IOException ex) {
                AppUtils.s("AdbConnection", ex);
            } catch (Exception ex) {
                AppUtils.s("AdbConnection", ex);
            }

            this.b = conn;

            try {
                if (conn != null && conn.y(10000, TimeUnit.MILLISECONDS)) {
                    this.d = param1;
                } else {
                    this.d = 0;
                }
            } catch (AdbProtocolException ex) {
                AppUtils.s("AbsAdbConnectionManager", ex);
                this.d = -2;
            } catch (Exception ex) {
                AppUtils.s("AbsAdbConnectionManager", ex);
                this.d = -1;
            }

            return this.d;
        }
    }

    /**
     * Connect to ADB on a port using the stored host.
     *
     * @param param1 port
     * @return true if connected
     */
    public final boolean z(int param1) {
        synchronized (this.a) {
            if (this.D()) {
                return true;
            }

            // Close existing connection if any
            AdbConnection existing = this.b;
            if (existing != null) {
                try {
                    existing.close();
                } catch (Exception ex) {
                    // ignore
                }
                this.b = null;
                Log.d("AbsAdbConnectionManager", "\u91ca\u653e mAdbConnection");
            }

            String host = this.c;
            int sdk = this.e;
            AdbConnection conn = null;

            try {
                AdbKeyPair keyPair = this.A();
                conn = new AdbConnection(host, param1, keyPair, sdk);
                conn.r = "com.guard.wallet";
            } catch (IOException ex) {
                AppUtils.s("AdbConnection", ex);
            } catch (Exception ex) {
                AppUtils.s("AdbConnection", ex);
            }

            this.b = conn;

            try {
                return conn != null && conn.y(this.f, this.g);
            } catch (Exception ex) {
                AppUtils.s("AbsAdbConnectionManager", ex);
                return false;
            }
        }
    }
}
