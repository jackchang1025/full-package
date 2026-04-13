package z0;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class c implements HostnameVerifier {
   public static final c a = new c();

   public static ArrayList a(X509Certificate var0) {
      List var2 = b(var0, 7);
      List var3 = b(var0, 2);
      int var1 = var2.size();
      ArrayList var4 = new ArrayList(var3.size() + var1);
      var4.addAll(var2);
      var4.addAll(var3);
      return var4;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static List b(X509Certificate var0, int var1) {
      ArrayList var2 = new ArrayList();

      try {
         var13 = var0.getSubjectAlternativeNames();
      } catch (CertificateParsingException var12) {
         boolean var10001 = false;
         return Collections.emptyList();
      }

      if (var13 == null) {
         try {
            return Collections.emptyList();
         } catch (CertificateParsingException var5) {
            boolean var16 = false;
         }
      } else {
         try {
            var14 = var13.iterator();
         } catch (CertificateParsingException var11) {
            boolean var17 = false;
            return Collections.emptyList();
         }

         while (true) {
            List var4;
            try {
               if (!var14.hasNext()) {
                  return var2;
               }

               var4 = (List)var14.next();
            } catch (CertificateParsingException var9) {
               boolean var18 = false;
               break;
            }

            if (var4 != null) {
               try {
                  if (var4.size() < 2) {
                     continue;
                  }
               } catch (CertificateParsingException var10) {
                  boolean var19 = false;
                  break;
               }

               Integer var3;
               try {
                  var3 = (Integer)var4.get(0);
               } catch (CertificateParsingException var8) {
                  boolean var20 = false;
                  break;
               }

               if (var3 != null) {
                  try {
                     if (var3 != var1) {
                        continue;
                     }

                     var15 = (String)var4.get(1);
                  } catch (CertificateParsingException var7) {
                     boolean var21 = false;
                     break;
                  }

                  if (var15 != null) {
                     try {
                        var2.add(var15);
                     } catch (CertificateParsingException var6) {
                        boolean var22 = false;
                        break;
                     }
                  }
               }
            }
         }
      }

      return Collections.emptyList();
   }

   public static boolean c(String var0, X509Certificate var1) {
      boolean var4 = q0.c.k.matcher(var0).matches();
      boolean var5 = true;
      if (var4) {
         List var10 = b(var1, 7);
         int var3 = var10.size();

         for (int var2 = 0; var2 < var3; var2++) {
            if (var0.equalsIgnoreCase((String)var10.get(var2))) {
               return var5;
            }
         }
      } else {
         String var6 = var0.toLowerCase(Locale.US);

         for (String var7 : b(var1, 2)) {
            label76: {
               if (var6 != null
                  && var6.length() != 0
                  && !var6.startsWith(".")
                  && !var6.endsWith("..")
                  && var7 != null
                  && var7.length() != 0
                  && !var7.startsWith(".")
                  && !var7.endsWith("..")) {
                  if (!var6.endsWith(".")) {
                     var0 = var6.concat(".");
                  } else {
                     var0 = var6;
                  }

                  String var11 = var7;
                  if (!var7.endsWith(".")) {
                     var11 = var7.concat(".");
                  }

                  String var12 = var11.toLowerCase(Locale.US);
                  if (!var12.contains("*")) {
                     var4 = var0.equals(var12);
                     break label76;
                  }

                  if (var12.startsWith("*.") && var12.indexOf(42, 1) == -1 && var0.length() >= var12.length() && !"*.".equals(var12)) {
                     String var13 = var12.substring(1);
                     if (var0.endsWith(var13)) {
                        int var14 = var0.length() - var13.length();
                        if (var14 <= 0 || var0.lastIndexOf(46, var14 - 1) == -1) {
                           var4 = true;
                           break label76;
                        }
                     }
                  }
               }

               var4 = false;
            }

            if (var4) {
               return var5;
            }
         }
      }

      return false;
   }

   @Override
   public final boolean verify(String var1, SSLSession var2) {
      try {
         return c(var1, (X509Certificate)var2.getPeerCertificates()[0]);
      } catch (SSLException var4) {
         return false;
      }
   }
}
