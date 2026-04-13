package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class DTLSRequest {
    private final ClientHello clientHello;
    private final byte[] message;
    private final long recordSeq;

    public DTLSRequest(long j2, byte[] bArr, ClientHello clientHello) {
        this.recordSeq = j2;
        this.message = bArr;
        this.clientHello = clientHello;
    }

    public ClientHello getClientHello() {
        return this.clientHello;
    }

    public byte[] getMessage() {
        return this.message;
    }

    public int getMessageSeq() {
        return TlsUtils.readUint16(this.message, 4);
    }

    public long getRecordSeq() {
        return this.recordSeq;
    }
}
