package p000;

/* loaded from: classes2.dex */
public abstract class uh0 {
    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        qh0.mul(iArr, iArr2, iArr3);
        qh0.mul(iArr, 6, iArr2, 6, iArr3, 12);
        int iAddToEachOther = qh0.addToEachOther(iArr3, 6, iArr3, 12);
        int iAddTo = qh0.addTo(iArr3, 18, iArr3, 12, qh0.addTo(iArr3, 0, iArr3, 6, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = qh0.create();
        int[] iArrCreate2 = qh0.create();
        boolean z = qh0.diff(iArr, 6, iArr, 0, iArrCreate, 0) != qh0.diff(iArr2, 6, iArr2, 0, iArrCreate2, 0);
        int[] iArrCreateExt = qh0.createExt();
        qh0.mul(iArrCreate, iArrCreate2, iArrCreateExt);
        yh0.addWordAt(24, iAddTo + (z ? yh0.addTo(12, iArrCreateExt, 0, iArr3, 6) : yh0.subFrom(12, iArrCreateExt, 0, iArr3, 6)), iArr3, 18);
    }

    public static void square(int[] iArr, int[] iArr2) {
        qh0.square(iArr, iArr2);
        qh0.square(iArr, 6, iArr2, 12);
        int iAddToEachOther = qh0.addToEachOther(iArr2, 6, iArr2, 12);
        int iAddTo = qh0.addTo(iArr2, 18, iArr2, 12, qh0.addTo(iArr2, 0, iArr2, 6, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = qh0.create();
        qh0.diff(iArr, 6, iArr, 0, iArrCreate, 0);
        int[] iArrCreateExt = qh0.createExt();
        qh0.square(iArrCreate, iArrCreateExt);
        yh0.addWordAt(24, yh0.subFrom(12, iArrCreateExt, 0, iArr2, 6) + iAddTo, iArr2, 18);
    }
}
