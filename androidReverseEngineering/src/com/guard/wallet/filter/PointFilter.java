package com.guard.wallet.filter;

import android.graphics.Rect;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;

public class PointFilter implements Filter {
   public static final int TYPE_CONTAINS = 1;
   public static final int TYPE_NEARLY = 2;
   private final Point point;
   private final int type;

   public PointFilter(Point var1, int var2) {
      this.point = var1;
      this.type = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      int var2 = this.type;
      boolean var8 = false;
      boolean var7 = false;
      if (var2 == 1 && var1.childCount() == 0) {
         Rect var11 = var1.boundsInScreen();
         boolean var13 = var7;
         if (var11 != null) {
            var13 = var7;
            if (var11.contains((int)this.point.getX(), (int)this.point.getY())) {
               var13 = true;
            }
         }

         return var13;
      } else {
         if (this.type == 2 && var1.childCount() == 0) {
            Rect var9 = var1.boundsInScreen();
            if (var9 != null) {
               int var5 = var9.right;
               var2 = var9.left;
               int var4 = var9.bottom;
               int var3 = var9.top;
               Point var10 = var1.centerInScreen();
               boolean var6 = var8;
               if (Math.abs(this.point.getX() - var10.getX()) <= (float)(var5 - var2) / 2.0F) {
                  var6 = var8;
                  if (Math.abs(this.point.getY() - var10.getY()) <= (float)(var4 - var3) / 2.0F) {
                     var6 = true;
                  }
               }

               return var6;
            }
         }

         return Boolean.FALSE;
      }
   }
}
