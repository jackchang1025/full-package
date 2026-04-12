package p000;

/* loaded from: classes2.dex */
public abstract class wh0 {
    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        sh0.mul(iArr, iArr2, iArr3);
        sh0.mul(iArr, 8, iArr2, 8, iArr3, 16);
        int iAddToEachOther = sh0.addToEachOther(iArr3, 8, iArr3, 16);
        int iAddTo = sh0.addTo(iArr3, 24, iArr3, 16, sh0.addTo(iArr3, 0, iArr3, 8, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        boolean z = sh0.diff(iArr, 8, iArr, 0, iArrCreate, 0) != sh0.diff(iArr2, 8, iArr2, 0, iArrCreate2, 0);
        int[] iArrCreateExt = sh0.createExt();
        sh0.mul(iArrCreate, iArrCreate2, iArrCreateExt);
        yh0.addWordAt(32, iAddTo + (z ? yh0.addTo(16, iArrCreateExt, 0, iArr3, 8) : yh0.subFrom(16, iArrCreateExt, 0, iArr3, 8)), iArr3, 24);
    }

    public static void square(int[] iArr, int[] iArr2) {
        sh0.square(iArr, iArr2);
        sh0.square(iArr, 8, iArr2, 16);
        int iAddToEachOther = sh0.addToEachOther(iArr2, 8, iArr2, 16);
        int iAddTo = sh0.addTo(iArr2, 24, iArr2, 16, sh0.addTo(iArr2, 0, iArr2, 8, 0) + iAddToEachOther) + iAddToEachOther;
        int[] iArrCreate = sh0.create();
        sh0.diff(iArr, 8, iArr, 0, iArrCreate, 0);
        int[] iArrCreateExt = sh0.createExt();
        sh0.square(iArrCreate, iArrCreateExt);
        yh0.addWordAt(32, yh0.subFrom(16, iArrCreateExt, 0, iArr2, 8) + iAddTo, iArr2, 24);
    }
}
