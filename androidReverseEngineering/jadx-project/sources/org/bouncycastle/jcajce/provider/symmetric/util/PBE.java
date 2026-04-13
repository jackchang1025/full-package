package org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.digests.TigerDigest;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.util.DigestFactory;

/* loaded from: classes.dex */
public interface PBE {
    public static final int GOST3411 = 6;
    public static final int MD2 = 5;
    public static final int MD5 = 0;
    public static final int OPENSSL = 3;
    public static final int PKCS12 = 2;
    public static final int PKCS5S1 = 0;
    public static final int PKCS5S1_UTF8 = 4;
    public static final int PKCS5S2 = 1;
    public static final int PKCS5S2_UTF8 = 5;
    public static final int RIPEMD160 = 2;
    public static final int SHA1 = 1;
    public static final int SHA224 = 7;
    public static final int SHA256 = 4;
    public static final int SHA384 = 8;
    public static final int SHA3_224 = 10;
    public static final int SHA3_256 = 11;
    public static final int SHA3_384 = 12;
    public static final int SHA3_512 = 13;
    public static final int SHA512 = 9;
    public static final int SM3 = 14;
    public static final int TIGER = 3;

    public static class Util {
        private static byte[] convertPassword(int i2, PBEKeySpec pBEKeySpec) {
            return i2 == 2 ? PBEParametersGenerator.PKCS12PasswordToBytes(pBEKeySpec.getPassword()) : (i2 == 5 || i2 == 4) ? PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pBEKeySpec.getPassword()) : PBEParametersGenerator.PKCS5PasswordToBytes(pBEKeySpec.getPassword());
        }

        private static PBEParametersGenerator makePBEGenerator(int i2, int i3) {
            if (i2 == 0 || i2 == 4) {
                if (i3 == 0) {
                    return new PKCS5S1ParametersGenerator(DigestFactory.createMD5());
                }
                if (i3 == 1) {
                    return new PKCS5S1ParametersGenerator(DigestFactory.createSHA1());
                }
                if (i3 == 5) {
                    return new PKCS5S1ParametersGenerator(new MD2Digest());
                }
                throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
            }
            if (i2 != 1 && i2 != 5) {
                if (i2 != 2) {
                    return new OpenSSLPBEParametersGenerator();
                }
                switch (i3) {
                    case 0:
                        return new PKCS12ParametersGenerator(DigestFactory.createMD5());
                    case 1:
                        return new PKCS12ParametersGenerator(DigestFactory.createSHA1());
                    case 2:
                        return new PKCS12ParametersGenerator(new RIPEMD160Digest());
                    case 3:
                        return new PKCS12ParametersGenerator(new TigerDigest());
                    case 4:
                        return new PKCS12ParametersGenerator(DigestFactory.createSHA256());
                    case 5:
                        return new PKCS12ParametersGenerator(new MD2Digest());
                    case 6:
                        return new PKCS12ParametersGenerator(new GOST3411Digest());
                    case 7:
                        return new PKCS12ParametersGenerator(DigestFactory.createSHA224());
                    case 8:
                        return new PKCS12ParametersGenerator(DigestFactory.createSHA384());
                    case 9:
                        return new PKCS12ParametersGenerator(DigestFactory.createSHA512());
                    default:
                        throw new IllegalStateException("unknown digest scheme for PBE encryption.");
                }
            }
            switch (i3) {
                case 0:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createMD5());
                case 1:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA1());
                case 2:
                    return new PKCS5S2ParametersGenerator(new RIPEMD160Digest());
                case 3:
                    return new PKCS5S2ParametersGenerator(new TigerDigest());
                case 4:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA256());
                case 5:
                    return new PKCS5S2ParametersGenerator(new MD2Digest());
                case 6:
                    return new PKCS5S2ParametersGenerator(new GOST3411Digest());
                case 7:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA224());
                case 8:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA384());
                case 9:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA512());
                case 10:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA3_224());
                case 11:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA3_256());
                case 12:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA3_384());
                case 13:
                    return new PKCS5S2ParametersGenerator(DigestFactory.createSHA3_512());
                case 14:
                    return new PKCS5S2ParametersGenerator(new SM3Digest());
                default:
                    throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
            }
        }

        public static CipherParameters makePBEMacParameters(SecretKey secretKey, int i2, int i3, int i4, PBEParameterSpec pBEParameterSpec) {
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i2, i3);
            byte[] encoded = secretKey.getEncoded();
            makePBEGenerator.init(secretKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i4);
            for (int i5 = 0; i5 != encoded.length; i5++) {
                encoded[i5] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static CipherParameters makePBEParameters(PBEKeySpec pBEKeySpec, int i2, int i3, int i4, int i5) {
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i2, i3);
            byte[] convertPassword = convertPassword(i2, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            CipherParameters generateDerivedParameters = i5 != 0 ? makePBEGenerator.generateDerivedParameters(i4, i5) : makePBEGenerator.generateDerivedParameters(i4);
            for (int i6 = 0; i6 != convertPassword.length; i6++) {
                convertPassword[i6] = 0;
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEMacParameters(PBEKeySpec pBEKeySpec, int i2, int i3, int i4) {
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i2, i3);
            byte[] convertPassword = convertPassword(i2, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i4);
            for (int i5 = 0; i5 != convertPassword.length; i5++) {
                convertPassword[i5] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static CipherParameters makePBEParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec, String str) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            byte[] encoded = bCPBEKey.getEncoded();
            if (bCPBEKey.shouldTryWrongPKCS12()) {
                encoded = new byte[2];
            }
            makePBEGenerator.init(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedParameters = bCPBEKey.getIvSize() != 0 ? makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize(), bCPBEKey.getIvSize()) : makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize());
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof ParametersWithIV) {
                    DESParameters.setOddParity(((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                } else {
                    DESParameters.setOddParity(((KeyParameter) generateDerivedParameters).getKey());
                }
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEMacParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            makePBEGenerator.init(bCPBEKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            return makePBEGenerator.generateDerivedMacParameters(bCPBEKey.getKeySize());
        }

        public static CipherParameters makePBEParameters(byte[] bArr, int i2, int i3, int i4, int i5, AlgorithmParameterSpec algorithmParameterSpec, String str) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator makePBEGenerator = makePBEGenerator(i2, i3);
            makePBEGenerator.init(bArr, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedParameters = i5 != 0 ? makePBEGenerator.generateDerivedParameters(i4, i5) : makePBEGenerator.generateDerivedParameters(i4);
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof ParametersWithIV) {
                    DESParameters.setOddParity(((KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                } else {
                    DESParameters.setOddParity(((KeyParameter) generateDerivedParameters).getKey());
                }
            }
            return generateDerivedParameters;
        }
    }
}
