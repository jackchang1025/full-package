package android.support.v4.os;

import android.os.Parcel;

/* loaded from: classes.dex */
public final class ParcelCompat {
    private ParcelCompat() {
    }

    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z2) {
        parcel.writeInt(z2 ? 1 : 0);
    }
}
