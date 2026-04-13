package t0;

import a1.h;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import p0.j0;
import p0.m;
import p0.n;
import p0.s;
import p0.u;
import w0.i;

public abstract class e {
   static {
      h.d("\"\\");
      h.d("\t ,=");
   }

   public static long a(j0 var0) {
      String var4 = var0.f.c("Content-Length");
      if (var4 != null) {
         try {
            return Long.parseLong(var4);
         } catch (NumberFormatException var3) {
         }
      }

      return -1L;
   }

   public static boolean b(j0 var0) {
      if (var0.a.b.equals("HEAD")) {
         return false;
      } else {
         int var1 = var0.c;
         return (var1 < 100 || var1 >= 200) && var1 != 204 && var1 != 304
            ? true
            : a(var0) != -1L || "chunked".equalsIgnoreCase(var0.x("Transfer-Encoding", null));
      }
   }

   public static int c(int var0, String var1) {
      long var2;
      try {
         var2 = Long.parseLong(var1);
      } catch (NumberFormatException var4) {
         return var0;
      }

      if (var2 > 2147483647L) {
         return Integer.MAX_VALUE;
      } else {
         return var2 < 0L ? 0 : (int)var2;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void d(n var0, u var1, s var2) {
      if (var0 != n.b) {
         Pattern var29 = m.j;
         int var5 = var2.a.length / 2;
         int var4 = 0;
         int var3 = 0;
         ArrayList var212 = null;

         while (var3 < var5) {
            ArrayList var30 = var212;
            if ("Set-Cookie".equalsIgnoreCase(var2.d(var3))) {
               var30 = var212;
               if (var212 == null) {
                  var30 = new ArrayList(2);
               }

               var30.add(var2.f(var3));
            }

            var3++;
            var212 = var30;
         }

         List var169;
         if (var212 != null) {
            var169 = Collections.unmodifiableList(var212);
         } else {
            var169 = Collections.emptyList();
         }

         int var6 = var169.size();
         var5 = 0;
         ArrayList var213 = null;
         int var184 = var4;
         var4 = var6;

         while (var5 < var4) {
            Throwable var10000;
            label2235: {
               List var214;
               m var215;
               label2236: {
                  y0.a var38;
                  label2218: {
                     label2217: {
                        label2216: {
                           String var34 = (String)var169.get(var5);
                           long var20 = System.currentTimeMillis();
                           int var7 = var34.length();
                           int var8 = q0.c.g(var34, var184, var7, ';');
                           int var9 = q0.c.g(var34, var184, var8, '=');
                           if (var9 != var8) {
                              String var35 = q0.c.u(var34, var184, var9);
                              if (!var35.isEmpty()) {
                                 int var10 = var35.length();
                                 var184 = 0;

                                 while (true) {
                                    if (var184 >= var10) {
                                       var6 = -1;
                                       break;
                                    }

                                    char var11 = var35.charAt(var184);
                                    var6 = var184;
                                    if (var11 <= 31) {
                                       break;
                                    }

                                    if (var11 >= 127) {
                                       var6 = var184;
                                       break;
                                    }

                                    var184++;
                                 }

                                 var214 = var169;
                                 if (var6 == -1) {
                                    String var36 = q0.c.u(var34, var9 + 1, var8);
                                    var9 = var36.length();
                                    var184 = 0;

                                    while (true) {
                                       if (var184 >= var9) {
                                          var6 = -1;
                                          break;
                                       }

                                       char var207 = var36.charAt(var184);
                                       var6 = var184;
                                       if (var207 <= 31) {
                                          break;
                                       }

                                       if (var207 >= 127) {
                                          var6 = var184;
                                          break;
                                       }

                                       var184++;
                                    }

                                    if (var6 == -1) {
                                       var184 = var8 + 1;
                                       long var14 = 253402300799999L;
                                       String var32 = null;
                                       long var12 = -1L;
                                       boolean var24 = false;
                                       boolean var25 = false;
                                       boolean var23 = true;
                                       boolean var22 = false;
                                       String var31 = null;

                                       while (true) {
                                          long var16 = Long.MAX_VALUE;
                                          if (var184 >= var7) {
                                             if (var12 == Long.MIN_VALUE) {
                                                var12 = Long.MIN_VALUE;
                                             } else if (var12 != -1L) {
                                                var14 = var16;
                                                if (var12 <= 9223372036854775L) {
                                                   var14 = var12 * 1000L;
                                                }

                                                var12 = var20 + var14;
                                                if (var12 < var20 || var12 > 253402300799999L) {
                                                   var12 = 253402300799999L;
                                                }
                                             } else {
                                                var12 = var14;
                                             }

                                             String var174 = var1.d;
                                             String var221;
                                             if (var32 == null) {
                                                var221 = var174;
                                             } else {
                                                boolean var188;
                                                if (!var174.equals(var32)
                                                   && (
                                                      !var174.endsWith(var32)
                                                         || var174.charAt(var174.length() - var32.length() - 1) != '.'
                                                         || q0.c.k.matcher(var174).matches()
                                                   )) {
                                                   var188 = false;
                                                } else {
                                                   var188 = true;
                                                }

                                                var221 = var32;
                                                if (!var188) {
                                                   break;
                                                }
                                             }

                                             if (var174.length() != var221.length()) {
                                                String[] var224;
                                                var38 = y0.a.h;
                                                var38.getClass();
                                                var224 = IDN.toUnicode(var221).split("\\.");
                                                label2112:
                                                if (!var38.a.get() && var38.a.compareAndSet(false, true)) {
                                                   var189 = false;

                                                   label2043: {
                                                      while (true) {
                                                         label2251: {
                                                            try {
                                                               try {
                                                                  var38.b();
                                                                  break;
                                                               } catch (InterruptedIOException var155) {
                                                                  break label2251;
                                                               } catch (IOException var156) {
                                                                  var175 = var156;
                                                               }
                                                            } catch (Throwable var157) {
                                                               var10000 = var157;
                                                               boolean var233 = false;
                                                               break label2235;
                                                            }

                                                            try {
                                                               i.a.m(5, "Failed to read public suffix list", var175);
                                                            } catch (Throwable var152) {
                                                               var10000 = var152;
                                                               boolean var234 = false;
                                                               break label2235;
                                                            }

                                                            if (!var189) {
                                                               break label2112;
                                                            }
                                                            break label2043;
                                                         }

                                                         try {
                                                            Thread.interrupted();
                                                         } catch (Throwable var153) {
                                                            var10000 = var153;
                                                            boolean var235 = false;
                                                            break label2235;
                                                         }

                                                         var189 = true;
                                                      }

                                                      if (!var189) {
                                                         break label2112;
                                                      }
                                                   }

                                                   Thread.currentThread().interrupt();
                                                } else {
                                                   try {
                                                      var38.b.await();
                                                   } catch (InterruptedException var148) {
                                                      Thread.currentThread().interrupt();
                                                   }
                                                }

                                                synchronized (var38){} // $VF: monitorenter 

                                                try {
                                                   if (var38.c == null) {
                                                      break label2217;
                                                   }

                                                   // $VF: monitorexit
                                                } catch (Throwable var154) {
                                                   var10000 = var154;
                                                   boolean var236 = false;
                                                   break label2218;
                                                }

                                                var6 = var224.length;
                                                byte[][] var39 = new byte[var6][];

                                                for (int var190 = 0; var190 < var224.length; var190++) {
                                                   var39[var190] = var224[var190].getBytes(StandardCharsets.UTF_8);
                                                }

                                                var184 = 0;

                                                while (true) {
                                                   if (var184 >= var6) {
                                                      var176 = null;
                                                      break;
                                                   }

                                                   var176 = y0.a.a(var38.c, var39, var184);
                                                   if (var176 != null) {
                                                      break;
                                                   }

                                                   var184++;
                                                }

                                                label2094: {
                                                   if (var6 > 1) {
                                                      byte[][] var222 = (byte[][])var39.clone();

                                                      for (int var192 = 0; var192 < var222.length - 1; var192++) {
                                                         var222[var192] = y0.a.e;
                                                         var32 = y0.a.a(var38.c, var222, var192);
                                                         if (var32 != null) {
                                                            break label2094;
                                                         }
                                                      }
                                                   }

                                                   var32 = null;
                                                }

                                                label2083: {
                                                   if (var32 != null) {
                                                      for (int var193 = 0; var193 < var6 - 1; var193++) {
                                                         var34 = y0.a.a(var38.d, var39, var193);
                                                         if (var34 != null) {
                                                            break label2083;
                                                         }
                                                      }
                                                   }

                                                   var34 = null;
                                                }

                                                String[] var177;
                                                if (var34 != null) {
                                                   var177 = "!".concat(var34).split("\\.");
                                                } else if (var176 == null && var32 == null) {
                                                   var177 = y0.a.g;
                                                } else {
                                                   if (var176 != null) {
                                                      var177 = var176.split("\\.");
                                                   } else {
                                                      var177 = y0.a.f;
                                                   }

                                                   String[] var217;
                                                   if (var32 != null) {
                                                      var217 = var32.split("\\.");
                                                   } else {
                                                      var217 = y0.a.f;
                                                   }

                                                   if (var177.length <= var217.length) {
                                                      var177 = var217;
                                                   }
                                                }

                                                String var179;
                                                if (var224.length == var177.length && var177[0].charAt(0) != '!') {
                                                   var179 = null;
                                                } else {
                                                   char var204 = var177[0].charAt(0);
                                                   var6 = var224.length;
                                                   var184 = var177.length;
                                                   if (var204 != '!') {
                                                      var184++;
                                                   }

                                                   var184 = var6 - var184;
                                                   StringBuilder var178 = new StringBuilder();

                                                   for (String[] var218 = var221.split("\\."); var184 < var218.length; var184++) {
                                                      var178.append(var218[var184]);
                                                      var178.append('.');
                                                   }

                                                   var178.deleteCharAt(var178.length() - 1);
                                                   var179 = var178.toString();
                                                }

                                                if (var179 == null) {
                                                   break;
                                                }
                                             }

                                             String var181;
                                             if (var31 != null && var31.startsWith("/")) {
                                                var181 = var31;
                                             } else {
                                                String var180 = var1.e();
                                                var184 = var180.lastIndexOf(47);
                                                if (var184 != 0) {
                                                   var181 = var180.substring(0, var184);
                                                } else {
                                                   var181 = "/";
                                                }
                                             }

                                             var184 = 0;
                                             var215 = new m(var35, var36, var12, var221, var181, var24, var25, var23, var22);
                                             break label2236;
                                          }

                                          var6 = q0.c.g(var34, var184, var7, ';');
                                          var8 = q0.c.g(var34, var184, var6, '=');
                                          String var37 = q0.c.u(var34, var184, var8);
                                          String var170;
                                          if (var8 < var6) {
                                             var170 = q0.c.u(var34, var8 + 1, var6);
                                          } else {
                                             var170 = "";
                                          }

                                          boolean var26;
                                          boolean var27;
                                          boolean var28;
                                          long var211;
                                          String var219;
                                          label2186: {
                                             label2185: {
                                                if (var37.equalsIgnoreCase("expires")) {
                                                   try {
                                                      var16 = m.b(var170, var170.length());
                                                      break label2185;
                                                   } catch (IllegalArgumentException var165) {
                                                      boolean var10001 = false;
                                                   }
                                                } else {
                                                   label2181:
                                                   if (var37.equalsIgnoreCase("max-age")) {
                                                      label2179: {
                                                         try {
                                                            var211 = Long.parseLong(var170);
                                                         } catch (NumberFormatException var164) {
                                                            NumberFormatException var33 = var164;

                                                            label2175: {
                                                               try {
                                                                  if (var170.matches("-?\\d+")) {
                                                                     if (var170.startsWith("-")) {
                                                                        break label2179;
                                                                     }
                                                                     break label2175;
                                                                  }
                                                               } catch (NumberFormatException var163) {
                                                                  boolean var226 = false;
                                                                  break label2181;
                                                               }

                                                               try {
                                                                  throw var33;
                                                               } catch (NumberFormatException var151) {
                                                                  boolean var227 = false;
                                                                  break label2181;
                                                               }
                                                            }

                                                            var12 = Long.MAX_VALUE;
                                                            var16 = var14;
                                                            break label2185;
                                                         }

                                                         var12 = var211;
                                                         var16 = var14;
                                                         if (var211 > 0L) {
                                                            break label2185;
                                                         }
                                                      }

                                                      var12 = Long.MIN_VALUE;
                                                      var16 = var14;
                                                      break label2185;
                                                   } else {
                                                      label2244: {
                                                         if (!var37.equalsIgnoreCase("domain")) {
                                                            if (var37.equalsIgnoreCase("path")) {
                                                               var219 = var170;
                                                               var171 = var32;
                                                               var211 = var12;
                                                               var16 = var14;
                                                               var26 = var24;
                                                               var27 = var23;
                                                               var28 = var22;
                                                            } else if (var37.equalsIgnoreCase("secure")) {
                                                               var26 = true;
                                                               var171 = var32;
                                                               var211 = var12;
                                                               var16 = var14;
                                                               var27 = var23;
                                                               var28 = var22;
                                                               var219 = var31;
                                                            } else {
                                                               var171 = var32;
                                                               var211 = var12;
                                                               var16 = var14;
                                                               var26 = var24;
                                                               var27 = var23;
                                                               var28 = var22;
                                                               var219 = var31;
                                                               if (var37.equalsIgnoreCase("httponly")) {
                                                                  var25 = true;
                                                                  var219 = var31;
                                                                  var28 = var22;
                                                                  var27 = var23;
                                                                  var26 = var24;
                                                                  var16 = var14;
                                                                  var211 = var12;
                                                                  var171 = var32;
                                                               }
                                                            }
                                                            break label2186;
                                                         }

                                                         label2245: {
                                                            try {
                                                               if (var170.endsWith(".")) {
                                                                  break label2245;
                                                               }
                                                            } catch (IllegalArgumentException var162) {
                                                               boolean var228 = false;
                                                               break label2244;
                                                            }

                                                            var219 = var170;

                                                            try {
                                                               if (var170.startsWith(".")) {
                                                                  var219 = var170.substring(1);
                                                               }
                                                            } catch (IllegalArgumentException var161) {
                                                               boolean var229 = false;
                                                               break label2244;
                                                            }

                                                            try {
                                                               var171 = q0.c.a(var219);
                                                            } catch (IllegalArgumentException var160) {
                                                               boolean var230 = false;
                                                               break label2244;
                                                            }

                                                            if (var171 != null) {
                                                               var27 = false;
                                                               var211 = var12;
                                                               var16 = var14;
                                                               var26 = var24;
                                                               var28 = var22;
                                                               var219 = var31;
                                                               break label2186;
                                                            }

                                                            try {
                                                               IllegalArgumentException var172 = new IllegalArgumentException();
                                                               throw var172;
                                                            } catch (IllegalArgumentException var159) {
                                                               boolean var231 = false;
                                                               break label2244;
                                                            }
                                                         }

                                                         try {
                                                            IllegalArgumentException var173 = new IllegalArgumentException();
                                                            throw var173;
                                                         } catch (IllegalArgumentException var158) {
                                                            boolean var232 = false;
                                                         }
                                                      }
                                                   }
                                                }

                                                var171 = var32;
                                                var211 = var12;
                                                var16 = var14;
                                                var26 = var24;
                                                var27 = var23;
                                                var28 = var22;
                                                var219 = var31;
                                                break label2186;
                                             }

                                             var28 = true;
                                             var171 = var32;
                                             var211 = var12;
                                             var26 = var24;
                                             var27 = var23;
                                             var219 = var31;
                                          }

                                          var184 = var6 + 1;
                                          var32 = var171;
                                          var12 = var211;
                                          var14 = var16;
                                          var24 = var26;
                                          var23 = var27;
                                          var22 = var28;
                                          var31 = var219;
                                       }
                                    }
                                 }

                                 var184 = 0;
                                 break label2216;
                              }
                           }

                           var214 = var169;
                        }

                        var215 = null;
                        break label2236;
                     }

                     label2015:
                     try {
                        IllegalStateException var168 = new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.");
                        throw var168;
                     } catch (Throwable var150) {
                        var10000 = var150;
                        boolean var237 = false;
                        break label2015;
                     }
                  }

                  while (true) {
                     Throwable var167 = var10000;

                     try {
                        // $VF: monitorexit
                        throw var167;
                     } catch (Throwable var149) {
                        var10000 = var149;
                        boolean var238 = false;
                        continue;
                     }
                  }
               }

               if (var215 != null) {
                  ArrayList var182 = var213;
                  if (var213 == null) {
                     var182 = new ArrayList();
                  }

                  var182.add(var215);
                  var213 = var182;
               }

               var5++;
               var169 = var214;
               continue;
            }

            Throwable var166 = var10000;
            if (var189) {
               Thread.currentThread().interrupt();
            }

            throw var166;
         }

         List var183;
         if (var213 != null) {
            var183 = Collections.unmodifiableList(var213);
         } else {
            var183 = Collections.emptyList();
         }

         if (!var183.isEmpty()) {
            var0.e(var1, var183);
         }
      }
   }

   public static int e(String var0, int var1, String var2) {
      while (var1 < var0.length() && var2.indexOf(var0.charAt(var1)) == -1) {
         var1++;
      }

      return var1;
   }
}
