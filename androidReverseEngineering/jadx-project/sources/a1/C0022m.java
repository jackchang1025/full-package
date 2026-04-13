package a1;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.RandomAccess;

/* renamed from: a1.m */
/* loaded from: classes.dex */
public final class C0022m extends AbstractList implements RandomAccess {

    /* renamed from: c */
    public static final /* synthetic */ int f39c = 0;

    /* renamed from: a */
    public final C0017h[] f40a;

    /* renamed from: b */
    public final int[] f41b;

    public C0022m(C0017h[] c0017hArr, int[] iArr) {
        this.f40a = c0017hArr;
        this.f41b = iArr;
    }

    /* renamed from: a */
    public static void m141a(long j2, C0014e c0014e, int i2, ArrayList arrayList, int i3, int i4, ArrayList arrayList2) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        C0014e c0014e2;
        if (i3 >= i4) {
            throw new AssertionError();
        }
        for (int i10 = i3; i10 < i4; i10++) {
            if (((C0017h) arrayList.get(i10)).mo125j() < i2) {
                throw new AssertionError();
            }
        }
        C0017h c0017h = (C0017h) arrayList.get(i3);
        C0017h c0017h2 = (C0017h) arrayList.get(i4 - 1);
        if (i2 == c0017h.mo125j()) {
            int i11 = i3 + 1;
            i6 = i11;
            i5 = ((Integer) arrayList2.get(i3)).intValue();
            c0017h = (C0017h) arrayList.get(i11);
        } else {
            i5 = -1;
            i6 = i3;
        }
        long j3 = 4;
        if (c0017h.mo121e(i2) == c0017h2.mo121e(i2)) {
            int min = Math.min(c0017h.mo125j(), c0017h2.mo125j());
            int i12 = 0;
            for (int i13 = i2; i13 < min && c0017h.mo121e(i13) == c0017h2.mo121e(i13); i13++) {
                i12++;
            }
            long j4 = 1 + j2 + ((int) (c0014e.f22b / 4)) + 2 + i12;
            c0014e.m89M(-i12);
            c0014e.m89M(i5);
            int i14 = i2;
            while (true) {
                i7 = i2 + i12;
                if (i14 >= i7) {
                    break;
                }
                c0014e.m89M(c0017h.mo121e(i14) & 255);
                i14++;
            }
            if (i6 + 1 == i4) {
                if (i7 != ((C0017h) arrayList.get(i6)).mo125j()) {
                    throw new AssertionError();
                }
                c0014e.m89M(((Integer) arrayList2.get(i6)).intValue());
                return;
            } else {
                C0014e c0014e3 = new C0014e();
                c0014e.m89M((int) ((((int) (c0014e3.f22b / 4)) + j4) * (-1)));
                m141a(j4, c0014e3, i7, arrayList, i6, i4, arrayList2);
                c0014e.mo67i(c0014e3, c0014e3.f22b);
                return;
            }
        }
        int i15 = 1;
        for (int i16 = i6 + 1; i16 < i4; i16++) {
            if (((C0017h) arrayList.get(i16 - 1)).mo121e(i2) != ((C0017h) arrayList.get(i16)).mo121e(i2)) {
                i15++;
            }
        }
        long j5 = j2 + ((int) (c0014e.f22b / 4)) + 2 + (i15 * 2);
        c0014e.m89M(i15);
        c0014e.m89M(i5);
        for (int i17 = i6; i17 < i4; i17++) {
            byte mo121e = ((C0017h) arrayList.get(i17)).mo121e(i2);
            if (i17 == i6 || mo121e != ((C0017h) arrayList.get(i17 - 1)).mo121e(i2)) {
                c0014e.m89M(mo121e & 255);
            }
        }
        C0014e c0014e4 = new C0014e();
        int i18 = i6;
        while (i18 < i4) {
            byte mo121e2 = ((C0017h) arrayList.get(i18)).mo121e(i2);
            int i19 = i18 + 1;
            int i20 = i19;
            while (true) {
                if (i20 >= i4) {
                    i8 = i4;
                    break;
                } else {
                    if (mo121e2 != ((C0017h) arrayList.get(i20)).mo121e(i2)) {
                        i8 = i20;
                        break;
                    }
                    i20++;
                }
            }
            if (i19 == i8 && i2 + 1 == ((C0017h) arrayList.get(i18)).mo125j()) {
                c0014e.m89M(((Integer) arrayList2.get(i18)).intValue());
                i9 = i8;
                c0014e2 = c0014e4;
            } else {
                c0014e.m89M((int) ((((int) (c0014e4.f22b / j3)) + j5) * (-1)));
                i9 = i8;
                c0014e2 = c0014e4;
                m141a(j5, c0014e4, i2 + 1, arrayList, i18, i8, arrayList2);
            }
            c0014e4 = c0014e2;
            i18 = i9;
            j3 = 4;
        }
        C0014e c0014e5 = c0014e4;
        c0014e.mo67i(c0014e5, c0014e5.f22b);
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i2) {
        return this.f40a[i2];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.f40a.length;
    }
}
