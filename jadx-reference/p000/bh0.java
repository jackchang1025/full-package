package p000;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.room.MultiInstanceInvalidationService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bh0 extends Binder implements IInterface {

    /* renamed from: a3 */
    public final /* synthetic */ MultiInstanceInvalidationService f45893a3;

    public bh0(MultiInstanceInvalidationService multiInstanceInvalidationService) {
        this.f45893a3 = multiInstanceInvalidationService;
        attachInterface(this, "androidx.room.IMultiInstanceInvalidationService");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("androidx.room.IMultiInstanceInvalidationService");
        }
        if (i == 1598968902) {
            parcel2.writeString("androidx.room.IMultiInstanceInvalidationService");
            return true;
        }
        p40 p40Var = null;
        if (i == 1) {
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("androidx.room.IMultiInstanceInvalidationCallback");
                if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof p40)) {
                    p40Var = new p40();
                    p40Var.f59154a3 = strongBinder;
                } else {
                    p40Var = (p40) iInterfaceQueryLocalInterface;
                }
            }
            String string = parcel.readString();
            t60.m214695b6(p40Var, "callback");
            int i3 = 0;
            if (string != null) {
                MultiInstanceInvalidationService multiInstanceInvalidationService = this.f45893a3;
                synchronized (multiInstanceInvalidationService.f45363a2) {
                    try {
                        int i4 = multiInstanceInvalidationService.f45361a0 + 1;
                        multiInstanceInvalidationService.f45361a0 = i4;
                        if (multiInstanceInvalidationService.f45363a2.register(p40Var, Integer.valueOf(i4))) {
                            multiInstanceInvalidationService.f45362a1.put(Integer.valueOf(i4), string);
                            i3 = i4;
                        } else {
                            multiInstanceInvalidationService.f45361a0--;
                        }
                    } finally {
                    }
                }
            }
            parcel2.writeNoException();
            parcel2.writeInt(i3);
            return true;
        }
        if (i == 2) {
            IBinder strongBinder2 = parcel.readStrongBinder();
            if (strongBinder2 != null) {
                IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("androidx.room.IMultiInstanceInvalidationCallback");
                if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof p40)) {
                    p40Var = new p40();
                    p40Var.f59154a3 = strongBinder2;
                } else {
                    p40Var = (p40) iInterfaceQueryLocalInterface2;
                }
            }
            int i5 = parcel.readInt();
            t60.m214695b6(p40Var, "callback");
            MultiInstanceInvalidationService multiInstanceInvalidationService2 = this.f45893a3;
            synchronized (multiInstanceInvalidationService2.f45363a2) {
                multiInstanceInvalidationService2.f45363a2.unregister(p40Var);
            }
            parcel2.writeNoException();
            return true;
        }
        if (i != 3) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        int i6 = parcel.readInt();
        String[] strArrCreateStringArray = parcel.createStringArray();
        t60.m214695b6(strArrCreateStringArray, "tables");
        MultiInstanceInvalidationService multiInstanceInvalidationService3 = this.f45893a3;
        synchronized (multiInstanceInvalidationService3.f45363a2) {
            try {
                String str = (String) multiInstanceInvalidationService3.f45362a1.get(Integer.valueOf(i6));
                if (str != null) {
                    int iBeginBroadcast = multiInstanceInvalidationService3.f45363a2.beginBroadcast();
                    for (int i7 = 0; i7 < iBeginBroadcast; i7++) {
                        try {
                            Object broadcastCookie = multiInstanceInvalidationService3.f45363a2.getBroadcastCookie(i7);
                            t60.m214693b4(broadcastCookie, "null cannot be cast to non-null type kotlin.Int");
                            Integer num = (Integer) broadcastCookie;
                            int iIntValue = num.intValue();
                            String str2 = (String) multiInstanceInvalidationService3.f45362a1.get(num);
                            if (i6 != iIntValue && str.equals(str2)) {
                                try {
                                    ((p40) multiInstanceInvalidationService3.f45363a2.getBroadcastItem(i7)).m214240a0(strArrCreateStringArray);
                                } catch (RemoteException unused) {
                                }
                            }
                        } finally {
                            multiInstanceInvalidationService3.f45363a2.finishBroadcast();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
