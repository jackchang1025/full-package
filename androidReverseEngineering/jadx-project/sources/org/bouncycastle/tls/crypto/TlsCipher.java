package org.bouncycastle.tls.crypto;

import org.bouncycastle.tls.ProtocolVersion;

/* loaded from: classes.dex */
public interface TlsCipher {
    TlsDecodeResult decodeCiphertext(long j2, short s2, ProtocolVersion protocolVersion, byte[] bArr, int i2, int i3);

    TlsEncodeResult encodePlaintext(long j2, short s2, ProtocolVersion protocolVersion, int i2, byte[] bArr, int i3, int i4);

    int getCiphertextDecodeLimit(int i2);

    int getCiphertextEncodeLimit(int i2, int i3);

    int getPlaintextLimit(int i2);

    void rekeyDecoder();

    void rekeyEncoder();

    boolean usesOpaqueRecordType();
}
