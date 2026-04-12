package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class u01 {
    private static final long serialVersionUID = 1;
    private final BigInteger bigInt;
    private final int scale;

    public u01(BigInteger bigInteger, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("scale may not be negative");
        }
        this.bigInt = bigInteger;
        this.scale = i;
    }

    private void checkScale(u01 u01Var) {
        if (this.scale != u01Var.scale) {
            throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
        }
    }

    public static u01 getInstance(BigInteger bigInteger, int i) {
        return new u01(bigInteger.shiftLeft(i), i);
    }

    public u01 add(u01 u01Var) {
        checkScale(u01Var);
        return new u01(this.bigInt.add(u01Var.bigInt), this.scale);
    }

    public u01 adjustScale(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("scale may not be negative");
        }
        int i2 = this.scale;
        return i == i2 ? this : new u01(this.bigInt.shiftLeft(i - i2), i);
    }

    public int compareTo(u01 u01Var) {
        checkScale(u01Var);
        return this.bigInt.compareTo(u01Var.bigInt);
    }

    public u01 divide(u01 u01Var) {
        checkScale(u01Var);
        return new u01(this.bigInt.shiftLeft(this.scale).divide(u01Var.bigInt), this.scale);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u01)) {
            return false;
        }
        u01 u01Var = (u01) obj;
        return this.bigInt.equals(u01Var.bigInt) && this.scale == u01Var.scale;
    }

    public BigInteger floor() {
        return this.bigInt.shiftRight(this.scale);
    }

    public int getScale() {
        return this.scale;
    }

    public int hashCode() {
        return this.bigInt.hashCode() ^ this.scale;
    }

    public int intValue() {
        return floor().intValue();
    }

    public long longValue() {
        return floor().longValue();
    }

    public u01 multiply(u01 u01Var) {
        checkScale(u01Var);
        BigInteger bigIntegerMultiply = this.bigInt.multiply(u01Var.bigInt);
        int i = this.scale;
        return new u01(bigIntegerMultiply, i + i);
    }

    public u01 negate() {
        return new u01(this.bigInt.negate(), this.scale);
    }

    public BigInteger round() {
        return add(new u01(InterfaceC1315uw.ONE, 1).adjustScale(this.scale)).floor();
    }

    public u01 shiftLeft(int i) {
        return new u01(this.bigInt.shiftLeft(i), this.scale);
    }

    public u01 subtract(u01 u01Var) {
        return add(u01Var.negate());
    }

    public String toString() {
        if (this.scale == 0) {
            return this.bigInt.toString();
        }
        BigInteger bigIntegerFloor = floor();
        BigInteger bigIntegerSubtract = this.bigInt.subtract(bigIntegerFloor.shiftLeft(this.scale));
        if (this.bigInt.signum() == -1) {
            bigIntegerSubtract = InterfaceC1315uw.ONE.shiftLeft(this.scale).subtract(bigIntegerSubtract);
        }
        if (bigIntegerFloor.signum() == -1 && !bigIntegerSubtract.equals(InterfaceC1315uw.ZERO)) {
            bigIntegerFloor = bigIntegerFloor.add(InterfaceC1315uw.ONE);
        }
        String string = bigIntegerFloor.toString();
        char[] cArr = new char[this.scale];
        String string2 = bigIntegerSubtract.toString(2);
        int length = string2.length();
        int i = this.scale - length;
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = '0';
        }
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i + i3] = string2.charAt(i3);
        }
        String str = new String(cArr);
        StringBuffer stringBuffer = new StringBuffer(string);
        stringBuffer.append(".");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public u01 add(BigInteger bigInteger) {
        return new u01(this.bigInt.add(bigInteger.shiftLeft(this.scale)), this.scale);
    }

    public int compareTo(BigInteger bigInteger) {
        return this.bigInt.compareTo(bigInteger.shiftLeft(this.scale));
    }

    public u01 divide(BigInteger bigInteger) {
        return new u01(this.bigInt.divide(bigInteger), this.scale);
    }

    public u01 multiply(BigInteger bigInteger) {
        return new u01(this.bigInt.multiply(bigInteger), this.scale);
    }

    public u01 subtract(BigInteger bigInteger) {
        return new u01(this.bigInt.subtract(bigInteger.shiftLeft(this.scale)), this.scale);
    }
}
