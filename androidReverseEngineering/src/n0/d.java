package n0;

import java.nio.charset.Charset;

public abstract class d {
   public static final Charset a = Charset.forName("UTF-8");

   static {
      Charset.forName("US-ASCII");
      Charset.forName("ISO-8859-1");
   }
}
