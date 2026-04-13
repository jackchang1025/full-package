package com.guard.wallet.http;

import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.ApiResult;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import p0.f0;
import p0.g0;
import p0.j0;

public final class i {
   public static final p0.x b;
   public final String a;

   static {
      p0.x var0;
      try {
         var0 = p0.x.a("application/json; charset=utf-8");
      } catch (IllegalArgumentException var1) {
         var0 = null;
      }

      b = var0;
   }

   public i() {
      this.a = l.a;
   }

   public i(String var1) {
      if (a1.q.B(var1)) {
         var1 = l.a;
      }

      this.a = var1;
   }

   public static void c(String var0) {
      if (!a1.q.B(var0)) {
         StringBuilder var1 = new StringBuilder("finishFetch:");
         var1.append(var0);
         Log.d("FetchClient", var1.toString());
         l.c.remove(var0);
      }
   }

   public static JsonObject g(j0 var0) {
      ApiResult var1 = new ApiResult();
      var1.setSuccess(Boolean.FALSE);
      String var2;
      if (var0 != null) {
         var1.setCode(var0.c);
         var2 = var0.d;
      } else {
         var1.setCode(500);
         var2 = "Network Error";
      }

      var1.setMsg(var2);
      var1.setCount(0);
      return com.guard.wallet.utils.h.M(com.guard.wallet.utils.h.N(var1));
   }

   public static boolean l(String var0, p0.e0 var1, p0.e var2) {
      if (!a1.q.B(var0)) {
         LinkedHashMap var3 = l.c;
         if (var3.containsKey(var0) && !l.b.contains(var0)) {
            var2.b(var1, new s.b(var0));
            return true;
         }

         var3.put(var0, new Date().getTime());
      }

      return false;
   }

