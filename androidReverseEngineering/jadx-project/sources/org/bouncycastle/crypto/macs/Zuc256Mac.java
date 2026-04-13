package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.Zuc128CoreEngine;
import org.bouncycastle.crypto.engines.Zuc256CoreEngine;

/* loaded from: classes.dex */
public final class Zuc256Mac implements Mac {
    private static final int TOPBIT = 128;
    private int theByteIndex;
    private final InternalZuc256Engine theEngine;
    private final int[] theKeyStream;
    private final int[] theMac;
    private final int theMacLength;
    private Zuc256CoreEngine theState;
    private int theWordIndex;

    public static class InternalZuc256Engine extends Zuc256CoreEngine {
        public InternalZuc256Engine(int i2) {
            super(i2);
        }

        public int createKeyStreamWord() {
            return super.makeKeyStreamWord();
        }
    }

    public Zuc256Mac(int i2) {
        this.theEngine = new InternalZuc256Engine(i2);
        this.theMacLength = i2;
        int i3 = i2 / 32;
        this.theMac = new int[i3];
        this.theKeyStream = new int[i3 + 1];
    }

    private int getKeyStreamWord(int i2, int i3) {
        int[] iArr = this.theKeyStream;
        int i4 = this.theWordIndex;
        int i5 = iArr[(i4 + i2) % iArr.length];
        if (i3 == 0) {
            return i5;
        }
        int i6 = iArr[((i4 + i2) + 1) % iArr.length];
        return (i6 >>> (32 - i3)) | (i5 << i3);
    }

    private void initKeyStream() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i3 >= iArr.length) {
                break;
            }
            iArr[i3] = this.theEngine.createKeyStreamWord();
            i3++;
        }
        while (true) {
            int[] iArr2 = this.theKeyStream;
            if (i2 >= iArr2.length - 1) {
                this.theWordIndex = iArr2.length - 1;
                this.theByteIndex = 3;
                return;
            } else {
                iArr2[i2] = this.theEngine.createKeyStreamWord();
                i2++;
            }
        }
    }

    private void shift4Final() {
        int i2 = (this.theByteIndex + 1) % 4;
        this.theByteIndex = i2;
        if (i2 == 0) {
            this.theWordIndex = (this.theWordIndex + 1) % this.theKeyStream.length;
        }
    }

    private void shift4NextByte() {
        int i2 = (this.theByteIndex + 1) % 4;
        this.theByteIndex = i2;
        if (i2 == 0) {
            this.theKeyStream[this.theWordIndex] = this.theEngine.createKeyStreamWord();
            this.theWordIndex = (this.theWordIndex + 1) % this.theKeyStream.length;
        }
    }

    private void updateMac(int i2) {
        int i3 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i3 >= iArr.length) {
                return;
            }
            iArr[i3] = iArr[i3] ^ getKeyStreamWord(i3, i2);
            i3++;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        shift4Final();
        updateMac(this.theByteIndex * 8);
        int i3 = 0;
        while (true) {
            int[] iArr = this.theMac;
            if (i3 >= iArr.length) {
                reset();
                return getMacSize();
            }
            Zuc128CoreEngine.encode32be(iArr[i3], bArr, (i3 * 4) + i2);
            i3++;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "Zuc256Mac-" + this.theMacLength;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.theMacLength / 8;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        this.theEngine.init(true, cipherParameters);
        this.theState = (Zuc256CoreEngine) this.theEngine.copy();
        initKeyStream();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        Zuc256CoreEngine zuc256CoreEngine = this.theState;
        if (zuc256CoreEngine != null) {
            this.theEngine.reset(zuc256CoreEngine);
        }
        initKeyStream();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b) {
        shift4NextByte();
        int i2 = this.theByteIndex * 8;
        int i3 = 128;
        int i4 = 0;
        while (i3 > 0) {
            if ((b & i3) != 0) {
                updateMac(i2 + i4);
            }
            i3 >>= 1;
            i4++;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            update(bArr[i2 + i4]);
        }
    }
}
