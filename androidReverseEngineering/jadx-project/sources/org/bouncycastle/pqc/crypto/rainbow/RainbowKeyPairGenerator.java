package org.bouncycastle.pqc.crypto.rainbow;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.bouncycastle.pqc.crypto.rainbow.util.GF2Field;

/* loaded from: classes.dex */
public class RainbowKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private short[][] A1;
    private short[][] A1inv;
    private short[][] A2;
    private short[][] A2inv;

    /* renamed from: b1, reason: collision with root package name */
    private short[] f2351b1;
    private short[] b2;
    private boolean initialized = false;
    private Layer[] layers;
    private int numOfLayers;
    private short[][] pub_quadratic;
    private short[] pub_scalar;
    private short[][] pub_singular;
    private RainbowKeyGenerationParameters rainbowParams;
    private SecureRandom sr;
    private int[] vi;

    private void compactPublicKey(short[][][] sArr) {
        int length = sArr.length;
        int length2 = sArr[0].length;
        this.pub_quadratic = (short[][]) Array.newInstance((Class<?>) Short.TYPE, length, ((length2 + 1) * length2) / 2);
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = 0;
            for (int i4 = 0; i4 < length2; i4++) {
                for (int i5 = i4; i5 < length2; i5++) {
                    short[][] sArr2 = this.pub_quadratic;
                    if (i5 == i4) {
                        sArr2[i2][i3] = sArr[i2][i4][i5];
                    } else {
                        short[] sArr3 = sArr2[i2];
                        short[][] sArr4 = sArr[i2];
                        sArr3[i3] = GF2Field.addElem(sArr4[i4][i5], sArr4[i5][i4]);
                    }
                    i3++;
                }
            }
        }
    }

    private void computePublicKey() {
        ComputeInField computeInField = new ComputeInField();
        int[] iArr = this.vi;
        int i2 = 0;
        int i3 = iArr[iArr.length - 1] - iArr[0];
        int i4 = iArr[iArr.length - 1];
        int i5 = 3;
        short[][][] sArr = (short[][][]) Array.newInstance((Class<?>) Short.TYPE, i3, i4, i4);
        this.pub_singular = (short[][]) Array.newInstance((Class<?>) Short.TYPE, i3, i4);
        this.pub_scalar = new short[i3];
        short[] sArr2 = new short[i4];
        int i6 = 0;
        int i7 = 0;
        while (true) {
            Layer[] layerArr = this.layers;
            if (i6 >= layerArr.length) {
                break;
            }
            short[][][] coeffAlpha = layerArr[i6].getCoeffAlpha();
            short[][][] coeffBeta = this.layers[i6].getCoeffBeta();
            short[][] coeffGamma = this.layers[i6].getCoeffGamma();
            short[] coeffEta = this.layers[i6].getCoeffEta();
            int length = coeffAlpha[i2].length;
            int length2 = coeffBeta[i2].length;
            while (i2 < length) {
                for (int i8 = 0; i8 < length; i8++) {
                    int i9 = 0;
                    while (i9 < length2) {
                        int i10 = i3;
                        int i11 = i4;
                        int i12 = i8 + length2;
                        short[] multVect = computeInField.multVect(coeffAlpha[i2][i8][i9], this.A2[i12]);
                        int i13 = i7 + i2;
                        int i14 = i6;
                        sArr[i13] = computeInField.addSquareMatrix(sArr[i13], computeInField.multVects(multVect, this.A2[i9]));
                        short[] multVect2 = computeInField.multVect(this.b2[i9], multVect);
                        short[][] sArr3 = this.pub_singular;
                        sArr3[i13] = computeInField.addVect(multVect2, sArr3[i13]);
                        short[] multVect3 = computeInField.multVect(this.b2[i12], computeInField.multVect(coeffAlpha[i2][i8][i9], this.A2[i9]));
                        short[][] sArr4 = this.pub_singular;
                        sArr4[i13] = computeInField.addVect(multVect3, sArr4[i13]);
                        short multElem = GF2Field.multElem(coeffAlpha[i2][i8][i9], this.b2[i12]);
                        short[] sArr5 = this.pub_scalar;
                        sArr5[i13] = GF2Field.addElem(sArr5[i13], GF2Field.multElem(multElem, this.b2[i9]));
                        i9++;
                        i4 = i11;
                        i3 = i10;
                        coeffAlpha = coeffAlpha;
                        i6 = i14;
                        coeffEta = coeffEta;
                    }
                }
                int i15 = i4;
                int i16 = i3;
                int i17 = i6;
                short[][][] sArr6 = coeffAlpha;
                short[] sArr7 = coeffEta;
                for (int i18 = 0; i18 < length2; i18++) {
                    for (int i19 = 0; i19 < length2; i19++) {
                        short[] multVect4 = computeInField.multVect(coeffBeta[i2][i18][i19], this.A2[i18]);
                        int i20 = i7 + i2;
                        sArr[i20] = computeInField.addSquareMatrix(sArr[i20], computeInField.multVects(multVect4, this.A2[i19]));
                        short[] multVect5 = computeInField.multVect(this.b2[i19], multVect4);
                        short[][] sArr8 = this.pub_singular;
                        sArr8[i20] = computeInField.addVect(multVect5, sArr8[i20]);
                        short[] multVect6 = computeInField.multVect(this.b2[i18], computeInField.multVect(coeffBeta[i2][i18][i19], this.A2[i19]));
                        short[][] sArr9 = this.pub_singular;
                        sArr9[i20] = computeInField.addVect(multVect6, sArr9[i20]);
                        short multElem2 = GF2Field.multElem(coeffBeta[i2][i18][i19], this.b2[i18]);
                        short[] sArr10 = this.pub_scalar;
                        sArr10[i20] = GF2Field.addElem(sArr10[i20], GF2Field.multElem(multElem2, this.b2[i19]));
                    }
                }
                for (int i21 = 0; i21 < length2 + length; i21++) {
                    short[] multVect7 = computeInField.multVect(coeffGamma[i2][i21], this.A2[i21]);
                    short[][] sArr11 = this.pub_singular;
                    int i22 = i7 + i2;
                    sArr11[i22] = computeInField.addVect(multVect7, sArr11[i22]);
                    short[] sArr12 = this.pub_scalar;
                    sArr12[i22] = GF2Field.addElem(sArr12[i22], GF2Field.multElem(coeffGamma[i2][i21], this.b2[i21]));
                }
                short[] sArr13 = this.pub_scalar;
                int i23 = i7 + i2;
                sArr13[i23] = GF2Field.addElem(sArr13[i23], sArr7[i2]);
                i2++;
                i4 = i15;
                i3 = i16;
                coeffAlpha = sArr6;
                i6 = i17;
                coeffEta = sArr7;
            }
            i7 += length;
            i6++;
            i2 = 0;
            i5 = 3;
        }
        int i24 = i4;
        int i25 = i3;
        int[] iArr2 = new int[i5];
        iArr2[2] = i24;
        iArr2[1] = i24;
        iArr2[0] = i25;
        short[][][] sArr14 = (short[][][]) Array.newInstance((Class<?>) Short.TYPE, iArr2);
        short[][] sArr15 = (short[][]) Array.newInstance((Class<?>) Short.TYPE, i25, i24);
        short[] sArr16 = new short[i25];
        for (int i26 = 0; i26 < i25; i26++) {
            int i27 = 0;
            while (true) {
                short[][] sArr17 = this.A1;
                if (i27 < sArr17.length) {
                    sArr14[i26] = computeInField.addSquareMatrix(sArr14[i26], computeInField.multMatrix(sArr17[i26][i27], sArr[i27]));
                    sArr15[i26] = computeInField.addVect(sArr15[i26], computeInField.multVect(this.A1[i26][i27], this.pub_singular[i27]));
                    sArr16[i26] = GF2Field.addElem(sArr16[i26], GF2Field.multElem(this.A1[i26][i27], this.pub_scalar[i27]));
                    i27++;
                }
            }
            sArr16[i26] = GF2Field.addElem(sArr16[i26], this.f2351b1[i26]);
        }
        this.pub_singular = sArr15;
        this.pub_scalar = sArr16;
        compactPublicKey(sArr14);
    }

    private void generateF() {
        this.layers = new Layer[this.numOfLayers];
        int i2 = 0;
        while (i2 < this.numOfLayers) {
            Layer[] layerArr = this.layers;
            int[] iArr = this.vi;
            int i3 = i2 + 1;
            layerArr[i2] = new Layer(iArr[i2], iArr[i3], this.sr);
            i2 = i3;
        }
    }

    private void generateL1() {
        int[] iArr = this.vi;
        int i2 = iArr[iArr.length - 1] - iArr[0];
        this.A1 = (short[][]) Array.newInstance((Class<?>) Short.TYPE, i2, i2);
        this.A1inv = null;
        ComputeInField computeInField = new ComputeInField();
        while (this.A1inv == null) {
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i2; i4++) {
                    this.A1[i3][i4] = (short) (this.sr.nextInt() & 255);
                }
            }
            this.A1inv = computeInField.inverse(this.A1);
        }
        this.f2351b1 = new short[i2];
        for (int i5 = 0; i5 < i2; i5++) {
            this.f2351b1[i5] = (short) (this.sr.nextInt() & 255);
        }
    }

    private void generateL2() {
        int[] iArr = this.vi;
        int i2 = iArr[iArr.length - 1];
        this.A2 = (short[][]) Array.newInstance((Class<?>) Short.TYPE, i2, i2);
        this.A2inv = null;
        ComputeInField computeInField = new ComputeInField();
        while (this.A2inv == null) {
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i2; i4++) {
                    this.A2[i3][i4] = (short) (this.sr.nextInt() & 255);
                }
            }
            this.A2inv = computeInField.inverse(this.A2);
        }
        this.b2 = new short[i2];
        for (int i5 = 0; i5 < i2; i5++) {
            this.b2[i5] = (short) (this.sr.nextInt() & 255);
        }
    }

    private void initializeDefault() {
        initialize(new RainbowKeyGenerationParameters(CryptoServicesRegistrar.getSecureRandom(), new RainbowParameters()));
    }

    private void keygen() {
        generateL1();
        generateL2();
        generateF();
        computePublicKey();
    }

    public AsymmetricCipherKeyPair genKeyPair() {
        if (!this.initialized) {
            initializeDefault();
        }
        keygen();
        RainbowPrivateKeyParameters rainbowPrivateKeyParameters = new RainbowPrivateKeyParameters(this.A1inv, this.f2351b1, this.A2inv, this.b2, this.vi, this.layers);
        int[] iArr = this.vi;
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) new RainbowPublicKeyParameters(iArr[iArr.length - 1] - iArr[0], this.pub_quadratic, this.pub_singular, this.pub_scalar), (AsymmetricKeyParameter) rainbowPrivateKeyParameters);
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        return genKeyPair();
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        initialize(keyGenerationParameters);
    }

    public void initialize(KeyGenerationParameters keyGenerationParameters) {
        RainbowKeyGenerationParameters rainbowKeyGenerationParameters = (RainbowKeyGenerationParameters) keyGenerationParameters;
        this.rainbowParams = rainbowKeyGenerationParameters;
        this.sr = rainbowKeyGenerationParameters.getRandom();
        this.vi = this.rainbowParams.getParameters().getVi();
        this.numOfLayers = this.rainbowParams.getParameters().getNumOfLayers();
        this.initialized = true;
    }
}
