package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class SipHash implements Mac {

    /* renamed from: c */
    protected final int f1252c;

    /* renamed from: d */
    protected final int f1253d;

    /* renamed from: k0, reason: collision with root package name */
    protected long f2343k0;

    /* renamed from: k1, reason: collision with root package name */
    protected long f2344k1;

    /* renamed from: m */
    protected long f1254m;

    /* renamed from: v0, reason: collision with root package name */
    protected long f2345v0;
    protected long v1;
    protected long v2;
    protected long v3;
    protected int wordCount;
    protected int wordPos;

    public SipHash() {
        this.f1254m = 0L;
        this.wordPos = 0;
        this.wordCount = 0;
        this.f1252c = 2;
        this.f1253d = 4;
    }

    public static long rotateLeft(long j2, int i2) {
        return (j2 >>> (-i2)) | (j2 << i2);
    }

    public void applySipRounds(int i2) {
        long j2 = this.f2345v0;
        long j3 = this.v1;
        long j4 = this.v2;
        long j5 = this.v3;
        for (int i3 = 0; i3 < i2; i3++) {
            long j6 = j2 + j3;
            long j7 = j4 + j5;
            long rotateLeft = rotateLeft(j3, 13) ^ j6;
            long rotateLeft2 = rotateLeft(j5, 16) ^ j7;
            long j8 = j7 + rotateLeft;
            j2 = rotateLeft(j6, 32) + rotateLeft2;
            j3 = rotateLeft(rotateLeft, 17) ^ j8;
            j5 = rotateLeft(rotateLeft2, 21) ^ j2;
            j4 = rotateLeft(j8, 32);
        }
        this.f2345v0 = j2;
        this.v1 = j3;
        this.v2 = j4;
        this.v3 = j5;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        Pack.longToLittleEndian(doFinal(), bArr, i2);
        return 8;
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "SipHash-" + this.f1252c + "-" + this.f1253d;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("'params' must be an instance of KeyParameter");
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length != 16) {
            throw new IllegalArgumentException("'params' must be a 128-bit key");
        }
        this.f2343k0 = Pack.littleEndianToLong(key, 0);
        this.f2344k1 = Pack.littleEndianToLong(key, 8);
        reset();
    }

    public void processMessageWord() {
        this.wordCount++;
        this.v3 ^= this.f1254m;
        applySipRounds(this.f1252c);
        this.f2345v0 ^= this.f1254m;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        long j2 = this.f2343k0;
        this.f2345v0 = 8317987319222330741L ^ j2;
        long j3 = this.f2344k1;
        this.v1 = 7237128888997146477L ^ j3;
        this.v2 = j2 ^ 7816392313619706465L;
        this.v3 = 8387220255154660723L ^ j3;
        this.f1254m = 0L;
        this.wordPos = 0;
        this.wordCount = 0;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b) {
        this.f1254m = (this.f1254m >>> 8) | ((b & 255) << 56);
        int i2 = this.wordPos + 1;
        this.wordPos = i2;
        if (i2 == 8) {
            processMessageWord();
            this.wordPos = 0;
        }
    }

    public SipHash(int i2, int i3) {
        this.f1254m = 0L;
        this.wordPos = 0;
        this.wordCount = 0;
        this.f1252c = i2;
        this.f1253d = i3;
    }

    public long doFinal() {
        this.f1254m = ((this.f1254m >>> ((7 - this.wordPos) << 3)) >>> 8) | ((((this.wordCount << 3) + r2) & 255) << 56);
        processMessageWord();
        this.v2 ^= 255;
        applySipRounds(this.f1253d);
        long j2 = ((this.f2345v0 ^ this.v1) ^ this.v2) ^ this.v3;
        reset();
        return j2;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) {
        int i4 = i3 & (-8);
        int i5 = this.wordPos;
        int i6 = 0;
        if (i5 == 0) {
            while (i6 < i4) {
                this.f1254m = Pack.littleEndianToLong(bArr, i2 + i6);
                processMessageWord();
                i6 += 8;
            }
            while (i6 < i3) {
                this.f1254m = (this.f1254m >>> 8) | ((bArr[i2 + i6] & 255) << 56);
                i6++;
            }
            this.wordPos = i3 - i4;
            return;
        }
        int i7 = i5 << 3;
        int i8 = 0;
        while (i8 < i4) {
            long littleEndianToLong = Pack.littleEndianToLong(bArr, i2 + i8);
            this.f1254m = (this.f1254m >>> (-i7)) | (littleEndianToLong << i7);
            processMessageWord();
            this.f1254m = littleEndianToLong;
            i8 += 8;
        }
        while (i8 < i3) {
            this.f1254m = (this.f1254m >>> 8) | ((bArr[i2 + i8] & 255) << 56);
            int i9 = this.wordPos + 1;
            this.wordPos = i9;
            if (i9 == 8) {
                processMessageWord();
                this.wordPos = 0;
            }
            i8++;
        }
    }
}
