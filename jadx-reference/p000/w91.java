package p000;

import android.os.Parcel;
import android.util.SparseIntArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w91 extends v91 {

    /* renamed from: a3 */
    public final SparseIntArray f60861a3;

    /* renamed from: a4 */
    public final Parcel f60862a4;

    /* renamed from: a5 */
    public final int f60863a5;

    /* renamed from: a6 */
    public final int f60864a6;

    /* renamed from: a7 */
    public final String f60865a7;

    /* renamed from: a8 */
    public int f60866a8;

    /* renamed from: a9 */
    public int f60867a9;

    /* renamed from: b0 */
    public int f60868b0;

    public w91(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new C0130bd(), new C0130bd(), new C0130bd());
    }

    @Override // p000.v91
    /* renamed from: a0 */
    public final w91 mo214911a0() {
        Parcel parcel = this.f60862a4;
        int iDataPosition = parcel.dataPosition();
        int i = this.f60867a9;
        if (i == this.f60863a5) {
            i = this.f60864a6;
        }
        return new w91(parcel, iDataPosition, i, AbstractC0003a2.m35b6(new StringBuilder(), this.f60865a7, "  "), this.f60608a0, this.f60609a1, this.f60610a2);
    }

    @Override // p000.v91
    /* renamed from: a4 */
    public final boolean mo214915a4(int i) {
        while (this.f60867a9 < this.f60864a6) {
            int i2 = this.f60868b0;
            if (i2 == i) {
                return true;
            }
            if (String.valueOf(i2).compareTo(String.valueOf(i)) > 0) {
                return false;
            }
            int i3 = this.f60867a9;
            Parcel parcel = this.f60862a4;
            parcel.setDataPosition(i3);
            int i4 = parcel.readInt();
            this.f60868b0 = parcel.readInt();
            this.f60867a9 += i4;
        }
        return this.f60868b0 == i;
    }

    @Override // p000.v91
    /* renamed from: a7 */
    public final void mo214918a7(int i) {
        int i2 = this.f60866a8;
        SparseIntArray sparseIntArray = this.f60861a3;
        Parcel parcel = this.f60862a4;
        if (i2 >= 0) {
            int i3 = sparseIntArray.get(i2);
            int iDataPosition = parcel.dataPosition();
            parcel.setDataPosition(i3);
            parcel.writeInt(iDataPosition - i3);
            parcel.setDataPosition(iDataPosition);
        }
        this.f60866a8 = i;
        sparseIntArray.put(i, parcel.dataPosition());
        parcel.writeInt(0);
        parcel.writeInt(i);
    }

    public w91(Parcel parcel, int i, int i2, String str, C0130bd c0130bd, C0130bd c0130bd2, C0130bd c0130bd3) {
        super(c0130bd, c0130bd2, c0130bd3);
        this.f60861a3 = new SparseIntArray();
        this.f60866a8 = -1;
        this.f60868b0 = -1;
        this.f60862a4 = parcel;
        this.f60863a5 = i;
        this.f60864a6 = i2;
        this.f60867a9 = i;
        this.f60865a7 = str;
    }
}
