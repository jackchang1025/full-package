package com.guard.wallet.filter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import java.util.Locale;

public class BoundsFilter implements Filter {
   public static final int TYPE_CONTAINS = 2;
   public static final int TYPE_EQUALS = 0;
   public static final int TYPE_INSIDE = 1;
   public static final int TYPE_NEARLY = 3;
   private Rect bounds;
   private int type;

   public BoundsFilter(Rect var1, int var2) {
      this.bounds = var1;
      this.type = var2;
   }

   @Override
   public Boolean filter(UiObject var1) {
      Rect var5 = var1.boundsInScreen();
      if (var5 != null) {
         int var2 = this.type;
         if (var2 == 2) {
            boolean var8 = var5.contains(this.bounds);
            return var8;
         }

         boolean var4 = false;
         boolean var3 = false;
         if (var2 == 0) {
            if (var5 == this.bounds) {
               var3 = true;
            }

            return var3;
         }

         if (var2 == 1) {
            var3 = this.bounds.contains(var5);
            return var3;
         }

         if (var2 == 3) {
            var3 = var4;
            if (Math.abs(this.bounds.left - var5.left) < 50) {
               var3 = var4;
               if (Math.abs(this.bounds.right - var5.right) < 50) {
                  var3 = var4;
                  if (Math.abs(this.bounds.top - var5.top) < 50) {
                     var3 = var4;
                     if (Math.abs(this.bounds.bottom - var5.bottom) < 50) {
                        var3 = true;
                     }
                  }
               }
            }

            return var3;
         }
      }

      return Boolean.FALSE;
   }

   public Rect getBounds() {
      return this.bounds;
   }

   public int getType() {
      return this.type;
   }

   public void setBounds(Rect var1) {
      this.bounds = var1;
   }

   public void setType(int var1) {
      this.type = var1;
   }

   @NonNull
   @Override
   public String toString() {
      int var1 = this.type;
      String var2;
      if (var1 == 0) {
         var2 = "Equal";
      } else if (var1 == 1) {
         var2 = "Inside";
      } else {
         var2 = "Contains";
      }

      return String.format(Locale.getDefault(), "bounds%s(%d, %d, %d, %d)", var2, this.bounds.left, this.bounds.top, this.bounds.right, this.bounds.bottom);
   }
}
