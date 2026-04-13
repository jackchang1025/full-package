package d;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.versionedparcelable.ParcelImpl;
import o0.g;

public final class a implements Creator {
   public final int a;

   public final Object createFromParcel(Parcel var1) {
      switch (this.a) {
         case 0:
            return new ParcelImpl(var1);
         case 1:
            return new o0.d(var1);
         default:
            return new g(var1);
      }
   }

   public final Object[] newArray(int var1) {
      switch (this.a) {
         case 0:
            return new ParcelImpl[var1];
         case 1:
            return new o0.d[var1];
         default:
            return new g[var1];
      }
   }
}
