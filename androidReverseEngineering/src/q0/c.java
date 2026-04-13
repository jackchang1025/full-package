package q0;

import a1.e;
import a1.m;
import a1.t;
import java.io.Closeable;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import p0.f;
import p0.h;
import p0.k0;
import p0.q;
import p0.s;
import p0.u;

public abstract class c {
   public static final byte[] a = new byte[0];
   public static final String[] b = new String[0];
   public static final s c;
   public static final k0 d;
   public static final m e;
   public static final Charset f;
   public static final Charset g;
   public static final TimeZone h;
   public static final h i;
   public static final Method j;
   public static final Pattern k;

   static {
      String[] var5 = (String[])new String[0].clone();

      for (int var0 = 0; var0 < var5.length; var0++) {
         String var6 = var5[var0];
         if (var6 == null) {
            throw new IllegalArgumentException("Headers cannot be null");
         }

         var5[var0] = var6.trim();
      }

      for (byte var11 = 0; var11 < var5.length; var11 += 2) {
         String var7 = var5[var11];
         String var22 = var5[var11 + 1];
         s.a(var7);
         s.b(var22, var7);
      }

      c = new s(var5);
      byte[] var18 = a;
      e var23 = new e();
      var23.I(var18, 0, 0);
      long var3 = (long)0;
      d = new k0(var3, var23);
      if ((var3 | var3) >= 0L && var3 <= var3 && var3 - var3 >= var3) {
         a1.h[] var19 = new a1.h[]{a1.h.b("efbbbf"), a1.h.b("feff"), a1.h.b("fffe"), a1.h.b("0000ffff"), a1.h.b("ffff0000")};
         int var12 = m.c;
         ArrayList var26 = new ArrayList<>(Arrays.asList(var19));
         Collections.sort(var26);
         ArrayList var8 = new ArrayList();

         for (int var13 = 0; var13 < var26.size(); var13++) {
            var8.add(-1);
         }

         for (int var14 = 0; var14 < var26.size(); var14++) {
            var8.set(Collections.binarySearch(var26, var19[var14]), var14);
         }

         if (((a1.h)var26.get(0)).j() == 0) {
            throw new IllegalArgumentException("the empty byte string is not a supported option");
         } else {
            var12 = 0;

            while (var12 < var26.size()) {
               a1.h var9 = (a1.h)var26.get(var12);
               int var1 = var12 + 1;
               int var2 = var1;

               while (var2 < var26.size()) {
                  a1.h var24 = (a1.h)var26.get(var2);
                  var24.getClass();
                  if (!var24.i(var9, var9.j())) {
                     break;
                  }

                  if (var24.j() == var9.j()) {
                     StringBuilder var20 = new StringBuilder("duplicate option: ");
                     var20.append(var24);
                     throw new IllegalArgumentException(var20.toString());
                  }

                  if ((Integer)var8.get(var2) > (Integer)var8.get(var12)) {
                     var26.remove(var2);
                     var8.remove(var2);
                  } else {
                     var2++;
                  }
               }

               var12 = var1;
            }

            var23 = new e();
            m.a(0L, var23, 0, var26, 0, var26.size(), var8);
            int var17 = (int)(var23.b / 4L);
            int[] var27 = new int[var17];

            for (int var16 = 0; var16 < var17; var16++) {
               var27[var16] = var23.readInt();
            }

            if (var23.n()) {
               e = new m((a1.h[])var19.clone(), var27);
               f = Charset.forName("UTF-32BE");
               g = Charset.forName("UTF-32LE");
               h = TimeZone.getTimeZone("GMT");
               i = new h(1);

               try {
                  var21 = Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class);
               } catch (Exception var10) {
                  var21 = null;
               }

               j = var21;
               k = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
            } else {
               throw new AssertionError();
            }
         }
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String a(String var0) {
      boolean var9 = var0.contains(":");
      int var2 = -1;
      int var3 = 0;
      byte var7 = 0;
      if (var9) {
         InetAddress var10;
         if (var0.startsWith("[") && var0.endsWith("]")) {
            var10 = f(var0, 1, var0.length() - 1);
         } else {
            var10 = f(var0, 0, var0.length());
         }

         if (var10 == null) {
            return null;
         } else {
            byte[] var11 = var10.getAddress();
            if (var11.length != 16) {
               if (var11.length == 4) {
                  return var10.getHostAddress();
               } else {
                  throw new AssertionError(a.a.l("Invalid IPv6 address: '", var0, "'"));
               }
            } else {
               int var17 = 0;
               var3 = 0;

               while (var17 < var11.length) {
                  int var5 = var17;

                  while (var5 < 16 && var11[var5] == 0 && var11[var5 + 1] == 0) {
                     var5 += 2;
                  }

                  int var8 = var5 - var17;
                  int var6 = var3;
                  int var4 = var2;
                  if (var8 > var3) {
                     var6 = var3;
                     var4 = var2;
                     if (var8 >= 4) {
                        var6 = var8;
                        var4 = var17;
                     }
                  }

                  var17 = var5 + 2;
                  var3 = var6;
                  var2 = var4;
               }

               e var16 = new e();
               var17 = var7;

               while (var17 < var11.length) {
                  if (var17 == var2) {
                     var16.J(58);
                     int var23 = var17 + var3;
                     var17 = var23;
                     if (var23 == 16) {
                        var16.J(58);
                        var17 = var23;
                     }
                  } else {
                     if (var17 > 0) {
                        var16.J(58);
                     }

                     var16.L((long)((var11[var17] & 255) << 8 | var11[var17 + 1] & 255));
                     var17 += 2;
                  }
               }

               return var16.D();
            }
         }
      } else {
         try {
            var0 = IDN.toASCII(var0).toLowerCase(Locale.US);
            if (var0.isEmpty()) {
               return null;
            }
         } catch (IllegalArgumentException var14) {
            boolean var10001 = false;
            return null;
         }

         int var1 = 0;

         while (true) {
            int var19 = var3;

            label130: {
               try {
                  if (var1 >= var0.length()) {
                     break label130;
                  }

                  var19 = var0.charAt(var1);
               } catch (IllegalArgumentException var13) {
                  boolean var24 = false;
                  break;
               }

               if (var19 > 31 && var19 < 127) {
                  try {
                     var19 = " #%/:?@[\\]".indexOf(var19);
                  } catch (IllegalArgumentException var12) {
                     boolean var25 = false;
                     break;
                  }

                  if (var19 == -1) {
                     var1++;
                     continue;
                  }
               }

               var19 = 1;
            }

            if (var19) {
               return null;
            }

            return var0;
         }

         return null;
      }
   }

   public static int b(String var0, long var1, TimeUnit var3) {
      long var6;
      int var4 = (var6 = var1 - 0L) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      if (var4 >= 0) {
         if (var3 != null) {
            var1 = var3.toMillis(var1);
            if (var1 <= 2147483647L) {
               if (var1 == 0L && var4 > 0) {
                  throw new IllegalArgumentException(var0.concat(" too small."));
               } else {
                  return (int)var1;
               }
            } else {
               throw new IllegalArgumentException(var0.concat(" too large."));
            }
         } else {
            throw new NullPointerException("unit == null");
         }
      } else {
         throw new IllegalArgumentException(var0.concat(" < 0"));
      }
   }

   public static void c(Closeable var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (RuntimeException var1) {
            throw var1;
         } catch (Exception var2) {
         }
      }
   }

