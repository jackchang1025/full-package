package org.conscrypt;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class OpenSSLCipher extends CipherSpi {
    private int blockSize;
    byte[] encodedKey;
    private boolean encrypting;
    byte[] iv;
    Mode mode;
    private Padding padding;

    public enum Mode {
        NONE,
        CBC,
        CTR,
        ECB,
        GCM,
        GCM_SIV,
        POLY1305;

        public static Mode getNormalized(String str) {
            String upperCase = str.toUpperCase(Locale.US);
            if (upperCase.equals("GCM-SIV")) {
                return GCM_SIV;
            }
            if (upperCase.equals("GCM_SIV")) {
                throw new IllegalArgumentException("Invalid mode");
            }
            return valueOf(upperCase);
        }
    }

    public enum Padding {
        NOPADDING,
        PKCS5PADDING,
        PKCS7PADDING;

        public static Padding getNormalized(String str) {
            Padding valueOf = valueOf(str.toUpperCase(Locale.US));
            return valueOf == PKCS7PADDING ? PKCS5PADDING : valueOf;
        }
    }

    public OpenSSLCipher() {
        this.mode = Mode.ECB;
        this.padding = Padding.PKCS5PADDING;
    }

    private byte[] checkAndSetEncodedKey(int i2, Key key) {
        if (i2 == 1 || i2 == 3) {
            this.encrypting = true;
        } else {
            if (i2 != 2 && i2 != 4) {
                throw new InvalidParameterException(AbstractC0000a.m11g("Unsupported opmode ", i2));
            }
            this.encrypting = false;
        }
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Only SecretKey is supported");
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        checkSupportedKeySize(encoded.length);
        this.encodedKey = encoded;
        return encoded;
    }

    public abstract void checkSupportedKeySize(int i2);

    public abstract void checkSupportedMode(Mode mode);

    public abstract void checkSupportedPadding(Padding padding);

    public abstract int doFinalInternal(byte[] bArr, int i2, int i3);

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5;
        if (bArr2 == null) {
            throw new NullPointerException("output == null");
        }
        int outputSizeForFinal = getOutputSizeForFinal(i3);
        if (i3 > 0) {
            i5 = updateInternal(bArr, i2, i3, bArr2, i4, outputSizeForFinal);
            i4 += i5;
            outputSizeForFinal -= i5;
        } else {
            i5 = 0;
        }
        return doFinalInternal(bArr2, i4, outputSizeForFinal) + i5;
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return this.blockSize;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        return this.iv;
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) {
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Only SecretKey is supported");
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        checkSupportedKeySize(encoded.length);
        return encoded.length * 8;
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetOutputSize(int i2) {
        return Math.max(getOutputSizeForUpdate(i2), getOutputSizeForFinal(i2));
    }

    @Override // javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        byte[] bArr = this.iv;
        if (bArr != null && bArr.length > 0) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(getBaseCipherName());
                algorithmParameters.init(new IvParameterSpec(this.iv));
                return algorithmParameters;
            } catch (NoSuchAlgorithmException | InvalidParameterSpecException unused) {
            }
        }
        return null;
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        engineInit(i2, key, getParameterSpec(algorithmParameters), secureRandom);
    }

    public abstract void engineInitInternal(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

    @Override // javax.crypto.CipherSpi
    public void engineSetMode(String str) {
        try {
            Mode normalized = Mode.getNormalized(str);
            checkSupportedMode(normalized);
            this.mode = normalized;
        } catch (IllegalArgumentException e2) {
            NoSuchAlgorithmException noSuchAlgorithmException = new NoSuchAlgorithmException(AbstractC0000a.m15k("No such mode: ", str));
            noSuchAlgorithmException.initCause(e2);
            throw noSuchAlgorithmException;
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetPadding(String str) {
        try {
            Padding normalized = Padding.getNormalized(str);
            checkSupportedPadding(normalized);
            this.padding = normalized;
        } catch (IllegalArgumentException e2) {
            NoSuchPaddingException noSuchPaddingException = new NoSuchPaddingException(AbstractC0000a.m15k("No such padding: ", str));
            noSuchPaddingException.initCause(e2);
            throw noSuchPaddingException;
        }
    }

    @Override // javax.crypto.CipherSpi
    public Key engineUnwrap(byte[] bArr, String str, int i2) {
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            if (i2 == 1) {
                return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(engineDoFinal));
            }
            if (i2 == 2) {
                return KeyFactory.getInstance(str).generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
            }
            if (i2 == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            throw new UnsupportedOperationException("wrappedKeyType == " + i2);
        } catch (InvalidKeySpecException e2) {
            throw new InvalidKeyException(e2);
        } catch (BadPaddingException e3) {
            throw new InvalidKeyException(e3);
        } catch (IllegalBlockSizeException e4) {
            throw new InvalidKeyException(e4);
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        return updateInternal(bArr, i2, i3, bArr2, i4, getOutputSizeForUpdate(i3));
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineWrap(Key key) {
        try {
            byte[] encoded = key.getEncoded();
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e2) {
            IllegalBlockSizeException illegalBlockSizeException = new IllegalBlockSizeException();
            illegalBlockSizeException.initCause(e2);
            throw illegalBlockSizeException;
        }
    }

    public abstract String getBaseCipherName();

    public abstract int getCipherBlockSize();

    public abstract int getOutputSizeForFinal(int i2);

    public abstract int getOutputSizeForUpdate(int i2);

    public Padding getPadding() {
        return this.padding;
    }

    public AlgorithmParameterSpec getParameterSpec(AlgorithmParameters algorithmParameters) {
        if (algorithmParameters == null) {
            return null;
        }
        try {
            return algorithmParameters.getParameterSpec(IvParameterSpec.class);
        } catch (InvalidParameterSpecException e2) {
            throw new InvalidAlgorithmParameterException("Params must be convertible to IvParameterSpec", e2);
        }
    }

    public boolean isEncrypting() {
        return this.encrypting;
    }

    public boolean supportsVariableSizeIv() {
        return false;
    }

    public boolean supportsVariableSizeKey() {
        return false;
    }

    public abstract int updateInternal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5);

    public OpenSSLCipher(Mode mode, Padding padding) {
        this.mode = Mode.ECB;
        Padding padding2 = Padding.NOPADDING;
        this.mode = mode;
        this.padding = padding;
        this.blockSize = getCipherBlockSize();
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        int i4;
        int outputSizeForFinal = getOutputSizeForFinal(i3);
        byte[] bArr2 = new byte[outputSizeForFinal];
        if (i3 > 0) {
            try {
                i4 = updateInternal(bArr, i2, i3, bArr2, 0, outputSizeForFinal);
            } catch (ShortBufferException e2) {
                throw new RuntimeException("our calculated buffer was too small", e2);
            }
        } else {
            i4 = 0;
        }
        try {
            int doFinalInternal = i4 + doFinalInternal(bArr2, i4, outputSizeForFinal - i4);
            return doFinalInternal == outputSizeForFinal ? bArr2 : doFinalInternal == 0 ? EmptyArray.BYTE : Arrays.copyOfRange(bArr2, 0, doFinalInternal);
        } catch (ShortBufferException e3) {
            throw new RuntimeException("our calculated buffer was too small", e3);
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        checkAndSetEncodedKey(i2, key);
        try {
            engineInitInternal(this.encodedKey, null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        int outputSizeForUpdate = getOutputSizeForUpdate(i3);
        byte[] bArr2 = outputSizeForUpdate > 0 ? new byte[outputSizeForUpdate] : EmptyArray.BYTE;
        try {
            int updateInternal = updateInternal(bArr, i2, i3, bArr2, 0, outputSizeForUpdate);
            return bArr2.length == updateInternal ? bArr2 : updateInternal == 0 ? EmptyArray.BYTE : Arrays.copyOfRange(bArr2, 0, updateInternal);
        } catch (ShortBufferException unused) {
            throw new RuntimeException(AbstractC0000a.m11g("calculated buffer size was wrong: ", outputSizeForUpdate));
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        checkAndSetEncodedKey(i2, key);
        engineInitInternal(this.encodedKey, algorithmParameterSpec, secureRandom);
    }
}