   public final p0.b0 a() {
      p0.a0 var1 = new p0.a0();
      TimeUnit var2 = TimeUnit.SECONDS;
      var1.t = q0.c.b("timeout", 60L, var2);
      var1.u = q0.c.b("timeout", 120L, var2);
      var1.v = q0.c.b("timeout", 120L, var2);
      var1.s = q0.c.b("timeout", 240L, var2);
      var1.r = true;
      var1.q = true;
      var1.p = true;
      var1.w = q0.c.b("interval", 30L, var2);
      var1.h = new h(this, 0);
      return new p0.b0(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final JsonObject b(f0 var1) {
      p0.b0 var2 = this.a();

      Exception var10000;
      label26: {
         try {
            var6 = p0.e0.d(var2, var1, false).b();
            var8 = var6.g;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label26;
         }

         if (var8 != null) {
            try {
               return com.guard.wallet.utils.h.M(var8.z());
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
            }
         } else {
            try {
               return g(var6);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10 = false;
            }
         }
      }

      Exception var7 = var10000;
      a1.q.s("FetchClient", var7);
      return g(null);
   }

   public final void d(Object var1, String var2, p0.e var3) {
      var2 = this.e(var1, var2);
      var1 = new l0.m();
      var1.d(var2);
      var1.b("GET", null);
      f0 var5 = var1.a();
      p0.e0 var7 = p0.e0.d(this.a(), var5, false);
      if (!l(var5.a.h, var7, var3)) {
         var7.a(var3);
      }
   }

   public final String e(Object var1, String var2) {
      String var3 = this.f(var2);
      if (var1 != null) {
         var1 = com.guard.wallet.utils.h.M(com.guard.wallet.utils.h.N(var1));
      } else {
         var1 = null;
      }

      var2 = var3;
      if (var1 != null) {
         var2 = var3;
         if (!var1.keySet().isEmpty()) {
            StringBuilder var4 = new StringBuilder();

            for (String var10 : var1.keySet()) {
               if (var1.get(var10) != null && !var1.get(var10).isJsonNull()) {
                  String var6 = var1.get(var10).getAsString();
                  if (!a1.q.B(var4.toString())) {
                     var4.append("&");
                  }

                  var4.append(var10);
                  var4.append("=");
                  var4.append(var6);
               }
            }

            var2 = var3;
            if (!a1.q.B(var4.toString())) {
               String var8;
               if (var3.contains("?")) {
                  var8 = var3.concat("&");
               } else {
                  var8 = var3.concat("?");
               }

               var2 = var8.concat(var4.toString());
            }
         }
      }

      return var2;
   }

   public final String f(String var1) {
      boolean var2 = a1.q.B(var1);
      String var3 = this.a;
      if (!var2) {
         return var1.startsWith("/") ? var3.concat(var1) : var3.concat("/").concat(var1);
      } else {
         return var3;
      }
   }

   public final void h(Object var1, String var2, p0.e var3) {
      f0 var5 = this.i(var1, var2);
      var1 = p0.e0.d(this.a(), var5, false);
      if (!l(var5.a.h, var1, var3)) {
         var1.a(var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final f0 i(Object var1, String var2) {
      String var13 = this.f(var2);
      String var14 = com.guard.wallet.utils.h.N(var1);
      Charset var23 = StandardCharsets.UTF_8;
      p0.x var11 = b;
      var1 = var11;
      if (var11 != null) {
         Object var12 = null;

         Charset var10;
         label52: {
            label51: {
               try {
                  var1 = var11.c;
               } catch (IllegalArgumentException var17) {
                  boolean var10001 = false;
                  break label51;
               }

               if (var1 != null) {
                  try {
                     var10 = Charset.forName(var1);
                     break label52;
                  } catch (IllegalArgumentException var16) {
                     boolean var27 = false;
                  }
               }
            }

            var10 = null;
         }

         var1 = var11;
         var23 = var10;
         if (var10 == null) {
            var23 = StandardCharsets.UTF_8;
            StringBuilder var20 = new StringBuilder();
            var20.append(var11);
            var20.append("; charset=utf-8");
            var1 = var20.toString();

            try {
               var1 = p0.x.a(var1);
            } catch (IllegalArgumentException var15) {
               var1 = (p0.x)var12;
            }
         }
      }

      byte[] var26 = var14.getBytes(var23);
      int var3 = var26.length;
      long var4 = (long)var26.length;
      long var6 = (long)0;
      long var8 = (long)var3;
      byte[] var24 = q0.c.a;
      if ((var6 | var8) >= 0L && var6 <= var4 && var4 - var6 >= var8) {
         g0 var22 = new g0(var3, var1, var26);
         l0.m var25 = new l0.m();
         var25.d(var13);
         var25.b("POST", var22);
         return var25.a();
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public final void j(UploadFileVO param1, String param2, LinkedList param3, p0.e param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: aload 2
      // 03: invokevirtual com/guard/wallet/http/i.e (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;
      // 06: astore 2
      // 07: new f0/t
      // 0a: dup
      // 0b: bipush 5
      // 0c: invokespecial f0/t.<init> (I)V
      // 0f: astore 5
      // 11: aload 3
      // 12: ifnull 81
      // 15: aload 3
      // 16: invokeinterface java/util/List.isEmpty ()Z 1
      // 1b: ifne 81
      // 1e: aload 3
      // 1f: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 24: astore 3
      // 25: aload 3
      // 26: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2b: ifeq 81
      // 2e: aload 3
      // 2f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 34: checkcast java/io/File
      // 37: astore 6
      // 39: aload 6
      // 3b: ifnull 25
      // 3e: aload 6
      // 40: invokevirtual java/io/File.exists ()Z
      // 43: ifeq 25
      // 46: aload 6
      // 48: invokevirtual java/io/File.isFile ()Z
      // 4b: ifeq 25
      // 4e: ldc_w "multipart/form-data"
      // 51: invokestatic p0/x.a (Ljava/lang/String;)Lp0/x;
      // 54: astore 1
      // 55: goto 5b
      // 58: astore 1
      // 59: aconst_null
      // 5a: astore 1
      // 5b: new p0/h0
      // 5e: astore 7
      // 60: aload 7
      // 62: aload 1
      // 63: aload 6
      // 65: invokespecial p0/h0.<init> (Lp0/x;Ljava/io/File;)V
      // 68: aload 5
      // 6a: aload 6
      // 6c: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 6f: aload 7
      // 71: invokevirtual f0/t.d (Ljava/lang/String;La1/q;)V
      // 74: goto 25
      // 77: astore 1
      // 78: ldc "FetchClient"
      // 7a: aload 1
      // 7b: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 7e: goto 25
      // 81: aload 5
      // 83: getstatic p0/z.t Lp0/x;
      // 86: invokevirtual f0/t.f (Lp0/x;)V
      // 89: aload 5
      // 8b: invokevirtual f0/t.e ()Lp0/z;
      // 8e: astore 3
      // 8f: new l0/m
      // 92: dup
      // 93: invokespecial l0/m.<init> ()V
      // 96: astore 1
      // 97: aload 1
      // 98: aload 2
      // 99: invokevirtual l0/m.d (Ljava/lang/String;)V
      // 9c: aload 1
      // 9d: ldc_w "PATCH"
      // a0: aload 3
      // a1: invokevirtual l0/m.b (Ljava/lang/String;La1/q;)V
      // a4: aload 1
      // a5: invokevirtual l0/m.a ()Lp0/f0;
      // a8: astore 1
      // a9: aload 0
      // aa: invokevirtual com/guard/wallet/http/i.a ()Lp0/b0;
      // ad: aload 1
      // ae: bipush 0
      // af: invokestatic p0/e0.d (Lp0/b0;Lp0/f0;Z)Lp0/e0;
      // b2: aload 4
      // b4: invokevirtual p0/e0.a (Lp0/e;)V
      // b7: return
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void k(Serializable var1, String var2, String var3, byte[] var4, p0.e var5) {
      if (a1.q.B(var3)) {
         var3 = "minicap-".concat(String.valueOf(System.currentTimeMillis())).concat(".webp");
      }

      f0.t var13;
      var2 = this.e(var1, var2);
      var13 = new f0.t(5);
      label63:
      if (var4.length > 0) {
         Exception var10000;
         label68: {
            label59: {
               try {
                  try {
                     var21 = p0.x.a("multipart/form-data");
                     break label59;
                  } catch (IllegalArgumentException var19) {
                  }
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var10001 = false;
                  break label68;
               }

               var21 = null;
            }

            int var6;
            long var11;
            try {
               var6 = var4.length;
               var11 = (long)var4.length;
            } catch (Exception var18) {
               var10000 = var18;
               boolean var29 = false;
               break label68;
            }

            long var7 = (long)0;
            long var9 = (long)var6;

            try {
               byte[] var14 = q0.c.a;
            } catch (Exception var17) {
               var10000 = var17;
               boolean var30 = false;
               break label68;
            }

            if ((var7 | var9) >= 0L && var7 <= var11 && var11 - var7 >= var9) {
               try {
                  g0 var28 = new g0(var6, var21, var4);
                  var13.d(var3, var28);
                  break label63;
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var32 = false;
               }
            } else {
               try {
                  ArrayIndexOutOfBoundsException var22 = new ArrayIndexOutOfBoundsException();
                  throw var22;
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var31 = false;
               }
            }
         }

         Exception var23 = var10000;
         a1.q.s("FetchClient", var23);
      }

      var13.f(p0.z.t);
      p0.z var24 = var13.e();
      l0.m var27 = new l0.m();
      var27.d(var2);
      var27.b("PATCH", var24);
      f0 var25 = var27.a();
      p0.e0.d(this.a(), var25, false).a(var5);
   }
}
