package o0;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import p001d.C0256a;

/* renamed from: o0.g */
/* loaded from: classes.dex */
public final class C0444g extends View.BaseSavedState {
    public static final Parcelable.Creator<C0444g> CREATOR = new C0256a(2);

    /* renamed from: a */
    public final String f1008a;

    /* renamed from: b */
    public final int f1009b;

    /* renamed from: c */
    public final boolean f1010c;

    /* renamed from: d */
    public final boolean f1011d;

    /* renamed from: e */
    public final boolean f1012e;

    public C0444g(Parcel parcel) {
        super(parcel);
        this.f1008a = parcel.readString();
        this.f1009b = parcel.readInt();
        this.f1010c = ((Boolean) parcel.readValue(null)).booleanValue();
        this.f1011d = ((Boolean) parcel.readValue(null)).booleanValue();
        this.f1012e = ((Boolean) parcel.readValue(null)).booleanValue();
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.f1008a);
        parcel.writeInt(this.f1009b);
        parcel.writeValue(Boolean.valueOf(this.f1010c));
        parcel.writeValue(Boolean.valueOf(this.f1011d));
        parcel.writeValue(Boolean.valueOf(this.f1012e));
    }

    public C0444g(Parcelable parcelable, String str, int i2, boolean z2, boolean z3, boolean z4) {
        super(parcelable);
        this.f1008a = str;
        this.f1009b = i2;
        this.f1010c = z2;
        this.f1011d = z3;
        this.f1012e = z4;
    }
}
