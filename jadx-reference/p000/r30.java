package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class r30 extends t80 {
    private final o90[] lmsParameters;

    public r30(o90[] o90VarArr, SecureRandom secureRandom) {
        super(secureRandom, xb0.calculateStrength(o90VarArr[0]));
        if (o90VarArr.length == 0 || o90VarArr.length > 8) {
            throw new IllegalArgumentException("lmsParameters length should be between 1 and 8 inclusive");
        }
        this.lmsParameters = o90VarArr;
    }

    public int getDepth() {
        return this.lmsParameters.length;
    }

    public o90[] getLmsParameters() {
        return this.lmsParameters;
    }
}
