package org.conscrypt;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.conscrypt.EvpMdRef;
import org.conscrypt.NativeRef;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OpenSSLSignature extends SignatureSpi {
    private NativeRef.EVP_MD_CTX ctx;
    private final EngineType engineType;
    private final long evpMdRef;
    private long evpPkeyCtx;
    private OpenSSLKey key;
    private boolean signing;
    private final byte[] singleByte;

    /* renamed from: org.conscrypt.OpenSSLSignature$1 */
    public static /* synthetic */ class C08381 {
        static final /* synthetic */ int[] $SwitchMap$org$conscrypt$OpenSSLSignature$EngineType;

        static {
            int[] iArr = new int[EngineType.values().length];
            $SwitchMap$org$conscrypt$OpenSSLSignature$EngineType = iArr;
            try {
                iArr[EngineType.RSA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$conscrypt$OpenSSLSignature$EngineType[EngineType.EC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum EngineType {
        RSA,
        EC
    }

    public static final class MD5RSA extends RSAPKCS1Padding {
        public MD5RSA() {
            super(EvpMdRef.MD5.EVP_MD);
        }
    }

    public static abstract class RSAPKCS1Padding extends OpenSSLSignature {
        public RSAPKCS1Padding(long j2) {
            super(j2, EngineType.RSA, null);
        }

        @Override // org.conscrypt.OpenSSLSignature
        public final void configureEVP_PKEY_CTX(long j2) {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(j2, 1);
        }
    }

    public static abstract class RSAPSSPadding extends OpenSSLSignature {
        private static final int TRAILER_FIELD_BC_ID = 1;
        private final String contentDigestAlgorithm;
        private String mgf1DigestAlgorithm;
        private long mgf1EvpMdRef;
        private int saltSizeBytes;

        public RSAPSSPadding(long j2, String str, int i2) {
            super(j2, EngineType.RSA, null);
            this.contentDigestAlgorithm = str;
            this.mgf1DigestAlgorithm = str;
            this.mgf1EvpMdRef = j2;
            this.saltSizeBytes = i2;
        }

        @Override // org.conscrypt.OpenSSLSignature
        public final void configureEVP_PKEY_CTX(long j2) {
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(j2, 6);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_mgf1_md(j2, this.mgf1EvpMdRef);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_pss_saltlen(j2, this.saltSizeBytes);
        }

        @Override // java.security.SignatureSpi
        public final AlgorithmParameters engineGetParameters() {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PSS");
                algorithmParameters.init(new PSSParameterSpec(this.contentDigestAlgorithm, "MGF1", new MGF1ParameterSpec(this.mgf1DigestAlgorithm), this.saltSizeBytes, 1));
                return algorithmParameters;
            } catch (NoSuchAlgorithmException e2) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", e2);
            } catch (InvalidParameterSpecException e3) {
                throw new ProviderException("Failed to create PSS AlgorithmParameters", e3);
            }
        }

        @Override // java.security.SignatureSpi
        public final void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Unsupported parameter: " + algorithmParameterSpec + ". Only " + PSSParameterSpec.class.getName() + " supported");
            }
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
            String jcaDigestAlgorithmStandardName = EvpMdRef.getJcaDigestAlgorithmStandardName(pSSParameterSpec.getDigestAlgorithm());
            if (jcaDigestAlgorithmStandardName == null) {
                throw new InvalidAlgorithmParameterException("Unsupported content digest algorithm: " + pSSParameterSpec.getDigestAlgorithm());
            }
            if (!this.contentDigestAlgorithm.equalsIgnoreCase(jcaDigestAlgorithmStandardName)) {
                throw new InvalidAlgorithmParameterException("Changing content digest algorithm not supported");
            }
            String mGFAlgorithm = pSSParameterSpec.getMGFAlgorithm();
            if (!"MGF1".equalsIgnoreCase(mGFAlgorithm) && !"1.2.840.113549.1.1.8".equals(mGFAlgorithm)) {
                throw new InvalidAlgorithmParameterException(AbstractC0000a.m16l("Unsupported MGF algorithm: ", mGFAlgorithm, ". Only MGF1 supported"));
            }
            AlgorithmParameterSpec mGFParameters = pSSParameterSpec.getMGFParameters();
            if (!(mGFParameters instanceof MGF1ParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF parameters: " + mGFParameters + ". Only " + MGF1ParameterSpec.class.getName() + " supported");
            }
            MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) pSSParameterSpec.getMGFParameters();
            String jcaDigestAlgorithmStandardName2 = EvpMdRef.getJcaDigestAlgorithmStandardName(mGF1ParameterSpec.getDigestAlgorithm());
            if (jcaDigestAlgorithmStandardName2 == null) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF1 digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
            }
            try {
                long eVP_MDByJcaDigestAlgorithmStandardName = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(jcaDigestAlgorithmStandardName2);
                int saltLength = pSSParameterSpec.getSaltLength();
                if (saltLength < 0) {
                    throw new InvalidAlgorithmParameterException(AbstractC0000a.m11g("Salt length must be non-negative: ", saltLength));
                }
                int trailerField = pSSParameterSpec.getTrailerField();
                if (trailerField != 1) {
                    throw new InvalidAlgorithmParameterException(AbstractC0000a.m12h("Unsupported trailer field: ", trailerField, ". Only 1 supported"));
                }
                this.mgf1DigestAlgorithm = jcaDigestAlgorithmStandardName2;
                this.mgf1EvpMdRef = eVP_MDByJcaDigestAlgorithmStandardName;
                this.saltSizeBytes = saltLength;
                long evp_pkey_ctx = getEVP_PKEY_CTX();
                if (evp_pkey_ctx != 0) {
                    configureEVP_PKEY_CTX(evp_pkey_ctx);
                }
            } catch (NoSuchAlgorithmException e2) {
                throw new ProviderException("Failed to obtain EVP_MD for ".concat(jcaDigestAlgorithmStandardName2), e2);
            }
        }
    }

    public static final class SHA1ECDSA extends OpenSSLSignature {
        public SHA1ECDSA() {
            super(EvpMdRef.SHA1.EVP_MD, EngineType.EC, null);
        }
    }

    public static final class SHA1RSA extends RSAPKCS1Padding {
        public SHA1RSA() {
            super(EvpMdRef.SHA1.EVP_MD);
        }
    }

    public static final class SHA1RSAPSS extends RSAPSSPadding {
        public SHA1RSAPSS() {
            super(EvpMdRef.SHA1.EVP_MD, McElieceCCA2KeyGenParameterSpec.SHA1, EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class SHA224ECDSA extends OpenSSLSignature {
        public SHA224ECDSA() {
            super(EvpMdRef.SHA224.EVP_MD, EngineType.EC, null);
        }
    }

    public static final class SHA224RSA extends RSAPKCS1Padding {
        public SHA224RSA() {
            super(EvpMdRef.SHA224.EVP_MD);
        }
    }

    public static final class SHA224RSAPSS extends RSAPSSPadding {
        public SHA224RSAPSS() {
            super(EvpMdRef.SHA224.EVP_MD, McElieceCCA2KeyGenParameterSpec.SHA224, EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class SHA256ECDSA extends OpenSSLSignature {
        public SHA256ECDSA() {
            super(EvpMdRef.SHA256.EVP_MD, EngineType.EC, null);
        }
    }

    public static final class SHA256RSA extends RSAPKCS1Padding {
        public SHA256RSA() {
            super(EvpMdRef.SHA256.EVP_MD);
        }
    }

    public static final class SHA256RSAPSS extends RSAPSSPadding {
        public SHA256RSAPSS() {
            super(EvpMdRef.SHA256.EVP_MD, "SHA-256", EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class SHA384ECDSA extends OpenSSLSignature {
        public SHA384ECDSA() {
            super(EvpMdRef.SHA384.EVP_MD, EngineType.EC, null);
        }
    }

    public static final class SHA384RSA extends RSAPKCS1Padding {
        public SHA384RSA() {
            super(EvpMdRef.SHA384.EVP_MD);
        }
    }

    public static final class SHA384RSAPSS extends RSAPSSPadding {
        public SHA384RSAPSS() {
            super(EvpMdRef.SHA384.EVP_MD, McElieceCCA2KeyGenParameterSpec.SHA384, EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class SHA512ECDSA extends OpenSSLSignature {
        public SHA512ECDSA() {
            super(EvpMdRef.SHA512.EVP_MD, EngineType.EC, null);
        }
    }

    public static final class SHA512RSA extends RSAPKCS1Padding {
        public SHA512RSA() {
            super(EvpMdRef.SHA512.EVP_MD);
        }
    }

    public static final class SHA512RSAPSS extends RSAPSSPadding {
        public SHA512RSAPSS() {
            super(EvpMdRef.SHA512.EVP_MD, "SHA-512", EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

    private OpenSSLSignature(long j2, EngineType engineType) {
        this.singleByte = new byte[1];
        this.engineType = engineType;
        this.evpMdRef = j2;
    }

    private void checkEngineType(OpenSSLKey openSSLKey) {
        int EVP_PKEY_type = NativeCrypto.EVP_PKEY_type(openSSLKey.getNativeRef());
        int i2 = C08381.$SwitchMap$org$conscrypt$OpenSSLSignature$EngineType[this.engineType.ordinal()];
        if (i2 == 1) {
            if (EVP_PKEY_type == 6) {
                return;
            }
            throw new InvalidKeyException("Signature initialized as " + this.engineType + " (not RSA)");
        }
        if (i2 != 2) {
            throw new InvalidKeyException("Key must be of type " + this.engineType);
        }
        if (EVP_PKEY_type == 408) {
            return;
        }
        throw new InvalidKeyException("Signature initialized as " + this.engineType + " (not EC)");
    }

    private void initInternal(OpenSSLKey openSSLKey, boolean z2) {
        checkEngineType(openSSLKey);
        this.key = openSSLKey;
        this.signing = z2;
        try {
            resetContext();
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2);
        }
    }

    private void resetContext() {
        NativeRef.EVP_MD_CTX evp_md_ctx = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        this.evpPkeyCtx = this.signing ? NativeCrypto.EVP_DigestSignInit(evp_md_ctx, this.evpMdRef, this.key.getNativeRef()) : NativeCrypto.EVP_DigestVerifyInit(evp_md_ctx, this.evpMdRef, this.key.getNativeRef());
        configureEVP_PKEY_CTX(this.evpPkeyCtx);
        this.ctx = evp_md_ctx;
    }

    public void configureEVP_PKEY_CTX(long j2) {
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    public Object engineGetParameter(String str) {
        return null;
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey) {
        initInternal(OpenSSLKey.fromPrivateKey(privateKey), true);
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) {
        initInternal(OpenSSLKey.fromPublicKey(publicKey), false);
    }

    @Override // java.security.SignatureSpi
    @Deprecated
    public void engineSetParameter(String str, Object obj) {
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() {
        try {
            try {
                byte[] EVP_DigestSignFinal = NativeCrypto.EVP_DigestSignFinal(this.ctx);
                try {
                    resetContext();
                    return EVP_DigestSignFinal;
                } catch (InvalidAlgorithmParameterException unused) {
                    throw new AssertionError("Reset of context failed after it was successful once");
                }
            } catch (Throwable th) {
                try {
                    resetContext();
                    throw th;
                } catch (InvalidAlgorithmParameterException unused2) {
                    throw new AssertionError("Reset of context failed after it was successful once");
                }
            }
        } catch (Exception e2) {
            throw new SignatureException(e2);
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte b) {
        byte[] bArr = this.singleByte;
        bArr[0] = b;
        engineUpdate(bArr, 0, 1);
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) {
        try {
            try {
                boolean EVP_DigestVerifyFinal = NativeCrypto.EVP_DigestVerifyFinal(this.ctx, bArr, 0, bArr.length);
                try {
                    resetContext();
                    return EVP_DigestVerifyFinal;
                } catch (InvalidAlgorithmParameterException unused) {
                    throw new AssertionError("Reset of context failed after it was successful once");
                }
            } catch (Throwable th) {
                try {
                    resetContext();
                    throw th;
                } catch (InvalidAlgorithmParameterException unused2) {
                    throw new AssertionError("Reset of context failed after it was successful once");
                }
            }
        } catch (Exception e2) {
            throw new SignatureException(e2);
        }
    }

    public final long getEVP_PKEY_CTX() {
        return this.evpPkeyCtx;
    }

    public /* synthetic */ OpenSSLSignature(long j2, EngineType engineType, C08381 c08381) {
        this(j2, engineType);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(ByteBuffer byteBuffer) {
        if (byteBuffer.hasRemaining()) {
            if (!byteBuffer.isDirect()) {
                super.engineUpdate(byteBuffer);
                return;
            }
            long directBufferAddress = NativeCrypto.getDirectBufferAddress(byteBuffer);
            if (directBufferAddress == 0) {
                super.engineUpdate(byteBuffer);
                return;
            }
            int position = byteBuffer.position();
            if (position < 0) {
                throw new RuntimeException("Negative position");
            }
            long j2 = directBufferAddress + position;
            int remaining = byteBuffer.remaining();
            if (remaining < 0) {
                throw new RuntimeException("Negative remaining amount");
            }
            NativeRef.EVP_MD_CTX evp_md_ctx = this.ctx;
            if (this.signing) {
                NativeCrypto.EVP_DigestSignUpdateDirect(evp_md_ctx, j2, remaining);
            } else {
                NativeCrypto.EVP_DigestVerifyUpdateDirect(evp_md_ctx, j2, remaining);
            }
            byteBuffer.position(position + remaining);
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        NativeRef.EVP_MD_CTX evp_md_ctx = this.ctx;
        if (this.signing) {
            NativeCrypto.EVP_DigestSignUpdate(evp_md_ctx, bArr, i2, i3);
        } else {
            NativeCrypto.EVP_DigestVerifyUpdate(evp_md_ctx, bArr, i2, i3);
        }
    }
}
