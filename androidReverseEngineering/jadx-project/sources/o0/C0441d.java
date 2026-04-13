package o0;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.Array;
import p000a.AbstractC0000a;
import p001d.C0256a;

/* renamed from: o0.d */
/* loaded from: classes.dex */
public final class C0441d implements Parcelable {
    public static final Parcelable.Creator<C0441d> CREATOR;

    /* renamed from: c */
    public static final C0441d[][] f989c;

    /* renamed from: a */
    public final int f990a;

    /* renamed from: b */
    public final int f991b;

    static {
        int i2 = C0445h.f1013K;
        f989c = (C0441d[][]) Array.newInstance((Class<?>) C0441d.class, i2, i2);
        for (int i3 = 0; i3 < C0445h.f1013K; i3++) {
            for (int i4 = 0; i4 < C0445h.f1013K; i4++) {
                f989c[i3][i4] = new C0441d(i3, i4);
            }
        }
        CREATOR = new C0256a(1);
    }

    public C0441d(int i2, int i3) {
        m1170a(i2, i3);
        this.f990a = i2;
        this.f991b = i3;
    }

    /* renamed from: a */
    public static void m1170a(int i2, int i3) {
        if (i2 >= 0) {
            int i4 = C0445h.f1013K;
            if (i2 <= i4 - 1) {
                if (i3 < 0 || i3 > i4 - 1) {
                    StringBuilder sb = new StringBuilder("mColumn must be in range 0-");
                    sb.append(C0445h.f1013K - 1);
                    throw new IllegalArgumentException(sb.toString());
                }
                return;
            }
        }
        StringBuilder sb2 = new StringBuilder("mRow must be in range 0-");
        sb2.append(C0445h.f1013K - 1);
        throw new IllegalArgumentException(sb2.toString());
    }

    /* renamed from: b */
    public static synchronized C0441d m1171b(int i2, int i3) {
        C0441d c0441d;
        synchronized (C0441d.class) {
            m1170a(i2, i3);
            c0441d = f989c[i2][i3];
        }
        return c0441d;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0441d)) {
            return super.equals(obj);
        }
        C0441d c0441d = (C0441d) obj;
        return this.f991b == c0441d.f991b && this.f990a == c0441d.f990a;
    }

    public final int hashCode() {
        return (this.f990a * 31) + this.f991b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("(Row = ");
        sb.append(this.f990a);
        sb.append(", Col = ");
        return AbstractC0000a.m17m(sb, this.f991b, ")");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f991b);
        parcel.writeInt(this.f990a);
    }

    public C0441d(Parcel parcel) {
        this.f991b = parcel.readInt();
        this.f990a = parcel.readInt();
    }
}
