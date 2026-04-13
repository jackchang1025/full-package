package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TlsECCUtils {
    public static void checkPointEncoding(int i2, byte[] bArr) {
        if (TlsUtils.isNullOrEmpty(bArr)) {
            throw new TlsFatalAlert((short) 47);
        }
        if (i2 != 29 && i2 != 30 && bArr[0] != 4) {
            throw new TlsFatalAlert((short) 47);
        }
    }

    public static TlsECConfig createNamedECConfig(TlsContext tlsContext, int i2) {
        if (NamedGroup.getCurveBits(i2) >= 1) {
            return new TlsECConfig(i2);
        }
        throw new TlsFatalAlert((short) 80);
    }

    public static int getMinimumCurveBits(int i2) {
        return isECCCipherSuite(i2) ? 1 : 0;
    }

    public static boolean isECCCipherSuite(int i2) {
        int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(i2);
        if (keyExchangeAlgorithm == 24) {
            return true;
        }
        switch (keyExchangeAlgorithm) {
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                return true;
            default:
                return false;
        }
    }

    public static TlsECConfig receiveECDHConfig(TlsContext tlsContext, InputStream inputStream) {
        int[] clientSupportedGroups;
        if (TlsUtils.readUint8(inputStream) != 3) {
            throw new TlsFatalAlert((short) 40);
        }
        int readUint16 = TlsUtils.readUint16(inputStream);
        if (NamedGroup.refersToAnECDHCurve(readUint16) && ((clientSupportedGroups = tlsContext.getSecurityParametersHandshake().getClientSupportedGroups()) == null || Arrays.contains(clientSupportedGroups, readUint16))) {
            return new TlsECConfig(readUint16);
        }
        throw new TlsFatalAlert((short) 47);
    }

    public static void writeECConfig(TlsECConfig tlsECConfig, OutputStream outputStream) {
        writeNamedECParameters(tlsECConfig.getNamedGroup(), outputStream);
    }

    public static void writeNamedECParameters(int i2, OutputStream outputStream) {
        if (!NamedGroup.refersToASpecificCurve(i2)) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.writeUint8((short) 3, outputStream);
        TlsUtils.checkUint16(i2);
        TlsUtils.writeUint16(i2, outputStream);
    }
}
