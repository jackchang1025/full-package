package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.IconCompat;
import p001d.AbstractC0257b;
import p001d.C0258c;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class IconCompatParcelizer {
    public static IconCompat read(AbstractC0257b abstractC0257b) {
        IconCompat iconCompat = new IconCompat();
        iconCompat.mType = abstractC0257b.m732c(iconCompat.mType, 1);
        byte[] bArr = iconCompat.mData;
        if (abstractC0257b.mo731b(2)) {
            Parcel parcel = ((C0258c) abstractC0257b).f417b;
            int readInt = parcel.readInt();
            if (readInt < 0) {
                bArr = null;
            } else {
                byte[] bArr2 = new byte[readInt];
                parcel.readByteArray(bArr2);
                bArr = bArr2;
            }
        }
        iconCompat.mData = bArr;
        Parcelable parcelable = iconCompat.mParcelable;
        if (abstractC0257b.mo731b(3)) {
            parcelable = ((C0258c) abstractC0257b).f417b.readParcelable(C0258c.class.getClassLoader());
        }
        iconCompat.mParcelable = parcelable;
        iconCompat.mInt1 = abstractC0257b.m732c(iconCompat.mInt1, 4);
        iconCompat.mInt2 = abstractC0257b.m732c(iconCompat.mInt2, 5);
        Parcelable parcelable2 = iconCompat.mTintList;
        if (abstractC0257b.mo731b(6)) {
            parcelable2 = ((C0258c) abstractC0257b).f417b.readParcelable(C0258c.class.getClassLoader());
        }
        iconCompat.mTintList = (ColorStateList) parcelable2;
        String str = iconCompat.mTintModeStr;
        if (abstractC0257b.mo731b(7)) {
            str = ((C0258c) abstractC0257b).f417b.readString();
        }
        iconCompat.mTintModeStr = str;
        iconCompat.onPostParceling();
        return iconCompat;
    }

    public static void write(IconCompat iconCompat, AbstractC0257b abstractC0257b) {
        abstractC0257b.getClass();
        iconCompat.onPreParceling(false);
        int i2 = iconCompat.mType;
        abstractC0257b.mo733d(1);
        Parcel parcel = ((C0258c) abstractC0257b).f417b;
        parcel.writeInt(i2);
        byte[] bArr = iconCompat.mData;
        abstractC0257b.mo733d(2);
        if (bArr != null) {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(bArr);
        } else {
            parcel.writeInt(-1);
        }
        Parcelable parcelable = iconCompat.mParcelable;
        abstractC0257b.mo733d(3);
        parcel.writeParcelable(parcelable, 0);
        int i3 = iconCompat.mInt1;
        abstractC0257b.mo733d(4);
        parcel.writeInt(i3);
        int i4 = iconCompat.mInt2;
        abstractC0257b.mo733d(5);
        parcel.writeInt(i4);
        ColorStateList colorStateList = iconCompat.mTintList;
        abstractC0257b.mo733d(6);
        parcel.writeParcelable(colorStateList, 0);
        String str = iconCompat.mTintModeStr;
        abstractC0257b.mo733d(7);
        parcel.writeString(str);
    }
}
