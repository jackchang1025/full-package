package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
class SSL3Utils {
    private static final byte[] SSL_CLIENT = {67, 76, 78, 84};
    private static final byte[] SSL_SERVER = {83, 82, 86, 82};
    private static final byte IPAD_BYTE = 54;
    private static final byte[] IPAD = genPad(IPAD_BYTE, 48);
    private static final byte OPAD_BYTE = 92;
    private static final byte[] OPAD = genPad(OPAD_BYTE, 48);

    public static byte[] calculateVerifyData(TlsHandshakeHash tlsHandshakeHash, boolean z2) {
        TlsHash forkPRFHash = tlsHandshakeHash.forkPRFHash();
        byte[] bArr = z2 ? SSL_SERVER : SSL_CLIENT;
        forkPRFHash.update(bArr, 0, bArr.length);
        return forkPRFHash.calculateHash();
    }

    public static void completeCombinedHash(TlsContext tlsContext, TlsHash tlsHash, TlsHash tlsHash2) {
        byte[] extract = tlsContext.getCrypto().adoptSecret(tlsContext.getSecurityParametersHandshake().getMasterSecret()).extract();
        completeHash(extract, tlsHash, 48);
        completeHash(extract, tlsHash2, 40);
    }

    private static void completeHash(byte[] bArr, TlsHash tlsHash, int i2) {
        tlsHash.update(bArr, 0, bArr.length);
        tlsHash.update(IPAD, 0, i2);
        byte[] calculateHash = tlsHash.calculateHash();
        tlsHash.update(bArr, 0, bArr.length);
        tlsHash.update(OPAD, 0, i2);
        tlsHash.update(calculateHash, 0, calculateHash.length);
    }

    private static byte[] genPad(byte b, int i2) {
        byte[] bArr = new byte[i2];
        Arrays.fill(bArr, b);
        return bArr;
    }

    public static byte[] readEncryptedPMS(InputStream inputStream) {
        return Streams.readAll(inputStream);
    }

    public static void writeEncryptedPMS(byte[] bArr, OutputStream outputStream) {
        outputStream.write(bArr);
    }
}
