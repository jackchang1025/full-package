package j0;

import com.guard.wallet.http.h;
import i0.e;
import java.util.Locale;

public class c {
   public h a;
   public final e b;
   public final long c = -1L;

   public c(h var1) {
      this.a = var1;
      this.b = e.c(var1.i("Content-Disposition"), ";", true, null);
   }

   public c(String var1, long var2) {
      this.c = var2;
      this.a = new h(4);
      StringBuilder var4 = new StringBuilder(String.format(Locale.ENGLISH, "form-data; name=\"%s\"", var1));
      this.a.k("Content-Disposition", var4.toString());
      this.b = e.c(this.a.i("Content-Disposition"), ";", true, null);
   }
}