   public static void d(Socket var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (AssertionError var1) {
            if (!n(var1)) {
               throw var1;
            }
         } catch (RuntimeException var2) {
            throw var2;
         } catch (Exception var3) {
         }
      }
   }

   public static int e(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else {
         byte var1 = 97;
         if (var0 < 'a' || var0 > 'f') {
            var1 = 65;
            if (var0 < 'A' || var0 > 'F') {
               return -1;
            }
         }

         return var0 - var1 + 10;
      }
   }

   public static InetAddress f(String var0, int var1, int var2) {
      byte[] var10 = new byte[16];
      int var5 = var1;
      var1 = 0;
      int var4 = -1;
      int var3 = -1;

      while (var5 < var2) {
         if (var1 == 16) {
            return null;
         }

         int var6 = var5 + 2;
         if (var6 <= var2 && var0.regionMatches(var5, "::", 0, 2)) {
            if (var4 != -1) {
               return null;
            }

            var1 += 2;
            if (var6 == var2) {
               var4 = var1;
               break;
            }

            var4 = var1;
            var3 = var6;
            var5 = var1;
         } else {
            var6 = var5;
            if (var1 != 0) {
               if (!var0.regionMatches(var5, ":", 0, 1)) {
                  if (!var0.regionMatches(var5, ".", 0, 1)) {
                     return null;
                  }

                  int var8 = var1 - 2;
                  var6 = var8;
                  var5 = var3;

                  label109: {
                     while (true) {
                        label106:
                        if (var5 < var2) {
                           if (var6 == 16) {
                              break;
                           }

                           var3 = var5;
                           if (var6 != var8) {
                              if (var0.charAt(var5) != '.') {
                                 break;
                              }

                              var3 = var5 + 1;
                           }

                           int var7 = 0;

                           for (var5 = var3; var5 < var2; var5++) {
                              char var9 = var0.charAt(var5);
                              if (var9 < '0' || var9 > '9') {
                                 break;
                              }

                              if (var7 == 0 && var3 != var5) {
                                 break label106;
                              }

                              var7 = var7 * 10 + var9 - 48;
                              if (var7 > 255) {
                                 break label106;
                              }
                           }

                           if (var5 - var3 != 0) {
                              var10[var6] = (byte)var7;
                              var6++;
                              continue;
                           }
                        } else if (var6 == var8 + 4) {
                           var14 = true;
                           break label109;
                        }

                        var14 = false;
                        break label109;
                     }

                     var14 = false;
                  }

                  if (!var14) {
                     return null;
                  }

                  var1 += 2;
                  break;
               }

               var6 = var5 + 1;
            }

            var3 = var6;
            var5 = var1;
         }

         var1 = var3;

         for (var6 = 0; var1 < var2; var1++) {
            int var22 = e(var0.charAt(var1));
            if (var22 == -1) {
               break;
            }

            var6 = (var6 << 4) + var22;
         }

         int var23 = var1 - var3;
         if (var23 == 0 || var23 > 4) {
            return null;
         }

         int var25 = var5 + 1;
         var10[var5] = (byte)(var6 >>> 8 & 0xFF);
         var23 = var25 + 1;
         var10[var25] = (byte)(var6 & 0xFF);
         var5 = var1;
         var1 = var23;
      }

      if (var1 != 16) {
         if (var4 == -1) {
            return null;
         }

         var2 = var1 - var4;
         System.arraycopy(var10, var4, var10, 16 - var2, var2);
         Arrays.fill(var10, var4, 16 - var1 + var4, (byte)0);
      }

      try {
         return InetAddress.getByAddress(var10);
      } catch (UnknownHostException var11) {
         throw new AssertionError();
      }
   }

   public static int g(String var0, int var1, int var2, char var3) {
      while (var1 < var2) {
         if (var0.charAt(var1) == var3) {
            return var1;
         }

         var1++;
      }

      return var2;
   }

   public static int h(String var0, int var1, int var2, String var3) {
      while (var1 < var2) {
         if (var3.indexOf(var0.charAt(var1)) != -1) {
            return var1;
         }

         var1++;
      }

      return var2;
   }

   public static String i(Object[] var0, String var1) {
      return String.format(Locale.US, var1, var0);
   }

   public static String j(u var0, boolean var1) {
      boolean var3 = var0.d.contains(":");
      String var5 = var0.d;
      String var4 = var5;
      if (var3) {
         var4 = a.a.l("[", var5, "]");
      }

      int var2 = var0.e;
      if (!var1 && var2 == u.c(var0.a)) {
         return var4;
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append(var4);
         var6.append(":");
         var6.append(var2);
         return var6.toString();
      }
   }

   public static List k(List var0) {
      return Collections.unmodifiableList(new ArrayList(var0));
   }

   public static List l(Object... var0) {
      return Collections.unmodifiableList(Arrays.asList((Object[])var0.clone()));
   }

   public static String[] m(h var0, String[] var1, String[] var2) {
      ArrayList var8 = new ArrayList();

      for (String var7 : var1) {
         int var6 = var2.length;

         for (int var4 = 0; var4 < var6; var4++) {
            if (var0.compare(var7, var2[var4]) == 0) {
               var8.add(var7);
               break;
            }
         }
      }

      return var8.toArray(new String[var8.size()]);
   }

   public static boolean n(AssertionError var0) {
      boolean var1;
      if (var0.getCause() != null && var0.getMessage() != null && var0.getMessage().contains("getsockname failed")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean o(h var0, String[] var1, String[] var2) {
      if (var1 != null && var2 != null && var1.length != 0 && var2.length != 0) {
         for (String var7 : var1) {
            int var6 = var2.length;

            for (int var4 = 0; var4 < var6; var4++) {
               if (var0.compare(var7, var2[var4]) == 0) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean p(u var0, u var1) {
      boolean var2;
      if (var0.d.equals(var1.d) && var0.e == var1.e && var0.a.equals(var1.a)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean q(t param0, int param1, TimeUnit param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: invokestatic java/lang/System.nanoTime ()J
      // 03: lstore 5
      // 05: aload 0
      // 06: invokeinterface a1/t.a ()La1/v; 1
      // 0b: invokevirtual a1/v.e ()Z
      // 0e: ifeq 21
      // 11: aload 0
      // 12: invokeinterface a1/t.a ()La1/v; 1
      // 17: invokevirtual a1/v.c ()J
      // 1a: lload 5
      // 1c: lsub
      // 1d: lstore 3
      // 1e: goto 25
      // 21: ldc2_w 9223372036854775807
      // 24: lstore 3
      // 25: aload 0
      // 26: invokeinterface a1/t.a ()La1/v; 1
      // 2b: lload 3
      // 2c: aload 2
      // 2d: iload 1
      // 2e: i2l
      // 2f: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 32: invokestatic java/lang/Math.min (JJ)J
      // 35: lload 5
      // 37: ladd
      // 38: invokevirtual a1/v.d (J)La1/v;
      // 3b: pop
      // 3c: new a1/e
      // 3f: astore 2
      // 40: aload 2
      // 41: invokespecial a1/e.<init> ()V
      // 44: aload 0
      // 45: aload 2
      // 46: ldc2_w 8192
      // 49: invokeinterface a1/t.u (La1/e;J)J 4
      // 4e: ldc2_w -1
      // 51: lcmp
      // 52: ifeq 5c
      // 55: aload 2
      // 56: invokevirtual a1/e.x ()V
      // 59: goto 44
      // 5c: aload 0
      // 5d: invokeinterface a1/t.a ()La1/v; 1
      // 62: astore 0
      // 63: lload 3
      // 64: ldc2_w 9223372036854775807
      // 67: lcmp
      // 68: ifne 73
      // 6b: aload 0
      // 6c: invokevirtual a1/v.a ()La1/v;
      // 6f: pop
      // 70: goto 7c
      // 73: aload 0
      // 74: lload 5
      // 76: lload 3
      // 77: ladd
      // 78: invokevirtual a1/v.d (J)La1/v;
      // 7b: pop
      // 7c: bipush 1
      // 7d: ireturn
      // 7e: astore 2
      // 7f: aload 0
      // 80: invokeinterface a1/t.a ()La1/v; 1
      // 85: astore 0
      // 86: lload 3
      // 87: ldc2_w 9223372036854775807
      // 8a: lcmp
      // 8b: ifne 96
      // 8e: aload 0
      // 8f: invokevirtual a1/v.a ()La1/v;
      // 92: pop
      // 93: goto 9f
      // 96: aload 0
      // 97: lload 5
      // 99: lload 3
      // 9a: ladd
      // 9b: invokevirtual a1/v.d (J)La1/v;
      // 9e: pop
      // 9f: aload 2
      // a0: athrow
      // a1: astore 2
      // a2: aload 0
      // a3: invokeinterface a1/t.a ()La1/v; 1
      // a8: astore 0
      // a9: lload 3
      // aa: ldc2_w 9223372036854775807
      // ad: lcmp
      // ae: ifne b9
      // b1: aload 0
      // b2: invokevirtual a1/v.a ()La1/v;
      // b5: pop
      // b6: goto c2
      // b9: aload 0
      // ba: lload 5
      // bc: lload 3
      // bd: ladd
      // be: invokevirtual a1/v.d (J)La1/v;
      // c1: pop
      // c2: bipush 0
      // c3: ireturn
   }

   public static int r(String var0, int var1, int var2) {
      while (var1 < var2) {
         char var3 = var0.charAt(var1);
         if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
            return var1;
         }

         var1++;
      }

      return var2;
   }

   public static int s(String var0, int var1, int var2) {
      var2--;

      while (var2 >= var1) {
         char var3 = var0.charAt(var2);
         if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
            return var2 + 1;
         }

         var2--;
      }

      return var1;
   }

   public static s t(ArrayList var0) {
      f var1 = new f();

      for (v0.c var4 : var0) {
         q var2 = q.c;
         String var5 = var4.a.m();
         String var6 = var4.b.m();
         var2.getClass();
         var1.a(var5, var6);
      }

      return new s(var1);
   }

   public static String u(String var0, int var1, int var2) {
      var1 = r(var0, var1, var2);
      return var0.substring(var1, s(var0, var1, var2));
   }
}
