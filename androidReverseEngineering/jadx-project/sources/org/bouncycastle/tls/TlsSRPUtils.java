package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Hashtable;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public class TlsSRPUtils {
    public static final Integer EXT_SRP = Integers.valueOf(12);

    public static void addSRPExtension(Hashtable hashtable, byte[] bArr) {
        hashtable.put(EXT_SRP, createSRPExtension(bArr));
    }

    public static byte[] createSRPExtension(byte[] bArr) {
        if (bArr != null) {
            return TlsUtils.encodeOpaque8(bArr);
        }
        throw new TlsFatalAlert((short) 80);
    }

    public static byte[] getSRPExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_SRP);
        if (extensionData == null) {
            return null;
        }
        return readSRPExtension(extensionData);
    }

    public static boolean isSRPCipherSuite(int i2) {
        switch (TlsUtils.getKeyExchangeAlgorithm(i2)) {
            case 21:
            case 22:
            case 23:
                return true;
            default:
                return false;
        }
    }

    public static byte[] readSRPExtension(byte[] bArr) {
        if (bArr != null) {
            return TlsUtils.decodeOpaque8(bArr, 1);
        }
        throw new IllegalArgumentException("'extensionData' cannot be null");
    }

    public static BigInteger readSRPParameter(InputStream inputStream) {
        return new BigInteger(1, TlsUtils.readOpaque16(inputStream, 1));
    }

    public static void writeSRPParameter(BigInteger bigInteger, OutputStream outputStream) {
        TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(bigInteger), outputStream);
    }
}
