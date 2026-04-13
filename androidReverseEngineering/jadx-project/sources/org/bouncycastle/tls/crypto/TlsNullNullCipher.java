package org.bouncycastle.tls.crypto;

import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsFatalAlert;

/* loaded from: classes.dex */
public class TlsNullNullCipher implements TlsCipher {
    public static final TlsNullNullCipher INSTANCE = new TlsNullNullCipher();

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsDecodeResult decodeCiphertext(long j2, short s2, ProtocolVersion protocolVersion, byte[] bArr, int i2, int i3) {
        return new TlsDecodeResult(bArr, i2, i3, s2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsEncodeResult encodePlaintext(long j2, short s2, ProtocolVersion protocolVersion, int i2, byte[] bArr, int i3, int i4) {
        int i5 = i2 + i4;
        byte[] bArr2 = new byte[i5];
        System.arraycopy(bArr, i3, bArr2, i2, i4);
        return new TlsEncodeResult(bArr2, 0, i5, s2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextDecodeLimit(int i2) {
        return i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextEncodeLimit(int i2, int i3) {
        return i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getPlaintextLimit(int i2) {
        return i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyDecoder() {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyEncoder() {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public boolean usesOpaqueRecordType() {
        return false;
    }
}
