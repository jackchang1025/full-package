package t0;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class c extends ThreadLocal {
   @Override
   public final Object initialValue() {
      SimpleDateFormat var1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      var1.setLenient(false);
      var1.setTimeZone(q0.c.h);
      return var1;
   }
}
