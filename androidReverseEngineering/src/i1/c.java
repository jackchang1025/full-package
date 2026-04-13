package i1;

import java.nio.charset.CharacterCodingException;

public class c extends Exception {
   public final int a;

   public c(int var1) {
      this.a = var1;
   }

   public c(int var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public c(CharacterCodingException var1) {
      super(var1);
      this.a = 1007;
   }
}
