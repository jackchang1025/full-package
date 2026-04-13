package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.util.SparseIntArray;
import java.lang.reflect.InvocationTargetException;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;
import p001d.AbstractC0257b;
import p001d.C0256a;
import p001d.C0258c;
import p001d.InterfaceC0259d;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new C0256a(0);

    /* renamed from: a */
    public final InterfaceC0259d f84a;

    public ParcelImpl(Parcel parcel) {
        parcel.dataPosition();
        int dataSize = parcel.dataSize();
        new SparseIntArray();
        String readString = parcel.readString();
        InterfaceC0259d interfaceC0259d = null;
        if (readString != null) {
            try {
                interfaceC0259d = (InterfaceC0259d) Class.forName(readString, true, AbstractC0257b.class.getClassLoader()).getDeclaredMethod("read", AbstractC0257b.class).invoke(null, new C0258c(parcel, parcel.dataPosition(), dataSize, AbstractC0000a.m30z(BuildConfig.FLAVOR, "  ")));
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
            } catch (InvocationTargetException e5) {
                if (!(e5.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
                }
                throw ((RuntimeException) e5.getCause());
            }
        }
        this.f84a = interfaceC0259d;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.dataPosition();
        int dataSize = parcel.dataSize();
        new SparseIntArray();
        InterfaceC0259d interfaceC0259d = this.f84a;
        if (interfaceC0259d == null) {
            parcel.writeString(null);
            return;
        }
        try {
            parcel.writeString(AbstractC0257b.m730a(interfaceC0259d.getClass()).getName());
            C0258c c0258c = new C0258c(parcel, parcel.dataPosition(), dataSize, "  ");
            try {
                AbstractC0257b.m730a(interfaceC0259d.getClass()).getDeclaredMethod("write", interfaceC0259d.getClass(), AbstractC0257b.class).invoke(null, interfaceC0259d, c0258c);
                int i3 = c0258c.f419d;
                if (i3 >= 0) {
                    int i4 = c0258c.f416a.get(i3);
                    Parcel parcel2 = c0258c.f417b;
                    int dataPosition = parcel2.dataPosition();
                    parcel2.setDataPosition(i4);
                    parcel2.writeInt(dataPosition - i4);
                    parcel2.setDataPosition(dataPosition);
                }
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e3);
            } catch (NoSuchMethodException e4) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
            } catch (InvocationTargetException e5) {
                if (!(e5.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e5);
                }
                throw ((RuntimeException) e5.getCause());
            }
        } catch (ClassNotFoundException e6) {
            throw new RuntimeException(interfaceC0259d.getClass().getSimpleName().concat(" does not have a Parcelizer"), e6);
        }
    }
}
