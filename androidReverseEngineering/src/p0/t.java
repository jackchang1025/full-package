package p0;

import java.io.Serializable;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class t {
   public final int a;
   public List b;
   public int c;
   public List d;
   public Object e;
   public Object f;
   public Object g;
   public Object h;
   public Serializable i;

   public t() {
      this.a = 0;
      super();
      this.f = "";
      this.g = "";
      this.c = -1;
      ArrayList var1 = new ArrayList();
      this.b = var1;
      var1.add("");
   }

   public t(a var1, com.guard.wallet.http.h var2, e0 var3, q var4) {
      this.a = 1;
      super();
      this.b = Collections.emptyList();
      this.d = Collections.emptyList();
      this.i = new ArrayList();
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
      Proxy var7 = var1.h;
      List var5;
      if (var7 != null) {
         var5 = Collections.singletonList(var7);
      } else {
         List var6 = var1.g.select(var1.a.n());
         if (var6 != null && !var6.isEmpty()) {
            var5 = q0.c.k(var6);
         } else {
            var5 = q0.c.l(Proxy.NO_PROXY);
         }
      }

      this.b = var5;
      this.c = 0;
   }

   public final u a() {
      if ((String)this.e != null) {
         if ((String)this.h != null) {
            return new u(this);
         } else {
            throw new IllegalStateException("host == null");
         }
      } else {
         throw new IllegalStateException("scheme == null");
      }
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   public final void b(u var1, String var2) {
      int var3;
      int var8;
      int var32;
      label311: {
         var3 = q0.c.r(var2, 0, var2.length());
         var8 = q0.c.s(var2, var3, var2.length());
         if (var8 - var3 >= 2) {
            char var4 = var2.charAt(var3);
            if (var4 >= 'a' && var4 <= 'z' || var4 >= 'A' && var4 <= 'Z') {
               var32 = var3;

               while (++var32 < var8) {
                  char var5 = var2.charAt(var32);
                  if ((var5 < 'a' || var5 > 'z') && (var5 < 'A' || var5 > 'Z') && (var5 < '0' || var5 > '9') && var5 != '+' && var5 != '-' && var5 != '.') {
                     if (var5 == ':') {
                        break label311;
                     }
                     break;
                  }
               }
            }
         }

         var32 = -1;
      }

      if (var32 != -1) {
         if (var2.regionMatches(true, var3, "https:", 0, 6)) {
            this.e = "https";
            var3 += 6;
         } else {
            if (!var2.regionMatches(true, var3, "http:", 0, 5)) {
               StringBuilder var16 = new StringBuilder("Expected URL scheme 'http' or 'https' but was '");
               var16.append(var2.substring(0, var32));
               var16.append("'");
               throw new IllegalArgumentException(var16.toString());
            }

            this.e = "http";
            var3 += 5;
         }
      } else {
         if (var1 == null) {
            throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
         }

         this.e = var1.a;
      }

      int var36 = var3;

      for (var32 = 0; var36 < var8; var36++) {
         char var6 = var2.charAt(var36);
         if (var6 != '\\' && var6 != '/') {
            break;
         }

         var32++;
      }

      label330: {
         if (var32 < 2 && var1 != null) {
            String var9 = (String)this.e;
            if (var1.a.equals(var9)) {
               this.f = var1.h();
               this.g = var1.d();
               this.h = var1.d;
               this.c = var1.e;
               this.b.clear();
               this.b.addAll(var1.f());
               if (var3 == var8 || var2.charAt(var3) == '#') {
                  String var22 = var1.g();
                  ArrayList var23;
                  if (var22 != null) {
                     var23 = u.l(u.b(var22, " \"'<>#", true, false, true, true));
                  } else {
                     var23 = null;
                  }

                  this.d = var23;
               }
               break label330;
            }
         }

         var32 = var3 + var32;
         int var26 = 0;
         int var37 = 0;

         while (true) {
            int var45 = q0.c.h(var2, var32, var8, "@/\\?#");
            int var7;
            if (var45 != var8) {
               var7 = var2.charAt(var45);
            } else {
               var7 = -1;
            }

            if (var7 == -1 || var7 == 35 || var7 == 47 || var7 == 92 || var7 == 63) {
               var26 = var32;

               while (true) {
                  if (var26 >= var45) {
                     var26 = var45;
                     break;
                  }

                  var37 = var2.charAt(var26);
                  if (var37 == ':') {
                     break;
                  }

                  if (var37 != '[') {
                     var37 = var26;
                  } else {
                     var37 = var26;

                     while (true) {
                        var26 = var37 + 1;
                        var37 = var26;
                        if (var26 >= var45) {
                           break;
                        }

                        var37 = var26;
                        if (var2.charAt(var26) == ']') {
                           var37 = var26;
                           break;
                        }
                     }
                  }

                  var26 = var37 + 1;
               }

               var7 = var26 + 1;
               if (var7 < var45) {
                  this.h = q0.c.a(u.i(var2, var32, var26, false));

                  label224: {
                     label223: {
                        try {
                           var37 = Integer.parseInt(u.a(var2, var7, var45, "", false, false, false, true));
                        } catch (NumberFormatException var15) {
                           break label223;
                        }

                        if (var37 > 0 && var37 <= 65535) {
                           break label224;
                        }
                     }

                     var37 = -1;
                  }

                  this.c = var37;
                  if (var37 == -1) {
                     StringBuilder var21 = new StringBuilder("Invalid URL port: \"");
                     var21.append(var2.substring(var7, var45));
                     var21.append('"');
                     throw new IllegalArgumentException(var21.toString());
                  }
               } else {
                  this.h = q0.c.a(u.i(var2, var32, var26, false));
                  this.c = u.c((String)this.e);
               }

               if ((String)this.h == null) {
                  StringBuilder var20 = new StringBuilder("Invalid URL host: \"");
                  var20.append(var2.substring(var32, var26));
                  var20.append('"');
                  throw new IllegalArgumentException(var20.toString());
               }

               var3 = var45;
               break;
            }

            if (var7 == 64) {
               if (!var26) {
                  var7 = q0.c.g(var2, var32, var45, ':');
                  String var50 = u.a(var2, var32, var7, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                  String var17 = var50;
                  if (var37) {
                     StringBuilder var18 = new StringBuilder();
                     var18.append((String)this.f);
                     var18.append("%40");
                     var18.append(var50);
                     var17 = var18.toString();
                  }

                  this.f = var17;
                  if (var7 != var45) {
                     this.g = u.a(var2, var7 + 1, var45, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                     var26 = 1;
                  }

                  var37 = 1;
               } else {
                  StringBuilder var19 = new StringBuilder();
                  var19.append((String)this.g);
                  var19.append("%40");
                  var19.append(u.a(var2, var32, var45, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true));
                  this.g = var19.toString();
               }

               var32 = var45 + 1;
            }
         }
      }

      var32 = q0.c.h(var2, var3, var8, "?#");
      String var24;
      t var51;
      if (var3 == var32) {
         var51 = this;
         var24 = var2;
      } else {
         int var42 = var2.charAt(var3);
         String var10;
         t var11;
         String var12;
         int var46;
         if (var42 != 47 && var42 != 92) {
            List var25 = this.b;
            var25.set(var25.size() - 1, "");
            var51 = this;
            var11 = this;
            var42 = var32;
            var10 = "";
            var24 = var2;
            var32 = var32;
            var12 = var2;
            var46 = var42;
         } else {
            this.b.clear();
            this.b.add("");
            var51 = this;
            var11 = this;
            var10 = "";
            var24 = var2;
            var12 = var2;
            var3++;
            var46 = var32;
            var32 = var32;
         }

         while (var3 < var46) {
            var42 = q0.c.h(var12, var3, var46, "/\\");
            boolean var49;
            if (var42 < var46) {
               var49 = true;
            } else {
               var49 = false;
            }

            String var13 = u.a(var12, var3, var42, " \"<>^`{}|/\\?#", true, false, false, true);
            boolean var29;
            if (!var13.equals(".")) {
               if (var13.equalsIgnoreCase("%2e")) {
                  var29 = true;
               } else {
                  var29 = false;
               }
            } else {
               var29 = true;
            }

            if (!var29) {
               if (!var13.equals("..") && !var13.equalsIgnoreCase("%2e.") && !var13.equalsIgnoreCase(".%2e") && !var13.equalsIgnoreCase("%2e%2e")) {
                  var29 = false;
               } else {
                  var29 = true;
               }

               if (var29) {
                  List var52 = var11.b;
                  if (((String)var52.remove(var52.size() - 1)).isEmpty() && !var11.b.isEmpty()) {
                     List var53 = var11.b;
                     var53.set(var53.size() - 1, var10);
                  } else {
                     var11.b.add(var10);
                  }
               } else {
                  List var14 = var11.b;
                  if (((String)var14.get(var14.size() - 1)).isEmpty()) {
                     var14 = var11.b;
                     var14.set(var14.size() - 1, var13);
                  } else {
                     var11.b.add(var13);
                  }

                  if (var49) {
                     var11.b.add(var10);
                  }
               }
            }

            var3 = var42;
            if (var49) {
               var3 = var42 + 1;
               var46 = var46;
               var32 = var32;
            }
         }
      }

      if (var32 < var8 && var24.charAt(var32) == '?') {
         var3 = q0.c.g(var24, var32, var8, '#');
         var51.d = u.l(u.a(var2, var32 + 1, var3, " \"'<>#", true, false, true, true));
      } else {
         var3 = var32;
      }

      if (var3 < var8 && var24.charAt(var3) == '#') {
         var51.i = u.a(var2, var3 + 1, var8, "", true, false, false, false);
      }
   }

   @Override
   public final String toString() {
      switch (this.a) {
         case 0:
            StringBuilder var5 = new StringBuilder();
            String var4 = (String)this.e;
            if (var4 != null) {
               var5.append(var4);
               var4 = "://";
            } else {
               var4 = "//";
            }

            var5.append(var4);
            if (!((String)this.f).isEmpty() || !((String)this.g).isEmpty()) {
               var5.append((String)this.f);
               if (!((String)this.g).isEmpty()) {
                  var5.append(':');
                  var5.append((String)this.g);
               }

               var5.append('@');
            }

            var4 = (String)this.h;
            if (var4 != null) {
               if (var4.indexOf(58) != -1) {
                  var5.append('[');
                  var5.append((String)this.h);
                  var5.append(']');
               } else {
                  var5.append((String)this.h);
               }
            }

            int var1 = this.c;
            if (var1 != -1 || (String)this.e != null) {
               if (var1 == -1) {
                  var1 = u.c((String)this.e);
               }

               var4 = (String)this.e;
               if (var4 == null || var1 != u.c(var4)) {
                  var5.append(':');
                  var5.append(var1);
               }
            }

            List var14 = this.b;
            int var3 = var14.size();
            byte var2 = 0;

            for (int var8 = 0; var8 < var3; var8++) {
               var5.append('/');
               var5.append((String)var14.get(var8));
            }

            if (this.d != null) {
               var5.append('?');
               List var7 = this.d;
               var3 = var7.size();

               for (byte var9 = var2; var9 < var3; var9 += 2) {
                  String var6 = (String)var7.get(var9);
                  var4 = (String)var7.get(var9 + 1);
                  if (var9 > 0) {
                     var5.append('&');
                  }

                  var5.append(var6);
                  if (var4 != null) {
                     var5.append('=');
                     var5.append(var4);
                  }
               }
            }

            if ((String)this.i != null) {
               var5.append('#');
               var5.append((String)this.i);
            }

            return var5.toString();
         default:
            return super.toString();
      }
   }
}
