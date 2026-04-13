package org.bouncycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.engines.DSTU7624Engine;
import org.bouncycastle.crypto.fpe.FPEEngine;
import org.bouncycastle.crypto.fpe.FPEFF1Engine;
import org.bouncycastle.crypto.fpe.FPEFF3_1Engine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.GCMSIVBlockCipher;
import org.bouncycastle.crypto.modes.GOFBBlockCipher;
import org.bouncycastle.crypto.modes.KCCMBlockCipher;
import org.bouncycastle.crypto.modes.KCTRBlockCipher;
import org.bouncycastle.crypto.modes.KGCMBlockCipher;
import org.bouncycastle.crypto.modes.OCBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.FPEParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.internal.asn1.cms.GCMParameters;
import org.bouncycastle.jcajce.PBKDF1Key;
import org.bouncycastle.jcajce.PBKDF1KeyWithParameters;
import org.bouncycastle.jcajce.PKCS12Key;
import org.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;
import org.bouncycastle.jcajce.spec.FPEParameterSpec;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.jcajce.spec.RepeatedSecretKeySpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BaseBlockCipher extends BaseWrapCipher {
    private static final int BUF_SIZE = 512;
    private static final Class gcmSpecClass = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.spec.GCMParameterSpec");
    private AEADParameters aeadParams;
    private Class[] availableSpecs;
    private BlockCipher baseEngine;
    private GenericBlockCipher cipher;
    private int digest;
    private BlockCipherProvider engineProvider;
    private boolean fixedIv;
    private int ivLength;
    private ParametersWithIV ivParam;
    private int keySizeInBits;
    private String modeName;
    private boolean padded;
    private String pbeAlgorithm;
    private PBEParameterSpec pbeSpec;
    private int scheme;

    public static class AEADGenericBlockCipher implements GenericBlockCipher {
        private static final Constructor aeadBadTagConstructor;
        private AEADCipher cipher;

        static {
            Class loadClass = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.AEADBadTagException");
            aeadBadTagConstructor = loadClass != null ? findExceptionConstructor(loadClass) : null;
        }

        public AEADGenericBlockCipher(AEADCipher aEADCipher) {
            this.cipher = aEADCipher;
        }

        private static Constructor findExceptionConstructor(Class cls) {
            try {
                return cls.getConstructor(String.class);
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i2) {
            BadPaddingException badPaddingException;
            try {
                return this.cipher.doFinal(bArr, i2);
            } catch (InvalidCipherTextException e2) {
                Constructor constructor = aeadBadTagConstructor;
                if (constructor != null) {
                    try {
                        badPaddingException = (BadPaddingException) constructor.newInstance(e2.getMessage());
                    } catch (Exception unused) {
                        badPaddingException = null;
                    }
                    if (badPaddingException != null) {
                        throw badPaddingException;
                    }
                }
                throw new BadPaddingException(e2.getMessage());
            }
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public String getAlgorithmName() {
            AEADCipher aEADCipher = this.cipher;
            return aEADCipher instanceof AEADBlockCipher ? ((AEADBlockCipher) aEADCipher).getUnderlyingCipher().getAlgorithmName() : aEADCipher.getAlgorithmName();
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getOutputSize(int i2) {
            return this.cipher.getOutputSize(i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public BlockCipher getUnderlyingCipher() {
            AEADCipher aEADCipher = this.cipher;
            if (aEADCipher instanceof AEADBlockCipher) {
                return ((AEADBlockCipher) aEADCipher).getUnderlyingCipher();
            }
            return null;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i2) {
            return this.cipher.getUpdateOutputSize(i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void init(boolean z2, CipherParameters cipherParameters) {
            this.cipher.init(z2, cipherParameters);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processByte(byte b, byte[] bArr, int i2) {
            return this.cipher.processByte(b, bArr, i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void updateAAD(byte[] bArr, int i2, int i3) {
            this.cipher.processAADBytes(bArr, i2, i3);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return false;
        }
    }

    public static class BufferedFPEBlockCipher implements GenericBlockCipher {
        private FPEEngine cipher;
        private BaseWrapCipher.ErasableOutputStream eOut = new BaseWrapCipher.ErasableOutputStream();

        public BufferedFPEBlockCipher(FPEEngine fPEEngine) {
            this.cipher = fPEEngine;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i2) {
            try {
                return this.cipher.processBlock(this.eOut.getBuf(), 0, this.eOut.size(), bArr, i2);
            } finally {
                this.eOut.erase();
            }
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public String getAlgorithmName() {
            return this.cipher.getAlgorithmName();
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getOutputSize(int i2) {
            return this.eOut.size() + i2;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public BlockCipher getUnderlyingCipher() {
            throw new IllegalStateException("not applicable for FPE");
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i2) {
            return 0;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void init(boolean z2, CipherParameters cipherParameters) {
            this.cipher.init(z2, cipherParameters);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processByte(byte b, byte[] bArr, int i2) {
            this.eOut.write(b);
            return 0;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
            this.eOut.write(bArr, i2, i3);
            return 0;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void updateAAD(byte[] bArr, int i2, int i3) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return false;
        }
    }

    public static class BufferedGenericBlockCipher implements GenericBlockCipher {
        private BufferedBlockCipher cipher;

        public BufferedGenericBlockCipher(BlockCipher blockCipher) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i2) {
            try {
                return this.cipher.doFinal(bArr, i2);
            } catch (InvalidCipherTextException e2) {
                throw new BadPaddingException(e2.getMessage());
            }
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getOutputSize(int i2) {
            return this.cipher.getOutputSize(i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i2) {
            return this.cipher.getUpdateOutputSize(i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void init(boolean z2, CipherParameters cipherParameters) {
            this.cipher.init(z2, cipherParameters);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processByte(byte b, byte[] bArr, int i2) {
            return this.cipher.processByte(b, bArr, i2);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void updateAAD(byte[] bArr, int i2, int i3) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return !(this.cipher instanceof CTSBlockCipher);
        }

        public BufferedGenericBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        public BufferedGenericBlockCipher(BufferedBlockCipher bufferedBlockCipher) {
            this.cipher = bufferedBlockCipher;
        }
    }

    public interface GenericBlockCipher {
        int doFinal(byte[] bArr, int i2);

        String getAlgorithmName();

        int getOutputSize(int i2);

        BlockCipher getUnderlyingCipher();

        int getUpdateOutputSize(int i2);

        void init(boolean z2, CipherParameters cipherParameters);

        int processByte(byte b, byte[] bArr, int i2);

        int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4);

        void updateAAD(byte[] bArr, int i2, int i3);

        boolean wrapOnNoPadding();
    }

    public BaseBlockCipher(BlockCipher blockCipher) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private CipherParameters adjustParameters(AlgorithmParameterSpec algorithmParameterSpec, CipherParameters cipherParameters) {
        ParametersWithSBox parametersWithSBox;
        ParametersWithIV parametersWithIV;
        if (cipherParameters instanceof ParametersWithIV) {
            CipherParameters parameters = ((ParametersWithIV) cipherParameters).getParameters();
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                parametersWithIV = new ParametersWithIV(parameters, ((IvParameterSpec) algorithmParameterSpec).getIV());
            } else {
                if (!(algorithmParameterSpec instanceof GOST28147ParameterSpec)) {
                    return cipherParameters;
                }
                GOST28147ParameterSpec gOST28147ParameterSpec = (GOST28147ParameterSpec) algorithmParameterSpec;
                ParametersWithSBox parametersWithSBox2 = new ParametersWithSBox(cipherParameters, gOST28147ParameterSpec.getSbox());
                if (gOST28147ParameterSpec.getIV() == null || this.ivLength == 0) {
                    return parametersWithSBox2;
                }
                parametersWithIV = new ParametersWithIV(parameters, gOST28147ParameterSpec.getIV());
            }
            this.ivParam = parametersWithIV;
            return parametersWithIV;
        }
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            ParametersWithIV parametersWithIV2 = new ParametersWithIV(cipherParameters, ((IvParameterSpec) algorithmParameterSpec).getIV());
            this.ivParam = parametersWithIV2;
            parametersWithSBox = parametersWithIV2;
        } else {
            if (!(algorithmParameterSpec instanceof GOST28147ParameterSpec)) {
                return cipherParameters;
            }
            GOST28147ParameterSpec gOST28147ParameterSpec2 = (GOST28147ParameterSpec) algorithmParameterSpec;
            ParametersWithSBox parametersWithSBox3 = new ParametersWithSBox(cipherParameters, gOST28147ParameterSpec2.getSbox());
            parametersWithSBox = parametersWithSBox3;
            if (gOST28147ParameterSpec2.getIV() != null) {
                parametersWithSBox = parametersWithSBox3;
                if (this.ivLength != 0) {
                    return new ParametersWithIV(parametersWithSBox3, gOST28147ParameterSpec2.getIV());
                }
            }
        }
        return parametersWithSBox;
    }

    private boolean isAEADModeName(String str) {
        return "CCM".equals(str) || "EAX".equals(str) || "GCM".equals(str) || "GCM-SIV".equals(str) || "OCB".equals(str);
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int processBytes;
        if (engineGetOutputSize(i3) + i4 > bArr2.length) {
            throw new ShortBufferException("output buffer too short for input.");
        }
        if (i3 != 0) {
            try {
                processBytes = this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
            } catch (OutputLengthException e2) {
                throw new IllegalBlockSizeException(e2.getMessage());
            } catch (DataLengthException e3) {
                throw new IllegalBlockSizeException(e3.getMessage());
            }
        } else {
            processBytes = 0;
        }
        return processBytes + this.cipher.doFinal(bArr2, i4 + processBytes);
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        BlockCipher blockCipher = this.baseEngine;
        if (blockCipher == null) {
            return -1;
        }
        return blockCipher.getBlockSize();
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        AEADParameters aEADParameters = this.aeadParams;
        if (aEADParameters != null) {
            return aEADParameters.getNonce();
        }
        ParametersWithIV parametersWithIV = this.ivParam;
        if (parametersWithIV != null) {
            return parametersWithIV.getIV();
        }
        return null;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public int engineGetOutputSize(int i2) {
        return this.cipher.getOutputSize(i2);
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    AlgorithmParameters createParametersInstance = createParametersInstance(this.pbeAlgorithm);
                    this.engineParams = createParametersInstance;
                    createParametersInstance.init(this.pbeSpec);
                } catch (Exception unused) {
                    return null;
                }
            } else if (this.aeadParams != null) {
                if (this.baseEngine == null) {
                    try {
                        AlgorithmParameters createParametersInstance2 = createParametersInstance(PKCSObjectIdentifiers.id_alg_AEADChaCha20Poly1305.getId());
                        this.engineParams = createParametersInstance2;
                        createParametersInstance2.init(new DEROctetString(this.aeadParams.getNonce()).getEncoded());
                    } catch (Exception e2) {
                        throw new RuntimeException(e2.toString());
                    }
                } else {
                    try {
                        AlgorithmParameters createParametersInstance3 = createParametersInstance("GCM");
                        this.engineParams = createParametersInstance3;
                        createParametersInstance3.init(new GCMParameters(this.aeadParams.getNonce(), this.aeadParams.getMacSize() / 8).getEncoded());
                    } catch (Exception e3) {
                        throw new RuntimeException(e3.toString());
                    }
                }
            } else if (this.ivParam != null) {
                String algorithmName = this.cipher.getUnderlyingCipher().getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                try {
                    AlgorithmParameters createParametersInstance4 = createParametersInstance(algorithmName);
                    this.engineParams = createParametersInstance4;
                    createParametersInstance4.init(new IvParameterSpec(this.ivParam.getIV()));
                } catch (Exception e4) {
                    throw new RuntimeException(e4.toString());
                }
            }
        }
        return this.engineParams;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters != null) {
            algorithmParameterSpec = SpecUtil.extractSpec(algorithmParameters, this.availableSpecs);
            if (algorithmParameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        } else {
            algorithmParameterSpec = null;
        }
        engineInit(i2, key, algorithmParameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public void engineSetMode(String str) {
        GenericBlockCipher aEADGenericBlockCipher;
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        if (this.baseEngine == null) {
            throw new NoSuchAlgorithmException("no mode supported for this algorithm");
        }
        String upperCase = Strings.toUpperCase(str);
        this.modeName = upperCase;
        if (upperCase.equals("ECB")) {
            this.ivLength = 0;
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(this.baseEngine);
        } else if (this.modeName.equals("CBC")) {
            this.ivLength = this.baseEngine.getBlockSize();
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CBCBlockCipher(this.baseEngine));
        } else if (this.modeName.startsWith("OFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new OFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                this.cipher = bufferedGenericBlockCipher;
                return;
            } else {
                BlockCipher blockCipher = this.baseEngine;
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new OFBBlockCipher(blockCipher, blockCipher.getBlockSize() * 8));
            }
        } else {
            if (!this.modeName.startsWith("CFB")) {
                if (this.modeName.startsWith("PGPCFB")) {
                    boolean equals = this.modeName.equals("PGPCFBWITHIV");
                    if (!equals && this.modeName.length() != 6) {
                        throw new NoSuchAlgorithmException("no mode support for " + this.modeName);
                    }
                    this.ivLength = this.baseEngine.getBlockSize();
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new PGPCFBBlockCipher(this.baseEngine, equals));
                } else if (this.modeName.equals("OPENPGPCFB")) {
                    this.ivLength = 0;
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new OpenPGPCFBBlockCipher(this.baseEngine));
                } else if (this.modeName.equals("FF1")) {
                    this.ivLength = 0;
                    aEADGenericBlockCipher = new BufferedFPEBlockCipher(new FPEFF1Engine(this.baseEngine));
                } else if (this.modeName.equals("FF3-1")) {
                    this.ivLength = 0;
                    aEADGenericBlockCipher = new BufferedFPEBlockCipher(new FPEFF3_1Engine(this.baseEngine));
                } else if (this.modeName.equals("SIC")) {
                    int blockSize = this.baseEngine.getBlockSize();
                    this.ivLength = blockSize;
                    if (blockSize < 16) {
                        throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
                    }
                    this.fixedIv = false;
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
                } else if (this.modeName.equals("CTR")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    this.fixedIv = false;
                    BlockCipher blockCipher2 = this.baseEngine;
                    bufferedGenericBlockCipher = blockCipher2 instanceof DSTU7624Engine ? new BufferedGenericBlockCipher(new BufferedBlockCipher(new KCTRBlockCipher(blockCipher2))) : new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(blockCipher2)));
                } else if (this.modeName.equals("GOFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.baseEngine)));
                } else if (this.modeName.equals("GCFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GCFBBlockCipher(this.baseEngine)));
                } else if (this.modeName.equals("CTS")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CTSBlockCipher(new CBCBlockCipher(this.baseEngine)));
                } else if (this.modeName.equals("CCM")) {
                    this.ivLength = 12;
                    aEADGenericBlockCipher = this.baseEngine instanceof DSTU7624Engine ? new AEADGenericBlockCipher(new KCCMBlockCipher(this.baseEngine)) : new AEADGenericBlockCipher(new CCMBlockCipher(this.baseEngine));
                } else if (this.modeName.equals("OCB")) {
                    if (this.engineProvider == null) {
                        throw new NoSuchAlgorithmException(AbstractC0000a.m15k("can't support mode ", str));
                    }
                    this.ivLength = 15;
                    aEADGenericBlockCipher = new AEADGenericBlockCipher(new OCBBlockCipher(this.baseEngine, this.engineProvider.get()));
                } else if (this.modeName.equals("EAX")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new AEADGenericBlockCipher(new EAXBlockCipher(this.baseEngine));
                } else if (this.modeName.equals("GCM-SIV")) {
                    this.ivLength = 12;
                    aEADGenericBlockCipher = new AEADGenericBlockCipher(new GCMSIVBlockCipher(this.baseEngine));
                } else {
                    if (!this.modeName.equals("GCM")) {
                        throw new NoSuchAlgorithmException(AbstractC0000a.m15k("can't support mode ", str));
                    }
                    BlockCipher blockCipher3 = this.baseEngine;
                    if (blockCipher3 instanceof DSTU7624Engine) {
                        this.ivLength = blockCipher3.getBlockSize();
                        aEADGenericBlockCipher = new AEADGenericBlockCipher(new KGCMBlockCipher(this.baseEngine));
                    } else {
                        this.ivLength = 12;
                        aEADGenericBlockCipher = new AEADGenericBlockCipher(new GCMBlockCipher(this.baseEngine));
                    }
                }
                this.cipher = bufferedGenericBlockCipher;
                return;
            }
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new CFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                this.cipher = bufferedGenericBlockCipher;
                return;
            } else {
                BlockCipher blockCipher4 = this.baseEngine;
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CFBBlockCipher(blockCipher4, blockCipher4.getBlockSize() * 8));
            }
        }
        this.cipher = aEADGenericBlockCipher;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public void engineSetPadding(String str) {
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        if (this.baseEngine == null) {
            throw new NoSuchPaddingException("no padding supported for this algorithm");
        }
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            if (!this.cipher.wrapOnNoPadding()) {
                return;
            } else {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
            }
        } else if (upperCase.equals("WITHCTS") || upperCase.equals("CTSPADDING") || upperCase.equals("CS3PADDING")) {
            bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new CTSBlockCipher(this.cipher.getUnderlyingCipher()));
        } else {
            this.padded = true;
            if (isAEADModeName(this.modeName)) {
                throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            }
            if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
            } else if (upperCase.equals("ZEROBYTEPADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ZeroBytePadding());
            } else if (upperCase.equals("ISO10126PADDING") || upperCase.equals("ISO10126-2PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO10126d2Padding());
            } else if (upperCase.equals("X9.23PADDING") || upperCase.equals("X923PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new X923Padding());
            } else if (upperCase.equals("ISO7816-4PADDING") || upperCase.equals("ISO9797-1PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO7816d4Padding());
            } else {
                if (!upperCase.equals("TBCPADDING")) {
                    throw new NoSuchPaddingException(AbstractC0000a.m16l("Padding ", str, " unknown."));
                }
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new TBCPadding());
            }
        }
        this.cipher = bufferedGenericBlockCipher;
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (this.cipher.getUpdateOutputSize(i3) + i4 > bArr2.length) {
            throw new ShortBufferException("output buffer too short for input.");
        }
        try {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        } catch (DataLengthException e2) {
            throw new IllegalStateException(e2.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining < 1) {
            return;
        }
        if (byteBuffer.hasArray()) {
            engineUpdateAAD(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), remaining);
            byteBuffer.position(byteBuffer.limit());
            return;
        }
        if (remaining <= 512) {
            byte[] bArr = new byte[remaining];
            byteBuffer.get(bArr);
            engineUpdateAAD(bArr, 0, remaining);
            Arrays.fill(bArr, (byte) 0);
            return;
        }
        byte[] bArr2 = new byte[512];
        do {
            int min = Math.min(512, remaining);
            byteBuffer.get(bArr2, 0, min);
            engineUpdateAAD(bArr2, 0, min);
            remaining -= min;
        } while (remaining > 0);
        Arrays.fill(bArr2, (byte) 0);
    }

    public BaseBlockCipher(BlockCipher blockCipher, int i2) {
        this(blockCipher, true, i2);
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        int engineGetOutputSize = engineGetOutputSize(i3);
        byte[] bArr2 = new byte[engineGetOutputSize];
        int processBytes = i3 != 0 ? this.cipher.processBytes(bArr, i2, i3, bArr2, 0) : 0;
        try {
            int doFinal = processBytes + this.cipher.doFinal(bArr2, processBytes);
            if (doFinal == engineGetOutputSize) {
                return bArr2;
            }
            if (doFinal > engineGetOutputSize) {
                throw new IllegalBlockSizeException("internal buffer overflow");
            }
            byte[] bArr3 = new byte[doFinal];
            System.arraycopy(bArr2, 0, bArr3, 0, doFinal);
            return bArr3;
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        int updateOutputSize = this.cipher.getUpdateOutputSize(i3);
        if (updateOutputSize <= 0) {
            this.cipher.processBytes(bArr, i2, i3, null, 0);
            return null;
        }
        byte[] bArr2 = new byte[updateOutputSize];
        int processBytes = this.cipher.processBytes(bArr, i2, i3, bArr2, 0);
        if (processBytes == 0) {
            return null;
        }
        if (processBytes == updateOutputSize) {
            return bArr2;
        }
        byte[] bArr3 = new byte[processBytes];
        System.arraycopy(bArr2, 0, bArr3, 0, processBytes);
        return bArr3;
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(byte[] bArr, int i2, int i3) {
        this.cipher.updateAAD(bArr, i2, i3);
    }

    public BaseBlockCipher(BlockCipher blockCipher, int i2, int i3, int i4, int i5) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.scheme = i2;
        this.digest = i3;
        this.keySizeInBits = i4;
        this.ivLength = i5;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    /* JADX WARN: Code restructure failed: missing block: B:184:0x01fc, code lost:
    
        r21.ivParam = (org.bouncycastle.crypto.params.ParametersWithIV) r6;
        r6 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x00ef, code lost:
    
        if (r8 != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x013e, code lost:
    
        if (r8 != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x01fa, code lost:
    
        if (r8 != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a7, code lost:
    
        if (r8 != false) goto L104;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x044a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x04c7 A[Catch: Exception -> 0x04ee, IllegalArgumentException -> 0x04f9, TryCatch #3 {IllegalArgumentException -> 0x04f9, Exception -> 0x04ee, blocks: (B:71:0x049e, B:72:0x04b4, B:73:0x04b5, B:74:0x04c1, B:76:0x04c7, B:78:0x04cb, B:82:0x04bc), top: B:66:0x0495 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v84, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14, types: [org.bouncycastle.crypto.CipherParameters, org.bouncycastle.crypto.params.RC5Parameters] */
    /* JADX WARN: Type inference failed for: r6v17, types: [org.bouncycastle.crypto.CipherParameters, org.bouncycastle.crypto.params.RC2Parameters] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21, types: [org.bouncycastle.crypto.CipherParameters, org.bouncycastle.crypto.params.ParametersWithSBox] */
    /* JADX WARN: Type inference failed for: r6v24, types: [org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v28, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARN: Type inference failed for: r6v33, types: [org.bouncycastle.crypto.params.AEADParameters] */
    /* JADX WARN: Type inference failed for: r6v55 */
    /* JADX WARN: Type inference failed for: r6v56 */
    /* JADX WARN: Type inference failed for: r6v57 */
    /* JADX WARN: Type inference failed for: r6v58 */
    /* JADX WARN: Type inference failed for: r6v59 */
    /* JADX WARN: Type inference failed for: r6v60 */
    /* JADX WARN: Type inference failed for: r6v61 */
    /* JADX WARN: Type inference failed for: r6v9, types: [org.bouncycastle.crypto.CipherParameters] */
    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        CipherParameters cipherParameters;
        CipherParameters cipherParameters2;
        ParametersWithIV parametersWithIV;
        ParametersWithRandom parametersWithRandom;
        GenericBlockCipher genericBlockCipher;
        CipherParameters cipherParameters3;
        BlockCipher blockCipher;
        KeyParameter keyParameter = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        this.aeadParams = null;
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException(AbstractC0000a.m18n(new StringBuilder("Key for algorithm "), key != null ? key.getAlgorithm() : null, " not suitable for symmetric enryption."));
        }
        if (algorithmParameterSpec == null && (blockCipher = this.baseEngine) != null && blockCipher.getAlgorithmName().startsWith("RC5-64")) {
            throw new InvalidAlgorithmParameterException("RC5 requires an RC5ParametersSpec to be passed in.");
        }
        int i3 = this.scheme;
        if (i3 == 2 || (key instanceof PKCS12Key)) {
            try {
                SecretKey secretKey = (SecretKey) key;
                if (algorithmParameterSpec instanceof PBEParameterSpec) {
                    this.pbeSpec = (PBEParameterSpec) algorithmParameterSpec;
                }
                boolean z2 = secretKey instanceof PBEKey;
                if (z2 && this.pbeSpec == null) {
                    PBEKey pBEKey = (PBEKey) secretKey;
                    if (pBEKey.getSalt() == null) {
                        throw new InvalidAlgorithmParameterException("PBEKey requires parameters to specify salt");
                    }
                    this.pbeSpec = new PBEParameterSpec(pBEKey.getSalt(), pBEKey.getIterationCount());
                }
                if (this.pbeSpec == null && !z2) {
                    throw new InvalidKeyException("Algorithm requires a PBE key");
                }
                if (key instanceof BCPBEKey) {
                    CipherParameters param = ((BCPBEKey) key).getParam();
                    if (!(param instanceof ParametersWithIV)) {
                        if (param != null) {
                            throw new InvalidKeyException("Algorithm requires a PBE key suitable for PKCS12");
                        }
                        param = PBE.Util.makePBEParameters(secretKey.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
                    }
                    cipherParameters = param;
                } else {
                    cipherParameters = PBE.Util.makePBEParameters(secretKey.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
                }
                boolean z3 = cipherParameters instanceof ParametersWithIV;
                keyParameter = cipherParameters;
                cipherParameters2 = cipherParameters;
            } catch (Exception unused) {
                throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
            }
        } else if (key instanceof PBKDF1Key) {
            PBKDF1Key pBKDF1Key = (PBKDF1Key) key;
            if (algorithmParameterSpec instanceof PBEParameterSpec) {
                this.pbeSpec = (PBEParameterSpec) algorithmParameterSpec;
            }
            if ((pBKDF1Key instanceof PBKDF1KeyWithParameters) && this.pbeSpec == null) {
                PBKDF1KeyWithParameters pBKDF1KeyWithParameters = (PBKDF1KeyWithParameters) pBKDF1Key;
                this.pbeSpec = new PBEParameterSpec(pBKDF1KeyWithParameters.getSalt(), pBKDF1KeyWithParameters.getIterationCount());
            }
            CipherParameters makePBEParameters = PBE.Util.makePBEParameters(pBKDF1Key.getEncoded(), 0, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
            boolean z4 = makePBEParameters instanceof ParametersWithIV;
            keyParameter = makePBEParameters;
            cipherParameters2 = makePBEParameters;
        } else if (key instanceof BCPBEKey) {
            BCPBEKey bCPBEKey = (BCPBEKey) key;
            this.pbeAlgorithm = bCPBEKey.getOID() != null ? bCPBEKey.getOID().getId() : bCPBEKey.getAlgorithm();
            if (bCPBEKey.getParam() != null) {
                cipherParameters3 = adjustParameters(algorithmParameterSpec, bCPBEKey.getParam());
            } else {
                if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                }
                this.pbeSpec = (PBEParameterSpec) algorithmParameterSpec;
                cipherParameters3 = PBE.Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.cipher.getUnderlyingCipher().getAlgorithmName());
            }
            boolean z5 = cipherParameters3 instanceof ParametersWithIV;
            keyParameter = cipherParameters3;
            cipherParameters2 = cipherParameters3;
        } else if (key instanceof PBEKey) {
            PBEKey pBEKey2 = (PBEKey) key;
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            this.pbeSpec = pBEParameterSpec;
            if ((pBEKey2 instanceof PKCS12KeyWithParameters) && pBEParameterSpec == null) {
                this.pbeSpec = new PBEParameterSpec(pBEKey2.getSalt(), pBEKey2.getIterationCount());
            }
            CipherParameters makePBEParameters2 = PBE.Util.makePBEParameters(pBEKey2.getEncoded(), this.scheme, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
            boolean z6 = makePBEParameters2 instanceof ParametersWithIV;
            keyParameter = makePBEParameters2;
            cipherParameters2 = makePBEParameters2;
        } else if (!(key instanceof RepeatedSecretKeySpec)) {
            if (i3 == 0 || i3 == 4 || i3 == 1 || i3 == 5) {
                throw new InvalidKeyException("Algorithm requires a PBE key");
            }
            keyParameter = new KeyParameter(key.getEncoded());
        }
        try {
            if (!(algorithmParameterSpec instanceof AEADParameterSpec)) {
                if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                    if (algorithmParameterSpec instanceof GOST28147ParameterSpec) {
                        GOST28147ParameterSpec gOST28147ParameterSpec = (GOST28147ParameterSpec) algorithmParameterSpec;
                        keyParameter = new ParametersWithSBox(new KeyParameter(key.getEncoded()), gOST28147ParameterSpec.getSbox());
                        if (gOST28147ParameterSpec.getIV() != null && this.ivLength != 0) {
                            parametersWithIV = keyParameter instanceof ParametersWithIV ? new ParametersWithIV(((ParametersWithIV) keyParameter).getParameters(), gOST28147ParameterSpec.getIV()) : new ParametersWithIV(keyParameter, gOST28147ParameterSpec.getIV());
                            keyParameter = parametersWithIV;
                        }
                    } else if (algorithmParameterSpec instanceof RC2ParameterSpec) {
                        RC2ParameterSpec rC2ParameterSpec = (RC2ParameterSpec) algorithmParameterSpec;
                        keyParameter = new RC2Parameters(key.getEncoded(), rC2ParameterSpec.getEffectiveKeyBits());
                        if (rC2ParameterSpec.getIV() != null && this.ivLength != 0) {
                            parametersWithIV = keyParameter instanceof ParametersWithIV ? new ParametersWithIV(((ParametersWithIV) keyParameter).getParameters(), rC2ParameterSpec.getIV()) : new ParametersWithIV(keyParameter, rC2ParameterSpec.getIV());
                            keyParameter = parametersWithIV;
                        }
                    } else if (algorithmParameterSpec instanceof RC5ParameterSpec) {
                        RC5ParameterSpec rC5ParameterSpec = (RC5ParameterSpec) algorithmParameterSpec;
                        keyParameter = new RC5Parameters(key.getEncoded(), rC5ParameterSpec.getRounds());
                        if (!this.baseEngine.getAlgorithmName().startsWith("RC5")) {
                            throw new InvalidAlgorithmParameterException("RC5 parameters passed to a cipher that is not RC5.");
                        }
                        if (this.baseEngine.getAlgorithmName().equals("RC5-32")) {
                            if (rC5ParameterSpec.getWordSize() != 32) {
                                throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 32 not " + rC5ParameterSpec.getWordSize() + ".");
                            }
                        } else if (this.baseEngine.getAlgorithmName().equals("RC5-64") && rC5ParameterSpec.getWordSize() != 64) {
                            throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 64 not " + rC5ParameterSpec.getWordSize() + ".");
                        }
                        if (rC5ParameterSpec.getIV() != null && this.ivLength != 0) {
                            parametersWithIV = keyParameter instanceof ParametersWithIV ? new ParametersWithIV(((ParametersWithIV) keyParameter).getParameters(), rC5ParameterSpec.getIV()) : new ParametersWithIV(keyParameter, rC5ParameterSpec.getIV());
                            keyParameter = parametersWithIV;
                        }
                    } else if (algorithmParameterSpec instanceof FPEParameterSpec) {
                        FPEParameterSpec fPEParameterSpec = (FPEParameterSpec) algorithmParameterSpec;
                        keyParameter = new FPEParameters(keyParameter, fPEParameterSpec.getRadix(), fPEParameterSpec.getTweak(), fPEParameterSpec.isUsingInverseFunction());
                    } else {
                        Class cls = gcmSpecClass;
                        if (cls == null || !cls.isInstance(algorithmParameterSpec)) {
                            if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                                throw new InvalidAlgorithmParameterException("unknown parameter type.");
                            }
                        } else {
                            if (!isAEADModeName(this.modeName) && !(this.cipher instanceof AEADGenericBlockCipher)) {
                                throw new InvalidAlgorithmParameterException("GCMParameterSpec can only be used with AEAD modes.");
                            }
                            keyParameter = GcmSpecUtil.extractAeadParameters(keyParameter instanceof ParametersWithIV ? (KeyParameter) ((ParametersWithIV) keyParameter).getParameters() : keyParameter, algorithmParameterSpec);
                        }
                    }
                    this.ivParam = keyParameter;
                } else if (this.ivLength != 0) {
                    IvParameterSpec ivParameterSpec = (IvParameterSpec) algorithmParameterSpec;
                    if (ivParameterSpec.getIV().length != this.ivLength && !(this.cipher instanceof AEADGenericBlockCipher) && this.fixedIv) {
                        throw new InvalidAlgorithmParameterException(AbstractC0000a.m17m(new StringBuilder("IV must be "), this.ivLength, " bytes long."));
                    }
                    keyParameter = keyParameter instanceof ParametersWithIV ? new ParametersWithIV(((ParametersWithIV) keyParameter).getParameters(), ivParameterSpec.getIV()) : new ParametersWithIV(keyParameter, ivParameterSpec.getIV());
                    this.ivParam = keyParameter;
                } else {
                    String str = this.modeName;
                    if (str != null && str.equals("ECB")) {
                        throw new InvalidAlgorithmParameterException("ECB mode does not use an IV");
                    }
                }
                parametersWithRandom = keyParameter;
                if (this.ivLength != 0) {
                    boolean z7 = keyParameter instanceof ParametersWithIV;
                    parametersWithRandom = keyParameter;
                    if (!z7) {
                        boolean z8 = keyParameter instanceof AEADParameters;
                        parametersWithRandom = keyParameter;
                        if (!z8) {
                            SecureRandom secureRandom2 = secureRandom == null ? CryptoServicesRegistrar.getSecureRandom() : secureRandom;
                            if (i2 == 1 || i2 == 3) {
                                byte[] bArr = new byte[this.ivLength];
                                secureRandom2.nextBytes(bArr);
                                ?? parametersWithIV2 = new ParametersWithIV(keyParameter, bArr);
                                this.ivParam = parametersWithIV2;
                                parametersWithRandom = parametersWithIV2;
                            } else {
                                parametersWithRandom = keyParameter;
                                if (this.cipher.getUnderlyingCipher().getAlgorithmName().indexOf("PGPCFB") < 0) {
                                    throw new InvalidAlgorithmParameterException("no IV set when one expected");
                                }
                            }
                        }
                    }
                }
                if (secureRandom != null && this.padded) {
                    parametersWithRandom = new ParametersWithRandom(parametersWithRandom, secureRandom);
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            if (i2 != 4) {
                                throw new InvalidParameterException("unknown opmode " + i2 + " passed");
                            }
                        }
                    }
                    this.cipher.init(false, parametersWithRandom);
                    genericBlockCipher = this.cipher;
                    if ((genericBlockCipher instanceof AEADGenericBlockCipher) && this.aeadParams == null) {
                        this.aeadParams = new AEADParameters((KeyParameter) this.ivParam.getParameters(), ((AEADGenericBlockCipher) genericBlockCipher).cipher.getMac().length * 8, this.ivParam.getIV());
                        return;
                    }
                    return;
                }
                this.cipher.init(true, parametersWithRandom);
                genericBlockCipher = this.cipher;
                if (genericBlockCipher instanceof AEADGenericBlockCipher) {
                    return;
                } else {
                    return;
                }
            }
            if (!isAEADModeName(this.modeName) && !(this.cipher instanceof AEADGenericBlockCipher)) {
                throw new InvalidAlgorithmParameterException("AEADParameterSpec can only be used with AEAD modes.");
            }
            AEADParameterSpec aEADParameterSpec = (AEADParameterSpec) algorithmParameterSpec;
            keyParameter = new AEADParameters(keyParameter instanceof ParametersWithIV ? (KeyParameter) ((ParametersWithIV) keyParameter).getParameters() : keyParameter, aEADParameterSpec.getMacSizeInBits(), aEADParameterSpec.getNonce(), aEADParameterSpec.getAssociatedData());
            if (i2 != 1) {
            }
            this.cipher.init(true, parametersWithRandom);
            genericBlockCipher = this.cipher;
            if (genericBlockCipher instanceof AEADGenericBlockCipher) {
            }
        } catch (IllegalArgumentException e2) {
            throw new InvalidAlgorithmParameterException(e2.getMessage(), e2);
        } catch (Exception e3) {
            throw new BaseWrapCipher.InvalidKeyOrParametersException(e3.getMessage(), e3);
        }
        this.aeadParams = keyParameter;
        parametersWithRandom = keyParameter;
        if (this.ivLength != 0) {
        }
        if (secureRandom != null) {
            parametersWithRandom = new ParametersWithRandom(parametersWithRandom, secureRandom);
        }
    }

    public BaseBlockCipher(BlockCipher blockCipher, boolean z2, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.fixedIv = z2;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
        this.ivLength = i2 / 8;
    }

    public BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, int i2) {
        this(bufferedBlockCipher, true, i2);
    }

    public BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, boolean z2, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = bufferedBlockCipher.getUnderlyingCipher();
        this.cipher = new BufferedGenericBlockCipher(bufferedBlockCipher);
        this.fixedIv = z2;
        this.ivLength = i2 / 8;
    }

    public BaseBlockCipher(AEADBlockCipher aEADBlockCipher) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.ivLength = aEADBlockCipher.getAlgorithmName().indexOf("GCM") >= 0 ? 12 : this.baseEngine.getBlockSize();
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    public BaseBlockCipher(AEADBlockCipher aEADBlockCipher, boolean z2, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.fixedIv = z2;
        this.ivLength = i2;
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    public BaseBlockCipher(AEADCipher aEADCipher, boolean z2, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = null;
        this.fixedIv = z2;
        this.ivLength = i2;
        this.cipher = new AEADGenericBlockCipher(aEADCipher);
    }

    public BaseBlockCipher(BlockCipherProvider blockCipherProvider) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipherProvider.get();
        this.engineProvider = blockCipherProvider;
        this.cipher = new BufferedGenericBlockCipher(blockCipherProvider.get());
    }
}
