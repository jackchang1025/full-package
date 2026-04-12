package p000;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.core.content.UnusedAppRestrictionsBackportService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class a91 extends Binder implements w40 {

    /* renamed from: a3 */
    public final /* synthetic */ UnusedAppRestrictionsBackportService f55a3;

    public a91(UnusedAppRestrictionsBackportService unusedAppRestrictionsBackportService) {
        this.f55a3 = unusedAppRestrictionsBackportService;
        attachInterface(this, w40.f60769a2);
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        v40 v40Var;
        String str = w40.f60769a2;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface(str);
        }
        if (i == 1598968902) {
            parcel2.writeString(str);
            return true;
        }
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        IBinder strongBinder = parcel.readStrongBinder();
        if (strongBinder == null) {
            v40Var = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface(v40.f60574a1);
            if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof v40)) {
                u40 u40Var = new u40();
                u40Var.f60323a3 = strongBinder;
                v40Var = u40Var;
            } else {
                v40Var = (v40) iInterfaceQueryLocalInterface;
            }
        }
        if (v40Var == null) {
            return true;
        }
        this.f55a3.m210079a0();
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
