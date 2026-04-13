package com.guard.wallet.http;

import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp ConnectionPool — inlined from s0.h after s0 package deletion.
 * Manages idle connections for reuse.
 */
public class ConnectionPool {
    public static final ThreadPoolExecutor g = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    public int a;
    public long b;
    public final ArrayDeque d = new ArrayDeque();
    public boolean f;

    public int b(ConnectionEntry entry, long nanoTime) { return 0; }

    /**
     * Represents a single pooled connection (was s0.g RealConnection).
     * Only the fields used by the cleanup loop are retained.
     */
    public static class ConnectionEntry {
        public java.net.Socket e;
        public long q = Long.MAX_VALUE;
    }
}
