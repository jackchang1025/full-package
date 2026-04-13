package v0;

import java.io.IOException;

public final class d0 extends IOException {
   public final b a;

   public d0(b var1) {
      StringBuilder var2 = new StringBuilder("stream was reset: ");
      var2.append(var1);
      super(var2.toString());
      this.a = var1;
   }
}
