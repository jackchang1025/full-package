package p001d;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.versionedparcelable.ParcelImpl;
import o0.C0441d;
import o0.C0444g;

/* renamed from: d.a */
/* loaded from: classes.dex */
public final class C0256a implements Parcelable.Creator {

    /* renamed from: a */
    public final /* synthetic */ int f415a;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.f415a) {
            case 0:
                return new ParcelImpl(parcel);
            case 1:
                return new C0441d(parcel);
            default:
                return new C0444g(parcel);
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        switch (this.f415a) {
            case 0:
                return new ParcelImpl[i2];
            case 1:
                return new C0441d[i2];
            default:
                return new C0444g[i2];
        }
    }
}
