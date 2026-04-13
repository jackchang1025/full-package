package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410ValidationParameters;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class GOST3410ParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private SecureRandom init_random;
    private int size;
    private int typeproc;

    private int procedure_A(int i2, int i3, BigInteger[] bigIntegerArr, int i4) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int i5;
        int i6;
        int i7 = i2;
        while (true) {
            if (i7 >= 0 && i7 <= 65536) {
                break;
            }
            i7 = this.init_random.nextInt() / 32768;
        }
        int i8 = i3;
        while (true) {
            if (i8 >= 0 && i8 <= 65536 && i8 / 2 != 0) {
                break;
            }
            i8 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Integer.toString(i8));
        BigInteger bigInteger5 = new BigInteger("19381");
        BigInteger bigInteger6 = new BigInteger(Integer.toString(i7));
        int i9 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i4};
        int i10 = 0;
        int i11 = 0;
        while (iArr[i10] >= 17) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i11 = i10 + 1;
            iArr[i11] = iArr[i10] / 2;
            i10 = i11;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[i11 + 1];
        int i12 = 16;
        bigIntegerArr4[i11] = new BigInteger("8003", 16);
        int i13 = i11 - 1;
        int i14 = 0;
        while (true) {
            if (i14 >= i11) {
                bigInteger = bigIntegerArr3[i9];
                break;
            }
            int i15 = iArr[i13] / i12;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i9, bigIntegerArr5, i9, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[i15 + 1];
                System.arraycopy(bigIntegerArr5, i9, bigIntegerArr2, i9, length2);
                int i16 = i9;
                while (i16 < i15) {
                    int i17 = i16 + 1;
                    bigIntegerArr2[i17] = bigIntegerArr2[i16].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i12));
                    i16 = i17;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i18 = i9; i18 < i15; i18++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i18].multiply(TWO.pow(i18 * 16)));
                }
                bigIntegerArr2[i9] = bigIntegerArr2[i15];
                BigInteger bigInteger8 = TWO;
                int i19 = i13 + 1;
                BigInteger add = bigInteger8.pow(iArr[i13] - 1).divide(bigIntegerArr4[i19]).add(bigInteger8.pow(iArr[i13] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i19].multiply(bigInteger8.pow(i15 * 16))));
                BigInteger mod = add.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (mod.compareTo(bigInteger9) == 0) {
                    add = add.add(bigInteger9);
                }
                int i20 = 0;
                while (true) {
                    bigInteger2 = bigInteger4;
                    bigInteger3 = bigInteger5;
                    long j2 = i20;
                    i5 = i11;
                    BigInteger multiply = bigIntegerArr4[i19].multiply(add.add(BigInteger.valueOf(j2)));
                    BigInteger bigInteger10 = ONE;
                    BigInteger add2 = multiply.add(bigInteger10);
                    bigIntegerArr4[i13] = add2;
                    BigInteger bigInteger11 = TWO;
                    i6 = i15;
                    if (add2.compareTo(bigInteger11.pow(iArr[i13])) == 1) {
                        break;
                    }
                    if (bigInteger11.modPow(bigIntegerArr4[i19].multiply(add.add(BigInteger.valueOf(j2))), bigIntegerArr4[i13]).compareTo(bigInteger10) == 0 && bigInteger11.modPow(add.add(BigInteger.valueOf(j2)), bigIntegerArr4[i13]).compareTo(bigInteger10) != 0) {
                        break;
                    }
                    i20 += 2;
                    i11 = i5;
                    bigInteger5 = bigInteger3;
                    bigInteger4 = bigInteger2;
                    i15 = i6;
                }
                i11 = i5;
                bigInteger5 = bigInteger3;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger4 = bigInteger2;
                i15 = i6;
                i9 = 0;
                i12 = 16;
            }
            i13--;
            if (i13 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i14++;
            i11 = i5;
            bigInteger5 = bigInteger3;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger4 = bigInteger2;
            i9 = 0;
            i12 = 16;
        }
        return bigInteger.intValue();
    }

    private long procedure_Aa(long j2, long j3, BigInteger[] bigIntegerArr, int i2) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int i3;
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L) {
                break;
            }
            j4 = this.init_random.nextInt() * 2;
        }
        long j5 = j3;
        while (true) {
            if (j5 >= 0 && j5 <= 4294967296L && j5 / 2 != 0) {
                break;
            }
            j5 = (this.init_random.nextInt() * 2) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Long.toString(j5));
        BigInteger bigInteger5 = new BigInteger("97781173");
        BigInteger bigInteger6 = new BigInteger(Long.toString(j4));
        int i4 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i2};
        int i5 = 0;
        int i6 = 0;
        while (iArr[i5] >= 33) {
            int length = iArr.length + 1;
            int[] iArr2 = new int[length];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[length];
            System.arraycopy(iArr2, 0, iArr, 0, length);
            i6 = i5 + 1;
            iArr[i6] = iArr[i5] / 2;
            i5 = i6;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[i6 + 1];
        bigIntegerArr4[i6] = new BigInteger("8000000B", 16);
        int i7 = i6 - 1;
        int i8 = 0;
        while (true) {
            if (i8 >= i6) {
                bigInteger = bigIntegerArr3[i4];
                break;
            }
            int i9 = 32;
            int i10 = iArr[i7] / 32;
            while (true) {
                int length2 = bigIntegerArr3.length;
                BigInteger[] bigIntegerArr5 = new BigInteger[length2];
                System.arraycopy(bigIntegerArr3, i4, bigIntegerArr5, i4, bigIntegerArr3.length);
                bigIntegerArr2 = new BigInteger[i10 + 1];
                System.arraycopy(bigIntegerArr5, i4, bigIntegerArr2, i4, length2);
                int i11 = i4;
                while (i11 < i10) {
                    int i12 = i11 + 1;
                    bigIntegerArr2[i12] = bigIntegerArr2[i11].multiply(bigInteger5).add(bigInteger4).mod(TWO.pow(i9));
                    i11 = i12;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i13 = i4; i13 < i10; i13++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i13].multiply(TWO.pow(i13 * 32)));
                }
                bigIntegerArr2[i4] = bigIntegerArr2[i10];
                BigInteger bigInteger8 = TWO;
                int i14 = i7 + 1;
                BigInteger add = bigInteger8.pow(iArr[i7] - 1).divide(bigIntegerArr4[i14]).add(bigInteger8.pow(iArr[i7] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i14].multiply(bigInteger8.pow(i10 * 32))));
                BigInteger mod = add.mod(bigInteger8);
                BigInteger bigInteger9 = ONE;
                if (mod.compareTo(bigInteger9) == 0) {
                    add = add.add(bigInteger9);
                }
                int i15 = 0;
                while (true) {
                    long j6 = i15;
                    bigInteger2 = bigInteger4;
                    BigInteger multiply = bigIntegerArr4[i14].multiply(add.add(BigInteger.valueOf(j6)));
                    BigInteger bigInteger10 = ONE;
                    BigInteger add2 = multiply.add(bigInteger10);
                    bigIntegerArr4[i7] = add2;
                    bigInteger3 = bigInteger5;
                    BigInteger bigInteger11 = TWO;
                    i3 = i6;
                    if (add2.compareTo(bigInteger11.pow(iArr[i7])) == 1) {
                        break;
                    }
                    if (bigInteger11.modPow(bigIntegerArr4[i14].multiply(add.add(BigInteger.valueOf(j6))), bigIntegerArr4[i7]).compareTo(bigInteger10) == 0 && bigInteger11.modPow(add.add(BigInteger.valueOf(j6)), bigIntegerArr4[i7]).compareTo(bigInteger10) != 0) {
                        break;
                    }
                    i15 += 2;
                    bigInteger4 = bigInteger2;
                    i6 = i3;
                    bigInteger5 = bigInteger3;
                }
                bigInteger4 = bigInteger2;
                bigIntegerArr3 = bigIntegerArr2;
                bigInteger5 = bigInteger3;
                i4 = 0;
                i9 = 32;
                i6 = i3;
            }
            i7--;
            if (i7 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i8++;
            bigInteger4 = bigInteger2;
            i6 = i3;
            bigIntegerArr3 = bigIntegerArr2;
            bigInteger5 = bigInteger3;
            i4 = 0;
        }
        return bigInteger.longValue();
    }

    private void procedure_B(int i2, int i3, BigInteger[] bigIntegerArr) {
        int i4 = i2;
        while (true) {
            if (i4 >= 0 && i4 <= 65536) {
                break;
            } else {
                i4 = this.init_random.nextInt() / 32768;
            }
        }
        int i5 = i3;
        while (true) {
            if (i5 >= 0 && i5 <= 65536 && i5 / 2 != 0) {
                break;
            } else {
                i5 = (this.init_random.nextInt() / 32768) + 1;
            }
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Integer.toString(i5));
        BigInteger bigInteger2 = new BigInteger("19381");
        int procedure_A = procedure_A(i4, i5, bigIntegerArr2, 256);
        int i6 = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        int procedure_A2 = procedure_A(procedure_A, i5, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[65];
        bigIntegerArr3[0] = new BigInteger(Integer.toString(procedure_A2));
        while (true) {
            int i7 = i6;
            while (i7 < 64) {
                int i8 = i7 + 1;
                bigIntegerArr3[i8] = bigIntegerArr3[i7].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(16));
                i7 = i8;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i9 = i6; i9 < 64; i9++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i9].multiply(TWO.pow(i9 * 16)));
            }
            bigIntegerArr3[i6] = bigIntegerArr3[64];
            BigInteger bigInteger6 = TWO;
            int i10 = 1024;
            BigInteger add = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger mod = add.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (mod.compareTo(bigInteger7) == 0) {
                add = add.add(bigInteger7);
            }
            BigInteger bigInteger8 = add;
            int i11 = i6;
            while (true) {
                long j2 = i11;
                BigInteger multiply = bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j2)));
                BigInteger bigInteger9 = ONE;
                BigInteger add2 = multiply.add(bigInteger9);
                BigInteger bigInteger10 = TWO;
                if (add2.compareTo(bigInteger10.pow(i10)) == 1) {
                    break;
                }
                if (bigInteger10.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger8.add(BigInteger.valueOf(j2))), add2).compareTo(bigInteger9) == 0 && bigInteger10.modPow(bigInteger3.multiply(bigInteger8.add(BigInteger.valueOf(j2))), add2).compareTo(bigInteger9) != 0) {
                    bigIntegerArr[0] = add2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                } else {
                    i11 += 2;
                    i10 = 1024;
                }
            }
            i6 = 0;
        }
    }

    private void procedure_Bb(long j2, long j3, BigInteger[] bigIntegerArr) {
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L) {
                break;
            } else {
                j4 = this.init_random.nextInt() * 2;
            }
        }
        long j5 = j3;
        while (true) {
            if (j5 >= 0 && j5 <= 4294967296L && j5 / 2 != 0) {
                break;
            } else {
                j5 = (this.init_random.nextInt() * 2) + 1;
            }
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Long.toString(j5));
        BigInteger bigInteger2 = new BigInteger("97781173");
        long j6 = j5;
        long procedure_Aa = procedure_Aa(j4, j6, bigIntegerArr2, 256);
        int i2 = 0;
        BigInteger bigInteger3 = bigIntegerArr2[0];
        long procedure_Aa2 = procedure_Aa(procedure_Aa, j6, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[33];
        bigIntegerArr3[0] = new BigInteger(Long.toString(procedure_Aa2));
        while (true) {
            int i3 = i2;
            while (i3 < 32) {
                int i4 = i3 + 1;
                bigIntegerArr3[i4] = bigIntegerArr3[i3].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(32));
                i3 = i4;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i5 = i2; i5 < 32; i5++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i5].multiply(TWO.pow(i5 * 32)));
            }
            bigIntegerArr3[i2] = bigIntegerArr3[32];
            BigInteger bigInteger6 = TWO;
            int i6 = 1024;
            BigInteger add = bigInteger6.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(bigInteger6.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.pow(1024))));
            BigInteger mod = add.mod(bigInteger6);
            BigInteger bigInteger7 = ONE;
            if (mod.compareTo(bigInteger7) == 0) {
                add = add.add(bigInteger7);
            }
            int i7 = i2;
            while (true) {
                long j7 = i7;
                BigInteger multiply = bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j7)));
                BigInteger bigInteger8 = ONE;
                BigInteger add2 = multiply.add(bigInteger8);
                BigInteger bigInteger9 = TWO;
                if (add2.compareTo(bigInteger9.pow(i6)) == 1) {
                    break;
                }
                if (bigInteger9.modPow(bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j7))), add2).compareTo(bigInteger8) == 0 && bigInteger9.modPow(bigInteger3.multiply(add.add(BigInteger.valueOf(j7))), add2).compareTo(bigInteger8) != 0) {
                    bigIntegerArr[0] = add2;
                    bigIntegerArr[1] = bigInteger3;
                    return;
                } else {
                    i7 += 2;
                    i6 = 1024;
                }
            }
            i2 = 0;
        }
    }

    private BigInteger procedure_C(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger subtract = bigInteger.subtract(ONE);
        BigInteger divide = subtract.divide(bigInteger2);
        int bitLength = bigInteger.bitLength();
        while (true) {
            BigInteger createRandomBigInteger = BigIntegers.createRandomBigInteger(bitLength, this.init_random);
            BigInteger bigInteger3 = ONE;
            if (createRandomBigInteger.compareTo(bigInteger3) > 0 && createRandomBigInteger.compareTo(subtract) < 0) {
                BigInteger modPow = createRandomBigInteger.modPow(divide, bigInteger);
                if (modPow.compareTo(bigInteger3) != 0) {
                    return modPow;
                }
            }
        }
    }

    public GOST3410Parameters generateParameters() {
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (this.typeproc == 1) {
            int nextInt = this.init_random.nextInt();
            int nextInt2 = this.init_random.nextInt();
            int i2 = this.size;
            if (i2 == 512) {
                procedure_A(nextInt, nextInt2, bigIntegerArr, 512);
            } else {
                if (i2 != 1024) {
                    throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
                }
                procedure_B(nextInt, nextInt2, bigIntegerArr);
            }
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            return new GOST3410Parameters(bigInteger, bigInteger2, procedure_C(bigInteger, bigInteger2), new GOST3410ValidationParameters(nextInt, nextInt2));
        }
        long nextLong = this.init_random.nextLong();
        long nextLong2 = this.init_random.nextLong();
        int i3 = this.size;
        if (i3 == 512) {
            procedure_Aa(nextLong, nextLong2, bigIntegerArr, 512);
        } else {
            if (i3 != 1024) {
                throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
            }
            procedure_Bb(nextLong, nextLong2, bigIntegerArr);
        }
        BigInteger bigInteger3 = bigIntegerArr[0];
        BigInteger bigInteger4 = bigIntegerArr[1];
        return new GOST3410Parameters(bigInteger3, bigInteger4, procedure_C(bigInteger3, bigInteger4), new GOST3410ValidationParameters(nextLong, nextLong2));
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        this.size = i2;
        this.typeproc = i3;
        this.init_random = secureRandom;
    }
}
