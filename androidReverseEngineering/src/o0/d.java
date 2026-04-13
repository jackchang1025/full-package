package o0;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class d implements Parcelable {
   public static final Creator<d> CREATOR;
   public static final d[][] c;
   public final int a;
   public final int b;

   static {
      int var0 = h.K;
      c = new d[var0][var0];

      for (int var2 = 0; var2 < h.K; var2++) {
         for (int var1 = 0; var1 < h.K; var1++) {
            c[var2][var1] = new d(var2, var1);
         }
      }

      CREATOR = new d.a(1);
   }

   public d(int var1, int var2) {
      a(var1, var2);
      this.a = var1;
      this.b = var2;
   }

   public d(Parcel var1) {
      this.b = var1.readInt();
      this.a = var1.readInt();
   }

   public static void a(int var0, int var1) {
      if (var0 >= 0) {
         int var2 = h.K;
         if (var0 <= var2 - 1) {
            if (var1 >= 0 && var1 <= var2 - 1) {
               return;
            }

            StringBuilder var4 = new StringBuilder("mColumn must be in range 0-");
            var4.append(h.K - 1);
            throw new IllegalArgumentException(var4.toString());
         }
      }

      StringBuilder var3 = new StringBuilder("mRow must be in range 0-");
      var3.append(h.K - 1);
      throw new IllegalArgumentException(var3.toString());
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static d b(int var0, int var1) {
      synchronized (d.class){} // $VF: monitorenter 

      d var2;
      try {
         a(var0, var1);
         var2 = c[var0][var1];
      } finally {
         // $VF: monitorexit
      }

      return var2;
   }

   public final int describeContents() {
      return 0;
   }

   @Override
   public final boolean equals(Object var1) {
      if (!(var1 instanceof d)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         int var2 = var1.b;
         boolean var3;
         if (this.b == var2 && this.a == var1.a) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   @Override
   public final int hashCode() {
      return this.a * 31 + this.b;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("(Row = ");
      var1.append(this.a);
      var1.append(", Col = ");
      return a.a.m(var1, this.b, ")");
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var1.writeInt(this.b);
      var1.writeInt(this.a);
   }
}
