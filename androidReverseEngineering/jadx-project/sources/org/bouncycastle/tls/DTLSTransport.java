package org.bouncycastle.tls;

import java.io.IOException;
import java.io.InterruptedIOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DTLSTransport implements DatagramTransport {
    private final DTLSRecordLayer recordLayer;

    public DTLSTransport(DTLSRecordLayer dTLSRecordLayer) {
        this.recordLayer = dTLSRecordLayer;
    }

    @Override // org.bouncycastle.tls.TlsCloseable
    public void close() {
        this.recordLayer.close();
    }

    @Override // org.bouncycastle.tls.DatagramReceiver
    public int getReceiveLimit() {
        return this.recordLayer.getReceiveLimit();
    }

    @Override // org.bouncycastle.tls.DatagramSender
    public int getSendLimit() {
        return this.recordLayer.getSendLimit();
    }

    @Override // org.bouncycastle.tls.DatagramReceiver
    public int receive(byte[] bArr, int i2, int i3, int i4) {
        if (bArr == null) {
            throw new NullPointerException("'buf' cannot be null");
        }
        if (i2 < 0 || i2 >= bArr.length) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("'off' is an invalid offset: ", i2));
        }
        if (i3 < 0 || i3 > bArr.length - i2) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("'len' is an invalid length: ", i3));
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("'waitMillis' cannot be negative");
        }
        try {
            return this.recordLayer.receive(bArr, i2, i3, i4);
        } catch (InterruptedIOException e2) {
            throw e2;
        } catch (IOException e3) {
            this.recordLayer.fail((short) 80);
            throw e3;
        } catch (RuntimeException e4) {
            this.recordLayer.fail((short) 80);
            throw new TlsFatalAlert((short) 80, (Throwable) e4);
        } catch (TlsFatalAlert e5) {
            this.recordLayer.fail(e5.getAlertDescription());
            throw e5;
        }
    }

    @Override // org.bouncycastle.tls.DatagramSender
    public void send(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new NullPointerException("'buf' cannot be null");
        }
        if (i2 < 0 || i2 >= bArr.length) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("'off' is an invalid offset: ", i2));
        }
        if (i3 < 0 || i3 > bArr.length - i2) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("'len' is an invalid length: ", i3));
        }
        try {
            this.recordLayer.send(bArr, i2, i3);
        } catch (InterruptedIOException e2) {
            throw e2;
        } catch (IOException e3) {
            this.recordLayer.fail((short) 80);
            throw e3;
        } catch (RuntimeException e4) {
            this.recordLayer.fail((short) 80);
            throw new TlsFatalAlert((short) 80, (Throwable) e4);
        } catch (TlsFatalAlert e5) {
            this.recordLayer.fail(e5.getAlertDescription());
            throw e5;
        }
    }
}
