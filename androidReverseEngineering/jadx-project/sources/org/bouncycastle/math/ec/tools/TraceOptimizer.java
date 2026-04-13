package org.bouncycastle.math.ec.tools;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public class TraceOptimizer {
    private static final BigInteger ONE = BigInteger.valueOf(1);

    /* renamed from: R */
    private static final SecureRandom f1514R = new SecureRandom();

    private static int calculateTrace(ECFieldElement eCFieldElement) {
        int fieldSize = eCFieldElement.getFieldSize();
        int numberOfLeadingZeros = 31 - Integers.numberOfLeadingZeros(fieldSize);
        ECFieldElement eCFieldElement2 = eCFieldElement;
        int i2 = 1;
        while (numberOfLeadingZeros > 0) {
            eCFieldElement2 = eCFieldElement2.squarePow(i2).add(eCFieldElement2);
            numberOfLeadingZeros--;
            i2 = fieldSize >>> numberOfLeadingZeros;
            if ((i2 & 1) != 0) {
                eCFieldElement2 = eCFieldElement2.square().add(eCFieldElement);
            }
        }
        if (eCFieldElement2.isZero()) {
            return 0;
        }
        if (eCFieldElement2.isOne()) {
            return 1;
        }
        throw new IllegalStateException("Internal error in trace calculation");
    }

    private static ArrayList enumToList(Enumeration enumeration) {
        ArrayList arrayList = new ArrayList();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    public static void implPrintNonZeroTraceBits(X9ECParameters x9ECParameters) {
        PrintStream printStream;
        StringBuilder sb;
        ECCurve curve = x9ECParameters.getCurve();
        int fieldSize = curve.getFieldSize();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < fieldSize; i2++) {
            if ((i2 & 1) != 0 || i2 == 0) {
                if (calculateTrace(curve.fromBigInteger(ONE.shiftLeft(i2))) != 0) {
                    arrayList.add(Integers.valueOf(i2));
                    printStream = System.out;
                    sb = new StringBuilder(" ");
                    sb.append(i2);
                    printStream.print(sb.toString());
                }
            } else if (arrayList.contains(Integers.valueOf(i2 >>> 1))) {
                arrayList.add(Integers.valueOf(i2));
                printStream = System.out;
                sb = new StringBuilder(" ");
                sb.append(i2);
                printStream.print(sb.toString());
            }
        }
        System.out.println();
        for (int i3 = 0; i3 < 1000; i3++) {
            BigInteger bigInteger = new BigInteger(fieldSize, f1514R);
            int calculateTrace = calculateTrace(curve.fromBigInteger(bigInteger));
            int i4 = 0;
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                if (bigInteger.testBit(((Integer) arrayList.get(i5)).intValue())) {
                    i4 ^= 1;
                }
            }
            if (calculateTrace != i4) {
                throw new IllegalStateException("Optimized-trace sanity check failed");
            }
        }
    }

    public static void main(String[] strArr) {
        TreeSet treeSet = new TreeSet(enumToList(ECNamedCurveTable.getNames()));
        treeSet.addAll(enumToList(CustomNamedCurves.getNames()));
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            X9ECParameters byName = CustomNamedCurves.getByName(str);
            if (byName == null) {
                byName = ECNamedCurveTable.getByName(str);
            }
            if (byName != null && ECAlgorithms.isF2mCurve(byName.getCurve())) {
                System.out.print(str + ":");
                implPrintNonZeroTraceBits(byName);
            }
        }
    }

    public static void printNonZeroTraceBits(X9ECParameters x9ECParameters) {
        if (!ECAlgorithms.isF2mCurve(x9ECParameters.getCurve())) {
            throw new IllegalArgumentException("Trace only defined over characteristic-2 fields");
        }
        implPrintNonZeroTraceBits(x9ECParameters);
    }
}
