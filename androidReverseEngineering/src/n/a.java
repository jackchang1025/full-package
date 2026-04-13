package n;

import java.nio.ByteBuffer;
import java.util.Comparator;

public final class a implements Comparator {
   public final int a;

   @Override
   public final int compare(Object var1, Object var2) {
      int var4 = this.a;
      byte var3 = 0;
      switch (var4) {
         case 0:
            String var7 = (String)var1;
            String var10 = (String)var2;
            return Integer.compare(var7.length() - var10.length(), 0);
         case 1:
            var1 = var1;
            var2 = var2;
            return var1.timestamp.compareTo(var2.timestamp);
         default:
            ByteBuffer var5 = (ByteBuffer)var1;
            ByteBuffer var8 = (ByteBuffer)var2;
            if (var5.capacity() != var8.capacity()) {
               if (var5.capacity() > var8.capacity()) {
                  var3 = 1;
               } else {
                  var3 = -1;
               }
            }

            return var3;
      }
   }
}
