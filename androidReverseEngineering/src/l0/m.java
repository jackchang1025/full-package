package l0;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import p0.f0;
import p0.t;
import p0.u;

public final class m {
   public String a;
   public Object b;
   public Object c;
   public Object d;
   public final Object e;

   public m() {
      this.e = Collections.emptyMap();
      this.a = "GET";
      this.c = new p0.f();
   }

   public m(String var1, String var2, Matcher var3, o var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = null;
   }

   public m(f0 var1) {
      this.e = Collections.emptyMap();
      this.b = var1.a;
      this.a = var1.b;
      this.d = var1.d;
      Map var2 = var1.e;
      if (var2.isEmpty()) {
         var2 = Collections.emptyMap();
      } else {
         var2 = new LinkedHashMap(var2);
      }

      this.e = var2;
      this.c = var1.c.e();
   }

   public final f0 a() {
      if ((u)this.b != null) {
         return new f0(this);
      } else {
         throw new IllegalStateException("url == null");
      }
   }

   public final void b(String var1, a1.q var2) {
      if (var1.length() != 0) {
         if (var2 != null && !a1.q.I(var1)) {
            throw new IllegalArgumentException(a.a.l("method ", var1, " must not have a request body."));
         } else {
            if (var2 == null) {
               boolean var3;
               if (!var1.equals("POST") && !var1.equals("PUT") && !var1.equals("PATCH") && !var1.equals("PROPPATCH") && !var1.equals("REPORT")) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               if (var3) {
                  throw new IllegalArgumentException(a.a.l("method ", var1, " must have a request body."));
               }
            }

            this.a = var1;
            this.d = var2;
         }
      } else {
         throw new IllegalArgumentException("method.length() == 0");
      }
   }

   public final void c(String var1) {
      ((p0.f)this.c).b(var1);
   }

   public final void d(String var1) {
      if (var1 == null) {
         throw new NullPointerException("url == null");
      } else {
         String var5;
         label17: {
            byte var2;
            StringBuilder var3;
            if (var1.regionMatches(true, 0, "ws:", 0, 3)) {
               var3 = new StringBuilder("http:");
               var2 = 3;
            } else {
               var5 = var1;
               if (!var1.regionMatches(true, 0, "wss:", 0, 4)) {
                  break label17;
               }

               var3 = new StringBuilder("https:");
               var2 = 4;
            }

            var3.append(var1.substring(var2));
            var5 = var3.toString();
         }

         t var4 = new t();
         var4.b(null, var5);
         this.b = var4.a();
      }
   }
}
