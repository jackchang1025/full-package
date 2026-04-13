package org.bouncycastle.openssl.bc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.RC2Engine;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.openssl.EncryptionException;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class PEMUtilities {
    private static final Map KEYSIZES;
    private static final Set PKCS5_SCHEME_1;
    private static final Set PKCS5_SCHEME_2;

    static {
        HashMap hashMap = new HashMap();
        KEYSIZES = hashMap;
        HashSet hashSet = new HashSet();
        PKCS5_SCHEME_1 = hashSet;
        HashSet hashSet2 = new HashSet();
        PKCS5_SCHEME_2 = hashSet2;
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
        hashSet2.add(PKCSObjectIdentifiers.id_PBES2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.des_EDE3_CBC;
        hashSet2.add(aSN1ObjectIdentifier);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_aes128_CBC;
        hashSet2.add(aSN1ObjectIdentifier2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_aes192_CBC;
        hashSet2.add(aSN1ObjectIdentifier3);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_aes256_CBC;
        hashSet2.add(aSN1ObjectIdentifier4);
        hashMap.put(aSN1ObjectIdentifier.getId(), Integers.valueOf(192));
        hashMap.put(aSN1ObjectIdentifier2.getId(), Integers.valueOf(128));
        hashMap.put(aSN1ObjectIdentifier3.getId(), Integers.valueOf(192));
        hashMap.put(aSN1ObjectIdentifier4.getId(), Integers.valueOf(256));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4.getId(), Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, Integers.valueOf(40));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, Integers.valueOf(192));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC, Integers.valueOf(40));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0130 A[Catch: Exception -> 0x016a, TRY_ENTER, TryCatch #0 {Exception -> 0x016a, blocks: (B:23:0x0130, B:26:0x013e, B:27:0x014a, B:31:0x0164, B:34:0x0142, B:35:0x0136), top: B:21:0x012e }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x013e A[Catch: Exception -> 0x016a, TryCatch #0 {Exception -> 0x016a, blocks: (B:23:0x0130, B:26:0x013e, B:27:0x014a, B:31:0x0164, B:34:0x0142, B:35:0x0136), top: B:21:0x012e }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0163 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0164 A[Catch: Exception -> 0x016a, TRY_LEAVE, TryCatch #0 {Exception -> 0x016a, blocks: (B:23:0x0130, B:26:0x013e, B:27:0x014a, B:31:0x0164, B:34:0x0142, B:35:0x0136), top: B:21:0x012e }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0142 A[Catch: Exception -> 0x016a, TryCatch #0 {Exception -> 0x016a, blocks: (B:23:0x0130, B:26:0x013e, B:27:0x014a, B:31:0x0164, B:34:0x0142, B:35:0x0136), top: B:21:0x012e }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0136 A[Catch: Exception -> 0x016a, TryCatch #0 {Exception -> 0x016a, blocks: (B:23:0x0130, B:26:0x013e, B:27:0x014a, B:31:0x0164, B:34:0x0142, B:35:0x0136), top: B:21:0x012e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] crypt(boolean z2, byte[] bArr, char[] cArr, String str, byte[] bArr2) {
        String str2;
        byte[] bArr3;
        KeyParameter key;
        BlockCipher aESEngine;
        BlockCipher oFBBlockCipher;
        int outputSize;
        int doFinal;
        byte[] bArr4 = bArr2;
        PKCS7Padding pKCS7Padding = new PKCS7Padding();
        PKCS7Padding pKCS7Padding2 = null;
        if (str.endsWith("-CFB")) {
            str2 = "CFB";
            pKCS7Padding = null;
        } else {
            str2 = "CBC";
        }
        if (str.endsWith("-ECB") || "DES-EDE".equals(str) || "DES-EDE3".equals(str)) {
            str2 = "ECB";
            bArr3 = null;
        } else {
            bArr3 = bArr4;
        }
        if (str.endsWith("-OFB")) {
            str2 = "OFB";
        } else {
            pKCS7Padding2 = pKCS7Padding;
        }
        if (str.startsWith("DES-EDE")) {
            key = getKey(cArr, 24, bArr4, !str.startsWith("DES-EDE3"));
            aESEngine = new DESedeEngine();
        } else if (str.startsWith("DES-")) {
            key = getKey(cArr, 8, bArr4);
            aESEngine = new DESEngine();
        } else if (str.startsWith("BF-")) {
            key = getKey(cArr, 16, bArr4);
            aESEngine = new BlowfishEngine();
        } else {
            int i2 = 128;
            if (str.startsWith("RC2-")) {
                if (str.startsWith("RC2-40-")) {
                    i2 = 40;
                } else if (str.startsWith("RC2-64-")) {
                    i2 = 64;
                }
                RC2Parameters rC2Parameters = new RC2Parameters(getKey(cArr, i2 / 8, bArr4).getKey(), i2);
                aESEngine = new RC2Engine();
                key = rC2Parameters;
            } else {
                if (!str.startsWith("AES-")) {
                    throw new EncryptionException("unknown encryption with private key: ".concat(str));
                }
                if (bArr4.length > 8) {
                    byte[] bArr5 = new byte[8];
                    System.arraycopy(bArr4, 0, bArr5, 0, 8);
                    bArr4 = bArr5;
                }
                if (!str.startsWith("AES-128-")) {
                    if (str.startsWith("AES-192-")) {
                        i2 = 192;
                    } else {
                        if (!str.startsWith("AES-256-")) {
                            throw new EncryptionException("unknown AES encryption with private key: ".concat(str));
                        }
                        i2 = 256;
                    }
                }
                key = getKey(cArr, i2 / 8, bArr4);
                aESEngine = new AESEngine();
            }
        }
        try {
            if (str2.equals("CBC")) {
                oFBBlockCipher = new CBCBlockCipher(aESEngine);
            } else {
                if (!str2.equals("CFB")) {
                    if (str2.equals("OFB")) {
                        oFBBlockCipher = new OFBBlockCipher(aESEngine, aESEngine.getBlockSize() * 8);
                    }
                    BufferedBlockCipher bufferedBlockCipher = pKCS7Padding2 != null ? new BufferedBlockCipher(aESEngine) : new PaddedBufferedBlockCipher(aESEngine, pKCS7Padding2);
                    if (bArr3 != null) {
                        bufferedBlockCipher.init(z2, key);
                    } else {
                        bufferedBlockCipher.init(z2, new ParametersWithIV(key, bArr3));
                    }
                    outputSize = bufferedBlockCipher.getOutputSize(bArr.length);
                    byte[] bArr6 = new byte[outputSize];
                    int processBytes = bufferedBlockCipher.processBytes(bArr, 0, bArr.length, bArr6, 0);
                    doFinal = processBytes + bufferedBlockCipher.doFinal(bArr6, processBytes);
                    if (doFinal != outputSize) {
                        return bArr6;
                    }
                    byte[] bArr7 = new byte[doFinal];
                    System.arraycopy(bArr6, 0, bArr7, 0, doFinal);
                    return bArr7;
                }
                oFBBlockCipher = new CFBBlockCipher(aESEngine, aESEngine.getBlockSize() * 8);
            }
            BufferedBlockCipher bufferedBlockCipher2 = pKCS7Padding2 != null ? new BufferedBlockCipher(aESEngine) : new PaddedBufferedBlockCipher(aESEngine, pKCS7Padding2);
            if (bArr3 != null) {
            }
            outputSize = bufferedBlockCipher2.getOutputSize(bArr.length);
            byte[] bArr62 = new byte[outputSize];
            int processBytes2 = bufferedBlockCipher2.processBytes(bArr, 0, bArr.length, bArr62, 0);
            doFinal = processBytes2 + bufferedBlockCipher2.doFinal(bArr62, processBytes2);
            if (doFinal != outputSize) {
            }
        } catch (Exception e2) {
            throw new EncryptionException("exception using cipher - please check password and data.", e2);
        }
        aESEngine = oFBBlockCipher;
    }

    public static KeyParameter generateSecretKeyForPKCS5Scheme2(String str, char[] cArr, byte[] bArr, int i2) {
        PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(new SHA1Digest());
        pKCS5S2ParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr, i2);
        return (KeyParameter) pKCS5S2ParametersGenerator.generateDerivedParameters(getKeySize(str));
    }

    private static KeyParameter getKey(char[] cArr, int i2, byte[] bArr) {
        return getKey(cArr, i2, bArr, false);
    }

    public static int getKeySize(String str) {
        Map map = KEYSIZES;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        throw new IllegalStateException(AbstractC0000a.m15k("no key size for algorithm: ", str));
    }

    public static boolean isPKCS12(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return aSN1ObjectIdentifier.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
    }

    public static boolean isPKCS5Scheme1(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_1.contains(aSN1ObjectIdentifier);
    }

    public static boolean isPKCS5Scheme2(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_2.contains(aSN1ObjectIdentifier);
    }

    private static KeyParameter getKey(char[] cArr, int i2, byte[] bArr, boolean z2) {
        OpenSSLPBEParametersGenerator openSSLPBEParametersGenerator = new OpenSSLPBEParametersGenerator();
        openSSLPBEParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr, 1);
        KeyParameter keyParameter = (KeyParameter) openSSLPBEParametersGenerator.generateDerivedParameters(i2 * 8);
        if (!z2 || keyParameter.getKey().length != 24) {
            return keyParameter;
        }
        byte[] key = keyParameter.getKey();
        System.arraycopy(key, 0, key, 16, 8);
        return new KeyParameter(key);
    }
}
