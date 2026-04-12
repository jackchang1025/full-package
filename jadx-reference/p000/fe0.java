package p000;

/* loaded from: classes2.dex */
public abstract class fe0 {
    public static final char MATRIX_TYPE_RANDOM_LT = 'L';
    public static final char MATRIX_TYPE_RANDOM_REGULAR = 'R';
    public static final char MATRIX_TYPE_RANDOM_UT = 'U';
    public static final char MATRIX_TYPE_UNIT = 'I';
    public static final char MATRIX_TYPE_ZERO = 'Z';
    protected int numColumns;
    protected int numRows;

    public abstract fe0 computeInverse();

    public abstract byte[] getEncoded();

    public int getNumColumns() {
        return this.numColumns;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public abstract boolean isZero();

    public abstract i91 leftMultiply(i91 i91Var);

    public abstract fe0 rightMultiply(fe0 fe0Var);

    public abstract fe0 rightMultiply(kn0 kn0Var);

    public abstract i91 rightMultiply(i91 i91Var);

    public abstract String toString();
}
