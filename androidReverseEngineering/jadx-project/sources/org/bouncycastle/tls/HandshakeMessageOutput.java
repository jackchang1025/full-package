package org.bouncycastle.tls;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
class HandshakeMessageOutput extends ByteArrayOutputStream {
    public HandshakeMessageOutput(short s2) {
        this(s2, 60);
    }

    public static int getLength(int i2) {
        return i2 + 4;
    }

    public void prepareClientHello(TlsHandshakeHash tlsHandshakeHash, int i2) {
        int i3 = (((ByteArrayOutputStream) this).count - 4) + i2;
        TlsUtils.checkUint24(i3);
        TlsUtils.writeUint24(i3, ((ByteArrayOutputStream) this).buf, 1);
        tlsHandshakeHash.update(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count);
    }

    public void send(TlsProtocol tlsProtocol) {
        int i2 = ((ByteArrayOutputStream) this).count - 4;
        TlsUtils.checkUint24(i2);
        TlsUtils.writeUint24(i2, ((ByteArrayOutputStream) this).buf, 1);
        tlsProtocol.writeHandshakeMessage(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count);
        ((ByteArrayOutputStream) this).buf = null;
    }

    public void sendClientHello(TlsClientProtocol tlsClientProtocol, TlsHandshakeHash tlsHandshakeHash, int i2) {
        if (i2 > 0) {
            tlsHandshakeHash.update(((ByteArrayOutputStream) this).buf, ((ByteArrayOutputStream) this).count - i2, i2);
        }
        tlsClientProtocol.writeHandshakeMessage(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count);
        ((ByteArrayOutputStream) this).buf = null;
    }

    public HandshakeMessageOutput(short s2, int i2) {
        super(getLength(i2));
        TlsUtils.checkUint8(s2);
        TlsUtils.writeUint8(s2, (OutputStream) this);
        ((ByteArrayOutputStream) this).count += 3;
    }

    public static void send(TlsProtocol tlsProtocol, short s2, byte[] bArr) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput(s2, bArr.length);
        handshakeMessageOutput.write(bArr);
        handshakeMessageOutput.send(tlsProtocol);
    }
}
