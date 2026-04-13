package org.conscrypt;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import org.conscrypt.OpenSSLCipher;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class OpenSSLAeadCipher extends OpenSSLCipher {
    static final int DEFAULT_TAG_SIZE_BITS = 128;
    private static final boolean ENABLE_BYTEBUFFER_OPTIMIZATIONS = true;
    private static int lastGlobalMessageSize = 32;
    private byte[] aad;
    byte[] buf;
    int bufCount;
    long evpAead;
    private boolean mustInitialize;
    private byte[] previousIv;
    private byte[] previousKey;
    int tagLengthInBytes;

    public OpenSSLAeadCipher(OpenSSLCipher.Mode mode) {
        super(mode, OpenSSLCipher.Padding.NOPADDING);
    }

    private boolean arraysAreEqual(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i2 |= bArr[i3] ^ bArr2[i3];
        }
        if (i2 == 0) {
            return ENABLE_BYTEBUFFER_OPTIMIZATIONS;
        }
        return false;
    }

    private void checkInitialization() {
        if (this.mustInitialize) {
            throw new IllegalStateException("Cannot re-use same key and IV for multiple encryptions");
        }
    }

    private void expand(int i2) {
        int i3 = this.bufCount;
        int i4 = i3 + i2;
        byte[] bArr = this.buf;
        if (i4 <= bArr.length) {
            return;
        }
        byte[] bArr2 = new byte[(i2 + i3) * 2];
        System.arraycopy(bArr, 0, bArr2, 0, i3);
        this.buf = bArr2;
    }

    private void reset() {
        this.aad = null;
        int i2 = lastGlobalMessageSize;
        byte[] bArr = this.buf;
        if (bArr == null) {
            this.buf = new byte[i2];
        } else {
            int i3 = this.bufCount;
            if (i3 > 0 && i3 != i2) {
                lastGlobalMessageSize = i3;
                if (bArr.length != i3) {
                    this.buf = new byte[i3];
                }
            }
        }
        this.bufCount = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void throwAEADBadTagExceptionIfAvailable(String str, Throwable th) {
        BadPaddingException badPaddingException;
        try {
            BadPaddingException badPaddingException2 = null;
            try {
                try {
                    badPaddingException = (BadPaddingException) Class.forName("javax.crypto.AEADBadTagException").getConstructor(String.class).newInstance(str);
                    try {
                        badPaddingException.initCause(th);
                    } catch (IllegalAccessException | InstantiationException unused) {
                        badPaddingException2 = badPaddingException;
                        badPaddingException = badPaddingException2;
                        if (badPaddingException == null) {
                        }
                    }
                } catch (InvocationTargetException e2) {
                    throw ((BadPaddingException) new BadPaddingException().initCause(e2.getTargetException()));
                }
            } catch (IllegalAccessException | InstantiationException unused2) {
            }
            if (badPaddingException == null) {
                throw badPaddingException;
            }
        } catch (Exception unused3) {
        }
    }

    public boolean allowsNonceReuse() {
        return false;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public void checkSupportedPadding(OpenSSLCipher.Padding padding) {
        if (padding != OpenSSLCipher.Padding.NOPADDING) {
            throw new NoSuchPaddingException("Must be NoPadding for AEAD ciphers");
        }
    }

    public void checkSupportedTagLength(int i2) {
        if (i2 % 8 != 0) {
            throw new InvalidAlgorithmParameterException(AbstractC0000a.m11g("Tag length must be a multiple of 8; was ", i2));
        }
    }

    public int doFinalInternal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        checkInitialization();
        try {
            int EVP_AEAD_CTX_seal_buf = isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, byteBuffer2, this.iv, byteBuffer, this.aad) : NativeCrypto.EVP_AEAD_CTX_open_buf(this.evpAead, this.encodedKey, this.tagLengthInBytes, byteBuffer2, this.iv, byteBuffer, this.aad);
            if (isEncrypting()) {
                this.mustInitialize = ENABLE_BYTEBUFFER_OPTIMIZATIONS;
            }
            return EVP_AEAD_CTX_seal_buf;
        } catch (BadPaddingException e2) {
            throwAEADBadTagExceptionIfAvailable(e2.getMessage(), e2.getCause());
            throw e2;
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int doFinalInternal;
        if (byteBuffer == null || byteBuffer2 == null) {
            throw new NullPointerException("Null ByteBuffer Error");
        }
        if (getOutputSizeForFinal(byteBuffer.remaining()) > byteBuffer2.remaining()) {
            throw new ShortBufferWithoutStackTraceException("Insufficient Bytes for Output Buffer");
        }
        if (byteBuffer2.isReadOnly()) {
            throw new IllegalArgumentException("Cannot write to Read Only ByteBuffer");
        }
        if (this.bufCount != 0) {
            return super.engineDoFinal(byteBuffer, byteBuffer2);
        }
        if (!byteBuffer.isDirect()) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.remaining());
            allocateDirect.mark();
            allocateDirect.put(byteBuffer);
            allocateDirect.reset();
            byteBuffer = allocateDirect;
        }
        if (byteBuffer2.isDirect()) {
            doFinalInternal = doFinalInternal(byteBuffer, byteBuffer2);
            byteBuffer2.position(byteBuffer2.position() + doFinalInternal);
        } else {
            ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(getOutputSizeForFinal(byteBuffer.remaining()));
            doFinalInternal = doFinalInternal(byteBuffer, allocateDirect2);
            byteBuffer2.put(allocateDirect2);
        }
        byteBuffer.position(byteBuffer.limit());
        return doFinalInternal;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public void engineInitInternal(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        byte[] bArr2 = null;
        int i2 = 128;
        if (algorithmParameterSpec != null) {
            GCMParameters fromGCMParameterSpec = Platform.fromGCMParameterSpec(algorithmParameterSpec);
            if (fromGCMParameterSpec != null) {
                bArr2 = fromGCMParameterSpec.getIV();
                i2 = fromGCMParameterSpec.getTLen();
            } else if (algorithmParameterSpec instanceof IvParameterSpec) {
                bArr2 = ((IvParameterSpec) algorithmParameterSpec).getIV();
            }
        }
        checkSupportedTagLength(i2);
        this.tagLengthInBytes = i2 / 8;
        boolean isEncrypting = isEncrypting();
        long evp_aead = getEVP_AEAD(bArr.length);
        this.evpAead = evp_aead;
        int EVP_AEAD_nonce_length = NativeCrypto.EVP_AEAD_nonce_length(evp_aead);
        if (bArr2 != null || EVP_AEAD_nonce_length == 0) {
            if (EVP_AEAD_nonce_length == 0 && bArr2 != null) {
                throw new InvalidAlgorithmParameterException("IV not used in " + this.mode + " mode");
            }
            if (bArr2 != null && bArr2.length != EVP_AEAD_nonce_length) {
                StringBuilder m21q = AbstractC0000a.m21q("Expected IV length of ", EVP_AEAD_nonce_length, " but was ");
                m21q.append(bArr2.length);
                throw new InvalidAlgorithmParameterException(m21q.toString());
            }
        } else {
            if (!isEncrypting) {
                throw new InvalidAlgorithmParameterException("IV must be specified in " + this.mode + " mode");
            }
            bArr2 = new byte[EVP_AEAD_nonce_length];
            if (secureRandom != null) {
                secureRandom.nextBytes(bArr2);
            } else {
                NativeCrypto.RAND_bytes(bArr2);
            }
        }
        if (isEncrypting() && bArr2 != null && !allowsNonceReuse()) {
            byte[] bArr3 = this.previousKey;
            if (bArr3 != null && this.previousIv != null && arraysAreEqual(bArr3, bArr) && arraysAreEqual(this.previousIv, bArr2)) {
                this.mustInitialize = ENABLE_BYTEBUFFER_OPTIMIZATIONS;
                throw new InvalidAlgorithmParameterException("When using AEAD key and IV must not be re-used");
            }
            this.previousKey = bArr;
            this.previousIv = bArr2;
        }
        this.mustInitialize = false;
        this.iv = bArr2;
        reset();
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(ByteBuffer byteBuffer) {
        checkInitialization();
        byte[] bArr = this.aad;
        if (bArr == null) {
            byte[] bArr2 = new byte[byteBuffer.remaining()];
            this.aad = bArr2;
            byteBuffer.get(bArr2);
        } else {
            byte[] bArr3 = new byte[byteBuffer.remaining() + bArr.length];
            byte[] bArr4 = this.aad;
            System.arraycopy(bArr4, 0, bArr3, 0, bArr4.length);
            byteBuffer.get(bArr3, this.aad.length, byteBuffer.remaining());
            this.aad = bArr3;
        }
    }

    public abstract long getEVP_AEAD(int i2);

    @Override // org.conscrypt.OpenSSLCipher
    public int getOutputSizeForFinal(int i2) {
        return this.bufCount + i2 + (isEncrypting() ? NativeCrypto.EVP_AEAD_max_overhead(this.evpAead) : 0);
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int getOutputSizeForUpdate(int i2) {
        return 0;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int updateInternal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        checkInitialization();
        if (this.buf == null) {
            throw new IllegalStateException("Cipher not initialized");
        }
        ArrayUtils.checkOffsetAndCount(bArr.length, i2, i3);
        if (i3 <= 0) {
            return 0;
        }
        expand(i3);
        System.arraycopy(bArr, i2, this.buf, this.bufCount, i3);
        this.bufCount += i3;
        return 0;
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int doFinalInternal(byte[] bArr, int i2, int i3) {
        checkInitialization();
        try {
            int EVP_AEAD_CTX_seal = isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal(this.evpAead, this.encodedKey, this.tagLengthInBytes, bArr, i2, this.iv, this.buf, 0, this.bufCount, this.aad) : NativeCrypto.EVP_AEAD_CTX_open(this.evpAead, this.encodedKey, this.tagLengthInBytes, bArr, i2, this.iv, this.buf, 0, this.bufCount, this.aad);
            if (isEncrypting()) {
                this.mustInitialize = ENABLE_BYTEBUFFER_OPTIMIZATIONS;
            }
            reset();
            return EVP_AEAD_CTX_seal;
        } catch (BadPaddingException e2) {
            throwAEADBadTagExceptionIfAvailable(e2.getMessage(), e2.getCause());
            throw e2;
        }
    }

    @Override // org.conscrypt.OpenSSLCipher, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr2 == null || getOutputSizeForFinal(i3) <= bArr2.length - i4) {
            return super.engineDoFinal(bArr, i2, i3, bArr2, i4);
        }
        throw new ShortBufferWithoutStackTraceException("Insufficient output space");
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(byte[] bArr, int i2, int i3) {
        checkInitialization();
        byte[] bArr2 = this.aad;
        if (bArr2 == null) {
            this.aad = Arrays.copyOfRange(bArr, i2, i3 + i2);
            return;
        }
        byte[] bArr3 = new byte[bArr2.length + i3];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, i2, bArr3, this.aad.length, i3);
        this.aad = bArr3;
    }
}
