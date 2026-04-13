package a1;

import java.nio.charset.Charset;

public abstract class w {
   public static final Charset a = Charset.forName("UTF-8");

   public static void a(long var0, long var2, long var4) {
      if ((var2 | var4) < 0L || var2 > var0 || var0 - var2 < var4) {
         throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", var0, var2, var4));
      }
   }
}
