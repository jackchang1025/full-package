package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLCipher;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class OpenSSLEvpCipher extends OpenSSLCipher {
    private boolean calledUpdate;
    private final NativeRef.EVP_CIPHER_CTX cipherCtx;
    private int modeBlockSize;

    public OpenSSLEvpCipher(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
        this.cipherCtx = new NativeRef.EVP_CIPHER_CTX(NativeCrypto.EVP_CIPHER_CTX_new());
    }

    private void reset() {
        NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, this.encodedKey, this.iv, isEncrypting());
        this.calledUpdate = false;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int doFinalInternal(byte[] bArr, int i2, int i3) {
        int i4;
        if (!isEncrypting() && !this.calledUpdate) {
            return 0;
        }
        int length = bArr.length - i2;
        if (length >= i3) {
            i4 = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, bArr, i2);
        } else {
            byte[] bArr2 = new byte[i3];
            int EVP_CipherFinal_ex = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, bArr2, 0);
            if (EVP_CipherFinal_ex > length) {
                throw new ShortBufferWithoutStackTraceException("buffer is too short: " + EVP_CipherFinal_ex + " > " + length);
            }
            if (EVP_CipherFinal_ex > 0) {
                System.arraycopy(bArr2, 0, bArr, i2, EVP_CipherFinal_ex);
            }
            i4 = EVP_CipherFinal_ex;
        }
        reset();
        return (i4 + i2) - i2;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public void engineInitInternal(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        byte[] iv = algorithmParameterSpec instanceof IvParameterSpec ? ((IvParameterSpec) algorithmParameterSpec).getIV() : null;
        long EVP_get_cipherbyname = NativeCrypto.EVP_get_cipherbyname(getCipherName(bArr.length, this.mode));
        if (EVP_get_cipherbyname == 0) {
            throw new InvalidAlgorithmParameterException("Cannot find name for key length = " + (bArr.length * 8) + " and mode = " + this.mode);
        }
        boolean isEncrypting = isEncrypting();
        int EVP_CIPHER_iv_length = NativeCrypto.EVP_CIPHER_iv_length(EVP_get_cipherbyname);
        if (iv != null || EVP_CIPHER_iv_length == 0) {
            if (EVP_CIPHER_iv_length == 0 && iv != null) {
                throw new InvalidAlgorithmParameterException("IV not used in " + this.mode + " mode");
            }
            if (iv != null && iv.length != EVP_CIPHER_iv_length) {
                StringBuilder m21q = AbstractC0000a.m21q("expected IV length of ", EVP_CIPHER_iv_length, " but was ");
                m21q.append(iv.length);
                throw new InvalidAlgorithmParameterException(m21q.toString());
            }
        } else {
            if (!isEncrypting) {
                throw new InvalidAlgorithmParameterException("IV must be specified in " + this.mode + " mode");
            }
            iv = new byte[EVP_CIPHER_iv_length];
            if (secureRandom != null) {
                secureRandom.nextBytes(iv);
            } else {
                NativeCrypto.RAND_bytes(iv);
            }
        }
        this.iv = iv;
        boolean supportsVariableSizeKey = supportsVariableSizeKey();
        NativeRef.EVP_CIPHER_CTX evp_cipher_ctx = this.cipherCtx;
        if (supportsVariableSizeKey) {
            NativeCrypto.EVP_CipherInit_ex(evp_cipher_ctx, EVP_get_cipherbyname, null, null, isEncrypting);
            NativeCrypto.EVP_CIPHER_CTX_set_key_length(this.cipherCtx, bArr.length);
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, bArr, iv, isEncrypting());
        } else {
            NativeCrypto.EVP_CipherInit_ex(evp_cipher_ctx, EVP_get_cipherbyname, bArr, iv, isEncrypting);
        }
        NativeCrypto.EVP_CIPHER_CTX_set_padding(this.cipherCtx, getPadding() == OpenSSLCipher.Padding.PKCS5PADDING);
        this.modeBlockSize = NativeCrypto.EVP_CIPHER_CTX_block_size(this.cipherCtx);
        this.calledUpdate = false;
    }

    public abstract String getCipherName(int i2, OpenSSLCipher.Mode mode);

    @Override // org.conscrypt.OpenSSLCipher
    public int getOutputSizeForFinal(int i2) {
        if (this.modeBlockSize == 1) {
            return i2;
        }
        int i3 = NativeCrypto.get_EVP_CIPHER_CTX_buf_len(this.cipherCtx);
        if (getPadding() == OpenSSLCipher.Padding.NOPADDING) {
            return i3 + i2;
        }
        int i4 = i2 + i3 + (NativeCrypto.get_EVP_CIPHER_CTX_final_used(this.cipherCtx) ? this.modeBlockSize : 0);
        int i5 = i4 + ((i4 % this.modeBlockSize != 0 || isEncrypting()) ? this.modeBlockSize : 0);
        return i5 - (i5 % this.modeBlockSize);
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int getOutputSizeForUpdate(int i2) {
        return getOutputSizeForFinal(i2);
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int updateInternal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        int length = bArr2.length - i4;
        if (length >= i5) {
            int EVP_CipherUpdate = NativeCrypto.EVP_CipherUpdate(this.cipherCtx, bArr2, i4, bArr, i2, i3) + i4;
            this.calledUpdate = true;
            return EVP_CipherUpdate - i4;
        }
        throw new ShortBufferWithoutStackTraceException("output buffer too small during update: " + length + " < " + i5);
    }
}
