package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.macs.Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ChaCha20Poly1305 implements AEADCipher {
    private static final long AAD_LIMIT = -1;
    private static final int BUF_SIZE = 64;
    private static final long DATA_LIMIT = 274877906880L;
    private static final int KEY_SIZE = 32;
    private static final int MAC_SIZE = 16;
    private static final int NONCE_SIZE = 12;
    private static final byte[] ZEROES = new byte[15];
    private long aadCount;
    private final byte[] buf;
    private int bufPos;
    private final ChaCha7539Engine chacha20;
    private long dataCount;
    private byte[] initialAAD;
    private final byte[] key;
    private final byte[] mac;
    private final byte[] nonce;
    private final Mac poly1305;
    private int state;

    public static final class State {
        static final int DEC_AAD = 6;
        static final int DEC_DATA = 7;
        static final int DEC_FINAL = 8;
        static final int DEC_INIT = 5;
        static final int ENC_AAD = 2;
        static final int ENC_DATA = 3;
        static final int ENC_FINAL = 4;
        static final int ENC_INIT = 1;
        static final int UNINITIALIZED = 0;

        private State() {
        }
    }

    public ChaCha20Poly1305() {
        this(new Poly1305());
    }

    private void checkAAD() {
        int i2 = this.state;
        int i3 = 2;
        if (i2 != 1) {
            if (i2 == 2) {
                return;
            }
            if (i2 == 4) {
                throw new IllegalStateException("ChaCha20Poly1305 cannot be reused for encryption");
            }
            i3 = 6;
            if (i2 != 5) {
                if (i2 != 6) {
                    throw new IllegalStateException();
                }
                return;
            }
        }
        this.state = i3;
    }

    private void checkData() {
        int i2;
        switch (this.state) {
            case 1:
            case 2:
                i2 = 3;
                break;
            case 3:
            case 7:
                return;
            case 4:
                throw new IllegalStateException("ChaCha20Poly1305 cannot be reused for encryption");
            case 5:
            case 6:
                i2 = 7;
                break;
            default:
                throw new IllegalStateException();
        }
        finishAAD(i2);
    }

    private void finishAAD(int i2) {
        padMAC(this.aadCount);
        this.state = i2;
    }

    private void finishData(int i2) {
        padMAC(this.dataCount);
        byte[] bArr = new byte[16];
        Pack.longToLittleEndian(this.aadCount, bArr, 0);
        Pack.longToLittleEndian(this.dataCount, bArr, 8);
        this.poly1305.update(bArr, 0, 16);
        this.poly1305.doFinal(this.mac, 0);
        this.state = i2;
    }

    private long incrementCount(long j2, int i2, long j3) {
        long j4 = i2;
        if (j2 - Long.MIN_VALUE <= (j3 - j4) - Long.MIN_VALUE) {
            return j2 + j4;
        }
        throw new IllegalStateException("Limit exceeded");
    }

    private void initMAC() {
        byte[] bArr = new byte[64];
        try {
            this.chacha20.processBytes(bArr, 0, 64, bArr, 0);
            this.poly1305.init(new KeyParameter(bArr, 0, 32));
        } finally {
            Arrays.clear(bArr);
        }
    }

    private void padMAC(long j2) {
        int i2 = ((int) j2) & 15;
        if (i2 != 0) {
            this.poly1305.update(ZEROES, 0, 16 - i2);
        }
    }

    private void processData(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (i4 > bArr2.length - i3) {
            throw new OutputLengthException("Output buffer too short");
        }
        this.chacha20.processBytes(bArr, i2, i3, bArr2, i4);
        this.dataCount = incrementCount(this.dataCount, i3, DATA_LIMIT);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        int i3;
        if (bArr == null) {
            throw new NullPointerException("'out' cannot be null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("'outOff' cannot be negative");
        }
        checkData();
        Arrays.clear(this.mac);
        int i4 = this.state;
        if (i4 == 3) {
            int i5 = this.bufPos;
            i3 = i5 + 16;
            if (i2 > bArr.length - i3) {
                throw new OutputLengthException("Output buffer too short");
            }
            if (i5 > 0) {
                processData(this.buf, 0, i5, bArr, i2);
                this.poly1305.update(bArr, i2, this.bufPos);
            }
            finishData(4);
            System.arraycopy(this.mac, 0, bArr, i2 + this.bufPos, 16);
        } else {
            if (i4 != 7) {
                throw new IllegalStateException();
            }
            int i6 = this.bufPos;
            if (i6 < 16) {
                throw new InvalidCipherTextException("data too short");
            }
            i3 = i6 - 16;
            if (i2 > bArr.length - i3) {
                throw new OutputLengthException("Output buffer too short");
            }
            if (i3 > 0) {
                this.poly1305.update(this.buf, 0, i3);
                processData(this.buf, 0, i3, bArr, i2);
            }
            finishData(8);
            if (!Arrays.constantTimeAreEqual(16, this.mac, 0, this.buf, i3)) {
                throw new InvalidCipherTextException("mac check in ChaCha20Poly1305 failed");
            }
        }
        reset(false, true);
        return i3;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return "ChaCha20Poly1305";
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        return Arrays.clone(this.mac);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i2) {
        int max = Math.max(0, i2) + this.bufPos;
        int i3 = this.state;
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            return max + 16;
        }
        if (i3 == 5 || i3 == 6 || i3 == 7) {
            return Math.max(0, max - 16);
        }
        throw new IllegalStateException();
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i2) {
        int max = Math.max(0, i2) + this.bufPos;
        int i3 = this.state;
        if (i3 != 1 && i3 != 2 && i3 != 3) {
            if (i3 != 5 && i3 != 6 && i3 != 7) {
                throw new IllegalStateException();
            }
            max = Math.max(0, max - 16);
        }
        return max - (max % 64);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        ParametersWithIV parametersWithIV;
        KeyParameter keyParameter;
        byte[] iv;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            int macSize = aEADParameters.getMacSize();
            if (128 != macSize) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid value for MAC size: ", macSize));
            }
            keyParameter = aEADParameters.getKey();
            iv = aEADParameters.getNonce();
            parametersWithIV = new ParametersWithIV(keyParameter, iv);
            this.initialAAD = aEADParameters.getAssociatedText();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to ChaCha20Poly1305");
            }
            parametersWithIV = (ParametersWithIV) cipherParameters;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
            iv = parametersWithIV.getIV();
            this.initialAAD = null;
        }
        if (keyParameter == null) {
            if (this.state == 0) {
                throw new IllegalArgumentException("Key must be specified in initial init");
            }
        } else if (32 != keyParameter.getKey().length) {
            throw new IllegalArgumentException("Key must be 256 bits");
        }
        if (iv == null || 12 != iv.length) {
            throw new IllegalArgumentException("Nonce must be 96 bits");
        }
        if (this.state != 0 && z2 && Arrays.areEqual(this.nonce, iv) && (keyParameter == null || Arrays.areEqual(this.key, keyParameter.getKey()))) {
            throw new IllegalArgumentException("cannot reuse nonce for ChaCha20Poly1305 encryption");
        }
        if (keyParameter != null) {
            System.arraycopy(keyParameter.getKey(), 0, this.key, 0, 32);
        }
        System.arraycopy(iv, 0, this.nonce, 0, 12);
        this.chacha20.init(true, parametersWithIV);
        this.state = z2 ? 1 : 5;
        reset(true, false);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkAAD();
        this.aadCount = incrementCount(this.aadCount, 1, AAD_LIMIT);
        this.poly1305.update(b);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new NullPointerException("'in' cannot be null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("'inOff' cannot be negative");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("'len' cannot be negative");
        }
        if (i2 > bArr.length - i3) {
            throw new DataLengthException("Input buffer too short");
        }
        checkAAD();
        if (i3 > 0) {
            this.aadCount = incrementCount(this.aadCount, i3, AAD_LIMIT);
            this.poly1305.update(bArr, i2, i3);
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i2) {
        checkData();
        int i3 = this.state;
        if (i3 == 3) {
            byte[] bArr2 = this.buf;
            int i4 = this.bufPos;
            bArr2[i4] = b;
            int i5 = i4 + 1;
            this.bufPos = i5;
            if (i5 != 64) {
                return 0;
            }
            processData(bArr2, 0, 64, bArr, i2);
            this.poly1305.update(bArr, i2, 64);
            this.bufPos = 0;
            return 64;
        }
        if (i3 != 7) {
            throw new IllegalStateException();
        }
        byte[] bArr3 = this.buf;
        int i6 = this.bufPos;
        bArr3[i6] = b;
        int i7 = i6 + 1;
        this.bufPos = i7;
        if (i7 != bArr3.length) {
            return 0;
        }
        this.poly1305.update(bArr3, 0, 64);
        processData(this.buf, 0, 64, bArr, i2);
        byte[] bArr4 = this.buf;
        System.arraycopy(bArr4, 64, bArr4, 0, 16);
        this.bufPos = 16;
        return 64;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5;
        int i6 = i2;
        int i7 = i3;
        if (bArr == null) {
            throw new NullPointerException("'in' cannot be null");
        }
        if (i6 < 0) {
            throw new IllegalArgumentException("'inOff' cannot be negative");
        }
        if (i7 < 0) {
            throw new IllegalArgumentException("'len' cannot be negative");
        }
        if (i6 > bArr.length - i7) {
            throw new DataLengthException("Input buffer too short");
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("'outOff' cannot be negative");
        }
        checkData();
        int i8 = this.state;
        if (i8 == 3) {
            if (this.bufPos != 0) {
                while (i7 > 0) {
                    i7--;
                    byte[] bArr3 = this.buf;
                    int i9 = this.bufPos;
                    int i10 = i6 + 1;
                    bArr3[i9] = bArr[i6];
                    int i11 = i9 + 1;
                    this.bufPos = i11;
                    if (i11 == 64) {
                        processData(bArr3, 0, 64, bArr2, i4);
                        this.poly1305.update(bArr2, i4, 64);
                        this.bufPos = 0;
                        i5 = 64;
                        i6 = i10;
                        break;
                    }
                    i6 = i10;
                }
            }
            i5 = 0;
            while (i7 >= 64) {
                int i12 = i4 + i5;
                processData(bArr, i6, 64, bArr2, i12);
                this.poly1305.update(bArr2, i12, 64);
                i6 += 64;
                i7 -= 64;
                i5 += 64;
            }
            if (i7 > 0) {
                System.arraycopy(bArr, i6, this.buf, 0, i7);
                this.bufPos = i7;
            }
        } else {
            if (i8 != 7) {
                throw new IllegalStateException();
            }
            i5 = 0;
            for (int i13 = 0; i13 < i7; i13++) {
                byte[] bArr4 = this.buf;
                int i14 = this.bufPos;
                bArr4[i14] = bArr[i6 + i13];
                int i15 = i14 + 1;
                this.bufPos = i15;
                if (i15 == bArr4.length) {
                    this.poly1305.update(bArr4, 0, 64);
                    processData(this.buf, 0, 64, bArr2, i4 + i5);
                    byte[] bArr5 = this.buf;
                    System.arraycopy(bArr5, 64, bArr5, 0, 16);
                    this.bufPos = 16;
                    i5 += 64;
                }
            }
        }
        return i5;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true, true);
    }

    public ChaCha20Poly1305(Mac mac) {
        this.key = new byte[32];
        this.nonce = new byte[12];
        this.buf = new byte[80];
        this.mac = new byte[16];
        this.state = 0;
        if (mac == null) {
            throw new NullPointerException("'poly1305' cannot be null");
        }
        if (16 != mac.getMacSize()) {
            throw new IllegalArgumentException("'poly1305' must be a 128-bit MAC");
        }
        this.chacha20 = new ChaCha7539Engine();
        this.poly1305 = mac;
    }

    private void reset(boolean z2, boolean z3) {
        Arrays.clear(this.buf);
        if (z2) {
            Arrays.clear(this.mac);
        }
        this.aadCount = 0L;
        this.dataCount = 0L;
        this.bufPos = 0;
        switch (this.state) {
            case 1:
            case 5:
                break;
            case 2:
            case 3:
            case 4:
                this.state = 4;
                return;
            case 6:
            case 7:
            case 8:
                this.state = 5;
                break;
            default:
                throw new IllegalStateException();
        }
        if (z3) {
            this.chacha20.reset();
        }
        initMAC();
        byte[] bArr = this.initialAAD;
        if (bArr != null) {
            processAADBytes(bArr, 0, bArr.length);
        }
    }
}
