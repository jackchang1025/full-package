package p000;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.customview.view.AbsSavedState;
import androidx.fragment.app.Fragment$SavedState;
import androidx.viewpager.widget.ViewPager$SavedState;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.stateful.ExtendableSavedState;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: f2 */
/* loaded from: classes.dex */
public final class C0478f2 implements Parcelable.ClassLoaderCreator {

    /* renamed from: a0 */
    public final /* synthetic */ int f56136a0;

    @Override // android.os.Parcelable.ClassLoaderCreator
    public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        switch (this.f56136a0) {
            case 0:
                if (parcel.readParcelable(classLoader) == null) {
                    return AbsSavedState.f44960a1;
                }
                throw new IllegalStateException("superState must be null");
            case 1:
                return new ExtendableSavedState(parcel, classLoader);
            case 2:
                return new Fragment$SavedState(parcel, classLoader);
            case 3:
                return new ParcelableSparseArray(parcel, classLoader);
            default:
                return new ViewPager$SavedState(parcel, classLoader);
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        switch (this.f56136a0) {
            case 0:
                return new AbsSavedState[i];
            case 1:
                return new ExtendableSavedState[i];
            case 2:
                return new Fragment$SavedState[i];
            case 3:
                return new ParcelableSparseArray[i];
            default:
                return new ViewPager$SavedState[i];
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.f56136a0) {
            case 0:
                if (parcel.readParcelable(null) == null) {
                    return AbsSavedState.f44960a1;
                }
                throw new IllegalStateException("superState must be null");
            case 1:
                return new ExtendableSavedState(parcel, null);
            case 2:
                return new Fragment$SavedState(parcel, null);
            case 3:
                return new ParcelableSparseArray(parcel, null);
            default:
                return new ViewPager$SavedState(parcel, null);
        }
    }
}
