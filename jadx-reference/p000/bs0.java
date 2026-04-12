package p000;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.support.v4.os.ResultReceiver;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bs0 extends Binder implements s40 {

    /* renamed from: a4 */
    public static final /* synthetic */ int f45996a4 = 0;

    /* renamed from: a3 */
    public final /* synthetic */ ResultReceiver f45997a3;

    public bs0(ResultReceiver resultReceiver) {
        this.f45997a3 = resultReceiver;
        attachInterface(this, s40.f59863a0);
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        String str = s40.f59863a0;
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
        parcel.readInt();
        this.f45997a3.getClass();
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
