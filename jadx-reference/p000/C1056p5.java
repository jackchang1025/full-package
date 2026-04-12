package p000;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ResultReceiver;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.IntentSenderRequest;
import androidx.versionedparcelable.ParcelImpl;
import com.google.android.material.badge.BadgeState$State;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.RangeDateSelector;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.android.material.internal.ParcelableSparseBooleanArray;
import com.google.android.material.internal.ParcelableSparseIntArray;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p5 */
/* loaded from: classes.dex */
public final class C1056p5 implements Parcelable.Creator {

    /* renamed from: a0 */
    public final /* synthetic */ int f59156a0;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int i = 0;
        s40 s40Var = null;
        switch (this.f59156a0) {
            case 0:
                return new ActivityResult(parcel);
            case 1:
                BadgeState$State badgeState$State = new BadgeState$State();
                badgeState$State.f49111a8 = v10.MASK;
                badgeState$State.f49112a9 = -2;
                badgeState$State.f49113b0 = -2;
                badgeState$State.f49119b6 = Boolean.TRUE;
                badgeState$State.f49103a0 = parcel.readInt();
                badgeState$State.f49104a1 = (Integer) parcel.readSerializable();
                badgeState$State.f49105a2 = (Integer) parcel.readSerializable();
                badgeState$State.f49106a3 = (Integer) parcel.readSerializable();
                badgeState$State.f49107a4 = (Integer) parcel.readSerializable();
                badgeState$State.f49108a5 = (Integer) parcel.readSerializable();
                badgeState$State.f49109a6 = (Integer) parcel.readSerializable();
                badgeState$State.f49110a7 = (Integer) parcel.readSerializable();
                badgeState$State.f49111a8 = parcel.readInt();
                badgeState$State.f49112a9 = parcel.readInt();
                badgeState$State.f49113b0 = parcel.readInt();
                badgeState$State.f49115b2 = parcel.readString();
                badgeState$State.f49116b3 = parcel.readInt();
                badgeState$State.f49118b5 = (Integer) parcel.readSerializable();
                badgeState$State.f49120b7 = (Integer) parcel.readSerializable();
                badgeState$State.f49121b8 = (Integer) parcel.readSerializable();
                badgeState$State.f49122b9 = (Integer) parcel.readSerializable();
                badgeState$State.f49123c0 = (Integer) parcel.readSerializable();
                badgeState$State.f49124c1 = (Integer) parcel.readSerializable();
                badgeState$State.f49125c2 = (Integer) parcel.readSerializable();
                badgeState$State.f49119b6 = (Boolean) parcel.readSerializable();
                badgeState$State.f49114b1 = (Locale) parcel.readSerializable();
                return badgeState$State;
            case 2:
                return new DateValidatorPointBackward(parcel.readLong());
            case 3:
                return new DateValidatorPointForward(parcel.readLong());
            case 4:
                return new IntentSenderRequest(parcel);
            case 5:
                return new ParcelImpl(parcel);
            case 6:
                int i2 = parcel.readInt();
                ParcelableSparseBooleanArray parcelableSparseBooleanArray = new ParcelableSparseBooleanArray(i2);
                int[] iArr = new int[i2];
                boolean[] zArr = new boolean[i2];
                parcel.readIntArray(iArr);
                parcel.readBooleanArray(zArr);
                while (i < i2) {
                    parcelableSparseBooleanArray.put(iArr[i], zArr[i]);
                    i++;
                }
                return parcelableSparseBooleanArray;
            case 7:
                int i3 = parcel.readInt();
                ParcelableSparseIntArray parcelableSparseIntArray = new ParcelableSparseIntArray(i3);
                int[] iArr2 = new int[i3];
                int[] iArr3 = new int[i3];
                parcel.readIntArray(iArr2);
                parcel.readIntArray(iArr3);
                while (i < i3) {
                    parcelableSparseIntArray.put(iArr2[i], iArr3[i]);
                    i++;
                }
                return parcelableSparseIntArray;
            case 8:
                RangeDateSelector rangeDateSelector = new RangeDateSelector();
                rangeDateSelector.f49398a2 = null;
                rangeDateSelector.f49399a3 = null;
                rangeDateSelector.f49400a4 = null;
                rangeDateSelector.f49401a5 = null;
                rangeDateSelector.f49398a2 = (Long) parcel.readValue(Long.class.getClassLoader());
                rangeDateSelector.f49399a3 = (Long) parcel.readValue(Long.class.getClassLoader());
                return rangeDateSelector;
            case 9:
                ResultReceiver resultReceiver = new ResultReceiver();
                IBinder strongBinder = parcel.readStrongBinder();
                int i4 = bs0.f45996a4;
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface(s40.f59863a0);
                    if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof s40)) {
                        r40 r40Var = new r40();
                        r40Var.f59624a3 = strongBinder;
                        s40Var = r40Var;
                    } else {
                        s40Var = (s40) iInterfaceQueryLocalInterface;
                    }
                }
                resultReceiver.f43730a0 = s40Var;
                return resultReceiver;
            default:
                SingleDateSelector singleDateSelector = new SingleDateSelector();
                singleDateSelector.f49403a1 = (Long) parcel.readValue(Long.class.getClassLoader());
                return singleDateSelector;
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        switch (this.f59156a0) {
            case 0:
                return new ActivityResult[i];
            case 1:
                return new BadgeState$State[i];
            case 2:
                return new DateValidatorPointBackward[i];
            case 3:
                return new DateValidatorPointForward[i];
            case 4:
                return new IntentSenderRequest[i];
            case 5:
                return new ParcelImpl[i];
            case 6:
                return new ParcelableSparseBooleanArray[i];
            case 7:
                return new ParcelableSparseIntArray[i];
            case 8:
                return new RangeDateSelector[i];
            case 9:
                return new ResultReceiver[i];
            default:
                return new SingleDateSelector[i];
        }
    }
}
