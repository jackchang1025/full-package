package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class HeartbeatExtension {
    protected short mode;

    public HeartbeatExtension(short s2) {
        if (!HeartbeatMode.isValid(s2)) {
            throw new IllegalArgumentException("'mode' is not a valid HeartbeatMode value");
        }
        this.mode = s2;
    }

    public static HeartbeatExtension parse(InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        if (HeartbeatMode.isValid(readUint8)) {
            return new HeartbeatExtension(readUint8);
        }
        throw new TlsFatalAlert((short) 47);
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.mode, outputStream);
    }

    public short getMode() {
        return this.mode;
    }
}
