package org.bouncycastle.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public class HeartbeatMessage {
    protected byte[] padding;
    protected byte[] payload;
    protected short type;

    public static class PayloadBuffer extends ByteArrayOutputStream {
        public byte[] getPadding(int i2) {
            return TlsUtils.copyOfRangeExact(((ByteArrayOutputStream) this).buf, i2, ((ByteArrayOutputStream) this).count);
        }

        public byte[] getPayload(int i2) {
            if (i2 > ((ByteArrayOutputStream) this).count - 16) {
                return null;
            }
            return Arrays.copyOf(((ByteArrayOutputStream) this).buf, i2);
        }
    }

    public HeartbeatMessage(short s2, byte[] bArr, byte[] bArr2) {
        if (!HeartbeatMessageType.isValid(s2)) {
            throw new IllegalArgumentException("'type' is not a valid HeartbeatMessageType value");
        }
        if (bArr == null || bArr.length >= 65536) {
            throw new IllegalArgumentException("'payload' must have length < 2^16");
        }
        if (bArr2 == null || bArr2.length < 16) {
            throw new IllegalArgumentException("'padding' must have length >= 16");
        }
        this.type = s2;
        this.payload = bArr;
        this.padding = bArr2;
    }

    public static HeartbeatMessage create(TlsContext tlsContext, short s2, byte[] bArr) {
        return create(tlsContext, s2, bArr, 16);
    }

    public static HeartbeatMessage parse(InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        if (!HeartbeatMessageType.isValid(readUint8)) {
            throw new TlsFatalAlert((short) 47);
        }
        int readUint16 = TlsUtils.readUint16(inputStream);
        PayloadBuffer payloadBuffer = new PayloadBuffer();
        Streams.pipeAll(inputStream, payloadBuffer);
        byte[] payload = payloadBuffer.getPayload(readUint16);
        if (payload == null) {
            return null;
        }
        return new HeartbeatMessage(readUint8, payload, payloadBuffer.getPadding(readUint16));
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.type, outputStream);
        TlsUtils.checkUint16(this.payload.length);
        TlsUtils.writeUint16(this.payload.length, outputStream);
        outputStream.write(this.payload);
        outputStream.write(this.padding);
    }

    public int getPaddingLength() {
        return this.padding.length;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public short getType() {
        return this.type;
    }

    public static HeartbeatMessage create(TlsContext tlsContext, short s2, byte[] bArr, int i2) {
        return new HeartbeatMessage(s2, bArr, tlsContext.getNonceGenerator().generateNonce(i2));
    }
}
