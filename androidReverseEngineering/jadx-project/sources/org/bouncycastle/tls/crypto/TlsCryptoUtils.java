package org.bouncycastle.tls.crypto;

import org.bouncycastle.tls.HashAlgorithm;
import org.bouncycastle.tls.MACAlgorithm;
import org.bouncycastle.tls.PRFAlgorithm;
import org.bouncycastle.tls.SignatureAlgorithm;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;

/* loaded from: classes.dex */
public abstract class TlsCryptoUtils {
    private static final byte[] TLS13_PREFIX = {116, 108, 115, 49, 51, 32};

    public static int getHash(short s2) {
        switch (s2) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new IllegalArgumentException("specified HashAlgorithm invalid: " + HashAlgorithm.getText(s2));
        }
    }

    public static int getHashForHMAC(int i2) {
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                if (i2 == 3) {
                    return 4;
                }
                if (i2 == 4) {
                    return 5;
                }
                if (i2 == 5) {
                    return 6;
                }
                throw new IllegalArgumentException("specified MACAlgorithm not an HMAC: " + MACAlgorithm.getText(i2));
            }
        }
        return i3;
    }

    public static int getHashForPRF(int i2) {
        if (i2 == 0 || i2 == 1) {
            throw new IllegalArgumentException("legacy PRF not a valid algorithm");
        }
        if (i2 != 2) {
            if (i2 != 3) {
                if (i2 != 4) {
                    if (i2 != 5) {
                        if (i2 == 7) {
                            return 7;
                        }
                        throw new IllegalArgumentException("unknown PRFAlgorithm: " + PRFAlgorithm.getText(i2));
                    }
                }
            }
            return 5;
        }
        return 4;
    }

    public static int getHashOutputSize(int i2) {
        switch (i2) {
            case 1:
                return 16;
            case 2:
                return 20;
            case 3:
                return 28;
            case 4:
            case 7:
                return 32;
            case 5:
                return 48;
            case 6:
                return 64;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static int getSignature(short s2) {
        int i2 = 64;
        if (s2 != 64) {
            i2 = 65;
            if (s2 != 65) {
                switch (s2) {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    case 3:
                        return 3;
                    case 4:
                        return 4;
                    case 5:
                        return 5;
                    case 6:
                        return 6;
                    case 7:
                        return 7;
                    case 8:
                        return 8;
                    case 9:
                        return 9;
                    case 10:
                        return 10;
                    case 11:
                        return 11;
                    default:
                        throw new IllegalArgumentException("specified SignatureAlgorithm invalid: " + SignatureAlgorithm.getText(s2));
                }
            }
        }
        return i2;
    }

    public static TlsSecret hkdfExpandLabel(TlsSecret tlsSecret, int i2, String str, byte[] bArr, int i3) {
        int length = str.length();
        if (length < 1) {
            throw new TlsFatalAlert((short) 80);
        }
        int length2 = bArr.length;
        byte[] bArr2 = TLS13_PREFIX;
        int length3 = bArr2.length + length;
        int i4 = length3 + 1 + 2;
        byte[] bArr3 = new byte[length2 + 1 + i4];
        TlsUtils.checkUint16(i3);
        TlsUtils.writeUint16(i3, bArr3, 0);
        TlsUtils.checkUint8(length3);
        TlsUtils.writeUint8(length3, bArr3, 2);
        System.arraycopy(bArr2, 0, bArr3, 3, bArr2.length);
        int length4 = bArr2.length + 1 + 2;
        for (int i5 = 0; i5 < length; i5++) {
            bArr3[length4 + i5] = (byte) str.charAt(i5);
        }
        TlsUtils.writeOpaque8(bArr, bArr3, i4);
        return tlsSecret.hkdfExpand(i2, bArr3, i3);
    }
}
