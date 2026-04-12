package p000;

/* loaded from: classes2.dex */
public class un0 {
    private z10 field;

    /* renamed from: p */
    private sn0 f60482p;
    protected sn0[] sqMatrix;
    protected sn0[] sqRootMatrix;

    public un0(z10 z10Var, sn0 sn0Var) {
        this.field = z10Var;
        this.f60482p = sn0Var;
        computeSquaringMatrix();
        computeSquareRootMatrix();
    }

    private void computeSquareRootMatrix() {
        int coefficient;
        int degree = this.f60482p.getDegree();
        sn0[] sn0VarArr = new sn0[degree];
        int i = degree - 1;
        for (int i2 = i; i2 >= 0; i2--) {
            sn0VarArr[i2] = new sn0(this.sqMatrix[i2]);
        }
        this.sqRootMatrix = new sn0[degree];
        while (i >= 0) {
            this.sqRootMatrix[i] = new sn0(this.field, i);
            i--;
        }
        for (int i3 = 0; i3 < degree; i3++) {
            if (sn0VarArr[i3].getCoefficient(i3) == 0) {
                int i4 = i3 + 1;
                boolean z = false;
                while (i4 < degree) {
                    if (sn0VarArr[i4].getCoefficient(i3) != 0) {
                        swapColumns(sn0VarArr, i3, i4);
                        swapColumns(this.sqRootMatrix, i3, i4);
                        i4 = degree;
                        z = true;
                    }
                    i4++;
                }
                if (!z) {
                    throw new ArithmeticException("Squaring matrix is not invertible.");
                }
            }
            int iInverse = this.field.inverse(sn0VarArr[i3].getCoefficient(i3));
            sn0VarArr[i3].multThisWithElement(iInverse);
            this.sqRootMatrix[i3].multThisWithElement(iInverse);
            for (int i5 = 0; i5 < degree; i5++) {
                if (i5 != i3 && (coefficient = sn0VarArr[i5].getCoefficient(i3)) != 0) {
                    sn0 sn0VarMultWithElement = sn0VarArr[i3].multWithElement(coefficient);
                    sn0 sn0VarMultWithElement2 = this.sqRootMatrix[i3].multWithElement(coefficient);
                    sn0VarArr[i5].addToThis(sn0VarMultWithElement);
                    this.sqRootMatrix[i5].addToThis(sn0VarMultWithElement2);
                }
            }
        }
    }

    private void computeSquaringMatrix() {
        int i;
        int degree = this.f60482p.getDegree();
        this.sqMatrix = new sn0[degree];
        int i2 = 0;
        while (true) {
            i = degree >> 1;
            if (i2 >= i) {
                break;
            }
            int i3 = i2 << 1;
            int[] iArr = new int[i3 + 1];
            iArr[i3] = 1;
            this.sqMatrix[i2] = new sn0(this.field, iArr);
            i2++;
        }
        while (i < degree) {
            int i4 = i << 1;
            int[] iArr2 = new int[i4 + 1];
            iArr2[i4] = 1;
            this.sqMatrix[i] = new sn0(this.field, iArr2).mod(this.f60482p);
            i++;
        }
    }

    private static void swapColumns(sn0[] sn0VarArr, int i, int i2) {
        sn0 sn0Var = sn0VarArr[i];
        sn0VarArr[i] = sn0VarArr[i2];
        sn0VarArr[i2] = sn0Var;
    }

    public sn0[] getSquareRootMatrix() {
        return this.sqRootMatrix;
    }

    public sn0[] getSquaringMatrix() {
        return this.sqMatrix;
    }
}
