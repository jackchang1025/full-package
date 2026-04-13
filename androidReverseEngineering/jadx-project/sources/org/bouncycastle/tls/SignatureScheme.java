package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCryptoUtils;

/* loaded from: classes.dex */
public class SignatureScheme {
    public static final int ecdsa_brainpoolP256r1tls13_sha256 = 2074;
    public static final int ecdsa_brainpoolP384r1tls13_sha384 = 2075;
    public static final int ecdsa_brainpoolP512r1tls13_sha512 = 2076;
    public static final int ecdsa_secp256r1_sha256 = 1027;
    public static final int ecdsa_secp384r1_sha384 = 1283;
    public static final int ecdsa_secp521r1_sha512 = 1539;
    public static final int ecdsa_sha1 = 515;
    public static final int ed25519 = 2055;
    public static final int ed448 = 2056;
    public static final int rsa_pkcs1_sha1 = 513;
    public static final int rsa_pkcs1_sha256 = 1025;
    public static final int rsa_pkcs1_sha384 = 1281;
    public static final int rsa_pkcs1_sha512 = 1537;
    public static final int rsa_pss_pss_sha256 = 2057;
    public static final int rsa_pss_pss_sha384 = 2058;
    public static final int rsa_pss_pss_sha512 = 2059;
    public static final int rsa_pss_rsae_sha256 = 2052;
    public static final int rsa_pss_rsae_sha384 = 2053;
    public static final int rsa_pss_rsae_sha512 = 2054;
    public static final int sm2sig_sm3 = 1800;

    public static int from(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        signatureAndHashAlgorithm.getClass();
        return from(signatureAndHashAlgorithm.getHash(), signatureAndHashAlgorithm.getSignature());
    }

    public static int getCryptoHashAlgorithm(int i2) {
        if (i2 == 1800) {
            return 7;
        }
        switch (i2) {
            case rsa_pss_rsae_sha256 /* 2052 */:
            case rsa_pss_pss_sha256 /* 2057 */:
                return 4;
            case rsa_pss_rsae_sha384 /* 2053 */:
            case rsa_pss_pss_sha384 /* 2058 */:
                return 5;
            case rsa_pss_rsae_sha512 /* 2054 */:
            case rsa_pss_pss_sha512 /* 2059 */:
                return 6;
            default:
                switch (i2) {
                    case ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
                        return 4;
                    case ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
                        return 5;
                    case ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                        return 6;
                    default:
                        short hashAlgorithm = getHashAlgorithm(i2);
                        if (8 != hashAlgorithm && HashAlgorithm.isRecognized(hashAlgorithm)) {
                            return TlsCryptoUtils.getHash(hashAlgorithm);
                        }
                        break;
                }
            case ed25519 /* 2055 */:
            case ed448 /* 2056 */:
                return -1;
        }
    }

    public static short getHashAlgorithm(int i2) {
        return (short) ((i2 >>> 8) & 255);
    }

    public static String getName(int i2) {
        if (i2 == 513) {
            return "rsa_pkcs1_sha1";
        }
        if (i2 == 515) {
            return "ecdsa_sha1";
        }
        if (i2 == 1025) {
            return "rsa_pkcs1_sha256";
        }
        if (i2 == 1027) {
            return "ecdsa_secp256r1_sha256";
        }
        if (i2 == 1281) {
            return "rsa_pkcs1_sha384";
        }
        if (i2 == 1283) {
            return "ecdsa_secp384r1_sha384";
        }
        if (i2 == 1537) {
            return "rsa_pkcs1_sha512";
        }
        if (i2 == 1539) {
            return "ecdsa_secp521r1_sha512";
        }
        if (i2 == 1800) {
            return "sm2sig_sm3";
        }
        switch (i2) {
            case rsa_pss_rsae_sha256 /* 2052 */:
                return "rsa_pss_rsae_sha256";
            case rsa_pss_rsae_sha384 /* 2053 */:
                return "rsa_pss_rsae_sha384";
            case rsa_pss_rsae_sha512 /* 2054 */:
                return "rsa_pss_rsae_sha512";
            case ed25519 /* 2055 */:
                return "ed25519";
            case ed448 /* 2056 */:
                return "ed448";
            case rsa_pss_pss_sha256 /* 2057 */:
                return "rsa_pss_pss_sha256";
            case rsa_pss_pss_sha384 /* 2058 */:
                return "rsa_pss_pss_sha384";
            case rsa_pss_pss_sha512 /* 2059 */:
                return "rsa_pss_pss_sha512";
            default:
                switch (i2) {
                    case ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
                        return "ecdsa_brainpoolP256r1tls13_sha256";
                    case ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
                        return "ecdsa_brainpoolP384r1tls13_sha384";
                    case ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                        return "ecdsa_brainpoolP512r1tls13_sha512";
                    default:
                        return "UNKNOWN";
                }
        }
    }

    public static int getNamedGroup(int i2) {
        if (i2 == 1027) {
            return 23;
        }
        if (i2 == 1283) {
            return 24;
        }
        if (i2 == 1539) {
            return 25;
        }
        if (i2 == 1800) {
            return 41;
        }
        switch (i2) {
            case ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
                return 31;
            case ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
                return 32;
            case ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                return 33;
            default:
                return -1;
        }
    }

    public static int getRSAPSSCryptoHashAlgorithm(int i2) {
        switch (i2) {
            case rsa_pss_rsae_sha256 /* 2052 */:
            case rsa_pss_pss_sha256 /* 2057 */:
                return 4;
            case rsa_pss_rsae_sha384 /* 2053 */:
            case rsa_pss_pss_sha384 /* 2058 */:
                return 5;
            case rsa_pss_rsae_sha512 /* 2054 */:
            case rsa_pss_pss_sha512 /* 2059 */:
                return 6;
            case ed25519 /* 2055 */:
            case ed448 /* 2056 */:
            default:
                return -1;
        }
    }

    public static short getSignatureAlgorithm(int i2) {
        return (short) (i2 & 255);
    }

    public static SignatureAndHashAlgorithm getSignatureAndHashAlgorithm(int i2) {
        return SignatureAndHashAlgorithm.getInstance(getHashAlgorithm(i2), getSignatureAlgorithm(i2));
    }

    public static String getText(int i2) {
        return getName(i2) + "(0x" + Integer.toHexString(i2) + ")";
    }

    public static boolean isECDSA(int i2) {
        switch (i2) {
            case ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
            case ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
            case ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                break;
            default:
                if (3 == getSignatureAlgorithm(i2)) {
                }
                break;
        }
        return true;
    }

    public static boolean isPrivate(int i2) {
        return (i2 >>> 9) == 254;
    }

    public static boolean isRSAPSS(int i2) {
        switch (i2) {
            case rsa_pss_rsae_sha256 /* 2052 */:
            case rsa_pss_rsae_sha384 /* 2053 */:
            case rsa_pss_rsae_sha512 /* 2054 */:
            case rsa_pss_pss_sha256 /* 2057 */:
            case rsa_pss_pss_sha384 /* 2058 */:
            case rsa_pss_pss_sha512 /* 2059 */:
                return true;
            case ed25519 /* 2055 */:
            case ed448 /* 2056 */:
            default:
                return false;
        }
    }

    public static int from(short s2, short s3) {
        return ((s2 & 255) << 8) | (s3 & 255);
    }
}
