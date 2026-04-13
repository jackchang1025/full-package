package b1;

import java.util.Arrays;

public final class o {
   public final byte a;
   public final byte[] b;

   public o(byte var1, byte[] var2) {
      byte[] var3 = new byte[8191];
      this.b = var3;
      this.a = var1;
      System.arraycopy(var2, 0, var3, 0, Math.min(var2.length, 8191));
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("PeerInfo{type=");
      var1.append(this.a);
      var1.append(", data=");
      var1.append(Arrays.toString(this.b));
      var1.append('}');
      return var1.toString();
   }
}
