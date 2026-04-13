package o0;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;

public final class g extends BaseSavedState {
   public static final Creator<g> CREATOR = new d.a(2);
   public final String a;
   public final int b;
   public final boolean c;
   public final boolean d;
   public final boolean e;

   public g(Parcel var1) {
      super(var1);
      this.a = var1.readString();
      this.b = var1.readInt();
      this.c = (Boolean)var1.readValue(null);
      this.d = (Boolean)var1.readValue(null);
      this.e = (Boolean)var1.readValue(null);
   }

   public g(Parcelable var1, String var2, int var3, boolean var4, boolean var5, boolean var6) {
      super(var1);
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.d = var5;
      this.e = var6;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeString(this.a);
      var1.writeInt(this.b);
      var1.writeValue(this.c);
      var1.writeValue(this.d);
      var1.writeValue(this.e);
   }
}
