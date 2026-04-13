package org.bouncycastle.cert.path;

import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
class CertPathValidationResultBuilder {
    private final CertPathValidationContext context;
    private final List<Integer> certIndexes = new ArrayList();
    private final List<Integer> ruleIndexes = new ArrayList();
    private final List<CertPathValidationException> exceptions = new ArrayList();

    public CertPathValidationResultBuilder(CertPathValidationContext certPathValidationContext) {
        this.context = certPathValidationContext;
    }

    private int[] toInts(List<Integer> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 != size; i2++) {
            iArr[i2] = list.get(i2).intValue();
        }
        return iArr;
    }

    public void addException(int i2, int i3, CertPathValidationException certPathValidationException) {
        this.certIndexes.add(Integers.valueOf(i2));
        this.ruleIndexes.add(Integers.valueOf(i3));
        this.exceptions.add(certPathValidationException);
    }

    public CertPathValidationResult build() {
        if (this.exceptions.isEmpty()) {
            return new CertPathValidationResult(this.context);
        }
        CertPathValidationContext certPathValidationContext = this.context;
        int[] ints = toInts(this.certIndexes);
        int[] ints2 = toInts(this.ruleIndexes);
        List<CertPathValidationException> list = this.exceptions;
        return new CertPathValidationResult(certPathValidationContext, ints, ints2, (CertPathValidationException[]) list.toArray(new CertPathValidationException[list.size()]));
    }
}
