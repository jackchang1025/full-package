package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.Zuc128CoreEngine;

/* loaded from: classes.dex */
public final class Zuc128Mac implements Mac {
    private static final int TOPBIT = 128;
    private int theByteIndex;
    private final InternalZuc128Engine theEngine = new InternalZuc128Engine();
    private final int[] theKeyStream = new int[2];
    private int theMac;
    private Zuc128CoreEngine theState;
    private int theWordIndex;

    public static class InternalZuc128Engine extends Zuc128CoreEngine {
        private InternalZuc128Engine() {
        }

        public int createKeyStreamWord() {
            return super.makeKeyStreamWord();
        }
    }

    private int getFinalWord() {
        if (this.theByteIndex != 0) {
            return this.theEngine.createKeyStreamWord();
        }
        int i2 = this.theWordIndex + 1;
        int[] iArr = this.theKeyStream;
        int length = i2 % iArr.length;
        this.theWordIndex = length;
        return iArr[length];
    }

    private int getKeyStreamWord(int i2) {
        int[] iArr = this.theKeyStream;
        int i3 = this.theWordIndex;
        int i4 = iArr[i3];
        if (i2 == 0) {
            return i4;
        }
        int i5 = iArr[(i3 + 1) % iArr.length];
        return (i5 >>> (32 - i2)) | (i4 << i2);
    }

    private void initKeyStream() {
        int i2 = 0;
        this.theMac = 0;
        while (true) {
            int[] iArr = this.theKeyStream;
            if (i2 >= iArr.length - 1) {
                this.theWordIndex = iArr.length - 1;
                this.theByteIndex = 3;
                return;
            } else {
                iArr[i2] = this.theEngine.createKeyStreamWord();
                i2++;
            }
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
        this.theMac = getKeyStreamWord(i2) ^ this.theMac;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        shift4NextByte();
        int keyStreamWord = this.theMac ^ getKeyStreamWord(this.theByteIndex * 8);
        this.theMac = keyStreamWord;
        int finalWord = keyStreamWord ^ getFinalWord();
        this.theMac = finalWord;
        Zuc128CoreEngine.encode32be(finalWord, bArr, i2);
        reset();
        return getMacSize();
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "Zuc128Mac";
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 4;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        this.theEngine.init(true, cipherParameters);
        this.theState = (Zuc128CoreEngine) this.theEngine.copy();
        initKeyStream();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        Zuc128CoreEngine zuc128CoreEngine = this.theState;
        if (zuc128CoreEngine != null) {
            this.theEngine.reset(zuc128CoreEngine);
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
