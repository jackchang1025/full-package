package d;

import android.os.Parcel;
import android.util.SparseIntArray;

public final class c extends b {
   public final SparseIntArray a = new SparseIntArray();
   public final Parcel b;
   public final int c;
   public int d = -1;
   public int e;

   public c(Parcel var1, int var2, int var3, String var4) {
      this.b = var1;
      this.c = var3;
      this.e = var2;
   }

   @Override
   public final boolean b(int var1) {
      while (true) {
         int var2 = this.e;
         int var3 = this.c;
         Parcel var4 = this.b;
         if (var2 < var3) {
            var4.setDataPosition(var2);
            var2 = var4.readInt();
            var3 = var4.readInt();
            this.e += var2;
            if (var3 != var1) {
               continue;
            }

            var1 = var4.dataPosition();
         } else {
            var1 = -1;
         }

         if (var1 == -1) {
            return false;
         }

         var4.setDataPosition(var1);
         return true;
      }
   }

   @Override
   public final void d(int var1) {
      int var2 = this.d;
      SparseIntArray var5 = this.a;
      Parcel var4 = this.b;
      if (var2 >= 0) {
         int var3 = var5.get(var2);
         var2 = var4.dataPosition();
         var4.setDataPosition(var3);
         var4.writeInt(var2 - var3);
         var4.setDataPosition(var2);
      }

      this.d = var1;
      var5.put(var1, var4.dataPosition());
      var4.writeInt(0);
      var4.writeInt(var1);
   }
}
