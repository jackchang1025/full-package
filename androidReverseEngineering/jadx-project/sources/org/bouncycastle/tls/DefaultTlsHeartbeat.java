package org.bouncycastle.tls;

import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class DefaultTlsHeartbeat implements TlsHeartbeat {
    private int counter = 0;
    private final int idleMillis;
    private final int timeoutMillis;

    public DefaultTlsHeartbeat(int i2, int i3) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("'idleMillis' must be > 0");
        }
        if (i3 <= 0) {
            throw new IllegalArgumentException("'timeoutMillis' must be > 0");
        }
        this.idleMillis = i2;
        this.timeoutMillis = i3;
    }

    @Override // org.bouncycastle.tls.TlsHeartbeat
    public synchronized byte[] generatePayload() {
        int i2;
        i2 = this.counter + 1;
        this.counter = i2;
        return Pack.intToBigEndian(i2);
    }

    @Override // org.bouncycastle.tls.TlsHeartbeat
    public int getIdleMillis() {
        return this.idleMillis;
    }

    @Override // org.bouncycastle.tls.TlsHeartbeat
    public int getTimeoutMillis() {
        return this.timeoutMillis;
    }
}
