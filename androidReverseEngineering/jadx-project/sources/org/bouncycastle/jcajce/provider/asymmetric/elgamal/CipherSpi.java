package org.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.ISO9796d1Encoding;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.ElGamalEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.bouncycastle.jcajce.provider.util.BadBlockException;
import org.bouncycastle.jcajce.provider.util.DigestFactory;
import org.bouncycastle.jce.interfaces.ElGamalKey;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CipherSpi extends BaseCipherSpi {
    private BaseCipherSpi.ErasableOutputStream bOut = new BaseCipherSpi.ErasableOutputStream();
    private AsymmetricBlockCipher cipher;
    private AlgorithmParameters engineParams;
    private AlgorithmParameterSpec paramSpec;

    public static class NoPadding extends CipherSpi {
        public NoPadding() {
            super(new ElGamalEngine());
        }
    }

    public static class PKCS1v1_5Padding extends CipherSpi {
        public PKCS1v1_5Padding() {
            super(new PKCS1Encoding(new ElGamalEngine()));
        }
    }

    public CipherSpi(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.cipher = asymmetricBlockCipher;
    }

    private byte[] getOutput() {
        try {
            try {
                try {
                    return this.cipher.processBlock(this.bOut.getBuf(), 0, this.bOut.size());
                } catch (ArrayIndexOutOfBoundsException e2) {
                    throw new BadBlockException("unable to decrypt block", e2);
                }
            } catch (InvalidCipherTextException e3) {
                throw new BadBlockException("unable to decrypt block", e3);
            }
        } finally {
            this.bOut.erase();
        }
    }

    private void initFromSpec(OAEPParameterSpec oAEPParameterSpec) {
        MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
        Digest digest = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
        if (digest != null) {
            this.cipher = new OAEPEncoding(new ElGamalEngine(), digest, ((PSource.PSpecified) oAEPParameterSpec.getPSource()).getValue());
            this.paramSpec = oAEPParameterSpec;
        } else {
            throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (engineGetOutputSize(i3) + i4 > bArr2.length) {
            throw new ShortBufferException("output buffer too short for input.");
        }
        if (bArr != null) {
            this.bOut.write(bArr, i2, i3);
        }
        if (this.cipher instanceof ElGamalEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for ElGamal block");
            }
        } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for ElGamal block");
        }
        byte[] output = getOutput();
        for (int i5 = 0; i5 != output.length; i5++) {
            bArr2[i4 + i5] = output[i5];
        }
        return output.length;
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return this.cipher.getInputBlockSize();
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) {
        BigInteger p2;
        if (key instanceof ElGamalKey) {
            p2 = ((ElGamalKey) key).getParameters().getP();
        } else {
            if (!(key instanceof DHKey)) {
                throw new IllegalArgumentException("not an ElGamal key!");
            }
            p2 = ((DHKey) key).getParams().getP();
        }
        return p2.bitLength();
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public int engineGetOutputSize(int i2) {
        return this.cipher.getOutputBlockSize();
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.paramSpec != null) {
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance("OAEP");
                this.engineParams = createParametersInstance;
                createParametersInstance.init(this.paramSpec);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.engineParams;
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        throw new InvalidAlgorithmParameterException("can't handle parameters in ElGamal");
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public void engineSetMode(String str) {
        String upperCase = Strings.toUpperCase(str);
        if (!upperCase.equals("NONE") && !upperCase.equals("ECB")) {
            throw new NoSuchAlgorithmException(AbstractC0000a.m15k("can't support mode ", str));
        }
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    public void engineSetPadding(String str) {
        OAEPParameterSpec oAEPParameterSpec;
        AsymmetricBlockCipher iSO9796d1Encoding;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            iSO9796d1Encoding = new ElGamalEngine();
        } else if (upperCase.equals("PKCS1PADDING")) {
            iSO9796d1Encoding = new PKCS1Encoding(new ElGamalEngine());
        } else {
            if (!upperCase.equals("ISO9796-1PADDING")) {
                if (!upperCase.equals("OAEPPADDING")) {
                    if (upperCase.equals("OAEPWITHMD5ANDMGF1PADDING")) {
                        oAEPParameterSpec = new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSource.PSpecified.DEFAULT);
                    } else if (!upperCase.equals("OAEPWITHSHA1ANDMGF1PADDING")) {
                        if (upperCase.equals("OAEPWITHSHA224ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec(McElieceCCA2KeyGenParameterSpec.SHA224, "MGF1", new MGF1ParameterSpec(McElieceCCA2KeyGenParameterSpec.SHA224), PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA256ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA384ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec(McElieceCCA2KeyGenParameterSpec.SHA384, "MGF1", MGF1ParameterSpec.SHA384, PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA512ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA3-224ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA3-256ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), PSource.PSpecified.DEFAULT);
                        } else if (upperCase.equals("OAEPWITHSHA3-384ANDMGF1PADDING")) {
                            oAEPParameterSpec = new OAEPParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), PSource.PSpecified.DEFAULT);
                        } else {
                            if (!upperCase.equals("OAEPWITHSHA3-512ANDMGF1PADDING")) {
                                throw new NoSuchPaddingException(AbstractC0000a.m30z(str, " unavailable with ElGamal."));
                            }
                            oAEPParameterSpec = new OAEPParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), PSource.PSpecified.DEFAULT);
                        }
                    }
                    initFromSpec(oAEPParameterSpec);
                    return;
                }
                oAEPParameterSpec = OAEPParameterSpec.DEFAULT;
                initFromSpec(oAEPParameterSpec);
                return;
            }
            iSO9796d1Encoding = new ISO9796d1Encoding(new ElGamalEngine());
        }
        this.cipher = iSO9796d1Encoding;
    }

    @Override // javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        this.bOut.write(bArr, i2, i3);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            this.bOut.write(bArr, i2, i3);
        }
        if (this.cipher instanceof ElGamalEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for ElGamal block");
            }
        } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for ElGamal block");
        }
        return getOutput();
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException("Eeeek! " + e2.toString(), e2);
        }
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        this.bOut.write(bArr, i2, i3);
        return null;
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        CipherParameters generatePrivateKeyParameter;
        AsymmetricBlockCipher asymmetricBlockCipher;
        if (key instanceof DHPublicKey) {
            generatePrivateKeyParameter = ElGamalUtil.generatePublicKeyParameter((PublicKey) key);
        } else {
            if (!(key instanceof DHPrivateKey)) {
                throw new InvalidKeyException("unknown key type passed to ElGamal");
            }
            generatePrivateKeyParameter = ElGamalUtil.generatePrivateKeyParameter((PrivateKey) key);
        }
        if (algorithmParameterSpec instanceof OAEPParameterSpec) {
            OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameterSpec;
            this.paramSpec = algorithmParameterSpec;
            if (!oAEPParameterSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !oAEPParameterSpec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
                throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
            }
            if (!(oAEPParameterSpec.getMGFParameters() instanceof MGF1ParameterSpec)) {
                throw new InvalidAlgorithmParameterException("unkown MGF parameters");
            }
            Digest digest = DigestFactory.getDigest(oAEPParameterSpec.getDigestAlgorithm());
            if (digest == null) {
                throw new InvalidAlgorithmParameterException("no match on digest algorithm: " + oAEPParameterSpec.getDigestAlgorithm());
            }
            MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
            Digest digest2 = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
            if (digest2 == null) {
                throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
            }
            this.cipher = new OAEPEncoding(new ElGamalEngine(), digest, digest2, ((PSource.PSpecified) oAEPParameterSpec.getPSource()).getValue());
        } else if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("unknown parameter type.");
        }
        if (secureRandom != null) {
            generatePrivateKeyParameter = new ParametersWithRandom(generatePrivateKeyParameter, secureRandom);
        }
        boolean z2 = true;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        throw new InvalidParameterException(AbstractC0000a.m12h("unknown opmode ", i2, " passed to ElGamal"));
                    }
                }
            }
            asymmetricBlockCipher = this.cipher;
            z2 = false;
            asymmetricBlockCipher.init(z2, generatePrivateKeyParameter);
        }
        asymmetricBlockCipher = this.cipher;
        asymmetricBlockCipher.init(z2, generatePrivateKeyParameter);
    }
}
