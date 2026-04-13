package p001d;

import android.os.Parcel;
import android.util.SparseIntArray;

/* renamed from: d.c */
/* loaded from: classes.dex */
public final class C0258c extends AbstractC0257b {

    /* renamed from: b */
    public final Parcel f417b;

    /* renamed from: c */
    public final int f418c;

    /* renamed from: e */
    public int f420e;

    /* renamed from: a */
    public final SparseIntArray f416a = new SparseIntArray();

    /* renamed from: d */
    public int f419d = -1;

    public C0258c(Parcel parcel, int i2, int i3, String str) {
        this.f417b = parcel;
        this.f418c = i3;
        this.f420e = i2;
    }

    @Override // p001d.AbstractC0257b
    /* renamed from: b */
    public final boolean mo731b(int i2) {
        Parcel parcel;
        int i3;
        while (true) {
            int i4 = this.f420e;
            int i5 = this.f418c;
            parcel = this.f417b;
            if (i4 >= i5) {
                i3 = -1;
                break;
            }
            parcel.setDataPosition(i4);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            this.f420e += readInt;
            if (readInt2 == i2) {
                i3 = parcel.dataPosition();
                break;
            }
        }
        if (i3 == -1) {
            return false;
        }
        parcel.setDataPosition(i3);
        return true;
    }

    @Override // p001d.AbstractC0257b
    /* renamed from: d */
    public final void mo733d(int i2) {
        int i3 = this.f419d;
        SparseIntArray sparseIntArray = this.f416a;
        Parcel parcel = this.f417b;
        if (i3 >= 0) {
            int i4 = sparseIntArray.get(i3);
            int dataPosition = parcel.dataPosition();
            parcel.setDataPosition(i4);
            parcel.writeInt(dataPosition - i4);
            parcel.setDataPosition(dataPosition);
        }
        this.f419d = i2;
        sparseIntArray.put(i2, parcel.dataPosition());
        parcel.writeInt(0);
        parcel.writeInt(i2);
    }
}
