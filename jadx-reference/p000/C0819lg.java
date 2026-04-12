package p000;

import java.util.Arrays;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lg */
/* loaded from: classes.dex */
public final class C0819lg {

    /* renamed from: a0 */
    public int[] f57912a0;

    /* renamed from: a1 */
    public int[] f57913a1;

    /* renamed from: a2 */
    public int f57914a2;

    /* renamed from: a3 */
    public int[] f57915a3;

    /* renamed from: a4 */
    public float[] f57916a4;

    /* renamed from: a5 */
    public int f57917a5;

    /* renamed from: a6 */
    public int[] f57918a6;

    /* renamed from: a7 */
    public String[] f57919a7;

    /* renamed from: a8 */
    public int f57920a8;

    /* renamed from: a9 */
    public int[] f57921a9;

    /* renamed from: b0 */
    public boolean[] f57922b0;

    /* renamed from: b1 */
    public int f57923b1;

    /* renamed from: a0 */
    public final void m213839a0(float f, int i) {
        int i2 = this.f57917a5;
        int[] iArr = this.f57915a3;
        if (i2 >= iArr.length) {
            this.f57915a3 = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.f57916a4;
            this.f57916a4 = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.f57915a3;
        int i3 = this.f57917a5;
        iArr2[i3] = i;
        float[] fArr2 = this.f57916a4;
        this.f57917a5 = i3 + 1;
        fArr2[i3] = f;
    }

    /* renamed from: a1 */
    public final void m213840a1(int i, int i2) {
        int i3 = this.f57914a2;
        int[] iArr = this.f57912a0;
        if (i3 >= iArr.length) {
            this.f57912a0 = Arrays.copyOf(iArr, iArr.length * 2);
            int[] iArr2 = this.f57913a1;
            this.f57913a1 = Arrays.copyOf(iArr2, iArr2.length * 2);
        }
        int[] iArr3 = this.f57912a0;
        int i4 = this.f57914a2;
        iArr3[i4] = i;
        int[] iArr4 = this.f57913a1;
        this.f57914a2 = i4 + 1;
        iArr4[i4] = i2;
    }

    /* renamed from: a2 */
    public final void m213841a2(int i, String str) {
        int i2 = this.f57920a8;
        int[] iArr = this.f57918a6;
        if (i2 >= iArr.length) {
            this.f57918a6 = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.f57919a7;
            this.f57919a7 = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
        }
        int[] iArr2 = this.f57918a6;
        int i3 = this.f57920a8;
        iArr2[i3] = i;
        String[] strArr2 = this.f57919a7;
        this.f57920a8 = i3 + 1;
        strArr2[i3] = str;
    }

    /* renamed from: a3 */
    public final void m213842a3(int i, boolean z) {
        int i2 = this.f57923b1;
        int[] iArr = this.f57921a9;
        if (i2 >= iArr.length) {
            this.f57921a9 = Arrays.copyOf(iArr, iArr.length * 2);
            boolean[] zArr = this.f57922b0;
            this.f57922b0 = Arrays.copyOf(zArr, zArr.length * 2);
        }
        int[] iArr2 = this.f57921a9;
        int i3 = this.f57923b1;
        iArr2[i3] = i;
        boolean[] zArr2 = this.f57922b0;
        this.f57923b1 = i3 + 1;
        zArr2[i3] = z;
    }

    /* renamed from: a4 */
    public final void m213843a4(C0820lh c0820lh) {
        for (int i = 0; i < this.f57914a2; i++) {
            int i2 = this.f57912a0[i];
            int i3 = this.f57913a1[i];
            if (i2 == 6) {
                c0820lh.f57930a4.f57964c9 = i3;
            } else if (i2 == 7) {
                c0820lh.f57930a4.f57965d0 = i3;
            } else if (i2 == 8) {
                c0820lh.f57930a4.f57971d6 = i3;
            } else if (i2 == 27) {
                c0820lh.f57930a4.f57966d1 = i3;
            } else if (i2 == 28) {
                c0820lh.f57930a4.f57968d3 = i3;
            } else if (i2 == 41) {
                c0820lh.f57930a4.f57983e8 = i3;
            } else if (i2 == 42) {
                c0820lh.f57930a4.f57984e9 = i3;
            } else if (i2 == 61) {
                c0820lh.f57930a4.f57961c6 = i3;
            } else if (i2 == 62) {
                c0820lh.f57930a4.f57962c7 = i3;
            } else if (i2 == 72) {
                c0820lh.f57930a4.f57993f8 = i3;
            } else if (i2 == 73) {
                c0820lh.f57930a4.f57994f9 = i3;
            } else if (i2 == 88) {
                c0820lh.f57929a3.f58018b1 = i3;
            } else if (i2 == 89) {
                c0820lh.f57929a3.f58019b2 = i3;
            } else if (i2 == 2) {
                c0820lh.f57930a4.f57970d5 = i3;
            } else if (i2 == 31) {
                c0820lh.f57930a4.f57972d7 = i3;
            } else if (i2 == 34) {
                c0820lh.f57930a4.f57969d4 = i3;
            } else if (i2 == 38) {
                c0820lh.f57926a0 = i3;
            } else if (i2 == 64) {
                c0820lh.f57929a3.f58008a1 = i3;
            } else if (i2 == 66) {
                c0820lh.f57929a3.f58012a5 = i3;
            } else if (i2 == 76) {
                c0820lh.f57929a3.f58011a4 = i3;
            } else if (i2 == 78) {
                c0820lh.f57928a2.f58026a2 = i3;
            } else if (i2 == 97) {
                c0820lh.f57930a4.f58002g7 = i3;
            } else if (i2 == 93) {
                c0820lh.f57930a4.f57973d8 = i3;
            } else if (i2 != 94) {
                switch (i2) {
                    case oe0.DEFAULT_M /* 11 */:
                        c0820lh.f57930a4.f57977e2 = i3;
                        break;
                    case FileClientSessionCache.MAX_SIZE /* 12 */:
                        c0820lh.f57930a4.f57978e3 = i3;
                        break;
                    case 13:
                        c0820lh.f57930a4.f57974d9 = i3;
                        break;
                    case 14:
                        c0820lh.f57930a4.f57976e1 = i3;
                        break;
                    case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                        c0820lh.f57930a4.f57979e4 = i3;
                        break;
                    case 16:
                        c0820lh.f57930a4.f57975e0 = i3;
                        break;
                    case 17:
                        c0820lh.f57930a4.f57939a4 = i3;
                        break;
                    case 18:
                        c0820lh.f57930a4.f57940a5 = i3;
                        break;
                    default:
                        switch (i2) {
                            case 21:
                                c0820lh.f57930a4.f57938a3 = i3;
                                break;
                            case 22:
                                c0820lh.f57928a2.f58025a1 = i3;
                                break;
                            case 23:
                                c0820lh.f57930a4.f57937a2 = i3;
                                break;
                            case 24:
                                c0820lh.f57930a4.f57967d2 = i3;
                                break;
                            default:
                                switch (i2) {
                                    case 54:
                                        c0820lh.f57930a4.f57985f0 = i3;
                                        break;
                                    case 55:
                                        c0820lh.f57930a4.f57986f1 = i3;
                                        break;
                                    case 56:
                                        c0820lh.f57930a4.f57987f2 = i3;
                                        break;
                                    case 57:
                                        c0820lh.f57930a4.f57988f3 = i3;
                                        break;
                                    case 58:
                                        c0820lh.f57930a4.f57989f4 = i3;
                                        break;
                                    case 59:
                                        c0820lh.f57930a4.f57990f5 = i3;
                                        break;
                                    default:
                                        switch (i2) {
                                            case 82:
                                                c0820lh.f57929a3.f58009a2 = i3;
                                                break;
                                            case 83:
                                                c0820lh.f57931a5.f58038a8 = i3;
                                                break;
                                            case 84:
                                                c0820lh.f57929a3.f58016a9 = i3;
                                                break;
                                        }
                                }
                        }
                }
            } else {
                c0820lh.f57930a4.f57980e5 = i3;
            }
        }
        for (int i4 = 0; i4 < this.f57917a5; i4++) {
            int i5 = this.f57915a3[i4];
            float f = this.f57916a4[i4];
            if (i5 == 19) {
                c0820lh.f57930a4.f57941a6 = f;
            } else if (i5 == 20) {
                c0820lh.f57930a4.f57958c3 = f;
            } else if (i5 == 37) {
                c0820lh.f57930a4.f57959c4 = f;
            } else if (i5 == 60) {
                c0820lh.f57931a5.f58031a1 = f;
            } else if (i5 == 63) {
                c0820lh.f57930a4.f57963c8 = f;
            } else if (i5 == 79) {
                c0820lh.f57929a3.f58013a6 = f;
            } else if (i5 == 85) {
                c0820lh.f57929a3.f58015a8 = f;
            } else if (i5 == 39) {
                c0820lh.f57930a4.f57982e7 = f;
            } else if (i5 != 40) {
                switch (i5) {
                    case 43:
                        c0820lh.f57928a2.f58027a3 = f;
                        break;
                    case 44:
                        C0824ll c0824ll = c0820lh.f57931a5;
                        c0824ll.f58043b3 = f;
                        c0824ll.f58042b2 = true;
                        break;
                    case 45:
                        c0820lh.f57931a5.f58032a2 = f;
                        break;
                    case 46:
                        c0820lh.f57931a5.f58033a3 = f;
                        break;
                    case 47:
                        c0820lh.f57931a5.f58034a4 = f;
                        break;
                    case 48:
                        c0820lh.f57931a5.f58035a5 = f;
                        break;
                    case 49:
                        c0820lh.f57931a5.f58036a6 = f;
                        break;
                    case oe0.DEFAULT_T /* 50 */:
                        c0820lh.f57931a5.f58037a7 = f;
                        break;
                    case 51:
                        c0820lh.f57931a5.f58039a9 = f;
                        break;
                    case 52:
                        c0820lh.f57931a5.f58040b0 = f;
                        break;
                    case 53:
                        c0820lh.f57931a5.f58041b1 = f;
                        break;
                    default:
                        switch (i5) {
                            case 67:
                                c0820lh.f57929a3.f58014a7 = f;
                                break;
                            case 68:
                                c0820lh.f57928a2.f58028a4 = f;
                                break;
                            case 69:
                                c0820lh.f57930a4.f57991f6 = f;
                                break;
                            case 70:
                                c0820lh.f57930a4.f57992f7 = f;
                                break;
                        }
                }
            } else {
                c0820lh.f57930a4.f57981e6 = f;
            }
        }
        for (int i6 = 0; i6 < this.f57920a8; i6++) {
            int i7 = this.f57918a6[i6];
            String str = this.f57919a7[i6];
            if (i7 == 5) {
                c0820lh.f57930a4.f57960c5 = str;
            } else if (i7 == 65) {
                c0820lh.f57929a3.f58010a3 = str;
            } else if (i7 == 74) {
                C0821li c0821li = c0820lh.f57930a4;
                c0821li.f57997g2 = str;
                c0821li.f57996g1 = null;
            } else if (i7 == 77) {
                c0820lh.f57930a4.f57998g3 = str;
            } else if (i7 == 90) {
                c0820lh.f57929a3.f58017b0 = str;
            }
        }
        for (int i8 = 0; i8 < this.f57923b1; i8++) {
            int i9 = this.f57921a9[i8];
            boolean z = this.f57922b0[i8];
            if (i9 == 44) {
                c0820lh.f57931a5.f58042b2 = z;
            } else if (i9 == 75) {
                c0820lh.f57930a4.f58001g6 = z;
            } else if (i9 == 80) {
                c0820lh.f57930a4.f57999g4 = z;
            } else if (i9 == 81) {
                c0820lh.f57930a4.f58000g5 = z;
            }
        }
    }
}
